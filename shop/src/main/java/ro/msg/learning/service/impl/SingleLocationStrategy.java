package ro.msg.learning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.entity.Product;
import ro.msg.learning.repository.OrderDetailRepository;
import ro.msg.learning.service.interfaces.LocationService;
import ro.msg.learning.service.interfaces.OrderDetailService;
import ro.msg.learning.service.interfaces.ProductService;
import ro.msg.learning.service.interfaces.StockService;

@Service
public class SingleLocationStrategy extends OrderDetailService {

	public SingleLocationStrategy(final LocationService locationService, final ProductService productService,
			final OrderDetailRepository orderDetailRepository, final StockService stockService) {
		this.locationService = locationService;
		this.productService = productService;
		this.orderDetailRepository = orderDetailRepository;
		this.stockService = stockService;
	}

	@Override
	public String getName() {
		return "single_location";
	}

	@Override
	protected List<OrderDetail> generateOrderDetails(final OrderDto orderDto) {
		final List<OrderDetail> orderDetails = new ArrayList<>();

		final Map<String, Integer> productsAndCorrespondingQuantities = orderDto
				.getProductsAndCorrespondingQuantities();

		final Location shippingLocation = locationService
				.getSingleShippingLocationForAllProducts(productsAndCorrespondingQuantities);

		for (final String productIdAsString : productsAndCorrespondingQuantities.keySet()) {
			final Integer productId = Integer.parseInt(productIdAsString);
			final Integer quantity = productsAndCorrespondingQuantities.get(productIdAsString);
			final Product product = productService.getProduct(productId).get();
			orderDetails.add(new OrderDetail(null, product, shippingLocation, quantity));
		}

		return orderDetails;
	}
}
