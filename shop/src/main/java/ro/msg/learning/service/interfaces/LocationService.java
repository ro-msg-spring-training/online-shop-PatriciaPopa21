package ro.msg.learning.service.interfaces;

import java.util.List;

import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.OrderDetailDto;

public interface LocationService {
	Location getProductMostAbundantShippingLocation(Integer productId, Integer quantity);

	Location getSingleShippingLocationForAllProducts(List<OrderDetailDto> orderDetailDtos);

	List<Location> getAllLocations();

	Location getLocation(Integer locationId);

	Integer getWithdrawnQuantity(Integer productId, Integer totalDesiredQuantity, Location location);
}
