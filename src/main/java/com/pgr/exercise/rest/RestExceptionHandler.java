package com.pgr.exercise.rest;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pgr.exercise.exception.AppenderException;
import com.pgr.exercise.exception.InvalidJsonException;

/**
 * 
 * Custom exception handler
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles {@link com.pgr.exercise.exception.InvalidJsonException}
	 * 
	 * @param e       InvalidJsonException to be handled
	 * @param request
	 * @return Response entity with bad request with added message
	 * @throws IOException
	 */
	@ExceptionHandler({ InvalidJsonException.class })
	public ResponseEntity<ApiResponse> handleInvalidJsonException(Exception e, WebRequest request) throws IOException {
		ApiResponse response = new ApiResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setError(HttpStatus.BAD_REQUEST.toString());
		response.setMessage(e.getMessage());
		response.setPath(((ServletWebRequest) request).getRequest().getRequestURI().toString());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles {@link com.pgr.exercise.exception.AppenderException}
	 * 
	 * @param e       AppenderException to be handled
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler({ AppenderException.class })
	public ResponseEntity<ApiResponse> handleInvalidAppenderException(Exception e, WebRequest request)
			throws IOException {
		ApiResponse response = new ApiResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		response.setMessage(e.getMessage());
		response.setPath(((ServletWebRequest) request).getRequest().getRequestURI().toString());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
