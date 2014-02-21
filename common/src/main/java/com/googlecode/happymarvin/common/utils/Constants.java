package com.googlecode.happymarvin.common.utils;


public class Constants {

	// paths in the LinkedHashMap getting back from JIRA REST service
	public static final String PATH_SEP_CHAR = "/";
	public static final String PATH_TO_DESCRIPTION = "/fields/description";
	
	// text constants in the JIRA description text
	public static final String CONS_IN_DESC_INTRUCTION_START = "~~~HAPPYMARVIN-INSTRUCTION~~~";
	public static final String CONS_IN_DESC_INTRUCTIONS_END = "~~~HAPPYMARVIN-INSTRUCTIONS-END~~~";
	public static final String CONS_IN_DESC_INTRUCTIONS_SENTENCE_SEPARATOR = "\\. ";
//	public static final String[] CONS_IN_DESC_NAMESOF_VALUES = {"TYPE", "TEMPLATE", "PROJECT", "NAME", "LOCATION"};
	
	public static final String OPTION_CHARACTERS = "]/[";
	public static final String REGEXP_PATTERN_BRACKETS = "([\\[][a-zA-Z0-9 _\\-'`\"£$&*\\+\\(\\)\\{\\}\\.]*[\\]])";
	public static final String REGEXP_END_OF_OPTION_GROUPS = "[\\/]" + REGEXP_PATTERN_BRACKETS + "[^\\/^\\[]";
	//public static final String REGEXP_CHARS_ALLOWED_IN_VALUE = "[a-zA-Z0-9-_:]";
	//public static final String REGEXP_CHARS_ALLOWED_IN_VALUE = "[a-zA-Z0-9_:-/=+\\.,\\[\\]\\{\\}#]";
	public static final String REGEXP_CHARS_ALLOWED_IN_VALUE = ".";

	public static final String PATH_TEMPLATE_CONFIG_XML = "hm-templates-config.xml";
	public static final String PATH_CONFIG_XML = "hm-config.xml";
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	public static final String PATTERNTYPE_SIMPLIFIED = "simplified";
	public static final String PATTERNTYPE_REGULAREXPRESSION = "regularExpression";

	public enum NamesOfValues {
		TYPE("TYPE"), TEMPLATE("TEMPLATE"), PROJECT("PROJECT"), NAME("NAME"), LOCATION("LOCATION");
		private String value;
		private NamesOfValues(String value) { this.value = value; }
		public String getValue() { return value; }
		public static String allValues() { return String.format("%s,%s,%s,%s,%s", TYPE.getValue(), TEMPLATE.getValue(), PROJECT.getValue(), 
				NAME.getValue(), LOCATION.getValue()); }
	}
	
	
//	public static void main(String[] args) {
//		String s = "I['d] need a ${template} ${type} [component]/[class]/[file]/[XML file] in the [project]/[folder] ${project}.";
//		String bracketsPattern = "([\\[][a-zA-Z0-9 _\\-'`\"£$&*\\+\\(\\)\\{\\}\\.]*[\\]])";
//		
//		//System.out.println(s.replaceAll(p, "____"));
//		Pattern pattern = Pattern.compile("[\\/]" + bracketsPattern + "[^\\/^\\[]");
//		Matcher matcher = pattern.matcher(s);
//		while (matcher.find()) {
//			int start = matcher.start();
//			int end = matcher.end();
//			System.out.println(start + "    " + end + "   " + matcher.group());
//			System.out.println(s.substring(0, start));
//			System.out.println(s.substring(0, end));
//			System.out.println("________________");
//		}
//		
//		
//		String sentence = "POJO Java component in the project tlem-validation-failures-report.";
//		String regExpr = "[ ]";
//		
//		pattern = Pattern.compile(regExpr);
//		matcher = pattern.matcher(sentence);
//		if (matcher.find()) {
//			int aaa = matcher.start();
//			int aaa2 = matcher.end();
//			System.out.println(aaa + "  " + aaa2);
//		}
//		
//	}
	
	
}
