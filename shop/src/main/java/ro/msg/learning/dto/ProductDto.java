package ro.msg.learning.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	private Integer id;
	private String productName;
	private String productDescription;
	private BigDecimal price;
	private Double weight;
	private Integer categoryId;
	private Integer supplierId;
	private String imageUrl;
}
