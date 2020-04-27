package ro.msg.learning.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ro.msg.learning.dto.ProductDto;
import ro.msg.learning.entity.Product;
import ro.msg.learning.service.interfaces.ProductCategoryService;
import ro.msg.learning.service.interfaces.ProductService;
import ro.msg.learning.service.interfaces.SupplierService;

@RestController
public class ProductController {
	private final ProductService productService;
	private final ModelMapper modelMapper;

	public ProductController(final ProductService productService, final ModelMapper modelMapper,
			final ProductCategoryService productCategoryService, final SupplierService supplierService) {
		this.productService = productService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(path = "/products", produces = "application/json")
	public List<ProductDto> getAllProducts() {
		final List<Product> products = (List<Product>) productService.getAllProducts();
		return products.stream().map(product -> convertToDto(product)).collect(Collectors.toList());
	}

	@GetMapping(value = "/products/{id}")
	public ProductDto getProduct(@PathVariable("id") final Integer id) {
		final Optional<Product> product = productService.getProduct(id);
		return convertToDto(product.get());
	}

	@PostMapping(value = "/products")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDto createProduct(@RequestBody final ProductDto productDto) {
		final Product product = convertToEntity(productDto);
		final Product productCreated = productService.createProduct(product);
		return convertToDto(productCreated);
	}

	@PutMapping(value = "/products/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateProduct(@PathVariable("id") final Integer id, @RequestBody final ProductDto productDto) {
		final Product product = convertToEntity(productDto);
		productService.updateProduct(id, product);
	}

	@DeleteMapping(value = "/products/{id}")
	public void deleteProduct(@PathVariable("id") final Integer id) {
		productService.deleteProduct(id);
	}

	private ProductDto convertToDto(final Product product) {
		return modelMapper.map(product, ProductDto.class);
	}

	private Product convertToEntity(final ProductDto productDto) {
		return modelMapper.map(productDto, Product.class);
	}
}
