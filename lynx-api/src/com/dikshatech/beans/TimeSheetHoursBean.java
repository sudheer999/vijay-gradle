package com.dikshatech.beans;

import java.io.Serializable;

public class TimeSheetHoursBean implements Serializable, ParsableObject {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4129562442292089521L;
	private String				day;
	private String				date;
	private String				hour;
	private String				comment;

	public TimeSheetHoursBean() {}

	public TimeSheetHoursBean(String day, String date, String hour, String comment) {
		this.day = day;
		this.date = date;
		this.hour = hour;
		this.comment = comment;
	}
	public TimeSheetHoursBean(String day, String date, String[] hourComment) {
		this.day = day;
		this.date = date;
		this.hour = hourComment[0];
		this.comment = hourComment[1];
	}
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
