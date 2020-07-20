package com.benz.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.benz.web.model.ErrorMessage;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> getResponse(DataNotFoundException ex)
	{
		ErrorMessage errorMessage=new ErrorMessage(404,ex.getMessage(),"www.benz.com");
		
		  return new ResponseEntity<ErrorMessage>(errorMessage,HttpStatus.NOT_FOUND);
	}
}
