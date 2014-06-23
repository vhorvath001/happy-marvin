package com.googlecode.happymarvin.artefactgenerator.extractor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlExtractor implements ExtractorI {


	private String path;
	private XPath xpath;

	
	public XmlExtractor(String path) {
		this.path = path;
		xpath = XPathFactory.newInstance().newXPath();
	}

	
	public Map<String, String> extract(Map<String, String> propertiesXpath) {
		Map<String, String> propertiesValue = new HashMap<String, String>();
		
		try {
			Document doc = loadDocument();
			
			for (String propName : propertiesXpath.keySet()) {
				String value = getValue(doc, propertiesXpath.get(propName));
				propertiesValue.put(propName, value);
			}
			
			return propertiesValue;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private Document loadDocument() throws ParserConfigurationException, SAXException, IOException {
		// only the filename needed from the path
		String[] pathArray = path.split("/");
		File file = new File("resource/" + pathArray[pathArray.length-1]);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(file);
	}
	
	
	private String getValue(Document doc, String xpathString) throws XPathExpressionException {
		XPathExpression expression = xpath.compile(xpathString);
		return (String) expression.evaluate(doc, XPathConstants.STRING);
	}

}
