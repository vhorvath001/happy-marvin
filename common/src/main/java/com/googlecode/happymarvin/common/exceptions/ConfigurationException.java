package com.googlecode.happymarvin.common.exceptions;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 2718284997295172686L;

	public ConfigurationException(String message) {
		super(message);
	}
	
	public ConfigurationException(String message, Throwable t) {
		super(message, t);
	}

}
