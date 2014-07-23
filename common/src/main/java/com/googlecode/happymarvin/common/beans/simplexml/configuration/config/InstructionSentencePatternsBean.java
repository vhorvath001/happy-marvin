package com.googlecode.happymarvin.common.beans.simplexml.configuration.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class InstructionSentencePatternsBean implements Serializable {

	private static final long serialVersionUID = 3865526107518698371L;

	@ElementList(inline=true, required=true, entry="sentence")
	private List<String> sentences = null;

	public InstructionSentencePatternsBean() { }
	
	public InstructionSentencePatternsBean(InstructionSentencePatternsBean source) {
		sentences = new ArrayList<String>();
		for(String sent : source.getSentences()) {
			sentences.add(sent);
		}
	}
	
	public List<String> getSentences() {
		return sentences;
	}

	public void setSentences(List<String> sentences) {
		this.sentences = sentences;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[");
		sb.append("sentences=");
		if (sentences != null && sentences.size() > 0) {
			for(String sentence : sentences) {
				sb.append(sentence).append(",");			
			}
		}
		sb.append("]");
		return sb.toString();
	}	
	
}