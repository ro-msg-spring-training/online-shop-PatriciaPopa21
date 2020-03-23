package ro.msg.learning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.OrderDetailDto;
import ro.msg.learning.entity.Product;
import ro.msg.learning.entity.Stock;
import ro.msg.learning.exception.InexistentIdException;
import ro.msg.learning.exception.SuitableShippingLocationNotFoundException;
import ro.msg.learning.repository.LocationRepository;
import ro.msg.learning.service.interfaces.LocationService;
import ro.msg.learning.service.interfaces.ProductService;
import ro.msg.learning.service.interfaces.StockService;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService{
	private static final String INEXISTENT_ID = "No product was found for the given id: ";

	private static final String NOT_ENOUGH_PRODUCTS = "Your order couldn't be processed. Not enough products on stock for product: ";

	private static final String NO_COMMON_SHIPPING_LOCATION_FOUND = "Your order couldn't be processed. No common shipping location was found for the products you have requested";

	private final LocationRepository locationRepository;

	private final ProductService productService;
	
	private final StockService stockService;

	@Override
	public Integer getWithdrawnQuantity(Integer productId, Integer totalDesiredQuantity, Location location) {
		validateProductIdExistence(productId);
		
		Stock stock = stockService.getStockByLocationAndProduct(productId, location.getId());
		Integer quantityOnStock = stock.getQuantity();
		Integer withdrawnQuantity = (totalDesiredQuantity > quantityOnStock) ? quantityOnStock : totalDesiredQuantity;
		
		return withdrawnQuantity;
	}
	
	@Override
	public Location getLocation(final Integer id) {
		return locationRepository.findById(id).get();
	}

	@Override
	public List<Location> getAllLocations() {
		return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Location getSingleShippingLocationForAllProducts(final List<OrderDetailDto> orderDetailDtos){
		final List<List<Location>> validShippingLocationsPerProduct = getAllValidLocationsForEachProduct(
				orderDetailDtos);

		final Location singleShippingLocation = computeSingleShippingLocation(validShippingLocationsPerProduct);

		return singleShippingLocation;
	}

	@Override
	public Location getProductMostAbundantShippingLocation(final Integer productId, final Integer quantity) {
		validateProductIdExistence(productId);

		final Location shippingLocation = locationRepository.getProductMostAbundantShippingLocation(productId, quantity);

		validateLocationNotNull(shippingLocation, productId);

		return shippingLocation;
	}

	private List<List<Location>> getAllValidLocationsForEachProduct(final List<OrderDetailDto> orderDetailDtos) {
		final List<List<Location>> validShippingLocationsPerProduct = new ArrayList<>();

		for(final OrderDetailDto orderDetailDto : orderDetailDtos) {
			final Integer productId = orderDetailDto.getProductId();
			final Integer quantity = orderDetailDto.getQuantity();

			validateProductIdExistence(productId);

			final List<Location> validShippingLocationsForCurrentProduct = locationRepository.getAllAvailableShippingLocations(productId, quantity);

			validateShippingLocationsForCurrentProductNotEmpty(validShippingLocationsForCurrentProduct, productId);

			validShippingLocationsPerProduct.add(validShippingLocationsForCurrentProduct);
		}

		return validShippingLocationsPerProduct;
	}

	private Location computeSingleShippingLocation(final List<List<Location>> allValidShippingLocationsPerProduct) {
		final List<Location> validCommonLocations = computeValidCommonLocations(allValidShippingLocationsPerProduct);

		validateCommonLocationExistence(validCommonLocations);

		// the final shipping location will be the first valid location in the list, which is already sorted based on the location id
		final Location singleShippingLocation = validCommonLocations.get(0);

		return singleShippingLocation;
	}

	private List<Location> computeValidCommonLocations(final List<List<Location>> allValidShippingLocationsPerProduct) {
		allValidShippingLocationsPerProduct.forEach(locationsPerProduct -> allValidShippingLocationsPerProduct.get(0).retainAll(locationsPerProduct));

		final List<Location> validCommonLocations = allValidShippingLocationsPerProduct.get(0);

		return validCommonLocations;
	}

	private void validateProductIdExistence(final Integer productId) {
		final Optional<Product> product = productService.getProduct(productId);

		if(!product.isPresent()) {
			throw new InexistentIdException(INEXISTENT_ID + productId);
		}
	}

	private void validateShippingLocationsForCurrentProductNotEmpty(final List<Location> validShippingLocationsForCurrentProduct, final Integer productId){
		if(validShippingLocationsForCurrentProduct == null || validShippingLocationsForCurrentProduct.isEmpty()) {
			final Product product = productService.getProduct(productId).get();
			throw new SuitableShippingLocationNotFoundException(NOT_ENOUGH_PRODUCTS + product);
		}
	}

	private void validateLocationNotNull(final Location shippingLocation, final Integer productId) {
		if(shippingLocation == null) {
			final Product product = productService.getProduct(productId).get();
			throw new SuitableShippingLocationNotFoundException(NOT_ENOUGH_PRODUCTS + product);
		}
	}

	private void validateCommonLocationExistence(final List<Location> suitableShippingLocations) {
		if(suitableShippingLocations == null || suitableShippingLocations.isEmpty()) {
			throw new SuitableShippingLocationNotFoundException(NO_COMMON_SHIPPING_LOCATION_FOUND);
		}
	}
}
