package com.dikshatech.beans;

import java.io.Serializable;

import com.dikshatech.portal.dto.FestivalWishes;

public class FestivalAndBirthdayWishesBean implements Serializable {

	private FestivalWishes[]	festivals;
	
	private Object[]			birthDays;
	
	private DateOfJoining[]		dojDays;

	public DateOfJoining[] getDojDays() {
		return dojDays;
	}

	public void setDojDays(DateOfJoining[] dojDays) {
		this.dojDays = dojDays;
	}

	public FestivalWishes[] getFestivals() {
		return festivals;
	}

	public void setFestivals(FestivalWishes[] festivals) {
		this.festivals = festivals;
	}

	public Object[] getBirthDays() {
		return birthDays;
	}

	public void setBirthDays(Object[] birthDays) {
		this.birthDays = birthDays;
	}
}
