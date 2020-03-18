package ro.msg.learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Customer;
import ro.msg.learning.entity.Order;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.repository.CustomerRepository;
import ro.msg.learning.repository.OrderRepository;
import ro.msg.learning.service.CustomerService;
import ro.msg.learning.service.OrderDetailService;
import ro.msg.learning.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	private final OrderDetailService orderDetailService;
	private final OrderRepository orderRepository;
	private final CustomerService customerService;

	@Override
	@Transactional
	public Order createOrder(final OrderDto orderDto) {
		final List<OrderDetail> orderDetails = orderDetailService.obtainOrderDetails(orderDto);
		
		Customer customer = customerService.getCustomer(orderDto.getCustomerId()).get();
		final Order orderCreated = new Order(null, customer, orderDto.getCreatedAt(), orderDto.getAddress());
		orderRepository.save(orderCreated);
		
		orderDetailService.addOrderToOrderDetails(orderCreated, orderDetails);
		return orderCreated;
	}

}
