package com.googlecode.happymarvin.jiraminer;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:jira-miner-context-test.xml")
public class UndergroundMiningTest_keyvalue_unsuccessful {

	
	private UndergroundMining undergroundMining = null;
	
	
	@Before
	public void setUp() {
		ApplicationContext context = new ClassPathXmlApplicationContext("jira-miner-context-test.xml");
		undergroundMining = context.getBean("undergroundMining", UndergroundMining.class);
	}
	

	@Test
	// missing value: template
	public void testMine_case0_missing_basic_value() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"TYPE:Java\n" +
				"PROJECT: tlem-validation-failures-report \n" +
				"NAME:   EmailSender\n" +
				"LOCATION:src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils\n" +
				"METHOD: int send(String emailTo, String filePath, String reportName)\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);

		try {
			undergroundMining.mine(jiraIssueBean);
			fail("InvalidInstructionException should have been thrown!");
		} catch(InvalidInstructionException e) {
			assertEquals("There is not matching pattern for the sentence 'TYPE:Java.'! Perhaps the problem is that first you have to define " +
					"the general (TYPE,TEMPLATE,PROJECT,NAME,LOCATION) values in the sentences or in the key-values pairs...", e.getMessage());
		}
	}


	@Test
	// missing value: METHOD
	public void testMine_case1_missing_method_value() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"TYPE:Java\n" +
				"TEMPLATE: POJO   \n" +
				"PROJECT: tlem-validation-failures-report \n" +
				"NAME:   EmailSender\n" +
				"LOCATION:src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);

		try {
			undergroundMining.mine(jiraIssueBean);
			fail("InvalidInstructionException should have been thrown!");
		} catch(InvalidInstructionException e) {
			assertEquals("The key METHOD cannot be found in the instruction!", e.getMessage());
		}
	}
	
	
	@Test
	// after the PROJECT
	public void testMine_case2_values_are_not_in_new_line() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"METHOD: int send(String emailTo, String filePath, String reportName)\n" +
				"PROJECT: tlem-validation-failures-report " +
				"TEMPLATE: POJO   \n" +
				"NAME:   EmailSender\n" +
				"LOCATION:src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils\n" +
				"TYPE:Java\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		try {
			undergroundMining.mine(jiraIssueBean);
			fail("InvalidInstructionException should have been thrown!");
		} catch(InvalidInstructionException e) {
			assertEquals("There is not matching pattern for the sentence 'METHOD: int send(String emailTo, String filePath, String reportName).'! " +
					"Perhaps the problem is that first you have to define the general (TYPE,TEMPLATE,PROJECT,NAME,LOCATION) values in the " +
					"sentences or in the key-values pairs...", e.getMessage());
		}
	}
	
}

