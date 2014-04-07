package com.googlecode.happymarvin.webutility.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.webutility.beans.ExceptionBean;


@Controller
@RequestMapping("/instruction")
public class CreateInstructionController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateInstructionController.class);
	
	private ConfigurationReaderUtil configurationReaderUtil = null;
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String getInstructionCheckPage() throws ConfigurationException {
		LOGGER.debug("Received GET request to show the instruction create page.");
		return "/instruction/create.tiles";
	}
	
	
	@RequestMapping(value = "/create/loadData", method = RequestMethod.POST)
	public @ResponseBody Map<String, Set<String>> loadData() {
		try {
			// building the type-template-... list to pass to the jquery wizard
			Map<String, Set<String>> typeTemplateMap = new TreeMap<String, Set<String>>();
			for(TemplateBean template : configurationReaderUtil.getTemplates()) {
				String type = template.getType();
				String templateName = template.getName();
				Set<String> templateList = new TreeSet<String>();
				if (typeTemplateMap.get(type) == null) {
					typeTemplateMap.put(type, new TreeSet<String>());
				} else {
					templateList = typeTemplateMap.get(type);
				}
				templateList.add(templateName);
				typeTemplateMap.put(type, templateList);
			}
			
		} catch(Exception e) {
			LOGGER.error("Error occurred at getInstructionCheckPage()!", e);
			throw new RuntimeException(e.getClass().getSimpleName() + " : " + e.getMessage());
		}
	}
	

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ExceptionBean handleException(RuntimeException e, HttpServletResponse re) {
		return new ExceptionBean(e.getMessage());
	}
	
}
