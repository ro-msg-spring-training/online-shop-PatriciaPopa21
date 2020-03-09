package ro.msg.learning.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Identifiable {
	@ManyToOne
	private Location shippedFrom;

	@ManyToOne
	private Customer customer;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Embedded
	private Address address;

	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<OrderDetail> orderDetails;
}
