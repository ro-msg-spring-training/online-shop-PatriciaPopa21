package ro.msg.learning.service;

import java.util.Map;

import ro.msg.learning.entity.Location;

public interface LocationService {
	Location getProductMostAbundantShippingLocation(Integer productId, Integer quantity);

	Location getSingleShippingLocationForAllProducts(Map<String, Integer> productsAndCorrespondingQuantities);
}
