package com.qa.LinkedInJob.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.LinkedInJob.base.BaseTest;
import com.qa.LinkedInJob.constants.AppConstants;
import com.qa.LinkedInJob.models.JobData;
import com.qa.LinkedInJob.utils.ExcelUtils;

public class JobsSearchPageTest extends BaseTest {
	private List<JobData> uniquejobs;
	private Set<String> seenLinks;
	private List<String> keywords;

	@BeforeClass
	public void JobsSearchPageSetUp() {
		System.out.println("JobsSearchPageSetUp: " + getClass().getSimpleName());
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		searchPage = homePage.clickSearchBox();

		if (searchPage.btnAllFiltresIsExist()) {
			searchPage.FiltreJobSearch();
		} else {
			jobsSearchPage = searchPage.ApplyJobSearch();
		}

		uniquejobs = new ArrayList<>();
		seenLinks = new HashSet<>();

		String jobKeywordsValue = prop.getProperty("jobKeywords");
		keywords = Arrays.asList(jobKeywordsValue.split(","));
	}

	@Test
	public void getJobsSearchPageUrlTest() {
		String currentUrl = jobsSearchPage.getJobsSearchPageUrl();
		Assert.assertTrue(currentUrl.contains(AppConstants.JOBS_SEARCH_PAGE_URL_FRACTION));
	}

	@Test
	public void TBD() {
		System.out.println("TBD: " + getClass().getSimpleName());
		String optionSelected = jobsSearchPage.getSelectedDropdownOption();
		if (optionSelected.equals("Jobs")) {
			jobsSearchPage.AdvanceJobSearch();
		}
	}

	@DataProvider
	public Object[][] getJobDataFromSheet() {
		Object[][] obj = ExcelUtils.getDataExcludingHeader("KeywordLocationJobs", "Keyword_Location_Jobs");
		return obj;
	}

	@Test(dependsOnMethods = "TBD", dataProvider = "getJobDataFromSheet")
	public void doSearchTest(String keyword, String location) {
		System.out.println("doSearchTest: " + getClass().getSimpleName());
		jobsSearchPage.doSearchKeyword(keyword);
		//String keywordTxt = jobsSearchPage.doSearchKeyword(keyword);
		//Assert.assertTrue(keywordTxt.contains(keyword));

		jobsSearchPage.doSearchLocation(keyword, location);
		//String FullJobSearchMsg = jobsSearchPage.doSearchLocation(keyword, location);
		//Assert.assertTrue(FullJobSearchMsg.equals(keyword + " in " + location));
	}

	@AfterMethod
	public void getjobsInformationTest(Method method) {

		if (method.getName().equals("doSearchTest")) {
			uniquejobs.addAll(jobsSearchPage.getjobsInformation(seenLinks, keywords));
			System.out.println("jobs.size()= " + uniquejobs.size());

			while (jobsSearchPage.isNextButtonJobListExist()) {
				jobsSearchPage.doClickNextButtonJobList();
				uniquejobs.addAll(jobsSearchPage.getjobsInformation(seenLinks, keywords));
				System.out.println("jobs.size()= " + uniquejobs.size());
			}
		}

		System.out.println("Final jobs.size()= " + uniquejobs.size());
		// System.out.println(jobs.toString());
	}

	@Test(dependsOnMethods = "doSearchTest", groups = "extractLastJobs")
	public void TBDExcel() {
		System.out.println("TBDExcel: " + getClass().getSimpleName());
		int rowCount = ExcelUtils.getRowCount("config", "excel.path", "FinalJobs_Last24Hours");
		Set<String> data = ExcelUtils.getUniqueColumnData("config", "excel.path",
				"FinalJobs_Last24Hours", 1);

		if (uniquejobs.size() != 0) {
			ExcelUtils.setCellData("config", "excel.path", "FinalJobs_Last24Hours", rowCount, 0,
					"Last Hour Job Search Results");
			for (JobData job : uniquejobs) {
				String jobHeader = job.toString();
				String jobLink = job.getJobLink();
				if (data.add(jobLink)) {
					ExcelUtils.setCellData("config", "excel.path", "FinalJobs_Last24Hours",
							rowCount + 1, 0, jobHeader);
					ExcelUtils.setCellData("config", "excel.path", "FinalJobs_Last24Hours",
							rowCount + 1, 1, jobLink);
					rowCount++;
				}
			}
		} else {
			ExcelUtils.setCellData("config", "excel.path", "FinalJobs_Last24Hours", rowCount, 0,
					"No Jobs Found in the Last Hour");
		}

	}
}
