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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.util.Log;

/**
 * A cache containing the score data from the database, include total scores and
 * average scores. The total and average scores for each score type are accessed
 * through a key.
 * 
 * @author Justin Albano
 */
public class ScoreCache {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The cached total score data.
	 */
	private Map<String, Integer> totalsMap;

	/**
	 * The cache average score data.
	 */
	private Map<String, Integer> averagesMap;

	/**
	 * The cached number of scores data (which is used for calculating the
	 * average cached score).
	 */
	private Map<String, Integer> numberOfValuesMap;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Default constructor that creates the necessary data structures for
	 * storing the cached score data.
	 */
	public ScoreCache () {

		// Initialize the empty maps
		this.totalsMap = new ConcurrentHashMap<String, Integer>();
		this.averagesMap = new ConcurrentHashMap<String, Integer>();
		this.numberOfValuesMap = new ConcurrentHashMap<String, Integer>();
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Returns the current total value cached for the provided key. Note that an
	 * Integer object is returned: This object is null if no value for the given
	 * key is currently in cache; if a total exists in cache for the given key,
	 * the total for the key will be returned.
	 * 
	 * @param key
	 *            The key for the total value.
	 * @return
	 *         Null if no total is associated with the key; the total value
	 *         associated with the key if a value is set for the key.
	 */
	public Integer getTotal (String key) {
		return this.totalsMap.get(key);
	}

	/**
	 * Returns the current average value cached for the provided key. Note that
	 * an Integer object is returned: This object is null if no value for the
	 * given key is currently in cache; if an average exists in cache for the
	 * given key, the average for the key will be returned.
	 * 
	 * @param key
	 *            The key for the average value.
	 * @return
	 *         Null if no average is associated with the key; the average value
	 *         associated with the key if a value is set for the key.
	 */
	public Integer getAverage (String key) {
		return this.averagesMap.get(key);
	}

	/**
	 * Factors a value into the current total. For a total, factoring in a new
	 * value adds the provided value to the current total.
	 * <p/>
	 * <strong>Preconditions:</strong>
	 * <ul>
	 * <li>The total has been loaded through the {@link #loadTotal(String, int)}
	 * method</li>
	 * </ul>
	 * 
	 * @param key
	 *            The key for the cached total.
	 * @param value
	 *            The value to factor into the total.
	 */
	public void factorIntoTotal (String key, int value) {

		if (!this.isTotalInCache(key)) {
			// Set the total value if it is not in cache
			this.loadTotal(key, value);
		}
		else {
			// Add the provided value into the total value
			int currentTotal = this.totalsMap.get(key);
			this.loadTotal(key, currentTotal + value);
		}
	}

	/**
	 * Factors a value into the current average. For an average, factoring a new
	 * value re-weighs the average to include the new value. This method should
	 * be used after
	 * <p/>
	 * <strong>Preconditions:</strong>
	 * <ul>
	 * <li>The average has been loaded through the
	 * {@link #loadAverage(String, int, int)} method</li>
	 * </ul>
	 * 
	 * @param key
	 *            The key for the cached average.
	 * @param value
	 *            The new value to factor into the average.
	 */
	public void factorIntoAverage (String key, int value) {

		if (!this.isAverageInCache(key)) {
			// Set the average if it is not in cache (assumes this is the first
			// value; therefore, the number of values for the key is set to 1)
			this.loadAverage(key, value, 1);
		}
		else {
			// Factor in the value to the average: convert the old average to
			// have the new number of values as a denominator, and add the
			// value, divided by the new number of values, to this newly
			// weighted value

			// Obtain the original values (casted to ensure proper arithmetic)
			int originalNumber = this.numberOfValuesMap.get(key);
			double originalAverage = this.averagesMap.get(key);

			// Calculate the new average
			int newAverage = (int) Math.round(((originalAverage * originalNumber) + value) / (originalNumber + 1));
			Log.d(this.getClass().getName(), "Original average: " + originalAverage);
			Log.d(this.getClass().getName(), "Original number: " + originalNumber);
			Log.d(this.getClass().getName(), "Original average times number: " + (originalAverage * originalNumber));

			// Save the new average
			this.averagesMap.put(key, newAverage);

			// Increment the number of values for the key
			this.incrementValueCount(key);
		}
	}

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
	 * Loads the total value for a key in the cache. This method should be used
	 * the first time the totals cache is loaded.
	 * 
	 * @param key
	 *            The key for the total value.
	 * @param value
	 *            The total value.
	 */
	public void loadTotal (String key, int value) {

		// Set the total value associated with the key
		this.totalsMap.put(key, value);
	}

	/**
	 * Loads the average value for a key in the cache. This method should be
	 * used the first time the average cache is loaded.
	 * 
	 * @param key
	 *            The key for the average value.
	 * @param value
	 *            The average value.
	 * @param numberOfValues
	 *            The number of values that makes up the average.
	 */
	public void loadAverage (String key, int value, int numberOfValues) {

		// Set the average value associated with the key
		this.averagesMap.put(key, value);

		// Reset the numbe of values in the average
		this.numberOfValuesMap.put(key, numberOfValues);
	}

	/**
	 * Helper method to increment the number of values that are factored in the
	 * current average.
	 * 
	 * @param key
	 *            The key for the average in cache.
	 */
	private void incrementValueCount (String key) {

		if (this.numberOfValuesMap.get(key) == null) {
			// Set the default value to 1
			this.numberOfValuesMap.put(key, 1);
		}
		else {
			// Increment the number associated with the key
			this.numberOfValuesMap.put(key, this.numberOfValuesMap.get(key) + 1);
			Log.d(this.getClass().getName(), "New average count value for '" + key + "': " + this.numberOfValuesMap.get(key));
		}
	}
}
