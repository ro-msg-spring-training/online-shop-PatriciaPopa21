package ro.msg.learning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Identifiable {
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email_address", nullable = false, unique = true)
	private String emailAddress;

	//	@OneToMany(fetch = FetchType.LAZY)
	//	private List<Order> orders;
}
