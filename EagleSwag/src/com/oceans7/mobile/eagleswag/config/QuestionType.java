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

package com.oceans7.mobile.eagleswag.config;

import com.oceans7.mobile.eagleswag.config.components.SqliteConfiguration;

/**
 * A question type specification that mimics a question type found in the
 * question type configuration file. This question type has pluggable components
 * for the persistence configuration of a question type. This allows for new
 * persistence configuration data to be added to the configuration file and be,
 * likewise, added to this question type object.
 * 
 * @author Justin Albano
 */
public class QuestionType {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The name of the question type.
	 */
	private String name;

	/**
	 * The SQLite persistence configuration. This configuration contains the
	 * information pertaining to the SQLite persistence layer.
	 * 
	 * @note This attribute may be null if no SQLite configuration data is
	 *       specified within the question type configuration file.
	 */
	private SqliteConfiguration sqliteConfiguration;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Default constructor.
	 */
	public QuestionType () {}

	/**
	 * Parameterized constructor that provides for the initialization of the
	 * name of the question type.
	 * 
	 * @param name
	 *            The name of the question type.
	 */
	public QuestionType (String name) {
		this.setName(name);
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The name.
	 */
	public String getName () {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName (String name) {
		this.name = name;
	}

	/**
	 * @return
	 *         The sqliteConfiguration.
	 */
	public SqliteConfiguration getSqliteConfiguration () {
		return sqliteConfiguration;
	}

	/**
	 * @param sqliteConfiguration
	 *            The sqliteConfiguration to set.
	 */
	public void setSqliteConfiguration (SqliteConfiguration sqliteConfiguration) {
		this.sqliteConfiguration = sqliteConfiguration;
	}

}
