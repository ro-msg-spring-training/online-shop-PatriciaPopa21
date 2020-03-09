package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}