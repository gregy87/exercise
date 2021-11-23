package com.pgr.exercise.dao.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.Getter;

/**
 * Simple configuration for Redis connection. No encryption etc.
 * 
 *
 */
@Configuration
@Getter
public class RedisConfig {
	
	@Value("${redis.host}")
	protected String host;
	
	@Value("${redis.port}")
	protected int port;
	
	@Value("${redis.pwd}")
	protected String pwd;
	
	@Value("${redis.db}")
	protected int db;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	    redisStandaloneConfiguration.setHostName(host);
	    redisStandaloneConfiguration.setPort(port);
	    redisStandaloneConfiguration.setPassword(RedisPassword.of(pwd));
	    redisStandaloneConfiguration.setDatabase(db);
	   
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
	
	@Bean
	public RedisTemplate<String, Long> redisTemplate() {
	    RedisTemplate<String, Long> template = new RedisTemplate<>();
	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
	    template.setConnectionFactory(jedisConnectionFactory());
	    return template;
	}

}
