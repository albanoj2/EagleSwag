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

package com.oceans7.mobile.eagleswag.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobile.eagleswag.config.components.SqliteConfiguration;
import com.oceans7.mobile.eagleswag.domain.questions.Question;

/**
 * A parser for the question type configuration data. This parser acts as the
 * real subject in the proxy pattern established for parsing the question type
 * configuration file. In order to extract the configuration data from the
 * question type configuration file, a question type object is created for each
 * of the question types found in the configuration file.
 * 
 * <p>
 * <em>Cited sources:</em>
 * <ul>
 * <li>http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/</li>
 * </ul>
 * </p>
 * 
 * @author Justin Albano
 */
public class ConfigurationParser implements ConfigurationController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The location of the question type configuration file within the assets
	 * directory of the Android project file structure.
	 */
	private static final String ASSET_LOCATION = "config/data/QuestionTypeConfig.xml";

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.config.ConfigurationController#getQuestionTypes(java.lang.Class)
	 */
	@Override
	public Map<Class<? extends Question>, QuestionType> getQuestionTypes (Context context) {

		// The stub for the question type map
		Map<Class<? extends Question>, QuestionType> questionTypeMap = new HashMap<Class<? extends Question>, QuestionType>();

		try {

			// Parse the XML file containing the question types
			InputStream is = context.getAssets().open(ASSET_LOCATION);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(is);
			is.close();

			// Normalize the parsed XML document
			document.getDocumentElement().normalize();

			// Create a list of all of the 'type' elements
			NodeList typeList = document.getElementsByTagName("type");

			for (int i = 0; i < typeList.getLength(); i++) {
				// Iterate through each of the types in the configuration file

				// A reference to the current node
				Node typeNode = typeList.item(i);

				if (typeNode.getNodeType() == Node.ELEMENT_NODE) {
					// The current node is an element

					// Create a basic question type object
					QuestionType questionType = new QuestionType();

					// Transform the node into an element
					Element typeElement = (Element) typeNode;

					// Obtain the element objects for each of the sub-elements
					Element keyElement = (Element) typeElement.getElementsByTagName("key").item(0);
					Element persistenceElement = (Element) typeElement.getElementsByTagName("persistence").item(0);
					Element jsonElement = (Element) persistenceElement.getElementsByTagName("json").item(0);
					Element sqliteElement = (Element) persistenceElement.getElementsByTagName("sqlite").item(0);

					// Extract the name of the question type
					String name = typeElement.getAttribute("name");
					questionType.setName(name);

					// Create the class key for map
					String packageName = keyElement.getAttribute("package");
					String className = keyElement.getTextContent().trim();
					String qualifiedClassName = packageName + "." + className;
					Class<? extends Question> clazz = Class.forName(qualifiedClassName).asSubclass(Question.class);

					if (jsonElement != null) {
						// The JSON element has been specified

						// The JSON data element within the configuration file
						Element jsonIdElement = (Element) jsonElement.getElementsByTagName("id").item(0);

						// The data within the JSON element
						String jsonId = jsonIdElement.getTextContent().trim();

						// The JSON data was added
						Log.i(this.getClass().getName(), "The JSON data was found for '" + name + "': id: <" + jsonId + ">");
					}

					if (sqliteElement != null) {
						// The SQLite element has been specified

						// The SQLite data element within the configuration file
						Element tableElement = (Element) sqliteElement.getElementsByTagName("table").item(0);

						// The SQLite data within the element
						String table = tableElement.getTextContent().trim();

						// Set the SQLite data for the question type
						questionType.setSqliteConfiguration(new SqliteConfiguration(table));

						// The SQLite data was added
						Log.i(this.getClass().getName(), "The SQLite data was found for '" + name + "': table: <" + table + ">");
					}

					// Insert the question type into the map
					questionTypeMap.put(clazz, questionType);
					Log.i(this.getClass().getName(), "Added question type to map: key<" + clazz.getCanonicalName() + ">: " + questionType);
				}
			}

		}
		catch (IOException e) {
			// An IO exception occurred while opening the configuration file
			Log.e(this.getClass().getName(), "IOException occurred while trying to parse configuration file: " + e);
		}
		catch (SAXException e) {
			// The SAX parser encountered an exception while parsing the file
			Log.e(this.getClass().getName(), "SAXException occurred while trying to parse configuration file: " + e);
		}
		catch (ParserConfigurationException e) {
			// The SAX parser was improperly configured for parsing the file
			Log.e(this.getClass().getName(), "ParserConfigurationException occurred while trying to parse configuration file: " + e);
		}
		catch (ClassNotFoundException e) {
			// An object could not be reflexively created (key, parser, etc.)
			Log.e(this.getClass().getName(),
				"ClassNotFoundException occurred while trying to create a class specified in the configuration file: " + e);
		}

		// Return question type map (which may be empty if there was an error)
		return questionTypeMap;
	}
}
