package ro.msg.learning.service.interfaces;

import java.util.Collection;
import java.util.Optional;

import ro.msg.learning.entity.Product;

public interface ProductService {
	Product createProduct(Product product);
	Optional<Product> getProduct(Integer id);
	void updateProduct(Integer id, Product product);
	void deleteProduct(Integer id);
	Collection<Product> getAllProducts();
}
