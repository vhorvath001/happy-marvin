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
public class UndergroundMiningTest_sentences_successful {

	
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
				"I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'.\n" +
				"The name should be 'EmailSender'. You should put it into the 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils' folder. Please add a method to it: 'int send(String emailTo, String filePath, String reportName)'\n" +
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
	// if the values are not between apostrophes then the sentence 'Please create a Java component in the project tlem-validation-failures-report.' matches
	//   the pattern  ([P][l][e][a][s][e][ ])?[cC][r][e][a][t][e][ ]([a][ ])?[a-zA-Z0-9-_:]+[ ][a-zA-Z0-9-_:]+[ ][c][o][m][p][o][n][e][n][t][ ][i][n][ ][t][h][e][ ][p][r][o][j][e][c][t][ ][a-zA-Z0-9-_:]+
	//   which is wrong as in the sentence there is just one value defined between the text 'component' and not too!!! -> the apostrophes can solve this problem
	public void testMine_case1() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"Create a 'Java' component in the project 'tlem-validation-failures-report'. Use the template 'POJO'.\n" +
				"The name is 'EmailSender'. The destination folder should be 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils'.\n" +
				"Method needed: 'int send(String emailTo, String filePath, String reportName)'.\n" +
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
	public void testMine_case2_in_one_line() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'. The name should be 'EmailSender'. You should put it into the 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils' folder. Please add a method to it: 'int send(String emailTo, String filePath, String reportName)'\n" +
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
	public void testMine_case2_in_new_lines() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'.\n" +
				"The name should be 'EmailSender'. \n" +
				"You should put it into the 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils' folder. \n" +
				"Please add a method to it: 'int send(String emailTo, String filePath, String reportName)'\n" +
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
	public void testMine_case3_only_mandatory_texts() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		String description = 
				"blahblah\n" +
				"~~~HAPPYMARVIN-INSTRUCTION~~~\n" +
				"I need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'.\n" +
				"The name is 'EmailSender'. \n" +
				"Put it into the 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils' folder. \n" +
				"Add a method: 'int send(String emailTo, String filePath, String reportName)'\n" +
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
