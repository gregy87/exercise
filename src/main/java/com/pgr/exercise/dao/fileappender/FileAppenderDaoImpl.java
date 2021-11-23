package com.pgr.exercise.dao.fileappender;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pgr.exercise.dao.IAppenderDao;
import com.pgr.exercise.exception.AppenderException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileAppenderDaoImpl implements IAppenderDao {

	@Value("${file.output}")
	protected String fileName;
	
	@Override
	public void append(String json) {
		File file = new File(fileName);
		try {
			FileUtils.writeStringToFile(file, json, StandardCharsets.UTF_8, true);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new AppenderException(e);
		}
	}

}
