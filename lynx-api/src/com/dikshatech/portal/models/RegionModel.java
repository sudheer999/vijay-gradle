package com.dikshatech.portal.models;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.LocationDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dto.Location;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.RegionsPk;
import com.dikshatech.portal.exceptions.LocationDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.factory.LocationDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class RegionModel extends ActionMethods
{
	private static Logger logger = LoggerUtil.getLogger(RegionModel.class);
	
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		Regions regions = (Regions) form;
		logger.info(regions.getRegName());
		return null;
	}
	
	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		boolean flag = false;
		RegionsDao regDao = RegionsDaoFactory.create();
		
		Regions regions = (Regions) form;
		try
		{
			
			switch (DeleteTypes.getValue(form.getdType()))
			{
				case DELETE:
					RegionsPk regionsPk = new RegionsPk();
					regionsPk.setId(regions.getId());
					
					regions = regDao.findByPrimaryKey(regions.getId());
					
					if ( restrict(regions.getId()) )
					{
						logger.info("Region cannot be deleted active users associated with it.");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("region.delete.validate", regions.getRegName()));
						result.setForwardName("success");
					}
					else
					{
						regDao.delete(regionsPk);
						logger.debug("Region " + regions.getRegName() + " deleted successfully.");
					}
					
					break;
				
				default:
					break;
			}
			
			flag = true;
			if ( flag )
			{
				logger.info("Exiting Regions model DELETED method.");
			}
			else
			{
				logger.info("Failed to DELETE Regions data");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
				result.setForwardName("success");
			}
		}
		catch (RegionsDaoException ede)
		{
			ede.printStackTrace();
		}
		return result;
		
	}
	
	private boolean restrict(int regionId)
	{
		String sqlQuery = "SELECT R.ID, R.COMPANY_ID, R.REG_NAME, R.REF_ABBREVIATION, R.PARENT_ID, R.CREATE_DATE FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID = R.ID LEFT JOIN LEVELS L ON L.DIVISION_ID = D.ID LEFT JOIN PROFILE_INFO PF ON PF.LEVEL_ID = L.ID LEFT JOIN USERS U ON U.PROFILE_ID = PF.ID WHERE R.ID = ? AND U.STATUS = 0";
		
		RegionsDao regDao = RegionsDaoFactory.create();
		
		try
		{
			Object[] sqlParams =
			{ regionId };
			//Object obj = regDao.findByDynamicSelect(sqlQuery, sqlParams);
			Regions[] dataFetched = regDao.findByDynamicSelect(sqlQuery, sqlParams);
			if ( dataFetched != null && dataFetched.length > 0)//there are active users under this Region
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
		RegionsDao regionDao = RegionsDaoFactory.create();
		LocationDao locationDao = LocationDaoFactory.create();
		
		ActionResult result = new ActionResult();
		try
		{
			switch (ReceiveTypes.getValue(form.getrType()))
			{
				case RECEIVEALLREGION:
					DropDown dropDown = (DropDown) form;
					Regions[] regions = regionDao.findByCompany(dropDown.getKey1());
					dropDown.setDropDown(regions);
					request.setAttribute("actionForm", dropDown);
					break;
				case RECEIVEREGION:
					Regions regions1 = (Regions) form;
					Regions regions2 = regionDao.findByPrimaryKey(regions1.getId());
					regions1.setCompanyId(regions2.getCompanyId());
					regions1.setRegName(regions2.getRegName());
					if ( regions2.getRefAbbreviation() != null )
					{
						regions1.setRefAbbreviation(regions2.getRefAbbreviation());
					}
					
					request.setAttribute("actionForm", regions1);
					break;
				case RECEIVEALLLOCATIONS:
					Location location = (Location) form;
					Location[] loc= locationDao.findByRegion(location.getKey2());
					location.setDropDown(loc);
					request.setAttribute("actionForm",location);
					break;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} catch (LocationDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		boolean flag = false;
		
		Regions regions = (Regions) form;
		regions.setId(0);
		try
		{
			String[] compReg = regions.getRegName().split("~;~");
			
			for (String cReg : compReg)
			{
				int companyId = Integer.parseInt(cReg.split("~,~")[0]);
				String[] regSubRegions = cReg.split("~,~")[1].split(",");
				for (String regSub : regSubRegions)
				{
					if ( !save(regSub, 0, companyId) )
					{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("region.duplicate.name", regSub));
						result.setForwardName("success");
					}
				}
				flag = true;
			}
			
			if ( flag )
			{
				logger.info("Regions data saved successfully");
			}
			else
			{
				logger.info("Failed to save Regions data");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("region.save.failed"));
				result.setForwardName("success");
			}
		}
		catch (RegionsDaoException rde)
		{
			rde.printStackTrace();
		}
		return result;
		
	}
	
	private boolean save(String regName, int parentId, int companyId) throws RegionsDaoException
	{
		RegionsDao regDao = RegionsDaoFactory.create();
		
		String[] split = regName.split(";");
		String regionName = split[0];
		String regionAbbre = split[1];
		
		boolean flag = false;
		Regions regions = new Regions();
		RegionsPk regionsPk = null;
		Regions[] regions2 = regDao.findByDynamicWhere("COMPANY_ID = ? AND REG_NAME = ?", new Object[]
		{ new Integer(companyId), regionName });
		if ( regions2 != null && regions2.length > 0 )
		{
			regions = regions2[0];
		}
		else
		{
			regions.setRegName(regionName);
			regions.setRefAbbreviation(regionAbbre);
			regions.setParentId(parentId);
			regions.setCompanyId(companyId);
			regionsPk = regDao.insert(regions);
			regions.setId(regionsPk.getId());
			flag = true;
		}
		return flag;
	}
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		boolean flag = false;
		RegionsDao regDao = RegionsDaoFactory.create();
		
		Regions regions = (Regions) form;
		try
		{
			
			switch (UpdateTypes.getValue(form.getuType()))
			{
				case UPDATE:
					RegionsPk regionsPk = new RegionsPk();
					regionsPk.setId(regions.getId());
					regDao.update(regionsPk, regions);
					break;
				
				default:
					break;
			}
			
			flag = true;
			if ( flag )
			{
				logger.info("Regions data UPDATED successfully");
			}
			else
			{
				logger.info("Failed to UPDATE Regions data");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
				result.setForwardName("success");
			}
		}
		catch (RegionsDaoException rde)
		{
			rde.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		
		return null;
	}
	
}
