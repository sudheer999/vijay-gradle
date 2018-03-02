package com.dikshatech.common.utils;
/**
 * Bean to keep the information of the individual escalators, name , id level, Permission to edit
 * @author praneeth.r
 *
 */
public class EscPermissionBean {
	
	private boolean permission;
	private String userId,empId;
	private String name;
	private String levelId;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public boolean isPermission() {
		return permission;
	}
	public void setPermission(boolean permission) {
		this.permission = permission;
	}
	public String getuserId() {
		return userId;
	}
	public void setUserId(String id) {
		this.userId = id;
	}
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	@Override
	public String toString() {
		return new String(name + permission + "	" + userId + "	" + "\n");
	}

}
