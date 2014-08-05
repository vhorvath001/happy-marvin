package com.googlecode.happymarvin.jiraminer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

import org.springframework.core.io.ClassPathResource;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;

public class FakeSurfaceMining extends SurfaceMining {

	
	// Java POJO
	public JiraIssueBean mine(final LinkedHashMap<String, Object> reponseJiraRest) {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		StringBuffer description = new StringBuffer("blahblah\n");
		try {
			ClassPathResource resource = new ClassPathResource("_instruction.txt");
			if (!resource.exists()) {
				throw new RuntimeException("the _instruction.txt doesn't exist!");
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader( resource.getInputStream()));
			String line = null;
			while ( (line = reader.readLine()) != null) {
				description.append(line).append("\n");
			}
		} catch (IOException e) {
			throw new RuntimeException("the _instruction.txt doesn't exist!", e);
		}
		
//		String description = 
//				"blahblah\n" +
//				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
//				"Create a 'Java' component in the project 'tlem-validation-failures-report'. Use the template 'POJO'.\n" +
//				"The name is 'EmailSender'. The destination folder should be 'src/main/java/com/acme/ib/cp/tlem/validationFailuresReport/utils'.\n" +
//				"Method needed: 'int send(String emailTo, String filePath, String reportName)'.\n" +
//				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description.toString());
		
		return jiraIssueBean;
	}

	
//	// Java Validator
//	public JiraIssueBean mine(final LinkedHashMap<String, Object> reponseJiraRest) {
//		JiraIssueBean jiraIssueBean = new JiraIssueBean();
//		
//		String description = 
//				"blahblah\n" +
//				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
//				"Create a 'Java' component in the project 'tlem-validation-failures-report'. Use the template 'JSR303Validator'.\n" +
//				"The name is 'PR2121'. The destination folder should be 'src/main/java/com/acme/ib/cp/tlem/validator'.\n" +
//				//"The config XML file should be in 'src/main/resources/xmls' folder.\n" +
//				"Please put the JSR303 validator class to the 'src/main/java/com/acme/ib/cp/tlem/validator' folder, the constraint class to the " +
//				"'src/main/java/com/acme/ib/cp/tlem/validator' folder and the config XML to the 'src/main/resources' folder.\n" + 
//				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
//		jiraIssueBean.setDescription(description);
//		
//		return jiraIssueBean;
//	}


}
