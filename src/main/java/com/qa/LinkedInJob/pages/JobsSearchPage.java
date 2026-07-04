package com.qa.LinkedInJob.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.LinkedInJob.constants.AppConstants;
import com.qa.LinkedInJob.models.JobData;
import com.qa.LinkedInJob.utils.ElementUtils;
import com.qa.LinkedInJob.utils.StringUtils;

public class JobsSearchPage {

	private final By resultsListTitle = By.id("results-list__title");
	private final By resultsListHeader = By.xpath("//main[@id='main']//header");

	private final By baseIframe = By.xpath("// iframe[@data-testid='interop-iframe']");
	private final By btnAllFiltres = By.xpath("//button[text()='All filters']");
	private final By dropDownOptions = By.xpath("//span[@id='selected-vertical']/div/button");
	private final By rdBtnSortByDD = By.xpath("//label[@for='advanced-filter-sortBy-DD']");
	private final By rdBtnSortByTime = By.xpath("(//label[contains(@for, 'advanced-filter-timePosted')])[4]");
	private final By btnShowResults = By.xpath("//div[@id='artdeco-modal-outlet']/div/div/div[3]/div/button[2]");
	private final By SearchBoxKeyword = By.xpath("//input[contains(@id, 'jobs-search-box-keyword')]");
	private final By SearchBoxLocation = By.xpath("//input[contains(@id, 'jobs-search-box-location')]");
	private final By searchBtn = By.xpath("//button[text()='Search']");
	private final By FullJobSearchTitle = By.xpath("//span[@id='results-list__title']");

	private final By jobListResult = By.xpath("//main[@id='main']/div/div[2]/div[1]/div/ul/li");
	private final By roleTitle = By
			.xpath(".//div[contains(@class, 'flex-grow-1 artdeco-entity-lockup')]/div[1]/a/span/strong");
	private final By roleCompany = By
			.xpath(".//div[contains(@class, 'flex-grow-1 artdeco-entity-lockup')]/div[2]/span");
	private final By roleLocation = By
			.xpath(".//div[contains(@class, 'flex-grow-1 artdeco-entity-lockup')]/div[3]/ul/li/span");
	private final By roleLink = By.xpath(".//div[contains(@class, 'flex-grow-1 artdeco-entity-lockup')]/div[1]/a");

	private final By btnNext = By.xpath("//div[@id='jobs-search-results-footer']//span[text()='Next']");

	private WebDriver driver;
	private ElementUtils elUtils;

	public JobsSearchPage(WebDriver driver) {
		this.driver = driver;
		elUtils = new ElementUtils(driver);
	}

	public String getJobsSearchPageUrl() {
		String currentUrl = elUtils.waitForUrlContains(AppConstants.SHORT_WAIT_5s,
				AppConstants.JOBS_SEARCH_PAGE_URL_FRACTION);

		return currentUrl;
	}

	public Boolean isResultsListHeaderExist() {
		Boolean flag = elUtils.isElementDisplayed(resultsListHeader);

		return flag;
	}

	public String getresultsListTitle() {
		String ListTitle = elUtils.waitForElementVisibility(AppConstants.SHORT_WAIT_5s, resultsListTitle).getText();
		System.out.println("The title of list results is: " + resultsListTitle);

		return ListTitle;
	}

	public String getSelectedDropdownOption() {
		elUtils.TBD(AppConstants.SHORT_WAIT_5s, baseIframe);
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, btnAllFiltres).click();
		String optionSelected = elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, dropDownOptions)
				.getText();

		return optionSelected;
	}

	public void AdvanceJobSearch() {
		elUtils.waitForElementToBeClickable(AppConstants.SHORT_WAIT_5s, rdBtnSortByDD).click();
		elUtils.doClick(rdBtnSortByTime);

		elUtils.doClick(btnShowResults);
	}

	public String doSearchKeyword(String keyword) {
		WebElement BoxKeyword = elUtils.waitForElementVisibility(AppConstants.SHORT_WAIT_5s, SearchBoxKeyword);
		BoxKeyword.clear();
		BoxKeyword.sendKeys(keyword);
		BoxKeyword.sendKeys(Keys.ENTER);

		String keywordTxt = elUtils.waitForStringContains(AppConstants.SHORT_WAIT_5s, FullJobSearchTitle, keyword);
		return keywordTxt;
	}

	public String doSearchLocation(String keyword, String location) {

		WebElement BoxLocation = elUtils.getElement(SearchBoxLocation);
		BoxLocation.clear();
		BoxLocation.sendKeys(location);
		BoxLocation.sendKeys(Keys.ENTER);

		String FullJobSearchMsg = elUtils.waitForExactString(AppConstants.SHORT_WAIT_5s, FullJobSearchTitle,
				keyword + " in " + location);
		elUtils.doClick(searchBtn);

		return FullJobSearchMsg;
	}

	public List<JobData> getjobsInformation(Set<String> seenLinks, List<String> keywords) {
		List<WebElement> jobList = elUtils.waitForElementsVisibility(AppConstants.SHORT_WAIT_5s, jobListResult);
		List<JobData> jobDataList = new ArrayList<>();

		for (WebElement job : jobList) {
			WebElement el = elUtils.doScrollToElement(job);
			String jobTitle = elUtils.getTextpointedElement(el, roleTitle);
			String companyName = elUtils.getTextpointedElement(el, roleCompany);
			String jobLocation = elUtils.getTextpointedElement(el, roleLocation);
			String jobLink = elUtils.getLinkpointedElement(el, roleLink);

			JobData jobData = new JobData(jobTitle, companyName, jobLocation, jobLink);

			if (!seenLinks.add(jobData.getJobLink())) {
				continue;
			}
			
			Boolean containsKeyword = StringUtils.containsAnyKeywordFromList(keywords, jobTitle);
			if(!containsKeyword) {
				continue;
			}
			
			System.out.println("Status Title Job: "+jobTitle+" : "+containsKeyword);
			System.out.println(jobData.getJobLink());
			jobDataList.add(jobData);
		}
		System.out.println("jobDataList.size()= " + jobDataList.size());
		return jobDataList;
	}

	public Boolean isNextButtonJobListExist() {
		Boolean flag = elUtils.isElementsDisplayed(btnNext);

		return flag;
	}

	public void doClickNextButtonJobList() {
		elUtils.doClick(btnNext);
	}

}
