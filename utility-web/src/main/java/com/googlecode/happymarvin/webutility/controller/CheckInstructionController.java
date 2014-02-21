package com.googlecode.happymarvin.webutility.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/instruction")
public class CheckInstructionController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckInstructionController.class);
	
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String getInstructionCheckPage() {
		LOGGER.debug("Received GET request to show the instruction check page.");
		return "instruction-check-page";
	}
	
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody String checkInstruction(@RequestParam(value="instructionText", required=true) String instructionText,
			                                     Model model) {
		LOGGER.debug(String.format("Received POST request to check the instruction. instruction:\n%s", instructionText));
		return "xxx";
	}
	
}
