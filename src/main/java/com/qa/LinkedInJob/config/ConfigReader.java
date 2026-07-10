package com.qa.LinkedInJob.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties prop;
	public static final String TEST_PATH_PROPERTIES_FILE = "/src/test/resources/config/";

	public static Properties initProp(String fileName) {
		String filePath = System.getProperty("user.dir") + TEST_PATH_PROPERTIES_FILE + fileName + ".properties";
		prop = new Properties();
		
		try(FileInputStream file = new FileInputStream(filePath)) {
			prop.load(file);
		} catch (IOException e) {
			throw new RuntimeException("Unable to load Properties File: " + fileName, e);
		}
		return prop;
	}

	public static String getProperty(String fileName, String key) {
		prop = initProp(fileName);
		return prop.getProperty(key);
	}
}