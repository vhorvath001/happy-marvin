package com.googlecode.happymarvin.webutility.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.config.InstructionSentencePatternsBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.webutility.beans.ExceptionBean;
import com.googlecode.happymarvin.webutility.beans.InstructionCreateGetSentenceInputBean;
import com.googlecode.happymarvin.webutility.beans.InstructionCreateInputBean;


@Controller
@RequestMapping("/instruction")
public class CreateInstructionController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateInstructionController.class);
	private static final List<TemplatePropertyBean> DEFAULT_TEMPLATE_PROPERTY_BEANS = getDefaultTemplatePropertyBean();
	
	@Autowired
	private ConfigurationReaderUtil configurationReaderUtil = null;
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String getInstructionCheckPage() throws ConfigurationException {
		LOGGER.debug("Received GET request to show the instruction create page.");
		return "/instruction/create.tiles";
	}
	
	
	@RequestMapping(value = "/create/loadData", method = RequestMethod.POST)
	public @ResponseBody InstructionCreateInputBean loadData() {
		LOGGER.debug("Received POST request to load the data for displaying the tabs.");
		InstructionCreateInputBean instructionCreateInputBean = new InstructionCreateInputBean();
		try {
			// building the type-template-textfields list to pass to the jquery wizard
			Map<String, Map<String, List<TemplatePropertyBean>>> typeTemplateMap = new TreeMap<String, Map<String, List<TemplatePropertyBean>>>();
			for(TemplateBean template : configurationReaderUtil.getTemplates()) {
				String type = template.getType();
				String templateName = template.getName();
				Map<String, List<TemplatePropertyBean>> templateList = new TreeMap<String, List<TemplatePropertyBean>>();
				if (typeTemplateMap.get(type) == null) {
					typeTemplateMap.put(type, new TreeMap<String, List<TemplatePropertyBean>>());
				} else {
					templateList = typeTemplateMap.get(type);
				}
				templateList.put(templateName, getTextFields(type, templateName));
				typeTemplateMap.put(type, templateList);
			}
			instructionCreateInputBean.setTypeTemplatesTextfields(typeTemplateMap);
			
			// adding the default sentences
			List<String> sentences = new ArrayList<String>();
			for (InstructionSentencePatternsBean instructionSentencePatternsBean : configurationReaderUtil.getSentencePatternsOfInstructions()) {
				sentences.addAll(instructionSentencePatternsBean.getSentences());
			}
			instructionCreateInputBean.setDefaultSentences(sentences);
			
			// adding the property dependant sentences
			Map<String, List<String>> sentences2 = new HashMap<String, List<String>>();
			for (TemplateBean templateBean : configurationReaderUtil.getTemplates()) {
				String key = templateBean.getType() + "-" + templateBean.getName();
				sentences = new ArrayList<String>();
				for(TemplatePropertyBean templatePropertyBean : templateBean.getProperties()) {
					sentences.addAll(templatePropertyBean.getTextDefs());
				}
				sentences2.put(key, sentences);
			}
			instructionCreateInputBean.setTemplateDependantSentences(sentences2);
			
			return instructionCreateInputBean;
		} catch(Exception e) {
			LOGGER.error("Error occurred at getInstructionCheckPage()!", e);
			throw new RuntimeException(e.getClass().getSimpleName() + " : " + e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "create/getSentence", method = RequestMethod.POST)
	public String getSentence(@RequestBody InstructionCreateGetSentenceInputBean input) {
		LOGGER.debug(String.format("Received POST request to create the sentence. type:%s, template:%s", input.getType(), input.getTemplate()));
		return "This is not the RESULT yet...";
	}
	

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ExceptionBean handleException(RuntimeException e, HttpServletResponse re) {
		return new ExceptionBean(e.getMessage());
	}
	
	private static List<TemplatePropertyBean> getDefaultTemplatePropertyBean() {
		List<TemplatePropertyBean> templatePropertyBeans = new ArrayList<TemplatePropertyBean>();
		// project
		TemplatePropertyBean bean = new TemplatePropertyBean();
		bean.setMandatory("true");
		bean.setName("project");
		bean.setText("PROJECT");
		bean.setTextfieldLabel("Project");
		templatePropertyBeans.add(bean);
		// name
		bean = new TemplatePropertyBean();
		bean.setMandatory("true");
		bean.setName("name");
		bean.setText("NAME");
		bean.setTextfieldLabel("Name");
		templatePropertyBeans.add(bean);
		// location
		bean = new TemplatePropertyBean();
		bean.setMandatory("true");
		bean.setName("location");
		bean.setText("LOCATION");
		bean.setTextfieldLabel("Location");
		templatePropertyBeans.add(bean);
		
		return templatePropertyBeans;
	}


	private List<TemplatePropertyBean> getTextFields(String type, String templateName) throws ConfigurationException {
		List<TemplatePropertyBean> templatePropertyBeans = new ArrayList<TemplatePropertyBean>();
		templatePropertyBeans.addAll(DEFAULT_TEMPLATE_PROPERTY_BEANS);
		templatePropertyBeans.addAll(configurationReaderUtil.getTemplateProperties(type, templateName));
		return templatePropertyBeans;
	}


}
