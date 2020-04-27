package ro.msg.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	@Query(value = "select * from customer where username = ?1", nativeQuery = true)
	Customer findByUsername(String username);

}