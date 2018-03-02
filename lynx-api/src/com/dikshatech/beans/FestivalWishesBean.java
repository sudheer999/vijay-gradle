package com.dikshatech.beans;

import java.io.Serializable;
import com.dikshatech.portal.dto.FestivalWishes;
import com.dikshatech.portal.forms.PortalForm;

public class FestivalWishesBean extends PortalForm implements Serializable {

	private static final long	serialVersionUID	= 1299130924754382041L;
	private int					regionId;
	private String[]			festivalList;
	private String				region;
	private int					year;
	private FestivalWishes[]	festivalWishes;

	public FestivalWishesBean(){}

	public FestivalWishesBean(int regionId, String region, int year) {
		this.regionId = regionId;
		this.region = region;
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public FestivalWishes[] getFestivalWishes() {
		return festivalWishes;
	}

	public void setFestivalWishes(FestivalWishes[] festivalWishes) {
		this.festivalWishes = festivalWishes;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String[] getFestivalList() {
		return festivalList;
	}

	public void setFestivalList(String[] festivalList) {
		this.festivalList = festivalList;
	}
}
