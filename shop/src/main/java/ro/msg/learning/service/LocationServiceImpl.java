package ro.msg.learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.Product;
import ro.msg.learning.exception.InexistentIdException;
import ro.msg.learning.exception.SuitableShippingLocationNotFoundException;
import ro.msg.learning.repository.LocationRepository;
import ro.msg.learning.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
	private static final String NOT_ENOUGH_PRODUCTS = "Your order couldn't be processed. Not enough products on stock for product: ";

	private static final String NO_COMMON_SHIPPING_LOCATION_FOUND = "Your order couldn't be processed. No common shipping location was found for the products you have requested";

	private final LocationRepository locationRepository;
	
	private final ProductRepository productRepository;

	@Override
	public Location getSingleShippingLocationForAllProducts(final Map<String, Integer> productsAndCorrespondingQuantities){
		final List<List<Location>> validShippingLocationsPerProduct = getAllValidLocationsForEachProduct(
				productsAndCorrespondingQuantities);

		final Location singleShippingLocation = computeSingleShippingLocation(validShippingLocationsPerProduct);

		return singleShippingLocation;
	}

	private List<List<Location>> getAllValidLocationsForEachProduct(
			final Map<String, Integer> productsAndCorrespondingQuantities) {
		final List<List<Location>> validShippingLocationsPerProduct = new ArrayList<>();

		for(final String productIdAsString : productsAndCorrespondingQuantities.keySet() ) {
			final Integer productId = Integer.parseInt(productIdAsString);
			final Integer quantity = productsAndCorrespondingQuantities.get(productIdAsString);

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

	@Override
	public Location getProductMostAbundantShippingLocation(final Integer productId, final Integer quantity) {
		validateProductIdExistence(productId);
		
		final Location shippingLocation = locationRepository.getProductMostAbundantShippingLocation(productId, quantity);

		validateLocationNotNull(shippingLocation, productId);

		return shippingLocation;
	}

	private void validateProductIdExistence(Integer productId) {
		Optional<Product> product = productRepository.findById(productId);
		
		if(!product.isPresent()) {
			throw new InexistentIdException("No product was found for the given id: " + productId);
		}
	}
	
	private void validateShippingLocationsForCurrentProductNotEmpty(final List<Location> validShippingLocationsForCurrentProduct, final Integer productId){
		if(validShippingLocationsForCurrentProduct == null || validShippingLocationsForCurrentProduct.isEmpty()) {
			Product product = productRepository.findById(productId).get();
			throw new SuitableShippingLocationNotFoundException(NOT_ENOUGH_PRODUCTS + product);
		}
	}

	private void validateLocationNotNull(final Location shippingLocation, final Integer productId) {
		if(shippingLocation == null) {
			Product product = productRepository.findById(productId).get();
			throw new SuitableShippingLocationNotFoundException(NOT_ENOUGH_PRODUCTS + product);
		}
	}

	private void validateCommonLocationExistence(final List<Location> suitableShippingLocations) {
		if(suitableShippingLocations == null || suitableShippingLocations.isEmpty()) {
			throw new SuitableShippingLocationNotFoundException(NO_COMMON_SHIPPING_LOCATION_FOUND);
		}
	}
}
