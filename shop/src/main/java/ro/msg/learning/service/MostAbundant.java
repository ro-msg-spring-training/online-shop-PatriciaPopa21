package ro.msg.learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.entity.Product;
import ro.msg.learning.repository.OrderDetailRepository;

@Service
public class MostAbundant extends OrderDetailService{

	public MostAbundant(final LocationService locationService, final ProductService productService, final OrderDetailRepository orderDetailRepository, final StockService stockService) {
		this.locationService = locationService;
		this.productService = productService;
		this.orderDetailRepository = orderDetailRepository;
		this.stockService = stockService;
	}

	@Override
	public String getName() {
		return "most_abundant";
	}

	@Override
	protected List<OrderDetail> generateOrderDetails(final OrderDto orderDto) {
		final List<OrderDetail> orderDetails = new ArrayList<>();

		final Map<String, Integer> productsAndCorrespondingQuantities = orderDto.getProductsAndCorrespondingQuantities();

		for(final String productIdAsString : productsAndCorrespondingQuantities.keySet() ) {
			final Integer productId = Integer.parseInt(productIdAsString);
			final Integer quantity = productsAndCorrespondingQuantities.get(productIdAsString);
			final Location shippingLocation = locationService.getProductMostAbundantShippingLocation(productId, quantity);
			final Product product = productService.getProduct(productId).get();
			orderDetails.add(new OrderDetail(null, product, shippingLocation, quantity));
		}

		return orderDetails;
	}
}
