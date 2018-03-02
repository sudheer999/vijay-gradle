package com.dikshatech.portal.dto;

import java.io.Serializable;

public class Category implements Serializable{
	
	
	
	protected int id;
	
	protected String category ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

}
