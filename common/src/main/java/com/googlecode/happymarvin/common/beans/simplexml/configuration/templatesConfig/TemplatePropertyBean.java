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

	@Attribute
	private String mandatory;
	
	// this attribute indicates two things:
	//   - if the property exists as an attribute then it means that the property is a location type property and 
	//        it will replace the common location value
	//   - the value of the property means that the location belongs to which file
	@Attribute(required=false)
	private String locationOf;

	@ElementList(inline=true, required=false, entry="textDef")
	private List<String> textDefs;
	
	@Attribute(required=false)
	private String textfieldLabel;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocationOf() {
		return locationOf;
	}

	public void setLocationOf(String locationOf) {
		this.locationOf = locationOf;
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
	
	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("name=").append(name);
		sb.append(",text=").append(text);
		if (mandatory != null && mandatory.length() > 0) {
			sb.append(",mandatory=").append(mandatory);
		}
		if (locationOf != null && locationOf.length() > 0) {
			sb.append(",locationOf=").append(locationOf);
		}
		sb.append(",textDefs=");
		if (textDefs != null && textDefs.size() > 0) {
			for(String textDef : textDefs) {
				sb.append(textDef).append(",");			
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public String getTextfieldLabel() {
		return textfieldLabel;
	}

	public void setTextfieldLabel(String textfieldLabel) {
		this.textfieldLabel = textfieldLabel;
	}

}
