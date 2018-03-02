package com.dikshatech.portal.mail;

public interface MailSettings {

	public static final String	FROM_MAILID											= "FROM_MAILID";
	public static final String	HOST_IP												= "HOST_IP";
	public static final String	PASSWORD											= "PASSWORD";
	public static final String	PORT_NO												= "PORT_NO";
	public static final String	AUTHENTICATION										= "AUTHENTICATION";
	public static final String	START_TLS											= "START_TLS";
	public static final String	SOC_FACT_CLASS										= "SOC_FACT_CLASS";
	public static final String	FALLBACK											= "FALLBACK";
	public static final String	DEBUG												= "DEBUG";
	public static final String	PROTOCOL											= "PROTOCOL";
	public static final String	TEMPLATE											= "TEMPLATE";
	/**
	 * email template names
	 */
	public static final String	CANDIDATE_OFFER										= "CANDIDATE_OFFER.html";
	public static final String	CANDIDATE_OFFER_PLINK								= "CANDIDATE_OFFER_PLINK.html";
	public static final String	CANDIDATE_OFFER_ACCEPT								= "CANDIDATE_OFFER_ACCEPT.html";
	public static final String	CANDIDATE_NO_SHOW									= "CANDIDATE_NO_SHOW.html";
	public static final String	CANDIDATE_OFFER_REJECT								= "CANDIDATE_OFFER_REJECT.html";
	public static final String	CANDIDATE_TAT_ACCEPTANCE							= "CANDIDATE_TAT_ACCEPTANCE.html";
	public static final String	CANDIDATE_TAT_REJECT								= "CANDIDATE_TAT_REJECT.html";
	public static final String	CANDIDATE_NEED_TIME									= "CANDIDATE_OFFER_NEED_TIME.html";
	public static final String	CANDIDATE_TAT_NEED_MORE_TIME						= "CANDIDATE_TAT_NEED_MORE_TIME.html";
	public static final String	NOTIFICATION_OFFER_SEND_TAT							= "NOTIFICATION_OFFER_SEND_TAT.html";
	public static final String	NOTIFICATION_OFFER_RESEND_TAT						= "NOTIFICATION_OFFER_RESEND_TAT.html";
	public static final String	OFFERSEND_NOTIFICATION								= "OFFERSEND_NOTIFICATION.html";
	public static final String	RESEND_OFFER_NOTIFICATION							= "RESEND_OFFER_NOTIFICATION.html";
	public static final String	OFFER_APPROVAL_NOTIFICATION							= "OFFER_APPROVAL_NOTIFICATION.html";
	public static final String	OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR			= "OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR.html";
	public static final String	UPDATE_PROFILE										= "UPDATE_PROFILE.html";
	public static final String	EMPLOYEE_SEPERATION_NOTIFY_USER						= "EMPLOYEE_SEPERATION_NOTIFY_USER.html";
	public static final String	EMPLOYEE_SEPERATION									= "EMPLOYEE_SEPERATION.html";
	public static final String	EMPLOYEE_UPDATION_RM								= "EMPLOYEE_UPDATION_RM.html";
	public static final String	EMPLOYEE_UPDATION_SPOC								= "EMPLOYEE_UPDATION_SPOC.html";
	public static final String	EMPLOYEE_UPDATION_RM_NOTIFY							= "EMPLOYEE_UPDATION_RM_NOTIFY.html";
	public static final String	EMPLOYEE_UPDATION_SPOC_NOTIFY						= "EMPLOYEE_UPDATION_SPOC_NOTIFY.html";
	public static final String	EMPLOYEE_ABSCONDING_NOTIFY_USER						= "EMPLOYEE_ABSCONDING_NOTIFY_USER.html";
	public static final String	EMPLOYEE_ABSCONDING									= "EMPLOYEE_ABSCONDING.html";
	public static final String	EMPLOYEE_WELCOME									= "EMPLOYEE_WELCOME.html";
	public static final String	EMPLOYEE_PROBATION									= "EMPLOYEE_PROBATION.html";
	public static final String	EMPLOYEE_PROBATION_NOTIFY							= "EMPLOYEE_PROBATION _NOTIFY.html";
	public static final String	ADDED_BANK_DETAILS									= "ADDED_BANK_DETAILS.html";
	public static final String	NEW_BANK_DETAILS									= "NEW_BANK_DETAILS.html";
	public static final String	EMPLOYEE_FORGOT_PASSWORD							= "EMPLOYEE_FORGOT_PASSWORD.html";
	public static final String	CANDIDATE_MARKED_AS_EMPLOYEE						= "CANDIDATE_MARKED_AS_EMPLOYEE.html";
	public static final String	CANDIDATE_NOTIFY_DEPT								= "CANDIDATE_NOTIFY_DEPT.html";
	public static final String	CANDIDATE_REPORTING_DETAILS							= "CANDIDATE_REPORTING_DETAILS.html";
	public static final String	EMPLOYEE_PROBATION_DETAILS							= "EMPLOYEE_PROBATION_DETAILS.html";
	public static final String	EMPLOYEE_PROBATION_DETAILS_POSTDATE					= "EMPLOYEE_PROBATION_DETAILS_POSTDATE.html";
	public static final String	EMPLOYEE_PROBATION_CONFIRM							= "EMPLOYEE_PROBATION_CONFIRM.html";
	public static final String	EMPLOYEELOGIN										= "EMPLOYEELOGIN.html";
	
	
	public static final String	AMOUNT_TO_BE_DEDUCTED								=	"AMOUNT_TO_BE_DEDUCTED.html";                               
	
	public static final String	AMOUNT_DEDUCTED								        =	"AMOUNT_DEDUCTED.html";
	
	
	public static final String	PAYMENT_NOTIFICATION								 =	"PAYMENT_NOTIFICATION.html";
	/**
	 * @author atul.patil
	 * Email Template for Employee Job Anniversary Reminder
	 */
	public static final String	EMPLOYEE_JOB_ANNIVERSARY_REMINDER_CONFIRM			= "EMPLOYEE_JOB_ANNIVERSARY_REMINDER_CONFIRM.html";
	// template for (1 to 5 years)
	public static final String	ANNIVERSARY										    = "ANNIVERSARY.html";
	
	
	public static final String	ANNIVERSARYJ_1										    = "ANNIVERSARYJ_1.html";
	public static final String	ANNIVERSARYJ_2										    = "ANNIVERSARYJ_2.html";
	public static final String	ANNIVERSARYJ_3										    = "ANNIVERSARYJ_3.html";
	public static final String	ANNIVERSARYJ_4										    = "ANNIVERSARYJ_4.html";
	public static final String	ANNIVERSARYJ_5										    = "ANNIVERSARYJ_5.html";
	
	// template for(5 to coming years)
	
	public static final String	ANNIVERSARY_M5										= "ANNIVERSARY_M5.html";
	/**
	 * Email Template for ROLL_ON
	 **/
	public static final String	ROLL_ON_EMP											= "ROLL_ON_EMP.html";
	public static final String	ROLL_ON_INFO										= "ROLL_ON_INFO.html";
	public static final String	CALENDAR											= "CALENDAR.html";
	/**
	 * Email Template for Leave
	 */
	/*public static final String	LEAVE_ACCPECT										= "LEAVE_ACCPECT.html";
	public static final String	LEAVE_APPROVE										= "LEAVE_APPROVE_BY.html";
	public static final String	LEAVE_APPROVER										= "LEAVE_APPROVER.html";
	public static final String	LEAVE_CANCELLED										= "LEAVE_CANCELLED.html";
	public static final String	LEAVE_REJECT										= "LEAVE_REJECT.html";
	public static final String	LEAVE_REJECTER										= "LEAVE_REJECTER.html";
	public static final String	LEAVE_APPROVE_FOR_SIBLING							= "LEAVE_APPROVE_FOR_SIBLING.html";
	public static final String	LEAVE_REJECT_FOR_SIBLING							= "LEAVE_REJECT_FOR_SIBLING.html";
	public static final String	LEAVE_CANCELL_FOR_SIBLING							= "LEAVE_CANCELL_FOR_SIBLING.html";
	public static final String	LEAVE_CANCELLER										= "LEAVE_CANCELLER.html";
	public static final String	LEAVE_LWP_NOTIFY									= "LEAVE_LWP_NOTIFY.html";
	public static final String	LEAVE_LWP_COMPLETED									= "LEAVE_LWP_COMPLETED.html";	*/
	public static final String	LEAVE_APPLY											= "LEAVE_APPLY.html";
	public static final String	LEAVE_LWP_USER_NOTIFY								= "LEAVE_LWP_USER_NOTIFY.html";
	public static final String	LEAVE_RECEIVE										= "LEAVE_RECEIVE.html";
	public static final String	LEAVE_LWP_COMPLETED_USER_NOTIFY						= "LEAVE_LWP_COMPLETED_USER_NOTIFY.html";
	public static final String	LEAVE_STATUS_NOTIFY_FOR_SIBLING						= "LEAVE_STATUS_NOTIFY_FOR_SIBLING.html";
	public static final String	LEAVE_NOTIFY_USER									= "LEAVE_NOTIFY_USER.html";
	/**
	 * email template names for REIMBURSEMENT
	 */
	public static final String	REIMBURSEMENT_USER									= "REIMBURSEMENT_USER.html";
	public static final String	REIMBURSEMENT_HOLD									= "REIMBURSEMENT_USER_ONHOLD.html";
	public static final String	REIMBURSEMENT_APPROVER_HOLD							= "REIMBURSEMENT_HOLD_APPROVER.html";
	public static final String	REIMBURSEMENT_ACCEPTED								= "REIMBURSEMENT_USER_ACCEPTED.html";
	public static final String	REIMBURSEMENT_APPROVER_ACCEPTED						= "REIMBURSEMENT_ACCEPTED_APPROVER.html";
	public static final String	REIMBURSEMENT_REJECTED								= "REIMBURSEMENT_USER_REJECTED.html";
	public static final String	REIMBURSEMENT_APPROVER_REJECTED						= "REIMBURSEMENT_REJECTED_APPROVER.html";
	public static final String	REIMBURSEMENT_IN_FINANCEDEPT						= "REIMBURSEMENT_IN_FINANCE.html";
	public static final String	REIMBURSEMENT_HR_RM_MAIL							= "REIMBURSEMENT_HR_OR_RM_MAIL.html";
	public static final String	REIMBURSEMENT_HR_ACCEPTED							= "REIMBURSEMENT_USER_HR.html";
	public static final String	REIMBURSEMENT_PROCESSED								= "REIMBURSEMENT_PROCESSED.html";
	public static final String	REIMBURSEMENT_APPROVAL_RM							= "REIMBURSEMENT_APPROVAL_RM.html";
	/**
	 * email template names for time-sheet
	 */
	public static final String	TSHEET_SUBMITTED_TO_APPROVER						= "NOTIFICATION_TO_APPROVER_TS_SUBMITTED.html";
	public static final String	NOTIFICATION_TO_EMPLOYEE_ON_DUE_DATE_PASSED			= "NOTIFICATION_TO_EMP_AFTER_DUE_DATE.html";
	public static final String	TS_NOTIFY_USER										= "TS_NOTIFY_USER.html";
	/*public static final String	TSHEET_SUBMITTED_SUCCESSFULLY						= "TS_SUBMITTED_SUCCESSFULLY.html";
	public static final String	TSHEET_APPROVED_SUCCESSFULLY						= "TS_APPROVED.html";
	public static final String	TSHEET_REJECTED										= "TS_REJECTED.html";
	public static final String	TSHEET_ONHOLD										= "TS_ONHOLD.html";
	public static final String	TSHEET_CANCELLED									= "TS_CANCELLED.html";
	public static final String	NOTIFICATION_TO_HRD_ON_APPROVE						= "NOTIFICATION_TO_HRD_TS_APPROVED.html";
	public static final String	NOTIFICATION_TO_HRD_ON_REJECT						= "NOTIFICATION_TO_HRD_TS_REJECTED.html";
	public static final String	NOTIFICATION_TO_HRD_ON_CANCELLED					= "NOTIFICATION_TO_HRD_TS_CANCELLED.html";
	public static final String	NOTIFICATION_TO_EMPLOYEE_ON_DUE_DATE_PASSED			= "NOTIFICATION_TO_EMP_AFTER_DUE_DATE.html";
	public static final String	TSHEET_APPROVE_CONFIRMATION_TO_APPROVERS			= "APPROVER_APPROVED_TS_CONFIRMATION.html";
	public static final String	TSHEET_REJECT_CONFIRMATION_TO_APPROVERS				= "APPROVER_REJECTED_TS_CONFIRMATION.html";
	public static final String	TSHEET_ONHOLD_CONFIRMATION_TO_APPROVERS				= "APPROVER_ONHOLD_TS_CONFIRMATION.html";
	public static final String	NOTIFICATION_TO_HRD_ON_HOLD							= "NOTIFICATION_TO_HRD_TS_HOLD.html";
	public static final String	NOTIFICATION_TO_FIN_TS								= "NOTIFICATION_TO_FIN_TS.html";*/
	/**
	 * email template names for PROJECT
	 */
	public static final String	PROJECT_DETAILS_ADDED								= "PROJECT_DETAILS_ADDED.html";
	public static final String	PROJECT_DETAILS_DISABLED							= "PROJECT_DETAILS_DISABLED.html";
	public static final String	PROJECT_DETAILS_ENABLED								= "PROJECT_DETAILS_ENABLED.html";
	public static final String	PROJECT_DETAILS_UPDATED								= "PROJECT_DETAILS_UPDATED.html";
	/**
	 * email template names for Client
	 */
	public static final String	CLIENT_DETAILS_ADDED								= "CLIENT_DETAILS_ADDED.html";
	public static final String	CLIENT_DETAILS_UPDATED								= "CLIENT_DETAILS_UPDATED.html";
	/**
	 * email template Birthday wishes
	 */
	public static final String	BIRTHDAY_WISHES										= "BIRTHDAY_WISHES.html";
	/**
	 * email template Birthday Auto wishes
	 */
	public static final String	BIRTHDAY_WISHES_AUTO								= "BIRTHDAY_WISHES_AUTO.html";
	/**
	 * email template ITREQUEST
	 */
	public static final String	NOTIFICATION_ITREQUEST_SUBMITTED					= "NOTIFICATION_ITREQUEST_SUBMITTED.html";
	public static final String	ITREQUEST_SUBMITTED_HANDLER							= "ITREQUEST_SUBMITTED_HANDLER.html";
	public static final String	NOTIFICATION_ITREQUEST_TO_REQUESTER_STATUS			= "NOTIFICATION_ITREQUEST_TO_REQUESTER_ STATUS.html";
	public static final String	ITREQUEST_NOTIFICATION_HANDLERS						= "ITREQUEST_NOTIFICATION_HANDLERS.html";
	public static final String	NOTIFICATION_ITREQUEST_HANDLER						= "NOTIFICATION_ITREQUEST_HANDLER.html";
	public static final String	ITREQUEST_NOTIFICATION_HANDLER_ASSIGNED				= "ITREQUEST_NOTIFICATION_HANDLER_ASSIGNED.html";
	public static final String	ITREQUEST_TO_REQUESTOR_SERVICED						= "ITREQUEST_TO_REQUESTOR_SERVICED.html";
	public static final String	ITREQUEST_REOPENED_HANDLER							= "ITREQUEST_REOPENED_HANDLER.html";
	public static final String	NOTIFICATION_TO_REQUESTER_ON_ITREQUEST_NOT_CLOSED	= "NOTIFICATION_REQUESTER_ITREQUEST_NOT_CLOSED.html";
	/**
	 * email template travelreq
	 */
	public static final String	TRAVEL_REQUEST_ASSIGNED								= "TRAVEL_REQUEST_ASSIGNED.html";
	public static final String	TRAVEL_REQUEST_COMPLETED_REQUESTER					= "TRAVEL_REQUEST_COMPLETED_REQUESTER.html";
	public static final String	TRAVEL_REQUEST_CANCEL_USER							= "TRAVEL_REQUEST_CANCEL_USER.html";
	public static final String	TRAVEL_REQUEST_CANCEL_HANDLER						= "TRAVEL_REQUEST_CANCEL_HANDLER.html";
	public static final String	TRAVEL_REQUEST_CANCELLED_HRD						= "TRAVEL_REQUEST_CANCELLED_HRD.html";
	public static final String	TRAVEL_REQUEST_NOTIFIER								= "TRAVELNOTIFIER.html";
	public static final String	NOTIFICATION_TRAVEL_REQUEST_COMPLETED_HANDLER		= "NOTIFICATION_TRAVEL_REQUEST_COMPLETED_HANDLER.html";
	public static final String	NOTIFICATION_TRAVEL_REQUEST_CANCELLED_USER			= "NOTIFICATION_TRAVEL_REQUEST_CANCELLED_USER.html";
	public static final String	NOTIFICATION_TRAVEL_REQUEST_SUBMITTED				= "NOTIFICATION_TRAVEL_REQUEST_SUBMITTED.html";
	public static final String	TRAVEL_REQUEST_APPROVED_APPROVER					= "TRAVEL_REQUEST_APPROVED_APPROVER.html";
	public static final String	TRAVEL_REQUEST_REJECTED_APPROVER					= "TRAVEL_REQUEST_REJECTED_APPROVER.html";
	public static final String	TRAVEL_REQUEST_REJECTED_REQUESTOR					= "TRAVEL_REQUEST_REJECTED.html";
	/*
	 * email template names for SODEXO*/
	public static final String	SODEXO_SUBMITTED_TO_REQUESTOR						= "SODEXO_SUBMITTED.html";
	public static final String	SODEXO_REQ_RECEIVED									= "SODEXO_REQ_RECEIVED.html";
	public static final String	SODEXO_REQ_CANCELLED								= "SODEXO_REQ_CANCELLED.html";
	public static final String	SODEXO_REQ_ASSIGNED_TO_HANDLER						= "SODEXO_REQ_ASSIGNED.html";
	public static final String	SODEXO_AMOUNT_CHANGED_TO_REQUESTOR					= "SODEXO_AMOUNT_CHANGED_TO_REQUESTOR.html";
	public static final String	SODEXO_AMOUNT_CHANGED_TO_HANDLER					= "SODEXO_AMOUNT_CHANGED_TO_HANDLER.html";
	public static final String	SODEXO_REQ_SERVICED_TO_HANDLER						= "SODEXO_REQ_SERVICED_TO_HANDLER.html";
	public static final String	SODEXO_REQ_SERVICED_TO_REQUESTOR					= "SODEXO_REQ_SERVICED_TO_REQUESTOR.html";
	public static final String	SODEXO_WITHDRAWN_TO_REQUESTOR						= "SODEXO_WITHDRAWN_TO_REQUESTOR.html";
	public static final String	SODEXO_WITHDRAWN_TO_HANDLER							= "SODEXO_WITHDRAWN_TO_HANDLER.html";
	/*
	 *new templates 
	 */
	
	public static final String	MODIFICATION_SALARY_REGISTRY						= "MODIFICATION_SALARY_REGISTRY.html";
	public static final String	Notification_SODEXO_SUBMITTED_To_REQUESTOR			= "Notification_SODEXO_SUBMITTED_To_REQUESTOR.html";
	public static final String	Notification_SODEXO_AMOUNT_CHANGED_TO_REQUESTOR		= "Notification_SODEXO_AMOUNT_CHANGED_TO_REQUESTOR.html";
	public static final String	Notification_SODEXO_WITHDRAWN_TO_REQUESTOR			= "Notification_SODEXO_WITHDRAWN_TO_REQUESTOR.html";
	public static final String	Notification_SODEXO_REQ_SERVICED_TO_HANDLER			= "Notification_SODEXO_REQ_SERVICED_TO_HANDLER.html";
	public static final String	MODIFICATION_PERDIEM_REGISTRY						= "MODIFICATION_PERDIEM_REGISTRY.html";
	/*
	 * 	
	* email Templates for Loan
	*/
	public static final String	LOAN_TEMPLATE										= "LN_TEMPLATE.html";
	/**
	 * email template names for ISSUES
	 */
	public static final String	ISSUE_SUBMITTED_SUCCESSFULLY						= "ISSUE_SUBMITTED.html";
	public static final String	ISSUE_STATUS_CHANGED								= "ISSUE_STATUS_CHANGED.html";
	public static final String	NOTIFICATION_TO_APPROVER_ON_ISSUE_SUBMISSION		= "NOTIFICATION_ISSUE_TO_APPROVER.html";
	public static final String	PORTAL_LINK											= "http://lynx.dikshatech.com";
	public static final String	NOTIFICATION_TO_APPROVER_ON_ISSUE_STATUS_CHANGED	= "NOTIFICATION_APPROVER_STATUS_CHANGED.html";
	public static final String	NOTIFICATION_TO_HANDLER_ON_ISSUE_ASSIGN				= "NOTIFICATION_HANDLER_ISSUE_ASSIGNED.html";
	public static final String	NOTIFICATION_REQUESTOR_ISSUE_INPROGRESS				= "NOTIFICATION_REQUESTOR_ISSUE_INPROGRESS.html";
	public static final String	NOTIFICATION_TO_HANDLER_ON_ISSUE_CREATION			= "NOTIFICATION_HANDLER_ISSUE_CREATED.html";
	public static final String	NOTIFICATION_TO_HANDLER_ON_ISSUE_NOT_RESOLVED		= "NOTIFICATION_HANDLER_ISSUE_NOT_RESOLVED.html";
	public static final String	NOTIFICATION_TO_HANDLER_ON_ITREQUEST_NOT_RESOLVED	= "NOTIFICATION_HANDLER_ITREQUSET_NOT_RESOLVED.html";
	public static final String	NOTIFICATION_TO_HANDLER_ON_ISSUE_CLOSE				= "NOTIFICATION_HANDLER_ISSUE_CLOSED.html";
	public static final String	NOTIFICATION_TO_HANDLER_ON_ISSUE_REOPEN				= "NOTIFICATION_HANDLER_ISSUE_REOPENED.html";
	public static final String	NOTIFICATION_TO_REQUESTER_ON_ISSUE_NOT_CLOSED		= "NOTIFICATION_REQUESTER_ISSUE_NOT_CLOSED.html";
	//appraisal
	public static final String	APPRAISAL_PAF_AVAILABLE								= "APPRAISAL_PAF_AVAILABLE.html";
	public static final String	APPRAISAL_PAF_REMINDER								= "APPRAISAL_PAF_REMINDER.html";
	public static final String	APPRAISAL_SALARY_REVISION							= "APPRAISAL_SALARY_REVISION.html";
	//resume management 
	public static final String	RESUMEMANAGEMENT_NEW								= "RESUMEMANAGEMENT_NEW_INFO.html";
	// exit employee
	public static final String	EXIT_FORM_SUBMITTED_TO_APPROVER						= "EXIT_FORM_SUBMITTED_TO_APPROVER.html";
	public static final String	EXIT_FORM_SUBMITTED_TO_HANDLER						= "EXIT_FORM_SUBMITTED_TO_HANDLER.html";
	public static final String	EXIT_EMPLOYEE_ACCEPTANCE							= "EXIT_EMPLOYEE_ACCEPTANCE.html";
	public static final String	EXIT_EMPLOYEE_REJECTED								= "EXIT_EMPLOYEE_REJECTED.html";
	public static final String	EXIT_EMPLOYEE_REJECTED_RMG							= "EXIT_EMPLOYEE_REJECTED_RMG.html";
	public static final String	EXIT_EMPLOYEE_NOC_AVAILABLE							= "EXIT_EMPLOYEE_NOC_AVAILABLE.html";
	public static final String	EXIT_EMPLOYEE_NOC_ACTION							= "EXIT_EMPLOYEE_NOC_ACTION.html";
	public static final String	EXIT_EMPLOYEE_NOC_SUBMITTED							= "EXIT_EMPLOYEE_NOC_SUBMITTED.html";
	public static final String	EXIT_EMPLOYEE_WITHDRAWN_SELF_COMPLETED				= "EXIT_EMPLOYEE_WITHDRAWN_SELF_COMPLETED.html";
	public static final String	EXIT_EMPLOYEE_WITHDRAWN_TO_APPROVER					= "EXIT_EMPLOYEE_WITHDRAWN_TO_APPROVER.html";
	public static final String	EXIT_EMPLOYEE_WITHDRAWN_TO_HANDLER					= "EXIT_EMPLOYEE_WITHDRAWN_TO_HANDLER.html";
	public static final String	EXIT_EMPLOYEE_WITHDRAWN_COMPLETD					= "EXIT_EMPLOYEE_WITHDRAWN_COMPLETD.html";
	public static final String	EXIT_EMPLOYEE_RELIEVING								= "EXIT_EMPLOYEE_RELIEVING.html";
	public static final String	EXIT_EMPLOYEE_CLOSURE								= "EXIT_EMPLOYEE_CLOSURE.html";
	//Insurance policy
	public static final String	INSURANCE_NOTIFICATION_REQUESTER_SUBMITTED			= "INSURANCE_NOTIFICATION_REQUESTER_SUBMITTED.html";
	public static final String	INSURANCE_NOTIFICATION_HANDLER_SUBMITTED			= "INSURANCE_NOTIFICATION_HANDLER_SUBMITTED.html";
	public static final String	INSURANCE_NOTIFICATION_HANDLER_ASSIGNED				= "INSURANCE_NOTIFICATION_HANDLER_ASSIGNED.html";
	public static final String	INSURANCE_NOTIFICATION_REQUESTER_COMPLETED			= "INSURANCE_NOTIFICATION_REQUESTER_COMPLETED.html";
	public static final String	INSURANCE_NOTIFICATION_AVAILED						= "INSURANCE_NOTIFICATION_AVAILED.html";
	// anniversary wishes
	public static final String	ANNIVERSARY_1										= "ANNIVERSARY_1.html";
	public static final String	ANNIVERSARY_3										= "ANNIVERSARY_3.html";
	public static final String	ANNIVERSARY_5										= "ANNIVERSARY_5.html";
	public static final String	ANNIVERSARY_7										= "ANNIVERSARY_7.html";
	public static final String	ANNIVERSARY_10										= "ANNIVERSARY_10.html";
	public static final String	ANNIVERSARY_11										= "ANNIVERSARY_11.html";
	// Escalation email-template file name
	public static final String	ESCALATION											= "ESCALATION.html";
	public static final String	ESCALATION_ACK										= "ESCALATION_ACK.html";
	// passport/visa notification mail
	public static final String	PASSPORT_EXPIRE_USER_NOTIFY							= "PASSPORT_EXPIRE_USER_NOTIFY.html";
	public static final String	VISA_EXPIRE_USER_NOTIFY								= "VISA_EXPIRE_USER_NOTIFY.html";
	// perdiem reconciliation
	public static final String	PERDEIM_ACCEPTED									= "PERDIEM_ACCEPTED.html";
	public static final String	PERDEIM_APROVED										= "PERDIEM_APPROVED.html";
	public static final String	PERDEIM_REJECTED									= "PERDIEM_REJECTED.html";
	public static final String	PERDIEM_REPORT_AUTO_GENERATED						= "PERDIEM_REPORT_AUTO_GENERATED.html";
	public static final String	PERDIEM_SUBMITTED									= "PERDIEM_SUBMITTED.html";
	public static final String	PERDIEM_REVIEWED									= "PERDIEM_REVIEWED.html";
	public static final String	PERDIEM_REVIEWED_AND_SUBMITTED						= "PERDIEM_REVIEWED_AND_SUBMITTED.html";
	public static final String	PERDIEM_COMPLETED									= "PERDIEM_COMPLETED.html";
	public static final String	PERDIEM_DISBURSAL									= "PERDIEM_DISBURSAL.html";
	public static final String	PERDIEM_RELEASE										= "PERDIEM_RELEASE.html";
	public static final String	PERDIEM_HOLD_REMINDER								= "PERDIEM_HOLD_REMINDER.html";
	public static final String	PERDIEM_HOLD_ESC									= "PERDIEM_HOLD_ESC.html";
	public static final String	PERDIEM_HOLD_TASK									= "PERDIEM_HOLD_TASK.html";
	

	
	
	public static final String	TIMESHEET_RECONCILIATION_COMPLETED						= "TIMESHEET_RECONCILIATION_COMPLETED.html";
	public static final String	TIMESHEET_RECONCILIATION_REJECTED						= "TIMESHEET_RECONCILIATION_REJECTED.html";
	public static final String	TIMESHEET_RECONCILIATION_SUBMITTED						= "TIMESHEET_RECONCILIATION_SUBMITTED.html";

	
	//Bonus Reconciliation
	public static final String	BONUS_ACCEPTED									= "BONUS_ACCEPTED.html";
	public static final String	BONUS_APPROVED									= "BONUS_APPROVED.html";
	public static final String	BONUS_REJECTED									= "BONUS_REJECTED.html";
	public static final String	BONUS_REPORT_AUTO_GENERATED						= "BONUS_REPORT_AUTO_GENERATED.html";
	public static final String	BONUS_SUBMITTED									= "BONUS_SUBMITTED.html";
	public static final String	BONUS_REVIEWED									= "BONUS_REVIEWED.html";
	public static final String	BONUS_REVIEWED_SUBMITTED					    = "BONUS_REVIEWED_SUBMITTED.html";
	public static final String	BONUS_COMPLETED									= "BONUS_COMPLETED.html";
	public static final String	BONUS_DISBURSAL									= "BONUS_DISBURSAL.html";
	public static final String	BONUS_RELEASE									= "BONUS_RELEASE.html";
	public static final String	BONUS_HOLD_REMINDER								= "BONUS_HOLD_REMINDER.html";
	public static final String	BONUS_HOLD_ESC									= "BONUS_HOLD_ESC.html";
	public static final String	BONUS_HOLD_TASK									= "BONUS_HOLD_TASK.html";
	
	//Flexi Benifit Plan
	public static final String	FBP_ACK												= "FBP_ACK.html";
	public static final String	FBP_APPROVAL										= "FBP_APPROVAL.html";
	public static final String	FBP_NOTIFY											= "FBP_NOTIFY.html";
	public static final String	FBP_HANDLER											= "FBP_HANDLER.html";
	public static final String	FBP_ASSIGNED										= "FBP_ASSIGNED.html";
	public static final String	FBP_CANCELLED										= "FBP_CANCELLED.html";
	public static final String	FBP_PROCESSED										= "FBP_PROCESSED.html";
	public static final String	FBP_REJECTED										= "FBP_REJECTED.html";
	public static final String	FBP													= "FBP.html";
	public static final String	SALARY_REPORT_NOTIFICATIONS							= "SALARY_REPORT_NOTIFICATIONS.html";
	public static final String	TAX_BENEFIT_DECLARATION								= "TAX_BENEFIT_DECLARATION.html";
	public static final String	TAX_BENEFIT_DECLARATION_ACK							= "TAX_BENEFIT_DECLARATION_ACK.html";
	public static final String	TAX_BENEFIT_ACCEPT									= "TAX_BENEFIT_ACCEPT.html";
	public static final String	TAX_BENEFIT_REJECT									= "TAX_BENEFIT_REJECT.html";
	public static final String	TAX_BENEFIT_CANCEL									= "TAX_BENEFIT_CANCEL.html";
	public static final String	TAX_BENEFIT_HANDLER_UPDATE							= "TAX_BENEFIT_HANDLER_UPDATE.html";
	public static final String	SAL_RECON_REMAINDER									= "SAL_RECON_REMAINDER.html";
	public static final String	SAL_RECON_ESCAL_NOTIFICATION						= "SAL_RECON_ESCAL_NOTIFICATION.html";
	public static final String	SAL_RECON_ESC										= "SAL_RECON_ESC.html";
	public static final String	PERDIEM_REMAINDER									= "PERDIEM_REMAINDER.html";
	public static final String	PERDIEM_ESCAL_NOTIFICATION							= "PERDIEM_ESCAL_NOTIFICATION.html";
	public static final String	PERDIEM_ESC											= "PERDIEM_ESC.html";
	public static final String	REIMBURSEMENT_REMAINDER								= "REIMBURSEMENT_REMAINDER.html";
	public static final String	CANDIDATE_CHKLST_REJECTED							= "CAND_CHKLST_REJECTED.html";
	public static final String BONUS_REMAINDER                                      = "BONUS_REMAINDER.html";
	public static final String BONUS_ESCAL_NOTIFICATION                             = "BONUS_ESCAL_NOTIFICATION.html";
	public static final String BONUS_ESC                                            ="BONUS_ESC.html";
	public static final String CANDIDATE_JOININGDATE_REMAINDER                      = "CANDIDATE_JOININGDATE_REMAINDER.html";
	public static final String CANDIDATE_JOINING_IT_REMAINDER                       = "CANDIDATE_JOINING_IT_REMAINDER.html";
	public static final String CANDIDATE_OFFER_SEND_TAT_NOTIFY = "CANDIDATE_OFFER_SEND_TAT_NOTIFY.html";
	public static final String JOINING_BONUS_REMAINDER ="JOINING_BONUS_REMAINDER.html";
	
	//Refer a Friend
	public static final String	REFER_FRIEND										= "REFER_FRIEND.html";
	
	//Resource Requirement
	public static final String RESOURCE_REQ_CREATED 								= "RESOURCE_REQ_CREATED.html";
	public static final String RESOURCE_REQ_UPDATED 								= "RESOURCE_REQ_UPDATED.html";
	public static final String	RESOURCE_REQ_EXPIRED								= "RESOURCE_REQ_EXPIRED.html";
	
	/**
	 * @author atul.patil
	 * Mail template for Retention Bonus Reconciliation
	 */
	public static final String	RETENTION_BONUS_ACCEPTED									= "RETENTION_BONUS_ACCEPTED.html";
	public static final String	RETENTION_BONUS_APPROVED									= "RETENTION_BONUS_APPROVED.html";
	public static final String	RETENTION_BONUS_REJECTED									= "RETENTION_BONUS_REJECTED.html";
	public static final String	RETENTION_BONUS_REPORT_AUTO_GENERATED						= "RETENTION_BONUS_REPORT_AUTO_GENERATED.html";
	public static final String	RETENTION_BONUS_SUBMITTED									= "RETENTION_BONUS_SUBMITTED.html";
	public static final String	RETENTION_BONUS_REVIEWED									= "RETENTION_BONUS_REVIEWED.html";
	public static final String	RETENTION_BONUS_REVIEWED_SUBMITTED					    	= "RETENTION_BONUS_REVIEWED_SUBMITTED.html";
	public static final String	RETENTION_BONUS_COMPLETED									= "RETENTION_BONUS_COMPLETED.html";
	public static final String	RETENTION_BONUS_DISBURSAL									= "RETENTION_BONUS_DISBURSAL.html";
	public static final String	RETENTION_BONUS_RELEASE										= "RETENTION_BONUS_RELEASE.html";
	public static final String	RETENTION_BONUS_HOLD_REMINDER								= "RETENTION_BONUS_HOLD_REMINDER.html";
	public static final String	RETENTION_BONUS_HOLD_ESC									= "RETENTION_BONUS_HOLD_ESC.html";
	public static final String	RETENTION_BONUS_HOLD_TASK									= "RETENTION_BONUS_HOLD_TASK.html";
	
}
