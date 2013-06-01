/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfigParser.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       TODO: Documentation
 *       Thanks to:
 *       http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */

package com.oceans7.mobileapps.eagleswag.config;

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
import com.oceans7.mobileapps.eagleswag.persistence.DataFileParserStrategy;

public class ConfigurationParser implements ConfigurationController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final String ASSET_LOCATION = "config/data/QuestionTypeConfig.xml";

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.config.ConfigurationController#getQuestionTypes(java.lang.Class)
	 * 
	 *      TODO make the configuration fields optional and parse them only if
	 *      they exist (otherwise, set them to null)
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
					Element dataElement = (Element) typeElement.getElementsByTagName("data").item(0);
					Element persistenceElement = (Element) typeElement.getElementsByTagName("persistence").item(0);
					Element assetElement = (Element) dataElement.getElementsByTagName("asset").item(0);
					Element parserStratElement = (Element) dataElement.getElementsByTagName("parserStrategy").item(0);
					Element jsonElement = (Element) persistenceElement.getElementsByTagName("json").item(0);
					Element jsonIdElement = (Element) jsonElement.getElementsByTagName("id").item(0);
					Element sqliteElement = (Element) persistenceElement.getElementsByTagName("sqlite").item(0);
					Element tableElement = (Element) sqliteElement.getElementsByTagName("table").item(0);

					// Extract the needed data from the elements of the type
					questionType.setName(typeElement.getAttribute("name"));
					questionType.setDataAsset(assetElement.getAttribute("path") + assetElement.getTextContent());
					questionType.setJsonId(jsonIdElement.getTextContent().trim());
					questionType.setSqliteTable(tableElement.getTextContent().trim());
					
					// Create the parser strategy class and set the attribute
					String parserStratPackage = parserStratElement.getAttribute("package");
					String parserStratClass= parserStratElement.getTextContent().trim();
					String parserStratQualifiedClass = parserStratPackage + "." + parserStratClass;
					Class<? extends DataFileParserStrategy> parserClass = Class.forName(parserStratQualifiedClass).asSubclass(DataFileParserStrategy.class);
					questionType.setParserStrategy(parserClass);

					// Create the class key for map
					String packageName = keyElement.getAttribute("package");
					String className = keyElement.getTextContent().trim();
					String qualifiedClassName = packageName + "." + className;
					Class<? extends Question> clazz = Class.forName(qualifiedClassName).asSubclass(Question.class);

					// Insert the question type into the map
					questionTypeMap.put(clazz, questionType);
					Log.i(this.getClass().getName(), "Added question type to map: key<" + clazz.getCanonicalName() + ">: " + questionType);
				}
			}

		}
		catch (IOException e) {
			Log.e(this.getClass().getName(), "IOException occurred while trying to parse configuration file: " + e);
		}
		catch (SAXException e) {
			Log.e(this.getClass().getName(), "SAXException occurred while trying to parse configuration file: " + e);
		}
		catch (ParserConfigurationException e) {
			Log.e(this.getClass().getName(), "ParserConfigurationException occurred while trying to parse configuration file: " + e);
		}
		catch (ClassNotFoundException e) {
			Log.e(this.getClass().getName(), "ClassNotFoundException occurred while trying to create a class specified in the configuration file: " + e);
		}

		return questionTypeMap;
	}

}
