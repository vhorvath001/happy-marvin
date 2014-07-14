package com.googlecode.happymarvin.artefactgenerator.extractor;

import java.util.List;
import java.util.Map;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateExtractedPropertyBean;

public interface ExtractorI {

	Map<String, String> extract(List<TemplateExtractedPropertyBean> templateExtractedPropertyBeans, Map<String, String> properties);
	
}
