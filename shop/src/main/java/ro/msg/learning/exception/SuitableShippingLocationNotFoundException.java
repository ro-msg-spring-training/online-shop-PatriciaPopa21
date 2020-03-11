package ro.msg.learning.exception;

public class SuitableShippingLocationNotFoundException extends RuntimeException {

	private static final String NOT_ENOUGH_PRODUCTS = "Your order couldn't be processed. There is currently not enough of the products you have requested, in any of our locations";

	public SuitableShippingLocationNotFoundException() {
		super(NOT_ENOUGH_PRODUCTS);
	}

}
