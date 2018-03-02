package com.dikshatech.beans;

import java.util.Date;

public class NewsBean {
	/** 
	 * This attribute maps to the column ID in the news table.
	 */
	protected int id;
	/** 
	 * This attribute maps to the column NEWS_HTML_TITLE in the news table.
	 */
	protected String newsHtmlTitle;

	/** 
	 * This attribute maps to the column NEWS_TITLE in the news table.
	 */	
	protected String newsTitle;

	/** 
	 * This attribute maps to the column NEWS_DISC in the news table.
	 */
	protected String newsDisc;

	/** 
	 * This attribute maps to the column PHOTOS in the news table.
	 */
	protected String photos;

	/** 
	 * This attribute maps to the column CREATE_DATE in the news table.
	 */
	protected Date createDate;

	/** 
	 * This attribute maps to the column USER_ID in the news table.
	 */
	protected int userId;

	/** 
	 * This attribute represents whether the primitive attribute userId is null.
	 */
	protected boolean userIdNull = true;

	/** 
	 * This attribute maps to the column REGION_ID in the news table.
	 */
	protected int regionId;

	/** 
	 * This attribute represents whether the primitive attribute regionId is null.
	 */
	protected boolean regionIdNull = true;

	/** 
	 * This attribute maps to the column DIV_ID in the news table.
	 */
	protected int divId;
	
	
	protected String newsTitleString;
	
	
	
	protected String imageName;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getNewsTitleString() {
		return newsTitleString;
	}

	public void setNewsTitleString(String newsTitleString) {
		this.newsTitleString = newsTitleString;
	}

	public String getNewsDescString() {
		return newsDescString;
	}

	public void setNewsDescString(String newsDescString) {
		this.newsDescString = newsDescString;
	}

	protected String newsDescString;

	/** 
	 * This attribute represents whether the primitive attribute divId is null.
	 */
	protected boolean divIdNull = true;	
	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Method 'getNewsTitle'
	 * 
	 * @return String
	 */
	public String getNewsTitle()
	{
		return newsTitle;
	}

	/**
	 * Method 'setNewsTitle'
	 * 
	 * @param newsTitle
	 */
	public void setNewsTitle(String newsTitle)
	{
		this.newsTitle = newsTitle;
	}

	/**
	 * Method 'getNewsDisc'
	 * 
	 * @return String
	 */
	public String getNewsDisc()
	{
		return newsDisc;
	}

	/**
	 * Method 'setNewsDisc'
	 * 
	 * @param newsDisc
	 */
	public void setNewsDisc(String newsDisc)
	{
		this.newsDisc = newsDisc;
	}

	/**
	 * Method 'getPhotos'
	 * 
	 * @return String
	 */
	public String getPhotos()
	{
		return photos;
	}

	/**
	 * Method 'setPhotos'
	 * 
	 * @param photos
	 */
	public void setPhotos(String photos)
	{
		this.photos = photos;
	}

	/**
	 * Method 'getCreateDate'
	 * 
	 * @return Date
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * Method 'setCreateDate'
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * Method 'getUserId'
	 * 
	 * @return int
	 */
	public Integer getUserId()
	{
		return userId;
	}

	/**
	 * Method 'setUserId'
	 * 
	 * @param userId
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
		this.userIdNull = false;
	}
	/**
	 * Method 'getRegionId'
	 * 
	 * @return int
	 */
	public int getRegionId()
	{
		return regionId;
	}

	/**
	 * Method 'setRegionId'
	 * 
	 * @param regionId
	 */
	public void setRegionId(int regionId)
	{
		this.regionId = regionId;
		this.regionIdNull = false;
	}

	

	/**
	 * Method 'getDivId'
	 * 
	 * @return int
	 */
	public int getDivId()
	{
		return divId;
	}

	/**
	 * Method 'setDivId'
	 * 
	 * @param divId
	 */
	public void setDivId(int divId)
	{
		this.divId = divId;
		this.divIdNull = false;
	}
	/**
	 * Method 'getNewsHtmlTitle'
	 * 
	 * @return String
	 */
	public String getNewsHtmlTitle()
	{
		return newsHtmlTitle;
	}

	/**
	 * Method 'setNewsHtmlTitle'
	 * 
	 * @param newsHtmlTitle
	 */
	public void setNewsHtmlTitle(String newsHtmlTitle)
	{
		this.newsHtmlTitle = newsHtmlTitle;
	}

	

}
