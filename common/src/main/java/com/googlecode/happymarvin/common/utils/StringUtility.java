package com.googlecode.happymarvin.common.utils;

import java.util.List;

public class StringUtility {

	
	private static final String[] ordinals = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
	
	public static String toStringInNewLine(List<String> list) {
		StringBuffer stringBuffer = new StringBuffer();
		for(String s : list) {
			stringBuffer.append(s).append("\n");
		}
		return stringBuffer.toString();
	}
	
	
	public static String ordinal(int i) {
	    return i % 100 == 11 || i % 100 == 12 || i % 100 == 13 ? i + "th" : i + ordinals[i % 10];
	}

	
	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
	
}
