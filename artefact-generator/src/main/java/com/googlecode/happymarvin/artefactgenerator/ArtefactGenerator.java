package com.googlecode.happymarvin.artefactgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.artefactgenerator.extractor.ExtractorI;
import com.googlecode.happymarvin.artefactgenerator.writer.VirtualWriter;
import com.googlecode.happymarvin.artefactgenerator.writer.VirtualWriterManager;
import com.googlecode.happymarvin.common.beans.InstructionBean;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateExtractedPropertyBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateFileBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.common.utils.Constants;
import com.googlecode.happymarvin.common.utils.StringUtility;

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
	// first must examine if all the artefacts can be generated (without creating the files!) and if they can
	//      then do the creation of the files! (e.g. there is an exception while creating the 2nd file then the first 
	//      file has been already created but not the others)
	// DONE package in the Java classes! from location
	public void generate(JiraIssueBean jiraIssueBean, boolean generate) throws IOException, TemplateException, InvalidInstructionException, ConfigurationException, 
			ClassNotFoundException, InstantiationException, IllegalAccessException {
		// initializing the Freemarker configuration
		Configuration cfg = init();
		
		// looping through all the instructions
		if (jiraIssueBean.getInstructions().size() == 0) {
			throw new InvalidInstructionException("There is no instruction in the JIRA!");
		}
		for(int i = 0; i < jiraIssueBean.getInstructions().size(); i++) {
			InstructionBean instructionBean = jiraIssueBean.getInstructions().get(i);
			LOGGER.info(String.format("Processing the %s instruction - type = %s, template = %s, name = %s", StringUtility.ordinal(i), instructionBean.getType(), 
					instructionBean.getTemplate(), instructionBean.getName()));
			TemplateBean templateBean = configurationReaderUtil.getTemplate(instructionBean.getType(), instructionBean.getTemplate());
			
			// checking if the JiraIssueBean contains all the necessary data that are needed for generating the artefact(s)
			//   (e.g. Java POJO - a method is needed if mandatory = true)
			examineJiraIssueBean(instructionBean, templateBean, i);
			
			// looping through the template files defined in the template config XML
			Queue<TemplateFileBean> queueFiles = new LinkedList<TemplateFileBean>(templateBean.getFiles());
			while (!queueFiles.isEmpty()) {
				TemplateFileBean templateFileBean = queueFiles.remove();
				LOGGER.debug(String.format("The next template file in the queue is %s", templateFileBean.toString()));
			
				// creating the datamodel from JiraIssueBean for Freemarker
				final Map<String, Object> dataModel = creatingDataModel(instructionBean, templateFileBean, templateBean.getProperties(), templateBean.getExtractedProperties());

				// getting the template
				final Template template = getTemplate(templateFileBean, cfg);

				// getting the path + name of the artefact
				final String artefactFileName = getArtefactFileName(i, instructionBean, templateFileBean, templateBean.getProperties(), generate);
				
				// I should create a VirtualWriter or something like this which won't create the file immediately but
				//    collect the FileWriter -> to check if each file will be generated
				VirtualWriter virtualWriter = new VirtualWriter("FILE", artefactFileName) {
					@Override
					public void work() throws TemplateException, IOException {
						template.process(dataModel, new FileWriter(artefactFileName));
					}
				};
				virtualWriterManager.add(i, virtualWriter);
				
				// creating the artefact virtually
				template.process(dataModel, virtualWriter);
				
				// adding a new TemplateFileBean to the queue if the artefactsToBeGenerated property of templateFileBean is not empty in hm-templates-config.xml
				addNewTemplateFileBeanIfNeeded(queueFiles, templateFileBean);
			}
		}

		// creating the artefacts for real
		if (generate) {
			virtualWriterManager.process();
		}
	}

	
	private void addNewTemplateFileBeanIfNeeded(Queue<TemplateFileBean> queueFiles, TemplateFileBean templateFileBean) throws ConfigurationException {
		if (templateFileBean.getAdditionalArtefactsToBeGenerated() != null) {
			// if the additional file to be generated is UnitTest
			if (templateFileBean.getAdditionalArtefactsToBeGenerated().equals(Constants.TEMPLATE_FILE_ADDITIONALARTEFACTSTOBEGENERATED_UNIT_TEST)) {
				TemplateFileBean additionalArtefactsToBeGenerated = new TemplateFileBean(templateFileBean);
				String path = additionalArtefactsToBeGenerated.getPath();
				// the template file name must end with the text _UnitTest
				//    for example: if the original file path is Java/POJO.ftl then the new file path will be Java/POJO_UnitTest.ftl
				if (path.lastIndexOf(Constants.TEMPLATE_FILE_EXTENSION) == -1) {
					throw new ConfigurationException(String.format("The extension of the file must be %s! path=%s", Constants.TEMPLATE_FILE_EXTENSION, templateFileBean.getPath()));
				}
				path = path.substring(0, path.lastIndexOf(Constants.TEMPLATE_FILE_EXTENSION)-1) + "_" + Constants.TEMPLATE_FILE_ADDITIONALARTEFACTSTOBEGENERATED_UNIT_TEST + "." + Constants.TEMPLATE_FILE_EXTENSION;
				
				additionalArtefactsToBeGenerated.setPath(path);
				additionalArtefactsToBeGenerated.setAdditionalArtefactsToBeGenerated(null);
				additionalArtefactsToBeGenerated.setSuffix((additionalArtefactsToBeGenerated.getSuffix() == null ? "" : additionalArtefactsToBeGenerated.getSuffix()) + 
						Constants.JAVA_UNITTEST_SUFFIX);
				additionalArtefactsToBeGenerated.setType(Constants.TEMPLATE_FILE_ADDITIONALARTEFACTSTOBEGENERATED_UNIT_TEST);
				
				queueFiles.add(additionalArtefactsToBeGenerated);
			} else {
				throw new ConfigurationException(String.format("Incorrect value in the property additionalArtefactsToBeGenerated! path=%s, incorrect value=%s", templateFileBean.getPath(), 
					templateFileBean.getAdditionalArtefactsToBeGenerated()));
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

	
	private String getArtefactFileName(int ind, InstructionBean instructionBean, TemplateFileBean templateFileBean, List<TemplatePropertyBean> templatePropertyBeans, boolean generate) throws IOException, ConfigurationException {
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
		String location = getLocation(instructionBean.getProperties(), templateFileBean.getName(), templatePropertyBeans, instructionBean.getLocation());
		
		// if this is a unit test then the artefact should be put to src/test/java/... instead of src/main/java/
		//    if the location doesn't contain the src/main/java then do nothing i.e. the location of the unit test will be the same the location of the normal class
		if (templateFileBean.getType() != null && templateFileBean.getType().equals(Constants.TEMPLATE_FILE_ADDITIONALARTEFACTSTOBEGENERATED_UNIT_TEST)) {
			location = location.replace(Constants.JAVA_DEFAULT_SRC_FOLDER, Constants.JAVA_DEFAULT_TEST_FOLDER);
		}

		final String locationFinal = location;
		// checking if the location folder exists in the project
		if (!new File(pathProject + locationFinal).exists()) {
			// if it doesn't exist then create the folder virtually
			LOGGER.debug(String.format("The folder %s is going to be created at the end of the process...", pathProject + locationFinal));
			virtualWriterManager.add(ind, new VirtualWriter("FOLDER", pathProject + locationFinal) {
				@Override
				public void work() throws TemplateException, IOException {
					StringBuilder pathArtefact = new StringBuilder();
					for(String folder : (pathProject + locationFinal).split("/")) {
						pathArtefact.append(folder).append("/");
						File folderPath = new File(pathArtefact.toString());
						if (!folderPath.exists()) {							
							folderPath.mkdir();
						}
					}
				}
			});
		}
		
		// building the name of the file to be created
		String artefactFileName = pathProject + locationFinal;
		artefactFileName = artefactFileName.endsWith("/") ? artefactFileName : artefactFileName+"/";
		artefactFileName = artefactFileName + 
				           (templateFileBean.getPrefix() == null ? "" : templateFileBean.getPrefix()) +
                           instructionBean.getName() +
				           (templateFileBean.getSuffix() == null ? "" : templateFileBean.getSuffix()) +
                           "." +
                           templateFileBean.getExtension();
		
		LOGGER.debug(String.format("The name of the file to be generated is %s.", artefactFileName));
		
		// checking if the artefact file exists -> it cannot be overridden
		if (generate && new File(artefactFileName).exists()) {
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

	
	private Map<String, Object> creatingDataModel(InstructionBean instructionBean, TemplateFileBean templateFileBean, List<TemplatePropertyBean> templatePropertyBeans,
			List<TemplateExtractedPropertyBean> templateExtractedProperties) throws ConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
		dataModelHm.put("location", instructionBean.getLocation().endsWith("/") ? instructionBean.getLocation() : instructionBean.getLocation()+"/");

		// setting the properties
		for(String name : instructionBean.getProperties().keySet()) {
			dataModelProperties.put(name, instructionBean.getProperties().get(name));
		}
		
		// setting the extracted properties
		dataModelHm.put("extractedProperty", getExtractedProperties(templateFileBean.getExtractorClass(), templateExtractedProperties, instructionBean.getProperties()));
		
		// setting the hm.package from location
		String packageName = getPackageNameFromLocation(instructionBean, templateFileBean, templatePropertyBeans);
		dataModelHm.put("package", packageName);
		
		// setting the file prefix and suffix
		Map<String, Object> dataModelFile = new HashMap<String, Object>();
		dataModelHm.put("file", dataModelFile);
		dataModelFile.put("prefix", templateFileBean.getPrefix());
		dataModelFile.put("suffix", templateFileBean.getSuffix());
		
		return dataModelRoot;
	}


	private Object getExtractedProperties(String extractorClassString, List<TemplateExtractedPropertyBean> templateExtractedPropertyBeans,
			Map<String, String> properties) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// if the extractorClass doesn't exist then no need to return any extracted property as they won't be used in the template
		if (extractorClassString == null) {
			return null;
		}
		
//		// extractorString = com.googlecode.happymarvin.artefactgenerator.extractor.XmlExtractor($proxy_wsdl_path)
//		// check if the value in the extractorString is correct
//		Pattern pattern = Pattern.compile(".+[(][$].+[)]");   // starting with a $, following a {, after those any character except $ and {, and ending with }
//		Matcher matcher = pattern.matcher((CharSequence) extractorString);
//		int count = 0;
//	    if (!matcher.find()) {
//	    	throw new ConfigurationException("Incorrect value in the extractorClass attribute!");
//	    }
//
//	    // getting the values
//		String classNameExtractor = extractorString.substring(extractorString.indexOf("(")-1);
//		String propertyName = extractorString.substring(extractorString.indexOf("(")+2, extractorString.length()-1);
		
		// executing the extractor class
		Class<ExtractorI> extractorClass = (Class<ExtractorI>) Class.forName(extractorClassString);
		ExtractorI extractorInstance = extractorClass.newInstance();
		return extractorInstance.extract(templateExtractedPropertyBeans, properties);
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


	public void setVirtualWriterManager(VirtualWriterManager virtualWriterManager) {
		this.virtualWriterManager = virtualWriterManager;
	}

	public VirtualWriterManager getVirtualWriterManager() {
		return virtualWriterManager;
	}
}
