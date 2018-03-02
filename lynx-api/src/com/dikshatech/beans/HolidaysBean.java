package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

public class HolidaysBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2415269348144731026L;
	private int holiDaysId;
	private int holiDayscalId;
	private String				holidayName;
	private Date datepicker;

	public int getHoliDaysId() {
		return holiDaysId;
	}
	public void setHoliDaysId(int holiDaysId) {
		this.holiDaysId = holiDaysId;
	}
	public int getHoliDayscalId() {
		return holiDayscalId;
	}
	public void setHoliDayscalId(int holiDayscalId) {
		this.holiDayscalId = holiDayscalId;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public Date getDatepicker() {
		return datepicker;
	}
	public void setDatepicker(Date datepicker) {
		this.datepicker = datepicker;
	}

}
