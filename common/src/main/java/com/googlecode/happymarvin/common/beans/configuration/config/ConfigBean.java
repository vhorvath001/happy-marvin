package com.googlecode.happymarvin.common.beans.configuration.config;

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

}
