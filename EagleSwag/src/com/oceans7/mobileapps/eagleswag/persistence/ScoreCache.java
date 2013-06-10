/**
 * @author Justin Albano
 * @date Jun 9, 2013
 * @file ScoreCache.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       TODO Documentation
 *       TODO Complete implementation
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.util.HashMap;
import java.util.Map;

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

	public boolean isTotalInCache (String key) {
		return false;
	}

	public boolean isAverageInCache (String key) {
		return false;
	}
}
