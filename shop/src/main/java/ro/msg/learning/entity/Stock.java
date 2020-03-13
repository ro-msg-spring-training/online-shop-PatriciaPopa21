package ro.msg.learning.entity;

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
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "location", "product", "quantity"})
public class Stock extends Identifiable{
	@ManyToOne
	@JsonUnwrapped
	private Location location;

	@ManyToOne
	@JsonUnwrapped
	private Product product;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
}
