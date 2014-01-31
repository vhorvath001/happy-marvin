package com.googlecode.happymarvin.jiraminer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.common.beans.InstructionBean;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.config.InstructionSentencePatternsBean;
import com.googlecode.happymarvin.common.beans.simplexml.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtilI;
import com.googlecode.happymarvin.common.utils.Constants;
import com.googlecode.happymarvin.common.utils.StringUtility;


/*
 * When having the JiraIssueBean (and the textual description) we can get information from the description
 * this is the 2nd phase
 * 
 * http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
 */
public class UndergroundMining {

	
	private static enum Pointer {BEFORE, START, INSTRUCTION, END}
	private static final Logger LOGGER = LoggerFactory.getLogger(UndergroundMining.class);
	
	private ConfigurationReaderUtilI configurationReaderUtil = null;
	

	public JiraIssueBean mine(final JiraIssueBean jiraIssueBean) throws IOException, InvalidInstructionException, ConfigurationException {
		// getting the text of the description
		String description = jiraIssueBean.getDescription();

		// getting the instructions
		List<List<String>> instructions = getInstructions(description);
		
		// updating
		updateJiraIssueBean(jiraIssueBean, instructions);
		
		return jiraIssueBean;
	}

	
	private List<List<String>> getInstructions(String description) throws IOException {
		List<List<String>> instructions = new ArrayList<List<String>>();
		List<String> instruction = null;
		BufferedReader bufferedReader = null;

		// looping through the lines of the description
		String line = null;
		try {
			bufferedReader = new BufferedReader(new StringReader(description));
			Pointer pointer = Pointer.BEFORE;
			while((line = bufferedReader.readLine()) != null) {
				// setting the pointer
				// 		finding the end of the happy-marvin instructions
				if (line.equals(Constants.CONS_IN_DESC_INTRUCTIONS_END)) {
					pointer = Pointer.END;
					instructions.add(instruction);
					break;
				}
				// 		we are in an instruction
				if (pointer.equals(Pointer.START)) {
					pointer = Pointer.INSTRUCTION;
				}
				// 		finding the beginning of the happy-marvin instructions
				if (line.equals(Constants.CONS_IN_DESC_INTRUCTION_START)) {
					if (!pointer.equals(Pointer.BEFORE)) {
						instructions.add(instruction);
					}
					instruction = new ArrayList<String>();
					pointer = Pointer.START;
				}
				
				// the line of the instruction has to be added to the list
				if (pointer.equals(Pointer.INSTRUCTION)) {
					instruction.add(line);
				}
			}
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		
		return instructions;
	}
	
	
	private void updateJiraIssueBean(JiraIssueBean jiraIssueBean, List<List<String>> instructions) throws IOException, InvalidInstructionException, ConfigurationException {
		List<InstructionBean> instructionBeans = new ArrayList<InstructionBean>();
		
		// looping through the instructions
		for(List<String> instruction : instructions) {
			// if key:value pairs can be found in the instruction...
			if (isKeyValueInstruction(instruction)) {
				processKeyValueInstruction(instructionBeans, instruction);
			}
			// if whole sentences can be found ..
			else {
				processSentenceInstruction(instructionBeans, instruction);
			}
		}
		
		// if no exception has been thrown then the jiraIssueBean can be updated
		jiraIssueBean.setInstructions(instructionBeans);
	}
	

	// getting the values from description sentences and return an updated JiraIssueBean
	// For example: I’d need a POJO Java class in the project tlem-validation-failures-report.	
	private void processSentenceInstruction(List<InstructionBean> instructionBeans, List<String> instruction) throws InvalidInstructionException, ConfigurationException {
		// splitting the description by sentences (the end of a sentence can mean the '. ' characters or the end of the line)
		List<String> sentences = getIndividualSentences(instruction);
		// getting the sentence patterns (from the hm-config.xml => only the general patterns will be loaded -> the specific patterns cannot be read 
		//     at this moment as the values (e.g. type and template -> JAVA POJO) are not known now
		//     they can be loaded after the general values have been extracted...)
		List<InstructionSentencePatternsBean> instructionSentencePatternsBean = configurationReaderUtil.getSentencePatternsOfInstructions();
		// processing the sentences
		Map<String, String> values = new HashMap<String, String>();
		boolean isTemplateSpecificPatternsLoaded = false;
		for(String sentence : sentences) {
			// refresh the pattern list (it is needed as earlier I couldn't load all the patterns only the general ones -> to load the template specific
			//    patterns I need the type and template values which haven't been loaded then)
			isTemplateSpecificPatternsLoaded = refreshPatternList(instructionSentencePatternsBean, values, isTemplateSpecificPatternsLoaded);
			// trying to find a matching sentence pattern
			String fakeRegExpr = getMatchingFakeRegExprCreatedFromPattern(sentence, instructionSentencePatternsBean, values);
			// extracting the values from the sentence
			values.putAll(extractValues(sentence, fakeRegExpr));
		}
		InstructionBean instructionBean = createInstructionBean(values);
		instructionBeans.add(instructionBean);
		LOGGER.debug("InstructionBean: {}", instructionBean.toString());
	}


	private boolean refreshPatternList(List<InstructionSentencePatternsBean> instructionSentencePatternsBean, Map<String, String> values,
			boolean isTemplateSpecificPatternsLoaded) throws ConfigurationException {
		// if we have the type and the template value then we can load the template specific pattern(s)
		//    if it hasn't been loaded yet
		if (values.containsKey("type") && values.containsKey("template") && !isTemplateSpecificPatternsLoaded) {
			// getting the template specific pattern from config
			List<TemplatePropertyBean> templatePropertyBeans = configurationReaderUtil.getTemplateProperties(values.get("type"), values.get("template"));
			
			InstructionSentencePatternsBean newInstructionSentencePatternsBean = new InstructionSentencePatternsBean();
			newInstructionSentencePatternsBean.setSentences(new ArrayList<String>());
			for(TemplatePropertyBean templatePropertyBean : templatePropertyBeans) {
				newInstructionSentencePatternsBean.getSentences().addAll(templatePropertyBean.getTextDefs());
			}
			instructionSentencePatternsBean.add(newInstructionSentencePatternsBean);
			
			isTemplateSpecificPatternsLoaded = true;
		}
		
		return isTemplateSpecificPatternsLoaded;
	}


	private InstructionBean createInstructionBean(Map<String, String> values) {
		InstructionBean instructionBean = new InstructionBean();
		
		instructionBean.setName(values.get("name"));
		instructionBean.setPlace(values.get("place"));
		instructionBean.setProject(values.get("project"));
		instructionBean.setTemplate(values.get("template"));
		instructionBean.setType(values.get("type"));
		
		values.remove("name");
		values.remove("place");
		values.remove("project");
		values.remove("template");
		values.remove("type");
		instructionBean.setProperties(values);
		
		return instructionBean;
	}


	// sentence: I'd need a POJO Java component in the project tlem-validation-failures-report.
	// regExp:   [I](['][d])?[ ][n][e][e][d][ ][a][ ]${template}[ ]${type}[ ][c][o][m][p][o][n][e][n][t][ ][i][n][ ][t][h][e][ ][p][r][o][j][e][c][t][ ]${project}[\\.]
	private Map<String, String> extractValues(final String originalSentence, final String originalFakeRegExpr) throws ConfigurationException, InvalidInstructionException {
		String sentence = originalSentence;
		String fakeRegExpr = originalFakeRegExpr;
		Map<String, String> values = new HashMap<String, String>();
		
		while(true) {
			// getting the start of the value in the reg expression
			int start = fakeRegExpr.indexOf("${");
			if (start == -1) {
				break;
			}
			// getting the part of the reg expr till the value (-> [I](['][d])?[ ][n][e][e][d][ ][a][ ]  in the example below)
			String partRegExpr = fakeRegExpr.substring(0, start);
			// removing the characters till the value in the sentence and in the fake reg expr 
			//     (the sentence will be: POJO Java component in the project tlem-validation-failures-report.)
			//     (the fake reg expr will be: ${template}[ ]${type}[ ][c][o][m][p][o][n][e][n][t][ ][i][n][ ][t][h][e][ ][p][r][o][j][e][c][t][ ]${project}[\\.])
			sentence = sentence.replaceFirst(partRegExpr, "");
			fakeRegExpr = fakeRegExpr.substring(start);
			// I need the separation characters between the values -> to find the last character that belongs to the value (a space in the sample below)
			// for that first I have to find the } character
			int end = fakeRegExpr.indexOf("}");
			if (end == -1) {
				throw new ConfigurationException(String.format("No ending bracket ('}') in the pattern! pattern:%s",fakeRegExpr));
			}
			// I also need the name of the value
			String nameOfValue = fakeRegExpr.substring(2, end);
			// getting the separation characters
			int nextStart = fakeRegExpr.indexOf("${", 3);
			//    if this is the last value in the fake reg expr
			if (nextStart == -1) {
				nextStart = fakeRegExpr.length();
			}
			String separationChars = fakeRegExpr.substring(end + 1, nextStart);
			// getting the value from the sentence
			String value = null;
			if (separationChars == null || separationChars.length() == 0) {
				value = sentence;
			} else {
				Pattern pattern = Pattern.compile(separationChars);
				Matcher matcher = pattern.matcher(sentence);
				if (matcher.find()) {
					value = sentence.substring(0, matcher.start());
				} else {
					throw new InvalidInstructionException(String.format("The value ${%s} cannot be found in the sentence '%s'!", nameOfValue, originalSentence));
				}
			}
			// rolling the sentence and the fake reg expr
			//   sentence: 'POJO Java component in the project tlem-validation-failures-report.'  ->  ' Java component in the project tlem-validation-failures-report.'
			//   fake reg expr: '${template}[ ]${type}[ ][c][o][m][p][o][n][e][n][t] ...'  ->  '[ ]${type}[ ][c][o][m][p][o][n][e][n][t] ...'
			sentence = sentence.substring(value.length());
			fakeRegExpr = fakeRegExpr.substring(2+nameOfValue.length()+1);
			
			values.put(nameOfValue, value);
		}
		
		return values;
	}


	// finding the pattern that matches the sentence
	private String getMatchingFakeRegExprCreatedFromPattern(final String sentence,
			                                                final List<InstructionSentencePatternsBean> instructionSentencePatternsBeans,
			                                                final Map<String, String> values) throws ConfigurationException, InvalidInstructionException {
		// finding a pattern that can be used to match
		List<String> allInstructionSentencePatterns = new ArrayList<String>();
		for(InstructionSentencePatternsBean instructionSentencePatternsBean : instructionSentencePatternsBeans) {
			allInstructionSentencePatterns.addAll(instructionSentencePatternsBean.getSentences());
		}
		for (String instructionSentencePattern : allInstructionSentencePatterns) {
			if (!checkIfContainingAFoundValues(instructionSentencePattern, values)) {
				// do the matching
				List<Map<String,String>> regExps = getRegularExpressionFromPattern(instructionSentencePattern);
				for(Map<String,String> record : regExps) {
					if (sentence.matches(record.get("regExpr"))) {
						LOGGER.debug(String.format("Found regular expression: %s", record.get("fakeRegExpr")));
						return record.get("fakeRegExpr");
					}
				}
			}
		}
		
		throw new InvalidInstructionException(String.format("There is not matching pattern for the sentence '%s'! Perhaps the problem is that first you have to "
				+ "define the general (not template specific) values in the sentences ...", sentence));
	}

	
	// sentence: I'd need a POJO Java component in the project tlem-validation-failures-report.
	// instructionSentencePattern:  I['d] need a ${template} ${type} [component]/[class]/[XML file] in the project ${project}.
	// patterns: I['d] need a ${template} ${type} [component] in the project ${project}.
	//           I['d] need a ${template} ${type} [class] in the project ${project}.
	//           I['d] need a ${template} ${type} [XML file] in the project ${project}.
	// regExp:   [I](['][d])?[ ][n][e][e][d][ ][a][ ].+[ ].+[ ][c][o][m][p][o][n][e][n][t][ ][i][n][ ][t][h][e][ ][p][r][o][j][e][c][t][ ].+[\\.]
	//           ...
	//
	// the return List will contain a the list of the normal and fake reg exp
	//     fake expression for example: [I](['][d])?[ ][n][e][e][d][ ][a][ ]${template}[ ]${type}[ ] ...
	// TODO not allowed strings in the pattern: 
	//           - [${project}]
	private List<Map<String,String>> getRegularExpressionFromPattern(String instructionSentencePattern) throws ConfigurationException {
		List<Map<String,String>> regExprs = new ArrayList<Map<String,String>>();
		
		List<String> patterns = createPatternsFromOptions(instructionSentencePattern);
		LOGGER.debug("Simplified patterns created from the original pattern:\nOriginal pattern:\n{}\nSimplified patterns:\n{}", instructionSentencePattern, 
				StringUtility.toStringInNewLine(patterns));
		
		for(String pattern : patterns) {
			StringBuilder regExpr = new StringBuilder();
			StringBuilder fakeRegExpr = new StringBuilder();
			// 0 - not in value, 1 - found a $ char, 2 - found the ${ characters
			int inValueProbability = 0;
			// if we are between the characters '${' and '}' and the '.+' characters are already put (they should be put just once)
			boolean alreadyPutTheReplacementValueChar = false;
			// if the first character in the pattern is a '[' then the first character of the next word after the not mandatory word can be an uppercase or lowercase
			//    e.g.: [Please ]put [a ]....  -> The sentence can start with 'Please put...' or 'Put...' => in the real regular expression the 'put' will be
			//         something like this '...[pP]...'
			//    the variable is for determining if the first character in the pattern is a '['
			//        0 - not starting with a bracket, 1 - starting with a bracket, 2 - after the char ']' so upper/lowercase allowed in the reg expr
			int isStartingWithaBracket = (pattern.charAt(0) == '[') ? 1 : 0;
			for(char kar : pattern.toCharArray()) {
				// handling the '[', ']' characters
				if (kar == '[') {
					regExpr.append("(");
					fakeRegExpr.append("(");
				} else if (kar == ']') {
					regExpr.append(")?");
					fakeRegExpr.append(")?");
					if (isStartingWithaBracket == 1) {
						isStartingWithaBracket = 2;
					}
				}
				// handling the values (e.g. ${project})
				else if (kar == '$') {
					inValueProbability = 1;
				} else if (kar == '{' && inValueProbability == 1) {
					inValueProbability = 2;
					fakeRegExpr.append("${");
				} else if (kar == '}') {
					inValueProbability = 0;
					alreadyPutTheReplacementValueChar = false;
					fakeRegExpr.append("}");
				}
				// handling the normal characters
				else if (inValueProbability != 2) {
					regExpr.append("[").append(convertCharIfNeeded(kar, isStartingWithaBracket)).append("]");
					fakeRegExpr.append("[").append(convertCharIfNeeded(kar, isStartingWithaBracket)).append("]");
					if (isStartingWithaBracket == 2) {
						isStartingWithaBracket = 0;
					}
				} 
				// if we are in the value - between the characters '${' and '}'
				else if (inValueProbability == 2) {
					if (!alreadyPutTheReplacementValueChar) {
						alreadyPutTheReplacementValueChar = true;
						regExpr.append(Constants.REGEXP_CHARS_ALLOWED_IN_VALUE + "+");
					}
					fakeRegExpr.append(kar);
				}
			}
			Map<String,String> record = new HashMap<String, String>();
			record.put("regExpr", regExpr.toString());
			record.put("fakeRegExpr", fakeRegExpr.toString());
			regExprs.add(record);
		}
		
		//LoggerUtility.logListInNewLine(LOGGER, regExprs, "Regular expression created:", LoggerUtility.Level.DEBUG);
		return regExprs;
	}

	
	// converting a char for the reg expression
	// e.g. '.' -> '\\.'
	private String convertCharIfNeeded(char kar, int isStartingWithaBracket) {
		String s = null;
		switch(kar) {
			case '.':
			case '/':
			case '+':
			case '*':
			case '?':
			case '^':
			case '$':
			case '|':
			case '\'':
			case '#':
				s = "\\"+String.valueOf(kar);
			default:
				s = String.valueOf(kar);
				if (isStartingWithaBracket == 2) {
					s = s.toLowerCase() + s.toUpperCase();
				}
		}
		return s;
	}


	// creating more patterns if the original pattern contains options
	// For example:
	//     original pattern: I['d] need a ${template} ${type} [component]/[file]/[XML file] in the [project]/[folder] ${project}.
	//     patterns:         I['d] need a ${template} ${type} [component] in the [project] ${project}.
	//                       I['d] need a ${template} ${type} [file] in the [project] ${project}.
	//                       I['d] need a ${template} ${type} [XML file] in the [project] ${project}.
	//                       I['d] need a ${template} ${type} [component] in the [folder] ${project}.
	//                       I['d] need a ${template} ${type} [file] in the [folder] ${project}.
	//                       I['d] need a ${template} ${type} [XML file] in the [folder] ${project}.
	// in the example below there is an option with 3 choices and an other option with 2 choices => 3*2=6 patterns will be generated
	private List<String> createPatternsFromOptions(String patternText) throws ConfigurationException {
		List<String> newPatterns = new ArrayList<String>();
		
		// if there are option characters in the pattern...
		int firstIndex = patternText.indexOf(Constants.OPTION_CHARACTERS);
		if (firstIndex != -1) {
			// getting the beginning the option groups ([class]/[file]/...)
			int start = patternText.substring(0, firstIndex).lastIndexOf("[");
			int end = 0;
			if (start == -1) {
				throw new ConfigurationException(String.format("There is not starting square bracket '[' before the options characters '%s' "
						+ "in the pattern %s!", Constants.OPTION_CHARACTERS, patternText));
			}
			// getting the end of the option groups
			Pattern pattern = Pattern.compile(Constants.REGEXP_END_OF_OPTION_GROUPS);
			Matcher matcher = pattern.matcher(patternText);
			if (matcher.find()) {
				end = matcher.end();
			} else {
				throw new ConfigurationException(String.format("The end of the options cannot be found in the pattern %s!", patternText));
			}
			// getting the text option group
			String optionGroup = patternText.substring(start, end-1);
			String[] options = optionGroup.split("/");
			if (options.length < 2) {
				throw new ConfigurationException(String.format("The optionGroup %s must contain at least 2 elements!", optionGroup));
			}
			// creating new patterns
			for (String option : options) {
				String newPattern = patternText.substring(0, start) + 
                                    option.substring(1, option.length() - 1) + 
                                    patternText.substring(end-1);
				newPatterns.add(newPattern);
			}
		} else {
			newPatterns.add(patternText);
		}
		
		List<String> result = new ArrayList<String>();
		for (String newPattern : newPatterns) {
			if (newPattern.indexOf(Constants.OPTION_CHARACTERS) != -1) {
				result.addAll(createPatternsFromOptions(newPattern));
			} else {
				result.add(newPattern);
			}
		}
		
		return result;
	}

	// the sentence pattern mustn't contain an already found value
	// for example: previously the ${template}, the ${type} and the ${project} have been already found so we cannot use 
	//    the pattern 'Use the template ${template}.' (as it contains the ${template})
	private boolean checkIfContainingAFoundValues(String instructionSentencePattern, Map<String, String> values) {
		for(String key : values.keySet().toArray(new String[0])) {
			if (instructionSentencePattern.contains(String.format("${%s}",key))) {
				return true;
			}
		}
		return false;
	}


	// splitting the description by sentences (the end of a sentence can mean the '. ' characters or the end of the line)
	private List<String> getIndividualSentences(List<String> instruction) {
		List<String> sentences = new ArrayList<String>();
		// adding the individual sentences to the list
		for(String line : instruction) {
			sentences.addAll(Arrays.asList(line.split(Constants.CONS_IN_DESC_INTRUCTIONS_SENTENCE_SEPARATOR)));
		}
		// adding dot in the end of the sentences if there isn't any
		for(int i = 0; i < sentences.size(); i++) {
			if (sentences.get(i).charAt(sentences.get(i).length()-1) != '.') {
				sentences.set(i, sentences.get(i) + ".");
			}
		}
		// removing those elements whose length are zero
		for(int i = 0; i < sentences.size(); i++) {
			String sentence = sentences.get(i);
			if (sentence == null || sentence.length() == 0) {
				sentences.remove(i);
			}
		}
		
		return sentences;
	}


	// getting the values as key/value from description and return an updated JiraIssueBean
	// For exmaple:	TYPE:Java
	//              TEMPLATE:POJO
	// TYPE, TEMPLATE, PROJECT, NAME, PLACE , <template specific properties>
	private void processKeyValueInstruction(List<InstructionBean> instructionBeans, List<String> instruction) throws ConfigurationException, InvalidInstructionException {
		InstructionBean instructionBean = new InstructionBean();
		
		// getting the basic values
		String type = getValue(instruction, "TYPE");
		String template = getValue(instruction, "TEMPLATE");
		String project = getValue(instruction, "PROJECT");
		String name = getValue(instruction, "NAME");
		String place = getValue(instruction, "PLACE");
		
		// getting the template-specific value(s)
		String key = null;
		String value = null;
		Map<String, String> properties = new HashMap<String, String>();
		List<TemplatePropertyBean> propertyBeans = configurationReaderUtil.getTemplateProperties(type, template);
		for (TemplatePropertyBean propertyBean : propertyBeans) {
			key = propertyBean.getText();
			value = getValue(instruction, key);
			properties.put(key, value);
		}
		
		// if no exception has been thrown then the instruction can be added to the list
		instructionBean.setName(name);
		instructionBean.setType(type);
		instructionBean.setPlace(place);
		instructionBean.setProject(project);
		instructionBean.setTemplate(template);
		instructionBean.setProperties(properties);
		instructionBeans.add(instructionBean);
	}


	private String getValue(List<String> instruction, String key) throws InvalidInstructionException {
		String line = null;
		// finding the key in the instruction list
		for(String _line : instruction) {
			if (_line.startsWith("key")) {
				line = _line;
				break;
			}
		}
		if (line == null) {
			throw new InvalidInstructionException(String.format("The key %s cannot be found in the instruction!", key));
		}
		
		// getting the value
		String value = line.split(":")[1].trim();
		
		// checking if the value is not empty
		if (value.length() == 0) {
			throw new InvalidInstructionException(String.format("The value of the key %s is empty!", key));
		}
		
		return value;
	}


	/*
	 * Check if the instruction is defined in key-value pairs
	 * Sample: TYPE:XQuery
	 */
	private boolean isKeyValueInstruction(List<String> instruction) {
		// the instruction must be at least in 5 lines (1.TYPE, 2.TEMPLATE, 3.PROJECT, 4.NAME, 5.PLACE)
		if (instruction.size() < 5) {
			return false;
		}
		// checking if the first line is starting with the text of the common property 'TYPE:', the second...
		for(int i=0; i < 5; i++) {
			try {
				getValue(instruction, Constants.CONS_IN_DESC_NAMESOF_VALUES[i]);
			} catch (InvalidInstructionException e) {
				return false;
			}
		}
		// checking if the additional lines are starting with the text of the property of the specific template
		// TODO I am not examining this here, probably I don't have to
		
		return true;
	}


	public static void main(String[] args) {
		String sentence0 = "I'd need a POJO Java component in the project tlem-validation-failures-report.";
		String pattern0 = "I['d] need a ${template} ${type} [component]/[class]/[file]/[XML file] in the project ${project}.";
		String reg0 = "[I](['][d])?[ ][n][e][e][d][ ][a][ ].+[ ].+[ ][c][o][m][p][o][n][e][n][t][ ][i][n][ ][t][h][e][ ][p][r][o][j][e][c][t][ ].+[\\.]";
		System.out.println(sentence0.matches(reg0));
		String updated = sentence0.replaceAll("([I](['][d])?[ ][n][e][e][d][ ][a][ ]){1}", "_7693674_");
		System.out.println(updated);
		updated = updated.replaceAll("([ ][c][o][m][p][o][n][e][n][t][ ][i][n][ ][t][h][e][ ][p][r][o][j][e][c][t][ ]){1}", "_7693674_");
		System.out.println(updated);
		updated = updated.replaceAll("([\\.]){1}", "_7693674_");
		System.out.println(updated);
		updated = updated.replaceAll("([ ]){1}", "_7693674_");
		System.out.println(updated);
		
		for (String u : updated.split("_7693674_")) {
			System.out.println("__"+u+"__");
		}
		
		String sentence1 = "Hal ma";
		String reg1 = "[H]?[a][l][ ][m][a]([f][a])?";
		
		System.out.println(sentence1.matches(reg1));
	}


	public void setConfigurationReaderUtil(ConfigurationReaderUtilI configurationReaderUtil) {
		this.configurationReaderUtil = configurationReaderUtil;
	}


}