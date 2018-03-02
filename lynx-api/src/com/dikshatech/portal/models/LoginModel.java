package com.dikshatech.portal.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.Features;
import com.dikshatech.beans.ModuleBean;
import com.dikshatech.beans.UserBean;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AccessibilityLevelDao;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.LoginDao;
import com.dikshatech.portal.dao.ModuleActionDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.ModulesDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RolesDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.AccessibilityLevel;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DivisonPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ModuleAction;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.Roles;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.LoginDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.AccessibilityLevelDaoFactory;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.LoginDaoFactory;
import com.dikshatech.portal.factory.ModuleActionDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.ModulesDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RolesDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class LoginModel extends ActionMethods {

	private static Logger		logger			= LoggerUtil.getLogger(UserModel.class);
	private static final int	EMPLOYEE_LOGIN	= 1;
	private static final int	CANDIDATE_LOGIN	= 2;
	
	
	//Google API Server Key here: Android Push Notifications
	private static final String	GOOGLE_SERVER_KEY	= "AIzaSyA6hF1qt8Knig4R24-m-RF-XpNtMq5tk4I";
	static final String			MESSAGE_KEY			= "message";
	static final String			CATEGORY			= "moduleCategory";

	//private static final int	CLIENT_LOGIN	= 3;
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		ActionResult result = new ActionResult();
		LoginDao loginDao = LoginDaoFactory.create();
		UserRolesDao userRolesDao = UserRolesDaoFactory.create();
		RolesDao rolesDao = RolesDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		ModulesDao modulesDao = ModulesDaoFactory.create();
		ModuleActionDao moActionDao = ModuleActionDaoFactory.create();
		UsersDao uDao = UsersDaoFactory.create();
		LevelsDao lDao = LevelsDaoFactory.create();
		RegionsDao rDao = RegionsDaoFactory.create();
		DivisonDao dao = DivisonDaoFactory.create();
		ProfileInfoDao pDao = ProfileInfoDaoFactory.create();
		AccessibilityLevelDao accessibilityLevelDao = AccessibilityLevelDaoFactory.create();
		UserLogin userLogin = new UserLogin();
		Set<com.dikshatech.beans.Roles> roles = new HashSet<com.dikshatech.beans.Roles>();
		Login login = (Login) form;
		try{
			/**
			 * Encrypt the user inserted password and check with DB
			 */
			String encrypt = DesEncrypterDecrypter.getInstance().encrypt(login.getPassword());
			login = loginDao.findByUserNamePassword(login.getUserName(), encrypt);
			//login = loginDao.findByUserNamePassword(login.getUserName(), login.getPassword());	
			if (login == null){
				login = (Login) form;
				request.setAttribute("actionForm", "");
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlogin"));
				result.setForwardName("success");
			} else{
				if (login.isUserIdNull()){
					/**
					 * candidate login
					 */
					CandidateDao cDao = CandidateDaoFactory.create();
					login.setLoginType(CANDIDATE_LOGIN);
					login.setUserId(login.getCandidateId());
					userLogin.setUserId(login.getCandidateId());
					userLogin.setRoles(roles);
					Candidate candidate = cDao.findByPrimaryKey(login.getUserId());
					ProfileInfo profileInfo = pDao.findByPrimaryKey(candidate.getProfileId());
					Levels levels = lDao.findByPrimaryKey(profileInfo.getLevelId());
					Divison divison = dao.findByPrimaryKey(levels.getDivisionId());
					Regions regions = rDao.findByPrimaryKey(divison.getRegionId());
					userLogin.setOfferStatus(candidate.getStatus());
					userLogin.setDesignation(levels.getDesignation());
					userLogin.setLevel(levels.getLabel());
					userLogin.setDivisionId(levels.getDivisionId());
					userLogin.setDivisionName(divison.getName());
					userLogin.setRegionId(divison.getRegionId());
					userLogin.setRegionName(regions.getRegName());
					userLogin.setUserName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
					userLogin.setOfficialEmaiID(profileInfo.getOfficalEmailId());
					userLogin.setLogin(true);
					userLogin.setUserId(login.getCandidateId());
					login.setUserName(null);
					login.setPassword(null);
					login.setUserLogin(userLogin);
				} else{
					login.setLoginType(EMPLOYEE_LOGIN);
					form.setUserId(login.getUserId());
					UserRoles userRoles = userRolesDao.findWhereUserIdEquals(login.getUserId());
					/*for (UserRoles uRoles : userRoles)
					{

						Roles roleDto = rolesDao.findByPrimaryKey(uRoles.getRoleId());
						// ModulePermission[] featurePermissionDtos =
						// modulePermissionDao.findWhereRoleIdEquals(uRoles.getRoleId());

						com.dikshatech.beans.Roles roleBean = DtoToBeanConverter.DtoToBeanConverter(roleDto);

						Set<ModuleBean> moduSet = new HashSet<ModuleBean>();
						ModuleAction[] moduleActions = moActionDao.findByRoles(uRoles.getRoleId());
						for (ModuleAction mAction : moduleActions)
						{
							Modules modules = modulesDao.findByPrimaryKey(mAction.getModuleId());
							ModuleBean mBean = new ModuleBean();
							mBean.setModuleId(modules.getId());
							mBean.setModuleName(modules.getName());

							Set<Features> featuresBeans = new HashSet<Features>();
							if (mAction.getActionChain().length() > 0)
							{
								String[] featureIds = mAction.getActionChain().trim().split(",");
								for (String fId : featureIds)
								{
									ModulePermission fPermission = modulePermissionDao.findByRoleAndModule(uRoles.getRoleId(), Integer.parseInt(fId));
									AccessibilityLevel accessibilityLevelDto = accessibilityLevelDao.findByPrimaryKey(fPermission.getAccessibilityId());
									
									
									Modules feature = modulesDao.findByPrimaryKey(fPermission.getModuleId());
									Features features = DtoToBeanConverter.DtoToBeanConverter(feature, accessibilityLevelDto);
									
									

									if (fPermission.getAccessibilityId() != 0)
									{
										features = DtoToBeanConverter.DtoToBeanConverter(features, accessibilityLevelDto);
									}

									featuresBeans.add(features);
								}
							}

							mBean.setFeatures(featuresBeans);
							moduSet.add(mBean);
						}
						roleBean.setModules(moduSet);
						roles.add(roleBean);
					
					}*/
					Roles roleDto = rolesDao.findByPrimaryKey(userRoles.getRoleId());
					// ModulePermission[] featurePermissionDtos =
					// modulePermissionDao.findWhereRoleIdEquals(uRoles.getRoleId());
					com.dikshatech.beans.Roles roleBean = DtoToBeanConverter.DtoToBeanConverter(roleDto);
					Set<ModuleBean> moduSet = new HashSet<ModuleBean>();
					ModuleAction[] moduleActions = moActionDao.findByRoles(userRoles.getRoleId());
					for (ModuleAction mAction : moduleActions){
						Modules modules = modulesDao.findByPrimaryKey(mAction.getModuleId());
						ModuleBean mBean = new ModuleBean();
						mBean.setModuleId(modules.getId());
						mBean.setModuleName(modules.getName());
						Set<Features> featuresBeans = new HashSet<Features>();
						if (mAction.getActionChain().length() > 0){
							String[] featureIds = mAction.getActionChain().trim().split(",");
							for (String fId : featureIds){
								try{
									ModulePermission fPermission = modulePermissionDao.findByRoleAndModule(userRoles.getRoleId(), Integer.parseInt(fId));
									AccessibilityLevel accessibilityLevelDto = accessibilityLevelDao.findByPrimaryKey(fPermission.getAccessibilityId());
									Modules feature = modulesDao.findByPrimaryKey(fPermission.getModuleId());
									Features features = DtoToBeanConverter.DtoToBeanConverter(feature, accessibilityLevelDto);
									if (fPermission.getAccessibilityId() != 0){
										features = DtoToBeanConverter.DtoToBeanConverter(features, accessibilityLevelDto);
									}
									featuresBeans.add(features);
								} catch (Exception e){}
							}
						}
						mBean.setFeatures(featuresBeans);
						moduSet.add(mBean);
					}
					roleBean.setModules(moduSet);
					roles.add(roleBean);
					//userLogin.setUserId(login.getUserId());
					userLogin.setRoles(roles);
					Users users = uDao.findByPrimaryKey(login.getUserId());
					Levels levels = lDao.findByPrimaryKey(users.getLevelId());
					Divison divison = dao.findByPrimaryKey(levels.getDivisionId());
					Regions regions = rDao.findByPrimaryKey(divison.getRegionId());
					ProfileInfo profileInfo = pDao.findByPrimaryKey(users.getProfileId());
					userLogin.setDesignation(levels.getDesignation());
					userLogin.setLevel(levels.getLabel());
					userLogin.setDivisionId(levels.getDivisionId());
					userLogin.setDivisionName(divison.getName());
					userLogin.setRegionId(divison.getRegionId());
					userLogin.setRegionName(regions.getRegName());
					userLogin.setFirstName(profileInfo.getFirstName());
					userLogin.setLastName(profileInfo.getLastName());
					userLogin.setUserName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
					userLogin.setOfficialEmaiID(profileInfo.getOfficalEmailId());
					userLogin.setGender(profileInfo.getGender());
					userLogin.setLogin(true);
					login.setUserName(null);
					login.setPassword(null);
					userLogin.setUnReadNotifications(ModelUtiility.getInstance().getUnreadNotifications(login.getUserId()) + "");
					userLogin.setUnReadDocked(ModelUtiility.getInstance().getUnreadDocked(login.getUserId()) + "");
					//***********************************************
					//if the logged_in employee is a R.M in Technical Department
					//dao is of type DivisonDao
					try{
						Divison[] loginDivison = dao.findByDynamicSelect("SELECT * FROM DIVISON D WHERE D.ID = (SELECT L.DIVISION_ID FROM LEVELS L WHERE L.ID=(SELECT P.LEVEL_ID FROM PROFILE_INFO P WHERE P.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)))", new Object[] { new Integer(login.getUserId()) });
						if (loginDivison != null && loginDivison.length > 0){
							int parentId = loginDivison[0].getParentId();
							String deptName = loginDivison[0].getName();
							Divison fetchedDivison = null;
							while (parentId != 0){
								fetchedDivison = dao.findByPrimaryKey(new DivisonPk(parentId));
								parentId = fetchedDivison.getParentId();
							}
							if (!(fetchedDivison == null)) deptName = fetchedDivison.getName();
							logger.debug("LOGIN : " + login.getUserName() + " WITH USER_ID=" + login.getUserId() + " BELONGS TO " + deptName);
							if (deptName.equalsIgnoreCase("HUMAN RESOURCE DEPARTMENT")) userLogin.setIsHrd(true);
							//				if (deptName.equalsIgnoreCase("RMG")) userLogin.setIsHrd(true);
							else userLogin.setIsHrd(false);
						} else{
							logger.error("LOGIN : COULD NOT FETCH ANY ROW FROM DIVISON FOR USER_ID=" + login.getUserId());
						}
					} catch (DivisonDaoException ddex){
						logger.error("LOGIN : EXCEPTION WAS THROWN WHEN TRYING TO FETCH DIVISON ROW FOR USER_ID=" + login.getUserId(), ddex);
					}
					//**********************************************					
					login.setUserLogin(userLogin);
					logger.info(login);
					if (users.getStatus() != UserModel.ACTIVE){
						login = (Login) form;
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlogin"));
						result.setForwardName("success");
					}
				}
				request.getSession(false).setAttribute("login", login);
				if (form.isAndroid()){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sessionid", request.getSession(false).getId());
					map.put("userId", login.getUserId());
					map.put("name", login.getUserLogin().getUserName());
					request.setAttribute("actionForm", map);
				} else request.setAttribute("actionForm", login);
			}
		} catch (DivisonDaoException de){
			de.printStackTrace();
		} catch (LoginDaoException le){
			le.printStackTrace();
		} catch (ProfileInfoDaoException pie){
			pie.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		ActionResult result = new ActionResult();
		/*
		 * if(request.getSession(false).getAttribute("login") != null) { Login
		 * login = Login.getLogin(request);
		 * if(login.getUserLogin() == null) {
		 * request.setAttribute("actionForm", null); }
		 * result.setForwardName("success"); }
		 */
		return result;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		
		ActionResult result = new ActionResult();
		if (request.getSession(false).getAttribute("login") != null){
			Login login = Login.getLogin(request);
			logger.info("Login Receive: " + login);
			if (login.getUserLogin() == null){
				request.setAttribute("actionForm", null);
			} else{
				request.setAttribute("actionForm", login);
			}
			result.setForwardName("success");
		}
	if(form.getrType()!=null && form.getrType().equalsIgnoreCase("RECEIVEALLANDROIDUSER")){
		switch (ReceiveTypes.getValue(form.getrType())) {
	    case RECEIVEALLANDROIDUSER:
		try{
		Connection conn=null;
		Set<Integer> userIds=null;
		List<ProfileInfo> profileList=new ArrayList<ProfileInfo>();
			
				conn = ResourceManager.getConnection();
			
		userIds=getAllAndroidUserIds(conn);
		Users user=null;ProfileInfo profile=null;
		UsersDao userDao=UsersDaoFactory.create();
		ProfileInfoDao profileDao=ProfileInfoDaoFactory.create();
		for(int id:userIds){
			user=userDao.findByPrimaryKey(id);
		    if(user!=null){
				profile=profileDao.findByPrimaryKey(user.getProfileId());
			profile.setUserId(user.getId());
			profileList.add(profile);
		}
		}
		request.setAttribute("actionForm", profileList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsersDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ProfileInfoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		}
		return result;	
	}		
	
	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		Login login = (Login) form;
		ActionResult result = new ActionResult();
		Connection conn=null;
		
	try{
		conn = ResourceManager.getConnection();
		switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
			case DELETEREGID:
				deleteDeviceRegId(login,request);
				break;
			case GETANDROID:
				defaultMethod2(login,request,conn);
				break;
			case SENDMESSAGEFORSELECTEDANDROIDUSERS:
				defaultMethod3(login,request,conn);
				break;
		}
		
	}catch(Exception e){
		e.printStackTrace();
	} finally{
		ResourceManager.close(conn);
	}
	return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = (Login) form;
		MailGenerator generator = new MailGenerator();
		PortalMail portalMail = new PortalMail();
		switch (UpdateTypes.getValue(form.getuType())) {
			case FORGOTPASSWORD:
				if (login != null && login.getUserName() != null){
					LoginDao loginDao = LoginDaoFactory.create();
					UsersDao usersDao = UsersDaoFactory.create();
					ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
					try{
						Login loginNew = loginDao.findWhereUserNameEquals(login.getUserName())[0];
						if (loginNew != null){
							Users user = usersDao.findByPrimaryKey(loginNew.getUserId());
							if (user.getProfileId() > 0){
								ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());
								UUID uuid = UUID.randomUUID();
								portalMail.setRecipientMailId(loginNew.getUserName());
								portalMail.setAutoGenPassword(request.getRequestURL().toString().replaceAll(request.getServletPath(), "") + "/pages/changePassword.jsp?uid=" + uuid.toString());
								portalMail.setCandidateName(profileInfo.getFirstName());
								portalMail.setMailSubject("Diksha Lynx: Forgot Password");
								portalMail.setTemplateName(MailSettings.EMPLOYEE_FORGOT_PASSWORD);
								JDBCUtiility.getInstance().update("UPDATE LOGIN SET UUID=? WHERE USER_NAME=?", new Object[] { uuid.toString(), login.getUserName() });
								generator.invoker(portalMail);
							}
						}
					} catch (ArrayIndexOutOfBoundsException arryE){
						logger.info("User name is wrong for forgot password.");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.forgotpassword"));
				//		arryE.printStackTrace();
					} catch (Exception e){
						logger.info("Failed to update new password.");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.updatenewpassword"));
				//		e.printStackTrace();
					}
				}//main if
				break;
			case CHANGEPASSWORD:
				if (login != null){
					String uid = request.getParameter("uuid");
					try{
						if (uid != null && login.getPassword() != null && login.getPassword().length() > 7){
							int affected = JDBCUtiility.getInstance().update("UPDATE LOGIN SET PASSWORD=? , UUID=NULL WHERE UUID=?", new Object[] { DesEncrypterDecrypter.getInstance().encrypt(login.getPassword()), uid });
							if (affected > 0) result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("levels.save.failed", "Password Changed Successfully!!"));
							else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("levels.save.failed", "Password Not Changed"));
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("levels.save.failed", "Password is too short. Please enter min 8 charactors"));
						}
					} catch (ArrayIndexOutOfBoundsException arryE){
						logger.error("User name is wrong for forgot password.", arryE);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("levels.save.failed", "Password Not Changed"));
					} catch (Exception e){
						logger.error("Failed to update new password.", e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("levels.save.failed", "Password Not Changed"));
					}
				}
				break;
			case RECEIVE_LOGIN:
				request.setAttribute("actionForm", "");
				if (login != null){
					String uid = request.getParameter("uuid");
					try{
						if (uid != null){
							Login loginObj = LoginDaoFactory.create().findByDynamicWhere("UUID=?", new Object[] { uid })[0];
							UserBean userBean = new UserBean();
							userBean.setId(loginObj.getUserName());
							request.setAttribute("actionForm", userBean);
						}
					} catch (Exception arryE){
						logger.error("User name is wrong for forgot password.", arryE);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("levels.save.failed", "Invalid request."));
					}
				}
				break;
			default:
				break;
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		Login login = (Login) form;
		ActionResult result = null;
		switch (ActionMethods.ValidationTypes.getValue(login.getvType())) {
			case IsValidSession:{
				result = new ActionResult();
				logger.trace("Logon form ..." + login);
				request.getSession(false).setAttribute("logonForm", login);
				result.setForwardName(ActionResult.SUCCESS);
				break;
			}
			default:
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		Login login = (Login) form;
		ActionResult result = null;
		switch (ActionMethods.ExecuteTypes.getValue(login.geteType())) {
			case LOGOUT:{
				request.getSession(false).invalidate();
				result = new ActionResult();
				result.setDispatchDestination("index.jsp");
				break;
			}
			default:
		}
		return result;
	}
	
	
	
	//                    Diksha Touch App code starts from here                             //
	
	

	// DikshaTouch code starts here	
	// Sending GCM Message to All Android users	
		public  void defaultMethod2(PortalForm form, HttpServletRequest request,Connection conn){
			MulticastResult result = null;
			boolean isRegIdSaved=false;
			Login req = (Login) form;
			Login req1=new Login();
			String status="false";
			String share = req.getShareRegId();
			int userId=0;
			
			// GCM RedgId of Android device to send push notification
			String regId = "";
			if (share != null && !share.isEmpty()) {
				try {
					userId=req.getUserId();	
		    List<Object[]> list=JDBCUtiility.getInstance().getData("SELECT * FROM GCM_REGISTRATION WHERE REG_ID=? and USER_ID=?",new Object[] { req.getRegId(),req.getUserId()}, conn);
		    if(list!=null && list.size()>0){
			for(Object[] obj:list){
				if(obj!=null && obj.length>0){
					int id=(Integer)obj[0];
					String rId=(String)obj[2];
					if(rId.equalsIgnoreCase(req.getRegId())){
					JDBCUtiility.getInstance().update("UPDATE GCM_REGISTRATION SET ID=?,USER_ID=?,REG_ID=? WHERE ID=?", new Object[] {id,userId,rId,id }, conn);	
					status="true";
					req1.setStatus(status);	
					}
					
				}
				
			}
		     }else{
				JDBCUtiility.getInstance().update("INSERT INTO GCM_REGISTRATION(ID,USER_ID,REG_ID) VALUES(?,?,?)", new Object[] {0,req.getUserId(),req.getRegId() }, conn);
				status="true";
				req1.setStatus(status);
		    }		
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("actionForm", req1);
			} else {
				try {
					String cType="BROADCAST";
					Date d=new Date();
					conn = ResourceManager.getConnection();
					String userMessage = req.getMessage();
					JDBCUtiility.getInstance().update("INSERT INTO GCM_BROADCAST_MESSAGE(ID,MESSAGE,TIME) VALUES(?,?,?)", new Object[] {0,userMessage,new Timestamp(d.getTime()) }, conn);
					Sender sender = new Sender(GOOGLE_SERVER_KEY);
					Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
							.addData(MESSAGE_KEY, userMessage).addData(CATEGORY, cType).build();
					Set<String> regIdSet = readFromFile1(conn);
					System.out.println("regIds: " + regIdSet);
					List<String> regIdList = new ArrayList<String>();
					regIdList.addAll(regIdSet);
					result = sender.send(message, regIdList, 1);
					
					request.setAttribute("pushStatus", result.toString());
				} catch (IOException ioe) {
					ioe.printStackTrace();
					request.setAttribute("pushStatus", "RegId required: " + ioe.toString());
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("pushStatus", e.toString());
				}
			}

		}
		
		
		
		private Set<String> readFromFile1(Connection conn) {
			Set<String> regIdSet = new HashSet<String>();
			List<Object[]> list=JDBCUtiility.getInstance().getData("SELECT * FROM GCM_REGISTRATION",null, conn);
			if(list!=null && list.size()>0){
				for(Object[] obj:list){
					String rId=(String)obj[2];
					regIdSet.add(rId);
				}
				}
			return regIdSet;
		}
		
	
		
		private Set<Integer> getAllAndroidUserIds(Connection conn) {
			Set<Integer> uIdSet = new HashSet<Integer>();
			List<Object[]> list=JDBCUtiility.getInstance().getData("SELECT * FROM GCM_REGISTRATION ",null, conn);
			if(list!=null && list.size()>0){
				for(Object[] obj:list){
					int uId=(Integer)obj[1];
					uIdSet.add(uId);
				}
				}
			return uIdSet;
		}

// Sending GCM Message to Selected Android users	
		public  void defaultMethod3(PortalForm form, HttpServletRequest request,Connection conn){
			MulticastResult result = null;
			boolean isRegIdSaved=false;
			Login req = (Login) form;
			int[] userIds=req.getUserIds();
			// GCM RedgId of Android device to send push notification
			String regId = "";
			
				try {
					String cType="SELECTEDBROADCAST";
					String userMessage = req.getMessage();
					Sender sender = new Sender(GOOGLE_SERVER_KEY);
					Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
							.addData(MESSAGE_KEY, userMessage).addData(CATEGORY, cType).build();
					Set<String> regIdSet = selectedUserRegId(conn,userIds);
	//				System.out.println("regIds: " + regIdSet);
					List<String> regIdList = new ArrayList<String>();
					regIdList.addAll(regIdSet);
					result = sender.send(message, regIdList, 1);
					
				} catch (IOException ioe) {
					ioe.printStackTrace();
					request.setAttribute("pushStatus", "RegId required: " + ioe.toString());
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("pushStatus", e.toString());
				}
				
			}

		
		private Set<String> selectedUserRegId(Connection conn,int[] userIds) {
			Set<String> regIdSet = new HashSet<String>();
			
			for(int uId:userIds){
			List<Object[]> list=JDBCUtiility.getInstance().getData("SELECT * FROM GCM_REGISTRATION WHERE USER_ID=?", new Object[] {uId}, conn);
			if(list!=null && list.size()>0){
				for(Object[] obj:list){
					String rId=(String)obj[2];
					regIdSet.add(rId);
				}
				}
			}
			return regIdSet;
		}
		
	public void sendGcmNotification(String userMessage,List<Integer> userIds,String cType){
		Connection conn = null;
		MulticastResult result = null;
		Set<String> regIdSet=null;
		List<String> regIdList = new ArrayList<String>();
		try {
			if(userIds!=null && userIds.size()>0){
			Integer[] ids=new Integer[userIds.size()];
			conn = ResourceManager.getConnection();
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message =new Message.Builder().timeToLive(30).delayWhileIdle(true)
					.addData(MESSAGE_KEY, userMessage).addData(CATEGORY, cType).build();
			regIdSet = getRegIds(conn,userIds.toArray(ids));
//			System.out.println("regIds: " + regIdSet);
			if(regIdSet!=null && regIdSet.size()>0 ){
			regIdList.addAll(regIdSet);
			result = sender.send(message, regIdList, 1);
			logger.info(cType + "Notification of Android message as been sent successfully at "+new Date());
			}	
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error("Failed to send Android Message");
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to send Android Message");
//			request.setAttribute("pushStatus", e.toString());
		} finally{
		ResourceManager.close(conn);
	}	
			
			
		}
		
	private Set<String> getRegIds(Connection conn,Integer[] userIds) {
		Set<String> regIdSet = new HashSet<String>();
		
		for(int uId:userIds){
		List<Object[]> list=JDBCUtiility.getInstance().getData("SELECT * FROM GCM_REGISTRATION WHERE USER_ID=?", new Object[] {uId}, conn);
		if(list!=null && list.size()>0){
			for(Object[] obj:list){
				String rId=(String)obj[2];
				regIdSet.add(rId);
			}
			}
		}
		return regIdSet;
	}

	public void deleteDeviceRegId(Login form,HttpServletRequest request){
		try{
		JDBCUtiility.getInstance().update("DELETE FROM GCM_REGISTRATION WHERE USER_ID=? AND REG_ID=? ", new Object[] {form.getUserId(),form.getRegId()});
		form.setStatus("true");
		
		}catch(Exception e){
			e.printStackTrace();
			form.setStatus("false");
		}
		request.setAttribute("actionForm", form);
	}

	
	
	
	
	
	
}