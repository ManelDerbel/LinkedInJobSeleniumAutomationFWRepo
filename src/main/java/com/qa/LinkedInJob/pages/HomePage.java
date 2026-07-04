package com.qa.LinkedInJob.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.qa.LinkedInJob.constants.AppConstants;
import com.qa.LinkedInJob.utils.ElementUtils;

public class HomePage {

	private final By shareBox = By.xpath("//div[contains(@class, 'share-box-feed-entry')]/div[2]/button");
	private final By searchBox = By.xpath("//input[@placeholder='Search']");

	private WebDriver driver;
	private ElementUtils elUtils;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		elUtils = new ElementUtils(driver);
	}

	public String getHomePageUrl() {
		String currentUrl = elUtils.waitForExactURL(AppConstants.SHORT_WAIT_5s, AppConstants.HOME_PAGE_URL);
		System.out.println("URL of Home Page is: " + currentUrl);

		return currentUrl;
	}

	public Boolean isShareBoxExist() {
		Boolean flag = elUtils.isElementDisplayed(shareBox);

		return flag;
	}

	public SearchPage clickSearchBox() {
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, searchBox).sendKeys(Keys.ENTER);

		String title = elUtils.waitForTitleContains(AppConstants.LONG_WAIT_10s,
				AppConstants.SEARCH_PAGE_TITLE_FRACTION);
		System.out.println("Title of Search Page is: " + title);

		return new SearchPage(driver);
	}
}
