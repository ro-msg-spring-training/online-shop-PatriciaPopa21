package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Supplier;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

}
