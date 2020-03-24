package ro.msg.learning.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import ro.msg.learning.entity.Address;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.OrderDetailDto;
import ro.msg.learning.entity.Product;
import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.entity.Stock;
import ro.msg.learning.entity.Supplier;
import ro.msg.learning.exception.InexistentIdException;
import ro.msg.learning.exception.SuitableShippingLocationNotFoundException;
import ro.msg.learning.service.impl.LocationServiceImpl;
import ro.msg.learning.service.impl.ProductServiceImpl;
import ro.msg.learning.service.impl.StockServiceImpl;

@DataJpaTest
@Import({ LocationServiceImpl.class, ProductServiceImpl.class, StockServiceImpl.class })
public class LocationPickingStrategyWithActualDBTests {
	private final Address address1 = new Address("Romania", "Timisoara", "Timis", "Str. Gh. Lazar nr. 2");
	private final Address address2 = new Address("Romania", "Bucuresti", "Ilfov", "Splaiul Nicolae Titulescu nr. 4");

	private final Location location1 = new Location("Cladirea A", address1);
	private final Location location2 = new Location("Cladirea B", address2);

	private final ProductCategory productCategory1 = new ProductCategory("Books", "Classical literature");
	private final ProductCategory productCategory2 = new ProductCategory("Watches", "Hand watch");

	private final Supplier supplier1 = new Supplier("Elefant.ro");
	private final Supplier supplier2 = new Supplier("Emag");

	private final Product product1 = new Product("Jane Eyre", "A nice book", new BigDecimal(25), 40.0, productCategory1,
			supplier1, "/janeEyre");
	private final Product product2 = new Product("GShock X33", "A nice watch", new BigDecimal(200), 120.9,
			productCategory2, supplier2, "/gShockX33");

	private static final Integer INEXISTENT_ID = 9999;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private LocationServiceImpl locationServiceImpl;

	@BeforeEach
	@Transactional
	void init() {
		/*
		 * refresh() is needed for each entity in order to avoid
		 * TransientPropertyValueException
		 */

		entityManager.persist(location1);
		entityManager.persist(location2);

		entityManager.refresh(location1);
		entityManager.refresh(location2);

		entityManager.persist(productCategory1);
		entityManager.persist(productCategory2);

		entityManager.refresh(productCategory1);
		entityManager.refresh(productCategory2);

		entityManager.persist(supplier1);
		entityManager.persist(supplier2);

		entityManager.refresh(supplier1);
		entityManager.refresh(supplier2);

		entityManager.persist(product1);
		entityManager.persist(product2);

		entityManager.refresh(product1);
		entityManager.refresh(product2);
	}

	@Test
	void When_OneCommonShippingLocationExists_Expect_ReturnLocation() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 10);
		final Stock stock3 = new Stock(location1, product2, 0);
		final Stock stock4 = new Stock(location2, product2, 5);

		setupStockData(stock1, stock2, stock3, stock4);

		final List<OrderDetailDto> orderDetailDtos = getInputData(1, 1);

		final Location location = locationServiceImpl.getSingleShippingLocationForAllProducts(orderDetailDtos);

		assertEquals("Cladirea B", location.getLocationName());
		assertEquals("Bucuresti", location.getAddress().getCity());
		assertEquals("Romania", location.getAddress().getCountry());
		assertEquals("Ilfov", location.getAddress().getCounty());
		assertEquals("Splaiul Nicolae Titulescu nr. 4", location.getAddress().getStreetAddress());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_MoreCommonShippingLocationsExist_Expect_ReturnLocationWithSmallestId() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 5);
		final Stock stock3 = new Stock(location1, product2, 5);
		final Stock stock4 = new Stock(location2, product2, 5);

		setupStockData(stock1, stock2, stock3, stock4);

		final List<OrderDetailDto> orderDetailDtos = getInputData(1, 1);

		final Location location = locationServiceImpl.getSingleShippingLocationForAllProducts(orderDetailDtos);

		assertEquals("Cladirea A", location.getLocationName());
		assertEquals("Timisoara", location.getAddress().getCity());
		assertEquals("Romania", location.getAddress().getCountry());
		assertEquals("Timis", location.getAddress().getCounty());
		assertEquals("Str. Gh. Lazar nr. 2", location.getAddress().getStreetAddress());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_NoShippingLocationExistsForOneProduct_Expect_RetrieveSingleShippingLocationToFail() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 10);
		final Stock stock3 = new Stock(location1, product2, 5);
		final Stock stock4 = new Stock(location2, product2, 0);

		setupStockData(stock1, stock2, stock3, stock4);

		final List<OrderDetailDto> orderDetailDtos = getInputData(1, 6);

		final SuitableShippingLocationNotFoundException exception = Assertions
				.assertThrows(SuitableShippingLocationNotFoundException.class, () -> {
					locationServiceImpl.getSingleShippingLocationForAllProducts(orderDetailDtos);
				});

		assertEquals("Your order couldn't be processed. Not enough products on stock for product: " + product2,
				exception.getMessage());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_NoCommonShippingLocationFound_Expect_RetrieveSingleShippingLocationToFail() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 0);
		final Stock stock3 = new Stock(location1, product2, 0);
		final Stock stock4 = new Stock(location2, product2, 2);

		setupStockData(stock1, stock2, stock3, stock4);

		final List<OrderDetailDto> orderDetailDtos = getInputData(2, 1);

		final SuitableShippingLocationNotFoundException exception = Assertions
				.assertThrows(SuitableShippingLocationNotFoundException.class, () -> {
					locationServiceImpl.getSingleShippingLocationForAllProducts(orderDetailDtos);
				});

		assertEquals(
				"Your order couldn't be processed. No common shipping location was found for the products you have requested",
				exception.getMessage());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_WrongProductIdGiven_Expect_RetrieveSingleShippingLocationToFail() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 5);
		final Stock stock3 = new Stock(location1, product2, 5);
		final Stock stock4 = new Stock(location2, product2, 5);

		setupStockData(stock1, stock2, stock3, stock4);

		final List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
		orderDetailDtos.add(new OrderDetailDto(INEXISTENT_ID, 1));

		final InexistentIdException exception = Assertions.assertThrows(InexistentIdException.class, () -> {
			locationServiceImpl.getSingleShippingLocationForAllProducts(orderDetailDtos);
		});

		assertEquals("No product was found for the given id: " + INEXISTENT_ID, exception.getMessage());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_OneMostAbundantLocationExists_Expect_ReturnLocation() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 10);
		final Stock stock3 = new Stock(location1, product2, 5);
		final Stock stock4 = new Stock(location2, product2, 0);

		setupStockData(stock1, stock2, stock3, stock4);

		final Integer id1 = (Integer) entityManager.getId(product1);

		final Location location = locationServiceImpl.getProductMostAbundantShippingLocation(id1, 1);

		assertEquals("Cladirea B", location.getLocationName());
		assertEquals("Bucuresti", location.getAddress().getCity());
		assertEquals("Romania", location.getAddress().getCountry());
		assertEquals("Ilfov", location.getAddress().getCounty());
		assertEquals("Splaiul Nicolae Titulescu nr. 4", location.getAddress().getStreetAddress());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_MoreMostAbundantLocationsExists_Expect_ReturnLocationWithSmallestId() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 5);
		final Stock stock3 = new Stock(location1, product2, 5);
		final Stock stock4 = new Stock(location2, product2, 0);

		setupStockData(stock1, stock2, stock3, stock4);

		final Integer id1 = (Integer) entityManager.getId(product1);

		final Location location = locationServiceImpl.getProductMostAbundantShippingLocation(id1, 1);

		assertEquals("Cladirea A", location.getLocationName());
		assertEquals("Timisoara", location.getAddress().getCity());
		assertEquals("Romania", location.getAddress().getCountry());
		assertEquals("Timis", location.getAddress().getCounty());
		assertEquals("Str. Gh. Lazar nr. 2", location.getAddress().getStreetAddress());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_NoLocationWithEnoughStockFound_Expect_RetrieveMostAbundantShippingLocationToFail() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 5);
		final Stock stock3 = new Stock(location1, product2, 5);
		final Stock stock4 = new Stock(location2, product2, 5);

		setupStockData(stock1, stock2, stock3, stock4);

		final Integer id1 = (Integer) entityManager.getId(product1);

		final SuitableShippingLocationNotFoundException exception = Assertions
				.assertThrows(SuitableShippingLocationNotFoundException.class, () -> {
					locationServiceImpl.getProductMostAbundantShippingLocation(id1, 10);
				});

		assertEquals("Your order couldn't be processed. Not enough products on stock for product: " + product1,
				exception.getMessage());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Test
	void When_WrongProductIdGiven_Expect_RetrieveMostAbundantShippingLocationToFail() {
		final Stock stock1 = new Stock(location1, product1, 5);
		final Stock stock2 = new Stock(location2, product1, 5);
		final Stock stock3 = new Stock(location1, product2, 5);
		final Stock stock4 = new Stock(location2, product2, 5);

		setupStockData(stock1, stock2, stock3, stock4);

		final InexistentIdException exception = Assertions.assertThrows(InexistentIdException.class, () -> {
			locationServiceImpl.getProductMostAbundantShippingLocation(INEXISTENT_ID, 1);
		});

		assertEquals("No product was found for the given id: " + INEXISTENT_ID, exception.getMessage());

		tearDownStockData(stock1, stock2, stock3, stock4);
	}

	@Transactional
	private void setupStockData(final Stock... stocks) {
		for (final Stock stock : stocks) {
			entityManager.persist(stock);
		}
	}

	@Transactional
	private void tearDownStockData(final Stock... stocks) {
		for (final Stock stock : stocks) {
			entityManager.remove(stock);
		}
	}

	private List<OrderDetailDto> getInputData(final Integer quantity1, final Integer quantity2) {
		/*
		 * We need to find the new id for the products with each new test that uses
		 * them, because they get incremented all the time, as a result of using a
		 * single DB instance for performing all the tests and running init() before
		 * each test. This could obviously be fixed avoided by running init() only once,
		 * before all the tests. This would mean making it static, which implies our
		 * EntityManager instance needs to be static as well, but this will cause it to
		 * be initialized with null, and consequently break the tests. This step is not
		 * actually necessary on the first test
		 */

		final Integer id1 = (Integer) entityManager.getId(product1);
		final Integer id2 = (Integer) entityManager.getId(product2);

		final List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
		orderDetailDtos.add(new OrderDetailDto(id1, quantity1));
		orderDetailDtos.add(new OrderDetailDto(id2, quantity2));

		return orderDetailDtos;
	}
}
