/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfigParser.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 * TODO: Documentation
 * Thanks to: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
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

public class QuestionTypeConfigParser implements QuestionTypeConfigController {
	
	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	
	private static final String ASSET_LOCATION = "config/data/QuestionTypeConfig.xml";
	
	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * @see com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigController#getQuestionTypes(java.lang.Class)
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
					Element configElement = (Element) typeElement.getElementsByTagName("configuration").item(0);
					Element dataElement = (Element) configElement.getElementsByTagName("data").item(0);
					Element assetElement = (Element) dataElement.getElementsByTagName("asset").item(0);
					Element jsonElement = (Element) configElement.getElementsByTagName("json").item(0);
					Element jsonIdElement = (Element) jsonElement.getElementsByTagName("id").item(0);
					Element sqliteElement = (Element) configElement.getElementsByTagName("sqlite").item(0);
					Element tableElement = (Element) sqliteElement.getElementsByTagName("table").item(0);
					
					// Extract the needed data from the elements of the type
					questionType.setName(typeElement.getAttribute("name"));
					questionType.setDataAsset(assetElement.getAttribute("path") + assetElement.getTextContent());
					questionType.setJsonId(jsonIdElement.getTextContent());
					questionType.setSqliteTable(tableElement.getTextContent());
					
					// Create the class key for map
					String packageName = keyElement.getAttribute("package");
					String className = keyElement.getTextContent();
					String qualifiedClassName = packageName + "." + className;
					Class<? extends Question> clazz = Class.forName(qualifiedClassName).asSubclass(Question.class);
					
					// Insert the question type into the map
					questionTypeMap.put(clazz, questionType);
					Log.i(this.getClass().getName(), "Added question type to map: key<" + clazz.getCanonicalName() + ">: " + questionType);
				}
			}
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return questionTypeMap;
	}

}
