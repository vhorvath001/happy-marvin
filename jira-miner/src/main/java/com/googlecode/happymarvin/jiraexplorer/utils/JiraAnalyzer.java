package com.googlecode.happymarvin.jiraexplorer.utils;

import java.util.LinkedHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.happymarvin.jiraexplorer.RestClient;


/*
 * this is just an utility class to get the 'xpath' of the fields in the LinkedHashMap getting beck from the JIRA REST API
 */
public class JiraAnalyzer {

	
	private RestClient restClient;
	
	
	private void analyze(LinkedHashMap<String, Object> map, String path) {
		for(String key : map.keySet().toArray(new String[0])) {
			if (map.get(key) instanceof LinkedHashMap) {
				analyze((LinkedHashMap)map.get(key), path+"/"+key);
			} else {
				System.out.println("****************** " + path+"/"+key + " : " + (map.get(key)==null?"null":map.get(key).getClass()) + "*********************");
				System.out.println(map.get(key));
			}
		}
	}
	
	
	private void start() {
		String urlJira = "https://issuetracking.jpmchase.net/jira15/rest/api/latest/issue/";
		String numberJira = "IBTCPDCC-1470";

		LinkedHashMap<String, Object> map = restClient.getJiraIssueAsJson(urlJira, numberJira);
		
		analyze(map, "");
	}
	

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}
	

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("jiraminer-context.xml");
		JiraAnalyzer jiraAnalyzer = context.getBean("jiraAnalyzer", JiraAnalyzer.class);
		jiraAnalyzer.start();
	}


}