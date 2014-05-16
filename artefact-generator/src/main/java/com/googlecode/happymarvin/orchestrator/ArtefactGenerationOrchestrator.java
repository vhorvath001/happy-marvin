package com.googlecode.happymarvin.orchestrator;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.happymarvin.artefactgenerator.ArtefactGenerator;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.jiraexplorer.RestClient;
import com.googlecode.happymarvin.jiraminer.SurfaceMining;
import com.googlecode.happymarvin.jiraminer.UndergroundMining;

import freemarker.template.TemplateException;

public class ArtefactGenerationOrchestrator {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtefactGenerator.class);
	
	private ArtefactGenerator artefactGenerator;
	private RestClient restClient;
	private SurfaceMining surfaceMining;
	private UndergroundMining undergroundMining;
	private ConfigurationReaderUtil configurationReaderUtil;
	
	
	public void start(String... args) throws IOException, InvalidInstructionException, ConfigurationException, TemplateException {
		LOGGER.info("------------- START -------------");
		
		try {
			// checking the arguments
			check(args);
			
			// calling the JIRA REST service
			LOGGER.info("----- 1/4 Calling the JIRA REST service -----");
			LinkedHashMap<String, Object> responseFromREST = restClient.getJiraIssueAsJson(
					configurationReaderUtil.getJiraRestUrlWithTrailingPer(), args[0]);
			
			// mining the JIRA ticket received from REST
			LOGGER.info("----- 2/4 Starting the surface mining -----");
			JiraIssueBean jiraIssueBean = surfaceMining.mine(responseFromREST);
			LOGGER.info("----- 3/4 Starting the underground mining -----");
			undergroundMining.mine(jiraIssueBean);
			
			// calling the artefact generation
			LOGGER.info("----- 4/4 Starting the artefact(s) generation -----");
			artefactGenerator.generate(jiraIssueBean);
		} finally {
			LOGGER.info("------------- END -------------");
		}
	}


	private void check(String... args) {
		if (args.length != 1) {
			LOGGER.error("One argument is needed to start the artefact generation!");
			LOGGER.error("---------- Usage ----------");
			LOGGER.error("             start_generator.bat <JIRA number>");
			LOGGER.error("     sample: start_generator.bat ABDF-145");
		}
		
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
		ArtefactGenerationOrchestrator processOrchestrator = context.getBean("artefactGenerationOrchestrator", ArtefactGenerationOrchestrator.class);
		
		processOrchestrator.start(args);
	}
	
}
