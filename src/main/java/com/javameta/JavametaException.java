package com.javameta;

public class JavametaException extends RuntimeException {
	private static final long serialVersionUID = -6448531730347863278L;

	public JavametaException() {
		super();
	}

	public JavametaException(String message) {
		super(message);
	}

	public JavametaException(String message, Throwable cause) {
		super(message, cause);
	}

	public JavametaException(Throwable cause) {
		super(cause);
	}
}
