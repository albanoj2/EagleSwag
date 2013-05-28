/**
 * @author Justin Albano
 * @date May 27, 2013
 * @file SQLiteDataControllerMappingsParser.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Parses the XML file containing the mappings from question class names,
 *       such as GeneralQuestion, to a SQLite database table that contains the
 *       questions of the same type. For example, this parser will parse the
 *       following mapping:
 * 
 *       <pre>
 *       <map>
 *         <class package="com.oceans7.mobileapps.eagleswag.domain">GeneralQuestion</class>
 *         <table>
 *             <class package="com.oceans7.mobileapps.eagleswag.persistence.sqlite">SQLiteDataControllerConstants</class>
 *             <constant>GENERAL_QUESTIONS_TABLE</constant>
 *         </table>
 * 		</map>
 * </pre>
 * 
 *       And map GeneralQuestion.class to
 *       SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE. Note that the
 *       GeneralQuestion.class is used as the same key that is used in the
 *       SQLiteDataController as the key for retrieving and storing questions in
 *       the SQLite database.
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

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

import com.oceans7.mobileapps.eagleswag.domain.Question;

public class SQLiteDataControllerMappingsParser {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used to access the mappings XML asset containing the mappings
	 * from question class name (ie: GeneralQustions) to the database table
	 * containing the questions of the same type.
	 */
	private Context context;

	/**
	 * The location of the database table mappings asset, relative to the
	 * assets/ directory in the Android project file system.
	 */
	private static String DATABASE_TABLE_MAPPINGS_ASSET = "config/sqlite/dbTableMappings.xml";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Constructor that provides for the initialization of the context used to
	 * access the mappings asset that contains the class name to database table
	 * mappings.
	 * 
	 * @param context
	 */
	public SQLiteDataControllerMappingsParser (Context context) {
		this.setContext(context);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Generates a map of question classes (the key used to retrieve and store
	 * questions in the database within SQLiteDataController) to database table
	 * that contains the questions for the type.
	 * 
	 * @return
	 *         A map of classes (that extend Question) to database tables.
	 */
	public Map<Class<? extends Question>, String> generateMappingsTable () {

		// The map stub to return
		Map<Class<? extends Question>, String> table = new HashMap<Class<? extends Question>, String>();

		try {
			// Initialize the DOM XML document parser
			InputStream is = context.getAssets().open(DATABASE_TABLE_MAPPINGS_ASSET);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = docBuilder.parse(is);
			is.close();

			// Normalize the parsed document
			document.getDocumentElement().normalize();

			// Obtain a reference to the map elements
			NodeList maps = document.getElementsByTagName("map");

			for (int i = 0; i < maps.getLength(); i++) {
				// Iterate over each map node

				// Obtain a reference to the current node
				Node map = maps.item(i);

				if (map.getNodeType() == Node.ELEMENT_NODE) {
					// Reached an element of the <map> type

					// Convert the node to an element
					Element mapElement = (Element) map;

					// Obtain the package name and the name of the class
					String classPackage = ((Element) mapElement.getElementsByTagName("class").item(0)).getAttribute("package");
					String className = mapElement.getElementsByTagName("class").item(0).getTextContent();
					String fullyQualifiedClass = classPackage + "." + className;

					// Get table element
					Node tableNode = mapElement.getElementsByTagName("table").item(0);
					Element tableElement = (Element) tableNode;

					// Table name stub
					String tableName = null;

					if (tableElement.getElementsByTagName("explicitName").item(0) == null) {
						// The string value of the table name is not set, so
						// used the supplied class constant value

						// Obtain the table name data
						String tableClassPackage = ((Element) (tableElement.getElementsByTagName("class").item(0))).getAttribute("package");
						String tableClassName = tableElement.getElementsByTagName("class").item(0).getTextContent();
						String tableFullyQualifiedClass = tableClassPackage + "." + tableClassName;
						String tableConstantName = tableElement.getElementsByTagName("constant").item(0).getTextContent();

						try {
							// Obtain the table from the constant class name
							tableName = (String) Class.forName(tableFullyQualifiedClass).getDeclaredField(tableConstantName).get(null);
						}
						catch (IllegalArgumentException e) {
							Log.e(
								this.getClass().getName(),
								"Illegal argument while trying to obtain the constant value of " + tableConstantName + " from " + tableFullyQualifiedClass + ": " + e);
						}
						catch (IllegalAccessException e) {
							Log.e(this.getClass().getName(),
								"Cannot access " + tableFullyQualifiedClass + " in order to obtain the constant value of the table name: " + e);
						}
						catch (NoSuchFieldException e) {
							Log.e(this.getClass().getName(), "Cannot find the constant specified while trying to obtain constant value: " + e);
						}
						catch (ClassNotFoundException e) {
							Log.e(
								this.getClass().getName(),
								"Cannot find the specified class [" + tableFullyQualifiedClass + "] containing the constants while trying to obtain the value of the constant: " + e);
						}


					}
					else {
						// The string value of the table mapping is set
						tableName = tableElement.getElementsByTagName("explicitName").item(0).getTextContent();

						// Log the discovered information
						Log.i(this.getClass().getName(),
							"Found mapping from " + DATABASE_TABLE_MAPPINGS_ASSET + ": " + fullyQualifiedClass + " -> " + tableName);
					}

					// Add the class-table entry to the map
					Class<? extends Question> key = Class.forName(fullyQualifiedClass).asSubclass(Question.class);
					table.put(key, tableName);
					
					// Log the storage of the mapping
					Log.i(this.getClass().getName(), "Stored mapping: " + fullyQualifiedClass + " -> " + tableName);
				}

			}
		}
		catch (ParserConfigurationException e) {
			Log.e(this.getClass().getName(), "Error with the XML parser configuration while trying to parse table mappings asset: " + e);
		}
		catch (SAXException e) {
			Log.e(this.getClass().getName(), "SAX error occurred while trying to parse table mappings asset: " + e);
		}
		catch (IOException e) {
			Log.e(this.getClass().getName(), "IOException occurred while trying to parse table mappings asset: " + e);
		}
		catch (ClassNotFoundException e) {
			Log.e(this.getClass().getName(), "ClassNotFoundException occurred while trying to parse table mappings asset: " + e);
		}

		return table;
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The context.
	 */
	public Context getContext () {
		return context;
	}

	/**
	 * @param context
	 *            The context to set.
	 */
	public void setContext (Context context) {
		this.context = context;
	}

}
