package com.dikshatech.portal.actions;

/**
 * @author h.rajesh
 */

public enum ActionComponents {
	CALENDAR, USER, LOGIN, COMPANY, REGION, DIVISION, LEVELS, ROLES, LOAN, PERMISSION, FEATURES, PROCESS, MODULES,
	MASTER, CANDIDATE, PROFILEINFO, EXPERIENCEINFO, EDUCATIONINFO, FINANCEINFO, PASSPORTINFO, PERSONALINFO, UNKNOWN,
	TIMESHEET, PROCESSCHAIN, NOMINEEINFO, SALARYDETAILS, ACCESSIBILITY, INBOX, ROLLONOFF, POLICY, SODEXO, 
	SERVICEREQINFO, TRAVEL, PAYSLIP, LEAVE, CLIENT, PROJECT, SKILLSET, EMPLOYEMENTTYPE, PODETAILS, PROJECTTASKS,
	REIMBURSEMENT, ROLLON, COMMITMENT, ISSUE, BANKDETAILS, ITSUPPORT, NEWS, CONTACTTYPE, FESTIVALWISHS, APPRAISAL,
	REFERFRIEND, RESUMEMANAGEMENT, INSURANCE, EXITEMPLOYEE, PROCESSCHAINESCALATION,
	FAQ, RECONCILIATION, FBP, SALARYRECONCILIATION,TAXBENEFIT,BONUSRECONCILIATION,RESOURCEREQUIREMENT,USERS,RETENTIONBONUSRECONCILIATION,TIMESHEETRECONCILIATION, INVOICERECONCILIATION;

	public static ActionComponents getValue(String value) {
		try {
		return valueOf(value);
		
		} catch (Exception e) {
			return UNKNOWN;
		}
	};
}