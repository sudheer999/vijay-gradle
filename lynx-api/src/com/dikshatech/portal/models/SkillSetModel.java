package com.dikshatech.portal.models;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dikshatech.beans.SkillSetBean;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.SkillSetDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.SkillSet;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.SkillSetDaoException;
import com.dikshatech.portal.factory.SkillSetDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class SkillSetModel extends ActionMethods
{

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author supriya.bhike
	 * receive vales in drop down depending on parent id and also for 
	 */
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
        ActionResult result = new ActionResult();
		DropDown dropDown = (DropDown) form;
		SkillSetDao skillSetDao= SkillSetDaoFactory.create();
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{
			case RECEIVE:
				try
				{
					//SkillSet skillSet[] = skillSetDao.findWhereParentIdEquals(dropDown.getKey1());
					SkillSet skillSet[] = skillSetDao.findWhereParentIdEquals(0);
					int i = 0;
					SkillSetBean[] skillSetBeans = new SkillSetBean[skillSet.length];
					for (SkillSet skill : skillSet)
					{
						SkillSet[] subSkills = skillSetDao.findWhereParentIdEquals(skill.getId());
						if(subSkills.length>0){
						SkillSetBean skillBean = DtoToBeanConverter.DtoToBeanConverter(skill, subSkills);
						skillSetBeans[i] = skillBean;
						i++;
						}else{
							SkillSetBean skillBean = new SkillSetBean();
							skillBean.setId(skill.getId());
							skillBean.setSkillname(skill.getSkillname());
							skillBean.setParentId(skill.getParentId());
							skillSetBeans[i] = skillBean;
							i++;
						}
					}
					dropDown.setDropDown(skillSetBeans);
					request.setAttribute("actionForm", dropDown);
				}
				catch (SkillSetDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case RECEIVEUSER:
				try
				{
					UsersDao usersDao = UsersDaoFactory.create();
					Users user;
						user = usersDao.findByPrimaryKey(dropDown.getKey1());//id = user id
						String skillset=user.getSkillsetId();
						if(skillset!=null && !skillset.equals("")){
							String skills[]=skillset.split(",");
							SkillSet userSkill[] = new SkillSet[skills.length];
							int count=0;
							for(String skill:skills){
						
								SkillSet singleSkill = skillSetDao.findByPrimaryKey(Integer.parseInt(skill));
								userSkill[count]=singleSkill;
								count++;
							}
							user.setSkillsArr(userSkill);
						}
					request.setAttribute("actionForm", user);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				default:
					break;
			}	
				
        
		return result;
	}

	/**
	 * @author supriya.bhike
	 * Save for skill set for users 
	 */
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
        ActionResult result = new ActionResult();
		SkillSet skillSet= (SkillSet) form;
		switch (ActionMethods.SaveTypes.getValue(form.getsType()))
			{
			case SAVE:
				
				try
				{
						if(skillSet.getSkills()!=null){
						UsersDao usersDao = UsersDaoFactory.create();
						UsersPk usersPk = new  UsersPk();
						Users user=usersDao.findByPrimaryKey(skillSet.getUserId());//id = user id
						user.setSkillsetId(skillSet.getSkills());
						user.setOthers(skillSet.getOthers());
						user.setuType("UPDATE");
						usersPk.setId(user.getId());
						usersDao.update(usersPk,user);
					    }
						
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
				default:
					break;
			}	
				
        
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
	      ActionResult result = new ActionResult();
			SkillSet skillSet= (SkillSet) form;
			switch (ActionMethods.UpdateTypes.getValue(form.getuType()))
				{
				case UPDATE:
					
					try
					{
							if(skillSet.getSkills()!=null){
							UsersDao usersDao = UsersDaoFactory.create();
							UsersPk usersPk = new  UsersPk();
							Users user=usersDao.findByPrimaryKey(skillSet.getId());//id = user id
							user.setSkillsetId(skillSet.getSkills());
							user.setOthers(skillSet.getOthers());
							user.setuType("UPDATE");
							usersPk.setId(user.getId());
							usersDao.update(usersPk,user);
						    }
						
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
					default:
						break;
				}	
					
	        
			return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
