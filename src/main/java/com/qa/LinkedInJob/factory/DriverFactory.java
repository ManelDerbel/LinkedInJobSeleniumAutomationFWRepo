package com.qa.LinkedInJob.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.LinkedInJob.errors.AppErrors;
import com.qa.LinkedInJob.exceptions.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public OptionsManager optionsManager;

	public WebDriver initDriver(Properties prop) {
		
		optionsManager = new OptionsManager(prop);

		String browser = prop.getProperty("browser");
		System.out.println("Testcase executed on browser name: " + browser);

		switch (browser.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;

		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;

		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println(AppErrors.INVALID_BROWSER_MSG);
			throw new FrameworkException("===INVALID BROWSER===");
		}

		getDriver().manage().deleteAllCookies();
		
		if (!Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
			getDriver().manage().window().maximize();
		}

		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		prop = new Properties();

		try {
			FileInputStream file = new FileInputStream("src/test/resources/config/config.properties");

			try {
				prop.load(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return prop;
	}
}
