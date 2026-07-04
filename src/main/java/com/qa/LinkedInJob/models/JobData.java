package com.qa.LinkedInJob.models;

public class JobData {
	
	private final String jobTitle;
	private final String companyName;
	private final String jobLocation;
	private final String jobLink;
	
	public JobData(String jobTitle, String companyName, String jobLocation, String jobLink) {
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.jobLocation = jobLocation;
		this.jobLink = jobLink;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	
	public String getJobLink() {
		String urlJob = jobLink.split("\\?")[0];
		
		return urlJob;
	}
	
	@Override
	public String toString() {
		return jobTitle + " | " + companyName + " | " + jobLocation;
	}
}
