package com.dikshatech.portal.models;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.BirthdaysBean;
import com.dikshatech.beans.DateOfJoining;
import com.dikshatech.beans.FestivalAndBirthdayWishesBean;
import com.dikshatech.beans.ProfileBean;
import com.dikshatech.common.utils.BirthdayNotifier;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.ExportType;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.jasper.JGenerator;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.ContactTypeDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ExitEmployeeDao;
import com.dikshatech.portal.dao.LeaveBalanceDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.LoginDao;
import com.dikshatech.portal.dao.PoDetailsDao;
import com.dikshatech.portal.dao.PoEmpMapDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjLocationsDao;
import com.dikshatech.portal.dao.ProjectMappingDao;
import com.dikshatech.portal.dao.RollOnDao;
import com.dikshatech.portal.dao.RollOnProjMapDao;
import com.dikshatech.portal.dao.TravellerTypeDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.ContactType;
import com.dikshatech.portal.dto.ContactTypePk;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqHistory;
import com.dikshatech.portal.dto.EmpSerReqHistoryPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.ExitEmployee;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.LeaveBalancePk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.LoginPk;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.PoEmpMap;
import com.dikshatech.portal.dto.PoEmpMapPk;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.ProjLocations;
import com.dikshatech.portal.dto.ProjectMapping;
import com.dikshatech.portal.dto.ProjectMappingPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.RollOnPk;
import com.dikshatech.portal.dto.RollOnProjMap;
import com.dikshatech.portal.dto.RollOnProjMapPk;
import com.dikshatech.portal.dto.TravellerType;
import com.dikshatech.portal.dto.TravellerTypePk;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.UserRolesPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectMappingDaoException;
import com.dikshatech.portal.exceptions.RollOnDaoException;
import com.dikshatech.portal.exceptions.RollOnProjMapDaoException;
import com.dikshatech.portal.exceptions.UserRolesDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.ContactTypeDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqHistoryDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ExitEmployeeDaoFactory;
import com.dikshatech.portal.factory.FestivalWishesDaoFactory;
import com.dikshatech.portal.factory.LeaveBalanceDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.LoginDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.PoEmpMapDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjLocationsDaoFactory;
import com.dikshatech.portal.factory.ProjectMappingDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.factory.RollOnProjMapDaoFactory;
import com.dikshatech.portal.factory.TravellerTypeDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.timer.EmployeeDateOfConfirmation;

public class ProfileInfoModel extends ActionMethods {

	private static Logger		logger				= LoggerUtil.getLogger(ProfileInfoModel.class);
	//private static final String selectUsers = "SELECT * FROM PROFILE_INFO p LEFT JOIN USERS u ON p.ID=u.PROFILE_ID WHERE DAY(p.DOB) BETWEEN DAY(CURRENT_DATE()-(SELECT DAYOFWEEK(CURRENT_DATE())-1)) AND DAY((CURRENT_DATE()+(7 -(SELECT DAYOFWEEK(CURRENT_DATE()))))) AND MONTH(p.DOB)=MONTH(CURRENT_DATE()) AND u.STATUS=0 ";
	private static final String	selectUsers			= " SELECT *  FROM PROFILE_INFO p  LEFT JOIN USERS u ON p.ID=u.PROFILE_ID  WHERE " + "(1 =  (FLOOR(DATEDIFF(DATE_ADD(DATE(NOW()),INTERVAL  6 DAY),p.DOB) / 365.25)) - (FLOOR(DATEDIFF(DATE(NOW()),DATE_ADD(DATE(p.DOB),INTERVAL 1 DAY))" + "/ 365.25))) AND u.ID NOT IN (1, 2, 3) AND u.STATUS=0 ORDER BY MONTH(p.DOB),DAY(p.DOB)";
	private static final String	dojUsers			= "SELECT *  FROM PROFILE_INFO p  LEFT JOIN USERS u ON p.ID=u.PROFILE_ID WHERE (p.DATE_OF_JOINING=Date(now()) and p.DATE_OF_JOINING=Date(now()) || DATE_OF_JOINING=DATE_ADD(Date(now()), INTERVAL 1 DAY)) AND u.STATUS=0 AND u.STATUS IS NOT NULL";
	private static String		CONFIRMATION_LETTER	= "confirmationLetter.jrxml";

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		ProfileInfo profileInfo = (ProfileInfo) form;
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProfileInfoPk profileInfoPk = new ProfileInfoPk();
		profileInfoPk.setId(profileInfo.getId());
		try{
			// profile info
			profileInfoDao.delete(profileInfoPk);
		} catch (Exception e){
			logger.info("Failed to delete Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// Login logonForm = (Login) form;
		ActionResult result = null;
		ProfileInfo profileDto = (ProfileInfo) form;
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		ExitEmployeeDao exitEmployeeDao = ExitEmployeeDaoFactory.create();
		switch (ActionMethods.ExecuteTypes.getValue(form.geteType())) {
			case BIRTHDAY:{
				profileDto = (ProfileInfo) form;
				result = new ActionResult();
				UsersDao userDao = UsersDaoFactory.create();
				Login login = (Login) request.getSession(false).getAttribute("login");
				MailGenerator mailGenerator = new MailGenerator();
				PortalMail pMail = new PortalMail();
				try{
					// Users bithdayUsers=userDao.findByPrimaryKey(userDto.getId());
					Users loginUsers = userDao.findByPrimaryKey(login.getUserId());
					ProfileInfo bithdayProfile = profielInfoDao.findByPrimaryKey(profileDto.getId());
					ProfileInfo loginProfile = profielInfoDao.findByPrimaryKey(loginUsers.getProfileId());
					pMail.setFromMailId(loginProfile.getOfficalEmailId());
					pMail.setCandidateName(bithdayProfile.getFirstName());
					pMail.setEmployeeName(loginProfile.getFirstName() + " " + loginProfile.getLastName());
					pMail.setComments(profileDto.getMessage());
					pMail.setMailSubject("Birthday Wishes");
					BirthdayNotifier bn = new BirthdayNotifier();
					int num = bn.generateRandomNumber();
					if (num == 0){
						num = 10;
					}
					pMail.setRandom(num);
					pMail.setRecipientMailId(bithdayProfile.getOfficalEmailId());
					pMail.setTemplateName(MailSettings.BIRTHDAY_WISHES);
					mailGenerator.invoker(pMail);
					result.setForwardName(ActionResult.SUCCESS);
				} catch (UsersDaoException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProfileInfoDaoException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AddressException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case UPDATERM:{
				try{
					Login login = Login.getLogin(request);
					if (profileDto.getUserList() != null) logger.info("Changing Reporting Manager from " + profileDto.getUserId() + " to " + profileDto.getId() + " for " + Arrays.asList(profileDto.getUserList()) + " is triggering by :" + ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + login.getUserId() + ")");
					else logger.info("Changing Reporting Manager from " + profileDto.getUserId() + " to " + profileDto.getId() + " is triggering by :" + ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + login.getUserId() + ")");
					profileDto = (ProfileInfo) form;
					result = new ActionResult();
					Integer oldRMId = profileDto.getUserId();
					Integer newRMId = profileDto.getId();
					Users newRM = usersDao.findByPrimaryKey(newRMId);
					Users oldRM = usersDao.findByPrimaryKey(oldRMId);
					ProfileInfo newRMProfile = profielInfoDao.findByPrimaryKey(newRM.getProfileId());
					ProfileInfo oldRMProfile = profielInfoDao.findByPrimaryKey(oldRM.getProfileId());
					ProfileInfo[] userProfile = null;
					ExitEmployee[] eeUm = null;
					if (profileDto.getUserList() == null){
						userProfile = profielInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE REPORTING_MGR=?", new Object[] { oldRMId });
					} else{
						userProfile = profielInfoDao.findByDynamicSelect("SELECT PF.* FROM PROFILE_INFO PF JOIN USERS U ON U.PROFILE_ID=PF.ID AND U.ID IN (" + ModelUtiility.getCommaSeparetedValues(profileDto.getUserList()) + ")", new Object[] {});
					}
					List<Integer> profileList = new ArrayList<Integer>();
					for (ProfileInfo profile : userProfile){
						try{
						
							Users SPOC = usersDao.findByPrimaryKey(profile.getHrSpoc());
							ProfileInfo userHRProfile = profielInfoDao.findByPrimaryKey(SPOC.getProfileId());
							sendRmChangedNotificationMail(newRM, newRMProfile, oldRM, oldRMProfile, userHRProfile, profile);
							profileList.add(profile.getId());
						} catch (Exception e){
							logger.error("unble to change Reporting manager from " + oldRM.getId() + " to " + newRM.getId() + " for " + profile.getFirstName() + " " + profile.getLastName(), e);
						}
					}
					if (profileList.size() > 0){
						String sql = "UPDATE PROFILE_INFO  SET REPORTING_MGR=?  WHERE REPORTING_MGR=? AND ID IN (" + ModelUtiility.getCommaSeparetedValues(profileList) + ")";
						profielInfoDao.findByDynamicUpdate(sql, new Object[] { new Integer(newRMId), new Integer(oldRMId) });
						
						for (ProfileInfo eachProfile :userProfile){
						 		int a =eachProfile.getId();
						 		eeUm = exitEmployeeDao.findByDynamicSelectExitEmployee("select EXIT_ID from EXIT_EMP_USERS_MAP EP  left join EXIT_EMPLOYEE EE on  EE.ID=EP.EXIT_ID left join USERS U on  U.ID = EE.USER_ID where EP.TYPE = 1   and U.PROFILE_ID = ("+a+") ",new Object[] {});
								}
					
						for(ExitEmployee eUm:eeUm)
						{
						String sql_eeum = "UPDATE EXIT_EMP_USERS_MAP   SET USER_ID=?   WHERE USER_ID=?  AND TYPE = 1 AND EXIT_ID =("+eUm.getId()+")";
						profielInfoDao.findByDynamicUpdate(sql_eeum, new Object[] { new Integer(newRMId), new Integer(oldRMId) });
					}}
					result.setForwardName(ActionResult.SUCCESS);
				} catch (Exception e){
					e.printStackTrace();
				}
				break;
			}
			case UPDATEHRSPOC:{
				try{
					Login login = Login.getLogin(request);
					if (profileDto.getUserList() != null) logger.info("Changing HR Spoc from " + profileDto.getUserId() + " to " + profileDto.getId() + " for " + Arrays.asList(profileDto.getUserList()) + " is triggering by :" + ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + login.getUserId() + ")");
					else logger.info("Changing HR Spoc from " + profileDto.getUserId() + " to " + profileDto.getId() + " is triggering by :" + ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + login.getUserId() + ")");
					profileDto = (ProfileInfo) form;
					result = new ActionResult();
					Integer oldhrId = profileDto.getUserId();
					Integer newhrId = profileDto.getId();
					Users newhr = usersDao.findByPrimaryKey(newhrId);
					Users oldhr = usersDao.findByPrimaryKey(oldhrId);
					ProfileInfo newhrProfile = profielInfoDao.findByPrimaryKey(newhr.getProfileId());
					ProfileInfo oldhrProfile = profielInfoDao.findByPrimaryKey(oldhr.getProfileId());
					ProfileInfo[] userProfile = null;
					if (profileDto.getUserList() == null){
						userProfile = profielInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE HR_SPOC=?", new Object[] { oldhrId });
					} else{
						userProfile = profielInfoDao.findByDynamicSelect("SELECT PF.* FROM PROFILE_INFO PF JOIN USERS U ON U.PROFILE_ID=PF.ID AND U.ID IN (" + ModelUtiility.getCommaSeparetedValues(profileDto.getUserList()) + ")", new Object[] {});
					}
					List<Integer> profileList = new ArrayList<Integer>();
					for (ProfileInfo profile : userProfile){
						try{
							Users rm = usersDao.findByPrimaryKey(profile.getReportingMgr());
							ProfileInfo userRMProfile = profielInfoDao.findByPrimaryKey(rm.getProfileId());
							sendHrSpocChangedNotificationMail(newhr, newhrProfile, oldhr, oldhrProfile, userRMProfile, profile);
							profileList.add(profile.getId());
						} catch (Exception e){
							logger.error("unble to change  HR Spoc from" + oldhr.getId() + " to " + newhr.getId() + " for " + profile.getFirstName() + " " + profile.getLastName(), e);
						}
					}
					if (profileList.size() > 0){
						String sql = "UPDATE PROFILE_INFO SET HR_SPOC=?  WHERE HR_SPOC=? AND ID IN (" + ModelUtiility.getCommaSeparetedValues(profileList) + ")";
						profielInfoDao.findByDynamicUpdate(sql, new Object[] { new Integer(newhrId), new Integer(oldhrId) });
					}
					result.setForwardName(ActionResult.SUCCESS);
				} catch (Exception e){
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
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		switch (ReceiveTypes.getValue(form.getrType())) {
			case RECEIVE:
				try{
					int levelId=0;
					ProfileInfo profileInfo = (ProfileInfo) form;
					UsersDao usersDao = UsersDaoFactory.create();
					ProfileInfo profileInfoDto = new ProfileInfo();
					profileInfoDto = profileInfoDao.findByPrimaryKey(profileInfo.getId());		
					request.setAttribute("actionForm", profileInfoDto);
				} catch (Exception e){
					logger.info("Failed to receive single Profile Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					e.printStackTrace();
				}
				break;
			/**
			 * Receive for employee's - users with added values for
			 * division,department,level label,phone number
			 */
			case RECEIVEUSER:
				try{
					ProfileInfo profileInfo = (ProfileInfo) form;
					int levelId=0;
					Users user = new Users();
					UsersDao usersDao = UsersDaoFactory.create();
					user = usersDao.findByPrimaryKey(profileInfo.getUserId());
					ProfileInfo profileInfoDto = new ProfileInfo();
					profileInfoDto = profileInfoDao.findByPrimaryKey(user.getProfileId());
					// Added to hidden the finance tab for RMG level less than L2-F2
					Levels level[]=null;
					//		level=levelsDao.findByDynamicWhere("DIVISION_ID=? and ID in(6,161,162)",  new Object[] {26});
							Users loginUser=usersDao.findByPrimaryKey(login.getUserId());
							levelId=loginUser.getLevelId();
					
							if(levelId>0){
								//As per requirement userId is hardcoded particular for Kamal Preet to diasble financeinfo in employee modules. 
						//		As per requirement userId is hardcoded particular for Preetika Ratan to diasble financeinfo in employee modules. 
							 
								
								if(login.getUserId()==626||login.getUserId()==719){
									profileInfoDto.setHideFinanceDetails(true);
								}
								
								else if(levelId==6||levelId==161||levelId==162){
									if(profileInfoDto!=null){
										
										if(!(login.getUserId()==profileInfoDto.getHrSpoc())){
											profileInfoDto.setHideFinanceDetails(true);
										}
										else  profileInfoDto.setHideFinanceDetails(false);
									}
								}	
							}
							LevelsDao levelsDao = LevelsDaoFactory.create();
							Divison divison = new Divison();
							DivisonDao divisonDao = DivisonDaoFactory.create();
							Levels level1 = levelsDao.findByPrimaryKey(levelId);
							divison = divisonDao.findByPrimaryKey(level1.getDivisionId());
							String name = (divison.getName());// division name
								System.out.println(name);
								
								if(name.equalsIgnoreCase("RMG")||name.equalsIgnoreCase("Finance"))
								{
									profileInfoDto.setHideCategoryDetails(false);
								}
								else
								{
									profileInfoDto.setHideCategoryDetails(true);
								}
							
									
									
								// End of hidden tab
					ProfileBean profileBean = DtoToBeanConverter.DtoToBeanConverter(user, profileInfoDto);
					request.setAttribute("actionForm", profileBean);
				} catch (Exception e){
					logger.info("Failed to receive single Profile Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					e.printStackTrace();
				}
				break;
			case RECEIVEBIRTHDAYS:{
				FestivalAndBirthdayWishesBean been = new FestivalAndBirthdayWishesBean();
				try{
					ProfileInfo birthdayUsers[] = profileInfoDao.findByDynamicSelect(selectUsers, null);
					if (birthdayUsers != null && birthdayUsers.length > 0){
						List<BirthdaysBean> birthdayBeans = new ArrayList<BirthdaysBean>();
						for (ProfileInfo singleUser : birthdayUsers){
							singleUser.getDob().setYear(new Date().getYear());
							if (PortalUtility.formateDateTimeToDateFormat(new Date()).compareTo(singleUser.getDob()) < 1) birthdayBeans.add(new BirthdaysBean(singleUser.getId() + "", null, null, singleUser.getLastName(), singleUser.getFirstName(), singleUser.getDob() + "", null, singleUser.getOfficalEmailId()));
						}
						been.setBirthDays(birthdayBeans.toArray());
					}
				} catch (Exception e){
					logger.error("Exception: " + e.getMessage());
				}
				try{
					been.setFestivals(FestivalWishesDaoFactory.create().findByDynamicWhere("DOF='" + PortalUtility.returnTodaysDate() + "' AND REGION_ID=?", new Object[] { login.getUserLogin().getRegionId() }));
				} catch (Exception e){
					logger.error("Exception: " + e.getMessage());
				}
				try{
					ProfileInfo dojDaysUsers[] = profileInfoDao.findByDynamicSelect(dojUsers, null);
					LevelsDao levelsDao = LevelsDaoFactory.create();
					if (dojDaysUsers != null && dojDaysUsers.length > 0){
						DateOfJoining[] dojBeans = new DateOfJoining[dojDaysUsers.length];
						int j = 0;
						for (ProfileInfo dojSingleUser : dojDaysUsers){
							Levels levels = new Levels();
							levels = levelsDao.findByPrimaryKey(dojSingleUser.getLevelId());
							String DepName = PortalUtility.getDepartment(dojSingleUser.getLevelId());
							String doj = PortalUtility.getdd_MM_yyyy(dojSingleUser.getDateOfJoining());
							dojBeans[j++] = new DateOfJoining(doj, dojSingleUser.getFirstName(), levels.getDesignation(), DepName, dojSingleUser.getLastName(), dojSingleUser.getLevelId());
						}
						been.setDojDays(dojBeans);
					}
					//been.setDojDays((FestivalWishesDaoFactory.create().findByDynamicWhere("DOF='"+PortalUtility.returnTodaysDate()+"' AND REGION_ID=?", new Object[]{login.getUserLogin().getRegionId()}));
				} catch (Exception e){
					logger.error("Exception: " + e.getMessage());
				}
				request.setAttribute("actionForm", been);
			}
				break;
		}// switch ends
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		Login login = Login.getLogin(request);
		ActionResult result = new ActionResult();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfo profileInfo = (ProfileInfo) form;
		profileInfo.setId(profileInfo.getId() != 0 ? 0 : 0);
		boolean flag = false;
		UsersPk usersPk = new UsersPk();
		LeaveBalancePk leaveBalancePk = new LeaveBalancePk();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProfileInfoPk profileInfoPk = new ProfileInfoPk();
		UserRolesPk userRolesPk = new UserRolesPk();
		PortalMail pMail = new PortalMail();
		ProjectMappingPk projectMappingPk = new ProjectMappingPk();
		//for Roll on
		Login loginDto = (Login) request.getSession(false).getAttribute("login");
		RollOnDao rollOnDao = RollOnDaoFactory.create();
		ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create();
		PoDetailsDao poDetailsDao = PoDetailsDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		DivisonDao divisonDao = DivisonDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProjLocationsDao proLocDao = ProjLocationsDaoFactory.create();
		UserRolesDao rolesDao = UserRolesDaoFactory.create();
		RollOnPk rollOnPk = new RollOnPk();
		PoEmpMapPk insertedRow = new PoEmpMapPk();
		RollOnProjMapPk rollOnProjMapPk = new RollOnProjMapPk();
		LeaveBalanceDao lDao = LeaveBalanceDaoFactory.create();
		boolean official = false;
		LoginDao loginDao = LoginDaoFactory.create();
		EmpSerReqMapPk empMapPk = new EmpSerReqMapPk();
		RollOnProjMapDao rollOnProjMapDao = RollOnProjMapDaoFactory.create();
		ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory.create();
		PoEmpMapDao poEmpMapDao = PoEmpMapDaoFactory.create();
		try{
			// profile info
			if (profileInfo != null){
				//check for change in email id
				if (profileInfo.getOfficalEmailId() != null){
					ProfileInfo officialEmailexists[] = profileInfoDao.findWhereOfficalEmailIdEquals(profileInfo.getOfficalEmailId());
					if (officialEmailexists != null && officialEmailexists.length > 0){
						official = true;
						throw new Exception("Official Email Id already exists");
					}
				}
				if (profileInfo.getMonths() != null){
					if (Integer.parseInt(profileInfo.getMonths()) > 0){
						Date doc = PortalUtility.addMonths(profileInfo.getDateOfJoining(), Integer.parseInt(profileInfo.getMonths()) + 6);
						profileInfo.setDoc(doc);
					}
				}
				profileInfo.setModifiedBy(login.getUserId());
				profileInfoPk = profileInfoDao.insert(profileInfo);
				profileInfo.setId(profileInfoPk.getId());
				Users[] users = usersDao.findWhereProfileIdEquals(profileInfo.getId());
				if (users.length > 0){
					users[0].setLevelId(profileInfo.getLevelId());
					usersPk.setId(users[0].getId());
					usersDao.update(usersPk, users[0]);
				}
			}
			/**
			 * add in User Table
			 */
			if (profileInfo.getEmpId() == 0){
				profileInfo.setEmpId(0);
			}
			if (profileInfo.getEmpId() >= 0){
				Users users = new Users();
				users.setProfileId(profileInfoPk.getId());
				users.setEmpId(profileInfo.getEmpId());
				users.setLevelId(profileInfo.getLevelId());
				users.setUserCreatedBy(login.getUserId());
				/**
				 * Validate emp id
				 */
				if (users.getEmpId() > 0){
					Users userslist[] = usersDao.findByDynamicWhere("EMP_ID = ?", new Object[] { users.getEmpId() });
					if (userslist != null && userslist.length > 0){
						profileInfoDao.delete(profileInfoPk);
						throw new UsersDaoException("Employee id exists");
					}
					/*UserModel userModel = new UserModel();
					DropDown dd = new DropDown();
					dd.setuType("VALIDATEEMPLOYEEID");
					dd.setKey1(users.getEmpId());
					userModel.update(dd, request);
					DropDown dropDown = (DropDown) request.getsession(true).getAttribute("actionForm");
					Boolean empidExists = (Boolean) dropDown.getDetail();
					if (!empidExists){
						usersPk = usersDao.insert(users);
					} else{
						throw new UsersDaoException("Employee id exists");
					}*/
				}
				usersPk = usersDao.insert(users);
				profileInfo.setUserId(usersPk.getId());
				/**
				 * add in contact type
				 */
				String[] primaryKeys = null;
				if (profileInfo.getContactType() != null && profileInfo.getContactType().length > 0){
					ContactType contactType = new ContactType();
					contactType.setContactType(profileInfo.getContactType());
					ContactTypeModel contactTypeModel = new ContactTypeModel();
					contactType.setUserId(usersPk.getId());
					contactTypeModel.save(contactType, request);
					primaryKeys = (String[]) request.getAttribute("ID");
					if (primaryKeys != null && primaryKeys.length > 0){
						profileInfo.setPrimaryKeys(primaryKeys);
					}
				}
				logger.info("primaryKeys--->" + primaryKeys);
				/**
				 * add in leave balance
				 */
				if (profileInfo.getId() > 0){
					LeaveBalance lBalance = new LeaveBalance();
					lBalance.setUserId(usersPk.getId());
					Calendar.getInstance();
					if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 15){
						lBalance.setLeaveAccumalated(1);
						lBalance.setBalance(1);
					}
					leaveBalancePk = lDao.insert(lBalance);
				}
				/**
				 * Add project details
				 */
				if (profileInfo.getProjectId() > 0){
					ProjectMapping projectMapping = new ProjectMapping();
					projectMapping.setProjectId(profileInfo.getProjectId());
					projectMapping.setUserId(usersPk.getId());
					projectMappingPk = projectMappingDao.insert(projectMapping);
					profileInfo.setProjectId(projectMapping.getProjectId());
				}
				/**
				 * add Roll On
				 **/
				Levels levels = levelsDao.findByPrimaryKey(profileInfo.getLevelId());
				if (profileInfo.getProjectId() > 0){
					PoDetails poDetails[] = poDetailsDao.findByDynamicWhere("PROJ_ID=? ORDER BY ID DESC", new Object[] { profileInfo.getProjectId() });
					ChargeCode chargeCode[] = null;
					if (poDetails != null && poDetails.length > 0){
						chargeCode = chargeCodeDao.findByDynamicWhere("PO_ID=?", new Object[] { poDetails[0].getId() });
					}
					Divison divison = divisonDao.findByPrimaryKey(levels.getDivisionId());
					SimpleDateFormat uniqueIdformat = new SimpleDateFormat("ssMMdd");
					EmpSerReqMap empSerReqMap = new EmpSerReqMap();
					String uniqueID = profileInfo.getUserId() + uniqueIdformat.format(new Date());
					empSerReqMap.setSubDate(new Date());
					empSerReqMap.setReqId("IN-RN-" + uniqueID);
					empSerReqMap.setReqTypeId(9);
					empSerReqMap.setRequestorId(loginDto.getUserId());
					empSerReqMap.setRegionId(divison.getRegionId());
					ProcessChain processChainDto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC  LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID WHERE ROLE_ID=(SELECT ROLE_ID FROM USER_ROLES UR LEFT JOIN USERS U ON U.ID=UR.USER_ID WHERE U.ID=?)AND MODULE_ID=18", new Object[] { new Integer(loginDto.getUserId()) })[0];
					if (!(processChainDto != null && processChainDto.getId() > 0)){
						throw new ProcessChainDaoException("No Access previlige to Roll On module");
					} else{
						empSerReqMap.setProcessChainId(processChainDto.getId());
						empSerReqMap.setNotify(processChainDto.getNotification());
						empMapPk = empSerReqMapDao.insert(empSerReqMap);
						RollOn rollOn = new RollOn();
						rollOn.setEmpId(profileInfo.getUserId());
						if (chargeCode != null && chargeCode.length > 0){
							rollOn.setChCodeId(chargeCode[0].getId());
						}
						rollOn.setEsrqmId(empMapPk.getId());
						rollOn.setCurrent(Short.parseShort("1"));
						rollOn.setStartDate(new Date());
						rollOnPk = rollOnDao.insert(rollOn);
						RollOnProjMap rollOnProjMap = new RollOnProjMap();
						rollOnProjMap.setRollOnId(rollOnPk.getId());
						//					ProjLocations[ ] locations = proLocDao.findByProject(project.getId());
						ProjLocations location[] = proLocDao.findWhereProjIdEquals(profileInfo.getProjectId());
						if (location != null && location.length > 0){
							rollOnProjMap.setProjLocId(location[0].getId());
						} else rollOnProjMap.setProjLocId(0);
						rollOnProjMap.setProjId(profileInfo.getProjectId());
						rollOnProjMapPk = rollOnProjMapDao.insert(rollOnProjMap);
						if (chargeCode != null && chargeCode.length > 0){
							//fix for ;post rollon, no of resources not getting updated
							PoEmpMap poEmpMapDto = new PoEmpMap();
							poEmpMapDto.setPoId(chargeCode[0].getPoId());
							poEmpMapDto.setEmpId(profileInfo.getUserId());
							poEmpMapDto.setRate("0.0");
							poEmpMapDto.setType(null);
							poEmpMapDto.setCurrency(null);
							poEmpMapDto.setInactive((short) 1);//INACTIVE=1 ==>
							poEmpMapDto.setCurrent((short) 1);//CURRENT=1 ==>
							insertedRow = poEmpMapDao.insert(poEmpMapDto);
						}
					}
				}
				/**
				 * add role in role table
				 */
				UserRoles roles = new UserRoles();
				roles.setRoleId(profileInfo.getRoleId());
				roles.setUserId(usersPk.getId());
				userRolesPk = rolesDao.insert(roles);
				/**
				 * add in login table
				 */
				if (profileInfo.getOfficalEmailId() != null){
					Login newLogin = new Login();
					newLogin.setUserName(profileInfo.getOfficalEmailId());
					MailGenerator mailGenerator = new MailGenerator();
					// MM/dd/yyyy/DOB
					String date = PortalUtility.formatDate(profileInfo.getDob());
					String arr[] = date.split("/");
					newLogin.setPassword(arr[1] + arr[0] + arr[2]);
					newLogin.setUserId(usersPk.getId());
					//before inserting Encrypt Password using DES algorithm
					String encryptedPassword = DesEncrypterDecrypter.getInstance().encrypt(newLogin.getPassword());
					String nonEncPass = newLogin.getPassword();
					if (encryptedPassword != null){
						newLogin.setPassword(encryptedPassword);
					}
					LoginPk logPk = loginDao.insert(newLogin);
					if (logPk != null && logPk.getId() > 0){
						pMail.setMailSubject("LYNX - Login Details");
						pMail.setAutoGenPassword(nonEncPass);
						pMail.setAutoGenUserName(newLogin.getUserName());
						pMail.setTemplateName(MailSettings.EMPLOYEELOGIN);
						pMail.setCandidateName(profileInfo.getFirstName());
						pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
						mailGenerator.invoker(pMail);
					}
				}
				/**
				 * send email if DOJ is todays date send email to employee
				 * attach confirmation letter
				 */
				if (profileInfo.getEmployeeType().equalsIgnoreCase("CONFIRMED")){
					Date cuurdate = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
					if (profileInfo.getDateOfConfirmation().before(cuurdate) || profileInfo.getDateOfConfirmation().equals(cuurdate)){
						new EmployeeDateOfConfirmation().addLeaves(usersPk.getId(), profileInfo.getDateOfConfirmation());
						/**
						 * send email to hrspoc of employee
						 */
						Attachements[] attachements = new Attachements[1];
						Attachements attachement = new Attachements();
						Users user = usersDao.findByPrimaryKey(usersPk.getId());
						String tempFileName1 = generateFName(user.getId(), PortalData.CONFIRMATION_LETTER, ExportType.pdf);
						if (generateConfirmation(user, profileInfo, levels, tempFileName1)){
							new JGenerator();
							//save some field in user/profile table
							String filePath = JGenerator.getOutputFile(JGenerator.EMPLOYEE, tempFileName1);
							attachement.setFilePath(filePath);
							attachement.setFileName("ConfirmationLetter.pdf");
							attachements[0] = attachement;
							pMail.setFileSources(attachements);
						}
						MailGenerator mailGenerator = new MailGenerator();
						pMail.setMailSubject("Employment Confirmation");
						pMail.setCandidateName(profileInfo.getFirstName());
						pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
						pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
						pMail.setTemplateName(MailSettings.EMPLOYEE_PROBATION_CONFIRM);
						mailGenerator.invoker(pMail);
					}
				}
			}
		} catch (UsersDaoException e){
			flag = true;
			logger.info("Employee id exists");
			try{
				profileInfoDao.delete(profileInfoPk);
			} catch (ProfileInfoDaoException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.employeeidalreadyexists"));
			e.printStackTrace();
		} catch (RollOnProjMapDaoException e){
			flag = true;
			logger.info("Failed to rollOn employee");
			try{
				rollOnDao.delete(rollOnPk);
				//profileInfoDao.delete(profileInfoPk);
			} catch (Exception e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosavepemployee"));
			e.printStackTrace();
		} catch (RollOnDaoException e2){
			flag = true;
			logger.info("Failed to rollOn employee");
			try{
				//profileInfoDao.delete(profileInfoPk);
			} catch (Exception e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosavepemployee"));
			e2.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e){
			flag = true;
			logger.info("Failed to rollOn employee");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.noaccesstomodule"));
			e.printStackTrace();
		} catch (Exception e){
			try{
				if (rollOnPk != null){
					logger.info("Failed to save employee");
					rollOnDao.delete(rollOnPk);
				}
				//profileInfoDao.delete(profileInfoPk);
			} catch (Exception e2){
				e2.printStackTrace();
			}
			if (official){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.officialemail.error"));
			} else{
				logger.info("Failed to save Employee Info");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtomigratecandidate"));
			}
			e.printStackTrace();
		} finally{
			if (flag){
				try{
					if (profileInfoPk.getId() > 0){
						profileInfoDao.delete(profileInfoPk);
					}
					if (projectMappingPk.getId() > 0){
						projectMappingDao.delete(projectMappingPk);
					}
					if (leaveBalancePk.getId() > 0){
						lDao.delete(leaveBalancePk);
					}
					/**
					 * Cancell roll on changes
					 */
					if (empMapPk.getId() > 0){
						empSerReqMapDao.delete(empMapPk);
						if (rollOnPk.getId() > 0) rollOnDao.delete(rollOnPk);
						if (rollOnProjMapPk.getId() > 0) rollOnProjMapDao.delete(rollOnProjMapPk);
						if (insertedRow.getId() > 0) poEmpMapDao.delete(insertedRow);
					}
					/**
					 * delete from roles table
					 */
					if (userRolesPk.getId() > 0){
						rolesDao.delete(userRolesPk);
					}
					/**
					 * delete in Contact Type
					 */
					ContactTypeDao contactTypeDao = ContactTypeDaoFactory.create();
					ContactType contactType[] = contactTypeDao.findWhereUserIdEquals(usersPk.getId());
					if (contactType != null && contactType.length > 0){
						for (ContactType contactType2 : contactType){
							ContactTypePk contactTypePk = new ContactTypePk();
							contactTypePk.setId(contactType2.getId());
							contactTypeDao.delete(contactTypePk);
						}
					}
					/**
					 * delete user
					 */
					usersDao.delete(usersPk);
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("profileId", profileInfoPk);
		return result;
	}

	/**
	 * @param user
	 *            for unique user id
	 * @param userProfile
	 *            for user name
	 * @param level
	 *            for filing designation
	 * @return true or false depending on pdf generated or not
	 */
	public static boolean generateConfirmation(Users user, ProfileInfo userProfile, Levels level, String tempFileName1) {
		boolean flag = false;
		JGenerator jGenerator = new JGenerator();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("DESIGNATION", level.getDesignation());
		params.put("EMPLOYEE_NAME", userProfile.getFirstName() + " " + userProfile.getLastName());
		flag = jGenerator.generateFile(JGenerator.EMPLOYEE, tempFileName1, CONFIRMATION_LETTER, params);
		return flag;
	}

	/**
	 * @param userId
	 *            for unique identification
	 * @param abbrivation
	 *            name of file as in CONFL_userID.pdf
	 * @param ext
	 *            type of preview expected eg: pdf for offerletter,etc
	 * @return complete file name
	 */
	public static String generateFName(int candidateId, String abbrivation, ExportType ext) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHH");
		String date = format.format(new Date());
		String fileName = abbrivation + candidateId + date + "." + ext;
		return fileName;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		UsersDao usersDao = UsersDaoFactory.create();
		Login login = Login.getLogin(request);
		ProfileInfo profileInfo = (ProfileInfo) form;
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		ProfileInfoPk profileInfoPk = new ProfileInfoPk();
		profileInfoPk.setId(profileInfo.getId());
		ProjectMapping projectMapping = new ProjectMapping();
		MailGenerator generator = new MailGenerator();
		PortalMail portalMail = new PortalMail();
		boolean HRSPOCChanged = false, official = false, dateOfConifrm = false;;
		boolean RMchanged = false, docChanged = false;
		int months = 0;
		Date newDoc = null, oldDateConfirm = null;
		MailGenerator mailGenerator = new MailGenerator();
		UsersPk usersPk = new UsersPk();
		try{
			// profile info
			if (profileInfo != null){
				if (!(profileInfo.getCandidateId() > 0)){
					ProfileInfo profile = profileInfoDao.findByPrimaryKey(profileInfoPk);
					Users[] users = usersDao.findWhereProfileIdEquals(profileInfo.getId());
					/**
					 * profileInfo is form data and profile is DB data
					 */
					if (profileInfo.getMonths() != null && Integer.parseInt(profileInfo.getMonths()) > 0){
						if (profile.getDoc() == null){
							months = Integer.parseInt(profileInfo.getMonths()) + 6;
							newDoc = PortalUtility.addMonths(profile.getDateOfJoining(), months);
							oldDateConfirm = PortalUtility.addMonths(profile.getDateOfJoining(), 6);
						} else{
							months = Integer.parseInt(profileInfo.getMonths());
							newDoc = PortalUtility.addMonths(profile.getDoc(), months);
							oldDateConfirm = profile.getDoc();
						}
						docChanged = true;
						//send emails to related people
						sendExtensionProbationEmail(portalMail, profileInfo, generator, login, profile, users, newDoc, oldDateConfirm);
					}
					if (!(profileInfo.getCandidateId() > 0)){
						if (!(profile.getReportingMgr() == profileInfo.getReportingMgr()) && profile.getReportingMgr() != 0){
							RMchanged = true;
						}
						if (!(profile.getHrSpoc() == profileInfo.getHrSpoc()) && profile.getHrSpoc() != 0){
							HRSPOCChanged = true;
						}
					}
					//check for change in email id
					if (!(profileInfo.getOfficalEmailId().equals(profile.getOfficalEmailId()))){
						ProfileInfo officialEmailexists[] = profileInfoDao.findWhereOfficalEmailIdEquals(profileInfo.getOfficalEmailId());
						if (officialEmailexists != null && officialEmailexists.length > 0){
							official = true;
							throw new Exception("Official Email Id already exists");
						}
					}
					///set flag before update check if date of confirmation  changed
					if (profileInfo.getDateOfConfirmation() != null) if (!(profileInfo.getDateOfConfirmation().equals(profile.getDateOfConfirmation()))){
						dateOfConifrm = true;
					}
					//send email to new and old RM
					if (RMchanged){
						Users newRM = usersDao.findByPrimaryKey(profileInfo.getReportingMgr());
						Users oldRM = usersDao.findByPrimaryKey(profile.getReportingMgr());
						Users SPOC = usersDao.findByPrimaryKey(profile.getHrSpoc());
						ProfileInfo newRMProfile = profileInfoDao.findByPrimaryKey(newRM.getProfileId());
						ProfileInfo oldRMProfile = profileInfoDao.findByPrimaryKey(oldRM.getProfileId());
						ProfileInfo hrspoc = profileInfoDao.findByPrimaryKey(SPOC.getProfileId());
						sendRmChangedNotificationMail(newRM, newRMProfile, oldRM, oldRMProfile, hrspoc, profileInfo);
						profileInfoDao.update(profileInfoPk, profileInfo);
					}
					//send email to new and old HRSPOC
					if (HRSPOCChanged){
						Users newHRSPOC = usersDao.findByPrimaryKey(profileInfo.getHrSpoc());
						Users oldHRSPOC = usersDao.findByPrimaryKey(profile.getHrSpoc());
						Users userRM = new Users();
						if (RMchanged){
							userRM = usersDao.findByPrimaryKey(profileInfo.getReportingMgr());
						} else{
							userRM = usersDao.findByPrimaryKey(profile.getReportingMgr());
						}
						ProfileInfo newHRSPOCProfile = profileInfoDao.findByPrimaryKey(newHRSPOC.getProfileId());
						ProfileInfo oldHRSPOCProfile = profileInfoDao.findByPrimaryKey(oldHRSPOC.getProfileId());
						ProfileInfo userRMProfile = profileInfoDao.findByPrimaryKey(userRM.getProfileId());
						sendHrSpocChangedNotificationMail(newHRSPOC, newHRSPOCProfile, oldHRSPOC, oldHRSPOCProfile, userRMProfile, profileInfo);
					}
					profileInfoDao.update(profileInfoPk, profileInfo);
					if (users.length > 0){
						users[0].setLevelId(profileInfo.getLevelId());
						usersPk.setId(users[0].getId());
						usersDao.update(usersPk, users[0]);
					}
				}
				if (docChanged){
					profileInfo.setDoc(newDoc);//set new temporary doc
					profileInfo.setExtension("2");//set extension as 2 action taken
				}
				profileInfo.setModifiedBy(login.getUserId());
				profileInfoDao.update(profileInfoPk, profileInfo);
				/**
				 * Update project details
				 */
				if (profileInfo.getProjectId() > 0){
					ProjectMappingPk projectMappingPk = new ProjectMappingPk();
					ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory.create();
					ProjectMapping projectMap[] = projectMappingDao.findWhereUserIdEquals(profileInfo.getUserId());
					if (projectMap != null && projectMap.length > 0){
						projectMap[0].setProjectId(profileInfo.getProjectId());
						projectMappingPk.setId(projectMap[0].getId());
						projectMappingDao.update(projectMappingPk, projectMap[0]);
					} else{
						projectMapping.setUserId(profileInfo.getUserId() != 0 ? profileInfo.getUserId() : 0);
						projectMapping.setProjectId(profileInfo.getProjectId());
						projectMappingDao.insert(projectMapping);
					}
				}
				/**
				 * Update role in role table
				 */
				if (profileInfo.getRoleId() > 0){
					UserRolesDao rolesDao = UserRolesDaoFactory.create();
					UserRoles roles = new UserRoles();
					UserRolesPk userRolesPk = new UserRolesPk();
					roles = rolesDao.findWhereUserIdEquals(profileInfo.getUserId());
					if (roles != null){
						roles.setRoleId(profileInfo.getRoleId());
						userRolesPk.setId(roles.getId());
						rolesDao.update(userRolesPk, roles);
					}
				}
			}//if candidate don't save other data
			/**
			 * update contact type
			 */
			String primaryKeys[] = null;
			if (profileInfo.getContactType() != null && profileInfo.getContactType().length > 0){
				ContactType contactType = new ContactType();
				contactType.setContactType(profileInfo.getContactType());
				ContactTypeModel contactTypeModel = new ContactTypeModel();
				ContactTypeDao contactTypeDao = ContactTypeDaoFactory.create();
				ContactType contacttype[] = contactTypeModel.populateDtos(profileInfo.getContactType());
				primaryKeys = new String[contacttype.length];
				int i = 0;
				for (ContactType contactType2 : contacttype){
					ContactTypePk contactTypePk = new ContactTypePk();
					if (contactType2.getId() > 0){
						contactTypePk.setId(contactType2.getId());
						contactTypeDao.update(contactTypePk, contactType2);
						primaryKeys[i] = String.valueOf(contactType2.getId());
					} else{
						int id = profileInfo.getUserId();
						contactType2.setUserId(id);
						contactTypePk = contactTypeDao.insert(contactType2);
						primaryKeys[i] = String.valueOf(contactTypePk.getId());
					}
					i++;
				}
				profileInfo.setPrimaryKeys(primaryKeys);
			}
			Levels levels = levelsDao.findByPrimaryKey(profileInfo.getLevelId());
			/**
			 * send email if DOJ is todays date send email to employee
			 * attach confirmation letter & check if this update is not for candiate
			 */
			if (!(profileInfo.getCandidateId() > 0)) if (profileInfo.getEmployeeType().equalsIgnoreCase("CONFIRMED") && profileInfo.getDateOfConfirmation() != null){
				if (dateOfConifrm){
					Date cuurdate = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
					if (profileInfo.getDateOfConfirmation().before(cuurdate) || profileInfo.getDateOfConfirmation().equals(cuurdate)){
						new EmployeeDateOfConfirmation().addLeaves(usersPk.getId(), profileInfo.getDateOfConfirmation());
						/**
						 * send email to hrspoc of employee
						 */
						Attachements[] attachements = new Attachements[1];
						Attachements attachement = new Attachements();
						Users user = usersDao.findByPrimaryKey(usersPk.getId());
						String tempFileName1 = generateFName(user.getId(), PortalData.CONFIRMATION_LETTER, ExportType.pdf);
						if (generateConfirmation(user, profileInfo, levels, tempFileName1)){
							new JGenerator();
							//save some field in user/profile table
							String filePath = JGenerator.getOutputFile(JGenerator.EMPLOYEE, tempFileName1);
							attachement.setFilePath(filePath);
							attachement.setFileName("ConfirmationLetter.pdf");
							attachements[0] = attachement;
							portalMail.setFileSources(attachements);
						}
						List ccIds = ModelUtiility.getInstance().getRMGManagerUserIds(new UsersPk(user.getId()));
						ccIds.add(profileInfo.getReportingMgr());
						ccIds.add(profileInfo.getHrSpoc());
						portalMail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(ccIds) + ") AND STATUS NOT IN (1,2,3))"));
						portalMail.setMailSubject("Employment Confirmation");
						portalMail.setCandidateName(profileInfo.getFirstName());
						portalMail.setRecipientMailId(profileInfo.getOfficalEmailId());
						portalMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
						portalMail.setTemplateName(MailSettings.EMPLOYEE_PROBATION_CONFIRM);
						mailGenerator.invoker(portalMail);
					}
				}
			}
		} catch (ProjectMappingDaoException pe){
			logger.info("Failed to Update Project Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			pe.printStackTrace();
		} catch (UserRolesDaoException ue){
			logger.info("Failed to Update USer Roles Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			ue.printStackTrace();
		} catch (Exception e){
			logger.info("Failed to Update Profile Info");
			if (official){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.officialemail.error"));
			} else{
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
				e.printStackTrace();
			}
		}
		return result;
	}

	public void sendExtensionProbationEmail(PortalMail portalMail, ProfileInfo profileInfo, MailGenerator generator, Login login, ProfileInfo oldProfile, Users[] users, Date newDoc, Date oldDateConfirm) {
		UsersDao usersDao1 = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao1 = ProfileInfoDaoFactory.create();
		try{
			/**
			 * send email to Employee
			 * notifying increase in probation period
			 */
			portalMail = getEmployeeInfo(profileInfo, null);
			portalMail.setMailSubject("Probation Extension");
			portalMail.setTemplateName(MailSettings.EMPLOYEE_PROBATION_NOTIFY);
			portalMail.setTotaldays(Integer.parseInt(profileInfo.getMonths()));
			generator.invoker(portalMail);
			/**
			 * send email to HR SPOC AND CC RM, RMG MANAGER
			 */
			Users loginUser = usersDao1.findByPrimaryKey(login.getUserId());
			ProfileInfo loginProfile = profileInfoDao1.findByPrimaryKey(loginUser.getProfileId());
			//rm of candidate
			Users Rmforemp = usersDao1.findByPrimaryKey(profileInfo.getReportingMgr());
			ProfileInfo RmforempProfile = profileInfoDao1.findByPrimaryKey(Rmforemp.getProfileId());
			Levels levels1 = LevelsDaoFactory.create().findByPrimaryKey(profileInfo.getLevelId());
			Divison divison1 = DivisonDaoFactory.create().findByPrimaryKey(levels1.getDivisionId());
			Regions region11 = RegionsDaoFactory.create().findByPrimaryKey(divison1.getRegionId());
			Notification notifi1 = new Notification(region11.getRefAbbreviation());
			int rmgManager1 = notifi1.rmgManagerLevelId;
			List emailList = UserModel.getUsersByLevelId(rmgManager1, null);
			
	//		Set<String> ccEmails = new HashSet<String>();
	//		ccEmails.addAll(emailList);
	//		ccEmails.add(RmforempProfile.getOfficalEmailId());
	//		portalMail.setAllReceipientcCMailId((String[]) ccEmails.toArray(new String[0]));
			
			List ccIds = ModelUtiility.getInstance().getRMGManagerUserIds(new UsersPk(loginUser.getId()));
			ccIds.add(profileInfo.getReportingMgr());
			ccIds.add(profileInfo.getHrSpoc());
			portalMail = getEmployeeInfo(loginProfile, profileInfo);
			portalMail.setAllReceipientcCMailId(profileInfoDao1.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(ccIds) + ") AND STATUS NOT IN (1,2,3))"));
			portalMail.setMailSubject("Probation Extension -" + profileInfo.getFirstName());
			portalMail.setEmpId(Integer.toString(users[0].getEmpId()));
			portalMail.setCandidateName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
			portalMail.setReportingDate(PortalUtility.formatDateddMMyyyy(newDoc));//new date of confirmation
			portalMail.setDateOfSeperation(PortalUtility.formatDateddMMyyyy(oldDateConfirm));//old date ofconfirmation
			portalMail.setEmpFname(loginProfile.getFirstName());
			portalMail.setTemplateName(MailSettings.EMPLOYEE_PROBATION);
			generator.invoker(portalMail);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void sendHrSpocChangedNotificationMail(Users newHRSPOC, ProfileInfo newHRSPOCProfile, Users oldHRSPOC, ProfileInfo oldHRSPOCProfile, ProfileInfo userRMProfile, ProfileInfo userProfile) throws Exception {
		transferPendingReq(newHRSPOC, oldHRSPOC, userProfile);
		MailGenerator mailGenerator = new MailGenerator();
		Levels levels1 = LevelsDaoFactory.create().findByPrimaryKey(userProfile.getLevelId());
		Divison divison1 = DivisonDaoFactory.create().findByPrimaryKey(levels1.getDivisionId());
		Regions region11 = RegionsDaoFactory.create().findByPrimaryKey(divison1.getRegionId());
		Notification notifi1 = new Notification(region11.getRefAbbreviation());
		int rmgManager1 = notifi1.rmgManagerLevelId;
		List rmgemailList = UserModel.getUsersByLevelId(rmgManager1, null);
		/**
		 * send email to employee
		 */
		//new HR Personal info
		PersonalInfo newHRSPOCPersonalInfo = PersonalInfoDaoFactory.create().findByPrimaryKey(newHRSPOC.getPersonalId());
		PortalMail portalMail = setEMailData(new PortalMail(), newHRSPOCProfile, oldHRSPOCProfile, userRMProfile, userProfile, newHRSPOCPersonalInfo);
		ArrayList<String> recipientMailIds = new ArrayList<String>();
		if (oldHRSPOC.getStatus() != 2 || oldHRSPOC.getStatus() != 3 || oldHRSPOC.getStatus() != 1){
			recipientMailIds.add(oldHRSPOCProfile.getOfficalEmailId());//old hr spoc
		}
		recipientMailIds.add(userRMProfile.getOfficalEmailId());//rm 
		recipientMailIds.add(newHRSPOCProfile.getOfficalEmailId());//new SPOC
		recipientMailIds.addAll(rmgemailList);//all rmg manager
		portalMail.setAllReceipientcCMailId(recipientMailIds.toArray(new String[recipientMailIds.size()]));
		portalMail.setMailSubject("Change in SPOC");
		portalMail.setTemplateName(MailSettings.EMPLOYEE_UPDATION_SPOC);
		mailGenerator.invoker(portalMail);
		/**
		 * send emails to old and new Hr spoc and Reporting manager of employee
		 */
		//		portalMail= setEMailData(new PortalMail(),newHRSPOCProfile,oldHRSPOCProfile,userRMProfile,userProfile,newHRSPOCPersonalInfo);
		//		portalMail.setMailSubject("HR SPOC Changed For Employee");
		//		portalMail.setTemplateName(MailSettings.EMPLOYEE_UPDATION_SPOC_NOTIFY);
		//		portalMail.setRecipientMailId(newHRSPOCProfile.getOfficalEmailId());
		//		mailGenerator.invoker(portalMail);
	}

	private void sendRmChangedNotificationMail(Users newRM, ProfileInfo newRMProfile, Users oldRM, ProfileInfo oldRMProfile, ProfileInfo hrspoc, ProfileInfo userProfile) throws Exception {
		MailGenerator mailGenerator = new MailGenerator();
		Levels levels1 = LevelsDaoFactory.create().findByPrimaryKey(userProfile.getLevelId());
		Divison divison1 = DivisonDaoFactory.create().findByPrimaryKey(levels1.getDivisionId());
		Regions region11 = RegionsDaoFactory.create().findByPrimaryKey(divison1.getRegionId());
		Notification notifi1 = new Notification(region11.getRefAbbreviation());
		transferPendingReq(newRM, oldRM, userProfile);
		int rmgManager1 = notifi1.rmgManagerLevelId;
		List rmgemailList = UserModel.getUsersByLevelId(rmgManager1, null);
		/**
		 * new RM personal info
		 */
		PersonalInfo newRMPersonalInfo = PersonalInfoDaoFactory.create().findByPrimaryKey(newRM.getPersonalId());
		/**
		 * send email to employee and cc to RM(New + old),HR,and RMG manager
		 */
		ArrayList<String> recipientMailIds = new ArrayList<String>();
		if (oldRM.getStatus() != 2 || oldRM.getStatus() != 3 || oldRM.getStatus() != 1){
			recipientMailIds.add(oldRMProfile.getOfficalEmailId());//old RM
		}
		PortalMail portalMail = setEMailData(new PortalMail(), newRMProfile, oldRMProfile, hrspoc, userProfile, newRMPersonalInfo);
		recipientMailIds.add(hrspoc.getOfficalEmailId());//hr spoc
		recipientMailIds.add(newRMProfile.getOfficalEmailId());//new RM
		recipientMailIds.addAll(rmgemailList);//all rmg manager
		portalMail.setRecipientMailId(userProfile.getOfficalEmailId());
		portalMail.setAllReceipientcCMailId(recipientMailIds.toArray(new String[recipientMailIds.size()]));
		portalMail.setMailSubject("Change in Reporting Manager");
		portalMail.setTemplateName(MailSettings.EMPLOYEE_UPDATION_RM);
		mailGenerator.invoker(portalMail);
	}

	private void transferPendingReq(Users newRM, Users oldRM, ProfileInfo userProfile) {
		EmpSerReqMapDao esrmDao = EmpSerReqMapDaoFactory.create();
		try{
			Users uesr = UsersDaoFactory.create().findWhereProfileIdEquals(userProfile.getId())[0];
			logger.info("Changing " + userProfile.getFirstName() + "(" + uesr.getId() + ")'s pending requestes from RM/HRSPOC(" + oldRM.getId() + ") to RM/HRSPOC(" + newRM.getId() + ")");
			EmpSerReqMap esrms[] = esrmDao.findByDynamicSelect("SELECT ESRM.* FROM INBOX I JOIN EMP_SER_REQ_MAP ESRM ON I.ESR_MAP_ID=ESRM.ID WHERE I.RECEIVER_ID=I.ASSIGNED_TO AND ASSIGNED_TO=? AND ESRM.REQUESTOR_ID=? ORDER BY REQ_TYPE_ID", new Object[] { oldRM.getId(), uesr.getId() });
			if (esrms != null && esrms.length > 0){
				//if (JDBCUtiility.getInstance().update("UPDATE INBOX UI,(SELECT I.ID FROM INBOX I JOIN EMP_SER_REQ_MAP ESRM ON I.ESR_MAP_ID=ESRM.ID WHERE I.RECEIVER_ID=I.ASSIGNED_TO AND ASSIGNED_TO=? AND ESRM.REQUESTOR_ID=?) W SET UI.RECEIVER_ID=? AND UI.ASSIGNED_TO=? WHERE UI.ID=W.ID", new Object[] { oldRM.getId(), uesr.getId(), newRM.getId(), newRM.getId() }) == -1){
				List<Object> inboxList = JDBCUtiility.getInstance().getSingleColumn("SELECT I.ID FROM INBOX I JOIN EMP_SER_REQ_MAP ESRM ON I.ESR_MAP_ID=ESRM.ID WHERE I.RECEIVER_ID=I.ASSIGNED_TO AND ASSIGNED_TO=? AND ESRM.REQUESTOR_ID=?", new Object[] { oldRM.getId(), uesr.getId() });
				JDBCUtiility.getInstance().update("UPDATE INBOX SET  RECEIVER_ID=?  WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(inboxList) + ")", new Object[] { newRM.getId() });
				JDBCUtiility.getInstance().update("UPDATE INBOX SET  ASSIGNED_TO=?  WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(inboxList) + ")", new Object[] { newRM.getId() });
				//}
				HashMap<Integer, ArrayList<Integer>> requests = new HashMap<Integer, ArrayList<Integer>>();
				for (EmpSerReqMap esrm : esrms){
					switch (esrm.getReqTypeId()) {
						case 1:
							ArrayList<Integer> leaveReq = requests.get(esrm.getReqTypeId());
							if (leaveReq == null){
								leaveReq = new ArrayList<Integer>();
								requests.put(esrm.getReqTypeId(), leaveReq);
							}
							leaveReq.add(esrm.getId());
							break;
						case 2:
							ArrayList<Integer> travelReq = requests.get(esrm.getReqTypeId());
							if (travelReq == null){
								travelReq = new ArrayList<Integer>();
								requests.put(esrm.getReqTypeId(), travelReq);
							}
							travelReq.add(esrm.getId());
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							ArrayList<Integer> reimReq = requests.get(esrm.getReqTypeId());
							if (reimReq == null){
								reimReq = new ArrayList<Integer>();
								requests.put(esrm.getReqTypeId(), reimReq);
							}
							reimReq.add(esrm.getId());
							break;
						case 6:
							break;
						case 7:
							break;
						case 8:
							ArrayList<Integer> timesheetReq = requests.get(esrm.getReqTypeId());
							if (timesheetReq == null){
								timesheetReq = new ArrayList<Integer>();
								requests.put(esrm.getReqTypeId(), timesheetReq);
							}
							timesheetReq.add(esrm.getId());
							break;
						case 9:
							break;
						case 10:
							break;
						case 11:
							ArrayList<Integer> issueReq = requests.get(esrm.getReqTypeId());
							if (issueReq == null){
								issueReq = new ArrayList<Integer>();
								requests.put(esrm.getReqTypeId(), issueReq);
							}
							issueReq.add(esrm.getId());
							break;
						case 12:
							break;
						case 13:
							break;
						case 14:
							break;
						default:
							break;
					}
				}
				logger.info("Changing " + userProfile.getFirstName() + "(" + uesr.getId() + ")'s pending requestes(" + requests + ") from RM/HRSPOC(" + oldRM.getId() + ") to RM/HRSPOC(" + newRM.getId() + ")");
				for (Integer key : requests.keySet()){
					switch (key) {
						case 1:
							changeLeaveRequest(requests.get(key), newRM, oldRM);
							for (Integer esrmapId : requests.get(key)){
								setAsValidApprover(esrmapId, uesr.getId(), oldRM.getId(), newRM.getId());
							}
							break;
						case 2:
							changetravelRequest(requests.get(key), newRM, oldRM);
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							changeReimbursementRequest(requests.get(key), newRM, oldRM);
							break;
						case 6:
							break;
						case 7:
							break;
						case 8:
							changetimesheetRequest(requests.get(key), newRM, oldRM);
							for (Integer esrmapId : requests.get(key)){
								setAsValidApprover(esrmapId, uesr.getId(), oldRM.getId(), newRM.getId());
							}
							break;
						case 9:
							break;
						case 10:
							break;
						case 11:
							changeIssueRequest(requests.get(key), newRM, oldRM);
							for (Integer esrmapId : requests.get(key)){
								setAsValidApprover(esrmapId, uesr.getId(), oldRM.getId(), newRM.getId());
							}
							break;
						case 12:
							break;
						case 13:
							break;
						case 14:
							break;
						default:
							break;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void changetravelRequest(ArrayList<Integer> reqList, Users newUser, Users oldUser) {
		JDBCUtiility.getInstance().update("UPDATE TRAVEL_REQ SET ASSIGNED_TO=? WHERE TL_REQ_ID IN (SELECT ID FROM TRAVEL T WHERE ASSIGNED_TO = ? AND ESRQM_ID IN (" + ModelUtiility.getCommaSeparetedValues(reqList) + "))", new Object[] { newUser.getId(), oldUser.getId() });
		try{
			TravellerTypeDao travellerTypeDao = TravellerTypeDaoFactory.create();
			TravellerType travellerType[] = travellerTypeDao.findByDynamicSelect("SELECT TT.* FROM TRAVELLER_TYPE TT JOIN TRAVEL T ON T.ID = TT.TL_ID WHERE T.ESRQM_ID IN (" + ModelUtiility.getCommaSeparetedValues(reqList) + ")", null);
			for (TravellerType travel : travellerType){
				travel.setNextSetOfApprovers(replaceUserId(travel.getNextSetOfApprovers(), new String[] { "~=~", "," }, oldUser.getId(), newUser.getId()));
				travellerTypeDao.update(new TravellerTypePk(travel.getId()), travel);
			}
		} catch (Exception e){}
	}

	private void changeIssueRequest(ArrayList<Integer> reqList, Users newUser, Users oldUser) {
		JDBCUtiility.getInstance().update("UPDATE ISSUE_HANDLER_CHAIN_REQ SET ASSIGNED_TO=? WHERE ASSIGNED_TO=? AND ESR_MAP_ID IN (" + ModelUtiility.getCommaSeparetedValues(reqList) + ")", new Object[] { newUser.getId(), oldUser.getId() });
	}

	private void changetimesheetRequest(ArrayList<Integer> reqList, Users newUser, Users oldUser) {
		JDBCUtiility.getInstance().update("UPDATE TIMESHEET_REQ SET ASSIGNED_TO =? WHERE ASSIGNED_TO = ? AND ESRQM_ID IN (" + ModelUtiility.getCommaSeparetedValues(reqList) + ")", new Object[] { newUser.getId(), oldUser.getId() });
	}

	private void changeLeaveRequest(ArrayList<Integer> reqList, Users newUser, Users oldUser) {
		JDBCUtiility.getInstance().update("UPDATE LEAVE_MASTER SET ASSIGNED_TO =? WHERE ASSIGNED_TO = ? AND ESR_MAP_ID IN (" + ModelUtiility.getCommaSeparetedValues(reqList) + ")", new Object[] { newUser.getId(), oldUser.getId() });
	}

	private void changeReimbursementRequest(ArrayList<Integer> reqList, Users newUser, Users oldUser) {
		JDBCUtiility.getInstance().update("UPDATE REIMBURSEMENT_REQ SET ASSIGN_TO =? WHERE ASSIGN_TO = ? AND ESR_MAP_ID IN (" + ModelUtiility.getCommaSeparetedValues(reqList) + ")", new Object[] { newUser.getId(), oldUser.getId() });
	}

	public boolean setAsValidApprover(int esrqmId, int userId, int oldApprover, int newApprover) {
		try{
			List<String> users = ModelUtiility.getInstance().getSiblingUsersList(esrqmId);
			EmpSerReqHistory esrHistory = EmpSerReqHistoryDaoFactory.create().findWhereEsrMapIdEqualsMax(esrqmId);
			if (esrHistory != null && esrHistory.getAssignedTo() == oldApprover){
				esrHistory.setAssignedTo(newApprover);
				EmpSerReqHistoryDaoFactory.create().update(new EmpSerReqHistoryPk(esrHistory.getId()), esrHistory);
			}
			if (users.contains(oldApprover + "")){
				users.remove(oldApprover + "");
				if (!users.contains(newApprover + "")) users.add(newApprover + "");
			}
			Integer[] updatedusers = new Integer[users.size()];
			int i = 0;
			for (String user : users)
				updatedusers[i++] = Integer.parseInt(user);
			ModelUtiility.getInstance().updateSiblings(updatedusers, esrqmId);
		} catch (Exception e){
			logger.error("Error while validateApprover ", e);
		}
		return false;
	}

	private PortalMail setEMailData(PortalMail portalMail, ProfileInfo newRMProfile, ProfileInfo oldRMProfile, ProfileInfo hrspoc, ProfileInfo userProfile, PersonalInfo newRMPersonalInfo) {
		portalMail.setRecipientMailId(userProfile.getOfficalEmailId());
		portalMail.setEmpFname(userProfile.getFirstName());//hi user
		portalMail.setEmpLName(newRMProfile.getFirstName());// new RM/HRSPOC name
		portalMail.setEmployeeName(oldRMProfile.getFirstName());//old RM/HRSPOC name
		portalMail.setEmpEmailId(newRMProfile.getOfficalEmailId());//email id of new rm
		if (newRMPersonalInfo != null && newRMPersonalInfo.getPrimaryPhoneNo() != null){
			portalMail.setEmpContNo(newRMPersonalInfo.getPrimaryPhoneNo() == "0" ? " N.A" : newRMPersonalInfo.getPrimaryPhoneNo());//contact number of new RM
		} else{
			portalMail.setEmpContNo(" N.A");//contact number of new RM
		}
		portalMail.setDate(PortalUtility.formatDateddMMyyyy(new Date()));
		return portalMail;
	}

	public PortalMail getEmployeeInfo(ProfileInfo profileInfo, ProfileInfo employeeInfo) {
		LevelsDao levelsDao = LevelsDaoFactory.create();
		PortalMail pMail = new PortalMail();
		Levels level;
		String designation = null;
		String candidatename = null, division = null;
		try{
			if (employeeInfo != null){
				int levelid = employeeInfo.getLevelId();
				level = levelsDao.findByPrimaryKey(levelid);
				// level id
				designation = level.getDesignation();// designation
				Divison divison = new Divison();
				DivisonDao divisonDao = DivisonDaoFactory.create();
				divison = divisonDao.findByPrimaryKey(level.getDivisionId());
				if (divison.getParentId() == 0){
					division = divison.getName();
				} else{
					Divison department = divisonDao.findByPrimaryKey(divison.getParentId());
					division = department.getName();
				}
			}
			candidatename = profileInfo.getFirstName() + " " + profileInfo.getLastName();
		}//end of try
		catch (LevelsDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DivisonDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
		pMail.setEmpLName(candidatename);
		pMail.setOffreredDivision(division);
		pMail.setOfferedDesignation(designation);
		pMail.setDate(PortalUtility.formatDateToddmmyyyyhhmmss(new Date()));
		return pMail;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public String replaceUserId(String inString, String[] delimater, int oldUserId, int newUserId) {
		StringBuffer sf = new StringBuffer();
		String[] array = inString.split(delimater[0]);
		if (array.length > 0){
			for (String s : array[0].split(delimater[1])){
				if (s.trim().equals(oldUserId + "")) s = newUserId + "";
				sf.append(s.trim()).append(delimater[1] + " ");
			}
			array[0] = sf.substring(0, sf.length() - (delimater[1].length() + 1));
			sf.delete(0, sf.length());
			for (String arrVal : array){
				sf.append(arrVal).append(delimater[0]);
			}
			inString = sf.substring(0, sf.length() - delimater[0].length());
		}
		return inString;
	}
}
