package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.HandlersListBean;
import com.dikshatech.beans.InsuranceBean;
import com.dikshatech.beans.XlsRecordsBean;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AddressDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.InsuranceHandlerChainReqDao;
import com.dikshatech.portal.dao.InsurancePolicyDetailsDao;
import com.dikshatech.portal.dao.InsurancePolicyReqDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Address;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.InsuranceHandlerChainReq;
import com.dikshatech.portal.dto.InsuranceHandlerChainReqPk;
import com.dikshatech.portal.dto.InsurancePolicyDetails;
import com.dikshatech.portal.dto.InsurancePolicyDetailsPk;
import com.dikshatech.portal.dto.InsurancePolicyReq;
import com.dikshatech.portal.dto.InsurancePolicyReqPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ReferFriend;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.InsurancePolicyReqDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.AddressDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.InsuranceHandlerChainReqDaoFactory;
import com.dikshatech.portal.factory.InsurancePolicyDetailsDaoFactory;
import com.dikshatech.portal.factory.InsurancePolicyReqDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class InsurancePolicyModel extends ActionMethods {

	private static Logger	logger	= LoggerUtil.getLogger(ItModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		InsuranceBean insuranceBean = (InsuranceBean) form;
		InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
		InsurancePolicyDetailsDao insPolicyDtlsDao = InsurancePolicyDetailsDaoFactory.create();
		InsuranceHandlerChainReqDao insHndChainReqDao = InsuranceHandlerChainReqDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		Users requestedUser = new Users();
		String userIdtFlag = "false";
		DropDown drpDown = new DropDown();
		switch (ActionMethods.ReceiveTypes.getValue(insuranceBean.getrType())) {
			case RECEIVEALL:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					if (loginDto.getUserId() <= 3){
						drpDown.setUserIdFlag("true");
					} else{
						InsuranceHandlerChainReq insHndChainReq[] = insHndChainReqDao.findByDynamicWhere("RECEIVER_ID=?", new Object[] { new Integer(loginDto.getUserId()) });
						if (insHndChainReq.length > 0){
							userIdtFlag = "true";
							drpDown.setUserIdFlag(userIdtFlag);
						} else{
							userIdtFlag = (JDBCUtiility.getInstance().getRowCount("FROM PROFILE_INFO WHERE HR_SPOC=?", new Object[] { Login.getLogin(request).getUserId() }) > 0) + "";
							drpDown.setUserIdFlag(userIdtFlag);
						}
					}
					EmpSerReqMap empMap[] = empSerReqDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=15 AND REQUESTOR_ID=?", new Object[] { new Integer(loginDto.getUserId()) });
					Object[] insuranceReceiverArray;
					List<InsuranceBean> insuranceReceiverList = new ArrayList<InsuranceBean>();
					if (empMap.length > 0){
						for (int i = 0; i < empMap.length; i++){
							InsuranceBean insuranceBean1 = new InsuranceBean();
							ProfileInfo profoleInfo = new ProfileInfo();
							String reqId = empMap[i].getReqId();
							InsurancePolicyReq insPolicyReqBean[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empMap[i].getId() });
							InsurancePolicyDetails insPolicydtlBean[] = insPolicyDtlsDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empMap[i].getId() });
							if (insPolicyReqBean.length > 0 && insPolicydtlBean.length > 0){
								/*	String requestedOn = insPolicyReqBean[0].getRequestedOn().toString();
									String[] requestedOnArray = requestedOn.split("-");
									requestedOn = requestedOnArray[2].split(" ")[0] + "-" + requestedOnArray[1] + "-" + requestedOnArray[0];*/
								for (InsurancePolicyReq req : insPolicyReqBean){
									for (InsurancePolicyDetails detail : insPolicydtlBean)
										if (detail.getPolicyReqId() == req.getId()){
											req.setName(detail.getName());
											break;
										}
								}
								Users insuredUser1 = usersDao.findByPrimaryKey(insPolicyReqBean[0].getRequeterId());
								profoleInfo = profileInfoDao.findByPrimaryKey(insuredUser1.getProfileId());
								insuranceBean1.setInsPolicydtlBean(insPolicydtlBean);
								insuranceBean1.setInsPolicyReqBean(insPolicyReqBean);
								insuranceBean1.setPolicyHolderName(profoleInfo.getFirstName() + " " + profoleInfo.getMaidenName() + " " + profoleInfo.getLastName());
								insuranceBean1.setRequestId(reqId);
								insuranceBean1.setRequestedOn(PortalUtility.getdd_MM_yyyy(insPolicyReqBean[0].getRequestedOn()));
								insuranceBean1.setEsrMapId(insPolicydtlBean[0].getEsrMapId());
								insuranceBean1.setStatus(getOverAllStatus(insPolicyReqBean));
								insuranceBean1.setStatusId(Status.getStatusId(getOverAllStatus(insPolicyReqBean)));
								insuranceReceiverList.add(insuranceBean1);
							}
						}
					}
					insuranceReceiverArray = insuranceReceiverList.toArray();
					//drpDown.setuserIdFlag(JDBCUtiility.getInstance().getRowCount("FROM PROFILE_INFO WHERE HR_SPOC=?", new Object[] { Login.getLogin(request).getUserId() }) > 0 );
					drpDown.setDropDown(insuranceReceiverArray);
					request.setAttribute("actionForm", drpDown);
				} catch (EmpSerReqMapDaoException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
				}
				break;
			}
			case RECEIVE:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					Object[] insuranceReceiverArray = new Object[1];
					ProfileInfo profoleInfo = new ProfileInfo();
					EmpSerReqMap empSerReqMap = empSerReqDao.findByPrimaryKey(insuranceBean.getEsrMapId());
					String reqId = empSerReqMap.getReqId();
					if (empSerReqMap != null){
						InsurancePolicyReq InsPlyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empSerReqMap.getId() });
						InsurancePolicyDetails InsPlyDtls[] = insPolicyDtlsDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empSerReqMap.getId() });
						if (InsPlyReq.length >= 1 && InsPlyDtls.length >= 1){
							Users insuredUser1 = usersDao.findByPrimaryKey(InsPlyReq[0].getRequeterId());
							profoleInfo = profileInfoDao.findByPrimaryKey(insuredUser1.getProfileId());
							String requestedOn = InsPlyReq[0].getRequestedOn().toString();
							String[] requestedOnArray = requestedOn.split("-");
							requestedOn = requestedOnArray[2].split(" ")[0] + "-" + requestedOnArray[1] + "-" + requestedOnArray[0];
							for (int j = 0; j < InsPlyReq.length; j++){
								InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { empSerReqMap.getId(), InsPlyReq[j].getId() });
								if (InsHndChainReqs.length > 0){
									InsPlyReq[j].setComments(InsHndChainReqs[0].getComments() == null ? "No Comments" : InsHndChainReqs[0].getComments());
								}
							}
							insuranceBean.setPolicyHolderName(profoleInfo.getFirstName() + " " + profoleInfo.getMaidenName() + " " + profoleInfo.getLastName());
							insuranceBean.setInsPolicydtlBean(InsPlyDtls);
							insuranceBean.setInsPolicyReqBean(InsPlyReq);
							insuranceBean.setRequestId(reqId);
							insuranceBean.setRequestedOn(requestedOn);
							insuranceBean.setEsrMapId(InsPlyReq[0].getEsrMapId());
							insuranceBean.setStatus(InsPlyReq[0].getStatus());
							insuranceBean.setStatusId(Status.getStatusId(InsPlyReq[0].getStatus()));
							insuranceBean.setStatus(getOverAllStatus(InsPlyReq));
							if (!InsPlyReq[0].getStatus().equals(Status.REQUESTSAVED)){
								insuranceBean.setHndSiblingList(getHndSiblingList(InsPlyReq[0].getId()));
								insuranceBean.setAssignedHndName(getAssignedUserName(InsPlyReq[0].getEsrMapId(), InsPlyReq[0].getId()));
							}
						}
					}
					insuranceReceiverArray[0] = insuranceBean;
					drpDown.setDropDown(insuranceReceiverArray);
					request.setAttribute("actionForm", drpDown);
				} catch (Exception ex){
					ex.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
				}
				break;
			}
			case RECEIVEALLTOHANDLE:{
				try{
					int pandingReq = 0;
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					EmpSerReqMap empMap[] = empSerReqDao.findByDynamicSelect("SELECT ESRM.* FROM EMP_SER_REQ_MAP ESRM JOIN INSURANCE_HANDLER_CHAIN_REQ IHCR ON IHCR.ESR_MAP_ID=ESRM.ID WHERE ESRM.REQ_TYPE_ID=15 AND IHCR.RECEIVER_ID=?", new Object[] { loginDto.getUserId() });
					Object[] insuranceReceiverArray;
					List<InsuranceBean> insuranceReceiverList = new ArrayList<InsuranceBean>();
					if (empMap.length > 0){
						for (int i = 0; i < empMap.length; i++){
							InsuranceBean insuranceBean1 = new InsuranceBean();
							ProfileInfo profoleInfo = new ProfileInfo();
							String reqId = empMap[i].getReqId();
							InsurancePolicyReq insPolicyReqBean[] = insPolicyReqDao.findByDynamicWhere(" ESR_MAP_ID=?", new Object[] { empMap[i].getId() });
							InsurancePolicyDetails insPolicydtlBean[] = insPolicyDtlsDao.findByDynamicWhere(" ESR_MAP_ID=?", new Object[] { empMap[i].getId() });
							if (insPolicyReqBean.length > 0 && insPolicydtlBean.length > 0){
								for (InsurancePolicyReq req : insPolicyReqBean){
									for (InsurancePolicyDetails detail : insPolicydtlBean)
										if (detail.getPolicyReqId() == req.getId()){
											req.setName(detail.getName());
											break;
										}
								}
								InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insPolicyReqBean[0].getEsrMapId() });
								if (!(insPolicyReqBean[0].getStatus().equals(Status.REQUESTSAVED)) && insPolicyReqBean[0] != null && insPolicydtlBean.length >= 1 && InsHndChainReqs.length >= 1){
									int requesterId = insPolicyReqBean[0].getRequeterId();
									if (requesterId != requestedUser.getId()){
										if (InsHndChainReqs[0].isAssignToNull() || InsHndChainReqs[0].getAssignTo() == requestedUser.getId() && (!InsHndChainReqs[0].getStatus().equals(Status.OFFERAPPROVED) && !InsHndChainReqs[0].getStatus().equals(Status.REJECTED))){
											insuranceBean1.setIsAssigned(true);
										} else insuranceBean1.setIsAssigned(false);
										Users abc = usersDao.findByPrimaryKey(requesterId);
										profoleInfo = profileInfoDao.findByPrimaryKey(abc.getProfileId());
										String requestedOn = insPolicyReqBean[0].getRequestedOn().toString();
										String[] requestedOnArray = requestedOn.split("-");
										requestedOn = requestedOnArray[2].split(" ")[0] + "-" + requestedOnArray[1] + "-" + requestedOnArray[0];
										insuranceBean1.setInsPolicydtlBean(insPolicydtlBean);
										insuranceBean1.setInsPolicyReqBean(insPolicyReqBean);
										insuranceBean1.setPolicyHolderName(profoleInfo.getFirstName() + " " + profoleInfo.getMaidenName() + " " + profoleInfo.getLastName());
										insuranceBean1.setRequestId(reqId);
										insuranceBean1.setRequestedOn(requestedOn);
										insuranceBean1.setEsrMapId(insPolicyReqBean[0].getEsrMapId());
										insuranceBean1.setStatus(getOverAllStatus(insPolicyReqBean));
										insuranceBean1.setStatusId(Status.getStatusId(getOverAllStatus(insPolicyReqBean)));
										pandingReq += getPandingRequestCount(insPolicyReqBean, InsHndChainReqs, requestedUser.getId());
										insuranceReceiverList.add(insuranceBean1);
									}
								}
							}
						}
					}
					insuranceReceiverArray = insuranceReceiverList.toArray();
					drpDown.setDropDown(insuranceReceiverArray);
					drpDown.setCount(pandingReq);
					request.setAttribute("actionForm", drpDown);
				} catch (Exception ex){
					ex.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.can not handle"));
				}
				break;
			}
		}
		return result;
	}

	public HashSet<Integer> getUserIdsFromUserList(ArrayList<Users> userList) {
		HashSet<Integer> userIdList = new HashSet<Integer>();
		for (Users user1 : userList){
			if (user1.getId() != 0){
				userIdList.add(user1.getId());
			}
		}
		return userIdList;
	}
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		InsuranceBean insuranceBean = (InsuranceBean) form;
		InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
		InsurancePolicyDetailsDao insPolicyDtlsDao = InsurancePolicyDetailsDaoFactory.create();
		InsuranceHandlerChainReqDao insHndChainReqDao = InsuranceHandlerChainReqDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		AddressDao addressDao = AddressDaoFactory.create();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		RegionsDao regionDao = RegionsDaoFactory.create();
		PersonalInfoDao prsInfoDao = PersonalInfoDaoFactory.create();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		Users requestedUser = new Users();
		InsurancePolicyReq insPolicyReq = null;
		try{
			Login loginDto = Login.getLogin(request);
			requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
			ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
			ProcessChain processChain[] = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=62 WHERE UR.USER_ID=?", new Object[] { new Integer(loginDto.getUserId()) });
			ArrayList<Users> handlerList = null;
			ArrayList<Users> notifierList = null;
			String handlers = null, notifiers = null, notifierIds = null, handlerIds = null;
			HashSet<Integer> handlerIdsSet = null, notifierIdsSet = null;
			if (processChain[0] != null){
				handlers = processChain[0].getHandler();
				if (handlers != null && !handlers.equals("")){
					handlerList = ItModel.getUserLstByLevelIds(handlers, requesterProfile);//11 July
					if (handlerList.contains(requestedUser)){
						handlerList.remove(requestedUser);
					}
					if (handlerList != null){
						handlerIdsSet=getUserIdsFromUserList(handlerList);
						handlerIds = ModelUtiility.getCommaSeparetedValues(handlerIdsSet);
					}
				}
				notifiers = processChain[0].getNotification();
				if (notifiers != null && !notifiers.equals("")){
					notifierList = ItModel.getUserLstByLevelIds(notifiers, requesterProfile);
					if (notifierList != null){
						notifierIdsSet = getUserIdsFromUserList(notifierList);
						notifierIdsSet.removeAll(handlerIdsSet);// make shure mails should not send for same user in to ans cc.
						notifierIds = ModelUtiility.getCommaSeparetedValues(notifierIdsSet);
					}
				}
			}
			if (handlerList == null || handlerList.size() < 1){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.toProcessRequest"));
				return result;
			}
			switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
				case SAVE:{
					try{
						boolean flag = false;
						Date date = new Date();
						if (requestedUser != null){
							int reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0].getId();
							String reg_abb = regionDao.findByPrimaryKey(reg_id).getRefAbbreviation();
							if (insuranceBean.getSaveType().equalsIgnoreCase("save") || insuranceBean.getSaveType().equalsIgnoreCase("saveAndSubmit")){
								// save Data in EMP_SER_REQ_MAP TABLE
								int uniqueID = 1;
								EmpSerReqMap empMap[] = empSerReqDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=15)", null);
								if (empMap.length > 0){
									String s = empMap[0].getReqId().split("-")[2];
									uniqueID = Integer.parseInt(s) + 1;
								}
								EmpSerReqMap empReqMapDto = new EmpSerReqMap();
								empReqMapDto.setSubDate(date);
								empReqMapDto.setReqId(reg_abb + "-INS-" + uniqueID);
								empReqMapDto.setReqTypeId(15);
								empReqMapDto.setRegionId(reg_id);
								empReqMapDto.setRequestorId(loginDto.getUserId());
								empReqMapDto.setProcessChainId(processChain[0].getId());
								reqpk = empSerReqDao.insert(empReqMapDto);
								// insert into INSURANCE_POLICY_REQ
								for (int i = 0; i < insuranceBean.getNoOfPolicies() && i < insuranceBean.getNoOfInsPolicyDtl(); i++){
									InsurancePolicyReq insPolicyReq1 = new InsurancePolicyReq();
									insPolicyReq1.setEsrMapId(reqpk.getId());
									insPolicyReq1.setRequeterId(loginDto.getUserId());
									insPolicyReq1.setDeliveryAddress((insuranceBean.getDeliveryAddress() == null || insuranceBean.getDeliveryAddress() == "") ? null : (insuranceBean.getDeliveryAddress()).split("~=~").length - 1 >= i ? (insuranceBean.getDeliveryAddress()).split("~=~")[i] : null);
									insPolicyReq1.setCoverage(Integer.parseInt((insuranceBean.getCoverage() == null || insuranceBean.getCoverage() == "") ? "0" : (insuranceBean.getCoverage()).split("~=~").length - 1 >= i ? (insuranceBean.getCoverage()).split("~=~")[i] : "0"));
									insPolicyReq1.setCoverageFrom(PortalUtility.StringToDateDB((insuranceBean.getCoverageFrom() == null || insuranceBean.getCoverageFrom() == "") ? "" : (insuranceBean.getCoverageFrom()).split("~=~").length - 1 >= i ? (insuranceBean.getCoverageFrom()).split("~=~")[i] : ""));
									insPolicyReq1.setCoverageUpto(PortalUtility.StringToDateDB((insuranceBean.getCoverageUpto() == null || insuranceBean.getCoverageUpto() == "") ? "" : (insuranceBean.getCoverageUpto()).split("~=~").length - 1 >= i ? (insuranceBean.getCoverageUpto()).split("~=~")[i] : ""));
									//insPolicyReq.setCoverageUpto(PortalUtility.StringToDateDB("12-12-2011"));
									insPolicyReq1.setPolicyType((insuranceBean.getPolicyType() == null || insuranceBean.getPolicyType() == "") ? null : (insuranceBean.getPolicyType()).split("~=~").length - 1 >= i ? (insuranceBean.getPolicyType()).split("~=~")[i] : null);
									insPolicyReq1.setRequestedOn(new Date());
									insPolicyReq1.setStatus(Status.getStatus(29));
									InsurancePolicyReqPk insPlyReqPk = insPolicyReqDao.insert(insPolicyReq1);
									insPolicyReq1.setId(insPlyReqPk.getId());
									//insert into INSURANCE_POLICY_DETAILS
									InsurancePolicyDetails insPolicyDetails = new InsurancePolicyDetails();
									insPolicyDetails.setEsrMapId(insPolicyReq1.getEsrMapId());
									insPolicyDetails.setPolicyReqId(insPolicyReq1.getId());
									insPolicyDetails.setName((insuranceBean.getName() == null || insuranceBean.getName() == "") ? null : (insuranceBean.getName()).split("~=~").length - 1 >= i ? (insuranceBean.getName()).split("~=~")[i] : null);
									insPolicyDetails.setGender((insuranceBean.getGender() == null || insuranceBean.getGender() == "") ? null : (insuranceBean.getGender()).split("~=~").length - 1 >= i ? (insuranceBean.getGender()).split("~=~")[i] : null);
									insPolicyDetails.setDob(PortalUtility.StringToDateDB((insuranceBean.getDob() == null || insuranceBean.getDob() == "") ? "" : (insuranceBean.getDob()).split("~=~").length - 1 >= i ? (insuranceBean.getDob()).split("~=~")[i] : ""));
									//insPolicyDetails.setDob(new Date());
									insPolicyDetails.setAge(Integer.parseInt((insuranceBean.getAge() == null || insuranceBean.getAge() == "") ? "0" : (insuranceBean.getAge()).split("~=~").length - 1 >= i ? (insuranceBean.getAge()).split("~=~")[i] : "0"));
									insPolicyDetails.setRelationship((insuranceBean.getRelationship() == null || insuranceBean.getRelationship() == "") ? null : (insuranceBean.getRelationship()).split("~=~").length - 1 >= i ? (insuranceBean.getRelationship()).split("~=~")[i] : null);
									InsurancePolicyDetailsPk insplyDtlsPk = insPolicyDtlsDao.insert(insPolicyDetails);
									insPolicyDetails.setId(insplyDtlsPk.getId());
									if (insuranceBean.getSaveType().equalsIgnoreCase("saveAndSubmit")){
										insPolicyReq1.setStatus(Status.getStatus(25));
										insPolicyReq1.setRequestedOn(new Date());
										insPolicyReqDao.update(new InsurancePolicyReqPk(insPolicyReq1.getId()), insPolicyReq1);
										Iterator<Users> it = handlerList.iterator();
										while (it.hasNext()){
											InsuranceHandlerChainReq insHndChainReq = new InsuranceHandlerChainReq();
											Users handler = it.next();
											insHndChainReq.setEsrMapId(insPolicyReq1.getEsrMapId());
											insHndChainReq.setPolicyReqId(insPolicyReq1.getId());
											insHndChainReq.setRequesterId(insPolicyReq1.getRequeterId());
											insHndChainReq.setReceiverId(handler.getId());
											insHndChainReq.setStatus(Status.getStatus(25));
											insHndChainReq.setLastModifiedDate(new Date());
											insHndChainReq.setCreateDate(insPolicyReq1.getRequestedOn());
											InsuranceHandlerChainReqPk insHndChainReqPK = insHndChainReqDao.insert(insHndChainReq);
											insHndChainReq.setId(insHndChainReqPK.getId());
											flag = true;
										}
									}
								}
								if (flag){
									sendInsuranceNotification(empReqMapDto.getId(), requestedUser, handlerIds, notifierIds);
								}
							}
						}
					} catch (RegionsDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.regions"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (ArrayIndexOutOfBoundsException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (EmpSerReqMapDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						e.printStackTrace();
					} catch (InsurancePolicyReqDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (Exception e){
						e.printStackTrace();
						empSerReqDao.delete(reqpk);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
					}
					break;
				}// end of SAVE
				case SUBMIT:{
					try{
						EmpSerReqMap empSerReqMap = empSerReqDao.findByPrimaryKey(insuranceBean.getEsrMapId());
						InsurancePolicyReq InsPlyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empSerReqMap.getId() });
						InsurancePolicyDetails InsPlyDtls[] = insPolicyDtlsDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empSerReqMap.getId() });
						//Status.getStatusId(insPolicyReq1.getStatus());
						if (InsPlyReq != null && InsPlyDtls != null){
							if (Status.REQUESTSAVED.equals(InsPlyReq[0].getStatus())){
								for (int j = 0; j < InsPlyDtls.length && j < InsPlyReq.length; j++){
									insPolicyDtlsDao.delete(new InsurancePolicyDetailsPk(InsPlyDtls[j].getId()));
									insPolicyReqDao.delete(new InsurancePolicyReqPk(InsPlyReq[j].getId()));
								}
								for (int i = 0; i < insuranceBean.getNoOfPolicies() && i < insuranceBean.getNoOfInsPolicyDtl(); i++){
									//update Insurance policy
									InsurancePolicyReq insPolicyReq1 = new InsurancePolicyReq();
									insPolicyReq1.setEsrMapId(empSerReqMap.getId());
									insPolicyReq1.setRequeterId(loginDto.getUserId());
									insPolicyReq1.setCoverage(Integer.parseInt(insuranceBean.getCoverage().split("~=~")[i]));
									insPolicyReq1.setCoverageFrom(PortalUtility.StringToDateDB(insuranceBean.getCoverageFrom().split("~=~")[i]));
									insPolicyReq1.setCoverageUpto(PortalUtility.StringToDateDB(insuranceBean.getCoverageUpto().split("~=~")[i]));
									insPolicyReq1.setDeliveryAddress(insuranceBean.getDeliveryAddress().split("~=~")[i]);
									insPolicyReq1.setPolicyType(insuranceBean.getPolicyType().split("~=~")[i]);
									insPolicyReq1.setStatus(Status.getStatus(25));
									insPolicyReq1.setRequestedOn(new Date());
									InsurancePolicyReqPk insPlyReqPk = insPolicyReqDao.insert(insPolicyReq1);
									insPolicyReq1.setId(insPlyReqPk.getId());
									//update insurance Policy Detail
									InsurancePolicyDetails insPolicyDetails = new InsurancePolicyDetails();
									insPolicyDetails.setEsrMapId(insPolicyReq1.getEsrMapId());
									insPolicyDetails.setPolicyReqId(insPolicyReq1.getId());
									insPolicyDetails.setName(insuranceBean.getName().split("~=~")[i]);
									insPolicyDetails.setGender(insuranceBean.getGender().split("~=~")[i]);
									insPolicyDetails.setDob(PortalUtility.StringToDateDB(insuranceBean.getDob().split("~=~")[i]));
									//insPolicyDetails.setDob(new Date());
									insPolicyDetails.setAge(Integer.parseInt(insuranceBean.getAge().split("~=~")[i]));
									insPolicyDetails.setRelationship(insuranceBean.getRelationship().split("~=~")[i]);
									//InsurancePolicyDetailsPk insplyDtlsPk=insPolicyDtlsDao.insert(insPolicyDetails);
									//insPolicyDetails.setId(insplyDtlsPk.getId());
									InsurancePolicyDetailsPk insplyDtlsPk = insPolicyDtlsDao.insert(insPolicyDetails);
									insPolicyDetails.setId(insplyDtlsPk.getId());
									Iterator<Users> it = handlerList.iterator();
									while (it.hasNext()){
										InsuranceHandlerChainReq insHndChainReq = new InsuranceHandlerChainReq();
										Users handler = it.next();
										insHndChainReq.setEsrMapId(insPolicyReq1.getEsrMapId());
										insHndChainReq.setPolicyReqId(insPolicyReq1.getId());
										insHndChainReq.setRequesterId(insPolicyReq1.getRequeterId());
										insHndChainReq.setReceiverId(handler.getId());
										insHndChainReq.setStatus(Status.getStatus(25));
										insHndChainReq.setLastModifiedDate(new Date());
										insHndChainReq.setCreateDate(insPolicyReq1.getRequestedOn());
										InsuranceHandlerChainReqPk insHndChainReqPK = insHndChainReqDao.insert(insHndChainReq);
										insHndChainReq.setId(insHndChainReqPK.getId());
									}
								}
								sendInsuranceNotification(insuranceBean.getEsrMapId(), requestedUser, handlerIds, notifierIds);
							}
						} else{
							result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("request can not be submited"));
							return result;
						}
						//sendInsuranceNotification(insPolicyReq1,requestedUser,handlerIds,notifierIds);
					} catch (Exception e){}
					break;
				}// end of submit	
				case DEFAULT_INSURANCE:{
					//int noOfInsPlsDetails=1;
					boolean isMarried = false;
					String spousGender = null;
					Date spous_dob = null;
					Date emp_dob = null;
					int spous_age = 0;
					int emp_age = 0;
					String spousName = null;
					String spous_relationship = null;
					String address = null;
					String maritalStatus = null;
					try{
						Users insured_emp = usersDao.findByPrimaryKey(insuranceBean.getId());
						Date date = new Date();
						if (insured_emp != null){
							int reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(insured_emp.getId()) })[0].getId();
							String reg_abb = regionDao.findByPrimaryKey(reg_id).getRefAbbreviation();
							int personalId = insured_emp.getPersonalId();
							int profileId = insured_emp.getProfileId();
							if (profileId != 0 || personalId != 0){
								ProfileInfo prfInfo = profileInfoDao.findByPrimaryKey(profileId);
								String gender = prfInfo.getGender();
								emp_dob = prfInfo.getDob();
								emp_age = PortalUtility.getYearBetween(new Date(), emp_dob);
								ProcessChain processChain1[] = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=62 WHERE UR.USER_ID=?", new Object[] { new Integer(insured_emp.getId()) });
								ArrayList<Users> handlerList1 = null;
								ArrayList<Users> notifierList1 = null;
								String handlers1 = null, notifiers1 = null, notifierIds1 = null, handlerIds1 = null;
								if (processChain1.length > 0){
									handlers1 = processChain1[0].getHandler();
									if (handlers1 != null && !handlers1.equals("")){
										handlerList1 = ItModel.getUserLstByLevelIds(handlers1, prfInfo);//9 jan
										if (handlerList1.contains(insured_emp)){
											handlerList1.remove(insured_emp);
										}
										if (handlerList1 != null){
											handlerIds1 = ItModel.getUserIdsFromUserList(handlerList1);
										}
									}
									notifiers1 = processChain1[0].getNotification();
									if (notifiers1 != null && !notifiers1.equals("")){
										notifierList1 = ItModel.getUserLstByLevelIds(notifiers1, prfInfo);
										if (notifierList1 != null){
											notifierIds1 = ItModel.getUserIdsFromUserList(notifierList1);
										}
									}
									if (personalId != 0){
										PersonalInfo prsInfo = prsInfoDao.findByPrimaryKey(personalId);
										Address[] ad = addressDao.findByDynamicSelect("SELECT * FROM ADDRESS A Join PERSONAL_INFO PF ON PF.PERMANENT_ADDRESS = A.ID WHERE PF.ID=?", new Object[] { new Integer(prsInfo.getId()) });
										if (ad.length > 0){
											address = ad[0].getAddress() + " " + ad[0].getCity() + " " + ad[0].getState() + " " + ad[0].getCountry();
										}
										maritalStatus = prsInfo.getMaritalStatus() == null ? null : prsInfo.getMaritalStatus();
										if (maritalStatus != null){
											if (maritalStatus.equalsIgnoreCase("MARRIED")){
												//noOfInsPlsDetails = 2;
												try{
													spousGender = gender.equalsIgnoreCase("FEMALE") ? "MALE" : "FEMALE";
													spous_dob = prsInfo.getSpouseDob();
													spous_age = PortalUtility.getYearBetween(new Date(), spous_dob);
													spous_relationship = prsInfo.getEmerCpRelationship();
													spousName = prsInfo.getSpouseName();
													isMarried = true;
												} catch (Exception ex){
													ex.printStackTrace();
													result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.spous.info"));
													//return result;
												}
											}
										}
									}
									int uniqueID = 1;
									EmpSerReqMap empMap[] = empSerReqDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=15)", null);
									if (empMap.length > 0){
										String s = empMap[0].getReqId().split("-")[2];
										uniqueID = Integer.parseInt(s) + 1;
									}
									EmpSerReqMap empReqMapDto = new EmpSerReqMap();
									empReqMapDto.setSubDate(date);
									empReqMapDto.setReqId(reg_abb + "-INS-" + uniqueID);
									empReqMapDto.setReqTypeId(15);
									empReqMapDto.setRegionId(reg_id);
									empReqMapDto.setRequestorId(insured_emp.getId());
									empReqMapDto.setProcessChainId(processChain[0].getId());
									reqpk = empSerReqDao.insert(empReqMapDto);
									for (int i = 0; i < 2; i++){
										// insert into INSURANCE_POLICY_REQ
										insPolicyReq = new InsurancePolicyReq();
										insPolicyReq.setEsrMapId(reqpk.getId());
										insPolicyReq.setRequeterId(insured_emp.getId());
										insPolicyReq.setCoverage(Integer.parseInt(insuranceBean.getCoverage().split("~=~")[i]));
										insPolicyReq.setCoverageFrom(PortalUtility.StringToDateDB(insuranceBean.getCoverageFrom().split("~=~")[i]));
										insPolicyReq.setCoverageUpto(PortalUtility.StringToDateDB(insuranceBean.getCoverageUpto().split("~=~")[i]));
										//insPolicyReq.setCoverageUpto(PortalUtility.StringToDateDB("12-12-2011"));
										insPolicyReq.setDeliveryAddress(address == null ? "" : address);
										insPolicyReq.setPolicyType(insuranceBean.getPolicyType().split("~=~")[i]);
										insPolicyReq.setPolicyNumber(insuranceBean.getPolicyNumber());
										insPolicyReq.setServiceTax(Integer.parseInt(insuranceBean.getServiceTax().split("~=~")[i]));
										insPolicyReq.setBasicPremium(Integer.parseInt(insuranceBean.getBasicPremium().split("~=~")[i]));
										insPolicyReq.setTotalPremium(Integer.parseInt(insuranceBean.getTotalPremium().split("~=~")[i]));
										insPolicyReq.setRequestedOn(new Date());
										insPolicyReq.setDateOfCompletion(new Date());
										insPolicyReq.setStatus(Status.getStatus(7));
										InsurancePolicyReqPk insPlyReqPk = insPolicyReqDao.insert(insPolicyReq);
										insPolicyReq.setId(insPlyReqPk.getId());
										InsurancePolicyDetails insPolicyDetails1 = new InsurancePolicyDetails();
										insPolicyDetails1.setEsrMapId(insPolicyReq.getEsrMapId());
										insPolicyDetails1.setName(prfInfo.getFirstName() + " " + prfInfo.getLastName());
										insPolicyDetails1.setGender(prfInfo.getGender());
										insPolicyDetails1.setDob(prfInfo.getDob());
										//insPolicyDetails.setDob(new Date());
										insPolicyDetails1.setAge(emp_age);
										insPolicyDetails1.setPolicyReqId(insPolicyReq.getId());
										insPolicyDetails1.setRelationship("SELF");
										insPolicyDetails1.setCardId(insuranceBean.getCardId().split("~=~")[i]);
										InsurancePolicyDetailsPk insplyDtlsPk1 = insPolicyDtlsDao.insert(insPolicyDetails1);
										insPolicyDetails1.setId(insplyDtlsPk1.getId());
										if (isMarried){
											InsurancePolicyReq insPolicyReq1 = new InsurancePolicyReq();
											insPolicyReq1.setEsrMapId(reqpk.getId());
											insPolicyReq1.setRequeterId(insured_emp.getId());
											insPolicyReq1.setCoverage(Integer.parseInt(insuranceBean.getCoverage().split("~=~")[i + 2]));
											insPolicyReq1.setCoverageFrom(PortalUtility.StringToDateDB(insuranceBean.getCoverageFrom().split("~=~")[i + 2]));
											insPolicyReq1.setCoverageUpto(PortalUtility.StringToDateDB(insuranceBean.getCoverageUpto().split("~=~")[i + 2]));
											insPolicyReq1.setDeliveryAddress(address == null ? "" : address);
											insPolicyReq1.setPolicyType(insuranceBean.getPolicyType().split("~=~")[i + 2]);
											insPolicyReq1.setStatus(Status.getStatus(25));
											insPolicyReq1.setRequestedOn(new Date());
											insPolicyReq1.setDateOfCompletion(new Date());
											insPolicyReq1.setPolicyNumber(insuranceBean.getPolicyNumber());
											insPolicyReq1.setServiceTax(Integer.parseInt(insuranceBean.getServiceTax().split("~=~")[i + 2]));
											insPolicyReq1.setBasicPremium(Integer.parseInt(insuranceBean.getBasicPremium().split("~=~")[i + 2]));
											insPolicyReq1.setTotalPremium(Integer.parseInt(insuranceBean.getTotalPremium().split("~=~")[i + 2]));
											insPolicyReq1.setStatus(Status.getStatus(7));
											InsurancePolicyReqPk insPlyReqPk1 = insPolicyReqDao.insert(insPolicyReq1);
											insPolicyReq.setId(insPlyReqPk1.getId());
											InsurancePolicyDetails insPolicyDetails2 = new InsurancePolicyDetails();
											insPolicyDetails2.setEsrMapId(insPolicyReq1.getEsrMapId());
											insPolicyDetails2.setName(spousName);
											insPolicyDetails2.setGender(spousGender);
											insPolicyDetails2.setDob(spous_dob);
											//insPolicyDetails.setDob(new Date());
											insPolicyDetails2.setAge(spous_age);
											insPolicyDetails2.setPolicyReqId(insPolicyReq1.getId());
											insPolicyDetails2.setRelationship(spous_relationship);
											insPolicyDetails1.setCardId(insuranceBean.getCardId().split("~=~")[i + 2]);
											InsurancePolicyDetailsPk insplyDtlsPk2 = insPolicyDtlsDao.insert(insPolicyDetails2);
											insPolicyDetails2.setId(insplyDtlsPk2.getId());
											Iterator<Users> it = handlerList1.iterator();
											while (it.hasNext()){
												InsuranceHandlerChainReq insHndChainReq = new InsuranceHandlerChainReq();
												Users handler = it.next();
												insHndChainReq.setEsrMapId(insPolicyReq1.getEsrMapId());
												insHndChainReq.setRequesterId(insured_emp.getId());
												insHndChainReq.setPolicyReqId(insPolicyReq1.getId());
												insHndChainReq.setReceiverId(handler.getId());
												insHndChainReq.setStatus(Status.getStatus(7));
												insHndChainReq.setAssignTo(requestedUser.getId());
												insHndChainReq.setLastModifiedDate(new Date());
												insHndChainReq.setCreateDate(insPolicyReq1.getRequestedOn());
												InsuranceHandlerChainReqPk insHndChainReqPK = insHndChainReqDao.insert(insHndChainReq);
												insHndChainReq.setId(insHndChainReqPK.getId());
											}
										}
										Iterator<Users> it = handlerList1.iterator();
										while (it.hasNext()){
											InsuranceHandlerChainReq insHndChainReq = new InsuranceHandlerChainReq();
											Users handler = it.next();
											insHndChainReq.setEsrMapId(insPolicyReq.getEsrMapId());
											insHndChainReq.setRequesterId(insured_emp.getId());
											insHndChainReq.setPolicyReqId(insPolicyReq.getId());
											insHndChainReq.setReceiverId(handler.getId());
											insHndChainReq.setStatus(Status.getStatus(7));
											insHndChainReq.setAssignTo(requestedUser.getId());
											insHndChainReq.setLastModifiedDate(new Date());
											insHndChainReq.setCreateDate(insPolicyReq.getRequestedOn());
											InsuranceHandlerChainReqPk insHndChainReqPK = insHndChainReqDao.insert(insHndChainReq);
											insHndChainReq.setId(insHndChainReqPK.getId());
										}
									}
									sendInsuranceFinalNotification(reqpk.getId(), insured_emp, handlerIds1, notifierIds1, requestedUser.getId());
								} else{
									result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.config.processchain"));
								}
							} else{
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.missing.record"));
							}
						}
					} catch (Exception ex){
						ex.printStackTrace();
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
					}
					break;
				}
				// end of default insurance
			} // end of switch
		} catch (ArrayIndexOutOfBoundsException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
		}
		return result;
	}

	private void sendInsuranceNotification(int insEsrId, Users requestedUser, String handlerIds, String notifierIds) {
		try{
			EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			InboxModel inboxModel = new InboxModel();
			ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
			Levels empLevel = levelsDao.findByPrimaryKey(requestedUser.getLevelId());
			Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
			String empDesignation = empLevel.getDesignation();
			String empDivision = requesterDivision.getName();
			String empDepartment = null;
			if (requesterDivision.getParentId() == 0){
				empDepartment = requesterDivision.getName();
			} else{
				empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
			}
			EmpSerReqMap empMap = empSerReqDao.findByPrimaryKey(insEsrId);
			//send mail to requester
			PortalMail portalMail = new PortalMail();
			MailGenerator mailGenerator = new MailGenerator();
			String mailSubject = "New Insurance Request Submitted " + empMap.getReqId();
			String mailSubjectForReqUsrInb = "New Insurance Request " + empMap.getReqId() + "-Acknoledgement";
			portalMail.setRecipientMailId(requesterProfile.getOfficalEmailId());
			portalMail.setMailSubject(mailSubject);
			portalMail.setEmpFname(requesterProfile.getFirstName());
			portalMail.setTemplateName(MailSettings.INSURANCE_NOTIFICATION_REQUESTER_SUBMITTED);
			portalMail.setAutoIssueReqId(empMap.getReqId());
			if (notifierIds != null){
				portalMail.setAllReceipientcCMailId(ItModel.fetchOfficialMailIds(notifierIds));
			}
			mailGenerator.invoker(portalMail);
			//send mails to handlers & CC to notifiers
			mailSubject = "New Insurance Request Submitted " + empMap.getReqId() + " by " + requesterProfile.getFirstName();
			portalMail = new PortalMail();
			portalMail.setMailSubject(mailSubject);
			portalMail.setTemplateName(MailSettings.INSURANCE_NOTIFICATION_HANDLER_SUBMITTED);
			portalMail.setAutoIssueReqId(empMap.getReqId());
			portalMail.setEmpFname(requesterProfile.getFirstName());
			portalMail.setIssueSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()));
			portalMail.setRequestorEmpId(requestedUser.getEmpId());
			portalMail.setRequestorFirstName(requesterProfile.getFirstName());
			portalMail.setRequestorLastName(requesterProfile.getLastName());
			portalMail.setEmpDesignation(empDesignation);
			portalMail.setEmpDepartment(empDepartment);
			portalMail.setEmpDivision(empDivision);
			portalMail.setAllReceipientMailId(ItModel.fetchOfficialMailIds(handlerIds));
			if (notifierIds != null){
				portalMail.setAllReceipientcCMailId(ItModel.fetchOfficialMailIds(notifierIds));
			}
			mailGenerator.invoker(portalMail);
			//populate inbox entry for handlers
			String mailBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.INSURANCE_NOTIFICATION_HANDLER_SUBMITTED), portalMail);
			inboxModel.populateInboxFromInsurance(empMap.getId(), new Integer(requestedUser.getId()).toString(), mailSubjectForReqUsrInb, mailBody);
			inboxModel.populateInboxFromInsurance(empMap.getId(), handlerIds, mailSubject, mailBody);
		} catch (EmpSerReqMapDaoException e){
			e.printStackTrace();
		} catch (AddressException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (MessagingException e){
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			e.printStackTrace();
		} catch (LevelsDaoException e){
			e.printStackTrace();
		} catch (DivisonDaoException e){
			e.printStackTrace();
		}
	}

	private void sendInsuranceFinalNotification(int insEsrId, Users requestedUser, String handlerIds, String notifierIds, int assignHndId) {
		try{
			EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			InboxDao inboxDao = InboxDaoFactory.create();
			InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
			String resolvedOn = "";
			String subRequestInfo = "";
			ProfileInfo assignHndProfile;
			String hndAndNotyIds = handlerIds + "," + notifierIds;
			Users assignHndUser = usersDao.findByPrimaryKey(assignHndId);
			assignHndProfile = profileInfoDao.findByPrimaryKey(assignHndUser.getProfileId());
			ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
			InsurancePolicyReq InsPlyReq[];
			InsPlyReq = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insEsrId });
			if (InsPlyReq.length >= 1){
				resolvedOn = InsPlyReq[0].getDateOfCompletion().toString();
				String[] resolvedOnArray = resolvedOn.split("-");
				resolvedOn = resolvedOnArray[2].split(" ")[0] + "-" + resolvedOnArray[1] + "-" + resolvedOnArray[0];
				for (int i = 0; i < InsPlyReq.length; i++){
					String reqStatus = InsPlyReq[i].getStatus();
					String policyType = InsPlyReq[i].getPolicyType();
					subRequestInfo += "<div> Policy Type  :" + policyType + "<br>RequestStatus : " + reqStatus + " </div>";
				}
			}
			Levels empLevel = levelsDao.findByPrimaryKey(requestedUser.getLevelId());
			Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
			String empDesignation = empLevel.getDesignation();
			String empDivision = requesterDivision.getName();
			String empDepartment = null;
			if (requesterDivision.getParentId() == 0){
				empDepartment = requesterDivision.getName();
			} else{
				empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
			}
			EmpSerReqMap empMap = empSerReqDao.findByPrimaryKey(insEsrId);
			//send mail to requester
			PortalMail portalMail = new PortalMail();
			MailGenerator mailGenerator = new MailGenerator();
			String mailSubject = "New Insurance Request " + empMap.getReqId() + " -Completed ";
			portalMail.setRecipientMailId(requesterProfile.getOfficalEmailId());
			portalMail.setMailSubject(mailSubject);
			portalMail.setEmpFname(requesterProfile.getFirstName());
			portalMail.setReqId(empMap.getReqId());
			portalMail.setRequestorEmpId(requestedUser.getEmpId());
			portalMail.setRequestorFirstName(requesterProfile.getFirstName());
			portalMail.setRequestorLastName(requesterProfile.getLastName());
			portalMail.setEmpDesignation(empDesignation);
			portalMail.setEmpDepartment(empDepartment);
			portalMail.setEmpDivision(empDivision);
			portalMail.setInsuranceRequestInfo(subRequestInfo);
			portalMail.setHndName(assignHndProfile.getFirstName());
			portalMail.setTemplateName(MailSettings.INSURANCE_NOTIFICATION_REQUESTER_COMPLETED);
			portalMail.setAutoIssueReqId(empMap.getReqId());
			portalMail.setResolvedOn(resolvedOn == "" ? new Date().toString() : resolvedOn);
			//send mails to handlers & CC to notifiers
			if (handlerIds != null){
				portalMail.setAllReceipientcCMailId(ItModel.fetchOfficialMailIds(hndAndNotyIds));
			}
			mailGenerator.invoker(portalMail);
			//populate inbox entry for handlers
			String mailBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.INSURANCE_NOTIFICATION_REQUESTER_COMPLETED), portalMail);
			Inbox[] inbox;
			inbox = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RAISED_BY=?", new Object[] { new Integer(insEsrId), InsPlyReq[0].getRequeterId() });
			if (inbox.length > 0){
				//InboxPk inboxPk = new InboxPk();
				for (Inbox inbox1 : inbox){
					logger.error("****************deleting inbox entry******************");
					//ModelUtiility.getInstance().deleteInboxEntries(insEsrId);
					if (inbox1.getReceiverId() != inbox1.getRaisedBy()){
						inboxDao.delete(new InboxPk(inbox1.getId()));
					} else{
						logger.error("****************changing inbox status for requested user******************");
						inbox1.setSubject(mailSubject);
						inbox1.setMessageBody(mailBody);
						inbox1.setStatus(getOverAllStatus(InsPlyReq));
						inbox1.setAssignedTo(assignHndUser.getId());
						inboxDao.update(new InboxPk(inbox1.getId()), inbox1);
					}
				}
			}
		} catch (InboxDaoException e){
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			e.printStackTrace();
		} catch (InsurancePolicyReqDaoException e){
			e.printStackTrace();
		} catch (UsersDaoException e){
			e.printStackTrace();
		} catch (AddressException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (MessagingException e){
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			e.printStackTrace();
		} catch (LevelsDaoException e){
			e.printStackTrace();
		} catch (DivisonDaoException e){
			e.printStackTrace();
		}
	}

	private void sendAssignedHndNotification(int insEsrId, Users requestedUser, Users assignedHandler, String handlerIds, String Comment) {
		try{
			EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			InboxDao inboxDao = InboxDaoFactory.create();
			InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
			String requestedOn = "";
			InsurancePolicyReq InsPlyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insEsrId });
			if (InsPlyReq.length >= 1){
				requestedOn = InsPlyReq[0].getRequestedOn().toString();
				String[] requestedOnArray = requestedOn.split("-");
				requestedOn = requestedOnArray[2].split(" ")[0] + "-" + requestedOnArray[1] + "-" + requestedOnArray[0];
			}
			ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
			ProfileInfo assignedHandlerProfile = profileInfoDao.findByPrimaryKey(assignedHandler.getProfileId());
			Levels empLevel = levelsDao.findByPrimaryKey(requestedUser.getLevelId());
			Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
			String empDesignation = empLevel.getDesignation();
			String empDivision = requesterDivision.getName();
			String empDepartment = null;
			if (requesterDivision.getParentId() == 0){
				empDepartment = requesterDivision.getName();
			} else{
				empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
			}
			EmpSerReqMap empMap = empSerReqDao.findByPrimaryKey(insEsrId);
			//send mail to requester
			PortalMail portalMail = new PortalMail();
			MailGenerator mailGenerator = new MailGenerator();
			//send mails to handlers & CC to notifiers
			String mailSubject = "Insurance Request " + empMap.getReqId() + " Assigned To  " + assignedHandlerProfile.getFirstName();
			portalMail = new PortalMail();
			portalMail.setMailSubject(mailSubject);
			portalMail.setTemplateName(MailSettings.INSURANCE_NOTIFICATION_HANDLER_ASSIGNED);
			portalMail.setAutoIssueReqId(empMap.getReqId());
			portalMail.setRecipientMailId(assignedHandlerProfile.getOfficalEmailId());
			portalMail.setEmpFname(assignedHandlerProfile.getFirstName());
			portalMail.setReqId(empMap.getReqId());
			portalMail.setIssueSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()));
			portalMail.setRequestorEmpId(requestedUser.getEmpId());
			portalMail.setRequestorFirstName(requesterProfile.getFirstName());
			portalMail.setRequestorLastName(requesterProfile.getLastName());
			portalMail.setEmpDesignation(empDesignation);
			portalMail.setEmpDepartment(empDepartment);
			portalMail.setEmpDivision(empDivision);
			portalMail.setComment(Comment);
			portalMail.setRequestedOn(requestedOn == "" ? new Date().toString() : requestedOn);
			if (handlerIds != null){
				portalMail.setAllReceipientcCMailId(ItModel.fetchOfficialMailIds(handlerIds));
			}
			mailGenerator.invoker(portalMail);
			String mailBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.INSURANCE_NOTIFICATION_HANDLER_ASSIGNED), portalMail);
			Inbox[] inbox;
			inbox = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RAISED_BY=?", new Object[] { new Integer(insEsrId), requestedUser.getId() });
			if (inbox.length > 0){
				//InboxPk inboxPk = new InboxPk();
				for (Inbox inbox1 : inbox){
					logger.error("****************changing inbox status******************");
					inbox1.setSubject(mailSubject);
					inbox1.setMessageBody(mailBody);
					inbox1.setStatus("Docked by " + assignedHandlerProfile.getFirstName() + " " + assignedHandlerProfile.getLastName());
					inbox1.setAssignedTo(assignedHandler.getId());
					inboxDao.update(new InboxPk(inbox1.getId()), inbox1);
				}
			}
		} catch (InboxDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			e.printStackTrace();
		} catch (InsurancePolicyReqDaoException e){
			e.printStackTrace();
		} catch (AddressException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (MessagingException e){
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			e.printStackTrace();
		} catch (LevelsDaoException e){
			e.printStackTrace();
		} catch (DivisonDaoException e){
			e.printStackTrace();
		}
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		InsuranceBean insuranceBean = (InsuranceBean) form;
		InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
		InsurancePolicyDetailsDao insPolicyDtlsDao = InsurancePolicyDetailsDaoFactory.create();
		InsuranceHandlerChainReqDao insHndChainReqDao = InsuranceHandlerChainReqDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		Users requestedUser = new Users();
		switch (ActionMethods.UpdateTypes.getValue(form.getuType())) {
			case UPDATEINSURANCE:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					//String reqId=empMap[i].getReqId();
					EmpSerReqMap empSerReqMap = empSerReqDao.findByPrimaryKey(insuranceBean.getEsrMapId());
					InsurancePolicyReq InsPlyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empSerReqMap.getId() });
					InsurancePolicyDetails InsPlyDtls[] = insPolicyDtlsDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { empSerReqMap.getId() });
					if (requestedUser != null && InsPlyReq.length >= 1 && InsPlyDtls.length >= 1){
						for (int i = 0; i < insuranceBean.getNoOfPolicies() && i < insuranceBean.getNoOfInsPolicyDtl(); i++){
							InsurancePolicyReq insPolicyReq1 = new InsurancePolicyReq();
							insPolicyReq1.setEsrMapId(empSerReqMap.getId());
							insPolicyReq1.setRequeterId(loginDto.getUserId());
							insPolicyReq1.setDeliveryAddress((insuranceBean.getDeliveryAddress() == null || insuranceBean.getDeliveryAddress() == "") ? null : (insuranceBean.getDeliveryAddress()).split("~=~").length - 1 >= i ? (insuranceBean.getDeliveryAddress()).split("~=~")[i] : null);
							insPolicyReq1.setCoverage(Integer.parseInt((insuranceBean.getCoverage() == null || insuranceBean.getCoverage() == "") ? "0" : (insuranceBean.getCoverage()).split("~=~").length - 1 >= i ? (insuranceBean.getCoverage()).split("~=~")[i] : "0"));
							insPolicyReq1.setCoverageFrom(PortalUtility.StringToDateDB((insuranceBean.getCoverageFrom() == null || insuranceBean.getCoverageFrom() == "") ? "" : (insuranceBean.getCoverageFrom()).split("~=~").length - 1 >= i ? (insuranceBean.getCoverageFrom()).split("~=~")[i] : ""));
							insPolicyReq1.setCoverageUpto(PortalUtility.StringToDateDB((insuranceBean.getCoverageUpto() == null || insuranceBean.getCoverageUpto() == "") ? "" : (insuranceBean.getCoverageUpto()).split("~=~").length - 1 >= i ? (insuranceBean.getCoverageUpto()).split("~=~")[i] : ""));
							//insPolicyReq.setCoverageUpto(PortalUtility.StringToDateDB("12-12-2011"));
							insPolicyReq1.setPolicyType((insuranceBean.getPolicyType() == null || insuranceBean.getPolicyType() == "") ? null : (insuranceBean.getPolicyType()).split("~=~").length - 1 >= i ? (insuranceBean.getPolicyType()).split("~=~")[i] : null);
							insPolicyReq1.setRequestedOn(new Date());
							insPolicyReq1.setStatus(Status.getStatus(29));
							if (InsPlyReq.length >= (i + 1)){
								insPolicyReq1.setId(InsPlyReq[i].getId());
								insPolicyReqDao.update(insPolicyReq1.createPk(), insPolicyReq1);
							} else{
								InsurancePolicyReqPk insPlyReqPk = insPolicyReqDao.insert(insPolicyReq1);
								insPolicyReq1.setId(insPlyReqPk.getId());
							}
							//insert into INSURANCE_POLICY_DETAILS
							InsurancePolicyDetails insPolicyDetails = new InsurancePolicyDetails();
							insPolicyDetails.setEsrMapId(insPolicyReq1.getEsrMapId());
							insPolicyDetails.setPolicyReqId(insPolicyReq1.getId());
							insPolicyDetails.setName((insuranceBean.getName() == null || insuranceBean.getName() == "") ? null : (insuranceBean.getName()).split("~=~").length - 1 >= i ? (insuranceBean.getName()).split("~=~")[i] : null);
							insPolicyDetails.setGender((insuranceBean.getGender() == null || insuranceBean.getGender() == "") ? null : (insuranceBean.getGender()).split("~=~").length - 1 >= i ? (insuranceBean.getGender()).split("~=~")[i] : null);
							insPolicyDetails.setDob(PortalUtility.StringToDateDB((insuranceBean.getDob() == null || insuranceBean.getDob() == "") ? "" : (insuranceBean.getDob()).split("~=~").length - 1 >= i ? (insuranceBean.getDob()).split("~=~")[i] : ""));
							//insPolicyDetails.setDob(new Date());
							insPolicyDetails.setAge(Integer.parseInt((insuranceBean.getAge() == null || insuranceBean.getAge() == "") ? "0" : (insuranceBean.getAge()).split("~=~").length - 1 >= i ? (insuranceBean.getAge()).split("~=~")[i] : "0"));
							insPolicyDetails.setRelationship((insuranceBean.getRelationship() == null || insuranceBean.getRelationship() == "") ? null : (insuranceBean.getRelationship()).split("~=~").length - 1 >= i ? (insuranceBean.getRelationship()).split("~=~")[i] : null);
							insPolicyDtlsDao.insert(insPolicyDetails);
							if (InsPlyDtls.length >= (i + 1)){
								insPolicyDetails.setId(InsPlyDtls[i].getId());
								insPolicyDtlsDao.update(insPolicyDetails.createPk(), insPolicyDetails);
							} else{
								insPolicyDtlsDao.insert(insPolicyDetails);
							}
						}
						for (int j = insuranceBean.getNoOfPolicies(); j < InsPlyDtls.length && j < InsPlyReq.length; j++){
							insPolicyDtlsDao.delete(new InsurancePolicyDetailsPk(InsPlyDtls[j].getId()));
							insPolicyReqDao.delete(new InsurancePolicyReqPk(InsPlyReq[j].getId()));
						}
					}
				} catch (InsurancePolicyReqDaoException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
					try{
						empSerReqDao.delete(reqpk);
					} catch (EmpSerReqMapDaoException e1){
						e1.printStackTrace();
					}
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
					try{
						empSerReqDao.delete(reqpk);
					} catch (EmpSerReqMapDaoException e1){
						e1.printStackTrace();
					}
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
				}
				break;
			}
			case ACCEPTREJECT:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					int assignedHndId = 0;
					int a = 0;
					int r = 0;
					//InsurancePolicyReq insPolicyReq1 = insPolicyReqDao.findByPrimaryKey(insuranceBean.getId());
					InsurancePolicyReq InsPlyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insuranceBean.getEsrMapId() });
					String approvedReqId = insuranceBean.getApprovedReqIds();
					String rejectedReqId = insuranceBean.getRejectedReqIds();
					if (InsPlyReq.length >= 1){
						Users insuranceRequester = usersDao.findByPrimaryKey(InsPlyReq[0].getRequeterId());
						ProfileInfo insuranceRequesterProfile = profileInfoDao.findByPrimaryKey(insuranceRequester.getProfileId());
						ProcessChain processChain[] = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=62 WHERE UR.USER_ID=?", new Object[] { new Integer(insuranceRequester.getId()) });
						ArrayList<Users> handlerList = null;
						ArrayList<Users> notifierList = null;
						String handlers = null, notifiers = null, notifierIds = null, handlerIds = null;
						if (processChain[0] != null){
							handlers = processChain[0].getHandler();
							if (handlers != null && !handlers.equals("")){
								handlerList = ItModel.getUserLstByLevelIds(handlers, insuranceRequesterProfile);//11 July
								if (handlerList.contains(insuranceRequester)){
									handlerList.remove(insuranceRequester);
								}
								if (handlerList != null){
									handlerIds = ItModel.getUserIdsFromUserList(handlerList);
								}
							}
							notifiers = processChain[0].getNotification();
							if (notifiers != null && !notifiers.equals("")){
								notifierList = ItModel.getUserLstByLevelIds(notifiers, insuranceRequesterProfile);
								if (notifierList != null){
									notifierIds = ItModel.getUserIdsFromUserList(notifierList);
								}
							}
						}
						for (int i = 0; i < InsPlyReq.length; i++){
							if (!Status.REJECTED.equals(InsPlyReq[i].getStatus()) && !Status.OFFERAPPROVED.equals(InsPlyReq[i].getStatus())){
								//InsurancePolicyReq insPolicyReq = InsPlyReq[i];
								int reqestId = InsPlyReq[i].getId();
								for (int j = 0; j < approvedReqId.split("~=~").length || j < rejectedReqId.split("~=~").length; j++){
									int id1 = Integer.parseInt(approvedReqId.split("~=~").length - 1 >= j ? (approvedReqId.split("~=~")[j] == "" ? "0" : approvedReqId.split("~=~")[j]) : "0");
									//int id1 = Integer.parseInt(approvedReqId.split("~=~")[j]);
									int id2 = Integer.parseInt(rejectedReqId.split("~=~").length - 1 >= j ? (rejectedReqId.split("~=~")[j] == "" ? "0" : rejectedReqId.split("~=~")[j]) : "0");
									if (id1 != 0){
										if (id1 == reqestId){
											InsurancePolicyReq insPolicyReq = InsPlyReq[i];
											//InsurancePolicyReq insPolicyReq1 = insPolicyReqDao.findByPrimaryKey(insuranceBean.getId());
											InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { insuranceBean.getEsrMapId(), insPolicyReq.getId() });
											if (!InsHndChainReqs[0].isAssignToNull() && requestedUser.getId() == InsHndChainReqs[0].getAssignTo()){
												assignedHndId = InsHndChainReqs[0].getAssignTo();
												insPolicyReq.setPolicyNumber(insuranceBean.getPolicyNumber());
												insPolicyReq.setServiceTax(Integer.parseInt(insuranceBean.getServiceTax().split("~=~").length - 1 >= a ? insuranceBean.getServiceTax().split("~=~")[a] : "0"));
												insPolicyReq.setBasicPremium(Integer.parseInt(insuranceBean.getBasicPremium().split("~=~").length - 1 >= a ? insuranceBean.getBasicPremium().split("~=~")[a] : "0"));
												insPolicyReq.setTotalPremium(Integer.parseInt(insuranceBean.getTotalPremium().split("~=~").length - 1 >= a ? insuranceBean.getTotalPremium().split("~=~")[a] : "0"));
												insPolicyReq.setStatus(Status.getStatus(7));
												insPolicyReq.setDateOfCompletion(new Date());
												insPolicyReqDao.update(new InsurancePolicyReqPk(insPolicyReq.getId()), insPolicyReq);
												//insPolicyReq.setPolicyNumber(insuranceBean.getPolicyNumber().split("~=~").length - 1 >= a ? insuranceBean.getPolicyNumber().split("~=~")[a] : null);
												JDBCUtiility.getInstance().update("UPDATE INSURANCE_POLICY_DETAILS SET CARD_ID=? WHERE POLICY_REQ_ID=?", new Object[] { (insuranceBean.getCardId().split("~=~").length - 1 >= a ? insuranceBean.getCardId().split("~=~")[a] : null), insPolicyReq.getId() });
												for (int k = 0; k < InsHndChainReqs.length; k++){
													InsuranceHandlerChainReq InsHndChainReq = InsHndChainReqs[k];
													//InsHndChainReq.setComments((insuranceBean.getComments()== null? null : insuranceBean.getComments()) );
													InsHndChainReq.setStatus(Status.getStatus(7));
													InsHndChainReq.setLastModifiedDate(new Date());
													insHndChainReqDao.update(new InsuranceHandlerChainReqPk(InsHndChainReq.getId()), InsHndChainReq);
												}
												a++;
											} else{
												result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("request can not be approved"));
												return result;
											}
										}
									}
									if (id2 != 0){
										if (id2 == reqestId){
											InsurancePolicyReq insPolicyReq = InsPlyReq[i];
											InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { insuranceBean.getEsrMapId(), insPolicyReq.getId() });
											if (!InsHndChainReqs[0].isAssignToNull() && requestedUser.getId() == InsHndChainReqs[0].getAssignTo()){
												assignedHndId = InsHndChainReqs[0].getAssignTo();
												insPolicyReq.setStatus(Status.getStatus(3));
												insPolicyReq.setDateOfCompletion(new Date());
												insPolicyReqDao.update(new InsurancePolicyReqPk(insPolicyReq.getId()), insPolicyReq);
												for (int k = 0; k < InsHndChainReqs.length; k++){
													InsuranceHandlerChainReq InsHndChainReq = InsHndChainReqs[k];
													InsHndChainReq.setComments((insuranceBean.getComments() == null ? null : insuranceBean.getComments().split("~=~").length - 1 >= r ? insuranceBean.getComments().split("~=~")[r] : null));
													InsHndChainReq.setStatus(Status.getStatus(3));
													InsHndChainReq.setLastModifiedDate(new Date());
													insHndChainReqDao.update(new InsuranceHandlerChainReqPk(InsHndChainReq.getId()), InsHndChainReq);
												}
												r++;
											} else{
												result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("request can not be approved"));
												return result;
											}
										}
									}
								}
							}
						}
						sendInsuranceFinalNotification(insuranceBean.getEsrMapId(), insuranceRequester, handlerIds, notifierIds, assignedHndId);
						//deleting inbox entry
						Inbox[] inbox = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RAISED_BY=?", new Object[] { insuranceBean.getEsrMapId(), InsPlyReq[0].getRequeterId() });
						if (inbox.length > 0){
							//InboxPk inboxPk = new InboxPk();
							for (Inbox inbox1 : inbox){
								logger.error("****************deleting inbox entry******************");
								//inboxDao.delete(new InboxPk(inbox1.getId()));
								ModelUtiility.getInstance().deleteInboxEntries(insuranceBean.getEsrMapId());
							}
						}
					} else{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("this user can not handle the request"));
						return result;
					}
				} catch (ArrayIndexOutOfBoundsException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
				}
				break;
			}
			case INSURANCEREJECT:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					//InsurancePolicyReq insPolicyReq1 = insPolicyReqDao.findByPrimaryKey(insuranceBean.getId());
					InsurancePolicyReq InsPlyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insuranceBean.getEsrMapId() });
					if (InsPlyReq.length >= 1){
						for (int i = 0; i < InsPlyReq.length; i++){
							if (!Status.OFFERAPPROVED.equals(InsPlyReq[i].getStatus())){
								InsurancePolicyReq insPolicyReq = InsPlyReq[i];
								InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { insuranceBean.getEsrMapId(), insPolicyReq.getId() });
								if (!InsHndChainReqs[0].isAssignToNull() && requestedUser.getId() == InsHndChainReqs[0].getAssignTo()){
									insPolicyReq.setStatus(Status.getStatus(3));
									insPolicyReq.setRequestedOn(new Date());
									insPolicyReqDao.update(new InsurancePolicyReqPk(insPolicyReq.getId()), insPolicyReq);
									for (int k = 0; k < InsHndChainReqs.length; k++){
										InsuranceHandlerChainReq InsHndChainReq = InsHndChainReqs[k];
										InsHndChainReq.setComments((insuranceBean.getComments() == null ? null : insuranceBean.getComments()));
										InsHndChainReq.setStatus(Status.getStatus(3));
										insHndChainReqDao.update(new InsuranceHandlerChainReqPk(InsHndChainReq.getId()), InsHndChainReq);
									}
								} else{
									result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("request can not be approved"));
									return result;
								}
							}
						} //sendInsuranceNotification(insuranceBean.getEsrMapId(),requestedUser,handlerIds,notifierIds);
					} else{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("this user can not handle the request"));
						return result;
					}
				} catch (Exception ex){}
				break;
			}
			case INSURANCEONHOLD:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					InsurancePolicyReq insPolicyReq1 = insPolicyReqDao.findByPrimaryKey(insuranceBean.getId());
					InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insPolicyReq1.getEsrMapId() });
					if (InsHndChainReqs[0].isAssignToNull() || requestedUser.getId() == InsHndChainReqs[0].getAssignTo()){
						if (!(Status.ACCEPTED.equals(insPolicyReq1.getStatus())) && !(Status.REJECTED.equals(insPolicyReq1.getStatus()))){
							insPolicyReq1.setCoverage(Integer.parseInt(insuranceBean.getCoverage()));
							insPolicyReq1.setCoverageFrom(PortalUtility.StringToDateDB(insuranceBean.getCoverageFrom()));
							insPolicyReq1.setCoverageUpto(PortalUtility.StringToDateDB(insuranceBean.getCoverageUpto()));
							insPolicyReq1.setDeliveryAddress(insuranceBean.getDeliveryAddress());
							insPolicyReq1.setPolicyType(insuranceBean.getPolicyType());
							insPolicyReq1.setPolicyNumber(insuranceBean.getPolicyNumber());
							insPolicyReq1.setServiceTax(Integer.parseInt(insuranceBean.getServiceTax()));
							insPolicyReq1.setBasicPremium(Integer.parseInt(insuranceBean.getBasicPremium()));
							insPolicyReq1.setTotalPremium(Integer.parseInt(insuranceBean.getTotalPremium()));
							insPolicyReq1.setStatus(Status.getStatus(11));
							insPolicyReq1.setRequestedOn(new Date());
							insPolicyReqDao.update(new InsurancePolicyReqPk(insPolicyReq1.getId()), insPolicyReq1);
							for (int k = 0; k < InsHndChainReqs.length; k++){
								InsuranceHandlerChainReq InsHndChainReq = InsHndChainReqs[k];
								//InsHndChainReq.setComments(insuranceBean.getComments().split(";;")[k]);
								InsHndChainReq.setComments(insuranceBean.getComments().split(";;")[k] == null ? null : insuranceBean.getComments().split(";;")[k]);
								InsHndChainReq.setStatus(Status.getStatus(11));
								insHndChainReqDao.update(new InsuranceHandlerChainReqPk(InsHndChainReq.getId()), InsHndChainReq);
							}
						} else{
							result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("request can not be on hold"));
							return result;
						}
					} else{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("this user can not handle the request"));
						return result;
					}
				} catch (ArrayIndexOutOfBoundsException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
				}
				break;
			}
			case INSURANCESIBLINGSLIST:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					InsurancePolicyReq insPolicyReq1 = insPolicyReqDao.findByPrimaryKey(insuranceBean.getId());
					InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insPolicyReq1.getEsrMapId() });
					Users siblings[] = new Users[InsHndChainReqs.length];
					for (int k = 0; k < InsHndChainReqs.length; k++){
						InsuranceHandlerChainReq InsHndChainReq = InsHndChainReqs[k];
						Users currentHandler = usersDao.findByPrimaryKey(InsHndChainReq.getReceiverId());
						siblings[k] = currentHandler;
						// siblings = usersDao.findByDynamicWhere("LEVEL_ID",new Object[]{currentHandler.getLevelId()});
						//insHndChainReqDao.update(new InsuranceHandlerChainReqPk(InsHndChainReq.getId()), InsHndChainReq);
					}
					//session.setAttribute("insHndChainReqId",InsHndChainReqId);
					request.setAttribute("actionForm", siblings);
				} catch (ArrayIndexOutOfBoundsException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
				}
				break;
			}
			case INSURANCEASSIGN:{
				try{
					Login loginDto = Login.getLogin(request);
					requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
					Users assignedHandler = usersDao.findByPrimaryKey(insuranceBean.getId());
					String Comments = insuranceBean.getComments() == null ? "No Comments" : insuranceBean.getComments();
					InsurancePolicyReq insPolicyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { insuranceBean.getEsrMapId() });
					ProfileInfo hndPrfInfo = profileInfoDao.findByPrimaryKey(assignedHandler.getProfileId());
					if (insPolicyReq.length >= 1 && assignedHandler != null){
						Users insuranceRequester = usersDao.findByPrimaryKey(insPolicyReq[0].getRequeterId());
						ProfileInfo insuranceRequesterProfile = profileInfoDao.findByPrimaryKey(insuranceRequester.getProfileId());
						ProcessChain processChain[] = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=62 WHERE UR.USER_ID=?", new Object[] { new Integer(insuranceRequester.getId()) });
						ArrayList<Users> handlerList = null;
						String handlers = null, handlerIds = null;
						if (processChain[0] != null){
							handlers = processChain[0].getHandler();
							if (handlers != null && !handlers.equals("")){
								handlerList = ItModel.getUserLstByLevelIds(handlers, insuranceRequesterProfile);//11 July
								if (handlerList.contains(assignedHandler)){
									handlerList.remove(assignedHandler);
								}
								if (handlerList != null){
									handlerIds = ItModel.getUserIdsFromUserList(handlerList);
								}
							}
						}
						for (int i = 0; i < insPolicyReq.length; i++){
							InsurancePolicyReq insPolicyReq1 = insPolicyReq[i];
							InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { insuranceBean.getEsrMapId(), insPolicyReq1.getId() });
							if (InsHndChainReqs[0].isAssignToNull() || requestedUser.getId() == InsHndChainReqs[0].getAssignTo()){
								insPolicyReq1.setStatus("Docked by " + hndPrfInfo.getFirstName() + " " + hndPrfInfo.getLastName());
								insPolicyReqDao.update(new InsurancePolicyReqPk(insPolicyReq1.getId()), insPolicyReq1);
								for (int k = 0; k < InsHndChainReqs.length; k++){
									InsuranceHandlerChainReq InsHndChainReq = InsHndChainReqs[k];
									InsHndChainReq.setAssignTo(assignedHandler.getId());
									InsHndChainReq.setLastModifiedDate(new Date());
									InsHndChainReq.setStatus("Docked by " + hndPrfInfo.getFirstName() + " " + hndPrfInfo.getLastName());
									InsHndChainReq.setComments(insuranceBean.getComments() == null ? null : insuranceBean.getComments());
									insHndChainReqDao.update(new InsuranceHandlerChainReqPk(InsHndChainReq.getId()), InsHndChainReq);
								}
							} else{
								result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("this user can not handle the request"));
								return result;
							}
						}
						if (handlerIds != null){
							sendAssignedHndNotification(insuranceBean.getEsrMapId(), insuranceRequester, assignedHandler, handlerIds, Comments);
							Inbox[] inbox = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RAISED_BY=?", new Object[] { insuranceBean.getEsrMapId(), insPolicyReq[0].getRequeterId() });
							if (inbox.length > 0){
								//InboxPk inboxPk = new InboxPk();
								for (Inbox inbox1 : inbox){
									logger.error("****************changing inbox status******************");
									inbox1.setStatus("Docked by " + hndPrfInfo.getFirstName() + " " + hndPrfInfo.getLastName());
									inbox1.setAssignedTo(assignedHandler.getId());
									inboxDao.update(new InboxPk(inbox1.getId()), inbox1);
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
				}
				break;
			}
		}
		return result;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		return result;
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int loginUserid, HttpServletRequest request, String description) {
		logger.info("under upload methos");
		UsersDao usersDao = UsersDaoFactory.create();
		Integer fieldsId[] = new Integer[] { -1 };
		for (FileItem fileItem : fileItems){
			logger.error("FileName: " + fileItem.getName());
			try{
				InputStream fileItemStream = null;
				fileItemStream = fileItem != null ? fileItem.getInputStream() : null;
				if (fileItemStream != null){
					Vector<Vector<Object>> list = POIParser.parseXls(fileItemStream, 0);
					List<XlsRecordsBean> xlsRecordsBeanList = new ArrayList<XlsRecordsBean>();
					List<Integer> wrongData = new ArrayList<Integer>();
					List<Integer> email = new ArrayList<Integer>();
					int i = 0;
					fileItemStream.close();
					if (list != null && !list.isEmpty()){
						for (Vector<Object> row : list){
							  String temp=row.get(1).toString();
							  temp=temp.replace(".","");
							i++;
							try{
								
								/*XlsRecordsBean xlsRecordsBean = new XlsRecordsBean();
								Users user = usersDao.findWhereEmpIdEquals(((Double) row.get(0)).intValue())[0];
								xlsRecordsBean.setUserId(user.getId());
								xlsRecordsBean.setCardId((String) temp);
								xlsRecordsBean.setCoverage(((Double) row.get(2)).intValue());
								xlsRecordsBean.setCoverageFrom((Date) row.get(3));
								xlsRecordsBean.setCoverageUpto((Date) row.get(4));
								xlsRecordsBean.setPolicyNumber((String) row.get(5));
								xlsRecordsBean.setName((String) row.get(6));
								xlsRecordsBean.setRelationship((String) row.get(8));
								xlsRecordsBean.setGender((String) row.get(9));
								xlsRecordsBean.setAge(((Double) row.get(10)).intValue());
								xlsRecordsBean.setDob((Date) row.get(11));
								xlsRecordsBean.setInsuranceCompanyName((String) row.get(12));
								xlsRecordsBean.setEndorsementNo((String) row.get(14));
								xlsRecordsBean.setPolicyType((String) row.get(13));
								xlsRecordsBean.setLoginUserid(loginUserid);
								xlsRecordsBeanList.add(xlsRecordsBean);
								email.add(user.getId());*/
								
								
								XlsRecordsBean xlsRecordsBean = new XlsRecordsBean();
								Users user = usersDao.findWhereEmpIdEquals(((Double) row.get(0)).intValue())[0];
								xlsRecordsBean.setUserId(user.getId());
								xlsRecordsBean.setCardId((String) temp);
								xlsRecordsBean.setCoverage(((Double) row.get(3)).intValue());
								xlsRecordsBean.setCoverageFrom((Date) row.get(4));
								xlsRecordsBean.setCoverageUpto((Date) row.get(5));
								xlsRecordsBean.setPolicyNumber((String) row.get(6));
								xlsRecordsBean.setName((String) row.get(8));
								xlsRecordsBean.setRelationship((String) row.get(9));
								xlsRecordsBean.setGender((String) row.get(10));
								xlsRecordsBean.setAge(((Double) row.get(11)).intValue());
								xlsRecordsBean.setDob((Date) row.get(12));
								xlsRecordsBean.setInsuranceCompanyName((String) row.get(13));
								xlsRecordsBean.setEndorsementNo((String) row.get(14));
							    xlsRecordsBean.setPolicyType((String) row.get(7));
								xlsRecordsBean.setLoginUserid(loginUserid);
								xlsRecordsBeanList.add(xlsRecordsBean);
								email.add(user.getId());
							} catch (Exception e){
								wrongData.add(i);
							}
						}
				//		if (xlsRecordsBeanList.size() == list.size()){
							//call function
							boolean flag = insertRecordFromXls(xlsRecordsBeanList.toArray(new XlsRecordsBean[xlsRecordsBeanList.size()]));
							if (flag){
								fieldsId = new Integer[] { 0 };
								PortalMail pmail = new PortalMail();
								ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
								if (email.size() > 0) pmail.setAllReceipientBCCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(email) + ") AND STATUS NOT IN (1,2,3))"));
								pmail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + loginUserid + ") AND STATUS NOT IN (1,2,3))"));
								pmail.setTemplateName(MailSettings.INSURANCE_NOTIFICATION_AVAILED);
								pmail.setMailSubject("Insurance is availed");
								MailGenerator mailGenarator = new MailGenerator();
								mailGenarator.invoker(pmail);
							}
							if (xlsRecordsBeanList.size() == list.size()){
						} else fieldsId = wrongData.toArray(new Integer[wrongData.size()]);
					}
				}
			} catch (Exception e){
				e.printStackTrace();
				return fieldsId;
			}
		}
		return fieldsId;
	}

	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		String seprator = File.separator;
		String path = "Data" + seprator;
		try{
			ReferFriend referFrd = (ReferFriend) form;
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents[] dacDocuments = documentsDao.findWhereIdEquals(referFrd.getAttachment());
			PortalData portalData = new PortalData();
			path = portalData.getDirPath();
			attachements.setFileName(dacDocuments[0].getFilename());
			path = portalData.getfolder(path);
			path = path + seprator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (DocumentsDaoException e){
			logger.error("failed to download check the file path:", e);
			e.printStackTrace();
		}
		return attachements;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		return null;
	}

	public HandlersListBean[] getHndSiblingList(int insPlsReqId) throws Exception {
		//Login loginDto = Login.getLogin(request);
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
		InsuranceHandlerChainReqDao insHndChainReqDao = InsuranceHandlerChainReqDaoFactory.create();
		//requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
		InsurancePolicyReq insPolicyReq1 = insPolicyReqDao.findByPrimaryKey(insPlsReqId);
		InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { insPolicyReq1.getEsrMapId(), insPlsReqId });
		HandlersListBean siblings[] = new HandlersListBean[InsHndChainReqs.length];
		for (int k = 0; k < InsHndChainReqs.length; k++){
			HandlersListBean hndListBean = new HandlersListBean();
			InsuranceHandlerChainReq InsHndChainReq = InsHndChainReqs[k];
			Users currentHandler = usersDao.findByPrimaryKey(InsHndChainReq.getReceiverId());
			ProfileInfo prfInfo = profileInfoDao.findByPrimaryKey(currentHandler.getProfileId());
			hndListBean.setUserId(currentHandler.getId());
			hndListBean.setUserName(prfInfo.getFirstName() + " " + prfInfo.getLastName());
			siblings[k] = hndListBean;
		}
		return siblings;
	}

	public String getAssignedUserName(int esrMapID, int requestId) {
		try{
			InsuranceHandlerChainReqDao insHndChainReqDao = InsuranceHandlerChainReqDaoFactory.create();
			InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { esrMapID, requestId });
			if (InsHndChainReqs.length > 0){
				if (!InsHndChainReqs[0].isAssignToNull()){
					int assigneduserID = InsHndChainReqs[0].getAssignTo();
					UsersDao usersDao = UsersDaoFactory.create();
					ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
					Users assignedUser = usersDao.findByPrimaryKey(assigneduserID);
					ProfileInfo prf = profileInfoDao.findByPrimaryKey(assignedUser.getProfileId());
					return (prf.getFirstName() + " " + prf.getLastName());
				} else{
					return "Request is not Assigned";
				}
			} else return "Request is not Assigned";
		} catch (Exception ex){
			ex.printStackTrace();
			return "Request is not Assigned";
		}
	}

	public int getUserId(String xlDateString, String relationShip, String xlname) {
		PersonalInfoDao prsInfoDao = PersonalInfoDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao userDao = UsersDaoFactory.create();
		Users[] usr = null;
		String[] date1 = xlDateString.split(" ");
		String formatedDateString = date1[5] + "-" + PortalUtility.getMonthNumber(date1[1]) + "-" + date1[2];
		String name = xlname.split(" ")[0];
		try{
			Date dob = PortalUtility.StringToDateDB(formatedDateString);
			if (relationShip.equalsIgnoreCase("SELF")){
				ProfileInfo[] prf = profileInfoDao.findByDynamicWhere("FIRST_NAME LIKE ? AND DOB=?", new Object[] { name + "%", formatedDateString.trim() });
				if (prf.length > 0){
					usr = userDao.findByDynamicWhere("PROFILE_ID=?", new Object[] { prf[0].getId() });
				}
			} else{
				PersonalInfo[] prs = prsInfoDao.findByDynamicWhere("SPOUSE_NAME LIKE ? AND SPOUSE_DOB=?", new Object[] { name + "%", dob });
				if (prs.length > 0){
					usr = userDao.findByDynamicWhere("PERSONAL_ID=?", new Object[] { prs[0].getId() });
				}
			}
			if (usr != null){ return usr[0].getId(); }
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	private int getPandingRequestCount(InsurancePolicyReq[] insPolicyReqBean, InsuranceHandlerChainReq[] InsHndChainReqs, int hndId) {
		int count = 0;
		if ((Status.getStatusId(insPolicyReqBean[0].getStatus()) == 25 || InsHndChainReqs[0].getAssignTo() == hndId) && (!(Status.getStatusId(insPolicyReqBean[0].getStatus()) == 7) && !(Status.getStatusId(insPolicyReqBean[0].getStatus()) == 3))){
			count++;
		}
		return count;
	}

	private String getOverAllStatus(InsurancePolicyReq[] insPolicyReqBean) {
		int statusId = 0;
		String status = null;
		for (int i = 0; i < insPolicyReqBean.length;){
			if (Status.getStatusId(insPolicyReqBean[i].getStatus()) == 7 || (Status.getStatusId(insPolicyReqBean[i].getStatus()) == 3)){
				statusId = 19;
				status = Status.getStatus(statusId);
				break;
			} else{
				statusId = Status.getStatusId(insPolicyReqBean[i].getStatus());
				if (statusId == 0){
					status = insPolicyReqBean[i].getStatus();
				} else{
					status = Status.getStatus(statusId);
				}
				break;
			}
		}
		return status;
	}

	private boolean insertRecordFromXls(XlsRecordsBean[] xlsRecordsBeanArray) {
		InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
		InsurancePolicyDetailsDao insPolicyDtlsDao = InsurancePolicyDetailsDaoFactory.create();
		InsuranceHandlerChainReqDao insHndChainReqDao = InsuranceHandlerChainReqDaoFactory.create();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		RegionsDao regionDao = RegionsDaoFactory.create();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		boolean flag = true;
		int i = 0;
		for (XlsRecordsBean xlsRecordsBean : xlsRecordsBeanArray){
			try{
				int reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(xlsRecordsBean.getUserId()) })[0].getId();
				String reg_abb = regionDao.findByPrimaryKey(reg_id).getRefAbbreviation();
				Date date = new Date();
				int uniqueID = 1;
				EmpSerReqMap empMap[] = empSerReqDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=15)", null);
				if (empMap.length > 0){
					String s = empMap[0].getReqId().split("-")[2];
					uniqueID = Integer.parseInt(s) + 1;
				}
				EmpSerReqMap empReqMapDto = new EmpSerReqMap();
				empReqMapDto.setSubDate(date);
				empReqMapDto.setReqId(reg_abb + "-INS-" + uniqueID);
				empReqMapDto.setReqTypeId(15);
				empReqMapDto.setRegionId(reg_id);
				empReqMapDto.setRequestorId(xlsRecordsBean.getUserId());
				empReqMapDto.setProcessChainId(0);
				reqpk = empSerReqDao.insert(empReqMapDto);
				InsurancePolicyReq insPolicyReq = new InsurancePolicyReq();
				insPolicyReq.setEsrMapId(reqpk.getId());
				insPolicyReq.setRequeterId(xlsRecordsBean.getUserId());
				insPolicyReq.setCoverage(xlsRecordsBean.getCoverage());
				insPolicyReq.setCoverageFrom(xlsRecordsBean.getCoverageFrom());
				insPolicyReq.setCoverageUpto(xlsRecordsBean.getCoverageUpto());
				insPolicyReq.setDeliveryAddress("-");
				insPolicyReq.setPolicyType(xlsRecordsBean.getPolicyType());
				insPolicyReq.setPolicyNumber(xlsRecordsBean.getPolicyNumber());
				insPolicyReq.setInsuranceCompanyName(xlsRecordsBean.getInsuranceCompanyName());
				insPolicyReq.setServiceTax(0);
				insPolicyReq.setBasicPremium(0);
				insPolicyReq.setTotalPremium(0);
				insPolicyReq.setRequestedOn(new Date());
				insPolicyReq.setStatus(Status.getStatus(7));
				
				InsurancePolicyReqPk insPlyReqPk = insPolicyReqDao.insert(insPolicyReq);
				insPolicyReq.setId(insPlyReqPk.getId());
				InsurancePolicyDetails insPolicyDetails1 = new InsurancePolicyDetails();
				insPolicyDetails1.setEsrMapId(insPolicyReq.getEsrMapId());
				insPolicyDetails1.setCardId(xlsRecordsBean.getCardId());
				insPolicyDetails1.setPolicyReqId(insPolicyReq.getId());
				insPolicyDetails1.setName(xlsRecordsBean.getName());
				insPolicyDetails1.setRelationship(xlsRecordsBean.getRelationship());
				insPolicyDetails1.setGender(xlsRecordsBean.getGender());
				insPolicyDetails1.setEndorsementNo(xlsRecordsBean.getEndorsementNo());
				insPolicyDetails1.setDob(xlsRecordsBean.getDob());
				insPolicyDetails1.setAge(xlsRecordsBean.getAge());
				InsurancePolicyDetailsPk insplyDtlsPk1 = insPolicyDtlsDao.insert(insPolicyDetails1);
				insPolicyDetails1.setId(insplyDtlsPk1.getId());
				InsuranceHandlerChainReq insHndChainReq = new InsuranceHandlerChainReq();
				insHndChainReq.setEsrMapId(insPolicyReq.getEsrMapId());
				insHndChainReq.setRequesterId(xlsRecordsBean.getUserId());
				insHndChainReq.setReceiverId(xlsRecordsBean.getLoginUserid());
				insHndChainReq.setPolicyReqId(insPolicyReq.getId());
				insHndChainReq.setStatus(Status.getStatus(7));
				insHndChainReq.setAssignTo(xlsRecordsBean.getLoginUserid());
				insHndChainReq.setLastModifiedDate(new Date());
				insHndChainReq.setCreateDate(insPolicyReq.getRequestedOn());
				InsuranceHandlerChainReqPk insHndChainReqPK = insHndChainReqDao.insert(insHndChainReq);
				insHndChainReq.setId(insHndChainReqPK.getId());
				//String handlerIds = ItModel.getUserIdsFromUserList(xlsRecordsBean.getHandlerList());
				//String notifierIds = ItModel.getUserIdsFromUserList(xlsRecordsBean.getNotifierList());
				//sendInsuranceFinalNotification(reqpk.getId(), xlsRecordsBean.getUserId(), handlerIds, notifierIds, xlsRecordsBean.getLoginUserid());
				i++;
			} catch (Exception ex){
				logger.error("insertion failed at row no.:=" + (i + 1));
				flag = false;
				ex.printStackTrace();
			}
		}
		return flag;
	}
	/*
	private boolean insertDocumentRecord(Integer fieldsId[], FileItem fileItem, String docType, int loginUserid, String description, int arrayPorition) {
		boolean flag = false;
		try{
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			String fileName = portalData.saveFile(fileItem, dTypes, loginUserid);
			String DBFilename = fileName;
			logger.error("******filename*********" + fileName);
			if (fileName != null){
				Documents documents = new Documents();
				DocumentsDao documentsDao = DocumentsDaoFactory.create();
				documents.setFilename(DBFilename);
				documents.setDoctype(docType);
				documents.setDescriptions(description);
				logger.error("The file " + fileName + " successfully uploaded");
				DocumentsPk documentsPk = documentsDao.insert(documents);
				fieldsId[arrayPorition] = documentsPk.getId();
				flag = true;
			}
		} catch (DocumentsDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}*/
}
