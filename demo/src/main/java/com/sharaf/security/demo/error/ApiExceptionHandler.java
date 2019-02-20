package com.sharaf.security.demo.error;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ApiBaseException.class)
	public ResponseEntity<ErrorDetails> handleApiExceptions(ApiBaseException ex, WebRequest request){
		ErrorDetails details = new ErrorDetails(ex.getStatusCode().value(),ex.getMessage());
		return new ResponseEntity<>(details,ex.getStatusCode());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ValiditionError validitionError = new ValiditionError();
		validitionError.setUrl(request.getDescription(false));
		List<FieldError> filederror = ex.getBindingResult().getFieldErrors();
		for(FieldError f:filederror) {
			validitionError.addErrors(f.getDefaultMessage());	
		}
		return new ResponseEntity<>(validitionError,HttpStatus.BAD_REQUEST);
	}

}
