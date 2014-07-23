package com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;

public class TemplateExtractedPropertyBean implements Serializable {
	
	private static final long serialVersionUID = 7648547724472600426L;

	public TemplateExtractedPropertyBean() {}
	
	public TemplateExtractedPropertyBean(TemplateExtractedPropertyBean source) {
		name = source.name;
		where = source.where;
		from = source.from;
	}
	
	@Attribute
	private String name;

	@Attribute
	private String where;

	@Attribute
	private String from;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("name=").append(name);
		sb.append(",where=").append(where);
		sb.append(",from=").append(from);
		sb.append("]");
		return sb.toString();
	}

}
