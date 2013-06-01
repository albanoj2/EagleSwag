/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file Type.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       TODO: Documentation
 * 
 */

package com.oceans7.mobileapps.eagleswag.config;

import com.oceans7.mobileapps.eagleswag.persistence.DataFileParserStrategy;

public class QuestionType {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private String name;
	private String dataAsset;
	private Class<? extends DataFileParserStrategy> parserStrategy;
	private String jsonId;
	private String sqliteTable;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public QuestionType () {}

	public QuestionType (String name) {
		this.setName(name);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return super.toString() + ": name='" + this.name + "', dataAsset='" + this.dataAsset + "', parserStrategy='" + this.parserStrategy.getName() + "', SQLiteTable='" + this.sqliteTable + "'";
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The name.
	 */
	public String getName () {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName (String name) {
		this.name = name;
	}

	/**
	 * @return
	 *         The dataAsset.
	 */
	public String getDataAsset () {
		return dataAsset;
	}

	/**
	 * @param dataAsset
	 *            The dataAsset to set.
	 */
	public void setDataAsset (String dataAsset) {
		this.dataAsset = dataAsset;
	}

	/**
	 * @return
	 *         The sqliteTable.
	 */
	public String getSqliteTable () {
		return sqliteTable;
	}

	/**
	 * @param sqliteTable
	 *            The sqliteTable to set.
	 */
	public void setSqliteTable (String sqliteTable) {
		this.sqliteTable = sqliteTable;
	}

	/**
	 * @return
	 *         The jsonId.
	 */
	public String getJsonId () {
		return jsonId;
	}

	/**
	 * @param jsonId
	 *            The jsonId to set.
	 */
	public void setJsonId (String jsonId) {
		this.jsonId = jsonId;
	}

	/**
	 * @return
	 *         The parserStrategy.
	 */
	public Class<? extends DataFileParserStrategy> getParserStrategy () {
		return parserStrategy;
	}

	/**
	 * @param parserStrategy
	 *            The parserStrategy to set.
	 */
	public void setParserStrategy (Class<? extends DataFileParserStrategy> parserStrategy) {
		this.parserStrategy = parserStrategy;
	}

}
