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

public class QuestionType {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private String name;
	private String dataAsset;
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
		return super.toString() + ": name='" + this.name + "', dataAsset='" + this.dataAsset + "', SQLiteTable='" + this.sqliteTable + "'";
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
}
