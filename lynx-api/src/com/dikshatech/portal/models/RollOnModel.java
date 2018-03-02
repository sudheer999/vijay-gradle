package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.ExperienceBean;
import com.dikshatech.beans.RollOnBean;
import com.dikshatech.beans.RollOnOffBean;
import com.dikshatech.beans.Salary;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CandidatePerdiemDetailsDao;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.ClientDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EducationInfoDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ExperienceInfoDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.PassportInfoDao;
import com.dikshatech.portal.dao.PerdiemMasterDataDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.PoDetailsDao;
import com.dikshatech.portal.dao.PoEmpMapDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjClientMapDao;
import com.dikshatech.portal.dao.ProjContInfoDao;
import com.dikshatech.portal.dao.ProjLocationsDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.ProjectMappingDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RollOnDao;
import com.dikshatech.portal.dao.RollOnMgrMapDao;
import com.dikshatech.portal.dao.RollOnProjMapDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.SalaryInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.Client;
import com.dikshatech.portal.dto.Currency;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DivisonPk;
import com.dikshatech.portal.dto.EducationInfo;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.ExperienceInfo;
import com.dikshatech.portal.dto.FixedPerdiem;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.LevelsPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PassportInfo;
import com.dikshatech.portal.dto.PassportInfoPk;
import com.dikshatech.portal.dto.PerdiemMasterData;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.PersonalInfoPk;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.PoEmpMap;
import com.dikshatech.portal.dto.PoEmpMapPk;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.ProjClientMap;
import com.dikshatech.portal.dto.ProjContInfo;
import com.dikshatech.portal.dto.ProjLocations;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectMap;
import com.dikshatech.portal.dto.ProjectMapping;
import com.dikshatech.portal.dto.ProjectMappingPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.RollOnMgrMap;
import com.dikshatech.portal.dto.RollOnMgrMapPk;
import com.dikshatech.portal.dto.RollOnPk;
import com.dikshatech.portal.dto.RollOnProjMap;
import com.dikshatech.portal.dto.RollOnProjMapPk;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SalaryInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.ChargeCodeDaoException;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.EducationInfoDaoException;
import com.dikshatech.portal.exceptions.ExperienceInfoDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.PassportInfoDaoException;
import com.dikshatech.portal.exceptions.PersonalInfoDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.ProjectMappingDaoException;
import com.dikshatech.portal.exceptions.RollOnDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.CandidatePerdiemDetailsDaoFactory;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.ClientDaoFactory;
import com.dikshatech.portal.factory.CurrencyDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EducationInfoDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ExperienceInfoDaoFactory;
import com.dikshatech.portal.factory.FixedPerdiemDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PassportInfoDaoFactory;
import com.dikshatech.portal.factory.PerdiemMasterDataDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.PoEmpMapDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjClientMapDaoFactory;
import com.dikshatech.portal.factory.ProjContInfoDaoFactory;
import com.dikshatech.portal.factory.ProjLocationsDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.ProjectMapDaoFactory;
import com.dikshatech.portal.factory.ProjectMappingDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.factory.RollOnMgrMapDaoFactory;
import com.dikshatech.portal.factory.RollOnProjMapDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.SalaryInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDownBean;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class RollOnModel extends ActionMethods {

	private static Logger	logger	= LoggerUtil.getLogger(UserModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		switch (ActionMethods.ExecuteTypes.getValue(form.geteType())) {
		}
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		Connection conn = null;
		try{
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVESINGLEROLLON:{
					RollOn rollOnForm = (RollOn) form;
					RollOnDao rOnOffDao = RollOnDaoFactory.create(conn);
					UsersDao userDao = UsersDaoFactory.create(conn);
					ProfileInfoDao profileDao = ProfileInfoDaoFactory.create(conn);
					LevelsDao levelDao = LevelsDaoFactory.create(conn);
					DivisonDao divDao = DivisonDaoFactory.create(conn);
					RollOnProjMapDao rOnProjDao = RollOnProjMapDaoFactory.create(conn);
					ProjClientMapDao clientMapDao = ProjClientMapDaoFactory.create(conn);
					ClientDao clientDao = ClientDaoFactory.create(conn);
					ProjectDao projectDao = ProjectDaoFactory.create(conn);
					RollOnMgrMapDao mgrMapDao = RollOnMgrMapDaoFactory.create(conn);
					ProjLocationsDao proLocDao = ProjLocationsDaoFactory.create(conn);
					ProjContInfoDao contactDao = ProjContInfoDaoFactory.create(conn);
					ChargeCodeDao chCodeDao = ChargeCodeDaoFactory.create(conn);
					SalaryInfoDao salaryInfoDao=SalaryInfoDaoFactory.create();
					CandidatePerdiemDetailsDao perdiemDetailsDao = CandidatePerdiemDetailsDaoFactory.create();
					int projectId = 0;
					int clientId = 0;
					int projLocId = 0;
					Set<RollOnOffBean> rollOnSet = new HashSet<RollOnOffBean>();
					Set<ProjLocations> projLoc = new HashSet<ProjLocations>();
					try{
						RollOn rollOn = rOnOffDao.findByPrimaryKey(rollOnForm.getId());
						Users user = userDao.findByPrimaryKey(rollOn.getEmpId());
						ProfileInfo userprofile = profileDao.findByPrimaryKey(user.getProfileId());
						Levels level = levelDao.findByPrimaryKey(user.getLevelId());
						Divison department = divDao.findByPrimaryKey(level.getDivisionId());
						rollOnForm.setEmpId(user.getEmpId());
						rollOnForm.setEmpName(userprofile.getFirstName());
						
						if (user.getEmpId() > 0){
							SalaryInfo[] salaryInfo=salaryInfoDao.findWhereBasicEquals(user.getEmpId());
							if(salaryInfo!=null){
								for(SalaryInfo info:salaryInfo){
									rollOnForm.setPerdiemOffered(info.getPerdiemOffered());
									//rollOnForm.setPerdiemOffered("100");									
								}
							}
							
						}
						
						
						if (user.getEmpId() > 0)
						{
							   //receive candidate's perdiem details
								CandidatePerdiemDetailsDao perdiemDao = CandidatePerdiemDetailsDaoFactory.create();
								//String perdiemDetails = perdiemDao.getPerdiemDetails(user.getEmpId());
								String perdiemDetails = perdiemDao.getPerdiemDetails(user.getEmpId());
								if(perdiemDetails.length() > 0)
									rollOnForm.setCandidatePerdiemDetails(perdiemDetails);
							}
						
						
						if (department != null){
							rollOnForm.setDeptName(department.getName());
							rollOnForm.setDeptId(department.getId());
						}
						rollOnForm.setDesignation(level.getDesignation());
						// Set Client Details
						RollOnProjMap[] projMap = rOnProjDao.findWhereRollOnIdEquals(rollOn.getId());
						for (RollOnProjMap rollOnProjMap : projMap){
							projectId = rollOnProjMap.getProjId();
							projLocId = rollOnProjMap.getProjLocId();
						}
						ProjClientMap[] proClient = clientMapDao.findWhereProjIdEquals(projectId);
						for (ProjClientMap projClientMap : proClient){
							clientId = projClientMap.getClientId();
						}
						Client client = clientDao.findByPrimaryKey(clientId);
						Project project = projectDao.findByPrimaryKey(projectId);
						ProjLocations location = proLocDao.findByPrimaryKey(projLocId);
						ProjContInfo[] contacts = contactDao.findWhereProjIdEquals(project.getId());
						RollOnMgrMap[] mgrMap = mgrMapDao.findWhereRollOnIdEquals(rollOn.getId());
						RollOnOffBean rollOnBean = new RollOnOffBean();
						if (client != null) rollOnBean.setClientName(client.getName());
						rollOnBean.setAddress(project.getBillAddress());
						rollOnBean.setComments(rollOn.getComments());
						if (rollOn.getStartDate() != null){
							rollOnBean.setRollOnStDate(rollOn.getStartDate().toString());
						}
						if (rollOn.getEndDate() != null){
							rollOnBean.setRollOnEndDate(rollOn.getEndDate().toString());
						}
						if (rollOn.getReportDt() != null){
							rollOnBean.setReportingDate(rollOn.getReportDt().toString());
						}
						if (rollOn.getProjectType() != null){
							rollOnBean.setProjectType(rollOn.getProjectType());
						}
						if (rollOn.getReportTm() != null) rollOnBean.setReportingTime(rollOn.getReportTm().toString());
						for (RollOnMgrMap rollOnMgrMap : mgrMap){
							rollOnBean.setManagerName(rollOnMgrMap.getMgrName());
							rollOnBean.setManagerEmail(rollOnMgrMap.getMgrEmail());
							rollOnBean.setMgrDetails(rollOnMgrMap.getMgrDetail());
							rollOnBean.setOnSiteMgrName(rollOnMgrMap.getOnsiteMgr());
							rollOnBean.setOnSiteMgrDetails(rollOnMgrMap.getOnsiteMgrDetails());
							rollOnBean.setMgrPhoneNo(rollOnMgrMap.getMgrPhoneNo());
						}
						// Set payment Details
						rollOnBean.setCurrency(rollOn.getCurrency());
						//rollOnBean.setPaymentTerms(rollOn.getPaymentTerm());
						rollOnBean.setPerdiem(rollOn.getPerdiem());
						if (rollOn.getEmpId() > 0){
							try{
								PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create(conn);
								PerdiemMasterData fetchedPerdiemDtos[] = perdiemMasterDao.findWhereUserIdEquals(rollOn.getEmpId());
								if (fetchedPerdiemDtos != null && fetchedPerdiemDtos.length > 0){
									rollOnBean.setPerdiem(DesEncrypterDecrypter.getInstance().decrypt(fetchedPerdiemDtos[0].getPerdiem()));
									Currency currencies = CurrencyDaoFactory.create(conn).findByPrimaryKey(Integer.parseInt(fetchedPerdiemDtos[0].getCurrencyType()));
									if (currencies != null) rollOnBean.setCurrency(currencies.getAbbrevation());
								}
							} catch (Exception e){}
						}
						// For ChargeCode Details
						ChargeCode chargeCode = chCodeDao.findByPrimaryKey(rollOn.getChCodeId());
						if (chargeCode != null){
							rollOnForm.setChargeCodeTitle(chargeCode.getChCodeName());
							rollOnBean.setChargeCodeName(chargeCode.getChCodeName());//for rollOn details						
							rollOnForm.setChargeCode(chargeCode.getChCode());
							rollOnForm.setChCodeId(chargeCode.getId());
						}
						/**
						 * set the NOTIFIERS and TRAVEL_REQ_FLAG data
						 */
						rollOnForm.setTravelReqFlag(rollOn.getTravelReqFlag());
						rollOnForm.setNotifiers(rollOn.getNotifiers());
						rollOnSet.add(rollOnBean);
						rollOnForm.setRollOnDetails(rollOnSet);
						// Set Project Details
						rollOnForm.setProjId(project.getId());
						rollOnForm.setProjName(project.getName());
						// rollOnForm.setProjLocs(locations);
						if (location != null){
							ProjLocations projectLocation = DtoToBeanConverter.DtoToBeanConverter(location);
							projLoc.add(projectLocation);
							rollOnForm.setProjLoc(projLoc);
						}
						rollOnForm.setProjContacts(contacts);
						rollOnForm.setUserId(user.getId());
						ProjectMap[] projectMaps = ProjectMapDaoFactory.create(conn).findWhereProjIdEquals(projectId);
						if (projectMaps != null && projectMaps.length > 0 && projectMaps[0].getRegionId() > 0){
							rollOnForm.setRegName(RegionsDaoFactory.create(conn).findByPrimaryKey(projectMaps[0].getRegionId()).getRegName());
						}
						FixedPerdiem fixedPerdiem = FixedPerdiemDaoFactory.create(conn).findByPrimaryKey(user.getId());
						if (fixedPerdiem != null){
							String currType = "INR";
							if (fixedPerdiem.getCurrencyType()!=null  && Integer.parseInt(fixedPerdiem.getCurrencyType()) > 1){
								Currency currency = CurrencyDaoFactory.create(conn).findByPrimaryKey(Integer.parseInt(fixedPerdiem.getCurrencyType()));
								if (currency != null) currType = currency.getAbbrevation();
							}
							rollOnForm.getMap().put("fixedPerdiem", fixedPerdiem.getPerdiem() + " (" + currType + ")");
						}
						//rollOnForm.getMap().put("fixedPerdiem", "200(USD)");
						request.setAttribute("actionForm", rollOnForm);
						result.setForwardName("success");
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				}
				case RECEIVEUSERVIEW:{
					RollOn rollOnForm = (RollOn) form;
					UsersDao userDao = UsersDaoFactory.create(conn);
					ProfileInfoDao profileDao = ProfileInfoDaoFactory.create(conn);
					LevelsDao levelDao = LevelsDaoFactory.create(conn);
					DivisonDao divDao = DivisonDaoFactory.create(conn);
					RegionsDao regDao = RegionsDaoFactory.create(conn);
					ProjClientMapDao clientMapDao = ProjClientMapDaoFactory.create(conn);
					ClientDao clientDao = ClientDaoFactory.create(conn);
					ProjectDao projectDao = ProjectDaoFactory.create(conn);
					ProjLocationsDao proLocDao = ProjLocationsDaoFactory.create(conn);
					ProjContInfoDao contactDao = ProjContInfoDaoFactory.create(conn);
					ProjectMappingDao empProMapDao = ProjectMappingDaoFactory.create(conn);
					ChargeCodeDao chCodeDao = ChargeCodeDaoFactory.create(conn);
					PoDetailsDao poDao = PoDetailsDaoFactory.create(conn);
					int projectId = 0;
					int clientId = 0;
					int poId = 0;
					Set<RollOnOffBean> rollOnSet = new HashSet<RollOnOffBean>();
					try{
						Users user = userDao.findByPrimaryKey(rollOnForm.getUserId());
						ProfileInfo userprofile = profileDao.findByPrimaryKey(user.getProfileId());
						Levels level = levelDao.findByPrimaryKey(user.getLevelId());
						Divison department = divDao.findByPrimaryKey(level.getDivisionId());
						// Set Region Info
						Regions region = regDao.findByPrimaryKey(department.getRegionId());
						rollOnForm.setRegionId(region.getId());
						rollOnForm.setRegName(region.getRegName());
						rollOnForm.setEmpId(user.getEmpId());
						rollOnForm.setEmpName(userprofile.getFirstName());
						if (department != null){
							rollOnForm.setDeptName(department.getName());
							rollOnForm.setDeptId(department.getId());
						}
						rollOnForm.setDesignation(level.getDesignation());
						// Set Client Details
						ProjectMapping[] projMapping = empProMapDao.findWhereUserIdEquals(user.getId());
						if (projMapping != null) for (ProjectMapping projectMapping : projMapping){
							projectId = projectMapping.getProjectId();
						}
						Project project = projectDao.findByPrimaryKey(projectId);
						ProjClientMap[] proClient = clientMapDao.findWhereProjIdEquals(projectId);
						for (ProjClientMap projClientMap : proClient){
							clientId = projClientMap.getClientId();
						}
						Client client = clientDao.findByPrimaryKey(clientId);
						ProjLocations[] locations = proLocDao.findByProject(project.getId());
						ProjContInfo[] contacts = contactDao.findWhereProjIdEquals(project.getId());
						RollOnOffBean rollOnBean = new RollOnOffBean();
						if (client != null){
							rollOnBean.setClientName(client.getName());
							rollOnBean.setClientId(client.getId());
							rollOnBean.setAddress(project.getBillAddress());
						}
						PoDetails[] poDetailsArr = poDao.findWhereProjIdEquals(projectId);
						if (poDetailsArr.length != 0){
							for (PoDetails poDetails : poDetailsArr){
								poId = poDetails.getId();
							}
							ChargeCode[] chargecodeArr = chCodeDao.findWherePoIdEquals(poId);
							for (ChargeCode chargeCode : chargecodeArr){
								rollOnBean.setChargeCodeName(chargeCode.getChCodeName());
								rollOnBean.setAssoChargeCode(chargeCode.getChCode());
							}
						}
						rollOnSet.add(rollOnBean);
						rollOnForm.setRollOnDetails(rollOnSet);
						// Set Project Details
						rollOnForm.setProjId(project.getId());
						rollOnForm.setProjName(project.getName());
						rollOnForm.setProjLocs(locations);
						rollOnForm.setProjContacts(contacts);
						request.setAttribute("actionForm", rollOnForm);
						result.setForwardName("success");
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				}
				case MYROLLONRESOURCE:{
					RollOnDao rollOnDao = RollOnDaoFactory.create(conn);
					String sql = " EMP_ID IN(SELECT ID FROM PROFILE_INFO WHERE REPORTING_MGR=" + form.getUserId() + ") AND CURRENT=1";
					RollOn[] rollOnArr;
					try{
						rollOnArr = rollOnDao.findByDynamicWhere(sql, null);
						DropDownBean dropdown = new DropDownBean();
						dropdown.setDropDown(rollOnArr);
						request.setAttribute("actionForm", dropdown);
					} catch (RollOnDaoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case MYRESOURCES:
					try{
						conn = ResourceManager.getConnection();
						DropDownBean dropdown = new DropDownBean();
						int userId = login.getUserId().intValue();
						// give temperary access to vikram chandra be half of abhishek.
						if (userId == 38){
							userId = 24;
							dropdown.setCount("1");
						}
						UsersDao usersDao = UsersDaoFactory.create(conn);
						ArrayList<RollOnBean> activeAndReportingList = null;
						Users[] activeAndReporting = usersDao.findByDynamicSelect("SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON U.PROFILE_ID = P.ID WHERE P.REPORTING_MGR=? AND U.STATUS = 0 AND U.ID > 3 AND U.EMP_ID>0", new Object[] { userId });
						if (activeAndReporting != null && activeAndReporting.length > 0){
							activeAndReportingList = populateProfileBean1(activeAndReporting, conn);}
						else logger.debug("ROLL_ON-->MYRESOURCES : FOUND RESOURCES REPORTING TO USER_ID=" + userId + " BUT NONE WERE ACTIVE ");
						HashSet<RollOnBean> resultList1 = new HashSet<RollOnBean>(activeAndReportingList);
						dropdown.setDropDown((resultList1 == null) ? null : resultList1.toArray());
						request.setAttribute("actionForm", dropdown);
					} catch (UsersDaoException udex){
						logger.error("ROLL_ON-->MYRESOURCES : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM USERS FOR ACTIVE_AND_REPORTING ", udex);
					} catch (SQLException e){
						e.printStackTrace();
					}
					break;
				//roll_on Wed-jun_6_2012
				case MYPROJECTS:
					/*  get all resources who are 
					 * (2)ACTIVE &	working on "my" projects
					 * 
					 * */
					try{
						ArrayList<RollOnBean> resourcesOnMyProjectsList = null;
						logger.info("inside try block of MYPROJECTS $#@#$#@#$@##" + resourcesOnMyProjectsList );
						/*	Date d2 = new Date();
							ProjectDao projectDao = ProjectDaoFactory.create(conn);
						Project[] projectsOwned = projectDao.findByDynamicSelect("SELECT * FROM PROJECT P WHERE P.OWNER_ID=? AND P.IS_ENABLE='ENABLED' ", new Object[] { new Integer(login.getUserId()) });
						if (projectsOwned != null && projectsOwned.length > 0){
							ArrayList<Integer> ownedProjectsIds = new ArrayList<Integer>(projectsOwned.length);
							for (Project eachProject : projectsOwned)
								ownedProjectsIds.add(eachProject.getId());
							String projectIds = ownedProjectsIds.toString().replace('[', ' ').replace(']', ' ').trim();
								RollOnProjMapDao rollOnProjMapDao = RollOnProjMapDaoFactory.create(conn);
							try{
								RollOnProjMap[] projRollOnDetails = rollOnProjMapDao.findByDynamicSelect("SELECT * FROM ROLL_ON_PROJ_MAP RPM WHERE RPM.PROJ_ID IN(" + projectIds + ")", null);
								if (projRollOnDetails != null && projRollOnDetails.length > 0){
									logger.debug("ROLL_ON MYPROJECTS: found matching data in the ROLL_ON_PROJ_MAP for projectIds : " + ownedProjectsIds);
									ArrayList<Integer> rollOnIdList = new ArrayList<Integer>(projRollOnDetails.length);
									for (RollOnProjMap eachRow : projRollOnDetails)
										rollOnIdList.add(eachRow.getRollOnId());
									String rollOnIds = rollOnIdList.toString().replace("[", " ").replace("]", " ").trim();
									logger.debug("ROLL_ON MYPROJECTS: obtained ROLL_ON_IDs : " + rollOnIds);
										RollOnDao rollOnDao = RollOnDaoFactory.create(conn);
									try{
										RollOn[] fetchedRollOnRows = rollOnDao.findByDynamicSelect(" SELECT * FROM ROLL_ON RO WHERE RO.ID IN(" + rollOnIds + ") AND RO.CURRENT=1", null);
										if (fetchedRollOnRows != null && fetchedRollOnRows.length > 0){
											logger.debug("ROLL_ON MYPROJECTS : found matching rows for \"ids\" and CURRENT=1");
											Set<Integer> userIdSet = new HashSet<Integer>();
											for (RollOn eachRollOnRow : fetchedRollOnRows)
												if (eachRollOnRow.getEmpId() != login.getUserId()) //ignore logged_in person
												userIdSet.add(eachRollOnRow.getEmpId());
											if (userIdSet.size() > 0){
												String userIds = userIdSet.toString().replace('[', ' ').replace(']', ' ').trim();
													UsersDao usersDao = UsersDaoFactory.create(conn);
												Users[] usersOnMyProjects = usersDao.findByDynamicSelect("SELECT * FROM USERS U WHERE U.ID IN(" + userIds + ") AND U.STATUS=0", null);
													resourcesOnMyProjectsList = populateProfileBean(usersOnMyProjects, conn);
													logger.info(new Date().getTime() - d2.getTime());
											} else{
												logger.debug("ROLL_ON MYPROJECTS : STRANGE...... found no userIds ... loggedIn user was excluded ");
											}
										} else{
											logger.debug("ROLL_ON MYPROJECTS : could not find any matching data for \"ids\" and CURRENT=1");
										}
									} catch (RollOnDaoException rodex){
										logger.error("ROLL_ON MYPROJECTS : exception was thrown when trying to fetch data from ROLL_ON for \"ids\" and current=1", rodex);
									}
								} else{
									logger.debug("ROLL_ON MYPROJECTS: could not find any entry in the ROLL_ON_PRJ_MAP for projectIds:" + ownedProjectsIds);
								}
							} catch (RollOnProjMapDaoException rpmex){
								logger.error("ROLL_ON MYPROJECTS: exception was thrown when trying to fetch data from ROLL_ON_PROJ_MAP for projectIds : " + ownedProjectsIds);
							}
						} else logger.debug("ROLL_ON-->MYPROJECTS : " + login.getUserName() + " WITH USER_ID=" + login.getUserId() + " DOES NOT OWN ANY PROJECTS..DATED-->" + new Date());
							*/
						//SELECT * FROM PROFILE_INFO PI JOIN USERS U ON PI.ID=U.PROFILE_ID JOIN ROLL_ON RO ON U.ID=RO.EMP_ID JOIN ROLL_ON_PROJ_MAP RP ON RO.ID=RP.ROLL_ON_ID AND CURRENT=1 JOIN PROJECT P ON P.ID=RP.PROJ_ID WHERE P.OWNER_ID=22 AND P.IS_ENABLE='ENABLED';
						resourcesOnMyProjectsList = populateProfileBean(UsersDaoFactory.create(conn).findByDynamicSelect("SELECT * FROM USERS U JOIN ROLL_ON RO ON U.ID=RO.EMP_ID JOIN ROLL_ON_PROJ_MAP RP ON RO.ID=RP.ROLL_ON_ID AND CURRENT=1 JOIN PROJECT P ON P.ID=RP.PROJ_ID WHERE P.OWNER_ID=? AND P.IS_ENABLE='ENABLED' AND U.STATUS=0 AND U.ID > 3 AND U.EMP_ID>0", new Object[] { login.getUserId() }), conn);
						DropDownBean dropdown = new DropDownBean();
						dropdown.setDropDown((resourcesOnMyProjectsList == null) ? null : resourcesOnMyProjectsList.toArray());
						request.setAttribute("actionForm", dropdown);
					} catch (UsersDaoException udex){
						logger.error("ROLL_ON-->MYPROJECTS : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM USERS ... --- resources on my projects ", udex);
					}
					break;
				case GLOBALPROJECT:
					try{
						Users[] GlobalResources=null;
						ArrayList<RollOnBean> globalBenchResources = null;
						 GlobalResources = UsersDaoFactory.create(conn).findByDynamicSelect("SELECT * FROM USERS U JOIN ROLL_ON RO ON U.ID=RO.EMP_ID JOIN ROLL_ON_PROJ_MAP RP ON RO.ID=RP.ROLL_ON_ID AND CURRENT=1 JOIN PROJECT P ON P.ID=RP.PROJ_ID WHERE P.ID IN(1,3) AND P.IS_ENABLE='ENABLED' AND U.STATUS=0 AND U.ID > 3 AND U.EMP_ID>0;", null);
						if (GlobalResources != null && GlobalResources.length > 0){ 
							globalBenchResources = populateGlobalProfileBean(GlobalResources, conn);
							Collections.sort(globalBenchResources);
							DropDownBean dropdown1 = new DropDownBean();
							if(globalBenchResources!=null){
							dropdown1.setDropDown(globalBenchResources.toArray());
							}
							request.setAttribute("actionForm", dropdown1);
						}
					} catch (UsersDaoException udex){
						logger.error("ROLL_ON-->GLOBALBENCH : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM USERS FOR ACTIVE_AND_REPORTING ", udex);
					}
					break;
				default:
					break;
			}
		} catch (Exception e){} finally{
			ResourceManager.close(conn);
		}
		// TODO Auto-generated method stub
		return result;
	}
  
	ArrayList<RollOnBean> populateGlobalProfileBean(Users[] activeAndReporting, Connection conn) {
		ArrayList<RollOnBean> resultList = new ArrayList<RollOnBean>(activeAndReporting.length);
		RollOnBean bean=null;
		for(Users eachUser:activeAndReporting){
			bean=getBean(eachUser,conn);
			if(bean!=null) resultList.add(bean);
		}
		
		
		return resultList;
	}
	
	
	ArrayList<RollOnBean> populateProfileBean(Users[] activeAndReporting, Connection conn) {
		ArrayList<RollOnBean> resultList = new ArrayList<RollOnBean>(activeAndReporting.length);
		boolean isConPass = conn == null ? false : true;
		try{
			if (!isConPass) conn = ResourceManager.getConnection();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			LevelsDao levelsDao = LevelsDaoFactory.create(conn);
			DivisonDao divisonDao = DivisonDaoFactory.create(conn);
			ProjectMappingDao mappingDao = ProjectMappingDaoFactory.create(conn);
			ProjectDao projectDao = ProjectDaoFactory.create(conn);
			RollOnDao rollOnDao = RollOnDaoFactory.create(conn);
			ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create(conn);
			PersonalInfo userPersonal=null;
			PassportInfo userPassport=null;
			EducationInfo[] userEducation=null;
			SalaryDetails[] salary=null;
			ExperienceInfo experienceinfo[] = null;
			SalaryDetailsDao salaryDetailDao=SalaryDetailsDaoFactory.create(conn);
			EducationInfoDao educationInfoDao=EducationInfoDaoFactory.create(conn);
			PassportInfoDao passportInfoDao=PassportInfoDaoFactory.create(conn);
			PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create(conn);
			ExperienceInfoDao experienceInfoDao = ExperienceInfoDaoFactory.create();
			ExperienceInfoModel expModel=new ExperienceInfoModel();
			String prevExp = "0-0-0";
			String currExp = "0-0-0";
			String totalExp = "0-0-d";
			String totalExpn = "0y0m0d";
			for (Users eachUser : activeAndReporting){
			//	logger.info("Fetching Roll-On Details for UserID====="+eachUser.getId());
				RollOnBean bean = new RollOnBean();
				bean.setUserId(eachUser.getId() + "");
				bean.setEmpid(eachUser.getEmpId() + "");
				ProfileInfo userProfile = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(eachUser.getProfileId()));
				bean.setName(userProfile.getFirstName() + " " + userProfile.getLastName());
				Levels userLevel = levelsDao.findByPrimaryKey(new LevelsPk(userProfile.getLevelId()));
				bean.setDesignation(userLevel.getDesignation());
				Divison userDivison = divisonDao.findByPrimaryKey(new DivisonPk(userLevel.getDivisionId()));
				//bean.setDivision(userDivison.getName());
				int parentId = userDivison.getParentId();
				while (parentId != 0){
					userDivison = divisonDao.findByPrimaryKey(new DivisonPk(parentId));
					parentId = userDivison.getParentId();
				}
				bean.setDepartment(userDivison.getName());
				// Setting mobile Number	
				int pesonalId=eachUser.getPersonalId();
				if(pesonalId>0) userPersonal=personalInfoDao.findByPrimaryKey(new PersonalInfoPk(pesonalId));
				if(userPersonal!=null)

				//	bean.setMobileNo(userPersonal.getEmerPhoneNo());
				bean.setMobileNo(userPersonal.getPrimaryPhoneNo());
				
					
			// Setting PassportNumber
				int passportId=eachUser.getPassportId();
				if(passportId>0)
				userPassport=passportInfoDao.findByPrimaryKey(new PassportInfoPk(passportId));
				if(userPassport!=null)
			//		logger.info("setting Passport number===>"+userPassport);
					bean.setPassportNo(userPassport.getPassportNo());
		/*		
				int passportId=eachUser.getPassportId();
				if(passportId>0)
				userPassport=passportInfoDao.findByPrimaryKey(new PassportInfoPk(passportId));
				if(userPassport!=null)
					bean.setPassportNo(userPassport.getPassportNo());*/
					
			// Setting Highest Degree 
				
					userEducation=educationInfoDao.findWhereUserIdEquals(eachUser.getId());
					if(userEducation!=null){
					for(EducationInfo edu:userEducation){
						bean.setHighDegree(edu.getDegreeCourse());
						
						
					}
					}
			// Setting ctc
				
				salary=salaryDetailDao.findByDynamicWhere("USER_ID= ? AND SAL_ID=14",  new Object[] { eachUser.getId()});
				if(salary!=null){
					for(SalaryDetails sal:salary){
						bean.setAnnualCtc(DesEncrypterDecrypter.getInstance().decrypt(sal.getAnnual()));
						
					}
				}
				
				
				 // Setting Total Experience	
				
				
				experienceinfo=experienceInfoDao.findWhereUserIdEquals(eachUser.getId());
				
				if(experienceinfo!=null){
					for(ExperienceInfo exp:experienceinfo){
						ExperienceBean expBean = DtoToBeanConverter.DtoToBeanConverter(exp);
						prevExp = expModel.addExperiance(expBean.getDateJoining(), expBean.getDateRelieving(), prevExp);
						}
				}
						if (userProfile != null){
						currExp = PortalUtility.getDuration(userProfile.getDateOfJoining(), (eachUser.getStatus() == 0) ? new Date() : (userProfile.getDateOfSeperation() == null) ? new Date() : userProfile.getDateOfSeperation());
						}
						totalExp = expModel.addExperiance(currExp, prevExp);
						String totEperience[] = totalExp.split("-");
						totalExpn = totEperience[0] + "y" + totEperience[1] + "m" + totEperience[2] + "d";
						
						//totalExpn = totalExp.substring(0, 1) + "y" + totalExp.substring(2, 3) + "m" + totalExp.substring(4, totalExp.length())+ "d";		
						bean.setTotExperience(totalExpn);
				
				// setting RM for each user
						if (userProfile != null){
							
					ProfileInfo rmProfile = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(userProfile.getReportingMgr()));
		               if(rmProfile!=null) bean.setRmName(rmProfile.getFirstName());
						}
				
				
				ProjectMapping[] projectMapping = mappingDao.findByDynamicSelect("SELECT * FROM PROJECT_MAPPING PM WHERE PM.USER_ID=? ORDER BY ID DESC ", new Object[] { new Integer(eachUser.getId()) });
				if (projectMapping != null && projectMapping.length > 0){
					Project userProject = projectDao.findByPrimaryKey(projectMapping[0].getProjectId());
					bean.setProject(userProject.getName());
					//bean.setProjectId(projectMapping[0].getProjectId());
					RollOn[] rollOn=null;
					
					 rollOn = rollOnDao.findByDynamicSelect("SELECT * FROM ROLL_ON RO WHERE RO.EMP_ID=? AND RO.CURRENT=1", new Object[] { new Integer(eachUser.getId()) });
					 if(rollOn!=null && rollOn.length>0){
					 bean.setRollOnId(rollOn[0].getId() + "");
					ChargeCode[] allChargeCode = chargeCodeDao.findByDynamicSelect("SELECT CC.* FROM CHARGE_CODE CC WHERE CC.ID= ?", new Object[] { rollOn[0].getChCodeId() });
					if (allChargeCode != null && allChargeCode.length > 0) bean.setChCode(allChargeCode[0].getChCodeName());
				}
				}
				
				
				resultList.add(bean);
			}
			
			Collections.sort(resultList);
		/*}catch (UsersDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
			
		} catch (LevelsDaoException ldex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM LEVELS ", ldex);
		} catch (DivisonDaoException ddex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM DIVISIONS ", ddex);
		} catch (ProjectMappingDaoException pmdex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROJECT_MAPPING ", pmdex);
		} catch (ProjectDaoException pdex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROJECT ", pdex);
		} catch (RollOnDaoException rodex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM ROLL_ON ", rodex);
		} catch (ChargeCodeDaoException ccdex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM CHARGE_CODE ", ccdex);
		} catch (ProfileInfoDaoException pidex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROFILE_INFO ", pidex);
		} catch (PersonalInfoDaoException per){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROFILE_INFO ", per);
		} catch (ExperienceInfoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EducationInfoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 catch (SalaryDetailsDaoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (PassportInfoDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (!isConPass) ResourceManager.close(conn);
		}
		return resultList;
	}
	
	
	
	ArrayList<RollOnBean> populateProfileBean1(Users[] activeAndReporting, Connection conn)  {
		ArrayList<RollOnBean> resultList = new ArrayList<RollOnBean>(activeAndReporting.length);
		boolean isConPass = conn == null ? false : true;
		try{
			if (!isConPass) conn = ResourceManager.getConnection();
			ProfileInfo[] tempRmProfile=null;
			ProfileInfo[] tempSmProfile=null;
			ProfileInfo[] tempTmProfile=null;
			ProfileInfo[] tempFmProfile=null;
			RollOnBean bean = null;
			RollOnBean bean1 = null;
			RollOnBean bean2 = null;
			RollOnBean bean3 = null;
			RollOnBean bean4 = null;
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			UsersDao userDao=UsersDaoFactory.create();
			if(activeAndReporting!=null){
			for (Users eachUser : activeAndReporting){
				bean=getBean(eachUser,conn);
				if(bean!=null) resultList.add(bean);
			// Manager view	
			// Start of First Level ---------------------------------------------------------------------
				tempRmProfile=profileInfoDao.findWhereReportingMgrEquals(eachUser.getId());
				if(tempRmProfile!=null)
				{
					Users[] activeAndReporting1 = userDao.findByDynamicSelect("SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON U.PROFILE_ID = P.ID WHERE P.REPORTING_MGR=? AND U.STATUS = 0 AND U.ID > 3 AND U.EMP_ID>0", new Object[] { eachUser.getId() });
					
					 for(Users user1:activeAndReporting1){
						 bean1=getBean(user1,conn);
						 if(bean1!=null) resultList.add(bean1);
						 
						 
			// Start of Second Level ----------------------------------------------------------------------
				 tempSmProfile=profileInfoDao.findWhereReportingMgrEquals(user1.getId());
				 if(tempSmProfile!=null)
							{
								Users[] activeAndReporting2 = userDao.findByDynamicSelect("SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON U.PROFILE_ID = P.ID WHERE P.REPORTING_MGR=? AND U.STATUS = 0 AND U.ID > 3 AND U.EMP_ID>0", new Object[] { user1.getId() });
								
								   for(Users user2:activeAndReporting2){
									   bean2=getBean(user2,conn);
									   if(bean2!=null) resultList.add(bean2);
			// Start of Third Level --------------------------------------------------------------------
				tempTmProfile=profileInfoDao.findWhereReportingMgrEquals(user2.getId());
				 if(tempTmProfile!=null)
										{
										Users[] activeAndReporting3 = userDao.findByDynamicSelect("SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON U.PROFILE_ID = P.ID WHERE P.REPORTING_MGR=? AND U.STATUS = 0 AND U.ID > 3 AND U.EMP_ID>0", new Object[] { user2.getId() });
											
										  for(Users user3:activeAndReporting3){
											  bean3=getBean(user3,conn);
											  if(bean3!=null) resultList.add(bean3);
			// Start of Fourth Level --------------------------------------------------------------------
				 tempFmProfile=profileInfoDao.findWhereReportingMgrEquals(user2.getId());
				    if(tempFmProfile!=null)
										          {
										         Users[] activeAndReporting4 = userDao.findByDynamicSelect("SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON U.PROFILE_ID = P.ID WHERE P.REPORTING_MGR=? AND U.STATUS = 0 AND U.ID > 3 AND U.EMP_ID>0", new Object[] { user2.getId() });
																				
											     for(Users user4:activeAndReporting4){
											    	 bean4=getBean(user4,conn);
											    	if(bean4!=null) resultList.add(bean4);	
										          
											     
											       }} // End of Level Four
																				
											 }} // End of Level Three
								 }} // End of Level Two
		
					}}// End of Level One
				
				
				
				
				
				
			    }
			}
			
			Collections.sort(resultList);
				
		} catch (ProfileInfoDaoException pidex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROFILE_INFO ", pidex);
		}catch (UsersDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (!isConPass) ResourceManager.close(conn);
		}
		return resultList;
	}

	
	
	public RollOnBean getBean(Users eachUser,Connection conn){
	//	logger.info("Fetching Roll-On Details for UserID====="+eachUser.getId());
		
		RollOnBean bean = new RollOnBean();
		
		ExperienceInfoDao experienceInfoDao = ExperienceInfoDaoFactory.create();
		EducationInfoDao educationInfoDao=EducationInfoDaoFactory.create(conn);
		PassportInfoDao passportInfoDao=PassportInfoDaoFactory.create(conn);
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create(conn);
		LevelsDao levelsDao = LevelsDaoFactory.create(conn);
		DivisonDao divisonDao = DivisonDaoFactory.create(conn);
		ProjectMappingDao mappingDao = ProjectMappingDaoFactory.create(conn);
		ProjectDao projectDao = ProjectDaoFactory.create(conn);
		RollOnDao rollOnDao = RollOnDaoFactory.create(conn);
		ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create(conn);
		PersonalInfo userPersonal=null;
		PassportInfo userPassport=null;
		EducationInfo[] userEducation=null;
		ExperienceInfo experienceinfo[] = null;
		ExperienceInfoModel expModel=new ExperienceInfoModel();
		String prevExp = "0-0-0";
		String currExp = "0-0-0";
		String totalExp = "0-0-d";
		String totalExpn = "0y0m0d";
		try{
		bean.setUserId(eachUser.getId() + "");
		bean.setEmpid(eachUser.getEmpId() + "");
		ProfileInfo userProfile = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(eachUser.getProfileId()));
		bean.setName(userProfile.getFirstName() + " " + userProfile.getLastName());
		Levels userLevel = levelsDao.findByPrimaryKey(new LevelsPk(userProfile.getLevelId()));
		bean.setDesignation(userLevel.getDesignation());
		Divison userDivison = divisonDao.findByPrimaryKey(new DivisonPk(userLevel.getDivisionId()));
	//bean.setDivision(userDivison.getName());
		int parentId = userDivison.getParentId();
		while (parentId != 0){
			userDivison = divisonDao.findByPrimaryKey(new DivisonPk(parentId));
			parentId = userDivison.getParentId();
		}
		bean.setDepartment(userDivison.getName());
	// Setting mobile Number	
		int pesonalId=eachUser.getPersonalId();
		if(pesonalId>0) userPersonal=personalInfoDao.findByPrimaryKey(new PersonalInfoPk(pesonalId));
		if(userPersonal!=null)

		//	bean.setMobileNo(userPersonal.getEmerPhoneNo());
			bean.setMobileNo(userPersonal.getPrimaryPhoneNo());

			
	// Setting PassportNumber
		int passportId=eachUser.getPassportId();
		if(passportId>0)
		userPassport=passportInfoDao.findByPrimaryKey(new PassportInfoPk(passportId));
		if(userPassport!=null)
			bean.setPassportNo(userPassport.getPassportNo());
			
	// Setting Highest Degree 
		
			userEducation=educationInfoDao.findWhereUserIdEquals(eachUser.getId());
			if(userEducation!=null){
			for(EducationInfo edu:userEducation){
				bean.setHighDegree(edu.getDegreeCourse());
				
			}
			}
	// Setting ctc
		// old ctc
			
	/*	salary=salaryDetailDao.findByDynamicWhere("USER_ID= ? AND SAL_ID=14",  new Object[] { eachUser.getId()});
		if(salary!=null){
			for(SalaryDetails sal:salary){
				bean.setAnnualCtc(DesEncrypterDecrypter.getInstance().decrypt(sal.getAnnual()));
			}
		}*/
		
			// setting updated ctc (Total cost to the company A+B+C+D)
			// new ctc
			
		SalaryDetailModel Tot = new SalaryDetailModel();
		Salary salaryStack = Tot.getSalary(eachUser.getId(), true, SalaryDetailModel.TYPE_NORMAL);
		float aa=	salaryStack.getAnnualCTC();
		bean.setAnnualCtc (Float.toString(aa));
		
	 // Setting Total Experience	
		
		experienceinfo=experienceInfoDao.findWhereUserIdEquals(eachUser.getId());
		if(experienceinfo!=null){
			for(ExperienceInfo exp:experienceinfo){
				ExperienceBean expBean = DtoToBeanConverter.DtoToBeanConverter(exp);
				prevExp = expModel.addExperiance(expBean.getDateJoining(), expBean.getDateRelieving(), prevExp);
				}
		}
				if (userProfile != null){
				currExp = PortalUtility.getDuration(userProfile.getDateOfJoining(), (eachUser.getStatus() == 0) ? new Date() : (userProfile.getDateOfSeperation() == null) ? new Date() : userProfile.getDateOfSeperation());
				
				}
				totalExp = expModel.addExperiance(currExp, prevExp);
				String totEperience[] = totalExp.split("-");
				totalExpn = totEperience[0] + "y" + totEperience[1] + "m" + totEperience[2] + "d";
				
				//totalExpn = totalExp.substring(0, 1) + "y" + totalExp.substring(2, 3) + "m" + totalExp.substring(4, totalExp.length())+ "d";		
				bean.setTotExperience(totalExpn);
		
		// setting RM for each user
			
				if (userProfile != null){		
			    ProfileInfo rmProfile = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(userProfile.getReportingMgr()));
                if(rmProfile!=null) bean.setRmName(rmProfile.getFirstName());
				        }
		
		ProjectMapping[] projectMapping = mappingDao.findByDynamicSelect("SELECT * FROM PROJECT_MAPPING PM WHERE PM.USER_ID=? ORDER BY ID DESC ", new Object[] { new Integer(eachUser.getId()) });
		if (projectMapping != null && projectMapping.length > 0){
			Project userProject = projectDao.findByPrimaryKey(projectMapping[0].getProjectId());
			bean.setProject(userProject.getName());
			//bean.setProjectId(projectMapping[0].getProjectId());
			RollOn[] rollOn = rollOnDao.findByDynamicSelect("SELECT * FROM ROLL_ON RO WHERE RO.EMP_ID=? AND RO.CURRENT=1", new Object[] { new Integer(eachUser.getId()) });
			 if(rollOn!=null && rollOn.length>0){
			bean.setRollOnId(rollOn[0].getId() + "");
			ChargeCode[] allChargeCode = chargeCodeDao.findByDynamicSelect("SELECT CC.* FROM CHARGE_CODE CC WHERE CC.ID= ?", new Object[] { rollOn[0].getChCodeId() });
			if (allChargeCode != null && allChargeCode.length > 0) bean.setChCode(allChargeCode[0].getChCodeName());
			 }
		}
		} catch (LevelsDaoException ldex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM LEVELS ", ldex);
		} catch (DivisonDaoException ddex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM DIVISIONS ", ddex);
		} catch (ProjectMappingDaoException pmdex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROJECT_MAPPING ", pmdex);
		} catch (ProjectDaoException pdex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROJECT ", pdex);
		} catch (RollOnDaoException rodex){
			rodex.printStackTrace();
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM ROLL_ON ", rodex);
		} catch (ChargeCodeDaoException ccdex){
			ccdex.printStackTrace();
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM CHARGE_CODE ", ccdex);
		} catch (ProfileInfoDaoException pidex){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROFILE_INFO ", pidex);
		} catch (PersonalInfoDaoException per){
			logger.error("ROLL_ON-->populateProfileBean : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM PROFILE_INFO ", per);
		} catch (ExperienceInfoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();} 
		catch (EducationInfoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (SalaryDetailsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/catch (PassportInfoDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		
		return bean;
		
	}
	

	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		RollOn rollOnForm = (RollOn) form;
		Connection conn = null;
		// *****************common to SINGLEROLLON and MULTIPLEROLLON************************
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			rollOnForm.setUserId(form.getUserId());//to remove the conflict b/w userId available in RollOn and PortalForm
			ProcessChainDao processChainDao = ProcessChainDaoFactory.create(conn);
			Login loginDto = Login.getLogin(request);
			RollOnDao rollOnDao = RollOnDaoFactory.create();
			RollOnDao rollOnDao1 = RollOnDaoFactory.create(conn);
			RollOnMgrMapDao rollOnMgrMapDao = RollOnMgrMapDaoFactory.create(conn);
			ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory.create(conn);
			ProjectMappingDao projectMappingDao1 = ProjectMappingDaoFactory.create();
			RollOnMgrMapDao rollOnMgrMapDao1 = RollOnMgrMapDaoFactory.create();
			EmpSerReqMapDao empReqMapDao = EmpSerReqMapDaoFactory.create();
			RegionsDao regionsDao = RegionsDaoFactory.create(conn);
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			ClientDao clientDao = ClientDaoFactory.create(conn);
			ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create(conn);
			ProjectDao projectDao = ProjectDaoFactory.create(conn);
			UsersDao usersDao = UsersDaoFactory.create(conn);
			Inbox inbox = new Inbox();
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail portalMail = new PortalMail();
			EmpSerReqMapPk reqMapPK = new EmpSerReqMapPk();
			// insert into EmpSerReqMap Table for internal mapping
			SimpleDateFormat uniqueIdformat = new SimpleDateFormat("MSsm");
			String uniqueID = uniqueIdformat.format(new Date());
			EmpSerReqMap serReqMap = new EmpSerReqMap();
			serReqMap.setSubDate(new Date());
			serReqMap.setReqId("IN-RN-" + uniqueID);
			serReqMap.setReqTypeId(9);
			serReqMap.setRequestorId(loginDto.getUserId());
			int regionid = regionsDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0].getId();
			serReqMap.setRegionId(regionid);
			ProcessChain processChainDto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC  LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID WHERE ROLE_ID=(SELECT ROLE_ID FROM USER_ROLES UR LEFT JOIN USERS U ON U.ID=UR.USER_ID WHERE U.ID=?) AND MODULE_ID=18", new Object[] { loginDto.getUserId() })[0];
			serReqMap.setProcessChainId(processChainDto.getId());
			serReqMap.setNotify(processChainDto.getNotification());
			reqMapPK = empReqMapDao.insert(serReqMap);
			int esrMap=reqMapPK.getId();
			// ************ data inserted into EMP_SER_REQ_MAP ****************
			rollOnForm.setProjName(projectDao.findByPrimaryKey(rollOnForm.getProjId()).getName());
			switch (SaveTypes.getValue(rollOnForm.getsType())) {
				case SINGLEROLLON:
					LevelsDao levels = LevelsDaoFactory.create(conn);
					DivisonDao divisionDao = DivisonDaoFactory.create(conn);
					RollOnPk rollOnPk = null;
				RollOnProjMapDao projMapDao1 = RollOnProjMapDaoFactory.create();
					Levels levelsDto = new Levels();
					RollOn rollOn = new RollOn();
					final int tempRollonInfo = (rollOnForm.getUserId() == null) ? rollOnForm.getEmpId() : rollOnForm.getUserId();
					logger.debug("ROLL_ON : SINGLEROLLON ... request rasied by user_id=" + loginDto.getUserId() + " on " + new Date() + " for employee with user_id=" + tempRollonInfo);
					// SINGLEROLLON begins
					try{
						rollOnDao.inactiveRollOnData(rollOnForm.getUserId());
						Time t = PortalUtility.fromStringToTime(rollOnForm.getReportingTime());
						rollOnForm.setReportTm(t);
						rollOnForm.setCurrent(Short.parseShort("1"));
						rollOnForm.setEsrqmId(esrMap);
						// TRAVEL_REQ_FLAG and NOTIFIERS are already set in the form
						rollOnForm.setEmpId(rollOnForm.getUserId());//need to set the userId in empId .... maps to EMP_ID in ROLL_ON
						/*
						 *It is not mandatory to have charge_code for a project....
						 *to support this, when a COMPANY_LEVEL/REGION_LEVEL project is selected, we get chCodeId as "0"
						 *we need to set it to NULL 
						 * */
						rollOnForm.setChCodeIdNull((rollOnForm.getChCodeId() == 0) ? true : false);//this will take care of chCodeId
						rollOnPk = rollOnDao.insert(rollOnForm);// The value emp_id in rollon table refers to userid in user table & not emp_id
						savePerdiemMasterData(rollOnForm.getEmpId(), rollOnForm.getPerdiem(), rollOnForm.getCurrency(), rollOnForm.getStartDate(), rollOnForm.getEndDate(), conn);
						// only employee's HR_SPOC must receive this info
						final int tempTravelReqFlag = rollOnForm.getTravelReqFlag();
						rollOnForm.setTravelReqFlag(0);// set this to tempTravelReqFlag just before sending mail to HR_SPOC
						// Insert Record for RollOn Project Map
						if (rollOnForm.getProjId() > 0){
							RollOnProjMap projMap = new RollOnProjMap();
							projMap.setProjId(rollOnForm.getProjId());
							projMap.setRollOnId(rollOnPk.getId());
							projMap.setProjLocId(rollOnForm.getProjectLocationId());
							projMapDao1.insert(projMap);
							ProjectMapping prjMapping = new ProjectMapping();
							prjMapping.setProjectId(rollOnForm.getProjId());
							prjMapping.setUserId(rollOnForm.getEmpId()); //this may be confusing
							projectMappingDao1.insert(prjMapping);
						}
						RollOnMgrMap managerMap = new RollOnMgrMap();
						managerMap.setMgrEmail(rollOnForm.getMgrEmailId());
						managerMap.setMgrName(rollOnForm.getManagerName());
						managerMap.setMgrDetail(rollOnForm.getMgrDetails());
						managerMap.setOnsiteMgr(rollOnForm.getOnSiteMgr());
						managerMap.setOnsiteMgrDetails(rollOnForm.getOnSiteMgrDetail());
						managerMap.setRollOnId(rollOnPk.getId());
						managerMap.setMgrPhoneNo(rollOnForm.getMgrPhoneNo());
						rollOnMgrMapDao1.insert(managerMap);
						rollOn = rollOnDao.findByPrimaryKey(rollOnPk.getId());
						ProfileInfo rolledonUserinfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON  PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(rollOnForm.getUserId()) })[0];
						rollOnForm.setEmpName(rolledonUserinfo.getFirstName());
						rollOnForm.setLastName(rolledonUserinfo.getLastName());
						Client[] clientDto = clientDao.findByDynamicSelect("SELECT * FROM CLIENT C LEFT JOIN PROJ_CLIENT_MAP PCM ON C.ID=PCM.CLIENT_ID WHERE PCM.PROJ_ID=?", new Object[] { new Integer(rollOnForm.getProjId()) });
						if (clientDto.length > 0){
							rollOnForm.setClientName(clientDto[0].getName());
						}
						rollOnForm.setStartDate(rollOn.getStartDate());
						rollOnForm.setReportDt(rollOn.getReportDt());
						rollOnForm.setEndDate(rollOn.getEndDate());
						rollOnForm.setCreateDate(rollOn.getCreateDate());
						//charge_code is not mandatory for a project...very confusing...
						if (!(rollOnForm.isChCodeIdNull()) && (rollOnForm.getChCodeId() > 0)){
							PoDetailsModel.updatePoEmpMap(rollOnForm.getUserId());//set CURRENT=0 for old charge-code
							//get the charge-code selected
							ChargeCode selectedChargeCode = chargeCodeDao.findByPrimaryKey(rollOnForm.getChCodeId());
							rollOnForm.setChargeCode(selectedChargeCode.getChCode());
							rollOnForm.setChargeCodeTitle(selectedChargeCode.getChCodeName());
							//fix for ;post rollon, no of resources not getting updated
							PoEmpMapDao poEmpMapDao = PoEmpMapDaoFactory.create(conn);
							PoEmpMap poEmpMapDto = new PoEmpMap();
							poEmpMapDto.setPoId(selectedChargeCode.getPoId());
							poEmpMapDto.setEmpId(rollOnForm.getUserId());
							poEmpMapDto.setRate(rollOnForm.getPerdiem());
							poEmpMapDto.setType(rollOnForm.getPaymentTerm());
							poEmpMapDto.setCurrency(rollOnForm.getCurrency());
							poEmpMapDto.setInactive((short) 1);//INACTIVE=1 ==>
							poEmpMapDto.setCurrent((short) 1);
							PoEmpMapPk insertedRow = poEmpMapDao.insert(poEmpMapDto);
							logger.debug("SINGLE_ROLLON : for userId=" + rollOnForm.getUserId() + "inserted row in PO_EMP_MAP.. id =" + insertedRow.getId());
						} else{
							PoDetailsModel.updatePoEmpMap(rollOnForm.getUserId());//set CURRENT=0 for old charge-code
							logger.debug("SINGLE_ROLLON : for userId=" + rollOnForm.getUserId() + "updated PO_EMP_MAP...set current=0..this employee is being rolled_on to a project having no charge-code" + "...this employee must be removed from the previous associated charge-code..");
						}
						/*else{
						logger.error("*************SINGLE_ROLLON : logged-in UserId="+loginDto.getUserId()+" was trying to roll_on userId="+rollOnForm.getUserId()+" on project with Id="+rollOnForm.getProjId()+
								" charge-code received was null..failed to update PO_EMP_MAP.CURRENT=0 & failed to insert new row into PO_EMP_MAP*************");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.chargecode.null"));
						result.setForwardName("success");
						return result;
						}*/
						//this was fetching wrong charge-code
						/*ChargeCode chargeCodeDto[] = chargeCodeDao.findByDynamicSelect("SELECT * FROM CHARGE_CODE CC LEFT JOIN PO_EMP_MAP PEM ON CC.ID=PEM.PO_ID WHERE PEM.EMP_ID=?",
										new Object[]{new Integer(rollOnForm.getUserId())});
						if (chargeCodeDto.length > 0){
							rollOnForm.setChargeCode(chargeCodeDto[0].getChCode());
							rollOnForm.setChargeCodeTitle(chargeCodeDto[0].getChCodeName());
						}
						*/
						levelsDto = levels.findByDynamicSelect("SELECT * FROM LEVELS L JOIN PROFILE_INFO PI ON PI.LEVEL_ID=L.ID WHERE PI.ID=?", new Object[] { new Integer(rolledonUserinfo.getId()) })[0];
						rollOnForm.setDesignation(levelsDto.getDesignation());
						rollOnForm.setEmpDiv(divisionDao.findByPrimaryKey(levelsDto.getDivisionId()).getName());
						/*
						 * looks like redundant code.. ?? ? 
						 * 
						if(!(rollOnForm.isChCodeIdNull())){
							ChargeCode chargeCode = chargeCodeDao.findByPrimaryKey(rollOnForm.getChCodeId());
							rollOnForm.setChargeCode(chargeCode.getChCode());
							rollOnForm.setChargeCodeTitle(chargeCode.getChCodeName());
						}*/
						// query for project location
						/*ProjLocations projLocations[] = projLocationsDao.findByProject(rollOnForm.getProjId());
						if (projLocations != null && projLocations.length > 0) {
							rollOnForm.setClientLocation(projLocations[0].getName());
							rollOnForm.setClientOfficeAddress(projLocations[0].getAddress());	
						}
						*/
						/*logger.debug("ROLL_ON SINGLEROLLON: preparing to send mail to notifiers");
						sendMailToNotifiers(rollOnForm);//send notification mails to those mailIds entered in the textbox (not from processchain)
						logger.debug("ROLL_ON SINGLEROLLON: sent mail to notifiers");*/
						ArrayList<String> mailid = new ArrayList<String>();
						String[] enteredMailIds = null;
						if (rollOnForm.getNotifiers() != null && rollOnForm.getNotifiers().length() > 0){
							if (rollOnForm.getNotifiers().length() == 1) rollOnForm.setNotifiers(rollOnForm.getNotifiers() + "@dikshatech.com");
							else rollOnForm.setNotifiers(rollOnForm.getNotifiers().replaceAll(",", "@dikshatech.com;") + "@dikshatech.com");
							enteredMailIds = rollOnForm.getNotifiers().split(";");
							mailid.addAll(Arrays.asList(enteredMailIds));
						}
						ProcessChain empProcessChain = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC  LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID WHERE ROLE_ID=(SELECT ROLE_ID FROM USER_ROLES UR LEFT JOIN USERS U ON U.ID=UR.USER_ID WHERE U.ID=?) AND MODULE_ID=18", new Object[] { loginDto.getUserId() })[0];
						Integer[] notifierUserIds = new ProcessEvaluator().notifiers(empProcessChain.getNotification(), (rollOnForm.getUserId() == null) ? rollOnForm.getEmpId() : rollOnForm.getUserId());
						for (Integer eachNotifierId : notifierUserIds){
							Users notifier = usersDao.findByPrimaryKey(eachNotifierId);
							ProfileInfo notifierProfileInfo = profileInfoDao.findByPrimaryKey(notifier.getProfileId());
							mailid.add(notifierProfileInfo.getOfficalEmailId());
						}
						rollOnForm.setReceiverEmailId(rolledonUserinfo.getOfficalEmailId());// Mail.To = Employee being rolledOn
						rollOnForm.setTravelReqFlag(tempTravelReqFlag);
						if (mailid.size() > 0){
							String[] stringArray = Arrays.copyOf(mailid.toArray(), mailid.toArray().length, String[].class);
							rollOnForm.setCCnotifierMailIds(stringArray);
							sendMail(rollOnForm, null, conn);
							logger.debug("ROLL_ON SINGLEROLLON: invoked ! ! sendMail(rollOnForm,null).... Mail.To : Employee   Mail.CC : Requester's HRSPOC, R.M from processChain(notifiers)");
						} else{
							sendMail(rollOnForm, null, conn);//else..just mail to the employee being rolledOn
							logger.debug("ROLL_ON SINGLEROLLON : mail not sent to hrspoc/rm/notifiers! ! ! pls check the notifiers in processchain with Id=" + empProcessChain.getId());
						}
						// sending to docking station
						rollOnForm.setReceiverEmailId(null);
						logger.debug("ROLL_ON SINGLEROLLON: begin sendMail(rollOnForm,INFO).... for DockingStation");
						portalMail = sendMail(rollOnForm, null, conn);//portalMail = sendMail(rollOnForm, "INFO");
						logger.debug("ROLL_ON SINGLEROLLON: begin sendMail(rollOnForm,INFO).... for DockingStation");
						String message_body = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
						rollOn.setRaisedBy(loginDto.getUserId());
						rollOn.setMessageBody(message_body);
						rollOn.setEsrqmId(reqMapPK.getId());
						rollOnDao.update(rollOnPk, rollOn);
						conn.commit();
						InboxModel inboxModel = new InboxModel();
						inbox = inboxModel.sendToDockingStation(reqMapPK.getId(), rollOn.getId(), portalMail.getMailSubject(), Status.ROLLEDON);
						inboxModel.notify(reqMapPK.getId(), inbox);
						String updateInboxQuery = "UPDATE INBOX SET ASSIGNED_TO=0 WHERE ESR_MAP_ID=" + inbox.getEsrMapId();
						InboxDaoFactory.create(conn).executeUpdate(updateInboxQuery);
						conn.commit();
					} catch (Exception ex){
						logger.error("SINGLEROLLON : Exception occurred when doing ROLL_ON ", ex);
						ex.printStackTrace();
					}
					// SINGLEROLLON ends
					break;
				case MULTIPLEROLLON:
					/*
					 *SINGLE_ROLLON
					 *MULTIPLE_ROLLON
					 *ROLL_ON update..........uses employee's processChain to obtain the hrspoc and rm 
					 */
					logger.debug("ROLL_ON : MULTIROLLON ... request rasied by user_id=" + loginDto.getUserId() + " on " + new Date());
					RollOnProjMapDao rollOnProjectMapDao = RollOnProjMapDaoFactory.create(conn);
					LevelsDao levelsDao = LevelsDaoFactory.create(conn);
					DivisonDao divisonDao = DivisonDaoFactory.create(conn);
					/*
					 * When a chargeCode is created and Saved, user will be
					 * prompted to ROLL_ON selected resources & there will
					 * be more than one resource to be rolled_on --> MULTIPLEROLLON
					 * 
					 * MULTIPLEROLLON is similar to SINGLEROLLON, the only
					 * change is in the number of employees being rolled on. There
					 * will be multiple entries in respective tables.
					 * 
					 * Insert into EMP_SER_REQ_MAP is pushed outside the switch
					 * statement, since it is common to both SINGLEROLLON &
					 * MULTIPLEROLLON cases 
					 * 
					 *               order of data insertion
					 * insert into ROLL_ON 
					 * insert into ROLL_ON_PROJ_MAP
					 * insert into PROJECT_MAPPING
					 * insert into ROLL_ON_MGR_MAP 
					 * sendMail to notifiers(including notifiers mentioned in the TextBox) 
					 * sendMail to eachEmployee(Mail.TO=Employee UserId , Mail.CC = Employee UserId's RM, HR_SPOC) + this mailTemplate 
					 * also includes the information "Who is doing the ROLL_ON" 
					 *
					 */
					for (String eachRollOn : rollOnForm.getMultiRollOn()){
						for (String input : eachRollOn.split("~=~")){
							String toCheckWith = input.split("=")[0]; //KEY
							String toSetWith = input.split("=")[1]; //VALUE				
							if (toCheckWith.equals("userId")) rollOnForm.setUserId(Integer.parseInt(toSetWith));
							else if (toCheckWith.equals("perdiem")) rollOnForm.setPerdiem(toSetWith);
							else if (toCheckWith.equals("paymentTerm")) rollOnForm.setPaymentTerm(toSetWith);
							else if (toCheckWith.equals("currency")) rollOnForm.setCurrency(toSetWith);
							else if (toCheckWith.equals("travelReqFlag")) rollOnForm.setTravelReqFlag(Integer.parseInt(toSetWith));
							else if (toCheckWith.equals("inactive")) rollOnForm.setEmpInactiveInfo(Short.parseShort(toSetWith));
						}
						logger.debug("ROLL_ON  MULTIROLLON : employee with user_id=" + rollOnForm.getUserId() + " is being rolled on to " + rollOnForm.getProjName());
						rollOnDao.inactiveRollOnData(rollOnForm.getUserId());//update ROLL_ON ... set CURRENT=0 for this user
						//********* this is common to all employees being rolled on *******
						rollOnForm.setCreateDate(new Date());
						rollOnForm.setCurrent(Short.parseShort("1"));
						rollOnForm.setEsrqmId(esrMap);
						String notifiers = rollOnForm.getNotifiers();
						Client[] clientDto = clientDao.findByDynamicSelect("SELECT * FROM CLIENT C LEFT JOIN PROJ_CLIENT_MAP PCM ON C.ID=PCM.CLIENT_ID WHERE PCM.PROJ_ID=?", new Object[] { new Integer(rollOnForm.getProjId()) });
						if (clientDto.length > 0) rollOnForm.setClientName(clientDto[0].getName());
						if (rollOnForm.getChCodeId() > 0){
							ChargeCode chargeCode = chargeCodeDao.findByPrimaryKey(rollOnForm.getChCodeId());
							rollOnForm.setChargeCode(chargeCode.getChCode());
						}
						//********* data is set in the pojo... persist it  ****************
						rollOnForm.setRaisedBy(loginDto.getUserId());
						rollOnForm.setReportTm(PortalUtility.fromStringToTime(rollOnForm.getReportingTime()));
						if (rollOnForm.getId() > 0) rollOnForm.setId(0); //Primary Key constraint violation
						rollOnForm.setEmpId(rollOnForm.getUserId());
						RollOnPk insertedRollOnPk = rollOnDao.insert(rollOnForm);
						logger.debug("MULTIPLEROLLON : inserted data into ROLL_ON");
						if ((!(rollOnForm.isChCodeIdNull())) && rollOnForm.getChCodeId() > 0){
							PoDetailsModel.updatePoEmpMap(rollOnForm.getUserId());//update PO_EMP_MAP set CURRENT=0 for old charge-code
							logger.debug("MULTI ROLLON : updated PO_EMP_MAP set current=0 for emp_id=" + rollOnForm.getUserId());
							PoEmpMapDao poEmpMapDao = PoEmpMapDaoFactory.create(conn);
							PoEmpMap poEmpMapDto = new PoEmpMap();
							ChargeCode selectedChargeCode = chargeCodeDao.findByPrimaryKey(rollOnForm.getChCodeId());
							poEmpMapDto.setPoId(selectedChargeCode.getPoId());
							poEmpMapDto.setEmpId(rollOnForm.getUserId());
							poEmpMapDto.setRate(rollOnForm.getPerdiem());
							poEmpMapDto.setType(rollOnForm.getPaymentTerm());
							poEmpMapDto.setCurrency(rollOnForm.getCurrency());
							poEmpMapDto.setInactive(rollOnForm.getEmpInactiveInfo());
							poEmpMapDto.setCurrent((short) 1);
							PoEmpMapPk insertedRow = poEmpMapDao.insert(poEmpMapDto);
							logger.debug("MULTIPLE_ROLLON : for userId=" + rollOnForm.getUserId() + "inserted row in PO_EMP_MAP.. id =" + insertedRow.getId());
						} else{
							logger.error("************** MULTI ROLLON : logged-in userId=" + loginDto.getUserId() + " tried to do multi rollon for userId=" + rollOnForm.getUserId() + " " + " on project with id=" + rollOnForm.getProjId() + " charge code id received was null.....failed to insert into roll_on_proj_map roll_on_mgr_map..." + "****************");
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.chargecode.null"));
							result.setForwardName("success");
							return result;
						}
						if (rollOnForm.getProjId() > 0){
							RollOnProjMap rollOnProjMap = new RollOnProjMap();
							rollOnProjMap.setRollOnId(insertedRollOnPk.getId());
							rollOnProjMap.setProjId(rollOnForm.getProjId());
							rollOnProjMap.setProjLocId(rollOnForm.getProjectLocationId());
							rollOnProjectMapDao.insert(rollOnProjMap);
							logger.debug("MULTIPLEROLLON : inserted data into ROLL_ON_PROJ_MAP");
						}
						ProjectMapping projectMapping = new ProjectMapping();
						projectMapping.setProjectId(rollOnForm.getProjId());
						projectMapping.setUserId(rollOnForm.getEmpId()); //this may be confusing....
						projectMappingDao.insert(projectMapping);
						RollOnMgrMap rollOnMgrMap = new RollOnMgrMap();
						rollOnMgrMap.setRollOnId(insertedRollOnPk.getId());
						rollOnMgrMap.setMgrName(rollOnForm.getManagerName());
						rollOnMgrMap.setMgrEmail(rollOnForm.getMgrEmailId());
						rollOnMgrMap.setMgrDetail(rollOnForm.getMgrDetails()); //not mandatory
						rollOnMgrMap.setOnsiteMgr(rollOnForm.getOnSiteMgr()); //not mandatory
						rollOnMgrMap.setOnsiteMgrDetails(rollOnForm.getOnSiteMgrDetail()); //not mandatory
						rollOnMgrMap.setMgrPhoneNo(rollOnForm.getMgrPhoneNo());
						rollOnMgrMapDao.insert(rollOnMgrMap);
						logger.debug("MULTIPLEROLLON : inserted data into ROLL_ON_MGR_MAP");
						/*
						 * inserted data into required tables for one of the Employee being Rolled On
						 * now, Mail.TO : employee/UserId
						 *      Mail.CC : (above mentioned person's) HR_SPOC , RM
						 */
						ProfileInfo[] rolledOnEmp = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI WHERE PI.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { (rollOnForm.getUserId() == null) ? rollOnForm.getEmpId() : rollOnForm.getUserId() });
						rollOnForm.setReceiverEmailId(rolledOnEmp[0].getOfficalEmailId());// Mail.To = Employee being rolledOn
						/*
						 * if required .. use this...
						 * rollOnForm.setEmpId(rollOnForm.getUserId());//need to set the userId in empId .... maps to EMP_ID in ROLL_ON
						 */
						ProcessChain empProcessChain = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC  LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID WHERE ROLE_ID=(SELECT ROLE_ID FROM USER_ROLES UR LEFT JOIN USERS U ON U.ID=UR.USER_ID WHERE U.ID=?) AND MODULE_ID=18", new Object[] { loginDto.getUserId() })[0];
						Integer[] notifierUserIds = new ProcessEvaluator().notifiers(empProcessChain.getNotification(), (rollOnForm.getUserId() == null) ? rollOnForm.getEmpId() : rollOnForm.getUserId());
						ArrayList<String> mailid = new ArrayList<String>();
						String[] enteredMailIds = null;
						if (rollOnForm.getNotifiers() != null && rollOnForm.getNotifiers().length() > 0){
							if (rollOnForm.getNotifiers().length() == 1) rollOnForm.setNotifiers(rollOnForm.getNotifiers() + "@dikshatech.com");
							else rollOnForm.setNotifiers(rollOnForm.getNotifiers().replaceAll(",", "@dikshatech.com;") + "@dikshatech.com");
							enteredMailIds = rollOnForm.getNotifiers().split(";");
							mailid.addAll(Arrays.asList(enteredMailIds));
						}
						for (Integer eachNotifierId : notifierUserIds){
							Users notifier = usersDao.findByPrimaryKey(eachNotifierId);
							ProfileInfo notifierProfileInfo = profileInfoDao.findByPrimaryKey(notifier.getProfileId());
							mailid.add(notifierProfileInfo.getOfficalEmailId());
						}
						Levels empLevel = levelsDao.findByPrimaryKey(rolledOnEmp[0].getLevelId());
						Divison empDivison = divisonDao.findByPrimaryKey(empLevel.getDivisionId());
						rollOnForm.setEmpName(rolledOnEmp[0].getFirstName());
						rollOnForm.setLastName(rolledOnEmp[0].getLastName());
						rollOnForm.setEmpDiv(empDivison.getName());
						rollOnForm.setDesignation(empLevel.getDesignation());
						if (mailid.size() > 0){
							String[] stringArray = Arrays.copyOf(mailid.toArray(), mailid.toArray().length, String[].class);
							rollOnForm.setCCnotifierMailIds(stringArray);
							sendMail(rollOnForm, null, conn);
							logger.debug("ROLL_ON MULTPILEROLLON: sendMail(rollOnForm,null).... " + "Mail.To : Requester   Mail.CC : Requester's HRSPOC, R.M from processChain with Id=" + empProcessChain.getId());
						} else{
							sendMail(rollOnForm, null, conn);
							logger.debug("ROLL_ON MULTPILEROLLON : mail not sent to hrspoc/rm/notifiers ! ! ! pls check notifiers in the processchain with id=" + empProcessChain.getId());
						}
						/*logger.debug("ROLL_ON MULTPILEROLLON: preparing to send mail to notifiers");
						sendMailToNotifiers(rollOnForm);
						logger.debug("ROLL_ON MULTPILEROLLON: sent mail to notifiers");*/
						/*
						 * send this info to Docking Station
						 * */
						rollOnForm.setReceiverEmailId(null);
						rollOnForm.setNotifierName(" ");
						logger.debug("ROLL_ON MULTIPLEROLLON: begin sendMail(rollOnForm,INFO)....for DockingStation");
						portalMail = sendMail(rollOnForm, null, conn);//portalMail = sendMail(rollOnForm, "INFO");
						logger.debug("ROLL_ON MULTIPLEROLLON: begin sendMail(rollOnForm,INFO)....for DockingStation");
						String message_body = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
						rollOnForm.setRaisedBy(loginDto.getUserId());
						rollOnForm.setMessageBody(message_body);
						rollOnForm.setEsrqmId(reqMapPK.getId());
						rollOnForm.setNotifiers(notifiers); //reset the notifiers... to what was received in the FORM
						rollOnDao1.update(insertedRollOnPk, rollOnForm);
						InboxModel inboxModel = new InboxModel();
						inbox = inboxModel.sendToDockingStation(reqMapPK.getId(), insertedRollOnPk.getId(), portalMail.getMailSubject(), Status.ROLLEDON);
						inboxModel.notify(reqMapPK.getId(), inbox);
						String updateInboxQuery = "UPDATE INBOX SET ASSIGNED_TO=0 WHERE ESR_MAP_ID=" + inbox.getEsrMapId();
						InboxDaoFactory.create(conn).executeUpdate(updateInboxQuery);
						logger.debug("MULTIPLEROLLON : inserted into INBOX.... docking station");
						conn.commit();
					}
					// MULTIPLEROLLON ends here
					break;
				default:
					logger.error("ROLL_ON DEFAULT: received a ROLL_ON request...which was neither SINGLEROLLON nor MULTIROLLON....");
					logger.error("ROLL_ON DEFAULT: event occurred on " + new Date() + " request was raised by user_id=" + loginDto.getUserId());
					break;
			}
		} catch (Exception ex){
			logger.error("ROLL_ON FAILED -->  ", ex);
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		Login loginDto = Login.getLogin(request);
		ActionResult result = new ActionResult();
		RollOn rollOnForm = (RollOn) form;
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			rollOnForm.setUserId(form.getUserId());//to remove the "userId" conflict b/w PortalForm and RollOnForm 
			rollOnForm.setEmpId(rollOnForm.getUserId());
			RollOnDao rollOnDao = RollOnDaoFactory.create(conn);
			RollOnProjMapDao projMapDao = RollOnProjMapDaoFactory.create(conn);
			ProjectMappingDao prjMappingDao = ProjectMappingDaoFactory.create(conn);
			RollOnMgrMapDao mgrMapDao = RollOnMgrMapDaoFactory.create(conn);
			ClientDao clientDao = ClientDaoFactory.create(conn);
			ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create(conn);
			ProjectDao projectDao = ProjectDaoFactory.create(conn);
			ProcessChainDao processChainDao = ProcessChainDaoFactory.create(conn);
			LevelsDao levelsDao = LevelsDaoFactory.create(conn);
			DivisonDao divisonDao = DivisonDaoFactory.create(conn);
			Inbox inbox = new Inbox();
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail portalMail = new PortalMail();
			ProjLocationsDao projLocationsDao = ProjLocationsDaoFactory.create(conn);
			ProcessChain empProcessChain = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC  LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID WHERE" + " ROLE_ID=(SELECT ROLE_ID FROM USER_ROLES UR LEFT JOIN USERS U ON U.ID=UR.USER_ID WHERE U.ID=?) " + "AND MODULE_ID=18", new Object[] { loginDto.getUserId() })[0];
			logger.debug("ROLL_ON _ UPDATE : employee being rolledOn with userId=" + rollOnForm.getUserId() + "" + " is associated with processchain, pc_id=" + empProcessChain.getId());
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			UsersDao usersDao = UsersDaoFactory.create(conn);
			RollOn selectedRollOnDto = rollOnDao.findByPrimaryKey(rollOnForm.getId());
			Time t = PortalUtility.fromStringToTime(rollOnForm.getReportingTime());
			selectedRollOnDto.setReportTm(t);
			selectedRollOnDto.setStartDate(rollOnForm.getStartDate());
			selectedRollOnDto.setEndDate(rollOnForm.getEndDate());
			selectedRollOnDto.setReportDt(rollOnForm.getReportDt());
			selectedRollOnDto.setPerdiem(rollOnForm.getPerdiem());
			selectedRollOnDto.setPaymentTerm(rollOnForm.getPaymentTerm());
			selectedRollOnDto.setCurrency(rollOnForm.getCurrency());
			selectedRollOnDto.setMgrEmailId(rollOnForm.getMgrEmailId());
			Users usersInfoEmp = usersDao.findByPrimaryKey(rollOnForm.getUserId());
			ProfileInfo profileInfoEmployee = profileInfoDao.findByPrimaryKey(usersInfoEmp.getProfileId());
			selectedRollOnDto.setEmpName(profileInfoEmployee.getFirstName());
			selectedRollOnDto.setLastName(profileInfoEmployee.getLastName());
			selectedRollOnDto.setReceiverEmailId(profileInfoEmployee.getOfficalEmailId());
			Levels levelsDto = levelsDao.findByDynamicSelect("SELECT * FROM LEVELS L JOIN PROFILE_INFO PI ON PI.LEVEL_ID=L.ID WHERE PI.ID=?", new Object[] { new Integer(profileInfoEmployee.getId()) })[0];
			selectedRollOnDto.setDesignation(levelsDto.getDesignation());
			selectedRollOnDto.setEmpDiv(divisonDao.findByPrimaryKey(levelsDto.getDivisionId()).getName());
			// Update Record for RollOn Project Map
			ProjectMapping prjMappingRollback = null;
			ProjectMapping prjMapping = new ProjectMapping();
			RollOnProjMap rollOnProjMap = null;
			RollOnProjMap rollOnProjMapRollback = null;
			if (selectedRollOnDto.getProjId() != rollOnForm.getProjId()){
				rollOnProjMap = projMapDao.findByDynamicWhere("ROLL_ON_ID=?", new Object[] { selectedRollOnDto.getId() })[0];
				rollOnProjMapRollback = projMapDao.findByDynamicWhere("ROLL_ON_ID=?", new Object[] { selectedRollOnDto.getId() })[0];
				rollOnProjMap.setProjId(rollOnForm.getProjId());
				//rollOnProjMap.setProjLocId(Integer.parseInt(request.getParameter("projLocId")));    why request parameter??
				rollOnProjMap.setProjLocId(rollOnForm.getProjectLocationId());
				try{
					projMapDao.update(new RollOnProjMapPk(rollOnProjMap.getId()), rollOnProjMap);
				} catch (Exception e){
					logger.error(e);
					e.printStackTrace();
					throw new Exception();
				}
				try{
					ProjectMapping[] latestRolledOn = prjMappingDao.findByDynamicSelect("SELECT * FROM PROJECT_MAPPING PM WHERE PM.USER_ID=? ORDER BY ID DESC", new Object[] { new Integer(rollOnForm.getUserId()) });
					if (latestRolledOn != null && latestRolledOn.length > 0){
						prjMappingRollback = latestRolledOn[0];
						prjMapping.setId(latestRolledOn[0].getId());
						prjMapping.setProjectId(rollOnForm.getProjId());
						prjMapping.setUserId(rollOnForm.getEmpId());
						prjMappingDao.update(new ProjectMappingPk(latestRolledOn[0].getId()), prjMapping);
					}
				} catch (Exception e){
					projMapDao.update(new RollOnProjMapPk(rollOnProjMap.getId()), rollOnProjMapRollback);
					logger.error(e);
					e.printStackTrace();
					throw new Exception();
				}
				Client[] clientDto = clientDao.findByDynamicSelect("SELECT * FROM CLIENT C LEFT JOIN PROJ_CLIENT_MAP PCM ON C.ID=PCM.CLIENT_ID WHERE PCM.PROJ_ID=?", new Object[] { new Integer(rollOnForm.getProjId()) });
				if (clientDto.length > 0){
					rollOnForm.setClientName(clientDto[0].getName());
					selectedRollOnDto.setClientName(clientDto[0].getName());
				}
				Project proj = projectDao.findByPrimaryKey(rollOnForm.getProjId());
				selectedRollOnDto.setProjId(proj.getId());
				selectedRollOnDto.setProjName(proj.getName());
				ProjLocationsDao pLocationsDao = ProjLocationsDaoFactory.create(conn);
				ProjLocations[] locations = pLocationsDao.findWhereProjIdEquals(proj.getId());
				selectedRollOnDto.setProjLocs(locations);
			}
			if (rollOnForm.getChCodeId() > 0){
				ChargeCode chargeCode = chargeCodeDao.findByPrimaryKey(rollOnForm.getChCodeId());
				selectedRollOnDto.setChargeCode(chargeCode.getChCode());
				selectedRollOnDto.setChargeCodeTitle(chargeCode.getChCodeName());
				/*
				 *get the last charge-code the user is associated with & update its PO_ID
				 *this is required to update the no_of_resources on a particular charge-code 
				 * */
				final int empId = (rollOnForm.getUserId() == 0) ? rollOnForm.getEmpId() : rollOnForm.getUserId();
				String selectLastChargeCode = "SELECT * FROM PO_EMP_MAP WHERE EMP_ID=? AND CURRENT=1";
				PoEmpMapDao poEmpMapDao = PoEmpMapDaoFactory.create(conn);
				PoEmpMap fetchedPoEmpMap = poEmpMapDao.findByDynamicSelect(selectLastChargeCode, new Object[] { empId })[0];
				logger.debug("ROLL_ON_UPDATE fetched PO_EMP_MAP row with id=" + fetchedPoEmpMap.getId() + " for userId=" + empId + " at " + new Date());
				fetchedPoEmpMap.setPoId(chargeCode.getPoId());
				poEmpMapDao.update(new PoEmpMapPk(fetchedPoEmpMap.getId()), fetchedPoEmpMap);
			} else{
				rollOnForm.setChCodeIdNull(true);
				selectedRollOnDto.setChCodeIdNull(true);
			}
			/*ChargeCode chargeCodeDto[] = chargeCodeDao.findByDynamicSelect("SELECT * FROM CHARGE_CODE CC LEFT JOIN PO_EMP_MAP PEM ON CC.ID=PEM.PO_ID WHERE PEM.EMP_ID=?", new Object[ ]
			                                                                                                                                                          				{ new Integer(rollOnForm.getUserId()) });
			if (chargeCodeDto.length > 0){
				selectedRollOnDto.setChargeCode(chargeCodeDto[0].getChCode());
				selectedRollOnDto.setChargeCodeTitle(chargeCodeDto[0].getChCodeName());
			}*/
			// Update Record for RollOn Manager Map
			if (rollOnForm.getManagerName() != null){
				RollOnMgrMap tempRollOnMgrMap = new RollOnMgrMap();
				RollOnMgrMap rollOnMgrMap[] = null;
				try{
					rollOnMgrMap = mgrMapDao.findWhereRollOnIdEquals(selectedRollOnDto.getId());
					if (rollOnMgrMap.length > 0){
						rollOnMgrMap[0].setMgrEmail(rollOnForm.getMgrEmailId());
						rollOnMgrMap[0].setMgrName(rollOnForm.getManagerName());
						rollOnMgrMap[0].setMgrDetail(rollOnForm.getMgrDetails());
						rollOnMgrMap[0].setOnsiteMgr(rollOnForm.getOnSiteMgr());
						rollOnMgrMap[0].setOnsiteMgrDetails(rollOnForm.getOnSiteMgrDetail());
						mgrMapDao.update(new RollOnMgrMapPk(rollOnMgrMap[0].getId()), rollOnMgrMap[0]);
					} else{
						tempRollOnMgrMap.setRollOnId(selectedRollOnDto.getId());
						tempRollOnMgrMap.setMgrEmail(rollOnForm.getMgrEmailId());
						tempRollOnMgrMap.setMgrName(rollOnForm.getManagerName());
						tempRollOnMgrMap.setMgrDetail(rollOnForm.getMgrDetails());
						tempRollOnMgrMap.setOnsiteMgr(rollOnForm.getOnSiteMgr());
						tempRollOnMgrMap.setOnsiteMgrDetails(rollOnForm.getOnSiteMgrDetail());
						mgrMapDao.insert(tempRollOnMgrMap);
					}
				} catch (Exception e){
					prjMappingDao.update(new ProjectMappingPk(prjMapping.getId()), prjMappingRollback);
					projMapDao.update(new RollOnProjMapPk(rollOnProjMap.getId()), rollOnProjMapRollback);
					logger.error(e);
					e.printStackTrace();
					throw new Exception();
				}
			}
			//query for charge code
			/*ChargeCode chargeCode=chargeCodeDao.findByPrimaryKey(rollOnForm.getChCodeId());
			selectedRollOnDto.setChargeCode(chargeCode.getChCode());
			selectedRollOnDto.setChargeCodeTitle(chargeCode.getChCodeName());*/
			//query for project location
			ProjLocations projLocations[] = projLocationsDao.findByProject(rollOnForm.getProjId());
			if (projLocations != null && projLocations.length > 0){
				selectedRollOnDto.setClientLocation(projLocations[0].getName());
				selectedRollOnDto.setClientOfficeAddress(projLocations[0].getAddress());
			}
			int tempTravelReqFlag = rollOnForm.getTravelReqFlag();
			selectedRollOnDto.setTravelReqFlag(0);
			final String notifiersFromTextbox = rollOnForm.getNotifiers();
			selectedRollOnDto.setOnSiteMgr(rollOnForm.getOnSiteMgr());
			selectedRollOnDto.setOnSiteMgrDetail(rollOnForm.getOnSiteMgrDetail());
			/*String[] enteredMailIds = null;
			if (rollOnForm.getNotifiers() != null && rollOnForm.getNotifiers().length() > 0){
				if (rollOnForm.getNotifiers().length() == 1) rollOnForm.setNotifiers(rollOnForm.getNotifiers() + "@dikshatech.com");
				else rollOnForm.setNotifiers(rollOnForm.getNotifiers().replaceAll(",", "@dikshatech.com;") + "@dikshatech.com");
				enteredMailIds = rollOnForm.getNotifiers().split(";");
			}
			
			ArrayList<String>mailIds = new ArrayList<String>();
			//	sendMailToNotifiers(selectedRollOnDto);//textbox
			
			
			 *SINGLE_ROLLON
			 *MULTIPLE_ROLLON
			 *ROLL_ON update..........uses employee's processChain to obtain the hrspoc and rm 
			 
			
			Integer[] notifierIds = new ProcessEvaluator().notifiers(empProcessChain.getNotification(),(rollOnForm.getUserId()==null)?rollOnForm.getEmpId():rollOnForm.getUserId());
			for(Integer eachNotifierId : notifierIds){
				ProfileInfo notifierInfo = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(eachNotifierId).getProfileId());
				mailIds.add(notifierInfo.getOfficalEmailId());
			}
			
			if(mailIds.size()>0){
				String[] stringArray = Arrays.copyOf(mailIds.toArray(), mailIds.toArray().length, String[].class);
				selectedRollOnDto.setCCnotifierMailIds(stringArray);
				selectedRollOnDto.setReceiverEmailId(profileInfoEmployee.getOfficalEmailId());
				selectedRollOnDto.setTravelReqFlag(tempTravelReqFlag);//HR_SPOC RM will receive this			
				sendMail(selectedRollOnDto, null);
				logger.debug("ROLLON _ UPDATE : sent mail to hrspoc and rm of userId="+rollOnForm.getUserId()+" " +
						"mailIds of hrspoc/rm were picked from processChain(notifiers) with Id="+empProcessChain.getId());
			}else{
				logger.debug("ROLLON_UPDATE : found no hrspoc and rm from process-chain with id="+empProcessChain.getId()+
						"...sending mail to employee being rolled on..his userId="+rollOnForm.getUserId());
				selectedRollOnDto.setReceiverEmailId(profileInfoEmployee.getOfficalEmailId());
				selectedRollOnDto.setTravelReqFlag(tempTravelReqFlag);//HR_SPOC RM were supposed to receive this			
				sendMail(selectedRollOnDto, null);
			}*/
			selectedRollOnDto.setNotifiers(notifiersFromTextbox);
			selectedRollOnDto.setTravelReqFlag(tempTravelReqFlag);
			selectedRollOnDto.setChCodeId(rollOnForm.getChCodeId());//was missed
			selectedRollOnDto.setComments(rollOnForm.getComments());
			selectedRollOnDto.setProjectType(rollOnForm.getProjectType());
			rollOnDao.update(new RollOnPk(selectedRollOnDto.getId()), selectedRollOnDto);
			savePerdiemMasterData(selectedRollOnDto.getEmpId(), selectedRollOnDto.getPerdiem(), selectedRollOnDto.getCurrency(), selectedRollOnDto.getStartDate(), selectedRollOnDto.getEndDate(), conn);

			// sending to docking station
			selectedRollOnDto.setReceiverEmailId(null);
			portalMail = sendMail(selectedRollOnDto, null, conn);//was "INFO"
			String message_body = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
			selectedRollOnDto.setRaisedBy(loginDto.getUserId());
			selectedRollOnDto.setMessageBody(message_body);
			rollOnDao.update(new RollOnPk(selectedRollOnDto.getId()), selectedRollOnDto);
			InboxModel inboxModel = new InboxModel();
			inbox = inboxModel.sendToDockingStation(selectedRollOnDto.getEsrqmId(), selectedRollOnDto.getId(), portalMail.getMailSubject(), Status.ROLLEDON);
			inboxModel.notify(selectedRollOnDto.getEsrqmId(), inbox);
			String updateInboxQuery = "UPDATE INBOX SET ASSIGNED_TO=0 WHERE ESR_MAP_ID=" + inbox.getEsrMapId();
			InboxDaoFactory.create(conn).executeUpdate(updateInboxQuery);
			conn.commit();
		} catch (Exception e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failed.update"));
			logger.error(e);
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	PortalMail sendMail(RollOn rollon, String Info, Connection conn) {
		PortalMail portalMail = new PortalMail();
		try{
			String mailSubject = null;
			UsersDao usersDao = UsersDaoFactory.create();
			Users rolledOnUser = usersDao.findByPrimaryKey(rollon.getEmpId());
			portalMail.setEmpId(new Integer(rolledOnUser.getEmpId()).toString());
			portalMail.setEmpFname(rollon.getEmpName());
			portalMail.setEmpLName(rollon.getLastName());
			portalMail.setEmpDesignation(rollon.getDesignation());
			portalMail.setEmpDivision(rollon.getEmpDiv());
			if (rollon.getClientLocation() != null && rollon.getClientLocation().length() > 0) portalMail.setClientLocation(rollon.getClientLocation());
			else portalMail.setClientLocation("Diksha");
			if (rollon.getClientOfficeAddress() != null && rollon.getClientOfficeAddress().length() > 0) portalMail.setClientAddress(rollon.getClientOfficeAddress());
			else portalMail.setClientAddress("-");
			if (rollon.getClientName() != null && rollon.getClientName().length() > 0) portalMail.setClientName(rollon.getClientName());
			else portalMail.setClientName("Diksha");
			if (rollon.getChargeCode() != null && rollon.getChargeCode().length() > 0) portalMail.setChargecode(rollon.getChargeCode());
			else portalMail.setChargecode("N.A");
			if (rollon.getChargeCodeTitle() != null && rollon.getChargeCodeTitle().length() > 0) portalMail.setChargeCodeTitle(rollon.getChargeCodeTitle());
			else portalMail.setChargeCodeTitle("N.A");
			portalMail.setProjectId(rollon.getProjId());
			portalMail.setProjectName(rollon.getProjName());
			if (rollon.getManagerName() != null && rollon.getManagerName().length() > 0) portalMail.setRepoMngrAtClient(" <strong>Client Reporting/Contact Person</strong>&nbsp;&nbsp;:&nbsp;&nbsp;" + rollon.getManagerName() + "<br />");
			else portalMail.setRepoMngrAtClient("");
			if (rollon.getOnSiteMgr() != null & rollon.getOnSiteMgr().length() > 0){
				portalMail.setDikshaReportingManager("<br /><strong>Diksha Reporting/contact Person</strong>&nbsp;&nbsp;:&nbsp;&nbsp;" + rollon.getOnSiteMgr() + "<br />");
			} else{
				portalMail.setDikshaReportingManager("");
			}
			if (rollon.getOnSiteMgrDetail() != null & rollon.getOnSiteMgrDetail().length() > 0){
				portalMail.setDikshaRmMailId("Details&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;" + rollon.getOnSiteMgrDetail() + "<br /><br />");
			} else{
				portalMail.setDikshaRmMailId("");
			}
			if (PortalUtility.formatTime(rollon.getReportDt()) == null){
				portalMail.setReportingDate("N.A");
				portalMail.setReportingTm("N.A");
			} else{
				portalMail.setReportingDate(PortalUtility.formatDateddMMyyyy(rollon.getReportDt()));
				portalMail.setReportingTm(PortalUtility.formatTime(rollon.getReportTm()));
			}
			if (rollon.getMgrPhoneNo() != null && rollon.getMgrPhoneNo().length() > 0){
				portalMail.setMgrPhoneNo("Phone No&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;" + rollon.getMgrPhoneNo() + "<br />");
			} else{
				portalMail.setMgrPhoneNo("");
			}
			if (PortalUtility.formatTime(rollon.getCreateDate()) == null) portalMail.setRollOnDate("N.A");
			else portalMail.setRollOnDate(PortalUtility.formatTime(rollon.getCreateDate()));
			if (rollon.getMgrEmailId() != null && rollon.getMgrEmailId().length() > 0) portalMail.setRepoMngrEmailId("Email ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;" + rollon.getMgrEmailId() + "<br />");
			else portalMail.setRepoMngrEmailId("");
			if ("INFO".equalsIgnoreCase(Info)){
				if (rollon.getReceiverEmailId() != null && rollon.getReceiverEmailId().length() > 0){
					String[] notifierMailIds = rollon.getReceiverEmailId().split(";");
					if (notifierMailIds.length > 1) //more than one mail id is found
					portalMail.setAllReceipientMailId(notifierMailIds);
					else portalMail.setRecipientMailId(rollon.getReceiverEmailId());
				}
				//portalMail.setEmployeeName(rollon.getNotifierName()); not supported anymore.. templates changed
				mailSubject = "Diksha: You have Rolled-on " + rollon.getEmpName() + " to " + rollon.getProjName() + " Id[" + rollon.getProjId() + "]";
				portalMail.setTemplateName(MailSettings.ROLL_ON_INFO);
				portalMail.setMailSubject(mailSubject);
			} else{
				portalMail.setRecipientMailId(rollon.getReceiverEmailId());//if not INFO...Mail.TO : RequesterId
				//during ROLL_ON, if employee has to TRAVEL...this flag variable will be set to TRUE if this option is checked from UI
				if (rollon.getTravelReqFlag() == 1) portalMail.setRollOnTravelInformation("<br /><br /><strong>Travel Details : Travel arragements are requested.<br />");
				else portalMail.setRollOnTravelInformation("");
				mailSubject = "Diksha: Roll on Details";
				portalMail.setTemplateName(MailSettings.ROLL_ON_EMP);
				portalMail.setMailSubject(mailSubject);
				portalMail.setAllReceipientcCMailId(rollon.getCCnotifierMailIds()); //Mail.TO : Employee   Mail.CC : Employee's HR_SPOC , Employee's R.M
			}
			if (rollon.getReceiverEmailId() != null && rollon.getReceiverEmailId().contains("@")){
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}
			return portalMail;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void savePerdiemMasterData(int userId, String perdiem, String currency, Date startDate, Date endDate, Connection conn) {
		try{
			PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create(conn);
			if (userId == 0) return;
			PerdiemMasterData fetchedPerdiemDto = null, fetchedPerdiemDtos[] = perdiemMasterDao.findWhereUserIdEquals(userId);
			if (fetchedPerdiemDtos != null && fetchedPerdiemDtos.length > 0){
				fetchedPerdiemDto = fetchedPerdiemDtos[0];
			} else{
				fetchedPerdiemDto = new PerdiemMasterData();
			}
			fetchedPerdiemDto.setPerdiem(DesEncrypterDecrypter.getInstance().encrypt(perdiem));
			fetchedPerdiemDto.setPerdiemFrom(startDate);
			fetchedPerdiemDto.setUserId(userId);
			fetchedPerdiemDto.setPerdiemTo(endDate);
			Currency[] currencies = CurrencyDaoFactory.create(conn).findWhereAbbrevationEquals(currency);
			if (currencies != null && currencies.length > 0 && currencies[0] != null) fetchedPerdiemDto.setCurrencyType(currencies[0].getId() + "");
			if (fetchedPerdiemDto.getId() > 0){
				if (perdiem == null || startDate == null || Double.parseDouble(perdiem) == 0) perdiemMasterDao.delete(fetchedPerdiemDto.createPk());
				else perdiemMasterDao.update(fetchedPerdiemDto.createPk(), fetchedPerdiemDto);
			} else{
				if (perdiem != null && startDate != null) perdiemMasterDao.insert(fetchedPerdiemDto);
			}
		} catch (Exception e){}
	}

	/*public void sendMailToNotifiers(RollOn rollOnForm) {
		try{
			// if there are mail_ids present in the textbox , parse and append @dikshatech.com
			String[] enteredMailIds = null;
			if (rollOnForm.getNotifiers() != null && rollOnForm.getNotifiers().length() > 0){
				if (rollOnForm.getNotifiers().length() == 1) rollOnForm.setNotifiers(rollOnForm.getNotifiers() + "@dikshatech.com");
				else rollOnForm.setNotifiers(rollOnForm.getNotifiers().replaceAll(",", "@dikshatech.com;") + "@dikshatech.com");
				enteredMailIds = rollOnForm.getNotifiers().split(";");
			}
			//send notification mail to those ids present in the textbox
			if (enteredMailIds != null){
				// these mailIds were ignored, as they were present in the notifiers list in the processChain
				rollOnForm.setReceiverEmailId(rollOnForm.getNotifiers());
				rollOnForm.setNotifierName(" ");
				sendMail(rollOnForm, "INFO");
			}
		} catch (Exception ex){
			logger.error("ROLL_ON : could not send mail to notifiers");
		}
	}*/
}
