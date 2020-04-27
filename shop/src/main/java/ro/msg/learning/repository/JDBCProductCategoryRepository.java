package ro.msg.learning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.mapper.ProductCategoryMapper;

@Repository
@RequiredArgsConstructor
public class JDBCProductCategoryRepository {
	private static final String DELETE_PRODUCT_CATEGORY_SQL = "delete from product_category where id = ?";
	private static final String GET_SPECIFIC_PRODUCT_CATEGORY_SQL = "select * from product_category where id = ? ";
	private static final String GET_ALL_PRODUCT_CATEGORIES_SQL = "select * from product_category";
	private static final String CREATE_PRODUCT_CATEGORY_SQL = "insert into product_category(name, description) VALUES (?,?)";
	private static final String UPDATE_PRODUCT_CATEGORY_SQL = "update product_category set name = ?, description = ? where id = ?";
	
	private final JdbcTemplate jdbcTemplate;
	private final ProductCategoryMapper productCategoryMapper;
	
	@SneakyThrows
	public Optional<ProductCategory> findById(final Integer id) {
		final String sql = GET_SPECIFIC_PRODUCT_CATEGORY_SQL;

		final ProductCategory result = jdbcTemplate.queryForObject(sql, new Object[] { id }, productCategoryMapper);

		return Optional.of(result);
	}

	@SneakyThrows
	public Iterable<ProductCategory> findAll() {
		final String sql = GET_ALL_PRODUCT_CATEGORIES_SQL;

		final List<ProductCategory> result = jdbcTemplate.query(sql, productCategoryMapper);

		return result;
	}

	@SneakyThrows
	public ProductCategory save(final ProductCategory productCategory) {
		int noOfRowsAffected;
		
		if(productCategory.getId() == null) {
			noOfRowsAffected = createProductCategory(productCategory);
		}
		else {
			noOfRowsAffected = updateProductCategory(productCategory);
		}

		if (noOfRowsAffected > 0) {
			return productCategory;
		}

		return null;
	}
	
	@SneakyThrows
	public void deleteById(final Integer id) {
		jdbcTemplate.update(DELETE_PRODUCT_CATEGORY_SQL, new Object[] { id });
	}
	

	private int createProductCategory(final ProductCategory productCategory) {
		int noOfRowsAffected = jdbcTemplate.update(CREATE_PRODUCT_CATEGORY_SQL, productCategory.getCategoryName(),
				productCategory.getCategoryDescription());
		
		addIdToProductCategory(productCategory);
		
		return noOfRowsAffected;
	}

	private void addIdToProductCategory(final ProductCategory productCategory) {
		String sql = "select MAX(id) from product_category";
		Integer idOfNewlyCreatedProductCategory = jdbcTemplate.queryForObject(sql, Integer.class);
		productCategory.setId(idOfNewlyCreatedProductCategory);
	}
	
	private int updateProductCategory(final ProductCategory productCategory) {
		int noOfRowsAffected = jdbcTemplate.update(UPDATE_PRODUCT_CATEGORY_SQL, productCategory.getCategoryName(),
				productCategory.getCategoryDescription(), productCategory.getId());
		
		return noOfRowsAffected;
	}

}
