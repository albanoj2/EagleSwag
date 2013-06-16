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

package com.oceans7.mobile.eagleswag.test.persistence.sqlite;

import junit.framework.TestCase;

import com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache;

/**
 * Test cases for
 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache}.
 * 
 * @author Justin Albano
 */
public class ScoreCacheTest extends TestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The score cache under test.
	 */
	private ScoreCache cache;

	/***************************************************************************
	 * Setup & Tear Down
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp () throws Exception {
		super.setUp();

		// Create the cache
		this.cache = new ScoreCache();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();

		// Destroy the cache
		this.cache = null;
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#getTotal(java.lang.String)}
	 * .
	 */
	public void testGetTotal () {

		// Add test total value into the cache
		this.cache.loadTotal("test", 10);

		// Ensure the correct value is returned
		assertEquals("Total value is set:", 10, (int) this.cache.getTotal("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#getAverage(java.lang.String)}
	 * .
	 */
	public void testGetAverage () {

		// Add test average value into the cache
		this.cache.loadAverage("test", 10, 1);

		// Ensure the correct value is returned
		assertEquals("Average value is set:", 10, (int) this.cache.getAverage("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#factorIntoTotal(java.lang.String, int)}
	 * .
	 */
	public void testFactorIntoTotal () {

		// Ensure a total is not associated with the key
		assertNull("Total is empty:", this.cache.getTotal("test"));

		// Factor in a single value and ensure that is the total value
		this.cache.factorIntoTotal("test", 25);
		assertNotNull("Total is defined:", this.cache.getTotal("test"));
		assertEquals("First factor set value:", 25, (int) this.cache.getTotal("test"));

		// Factor in another value and ensure new total is the sum of the two
		this.cache.factorIntoTotal("test", 50);
		assertNotNull("Total is defined:", this.cache.getTotal("test"));
		assertEquals("Total is sum of two factors:", 75, (int) this.cache.getTotal("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#factorIntoTotal(java.lang.String, int)}
	 * .
	 */
	public void testFactorIntoTotalNegative () {

		// Ensure a total is not associated with the key
		assertNull("Total is empty:", this.cache.getTotal("test"));

		// Factor in a single value and ensure that is the total value
		this.cache.factorIntoTotal("test", 25);
		assertNotNull("Total is defined:", this.cache.getTotal("test"));
		assertEquals("First factor set value:", 25, (int) this.cache.getTotal("test"));

		// Factor in another value and ensure new total is the sum of the two
		this.cache.factorIntoTotal("test", -50);
		assertNotNull("Total is defined:", this.cache.getTotal("test"));
		assertEquals("Total is sum of two factors:", -25, (int) this.cache.getTotal("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#factorIntoTotal(java.lang.String, int)}
	 * .
	 */
	public void testFactorIntoTotalZero () {

		// Ensure a total is not associated with the key
		assertNull("Total is empty:", this.cache.getTotal("test"));

		// Factor in a single value and ensure that is the total value
		this.cache.factorIntoTotal("test", 25);
		assertNotNull("Total is defined:", this.cache.getTotal("test"));
		assertEquals("First factor set value:", 25, (int) this.cache.getTotal("test"));

		// Factor in another value and ensure new total is the sum of the two
		this.cache.factorIntoTotal("test", 0);
		assertNotNull("Total is defined:", this.cache.getTotal("test"));
		assertEquals("Total is sum of two factors:", 25, (int) this.cache.getTotal("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#factorIntoAverage(java.lang.String, int)}
	 * .
	 */
	public void testFactorIntoAverage () {

		// Ensure an average is not associated with the key
		assertNull("Average is empty:", this.cache.getAverage("test"));

		// Factor in a single value and ensure that is the average value
		this.cache.factorIntoAverage("test", 25);
		assertNotNull("Average is defined:", this.cache.getAverage("test"));
		assertEquals("First factor set value:", 25, (int) this.cache.getAverage("test"));

		// Factor in another value and ensure new average is correct
		this.cache.factorIntoAverage("test", 70);
		assertNotNull("Average is defined:", this.cache.getAverage("test"));
		assertEquals("Average is correct:", 48, (int) this.cache.getAverage("test"));

		// Factor in another value and ensure new average is correct
		this.cache.factorIntoAverage("test", 100);
		assertNotNull("Average is defined:", this.cache.getAverage("test"));
		assertEquals("Average is correct:", 65, (int) this.cache.getAverage("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#factorIntoAverage(java.lang.String, int)}
	 * .
	 */
	public void testFactorIntoAverageNegative () {

		// Ensure an average is not associated with the key
		assertNull("Average is empty:", this.cache.getAverage("test"));

		// Factor in a single value and ensure that is the average value
		this.cache.factorIntoAverage("test", 25);
		assertNotNull("Average is defined:", this.cache.getAverage("test"));
		assertEquals("First factor set value:", 25, (int) this.cache.getAverage("test"));

		// Factor in another value and ensure new average is correct
		this.cache.factorIntoAverage("test", -10);
		assertNotNull("Average is defined:", this.cache.getAverage("test"));
		assertEquals("Average is correct:", 8, (int) this.cache.getAverage("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#factorIntoAverage(java.lang.String, int)}
	 * .
	 */
	public void testFactorIntoAverageZero () {

		// Ensure an average is not associated with the key
		assertNull("Average is empty:", this.cache.getAverage("test"));

		// Factor in a single value and ensure that is the average value
		this.cache.factorIntoAverage("test", 50);
		assertNotNull("Average is defined:", this.cache.getAverage("test"));
		assertEquals("First factor set value:", 50, (int) this.cache.getAverage("test"));

		// Factor in another value and ensure new average is correct
		this.cache.factorIntoAverage("test", 0);
		assertNotNull("Average is defined:", this.cache.getAverage("test"));
		assertEquals("Average is correct:", 25, (int) this.cache.getAverage("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#isTotalInCache(java.lang.String)}
	 * .
	 */
	public void testIsTotalInCache () {

		// Ensure that the cache does not contain the test data
		assertEquals("Cache does not contain data:", false, this.cache.isTotalInCache("test"));

		// Add the test data into the cache
		this.cache.loadTotal("test", 10);

		// Ensure that the cache now contains the test data
		assertEquals("Cache contains data:", true, this.cache.isTotalInCache("test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.ScoreCache#isAverageInCache(java.lang.String)}
	 * .
	 */
	public void testIsAverageInCache () {

		// Ensure that the cache does not contain the test data
		assertEquals("Cache does not contain data:", false, this.cache.isAverageInCache("test"));

		// Add the test data into the cache
		this.cache.loadAverage("test", 10, 1);

		// Ensure that the cache now contains the test data
		assertEquals("Cache contains data:", true, this.cache.isAverageInCache("test"));
	}

}
