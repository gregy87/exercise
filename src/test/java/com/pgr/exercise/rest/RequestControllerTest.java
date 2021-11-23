package com.pgr.exercise.rest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pgr.exercise.dao.IAppenderDao;
import com.pgr.exercise.dao.ICountDao;

@WebMvcTest(RequestController.class)
public class RequestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	protected ICountDao countDao;

	@MockBean
	protected IAppenderDao appenderDao;

	@Test
	public void getCountSuccess() throws Exception {
		Mockito.when(countDao.getCount()).thenReturn(5L);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/count")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON),
				jsonPath("$.count").value(5));
	}

	@Test
	public void getCountBadRequest() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/count")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andExpect(status().isMethodNotAllowed());

	}

	@Test
	public void testBadRequest() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/countTypo")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andExpect(status().isNotFound());
	}

	@Test
	public void postTrackWithCountSuccess() throws Exception {
		Mockito.when(countDao.increment(5L)).thenReturn(10L);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/track")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"count\":5}");
		mockMvc.perform(builder).andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON), jsonPath("$.count", is(10)));
	}
	
	@Test
	public void postTrackWithoutCountSuccess() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/track")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"countTypo\":5}");
		mockMvc.perform(builder).andExpectAll(status().isOk(), content().string(""));
	}
	
	
	@Test
	public void postTrackWithInvalidJson() throws Exception {
		// This whole method could be enhanced with checking of the content of the response
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/track")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"count\":5");
		mockMvc.perform(builder).andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_JSON));
		
		builder = MockMvcRequestBuilders.post("/track")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"count\":null}");
		mockMvc.perform(builder).andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_JSON));
		
		builder = MockMvcRequestBuilders.post("/track")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"count\":\"invalid\"}");
		mockMvc.perform(builder).andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_JSON));
		
		builder = MockMvcRequestBuilders.post("/track")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"count\":5.1}");
		mockMvc.perform(builder).andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_JSON));
	}

}
