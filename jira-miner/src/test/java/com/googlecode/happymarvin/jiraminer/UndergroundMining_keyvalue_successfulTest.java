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
public class UndergroundMining_keyvalue_successfulTest {

	
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
	
	
	@Test
	// not mandatory property exists but it doesn't have value
	public void testMine_case2_notmandatory_property_with_empty_value() throws IOException, ConfigurationException, InvalidInstructionException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"TYPE:Java\n" +
				"TEMPLATE:JSR303Validator\n" +
				"PROJECT:tlem-processor\n" +
				"NAME:TlemValidationRule_2211\n" +
				"LOCATION:src/main/java/com/jpmorgan/ib/cp\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_VALIDATOR: src/main/java/com/jpmorgan/ib/cp/validator\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_CONSTRAINT:src/main/java/com/jpmorgan/ib/cp/constraint\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_CONFIGXML:\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		undergroundMining.mine(jiraIssueBean);
		
		assertEquals(1, jiraIssueBean.getInstructions().size());
		assertEquals("Java", jiraIssueBean.getInstructions().get(0).getType());
		assertEquals("JSR303Validator", jiraIssueBean.getInstructions().get(0).getTemplate());
		assertEquals("tlem-processor", jiraIssueBean.getInstructions().get(0).getProject());
		assertEquals("TlemValidationRule_2211", jiraIssueBean.getInstructions().get(0).getName());
		assertEquals("src/main/java/com/jpmorgan/ib/cp", jiraIssueBean.getInstructions().get(0).getLocation());
		assertEquals("src/main/java/com/jpmorgan/ib/cp/validator", jiraIssueBean.getInstructions().get(0).getProperties().get("location_Java_JSR303Validator_Validator"));
		assertEquals("src/main/java/com/jpmorgan/ib/cp/constraint", jiraIssueBean.getInstructions().get(0).getProperties().get("location_Java_JSR303Validator_Constraint"));
	}
	
	
	@Test
	//not mandatory property doesn't exist 
	public void testMine_case3_notmandatory_property_not_exist() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"TYPE:Java\n" +
				"TEMPLATE:JSR303Validator\n" +
				"PROJECT:tlem-processor\n" +
				"NAME:TlemValidationRule_2211\n" +
				"LOCATION:src/main/java/com/jpmorgan/ib/cp\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_VALIDATOR:src/main/java/com/jpmorgan/ib/cp/validator\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_CONSTRAINT:src/main/java/com/jpmorgan/ib/cp/constraint\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);

		undergroundMining.mine(jiraIssueBean);

		assertEquals(1, jiraIssueBean.getInstructions().size());
		assertEquals("Java", jiraIssueBean.getInstructions().get(0).getType());
		assertEquals("JSR303Validator", jiraIssueBean.getInstructions().get(0).getTemplate());
		assertEquals("tlem-processor", jiraIssueBean.getInstructions().get(0).getProject());
		assertEquals("TlemValidationRule_2211", jiraIssueBean.getInstructions().get(0).getName());
		assertEquals("src/main/java/com/jpmorgan/ib/cp", jiraIssueBean.getInstructions().get(0).getLocation());
		assertEquals("src/main/java/com/jpmorgan/ib/cp/validator", jiraIssueBean.getInstructions().get(0).getProperties().get("location_Java_JSR303Validator_Validator"));
		assertEquals("src/main/java/com/jpmorgan/ib/cp/constraint", jiraIssueBean.getInstructions().get(0).getProperties().get("location_Java_JSR303Validator_Constraint"));
	}
	

	@Test
	// colon in value 
	public void testMine_case4_colon_in_value() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"TYPE:Java\n" +
				"TEMPLATE:JSR303Validator\n" +
				"PROJECT:tlem-processor\n" +
				"NAME:TlemValidationRule_2211\n" +
				"LOCATION:C:/work/jpmorgan/Marklogic\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_VALIDATOR:C:\\svn3\\validator\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_CONSTRAINT:aaa\n" +
				"LOCATION_JAVA_JSR303VALIDATOR_CONFIGXML:\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);

		undergroundMining.mine(jiraIssueBean);

		assertEquals(1, jiraIssueBean.getInstructions().size());
		assertEquals("Java", jiraIssueBean.getInstructions().get(0).getType());
		assertEquals("JSR303Validator", jiraIssueBean.getInstructions().get(0).getTemplate());
		assertEquals("tlem-processor", jiraIssueBean.getInstructions().get(0).getProject());
		assertEquals("TlemValidationRule_2211", jiraIssueBean.getInstructions().get(0).getName());
		assertEquals("C:/work/jpmorgan/Marklogic", jiraIssueBean.getInstructions().get(0).getLocation());
		assertEquals("C:\\svn3\\validator", jiraIssueBean.getInstructions().get(0).getProperties().get("location_Java_JSR303Validator_Validator"));
		assertEquals("aaa", jiraIssueBean.getInstructions().get(0).getProperties().get("location_Java_JSR303Validator_Constraint"));
	}
}

