package com.googlecode.happymarvin.artefactgenerator.writer;

import java.io.IOException;
import java.io.Writer;

import freemarker.template.TemplateException;


public abstract class VirtualWriter extends Writer {
	
	
	private String artefactType;
	private String artefactName;
	
	
	public VirtualWriter(String artefactType, String artefactName) {
		this.artefactType = artefactType;
		this.artefactName = artefactName;
	}

	
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException { }

	@Override
	public void flush() throws IOException { }

	@Override
	public void close() throws IOException { }

	
	public abstract void work() throws TemplateException, IOException;


	public String getArtefactType() {
		return artefactType;
	}

	public String getArtefactName() {
		return artefactName;
	}


}
