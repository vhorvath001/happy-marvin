package com.googlecode.happymarvin.common.beans.simplexml.configuration.config;

import org.simpleframework.xml.Attribute;

public class ProjectBean {

	@Attribute
	private String name;

	@Attribute
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("name=").append(name);
		sb.append(",value=").append(value);
		sb.append("]");
		return sb.toString();
	}
	
}
