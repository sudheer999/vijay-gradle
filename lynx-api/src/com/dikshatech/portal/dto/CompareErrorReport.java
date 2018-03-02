package com.dikshatech.portal.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CompareErrorReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompareErrorReport {
	
	public String id;
	
	public String name;
	
	public String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrorMessage() {
		return message;
	}

	public void setErrorMessage(String errorMessage) {
		this.message = errorMessage;
	}

	@Override
	public String toString() {
		return "CompareErrorReport [id=" + id + ", errorMessage=" + message + "]";
	}
	
}
