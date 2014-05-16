package com.googlecode.happymarvin.common.beans.simplexml.configuration.config;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="config")
public class ConfigBean implements Serializable {

	private static final long serialVersionUID = -4744741321829343113L;

	@Element(name="patternType")
	private String patternType;
	
	@Element(name="instructionSeparationStart")
	private String instructionSeparationStart;

	@Element(name="instructionSeparationEnd")
	private String instructionSeparationEnd;

	@ElementList(inline=true, required=false, entry="instructionSentencePatterns")
	private List<InstructionSentencePatternsBean> instructionSentencePatterns;

	@ElementList(inline=true, required=false, entry="project")
	private List<ProjectBean> projects;
	
	@Element(name="jiraRestUrl")
	private String jiraRestUrl;

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

	public String getInstructionSeparationStart() {
		return instructionSeparationStart;
	}

	public void setInstructionSeparationStart(String instructionSeparationStart) {
		this.instructionSeparationStart = instructionSeparationStart;
	}

	public String getInstructionSeparationEnd() {
		return instructionSeparationEnd;
	}

	public void setInstructionSeparationEnd(String instructionSeparationEnd) {
		this.instructionSeparationEnd = instructionSeparationEnd;
	}

	public String getJiraRestUrl() {
		return jiraRestUrl;
	}

	public void setJiraRestUrl(String jiraRestUrl) {
		this.jiraRestUrl = jiraRestUrl;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("patternType=").append(patternType);
		sb.append(",instructionSeparationStart=").append(instructionSeparationStart);
		sb.append(",instructionSeparationEnd=").append(instructionSeparationEnd);
		sb.append(",jiraRestUrl=").append(jiraRestUrl);
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
