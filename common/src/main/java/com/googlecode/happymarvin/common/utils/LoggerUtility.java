package com.googlecode.happymarvin.common.utils;

import java.util.List;

import org.slf4j.Logger;


public class LoggerUtility {
	
	
	public static enum Level {DEBUG};

	
	public static void logListInNewLine(Logger logger, List<String> list, String message, Level level) {
		log(logger, message, level);
		for(String s : list) {
			log(logger, s, level);
		}
	}

	
	private static void log(Logger logger, String message, Level level) {
		if (level.equals(LoggerUtility.Level.DEBUG)) {
			logger.debug(message);
		}
	}
	
}
