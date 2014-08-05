package com.googlecode.happymarvin.orchestrator;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.happymarvin.artefactgenerator.ArtefactGenerator;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.jiraexplorer.RestClient;
import com.googlecode.happymarvin.jiraminer.SurfaceMining;
import com.googlecode.happymarvin.jiraminer.UndergroundMining;
import com.googlecode.happymarvin.orchestrator.operation.GenerateOperation;
import com.googlecode.happymarvin.orchestrator.operation.IOperation;
import com.googlecode.happymarvin.orchestrator.operation.ReportOperation;

import freemarker.template.TemplateException;

public class ArtefactGenerationOrchestrator {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtefactGenerationOrchestrator.class);
	private enum Operation {GENERATE, REPORT};
	
	private ArtefactGenerator artefactGenerator;
	private RestClient restClient;
	private SurfaceMining surfaceMining;
	private UndergroundMining undergroundMining;
	private ConfigurationReaderUtil configurationReaderUtil;
	private Operation operation;
	
	
	public void start(String... args) throws IOException, InvalidInstructionException, ConfigurationException, TemplateException {
		LOGGER.info("\n");
		LOGGER.info("-----------------------------------------------------------");
		LOGGER.info("-------------------------- START --------------------------");
		LOGGER.info("-----------------------------------------------------------");
		
		try {
			if (checkArguments(args)) {
				doOperation(args);
			}
		} catch(Exception e) {
			LOGGER.error("Error happened during the execution!", e);
		} finally {
			LOGGER.info("--------------------------- END ---------------------------");
		}
	}


	private void doOperation(String[] args) throws IOException, InvalidInstructionException, ConfigurationException, TemplateException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		IOperation oneOperation = null;
		
		if (Operation.GENERATE.equals(operation)) {
			oneOperation = new GenerateOperation(restClient, configurationReaderUtil, surfaceMining, undergroundMining, artefactGenerator);
		} else if (Operation.REPORT.equals(operation)) {
			oneOperation = new ReportOperation(restClient, configurationReaderUtil, surfaceMining, undergroundMining, artefactGenerator);
		}
		
		oneOperation.perform(args);
	}


	private boolean checkArguments(String... args) {
		if (args.length < 2) {
			LOGGER.error("At least two arguments are needed to start the artefact generation!");
			LOGGER.error("---------- Usage ----------");
			LOGGER.error("             start_generator.bat <JIRA number> <COMMAND>");
			LOGGER.error("     sample: start_generator.bat ABDF-145 GENERATE");
			return false;
		} else {
			try {
				operation = Operation.valueOf(args[1].toUpperCase());
			} catch(Exception e) {
				LOGGER.error("Incorrect value in the second argument! Possible values:" + Operation.GENERATE + "," + Operation.REPORT);
				return false;
			}
			return true;
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

	public void setConfigurationReaderUtil(ConfigurationReaderUtil configurationReaderUtil) {
		this.configurationReaderUtil = configurationReaderUtil;
	}

	
	public static void main(String[] args) throws IOException, InvalidInstructionException, ConfigurationException, TemplateException {
		ApplicationContext context = new ClassPathXmlApplicationContext("orchestrator-context.xml");
//		ApplicationContext context = new ClassPathXmlApplicationContext("fake-orchestrator-context.xml");
		ArtefactGenerationOrchestrator processOrchestrator = context.getBean("artefactGenerationOrchestrator", ArtefactGenerationOrchestrator.class);
		
		processOrchestrator.start(args);
	}


}
