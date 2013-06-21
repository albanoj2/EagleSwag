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

package com.oceans7.mobile.eagleswag.test.persistence;

import com.oceans7.mobile.eagleswag.domain.questions.Question;

/**
 * A fake question to test the JSON data file parser.
 *
 * @author Justin Albano
 */
public class FakeQuestion extends Question {

	public FakeQuestion (Integer id, String text, Integer yesValue, Integer noValue, Integer usedCount) {
		super(id, text, yesValue, noValue, usedCount);
	}

}
