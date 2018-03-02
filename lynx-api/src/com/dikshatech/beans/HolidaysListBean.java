package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

public class HolidaysListBean implements Serializable {

	private Date	datepicker;

	public HolidaysListBean() {
		// TODO Auto-generated constructor stub
	}

	public HolidaysListBean(Date datepicker) {
		setDatepicker(datepicker);
	}

	public Date getDatepicker() {
		return datepicker;
	}

	public void setDatepicker(Date datepicker) {
		this.datepicker = datepicker;
	}
}
