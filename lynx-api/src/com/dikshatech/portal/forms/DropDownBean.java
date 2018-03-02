package com.dikshatech.portal.forms;

import java.io.Serializable;

public class DropDownBean implements Serializable {

	private Object[]	dropDown;
	private String		count;

	public void setDropDown(Object[] dropDown) {
		this.dropDown = dropDown;
	}

	public Object[] getDropDown() {
		return dropDown;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
