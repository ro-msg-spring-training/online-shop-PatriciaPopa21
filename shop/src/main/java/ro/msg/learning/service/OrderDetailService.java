package ro.msg.learning.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Order;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.repository.OrderDetailRepository;

public abstract class OrderDetailService {
	public abstract String getName();
	protected abstract List<OrderDetail> generateOrderDetails(OrderDto orderDto);
	protected OrderDetailRepository orderDetailRepository;
	protected LocationService locationService;
	protected ProductService productService;
	protected StockService stockService;

	public void addOrderToOrderDetails(final Order order, final List<OrderDetail> orderDetails) {
		orderDetails.forEach(orderDetail -> {
			orderDetail.setOrder(order);
			orderDetailRepository.save(orderDetail);
		});
	}

	@Transactional
	public List<OrderDetail> obtainOrderDetails(final OrderDto orderDto) {
		final List<OrderDetail> orderDetails = generateOrderDetails(orderDto);

		final List<OrderDetail> orderDetailsCreated = persistOrderDetails(orderDetails);

		updateStocks(orderDetails);

		return orderDetailsCreated;
	}

	private List<OrderDetail> persistOrderDetails(final List<OrderDetail> orderDetails) {
		final List<OrderDetail> orderDetailsCreated = new ArrayList<>();

		orderDetails.forEach(orderDetail -> {
			final OrderDetail orderDetailCreated = orderDetailRepository.save(orderDetail);
			orderDetailsCreated.add(orderDetailCreated);
		});

		return orderDetailsCreated;
	}

	private void updateStocks(final List<OrderDetail> orderDetails) {
		orderDetails.forEach(orderDetail -> stockService.updateStock(orderDetail.getLocation().getId(), orderDetail.getProduct().getId(), orderDetail.getQuantity()));
	}
}
