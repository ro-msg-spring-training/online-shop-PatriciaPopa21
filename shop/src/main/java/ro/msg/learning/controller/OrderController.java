package ro.msg.learning.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Order;
import ro.msg.learning.service.interfaces.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping(value = "/orders")
	@ResponseStatus(HttpStatus.CREATED)
	public Order createOrder(@RequestBody final OrderDto orderDto) {
		return orderService.createOrder(orderDto);
	}
}
