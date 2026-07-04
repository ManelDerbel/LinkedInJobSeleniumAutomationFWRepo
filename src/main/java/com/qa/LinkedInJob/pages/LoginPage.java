package com.qa.LinkedInJob.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.LinkedInJob.constants.AppConstants;
import com.qa.LinkedInJob.utils.ElementUtils;

public class LoginPage {

	private final By email = By.xpath("(//input[@type='email'])[2]"); //(//input[@type='email'])[2] //input[@type='email']
	private final By password = By.xpath("(//input[@type='password'])[2]"); //(//input[@type='password'])[2] //input[@type='password']
	private final By loginBtn = By.xpath("(//button[@type='button'])[8]"); //(//button[@type='button'])[8] //button[contains(text(), 'Sign in')]

	private WebDriver driver;
	private ElementUtils elUtils;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elUtils = new ElementUtils(driver);
	}

	public String getLoginPageTitle() {
		String title = elUtils.waitForExactTitle(AppConstants.LONG_WAIT_10s, AppConstants.LOGIN_PAGE_TITLE);
		System.out.println("Title of Login Page is: " + title);

		return title;
	}

	public String getLoginPageUrl() {
		String currentUrl = elUtils.waitForUrlContains(AppConstants.SHORT_WAIT_5s,
				AppConstants.LOGIN_PAGE_URL_FRACTION);
		System.out.println("URL of Login Page is: " + currentUrl);

		return currentUrl;
	}

	public HomePage doLogin(String uname, String pass) {
		elUtils.waitForElementVisibility(AppConstants.SHORT_WAIT_5s, email).sendKeys(uname);
		elUtils.waitForElementVisibility(AppConstants.SHORT_WAIT_5s, password).sendKeys(pass);

		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, loginBtn).click();

		String title = elUtils.waitForTitleContains(AppConstants.LONG_WAIT_10s, AppConstants.HOME_PAGE_TITLE_FRACTION);
		System.out.println("Title of Home Page is: " + title);

		return new HomePage(driver);
	}
}
