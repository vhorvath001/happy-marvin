package com.googlecode.happymarvin.jiraminer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.common.beans.InstructionBean;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.beans.configuration.config.InstructionSentencePatternsBean;
import com.googlecode.happymarvin.common.beans.configuration.templatesConfig.TemplatePropertyBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.common.utils.Constants;
import com.googlecode.happymarvin.common.utils.LoggerUtility;

/*
 * When having the JiraIssueBean (and the textual description) we can get information from the description
 * this is the 2nd phase
 * 
 * http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
 */
public class UndergroundMining {

	private static enum Pointer {BEFORE, START, INSTRUCTION, END}
	private static final String[] INTRUCTION_STARTING_STRING = {"TYPE", "TEMPLATE", "PROJECT", "NAME", "PLACE"};
	private static final Logger LOGGER = LoggerFactory.getLogger(UndergroundMining.class);
	

	public JiraIssueBean mine(final JiraIssueBean jiraIssueBean) throws IOException, InvalidInstructionException, ConfigurationException {
		// getting the text of the description
		String description = jiraIssueBean.getDescription();

		// getting the instructions
		List<List<String>> instructions = getInstructions(description);
		
		// updating
		updateJiraIssueBean(jiraIssueBean, instructions);
		
		return jiraIssueBean;
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
		// getting the sentence patterns
		List<InstructionSentencePatternsBean> instructionSentencePatternsBean = ConfigurationReaderUtil.getSentencePatternsOfInstructions();
		// processing the sentences
		Map<String, String> values = new HashMap<String, String>();
		for(String sentence : sentences) {
			// trying to find a matching sentence pattern
			String fakeRegExpr = getMatchingRegExprCreatedFromPattern(sentence, instructionSentencePatternsBean, values);
			// extracting the values from the sentence
			values = extractValues(sentence, regExpr);
		}
	}


	// finding the pattern that matches the sentence
	private String getMatchingRegExprCreatedFromPattern(String sentence,
			                                            List<InstructionSentencePatternsBean> instructionSentencePatternsBeans,
			                                            Map<String, String> values) throws ConfigurationException {
		String regExp = null;
		
		// finding a pattern that can be used to match
		List<String> allInstructionSentencePatterns = new ArrayList<String>();
		for(InstructionSentencePatternsBean instructionSentencePatternsBean : instructionSentencePatternsBeans) {
			allInstructionSentencePatterns.addAll(instructionSentencePatternsBean.getSentence1s());
			allInstructionSentencePatterns.addAll(instructionSentencePatternsBean.getSentence2s());
			allInstructionSentencePatterns.addAll(instructionSentencePatternsBean.getSentence3s());
			allInstructionSentencePatterns.addAll(instructionSentencePatternsBean.getSentence4s());
			allInstructionSentencePatterns.addAll(instructionSentencePatternsBean.getSentence5s());
		}
		main: for (String instructionSentencePattern : allInstructionSentencePatterns) {
			if (!checkIfContainingAFoundValues(instructionSentencePattern, values)) {
				// do the matching
				List<String> regExps = getRegularExpressionFromPattern(instructionSentencePattern);
				for(String _regExp : regExps) {
					if (sentence.matches(_regExp)) {
						regExp = _regExp;
						break main;
					}
				}
			}
		}
		
		return regExp;
	}

	
	// sentence: I'd need a POJO Java component in the project tlem-validation-failures-report.
	// instructionSentencePattern:  I['d] need a ${template} ${type} [component]/[class]/[XML file] in the project ${project}.
	// patterns: I['d] need a ${template} ${type} [component] in the project ${project}.
	//           I['d] need a ${template} ${type} [class] in the project ${project}.
	//           I['d] need a ${template} ${type} [XML file] in the project ${project}.
	// regExp:   [I](['][d])?[ ][n][e][e][d][ ][a][ ].+[ ].+[ ][c][o][m][p][o][n][e][n][t][ ][i][n][ ][t][h][e][ ][p][r][o][j][e][c][t][ ].+[\\.]
	//           ...
	private List<String> getRegularExpressionFromPattern(String instructionSentencePattern) throws ConfigurationException {
		List<String> regExprs = new ArrayList<String>();
		
		List<String> patterns = createPatternsFromOptions(instructionSentencePattern);
		LoggerUtility.logListInNewLine(LOGGER, patterns, "Simplified patterns created from the original pattern:", LoggerUtility.Level.DEBUG);
		
		for(String pattern : patterns) {
			StringBuilder regExpr = new StringBuilder();
			// 0 - not in value, 1 - found a $ char, 2 - found the ${ characters
			int inValueProbability = 0;
			// if we are between the characters '${' and '}' and the '.+' characters are already put (they should be put just once)
			boolean alreadyPutTheReplacementValueChar = false; 
			for(char kar : pattern.toCharArray()) {
				// handling the '[', ']' characters
				if (kar == '[') {
					regExpr.append("(");
				} else if (kar == ']') {
					regExpr.append(")?");
				}
				// handling the values (e.g. ${project})
				else if (kar == '$') {
					inValueProbability = 1;
				} else if (kar == '{' && inValueProbability == 1) {
					inValueProbability = 2;
				} else if (kar == '}') {
					inValueProbability = 0;
					alreadyPutTheReplacementValueChar = false;
				}
				// handling the normal characters
				else if (inValueProbability != 2) {
					regExpr.append("[").append(convertCharIfNeeded(kar)).append("]");
				} 
				// if we are in the value - between the characters '${' and '}'
				else if (inValueProbability == 2) {
					if (!alreadyPutTheReplacementValueChar) {
						alreadyPutTheReplacementValueChar = true;
						regExpr.append(".+");
					}
				}
			}
			regExprs.add(regExpr.toString());
		}
		
		LoggerUtility.logListInNewLine(LOGGER, regExprs, "Regular expression created:", LoggerUtility.Level.DEBUG);
		return regExprs;
	}

	
	// converting a char for the reg expression
	// e.g. '.' -> '\\.'
	private String convertCharIfNeeded(char kar) {
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
		}
		// TODO Auto-generated method stub
		return null;
	}


	// creating more patterns if the original pattern contains options
	// For example:
	//     original pattern: I['d] need a ${template} ${type} [component]/[class]/[file]/[XML file] in the [project]/[folder] ${project}.
	//     patterns:         I['d] need a ${template} ${type} [component] in the [project] ${project}.
	//                       I['d] need a ${template} ${type} [file] in the [project] ${project}.
	//                       I['d] need a ${template} ${type} [file]/[XML file] in the [project] ${project}.
	//                       I['d] need a ${template} ${type} [component] in the [folder] ${project}.
	//                       ...
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
			String optionGroup = patternText.substring(start, end);
			String[] options = optionGroup.split("/");
			if (options.length < 2) {
				throw new ConfigurationException(String.format("The optionGroup %s must contain at least 2 elements!", optionGroup));
			}
			// creating new patterns
			for (String option : options) {
				String newPattern = patternText.substring(0, start) + option + patternText.substring(end);
				LOGGER.debug("new pattern: {}", newPattern);
				newPatterns.add(newPattern);
			}
		} else {
			newPatterns.add(patternText);
		}
		
		for (String newPattern : newPatterns) {
			if (newPattern.indexOf(Constants.OPTION_CHARACTERS) != -1) {
				newPatterns.addAll(createPatternsFromOptions(newPattern));
			}
		}
		
		return newPatterns;
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
	private void processKeyValueInstruction(List<InstructionBean> instructionBeans, List<String> instruction) throws InvalidInstructionException {
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
		List<TemplatePropertyBean> propertyBeans = ConfigurationReaderUtil.getTemplateProperties(type, template);
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
				getValue(instruction, INTRUCTION_STARTING_STRING[i]);
			} catch (InvalidInstructionException e) {
				return false;
			}
		}
		// checking if the additional linea are starting with the text of the property of the specific template
		// TODO I am not examining this here, probably I don't have to
		
		return true;
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
}