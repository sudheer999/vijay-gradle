package com.dikshatech.portal.models;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.Division;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CategoryDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dto.Category;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DivisonPk;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.factory.CategoryDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class DivisionModel extends ActionMethods
{
	
	private static Logger logger = LoggerUtil.getLogger(DivisionModel.class);
	
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @author supriya.bhike
	 * @param PortalForm
	 *            ,HttpServletRequest Delete division
	 */
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		DivisonDao divisonDao = DivisonDaoFactory.create();
		
		ActionResult result = new ActionResult();
		try
		{
			switch (DeleteTypes.getValue(form.getdType()))
			{
				case DELETE:
					try
					{
						Divison divisionDTO = (Divison) form;
						DivisonPk divDto1 = new DivisonPk();
						divDto1.setId(divisionDTO.getId());
						
						divisionDTO = divisonDao.findByPrimaryKey(divisionDTO.getId());
						String deptMessage = "department.delete.validate";
						String divMessage = "division.delete.validate";
						String errorMessage = deptMessage;
						if(divisionDTO.getParentId() != 0)
						{
							errorMessage = divMessage;
						}	
						
						if ( restrict(divisionDTO.getId()) )
						{
							logger.info("Division cannot be deleted active users associated with it.");
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,
							        new ActionMessage(errorMessage, divisionDTO.getName()));
							result.setForwardName("success");
						}
						else
						{
							divisonDao.delete(divDto1);
						}
						
						result.setForwardName("success");
					}
					catch (Exception e)
					{
						
						e.printStackTrace();
					}
					
					break;
				
			}
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean restrict(int divisionId)
	{
		String sqlQuery = "SELECT D.ID, D.NAME, D.PARENT_ID, D.REGION_ID FROM DIVISON D LEFT JOIN LEVELS L ON L.DIVISION_ID = D.ID LEFT JOIN PROFILE_INFO PF ON PF.LEVEL_ID = L.ID LEFT JOIN USERS U ON U.PROFILE_ID = PF.ID WHERE D.ID IN (SELECT ID FROM DIVISON WHERE ID = ? OR PARENT_ID = ?) AND U.STATUS = 0;";
		
		DivisonDao divisonDao = DivisonDaoFactory.create();
		
		try
		{
			Object[] sqlParams =
			{ divisionId, divisionId};
			Object[] obj = divisonDao.findByDynamicSelect(sqlQuery, sqlParams);
			
			if ( obj != null && obj.length > 0 )
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
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
		DivisonDao divisonDao = DivisonDaoFactory.create();
		
		ActionResult result = new ActionResult();
		try
		{
			switch (ReceiveTypes.getValue(form.getrType()))
			{
				case RECEIVEALLDIVISION:
					DropDown dropDown = (DropDown) form;
					Divison[] divDtos = divisonDao.findByDynamicWhere("PARENT_ID = ? AND REGION_ID = ?", new Object[]
					{ new Integer(dropDown.getKey1()), new Integer(dropDown.getKey2()) });
					Division[] divBeans = new Division[divDtos.length];
					int i = 0;
					for (Divison divDto : divDtos)
					{
						Divison[] subDivisios = divisonDao.findWhereParentIdEquals(divDto.getId());
						Division divBean = DtoToBeanConverter.DtoToBeanConverter(divDto, subDivisios);
						divBeans[i] = divBean;
						i++;
					}
					dropDown.setDropDown(divBeans);
					request.setAttribute("actionForm", dropDown);
					System.out.println(dropDown);
					break;
				case RECEIVEDIVISION:
					Divison divison = (Divison) form;
					Divison div = divisonDao.findByPrimaryKey(divison.getId());
					divison.setName(div.getName());
					request.setAttribute("actionForm", divison);
					break;
				
				case RECEIVEALLDEPARTMENTBYREGION:
					DropDown dropDown1 = (DropDown) form;
					Divison[] divDtos1 = divisonDao.findByDynamicWhere("PARENT_ID = ? AND REGION_ID = ?", new Object[]
					{ new Integer(dropDown1.getKey1()), new Integer(dropDown1.getKey2()) });
					dropDown1.setDropDown(divDtos1);
					request.setAttribute("actionForm", dropDown1);
					break;
				case RECEIVEALLCATEGORY:
					DropDown dropDownCategory = (DropDown)form;
					CategoryDao category = CategoryDaoFactory.create();
					
					Category[] Cat = category.findAllCategory("SELECT * FROM CATEGORY");
					dropDownCategory.setDropDown(Cat);
					
					request.setAttribute("actionForm", dropDownCategory);
					break;
					
					
					
			}
				
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @author supriya.bhike
	 * @return ActionResult Save single division
	 */
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		boolean flag = false;
		DivisonDao divDao = DivisonDaoFactory.create();
		Divison divison1 = (Divison) form;
		String[] divisions = divison1.getName().split(",");
		for (String divs : divisions)
		{
			Divison divison = new Divison();
			divison.setName(divs);
			divison.setParentId(divison1.getParentId());
			divison.setRegionId(divison1.getRegionId());
			
			try
			{
				divDao.insert(divison);
				flag = true;
			}
			catch (DivisonDaoException e)
			{
				logger.error(e.getMessage());
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("division.save.failed", e.getMessage()));
				result.setForwardName("success");
			}
		}
		
		if ( flag )
		{
			logger.info("Divisions data saved successfully");
		}
		else
		{
			logger.info("Failed to save Divisions data");
		}
		return result;
	}
	
	/**
	 * @author supriya.bhike
	 * @return ActionResult Update single division name,parent id
	 */
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		DivisonDao divisonDao = DivisonDaoFactory.create();
		
		ActionResult result = new ActionResult();
		try
		{
			switch (UpdateTypes.getValue(form.getuType()))
			{
				
				case UPDATE:
					Divison Single = (Divison) form;
					DivisonPk divPK = new DivisonPk();
					divPK.setId(Single.getId());
					divisonDao.update(divPK, Single);
					request.setAttribute("actionForm", Single);
					break;
			}
		}
		catch (DivisonDaoException e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("division.save.failed", e.getMessage()));
			result.setForwardName("success");
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
