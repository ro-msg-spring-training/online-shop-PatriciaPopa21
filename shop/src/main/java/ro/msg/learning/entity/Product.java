package ro.msg.learning.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "productName", "productDescription", "price", "weight", "category", "supplier", "imageUrl"})

public class Product extends Identifiable{
	@Column(name = "name", nullable = false)
	private String productName;

	@Column(name = "description")
	private String productDescription;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "weight")
	private Double weight;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JsonUnwrapped
	private ProductCategory category;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JsonUnwrapped
	private Supplier supplier;

	@Column(name = "image_url")
	private String imageUrl;
}
