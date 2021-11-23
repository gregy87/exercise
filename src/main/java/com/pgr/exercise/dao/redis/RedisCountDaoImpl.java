package com.pgr.exercise.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pgr.exercise.dao.ICountDao;

@Repository
public class RedisCountDaoImpl implements ICountDao {

	@Autowired
	protected RedisConfig config;

	@Override
	public long getCount() {
		Long value = config.redisTemplate().opsForValue().get("count");
		return value != null ? value : 0L;
	}

	@Override
	public long increment(long value) {
		return config.redisTemplate().opsForValue().increment("count", value);
	}
}
