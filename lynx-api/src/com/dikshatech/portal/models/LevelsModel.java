package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.UserNameBean;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.LevelsPk;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;

public class LevelsModel extends ActionMethods
{
	
	private static Logger logger = LoggerUtil.getLogger(LevelsModel.class);
	
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
	
		return null;
	}
	
	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		boolean flag = false;
		
		Levels levels = (Levels) form;
		
		try
		{
			switch (DeleteTypes.getValue(form.getdType()))
			{
				case DELETE:
					LevelsPk levelsPk = new LevelsPk();
					levelsPk.setId(levels.getId());
					
					levels = levelsDao.findByPrimaryKey(levels.getId());
					
					if ( restrict(levels.getId()) )
					{
						logger.info("Levels cannot be deleted active users associated with it.");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("level.delete.validate", levels.getLabel() + " " + levels.getDesignation()));
						result.setForwardName("success");
					}
					else
					{
						levelsDao.delete(levelsPk);
						logger.debug("Level " + levels.getLabel() + " " + levels.getDesignation() + " deleted successfully.");
					}
					flag = true;
					break;
				
				default:
					break;
			}
		}
		catch (LevelsDaoException e)
		{
			logger.error(e.getMessage());
		}
		
		if ( flag )
		{
			logger.info("Exiting Levels model DELETED method.");
		}
		else
		{
			logger.info("Failed to DELETE levels data");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			result.setForwardName("success");
		}
		
		return result;
	}
	
	private boolean restrict(int levelId)
	{
		//String sqlQuery = "SELECT L.ID, L.LABEL, L.GRADE, L.DESIGNATION, L.DIVISION_ID, D.NAME FROM LEVELS L LEFT JOIN DIVISON D ON D.ID = L.DIVISION_ID LEFT JOIN PROFILE_INFO PF ON PF.LEVEL_ID = L.ID LEFT JOIN USERS U ON U.PROFILE_ID = PF.ID WHERE L.ID = ? AND U.STATUS = 0;";
		String sqlQuery = "SELECT L.ID, L.LABEL,L.DESIGNATION, L.DIVISION_ID, D.NAME FROM LEVELS L LEFT JOIN DIVISON D ON D.ID = L.DIVISION_ID LEFT JOIN PROFILE_INFO PF ON PF.LEVEL_ID = L.ID LEFT JOIN USERS U ON U.PROFILE_ID = PF.ID WHERE L.ID = ? AND U.STATUS = 0;";
		
		LevelsDao levelsDao = LevelsDaoFactory.create();
		
		try
		{
			Object[] sqlParams =
			{ levelId };
			Object[] obj = levelsDao.findByDynamicSelect(sqlQuery, sqlParams);
			
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
	
		return null;
	}
	
	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException
	{
	
		return null;
	}
	
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		LevelsDao levelsDao = LevelsDaoFactory.create();
		DropDown dropDown = null;
		Levels[] levelArr = null;
		
		
		ActionResult result = new ActionResult();
		try
		{
			switch (ReceiveTypes.getValue(form.getrType()))
			{
				case RECEIVEALLLEVELS:
					dropDown = (DropDown) form;
					Levels[] levels = levelsDao.findAll();
					dropDown.setDropDown(levels);
					request.setAttribute("actionForm", dropDown);
					break;
				case RECEIVELEVEL:
					Levels level = (Levels) form;
					Levels lev = levelsDao.findByPrimaryKey(level.getId());
					level.setLabel(lev.getLabel());
					level.setDesignation(lev.getDesignation());
					level.setDivisionId(lev.getDivisionId());
					//level.setGrade(lev.getGrade());
					request.setAttribute("actionForm", level);
					break;
				
				case RECEIVEALLLEVELSBYDIVISION:
					dropDown = (DropDown) form;
					levelArr = levelsDao.findByDynamicWhere("DIVISION_ID IN (SELECT ID FROM DIVISON WHERE ID = ? OR PARENT_ID = ?)", new Object[]
					{ new Integer(dropDown.getKey1()), new Integer(dropDown.getKey1()) });
					dropDown.setDropDown(levelArr);
					request.setAttribute("actionForm", dropDown);
					break;
				case RECEIVEALLLEVELSBYREGION:
					dropDown = (DropDown) form;
					levelArr = levelsDao.findByDynamicWhere("DIVISION_ID IN (SELECT ID FROM DIVISON WHERE REGION_ID = ?)", new Object[]
					{ new Integer(dropDown.getKey1()) });
					dropDown.setDropDown(levelArr);
					request.setAttribute("actionForm", dropDown);
					break;
				case RECEIVEDESIGNATION:
					Levels levels2 = (Levels) form;
					DropDown dropDown2 = new DropDown();
					levelArr = levelsDao.findByDynamicWhere("DIVISION_ID = ? AND LABEL = ?", new Object[]
					{ new Integer(levels2.getDivisionId()), new String(levels2.getLabel()) });
					dropDown2.setDropDown(levelArr);
					request.setAttribute("actionForm", dropDown2);
					break;
					
				// Included to get the list of users for configuring the escalation Action by Admin
				// The level Id has to be passed along with the request to get the list of users 
				// URL is of type dropdown.do?method=receive&rType=RECEIVEALLUSERSBYLEVELS&cType=LEVELS&levelId=1004
				case RECEIVEALLUSERSBYLEVELS:
					Connection connection=null;
					try{
						dropDown = (DropDown) form;
						List<UserNameBean> usersList = new ArrayList<UserNameBean>();
						connection=ResourceManager.getConnection();
						PreparedStatement stmt=null;
						ResultSet resultSet=null;
						stmt=connection.prepareStatement("SELECT U.ID, P.FIRST_NAME, P.LAST_NAME FROM USERS U join PROFILE_INFO P ON U.PROFILE_ID = P.ID WHERE U.LEVEL_ID = ?");
						stmt.setString(1, String.valueOf(dropDown.getLevelId()));
						resultSet=stmt.executeQuery();
						while(resultSet.next()){
							UserNameBean ubean = new UserNameBean();
							ubean.setUserId(resultSet.getInt("U.ID"));
							ubean.setFirstName(resultSet.getString("P.FIRST_NAME"));
							ubean.setFirstName(resultSet.getString("P.LAST_NAME"));
							usersList.add(ubean);
						}
						
						if(usersList != null && usersList.size() > 0){
							dropDown.setDropDown(usersList.toArray());
						}
						request.setAttribute("actionForm", dropDown);
					}catch(SQLException ex){
						logger.error("Unable to retrieve records for RECEIVEALLUSERSBYLEVELS for LEVEL_ID: " + dropDown.getLevelId());
						logger.error(ex.getMessage());
					}finally{
						if(connection!=null)
						{
							ResourceManager.close(connection);
						}
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
	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		boolean flag = false;
		
		Levels levels = (Levels) form;
		String[] levelDiv = levels.getLabel().split("~,~");
		
		for (String lDiv : levelDiv)
		{
			int divisionId = Integer.parseInt(lDiv.split(":")[0]);
			String[] levelArr = lDiv.split(":")[1].split(",");
			for (String lev : levelArr)
			{
				String[] desgLabel = lev.split(";");
				Levels levelInsert = new Levels();
				levelInsert.setLabel(desgLabel[0]);
				//levelInsert.setGrade(desgLabel[1]);
				levelInsert.setDesignation(desgLabel[1]);//was desgLabel[2] changed by vijay to fix ArrayIndexOutOfBoundsException
				levelInsert.setDivisionId(divisionId);
				try
				{
					levelsDao.insert(levelInsert);
				}
				catch (LevelsDaoException e)
				{
					e.printStackTrace();
					flag = false;
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("levels.save.failed", e.getMessage()));
					result.setForwardName("success");
				}
				flag = true;
			}
		}
		
		if ( flag )
		{
			logger.info("Levels data saved successfully");
		}
		else
		{
			logger.info("Failed to save Levels data");
		}
		
		return result;
	}
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		boolean flag = false;
		
		Levels levels = (Levels) form;
		
		try
		{
			switch (UpdateTypes.getValue(form.getuType()))
			{
				case UPDATE:
					LevelsPk levelsPk = new LevelsPk();
					levelsPk.setId(levels.getId());
					levelsDao.update(levelsPk, levels);
					flag = true;
					break;
				
				default:
					break;
			}
		}
		catch (LevelsDaoException e)
		{
			logger.error(e.getMessage());
		}
		
		if ( flag )
		{
			logger.info("Levels data UPDATED successfully");
		}
		else
		{
			logger.info("Failed to update levels data");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			result.setForwardName("success");
		}
		
		return result;
	}
	
	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
	
		return null;
	}
	
}
