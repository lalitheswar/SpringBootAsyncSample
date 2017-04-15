package com.al.app.dto;

/**
 * @author lalith
 *
 */
public class ProcessingStatus {
	private int status;
	private String message;
	
	public ProcessingStatus() {
		status = -1;
		message = "";
	}

	public ProcessingStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
