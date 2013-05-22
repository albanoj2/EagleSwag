/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file DataFileParserFactory.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Factory for the creation of the data file parser. The class returned by
 *       this factory is dependent on the data specified in the data controller
 *       configuration file; likewise, the resource ID of the data file to parse
 *       is also located in the data controller configuration file.
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class DataFileParserFactory {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of factory.
	 */
	private static DataFileParserFactory instance;

	/**
	 * The location of the the data file parser configuration file. This
	 * location is relative to the assets directory of the Android project.
	 */
	private static final String DATA_FILE_PARSER_CONFIG_ASSET = "config/domain/data-controller.cfg";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Hidden constructor (for singleton).
	 */
	private DataFileParserFactory () {}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	public static synchronized DataFileParserFactory getInstance () {

		if (instance == null) {
			// Lazy instantiation of the instance
			instance = new DataFileParserFactory();
		}

		return instance;
	}

	public DataFileParser getDataFileParser (Context context) {

		// Stub for data file parser object
		DataFileParser dataFileParser = null;

		try {
			// Obtain the class name of the data file parser
			InputStream is = context.getAssets().open(DATA_FILE_PARSER_CONFIG_ASSET);
			Properties properties = new Properties();
			properties.load(is);
			is.close();
			
			// Obtain the class name of the data parser
			String dataFileParserName = properties.getProperty("dataFileParser.classname");
			Log.i(this.getClass().getName(), "Configuration file specified data file parser as " + dataFileParserName);

			// Reflexively set the data file parser
			dataFileParser = (DataFileParser) Class.forName(dataFileParserName).newInstance();
			Log.i(this.getClass().getName(), "Data controller set to " + dataFileParserName);

			// Set the context and the location of the data file asset
			String dataFileResource = properties.getProperty("dataFileParser.assetLocation");
			dataFileParser.setContext(context);
			dataFileParser.setAsset(dataFileResource);
		}
		catch (InstantiationException e) {
			// Error occurred while instantiating data file parser
			Log.e(this.getClass().getName(), "Error occurred while instantiating data file parser: " + e);
		}
		catch (IllegalAccessException e) {
			// Illegal access occurred when trying to instantiate
			Log.e(this.getClass().getName(), "Illegal access occurred while setting data file parser: " + e);
		}
		catch (ClassNotFoundException e) {
			// The data file parser could not be found
			Log.e(this.getClass().getName(), "Data file parser class cannot be found: " + e);
		}
		catch (FileNotFoundException e) {
			// The configuration file could not be found
			Log.e(this.getClass().getName(), "Configuration file could not be found: " + e);
		}
		catch (IOException e) {
			// IOException occurred while trying to access the properties file
			Log.e(this.getClass().getName(), "IOException occurred while trying to access the confiuration file: " + e);
		}
		catch (IllegalArgumentException e) {
			// Illegal argument exception while trying to obtain the resource
			Log.e(this.getClass().getName(), "IllegalArgumentException occurred while trying to obtain the resource: " + e);
		}

		return dataFileParser;
	}

}
