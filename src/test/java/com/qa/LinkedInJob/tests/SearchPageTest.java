package com.qa.LinkedInJob.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.LinkedInJob.base.BaseTest;
import com.qa.LinkedInJob.constants.AppConstants;

public class SearchPageTest extends BaseTest {
	
	@BeforeClass
	public void SearchPageSetUp() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		searchPage = homePage.clickSearchBox();
	}
	
	@Test
	public void getSearchPageUrl() {
		String currentUrl = searchPage.getSearchPageUrl();
		Assert.assertTrue(currentUrl.contains(AppConstants.SEARCH_PAGE_URL_FRACTION));
	}
	
	@Test
	public void FiltreJobSearchTest() {
		jobsSearchPage = searchPage.FiltreJobSearch();
		Assert.assertTrue(jobsSearchPage.isResultsListHeaderExist());
		
		String resultsListTitle = jobsSearchPage.getresultsListTitle();
		Assert.assertTrue(resultsListTitle.contains("Jobs"));
	}

}
