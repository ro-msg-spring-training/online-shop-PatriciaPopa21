package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}