package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Product;

@Repository
public interface ProductTypeRepository extends CrudRepository<Product, Integer> {

}