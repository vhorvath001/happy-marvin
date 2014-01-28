package com.googlecode.happymarvin.jiraminer;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;


@RunWith(SpringJUnit4ClassRunner.class)
public class UndergroundMiningTest {

	
	private UndergroundMining undergroundMining = null;
	
	
	@Before
	public void setUp() {
		undergroundMining = new UndergroundMining();
	}
	
	
	@Test
	public void testMine() throws IOException, InvalidInstructionException, ConfigurationException {
		JiraIssueBean jiraIssueBean = createJiraIssueBean();
		
		undergroundMining.mine(jiraIssueBean);
	}

	
	private JiraIssueBean createJiraIssueBean() {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		jiraIssueBean.setDescription("I'd need a POJO Java component in the project tlem-validation-failures-report.");
		
		return jiraIssueBean;
	}
}
