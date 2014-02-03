package com.googlecode.happymarvin.common.utils;

import java.util.List;

public class StringUtility {

	
	public static String toStringInNewLine(List<String> list) {
		StringBuffer stringBuffer = new StringBuffer();
		for(String s : list) {
			stringBuffer.append(s).append("\n");
		}
		return stringBuffer.toString();
	}
	
}
