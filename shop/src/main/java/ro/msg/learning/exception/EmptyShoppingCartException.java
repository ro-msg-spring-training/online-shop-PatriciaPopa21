package ro.msg.learning.exception;

public class EmptyShoppingCartException extends RuntimeException {
	private static final String MESSAGE = "Your shopping cart is empty!";

	public EmptyShoppingCartException() {
		super(MESSAGE);
	}
}
