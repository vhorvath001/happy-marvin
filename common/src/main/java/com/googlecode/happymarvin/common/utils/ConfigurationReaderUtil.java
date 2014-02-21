package com.googlecode.happymarvin.common.utils;

import java.io.InputStream;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.core.io.ClassPathResource;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.config.ConfigBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.config.InstructionSentencePatternsBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.config.ProjectBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatesBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;

public class ConfigurationReaderUtil {

	
	private TemplatesBean templatesBean = null;
	private ConfigBean configBean = null;
	private static final Object MONITOR_CONFIG_TEMPLATES = new Object();
	private static final Object MONITOR_CONFIG = new Object();
	
	private String pathConfigFile = null;
	private String pathTemplateConfigFile = null;
	
	
	private void initTemplateConfig() throws ConfigurationException {
		if (templatesBean == null) {
			synchronized (MONITOR_CONFIG_TEMPLATES) {
				if (templatesBean == null) {
					Serializer serializer = new Persister();
			
					try {
						InputStream in = new ClassPathResource(pathTemplateConfigFile).getInputStream();
						templatesBean = serializer.read(TemplatesBean.class, in);
					} catch (Exception e) {
						throw new ConfigurationException(String.format("The template config file %s cannot be read or incorrect template config file!", pathTemplateConfigFile), e);
					}
				}
			}
		}
	}
	
	
	private void initConfig() throws ConfigurationException {
		if (configBean == null) {
			synchronized (MONITOR_CONFIG) {
				if (configBean == null) {
					Serializer serializer = new Persister();
			
					try {
						InputStream in = new ClassPathResource(pathConfigFile).getInputStream();
						configBean = serializer.read(ConfigBean.class, in);
					} catch (Exception e) {
						throw new ConfigurationException(String.format("The config file %s cannot be read or incorrect config file!", pathConfigFile), e);
					}
				}
			}
		}
	}

	
	public List<TemplatePropertyBean> getTemplateProperties(String type, String template) throws ConfigurationException {
		initTemplateConfig();
		
		List<TemplatePropertyBean> properties = null;
		
		for(TemplateBean templateBean : templatesBean.getTemplate()) {
			if (templateBean.getName().equals(template) && templateBean.getType().equals(type)) {
				properties = templateBean.getProperties();
				break;
			}
		}
		
		return properties;
	}
	
	
	public TemplateBean getTemplate(String type, String template) throws ConfigurationException {
		initTemplateConfig();
		
		for(TemplateBean templateBean : templatesBean.getTemplate()) {
			if (templateBean.getName().equals(template) && templateBean.getType().equals(type)) {
				return templateBean;
			}
		}
		
		throw new ConfigurationException(String.format("The template (type=%s, template=%s) cannot be found!", type, template));
	}	
	
	
	public List<InstructionSentencePatternsBean> getSentencePatternsOfInstructions() throws ConfigurationException {
		initConfig();
		
		return configBean.getInstructionSentencePatterns();
	}


	public ProjectBean getProjects(String nameProject) throws ConfigurationException {
		initConfig();
		
		for(ProjectBean projectBean : configBean.getProjects()) {
			if (projectBean.getName().equals(nameProject)) {
				return projectBean;
			}
		}
		
		throw new ConfigurationException(String.format("The project %s cannot be found in the config file!", nameProject));
	}

	
	public String getPatternType() throws ConfigurationException {
		initConfig();
		return configBean.getPatternType();
	}
	
	
	public void setPathConfigFile(String pathConfigFile) {
		this.pathConfigFile = pathConfigFile;
	}


	public void setPathTemplateConfigFile(String pathTemplateConfigFile) {
		this.pathTemplateConfigFile = pathTemplateConfigFile;
	}
	
}
