package com.googlecode.happymarvin.artefactgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.common.beans.InstructionBean;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateFileBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.common.utils.Constants;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ArtefactGenerator {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtefactGenerator.class);
	
	private ConfigurationReaderUtil configurationReaderUtil = null;
	
	
	// generating the artefact(s) based on the JiraIssueBean
	public void generate(JiraIssueBean jiraIssueBean) throws IOException, TemplateException, InvalidInstructionException, ConfigurationException {
		// initializing the Freemarker configuration
		Configuration cfg = init();
		
		// looping through all the instructions
		if (jiraIssueBean.getInstructions().size() == 0) {
			throw new InvalidInstructionException("There is no instruction in the JIRA!");
		}
		for(int i = 0; i < jiraIssueBean.getInstructions().size(); i++) {
			InstructionBean instructionBean = jiraIssueBean.getInstructions().get(i);
			LOGGER.info(String.format("Processing the instruction %d. type = %s, name = %s", i, instructionBean.getType(), instructionBean.getName()));
			TemplateBean templateBean = configurationReaderUtil.getTemplate(instructionBean.getType(), instructionBean.getTemplate());
			
			// checking if the JiraIssueBean contains all the necessary data that are needed for generating the artefact(s)
			//   (e.g. Java POJO - a method is needed if mandatory = true)
			examineJiraIssueBean(instructionBean, templateBean, i);

			// creating the datamodel from JiraIssueBean for Freemarker
			Map<String, Object> dataModel = creatingDataModel(instructionBean);

			// looping through the template files defined in the template config XML
			for(TemplateFileBean templateFile : templateBean.getFiles()) {
				// getting the template
				Template template = getTemplate(templateFile, cfg);

				// setting where to write and writing the output there
				Writer write = getWriter(instructionBean, templateFile, templateBean.getProperties());
				template.process(dataModel, write);
			}
		}
	}


	private void examineJiraIssueBean(InstructionBean instructionBean, TemplateBean templateBean, int i) throws InvalidInstructionException, ConfigurationException {
		// checking the type, template, project, name and location
		if (instructionBean.getType() == null || instructionBean.getType().trim().length() == 0) {
			throw new InvalidInstructionException(String.format("The type cannot be found in the instruction %d!", i));
		} else if (instructionBean.getTemplate() == null || instructionBean.getTemplate().trim().length() == 0) {
			throw new InvalidInstructionException(String.format("The template cannot be found in the instruction %d!", i));
		} else if (instructionBean.getProject() == null || instructionBean.getProject().trim().length() == 0) {
			throw new InvalidInstructionException(String.format("The project cannot be found in the instruction %d!", i));
		} else if (instructionBean.getName() == null || instructionBean.getName().trim().length() == 0) {
			throw new InvalidInstructionException(String.format("The name cannot be found in the instruction %d!", i));
		} else if (instructionBean.getLocation() == null || instructionBean.getLocation().trim().length() == 0) {
			throw new InvalidInstructionException(String.format("The location cannot be found in the instruction %d!", i));
		}
		// checking the template specific properties
		for(TemplatePropertyBean templatePropertyBean : templateBean.getProperties()) {
			// if the property is mandatory ...
			if (templatePropertyBean.getMandatory() != null && templatePropertyBean.getMandatory().equalsIgnoreCase(Constants.TRUE)) {
				String valueProp = instructionBean.getProperties().get(templatePropertyBean.getName());
				// ... and empty
				if (valueProp == null || valueProp.trim().length() == 0) {
					throw new InvalidInstructionException(String.format("The %s cannot be found in the instruction %d!", templatePropertyBean.getName(), i));
				}
			}
		}
	}

	
	private Writer getWriter(InstructionBean instructionBean, TemplateFileBean templateFileBean, List<TemplatePropertyBean> templatePropertyBeans) throws IOException, ConfigurationException {
		String pathProject = configurationReaderUtil.getProjects(instructionBean.getProject()).getValue();
		pathProject = pathProject.endsWith("/") ? pathProject : pathProject + "/";
		
		// if the path of the project doesn't exist then throw an exception
		if (pathProject == null || pathProject.trim().length() == 0) {
			throw new ConfigurationException(String.format("The path of the project %s is empty in the config file!", instructionBean.getProject()));
		}
		// check if the path of the project exists
		if (!new File(pathProject).exists()) {
			throw new ConfigurationException(String.format("The project %s on the path %s does not exist!", instructionBean.getProject(), pathProject));
		}
		
		// getting the location of the artefact file
		String location = getLocation(instructionBean.getProperties(), templateFileBean.getName(), templatePropertyBeans, instructionBean.getLocation());
		
		// checking if the location folder exists in the project
		if (!new File(pathProject + location).exists()) {
			// if it doesn't exist then create the folder
			LOGGER.info(String.format("The folder %s is going to be created...", pathProject + location));
			StringBuilder pathArtefact = new StringBuilder(pathProject);
			for(String folder : location.split("/")) {
				pathArtefact.append(folder).append("/");
				if (!new File(pathArtefact.toString()).exists()) {
					new File(pathArtefact.toString()).mkdir();
				}
			}
		}
		
		// building the name of the file to be created
		String artefactFileName = pathProject + location;
		artefactFileName = artefactFileName.endsWith("/") ? artefactFileName : artefactFileName+"/";
		artefactFileName = artefactFileName + 
                           instructionBean.getName() +
                           "." +
                           configurationReaderUtil.getTemplate(instructionBean.getType(), instructionBean.getTemplate()).getExtension();
		
		LOGGER.info(String.format("The name of the file to be generated is %s.", artefactFileName));
		
		// checking if the artefact file exists -> it cannot be overridden
		if (new File(artefactFileName).exists()) {
			throw new ConfigurationException(String.format("The artefact file %s exists, it cannot be overridden!", artefactFileName));
		}
		
		return new FileWriter(artefactFileName);
	}

	
	// there are two types of location
	//   - common location e.g. ${location}
	//   - template specific location e.g. ${location_Java_JSR303Validator_Validator}
	// if the ${location_Java_JSR303Validator_Validator} exists then that value has to be used 
	// else use the ${location}
	// TODO one more question: if the ${location_Java_JSR303Validator_Validator} is defined then does the ${location} have to exist? at the moment yes
	// getting the location of the artefact file	
	private String getLocation(Map<String, String> properties, String templateFileName, List<TemplatePropertyBean> templatePropertyBeans, String commonLocation) {
		// looping through template properties
		for(TemplatePropertyBean templatePropertyBean : templatePropertyBeans) {
			// if the template has the locationOf property and the it refers to the file (templateFileName) being processed at the moment then ... 
			if (templatePropertyBean.getLocationOf() != null && templatePropertyBean.getLocationOf().equals(templateFileName)) {
				// ... then it has to check if the property (e.g. location_Java_JSR303Validator_Validator) exists in the property map ...
				if (properties.get(templatePropertyBean.getName()) != null) {
					return properties.get(templatePropertyBean.getName());
				} 
				// ... if not then the common location has to be used
				else {
					break;
				}
			}
		}
		return commonLocation;
	}


	// getting the Freemarker templace by the path of the template file
	private Template getTemplate(TemplateFileBean templateFile, Configuration cfg) throws IOException {
		return cfg.getTemplate(templateFile.getPath());
	}

	
	private Map<String, Object> creatingDataModel(InstructionBean instructionBean) {
		Map<String, Object> dataModelProperties = new HashMap<String, Object>();

		Map<String, Object> dataModelHm = new HashMap<String, Object>();
		dataModelHm.put("property", dataModelProperties);

		Map<String, Object> dataModelRoot = new HashMap<String, Object>();
		dataModelRoot.put("hm", dataModelHm);
		
		
		// setting the type, template, project, name and location
		dataModelHm.put("type", instructionBean.getName());
		dataModelHm.put("template", instructionBean.getTemplate());
		dataModelHm.put("project", instructionBean.getProject());
		dataModelHm.put("name", instructionBean.getName());
		dataModelHm.put("location", instructionBean.getLocation());

		// setting the properties
		for(String name : instructionBean.getProperties().keySet()) {
			dataModelProperties.put(name, instructionBean.getProperties().get(name));
		}
		
		return dataModelRoot;
	}

	
	private Configuration init() throws IOException {
		Configuration cfg = new Configuration();
		// Specify the data source where the template files come from.
		//cfg.setDirectoryForTemplateLoading(new File("templates"));
		cfg.setClassForTemplateLoading(this.getClass(), "/templates");
		// Specify how templates will see the data-model.
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		// Set your preferred charset template files are stored in. UTF-8 is a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");
		// Sets how errors will appear.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		
		return cfg;
	}


	public void setConfigurationReaderUtil(ConfigurationReaderUtil configurationReaderUtil) {
		this.configurationReaderUtil = configurationReaderUtil;
	}


}
