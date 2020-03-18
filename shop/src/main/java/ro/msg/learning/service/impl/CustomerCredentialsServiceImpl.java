package ro.msg.learning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Customer;
import ro.msg.learning.entity.CustomerCredentials;
import ro.msg.learning.repository.CustomerRepository;
import ro.msg.learning.service.CustomerCredentialsService;

@Service
@RequiredArgsConstructor
public class CustomerCredentialsServiceImpl implements CustomerCredentialsService {
	private final CustomerRepository customerRepository;

	@Override
	public List<CustomerCredentials> getCredentialsForAllUsers() {
		final List<Customer> allCustomers = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());

		final List<CustomerCredentials> allUserCredentials = new ArrayList<>();
		allCustomers.forEach(customer -> allUserCredentials.add(new CustomerCredentials(customer.getUsername(), customer.getPassword())));

		return allUserCredentials;
	}

}
