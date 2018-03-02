package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.HandlerAction;
import com.dikshatech.beans.Roles;
import com.dikshatech.beans.TravelRequest;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.RequestEscalation;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.ItemCostInfoDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.PoDetailsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.TravelContactDetailsDao;
import com.dikshatech.portal.dao.TravelDao;
import com.dikshatech.portal.dao.TravelReqDao;
import com.dikshatech.portal.dao.TravellerTypeDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DivisonPk;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.ItemCostInfo;
import com.dikshatech.portal.dto.ItemCostInfoPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProcessChainPk;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.Travel;
import com.dikshatech.portal.dto.TravelContactDetails;
import com.dikshatech.portal.dto.TravelContactDetailsPk;
import com.dikshatech.portal.dto.TravelPk;
import com.dikshatech.portal.dto.TravelReq;
import com.dikshatech.portal.dto.TravelReqPk;
import com.dikshatech.portal.dto.TravellerType;
import com.dikshatech.portal.dto.TravellerTypePk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.StatusDaoException;
import com.dikshatech.portal.exceptions.TravelContactDetailsDaoException;
import com.dikshatech.portal.exceptions.TravelDaoException;
import com.dikshatech.portal.exceptions.TravelMailException;
import com.dikshatech.portal.exceptions.TravelNotificationException;
import com.dikshatech.portal.exceptions.TravelReqDaoException;
import com.dikshatech.portal.exceptions.TravellerTypeDaoException;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.ItemCostInfoDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.TravelContactDetailsDaoFactory;
import com.dikshatech.portal.factory.TravelDaoFactory;
import com.dikshatech.portal.factory.TravelReqDaoFactory;
import com.dikshatech.portal.factory.TravellerTypeDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.forms.TravelForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.timer.ParseTemplate;

public class TravelModel extends ActionMethods
{
	private static Logger	logger		= LoggerUtil
												.getLogger(TravelModel.class);
	private static final String	selectUsers		= "SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE,U.USER_CREATED_BY,U.EXPERIENCE_ID, U.SKILLSET_ID, U.OTHERS, U.ACTION_BY  FROM USERS U ";
	private RequestEscalation reqEscalation=new RequestEscalation();

	EmpSerReqMapDao			eMapDao		= EmpSerReqMapDaoFactory.create();
	ProcessChainDao			pChainDao	= ProcessChainDaoFactory.create();
	UsersDao				usersDao	= UsersDaoFactory.create();
	TravelDao				tDao		= TravelDaoFactory.create();
	TravelReqDao			tReqDao		= TravelReqDaoFactory.create();

	private ProcessChain	procChain;

	@Override
	public ActionResult defaultMethod(PortalForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 *@author sasmita.sabar **/
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		TravelDao travelDao = TravelDaoFactory.create();
		ActionResult result = new ActionResult();
		EmpSerReqMapPk eMapPk = new EmpSerReqMapPk();
		TravelPk travelPk = new TravelPk();
		TravelForm travelForm = (TravelForm) form;
		try
		{
			switch (ActionMethods.DeleteTypes.getValue(form.getdType()))
			{
				case DELETEALL:
					int travelIds[] = travelForm.getTravelIds();

					for (int travelId : travelIds)
					{
						Travel travel = travelDao.findByPrimaryKey(travelId);
						if (travel.getStatus() == Status
								.getStatusId(Status.REQUESTSAVED))
						{
							travelPk.setId(travelId);
							travelDao.delete(travelPk);
							eMapPk.setId(travel.getEsrqmId());
							eMapDao.delete(eMapPk);
						}
						else
						{
							try
							{
								throw new Exception();
							} catch (Exception e)
							{
								result.getActionMessages().add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionMessage(
											"errors.delete.failed.travel"));
							}
						}

					}
					break;

			}

		} catch (Exception e)
		{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,
				new ActionMessage("errors.failedtodelete"));
			e.printStackTrace();
			logger.error("failed to delete:",e);

		}
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request)
			throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author sasmita.sabar
	 * **/
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		DropDown dropDown = new DropDown();
		
		TravelDao travelDao = TravelDaoFactory.create();
		TravelReqDao travelReqDao = TravelReqDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		StatusDao statusDao = StatusDaoFactory.create();
		Login login = Login.getLogin(request);
		ProfileInfo profileInfo2 = null;
		ItemCostInfoDao itemCostInfoDao = ItemCostInfoDaoFactory.create();
		DivisonDao divisonDao=DivisonDaoFactory.create();
		ProjectDao projectDao=ProjectDaoFactory.create();
		ChargeCodeDao chargeCodeDao=ChargeCodeDaoFactory.create();
		PoDetailsDao poDetailsDao=PoDetailsDaoFactory.create();

		String sqlItemInfo = "TL_ID=?";
		try
		{
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{
			case RECEIVE:
				TravelForm tForm = (TravelForm) form;
				
				int travellersDivisonId = 0 ;
				int travellersDeptId = 0;
				String travellersDivisonName = null;
				String travellersDeptName = null;
				
				if ((tForm.getTravelId()) != 0) {
					Object[] sqlParams = { tForm.getTravelId() };
					TravelRequest travelRequest2 = travelDao.findTravelId(sqlParams);
					
					/*
					 * to find out the traveller's Department Division details
					 */
					final int travellerUserId = travelRequest2.getTrlUserId();
					Divison fetchedDivison = divisonDao.findByDynamicSelect("SELECT * FROM DIVISON D WHERE ID=(SELECT L.DIVISION_ID FROM LEVELS L WHERE L.ID=(SELECT U.LEVEL_ID FROM USERS U WHERE U.ID=?))", new Object[]{travellerUserId})[0];
					if(fetchedDivison!=null){
						int parentId = fetchedDivison.getParentId();
						if(parentId>0){
							travellersDivisonId = fetchedDivison.getId();
							travellersDivisonName = fetchedDivison.getName();
							while(parentId > 0){
								fetchedDivison = divisonDao.findByPrimaryKey(parentId);
								parentId = fetchedDivison.getParentId();
							}
							travellersDeptId = fetchedDivison.getId();
							travellersDeptName = fetchedDivison.getName();							
						}else{
							travellersDeptId = fetchedDivison.getId();
							travellersDeptName = fetchedDivison.getName();
						}								
					}	
					
					profileInfo2 = getProfileObjForUser(travelRequest2.getRaisedBy());
					travelRequest2.setRequestorName(profileInfo2.getFirstName()+ " " + profileInfo2.getLastName());

					// if status is not new set Emp_ser_req_map reqId to trl Dto
					if (!(travelRequest2.getStatus() == Status.getStatusId(Status.REQUESTSAVED))) {
						EmpSerReqMap empSerReqMap2 = empSerReqMapDao.findByPrimaryKey(travelRequest2.getEsrqmId());
						travelRequest2.setEmpServReqMapId(empSerReqMap2.getReqId());
					}
					TravelReq[] travelReq = null;

					/**
					 * commented as approvers not needed according to new
					 * requirement in the commented code the status used are
					 * old, new status used: PROCESSED for completed
					 * 
					 * **/

					if (travelRequest2.getStatus() == Status.getStatusId(Status.PROCESSED)|| travelRequest2.getStatus() == Status.getStatusId(Status.REJECTED)) {
						int actionType = travelRequest2.getStatus();
						if (travelRequest2.getStatus() == Status.getStatusId(Status.PROCESSED)) {
							actionType = Status.getStatusId(Status.PROCESSED);
						}
						Object[] sqlParams4 = { tForm.getTravelId(), actionType };
						String sql2 = "SELECT * FROM TRAVEL_REQ WHERE TL_REQ_ID=? AND ACTION_TYPE=? ORDER BY DATE_OF_COMPLETION DESC";
						travelReq = travelReqDao.findByDynamicSelect(sql2,sqlParams4);
					}

					if (travelReq != null) {
						ProfileInfo profileInfo = getProfileObjForUser(travelReq[0].getAssignedTo());
						travelRequest2.setApprovedRejBy(profileInfo.getFirstName()+ " " + profileInfo.getLastName());
						travelRequest2.setApprovedRejOn(PortalUtility.formatDateToddmmyyyyhhmmss(travelReq[0].getDateOfCompletion()));
					}
					if (travelRequest2.getChargeCode() != 0) {
						ChargeCode chargeCode = chargeCodeDao.findByPrimaryKey(travelRequest2.getChargeCode());
						PoDetails poDetails = poDetailsDao.findByPrimaryKey(chargeCode.getPoId());
						Project project = projectDao.findByPrimaryKey(poDetails.getProjId());
						travelRequest2.setChargeCodeTittle(chargeCode.getChCodeName());
						travelRequest2.setChargeCodeName(chargeCode.getChCode());
						travelRequest2.setProjectId(project.getId());
						travelRequest2.setProjectName(project.getName());
					}
					if (travelRequest2.getDivisionId() != 0) {
						Divison divison = divisonDao.findByPrimaryKey(new DivisonPk(travelRequest2.getDivisionId()));
						travelRequest2.setDivisionName(divison.getName());
					}
					if (travelRequest2.getIscontactPersonReq() == 1) {
						TravelContactDetailsDao travelContactDetailsDao = TravelContactDetailsDaoFactory.create();
						TravelContactDetails travelContactDetails[] = travelContactDetailsDao.findWhereTlIdEquals(travelRequest2.getTravelId());
						if (travelContactDetails.length > 0 && travelContactDetails != null) {
							travelRequest2.setContactPerson(travelContactDetails[0].getContactPerson());
							travelRequest2.setPhoneNo(String.valueOf(travelContactDetails[0].getPhoneNo()));
							travelRequest2.setEmailId(travelContactDetails[0].getEmailId());
							travelRequest2.setAddress(travelContactDetails[0].getAddress());
						}

					}

					com.dikshatech.portal.dto.Status status2 = statusDao.findByPrimaryKey(travelRequest2.getStatus());
					travelRequest2.setStatusName(status2.getStatus());
					
					/*
					 * change in finding out traveller's dept.Name division.Name dept.Id division.Id
					 */
					travelRequest2.setDepartmentId(travellersDeptId);
					travelRequest2.setDepartmentName(travellersDeptName);
					travelRequest2.setDivisionId(travellersDivisonId);					
					travelRequest2.setDivisionName(travellersDivisonName);
					
					TravelReq[] fetchedTravelReq = travelReqDao.findByDynamicWhere(" TL_REQ_ID=? AND APP_LEVEL>0 AND ACTION_TYPE>0", new Object[]{tForm.getTravelId()});
					if(fetchedTravelReq!=null && fetchedTravelReq.length>0){
						ProfileInfo approverInfo = getProfileObjForUser(fetchedTravelReq[0].getAssignedTo());
						travelRequest2.setApprovedRejBy(approverInfo.getFirstName()+" "+approverInfo.getLastName());
						travelRequest2.setApprovedRejOn(PortalUtility.formatDateToddmmyyyyhhmmss(fetchedTravelReq[0].getDateOfCompletion()));
					}
					travelRequest2.setCreatedDate(PortalUtility.formatDateToddmmyyyyhhmmss(travelDao.findByPrimaryKey(tForm.getTravelId()).getCreateDate()));
										
					request.setAttribute("actionForm", travelRequest2);
				}
				// only for rollOn
				else if (tForm.getIsRollOn() == 1) {
					String travellerDetails = tForm.getTravellerDetails();
					String travellerDetailsArray[] = travellerDetails.split("~=~");
					for (String travellerdetail : travellerDetailsArray) {
						if ((travellerdetail.split("=")[0].trim()).equalsIgnoreCase("empId")&& travellerdetail.split("=").length > 1) {
							tForm.setEmpId(Integer.parseInt(travellerdetail.split("=")[1]));
						} else if ((travellerdetail.split("=")[0].trim()).equalsIgnoreCase("projectId")&& travellerdetail.split("=").length > 1) {
							tForm.setProjectId(Integer.parseInt(travellerdetail.split("=")[1]));
						} else if ((travellerdetail.split("=")[0].trim()).equalsIgnoreCase("chargeCodeId")
								&& travellerdetail.split("=").length > 1) {
							tForm.setChargeCodeId(Integer.parseInt(travellerdetail.split("=")[1]));
						}

					}

					// depending on empId need to get traveller details
					TravelForm travelForm = null;
					try {
						travelForm = travelDao.findTravellerDetailsByEmpId(new Object[] { tForm.getEmpId() })[0];
					} catch (Exception e) {
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.selectedTravellerIsnotActive"));
						logger.debug(e);
						e.printStackTrace();
					}
					if (travelForm == null) {
						logger.debug("emp does not exist");
					}

					tForm.setTravelerName(travelForm.getTravelerName());
					tForm.setDesignation(travelForm.getDesignation());
					tForm.setDivisionId(travelForm.getDivisionId());
					tForm.setDivisionName(travelForm.getDivisionName());
					tForm.setRegName(travelForm.getRegName());
					tForm.setCompanyId(travelForm.getCompanyId());
					tForm.setCompanyName(travelForm.getCompanyName());

					Divison divisionFetched = null;
					boolean deptFlag = true;
					int divId = tForm.getDivisionId();
					
					while (deptFlag) {
						divisionFetched = divisonDao.findByPrimaryKey(new DivisonPk(divId));
						if (divisionFetched.getParentId() != 0)
							divId = divisionFetched.getParentId();
						else
							deptFlag = false;
					}
				
					if (divisionFetched == null) {
						logger.debug("div id null");

					} else
						tForm.setDepartmentName(divisionFetched.getName());

					if (tForm.getProjectId() == 0) {
						logger.debug("project id is 0");

					} else {
						Project project = projectDao.findByPrimaryKey(tForm.getProjectId());
						tForm.setProjectName(project.getName());
					}
					
					if (tForm.getChargeCodeId() == 0) {
						logger.debug("charge code id is 0");
					} else {
						ChargeCode chargeCode = chargeCodeDao.findByPrimaryKey(tForm.getChargeCodeId());
						tForm.setChargeCodeName(chargeCode.getChCode());
						tForm.setChargeCodeTittle(chargeCode.getChCodeName());
					}
					
					request.setAttribute("actionForm", tForm);
				}
				
				break;
				
				
				
			case RECEIVEALL:// receive for req_raised users:
				
				TravelRequest travelRequestRequester = new TravelRequest();//i was pushed here from below
				
				String divisonQuery= "SELECT * FROM DIVISON D WHERE D.ID=(SELECT L.DIVISION_ID FROM LEVELS L WHERE ID=(SELECT PI.LEVEL_ID FROM PROFILE_INFO PI WHERE ID=(SELECT PROFILE_ID FROM USERS U WHERE U.ID=?)))";
				Divison loggedInUserDivison= divisonDao.findByDynamicSelect(divisonQuery, new Object[]{login.getUserId()})[0];
					boolean showRadioButtons = true;//SELF OTHERS
							
				if(loggedInUserDivison.getName().equalsIgnoreCase("RMG")){
					//logged in user belongs to HRD....specifically RMG
					showRadioButtons = true;
				}else{
					//check if he is an R.M
					String rmQuery1 = "SELECT * FROM PROFILE_INFO WHERE REPORTING_MGR=?";
					ProfileInfo[] fetchedProfileInfoRows = ProfileInfoDaoFactory.create().findByDynamicSelect(rmQuery1, new Object[]{login.getUserId()});
					
					if(fetchedProfileInfoRows!=null && fetchedProfileInfoRows.length>0){
						//logged in person is R.M
						showRadioButtons = true;
					}	
				}
				//if the showRadioButtons is still false : logged in user is neither R.M nor HR_SPOC  UI will be different
				travelRequestRequester.setShowRadioButtons(showRadioButtons);
				travelRequestRequester.setLoggedInUserId(login.getUserId());				
				
				
				String sql = "RAISED_BY=?";

				Travel travel[] = travelDao.findByDynamicWhere(sql,new Object[] { login.getUserId() });
				TravelRequest travelRequestBean[] = new TravelRequest[travel.length];
				int j = 0;
				for (Travel trl : travel) {
					Object sqlParams2[] = new Object[] { trl.getId() };
					TravelRequest travelRequest = travelDao.findTravelId(sqlParams2);
					ProfileInfo profileInfo = getProfileObjForUser(travelRequest.getRaisedBy());
					travelRequest.setRequestorName(profileInfo.getFirstName()+ " " + profileInfo.getLastName());

					// sending flag as true if cancel request is raised for this travel requests

					TravelReq travelReq[] = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND STATUS=?", new Object[] {
									trl.getId(),Status.getStatusId(Status.REVOKED) });

					if (travelReq != null && travelReq.length > 0) {
						travelRequest.setIsRevokedRequestRaised(true);
						//logger.error("the cancel request is already raised by the requester for travel id:"+ trl.getId());
					} else{
						travelRequest.setIsRevokedRequestRaised(false);
					}
					
					//to distinguish b/w SELF & OTHERS
					TravellerType[] travellerType = TravellerTypeDaoFactory.create().findWhereTlIdEquals(trl.getId());
					if(travellerType!=null && travellerType.length>0){
						travelRequest.setIsBussinessType(travellerType[0].getIsBussinessType());	
					}

					//use this as fix when required	travelRequestBean[j].setTravelReqId(travelRequest.getTravelId());
					
					travelRequestBean[j] = travelRequest;
					j++;
				}
				//TravelRequest travelRequestRequester = new TravelRequest(); shifted few lines above
				travelRequestRequester.setTravelRequest(travelRequestBean);
				getApproverHandlerVisibility(login.getUserId(),travelRequestRequester);
				dropDown.setDetail(travelRequestRequester);
				request.setAttribute("actionForm", dropDown);
				break;

					
					
					
				case RECEIVEALLTOHANDLE:
				DropDown trltoHandlerForm = (DropDown) form;
				Object[] sqlParamHandler = { login.getUserId() };
				
				// GURUNATH ADDED CODE FOR FILTERING
				StringBuffer query = new StringBuffer("ASSIGNED_TO=? AND APP_LEVEL IS NULL");
								
				if (trltoHandlerForm.getMonth() != null || trltoHandlerForm.getSearchyear() != null || trltoHandlerForm.getSearchName() != null){
					if (trltoHandlerForm.getMonth() != null && trltoHandlerForm.getToMonth() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE (MONTH(TR.CREATE_DATE) BETWEEN " + trltoHandlerForm.getMonth() + " AND " + trltoHandlerForm.getToMonth() + ")) ");
					else if (trltoHandlerForm.getMonth() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE MONTH(TR.CREATE_DATE)=" + trltoHandlerForm.getMonth() + " ) ");
					if (trltoHandlerForm.getSearchyear() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE YEAR(TR.CREATE_DATE)=" + trltoHandlerForm.getSearchyear() + ") ");
					if (trltoHandlerForm.getSearchName() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE (SELECT CONCAT(PF.FIRST_NAME,' ',PF.LAST_NAME) FROM PROFILE_INFO PF WHERE PF.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=TR.TRL_USER_ID)) LIKE '%" + trltoHandlerForm.getSearchName() + "%') ");
				} else query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE TIMESTAMPDIFF(DAY,TR.CREATE_DATE,NOW()) <= 60)");
				TravelReq[] trlRqHandler = travelReqDao.findByDynamicWhere(query.toString(), sqlParamHandler);
				//ENDED FILTERING
				Travel[] trlRqHandler2 = travelDao.findByDynamicWhere(" STATUS NOT IN(27,28)",null);
				InboxDao inboxdao = InboxDaoFactory.create();
				TravelRequest travelReqestBean[] = new TravelRequest[trlRqHandler.length];
				int k = 0;
				for (TravelReq tlReq : trlRqHandler) {
					HandlerAction handlerAction = new HandlerAction();
					Object[] sqlParamtl = new Object[] { tlReq.getTlReqId() };
					TravelRequest trvlReq = travelDao.findTravelId(sqlParamtl);
					ProfileInfo profileInfo = getProfileObjForUser(trvlReq.getRaisedBy());
					trvlReq.setRequestorName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());

					/**
					 * set the handler Action,to send flag as active or
					 * inactive(1 is active,0 is inactive)
					 **/
					
					int esrMapId = travelDao.findByPrimaryKey(tlReq.getTlReqId()).getEsrqmId();
					Inbox[] inboxRows =  inboxdao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? AND ASSIGNED_TO=?", new Object[]{esrMapId,login.getUserId()});
					if(inboxRows!=null && inboxRows.length>0){
						handlerAction.setAssign(1);				
					}else{
						handlerAction.setAssign(0);
					}
										
					setTravel(trvlReq, tlReq);
					
					inboxRows =  inboxdao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? ORDER BY ID DESC", new Object[]{esrMapId});
					if(inboxRows!=null && inboxRows.length>0){
						trvlReq.setTrlreqStatusName(inboxRows[0].getStatus());//**
					}else{
						//it is serviced....need to get its status from TRAVEL
						Travel tempTravelDetails = travelDao.findWhereEsrqmIdEquals(esrMapId)[0];
						if(Status.getStatus(tempTravelDetails.getStatus()).equalsIgnoreCase(Status.APPROVED)){
							TravelReq[] tempTravelReq = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[]{tempTravelDetails.getId(),login.getUserId()});
							if(tempTravelReq!=null && tempTravelReq.length>0){
								trvlReq.setTrlreqStatusName(Status.getStatus(tempTravelReq[0].getStatus()));
							}
						}else{
							trvlReq.setTrlreqStatusName(Status.getStatus(travelDao.findWhereEsrqmIdEquals(esrMapId)[0].getStatus()));//**	
						}
						
					}
					
					//**correction for status in HANDLER tab
					if(travelDao.findWhereEsrqmIdEquals(esrMapId)[0].getStatus()==Status.getStatusId(Status.REVOKED)){
						trvlReq.setTrlreqStatusName(Status.getStatus(travelDao.findWhereEsrqmIdEquals(esrMapId)[0].getStatus()));//**
					}
					
					trvlReq.setHandlerAction(handlerAction);
					ItemCostInfo itemCostInfo[] = itemCostInfoDao.findByDynamicWhere(sqlItemInfo,new Object[] { tlReq.getId() });

					// set the values to ItemCostInfo Object
					if (itemCostInfo != null) {
						ItemCostInfo[] itemcostinfo = new ItemCostInfo[itemCostInfo.length];
						int m = 0;
						for (ItemCostInfo itemcost : itemCostInfo) {
							ItemCostInfo itemCostInfoDto = new ItemCostInfo();
							itemCostInfoDto.setItem(itemcost.getItem());
							itemCostInfoDto.setItemCost(itemcost.getItemCost());
							itemCostInfoDto.setId(itemcost.getId());
							itemCostInfoDto.setTlId(itemcost.getTlId());
							itemcostinfo[m] = itemCostInfoDto;
							m++;
						}
						trvlReq.setItemCostInfoArray(itemcostinfo);

					}
					travelReqestBean[k] = trvlReq;
					//fix
					travelReqestBean[k].setTravelReqId(trvlReq.getTravelId());
					k++;
				}

				//dropDown.setCount(trlRqHandler2.length);
				//fix for count
				Travel[] travelRows = travelDao.findByDynamicWhere(" STATUS NOT IN(27,28,29,7) AND RAISED_BY != ?", new Object[]{login.getUserId()});//27 PROCESSED			28 REVOKED			29 REQUEST SAVED			7 ACCEPTED
				int handlerCount = 0;
				List<Integer>esrList = new ArrayList<Integer>();
				HashMap<Integer, Integer>tlIdEsrIdMap = new HashMap<Integer, Integer>();
				
				if(travelRows!=null && travelRows.length>0){
					List<Integer>tlIdList = new ArrayList<Integer>(travelRows.length); 
					for(Travel eachTravelrow : travelRows){
						tlIdList.add(eachTravelrow.getId());
						esrList.add(eachTravelrow.getEsrqmId());
						tlIdEsrIdMap.put(eachTravelrow.getId(), eachTravelrow.getEsrqmId());
					}
					List<Integer>actionTakenIdList = new ArrayList<Integer>();
					
					for(Iterator<Integer>idIter = tlIdList.iterator();idIter.hasNext();){
						Integer id = idIter.next();
						TravelReq[] actionTakenReq = travelReqDao.findByDynamicSelect("SELECT * FROM TRAVEL_REQ WHERE TL_REQ_ID=? AND ACTION_TYPE != 0", new Object[]{id});
						if(actionTakenReq!=null && actionTakenReq.length>0){
							actionTakenIdList.add(actionTakenReq[0].getTlReqId());
						}					
					}
					
					handlerCount = tlIdList.size() - actionTakenIdList.size();
					
					//for those Docked By and Work -In- Progress
					for(Iterator<Integer>idIter = actionTakenIdList.iterator();idIter.hasNext();){
						Integer tlId = idIter.next();
						Integer esrMapId = tlIdEsrIdMap.get(tlId);
						Inbox[] fetchedInboxRow = inboxdao.findWhereEsrMapIdEquals(esrMapId);
						if(fetchedInboxRow != null && fetchedInboxRow.length>0){
							if(fetchedInboxRow[0].getAssignedTo() == login.getUserId()){
								handlerCount++;
							}
						}
						
					}
				}
							
				dropDown.setCount(handlerCount);				
				dropDown.setDropDown(travelReqestBean);

				request.setAttribute("actionForm", dropDown);
				break;
					
					
					
					
				case HANDLER:
				TravelForm trlHandlerForm = (TravelForm) form;
				TravelReq trlReqhandlerDto = null;

				//Set escalation
	
				int escEsrMapId=trlHandlerForm.getEsrqmId();
				int travelId=trlHandlerForm.getTravelReqId();
				if(escEsrMapId<=0&&travelId!=0)
				{
					TravelDao travelProvider=TravelDaoFactory.create();
					Travel escTravel=travelProvider.findByPrimaryKey(travelId);
					escEsrMapId=escTravel.getEsrqmId();
				}
				else
					logger.debug("Unable to obtain ESR_MAP_ID for travel ID: " + travelId);
				
				int escUserId=login!=null?login.getUserId():0;
				reqEscalation.setEscalationAction(escEsrMapId, escUserId);
				
				//If escalation action set current esrMapId
				if(reqEscalation.isEscalationAction())
				{
					reqEscalation.setEsrMapId(escEsrMapId);
				}
				
				if (trlHandlerForm.getEsrqmId() != 0) {
					TravelReq trlReqhandlerDto2[] = travelReqDao.findByDynamicWhere(
									" TL_REQ_ID=(SELECT ID FROM TRAVEL WHERE ESRQM_ID=?) AND ASSIGNED_TO=? ORDER BY ID DESC",
									new Object[] { trlHandlerForm.getEsrqmId(),login.getUserId() });
					trlReqhandlerDto = trlReqhandlerDto2[0];
				} else {
					trlReqhandlerDto = travelReqDao.findByDynamicWhere("TL_REQ_ID=? ORDER BY ID DESC", new Object[]{trlHandlerForm.getTravelReqId()})[0];					
				}

				TravelReq travelReqRow = travelReqDao.findByDynamicWhere("TL_REQ_ID=? ORDER BY ID DESC ",new Object[] { trlReqhandlerDto.getTlReqId() })[0];
				Object[] tlsqlHandlerParam = { trlReqhandlerDto.getTlReqId() };
				TravelRequest trlReqhandlerBean = travelDao.findTravelId(tlsqlHandlerParam);
				trlReqhandlerDto.setTotalCost(travelReqRow.getTotalCost());

				if (trlReqhandlerBean.getIscontactPersonReq() == 1) {
					TravelContactDetailsDao travelContactDetailsDao = TravelContactDetailsDaoFactory.create();
					TravelContactDetails travelContactDetails = travelContactDetailsDao.findWhereTlIdEquals(trlReqhandlerBean.getTravelId())[0];
					trlReqhandlerBean.setContactPerson(travelContactDetails.getContactPerson());
					trlReqhandlerBean.setPhoneNo(travelContactDetails.getPhoneNo());
					trlReqhandlerBean.setEmailId(travelContactDetails.getEmailId());
					trlReqhandlerBean.setAddress(travelContactDetails.getAddress());
				}

				Travel travelRow = travelDao.findByPrimaryKey(travelReqRow.getTlReqId());
				if (travelRow != null) {
					if (travelRow.getChargeCode() != 0) {
						ChargeCode chargeCode = chargeCodeDao.findByPrimaryKey(travelRow.getChargeCode());
						PoDetails poDetails = poDetailsDao.findByPrimaryKey(chargeCode.getPoId());
						Project project = projectDao.findByPrimaryKey(poDetails.getProjId());
						trlReqhandlerBean.setChargeCodeTittle(chargeCode.getChCodeName());
						trlReqhandlerBean.setChargeCodeName(chargeCode.getChCode());
						trlReqhandlerBean.setProjectId(project.getId());
						trlReqhandlerBean.setProjectName(project.getName());
					}
				}

				profileInfo2 = getProfileObjForUser(trlReqhandlerBean.getRaisedBy());
				trlReqhandlerBean.setRequestorName(profileInfo2.getFirstName()+ " " + profileInfo2.getLastName());
				EmpSerReqMap eReqMap = eMapDao.findByPrimaryKey(trlReqhandlerBean.getEsrqmId());

				// get last action taken by and action taken on
				TravelReq travelHandlerReqActionInfo[] = travelReqDao.findByDynamicSelect(
								"SELECT * FROM TRAVEL_REQ WHERE TL_REQ_ID=? AND ACTION_TYPE!=0 ORDER BY ID DESC",
								new Object[] { trlReqhandlerDto.getTlReqId() });
				if (travelHandlerReqActionInfo.length != 0) {
					ProfileInfo profileInfoActionInfo = getProfileObjForUser(travelHandlerReqActionInfo[0].getAssignedTo());
					trlReqhandlerBean.setApprovedRejBy(profileInfoActionInfo.getFirstName()+ " " + profileInfoActionInfo.getLastName());
					trlReqhandlerBean.setApprovedRejOn(PortalUtility.formatDateToddmmyyyyhhmmss(travelHandlerReqActionInfo[0].getDateOfCompletion()));
				}

				//approvers??....get list of approvers..handlers??..get list of handlers
				
				Handlers[] handlerObj = null;
				boolean onlyOneHandler = false;
				
				if(trlReqhandlerDto.getAppLevel()>0){
					//this request goes thru approvers
					
					TravellerTypeDao travellerTypeDao = TravellerTypeDaoFactory.create();
					TravellerType fetchedTravellerType = travellerTypeDao.findWhereTlIdEquals(trlReqhandlerDto.getTlReqId())[0];
					String presentLevelApproverIds = fetchedTravellerType.getNextSetOfApprovers().split("~=~")[0];//assuming self will be there ! !
					
					if(reqEscalation.isEscalationAction())
					{
						reqEscalation.setPermission(true);
						if(presentLevelApproverIds.endsWith(","))
							presentLevelApproverIds+=reqEscalation.getEscalationUserIdCsv();
						else
							presentLevelApproverIds+=","+reqEscalation.getEscalationUserIdCsv();
						
						reqEscalation.setPermission(false);
					}

					String[] approverIds = presentLevelApproverIds.split(",");
					
					int l = 0;
					handlerObj = new Handlers[approverIds.length];
					for(String eachApproverId : approverIds){						
						Handlers handlers = new Handlers();
						ProfileInfo profileInfo = getProfileObjForUser(Integer.parseInt(eachApproverId.trim()));
						handlers.setId(Integer.parseInt(eachApproverId.trim()));
						handlers.setName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());
						handlerObj[l] = handlers;
						l++;
					}					
					
				}else{
					//handlers
					procChain = pChainDao.findByPrimaryKey(eReqMap.getProcessChainId());
					ProcessEvaluator processEvaluator = new ProcessEvaluator();
					
					if(reqEscalation.isEscalationAction())
					{
						reqEscalation.setEscalationProcess(processEvaluator);
						//No need to disable Escalation permission as ProcessEvaluator is local in scope
						reqEscalation.enableEscalationPermission(processEvaluator);
					}

					Object[] users = processEvaluator.handlers(procChain.getHandler(), trlReqhandlerBean.getRaisedBy());
					//Handlers[] handlerObj = new Handlers[users.length];
					int l = 0;
					
					if(users.length==1){
						onlyOneHandler = true;
					}
					
					handlerObj = new Handlers[users.length];
					
					// set the handler list for dropdown
					for (Object user : users) {
						Handlers handlers = new Handlers();
						ProfileInfo profileInfo = getProfileObjForUser(Integer.parseInt(user.toString()));
						handlers.setId(Integer.parseInt(user.toString()));
						handlers.setName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());
						handlerObj[l] = handlers;
						l++;
					}
				}
				
				
				// setTravel(trlReqhandlerBean, trlReqhandlerDto);
				

				ItemCostInfo itemCostInfo[] = itemCostInfoDao.findByDynamicWhere(sqlItemInfo,new Object[] { trlReqhandlerDto.getTlReqId() });

				// set the values to ItemCostInfo Object
				if (itemCostInfo != null) {
					ItemCostInfo[] itemcostinfo = new ItemCostInfo[itemCostInfo.length];
					int m = 0;
					for (ItemCostInfo itemcost : itemCostInfo) {
						ItemCostInfo itemCostInfoDto2 = new ItemCostInfo();
						itemCostInfoDto2.setItem(itemcost.getItem());
						itemCostInfoDto2.setItemCost(itemcost.getItemCost());
						itemCostInfoDto2.setId(itemcost.getId());
						itemCostInfoDto2.setTlId(itemcost.getTlId());
						itemcostinfo[m] = itemCostInfoDto2;
						m++;
					}
					trlReqhandlerBean.setItemCostInfoArray(itemcostinfo);
				}
				if (trlReqhandlerBean.getDocumentsId() != 0) {
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					Documents documents = documentsDao.findByPrimaryKey(trlReqhandlerBean.getDocumentsId());
					trlReqhandlerBean.setDescriptions(documents.getDescriptions());
				}
				trlReqhandlerBean.setHandlers(handlerObj);
				
				Object[] sqlParams = { travelRow.getId() };
				TravelRequest travelRequest2 = travelDao.findTravelId(sqlParams);
				TravelReq[] travelReq = null;
				
				if (travelRequest2.getStatus() == Status.getStatusId(Status.PROCESSED)|| travelRequest2.getStatus() == Status.getStatusId(Status.REJECTED)) {
					int actionType = travelRequest2.getStatus();
					if (travelRequest2.getStatus() == Status.getStatusId(Status.PROCESSED)) {
						actionType = Status.getStatusId(Status.PROCESSED);
					}
					Object[] sqlParams4 = { travelRow.getId(), actionType };
					String sql2 = "SELECT * FROM TRAVEL_REQ WHERE TL_REQ_ID=? AND ACTION_TYPE=? ORDER BY DATE_OF_COMPLETION DESC";
					travelReq = travelReqDao.findByDynamicSelect(sql2,sqlParams4);
				}

				if (travelReq != null) {
					ProfileInfo profileInfo = getProfileObjForUser(travelReq[0].getAssignedTo());
					travelRequest2.setApprovedRejBy(profileInfo.getFirstName()+ " " + profileInfo.getLastName());
					travelRequest2.setApprovedRejOn(PortalUtility.formatDateToddmmyyyyhhmmss(travelReq[0].getDateOfCompletion()));
				}
				
				setTravel(trlReqhandlerBean, trlReqhandlerDto);
				
				trlHandlerForm.setEsrqmId(travelDao.findByPrimaryKey(trlHandlerForm.getTravelReqId()).getEsrqmId());
				
				//this is messed up
				//trlHandlerForm.setEsrqmId(travelDao.findByPrimaryKey(travelReqDao.findByPrimaryKey(trlHandlerForm.getTravelReqId()).getTlReqId()).getEsrqmId());//**
				
				final String commentsHistory = travelDao.getCommentsHistory(trlHandlerForm.getEsrqmId());//**
				List<String>commentsList = null;
				if(commentsHistory!=null){
					String[] brokenParts = commentsHistory.split(";");
					commentsList = new ArrayList<String>(brokenParts.length);
					for(String eachPart : brokenParts){
						String[] innerData = eachPart.split("~=~");
						String name = getProfileObjForUser(Integer.parseInt(innerData[1])).getFirstName();
						commentsList.add(name+" : "+innerData[0]);						
					}
				}
				
								
				/*
				 * separate logic to find out who is the assignee of a particular travel request
				 */
				String completedByQuery = "SELECT * FROM INBOX WHERE ESR_MAP_ID="+travelRow.getEsrqmId()+" AND SUBJECT LIKE 'TRAVEL DETAILS%' ORDER BY CREATION_DATETIME DESC";
				String assignedToQuery = "SELECT * FROM INBOX WHERE ESR_MAP_ID="+travelRow.getEsrqmId()+" AND SUBJECT LIKE 'YOU HAVE BEEN ASSIGNED%' ORDER BY CREATION_DATETIME DESC";
				InboxDao inboxDao = InboxDaoFactory.create();				
				Inbox[] inboxAction = null;
				if(travelRow.getStatus()==Status.getStatusId(Status.PROCESSED)){
					inboxAction = inboxDao.findByDynamicSelect(completedByQuery, null);
				}else{
					inboxAction = inboxDao.findByDynamicSelect(assignedToQuery, null);//WORK IN-PROGRESS    ASSIGNED
				}
				
				if(inboxAction!=null && inboxAction.length>0){
					ProfileInfo assigneePersonalInfo = getProfileObjForUser(inboxAction[0].getAssignedTo());
					trlReqhandlerBean.setAssignedToUser(assigneePersonalInfo.getFirstName() + " " + assigneePersonalInfo.getLastName());
				}else{
					//TRAVEL_REQ where ACTION_TYPE=27 || 28 with ESR_MAP_ID...get TL_REQ_ID....search in this table...read from beginning of this line 
				}
				
				trlReqhandlerBean.setApprovedRejBy(travelRequest2.getApprovedRejBy());
				trlReqhandlerBean.setApprovedRejOn(travelRequest2.getApprovedRejOn());
				
				
				//to receive all statuses

				com.dikshatech.portal.dto.Status[] status = null;
				//trlHandlerForm.setEsrqmId(travelDao.findByPrimaryKey(travelReqDao.findByPrimaryKey(trlHandlerForm.getTravelReqId()).getTlReqId()).getEsrqmId());
				Inbox[] fetchedInboxRows = inboxDao.findWhereEsrMapIdEquals(trlHandlerForm.getEsrqmId());
				if(fetchedInboxRows!=null && fetchedInboxRows.length>0){
					String inboxStatus = fetchedInboxRows[0].getStatus();
					if(inboxStatus.contains("Docked By ") || inboxStatus.equals(Status.WORKINPROGRESS)|| inboxStatus.equals(Status.CANCELINPROGRESS) || onlyOneHandler){
						//it was assigned to some handler
						status = new com.dikshatech.portal.dto.Status[4];
						com.dikshatech.portal.dto.Status inProgressStatus = null;
						
						if(trlHandlerForm.getStatusesForCancelRequest()==1){
							inProgressStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.CANCELINPROGRESS));					
						}else{		
							inProgressStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.WORKINPROGRESS));					
						}
						com.dikshatech.portal.dto.Status assigned = statusDao.findByPrimaryKey(Status.getStatusId(Status.ASSIGNED));
						assigned.setId(Status.getStatusId(Status.REQUESTRAISED));
						com.dikshatech.portal.dto.Status completeStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.PROCESSED));
						completeStatus.setStatus(Status.PROCESSED);			
						com.dikshatech.portal.dto.Status tempStatus = new com.dikshatech.portal.dto.Status();
						tempStatus.setStatus("Select a Status");
						status[0]=tempStatus;
						status[1]=assigned;
						status[2]=inProgressStatus;
						status[3]=completeStatus;
					}else{
						status = new com.dikshatech.portal.dto.Status[2];
						com.dikshatech.portal.dto.Status assigned = statusDao.findByPrimaryKey(Status.getStatusId(Status.ASSIGNED));
						assigned.setId(Status.getStatusId(Status.REQUESTRAISED));
						com.dikshatech.portal.dto.Status tempStatus = new com.dikshatech.portal.dto.Status();
						tempStatus.setStatus("Select a Status");
						status[0]=tempStatus;
						status[1]=assigned;					
					}
				}				
				trlReqhandlerBean.setTravelReqId(travelRow.getId());//fix
				trlReqhandlerBean.setStatusArray(status);
				
				HandlerAction handlerAction = new HandlerAction();
				
				fetchedInboxRows = inboxDao.findByDynamicWhere(" ESR_MAP_ID=? AND ASSIGNED_TO=? ", new Object[]{(travelRow.getEsrqmId()),login.getUserId()});
				if(fetchedInboxRows!=null && fetchedInboxRows.length>0){
					handlerAction.setAssign(1);
				}else{
					handlerAction.setAssign(0);
				}
				trlReqhandlerBean.setHandlerAction(handlerAction);
				
				if(commentsHistory!=null){
					trlReqhandlerBean.setCommentsHistory(commentsList.toArray());
				}			
				
				TravelReq[] fetchedTravelReq = travelReqDao.findByDynamicWhere(" TL_REQ_ID=? AND APP_LEVEL>0 AND ACTION_TYPE>0", new Object[]{trlHandlerForm.getTravelReqId()});
				if(fetchedTravelReq!=null && fetchedTravelReq.length>0){
					ProfileInfo approverInfo = getProfileObjForUser(fetchedTravelReq[0].getAssignedTo());
					trlReqhandlerBean.setApprovedRejBy(approverInfo.getFirstName()+" "+approverInfo.getLastName());
					trlReqhandlerBean.setApprovedRejOn(PortalUtility.formatDateToddmmyyyyhhmmss(fetchedTravelReq[0].getDateOfCompletion()));
				}
				trlReqhandlerBean.setCreatedDate(PortalUtility.formatDateToddmmyyyyhhmmss(travelDao.findByPrimaryKey(trlHandlerForm.getTravelReqId()).getCreateDate()));
				
				
				request.setAttribute("actionForm", trlReqhandlerBean);
				break;
					
					
					
			case RECEIVEALLSTATUS:
				/*
				ArrayList<com.dikshatech.portal.dto.Status> status = new ArrayList<com.dikshatech.portal.dto.Status>();
				com.dikshatech.portal.dto.Status inProgressStatus = null;
				
				if(travelForm.getStatusesForCancelRequest()==1){
					inProgressStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.CANCELINPROGRESS));		
				}else{		
					inProgressStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.WORKINPROGRESS));		
				}
				com.dikshatech.portal.dto.Status assigned = statusDao.findByPrimaryKey(Status.getStatusId(Status.WORKINPROGRESS));
				com.dikshatech.portal.dto.Status completeStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.PROCESSED));
				status.add(inProgressStatus);
				status.add(completeStatus);	*/
				break;
			

			case RECEIVEALLTOAPPROVE://this is same as RECEIVEALLTOHANDLE except for APP_LEVEL IS NOT NULL
				
				trltoHandlerForm = (DropDown) form;	
								
				query = new StringBuffer("ASSIGNED_TO=? AND APP_LEVEL IS NOT NULL");
								
				if (trltoHandlerForm.getMonth() != null || trltoHandlerForm.getSearchyear() != null || trltoHandlerForm.getSearchName() != null){
					if (trltoHandlerForm.getMonth() != null && trltoHandlerForm.getToMonth() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE (MONTH(TR.CREATE_DATE) BETWEEN " + trltoHandlerForm.getMonth() + " AND " + trltoHandlerForm.getToMonth() + ")) ");
					else if (trltoHandlerForm.getMonth() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE MONTH(TR.CREATE_DATE)=" + trltoHandlerForm.getMonth() + " ) ");
					if (trltoHandlerForm.getSearchyear() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE YEAR(TR.CREATE_DATE)=" + trltoHandlerForm.getSearchyear() + ") ");
					if (trltoHandlerForm.getSearchName() != null) query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE (SELECT CONCAT(PF.FIRST_NAME,' ',PF.LAST_NAME) FROM PROFILE_INFO PF WHERE PF.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=TR.TRL_USER_ID)) LIKE '%" + trltoHandlerForm.getSearchName() + "%') ");
				} else query.append(" AND TL_REQ_ID IN (SELECT TR.ID FROM TRAVEL TR WHERE TIMESTAMPDIFF(DAY,TR.CREATE_DATE,NOW()) <= 60)");
				trlRqHandler = travelReqDao.findByDynamicWhere(query.toString(), new Object[]{login.getUserId()});
				
				/*int approverCount = 0;
				Travel[] allRowsFetched = travelDao.findAll();
				for(Travel eachTravelRow : allRowsFetched){
					Inbox[] approverInboxEntry = InboxDaoFactory.create().findByDynamicWhere(" ESR_MAP_ID=? AND ASSIGNED_TO=?", new Object[]{eachTravelRow.getEsrqmId(),login.getUserId()});
						if(approverInboxEntry!=null && approverInboxEntry.length>0){
							if(approverInboxEntry[0].getStatus().equalsIgnoreCase("REQUEST RAISED") || approverInboxEntry[0].getStatus().startsWith("Docked By")){
								approverCount++;
							}
						}					
				}	*/			
				
				
				//trlRqHandler2 = travelDao.findByDynamicWhere(" STATUS NOT IN(27,28,30,33)",null);
				inboxdao = InboxDaoFactory.create();
				travelReqestBean = new TravelRequest[trlRqHandler.length];
				k = 0;
				for (TravelReq tlReq : trlRqHandler) {
					handlerAction = new HandlerAction();
					Object[] sqlParamtl = new Object[] { tlReq.getTlReqId() };
					TravelRequest trvlReq = travelDao.findTravelId(sqlParamtl);
					ProfileInfo profileInfo = getProfileObjForUser(trvlReq.getRaisedBy());
					trvlReq.setRequestorName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());

					/**
					 * set the handler Action,to send flag as active or
					 * inactive(1 is active,0 is inactive)
					 **/
					Travel travelTemp = travelDao.findByPrimaryKey(tlReq.getTlReqId());
					int esrMapId = travelTemp.getEsrqmId();
					Inbox[] inboxRows =  inboxdao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[]{esrMapId,login.getUserId()});
					if(inboxRows!=null && inboxRows.length>0){
						Travel travelDetails = travelDao.findByPrimaryKey(tlReq.getTlReqId());
						if(travelDetails.getStatus()!=Status.getStatusId(Status.REVOKED) && travelDetails.getStatus()!=Status.getStatusId(Status.REJECTED)){
							if(!(inboxRows[0].getStatus().equalsIgnoreCase(Status.APPROVED))){
								fetchedTravelReq = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[]{travelTemp.getId(),login.getUserId()});
								if(fetchedTravelReq!=null && fetchedTravelReq.length>0){
									if(fetchedTravelReq[0].getAppLevel()>0){
										//approver
										handlerAction.setAssign(1);
									}else{
										//handler
										handlerAction.setAssign(0);
									}
								}
								
							}
						}
													
					}
									
									
					setTravel(trvlReq, tlReq);
					
					inboxRows =  inboxdao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? ", new Object[]{esrMapId});
					if(inboxRows!=null && inboxRows.length>0){
						trvlReq.setTrlreqStatusName(inboxRows[inboxRows.length-1].getStatus());//**
					}else{
						//it is serviced....need to get its status from TRAVEL
						trvlReq.setTrlreqStatusName(Status.getStatus(travelDao.findWhereEsrqmIdEquals(esrMapId)[0].getStatus()));//**
					}
					
					if(tlReq.getActionType()>0){
						trvlReq.setTrlreqStatusName(Status.getStatus(tlReq.getActionType()));
					}
					
					//**correction for status in HANDLER tab
					if(travelDao.findWhereEsrqmIdEquals(esrMapId)[0].getStatus()==Status.getStatusId(Status.REVOKED)){
						trvlReq.setTrlreqStatusName(Status.getStatus(travelDao.findWhereEsrqmIdEquals(esrMapId)[0].getStatus()));//**
					}
					
					trvlReq.setHandlerAction(handlerAction);
					itemCostInfo = itemCostInfoDao.findByDynamicWhere(sqlItemInfo,new Object[] { tlReq.getId() });

					// set the values to ItemCostInfo Object
					if (itemCostInfo != null) {
						ItemCostInfo[] itemcostinfo = new ItemCostInfo[itemCostInfo.length];
						int m = 0;
						for (ItemCostInfo itemcost : itemCostInfo) {
							ItemCostInfo itemCostInfoDto = new ItemCostInfo();
							itemCostInfoDto.setItem(itemcost.getItem());
							itemCostInfoDto.setItemCost(itemcost.getItemCost());
							itemCostInfoDto.setId(itemcost.getId());
							itemCostInfoDto.setTlId(itemcost.getTlId());
							itemcostinfo[m] = itemCostInfoDto;
							m++;
						}
						trvlReq.setItemCostInfoArray(itemcostinfo);

					}
					travelReqestBean[k] = trvlReq;
					//fix
					travelReqestBean[k].setTravelReqId(trvlReq.getTravelId());
					k++;
				}
				//dropDown.setCount(trlRqHandler2.length);changed for approver
				
				//fix for count
				Travel[] travelDetails = travelDao.findByDynamicWhere(" STATUS NOT IN(27,28,29,7) AND RAISED_BY != ? ", new Object[]{login.getUserId()});//27 PROCESSED			28 REVOKED			29 REQUEST SAVED			7 ACCEPTED
				int count = 0;
				List<Integer>esrIdList = new ArrayList<Integer>();
				HashMap<Integer, Integer>idMap = new HashMap<Integer, Integer>();
				
				if(travelDetails!=null && travelDetails.length>0){
					List<Integer>tlIdList = new ArrayList<Integer>(travelDetails.length); 
					for(Travel eachTravelrow : travelDetails){
						tlIdList.add(eachTravelrow.getId());
						esrIdList.add(eachTravelrow.getEsrqmId());
						idMap.put(eachTravelrow.getId(), eachTravelrow.getEsrqmId());
					}
					List<Integer>actionTakenIdList = new ArrayList<Integer>();
					
					for(Iterator<Integer>idIter = tlIdList.iterator();idIter.hasNext();){
						Integer id = idIter.next();
						TravelReq[] actionTakenReq = travelReqDao.findByDynamicSelect("SELECT * FROM TRAVEL_REQ WHERE TL_REQ_ID=? AND ACTION_TYPE != 0 AND APP_LEVEL>0", new Object[]{id});
						if(actionTakenReq!=null && actionTakenReq.length>0){
							actionTakenIdList.add(actionTakenReq[0].getTlReqId());
						}					
					}
					
					count = tlIdList.size() - actionTakenIdList.size();
					
					//for those Docked By and Work -In- Progress
					for(Iterator<Integer>idIter = actionTakenIdList.iterator();idIter.hasNext();){
						Integer tlId = idIter.next();
						Integer esrMapId = idMap.get(tlId);
						Inbox[] fetchedInboxRow = inboxdao.findWhereEsrMapIdEquals(esrMapId);
						if(fetchedInboxRow != null && fetchedInboxRow.length>0){
							if(fetchedInboxRow[0].getAssignedTo() == login.getUserId()){
								count++;
							}
						}
						
					}
				}
				
				
				dropDown.setCount(count);
				dropDown.setDropDown(travelReqestBean);
				request.setAttribute("actionForm", dropDown);
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoreceive"));
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		boolean flag = false;
		Login login = Login.getLogin(request);
		TravelForm tForm = (TravelForm) form;
		TravelDao travelDao = TravelDaoFactory.create();
		EmpSerReqMapPk eMapPk = new EmpSerReqMapPk();
		Travel travelDto = new Travel();
		Travel trvl = new Travel();
		TravelPk travelPk=new TravelPk();
		Login loginUSer = Login.getLogin(request);
		TravelPk travelPk2 = new TravelPk();
		TravellerType travellerType=new TravellerType();
		TravellerTypeDao travellerTypeDao=TravellerTypeDaoFactory.create();
		ProcessEvaluator processEvaluator=new ProcessEvaluator();
		TravelContactDetailsDao travelContactDetailsDao=TravelContactDetailsDaoFactory.create();
		
		switch (ActionMethods.SaveTypes.getValue(form.getsType()))
		{
				/**
				 * while saving the Travel the Request will not generate for
				 * Emp_ser_req_map so ESRQM_ID will be null
				 **/

			case SAVE:
				tForm.setRaisedBy(login.getUserId());
				Travel travel = tForm.getTravel(tForm);
					
				//tForm.setIsBussinessType(0); hard coded... not required
					
			try {
				travelPk = saveTravel(travel, null, Status.getStatusId(Status.REQUESTSAVED));
				travellerType.setTlId(travelPk.getId());
				travellerType.setIsBussinessType(tForm.getIsBussinessType());
				try {

					travellerTypeDao.insert(travellerType);

				} catch (TravellerTypeDaoException e) {
					logger.error("failed to save traveller type", e);
					if (travelPk != null) {
						if (travelPk != null) {
							TravelContactDetails travelContactDetails[] = travelContactDetailsDao.findWhereTlIdEquals(travelPk.getId());
							if (travelContactDetails.length > 0) {
								travelContactDetailsDao.delete(new TravelContactDetailsPk(travelContactDetails[0].getId()));
							}
						}
						tDao.delete(travelPk);

					}
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.contactDetail"));
					throw new Exception();
				}

			} catch (TravelDaoException trExc) {
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.travel "));
				logger.error("failed to save,unable to insert the data to travel:",trExc);
				trExc.printStackTrace();

			} catch (TravelContactDetailsDaoException trcExc) {
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.contactDetail"));
				logger.error(trcExc);
				logger.error("check contact information");
				trcExc.printStackTrace();
				try {
					tDao.delete(travelPk);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.travel "));
				logger.error("failed to save,unable to insert the data to travel:",e);
				e.printStackTrace();

			}
			break;
					
					
					
				/**
				 * while submit the new entry will go for TRAVEL_REQ
				 * and new entry will go for EMP_SER_REQ_MAP
				 **/

					
			case SUBMIT:
					
			try {
				Travel travelDtoBackup = null;
				setProcChain(request);
				String validateHandlers = processEvaluator.validateProcessChain(procChain, loginUSer.getUserId(),false, true);
				String nextSetOfApprovers = null;
				
				if(tForm.getIsBussinessType()==1){
					//validate Approvers for self
					String validateApprovers = processEvaluator.validateProcessChain(procChain, loginUSer.getUserId(),true, false);
					try {
						Integer.parseInt(validateApprovers);
						String approvalChain = procChain.getApprovalChain();//102,103,104;99,56;32
						int maxApprovalLevelAvailable = approvalChain.split(";").length;
						String tempApprover = null;
						
						for(int approverLevel=1;approverLevel<=maxApprovalLevelAvailable;approverLevel++){							
							tempApprover = "~=~";//~=~1,2,3,4,5,6,7,8,9~=~55,56,57~=~99
							tempApprover = tempApprover+Arrays.asList(processEvaluator.approvers(approvalChain,approverLevel,loginUSer.getUserId())).toString().replace('[',' ').replace(']',' ').trim();
							tempApprover = tempApprover.replaceAll(", ", ",");
							if(nextSetOfApprovers==null){
								nextSetOfApprovers = tempApprover;
							}else{
								nextSetOfApprovers = nextSetOfApprovers+tempApprover;
							}
							
						}
						nextSetOfApprovers = nextSetOfApprovers.replaceFirst("~=~", " ").trim();
						
						
						//all the approvers from all levels are obtained....persist this in TRAVELLER_TYPE
						
						
					} catch (NumberFormatException nfex) {
						logger.error("SERVICE REQUEST PRE-PREPROCESS FAILED FOR REQUESTER_ID="+ login.getUserId()+ " SERVICE_REQUEST IS TRAVEL ERR_CODE="+ validateApprovers);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.servicerequest",validateApprovers));
						result.setForwardName("success");
						return result;
					}
				}			
				
				
				//validate Handlers
				try {
					Integer.parseInt(validateHandlers);
				} catch (NumberFormatException nfex) {
					logger.error("SERVICE REQUEST PRE-PREPROCESS FAILED FOR REQUESTER_ID="+ login.getUserId()+ " SERVICE_REQUEST IS TRAVEL ERR_CODE="+ validateHandlers);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.servicerequest",validateHandlers));
					result.setForwardName("success");
					return result;
				}

				eMapPk = saveESRM(request);

				if (eMapPk.getId() != 0) {
					if (tForm.getTravelId() == 0) {
						tForm.setRaisedBy(login.getUserId());
						travelDto = tForm.getTravel(tForm);
						travelDto.setEsrqmId(eMapPk.getId());
						try {
							travelPk2 = saveTravel(travelDto, new Date(),Status.getStatusId(Status.REQUESTRAISED));
							//if (tForm.getIsBussinessType() == 1){
								travellerType.setTlId(travelPk2.getId());
								travellerType.setIsBussinessType(tForm.getIsBussinessType());
								travellerType.setNextSetOfApprovers(nextSetOfApprovers);
								try {
									travellerTypeDao.insert(travellerType);
								} catch (TravellerTypeDaoException e) {
									logger.error("failed to save traveller type", e);
									if (travelPk2 != null) {
										TravelContactDetails travelContactDetails[] = travelContactDetailsDao.findWhereTlIdEquals(travelPk2.getId());
										if (travelContactDetails.length > 0) {
											travelContactDetailsDao.delete(new TravelContactDetailsPk(travelContactDetails[0].getId()));
										}
										eMapDao.delete(eMapPk);
										result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.contactDetail"));
									}
									throw new TravellerTypeDaoException("could not save travel information");
								}
							//}

						} catch (TravelDaoException trExc) {

							if (travelPk2 != null) {
								TravelContactDetails travelContactDetails[] = travelContactDetailsDao.findWhereTlIdEquals(travelPk2.getId());
								if (travelContactDetails.length > 0) {
									travelContactDetailsDao.delete(new TravelContactDetailsPk(travelContactDetails[0].getId()));
								}
								eMapDao.delete(eMapPk);
							}
							throw new TravelDaoException("travel dao exception");
						} catch (TravelContactDetailsDaoException trcExc) {
							result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.contactDetail"));
							logger.error(trcExc);
							logger.error("check contact information");
							trcExc.printStackTrace();
							if (travelPk2 != null) {
								try {
									TravelContactDetails travelContactDetails[] = travelContactDetailsDao.findWhereTlIdEquals(travelPk2.getId());
									if (travelContactDetails.length > 0) {
										travelContactDetailsDao.delete(new TravelContactDetailsPk(travelContactDetails[0].getId()));
									}
									eMapDao.delete(eMapPk);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							throw new TravelDaoException("travel dao exception");
						}
						tForm.setTravelId(travelPk2.getId());
					} else {
						//during save the data was saved in TRAVELLER_TYPE..now update the nextSetOfApprovers
						
						TravellerType existingTravellerTypeRow = travellerTypeDao.findWhereTlIdEquals(tForm.getTravelId())[0];
						existingTravellerTypeRow.setNextSetOfApprovers(nextSetOfApprovers);
						travellerTypeDao.update(new TravellerTypePk(existingTravellerTypeRow.getId()), existingTravellerTypeRow);
						
						travelPk2.setId(tForm.getTravelId());
						travelDtoBackup = travelDao.findByPrimaryKey(travelPk2.getId());
						travelDto = travelDao.findByPrimaryKey(travelPk2.getId());
						trvl = travelDto;
						travelDto.setStatus(Status.getStatusId(Status.REQUESTRAISED));
						travelDto.setCreateDate(new Date());
						travelDto.setEsrqmId(eMapPk.getId());
						try {
							travelDao.update(travelPk2, travelDto);
						} catch (Exception e) {
							throw new Exception();
						}

					}
					if (travelPk2.getId() != 0) {
						try {
							updateESRM(eMapPk, travelPk2.getId());
						} catch (Exception e) {
							travelDao.update(travelPk2, travelDtoBackup);
							logger.error("failed to update empservice request map");
							e.printStackTrace();
							throw new Exception();
						}
					}
					TravelReq travelReq = tForm.getTravelReq(tForm);

					// submit will return a flag true or false,depending
					// on the entry has done successfully in TRAVEL_REQ
					try {
						travelDto.setIsBussinessType(tForm.getIsBussinessType());
						flag = submit(travelDto, travelReq, procChain, eMapPk,login);
					} catch (TravelMailException e) {
						throw new TravelMailException();
					} catch (TravelNotificationException e) {
						throw new TravelNotificationException();
					} catch (Exception e) {
						logger.error("failed to submit", e);
						e.printStackTrace();
						throw new Exception();
					}
					finally {
						if (!flag) {
							try {
								if (travelDtoBackup != null) {
									travelDtoBackup.setEsrqmId(0);
									travelDao.update(new TravelPk(travelDtoBackup.getId()),	travelDtoBackup);
								}
								eMapDao.delete(eMapPk);
								if (tForm.getTravelId() != 0) {
									trvl.setEsrqmId(0);
									trvl.setStatus(Status.getStatusId(Status.REQUESTSAVED));
									trvl.setCreateDate(null);
									travelDao.update(travelPk2, trvl);
								}
							} catch (EmpSerReqMapDaoException e) {
								logger.error(e);
								e.printStackTrace();
							}

						}
					}

				}
			}catch (TravellerTypeDaoException trExp) {
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.travel "));
					logger.error("failed to save,unable to insert the data to travel:",trExp);
					trExp.printStackTrace();					
				}catch (TravelDaoException trDaoExp) {
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.save.failed.travel "));
					logger.error("failed to save,unable to insert the data to travel:",trDaoExp);
					trDaoExp.printStackTrace();
				}catch(TravelNotificationException trvNotifExp){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest","ERR-007"));
					logger.error("failed to send notification",trvNotifExp);
					trvNotifExp.printStackTrace();				
				}catch (TravelMailException trvlMailExp) {
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest","ERR-006"));
					logger.error("failed to send notification",trvlMailExp);
					trvlMailExp.printStackTrace();
				}catch (Exception e) {
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.submit.failed.travel"));
					logger.error("failed to submit",e);
					e.printStackTrace();
				}	

					break;
		}
		return result;
	}

	/*
	 *  submit method is used for direct submit,update and submit
	 */

	private boolean submit(Travel travelDto, TravelReq travelReq,ProcessChain procChain, EmpSerReqMapPk eMapPk, Login login) 
					throws Exception, TravellerTypeDaoException, TravelMailException {
		
		//TravelReqDao travelReqDao = TravelReqDaoFactory.create();

		boolean flag = false;
		Integer[] users = null;
		travelReq.setStatus(Status.getStatusId(Status.REQUESTRAISED));
		travelReq.setActionType(0);

		ProcessEvaluator processEvaluator = new ProcessEvaluator();
		
		// self... approvers required
		if (travelDto.getIsBussinessType() == 1) {
			users = processEvaluator.approvers(procChain.getApprovalChain(), 1,	login.getUserId());
			logger.error("users in the approval chain");
		} else {
			//others.... approvers not requried
			users = processEvaluator.handlers(procChain.getHandler(), login.getUserId());
			logger.error("users in the handler chain");
		}

		// save the data into TRAVEL_REQ
		flag = saveTravelReq(travelReq, users, eMapPk.getId(), login,procChain,travelDto.getIsBussinessType());
		
		/*if (flag == true) {

			// send mail as multiple recipient
			ProfileInfo raisedbyUserProfileInfo = getProfileObjForUser(travelDto.getRaisedBy());
			PortalMail portalMail = new PortalMail();
			portalMail.setMessageBody(null);
			portalMail.setMailSubject("Your travel request has been submitted successfully.");
			portalMail.setTemplateName(MailSettings.NOTIFICATION_TRAVEL_REQUEST_SUBMITTED);
			portalMail.setRecipientMailId(raisedbyUserProfileInfo.getOfficalEmailId());
			portalMail.setEmpFname(raisedbyUserProfileInfo.getFirstName());
			String msgBody = null;
			try {
				msgBody = sendEmail(travelReq.getId(), false, portalMail);
			} catch (Exception e) {
				flag = false;
				throw new TravelMailException();
			}
			travelReq.setMessageBody(msgBody);
			travelReqDao.update(new TravelReqPk(travelReq.getId()), travelReq);

			sendToInboxForTravelRequest(eMapPk.getId(), travelReq.getId(),"Your travel request has been submitted successfully.",Status.REQUESTRAISED, travelDto.getRaisedBy());
			//int esrMapId, int trvlreqId,String subject, String status,int userId)
		}*/

		return flag;
	}

	private void setProcChain(HttpServletRequest request) {
		Login login = Login.getLogin(request);
		UserLogin userLogin = login.getUserLogin();

		Object[] objs = userLogin.getRoles().toArray();

		Roles roles = (Roles) objs[0];
		Object[] sqlParams = { roles.getRoleId() };
		try {
			procChain = pChainDao.findByDynamicWhere("ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION WHERE ROLE_ID = ? AND MODULE_ID = 23 )",sqlParams)[0];
		} catch (ProcessChainDaoException e) {
			logger.error("failed to initialize processchain:", e);
			e.printStackTrace();
		}
	}


	private boolean updateESRM(EmpSerReqMapPk eMapPk, int travelId) throws Exception
	{
		boolean result = false;
		try
		{
			EmpSerReqMap eReqMap = eMapDao.findByPrimaryKey(eMapPk);
			eReqMap.setReqId(eReqMap.getReqId() + travelId);
			eMapDao.update(eMapPk, eReqMap);
			result = true;
		} catch (EmpSerReqMapDaoException e)
		{
			
			logger.error("failed to update ESRMAP:",e);
			e.printStackTrace();
			throw new Exception();
		}

		return result;
	}

	private EmpSerReqMapPk saveESRM(HttpServletRequest request) throws Exception
	{

		EmpSerReqMapPk eMapPk = new EmpSerReqMapPk();
		EmpSerReqMap eReqMap = new EmpSerReqMap();
		Login login = Login.getLogin(request);
		ModulePermissionDao mDao = ModulePermissionDaoFactory.create();
		RegionsDao rDao = RegionsDaoFactory.create();

		try
		{
			UserLogin userLogin = login.getUserLogin();
			Regions regions = rDao.findByPrimaryKey(userLogin.getRegionId());
			Object[ ] objs = userLogin.getRoles().toArray();
			Roles roles = (Roles) objs[0];

			ModulePermission mPermission = mDao.findByRoleAndModule(roles.getRoleId(), 23);

			eReqMap.setSubDate(new Date());
			eReqMap.setReqTypeId(2);
			eReqMap.setRegionId(userLogin.getRegionId());
			eReqMap.setRequestorId(login.getUserId());
			eReqMap.setProcessChainId(mPermission.getProcChainId());
			eReqMap.setReqId(regions.getRefAbbreviation() + "-TL-");
			eMapPk = eMapDao.insert(eReqMap);
		} catch (Exception e) {
			logger.error("failed to save empservice request:",e);
			throw new Exception();
		}

		return eMapPk;
	}

	private TravelPk saveTravel(Travel travel, Date date, int status) throws TravelContactDetailsDaoException, TravelDaoException 
	{
		TravelPk travelPk = new TravelPk();

		travel.setStatus(status);
		travel.setCreateDate(date);
		
			travelPk = tDao.insert(travel);
			if(travel.getIsContactPersonReq()==1)
			{
				TravelContactDetailsDao travelContactDetailsDao=TravelContactDetailsDaoFactory.create();
				TravelContactDetails travelContactDetails=new TravelContactDetails();
				travelContactDetails.setTlId(travelPk.getId());
				travelContactDetails.setContactPerson(travel.getContactPerson());
				travelContactDetails.setEmailId(travel.getEmailId());
				travelContactDetails.setPhoneNo(travel.getPhoneNo());
				travelContactDetails.setAddress(travel.getAddress());
				
				try{
					travelContactDetailsDao.insert(travelContactDetails);
				}catch (Exception e) {
					logger.error("failed to save travelcontactdetails:",e);
					tDao.delete(travelPk);
				}
				
			}
		

		return travelPk;
	}

	private Boolean saveTravelReq(TravelReq travelReq, Integer[ ] handlerIds,int esrMapId,Login login,ProcessChain procChain,int approversRequired) 
		throws Exception,TravelNotificationException {

		boolean flag = true;
		PortalMail portalMail = new PortalMail();
		TravelReqPk travelReqPk = null;
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(esrMapId);
		
		TravelDao travelDao = TravelDaoFactory.create();
		Travel travelDetails = travelDao.findByPrimaryKey(travelReq.getTlReqId());
		
		int travellerUserId = travelDetails.getTrlUserId();		
		
		Integer[] notifierIds = new ProcessEvaluator().notifiers(procChain.getNotification(), travellerUserId);//HRSPOC & RM  was login.getUserId()...changed to travellerUserId
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		Set<String> mailIds = new HashSet<String>();
		
		for(Integer eachNotifierId : notifierIds){
			mailIds.add(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(eachNotifierId).getProfileId()).getOfficalEmailId());
		}
		
		if(mailIds.size()>0){
			//Mail.CC available
			mailIds.remove(null);
			String[] allReceipientCcMailIds = (String[]) Arrays.copyOf(mailIds.toArray(), mailIds.toArray().length,String[].class);
			portalMail.setAllReceipientcCMailId(allReceipientCcMailIds);
		}
		
		
		mailIds.clear();//remove all the mailIds 
		 
		
		for(Integer eachHandlerId : handlerIds){//Handlers from process-chain
			mailIds.add(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(eachHandlerId).getProfileId()).getOfficalEmailId());
		}
		
		if(mailIds.size()>0){
			//Mail.TO available
			mailIds.remove(null);
			String[] allReceipientMailId = (String[]) Arrays.copyOf(mailIds.toArray(), mailIds.toArray().length,String[].class);
			portalMail.setAllReceipientMailId(allReceipientMailId);
		}
		
		ProfileInfo requesterProfileInfo = getProfileObjForUser(login.getUserId());
		portalMail.setMailSubject("Diksha Lynx: New Travel request ["+empSerReqMap.getReqId()+"] by ["+requesterProfileInfo.getFirstName() + " "	+ requesterProfileInfo.getLastName() +"]");
		
		//select suitable template for approvers and handlers
		if(approversRequired>0){
			portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_ASSIGNED);//check this..if required change the template used   
		}else{
			portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_ASSIGNED);
		}
		
		
		Integer[] insertedIds = new Integer[handlerIds.length];
		int i = 0 ;
		for(Integer eachHandlerId : handlerIds){
			travelReqPk = new TravelReqPk();
			if(approversRequired==1){
				travelReq.setAppLevelNull(false);
				travelReq.setAppLevel(1);//check this
			}else{
				travelReq.setAppLevelNull(true);
			}			
			travelReq.setAssignedTo(Integer.parseInt(eachHandlerId.toString()));
			travelReq.setId(0);
			travelReq.setDateOfCompletion(new Date());
			travelReq.setMessageBody(null);
			insertedIds[i++]=tReqDao.insert(travelReq).getId();
		}
				
		
		String portalMailbody = sendEmail(insertedIds[0], true, portalMail);
			
		try {
			int index = 0;
			while(index<insertedIds.length){
				tReqDao.executeUpdate("UPDATE TRAVEL_REQ SET MESSAGE_BODY='"+portalMailbody+"' WHERE ID="+insertedIds[index]);
				// populate inbox for all available handlers
				Inbox inbox = sendDockingStationForTravel(insertedIds[index++],portalMail.getMailSubject(), empSerReqMap,	Status.REQUESTRAISED);	
			}
					
		} catch (TravelNotificationException trvNotifExc) {
			logger.error("failed to save travelrequest:", trvNotifExc);
			flag = false;
			trvNotifExc.printStackTrace();
			throw new TravelNotificationException();
		} catch (Exception e) {
			flag = false;
			tReqDao.delete(travelReqPk);
			logger.error("failed to save travelrequest:", e);
			e.printStackTrace();
			throw new Exception();
		}
		return flag;
	}

	
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		TravelForm travelForm = (TravelForm) form;
		TravelReqDao travelReqDao = TravelReqDaoFactory.create();
		TravelReqPk travelReqPk = new TravelReqPk();
		TravelDao travelDao = TravelDaoFactory.create();
		TravelPk travelPk = new TravelPk();

		EmpSerReqMapPk eMapPk = new EmpSerReqMapPk();
		TravelReq travelReq = new TravelReq();

		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String SQLProfile = "ID = (SELECT ESRQM_ID FROM TRAVEL WHERE ID = ?)";

		RegionsDao regionDao = RegionsDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		DivisonDao divisonDao = DivisonDaoFactory.create();

		TravellerTypeDao travellerTypeDao = TravellerTypeDaoFactory.create();
		
		Login loginUSer = Login.getLogin(request);
		ProcessEvaluator processEvaluator = new ProcessEvaluator();

		try{
			//get finance head info 
			Users userLogin=usersDao.findByPrimaryKey(login.getUserId());
			Levels levels=levelsDao.findByPrimaryKey(userLogin.getLevelId());
			Divison divison=divisonDao.findByPrimaryKey(levels.getDivisionId());
			Regions region = regionDao.findByPrimaryKey(divison.getRegionId());
			Notification notification = new Notification(region.getRefAbbreviation());
			
			int  financeHeadLevelId= notification.financeHeadLevelId;
			int rmgMgrLevelId = notification.rmgManagerLevelId;
			
			Users financeHeadUsers[]=null;
			Users rmgManagers[] = null;
			
			
			try{
				financeHeadUsers = usersDao.findByDynamicSelect(selectUsers+ "WHERE LEVEL_ID=? AND STATUS=0;", new Object[]{ financeHeadLevelId});
			}catch (Exception e) {
				logger.error("failed to find finance head info",e);				
			}
			
			
			try{
				rmgManagers = usersDao.findByDynamicSelect(selectUsers+ "WHERE LEVEL_ID=? AND STATUS=0;", new Object[]{ rmgMgrLevelId});
			}catch(Exception e){
				logger.error("failed to find Sr.Manager RMG info",e);		
			}
			
			switch (ActionMethods.UpdateTypes.getValue(form.getuType())){
			
			case ACCEPTREJECT:{
				
				InboxDao inboxDao = InboxDaoFactory.create();
				
				//common stuff for any ActionType taken
				EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
				ProcessChainDao processChainDao = ProcessChainDaoFactory.create();

				travelReq = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND ASSIGNED_TO=?", new Object[]{travelForm.getTravelReqId(),loginUSer.getUserId()})[0];
				travelReqPk.setId(travelReq.getId());//TRAVEL_DETAILS			
				
				travelReq.setActionType(travelForm.getActionType());
				travelReq.setComment(travelForm.getComment());//check this vijay
				
				//get all the approvers of present level
				TravellerType travellerType = travellerTypeDao.findWhereTlIdEquals(travelReq.getTlReqId())[0];										
				String[] nextSetOfApprovers = travellerType.getNextSetOfApprovers().split("~=~");//variable name is confusing 				
				
				List<String> presentLevelApproverIds = Arrays.asList(nextSetOfApprovers[0].split(","));
				
				//Set escalation action
				int escTravelId=travelForm.getTravelReqId();
				Travel escTravelDetail=travelDao.findByPrimaryKey(escTravelId);
				if(escTravelDetail!=null)
				{
					int escEsrMapId=escTravelDetail.getEsrqmId();
					int escUserId=login.getUserId();

					reqEscalation.setEscalationAction(escEsrMapId, escUserId);
					if(reqEscalation.isEscalationAction())
					{
						List<String> escActorIds=reqEscalation.getEscalationUserIdStringList();
						//Updates current approver list to include escalation actors
						//Because Arrays.asList() returns fixed size collection following loop adds
						//from presentLevelApproverIds to escActorIds
						for(String id:presentLevelApproverIds)
						{
							if(!escActorIds.contains(id))
								escActorIds.add(id);
						}

						presentLevelApproverIds=escActorIds;
					}
				}
				else
					logger.info("Unable to find Travel record for ID: "+escTravelId);
				
				
				PortalMail portalMail = new PortalMail();
				String msgBody;
				Inbox inbox = new Inbox();
				EmpSerReqMap esrRow = esrMapDao.findByPrimaryKey(travelDao.findByPrimaryKey(travelReq.getTlReqId()).getEsrqmId());
				
				//before sending mail & inserting into Docking Station...delete the existing inbox entries...common for ACCEPT & REJECT
				String approverIdsToBeRemovedFromInbox = presentLevelApproverIds.toString().replace('[', ' ').replace(']', ' ').trim();
				
				if(approverIdsToBeRemovedFromInbox!=null && approverIdsToBeRemovedFromInbox.length()>0){			
					//Query changed to delete inbox entries for all approvers and escalation actors
					String deleteExistingTravelEntriesFromInboxQuery = "DELETE FROM INBOX WHERE ESR_MAP_ID="+esrRow.getId()+" AND ASSIGNED_TO =RECEIVER_ID";//IN("+approverIdsToBeRemovedFromInbox+")";
					inboxDao.executeUpdate(deleteExistingTravelEntriesFromInboxQuery);
				}
				
				if (travelReq.getActionType() == Status.getStatusId(Status.ACCEPTED)) {
					
					//is mail subject set inside this method()?....check this
					msgBody = sendInfoToApproversInPresentLevel(presentLevelApproverIds,login.getUserId(),travelReq,travelForm.getTravelReqId(),Status.getStatusId(Status.ACCEPTED));
					
					if(nextSetOfApprovers.length == 1){
						//sent mail to present level approvers
						
						//send mail to handlers						
						Integer[] handlerIds = processEvaluator.handlers(processChainDao.findByPrimaryKey(esrRow.getProcessChainId()).getHandler(), esrRow.getRequestorId());
						String ids = Arrays.asList(handlerIds).toString().replace("[", " ").replace("]"," ").trim();
						ProfileInfo requesterProfileInfo = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(esrRow.getRequestorId()).getProfileId());
						portalMail.setAllReceipientMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN ("+ids+") )"));
						portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_ASSIGNED);	
						portalMail.setMailSubject("Diksha Lynx: New Travel request ["+esrRow.getReqId()+"] by ["+requesterProfileInfo.getFirstName() + " "	+ requesterProfileInfo.getLastName() +"]");
						//if required set more fields here before calling the sendEmail()		
						
						msgBody = sendEmail(travelReq.getId(), true, portalMail);//mail + INBOX						
						
						for(Iterator<Integer>idIterator=((List<Integer>)Arrays.asList(handlerIds)).iterator();idIterator.hasNext();){
							inbox = new Inbox();	
							int handlerId = (Integer)idIterator.next();
							inbox.setReceiverId(handlerId);
							inbox.setEsrMapId(travelDao.findByPrimaryKey(travelForm.getTravelReqId()).getEsrqmId());
							inbox.setSubject("New Travel Request Raised");
							inbox.setAssignedTo(handlerId);//this is a notification and must not be visible in Docking Station...or is it!!
							inbox.setCreationDatetime(new Date());							
							inbox.setStatus("Request Raised");
							inbox.setCategory("TRAVEL");
							inbox.setIsRead(0);
							inbox.setIsDeleted(0);
							inbox.setMessageBody(msgBody);
							inboxDao.insert(inbox);								
						}					
						
						//update requester status to APPROVED						
						travelDao.executeUpdate("UPDATE TRAVEL SET STATUS=7, COMMENTS_HISTORY=NULL WHERE ID="+travelForm.getTravelReqId());
						
						/*//delete existing entries for present level approvers
						travelReqDao.executeUpdate("DELETE FROM TRAVEL_REQ WHERE TL_REQ_ID="+travelReq.getTlReqId());*/
						
						//deleting travelReq records is not required...instead update status in TRAVEL_REQ to APPROVED.....so that Docking Station will have no conflict
						travelReqDao.executeUpdate("UPDATE TRAVEL_REQ SET STATUS=7, ACTION_TYPE=7 , DATE_OF_COMPLETION = '"+new java.sql.Timestamp( new Date().getTime()) +"' WHERE TL_REQ_ID="+travelReq.getTlReqId());					
						
						//insert into TRAVEL_REQ for each handler
						TravelReq handlerTravelReq;
						for(Iterator<Integer>idIterator=((List<Integer>)Arrays.asList(handlerIds)).iterator();idIterator.hasNext();){
							int handlerId = (Integer)idIterator.next();
							handlerTravelReq = new TravelReq();
							handlerTravelReq.setTlReqId(travelForm.getTravelReqId());
							handlerTravelReq.setStatus(Status.getStatusId(Status.REQUESTRAISED));
							handlerTravelReq.setAssignedTo(handlerId);
							handlerTravelReq.setAppLevelNull(true);
							handlerTravelReq.setActionType(0);
							handlerTravelReq.setMessageBody(msgBody);
							handlerTravelReq.setDateOfCompletion(new Date());
							travelReqDao.insert(handlerTravelReq);
						}
						
						 
					}else if(nextSetOfApprovers.length > 1){
						//sent mail to present level approvers	
						
						//update requester status to PENDING APPROVAL
						String requesterStatusUpdate = "UPDATE TRAVEL SET STATUS=30, COMMENTS_HISTORY=NULL WHERE ID="+travelForm.getTravelReqId();
						travelDao.executeUpdate(requesterStatusUpdate);
						
						//update nextSetOfApprovers in Traveller_Type
						updateNextSetOfApproversInTravellerType(travellerType, travellerTypeDao);
						nextSetOfApprovers = travellerType.getNextSetOfApprovers().split("~=~");
						presentLevelApproverIds = Arrays.asList(nextSetOfApprovers[0].split(","));
						
						//this is for the next level approvers..present level is updated
						msgBody = sendInfoToApproversInPresentLevel(presentLevelApproverIds,login.getUserId(),travelReq,travelForm.getTravelReqId(),Status.getStatusId(Status.REQUESTRAISED));//status must be new..vijay check this
						
						travelReqDao = TravelReqDaoFactory.create();
						TravelReq existingEntryForApprover = travelReqDao.findWhereTlReqIdEquals(travelReq.getTlReqId())[0];//bkp				
						
						/*//delete existing entries for present level approvers
						travelReqDao.executeUpdate("DELETE FROM TRAVEL_REQ WHERE TL_REQ_ID="+travelReq.getTlReqId());*/
						
						//deleting travelReq records is not required...instead update status in TRAVEL_REQ to ACCEPTED.....so that Docking Station will have no conflict
						travelReqDao.executeUpdate("UPDATE TRAVEL_REQ SET STATUS=7, ACTION_TYPE=7 , DATE_OF_COMPLETION = '"+new java.sql.Timestamp( new Date().getTime()) +"' WHERE TL_REQ_ID="+travelReq.getTlReqId());	
																
						//inserting new rows for next level approver ids
						TravelReq appTravelReq;
						for(Iterator<String>approverIdIterator = presentLevelApproverIds.iterator();approverIdIterator.hasNext();){
							appTravelReq = new TravelReq();
							appTravelReq.setAssignedTo(Integer.parseInt(approverIdIterator.next()));
							appTravelReq.setTlReqId(travelForm.getTravelReqId());
							appTravelReq.setStatus(Status.getStatusId(Status.REQUESTRAISED));
							appTravelReq.setAppLevel(existingEntryForApprover.getAppLevel()+1);
							appTravelReq.setActionType(0);
							appTravelReq.setMessageBody(msgBody);
							appTravelReq.setDateOfCompletion(new Date());
							travelReqDao.insert(appTravelReq);							
						}			
												
					}					
					
				}else if(travelReq.getActionType() == Status.getStatusId(Status.REJECTED)){
					//send mail to present level approvers
					sendInfoToApproversInPresentLevel(presentLevelApproverIds,login.getUserId(),travelReq,travelForm.getTravelReqId(),Status.getStatusId(Status.REJECTED));
					
					Travel travelDetails = travelDao.findByPrimaryKey(travelForm.getTravelReqId());
					
					//send mail to requester
					portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_REJECTED_REQUESTOR);
					portalMail.setEmpFname(getProfileObjForUser(travelDetails.getTrlUserId()).getFirstName());
					portalMail.setComments(travelDao.getCommentsHistory(travelDetails.getEsrqmId()));
					portalMail.setRecipientMailId(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(esrRow.getRequestorId()).getProfileId()).getOfficalEmailId());
					portalMail.setMailSubject("Diksha Lynx: Travel Request Rejected");
					
					
					/*
					 * Including Travel-Details in the mail template (visible to all)
					 * mode of travel
					 * travelling from
					 * travelling to
					 * preferred date of travel
					 * preferred time of travel
					 * one way or round trip
					 * is accommodation required (if yes....??)
					 * is cab required (??onward_inward)
					 */
					
					portalMail.setTravelMode(travelDetails.getModeOfTl());
					portalMail.setTravellingFrom(travelDetails.getTlFrom());
					portalMail.setTravellingTo(travelDetails.getTlTo());
					portalMail.setTravelPrefDate(""+travelDetails.getPrfDateToTl());
					portalMail.setTravelPrefTime(""+travelDetails.getPrfTimeToTl());//formatting required
					portalMail.setTravelOneWayRoundTrip((travelDetails.getIsRoundTrip()==1)?"Round Trip":"One Way");//formatting required
					if(travelDetails.getReturnDate()==null){
						portalMail.setTravelRetDate("N.A");
					}else{
						portalMail.setTravelRetDate(""+travelDetails.getReturnDate());
					}
					if(travelDetails.getReturnTime()==null){
						portalMail.setTravelRetTime("N.A");
					}else{
						portalMail.setTravelRetTime(""+travelDetails.getReturnTime());
					}
					portalMail.setTravelAccommodationReq((travelDetails.getAccomodationReq()==1)?"Yes":"No");//formatting required
					portalMail.setTravelAccommodationType((travelDetails.getTypeOfAccomodation()==null)?"N.A":travelDetails.getTypeOfAccomodation());
					portalMail.setTravelCabReq((travelDetails.getCabReq()==1)?"Yes":"No");
					if(portalMail.getTravelCabReq().equalsIgnoreCase("NO")){
						portalMail.setTravelCabOnwardInward("N.A");
					}else{
						portalMail.setTravelCabOnwardInward((travelDetails.getOnwardInward()==1)?"Onward":"Inward");	
					}
					
					
					String travellersFamily = travelDetails.getTravellerSpouseName();
					String spouseName = "N.A";
					String children = "N.A";
					
					if(travellersFamily!=null && travellersFamily.length()>0){
						String[] spouseAndChildren = travellersFamily.split("~=~");
						if(spouseAndChildren[0]!=null && spouseAndChildren[0].length()>0){
							spouseName = spouseAndChildren[0];				
						}
						if(spouseAndChildren[1]!=null && spouseAndChildren[1].length()>0){
							children = spouseAndChildren[1];
						}			
					}
					
					if(spouseName.equalsIgnoreCase("N.A")){
						portalMail.setTravellerWithFamily("No");
					}else{
						portalMail.setTravellerWithFamily("Yes");
					}
					
					String travellerComments=travelDetails.getTravellerComments();
					travellerComments=travellerComments!=null?travellerComments:"No remarks.";
					portalMail.setTravellerRemarks(travellerComments);
					
					portalMail.setTravellerSpouseName(spouseName+"<br/>Children : "+children+"<br/>");
					String handlerComments=travelForm.getComment();
					handlerComments=handlerComments!=null?handlerComments:"No comments.";
					portalMail.setComment(handlerComments);
					
					MailGenerator mailGenerator = new MailGenerator();
					msgBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
					portalMail.setMailBody(msgBody);
					mailGenerator.invoker(portalMail);					
					
					//update status wherever required
					travelDao.executeUpdate("UPDATE TRAVEL SET STATUS=8 WHERE ID="+travelForm.getTravelReqId());					
					
					travelReqDao.executeUpdate("UPDATE TRAVEL_REQ SET STATUS=8, ACTION_TYPE=8 , DATE_OF_COMPLETION = '"+new java.sql.Timestamp( new Date().getTime()) +"' WHERE TL_REQ_ID="+travelReq.getTlReqId());
					
					
				}else{
					//ON-HOLD not required in TRAVEL
					
				}
			
				
					/*	EmpSerReqMapDao eReqDao = EmpSerReqMapDaoFactory.create();
						ProcessChainDao pChainDao = ProcessChainDaoFactory.create();

						travelReq = travelReqDao.findByPrimaryKey(travelForm.getTravelReqId());
						travelReqPk.setId(travelReq.getId());
						travelReq.setActionType(travelForm.getActionType());
						travelReq.setComment(travelForm.getComment());

						if (travelReq.getActionType() == Status.getStatusId(Status.ACCEPTED)) {
							travelReq.setStatus(Status.getStatusId(Status.ACCEPTED));
							travelReq.setDateOfCompletion(new Date());
							travelReqDao.update(travelReqPk, travelReq);
							String SQL = "ID = (SELECT ESRQM_ID FROM TRAVEL WHERE ID = ?)";
							Object[] sqlParams = { travelReq.getTlReqId() };
							eReqDao.setMaxRows(1);
							EmpSerReqMap[] eReqMap = eReqDao.findByDynamicWhere(SQL,sqlParams);

							if (eReqMap.length > 0) {
								pChain = pChainDao.findByPrimaryKey(eReqMap[0].getProcessChainId());
								getReqAssToAppOrHandler(travelReq, pChain, eReqMap,request);						
							}
							
							//sendNotification(travelReq, pChain, eReqMap, request,"Travel Request[", "] Approved",MailSettings.TRAVEL_REQUEST_APPROVED_HRD,Status.ACCEPTED);

							// send to Dockingstation
						//	String portalMailbody = sendEmail("you have approved Travel Request["+ eReqMap[0].getReqId() + "]", travelReq.getAssignedTo(),MailSettings.TRAVEL_REQUEST_APPROVED_APPROVER,travelReq.getId(), false, null);
							
							
							// update msg body for travelRequest
							travelReq.setMessageBody(portalMailbody);
							travelReqDao.update(travelReqPk, travelReq);
							Inbox inbox = sendDockingStationForTravel(travelReqPk.getId(), "Travel Request[", "] Approved",eReqMap[0], Status.ACCEPTED);
							sendNotificationforNotifiers(inbox, travelReq);
							
						} else if (travelReq.getActionType() == Status.getStatusId(Status.REJECTED)) {

							travelReq.setStatus(Status.getStatusId(Status.REJECTED));
							travelReq.setDateOfCompletion(new Date());
							travelReq.setComment(travelForm.getComment());
							travelReqDao.update(travelReqPk, travelReq);
							travel = travelDao.findByPrimaryKey(travelReq.getTlReqId());
							travel.setStatus(Status.getStatusId(Status.REJECTED));
							travelPk.setId(travel.getId());
							travelDao.update(travelPk, travel);

							Users users = usersDao.findByPrimaryKey(travel.getTrlUserId());
							ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(users.getProfileId());
							eReqDao.setMaxRows(1);
							EmpSerReqMap[] eReqMap = eReqDao.findByDynamicWhere(SQLProfile,new Object[] { travelReq.getTlReqId() });
							if (eReqMap.length > 0) {
								pChain = pChainDao.findByPrimaryKey(eReqMap[0].getProcessChainId());
							}

							userId.add(travel.getRaisedBy());
							userId.add(travel.getTrlUserId());

							for (int user : userId) {
								sendEmail(" Travel Request – Rejected ["+ eReqMap[0].getReqId() + "]", user,MailSettings.TRAVEL_REQUEST_REJECTED, travelReq.getId(), true, null);

							}
							
							
							// send mail to hrSpoc
							//sendEmail(" Travel Request – Rejected ["+ eReqMap[0].getReqId() + "]", profileInfo.getHrSpoc(),MailSettings.TRAVEL_REQUEST_REJECTED_HRD, travelReq.getId(), true, null);

							//sendNotification(travelReq, pChain, eReqMap, request," Travel Request – Rejected [", "]",MailSettings.TRAVEL_REQUEST_REJECTED_HRD,Status.REJECTED);

							// send to Dockingstation
							//String portalMailbody = sendEmail("you have rejected Travel Request["+ eReqMap[0].getReqId() + "]", travelReq.getAssignedTo(),MailSettings.TRAVEL_REQUEST_REJECTED_APPROVER,travelReq.getId(), false, null);

							travelReq.setMessageBody(portalMailbody);
							travelReqDao.update(travelReqPk, travelReq);
							Inbox inbox = sendDockingStationForTravel(travelReqPk.getId(), "Travel Request[", "] rejected",eReqMap[0], Status.REJECTED);
							//sendNotificationforNotifiers(inbox, travelReq);

							// send mail to Finance Head
							if (financeHeadUsers.length > 0) {
								for (Users financeHead : financeHeadUsers) {
									sendEmail(" Travel Request – Rejected ["+ eReqMap[0].getReqId() + "]", financeHead.getId(),MailSettings.TRAVEL_REQUEST_REJECTED_HRD,travelReq.getId(), true, null);
								}

							}

						} else if (travelReq.getActionType() == Status.getStatusId(Status.ON_HOLD)) {
							travelReq.setStatus(Status.getStatusId(Status.ON_HOLD));
							travelReq.setDateOfCompletion(new Date());
							travelReq.setComment(travelForm.getComment());
							travelReqDao.update(travelReqPk, travelReq);
							travel = travelDao.findByPrimaryKey(travelReq.getTlReqId());
							travel.setStatus(Status.getStatusId(Status.ON_HOLD));
							travelPk.setId(travel.getId());
							travelDao.update(travelPk, travel);

							eReqDao.setMaxRows(1);
							EmpSerReqMap[] eReqMap = eReqDao.findByDynamicWhere(SQLProfile,new Object[] { travelReq.getTlReqId() });
							
							if (eReqMap.length > 0) {
								pChain = pChainDao.findByPrimaryKey(eReqMap[0].getProcessChainId());
							}

							sendNotification(travelReq, pChain, eReqMap, request," Travel Request – is on HOLD [", "]",MailSettings.TRAVEL_REQUEST_ONHOLD_HRD,Status.ON_HOLD);// here the inbox should get
							// populated

							// send to Dockingstation
							String portalMailbody = sendEmail("you have put the Travel Request["+ eReqMap[0].getReqId() + "] on-hold",travelReq.getAssignedTo(),MailSettings.TRAVEL_REQUEST_ONHOLD_APPROVER,travelReq.getId(), false, null);

							travelReq.setMessageBody(portalMailbody);
							travelReqDao.update(travelReqPk, travelReq);
							Inbox inbox = sendDockingStationForTravel(travelReqPk.getId(), "Travel Request[", "] is on-hold",eReqMap[0], Status.ON_HOLD);
							sendNotificationforNotifiers(inbox, travelReq);
						}

						break;
					*/
			}
			break;
			case CANCEL:
			{
				
				/*
				 * if the current-status is request-raised
				 * the status must change to REVOKED
				 */
				boolean insertIntoInboxFlag = false;
				Travel travelDetails = travelDao.findByPrimaryKey(travelForm.getTravelId());
				boolean isNotifiersRequired = true ;
				
				final int statusId;
				if(travelForm.getPreviousStatus()==Status.getStatusId(Status.REQUESTRAISED) || Status.getStatus(travelDetails.getStatus()).equalsIgnoreCase(Status.PENDINGAPPROVAL)){
					statusId = Status.getStatusId(Status.REVOKED);
						ModelUtiility.getInstance().deleteInboxEntries(travelDetails.getEsrqmId());
				}else{
					statusId = Status.getStatusId(Status.CANCELREQUEST);
					insertIntoInboxFlag = true;
				}
				
				/*
				 * flow -->as per the new requirements
				 * use MailSettings.TRAVEL_REQUEST_CANCEL_HANDLER
				 * parse + sendMail + get the mailTemplate data
				 * update TRAVEL set status=request_raised
				 * update TRAVEL_REQ set status=request_raised action_type=0 date_of_completion=new Date() msg_body=new template data
				 * insert into INBOX fresh data  
				 */
				
							
				setProcChain(request);
				String validateHandlers = processEvaluator.validateProcessChain(procChain, loginUSer.getUserId(),false, true);
				try{
					Integer.parseInt(validateHandlers);
				}catch(NumberFormatException nfex){
					logger.error("SERVICE REQUEST PRE-PREPROCESS FAILED FOR REQUESTER_ID="+login.getUserId()+" SERVICE_REQUEST IS TRAVEL ERR_CODE="+validateHandlers);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest",validateHandlers));
					result.setForwardName("success");
					return result;
				}
								
				Travel travelRow = travelDao.findByPrimaryKey(travelForm.getTravelId());
				TravelReq[] travelReqRows = travelReqDao.findWhereTlReqIdEquals(travelForm.getTravelId());
				EmpSerReqMap esrRow = eMapDao.findByPrimaryKey(travelRow.getEsrqmId());
							
				Integer[] handlerIds = processEvaluator.handlers(procChain.getHandler(),travelRow.getRaisedBy());
				Set<String>mailIds = new HashSet<String>();
				PortalMail portalMail = new PortalMail();
				
				//if the request is in any approver level...they must receive this info instead of handlers...override
				if(Status.getStatus(travelDetails.getStatus()).equalsIgnoreCase(Status.PENDINGAPPROVAL)){
					TravelReq[] tempTravelReq = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND APP_LEVEL IS NOT NULL ORDER BY ID DESC", new Object[]{travelDetails.getId()});
					if(tempTravelReq!=null && tempTravelReq.length>0){
						TravelReq[] approverTravelReq = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND APP_LEVEL=? ORDER BY ID DESC", new Object[]{travelDetails.getId(),tempTravelReq[0].getAppLevel()});
						int index = 0 ;
						Integer[] approverIds = new Integer[approverTravelReq.length];
						for(TravelReq eachTravelReq:approverTravelReq){
							approverIds[index++] = eachTravelReq.getAssignedTo();							
						}		
						System.arraycopy(approverIds, 0, handlerIds, 0, approverIds.length);
						isNotifiersRequired = false;
					}
				}
				
				
				for(Integer eachHandlerId : handlerIds){
					mailIds.add(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(eachHandlerId).getProfileId()).getOfficalEmailId());					
				}
				
				mailIds.remove(null);
			
				portalMail.setAllReceipientMailId((String[]) Arrays.copyOf(mailIds.toArray(), mailIds.toArray().length,String[].class));
				
				mailIds.clear();//remove all entries
				
				if(isNotifiersRequired){
					/*
					 * commented...because...
					 * scenario : HR_1 raises a travel req for emp_1
					 * consider....process_chain configuration : approvers=RM 			handlers=[list of handlers] 		notifiers=HR_SPOC
					 * for some reason HR_1 cancels the travel req....
					 * now...Mail.CC will include (HR_1)'s HR_SPOC (ends up being no_spoc!!) instead of (emp_1)'s HR_SPOC..this was because of
					 * Integer[] notifierIds = processEvaluator.notifiers(procChain.getNotification(),travelRow.getRaisedBy());
					 * 
					 * now directly picking up the HR_SPOC of the person who is travelling.
					 * 
					 * Integer[] notifierIds = processEvaluator.notifiers(procChain.getNotification(),travelRow.getRaisedBy());
					for(Integer eachNotifierId : notifierIds){
						mailIds.add(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(eachNotifierId).getProfileId()).getOfficalEmailId());
					}
					
					mailIds.remove(null);
					portalMail.setAllReceipientcCMailId((String[]) Arrays.copyOf(mailIds.toArray(), mailIds.toArray().length,String[].class));	
						
					*/
					
					ProfileInfo travellerProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(travelRow.getTrlUserId()).getProfileId());
					ProfileInfo travellerSpocProfile = profileInfoDao.findByPrimaryKey(travellerProfile.getHrSpoc());
					mailIds.add(travellerSpocProfile.getOfficalEmailId());
					portalMail.setAllReceipientcCMailId((String[]) Arrays.copyOf(mailIds.toArray(), mailIds.toArray().length,String[].class));		
				}				
				
				ProfileInfo cancellerProfileInfo = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(travelRow.getRaisedBy()).getProfileId());
				
				portalMail.setMailSubject("Diksha Lynx: Travel request ["+esrRow.getReqId()+"] revoked by ["+cancellerProfileInfo.getFirstName() +cancellerProfileInfo.getLastName()+"]");
				portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_CANCEL_HANDLER);		
							
				String msgBody = sendEmail(travelReqRows[0].getId(), true, portalMail);
				portalMail.setMessageBody(msgBody);
				
				
				//mail is sent successfully..proceed updating the tables
				travelDao.executeUpdate("UPDATE TRAVEL SET STATUS="+statusId+" , CREATE_DATE='"+new java.sql.Timestamp(new Date().getTime())+"' WHERE ID="+travelForm.getTravelId());
				travelReqDao.executeUpdate("UPDATE TRAVEL_REQ SET STATUS="+statusId+", ACTION_TYPE=0 , DATE_OF_COMPLETION='"+ new java.sql.Timestamp(new Date().getTime())+"' , MESSAGE_BODY='"+msgBody+"' WHERE TL_REQ_ID="+travelForm.getTravelId());
				InboxDao inboxDao = InboxDaoFactory.create();
				
				
				if(insertIntoInboxFlag){	
					//insert into INBOX for all handlers
					for(Integer eachHandler : handlerIds){
						Inbox inboxRow = new Inbox();
						inboxRow.setReceiverId(eachHandler);
						inboxRow.setEsrMapId(travelRow.getEsrqmId());
						inboxRow.setSubject(portalMail.getMailSubject());
						inboxRow.setAssignedTo(eachHandler);
						inboxRow.setRaisedBy(travelRow.getRaisedBy());
						inboxRow.setCreationDatetime(new Date());
						inboxRow.setStatus(Status.getStatus(statusId));
						inboxRow.setCategory("TRAVEL");
						inboxRow.setIsRead(0);
						inboxRow.setIsDeleted(0);
						inboxRow.setMessageBody(msgBody);
						inboxDao.insert(inboxRow);					
					}
				}			
												
			}

				
				
				/*EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
				TravelReqPk tReqCancelPk = new TravelReqPk();
				Travel travelRow = travelDao.findByPrimaryKey(travelForm.getTravelId());
				EmpSerReqMap empSerReqMap = esrMapDao.findByPrimaryKey(travelRow.getEsrqmId());
				TravelReq travelReqinfo = new TravelReq();
				TravelReqPk trvlReqPk = new TravelReqPk();

				try {
					TravelReq travelReqRowsFetched[] = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND ( STATUS=? OR STATUS=? OR STATUS=? ) ORDER BY ID DESC",
									new Object[] {travelForm.getTravelId(),Status.getStatusId(Status.WORKINPROGRESS),
									Status.getStatusId(Status.REQUESTRAISED),
									Status.getStatusId(Status.PROCESSED) });
					TravelReq travelReqBkp[] = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND ( STATUS=? OR STATUS=? OR STATUS=? ) ORDER BY ID DESC",
									new Object[] {travelForm.getTravelId(),Status.getStatusId(Status.WORKINPROGRESS),
									Status.getStatusId(Status.REQUESTRAISED),
									Status.getStatusId(Status.PROCESSED) });

					
					
					
					
					if (travelReqRowsFetched != null && travelReqRowsFetched.length > 0) {
						for (TravelReq eachTravelReqRow : travelReqRowsFetched) {
							travelReqinfo = eachTravelReqRow;
							eachTravelReqRow.setStatus(Status.getStatusId(Status.REVOKED));
							eachTravelReqRow.setActionType(0);
							eachTravelReqRow.setMessageBody(null);
							tReqCancelPk.setId(eachTravelReqRow.getId());
							try {
								travelReqDao.update(tReqCancelPk,eachTravelReqRow);
							} catch (Exception e) {
								logger.error("failed to update travel request status to revoked ,while requesting for cancel",e);
								for (TravelReq travelReqBabkup : travelReqBkp) {
									travelReqDao.update(new TravelReqPk(travelReqBabkup.getId()),travelReqBabkup);
								}
								throw new Exception();
							}

							*//**
							 * populate Inbox notification for the handler who
							 * has been assigned the travel request
							 **//*
							ProfileInfo profileInfoForUser = getProfileObjForUser(eachTravelReqRow.getAssignedTo());
							ProfileInfo requesterProfileInfo = getProfileObjForUser(travelRow.getRaisedBy());

							PortalMail portalMail = new PortalMail();
							portalMail.setMessageBody(null);
							portalMail.setMailSubject(requesterProfileInfo.getFirstName()+ " "+ requesterProfileInfo.getLastName()+ " has Revoked Travel Request.");
							portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_CANCEL_HANDLER);
							portalMail.setEmpFname(profileInfoForUser.getFirstName());

							String msg = sendEmail(eachTravelReqRow.getId(), false, portalMail);

							eachTravelReqRow.setMessageBody(msg);
							trvlReqPk.setId(eachTravelReqRow.getId());
							travelReqDao.update(trvlReqPk,eachTravelReqRow);
							// populate Inbox notification for the Approver
							sendDockingStationForTravel(trvlReqPk.getId(),requesterProfileInfo.getFirstName()+ " has Revoked Travel Request.",empSerReqMap, Status.REVOKED);
						}
					}

					try {
						// send mail to requester regarding cancel request submitted

						ProfileInfo profileInfoForRequesterUsers = getProfileObjForUser(travelRow.getRaisedBy());
						PortalMail portalMailInfoObj = new PortalMail();
						
						portalMailInfoObj.setMessageBody(null);
						portalMailInfoObj.setMailSubject("You have Revoked the Travel Request");
						portalMailInfoObj.setTemplateName(MailSettings.TRAVEL_REQUEST_CANCEL_USER);
						portalMailInfoObj.setRecipientMailId(profileInfoForRequesterUsers.getOfficalEmailId());
						portalMailInfoObj.setEmpFname(profileInfoForRequesterUsers.getFirstName());
						sendEmail(travelReqinfo.getId(), true,portalMailInfoObj);
					} catch (TravelMailException e) {
						logger.error("failed to send mail to the requester", e);
						for (TravelReq travelReqBabkup : travelReqBkp) {
								travelReqDao.update(new TravelReqPk(travelReqBabkup.getId()), travelReqBabkup);
						}
						throw new Exception();
					}

				} catch (Exception e) {
					logger.error("failed to raise cancel request for travel",e);
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
				}

				*//**
				 * 4//****Cancel request by requester while Travel status is
				 * ACCEPTED //
				 **/

				break;
					
					
				
				
				
				
				
				case CANCELACTION:
					// update the status of Travel and TravelReq to Cancelled
					TravelReq traReqCancelDto = travelReqDao.findByPrimaryKey(travelForm.getTravelReqId());
					String status=Status.getStatus(traReqCancelDto.getStatus());					
					EmpSerReqMapDao eSRMapDao=EmpSerReqMapDaoFactory.create();
					ProcessChainDao pchainDao=ProcessChainDaoFactory.create();
					traReqCancelDto.setStatus(Status.getStatusId(Status.PROCESSED)); 
					travelReqPk.setId(traReqCancelDto.getId());
					travelReqDao.update(travelReqPk, traReqCancelDto);
					Travel travelCancelDto = travelDao.findByPrimaryKey(traReqCancelDto.getTlReqId());
					travelCancelDto.setStatus(Status.getStatusId(Status.REVOKED)); 
					travelPk.setId(travelCancelDto.getId());
					try{
						travelDao.update(travelPk, travelCancelDto);
					}
					catch (TravelDaoException te){
						logger.error(te);
						traReqCancelDto.setStatus(Status.getStatusId(status));
						travelReqDao.update(travelReqPk, traReqCancelDto);
						te.printStackTrace();
					}
					
					TravelReq trvlReq=travelReqDao.findByPrimaryKey(travelReqPk.getId());					
					EmpSerReqMap empMap=eSRMapDao.findByPrimaryKey(travelCancelDto.getEsrqmId());
					ProcessChain procesChain=pchainDao.findByPrimaryKey(empMap.getProcessChainId());
					
					// email notification for the other handlers
					ProfileInfo assignedToHandlersProfileInfo=getProfileObjForUser(trvlReq.getAssignedTo());
					sendNotificationforHandler(trvlReq, procesChain, empMap, request,
						" "+ assignedToHandlersProfileInfo.getFirstName()+" "+assignedToHandlersProfileInfo.getLastName()+" has revoked the travel request. ",
						MailSettings.TRAVEL_REQUEST_CANCELLED_HRD);
					
					//send to DockingStation					
					PortalMail portalMailForHandler=new PortalMail();
					portalMailForHandler.setMessageBody(null);
					portalMailForHandler.setMailSubject("Diksha Lynx: "+ assignedToHandlersProfileInfo.getFirstName()+" "+assignedToHandlersProfileInfo.getLastName()+" has "+ "revoked the travel request. ");
					portalMailForHandler.setTemplateName(MailSettings.TRAVEL_REQUEST_CANCELLED_HRD);
					portalMailForHandler.setComment(traReqCancelDto.getComment());
					
					ProfileInfo HandlerprofileInfo = getProfileObjForUser(login.getUserId());
					Users usersInfo[]=null;
					try{
						usersInfo=usersDao.findByDynamicWhere("PROFILE_ID=?" , new Object[]{HandlerprofileInfo.getId()});
					}catch (Exception e) {
						logger.error(" Exception while retriving loggedin handler's user info in update method's CANCELACTION",e);
					}
					
					portalMailForHandler.setHandlerName(HandlerprofileInfo.getFirstName()+" "+HandlerprofileInfo.getLastName());
					if(usersInfo!=null && usersInfo.length>0){
						portalMailForHandler.setHandlerEmpId(usersInfo[0].getEmpId());
					}else{						
						portalMailForHandler.setHandlerEmpId(0);
						logger.error(" check empId of userId : "+usersInfo[0].getId()+" as empId is null");
					}

					portalMailForHandler.setRecipientMailId(HandlerprofileInfo.getOfficalEmailId());
					
					//sending ccMail to hrSpoc and rm
					
					Set<String> ccMailidsOfHrSpocAndRmOfTraveller = new HashSet<String>();
					ProfileInfo travellerProfileInfo2=getProfileObjForUser(travelCancelDto.getTrlUserId());
					
					ProfileInfo travellerHrSpocProfileInfo=getProfileObjForUser(travellerProfileInfo2.getHrSpoc());
					ccMailidsOfHrSpocAndRmOfTraveller.add(travellerHrSpocProfileInfo.getOfficalEmailId());
					
					ProfileInfo travellerRmProfileInfo=getProfileObjForUser(travellerProfileInfo2.getReportingMgr());
					ccMailidsOfHrSpocAndRmOfTraveller.add(travellerRmProfileInfo.getOfficalEmailId());
					
					String[] ccMailIdsForHrSpocAndRmOfHandler = (String[ ]) Arrays.copyOf(ccMailidsOfHrSpocAndRmOfTraveller.toArray(), ccMailidsOfHrSpocAndRmOfTraveller.toArray().length,String[].class);
					
					portalMailForHandler.setAllReceipientcCMailId(ccMailIdsForHrSpocAndRmOfHandler);
					String portalMailbody = sendEmail( trvlReq.getId(),true,portalMailForHandler );					
					trvlReq.setMessageBody(portalMailbody);
					travelReqDao.update(travelReqPk, trvlReq);
					String fNameLnameSub = assignedToHandlersProfileInfo.getFirstName()+" "+assignedToHandlersProfileInfo.getLastName() + " has revoked the travel request. ";
					Inbox inbox=sendDockingStationForTravel(travelReqPk.getId(),fNameLnameSub ,empMap,Status.PROCESSED);
					sendNotificationforNotifiers(inbox,trvlReq);			
				
					//send mail to Finance Head
					portalMailForHandler.setAllReceipientcCMailId(null);
					portalMailForHandler.setRecipientMailId(null);	
				
					if(financeHeadUsers.length>0){
						Set<String> mailidOfFinanceHeadUsers = new HashSet<String>();
						
						for(Users financeHead:financeHeadUsers){
							ProfileInfo financeHeadProfileInfo=getProfileObjForUser(financeHead.getId());
							mailidOfFinanceHeadUsers.add(financeHeadProfileInfo.getOfficalEmailId());							 
						}
						
						String[] financeHeadMailIds = (String[ ]) Arrays.copyOf(mailidOfFinanceHeadUsers.toArray(), mailidOfFinanceHeadUsers.toArray().length,String[].class);
						portalMailForHandler.setAllReceipientMailId(financeHeadMailIds);					
						sendEmail(trvlReq.getId(),true,portalMailForHandler);						
					}		
					
					ProfileInfo raisedByProfileInfo=getProfileObjForUser(travelCancelDto.getRaisedBy());					
					portalMailForHandler.setMessageBody(null);
					portalMailForHandler.setMailSubject("Diksha Lynx: Your travel request has been successfully revoked.");
					portalMailForHandler.setTemplateName(MailSettings.NOTIFICATION_TRAVEL_REQUEST_CANCELLED_USER);
					portalMailForHandler.setComment(traReqCancelDto.getComment());
					portalMailForHandler.setAllReceipientcCMailId(null);
					portalMailForHandler.setRecipientMailId(raisedByProfileInfo.getOfficalEmailId());
					portalMailForHandler.setEmpFname(raisedByProfileInfo.getFirstName());
					
					String messageBodyForRevokedReq=sendEmail(trvlReq.getId(),false,portalMailForHandler);
					trvlReq.setMessageBody(messageBodyForRevokedReq);
					travelReqDao.update(new TravelReqPk(trvlReq.getId()), trvlReq);
					sendToInboxForTravelRequest(empMap.getId(),trvlReq.getId(),
						"Your travel request has been successfully revoked.", Status.REVOKED, travelCancelDto.getRaisedBy());
					
					break;
					
			
				case ASSIGN:
					
				String sql = "ID=(SELECT TL_REQ_ID FROM TRAVEL_REQ WHERE ID=?)";
				Travel travel2 = travelDao.findByPrimaryKey(travelForm.getTravelReqId());
				Travel travel2Backup[] = travelDao.findByDynamicWhere(sql, new Object[] { travelForm.getTravelReqId() });
				Travel travel3[] = {travel2};
				travelReq = travelReqDao.findWhereTlReqIdEquals(travelForm.getTravelReqId())[0];
				TravelReq travelReqBackup = travelReqDao.findByPrimaryKey(travelForm.getTravelReqId());
				TravelReq travelRequest = new TravelReq();
				TravelReqPk travelReqPk2 = new TravelReqPk();
				TravelPk travelPk2 = new TravelPk();
				travelPk2.setId(travelReq.getTlReqId());
				//travelPk2.setId(travelReq.getTlReqId()); variable name changed... accidentally??!
				travelReqPk2.setId(travelReq.getId());
				EmpSerReqMapDao eSRMapDao2 = EmpSerReqMapDaoFactory.create();
				TravelReq travelReqHandlerAssignedTo[] = null;
				TravelReq travelReqHandlerAssignedToBackup[] = null;

				if (travelForm.getAssignedTo() != 0) {

					try {
						travelReqHandlerAssignedToBackup = travelReqDao.findByDynamicWhere(	"TL_REQ_ID=?	AND ASSIGNED_TO=? AND APP_LEVEL IS NULL ORDER BY ID DESC",
										new Object[] { travelReq.getTlReqId(), travelForm.getAssignedTo() });
						travelReqHandlerAssignedTo = travelReqDao.findByDynamicWhere("TL_REQ_ID=?	AND ASSIGNED_TO=? AND APP_LEVEL IS NULL ORDER BY ID DESC",
										new Object[] { travelReq.getTlReqId(), travelForm.getAssignedTo() });
						if (travelReqHandlerAssignedTo[0] != null && travelReqHandlerAssignedTo.length > 0) {
							travelReqHandlerAssignedTo[0].setAppLevelNull(true);
							travelReqHandlerAssignedTo[0].setCurrency(travelForm.getCurrency());
							travelReqHandlerAssignedTo[0].setTotalCost(travelForm.getTotalCost());
							travelReqHandlerAssignedTo[0].setDateOfCompletion(new Date());
							travelReqHandlerAssignedTo[0].setStatus(travelForm.getPreviousStatus());
							travelRequest.setActionType(0);
							travelReqDao.update(new TravelReqPk(travelReqHandlerAssignedTo[0].getId()),travelReqHandlerAssignedTo[0]);
							/*if (travelForm.getPreviousStatusId() == Status.getStatusId(Status.CANCELINPROGRESS) ||
									travelForm.getPreviousStatusId() == Status.getStatusId(Status.CANCELREQUEST)) {
								travelReq.setStatus(Status.getStatusId(Status.CANCELINPROGRESS));
								travelReq.setActionType(Status.getStatusId(Status.CANCELINPROGRESS));
							} else {
								travelReq.setStatus(Status.getStatusId(Status.WORKINPROGRESS));
								travelReq.setActionType(Status.getStatusId(Status.WORKINPROGRESS));
							}*/
							
							
							travelReq.setStatus(travelForm.getPreviousStatus());
							travelReq.setActionType(travelForm.getPreviousStatus());
											
							travelReq.setComment(travelForm.getComment());
							travelReq.setDateOfCompletion(new Date());//**							
							
							travelForm.setEsrqmId(travelDao.findByPrimaryKey(travelForm.getTravelReqId()).getEsrqmId());
							
							travelForm.setComment(travelForm.getComment()+"~=~"+login.getUserId()+"~=~;");//**							
							travelDao.appendCommentsHistory(travelForm.getEsrqmId(), travelForm.getComment());//**
							
							travelReq.setTotalCost(travelForm.getTotalCost());
							travelReq.setCurrency(travelForm.getCurrency());
							
							/**
							 * update the status and action of the current
							 * handler
							 **/
							try {
								travelReqDao.update(travelReqPk2, travelReq);
							} catch (Exception e) {
								logger.error("failed to update the assigning handler's status to workinprogress",e);
								travelReqDao.update(new TravelReqPk(travelReqHandlerAssignedToBackup[0].getId()),travelReqHandlerAssignedToBackup[0]);
								throw new Exception();
							}

							if (travelForm.getItemCostInfo() != null) {
								try {
									saveItemCostInfo(travel2.getId(),travelForm);
								} catch (Exception e) {
									logger.error("failed to save itemcostinfo so failed to assign travel to other handler",e);
									travelReqDao.update(new TravelReqPk(travelReqHandlerAssignedToBackup[0].getId()),travelReqHandlerAssignedToBackup[0]);
									travelReqDao.update(new TravelReqPk(travelReqBackup.getId()),travelReqBackup);
									throw new Exception();
								}

							}

							/**
							 * update the other handlers status for the travel
							 * request
							 **/
							String sqlHandler = "TL_REQ_ID=? AND APP_LEVEL IS NULL AND ASSIGNED_TO NOT IN(?,?) AND (STATUS=? OR STATUS=?)";
							Object paramsHandler[] = new Object[] {
									travelReq.getTlReqId(),
									loginUSer.getUserId(),
									travelForm.getAssignedTo(),
									travelForm.getPreviousStatus(),
									travelForm.getPreviousStatus()};

							TravelReq travelReq2[] = travelReqDao.findByDynamicWhere(sqlHandler,paramsHandler);
							TravelReq travelReq2Backup[] = travelReqDao.findByDynamicWhere(sqlHandler,paramsHandler);
	
							String deleteTravelFromInbox = "DELETE FROM INBOX WHERE ESR_MAP_ID="+travelForm.getEsrqmId();//**
							InboxDaoFactory.create().executeUpdate(deleteTravelFromInbox);
												
							for (TravelReq trlReq : travelReq2) {
								TravelReqPk travelReqPk3 = new TravelReqPk();

								travelReqPk3.setId(trlReq.getId());
								trlReq.setCurrency(travelForm.getCurrency());
								trlReq.setTotalCost(travelForm.getTotalCost());
								trlReq.setStatus(travelForm.getPreviousStatus());
								try {
									travelReqDao.update(travelReqPk3, trlReq);
								} catch (Exception e) {
									logger.error("failed to update all the handler's  status to workinprogress who have assigned with the travel req id:(travelForm.getid) while assigning to a handler",e);
									travelReqDao.update(new TravelReqPk(travelReqHandlerAssignedToBackup[0].getId()),travelReqHandlerAssignedToBackup[0]);
									travelReqDao.update(new TravelReqPk(travelReqBackup.getId()),travelReqBackup);
									// need to handle for itemcostinfo
									throw new Exception();
								}

								// sending inbox
								Travel travelForOtherHandlers = travelDao.findByPrimaryKey(trlReq.getTlReqId());
								EmpSerReqMap empMapForOtherHandler = eSRMapDao2.findByPrimaryKey(travelForOtherHandlers.getEsrqmId());

								ProfileInfo profileInfoForOtherHandlers = getProfileObjForUser(trlReq.getAssignedTo());
								ProfileInfo assignedToUserProfileInfo = getProfileObjForUser(travelForm.getAssignedTo());

								// get message body
								PortalMail portalMailObj = new PortalMail();
								portalMailObj.setMessageBody(null);
								portalMailObj.setMailSubject("Diksha Lynx: Travel Request-["+ empMapForOtherHandler.getReqId()+ "] is assigned.");
								portalMailObj.setTemplateName(MailSettings.TRAVEL_REQUEST_ASSIGNED);
								portalMailObj.setEmpFname(profileInfoForOtherHandlers.getFirstName());

								String portalMail = sendEmail(trlReq.getId(),false, portalMailObj);

								ParseTemplate pTemplate = new ParseTemplate();
								portalMail = pTemplate.updateTagData("otherhandlernotification","This is to notify you that Travel Request-["+ empMapForOtherHandler.getReqId()
												+ "] is assigned to "+ assignedToUserProfileInfo.getFirstName()+ " "+ assignedToUserProfileInfo.getLastName() + ".",portalMail);

								// set MessageBody to TRAVEL_REQ
								trlReq.setMessageBody(portalMail);

								// update MessageBody in TRAVEL_REQ
								travelReqDao.update(new TravelReqPk(trlReq.getId()), trlReq);

								// populate inbox to the handler
								Inbox inboxAssign = sendDockingStationForTravel(trlReq.getId(), portalMailObj.getMailSubject(),empMapForOtherHandler,Status.getStatus(travelForm.getPreviousStatus()));

							}

							// now update the main travel status

							travel3[0].setStatus(travel2.getStatus());
							travel3[0].setDocumentsId(travelForm.getFileId());
							
							try {
								travelDao.update(travelPk2, travel3[0]);
							} catch (Exception e) {
								logger.error("failed to update main travel status to inprogress while assinging to a handler",e);
								travelReqDao.update(new TravelReqPk(travelReqHandlerAssignedToBackup[0].getId()),travelReqHandlerAssignedToBackup[0]);
								travelReqDao.update(new TravelReqPk(travelReqBackup.getId()),travelReqBackup);
								for (TravelReq trlReq : travelReq2Backup) {
									travelReqDao.update(new TravelReqPk(trlReq.getId()), trlReq);
								}
								// need to handle for itemcostinfo
								throw new Exception();
							}

							EmpSerReqMap empMap2 = eSRMapDao2.findByPrimaryKey(travel3[0].getEsrqmId());
							TravelReq travelReqforhandler = travelReqDao.findByPrimaryKey(new TravelReqPk(travelReqHandlerAssignedTo[0].getId()));
							ProfileInfo raisedbyUserProfileInfo = getProfileObjForUser(travel3[0].getRaisedBy());
							ProfileInfo assignedToUserProfileInfo = getProfileObjForUser(travelForm.getAssignedTo());

							// get message body
							PortalMail portalMailObj = new PortalMail();
							portalMailObj.setMessageBody(null);
							portalMailObj.setMailSubject("Diksha Lynx: You have been assigned Travel Request-["+ empMap2.getReqId() + "]");
							portalMailObj.setTemplateName(MailSettings.TRAVEL_REQUEST_ASSIGNED);
							portalMailObj.setEmpFname(assignedToUserProfileInfo.getFirstName());

							String portalMail = sendEmail(travelReqHandlerAssignedTo[0].getId(),false, portalMailObj);

							ParseTemplate pTemplate = new ParseTemplate();
							portalMail = pTemplate.updateTagData("assignMsg","You have been assigned Travel Request-["+ empMap2.getReqId() + "]",portalMail);

							// set MessageBody to TRAVEL_REQ
							travelReqforhandler.setMessageBody(portalMail);

							// update MessageBody in TRAVEL_REQ
							travelReqDao.update(new TravelReqPk(travelReqHandlerAssignedTo[0].getId()),travelReqforhandler);

							// populate inbox to the handler
							Inbox inboxAssign = sendDockingStationForTravel(travelReqHandlerAssignedTo[0].getId(),portalMailObj.getMailSubject(), empMap2,Status.getStatus(travel2.getStatus()));
							TravelReq travelReqAssign = travelReqDao.findByPrimaryKey(new TravelReqPk(travelReqHandlerAssignedTo[0].getId()));
							
							if(!(travelForm.getPreviousStatus()==Status.getStatusId(Status.CANCELREQUEST) || travelForm.getPreviousStatus()==Status.getStatusId(Status.CANCELINPROGRESS))){
								travel2.setStatus(Status.getStatusId(Status.REQUESTRAISED));
							}else{
								travel2.setStatus(Status.getStatusId(Status.CANCELREQUEST));
							}
							
							if(travelForm.getPreviousStatus()==Status.getStatusId(Status.WORKINPROGRESS)){
								travel2.setStatus(Status.getStatusId(Status.WORKINPROGRESS));
							}						
							
							
							String changeStatusTo = null;
							//if(travelForm.getPreviousStatus()==Status.getStatusId(Status.REQUESTRAISED) || travelForm.getPreviousStatus()==Status.getStatusId(Status.CANCELREQUEST)){
								ProfileInfo handlerProfileInfo = getProfileObjForUser(travelForm.getAssignedTo());
								changeStatusTo = "Docked By "+handlerProfileInfo.getFirstName()+" "+handlerProfileInfo.getLastName();						
							//}else{
								//CANCEL IN-PROGRESS   ||    WORK IN-PROGRESS
								//changeStatusTo = Status.getStatus(travelForm.getPreviousStatus());
							//}
							updateInboxStatus(travel2.getEsrqmId(),travelForm.getAssignedTo(),changeStatusTo);//**
							
							updateTravelAndTravelReqTables(travelForm,travel2);//**
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("failed to assign travel request to handler",e);
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
					}

				}

				else if(travelForm.getStatus()==Status.getStatusId(Status.PROCESSED)){
					boolean cancelFlag = false;
					try {
						// if the handler has completed the travel request:
						if (travelForm.getItemCostInfo() != null) {
							saveItemCostInfo(travel2.getId(), travelForm);
						}

						
						//fix for wrong row being fetched in TRAVEL_REQ
						travelReq = travelReqDao.findByDynamicWhere(" TL_REQ_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC ", new Object[]{travelReq.getTlReqId(), login.getUserId()})[0];
						travelReqPk2.setId(travelReq.getId());
						
						travelReq.setCurrency(travelForm.getCurrency());
						travelReq.setTotalCost(travelForm.getTotalCost());						
						
						if (travelForm.getPreviousStatus() == Status.getStatusId(Status.CANCELINPROGRESS) ||
								travelForm.getPreviousStatus() == Status.getStatusId(Status.CANCELREQUEST)) {
							travelReq.setStatus(Status.getStatusId(Status.REVOKED));
							travelReq.setActionType(Status.getStatusId(Status.REVOKED));
							cancelFlag = true;
						} else {
							travelReq.setStatus(Status.getStatusId(Status.PROCESSED));
							travelReq.setActionType(Status.getStatusId(Status.PROCESSED));
						}
						
						travelReq.setComment(travelForm.getComment());
						travelReq.setDateOfCompletion(new Date());
						
						
							/**
							 * update travel request and also update the status for
							 * other handlers who had
							 * beensendDockingStationForTravel
							 * (trvlReqPk.getId(),"Travel Request – Cancellation[",
							 * "]",empSerReqMap,Status.CANCEL); assigned the travel
							 * request
							 */

						/**
						 * update status to completed for the handler who has
						 * completed the travel request
						 */
						try {
							travelReqDao.update(travelReqPk2, travelReq);
							TravelReq tempTravelReq = travelReqDao.findByPrimaryKey(travelForm.getTravelReqId());
							if(tempTravelReq==null){
								//conflict again
								Travel tempTravelDetails = travelDao.findByPrimaryKey(travelForm.getTravelReqId());
								travelForm.setEsrqmId(tempTravelDetails.getEsrqmId());								
							}else{
								travelForm.setEsrqmId(travelDao.findByPrimaryKey(travelReqDao.findByPrimaryKey(travelForm.getTravelReqId()).getTlReqId()).getEsrqmId());//**
							}
							
							
							travelForm.setComment(travelForm.getComment()+"~=~"+login.getUserId()+"~=~;");//**							
							travelDao.appendCommentsHistory(travelForm.getEsrqmId(), travelForm.getComment());//**						
							
						} catch (Exception e) {
							logger.error("failed to update the status to processed",e);
							throw new Exception();
						}

						travelPk2.setId(travel2.getId());

						// update status to completed for other handlers

						TravelReq travelReqDtoStatusCompleted[] = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND APP_LEVEL IS NULL AND ID NOT IN(?)",
										new Object[] { travelReq.getTlReqId(),travelReq.getId() });

						TravelReq travelReqDtoStatusCompletedBackup[] = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND APP_LEVEL IS NULL AND ID NOT IN(?)",
										new Object[] { travelReq.getTlReqId(),travelReq.getId() });

						for (TravelReq trlReq : travelReqDtoStatusCompleted) {
							TravelReqPk travelRequestPk = new TravelReqPk();
							trlReq.setStatus(travelReq.getStatus());
							trlReq.setCurrency(travelForm.getCurrency());
							trlReq.setTotalCost(travelForm.getTotalCost());
							travelRequestPk.setId(trlReq.getId());
							try {
								travelReqDao.update(travelRequestPk, trlReq);
							} catch (Exception e) {
								logger.error("failed to update status to processed for other handlers",e);
								travelReqDao.update(new TravelReqPk(travelReqBackup.getId()),travelReqBackup);
								throw new Exception();
							}

						}

						// update the main travel status to complete
						travel2.setStatus(Status.getStatusId(Status.PROCESSED));
						travel2.setDocumentsId(travelForm.getFileId());
						try {
							travelDao.update(travelPk2, travel2);
						} catch (Exception e) {
							for (TravelReq trlReq : travelReqDtoStatusCompletedBackup) {
								travelReqDao.update(new TravelReqPk(trlReq.getId()), trlReq);
							}
							logger.error("failed to update the main travel status to processed",e);
							travelReqDao.update(new TravelReqPk(travelReqBackup.getId()), travelReqBackup);
							throw new Exception();
						}

						// send mail to requester and traveller attaching
						// itinerary

						Users users5 = usersDao.findByPrimaryKey(travel2.getTrlUserId());
						Set<String> mailIds = new HashSet<String>();
						//Set<String> ccMailid = new HashSet<String>();

						ProfileInfo userProfileInfo = getProfileObjForUser(travel2.getRaisedBy());
						mailIds.add(userProfileInfo.getOfficalEmailId());
						
						ProfileInfo personWhohandled = null;
						if(cancelFlag){
							personWhohandled = getProfileObjForUser(travelReqDao.findByDynamicSelect("SELECT * FROM TRAVEL_REQ WHERE TL_REQ_ID="+travel2.getId()+" AND ACTION_TYPE=28",null)[0].getAssignedTo());
						}else{
							personWhohandled = getProfileObjForUser(travelReqDao.findByDynamicSelect("SELECT * FROM TRAVEL_REQ WHERE TL_REQ_ID="+travel2.getId()+" AND ACTION_TYPE=27",null)[0].getAssignedTo());
						}						
												
						ProfileInfo travellerProfileInfoForUSer = getProfileObjForUser(travel2.getTrlUserId());
						mailIds.add(personWhohandled.getOfficalEmailId());

						mailIds.remove(null);
						
						String[] allReceipientMailId = (String[]) Arrays.copyOf(mailIds.toArray(),mailIds.toArray().length, String[].class);
						PortalMail portalMail = new PortalMail();
						portalMail.setMessageBody(null);
						portalMail.setMailSubject("Diksha Lynx: Travel Details");
						portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_COMPLETED_REQUESTER);
						portalMail.setAllReceipientMailId(allReceipientMailId);
						
						//Mail.CC
						
						mailIds.clear();
						setProcChain(request);
						
						//hrspoc rm from profile-info table will be added
						userProfileInfo = getProfileObjForUser(travel2.getTrlUserId());
						mailIds.add(getProfileObjForUser(userProfileInfo.getReportingMgr()).getOfficalEmailId());//R.M of the traveller
						mailIds.add(getProfileObjForUser(userProfileInfo.getHrSpoc()).getOfficalEmailId());//HRSPOC of the traveller
						
						mailIds.remove(null);
						
						/*Integer[] notifierIds = processEvaluator.notifiers(procChain.getNotification(),travel2[0].getRaisedBy());
						for(Integer eachNotifier : notifierIds){
							mailIds.add(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(eachNotifier).getProfileId()).getOfficalEmailId());
						}*/
												
						ProfileInfo travellersProfileInfo = profileInfoDao.findByPrimaryKey(users5.getProfileId());
										
						// finance head information in Mail.CC
						if (financeHeadUsers != null && financeHeadUsers.length > 0) {
							for (Users financeHead : financeHeadUsers) {
								ProfileInfo financeHeadUsersProfileInfo = getProfileObjForUser(financeHead.getId());
								mailIds.add(financeHeadUsersProfileInfo.getOfficalEmailId());
							}
						}
						
						//adding Sr.Manager RMG in Mail.CC
						if (rmgManagers != null && rmgManagers.length > 0) {
							for (Users manager : rmgManagers) {
								ProfileInfo managerProfileInfo = getProfileObjForUser(manager.getId());
								mailIds.add(managerProfileInfo.getOfficalEmailId());
							}
						}
														
						String[] allReceipientcCMailId = (String[]) Arrays.copyOf(mailIds.toArray(),mailIds.toArray().length,String[].class);
						portalMail.setAllReceipientcCMailId(allReceipientcCMailId);
						portalMail.setEmpFname(travellerProfileInfoForUSer.getFirstName()+" "+travellerProfileInfoForUSer.getLastName());

						try {
							String msgBody = sendEmail(travelReq.getId(), true,portalMail);
						} catch (TravelMailException e) {
							logger.error("failed to send mail", e);
							for (TravelReq trlReq : travelReqDtoStatusCompletedBackup) {
								travelReqDao.update(new TravelReqPk(trlReq.getId()), trlReq);
							}
							travelReqDao.update(new TravelReqPk(travelReqBackup.getId()), travelReqBackup);
							travelDao.update(new TravelPk(travel2Backup[0].getId()), travel2Backup[0]);
							throw new TravelMailException();
						}
						TravelReq travelReqHandlerRows[] = null;
						Inbox inboxForHandler = null;

						// for(Integer handler:handlerIds){
						//													 
						// if(handler.equals(travelReq.getAssignedTo())){
						// //insert a row into ASSIGNED_TO handler's inbox
						// ProfileInfo
						// assignedHandlerProfileInfo=getProfileObjForUser(travelReq.getAssignedTo());
						//								 
						//								 
						// String msgBody = sendEmail(" Travel Request[" +
						// eReqMap[0].getReqId() + "] completed",
						// travelReq.getAssignedTo(),
						// MailSettings.TRAVEL_REQUEST_COMPLETED_HANDLER,travelReq.getId(),false,null);
						// travelReq.setMessageBody(msgBody);
						// travelReqDao.update(new
						// TravelReqPk(travelReq.getId()), travelReq);
						// inboxForHandler=sendDockingStationForTravel(travelReq.getId()," You have completed the Travel Request [",
						// "]",empMap2,Status.PROCESSED);
						//								 
						// }else{
						// //inserting suitable rows into other handler's inbox
						// travelReqHandlerRows=
						// travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND ASSIGNED_TO=? AND APP_LEVEL IS NULL ORDER BY DATE_OF_COMPLETION DESC ",
						// new Object[]{travelReq.getTlReqId(),handler});
						// String msgBody = sendEmail(" Travel Request["+
						// eReqMap[0].getReqId() + "] completed", handler,
						// MailSettings.TRAVEL_REQUEST_COMPLETED,travelReqHandlerRows[0].getId(),false,null);
						// travelReqHandlerRows[0].setMessageBody(msgBody);
						// travelReqDao.update(new
						// TravelReqPk(travelReqHandlerRows[0].getId()),
						// travelReqHandlerRows[0]);
						// inboxForHandler=sendDockingStationForTravel(travelReqHandlerRows[0].getId()," This is a notification for Travel Request[",
						// "] completed",empMap2,Status.PROCESSED);
						// }
						//							 
						//							 
						// }
						EmpSerReqMap empMap2 = eSRMapDao2.findByPrimaryKey(travel3[0].getEsrqmId());

						// sending mail to handler
						ProfileInfo assignedHandlerProfileInfo = getProfileObjForUser(travelReq.getAssignedTo());
						PortalMail portalMailHandlerMailInfo = new PortalMail();
						portalMailHandlerMailInfo.setMessageBody(null);
						portalMailHandlerMailInfo.setMailSubject("Diksha Lynx: Travel Details");
						portalMailHandlerMailInfo.setTemplateName(MailSettings.NOTIFICATION_TRAVEL_REQUEST_COMPLETED_HANDLER);
						portalMailHandlerMailInfo.setEmpFname(assignedHandlerProfileInfo.getFirstName());
						String msgBody = sendEmail(travelReq.getId(), false,portalMailHandlerMailInfo);

						travelReq.setMessageBody(msgBody);
						travelReqDao.update(new TravelReqPk(travelReq.getId()),travelReq);						
						// sendNotificationforNotifiers(inboxForHandler,
						// travelReq);

						if(cancelFlag){
							 travel2.setStatus(Status.getStatusId(Status.REVOKED));
							 inboxForHandler = sendDockingStationForTravel(travelReq.getId(),"Travel Details ",empMap2,Status.REVOKED);
						}else{
							travel2.setStatus(Status.getStatusId(Status.PROCESSED));
							inboxForHandler = sendDockingStationForTravel(travelReq.getId(),"Travel Details ",empMap2, Status.PROCESSED);
						}
						updateTravelAndTravelReqTables(travelForm, travel2);
						
						String deleteTravelFromInbox = "DELETE FROM INBOX WHERE ESR_MAP_ID="+empMap2.getId();//**
						InboxDaoFactory.create().executeUpdate(deleteTravelFromInbox);
						
					} catch (TravelMailException trvlMailExp) {
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.servicerequest","ERR-006"));
						logger.error("failed to send notification",trvlMailExp);
						trvlMailExp.printStackTrace();
						throw new Exception();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("failed to complete travel request", e);
						result.getActionMessages().add(	ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
					}

				}
				else{
					
					if(travelForm.getPreviousStatus()==Status.getStatusId(Status.CANCELREQUEST)){
						travel2.setStatus(Status.getStatusId(Status.CANCELINPROGRESS));
					}else{
						travel2.setStatus(Status.getStatusId(Status.WORKINPROGRESS));
					}
					if (travelForm.getItemCostInfo() != null) {
						try {
							saveItemCostInfo(travel2.getId(),travelForm);
							
							//variable name conflict : fix
							TravelReq tempTravelReq = travelReqDao.findByPrimaryKey(travelForm.getTravelReqId());
							if(tempTravelReq==null){
								//conflict
								travelForm.setEsrqmId(travelDao.findByPrimaryKey(travelForm.getTravelReqId()).getEsrqmId());
							}else{
								travelForm.setEsrqmId(travelDao.findByPrimaryKey(travelReqDao.findByPrimaryKey(travelForm.getTravelReqId()).getTlReqId()).getEsrqmId());//**
							}							
							travelForm.setComment(travelForm.getComment()+"~=~"+login.getUserId()+"~=~;");//**							
							travelDao.appendCommentsHistory(travelForm.getEsrqmId(), travelForm.getComment());//**							
						} catch (Exception e) {
							logger.error("failed to save itemcostinfo so failed to assign travel to other handler",e);
							travelReqDao.update(new TravelReqPk(travelReqHandlerAssignedToBackup[0].getId()),travelReqHandlerAssignedToBackup[0]);
							travelReqDao.update(new TravelReqPk(travelReqBackup.getId()),travelReqBackup);
							throw new Exception();
						}

					}
					
					travelReq = travelReqDao.findByPrimaryKey(travelForm.getTravelReqId());
					if(travelReq==null){
						travelReq = travelReqDao.findByDynamicWhere("TL_REQ_ID=? AND ASSIGNED_TO=? AND APP_LEVEL IS NULL ORDER BY ID DESC", new Object[]{travelForm.getTravelReqId(),login.getUserId()})[0];
					}
					travelReq.setStatus(travelForm.getPreviousStatus());
					travelReq.setActionType(travelForm.getPreviousStatus());
					travelReq.setComment(travelForm.getComment());
					travelReq.setTotalCost(travelForm.getTotalCost());
					travelReq.setCurrency(travelForm.getCurrency());
					travelReqDao.update(new TravelReqPk(travelReq.getId()), travelReq);			
					
					updateTravelAndTravelReqTables(travelForm, travel2);
					
					int esrqmId = travelDao.findByPrimaryKey(travelReq.getTlReqId()).getEsrqmId();
					
					updateInboxStatus(esrqmId, login.getUserId(),Status.getStatus(travel2.getStatus()));
					
					/*// WORK IN-PROGRESS & CANCEL IN-PROGRESS
					String inProgressQuery1 = "UPDATE TRAVEL_REQ SET STATUS="+travelForm.getStatus()+" , ACTION_TYPE="+travelForm.getStatus()+" WHERE ID="+travelForm.getTravelReqId();
					travelReqDao.executeUpdate(inProgressQuery1);
					String inProgressQuery2 = "UPDATE TRAVEL SET STATUS="+travelForm.getStatus()+" WHERE ID="+travel2[0].getId();
					travelDao.executeUpdate(inProgressQuery2);
					String inProgressQuery3 = "UPDATE TRAVEL_REQ SET STATUS="+travelForm.getStatus()+" WHERE TL_REQ_ID="+travel2[0].getId();
					travelDao.executeUpdate(inProgressQuery3);*/
				}
					break;
					
				case APPROVERASSIGN:	
					//you are here because an approver has assigned a travel request to self/another approver
					
					/*
					 * when one approver assigns a travel request to another approver
					 * all the approvers in the same level would have already received the "new travel request" info through D.S
					 * get the person to whom it is assigned (Docked By This Person)
					 * send him a mail saying you have been assigned to approve/reject this particular travel request
					 * use this mail body to update INBOX set MSG_BODY=newMsgBody, ASSIGNED_TO=this where ESR_MAP_ID=this and RECEIVER_ID=this 
					 * update INBOX set STATUS='Docked By Assigned_To's F_Name' , ASSIGNED_TO=this where ESR_MAP_ID=this
					 * update TRAVEL.COMMENTS_HISTORY
					 * 
					 * tweak the handler's ASSIGNED mail template as per your needs
					 */
					
					if (travelForm.getAssignedTo() != 0) {

						try {
							ProfileInfo assigneeProfileInfo = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(travelForm.getAssignedTo()).getProfileId());
							InboxDao inboxDao = InboxDaoFactory.create();
							PortalMail portalMail = new PortalMail();
							int esrMapId = travelDao.findByPrimaryKey(travelForm.getTravelReqId()).getEsrqmId();
							
							portalMail.setMailSubject("Diksha Lynx: Travel Request Assigned");
							portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_ASSIGNED);//check this
							portalMail.setRecipientMailId(assigneeProfileInfo.getOfficalEmailId());
						    //set required variables in portalMail based no the new template n parse it..set this as portalMail.setMessageBody()....and then send mail
							
							//String msgBody = "this is a temp message body which will be replaced by a template";//check this
							
							//String updateInbox = "UPDATE INBOX SET STATUS='Docked By "+assigneeProfileInfo.getFirstName()+"',ASSIGNED_TO="+travelForm.getAssignedTo() 
							//+", MESSAGE_BODY='"+msgBody+"' WHERE ESR_MAP_ID="+esrMapId;
							
							String updateInbox = "UPDATE INBOX SET STATUS='Docked By "+assigneeProfileInfo.getFirstName()+"',ASSIGNED_TO="+travelForm.getAssignedTo() 
							+",IS_READ=0 WHERE ESR_MAP_ID="+esrMapId;
							
							inboxDao.executeUpdate(updateInbox);//for the assignee approver
							
							updateInbox = "UPDATE INBOX SET STATUS='Docked By "+assigneeProfileInfo.getFirstName()+"' ,ASSIGNED_TO="+travelForm.getAssignedTo()+", IS_READ=0 WHERE ESR_MAP_ID="+esrMapId;
							inboxDao.executeUpdate(updateInbox);//for remaining approvers
							
							travelForm.setEsrqmId(travelDao.findByPrimaryKey(travelForm.getTravelReqId()).getEsrqmId());
							travelForm.setComment(travelForm.getComment()+"~=~"+login.getUserId()+"~=~;");//**							
							travelDao.appendCommentsHistory(travelForm.getEsrqmId(), travelForm.getComment());//**							
							
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("failed to assign travel request to approver",e);
							result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
						}

					}else{
						//throw an error saying data was not received properly
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
					}
					
					break;
					
					
					
				case EDIT:
					
				try{
					travelForm.setIsBussinessType(0);
					
					Travel travelDto = travelDao.findByPrimaryKey(travelForm.getTravelId());
					Travel travelDtoToRollBack= travelDao.findByPrimaryKey(travelForm.getTravelId());
					if (travelDto.getStatus() == Status.getStatusId(Status.REQUESTSAVED)){
						getTravel(travelDto, travelForm);
						TravelPk trlPk = new TravelPk();
						trlPk.setId(travelDto.getId());
						travelDto.setTravellerComments(travelForm.getTravellerComments());
						travelDto.setTravellerSpouseName(travelForm.getTravellerSpouseName());
						travelDao.update(trlPk, travelDto);
						
						TravellerType traType[]=travellerTypeDao.findWhereTlIdEquals(trlPk.getId());
						TravellerType traTypeBackup[]=travellerTypeDao.findWhereTlIdEquals(trlPk.getId());
						
						if(traType.length>0 && traType!=null){
							traType[0].setIsBussinessType(travelForm.getIsBussinessType());
							travellerTypeDao.update(new TravellerTypePk(traType[0].getId()), traType[0]);
						}else{
							logger.error("failed to update traveller type:");
							travelDao.update(new TravelPk(travelDtoToRollBack.getId()), travelDtoToRollBack);
							throw new Exception();
						}
						
						if(travelDto.getIsContactPersonReq()==1){
							TravelContactDetailsDao travelContactDetailsDao=TravelContactDetailsDaoFactory.create();
							TravelContactDetails travelContactDetails[]=travelContactDetailsDao.findWhereTlIdEquals(trlPk.getId());
							if(travelContactDetails.length>0 && travelContactDetails!=null){
								travelContactDetails[0].setContactPerson(travelDto.getContactPerson());
								travelContactDetails[0].setPhoneNo(travelDto.getPhoneNo());
								travelContactDetails[0].setEmailId(travelDto.getEmailId());
								travelContactDetails[0].setAddress(travelDto.getAddress());
								try{
									travelContactDetailsDao.update(new TravelContactDetailsPk(travelContactDetails[0].getId()), travelContactDetails[0]);
								}catch (TravelContactDetailsDaoException e) {									
									logger.error("failed to update contact information:",e);
									travelDao.update(new TravelPk(travelDtoToRollBack.getId()), travelDtoToRollBack);
									travellerTypeDao.update(new TravellerTypePk(traTypeBackup[0].getId()), traTypeBackup[0]);
									result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
									logger.error("errors.update.failed.travel  :",e);										
									}
							}else{
								TravelContactDetails trvlContactDetails=new TravelContactDetails();
								trvlContactDetails.setContactPerson(travelDto.getContactPerson());
								trvlContactDetails.setPhoneNo(travelDto.getPhoneNo());
								trvlContactDetails.setEmailId(travelDto.getEmailId());
								trvlContactDetails.setAddress(travelDto.getAddress());
								trvlContactDetails.setTlId(travelDto.getId());
								try{
									travelContactDetailsDao.insert(trvlContactDetails);
								}catch (TravelContactDetailsDaoException e) {
									
									logger.error("failed to failed to update contact information:",e);
									travelDao.update(new TravelPk(travelDtoToRollBack.getId()), travelDtoToRollBack);
									travelDao.update(new TravelPk(travelDtoToRollBack.getId()), travelDtoToRollBack);
									travellerTypeDao.update(new TravellerTypePk(traTypeBackup[0].getId()), traTypeBackup[0]);
										
									result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
									logger.error("errors.update.failed.travel  :",e);										
									}								
							}							
							
						}	
						
					}
					else{

					}
					}catch (Exception e) {
						e.printStackTrace();
						logger.error("failed to failed to update travel request:",e);
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
						logger.error("errors.update.failed.travel  :",e);
					}

					break;
					
					
				case SUBMIT:
					try{
						travelForm.setIsBussinessType(0);					
						setProcChain(request);				
						String validateHandlers=processEvaluator.validateProcessChain(procChain,loginUSer.getUserId(),false,true);				
						String validateApprovers=processEvaluator.validateProcessChain(procChain,loginUSer.getUserId(),true,false);				
						if(travelForm.getIsBussinessType()!=0){
							//parse validateApprovers
							try{
								Integer.parseInt(validateApprovers);
							}catch(NumberFormatException nfex){
								logger.error("SERVICE REQUEST PRE-PREPROCESS FAILED FOR REQUESTER_ID="+login.getUserId()+" SERVICE_REQUEST IS TRAVEL ERR_CODE="+validateApprovers);
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest",validateApprovers));
								result.setForwardName("success");
								return result;
							}
						}else{
						//parse validateHandlers
							try{
								Integer.parseInt(validateHandlers);
							}catch(NumberFormatException nfex){
								logger.error("SERVICE REQUEST PRE-PREPROCESS FAILED FOR REQUESTER_ID="+login.getUserId()+" SERVICE_REQUEST IS TRAVEL ERR_CODE="+validateApprovers);
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest",validateApprovers));
								result.setForwardName("success");
								return result;
							}					
						}				
						travelPk.setId(travelForm.getTravelId());
						setProcChain(request);
						boolean flag2 = false;

						eMapPk = saveESRM(request);

						if (eMapPk.getId() != 0){
							Travel travelDto2 = tDao.findByPrimaryKey(travelForm.getTravelId());
							Travel travelDto2Backup = tDao.findByPrimaryKey(travelForm.getTravelId());
							
							if(travelDto2!=null){
								travelDto2.setStatus(Status.getStatusId(Status.REQUESTRAISED));
								travelDto2.setCreateDate(new Date());
								travelDto2.setEsrqmId(eMapPk.getId());
								getTravel(travelDto2, travelForm);
								try{
									travelDao.update(travelPk, travelDto2);								
								}catch (Exception e) {
									logger.error("failed to update tavel:",e);
									try{
										eMapDao.delete(eMapPk);
									}catch (Exception e2) {
										e.printStackTrace();
									}
									throw new Exception();
								}
							}
							
							try{
								updateESRM(eMapPk, travelPk.getId());								
							}catch (Exception e) {								
								travelDto2Backup.setEsrqmId(0);
								travelDao.update(travelPk, travelDto2Backup);
								eMapDao.delete(eMapPk);								
								logger.error("failed to update empservice request map",e);
								throw new Exception();								
							}
							
							travelForm.setTravelReqId(travelDto2.getId());
							TravelReq travelReq2 = travelForm.getTravelReq(travelForm);
							try{
								flag2 = submit(travelDto2, travelReq2, procChain,eMapPk, login);								
							}catch (TravelMailException e) {								
								throw new TravelMailException();								
							}catch (TravelNotificationException e) {								
								throw new TravelNotificationException();								
							}catch (Exception e) {
								logger.error("failed to submit",e);
								e.printStackTrace();
								throw new Exception();
							}

							finally{
								if (!flag2){
									try{
										eMapDao.delete(eMapPk);
										if (travelDto2.getId() != 0){
											travelDto2.setEsrqmId(0);
											travelDto2.setStatus(Status.getStatusId(Status.REQUESTSAVED));
											travelDto2.setCreateDate(null);
											travelDao.update(travelPk, travelDto2);
										}
									} catch (EmpSerReqMapDaoException e){
										logger.error("failed to delete eMapPk while submitting travel request:",e);
										e.printStackTrace();
									}
								}
							}
							
						}
					}catch(TravelNotificationException trvNotifExp){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest","ERR-007"));
						logger.error("failed to send notification",trvNotifExp);
						trvNotifExp.printStackTrace();						
					}catch (TravelMailException trvlMailExp) {
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest","ERR-006"));
						logger.error("failed to send notification",trvlMailExp);
						trvlMailExp.printStackTrace();
					}catch (Exception e) {
						e.printStackTrace();
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.submit.failed.travel"));
						logger.error("failed to submit",e);
						e.printStackTrace();						
					}
					
					
				default:					
					break;					
					
			}
		} catch (Exception e){
			e.printStackTrace();
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.failedtoProcessTheAction.travel"));
			logger.error("failedtoProcessTheAction:",e);
		}

		return result;
	}
	
	
	//common code to send mail to present level approvers
	private String sendInfoToApproversInPresentLevel(List<String>presentLevelApproverIds, int loginId,TravelReq travelReq,int tlReqId, int statusId) throws Exception{		
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		TravelDao travelDao = TravelDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		PortalMail portalMail = new PortalMail();
		List<String> ccMailIds = new ArrayList<String>();
		List<String> toMailIds = new ArrayList<String>();				
		
		for(Iterator<String>idIterator=presentLevelApproverIds.iterator();idIterator.hasNext();){
			Integer approverId = Integer.parseInt(idIterator.next());
			if(approverId != loginId){
				ccMailIds.add(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(approverId).getProfileId()).getOfficalEmailId());									
			}else{
				toMailIds.add(profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(approverId).getProfileId()).getOfficalEmailId());									
			}								
		}
		
		if(toMailIds.size()==0 && ccMailIds.size()>0){
			toMailIds = Arrays.asList(new String[ccMailIds.size()]);
			Collections.copy(toMailIds, ccMailIds);
			ccMailIds.clear();
		}
		
		portalMail.setAllReceipientMailId(toMailIds.toString().replace('[', ' ').replace(']',' ').trim().split(","));
		if(ccMailIds.size()>0){
			portalMail.setAllReceipientcCMailId(ccMailIds.toString().replace('[', ' ').replace(']',' ').trim().split(","));
		}
		
		int useThisAsAssignedTo = 0 ;//confusing??
		String inboxSubject = null;
		String inboxStatus = null;
		EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create();
		Travel travelDetails = travelDao.findByPrimaryKey(tlReqId);
		EmpSerReqMap esrRow = esrDao.findByPrimaryKey(travelDetails.getEsrqmId());
		MailGenerator mailGenerator = new MailGenerator();
		String msgBody = null;
		
		switch(statusId){
		case 2:
			//ACCEPTED
			portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_APPROVED_APPROVER);			
			portalMail.setMailSubject("Diksha Lynx: Travel Request Approved");
			portalMail.setEmpFname(getProfileObjForUser(loginId).getFirstName());			
			portalMail.setTrvlReqId(esrRow.getReqId());
			portalMail.setPurposeOfTrvl(travelDetails.getPurposeOfTl());
			portalMail.setTrvlFrom(travelDetails.getTlFrom());
			portalMail.setTrvlTo(travelDetails.getTlTo());	
						
			inboxSubject = "TRAVEL REQUEST APPROVED";
			inboxStatus = "Approved";
			
			useThisAsAssignedTo = 0;
			break;
		case 3:
			//REJECTED
			portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_REJECTED_APPROVER);		
			portalMail.setMailSubject("Diksha Lynx: Travel Request Rejected");
			portalMail.setEmpFname(getProfileObjForUser(loginId).getFirstName());
			travelDetails = travelDao.findByPrimaryKey(travelReq.getTlReqId());			
			portalMail.setTrvlReqId(esrRow.getReqId());
			portalMail.setPurposeOfTrvl(travelDetails.getPurposeOfTl());
			portalMail.setTrvlFrom(travelDetails.getTlFrom());
			portalMail.setTrvlTo(travelDetails.getTlTo());			
			
			inboxSubject = "TRAVEL REQUEST REJECTED";
			inboxStatus = "Rejected By Approver";
			
			useThisAsAssignedTo = 0;			
			break;
		case 25:
			//REQUEST_RAISED for next set of approvers
			TravelRequest travel = travelDao.findTravelId(new Object[] {tlReqId});
			
			// finding department for requester
			DivisonDao divisonDao = DivisonDaoFactory.create();
			Divison divison = DivisonDaoFactory.create().findByPrimaryKey(travel.getDivisionId());
			int parentId = divison.getParentId();
			while (parentId > 0) {
				divison = divisonDao.findByPrimaryKey(parentId);
				parentId = divison.getParentId();
			}
			
			portalMail.setTemplateName(MailSettings.TRAVEL_REQUEST_ASSIGNED);//check this...change this template later if required
			portalMail.setMailSubject("Diksha Lynx: New Travel Request Submitted");
			ProfileInfo requestorProfileInfo = getProfileObjForUser(travel.getRaisedBy());
			portalMail.setTrvlRequestorName(requestorProfileInfo.getFirstName()+" "+requestorProfileInfo.getLastName());
			portalMail.setTravellerEmpId(usersDao.findByPrimaryKey(travel.getRaisedBy()).getEmpId());
			portalMail.setTravellerName(portalMail.getTrvlRequestorName());//you are here because it is a self travel
			portalMail.setTravellerDepartment(divison.getName());
			portalMail.setTravellerDesignation(travel.getDesignation());
			portalMail.setDateOfSubmission(travel.getCreatedDate());
			portalMail.setTrvlReqId(esrRow.getReqId());
			portalMail.setPurposeOfTrvl(travel.getPurposeOfTl());
			portalMail.setTrvlFrom(travel.getTlFrom());
			portalMail.setTrvlTo(travel.getTlTo());
			if(travel.getTypeOfAccomodation()==null){
				portalMail.setTypeOfAccomodation("N.A");
			}else{
				portalMail.setTypeOfAccomodation(travel.getTypeOfAccomodation());
			}
			
			useThisAsAssignedTo = 1;//request raised info must be visible to all handlers in their DockingStation...
			
			inboxSubject = "NEW TRAVEL REQUEST RAISED";	
			inboxStatus = "Request Raised";
			break;
		}
		
		
		/*
		 * Including Travel-Details in the mail template (visible to all)
		 * mode of travel
		 * travelling from
		 * travelling to
		 * preferred date of travel
		 * preferred time of travel
		 * one way or round trip
		 * is accommodation required (if yes....??)
		 * is cab required (??onward_inward)
		 */
		
		Travel travelDetails2 = travelDao.findByPrimaryKey(tlReqId);
		
		portalMail.setTravelMode(travelDetails2.getModeOfTl());
		portalMail.setTravellingFrom(travelDetails2.getTlFrom());
		portalMail.setTravellingTo(travelDetails2.getTlTo());
		portalMail.setTravelPrefDate(""+travelDetails2.getPrfDateToTl());
		portalMail.setTravelPrefTime(""+travelDetails2.getPrfTimeToTl());//formatting required
		portalMail.setTravelOneWayRoundTrip((travelDetails2.getIsRoundTrip()==1)?"Round Trip":"One Way");//formatting required
		if(travelDetails2.getReturnDate()==null){
			portalMail.setTravelRetDate("N.A");
		}else{
			portalMail.setTravelRetDate(""+travelDetails2.getReturnDate());
		}
		if(travelDetails2.getReturnTime()==null){
			portalMail.setTravelRetTime("N.A");
		}else{
			portalMail.setTravelRetTime(""+travelDetails2.getReturnTime());
		}
		portalMail.setTravelAccommodationReq((travelDetails2.getAccomodationReq()==1)?"Yes":"No");//formatting required
		portalMail.setTravelAccommodationType((travelDetails2.getTypeOfAccomodation()==null)?"N.A":travelDetails2.getTypeOfAccomodation());
		portalMail.setTravelCabReq((travelDetails2.getCabReq()==1)?"Yes":"No");
		if(portalMail.getTravelCabReq().equalsIgnoreCase("NO")){
			portalMail.setTravelCabOnwardInward("N.A");
		}else{
			portalMail.setTravelCabOnwardInward((travelDetails2.getOnwardInward()==1)?"Onward":"Inward");	
		}
		
		String travellersFamily = travelDetails2.getTravellerSpouseName();
		String spouseName = "N.A";
		String children = "N.A";
		
		if(travellersFamily!=null && travellersFamily.length()>0){
			String[] spouseAndChildren = travellersFamily.split("~=~");
			if(spouseAndChildren[0]!=null && spouseAndChildren[0].length()>0){
				spouseName = spouseAndChildren[0];				
			}
			if(spouseAndChildren.length>1){
				if(spouseAndChildren[1]!=null && spouseAndChildren[1].length()>0){
					children = spouseAndChildren[1];
				}
			}		
		}
		
		if(spouseName.equalsIgnoreCase("N.A")){
			portalMail.setTravellerWithFamily("No");
		}else{
			portalMail.setTravellerWithFamily("Yes");
		}
		
		portalMail.setTravellerSpouseName(spouseName+"<br/>Children : "+children+"<br/>");
		
		String travellerRemarks = travelDetails2.getTravellerComments();
		portalMail.setTravellerRemarks((travellerRemarks==null)?"N.A":travellerRemarks);
		
		//common to all the above mentioned cases
		msgBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
		portalMail.setMailBody(msgBody);
		mailGenerator.invoker(portalMail);
		
		
		//the mail should be referred/addressed to a particular person...to avoid conflict in INBOX	
		
		Inbox inbox = null;
		for(Iterator<String>idIterator=presentLevelApproverIds.iterator();idIterator.hasNext();){
			inbox = new Inbox();								
			inbox.setReceiverId(Integer.parseInt(idIterator.next()));
			inbox.setEsrMapId(travelDetails.getEsrqmId());
			inbox.setSubject(inboxSubject);
			if(useThisAsAssignedTo==0){//for ACCEPTED REJECTED the msg should be visible only in notification				
				inbox.setAssignedTo(useThisAsAssignedTo);
			}else{
				inbox.setAssignedTo(inbox.getReceiverId());//request raised...to handler....each person must see this in their docking station
			}
			inbox.setRaisedBy(travelDetails.getRaisedBy());
			inbox.setCreationDatetime(new Date());							
			inbox.setStatus(inboxStatus);
			inbox.setCategory("TRAVEL");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setMessageBody(msgBody);
			inboxDao.insert(inbox);								
		}
			
		return msgBody;
	}

	private void updateNextSetOfApproversInTravellerType(TravellerType travellerType,TravellerTypeDao travellerTypeDao) throws TravellerTypeDaoException {
		String ip = travellerType.getNextSetOfApprovers();
		if(ip.indexOf("~=~")>0){
			travellerType.setNextSetOfApprovers(ip.substring(ip.indexOf("~=~")).replaceFirst("~=~", " ").trim());
			travellerTypeDao.update(new TravellerTypePk(travellerType.getId()), travellerType);		
		}
		
	}
	
	

	private void updateInboxStatus(int esrqmId,int assignedToHandlerId,String status) {
		// TODO Auto-generated method stub
		String updateInboxQuery = "UPDATE INBOX SET STATUS='"+status+"' , ASSIGNED_TO="+assignedToHandlerId+" WHERE ESR_MAP_ID="+esrqmId;
		try{
			InboxDaoFactory.create().executeUpdate(updateInboxQuery);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	private void updateTravelAndTravelReqTables(TravelForm travelForm, Travel travelRow) throws TravelReqDaoException, TravelDaoException {
		// TODO Auto-generated method stub
		// WORK IN-PROGRESS & CANCEL IN-PROGRESS
		TravelReqDao travelReqDao = TravelReqDaoFactory.create();
		TravelDao travelDao = TravelDaoFactory.create();		
		String inProgressQuery1 = "UPDATE TRAVEL_REQ SET STATUS="+travelRow.getStatus()+" , ACTION_TYPE="+travelRow.getStatus()+" WHERE ID="+travelForm.getTravelReqId();
		travelReqDao.executeUpdate(inProgressQuery1);
		String inProgressQuery2 = "UPDATE TRAVEL SET STATUS="+travelRow.getStatus()+" WHERE ID="+travelRow.getId();
		travelDao.executeUpdate(inProgressQuery2);
		String inProgressQuery3 = "UPDATE TRAVEL_REQ SET STATUS="+travelRow.getStatus()+" WHERE TL_REQ_ID="+travelRow.getId();
		travelDao.executeUpdate(inProgressQuery3);
	}

	
	
	public Inbox sendDockingStationForTravel(int trvlReqId, String msg1,EmpSerReqMap esrMap, String status)	throws TravelNotificationException {
		InboxModel iModel = new InboxModel();
		Inbox inbox = iModel.sendToDockingStation(esrMap.getId(), trvlReqId,msg1, status);
		if (inbox == null) {
			logger.error("inbox is null so failed to send notification.");
			throw new TravelNotificationException();
		}
		return inbox;
		// iModel.notify(esrMapId, inbox);
	}
	
	
	

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id,HttpServletRequest request, String description){
		boolean isUpload = true;
		Integer fieldsId[] = new Integer[fileItems.size()];
		int i = 0;
		for (FileItem fileItem2 : fileItems) {
			logger.error("FileName: " + fileItem2.getName());
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			try {
				String fileName = portalData.saveFile(fileItem2, dTypes, id);
				String DBFilename = fileName;
				logger.info("filename" + fileName);
				if (fileName != null) {
					Documents documents = new Documents();
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					documents.setFilename(DBFilename);
					documents.setDoctype(docType);
					documents.setDescriptions(description);
					logger.error("The file " + fileName	+ " successfully uploaded");
					try {
						DocumentsPk documentsPk = documentsDao.insert(documents);
						fieldsId[i] = documentsPk.getId();
					} catch (DocumentsDaoException e) {
						e.printStackTrace();
					}
					i++;
				} else {
					isUpload=false;
				}
			} catch (FileNotFoundException e) {
				logger.error("file not found", e);
				isUpload=false;
				e.printStackTrace();
			}
		}
		if (isUpload) {
			logger.error("File uploaded successfully.");
		} else {
			logger.error("File uploaded failed.");
		}
		return fieldsId;
	}

	
	
	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		String seprator = File.separator;
		String path = "Data" + seprator;
		try {
			TravelForm travelForm = (TravelForm) form;
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents[] dacDocuments = documentsDao.findWhereIdEquals(travelForm.getFileId());
			PortalData portalData = new PortalData();
			path = portalData.getDirPath();
			attachements.setFileName(dacDocuments[0].getFilename());
			path = portalData.getfolder(path);
			path = path + seprator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (DocumentsDaoException e) {
			// TODO Auto-generated catch block
			logger.error("failed to download check the file path:", e);
			e.printStackTrace();
		}
		return attachements;
	}

	
	
	
	
	private void saveItemCostInfo(int travelId, TravelForm travelForm)throws Exception {
		ItemCostInfoDao itemCostInfoDao = ItemCostInfoDaoFactory.create();
		ItemCostInfoPk itemCostInfoPk = new ItemCostInfoPk();
		List<Integer> listOfIds = new ArrayList<Integer>();
		String multyitemCostinfo = travelForm.getItemCostInfo();
		ItemCostInfo itemCostInfo2[] = itemCostInfoDao.findWhereTlIdEquals(travelId);
		ItemCostInfoPk pk = new ItemCostInfoPk();

		List<Integer> itemcostinfoArray = new ArrayList<Integer>();
		List<ItemCostInfo> itemCostInfoBackup = new ArrayList<ItemCostInfo>();

		if (multyitemCostinfo != null) {
			String itemCostInfoArray[] = multyitemCostinfo.split(",");
			for (String itemCostInfos : itemCostInfoArray) {
				ItemCostInfo itemCostInfo = new ItemCostInfo();
				String itemCostinfo[] = itemCostInfos.split("~=~");
				for (String itemCost : itemCostinfo) {
					if ((itemCost.split("=")[0].trim()).equalsIgnoreCase("item") && itemCost.split("=").length > 1) {
						itemCostInfo.setItem(itemCost.split("=")[1]);
					} else if (itemCost.split("=")[0].trim().equalsIgnoreCase("itemCost") && itemCost.split("=").length > 1) {
						itemCostInfo.setItemCost(Double.parseDouble(itemCost.split("=")[1]));
					} else if (itemCost.split("=")[0].trim().equalsIgnoreCase("itemCostInfoId")	&& itemCost.split("=").length > 1) {
						itemCostInfo.setId(Integer.parseInt(itemCost.split("=")[1]));
					}
				}
				if (itemCostInfo.getId() != 0) {
					itemcostinfoArray.add(itemCostInfo.getId());
					itemCostInfoBackup.add(itemCostInfoDao.findByPrimaryKey(itemCostInfo.getId()));
					itemCostInfo.setTlId(travelId);
					itemCostInfoPk.setId(itemCostInfo.getId());
					try {
						itemCostInfoDao.update(itemCostInfoPk, itemCostInfo);
					} catch (Exception e) {
						logger.error("failed to update itemcost info");
						throw new Exception();
					}
					listOfIds.add(itemCostInfo.getId());
				} else {
					if (itemCostInfo.getItemCost() != 0) {
						itemCostInfo.setTlId(travelId);
						itemCostInfo.setId(0);
						try {
							pk = itemCostInfoDao.insert(itemCostInfo);
						} catch (Exception e) {
							logger.error("failed to save itemcost info");
							throw new Exception();
						}
						listOfIds.add(pk.getId());
					}

				}

			}
			for (ItemCostInfo itemInfo : itemCostInfo2) {
				
				if (!(listOfIds.contains(itemInfo.getId()))) {
					ItemCostInfoPk pk2 = new ItemCostInfoPk();
					pk2.setId(itemInfo.getId());
					try {
						itemCostInfoDao.delete(pk2);
					} catch (Exception e) {
						if (pk != null) {
							itemCostInfoDao.delete(pk);
						} else {
							for (Iterator<ItemCostInfo> itemIter = itemCostInfoBackup.iterator(); itemIter.hasNext();) {
								ItemCostInfo tempItemCostInfo = itemIter.next();
								itemCostInfoDao.update(new ItemCostInfoPk(tempItemCostInfo.getId()),tempItemCostInfo);
							}

						}
						logger.error("failed to delete itemcost info from db,which are not send from UI.");
						throw new Exception();
					}

				}
			}

		}

	}

	private ProfileInfo getProfileObjForUser(int userId) {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String sql = "SELECT * FROM PROFILE_INFO PFINFO LEFT JOIN USERS U ON U.PROFILE_ID=PFINFO.ID WHERE U.ID=? ";
		ProfileInfo profileInfo[] = null;
		try {
			profileInfo = profileInfoDao.findByDynamicSelect(sql,new Object[] { userId });
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return profileInfo[0];
	}

	
	private void sendNotificationforHandler(TravelReq travelReq,ProcessChain pChain, EmpSerReqMap eReqMap,	HttpServletRequest request, String subject, String templateName) {
		TravelReqDao travelReqDao = TravelReqDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();

		try {
			Login login = Login.getLogin(request);
			ProfileInfo profileInfo = getProfileObjForUser(login.getUserId());
			Users users[] = null;
			try {
				users = usersDao.findByDynamicWhere("PROFILE_ID=?",new Object[] { profileInfo.getId() });
			} catch (Exception e) {
				logger.error(" Exception while retriving loggedin handler's user info in method sendNotificationforHandler");
			}

			ProcessEvaluator pEvaluator = new ProcessEvaluator();
			List<Integer> handlerlist = new ArrayList<Integer>();
			Integer handlers[] = pEvaluator.handlers(pChain.getHandler(),eReqMap.getRequestorId());
			if (handlers.length != 0) {
				for (Integer handler : handlers) {
					if (!login.getUserId().equals(handler)) {
						handlerlist.add(handler);
					}
				}
				if (!handlerlist.isEmpty()) {
					PortalMail portalMail = new PortalMail();
					portalMail.setMessageBody(null);
					portalMail.setMailSubject(subject);
					portalMail.setHandlerName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());
					if (users != null && users.length > 0) {
						portalMail.setHandlerEmpId(users[0].getEmpId());
					} else {
						portalMail.setHandlerEmpId(0);
						logger.error(" check empId of userId : "+ users[0].getId() + " as empId is null");
					}
					// now need to change arguments in send mail
					portalMail.setTemplateName(templateName);
					for (Integer handler : handlerlist) {
						TravelReqPk pk = new TravelReqPk();
						TravelReq trvlReq = travelReqDao.findByDynamicSelect("select * from TRAVEL_REQ where TL_REQ_ID=? AND ASSIGNED_TO=? ",
								new Object[] { travelReq.getTlReqId(),handler })[0];
						String mail = null;
						
						try {
							mail = sendEmail(travelReq.getId(), false,portalMail);
						} catch (TravelMailException e) {
							e.printStackTrace();
							logger.error(" exception while creating messagebody in method sendNotificationforHandler ");
						}
						pk.setId(trvlReq.getId());
						trvlReq.setMessageBody(mail);
						travelReqDao.update(pk, trvlReq);
						try {
							sendDockingStationForTravel(pk.getId(), subject, eReqMap, Status.REVOKED);
						} catch (TravelNotificationException e) {
							e.printStackTrace();
							logger.error(" exception while sending docking station in method sendNotificationforHandler ");
						}
					}
				}
			}

		} catch (TravelReqDaoException e) {
			logger.error("failed to retrive value from travelReq while sending notification for handler:",e);
			e.printStackTrace();
		}

	}


	private boolean setTravel(TravelRequest trlReqbean, TravelReq tlreq)throws StatusDaoException{
		StatusDao statusDao = StatusDaoFactory.create();
		com.dikshatech.portal.dto.Status status = statusDao.findByPrimaryKey(tlreq.getStatus());		
		boolean flag = false;
		trlReqbean.setTravelReqId(tlreq.getId());		
		trlReqbean.setTravelreqStatus(tlreq.getStatus());
		trlReqbean.setAssignedTo(tlreq.getAssignedTo());
		trlReqbean.setAppLevel(tlreq.getAppLevel());
		trlReqbean.setActionType(tlreq.getActionType());
		trlReqbean.setComment(tlreq.getComment());
		trlReqbean.setCurrency(tlreq.getCurrency());
		trlReqbean.setTotalCost(tlreq.getTotalCost());
		//trlReqbean.setTrlreqStatusName(status.getStatus());
		//trlReqbean.setAssignedToUser(getProfileObjForUser(tlreq.getAssignedTo()).getFirstName()+" "+getProfileObjForUser(tlreq.getAssignedTo()).getLastName());
		return flag;
	}


	public String sendEmail(int trlReqId, boolean wishToSendMail,PortalMail portalMail) throws TravelMailException {

		String msgBody = null;
		MailGenerator mailGenerator = new MailGenerator();
		try {
			String filePath = PropertyLoader.getEnvVariable() + File.separator + "Data" + File.separator + "travel";
			TravelDao travelDao = TravelDaoFactory.create();
			TravelReqDao travelReqDao = TravelReqDaoFactory.create();
			EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
			UsersDao userDao = UsersDaoFactory.create();
			DivisonDao divisonDao = DivisonDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();

			TravelReq travelReq = travelReqDao.findByPrimaryKey(trlReqId);
			TravelRequest travel = travelDao.findTravelId(new Object[] { travelReq.getTlReqId() });
			ProfileInfo profileInfo2 = getProfileObjForUser(travel.getRaisedBy());
			EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(travel.getEsrqmId());
			ProfileInfo profileInfo3 = getProfileObjForUser(travelReq.getAssignedTo());

			// requester info
			Users users = userDao.findByPrimaryKey(travel.getRaisedBy());
			// traveller info

			Users travellersUserInfo = userDao.findByPrimaryKey(travel.getTrlUserId());
			ProfileInfo travellerProfileInfo = getProfileObjForUser(travellersUserInfo.getId());
			Levels levels = levelsDao.findByPrimaryKey(travellersUserInfo.getLevelId());
			Divison divisonForTraveller = divisonDao.findByPrimaryKey(levels.getDivisionId());

			// finding department for traveller
			int parentIdForTraveller = divisonForTraveller.getParentId();
			while (parentIdForTraveller > 0) {
				divisonForTraveller = divisonDao.findByPrimaryKey(parentIdForTraveller);
				parentIdForTraveller = divisonForTraveller.getParentId();
			}

			// finding department for requester
			Divison divison = divisonDao.findByPrimaryKey(travel.getDivisionId());
			int parentId = divison.getParentId();
			while (parentId > 0) {
				divison = divisonDao.findByPrimaryKey(parentId);
				parentId = divison.getParentId();
			}

			if (travel.getDocumentsId() != 0) {
				DocumentsDao documentsDao = DocumentsDaoFactory.create();
				Documents doc = documentsDao.findByPrimaryKey(travel.getDocumentsId());
				Attachements attachment = new Attachements();
				Attachements attachments[] = new Attachements[1];

				attachment.setFileName(doc.getFilename());
				attachment.setFilePath(filePath + File.separator+ doc.getFilename());

				attachments[0] = attachment;
				portalMail.setFileSources(attachments);
			}

			portalMail.setTrvlReqId(empSerReqMap.getReqId());
			portalMail.setTrvlFrom(travel.getTlFrom());
			portalMail.setTrvlTo(travel.getTlTo());
			portalMail.setPurposeOfTrvl(travel.getPurposeOfTl());
			portalMail.setTrvlRequestorName(profileInfo2.getFirstName() + " "+ profileInfo2.getLastName());
			portalMail.setRequestorEmpId(users.getEmpId());
			portalMail.setEmpDivision(travel.getDivisionName());
			portalMail.setEmpDepartment(divison.getName());
			portalMail.setEmpDesignation(travel.getDesignation());
			portalMail.setDateOfSubmission(travel.getCreatedDate());			
			portalMail.setTypeOfAccomodation(travel.getTypeOfAccomodation()==null?("N.A"):travel.getTypeOfAccomodation());
			portalMail.setApproverName(profileInfo3.getFirstName() + " "+ profileInfo3.getLastName());
			//portalMail.setDate(travelReq.getDateOfCompletion().toString());
			//portalMail.setComments(travelReq.getComment());
			portalMail.setTravellerEmpId(travellersUserInfo.getEmpId());
			portalMail.setTravellerName(travellerProfileInfo.getFirstName()+ " " + travellerProfileInfo.getLastName());
			portalMail.setTravellerDepartment(divisonForTraveller.getName());
			portalMail.setTravellerDesignation(levels.getDesignation());

			
			/*
			 * Including Travel-Details in the mail template (visible to all)
			 * mode of travel
			 * travelling from
			 * travelling to
			 * preferred date of travel
			 * preferred time of travel
			 * one way or round trip
			 * is accommodation required (if yes....??)
			 * is cab required (??onward_inward)
			 */
			
			portalMail.setTravelMode(travel.getModeOfTl());
			portalMail.setTravellingFrom(travel.getTlFrom());
			portalMail.setTravellingTo(travel.getTlTo());
			portalMail.setTravelPrefDate(""+travel.getPrfDateToTl());
			portalMail.setTravelPrefTime(""+travel.getPrfTimeToTl());//formatting required
			portalMail.setTravelOneWayRoundTrip((travel.getIsRoundTrip()==1)?"Round Trip":"One Way");//formatting required
			if(travel.getReturnDate()==null){
				portalMail.setTravelRetDate("N.A");
			}else{
				portalMail.setTravelRetDate(""+travel.getReturnDate());
			}
			if(travel.getReturnTime()==null){
				portalMail.setTravelRetTime("N.A");
			}else{
				portalMail.setTravelRetTime(""+travel.getReturnTime());
			}
			portalMail.setTravelAccommodationReq((travel.getAccomodationReq()==1)?"Yes":"No");//formatting required
			portalMail.setTravelAccommodationType((travel.getTypeOfAccomodation()==null)?"N.A":travel.getTypeOfAccomodation());
			portalMail.setTravelCabReq((travel.getCabReq()==1)?"Yes":"No");
			if(portalMail.getTravelCabReq().equalsIgnoreCase("NO")){
				portalMail.setTravelCabOnwardInward("N.A");
			}else{
				portalMail.setTravelCabOnwardInward((travel.getOnwardInward()==1)?"Onward":"Inward");	
			}
			
			String travellerRemarks = travel.getTravellerComments();
			String travellersFamily = travel.getTravellerSpouseName();
			String spouseName = "N.A";
			String children = "N.A";
			
			if(travellersFamily!=null && travellersFamily.length()>0){
				String[] spouseAndChildren = travellersFamily.split("~=~");
				if(spouseAndChildren[0]!=null && spouseAndChildren[0].length()>0){
					spouseName = spouseAndChildren[0];				
				}
				if(spouseAndChildren.length>1){
					if(spouseAndChildren[1]!=null && spouseAndChildren[1].length()>0){
						children = spouseAndChildren[1];
					}
				}
							
			}
			
			if(spouseName.equalsIgnoreCase("N.A") && children.equalsIgnoreCase("N.A")){
				portalMail.setTravellerWithFamily("No");
			}else{
				portalMail.setTravellerWithFamily("Yes");
			}
			
			portalMail.setTravellerRemarks((travellerRemarks==null)?"N.A":travellerRemarks);
			portalMail.setTravellerSpouseName(spouseName+"<br/>Children : "+children+"<br/>");
			
			if (wishToSendMail) { // send mail as usual
				boolean allMailIdsFlag = true;
				if (portalMail.getAllReceipientMailId() != null) {
					for (String eachMailId : portalMail.getAllReceipientMailId()) {
						if (!(eachMailId.contains("@"))) {
							allMailIdsFlag = false;
							break;
						}
						
					}
				}

				if ((portalMail.getRecipientMailId() != null && (portalMail.getRecipientMailId()).contains("@"))
						|| (portalMail.getAllReceipientMailId() != null && allMailIdsFlag == true)){
								mailGenerator.invoker(portalMail);
				}
			}
			msgBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);

		} catch (Exception e) {
			logger.error("Exception while sending mail:", e);
			e.printStackTrace();
			throw new TravelMailException();
		}

		return msgBody;

	}
	
	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getLastLevelEntry(int travelId) {

		Object lastLevel = null;
		try {
			TravelReqDao travelReqDao = TravelReqDaoFactory.create();
			String sql = "TL_REQ_ID=? AND ACTION_TYPE=?";
			TravelReq travelReq[] = travelReqDao.findByDynamicWhere(sql,new Object[] { travelId, 2 });
			Set<Integer> appLevel = new TreeSet();
			for (TravelReq trlReq : travelReq) {
				appLevel.add(trlReq.getAppLevel());
			}
			lastLevel = Collections.max(appLevel);
			logger.info("max level:" + lastLevel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return (Integer.parseInt(lastLevel.toString()));
	}

	
	
		
	
	public int getTotalNoAppLevel(String appChain) {
		String[] approvers = appChain.split(";");
		return approvers.length;
	}

	

	
	public void getTravel(Travel travelDto, TravelForm travelForm) {

		travelDto.setIsContactPersonReq(travelForm.getIsContactPersonReq());
		travelDto.setTlType(travelForm.getTlType());
		travelDto.setTrlUserId(travelForm.getTrlUserId());
		travelDto.setPurposeOfTl(travelForm.getPurposeOfTl());
		travelDto.setIsBussinessType(travelForm.getIsBussinessType());

		if (travelForm.getIsRollOn() == 1) {
			travelDto.setIsRollOn(travelForm.getIsRollOn());
			travelDto.setChargeCode(travelForm.getChargeCode());
		} else {
			travelDto.setIsRollOn(travelForm.getIsRollOn());
			travelDto.setChargeCode(0);

		}
		travelDto.setModeOfTl(travelForm.getModeOfTl());
		travelDto.setTlFrom(travelForm.getTlFrom());
		travelDto.setTlTo(travelForm.getTlTo());
		if (travelForm.getIsContactPersonReq() == 1) {
			travelDto.setContactPerson(travelForm.getContactPerson());
			travelDto.setPhoneNo(travelForm.getPhoneNo());
			travelDto.setEmailId(travelForm.getEmailId());
			travelDto.setAddress(travelForm.getAddress());
		}

		if (travelForm.getPrfDateToTl() != null) {
			try {
				travelDto.setPrfDateToTl(PortalUtility.fromStringToDate(travelForm.getPrfDateToTl()));
			} catch (ParseException e) {
				logger.error("ParseException while setting PrfDateToTl from travel form to travelDto:",e);
				e.printStackTrace();
			}
		}

		if (travelForm.getPrfTimeToTl() != null) {
			try {
				travelDto.setPrfTimeToTl(PortalUtility.fromStringToTime(travelForm.getPrfTimeToTl()));
			} catch (ParseException e) {
				logger.error("ParseException while setting PrfTimeToTl from travel form to travelDto:",	e);
				e.printStackTrace();
			}
		}

		if (travelForm.getIsRoundTrip() == 1) {
			travelDto.setIsRoundTrip(travelForm.getIsRoundTrip());
			if (travelForm.getReturnDate() != null) {
				try {
					travelDto.setReturnDate(PortalUtility.fromStringToDate(travelForm.getReturnDate()));
				} catch (ParseException e) {
					logger.error("ParseException while setting ReturnDate from travel form to travelDto:",e);
					e.printStackTrace();
				}
			}

			if (travelForm.getReturnTime() != null) {
				try {
					travelDto.setReturnTime(PortalUtility.fromStringToTime(travelForm.getReturnTime()));
				} catch (ParseException e) {
					logger.error("ParseException while setting ReturnTime from travel form to travelDto:",e);
					e.printStackTrace();
				}
			}
		} else if (travelForm.getIsRoundTrip() == 0	&& travelDto.getIsRoundTrip() == 1) {
			travelDto.setIsRoundTrip(travelForm.getIsRoundTrip());
			travelDto.setReturnDate(null);
			travelDto.setReturnTime(null);
		}

		if (travelForm.getAccomodationReq() == 1) {
			travelDto.setAccomodationReq(travelForm.getAccomodationReq());
			travelDto.setTypeOfAccomodation(travelForm.getTypeOfAccomodation());
		}

		if (travelForm.getCabReq() == 1) {
			travelDto.setCabReq(travelForm.getCabReq());
			travelDto.setOnwardInward(travelForm.getOnwardInward());
		}
		
		travelDto.setTravellerComments(travelForm.getTravellerComments());
		travelDto.setTravellerSpouseName(travelForm.getTravellerSpouseName());		
	}

	
	
	
	
	public boolean getActionFlag(TravelReq tlReq) {
		TravelReqDao travelReqDao = TravelReqDaoFactory.create();
		String sqlTrlRq2 = "TL_REQ_ID=? AND APP_LEVEL=?";
		Object[] sqlParam2 = new Object[] { tlReq.getTlReqId(),	tlReq.getAppLevel() };
		boolean flag = false;
		try {
			TravelReq travelReq2[] = travelReqDao.findByDynamicWhere(sqlTrlRq2,	sqlParam2);
			for (int i = 0; i < travelReq2.length; i++) {
				while (travelReq2[i].getActionType() != 0) {
					flag = true;
					break;
				}
			}
		} catch (TravelReqDaoException e) {
			logger.error("TravelReqDaoException :", e);
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
	
	private void sendNotificationforNotifiers(Inbox lastHandlersInbox,TravelReq trvlReq) throws TravelNotificationException{
		EmpSerReqMapDao empSerReqDao=EmpSerReqMapDaoFactory.create();
		ProcessChainDao processChainDao=ProcessChainDaoFactory.create();
		InboxDao inboxDao=InboxDaoFactory.create();
					
		try{
			EmpSerReqMap esrRow = empSerReqDao.findByPrimaryKey(new EmpSerReqMapPk(lastHandlersInbox.getEsrMapId()));
			ProcessChain pcRow = processChainDao.findByPrimaryKey(new ProcessChainPk(esrRow.getProcessChainId()));
			String handlerString = pcRow.getHandler();
			String approverString = pcRow.getApprovalChain();
			String notifierString = pcRow.getNotification();
			
			if(notifierString!=null && notifierString.length()>0){
				ProcessEvaluator pEval = new ProcessEvaluator();
				Integer[] notifierIds = pEval.notifiers(notifierString, esrRow.getRequestorId());
				List<Integer> handlerIdList = Arrays.asList(pEval.handlers(handlerString, esrRow.getRequestorId()));
				List<Integer> approversIds =Arrays.asList(pEval.approvers(approverString, trvlReq.getAppLevel(), esrRow.getRequestorId()));
				
				for(Integer notifier:notifierIds){
					
					if(handlerIdList.contains(trvlReq.getAssignedTo())&&approversIds.contains(trvlReq.getAssignedTo())){
						continue;
					}else if(handlerIdList.contains(trvlReq.getAssignedTo())){						
							if(handlerIdList.contains(notifier)){
								continue;//if handler has to be notified...just skip								
							}
					}
					
					Inbox inboxNotify = new Inbox();
										
					ParseTemplate pTemplate = new ParseTemplate();				
					String newMsgStr = pTemplate.updateTagData("greeting","Hi ", lastHandlersInbox.getMessageBody());
					newMsgStr = pTemplate.updateTagData("textMsg","This is to notify that ", newMsgStr);
					
					inboxNotify.setReceiverId(notifier);
					inboxNotify.setEsrMapId(lastHandlersInbox.getEsrMapId());
					inboxNotify.setSubject(lastHandlersInbox.getSubject());
					inboxNotify.setAssignedTo(lastHandlersInbox.getAssignedTo());
					inboxNotify.setRaisedBy(lastHandlersInbox.getRaisedBy());
					inboxNotify.setStatus(lastHandlersInbox.getStatus());
					inboxNotify.setCategory(lastHandlersInbox.getCategory());
					inboxNotify.setCreationDatetime(new Date());
					inboxNotify.setIsRead(0);
					inboxNotify.setIsDeleted(0);
					inboxNotify.setMessageBody(newMsgStr);
					
					inboxDao.insert(inboxNotify);					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("exception is thrown while sending notification.");
			throw new TravelNotificationException();
		}
	}	
	
	
	
	
	
	private void getApproverHandlerVisibility(int userId,TravelRequest travelRequest) throws TravelReqDaoException{
		
		TravelReqDao travelReqDao=TravelReqDaoFactory.create();
		TravelReq travelReq[]=travelReqDao.findByDynamicSelect("select * from TRAVEL_REQ where ASSIGNED_TO=?", new Object[]{new Integer(userId)});
		for(TravelReq trvlReq:travelReq)
		{
			if(trvlReq.getAppLevel()!=0)
			{
				travelRequest.setToApprove(true);
			}if(trvlReq.getAppLevel()==0)
			{
				travelRequest.setTohandle(true);
			}
		}
		
		
	}
	
	
	
	
	
	private Inbox sendToInboxForTravelRequest(int esrMapId, int trvlreqId,String subject, String status, int userId) {
		Inbox inbox = new Inbox();
		TravelReqDao travelReqqDao = TravelReqDaoFactory.create();
		TravelDao travelDao = TravelDaoFactory.create();
		try {
			travelReqqDao.setMaxRows(1);
			TravelReq travelReq = travelReqqDao.findByTravel(trvlreqId)[0];
			Travel trvl = travelDao.findByPrimaryKey(travelReq.getTlReqId());
			inbox.setSubject(subject);
			inbox.setRaisedBy(trvl.getRaisedBy());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("TRAVEL");
			inbox.setEsrMapId(esrMapId);
			inbox.setComments(travelReq.getComment());
			inbox.setMessageBody(travelReq.getMessageBody());
			try {
				InboxDao inboxDao = InboxDaoFactory.create();// send request to assigned person				
				inbox.setReceiverId(userId);
				InboxPk inboxPk = inboxDao.insert(inbox);
				inbox.setId(inboxPk.getId());				
			} catch (InboxDaoException e) {
				e.printStackTrace();
			}
		} catch (TravelReqDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return inbox;
	}
	
}
