package com.googlecode.happymarvin.common.beans.simplexml.configuration.config;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="config")
public class ConfigBean {

	@Element(name="patternType")
	private String patternType;
	
	@ElementList(inline=true, required=false, entry="instructionSentencePatterns")
	private List<InstructionSentencePatternsBean> instructionSentencePatterns;

	@ElementList(inline=true, required=false, entry="project")
	private List<ProjectBean> projects;

	public String getPatternType() {
		return patternType;
	}

	public void setPatternType(String patternType) {
		this.patternType = patternType;
	}

	public List<InstructionSentencePatternsBean> getInstructionSentencePatterns() {
		return instructionSentencePatterns;
	}

	public void setInstructionSentencePatterns(
			List<InstructionSentencePatternsBean> instructionSentencePatterns) {
		this.instructionSentencePatterns = instructionSentencePatterns;
	}

	public List<ProjectBean> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectBean> projects) {
		this.projects = projects;
	}	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("patternType=").append(patternType);
		if (instructionSentencePatterns != null && instructionSentencePatterns.size() > 0) {
			for(InstructionSentencePatternsBean bean : instructionSentencePatterns) {
				sb.append(bean).append(",");			
			}
		}
		if (projects != null && projects.size() > 0) {
			for(ProjectBean bean : projects) {
				sb.append(bean).append(",");			
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
