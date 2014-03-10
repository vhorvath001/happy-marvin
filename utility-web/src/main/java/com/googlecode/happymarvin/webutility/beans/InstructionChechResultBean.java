package com.googlecode.happymarvin.webutility.beans;

import java.util.List;

import com.googlecode.happymarvin.common.beans.InstructionBean;

public class InstructionChechResultBean {

	
	private List<String[]> sentencePatternPairs = null;
	private InstructionBean instructionBean = null;
	
	
	public List<String[]> getSentencePatternPairs() {
		return sentencePatternPairs;
	}
	
	public void setSentencePatternPairs(List<String[]> sentencePatternPairs) {
		this.sentencePatternPairs = sentencePatternPairs;
	}
	
	public InstructionBean getInstructionBean() {
		return instructionBean;
	}
	
	public void setInstructionBean(InstructionBean instructionBean) {
		this.instructionBean = instructionBean;
	}
	
}
