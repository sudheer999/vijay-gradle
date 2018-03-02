package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.FbpComponent;
import com.dikshatech.beans.Salary;
import com.dikshatech.common.utils.Converter;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.ExportType;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.Status;
import com.dikshatech.common.utils.TDSUtility;
import com.dikshatech.jasper.JGenerator;
import com.dikshatech.jasper.JTemplates;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.FbpConfigDao;
import com.dikshatech.portal.dao.FbpDetailsDao;
import com.dikshatech.portal.dao.FbpReqDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ModulesDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dao.VoluntaryProvidentFundDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.FbpConfig;
import com.dikshatech.portal.dto.FbpConfigPk;
import com.dikshatech.portal.dto.FbpDetails;
import com.dikshatech.portal.dto.FbpDetailsPk;
import com.dikshatech.portal.dto.FbpReq;
import com.dikshatech.portal.dto.FbpReqPk;
import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.VoluntaryProvidentFund;
import com.dikshatech.portal.dto.VoluntaryProvidentFundPk;
import com.dikshatech.portal.exceptions.DaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.FbpConfigDaoException;
import com.dikshatech.portal.exceptions.FbpDetailsDaoException;
import com.dikshatech.portal.exceptions.FbpReqDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.ModulesDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.StatusDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.exceptions.VoluntaryProvidentFundDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.FbpConfigDaoFactory;
import com.dikshatech.portal.factory.FbpDetailsDaoFactory;
import com.dikshatech.portal.factory.FbpReqDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ModulesDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.factory.VoluntaryProvidentFundDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.FBPForm;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class FBPModel extends ActionMethods {

	private enum heads {
		EMP_ID, MONTH_ID, LTA, TPA, MA, CEA, MV, TRA, OA, ELIGIBLE_AMT, USED_AMT, UNUSED_AMT;

		public static heads getValue(String value) {
			try{
				return valueOf(value);
			} catch (Exception e){
				return null;
			}
		};
	};

	final public static String				TODO_APPROVE		= "APPROVE";
	final public static String				TODO_HANDLE			= "HANDLE";
	final private String					COMMENT_PARAGRAPH	= "<p id=\"notifierComment\">";
	final private String					CRLF				= "\r\n";
	final private String					LTA					= "LTA";
	final private String					OA					= "OA";
	final private int						DEADLINE_DATE		= 31;
	final private int						BENCHMARK_YEAR		= 2010;
	final private String					MONTHLY				= "monthly";
	final private String					FBP_MODULE_NAME		= "Flexi-benefit Plan";
	final private int						FBP_MODULE_ID		= getFbpModuelId();
	final private int						REQUEST_TYPE_ID		= 19;
	private static HashMap<String, Method>	fbpConfigMethods	= getFbpConfigMethods();
	private static Logger					logger				= LoggerUtil.getLogger(FBPModel.class);

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Saves the String of the format
	 * LTA=500;TPA=1000;MA=1500;CEA=500;MV=2500;TRA=900;OA=1500;
	 * @throws VoluntaryProvidentFundDaoException 
	 */
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) throws VoluntaryProvidentFundDaoException {
		ActionResult result = new ActionResult();
		FBPForm fbpForm = (FBPForm) form;
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		FbpConfigDao fbpConfigDao = FbpConfigDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		VoluntaryProvidentFundDao voluntaryProvidentFundDao = VoluntaryProvidentFundDaoFactory.create();
		FbpReq fbpReq = null;
		
		Users user = null;
		FbpConfig[] fbpConfigs = null;
		FbpConfig fbpConfig = null;
		FbpReqPk fbpReqPk = null;
		EmpSerReqMapPk esrMapPk = null;
		FbpDetailsPk[] fbpDetailsPk = null;
	    VoluntaryProvidentFundPk VoluntaryProvidentFundPk = null;
	   ;
	    
		ProfileInfo profileInfo = null;
		String comments = null;
		switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
			case SAVE:
				try{
					user = usersDao.findByPrimaryKey(fbpForm.getUserID());
					if (user != null){
						int userId = user.getId();
						fbpConfigs = fbpConfigDao.findWhereLevelIdEquals(user.getLevelId());
						fbpConfig = (fbpConfigs != null && fbpConfigs.length == 1) ? fbpConfigs[0] : null;
						// Saving the FBP Request
						fbpReq = new FbpReq();
						fbpReq.setCreatedOn(new Date());
						fbpReq.setUserId(user.getId());
						fbpReq.setSequence(1);
						fbpReq.setFrequent(fbpForm.getFrequent());
						// Save the comments entered by the raised user
						profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
						if (fbpForm.getComments() != null && !fbpForm.getComments().equals("")){
							if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + user.getEmpId() + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
							fbpReq.setComments(comments);
						}
						// Get the FBPCONFIG DATA
						if (fbpConfigs != null && fbpConfigs.length == 1){
							fbpReq.setLevelId(fbpConfigs[0].getLevelId());
							fbpReq.setLevel(fbpConfigs[0].getLevel());
						} else{
							// Log if no FBPCONFIG Records and Return
							logger.error("There is no FBPCONFIG record or more than one record found for the user, check the configuration table for the user " + userId);
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosaveFBP"));
							return result;
						}
						String monthId = getMonthId();
						if (fbpReq.getFrequent().equalsIgnoreCase(MONTHLY)) fbpReq.setMonthId(monthId);
						else fbpReq.setMonthId(getFinancialYear());
						// Generate the HTML message String
						FbpComponent fbpComponent = new FbpComponent();
						String benefitDetails = fbpForm.getBenefitsDetails();
						String[] benefitsArr = benefitDetails.split(";");
						StringBuffer msgBuffer = new StringBuffer("<table class=\"FbpData\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\"><tr><th>FBP</th><th>Amount</th><tr>");
						for (String eachBenefit : benefitsArr){
							String[] eachBenefitArr = eachBenefit.split("=");
							String fbpAbbr = eachBenefitArr[0].toUpperCase();
							FbpComponent currFbpComponent = fbpComponent.getFbpComponentByName(fbpAbbr);
							msgBuffer.append("<tr><td>" + currFbpComponent.getFbpLabel() + " (" + fbpAbbr + ")</td>");
							msgBuffer.append("<td>" + eachBenefitArr[1] + "</td></tr>");
						}
						//empId
						msgBuffer.append("</table>");
						String msgBody = msgBuffer.toString();
						PortalMail mail = new PortalMail();
						MailGenerator mGenerator = new MailGenerator();
						if (profileInfo != null) mail.setEmpFname(profileInfo.getFirstName() + " " + profileInfo.getLastName());
						mail.setEmployeeId(user.getEmpId());
						mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
						mail.setMessageBody(msgBody);
						mail.setComments(getHtmlComments(fbpReq.getComments()));
						mail.setApproverName("[approverName]");
						try{
							fbpReq.setMessageBody(mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_APPROVAL), mail));
						} catch (FileNotFoundException e){
							logger.error("There is not such file " + MailSettings.FBP_APPROVAL + " found while generating the HTML template for the request " + e.getMessage());
						}
						
						
						fbpReqPk = fbpReqDao.insert(fbpReq);
						// Get the process chain details
						ProcessChain[] processChain = processChainDao.findByDynamicWhere(" ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLES WHERE USER_ID= ?) AND MODULE_ID=?)", new Object[] { user.getId(), FBP_MODULE_ID });
						if (processChain != null && processChain.length == 1){
							ProcessEvaluator evaluator = new ProcessEvaluator();
							// TODO Check and validate process chain being used
							// evaluator.validateProcessChain(processChain,
							// requesterId, checkActiveApprovers,
							// checkActiveHandlers);
							// Generate a new sequence request number for the
							// request like RegIndex-FBP-SeqNo
							FbpReq[] maxFbpReq = fbpReqDao.findByDynamicWhere(" ID = (SELECT MAX(ID) FROM FBP_REQ) ", new Object[] {});
							String fbpReqSeqId = "0";
							if (maxFbpReq != null) fbpReqSeqId = String.valueOf(maxFbpReq[0].getId());
							int size = fbpReqSeqId.length();
							int i = 0;
							while (i < 4 - size){
								fbpReqSeqId = "0" + fbpReqSeqId;
								i++;
							}
							fbpReqSeqId = "-FBP-" + fbpReqSeqId;
							Regions[] regions = RegionsDaoFactory.create().findByDynamicWhere("ID = (SELECT D.REGION_ID FROM DIVISON D WHERE D.ID = (SELECT L.DIVISION_ID FROM LEVELS L WHERE L.ID = (SELECT U.LEVEL_ID FROM USERS U WHERE ID = ?)))", new Object[] { userId });
							if (regions != null) fbpReqSeqId = regions[0].getRefAbbreviation() + fbpReqSeqId;
							else{
								// If nor region Id found for the user then Default
								// taken as ERROR but not returned from Execution
								logger.error(" The region is not specfied for the user " + userId);
								fbpReqSeqId = "ERROR" + fbpReqSeqId;
							}
							// Create a ESR MAP record for the Request
							EmpSerReqMap esrMap = new EmpSerReqMap();
							esrMap.setSubDate(new Date());
							esrMap.setReqId(fbpReqSeqId);
							esrMap.setReqTypeId(REQUEST_TYPE_ID);
							if (regions != null) esrMap.setRegionId(regions[0].getId());
							esrMap.setRequestorId(userId);
							esrMap.setProcessChainId(processChain[0].getId());
							esrMap.setNotify(processChain[0].getNotification());
							Integer[] approvers = evaluator.approvers(processChain[0].getApprovalChain(), 1, userId);
							Integer[] notifiers = evaluator.notifiers(processChain[0].getNotification(), userId);
							Integer[] handlers = evaluator.handlers(processChain[0].getHandler(), userId);
							// Update the ESRMAP ID And Status in FbpReq table
							if (approvers != null && approvers.length > 0){
								fbpReq.setStatus(Status.REQUESTRAISED);
								esrMap.setSiblings(Arrays.toString(approvers).replace("[", "").replace("]", "").replace(" ", ""));
							} else if (handlers != null && handlers.length > 0){
								fbpReq.setStatus(Status.APPROVED);
								esrMap.setSiblings(Arrays.toString(handlers).replace("[", "").replace("]", "").replace(" ", ""));
							} else logger.error("There is no approvers nor handlers configured for the process chain " + processChain[0].getId());
							esrMapPk = esrMapDao.insert(esrMap);
							fbpReq.setEsrMapId(esrMapPk.getId());
							fbpReqDao.update(fbpReqPk, fbpReq);
							// insert into FBP_DETAILS table
							fbpDetailsPk = saveFBPDetails(benefitDetails, userId, fbpReqPk, fbpReq.getFrequent());
							
							
	/*						// insert into VOLUNTARY_PROVIDENT_FUND table
							VoluntaryProvidentFund vpf =  new VoluntaryProvidentFund();
							vpf.setUserId(fbpForm.getUserID());
							vpf.getVPF();
							VoluntaryProvidentFundPk = voluntaryProvidentFundDao.insert(vpf);
							*/
							
							
							
							
							
							// Create Inbox records for Notify and Approver/Handlers
							boolean isSuccessfull = false;
							if (approvers != null && approvers.length > 0) isSuccessfull = createInboxRecords(approvers, notifiers, userId, fbpReqPk, esrMapPk, msgBody);
							else isSuccessfull = createInboxRecords(handlers, notifiers, userId, fbpReqPk, esrMapPk, msgBody);
							if (!isSuccessfull){
								if (fbpDetailsPk != null && fbpDetailsPk.length > 0) for (FbpDetailsPk eachFbpDetailPk : fbpDetailsPk)
									fbpDetailsDao.delete(eachFbpDetailPk);
								if (esrMap != null){
									esrMapDao.delete(esrMapPk);
									Inbox[] inboxEntries = inboxDao.findWhereEsrMapIdEquals(esrMap.getId());
									for (Inbox eachInbox : inboxEntries)
										inboxDao.delete(new InboxPk(eachInbox.getId()));
								}
								if (fbpReq != null) fbpReqDao.delete(fbpReqPk);
							}
						} else{
							// If there are more than one process chain configured
							// for the transaction then reverted
							logger.error("There are more than one process chain records for the user " + user.getId() + " For the  module FBP ");
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosaveFBP"));
							if (fbpReqPk != null) fbpReqDao.delete(fbpReqPk);
							return result;
						}
					} else{
						// If there is no user such user in the users table then the
						// transaction is reverted
						logger.error("There is no Users table record for the user " + fbpForm.getUserID());
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosaveFBP"));
						return result;
					}
				} catch (DaoException e){
					logger.error(" There was a DAOException occured While saving.. Please check");
				}
				break;
			// LTA=500;TPA=1000;MA=1500;CEA=500;MV=2500;TRA=900;OA=1500;
			case SAVEMASTER:
				int confId = fbpForm.getFbpConfId();
				try{
					fbpForm = (FBPForm) form;
					fbpConfig = fbpConfigDao.findByPrimaryKey(confId);
					if (fbpConfig != null){
						String benefitDetails = fbpForm.getBenefitsDetails();
						String[] benefitList = benefitDetails.split(";");
						for (int i = 0; i < benefitList.length; i++){
							String[] benefit = benefitList[i].split("=");
							String methodName = "set" + benefit[0];
							Method method = fbpConfig.getClass().getMethod(methodName, new Class[] { String.class });
							method.invoke(fbpConfig, new Object[] { benefit[1] });
						}
						fbpConfigDao.update(new FbpConfigPk(confId), fbpConfig);
					} else{
						logger.error("There is no such record in the FBPCONFIG Table withe the Id " + confId);
					}
				} catch (FbpConfigDaoException e){
					logger.error("There is a FbpConfigDaoException While querying for the FBPCONFIG table " + e.getMessage());
				} catch (SecurityException e){
					logger.error("There is a SecurityException While querying for the FBPCONFIG table " + e.getMessage());
				} catch (NoSuchMethodException e){
					logger.error("There is a NoSuchMethodException While querying for the FBPCONFIG table " + e.getMessage());
				} catch (IllegalArgumentException e){
					logger.error("There is a IllegalArgumentException While querying for the FBPCONFIG table " + e.getMessage());
				} catch (IllegalAccessException e){
					logger.error("There is a IllegalAccessException While querying for the FBPCONFIG table " + e.getMessage());
				} catch (InvocationTargetException e){
					logger.error("There is a InvocationTargetException While querying for the FBPCONFIG table " + e.getMessage());
				}
				break;
				
			case SAVEVPF:
				VoluntaryProvidentFundDao vpfdap = VoluntaryProvidentFundDaoFactory.create();
				VoluntaryProvidentFund vpf = new VoluntaryProvidentFund();
				vpf.setUserId(fbpForm.getUserID());
				vpf.setVpf(fbpForm.getVpf());
				vpf.setId(fbpForm.getId());
				vpfdap.insert(vpf);
			
				break;
			
			
			
		}
		return result;
	}

	public static String getMonthId() {
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf((cal.get(Calendar.MONTH) + 1));
		if (month.length() == 1) month = "0" + month;
		return (String.valueOf(cal.get(Calendar.YEAR)) + month);
	}

	public static String getFinancialYear() {
		// Calculating Financial year
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		Date fisicalDate, currentDate;
		fisicalDate = currentDate = null;
		currentDate = calendar.getTime();
		calendar.set(Calendar.YEAR, currentYear);
		calendar.set(Calendar.MONTH, Calendar.MARCH);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		fisicalDate = calendar.getTime();
		int startYear = currentDate.after(fisicalDate) ? currentYear : currentYear - 1;
		int endYear = startYear + 1;
		return String.valueOf(startYear) + "-" + String.valueOf(endYear);
	}

	private boolean createInboxRecords(Integer[] approvers, Integer[] notifiers, int userId, FbpReqPk fbpReqPk, EmpSerReqMapPk esrMapPk, String msgBody) {
		boolean successfull = false;
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		EmpSerReqMap esrMap = null;
		ProfileInfo profileInfo = null;
		FbpReq fbpReq = null;
		Users user =null;
		
		try{
			 user = usersDao.findByPrimaryKey(userId);
			esrMap = empSerReqMapDao.findByPrimaryKey(esrMapPk);
			profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
			fbpReq = FbpReqDaoFactory.create().findByPrimaryKey(fbpReqPk);
		} catch (DaoException e){
			logger.error("There was a DaoException for the request ");
		}
		// Create an Inbox Entry for the raised user for notification
		String esrReqNo = null;
		if (esrMap != null){
			esrReqNo = esrMap.getReqId();
			final String subject = "Diksha Lynx: FBP " + esrReqNo + " " + fbpReq.getStatus();
			InboxDao inboxDao = InboxDaoFactory.create();
			Inbox inboxNotify = new Inbox();
			inboxNotify.setReceiverId(userId);
			inboxNotify.setEsrMapId(esrMapPk.getId());
			inboxNotify.setSubject(subject);
			inboxNotify.setAssignedTo(0);
			inboxNotify.setRaisedBy(userId);
			inboxNotify.setCreationDatetime(new Date());
			inboxNotify.setStatus(fbpReq.getStatus());
			inboxNotify.setCategory("FBP");
			inboxNotify.setIsRead(0);
			inboxNotify.setIsDeleted(0);
			inboxNotify.setIsEscalated(0);
			PortalMail mail = new PortalMail();
			MailGenerator mGenerator = new MailGenerator();
			mail.setEmpFname(profileInfo.getFirstName() + " " + profileInfo.getLastName());
			mail.setEmployeeId(user.getEmpId());
			mail.setComments(getHtmlComments(fbpReq.getComments()));
			mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
			mail.setMessageBody(msgBody);
			String message = null;
			try{
				message = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_ACK), mail);
				inboxNotify.setMessageBody(message);
				inboxDao.insert(inboxNotify);
			} catch (FileNotFoundException e){
				logger.error("There is no template file found while sending the mail to the raised user notification for the request " + esrMap.getReqId());
			} catch (InboxDaoException e){
				logger.error("There is InboxDaoException while inserting record fo the notification for the request " + esrMap.getReqId());
			}
			// Send the mail for the raised user for the notification
			mail.setMailBody(message);
			mail.setMailSubject(subject);
			mail.setTemplateName(MailSettings.FBP_ACK);
			sendMail(mail, profileInfo.getOfficalEmailId(), null, null, null);
			// Create record for the Approvers
			Inbox inbox = new Inbox();
			inbox.setEsrMapId(esrMap.getId());
			inbox.setSubject(subject);
			inbox.setRaisedBy(userId);
			inbox.setCreationDatetime(new Date());
			inbox.setStatus(fbpReq.getStatus());
			inbox.setCategory("FBP");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setIsEscalated(0);
			String messageBody = null;
			if (fbpReq != null) messageBody = fbpReq.getMessageBody();
			int eachApprover = 0;
			try{
				for (int i = 0; i < approvers.length; i++){
					eachApprover = approvers[i];
					inbox.setReceiverId(eachApprover);
					inbox.setAssignedTo(eachApprover);
					profileInfo = profileInfoDao.findWhereUserIdEquals(eachApprover);
					String approverName = profileInfo.getFirstName() + " " + profileInfo.getLastName();
					inbox.setMessageBody(messageBody.replaceAll("\\[approverName\\]", approverName));
					inboxDao.insert(inbox);
					// Create the mail for the approvers
					mail.setComments(getHtmlComments(fbpReq.getComments()));
					mail.setMessageBody(getHtmlOfFbpDetails(fbpReq.getId()));
					mail.setApproverName(approverName);
					mail.setEmployeeId(user.getEmpId());
					mail.setTemplateName(MailSettings.FBP_APPROVAL);
					mail.setMailBody(mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_APPROVAL), mail));
					sendMail(mail, profileInfo.getOfficalEmailId(), null, null, null);
				}
				// Create Records for the notification
				inbox = new Inbox();
				inbox.setSubject(subject);
				inbox.setEsrMapId(esrMapPk.getId());
				inbox.setAssignedTo(0);
				inbox.setRaisedBy(userId);
				inbox.setCreationDatetime(new Date());
				inbox.setStatus(fbpReq.getStatus());
				inbox.setCategory("FBP");
				inbox.setIsRead(0);
				inbox.setIsDeleted(0);
				inbox.setIsEscalated(0);
				mail.setApproverName("\\[approverName\\]");
				mail.setComments(getHtmlComments(fbpReq.getComments()));
				String msgString = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_NOTIFY), mail);
				ProfileInfo profileInfos = null;
				for (int eachNotifier : notifiers){
					profileInfos = profileInfoDao.findWhereUserIdEquals(eachNotifier);
					if (profileInfos != null) profileInfo = profileInfos;
					String repMsgString = msgString.replaceAll("\\[approverName\\]", profileInfo.getFirstName() + " " + profileInfo.getLastName());
					inbox.setMessageBody(repMsgString);
					inbox.setReceiverId(eachNotifier);
					inboxDao.insert(inbox);
					mail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
					mail.setEmployeeId(user.getEmpId());
					mail.setMailBody(repMsgString);
					mail.setTemplateName(MailSettings.FBP_NOTIFY);
					sendMail(mail, profileInfo.getOfficalEmailId(), null, null, null);
				}
				successfull = true;
			} catch (ProfileInfoDaoException e){
				logger.error("There is no profile Info record found for the user configured as the approver for the ID " + eachApprover + e.getMessage());
			} catch (InboxDaoException e){
				logger.error("There is an InboxDaoException thrown while inserting record for the approver ID " + eachApprover + e.getMessage());
			} catch (FileNotFoundException e){
				logger.error("There is no file like " + MailSettings.FBP_APPROVAL + " while getting the template to send mails for the FBP request for " + eachApprover + e.getMessage());
			}
		}
		return successfull;
	}

	public void sendMail(PortalMail mail, String receipent, String[] allReceipent, String sender, String[] cc) {
		MailGenerator generator = new MailGenerator();
		mail.setRecipientMailId(receipent);
		mail.setAllReceipientMailId(allReceipent);
		mail.setFromMailId(sender);
		mail.setAllReceipientcCMailId(cc);
		try{
			generator.invoker(mail);
		} catch (Exception e){
			logger.error("There is an exception while sending the mail for the raised user mail " + receipent + e.getMessage());
		}
	}

	/**
	 * This method saves all the FBP details sub records for the FBPReq record
	 * saved
	 * 
	 * @param benefitDetails
	 * @param userId
	 * @return
	 */
	public FbpDetailsPk[] saveFBPDetails(String benefitDetails, int userId, FbpReqPk fbpReqPk, String frequency) {
		FbpConfigDao fbpConfigDao = FbpConfigDaoFactory.create();
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		boolean successfullySaved = false;
		Users users = null;
		FbpDetails fbpDetails = null;
		FbpConfig[] fbpConfigs = null;
		List<FbpDetailsPk> fbpDetailsPkList = new ArrayList<FbpDetailsPk>();
		try{
			users = usersDao.findByPrimaryKey(userId);
			if (users != null){
				int userLevelId = users.getLevelId();
				fbpConfigs = fbpConfigDao.findWhereLevelIdEquals(userLevelId);
				if (fbpConfigs != null && fbpConfigs.length == 1){
					FbpConfig fbpConfig = fbpConfigs[0];
					String[] benefitsList = benefitDetails.split(";");
					for (int i = 0; i < benefitsList.length; i++){
						String[] benefit = benefitsList[i].split("=");
						String fbpAbbr = benefit[0];
						FbpComponent fbpComponent = new FbpComponent().getFbpComponentByName(fbpAbbr);
						fbpDetails = getFbpDetails(userId, "User-level", fbpComponent, fbpConfig);
						fbpDetails.setFbpId(fbpReqPk.getId());
						String methodResStr = fbpDetails.getAnnualEligibleAmt();
						if (frequency.equalsIgnoreCase("yearly")){
							if ((!methodResStr.equalsIgnoreCase("NA")) && (!methodResStr.equalsIgnoreCase("UA"))){
								fbpDetails.setUsedAmt(benefit[1]);
								if (fbpDetails.getFbp().equalsIgnoreCase("LTA") && !benefit[1].equalsIgnoreCase("NA")) fbpDetails.setUnusedAmt(String.valueOf(Float.valueOf(fbpDetails.getTotalEligibleAmt()) - Float.valueOf(benefit[1])));
								else if (!fbpDetails.getFbp().equalsIgnoreCase("LTA")) fbpDetails.setUnusedAmt(String.valueOf(Float.valueOf(fbpDetails.getAnnualEligibleAmt()) - Float.valueOf(benefit[1])));
							} else if (methodResStr.equalsIgnoreCase("UA")) {
								SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
								String sum = 0 + "";
								SalaryDetails[] grossSalaries = salaryDetailsDao.findByDynamicWhere(
										" USER_ID = ? AND SAL_ID IN (5) ORDER BY SAL_ID ", new Object[] { userId });
								if (grossSalaries == null || grossSalaries.length <= 0) {
									logger.error(" There is FbpReqDaoException while querying FBP_REQ for the user "
											+ userId);
								} else {
									float amt = 0;
									float totalFbp = 0;
									for (SalaryDetails salaryDetails : grossSalaries) {
										sum = (DesEncrypterDecrypter.getInstance()
												.encrypt(Math.round(Double.parseDouble((DesEncrypterDecrypter
														.getInstance().decrypt(salaryDetails.getAnnual())))) + ".00"));// total

										String[] benefitsListt = benefitDetails.split(";"); // monthly
										for (int j = 0; j < benefitsListt.length; j++) {
											String[] benefitt = benefitsListt[j].split("=");
											String fbpAbbrt = benefitt[1];
											if (!fbpAbbrt.equalsIgnoreCase("NA")) {
												totalFbp += Float.valueOf(fbpAbbrt); // getting
																						// total
																						// FBP
																						// decloared
											}
										}
										String otherAllowances = benefit[1];// OA
																			// coming
																			// from
																			// UI

										String total = (DesEncrypterDecrypter.getInstance().decrypt(sum));// annual

										amt = Float.valueOf(total) - totalFbp;// balance
																				// amt
																				// which
																				// is
																				// not
																				// part
																				// of
																				// FBP
																				// has
																				// to
																				// be
																				// in
																				// OA
										amt += Float.valueOf(otherAllowances);// adding
																				// Unused
																				// amt
																				// and
																				// OA
																				// coming
																				// from
																				// UI
																				// to
																				// OA
																				// finally

										fbpDetails.setUsedAmt(String.valueOf(amt));

									}
								}

							} else{
								fbpDetails.setEligibleAmt(methodResStr);
								fbpDetails.setUsedAmt(methodResStr);
								fbpDetails.setUnusedAmt(methodResStr);
							}
						} else if (frequency.equalsIgnoreCase("monthly")){
							if (!(methodResStr.equalsIgnoreCase("NA") || methodResStr.equalsIgnoreCase("UA"))){
								fbpDetails.setUsedAmt(benefit[1]);
								if (fbpDetails.getFbp().equalsIgnoreCase("LTA") && !benefit[1].equalsIgnoreCase("NA")) fbpDetails.setUnusedAmt(String.valueOf(Float.valueOf(fbpDetails.getTotalEligibleAmt()) - Float.valueOf(benefit[1])));
								else if (!fbpDetails.getFbp().equalsIgnoreCase("LTA")) fbpDetails.setUnusedAmt(String.valueOf(Float.valueOf(fbpDetails.getUnusedAmt()) - Float.valueOf(benefit[1])));
							} else if (methodResStr.equalsIgnoreCase("UA")){
								fbpDetails.setEligibleAmt("NA");
								fbpDetails.setUsedAmt(benefit[1]);
								fbpDetails.setUnusedAmt("NA");
							} else{// For NA
								fbpDetails.setEligibleAmt(methodResStr);
								fbpDetails.setUsedAmt(methodResStr);
								fbpDetails.setUnusedAmt(methodResStr);
							}
						}
						fbpDetailsPkList.add(fbpDetailsDao.insert(fbpDetails));
					}
					successfullySaved = true;
				} else{
					logger.error("There is a problem in FBP_CONGIF table as it is returning null / more than one row for the level " + users.getLevelId());
				}
			} else{
				logger.error("There is no USERS record for the user " + userId);
			}
		} catch (DaoException e){
			logger.error("There is a Dao exception for the user " + userId + e.getMessage());
		}
		if (!successfullySaved){
			for (FbpDetailsPk fbpDetailsPk : fbpDetailsPkList){
				try{
					fbpDetailsDao.delete(fbpDetailsPk);
				} catch (FbpDetailsDaoException e){
					logger.error("There is a FbpDetailsDaoException for the user " + userId + " While deleteting Half saved FBP DETAILS records due to exception occured During the saving of FBP");
					e.getMessage();
				}
			}
		}
		return (FbpDetailsPk[]) fbpDetailsPkList.toArray(new FbpDetailsPk[fbpDetailsPkList.size()]);
	}

	/*private String getUntilisedAmt(int userId, String benefitName, String eligibleAmt) {
		String uA = null;
		Connection con = null;
		try{
			con = ResourceManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT COALESCE(SUM(D.USED_AMT),0) " + "FROM FBP_REQ R JOIN FBP_DETAILS D " + "ON R.ID = D.FBP_ID " + "WHERE R.USER_ID = ? " + "AND D.FBP = ? " + "AND R.STATUS = '" + Status.PROCESSED + "' " + "AND R.CREATED_ON BETWEEN ? AND ? ");
			pstmt.setInt(1, userId);
			pstmt.setString(2, benefitName.toUpperCase());
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			if (month < 4){
				// Then JanFebMar, previous year is starting Current year is
				// ending
				cal.set(year - 1, Calendar.APRIL, 01);
				pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
				cal.set(year, Calendar.MARCH, 31);
				pstmt.setString(4, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
			} else{
				// Current year is Starting year, next year is ending year
				cal.set(year, Calendar.APRIL, 01);
				pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
				cal.set(year + 1, Calendar.MARCH, 31);
				pstmt.setString(4, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
			}
			ResultSet res = pstmt.executeQuery();
			if (res.next()){
				uA = res.getString(1);
			}
			// if(uA == null) uA = "0";
		} catch (SQLException e){
			logger.error("There is a SqlException found while picking the unused amount for the user " + userId + e.getMessage());
		} finally{
			if (con != null) ResourceManager.close(con);
		}
		return String.valueOf(Float.valueOf(eligibleAmt) - Float.valueOf(uA));
	}*/
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) throws VoluntaryProvidentFundDaoException {
		ActionResult result = new ActionResult();
		FBPForm fbpForm = (FBPForm) form;
		FbpConfigDao fbpConfigDao = FbpConfigDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		Login login = Login.getLogin(request);
		int userId = login.getUserId();
		//Integer pUserId = form.getUserId();
		//int paramUserId = pUserId != null ? pUserId : 0;
		int paramFbpId = fbpForm.getFbpId();
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
			case RECEIVEMASTER:
				// Returns FBP declaration manual for logged-in user
				try{
					fbpForm = getFbpMaster(userId, fbpForm);
					if (fbpForm != null) request.setAttribute("actionForm", fbpForm);
					else{
						logger.debug("Unable to retrieve flexible benefit plan form bean for USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveMaster"));
					}
				} catch (UsersDaoException ex){
					logger.error("Unable to retrieve flexible benefit plan data for user of USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveLevel"));
				} catch (LevelsDaoException ex){
					logger.error("Unable to retrieve flexible benefit plan data for level of USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveLevel"));
				} catch (FbpConfigDaoException ex){
					logger.error("Unable to retrieve flexible benefit plan configuration for level of USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveMaster"));
				} catch (FbpDetailsDaoException ex){
					logger.error("Unable to retrieve flexible benefit plan history for USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveHistory"));
				} catch (Exception e){
					logger.error("Unable to retrieve flexible benefit plan configuration for level of USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveMaster"));
				}
				break;
			case RECEIVE:
			
				if (paramFbpId > 0){
					try{
						fbpForm = getFbpDetails(paramFbpId, fbpForm);
						request.setAttribute("actionForm", fbpForm);
					} catch (FbpReqDaoException ex){
						logger.error("Unable to retrieve flexible benefit plan data for USER_ID: " + userId, ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
					} catch (FbpDetailsDaoException ex){
						logger.error("Unable to retrieve flexible benefit plan history for USER_ID: " + userId, ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
					} catch (FbpConfigDaoException ex){
						logger.error("Unable to retrieve flexible benefit plan configuration for level of USER_ID: " + userId, ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveMaster"));
					} catch (ProfileInfoDaoException ex){
						logger.error("Unable to retrieve name for USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveMaster"));
					} catch (Exception e){
						logger.error("Unable to retrieve flexible benefit plan configuration for level of USER_ID: " + userId, e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
					}
				} else{
					logger.error("Unable to retrieve flexible benefit plan details for " + paramFbpId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
				}
				break;
			case RECEIVEALL:
				// Returns all FBP declaration for logged-in user for all year
				try{
					fbpForm = getFbpForUser(userId);
					if (fbpForm != null) request.setAttribute("actionForm", fbpForm);
					else{
						logger.debug("Unable to retrieve flexible benefit plan form bean for USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveAll"));
					}
				} catch (FbpReqDaoException e){
					logger.error("Unable to retrieve flexible benefit plan data for USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveAll"));
				} catch (InboxDaoException e){
					logger.error("Unable to retrieve flexible benefit plan data for USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveAll"));
				}
				break;
			case RECEIVEALLTOAPPROVE:
				try{
					fbpForm = getFbpToApprove(userId);
					if (fbpForm != null) request.setAttribute("actionForm", fbpForm);
					else{
						logger.debug("Unable to retrieve flexible benefit plan form bean for USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveToApprove"));
					}
				} catch (FbpReqDaoException e){
					logger.error("Unable to retrieve flexible benefit plan data for USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveToApprove"));
				}
				break;
			case RECEIVEALLTOHANDLE:
				try{
					fbpForm = getFbpToHandle(userId);
					if (fbpForm != null) request.setAttribute("actionForm", fbpForm);
					else{
						logger.debug("Unable to retrieve flexible benefit plan form bean for USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveToHandle"));
					}
				} catch (FbpReqDaoException e){
					logger.error("Unable to retrieve flexible benefit plan data for USER_ID: " + userId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveToHandle"));
				}
				break;
			case RECEIVETOHANDLE:
				if (paramFbpId > 0){
					try{
						fbpForm = getFbpToHandle(userId, paramFbpId, fbpForm);
						request.setAttribute("actionForm", fbpForm);
					} catch (FbpReqDaoException ex){
						logger.error("Unable to retrieve flexible benefit plan data for USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
					} catch (FbpDetailsDaoException ex){
						logger.error("Unable to retrieve flexible benefit plan history for USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
					} catch (FbpConfigDaoException ex){
						logger.error("Unable to retrieve flexible benefit plan configuration for level of USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceiveMaster"));
					} catch (Exception e){
						logger.error("Unable to retrieve flexible benefit plan configuration for level of USER_ID: " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
					}
				} else{
					logger.error("Unable to retrieve flexible benefit plan details for " + paramFbpId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.fbpReceive"));
				}
				break;
			case RECEIVECONFIG:
				DropDown dropDown = new DropDown();
				String levelName = fbpForm.getLevelName();
				DivisonDao divisonDao = DivisonDaoFactory.create();
				Levels level = null;
				List<Object> listOfObject = new ArrayList<Object>();
				try{
					FbpConfig[] fbpConfigs = fbpConfigDao.findWhereLevelEquals(levelName);
					for (FbpConfig eachFBPConfig : fbpConfigs){
						level = levelsDao.findByPrimaryKey(eachFBPConfig.getLevelId());
						Divison division = divisonDao.findByPrimaryKey(level.getDivisionId());
						eachFBPConfig.setDesignation(level.getDesignation());
						eachFBPConfig.setDivision(division.getName());
						listOfObject.add(eachFBPConfig);
					}
					dropDown.setDropDown(listOfObject.toArray(new Object[listOfObject.size()]));
				} catch (DaoException e){
					logger.error("There is a DaoException occured while getting the Config data for the Id " + levelName + " " + e.getMessage());
				}
				request.setAttribute("actionForm", dropDown);
				break;
			default:
				break;
		}
		return result;
	}

	/**
	 * Returns a list of all flexible benefit plan (FBP) request for given user
	 * id. To be displayed in the FBP module dash board.
	 * 
	 * @param userId
	 * @throws FbpReqDaoException
	 * @throws InboxDaoException
	 * @throws VoluntaryProvidentFundDaoException 
	 * @throws EmpSerReqMapDaoException
	 */
	protected FBPForm getFbpForUser(int userId) throws FbpReqDaoException, InboxDaoException, VoluntaryProvidentFundDaoException {
		final String SELECT_REQUEST = "SELECT ESR.REQ_ID,RT.NAME FROM EMP_SER_REQ_MAP AS ESR INNER JOIN REQUEST_TYPE AS RT ON ESR.REQ_TYPE_ID=RT.ID AND ESR.ID=?";
		FbpReqDao fbpReqProvider = FbpReqDaoFactory.create();
		FBPForm fbpForm = new FBPForm();
		// Get all FBP request by logged-in user
		FbpReq[] fbpRequests = fbpReqProvider.findWhereUserIdEquals(userId);
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentDate = calendar.get(Calendar.DATE);
		String monthId = String.valueOf(currentYear);
		if (currentMonth < 10) monthId += "0" + currentMonth;
		else monthId += currentMonth;
		boolean newFlag = false;
		if (currentDate <= DEADLINE_DATE) {
			newFlag = true;
		}
		else newFlag = false;
		for (FbpReq element : fbpRequests){
			// Process details to get requester name and financial year string
			element = getFbpReqProcessed(element);
			element.setMessageBody(null);// Removing message body for receiveAll
			String status = element.getStatus();
			String elementMonthId = element.getMonthId();
			Connection conn = null;
			try{
				conn = ResourceManager.getConnection();
				// Get requestId IN-FBP-0005 and request name for current FBP
				// request
				PreparedStatement stmt = conn.prepareStatement(SELECT_REQUEST);
				stmt.setInt(1, element.getEsrMapId());
				ResultSet result = stmt.executeQuery();
				if (result.next()){
					element.setReqId(result.getString(1));
					element.setRequestName(result.getString(2));
				} else logger.debug("No request type exists for ESR_MAP_ID: " + element.getId());
				ResourceManager.close(conn);
			} catch (SQLException ex){
				logger.error("Unable to get REQ_ID AND REQUEST_TYPE for ESR_MAP_ID: " + element.getId());
				logger.error(ex.getMessage());
			} finally{
				if (conn != null){
					ResourceManager.close(conn);
				}
			}
			elementMonthId = elementMonthId == null ? "" : elementMonthId;
			Date createdOn = element.getCreatedOn();
			calendar.setTime(createdOn);
			// Reset newFlag to false if yearly declaration made for currentYear
			// and is not cancelled or rejected
			if (((element.getFrequent().equalsIgnoreCase("yearly") && elementMonthId.equals(getFinancialYear())) || (element.getFrequent().equalsIgnoreCase("monthly") && elementMonthId.equals(monthId))) && (status.equalsIgnoreCase(Status.REQUESTRAISED) || status.equalsIgnoreCase(Status.PENDINGAPPROVAL) || status.equalsIgnoreCase(Status.APPROVED) ||status.equalsIgnoreCase(Status.PROCESSED)   ))
			{
				// If declaration made for current month and not cancelled
				// set newFlag to false otherwise true
				element.setCancelFlag(true);
				newFlag = true;
			}
			// else
			// if((element.getFrequent().equalsIgnoreCase("yearly")&&elementMonthId.equals(getFinancialYear()))
			// &&!(status.equalsIgnoreCase(Status.REJECTED)||status.equalsIgnoreCase(Status.CANCELLED))){
			// newFlag=false;
			// }
			else if (((element.getFrequent().equalsIgnoreCase("yearly") && elementMonthId.equals(getFinancialYear())) || (element.getFrequent().equalsIgnoreCase("monthly") && elementMonthId.equals(monthId))) && !status.equalsIgnoreCase(Status.CANCELLED) && !status.equalsIgnoreCase(Status.REJECTED))
			{
				newFlag = false;
			} else{
				logger.debug("No operation for flexi benefit plan request FBP_REQ.ID: " + element.getId());
			}
		}
		// Creating formbean to return result
		if (fbpRequests.length > 0){
			fbpForm.setLevel(fbpRequests[0].getLevel());
			fbpForm.setLevelId(fbpRequests[0].getLevelId());
		} else{
			logger.debug("No previous fbp declaration made by USER_ID: " + userId);
		}
		fbpForm.setUserID(userId);
		fbpForm.setNewFlag(newFlag);
		// Setting approveFlag and handleFlag
		fbpForm.setApproveFlag(FBPModel.getFbpToDoFlag(userId, TODO_APPROVE));
		fbpForm.setHandleFlag(FBPModel.getFbpToDoFlag(userId, TODO_HANDLE));
		// Setting all requests for logged-in user
		fbpForm.setRequests(fbpRequests);
		
		// getting vpf for user 
		
		VoluntaryProvidentFundDao vpfdao = VoluntaryProvidentFundDaoFactory.create();
		VoluntaryProvidentFund[] vpf = vpfdao.findByVpf("SELECT * FROM VPF_REQ where USER_ID = ? ORDER BY ID DESC LIMIT 1", new Object[]{(userId)} );
		if(vpf==null)
		{
			logger.debug("No previous vpf declaration made by USER_ID: " + userId);
		}
		else
		{
		for (VoluntaryProvidentFund voluntaryProvidentFund : vpf) {
			fbpForm.setVpf(voluntaryProvidentFund.getVpf());
			
		}
		}
		
		return (fbpForm);
	}

	public static boolean getFbpToDoFlag(int userId, String type) throws InboxDaoException {
		final String WHERE_INBOX = "ESR_MAP_ID IN (SELECT DISTINCT ESR_MAP_ID FROM FBP_REQ) AND RECEIVER_ID=ASSIGNED_TO AND RECEIVER_ID=?";
		final String statusClause = " AND STATUS IN ('";
		boolean toDoFlag = false;
		String toDoStatus = null;
		StringBuilder sb = new StringBuilder(statusClause);
		if (type.equalsIgnoreCase(TODO_APPROVE)){
			sb.append(Status.REQUESTRAISED);
			sb.append("','");
			sb.append(Status.PENDINGAPPROVAL);
			sb.append("')");
		} else if (type.equalsIgnoreCase(TODO_HANDLE)){
			sb = new StringBuilder(statusClause);
			sb.append(Status.APPROVED);
			sb.append("','");
			sb.append(Status.ASSIGNED);
			sb.append("','");
			sb.append(Status.INPROGRESS);
			sb.append("')");
		} else{
			sb = new StringBuilder("");
		}
		toDoStatus = sb.toString();
		String toDoQuery = WHERE_INBOX + toDoStatus;
		InboxDao inboxProvider = InboxDaoFactory.create();
		Inbox[] inbox = inboxProvider.findByDynamicWhere(toDoQuery, new Object[] { userId });
		toDoFlag = inbox.length > 0;
		return (toDoFlag);
	}

	protected FBPForm getFbpMaster(int userId, FBPForm fbpForm1) throws LevelsDaoException, UsersDaoException, FbpConfigDaoException, FbpDetailsDaoException, ProfileInfoDaoException {
		boolean isYearlyFlag = true;
		UsersDao userProvider = UsersDaoFactory.create();
		Users user = null;
		LevelsDao levelProvider = LevelsDaoFactory.create();
		Levels userLevel = null;
		int userLevelId = 0;
		int empId = 0;
		String userLevelName = null;
		user = userProvider.findByPrimaryKey(userId);
		userLevelId = user != null ? user.getLevelId() : 0;
		userLevel = levelProvider.findByPrimaryKey(userLevelId);
		userLevelName = userLevel != null ? userLevel.getLabel() : null;
		empId = user != null ? user.getEmpId() : 0;
		ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
		ProfileInfo profile = profileInfoProvider.findWhereUserIdEquals(userId);
		String empName = profile != null ? profile.getFirstName() + " " + profile.getLastName() : "Unknown";
		fbpForm1.setEmpId(empId);
		fbpForm1.setEmpName(empName);
		FbpConfigDao fbpConfigProvider = FbpConfigDaoFactory.create();
		FbpConfig[] userFbpConfig = fbpConfigProvider.findWhereLevelIdEquals(userLevelId);
		if (userFbpConfig.length > 0){
			FbpConfig currentFbpConfig = userFbpConfig[0];
			fbpForm1.setLevel(currentFbpConfig.getLevel());
			fbpForm1.setLevelId(currentFbpConfig.getLevelId());
			fbpForm1.setUserID(userId);
			List<FbpDetails> benefits = new ArrayList<FbpDetails>();
			List<FbpComponent> fbpComponents = new FbpComponent().getFbpComponents();
			float sumMonthlyEligibleAmt = 0.0f;
			float sumAnnualEligibleAmt = 0.0f;
			FbpDetails oaFbp = null;
			for (FbpComponent element : fbpComponents){
				if (element.getFbp().equalsIgnoreCase("OA")) continue;
				FbpDetails newFbpDetails = getFbpDetails(userId, userLevelName, element, currentFbpConfig);
				if (newFbpDetails != null){
					newFbpDetails.setFbpOrder(element.getOrder());
					benefits.add(newFbpDetails);
					String mthEA = newFbpDetails.getEligibleAmt();
					String annEA = newFbpDetails.getAnnualEligibleAmt();
					if (!(mthEA.equalsIgnoreCase("NA") || mthEA.equalsIgnoreCase("UA"))) sumMonthlyEligibleAmt += Float.valueOf(mthEA);
					if (!(annEA.equalsIgnoreCase("NA") || annEA.equalsIgnoreCase("UA"))) sumAnnualEligibleAmt += Float.valueOf(annEA);
					if (annEA.equals("UA") && newFbpDetails.getFbp().equals(OA)) oaFbp = newFbpDetails;
					fbpForm1.setOaAmt(String.valueOf(sumAnnualEligibleAmt));
				} else{
					logger.debug("Null FbpDetails object returned for FBP_COMPONENT: " + element.getFbp());
				}
			}
			if (oaFbp != null){
				oaFbp.setEligibleAmt(String.valueOf(sumMonthlyEligibleAmt));
				oaFbp.setAnnualEligibleAmt(String.valueOf(sumAnnualEligibleAmt));
			}
			try{
				FbpReqDao fbpReqProvider = FbpReqDaoFactory.create();
				FbpReq[] userFbpReq = fbpReqProvider.findByDynamicWhere("UCASE(FREQUENT)='MONTHLY' AND STATUS!=? AND USER_ID=?", new Object[] { Status.CANCELLED, userId });
				if (userFbpReq.length > 0) isYearlyFlag = false;
				else isYearlyFlag = true;
				fbpForm1.setYearlyFlag(isYearlyFlag);
				String selectLtaFbq = "SELECT FBP_REQ.* FROM FBP_REQ INNER JOIN FBP_DETAILS ON FBP_REQ.ID=FBP_DETAILS.FBP_ID WHERE FBP='LTA' AND FBP_REQ.USER_ID=?";
				FbpReq[] userFbpLtaReq = fbpReqProvider.findByDynamicSelect(selectLtaFbq, new Object[] { userId });
				FbpComponent lta = new FbpComponent().getFbpComponentByName(this.LTA);
				String description = lta.getFbpLabel() + " availed.";
				if (userFbpLtaReq.length >= lta.getOccurence()){
					for (FbpDetails element : benefits){
						if (element.getFbp().equalsIgnoreCase(this.LTA)){
							element.setEditFlag(false);
							element.setDescription(description);
						}
					}
				}
			} catch (FbpReqDaoException ex){
				logger.error("Unable to set yearlyFlag for the RECEIVE MASTER for USERID: " + userId, ex);
			}
			fbpForm1.setFbpAmt(getSalaryFbp(userId));
			// Use Collections algorith to sort ArrayList
			sortBenefit(benefits);
			fbpForm1.setBenefits(benefits);
			return (fbpForm1);
		} else{
			logger.debug("No FBP configuration found. Admin action required.");
			throw new FbpConfigDaoException("No FBP configuration found. Admin action required.");
		}
	}

	protected FbpDetails getFbpDetails(int userId, String userLevelName, FbpComponent element, FbpConfig currentFbpConfig) throws FbpDetailsDaoException {
		String fbpAbbr = element.getFbp();
		String fbpLabel = element.getFbpLabel();
		FbpDetails newFbpDetails = new FbpDetails();
		newFbpDetails.setFbpId(0);// This will be a new request and fbpId is
									// FBP_REQ.ID
		// Eligible amount for an year
		String annualEligibleAmtStr = getFbpEligibleAmount(fbpAbbr, currentFbpConfig);
		String eligibleAmtStr = null;
		String description = null;
		float annualEligibleAmount = 0.0f;
		float currentEligibleAmount = 0.0f;
		float usedAmount = 0.0f;
		boolean fbpEditFlag = true;
		if (annualEligibleAmtStr.equals("NA") || annualEligibleAmtStr.equals("UA")){
			eligibleAmtStr = annualEligibleAmtStr;
			if (annualEligibleAmtStr.equals("UA")){
				eligibleAmtStr = "NA";
				description = "Unused benefits. Calculated based on you declaration.";
			} else{
				description = "Not applicable for your level- " + userLevelName;
			}
			newFbpDetails.setFbp(fbpAbbr);
			newFbpDetails.setEditFlag(false);
			newFbpDetails.setFbpLabel(fbpLabel);
			newFbpDetails.setAnnualEligibleAmt(annualEligibleAmtStr);// Amount
																		// from
																		// FBP_CONFIG
			newFbpDetails.setEligibleAmt(eligibleAmtStr);// Calculated
															// Monthly/Periodic
															// eligible amount
			newFbpDetails.setUsedAmt("0.0");// 0 for all receive. Send by user
											// later
			newFbpDetails.setUnusedAmt(eligibleAmtStr);// Calculated for the
														// year/period
			newFbpDetails.setAccruedEligibleAmt(eligibleAmtStr);// Calculated
																// for
																// FBP_COMPONENT
																// more than
																// annual
			newFbpDetails.setDescription(description);
			newFbpDetails.setTotalEligibleAmt(annualEligibleAmtStr);// Calculated
																	// for
																	// FBP_COMPONENT
																	// more than
																	// annual.
																	// LTA-
																	// 4years
			return (newFbpDetails);
		} else{
			annualEligibleAmount = Float.valueOf(annualEligibleAmtStr);
			annualEligibleAmtStr = String.valueOf(annualEligibleAmount);
		}
		newFbpDetails.setFbp(fbpAbbr);
		newFbpDetails.setFbpLabel(fbpLabel);
		// Annual amount from FBP_CONFIG
		newFbpDetails.setAnnualEligibleAmt(annualEligibleAmtStr);
		// Amount eligible for whole period either annual or more than 1 year
		// For n year it is n * annualEligibleAmt
		newFbpDetails.setTotalEligibleAmt(annualEligibleAmtStr);
		// Amount declared for current declaration
		newFbpDetails.setUsedAmt(String.valueOf(0.0f));
		int fbpPeriod = element.getPeriod();
		int periodInYear = fbpPeriod / 12;
		int periodInMonth = fbpPeriod % 12;
		if (periodInYear > 0 && fbpAbbr.equals(LTA)){
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// int currentMonth=calendar.get(Calendar.MONTH);
			int accruedPeriod = (currentYear - BENCHMARK_YEAR) % periodInYear + 1;
			int startYear = currentYear - (currentYear - BENCHMARK_YEAR) % periodInYear;
			int endYear = startYear + periodInYear - 1;
			int totalPeriod = endYear - startYear + 1;
			Date startDate, endDate;
			startDate = endDate = null;
			calendar.set(Calendar.YEAR, startYear);//
			calendar.set(Calendar.MONTH, Calendar.JANUARY);// April
			calendar.set(Calendar.DATE, 1);// 1st
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			startDate = calendar.getTime();
			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, endYear);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DATE, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 11);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			endDate = calendar.getTime();
			float accruedEligibleAmount = annualEligibleAmount * accruedPeriod;
			float totalEligibleAmount = annualEligibleAmount * totalPeriod;
			usedAmount = getFbpUsedAmount(fbpAbbr, userId, startDate, endDate);
			final String WHERE_FBPDETAILS = "FBP=? AND FBP_ID IN (" + "SELECT ID FROM FBP_REQ WHERE USER_ID=? AND STATUS!=? AND CREATED_ON >=? AND CREATED_ON <=?)";
			FbpDetailsDao fbpDetailsProvider = FbpDetailsDaoFactory.create();
			FbpDetails[] previousFbpDetails = fbpDetailsProvider.findByDynamicWhere(WHERE_FBPDETAILS, new Object[] { fbpAbbr, userId, Status.CANCELLED, startDate, endDate });
			if (previousFbpDetails.length >= element.getOccurence()) fbpEditFlag = false;
			currentEligibleAmount = Math.round(accruedEligibleAmount - usedAmount);
			String currentEligibleAmtStr = String.valueOf(currentEligibleAmount);
			// Maximum amount user can feed in the tax field for current
			// declaration
			// Accrued benefit minus obtained benefit
			newFbpDetails.setEligibleAmt(currentEligibleAmtStr);
			// Amount accrued benefit
			newFbpDetails.setAccruedEligibleAmt(String.valueOf(accruedEligibleAmount));
			// Total benefit for the period annualEligibleAmount * n years
			newFbpDetails.setTotalEligibleAmt(String.valueOf(totalEligibleAmount));
			newFbpDetails.setUnusedAmt(String.valueOf(totalEligibleAmount - usedAmount));
			/*String sd, cd, ed;
			sd = PortalUtility.getdd_MM_yyyy(startDate);
			cd = PortalUtility.getdd_MM_yyyy(currentDate);
			ed = PortalUtility.getdd_MM_yyyy(endDate);*/
			StringBuilder desc = new StringBuilder();
			desc.append(annualEligibleAmount);
			desc.append(" per annum, \n");
			desc.append(totalEligibleAmount);
			desc.append(" per ");
			desc.append(periodInYear);
			desc.append(" years \n");
			// desc.append(accruedEligibleAmount);
			// desc.append(" for period ");
			// desc.append(sd);
			// desc.append(" to ");
			// desc.append(cd + "\n");
			// desc.append(usedAmount);
			// desc.append(" declared for period ");
			// desc.append(sd);
			// desc.append(" to ");
			// desc.append(ed + "\n");
			// desc.append("Term: \n");
			// desc.append(element.getOccurence());
			// desc.append(" times in ");
			// desc.append(periodInYear);
			// desc.append(" years");
			description = desc.toString();
		} else if (periodInMonth == 1){
			currentEligibleAmount = Math.round(annualEligibleAmount / 12f);
			newFbpDetails.setEligibleAmt(String.valueOf(currentEligibleAmount));
			newFbpDetails.setAccruedEligibleAmt("0");
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			Date startDate, endDate;
			Date fisicalDate, currentDate;
			startDate = endDate = null;
			fisicalDate = currentDate = null;
			currentDate = calendar.getTime();
			calendar.set(Calendar.YEAR, currentYear);
			calendar.set(Calendar.MONTH, Calendar.MARCH);
			calendar.set(Calendar.DATE, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			fisicalDate = calendar.getTime();
			int startYear = currentDate.after(fisicalDate) ? currentYear : currentYear - 1;
			int endYear = startYear + 1;
			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, startYear);
			calendar.set(Calendar.MONTH, Calendar.APRIL);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			startDate = calendar.getTime();
			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, endYear);//
			calendar.set(Calendar.MONTH, Calendar.MARCH);// March
			calendar.set(Calendar.DATE, 31);// 1st
			calendar.set(Calendar.DATE, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			endDate = calendar.getTime();
			usedAmount = getFbpUsedAmount(fbpAbbr, userId, startDate, endDate);
			newFbpDetails.setUnusedAmt(String.valueOf(annualEligibleAmount - usedAmount));
			StringBuilder desc = new StringBuilder();
			desc.append(annualEligibleAmount);
			desc.append(" per annum, \n");
			desc.append(currentEligibleAmount);
			desc.append(" per month");
			description = desc.toString();
		} else{
			fbpEditFlag = false;
			description = "Unable to process FBP Component as decalarion period is: " + 12 / periodInMonth + " times in 1 year.";
			logger.debug(description);
		}
		newFbpDetails.setEditFlag(fbpEditFlag);
		newFbpDetails.setDescription(description);
		return (newFbpDetails);
	}

	protected float getFbpUsedAmount(String fbpAbbr, int userId, Date startDate, Date endDate) throws FbpDetailsDaoException {
		float usedAmount = 0.0f;
		final String WHERE_FBPDETAILS = "FBP=? AND FBP_ID IN (" + "SELECT ID FROM FBP_REQ WHERE USER_ID=? AND STATUS!=? AND CREATED_ON >=? AND CREATED_ON <=?)";
		FbpDetailsDao fbpDetailsProvider = FbpDetailsDaoFactory.create();
		FbpDetails[] previousFbpDetails = fbpDetailsProvider.findByDynamicWhere(WHERE_FBPDETAILS, new Object[] { fbpAbbr, userId, Status.CANCELLED, startDate, endDate });
		for (FbpDetails prevFbp : previousFbpDetails){
			String usedAmtStr = prevFbp.getUsedAmt();
			usedAmtStr = usedAmtStr == null ? "0" : usedAmtStr;
			try{
				Float f = Float.valueOf(usedAmtStr);
				usedAmount += f.floatValue();
			} catch (NumberFormatException ex){
				logger.debug("Unable to convert FBP_DETAILS.USED_AMT: " + usedAmtStr + " to float value.");
			}
		}
		return (usedAmount);
	}

	protected FBPForm getFbpDetails(int paramFbpId, FBPForm fbpForm) throws FbpConfigDaoException, FbpReqDaoException, FbpDetailsDaoException, ProfileInfoDaoException, UsersDaoException, VoluntaryProvidentFundDaoException {
		HashMap<String, FbpDetails> declaredFbpDetails = new HashMap<String, FbpDetails>();
		FbpReqDao fbpReqProvider = FbpReqDaoFactory.create();
		FbpReq fbpReq = fbpReqProvider.findByPrimaryKey(paramFbpId);
		FbpDetailsDao fbpDetailsProvider = FbpDetailsDaoFactory.create();
		FbpDetails[] fbpDetails = fbpDetailsProvider.findWhereFbpIdEquals(paramFbpId);
		FbpConfigDao fbpConfigProvider = FbpConfigDaoFactory.create();
		FbpConfig[] fbpConfig = fbpConfigProvider.findWhereLevelIdEquals(fbpReq.getLevelId());
		FbpConfig currentFbpConfig = fbpConfig.length > 0 ? fbpConfig[0] : null;
		ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
		UsersDao userProvider = UsersDaoFactory.create();
		if (currentFbpConfig == null){ throw new FbpConfigDaoException("Unable to get fbp config for LEVEL_ID: " + fbpReq.getLevelId()); }
		String userLevelName = fbpReq.getLevel();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fbpReq.getCreatedOn());
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		String monthId = String.valueOf(calendar.get(Calendar.YEAR));
		if (currentMonth < 10) monthId += "0" + currentMonth;
		else monthId += currentMonth;
		// Process details to get requester name and financial year string
		fbpReq = getFbpReqProcessed(fbpReq);
		fbpReq.setMessageBody(null);// Setting message body to null for
									// individual receive.
		int userId = fbpReq.getUserId();
		String status = fbpReq.getStatus();
		// Sets EmpId and Employee name
		Users user = userProvider.findByPrimaryKey(userId);
		ProfileInfo profile = profileInfoProvider.findWhereUserIdEquals(userId);
		fbpForm.setEmpId(user.getEmpId());
		fbpForm.setEmpName(profile.getFirstName() + " " + profile.getLastName());
		String fbpReqMonthId = fbpReq.getMonthId();
		if (((fbpReq.getFrequent().equalsIgnoreCase("yearly") && fbpReqMonthId.equals(getFinancialYear())) || (fbpReq.getFrequent().equalsIgnoreCase("monthly") && fbpReqMonthId.equals(monthId))) && (status.equalsIgnoreCase(Status.REQUESTRAISED) || status.equalsIgnoreCase(Status.PENDINGAPPROVAL) || status.equalsIgnoreCase(Status.APPROVED))){
			fbpReq.setCancelFlag(true);
		} else{
			logger.debug("No cancel flag for flexi benefit plan reuqest FBP_REQ.ID: " + fbpReq.getId());
		}
	// getting vpf for user 
		
		VoluntaryProvidentFundDao vpfdao = VoluntaryProvidentFundDaoFactory.create();
		VoluntaryProvidentFund[] vpf = vpfdao.findByVpf("SELECT * FROM VPF_REQ where USER_ID = ? ORDER BY ID DESC LIMIT 1", new Object[]{(userId)} );
		for (VoluntaryProvidentFund voluntaryProvidentFund : vpf) {
			fbpForm.setVpf(voluntaryProvidentFund.getVpf());
			
		}

		

		// Create a search map of declared FbpDetails
		for (FbpDetails element : fbpDetails){
			declaredFbpDetails.put(element.getFbp().toUpperCase(), element);
		}
		List<FbpComponent> fbpComponents = new FbpComponent().getFbpComponents();
		// Generate a list of all FbpDetails including all FbpComponents
		List<FbpDetails> benefits = new ArrayList<FbpDetails>();
		for (FbpComponent element : fbpComponents){
			String fbpAbbr = element.getFbp().toUpperCase();
			FbpDetails newDetail = declaredFbpDetails.get(fbpAbbr);
			if (newDetail == null){
				newDetail = new FbpDetails();
				newDetail.setFbp(fbpAbbr);
				newDetail.setFbpId(0);
				newDetail.setFbpIdNull(false);
				newDetail.setFbpLabel(element.getFbpLabel());
				newDetail.setFbpOrder(element.getOrder());
			}
			// if(element.getFbp().equalsIgnoreCase("OA")) {
			// continue;
			// }
			benefits.add(newDetail);
		}
		for (FbpDetails element : benefits){
			String fbpAbbr = element.getFbp().toUpperCase();
			String annualEligibleAmtStr = getFbpEligibleAmount(fbpAbbr, currentFbpConfig);
			String description = null;
			float annualEligibleAmount = 0.0f;
			float currentEligibleAmount = 0.0f;
			float usedAmount = 0.0f;
			if (annualEligibleAmtStr.equals("NA") || annualEligibleAmtStr.equals("UA")){
				String eligibleAmtStr = annualEligibleAmtStr;
				if (annualEligibleAmtStr.equals("UA")){
					eligibleAmtStr = "NA";
					description = "Unused benefits. Calculated based on you declaration.";
				} else{
					element.setUsedAmt(annualEligibleAmtStr);
					description = "Not applicable for your level- " + userLevelName;
				}
				element.setAnnualEligibleAmt(annualEligibleAmtStr);
				element.setEligibleAmt(eligibleAmtStr);
				element.setUnusedAmt(eligibleAmtStr);
				element.setAccruedEligibleAmt(eligibleAmtStr);
				element.setDescription(description);
				element.setTotalEligibleAmt(annualEligibleAmtStr);
				FbpComponent fbpComponent = new FbpComponent();
				fbpComponent = fbpComponent.getFbpComponentByName(fbpAbbr);
				element.setFbpLabel(fbpComponent.getFbpLabel());
				element.setFbpOrder(fbpComponent.getOrder());
				continue;
			} else{
				annualEligibleAmount = Float.valueOf(annualEligibleAmtStr);
			}
			// Annual amount from FBP_CONFIG
			element.setAnnualEligibleAmt(annualEligibleAmtStr);
			// If user has done yearly FBP Declaraion then sum of all months
			// amount in returned details
			// Amount eligible for whole period either annual or more than 1
			// year
			// For n year it is n * annualEligibleAmt
			element.setTotalEligibleAmt(annualEligibleAmtStr);
			element.setAccruedEligibleAmt(String.valueOf(0.0f));
			FbpComponent fbpComponent = new FbpComponent();
			fbpComponent = fbpComponent.getFbpComponentByName(fbpAbbr);
			element.setFbpLabel(fbpComponent.getFbpLabel());
			element.setFbpOrder(fbpComponent.getOrder());
			int fbpPeriod = fbpComponent.getPeriod();
			int periodInYear = fbpPeriod / 12;
			int periodInMonth = fbpPeriod % 12;
			if (declaredFbpDetails.get(fbpAbbr) == null){
				if (periodInYear > 0){
					StringBuilder desc = new StringBuilder();
					desc.append("You have not declared.");
					desc.append("Term: \n");
					desc.append(fbpComponent.getOccurence());
					desc.append(" times in ");
					desc.append(periodInYear);
					desc.append(" years");
					description = desc.toString();
				} else if (periodInMonth == 1){
					currentEligibleAmount = Math.round(annualEligibleAmount / 12f);
					StringBuilder desc = new StringBuilder();
					desc.append("You have not declared.");
					desc.append(annualEligibleAmount);
					desc.append(" per annum, \n");
					desc.append(currentEligibleAmount);
					desc.append(" per month");
					description = desc.toString();
				}
			} else if (periodInYear > 0){
				calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				// int currentMonth=calendar.get(Calendar.MONTH);
				// int accruedPeriod=(currentYear -
				// BENCHMARK_YEAR)%periodInYear+1;
				int startYear = currentYear - (currentYear - BENCHMARK_YEAR) % periodInYear;
				int endYear = startYear + periodInYear - 1;
				int totalPeriod = endYear - startYear + 1;
				float totalEligibleAmount = annualEligibleAmount * totalPeriod;
				float accruedEligibleAmount = Float.valueOf(element.getEligibleAmt());
				usedAmount = Float.valueOf(element.getUsedAmt().equalsIgnoreCase("NA") ? "0" : element.getUsedAmt());
				// Amount accrued benefit
				element.setAccruedEligibleAmt(String.valueOf(accruedEligibleAmount));
				// Total benefit for the period annualEligibleAmount * n years
				element.setTotalEligibleAmt(String.valueOf(totalEligibleAmount));
				Date startDate, endDate, currentDate;
				startDate = endDate = currentDate = null;
				currentDate = calendar.getTime();
				calendar.set(Calendar.YEAR, startYear);//
				calendar.set(Calendar.MONTH, Calendar.JANUARY);// April
				calendar.set(Calendar.DATE, 1);// 1st
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				startDate = calendar.getTime();
				calendar.set(Calendar.YEAR, endYear);//
				calendar.set(Calendar.MONTH, Calendar.DECEMBER);// April
				calendar.set(Calendar.DATE, 31);// 1st
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				endDate = calendar.getTime();
				String sd, cd, ed;
				sd = PortalUtility.getdd_MM_yyyy(startDate);
				cd = PortalUtility.getdd_MM_yyyy(currentDate);
				ed = PortalUtility.getdd_MM_yyyy(endDate);
				StringBuilder desc = new StringBuilder();
				desc.append(annualEligibleAmount);
				desc.append(" per annum, \n");
				desc.append(totalEligibleAmount);
				desc.append(" per ");
				desc.append(periodInYear);
				desc.append(" years, \n");
				desc.append(accruedEligibleAmount);
				desc.append(" for period ");
				desc.append(sd);
				desc.append(" to ");
				desc.append(cd + "\n");
				desc.append(usedAmount);
				desc.append(" declared for period ");
				desc.append(sd);
				desc.append(" to ");
				desc.append(ed + "\n");
				desc.append("Term: \n");
				desc.append(fbpComponent.getOccurence());
				desc.append(" times in ");
				desc.append(periodInYear);
				desc.append(" years");
				description = desc.toString();
			} else if (periodInMonth == 1){
				currentEligibleAmount = Math.round(annualEligibleAmount / 12f);
				StringBuilder desc = new StringBuilder();
				desc.append(annualEligibleAmount);
				desc.append(" per annum, \n");
				desc.append(currentEligibleAmount);
				desc.append(" per month");
				description = desc.toString();
			} else{
				logger.debug("Unable to process FBP Component as decalarion period is: " + 12 / periodInMonth + " times in 1 year.");
				continue;
			}
			element.setDescription(description);
		}
		fbpForm.setFbpAmt(getSalaryFbp(userId));
		// Use Collections algorith to sort ArrayList
		sortBenefit(benefits);
		fbpForm.setBenefits(benefits);
		FbpDetails temp = null;
		for (FbpDetails eachDet : benefits)
			if (eachDet.getFbp().equalsIgnoreCase("OA")){
				temp = eachDet;
				break;
			}
		benefits.remove(temp);
		fbpForm.setOaAmt(temp == null ? "0.00" : temp.getUsedAmt());
		fbpForm.setUserID(fbpReq.getUserId());
		fbpForm.setLevelId(fbpReq.getLevelId());
		fbpForm.setLevel(fbpReq.getLevel());
		fbpForm.setRequests(new FbpReq[] { fbpReq });
		
		
		return fbpForm;
	}

	protected FBPForm getFbpToApprove(int userId) throws FbpReqDaoException {
		StringBuilder selectFbpReq = new StringBuilder("SELECT FBP_REQ.* FROM FBP_REQ INNER JOIN INBOX ON FBP_REQ.ESR_MAP_ID=INBOX.ESR_MAP_ID ");
		selectFbpReq.append("WHERE ");
		selectFbpReq.append("FBP_REQ.STATUS IN ('");
		selectFbpReq.append(Status.REQUESTRAISED);
		selectFbpReq.append("','");
		selectFbpReq.append(Status.PENDINGAPPROVAL);
		selectFbpReq.append("') ");
		selectFbpReq.append("AND ");
		selectFbpReq.append("INBOX.RECEIVER_ID=INBOX.ASSIGNED_TO AND ");
		selectFbpReq.append("INBOX.IS_DELETED!=1 AND ");
		selectFbpReq.append("INBOX.ASSIGNED_TO=");
		selectFbpReq.append(userId);
		FBPForm fbpForm = getFbpTodo(userId, selectFbpReq.toString());
		return (fbpForm);
	}

	protected FBPForm getFbpToHandle(int userId) throws FbpReqDaoException {
		StringBuilder selectFbpReq = new StringBuilder("SELECT FBP_REQ.* FROM FBP_REQ INNER JOIN INBOX ON FBP_REQ.ESR_MAP_ID=INBOX.ESR_MAP_ID ");
		selectFbpReq.append("WHERE ");
		selectFbpReq.append("FBP_REQ.STATUS IN ('");
		selectFbpReq.append(Status.APPROVED);
		selectFbpReq.append("','");
		selectFbpReq.append(Status.ASSIGNED);
		selectFbpReq.append("','");
		selectFbpReq.append(Status.INPROGRESS);
		selectFbpReq.append("','");
		selectFbpReq.append(Status.PROCESSED);
		selectFbpReq.append("') ");
		selectFbpReq.append("AND ");
		selectFbpReq.append("INBOX.RECEIVER_ID=INBOX.ASSIGNED_TO AND ");
		selectFbpReq.append("INBOX.IS_DELETED!=1 AND ");
		selectFbpReq.append("INBOX.ASSIGNED_TO=");
		selectFbpReq.append(userId);
		FBPForm fbpForm = getFbpTodo(userId, selectFbpReq.toString());
		return (fbpForm);
	}

	protected FBPForm getFbpTodo(int userId, final String SELECT_FBP_REQ) throws FbpReqDaoException {
		final String SELECT_REQUEST = "SELECT ESR.REQ_ID,RT.NAME FROM EMP_SER_REQ_MAP AS ESR INNER JOIN REQUEST_TYPE AS RT ON ESR.REQ_TYPE_ID=RT.ID AND ESR.ID=?";
		FBPForm fbpForm = new FBPForm();
		FbpReqDao fbpReqProvider = FbpReqDaoFactory.create();
		FbpReq[] fbpReqToDo = fbpReqProvider.findByDynamicSelect(SELECT_FBP_REQ, null);
		for (FbpReq element : fbpReqToDo){
			// Process details to get requester name and financial year string
			element = getFbpReqProcessed(element);
			element.setMessageBody(null);// Removing message body for
											// receiveTODO
			String status = element.getStatus();
			Connection conn = null;
			try{
				conn = ResourceManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_REQUEST);
				stmt.setInt(1, element.getEsrMapId());
				ResultSet result = stmt.executeQuery();
				if (result.next()){
					element.setReqId(result.getString(1));
					element.setRequestName(result.getString(2));
				} else logger.debug("No request type exists for ESR_MAP_ID: " + element.getId());
				ResourceManager.close(conn);
			} catch (SQLException ex){
				logger.error("Unable to get REQ_ID AND REQUEST_TYPE for ESR_MAP_ID: " + element.getId());
				logger.error(ex.getMessage());
			} finally{
				if (conn != null){
					ResourceManager.close(conn);
				}
			}
			if (status.equalsIgnoreCase(Status.REQUESTRAISED) || status.equalsIgnoreCase(Status.PENDINGAPPROVAL)){
				element.setApproveFlag(true);
				element.setRejectFlag(true);
			} else if (status.equalsIgnoreCase(Status.APPROVED)){
				element.setAssignFlag(true);
			} else if (status.equalsIgnoreCase(Status.ASSIGNED) || status.equalsIgnoreCase(Status.INPROGRESS)){
				try{
					InboxDao inboxDaoProvider = InboxDaoFactory.create();
					Inbox[] inbox = inboxDaoProvider.findByDynamicWhere("RECEIVER_ID=ASSIGNED_TO AND ESR_MAP_ID=? AND ASSIGNED_TO=?", new Object[] { element.getEsrMapId(), userId });
					if (inbox.length > 0) element.setAssignFlag(true);
					else element.setAssignFlag(false);
				} catch (InboxDaoException ex){
					logger.error("Unable to set status of assigned button while retrieveing FBP detail todo (approve/handle) for FBP_ID: " + element.getId(), ex);
				}
			} else{
				logger.debug("No operation for flexi benefit plan reuqest FBP_REQ.ID: " + element.getId());
			}
		}
		fbpForm.setUserID(userId);
		// Setting all requests to do for logged-in user
		fbpForm.setRequests(fbpReqToDo);
		fbpForm.setTodoCount(fbpReqToDo.length);
		return (fbpForm);
	}

	protected FBPForm getFbpToHandle(int userId, int paramFbpId, FBPForm fbpForm) throws FbpConfigDaoException, FbpReqDaoException, FbpDetailsDaoException, StatusDaoException, ProfileInfoDaoException, UsersDaoException, VoluntaryProvidentFundDaoException {
		fbpForm = getFbpDetails(paramFbpId, fbpForm);
		List<FbpReq> fbpRequest = fbpForm.getRequests();
		int esrMapId = fbpRequest != null ? (fbpRequest.size() > 0 ? fbpRequest.get(0).getEsrMapId() : 0) : 0;
		int requesterId = fbpRequest != null ? (fbpRequest.size() > 0 ? fbpRequest.get(0).getUserId() : 0) : 0;
		ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
		ProcessChain reqProcessChain = getRequestorProcessChain(requesterId);
		ProcessEvaluator evaluator = new ProcessEvaluator();
		Integer[] handlersUserIds = evaluator.handlers(reqProcessChain.getHandler(), requesterId);
		List<Handlers> handlersArray = new ArrayList<Handlers>();
		// with these userIds extract name and their id from PROFILE_INFO
		for (Integer handlerId : handlersUserIds){
			ProfileInfo handlerProfileInfo = null;
			try{
				handlerProfileInfo = profileInfoProvider.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI WHERE PI.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { handlerId })[0];
				Handlers handler = new Handlers();
				handler.setName(handlerProfileInfo.getFirstName());
				handler.setId(handlerId);
				handlersArray.add(handler);
			} catch (ProfileInfoDaoException ex){
				logger.error("Unable to get handler details for HANDLER ID: " + handlerId, ex);
			}
		}
		fbpForm.setHandlers(handlersArray);
		StatusDao statusProvider = StatusDaoFactory.create();
		List<com.dikshatech.portal.dto.Status> statusArray = new ArrayList<com.dikshatech.portal.dto.Status>();
		// to know who is the assigned_handler for a particular request
		try{
			InboxDao inboxDaoProvider = InboxDaoFactory.create();
			Inbox[] inbox = inboxDaoProvider.findByDynamicWhere("RECEIVER_ID=ASSIGNED_TO AND ESR_MAP_ID=? AND ASSIGNED_TO=?", new Object[] { esrMapId, userId });
			if (inbox.length == 1){
				com.dikshatech.portal.dto.Status tempStatus = new com.dikshatech.portal.dto.Status();
				tempStatus.setStatus("Select a Status");
				statusArray.add(tempStatus);
				statusArray.add(statusProvider.findByPrimaryKey(Status.getStatusId(Status.ASSIGNED)));
				// it was assigned to someone before
				statusArray.add(statusProvider.findByPrimaryKey(Status.getStatusId(Status.INPROGRESS)));
				statusArray.add(statusProvider.findByPrimaryKey(Status.getStatusId(Status.PROCESSED)));
			} else if (inbox.length > 1){
				com.dikshatech.portal.dto.Status tempStatus = new com.dikshatech.portal.dto.Status();
				tempStatus.setStatus("Select a Status");
				statusArray.add(tempStatus);
				statusArray.add(statusProvider.findByPrimaryKey(Status.getStatusId(Status.ASSIGNED)));
			} else{
				logger.debug("Unable to obtain status for assigned FBP request ESR_MAP_ID: " + esrMapId);
			}
			fbpForm.setStatus(statusArray);
		} catch (InboxDaoException ex){
			logger.error("Unable to set status of assigned button while retrieveing FBP detail todo (approve/handle) for FBP_ID: " + paramFbpId, ex);
		}
		return (fbpForm);
	}

	protected FbpReq getFbpReqProcessed(FbpReq fbpRequest) {
		FbpReq fbpReqProcessed = fbpRequest;
		// Calculating Financial year
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fbpRequest.getCreatedOn());
		int currentYear = calendar.get(Calendar.YEAR);
		Date fisicalDate, currentDate;
		fisicalDate = currentDate = null;
		currentDate = calendar.getTime();
		calendar.set(Calendar.YEAR, currentYear);
		calendar.set(Calendar.MONTH, Calendar.MARCH);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		fisicalDate = calendar.getTime();
		int startYear = currentDate.after(fisicalDate) ? currentYear : currentYear - 1;
		int endYear = startYear + 1;
		// Setting financial year for display
		fbpReqProcessed.setDeclarationYear(startYear + "-" + endYear);
		// Setting monthId: YYYYMM to monthName: MMM- YYYY
		// monthId: YYYY-YYYY (startyear-endyear) to monthName: Mar- YYYY (Mar-
		// startyear)
		String monthId = fbpReqProcessed.getMonthId();
		String month = Converter.getMonthNameFromMonthId(monthId);
		fbpReqProcessed.setMonthName(month);
		// Setting Requester name and designation
		ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
		try{
			ProfileInfo requesterProfile = profileInfoProvider.findWhereUserIdEquals(fbpRequest.getUserId());
			if (requesterProfile != null){
				fbpReqProcessed.setFname(requesterProfile.getFirstName());
				fbpReqProcessed.setLname(requesterProfile.getLastName());
			}
		} catch (ProfileInfoDaoException ex){
			logger.debug("Unable to process FBP request while setting requester name for USER_ID: " + fbpRequest.getUserId(), ex);
		}
		return (fbpReqProcessed);
	}

	protected float getSalaryFbp(int userId) {
		float fbpAmt = 0.0f;
		SalaryDetailModel sdm = new SalaryDetailModel();
		Salary salary = sdm.getSalary(userId, true, SalaryDetailModel.TYPE_NORMAL);
		fbpAmt = salary.getFBP();
		return (fbpAmt);
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		FBPForm fbpForm = (FBPForm) form;
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProcessEvaluator evaluator = new ProcessEvaluator();
		//Users user=null;
		try{
			int fbpId = fbpForm.getFbpId();
			FbpReq fbpReq = fbpReqDao.findByPrimaryKey(fbpId);
			int userId = fbpReq.getUserId();
			//user = profileInfoDao.findWhereUserIdEquals(userId);
			int currSequence = fbpReq.getSequence();
			EmpSerReqMap esrMap = empSerReqMapDao.findByPrimaryKey(fbpReq.getEsrMapId());
			int esrMapId = esrMap.getId();
			ProcessChain[] processChain = processChainDao.findByDynamicWhere(" ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLES WHERE USER_ID= ?) AND MODULE_ID=?)", new Object[] { userId, FBP_MODULE_ID });
			MailGenerator mGenerator = new MailGenerator();
			PortalMail mail = new PortalMail();
			ProfileInfo profileInfo = null;
			String subject = null;
			Inbox[] inboxEntries = null;
			Login login = Login.getLogin(request);
			String comments = null;
			int paramAssigneeId = fbpForm.getAssigneeId();
			Users user = usersDao.findByPrimaryKey(login.getUserId());
			Users user1 = usersDao.findByPrimaryKey(userId);
			int empId = user != null ? user.getEmpId() : 0;
			switch (ActionMethods.UpdateTypes.getValue(form.getuType())) {
			// uType when Handlers try to assign themselves for the handling
			// purpose
				case APPROVE:
					if (fbpReq.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || fbpReq.getStatus().equalsIgnoreCase(Status.PENDINGAPPROVAL)){
						if (processChain != null && processChain.length == 1){
							// To check whether the user Approving is a valid user
							// and there in siblings list
							if (Arrays.asList(esrMap.getSiblings().split(",")).contains(String.valueOf(Login.getLogin(request).getUserId()))){
								Integer[] approvers = evaluator.approvers(processChain[0].getApprovalChain(), currSequence + 1, userId);
								Integer[] handlers = evaluator.handlers(processChain[0].getHandler(), userId);
								if (approvers != null && approvers.length > 0){
									// update the status and the sequence in the
									// request table
									fbpReq.setSequence(currSequence + 1);
									fbpReq.setStatus(Status.PENDINGAPPROVAL);
									// update the comments field in the Req table
									String existingComments = fbpReq.getComments();
									existingComments = existingComments == null ? "" : existingComments;
									profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
									if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + empId + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
									fbpReq.setComments(comments + existingComments);
									fbpReqDao.update(new FbpReqPk(fbpId), fbpReq);
									subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " " + fbpReq.getStatus();
									// update Siblings in ESR MAP;
									esrMap.setSiblings(Arrays.toString(approvers).replace("[", "").replace("]", "").replace(" ", ""));
									empSerReqMapDao.update(new EmpSerReqMapPk(esrMap.getId()), esrMap);
									// Update the Inbox for Approvers
									profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
									if (profileInfo != null) mail.setEmpFname(profileInfo.getFirstName() + " " + profileInfo.getLastName());
									else logger.error("There is no such user record found in the ProfileInfo Table with Id" + userId);
									mail.setEmployeeId(user.getEmpId());
									mail.setMessageBody(getHtmlOfFbpDetails(fbpId));
									mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
									mail.setTemplateName(MailSettings.FBP_APPROVAL);
									mail.setComments(getHtmlComments(fbpReq.getComments()));
									mail.setMailSubject(subject);
									// Delete the records of the existing approvers
									Inbox[] inboxToBeDeleted = inboxDao.findByDynamicWhere(" ESR_MAP_ID = ? AND ASSIGNED_TO = RECEIVER_ID ", new Object[] { esrMap.getId() });
									for (Inbox inbox : inboxToBeDeleted)
										inboxDao.delete(new InboxPk(inbox.getId()));
									// Update the records the new subject for all
									// the records existing
									Inbox[] inboxToBeUpdated = inboxDao.findByDynamicWhere(" ESR_MAP_ID = ? AND ASSIGNED_TO != RECEIVER_ID ", new Object[] { esrMap.getId() });
									for (Inbox inbox : inboxToBeUpdated){
										inbox.setIsRead(0);
										inbox.setSubject(subject);
										inbox.setStatus(Status.PENDINGAPPROVAL);
										String message = inbox.getMessageBody();
										message = getUpdatedMessageBody(message, fbpReq.getComments());
										inbox.setMessageBody(message);
										inboxDao.update(new InboxPk(inbox.getId()), inbox);
									}
									// Create records for all the approver users
									Inbox inbox = new Inbox();
									inbox.setEsrMapId(esrMapId);
									inbox.setSubject(subject);
									inbox.setRaisedBy(userId);
									inbox.setCreationDatetime(new Date());
									inbox.setStatus(Status.PENDINGAPPROVAL);
									inbox.setCategory("FBP");
									inbox.setIsRead(0);
									inbox.setIsDeleted(0);
									inbox.setIsEscalated(0);
									for (int eachApprover : approvers){
										profileInfo = profileInfoDao.findWhereUserIdEquals(eachApprover);
										if (profileInfo != null){
											mail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
											String messageBody = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_APPROVAL), mail);
											inbox.setMessageBody(messageBody);
											inbox.setAssignedTo(eachApprover);
											inbox.setReceiverId(eachApprover);
											inboxDao.insert(inbox);
											mail.setMailBody(messageBody);
											sendMail(mail, profileInfo.getOfficalEmailId(), null, null, null);
										}
									}
								} else if (handlers != null && handlers.length > 0){
									// TODO Code for creating the records for the
									// handlers update the status and the sequence
									// in the request table
									fbpReq.setSequence(currSequence + 10);
									fbpReq.setStatus(Status.APPROVED);
									// update the comments field in the Req table
									String existingComments = fbpReq.getComments();
									existingComments = existingComments == null ? "" : existingComments;
									profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
									if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + empId + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
									fbpReq.setComments(comments + existingComments);
									fbpReqDao.update(new FbpReqPk(fbpId), fbpReq);
									subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " " + fbpReq.getStatus();
									// update Siblings in ESR MAP;
									esrMap.setSiblings(Arrays.toString(handlers).replace("[", "").replace("]", "").replace(" ", ""));
									empSerReqMapDao.update(new EmpSerReqMapPk(esrMap.getId()), esrMap);
									// Deleting the old approver records
									Inbox[] inboxToBeDeleted = inboxDao.findByDynamicWhere(" ESR_MAP_ID = ? AND ASSIGNED_TO = RECEIVER_ID ", new Object[] { esrMap.getId() });
									for (Inbox inbox : inboxToBeDeleted)
										inboxDao.delete(new InboxPk(inbox.getId()));
									// Update the status of the notifier records
									Inbox[] inboxToBeUpdated = inboxDao.findByDynamicWhere(" ESR_MAP_ID = ? AND ASSIGNED_TO != RECEIVER_ID ", new Object[] { esrMap.getId() });
									for (Inbox inbox : inboxToBeUpdated){
										inbox.setIsRead(0);
										inbox.setStatus(Status.APPROVED);
										inbox.setSubject(subject);
										inboxDao.update(new InboxPk(inbox.getId()), inbox);
									}
									// Update the Inbox records for Handlers
									Inbox inbox = new Inbox();
									inbox.setEsrMapId(esrMapId);
									inbox.setSubject(subject);
									inbox.setRaisedBy(userId);
									inbox.setCreationDatetime(new Date());
									inbox.setStatus(Status.APPROVED);
									inbox.setCategory("FBP");
									inbox.setIsRead(0);
									inbox.setIsDeleted(0);
									inbox.setIsEscalated(0);
									mail.setTemplateName(MailSettings.FBP_HANDLER);
									profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
									mail.setEmpFname(profileInfo.getFirstName() + " " + profileInfo.getLastName());
									mail.setEmployeeId(user.getEmpId());
									mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
									mail.setMessageBody(getHtmlOfFbpDetails(fbpId));
									mail.setComments(getHtmlComments(fbpReq.getComments()));
									for (int eachHandler : handlers){
										profileInfo = profileInfoDao.findWhereUserIdEquals(eachHandler);
										mail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
										mail.setEmployeeId(user.getEmpId());
										inbox.setReceiverId(eachHandler);
										inbox.setAssignedTo(eachHandler);
										String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_HANDLER), mail);
										inbox.setMessageBody(msg);
										inboxDao.insert(inbox);
										mail.setMailBody(msg);
										mail.setMailSubject(subject);
										mail.setTemplateName(MailSettings.FBP_HANDLER);
										sendMail(mail, profileInfo.getOfficalEmailId(), null, null, null);
									}
								}
							} else{
								logger.error("The User " + userId + "is not in the Siblings list and still trying to approve ");
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.IncorrectUserApproval", new Object[] { userId }));
								return result;
							}
						} else{
							logger.error("The FBP record" + fbpId + " is in the approval process but the current process dont have a process chain associated for user " + userId);
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.NoProcessChainMapped", new Object[] { userId }));
							return result;
						}
					} else if (fbpReq.getStatus().equalsIgnoreCase(Status.APPROVED)){
						logger.error("The record is already approved from all levels of approval and the Approved is called again");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.AlreadyApproved"));
						return result;
					}
					break;
				// uType when Handlers try to assign themselves for the handling
				// purpose
				case ASSIGN:
					
					if (Arrays.asList(esrMap.getSiblings().split(",")).contains(String.valueOf(Login.getLogin(request).getUserId()))){
						if (fbpReq.getStatus().equalsIgnoreCase(Status.APPROVED) || fbpReq.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
							// update the status and the sequence in the request
							// table
							boolean isPreviousStatusAssigned = fbpReq.getStatus().equalsIgnoreCase(Status.ASSIGNED);
							fbpReq.setSequence(currSequence + 1);
							fbpReq.setStatus(Status.ASSIGNED);
							// update the comments field in the Req table
							String existingComments = fbpReq.getComments();
							existingComments = existingComments == null ? "" : existingComments;
							profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
							if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + empId + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
							fbpReq.setComments(comments + existingComments);
							fbpReqDao.update(new FbpReqPk(fbpId), fbpReq);
							// int assigneeId = Login.getLogin(request).getUserId();
							int assigneeId = paramAssigneeId;
							ProfileInfo profileInfoNew = profileInfoDao.findWhereUserIdEquals(assigneeId);
							profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
							// Update the inbox records with the new assigned to and
							// Subject and the message Body
							if (isPreviousStatusAssigned){
								int prevAssigneeId = 0;
								inboxEntries = inboxDao.findByDynamicWhere(" ESR_MAP_ID = ? AND ASSIGNED_TO = RECEIVER_ID ", new Object[] { esrMapId });
								if (inboxEntries.length == 1) prevAssigneeId = inboxEntries[0].getAssignedTo();
								else{
									logger.debug("Previous Assignee not found for ESR_MAP_ID: " + esrMapId);
									prevAssigneeId = 0;
								}
								inboxEntries = inboxDao.findByDynamicWhere(" ESR_MAP_ID = ? AND ASSIGNED_TO = ? ", new Object[] { esrMapId, prevAssigneeId });
							} else{
								inboxEntries = inboxDao.findByDynamicWhere(" ESR_MAP_ID = ? AND ASSIGNED_TO = RECEIVER_ID ", new Object[] { esrMapId });
							}
							String assigneeName = null, userName = null;
							if (profileInfoNew != null) assigneeName = profileInfoNew.getFirstName() + " " + profileInfoNew.getLastName();
							if (profileInfo != null) userName = profileInfo.getFirstName() + profileInfo.getLastName();
							if (paramAssigneeId == login.getUserId()){
								subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " is Docked By " + assigneeName;
							} else{
								subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " is Docked To " + assigneeName;
							}
							mail.setApproverName(assigneeName);
							mail.setEmpFname(userName);
							mail.setEmployeeId(user1.getEmpId());
							mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
							mail.setTemplateName(MailSettings.FBP_ASSIGNED);
							mail.setMessageBody(getHtmlOfFbpDetails(fbpId));
							mail.setRequester("\\[Requester\\]");
							mail.setComments(getHtmlComments(fbpReq.getComments()));
							String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_ASSIGNED), mail);
							if (inboxEntries != null && inboxEntries.length > 0){
								for (Inbox eachInbox : inboxEntries){
									eachInbox.setIsRead(0);
									eachInbox.setStatus(Status.ASSIGNED);
									eachInbox.setAssignedTo(assigneeId);
									eachInbox.setSubject(subject);
									profileInfoNew = profileInfoDao.findWhereUserIdEquals(eachInbox.getReceiverId());
									String repMsg = new String(msg);
									repMsg = repMsg.replaceAll("\\[Requester\\]", profileInfoNew.getFirstName() + " " + profileInfoNew.getLastName());
									eachInbox.setMessageBody(repMsg);
									inboxDao.update(new InboxPk(eachInbox.getId()), eachInbox);
								}
							}
							Inbox[] inboxToBeUpdated = inboxDao.findByDynamicWhere("ESR_MAP_ID = ? AND ASSIGNED_TO != RECEIVER_ID", new Object[] { esrMapId });
							for (Inbox inbox : inboxToBeUpdated){
								inbox.setIsRead(0);
								inbox.setStatus(Status.ASSIGNED);
								inbox.setSubject(subject);
								String message = inbox.getMessageBody();
								message = getUpdatedMessageBody(message, fbpReq.getComments());
								inbox.setMessageBody(message);
								inboxDao.update(new InboxPk(inbox.getId()), inbox);
							}
						} else{
							logger.debug("The record " + esrMap.getReqId() + " is not in the Approved Status ");
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.InvalidStatusForAssign", new Object[] { esrMap.getReqId() }));
						}
					} else{
						logger.error("The user " + userId + "is not a valid Assignee for the request and hence cannot be assigned");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.InvalidAssignee", new Object[] { userId }));
					}
					break;
				case INPROGRESS:
					if (Arrays.asList(esrMap.getSiblings().split(",")).contains(String.valueOf(Login.getLogin(request).getUserId()))){
						if (fbpReq.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
							// update the status and the sequence in the request
							// table
							fbpReq.setSequence(currSequence + 1);
							fbpReq.setStatus(Status.INPROGRESS);
							// update the comments field in the Req table
							String existingComments = fbpReq.getComments();
							existingComments = existingComments == null ? "" : existingComments;
							profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
							if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + empId + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
							fbpReq.setComments(comments + existingComments);
							fbpReqDao.update(new FbpReqPk(fbpId), fbpReq);
							subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " is in Progress ";
							// Code added to update inbox message body after
							// assignment
							String message = null;
							inboxEntries = inboxDao.findWhereEsrMapIdEquals(esrMapId);
							if (inboxEntries.length > 0){
								message = inboxEntries[0].getMessageBody();
								message = getUpdatedMessageBody(message, fbpReq.getComments());
							} else{
								logger.debug("No inbox entries to update for ESR_MAP_ID: " + esrMapId);
							}
							for (Inbox eachInbox : inboxEntries){
								eachInbox.setSubject(subject);
								eachInbox.setIsRead(0);
								eachInbox.setStatus(Status.INPROGRESS);
								eachInbox.setMessageBody(message);
								inboxDao.update(new InboxPk(eachInbox.getId()), eachInbox);
							}
						} else{
							logger.debug("The record " + esrMap.getReqId() + " is not in the Assigned Status ");
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.InvalidStatusForInProgress", new Object[] { esrMap.getReqId() }));
						}
					} else{
						logger.error("The user " + userId + "is not a valid Assignee for the request and hence cannot be assigned");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.InvalidAssignee", new Object[] { userId }));
					}
					break;
				case PROCESSSED:
					if (Arrays.asList(esrMap.getSiblings().split(",")).contains(String.valueOf(Login.getLogin(request).getUserId()))){
						if (fbpReq.getStatus().equalsIgnoreCase(Status.INPROGRESS) || fbpReq.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
							// update the status and the sequence in the request
							// table
							fbpReq.setSequence(currSequence + 1);
							fbpReq.setStatus(Status.PROCESSED);
							// update the comments field in the Req table
							String existingComments = fbpReq.getComments();
							existingComments = existingComments == null ? "" : existingComments;
							profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
							if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + empId + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
							fbpReq.setComments(comments + existingComments);
							fbpReqDao.update(new FbpReqPk(fbpId), fbpReq);
							subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " is Processed By " + profileInfo.getFirstName() + " " + profileInfo.getLastName();
							mail = new PortalMail();
							mail.setMailSubject(subject);
							mail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
							mail.setEmployeeId(user1.getEmpId());
							mail.setRequester("\\[Requester\\]");
							mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
							mail.setMessageBody(getHtmlOfFbpDetails(fbpId));
							mail.setComments(getHtmlComments(fbpReq.getComments()));
							mail.setTemplateName(MailSettings.FBP_PROCESSED);
							profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
							if (profileInfo != null) mail.setEmpFname(profileInfo.getFirstName() + " " + profileInfo.getLastName());
							mail.setEmployeeId(user1.getEmpId());
							String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_PROCESSED), mail);
							inboxEntries = inboxDao.findWhereEsrMapIdEquals(esrMapId);
							for (Inbox eachInbox : inboxEntries){
								eachInbox.setSubject(subject);
								eachInbox.setIsRead(0);
								eachInbox.setStatus(Status.PROCESSED);
								eachInbox.setAssignedTo(0);
								String repMsg = new String(msg);
								profileInfo = profileInfoDao.findWhereUserIdEquals(eachInbox.getReceiverId());
								repMsg = repMsg.replaceAll("\\[Requester\\]", profileInfo.getFirstName() + " " + profileInfo.getLastName());
								eachInbox.setMessageBody(repMsg);
								mail.setMailBody(repMsg);
								mail.setEmployeeId(user.getEmpId());
								inboxDao.update(new InboxPk(eachInbox.getId()), eachInbox);
								sendMail(mail, profileInfo.getOfficalEmailId(), null, null, null);
							}
							// Recalculate the TDS and Save for the same
							new TDSUtility().reCalculate(userId);
						} else{
							logger.debug("The record " + esrMap.getReqId() + " is not in the InProgress Status ");
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.InvalidStatusForProcessed", new Object[] { esrMap.getReqId() }));
						}
					} else{
						logger.error("The user " + userId + "is not a valid Assignee for the request and hence cannot be assigned");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.InvalidAssignee", new Object[] { userId }));
					}
					break;
				case CANCEL:
					profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
					String loggedUser = null;
					if (profileInfo != null) loggedUser = profileInfo.getFirstName() + " " + profileInfo.getLastName();
					String status = fbpReq.getStatus();
					if (login.getUserId() == fbpReq.getUserId()){
						if (status.equalsIgnoreCase(Status.REQUESTRAISED) || status.equalsIgnoreCase(Status.PENDINGAPPROVAL) || status.equalsIgnoreCase(Status.APPROVED)){
							// update the comments field in the Req table
							String existingComments = fbpReq.getComments();
							existingComments = existingComments == null ? "" : existingComments;
							profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
							if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + empId + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
							fbpReq.setComments(comments + existingComments);
							mail.setReqId(esrMap.getReqId());
							profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
							if (profileInfo != null) mail.setEmpFname(profileInfo.getFirstName() + " " + profileInfo.getLastName());
							mail.setEmployeeId(user1.getEmpId());
							mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
							mail.setTemplateName(MailSettings.FBP_CANCELLED);
							mail.setComments(getHtmlComments(fbpReq.getComments()));
							String msgBody = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_CANCELLED), mail);
							mail.setMailBody(msgBody);
							mail.setMailSubject(subject);
							fbpReq.setStatus(Status.CANCELLED);
							fbpReq.setSequence(0);
							fbpReqDao.update(new FbpReqPk(fbpId), fbpReq);
							subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " is Cancelled By " + loggedUser;
							// update the siblings in the ESR MAP
							esrMap.setSiblings("");
							empSerReqMapDao.update(new EmpSerReqMapPk(esrMap.getId()), esrMap);
							// update the inbox entries
							inboxEntries = inboxDao.findWhereEsrMapIdEquals(esrMapId);
							List<String> ccMailIds = new ArrayList<String>();
							for (Inbox eachInbox : inboxEntries){
								eachInbox.setAssignedTo(0);
								eachInbox.setIsRead(0);
								eachInbox.setSubject(subject);
								eachInbox.setMessageBody(msgBody);
								eachInbox.setStatus(Status.CANCELLED);
								inboxDao.update(new InboxPk(eachInbox.getId()), eachInbox);
								profileInfo = profileInfoDao.findWhereUserIdEquals(eachInbox.getReceiverId());
								ccMailIds.add(profileInfo.getOfficalEmailId());
							}
							profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
							sendMail(mail, profileInfo.getOfficalEmailId(), null, null, ccMailIds.toArray(new String[ccMailIds.size()]));
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.InvalidStatusForCancelling"));
						}
					} else{
						logger.error(loggedUser + " is not autorized to cancell the request raised by the user " + userId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.IncorrectCancell", new Object[] { login.getUserName(), userId }));
					}
					break;
				case REJECTED:
					int loggedUserId = login.getUserId();
					profileInfo = profileInfoDao.findWhereUserIdEquals(loggedUserId);
					String loggedInUser = null;
					if (profileInfo != null) loggedInUser = profileInfo.getFirstName() + " " + profileInfo.getLastName();
					if (Arrays.asList(esrMap.getSiblings().split(",")).contains(String.valueOf(loggedUserId))){
						ProfileInfo userProfileInfo = profileInfoDao.findWhereUserIdEquals(userId);
						String userName = null;
						if (userProfileInfo != null) userName = userProfileInfo.getFirstName() + " " + userProfileInfo.getLastName();
						subject = "Diksha Lynx: FBP Request " + esrMap.getReqId() + " has been cancelled by " + loggedInUser;
						// update the status for the request record
						fbpReq.setStatus(Status.REJECTED);
						fbpReq.setSequence(0);
						// update the comments field in the Req table
						String existingComments = fbpReq.getComments();
						existingComments = existingComments == null ? "" : existingComments;
						profileInfo = profileInfoDao.findWhereUserIdEquals(login.getUserId());
						if (profileInfo != null) comments = CRLF + profileInfo.getFirstName() + " " + profileInfo.getLastName() + "(" + empId + "): " + fbpForm.getComments() + ", " + PortalUtility.getdd_MM_yyyy(new Date()) + "";
						fbpReq.setComments(comments + existingComments);
						mail.setEmpFname(userName);
						mail.setEmployeeId(user1.getEmpId());
						mail.setReqId(esrMap.getReqId());
						mail.setApproverName(loggedInUser);
						mail.setDateOfAction(PortalUtility.getdd_MM_yyyy(new Date()));
						mail.setMessageBody(getHtmlOfFbpDetails(fbpId));
						mail.setComments(getHtmlComments(fbpReq.getComments()));
						String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.FBP_REJECTED), mail);
						fbpReq.setMessageBody(msg);
						fbpReqDao.update(new FbpReqPk(fbpReq.getId()), fbpReq);
						mail.setMailBody(msg);
						mail.setTemplateName(MailSettings.FBP_REJECTED);
						// update the siblings in the ESR MAP
						esrMap.setSiblings("");
						empSerReqMapDao.update(new EmpSerReqMapPk(esrMap.getId()), esrMap);
						// Update the inbox records
						List<String> ccMailIds = new ArrayList<String>();
						inboxEntries = inboxDao.findWhereEsrMapIdEquals(esrMapId);
						for (Inbox eachInbox : inboxEntries){
							eachInbox.setStatus(Status.REJECTED);
							eachInbox.setIsRead(0);
							eachInbox.setSubject(subject);
							eachInbox.setMessageBody(msg);
							eachInbox.setAssignedTo(0);
							eachInbox.setCreationDatetime(new Date());
							profileInfo = profileInfoDao.findWhereUserIdEquals(eachInbox.getReceiverId());
							if (profileInfo != null) ccMailIds.add(profileInfo.getOfficalEmailId());
							inboxDao.update(new InboxPk(eachInbox.getId()), eachInbox);
						}
						sendMail(mail, userProfileInfo.getOfficalEmailId(), null, null, ccMailIds.toArray(new String[ccMailIds.size()]));
					} else{
						logger.error("The logged user is not a valid Approver for the record to reject");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.IncorrectUserApproval", new Object[] { loggedInUser }));
					}
			}
			return result;
		} catch (DaoException e){
			logger.error("There was a DaoException that has occured in the Block " + e.getMessage());
		} catch (FileNotFoundException e){
			logger.error("There was a FileNotFoundException that has occured in the Block while getting the HTML template" + e.getMessage());
		}
		return result;
	}

	private String getHtmlOfFbpDetails(int fbpId) {
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		FbpDetails[] fbpDetails = null;
		StringBuffer msgBuffer = new StringBuffer();
		try{
			fbpDetails = fbpDetailsDao.findWhereFbpIdEquals(fbpId);
		} catch (FbpDetailsDaoException e){
			logger.error("There is a Dao exception while fetching the records of the FBPDETAILS for the FbpID " + fbpId + e.getMessage());
		}
		if (fbpDetails != null && fbpDetails.length > 0){
			List<FbpDetails> benefit = new ArrayList<FbpDetails>();
			FbpComponent fbpComp = new FbpComponent();
			for (FbpDetails element : fbpDetails){
				FbpComponent currComp = fbpComp.getFbpComponentByName(element.getFbp());
				if (currComp != null){
					element.setFbpLabel(currComp.getFbpLabel());
					element.setFbpOrder(currComp.getOrder());
				}
				benefit.add(element);
			}
			sortBenefit(benefit);
			msgBuffer = new StringBuffer("<table class=\"FbpData\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\"><tr><th>FBP</th><th>Amount</th><tr>");
			for (FbpDetails eachBenefit : benefit){
				msgBuffer.append("<tr><td>" + eachBenefit.getFbpLabel() + " (" + eachBenefit.getFbp() + ")</td>");
				msgBuffer.append("<td>" + eachBenefit.getUsedAmt() + "</td></tr>");
			}
			msgBuffer.append("</table>");
		}
		return msgBuffer.toString();
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		FBPForm fbpForm = (FBPForm) form;
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		FbpDetails[] fbpDetails = null;
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		switch (ActionMethods.DeleteTypes.getValue(form.getdType())) {
		// Deletes the single details record
			case DELETE:
				try{
					fbpDetailsDao.delete(new FbpDetailsPk(fbpForm.getFbpId()));
				} catch (FbpDetailsDaoException e){
					logger.error("The DaoException occured while deleting the FBP records having the ID " + fbpForm.getId());
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtodeleteFBP"));
					e.getMessage();
				}
				break;
			// Deletes the single Req record and all child Details records
			case DELETEALL:
				try{
					fbpReqDao.delete(new FbpReqPk(fbpForm.getId()));
					fbpDetails = fbpDetailsDao.findWhereFbpIdEquals(fbpForm.getFbpId());
					if (fbpDetails != null && fbpDetails.length > 0){
						for (FbpDetails eachFbpDetails : fbpDetails){
							fbpDetailsDao.delete(new FbpDetailsPk(eachFbpDetails.getId()));
						}
					}
				} catch (DaoException e){
					logger.error("The DaoException occured while deleting the FBP records For the user " + fbpForm.getUserID());
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtodeleteFBP"));
					e.getMessage();
				}
				break;
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getFbpEligibleAmount(String fbpAbbr, FbpConfig fbpConfig) {
		String eligibleValue = "0";
		try{
			String methodName = "GET" + fbpAbbr;
			methodName = methodName.toUpperCase();
			Method fbpConfigMethod = FBPModel.fbpConfigMethods.get(methodName);
			Object[] params = null;
			if (fbpConfigMethod != null){
				Object result = fbpConfigMethod.invoke(fbpConfig, params);
				eligibleValue = result != null ? result.toString() : "0";
			} else{
				eligibleValue = "0";
				logger.error("Unable to find method to get value for FBP: " + fbpAbbr);
			}
		} catch (IllegalArgumentException ex){
			logger.error("Unable to find method to retrieve: " + fbpAbbr);
			logger.error("Illegal argument: " + ex.getMessage());
		} catch (IllegalAccessException ex){
			logger.error("Unable to find method to retrieve: " + fbpAbbr);
			logger.error("Illegal access: " + ex.getMessage());
		} catch (InvocationTargetException ex){
			logger.error("Unable to find method to retrieve: " + fbpAbbr);
			logger.error("Illegal target object: " + ex.getMessage());
		}
		return (eligibleValue);
	}

	public static HashMap<String, Method> getFbpConfigMethods() {
		HashMap<String, Method> fbpConfigMethods = new HashMap<String, Method>();
		Method[] methods = FbpConfig.class.getMethods();
		for (Method element : methods){
			fbpConfigMethods.put(element.getName().toUpperCase(), element);
		}
		return (fbpConfigMethods);
	}

	public ProcessChain getRequestorProcessChain(int requesterId) {
		ProcessChain processChain = null;
		ProcessChainDao processChainProvider = ProcessChainDaoFactory.create();
		try{
			processChain = processChainProvider.findByDynamicSelect("select * from PROCESS_CHAIN where ID=(select PROC_CHAIN_ID from MODULE_PERMISSION where ROLE_ID=(select ROLE_ID from USER_ROLES where USER_ID=?) " + "AND MODULE_ID=?)", new Object[] { new Integer(requesterId), FBP_MODULE_ID })[0];
		} catch (ProcessChainDaoException ex){
			logger.error("Unable to get process chain for user ID: " + requesterId, ex);
		}
		return processChain;
	}

	@Override
	public Attachements download(PortalForm form) {
		Attachements report = new Attachements();
		FBPForm fbpForm = null;
		if (form instanceof FBPForm){
			fbpForm = (FBPForm) form;
			String downloadType = fbpForm.getdType();
			// Get report type
			// pdf or excel
			String paramType = fbpForm.getType();
			// Get report year
			// Parameter: year=2013-2014
			// Mandatory parameter
			String paramYear = fbpForm.getYear();
			String paramFinancialYear[] = null;
			// Get report selected months
			// Paramenter: month=01;07;12
			String paramMonth = fbpForm.getMonth();
			String monthId = "'" + String.valueOf(paramYear) + "','";
			// Get report selected level label
			// Paramenter: level=L2-F2
			String paramLevel = fbpForm.getLevel();
			// Get report empId
			int empId = fbpForm.getEmpId();
			int userId = 0;
			Calendar calendar = Calendar.getInstance();
			// If report year not passed take current year
			if (paramYear == null || paramYear.trim().length() < 4){
				String temp = paramYear;
				// paramYear=String.valueOf(calendar.get(Calendar.YEAR));
				paramYear = getFinancialYear();
				paramFinancialYear = paramYear.split("-");
				logger.debug("Invalid year: " + temp + ". Processing FBP declaration for year: " + paramYear);
			} else{
				paramFinancialYear = paramYear.split("-");
			}
			// If report months not passed set it to current month
			// other wise get monthId CSVl: '201301','201307','201312'
			if (paramMonth == null || paramMonth.trim().length() < 2){
				int calMonth = calendar.get(Calendar.MONTH) + 1;
				if (calMonth >= 4 && paramFinancialYear.length > 0) monthId = paramFinancialYear[0] + (calMonth < 10 ? "0" + calMonth : String.valueOf(calMonth));
				else if (paramFinancialYear.length > 0) monthId = paramFinancialYear[1] + (calMonth < 10 ? "0" + calMonth : String.valueOf(calMonth));
				else{
					logger.debug("Unable to create MonthId for filtering report: " + paramFinancialYear + ", PRRAMYEAR: " + paramYear);
				}
			} else{
				String temp[] = paramMonth.split(";");
				for (String element : temp){
					int pMonth = Integer.parseInt(element);
					String tempMonthId = null;
					if (pMonth >= 4 && paramFinancialYear.length > 0) tempMonthId = paramFinancialYear[0] + (pMonth < 10 ? "0" + pMonth : String.valueOf(pMonth));
					else if (paramFinancialYear.length > 0) tempMonthId = paramFinancialYear[1] + (pMonth < 10 ? "0" + pMonth : String.valueOf(pMonth));
					else{
						logger.debug("Unable to create MonthId for filtering report: " + paramFinancialYear + ", PRRAMYEAR: " + paramYear);
					}
					if (tempMonthId != null) monthId += tempMonthId + "','";
				}
				if (monthId.length() > 2){
					monthId = monthId.substring(0, monthId.lastIndexOf(","));
				}
			}
			if (empId > 0){
				UsersDao userProvider = UsersDaoFactory.create();
				Users[] user;
				try{
					user = userProvider.findWhereEmpIdEquals(empId);
					if (user.length == 1){
						userId = user[0].getUserId();
					} else userId = 0;
				} catch (UsersDaoException ex){
					logger.error("Unable to get userId for EMP_ID: " + empId, ex);
					// TODO: Add UI error and return
					return (report);
				}
			} else logger.debug("Processing FBP declaration for all employees as employee id not passed.");
			switch (DownloadTypes.getValue(downloadType)) {
				case FBPDECLARATION:{
					// Used in report query
					// eg. FBP_REQ.MONTH_ID IN ('2013','201304','201305','201306')
					String whereFbpDetailsClause = "";
					if (monthId.length() > 0) whereFbpDetailsClause = " FBP_REQ.MONTH_ID IN(" + monthId + ")";
					else{
						// TODO: Add UI Error here that no months have been selected
						// and return
					}
					if (paramLevel != null && paramLevel.length() > 0) whereFbpDetailsClause += " AND FBP_REQ.LEVEL='" + paramLevel + "'";
					// FBP_20130421125820.pdf or FBP_20130421125820.xls
					String destFileName = PortalData.FBP_DECLARATION + PortalUtility.formatDateToyyyymmddahhmmss(calendar.getTime());
					JGenerator jGenerator = new JGenerator();
					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("YEAR", paramYear);
					params.put("MONTH_IDS_CLAUSE", whereFbpDetailsClause);
					String template = JTemplates.FBP_DECLARATION;
					boolean isReportGenerated = false;
					if (paramType.equalsIgnoreCase("pdf")){
						destFileName += "." + ExportType.pdf;
						isReportGenerated = jGenerator.generateFile(JGenerator.FBP_DECLARATION, destFileName, template, params);
					} else{
						destFileName += "." + ExportType.xls;
						isReportGenerated = jGenerator.generateXlsFile(JGenerator.FBP_DECLARATION, destFileName, template, params);
					}
					if (isReportGenerated){
						String destPath = null;
						try{
							destPath = JGenerator.getOutputFile(JGenerator.FBP_DECLARATION, destFileName);
						} catch (Exception ex){
							logger.error("Unable to get generated report file: " + destFileName, ex);
							// TODO: Add code to return UI Error
						}
						report.setFileName(destFileName);
						report.setFilePath(destPath);
					} else{
						// TODO: Add code to return UI error
					}
				}
					break;
				default:
					break;
			}
		} else{
			logger.debug("Invalid form bean send while downloading FBP report.");
		}
		return (report);
	}

	protected String getHtmlComments(String comments) {
		if (comments == null || comments.length() <= 0) comments = "Comments : <br/>-";
		else comments = "Comments : " + comments.replaceAll(CRLF, "<br/>");
		return (comments);
	}

	protected String getUpdatedMessageBody(String messageBody, String comments) {
		StringBuilder sb = new StringBuilder(messageBody);
		comments = getHtmlComments(comments);
		int pos = sb.lastIndexOf(COMMENT_PARAGRAPH);
		if (pos != -1){
			int startPos = pos + COMMENT_PARAGRAPH.length();
			int endPos = sb.indexOf("</p>", startPos);
			sb.replace(startPos, endPos, comments);
		} else{
			logger.debug("Invalid email body to replace: " + COMMENT_PARAGRAPH + " with new comments: " + comments);
		}
		return (sb.toString());
	}

	protected void sortBenefit(List<FbpDetails> benefits) {
		Collections.sort(benefits, new Comparator<FbpDetails>() {

			public int compare(FbpDetails d1, FbpDetails d2) {
				int result = 0;
				if (d1 != null && d2 != null){
					result = d1.getFbpOrder() - d2.getFbpOrder();
				} else{
					result = 0;
				}
				return (result);
			}
		});
	}

	protected int getFbpModuelId() {
		int moduleId = 0;
		try{
			ModulesDao moduleProvider = ModulesDaoFactory.create();
			Modules[] module = moduleProvider.findWhereNameEquals(FBP_MODULE_NAME);
			if (module.length == 1){
				moduleId = module[0].getId();
			} else{
				throw new ModulesDaoException("Module not found with name: " + FBP_MODULE_NAME);
			}
		} catch (ModulesDaoException ex){
			logger.error("Unable to find module with MODULE.NAME: " + FBP_MODULE_NAME, ex);
		}
		return (moduleId);
	}

	/**
	 * method to parse the Excel file and get the Fbp records inserted. If the
	 * record is erroreneous it deletes the incorrected records and process the
	 * next records. It return the list of users (Comma seperated) for whom the
	 * records are not correctly inserted.
	 * 
	 * @param filePath
	 * @return
	 */
	public String uploadFBPDetails(String filePath, InputStream stream) {
		String notSuccess = "0";
		try{
			FbpConfigDao fbpConfigDao = FbpConfigDaoFactory.create();
			FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
			FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			FbpReq fbpReq = null;
			FbpConfig fbpConfig = null;
			Users user = null;
			Levels level = null;
			int emp_id = 0;
			// String emp_id = null, lta = null , tpa = null , ma = null , cea =
			// null , mv = null , tra = null , oa = null ;
			String[] values = new String[8];
			InputStream fs = stream; // new FileInputStream(new File(filePath));
										// stream
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			Connection conn=null;
			Statement s=null;
			final boolean isConnSupplied = (conn != null);
			try {
				
				
				conn = ResourceManager.getConnection();
				conn = isConnSupplied ? conn : ResourceManager.getConnection();
			     s = conn.createStatement();

				s.execute("SET FOREIGN_KEY_CHECKS = 0");
				
				JDBCUtiility.getInstance().update("TRUNCATE FBP_DETAILS", null);
				JDBCUtiility.getInstance().update("TRUNCATE FBP_REQ", null);
				s.execute("SET FOREIGN_KEY_CHECKS = 1");
				
			} catch (Exception _e) {
				
				logger.error("Exception: " + _e.getMessage(), _e);
				_e.printStackTrace();
			}finally{
				//ResourceManager.close(rs);
				s.close();
				if (!isConnSupplied){
					ResourceManager.close(conn);
				}
			}
			
			
			
			
			boolean currRecSucc = false;
			while (rowIterator.hasNext()){
				int fbpId = 0;
				try{
					currRecSucc = false;
					emp_id = 0;
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					if (row.getRowNum() == 0) continue;
					while (cellIterator.hasNext()){
						Cell cell = cellIterator.next();
						// emp_id = ""; lta = "" ; tpa = "" ; ma = "" ; cea = ""
						// ; mv = "" ; tra = "" ; oa = "" ;
						int colNum = cell.getColumnIndex();
						switch (colNum) {
							case 0:
								// logger.info(cell.getCellType());
								// values[colNum] = cell.getRichStringCellValue();
								emp_id = (int) cell.getNumericCellValue();
								break;
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 7:
								values[colNum] = cell.getNumericCellValue() > 0 ? (cell.getNumericCellValue() + "") : "NA";
							default:
								break;
						}
					}
					// create FBP_req record
					user = usersDao.findWhereEmpIdEquals(Integer.valueOf(emp_id))[0];
					int levelId = user.getLevelId();
					level = levelsDao.findByPrimaryKey(levelId);
					fbpReq = new FbpReq();
					fbpReq.setUserId(user.getId());
					fbpReq.setLevelId(levelId);
					fbpReq.setLevel(level.getLabel());
					fbpReq.setMonthId(getFinancialYear());
					fbpReq.setStatus("Processed");
					fbpReq.setFrequent("yearly");
					fbpReq.setCreatedOn(new Date());
					fbpId = fbpReqDao.insert(fbpReq).getId();
					fbpConfig = fbpConfigDao.findWhereLevelIdEquals(levelId)[0];
					// Creates FBP_DETAILS records
					createFbp(fbpConfig, fbpId, values);
					currRecSucc = true;
					logger.info("FBP details uploaded successfully for employee id : " + emp_id);
				} catch (NumberFormatException e){
					logger.error("There is a NumberFormatException occured while uploading the FBP details" + e.getMessage());
				} catch (UsersDaoException e){
					logger.error("There is a UsersDaoException occured while uploading the FBP details" + e.getMessage());
				} catch (LevelsDaoException e){
					logger.error("There is a LevelsDaoException occured while uploading the FBP details" + e.getMessage());
				} catch (FbpReqDaoException e){
					logger.error("There is a FbpReqDaoException occured while uploading the FBP details" + e.getMessage());
				} catch (FbpConfigDaoException e){
					logger.error("There is a FbpConfigDaoException occured while uploading the FBP details" + e.getMessage());
				} catch (FbpDetailsDaoException e){
					logger.error("There is a FbpDetailsDaoException occured while uploading the FBP details" + e.getMessage());
				} catch (Exception e){
					logger.error("There is a Exception occured while uploading the FBP details", e);
				}
				if (!currRecSucc){
					try{
						FbpDetails[] fbpDetails = fbpDetailsDao.findWhereFbpIdEquals(fbpId);
						for (FbpDetails eachDetail : fbpDetails){
							fbpDetailsDao.delete(eachDetail.createPk());
						}
						fbpReqDao.delete(new FbpReqPk(fbpId));
						notSuccess += "," + emp_id;
					} catch (Exception e){
						logger.error("There is a Exception occured while deleting the incorrect records the FBP details" + e.getMessage());
					}
				}
			}
			fs.close();
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException occured while uploading the FBP details" + e.getMessage());
		} catch (IOException e){
			logger.error("There is a FileNotFoundException occured while uploading the FBP details" + e.getMessage());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return notSuccess;
	}

	private void createFbp(FbpConfig fbpConfig, int fbpId, String[] values) throws FbpDetailsDaoException {
		String[] fbps = { "", "MA", "MV", "LTA", "TPA", "TRA", "CEA", "OA" };
		Map<String, String> map = new HashMap<String, String>();
		map.put("LTA", fbpConfig.getLta());
		map.put("TPA", fbpConfig.getTpa());
		map.put("MA", fbpConfig.getMa());
		map.put("CEA", fbpConfig.getCea());
		map.put("MV", fbpConfig.getMv());
		map.put("TRA", fbpConfig.getTra());
		map.put("OA", fbpConfig.getOa().equals("UA") ? "NA" : "");
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		FbpDetails fbpDetails = null;
		String eligAmt = "";
		String uAmt = "";
		for (int i = 1; i < 8; i++){
			fbpDetails = new FbpDetails();
			fbpDetails.setFbpId(fbpId);
			fbpDetails.setFbp(fbps[i]);
			eligAmt = map.get(fbps[i]);
			uAmt = values[i];
			fbpDetails.setEligibleAmt(eligAmt);
			fbpDetails.setUsedAmt(uAmt);
			if (!eligAmt.equals("") && !eligAmt.equals("NA") && !eligAmt.equals("UA")){
				if (!uAmt.equals("") && !uAmt.equals("NA")){
					fbpDetails.setUnusedAmt(String.valueOf(Float.valueOf(eligAmt) - Float.valueOf(uAmt)));
				} else fbpDetails.setUnusedAmt(eligAmt);
			} else fbpDetails.setUnusedAmt("NA");
			// if(!eligAmt.equals("") && !eligAmt.equals("NA") &&
			// !eligAmt.equals("OA")){
			// if(!uAmt.equals("") && uAmt.equals("NA"))
			// fbpDetails.setUnusedAmt(String.valueOf(Float.valueOf(eligAmt) -
			// Float.valueOf(uAmt)));
			// }else fbpDetails.setUnusedAmt("NA");
			fbpDetailsDao.insert(fbpDetails);
		}
	}

	public static void main(String[] args) {
		// logger.info(new
		// FBPModel().uploadFBPDetails("/home/praneeth.r/Desktop/FBP_Upload_template.xls"));
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		DocumentTypes dTypes = DocumentTypes.valueOf(docType);
		Integer[] nonsuccess = null;
		if (dTypes.equals(DocumentTypes.FBP_XLS)){
			if (fileItems != null && !fileItems.isEmpty()){
				FileItem file = (FileItem) fileItems.get(0);
				InputStream stream = null;
				// FileOutputStream fs = null;
				try{
					stream = file != null ? file.getInputStream() : null;
					// File oFile = new File(new PortalData().getDirPath() +
					// File.separator + "Data" + File.separator +
					// "FBP_Upload_template.xls");
					// oFile.createNewFile();
					// fs = new FileOutputStream(oFile);
					// while(stream.available() != -1){
					// fs.write(stream.read());
					// }
					// success = true;
				} catch (IOException e1){
					e1.printStackTrace();
				}
				// if(success){
				String[] temp = uploadFBPDetails(new PortalData().getDirPath() + File.separator + "Data" + File.separator + "FBP_Upload_template.xls", stream).split(",");
				nonsuccess = new Integer[temp.length];
				for (int i = 0; i < temp.length; i++){
					try{
						nonsuccess[i] = Integer.parseInt(temp[i]);
					} catch (NumberFormatException nfe){};
				}
			}
		}
		// }
		return nonsuccess;
	}
}