package ro.msg.learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ro.msg.learning.entity.Location;
import ro.msg.learning.exception.SuitableShippingLocationNotFoundException;
import ro.msg.learning.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService{
	private final LocationRepository locationRepository;

	public LocationServiceImpl(final LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public Location getSingleShippingLocationForAllProducts(final Map<String, Integer> productsAndCorrespondingQuantities) {
		final List<List<Location>> allValidShippingLocationsPerProduct = new ArrayList<>();

		for(final String productIdAsString : productsAndCorrespondingQuantities.keySet() ) {
			final Integer productId = Integer.parseInt(productIdAsString);
			final Integer quantity = productsAndCorrespondingQuantities.get(productIdAsString);

			final List<Location> validShippingLocationsForCurrentProduct = locationRepository.getAllAvailableShippingLocations(productId, quantity);

			validateAtLeastOneLocationFound(validShippingLocationsForCurrentProduct);

			allValidShippingLocationsPerProduct.add(validShippingLocationsForCurrentProduct);
		}

		if(allValidShippingLocationsPerProduct == null || allValidShippingLocationsPerProduct.isEmpty()) {
			throw new SuitableShippingLocationNotFoundException();
		}

		final Location finalShippingLocation = computeFinalShippingLocation(allValidShippingLocationsPerProduct);

		validateLocationExistence(finalShippingLocation);

		return finalShippingLocation;
	}

	private Location computeFinalShippingLocation(final List<List<Location>> allValidShippingLocationsPerProduct) {
		final List<Location> validCommonLocations = computeValidCommonLocations(allValidShippingLocationsPerProduct);

		validateCommonLocationExistence(validCommonLocations);

		// return the first valid location, based on the location id
		return validCommonLocations.get(0);
	}

	private List<Location> computeValidCommonLocations(final List<List<Location>> allValidShippingLocationsPerProduct) {
		for(final List<Location> locationsPerProduct : allValidShippingLocationsPerProduct) {
			allValidShippingLocationsPerProduct.get(0).retainAll(locationsPerProduct);
		}

		final List<Location> validCommonLocations = allValidShippingLocationsPerProduct.get(0);
		return validCommonLocations;
	}

	private void validateAtLeastOneLocationFound(final List<Location> validShippingLocationsForCurrentProduct){
		if(validShippingLocationsForCurrentProduct == null || validShippingLocationsForCurrentProduct.isEmpty()) {
			throw new SuitableShippingLocationNotFoundException();
		}
	}

	@Override
	public Location getProductMostAbundantShippingLocation(final Integer productId, final Integer quantity) {
		final Location shippingLocation = locationRepository.getProductMostAbundantShippingLocation(productId, quantity);

		validateLocationExistence(shippingLocation);

		return shippingLocation;
	}

	private void validateLocationExistence(final Location shippingLocation) {
		if(shippingLocation == null) {
			throw new SuitableShippingLocationNotFoundException();
		}
	}

	private void validateCommonLocationExistence(final List<Location> suitableShippingLocations) {
		if(suitableShippingLocations == null || suitableShippingLocations.isEmpty()) {
			throw new SuitableShippingLocationNotFoundException();
		}
	}
}
