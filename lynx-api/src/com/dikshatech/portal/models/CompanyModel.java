package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.NestedBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CompanyDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dto.Company;
import com.dikshatech.portal.dto.CompanyPk;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.exceptions.CompanyDaoException;
import com.dikshatech.portal.factory.CompanyDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class CompanyModel extends ActionMethods
{
	
	private static Logger logger = LoggerUtil.getLogger(CompanyModel.class);
	
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		boolean flag = false;
		
		CompanyDao cDao = CompanyDaoFactory.create();
		Company company = (Company) form;
		CompanyPk companyPk = new CompanyPk();
		logger.debug("Company model delete method invoked to delete companyId " + company.getId());
		try
		{
			switch (DeleteTypes.getValue(form.getdType()))
			{
				
				case DELETE:

					company = cDao.findByPrimaryKey(company.getId());
					companyPk.setId(company.getId());
					if ( restrict(company.getId()) )
					{
						logger.info("Company cannot be deleted active users associated with it.");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("company.delete.validate", company.getCompanyName()));
						result.setForwardName("success");
					}
					else
					{
						cDao.delete(companyPk);
						logger.debug("Company " + company.getCompanyName() + " deleted successfully.");
					}
					
					request.setAttribute("actionForm", company);
					flag = true;
					break;
				default:
			}
		}
		catch (CompanyDaoException e)
		{
			e.printStackTrace();
		}
		
		if ( flag )
		{
			logger.info("Exiting Company model delete method");
		}
		else
		{
			logger.info("Failed to delete Company data");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			result.setForwardName("success");
		}
		
		return result;
	}
	
	private boolean restrict(int companyId)
	{
		String sqlQuery = "SELECT C.ID, C.COMPANY_NAME, C.CREATION_DATE FROM COMPANY C LEFT JOIN REGIONS R ON R.COMPANY_ID = C.ID LEFT JOIN DIVISON D ON D.REGION_ID = R.ID LEFT JOIN LEVELS L ON L.DIVISION_ID = D.ID LEFT JOIN PROFILE_INFO PF ON PF.LEVEL_ID = L.ID LEFT JOIN USERS U ON U.PROFILE_ID = PF.ID WHERE C.ID = ? AND U.STATUS = 0;";
		
		CompanyDao cDao = CompanyDaoFactory.create();
		
		try
		{
			Object[] sqlParams =
			{ companyId };
			Object[] obj = cDao.findByDynamicSelect(sqlQuery, sqlParams);
			
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
		CompanyDao companyDao = CompanyDaoFactory.create();
		
		ActionResult result = new ActionResult();
		try
		{
			switch (ReceiveTypes.getValue(form.getrType()))
			{
				case RECEIVEALLCOMPANY:
					DropDown dropDown = (DropDown) form;
					Company[] companies = companyDao.findAll();
					dropDown.setDropDown(companies);
					request.setAttribute("actionForm", dropDown);
					break;
				case RECEIVECOMPANY:
					Company company = (Company) form;
					company = companyDao.findByPrimaryKey(company.getId());
					request.setAttribute("actionForm", company);
					break;
				case RECEIVECOMPANYRECORD:

					RegionsDao rDao = RegionsDaoFactory.create();
					DivisonDao divisonDao = DivisonDaoFactory.create();
					LevelsDao lDao = LevelsDaoFactory.create();
					
					DropDown dropDown2 = (DropDown) form;
					int index = 0;
					Company[] companies1 = companyDao.findAll();
					NestedBean[] companySet = new NestedBean[companies1.length];
					for (Company company2 : companies1)
					{
						NestedBean companyBean = new NestedBean();
						companyBean.setDataLevel("Company");
						companyBean.setFieldId(company2.getId());
						companyBean.setFieldName(company2.getCompanyName());
						
						Set<NestedBean> regionSet = new HashSet<NestedBean>();
						Regions[] regions = rDao.findByCompany(company2.getId());
						for (Regions regions2 : regions)
						{
							NestedBean regionBean = new NestedBean();
							
							regionBean.setDataLevel("Region");
							regionBean.setFieldId(regions2.getId());
							regionBean.setFieldName(regions2.getRegName());
							
							Set<NestedBean> departmentSet = new HashSet<NestedBean>();
							Divison[] departments = divisonDao.findByDynamicWhere("REGION_ID = ? AND PARENT_ID = ?", new Object[]
							{ new Integer(regions2.getId()), new Integer(0) });
							for (Divison dept : departments)
							{
								NestedBean departmentBean = new NestedBean();
								departmentBean.setDataLevel("Department");
								departmentBean.setFieldId(dept.getId());
								departmentBean.setFieldName(dept.getName());
								
								Set<NestedBean> divisionSet = new HashSet<NestedBean>();
								Divison[] divisons = divisonDao.findWhereParentIdEquals(dept.getId());
								for (Divison divison : divisons)
								{
									NestedBean divisionBean = new NestedBean();
									divisionBean.setDataLevel("Division");
									divisionBean.setFieldId(divison.getId());
									divisionBean.setFieldName(divison.getName());
									
									Set<NestedBean> levelsSet = new HashSet<NestedBean>();
									
									Levels[] levels = lDao.findByDivison(divison.getId());
									for (Levels levels2 : levels)
									{
										NestedBean levelBean = new NestedBean();
										levelBean.setDataLevel("Levels");
										levelBean.setFieldId(levels2.getId());
										levelBean.setFieldName(levels2.getDesignation());
										levelBean.setDataLevel1(levels2.getLabel());
										//levelBean.setGrade(levels2.getGrade());
										
										levelsSet.add(levelBean);
									}
									divisionBean.setNestedRecord(levelsSet);
									divisionSet.add(divisionBean);
								}
								
								Levels[] levels = lDao.findByDivison(dept.getId());
								if ( levels.length > 0 )
								{
									NestedBean leBean = new NestedBean();
									Set<NestedBean> levelsSet = new HashSet<NestedBean>();
									for (Levels levels2 : levels)
									{
										NestedBean levelBean = new NestedBean();
										levelBean.setDataLevel("Levels");
										levelBean.setFieldId(levels2.getId());
										levelBean.setFieldName(levels2.getDesignation());
										levelBean.setDataLevel1(levels2.getLabel());
										//levelBean.setGrade(levels2.getGrade());
										
										levelsSet.add(levelBean);
									}
									leBean.setNestedRecord(levelsSet);
									divisionSet.add(leBean);
								}
								
								departmentBean.setNestedRecord(divisionSet);
								departmentSet.add(departmentBean);
							}
							regionBean.setNestedRecord(departmentSet);
							regionSet.add(regionBean);
						}
						companyBean.setNestedRecord(regionSet);
						companySet[index] = companyBean;
						index++;
					}
					dropDown2.setDropDown(companySet);
					request.setAttribute("actionForm", dropDown2);
					logger.info("RECEIVECOMPANYRECORD data generated successfully.");
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
		boolean flag = false;
		
		CompanyDao cDao = CompanyDaoFactory.create();
		Company company = (Company) form;
		company.setId(0);
		CompanyPk companyPk = null;
		try
		{
			Company company2 = cDao.findWhereCompanyNameEquals(company.getCompanyName());
			if ( company2 != null )
			{
				company = company2;
				logger.info("Duplicate company name entered.");
			}
			else
			{
				companyPk = cDao.insert(company);
				company.setId(companyPk.getId());
				flag = true;
			}
		}
		catch (CompanyDaoException e)
		{
			e.printStackTrace();
			logger.info("Failed to save Company data");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			result.setForwardName("success");
		}
		
		if ( flag )
		{
			logger.info("Company data saved successfully");
		}
		else
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("company.duplicate.name", company.getCompanyName()));
			result.setForwardName("success");
		}
		
		request.setAttribute("actionForm", company);
		return result;
	}
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		boolean flag = false;
		
		CompanyDao cDao = CompanyDaoFactory.create();
		Company company = (Company) form;
		CompanyPk companyPk = null;
		try
		{
			switch (UpdateTypes.getValue(form.getuType()))
			{
				
				case UPDATE:
					Company company2 = cDao.findWhereCompanyNameEquals(company.getCompanyName());
					if ( company2 != null && company2.getId() != company.getId() )
					{
						company = company2;
						logger.info("Duplicate company name entered.");
					}
					else
					{
						companyPk = new CompanyPk();
						companyPk.setId(company.getId());
						cDao.update(companyPk, company);
						flag = true;
					}
					
					break;
				default:
			}
		}
		catch (CompanyDaoException e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			result.setForwardName("success");
		}
		
		if ( flag )
		{
			logger.info("Company data update successfully");
		}
		else
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("company.duplicate.name", company.getCompanyName()));
			result.setForwardName("success");
		}
		request.setAttribute("actionForm", company);
		return result;
	}
	
	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
