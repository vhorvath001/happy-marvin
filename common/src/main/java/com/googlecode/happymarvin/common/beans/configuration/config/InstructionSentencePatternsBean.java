package com.googlecode.happymarvin.common.beans.configuration.config;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class InstructionSentencePatternsBean {

	@ElementList(inline=true, required=true, entry="sentence1")
	private List<String> sentence1s = null;

	@ElementList(inline=true, required=false, entry="sentence2")
	private List<String> sentence2s = null;

	@ElementList(inline=true, required=false, entry="sentence3")
	private List<String> sentence3s = null;

	@ElementList(inline=true, required=false, entry="sentence4")
	private List<String> sentence4s = null;

	@ElementList(inline=true, required=false, entry="sentence5")
	private List<String> sentence5s = null;

	public List<String> getSentence1s() {
		return sentence1s;
	}

	public void setSentence1s(List<String> sentence1s) {
		this.sentence1s = sentence1s;
	}

	public List<String> getSentence2s() {
		return sentence2s;
	}

	public void setSentence2s(List<String> sentence2s) {
		this.sentence2s = sentence2s;
	}

	public List<String> getSentence3s() {
		return sentence3s;
	}

	public void setSentence3s(List<String> sentence3s) {
		this.sentence3s = sentence3s;
	}

	public List<String> getSentence4s() {
		return sentence4s;
	}

	public void setSentence4s(List<String> sentence4s) {
		this.sentence4s = sentence4s;
	}

	public List<String> getSentence5s() {
		return sentence5s;
	}

	public void setSentence5s(List<String> sentence5s) {
		this.sentence5s = sentence5s;
	}

}
