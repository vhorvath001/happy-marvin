package com.googlecode.happymarvin.artefactgenerator.extractor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplateExtractedPropertyBean;

public class XmlExtractor implements ExtractorI {

	private static Map<String, Document> docStore = new HashMap<String, Document>();
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlExtractor.class);

	private XPath xpath;
	private String path;

	
	public XmlExtractor() {
		xpath = XPathFactory.newInstance().newXPath();
		path = null;
	}

	
	public Map<String, String> extract(List<TemplateExtractedPropertyBean> templateExtractedPropertyBeans, Map<String, String> properties) {
		Map<String, String> propertiesValue = new HashMap<String, String>();
		
		try {
			for(TemplateExtractedPropertyBean templateExtractedPropertyBean : templateExtractedPropertyBeans) {
				Document doc = loadDocument(properties.get(templateExtractedPropertyBean.getFrom()));
				
				String value = getValue(doc, templateExtractedPropertyBean.getWhere());
				propertiesValue.put(templateExtractedPropertyBean.getName(), value);
			}
			LOGGER.debug("The extracted properties: " + propertiesValue);
			
			return propertiesValue;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private Document loadDocument(String from) throws ParserConfigurationException, SAXException, IOException {
		if (docStore.get(from) == null) {
			// only the filename needed from the path
			String[] pathArray = from.split("/");
			path = "resource/" + pathArray[pathArray.length-1];
			File file = new File(path);
			if (!file.exists()) {
				throw new RuntimeException(String.format("The file '%s' doesn't exist!", path));
			}
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			docStore.put(from, builder.parse(file));
		}
		return docStore.get(from);
	}
	
	
	private String getValue(Document doc, String xpathString) throws XPathExpressionException {
		XPathExpression expression = xpath.compile(xpathString);
		String value = (String) expression.evaluate(doc, XPathConstants.STRING);
		if (value == null) {
			throw new RuntimeException("There is no value in the document '" + path + "' on the XPath '" + xpathString + "'!");
		}
		return value;
	}

}
