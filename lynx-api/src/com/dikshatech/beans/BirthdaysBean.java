package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class BirthdaysBean implements Serializable {

	private String	id;
	private String	userId;
	private String	empid;
	private String	lastName;
	private String	firstName;
	private String	dob;
	private String	regionid;
	private String	officalEmailId;

	public BirthdaysBean() {}

	public BirthdaysBean(String id, String userId, String empid, String lastName, String firstName, String dob, String regionid, String officalEmailId) {
		this.id = id;
		this.userId = userId;
		this.empid = empid;
		this.lastName = lastName;
		this.firstName = firstName;
		this.dob = dob;
		this.regionid = regionid;
		this.officalEmailId = officalEmailId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public void setOfficalEmailId(String officalEmailId) {
		this.officalEmailId = officalEmailId;
	}

	public String getOfficalEmailId() {
		return officalEmailId;
	}
}
