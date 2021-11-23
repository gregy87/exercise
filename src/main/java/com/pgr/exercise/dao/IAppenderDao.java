package com.pgr.exercise.dao;

public interface IAppenderDao {

	/**
	 * Appends JSON content to existing resource
	 * 
	 * @param json provided JSON to be appended
	 */
	void append(String json);

}
