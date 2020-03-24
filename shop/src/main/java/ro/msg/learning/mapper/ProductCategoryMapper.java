package ro.msg.learning.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ro.msg.learning.entity.ProductCategory;

@Component
public class ProductCategoryMapper implements RowMapper<ProductCategory> {

	@Override
	public ProductCategory mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final ProductCategory pc = new ProductCategory();

		pc.setId(rs.getInt("id"));
		pc.setCategoryName(rs.getString("name"));
		pc.setCategoryDescription(rs.getString("description"));

		return pc;
	}
}