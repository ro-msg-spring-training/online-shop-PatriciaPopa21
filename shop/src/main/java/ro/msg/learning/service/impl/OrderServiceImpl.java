package ro.msg.learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Customer;
import ro.msg.learning.entity.Order;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.entity.OrderDetailDto;
import ro.msg.learning.exception.EmptyShoppingCartException;
import ro.msg.learning.repository.OrderRepository;
import ro.msg.learning.service.interfaces.CustomerService;
import ro.msg.learning.service.impl.EmailService;
import ro.msg.learning.service.interfaces.OrderDetailService;
import ro.msg.learning.service.interfaces.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	private final OrderDetailService orderDetailService;
	private final OrderRepository orderRepository;
	private final CustomerService customerService;
	private final EmailService emailService;
	
	@Override
	@Transactional
	public OrderDto createOrder(final OrderDto orderDto) {
		validateShoppingCartNotEmpty(orderDto.getOrderDetailDtos());
		
		Customer customer = customerService.getCustomerById(orderDto.getCustomerId()).get();
		final Order orderCreated = new Order(null, customer, orderDto.getCreatedAt(), orderDto.getAddress());
		orderRepository.save(orderCreated);
		
		final List<OrderDetail> orderDetails = orderDetailService.obtainOrderDetails(orderDto);
		orderDetailService.addOrderToOrderDetails(orderCreated, orderDetails);
		
		emailService.sendSimpleHtmlMail("patriciapopa1997@gmail.com", orderDto, orderCreated.getId());
		//emailService.sendTextMail("patriciapopa1997@gmail.com", orderDto, orderCreated.getId());
		
		return orderDto;
	}

	private void validateShoppingCartNotEmpty(List<OrderDetailDto> orderDetailDtos) {
		if(orderDetailDtos.isEmpty()) {
			throw new EmptyShoppingCartException();
		}
	}
}