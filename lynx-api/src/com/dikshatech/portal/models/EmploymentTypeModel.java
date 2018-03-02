package com.dikshatech.portal.models;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.EmploymenttypeDao;
import com.dikshatech.portal.dto.Employmenttype;
import com.dikshatech.portal.exceptions.EmploymenttypeDaoException;
import com.dikshatech.portal.factory.EmploymenttypeDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class EmploymentTypeModel extends ActionMethods
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

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
        ActionResult result = new ActionResult();
		EmploymenttypeDao employmenttypeDao = EmploymenttypeDaoFactory.create();
		
		
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{
			case RECEIVEALL:

			    DropDown dropDown = new DropDown();
                Employmenttype employmenttype[];
				try
				{
					employmenttype = employmenttypeDao.findAll();
					dropDown.setDropDown(employmenttype);
				}
				catch (EmploymenttypeDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("actionForm", dropDown);
				break;

			default:
				 break;
				
			}

		
		

		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
