package com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class TemplateBean implements Serializable {


	private static final long serialVersionUID = 4312402107703840825L;
	

	@Attribute
	private String name = null;

	@Attribute
	private String type = null;
	
	@Element(name="file")
	private String file = null;
	
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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
		sb.append(",file=").append(file);
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
