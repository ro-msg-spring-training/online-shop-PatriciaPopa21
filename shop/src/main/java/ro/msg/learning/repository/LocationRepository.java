package ro.msg.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

}