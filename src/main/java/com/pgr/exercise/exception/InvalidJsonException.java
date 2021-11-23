package com.pgr.exercise.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidJsonException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidJsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidJsonException(String message) {
		super(message);
	}

}
