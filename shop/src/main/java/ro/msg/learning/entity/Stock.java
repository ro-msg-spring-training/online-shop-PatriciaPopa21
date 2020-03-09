package ro.msg.learning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends Identifiable{
	@ManyToOne
	private Product product;

	@ManyToOne
	private Location location;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;
}
