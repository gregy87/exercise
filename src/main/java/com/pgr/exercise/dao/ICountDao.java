package com.pgr.exercise.dao;

public interface ICountDao {

	/**
	 * Retrieves count value
	 * 
	 * @return count value or 0, if count value is not available
	 */
	long getCount();

	/**
	 * Increments count value. If value is not present, it is set to the passed value
	 * 
	 * @param value
	 * @return updated value
	 */
	long incrementCount(long value);
	
}
