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

public class QuestionTypeConfigControllerFactory {

	private static QuestionTypeConfigControllerFactory instance;
	
	private QuestionTypeConfigControllerFactory () {}
	
	public static synchronized QuestionTypeConfigControllerFactory getInstance () {
		
		if (instance == null) {
			// Lazy instantiation of the singleton instance
			instance = new QuestionTypeConfigControllerFactory();
		}
		
		return instance;
	}
	
	public QuestionTypeConfigController getController () {
		return new QuestionTypeConfigProxy();
	}
}
