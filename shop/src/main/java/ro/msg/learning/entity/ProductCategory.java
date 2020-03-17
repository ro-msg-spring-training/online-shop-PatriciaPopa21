package ro.msg.learning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "categoryName", "categoryDescription"})
public class ProductCategory extends Identifiable{
	@Column(name = "name", nullable = false)
	private String categoryName;

	@Column(name = "description")
	private String categoryDescription;
}
