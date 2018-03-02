package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.Division;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.enums.StateType;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AccessibilityTopLevelDao;
import com.dikshatech.portal.dao.ActiveChargeCodeDao;
import com.dikshatech.portal.dao.CalendarDao;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.ClientDao;
import com.dikshatech.portal.dao.CompanyDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.PoDetailDao;
import com.dikshatech.portal.dao.PoDetailsDao;
import com.dikshatech.portal.dao.PoEmpMapDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjClientMapDao;
import com.dikshatech.portal.dao.ProjContInfoDao;
import com.dikshatech.portal.dao.ProjHistoryDao;
import com.dikshatech.portal.dao.ProjLocationsDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.ProjectMapDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RollOnDao;
import com.dikshatech.portal.dao.RollOnProjMapDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.AccessibilityTopLevel;
import com.dikshatech.portal.dto.ActiveChargeCode;
import com.dikshatech.portal.dto.Calendar;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.Client;
import com.dikshatech.portal.dto.Company;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.PoDetail;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.PoEmpMap;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.ProjClientMap;
import com.dikshatech.portal.dto.ProjContInfo;
import com.dikshatech.portal.dto.ProjHistory;
import com.dikshatech.portal.dto.ProjLocations;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectMap;
import com.dikshatech.portal.dto.ProjectPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.RollOnProjMap;
import com.dikshatech.portal.dto.UserDetails;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.AccessibilityTopLevelDaoException;
import com.dikshatech.portal.exceptions.ActiveChargeCodeDaoException;
import com.dikshatech.portal.exceptions.CalendarDaoException;
import com.dikshatech.portal.exceptions.ChargeCodeDaoException;
import com.dikshatech.portal.exceptions.ClientDaoException;
import com.dikshatech.portal.exceptions.CompanyDaoException;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.ModulePermissionDaoException;
import com.dikshatech.portal.exceptions.PoDetailDaoException;
import com.dikshatech.portal.exceptions.PoDetailsDaoException;
import com.dikshatech.portal.exceptions.PoEmpMapDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjClientMapDaoException;
import com.dikshatech.portal.exceptions.ProjContInfoDaoException;
import com.dikshatech.portal.exceptions.ProjHistoryDaoException;
import com.dikshatech.portal.exceptions.ProjLocationsDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.ProjectMapDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.RollOnDaoException;
import com.dikshatech.portal.exceptions.RollOnProjMapDaoException;
import com.dikshatech.portal.exceptions.UserRolesDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.AccessibilityTopLevelDaoFactory;
import com.dikshatech.portal.factory.ActiveChargeCodeDaoFactory;
import com.dikshatech.portal.factory.CalendarDaoFactory;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.ClientDaoFactory;
import com.dikshatech.portal.factory.CompanyDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.PoDetailDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.PoEmpMapDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjClientMapDaoFactory;
import com.dikshatech.portal.factory.ProjContInfoDaoFactory;
import com.dikshatech.portal.factory.ProjHistoryDaoFactory;
import com.dikshatech.portal.factory.ProjLocationsDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.ProjectMapDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.factory.RollOnProjMapDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class ProjectModel extends ActionMethods
{

	private static final int BILLABLE     = 1;
	private static final int NON_BILLABLE = 2;
	private static final int SUPPORT      = 3;
	private static final int MAINTENANCE  = 4;
	Logger	logger	= LoggerUtil.getLogger();

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
		Login loginDto = Login.getLogin(request);
		ActionResult result = new ActionResult();
		
		switch (ActionMethods.ExecuteTypes.getValue(form.geteType()))
		{

			case DISABLEPROJECT:
			{
				Project project = (Project) form;
				ProjectDao projectDao = ProjectDaoFactory.create();
				
				try{
					RollOnProjMap[] rollOnProjMap = RollOnProjMapDaoFactory.create().findWhereProjIdEquals(project.getId());
					if(rollOnProjMap.length>0){
						logger.debug("DISABLEPROJECT : project with id="+project.getId()+" is being disabled...there are resources rolled_on to this project");
						logger.debug("DISABLEPROJECT : projectId="+project.getId()+" finding out if these resources are currently rolledOn to this project and are active");
						UsersDao usersDao = UsersDaoFactory.create();
						Users[] activeRolledOnResources = usersDao.findByDynamicSelect("SELECT * FROM USERS U WHERE U.ID IN" +
								"(SELECT RO.EMP_ID FROM ROLL_ON RO WHERE RO.ID IN" +
								"(SELECT ROLL_ON_ID FROM ROLL_ON_PROJ_MAP RPM WHERE RPM.PROJ_ID=?) AND RO.CURRENT=1) AND U.STATUS=0", new Object[]{project.getId()});
						if(activeRolledOnResources.length>0){
							logger.debug("DISABLE PROJECT FOR PROJECT_ID=" + project.getId() + " FAILED..REASON->THERE WERE RESOURCES ASSOCIATED WITH THE PROJECT");
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("project.disable.failed"));
							result.setForwardName("success");
							return result;
						}else
							logger.debug("DISABLEPROJECT : there were resources rolledOn to project with id="+project.getId()+" but none of them were active...proceed DISABLE");							
					}else
						logger.debug("DISABLEPROJECT : the project selected is not having any resources rolled_on to it....proceed DISABLE");						
				}catch(Exception ex){
					logger.error("DISABLEPROJECT : exception was thrown when trying to fetch details required to disable a project", ex);
				}
				
				ProjHistoryDao historyDao = ProjHistoryDaoFactory.create();
				UsersDao usersDao = UsersDaoFactory.create();
				UserRolesDao userRolesDao = UserRolesDaoFactory.create();
				ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
				ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				String region = "None";
				try
				{
					Project project2 = projectDao.findByPrimaryKey(project.getId());
					logger.debug("Project find is>>" + project2);
					ProjectPk projectPk = project.createPk();
					project2.setIsEnable(StateType.Disabled.name());
					projectDao.update(projectPk, project2);

					if (form.getUserId() != null)
					{

						ProjHistory history = new ProjHistory();
						history.setLastModifiedBy(form.getUserId());
						history.setProjId(project.getId());
						history.setModifiedTime(new Date());
						historyDao.insert(history);
					}

					region = project2.getRegionId() > 0 ? RegionsDaoFactory.create().findByPrimaryKey(project2.getRegionId()).getRegName() : region;
					// send Mail Notification
					UserRoles userRolesDto = userRolesDao.findWhereUserIdEquals(form.getUserId().intValue());
					ModulePermission modulePermisssionData = modulePermissionDao.findByDynamicSelect("SELECT * FROM MODULE_PERMISSION WHERE ROLE_ID=? AND MODULE_ID=32", new Object[ ]
					{ new Integer(userRolesDto.getRoleId()) })[0];
					ProcessChain processChainDto = processChainDao.findWhereIdEquals(modulePermisssionData.getProcChainId())[0];

					ProcessEvaluator pEvaluator = new ProcessEvaluator();
					Integer[ ] userIds = pEvaluator.notifiers(processChainDto.getNotification(), form.getUserId());
					
					String disabledBy = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(loginDto.getUserId()).getProfileId()).getFirstName();
					
					String mailSubject = "Diksha Lynx: Project ["+project2.getName()+"] disabled by ["+disabledBy+"]";
					if (userIds.length > 0)
					{
						for (int id : userIds)
						{
							Users usersDto = usersDao.findByPrimaryKey(id);
							ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(usersDto.getProfileId());
							sendMailDetails(mailSubject, profileInfo.getOfficalEmailId(), profileInfo.getFirstName(), region, MailSettings.PROJECT_DETAILS_DISABLED, projectPk.getId(), project2.getName(), project.toString(),disabledBy);
						}
					}
					logger.debug("DISABLE_PROJECT : project with id="+project.getId()+" is disabled...sent mail..proceeding to disable associated charge-codes");
					try{
						PoDetailsDao poDetailsDao = PoDetailsDaoFactory.create();
						PoDetails[] enabledPoDetails = poDetailsDao.findByDynamicWhere(" PROJ_ID=? AND IS_DISABLE=0", new Object[]{project.getId()});
						if(enabledPoDetails.length>0){
							logger.debug("DISABLE_PROJECT : project with id="+project.getId()+" is having ("+enabledPoDetails.length+") charge code(s) associated with it, which will be disabled");
							int noOfRowsUpdated = poDetailsDao.updatePoDetailsForProjectDisable(project.getId());
							logger.debug("DISABLE_PROJECT : updated PO_DETAILS.IS_DISABLED... no of rows affected="+noOfRowsUpdated);
						}else
							logger.debug("DISABLE_PROJECT : project with id="+project.getId()+" is having no charge code(s) associated with it, that can be disabled");						
					}catch(Exception ex){
						logger.error("DISABLE_PROJECT : Exception was thrown when trying to update PO_DETAILS.IS_DISABLE",ex);
					}
					
					result.setForwardName("success");
				} catch (ProjectDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProjHistoryDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RegionsDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UserRolesDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ModulePermissionDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProcessChainDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProfileInfoDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UsersDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case ENABLEPROJECT:
			{
				Project project = (Project) form;
				ProjectDao projectDao = ProjectDaoFactory.create();
				ProjHistoryDao historyDao = ProjHistoryDaoFactory.create();
				UsersDao usersDao = UsersDaoFactory.create();
				UserRolesDao userRolesDao = UserRolesDaoFactory.create();
				ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
				ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				String region = "None";
				try
				{
					Project project2 = projectDao.findByPrimaryKey(project.getId());
					logger.debug("Project find is>>" + project2);
					ProjectPk projectPk = project.createPk();
					project2.setIsEnable(StateType.Enabled.name());
					projectDao.update(projectPk, project2);

					if (form.getUserId() != null)
					{

						ProjHistory history = new ProjHistory();
						history.setLastModifiedBy(form.getUserId());
						history.setProjId(project.getId());
						history.setModifiedTime(new Date());
						historyDao.insert(history);
					}
					region = project2.getRegionId() > 0 ? RegionsDaoFactory.create().findByPrimaryKey(project2.getRegionId()).getRegName() : region;
					// send Mail Notification
					UserRoles userRolesDto = userRolesDao.findWhereUserIdEquals(form.getUserId().intValue());
					ModulePermission modulePermisssionData = modulePermissionDao.findByDynamicSelect("SELECT * FROM MODULE_PERMISSION WHERE ROLE_ID=? AND MODULE_ID=32", new Object[ ]
					{ new Integer(userRolesDto.getRoleId()) })[0];
					ProcessChain processChainDto = processChainDao.findWhereIdEquals(modulePermisssionData.getProcChainId())[0];

					/*
					 * ProcessChainBean processChainBean =
					 * DtoToBeanConverter.DtoToBeanConverterToParseProcessChain
					 * (processChainDto.getNotification());
					 */
					ProcessEvaluator pEvaluator = new ProcessEvaluator();
					Integer[ ] userIds = pEvaluator.notifiers(processChainDto.getNotification(), form.getUserId());
					
					String enabledBy = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(project2.getOwnerId()).getProfileId()).getFirstName();
					
					String mailSubject = "Diksha Lynx: Project ["+project2.getName()+"] enabled by ["+enabledBy+"]";
					
					if (userIds.length > 0)
					{
						for (int id : userIds)
						{
							Users usersDto = usersDao.findByPrimaryKey(id);
							ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(usersDto.getProfileId());
							sendMailDetails(mailSubject, profileInfo.getOfficalEmailId(), profileInfo.getFirstName(), region, MailSettings.PROJECT_DETAILS_ENABLED, projectPk.getId(), project2.getName(), null,enabledBy);
						}
					}
					logger.debug("ENABLE_PROJECT : project with id="+project.getId()+" is ENABLED...proceeding to enable associated charge-codes..");
					try{
						PoDetailsDao poDetailsDao = PoDetailsDaoFactory.create();
						PoDetails[] disabledPoDetails = poDetailsDao.findByDynamicWhere(" PROJ_ID=? AND IS_DISABLE=1", new Object[]{project.getId()});
						if(disabledPoDetails.length>0){
							logger.debug("ENABLE_PROJECT : found disabled charge-codes associated with project_Id="+project.getId());
							int noOfRowsUpdated = poDetailsDao.updatePoDetailsForProjectEnable(project.getId());
							logger.debug("ENABLE_PROJECT : enabled charge-codes associated with project_Id="+project.getId()+"...no of rows affected="+noOfRowsUpdated);							
						}else
							logger.debug("ENABLE_PROJECT : found no disabled charge-codes associated with project_Id="+project.getId());
					}catch(Exception ex){
						logger.error("ENABLE_PROJECT : Exception was thrown when trying to enable charge-codes associated with project_Id="+project.getId(), ex);
					}
					
					result.setForwardName("success");
				} catch (ProjectDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProjHistoryDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RegionsDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UserRolesDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ModulePermissionDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProcessChainDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				catch (UsersDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProfileInfoDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			default:
				break;
		}
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Login loginDto = Login.getLogin(request);
		AccessibilityTopLevelDao topLevelDao = AccessibilityTopLevelDaoFactory.create();
		DropDown dropDown = new DropDown();
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
		{
		
		case RECEIVEPROJECTSOWNED:
			/*
			 * This is used to populate the list of projects during creation of new charge-code
			 * select those projects, owned by login.getUserId() which are ENABLED  
			 */
			{
				Project dropdownForm = (Project) form;
				try{
					int userId = loginDto.getUserId().intValue();
					// give temperary access to vikram chandra be half of abhishek.
					if (userId == 38){
						userId = 24;
					}
					ProjectDao projectDao = ProjectDaoFactory.create();
					Project[] projectsOwned = projectDao.findByDynamicSelect("SELECT * FROM PROJECT WHERE OWNER_ID=? AND IS_ENABLE LIKE 'ENABLED'", new Object[] { userId });
					Set<Project> projectInfoSet = new HashSet<Project>();
					dropdownForm.setRegionId(RegionsDaoFactory.create().getRegionId(userId));
					dropdownForm.setCompanyId(getCompanyIdForUser(dropdownForm.getRegionId()));
					projectInfoSet.addAll(receiveProjectsByCompany(dropdownForm.getCompanyId()));
					projectInfoSet.addAll(receiveProjectsByRegion(dropdownForm.getCompanyId(),dropdownForm.getRegionId()));
					if(projectsOwned!=null & projectsOwned.length>0){
						for(Project eachProject : projectsOwned){
							Project tempProj = new Project();
							tempProj.setId(eachProject.getId());
							tempProj.setName(eachProject.getName());
							projectInfoSet.add(tempProj);
						}
						dropdownForm.setProjects(projectInfoSet);
						result.setForwardName("success");
					}else{
						ProfileInfo ownerProfile = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT * FROM PROFILE_INFO PI WHERE PI.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { userId })[0];
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("populate.project.none",ownerProfile.getFirstName()+" "+ownerProfile.getLastName()));
						result.setForwardName("success");
						return result;
					}
				}catch(Exception ex){
					ex.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.populate.project"));
					result.setForwardName("success");
					return result;
				}
				
			}break;
			
			
		case RECEIVEALLPROJECTSFORHR:
		{

			Project dropdownForm = (Project) form;
			try{
				ProjectDao projectDao = ProjectDaoFactory.create();
				Project[] projectsOwned = projectDao.findByDynamicSelect("SELECT * FROM PROJECT WHERE IS_ENABLE LIKE 'ENABLED'", null);
				if(projectsOwned!=null & projectsOwned.length>0){
					Set<Project> benchProjectSet = new HashSet<Project>();
					dropdownForm.setRegionId(RegionsDaoFactory.create().getRegionId(loginDto.getUserId()));
					dropdownForm.setCompanyId(getCompanyIdForUser(dropdownForm.getRegionId()));
					benchProjectSet.addAll(receiveProjectsByCompany(dropdownForm.getCompanyId()));
					benchProjectSet.addAll(receiveProjectsByRegion(dropdownForm.getCompanyId(),dropdownForm.getRegionId()));
					Set<Project> projectInfoSet = new HashSet<Project>();
					for(Project eachProject : projectsOwned){
						Project tempProj = new Project();
						tempProj.setId(eachProject.getId());
						tempProj.setName(eachProject.getName());
						if(benchProjectSet.contains(tempProj)){
							tempProj.setBenchProject(true);
						}
						projectInfoSet.add(tempProj);
					}
					dropdownForm.setProjects(projectInfoSet);
					result.setForwardName("success");
				}else{
					ProfileInfo ownerProfile = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT * FROM PROFILE_INFO PI WHERE PI.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)",new Object[]{loginDto.getUserId()})[0];
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("populate.project.none",ownerProfile.getFirstName()+" "+ownerProfile.getLastName()));
					result.setForwardName("success");
					return result;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.populate.project"));
				result.setForwardName("success");
				return result;
			}		
			
		}
			
			break;
					
		
			case RECEIVEALLPROJECT:
			{
			Project dropdownForm = (Project) form;
			ProjectDao projectDao = ProjectDaoFactory.create();
			Set<Project> projectSet = new HashSet<Project>();
			DivisonDao divDao = DivisonDaoFactory.create();
			// ProjectDivMapDao divMapDao = ProjectDivMapDaoFactory.create();
			RegionsDao regDao = RegionsDaoFactory.create();
			Divison department = null;
			String clientName = null;
			ProjClientMapDao clientMapDao = ProjClientMapDaoFactory.create();
			ClientDao clientDao = ClientDaoFactory.create();
			ProjectMapDao proMapDao = ProjectMapDaoFactory.create();
			ChargeCodeDao chCodeDao = ChargeCodeDaoFactory.create();
			PoDetailsDao poDao = PoDetailsDaoFactory.create();

			UsersDao usersDao = UsersDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			
			RollOnProjMapDao rollOnProjMapDao = RollOnProjMapDaoFactory.create();
			RollOnDao rollOnDao = RollOnDaoFactory.create();
			
			try {
				Project[] allProjects = null;

				if (dropdownForm.isShowDisabled())
					allProjects = projectDao.findAll();
				else
					allProjects = projectDao.findWhereIsEnableEquals("Enabled");//this will fetch only those whcih are enabled

				for (Project eachProject : allProjects) {
					Project tempProject = new Project();
					if (eachProject.getOwnerId() == loginDto.getUserId())
						tempProject.setOwnerFlag(true);//to control EDIT and DISABLE visibility
					ProjectMap[] projectMaps = proMapDao.findWhereProjIdEquals(eachProject.getId());

					if (projectMaps != null)
						for (ProjectMap projMap : projectMaps) {
							if (!projMap.isDeptIdNull()) {
								department = divDao.findByPrimaryKey(projMap.getDeptId());
								tempProject.setDeptId(department.getId());
								tempProject.setDeptName(department.getName());
							}
							// divison = divDao.findByPrimaryKey(projMap.getDivId());
							if (!projMap.isRegionIdNull()) {
								Regions region = regDao.findByPrimaryKey(projMap.getRegionId());
								tempProject.setRegionId(region.getId());
								tempProject.setRegionName(region.getRegName());
							}
						}

					tempProject.setId(eachProject.getId());

					// project owner information
					try {
						if (eachProject.getOwnerId()!= 0) {
							tempProject.setOwnerId(eachProject.getOwnerId());
							Users projectOwner = usersDao.findByPrimaryKey(new UsersPk(eachProject.getOwnerId()));
							ProfileInfo ownerInfo = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(projectOwner.getProfileId()));
							tempProject.setProjectOwnedBy(ownerInfo.getFirstName()+ " " + ownerInfo.getLastName());
						}
					} catch (UsersDaoException udex) {
						logger.error("RECEIVEALLPROJECT : EXCEPTION WAS THROWN WHEN TRYING TO FETCH INFORMATION FROM USER'S TABLE , WAS TRYING TO GET PROJECT_OWNER RELATED DATA",udex);
					} catch (ProfileInfoDaoException pidex) {
						logger.error("RECEIVEALLPROJECT : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM PROFILE_INFO TO DISPLAY PROJECT_OWNER NAME",pidex);
					}

					tempProject.setOwnerId(eachProject.getOwnerId());
					tempProject.setName(eachProject.getName());
					tempProject.setIsEnable(eachProject.getIsEnable());

					// Find Client
					ProjClientMap[] clients = clientMapDao.findWhereProjIdEquals(eachProject.getId());
					for (ProjClientMap projClientMap : clients) {
						Client client = clientDao.findByPrimaryKey(projClientMap.getClientId());
						clientName = client.getName();
						tempProject.setClientName(clientName);
					}

					// Set ChargeCode Detail
					String chCodequery = "  PO_ID IN(SELECT ID FROM PO_DETAILS WHERE PROJ_ID="+ eachProject.getId() + ")";
					ChargeCode[] allChargeCode = chCodeDao.findByDynamicWhere(chCodequery, null);

					if (allChargeCode.length != 0)
						tempProject.setProjChargeCode(allChargeCode);
					// Set Po_Details

					/*
					 * CHANGED..
					 * 
					 * String poQuery =
					 * " PO_ID IN(SELECT ID FROM PO_DETAILS WHERE PROJ_ID=" +
					 * project.getId() + ")"; PoEmpMap[ ] poEmpArr =
					 * poEmpDao.findByDynamicWhere(poQuery, null); if
					 * (poEmpArr.length != 0)
					 * tempProject.setNoOfResources(poEmpArr.length);
					 */

					// finding out no_of_active employees being rolled on to a particular project
					RollOnProjMap[] rollOnProjMaps = rollOnProjMapDao.findWhereProjIdEquals(eachProject.getId());
					ArrayList<Integer> rollOnIdList = new ArrayList<Integer>();
					if(rollOnProjMaps!=null && rollOnProjMaps.length>0){
						for(RollOnProjMap eachRow:rollOnProjMaps)
							rollOnIdList.add(eachRow.getRollOnId());
						
						String rollOnIds = rollOnIdList.toString().replace('[', ' ').replace(']', ' ').trim();
						RollOn[] rolledOnResources = rollOnDao.findByDynamicSelect("SELECT * FROM ROLL_ON RO WHERE RO.ID IN("+rollOnIds+") AND RO.CURRENT=1", null);
						
						//need to check if each resource is ACTIVE
						if(rolledOnResources!=null && rolledOnResources.length>0){
							ArrayList<Integer> userIdList = new ArrayList<Integer>();
							for(RollOn eachResource : rolledOnResources)
								userIdList.add(eachResource.getEmpId());//empId actually contains userId... column name mismatch
							
							String userIds = userIdList.toString().replace('[', ' ').replace(']', ' ').trim();
							Users[] activeAndRolledOn = usersDao.findByDynamicSelect("SELECT * FROM USERS U WHERE U.ID IN("+userIds+") AND U.STATUS=0", null);
							if(activeAndRolledOn!=null && activeAndRolledOn.length>0)
								tempProject.setNoOfResources(activeAndRolledOn.length);							
							else
								tempProject.setNoOfResources(0);	
						}else
							tempProject.setNoOfResources(0);						
						
					}else
						logger.debug("PROJECT : RECEIVEALL ...rollOnProjMaps returned 0 rows when finding out the number of resources on project with Id="+eachProject.getId());
					
					
					PoDetails[] poDetailsDto = poDao.findWhereProjIdEquals(eachProject.getId());
					if (poDetailsDto.length != 0) {
						tempProject.setProjPos(poDetailsDto);
						tempProject.setNoOfPoDetail(poDetailsDto.length);
					}
					tempProject.setEsrqmId(eachProject.getEsrqmId());
					tempProject.setCreateDateString(PortalUtility.formatDateToddmmyyyyhhmmss(eachProject.getCreateDate()));

					Users lastModifier = usersDao.findByPrimaryKey(eachProject
							.getLastModifiedBy());
					ProfileInfo modifierProfileInfo = profileInfoDao.findByPrimaryKey(lastModifier.getProfileId());

					tempProject.setLastModifierName(modifierProfileInfo.getFirstName()+ " " + modifierProfileInfo.getLastName());
					tempProject.setLastModifiedBy(eachProject.getLastModifiedBy());
					tempProject.setLastModifiedOnString(PortalUtility.formatDateToddmmyyyyhhmmss(eachProject.getLastModifiedOn()));

					projectSet.add(tempProject);
		
				}
				dropdownForm.setProjects(projectSet);
				result.setForwardName("success");
			} catch (ProjectDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DivisonDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RegionsDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProjClientMapDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProjectMapDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ChargeCodeDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PoDetailsDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UsersDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProfileInfoDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollOnProjMapDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollOnDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

			case RECEIVEPROJECT:
			{
				Project projectForm = (Project) form;
				ProjectDao projDao = ProjectDaoFactory.create();

				Client client = null;
				ClientDao clientDao = ClientDaoFactory.create();
				ProjLocationsDao locDao = ProjLocationsDaoFactory.create();
				ProjClientMapDao clientMapDao = ProjClientMapDaoFactory.create();
				ProjContInfoDao contactDao = ProjContInfoDaoFactory.create();

				DivisonDao divDao = DivisonDaoFactory.create();
				CalendarDao calDao=CalendarDaoFactory.create();
				RegionsDao regDao = RegionsDaoFactory.create();
				CompanyDao companyDao = CompanyDaoFactory.create();
				ProjectMapDao proMapDao = ProjectMapDaoFactory.create();
				ChargeCodeDao chCodeDao = ChargeCodeDaoFactory.create();

				UsersDao usersdao = UsersDaoFactory.create();
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				try
				{
					Project project = projDao.findByPrimaryKey(projectForm.getId());
					logger.debug("Project is:::" + project.getId());

					ProjClientMap[ ] clientMap = clientMapDao.findWhereProjIdEquals(project.getId());
					for (ProjClientMap projClientMap : clientMap)
					{
						client = clientDao.findByPrimaryKey(projClientMap.getClientId());
					}
					
					projectForm.setId(project.getId());
					
					try{
						Users modiferUser = usersdao.findByPrimaryKey(new UsersPk(project.getLastModifiedBy()));
						ProfileInfo modifierProfileInfo = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(modiferUser.getProfileId()));
						projectForm.setLastModifierName(modifierProfileInfo.getFirstName()+" "+modifierProfileInfo.getLastName());
						projectForm.setLastModifiedBy(project.getLastModifiedBy());
					}catch(UsersDaoException udex){
						logger.error("RECEIVEPROJECT : EXCEPTION WAS THROWN WHEN TRYING TO FETCH DATA FROM USERS TABLE..WAS TRYING TO GET LAST MODIFIER DATA",udex);
					}catch(ProfileInfoDaoException pidex){
						logger.error("RECEIVEPROJECT : EXCEPTION WAS THROWN WHEN TRYING TO FETCH DATA FROM PROFILE_INFO TABLE..WAS TRYING TO GET LAST MODIFIER NAME",pidex);
					}
					
					projectForm.setLastModifiedOnString(PortalUtility.formatDateToddmmyyyyhhmmss(project.getLastModifiedOn()));
					projectForm.setCreateDateString(PortalUtility.formatDateToddmmyyyyhhmmss(project.getCreateDate()));
					
					// project owner information
					try{
						projectForm.setOwnerId(project.getOwnerId());
						Users projectOwner = usersdao.findByPrimaryKey(new UsersPk(project.getOwnerId()));
						ProfileInfo ownerInfo = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(projectOwner.getProfileId()));
						projectForm.setProjectOwnedBy(ownerInfo.getFirstName() + " " + ownerInfo.getLastName());
					}catch(UsersDaoException udex){
						logger.error("RECEIVEPROJECT : EXCEPTION WAS THROWN WHEN TRYING TO FETCH INFORMATION FROM USER'S TABLE , WAS TRYING TO GET PROJECT_OWNER RELATED DATA", udex);
					}catch(ProfileInfoDaoException pidex){
						logger.error("RECEIVEPROJECT : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM PROFILE_INFO TO DISPLAY PROJECT_OWNER NAME", pidex);
					}

					try{
						projectForm.setCreatorId(project.getCreatorId());
						Users projectCreator = usersdao.findByPrimaryKey(new UsersPk(project.getCreatorId()));
						ProfileInfo creatorInfo = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(projectCreator.getProfileId()));
						projectForm.setCreatedBy(creatorInfo.getFirstName() + " " + creatorInfo.getLastName());
					}catch(Exception ex){
						logger.error("exception was thrown when trying to retrieve project creator information",ex);
					}
					
					projectForm.setName(project.getName());
					if (client != null)
					{
						projectForm.setClientName(client.getName());
						projectForm.setClientId(client.getId());
					}

					projectForm.setBillAddress((project.getBillAddress() != null) ? project.getBillAddress() : null);
					projectForm.setBillCity((project.getBillCity() != null) ? project.getBillCity() : null);
					projectForm.setBillCountry((project.getBillCountry() != null) ? project.getBillCountry() : null);
					projectForm.setBillState((project.getBillState() != null) ? project.getBillState() : null);
					if (project.getBillZipCode() > 0)
					projectForm.setBillZipCode(project.getBillZipCode());
					projectForm.setBillFaxNum(project.getBillFaxNum());

					projectForm.setBillTelNum(project.getBillTelNum());
					projectForm.setIsEnable(project.getIsEnable());
					projectForm.setDescription((project.getDescription() != null) ? project.getDescription() : null);
					// Set Project Locations Info
					Set< ProjLocations > locationsSet = new HashSet< ProjLocations >();
					ProjLocations[ ] locations = locDao.findWhereProjIdEquals(project.getId());
					if (locations != null)
					{
						for (ProjLocations projLocations : locations)
						{
							ProjLocations location = new ProjLocations();
							location.setAddress(projLocations.getAddress());
							location.setName(projLocations.getName());
							location.setCity(projLocations.getCity());
							location.setCountry(projLocations.getCountry());
							location.setState(projLocations.getState());
							location.setId(projLocations.getId());
							if (projLocations.getZipCode() > 0)
								location.setZipCode(projLocations.getZipCode());
							if (projLocations.getFaxNum() != null)
								location.setFaxNum(projLocations.getFaxNum());
							if (projLocations.getTelNum() != null)
								location.setTelNum(projLocations.getTelNum());
							locationsSet.add(location);
						}
						projectForm.setLocations(locationsSet);
					}
					// Set Project Contact Info
					Set< ProjContInfo > contInfoSet = new HashSet< ProjContInfo >();
					ProjContInfo[ ] contInfos = contactDao.findWhereProjIdEquals(project.getId());
					if (contInfos != null)
					{
						for (ProjContInfo projContInfo : contInfos)
						{
							ProjContInfo contactInfo = new ProjContInfo();
							contactInfo.setName(projContInfo.getName());
							if (projContInfo.getContNumber() != null)
								contactInfo.setContNumber(projContInfo.getContNumber());
							contactInfo.setDesignation(projContInfo.getDesignation());
							contactInfo.setEMail(projContInfo.getEMail());
							contactInfo.setContType(projContInfo.getContType());
							contactInfo.setComments(projContInfo.getComments());
							contactInfo.setId(projContInfo.getId());
							contInfoSet.add(contactInfo);
						}
						projectForm.setContacts(contInfoSet);
					}
					// Set Division Info
					Set< Divison > divisionSet = new HashSet< Divison >();
					ProjectMap[ ] projectMaps = proMapDao.findWhereProjIdEquals(project.getId());
					Company company = null;
					Regions region = null;
					Divison department = null;
					Calendar calendar=null;
					if (projectMaps != null)
						for (ProjectMap projectMap : projectMaps)
						{
							if (company == null)
							{
								company = companyDao.findByPrimaryKey(projectMap.getCompId());
								projectForm.setCompanyId(company.getId());
								projectForm.setCompanyName(company.getCompanyName());
							}
							if (region == null && !projectMap.isRegionIdNull())
							{
								region = regDao.findByPrimaryKey(projectMap.getRegionId());
								projectForm.setRegionId(region.getId());
								projectForm.setRegionName(region.getRegName());
							}	
				// Done for getting CALENDAR_ID and CALENDAR_NAME				
							if (calendar == null && projectMap.getCalendarId()>0)
							{	
						      
							    calendar=calDao.findByPrimaryKey(projectMap.getCalendarId());
							    projectForm.setCalendarId(calendar.getId());
							    projectForm.setCalendarName(calendar.getName());
						
							}
							if (department == null && !projectMap.isDeptIdNull())
							{
								department = divDao.findByPrimaryKey(projectMap.getDeptId());
								projectForm.setDeptId(department.getId());
								projectForm.setDeptName(department.getName());
							}
							// Set Divison
							Divison division = divDao.findByPrimaryKey(projectMap.getDivId());
							if (division != null)
							{
								divisionSet.add(division);
							}

						}
					projectForm.setDivisions(divisionSet);

					// Set All Charge Code of Projects
					String chCodequery = " PO_ID IN(SELECT ID FROM PO_DETAILS WHERE PROJ_ID=" + project.getId() + ")";
					ChargeCode[ ] allChargeCode = chCodeDao.findByDynamicWhere(chCodequery, null);

					if (allChargeCode.length != 0)
						projectForm.setProjChargeCode(allChargeCode);
					// Set Po_NUmber and Details
					
					if(clientMap!=null && clientMap.length>0){
						projectForm.setBenchProject(false);
					}else{
						projectForm.setBenchProject(true);
					}				
					
					//project.setLocations(projectForm.getLocations());

					request.setAttribute("actionForm", projectForm);
					result.setForwardName("success");
				} catch (ProjectDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProjClientMapDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProjLocationsDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProjContInfoDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * catch (ProjectDivMapDaoException e)
				 * {
				 * // TODO Auto-generated catch block
				 * e.printStackTrace();
				 * }
				 */
				catch (DivisonDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RegionsDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CompanyDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProjectMapDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ChargeCodeDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (CalendarDaoException e) 
				{
					e.printStackTrace();
					}
				/*
				 * catch (PoDetailsDaoException e)
				 * {
				 * // TODO Auto-generated catch block
				 * e.printStackTrace();
				 * }
				 */
				break;
			}
			case RECEIVEPROJECTBYEMPLOYEE: {
				Project Projdropdown = (Project) form;
				ProjectDao projectDao = ProjectDaoFactory.create();
				Set<Project> projectList = new HashSet<Project>();
				try{
					int days=183; // 6 months.

/*SELECT 
    *
FROM
    PROJECT P
        LEFT JOIN
    ROLL_ON_PROJ_MAP RP ON RP.PROJ_ID = P.ID
        LEFT JOIN
    ROLL_ON R ON R.ID = RP.ROLL_ON_ID
WHERE
    EMP_ID = 9
        AND (TIMESTAMPDIFF(DAY, ROLL_OFF_DATE, NOW()) < 183
        OR (TIMESTAMPDIFF(DAY, ROLL_OFF_DATE, NOW()) IS NULL
        AND CURRENT = 1))
GROUP BY PROJ_ID;
					*/
					Project[] p = projectDao.findByDynamicSelect("SELECT * FROM PROJECT P LEFT JOIN  ROLL_ON_PROJ_MAP RP ON RP.PROJ_ID = P.ID LEFT JOIN ROLL_ON R ON R.ID = RP.ROLL_ON_ID  WHERE  EMP_ID=? AND (TIMESTAMPDIFF(DAY,ROLL_OFF_DATE,NOW())<? OR (TIMESTAMPDIFF(DAY,ROLL_OFF_DATE,NOW()) IS null AND CURRENT=1)) GROUP BY PROJ_ID", new Object[ ]{Projdropdown.getUserId(),days});
					
					
		//			Project[] p = projectDao.findByDynamicSelect("SELECT * FROM PROJECT WHERE ID IN (SELECT PROJ_ID FROM ROLL_ON_PROJ_MAP WHERE ROLL_ON_ID IN (SELECT ID FROM ROLL_ON WHERE  EMP_ID=? AND (TIMESTAMPDIFF(DAY,ROLL_OFF_DATE,NOW())<? OR (TIMESTAMPDIFF(DAY,ROLL_OFF_DATE,NOW()) IS null AND CURRENT=1))) GROUP BY PROJ_ID)", new Object[ ]{Projdropdown.getUserId(),days});
					projectList = new HashSet<Project>(Arrays.asList(p));
					Projdropdown.setProjects(projectList);
					request.setAttribute("actionForm", Projdropdown);
				} catch (Exception e){
					e.printStackTrace();
				}
				break;
			}

			case RECEIVEPROJECTBYCOMPANY:
			{
				Project projectForm = (Project) form;
				try{				
					Set< Project > projectSet = new HashSet< Project >();
					projectSet = receiveProjectsByCompany(projectForm.getCompanyId());
					projectForm.setProjects(projectSet);
					request.setAttribute("actionForm", projectForm);
					result.setForwardName("success");
				}catch(Exception ex){
					ex.printStackTrace();
				}				
				break;
			}
			
			
			case RECEIVEPROJECTBYREGION:
			{
				Project projectForm = (Project) form;
				try{
					Set< Project > projectSet = new HashSet< Project >();
					projectSet = receiveProjectsByRegion(projectForm.getCompanyId(),projectForm.getRegionId());
					projectForm.setProjects(projectSet);
					request.setAttribute("actionForm", projectForm);
					result.setForwardName("success");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				break;
			}
			case RECEIVEPROJECTBYDEPT:
			{
				Project projectForm = (Project) form;
				ProjectDao projectDao = ProjectDaoFactory.create();
				String sql = "SELECT * FROM PROJECT WHERE ID IN(SELECT DISTINCT PROJ_ID FROM PROJECT_MAP WHERE COMP_ID=" + projectForm.getCompanyId() + " AND (REGION_ID=" + projectForm.getRegionId() + " OR REGION_ID IS NULL) AND (DEPT_ID=" + projectForm.getDeptId() + " OR DEPT_ID IS NULL) AND DIV_ID IS NULL)";
				Set< Project > projectSet = new HashSet< Project >();
				try
				{
					Project[ ] projects = projectDao.findByDynamicSelect(sql, null);
					if (projects != null)
					{
						for (Project project : projects)
						{
							Project tempProject = new Project();
							tempProject.setId(project.getId());
							tempProject.setName(project.getName());
							projectSet.add(tempProject);
						}
						projectForm.setProjects(projectSet);
					}
					request.setAttribute("actionForm", projectForm);
					result.setForwardName("success");
				} catch (ProjectDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case RECEIVEPROJECTBYDIVISION:
			{
				Project projectForm = (Project) form;
				ProjectDao projectDao = ProjectDaoFactory.create();
				String sql = "SELECT * FROM PROJECT WHERE ID IN(SELECT DISTINCT PROJ_ID FROM PROJECT_MAP WHERE COMP_ID=" + projectForm.getCompanyId() + " AND (REGION_ID=" + projectForm.getRegionId() + " OR REGION_ID IS NULL) AND (DEPT_ID=" + projectForm.getDeptId() + " OR DEPT_ID IS NULL) AND (DIV_ID=" + projectForm.getDivisonId() + " OR DIV_ID IS NULL))";
				Set< Project > projectSet = new HashSet< Project >();
				try
				{
					Project[ ] projects = projectDao.findByDynamicSelect(sql, null);
					if (projects != null)
					{
						for (Project project : projects)
						{
							Project tempProject = new Project();
							tempProject.setId(project.getId());
							tempProject.setName(project.getName());
							projectSet.add(tempProject);
						}
						projectForm.setProjects(projectSet);
					}
					request.setAttribute("actionForm", projectForm);
					result.setForwardName("success");
				} catch (ProjectDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			}
			case RECEIVEPROJFORPODETAILS:
			{
			/*
			 * fetch all the projects having charge-code
			 * for each project, get all the associated charge-codes
			 */
			Login login = Login.getLogin(request);
			Project projectForm = (Project) form;
			ProjectDao projectDao = ProjectDaoFactory.create();
			PoDetailsDao poDetailsDao = PoDetailsDaoFactory.create();
			Set<Project> projectSet = new HashSet<Project>();
			ProjClientMapDao projClientMapDao = ProjClientMapDaoFactory.create();
			ClientDao clientDao = ClientDaoFactory.create();
			PoEmpMapDao poEmpMapDao = PoEmpMapDaoFactory.create();
			ProjClientMap[] clientMap = null;
			ChargeCodeDao chCodeDao = ChargeCodeDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();

			try{
				Project[] projectsHavingChargeCode = projectDao.findByDynamicSelect("SELECT * FROM PROJECT WHERE ID IN (SELECT DISTINCT PROJ_ID FROM PO_DETAILS)", null);
				if(projectsHavingChargeCode != null){
					for(Project eachProject : projectsHavingChargeCode){
						Project tempProject = new Project();
						PoDetails[] fetchedPoDetails = poDetailsDao.findByDynamicWhere(" PROJ_ID=?" , new Object[]{eachProject.getId()});
						if(fetchedPoDetails != null){
							Set<PoDetails> poSets = new HashSet<PoDetails>();
							for(PoDetails eachPoDetail : fetchedPoDetails){
								//PoEmpMap[] fetchedPoEmpMap = poEmpMapDao.findWherePoIdEquals(eachPoDetail.getId());
								PoEmpMap[] fetchedPoEmpMap = poEmpMapDao.findByDynamicSelect("SELECT * FROM PO_EMP_MAP WHERE PO_ID=? AND CURRENT=1" , new Object[]{eachPoDetail.getId()});
								PoDetails tempPoDetail = new PoDetails();
								ChargeCode[] chargecode = chCodeDao.findWherePoIdEquals(eachPoDetail.getId());

								if(chargecode != null){
									for(ChargeCode chargeCode2 : chargecode){
										tempPoDetail.setChCode(chargeCode2.getChCode());
										tempPoDetail.setChCodeName(chargeCode2.getChCodeName());

										String authUser = chargeCode2.getAuthUsers();
										Users user = usersDao.findByPrimaryKey(Integer.parseInt(authUser));
										ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());

										tempPoDetail.setOwnerName(profileInfo.getFirstName()+ " "+profileInfo.getLastName());
										tempPoDetail.setAuthorisedUsers(authUser);

										if(Integer.parseInt(authUser) == login.getUserId()) 
											tempPoDetail.setEditEnable(true);
										 else 
											tempPoDetail.setEditEnable(false);
									}
								}

								tempPoDetail.setId(eachPoDetail.getId());
								// tempPoDetail.setPoStDate(PortalUtility.fromStringToDate(poDetails2.getPoStDate().toString()));
								tempPoDetail.setPoStDate(eachPoDetail.getPoStDate());
								// tempPoDetail.setPoEndDate(PortalUtility.fromStringToDate(poDetails2.getPoEndDate().toString()));
								tempPoDetail.setPoEndDate(eachPoDetail.getPoEndDate());
								tempPoDetail.setPoDuration(eachPoDetail.getPoDuration());
								tempPoDetail.setPaymentTerms(eachPoDetail.getPaymentTerms());
								tempPoDetail.setIsDisable(eachPoDetail.getIsDisable());								
								tempPoDetail.setIsDisable(eachPoDetail.getIsDisable());
								tempPoDetail.setNoOfResource(fetchedPoEmpMap.length);
								if(eachPoDetail.getPoNumber() != null){
									tempPoDetail.setPoNumber(eachPoDetail.getPoNumber());
									tempPoDetail.setPoDate(eachPoDetail.getPoDate());
								}
								poSets.add(tempPoDetail);
							}
							tempProject.setPoDeatilsSet(poSets);
						}
						
						// Find Client For Project
						clientMap = projClientMapDao.findWhereProjIdEquals(eachProject.getId());
						if(clientMap != null){
							Client client = null;
							
							for(ProjClientMap projClientMap : clientMap)
								client = clientDao.findByPrimaryKey(projClientMap.getClientId());
							
							if(client != null){
								tempProject.setClientId(client.getId());
								tempProject.setClientName(client.getName());
							}
						}

						tempProject.setId(eachProject.getId());
						tempProject.setName(eachProject.getName());
						tempProject.setIsEnable(eachProject.getIsEnable());
						projectSet.add(tempProject);
					}
					projectForm.setProjects(projectSet);
					request.setAttribute("actionForm",projectForm);
				}
				result.setForwardName("success");

			}catch (ProjectDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (PoDetailsDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ProjClientMapDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClientDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (PoEmpMapDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NumberFormatException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (UsersDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ProfileInfoDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(ChargeCodeDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			break;
				
				
				
			case RECEIVEALLCOMPANY:
				AccessibilityTopLevelDao ctopDao = AccessibilityTopLevelDaoFactory.create();
				// DropDown dropDown = new DropDown();
				CompanyDao comDao = CompanyDaoFactory.create();
				try
				{
					
					AccessibilityTopLevel[ ] nocomp = ctopDao.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL ATL LEFT JOIN MODULE_PERMISSION MP ON ATL.ACCESSIBILITY_ID=MP.ACCESSIBILITY_ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID LEFT JOIN USERS U ON U.ID=UR.USER_ID  AND MODULE_ID=32 WHERE U.ID=?", new Object[ ]
							{ new Integer(loginDto.getUserId()) });
			//		AccessibilityTopLevel[ ] nocomp = ctopDao.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL ATL LEFT JOIN MODULE_PERMISSION MP ON ATL.ACCESSIBILITY_ID=MP.ACCESSIBILITY_ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID LEFT JOIN USERS U ON U.ID=UR.USER_ID  AND MODULE_ID=32 WHERE U.ID=? GROUP BY COMPANY_ID", new Object[ ]
					
					Object company[] = new Object[nocomp.length];
					Set< Company > com = new HashSet< Company >();
					int i = 0;
					if (nocomp.length > 0)
					{
						for (AccessibilityTopLevel l : nocomp)
						{

							if (!l.getCompanyId().equals("0"))
							{
								Company co = comDao.findByPrimaryKey(Integer.parseInt(l.getCompanyId()));
								if (!com.contains(co))
								{
									com.add(co);
									company[i] = co;
									i++;
								}
							}
						}
						dropDown.setDropDown(company[0] != null && company.length > 0 ? company : comDao.findAll());
					}
					request.setAttribute("actionForm", dropDown);
				} catch (AccessibilityTopLevelDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CompanyDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case RECEIVEREGION:
				Project rForm = (Project) form;
				DropDown dropDown1 = new DropDown();
				RegionsDao regDao = RegionsDaoFactory.create();
				AccessibilityTopLevelDao ctopDao1 = AccessibilityTopLevelDaoFactory.create();
				try
				{
					AccessibilityTopLevel[ ] nocomp = ctopDao1.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL ATL LEFT JOIN MODULE_PERMISSION MP ON ATL.ACCESSIBILITY_ID=MP.ACCESSIBILITY_ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=32 WHERE UR.ID=? GROUP BY COMPANY_ID", new Object[ ]
					{ new Integer(loginDto.getUserId()) });
					if (nocomp.length > 0 && nocomp[0].getCompanyId().equals("0"))
					{
						dropDown1.setDropDown(regDao.findAll());
					}
					else if (rForm.getCompanyId() > 0)
					{
						AccessibilityTopLevel[ ] a = topLevelDao.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL WHERE ACCESSIBILITY_ID IN(SELECT ACCESSIBILITY_ID FROM MODULE_PERMISSION MP JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID WHERE UR.USER_ID=? AND MP.MODULE_ID=32) AND COMPANY_ID=? GROUP BY REGION_ID", new Object[ ]
						{ new Integer(loginDto.getUserId()), rForm.getCompanyId() });
						Object regions[] = new Object[a.length];
						Set< Regions > reg = new HashSet< Regions >();
						int i = 0;
						for (AccessibilityTopLevel l : a)
						{
							int regid = Integer.parseInt(l.getRegionId());
							Regions r = regDao.findByPrimaryKey(regid);
							if (regid > 0 && !reg.contains(r))
							{
								reg.add(r);
								regions[i] = r;
								i++;
							}
							else
							{
								dropDown1.setDropDown(regDao.findAll());
								break;
							}
							dropDown1.setDropDown(regions);
						}

					}
					request.setAttribute("actionForm", dropDown1);
				} catch (AccessibilityTopLevelDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RegionsDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
				
			case RECEIVECALENDAR:	
				Project pForm = (Project) form;
				DropDown dropDown5 = new DropDown();
				CalendarDao calDao = CalendarDaoFactory.create();
				Calendar[] calendar=null;
				int regionId=pForm.getRegionId();
				if(regionId>0){
					
			try {
			    java.util.Calendar now = java.util.Calendar.getInstance();  
				int year=now.get(java.util.Calendar.YEAR);      // This gets the current year.
				calendar=calDao.findByDynamicSelect("SELECT * FROM CALENDAR WHERE REGION= ? AND YEAR= ?",new Object[]{regionId,year});
				dropDown5.setDropDown(calendar);
			    request.setAttribute("actionForm", dropDown5);
				
				} catch (CalendarDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}	
				break;
			case RECEIVEDIVISION:
				Project dForm = (Project) form;
				DropDown dropDown2 = new DropDown();
				DivisonDao divDao = DivisonDaoFactory.create();
				AccessibilityTopLevelDao ctopDao2 = AccessibilityTopLevelDaoFactory.create();
				try
				{
					Division[ ] beans = null;
					if (!dForm.isGetDiv())
					{
						AccessibilityTopLevel[ ] nocomp = ctopDao2.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL ATL LEFT JOIN MODULE_PERMISSION MP ON ATL.ACCESSIBILITY_ID=MP.ACCESSIBILITY_ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID JOIN USERS U ON U.ID=UR.USER_ID AND MODULE_ID=32 WHERE U.ID=? GROUP BY DEPARTMENT_ID", new Object[ ]
						{ new Integer(loginDto.getUserId()) });
						int i = 0;
						if(nocomp!=null && nocomp.length>0){
						for (AccessibilityTopLevel l : nocomp)
						{
							if (!l.getDepartmentId().equals("0"))
							{
								Divison[ ] dept = divDao.findByDynamicSelect("SELECT * FROM DIVISON WHERE ID=?", new Object[ ]
								{ new Integer(l.getDepartmentId()) });
								beans = new Division[dept.length];
								if (dept.length > 0)
								{
									Division dbeans = new Division();
									dbeans.setDivisionId(dept[0].getId());
									dbeans.setDivisionName(dept[0].getName());
									beans[i] = dbeans;
									i++;
								}
							}
							else
							{
								Divison[ ] dept = divDao.findByDynamicSelect("SELECT * FROM DIVISON WHERE REGION_ID=? AND PARENT_ID=0", new Object[ ]
								{ new Integer(dForm.getRegionId()) });
								beans = new Division[dept.length];
								if (dept.length > 0)
								{
									int k = 0;
									for (Divison d : dept)
									{
										Division dbeans = new Division();
										dbeans.setDivisionId(d.getId());
										dbeans.setDivisionName(d.getName());
										beans[k] = dbeans;
										k++;
									}
									break;
								}
							}

						}
						}
						Set <String> s=new HashSet<String>();
						dropDown2.setDropDown(beans);
					}
					else if (dForm.isGetDiv())
					{

						AccessibilityTopLevel[ ] nocomp = ctopDao2.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL ATL LEFT JOIN MODULE_PERMISSION MP ON ATL.ACCESSIBILITY_ID=MP.ACCESSIBILITY_ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID JOIN USERS U ON U.ID=UR.USER_ID  AND MODULE_ID=32 WHERE U.ID=? AND ATL.DEPARTMENT_ID=?", new Object[ ]
						{ new Integer(loginDto.getUserId()), dForm.getDeptId() });
						int i = 0;
						// no comp always be one record
						if (nocomp.length > 0)
						{
							for (AccessibilityTopLevel l : nocomp)
							{
								Division[ ] bean = new Division[l.getDivisionId().split(",").length];
								for (String s : l.getDivisionId().split(","))
								{
									if (!s.equals("0"))
									{
										Divison[ ] dept = divDao.findByDynamicSelect("SELECT * FROM DIVISON WHERE ID=?", new Object[ ]
										{ new Integer(s) });
										if (dept.length > 0)
										{
											Division dbeans = new Division();
											dbeans.setDivisionId(dept[0].getId());
											dbeans.setDivisionName(dept[0].getName());
											bean[i] = dbeans;
											i++;
										}
									}
									else
									{
										Divison[ ] dept = divDao.findByDynamicSelect("SELECT * FROM DIVISON WHERE PARENT_ID=?", new Object[ ]
										{ new Integer(l.getDepartmentId()) });
										bean = new Division[dept.length];
										for (Divison divn : dept)
										{
											Division dbeans = new Division();
											dbeans.setDivisionId(divn.getId());
											dbeans.setDivisionName(divn.getName());
											bean[i] = dbeans;
											i++;
										}
									}
								}

								dropDown2.setDropDown(bean);
							}
						}
						else
						{
							AccessibilityTopLevel[ ] nocomp1 = ctopDao2.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL ATL LEFT JOIN MODULE_PERMISSION MP ON ATL.ACCESSIBILITY_ID=MP.ACCESSIBILITY_ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=32 WHERE UR.ID=? GROUP BY DEPARTMENT_ID", new Object[ ]
							{ new Integer(loginDto.getUserId()) });
							if (nocomp1.length > 0)
							{
								Divison[ ] dept = divDao.findByDynamicSelect("SELECT * FROM DIVISON WHERE PARENT_ID=?", new Object[ ]
								{ new Integer(dForm.getDeptId()) });
								Division[ ] bean = new Division[dept.length];
								bean = new Division[dept.length];
								for (Divison divn : dept)
								{
									Division dbeans = new Division();
									dbeans.setDivisionId(divn.getId());
									dbeans.setDivisionName(divn.getName());
									bean[i] = dbeans;
									i++;
								}
								dropDown2.setDropDown(bean);
							}
						}
					}

					request.setAttribute("actionForm", dropDown2);
				} catch (AccessibilityTopLevelDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DivisonDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

				
				
			
			/*
			 * This returns a list of employees who come under the "logged-in" user's region
			 * The logged-in user will select an employee as OWNER for the project being created
			 */
			case RECEIVEPROJECTOWNER:
				// get logged_in user's REGION info
				try
				{
					RegionsDao regionsDao = RegionsDaoFactory.create();
					Regions[ ] loggedInRegion = regionsDao.findByDynamicSelect("SELECT * FROM REGIONS R WHERE R.ID=(SELECT D.REGION_ID FROM DIVISON D WHERE D.ID=(SELECT L.DIVISION_ID FROM LEVELS L WHERE L.ID=(SELECT U.LEVEL_ID FROM USERS U WHERE U.ID=?)))", new Object[ ]
					{ new Integer(loginDto.getUserId()) });
					if (loggedInRegion != null && loggedInRegion.length > 0)
					{
						logger.debug("RECEIVEPROJECTOWNER : REGION_NAME--->USER_ID       :       " + loggedInRegion[0].getRegName() + "--->" + loginDto.getUserId());
						// get all ACTIVE employees under this REGION
						UsersDao usersDao = UsersDaoFactory.create();
						try
						{
							Users[ ] fetchedUsers = usersDao.findByDynamicSelect("SELECT * FROM USERS U WHERE U.LEVEL_ID IN (SELECT L.ID FROM LEVELS L WHERE L.DIVISION_ID IN(SELECT D.ID FROM DIVISON D WHERE D.REGION_ID=?)) AND U.STATUS=0", new Object[ ]
							{ loggedInRegion[0].getId() });
							if (fetchedUsers != null && fetchedUsers.length > 0)
							{
								logger.debug("RECEIVEPROJECTOWNER : FOUND ACTIVE USERS UNDER THE REGION --->" + loggedInRegion[0].getRegName());
								ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
								ArrayList< UserDetails > detailsList = new ArrayList< UserDetails >(fetchedUsers.length);
								for (Users eachUser : fetchedUsers)
								{
									ProfileInfo userInfo = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(eachUser.getProfileId()));
									if (userInfo != null)
										detailsList.add(new UserDetails(eachUser.getId(),userInfo.getFirstName(), userInfo.getFirstName() + " " + userInfo.getLastName()));
									else
										logger.error("RECEIVEPROJECTOWNER : profileInfoDao.findByPrimaryKey(...) RETURNED NULL FOR PROFILE_ID=" + eachUser.getProfileId());
								}
								dropDown.setDropDown(detailsList.toArray());
								request.setAttribute("actionForm", dropDown);
							}
							else
							{
								logger.error("RECEIVEPROJECTOWNER RETURNED 0 ROWS FROM USERS ;;;;;;;  logged_in user was also not fetched");
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("project.owner.failed"));
								result.setForwardName("success");
							}
						} catch (UsersDaoException udex)
						{
							logger.error("RECEIVEPROJECTOWNER : EXCEPTION WAS THROWN WHEN TRYING TO FETCH USERS FROM REGION_ID=" + loggedInRegion[0].getId(), udex);
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("project.owner.failed"));
							result.setForwardName("success");
						} catch (ProfileInfoDaoException pdex)
						{
							logger.error("RECEIVEPROJECTOWNER : EXCEPTION WAS THROWN WHEN TRYING TO FETCH A ROW FROM PROFILE_INFO TO GET THE EMPLOYEE NAME", pdex);
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("project.owner.failed"));
							result.setForwardName("success");
						}

					}
					else
					{
						logger.error("RECEIVEPROJECTOWNER RETURNED 0 ROWS FROM REGIONS");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("project.owner.failed"));
						result.setForwardName("success");
					}
				} catch (RegionsDaoException ddex)
				{
					logger.error("EXCEPTION WAS THROWN WHEN TRYING TO FETCH A ROW FROM REGIONS FOR USER_ID=" + loginDto.getUserId(), ddex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("project.owner.failed"));
					result.setForwardName("success");
				}

				break;
				
			case RECEIVEALLACTIVECHARGECODE:
				
				
				
				 
				Login login = Login.getLogin(request);
				//ActiveChargeCode[] activeChargeCodeData=null;
				ActiveChargeCode report=new ActiveChargeCode();
				//ActiveChargeCode activeChargeCode=(ActiveChargeCode) form;
				ActiveChargeCodeDao activeChargeCodeDao=ActiveChargeCodeDaoFactory.create(); 
				//Set<ActiveChargeCode> activeChargeCodeSet= new HashSet<ActiveChargeCode>();
		

				try{
					
					ActiveChargeCode[] activeChargeCodeData=activeChargeCodeDao.findByAll();
					//Create 4 List 
					
					if (activeChargeCodeData != null && activeChargeCodeData.length > 0){
					List<Map<String, Object>> billable = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> nonBillable = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> support = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> maintenance = new ArrayList<Map<String, Object>>();
					//List<Map<String, Object>> noProjectType = new ArrayList<Map<String, Object>>();
					int billableCount=0;
					int nonBillableCount=0;
					int supportCount=0;
					int maintenanceCount=0;
					int totalCount=0;
					//int noProjectTypeCount=0;
					int i=0;
					for(ActiveChargeCode data : activeChargeCodeData){
						
						
					
						if(data.getProjectType()!=null){
				
						if("BILLABLE".equalsIgnoreCase(data.getProjectType())){
							billableCount++;
							report.setProjType(1);
						}
						else if("NON_BILLABLE".equalsIgnoreCase(data.getProjectType())){
							nonBillableCount++;
							report.setProjType(2);
						}
						else{
							if("SUPPORT".equals(data.getProjectType())){
								supportCount++;
								report.setProjType(3);
							}else if("MAINTENANCE".equals(data.getProjectType())){
								maintenanceCount++;
								report.setProjType(4);
							}
						}
						/*if("MAINTENANCE".equals(data.getProjectType())){
							maintenanceCount++;
							report.setProjType(4);
						}*/
						
					/*	String type=data.getProjectType();
						if(null==type){
							noProjectTypeCount++;
							report.setProjType(5);
						}*/
						
					switch (report.getProjType()) {
					case BILLABLE:
						billable.add(data.toMap(1));
						break;
					case NON_BILLABLE:
						nonBillable.add(data.toMap(1));
						break;
					case SUPPORT:
						support.add(data.toMap(1));
						break;
					case MAINTENANCE:
						maintenance.add(data.toMap(1));
					/*case NO_PROJECT_TYPE:
						noProjectType.add(data.toMap(1));*/
					
					default:
						break;
				}
						}
			
					totalCount=billableCount+nonBillableCount+supportCount+maintenanceCount;
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("billableCount", billableCount);
					map.put("nonBillableCount", nonBillableCount);
					map.put("supportCount", supportCount);
					map.put("maintenanceCount", maintenanceCount);
					//map.put("noProjectTypeCount", noProjectTypeCount);
					map.put("billable", billable);
					map.put("nonBillable", nonBillable);
					map.put("support", support);
					map.put("maintenance", maintenance);
					map.put("totalCount", totalCount);
					//map.put("noProjectType", noProjectType);
				    
					request.setAttribute("actionForm", map);
					result.setForwardName("success");
					}
					}
				}catch(ActiveChargeCodeDaoException e){
					e.printStackTrace();
				}
					
				break;
				
			case RECEIVEPODETAILSFORCHARGECODE:{
			//case RECEIVEALLACTIVECHARGECODE:{
			
				try {
					    /*  Project poDet = (Project) form;
				            PoDetailDao podetDao=PoDetailDaoFactory.create();
				            PoDetail[] PoDetaila=podetDao.findByPoIdAndEmpId(poDet.getPoId(),poDet.getEmpId());
						    request.setAttribute("actionForm", PoDetaila);
							result.setForwardName("success");*/
					Map<String, Object> mapPO = new HashMap<String, Object>();
							 PoDetailDao podetDao=PoDetailDaoFactory.create();
							 Project poDet = (Project) form;
					            PoDetail[] PoDetaila=podetDao.findByPoIdAndEmpId(poDet.getPoId(),poDet.getPoEmapId());
					            for(PoDetail data:PoDetaila){
						        	   

						        	   mapPO.put("podate", data.getEmpPoDate());
						        	   mapPO.put("ponumber", data.getEmpPoNumber());
						        	   mapPO.put("stdate", data.getEmpPoStDate());
						        	   mapPO.put("enddate", data.getEmpPoEndDate());
						        	   mapPO.put("podauration", data.getEmpPoDuration());
						        	   mapPO.put("rate", data.getRate());
						       		
						           }
							    request.setAttribute("actionForm", mapPO);
								result.setForwardName("success");
				
				} catch (PoDetailDaoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
			break;
			}
			default:
			    break;
			   }
		 return result;
		}
	
	
	

		

	
	
	private int getCompanyIdForUser(int regionId) throws RegionsDaoException {
		Regions region = RegionsDaoFactory.create().findWhereIdEquals(regionId)[0];
		return region.getCompanyId();
	}

	private Set<Project> receiveProjectsByRegion(int companyId, int regionId) throws Exception {
		ProjectDao projectDao = ProjectDaoFactory.create();
		String sql = "SELECT * FROM PROJECT WHERE ID IN(SELECT DISTINCT PROJ_ID FROM PROJECT_MAP WHERE COMP_ID=" + companyId + " AND (REGION_ID=" + regionId + " OR REGION_ID IS NULL) AND DEPT_ID IS NULL AND DIV_ID IS NULL)";
		Set< Project > projectSet = new HashSet< Project >();
		Project[ ] projects = projectDao.findByDynamicSelect(sql, null);
		if (projects != null){
			for (Project project : projects){
				Project tempProject = new Project();
				tempProject.setId(project.getId());
				tempProject.setName(project.getName());
				tempProject.setBenchProject(true);
				projectSet.add(tempProject);
			}				
		}			
		return projectSet;
	}
	

	private Set<Project> receiveProjectsByCompany(int companyId) throws Exception{
		ProjectDao projectDao = ProjectDaoFactory.create();
		String sql = "SELECT * FROM PROJECT WHERE ID IN (SELECT DISTINCT PROJ_ID FROM PROJECT_MAP WHERE COMP_ID=" + companyId + " AND REGION_ID IS NULL AND DEPT_ID IS NULL AND DIV_ID IS NULL)";
		Set< Project > projectSet = new HashSet< Project >();
			Project[ ] projects = projectDao.findByDynamicSelect(sql, null);
			for (Project project : projects){
				Project tempProject = new Project();
				tempProject.setId(project.getId());
				tempProject.setName(project.getName());
				tempProject.setBenchProject(true);
				projectSet.add(tempProject);
			}	
		return projectSet;
	}

	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request){
		ActionResult result = new ActionResult();
		Project project = (Project) form;
		ProjectDao projectDao = ProjectDaoFactory.create();
		ProjectPk projPk = null;

		ProjClientMapDao proClientDao = ProjClientMapDaoFactory.create();
		ProjLocationsDao locationDao = ProjLocationsDaoFactory.create();
		ProjContInfoDao contInfoDao = ProjContInfoDaoFactory.create();
		ProjHistoryDao historyDao = ProjHistoryDaoFactory.create();

		ProjectMapDao proMapDao = ProjectMapDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		UserRolesDao userRolesDao = UserRolesDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String region = "None";

		// Added
		RegionsDao regionsDao = RegionsDaoFactory.create();
		EmpSerReqMapDao empReqMapDao = EmpSerReqMapDaoFactory.create();
		EmpSerReqMapPk reqMapPK = new EmpSerReqMapPk();
		Project[ ] projects = null;
		try{
			Login login = Login.getLogin(request);

			projects = projectDao.findWhereNameEquals(project.getName());
			if(projects.length != 0){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.project.name"));
				result.setForwardName("success");
				return result;
			}
			else{
				SimpleDateFormat uniqueIdformat = new SimpleDateFormat("mmMMdd");
				String uniqueID = login.getUserId() + uniqueIdformat.format(new Date());
				EmpSerReqMap serReqMap = new EmpSerReqMap();
				serReqMap.setSubDate(new Date());
				serReqMap.setReqId("IN-PR-" + uniqueID);
				serReqMap.setReqTypeId(10);
				int regionid = regionsDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[ ]
				{ new Integer(login.getUserId()) })[0].getId();
				serReqMap.setRegionId(regionid);
				serReqMap.setRequestorId(login.getUserId());
				reqMapPK = empReqMapDao.insert(serReqMap);
				
				// insert into project
				project.setEsrqmId(reqMapPK.getId());			
				project.setLastModifiedBy(login.getUserId());
				project.setCreatorId(login.getUserId());
				projPk = projectDao.insert(project);
				
				logger.debug("Project saved is :::" + projPk);
				
				if(projPk != null && form.getUserId() != null){
					ProjHistory proHistory = new ProjHistory();
					proHistory.setLastModifiedBy(form.getUserId());
					proHistory.setModifiedTime(new Date());
					proHistory.setProjId(projPk.getId());
					historyDao.insert(proHistory);
				}

				// Insert Records For Project Map
				if(projPk != null){
					if(project.getDivIds() != null){
						for (int divId : project.getDivIds()){
							// Attach Project Map Info
							ProjectMap proMap = new ProjectMap();
							proMap.setProjId(projPk.getId());
							proMap.setCompId(project.getCompanyId());
							proMap.setRegionId(project.getRegionId());
							proMap.setDeptId(project.getDeptId());
							proMap.setDivId(divId);
			// added setting value for newly added column CALENDAR_ID
							proMap.setCalendarId(project.getCalendarId());
							if(proMap.getCalendarId()>0){
								proMap.setCalendarIdNull(false);	
							}
							
							proMapDao.insert(proMap);
						}
					}
					else{
						ProjectMap proMap = new ProjectMap();
						proMap.setProjId(projPk.getId());
						proMap.setCompId(project.getCompanyId());
						if (project.getRegionId() > 0)
							proMap.setRegionId(project.getRegionId());
						if (project.getDeptId() > 0)
							proMap.setDeptId(project.getDeptId());
				
				// added setting value for newly added column CALENDAR_ID
						if (project.getCalendarId()>0){
						proMap.setCalendarId(project.getCalendarId());
						proMap.setCalendarIdNull(false);
						}
					proMapDao.insert(proMap);
					}

				}
				// Attach Clients to the Project
				if(project.getClientIds() != null){
					for(int clientId : project.getClientIds()){
						ProjClientMap proClient = new ProjClientMap();
						proClient.setClientId(clientId);
						proClient.setProjId(projPk.getId());
						proClientDao.insert(proClient);
					}
				}

				// Attach Location Info to Project
				if(project.getLocInfo() != null){
					for(String location : project.getLocInfo()){
						ProjLocations locations = DtoToBeanConverter.stringToProjLocConverter(location);
						locations.setProjId(projPk.getId());
						locationDao.insert(locations);
					}
				}

				// Attach Contact Info to Project
				if(project.getContInfo() != null){
					for(String contact : project.getContInfo()){
						ProjContInfo contacts = DtoToBeanConverter.stringToProjInfoConverter(contact);
						contacts.setProjId(projPk.getId());
						contInfoDao.insert(contacts);
					}
				}

				if(project.getRegionId() > 0){
					region = regionsDao.findByPrimaryKey(project.getRegionId()).getRegName();
					
				}
				
				String createdBy = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(login.getUserId()).getProfileId()).getFirstName();
				
				String mailSubject = "Diksha Lynx: New Project ["+project.getName()+"] created by ["+createdBy+"]";
				// send Mail Notification
				UserRoles userRolesDto = userRolesDao.findWhereUserIdEquals(login.getUserId());
				ModulePermission modulePermisssionData = modulePermissionDao.findByDynamicSelect("SELECT * FROM MODULE_PERMISSION WHERE ROLE_ID=? AND MODULE_ID=32", new Object[ ]
				{ new Integer(userRolesDto.getRoleId()) })[0];
				ProcessChain processChainDto = processChainDao.findWhereIdEquals(modulePermisssionData.getProcChainId())[0];
				ProcessEvaluator pEvaluator = new ProcessEvaluator();
				Integer[ ] userIds = pEvaluator.notifiers(processChainDto.getNotification(), login.getUserId());
				if (userIds.length > 0)
				{
					for (int id : userIds)
					{
						Users usersDto = usersDao.findByPrimaryKey(id);
						ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(usersDto.getProfileId());
						sendMailDetails(mailSubject, profileInfo.getOfficalEmailId(), profileInfo.getFirstName(), region, MailSettings.PROJECT_DETAILS_ADDED, projPk.getId(), project.getName(), project.toString(),createdBy);
					}
				}
				result.setForwardName("success");
			}
		} catch (ProjectDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Project project = (Project) form;
		ProjectDao projectDao = ProjectDaoFactory.create();
		// ProjectDivMapDao divMapDao = ProjectDivMapDaoFactory.create();
		ProjClientMapDao proClientDao = ProjClientMapDaoFactory.create();
		ProjLocationsDao locationDao = ProjLocationsDaoFactory.create();
		ProjContInfoDao contInfoDao = ProjContInfoDaoFactory.create();
		ProjectMapDao proMapDao = ProjectMapDaoFactory.create();
		boolean isDone = false;
		String region = "None";
		ProjHistoryDao historyDao = ProjHistoryDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		UserRolesDao userRolesDao = UserRolesDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		// Project[ ] projects = null;

		Login loginDto = Login.getLogin(request);
		
		try
		{

			if (project.isNameChanged()){
				try{
					String WHERE_CLAUSE = " WHERE P.NAME='" + project.getName() + "'";
					Project[ ] existingProjects = projectDao.findByDynamicSelect("SELECT * FROM PROJECT P " + WHERE_CLAUSE + " AND P.ID != ? ", new Object[ ]
					{ project.getId() });
					if(existingProjects != null && existingProjects.length > 0){
						logger.error("PROJECT UPDATE ERROR : PROJECT_NAME--->" + project.getName() + " ALREADY EXISTS");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.project.name"));
						result.setForwardName("success");
						return result;
					}
					logger.debug("PROJECT UPDATE : PROJECT NAME--->" + project.getName() + " IS UNIQUE");
				}catch(ProjectDaoException pdex){
					logger.error("EXCEPTION WAS THROWN WHEN TRYING TO CHECK IF THE PROJECT_NAME ALREADY EXISTED. PROJECT BEING MODIFIED : " + project.getName(), pdex);
				}
			}

			// projects = projectDao.findWhereNameEquals(project.getName());
			// if (projects.length != 0)
			// {
			// result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new
			// ActionMessage("errors.duplicate.project.name"));
			// result.setForwardName("success");
			// return result;
			// }

			// else{
			logger.debug("PROJECT_UPDATE : BEGIN ");
			ProjectPk projPk = project.createPk();
			Project projectBeingUpdated = projectDao.findByPrimaryKey(projPk);
			project.setId(projPk.getId());
			project.setMessageBody(projectBeingUpdated.getMessageBody());
			project.setEsrqmId(projectBeingUpdated.getEsrqmId());
			project.setCreateDate(projectBeingUpdated.getCreateDate());
			
			/*
			 * need to update the LAST_MODIFIED_BY(userId) and LAST_MODIFIED_ON(timestamp)
			 */
			project.setLastModifiedBy(loginDto.getUserId());
			project.setCreatorId(projectBeingUpdated.getCreatorId());
			projectDao.update(projPk, project);
			
			// Insert Records For Project and Divisions Map
			isDone = projectDao.deleteAllByProject(project.getId());
			if (isDone)
			{

				if (project.getDivIds() != null)
				{
					for (int divId : project.getDivIds())
					{
						// Attach Project Map Info

						ProjectMap proMap = new ProjectMap();
						proMap.setProjId(projPk.getId());
						proMap.setCompId(project.getCompanyId());
						proMap.setRegionId(project.getRegionId());
						proMap.setDeptId(project.getDeptId());
						proMap.setDivId(divId);
						
						proMap.setCalendarId(project.getCalendarId());
						if(proMap.getCalendarId()>0) proMap.setCalendarIdNull(false);
						proMapDao.insert(proMap);

					}
				}
				else
				{
					ProjectMap proMap = new ProjectMap();
					proMap.setProjId(projPk.getId());
					proMap.setCompId(project.getCompanyId());
					if (project.getRegionId() > 0)
						proMap.setRegionId(project.getRegionId());

					if (project.getDeptId() > 0)
						proMap.setDeptId(project.getDeptId());
					if (project.getCalendarId()>0){
						proMap.setCalendarId(project.getCalendarId());
						proMap.setCalendarIdNull(false);
					}
					proMapDao.insert(proMap);
				}

			}
			// Attach Clients to the Project
			if (project.getClientIds() != null)
			{
				for (int clientId : project.getClientIds())
				{
					ProjClientMap proClient = new ProjClientMap();
					proClient.setClientId(clientId);
					proClient.setProjId(projPk.getId());
					proClientDao.insert(proClient);
				}
			}

			// Attach Contact Info to Project
			if (project.getContInfo() != null)
			{
				for (String contact : project.getContInfo())
				{
					ProjContInfo contacts = DtoToBeanConverter.stringToProjInfoConverter(contact);
					contacts.setProjId(projPk.getId());
					contInfoDao.insert(contacts);
				}

			}

			// Attach Location Info to Project
			if (project.getLocInfo() != null)
			{
				for (String location : project.getLocInfo())
				{
					ProjLocations locations = DtoToBeanConverter.stringToProjLocConverter(location);
					locations.setProjId(projPk.getId());
					locationDao.insert(locations);
				}
			}

			if (form.getUserId() != null)
			{

				ProjHistory history = new ProjHistory();
				history.setLastModifiedBy(form.getUserId());
				history.setProjId(project.getId());
				history.setModifiedTime(new Date());
				historyDao.insert(history);
			}

			logger.debug("Project updated is :::" + projPk);

			region = project.getRegionId() > 0 ? RegionsDaoFactory.create().findByPrimaryKey(project.getRegionId()).getRegName() : region;
			// send Mail Notification
			UserRoles userRolesDto = userRolesDao.findWhereUserIdEquals(form.getUserId());
			ModulePermission modulePermisssionData = modulePermissionDao.findByDynamicSelect("SELECT * FROM MODULE_PERMISSION WHERE ROLE_ID=? AND MODULE_ID=32", new Object[ ]
			{ new Integer(userRolesDto.getRoleId()) })[0];
			ProcessChain processChainDto = processChainDao.findWhereIdEquals(modulePermisssionData.getProcChainId())[0];
			/*
			 * ProcessChainBean processChainBean = DtoToBeanConverter
			 * .DtoToBeanConverterToParseProcessChain(processChainDto.getNotification());
			 */
			ProcessEvaluator pEvaluator = new ProcessEvaluator();
			Integer[ ] userIds = pEvaluator.notifiers(processChainDto.getNotification(), form.getUserId());
			
			String updatedBy = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(loginDto.getUserId()).getProfileId()).getFirstName();
			
			String mailSubject = "Diksha Lynx: Project ["+project.getName()+"] updated by ["+updatedBy+"]" ;
			if (userIds.length > 0)
			{
				for (int id : userIds)
				{
					Users usersDto = usersDao.findByPrimaryKey(id);
					ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(usersDto.getProfileId());
					sendMailDetails(mailSubject, profileInfo.getOfficalEmailId(), profileInfo.getFirstName(), region, MailSettings.PROJECT_DETAILS_UPDATED, projPk.getId(), project.getName(), project.toString(),updatedBy);
				}
			}

			logger.debug("PROJECT_UPDATE : END");
			result.setForwardName("success");
			// }

		} catch (ProjectDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (ProjClientMapDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProjContInfoDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProjLocationsDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProjectMapDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProjHistoryDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegionsDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserRolesDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModulePermissionDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsersDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessChainDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProfileInfoDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public PortalMail sendMailDetails(String mailSubject, String mailId, String empFname, String region, String templateName, int projectId, String projectName, String fieldName, String disabledOrEnabledBy)
	{
		try
		{

			PortalMail portalMail = new PortalMail();
			portalMail.setRecipientMailId(mailId);
			portalMail.setMailSubject(mailSubject);
			portalMail.setEmpFname(empFname);
			portalMail.setRegion(region);
			portalMail.setTemplateName(templateName);
			portalMail.setFieldName(fieldName);
			portalMail.setProjectName(projectName);
			portalMail.setProjectId(projectId);
			portalMail.setOnDate(PortalUtility.formatDateToddmmyy(new Date()));
			portalMail.setSenderName(disabledOrEnabledBy);//createdBy || updatedBy
			
			if (mailId != null && mailId.contains("@"))
			{
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}
			return portalMail;
		} catch (AddressException ae)
		{
			ActionResult result = new ActionResult();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest", "error.portal.mail"));
			result.setForwardName("success");
			ae.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}