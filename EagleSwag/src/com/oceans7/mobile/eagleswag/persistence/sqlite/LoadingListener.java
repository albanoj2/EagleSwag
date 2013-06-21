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

package com.oceans7.mobile.eagleswag.persistence.sqlite;

/**
 * An interface for an observer of a load event while the SQLite database helper
 * is loading questions into the database.
 * 
 * @author Justin Albano
 */
public interface LoadingListener {

	/**
	 * Update method called when an observer is notified of when loading
	 * questions in the database.
	 * 
	 * @param total
	 *            The total of number of questions that are being loaded.
	 * @param current
	 *            The number of questions that have already been loaded into the
	 *            database.
	 */
	public void update (int total, int current);
}
