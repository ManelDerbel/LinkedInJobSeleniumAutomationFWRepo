package com.qa.LinkedInJob.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.LinkedInJob.constants.AppConstants;
import com.qa.LinkedInJob.utils.ElementUtils;

public class SearchPage {
	
	private final By btnJobs = By.xpath("//label[text()='Jobs']");
	private final By btnAllFiltres = By.xpath("//button[normalize-space()='All filters']");
	private final By dropDownOptions = By.xpath("//span[@id='selected-vertical']/div/button");
	private final By JobsOption = By.xpath("//div[text()='Jobs']");
	private final By rdBtnSortByDD = By.xpath("//label[@for='advanced-filter-sortBy-DD']");
	private final By rdBtnSortByTime =  By.xpath("(//label[contains(@for, 'advanced-filter-timePosted')])[4]");
	private final By btnShowResults = By.xpath("//span[text()='Show results']");
	
	private WebDriver driver;
	private ElementUtils elUtils;
	
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		elUtils = new ElementUtils(driver);
	}
	
	public String getSearchPageUrl() {
		String currentUrl = elUtils.waitForUrlContains(AppConstants.SHORT_WAIT_5s, AppConstants.SEARCH_PAGE_URL_FRACTION);
		System.out.println("URL of Search Page is: "+currentUrl);
		
		return currentUrl;
	}
	
	public Boolean btnAllFiltresIsExist() {
		Boolean flag = elUtils.isElementsDisplayed(btnAllFiltres);
		
		return flag;
	}
	
	public JobsSearchPage ApplyJobSearch() {
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, btnJobs).click();
		elUtils.waitForTitleContains(AppConstants.LONG_WAIT_10s, AppConstants.JOBS_SEARCH_PAGE_TITLE_FRACTION);
		
		return new JobsSearchPage(driver);
	}
	
	public JobsSearchPage FiltreJobSearch() {
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, btnAllFiltres).click();
		
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, dropDownOptions).click();
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, JobsOption).click();
		
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, rdBtnSortByDD).click();
		elUtils.doClick(rdBtnSortByTime);
		
		elUtils.doClick(btnShowResults);
		elUtils.waitForTitleContains(AppConstants.LONG_WAIT_10s, AppConstants.JOBS_SEARCH_PAGE_TITLE_FRACTION);
		
		return new JobsSearchPage(driver);
	}

}
