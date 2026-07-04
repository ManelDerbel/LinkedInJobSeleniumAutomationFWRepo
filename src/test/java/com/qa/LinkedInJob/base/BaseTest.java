package com.qa.LinkedInJob.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa.LinkedInJob.factory.DriverFactory;
import com.qa.LinkedInJob.pages.HomePage;
import com.qa.LinkedInJob.pages.JobsSearchPage;
import com.qa.LinkedInJob.pages.LoginPage;
import com.qa.LinkedInJob.pages.SearchPage;

public class BaseTest {

	public WebDriver driver;
	public DriverFactory df;
	public Properties prop;
	public LoginPage loginPage;
	public HomePage homePage;
	public SearchPage searchPage;
	public JobsSearchPage jobsSearchPage;

	@Parameters({ "browser" })
	@BeforeTest
	public void setup(@Optional ("chrome") String browserName) {
		df = new DriverFactory();
		prop = df.initProp();

		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}

		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
	}

	@AfterTest
	public void tearDown() {
		// driver.quit();
	}
}
