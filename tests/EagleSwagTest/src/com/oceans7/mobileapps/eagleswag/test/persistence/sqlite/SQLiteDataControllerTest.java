/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SQLiteDataControllerTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Test fixture for the SQLite Data Controller class.
 * 
 * @see com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataController
 */

package com.oceans7.mobileapps.eagleswag.test.persistence.sqlite;

import java.util.Queue;

import android.database.DatabaseUtils;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataController;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerConstants;

public class SQLiteDataControllerTest extends InstrumentationTestCase {

	private SQLiteDataController sqliteDataController;

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp () throws Exception {
		super.setUp();

		// Create the data controller
		this.sqliteDataController = new SQLiteDataController();
		this.sqliteDataController.open(getInstrumentation().getTargetContext());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();

		// Close the data controller
		this.sqliteDataController.close();
	}

	/**
	 * Helper method that requests general questions from the SQLite data
	 * controller and returns the number of questions obtained from the data
	 * controller.
	 * 
	 * @param number
	 *            Number of questions requested.
	 * @return
	 *         The number of questions found.
	 * @throws Exception
	 */
	private int checkGeneralQuestionsFound (int number) throws Exception {

		// The queue to store the general questions
		Queue<GeneralQuestion> questions = this.sqliteDataController.<GeneralQuestion>getQuestions(GeneralQuestion.class, number);

		// Number of questions in the queue
		int questionsFound = 0;

		for (@SuppressWarnings("unused")
		Question question : questions) {
			// Loop through the queue
			questionsFound++;
		}

		return questionsFound;
	}

	/**
	 * Helper method that requests engineering questions from the SQLite data
	 * controller and returns the number of questions obtained from the data
	 * controller.
	 * 
	 * @param number
	 *            Number of questions requested.
	 * @return
	 *         The number of questions found.
	 * @throws Exception
	 */
	private int checkEngineeringQuestionsFound (int number) throws Exception {

		// The queue to store the engineering questions
		Queue<EngineeringQuestion> questions = this.sqliteDataController.<EngineeringQuestion>getQuestions(EngineeringQuestion.class, number);

		// Number of questions in the queue
		int questionsFound = 0;

		for (@SuppressWarnings("unused")
		Question question : questions) {
			// Loop through the queue
			questionsFound++;
		}

		return questionsFound;
	}

	/**
	 * Helper method that requests pilot questions from the SQLite data
	 * controller and returns the number of questions obtained from the data
	 * controller.
	 * 
	 * @param number
	 *            Number of questions requested.
	 * @return
	 *         The number of questions found.
	 * @throws Exception
	 */
	private int checkPilotQuestionsFound (int number) throws Exception {

		// The queue to store the pilot questions
		Queue<PilotQuestion> questions = this.sqliteDataController.<PilotQuestion>getQuestions(PilotQuestion.class, number);

		// Number of questions in the queue
		int questionsFound = 0;

		for (@SuppressWarnings("unused")
		Question question : questions) {
			// Loop through the queue
			questionsFound++;
		}

		return questionsFound;
	}

	/**
	 * Checks that if one more questions is requested from a questions table,
	 * only the number of questions in the table is actually returned.
	 * 
	 * @param table
	 *            The table to request the questions from.
	 * @throws Exception
	 */
	public void checkObtainMoreQuestionsThenAvailable (String table) throws Exception {

		// Number of questions in the general questions table
		long numberOfQuestionsInDatabase = DatabaseUtils.queryNumEntries(this.sqliteDataController.getDatabase(), table);

		// Number of questions actually found
		int requestedQuestions = (int) (numberOfQuestionsInDatabase + 1);
		int numberOfQuestionsFound = this.checkGeneralQuestionsFound(requestedQuestions);

		Log.d(this.getClass().getName(), "Requested " + requestedQuestions + " questions from " + table + " and obtained " + numberOfQuestionsFound);
		assertEquals(numberOfQuestionsInDatabase, numberOfQuestionsFound);
	}

	/**
	 * Ensures that positive (greater than 0) general questions are requested, 0
	 * are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfGeneralQuestionsAboveZero () throws Exception {
		assertEquals(this.checkGeneralQuestionsFound(3), 3);
	}

	/**
	 * Ensures that if negative general questions are requested, 0 are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfGeneralQuestionsBelowZero () throws Exception {
		assertEquals(this.checkGeneralQuestionsFound(-1), 0);
	}

	/**
	 * Ensures that if 0 general questions are requested, 0 are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfGeneralQuestionsZero () throws Exception {
		assertEquals(this.checkGeneralQuestionsFound(0), 0);
	}

	/**
	 * Ensures that positive (greater than 0) engineering questions are
	 * requested, 0 are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfEngineeringQuestionsAboveZero () throws Exception {
		assertEquals(this.checkEngineeringQuestionsFound(3), 3);
	}

	/**
	 * Ensures that if negative engineering questions are requested, 0 are
	 * returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfEngineeringQuestionsBelowZero () throws Exception {
		assertEquals(this.checkEngineeringQuestionsFound(-1), 0);
	}

	/**
	 * Ensures that if 0 engineering questions are requested, 0 are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfEngineeringQuestionsZero () throws Exception {
		assertEquals(this.checkEngineeringQuestionsFound(0), 0);
	}

	/**
	 * Ensures that positive (greater than 0) pilot questions are requested, 0
	 * are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfPilotQuestionsAboveZero () throws Exception {
		assertEquals(this.checkPilotQuestionsFound(3), 3);
	}

	/**
	 * Ensures that if negative pilot questions are requested, 0 are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfPilotQuestionsBelowZero () throws Exception {
		assertEquals(this.checkPilotQuestionsFound(-1), 0);
	}

	/**
	 * Ensures that if 0 pilot questions are requested, 0 are returned.
	 * 
	 * @throws Exception
	 */
	public void testCorrectNumberOfPilotQuestionsZero () throws Exception {
		assertEquals(this.checkPilotQuestionsFound(0), 0);
	}

	/**
	 * Ensures that if more questions are requested from the database than are
	 * available, only the maximum number of questions in the database are
	 * actually returned.
	 * 
	 * @throws Exception
	 */
	public void testObtainMoreGeneralQuestionsThenAvailable () throws Exception {

		// Number of questions in the general questions table
		long numberOfQuestionsInDatabase = DatabaseUtils.queryNumEntries(this.sqliteDataController.getDatabase(), SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE);

		// Number of questions actually found
		int requestedQuestions = (int) (numberOfQuestionsInDatabase + 1);
		int numberOfQuestionsFound = this.checkGeneralQuestionsFound(requestedQuestions);

		Log.d(this.getClass().getName(), "Requested " + requestedQuestions + " questions from general questions table and obtained " + numberOfQuestionsFound);
		assertEquals(numberOfQuestionsInDatabase, numberOfQuestionsFound);
	}

	/**
	 * Ensures that if more questions are requested from the database than are
	 * available, only the maximum number of questions in the database are
	 * actually returned.
	 * 
	 * @throws Exception
	 */
	public void testObtainMoreEngineeringQuestionsThenAvailable () throws Exception {

		// Number of questions in the engineering questions table
		long numberOfQuestionsInDatabase = DatabaseUtils.queryNumEntries(this.sqliteDataController.getDatabase(), SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE);

		// Number of questions actually found
		int requestedQuestions = (int) (numberOfQuestionsInDatabase + 1);
		int numberOfQuestionsFound = this.checkEngineeringQuestionsFound(requestedQuestions);

		Log.d(this.getClass().getName(), "Requested " + requestedQuestions + " questions from engineering questions table and obtained " + numberOfQuestionsFound);
		assertEquals(numberOfQuestionsInDatabase, numberOfQuestionsFound);
	}

	/**
	 * Ensures that if more questions are requested from the database than are
	 * available, only the maximum number of questions in the database are
	 * actually returned.
	 * 
	 * @throws Exception
	 */
	public void testObtainMorePilotQuestionsThenAvailable () throws Exception {
		
		// Number of questions in the pilot questions table
		long numberOfQuestionsInDatabase = DatabaseUtils.queryNumEntries(this.sqliteDataController.getDatabase(), SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE);

		// Number of questions actually found
		int requestedQuestions = (int) (numberOfQuestionsInDatabase + 1);
		int numberOfQuestionsFound = this.checkPilotQuestionsFound(requestedQuestions);

		Log.d(this.getClass().getName(), "Requested " + requestedQuestions + " questions from pilot questions table and obtained " + numberOfQuestionsFound);
		assertEquals(numberOfQuestionsInDatabase, numberOfQuestionsFound);
	}
}
