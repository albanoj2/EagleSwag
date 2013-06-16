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

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerHelper;
import com.oceans7.mobile.eagleswag.util.LoadingListener;

/**
 * TODO Class documentation
 * 
 * @author Justin Albano
 */
public class SqliteDataControllerHelperTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private Context context;
	private SqliteDataControllerHelper helper;
	private boolean listener1Called;
	private boolean listener2Called;

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

		// Create context and helper
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");
		this.helper = new SqliteDataControllerHelper(this.context);

		// Set listener flags to false
		listener1Called = false;
		listener2Called = false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.test.InstrumentationTestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerHelper#addLoadingListener(com.oceans7.mobile.eagleswag.util.LoadingListener)}
	 * .
	 */
	public void testAddLoadingListener () {

		// Create loading listener 1
		LoadingListener ll1 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener1Called = true;
			}
		};

		// Create loading listener 2
		LoadingListener ll2 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener2Called = true;
			}
		};

		// Add loading listeners to the helper
		this.helper.addLoadingListener(ll1);
		this.helper.addLoadingListener(ll2);

		// Call the loading listeners
		this.helper.updateLoadingListeners(0, 0);

		// Ensure the listeners were called
		assertTrue("Loading listener 1 was called:", listener1Called);
		assertTrue("Loading listener 2 was called:", listener2Called);
	}
	
	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerHelper#removeLoadingListener(com.oceans7.mobile.eagleswag.util.LoadingListener)}
	 * .
	 */
	public void testRemoveLoadingListener () {

		// Create loading listener 1
		LoadingListener ll1 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener1Called = true;
			}
		};

		// Create loading listener 2
		LoadingListener ll2 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener2Called = true;
			}
		};

		// Add loading listeners to the helper
		this.helper.addLoadingListener(ll1);
		this.helper.addLoadingListener(ll2);
		
		// Remove the loading listener from the helper
		this.helper.removeLoadingListener(ll1);
		this.helper.removeLoadingListener(ll2);

		// Call the loading listeners
		this.helper.updateLoadingListeners(0, 0);

		// Ensure the listeners were not called
		assertFalse("Loading listener 1 was not called:", listener1Called);
		assertFalse("Loading listener 2 was not called:", listener2Called);
	}
}
