package com.googlecode.happymarvin.common.utils;


public class Constants {

	// paths in the LinkedHashMap getting back from JIRA REST service
	public static final String PATH_SEP_CHAR = "/";
	public static final String PATH_TO_DESCRIPTION = "/fields/description";
	
	// text constants in the JIRA description text
	public static final String CONS_IN_DESC_INTRUCTIONS_SENTENCE_SEPARATOR = "\\. ";
	
	public static final String OPTION_CHARACTERS = "]/[";
	public static final String REGEXP_PATTERN_BRACKETS = "([\\[][a-zA-Z0-9 _\\-'`\"£$&*\\+\\(\\)\\{\\}\\.]*[\\]])";
	public static final String REGEXP_END_OF_OPTION_GROUPS = "[\\/]" + REGEXP_PATTERN_BRACKETS + "[^\\/^\\[]";
	public static final String REGEXP_CHARS_ALLOWED_IN_VALUE = ".";

	public static final String PATH_TEMPLATE_CONFIG_XML = "hm-templates-config.xml";
	public static final String PATH_CONFIG_XML = "hm-config.xml";
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	public static final String PATTERNTYPE_SIMPLIFIED = "simplified";
	public static final String PATTERNTYPE_REGULAREXPRESSION = "regularExpression";
	
	public static final String REPORT_DEFAULT_FILE_PREFIX = "REPORT";
	public static final String REPORT_FILE_NAME_DATEFORMAT = "yyyyMMdd-HHmmss-SSS";	
	
	public static final String TEMPLATE_FILE_ADDITIONALARTEFACTSTOBEGENERATED_UNIT_TEST = "UnitTest";
	public static final String JAVA_DEFAULT_SRC_FOLDER = "src/main/java";
	public static final String JAVA_DEFAULT_TEST_FOLDER = "src/test/java";
	public static final String JAVA_UNITTEST_SUFFIX = "Test";
	public static final String TEMPLATE_FILE_EXTENSION = "ftl";
	

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
