package com.googlecode.happymarvin.orchestrator.operation;

import java.io.IOException;

import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;

import freemarker.template.TemplateException;

public interface IOperation {

	void perform(String... args) throws IOException, InvalidInstructionException, ConfigurationException, TemplateException, ClassNotFoundException, InstantiationException, IllegalAccessException;
	
}
