package com.googlecode.happymarvin.jiraminer;

import java.util.LinkedHashMap;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.utils.Constants;

public class FakeSurfaceMining extends SurfaceMining {

	
	public JiraIssueBean mine(final LinkedHashMap<String, Object> reponseJiraRest) {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"Create a 'Java' component in the project 'tlem-validation-failures-report'. Use the template 'POJO'.\n" +
				"The name is 'EmailSender'. The destination folder should be 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils'.\n" +
				"Method needed: 'int send(String emailTo, String filePath, String reportName)'.\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		return jiraIssueBean;
	}

	
}
