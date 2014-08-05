package com.googlecode.happymarvin.jiraexplorer;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FakeRestClient extends RestClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(FakeRestClient.class);
	
	
	public LinkedHashMap<String, Object> getJiraIssueAsJson(final String urlJira, final String numberJira) {
		String url = urlJira + numberJira;
		LOGGER.info("Getting the JIRA issue from " + url);
		LOGGER.info("Successful call!");
		return null;
	}
	
}