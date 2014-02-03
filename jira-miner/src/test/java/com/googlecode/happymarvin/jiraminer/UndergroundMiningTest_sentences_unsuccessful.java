package com.googlecode.happymarvin.jiraminer;

import java.io.IOException;

import static org.junit.Assert.*;

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
@ContextConfiguration(locations="classpath:jiraminer-context-test.xml")
public class UndergroundMiningTest_sentences_unsuccessful {

	
	private ApplicationContext context = null;
	
	
	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("jiraminer-context-test.xml");
	}
	
	
	@Test
	// in config line 16 : char 92
	public void testMine_invalidConfig_notClosedSquaredBracket() throws IOException, InvalidInstructionException, ConfigurationException {
		testMineCatchConfigurationException("undergroundMining_notClosedSquaredBracket", 
                 "A squared bracket is not closed in the pattern 'I['d] need a '${template}' '${type}' [component]/[class]/[file]/[XML file] in the [project]/[folder '${project}'.'!");
	}


	@Test
	// in config line 16 : char 76
	public void testMine_invalidConfig_startingSquaredBracketMissing() throws IOException, InvalidInstructionException, ConfigurationException {
		testMineCatchConfigurationException("undergroundMining_startingSquaredBracketMissing", 
                 "A starting squared bracket is missing in the pattern 'I['d] need a '${template}' '${type}' [component]/[class]/file]/[XML file] in the [project]/[folder] '${project}'.'!");
	}


	@Test
	// in config line 16 : char 131
	public void testMine_invalidConfig_notClosedCurlyBracket() throws IOException, InvalidInstructionException, ConfigurationException {
		testMineCatchConfigurationException("undergroundMining_notClosedCurlyBracket", 
                 "A curly bracket is not closed in the pattern 'I['d] need a '${template}' '${type}' [component]/[class]/[file]/[XML file] in the [project]/[folder] '${project'.'!");
	}


	@Test
	// in config line 16 : char 34
	public void testMine_invalidConfig_startingCurlyBracketMissing() throws IOException, InvalidInstructionException, ConfigurationException {
		testMineCatchConfigurationException("undergroundMining_startingCurlyBracketMissing", 
                 "A starting curly bracket is missing in the pattern 'I['d] need a '$template}' '${type}' [component]/[class]/[file]/[XML file] in the [project]/[folder] '${project}'.'!");
	}


	@Test
	// in config line 16 : char 34
	public void testMine_invalidConfig_matchingPatternCannotBeFound() throws IOException, InvalidInstructionException, ConfigurationException {
		testMineCatchInvalidInstructionException("undergroundMining_matchingPatternCannotBeFound", 
				"There is not matching pattern for the sentence 'I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'.'! " +
				"Perhaps the problem is that first you have to define the general (not template specific) values in the sentences ...");
	}
	
	private void testMineCatchConfigurationException(String bean, String message) throws IOException, InvalidInstructionException {
		UndergroundMining undergroundMining = new ClassPathXmlApplicationContext("jiraminer-context-test.xml").getBean(bean, UndergroundMining.class);
		
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'.\n" +
				"The name should be 'EmailSender'. You should put it into the 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils' folder. Please add a method to it: 'int send(String emailTo, String filePath, String reportName)'\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		try {
			undergroundMining.mine(jiraIssueBean);
			fail("A ConfigurationException should have been thrown!");
		} catch(ConfigurationException ce) {
			assertEquals(message ,ce.getMessage());
		}
	}

	private void testMineCatchInvalidInstructionException(String bean, String message) throws IOException, ConfigurationException {
		UndergroundMining undergroundMining = new ClassPathXmlApplicationContext("jiraminer-context-test.xml").getBean(bean, UndergroundMining.class);
		
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'.\n" +
				"The name should be 'EmailSender'. You should put it into the 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils' folder. Please add a method to it: 'int send(String emailTo, String filePath, String reportName)'\n" +
				"~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
		jiraIssueBean.setDescription(description);
		
		try {
			undergroundMining.mine(jiraIssueBean);
			fail("A ConfigurationException should have been thrown!");
		} catch(InvalidInstructionException iie) {
			assertEquals(message, iie.getMessage());
		}
	}
}
