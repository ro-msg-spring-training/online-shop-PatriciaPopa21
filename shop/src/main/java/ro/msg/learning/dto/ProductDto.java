package ro.msg.learning.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.entity.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	private Integer id;
	private String name;
	private String description;
	private BigDecimal price;
	private Double weight;
	private ProductCategory category;
	private Supplier supplier;
	private String imageUrl;
}
