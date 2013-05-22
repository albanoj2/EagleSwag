/**
 * @author Justin Albano
 * @date May 22, 2013
 * @file SQLiteDataControllerMappingsParser.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 * TODO: Documentation
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
	
	private Context context;
	private static String DATABASE_TABLE_MAPPINGS_ASSET = "config/sqlite/dbTableMappings.xml";
	
	public SQLiteDataControllerMappingsParser (Context context) {
		this.context = context;
	}
	
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
						String tableClassPackage = ((Element)(tableElement.getElementsByTagName("class").item(0))).getAttribute("package");
						String tableClassName = tableElement.getElementsByTagName("class").item(0).getTextContent();
						String tableFullyQualifiedClass = tableClassPackage + "." + tableClassName;
						String tableConstantName = tableElement.getElementsByTagName("constant").item(0).getTextContent();
						
						try {
							// Obtain the table name from the constant class and name
							tableName = (String) Class.forName(tableFullyQualifiedClass).getDeclaredField(tableConstantName).get(null);
						}
						catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						// Log the discovered information
						Log.d(this.getClass().getName(), "Found mapping from " + DATABASE_TABLE_MAPPINGS_ASSET + ": " +
							fullyQualifiedClass + " -> " + tableName + " [" + tableFullyQualifiedClass + "." + tableConstantName + "]");
				
					}
					else {
						// The string value of the table mapping is set
						tableName = tableElement.getElementsByTagName("explicitName").item(0).getTextContent();
						
						// Log the discovered information
						Log.d(this.getClass().getName(), "Found mapping from " + DATABASE_TABLE_MAPPINGS_ASSET + ": " +
							fullyQualifiedClass + " -> " + tableName);
					}
					
					// Add the class-table entry to the map
					Class<? extends Question> key = Class.forName(fullyQualifiedClass).asSubclass(Question.class);
					table.put(key, tableName);
				}
				
			}
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return table;
	}

}
