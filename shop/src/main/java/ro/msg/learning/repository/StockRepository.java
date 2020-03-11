package ro.msg.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.msg.learning.entity.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {
	@Query(value = "select * from stock where location_id = ?1 and product_id = ?2",
			nativeQuery = true)
	Stock getStockByLocationAndProduct(final Integer location_id, final Integer product_id);
}