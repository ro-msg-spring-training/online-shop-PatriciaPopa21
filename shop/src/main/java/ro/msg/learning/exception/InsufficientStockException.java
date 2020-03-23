package ro.msg.learning.exception;

public class InsufficientStockException extends RuntimeException {
	private static final String MESSAGE = "Your order couldn't be processed. Not enough products on stock.";

	public InsufficientStockException() {
		super(MESSAGE);
	}
}
