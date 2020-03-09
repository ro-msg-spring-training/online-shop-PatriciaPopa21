package ro.msg.learning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends Identifiable{
	@ManyToOne
	private Order order;

	@ManyToOne
	private Product product;

	@Column(name = "quantity_id", nullable = false)
	private Integer quantity;
}
