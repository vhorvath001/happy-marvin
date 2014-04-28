package com.googlecode.happymarvin.webutility.beans;

import java.util.List;
import java.util.Map;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;

public class InstructionCreateInputBean {

	private Map<String, Map<String, List<TemplatePropertyBean>>> typeTemplatesTextfields;

	public Map<String, Map<String, List<TemplatePropertyBean>>> getTypeTemplatesTextfields() {
		return typeTemplatesTextfields;
	}

	public void setTypeTemplatesTextfields(Map<String, Map<String, List<TemplatePropertyBean>>> typeTemplatesTextfields) {
		this.typeTemplatesTextfields = typeTemplatesTextfields;
	}
	
}