package ro.msg.learning.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.msg.learning.entity.Address;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.Product;
import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.entity.Stock;
import ro.msg.learning.entity.Supplier;
import ro.msg.learning.exception.InexistentIdException;
import ro.msg.learning.exception.SuitableShippingLocationNotFoundException;
import ro.msg.learning.repository.LocationRepository;
import ro.msg.learning.repository.ProductRepository;
import ro.msg.learning.service.LocationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationPickingStrategyWithMocksTests {
	private final Address address1 = new Address("Romania","Timisoara","Timis","Str. Gh. Lazar nr. 2");
	private final Address address2 = new Address("Romania","Bucuresti","Ilfov","Splaiul Nicolae Titulescu nr. 4");

	private final Location location1 = new Location("Cladirea A", address1);
	private final Location location2 = new Location("Cladirea B", address2);

	private final ProductCategory productCategory1 = new ProductCategory("Books", "Classical literature");
	private final ProductCategory productCategory2 = new ProductCategory("Watches", "Hand watch");

	private final Supplier supplier1 = new Supplier("Elefant.ro");
	private final Supplier supplier2 = new Supplier("Emag");

	private final Product product1 = new Product("Jane Eyre", "A nice book", new BigDecimal(25), 40.0, productCategory1, supplier1, "/janeEyre");
	private final Product product2 = new Product("GShock X33", "A nice watch", new BigDecimal(200), 120.9, productCategory2, supplier2, "/gShockX33");

	private static final Integer INEXISTENT_ID = 9999;

	@Mock
	private LocationRepository locationRepository;

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private LocationServiceImpl locationServiceImpl;

	@BeforeEach
	void init() {
		lenient().when(productRepository.findById(1)).thenReturn(Optional.of(product1));
		lenient().when(productRepository.findById(2)).thenReturn(Optional.of(product2));
		lenient().when(productRepository.findById(INEXISTENT_ID)).thenReturn(Optional.empty());
	}

	@Test
	void When_OneCommonShippingLocationExists_Expect_ReturnLocation(){
		/* we need this heavy syntax for generating an arraylist in order to prevent an exception when calling
		 * retainAll() on an abstract list */
		when(locationRepository.getAllAvailableShippingLocations(1, 1)).thenReturn(new ArrayList<>(Arrays.asList(location1, location2)));
		when(locationRepository.getAllAvailableShippingLocations(2, 1)).thenReturn(new ArrayList<>(Arrays.asList(location2)));

		final Map<String, Integer> productsAndCorrespondingQuantities = new HashMap<>();
		productsAndCorrespondingQuantities.put("1", 1);
		productsAndCorrespondingQuantities.put("2", 1);

		final Location location = locationServiceImpl.getSingleShippingLocationForAllProducts(productsAndCorrespondingQuantities);

		assertEquals("Cladirea B", location.getLocationName());
		assertEquals("Bucuresti", location.getAddress().getCity());
		assertEquals("Romania", location.getAddress().getCountry());
		assertEquals("Ilfov", location.getAddress().getCounty());
		assertEquals("Splaiul Nicolae Titulescu nr. 4", location.getAddress().getStreetAddress());

	}

	@Test
	void When_NoShippingLocationExistsForOneProduct_Expect_RetrieveSingleShippingLocationToFail(){
		when(locationRepository.getAllAvailableShippingLocations(1, 1)).thenReturn(new ArrayList<>(Arrays.asList(location1, location2)));
		when(locationRepository.getAllAvailableShippingLocations(2, 1)).thenReturn(new ArrayList<>());

		final Map<String, Integer> productsAndCorrespondingQuantities = new HashMap<>();
		productsAndCorrespondingQuantities.put("1", 1);
		productsAndCorrespondingQuantities.put("2", 1);

		final SuitableShippingLocationNotFoundException exception = Assertions.assertThrows(SuitableShippingLocationNotFoundException.class, () -> {
			locationServiceImpl.getSingleShippingLocationForAllProducts(productsAndCorrespondingQuantities);
		});

		assertEquals("Your order couldn't be processed. Not enough products on stock for product: " + product2, exception.getMessage());
	}


	@Test
	void When_NoCommonShippingLocationFound_Expect_RetrieveSingleShippingLocationToFail(){
		when(locationRepository.getAllAvailableShippingLocations(1, 1)).thenReturn(new ArrayList<>(Arrays.asList(location1)));
		when(locationRepository.getAllAvailableShippingLocations(2, 1)).thenReturn(new ArrayList<>(Arrays.asList(location2)));

		final Map<String, Integer> productsAndCorrespondingQuantities = new HashMap<>();
		productsAndCorrespondingQuantities.put("1", 1);
		productsAndCorrespondingQuantities.put("2", 1);

		final SuitableShippingLocationNotFoundException exception = Assertions.assertThrows(SuitableShippingLocationNotFoundException.class, () -> {
			locationServiceImpl.getSingleShippingLocationForAllProducts(productsAndCorrespondingQuantities);
		});

		assertEquals("Your order couldn't be processed. No common shipping location was found for the products you have requested", exception.getMessage());
	}

	@Test
	void When_WrongProductIdGiven_Expect_RetrieveSingleShippingLocationToFail(){
		final Map<String, Integer> productsAndCorrespondingQuantities = new HashMap<>();
		productsAndCorrespondingQuantities.put(INEXISTENT_ID + "", 1);

		final InexistentIdException exception = Assertions.assertThrows(InexistentIdException.class, () -> {
			locationServiceImpl.getSingleShippingLocationForAllProducts(productsAndCorrespondingQuantities);
		});

		assertEquals("No product was found for the given id: " + INEXISTENT_ID, exception.getMessage());
	}

	@Test
	void When_OneMostAbundantLocationExists_Expect_ReturnLocation(){
		when(locationRepository.getProductMostAbundantShippingLocation(1, 1)).thenReturn(location2);

		final Location location = locationServiceImpl.getProductMostAbundantShippingLocation(1, 1);

		assertEquals("Cladirea B", location.getLocationName());
		assertEquals("Bucuresti", location.getAddress().getCity());
		assertEquals("Romania", location.getAddress().getCountry());
		assertEquals("Ilfov", location.getAddress().getCounty());
		assertEquals("Splaiul Nicolae Titulescu nr. 4", location.getAddress().getStreetAddress());
	}


	@Test
	void When_NoLocationWithEnoughStockFound_Expect_RetrieveMostAbundantShippingLocationToFail(){
		when(locationRepository.getProductMostAbundantShippingLocation(1, 1)).thenReturn(null);

		final SuitableShippingLocationNotFoundException exception = Assertions.assertThrows(SuitableShippingLocationNotFoundException.class, () -> {
			locationServiceImpl.getProductMostAbundantShippingLocation(1, 1);
		});

		assertEquals("Your order couldn't be processed. Not enough products on stock for product: " + product1, exception.getMessage());
	}

	@Test
	void When_WrongProductIdGiven_Expect_RetrieveMostAbundantShippingLocationToFail(){
		final InexistentIdException exception = Assertions.assertThrows(InexistentIdException.class, () -> {
			locationServiceImpl.getProductMostAbundantShippingLocation(INEXISTENT_ID, 10);
		});

		assertEquals("No product was found for the given id: " + INEXISTENT_ID, exception.getMessage());
	}
}
