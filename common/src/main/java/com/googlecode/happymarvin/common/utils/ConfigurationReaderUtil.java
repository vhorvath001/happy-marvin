package com.googlecode.happymarvin.common.utils;

import java.io.InputStream;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.googlecode.happymarvin.common.beans.configuration.config.ConfigBean;
import com.googlecode.happymarvin.common.beans.configuration.config.InstructionSentencePatternsBean;
import com.googlecode.happymarvin.common.beans.configuration.templatesConfig.TemplateBean;
import com.googlecode.happymarvin.common.beans.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.beans.configuration.templatesConfig.TemplatesBean;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;

public class ConfigurationReaderUtil {

	
	private static TemplatesBean templatesBean = null;
	private static ConfigBean configBean = null;
	private static final Object MONITOR_CONFIG_TEMPLATES = new Object();
	private static final Object MONITOR_CONFIG = new Object();
	
	
	private static void initTemplateConfig() throws InvalidInstructionException {
		if (templatesBean == null) {
			synchronized (MONITOR_CONFIG_TEMPLATES) {
				if (templatesBean == null) {
					Serializer serializer = new Persister();
			
					InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.PATH_TEMPLATE_CONFIG_XML);
					try {
						templatesBean = serializer.read(TemplatesBean.class, in);
					} catch (Exception e) {
						throw new InvalidInstructionException(String.format("The template config file %s cannot be read!", Constants.PATH_TEMPLATE_CONFIG_XML));
					}
				}
			}
		}
	}
	
	
	private static void initConfig() throws InvalidInstructionException {
		if (configBean == null) {
			synchronized (MONITOR_CONFIG) {
				if (configBean == null) {
					Serializer serializer = new Persister();
			
					InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.PATH_CONFIG_XML);
					try {
						configBean = serializer.read(ConfigBean.class, in);
					} catch (Exception e) {
						throw new InvalidInstructionException(String.format("The config file %s cannot be read!", Constants.PATH_CONFIG_XML));
					}
				}
			}
		}
	}

	
	public static List<TemplatePropertyBean> getTemplateProperties(String type, String templateName) throws InvalidInstructionException {
		initTemplateConfig();
		
		List<TemplatePropertyBean> properties = null;
		
		for(TemplateBean template : templatesBean.getTemplate()) {
			if (template.getName().equals(template) && template.getType().equals(type)) {
				properties = template.getProperties();
				break;
			}
		}
		
		return properties;
	}
	
	
	public static List<InstructionSentencePatternsBean> getSentencePatternsOfInstructions() throws InvalidInstructionException {
		initConfig();
		
		return configBean.getInstructionSentencePatterns();
	}
	
}
