package ro.msg.learning.service.interfaces;

import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.OrderDetailDto;

public interface ShoppingService {
	OrderDto getShoppingCart();

	void addToShoppingCart(OrderDetailDto productDetails);

	void clearShoppingCart();
}
