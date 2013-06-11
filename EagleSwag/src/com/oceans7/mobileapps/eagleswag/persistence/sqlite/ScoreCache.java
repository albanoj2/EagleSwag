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

	public boolean isTotalInCache (String key) {
		return false;
	}

	public boolean isAverageInCache (String key) {
		return false;
	}
}
