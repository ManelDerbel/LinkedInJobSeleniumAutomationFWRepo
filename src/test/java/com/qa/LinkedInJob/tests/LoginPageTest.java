package com.qa.LinkedInJob.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.LinkedInJob.base.BaseTest;
import com.qa.LinkedInJob.constants.AppConstants;

public class LoginPageTest extends BaseTest {

	@Test
	public void getLoginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test
	public void getLoginPageUrlTest() {
		String actualUrl = loginPage.getLoginPageUrl();
		Assert.assertTrue(actualUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}

	@Test
	public void loginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(homePage.isShareBoxExist());
	}
}
