package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class Roles implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<ModuleBean> moduleBean;
	private String roleName;
	private int roleId;

	public Set<ModuleBean> getModules()
	{
		return moduleBean;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public int getRoleId()
	{
		return roleId;
	}

	public void setModules(Set<ModuleBean> moduleBean)
	{
		this.moduleBean = moduleBean;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
	}

}
