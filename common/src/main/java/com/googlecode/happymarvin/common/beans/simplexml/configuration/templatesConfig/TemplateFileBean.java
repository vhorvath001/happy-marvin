package com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;

public class TemplateFileBean implements Serializable {


	private static final long serialVersionUID = 4312402107703840825L;
	
	public TemplateFileBean() { }
	
	public TemplateFileBean(TemplateFileBean source) {
		name = source.name;
		extension = source.extension;
		path = source.path;
		prefix = source.prefix;
		suffix = source.suffix;
		additionalArtefactsToBeGenerated = source.additionalArtefactsToBeGenerated;
	}
	
	@Attribute(required=false)
	private String name = null;

	@Attribute
	private String extension = null;

	@Attribute
	private String path = null;
	
	@Attribute(required=false)
	private String prefix = null;

	@Attribute(required=false)
	private String suffix = null;
	
	@Attribute(required=false)
	private String additionalArtefactsToBeGenerated = null;
	
	@Attribute(required=false)
	private String extractorClass;
	
	private transient String type = null;
	
	public String getExtractorClass() {
		return extractorClass;
	}

	public void setExtractorClass(String extractorClass) {
		this.extractorClass = extractorClass;
	}

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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getAdditionalArtefactsToBeGenerated() {
		return additionalArtefactsToBeGenerated;
	}

	public void setAdditionalArtefactsToBeGenerated(String additionalArtefactsToBeGenerated) {
		this.additionalArtefactsToBeGenerated = additionalArtefactsToBeGenerated;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("name=").append(name);
		sb.append("extension=").append(extension);
		sb.append(",path=").append(path);
		sb.append(",prefix=").append(prefix);
		sb.append(",suffix=").append(suffix);
		sb.append(",additionalArtefactsToBeGenerated=").append(additionalArtefactsToBeGenerated);
		sb.append(",extractorClass=").append(extractorClass);
		sb.append(",path=").append(path);
		sb.append("]");
		return sb.toString();
	}

}