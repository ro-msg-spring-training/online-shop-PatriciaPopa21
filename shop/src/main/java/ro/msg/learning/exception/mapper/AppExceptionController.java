package ro.msg.learning.exception.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ro.msg.learning.exception.InexistentIdException;
import ro.msg.learning.exception.InsufficientStockException;
import ro.msg.learning.exception.SuitableShippingLocationNotFoundException;

@ControllerAdvice
public class AppExceptionController {
	@ExceptionHandler(value = { SuitableShippingLocationNotFoundException.class })
	public ResponseEntity<Object> exception(final SuitableShippingLocationNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { InsufficientStockException.class })
	public ResponseEntity<Object> exception(final InsufficientStockException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { InexistentIdException.class })
	public ResponseEntity<Object> exception(final InexistentIdException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}