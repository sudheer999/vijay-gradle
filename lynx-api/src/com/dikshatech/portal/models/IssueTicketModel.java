package com.dikshatech.portal.models;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.dikshatech.beans.HandlersListBean;
import com.dikshatech.beans.IssueBean;
import com.dikshatech.beans.IssueResolutionBean;
import com.dikshatech.beans.RequestIssueBean;
import com.dikshatech.beans.UserBean;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.RequestEscalation;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.IssueApproverChainReqDao;
import com.dikshatech.portal.dao.IssueCommentsDao;
import com.dikshatech.portal.dao.IssueCommentsMapDao;
import com.dikshatech.portal.dao.IssueHandlerChainReqDao;
import com.dikshatech.portal.dao.IssueResolutionDetailsDao;
import com.dikshatech.portal.dao.IssuesDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RequestedIssuesDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.IssueApproverChainReq;
import com.dikshatech.portal.dto.IssueComments;
import com.dikshatech.portal.dto.IssueCommentsMap;
import com.dikshatech.portal.dto.IssueCommentsPk;
import com.dikshatech.portal.dto.IssueHandlerChainReq;
import com.dikshatech.portal.dto.IssueHandlerChainReqPk;
import com.dikshatech.portal.dto.IssueResolutionDetails;
import com.dikshatech.portal.dto.IssueResolutionDetailsPk;
import com.dikshatech.portal.dto.Issues;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.RequestType;
import com.dikshatech.portal.dto.RequestedIssues;
import com.dikshatech.portal.dto.RequestedIssuesPk;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.IssueCommentsDaoException;
import com.dikshatech.portal.exceptions.IssueCommentsMapDaoException;
import com.dikshatech.portal.exceptions.IssueHandlerChainReqDaoException;
import com.dikshatech.portal.exceptions.IssueResolutionDetailsDaoException;
import com.dikshatech.portal.exceptions.IssuesDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.RequestedIssuesDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.IssueApproverChainReqDaoFactory;
import com.dikshatech.portal.factory.IssueCommentsDaoFactory;
import com.dikshatech.portal.factory.IssueCommentsMapDaoFactory;
import com.dikshatech.portal.factory.IssueHandlerChainReqDaoFactory;
import com.dikshatech.portal.factory.IssueResolutionDetailsDaoFactory;
import com.dikshatech.portal.factory.IssuesDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RequestTypeDaoFactory;
import com.dikshatech.portal.factory.RequestedIssuesDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class IssueTicketModel extends ActionMethods {

	protected static final Logger	logger			= Logger.getLogger(IssueTicketModel.class);
	private RequestEscalation reqEscalation=new RequestEscalation();
	
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
		if (form.getActionFlag().intValue() == 1){
			result = this.approverProcess(form, request); // no approvers....
															// so,not being used
		}
		if (form.getActionFlag().intValue() == 2){
			result = this.handlerProcess(form, request);
		}
		return result;
	}

	private ActionResult approverProcess(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		return result;
	}

	//remove it later--runs only once
	public void ServiceRequestDataRepair() throws IssueResolutionDetailsDaoException, RequestedIssuesDaoException, IssueHandlerChainReqDaoException {
		IssueResolutionDetailsDao ird = IssueResolutionDetailsDaoFactory.create();
		RequestedIssuesDao issuesDao = RequestedIssuesDaoFactory.create();
		IssueHandlerChainReqDao issueHandlerChainReqDao = IssueHandlerChainReqDaoFactory.create();
		RequestedIssues requestedIssuesArr[] = issuesDao.findAll();
		if (requestedIssuesArr.length == 0){
			return;
		} else{
			IssueResolutionDetails irdtls[] = ird.findAll();
			if (irdtls.length == 0){
				for (RequestedIssues requestedIssues : requestedIssuesArr){
					IssueResolutionDetails irdtl = new IssueResolutionDetails();
					irdtl.setEsrMapId(requestedIssues.getEsrMapId());
					irdtl.setFirstStatus("Request Raised");
					if (requestedIssues.getStatus().equalsIgnoreCase(Status.RESOLVED) || requestedIssues.getStatus().equalsIgnoreCase(Status.CLOSED)){
						irdtl.setLastStatus(requestedIssues.getStatus());
						IssueHandlerChainReq ihcr = issueHandlerChainReqDao.findByDynamicSelect("SELECT * FROM ISSUE_HANDLER_CHAIN_REQ where ESR_MAP_ID=? ORDER BY ID desc", new Object[] { requestedIssues.getEsrMapId() })[0];
						irdtl.setResolvedBy(ihcr.getAssignedTo());
						irdtl.setResolvedOn(ihcr.getCreationDatetime());
						irdtl.setClosedOn(ihcr.getCreationDatetime());
					}
					irdtl.setRequesterComments(requestedIssues.getComment());
					irdtl.setRequestedOn(requestedIssues.getSubmissionDate());
					ird.insert(irdtl);
				}
			} else{
				boolean found = false;
				for (RequestedIssues requestedIssues : requestedIssuesArr){
					for (IssueResolutionDetails issueResolutionDetails : irdtls){
						if (requestedIssues.getEsrMapId() == issueResolutionDetails.getEsrMapId()){
							found = true;
						}
					}
					if (found == false){
						IssueResolutionDetails irdtl = new IssueResolutionDetails();
						irdtl.setEsrMapId(requestedIssues.getEsrMapId());
						irdtl.setFirstStatus("Request Raised");
						if (requestedIssues.getStatus().equalsIgnoreCase(Status.RESOLVED) || requestedIssues.getStatus().equalsIgnoreCase(Status.CLOSED)){
							irdtl.setLastStatus(requestedIssues.getStatus());
							IssueHandlerChainReq ihcr = issueHandlerChainReqDao.findByDynamicSelect("SELECT * FROM ISSUE_HANDLER_CHAIN_REQ where ESR_MAP_ID=? ORDER BY ID desc", new Object[] { requestedIssues.getEsrMapId() })[0];
							irdtl.setResolvedBy(ihcr.getAssignedTo());
							irdtl.setResolvedOn(ihcr.getCreationDatetime());
							irdtl.setClosedOn(ihcr.getCreationDatetime());
						}
						irdtl.setRequesterComments(requestedIssues.getComment());
						irdtl.setRequestedOn(requestedIssues.getSubmissionDate());
						ird.insert(irdtl);
					}
				}
			}
		}
	}

	private ActionResult handlerProcess(PortalForm form, HttpServletRequest request) {
		Login loginDto = Login.getLogin(request);
		ActionResult result = new ActionResult();
		try{
			MailGenerator mailGenarator = new MailGenerator();
			InboxModel inboxModel = new InboxModel();
			UsersDao userDao = UsersDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			IssueHandlerChainReqDao issueHandlerChainReqDao = IssueHandlerChainReqDaoFactory.create();
			RequestedIssuesDao requestedIssueDao = RequestedIssuesDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			InboxDao inboxDao = InboxDaoFactory.create();
			EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
			IssueResolutionDetailsDao issueResolutionDetailsDao = IssueResolutionDetailsDaoFactory.create();
			IssueHandlerChainReq issueHandlerChainReqDto = (IssueHandlerChainReq) form;
			RequestedIssues requestedIssueDto = requestedIssueDao.findWhereEsrMapIdEquals(issueHandlerChainReqDto.getEsrMapId())[0];
			EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(requestedIssueDto.getEsrMapId());
			Users requestorUserDto = userDao.findByPrimaryKey(requestedIssueDto.getUserId().intValue());
			ProfileInfo requestorProfileInfoDto = profileInfoDao.findByPrimaryKey(requestorUserDto.getProfileId());
			// ProcessChain
			// pChain=this.getProcessChainBasedOnRoleAndFeature(userRolesDto.getRoleId(),
			// requestedIssueDto.getIssueId());
			Levels empLevel = levelsDao.findByPrimaryKey(requestorUserDto.getLevelId());
			Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
			String empDesignation = empLevel.getDesignation();
			String empDivision = requesterDivision.getName();
			String empId = String.valueOf(requestorUserDto.getEmpId());
			String empDepartment = null;
			if (requesterDivision.getParentId() == 0){
				empDepartment = requesterDivision.getName();
			} else{
				empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
			}
			String notifiers = null, notifierIds = null, handlerIds = null;
			ArrayList<Users> notifierList = null;
			ModelUtiility modelUtility = ModelUtiility.getInstance();
			List<String> handlersList = modelUtility.getSiblingUsersList(requestedIssueDto.getEsrMapId());
			if (handlersList == null || handlersList.size() < 1){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.toProcessRequest"));
				return result;
			}
			UserBean[] userBeans = UsersDaoFactory.create().findAllUserNames(ModelUtiility.getCommaSeparetedValues(handlersList), null);
			handlerIds = empSerReqMap.getSiblings();
			notifiers = empSerReqMap.getNotify(); // notifiers gives levelIds
			if (notifiers != null && !notifiers.equals("")){
				notifierList = getUserLstByLevelIds(notifiers, requestorProfileInfoDto);
				if (notifierList != null){
					notifierIds = getUserIdsFromUserList(notifierList);
				}
			}
			switch (ActionMethods.SaveTypes.getValue(issueHandlerChainReqDto.getActionStatus())) {
				case ASSIGN:{
					// save comments if any
					this.saveComments(issueHandlerChainReqDto, loginDto);
					Inbox oldInboxEntries[] = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RECEIVER_ID!=RAISED_BY", new Object[] { issueHandlerChainReqDto.getEsrMapId() });
					// to send mail to handler
					ProfileInfo handlerProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(issueHandlerChainReqDto.getAssignedTo()).getProfileId());
					String mailSubject = "Diksha Lynx: New Service Request " + requestedIssueDto.getAutoReqId() + " assigned";
					String templateName = MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_ASSIGN;
					String candidateName = requestorProfileInfoDto.getFirstName() + " " + requestorProfileInfoDto.getLastName();
					String autoIssueReqId = requestedIssueDto.getAutoReqId();
					String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(requestedIssueDto.getSubmissionDate());
					String issueDescription = "";
					String assigneeName = handlerProfile.getFirstName();
					if (requestedIssueDto.getDescription() != null){
						issueDescription = requestedIssueDto.getDescription();
					}
					PortalMail portalMail = new PortalMail();
					portalMail.setIssueApprover(assigneeName);
					portalMail.setMailSubject(mailSubject);
					portalMail.setTemplateName(templateName);
					portalMail.setCandidateName(candidateName);
					portalMail.setEmpId(empId);
					portalMail.setEmpDepartment(empDepartment);
					portalMail.setEmpDivision(empDivision);
					portalMail.setEmpDesignation(empDesignation);
					portalMail.setAutoIssueReqId(autoIssueReqId);
					portalMail.setIssueDescription(issueDescription);
					portalMail.setIssueSubmissionDate(issueSubmissionDate);
					portalMail.setAllReceipientMailId(fetchOfficialMailIds(String.valueOf(issueHandlerChainReqDto.getAssignedTo()))); // to
																																		// send
																																		// mails
																																		// to
																																		// handlers
					if (notifierIds != null){
						portalMail.setAllReceipientcCMailId(fetchOfficialMailIds(notifierIds)); // to
																								// send
																								// mails
																								// to
																								// notifiers
					}
					mailGenarator.invoker(portalMail);
					Inbox assignerInboxEntry = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RECEIVER_ID=?", new Object[] { issueHandlerChainReqDto.getEsrMapId(), loginDto.getUserId() })[0];
					String nextStatus = null;
					if (!assignerInboxEntry.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
						nextStatus = "Docked By " + assigneeName;
					} else{
						nextStatus = Status.INPROGRESS;
					}
					IssueHandlerChainReq issueHandlerChainReqArr[] = issueHandlerChainReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { new Integer(issueHandlerChainReqDto.getEsrMapId()) });
					if (issueHandlerChainReqArr != null && issueHandlerChainReqArr.length > 0){
						for (int i = 0; i < issueHandlerChainReqArr.length; i++){
							issueHandlerChainReqArr[i].setStatus("Docked By " + assigneeName);
							IssueHandlerChainReqPk isHndReqPk = new IssueHandlerChainReqPk();
							isHndReqPk.setId(issueHandlerChainReqArr[i].getId());
							issueHandlerChainReqDao.update(isHndReqPk, issueHandlerChainReqArr[i]);
						}
					}
					issueHandlerChainReqDto.setActionBy(loginDto.getUserId());
					issueHandlerChainReqDto.setStatus(nextStatus);
					issueHandlerChainReqDto.setCreationDatetime(new Date());
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_ASSIGN), portalMail);
					issueHandlerChainReqDto.setMessageBody(bodyText);
					IssueHandlerChainReqPk issueHandlePk = issueHandlerChainReqDao.insert(issueHandlerChainReqDto);
					if (issueHandlePk != null){
						// update previous inbox entry
						InboxPk inboxPK = null;
						for (Inbox inboxOld : oldInboxEntries){
							inboxOld.setAssignedTo(0);
							inboxOld.setCreationDatetime(new Date());
							inboxOld.setStatus("Docked By " + assigneeName);
							inboxOld.setIsDeleted(0);
							inboxOld.setIsRead(0);
							inboxPK = new InboxPk();
							inboxPK.setId(inboxOld.getId());
							if (inboxOld.getReceiverId() == issueHandlerChainReqDto.getAssignedTo()){
								inboxDao.delete(inboxPK);
							} else{
								inboxDao.update(inboxPK, inboxOld);
							}
						}
					}
					break;
				}
				case INPROGRESS:{
					// save comments if any
					this.saveComments(issueHandlerChainReqDto, loginDto);
					ProfileInfo handlerProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(issueHandlerChainReqDto.getAssignedTo()).getProfileId());
					String ccMailIds = null;
					// to send mail to Requester and CC to handlers/notifiers
					String mailSubject = "Diksha Lynx: Service Request Id " + requestedIssueDto.getAutoReqId() + " - Work in progress";
					String portalLink = MailSettings.PORTAL_LINK;
					String templateName = MailSettings.NOTIFICATION_REQUESTOR_ISSUE_INPROGRESS;
					String candidateName = requestorProfileInfoDto.getFirstName() + " " + requestorProfileInfoDto.getLastName();
					String autoIssueReqId = requestedIssueDto.getAutoReqId();
					String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(requestedIssueDto.getSubmissionDate());
					String issueDescription = "";
					if (requestedIssueDto.getDescription() != null){
						issueDescription = requestedIssueDto.getDescription();
					}
					PortalMail portalMail = new PortalMail();
					portalMail.setMailSubject(mailSubject);
					portalMail.setTemplateName(templateName);
					portalMail.setPortalLink(portalLink);
					portalMail.setCandidateName(candidateName);
					portalMail.setAutoIssueReqId(autoIssueReqId);
					portalMail.setIssueDescription(issueDescription);
					portalMail.setIssueSubmissionDate(issueSubmissionDate);
					portalMail.setHandlerName(handlerProfile.getFirstName());
					portalMail.setDate(issueHandlerChainReqDto.getHdEstDateResolve() != null ? PortalUtility.getdd_MM_yyyy(issueHandlerChainReqDto.getHdEstDateResolve()) : "N/A");
					portalMail.setRecipientMailId(requestorProfileInfoDto.getOfficalEmailId()); // send mail to Requester
					ccMailIds = handlerIds;
					if (notifierIds != null){
						ccMailIds += "," + notifierIds;
					}
					portalMail.setAllReceipientcCMailId(fetchOfficialMailIds(ccMailIds)); // send
																							// mail
																							// to
																							// handlers/notifiers
					mailGenarator.invoker(portalMail);
					Inbox oldInboxEntries[] = inboxDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { issueHandlerChainReqDto.getEsrMapId() });
					issueHandlerChainReqDto.setActionBy(loginDto.getUserId());
					issueHandlerChainReqDto.setStatus(Status.INPROGRESS);
					issueHandlerChainReqDto.setCreationDatetime(new Date());
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.NOTIFICATION_REQUESTOR_ISSUE_INPROGRESS), portalMail);
					issueHandlerChainReqDto.setMessageBody(bodyText);
					IssueHandlerChainReqPk issueHandlePk = issueHandlerChainReqDao.insert(issueHandlerChainReqDto);
					if (issueHandlePk != null){
						requestedIssueDto.setStatus(Status.INPROGRESS);
						requestedIssueDao.update(new RequestedIssuesPk(requestedIssueDto.getId()), requestedIssueDto);
						// delete previous inbox entry
						InboxPk inboxPK = null;
						for (Inbox inboxOld : oldInboxEntries){
							inboxOld.setAssignedTo(0);
							inboxPK = new InboxPk();
							inboxPK.setId(inboxOld.getId());
							if (inboxOld.getReceiverId() == issueHandlerChainReqDto.getAssignedTo()){
								inboxDao.delete(inboxPK);
							} else{
								inboxDao.update(inboxPK, inboxOld);
							}
						}
					}
					break;
				}
				case RESOLVED:{
					String sql = "SELECT * FROM ISSUE_HANDLER_CHAIN_REQ WHERE ESR_MAP_ID=? AND (ASSIGNED_TO=? OR ACTION_BY=?) ORDER BY CREATION_DATETIME DESC";
					IssueHandlerChainReq issueHandlerChainReqFromDB = issueHandlerChainReqDao.findByDynamicSelect(sql, new Object[] { issueHandlerChainReqDto.getEsrMapId(), loginDto.getUserId(), loginDto.getUserId() })[0];
					// save comments if any
					this.saveComments(issueHandlerChainReqDto, loginDto);
					issueHandlerChainReqDto.setAssignedToDiv(issueHandlerChainReqFromDB.getAssignedToDiv());
					issueHandlerChainReqDto.setActionBy(loginDto.getUserId());
					issueHandlerChainReqDto.setStatus(Status.RESOLVED);
					issueHandlerChainReqDto.setCreationDatetime(new Date());
					RequestedIssues requestedIssues = requestedIssueDao.findWhereEsrMapIdEquals(issueHandlerChainReqDto.getEsrMapId())[0];
					requestedIssues.setStatus(Status.RESOLVED);
					requestedIssueDao.update(new RequestedIssuesPk(requestedIssues.getId()), requestedIssues);
					// send mails to Requester & CC to handlers/notifiers
					String mailSubject = "Diksha Lynx: Service Request " + requestedIssues.getAutoReqId() + " - Resolution ";
					String templateName = MailSettings.ISSUE_STATUS_CHANGED;
					String candidateName = requestorProfileInfoDto.getFirstName() + " " + requestorProfileInfoDto.getLastName();
					String autoIssueReqId = requestedIssues.getAutoReqId();
					String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(requestedIssues.getSubmissionDate());
					String issueDescription = "";
					if (requestedIssues.getDescription() != null){
						issueDescription = requestedIssues.getDescription();
					}
					String status = Status.RESOLVED;
					String comments = "Not Available";
					if (issueHandlerChainReqDto.getComments() != null && issueHandlerChainReqDto.getComments().trim().length() > 0){
						comments = issueHandlerChainReqDto.getComments();
					}
					PortalMail portalMail = new PortalMail();
					portalMail.setIssueApprover("");
					portalMail.setMailSubject(mailSubject);
					portalMail.setTemplateName(templateName);
					portalMail.setCandidateName(candidateName);
					portalMail.setAutoIssueReqId(autoIssueReqId);
					portalMail.setIssueReqStatus(status);
					portalMail.setComments(comments);
					portalMail.setIssueDescription(issueDescription);
					portalMail.setIssueSubmissionDate(issueSubmissionDate);
					portalMail.setDateOfAction(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()));
					portalMail.setRecipientMailId(requestorProfileInfoDto.getOfficalEmailId()); // send mail to Requester
					String ccMailIds = handlerIds;
					if (notifierIds != null){
						ccMailIds += "," + notifierIds;
					}
					portalMail.setAllReceipientcCMailId(fetchOfficialMailIds(ccMailIds)); // send
																							// mail
																							// to
																							// handlers/notifiers
					mailGenarator.invoker(portalMail);
					Inbox oldInboxEntries[] = inboxDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { issueHandlerChainReqDto.getEsrMapId() });
					Inbox oldInboxEntry = null;
					for (Inbox oldInbox : oldInboxEntries){
						if (oldInbox.getReceiverId() == requestedIssueDto.getUserId()){
							oldInboxEntry = oldInbox;
						}
					}
					InboxPk inboxPK = null;
					// populating inbox entry for Requester
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.ISSUE_STATUS_CHANGED), portalMail);
					// delete previous inbox entry
					if (oldInboxEntry != null){
						inboxPK = new InboxPk();
						inboxPK.setId(oldInboxEntry.getId());
						inboxDao.delete(inboxPK);
					}
					// populating inbox entry for handlers
					if (userBeans != null && userBeans.length > 0){
						for (UserBean user : userBeans){
							oldInboxEntry = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RECEIVER_ID=?", new Object[] { issueHandlerChainReqDto.getEsrMapId(), user.getId() })[0];
							inboxPK = new InboxPk();
							inboxPK.setId(oldInboxEntry.getId());
							inboxModel.populateInboxFromIssuesHandlers(requestedIssueDto.getEsrMapId(), mailSubject, Status.RESOLVED, 0, Integer.parseInt(user.getId()), requestedIssueDto.getUserId().intValue(), bodyText);
							// delete previous inbox entry
							inboxDao.delete(inboxPK);
						}
					}
					issueHandlerChainReqDto.setMessageBody(bodyText);
					issueHandlerChainReqDao.insert(issueHandlerChainReqDto);
					//update ISSUE_RESOLUTION_DETAILS TABLE
					IssueResolutionDetails issueResolutionDetails = issueResolutionDetailsDao.findByDynamicWhere("ESR_MAP_ID=? AND RESOLVED_BY IS NULL", new Object[] { issueHandlerChainReqDto.getEsrMapId() })[0];
					issueResolutionDetails.setLastStatus(Status.RESOLVED);
					issueResolutionDetails.setResolvedOn(new Date());
					issueResolutionDetails.setResolvedBy(loginDto.getUserId());
					issueResolutionDetailsDao.update(new IssueResolutionDetailsPk(issueResolutionDetails.getId()), issueResolutionDetails);
					break;
				}
			}// End of switch stmt
		}// End of try
		catch (Exception e){
			// TODO: handle exception
		}
		return result;
	}

	private void saveComments(IssueHandlerChainReq issueHandlerChainReqDto, Login loginDto) throws IssueCommentsMapDaoException, IssueCommentsDaoException {
		if (issueHandlerChainReqDto.getComments() != null && issueHandlerChainReqDto.getComments().trim().length() > 0){
			IssueCommentsDao issueCommentsDao = IssueCommentsDaoFactory.create();
			issueHandlerChainReqDto.setIsComment(new Short((short) 1));
			IssueComments issueComents = new IssueComments();
			issueComents.setComment(issueHandlerChainReqDto.getComments());
			issueComents.setCommentDate(new Date());
			IssueCommentsPk issueCommentPk = issueCommentsDao.insert(issueComents);
			if (issueCommentPk != null){
				IssueCommentsMapDao issueCommentMapDao = IssueCommentsMapDaoFactory.create();
				IssueCommentsMap issueCommentsMap = new IssueCommentsMap();
				issueCommentsMap.setEsrMapId(issueHandlerChainReqDto.getEsrMapId());
				issueCommentsMap.setCommentId(issueCommentPk.getId());
				issueCommentsMap.setUserId(loginDto.getUserId());
				issueCommentMapDao.insert(issueCommentsMap);
			}
		}
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			IssuesDao issueDao = IssuesDaoFactory.create();
			Login login = Login.getLogin(request);
			RequestedIssuesDao requestedIssueDao = RequestedIssuesDaoFactory.create();
			IssueApproverChainReqDao issueApprChainReqDao = IssueApproverChainReqDaoFactory.create();
			UsersDao userDao = UsersDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			RegionsDao regionDao = RegionsDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			IssueHandlerChainReqDao issuehandlerChainReqDao = IssueHandlerChainReqDaoFactory.create();
			StatusDao sDao = StatusDaoFactory.create();
			EmpSerReqMapDao empserMapDao = EmpSerReqMapDaoFactory.create();
			IssueCommentsDao issueCommentsDao = IssueCommentsDaoFactory.create();
			IssueResolutionDetailsDao issueResolutionDetailsDao = IssueResolutionDetailsDaoFactory.create();
			ModelUtiility modelUtility = ModelUtiility.getInstance();
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEISSUES:{
					RequestedIssues reqIssueDto = (RequestedIssues) form;
					IssueBean[] issueBeanArr = new IssueBean[1];
					IssueBean issuBean = null;
					RequestedIssues reqIssueTempDto = requestedIssueDao.findByPrimaryKey(reqIssueDto.getId());
					issuBean = DtoToBeanConverter.DtoToBeanConverterIssueDeptList(issueDao.findWhereFeatureIssueIdEquals(reqIssueTempDto.getIssueId())[0]);
					issuBean.setDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(reqIssueTempDto.getSubmissionDate()));
					// to get Handler comments
					String query = "SELECT CONCAT((SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID=ICM.USER_ID)),' : ',IC.COMMENT) AS ISSUE_COMMENTS " + "from ISSUE_COMMENTS IC, ISSUE_COMMENTS_MAP ICM where IC.ID = ICM.COMMENT_ID AND ICM.ESR_MAP_ID=" + reqIssueTempDto.getEsrMapId();
					ArrayList<String> resultList = issueCommentsDao.getIssueComments(query);
					String handlerComments[] = new String[resultList.size()];
					resultList.toArray(handlerComments);
					reqIssueTempDto.setHandlerComments(handlerComments);
					//to get requester comments
					String query1 = "SELECT CONCAT(date_format(REQUESTED_ON,'%d-%m-%Y %h:%i'),' : ',REQUESTER_COMMENTS) AS REQUESTER_COMMENTS FROM ISSUE_RESOLUTION_DETAILS WHERE ESR_MAP_ID=" + reqIssueTempDto.getEsrMapId();
					ArrayList<String> requesterCommentsLst = issueResolutionDetailsDao.getRequesterComments(query1);
					if (requesterCommentsLst == null || requesterCommentsLst.size() < 1){
						requesterCommentsLst = new ArrayList<String>();
						requesterCommentsLst.add(reqIssueTempDto.getComment());
					}
					String requesterComments[] = new String[requesterCommentsLst.size()];
					requesterCommentsLst.toArray(requesterComments);
					reqIssueTempDto.setRequesterComments(requesterComments);
					//to get previous resolution details of the request
					String query3 = "SELECT * from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=? AND REQUESTED_ON NOT IN(select MAX(REQUESTED_ON) from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=?) ";
					IssueResolutionDetails resolutionDetailsArr[] = null;
					resolutionDetailsArr = issueResolutionDetailsDao.findByDynamicSelect(query3, new Object[] { reqIssueTempDto.getEsrMapId(), reqIssueTempDto.getEsrMapId() });
					if (resolutionDetailsArr != null && resolutionDetailsArr.length > 0){
						IssueResolutionBean issueResolutionBean[] = new IssueResolutionBean[resolutionDetailsArr.length];
						IssueResolutionBean isRlnBn = null;
						int i = 0;
						for (IssueResolutionDetails resDtls : resolutionDetailsArr){
							isRlnBn = new IssueResolutionBean();
							isRlnBn.setStatus1(resDtls.getFirstStatus());
							isRlnBn.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getRequestedOn()));
							isRlnBn.setStatus2(Status.RESOLVED);
							isRlnBn.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getResolvedOn()));
							String resolvedBy = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(resDtls.getResolvedBy()).getProfileId()).getFirstName();
							isRlnBn.setResolvedBy(resolvedBy);
							if (resDtls.getLastStatus().equalsIgnoreCase(Status.CLOSED)){
								isRlnBn.setStatus3(Status.CLOSED);
								isRlnBn.setClosedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getClosedOn()));
							}
							issueResolutionBean[i++] = isRlnBn;
						}
						reqIssueTempDto.setPreviousResolutionDetails(issueResolutionBean);
					}
					//to get present details of request
					String query4 = "SELECT * from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=? AND REQUESTED_ON IN(select MAX(REQUESTED_ON) from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=?) ";
					resolutionDetailsArr = issueResolutionDetailsDao.findByDynamicSelect(query4, new Object[] { reqIssueTempDto.getEsrMapId(), reqIssueTempDto.getEsrMapId() });
					if (resolutionDetailsArr != null && resolutionDetailsArr.length > 0){
						reqIssueTempDto.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getRequestedOn()));
						if (resolutionDetailsArr[0].getLastStatus() != null){
							if (resolutionDetailsArr[0].getLastStatus().equalsIgnoreCase(Status.RESOLVED)){
								reqIssueTempDto.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getResolvedOn()));
							} else if (resolutionDetailsArr[0].getLastStatus().equalsIgnoreCase(Status.CLOSED)){
								reqIssueTempDto.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getResolvedOn()));
								reqIssueTempDto.setClosedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getClosedOn()));
							}
						}
					}
					issueBeanArr[0] = issuBean;
					reqIssueTempDto.setIssueBeanArr(issueBeanArr);
					request.setAttribute("actionForm", reqIssueTempDto);
					break;
				}
				case RECEIVEALLISSUES:{
					DropDown dropdown = (DropDown) form;
					String query = "SELECT * FROM REQUESTED_ISSUES WHERE USER_ID=?";
					RequestedIssues[] requestedIssueDtoArr = requestedIssueDao.findByDynamicSelect(query, new Object[] { login.getUserId() });
					RequestIssueBean[] requestedIssueBeanArr = new RequestIssueBean[requestedIssueDtoArr.length];
					int i = 0;
					for (RequestedIssues reqIssueDto : requestedIssueDtoArr){
						RequestIssueBean reqIssueBean = DtoToBeanConverter.DtoToBeanConverter(reqIssueDto, null);
						reqIssueBean.setIssueName(issueDao.findWhereFeatureIssueIdEquals(reqIssueDto.getIssueId())[0].getIsName());
						requestedIssueBeanArr[i] = reqIssueBean;
						i++;
					}
					RequestedIssues tempReqIssueDto = new RequestedIssues();
					tempReqIssueDto.setToHandle(this.isHandler(login.getUserId()));
					tempReqIssueDto.setToApprove(this.isApprover(login.getUserId()));
					tempReqIssueDto.setReqIssueBeanArray(requestedIssueBeanArr);
					dropdown.setDetail(tempReqIssueDto);
					break;
				}
				case ISSUESWITHDEPT:{
					DropDown dropdown = (DropDown) form;
					Issues[] issuesArr = issueDao.findAll();
					IssueBean[] issueBeanArr = new IssueBean[issuesArr.length];
					UserLogin userLogin = login.getUserLogin();
					Regions region = regionDao.findByPrimaryKey(userLogin.getRegionId());
					Set<com.dikshatech.beans.Roles> roles = userLogin.getRoles();
					com.dikshatech.beans.Roles roles2 = (com.dikshatech.beans.Roles) roles.toArray()[0];
					Notification notification = new Notification(region.getRefAbbreviation(), null);
					// |--We can remove below line
					int issueID = notification.issueID;
					String sql = "SELECT * FROM ISSUES ISU LEFT JOIN MODULE_PERMISSION  MP  ON ISU.FEATURE_ISSUE_ID=MP.MODULE_ID WHERE ROLE_ID=?";
					Issues[] issuesDtoArr = issueDao.findByDynamicSelect(sql, new Object[] { roles2.getRoleId() });
					int i = 0;
					for (Issues issueDto : issuesDtoArr){
						IssueBean issuBean = DtoToBeanConverter.DtoToBeanConverterIssueDeptList(issueDto);
						issueBeanArr[i] = issuBean;
						i++;
					}
					dropdown.setDropDown(issueBeanArr);
					request.setAttribute("actionForm", dropdown);
					break;
				}
				case ISSUESTOAPPROVE:{
					DropDown dropdown = (DropDown) form;
					RequestedIssues allRequestedIssues = new RequestedIssues();
					IssueApproverChainReq[] isuReqApproverArr = issueApprChainReqDao.findByDynamicSelect("SELECT * FROM ISSUE_APPROVER_CHAIN_REQ  WHERE ASSIGNED_TO=? AND STATUS='New' ORDER BY ID ", new Object[] { login.getUserId() });
					if (isuReqApproverArr.length > 0){
						Map<Integer, IssueApproverChainReq> reqIssueMapmap = new HashMap<Integer, IssueApproverChainReq>();
						List issueReqList = Arrays.asList(isuReqApproverArr);
						Set issueReqListSet = new HashSet(issueReqList);
						RequestIssueBean[] reqIssueBeanArr = new RequestIssueBean[isuReqApproverArr.length];
						int j = 0;
						for (Iterator iterator = issueReqListSet.iterator(); iterator.hasNext();){
							IssueApproverChainReq tempIsuReqApproverDto = (IssueApproverChainReq) iterator.next();
							RequestedIssues requestedIssueDto = requestedIssueDao.findWhereEsrMapIdEquals(tempIsuReqApproverDto.getEsrqmId())[0];
							if (!reqIssueMapmap.containsKey(tempIsuReqApproverDto.getId())){
								IssueApproverChainReq[] isuReqApproverArr1 = issueApprChainReqDao.findByDynamicSelect("SELECT * FROM ISSUE_APPROVER_CHAIN_REQ WHERE ESRQM_ID = ? AND ASSIGNED_TO=? ORDER BY CREATEDATETIME DESC", new Object[] { tempIsuReqApproverDto.getEsrqmId(), login.getUserId() });
								if (isuReqApproverArr1[0].getStatus().equalsIgnoreCase("New") || isuReqApproverArr1[0].getStatus().equalsIgnoreCase("Onhold")){
									// get raised by first name
									int profileId = userDao.findByPrimaryKey(isuReqApproverArr1[0].getRaisedBy()).getProfileId();
									String raisedBy = profileInfoDao.findWhereIdEquals(profileId)[0].getFirstName();
									RequestIssueBean requestedIssueBean = DtoToBeanConverter.DtoToBeanConverter(requestedIssueDto, raisedBy);
									requestedIssueBean.setStatus(isuReqApproverArr1[0].getStatus());
									requestedIssueBean.setIssueName(issueDao.findWhereFeatureIssueIdEquals(requestedIssueDto.getIssueId())[0].getIsName());
									reqIssueBeanArr[j] = requestedIssueBean;
									reqIssueMapmap.put(tempIsuReqApproverDto.getEsrqmId(), tempIsuReqApproverDto);
									j++;
								}// End of inner if
							}// End of if
						}// End of for
						reqIssueMapmap = null;
						allRequestedIssues.setNoOfIssuesToApprove(j);
						allRequestedIssues.setReqIssueBeanArray(reqIssueBeanArr);
					}
					dropdown.setDetail(allRequestedIssues);
					request.setAttribute("actionForm", dropdown);
					break;
				}
				case ISSUESTOHANDLE:{
					DropDown dropdown = (DropDown) form;
					RequestedIssues allRequestedIssues = new RequestedIssues();
					StringBuffer query = new StringBuffer("SELECT * FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=11");
					if (dropdown.getMonth() != null || dropdown.getSearchyear() != null || dropdown.getSearchName() != null){
						if (dropdown.getMonth() != null && dropdown.getToMonth() != null) query.append(" AND (MONTH(SUB_DATE) BETWEEN " + dropdown.getMonth() + " AND " + dropdown.getToMonth() + ") ");
						else if (dropdown.getMonth() != null) query.append(" AND MONTH(SUB_DATE)=" + dropdown.getMonth() + " ");
						if (dropdown.getSearchyear() != null) query.append(" AND YEAR(SUB_DATE)=" + dropdown.getSearchyear() + " ");
						if (dropdown.getSearchName() != null) query.append(" AND REQUESTOR_ID IN (SELECT ID FROM USERS U WHERE U.STATUS NOT IN ( 1, 2, 3 ) AND PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE (FIRST_NAME IS NOT NULL AND FIRST_NAME LIKE '%" + dropdown.getSearchName() + "%') OR (LAST_NAME IS NOT NULL AND LAST_NAME LIKE '%" + dropdown.getSearchName() + "%'))) ");
					} else{
						query.append(" AND TIMESTAMPDIFF(DAY,SUB_DATE,NOW()) <= 30");
					}
					EmpSerReqMap empSerReqMapArr[] = empserMapDao.findByDynamicSelect(query.toString(), new Object[] {});
					if (empSerReqMapArr.length > 0){
						int j = 0, count = 0;
						RequestIssueBean[] reqIssueBeanArr = new RequestIssueBean[empSerReqMapArr.length];
						for (EmpSerReqMap esrMap : empSerReqMapArr){
							List<String> handlerLst = modelUtility.getSiblingUsersList(esrMap.getId());
							if (!handlerLst.contains(login.getUserId().toString())){
								continue;
							}
							String SQL2 = "SELECT * FROM ISSUE_HANDLER_CHAIN_REQ  where ESR_MAP_ID=? ORDER BY ID DESC";
							IssueHandlerChainReq[] issueHandlerChainReqArr = issuehandlerChainReqDao.findByDynamicSelect(SQL2, new Object[] { esrMap.getId() });
							if (issueHandlerChainReqArr.length > 0){
								IssueHandlerChainReq ihcReq = issueHandlerChainReqArr[0];
								RequestedIssues requestedIssueDto = requestedIssueDao.findWhereEsrMapIdEquals(esrMap.getId())[0];
								Users requestedUser = userDao.findByPrimaryKey(requestedIssueDto.getUserId());
								ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
								String completionDate = null;
								if (ihcReq.getStatus().equalsIgnoreCase("Resolved") || ihcReq.getStatus().equalsIgnoreCase("Closed")){
									completionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(ihcReq.getCreationDatetime()); // to get Date of Completion
								}
								RequestIssueBean requestedIssueBean = DtoToBeanConverter.DtoToBeanConverter(requestedIssueDto, requesterProfile.getFirstName());
								if (ihcReq.getStatus().contains("Docked By")){
									requestedIssueBean.setStatus(ihcReq.getStatus());
								}
								requestedIssueBean.setIssueName(issueDao.findWhereFeatureIssueIdEquals(requestedIssueDto.getIssueId())[0].getIsName());
								requestedIssueBean.setDateOfCompletion(completionDate);
								requestedIssueBean.setToHandle(false);
								if (ihcReq.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || ihcReq.getStatus().equalsIgnoreCase(Status.REOPENED)){
									requestedIssueBean.setToHandle(true);
								} else if ((ihcReq.getStatus().contains("Docked By") || ihcReq.getStatus().equalsIgnoreCase(Status.INPROGRESS)) && ihcReq.getAssignedTo() == login.getUserId()){
									requestedIssueBean.setToHandle(true);
								}
								if (requestedIssueBean.isToHandle() && !requestedIssueDto.getStatus().equalsIgnoreCase(Status.REVOKED) && !requestedIssueDto.getStatus().equalsIgnoreCase(Status.RESOLVED)){
									count++;
								}
								reqIssueBeanArr[j] = requestedIssueBean;
								j++;
							}
						}
						allRequestedIssues.setNoOfIssuesToApprove(count);
						allRequestedIssues.setReqIssueBeanArray(reqIssueBeanArr);
					}
					dropdown.setDetail(allRequestedIssues);
					request.setAttribute("actionForm", dropdown);
					break;
				}
				case HANDLERSINGLEISSUERECEIVE:{
					RequestedIssues reqIssueDto = (RequestedIssues) form;
					IssueBean[] issueBeanArr = new IssueBean[1];
					IssueBean issuBean = null;
					RequestedIssues reqIssueTempDto = requestedIssueDao.findByPrimaryKey(reqIssueDto.getId());
					Users requestedUser = userDao.findByPrimaryKey(reqIssueTempDto.getUserId());
					ProfileInfo requesterprofile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
					Levels levelsDto = levelsDao.findByPrimaryKey(requesterprofile.getLevelId());
					//to get estimated date of resolution
					if (reqIssueTempDto != null){
						String sql = "SELECT * FROM ISSUE_HANDLER_CHAIN_REQ WHERE ESR_MAP_ID=? AND STATUS='In-progress' ORDER BY CREATION_DATETIME DESC";
						IssueHandlerChainReq[] issueHandlerChainReqDtoArr = issuehandlerChainReqDao.findByDynamicSelect(sql, new Object[] { reqIssueTempDto.getEsrMapId() });
						if (issueHandlerChainReqDtoArr.length > 0){
							reqIssueTempDto.setEstdateOfResolution(PortalUtility.getdd_MM_yyyy(issueHandlerChainReqDtoArr[0].getHdEstDateResolve()));
						}
					}
					issuBean = DtoToBeanConverter.DtoToBeanConverterIssueDeptList(issueDao.findWhereFeatureIssueIdEquals(reqIssueTempDto.getIssueId())[0]);
					issueBeanArr[0] = DtoToBeanConverter.DtoToBeanConverter(issuBean, requestedUser, requesterprofile, levelsDto);
					issueBeanArr[0].setDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(reqIssueTempDto.getSubmissionDate()));
					reqIssueTempDto.setIssueBeanArr(issueBeanArr);
					// changes done by Ganesh 0n 10 Sep 2012
					modelUtility = ModelUtiility.getInstance();
					List<String> handlerLst = modelUtility.getSiblingUsersList(reqIssueTempDto.getEsrMapId());
					if (handlerLst != null && handlerLst.size() > 0){
						UserBean[] userBeans = UsersDaoFactory.create().findAllUserNames(ModelUtiility.getCommaSeparetedValues(handlerLst), null);
						HandlersListBean[] handlerListBeanArr = new HandlersListBean[handlerLst.size()];
						HandlersListBean handlerListBean = null;
						int i = 0;
						for (UserBean user : userBeans){
							handlerListBean = new HandlersListBean();
							handlerListBean.setUserId(Integer.parseInt(user.getId()));
							handlerListBean.setUserName(user.getFirstName());
							handlerListBeanArr[i] = handlerListBean;
							i++;
						}
						reqIssueTempDto.setHandlerListBean(handlerListBeanArr);
						// start : to get dynamic status list
						String SQL2 = "SELECT * FROM ISSUE_HANDLER_CHAIN_REQ  where ESR_MAP_ID=? ORDER BY ID DESC";
						IssueHandlerChainReq ihcReq = issuehandlerChainReqDao.findByDynamicSelect(SQL2, new Object[] { reqIssueTempDto.getEsrMapId() })[0];
						ArrayList<com.dikshatech.portal.dto.Status> statusLst = new ArrayList<com.dikshatech.portal.dto.Status>();
						boolean assign = false, inProgress = false, resolved = false;
						if (handlerLst.size() == 1){
							if (ihcReq.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || ihcReq.getStatus().equalsIgnoreCase(Status.REOPENED)){
								assign = true;
								inProgress = true;
								resolved = true;
							} else if (ihcReq.getStatus().contains("Docked By")){
								inProgress = true;
								resolved = true;
							} else if (ihcReq.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
								resolved = true;
							}
						} else{
							if (ihcReq.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || ihcReq.getStatus().equalsIgnoreCase(Status.REOPENED)){
								assign = true;
							} else if (ihcReq.getStatus().contains("Docked By")){
								assign = true;
								inProgress = true;
								resolved = true;
							} else if (ihcReq.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
								assign = true;
								resolved = true;
							}
						}
						if (assign == true){
							statusLst.add(sDao.findByDynamicWhere("STATUS='" + Status.ASSIGNED + "'", new Object[] {})[0]);
						}
						if (inProgress == true){
							statusLst.add(sDao.findByDynamicWhere("STATUS='" + Status.INPROGRESS + "'", new Object[] {})[0]);
						}
						if (resolved == true){
							statusLst.add(sDao.findByDynamicWhere("STATUS='" + Status.RESOLVED + "'", new Object[] {})[0]);
						}
						com.dikshatech.portal.dto.Status[] statusArr = new com.dikshatech.portal.dto.Status[statusLst.size()];
						statusLst.toArray(statusArr);
						// end : to get dynamic status list
						reqIssueTempDto.setTempStatus("");
						if (!ihcReq.getStatus().equalsIgnoreCase(Status.RESOLVED) && !ihcReq.getStatus().equalsIgnoreCase(Status.REVOKED) && !ihcReq.getStatus().equalsIgnoreCase(Status.CLOSED)){
							if (ihcReq.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || ihcReq.getStatus().equalsIgnoreCase(Status.REOPENED)){
								reqIssueTempDto.setToHandle(true);
							} else if (ihcReq.getStatus().equalsIgnoreCase(Status.INPROGRESS) || ihcReq.getStatus().contains("Docked By")){
								if (ihcReq.getAssignedTo() == login.getUserId()){
									reqIssueTempDto.setToHandle(true);
								} else{
									ProfileInfo handlerProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(ihcReq.getAssignedTo()).getProfileId());
									reqIssueTempDto.setTempStatus("Docked By " + handlerProfile.getFirstName());
								}
							}
						}
						reqIssueTempDto.setStatuslist(statusArr);
					}
					// to get handler comments-- coded by ganesh
					String query = "SELECT CONCAT((SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID=ICM.USER_ID)),' : ',IC.COMMENT) AS ISSUE_COMMENTS " + "from ISSUE_COMMENTS IC, ISSUE_COMMENTS_MAP ICM where IC.ID = ICM.COMMENT_ID AND ICM.ESR_MAP_ID=" + reqIssueTempDto.getEsrMapId();
					ArrayList<String> resultList = issueCommentsDao.getIssueComments(query);
					String handlerComments[] = new String[resultList.size()];
					resultList.toArray(handlerComments);
					reqIssueTempDto.setHandlerComments(handlerComments);
					//to get requester comments
					String query1 = "SELECT CONCAT(date_format(REQUESTED_ON,'%d-%m-%Y %h:%i'),' : ',REQUESTER_COMMENTS) AS REQUESTER_COMMENTS FROM ISSUE_RESOLUTION_DETAILS WHERE ESR_MAP_ID=" + reqIssueTempDto.getEsrMapId();
					ArrayList<String> requesterCommentsLst = issueResolutionDetailsDao.getRequesterComments(query1);
					if (requesterCommentsLst == null || requesterCommentsLst.size() < 1){
						requesterCommentsLst = new ArrayList<String>();
						requesterCommentsLst.add(reqIssueTempDto.getComment());
					}
					String requesterComments[] = new String[requesterCommentsLst.size()];
					requesterCommentsLst.toArray(requesterComments);
					reqIssueTempDto.setRequesterComments(requesterComments);
					//to get previous resolution details of the request
					String query3 = "SELECT * from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=? AND REQUESTED_ON NOT IN(select MAX(REQUESTED_ON) from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=?) ";
					IssueResolutionDetails resolutionDetailsArr[] = null;
					resolutionDetailsArr = issueResolutionDetailsDao.findByDynamicSelect(query3, new Object[] { reqIssueTempDto.getEsrMapId(), reqIssueTempDto.getEsrMapId() });
					if (resolutionDetailsArr != null && resolutionDetailsArr.length > 0){
						IssueResolutionBean issueResolutionBean[] = new IssueResolutionBean[resolutionDetailsArr.length];
						IssueResolutionBean isRlnBn = null;
						int i = 0;
						for (IssueResolutionDetails resDtls : resolutionDetailsArr){
							isRlnBn = new IssueResolutionBean();
							isRlnBn.setStatus1(resDtls.getFirstStatus());
							isRlnBn.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getRequestedOn()));
							isRlnBn.setStatus2(Status.RESOLVED);
							isRlnBn.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getResolvedOn()));
							String resolvedBy = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(resDtls.getResolvedBy()).getProfileId()).getFirstName();
							isRlnBn.setResolvedBy(resolvedBy);
							if (resDtls.getLastStatus().equalsIgnoreCase(Status.CLOSED)){
								isRlnBn.setStatus3(Status.CLOSED);
								isRlnBn.setClosedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getClosedOn()));
							}
							issueResolutionBean[i++] = isRlnBn;
						}
						reqIssueTempDto.setPreviousResolutionDetails(issueResolutionBean);
					}
					//to get present details of request
					String query4 = "SELECT * from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=? AND REQUESTED_ON IN(select MAX(REQUESTED_ON) from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=?) ";
					resolutionDetailsArr = issueResolutionDetailsDao.findByDynamicSelect(query4, new Object[] { reqIssueTempDto.getEsrMapId(), reqIssueTempDto.getEsrMapId() });
					if (resolutionDetailsArr != null && resolutionDetailsArr.length > 0){
						reqIssueTempDto.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getRequestedOn()));
						if (resolutionDetailsArr[0].getLastStatus() != null){
							if (resolutionDetailsArr[0].getLastStatus().equalsIgnoreCase(Status.RESOLVED)){
								reqIssueTempDto.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getResolvedOn()));
							} else if (resolutionDetailsArr[0].getLastStatus().equalsIgnoreCase(Status.CLOSED)){
								reqIssueTempDto.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getResolvedOn()));
								reqIssueTempDto.setClosedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getClosedOn()));
							}
						}
					}
					request.setAttribute("actionForm", reqIssueTempDto);
					break;
				}
				case HANDLERSLIST:{
					RequestedIssues reqIssueDto = (RequestedIssues) form;
					ProcessEvaluator pEvaluator = null;
					RequestedIssues[] reqIssueTempDto = requestedIssueDao.findWhereEsrMapIdEquals(reqIssueDto.getEsrMapId());
					ProcessChain processChainDto = null;
					
					//Set escalation action
					int escEsrMapid=reqIssueTempDto[0].getEsrMapId();
					int escUserId=reqIssueTempDto[0].getUserId().intValue();
					reqEscalation.setEscalationAction(escEsrMapid,escUserId);
					
					if(reqEscalation.isEscalationAction())
						processChainDto=reqEscalation.getRequestProcessChain();
					else
						processChainDto=getProcessChainForFeatures(reqIssueTempDto[0].getEsrMapId());
					
					if (processChainDto.getHandler() != null){
						pEvaluator = new ProcessEvaluator();
						
						if(reqEscalation.isEscalationAction())
						{
							reqEscalation.setEscalationProcess(pEvaluator);
							reqEscalation.enableEscalationPermission(pEvaluator);
						}
						
						Integer[] handlerId = pEvaluator.handlers(processChainDto.getHandler(), reqIssueTempDto[0].getUserId().intValue());
						if (handlerId.length > 0){
							HandlersListBean[] handlerListBeanArr = new HandlersListBean[handlerId.length];
							HandlersListBean handlerListBean = null;
							for (int i = 0; i < handlerId.length; i++){
								handlerListBean = new HandlersListBean();
								Users userDto = userDao.findByPrimaryKey(handlerId[i]);
								ProfileInfo profileDto = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
								handlerListBean.setUserId(userDto.getId());
								handlerListBean.setUserName(profileDto.getFirstName());
								handlerListBeanArr[i] = handlerListBean;
							}
							reqIssueDto.setHandlerListBean(handlerListBeanArr);
						}
						request.setAttribute("actionForm", reqIssueDto);
					}
					break;
				}
				case DEPENDENTLIST:{
					DropDown dropdown = (DropDown) form;
					RequestedIssues tempReqIssueDto = null;
					EmpSerReqMap[] empSerMapDtoArr = empserMapDao.findWhereRequestorIdEquals(login.getUserId().intValue());
					RequestedIssues[] requestedIssueDtoArr = new RequestedIssues[empSerMapDtoArr.length];
					if (empSerMapDtoArr.length > 0){
						for (int i = 0; i < empSerMapDtoArr.length; i++){
							tempReqIssueDto = new RequestedIssues();
							RequestType requestType = (RequestTypeDaoFactory.create()).findByPrimaryKey(empSerMapDtoArr[i].getReqTypeId());
							String temp = empSerMapDtoArr[i].getReqId();
							tempReqIssueDto.setDependentId(temp);
							requestedIssueDtoArr[i] = tempReqIssueDto;
						}
					}
					dropdown.setDropDown(requestedIssueDtoArr);
					request.setAttribute("actionForm", dropdown);
					break;
				}
			}// /End of switch stmt
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		boolean temp = true;
		try{
			UsersDao userDao = UsersDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			IssueResolutionDetailsDao issueResolutionDetailsDao = new IssueResolutionDetailsDaoFactory().create();
			IssueHandlerChainReqDao issueHandlerChainReqDao = IssueHandlerChainReqDaoFactory.create();
			RequestedIssues requestedIssueDto = (RequestedIssues) form;
			RequestedIssuesDao requestedIssueDao = RequestedIssuesDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			InboxDao inboxDao = InboxDaoFactory.create();
			EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
			ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
			IssuesDao issuesDao = IssuesDaoFactory.create();
			Login loginDto = Login.getLogin(request);
			switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
				case SAVE:{
					requestedIssueDto.setStatus(Status.REQUESTRAISED);
					requestedIssueDto.setSubmissionDate(new Date());
					requestedIssueDto.setUserId(loginDto.getUserId().intValue());
					requestedIssueDao.insert(requestedIssueDto);
					temp = this.issueTicketServiceReqInvokeForHandling(form, request, requestedIssueDto);
					if (temp == false){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.toProcessRequest"));
					}
					break;
				}
				case CLOSEDBYREQUESTOR:{
					RequestedIssues requestIssueDto = requestedIssueDao.findByPrimaryKey(requestedIssueDto.getId());
					requestIssueDto.setStatus(Status.CLOSED);
					requestedIssueDao.update(new RequestedIssuesPk(requestIssueDto.getId()), requestIssueDto);
					Users requestorUserDto = userDao.findByPrimaryKey(requestIssueDto.getUserId().intValue());
					ProfileInfo requestorProfileInfoDto = profileInfoDao.findByPrimaryKey(requestorUserDto.getProfileId());
					IssueHandlerChainReq[] issueHandlerChainReqLst = issueHandlerChainReqDao.findWhereEsrMapIdEquals(requestIssueDto.getEsrMapId());
					if (issueHandlerChainReqLst != null){
						for (int i = 0; i < issueHandlerChainReqLst.length; i++){
							if (issueHandlerChainReqLst[i].getStatus().equals(Status.RESOLVED)){
								String mailSubject = "Diksha Lynx: Issue Closed";
								String templateName = MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_CLOSE;
								String candidateName = requestorProfileInfoDto.getFirstName() + " " + requestorProfileInfoDto.getLastName();
								String autoIssueReqId = requestIssueDto.getAutoReqId();
								String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(requestIssueDto.getSubmissionDate());
								String description = "";
								if (requestIssueDto.getDescription() != null){
									description = requestIssueDto.getDescription();
								}
								String issueReqStatus = Status.CLOSED;
								ProfileInfo handlerProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(issueHandlerChainReqLst[i].getActionBy()).getProfileId());
								MailGenerator mailGenarator = new MailGenerator();
								PortalMail portalMail = new PortalMail();
								portalMail.setIssueApprover(handlerProfile.getFirstName());
								portalMail.setMailSubject(mailSubject);
								portalMail.setTemplateName(templateName);
								portalMail.setCandidateName(candidateName);
								portalMail.setAutoIssueReqId(autoIssueReqId);
								portalMail.setIssueReqStatus(issueReqStatus);
								portalMail.setIssueDescription(description);
								portalMail.setIssueSubmissionDate(issueSubmissionDate);
								portalMail.setRecipientMailId(handlerProfile.getOfficalEmailId());
								mailGenarator.invoker(portalMail);
								Inbox oldInboxEntry = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RECEIVER_ID=?", new Object[] { issueHandlerChainReqLst[i].getEsrMapId(), issueHandlerChainReqLst[i].getAssignedTo() })[0];
								InboxPk inboxPK = new InboxPk();
								inboxPK.setId(oldInboxEntry.getId());
								// Sending inbox notification to the handler
								String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_CLOSE), portalMail);
								new InboxModel().populateInboxFromIssuesHandlers(issueHandlerChainReqLst[i].getEsrMapId(), mailSubject, Status.CLOSED, 0, issueHandlerChainReqLst[i].getActionBy(), requestIssueDto.getUserId().intValue(), bodyText);
								// delete previous inbox entry
								inboxDao.delete(inboxPK);
							}
						}
					}
					//update ISSUE_RESOLUTION_DETAILS TABLE
					IssueResolutionDetails issueResolutionDetails = issueResolutionDetailsDao.findByDynamicWhere("ESR_MAP_ID=? ORDER BY REQUESTED_ON DESC", new Object[] { requestIssueDto.getEsrMapId() })[0];
					issueResolutionDetails.setLastStatus(Status.CLOSED);
					issueResolutionDetails.setClosedOn(new Date());
					issueResolutionDetailsDao.update(new IssueResolutionDetailsPk(issueResolutionDetails.getId()), issueResolutionDetails);
					break;
				}
				case REVOKED:{
					RequestedIssues requestIssueDto = requestedIssueDao.findByPrimaryKey(requestedIssueDto.getId());
					requestIssueDto.setStatus(Status.REVOKED);
					requestedIssueDao.update(new RequestedIssuesPk(requestIssueDto.getId()), requestIssueDto);
					IssueHandlerChainReq issueHandlerChainReqArr[] = issueHandlerChainReqDao.findByDynamicWhere("ESR_MAP_ID=" + requestIssueDto.getEsrMapId(), new Object[0]);
					for (IssueHandlerChainReq issueHandlerChainReq : issueHandlerChainReqArr){
						issueHandlerChainReq.setStatus(Status.REVOKED);
						issueHandlerChainReq.setCreationDatetime(new Date());
						issueHandlerChainReqDao.update(new IssueHandlerChainReqPk(issueHandlerChainReq.getId()), issueHandlerChainReq);
					}
					// notify handlers and notifiers when request is revoked by
					// requester
					Users requestorUserDto = userDao.findByPrimaryKey(requestIssueDto.getUserId().intValue());
					ProfileInfo requestorProfileInfoDto = profileInfoDao.findByPrimaryKey(requestorUserDto.getProfileId());
					Levels empLevel = levelsDao.findByPrimaryKey(requestorUserDto.getLevelId());
					Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
					String empDesignation = empLevel.getDesignation();
					String empDivision = requesterDivision.getName();
					String empDepartment = null;
					if (requesterDivision.getParentId() == 0){
						empDepartment = requesterDivision.getName();
					} else{
						empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
					}
					EmpSerReqMap empSerReq = (EmpSerReqMapDaoFactory.create()).findByPrimaryKey(requestIssueDto.getEsrMapId());
					ArrayList<Users> notifierList = null;
					String notifiers = null, notifierIds = null, handlerIds = null;
					MailGenerator mailGenarator = new MailGenerator();
					InboxModel inboxModel = new InboxModel();
					handlerIds = empSerReq.getSiblings();
					notifiers = empSerReq.getNotify();
					if (notifiers != null && !notifiers.equals("")){
						notifierList = getUserLstByLevelIds(notifiers, requestorProfileInfoDto);
						if (notifierList != null){
							notifierIds = getUserIdsFromUserList(notifierList);
						}
					}
					List<String> handlersList = ModelUtiility.getInstance().getSiblingUsersList(requestIssueDto.getEsrMapId());
					UserBean[] userBeans = UsersDaoFactory.create().findAllUserNames(ModelUtiility.getCommaSeparetedValues(handlersList), null);
					if (handlersList != null && handlersList.size() > 0){
						String approver = "";
						if (handlersList.size() == 1){
							for (UserBean user : userBeans){
								Users userApproverDto = userDao.findByPrimaryKey(Integer.parseInt(user.getId()));
								ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userApproverDto.getProfileId());
								approver = profileInfoDto.getFirstName();
							}
						} else{
							approver = "All";
						}
						String templateName = MailSettings.NOTIFICATION_TO_APPROVER_ON_ISSUE_STATUS_CHANGED;
						String candidateName = requestorProfileInfoDto.getFirstName() + " " + requestorProfileInfoDto.getLastName();
						String autoIssueReqId = requestIssueDto.getAutoReqId();
						String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(requestIssueDto.getSubmissionDate());
						String description = "";
						if (requestIssueDto.getDescription() != null){
							description = requestIssueDto.getDescription();
						}
						String status = Status.REVOKED;
						String mailSubject = "Diksha Lynx: Service Request " + autoIssueReqId + " Revoked by " + candidateName;
						PortalMail portalMail = new PortalMail();
						portalMail.setIssueApprover(approver);
						portalMail.setMailSubject(mailSubject);
						portalMail.setTemplateName(templateName);
						portalMail.setCandidateName(candidateName);
						portalMail.setAutoIssueReqId(autoIssueReqId);
						portalMail.setIssueReqStatus(status);
						portalMail.setIssueDescription(description);
						portalMail.setIssueSubmissionDate(issueSubmissionDate);
						portalMail.setEmpId(String.valueOf(requestorUserDto.getEmpId()));
						portalMail.setEmpDesignation(empDesignation);
						portalMail.setEmpDepartment(empDepartment);
						portalMail.setEmpDivision(empDivision);
						portalMail.setAllReceipientMailId(fetchOfficialMailIds(handlerIds)); // to
																								// send
																								// mails
																								// to
																								// handlers
						if (notifierIds != null){
							portalMail.setAllReceipientcCMailId(fetchOfficialMailIds(notifierIds)); // to
																									// send
																									// CC
																									// to
																									// notifiers
						}
						mailGenarator.invoker(portalMail);
						for (UserBean user : userBeans){
							Inbox oldInboxEntry = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND RECEIVER_ID=?", new Object[] { requestIssueDto.getEsrMapId(), Integer.parseInt(user.getId()) })[0];
							InboxPk inboxPK = new InboxPk();
							inboxPK.setId(oldInboxEntry.getId());
							String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.NOTIFICATION_TO_APPROVER_ON_ISSUE_STATUS_CHANGED), portalMail);
							// populating inbox entry for handlers
							Inbox inbox = inboxModel.populateInboxFromIssuesHandlers(requestIssueDto.getEsrMapId(), mailSubject, Status.REVOKED, 0, Integer.parseInt(user.getId()), requestIssueDto.getUserId().intValue(), bodyText);
							// delete previous inbox entry
							inboxDao.delete(inboxPK);
						}
					}
					break;
				}
				case REOPEN:{
					Login login = Login.getLogin(request);
					UserLogin userLogin = login.getUserLogin();
					requestedIssueDto = (RequestedIssues) form;
					RequestedIssues requestedIssue = requestedIssueDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { requestedIssueDto.getEsrMapId() })[0];
					Users requestorUserDto = userDao.findByPrimaryKey(requestedIssue.getUserId().intValue());
					ProfileInfo requestorProfileInfoDto = profileInfoDao.findByPrimaryKey(requestorUserDto.getProfileId());
					Levels empLevel = levelsDao.findByPrimaryKey(requestorUserDto.getLevelId());
					Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
					String empDesignation = empLevel.getDesignation();
					String empDivision = requesterDivision.getName();
					String empDepartment = null;
					if (requesterDivision.getParentId() == 0){
						empDepartment = requesterDivision.getName();
					} else{
						empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
					}
					EmpSerReqMap empSerReqMap = empSerReqDao.findByPrimaryKey(requestedIssue.getEsrMapId());
					ProcessChain pChain = processChainDao.findByPrimaryKey(empSerReqMap.getProcessChainId());
					ArrayList<Users> handlerList = null;
					ArrayList<Users> notifierList = null;
					String handlers = null, notifiers = null, notifierIds = null, handlerIds = null;
					if (pChain != null){
						handlers = pChain.getHandler();
						if (handlers != null && !handlers.equals("")){
							handlerList = getUserLstByLevelIds(handlers, requestorProfileInfoDto);
							if (handlerList.contains(requestorUserDto)){
								handlerList.remove(requestorUserDto);
							}
							if (handlerList != null){
								handlerIds = getUserIdsFromUserList(handlerList);
							}
						}
						notifiers = pChain.getNotification();
						if (notifiers != null && !notifiers.equals("")){
							notifierList = getUserLstByLevelIds(notifiers, requestorProfileInfoDto);
							if (notifierList != null){
								notifierIds = getUserIdsFromUserList(notifierList);
							}
						}
					}
					if (handlerList == null || handlerList.size() < 1){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.toProcessRequest"));
						return result;
					}
					//update REQUESTED_ISSUES
					requestedIssue.setStatus(Status.REOPENED);
					requestedIssue.setSubmissionDate(new Date());
					requestedIssueDao.update(new RequestedIssuesPk(requestedIssue.getId()), requestedIssue);
					//to delete old entries
					JDBCUtiility jdbcUtility = JDBCUtiility.getInstance();
					String deleteQuery = "DELETE FROM ISSUE_HANDLER_CHAIN_REQ WHERE ESR_MAP_ID=?";
					int n = jdbcUtility.update(deleteQuery, new Object[] { requestedIssue.getEsrMapId() });
					logger.info("--------NO OF ISSUE_HANDLER_CHAIN_REQ table ROWS DELETED----" + n);
					deleteQuery = "DELETE FROM INBOX WHERE ESR_MAP_ID=?";
					n = jdbcUtility.update(deleteQuery, new Object[] { requestedIssue.getEsrMapId() });
					logger.info("--------NO OF INBOX table ROWS DELETED----" + n);
					//update EMP_SER_REQ_MAP
					empSerReqMap.setSiblings(handlerIds);
					empSerReqDao.update(new EmpSerReqMapPk(empSerReqMap.getId()), empSerReqMap);
					String tempStr = "";
					if (handlerList.size() == 1){
						for (Users user : handlerList){
							Users userApproverDto = userDao.findByPrimaryKey(user.getId());
							ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userApproverDto.getProfileId());
							tempStr = profileInfoDto.getFirstName();
						}
					} else{
						tempStr = "All";
					}
					PortalMail portalMail = new PortalMail();
					MailGenerator mailGenarator = new MailGenerator();
					InboxModel inboxModel = new InboxModel();
					String autoIssueReqId = requestedIssue.getAutoReqId();
					String mailSubject = "Diksha Lynx: Service Request is Reopened " + autoIssueReqId + " by " + requestorProfileInfoDto.getFirstName();
					String templateName = MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_REOPEN;
					String candidateName = requestorProfileInfoDto.getFirstName() + " " + requestorProfileInfoDto.getLastName();
					String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date());
					String issueDescription = requestedIssue.getDescription();
					Issues issue[] = issuesDao.findByDynamicWhere("FEATURE_ISSUE_ID=?", new Object[] { requestedIssue.getIssueId() });
					String issueType = issue[0].getIsName();
					portalMail.setIssueApprover(tempStr);
					portalMail.setMailSubject(mailSubject);
					portalMail.setTemplateName(templateName);
					portalMail.setCandidateName(candidateName);
					portalMail.setEmpId(String.valueOf(requestorUserDto.getEmpId()));
					portalMail.setAutoIssueReqId(autoIssueReqId);
					portalMail.setEmpDivision(empDivision);
					portalMail.setEmpDepartment(empDepartment);
					portalMail.setEmpDesignation(empDesignation);
					portalMail.setIssueDescription(issueDescription);
					portalMail.setIssueSubmissionDate(issueSubmissionDate);
					portalMail.setType(issueType);
					portalMail.setAllReceipientMailId(fetchOfficialMailIds(handlerIds));
					if (notifierIds != null){
						portalMail.setAllReceipientcCMailId(fetchOfficialMailIds(notifierIds));
					}
					mailGenarator.invoker(portalMail);
					IssueHandlerChainReq issueHandlerChainReq = null;
					String bodyText = null;
					for (Users user : handlerList){
						issueHandlerChainReq = new IssueHandlerChainReq();
						issueHandlerChainReq.setAssignedTo(user.getId());
						issueHandlerChainReq.setStatus(Status.REOPENED);
						issueHandlerChainReq.setAssignedToDiv(this.serViceRequestAssignToDept(requestedIssue.getIssueId(), userLogin.getRegionId()));
						issueHandlerChainReq.setCreationDatetime(new Date());
						issueHandlerChainReq.setIsHandler(new Short((short) 1));
						issueHandlerChainReq.setDescription(requestedIssue.getDescription());
						issueHandlerChainReq.setSummary(requestedIssue.getSummary());
						issueHandlerChainReq.setEsrMapId(requestedIssue.getEsrMapId());
						bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_CREATION), portalMail);
						issueHandlerChainReq.setMessageBody(bodyText);
						issueHandlerChainReqDao.insert(issueHandlerChainReq);
						// --| Sending inbox notification to handlers
						inboxModel.populateInboxOfConcernedDeptForISsues(requestedIssue.getEsrMapId(), user.getId(), requestedIssue.getUserId().intValue(), mailSubject, Status.REOPENED, bodyText);
					}
					/*	// send ACK mail to Requestor
						if (requestorProfileInfoDto.getOfficalEmailId() != null) {
							mailSubject = "Service Request "+ requestedIssue.getAutoReqId()+ " - Acknowledgement";
							templateName = MailSettings.ISSUE_SUBMITTED_SUCCESSFULLY;
							autoIssueReqId = requestedIssue.getAutoReqId();
							candidateName = requestorProfileInfoDto.getFirstName();
							InnerEmailService emailService1 = this.new InnerEmailService(
									mailSubject, candidateName, templateName,
									autoIssueReqId, null, null, null, null, null);
							emailService1.sendEmailToRecipient(login.getUserId());
						} */
					//code added on Nov-05 by Ganesh
					IssueResolutionDetails issueResolutionDetails = new IssueResolutionDetails();
					issueResolutionDetails.setEsrMapId(requestedIssue.getEsrMapId());
					issueResolutionDetails.setFirstStatus(Status.REOPENED);
					if (requestedIssueDto.getComment() != null && requestedIssueDto.getComment().trim().length() > 0){
						issueResolutionDetails.setRequesterComments(requestedIssueDto.getComment());
					}
					issueResolutionDetails.setRequestedOn(new Date());
					issueResolutionDetailsDao.insert(issueResolutionDetails);
				}
			}// End of switch stmt
		} catch (RequestedIssuesDaoException re){
			re.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public static final String[] fetchOfficialMailIds(String ids) throws ProfileInfoDaoException {
		String whereClause = " WHERE PI.ID IN (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN (" + ids + ") )"; // PROFILE_INFO AS PI
		ProfileInfoDao pInfoDao = ProfileInfoDaoFactory.create();
		return (pInfoDao.findOfficalMailIdsWhere(whereClause));
	}

	private boolean issueTicketServiceReqInvokeForHandling(PortalForm form, HttpServletRequest request, Object requestedIssues) {
		boolean result = true;
		MailGenerator mailGenarator = new MailGenerator();
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		UsersDao userDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		InboxModel inboxModel = new InboxModel();
		UserRolesDao userRolesDao = UserRolesDaoFactory.create();
		RequestedIssues tempRequestedIssue = (RequestedIssues) requestedIssues;
		Login login = Login.getLogin(request);
		EmpSerReqMapDao empSerDao = EmpSerReqMapDaoFactory.create();
		IssueHandlerChainReqDao issueHandlerChaimReqDao = IssueHandlerChainReqDaoFactory.create();
		RequestedIssuesDao requestedIssueDao = RequestedIssuesDaoFactory.create();
		DivisonDao divisionDao = DivisonDaoFactory.create();
		IssueResolutionDetailsDao issueResolutionDetailsDao = IssueResolutionDetailsDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		IssuesDao issuesDao = IssuesDaoFactory.create();
		UserLogin userLogin = login.getUserLogin();
		try{
			Users requestorUserDto = userDao.findByPrimaryKey(tempRequestedIssue.getUserId().intValue());
			ProfileInfo requestorProfileInfoDto = profileInfoDao.findByPrimaryKey(requestorUserDto.getProfileId());
			UserRoles userRolesDto = userRolesDao.findWhereUserIdEquals(tempRequestedIssue.getUserId().intValue());
			Levels empLevel = levelsDao.findByPrimaryKey(requestorUserDto.getLevelId());
			Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
			String empDesignation = empLevel.getDesignation();
			String empDivision = requesterDivision.getName();
			String empDepartment = null;
			if (requesterDivision.getParentId() == 0){
				empDepartment = requesterDivision.getName();
			} else{
				empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
			}
			ProcessChain pChain = this.getProcessChainBasedOnRoleAndFeature(userRolesDto.getRoleId(), tempRequestedIssue.getIssueId());
			// Changes done by Ganesh
			ArrayList<Users> handlerList = null;
			ArrayList<Users> notifierList = null;
			String handlers = null, notifiers = null, notifierIds = null, handlerIds = null;
			if (pChain != null){
				handlers = pChain.getHandler();
				if (handlers != null && !handlers.equals("")){
					handlerList = getUserLstByLevelIds(handlers, requestorProfileInfoDto);// 11 July
					if (handlerList.contains(requestorUserDto)){
						handlerList.remove(requestorUserDto);
					}
					if (handlerList != null){
						handlerIds = getUserIdsFromUserList(handlerList);
					}
				}
				notifiers = pChain.getNotification();
				if (notifiers != null && !notifiers.equals("")){
					notifierList = getUserLstByLevelIds(notifiers, requestorProfileInfoDto);
					if (notifierList != null){
						notifierIds = getUserIdsFromUserList(notifierList);
					}
				}
			}
			if (handlerList != null && handlerList.size() > 0){
				// |--Inserting record into empSerReqMap table
				RegionsDao regionsDao = RegionsDaoFactory.create();
				Regions regions = regionsDao.findByPrimaryKey(login.getUserLogin().getRegionId());
				EmpSerReqMap eMapDto = null;
				EmpSerReqMapPk eMapPk = null;
				String siblings = getUserIdsFromUserList(handlerList);
				if (!(tempRequestedIssue.getEsrMapId() > 0)){
					eMapDto = new EmpSerReqMap();
					eMapDto.setSubDate(new Date());
					eMapDto.setReqId(regions.getRefAbbreviation() + "-ISU-" + tempRequestedIssue.getId());
					eMapDto.setReqTypeId(11);
					eMapDto.setRegionId(regions.getId());
					eMapDto.setRequestorId(login.getUserId());
					eMapDto.setProcessChainId(pChain.getId());
					eMapDto.setSiblings(siblings);
					eMapDto.setNotify(notifiers);
					eMapPk = empSerDao.insert(eMapDto);
					// |--Updating RequestedIssue table
					tempRequestedIssue.setEsrMapId(eMapPk.getId());
					tempRequestedIssue.setAutoReqId(eMapDto.getReqId());
					requestedIssueDao.update(new RequestedIssuesPk(tempRequestedIssue.getId()), tempRequestedIssue);
				} else{
					eMapDto = empSerDao.findByPrimaryKey(tempRequestedIssue.getEsrMapId());
				}
				String temp = "";
				if (handlerList.size() == 1){ // to get handler name if there
												// is only one handler
					for (Users user : handlerList){
						Users userApproverDto = userDao.findByPrimaryKey(user.getId());
						ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userApproverDto.getProfileId());
						temp = profileInfoDto.getFirstName();
					}
				} else{
					temp = "All"; // use 'All' if there are more than one
									// handler
				}
				String autoIssueReqId = tempRequestedIssue.getAutoReqId();
				String mailSubject = "Diksha Lynx: New Service Request [" + autoIssueReqId + "] Raised by " + requestorProfileInfoDto.getFirstName();
				String templateName = MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_CREATION;
				String candidateName = requestorProfileInfoDto.getFirstName() + " " + requestorProfileInfoDto.getLastName();
				String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(tempRequestedIssue.getSubmissionDate());
				String issueDescription = tempRequestedIssue.getDescription();
				Issues issue[] = issuesDao.findByDynamicWhere("FEATURE_ISSUE_ID=?", new Object[] { tempRequestedIssue.getIssueId() });
				String issueType = issue[0].getIsName();
				PortalMail portalMail = new PortalMail();
				portalMail.setIssueApprover(temp);
				portalMail.setMailSubject(mailSubject);
				portalMail.setTemplateName(templateName);
				portalMail.setCandidateName(candidateName);
				portalMail.setEmpId(String.valueOf(requestorUserDto.getEmpId()));
				portalMail.setAutoIssueReqId(autoIssueReqId);
				portalMail.setEmpDivision(empDivision);
				portalMail.setEmpDepartment(empDepartment);
				portalMail.setEmpDesignation(empDesignation);
				portalMail.setIssueDescription(issueDescription);
				portalMail.setIssueSubmissionDate(issueSubmissionDate);
				portalMail.setType(issueType);
				portalMail.setAllReceipientMailId(fetchOfficialMailIds(handlerIds)); // to
																						// send
																						// mails
																						// to
																						// handlers
				if (notifierIds != null){
					portalMail.setAllReceipientcCMailId(fetchOfficialMailIds(notifierIds)); // to
																							// send
																							// mails
																							// to
																							// notifiers
				}
				mailGenarator.invoker(portalMail);
				IssueHandlerChainReq issueHandlerChainReq = null;
				String bodyText = null;
				for (Users user : handlerList){
					issueHandlerChainReq = new IssueHandlerChainReq();
					issueHandlerChainReq.setAssignedTo(user.getId());
					issueHandlerChainReq.setStatus(Status.REQUESTRAISED);
					issueHandlerChainReq.setAssignedToDiv(this.serViceRequestAssignToDept(tempRequestedIssue.getIssueId(), userLogin.getRegionId()));
					issueHandlerChainReq.setCreationDatetime(new Date());
					issueHandlerChainReq.setIsHandler(new Short((short) 1));
					issueHandlerChainReq.setDescription(tempRequestedIssue.getDescription());
					issueHandlerChainReq.setSummary(tempRequestedIssue.getSummary());
					issueHandlerChainReq.setEsrMapId(eMapDto.getId());
					bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_CREATION), portalMail);
					issueHandlerChainReq.setMessageBody(bodyText);
					issueHandlerChaimReqDao.insert(issueHandlerChainReq);
					// --| Sending inbox notification to handlers
					inboxModel.populateInboxOfConcernedDeptForISsues(eMapDto.getId(), user.getId(), tempRequestedIssue.getUserId().intValue(), mailSubject, Status.REQUESTRAISED, bodyText);
				}
				// send ACK mail to Requestor
				if (requestorProfileInfoDto.getOfficalEmailId() != null){
					mailSubject = "Diksha Lynx: Service Request " + tempRequestedIssue.getAutoReqId() + " - Acknowledgement";
					templateName = MailSettings.ISSUE_SUBMITTED_SUCCESSFULLY;
					autoIssueReqId = tempRequestedIssue.getAutoReqId();
					candidateName = requestorProfileInfoDto.getFirstName();
					InnerEmailService emailService1 = this.new InnerEmailService(mailSubject, candidateName, templateName, autoIssueReqId, null, null, null, null, null);
					emailService1.sendEmailToRecipient(login.getUserId());
				}
				//code added on Nov-05 by Ganesh
				IssueResolutionDetails issueResolutionDetails = new IssueResolutionDetails();
				issueResolutionDetails.setEsrMapId(tempRequestedIssue.getEsrMapId());
				issueResolutionDetails.setFirstStatus(Status.REQUESTRAISED);
				if (tempRequestedIssue.getComment() != null && tempRequestedIssue.getComment().trim().length() > 0){
					issueResolutionDetails.setRequesterComments(tempRequestedIssue.getComment());
				}
				issueResolutionDetails.setRequestedOn(new Date());
				issueResolutionDetailsDao.insert(issueResolutionDetails);
			} else{
				// to delete entry from RequestedIssue table if no handler to
				// process request
				RequestedIssues serviceRequest = (RequestedIssues) requestedIssues;
				requestedIssueDao.delete(serviceRequest.createPk());
				result = false;
			}
		} catch (Exception e){
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// to get Users List from level Ids
	public static final ArrayList<Users> getUserLstByLevelIds(String notifiers, ProfileInfo requestorProfileInfoDto) throws UsersDaoException {
		UsersDao userDao = UsersDaoFactory.create();
		Object[] sqlParams = null;
		//notifiers = (notifiers.trim()).replace('|', ',');
		String handlers[] = notifiers.split("\\|");
		String tempArr[] = null;
		if (handlers.length == 1){
			tempArr = handlers[0].split(",");
		} else{
			tempArr = handlers[1].split(",");
		}
		String levels = "0";
		ArrayList<Users> notifiersList = new ArrayList<Users>();
		Users RM = null, HRSPOC = null;
		for (int i = 0; i < tempArr.length; i++){
			if (tempArr[i].equals("RM")){
				RM = userDao.findByPrimaryKey(requestorProfileInfoDto.getReportingMgr());
			} else if (tempArr[i].equals("HRSPOC")){
				HRSPOC = userDao.findByPrimaryKey(requestorProfileInfoDto.getHrSpoc());
			} else{
				levels = levels + "," + tempArr[i];
			}
		}
		Users[] notifierLst = userDao.findByDynamicWhere("LEVEL_ID IN (" + levels.trim() + ")", sqlParams);
		for (Users user : notifierLst){
			short status = user.getStatus();
			if (user.getId() > 0 && status != 1 && status != 2 && status != 3){
				notifiersList.add(user);
			}
		}
		if (RM != null){
			notifiersList.add(RM);
		}
		if (HRSPOC != null){
			notifiersList.add(HRSPOC);
		}
		return notifiersList;
	}

	public static final String getUserIdsFromUserList(ArrayList<Users> userList) {
		String userIds = "0";
		for (Users user : userList){
			if (user.getId() != 0){
				userIds += "," + user.getId();
			}
		}
		return userIds;
	}

	private boolean isApprover(Integer userId) {
		IssueApproverChainReqDao issueApprChainReqDao = IssueApproverChainReqDaoFactory.create();
		boolean result = false;
		try{
			String sql = "ASSIGNED_TO = ? AND ACTION_BY IS NULL";
			Object[] sqlParams = { userId };
			IssueApproverChainReq[] isuReqApproverDto = issueApprChainReqDao.findByDynamicWhere(sql, sqlParams);
			if (isuReqApproverDto.length > 0){
				result = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private boolean isHandler(Integer userId) {
		IssueHandlerChainReqDao issueApprChainReqDao = IssueHandlerChainReqDaoFactory.create();
		boolean result = false;
		try{
			String sql = "ASSIGNED_TO = ? AND ACTION_BY IS NULL";
			Object[] sqlParams = { userId };
			IssueHandlerChainReq[] isuReqHandlerDto = issueApprChainReqDao.findByDynamicWhere(sql, sqlParams);
			if (isuReqHandlerDto.length > 0){
				result = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public final ProcessChain getProcessChainForFeatures(int empSerReqId) {
		ProcessChainDao processDao = ProcessChainDaoFactory.create();
		ProcessChain[] pChain = null;
		try{
			Object[] sqlParams = { empSerReqId };
			processDao.setMaxRows(1);
			pChain = processDao.findByDynamicWhere(" ID = (SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID = ?)", sqlParams);
		} catch (ProcessChainDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pChain.length == 0 ? null : pChain[0];
	}

	/*
	 * Inner class for email service
	 */
	private class InnerEmailService {

		private String	mailSubject			= "";
		private String	candidateName		= "";
		private String	templateName		= "";
		private String	autoIssueReqId		= "";
		private String	issueReqStatus		= "";
		private String	issueDescription	= "";
		private String	issueSubmissionDate	= "";
		private String	approverName		= "";
		private String	portalLink			= "";

		InnerEmailService(String mailSubject, String candidateName, String templateName, String autoIssueReqId, String issueReqStatus, String issueDescription, String issueSubmissionDate, String approverName, String portalLink) {
			this.mailSubject = mailSubject;
			this.candidateName = candidateName;
			this.templateName = templateName;
			this.autoIssueReqId = autoIssueReqId;
			this.issueReqStatus = issueReqStatus;
			this.issueDescription = issueDescription;
			this.issueSubmissionDate = issueSubmissionDate;
			this.approverName = approverName;
			this.portalLink = portalLink;
		}

		public PortalMail sendEmailToRecipient(int userId) {
			PortalMail portalMail = new PortalMail();
			try{
				UsersDao userDao = UsersDaoFactory.create();
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				Users userApproverDto = userDao.findByPrimaryKey(userId);
				ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userApproverDto.getProfileId());
				portalMail.setIssueApprover((approverName == null) ? profileInfoDto.getFirstName() : approverName);
				portalMail.setMailSubject(mailSubject);
				portalMail.setTemplateName(templateName);
				portalMail.setCandidateName(candidateName);
				portalMail.setAutoIssueReqId(autoIssueReqId);
				portalMail.setIssueReqStatus(issueReqStatus);
				portalMail.setIssueDescription(issueDescription);
				portalMail.setIssueSubmissionDate(issueSubmissionDate);
				portalMail.setPortalLink(portalLink);
				portalMail.setRecipientMailId(profileInfoDto.getOfficalEmailId());
				if (profileInfoDto.getOfficalEmailId() != null && profileInfoDto.getOfficalEmailId().contains("@")){
					MailGenerator mailGenarator = new MailGenerator();
					mailGenarator.invoker(portalMail);
				}
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
			} catch (Exception e){
				e.printStackTrace();
			}
			return (portalMail != null ? portalMail : null);
		}
	}// end of Inner class

	public ProcessChain getProcessChainBasedOnRoleAndFeature(int roleId, int featureId) {
		ProcessChainDao pDao = ProcessChainDaoFactory.create();
		Object[] sqlParams = { roleId, featureId };
		pDao.setMaxRows(1);
		String SQL = "ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION WHERE ROLE_ID = ? AND MODULE_ID = ?)";
		ProcessChain pChain = null;
		try{
			pChain = pDao.findByDynamicWhere(SQL, sqlParams)[0];
		} catch (ProcessChainDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pChain;
	}

	public int getHandlerUserOfParticularDept(int featureId, int regionId) {
		IssuesDao isssueDao = IssuesDaoFactory.create();
		RegionsDao regionDao = RegionsDaoFactory.create();
		int userId = 0;
		try{
			Issues issueDto = isssueDao.findWhereFeatureIssueIdEquals(featureId)[0];
			Regions region = regionDao.findByPrimaryKey(regionId);
			Notification notification = new Notification(region.getRefAbbreviation(), null);
			userId = notification.getDepartmentIdBasedOnRegion(issueDto.getDivId(), region.getRefAbbreviation());
		} catch (IssuesDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegionsDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
	}

	public int serViceRequestAssignToDept(int featureId, int regionId) {
		IssuesDao isssueDao = IssuesDaoFactory.create();
		int deptId = 0;
		try{
			Issues issueDto = isssueDao.findWhereFeatureIssueIdEquals(featureId)[0];
			deptId = issueDto.getDivId();
		} catch (IssuesDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deptId;
	}
}// end of class