package ro.msg.learning.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Identifiable{
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "weight")
	private Double weight;

	@ManyToOne
	private ProductCategory category;

	@ManyToOne
	private Supplier supplier;

	@Column(name = "image_url")
	private String imageUrl;

	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<Stock> stocks;
	//
	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<OrderDetail> orderDetail;
}
