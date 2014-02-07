package com.googlecode.happymarvin.orchestrator;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.happymarvin.artefactgenerator.ArtefactGenerator;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.jiraexplorer.RestClient;
import com.googlecode.happymarvin.jiraminer.SurfaceMining;
import com.googlecode.happymarvin.jiraminer.UndergroundMining;

import freemarker.template.TemplateException;

public class ProcessOrchestrator {

	
	private ArtefactGenerator artefactGenerator;
	private RestClient restClient;
	private SurfaceMining surfaceMining;
	private UndergroundMining undergroundMining;
	
	
	public void start() throws IOException, InvalidInstructionException, ConfigurationException, TemplateException {
		// calling the JIRA REST service
		LinkedHashMap<String, Object> responseFromREST = restClient.getJiraIssueAsJson("", "");
		
		// mining the JIRA ticket received from REST
		JiraIssueBean jiraIssueBean = surfaceMining.mine(responseFromREST);
		undergroundMining.mine(jiraIssueBean);
		
		// calling the artefact generation
		artefactGenerator.generate(jiraIssueBean);
	}


	public void setArtefactGenerator(ArtefactGenerator artefactGenerator) {
		this.artefactGenerator = artefactGenerator;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public void setSurfaceMining(SurfaceMining surfaceMining) {
		this.surfaceMining = surfaceMining;
	}

	public void setUndergroundMining(UndergroundMining undergroundMining) {
		this.undergroundMining = undergroundMining;
	}


	public static void main(String[] args) throws IOException, InvalidInstructionException, ConfigurationException, TemplateException {
		//ApplicationContext context = new ClassPathXmlApplicationContext("orchestrator-context.xml");
		ApplicationContext context = new ClassPathXmlApplicationContext("fake-orchestrator-context.xml");
		ProcessOrchestrator processOrchestrator = context.getBean("processOrchestrator", ProcessOrchestrator.class);
		
		processOrchestrator.start();
	}
	
}
