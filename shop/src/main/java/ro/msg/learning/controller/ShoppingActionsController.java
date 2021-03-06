package ro.msg.learning.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Address;
import ro.msg.learning.entity.Customer;
import ro.msg.learning.entity.OrderDetailDto;
import ro.msg.learning.entity.Product;
import ro.msg.learning.service.interfaces.CustomerService;
import ro.msg.learning.service.interfaces.OrderService;
import ro.msg.learning.service.interfaces.ProductService;
import ro.msg.learning.service.interfaces.ShoppingService;

@Controller
@AllArgsConstructor
public class ShoppingActionsController {
	private final ProductService productService;
	private final ShoppingService shoppingService;
	private final CustomerService customerService;
	private final OrderService orderService;

	@GetMapping(value = "/homepage")
	public String homepage() {
		return "homepage";
	}

	@GetMapping(value = "/products/v2")
	public String getAllProducts(final Model model) {
		final List<Product> products = (List<Product>) productService.getAllProducts();
		model.addAttribute("products", products);
		return "products-browser";
	}

	@GetMapping(value = "/product/v2")
	public String getProduct(@RequestParam(name = "id") final Integer id,
			@RequestParam(name = "name") final String name, final Model model) {
		final Optional<Product> product = productService.getProduct(id);

		if (product.isPresent()) {
			model.addAttribute("product", product.get());
		}

		model.addAttribute("orderDetailDto", new OrderDetailDto(id, 1, name));

		return "product";
	}

	@GetMapping(value = "/shoppingcart")
	public String getShoppingCart(final Model model) {
		final List<OrderDetailDto> orderDetailDtos = shoppingService.getShoppingCart().getOrderDetailDtos();
		model.addAttribute("orderDetailDtos", orderDetailDtos);

		return "shoppingcart";
	}

	@PostMapping(value = "/shoppingcart")
	public String addToShoppingCart(final OrderDetailDto orderDetailDto) {
		shoppingService.addToShoppingCart(orderDetailDto);

		return "product-added";
	}

	@GetMapping(value = "/proceed/v2")
	public String proceedWithOrder(final Model model) {
		model.addAttribute("destinationAddress", new Address());

		return "fill-in-address";
	}

	@PostMapping(value = "/orders/v2")
	public String createOrder(final Address destinationAddress) {

		final OrderDto orderDto = createOrderDto(destinationAddress);

		orderService.createOrder(orderDto);

		shoppingService.clearShoppingCart();

		return "order-placed";
	}

	private OrderDto createOrderDto(final Address destinationAddress) {
		final OrderDto orderDto = shoppingService.getShoppingCart();
		orderDto.setAddress(destinationAddress);
		orderDto.setCreatedAt(LocalDateTime.now());
		final Customer customer = customerService.getCustomerByUsername(getCurrentLoggedInUsername());
		orderDto.setCustomerId(customer.getId());
		return orderDto;
	}

	private String getCurrentLoggedInUsername() {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}
}
