package ro.msg.learning.service.interfaces;

import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Order;

public interface OrderService {
	Order createOrder(OrderDto orderDto);
}
