package com.googlecode.happymarvin.jiraexplorer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.googlecode.happymarvin.jiraexplorer.RestClient;

import static org.junit.Assert.assertEquals;
//import static org.springframework.test.web.client.match.RequestMatchers.method;
//import static org.springframework.test.web.client.match.RequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.ResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:jiraminer-context-test.xml")
public class RestClientTest {

	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private RestClient restClient;
	
//	private MockRestServiceServer mockRestServiceServer;
	
	
	@Before
	public void setUp() {
		// setting up the mocked REST server
//		mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
	}
	
	
	@Test
	public void testGetJiraIssueAsJson() {
		String urlJira = "http://issuetracking.jpmchase.net/jira15/rest/api/latest/issue/";
		String numberJira = "IBTCPDCC-1470";
		
		String response = "{\"menu\": { \"id\": \"file\", \"value\": \"File\"}}";
//		mockRestServiceServer.expect(requestTo(urlJira + numberJira))
//		                     .andExpect(method(HttpMethod.GET))
//		                     .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
		
		restClient.getJiraIssueAsJson(urlJira, numberJira);
	}
}
