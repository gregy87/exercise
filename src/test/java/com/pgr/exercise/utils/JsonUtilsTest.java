package com.pgr.exercise.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.pgr.exercise.exception.InvalidJsonException;

public class JsonUtilsTest {

	String[] INVALID_JSONS = new String[] { "{\"a\":\"b\":}", "{\"a\":}", "{\"a\":null" };

	@Test
	void testValidJsonInput() {
		String[] jsons = new String[] { "{}", "{\"a\":\"b\"}", "{\"a\":25}", "{\"a\":null}" };
		Stream.of(jsons).forEach(json -> assertTrue(JsonUtils.isValidJSON(json)));
	}

	@Test
	void testInvalidJsonInput() {
		Stream.of(INVALID_JSONS).forEach(json -> assertTrue(!JsonUtils.isValidJSON(json)));
	}

	@Test
	void getLongValueFromInvalidJson() {
		Stream.of(INVALID_JSONS).forEach(json -> {
			InvalidJsonException e = assertThrows(InvalidJsonException.class, () -> {
				JsonUtils.getLongValueFromJSON("", json);
			});
			assertEquals("Invalid JSON provided", e.getMessage());
		});
	}

	@Test
	void getLongValueFromValidJson() {

		// Successful searches
		String[] jsons = new String[] { "{\"value\":25}", "{\"value\":\"25\"}" };
		Stream.of(jsons).forEach(json -> assertEquals(25L, JsonUtils.getLongValueFromJSON("value", json)));

		// Handling exceptions
		Map<String, String> map = new HashMap<>() {

			private static final long serialVersionUID = 1L;

			{
				put("{\"value\":null}", "Invalid (null) value in data");
				put("{\"value\":25.5}", "Invalid value in data");
				put("{\"value\":\"text\"}", "Invalid value in data");

			}
		};

		map.forEach((key, value) -> {
			InvalidJsonException e = assertThrows(InvalidJsonException.class,
					() -> JsonUtils.getLongValueFromJSON("value", key));
			assertEquals(value, e.getMessage());
		});

		// Returning null
		String json = "{\"valueTmp\":\"text\"}";
		assertNull(JsonUtils.getLongValueFromJSON("value", json));

	}

	@Test
	void getLongValueNullCheck() {
		assertAll(() -> assertThrows(NullPointerException.class, () -> JsonUtils.getLongValueFromJSON(null, "")),
				() -> assertThrows(NullPointerException.class, () -> JsonUtils.getLongValueFromJSON("", null)));
	}

	@Test
	void valueToJSON() {
		
		assertThrows(NullPointerException.class, () -> JsonUtils.valueToJSON(null, ""));		
		assertThrows(IllegalArgumentException.class, () -> JsonUtils.valueToJSON("key", new Object()));
		
		// For simplicity not testing JsonElement
		Map<String, Object> map = new HashMap<>() {
			private static final long serialVersionUID = 1L;
			{
				put("{\"value\":null}", null);
				put("{\"value\":\"stringVal\"}", "stringVal");
				put("{\"value\":30}", 30);
				put("{\"value\":30}", 30L);
				put("{\"value\":30.1}", 30.1D);
				put("{\"value\":30.2}", 30.2F);
				put("{\"value\":\"T\"}", 'T');
				put("{\"value\":true}", true);
				put("{\"value\":false}", false);
			}
		};

		map.forEach((key, value) -> {
			String json = JsonUtils.valueToJSON("value", value);
			assertEquals(key, json);
		});		
		
	}

}
