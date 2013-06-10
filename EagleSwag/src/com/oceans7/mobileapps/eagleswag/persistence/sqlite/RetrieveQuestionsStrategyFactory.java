/**
 * @author Justin Albano
 * @date May 29, 2013
 * @file RetrieveQuestionsStrategyFactory.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Factory to create strategy for retrieving questions from the database.
 *       The strategy created varies depending on the class specified in the
 *       SQLite configuration file.
 *       
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class RetrieveQuestionsStrategyFactory {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of factory.
	 */
	private static RetrieveQuestionsStrategyFactory instance;

	/**
	 * The location of the retrieval strategy configuration asset. This location
	 * is relative to the assets directory of the Android project.
	 */
	private static final String DATA_CONTROLLER_CONFIG_ASSET = "config/sqlite/sqlite.cfg";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Hidden constructor (for singleton).
	 */
	private RetrieveQuestionsStrategyFactory () {}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains a reference to the singleton instance of the factory.
	 * 
	 * @return
	 *         A singleton instance of the factory.
	 */
	public static synchronized RetrieveQuestionsStrategyFactory getInstance () {

		if (instance == null) {
			// Lazy instantiation of the instance
			instance = new RetrieveQuestionsStrategyFactory();
		}

		return instance;
	}

	/**
	 * Obtains the strategy for retrieving questions from the database. The
	 * concrete class returned by this method is specified in the SQLite
	 * configuration file.
	 * 
	 * @param context
	 *            The context used to access the configuration file which
	 *            specifies the concrete strategy to return.
	 * @return
	 *         A strategy for accessing questions from the database.
	 */
	public RetrieveQuestionsStrategy getRetrieveQuestionsStrategy (Context context) {

		// Stub for data controller object
		RetrieveQuestionsStrategy retrieveQuestionsStrategy = null;

		try {
			// Obtain the class name of the retrieval strategy
			InputStream is = context.getAssets().open(DATA_CONTROLLER_CONFIG_ASSET);
			Properties properties = new Properties();
			properties.load(is);
			is.close();

			// Obtain the retrieval strategy class name
			String retrievalStrategyName = properties.getProperty("retrieveQuestionsStrategy.classname");
			Log.i(this.getClass().getName(), "Configuration file specified questions retrieval strategy as " + retrievalStrategyName);

			// Reflexively set the retrieval strategy
			retrieveQuestionsStrategy = (RetrieveQuestionsStrategy) Class.forName(retrievalStrategyName).newInstance();
			Log.i(this.getClass().getName(), "Retrieval strategy set to " + retrievalStrategyName);
		}
		catch (InstantiationException e) {
			// Error occurred while instantiating retrieval strategy
			Log.e(this.getClass().getName(), "Error occurred while instantiating retrieval strategy: " + e);
		}
		catch (IllegalAccessException e) {
			// Illegal access occurred when trying to instantiate
			Log.e(this.getClass().getName(), "Illegal access occurred while setting retrieval strategy: " + e);
		}
		catch (ClassNotFoundException e) {
			// The data controller class could not be found
			Log.e(this.getClass().getName(), "Retrieval strategy class cannot be found: " + e);
		}
		catch (FileNotFoundException e) {
			// The configuration file could not be found
			Log.e(this.getClass().getName(), "Configuration file could not be found: " + e);
		}
		catch (IOException e) {
			// IOException occurred while trying to access the properties file
			Log.e(this.getClass().getName(), "IOException occurred while trying to access the configuration file: " + e);
		}

		return retrieveQuestionsStrategy;
	}

}
