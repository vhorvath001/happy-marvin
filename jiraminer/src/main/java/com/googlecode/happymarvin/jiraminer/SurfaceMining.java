package com.googlecode.happymarvin.jiraminer;

import java.util.LinkedHashMap;

import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.utils.Constants;

/*
 * the class populates the JiraIssueBean from the LinkedHashMap getting back from the JIRA REST service
 * this is the 1st phase
 */
public class SurfaceMining {

	
	public JiraIssueBean mine(final LinkedHashMap<String, Object> reponseJiraRest) {
		JiraIssueBean jiraIssueBean = new JiraIssueBean();
		
		jiraIssueBean.setDescription(get(reponseJiraRest, Constants.PATH_TO_DESCRIPTION, String.class));
		
		return jiraIssueBean;
	}

	
	private <T> T get(LinkedHashMap<String, Object> reponseJiraRest, String path, Class<T> clazz) {
		String[] pathElements = path.split(Constants.PATH_SEP_CHAR);
		
		// the path doesn't start with a '/'
		if (pathElements[0].length() > 0) {
			throw new IllegalArgumentException(String.format("The path %s must begin with the character '%s'!", path, Constants.PATH_SEP_CHAR));
		}
		
		// this is the value has to be passed back -> e.g. '/description'
		if (pathElements.length == 2) {
			Object value = reponseJiraRest.get(pathElements[1]);
			return clazz.cast(value);
		}
		// this is not the final element -> e.g. '/fields/description'
		else {
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) reponseJiraRest.get(pathElements[1]);
			return get(map, path.replaceFirst(Constants.PATH_SEP_CHAR + pathElements[1], ""), clazz);
		}
	}
	
}
