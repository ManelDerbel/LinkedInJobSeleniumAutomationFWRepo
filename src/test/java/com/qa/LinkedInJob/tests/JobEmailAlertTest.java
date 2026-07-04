package com.qa.LinkedInJob.tests;

import java.util.List;

import org.testng.annotations.Test;

import com.qa.LinkedInJob.constants.AppConstants;
import com.qa.LinkedInJob.utils.EmailSender;
import com.qa.LinkedInJob.utils.ExcelUtils;
import com.qa.LinkedInJob.utils.StringUtils;

public class JobEmailAlertTest {

	@Test(dependsOnGroups="extractLastJobs")
	public void TestTBD() {
		System.out.println("TestTBD: " + getClass().getSimpleName());
		List<String> columnData = ExcelUtils.getColumnData("DailyJobs_UpdatedHourly", "FinalJobs_Last24Hours", 0);
		for (String data : columnData) {
			System.out.println(data);
		}

		Boolean isLastNoJobs = StringUtils.isLastStringEqual(columnData, AppConstants.NO_JOBS_FOUND_LAST_HOUR);
		System.out.println("isLastNoJobs: " + isLastNoJobs);
		int startRowIndex = StringUtils.getIndexOfString(columnData, AppConstants.LAST_HOUR_JOB_RESULTS);
		System.out.println("startRowIndex: " + startRowIndex);

		StringBuilder emailContent = new StringBuilder();

		if (!isLastNoJobs && startRowIndex != -1) {
			Object[][] dataFromRowIndex = ExcelUtils.getDataFromRowIndex("DailyJobs_UpdatedHourly",
					"FinalJobs_Last24Hours", startRowIndex);
			for (int i = 0; i < dataFromRowIndex.length; i++) {
				emailContent.append(dataFromRowIndex[i][0]).append("\n").append(dataFromRowIndex[i][1]).append("\n");
			}
		}

		EmailSender.SendEmail(emailContent.toString());
	}
}
