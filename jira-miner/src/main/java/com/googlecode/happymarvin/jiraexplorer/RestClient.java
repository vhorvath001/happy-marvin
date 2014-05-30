package com.googlecode.happymarvin.jiraexplorer;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


public class RestClient {


	private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
	
	private RestTemplate restTemplate;

	
	public LinkedHashMap<String, Object> getJiraIssueAsJson(final String urlJira, final String numberJira) {
		// https://ibjira.uk.jpmorgan.com/jira15/rest/api/latest/issue/DCPPPS-495
		String url = urlJira + numberJira;
		LOGGER.info("Getting the JIRA issue from " + url);
		Object storage = restTemplate.getForObject(url, Object.class);
		if (storage == null) {
			throw new RuntimeException("The response of the REST call is empty!");
		}
		if (!(storage instanceof LinkedHashMap)) {
			throw new RuntimeException("The response of the REST call is not a LinkedHashMap!");
		}
		LOGGER.info("Successful call!");
		return (LinkedHashMap<String, Object>)storage;
	}
	
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
//	public static void main(String[] args) {
//		String urlJira = "https://issuetracking.jpmchase.net/jira15/rest/api/latest/issue/";
//		String numberJira = "IBTCPDCC-1470";
//		
//		ApplicationContext context = new ClassPathXmlApplicationContext("jiraminer-context.xml");
//		RestClient restClient = context.getBean("restClient", RestClient.class);
//		restClient.getJiraIssueAsJson(urlJira, numberJira);
//	}
	
}