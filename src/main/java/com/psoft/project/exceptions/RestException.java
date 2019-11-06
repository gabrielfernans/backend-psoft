package com.psoft.project.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		 List<ObjectError> errors = getErrors(ex);
	     ErrorResponse errorResponse = getErrorResponse(ex, status, errors);
	     return new ResponseEntity<>(errorResponse, status);
	 }
	 
	 @ExceptionHandler(InvalidDateException.class)
	 public ResponseEntity<Object> handleNotFoundException(InvalidDateException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		 	
		    return new ResponseEntity<>(ex.getMessage(), status);
		  }
	
	private List<ObjectError> getErrors(MethodArgumentNotValidException ex){
		return ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new ObjectError(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
				.collect(Collectors.toList());
	}
	
	private ErrorResponse getErrorResponse(MethodArgumentNotValidException ex, HttpStatus status, List<ObjectError> errors) {
	        return new ErrorResponse("Requisição possui campos inválidos", status.value(),
	                status.getReasonPhrase(), ex.getBindingResult().getObjectName(), errors);
	}
	
	
	
	@AllArgsConstructor
	public class ErrorResponse {
		public String message;
	    public int code;
	    public String status;
	    public String objectName;
	    public List<ObjectError> errors;
	    public ErrorResponse(String message, int code, String status, String objectName,
				List<ObjectError> errors) {
	    	this.message = message;
			this.code = code;
			this.status = status;
			this.objectName = objectName;
			this.errors = errors;
		}
	    
	}
	
	@AllArgsConstructor
	public class ObjectError {
		public String message;
		public String field;
		public Object parameter;
	    
	    public ObjectError(String defaultMessage, String field2, Object rejectedValue) {
			this.message = defaultMessage;
			this.field = field2;
			this.parameter = rejectedValue;
		}
	}
}
