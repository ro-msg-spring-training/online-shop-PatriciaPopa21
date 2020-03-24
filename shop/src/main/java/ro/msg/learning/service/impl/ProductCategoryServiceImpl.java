package ro.msg.learning.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.repository.JDBCProductCategoryRepository;
import ro.msg.learning.service.interfaces.ProductCategoryService;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
	// private final ProductCategoryRepository productCategoryRepository;
	private final JDBCProductCategoryRepository jdbcProductCategoryRepository;

	@Override
	public Optional<ProductCategory> getProductCategory(final Integer id) {
		// return productCategoryRepository.findById(id);
		return jdbcProductCategoryRepository.findById(id);
	}

}
