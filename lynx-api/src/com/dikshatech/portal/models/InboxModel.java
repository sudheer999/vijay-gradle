package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.Approval;
import com.dikshatech.beans.ExitNocInboxFlags;
import com.dikshatech.beans.HandlerAction;
import com.dikshatech.beans.IssueInboxInfo;
import com.dikshatech.beans.RequestHistory;
import com.dikshatech.beans.TimeSheetInboxInfo;
import com.dikshatech.beans.TravelRequest;
import com.dikshatech.beans.UserBean;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.RequestEscalation;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.CandidateReqDao;
import com.dikshatech.portal.dao.EmpSerReqHistoryDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.InsurancePolicyReqDao;
import com.dikshatech.portal.dao.IssueApproverChainReqDao;
import com.dikshatech.portal.dao.IssueHandlerChainReqDao;
import com.dikshatech.portal.dao.ItRequestDao;
import com.dikshatech.portal.dao.LeaveMasterDao;
import com.dikshatech.portal.dao.LoanDetailsDao;
import com.dikshatech.portal.dao.LoanRequestDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ReimbursementReqDao;
import com.dikshatech.portal.dao.RequestTypeDao;
import com.dikshatech.portal.dao.RequestedIssuesDao;
import com.dikshatech.portal.dao.RollOnDao;
import com.dikshatech.portal.dao.ServiceReqInfoDao;
import com.dikshatech.portal.dao.SodexoDetailsDao;
import com.dikshatech.portal.dao.SodexoReqDao;
import com.dikshatech.portal.dao.TimeSheetDetailsDao;
import com.dikshatech.portal.dao.TimesheetReqDao;
import com.dikshatech.portal.dao.TravelDao;
import com.dikshatech.portal.dao.TravelReqDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.CandidatePk;
import com.dikshatech.portal.dto.CandidateReq;
import com.dikshatech.portal.dto.DepPerdiemReport;
import com.dikshatech.portal.dto.EmpSerReqHistory;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.ExitEmployee;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.InsurancePolicyReq;
import com.dikshatech.portal.dto.IssueApproverChainReq;
import com.dikshatech.portal.dto.IssueHandlerChainReq;
import com.dikshatech.portal.dto.ItRequest;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.LoanDetails;
import com.dikshatech.portal.dto.LoanRequest;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.ReimbursementReq;
import com.dikshatech.portal.dto.RequestType;
import com.dikshatech.portal.dto.RequestedIssues;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.SalaryReconciliationReport;
import com.dikshatech.portal.dto.ServiceReqInfo;
import com.dikshatech.portal.dto.SodexoDetails;
import com.dikshatech.portal.dto.SodexoReq;
import com.dikshatech.portal.dto.TimeSheetDetails;
import com.dikshatech.portal.dto.TimesheetReq;
import com.dikshatech.portal.dto.Travel;
import com.dikshatech.portal.dto.TravelReq;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.CandidateDaoException;
import com.dikshatech.portal.exceptions.CandidateReqDaoException;
import com.dikshatech.portal.exceptions.DaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.IssueApproverChainReqDaoException;
import com.dikshatech.portal.exceptions.LoanRequestDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ReimbursementReqDaoException;
import com.dikshatech.portal.exceptions.RequestTypeDaoException;
import com.dikshatech.portal.exceptions.SodexoReqDaoException;
import com.dikshatech.portal.exceptions.TimesheetReqDaoException;
import com.dikshatech.portal.exceptions.TravelReqDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.CandidateReqDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReportDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqHistoryDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ExitEmployeeDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.InsurancePolicyReqDaoFactory;
import com.dikshatech.portal.factory.IssueApproverChainReqDaoFactory;
import com.dikshatech.portal.factory.IssueHandlerChainReqDaoFactory;
import com.dikshatech.portal.factory.ItRequestDaoFactory;
import com.dikshatech.portal.factory.LeaveMasterDaoFactory;
import com.dikshatech.portal.factory.LoanDetailsDaoFactory;
import com.dikshatech.portal.factory.LoanRequestDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ReimbursementReqDaoFactory;
import com.dikshatech.portal.factory.RequestTypeDaoFactory;
import com.dikshatech.portal.factory.RequestedIssuesDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.ServiceReqInfoDaoFactory;
import com.dikshatech.portal.factory.SodexoDetailsDaoFactory;
import com.dikshatech.portal.factory.SodexoReqDaoFactory;
import com.dikshatech.portal.factory.TimeSheetDetailsDaoFactory;
import com.dikshatech.portal.factory.TimesheetReqDaoFactory;
import com.dikshatech.portal.factory.TravelDaoFactory;
import com.dikshatech.portal.factory.TravelReqDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.InboxDaoImpl;
import com.dikshatech.portal.jdbc.ResourceManager;

enum ServiceRequestTypes {
	LEAVE, TRAVEL, LOAN, ISSUES, REIMBURSEMENT, MEDICAL_REIMBURSEMENT, MEDICAL_CLAIM, CANDIDATE, TIMESHEET, UNKNOWN, ROLLON, ITSUPPORT, SODEXO, PAYSLIPS, NOTIFICATION, APPRAISAL, EXIT, INSURANCE, PERDIEM_REPORT, PERDIEM_RECON, FBP, SALARY_RECON;

	public static ServiceRequestTypes getValue(String value) {
		try{
			return valueOf(value);
		} catch (Exception e){
			return UNKNOWN;
		}
	};
}

/**
 * @author veeranjaneyulu
 *
 */
public class InboxModel extends ActionMethods {

	private SimpleDateFormat	dateFormat				= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	private static Logger		logger					= LoggerUtil.getLogger(InboxModel.class);
	protected final String		SQL_RECEIVE_ALL_MAIL	= "SELECT * FROM INBOX ";
	protected final String		SQL_STATUS_SELECT		= "SELECT * FROM STATUS WHERE STATUS=?";
	private static final int	APPROVER				= 0;
	private static final int	NOTIFIER				= 1;
	private static final int	HANDLER					= 2;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		/*CandidateReqDao candiDao = CandidateReqDaoFactory.create();
		LeaveMasterDao leavDao = LeaveMasterDaoFactory.create();
		EmpSerReqMapDao empSerDao = EmpSerReqMapDaoFactory.create();
		Date showDt=null;*/
		Inbox inbox[] = null;
		List<Inbox> inboxList = new ArrayList<Inbox>();
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
			case GETDETAILS:
				try{
					inboxForm.setUnReadNotifications(ModelUtiility.getInstance().getUnreadNotifications(inboxForm.getReceiverId()) + "");
					inboxForm.setUnReadDocked(ModelUtiility.getInstance().getUnreadDocked(inboxForm.getReceiverId()) + "");
					Map<String, String> map = new HashMap<String, String>();
					map.put("unReadNotifications", inboxForm.getUnReadNotifications());
					map.put("unReadDocked", inboxForm.getUnReadDocked());
					request.setAttribute("actionForm", map);
				} catch (Exception e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("inbox.details.failed"));
				}
				break;
			case RECEIVEALL:{
				DropDown dropDownForm = new DropDown();
				try{
					//					inbox = inboxDao.findByDynamicWhere("IS_DELETED != 1 AND RECEIVER_ID = ?  AND RECEIVER_ID != ASSIGNED_TO", new Object[] { login.getUserId() });
					//					//inbox = inboxDao.findByDynamicWhere("IS_DELETED != 1 AND RECEIVER_ID = ?", new Object[] { login.getUserId() });
					//					dropDownForm.setDropDown(inbox);
					inboxList = new ArrayList(Arrays.asList(inboxDao.findByDynamicSelect(InboxDaoImpl.SQL_SELECT_WITHOUT_BODY + " WHERE IS_DELETED != 1 AND RECEIVER_ID = ?  AND RECEIVER_ID != ASSIGNED_TO", new Object[] { login.getUserId() })));
					Inbox[] tempInbox = inboxDao.findByDynamicSelect(InboxDaoImpl.SQL_SELECT_WITHOUT_BODY + " WHERE IS_DELETED != 1 AND RECEIVER_ID = ?  AND RECEIVER_ID = ASSIGNED_TO AND CATEGORY = 'CANDIDATE' AND STATUS != 'Pending Approval'", new Object[] { login.getUserId() });
					if (tempInbox != null){
						inboxList.addAll(new ArrayList(Arrays.asList(tempInbox)));
					}
					dropDownForm.setDropDown(inboxList.toArray());
				} catch (Exception e){
					e.printStackTrace();
				}
				request.setAttribute("actionForm", dropDownForm);
				break;
			}
			case RECEIVEDOCKED:{
				DropDown dropDownForm = new DropDown();
				try{
					inbox = inboxDao.findByDynamicSelect(InboxDaoImpl.SQL_SELECT_WITHOUT_BODY + " WHERE IS_DELETED != 1 AND RECEIVER_ID = ?  AND RECEIVER_ID = ASSIGNED_TO ", new Object[] { login.getUserId() });
					ArrayList<Inbox> categorizedInbox = new ArrayList<Inbox>();
					if (inbox != null){
						for (Iterator<Inbox> inboxIter = Arrays.asList(inbox).iterator(); inboxIter.hasNext();){
							Inbox eachInboxRow = inboxIter.next();
							switch (ActionMethods.CategoryTypes.getValue(eachInboxRow.getCategory())) {
								case TRAVEL:
								case SODEXO:
								case ITSUPPORT:
								case ISSUES:
									if (eachInboxRow.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || eachInboxRow.getStatus().equalsIgnoreCase(Status.REOPENED)){
										eachInboxRow.setAssignedToName(null);
									} else{
										String assignedToName = ProfileInfoDaoFactory.create().findByPrimaryKey(UsersDaoFactory.create().findByPrimaryKey(eachInboxRow.getAssignedTo()).getProfileId()).getFirstName();
										eachInboxRow.setAssignedToName(assignedToName);
									}
									categorizedInbox.add(eachInboxRow);
									break;
								case CANDIDATE:
									if (eachInboxRow.getStatus().equalsIgnoreCase(Status.PENDINGAPPROVAL)){
										categorizedInbox.add(eachInboxRow);
									}
									break;
								case REIMBURSEMENT:
									if (eachInboxRow.getStatus().equalsIgnoreCase(Status.REQUESTRAISED)){
										eachInboxRow.setAssignedToName(null);
									} else{
										ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
										ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
										ReimbursementReq dockedBy[] = new ReimbursementReq[1];
										dockedBy = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN" + "(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ACTIVE IS NULL AND STATUS IN( '" + Status.ASSIGNED + "','In-Progress','Completed','Cancel Request') AND ESR_MAP_ID= ?" + " ORDER BY ACTION_TAKEN_ON DESC )", new Object[] { new Integer(eachInboxRow.getEsrMapId()) });
										ProfileInfo dockedByProfile[] = null;
										if (dockedBy != null && dockedBy.length > 0){
											dockedByProfile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(dockedBy[0].getAssignTo()) });
											eachInboxRow.setStatus(eachInboxRow.getStatus().equalsIgnoreCase(Status.ASSIGNED) || (dockedBy[0].getStatus().equalsIgnoreCase(Status.CANCELREQUEST) && dockedBy[0].getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST)) ? "Docked By " + dockedByProfile[0].getFirstName() + " " + dockedByProfile[0].getLastName() : eachInboxRow.getStatus());
										}
										String assignedToName = ProfileInfoDaoFactory.create().findByPrimaryKey(UsersDaoFactory.create().findByPrimaryKey(eachInboxRow.getAssignedTo()).getProfileId()).getFirstName();
										eachInboxRow.setAssignedToName(assignedToName);
									}
									categorizedInbox.add(eachInboxRow);
									break;
								/*case INSURANCE:
									break;*/
								default:
									categorizedInbox.add(eachInboxRow);
									break;
							}
						}
					}
					dropDownForm.setDropDown(categorizedInbox.toArray(new Inbox[categorizedInbox.size()]));
				} catch (Exception e){
					e.printStackTrace();
				}
				request.setAttribute("actionForm", dropDownForm);
				break;
			}
			/**
			 * Modified By Gurunath.rokkam On 07-06-2013 11:00 PM.
			 * setting only subject to response instead of sending total inbox object with template.
			 */
			case RECEIVEMYTASKS:{
				HashMap<String, List<?>> map = new HashMap<String, List<?>>();
				try{
					map.put("List", JDBCUtiility.getInstance().getSingleColumn("SELECT SUBJECT FROM INBOX WHERE RECEIVER_ID=? AND RECEIVER_ID = ASSIGNED_TO ORDER BY ID DESC LIMIT 0,7", new Object[] { inboxForm.getReceiverId() }));
				} catch (Exception e){
					e.printStackTrace();
				}
				request.setAttribute("actionForm", map);
				break;
			}
			case RECEIVE:{
				DropDown dropDownForm = new DropDown();
				//				int assignId = 0;
				//				try {
				//					assignId = inboxDao.findByDynamicWhere(" ESR_MAP_ID = '" + inboxForm.getEsrMapId() + "' AND IS_ESCALATED = '1' ", null)[0].getAssignedTo();
				//				} catch (InboxDaoException e) {
				//					e.printStackTrace();
				//				}
				switch (ServiceRequestTypes.getValue(inboxForm.getCategory())) {
					case CANDIDATE:{
						getCandidateReqDetail(form, request, dropDownForm);
						//						dropDownForm.setCandidateId(assignId);
						break;
					}
					case LEAVE:{
						getLeaveReqDetail(form, request, dropDownForm);
						break;
					}
					case TRAVEL:{
						getTravelReqDetail(form, request, dropDownForm);
						break;
					}
					case LOAN:{
						getLoanRequestDetail(form, request, dropDownForm);
						break;
					}
					case REIMBURSEMENT:{
						getReimbursementDetail(form, request, dropDownForm);
						break;
					}
					case MEDICAL_REIMBURSEMENT:{
						break;
					}
					case MEDICAL_CLAIM:{
						break;
					}
					case TIMESHEET:{
						getTimeSheetReqDetail(form, request, dropDownForm);
						break;
					}
					case ROLLON:{
						getRollOnReqDetail(form, request, dropDownForm);
						break;
					}
					case ISSUES:{
						getIssueReqDetail(form, request, dropDownForm);
						break;
					}
					case ITSUPPORT:{
						getItReqDetail(form, request, dropDownForm);
						break;
					}
					case SODEXO:
						getSodexoDetail(form, request, dropDownForm);
						break;
					case PAYSLIPS:
					case NOTIFICATION:
						getInboxDetail(form, request, dropDownForm);
						break;
					case APPRAISAL:
						getAppraisalDetail(form, request, dropDownForm);
						break;
					case EXIT:
						getExitDetail(form, request, dropDownForm);
						break;
					case INSURANCE:
						getInsuranceDetail(form, request, dropDownForm);
						break;
					case PERDIEM_REPORT:
						getPerdiemDetails(form, request, dropDownForm);
						break;
					case PERDIEM_RECON:
						getPerdiemReconDetails(form, request, dropDownForm);
						break;
					case SALARY_RECON:
						getSalaryReconDetails(form, request, dropDownForm);
						break;
					case FBP:{
						getFbpRecord(form, request, dropDownForm);
						break;
					}
					default:
						getAppraisalDetail(inboxForm, request, dropDownForm);
						break;
				}
				request.setAttribute("actionForm", dropDownForm);
				break;
			}
			case RECEIVESIBLINGS:{
				DropDown dropDownForm = new DropDown();
				setSiblingUsersList(form, request, dropDownForm);
				request.setAttribute("actionForm", dropDownForm);
				break;
			}
			case RECEIVEREQHISTORY:
				DropDown dropDownForm = new DropDown();
				try{
					HashMap<String, String> usersnames = getusersMap(UsersDaoFactory.create().findAllUserNames("SELECT ASSIGNED_BY FROM EMP_SER_REQ_HISTORY UNION SELECT ASSIGNED_TO FROM EMP_SER_REQ_HISTORY", null));
					if (!usersnames.isEmpty()){
						int esrMapId = 0;
						if (inboxForm.getEsrMapId() == 0){
							Inbox inboxReq = InboxDaoFactory.create().findByPrimaryKey(inboxForm.getId());
							esrMapId = inboxReq.getEsrMapId();
						} else esrMapId = inboxForm.getEsrMapId();
						EmpSerReqHistory[] esrqHistory = EmpSerReqHistoryDaoFactory.create().findWhereEsrMapIdEquals(esrMapId);
						for (EmpSerReqHistory esrqh : esrqHistory){
							esrqh.setAssignedByName(usersnames.get(esrqh.getAssignedBy() + ""));
							esrqh.setAssignedToName(usersnames.get(esrqh.getAssignedTo() + ""));
						}
						dropDownForm.setDropDown(esrqHistory);
					}
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("actionForm", dropDownForm);
				break;
			default:
				break;
		}
		return result;
	}

	private void getSalaryReconDetails(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inbox = null;
		Inbox inboxForm = (Inbox) form;
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			inbox = InboxDaoFactory.create(conn).findByPrimaryKey(inboxForm.getId());
			String empId = inbox.getSubject().substring(inbox.getSubject().indexOf("(") + 1, inbox.getSubject().indexOf(")"));
			//DepPerdiem perdiem = DepPerdiemDaoFactory.create(conn).findWhereEsrMapIdEquals(inbox.getEsrMapId());
			SalaryReconciliationReport reports[] = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelect("SELECT SRR.* FROM SALARY_RECONCILIATION_REPORT SRR JOIN SALARY_RECONCILIATION SR ON SR.ID=SRR.SR_ID JOIN USERS U ON U.ID=SRR.USER_ID WHERE U.EMP_ID=? AND SR.ESR_MAP_ID=?", new Object[] { empId, inbox.getEsrMapId() });
			if (reports != null && reports.length > 0){
				SalaryReconciliationReport report = reports[0];
				if (report.getStatus() == SalaryReconciliationModel.HOLD && JDBCUtiility.getInstance().getRowCount(" FROM SALARY_RECONCILIATION_HOLD WHERE REP_ID=? AND USER_ID=?", new Object[] { report.getId(), inbox.getReceiverId() }, conn) > 0){
					dropDownForm.setKey1(report.getId());
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		dropDownForm.setDetail(inbox);
	}

	private void getPerdiemReconDetails(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inbox = null;
		Inbox inboxForm = (Inbox) form;
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			inbox = InboxDaoFactory.create(conn).findByPrimaryKey(inboxForm.getId());
			String empId = inbox.getSubject().substring(inbox.getSubject().indexOf("(") + 1, inbox.getSubject().indexOf(")"));
			//DepPerdiem perdiem = DepPerdiemDaoFactory.create(conn).findWhereEsrMapIdEquals(inbox.getEsrMapId());
			DepPerdiemReport reports[] = DepPerdiemReportDaoFactory.create(conn).findByDynamicSelect("SELECT DPR.* FROM DEP_PERDIEM_REPORT DPR JOIN DEP_PERDIEM DP ON DP.ID=DPR.DEP_ID JOIN USERS U ON U.ID=DPR.USER_ID WHERE U.EMP_ID=? AND DP.ESR_MAP_ID=?", new Object[] { empId, inbox.getEsrMapId() });
			if (reports != null && reports.length > 0){
				DepPerdiemReport report = reports[0];
				if (report.getType() == ReconciliationModel.HOLD && JDBCUtiility.getInstance().getRowCount(" FROM DEP_PERDIEM_HOLD D WHERE REP_ID=? AND USER_ID=?", new Object[] { report.getId(), inbox.getReceiverId() }, conn) > 0){
					dropDownForm.setKey1(report.getId());
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		dropDownForm.setDetail(inbox);
	}

	private void getFbpRecord(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inbox = null;
		Inbox inboxForm = (Inbox) form;
		try{
			inbox = InboxDaoFactory.create().findByPrimaryKey(inboxForm.getId());
			EmpSerReqMap[] esrMap = EmpSerReqMapDaoFactory.create().findByDynamicWhere("ID = (SELECT ESR_MAP_ID FROM INBOX WHERE ID = ?)", new Object[] { inboxForm.getId() });
			if (esrMap != null && esrMap.length == 1){
				ProfileInfo profileInfo = ProfileInfoDaoFactory.create().findWhereUserIdEquals(esrMap[0].getRequestorId());
				if (profileInfo != null) inbox.setSubmittedBy(profileInfo.getFirstName() + " " + profileInfo.getLastName());
				inbox.setSubmittedOn(PortalUtility.getdd_MM_yyyy(esrMap[0].getSubDate()));
			}
			dropDownForm.setDetail(inbox);
		} catch (DaoException e){
			logger.error("There was a DaoException occured while logged from the Inbox Model Recieve while receiving the Inbox details for the Id " + inboxForm.getId() + e.getMessage());
		}
	}

	private void getPerdiemDetails(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inbox = null;
		Inbox inboxForm = (Inbox) form;
		try{
			inbox = InboxDaoFactory.create().findByPrimaryKey(inboxForm.getId());
			inbox.setSubmittedOn(inbox.getCreatedOn());
			dropDownForm.setDetail(inbox);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	private void getInboxDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inboxForm = (Inbox) form;
		try{
			HandlerAction ha = new HandlerAction();
			ha.setComplete(1);
			Inbox inbox = InboxDaoFactory.create().findByPrimaryKey(inboxForm.getId());
			inbox.setHandleraction(ha);
			inbox.setSubmittedOn(inbox.getCreatedOn());
			inbox.setSubmittedBy("Admin");
			inbox.setRaisedByName("Admin");
			inbox.setCurrentStatus("Not Completed");
			dropDownForm.setDetail(inbox);
		} catch (InboxDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setSiblingUsersList(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		try{
			Inbox inboxForm = (Inbox) form;
			List<String> users = ModelUtiility.getInstance().getSiblingUsersList(inboxForm.getEsrMapId());
			if (users != null && users.size() > 0){
				UserBean[] userBeans = UsersDaoFactory.create().findAllUserNames(ModelUtiility.getCommaSeparetedValues(users), null);
				dropDownForm.setDropDown(userBeans);
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HashMap<String, String> getusersMap(UserBean[] findAllUserNames) {
		HashMap<String, String> usersMap = new HashMap<String, String>();
		for (UserBean user : findAllUserNames){
			usersMap.put(user.getId() + "", user.getFirstName() + " " + user.getLastName());
		}
		return usersMap;
	}

	private void getTravelReqDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		TravelReqDao travelReqDao = TravelReqDaoFactory.create();
		TravelDao travelDao = TravelDaoFactory.create();
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		EmpSerReqMapDao eDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao pChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		int loggedInUser = login.getUserId();
		try{
			Inbox inboxDto = inboxDao.findByPrimaryKey(inboxForm.getId());
			pChainDao.setMaxRows(1);
			Object[] pChainParams = { inboxDto.getEsrMapId() };
			String PC_SQL = "ID = (SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID = ?)";
			ProcessChain processChain = pChainDao.findByDynamicWhere(PC_SQL, pChainParams)[0];
			EmpSerReqMap eReqMap = eDao.findByPrimaryKey(inboxDto.getEsrMapId());
			TravelReq travelReq = null;
			if (inboxDto.getAssignedTo() > 0){
				Object[] trParams = { inboxDto.getEsrMapId(), inboxDto.getAssignedTo() };
				String TR_SQL = "TL_REQ_ID = (SELECT ID FROM TRAVEL WHERE ESRQM_ID = ?) AND ASSIGNED_TO = ? ORDER BY ID DESC";
				travelReqDao.setMaxRows(1);
				travelReq = travelReqDao.findByDynamicWhere(TR_SQL, trParams)[0];
			} else{
				travelReqDao.setMaxRows(1);
				travelReq = travelReqDao.findByDynamicWhere("TL_REQ_ID = (SELECT ID FROM TRAVEL WHERE ESRQM_ID = ?) ORDER BY ID DESC", new Object[] { inboxDto.getEsrMapId() })[0];
			}
			int appLevel = travelReq.getAppLevel();
			Integer[] handlers = pEvaluator.handlers(processChain.getHandler(), eReqMap.getRequestorId());
			Integer[] notifiers = pEvaluator.notifiers(processChain.getNotification(), eReqMap.getRequestorId());
			Integer[] approvers = pEvaluator.approvers(processChain.getApprovalChain(), appLevel, eReqMap.getRequestorId());
			Integer[] result = checkLoggedInUser(approvers, notifiers, handlers, loggedInUser);
			TravelRequest travel = travelDao.findTravelId(new Object[] { travelReq.getTlReqId() });
			/**
			 * check request if status is Accepted then enable/disable assign
			 * option, else if status is On Hold, Under Review or Rejected then
			 * Accept/Reject option will be shown based on the actions taken by
			 * approver
			 */
			HandlerAction handlerAction = new HandlerAction();
			Inbox inbox = inboxDao.findByPrimaryKey(inboxDto.getId());
			inbox.setCurrentStatus(travel.getStatusName());
			if (result[HANDLER] != null && (travelReq.getStatus() == Status.getStatusId(Status.WORKINPROGRESS) || travelReq.getStatus() == Status.getStatusId(Status.REQUESTRAISED)) && travelReq.getAppLevel() == 0){
				TravelReq travReq[] = travelReqDao.findByDynamicSelect("SELECT * from TRAVEL_REQ WHERE TL_REQ_ID=? AND ACTION_TYPE!=0 ORDER BY DATE_OF_COMPLETION", new Object[] { travel.getTravelId() });
				if (travReq != null && travReq.length > 0){
					Users users = usersDao.findByPrimaryKey(travReq[0].getAssignedTo());
					ProfileInfo profileinfo = profileInfoDao.findByPrimaryKey(users.getProfileId());
					inbox.setApprovedBy(profileinfo.getFirstName() + " " + profileinfo.getLastName());
					inbox.setApprovedOn(travReq[0].getDateOfCompletion());
					dropDownForm.setKey1(1);
				} else{
					dropDownForm.setKey1(1);
				}
			}
			if (result[NOTIFIER] != null){
				TravelReq travReq[] = travelReqDao.findByDynamicSelect("SELECT * from TRAVEL_REQ WHERE TL_REQ_ID=? ORDER BY DATE_OF_COMPLETION DESC", new Object[] { travel.getTravelId() });
				if (travReq[0].getActionType() != 0){
					Users users = usersDao.findByPrimaryKey(travReq[0].getAssignedTo());
					ProfileInfo profileinfo = profileInfoDao.findByPrimaryKey(users.getProfileId());
					if (travReq[0].getActionType() == Status.getStatusId(Status.WORKINPROGRESS) //need to change to workinprogress
							|| travReq[0].getActionType() == Status.getStatusId(Status.PROCESSED)) //need to change to processed
					{
						inbox.setApprovedBy(profileinfo.getFirstName() + " " + profileinfo.getLastName());
						inbox.setApprovedOn(travReq[0].getDateOfCompletion());
					} else if (travReq[0].getActionType() == Status.getStatusId(Status.REJECTEDBYAPPROVER)){
						inbox.setRejectedBy(profileinfo.getFirstName() + " " + profileinfo.getLastName());
						inbox.setRejectedOn(travReq[0].getDateOfCompletion());
					}
				}
			}
			Users users = usersDao.findByPrimaryKey(travel.getRaisedBy());
			ProfileInfo profileinfo = profileInfoDao.findByPrimaryKey(users.getProfileId());
			inbox.setSubmittedBy(profileinfo.getFirstName() + " " + profileinfo.getLastName());
			//Date date = PortalUtility.StringToDate(travel.getCreatedDate());
			//Date date = PortalUtility.fromStringToDateDDMMYYYYHHMMSS(travel.getCreatedDate());
			if (inboxDto.getAssignedTo() == login.getUserId()){
				if (!(inboxDto.getStatus().equals(Status.REVOKED) || inboxDto.getStatus().equals(Status.PROCESSED) || inboxDto.getStatus().equals(Status.ACCEPTED) || inboxDto.getStatus().equals(Status.REJECTEDBYAPPROVER))){
					if (!(travelDao.findWhereEsrqmIdEquals(inboxDto.getEsrMapId())[0].getStatus() == Status.getStatusId(Status.REVOKED))){
						handlerAction.setAssign(1);
						handlerAction.setAssignButtonForHandlerOrApprover(1);//handler only
						//handling the button visibility in docking station for approvers
						Travel travelDetails = travelDao.findWhereEsrqmIdEquals(inboxDto.getEsrMapId())[0];
						TravelReq[] travelReq2 = travelReqDao.findWhereTlReqIdEquals(travelDetails.getId());
						if (!(result[HANDLER] != null && (travelReq.getStatus() == Status.getStatusId(Status.WORKINPROGRESS) || travelReq.getStatus() == Status.getStatusId(Status.REQUESTRAISED)) && travelReq.getAppLevel() == 0)) if (travelReq2[travelReq2.length - 1].getAppLevel() > 0){
							handlerAction.setApprove(1);
							handlerAction.setReject(1);
							handlerAction.setAssignButtonForHandlerOrApprover(2);//approver only
						}
					}
				}
			}
			inbox.setHandleraction(handlerAction);
			inbox.setSubmittedOn(travel.getCreatedDate());
			inbox.setTravelReqId(travelReq.getTlReqId());//**
			dropDownForm.setDetail(inbox);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private Integer[] checkLoggedInUser(Integer[] approvers, Integer[] notifiers, Integer[] handlers, int loggedInUser) {
		Integer[] result = new Integer[3];
		for (int approver : approvers){
			if (approver == loggedInUser){
				result[APPROVER] = loggedInUser;
				break;
			}
		}
		for (int notifier : notifiers){
			if (notifier == loggedInUser){
				result[NOTIFIER] = loggedInUser;
				break;
			}
		}
		for (int handler : handlers){
			if (handler == loggedInUser){
				result[HANDLER] = loggedInUser;
				break;
			}
		}
		return result;
	}

	private void getRollOnReqDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		try{
			Inbox inbox = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
			inbox.setCurrentStatus(inbox.getStatus());
			inbox.setOfferMadeOn(dateFormat.format(inbox.getCreationDatetime()));
			ProfileInfo dto = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(inbox.getRaisedBy()) })[0];
			inbox.setOfferMadeBy(dto.getFirstName() + " " + dto.getLastName());
			dropDownForm.setDetail(inbox);
		} catch (InboxDaoException e){
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void getTimeSheetReqDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		UsersDao usersDao = UsersDaoFactory.create();
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		//StatusDao statusDao = StatusDaoFactory.create();
		TimesheetReqDao timesheetReqDao = TimesheetReqDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		TimeSheetDetailsDao timeSheetdetailDao = TimeSheetDetailsDaoFactory.create();
		String sql = "SELECT * FROM TIMESHEET_REQ WHERE ID=(SELECT MAX(ID) FROM TIMESHEET_REQ WHERE ESRQM_ID=? AND ASSIGNED_TO=? GROUP BY ESRQM_ID)";
		String tsheet = "SELECT * FROM TIME_SHEET_DETAILS WHERE ID=(SELECT MAX(TSHEET_ID) FROM TIMESHEET_REQ WHERE ESRQM_ID=? )";
		try{
			TimesheetReq[] timeSheetReqArr = null;
			Inbox inbox = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
			TimeSheetInboxInfo timeSheetInboxBean = new TimeSheetInboxInfo();
			TimeSheetDetails[] timeSheetDtoArr = timeSheetdetailDao.findByDynamicSelect(tsheet, new Object[] { new Integer(inbox.getEsrMapId()) });
			TimeSheetDetails timeSheetDto = null;
			if (timeSheetDtoArr != null && timeSheetDtoArr.length > 0){
				timeSheetDto = timeSheetDtoArr[0];
				Users tsRequestor = usersDao.findByPrimaryKey(timeSheetDto.getUserId());
				ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(tsRequestor.getProfileId());
				timeSheetInboxBean.setSubmittedBy(profileInfo.getFirstName());
				timeSheetInboxBean.setSubmittedOn(dateFormat.format(timeSheetDto.getSubmissionDate()));
				timeSheetInboxBean.setTimeSheetId(timeSheetDto.getId() + "");
			}
			if (timeSheetDto != null && timeSheetDto.getUserId().intValue() == login.getUserId().intValue()) timeSheetReqArr = timesheetReqDao.findByDynamicSelect(sql.replace("AND ASSIGNED_TO=?", ""), new Object[] { inboxForm.getEsrMapId() });
			else timeSheetReqArr = timesheetReqDao.findByDynamicSelect(sql, new Object[] { inboxForm.getEsrMapId(), login.getUserId() });
			TimesheetReq timeSheetReqDto = null;
			if (timeSheetReqArr.length > 0 && timeSheetReqArr[0] != null) timeSheetReqDto = timeSheetReqArr[0];
			else{
				inbox.setTimeSheetInboxInfo(timeSheetInboxBean);
				inbox.setCurrentStatus(inbox.getStatus());
				dropDownForm.setDetail(inbox);
				return;
			}
			String currentStatus = timeSheetReqDto.getStatus();
			HandlerAction handlerAction = null;
			if (timeSheetReqDto.getStatus().equalsIgnoreCase(Status.APPROVED)){
				ProfileInfo profileInfoDto = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE ID= (SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { timeSheetReqDto.getActionBy() })[0];
				timeSheetInboxBean.setApprovedOn(dateFormat.format(timeSheetReqDto.getCreatedatetime()));
				timeSheetInboxBean.setApprovedBy(profileInfoDto.getFirstName());
				handlerAction = new HandlerAction();
			} else if (timeSheetReqDto.getStatus().equalsIgnoreCase(Status.REJECTED)){
				ProfileInfo profileInfoDto = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE ID= (SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { timeSheetReqDto.getActionBy() })[0];
				timeSheetInboxBean.setRejectedOn(dateFormat.format(timeSheetReqDto.getCreatedatetime()));
				timeSheetInboxBean.setRejectedBy(profileInfoDto.getFirstName());
				handlerAction = new HandlerAction();
			} else if (timeSheetReqDto.getStatus().equalsIgnoreCase(Status.ON_HOLD)){
				ProfileInfo profileInfoDto = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE ID= (SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { timeSheetReqDto.getActionBy() })[0];
				timeSheetInboxBean.setHoldOn(dateFormat.format(timeSheetReqDto.getCreatedatetime()));
				timeSheetInboxBean.setHoldBy(profileInfoDto.getFirstName());
			}
			/*Status[] status = statusDao.findByDynamicSelect(SQL_STATUS_SELECT, new Object[] { inbox.getStatus() });
			if (status != null && status.length > 0){
				inbox.setStatusId(status);
			}*/
			inbox.setStatusId(Status.getStatusId(inbox.getStatus()));
			if (currentStatus != null){
				inbox.setCurrentStatus(currentStatus);
			} else{
				inbox.setCurrentStatus(inbox.getStatus());
			}
			if (timeSheetReqDto.getAssignedTo() == login.getUserId()){
				if (currentStatus.equalsIgnoreCase(Status.APPROVED) || currentStatus.equalsIgnoreCase(Status.REJECTED) || currentStatus.equalsIgnoreCase(Status.CANCELLED)){
					handlerAction = new HandlerAction();
				} else{
					if (ModelUtiility.getInstance().isValidApprover(timeSheetReqDto.getEsrqmId(), login.getUserId())){
						handlerAction = new HandlerAction();
						/*if (ModelUtiility.getInstance().isAssignedApprover(timeSheetReqDto.getEsrqmId(), login.getUserId())){
							if (!currentStatus.equalsIgnoreCase(Status.ON_HOLD)) handlerAction.setOnHold(1);
							handlerAction.setApprove(1);
							handlerAction.setReject(1);
						}*/
						handlerAction.setAssign(1);
					}
				}
			} else{
				handlerAction = new HandlerAction();
			}
			inbox.setHandlerActionBean(handlerAction);
			inbox.setTimeSheetInboxInfo(timeSheetInboxBean);
			dropDownForm.setDetail(inbox);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void getCandidateReqDetail(ActionForm form, HttpServletRequest request, DropDown dropDownForm) {
		CandidateReqDao canReqDao = CandidateReqDaoFactory.create();
		CandidateDao candidateDao = CandidateDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		EmpSerReqMapDao eDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao pChainDao = ProcessChainDaoFactory.create();
		//StatusDao statusDao = StatusDaoFactory.create();
		int candidateId = 0;
		try{
			String sql = "SELECT * FROM CANDIDATE_REQ WHERE ESRQM_ID=?  ORDER BY CREATEDATETIME DESC";
			CandidateReq[] canReq = canReqDao.findByDynamicSelect(sql, new Object[] { inboxForm.getEsrMapId() });
			Map<Integer, java.util.List<CandidateReq>> candidateHistoryByCycle = populateMapFromCandidateHistory(canReq);
			String candReqStatus = null;
			if (canReq != null && canReq.length > 0){
				candReqStatus = canReq[0].getStatus();
				RequestHistory[] histories = new RequestHistory[candidateHistoryByCycle.keySet().size()];
				Set<Integer> cycles = candidateHistoryByCycle.keySet();
				int idx = 0;
				for (Integer cycle : cycles){
					RequestHistory requestHistory = new RequestHistory();
					java.util.List<CandidateReq> candidateReqs = candidateHistoryByCycle.get(cycle);
					int index = 0;
					Approval[] approvals = null;
					for (CandidateReq candidateReq : candidateReqs){
						if (candidateReq.getStatus().equalsIgnoreCase(Status.OFFERSENT)){
							requestHistory.setOfferMadeOn(dateFormat.format(candidateReq.getCreatedatetime()));
							requestHistory.setOfferMadeBy(profileDao.findByPrimaryKey(new ProfileInfoPk(usersDao.findByPrimaryKey(candidateReq.getAssignedTo()).getProfileId())).getFirstName());
						} else if (candidateReq.getStatus().equalsIgnoreCase(Status.REJECTED)){
							requestHistory.setRejectedOn(dateFormat.format(candidateReq.getCreatedatetime()));
						} else if (candidateReq.getStatus().equalsIgnoreCase(Status.ACCEPTED)){
							requestHistory.setAcceptedOn(dateFormat.format(candidateReq.getCreatedatetime()));
						} else if (candidateReq.getStatus().equalsIgnoreCase(Status.OFFERRESENT)){
							requestHistory.setReOfferOn(dateFormat.format(candidateReq.getCreatedatetime()));
							requestHistory.setReOfferMadeBy(profileDao.findByPrimaryKey(new ProfileInfoPk(usersDao.findByPrimaryKey(candidateReq.getAssignedTo()).getProfileId())).getFirstName());
						} else if (candidateReq.getCycle() == 1 && candidateReq.getStatus().equalsIgnoreCase(Status.OFFERUNDERREVIEW)){
							if (candidateReq.getActionTaken() == Status.AT_APPROVED){
								if (index == 0){
									approvals = new Approval[index + 1];
								} else{
									Object[] objects = approvals;
									approvals = new Approval[index + 1];
									approvals = (Approval[]) new PortalUtility().copyArray(objects, approvals);
								}
								Approval approval = new Approval();
								String approvedOn = dateFormat.format(candidateReq.getCreatedatetime());
								String approvedBy = profileDao.findByPrimaryKey(new ProfileInfoPk(usersDao.findByPrimaryKey(candidateReq.getAssignedTo()).getProfileId())).getFirstName();
								approval.setApprovedBy(approvedBy);
								approval.setApprovedOn(approvedOn);
								approvals[index] = approval;
								index++;
							}
							//							/**
							//							 * if approver then show him status as Offer for Approval instead of under review
							//							 */
							//							EmpSerReqMap eMap = eDao.findByPrimaryKey( candidateReq.getEsrqmId());
							//							ProcessChain processChain = pChainDao.findByPrimaryKey(eMap.getProcessChainId());
							//							
							//							if(login.getUserId()==candidateReq.getRaisedBy());
						} else if (candidateReq.getCycle() != 1 && candidateReq.getStatus().equalsIgnoreCase(Status.OFFERNOTAPPROVED)){
							requestHistory.setReOfferApprovedOn(dateFormat.format(candidateReq.getCreatedatetime()));
							requestHistory.setReOfferApprovedBy(profileDao.findByPrimaryKey(new ProfileInfoPk(usersDao.findByPrimaryKey(candidateReq.getAssignedTo()).getProfileId())).getFirstName());
						} else if (candidateReq.getCycle() == 1 && candidateReq.getStatus().equalsIgnoreCase(Status.OFFERAPPROVED)){
							if (candidateReq.getActionTaken() == Status.AT_APPROVED){
								if (index == 0){
									approvals = new Approval[index + 1];
								} else{
									Object[] objects = approvals;
									approvals = new Approval[index + 1];
									approvals = (Approval[]) new PortalUtility().copyArray(objects, approvals);
								}
								Approval approval = new Approval();
								String approvedOn = dateFormat.format(candidateReq.getCreatedatetime());
								String approvedBy = profileDao.findByPrimaryKey(new ProfileInfoPk(usersDao.findByPrimaryKey(candidateReq.getAssignedTo()).getProfileId())).getFirstName();
								approval.setApprovedBy(approvedBy);
								approval.setApprovedOn(approvedOn);
								approvals[index] = approval;
								index++;
							}
						}
						candidateId = candidateReq.getCandidateId();
					}
					if (approvals != null){
						requestHistory.setApprovals(approvals);
					}
					histories[idx++] = requestHistory;
					dropDownForm.setCandidateId(candidateId);
				}
				dropDownForm.setDropDown(histories);
				Candidate candidate = candidateDao.findByPrimaryKey(new CandidatePk(candidateId));
				Inbox inbox = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
				inbox.setCandidateId(candidate.getId());
				inbox.setCandidateName(profileDao.findByPrimaryKey(candidate.getProfileId()).getFirstName());
				/*Status[] status = statusDao.findByDynamicSelect(SQL_STATUS_SELECT, new Object[] { inbox.getStatus() });
				if (status != null && status.length > 0){
					inbox.setStatusId(status[0].getId());
				}*/
				inbox.setStatusId(Status.getStatusId(inbox.getStatus()));
				if (candReqStatus != null){
					inbox.setCurrentStatus(candReqStatus);
				} else{
					inbox.setCurrentStatus(inbox.getStatus());
				}
				inbox.setOfferMadeOn(dateFormat.format(candidate.getCreateDate()));
				inbox.setOfferMadeBy(profileDao.findByPrimaryKey(new ProfileInfoPk(usersDao.findByPrimaryKey(inbox.getRaisedBy()).getProfileId())).getFirstName());
				if (inbox.getStatus().equalsIgnoreCase("Accepted")){
					inbox.setAcceptedOn(dateFormat.format(inbox.getCreationDatetime()));
				}
				EmpSerReqMap eMap = eDao.findByPrimaryKey(inbox.getEsrMapId());
				ProcessChain pChain = pChainDao.findByPrimaryKey(eMap.getProcessChainId());
				if (candidate.getIsEmployee() != 1){
					ProcessEvaluator pEvaluator = new ProcessEvaluator();
					/*
					 * HandlerAction handlerAction =
					 * populateHandlerActionFormPC(pChain, login.getUserId(),
					 * inbox.getCurrentStatus());
					 */
					int approvalLevel = pEvaluator.findLastAppLevel(eMap);
					if (approvalLevel == 0){
						approvalLevel = 1;
					}
					HandlerAction handlerAction = populateHandlerActionormPC(pChain, login.getUserId(), inbox.getCurrentStatus(), approvalLevel, inbox.getRaisedBy());
					if (login.getUserId() == inbox.getAssignedTo()){
						Object[] sqlParams = { inbox.getEsrMapId(), inbox.getStatus(), login.getUserId() };
						CandidateReq[] candidateReq = canReqDao.findByDynamicWhere("ESRQM_ID = ? AND STATUS = ? AND ASSIGNED_TO = ? AND SERVED > 0", sqlParams);
						if (candidateReq != null && candidateReq.length > 0 && handlerAction.getApprove() == 1){
							handlerAction.setApprove(1);
							handlerAction.setReject(1);
						} else{
							handlerAction.setApprove(0);
							handlerAction.setReject(0);
						}
					} else{
						handlerAction.setApprove(0);
						handlerAction.setReject(0);
					}
					inbox.setHandlerActionBean(handlerAction);
				}
				inbox.setStatusId(Status.getStatusId(inbox.getCurrentStatus()));
				dropDownForm.setDetail(inbox);
			} else{
				JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { inboxForm.getEsrMapId() });
			}
		} catch (CandidateReqDaoException e){
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			e.printStackTrace();
		} catch (UsersDaoException e){
			e.printStackTrace();
		} catch (CandidateDaoException e){
			e.printStackTrace();
		} catch (InboxDaoException e){
			e.printStackTrace();
		} catch (ProcessChainDaoException e){
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<Integer, java.util.List<CandidateReq>> populateMapFromCandidateHistory(CandidateReq[] canReq) {
		Map<Integer, java.util.List<CandidateReq>> candidateReqByCycle = new HashMap<Integer, java.util.List<CandidateReq>>();
		for (CandidateReq candidateReq : canReq){
			if (candidateReqByCycle.containsKey(candidateReq.getCycle())){
				candidateReqByCycle.get(candidateReq.getCycle()).add(candidateReq);
			} else{
				java.util.List<CandidateReq> candidateReqs = new ArrayList<CandidateReq>();
				candidateReqs.add(candidateReq);
				candidateReqByCycle.put(candidateReq.getCycle(), candidateReqs);
			}
		}
		return candidateReqByCycle;
	}

	private HandlerAction populateHandlerActionormPC(ProcessChain processChain, int userId, String status, int appLevel, int raisedBy) {
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		boolean isInApproval = false;
		boolean isInHandlerAction = false;
		HandlerAction handlerAction = new HandlerAction();
		if (processChain.getApprovalChain() != null) for (Integer approver : pEvaluator.approvers(processChain.getApprovalChain(), appLevel, raisedBy)){
			if (userId == approver){
				isInApproval = true;
				break;
			}
		}
		if (processChain.getHandler() != null){
			for (Integer handler : pEvaluator.handlers(processChain.getHandler(), raisedBy)){
				if (handler == userId){
					isInHandlerAction = true;
					break;
				}
			}
		}
		if (status.equalsIgnoreCase("ACCEPTED")){
			handlerAction.setViewOffer(1);
			if (isInHandlerAction){
				handlerAction.setReSend(1);
			}
		}
		if (status.equalsIgnoreCase("REJECTED")){
			handlerAction.setViewOffer(1);
			if (isInHandlerAction) handlerAction.setReSend(1);
		}
		if (status.equalsIgnoreCase("RE-OFFERED")){
			if (isInHandlerAction) handlerAction.setReSend(1);
			handlerAction.setViewOffer(1);
		}
		if (status.equalsIgnoreCase("PENDING APPROVAL")){
			handlerAction.setViewOffer(1);
			if (isInApproval){
				handlerAction.setApprove(1);
				handlerAction.setReject(1);
			}
		}
		if (status.equalsIgnoreCase("OFFER ACCEPTED")){
			handlerAction.setViewOffer(1);
		}
		if (status.equalsIgnoreCase("OFFER REJECTED")){
			handlerAction.setViewOffer(1);
			if (isInHandlerAction) handlerAction.setReSend(1);
		}
		if (status.equalsIgnoreCase("OFFER SENT")){
			handlerAction.setViewOffer(1);
		}
		if (status.equalsIgnoreCase("OFFER APPROVED")){
			handlerAction.setViewOffer(1);
			/*
			 * if (isInHandlerAction) { handlerAction.setReSend(1); }
			 */
		}
		return handlerAction;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		switch (ActionMethods.SaveTypes.getValue(form.getrType())) {
			case ASSIGN:
				boolean isUpdated = false;
				try{
					if (inboxForm.getAssignedTo() > 0 && inboxForm.getEsrMapId() > 0){
						EmpSerReqHistoryDao esrhDao = EmpSerReqHistoryDaoFactory.create();
						EmpSerReqHistory esrHistory = esrhDao.findWhereEsrMapIdEqualsMax(inboxForm.getEsrMapId());
						if (esrHistory != null){
							if (esrHistory.getAssignedTo() != login.getUserId().intValue()){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
								return result;
							} else if (esrHistory.getAssignedTo() == inboxForm.getAssignedTo()) return result;
						}
						List<String> users = ModelUtiility.getInstance().getSiblingUsersList(inboxForm.getEsrMapId());
						if (users != null && users.size() > 0){
							if (users.contains(inboxForm.getAssignedTo() + "") && users.contains(login.getUserId().intValue() + "")){
								EmpSerReqHistory esrqh = new EmpSerReqHistory(inboxForm.getEsrMapId(), new Date(), inboxForm.getAssignedTo(), login.getUserId().intValue(), inboxForm.getComments());
								esrhDao.insert(esrqh);
								JDBCUtiility.getInstance().update("UPDATE INBOX SET STATUS= CONCAT_WS(' ','Docked By ',(SELECT FIRST_NAME FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID= ?))), IS_READ=0 WHERE ESR_MAP_ID=? AND ASSIGNED_TO IN " + (users.toString().replace("[", "(").replace("]", ")")), new Object[] { inboxForm.getAssignedTo(), inboxForm.getEsrMapId() });
								isUpdated = true;
							}
						}
					}
				} catch (Exception e){
					logger.error("Error while assignig to sibling", e);
				}
				if (!isUpdated) result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
				break;
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		InboxDao iDao = InboxDaoFactory.create();
		Inbox inbox = (Inbox) form;
		switch (UpdateTypes.getValue(form.getuType())) {
			case MARKREAD:
				try{
					Inbox inbox2 = iDao.findByPrimaryKey(inbox.getId());
					if (inbox2 != null){
						inbox2.setIsRead(inbox.getIsRead());
						InboxPk inboxPk = new InboxPk();
						inboxPk.setId(inbox2.getId());
						iDao.update(inboxPk, inbox2);
						inbox.setUnReadNotifications(ModelUtiility.getInstance().getUnreadNotifications(inbox2.getReceiverId()) + "");
						inbox.setUnReadDocked(ModelUtiility.getInstance().getUnreadDocked(inbox2.getReceiverId()) + "");
					} else{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("inbox.markread.invaild"));
					}
				} catch (InboxDaoException e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("inbox.markread.failed"));
				}
				break;
			case UPDATE:
				try{
					Login login = Login.getLogin(request);
					if (JDBCUtiility.getInstance().getRowCount("FROM INBOX WHERE ESR_MAP_ID = ? AND ASSIGNED_TO = ? ", new Object[] { inbox.getEsrMapId(), login.getUserId().intValue() }) > 0){
						JDBCUtiility.getInstance().update("UPDATE INBOX SET STATUS='COMPLETED BY " + login.getUserId().intValue() + "' WHERE ESR_MAP_ID= ?", new Object[] { inbox.getEsrMapId() });
						JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID= ?", new Object[] { inbox.getEsrMapId() });
					} else{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
					}
				} catch (Exception e){
					e.printStackTrace();
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
				}
				break;
			default:
				break;
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		return null;
	}

	/**
	 * This method used to send the request to docking station/inbox
	 * 
	 * @param esrMapId
	 *            :Employee Service Request Map Id
	 * @param reqId
	 *            :Specific request id eg(candidate_reqId,Travel_Id etc..)
	 */
	public Inbox sendToDockingStation(int esrMapId, int reqId, String subject, String status) {
		EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
		RequestTypeDao reTypeDao = RequestTypeDaoFactory.create();
		ProcessChainDao pDao = ProcessChainDaoFactory.create();
		Inbox inbox = null;
		try{
			EmpSerReqMap esrMap = esrMapDao.findByPrimaryKey(new EmpSerReqMapPk(esrMapId));
			RequestType reqType = reTypeDao.findByPrimaryKey(esrMap.getReqTypeId());
			ProcessChain pChain = pDao.findByPrimaryKey(esrMap.getProcessChainId());
			switch (ServiceRequestTypes.getValue(reqType.getName())) {
				case CANDIDATE:{
					inbox = populateInboxFromCandidate(esrMapId, reqId, subject, status);
					break;
				}
				case LEAVE:{
					inbox = populateInboxFromLeave(esrMapId, reqId, subject, status);
					break;
				}
				case TRAVEL:{
					inbox = populateInboxFromTravel(esrMapId, reqId, subject, status);
					break;
				}
				case LOAN:{
					inbox = populateInboxFromLoan(esrMapId, reqId, subject, status);
					break;
				}
				case REIMBURSEMENT:{
					inbox = populateInboxFromReimbursement(esrMapId, reqId, subject, status);
					break;
				}
				case MEDICAL_REIMBURSEMENT:{
					break;
				}
				case MEDICAL_CLAIM:{
					break;
				}
				case TIMESHEET:{
					inbox = populateInboxFromTimeSheet(esrMapId, reqId, subject);
					break;
				}
				case ROLLON:{
					inbox = populateInboxFromRollOn(esrMapId, reqId, subject);
					break;
				}
				case ISSUES:{
					inbox = populateInboxFromIssues(esrMapId, reqId, subject);
					break;
				}
				case ITSUPPORT:{
					inbox = populateInboxFromITrequest(esrMapId, reqId, subject, status);
					break;
				}
				case SODEXO:
					inbox = populateInboxFromSodexorequest(esrMapId, reqId, subject, status);
					break;
				default:
					break;
			}
			inbox = sendToInbox(inbox, pChain.getNotification());
		} catch (EmpSerReqMapDaoException e){
			e.printStackTrace();
		} catch (RequestTypeDaoException e){
			e.printStackTrace();
		} catch (ProcessChainDaoException e){
			e.printStackTrace();
		}
		return inbox;
	}

	private Inbox populateInboxFromTravel(int esrMapId, int trvlreqId, String subject, String status) {
		Inbox inbox = new Inbox();
		TravelReqDao travelReqqDao = TravelReqDaoFactory.create();
		TravelDao travelDao = TravelDaoFactory.create();
		try{
			travelReqqDao.setMaxRows(1);
			TravelReq travelReq = travelReqqDao.findByTravel(trvlreqId)[0];
			Travel trvl = travelDao.findByPrimaryKey(travelReq.getTlReqId());
			inbox.setSubject(subject);
			inbox.setAssignedTo(travelReq.getAssignedTo());
			inbox.setRaisedBy(trvl.getRaisedBy());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("TRAVEL");
			inbox.setEsrMapId(esrMapId);
			inbox.setComments(travelReq.getComment());
			inbox.setMessageBody(travelReq.getMessageBody());
		} catch (TravelReqDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox;
	}

	/**
	 * This method is used to insert record into inbox table
	 * 
	 * @param inbox
	 *            :inbox details
	 * @param notify
	 *            :notify chain
	 */
	private Inbox sendToInbox(Inbox inbox, String notify) {
		try{
			InboxDao inboxDao = InboxDaoFactory.create();
			// send request to assigned person
			inbox.setReceiverId(inbox.getAssignedTo());
			InboxPk inboxPk = inboxDao.insert(inbox);
			inbox.setId(inboxPk.getId());
			/*
			 * if (notify != null && notify.length() > 0) { Integer[] users =
			 * new ProcessEvaluator().notifiers(notify, inbox.getRaisedBy());
			 * for (int user : users) { if (inbox.getAssignedTo() != user) {
			 * inbox.setReceiverId(user); inboxDao.insert(inbox); } } }
			 */
		} catch (InboxDaoException e){
			e.printStackTrace();
		}
		return inbox;
	}

	public void notify(int empSerId, Inbox inbox) {
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		EmpSerReqMapDao eDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao pDao = ProcessChainDaoFactory.create();
		try{
			Object[] sqlParams = { empSerId };
			String SQL = "ID = (SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID = ?)";
			pDao.setMaxRows(1);
			ProcessChain pChain = pDao.findByDynamicWhere(SQL, sqlParams)[0];
			EmpSerReqMap eReqMap = eDao.findByPrimaryKey(empSerId);
			int appLevel = pEvaluator.findLastAppLevel(eReqMap);
			Integer[] notify = pEvaluator.notifiers(pChain.getNotification(), eReqMap.getRequestorId());
			Integer[] approvers = pEvaluator.approvers(pChain.getApprovalChain(), appLevel, eReqMap.getRequestorId());
			InboxDao inboxDao = InboxDaoFactory.create();
			List<Integer> approverList = Arrays.asList(approvers);
			if (approverList.size() > 0){
				for (int notifier : notify){
					if (!approverList.contains(notifier)){
						inbox.setReceiverId(notifier);
						inboxDao.insert(inbox);
					}
				}
			}
		} catch (InboxDaoException e){
			e.printStackTrace();
		} catch (ProcessChainDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Inbox populateInboxFromCandidate(int esrMapId, int reqId, String subject, String status) {
		Inbox inbox = new Inbox();
		CandidateReqDao canReqqDao = CandidateReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM CANDIDATE_REQ WHERE ID=? AND ESRQM_ID=?";
			CandidateReq canReq = canReqqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(canReq.getAssignedTo());
			inbox.setRaisedBy(canReq.getRaisedBy());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("CANDIDATE");
			inbox.setEsrMapId(esrMapId);
			inbox.setComments(canReq.getComments());
			inbox.setMessageBody(canReq.getMessageBody());
			return inbox;
		} catch (CandidateReqDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private Inbox populateInboxFromLeave(int esrMapId, int reqId, String subject, String status) {
		Inbox inbox = new Inbox();
		LeaveMasterDao leaveMasterDao = LeaveMasterDaoFactory.create();
		try{
			String sql = "SELECT * FROM LEAVE_MASTER WHERE  ID=?";
			LeaveMaster[] leaveMaster = leaveMasterDao.findByDynamicSelect(sql, new Object[] { reqId });
			EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
			EmpSerReqMap[] empSerReqMap = empSerReqMapDao.findWhereIdEquals(esrMapId);
			inbox.setSubject(subject);
			inbox.setAssignedTo(leaveMaster[0].getAssignedTo());
			inbox.setRaisedBy(empSerReqMap[0].getRequestorId());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("LEAVE");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(leaveMaster[0].getMessageBody());
			return inbox;
		} catch (Exception e){}
		return null;
	}

	/**
	 * This method send message to docking station who are all under the
	 * specified department
	 * 
	 * @param deptId
	 * @param inbox
	 */
	/*private void saveInboxByDept(int deptId, Inbox inbox) {
		InboxDao inboxDao = InboxDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		Users[] users = null;
		try{
			users = usersDao.findUsersByDivision(deptId);
			if (users != null) for (Users users2 : users){
				inbox.setReceiverId(users2.getId());
				inboxDao.insert(inbox);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	*/
	/**
	 * Method used to mark the mail as read or unread --- 0: read 1
	 */
	public void markAsRead(int messageId, int read) {
		InboxDao inboxDao = InboxDaoFactory.create();
		try{
			Inbox inboxDto = inboxDao.findByPrimaryKey(new InboxPk(messageId));
			inboxDto.setIsRead(read);
			inboxDao.update(new InboxPk(messageId), inboxDto);
		} catch (InboxDaoException e){
			e.printStackTrace();
		}
	}

	/**
	 * Method to populate inbox for TimeSheet
	 */
	private Inbox populateInboxFromTimeSheet(int esrMapId, int reqId, String subject) {
		Inbox inbox = new Inbox();
		TimesheetReqDao timeSheetReqDao = TimesheetReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM TIMESHEET_REQ WHERE ID=? AND ESRQM_ID=?";
			TimesheetReq timeSheetReqDto = timeSheetReqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(timeSheetReqDto.getAssignedTo());
			inbox.setRaisedBy(timeSheetReqDto.getRaisedBy());
			inbox.setStatus(timeSheetReqDto.getStatus());
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("TIMESHEET");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(timeSheetReqDto.getMessageBody());
		} catch (TimesheetReqDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox == null ? null : inbox;
	}

	public HandlerAction populateHandlerActionFormPC(ProcessChain processChain, int userId, int approvalLevel, int raisedBy) {
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		boolean isInApproval = false;
		boolean isInHandlerAction = false;
		HandlerAction handlerAction = new HandlerAction();
		Integer[] approvers = pEvaluator.approvers(processChain.getApprovalChain(), approvalLevel, raisedBy);
		Integer[] handlers = pEvaluator.handlers(processChain.getHandler(), raisedBy);
		for (int user : approvers){
			if (user == userId){
				isInApproval = true;
				break;
			}
		}
		for (int user : handlers){
			if (user == userId){
				isInHandlerAction = true;
				break;
			}
		}
		if (isInApproval){
			handlerAction.setApprove(1);
			handlerAction.setReject(1);
			handlerAction.setOnHold(1);
		} else if (isInHandlerAction){
			handlerAction.setApprove(1);
			handlerAction.setReject(1);
			handlerAction.setAssign(1);
			handlerAction.setReassign(1);
			handlerAction.setResolved(1);
			handlerAction.setClosed(1);
		}
		return handlerAction;
	}

	private Inbox populateInboxFromRollOn(int esrMapId, int reqId, String subject) {
		Inbox inbox = new Inbox();
		RollOnDao rollOn = RollOnDaoFactory.create();
		try{
			String sql = "SELECT * FROM ROLL_ON WHERE ID=? AND ESRQM_ID=?";
			RollOn rollon = rollOn.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(rollon.getRaisedBy());
			inbox.setRaisedBy(rollon.getRaisedBy());
			inbox.setStatus(Status.ROLLEDON);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setEsrMapId(rollon.getEsrqmId());
			inbox.setCategory("ROLLON");
			inbox.setMessageBody(rollon.getMessageBody());
			return inbox;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * |--Method to populate inbox for TimeSheet
	 * 
	 * @param status
	 */
	/*public void populateInboxForTimeSheetRequestor(int esrMapId, int sequenceNo, int requestorId, String subject, String bodyText, String status) {
		InboxDao inboxDao = InboxDaoFactory.create();
		Inbox inbox = new Inbox();
		TimesheetReqDao timeSheetReqDao = TimesheetReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM TIMESHEET_REQ WHERE  ESRQM_ID=?    AND SEQUENCE =? ORDER BY CREATEDATETIME DESC;";
			TimesheetReq timeSheetReqDto = timeSheetReqDao.findByDynamicSelect(sql, new Object[] { esrMapId, sequenceNo })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(timeSheetReqDto.getAssignedTo());
			inbox.setRaisedBy(timeSheetReqDto.getRaisedBy());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("TIMESHEET");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
			inbox.setReceiverId(requestorId);
			inboxDao.insert(inbox);
		} catch (TimesheetReqDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	<<<<<<< .working
	<<<<<<< .working
	}*/
	public Inbox populateInboxFromReimbursement(int esrMapId, int reqId, String subject, String status) {
		Inbox inbox = new Inbox();
		ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM REIMBURSEMENT_REQ WHERE ID=? AND ESR_MAP_ID=? ORDER BY CREATE_DATE DESC";
			ReimbursementReq rmReq = reimbursementReqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(rmReq.getAssignTo());
			inbox.setRaisedBy(rmReq.getRequesterId());
			inbox.setStatus(status);
			inbox.setCreationDatetime(rmReq.getCreateDate());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("REIMBURSEMENT");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(rmReq.getMessageBody());
			return inbox;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void populateInboxFromInsurance(int esrMapId, String handlerIds, String subject, String bodyText) {
		Inbox inbox = new Inbox();
		InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		try{
			for (String hndId : handlerIds.split(",")){
				if (hndId == null) continue;
				InsurancePolicyReq InsPlyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { esrMapId });
				if (InsPlyReq.length > 0 && !hndId.trim().equalsIgnoreCase("0")){
					inbox.setEsrMapId(esrMapId);
					inbox.setSubject(subject);
					inbox.setMessageBody(bodyText);
					inbox.setRaisedBy(InsPlyReq[0].getRequeterId());
					inbox.setStatus(InsPlyReq[0].getStatus());
					inbox.setCreationDatetime(InsPlyReq[0].getRequestedOn());
					inbox.setIsRead(0);
					inbox.setIsDeleted(0);
					inbox.setCategory("INSURANCE");
					inbox.setReceiverId(Integer.parseInt(hndId.trim()));
					if (handlerIds.split(",").length == 1) inbox.setAssignedTo(Integer.parseInt("0"));
					else inbox.setAssignedTo(Integer.parseInt(hndId.trim()));
					InboxPk inboxPk = inboxDao.insert(inbox);
					inbox.setId(inboxPk.getId());
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return;
	}

	private void getReimbursementDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		HandlerAction handlerAction = new HandlerAction();
		ProcessEvaluator processEvaluator = new ProcessEvaluator();
		ProcessChainDao pcDao = ProcessChainDaoFactory.create();
		ReimbursementReqDao reqDao = ReimbursementReqDaoFactory.create();
		ProfileInfoDao prfileDao = ProfileInfoDaoFactory.create();
		RequestEscalation reqEscalation = new RequestEscalation();
		try{
			Inbox inbox = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
			ProfileInfo dto = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(inbox.getRaisedBy()) })[0];
			inbox.setRaisedByName(dto.getFirstName());
			Inbox inbox1 = null;
			if (login.getUserId() == inbox.getRaisedBy()){
				inbox1 = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? AND ASSIGNED_TO= 0 ORDER BY CREATION_DATETIME DESC ", new Object[] { new Integer(inbox.getEsrMapId()) })[0];
			} else{
				//Query segment changed from ... AND ASSIGNED_TO= ? to ... AND RECEIVER_ID= ?
				inbox1 = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? AND RECEIVER_ID= ? ORDER BY CREATION_DATETIME DESC ", new Object[] { new Integer(inbox.getEsrMapId()), login.getUserId() })[0];
			}
			//Set escalation action
			reqEscalation.setEscalationAction(inbox.getEsrMapId(), login.getUserId());
			ProcessChain process_chain_dto = null;
			if (reqEscalation.isEscalationAction()){
				process_chain_dto = reqEscalation.getRequestProcessChain();
				reqEscalation.setEscalationProcess(processEvaluator);
				reqEscalation.enableEscalationPermission(processEvaluator);
			} else{
				process_chain_dto = pcDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=26 WHERE UR.USER_ID=?", new Object[] { new Integer(inbox.getRaisedBy()) })[0];
			}
			ReimbursementReq reimbursementDto1 = new ReimbursementReq();
			reimbursementDto1 = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?  ORDER BY ACTION_TAKEN_ON DESC ", new Object[] { new Integer(inbox.getEsrMapId()) })[0];
			Integer[] thirdlevel = processEvaluator.handlers(process_chain_dto.getHandler(), inbox.getRaisedBy());
			if (login.getUserId() == inbox1.getAssignedTo() && inbox1.getStatus().equals(Status.REQUESTRAISED) && !(Arrays.asList(thirdlevel).contains(inbox1.getAssignedTo()))){
				if (login.getUserId() != inbox1.getRaisedBy()){
					handlerAction.setApprove(1);
					handlerAction.setReject(1);
					// dissabled hold make as 1 if needs to enable.
					handlerAction.setOnHold(0);
				}
			} else if (login.getUserId() == inbox1.getAssignedTo() && inbox1.getStatus().equals(Status.ON_HOLD)){
				handlerAction.setApprove(1);
				handlerAction.setReject(1);
			} else if (Arrays.asList(thirdlevel).contains(login.getUserId()) && inbox1.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) && !reimbursementDto1.getStatus().equals(Status.COMPLETED)){
				handlerAction.setAssign(1);
				dropDownForm.setKey1(1);
			} else if (reimbursementDto1.getStatus().equals("Cancel Request") && inbox1.getStatus().equalsIgnoreCase("Cancel Request")){
				if (login.getUserId() != inbox1.getRaisedBy()){
					if (login.getUserId() == inbox1.getAssignedTo() && reimbursementDto1.getOldStatus().equalsIgnoreCase("Cancel Request")){
						ReimbursementReq assignedTo[] = new ReimbursementReq[1];
						assignedTo = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN" + "(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ACTIVE IS NULL AND STATUS IN( '" + Status.ASSIGNED + "','In-Progress','Completed','Cancel Request','Cancel In-Progress') AND ESR_MAP_ID= ?" + " ORDER BY ACTION_TAKEN_ON DESC )", new Object[] { new Integer(inbox1.getEsrMapId()) });
						if (assignedTo != null && assignedTo.length > 0){
							if (inbox.getAssignedTo() == assignedTo[0].getAssignTo()){
								handlerAction.setAssign(1);
								dropDownForm.setKey1(1);
							}
							ProfileInfo assignedToName[] = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(assignedTo[0].getAssignTo()) });
							if (assignedToName != null){
								inbox.setAssignedToName(assignedToName[0].getFirstName() + " " + assignedToName[0].getLastName());
							}
						}
					} else if (login.getUserId() == inbox1.getAssignedTo()){
						handlerAction.setAssign(1);
						dropDownForm.setKey1(1);
					}
				}
			} else if (login.getUserId() == inbox1.getAssignedTo() && inbox1.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
				if (login.getUserId() != inbox1.getRaisedBy()){
					ReimbursementReq assignedTo[] = new ReimbursementReq[1];
					assignedTo = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN" + "(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ACTIVE IS NULL AND STATUS IN( '" + Status.ASSIGNED + "','In-Progress','Completed','Cancel Request','Cancel In-Progress') AND ESR_MAP_ID= ?" + " ORDER BY ACTION_TAKEN_ON DESC )", new Object[] { new Integer(inbox1.getEsrMapId()) });
					if (assignedTo != null && assignedTo.length > 0){
						if (inbox.getAssignedTo() == assignedTo[0].getAssignTo()){
							handlerAction.setAssign(1);
							dropDownForm.setKey1(1);
						}
					}
				}
			} else if (login.getUserId() == inbox1.getAssignedTo() && (inbox1.getStatus().equalsIgnoreCase(Status.ASSIGNED) || inbox1.getStatus().equalsIgnoreCase(Status.CANCELREQUEST) || inbox1.getStatus().equalsIgnoreCase(Status.CANCELINPROGRESS))){
				if (login.getUserId() != inbox1.getRaisedBy()){
					/**
					 * get entry which is active to whom request is assigned and get assigned to from that row
					 */
					//					ReimbursementReq assignedTo[]=  reqDao.findByDynamicWhere("ACTIVE IS NULL AND STATUS = '"+Status.ASSIGNED+"' ",null);
					ReimbursementReq assignedTo[] = new ReimbursementReq[1];
					assignedTo = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN" + "(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ACTIVE IS NULL AND STATUS IN( '" + Status.ASSIGNED + "','In-Progress','Completed','Cancel Request','Cancel In-Progress') AND ESR_MAP_ID= ?" + " ORDER BY ACTION_TAKEN_ON DESC )", new Object[] { new Integer(inbox1.getEsrMapId()) });
					if (assignedTo != null && assignedTo.length > 0){
						if (inbox.getAssignedTo() == assignedTo[0].getAssignTo()){
							handlerAction.setAssign(1);
							dropDownForm.setKey1(1);
						}
						ProfileInfo assignedToName[] = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(assignedTo[0].getAssignTo()) });
						if (assignedToName != null){
							inbox.setAssignedToName(assignedToName[0].getFirstName() + " " + assignedToName[0].getLastName());
						}
					}
				}
			}//else-if ends
				// get the required data
			ReimbursementReq reimbursementDto = reqDao.findWhereEsrMapIdEquals(inbox.getEsrMapId())[0];
			inbox.setSubmittedOn(reimbursementDto.getCreateDate());
			inbox.setAcceptedOn(PortalUtility.formatDateddmmyyyyhhmmss(reimbursementDto.getCreateDate()));
			ProfileInfo requsterProfile = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(inbox.getRaisedBy()) })[0];
			inbox.setSubmittedBy(requsterProfile.getFirstName() + " " + requsterProfile.getLastName());
			if (reimbursementDto1.getStatus().equals(Status.REJECTED)){
				if (inbox.getStatus().equalsIgnoreCase(Status.REJECTED)){
					inbox.setRejectedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto1.getActionTakenOn()));
					ProfileInfo rejectedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reimbursementDto1.getActionTakenBy()) })[0];
					inbox.setRejectedBy(rejectedby.getFirstName() + " " + rejectedby.getLastName());
				} else{
					ReimbursementReq reimbursementDto2[] = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND STATUS ='Approved' ORDER BY ACTION_TAKEN_ON DESC", new Object[] { new Integer(inbox.getEsrMapId()) });
					inbox.setApprovedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto2[0].getActionTakenOn()));
					ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reimbursementDto2[0].getActionTakenBy()) })[0];
					inbox.setApprovedBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
				}
			} else if (reimbursementDto1.getStatus().equals(Status.COMPLETED)){
				ReimbursementReq reimbursementDto2[] = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND STATUS ='Completed' ORDER BY ACTION_TAKEN_ON DESC", new Object[] { new Integer(inbox.getEsrMapId()) });
				inbox.setCompletedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto2[0].getActionTakenOn()));
				ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reimbursementDto2[0].getActionTakenBy()) })[0];
				inbox.setCompletedBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
			} else if (reimbursementDto1.getStatus().equals(Status.APPROVED) || inbox.getStatus().equals(Status.ACCEPTED) || inbox.getStatus().equals(Status.SUBMITTED)){
				ReimbursementReq reimbursementDto2[] = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND STATUS ='Approved' ORDER BY ACTION_TAKEN_ON DESC", new Object[] { new Integer(inbox.getEsrMapId()) });
				if (reimbursementDto2.length == 3 && reimbursementDto1.getStatus().equals(Status.SUBMITTED)){
					inbox.setApprovedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto2[0].getActionTakenOn()));
					ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=? ", new Object[] { new Integer(reimbursementDto2[0].getActionTakenBy()) })[0];
					inbox.setApprovedBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
				} else{
					inbox.setApprovedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto2[0].getActionTakenOn()));
					ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reimbursementDto2[0].getActionTakenBy()) })[0];
					inbox.setApprovedBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
				}
			} else if (reimbursementDto1.getStatus().equals(Status.ON_HOLD)){
				if (inbox.getStatus().equalsIgnoreCase(Status.ON_HOLD)){
					ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=? ", new Object[] { new Integer(reimbursementDto1.getActionTakenBy()) })[0];
					inbox.setHoldBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
					inbox.setHoldOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto1.getActionTakenOn()));
				} else{
					ReimbursementReq reimbursementDto2[] = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND STATUS ='Approved' ORDER BY ACTION_TAKEN_ON DESC", new Object[] { new Integer(inbox.getEsrMapId()) });
					inbox.setApprovedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto2[0].getActionTakenOn()));
					ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reimbursementDto2[0].getActionTakenBy()) })[0];
					inbox.setApprovedBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
				}
			} else if (reimbursementDto1.getStatus().equals(Status.CANCELREQUEST)){
				ReimbursementReq reimbursementDto2[] = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?  ORDER BY ACTION_TAKEN_ON DESC", new Object[] { new Integer(inbox.getEsrMapId()) });
				inbox.setApprovedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto2[0].getActionTakenOn()));
				ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reimbursementDto2[0].getActionTakenBy()) })[0];
				inbox.setApprovedBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
			} else if (reimbursementDto1.getStatus().equalsIgnoreCase(Status.INPROGRESS) || reimbursementDto1.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
				ReimbursementReq reimbursementDto2[] = reqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND STATUS = 'Approved'  ORDER BY ACTION_TAKEN_ON DESC ", new Object[] { new Integer(inbox.getEsrMapId()) });
				inbox.setApprovedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reimbursementDto2[0].getActionTakenOn()));
				ProfileInfo acceptedby = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reimbursementDto2[0].getActionTakenBy()) })[0];
				inbox.setApprovedBy(acceptedby.getFirstName() + " " + acceptedby.getLastName());
				inbox.setSubmittedOn(reimbursementDto2[0].getCreateDate());
				inbox.setAcceptedOn(PortalUtility.formatDateddmmyyyyhhmmss(reimbursementDto2[0].getCreateDate()));
				ProfileInfo requstProfile = prfileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(inbox.getRaisedBy()) })[0];
				inbox.setSubmittedBy(requstProfile.getFirstName() + " " + requstProfile.getLastName());
			}
			inbox.setReimbuFlag(reimbursementDto1.getReimbuFlag());
			inbox.setHandleraction(handlerAction);
			inbox.setCurrentStatus(inbox.getStatus());
			dropDownForm.setDetail(inbox);
		} catch (InboxDaoException e){} catch (ProfileInfoDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessChainDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReimbursementReqDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Inbox populateInboxFromITrequest(int esrMapId, int reqId, String subject, String status) {
		Inbox inbox = new Inbox();
		ItRequestDao itreqDao = ItRequestDaoFactory.create();
		try{
			String sql = "SELECT * FROM IT_REQUEST WHERE ID=? AND ESR_MAP_ID=?";
			ItRequest itReq = itreqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			if (itReq.getAssignTo() > 0) inbox.setAssignedTo(itReq.getAssignTo());
			else inbox.setAssignedToNull(true);
			inbox.setRaisedBy(itReq.getRequesterId());
			inbox.setStatus(status);
			inbox.setCreationDatetime(itReq.getCreateDate());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("ITSUPPORT");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(itReq.getMessageBody());
			return inbox;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private void getItReqDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ItRequestDao itDao = ItRequestDaoFactory.create();
		HandlerAction handler = new HandlerAction();
		try{
			Inbox inbox = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
			ItRequest[] it = null;
			if (inbox.getAssignedTo() > 0){
				//if (login.getUserId() == inbox.getAssignedTo() && Arrays.asList(ids).contains(login.getUserId()) && !inbox1.getStatus().equals(Status.COMPLETE) && !inbox1.getStatus().equals(Status.COMPLETED))
				if (login.getUserId() == inbox.getAssignedTo()){
					handler.setAssign(1);
				}
				it = itDao.findByDynamicSelect("SELECT * FROM IT_REQUEST WHERE ESR_MAP_ID=? AND ASSIGN_TO >0 ORDER BY CREATE_DATE DESC", new Object[] { new Integer(inbox.getEsrMapId()) });
			} else{
				it = itDao.findByDynamicSelect("SELECT * FROM IT_REQUEST WHERE ESR_MAP_ID=? ORDER BY CREATE_DATE DESC", new Object[] { new Integer(inbox.getEsrMapId()) });
			}
			IssueInboxInfo issueInboxInfoBean = new IssueInboxInfo();
			issueInboxInfoBean.setRaisedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(it[0].getCreateDate()));
			if (inbox.getStatus().equalsIgnoreCase("Resolved") || inbox.getStatus().equalsIgnoreCase("Closed")){
				for (ItRequest itRequest : it){
					if (itRequest.getAssignTo() > 0){
						Users userResolved = usersDao.findByPrimaryKey(itRequest.getAssignTo());
						ProfileInfo resolverProfile = profileInfoDao.findByPrimaryKey(userResolved.getProfileId());
						issueInboxInfoBean.setResolvedBy(resolverProfile.getFirstName() + " " + resolverProfile.getLastName());
						issueInboxInfoBean.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(itRequest.getLastModifiedDate()));
					}
				}
			}
			inbox.setSubmittedBy(inbox.getRaisedByName());
			inbox.setSubmittedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(it[0].getCreateDate()));
			ItRequest requesterItrequestEntry = itDao.findByDynamicWhere("ESR_MAP_ID=? AND REQUESTER_ID=RECEIVER_ID", new Object[] { inbox.getEsrMapId() })[0];
			//inbox.setCurrentStatus(inbox.getStatus().contains("Docked By ") ? "Request Raised" : inbox.getStatus());
			inbox.setCurrentStatus(requesterItrequestEntry.getStatus());
			for (ItRequest itRequest : it){
				if (itRequest.getReceiverId() == login.getUserId()){
					inbox.setItId(itRequest.getId());
				}
			}
			inbox.setHandleraction(handler);
			inbox.setIssueInfoInbxBean(issueInboxInfoBean);
			dropDownForm.setDetail(inbox);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void getSodexoDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		HandlerAction handlerAction = new HandlerAction();
		ProfileInfo profileInfoRow = null;
		String assignedTo = null;
		String profileInfoQuery = "SELECT * FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS U WHERE U.ID=?)";
		Inbox inboxRow;
		EmpSerReqHistoryDao esrHistoryDao = EmpSerReqHistoryDaoFactory.create();
		try{
			inboxRow = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
			SodexoReq sodexoReqRow = SodexoReqDaoFactory.create().findByDynamicWhere(" ESR_MAP_ID=? ORDER BY ACTION_DATE DESC", new Object[] { new Integer(inboxRow.getEsrMapId()) })[0];
			EmpSerReqHistory[] esrHistoryRows = esrHistoryDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_HISTORY WHERE ESR_MAP_ID=? ORDER BY ID DESC", new Object[] { sodexoReqRow.getEsrMapId() });
			SodexoDetails sodexoDetailsRow = SodexoDetailsDaoFactory.create().findByDynamicWhere(" ID=?", new Object[] { new Integer(sodexoReqRow.getSdId()) })[0];
			//Date requestedOn = sodexoDetailsRow.getRequestedOn();//dd-mm-yyyy
			profileInfoRow = ProfileInfoDaoFactory.create().findByDynamicSelect(profileInfoQuery, new Object[] { new Integer(sodexoDetailsRow.getRequestorId()) })[0];
			String requestedBy = profileInfoRow.getFirstName() + " " + profileInfoRow.getLastName();
			Date actionTakenOn = null;
			//sodexoReqRow.getAssignedTo();
			if (sodexoReqRow.getActionBy() > 0){
				actionTakenOn = sodexoReqRow.getActionDate();
			}
			//sodexoReqRow.getActionBy();//this can be 0, when it is assigned and the action has not yet been taken
			if (esrHistoryRows != null && esrHistoryRows.length > 0){
				profileInfoRow = ProfileInfoDaoFactory.create().findByDynamicSelect(profileInfoQuery, new Object[] { new Integer(esrHistoryRows[0].getAssignedTo()) })[0];//INBOX has the latest ASSIGNED_TO's id
				assignedTo = profileInfoRow.getFirstName() + " " + profileInfoRow.getLastName();
			}
			/*if (inboxRow.getAssignedTo() > 0){//assignedTo will be null
				//it is assigned to some handler
				profileInfoRow = ProfileInfoDaoFactory.create().findByDynamicSelect(profileInfoQuery, new Object[] { new Integer(inboxRow.getAssignedTo()) })[0];//INBOX has the latest ASSIGNED_TO's id
				assignedTo = profileInfoRow.getFirstName() + " " + profileInfoRow.getLastName();
			}*/
			//else it is still an open ticket and no handler is assigned		
			inboxRow.setSubmittedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(sodexoDetailsRow.getRequestedOn()));
			inboxRow.setSubmittedBy(requestedBy);
			//to get the current_status
			SodexoDetailsDao sodexoDetailsDao = SodexoDetailsDaoFactory.create();
			SodexoDetails sodexoDetails = sodexoDetailsDao.findByDynamicSelect("select * from SODEXO_DETAILS where ID IN (select DISTINCT SD_ID from SODEXO_REQ where ESR_MAP_ID=?)", new Object[] { new Integer(inboxRow.getEsrMapId()) })[0];
			inboxRow.setCurrentStatus(Status.getStatus(sodexoDetails.getStatus()));
			ProcessChain pChain = new SodexoModel().getRequestorProcessChain(inboxRow.getRaisedBy());//process chain must be selected based on the requesterId
			String handlerString = pChain.getHandler();
			ProcessEvaluator pEval = new ProcessEvaluator();
			List<Integer> handlerList = Arrays.asList(pEval.handlers(handlerString, sodexoDetailsRow.getRequestorId()));
			//handler stuff
			if (inboxRow.getAssignedTo() == login.getUserId()){
				if (!(inboxRow.getStatus().equals(Status.REVOKED))){
					handlerAction.setAssign(1);
				}
			}
			if (Status.REVOKED.equals(Status.getStatus(sodexoDetailsRow.getStatus()))){
				inboxRow.setRejectedOn(actionTakenOn);
				inboxRow.setRejectedBy(assignedTo);
			} else if (Status.PROCESSED.equals(Status.getStatus(sodexoDetailsRow.getStatus()))){
				inboxRow.setApprovedOn(actionTakenOn);
				Inbox inboxTemp = inboxDao.findByDynamicSelect("Select * from INBOX where ESR_MAP_ID=? AND STATUS like 'PROCESSED' ORDER BY ID DESC", new Object[] { new Integer(inboxRow.getEsrMapId()) })[0];
				profileInfoRow = ProfileInfoDaoFactory.create().findByDynamicSelect(profileInfoQuery, new Object[] { new Integer(inboxTemp.getAssignedTo()) })[0];
				assignedTo = profileInfoRow.getFirstName() + " " + profileInfoRow.getLastName();
				inboxRow.setApprovedBy(assignedTo);
			} else{
				if (Status.WORKINPROGRESS.equals(Status.getStatus(sodexoDetailsRow.getStatus())))
				//inboxRow.setAssignedToName(assignedTo);
				if (handlerList.contains(login.getUserId()))
				//logged in user is a handler for that process chain					
				if (inboxRow.getStatus().equalsIgnoreCase(inboxRow.getCurrentStatus()) && inboxRow.getCurrentStatus() != Status.PROCESSED) dropDownForm.setKey1(1);
			}
			inboxRow.setHandleraction(handlerAction);
			inboxRow.setAcceptedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(sodexoReqRow.getActionDate()));
			inboxRow.setApprovedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(actionTakenOn));
			dropDownForm.setDetail(inboxRow);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	private Inbox populateInboxFromSodexorequest(int esrMapId, int reqId, String subject, String status) {
		Inbox inbox = new Inbox();
		SodexoReqDao sodexoReqDao = SodexoReqDaoFactory.create();
		SodexoDetailsDao sodexoDetailsDao = SodexoDetailsDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		try{
			sodexoReqDao.setMaxRows(1);
			SodexoReq sodexoReqRow = sodexoReqDao.findByPrimaryKey(reqId);
			inbox.setEsrMapId(esrMapId);
			inbox.setSubject(subject);
			inbox.setAssignedTo(sodexoReqRow.getAssignedTo());
			SodexoDetails sodexoDetailsRow = sodexoDetailsDao.findByDynamicSelect("SELECT * FROM SODEXO_DETAILS WHERE ID=(SELECT SD_ID FROM SODEXO_REQ WHERE ID=?)", new Object[] { new Integer(reqId) })[0];
			inbox.setRaisedBy(sodexoDetailsRow.getRequestorId());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("SODEXO");
			inbox.setMessageBody(sodexoReqRow.getMessageBody());
			ProfileInfo profileInfoRow = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { new Integer(sodexoDetailsRow.getRequestorId()) })[0];
			inbox.setRaisedByName(profileInfoRow.getFirstName() + " " + profileInfoRow.getLastName());
		} catch (SodexoReqDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox;
	}

	/**
	 * |--Method to populate inbox for TimeSheet
	 */
	private Inbox populateInboxFromIssues(int esrMapId, int reqId, String subject) {
		Inbox inbox = new Inbox();
		IssueApproverChainReqDao issueApprChainReqDao = IssueApproverChainReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM ISSUE_APPROVER_CHAIN_REQ WHERE ID=? AND ESRQM_ID=?";
			IssueApproverChainReq issueAppChainReq = issueApprChainReqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(issueAppChainReq.getAssignedTo());
			inbox.setRaisedBy(issueAppChainReq.getRaisedBy());
			inbox.setStatus(issueAppChainReq.getStatus());
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("ISSUES");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(issueAppChainReq.getMessageBody());
		} catch (IssueApproverChainReqDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox == null ? null : inbox;
	}

	/**
	 * |--Method to populate inbox for TimeSheet
	 */
	public void populateInboxForIssueTicketRequestor(int esrMapId, int sequenceNo, int requestorId, String subject, String bodyText) {
		InboxDao inboxDao = InboxDaoFactory.create();
		Inbox inbox = new Inbox();
		IssueApproverChainReqDao issueApprChainReqDao = IssueApproverChainReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM ISSUE_APPROVER_CHAIN_REQ WHERE  ESRQM_ID=?    AND SEQUENCE =? ORDER BY CREATEDATETIME DESC;";
			IssueApproverChainReq issueAppChainReq = issueApprChainReqDao.findByDynamicSelect(sql, new Object[] { esrMapId, sequenceNo })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(issueAppChainReq.getAssignedTo());
			inbox.setRaisedBy(issueAppChainReq.getRaisedBy());
			inbox.setStatus(issueAppChainReq.getStatus());
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("ISSUES");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
			inbox.setReceiverId(requestorId);
			inboxDao.insert(inbox);
		} catch (IssueApproverChainReqDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * |--Method to populate inbox for TimeSheet
	 */
	public void populateInboxOfConcernedDeptForISsues(int esrMapId, int deptUserId, int requestorId, String subject, String status, String bodyText) {
		InboxDao inboxDao = InboxDaoFactory.create();
		Inbox inbox = new Inbox();
		try{
			inbox.setSubject(subject);
			inbox.setRaisedBy(requestorId);
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setAssignedTo(deptUserId);
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("ISSUES");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
			inbox.setReceiverId(deptUserId);
			inboxDao.insert(inbox);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * |--Method to populate inbox for Insurance
	 */
	public void populateInboxEntryForInsurance(int esrMapId, int deptUserId, int requestorId, String subject, String status, String bodyText) {
		InboxDao inboxDao = InboxDaoFactory.create();
		Inbox inbox = new Inbox();
		try{
			inbox.setSubject(subject);
			inbox.setRaisedBy(requestorId);
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setAssignedTo(deptUserId);
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("INSURANCE");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
			inbox.setReceiverId(deptUserId);
			inboxDao.insert(inbox);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void getIssueReqDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		RequestedIssuesDao requestedIssueDao = RequestedIssuesDaoFactory.create();
		IssueHandlerChainReqDao issueHandlerChainReqDao = IssueHandlerChainReqDaoFactory.create();
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		ProcessChainDao pChainDao = ProcessChainDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		int loggedInUser = login.getUserId();
		try{
			pChainDao.setMaxRows(1);
			Inbox inbox = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
			IssueInboxInfo issueInboxInfoBean = new IssueInboxInfo();
			HandlerAction handlerAction = null;
			IssueHandlerChainReq issueHandlerChainReqDto = null;
			RequestedIssues requestedIssues = requestedIssueDao.findByDynamicSelect("SELECT * FROM REQUESTED_ISSUES WHERE ESR_MAP_ID=? ", new Object[] { inboxForm.getEsrMapId() })[0];
			Users issueRequestor = usersDao.findByPrimaryKey(requestedIssues.getUserId());
			ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(issueRequestor.getProfileId());
			issueInboxInfoBean.setRaisedBy(profileInfo.getFirstName());
			issueInboxInfoBean.setRaisedOn(dateFormat.format(requestedIssues.getSubmissionDate()));
			issueInboxInfoBean.setIssueId(requestedIssues.getId() + "");
			String currentStatus = requestedIssues.getStatus();
			String Handler_req_sql = "ESR_MAP_ID=? AND ASSIGNED_TO =?  ORDER BY CREATION_DATETIME DESC";
			Object[] Handler_req_sql_Params = { inboxForm.getEsrMapId(), loggedInUser };
			IssueHandlerChainReq[] issueHandlerChainReqArr = issueHandlerChainReqDao.findByDynamicWhere(Handler_req_sql, Handler_req_sql_Params);
			if (issueHandlerChainReqArr.length > 0) //for handlers
			{
				issueHandlerChainReqDto = issueHandlerChainReqArr[0];
			} else{ //for requester
				String sql1 = "ESR_MAP_ID=?   ORDER BY CREATION_DATETIME DESC";
				issueHandlerChainReqArr = issueHandlerChainReqDao.findByDynamicWhere(sql1, new Object[] { inboxForm.getEsrMapId() });
				issueHandlerChainReqDto = issueHandlerChainReqArr[0];
			}
			if (currentStatus.equalsIgnoreCase("RESOLVED") || currentStatus.equalsIgnoreCase("CLOSED")){
				if (issueHandlerChainReqDto.getStatus().equalsIgnoreCase("RESOLVED") || issueHandlerChainReqDto.getStatus().equalsIgnoreCase("CLOSED")){
					Users userDto = usersDao.findByPrimaryKey(issueHandlerChainReqDto.getActionBy());
					ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
					issueInboxInfoBean.setResolvedOn(dateFormat.format(issueHandlerChainReqDto.getCreationDatetime()));
					issueInboxInfoBean.setResolvedBy(profileInfoDto.getFirstName());
					handlerAction = new HandlerAction();
				} else{
					IssueHandlerChainReq issueHandlerChainReq2 = issueHandlerChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND STATUS IN('Resolved','Closed')", new Object[] { issueHandlerChainReqDto.getEsrMapId() })[0];
					Users userDto = usersDao.findByPrimaryKey(issueHandlerChainReq2.getActionBy());
					ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
					issueInboxInfoBean.setResolvedOn(dateFormat.format(issueHandlerChainReq2.getCreationDatetime()));
					issueInboxInfoBean.setResolvedBy(profileInfoDto.getFirstName());
					handlerAction = new HandlerAction();
				}
			}
			/*Status[] status = statusDao.findByDynamicSelect(SQL_STATUS_SELECT, new Object[] { inbox.getStatus() });
			if (status != null && status.length > 0){
				inbox.setStatusId(status[0].getId());
			}*/
			inbox.setStatusId(Status.getStatusId(inbox.getStatus()));
			inbox.setCurrentStatus(inbox.getStatus());
			if (currentStatus != null){
				inbox.setCurrentStatus(currentStatus);
			}
			boolean assign = false;
			if (issueHandlerChainReqDto.getAssignedTo() == login.getUserId()){
				assign = true;
			}
			if (assign == true){
				handlerAction = new HandlerAction();
				handlerAction.setAssign(1);
			} else{
				handlerAction = new HandlerAction();
			}
			inbox.setSubmittedBy(issueInboxInfoBean.getRaisedBy());
			inbox.setSubmittedOn(issueInboxInfoBean.getRaisedOn().toString());
			inbox.setHandlerActionBean(handlerAction);
			inbox.setIssueInfoInbxBean(issueInboxInfoBean);
			dropDownForm.setDetail(inbox);
		} catch (Exception e){}
	}//End of method

	/**
	 * |--Method to populate inbox for Issue Handler
	 */
	public Inbox populateInboxFromIssuesHandlers(int esrMapId, String subject, String status, int AssignedTo, int receiverId, int RaisedBy, String bodyText) {
		Inbox inbox = new Inbox();
		try{
			inbox.setSubject(subject);
			inbox.setAssignedTo(AssignedTo);
			inbox.setRaisedBy(RaisedBy);
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("ISSUES");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
			inbox.setReceiverId(receiverId);
			InboxDao inboxDao = InboxDaoFactory.create();
			InboxPk inboxPk = inboxDao.insert(inbox);
			inbox.setId(inboxPk.getId());
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox == null ? null : inbox;
	}

	public void notifyToIssueHandlers(int empSerId, Inbox inbox) {
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		EmpSerReqMapDao eDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao pDao = ProcessChainDaoFactory.create();
		try{
			Object[] sqlParams = { empSerId };
			String SQL = "ID = (SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID = ?)";
			pDao.setMaxRows(1);
			ProcessChain pChain = pDao.findByDynamicWhere(SQL, sqlParams)[0];
			EmpSerReqMap eReqMap = eDao.findByPrimaryKey(empSerId);
			Integer[] notify = pEvaluator.notifiers(pChain.getNotification(), eReqMap.getRequestorId());
			Integer[] handlers = pEvaluator.handlers(pChain.getHandler(), eReqMap.getRequestorId());
			InboxDao inboxDao = InboxDaoFactory.create();
			List<Integer> handlerList = Arrays.asList(handlers);
			for (int notifier : notify){
				if (!handlerList.contains(notifier)){
					inbox.setReceiverId(notifier);
					inboxDao.insert(inbox);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void getLeaveReqDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		//ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		//ProcessEvaluator proEva = new ProcessEvaluator();
		LeaveMasterDao leaveDao = LeaveMasterDaoFactory.create();
		HandlerAction handlerAction = new HandlerAction();
		ServiceReqInfo serviceReqInfo[] = null;
		ServiceReqInfoDao serviceReqInfoDao = ServiceReqInfoDaoFactory.create();
		try{
			Inbox inbox = inboxDao.findByPrimaryKey(new InboxPk(inboxForm.getId()));
			serviceReqInfo = serviceReqInfoDao.findWhereEsrMapIdEquals(inbox.getEsrMapId());
			LeaveMaster[] leaveforreceive = leaveDao.findByDynamicSelect("SELECT * FROM LEAVE_MASTER WHERE ID =( SELECT MAX(ID) FROM LEAVE_MASTER WHERE ESR_MAP_ID=? AND ASSIGNED_TO = ? )", new Object[] { new Integer(inbox.getEsrMapId()), new Integer(login.getUserId()) });
			if (serviceReqInfo != null && serviceReqInfo.length > 0 && ModelUtiility.getInstance().isValidApprover(inbox.getEsrMapId(), login.getUserId())){
				String currentStatus = serviceReqInfo[0].getStatus() + "";
				if (currentStatus.equals(Status.REQUESTRAISED) || currentStatus.equals(Status.PENDINGAPPROVAL)){
					if (ModelUtiility.getInstance().isAssignedApprover(inbox.getEsrMapId(), login.getUserId())){
						handlerAction.setApprove(1);
						handlerAction.setReject(1);
					}
					handlerAction.setAssign(1);
				} else if (currentStatus.equals(Status.ACCEPTED)){
					if (ModelUtiility.getInstance().isAssignedApprover(inbox.getEsrMapId(), login.getUserId())) handlerAction.setComplete(1);
					handlerAction.setAssign(1);
				}
				inbox.setHandleraction(handlerAction);
			}
			if (serviceReqInfo != null && serviceReqInfo.length > 0) inbox.setCurrentStatus((serviceReqInfo[0].getStatus() + "").equals(Status.ACCEPTED) ? "Apporved" : serviceReqInfo[0].getStatus());
			dropDownForm.setDetail(inbox);
			try{
				inbox.setLeaveId(leaveforreceive.length > 0 ? leaveforreceive[0].getId() : 0);
				inbox.setSubmittedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(leaveforreceive[0].getAppliedDateTime()));
				inbox.setSubmittedBy(((String) JDBCUtiility.getInstance().getSingleColumn("SELECT FIRST_NAME FROM PROFILE_INFO WHERE ID = (SELECT PROFILE_ID FROM USERS WHERE ID = (SELECT REQUESTOR_ID FROM EMP_SER_REQ_MAP WHERE ID=?))", new Object[] { inbox.getEsrMapId() }).get(0)));
			} catch (Exception e){}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private Inbox populateInboxFromLoan(int esrMapId, int reqId, String subject, String bodyText) {
		Inbox inbox = new Inbox();
		LoanRequestDao loanReqDao = LoanRequestDaoFactory.create();
		try{
			String[] mailSts = subject.split("@");
			String sql = "SELECT * FROM LOAN_REQUEST WHERE ID=? AND ESR_MAP_ID=?";
			LoanRequest loanReq = loanReqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			//Status sts[] = statusDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID = ? AND ACTION_TAKEN_BY != 0", new Object[]{loanReq.getLoanId()});
			//LoanRequest[] loanReq1 = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID = ? AND ACTION_TAKEN_BY != 0", new Object[]{loanReq.getLoanId()});
			inbox.setSubject(mailSts[0]);
			inbox.setAssignedTo(loanReq.getAssignTo());
			inbox.setRaisedBy(loanReq.getLoanUserId());
			if (mailSts.length > 2){
				inbox.setStatus(mailSts[1] + mailSts[2]);
			}
			if (mailSts.length > 1) inbox.setStatus(mailSts[1]);
			else{
				inbox.setStatus(Status.getStatus(loanReq.getStatusId()));
			}
			inbox.setLoanReceiveId(loanReq.getLoanId());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("LOAN");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
		} catch (LoanRequestDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox == null ? null : inbox;
	}

	public void populateInboxForLoanRequestor(int esrMapId, int sequence, int requestorId, String subject, String bodyText) {
		InboxDao inboxDao = InboxDaoFactory.create();
		Inbox inbox = new Inbox();
		LoanRequestDao loanReqDao = LoanRequestDaoFactory.create();
		try{
			String[] subSts = subject.split("@");
			String sql = "SELECT * FROM LOAN_REQUEST WHERE  ESR_MAP_ID=?    AND SEQUENCE =? ORDER BY CREATED_DATETIME DESC;";
			LoanRequest loanReqDto = loanReqDao.findByDynamicSelect(sql, new Object[] { esrMapId, sequence })[0];
			inbox.setSubject(subSts[0]);
			inbox.setAssignedTo(loanReqDto.getAssignTo());
			inbox.setRaisedBy(loanReqDto.getLoanUserId());
			if (subSts.length > 1) inbox.setStatus(subSts[1]);
			else inbox.setStatus(Status.getStatus(loanReqDto.getStatusId()));
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("LOAN");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
			inbox.setReceiverId(requestorId);
			inboxDao.insert(inbox);
		} catch (LoanRequestDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void getLoanRequestDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		LoanRequestDao loanReqDao = LoanRequestDaoFactory.create();
		LoanDetailsDao loanDao = LoanDetailsDaoFactory.create();
		Inbox inboxForm = (Inbox) form;
		Login login = Login.getLogin(request);
		inboxForm.setReceiverId(login.getUserId());
		InboxDao inboxDao = InboxDaoFactory.create();
		EmpSerReqMapDao eDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao pChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProcessChain processChain = null;
		//StatusDao statusDao = StatusDaoFactory.create();
		UsersDao userDao = UsersDaoFactory.create();
		EmpSerReqMap eReqMap = null;
		LoanRequest[] loanReqDtoArr = null;
		LoanRequest loanReqDto = null;
		LoanDetails loanDto = null;
		try{
			pChainDao.setMaxRows(1);
			eReqMap = eDao.findByPrimaryKey(inboxForm.getEsrMapId());
			processChain = pChainDao.findByPrimaryKey(eReqMap.getProcessChainId());
			String sql1 = "SELECT * FROM LOAN_REQUEST WHERE ESR_MAP_ID=? AND ASSIGN_TO =?  ORDER BY ID DESC";
			loanReqDtoArr = loanReqDao.findByDynamicSelect(sql1, new Object[] { inboxForm.getEsrMapId(), login.getUserId() });
			if (loanReqDtoArr.length > 0){
				loanReqDto = loanReqDtoArr[0];
			} else{
				loanReqDtoArr = loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE ESR_MAP_ID=? ORDER BY ID DESC", new Object[] { inboxForm.getEsrMapId() });
				loanReqDto = loanReqDtoArr[0];
			}
			Inbox inbox = inboxDao.findByPrimaryKey(inboxForm.getId());
			HandlerAction handlerAction = null;
			loanDto = loanDao.findByPrimaryKey(loanReqDto.getLoanId());
			Users submitUsr = userDao.findByPrimaryKey(loanReqDtoArr[0].getLoanUserId());
			ProfileInfo submtrinfo = profileInfoDao.findByPrimaryKey(submitUsr.getProfileId());
			inbox.setSubmittedBy(submtrinfo.getFirstName());
			inbox.setSubmittedOn((loanDto.getApplyDate()));
			ProfileInfo profileinfo = null;
			Users actionUsr = null;
			String currentStatus = null;
			//if(loanReqDto.getStatusId()!=17 && loanDto.getStatusId()==17 )	
			currentStatus = Status.getStatus(loanReqDto.getStatusId());
			//else if(loanReqDto.getStatusId()==17 && loanDto.getStatusId()==17)
			//currentStatus = Status.getStatus(loanDto.getStatusId());
			if (loanReqDto.getStatusId() == Status.getStatusId(Status.ACCEPTED) || (inbox.getStatus().equalsIgnoreCase(Status.ACCEPTED))){
				if (loanReqDto.getActionTakenBy() != 0){
					actionUsr = userDao.findByPrimaryKey(loanReqDto.getActionTakenBy());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				} else{
					LoanRequest[] holdLnDto = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID=? AND STATUS_ID=?", new Object[] { loanReqDto.getLoanId(), Status.getStatusId(Status.ACCEPTED) });
					actionUsr = userDao.findByPrimaryKey(holdLnDto[0].getActionTakenBy());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				}
				inbox.setApprovedBy(profileinfo.getFirstName());
				inbox.setApprovedOn(loanReqDto.getActionTakenDate());
			} else if (loanReqDto.getStatusId() == Status.getStatusId(Status.REJECTED) || inbox.getStatus().equalsIgnoreCase(Status.REJECTED)){
				if (loanReqDto.getActionTakenBy() != 0){
					actionUsr = userDao.findByPrimaryKey(loanReqDto.getActionTakenBy());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				} else{
					LoanRequest[] holdLnDto = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID=? AND STATUS_ID=?", new Object[] { loanReqDto.getLoanId(), Status.getStatusId(Status.REJECTED) });
					actionUsr = userDao.findByPrimaryKey(holdLnDto[0].getActionTakenBy());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				}
				inbox.setRejectedBy((profileinfo.getFirstName()));
				inbox.setRejectedOn((loanReqDto.getActionTakenDate()));
			} else if (loanReqDto.getStatusId() == Status.getStatusId(Status.COMPLETE) || loanReqDto.getStatusId() == Status.getStatusId(Status.COMPLETED) || loanReqDto.getStatusId() == Status.getStatusId(Status.ASSIGNED)){
				actionUsr = userDao.findByPrimaryKey(loanReqDto.getActionTakenBy());
				profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				inbox.setApprovedBy(profileinfo.getFirstName());
				inbox.setApprovedOn(loanReqDto.getActionTakenDate());
			} else if (loanReqDto.getStatusId() == Status.getStatusId(Status.ON_HOLD) || inbox.getStatus().equalsIgnoreCase(Status.ON_HOLD)){
				if (loanReqDto.getActionTakenBy() != 0){
					actionUsr = userDao.findByPrimaryKey(loanReqDto.getActionTakenBy());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				} else{
					LoanRequest[] holdLnDto = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID=? AND STATUS_ID=?", new Object[] { loanReqDto.getLoanId(), Status.getStatusId(Status.ON_HOLD) });
					actionUsr = userDao.findByPrimaryKey(holdLnDto[0].getActionTakenBy());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				}
				inbox.setHoldBy((profileinfo.getFirstName()));
				inbox.setHoldOn((loanReqDto.getActionTakenDate()));
			} else if (loanReqDto.getStatusId() == Status.getStatusId(Status.CANCEL) || (inbox.getStatus().equalsIgnoreCase(Status.CANCELLED))){
				if (loanReqDto.getActionTakenBy() != 0){
					actionUsr = userDao.findByPrimaryKey(loanReqDto.getActionTakenBy());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				} else{
					LoanDetails holdLnDto = loanDao.findByPrimaryKey(loanReqDto.getLoanId());
					actionUsr = userDao.findByPrimaryKey(holdLnDto.getRequesterId());
					profileinfo = profileInfoDao.findByPrimaryKey(actionUsr.getProfileId());
				}
				inbox.setApprovedBy(profileinfo.getFirstName());
				inbox.setApprovedOn(loanReqDto.getActionTakenDate());
			}
			/*Status[] status = statusDao.findByDynamicSelect(SQL_STATUS_SELECT, new Object[] { inbox.getStatus() });
			if (status != null && status.length > 0){
				inbox.setStatusId(status[0].getId());
			}*/
			inbox.setStatusId(Status.getStatusId(inbox.getStatus()));
			if (currentStatus != null){
				inbox.setCurrentStatus(currentStatus);
			}
			if (loanReqDto.getAssignTo() == login.getUserId()){
				pEvaluator = new ProcessEvaluator();
				int approvalLevel = pEvaluator.findLastAppLevel(eReqMap);
				if (inboxForm.getStatus().equalsIgnoreCase(Status.ON_HOLD) || inboxForm.getStatus().equalsIgnoreCase(Status.SUBMITTED)){
					if (currentStatus.equalsIgnoreCase(Status.ACCEPTED) || currentStatus.equalsIgnoreCase(Status.REJECTED)){
						if (loanReqDto.getSequence() == 0){
							//approvalLevel=(approvalLevel != 0 ? approvalLevel : 0);
							approvalLevel = 0;
							handlerAction = this.populateHandlerActionForLoan(processChain, login.getUserId(), approvalLevel, inbox.getRaisedBy(), dropDownForm);
						} else handlerAction = new HandlerAction();
					} else if (inboxForm.getStatus().equalsIgnoreCase(Status.ON_HOLD) || currentStatus.equalsIgnoreCase(Status.ON_HOLD)){
						if (loanReqDto.getActionTakenBy() == login.getUserId()){
							approvalLevel = (approvalLevel != 0 ? approvalLevel : 0);
							handlerAction = this.populateHandlerActionForLoan(processChain, login.getUserId(), approvalLevel, inbox.getRaisedBy(), dropDownForm);
							handlerAction.setOnHold(0);
						} else{
							handlerAction = new HandlerAction();
						}
					} else{
						if (currentStatus.equalsIgnoreCase(Status.COMPLETE)) handlerAction = new HandlerAction();
						else{
							approvalLevel = (approvalLevel != 0 ? approvalLevel : 0);
							handlerAction = this.populateHandlerActionForLoan(processChain, login.getUserId(), approvalLevel, inbox.getRaisedBy(), dropDownForm);
						}
					}
				}
			} else{
				handlerAction = new HandlerAction();
			}
			inbox.setHandleraction(handlerAction);
			dropDownForm.setDetail(inbox);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public HandlerAction populateHandlerActionForLoan(ProcessChain processChain, int userId, int approvalLevel, int raisedBy, DropDown dropDownForm) {
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		boolean isInApproval = false;
		boolean isInHandlerAction = false;
		HandlerAction handlerAction = new HandlerAction();
		Integer[] approvers = pEvaluator.approvers(processChain.getApprovalChain(), approvalLevel, raisedBy);
		Integer[] handlers = pEvaluator.handlers(processChain.getHandler(), raisedBy);
		for (int user : approvers){
			if (user == userId){
				isInApproval = true;
				break;
			}
		}
		for (int user : handlers){
			if (user == userId){
				isInHandlerAction = true;
				break;
			}
		}
		if (isInApproval){
			handlerAction.setApprove(1);
			handlerAction.setReject(1);
			handlerAction.setOnHold(1);
		} else if (isInHandlerAction){
			handlerAction.setAssign(1);
			dropDownForm.setKey1(1);
		}
		return handlerAction;
	}

	/**
	 * |--Method to populate inbox for Issue Handler
	 */
	public Inbox populateInboxOfRequestorOnIssuesHandled(int esrMapId, String subject, String status, int AssignedTo, int receiverId, int RaisedBy, String bodyText) {
		InboxDao inboxDao = InboxDaoFactory.create();
		Inbox inbox = new Inbox();
		try{
			inbox.setSubject(subject);
			inbox.setAssignedTo(AssignedTo);
			inbox.setRaisedBy(RaisedBy);
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("ISSUES");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(bodyText);
			inbox.setReceiverId(receiverId);
			InboxPk inboxPk = inboxDao.insert(inbox);
			inbox.setId(inboxPk.getId());
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox == null ? null : inbox;
	}

	public void populateInboxForAppraisal(int esrMapId, String subject, String status, int AssignedTo, int receiverId, int RaisedBy, String bodyText) {
		populateInbox(esrMapId, subject, status, AssignedTo, receiverId, RaisedBy, bodyText, "APPRAISAL");
	}

	public void populateInboxForExitEmployee(int esrMapId, String subject, String status, int assignedTo, int receiverId, int raisedBy, String bodyText) {
		populateInbox(esrMapId, subject, status, assignedTo, receiverId, raisedBy, bodyText, "EXIT");
	}

	public void populateInbox(int esrMapId, String subject, String status, int assignedTo, int receiverId, int raisedBy, String bodyText, String category) {
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			populateInbox(esrMapId, subject, status, assignedTo, receiverId, raisedBy, bodyText, category, conn);
		} catch (Exception e){} finally{
			ResourceManager.close(conn);
		}
	}
	public void populateInbox(int esrMapId, String subject, String status, int assignedTo, int receiverId, int raisedBy, String bodyText, String category, Connection conn) {
		try{
			InboxDaoFactory.create().insert(new Inbox(receiverId, esrMapId, subject, assignedTo, raisedBy, new Date(), status, category, 0, 0, bodyText));
		} catch (Exception e){
			logger.error("unable to populate " + category + " request into inbox", e);
		}
	}

	private void getAppraisalDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inbox = null, inboxForm = (Inbox) form;
		try{
			inbox = InboxDaoFactory.create().findByPrimaryKey(inboxForm.getId());
			inbox.setSubmittedBy(inbox.getRaisedByName());
			inbox.setSubmittedOn(inbox.getCreatedOn());
			inbox.setStatusId(Status.getStatusId(inbox.getStatus()));
		} catch (InboxDaoException e){
			e.printStackTrace();
		}
		dropDownForm.setDetail(inbox);
	}

	private void getExitDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inbox = null, inboxForm = (Inbox) form;
		try{
			inbox = InboxDaoFactory.create().findByPrimaryKey(inboxForm.getId());
			inbox.setSubmittedBy(inbox.getRaisedByName());
			inbox.setSubmittedOn(inbox.getCreatedOn());
			inbox.setStatusId(Status.getStatusId(inbox.getStatus()));
			try{
				ExitEmployee exitEmployee[] = ExitEmployeeDaoFactory.create().findWhereEsrMapIdEquals(inbox.getEsrMapId());
				if (exitEmployee != null && exitEmployee.length == 1){
					inbox.setEsrMapId(exitEmployee[0].getId());
					if (exitEmployee[0].getStatusId() == Status.getStatusId(Status.EXIT_FORMALITIES)){
						List<Integer> itHandlers = ExitModel.getInstance().getITHandlers(exitEmployee[0].getId());
						List<Integer> adminHandlers = ExitModel.getInstance().getAdminHandlers(exitEmployee[0].getId());
						List<Integer> financeHandlers = ExitModel.getInstance().getFinanceHandlers(exitEmployee[0].getId());
						Login login = Login.getLogin(request);
						ExitNocInboxFlags exitNocInboxFlags = new ExitNocInboxFlags();
						if (itHandlers.contains(login.getUserId())) exitNocInboxFlags.setItNoc(1);
						if (adminHandlers.contains(login.getUserId())) exitNocInboxFlags.setAdminNoc(1);
						if (financeHandlers.contains(login.getUserId())) exitNocInboxFlags.setFinanceNoc(1);
						inbox.setHandleraction(exitNocInboxFlags);
					}
				}
			} catch (Exception e){}
		} catch (InboxDaoException e){
			e.printStackTrace();
		}
		dropDownForm.setDetail(inbox);
	}

	private void getInsuranceDetail(PortalForm form, HttpServletRequest request, DropDown dropDownForm) {
		Inbox inbox = null, inboxForm = (Inbox) form;
		try{
			//UsersDao usersDao = UsersDaoFactory.create();
			//Login loginDto = Login.getLogin(request);
			//Users hndUser = usersDao.findByPrimaryKey(loginDto.getUserId());
			///int esr_map_id = inboxForm.getEsrMapId();
			//HandlerAction handlerAction = new HandlerAction();
			//handlerAction.setAssign(new Integer(getInsuranceActionPermision(esr_map_id, hndUser.getId())));
			inbox = InboxDaoFactory.create().findByPrimaryKey(inboxForm.getId());
			inbox.setSubmittedBy(inbox.getRaisedByName());
			inbox.setSubmittedOn(inbox.getCreatedOn());
			inbox.setStatusId(Status.getStatusId(inbox.getStatus()));
			inbox.setAssignedToName(ModelUtiility.getInstance().getEmployeeName(inbox.getAssignedTo()));
			//inbox.setHandleraction(handlerAction);
		} catch (InboxDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dropDownForm.setDetail(inbox);
	}
	/*private int getInsuranceActionPermision(int esrMapId, int hndUserId) {
		int count = 0;
		try{
			InsurancePolicyReqDao insPolicyReqDao = InsurancePolicyReqDaoFactory.create();
			InsuranceHandlerChainReqDao insHndChainReqDao = InsuranceHandlerChainReqDaoFactory.create();
			InsurancePolicyReq insPolicyReq[] = insPolicyReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { new Integer(esrMapId) });
			if (insPolicyReq.length >= 1){
				for (int i = 0; i < insPolicyReq.length; i++){
					InsurancePolicyReq insPolicyReq1 = insPolicyReq[i];
					InsuranceHandlerChainReq InsHndChainReqs[] = insHndChainReqDao.findByDynamicWhere("ESR_MAP_ID=? AND POLICY_REQ_ID=?", new Object[] { new Integer(esrMapId), insPolicyReq1.getId() });
					if (InsHndChainReqs[0].isAssignToNull() || hndUserId == InsHndChainReqs[0].getAssignTo()){
						count = 1;
					} else{
						count = 0;
					}
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
			return 0;
		}
		return count;
	}*/
}// End of class
