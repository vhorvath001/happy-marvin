package com.googlecode.happymarvin.artefactgenerator.extractor;

import java.util.Map;

public interface ExtractorI {

	Map<String, String> extract(Map<String, String> properties);
	
}
