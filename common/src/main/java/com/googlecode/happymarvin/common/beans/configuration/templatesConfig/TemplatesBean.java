package com.googlecode.happymarvin.common.beans.configuration.templatesConfig;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="templates")
public class TemplatesBean implements Serializable {

	
	private static final long serialVersionUID = 7618177802201779014L;
	
	@ElementList(inline=true, required=false, entry="template")
	private List<TemplateBean> template = null;

	
	public List<TemplateBean> getTemplate() {
		return template;
	}

	public void setTemplate(List<TemplateBean> template) {
		this.template = template;
	}

}
