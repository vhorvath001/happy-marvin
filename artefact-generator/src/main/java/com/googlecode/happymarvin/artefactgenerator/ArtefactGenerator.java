package com.googlecode.happymarvin.artefactgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.artefactgenerator.writer.VirtualWriter;
import com.googlecode.happymarvin.artefactgenerator.writer.VirtualWriterManager;
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
	private VirtualWriterManager virtualWriterManager = null;
	
	
	// generating the artefact(s) based on the JiraIssueBean
	// TODO first must examine if all the artefacts can be generated (without creating the files!) and if they can
	//      then do the creation of the files! (e.g. there is an exception while creating the 2nd file then the first 
	//      file has been already created but not the others)
	// DONE package in the Java classes! from location
	public void generate(JiraIssueBean jiraIssueBean) throws IOException, TemplateException, InvalidInstructionException, ConfigurationException {
		// initializing the Freemarker configuration
		Configuration cfg = init();
		
		// looping through all the instructions
		if (jiraIssueBean.getInstructions().size() == 0) {
			throw new InvalidInstructionException("There is no instruction in the JIRA!");
		}
		for(int i = 0; i < jiraIssueBean.getInstructions().size(); i++) {
			InstructionBean instructionBean = jiraIssueBean.getInstructions().get(i);
			LOGGER.info(String.format("Processing the instruction %d - type = %s, template = %s, name = %s", i, instructionBean.getType(), 
					instructionBean.getTemplate(), instructionBean.getName()));
			TemplateBean templateBean = configurationReaderUtil.getTemplate(instructionBean.getType(), instructionBean.getTemplate());
			
			// checking if the JiraIssueBean contains all the necessary data that are needed for generating the artefact(s)
			//   (e.g. Java POJO - a method is needed if mandatory = true)
			examineJiraIssueBean(instructionBean, templateBean, i);

			// looping through the template files defined in the template config XML
			for(TemplateFileBean templateFileBean : templateBean.getFiles()) {
				// creating the datamodel from JiraIssueBean for Freemarker
				final Map<String, Object> dataModel = creatingDataModel(instructionBean, templateFileBean, templateBean.getProperties());

				// getting the template
				final Template template = getTemplate(templateFileBean, cfg);

				// getting the path + name of the artefact
				final String artefactFileName = getArtefactFileName(instructionBean, templateFileBean, templateBean.getProperties());
				
				// I should create a VirtualWriter or something like this which won't create the file immediately but
				//    collect the FileWriter -> to check if each file will be generated
				VirtualWriter virtualWriter = new VirtualWriter("FILE", artefactFileName) {
					@Override
					public void work() throws TemplateException, IOException {
						template.process(dataModel, new FileWriter(artefactFileName));
					}
				};
				virtualWriterManager.add(virtualWriter);
				
				// creating the artefact virtually
				template.process(dataModel, virtualWriter);
			}
		}

		// creating the artefacts for real
		virtualWriterManager.process();
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

	
	private String getArtefactFileName(InstructionBean instructionBean, TemplateFileBean templateFileBean, List<TemplatePropertyBean> templatePropertyBeans) throws IOException, ConfigurationException {
		String _pathProject = configurationReaderUtil.getProjects(instructionBean.getProject()).getValue();
		final String pathProject = _pathProject.endsWith("/") ? _pathProject : _pathProject + "/";
		
		// if the path of the project doesn't exist then throw an exception
		if (pathProject == null || pathProject.trim().length() == 0) {
			throw new ConfigurationException(String.format("The path of the project %s is empty in the config file!", instructionBean.getProject()));
		}
		// check if the path of the project exists
		if (!new File(pathProject).exists()) {
			throw new ConfigurationException(String.format("The project %s on the path %s does not exist!", instructionBean.getProject(), pathProject));
		}
		
		// getting the location of the artefact file
		final String location = getLocation(instructionBean.getProperties(), templateFileBean.getName(), templatePropertyBeans, instructionBean.getLocation());
		
		// checking if the location folder exists in the project
		if (!new File(pathProject + location).exists()) {
			// if it doesn't exist then create the folder virtually
			LOGGER.debug(String.format("The folder %s is going to be created at the end of the process...", pathProject + location));
			virtualWriterManager.add(new VirtualWriter("FOLDER", pathProject + location) {
				@Override
				public void work() throws TemplateException, IOException {
					StringBuilder pathArtefact = new StringBuilder();
					for(String folder : (pathProject + location).split("/")) {
						pathArtefact.append(folder).append("/");
						if (!new File(pathArtefact.toString()).exists()) {							
							new File(pathArtefact.toString()).mkdir();
						}
					}
				}
			});
		}
		
		// building the name of the file to be created
		String artefactFileName = pathProject + location;
		artefactFileName = artefactFileName.endsWith("/") ? artefactFileName : artefactFileName+"/";
		artefactFileName = artefactFileName + 
				           (templateFileBean.getPrefix() == null ? "" : templateFileBean.getPrefix()) +
                           instructionBean.getName() +
				           (templateFileBean.getSuffix() == null ? "" : templateFileBean.getSuffix()) +
                           "." +
                           templateFileBean.getExtension();
		
		LOGGER.debug(String.format("The name of the file to be generated is %s.", artefactFileName));
		
		// checking if the artefact file exists -> it cannot be overridden
		if (new File(artefactFileName).exists()) {
			throw new ConfigurationException(String.format("The artefact file %s exists, it cannot be overridden!", artefactFileName));
		}
		
		return artefactFileName;
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

	
	private Map<String, Object> creatingDataModel(InstructionBean instructionBean, TemplateFileBean templateFileBean, List<TemplatePropertyBean> templatePropertyBeans) throws ConfigurationException {
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
		
		// setting the hm.package from location
		String packageName = getPackageNameFromLocation(instructionBean, templateFileBean, templatePropertyBeans);
		dataModelHm.put("package", packageName);
		
		return dataModelRoot;
	}


	private String getPackageNameFromLocation(InstructionBean instructionBean, TemplateFileBean templateFileBean, 
			List<TemplatePropertyBean> templatePropertyBeans) throws ConfigurationException {
		String location = getLocation(instructionBean.getProperties(), templateFileBean.getName(), templatePropertyBeans, instructionBean.getLocation());
		String srcFolder = configurationReaderUtil.getProjects(instructionBean.getProject()).getSrcFolder();
		String packageName = location.replaceFirst(srcFolder, "").replace("/", ".");
		if (packageName.length() > 0) {
			packageName = packageName.charAt(0) == '.' ? packageName.substring(1) : packageName;
		}
		if (packageName.length() > 0) {
			packageName = packageName.charAt(packageName.length()-1) == '.' ? packageName.substring(0,packageName.length()-1) : packageName;
		}
		return packageName;
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


	public static void main(String[] args) {
		String location = "src/main/java.asd";
		String srcFolder = "src/main/java";
		String packageName = location.replaceFirst(srcFolder, "").replace("/", ".");
		packageName = packageName.charAt(0) == '.' ? packageName.substring(1) : packageName;
		packageName = packageName.charAt(packageName.length()-1) == '.' ? packageName.substring(0,packageName.length()-1) : packageName;
		System.out.println("-"+packageName+"-");
	}


	public void setVirtualWriterManager(VirtualWriterManager virtualWriterManager) {
		this.virtualWriterManager = virtualWriterManager;
	}
}
