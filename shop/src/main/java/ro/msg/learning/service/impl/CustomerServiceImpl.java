package ro.msg.learning.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Customer;
import ro.msg.learning.repository.CustomerRepository;
import ro.msg.learning.service.interfaces.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;

	@Override
	public Optional<Customer> getCustomer(final Integer customerId) {
		return customerRepository.findById(customerId);
	}
}
