package com.pgr.exercise.rest;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RestConfiguration.TIME_FORMAT)
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String path;
	private String message;

}
