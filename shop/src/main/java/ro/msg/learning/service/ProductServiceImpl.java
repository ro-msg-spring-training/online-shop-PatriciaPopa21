package ro.msg.learning.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ro.msg.learning.entity.Product;
import ro.msg.learning.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product createProduct(final Product product) {
		return productRepository.save(product);
	}

	@Override
	public Optional<Product> getProduct(final Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public void updateProduct(final Integer id, final Product product) {
		productRepository.save(product);
	}

	@Override
	public void deleteProduct(final Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public Collection<Product> getAllProducts() {
		return (Collection<Product>) productRepository.findAll();
	}
}
