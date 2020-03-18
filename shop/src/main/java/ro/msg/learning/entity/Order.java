package ro.msg.learning.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private Location locationShippedFrom;

	@ManyToOne
	private Customer customer;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Embedded
	private Address address;
}
