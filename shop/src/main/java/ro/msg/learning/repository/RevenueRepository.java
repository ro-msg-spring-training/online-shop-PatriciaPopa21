package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Revenue;

@Repository
public interface RevenueRepository extends CrudRepository<Revenue, Integer> {

}
