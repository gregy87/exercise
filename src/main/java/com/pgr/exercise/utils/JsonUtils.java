package com.pgr.exercise.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.pgr.exercise.exception.InvalidJsonException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for operations with JSON
 *
 */
@Slf4j
public class JsonUtils {

	private JsonUtils() {
	}

	/**
	 * Validates string input, if it is JSON.
	 * 
	 * (Most likely this method is not entirely bulletproof, but was chosen for
	 * simplicity)
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isValidJSON(String input) {
		try {
			return JsonParser.parseString(input) != null;
		} catch (JsonParseException e) {
			log.warn(input + "is invalid JSON");
			return false;
		}
	}

	/**
	 * Obtains Long value from provided JSON string.
	 * 
	 * @param key
	 * @param json
	 * @return
	 */
	public static Long getLongValueFromJSON(@NonNull String key, @NonNull String json) {
		try {
			JsonElement element = JsonParser.parseString(json);
			JsonObject object = element.getAsJsonObject();
			JsonElement valueElement = object.get(key);

			if (valueElement != null && !(valueElement instanceof JsonNull)) {
				log.debug("Value of " + key + ": " + valueElement.toString());
				try {
					double doubleValue = valueElement.getAsDouble();
					if ((double) (long) doubleValue != doubleValue) {
						throw new NumberFormatException();
					}
					return valueElement.getAsLong();
				} catch (NumberFormatException | ClassCastException | IllegalStateException e) {
					log.error(key + " is present, but the value is invalid. Value: " + valueElement.toString());
					throw new InvalidJsonException("Invalid value in data", e);
				}
			} else {
				if (valueElement instanceof JsonNull) {
					log.error(key + "is present, but the value is null");
					throw new InvalidJsonException("Invalid (null) value in data");
				}
				log.debug("Key " + key + " is not present");
				return null;
			}
		} catch (JsonParseException | IllegalStateException e) {
			log.error(json + " is invalid JSON");
			throw new InvalidJsonException("Invalid JSON provided", e);
		}
	}

	/**
	 * Method converting object to JSON string
	 * 
	 * @param key   - key for the value
	 * @param value - object to be converted to value
	 * @return string representation of key/value pair
	 */
	public static String valueToJSON(@NonNull String key, Object value) {

		JsonObject obj = new JsonObject();

		if (value instanceof String || value == null) {
			obj.addProperty(key, (String) value);
		} else if (value instanceof Boolean) {
			obj.addProperty(key, (boolean) value);
		} else if (value instanceof Number) {
			obj.addProperty(key, (Number) value);
		} else if (value instanceof Character) {
			obj.addProperty(key, (Character) value);
		} else if (value instanceof JsonElement) {
			obj.add(key, (JsonElement) value);
		} else {
			throw new IllegalArgumentException("Invalid object passed for conversion: " + value.getClass());
		}

		return obj.toString();
	}

}
