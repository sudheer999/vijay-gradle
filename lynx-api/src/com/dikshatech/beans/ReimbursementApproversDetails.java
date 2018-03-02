package com.dikshatech.beans;

public class ReimbursementApproversDetails {

	private String	name;
	private String	time;
	private String	comments;

	public ReimbursementApproversDetails() {}

	public ReimbursementApproversDetails(String name, String time, String comments) {
		super();
		this.name = name;
		this.time = time;
		this.comments = comments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
