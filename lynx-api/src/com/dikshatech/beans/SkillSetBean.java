package com.dikshatech.beans;

import java.util.Set;

public class SkillSetBean
{
	protected int id;
	protected String skillname;
	protected int parentId;
	protected String skills;
	protected String others;
	private Set<SkillSetBean> subSkillSet;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getSkillname()
	{
		return skillname;
	}
	public void setSkillname(String skillname)
	{
		this.skillname = skillname;
	}
	public int getParentId()
	{
		return parentId;
	}
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}
	public String getSkills()
	{
		return skills;
	}
	public void setSkills(String skills)
	{
		this.skills = skills;
	}
	public String getOthers()
	{
		return others;
	}
	public void setOthers(String others)
	{
		this.others = others;
	}
	public Set<SkillSetBean> getSubSkillSet()
	{
		return subSkillSet;
	}
	public void setSubSkillSet(Set<SkillSetBean> subSkillSet)
	{
		this.subSkillSet = subSkillSet;
	}
	
	
	
}
