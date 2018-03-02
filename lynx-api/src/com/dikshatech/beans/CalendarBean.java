package com.dikshatech.beans;

import java.io.Serializable;

public class CalendarBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1299130924754382041L;
	private int calendarId;
	private String calendarName;
	private int region;
	private String regionName;
	private int calendarYear;
	private String [][]holidaylist;
	private CalendarBean calendar;
	private HolidaysBean []holidaysBean;
	

	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public CalendarBean getCalendar() {
		return calendar;
	}
	public void setCalendar(CalendarBean calendar) {
		this.calendar = calendar;
	}
	
	public int getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public int getCalendarYear() {
		return calendarYear;
	}
	public void setCalendarYear(int calendarYear) {
		this.calendarYear = calendarYear;
	}
	
	public String[][] getHolidaylist() {
		return holidaylist;
	}
	public void setHolidaylist(String[][] holidaylist) {
		this.holidaylist = holidaylist;
	}
	public HolidaysBean[] getHolidaysBean() {
		return holidaysBean;
	}
	public void setHolidaysBean(HolidaysBean[] holidaysBean) {
		this.holidaysBean = holidaysBean;
	}
	
}
