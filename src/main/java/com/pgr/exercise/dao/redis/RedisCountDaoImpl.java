package com.pgr.exercise.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pgr.exercise.dao.ICountDao;

@Repository
public class RedisCountDaoImpl implements ICountDao {

	@Autowired
	protected RedisConfig config;
	
	private static final String COUNT = "count";

	@Override
	public long getCount() {
		Long value = config.redisTemplate().opsForValue().get(COUNT);
		return value != null ? value : 0L;
	}

	@Override
	public long incrementCount(long value) {
		return config.redisTemplate().opsForValue().increment(COUNT, value);
	}
}
