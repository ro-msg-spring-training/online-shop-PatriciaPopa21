package ro.msg.learning.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ro.msg.learning.entity.Revenue;

@Repository
public interface RevenueRepository extends CrudRepository<Revenue, Integer> {
	@Query(value = "select orddet.location_id AS locationId, SUM(orddet.quantity*prod.price) AS profit "
			+ "from order_detail orddet join orderr ord on (ord.id = orddet.order_id) "
			+ "join product prod on (prod.id = orddet.product_id) where ord.created_at like :formattedDate "
			+ "group by orddet.location_id", nativeQuery = true)
	List<ProfitPerLocation> getRevenueInfoForDate(String formattedDate);

	@Query(value = "select * from revenue where date like :formattedDate", nativeQuery = true)
	List<Revenue> getRevenuesForDate(String formattedDate);

	@JsonPropertyOrder({ "locationId", "profit" })
	public interface ProfitPerLocation {

		Integer getLocationId();

		BigDecimal getProfit();
	}
}
