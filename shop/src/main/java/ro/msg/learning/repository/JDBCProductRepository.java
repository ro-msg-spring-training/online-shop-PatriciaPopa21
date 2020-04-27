package ro.msg.learning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ro.msg.learning.entity.Product;
import ro.msg.learning.mapper.ProductMapper;

@Repository
@RequiredArgsConstructor
public class JDBCProductRepository {
	private static final String DELETE_PRODUCT_SQL = "delete from product where id = ?";
	private static final String GET_SPECIFIC_PRODUCT_SQL = "select * from product where id = ? ";
	private static final String GET_ALL_PRODUCTS_SQL = "select * from product";
	private static final String CREATE_PRODUCT_SQL = "insert into product(description, image_url, name, price, weight, category_id, supplier_id) VALUES (?,?,?,?,?,?,?)";
	private static final String UPDATE_PRODUCT_CATEGORY_SQL = "update product set description = ?, image_url = ?, name = ?, price = ?, weight = ?, category_id = ?, supplier_id = ? where id = ?";
	
	private final JdbcTemplate jdbcTemplate;
	private final ProductMapper productMapper;
	
	@SneakyThrows
	public Optional<Product> findById(final Integer id) {
		final String sql = GET_SPECIFIC_PRODUCT_SQL;

		final Product result = jdbcTemplate.queryForObject(sql, new Object[] { id }, productMapper);

		return Optional.of(result);
	}

	@SneakyThrows
	public Iterable<Product> findAll() {
		final String sql = GET_ALL_PRODUCTS_SQL;

		final List<Product> result = jdbcTemplate.query(sql, productMapper);

		return result;
	}

	@SneakyThrows
	public Product save(final Product product) {
		int noOfRowsAffected;
		
		if(product.getId() == null) {
			noOfRowsAffected = createProductCategory(product);
		}
		else {
			noOfRowsAffected = updateProductCategory(product);
		}

		if (noOfRowsAffected > 0) {
			return product;
		}

		return null;
	}
	
	@SneakyThrows
	public void deleteById(final Integer id) {
		jdbcTemplate.update(DELETE_PRODUCT_SQL, new Object[] { id });
	}
	

	private int createProductCategory(final Product product) {
		int noOfRowsAffected = jdbcTemplate.update(CREATE_PRODUCT_SQL, product.getProductDescription(), product.getImageUrl(), 
				product.getProductName(), product.getPrice(), product.getWeight(), product.getCategory().getId(), 
				product.getSupplier().getId());
		
		addIdToProductCategory(product);
		
		return noOfRowsAffected;
	}

	private void addIdToProductCategory(final Product product) {
		String sql = "select MAX(id) from product";
		Integer idOfNewlyCreatedProductCategory = jdbcTemplate.queryForObject(sql, Integer.class);
		product.setId(idOfNewlyCreatedProductCategory);
	}
	
	private int updateProductCategory(final Product product) {
		int noOfRowsAffected = jdbcTemplate.update(UPDATE_PRODUCT_CATEGORY_SQL, product.getProductDescription(), product.getImageUrl(), 
				product.getProductName(), product.getPrice(), product.getWeight(), product.getCategory().getId(), 
				product.getSupplier().getId(), product.getId());
		
		return noOfRowsAffected;
	}

}
