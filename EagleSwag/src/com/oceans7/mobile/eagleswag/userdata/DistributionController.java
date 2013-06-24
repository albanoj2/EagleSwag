/*
 * EagleSwag Android Mobile Application
 * Copyright (C) 2013 Oceans7
 * Oceans7 Mobile Applications Development Team
 * 
 * This software is free and governed by the terms of the GNU General Public
 * License as published by the Free Software Foundation. This software may be
 * redistributed and/or modified in accordance with version 3, or any later
 * version, of the GNU General Public License.
 * 
 * This software is distributed without any warranty; without even the implied
 * warranty of merchantability or fitness for a particular purpose. For further
 * detail, refer to the GNU General Public License, which can be found in the
 * LICENSE.txt file at the root directory of this project, or online at:
 * 
 * <http://www.gnu.org/licenses/>
 */

package com.oceans7.mobile.eagleswag.userdata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.domain.RoundType;

/**
 * TODO Class documentation
 * TODO Add set(...) method
 * TODO Investigate a way to modify the properties (possibly a database)
 * 
 * @author Justin Albano
 */
public class DistributionController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The prefix for the distribution properties found in the distribution
	 * configuration file. Note that the prefix sound not include a trailing
	 * period (.), as it is implicit.
	 */
	private static final String PREFIX = "distribution";

	/**
	 * The default distribution count.
	 */
	public static final int DEFAULT_DISTRIBUTION = 5;

	/**
	 * The context used to open the configuration file.
	 */
	private Context context;

	/**
	 * The path (including file name) of the configuration file.
	 */
	private String file;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Create the distribution controller.
	 * 
	 * @param file
	 *            The location of the configuration file containing the
	 *            distribution configuration data.
	 * @param context
	 *            The context used to access the configuration data file.
	 */
	public DistributionController (String file, Context context) {
		this.context = context;
		this.file = file;
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains the distribution count for a specified round type, and further, a
	 * specified question type. If a distribution count for the provided
	 * constraints, a default value is returned.
	 * 
	 * @param type
	 *            The round type constraint.
	 * @param key
	 *            The question type constraint.
	 * @return
	 *         The distribution count (the number of questions) for the
	 *         specified constraints.
	 */
	public int getDistribution (RoundType type, Class<? extends Question> key) {

		// A stub for the distribution
		int intDistribution = 0;

		try {
			// Obtain the number of questions that "should" be loaded
			Properties properties = new Properties();
			InputStream is = this.context.getAssets().open(file);
			properties.load(is);
			is.close();

			// Obtain the distribution for the type and key provided
			String distribution = properties.getProperty(this.generatePropertyName(type, key));

			if (distribution != null) {
				// Set the distribution if it is configured
				intDistribution = Integer.parseInt(distribution);
			}
			else {
				// Use the default value if no value is set in the configuration
				intDistribution = DEFAULT_DISTRIBUTION;
			}

		}
		catch (FileNotFoundException e) {
			// The configuration file specified cannot be found
			Log.e(this.getClass().getName(), "The distribution configuration '" + file + "' cannot be found: " + e);

			// Use the default distribution value
			intDistribution = DEFAULT_DISTRIBUTION;
		}
		catch (IOException e) {
			// An IO exception occurred while opening the configuration file
			Log.e(this.getClass().getName(), "An IO exception occurred while opening configuration file: " + e);

			// Use the default distribution value
			intDistribution = DEFAULT_DISTRIBUTION;
		}

		return intDistribution;
	}

	public void setDistribution (RoundType type, Class<? extends Question> key, int distribution) {
		
	}

	/**
	 * Helper method that generates the property name from the supplied
	 * parameters.
	 * 
	 * @param type
	 *            The round type.
	 * @param key
	 *            The question type (key).
	 * @return
	 *         A property name from the supplied parameters.
	 */
	private String generatePropertyName (RoundType type, Class<? extends Question> key) {
		return PREFIX + "." + type.getName() + "." + key.getSimpleName();
	}

}
