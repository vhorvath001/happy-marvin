package com.googlecode.happymarvin.codegenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class CodeGenerator {

	public void generate(JiraIssueBean jiraIssueBean) throws IOException, TemplateException {
		Configuration cfg = init();
		
		Map<String, String> dataModel = creatingDataModel(jiraIssueBean);
		
		Template template = cfg.getTemplate("Java-POJO.ftl");
		
		Writer write = new FileWriter("C:/work/happy-marvin/temp/Valami.java");
		template.process(dataModel, write);
	}

	private Map<String, String> creatingDataModel(JiraIssueBean jiraIssueBean) {
		Map<String, String> dataModel = new HashMap<String, String>();
		dataModel.put("name", jiraIssueBean.getInstructions().get(0).getName());
		dataModel.put("method", jiraIssueBean.getInstructions().get(0).getProperties().get("method"));
		return dataModel;
	}

	private Configuration init() throws IOException {
		Configuration cfg = new Configuration();
		// Specify the data source where the template files come from.
		//cfg.setDirectoryForTemplateLoading(new File("templates"));
		cfg.setClassForTemplateLoading(this.getClass(), "/templates");
		// Specify how templates will see the data-model.
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		// Set your preferred charset template files are stored in. UTF-8 is a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");
		// Sets how errors will appear.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		
		return cfg;
	}
}
