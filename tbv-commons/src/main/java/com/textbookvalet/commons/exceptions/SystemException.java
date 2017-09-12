package com.textbookvalet.commons.exceptions;

public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SystemException(String message, Exception exception) {
		super(message);
		super.setStackTrace(exception.getStackTrace());
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException() {
	}
}
