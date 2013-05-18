/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file QuestionManagerTest.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 */

package com.oceans7.mobileapps.eagleswag.domain.test;

import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.domain.QuestionManager;

public class QuestionManagerTest extends AndroidTestCase {
	
	private QuestionManager questionManager;

	/**
	 * {@inheritDoc}
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	protected void setUp () throws Exception {
		super.setUp();
		
		// Create the question manager
		this.questionManager = QuestionManager.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	protected void tearDown () throws Exception {
		super.tearDown();
	}
	
	@Test
	protected void createEngineeringQueueTest () {
		
		// Obtain the engineering questions
		Queue<Question> engineeringQuestions = this.questionManager.getEngineeringQuestions();
		
		for (Question question : engineeringQuestions) {
			// Iterate through the queue and pop off each element
			
			try {
				// Try to obtain a simple piece of data from the 
				question.getId();
			}
			catch (NullPointerException e) {
				// The queue is not fluid and is either null or has null values
				assertTrue(false);
			}
		}
	}

}
