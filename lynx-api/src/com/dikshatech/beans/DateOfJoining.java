package com.dikshatech.beans;

public class DateOfJoining {
	public DateOfJoining(String dateJoining, String firstName,
			String designation, String divisionName, String lastName,
			int levelId) {		
		this.dateJoining = dateJoining;
		this.firstName = firstName;
		this.designation = designation;
		this.divisionName = divisionName;
		this.lastName = lastName;
		this.levelId = levelId;
		
	}	
	/**
	 * This attribute maps to the column DATE_OF_JOINING in the PROFILE_INFO
	 * table.
	 */
	protected String dateJoining;
	/**
	 * This attribute maps to the column FIRST_NAME in the PROFILE_INFO table.
	 */
	protected String firstName;
	/**
	 * This attribute maps to the column DESIGNATION in the LEVELS table.
	 */
	protected String designation;
	/**
	 * This attribute maps to the column DIVISION_NAME in the DIVISON table.
	 */
	protected String divisionName;
	/**
	 * This attribute maps to the column LAST_NAME in the PROFILE_INFO table.
	 */
	protected String lastName;

	/**
	 * This attribute maps to the column LEVEL_ID in the PROFILE_INFO table.
	 */
	protected int levelId;

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getDateJoining() {
		return dateJoining;
	}

	public void setDateJoining(String dateJoining) {
		this.dateJoining = dateJoining;
	}

}
