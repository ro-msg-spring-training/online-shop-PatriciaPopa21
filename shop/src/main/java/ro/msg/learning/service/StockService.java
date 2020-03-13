package ro.msg.learning.service;

import java.util.List;

import ro.msg.learning.entity.Stock;

public interface StockService {
	void updateStock(Integer location_id, Integer product_id, Integer quantityPurchased);
	List<Stock> getAllStocksForLocation(Integer location_id);
}
