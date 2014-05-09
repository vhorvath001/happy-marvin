package com.googlecode.happymarvin.webutility.beans;

import java.util.List;
import java.util.Map;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;

public class InstructionCreateInputBean {

	private Map<String, Map<String, List<TemplatePropertyBean>>> typeTemplatesTextfields;
	private List<String> defaultSentences;
	private Map<String, List<String>> templateDependantSentences;
	private String instructionSeparationStart;
	private String instructionSeparationEnd;

	public Map<String, Map<String, List<TemplatePropertyBean>>> getTypeTemplatesTextfields() {
		return typeTemplatesTextfields;
	}
	public void setTypeTemplatesTextfields(Map<String, Map<String, List<TemplatePropertyBean>>> typeTemplatesTextfields) {
		this.typeTemplatesTextfields = typeTemplatesTextfields;
	}
	public List<String> getDefaultSentences() {
		return defaultSentences;
	}
	public void setDefaultSentences(List<String> defaultSentences) {
		this.defaultSentences = defaultSentences;
	}
	public Map<String, List<String>> getTemplateDependantSentences() {
		return templateDependantSentences;
	}
	public void setTemplateDependantSentences(
			Map<String, List<String>> templateDependantSentences) {
		this.templateDependantSentences = templateDependantSentences;
	}
	public String getInstructionSeparationStart() {
		return instructionSeparationStart;
	}
	public void setInstructionSeparationStart(String instructionSeparationStart) {
		this.instructionSeparationStart = instructionSeparationStart;
	}
	public String getInstructionSeparationEnd() {
		return instructionSeparationEnd;
	}
	public void setInstructionSeparationEnd(String instructionSeparationEnd) {
		this.instructionSeparationEnd = instructionSeparationEnd;
	}
	
}