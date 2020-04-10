package ro.msg.learning.service.interfaces;

import java.util.Optional;

import ro.msg.learning.entity.Customer;

public interface CustomerService {
	Optional<Customer> getCustomerById(Integer customerId);

	Customer getCustomerByUsername(String username);
}
