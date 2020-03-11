package ro.msg.learning.service;

import org.springframework.stereotype.Service;

import ro.msg.learning.entity.Stock;
import ro.msg.learning.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
	private final StockRepository stockRepository;

	public StockServiceImpl(final StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Override
	public void updateStock(final Integer location_id, final Integer product_id, final Integer quantityPurchased) {
		final Stock retrievedStock = stockRepository.getStockByLocationAndProduct(location_id, product_id);
		retrievedStock.setQuantity(retrievedStock.getQuantity() - quantityPurchased);
		stockRepository.save(retrievedStock);
	}
}
