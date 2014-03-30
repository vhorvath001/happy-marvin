package com.googlecode.happymarvin.jiraminer;

import static org.junit.Assert.assertEquals;

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
public class UndergroundMiningTest_keyvalue_successful {

	
	private UndergroundMining undergroundMining = null;
	
	
	@Before
	public void setUp() {
		ApplicationContext context = new ClassPathXmlApplicationContext("jira-miner-context-test.xml");
		undergroundMining = context.getBean("undergroundMining", UndergroundMining.class);
	}
	

	@Test
	public void testMine_case0() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"TYPE:Java\n" +
				"TEMPLATE: POJO   \n" +
				"PROJECT: tlem-validation-failures-report \n" +
				"NAME:   EmailSender\n" +
				"LOCATION:src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils\n" +
				"METHOD: int send(String emailTo, String filePath, String reportName)\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		undergroundMining.mine(jiraIssueBean);
		
		assertEquals(1, jiraIssueBean.getInstructions().size());
		assertEquals("Java", jiraIssueBean.getInstructions().get(0).getType());
		assertEquals("POJO", jiraIssueBean.getInstructions().get(0).getTemplate());
		assertEquals("tlem-validation-failures-report", jiraIssueBean.getInstructions().get(0).getProject());
		assertEquals("EmailSender", jiraIssueBean.getInstructions().get(0).getName());
		assertEquals("src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils", jiraIssueBean.getInstructions().get(0).getLocation());
		assertEquals("int send(String emailTo, String filePath, String reportName)", jiraIssueBean.getInstructions().get(0).getProperties().get("method"));
	}


	@Test
	public void testMine_case1_diffent_order_of_values() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"METHOD: int send(String emailTo, String filePath, String reportName)\n" +
				"PROJECT: tlem-validation-failures-report \n" +
				"TEMPLATE: POJO   \n" +
				"NAME:   EmailSender\n" +
				"LOCATION:src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils\n" +
				"TYPE:Java\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		undergroundMining.mine(jiraIssueBean);
		
		assertEquals(1, jiraIssueBean.getInstructions().size());
		assertEquals("Java", jiraIssueBean.getInstructions().get(0).getType());
		assertEquals("POJO", jiraIssueBean.getInstructions().get(0).getTemplate());
		assertEquals("tlem-validation-failures-report", jiraIssueBean.getInstructions().get(0).getProject());
		assertEquals("EmailSender", jiraIssueBean.getInstructions().get(0).getName());
		assertEquals("src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils", jiraIssueBean.getInstructions().get(0).getLocation());
		assertEquals("int send(String emailTo, String filePath, String reportName)", jiraIssueBean.getInstructions().get(0).getProperties().get("method"));
	}
}

