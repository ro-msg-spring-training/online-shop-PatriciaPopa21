package ro.msg.learning.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Product;
import ro.msg.learning.repository.JDBCProductCategoryRepository;
import ro.msg.learning.repository.SupplierRepository;

@Component
@RequiredArgsConstructor
public class ProductMapper implements RowMapper<Product> {
	private final JDBCProductCategoryRepository jdbcProductCategoryRepository;
	private final SupplierRepository supplierRepository;

	@Override
	public Product mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final Product product = new Product();

		product.setId(rs.getInt("id"));
		product.setCategory(jdbcProductCategoryRepository.findById(rs.getInt("category_id")).get());
		product.setImageUrl(rs.getString("image_url"));
		product.setPrice(rs.getBigDecimal("price"));
		product.setProductDescription(rs.getString("description"));
		product.setProductName(rs.getString("name"));
		product.setSupplier(supplierRepository.findById(rs.getInt("supplier_id")).get());
		product.setWeight(rs.getDouble("weight"));

		return product;
	}
}