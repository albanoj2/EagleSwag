/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file AllTests.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;

/**
 * A test suite containing all tests for my application.
 */
public class AllTests extends TestSuite {
	public static Test suite () {
		return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
	}
}