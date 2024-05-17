package com.hong.demo.exceptions;

import lombok.Data;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDetails {

	private HttpStatus status;
	private Map<String, String> errorDetails = new HashMap<>();
 
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Map<String, String> getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(Map<String, String> errorDetails) {
		this.errorDetails = errorDetails;
	}

	public void setMessage(String message) {
		errorDetails.put("exception", message);
	}

	// public String getMessage() {
	// 	return message;
	// }
	// public void setMessage(String message) {
	// 	this.message = message;
	// }

}
