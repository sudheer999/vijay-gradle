package com.dikshatech.portal.forms;

import java.io.Serializable;

public class DropDown extends PortalForm implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Object[]			dropDown;
	private int					key1;
	private int					key2;
	private int					count;
	private Object				detail;
	private String 			userIdFlag;
	private int levelId;
	private int projectId;
	
	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	private int deptId;
	private int regionId;
	
	
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	public void setDropDown(Object[] dropDown) {
		this.dropDown = dropDown;
	}

	public Object[] getDropDown() {
		return dropDown;
	}

	public void setKey1(int key1) {
		this.key1 = key1;
	}

	public int getKey1() {
		return key1;
	}

	public void setKey2(int key2) {
		this.key2 = key2;
	}

	public int getKey2() {
		return key2;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserIdFlag() {
		return userIdFlag;
	}

	public void setUserIdFlag(String userIdFlag) {
		this.userIdFlag = userIdFlag;
	}
}
