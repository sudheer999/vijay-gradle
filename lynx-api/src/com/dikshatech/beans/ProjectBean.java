package com.dikshatech.beans;

public class ProjectBean {

	private int		id;
	private String	projectName;
	private String	clientName;
	private String	location;

	public ProjectBean() {}

	public ProjectBean(int id, String projectName, String clientName, String location) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.clientName = clientName;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
