/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file DataControllerFactory.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Factory used to create the data controller for persistent storage. The
 *       data controller created varies depending on the class specified in the
 *       data controller configuration file.
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class DataControllerFactory {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of factory.
	 */
	private static DataControllerFactory instance;

	/**
	 * The ID of the data controller configuration resource.
	 */
	private static final int DATA_CONTROLLER_CONFIG_RES = com.oceans7.mobileapps.eagleswag.R.raw.datacontroller;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Hidden constructor (for singleton).
	 */
	private DataControllerFactory () {}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	public static DataControllerFactory getInstance () {

		if (instance == null) {
			// Lazy instantiation of the instance
			instance = new DataControllerFactory();
		}

		return instance;
	}

	/**
	 * Obtains the data controller for persistence storage. The data controller
	 * used is specified in the data controller configuration file.
	 * 
	 * @param context
	 *            The context used to create the data controller.
	 * @return
	 *         The data controller (as specified by the data controller
	 *         configuration file).
	 */
	public DataController getDataController (Context context) {

		// Stub for data controller object
		DataController dataController = null;

		try {
			// Obtain the class name of the data controller
			InputStream is = context.getResources().openRawResource(DATA_CONTROLLER_CONFIG_RES);
			Properties properties = new Properties();
			properties.load(is);
			is.close();
			
			// Obtain the data controller class name
			String dataControllerName = properties.getProperty("dataController.classname");
			Log.i(this.getClass().getName(), "Configuration file specified data controller as " + dataControllerName);

			// Reflexively set the data controller
			dataController = (DataController) Class.forName(dataControllerName).newInstance();
			Log.i(this.getClass().getName(), "Data controller set to " + dataControllerName);
		}
		catch (InstantiationException e) {
			// Error occurred while instantiating data controller
			Log.e(this.getClass().getName(), "Error occurred while instantiating data controller: " + e);
		}
		catch (IllegalAccessException e) {
			// Illegal access occurred when trying to instantiate
			Log.e(this.getClass().getName(), "Illegal access occurred while setting data controller: " + e);
		}
		catch (ClassNotFoundException e) {
			// The data controller class could not be found
			Log.e(this.getClass().getName(), "Data controller class cannot be found: " + e);
		}
		catch (FileNotFoundException e) {
			// The configuration file could not be found
			Log.e(this.getClass().getName(), "Configuration file could not be found: " + e);
		}
		catch (IOException e) {
			// IOException occurred while trying to access the properties file
			Log.e(this.getClass().getName(), "IOException occurred while trying to access the confiuration file: " + e);
		}

		return dataController;
	}

}
