package com.googlecode.happymarvin.artefactgenerator.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.artefactgenerator.ArtefactGenerator;

import freemarker.template.TemplateException;


// for creating files and folders
public class VirtualWriterManager {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtefactGenerator.class);
	
	private List<List<VirtualWriter>> writers = new ArrayList<List<VirtualWriter>>();
	
	
	public void add(int ind, VirtualWriter writer) {
		// checking if the the same artefact exists already in the writers list
		// if yes the do nothing
		for(List<VirtualWriter> writerList : writers) {
			for(VirtualWriter w : writerList) {
				if (w.getArtefactType().equals(writer.getArtefactType()) && w.getArtefactName().equals(writer.getArtefactName())) {
					return;
				}
			}
		}
		
		if (writers.size() < ind+1) {
			writers.add(ind, new ArrayList<VirtualWriter>());
		}
		writers.get(ind).add(writer);
	}
	
	
	public void process() throws TemplateException, IOException {
		LOGGER.info("Artefacts to be created:");
		for(List<VirtualWriter> writerList : writers) {
			for(VirtualWriter writer : writerList) {
				writer.work();
				LOGGER.info(String.format("\tType:%s\t\tName:%s", writer.getArtefactType(), writer.getArtefactName()));
			}
		}
	}

	
	public List<VirtualWriter> getVirtualWriters(int ind) {
		return writers.get(ind);
	}
	
}