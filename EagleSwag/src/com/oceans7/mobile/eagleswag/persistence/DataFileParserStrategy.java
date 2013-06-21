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

package com.oceans7.mobile.eagleswag.persistence;

import java.util.Queue;

import com.oceans7.mobile.eagleswag.domain.Question;

/**
 * Interface for a data file parser strategy. The data file parser strategy is
 * required to provide a method for obtaining the questions from the data file
 * containing question data.
 * 
 * @author Justin Albano
 */
public interface DataFileParserStrategy {
	
	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains the questions from the data file for a specific question type.
	 * For example, to retrieve the general questions from the data file, the
	 * key 'GeneralQuestion.class' is used.
	 * 
	 * @param key
	 *            Specifies which question type to retrieve from the data file.
	 *            This key also specifies the type of the elements contained in
	 *            the queue. For example, if GeneralQuestion.class is used as
	 *            the key, the general questions from the data file will be
	 *            returned, and the queue returned will contain elements of type
	 *            GeneralQuestion.
	 * @return
	 *         A queue containing questions of the type specified by the key
	 *         provided.
	 */
	public <T extends Question> Queue<T> getQuestions (Class<T> key);
}
