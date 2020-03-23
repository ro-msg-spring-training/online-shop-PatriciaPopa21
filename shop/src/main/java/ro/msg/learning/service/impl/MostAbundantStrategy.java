package ro.msg.learning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.entity.OrderDetailDto;
import ro.msg.learning.entity.Product;
import ro.msg.learning.repository.OrderDetailRepository;
import ro.msg.learning.service.interfaces.LocationService;
import ro.msg.learning.service.interfaces.OrderDetailService;
import ro.msg.learning.service.interfaces.ProductService;
import ro.msg.learning.service.interfaces.StockService;

@Service
public class MostAbundantStrategy extends OrderDetailService {

	private static final String MOST_ABUNDANT = "most_abundant";

	public MostAbundantStrategy(final LocationService locationService, final ProductService productService,
			final OrderDetailRepository orderDetailRepository, final StockService stockService) {
		this.locationService = locationService;
		this.productService = productService;
		this.orderDetailRepository = orderDetailRepository;
		this.stockService = stockService;
	}

	@Override
	public String getName() {
		return MOST_ABUNDANT;
	}

	@Override
	protected List<OrderDetail> generateOrderDetails(final OrderDto orderDto) {
		final List<OrderDetail> orderDetails = new ArrayList<>();

		final List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetailDtos();

		for (final OrderDetailDto orderDetailDto : orderDetailDtos) {
			final Integer productId = orderDetailDto.getProductId();
			final Integer quantity = orderDetailDto.getQuantity();
			final Location shippingLocation = locationService.getProductMostAbundantShippingLocation(productId,
					quantity);
			final Product product = productService.getProduct(productId).get();
			orderDetails.add(new OrderDetail(null, product, shippingLocation, quantity));
		}

		return orderDetails;
	}
}
