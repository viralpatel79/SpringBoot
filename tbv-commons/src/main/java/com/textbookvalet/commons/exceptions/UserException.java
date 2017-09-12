package com.textbookvalet.commons.exceptions;

@SuppressWarnings("serial")
public class UserException extends RuntimeException {

	private int errorCode = -1;

	public UserException(String message, Exception exception) {
		super(message);
		super.setStackTrace(exception.getStackTrace());
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}