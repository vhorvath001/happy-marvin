package com.googlecode.happymarvin.webutility.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.jiraminer.IUndergroundMining;
import com.googlecode.happymarvin.webutility.beans.ExceptionBean;
import com.googlecode.happymarvin.webutility.beans.InstructionChechResultBean;


@Controller
@RequestMapping("/instruction")
public class CheckInstructionController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckInstructionController.class);
	
	@Autowired
	private IUndergroundMining undergroundMining;
	
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String getInstructionCheckPage() {
		LOGGER.debug("Received GET request to show the instruction check page.");
		return "/instruction/check.tiles";
	}
	

/*
~~~HAPPYMARVIN-INSTRUCTION~~~
I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'.
The name should be 'EmailSender'. You should put it into the 'src/main/java/com/acme/ib/cp/tlem/validationFailuresReport/utils' folder. Please add a method to it: 'int send(String emailTo, String filePath, String reportName)'
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
*/	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody List<InstructionChechResultBean> checkInstruction(@RequestParam(value="instructionText", required=true) String instructionText,
                                                                           Model model,
                                                                           HttpServletResponse response) 
                                  throws IOException, InvalidInstructionException, ConfigurationException {
		LOGGER.debug(String.format("Received POST request to check the instruction. instruction:\n%s", instructionText));
		
		// create jiraIssueBean
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		jiraIssueBean.setDescription(instructionText);
		
		// calling the UndergroundMining
		try {
			jiraIssueBean = undergroundMining.mine(jiraIssueBean);
		} catch(Exception e) {
			LOGGER.error("Error occurred when calling undergroundMining.mine(...)!", e);
			throw new RuntimeException(e.getClass().getSimpleName() + " : " + e.getMessage());
		}
		List<List<String[]>> sentencePatternPairs = undergroundMining.getSentencePatternPairs();
		
		// creating the result
		List<InstructionChechResultBean> instructionChechResultBeans = new ArrayList<InstructionChechResultBean>();
		for(int i = 0; i < jiraIssueBean.getInstructions().size(); i++) {
			instructionChechResultBeans.add(new InstructionChechResultBean());
			instructionChechResultBeans.get(i).setInstructionBean(jiraIssueBean.getInstructions().get(i));
			instructionChechResultBeans.get(i).setSentencePatternPairs(sentencePatternPairs.get(i));
		}
		
		return instructionChechResultBeans;
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ExceptionBean handleException(RuntimeException e, HttpServletResponse re) {
		return new ExceptionBean(e.getMessage());
	}
	
}
