package com.googlecode.happymarvin.common.beans;

import java.util.List;

public class JiraIssueBean {

	private String description;
	private List<InstructionBean> instructions;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<InstructionBean> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<InstructionBean> instructions) {
		this.instructions = instructions;
	}
	
}