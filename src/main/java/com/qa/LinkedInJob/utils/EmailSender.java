package com.qa.LinkedInJob.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;

import com.qa.LinkedInJob.constants.AppConstants;

public class EmailSender {
	
	public static void SendEmail(String messageContent) {
		
		try {
			if (messageContent == null || messageContent.isEmpty()) {
                messageContent = AppConstants.NO_JOBS_FOUND_LAST_HOUR;
            }
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("training.manel.derbel@gmail.com", "ylbllftjworwkfpj"));
			email.setSSLOnConnect(true);
			email.setFrom("training.manel.derbel@gmail.com"); //Sender
			email.setSubject("LinkedIn QA Jobs");
			email.addTo("training.manel.derbel@gmail.com"); //Receiver
			email.setTextMsg(messageContent);
			email.setHtmlMsg("<h2>LinkedIn QA Jobs</h2>" +
                    		 "<p>" + messageContent.replace("\n", "<br>") + "</p>");
			email.send();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
