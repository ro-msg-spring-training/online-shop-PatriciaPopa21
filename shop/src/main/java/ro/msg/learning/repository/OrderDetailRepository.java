package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

}