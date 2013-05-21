/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file DataManager.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.util.Queue;

import android.content.Context;
import android.database.SQLException;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public interface DataController {

	public void open (Context context) throws SQLException;

	public void close ();
	
	public <T extends Question> Queue<T> getQuestions (Class<T> key, int number);

	public void saveQuestion (Class<? extends Question> key, Question question);
}
