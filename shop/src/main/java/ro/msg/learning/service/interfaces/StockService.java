package ro.msg.learning.service.interfaces;

import java.util.List;

import ro.msg.learning.entity.Stock;

public interface StockService {
	void updateStock(Integer location_id, Integer product_id, Integer quantityPurchased);
	List<Stock> getAllStocksForLocation(Integer location_id);
	Stock getStockByLocationAndProduct(Integer productId, Integer locationId);
}
