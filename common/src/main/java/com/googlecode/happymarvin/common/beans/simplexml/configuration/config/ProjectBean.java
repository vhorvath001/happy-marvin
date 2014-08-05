package com.googlecode.happymarvin.common.beans.simplexml.configuration.config;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;

public class ProjectBean implements Serializable {

	private static final long serialVersionUID = -2098680058106072116L;

	public ProjectBean() { }
	
	public ProjectBean(ProjectBean source) {
		name = source.name;
		value = source.value;
		srcFolder = source.srcFolder;
	}
	
	@Attribute
	private String name;

	@Attribute
	private String value;

	@Attribute
	// it is necessary as the package field at Java classes are generated from the location but the location
	//   contains something like 'src/main/java/com/acme/ib/cp/tlem/validationFailuresReport/utils' so we 
	//   need the path of the source folder -> 'src/main/java' in this case
	private String srcFolder;

	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

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
		sb.append(",srcFolder=").append(srcFolder);
		sb.append("]");
		return sb.toString();
	}
	
}
