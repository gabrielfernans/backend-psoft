package com.psoft.project.exceptions;

import lombok.AllArgsConstructor;

//@AllArgsConstructor
public class ObjectFail {
	public String message;
	public String field;
	public Object parameter;
    
	public ObjectFail() {}
	
    public ObjectFail(String defaultMessage, String field2, Object rejectedValue) {
		this.message = defaultMessage;
		this.field = field2;
		this.parameter = rejectedValue;
	}
}