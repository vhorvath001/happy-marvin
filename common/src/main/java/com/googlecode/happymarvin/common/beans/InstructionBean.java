package com.googlecode.happymarvin.common.beans;

import java.io.Serializable;
import java.util.Map;

public class InstructionBean implements Serializable {

	private static final long serialVersionUID = 3569491423268754849L;
	
	private String type;
	private String template;
	private String project;
	private String name;
	private String location;
	private Map<String, String> properties;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("type=").append(type);
		sb.append(",template=").append(template);
		sb.append(",project=").append(project);
		sb.append(",name=").append(name);
		sb.append(",location=").append(location);
		sb.append(",properties={");
		if (properties != null && properties.size() > 0) {
			for(String key : properties.keySet()) {
				sb.append(key).append("=").append(properties.get(key)).append(",");			
			}
		}
		sb.append("}]");
		return sb.toString();
	}	
	
}
