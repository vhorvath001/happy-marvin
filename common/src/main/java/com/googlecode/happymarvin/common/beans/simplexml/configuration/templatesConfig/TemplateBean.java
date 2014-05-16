package com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class TemplateBean implements Serializable {

	private static final long serialVersionUID = 4312402107703840825L;
	
	public TemplateBean() { }
	
	public TemplateBean(TemplateBean source) {
		name = source.name;
		type = source.type;
		files = new ArrayList<TemplateFileBean>();
		for(TemplateFileBean templateFileBean : source.files) {
			files.add(new TemplateFileBean(templateFileBean));
		}
		properties = new ArrayList<TemplatePropertyBean>();
		for(TemplatePropertyBean templatePropertyBean : source.properties) {
			properties.add(new TemplatePropertyBean(templatePropertyBean));
		}
	}

	@Attribute
	private String name = null;

	@Attribute
	private String type = null;
	
	@ElementList(inline=true, required=true, entry="file")
	private List<TemplateFileBean> files = null;
	
	@ElementList(inline=true, required=false, entry="property")
	private List<TemplatePropertyBean> properties = null;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<TemplateFileBean> getFiles() {
		return files;
	}

	public void setFiles(List<TemplateFileBean> files) {
		this.files = files;
	}

	public List<TemplatePropertyBean> getProperties() {
		return properties;
	}

	public void setProperties(List<TemplatePropertyBean> properties) {
		this.properties = properties;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("name=").append(name);
		sb.append(",type=").append(type);
		sb.append("file=");
		if (files != null && files.size() > 0) {
			for(TemplateFileBean file : files) {
				sb.append(file).append(",");			
			}
		}
		sb.append("properties=");
		if (properties != null && properties.size() > 0) {
			for(TemplatePropertyBean property : properties) {
				sb.append(property).append(",");			
			}
		}
		sb.append("]");
		return sb.toString();
	}

}