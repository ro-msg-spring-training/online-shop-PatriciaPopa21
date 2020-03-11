package ro.msg.learning.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Order;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	private final OrderDetailService orderDetailService;
	private final OrderRepository orderRepository;

	public OrderServiceImpl(final OrderDetailService orderDetailService, final OrderRepository orderRepository) {
		this.orderDetailService = orderDetailService;
		this.orderRepository = orderRepository;
	}

	@Override
	@Transactional
	public Order createOrder(final OrderDto orderDto) {
		final List<OrderDetail> orderDetails = orderDetailService.obtainOrderDetails(orderDto);
		final Order orderCreated = new Order(null, orderDto.getCustomer(), orderDto.getCreatedAt(), orderDto.getAddress());
		orderRepository.save(orderCreated);
		orderDetailService.addOrderToOrderDetails(orderCreated, orderDetails);
		return orderCreated;
	}

}
