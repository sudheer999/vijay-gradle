package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.HolidaysListBean;
import com.dikshatech.beans.LeaveBean;
import com.dikshatech.beans.LeaveMasterBean;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.RequestEscalation;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LeaveBalanceDao;
import com.dikshatech.portal.dao.LeaveHistroyDao;
import com.dikshatech.portal.dao.LeaveLwpDao;
import com.dikshatech.portal.dao.LeaveMasterDao;
import com.dikshatech.portal.dao.LeaveTypeDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.ProjectMapDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.ServiceReqInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Holidays;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.LeaveBalancePk;
import com.dikshatech.portal.dto.LeaveHistroy;
import com.dikshatech.portal.dto.LeaveHistroyPk;
import com.dikshatech.portal.dto.LeaveLwp;
import com.dikshatech.portal.dto.LeaveLwpPk;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.LeaveMasterPk;
import com.dikshatech.portal.dto.LeaveReceiveBean;
import com.dikshatech.portal.dto.LeaveType;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectMap;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.ServiceReqInfo;
import com.dikshatech.portal.dto.ServiceReqInfoPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.LeaveMasterDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.HolidaysDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LeaveBalanceDaoFactory;
import com.dikshatech.portal.factory.LeaveHistroyDaoFactory;
import com.dikshatech.portal.factory.LeaveLwpDaoFactory;
import com.dikshatech.portal.factory.LeaveMasterDaoFactory;
import com.dikshatech.portal.factory.LeaveTypeDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.ProjectMapDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.ServiceReqInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.DropDownBean;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class LeaveModel extends ActionMethods {

	private static Logger		logger			= LoggerUtil.getLogger(LeaveModel.class);
	private RequestEscalation	reqEscalation	= new RequestEscalation();

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		request.setAttribute("actionForm", "");
		ActionResult result = new ActionResult();
		LeaveMaster leaveMasterData = (LeaveMaster) form;
		ProcessChain process_chain_dto = new ProcessChain();
		UsersDao usersDao = UsersDaoFactory.create();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		EmpSerReqMap empReqMapDto = new EmpSerReqMap();
		LeaveMasterPk leaveMasterPk = new LeaveMasterPk();
		LeaveMasterDao leavMasterDao = LeaveMasterDaoFactory.create();
		LeaveHistroyDao leaveHistroyDao = LeaveHistroyDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		// Users users = new Users();
		RegionsDao regionDao = RegionsDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		EmpSerReqMapDao emp_Ser_Req_Map_Dao = EmpSerReqMapDaoFactory.create();
		LeaveBalanceDao leaveBalanceDao = LeaveBalanceDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ServiceReqInfoDao reqinfoDao = ServiceReqInfoDaoFactory.create();
		Date date = new Date();
		MailGenerator mailGenarator = new MailGenerator();
		ProjectDao projectDao = ProjectDaoFactory.create();
		Project[] project = null;
		ChargeCodeDao chareCodeDao = ChargeCodeDaoFactory.create();
		ChargeCode[] chargeCode = null;
		Login login = Login.getLogin(request);
		int user_id = login.getUserId();
		InboxModel inboxModel = new InboxModel();
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		boolean approver = true;
		try{
			Users userdata = usersDao.findByPrimaryKey(user_id);
			ProfileInfo requsterprofile = profileInfoDao.findByPrimaryKey(userdata.getProfileId());
			switch (SaveTypes.getValue(form.getsType())) {
				case SAVE:{
					try{
						if (leaveMasterData.getDuration() * 10 % 5 != 0){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
							break;
						}
						LeaveBalance leaveBalance = leaveBalanceDao.findWhereUserIdEquals(user_id);
						if (leaveBalance != null){
							if (leaveMasterData.getLeaveType() == 3){
								if (leaveBalance.getCompOff() == 0){
									result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.nocompoff"));
									break;
								} else if (leaveBalance.getCompOff() < leaveMasterData.getDuration()){
									result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notenoughcompoff", leaveBalance.getCompOff()));
									break;
								}
							}
						}
						Regions reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(user_id) })[0];
						String reg_abb = reg_id.getRefAbbreviation();
						// requestor profile
						// Get Process chain DTO object
						process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=22  WHERE UR.USER_ID=?", new Object[] { new Integer(user_id) })[0];
						Integer[] approverGroupIds = pEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, user_id);
						int unQ = 1;
						EmpSerReqMap empMap[] = emp_Ser_Req_Map_Dao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=1 AND REQ_ID NOT LIKE '%CN' AND REQ_ID LIKE '%LV%')", null);
						if (empMap.length > 0){
							String s = empMap[0].getReqId().split("-")[2];
							unQ = Integer.parseInt(s) + 1;
						}
						// save Data in EMP_SER_REQ_MAP TABLE
						empReqMapDto = new EmpSerReqMap();
						empReqMapDto.setSubDate(date);
						empReqMapDto.setReqId(reg_abb + "-LV-" + unQ);
						empReqMapDto.setReqTypeId(1);
						empReqMapDto.setRegionId(reg_id.getId());
						empReqMapDto.setRequestorId(user_id);
						empReqMapDto.setSiblings(ModelUtiility.getCommaSeparetedValues(approverGroupIds));
						empReqMapDto.setProcessChainId(process_chain_dto.getId());
						empReqMapDto.setNotify(process_chain_dto.getNotification());
						reqpk = emp_Ser_Req_Map_Dao.insert(empReqMapDto);// ------------exception
						if (leaveMasterData.getLeaveProject() != null && leaveMasterData.getLeaveProject() != ""){
							String fieldsArray[] = leaveMasterData.getLeaveProject().split(",");
							if (fieldsArray.length > 0){
								if (!fieldsArray[0].equals("0") || !fieldsArray[0].equals("")){
									project = projectDao.findWhereIdEquals(Integer.parseInt(fieldsArray[0]));
									leaveMasterData.setProjectName(project[0].getName());
								}
								if (!fieldsArray[1].equals("0") || !fieldsArray[1].equals("")){
									chargeCode = chareCodeDao.findWhereIdEquals(Integer.parseInt(fieldsArray[1]));
									leaveMasterData.setProjectTitle(chargeCode[0].getChCodeName());
								}
							}
						}
						leaveMasterData.setEsrMapId(reqpk.getId());
						if (approverGroupIds != null && approverGroupIds.length > 0){
							PortalMail portalMail = null;
							for (int j : approverGroupIds){
								LeaveMaster leaveMasterData1 = LeaveMaster.getDto(leaveMasterData);
								leaveMasterData1.setId(0);
								leaveMasterData1.setStatus(com.dikshatech.common.utils.Status.REQUESTRAISED);
								leaveMasterData1.setAssignedTo(j);
								int div_id = levelsDao.findByDynamicSelect("SELECT * FROM LEVELS L LEFT JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(j) })[0].getDivisionId();
								leaveMasterData1.setAssignedToDivision(div_id);
								leaveMasterData1.setAppliedDateTime(new Date());
								ProfileInfo appProfile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(j) })[0];
								// insert into LeaveMaster Table
								/**
								 * This piece of code is used to generate UUID for candidate
								 * accept/reject of offer
								 */
								UUID uuid = UUID.randomUUID();
								leaveMasterData1.setUuid(uuid.toString());
								leaveMasterData1.setActionByNull(true);
								leaveMasterPk = leavMasterDao.insert(leaveMasterData1);
								String acceptLink = "validate?uuid=" + leaveMasterData1.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=1" + "&type=leave" + "&assignedTo=" + leaveMasterData1.getAssignedTo();
								String rejectLink = "pages/leaveReject.jsp?uuid=" + leaveMasterData1.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=2" + "&type=leave" + "&assignedTo=" + leaveMasterData1.getAssignedTo();
								leaveMasterData.setAcceptLink(acceptLink);
								leaveMasterData.setRejectLink(rejectLink);
								portalMail = sendMailDetails("New leave request - " + requsterprofile.getFirstName() + " from " + PortalUtility.getdd_MM_yyyy(leaveMasterData.getFromDate()) + " to " + PortalUtility.getdd_MM_yyyy(leaveMasterData.getToDate()), MailSettings.LEAVE_RECEIVE, userdata, requsterprofile, appProfile, leaveMasterData, leaveBalance, 0, Status.SUBMITTED, null, request);
						// sending Android Notification
								String msg="New leave request - " + requsterprofile.getFirstName() + " from " + PortalUtility.getdd_MM_yyyy(leaveMasterData.getFromDate()) + " to " + PortalUtility.getdd_MM_yyyy(leaveMasterData.getToDate());
								List<String> mailids=new ArrayList<String>();
								mailids.add(portalMail.getRecipientMailId());
								if(portalMail.getAllReceipientcCMailId()!=null){
								for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
								sendAndroidLeaveNotification(msg,mailids);
								
								
								String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(portalMail.getTemplateName()), portalMail);
								leaveMasterData1.setMessageBody(bodyText);
								leavMasterDao.update(leaveMasterPk, leaveMasterData1);//update message body
								leavMasterDao.update(leaveMasterPk, leaveMasterData1);
								inboxModel.sendToDockingStation(leaveMasterData1.getEsrMapId(), leaveMasterPk.getId(), portalMail.getMailSubject(), leaveMasterData1.getStatus());
								insertServiceInfo(leaveMasterData1);
							}
							notifyHRSPOC(portalMail, requsterprofile.getHrSpoc());
							LeaveHistroy leaveHistroy = new LeaveHistroy();
							leaveHistroy.setEsrMapId(leaveMasterData.getEsrMapId());
							leaveHistroy.setLeaveType(leaveMasterData.getLeaveType());
							leaveHistroy.setCurrentDuration(leaveMasterData.getDuration());
							leaveHistroy.setCurrentQuotaBalance(0);
							leaveHistroyDao.insert(leaveHistroy);
						} else{
							approver = false;
							throw new Exception();
						}
					} catch (Exception e){
						reqpk.setId(leaveMasterData.getEsrMapId());
						emp_Ser_Req_Map_Dao.delete(reqpk);
						if (!approver){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.approver"));
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
						}
						e.printStackTrace();
					}
				}
					break;
				case CANCELLSAVE:{
					try{
						if (leaveMasterData.getDuration() * 10 % 5 != 0){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
							break;
						}
						EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
						process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=22  WHERE UR.USER_ID=?", new Object[] { new Integer(user_id) })[0];
						if (leaveMasterData.getLeavemasterIds().length > 0){
							LeaveBalance leaveBalance = LeaveBalanceDaoFactory.create().findWhereUserIdEquals(login.getUserId());
							for (int k : leaveMasterData.getLeavemasterIds()){
								Integer[] approverGroupIds = pEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, login.getUserId());
								LeaveMaster leaveMaster2 = leavMasterDao.findByPrimaryKey(k);
								LeaveMaster leaveMasterDto = new LeaveMaster();//LeaveMaster.getDto(leaveMaster2);
								if (approverGroupIds != null && approverGroupIds.length > 0){
									ServiceReqInfo[] serviceReqInfos = reqinfoDao.findWhereEsrMapIdEquals(leaveMaster2.getEsrMapId());
									// if already leave canceled.
									if (leaveMaster2.getToCancell() == 1 && !serviceReqInfos[0].getStatus().equals(Status.REQUESTRAISED)){
										if (leaveMasterData.getLeavemasterIds().length == 1){
											result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.reqcancelled", "Leave"));
										}
										break;
									}
									if (leaveMasterData.getDuration() > leaveMaster2.getDuration()){
										result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
										break;
									}
									if (serviceReqInfos[0].getStatus().equals(Status.PENDINGAPPROVAL) || serviceReqInfos[0].getStatus().equals(Status.REJECTED)){
										result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.cannotcancelleave"));
										break;
									}
									leaveMaster2.setToCancell(1);
									leavMasterDao.update(new LeaveMasterPk(leaveMaster2.getId()), leaveMaster2);
									EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(leaveMaster2.getEsrMapId());
									ModelUtiility.getInstance().deleteInboxEntries(leaveMaster2.getEsrMapId());
									if (serviceReqInfos[0].getStatus().equals(Status.REQUESTRAISED)){
										serviceReqInfos[0].setStatus(Status.CANCELLED);
										reqinfoDao.update(new ServiceReqInfoPk(serviceReqInfos[0].getId()), serviceReqInfos[0]);
										leaveMaster2.setToCancell(1);
										leaveMaster2.setStatus(Status.CANCELLED);
										leaveMaster2.setUuid(null);
										leavMasterDao.update(new LeaveMasterPk(leaveMaster2.getId()), leaveMaster2);
										if (empSerReqMap.getReqId().endsWith("-CN")){
											LeaveMaster parentLeaveMaster = leavMasterDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE ID=(SELECT MAX(ID) FROM LEAVE_MASTER WHERE ESR_MAP_ID = (SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_ID=? AND REQUESTOR_ID=? GROUP BY REQ_ID) GROUP BY ESR_MAP_ID)", new Object[] { empSerReqMap.getReqId().replace("-CN", ""), user_id })[0];
											parentLeaveMaster.setToCancell(0);
											leavMasterDao.update(new LeaveMasterPk(parentLeaveMaster.getId()), parentLeaveMaster);
										}
									} else if (!serviceReqInfos[0].getStatus().equals(Status.REJECTED) && !serviceReqInfos[0].getStatus().equals(Status.PENDINGAPPROVAL)){
										empReqMapDto = new EmpSerReqMap();
										empReqMapDto.setSubDate(new Date());
										empReqMapDto.setReqId(empSerReqMap.getReqId() + "-CN");
										empReqMapDto.setReqTypeId(1);
										empReqMapDto.setRegionId(empSerReqMap.getRegionId());
										empReqMapDto.setRequestorId(user_id);
										empReqMapDto.setSiblings(ModelUtiility.getCommaSeparetedValues(approverGroupIds));
										empReqMapDto.setProcessChainId(process_chain_dto.getId());
										empReqMapDto.setNotify(process_chain_dto.getNotification());
										reqpk = emp_Ser_Req_Map_Dao.insert(empReqMapDto);
										boolean mailToHrflag = true;
										for (int j : approverGroupIds){
											leaveMasterDto.setStatus(Status.REQUESTRAISED);
											leaveMasterDto.setFromDate(leaveMasterData.getFromDate());
											leaveMasterDto.setToDate(leaveMasterData.getToDate());
											leaveMasterDto.setDuration(leaveMasterData.getDuration());
											leaveMasterDto.setAssignedTo(j);
											leaveMasterDto.setLeaveType(leaveMaster2.getLeaveType());
											leaveMasterDto.setContactNo(leaveMaster2.getContactNo());
											leaveMasterDto.setIslwp(leaveMaster2.getIslwp());
											leaveMasterDto.setAssignedToDivision(leaveMaster2.getAssignedToDivision());
											leaveMasterDto.setProjectTitle(leaveMaster2.getProjectTitle());
											leaveMasterDto.setProjectName(leaveMaster2.getProjectName());
											leaveMasterDto.setAppliedDateTime(new Date());
											leaveMasterDto.setToCancell(0);
											ProfileInfo appProfile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(j) })[0];
											UUID uuid = UUID.randomUUID();
											leaveMasterDto.setUuid(uuid.toString());
											leaveMasterDto.setEsrMapId(reqpk.getId());
											leaveMasterDto.setActionByNull(true);
											leaveMasterPk = leavMasterDao.insert(leaveMasterDto);
											String acceptLink = "validate?uuid=" + leaveMasterDto.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=1" + "&type=leave" + "&assignedTo=" + leaveMasterDto.getAssignedTo();
											String rejectLink = "pages/leaveReject.jsp?uuid=" + leaveMasterDto.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=2" + "&type=leave" + "&assignedTo=" + leaveMasterDto.getAssignedTo();
											leaveMaster2.setAcceptLink(acceptLink);
											leaveMaster2.setRejectLink(rejectLink);
											String mailSubject = "Diksha Lynx: New leave cancel request - " + requsterprofile.getFirstName() + " from " + PortalUtility.getdd_MM_yyyy(leaveMasterData.getFromDate()) + " to " + PortalUtility.getdd_MM_yyyy(leaveMasterData.getToDate());
											PortalMail portalMail = sendMailDetails(mailSubject, MailSettings.LEAVE_RECEIVE, userdata, requsterprofile, appProfile, leaveMaster2, leaveBalance, 0, Status.SUBMITTED, null, request);
										// sending Android Notification
										
											List<String> mailids=new ArrayList<String>();
											mailids.add(portalMail.getRecipientMailId());
											if(portalMail.getAllReceipientcCMailId()!=null){
											for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
											sendAndroidLeaveNotification(mailSubject,mailids);
											
											if (mailToHrflag){
												if (requsterprofile.getHrSpoc() > 2) notifyHRSPOC(portalMail, requsterprofile.getHrSpoc());
												mailToHrflag = false;
											}
											String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(portalMail.getTemplateName()), portalMail);
											leaveMasterDto.setMessageBody(bodyText);
											// insert into leave master
											leavMasterDao.update(leaveMasterPk, leaveMasterDto);
											// stop further req
											inboxModel.sendToDockingStation(leaveMasterDto.getEsrMapId(), leaveMasterPk.getId(), mailSubject, Status.REQUESTRAISED);
											insertServiceInfo(leaveMasterDto);
										}
									}
								}
							}
						}
					} catch (Exception e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
						e.printStackTrace();
					}
					break;
				}
				default:
					break;
			}
		} catch (Exception e){
			// TODO: handle exception
		}
		return result;
	}

	private void notifyHRSPOC(PortalMail portalMail, int hrSpoc) {
		if (hrSpoc <= 3) return;
		try{
			ProfileInfo appProfile = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { hrSpoc })[0];
			portalMail.setHandlerName(appProfile.getFirstName());
			portalMail.setTemplateName(MailSettings.LEAVE_APPLY);
			portalMail.setRecipientMailId(appProfile.getOfficalEmailId());
			new MailGenerator().invoker(portalMail);
		} catch (Exception e){
			logger.error("unable to notify hrspoc", e);
		}
	}

	public boolean insertServiceInfo(LeaveMaster leaveMaster) {
		try{
			ServiceReqInfo serviceReqInfo = new ServiceReqInfo();
			serviceReqInfo.setEsrMapId(leaveMaster.getEsrMapId());
			serviceReqInfo.setStatus(com.dikshatech.common.utils.Status.REQUESTRAISED);
			serviceReqInfo.setCreationDatetime(leaveMaster.getAppliedDateTime());
			ServiceReqInfoDao reqinfoDao = ServiceReqInfoDaoFactory.create();
			reqinfoDao.insert(serviceReqInfo);
		} catch (Exception e){
			EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
			EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
			reqpk.setId(leaveMaster.getEsrMapId());
			try{
				empSerReqMapDao.delete(reqpk);
			} catch (EmpSerReqMapDaoException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		LeaveMaster leaveMaster = (LeaveMaster) form;
		InboxModel inboxModel = new InboxModel();
		LeaveMasterDao leavMasterDao = LeaveMasterDaoFactory.create();
		ServiceReqInfoDao serviceReqInfoDao = ServiceReqInfoDaoFactory.create();
		ServiceReqInfoPk serviceReqInfoPk = new ServiceReqInfoPk();
		LeaveBalanceDao leaveBalanceDao = LeaveBalanceDaoFactory.create();
		LeaveLwpDao leaveLwpDao = LeaveLwpDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProcessChainDao processDao = ProcessChainDaoFactory.create();
		LeaveTypeDao leaveTypeDao = LeaveTypeDaoFactory.create();
		LeaveBalancePk leaveBalancePk = new LeaveBalancePk();
		LeaveLwpPk leaveLwpPk = new LeaveLwpPk();
		MailGenerator mailGenerator = new MailGenerator();
		ProcessEvaluator evaluator = new ProcessEvaluator();
		LeaveHistroyDao leaveHistroyDao = LeaveHistroyDaoFactory.create();
		boolean calculate = false;
		boolean noHandlers = false;
		Integer handlers[] = null;
		switch (UpdateTypes.getValue(form.getuType())) {
			case APPROVEREJECTCANCELL:{
				try{
					Login login = null;
					int userId = 0;
					if (request.getParameter("assignedTo") != null) userId = Integer.parseInt(request.getParameter("assignedTo"));
					else{
						login = Login.getLogin(request);
						userId = login.getUserId();
					}
					LeaveMasterPk leaveMasterPk = new LeaveMasterPk();
					String mailSubject = null;
					PortalMail portalMail = new PortalMail();
					boolean cancell = false;
					if (leaveMaster.getLeavemasterIds().length > 0){
						for (int k : leaveMaster.getLeavemasterIds()){
							LeaveMaster leaveMaster2 = leavMasterDao.findByPrimaryKey(k);
							//Set escalation action
							reqEscalation.setEscalationAction(leaveMaster2.getEsrMapId(), userId);
							EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(leaveMaster2.getEsrMapId());
							ServiceReqInfo[] serviceReqInfo = serviceReqInfoDao.findWhereEsrMapIdEquals(leaveMaster2.getEsrMapId());
							String status = setStatusAccToActionType(leaveMaster);
							if (!status.equals(Status.COMPLETED) && (serviceReqInfo == null || serviceReqInfo.length == 0 || (serviceReqInfo[0].getStatus() + "").equals(Status.CANCELLED))){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.reqcancelled", empSerReqMap.getReqId()));
								continue;
							}
							if (leaveMaster2.getUuid() == null || leaveMaster2.getActionBy() > 0){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.reqid", empSerReqMap.getReqId()));
								continue;
							}
							if (!ModelUtiility.getInstance().isValidApprover(empSerReqMap.getId(), userId)){
								result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.reqid", empSerReqMap.getReqId()));
								continue;
							}
							LeaveMaster parentLeaveMaster = null;
							if (empSerReqMap.getReqId().endsWith("-CN")){
								cancell = true;
							}
							ProfileInfo approverprofileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(userId) })[0];
							ProfileInfo reqprofileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(empSerReqMap.getRequestorId()) })[0];
							Users reqUserDto = usersDao.findByPrimaryKey(new Integer(empSerReqMap.getRequestorId()));
							//find mail id of Hrspoc and hrspoc
							Users usersInfoHrspoc = usersDao.findByPrimaryKey(reqprofileInfo.getHrSpoc());
							ProfileInfo profileInfoHrspoc = profileInfoDao.findByPrimaryKey(usersInfoHrspoc.getProfileId());
							Users usersInfoRM = usersDao.findByPrimaryKey(reqprofileInfo.getReportingMgr());
							ProfileInfo profileInfoRM = profileInfoDao.findByPrimaryKey(usersInfoRM.getProfileId());
							ProcessChain processChainDto = processDao.findByDynamicWhere(" ID = (SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID = ?)", new Object[] { leaveMaster2.getEsrMapId() })[0];
							leaveMaster2.setActionBy(userId);
							String leaveType = leaveTypeDao.findByPrimaryKey(leaveMaster2.getLeaveType()).getLeaveType();
							leaveMaster2.setUuid(null);
							LeaveBalance leaBalance = leaveBalanceDao.findWhereUserIdEquals(empSerReqMap.getRequestorId());
							//sathi.reddy code
							LeaveLwp leaveLwp = new LeaveLwp();
							if (status.equals(Status.COMPLETED) || status.equals(Status.INPROGRESS)){
								leaveMaster2.setStatus(status);
								leaveMaster2.setApprovedDateTime(new Date());
								leaveMasterPk.setId(leaveMaster2.getId());
								leaveMaster2.setRemark(PortalUtility.removeExtraChars(leaveMaster.getRemark(), 250));
								leaveMaster2.setComment(PortalUtility.removeExtraChars(leaveMaster.getComment(), 250));
								if (status.equals(Status.COMPLETED)){
									ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
									ProcessChain proChain = processChainDao.findByPrimaryKey(empSerReqMap.getProcessChainId());
									handlers = new ProcessEvaluator().handlers(proChain.getHandler(), empSerReqMap.getRequestorId());
									Integer[] notifiers = new ProcessEvaluator().notifiers(proChain.getNotification(), empSerReqMap.getRequestorId());
									HashSet<Integer> ccids = new HashSet<Integer>();
									if (reqprofileInfo.getHrSpoc() > 2) ccids.add(reqprofileInfo.getHrSpoc());
									for (Integer id : handlers)
										ccids.add(id);
									for (Integer id : notifiers)
										ccids.add(id);
									if (leaveMaster2.getIslwp() > 0){
										if (!cancell){
											leaBalance.setLwp(leaBalance.getLwp() - leaveMaster2.getIslwp());
											leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() - leaveMaster2.getIslwp());
											leaBalance.setBalance(leaBalance.getLeaveAccumalated() - leaBalance.getLeavesTaken());
											leaveBalancePk.setId(leaBalance.getId());
									//		leaveBalanceDao.update(leaveBalancePk, leaBalance);
										}
										leaveMaster2.setIslwp(-leaveMaster2.getIslwp());
									}
									leaveMaster2.setId(0);
									float lwp = 0;
									if (cancell){
										parentLeaveMaster = leavMasterDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE ID=(SELECT MAX(ID) FROM LEAVE_MASTER WHERE ESR_MAP_ID = (SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_ID=? GROUP BY REQ_ID) AND STATUS='Accepted' GROUP BY ESR_MAP_ID)", new Object[] { empSerReqMap.getReqId() })[0];
										lwp = Math.abs(parentLeaveMaster.getIslwp() - leaveMaster2.getIslwp());
									}
									if (lwp == 0) lwp = Math.abs(leaveMaster2.getIslwp());
									ServiceReqInfo sri = serviceReqInfoDao.findWhereEsrMapIdEquals(leaveMaster2.getEsrMapId())[0];
									if (sri != null && sri.getStatus().equals(Status.ACCEPTED)){
										sri.setStatus(Status.COMPLETED);
								//		serviceReqInfoDao.update(new ServiceReqInfoPk(sri.getId()), sri);
									}
									ModelUtiility.getInstance().deleteInboxEntries(leaveMaster2.getEsrMapId());
									if (handlers != null && handlers.length > 0){
							//			portalMail = sendMailDetails("Pay " + (cancell ? "Credited" : "Deducted") + " for " + reqprofileInfo.getFirstName() + " on " + PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()), MailSettings.LEAVE_LWP_COMPLETED_USER_NOTIFY, reqUserDto, reqprofileInfo, approverprofileInfo, leaveMaster2, leaBalance, lwp, cancell ? Status.CANCELLED : Status.COMPLETED, profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( "
								//				+ (ccids.toString().replace("[", "").replace("]", "")) + "))"), null);
										
								// Sending Android Noitification		
										List<String> mailids=new ArrayList<String>();
										String message="Pay " + (cancell ? "Credited" : "Deducted") + " for " + reqprofileInfo.getFirstName() + " on " + PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date());
										mailids.add(portalMail.getRecipientMailId());
										if(portalMail.getAllReceipientcCMailId()!=null){
										for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
										sendAndroidLeaveNotification(message,mailids);
										
										
							//			String bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
								//		leaveMaster2.setMessageBody(bodyText);
						//				leaveMasterPk = leavMasterDao.insert(leaveMaster2);
										leaveMaster2.setId(leaveMasterPk.getId());
										//String handlerStatus = Status.getStatus(19);
										//inboxModel.sendToDockingStation(empSerReqMap.getId(), leaveMasterPk.getId(), "Leave Request is Completed ", handlerStatus);
										// stop all other handler for this task.
					//					leavMasterDao.dynamicUpdate("UPDATE LEAVE_MASTER SET UUID=NULL,STATUS=? WHERE ESR_MAP_ID=? AND STATUS=?", new Object[] { Status.COMPLETED, leaveMaster2.getEsrMapId(), Status.ASSIGNED });
							//			if (!cancell) leavMasterDao.dynamicUpdate("UPDATE LEAVE_MASTER SET ISLWP=? WHERE ESR_MAP_ID=? AND ISLWP=?", new Object[] { leaveMaster2.getIslwp(), leaveMaster2.getEsrMapId(), -leaveMaster2.getIslwp() });
									} 
									else
									{
										noHandlers = true;
										throw new Exception("No Handlers for this process");
									}
								}
							} else{
								leaveMaster2.setStatus(status);
								leaveMaster2.setApprovedDateTime(new Date());
								leaveMasterPk.setId(leaveMaster2.getId());
								leaveMaster2.setRemark(PortalUtility.removeExtraChars(leaveMaster.getRemark(), 250));
								leaveMaster2.setComment(PortalUtility.removeExtraChars(leaveMaster.getRemark(), 250));
								//if leave type is comp-off then check avilable comp-off leaves.
								if (!cancell && leaveMaster2.getLeaveType() == 3 && leaBalance.getCompOff() < leaveMaster2.getDuration()){
									result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.nocompoff"));
									continue;
								}
								mailSubject = "Diksha Lynx: Leave " + (leaveMaster2.getStatus().equals("Accepted") ? "approved" : leaveMaster2.getStatus()) + " for " + reqprofileInfo.getFirstName();
								leavMasterDao.update(leaveMasterPk, leaveMaster2);
								// update status in inbox.
								//leavMasterDao.dynamicUpdate("UPDATE INBOX SET STATUS=? WHERE ESR_MAP_ID=?", new Object[] { (status.equals(Status.ACCEPTED) ? Status.APPROVED : status), leaveMaster2.getEsrMapId() });
								ModelUtiility.getInstance().deleteInboxEntries(leaveMaster2.getEsrMapId());
								leavMasterDao.dynamicUpdate("UPDATE LEAVE_MASTER SET UUID = NULL , STATUS=?, ACTION_BY=? WHERE UUID IS NOT NULL AND ESR_MAP_ID=?", new Object[] { status, userId, leaveMaster2.getEsrMapId() });
								Integer[] approverGroupIds = getAllSiblingApprover(processChainDto.getApprovalChain(), 1, userId, empSerReqMap.getRequestorId());
								if (approverGroupIds != null && approverGroupIds.length > 1){
									for (int ij = 0; ij < approverGroupIds.length; ij++)
										if (userId == approverGroupIds[ij]) approverGroupIds[ij] = -1;
									mailSubject = "Diksha Lynx: "+ approverprofileInfo.getFirstName() + " has " + (leaveMaster2.getStatus().equals("Accepted") ? "approved" : leaveMaster2.getStatus()) + " leave request for " + reqprofileInfo.getFirstName();
								 portalMail=sendMailDetails(mailSubject, MailSettings.LEAVE_STATUS_NOTIFY_FOR_SIBLING, reqUserDto, reqprofileInfo, approverprofileInfo, leaveMaster2, leaBalance, 0, (leaveMaster2.getStatus().equals("Accepted") ? Status.APPROVED : Status.REJECTED), profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN (" + Arrays.toString(approverGroupIds).replace("[", "").replace("]", "") + "))"), null);
								// Sending Android Noitification		
									List<String> mailids=new ArrayList<String>();
									mailids.add(portalMail.getRecipientMailId());
									if(portalMail.getAllReceipientcCMailId()!=null){
									for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
									sendAndroidLeaveNotification(mailSubject,mailids);
								} else{
									ServiceReqInfo[] serviceReqInfoarr = serviceReqInfoDao.findWhereEsrMapIdEquals(leaveMaster2.getEsrMapId());
									serviceReqInfoarr[0].setHdComments(leaveMaster2.getComment());
									serviceReqInfoPk.setId(serviceReqInfoarr[0].getId());
									serviceReqInfoarr[0].setStatus(com.dikshatech.common.utils.Status.PENDINGAPPROVAL);
									serviceReqInfoDao.update(serviceReqInfoPk, serviceReqInfoarr[0]);
								}
								Integer nextLevel = getAllNextApproverGroup(processChainDto.getApprovalChain(), 1, userId, empSerReqMap.getRequestorId());
								nextLevel = (nextLevel == null ? 1 : nextLevel);
								Integer[] nextApproverGroupIds = evaluator.approvers(processChainDto.getApprovalChain(), nextLevel, empSerReqMap.getRequestorId());
								if ((status.equalsIgnoreCase(com.dikshatech.common.utils.Status.ACCEPTED)) && nextApproverGroupIds != null && nextApproverGroupIds.length > 0){
									ModelUtiility.getInstance().updateSiblings(nextApproverGroupIds, empSerReqMap.getId());
									for (int j : nextApproverGroupIds){
										leaveMaster2.setStatus(com.dikshatech.common.utils.Status.REQUESTRAISED);
										leaveMaster2.setServed(new Short("1"));
										leaveMaster2.setAssignedTo(j);
										leaveMaster2.setAppliedDateTime(new Date());
										ProfileInfo appProfile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(j) })[0];
										UUID uuid = UUID.randomUUID();
										leaveMaster2.setUuid(uuid.toString());
										leaveMaster2.setActionByNull(true);
										leaveMaster2.setId(0);
										leaveMaster2.setApprovedDateTime(null);
										leaveMasterPk = leavMasterDao.insert(leaveMaster2);
										String acceptLink = "validate?uuid=" + leaveMaster2.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=1" + "&type=leave" + "&assignedTo=" + leaveMaster2.getAssignedTo();
										String rejectLink = "pages/leaveReject.jsp?uuid=" + leaveMaster2.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=2" + "&type=leave" + "&assignedTo=" + leaveMaster2.getAssignedTo();
										leaveMaster2.setAcceptLink(acceptLink);
										leaveMaster2.setRejectLink(rejectLink);
										mailSubject = "Diksha Lynx: New leave " + (cancell ? "cancel" : "") + " request - " + reqprofileInfo.getFirstName() + " from " + PortalUtility.getdd_MM_yyyy(leaveMaster2.getFromDate()) + " to " + PortalUtility.getdd_MM_yyyy(leaveMaster2.getToDate());
										portalMail = sendMailDetails(mailSubject, MailSettings.LEAVE_RECEIVE, reqUserDto, reqprofileInfo, appProfile, leaveMaster2, leaBalance, 0, Status.SUBMITTED, null, request);
										// Sending Android Noitification		
										List<String> mailids=new ArrayList<String>();
										mailids.add(portalMail.getRecipientMailId());
										if(portalMail.getAllReceipientcCMailId()!=null){
										for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
										sendAndroidLeaveNotification(mailSubject,mailids);
										
										String bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
										leaveMaster2.setMessageBody(bodyText);
										leaveMaster2.setActionBy(0);
										leaveMaster2.setComment((null));
										leavMasterDao.update(leaveMasterPk, leaveMaster2);
										/*Inbox inbox = */inboxModel.sendToDockingStation(leaveMaster2.getEsrMapId(), leaveMasterPk.getId(), portalMail.getMailSubject(), com.dikshatech.common.utils.Status.REQUESTRAISED);
										//inboxModel.notify(leaveMaster2.getEsrMapId(), inbox);
									}
									ServiceReqInfo[] serviceReqInfoarr = serviceReqInfoDao.findWhereEsrMapIdEquals(leaveMaster2.getEsrMapId());
									serviceReqInfoarr[0].setHdComments(leaveMaster2.getComment());
									serviceReqInfoPk.setId(serviceReqInfoarr[0].getId());
									serviceReqInfoarr[0].setStatus(com.dikshatech.common.utils.Status.PENDINGAPPROVAL);
									serviceReqInfoDao.update(serviceReqInfoPk, serviceReqInfoarr[0]);
								} else{
									ServiceReqInfo[] serviceReqInfoarr = serviceReqInfoDao.findWhereEsrMapIdEquals(leaveMaster2.getEsrMapId());
									ModelUtiility.getInstance().deleteInboxEntries(leaveMaster2.getEsrMapId());
									serviceReqInfoarr[0].setHdComments(leaveMaster2.getComment());
									serviceReqInfoPk.setId(serviceReqInfoarr[0].getId());
									if (cancell) parentLeaveMaster = leavMasterDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE ID=(SELECT MAX(ID) FROM LEAVE_MASTER WHERE ESR_MAP_ID = (SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_ID=?  AND REQUESTOR_ID=? GROUP BY REQ_ID) GROUP BY ESR_MAP_ID)", new Object[] { empSerReqMap.getReqId().replace("-CN", ""), empSerReqMap.getRequestorId() })[0];
									if (status.equals(com.dikshatech.common.utils.Status.ACCEPTED)){
										serviceReqInfoarr[0].setStatus(com.dikshatech.common.utils.Status.ACCEPTED);
										calculate = true;
									} else if (status.equals(com.dikshatech.common.utils.Status.REJECTED)){
										serviceReqInfoarr[0].setStatus(com.dikshatech.common.utils.Status.REJECTED);
										mailSubject = "Diksha Lynx: Leave rejected by " + approverprofileInfo.getFirstName();
										portalMail = sendMailDetails(mailSubject, MailSettings.LEAVE_NOTIFY_USER, reqUserDto, reqprofileInfo, approverprofileInfo, leaveMaster2, leaBalance, 0, Status.REJECTED, new String[] { profileInfoRM.getOfficalEmailId(), profileInfoHrspoc.getOfficalEmailId() }, null);
										
										// Sending Android Noitification		
										List<String> mailids=new ArrayList<String>();
										mailids.add(portalMail.getRecipientMailId());
										if(portalMail.getAllReceipientcCMailId()!=null){
										for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
										sendAndroidLeaveNotification(mailSubject,mailids);
										
										String bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
										leaveMaster2.setMessageBody(bodyText);
									}
									if (cancell){
										parentLeaveMaster.setToCancell(0);
										leaveMaster2.setToCancell(1);
										leavMasterDao.update(new LeaveMasterPk(parentLeaveMaster.getId()), parentLeaveMaster);
										leavMasterDao.update(leaveMasterPk, leaveMaster2);
									}
									serviceReqInfoDao.update(serviceReqInfoPk, serviceReqInfoarr[0]);
								}
								if (calculate){
									/*********************************** LEAVE BALANCE TABLE POPULATE ************************************/
									LeaveHistroy leaveHistroy = null;
									if (cancell) leaveHistroy = leaveHistroyDao.findWhereEsrMapIdEquals(parentLeaveMaster.getEsrMapId());
									else leaveHistroy = leaveHistroyDao.findWhereEsrMapIdEquals(leaveMaster2.getEsrMapId());
									float quota;
									float spill = 0;
									boolean withoutpay = false/*,flag=false*/;
									float existingDuration = 0;
									if (leaveHistroy != null){
										existingDuration = leaveHistroy.getCurrentDuration();
									}
									float newDuration = leaveMaster2.getDuration();
									float resetDuration = existingDuration - newDuration;
									if (leaBalance == null){
										leaBalance = new LeaveBalance();
										LeaveBalanceDao balanceDao = LeaveBalanceDaoFactory.create();
										leaBalance.setUserId(empSerReqMap.getRequestorId());
										LeaveBalancePk pk = balanceDao.insert(leaBalance);
										leaBalance.setId(pk.getId());
									}
									if (!cancell){
										// checkleave type
										if (leaveMaster2.getLeaveType() != 2 && leaveMaster2.getLeaveType() != 3){
											quota = getQuota(leaveMaster2, leaBalance);
											logger.info("Leave Quota :" + quota);
											if (leaveMaster2.getDuration() > quota){
												spill = Math.abs(quota - leaveMaster2.getDuration());
												logger.info("SPILL :" + spill);
												if (leaBalance.getBalance() < spill){
													withoutpay = true;
													float lwp = 0;
													if (leaBalance.getBalance() <= 0) lwp = spill;
													else lwp = ((spill - leaBalance.getBalance()));
													leaBalance.setLwp((leaBalance.getLwp() + lwp));
													leaveMaster2.setIslwp(lwp);
												}
												leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() + spill);
												leaBalance.setBalance(leaBalance.getBalance() - spill);
												leaveHistroy.setCurrentQuotaBalance(quota);
												setQuota(leaveMaster2, leaBalance, 0);
												leaveBalancePk.setId(leaBalance.getId());
												leaveBalanceDao.update(leaveBalancePk, leaBalance);
												
												
												// * sathi.reddy code start
												 
												if((leaBalance.getLeaveAccumalated()-leaBalance.getLeavesTaken())< 0){
												DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
											    String approvedDate = df.format(leaveMaster2.getApprovedDateTime());
												//Insert code for LeaveLwp
												String cycle =null;
												String monthCycle = null;
												String dateSplit[] = approvedDate.split("-");
												int day = Integer.parseInt(dateSplit[0]);
												if(day <= 12)
													cycle="1";
												else if(day > 12 && day <=25)
													cycle = "2";
												else
													cycle = "3";
												monthCycle = dateSplit[2]+"-"+dateSplit[1]+"-"+cycle;
												
												LeaveLwp record = leaveLwpDao.findWhereUserIdEquals(leaBalance.getUserId(),monthCycle);
											    if(record != null){
											        leaveLwpPk.setId(record.getId());
											        leaveLwp.setId(record.getId());
											        leaveLwp.setUserId(record.getUserId());
											    	leaveLwp.setMonthCycle(record.getMonthCycle());
											    	leaveLwp.setLwp(record.getLwp()+existingDuration);
											    	leaveLwpDao.update(leaveLwpPk, leaveLwp);
											    	
											    }else{
											    	leaveLwp.setUserId(leaBalance.getUserId());
											    	leaveLwp.setMonthCycle(monthCycle);
											    	leaveLwp.setLwp(Math.abs(leaBalance.getLeaveAccumalated()-leaBalance.getLeavesTaken()));
													leaveLwpDao.insert(leaveLwp);
											    }
												}
											
												/*
												 * sathi.reddy code stops
												 */
												
												
											} else{
												quota = quota - leaveMaster2.getDuration();
												setQuota(leaveMaster2, leaBalance, quota);
												leaveBalancePk.setId(leaBalance.getId());
												leaveBalanceDao.update(leaveBalancePk, leaBalance);
												leaveHistroy.setCurrentQuotaBalance(leaveMaster2.getDuration());
											}
										} else{
											if (leaveMaster2.getLeaveType() == 2){
												leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() + leaveMaster2.getDuration());
												leaveBalancePk.setId(leaBalance.getId());
												if (leaBalance.getBalance() < 0){
													withoutpay = true;
													leaBalance.setLwp((leaBalance.getLwp() + leaveMaster2.getDuration()));
													leaveMaster2.setIslwp(leaveMaster2.getDuration());
												} else if (leaBalance.getBalance() < leaveMaster2.getDuration()){
													withoutpay = true;
													leaBalance.setLwp((leaBalance.getLwp() + leaveMaster2.getDuration() - leaBalance.getBalance()));
													leaveMaster2.setIslwp((leaveMaster2.getDuration() - leaBalance.getBalance()));
												}
												leaBalance.setBalance(leaBalance.getBalance() - leaveMaster2.getDuration());
												LeaveMasterPk leaveMpk = new LeaveMasterPk();
												leaveMpk.setId(leaveMaster2.getId());
												leavMasterDao.update(leaveMpk, leaveMaster2);
											} else if (leaveMaster2.getLeaveType() == 3){
												leaBalance.setCompOff(leaBalance.getCompOff() - leaveMaster2.getDuration());
												leaveBalancePk.setId(leaBalance.getId());
											}
										}
										/**
										 * update Leave history with new current duration
										 */
										LeaveHistroyPk leaveHistroyPk = new LeaveHistroyPk();
										if (leaveHistroy != null){
											leaveHistroyPk.setId(leaveHistroy.getId());
											leaveHistroy.setCurrentDuration(leaveMaster2.getDuration());
											leaveHistroyDao.update(leaveHistroyPk, leaveHistroy);
										}
										// final update statement
										leaveBalancePk.setId(leaBalance.getId());
										leaveBalanceDao.update(leaveBalancePk, leaBalance);
										if (withoutpay){
											/***
											 * send Notification to inbox for
											 * finance departmaent
											 * current accumalated is the without
											 * pay:
											 */
											noHandlers = notifyHandlers(empSerReqMap, leaveMaster2, leaBalance, reqUserDto, profileInfoDao, reqprofileInfo, inboxModel, leavMasterDao, leaveType, 0, false);
										}
									} else{
										/*
										 * Author : sathi.reddy
										 * purpose: update leaveLwp table when cancel request raised
										 */
										if((leaBalance.getLeaveAccumalated()-leaBalance.getLeavesTaken())< 0){
											DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
										    String approvedDate = df.format(leaveMaster2.getApprovedDateTime());
											//Insert code for LeaveLwp
											String cycle =null;
											String monthCycle = null;
											String dateSplit[] = approvedDate.split("-");
											int day = Integer.parseInt(dateSplit[0]);
											if(day <= 12)
												cycle="1";
											else if(day > 12 && day <=25)
												cycle = "2";
											else
												cycle = "3";
											monthCycle = dateSplit[2]+"-"+dateSplit[1]+"-"+cycle;
											
											LeaveLwp record = leaveLwpDao.findWhereUserIdEquals(leaBalance.getUserId(),monthCycle);
										    if(record != null){
										        leaveLwpPk.setId(record.getId());
										        leaveLwp.setId(record.getId());
										        leaveLwp.setUserId(record.getUserId());
										    	leaveLwp.setMonthCycle(record.getMonthCycle());
										    	float duration=0;
										    	if(resetDuration==0)
										    		duration = existingDuration;
										    	else
										    		duration = resetDuration;
										    	if(record.getLwp()-duration <= 0){
										    		leaveLwp.setLwp(0);
											    	leaveLwpDao.delete(leaveLwpPk);
										    	}
										    	else{
										    		leaveLwp.setLwp(record.getLwp()-duration);
											    	leaveLwpDao.update(leaveLwpPk, leaveLwp);
										    	}
										    	
										    }
										}
											
										
										
										/*
										 * Author:sathi.reddy
										 * code end
										 */
										if (leaveHistroy.getCurrentDuration() == 0){
											result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.cannotcancel"));
											break;
										}
										if (resetDuration == 0){
											parentLeaveMaster.setToCancell(1);
											parentLeaveMaster.setStatus(Status.CANCELLED);
											ServiceReqInfo[] serviceReqInfoarr = serviceReqInfoDao.findWhereEsrMapIdEquals(parentLeaveMaster.getEsrMapId());
											serviceReqInfoarr[0].setHdComments(parentLeaveMaster.getComment());
											serviceReqInfoPk.setId(serviceReqInfoarr[0].getId());
											serviceReqInfoarr[0].setStatus(Status.CANCELLED);
											serviceReqInfoDao.update(serviceReqInfoPk, serviceReqInfoarr[0]);
										}
										float lwp = leaveMaster2.getIslwp();
										if (leaveMaster2.getLeaveType() != 3){
											if (resetDuration == 0){
												if (lwp != 0){
													if (lwp < 0){// finace dep taken action.
														noHandlers = notifyHandlers(empSerReqMap, leaveMaster2, leaBalance, reqUserDto, profileInfoDao, reqprofileInfo, inboxModel, leavMasterDao, leaveType, Math.abs(lwp), false);
													} else if (lwp > 0){
														// stop the handlers to take action.
														leavMasterDao.dynamicUpdate("UPDATE LEAVE_MASTER SET UUID=NULL,STATUS=? WHERE ESR_MAP_ID=? AND STATUS=?", new Object[] { Status.REVOKED, leaveMaster2.getEsrMapId(), Status.ASSIGNED });
														leaveMaster2.setIslwp(0);
														leaBalance.setLwp((leaBalance.getLwp() - lwp) <= 0 ? 0 : (leaBalance.getLwp() - lwp));
														noHandlers = notifyHandlers(empSerReqMap, leaveMaster2, leaBalance, reqUserDto, profileInfoDao, reqprofileInfo, inboxModel, leavMasterDao, leaveType, Math.abs(lwp), true);
														lwp = 0;
													}
												}
												leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() - (leaveHistroy.getCurrentDuration() - leaveHistroy.getCurrentQuotaBalance() - Math.abs(lwp)));
												if (leaveMaster2.getLeaveType() != 2) setQuota(leaveMaster2, leaBalance, getQuota(leaveMaster2, leaBalance) + leaveHistroy.getCurrentQuotaBalance());
												leaveHistroy.setCurrentDuration(0);
												leaveHistroy.setCurrentQuotaBalance(0);
											} else{
												float resetDuration1 = resetDuration;
												if (lwp != 0){
													if (lwp < 0){// finace dep taken action.
														if (Math.abs(lwp) < resetDuration1){
															leaveMaster2.setIslwp(Math.abs(lwp));
															resetDuration1 -= Math.abs(lwp);
															noHandlers = notifyHandlers(empSerReqMap, leaveMaster2, leaBalance, reqUserDto, profileInfoDao, reqprofileInfo, inboxModel, leavMasterDao, leaveType, Math.abs(lwp), false);
														} else if (resetDuration1 <= Math.abs(lwp)){
															leaveMaster2.setIslwp(-(Math.abs(lwp) - resetDuration1));
															noHandlers = notifyHandlers(empSerReqMap, leaveMaster2, leaBalance, reqUserDto, profileInfoDao, reqprofileInfo, inboxModel, leavMasterDao, leaveType, resetDuration1, false);
															resetDuration1 = 0;
														}
													} else if (lwp > 0){
														if (lwp < resetDuration1){
															leaveMaster2.setIslwp(0);
															leaBalance.setLwp(0);
															resetDuration1 -= lwp;
															noHandlers = notifyHandlers(empSerReqMap, leaveMaster2, leaBalance, reqUserDto, profileInfoDao, reqprofileInfo, inboxModel, leavMasterDao, leaveType, Math.abs(lwp), true);
															leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() - Math.abs(lwp));
														} else if (resetDuration1 <= lwp){
															leaveMaster2.setIslwp((lwp - resetDuration1));
															leaBalance.setLwp(lwp - resetDuration1);
															leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() - resetDuration1);
															noHandlers = notifyHandlers(empSerReqMap, leaveMaster2, leaBalance, reqUserDto, profileInfoDao, reqprofileInfo, inboxModel, leavMasterDao, leaveType, resetDuration1, true);
															resetDuration1 = 0;
														}
													}
												}
												float accLeaves = existingDuration - leaveHistroy.getCurrentQuotaBalance() - Math.abs(lwp);
												if (accLeaves > 0 && resetDuration1 > 0){
													if (accLeaves > resetDuration1){
														leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() - resetDuration1);
														resetDuration1 = 0;
													} else{
														leaBalance.setLeavesTaken(leaBalance.getLeavesTaken() - accLeaves);
														resetDuration1 -= accLeaves;
													}
												}
												if (resetDuration1 > 0 && leaveHistroy.getCurrentQuotaBalance() > 0){
													if (leaveHistroy.getCurrentQuotaBalance() > resetDuration1){
														setQuota(leaveMaster2, leaBalance, leaveHistroy.getCurrentQuotaBalance() - resetDuration1);
														leaveHistroy.setCurrentQuotaBalance(leaveHistroy.getCurrentQuotaBalance() - resetDuration1);
														resetDuration1 = 0;
													} else{
														setQuota(leaveMaster2, leaBalance, -leaveHistroy.getCurrentQuotaBalance() + resetDuration1);
														leaveHistroy.setCurrentQuotaBalance(0);
														resetDuration1 -= leaveHistroy.getCurrentQuotaBalance();
													}
												}
												leaveHistroy.setCurrentDuration(newDuration);
											}
											leaBalance.setBalance(leaBalance.getLeaveAccumalated() - leaBalance.getLeavesTaken());
										} else if (leaveMaster2.getLeaveType() == 3){
											leaBalance.setCompOff(leaBalance.getCompOff() + resetDuration == 0 ? leaveMaster2.getDuration() : resetDuration);
										}
										leaveMaster2.setId(0);
										leaveMaster2.setUuid(null);
										leaveMaster2.setAssignedTo(userId);
										leaveMaster2.setToCancell(parentLeaveMaster.getToCancell());
										leaveMaster2.setStatus(Status.ACCEPTED);
										leaveMaster2.setEsrMapId(parentLeaveMaster.getEsrMapId());
										leavMasterDao.insert(leaveMaster2);
									}// else for cancell
										// final update statement
									leaveBalancePk.setId(leaBalance.getId());
									leaveBalanceDao.update(leaveBalancePk, leaBalance);
									
									
									/**
									 * update Leave history with new current duratiion
									 */
									LeaveHistroyPk leaveHistroyPk = new LeaveHistroyPk();
									leaveHistroyPk.setId(leaveHistroy.getId());
									leaveHistroyDao.update(leaveHistroyPk, leaveHistroy);
									ProfileInfo appProfile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(userId) })[0];
									mailSubject = "Diksha Lynx: Your leave " + (cancell ? "cancel " : "") + "request has been approved ";
									PortalMail pmail = sendMailDetails("Leave " + (cancell ? "cancel " : "") + "request accepted by " + appProfile.getFirstName(), MailSettings.LEAVE_NOTIFY_USER, reqUserDto, reqprofileInfo, appProfile, leaveMaster2, leaBalance, 0, Status.APPROVED, new String[] { profileInfoRM.getOfficalEmailId(), profileInfoHrspoc.getOfficalEmailId() }, null);
									// Sending Android Noitification		
									List<String> mailids=new ArrayList<String>();
									String message="Leave " + (cancell ? "cancel " : "") + "request accepted by " + appProfile.getFirstName();
									mailids.add(pmail.getRecipientMailId());
									if(portalMail.getAllReceipientcCMailId()!=null){
									for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
									sendAndroidLeaveNotification(message,mailids);
									
									String messageBody = (mailGenerator.replaceFields(mailGenerator.getMailTemplate(pmail.getTemplateName()), pmail));
									JDBCUtiility.getInstance().update("UPDATE LEAVE_MASTER SET MESSAGE_BODY=? WHERE ID=?", new Object[] { messageBody, leaveMasterPk.getId() });
								}
							}
						}//else for handle ends
					}// is alredy accepted if ends
					else{
						logger.info("Cannot reject approved Leave:");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					}
				} catch (Exception e){
					if (noHandlers){
						logger.info("No handlers for this process:");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nohandler"));
					} else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.reimburse.action.failed"));
					e.printStackTrace();
				}
				break;
			}
			case EDIT:{
				try{
					LeaveMaster leaveMasterDto = (LeaveMaster) form;
					if (leaveMasterDto.getDuration() * 10 % 5 != 0){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
						break;
					}
					Login login = Login.getLogin(request);
					LeaveBalance leaveBalance = leaveBalanceDao.findWhereUserIdEquals(login.getUserId());
					if (leaveBalance != null){
						if (leaveMasterDto.getLeaveType() == 3){
							if (leaveBalance.getCompOff() == 0){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.nocompoff"));
								break;
							} else if (leaveBalance.getCompOff() < leaveMasterDto.getDuration()){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notenoughcompoff", leaveBalance.getCompOff()));
								break;
							}
						}
					}
					ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
					ProcessChain processChainDto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=22  WHERE UR.USER_ID=?", new Object[] { new Integer(login.getUserId()) })[0];
					ServiceReqInfoDao serviceReqInfoDao2 = ServiceReqInfoDaoFactory.create();
					ServiceReqInfo serviceReqInfo[] = serviceReqInfoDao2.findWhereEsrMapIdEquals(leaveMasterDto.getEsrMapId());
					ProjectDao projectDao = ProjectDaoFactory.create();
					ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create();
					LeaveMasterDao leaveMasterDao = LeaveMasterDaoFactory.create();
					String status_to_edit = serviceReqInfo[0].getStatus();
					EmpSerReqMap empMapDto = empSerReqMapDao.findWhereIdEquals(leaveMasterDto.getEsrMapId())[0];
					String mailSubject = "";
					/*String leaveType = "";*/
					ProfileInfo reqProfile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(empMapDto.getRequestorId()) })[0];
					Users userdto = UsersDaoFactory.create().findByPrimaryKey(empMapDto.getRequestorId());
					if (status_to_edit.equals(com.dikshatech.common.utils.Status.REQUESTRAISED)){
						LeaveHistroy leaveHistroy = leaveHistroyDao.findWhereEsrMapIdEquals(leaveMasterDto.getEsrMapId());
						// if cancel request
						if (leaveHistroy == null && empMapDto.getReqId().endsWith("CN")){
							LeaveMaster master = leaveMasterDao.findByPrimaryKey(leaveMasterDto.getId());
							master.setFromDate(leaveMasterDto.getFromDate());
							master.setToDate(leaveMasterDto.getToDate());
							master.setDuration(leaveMasterDto.getDuration());
							leaveMasterDao.update(new LeaveMasterPk(master.getId()), master);
							break;
						}
						LeaveHistroyPk leaveHistroyPk = new LeaveHistroyPk();
						leaveHistroyPk.setId(leaveHistroy.getId());
						int appLevel = 1;
						ProcessEvaluator pEvaluator = new ProcessEvaluator();
						Integer[] approverGroupIds = pEvaluator.approvers(processChainDto.getApprovalChain(), appLevel, login.getUserId());
						if (approverGroupIds != null && approverGroupIds.length > 0) for (int j : approverGroupIds){
							leaveMasterDto.setAssignedTo(j);
							leaveMasterDto.setStatus(com.dikshatech.common.utils.Status.REQUESTRAISED);
							leaveMasterDto.setAppliedDateTime(new Date());
							DocumentsPk documentsPk = new DocumentsPk();
							if (leaveMasterDto.getAttachment().equals("0") || leaveMasterDto.getAttachment() == ""){
								leaveMasterDto.setAttachment(null);
							} else{
								DocumentsDao documentsDao = DocumentsDaoFactory.create();
								LeaveMaster oldRow = leaveMasterDao.findByPrimaryKey(leaveMasterDto.getId());
								Documents documents = documentsDao.findByPrimaryKey(Integer.parseInt(oldRow.getAttachment()));
								documentsPk.setId(documents.getId());
								documentsDao.delete(documentsPk);
							}
							if (leaveMasterDto.getLeaveProject() != null || leaveMasterDto.getLeaveProject() != "" || leaveMasterDto.getLeaveProject() != "0"){
								String fieldsArray[] = leaveMasterDto.getLeaveProject().split(",");
								if (fieldsArray.length > 0){
									if (!fieldsArray[0].equals("0") || !fieldsArray[0].equals("")){
										Project project = projectDao.findWhereIdEquals(Integer.parseInt(fieldsArray[0]))[0];
										leaveMasterDto.setProjectName(project.getName());
									}
									if (!fieldsArray[1].equals("0") || !fieldsArray[1].equals("")){
										ChargeCode chargecode = chargeCodeDao.findWhereIdEquals(Integer.parseInt(fieldsArray[1]))[0];
										leaveMasterDto.setProjectTitle(chargecode.getChCodeName());
									}
								}
							}
							leaveMasterDto.setServed(new Short("1"));
							leaveMasterDto.setId(leaveMasterDto.getId());
							LeaveMasterPk leaveMasterPk = new LeaveMasterPk();
							leaveMasterPk.setId(leaveMasterDto.getId());
							mailSubject = "RE: Diksha Lynx: New leave request - " + reqProfile.getFirstName() + " from " + PortalUtility.getdd_MM_yyyy(leaveMasterDto.getFromDate()) + " to " + PortalUtility.getdd_MM_yyyy(leaveMasterDto.getToDate());
							leaveMasterDto.setUuid(PortalUtility.getUniqueID());
							String acceptLink = "validate?uuid=" + leaveMasterDto.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=1" + "&type=leave" + "&assignedTo=" + leaveMasterDto.getAssignedTo();
							String rejectLink = "pages/leaveReject.jsp?uuid=" + leaveMasterDto.getUuid() + "&cId=" + leaveMasterPk.getId() + "&sId=2" + "&type=leave" + "&assignedTo=" + leaveMasterDto.getAssignedTo();
							leaveMasterDto.setAcceptLink(acceptLink);
							leaveMasterDto.setRejectLink(rejectLink);
							ProfileInfo appProfile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(j) })[0];
							PortalMail portalMail = sendMailDetails(mailSubject, MailSettings.LEAVE_RECEIVE, userdto, reqProfile, appProfile, leaveMasterDto, leaveBalance, 0, Status.SUBMITTED, null, request);
							
						// Sending Android Noitification		
							List<String> mailids=new ArrayList<String>();
							mailids.add(portalMail.getRecipientMailId());
							if(portalMail.getAllReceipientcCMailId()!=null){
							for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
							sendAndroidLeaveNotification(mailSubject,mailids);
							
							leaveMasterDto.setMessageBody(mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail));
							leaveMasterDao.update(leaveMasterPk, leaveMasterDto);
							InboxDao inboxDao = InboxDaoFactory.create();
							Inbox inbox[] = inboxDao.findByDynamicWhere("ESR_MAP_ID =? AND ASSIGNED_TO = ?", new Object[] { leaveMasterDto.getEsrMapId(), j });
							for (Inbox inboxSingle : inbox){
								InboxPk inboxPk = new InboxPk();
								inboxSingle = updateInboxSetter(inboxSingle, leaveMasterDto, mailSubject);
								inboxPk.setId(inboxSingle.getId());
								inboxDao.update(inboxPk, inboxSingle);
							}
						}//for loop for approver id's
						serviceReqInfo = serviceReqInfoDao.findWhereEsrMapIdEquals(leaveMasterDto.getEsrMapId());
						serviceReqInfo[0].setEsrMapId(leaveMasterDto.getEsrMapId());
						serviceReqInfo[0].setStatus(com.dikshatech.common.utils.Status.REQUESTRAISED);
						serviceReqInfo[0].setCreationDatetime(leaveMasterDto.getAppliedDateTime());
						serviceReqInfoPk.setId(serviceReqInfo[0].getId());
						serviceReqInfoDao.update(serviceReqInfoPk, serviceReqInfo[0]);
						leaveHistroy.setCurrentDuration(leaveMasterDto.getDuration());
						leaveHistroyDao.update(leaveHistroyPk, leaveHistroy);
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
				break;
			default:
				break;
		}
		return result;
	}

	/**
	 * @author gurunath.rokkam
	 *         Sends the mail to handlers about leave with out pay details.
	 * @param b
	 * @throws Exception
	 */
	private boolean notifyHandlers(EmpSerReqMap empSerReqMap, LeaveMaster leaveMaster2, LeaveBalance leaBalance, Users userDto, ProfileInfoDao profileInfoDao, ProfileInfo reqprofileInfo, InboxModel inboxModel, LeaveMasterDao leavMasterDao, String leaveType, float lwp, boolean isNotification) throws Exception {
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		MailGenerator mailGenerator2 = new MailGenerator();
		ProcessChain proChain = processChainDao.findByPrimaryKey(empSerReqMap.getProcessChainId());
		Integer[] handlers = new ProcessEvaluator().handlers(proChain.getHandler(), empSerReqMap.getRequestorId());
		Integer[] notifiers = new ProcessEvaluator().notifiers(proChain.getNotification(), empSerReqMap.getRequestorId());
		HashSet<Integer> ccids = new HashSet<Integer>();
		if (reqprofileInfo.getHrSpoc() > 2) ccids.add(reqprofileInfo.getHrSpoc());
		/*for (Integer id : handlers)
			ccids.add(id);*/
		for (Integer id : notifiers)
			ccids.add(id);
		ccids.add(empSerReqMap.getRequestorId());
		ModelUtiility.getInstance().updateSiblings(handlers, empSerReqMap.getId());
		if ((handlers != null && handlers.length > 0) || (notifiers != null && notifiers.length > 0)){
			String[] ccMailIds = null, toMailIds = null;
			if (ccids != null && ccids.size() > 0){
				ccMailIds = profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ModelUtiility.getCommaSeparetedValues(ccids) + "))");
			}
			if (handlers != null && handlers.length > 0){
				toMailIds = profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ModelUtiility.getCommaSeparetedValues(handlers) + "))");
			}
			PortalMail portalMail = sendMailDetails("Leave without pay details for " + reqprofileInfo.getFirstName(), MailSettings.LEAVE_LWP_USER_NOTIFY, userDto, reqprofileInfo, null, leaveMaster2, leaBalance, (lwp == 0) ? Math.abs(leaveMaster2.getIslwp()) : Math.abs(lwp), lwp <= 0 ? Status.SUBMITTED : Status.CANCELLED, ccMailIds, null, toMailIds);
			String bodyText = mailGenerator2.replaceFields(mailGenerator2.getMailTemplate(portalMail.getTemplateName()), portalMail);
			if (!isNotification){
				leaveMaster2.setMessageBody(bodyText);
				leaveMaster2.setActionByNull(true);
				leaveMaster2.setComment(null);
				leaveMaster2.setStatus(Status.getStatus(18));
				for (int j = 0; j < handlers.length; j++){
					leaveMaster2.setId(0);
					leaveMaster2.setAssignedTo(handlers[j]);
					leaveMaster2.setUuid(PortalUtility.getUniqueID());
					LeaveMasterPk leaveMasterPk = leavMasterDao.insert(leaveMaster2);
					inboxModel.sendToDockingStation(empSerReqMap.getId(), leaveMasterPk.getId(), "Leave without pay details for " + reqprofileInfo.getFirstName(), Status.getStatus(18));
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * @author gurunath.rokkam
	 *         sets the employee position details.
	 * @param portalMail
	 * @param userDto
	 * @param profileInfoDto
	 */
	private void setEmployeeDetails(PortalMail portalMail, Users userDto, ProfileInfo profileInfoDto) {
		try{
			LevelsDao levelsdao = LevelsDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			Levels level = levelsdao.findByPrimaryKey(profileInfoDto.getLevelId());
			Divison division = divisionDao.findByPrimaryKey(level.getDivisionId());
			portalMail.setEmpDesignation(level.getDesignation());
			portalMail.setEmpFname(profileInfoDto.getFirstName());
			portalMail.setEmpLName(profileInfoDto.getLastName());
			portalMail.setEmpId(userDto.getEmpId() + "");
			portalMail.setOfferedDepartment("N.A");
			portalMail.setEmpDivision("N.A");
			if (division != null){
				if (division.getParentId() != 0){
					portalMail.setEmpDivision(division.getName());
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) portalMail.setOfferedDepartment(division.getName());
				} else portalMail.setOfferedDepartment(division.getName());
			}
		} catch (Exception e){}
	}

	public Inbox updateInboxSetter(Inbox inboxSingle, LeaveMaster leaveMasterDto, String mailSubject) {
		String sql = "SELECT * FROM LEAVE_MASTER WHERE  ID=?";
		LeaveMasterDao leaveMasterDao = LeaveMasterDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		LeaveMaster[] leaveMaster3;
		try{
			leaveMaster3 = leaveMasterDao.findByDynamicSelect(sql, new Object[] { leaveMasterDto.getId() });
			EmpSerReqMap[] empSerReqMap = empSerReqMapDao.findWhereIdEquals(leaveMasterDto.getEsrMapId());
			inboxSingle.setSubject(mailSubject);
			inboxSingle.setAssignedTo(leaveMaster3[0].getAssignedTo());
			inboxSingle.setRaisedBy(empSerReqMap[0].getRequestorId());
			inboxSingle.setStatus(leaveMasterDto.getStatus());
			inboxSingle.setCreationDatetime(new Date());
			inboxSingle.setIsRead(0);
			inboxSingle.setIsDeleted(0);
			inboxSingle.setCategory("LEAVE");
			inboxSingle.setEsrMapId(leaveMasterDto.getEsrMapId());
			inboxSingle.setMessageBody(leaveMasterDto.getMessageBody());
			inboxSingle.setIsRead(0);
		} catch (LeaveMasterDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inboxSingle;
	}

	/**
	 * @author praveen.kumar
	 *         Set status depending on action type
	 * @params acton type
	 */
	public String setStatusAccToActionType(LeaveMaster leaveMaster) {
		String status = new String();
		if (leaveMaster.getIsActionType() == 1){
			status = Status.ACCEPTED;
		} else if (leaveMaster.getIsActionType() == 2){
			status = Status.REJECTED;
		} else if (leaveMaster.getIsActionType() == 3){
			status = Status.CANCELLED;
		} else if (leaveMaster.getIsActionType() == 4){
			status = Status.COMPLETEDWITHOUTLWP;
		} else if (leaveMaster.getIsActionType() == 5){
			status = Status.INPROGRESS;
		}
		return status;
	}

	/**
	 * @author praveen.kumar
	 *         type of leave
	 * @param procChain
	 * @param element
	 * @return
	 */
	public Float getQuota(LeaveMaster leaveMaster2, LeaveBalance leaBalance) {
		Float quota;
		if (leaveMaster2.getLeaveType() == 1){
			quota = leaBalance.getMarriage();
		} else if (leaveMaster2.getLeaveType() == 4){
			quota = leaBalance.getBereavement();
		} else if (leaveMaster2.getLeaveType() == 5){
			quota = leaBalance.getMaternity();
		} else{
			quota = leaBalance.getPaternity();
		}
		return quota;
	}

	public int getFixQuota(LeaveMaster leaveMaster2, LeaveBalance leaBalance) {
		int quota;
		if (leaveMaster2.getLeaveType() == 1){
			quota = 3;
		} else if (leaveMaster2.getLeaveType() == 4){
			quota = 3;
		} else if (leaveMaster2.getLeaveType() == 5){
			quota = 90;
		} else{
			quota = 3;
		}
		return quota;
	}

	public float setQuota(LeaveMaster leaveMaster2, LeaveBalance leaBalance, float quota) {
		if (leaveMaster2.getLeaveType() == 1){
			leaBalance.setMarriage(quota);
		} else if (leaveMaster2.getLeaveType() == 4){
			leaBalance.setBereavement(quota);
		} else if (leaveMaster2.getLeaveType() == 5){
			leaBalance.setMaternity(quota);
		} else{
			leaBalance.setPaternity(quota);
		}
		return quota;
	}

	public static List<String> getUsersByDivision(int id, HttpServletRequest request) {
		List<String> Mylist = new ArrayList<String>();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		String selectUsers = "SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE, U.EXPERIENCE_ID, U.SKILLSET_ID, U.OTHERS, U.ACTION_BY  FROM USERS U ";
		try{
			Users users[] = usersDao.findByDynamicSelect(selectUsers + "  LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID " + "LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT ID FROM " + "DIVISON WHERE ID = ? OR PARENT_ID = ?);", new Integer[] { id, id });
			if (users != null){
				for (Users user : users){
					ProfileInfo profile = profielInfoDao.findByPrimaryKey(user.getProfileId());
					Mylist.add(profile.getOfficalEmailId());
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mylist;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			Login login = Login.getLogin(request);
			int userid = login.getUserId();
			EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create(conn);
			EmpSerReqMap[] empSerReqMaps = empSerReqMapDao.findWhereRequestorIdEquals(userid);
			@SuppressWarnings("unused")
			LeaveBean[] leaveBeanArr = new LeaveBean[empSerReqMaps.length];
			LeaveMasterDao leaveMasterDao = LeaveMasterDaoFactory.create(conn);
			UsersDao usersDao = UsersDaoFactory.create(conn);
			switch (ReceiveTypes.getValue(form.getrType())) {
			// gives the list of my leave info based on the login
				case LEAVERECEIVE:{
					ServiceReqInfoDao serviceReqInfoDao = ServiceReqInfoDaoFactory.create(conn);
					List<Object> list = JDBCUtiility.getInstance().getSingleColumn("SELECT MAX(ID) FROM LEAVE_MASTER WHERE ESR_MAP_ID IN(SELECT ID FROM EMP_SER_REQ_MAP WHERE  REQUESTOR_ID=?) GROUP BY ESR_MAP_ID", new Object[] { new Integer(userid) });
					LeaveReceiveBean receiveBean = new LeaveReceiveBean();
					receiveBean.setToApprove(isApprover(login.getUserId()) + "");
					receiveBean.setToHandle(isHandler(login.getUserId()) + "");
					if (list == null || list.size() == 0){
						request.setAttribute("actionForm", receiveBean);
						break;
					}
					LeaveMaster[] leaveMastersarr = leaveMasterDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE  ID IN (" + ModelUtiility.getCommaSeparetedValues(list) + ")", null);
					LeaveBean[] leaveBean = new LeaveBean[leaveMastersarr.length];
					int j = 0;
					for (LeaveMaster leavemasterDto : leaveMastersarr){
						ServiceReqInfo[] serviceReqInfoarr = serviceReqInfoDao.findByDynamicSelect("SELECT * FROM SERVICE_REQ_INFO WHERE ESR_MAP_ID=?", new Object[] { new Integer(leavemasterDto.getEsrMapId()) });
						LeaveBean leaveBeans = new LeaveBean();
						LeaveType[] leaveTypeDto = LeaveTypeDaoFactory.create(conn).findWhereIdEquals(leavemasterDto.getLeaveType());
						leaveBeans.setLeaveTypeName(leaveTypeDto[0].getLeaveType());
						leaveBeans.setDuration(leavemasterDto.getDuration() + "");
						leaveBeans.setFromDate(leavemasterDto.getFromDate());
						leaveBeans.setToDate(leavemasterDto.getToDate());
						leaveBeans.setId(leavemasterDto.getId() + "");
						EmpSerReqMap empSerReqMap = EmpSerReqMapDaoFactory.create(conn).findWhereIdEquals(leavemasterDto.getEsrMapId())[0];
						leaveBeans.setReqId(empSerReqMap.getReqId());
						if (empSerReqMap.getReqId() != null && empSerReqMap.getReqId().endsWith("-CN")) leaveBeans.setStatus(serviceReqInfoarr[0].getStatus().equals(Status.REQUESTRAISED) ? "Cancel Request Raised" : "Cancel Request " + serviceReqInfoarr[0].getStatus());
						else leaveBeans.setStatus(serviceReqInfoarr[0].getStatus().equalsIgnoreCase("Accepted") ? "Approved" : serviceReqInfoarr[0].getStatus());
						int statusId = serviceReqInfoarr[0].getStatus().equalsIgnoreCase("Accepted") ? 1 : Status.getStatusId(serviceReqInfoarr[0].getStatus());
						leaveBeans.setStatusId(statusId + "");
						if (statusId == Status.getStatusId(Status.PENDINGAPPROVAL) || statusId == Status.getStatusId(Status.REJECTED)) leaveBeans.setToCancell("1");
						else leaveBeans.setToCancell(leavemasterDto.getToCancell() + "");
						leaveBeans.setAppliedDate(serviceReqInfoarr[0].getCreationDatetime());
						leaveBean[j] = leaveBeans;
						j++;
					}
					receiveBean.setLeaveDetails(DtoToBeanConverter.DtoToBeanConverter(login));
					receiveBean.setLeaveBeanArray(leaveBean);
					request.setAttribute("actionForm", receiveBean);
					break;
				}
				// for while editing retrives the info of particular request
				// SELECT * FROM LEAVE_MASTER WHERE ID IN (SELECT MAX(LM.ID) FROM LEAVE_MASTER LM WHERE ESR_MAP_ID IN (
				// SELECT ESRM.ID FROM EMP_SER_REQ_MAP ESRM JOIN SERVICE_REQ_INFO SRI ON ESRM.ID=SRI.ESR_MAP_ID WHERE REQUESTOR_ID = 39 AND REQ_TYPE_ID=1 AND REQ_ID NOT LIKE "%CN" AND STATUS IN ('Accepted','Completed')) GROUP BY ESR_MAP_ID);
				case LEAVESINGLERECEIVE:{
					ServiceReqInfoDao serviceReqInfoDao = ServiceReqInfoDaoFactory.create(conn);
					LeaveMaster leaveMaster = (LeaveMaster) form;
					LeaveMaster leaveMasterDto = new LeaveMaster();
					leaveMasterDto = leaveMasterDao.findByPrimaryKey(leaveMaster.getId());
					LeaveBean leaveBeans = DtoToBeanConverter.DtoToBeanConverter(leaveMasterDto);
					if (Integer.parseInt(leaveBeans.getUserId()) != userid){
						leaveBeans.setLeaveCounts(leaveMasterDao.getLeaveHistoryMonthlyCount(Integer.parseInt(leaveBeans.getUserId())));
						LeaveMasterBean[] bean = leaveMasterDao.getLeaveHistory(Integer.parseInt(leaveBeans.getUserId()), 1);
						if (bean != null && bean.length == 1){
							leaveBeans.getLeaveCounts().put("lastLeave", bean[0].getleaveHistoryString());
						}
					}
					leaveBeans.setUserId(null);
					ServiceReqInfo[] serviceReqInfoarr = serviceReqInfoDao.findByDynamicSelect("SELECT * FROM SERVICE_REQ_INFO WHERE ID=(SELECT MAX(ID) from SERVICE_REQ_INFO WHERE ESR_MAP_ID = ?)", new Object[] { new Integer(leaveMasterDto.getEsrMapId()) });
					leaveBeans.setStatus(serviceReqInfoarr[0].getStatus().equalsIgnoreCase("Accepted") ? "Approved" : serviceReqInfoarr[0].getStatus());
					leaveBeans.setStatusId(serviceReqInfoarr[0].getStatus().equalsIgnoreCase("Accepted") ? "1" : Status.getStatusId(serviceReqInfoarr[0].getStatus()) + "");
					request.setAttribute("actionForm", leaveBeans);
					break;
				}
				case LEAVEFORAPPROVE:{
					try{
						DropDown dropdown = (DropDown) form;
						ServiceReqInfoDao serviceReqInfoDao = ServiceReqInfoDaoFactory.create(conn);
						// start filtering
						StringBuffer query = new StringBuffer("SELECT MAX(ID) FROM LEAVE_MASTER WHERE ASSIGNED_TO=? AND STATUS NOT IN(?,?,?) ");
						if (dropdown.getMonth() != null || dropdown.getSearchyear() != null || dropdown.getSearchName() != null){
							if (dropdown.getMonth() != null && dropdown.getToMonth() != null) query.append(" AND (MONTH(FROM_DATE) BETWEEN " + dropdown.getMonth() + " AND " + dropdown.getToMonth() + ") ");
							else if (dropdown.getMonth() != null) query.append(" AND MONTH(FROM_DATE)=" + dropdown.getMonth() + " ");
							if (dropdown.getSearchyear() != null) query.append(" AND YEAR(FROM_DATE)=" + dropdown.getSearchyear() + " ");
							if (dropdown.getSearchName() != null) query.append(" AND (SELECT CONCAT(PF.FIRST_NAME,' ',PF.LAST_NAME) FROM PROFILE_INFO PF WHERE PF.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=(SELECT ESRM.REQUESTOR_ID FROM EMP_SER_REQ_MAP ESRM WHERE ESRM.ID=ESR_MAP_ID))) LIKE '%" + dropdown.getSearchName() + "%' ");
						} else query.append(" AND (TIMESTAMPDIFF(DAY,FROM_DATE,NOW()) <= 30 OR STATUS IN ('" + Status.REQUESTRAISED + "'))");
						query.append(" GROUP BY  ESR_MAP_ID");
						//end filtering
						// added gurunath for performance tuning
						List<Object> list = JDBCUtiility.getInstance().getSingleColumn(query.toString(), new Object[] { new Integer(userid), Status.ASSIGNED, Status.COMPLETED, Status.REVOKED });
						if (list == null || list.size() == 0){
							request.setAttribute("actionForm", "");
							break;
						}
						LeaveMaster[] leaveMastersarr = leaveMasterDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE  ID IN (" + ModelUtiility.getCommaSeparetedValues(list) + ")", null);
						LeaveBean[] leaveBean = new LeaveBean[leaveMastersarr.length];
						int k = 0;
						Integer count = 0;
						for (LeaveMaster leavemasterDto : leaveMastersarr){
							ServiceReqInfo[] serviceReqInfoarr = serviceReqInfoDao.findByDynamicSelect("SELECT * FROM SERVICE_REQ_INFO WHERE ESR_MAP_ID=?", new Object[] { new Integer(leavemasterDto.getEsrMapId()) });
							LeaveBean leaveBeans = new LeaveBean();
							DtoToBeanConverter.DtoToBeanConverter(leavemasterDto);
							EmpSerReqMap empSerReqMap = EmpSerReqMapDaoFactory.create(conn).findWhereIdEquals(leavemasterDto.getEsrMapId())[0];
							ProfileInfo info = ProfileInfoDaoFactory.create(conn).findWhereUserIdEquals(empSerReqMap.getRequestorId());
							leaveBeans.setId(leavemasterDto.getId() + "");
							leaveBeans.setAppliedDate(leavemasterDto.getAppliedDateTime());
							leaveBeans.setFromDate(leavemasterDto.getFromDate());
							leaveBeans.setToDate(leavemasterDto.getToDate());
							leaveBeans.setDuration(leavemasterDto.getDuration() + "");
							leaveBeans.setReportingMgr(ModelUtiility.getInstance().getEmployeeName(info.getReportingMgr()));
							leaveBeans.setAppliedBy(info.getFirstName() + " " + info.getLastName());
							leaveBeans.setEsr_map_id(leavemasterDto.getEsrMapId() + "");
							leaveBeans.setReqId(empSerReqMap.getReqId());
							leaveBeans.setStatus(leavemasterDto.getStatus().equalsIgnoreCase(Status.ACCEPTED) ? "Approved" : leavemasterDto.getStatus());
							leaveBeans.setStatusId(leavemasterDto.getStatus().equalsIgnoreCase(Status.ACCEPTED) ? "1" : Status.getStatusId(leavemasterDto.getStatus()) + "");
							LeaveType[] leaveTypeDto = LeaveTypeDaoFactory.create(conn).findWhereIdEquals(leavemasterDto.getLeaveType());
							leaveBeans.setLeaveTypeName(leaveTypeDto[0].getLeaveType());
							if (serviceReqInfoarr[0].getStatus().equalsIgnoreCase(Status.ACCEPTED) || serviceReqInfoarr[0].getStatus().equalsIgnoreCase(Status.REJECTED) || serviceReqInfoarr[0].getStatus().equalsIgnoreCase(Status.CANCELLED)){
								leaveBeans.setStatus(serviceReqInfoarr[0].getStatus().equalsIgnoreCase(Status.ACCEPTED) ? "Approved" : serviceReqInfoarr[0].getStatus());
								leaveBeans.setStatusId(serviceReqInfoarr[0].getStatus().equalsIgnoreCase(Status.ACCEPTED) ? "1" : Status.getStatusId(serviceReqInfoarr[0].getStatus()) + "");
							} else{
								try{
									if (!leavemasterDto.getStatus().equalsIgnoreCase(Status.ACCEPTED)) leaveBeans.setStatus((String) JDBCUtiility.getInstance().getSingleColumn("SELECT STATUS FROM INBOX WHERE ESR_MAP_ID=? ORDER BY ID DESC", new Object[] { leaveBeans.getEsr_map_id() }).get(0));
								} catch (Exception e){}
								if (leavemasterDto.getToCancell() != 1 && !leavemasterDto.getStatus().equalsIgnoreCase(Status.ACCEPTED) && ModelUtiility.getInstance().isValidApprover(leavemasterDto.getEsrMapId(), userid)){
									leaveBeans.setAssign("1");
									if (ModelUtiility.getInstance().isAssignedApprover(leavemasterDto.getEsrMapId(), login.getUserId())){
										leaveBeans.setApprove("1");
										leaveBeans.setReject("1");
										count++;
									}
								}
							}
							leaveBean[k] = leaveBeans;
							k++;
						}// End of bean
						LeaveReceiveBean receiveBean = new LeaveReceiveBean();
						receiveBean.setLeaveBean(leaveBean);
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
						ProfileInfo[] hrProfileInfo = profileInfoDao.findWhereHrSpocEquals(userid);
						if (hrProfileInfo.length > 0){
							receiveBean.setLeaveHandler("HRSPOC");
						} else{
							ProfileInfo[] rmProfileInfo = profileInfoDao.findWhereReportingMgrEquals(userid);
							if (rmProfileInfo.length > 0){
								receiveBean.setLeaveHandler("RM");
							} else{
								Users[] usrUsers = usersDao.findWhereIdEquals(userid);
								int profileID = usrUsers[0].getProfileId();
								ProfileInfo[] userProfileInfo = profileInfoDao.findWhereReportingMgrEquals(profileID);
								if (userProfileInfo.length > 0){
									receiveBean.setLeaveHandler(userProfileInfo[0].getFirstName());
								}
							}
						}
						receiveBean.setCount(count + "");
						request.setAttribute("actionForm", receiveBean);
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				}
				case RECEIVEALLTOHANDLE:{
					try{
						DropDown dropdown = (DropDown) form;
						// start filtering
						StringBuffer query = new StringBuffer("SELECT MAX(ID) FROM LEAVE_MASTER WHERE ASSIGNED_TO=? AND STATUS IN(?,?,?) ");
						if (dropdown.getMonth() != null || dropdown.getSearchyear() != null || dropdown.getSearchName() != null){
							if (dropdown.getMonth() != null && dropdown.getToMonth() != null) query.append(" AND (MONTH(FROM_DATE) BETWEEN " + dropdown.getMonth() + " AND " + dropdown.getToMonth() + ") ");
							else if (dropdown.getMonth() != null) query.append(" AND MONTH(FROM_DATE)=" + dropdown.getMonth() + " ");
							if (dropdown.getSearchyear() != null) query.append(" AND YEAR(FROM_DATE)=" + dropdown.getSearchyear() + " ");
							if (dropdown.getSearchName() != null) query.append(" AND (SELECT CONCAT(PF.FIRST_NAME,' ',PF.LAST_NAME) FROM PROFILE_INFO PF WHERE PF.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=(SELECT ESRM.REQUESTOR_ID FROM EMP_SER_REQ_MAP ESRM WHERE ESRM.ID=ESR_MAP_ID))) LIKE '%" + dropdown.getSearchName() + "%' ");
						} else query.append(" AND (TIMESTAMPDIFF(DAY,FROM_DATE,NOW()) <= 30 OR STATUS IN ('" + Status.ASSIGNED + "'))");
						query.append(" GROUP BY  ESR_MAP_ID");
						// end filtering
						List<Object> list = JDBCUtiility.getInstance().getSingleColumn(query.toString(), new Object[] { new Integer(userid), Status.ASSIGNED, Status.COMPLETED, Status.REVOKED });
						if (list == null || list.size() == 0){
							request.setAttribute("actionForm", "");
							break;
						}
						LeaveMaster[] leaveMastersarr = leaveMasterDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE  ID IN (" + ModelUtiility.getCommaSeparetedValues(list) + ")", null);
						LeaveBean[] leaveBean = new LeaveBean[leaveMastersarr.length];
						int k = 0;
						Integer count = 0;
						for (LeaveMaster leavemasterDto : leaveMastersarr){
							LeaveBean leaveBeans = DtoToBeanConverter.DtoToBeanConverter(leavemasterDto);
							leaveBeans.setStatus(leavemasterDto.getStatus());
							leaveBeans.setStatusId(Status.getStatusId(leavemasterDto.getStatus()) + "");
							try{
								if (leavemasterDto.getStatus().equals(Status.ASSIGNED)) leaveBeans.setStatus((String) JDBCUtiility.getInstance().getSingleColumn("SELECT STATUS FROM INBOX WHERE ESR_MAP_ID=? ORDER BY ID DESC", new Object[] { leaveBeans.getEsr_map_id() }).get(0));
							} catch (Exception e){}
							if (ModelUtiility.getInstance().isValidApprover(leavemasterDto.getEsrMapId(), userid) && leavemasterDto.getStatus().equals(Status.ASSIGNED)){
								leaveBeans.setAssign("1");
								if (ModelUtiility.getInstance().isAssignedApprover(leavemasterDto.getEsrMapId(), login.getUserId())){
									leaveBeans.setComplete("1");
									count++;
								}
							}
							leaveBean[k] = leaveBeans;
							k++;
						}// End of bean
						LeaveMasterBean leaveMasterBean = new LeaveMasterBean();
						leaveMasterBean.setLeaveBean(leaveBean);
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
						ProfileInfo[] hrProfileInfo = profileInfoDao.findWhereHrSpocEquals(userid);
						ProfileInfo[] rmProfileInfo = profileInfoDao.findWhereReportingMgrEquals(userid);
						if (hrProfileInfo.length > 0){
							leaveMasterBean.setLeaveHandler("HRSPOC");
						} else if (rmProfileInfo.length > 0){
							leaveMasterBean.setLeaveHandler("RM");
						} else{
							Users[] usrUsers = usersDao.findWhereIdEquals(userid);
							int profileID = usrUsers[0].getProfileId();
							ProfileInfo[] userProfileInfo = profileInfoDao.findWhereReportingMgrEquals(profileID);
							if (userProfileInfo.length > 0){
								leaveMasterBean.setLeaveHandler(userProfileInfo[0].getFirstName());
							}
						}
						dropdown.setDetail(leaveMasterBean);
						dropdown.setCount(count);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				}
				case LEAVETYPES:{
					try{
						LeaveTypeDao leaveTypeDao = LeaveTypeDaoFactory.create(conn);
						DropDown dropDown = (DropDown) form;
						if (login != null){
							Users user = usersDao.findByPrimaryKey(login.getUserId());
							if (user != null && user.getProfileId() > 0){
								ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
								ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());
								if (profileInfo.getDateOfConfirmation() != null){
									if (profileInfo.getDateOfConfirmation().before(new Date())){
										// added condition for paternity and maternity leaves.
										if (profileInfo.getGender().equalsIgnoreCase("MALE")) dropDown.setDropDown(leaveTypeDao.findByDynamicWhere(" ID NOT IN (5) ", null));
										else dropDown.setDropDown(leaveTypeDao.findByDynamicWhere(" ID NOT IN (6) ", null));
									} else{
										LeaveType lt[] = leaveTypeDao.findByDynamicWhere(" ID IN (2,3) ", null);
										dropDown.setDropDown(lt);
									}
								} else{
									LeaveType lt[] = leaveTypeDao.findByDynamicWhere(" ID IN (2,3) ", null);
									dropDown.setDropDown(lt);
								}
							}
						}
						request.setAttribute("actionForm", dropDown);
					} catch (Exception e){
						e.printStackTrace();
					}
					return result;
				}
				case LEAVEACCUMALATOR:{
					try{
						LeaveBalance leaveBalanceDto1 = new LeaveBalance();
						LeaveBalanceDao leaveBalanceDao1 = LeaveBalanceDaoFactory.create(conn);
						LeaveBalance leaveBalance1 = leaveBalanceDao1.findWhereUserIdEquals(userid);
						if (leaveBalance1 != null){
							leaveBalanceDto1.setLeaveAccumalated(leaveBalance1.getLeaveAccumalated());
							leaveBalanceDto1.setLeavesTaken(leaveBalance1.getLeavesTaken());
							// setting the avilabe comp-off leaves for UI validation. 
							leaveBalanceDto1.setCompOff(leaveBalance1.getCompOff());
						}
						request.setAttribute("actionForm", leaveBalanceDto1);
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				}
				case RECEIVEHOLIDAYLIST_TIMESHEET:
					try
					{
						//		Holidays[] holiday1 = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS H JOIN CALENDAR C ON H.CAL_ID=C.ID JOIN PROJECT_MAP P ON C.ID=P.CALENDAR_ID JOIN PROJECT PR ON PR.ID=P.PROJ_ID where PR.ID=?",new Object[] { projectId });
						/*DropDown dropDown = (DropDown) form;
						CalendarDao calendarDao2 = CalendarDaoFactory.create(conn);
						Calendar[] cal = calendarDao2.findWhereRegionEquals(login.getUserLogin().getRegionId());
						if (cal != null && cal.length > 0){
							ArrayList<Integer> list = new ArrayList<Integer>();
							for (Calendar calendars : cal)
								list.add(calendars.getId());
							HolidaysDao holidaysDao = HolidaysDaoFactory.create(conn);
							Holidays[] holidays = holidaysDao.findByDynamicWhere(" CAL_ID IN ( " + ModelUtiility.getCommaSeparetedValues(list) + " )", null);
							HolidaysBean[] holidaysBeans = new HolidaysBean[holidays.length];
							int i = 0;
							for (Holidays holidays2 : holidays){
								HolidaysBean holidaysBean = DtoToBeanConverter.DtoToBeanConverter(holidays2);
								holidaysBeans[i] = holidaysBean;
								i++;
							}
							dropDown.setDropDown(holidaysBeans);
							request.setAttribute("actionForm", dropDown);
						}*/
						Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT H.* FROM HOLIDAYS H JOIN CALENDAR C ON H.CAL_ID=C.ID  JOIN DIVISON D ON  C.REGION = D.REGION_ID JOIN LEVELS L ON D.ID=L.DIVISION_ID JOIN USERS U ON U.LEVEL_ID=L.ID  WHERE U.ID=?", new Object[] { userid });
						HolidaysListBean[] holidaysBeans = new HolidaysListBean[holiday.length];
						int i = 0;
						if (holiday != null && holiday.length > 0) for (Holidays tmpHoliday : holiday){
							holidaysBeans[i] = new HolidaysListBean(tmpHoliday.getDatePicker());
							i++;
						}
						DropDownBean bean = new DropDownBean();
						bean.setDropDown(holidaysBeans);
						request.setAttribute("actionForm", bean);
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
					
				case RECEIVEHOLIDAYLIST:
					try{
			            ProjectMapDao mapDao=ProjectMapDaoFactory.create();
	
				
				//		Holidays[] holiday1 = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS H JOIN CALENDAR C ON H.CAL_ID=C.ID JOIN PROJECT_MAP P ON C.ID=P.CALENDAR_ID JOIN PROJECT PR ON PR.ID=P.PROJ_ID where PR.ID=?",new Object[] { projectId });
						/*DropDown dropDown = (DropDown) form;
						CalendarDao calendarDao2 = CalendarDaoFactory.create(conn);
						Calendar[] cal = calendarDao2.findWhereRegionEquals(login.getUserLogin().getRegionId());
						if (cal != null && cal.length > 0){
							ArrayList<Integer> list = new ArrayList<Integer>();
							for (Calendar calendars : cal)
								list.add(calendars.getId());
							HolidaysDao holidaysDao = HolidaysDaoFactory.create(conn);
							Holidays[] holidays = holidaysDao.findByDynamicWhere(" CAL_ID IN ( " + ModelUtiility.getCommaSeparetedValues(list) + " )", null);
							HolidaysBean[] holidaysBeans = new HolidaysBean[holidays.length];
							int i = 0;
							for (Holidays holidays2 : holidays){
								HolidaysBean holidaysBean = DtoToBeanConverter.DtoToBeanConverter(holidays2);
								holidaysBeans[i] = holidaysBean;
								i++;
							}
							dropDown.setDropDown(holidaysBeans);
							request.setAttribute("actionForm", dropDown);
						}*/
						
					int year=Calendar.getInstance().get(Calendar.YEAR); 
					DropDown leave=	(DropDown) form;
					int projectId=leave.getProjectId();
					Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS H JOIN CALENDAR C ON H.CAL_ID=C.ID JOIN PROJECT_MAP P ON C.ID=P.CALENDAR_ID JOIN PROJECT PR ON PR.ID=P.PROJ_ID where PR.ID=? and YEAR= ?",new Object[] { projectId,year });
				//	Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT H.* FROM HOLIDAYS H JOIN CALENDAR C ON H.CAL_ID=C.ID  JOIN DIVISON D ON  C.REGION = D.REGION_ID JOIN LEVELS L ON D.ID=L.DIVISION_ID JOIN USERS U ON U.LEVEL_ID=L.ID  WHERE U.ID=?", new Object[] { userid });
						HolidaysListBean[] holidaysBeans = new HolidaysListBean[holiday.length];
						HolidaysListBean[] holidaysBeans1 = null;
						int i = 0;
						if (holiday != null && holiday.length > 0) 
							for (Holidays tmpHoliday : holiday){
							holidaysBeans[i] = new HolidaysListBean(tmpHoliday.getDatePicker());
							i++;
						}
						else if(holiday.length==0){
							ProjectMap[] proMap=mapDao.findWhereProjIdEquals(projectId);
							if(proMap!=null){
								for(ProjectMap pro:proMap){
									Holidays[] holidayDefault = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS H JOIN CALENDAR C ON H.CAL_ID=C.ID  WHERE C.REGION=? and YEAR= ? and C.ID IN(SELECT max(ID) FROM CALENDAR  WHERE REGION=? and YEAR=?)",new Object[] { pro.getRegionId(),year,pro.getRegionId(),year });
									holidaysBeans1 = new HolidaysListBean[holidayDefault.length];
									for (Holidays tmpHoliday1 : holidayDefault){
										holidaysBeans1[i] = new HolidaysListBean(tmpHoliday1.getDatePicker());
										i++;
									}
								}
								
							}
							
						}
						DropDownBean bean = new DropDownBean();
						if (holiday != null && holiday.length > 0) {
						bean.setDropDown(holidaysBeans);
						}else bean.setDropDown(holidaysBeans1);
						request.setAttribute("actionForm", bean);
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				case TOAPPROVE:
					try{
						request.setAttribute("actionForm", isApprover(login.getUserId()));
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				case TOHANDLE:
					try{
						request.setAttribute("actionForm", isHandler(login.getUserId()));
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				default:
					break;
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		DocumentTypes dTypes = DocumentTypes.valueOf(docType);
		Integer fieldsId[] = new Integer[fileItems.size()];
		// uplaoding leave details from xls sheet.
		if (dTypes.equals(DocumentTypes.LEAVES_XLS)){
			List<Integer> notUploaded = new ArrayList<Integer>();
			if (fileItems != null && !fileItems.isEmpty()){
				FileItem file = (FileItem) fileItems.get(0);
				InputStream stream = null;
				try{
					stream = file != null ? file.getInputStream() : null;
				} catch (IOException e1){
					e1.printStackTrace();
				}
				if (stream != null) try{
					Vector<Vector<Object>> list = POIParser.parseXls(stream, 0);
					stream.close();
					if (list != null && !list.isEmpty()){
						Connection con = ResourceManager.getConnection();
						LeaveBalanceDao leaveDao = LeaveBalanceDaoFactory.create(con);
						UsersDao userDao = UsersDaoFactory.create();
						LeaveBalance leaveto = null;
						int empId = -1;
						for (Vector<Object> row : list){
							try{
								leaveto = new LeaveBalance();
								try{
									empId = (int) Float.parseFloat(row.get(0) + "");
								} catch (Exception e){
									// skip if emp id is not valid data type.
									continue;
								}
								Users[] user = userDao.findWhereEmpIdEquals(empId);
								//skip if employee id not present in database.
								if (user == null || user.length == 0){
									notUploaded.add(empId);
									continue;
								}
								leaveto.setUserId(user[0].getId());
								leaveto.setLeaveAccumalated(Float.parseFloat(row.get(1).toString()));
								leaveto.setLeavesTaken(Float.parseFloat(row.get(2).toString()));
								leaveto.setPaternity(Float.parseFloat(row.get(3).toString()));
								leaveto.setMarriage(Float.parseFloat(row.get(4).toString()));
								leaveto.setBereavement(Float.parseFloat(row.get(5).toString()));
								leaveto.setMaternity(Float.parseFloat(row.get(6).toString()));
								leaveto.setCompOff(Float.parseFloat(row.get(7).toString()));
								leaveto.setBalance(Float.parseFloat(row.get(1).toString()) - Float.parseFloat(row.get(2).toString()));
								if (!leaveDao.updateFromXls(leaveto)) notUploaded.add(empId);
							} catch (Exception e){
								if (!notUploaded.contains(empId)) notUploaded.add(empId);
							}
						}
						if (con != null) con.close();
					}
				} catch (Exception e){
					notUploaded = new ArrayList<Integer>();
					notUploaded.add(-1);
				}
			}
			Integer in[] = new Integer[notUploaded.size() == 0 ? 1 : notUploaded.size()];
			in[0] = 0;
			for (int i = 0, l = notUploaded.size(); i < l; i++)
				in[i] = notUploaded.get(i);
			return in;
		}
		boolean isUpload = true;
		int i = 0;
		for (FileItem fileItem2 : fileItems){
			logger.info("Inside leave model.");
			logger.info("FileName: " + fileItem2.getName());
			logger.info("FileType: " + fileItem2.getContentType());
			logger.info("FileSize: " + fileItem2.getSize());
			String[] temp = fileItem2.getName().split("\\.");
			logger.info("FileExtension: " + temp[temp.length - 1]);
			PortalData portalData = new PortalData();
			try{
				/**
				 * save fileName in dataBase
				 */
				Documents documents = new Documents();
				DocumentsDao documentsDao = DocumentsDaoFactory.create();
				documents.setFilename(fileItem2.getName());
				documents.setDoctype(docType);
				documents.setDescriptions(description);
				DocumentsPk documentsPk = null;
				try{
					documentsPk = documentsDao.insert(documents);
					fieldsId[i] = documentsPk.getId();
				} catch (DocumentsDaoException e){
					fieldsId[i] = 0;
					e.printStackTrace();
				}
				i++;
				String fileName = portalData.saveFile(fileItem2, dTypes, id, documentsPk.getId() + "");
				if (fileName != null){
					documents.setFilename(fileName);
					try{
						documentsDao.update(documentsPk, documents);
					} catch (DocumentsDaoException e){
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e){
				isUpload = false;
				e.printStackTrace();
			}
		}
		if (isUpload){
			logger.info("File uploaded successfully.");
		} else{
			logger.info("File upload failed.");
		}
		// request.getSession(false).setAttribute("fileId",fieldsId);
		return fieldsId;
	}

	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		LeaveMaster leaveMaster = (LeaveMaster) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		try{
			PortalData portalData = new PortalData();
			path = portalData.getfolder(portalData.getDirPath());
			File file = new File(path);
			if (!file.exists()) file.mkdir();
			switch (DownloadTypes.getValue(form.getdType())) {
				case LEAVEBALANCESHEET:
					if (ModelUtiility.hasModulePermission((Login) form.getObject(), 17)) try{
						path += seprator + "temp";
						File file1 = new File(path);
						if (!file1.exists()) file1.mkdir();
						String query = "SELECT (SELECT EMP_ID FROM USERS US WHERE US.ID=USER_ID) AS ID, USER_ID, LEAVE_ACCUMALATED, PATERNITY, MARRIAGE, BEREAVEMENT, MATERNITY, LEAVES_TAKEN, BALANCE, CREATED_ON, COMP_OFF, LWP FROM LEAVE_BALANCE  WHERE USER_ID IN (SELECT U.ID FROM USERS U WHERE U.STATUS NOT IN(1,2,3) AND EMP_ID !=0) ORDER BY ID";
						attachements.setFileName(new GenerateXls().generateLeaveBalanceXls(LeaveBalanceDaoFactory.create().findByDynamicSelect(query, null), path + File.separator + "Leave_balance_" + PortalUtility.formateDateTimeToDByyyyMMdd(new Date()) + ".xls"));
					} catch (Exception e){
						logger.error("unable to generate leave balance xls", e);
					}
					break;
				default:
					try{
						DocumentsDao documentsDao = DocumentsDaoFactory.create();
						int fileID = Integer.parseInt(leaveMaster.getAttachment());
						Documents[] dacDocuments = documentsDao.findWhereIdEquals(fileID);
						path = portalData.getDirPath();
						attachements.setFileName(dacDocuments[0].getFilename());
						path = portalData.getfolder(path);
					} catch (DocumentsDaoException e){
						e.printStackTrace();
					}
					break;
			}
			path = path + File.separator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (Exception e){
			e.printStackTrace();
		}
		return attachements;
	}

	private Integer getAllNextApproverGroup(String approvalChain, int appLevel, int approverId, int raisedBy) {
		String[] splittedApproverChain = approvalChain.split(";");
		ProcessEvaluator procEvalutor = new ProcessEvaluator();
		if (reqEscalation.isEscalationAction()) reqEscalation.setEscalationProcess(procEvalutor);
		for (int i = 0; i < splittedApproverChain.length; i++){
			Integer[] approverGroupIds = procEvalutor.approvers(approvalChain, appLevel, raisedBy);
			List<Integer> approversuserList = new ArrayList<Integer>(Arrays.asList(approverGroupIds));
			if (approversuserList.contains(approverId)){
				int newLevel = appLevel + 1;
				return new Integer(newLevel);
			}
			appLevel++;
		}
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isApprover(Integer userId) {
		LeaveMasterDao lDao = LeaveMasterDaoFactory.create();
		boolean result = false;
		try{
			String sql = "ASSIGNED_TO = ? AND STATUS NOT IN ('Assigned' ,'Completed','Revoked')";
			Object[] sqlParams = { userId };
			LeaveMaster[] lReqs = lDao.findByDynamicWhere(sql, sqlParams);
			if (lReqs.length > 0){
				result = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private boolean isHandler(Integer userId) {
		LeaveMasterDao lDao = LeaveMasterDaoFactory.create();
		boolean result = false;
		try{
			String sql = "ASSIGNED_TO = ? AND  STATUS IN('Assigned' ,'Completed','Revoked')";
			Object[] sqlParams = { userId };
			LeaveMaster[] lReqs = lDao.findByDynamicWhere(sql, sqlParams);
			if (lReqs.length > 0){
				result = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private PortalMail sendMailDetails(String subject, String template, Users userDto, ProfileInfo requsterprofile, ProfileInfo approverProfile, LeaveMaster leaveMaster, LeaveBalance leavebalance, float lwp, String action, String[] ccMailIds, HttpServletRequest request) {
		return sendMailDetails(subject, template, userDto, requsterprofile, approverProfile, leaveMaster, leavebalance, lwp, action, ccMailIds, request, null);
	}

	/**
	 * @author gurunath.rokkam
	 * @return PortalMail
	 */
	private PortalMail sendMailDetails(String subject, String template, Users userDto, ProfileInfo requsterprofile, ProfileInfo approverProfile, LeaveMaster leaveMaster, LeaveBalance leavebalance, float lwp, String action, String[] ccMailIds, HttpServletRequest request, String[] toMailIds) {
		PortalMail portalMail = new PortalMail();
		try{
			if (request != null){
				portalMail.setServerId(request.getRequestURL().toString().replaceAll(request.getServletPath(), ""));
			}
			portalMail.setActionType("");
			portalMail.setComment(getCommentsForMail(leaveMaster.getComment()));
			portalMail.setReason(leaveMaster.getReason() == null ? "" : leaveMaster.getReason());
			portalMail.setMailSubject(subject);
			portalMail.setTemplateName(template);
			portalMail.setLeavebalance(leavebalance.getBalance());
			portalMail.setLeavesTaken(leavebalance.getLeavesTaken() + "");
			portalMail.setAccumulatedLeaves(leavebalance.getLeaveAccumalated() + "");
			portalMail.setLeaveStartDate(PortalUtility.getdd_MM_yyyy(leaveMaster.getFromDate()));
			portalMail.setLeaveEndDate(PortalUtility.getdd_MM_yyyy(leaveMaster.getToDate()));
			portalMail.setTotaldays(leaveMaster.getDuration());
			portalMail.setAcceptOfferLink(leaveMaster.getAcceptLink());
			portalMail.setRejectOfferLink(leaveMaster.getRejectLink());
			portalMail.setOnDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()));
			if (approverProfile != null) portalMail.setHandlerName(approverProfile.getFirstName());
			String leaveType = "N.A";
			try{
				leaveType = LeaveTypeDaoFactory.create().findByPrimaryKey(leaveMaster.getLeaveType()).getLeaveType();
			} catch (Exception e){}
			portalMail.setLeaveType(leaveType);
			portalMail.setAllReceipientcCMailId(ccMailIds);
			if (template.equals(MailSettings.LEAVE_LWP_USER_NOTIFY)){
				portalMail.setAllReceipientMailId(toMailIds);
				portalMail.setBalance(lwp);
				if (action.equals(Status.SUBMITTED)){
					portalMail.setActionType("taken");
				} else{
					portalMail.setActionType("cancelled");
				}
			} else if (template.equals(MailSettings.LEAVE_LWP_COMPLETED_USER_NOTIFY)){
				portalMail.setRecipientMailId(requsterprofile.getOfficalEmailId());
				portalMail.setBalance(lwp);
				if (action.equals(Status.COMPLETED)){
					portalMail.setActionType("");
					portalMail.setComment(getCommentsForMail(leaveMaster.getRemark()));
				} else{
					portalMail.setActionType("cancel");
				}
			} else if (template.equals(MailSettings.LEAVE_RECEIVE)){
				if (action.equals(Status.SUBMITTED)){
					if (subject.contains("cancel")) portalMail.setActionType("cancel ");
					portalMail.setRecipientMailId(approverProfile.getOfficalEmailId());
				}
			} else if (template.equals(MailSettings.LEAVE_NOTIFY_USER)){
				portalMail.setRecipientMailId(requsterprofile.getOfficalEmailId());
				if (action.equals(Status.APPROVED)){
					portalMail.setActionType("approved");
				} else portalMail.setActionType("rejected");
			} else if (template.equals(MailSettings.LEAVE_STATUS_NOTIFY_FOR_SIBLING)){
				portalMail.setRecipientMailId(approverProfile.getOfficalEmailId());
				if (action.equals(Status.APPROVED)){
					portalMail.setActionType("approved");
				} else portalMail.setActionType("rejected");
			}
			setEmployeeDetails(portalMail, userDto, requsterprofile);
			if ((portalMail.getRecipientMailId() != null && portalMail.getRecipientMailId().contains("@")) || portalMail.getAllReceipientMailId() != null){
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}
			return portalMail;
		} catch (Exception e){}
		return portalMail;
	}

	private Integer[] getAllSiblingApprover(String approvalChain, int appLevel, int approverUid, int raisedBy) {
		String[] splittedApproverChain = approvalChain.split(";");
		ProcessEvaluator procEvalutor = new ProcessEvaluator();
		if (reqEscalation.isEscalationAction()) reqEscalation.setEscalationProcess(procEvalutor);
		for (int i = 0; i < splittedApproverChain.length; i++){
			Integer[] approverGroupIds = procEvalutor.approvers(approvalChain, appLevel, raisedBy);
			List<Integer> approversuserList = new ArrayList<Integer>(Arrays.asList(approverGroupIds));
			if (approversuserList.contains(approverUid)){ return approverGroupIds; }
			appLevel++;
		}
		return null;
	}

	/**
	 * @author gurunath.rokkam
	 * @param comments
	 * @return Html formatted comments
	 */
	private String getCommentsForMail(String comments) {
		if (comments != null && comments.length() > 0) return "<strong>Comments:</strong>&nbsp;" + comments + "<br/><br/>";
		return "";
	}
	
private void sendAndroidLeaveNotification(String message,List<String> mailIds)	{
	LoginModel rmModel=new LoginModel();
	List<Integer> ids=new ArrayList<Integer>();
	ProfileInfo[] pf=null;
	ProfileInfoDao profileInfoDao=ProfileInfoDaoFactory.create();
	Users[] user=null;
	UsersDao usersDao=UsersDaoFactory.create();
	if(mailIds!=null){
		for(String mId: mailIds ){
		try {
			pf=profileInfoDao.findWhereOfficalEmailIdEquals(mId);
			if(pf!=null && pf.length>0){
					user=usersDao.findWhereProfileIdEquals(pf[0].getId());
					ids.add(user[0].getId());
			}
			
		} catch (ProfileInfoDaoException e) {
			e.printStackTrace();
		} catch (UsersDaoException e) {
			e.printStackTrace();}
		}
	}
	rmModel.sendGcmNotification(message,ids,"LEAVE");
}
	
}