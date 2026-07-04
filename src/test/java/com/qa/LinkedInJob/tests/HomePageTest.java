package com.qa.LinkedInJob.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.LinkedInJob.base.BaseTest;
import com.qa.LinkedInJob.constants.AppConstants;

public class HomePageTest extends BaseTest {
	
	@BeforeClass
	public void setUpHomePage() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void getHomePageUrlTest() {
		String currentUrl = homePage.getHomePageUrl();
		Assert.assertEquals(currentUrl, AppConstants.HOME_PAGE_URL);
	}
	
	@Test
	public void clickSearchBoxTest() {
		searchPage = homePage.clickSearchBox();
		String currentUrl = searchPage.getSearchPageUrl();
		Assert.assertTrue(currentUrl.contains(AppConstants.SEARCH_PAGE_URL_FRACTION));
	}

}
