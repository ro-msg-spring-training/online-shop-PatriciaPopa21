package ro.msg.learning.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "revenue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Revenue extends Identifiable{
	@ManyToOne(cascade = {CascadeType.ALL})
	private Location location;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "sum", nullable = false)
	private BigDecimal sum;
}
