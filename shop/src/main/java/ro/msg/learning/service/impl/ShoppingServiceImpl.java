package ro.msg.learning.service.impl;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	public void clearShoppingCart() {
		shoppingCart.setAddress(null);
		shoppingCart.setCreatedAt(null);
		shoppingCart.setCustomerId(null);
		shoppingCart.getOrderDetailDtos().clear();
	}

	@Override
	public void addToShoppingCart(final OrderDetailDto productDetails) {
		final List<OrderDetailDto> orderDetailDtos = shoppingCart.getOrderDetailDtos();
		final Predicate<? super OrderDetailDto> predicate = orderDetailDto -> orderDetailDto
				.getProductId() == productDetails.getProductId();
		final boolean isProductAlreadyInShoppingCart = orderDetailDtos.stream().anyMatch(predicate);

		if (isProductAlreadyInShoppingCart) {
			updateProductWithExtraQuantity(productDetails, orderDetailDtos, predicate);
		} else {
			shoppingCart.getOrderDetailDtos().add(productDetails);
		}
	}

	private void updateProductWithExtraQuantity(final OrderDetailDto productDetails,
			final List<OrderDetailDto> orderDetailDtos, final Predicate<? super OrderDetailDto> predicate) {
		final OrderDetailDto orderDetailDtoToUpdate = orderDetailDtos.stream().filter(predicate)
				.collect(Collectors.toList()).get(0);
		orderDetailDtoToUpdate.setQuantity(orderDetailDtoToUpdate.getQuantity() + productDetails.getQuantity());
	}
}
