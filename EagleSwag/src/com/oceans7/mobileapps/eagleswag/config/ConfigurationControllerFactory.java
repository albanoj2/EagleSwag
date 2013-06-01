/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfigControllerFactory.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 */

package com.oceans7.mobileapps.eagleswag.config;

public class ConfigurationControllerFactory {

	private static ConfigurationControllerFactory instance;
	
	private ConfigurationControllerFactory () {}
	
	public static synchronized ConfigurationControllerFactory getInstance () {
		
		if (instance == null) {
			// Lazy instantiation of the singleton instance
			instance = new ConfigurationControllerFactory();
		}
		
		return instance;
	}
	
	public ConfigurationController getController () {
		return new ConfigurationProxy();
	}
}
