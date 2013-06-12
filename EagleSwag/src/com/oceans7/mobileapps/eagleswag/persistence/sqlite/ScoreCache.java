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

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Documentation
 * TODO Complete implementation
 * 
 * @author Justin Albano
 */
public class ScoreCache {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private Map<String, Integer> totalsMap;
	private Map<String, Integer> averagesMap;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public ScoreCache () {

		// Initialize the empty maps
		this.totalsMap = new HashMap<String, Integer>();
		this.averagesMap = new HashMap<String, Integer>();
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	public int getTotal (String key) {
		return 0;
	}

	public int getAverage (String key) {
		return 0;
	}

	public void factorIntoTotal (String key, int value) {}

	public void factorIntoAverage (String key, int value) {}

	/**
	 * Returns true if there is a total set for the key.
	 * 
	 * @param key
	 *            The key for cached total value.
	 * @return
	 *         True if there is a non-null total associated with the key.
	 */
	public boolean isTotalInCache (String key) {

		// Return if the value in the totals is null for the provided key
		return this.totalsMap.get(key) != null;
	}

	/**
	 * Returns true if there is an average set for the key.
	 * 
	 * @param key
	 *            The key for the cached average value.
	 * @return
	 *         True if there is a non-null total associated with the key.
	 */
	public boolean isAverageInCache (String key) {

		// Return if the value in the averages is null for the provided key
		return this.averagesMap.get(key) != null;
	}

	/**
	 * Sets the total value for a key in the cache.
	 * 
	 * @param key
	 *            The key for the total value.
	 * @param value
	 *            The total value.
	 */
	public void setTotal (String key, int value) {

		// Set the total value associated with the key
		this.totalsMap.put(key, value);
	}

	/**
	 * Sets the average value for a key in the cache.
	 * 
	 * @param key
	 *            The key for the average value.
	 * @param value
	 *            The average value.
	 */
	public void setAverage (String key, int value) {

		// Set the average value associated with the key
		this.averagesMap.put(key, value);
	}
}
