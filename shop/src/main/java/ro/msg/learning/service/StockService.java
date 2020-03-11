package ro.msg.learning.service;

public interface StockService {
	void updateStock(Integer location_id, Integer product_id, Integer quantityPurchased);
}
