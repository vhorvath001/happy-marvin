package com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;

public class TemplateFileBean implements Serializable {


	private static final long serialVersionUID = 4312402107703840825L;
	

	@Attribute
	private String name = null;

	@Attribute
	private String extension = null;

	@Attribute
	private String path = null;

	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("name=").append(name);
		sb.append("extension=").append(extension);
		sb.append(",path=").append(path);
		sb.append("]");
		return sb.toString();
	}

}