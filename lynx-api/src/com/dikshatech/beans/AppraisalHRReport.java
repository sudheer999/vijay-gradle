package com.dikshatech.beans;

import java.io.Serializable;

public class AppraisalHRReport implements Serializable {

	private String	id;
	private String	name;
	private String	ratings;
	private String	comments;
	private Object	obj;

	public String getRatings() {
		return ratings;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getComments() {
		return comments;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object getObj() {
		return obj;
	}

	public String[] toArrayString() {
		// TODO Auto-generated method stub
		return new String[]{getId()};
	}
}
