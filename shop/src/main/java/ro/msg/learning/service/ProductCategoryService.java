package ro.msg.learning.service;

import java.util.Optional;

import ro.msg.learning.entity.ProductCategory;

public interface ProductCategoryService {
	Optional<ProductCategory> getProductCategory(Integer id);
}
