package com.psoft.project.exceptions;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;

@RestControllerAdvice
public class RestException extends ResponseEntityExceptionHandler{

	 @Override
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		 List<ObjectFail> errors = getErrors(ex);
		 FailResponse errorResponse = getErrorResponse(ex, status, errors);
	     return new ResponseEntity<>(errorResponse, status);
	 }
	 
	 @ExceptionHandler(InvalidDateException.class)
	 public ResponseEntity<Object> handleNotFoundException(InvalidDateException ex) {
		 FailResponse errorResponse = getErrorResponse(ex,  HttpStatus.BAD_REQUEST, new ArrayList<ObjectFail>());
		 return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	 }
	 
	
	private List<ObjectFail> getErrors(MethodArgumentNotValidException ex){
		return ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new ObjectFail(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
				.collect(Collectors.toList());
	}
	
	private FailResponse getErrorResponse(MethodArgumentNotValidException ex, HttpStatus status, List<ObjectFail> errors) {
	        return new FailResponse("Requisição possui campos inválidos", status.value(),
	                status.getReasonPhrase(), ex.getBindingResult().getObjectName(), errors);
	}
	
	private FailResponse getErrorResponse(InvalidDateException ex, HttpStatus status, List<ObjectFail> errors) {
        return new FailResponse("Requisição possui campos inválidos", status.value(),
                status.getReasonPhrase(), ex.getMessage(), errors);
	}
}
