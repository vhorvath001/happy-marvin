package com.googlecode.happymarvin.orchestrator.operation;


import java.io.IOException;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.artefactgenerator.ArtefactGenerator;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.jiraexplorer.RestClient;
import com.googlecode.happymarvin.jiraminer.SurfaceMining;
import com.googlecode.happymarvin.jiraminer.UndergroundMining;

import freemarker.template.TemplateException;


public class GenerateOperation implements IOperation {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateOperation.class);
	
	private ArtefactGenerator artefactGenerator;
	private RestClient restClient;
	private SurfaceMining surfaceMining;
	private UndergroundMining undergroundMining;
	private ConfigurationReaderUtil configurationReaderUtil;
	
	
	public GenerateOperation(RestClient restClient,
                             ConfigurationReaderUtil configurationReaderUtil,
                             SurfaceMining surfaceMining, 
                             UndergroundMining undergroundMining, 
                             ArtefactGenerator artefactGenerator) {
		this.restClient = restClient;
		this.configurationReaderUtil = configurationReaderUtil;
		this.undergroundMining = undergroundMining;
		this.surfaceMining = surfaceMining;
		this.artefactGenerator = artefactGenerator;
	}

	
	public void perform(String... args) throws IOException, InvalidInstructionException, ConfigurationException, TemplateException {
		// calling the JIRA REST service
		LOGGER.info("----- 1/4 Calling the JIRA REST service -----");
		LinkedHashMap<String, Object> responseFromREST = restClient.getJiraIssueAsJson(
				configurationReaderUtil.getJiraRestUrlWithTrailingPer(), args[0]);
		
		// mining the JIRA ticket received from REST
		LOGGER.info("----- 2/4 Starting the surface mining -----");
		JiraIssueBean jiraIssueBean = surfaceMining.mine(responseFromREST);
		LOGGER.info("----- 3/4 Starting the underground mining -----");
		jiraIssueBean = undergroundMining.mine(jiraIssueBean);
		
		// calling the artefact generation
		LOGGER.info("----- 4/4 Starting the artefact(s) generation -----");
		artefactGenerator.generate(jiraIssueBean, true);
	}

	
}
