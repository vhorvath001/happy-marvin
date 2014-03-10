package com.googlecode.happymarvin.jiraminer;

import java.io.IOException;
import java.util.List;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;


public interface IUndergroundMining {

	
	public JiraIssueBean mine(final JiraIssueBean jiraIssueBean) throws IOException, InvalidInstructionException, ConfigurationException;
	
	
	public List<List<String[]>> getSentencePatternPairs();
	
}
