package com.googlecode.happymarvin.jiraminer;

import java.util.LinkedHashMap;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;

public class FakeSurfaceMining extends SurfaceMining {

	
	// Java POJO
	public JiraIssueBean mine_(final LinkedHashMap<String, Object> reponseJiraRest) {
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

	
	// Java Validator
	public JiraIssueBean mine(final LinkedHashMap<String, Object> reponseJiraRest) {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"Create a 'Java' component in the project 'tlem-validation-failures-report'. Use the template 'JSR303Validator'.\n" +
				"The name is 'PR2121'. The destination folder should be 'src/main/java/com/jpmorgan/ib/cp/tlem/validator'.\n" +
				//"The config XML file should be in 'src/main/resources/xmls' folder.\n" +
				"Please put the JSR303 validator class to the 'src/main/java/com/jpmorgan/ib/cp/tlem/validator' folder, the constraint class to the " +
				"'src/main/java/com/jpmorgan/ib/cp/tlem/validator' folder and the config XML to the 'src/main/resources' folder.\n" + 
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		return jiraIssueBean;
	}


}
