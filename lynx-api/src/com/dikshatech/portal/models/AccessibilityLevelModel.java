package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dikshatech.beans.NestedBean;
import com.dikshatech.common.utils.EnumUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AccessibilityLevelDao;
import com.dikshatech.portal.dao.AccessibilityTopLevelDao;
import com.dikshatech.portal.dao.CompanyDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dto.AccessibilityLevel;
import com.dikshatech.portal.dto.AccessibilityLevelPk;
import com.dikshatech.portal.dto.AccessibilityTopLevel;
import com.dikshatech.portal.dto.Company;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.exceptions.AccessibilityLevelDaoException;
import com.dikshatech.portal.factory.AccessibilityLevelDaoFactory;
import com.dikshatech.portal.factory.AccessibilityTopLevelDaoFactory;
import com.dikshatech.portal.factory.CompanyDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.forms.PortalForm;

import java.util.Arrays;

public class AccessibilityLevelModel extends ActionMethods
{

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{

		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();

		AccessibilityLevel accessibilityLevelDto = (AccessibilityLevel) form;

		AccessibilityLevelDao accessibilityLevelDao = AccessibilityLevelDaoFactory.create();

		for (Integer id : accessibilityLevelDto.getAccessibilityIds())
		{

			try
			{
				accessibilityLevelDao.delete(new AccessibilityLevelPk(id.intValue()));
			}
			catch (AccessibilityLevelDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;

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

	@SuppressWarnings("unchecked")
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		AccessibilityLevelDao accessibilityLevelDao = AccessibilityLevelDaoFactory.create();
		AccessibilityTopLevelDao accessibilityTopLevelDao = AccessibilityTopLevelDaoFactory.create();

		CompanyDao companyDao = CompanyDaoFactory.create();
		RegionsDao rDao = RegionsDaoFactory.create();
		DivisonDao divisonDao = DivisonDaoFactory.create();

		try
		{
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{
			case RECEIVEACCESSIBILITY:
			{
				AccessibilityLevel accessibilityDto = (AccessibilityLevel) form;
				AccessibilityLevel dbReturnaccessibilityDto = accessibilityLevelDao
				        .findByPrimaryKey(accessibilityDto.getId());
				
				Set<String> companyList = new HashSet<String>();
				Set<String> regionList = new HashSet<String>();
				Set<String> deptlistList = new HashSet<String>();
				List<String> divisionList = new ArrayList<String>();
				
				if (dbReturnaccessibilityDto != null)
				{
					
					AccessibilityTopLevel[] accessibilityTopLevel = accessibilityTopLevelDao
					        .findWhereAccessibilityIdEquals(dbReturnaccessibilityDto
					                .getId());
					for (AccessibilityTopLevel topLevel : accessibilityTopLevel)
					{
						companyList.add(topLevel.getCompanyId());
						
					}
					
				}
				
				AccessibilityTopLevel[] accessibilityFindRegionArr = null;
				
				int index = 0;
				Company[] companies1 = companyDao.findAll();
				NestedBean[] companySet = new NestedBean[companies1.length];
				for (Company company2 : companies1)
				{
					
					NestedBean companyBean = new NestedBean();
					companyBean.setDataLevel("Company");
					companyBean.setFieldId(company2.getId());
					companyBean.setFieldName(company2.getCompanyName());
					
					if (companyList.contains(new Integer(company2.getId())
					        .toString()))
					{
						companyBean.setIsSelected(1);
						
						// accessibilityFindRegionArr= accessibilityTopLevelDao.findWhereCompanyIdEquals(new
						// Integer(company2.getId()).toString());
						
						accessibilityFindRegionArr = accessibilityTopLevelDao
						        .findByDynamicWhere(
						                "COMPANY_ID = ? AND ACCESSIBILITY_ID = ?",
						                new Object[]
						                {
						                        new Integer(company2
						                                .getId()),
						                        new Integer(
						                                dbReturnaccessibilityDto
						                                        .getId())
						                });
						for (AccessibilityTopLevel accessibilityFindRegion : accessibilityFindRegionArr)
						{
							
							regionList.add(accessibilityFindRegion
							        .getRegionId());
						}
						
					}
					else
					{
						
						companyBean.setIsSelected(0);
					}
					
					Set<NestedBean> regionSet = new HashSet<NestedBean>();
					
					Regions[] regions = rDao
					        .findByCompany(company2.getId());
					int regionCount = 0;
					for (Regions regions2 : regions)
					{
						NestedBean regionBean = new NestedBean();
						
						regionBean.setDataLevel("Region");
						regionBean.setFieldId(regions2.getId());
						regionBean.setFieldName(regions2.getRegName());
						
						if (regionList.contains(new Integer(0).toString()))
						{
							
							regionBean.setIsSelected(1);
							regionCount++;
						}
						
						else if (regionList.contains(new Integer(regions2
						        .getId()).toString()))
						{
							regionBean.setIsSelected(1);
							companyBean.setIsSelected(2);
							regionCount++;
							
							for (AccessibilityTopLevel accessibilityFindRegion : accessibilityFindRegionArr)
							{
								if (new Integer(regions2.getId())
								        .toString().equals(
								                accessibilityFindRegion
								                        .getRegionId()))
								{
									
									deptlistList
									        .add(accessibilityFindRegion
									                .getDepartmentId());
								}
								
							}
							
						}
						if (companyBean.getIsSelected() == 1)
						{
							
							regionBean.setIsSelected(1);
						}
						
						Set<NestedBean> departmentSet = new HashSet<NestedBean>();
						
						Divison[] departments = divisonDao
						        .findByDynamicWhere(
						                "REGION_ID = ? AND PARENT_ID = ?",
						                new Object[]
						                {
						                        new Integer(regions2
						                                .getId()),
						                        new Integer(0)
						                });
						
						int deptCount = 0;
						for (Divison dept : departments)
						{
							NestedBean departmentBean = new NestedBean();
							departmentBean.setDataLevel("Department");
							departmentBean.setFieldId(dept.getId());
							departmentBean.setFieldName(dept.getName());
							
							if (deptlistList.contains(new Integer(0)
							        .toString())
							        && regionList.contains(new Integer(
							                regions2.getId()).toString()))
							{
								
								departmentBean.setIsSelected(1);
								deptCount++;
							}
							
							else if (deptlistList.contains(new Integer(dept
							        .getId()).toString()))
							{
								departmentBean.setIsSelected(1);
								regionBean.setIsSelected(2);
								deptCount++;
								
								for (AccessibilityTopLevel accessibilityFindRegion : accessibilityFindRegionArr)
								{
									if (new Integer(regions2.getId())
									        .toString().equals(
									                accessibilityFindRegion
									                        .getRegionId())
									        && new Integer(dept.getId())
									                .toString()
									                .equals(
									                        accessibilityFindRegion
									                                .getDepartmentId()))
									{
										
										String[] str = accessibilityFindRegion
										        .getDivisionId().split(
										                "\\,");
										divisionList = Arrays.asList(str);
									}
								}
							}
							
							Set<NestedBean> divisionSet = new HashSet<NestedBean>();
							
							Divison[] divisons = divisonDao
							        .findWhereParentIdEquals(dept.getId());
							int divisionCount = 0;
							for (Divison divison : divisons)
							{
								NestedBean divisionBean = new NestedBean();
								divisionBean.setDataLevel("Division");
								divisionBean.setFieldId(divison.getId());
								divisionBean
								        .setFieldName(divison.getName());
								
								if (divisionList.contains(new Integer(0)
								        .toString())
								        && deptlistList
								                .contains(new Integer(dept
								                        .getId())
								                        .toString()))
								{
									divisionBean.setIsSelected(1);
									divisionCount++;
								}
								
								else if (divisionList.contains(new Integer(
								        divison.getId()).toString()))
								{
									divisionBean.setIsSelected(1);
									departmentBean.setIsSelected(2);
									divisionCount++;
									
								}
								
								divisionSet.add(divisionBean);
							} // End of division loop
							
							if ((divisons.length != 0 || divisionCount != 0)
							        && (divisons.length == divisionCount))
							{
								
								departmentBean.setIsSelected(1);
							}
							departmentBean.setNestedRecord(divisionSet);
							departmentSet.add(departmentBean);
							
						} // End of deptartment loop
						
						if ((departments.length != 0 || deptCount != 0)
						        && (departments.length == deptCount))
						{
							regionBean.setIsSelected(1);
							
						}
						regionBean.setNestedRecord(departmentSet);
						regionSet.add(regionBean);
						
					} // end of Region loop
					
					if ((regions.length != 0 || regionCount != 0)
					        && (regions.length == regionCount))
					{
						companyBean.setIsSelected(1);
					}
					companyBean.setNestedRecord(regionSet);
					companySet[index] = companyBean;
					index++;
				}
				// dropDown2.setDropDown(companySet);
				
				dbReturnaccessibilityDto.setDropDown(companySet);
				
				if (companySet.length != 0)
				{
					for (NestedBean company : companySet)
					{
						
						for (NestedBean region : company.getNestedRecord())
						{
							
							if (region.getIsSelected() == 1
							        && deptlistList.isEmpty())
							{
								
								for (NestedBean dept : region
								        .getNestedRecord())
								{
									
									dept.setIsSelected(1);
									
									for (NestedBean division : dept
									        .getNestedRecord())
									{
										
										division.setIsSelected(1);
									}
								}
							}
						}
						
					}
					
				}
				request
				        .setAttribute("actionForm",
				                dbReturnaccessibilityDto);
			}
				
		}// End of switch statement
		
	}
	catch (AccessibilityLevelDaoException e)
	{
		e.printStackTrace();
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

		AccessibilityLevel accessibilityLevelDto = (AccessibilityLevel) form;
		AccessibilityLevelDao accessibilityLevelDao = AccessibilityLevelDaoFactory.create();
		AccessibilityTopLevelDao accessibilityTopDao = AccessibilityTopLevelDaoFactory.create();

		try
		{

			AccessibilityLevel tempAccessibilityLevel = parseAccessibilityLevel(accessibilityLevelDto);

			AccessibilityLevelPk accessibilityLevelPk = accessibilityLevelDao.insert(tempAccessibilityLevel);

			if (accessibilityLevelDto.getLocaleAccessArr().length > 0)
			{

				String[ ] strArr = accessibilityLevelDto.getLocaleAccessArr();
				AccessibilityTopLevel accessibilityTopDto = null;
				if (strArr.length == 1)
				{
					String str = strArr[0];
					String[ ] pipeSplitArr = str.split("\\|");
					String[ ] leftArr = pipeSplitArr[0].split("\\,");
					String[ ] rightArr = pipeSplitArr[1].split("\\,");

					accessibilityTopDto = new AccessibilityTopLevel();

					accessibilityTopDto.setCompanyId(Integer.parseInt(leftArr[0]) != 0 ? leftArr[0] : new Integer(0).toString());
					accessibilityTopDto.setRegionId(Integer.parseInt(leftArr[1]) != 0 ? leftArr[1] : new Integer(0).toString());
					accessibilityTopDto.setDepartmentId(Integer.parseInt(leftArr[2]) != 0 ? leftArr[2] : new Integer(0).toString());
					accessibilityTopDto.setDivisionId(rightArr.length != 0 ? pipeSplitArr[1] : new Integer(0).toString());
					accessibilityTopDto.setAccessibilityId(accessibilityLevelPk.getId());

					accessibilityTopDao.insert(accessibilityTopDto);

				}
				else
				{

					for (String str : strArr)
					{

						String[ ] pipeSplitArr = str.split("\\|");
						String[ ] leftArr = pipeSplitArr[0].split("\\,");
						String[ ] rightArr = pipeSplitArr[1].split("\\,");

						accessibilityTopDto = new AccessibilityTopLevel();

						accessibilityTopDto.setCompanyId(Integer.parseInt(leftArr[0]) != 0 ? leftArr[0] : new Integer(0).toString());
						accessibilityTopDto.setRegionId(Integer.parseInt(leftArr[1]) != 0 ? leftArr[1] : new Integer(0).toString());
						accessibilityTopDto.setDepartmentId(Integer.parseInt(leftArr[2]) != 0 ? leftArr[2] : new Integer(0).toString());
						accessibilityTopDto.setDivisionId(rightArr.length != 0 ? pipeSplitArr[1] : new Integer(0).toString());
						accessibilityTopDto.setAccessibilityId(accessibilityLevelPk.getId());
						if (!accessibilityTopDto.getRegionId().equals("0"))
							accessibilityTopDao.insert(accessibilityTopDto);

					}// End of for

				}

			}// End of if

			if (accessibilityLevelPk != null)
			{
				tempAccessibilityLevel.setId(accessibilityLevelPk.getId());
				request.setAttribute("actionForm", tempAccessibilityLevel);
			}

		} catch (AccessibilityLevelDaoException ale)
		{
			ale.printStackTrace();
		}

		catch (Exception e)
		{
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();

		
		AccessibilityLevel accessibilityLevelDto = (AccessibilityLevel) form;
		AccessibilityLevelDao accessibilityLevelDao = AccessibilityLevelDaoFactory.create();
		AccessibilityTopLevelDao accessibilityTopDao = AccessibilityTopLevelDaoFactory.create();
        int asseccibilityId=accessibilityLevelDto.getId();
        
       
		try
		{
			
			AccessibilityLevel tempAccessibilityLevel = parseAccessibilityLevel(accessibilityLevelDto);
			tempAccessibilityLevel.setId(0);
			AccessibilityLevelPk accessibilityLevelPk = accessibilityLevelDao.insert(tempAccessibilityLevel);

			if (accessibilityLevelDto.getLocaleAccessArr().length > 0)
			{

				String[ ] strArr = accessibilityLevelDto.getLocaleAccessArr();
				AccessibilityTopLevel accessibilityTopDto = null;
				if (strArr.length == 1)
				{
					String str = strArr[0];
					String[ ] pipeSplitArr = str.split("\\|");
					String[ ] leftArr = pipeSplitArr[0].split("\\,");
					String[ ] rightArr = pipeSplitArr[1].split("\\,");

					accessibilityTopDto = new AccessibilityTopLevel();

					accessibilityTopDto.setCompanyId(Integer.parseInt(leftArr[0]) != 0 ? leftArr[0] : new Integer(0).toString());
					accessibilityTopDto.setRegionId(Integer.parseInt(leftArr[1]) != 0 ? leftArr[1] : new Integer(0).toString());
					accessibilityTopDto.setDepartmentId(Integer.parseInt(leftArr[2]) != 0 ? leftArr[2] : new Integer(0).toString());
					accessibilityTopDto.setDivisionId(rightArr.length != 0 ? pipeSplitArr[1] : new Integer(0).toString());
					accessibilityTopDto.setAccessibilityId(accessibilityLevelPk.getId());

					accessibilityTopDao.insert(accessibilityTopDto);

				}
				else
				{

					for (String str : strArr)
					{

						String[ ] pipeSplitArr = str.split("\\|");
						String[ ] leftArr = pipeSplitArr[0].split("\\,");
						String[ ] rightArr = pipeSplitArr[1].split("\\,");

						accessibilityTopDto = new AccessibilityTopLevel();

						accessibilityTopDto.setCompanyId(Integer.parseInt(leftArr[0]) != 0 ? leftArr[0] : new Integer(0).toString());
						accessibilityTopDto.setRegionId(Integer.parseInt(leftArr[1]) != 0 ? leftArr[1] : new Integer(0).toString());
						accessibilityTopDto.setDepartmentId(Integer.parseInt(leftArr[2]) != 0 ? leftArr[2] : new Integer(0).toString());
						accessibilityTopDto.setDivisionId(rightArr.length != 0 ? pipeSplitArr[1] : new Integer(0).toString());
						accessibilityTopDto.setAccessibilityId(accessibilityLevelPk.getId());
						if (!accessibilityTopDto.getRegionId().equals("0"))
							accessibilityTopDao.insert(accessibilityTopDto);

					}// End of for

				}

			}// End of if

			if (accessibilityLevelPk != null)
			{
				tempAccessibilityLevel.setId(accessibilityLevelPk.getId());
				request.setAttribute("actionForm", tempAccessibilityLevel);	
				//--|Deleting old accessibility mapped to the role after updation
			//	accessibilityLevelDao.delete(new AccessibilityLevelPk(asseccibilityId));
				
			}               

		}
		catch (AccessibilityLevelDaoException ale)
		{
			ale.printStackTrace();
		}

		catch (Exception e)
		{
			// TODO: handle exception
		}
		
		return result;
	}
	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private AccessibilityLevel parseAccessibilityLevel(AccessibilityLevel accessibilityLevel)
	{

		//AccessibilityLevel accessibilityLevel=new AccessibilityLevel();
		
		String[] permissionTypes = accessibilityLevel.getPermissionLabel().split("\\,");

		for (int i = 0; i < permissionTypes.length; i++)
		{

			switch (EnumUtil.PermissionType.getValue(permissionTypes[i]))
			{

			case SAVE:
			{

				accessibilityLevel.setSave(new Short((short) 1).shortValue());
				break;
			}
			case VIEW:

			{
				accessibilityLevel.setView(new Short((short) 1).shortValue());

				break;
			}
			case REMOVE:
			{

				accessibilityLevel.setRemove(new Short((short) 1).shortValue());

				break;
			}
			case MODIFY:
			{

				accessibilityLevel.setModify(new Short((short) 1).shortValue());
				break;
			}
			case MARKEMPLOYEE:
			{

				accessibilityLevel.setMarkAsEmp(new Short((short) 1).shortValue());
				break;
			}
			case ENABLE:
			{

				accessibilityLevel.setEnable(new Short((short) 1).shortValue());
				break;
			}
			case DISABLE:
			{

				accessibilityLevel.setDisable(new Short((short) 1).shortValue());

				break;
			}
			case GENERATEOFFER:
			{

				accessibilityLevel.setGenOffer(new Short((short) 1).shortValue());
				break;
			}
			case RESENDOFFER:
			{

				accessibilityLevel.setResendOffer(new Short((short) 1).shortValue());
				break;
			}
			case APPROVE:
			{

				accessibilityLevel.setApprove(new Short((short) 1).shortValue());
				break;
			}
			case REJECT:
			{

				accessibilityLevel.setReject(new Short((short) 1).shortValue());

				break;
			}
			case NOSHOW:
			{

				accessibilityLevel.setNoshow(new Short((short) 1).shortValue());

				break;
			}
			case CANCEL:
			{

				accessibilityLevel.setCancel(new Short((short) 1).shortValue());
				break;
			}
			case ROLLON:

			{
				accessibilityLevel.setRollon(new Short((short) 1).shortValue());

				break;
			}
			case SUBMIT:
			{

				accessibilityLevel.setSubmit(new Short((short) 1).shortValue());

				break;
			}
			case DOWNLOAD:
			{

				accessibilityLevel.setDownload(new Short((short) 1).shortValue());
				break;
			}
			case UPLOAD:
			{

				accessibilityLevel.setUpload(new Short((short) 1).shortValue());
				break;
			}
			case EMAIL:
			{

				accessibilityLevel.setEmail(new Short((short) 1).shortValue());
				break;
			}
			case ASSIGN:
			{

				accessibilityLevel.setAssign(new Short((short) 1).shortValue());
				break;
			}
			case HOLD:
			{

				accessibilityLevel.setHold(new Short((short) 1).shortValue());
				break;
			}
			default:
				break;

			} // End of switch

		}// End of for
		return accessibilityLevel;

	}
}
