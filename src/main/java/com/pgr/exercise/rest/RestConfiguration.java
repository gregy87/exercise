package com.pgr.exercise.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

@Configuration
public class RestConfiguration {

	public static final String TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

	/**
	 * Overrides default error attributes
	 * 
	 * @return
	 */
	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_FORMAT);

				errorAttributes.put("timestamp", dtf.format(LocalDateTime.now()));

				return errorAttributes;
			}

		};
	}
}
