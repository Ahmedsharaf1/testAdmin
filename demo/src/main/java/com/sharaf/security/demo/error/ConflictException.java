package com.sharaf.security.demo.error;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiBaseException{

	public ConflictException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatusCode() {
		// TODO Auto-generated method stub
		return HttpStatus.CONFLICT;
	}

}
