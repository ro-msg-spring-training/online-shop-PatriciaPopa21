package ro.msg.learning.service.interfaces;

import java.util.List;
import java.util.Map;

import ro.msg.learning.entity.Location;

public interface LocationService {
	Location getProductMostAbundantShippingLocation(Integer productId, Integer quantity);

	Location getSingleShippingLocationForAllProducts(Map<String, Integer> productsAndCorrespondingQuantities);

	List<Location> getAllLocations();

	Location getLocation(Integer locationId);

	Integer getWithdrawnQuantity(Integer productId, Integer totalDesiredQuantity, Location location);
}
