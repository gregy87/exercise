package com.pgr.exercise.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ai.grakn.redismock.RedisServer;

/**
 * This is only attempt to do some more testing. The library mocking Redis, however, seems not to support all necessary features.
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class RequestControllerTestWithDB {
	
	private static RedisServer mockServer;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeAll
	public static void init() throws IOException {
		mockServer = RedisServer.newRedisServer(8089);		
		mockServer.start();
	}
	
	@AfterAll
	public static void cleanup() {
		mockServer.stop();
	}
	
	@Test
	@Disabled
	public void getCountSuccess() throws Exception {
		

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/count")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON),
				jsonPath("$.count").value(0));
	}
	
}
