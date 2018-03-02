package com.dikshatech.portal.timer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.EscPermissionBean;
import com.dikshatech.common.utils.EscalationParser;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.dao.BonusReconciliationDao;
import com.dikshatech.portal.dao.BonusReconciliationReqDao;
import com.dikshatech.portal.dao.CandidateReqDao;
import com.dikshatech.portal.dao.DepPerdiemDao;
import com.dikshatech.portal.dao.DepPerdiemReqDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.EscalationDao;
import com.dikshatech.portal.dao.ExitEmpUsersMapDao;
import com.dikshatech.portal.dao.ExitEmployeeDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.InsuranceHandlerChainReqDao;
import com.dikshatech.portal.dao.IssueHandlerChainReqDao;
import com.dikshatech.portal.dao.ItRequestDao;
import com.dikshatech.portal.dao.LeaveMasterDao;
import com.dikshatech.portal.dao.LoanRequestDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ReimbursementReqDao;
import com.dikshatech.portal.dao.SalaryReconciliationDao;
import com.dikshatech.portal.dao.SalaryReconciliationReqDao;
import com.dikshatech.portal.dao.SodexoReqDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.TimesheetReqDao;
import com.dikshatech.portal.dao.TravelReqDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.BonusReconciliation;
import com.dikshatech.portal.dto.BonusReconciliationReq;
import com.dikshatech.portal.dto.CandidateReq;
import com.dikshatech.portal.dto.DepPerdiem;
import com.dikshatech.portal.dto.DepPerdiemReq;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Escalation;
import com.dikshatech.portal.dto.ExitEmpUsersMap;
import com.dikshatech.portal.dto.ExitEmployee;
import com.dikshatech.portal.dto.Holidays;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.InsuranceHandlerChainReq;
import com.dikshatech.portal.dto.IssueHandlerChainReq;
import com.dikshatech.portal.dto.ItRequest;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.LoanRequest;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ReimbursementReq;
import com.dikshatech.portal.dto.ReimbursementReqPk;
import com.dikshatech.portal.dto.SalaryReconciliation;
import com.dikshatech.portal.dto.SalaryReconciliationReq;
import com.dikshatech.portal.dto.SalaryReconciliationReqPk;
import com.dikshatech.portal.dto.SodexoReq;
import com.dikshatech.portal.dto.TimesheetReq;
import com.dikshatech.portal.dto.TravelReq;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.AppraisalDaoException;
import com.dikshatech.portal.exceptions.BonusReconciliationDaoException;
import com.dikshatech.portal.exceptions.BonusReconciliationReqDaoException;
import com.dikshatech.portal.exceptions.CandidateReqDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemReqDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.EscalationDaoException;
import com.dikshatech.portal.exceptions.ExitEmpUsersMapDaoException;
import com.dikshatech.portal.exceptions.ExitEmployeeDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.InsuranceHandlerChainReqDaoException;
import com.dikshatech.portal.exceptions.IssueHandlerChainReqDaoException;
import com.dikshatech.portal.exceptions.ItRequestDaoException;
import com.dikshatech.portal.exceptions.LeaveMasterDaoException;
import com.dikshatech.portal.exceptions.LoanRequestDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ReferFriendDaoException;
import com.dikshatech.portal.exceptions.ReimbursementReqDaoException;
import com.dikshatech.portal.exceptions.SalaryReconciliationDaoException;
import com.dikshatech.portal.exceptions.SalaryReconciliationReqDaoException;
import com.dikshatech.portal.exceptions.SodexoReqDaoException;
import com.dikshatech.portal.exceptions.StatusDaoException;
import com.dikshatech.portal.exceptions.TimesheetReqDaoException;
import com.dikshatech.portal.exceptions.TravelReqDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.BonusReconciliationDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationReqDaoFactory;
import com.dikshatech.portal.factory.CandidateReqDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReqDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.EscalationDaoFactory;
import com.dikshatech.portal.factory.ExitEmpUsersMapDaoFactory;
import com.dikshatech.portal.factory.ExitEmployeeDaoFactory;
import com.dikshatech.portal.factory.HolidaysDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.InsuranceHandlerChainReqDaoFactory;
import com.dikshatech.portal.factory.IssueHandlerChainReqDaoFactory;
import com.dikshatech.portal.factory.ItRequestDaoFactory;
import com.dikshatech.portal.factory.LeaveMasterDaoFactory;
import com.dikshatech.portal.factory.LoanRequestDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ReimbursementReqDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationReqDaoFactory;
import com.dikshatech.portal.factory.SodexoReqDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.TimesheetReqDaoFactory;
import com.dikshatech.portal.factory.TravelReqDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.BonusReconciliationModel;
import com.dikshatech.portal.models.ItModel;
import com.dikshatech.portal.models.ReconciliationModel;
import com.dikshatech.portal.models.SalaryReconciliationModel;

/**
 * Check for due approvals, handler actions and escalates to the configured escalation chain. 2
 * 
 * @author pawan.kaushal
 */
public class EscalationJob implements Job {

	private final String				HRSPOC						= "HRSPOC";
	private final String				RM							= "RM";
	private final String				PREPEND_ESCALATION_SUBJECT	= "Request escalated: ";
	private final String				PREPEND_ACK_SUBJECT			= "Request forwarded: ";
	private final String				ACTOR_ALL					= "concerned person(s)";
	private final int					ESCALATION_ON				= 1;
	//private final int					ESCALATION_OFF				= 0;
	private final Logger				logger						= LoggerUtil.getLogger(EscalationJob.class);
	private HashMap<String, String[]>	escalationEmailCache		= new HashMap<String, String[]>();

	@Override
	/**
	 * Create escalation for all the pending requests in in-box. Executed based on Quartz trigger.
	 * 
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			logger.setLevel(Level.ALL);
			logger.debug("+-----------Starting Escalation Job-----------+");
			doEscalation();
			if (IsWorkingDay(1, new Date())){
				doEscalationForBonus();
				doEscalationForSalary();
				doEscalationForPerdiem();
				sendEscalMailsForReimbur();
				new ReconciliationModel().escalationforPerdiemHold();
				SalaryReconciliationModel.getInstance().escalationforSalaryHold();
				new BonusReconciliationModel().escalationforBonusHold();
			}
			logger.debug("+-----------Finishing Escalation Job----------+");
		} catch (EscalationDaoException ex){
			logger.error("Unable to insert/update/delete ESCALATION record.");
			logger.error("Error message: " + ex.getMessage());
		} catch (EmpSerReqMapDaoException ex){
			logger.error("Unable to insert/update/delete EMP_SER_REQ_MAP record.");
			logger.error("Error message: " + ex.getMessage());
		} catch (InboxDaoException ex){
			logger.error("Unable to insert/update/delete INBOX record.");
			logger.error("Error message: " + ex.getMessage());
		} catch (ProfileInfoDaoException ex){
			logger.error("Unable to insert/update/delete PROFILE_INFO record.");
			logger.error("Error message: " + ex.getMessage());
		} catch (FileNotFoundException ex){
			logger.error("Unable to access/modify/delete file.");
			logger.error("Error message: " + ex.getMessage());
		} catch (UsersDaoException ex){
			logger.error("Unable to insert/update/delete USERS.");
			logger.error("Error message: " + ex.getMessage());
		} catch (LeaveMasterDaoException ex){
			logger.error("Unable to update request table entries for insert/update/delete LEAVE_MASTER.");
			logger.error("Error message: " + ex.getMessage());
		} catch (TimesheetReqDaoException ex){
			logger.error("Unable to update request table entries for insert/update/delete TIMESHEET_REQ.");
			logger.error("Error message: " + ex.getMessage());
		} catch (TravelReqDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete TRAVEL_REQ.");
			logger.error("Error message: " + e.getMessage());
		} catch (SodexoReqDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete SODEXO_REQ.");
			logger.error("Error message: " + e.getMessage());
		} catch (LoanRequestDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete Loan_Request_REQ.");
			logger.error("Error message: " + e.getMessage());
		} catch (ReimbursementReqDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete SODEXO_REQ.");
			logger.error("Error message: " + e.getMessage());
		} catch (ItRequestDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete IT_REQUESTs.");
			logger.error("Error message: " + e.getMessage());
		} catch (CandidateReqDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete CANDIDATE_REQ.");
			logger.error("Error message: " + e.getMessage());
		} catch (IssueHandlerChainReqDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete ISSUE_HANDLER_CHAIN_REQ.");
			logger.error("Error message: " + e.getMessage());
		} catch (AppraisalDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete APPRAISAL.");
			logger.error("Error message: " + e.getMessage());
		} catch (ReferFriendDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete REFER_FRIEND.");
			logger.error("Error message: " + e.getMessage());
		} catch (ExitEmployeeDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete EXIT_EMPLOYEE.");
			logger.error("Error message: " + e.getMessage());
		} catch (ExitEmpUsersMapDaoException ex){
			logger.error("Unable to insert/update/delete EXIT_EMP_USERS_MAP record.");
			logger.error("Error message: " + ex.getMessage());
		} catch (InsuranceHandlerChainReqDaoException e){
			logger.error("Unable to update request table entries for insert/update/delete INSURANCE_HANDLER_CHAIN_REQ.");
			logger.error("Error message: " + e.getMessage());
		}
	}


	private void sendEscalMailsForReimbur() {
		final String SQL = " SELECT * FROM INBOX I WHERE I.ESR_MAP_ID IN " + " (SELECT E.ID FROM EMP_SER_REQ_MAP E WHERE E.REQ_TYPE_ID IN (SELECT R.ID FROM REQUEST_TYPE R WHERE R.`NAME` = 'REIMBURSEMENT')) " + " AND I.`STATUS` NOT IN ('COMPLETED','REJECTED','REVOKED','APPROVED') " + " AND I.RECEIVER_ID = I.ASSIGNED_TO ";
		InboxDao inboxDao = InboxDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		EscalationDao escalationDao = EscalationDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create();
		ProfileInfo profileInfo = null;
		EmpSerReqMap esr = null;
		try{
			ProcessChain processChain = processChainDao.findWhereProcNameEquals("IN_REIMBURSEMENT_PROC")[0];
			Inbox[] inbox = inboxDao.findByDynamicSelect(SQL, new Object[] {});
			PortalMail pMail = null;
			MailGenerator mGenerator = new MailGenerator();
			for (Inbox eachInbox : inbox){
				
				int userId = eachInbox.getRaisedBy();
				
				int actorId = eachInbox.getAssignedTo();
				String pendingWith = "";
				String levelWith = "";
				if (actorId == getRmHrSpocId(userId, "RM")) pendingWith = "RM";
				else if (actorId == getRmHrSpocId(userId, "HRSPOC")) pendingWith = "HRSPOC";
				else{
					levelWith = String.valueOf(getLevelId(actorId));
				}
				Escalation[] escalations = escalationDao.findByDynamicWhere(" PC_ID = ? AND (USERS_ID = ? OR LEVEL_ID = ?) ", new Object[] { processChain.getId(), pendingWith, levelWith });
				int escalDays = (escalations != null ? (escalations.length == 1 ? escalations[0].getDueDays() : 0) : 0);
				int daysCrossed = getWorkingDaysCount(1, eachInbox.getCreationDatetime(), new Date());
				if (daysCrossed <= escalDays && daysCrossed > 0){
					profileInfo = profileInfoDao.findWhereUserIdEquals(actorId);
					if (profileInfo != null){
						esr = esrDao.findByPrimaryKey(eachInbox.getEsrMapId());
						pMail = new PortalMail();
						pMail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
						pMail.setSerReqID(esr != null ? esr.getReqId() : "");
						pMail.setTotaldays(daysCrossed);
						pMail.setDate(String.valueOf(escalDays));
						pMail.setTemplateName(MailSettings.REIMBURSEMENT_REMAINDER);
						pMail.setMailBody(mGenerator.replaceFields(MailSettings.REIMBURSEMENT_REMAINDER, pMail));
						pMail.setMailSubject("ReimBursement Reminder - " + (esr != null ? esr.getReqId() : ""));
						pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
						pMail.setFromMailId(null);
						mGenerator.invoker(pMail);
					}
				}
				
			}
		} catch (InboxDaoException e){
			logger.error("There is a InboxDaoException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (ProcessChainDaoException e){
			logger.error("There is a ProcessChainDaoException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (EscalationDaoException e){
			logger.error("There is a EscalationDaoException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (UsersDaoException e){
			logger.error("There is a UsersDaoException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (ProfileInfoDaoException e){
			logger.error("There is a ProfileInfoDaoException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (EmpSerReqMapDaoException e){
			logger.error("There is a EmpSerReqMapDaoException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (AddressException e){
			logger.error("There is a AddressException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (UnsupportedEncodingException e){
			logger.error("There is a UnsupportedEncodingException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		} catch (MessagingException e){
			logger.error("There is a MessagingException occured while wuerying the records for escalating reimbursements" + e.getMessage());
		}
	}

	private void doEscalationForPerdiem() {
		final String PERDIEM_RECONCILATION_QUERY = " SELECT * FROM DEP_PERDIEM WHERE `STATUS` != 'Completed' ";
		DepPerdiemDao depPerdiemDao = DepPerdiemDaoFactory.create();
		DepPerdiemReqDao depPerdiemReqDao = DepPerdiemReqDaoFactory.create();
		try{
			DepPerdiem[] depPerdiems = depPerdiemDao.findByDynamicSelect(PERDIEM_RECONCILATION_QUERY, new Object[] {});
			if (depPerdiems != null && depPerdiems.length > 0){
				for (DepPerdiem eachPerdiem : depPerdiems){
					DepPerdiemReq[] depPerdiemReqs = depPerdiemReqDao.findByDynamicWhere("DEP_ID = ? AND SUBMITTED_ON IS NULL", new Object[] { eachPerdiem.getId() });
					for (DepPerdiemReq eachPerdiemReq : depPerdiemReqs){
						if(findSubmittedOn(eachPerdiemReq.getSeqId(),eachPerdiemReq.getDepId())){
						try{
							int daysCrossed = getWorkingDaysCount(1, eachPerdiemReq.getReceivedOn(), new Date());
							Escalation escalation = null;
							if (eachPerdiemReq.getIsEscalated() == 0) escalation = getPerdiemEscalationRecord(eachPerdiemReq.getSeqId(), eachPerdiemReq.getAssignedTo());
							if (escalation != null && daysCrossed == escalation.getDueDays() && eachPerdiemReq.getIsEscalated() == 0){
								escalatePerdiemForTheLevel(daysCrossed, escalation, eachPerdiemReq, eachPerdiem);
							} else{
								sendRemainderPerdiem(daysCrossed, escalation == null ? 0 : escalation.getDueDays(), eachPerdiem, eachPerdiemReq);
							}
						} catch (Exception e){
							logger.error("There is a Exception occured while querying the data for the escalation \n" + eachPerdiemReq, e);
						}
						}
					}
				}
			}
		} catch (DepPerdiemDaoException e){
			logger.error("There is a DepPerdiemDaoException occured while querying the data for the escalation");
		} catch (DepPerdiemReqDaoException e){
			logger.error("There is a DepPerdiemReqDaoException occured while querying the data for the escalation");
		} catch (Exception e){
			logger.error("There is a ProfileInfoDaoException occured while querying the data for the escalation", e);
		}
	}
	
	
	public boolean findSubmittedOn(int seqId,int depId)
	{
		DepPerdiemReqDao depPerdiemReqDao = DepPerdiemReqDaoFactory.create();
		try {
		DepPerdiemReq[] depPerdiemReqs = depPerdiemReqDao.findByDynamicWhere("DEP_ID = ? AND SEQ_ID= ?", new Object[] { depId ,seqId });
		for (DepPerdiemReq eachPerdiem : depPerdiemReqs){
		if(eachPerdiem.getSubmittedOn()!=null){
			return false;
		}
			}
		} catch (DepPerdiemReqDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	

	private Escalation getPerdiemEscalationRecord(int sequenceId, int assignedTo) {
		try{
			ProcessChain[] processChain = ProcessChainDaoFactory.create().findByDynamicWhere(" PROC_NAME = 'IN_DEPUTATION_PROC' ", new Object[] {});
			String[] levelsList = processChain[0].getApprovalChain().split(";");
			int levelEscLvlId = 0;
			switch (sequenceId) {
				case 0:
					String[] handlersLevelsList = processChain[0].getHandler().split("\\|");
					levelEscLvlId = getEscaltionLvelId(handlersLevelsList[1], assignedTo);
					break;
				case 1:
					levelEscLvlId = getEscaltionLvelId(levelsList[0], assignedTo);
					break;
				case 2:
					levelEscLvlId = getEscaltionLvelId(levelsList[1], assignedTo);
					break;
				case 3:
					levelEscLvlId = getEscaltionLvelId(levelsList[2], assignedTo);
					break;
				case 4:
					levelEscLvlId = getEscaltionLvelId(levelsList[3], assignedTo);
					break;
				default:
					break;
			}
			if (levelEscLvlId > 0){
				Escalation[] escalations = EscalationDaoFactory.create().findByDynamicWhere(" PC_ID = ? AND LEVEL_ID = ? ", new Object[] { processChain[0].getId(), levelEscLvlId });
				if (escalations != null && escalations.length > 0) return escalations[0];
			}
		} catch (Exception e){
			logger.error("Unable to get escalator for perdiem reconciliation", e);
		}
		return null;
	}
	
	private Escalation getBonusEscalationRecord(int sequenceId, int assignedTo) {
		try{
			ProcessChain[] processChain = ProcessChainDaoFactory.create().findByDynamicWhere(" PROC_NAME = 'IN_BONUS_RECON' ", new Object[] {});
			String[] levelsList = processChain[0].getApprovalChain().split(";");
			int levelEscLvlId = 0;
			switch (sequenceId) {
				case 0:
					String[] handlersLevelsList = processChain[0].getHandler().split("\\|");
					levelEscLvlId = getEscaltionLvelId(handlersLevelsList[1], assignedTo);
					break;
				case 1:
					levelEscLvlId = getEscaltionLvelId(levelsList[0], assignedTo);
					break;
				case 2:
					levelEscLvlId = getEscaltionLvelId(levelsList[1], assignedTo);
					break;
				case 3:
					levelEscLvlId = getEscaltionLvelId(levelsList[2], assignedTo);
					break;
				case 4:
					levelEscLvlId = getEscaltionLvelId(levelsList[3], assignedTo);
					break;
				default:
					break;
			}
			if (levelEscLvlId > 0){
				Escalation[] escalations = EscalationDaoFactory.create().findByDynamicWhere(" PC_ID = ? AND LEVEL_ID = ? ", new Object[] { processChain[0].getId(), levelEscLvlId });
				if (escalations != null && escalations.length > 0) return escalations[0];
			}
		} catch (Exception e){
			logger.error("Unable to get escalator for perdiem reconciliation", e);
		}
		return null;
	}

	private int getEscaltionLvelId(String levelStr, int assignedTo) throws Exception {
		if (levelStr.contains(",")){
			Users user = UsersDaoFactory.create().findByPrimaryKey(assignedTo);
			String[] temp = levelStr.split(",");
			for (String tempStr : temp){
				if (user.getLevelId() == Integer.parseInt(tempStr)){ return user.getLevelId(); }
			}
		} else return Integer.parseInt(levelStr);
		return 0;
	}

	private void escalatePerdiemForTheLevel(int daysCrossed, Escalation escalation, DepPerdiemReq depPerdiemReq, DepPerdiem depPerdiem) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, InboxDaoException, AddressException, UnsupportedEncodingException, MessagingException, SalaryReconciliationReqDaoException, DepPerdiemReqDaoException {
		String[] escalTo = escalation.getEscalTo().split(",");
		String[] perm = null;
		for (String eachEscal : escalTo){
			try{
				perm = eachEscal.split("\\|");
				if (perm[0].equalsIgnoreCase("false")){
					escalatePerdiemWithNoEdit(perm[1], daysCrossed, depPerdiemReq, depPerdiem);
				} else{
					escalatePerdiemWithEdit(daysCrossed, perm[1], depPerdiemReq, depPerdiem);
				}
			} catch (Exception e){
				logger.error("unable to escalte record", e);
			}
		}
	}

	private void escalatePerdiemWithEdit(int daysCrossed, String escId, DepPerdiemReq depPerdiemReq, DepPerdiem depPerdiem) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, DepPerdiemReqDaoException, AddressException, UnsupportedEncodingException, MessagingException, UsersDaoException {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		DepPerdiemReqDao depPerdiemReqDao = DepPerdiemReqDaoFactory.create();
		PortalMail pMail = new PortalMail();
		MailGenerator mGenerator = new MailGenerator();
		UsersDao usersDao = UsersDaoFactory.create();
		Users escalUser = null;
		if (escId == null) return;
		if (escId.equalsIgnoreCase("RM")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.REPORTING_MGR", new Object[] { depPerdiemReq.getAssignedTo() })[0];
		} else if (escId.equalsIgnoreCase("HRSPOC")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.HR_SPOC", new Object[] { depPerdiemReq.getAssignedTo() })[0];
		} else{
			escalUser = usersDao.findByPrimaryKey(Integer.parseInt(escId));
		}
		ProfileInfo escalProfile = profileInfoDao.findByPrimaryKey(escalUser.getProfileId());;
		ProfileInfo assignedProfile = profileInfoDao.findWhereUserIdEquals(depPerdiemReq.getAssignedTo());
		pMail.setEmployeeName(escalProfile.getFirstName() + " " + escalProfile.getLastName());
		pMail.setSerReqID(getReqId(depPerdiem.getEsrMapId()) + "(" + new ReconciliationModel().getPerdiemPeriodTermStr(depPerdiem) + ")");
		pMail.setApproverName(assignedProfile.getFirstName() + " " + assignedProfile.getLastName());
		pMail.setDaysCrossed(daysCrossed + "");
		pMail.setDateOfAction(PortalUtility.formatDateddMMyyyy(depPerdiemReq.getReceivedOn()));
		pMail.setTemplateName(MailSettings.PERDIEM_ESC);
		String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.PERDIEM_ESC), pMail);
		DepPerdiemReq newDepPerdiemReq = new DepPerdiemReq();
		newDepPerdiemReq.setDepId(depPerdiem.getId());
		newDepPerdiemReq.setSeqId(depPerdiemReq.getSeqId());
		newDepPerdiemReq.setAssignedTo(escalUser.getId());
		newDepPerdiemReq.setReceivedOn(new Date());
		newDepPerdiemReq.setIsEscalated(depPerdiemReq.getId());
		depPerdiemReqDao.insert(newDepPerdiemReq);
		pMail.setMailBody(msg);
		pMail.setMailSubject("Per-Diem Report Escalation for the period " + pMail.getSerReqID());
		pMail.setFromMailId(null);
		pMail.setRecipientMailId(escalProfile.getOfficalEmailId());
		pMail.setAllReceipientcCMailId(new String[] { assignedProfile.getOfficalEmailId() });
		mGenerator.invoker(pMail);
	}

	private void escalatePerdiemWithNoEdit(String escId, int daysCrossed, DepPerdiemReq depPerdiemReq, DepPerdiem depPerdiem) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, InboxDaoException, AddressException, UnsupportedEncodingException, MessagingException, UsersDaoException {
		InboxDao inboxDao = InboxDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(depPerdiemReq.getAssignedTo());
		UsersDao usersDao = UsersDaoFactory.create();
		Users escalUser = null;
		if (escId == null) return;
		if (escId.equalsIgnoreCase("RM")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.REPORTING_MGR", new Object[] { depPerdiemReq.getAssignedTo() })[0];
		} else if (escId.equalsIgnoreCase("HRSPOC")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.HR_SPOC", new Object[] { depPerdiemReq.getAssignedTo() })[0];
		} else{
			escalUser = usersDao.findByPrimaryKey(Integer.parseInt(escId));
		}
		ProfileInfo escalProfile = profileInfoDao.findByPrimaryKey(escalUser.getProfileId());;
		Inbox inbox = new Inbox();
		PortalMail pMail = new PortalMail();
		MailGenerator mGenerator = new MailGenerator();
		pMail.setSerReqID(getReqId(depPerdiem.getEsrMapId()) + "(" + new ReconciliationModel().getPerdiemPeriodTermStr(depPerdiem) + ")");
		if (profileInfo != null) pMail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
		if (escalProfile != null) pMail.setEmployeeName(escalProfile.getFirstName() + " " + escalProfile.getLastName());
		pMail.setDaysCrossed(daysCrossed + "");
		pMail.setDateOfAction(PortalUtility.getdd_MM_yyyy(depPerdiemReq.getReceivedOn()));
		pMail.setTemplateName(MailSettings.PERDIEM_ESCAL_NOTIFICATION);
		String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.PERDIEM_ESCAL_NOTIFICATION), pMail);
		inbox.setReceiverId(escalUser.getId());
		inbox.setEsrMapId(depPerdiem.getEsrMapId());
		inbox.setSubject("Escalated Request Notification - " + pMail.getSerReqID());
		inbox.setAssignedTo(0);
		inbox.setRaisedBy(0);
		inbox.setCreationDatetime(new Date());
		inbox.setStatus(depPerdiem.getStatus());
		inbox.setCategory("PERDIEM_REPORT");
		inbox.setIsDeleted(0);
		inbox.setIsRead(0);
		inbox.setMessageBody(msg);
		inbox.setIsEscalated(2);
		inboxDao.insert(inbox);
		pMail.setMailSubject("Per-Diem Report Escalation for the period " + pMail.getSerReqID());
		pMail.setRecipientMailId(escalProfile.getOfficalEmailId());
		mGenerator.invoker(pMail);
	}

	private void sendRemainderPerdiem(int daysCrossed, int daysConfigured, DepPerdiem depPerdiem, DepPerdiemReq depPerdiemReq) {
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		PortalMail pMail = new PortalMail();
		MailGenerator generator = new MailGenerator();
		try{
			if (depPerdiemReq != null){
				ProfileInfo assignedTo = profileDao.findWhereUserIdEquals(depPerdiemReq.getAssignedTo());
				pMail.setApproverName(assignedTo.getFirstName() + " " + assignedTo.getLastName());
				pMail.setSerReqID(getReqId(depPerdiem.getEsrMapId()) + "(" + new ReconciliationModel().getPerdiemPeriodTermStr(depPerdiem) + ")");
				pMail.setDaysCrossed(daysCrossed + "");
				pMail.setTemplateName(MailSettings.PERDIEM_REMAINDER);
				pMail.setMailSubject("Reminder Per-Diem Reconciliation Report Request Pending");
				pMail.setRecipientMailId(assignedTo.getOfficalEmailId());
				if (daysConfigured > daysCrossed && depPerdiemReq.getIsEscalated() == 0) pMail.setComment(" The escalation chain configured for the action is " + daysConfigured + " days. Please take action inorder to avoid escalations");
				else pMail.setComment("");
				generator.invoker(pMail);
			}
		} catch (ProfileInfoDaoException e){
			logger.error("There is a ProfileInfoDaoException occured while querying the data for the escalation");
		} catch (EmpSerReqMapDaoException e){
			logger.error("There is a EmpSerReqMapDaoException occured while querying the data for the escalation");
		} catch (FileNotFoundException e){
			logger.error("There is no SAL_RECON_Reminder html template found");
		} catch (AddressException e){
			logger.error("There is a AddressException occured while querying the data for the escalation");
		} catch (UnsupportedEncodingException e){
			logger.error("There is a UnsupportedEncodingException occured while querying the data for the escalation");
		} catch (MessagingException e){
			logger.error("There is a MessagingException occured while querying the data for the escalation");
		}
	}

	private void doEscalationForSalary() {
		final String SALARY_RECONCILATION_QUERY = "SELECT * FROM SALARY_RECONCILIATION SR " + " WHERE SR.`STATUS` NOT IN (SELECT ID FROM `STATUS` WHERE `STATUS` IN ('" + Status.COMPLETED + "')) ";
		SalaryReconciliationDao salaryReconciliationDao = SalaryReconciliationDaoFactory.create();
		SalaryReconciliationReqDao salaryReconciliationReqDao = SalaryReconciliationReqDaoFactory.create();
		try{
			SalaryReconciliation[] salaryReconciliations = salaryReconciliationDao.findByDynamicSelect(SALARY_RECONCILATION_QUERY, new Object[] {});
			if (salaryReconciliations != null && salaryReconciliations.length > 0){
				for (SalaryReconciliation eachReconciliation : salaryReconciliations){
					SalaryReconciliationReq[] salaryReconciliationReqs = salaryReconciliationReqDao.findByDynamicSelect(" SELECT * FROM SALARY_RECONCILIATION_REQ WHERE SR_ID = ? AND ACTION_BY=0 ", new Object[] { eachReconciliation.getId() });
					for (SalaryReconciliationReq eachSalaryReconciliationReq : salaryReconciliationReqs){
						int daysCrossed = getWorkingDaysCount(1, eachSalaryReconciliationReq.getCreatedOn(), new Date());
						Escalation escalation = null;
						if (eachSalaryReconciliationReq.getEscalatedFrom() == null || eachSalaryReconciliationReq.getEscalatedFrom().equalsIgnoreCase("0")) escalation = getSalaryEscalationRecord(eachSalaryReconciliationReq.getLevel(), eachSalaryReconciliationReq.getAssignedTo());
						if (escalation != null && daysCrossed == escalation.getDueDays()){
							escalateForTheLevel(daysCrossed, escalation, eachSalaryReconciliationReq, eachReconciliation);
						} else if (daysCrossed > 0){
							sendRemainderMail(daysCrossed, escalation == null ? 0 : escalation.getDueDays(), eachReconciliation, eachSalaryReconciliationReq);
						} else{
							logger.debug("The record with Id : " + eachReconciliation.getId() + " has been crossed the escalation matrix");
						}
					}
				}
			}
		} catch (SalaryReconciliationDaoException e){
			logger.error("There is a SalaryReconciliationDaoException occured while querying the data for the escalation");
		} catch (SalaryReconciliationReqDaoException e){
			logger.error("There is a SalaryReconciliationReqDaoException occured while querying the data for the escalation");
		} catch (Exception e){
			logger.error("There is a ProfileInfoDaoException occured while querying the data for the escalation");
		}
	}

	private Escalation getSalaryEscalationRecord(int levelId, int assignedTo) {
		EscalationDao escalationDao = EscalationDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		try{
			ProcessChain[] processChain = processChainDao.findByDynamicWhere(" PROC_NAME = 'IN_MSR' ", new Object[] {});
			if (processChain != null && processChain.length == 1){
				String[] levelsList = processChain[0].getApprovalChain().split(";");
				String[] handlersLevelsList = processChain[0].getHandler().split("\\|");
				String LevelIds = "";
				if (levelId == 1) LevelIds = getEscaltionLvelId(levelsList[0], assignedTo) + "";
				else if (levelId == 2 || levelId == 4) LevelIds = getEscaltionLvelId(levelsList[1], assignedTo) + "";
				else if (levelId == 3) LevelIds = getEscaltionLvelId(handlersLevelsList[1], assignedTo) + "";
				if (LevelIds != null && LevelIds.length() > 0){
					Escalation[] escalations = escalationDao.findByDynamicWhere(" PC_ID = ? AND LEVEL_ID IN (" + LevelIds + ") ", new Object[] { processChain[0].getId() });
					if (escalations != null && escalations.length == 1){ return escalations[0]; }
				}
			}
		} catch (Exception e){
			logger.error("unable to get escaltion record for salary reconciliation", e);
		}
		return null;
	}

	private void sendRemainderMail(int daysCrossed, int daysConfigured, SalaryReconciliation salaryReconciliation, SalaryReconciliationReq salaryReconciliationReqs) {
		//SalaryReconciliationReqDao salaryReconciliationReqDao = SalaryReconciliationReqDaoFactory.create();
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		PortalMail pMail = new PortalMail();
		MailGenerator generator = new MailGenerator();
		try{
			//SalaryReconciliationReq[] salaryReconciliationReqs = salaryReconciliationReqDao.findWhereSrIdEquals(salaryReconciliation.getId());
			if (salaryReconciliationReqs != null)/*&& salaryReconciliationReqs.length == 1)*/{
				ProfileInfo assignedTo = profileDao.findWhereUserIdEquals(salaryReconciliationReqs/*[0]*/.getAssignedTo());
				pMail.setApproverName(assignedTo.getFirstName() + " " + assignedTo.getLastName());
				pMail.setSerReqID(getReqId(salaryReconciliation.getEsrMapId()) + "( " + PortalUtility.returnMonth(salaryReconciliation.getMonth()) + ", " + salaryReconciliation.getYear() + ")");
				pMail.setDaysCrossed(daysCrossed + "");
				pMail.setTemplateName(MailSettings.SAL_RECON_REMAINDER);
				if (daysConfigured > daysCrossed && (salaryReconciliationReqs.getEscalatedFrom() == null || salaryReconciliationReqs.getEscalatedFrom().equalsIgnoreCase("0"))) pMail.setComment("The escalation chain configured for the action is " + daysConfigured + " day(s). Please take action inorder to avoid escalations.");
				else pMail.setComment("");
				pMail.setMailBody(generator.replaceFields(generator.getMailTemplate(MailSettings.SAL_RECON_REMAINDER), pMail));
				pMail.setMailSubject("Reminder Salary Reconciliation Request Pending");
				pMail.setRecipientMailId(assignedTo.getOfficalEmailId());
				pMail.setFromMailId(null);
				generator.invoker(pMail);
			}
		} catch (ProfileInfoDaoException e){
			logger.error("There is a ProfileInfoDaoException occured while querying the data for the escalation");
		} catch (EmpSerReqMapDaoException e){
			logger.error("There is a EmpSerReqMapDaoException occured while querying the data for the escalation");
		} catch (FileNotFoundException e){
			logger.error("There is no SAL_RECON_REMAINDER html template found");
		} catch (AddressException e){
			logger.error("There is a AddressException occured while querying the data for the escalation");
		} catch (UnsupportedEncodingException e){
			logger.error("There is a UnsupportedEncodingException occured while querying the data for the escalation");
		} catch (MessagingException e){
			logger.error("There is a MessagingException occured while querying the data for the escalation");
		}
	}

	private String getReqId(Integer esrMapId) throws EmpSerReqMapDaoException {
		EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create();
		return esrDao.findByPrimaryKey(esrMapId).getReqId();
	}

	private void escalateForTheLevel(int daysCrossed, Escalation escalation, SalaryReconciliationReq salaryReconciliationReq, SalaryReconciliation salReconciliation) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, InboxDaoException, AddressException, UnsupportedEncodingException, MessagingException, SalaryReconciliationReqDaoException {
		String[] escalTo = escalation.getEscalTo().split(",");
		String[] perm = null;
		for (String eachEscal : escalTo){
			try{
				perm = eachEscal.split("\\|");
				if (perm[0].equalsIgnoreCase("false")){
					escalateWithNoEdit(perm[1], daysCrossed, salaryReconciliationReq, salReconciliation);
				} else{
					escalateWithEdit(daysCrossed, perm[1], salaryReconciliationReq, salReconciliation);
				}
			} catch (Exception e){
				logger.error("unable to escalte salary reconciliation request\n" + salaryReconciliationReq, e);
			}
		}
	}

	private void escalateWithEdit(int daysCrossed, String escId, SalaryReconciliationReq salaryReconciliationReq, SalaryReconciliation salaryReconciliation) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, SalaryReconciliationReqDaoException, AddressException, UnsupportedEncodingException, MessagingException, UsersDaoException {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		SalaryReconciliationReqDao salaryReconciliationReqDao = SalaryReconciliationReqDaoFactory.create();
		PortalMail pMail = new PortalMail();
		MailGenerator mGenerator = new MailGenerator();
		UsersDao usersDao = UsersDaoFactory.create();
		Users escalUser = null;
		if (escId == null) return;
		if (escId.equalsIgnoreCase("RM")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.REPORTING_MGR", new Object[] { salaryReconciliationReq.getAssignedTo() })[0];
		} else if (escId.equalsIgnoreCase("HRSPOC")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.HR_SPOC", new Object[] { salaryReconciliationReq.getAssignedTo() })[0];
		} else{
			escalUser = usersDao.findByPrimaryKey(Integer.parseInt(escId));
		}
		ProfileInfo escalProfile = profileInfoDao.findByPrimaryKey(escalUser.getProfileId());;
		ProfileInfo assignedProfile = profileInfoDao.findWhereUserIdEquals(salaryReconciliationReq.getAssignedTo());
		pMail.setEmployeeName(escalProfile.getFirstName() + " " + escalProfile.getLastName());
		pMail.setSerReqID(getReqId(salaryReconciliation.getEsrMapId()) + "( " + PortalUtility.returnMonth(salaryReconciliation.getMonth()) + ", " + salaryReconciliation.getYear() + ")");
		pMail.setApproverName(assignedProfile.getFirstName() + " " + assignedProfile.getLastName());
		pMail.setDaysCrossed(daysCrossed + "");
		pMail.setDateOfAction(PortalUtility.formatDateddMMyyyy(salaryReconciliation.getCreatedOn()));
		pMail.setTemplateName(MailSettings.SAL_RECON_ESC);
		String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.SAL_RECON_ESC), pMail);
		SalaryReconciliationReq newSalaryReconReq = new SalaryReconciliationReq();
		newSalaryReconReq.setSrId(salaryReconciliationReq.getSrId());
		newSalaryReconReq.setAssignedTo(escalUser.getId());
		newSalaryReconReq.setLevel(salaryReconciliationReq.getLevel());
		newSalaryReconReq.setComments(salaryReconciliationReq.getComments());
		newSalaryReconReq.setCreatedOn(new Date());
		newSalaryReconReq.setEscalatedFrom("0");
		SalaryReconciliationReqPk pk = salaryReconciliationReqDao.insert(newSalaryReconReq);
		String escalFrom = salaryReconciliationReq.getEscalatedFrom();
		if (escalFrom == null){
			escalFrom = salaryReconciliationReq.getId() + "," + pk.getId();
		} else if (escalFrom.equalsIgnoreCase("0")){
			escalFrom = salaryReconciliationReq.getId() + "," + pk.getId();
		} else if (escalFrom.length() > 0){
			escalFrom += "," + pk.getId();
		}
		newSalaryReconReq.setEscalatedFrom(escalFrom);
		salaryReconciliationReq.setEscalatedFrom(escalFrom);
		salaryReconciliationReqDao.update(pk, newSalaryReconReq);
		salaryReconciliationReqDao.update(new SalaryReconciliationReqPk(salaryReconciliationReq.getId()), salaryReconciliationReq);
		String reqId = getReqId(salaryReconciliation.getEsrMapId());
		pMail.setMailBody(msg);
		pMail.setMailSubject("Salary Reconciliation Request Escalation for " + reqId);
		pMail.setFromMailId(null);
		pMail.setRecipientMailId(escalProfile.getOfficalEmailId());
		pMail.setAllReceipientcCMailId(new String[] { assignedProfile.getOfficalEmailId() });
		mGenerator.invoker(pMail);
	}

	private void escalateWithNoEdit(String escId, int daysCrossed, SalaryReconciliationReq salaryReconciliationReq, SalaryReconciliation salReconciliation) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, InboxDaoException, AddressException, UnsupportedEncodingException, MessagingException, UsersDaoException {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		ProfileInfo profileInfo = ProfileInfoDaoFactory.create().findWhereUserIdEquals(salaryReconciliationReq.getAssignedTo());
		UsersDao usersDao = UsersDaoFactory.create();
		Users escalUser = null;
		if (escId == null) return;
		if (escId.equalsIgnoreCase("RM")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.REPORTING_MGR", new Object[] { salaryReconciliationReq.getAssignedTo() })[0];
		} else if (escId.equalsIgnoreCase("HRSPOC")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.HR_SPOC", new Object[] { salaryReconciliationReq.getAssignedTo() })[0];
		} else{
			escalUser = usersDao.findByPrimaryKey(Integer.parseInt(escId));
		}
		ProfileInfo escalProfile = profileInfoDao.findByPrimaryKey(escalUser.getProfileId());;
		Inbox inbox = new Inbox();
		PortalMail pMail = new PortalMail();
		MailGenerator mGenerator = new MailGenerator();
		String reqId = getReqId(salReconciliation.getEsrMapId());
		pMail.setSerReqID(reqId + "( " + PortalUtility.returnMonth(salReconciliation.getMonth()) + ", " + salReconciliation.getYear() + ")");
		if (profileInfo != null) pMail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
		if (escalProfile != null) pMail.setEmployeeName(escalProfile.getFirstName() + " " + escalProfile.getLastName());
		pMail.setDaysCrossed(daysCrossed + "");
		pMail.setDateOfAction(PortalUtility.formatDateddMMyyyy(salReconciliation.getCreatedOn()));
		pMail.setTemplateName(MailSettings.SAL_RECON_ESCAL_NOTIFICATION);
		String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.SAL_RECON_ESCAL_NOTIFICATION), pMail);
		inbox.setReceiverId(escalUser.getId());
		inbox.setEsrMapId(salReconciliation.getEsrMapId());
		inbox.setSubject("Escalated Request Notification - " + reqId);
		inbox.setAssignedTo(0);
		inbox.setRaisedBy(0);
		inbox.setCreationDatetime(new Date());
		inbox.setStatus(getStatus(salReconciliation.getStatus()));
		inbox.setCategory("Salary Reconciliation");
		inbox.setIsDeleted(0);
		inbox.setIsRead(0);
		inbox.setMessageBody(msg);
		inbox.setIsEscalated(2);
		inboxDao.insert(inbox);
		pMail.setMailBody(msg);
		pMail.setMailSubject("Escalated Request Notification - " + reqId);
		pMail.setRecipientMailId(escalProfile.getOfficalEmailId());
		pMail.setFromMailId(null);
		mGenerator.invoker(pMail);
	}

	private String getStatus(Integer status) {
		StatusDao statusDao = StatusDaoFactory.create();
		try{
			return statusDao.findByPrimaryKey(status).getStatus();
		} catch (StatusDaoException e){
			logger.error("There is a StatusDaoException occured while quering the status in Db");
			return null;
		}
	}

	/**
	 * Poll in-box to get pending actions for which escalation are defined and do escalation processing.
	 * 
	 * @throws UsersDaoException
	 * @throws TimesheetReqDaoException
	 * @throws LeaveMasterDaoException
	 * @throws InsuranceHandlerChainReqDaoException
	 * @throws ExitEmployeeDaoException
	 * @throws ReferFriendDaoException
	 * @throws AppraisalDaoException
	 * @throws IssueHandlerChainReqDaoException
	 * @throws CandidateReqDaoException
	 * @throws ItRequestDaoException
	 * @throws ReimbursementReqDaoException
	 * @throws LoanRequestDaoException
	 * @throws SodexoReqDaoException
	 * @throws TravelReqDaoException
	 * @throws ExitEmpUsersMapDaoException
	 * @throws SQLException
	 */
	protected void doEscalation() throws ProfileInfoDaoException, EscalationDaoException, InboxDaoException, FileNotFoundException, EmpSerReqMapDaoException, UsersDaoException, LeaveMasterDaoException, TimesheetReqDaoException, TravelReqDaoException, SodexoReqDaoException, LoanRequestDaoException, ReimbursementReqDaoException, ItRequestDaoException, CandidateReqDaoException, IssueHandlerChainReqDaoException, AppraisalDaoException, ReferFriendDaoException, ExitEmployeeDaoException,
			InsuranceHandlerChainReqDaoException, ExitEmpUsersMapDaoException {
		final String QUERY_ACTOR_INBOX = "SELECT I.*,REQ.PROCESSCHAIN_ID, REQ.REQUESTOR_ID,REQ.SUB_DATE " + "FROM INBOX I JOIN EMP_SER_REQ_MAP REQ ON I.ESR_MAP_ID = REQ.ID " + "WHERE I.ASSIGNED_TO = I.RECEIVER_ID AND REQ.PROCESSCHAIN_ID IN (SELECT DISTINCT(PC_ID) FROM ESCALATION) AND IS_DELETED!=1" + " AND I.IS_ESCALATED=0 AND I.STATUS != 'REVOKED' ORDER BY ESR_MAP_ID DESC";
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try{
			connection = ResourceManager.getConnection();
			stmt = connection.prepareStatement(QUERY_ACTOR_INBOX);
			result = stmt.executeQuery();
			EscalationDao escProvider = EscalationDaoFactory.create();
			while (result.next()){
				int inboxId = result.getInt("I.ID");
				//				Kept this code for debugging
				if (inboxId == 2149){
					logger.debug(inboxId + "test");
				}
				int actorId = result.getInt("I.ASSIGNED_TO");
				int levelId = getLevelId(actorId);
				int requestorId = result.getInt("REQ.REQUESTOR_ID");
				int requestorHrSpocId = getRmHrSpocId(requestorId, HRSPOC);
				int requestorRmId = getRmHrSpocId(requestorId, RM);
				int processChainId = result.getInt("REQ.PROCESSCHAIN_ID");
				String escUserId = "NA";
				if (requestorHrSpocId == actorId){
					escUserId = HRSPOC;
				} else if (requestorRmId == actorId){
					escUserId = RM;
				} else{
					escUserId = String.valueOf(actorId);
				}
				final String WHERE_CLAUSE = "PC_ID=? AND (LEVEL_ID=? OR USERS_ID=?)";
				final Object[] WHERE_PARAM = new Object[] { processChainId, levelId, escUserId };
				Escalation[] esc = escProvider.findByDynamicWhere(WHERE_CLAUSE, WHERE_PARAM);
				for (int escIdx = 0; escIdx < esc.length; escIdx++){
					processEscalation(esc[escIdx], inboxId, requestorId, requestorHrSpocId, requestorRmId);
				}
			}
		} catch (SQLException ex){
			logger.error("Unable to process escalation transactions.");
		} finally{
			if (connection != null){
				ResourceManager.close(stmt);
				ResourceManager.close(result);
				ResourceManager.close(connection);
			}
		}
	}

	/**
	 * Creates in-box/dash-board entries for escalation and send email notification for pending, overdue actions by approvers/handlers.
	 * 
	 * @param escalation
	 * @param inboxId
	 * @param requesterId
	 * @param requestorHrSpocId
	 * @param requestorRmId
	 * @throws InboxDaoException
	 * @throws ProfileInfoDaoException
	 * @throws FileNotFoundException
	 * @throws EmpSerReqMapDaoException
	 * @throws LeaveMasterDaoException
	 * @throws TimesheetReqDaoException
	 * @throws InsuranceHandlerChainReqDaoException
	 * @throws ExitEmployeeDaoException
	 * @throws ReferFriendDaoException
	 * @throws AppraisalDaoException
	 * @throws IssueHandlerChainReqDaoException
	 * @throws CandidateReqDaoException
	 * @throws ItRequestDaoException
	 * @throws ReimbursementReqDaoException
	 * @throws LoanRequestDaoException
	 * @throws SodexoReqDaoException
	 * @throws TravelReqDaoException
	 * @throws ExitEmpUsersMapDaoException
	 */
	protected void processEscalation(Escalation escalation, int inboxId, int requesterId, int requestorHrSpocId, int requestorRmId) throws InboxDaoException, ProfileInfoDaoException, FileNotFoundException, EmpSerReqMapDaoException, LeaveMasterDaoException, TimesheetReqDaoException, TravelReqDaoException, SodexoReqDaoException, LoanRequestDaoException, ReimbursementReqDaoException, ItRequestDaoException, CandidateReqDaoException, IssueHandlerChainReqDaoException, AppraisalDaoException,
			ReferFriendDaoException, ExitEmployeeDaoException, InsuranceHandlerChainReqDaoException, ExitEmpUsersMapDaoException {
		logger.debug("Begin escalation processing: INBOX.ID: " + inboxId + ", ESCALATION.ID: " + escalation.getId() + "");
		final String INBOX_ENTRY_EXISTS_QUERY = "select * from INBOX where RECEIVER_ID=? AND ESR_MAP_ID=? AND IS_DELETED!=1 AND SUBJECT=?";
		Connection connEsc = null;
		//Enabling transaction
		try{
			connEsc = ResourceManager.getConnection();
			connEsc.setAutoCommit(false);
		} catch (SQLException ex){
			logger.error("Unable to obtain connection while processing escalation transaction.");
			logger.error("Error message: " + ex.getMessage());
			if (connEsc != null){
				ResourceManager.close(connEsc);
			}
		}
		//InboxDao inboxProvider=InboxDaoFactory.create(connEsc);
		//Deferring transaction point
		InboxDao inboxReadProvider = InboxDaoFactory.create();
		Inbox inbox = inboxReadProvider.findByPrimaryKey(inboxId);
		Date creationDate = inbox.getCreationDatetime();
		int dueDays = escalation.getDueDays();
		if (getWorkingDaysCount(1, inbox.getCreationDatetime(), new Date()) == escalation.getDueDays()){
			logger.debug("Overdue true: INBOX.ID: " + inboxId + ", ESCALATION.ID: " + escalation.getId() + "");
			//Coded for decreasing transaction scope
			boolean flagAckInsert, flagAckUpdate;
			Inbox ackReq = inboxReadProvider.findByPrimaryKey(inboxId);
			flagAckInsert = flagAckUpdate = false;
			//Updates request to be escalated in INBOX table in field IS_ESCALATED with value 1
			Inbox existingReq = inboxReadProvider.findByPrimaryKey(inboxId);
			existingReq.setIsEscalated(ESCALATION_ON);
			//Moved this statement for decreasing transaction scope
			//inboxReadProvider.update(new InboxPk(inboxId), existingReq);
			Date today = new Date();
			inbox.setCreationDatetime(today);
			ackReq.setCreationDatetime(today);
			String reqSubject = inbox.getSubject();
			String reqMessageBody = inbox.getMessageBody();
			//int requesterId=inbox.getRaisedBy();
			int actorId = inbox.getAssignedTo();
			int ackEsrMapId = inbox.getEsrMapId();
			try{
				String ackSubject = PREPEND_ACK_SUBJECT + reqSubject;
				//Create in-box entry for requester- notification
				final String WHERE_ACK = "ESR_MAP_ID=? AND RECEIVER_ID=? AND SUBJECT=?";
				Inbox[] existingAck = inboxReadProvider.findByDynamicWhere(WHERE_ACK, new Object[] { ackEsrMapId, requesterId, ackSubject });
				PortalMail escAckPortalEmail = null;
				if (existingAck.length > 0){
					ackReq = existingAck[0];
					if (ackReq.getMessageBody().indexOf(ACTOR_ALL) == -1){
						escAckPortalEmail = getEscAckMail(requesterId, 0, creationDate, ackSubject);
						String escAckEmailMessage = escAckPortalEmail.getMailBody();
						ackReq.setMessageBody(escAckEmailMessage);
						flagAckUpdate = true;
						//Moved this statement for decreasing transaction scope
						//inboxReadProvider.update(new InboxPk(ackReq.getId()), ackReq);
					} else{
						ackReq.setIsRead(0);
						ackReq.setCreationDatetime(creationDate);
						flagAckUpdate = true;
						logger.info("Escalation acknowledgement exists. Upading CREATION_DATETIME and IS_READ for ESR_MAP_ID: " + ackEsrMapId + ", RECEIVER_ID: " + requesterId);
					}
				} else{
					ackReq.setSubject(ackSubject);
					escAckPortalEmail = getEscAckMail(requesterId, actorId, creationDate, ackSubject);
					String escAckEmailMessage = escAckPortalEmail.getMailBody();
					ackReq.setIsRead(0);
					ackReq.setIsEscalated(ESCALATION_ON);
					//Setting it to the in-box entry
					ackReq.setMessageBody(escAckEmailMessage);
					ackReq.setReceiverId(requesterId);
					ackReq.setAssignedTo(0);
					ackReq.setId(0);
					flagAckInsert = true;
					//Moved this statement for decreasing transaction scope
					//inboxReadProvider.insert(ackReq);
				}
				//Create in-box entry for escalation- notification and/or my-task
				inbox.setAssignedTo(actorId);
				String escSubject = PREPEND_ESCALATION_SUBJECT + reqSubject;
				inbox.setSubject(escSubject);
				PortalMail escPortalEmail = getEscMail(actorId, creationDate, dueDays, reqMessageBody, escSubject);
				String escEmailMessage = escPortalEmail.getMailBody();
				inbox.setIsRead(0);
				inbox.setIsEscalated(2);
				//Setting it to the in-box entry
				inbox.setMessageBody(escEmailMessage);
				EscalationParser escParser = new EscalationParser();
				List<EscPermissionBean> escActorList = escParser.getEscalationPermissionsForUsers(escalation.getEscalTo());
				//To and Cc email addresses are stored in there variables for escalation; requester and actual approver.
				String escIdCsv = "";
				String reqAppIdCsv = String.valueOf(requesterId);//actorId +","+requestorId;
				//Obtain EMP_SER_REQ_MAP record to update siblings column
				int esrMapId = inbox.getEsrMapId();
				List<String> newSiblingsIds = new ArrayList<String>();
				//Database transaction begins 
				InboxDao inboxWriteProvider = null;
				if (connEsc != null) inboxWriteProvider = InboxDaoFactory.create(connEsc);
				else inboxWriteProvider = InboxDaoFactory.create();
				//Creating escalation entries for escalation
				for (EscPermissionBean escActor : escActorList){
					int escActorId = 0;
					String escActorUserId = escActor.getuserId();
					if (escActorUserId.equals(HRSPOC)) escActorId = requestorHrSpocId;
					else if (escActorUserId.equals(RM)) escActorId = requestorRmId;
					else escActorId = Integer.parseInt(escActorUserId);
					//Make an new inbox entry for escalator if not exists
					Inbox[] duplicateInbox = inboxReadProvider.findByDynamicSelect(INBOX_ENTRY_EXISTS_QUERY, new Object[] { escActorId, esrMapId, escSubject });
					if (duplicateInbox.length < 1){
						inbox.setReceiverId(escActorId);
						if (escActor.isPermission()){
							inbox.setAssignedTo(escActorId);
						}
						//Making entries for my tasks and notification Line: 184-197
						inbox.setId(0);
						inboxWriteProvider.insert(inbox);
						if (escActor.isPermission()){
							newSiblingsIds.add(String.valueOf(escActorId));
							updateRequestTables(connEsc, esrMapId, escActorId, actorId);
						}
						//Reset the ASSIGNED_TO to actual approver
						//to ensure escalation without permission to execute
						//see actual approver reference
						inbox.setAssignedTo(actorId);
						escIdCsv += escActorId + ",";
					} else{
						//Code updates already escalated entry approver name to concerned person(s)
						Inbox escInbox = duplicateInbox[0];
						if (escInbox.getMessageBody().indexOf(ACTOR_ALL) == -1){
							escPortalEmail = getEscMail(0, creationDate, dueDays, reqMessageBody, escSubject);
							escEmailMessage = escPortalEmail.getMailBody();
							//Setting it to the in-box entry
							escInbox.setMessageBody(escEmailMessage);
							inboxWriteProvider.update(new InboxPk(escInbox.getId()), escInbox);
						} else{
							logger.info("Escalation inbox already updated for ESR_MAP_ID: " + esrMapId);
						}
						logger.debug("Escalation inbox already created for ESR_MAP_ID: " + esrMapId + ", RECEIVER_ID: " + escActorId);
					}
				}
				//Update siblings in EMP_SER_REQ_MAP table
				if (newSiblingsIds.size() > 0){
					EmpSerReqMapDao esrReadProvider = EmpSerReqMapDaoFactory.create();
					EmpSerReqMap esr = esrReadProvider.findByPrimaryKey(esrMapId);
					String tempSiblings = esr.getSiblings();
					//Updating existing siblings
					if (tempSiblings != null && tempSiblings.length() > 0){
						String appendIds = "";
						String[] tempSiblingsArray = tempSiblings.split(",");
						List<String> tempSiblingsList = new ArrayList<String>();
						for (String element : tempSiblingsArray)
							tempSiblingsList.add(element);
						for (String element : newSiblingsIds){
							if (tempSiblingsList.contains(element) == false){
								appendIds += element + ",";
							} else{
								logger.debug(element + " is already in siblings skipping.");
							}
						}
						if (tempSiblings.endsWith(",")) tempSiblings += appendIds;
						else if (appendIds.length() > 0){
							appendIds = appendIds.substring(0, appendIds.lastIndexOf(","));
							tempSiblings += "," + appendIds;
						} else logger.debug("Unable to process siblings. EXISTING SIBLINGS: " + tempSiblings + ". APPENDSIBLINGS: " + appendIds);
						esr.setSiblings(tempSiblings);
					} else //Creating new siblings
					{
						String newIds = "";
						for (String element : newSiblingsIds){
							newIds += element + ",";
						}
						newIds = newIds.substring(0, newIds.lastIndexOf(","));
						esr.setSiblings(newIds);
					}
					//Updates siblings as a part of transaction
					EmpSerReqMapDao esrWriteProvider = EmpSerReqMapDaoFactory.create(connEsc);
					esrWriteProvider.update(new EmpSerReqMapPk(esr.getId()), esr);
				}
				//Creating escalation acknowledgement
				if (flagAckInsert) inboxWriteProvider.insert(ackReq);
				else if (flagAckUpdate) inboxWriteProvider.update(new InboxPk(ackReq.getId()), ackReq);
				else logger.info("Escalation acknowledgement is already existing for ESR_MAP_ID: " + ackEsrMapId + ", RECEIVER_ID: " + requesterId);
				//Updating existing request to mark as being escalated
				inboxWriteProvider.update(new InboxPk(inboxId), existingReq);
				logger.debug("Escalation processing commit: INBOX.ID: " + inboxId + ", ESCALATION.ID: " + escalation.getId() + "");
				//Escalation transaction being commit which comprise adding acknowledgement for requester, escalation.
				connEsc.commit();
				//Send email notifications
				if (escIdCsv.length() > 0){
					escIdCsv = escIdCsv.substring(0, escIdCsv.lastIndexOf(","));
					try{
						//String approverId=String.valueOf(actorId);
						String approverId = getAllActor(ackEsrMapId, actorId);
						logger.debug("Sending escalation email: Recipient: " + escIdCsv);
						sendEmailToEscalation(escPortalEmail, escIdCsv, approverId);
						logger.debug("Sending escalation acknowledgement email: Recipient: " + reqAppIdCsv);
						sendEmailToRequester(escAckPortalEmail, reqAppIdCsv);
					} catch (AddressException ex){
						logger.error("Unable to send escalation email. Invalid email address.");
						logger.error("Error message: " + ex.getMessage());
					} catch (UnsupportedEncodingException ex){
						logger.error("Unable to send escalation email. Invalid encoding.");
						logger.error("Error message: " + ex.getMessage());
					} catch (MessagingException ex){
						logger.error("Unable to send escalation email.");
						logger.error("Error message: " + ex.getMessage());
						Throwable cause = ex.getCause();
						logger.error("Error cause: " + cause != null ? cause.getMessage() : "No information.");
					}
				} else{
					logger.debug("No escalation emails");
				}
			} catch (SQLException ex){
				logger.error("Unable to commit escalation transaction. Rolling back.");
				logger.error("Error message: " + ex.getMessage());
				try{
					logger.debug("Escalation processing rollback: INBOX.ID: " + inboxId + ", ESCALATION.ID: " + escalation.getId() + "");
					connEsc.rollback();
				} catch (SQLException e){
					logger.error("Unable to rollback escalation transaction.");
					logger.error("Error message: " + ex.getMessage());
				}
			} finally{
				if (connEsc != null){
					ResourceManager.close(connEsc);
				}
			}
		} else{
			logger.debug("No overdue: INBOX.ID: " + inboxId + ", ESCALATION.ID: " + escalation.getId() + "");
			if (connEsc != null){
				ResourceManager.close(connEsc);
			}
		}
		logger.debug("End escalation processing: INBOX.ID: " + inboxId + ", ESCALATION.ID: " + escalation.getId() + "");
	}

	protected String getAllActor(int esrMapId, int actorId) {
		final String SELECT_ACTORS = "SELECT * FROM INBOX I JOIN USERS U ON I.RECEIVER_ID=U.ID WHERE I.IS_DELETED!=1 AND I.IS_ESCALATED=0 AND I.RECEIVER_ID=I.ASSIGNED_TO AND I.ESR_MAP_ID=? AND U.LEVEL_ID=?";
		String actorIds = String.valueOf(actorId);
		try{
			int actorLevelId = getLevelId(actorId);
			InboxDao inboxProvider = InboxDaoFactory.create();
			Inbox[] otherActorInbox = inboxProvider.findByDynamicSelect(SELECT_ACTORS, new Object[] { esrMapId, actorLevelId });
			for (Inbox element : otherActorInbox){
				int otherActorId = element.getReceiverId();
				actorIds += "," + otherActorId;
			}
			return (actorIds);
		} catch (InboxDaoException ex){} catch (ProfileInfoDaoException e){}
		return (actorIds);
	}

	/**
	 * Creates email recipient list (To: List of escalation) and calls a mailer utility for sending emails.
	 * 
	 * @param escPortalEmail
	 * @param escIdCsv
	 * @param actorId
	 * @throws ProfileInfoDaoException
	 * @throws AddressException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws MessagingException
	 */
	protected void sendEmailToEscalation(PortalMail escPortalEmail, String escIdCsv, String actorId) throws ProfileInfoDaoException, AddressException, UnsupportedEncodingException, FileNotFoundException, MessagingException {
		final String SENDER_EMAIL = null;
		String[] escEmailAddresses = null;
		String[] escEmailCcAddresses = null;
		if (escalationEmailCache.containsKey(escIdCsv)) escEmailAddresses = escalationEmailCache.get(escIdCsv);
		else{
			escEmailAddresses = ItModel.fetchOfficialMailIds(escIdCsv);
			String[] temp = new String[escEmailAddresses.length];
			System.arraycopy(escEmailAddresses, 0, temp, 0, escEmailAddresses.length);
			escalationEmailCache.put(escIdCsv, temp);
		}
		escEmailCcAddresses = ItModel.fetchOfficialMailIds(actorId);
		escPortalEmail.setFromMailId(SENDER_EMAIL);
		escPortalEmail.setAllReceipientMailId(escEmailAddresses);
		escPortalEmail.setAllReceipientcCMailId(escEmailCcAddresses);
		MailGenerator mailer = new MailGenerator();
		mailer.invoker(escPortalEmail);
	}

	/**
	 * Creates email recipient list (To: List of escalation) and calls a mailer utility for sending emails.
	 * 
	 * @param escAckPortalEmail
	 * @param requesterIdCsv
	 * @throws ProfileInfoDaoException
	 * @throws AddressException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws MessagingException
	 */
	protected void sendEmailToRequester(PortalMail escAckPortalEmail, String requesterIdCsv) throws ProfileInfoDaoException, AddressException, UnsupportedEncodingException, FileNotFoundException, MessagingException {
		final String SENDER_EMAIL = null;
		String[] escEmailAddresses = null;
		String[] escEmailCcAddresses = null;
		if (escalationEmailCache.containsKey(requesterIdCsv)) escEmailAddresses = escalationEmailCache.get(requesterIdCsv);
		else{
			escEmailAddresses = ItModel.fetchOfficialMailIds(requesterIdCsv);
			String[] temp = new String[escEmailAddresses.length];
			System.arraycopy(escEmailAddresses, 0, temp, 0, escEmailAddresses.length);
			escalationEmailCache.put(requesterIdCsv, temp);
		}
		escAckPortalEmail.setFromMailId(SENDER_EMAIL);
		escAckPortalEmail.setAllReceipientMailId(escEmailAddresses);
		escAckPortalEmail.setAllReceipientcCMailId(escEmailCcAddresses);
		MailGenerator mailer = new MailGenerator();
		mailer.invoker(escAckPortalEmail);
	}

	/**
	 * Updates SIBLINGS column in EMP_SER_REQ_MAP database table by adding escalation-user ID. This makes escalated used as approver/handler.
	 * 
	 * @param connEsc
	 * @param esrMapId
	 * @param escActorId
	 * @throws EmpSerReqMapDaoException
	 * @throws LeaveMasterDaoException
	 * @throws TimesheetReqDaoException
	 * @throws TravelReqDaoException
	 * @throws SodexoReqDaoException
	 * @throws LoanRequestDaoException
	 * @throws ReimbursementReqDaoException
	 * @throws ItRequestDaoException
	 * @throws CandidateReqDaoException
	 * @throws IssueHandlerChainReqDaoException
	 * @throws AppraisalDaoException
	 * @throws ReferFriendDaoException
	 * @throws ExitEmployeeDaoException
	 * @throws InsuranceHandlerChainReqDaoException
	 * @throws ExitEmpUsersMapDaoException
	 */
	protected void updateRequestTables(Connection connEsc, int esrMapId, int escActorId, int actorId) throws EmpSerReqMapDaoException, LeaveMasterDaoException, TimesheetReqDaoException, TravelReqDaoException, SodexoReqDaoException, LoanRequestDaoException, ReimbursementReqDaoException, ItRequestDaoException, CandidateReqDaoException, IssueHandlerChainReqDaoException, AppraisalDaoException, ReferFriendDaoException, ExitEmployeeDaoException, InsuranceHandlerChainReqDaoException,
			ExitEmpUsersMapDaoException {
		EmpSerReqMapDao esrReadProvider = EmpSerReqMapDaoFactory.create();
		EmpSerReqMap esr = esrReadProvider.findByPrimaryKey(esrMapId);
		int requestTypeId = esr.getReqTypeId();
		switch (requestTypeId) {
			case 1:
				//Update LEAVE_MASTER table
				LeaveMasterDao leaveMasterDao = LeaveMasterDaoFactory.create(connEsc);
				LeaveMaster[] leaveMaster = leaveMasterDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE ESR_MAP_ID = ? AND ASSIGNED_TO = ?", new Object[] { esrMapId, actorId });
				if (leaveMaster != null && leaveMaster.length > 0){
					LeaveMaster newleaveentry = leaveMaster[0];
					newleaveentry.setAssignedTo(escActorId);
					newleaveentry.setId(0);
					leaveMasterDao.insert(newleaveentry);
				} else{
					logger.debug("LEAVE_MASTER request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 2:
				//Update TRAVEL, TRAVEL_REQ :: TL_REQ_ID table
				TravelReqDao travelReqProvider = TravelReqDaoFactory.create(connEsc);
				TravelReq[] travelReq = travelReqProvider.findByDynamicSelect("SELECT TR.* from TRAVEL T JOIN TRAVEL_REQ TR ON T.ID=TR.TL_REQ_ID WHERE T.ESRQM_ID = ? and TR.ASSIGNED_TO = ?", new Object[] { esrMapId, actorId });
				if (travelReq != null && travelReq.length > 0){
					TravelReq newTravelReq = travelReq[0];
					newTravelReq.setAssignedTo(escActorId);
					newTravelReq.setId(0);
					travelReqProvider.insert(newTravelReq);
				} else{
					logger.debug("TRAVEL_REQ request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 3:
				SodexoReqDao sodexoReqDao = SodexoReqDaoFactory.create(connEsc);
				SodexoReq[] sodexoReq = sodexoReqDao.findByDynamicSelect(" SELECT * FROM SODEXO_REQ WHERE ESR_MAP_ID = ? AND ASSIGNED_TO = ? ", new Object[] { esrMapId, actorId });
				if (sodexoReq != null && sodexoReq.length > 0){
					SodexoReq newSodexoReq = sodexoReq[0];
					newSodexoReq.setAssignedTo(escActorId);
					newSodexoReq.setId(0);
					sodexoReqDao.insert(newSodexoReq);
				} else{
					logger.debug("SODEXO_REQ request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 4:
				LoanRequestDao loanRequestDao = LoanRequestDaoFactory.create(connEsc);
				LoanRequest[] loanRequest = loanRequestDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE ESR_MAP_ID = ? AND ASSIGNED_TO = ?", new Object[] { esrMapId, actorId });
				if (loanRequest != null && loanRequest.length > 0){
					LoanRequest newloanRequest = loanRequest[0];
					newloanRequest.setId(0);
					newloanRequest.setAssignTo(escActorId);
					loanRequestDao.insert(newloanRequest);
				} else{
					logger.debug("LOAN_REQUEST request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 5:
				//Update REIMBURSEMENT_REQ table
				ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create(connEsc);
				ReimbursementReq[] reimbursementReq = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { esrMapId, actorId });
				ReimbursementReq[] reimbursementExist = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { esrMapId, escActorId });
				if (reimbursementReq != null && reimbursementReq.length > 0){
					ReimbursementReq newReimbursementReqId = reimbursementReq[0];
					newReimbursementReqId.setAssignTo(escActorId);
					newReimbursementReqId.setId(0);
					if (reimbursementExist.length == 1){
						ReimbursementReq existingEsc = reimbursementExist[0];
						existingEsc.setStatus(newReimbursementReqId.getStatus());
						reimbursementReqDao.update(new ReimbursementReqPk(existingEsc.getId()), existingEsc);
					} else reimbursementReqDao.insert(newReimbursementReqId);
				} else{
					logger.debug("REIMBURSEMENT_REQ request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 6:
				//Update IT_REQUEST table
				ItRequestDao itRequestDao = ItRequestDaoFactory.create(connEsc);
				ItRequest[] itRequest = itRequestDao.findByDynamicSelect("SELECT * FROM IT_REQUEST WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { esrMapId, actorId });
				if (itRequest != null && itRequest.length > 0){
					ItRequest newItRequest = itRequest[0];
					newItRequest.setId(0);
					newItRequest.setAssignTo(escActorId);
					newItRequest.setReceiverId(escActorId);
					itRequestDao.insert(newItRequest);
				} else{
					logger.debug("IT_REQUEST request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 7:
				//Update CANDIDATE_REQ table
				CandidateReqDao candidateReqDao = CandidateReqDaoFactory.create(connEsc);
				CandidateReq[] candidateReq = candidateReqDao.findByDynamicWhere("SELECT * FROM CANDIDATE_REQ WHERE ESR_MAP_ID = ? AND ASSIGNED_TO = ?", new Object[] { esrMapId, actorId });
				if (candidateReq != null && candidateReq.length > 0){
					CandidateReq newCandidateReq = candidateReq[0];
					newCandidateReq.setAssignedTo(escActorId);
					newCandidateReq.setId(0);
					candidateReqDao.insert(newCandidateReq);
				} else{
					logger.debug("CANDIDATE_REQ request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 8:
				//Update TIMESHEET_REQ, TIMESHEET_REQ_BACKUupdateRequestTablesP table
				TimesheetReqDao timesheetReqDao = TimesheetReqDaoFactory.create(connEsc);
				TimesheetReq[] timesheetReq = timesheetReqDao.findByDynamicSelect("SELECT * FROM TIMESHEET_REQ WHERE ESRQM_ID = ? AND ASSIGNED_TO = ? ", new Object[] { esrMapId, actorId });
				if (timesheetReq != null && timesheetReq.length > 0){
					TimesheetReq newTSEntry = timesheetReq[0];
					newTSEntry.setAssignedTo(escActorId);
					newTSEntry.setId(0);
					timesheetReqDao.insert(newTSEntry);
				} else{
					logger.debug("TIMESHEET_REQ request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 9:
				//No changed required for ROLL_ON table
				break;
			case 10:
				//No changed required for PROJECTS table
				break;
			case 11:
				//Update ISSUE_HANDLER_CHAIN_REQ table
				IssueHandlerChainReqDao issueHandlerChainReqDao = IssueHandlerChainReqDaoFactory.create(connEsc);
				IssueHandlerChainReq[] issueHandlerChainReq = issueHandlerChainReqDao.findByDynamicSelect("SELECT * FROM ISSUE_HANDLER_CHAIN_REQ WHERE ESR_MAP_ID = ? AND ASSIGNED_TO = ? ", new Object[] { esrMapId, actorId });
				if (issueHandlerChainReq != null && issueHandlerChainReq.length > 0){
					IssueHandlerChainReq newIssueHandlerChainReq = issueHandlerChainReq[0];
					newIssueHandlerChainReq.setId(0);
					newIssueHandlerChainReq.setAssignedTo(escActorId);
					issueHandlerChainReqDao.insert(newIssueHandlerChainReq);
				} else{
					logger.debug("ISSUE_HANDLER_CHAIN_REQ request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 12:
				//Update APPRAISAL, APPRAISAL_REQ :: APPRAISAL_ID table
				//				AppraisalDao appraisalDao = AppraisalDaoFactory.create(connEsc);
				//				Appraisal[] appraisal = appraisalDao.findByDynamicSelect("SELECT * FROM APPRAISAL A JOIN APPRAISAL_REQ AR ON A.ID = AR.APPRAISAL_ID WHERE A.ESR_MAP_ID = ?", new Object[]{esrMapId, actorId});
				//				if(appraisal != null && appraisal.length>0){
				//					Appraisal newAppraisal = appraisal[0];
				//					newAppraisal.setId(0);
				//					appraisalDao.insert(newAppraisal);
				//				}else {
				//					logger.debug("APPRAISAL request doesn't exists for ESR_MAP_ID: " + esrMapId +", ASSIGNED_TO: " + actorId );
				//				}
				break;
			case 13:
				//Update REFER_FRIEND table
				//				ReferFriendDao referFriendDao = ReferFriendDaoFactory.create(connEsc);
				//				ReferFriend[] referFriend = referFriendDao.findByDynamicSelect("SELECT * FROM REFER_FRIEND WHERE ESR_MAP_ID = ? ", new Object[]{esrMapId});
				//				if(referFriend != null && referFriend.length > 0){
				//					ReferFriend newreferFriend = referFriend[0];
				//					newreferFriend.setId(0);
				//					referFriendDao.insert(newreferFriend);HRSPOC
				//				}else {
				//					logger.debug("REFER_FRIEND request doesn't exists for ESR_MAP_ID: " + esrMapId +", ASSIGNED_TO: " + actorId );
				//					}
				//				break;
			case 14:
				//Update EXIT_EMPLOYEE table
				ExitEmployeeDao exitEmployeeDao = ExitEmployeeDaoFactory.create(connEsc);
				ExitEmployee[] exitEmployee = exitEmployeeDao.findByDynamicSelect("SELECT * FROM EXIT_EMPLOYEE WHERE ESR_MAP_ID = ?", new Object[] { esrMapId });
				int exitId = exitEmployee.length > 0 ? exitEmployee[0].getId() : 0;
				if (exitId != 0){
					final String SELECT_EEUM = "SELECT * FROM EXIT_EMP_USERS_MAP WHERE EXIT_ID=? AND USER_ID=? ORDER BY `TYPE` ASC";
					ExitEmpUsersMapDao eeumProvider = ExitEmpUsersMapDaoFactory.create();
					ExitEmpUsersMap[] eeum = eeumProvider.findByDynamicSelect(SELECT_EEUM, new Object[] { exitId, actorId });
					if (eeum.length > 0){
						ExitEmpUsersMap existingEeum = eeum[0];
						existingEeum.setId(0);
						existingEeum.setUserId(escActorId);
						eeumProvider.insert(existingEeum);
					} else{
						logger.debug("Unable to process EXIT_EMP_USER_MAP. No entry found for EXIT_ID: " + exitId + ", USER_ID: " + actorId);
					}
				} else{
					logger.debug("EXIT_EMPLOYEE request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			case 15:
				//Update INSURANCE_HANDLER_CHAIN_REQ table
				InsuranceHandlerChainReqDao insuranceHandlerChainReqDao = InsuranceHandlerChainReqDaoFactory.create(connEsc);
				InsuranceHandlerChainReq[] insuranceHandlerChainReq = insuranceHandlerChainReqDao.findByDynamicSelect("SELECT * FROM INSURANCE_HANDLER_CHAIN_REQ WHERE ESR_MAP_ID = ?", new Object[] { esrMapId });
				if (insuranceHandlerChainReq != null && insuranceHandlerChainReq.length > 0){
					InsuranceHandlerChainReq newInsuranceHandlerChainReq = insuranceHandlerChainReq[0];
					newInsuranceHandlerChainReq.setId(0);
					newInsuranceHandlerChainReq.setReceiverId(escActorId);
					insuranceHandlerChainReqDao.insert(newInsuranceHandlerChainReq);
				} else{
					logger.debug("INSURANCE_HANDLER_CHAIN_REQ request doesn't exists for ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId);
				}
				break;
			default:
				logger.debug("For the ESR_MAP_ID: " + esrMapId + ", ASSIGNED_TO: " + actorId + " None of the category is matched");
		}
	}

	/**
	 * @param approverId
	 * @param creationDate
	 * @param dueDays
	 * @param requestMail
	 * @param subject
	 * @return Returns the PortalMail object by setting necessary field for creating escalation email message body except recipient list.
	 * @throws ProfileInfoDaoException
	 * @throws FileNotFoundException
	 */
	protected PortalMail getEscMail(int approverId, Date creationDate, int dueDays, String requestMail, String subject) throws ProfileInfoDaoException, FileNotFoundException {
		String escMail = "";
		String actorName = "";
		String dateOfAction = PortalUtility.getdd_MM_yyyy(creationDate);
		PortalMail escMailDesigner = new PortalMail();
		escMailDesigner.setTemplateName(MailSettings.ESCALATION);
		//Email actual actor name
		if (approverId == 0) actorName = ACTOR_ALL;
		else actorName = getProfileName(approverId, true);//gets full name
		//Setting PortalMail properties used by the template fields 
		escMailDesigner.setApproverName(actorName);
		//Extracting request email message body
		String forwardedRequest = getForwardedRequest(requestMail);
		//Setting PortalMail properties used by the template fields
		escMailDesigner.setTotaldays(dueDays);
		escMailDesigner.setMessageBody(forwardedRequest);
		escMailDesigner.setDateOfAction(dateOfAction);
		MailGenerator escMailGenerator = new MailGenerator();
		//Creating string email message by merging template with field values
		escMail = escMailGenerator.replaceFields(escMailGenerator.getMailTemplate(MailSettings.ESCALATION), escMailDesigner);
		//Setting PortalMail subject
		escMailDesigner.setMailSubject(subject);
		//Setting PortalMail message body
		escMailDesigner.setMailBody(escMail);
		return (escMailDesigner);
	}

	protected PortalMail getEscAckMail(int reqesterId, int approverId, Date creationDate, String subject) throws ProfileInfoDaoException, FileNotFoundException {
		String escMail = "";
		String requestorName = "";
		String actorName = "";
		String dateOfAction = PortalUtility.getdd_MM_yyyy(creationDate);
		PortalMail escMailDesigner = new PortalMail();
		escMailDesigner.setTemplateName(MailSettings.ESCALATION_ACK);
		//Email actual actor name
		if (approverId == 0) actorName = ACTOR_ALL;
		else actorName = getProfileName(approverId, false);
		requestorName = getProfileName(reqesterId, false);
		//Setting PortalMail properties used by the template fields 
		escMailDesigner.setEmpFname(requestorName);
		escMailDesigner.setApproverName(actorName);
		escMailDesigner.setDateOfAction(dateOfAction);
		MailGenerator escMailGenerator = new MailGenerator();
		//Creating string email message by merging template with field values
		escMail = escMailGenerator.replaceFields(escMailGenerator.getMailTemplate(MailSettings.ESCALATION_ACK), escMailDesigner);
		//Setting PortalMail subject
		escMailDesigner.setMailSubject(subject);
		//Setting PortalMail message body
		escMailDesigner.setMailBody(escMail);
		return (escMailDesigner);
	}

	/**
	 * @param userId
	 * @return Returns the full employee name of a given ID. Used here to get the name of actual approver.
	 * @throws ProfileInfoDaoException
	 */
	protected String getProfileName(int userId, boolean full) throws ProfileInfoDaoException {
		final String QUERY_PROFILE_USERS = "SELECT PI.* FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID AND U.ID=?";
		String firstName, lastName;
		String profileName;
		firstName = lastName = profileName = "";
		ProfileInfoDao profileProvider = ProfileInfoDaoFactory.create();
		ProfileInfo[] profile = profileProvider.findByDynamicSelect(QUERY_PROFILE_USERS, new Object[] { userId });
		if (profile.length == 1){
			firstName = profile[0].getFirstName();
			lastName = profile[0].getLastName();
			profileName = firstName;
			if (full && lastName != null) profileName += " " + lastName;
		} else{
			logger.debug("Unable to get profileName from PROFILE_INFO and USERS for USERS.ID: " + userId);
			profileName = "Unknown";
		}
		return (profileName);
	}

	/**
	 * @param requestMail
	 * @return A string extracted from HTML string. Used here to get the content of actual request from request email.
	 */
	protected String getForwardedRequest(String requestMail) {
		final String TABLE_BEG = "<table";
		String forwardedMail = null;
		//To retrieve the position of second table element in email
		int startPos = 0, endPos = 0;
		//
		//To retrieve third table that contains email body
		//assuming the current email templates
		//
		try{
			startPos = requestMail.indexOf(TABLE_BEG);
			startPos = requestMail.indexOf(TABLE_BEG, startPos + 1);
			startPos = requestMail.indexOf(TABLE_BEG, startPos + 1);
			startPos = requestMail.indexOf(TABLE_BEG, startPos + 1);
			endPos = requestMail.lastIndexOf(TABLE_BEG);
			forwardedMail = requestMail.substring(startPos, endPos);
		} catch (Exception ex){
			logger.error("Error in retrieving forwared e-mail content from request e-mail. Location: EscalatinoJob.class:getForwardedRequest(String)");
			logger.error("Error messge: " + ex.getMessage());
			forwardedMail = "<font color=\"#ff0000\">Request email cannot be retrieved.</font>";
		}
		return (forwardedMail);
	}

	/**
	 * @param requestMail
	 * @return A string extracted from HTML string. Used here to get the content of actual request from request email.
	 *         This uses Jsoup library for parsing HTML document.
	 */
	protected String getForwardedRequest2(String requestMail) {
		final String selector = "table table:eq(1)";
		String forwardedMail = null;
		Document document = Jsoup.parse(requestMail);
		Elements contentTable = document.select(selector);
		if (contentTable != null) forwardedMail = contentTable.html();
		else{
			forwardedMail = "<font color=\"#ff0000\">Request email cannot be retrieved.</font>";
		}
		return (forwardedMail);
	}

	/**
	 * @param creationDate
	 * @param dueDays
	 * @return Returns true if todays date comes after given date added with due days. Used to check for approver action overdue.
	 */
	protected boolean overdue(Date creationDate, int dueDays) {
		boolean result = false;
		Date today = new Date();
		Calendar creationDateCal = Calendar.getInstance();
		creationDateCal.setTime(creationDate);
		creationDateCal.add(Calendar.DATE, dueDays);
		Date dueDate = creationDateCal.getTime();
		if (today.after(dueDate)) result = true;
		else result = false;
		return (result);
	}

	/**
	 * @param actorId
	 * @return Returns the LEVELID of an employee. Used here to get level id of an approver.
	 * @throws ProfileInfoDaoException
	 */
	protected int getLevelId(int actorId) throws ProfileInfoDaoException {
		final String WHERE_PROFILE_USERS = "ID IN (SELECT PROFILE_ID FROM USERS WHERE ID=?)";
		int levelId = 0;
		ProfileInfoDao profileProvider = ProfileInfoDaoFactory.create();
		ProfileInfo[] actorProfile = profileProvider.findByDynamicWhere(WHERE_PROFILE_USERS, new Object[] { actorId });
		if (actorProfile.length == 1) levelId = actorProfile[0].getLevelId();
		else logger.debug("Unble to get level id for USERS.ID: " + actorId);
		return (levelId);
	}

	/**
	 * @param requestorId
	 * @param rmHrSpoc
	 *            : Argument Values- HRSPOC/RM
	 * @return Returns the ID for HRSPOC- HR Single point of contact/RM- Reporting manager for requester.
	 * @throws ProfileInfoDaoException
	 * @throws UsersDaoException
	 */
	protected int getRmHrSpocId(int requestorId, String rmHrSpoc) throws ProfileInfoDaoException, UsersDaoException {
		int rmHrSpocId = 0;
		UsersDao userProvider = UsersDaoFactory.create();
		Users users = userProvider.findByPrimaryKey(requestorId);
		ProfileInfoDao profileProvider = ProfileInfoDaoFactory.create();
		ProfileInfo requestorProfile = profileProvider.findByPrimaryKey(users.getProfileId());
		if (rmHrSpoc == HRSPOC){
			rmHrSpocId = requestorProfile.getHrSpoc();
		} else if (rmHrSpoc == RM){
			rmHrSpocId = requestorProfile.getReportingMgr();
		}
		return (rmHrSpocId);
	}

	/**
	 * @return Returns org.slf4j.Logger implementation.
	 */
	protected Logger getLogger() {
		Logger logger = LoggerUtil.getLogger(this.getClass());
		return (logger);
	}

	@Override
	public String toString() {
		return "EscalationJob [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	/**
	 * @author Gurunath.rokkam
	 * @param regionId
	 * @param date
	 * @param days
	 * @return next working day excluding weekends and public holidays
	 */
	public static Date getNextWorkingDay(int regionId, Date date, int days) {
		try{
			Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS WHERE CAL_ID IN (SELECT ID FROM CALENDAR WHERE REGION = ? AND YEAR=? ) AND DATE_PICKER BETWEEN ? AND ADDDATE(?,180) ORDER BY DATE_PICKER ASC", new Object[] { regionId, PortalUtility.getyear(date), date, date });
			int nextdays = 0;
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(PortalUtility.getdd_MM_yyyy(date)));
			cal.add(Calendar.DAY_OF_MONTH, -1);
			while (nextdays < days){
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
					continue;
				}
				String dat = PortalUtility.getdd_MM_yyyy(cal.getTime());
				boolean tocontinue = false;
				if (holiday != null && holiday.length > 0) for (Holidays tmpHoliday : holiday){
					if (dat.equals(PortalUtility.getdd_MM_yyyy(tmpHoliday.getDatePicker()))){
						tocontinue = true;
						break;
					}
				}
				if (tocontinue) continue;
				nextdays++;
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
			return cal.getTime();
		} catch (Exception e){}
		return null;
	}

	/**
	 * @author Gurunath.rokkam
	 * @param regionId
	 * @param date
	 * @param days
	 * @return next working day excluding weekends and public holidays
	 */
	public static int getWorkingDaysCount(int regionId, Date from, Date to) {
		try{
			from = new SimpleDateFormat("dd-MM-yyyy").parse(PortalUtility.getdd_MM_yyyy(from));
			to = new SimpleDateFormat("dd-MM-yyyy").parse(PortalUtility.getdd_MM_yyyy(to));
			Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS WHERE CAL_ID IN (SELECT ID FROM CALENDAR WHERE REGION = ?) AND DATE_PICKER BETWEEN ? AND ? ORDER BY DATE_PICKER ASC", new Object[] { regionId, from, to });
			int nextdays = -1;
			Calendar cal = Calendar.getInstance();
			cal.setTime(from);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			while (cal.getTime().before(to)){
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
					continue;
				}
				String dat = PortalUtility.getdd_MM_yyyy(cal.getTime());
				boolean tocontinue = false;
				if (holiday != null && holiday.length > 0) for (Holidays tmpHoliday : holiday){
					if (dat.equals(PortalUtility.getdd_MM_yyyy(tmpHoliday.getDatePicker()))){
						tocontinue = true;
						break;
					}
				}
				if (tocontinue) continue;
				nextdays++;
			}
			return nextdays;
		} catch (Exception e){}
		return -1;
	}

	public static boolean IsWorkingDay(int region, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7) return false;
		try{
			Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS WHERE CAL_ID IN (SELECT ID FROM CALENDAR WHERE REGION = ?) AND DATE_PICKER = ? ORDER BY DATE_PICKER ASC", new Object[] { region, date });
			if (holiday != null && holiday.length > 0) return false;
		} catch (Exception e){}
		return true;
	}
	
 /*********************Escalation Bonus Part **********************************************/	
	private void doEscalationForBonus() {
		final String BONUS_RECONCILATION_QUERY = " SELECT * FROM BONUS_RECONCILIATION WHERE `STATUS` != 'Completed' ";
		BonusReconciliationDao bonusReconciliationDao = BonusReconciliationDaoFactory.create();
		BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create();
		try{
			BonusReconciliation[] bonusRec = bonusReconciliationDao.findByDynamicSelect(BONUS_RECONCILATION_QUERY, new Object[] {});
			if (bonusRec != null && bonusRec.length > 0){
				for (BonusReconciliation eachBonus : bonusRec){
					BonusReconciliationReq[] bonusReqs = bonusReqDao.findByDynamicWhere("BR_ID = ? AND SUBMITTED_ON IS NULL", new Object[] { eachBonus.getId() });
					for (BonusReconciliationReq eachBonusReq : bonusReqs){
						try{
							int daysCrossed = getWorkingDaysCount(1, eachBonusReq.getReceivedOn(), new Date());
							Escalation escalation = null;
							if (eachBonusReq.getIsEscalated() == 0) escalation = getBonusEscalationRecord(eachBonusReq.getSeqId(), eachBonusReq.getAssignedTo());
							if (escalation != null && daysCrossed == escalation.getDueDays() && eachBonusReq.getIsEscalated() == 0){
								escalateBonusForTheLevel(daysCrossed, escalation, eachBonusReq, eachBonus);
							} else{
								sendRemainderBonus(daysCrossed, escalation == null ? 0 : escalation.getDueDays(), eachBonus, eachBonusReq);
							}
						} catch (Exception e){
							logger.error("There is a Exception occured while querying the data for the escalation \n" + eachBonusReq, e);
						}
					}
				}
			}
		} catch (BonusReconciliationDaoException e){
			logger.error("There is a DepPerdiemDaoException occured while querying the data for the escalation");
		} catch (BonusReconciliationReqDaoException e){
			logger.error("There is a DepPerdiemReqDaoException occured while querying the data for the escalation");
		} catch (Exception e){
			logger.error("There is a ProfileInfoDaoException occured while querying the data for the escalation", e);
		}
	}
	
	
	private void sendRemainderBonus(int daysCrossed, int daysConfigured, BonusReconciliation bonus, BonusReconciliationReq bonusReq) {
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		PortalMail pMail = new PortalMail();
		MailGenerator generator = new MailGenerator();
		try{
			if (bonusReq != null){
				ProfileInfo assignedTo = profileDao.findWhereUserIdEquals(bonusReq.getAssignedTo());
				pMail.setApproverName(assignedTo.getFirstName() + " " + assignedTo.getLastName());
				pMail.setSerReqID(getReqId(bonus.getEsrMapId())+""+bonus.getMonth());
				pMail.setDaysCrossed(daysCrossed + "");
				pMail.setTemplateName(MailSettings.BONUS_REMAINDER);
				pMail.setMailSubject("Reminder Bonus Reconciliation Report Request Pending");
				pMail.setRecipientMailId(assignedTo.getOfficalEmailId());
				if (daysConfigured > daysCrossed && bonusReq.getIsEscalated() == 0) pMail.setComment(" The escalation chain configured for the action is " + daysConfigured + " days. Please take action inorder to avoid escalations");
				else pMail.setComment("");
				generator.invoker(pMail);
			}
		} catch (ProfileInfoDaoException e){
			logger.error("There is a ProfileInfoDaoException occured while querying the data for the escalation");
		} catch (EmpSerReqMapDaoException e){
			logger.error("There is a EmpSerReqMapDaoException occured while querying the data for the escalation");
		} catch (FileNotFoundException e){
			logger.error("There is no SAL_RECON_Reminder html template found");
		} catch (AddressException e){
			logger.error("There is a AddressException occured while querying the data for the escalation");
		} catch (UnsupportedEncodingException e){
			logger.error("There is a UnsupportedEncodingException occured while querying the data for the escalation");
		} catch (MessagingException e){
			logger.error("There is a MessagingException occured while querying the data for the escalation");
		}
	}
	
	
	private void escalateBonusForTheLevel(int daysCrossed, Escalation escalation, BonusReconciliationReq bonusReq, BonusReconciliation bonus) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, InboxDaoException, AddressException, UnsupportedEncodingException, MessagingException, SalaryReconciliationReqDaoException, DepPerdiemReqDaoException {
		String[] escalTo = escalation.getEscalTo().split(",");
		String[] perm = null;
		for (String eachEscal : escalTo){
			try{
				perm = eachEscal.split("\\|");
				if (perm[0].equalsIgnoreCase("false")){
					escalateBonusWithNoEdit(perm[1], daysCrossed, bonusReq, bonus);
				} else{
					escalateBonusWithEdit(daysCrossed, perm[1], bonusReq, bonus);
				}
			} catch (Exception e){
				logger.error("unable to escalte record", e);
			}
		}
	}

	private void escalateBonusWithNoEdit(String escId, int daysCrossed, BonusReconciliationReq bonusReq, BonusReconciliation bonus) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, InboxDaoException, AddressException, UnsupportedEncodingException, MessagingException, UsersDaoException {
		InboxDao inboxDao = InboxDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(bonusReq.getAssignedTo());
		UsersDao usersDao = UsersDaoFactory.create();
		Users escalUser = null;
		if (escId == null) return;
		if (escId.equalsIgnoreCase("RM")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.REPORTING_MGR", new Object[] { bonusReq.getAssignedTo() })[0];
		} else if (escId.equalsIgnoreCase("HRSPOC")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.HR_SPOC", new Object[] {  bonusReq.getAssignedTo() })[0];
		} else{
			escalUser = usersDao.findByPrimaryKey(Integer.parseInt(escId));
		}
		ProfileInfo escalProfile = profileInfoDao.findByPrimaryKey(escalUser.getProfileId());;
		Inbox inbox = new Inbox();
		PortalMail pMail = new PortalMail();
		MailGenerator mGenerator = new MailGenerator();
		pMail.setSerReqID(getReqId(bonus.getEsrMapId())+""+bonus.getMonth());
		if (profileInfo != null) pMail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
		if (escalProfile != null) pMail.setEmployeeName(escalProfile.getFirstName() + " " + escalProfile.getLastName());
		pMail.setDaysCrossed(daysCrossed + "");
		pMail.setDateOfAction(PortalUtility.getdd_MM_yyyy(bonusReq.getReceivedOn()));
		pMail.setTemplateName(MailSettings.BONUS_ESCAL_NOTIFICATION);
		String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.BONUS_ESCAL_NOTIFICATION), pMail);
		inbox.setReceiverId(escalUser.getId());
		inbox.setEsrMapId(bonus.getEsrMapId());
		inbox.setSubject("Escalated Request Notification - " + pMail.getSerReqID());
		inbox.setAssignedTo(0);
		inbox.setRaisedBy(0);
		inbox.setCreationDatetime(new Date());
		inbox.setStatus(bonus.getStatus());
		inbox.setCategory("Bonus Reconciliation");
		inbox.setIsDeleted(0);
		inbox.setIsRead(0);
		inbox.setMessageBody(msg);
		inbox.setIsEscalated(2);
		inboxDao.insert(inbox);
		pMail.setMailSubject("Per-Diem Report Escalation for the period " + pMail.getSerReqID());
		pMail.setRecipientMailId(escalProfile.getOfficalEmailId());
		mGenerator.invoker(pMail);
	}
	
	
	
	private void escalateBonusWithEdit(int daysCrossed, String escId, BonusReconciliationReq bonusReq, BonusReconciliation bonus) throws ProfileInfoDaoException, EmpSerReqMapDaoException, FileNotFoundException, DepPerdiemReqDaoException, AddressException, UnsupportedEncodingException, MessagingException, UsersDaoException {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create();
		PortalMail pMail = new PortalMail();
		MailGenerator mGenerator = new MailGenerator();
		UsersDao usersDao = UsersDaoFactory.create();
		Users escalUser = null;
		try {
		if (escId == null) return;
		if (escId.equalsIgnoreCase("RM")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.REPORTING_MGR", new Object[] { bonusReq.getAssignedTo() })[0];
		} else if (escId.equalsIgnoreCase("HRSPOC")){
			escalUser = usersDao.findByDynamicSelect("SELECT UU.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID AND U.ID=? JOIN USERS UU ON UU.ID=P.HR_SPOC", new Object[] { bonusReq.getAssignedTo() })[0];
		} else{
			escalUser = usersDao.findByPrimaryKey(Integer.parseInt(escId));
		}
		ProfileInfo escalProfile = profileInfoDao.findByPrimaryKey(escalUser.getProfileId());;
		ProfileInfo assignedProfile = profileInfoDao.findWhereUserIdEquals(bonusReq.getAssignedTo());
		pMail.setEmployeeName(escalProfile.getFirstName() + " " + escalProfile.getLastName());
		pMail.setSerReqID(getReqId(bonus.getEsrMapId())+""+bonus.getMonth());
		pMail.setApproverName(assignedProfile.getFirstName() + " " + assignedProfile.getLastName());
		pMail.setDaysCrossed(daysCrossed + "");
		pMail.setDateOfAction(PortalUtility.formatDateddMMyyyy(bonusReq.getReceivedOn()));
		pMail.setTemplateName(MailSettings.PERDIEM_ESC);
		String msg = mGenerator.replaceFields(mGenerator.getMailTemplate(MailSettings.BONUS_ESC), pMail);
		BonusReconciliationReq newBonusReq = new BonusReconciliationReq();
		newBonusReq.setBonusId(bonus.getId());
		newBonusReq.setSeqId(bonusReq.getSeqId());
		newBonusReq.setAssignedTo(escalUser.getId());
		newBonusReq.setReceivedOn(new Date());
		newBonusReq.setIsEscalated(bonusReq.getId());
		bonusReqDao.insert(newBonusReq);
		pMail.setMailBody(msg);
		pMail.setMailSubject("Bonus Report Escalation for the month " + pMail.getSerReqID());
		pMail.setFromMailId(null);
		pMail.setRecipientMailId(escalProfile.getOfficalEmailId());
		pMail.setAllReceipientcCMailId(new String[] { assignedProfile.getOfficalEmailId() });
		mGenerator.invoker(pMail);
		} catch (BonusReconciliationReqDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}