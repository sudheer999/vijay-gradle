package com.dikshatech.beans;

public class UserBean {

	private String	id;
	private String	empId;
	private String	firstName;
	private String	lastName;
	private String	name;

	public UserBean() {}

	public UserBean(String id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = firstName + " " + lastName + "(" + id + ")";
	}

	public UserBean(String id, String name, String empId, boolean flag) {
		this.id = id;
		this.empId = empId;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "ID: " + this.id + " Name: " + this.firstName + " " + this.lastName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
}
