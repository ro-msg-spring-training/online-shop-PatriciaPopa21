package ro.msg.learning.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "name", "address"})
public class Location extends Identifiable {
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Embedded
	@JsonUnwrapped
	private Address address;

	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<Stock> stocks;
	//
	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<Order> orders;
	//
	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<Revenue> revenues;
}
