package com.googlecode.happymarvin.jiraexplorer;

import java.util.LinkedHashMap;

import org.springframework.web.client.RestTemplate;


public class RestClient {

	
	private RestTemplate restTemplate;

	
	public LinkedHashMap<String, Object> getJiraIssueAsJson(final String urlJira, final String numberJira) {
		// http://issuetracking.jpmchase.net/jira15/rest/api/latest/issue/IBTCPDCC-1470
		String url = urlJira + numberJira;
		Object storage = restTemplate.getForObject(url, Object.class);
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