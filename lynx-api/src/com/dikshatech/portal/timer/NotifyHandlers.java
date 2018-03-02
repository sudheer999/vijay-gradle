package com.dikshatech.portal.timer;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.IssueHandlerChainReqDao;
import com.dikshatech.portal.dao.ItRequestDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RequestedIssuesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.IssueHandlerChainReq;
import com.dikshatech.portal.dto.ItRequest;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.RequestedIssues;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.IssueHandlerChainReqDaoFactory;
import com.dikshatech.portal.factory.ItRequestDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RequestedIssuesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.IssueTicketModel;
import com.dikshatech.portal.models.ItModel;

public class NotifyHandlers implements Job {

	/*
	 * @author ganesh MN
	 */
	boolean					flag	= false;
	private static Logger	logger	= Logger.getLogger(CandidateReporting.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		RequestedIssuesDao requestedIssueDao = RequestedIssuesDaoFactory.create();
		UsersDao userDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ItRequestDao itReqDao = ItRequestDaoFactory.create();
		EmpSerReqMapDao empserMapDao = EmpSerReqMapDaoFactory.create();
		PortalMail portalMail = null;
		MailGenerator mailGenerator = new MailGenerator();
		/**
		 * send mail to handlers till service request status moves to resolved
		 */
		try{
			logger.info("Executing method for sending mail to handlers till service request status moves to resolved");
			LevelsDao levelsDao = LevelsDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			IssueHandlerChainReqDao issueHandlerChainReqDao = IssueHandlerChainReqDaoFactory.create();
			//remove these 2 line of code later
			IssueTicketModel ism = new IssueTicketModel();
			ism.ServiceRequestDataRepair();
			Object[] sqlParams = null;
			String sql = "STATUS IN ('Request Raised','In-progress')";
			RequestedIssues[] unresolvedIssuesLst = requestedIssueDao.findByDynamicWhere(sql, sqlParams);
			logger.info("--------------NO OF UNRESOLVED ISSUES--------- " + unresolvedIssuesLst.length);
			String handlerIds = null;
			if (unresolvedIssuesLst != null && unresolvedIssuesLst.length > 0){
				for (RequestedIssues requestedIssue : unresolvedIssuesLst){
					Users requestedUser = userDao.findByPrimaryKey(requestedIssue.getUserId());
					ProfileInfo requestorProfileInfo = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
					EmpSerReqMap empSerReqMap = empserMapDao.findByPrimaryKey(requestedIssue.getEsrMapId());
					Levels empLevel = levelsDao.findByPrimaryKey(requestedUser.getLevelId());
					Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
					if (requestedIssue.getStatus().equalsIgnoreCase("Request Raised")){
						IssueHandlerChainReq issueHandlerChainReq = issueHandlerChainReqDao.findByDynamicSelect("SELECT * FROM ISSUE_HANDLER_CHAIN_REQ  WHERE ESR_MAP_ID=? ORDER BY ID DESC ", new Object[] { requestedIssue.getEsrMapId() })[0];
						if (issueHandlerChainReq.getStatus().contains("Docked By")){
							handlerIds = Integer.toString(issueHandlerChainReq.getAssignedTo());
						} else{
							handlerIds = empSerReqMap.getSiblings();
						}
					} else{
						IssueHandlerChainReq issueHandlerChainReq = issueHandlerChainReqDao.findByDynamicSelect("SELECT * FROM ISSUE_HANDLER_CHAIN_REQ  WHERE STATUS='IN-PROGRESS' AND ESR_MAP_ID=? ORDER BY ID DESC ", new Object[] { requestedIssue.getEsrMapId() })[0];
						handlerIds = Integer.toString(issueHandlerChainReq.getAssignedTo());
					}
					if (handlerIds != null){
						String autoIssueReqId = requestedIssue.getAutoReqId();
						String mailSubject = "Service Request " + autoIssueReqId + " - Reminder";
						String templateName = MailSettings.NOTIFICATION_TO_HANDLER_ON_ISSUE_NOT_RESOLVED;
						String candidateName = requestorProfileInfo.getFirstName();
						autoIssueReqId = requestedIssue.getAutoReqId();
						String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(requestedIssue.getSubmissionDate());
						String description = "";
						if (requestedIssue.getDescription() != null){
							description = requestedIssue.getDescription();
						}
						portalMail = new PortalMail();
						portalMail.setIssueApprover("All");
						portalMail.setMailSubject(mailSubject);
						portalMail.setTemplateName(templateName);
						portalMail.setCandidateName(candidateName);
						portalMail.setAutoIssueReqId(autoIssueReqId);
						portalMail.setIssueDescription(description);
						portalMail.setIssueSubmissionDate(issueSubmissionDate);
						portalMail.setEmpId(String.valueOf(requestedUser.getEmpId()));
						portalMail.setEmpDesignation(empLevel.getDesignation());
						portalMail.setEmpDivision(requesterDivision.getName());
						if (requesterDivision.getParentId() == 0){
							portalMail.setEmpDepartment(requesterDivision.getName());
						} else{
							portalMail.setEmpDepartment(divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName());
						}
						portalMail.setAllReceipientMailId(IssueTicketModel.fetchOfficialMailIds(handlerIds));
						portalMail.setIssueApprover("All");
						try{
							if (portalMail.getAllReceipientMailId().length == 1){
								if (portalMail.getAllReceipientMailId()[0].startsWith("rmg")) portalMail.setIssueApprover("RMG");
								else{
									String s = (portalMail.getAllReceipientMailId()[0].split("@"))[0];
									portalMail.setIssueApprover((s.indexOf(".") > 0) ? s.substring(0, s.indexOf(".")) : s);
								}
							}
						} catch (Exception e){}
						new MailGenerator().invoker(portalMail);
					}
				}
			}
		} catch (Exception e){
			logger.debug("error while notifying handlers for unresolved requests.....");
			e.printStackTrace();
		}
		/**
		 * send mail to handlers till it_request status moves to resolved
		 */
		try{
			logger.info("Executing method for sending mail to handlers till It_Request status moves to resolved");
			LevelsDao levelsDao = LevelsDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			//remove these 2 line of code later
			ItModel itModel = new ItModel();
			itModel.ItModelDataRepair();
			Object[] sqlParams = null;
			String sql = "REQUESTER_ID != RECEIVER_ID AND STATUS IN('Request Raised','In-progress') or STATUS like 'Docked By %' ORDER BY ESR_MAP_ID,ASSIGN_TO DESC";
			ItRequest unresolvedItReqLst[] = itReqDao.findByDynamicWhere(sql, sqlParams);
			String handlerIds = null;
			ArrayList<Integer> uniqueEsrMapIds = new ArrayList<Integer>();
			if (unresolvedItReqLst != null && unresolvedItReqLst.length > 0){
				for (ItRequest itreq : unresolvedItReqLst){
					if (uniqueEsrMapIds.contains(new Integer(itreq.getEsrMapId()))){
						continue;
					}
					uniqueEsrMapIds.add(itreq.getEsrMapId());
					Users requestedUser = userDao.findByPrimaryKey(itreq.getRequesterId());
					ProfileInfo requestorProfileInfo = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
					EmpSerReqMap empSerReqMap = empserMapDao.findByPrimaryKey(itreq.getEsrMapId());
					Levels empLevel = levelsDao.findByPrimaryKey(requestedUser.getLevelId());
					Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
					if (itreq.getStatus().equalsIgnoreCase("Request Raised")){
						handlerIds = empSerReqMap.getSiblings();
					} else{
						if (itreq.getAssignTo() != 0){
							handlerIds = Integer.toString(itreq.getAssignTo());
						}
					}
					if (handlerIds != null){
						String autoIssueReqId = empSerReqMap.getReqId();
						String mailSubject = "IT Request " + autoIssueReqId + " - Reminder";
						String templateName = MailSettings.NOTIFICATION_TO_HANDLER_ON_ITREQUEST_NOT_RESOLVED;
						String candidateName = requestorProfileInfo.getFirstName();
						String issueSubmissionDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(itreq.getCreateDate());
						String description = "";
						if (itreq.getDescription() != null){
							description = itreq.getDescription();
						}
						portalMail = new PortalMail();
						portalMail.setMailSubject(mailSubject);
						portalMail.setTemplateName(templateName);
						portalMail.setCandidateName(candidateName);
						portalMail.setAutoIssueReqId(autoIssueReqId);
						portalMail.setIssueDescription(description);
						portalMail.setIssueSubmissionDate(issueSubmissionDate);
						portalMail.setEmpId(String.valueOf(requestedUser.getEmpId()));
						portalMail.setEmpDesignation(empLevel.getDesignation());
						portalMail.setEmpDivision(requesterDivision.getName());
						if (requesterDivision.getParentId() == 0){
							portalMail.setEmpDepartment(requesterDivision.getName());
						} else{
							portalMail.setEmpDepartment(divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName());
						}
						portalMail.setAllReceipientMailId(IssueTicketModel.fetchOfficialMailIds(handlerIds));
						portalMail.setIssueApprover("All");
						try{
							if (portalMail.getAllReceipientMailId().length == 1){
								if (portalMail.getAllReceipientMailId()[0].startsWith("rmg")) portalMail.setIssueApprover("RMG");
								else{
									String s = (portalMail.getAllReceipientMailId()[0].split("@"))[0];
									portalMail.setIssueApprover((s.indexOf(".") > 0) ? s.substring(0, s.indexOf(".")) : s);
								}
							}
						} catch (Exception e){}
						new MailGenerator().invoker(portalMail);
					}
				}
				logger.info("--------------NO OF UNRESOLVED IT REQUESTS--------- " + uniqueEsrMapIds.size());
			}
		} catch (Exception e){
			logger.debug("error while notifying handlers for unresolved it_requests.....");
			e.printStackTrace();
		}
		/**
		 * to send mail to requester till he closes the service request which is resolved
		 */
		try{
			RequestedIssues requestedIssuesArr[] = requestedIssueDao.findByDynamicWhere("STATUS='Resolved'", null);
			if (requestedIssuesArr.length > 0){
				for (RequestedIssues reqIssue : requestedIssuesArr){
					Users user = userDao.findByPrimaryKey(reqIssue.getUserId());
					// close the pending requests of seperated users from organization.
					if (user.getStatus() == 2){
						try{
							JDBCUtiility.getInstance().update("UPDATE REQUESTED_ISSUES SET STATUS='Closed' WHERE ESR_MAP_ID=?", new Object[] { reqIssue.getEsrMapId() });
							continue;
						} catch (Exception e){
							logger.error("unable to close the service request (" + reqIssue.getId() + ") " + e.getMessage());
						}
					}
					ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(user.getProfileId());
					String mailSubject = "Service Request " + reqIssue.getAutoReqId() + " Not Closed - Reminder";
					portalMail = new PortalMail();
					portalMail.setMailSubject(mailSubject);
					portalMail.setCandidateName(requesterProfile.getFirstName());
					portalMail.setAutoIssueReqId(reqIssue.getAutoReqId());
					portalMail.setDateOfSubmission(PortalUtility.getdd_MM_yyyy_hh_mm_a(reqIssue.getSubmissionDate()));
					portalMail.setTemplateName(MailSettings.NOTIFICATION_TO_REQUESTER_ON_ISSUE_NOT_CLOSED);
					portalMail.setRecipientMailId(requesterProfile.getOfficalEmailId());
					mailGenerator.invoker(portalMail);
				}
			}
		} catch (Exception e){
			logger.debug("error while notifying requester for closing Service request.....");
			e.printStackTrace();
		}
		/**
		 * to send mail to requester till he closes the IT request which is resolved
		 */
		try{
			ItRequest itRequestArr[] = itReqDao.findByDynamicWhere("REQUESTER_ID=RECEIVER_ID AND STATUS='Resolved'", null);
			if (itRequestArr.length > 0){
				for (ItRequest itReq : itRequestArr){
					Users user = userDao.findByPrimaryKey(itReq.getRequesterId());
					// close the pending requests of seperated users from organization.
					if (user.getStatus() == 2){
						try{
							JDBCUtiility.getInstance().update("UPDATE IT_REQUEST SET STATUS='Closed' WHERE ESR_MAP_ID=?", new Object[] { itReq.getEsrMapId() });
							continue;
						} catch (Exception e){
							logger.error("unable to close the IT request (" + itReq.getId() + ") " + e.getMessage());
						}
					}
					ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(user.getProfileId());
					EmpSerReqMap empSerMap = empserMapDao.findByPrimaryKey(itReq.getEsrMapId());
					String mailSubject = "IT Request " + empSerMap.getReqId() + " Not Closed - Reminder";
					portalMail = new PortalMail();
					portalMail.setMailSubject(mailSubject);
					portalMail.setCandidateName(requesterProfile.getFirstName());
					portalMail.setAutoIssueReqId(empSerMap.getReqId());
					portalMail.setDateOfSubmission(PortalUtility.getdd_MM_yyyy_hh_mm_a(itReq.getCreateDate()));
					portalMail.setTemplateName(MailSettings.NOTIFICATION_TO_REQUESTER_ON_ITREQUEST_NOT_CLOSED);
					portalMail.setRecipientMailId(requesterProfile.getOfficalEmailId());
					mailGenerator.invoker(portalMail);
				}
			}
		} catch (Exception e){
			logger.debug("error while notifying requester for closing IT request.....");
			e.printStackTrace();
		}
	}//end of execute method
}