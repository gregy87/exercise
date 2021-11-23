package com.pgr.exercise.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pgr.exercise.dao.IAppenderDao;
import com.pgr.exercise.dao.ICountDao;
import com.pgr.exercise.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RequestController {

	@Autowired
	protected ICountDao countDao;

	@Autowired
	protected IAppenderDao appenderDao;

	@GetMapping(path = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCount() {
		log.debug("Count requested.");
		long count = countDao.getCount();
		return JsonUtils.valueToJSON("count", count);
	}

	/**
	 * 
	 * @param body
	 * @return
	 */
	@PostMapping(path = "/track", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String appendContent(@RequestBody String body) {
		log.debug("track request; body: " + body);

		// This will also validate JSON
		/*
		 * For simplicity, if the searched value is present, but is not Long value,
		 * exception will be thrown, which is not always correct behavior, but it is
		 * intentionally ignored in this example.
		 */
		Long increment = JsonUtils.getLongValueFromJSON("count", body);

		Long newCount = null;
		if (increment != null) {
			newCount = countDao.increment(increment);
		}

		appenderDao.append(body);

		return newCount != null ? JsonUtils.valueToJSON("count", newCount) : null;
	}

}
