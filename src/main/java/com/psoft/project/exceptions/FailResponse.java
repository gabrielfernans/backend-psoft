package com.psoft.project.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;

//@AllArgsConstructor
public class FailResponse {
	public String message;
    public int code;
    public String status;
    public String objectName;
    public List<ObjectFail> errors;
    
    public FailResponse() {}
    public FailResponse(String message, int code, String status, String objectName,
			List<ObjectFail> errors) {
    	this.message = message;
		this.code = code;
		this.status = status;
		this.objectName = objectName;
		this.errors = errors;
	}
    
}