package com.googlecode.happymarvin.common.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.core.io.ClassPathResource;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.config.ConfigBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.config.InstructionSentencePatternsBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatesBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;

public class ConfigurationReaderUtil implements ConfigurationReaderUtilI {

	
	private static TemplatesBean templatesBean = null;
	private static ConfigBean configBean = null;
	private static final Object MONITOR_CONFIG_TEMPLATES = new Object();
	private static final Object MONITOR_CONFIG = new Object();
	
	
	private void initTemplateConfig() throws ConfigurationException {
		if (templatesBean == null) {
			synchronized (MONITOR_CONFIG_TEMPLATES) {
				if (templatesBean == null) {
					Serializer serializer = new Persister();
			
//					InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.PATH_TEMPLATE_CONFIG_XML);
					try {
						InputStream in = new ClassPathResource(Constants.PATH_TEMPLATE_CONFIG_XML).getInputStream();
						templatesBean = serializer.read(TemplatesBean.class, in);
					} catch (Exception e) {
						throw new ConfigurationException(String.format("The template config file %s cannot be read!", Constants.PATH_TEMPLATE_CONFIG_XML), e);
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
			
					//InputStream in = this.getClass().getResourceAsStream(Constants.PATH_CONFIG_XML);
					try {
						InputStream in = new ClassPathResource(Constants.PATH_CONFIG_XML).getInputStream();
						configBean = serializer.read(ConfigBean.class, in);
					} catch (Exception e) {
						throw new ConfigurationException(String.format("The config file %s cannot be read!", Constants.PATH_CONFIG_XML), e);
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
	
	
	public List<InstructionSentencePatternsBean> getSentencePatternsOfInstructions() throws ConfigurationException {
		initConfig();
		
		return configBean.getInstructionSentencePatterns();
	}
	
}
