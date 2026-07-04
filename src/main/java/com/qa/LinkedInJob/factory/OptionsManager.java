package com.qa.LinkedInJob.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
			co.addArguments("--headless");
			co.addArguments("--window-size=1920,1080");
			//Desktop web application: 1920,1080
			//windowWidth=1920,	windowHeight=1080
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito", "false"))) {
			co.addArguments("--incognito");
		}
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
			fo.addArguments("--headless");
			fo.addArguments("--window-size=1920,1080");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito", "false"))) {
			fo.addArguments("--incognito");
		}
		return fo;
	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
			eo.addArguments("--headless");
			eo.addArguments("--window-size=1920,1080");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito", "false"))) {
			eo.addArguments("--incognito");
		}
		return eo;
	}
}
