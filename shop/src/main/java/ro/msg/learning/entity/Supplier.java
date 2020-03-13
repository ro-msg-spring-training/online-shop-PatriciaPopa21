package ro.msg.learning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends Identifiable{
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<Product> products;
}
