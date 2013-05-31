/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file DataFileParserStrategy.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 * 
 * TODO Documentation
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.util.Queue;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public interface DataFileParserStrategy {

	public <T extends Question> Queue<T> getQuestions (Class<T> key, Context context);
}
