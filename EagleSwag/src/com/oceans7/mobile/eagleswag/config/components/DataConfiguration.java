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

package com.oceans7.mobile.eagleswag.config.components;

import com.oceans7.mobile.eagleswag.persistence.DataFileParserStrategy;

/**
 * Specification representing the data configuration element of the question
 * type configuration file.
 * 
 * @author Justin Albano
 */
public class DataConfiguration {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The location of the data file asset for a question type. This location is
	 * relative to the assets/ directory of the Android project directory.
	 */
	private String asset;

	/**
	 * Class representing the parser to use to obtain the data from the data
	 * file specified for a question type.
	 */
	private Class<? extends DataFileParserStrategy> parserStrategy;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor that provides for the specification of the data
	 * file asset and the parser strategy class for a data type.
	 * 
	 * @param asset
	 *            The location, relative to the assets/ directory, of the data
	 *            file asset.
	 * @param parserStrategy
	 *            The class used to parse the data file asset.
	 */
	public DataConfiguration (String asset, Class<? extends DataFileParserStrategy> parserStrategy) {

		// Set the data configuration
		this.setAsset(asset);
		this.setParserStrategy(parserStrategy);
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The asset.
	 */
	public String getAsset () {
		return asset;
	}

	/**
	 * @param asset
	 *            The asset to set.
	 */
	public void setAsset (String asset) {
		this.asset = asset;
	}

	/**
	 * @return
	 *         The parserStrategy.
	 */
	public Class<? extends DataFileParserStrategy> getParserStrategy () {
		return parserStrategy;
	}

	/**
	 * @param parserStrategy
	 *            The parserStrategy to set.
	 */
	public void setParserStrategy (Class<? extends DataFileParserStrategy> parserStrategy) {
		this.parserStrategy = parserStrategy;
	}

}
