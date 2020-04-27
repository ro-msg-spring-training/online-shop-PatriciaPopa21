package ro.msg.learning.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Product;
import ro.msg.learning.repository.ProductRepository;
import ro.msg.learning.service.interfaces.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	// private final JDBCProductRepository jdbcProductRepository;

	@Override
	public Product createProduct(final Product product) {
		return productRepository.save(product);
		// return jdbcProductRepository.save(product);
	}

	@Override
	public Optional<Product> getProduct(final Integer id) {
		return productRepository.findById(id);
		// return jdbcProductRepository.findById(id);
	}

	@Override
	public void updateProduct(final Integer id, final Product product) {
		product.setId(id);
		productRepository.save(product);
		// jdbcProductRepository.save(product);
	}

	@Override
	public void deleteProduct(final Integer id) {
		productRepository.deleteById(id);
		// jdbcProductRepository.deleteById(id);

	}

	@Override
	public Collection<Product> getAllProducts() {
		return (Collection<Product>) productRepository.findAll();
		// return (Collection<Product>) jdbcProductRepository.findAll();
	}
}
