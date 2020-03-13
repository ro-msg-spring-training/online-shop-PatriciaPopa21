package ro.msg.learning.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ro.msg.learning.entity.Stock;
import ro.msg.learning.service.StockService;

@RestController
public class StockController {
	private final StockService stockService;

	public StockController(final StockService stockService) {
		this.stockService = stockService;
	}

	@GetMapping(path = "/stocks/{locationId}")
	public List<Stock> exportStock(@PathVariable("locationId") final Integer locationId, final HttpServletResponse response) throws IOException{
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv");
		return stockService.getAllStocksForLocation(locationId);
	}

	@PostMapping(path = "/stocks")
	@ResponseStatus(HttpStatus.CREATED)
	public void importStock(final HttpServletResponse response) throws IOException{
	}
}
