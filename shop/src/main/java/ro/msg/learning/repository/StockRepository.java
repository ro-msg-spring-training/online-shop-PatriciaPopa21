package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {

}