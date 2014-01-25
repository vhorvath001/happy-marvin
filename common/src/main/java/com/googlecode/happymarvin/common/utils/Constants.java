package com.googlecode.happymarvin.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {

	// paths in the LinkedHashMap getting back from JIRA REST service
	public static final String PATH_SEP_CHAR = "/";
	public static final String PATH_TO_DESCRIPTION = "/fields/description";
	
	// text constants in the JIRA description text
	public static final String CONS_IN_DESC_INTRUCTION_START = "~~~HAPPYMARVIN-INSTRUCTION~~~";
	public static final String CONS_IN_DESC_INTRUCTIONS_END = "~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
	public static final String CONS_IN_DESC_INTRUCTIONS_SENTENCE_SEPARATOR = ". ";
	
	public static final String OPTION_CHARACTERS = "]/[";
	public static final String REGEXP_PATTERN_BRACKETS = "([\\[][a-zA-Z0-9 _\\-'`\"£$&*\\+\\(\\)\\{\\}\\.]*[\\]])";
	public static final String REGEXP_END_OF_OPTION_GROUPS = "[\\/]" + REGEXP_PATTERN_BRACKETS + "[^\\/^\\[]";

	public static final String PATH_TEMPLATE_CONFIG_XML = "hm-templates-config.xml";
	public static final String PATH_CONFIG_XML = "hm-config.xml";
	

	public static void main(String[] args) {
		String s = "I['d] need a ${template} ${type} [component]/[class]/[file]/[XML file] in the [project]/[folder] ${project}.";
		String bracketsPattern = "([\\[][a-zA-Z0-9 _\\-'`\"£$&*\\+\\(\\)\\{\\}\\.]*[\\]])";
		
		//System.out.println(s.replaceAll(p, "____"));
		Pattern pattern = Pattern.compile("[\\/]" + bracketsPattern + "[^\\/^\\[]");
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			System.out.println(start + "    " + end + "   " + matcher.group());
			System.out.println(s.substring(0, start));
			System.out.println(s.substring(0, end));
			System.out.println("________________");
		}
	}
	
}
