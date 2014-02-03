package com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class TemplatePropertyBean implements Serializable {

	
	private static final long serialVersionUID = -7162034148754570245L;

	@Attribute
	private String name;

	@Attribute
	private String text;

	@ElementList(inline=true, required=false, entry="textDef")
	private List<String> textDefs;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getTextDefs() {
		return textDefs;
	}

	public void setTextDefs(List<String> textDefs) {
		this.textDefs = textDefs;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("name=").append(name);
		sb.append(",text=").append(text);
		sb.append(",textDefs=");
		if (textDefs != null && textDefs.size() > 0) {
			for(String textDef : textDefs) {
				sb.append(textDef).append(",");			
			}
		}
		sb.append("]");
		return sb.toString();
	}	
	
}
