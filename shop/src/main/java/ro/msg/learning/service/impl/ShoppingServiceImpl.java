package ro.msg.learning.service.impl;

import org.springframework.stereotype.Service;

import lombok.Data;
import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.OrderDetailDto;
import ro.msg.learning.service.interfaces.ShoppingService;

@Service
@Data
public class ShoppingServiceImpl implements ShoppingService {
	private final OrderDto shoppingCart;

	public ShoppingServiceImpl() {
		shoppingCart = new OrderDto();
	}

	@Override
	public void addToShoppingCart(final OrderDetailDto productDetails) {
		shoppingCart.getOrderDetailDtos().add(productDetails);
	}

	@Override
	public void clearShoppingCart() {
		shoppingCart.setAddress(null);
		shoppingCart.setCreatedAt(null);
		shoppingCart.setCustomerId(null);
		shoppingCart.getOrderDetailDtos().clear();
	}
}
