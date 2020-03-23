package ro.msg.learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {
	@Query(value = "select loc.* from location loc " +
			"join stock stk on (loc.id = stk.location_id) " +
			"join product prod on (prod.id = stk.product_id) " +
			"where prod.id = ?1 and stk.quantity >= ?2 " +
			"order by loc.id asc", nativeQuery = true)
	List<Location> getAllAvailableShippingLocations(final Integer productId, final Integer quantity);

	@Query(value = "select * from location loc " +
			"join stock stk on (loc.id = stk.location_id) " +
			"join product prod on (prod.id = stk.product_id) " +
			"where prod.id = ?1 and stk.quantity >= ?2 " +
			"and stk.quantity = (select max(quantity) from stock where product_id = ?1) " +
			"and rownum <= 1 " +
			"order by loc.id asc",
			nativeQuery = true)
	Location getProductMostAbundantShippingLocation(final Integer productId, final Integer quantity);

}