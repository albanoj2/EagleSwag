/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file Question.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.domain;

public interface Question {

	public int getId ();
	
	public void setId (int id);

	public String getQuestionString ();

	public void setQuestionString (String text);
	
	public int getYesPointValue ();

	public void setYesPointValue (int value);

	public int getNoPointValue ();

	public void setNoPointValue (int value);

	public int getUsedCount ();

	public void incrementUsedCount ();

}
