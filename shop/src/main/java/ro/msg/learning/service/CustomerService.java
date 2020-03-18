package ro.msg.learning.service;

import java.util.Optional;

import ro.msg.learning.entity.Customer;

public interface CustomerService {
	Optional<Customer> getCustomer(Integer customerId);
}
