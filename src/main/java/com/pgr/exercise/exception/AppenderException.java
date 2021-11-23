package com.pgr.exercise.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppenderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AppenderException(Throwable t) {
		super(t);
	}
}
