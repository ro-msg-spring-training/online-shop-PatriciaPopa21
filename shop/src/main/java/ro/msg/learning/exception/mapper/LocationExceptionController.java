package ro.msg.learning.exception.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ro.msg.learning.exception.SuitableShippingLocationNotFoundException;

@ControllerAdvice
public class LocationExceptionController {
	@ExceptionHandler(value = SuitableShippingLocationNotFoundException.class)
	public ResponseEntity<Object> exception(final SuitableShippingLocationNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}