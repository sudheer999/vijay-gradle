package com.dikshatech.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

/**
 * @author gurunath.rokkam
 */
public class TimeSheetNotifier {

	private static Logger	logger	= LoggerUtil.getLogger(TimeSheetNotifier.class);

	public TimeSheetNotifier() {}

	public void notifyEmployeesOnTimeSheetDueDatePassed() {
		String[] ids = null;
		try{
			ids = ProfileInfoDaoFactory.create().findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS U WHERE U.ID NOT IN (SELECT USER_ID FROM TIME_SHEET_DETAILS WHERE END_DATE=DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND STATUS NOT IN ('NEW','CANCELLED','REJECTD')) AND U.ID NOT IN (SELECT USER_ID FROM CONSULTANTS ) AND EMP_ID !=0 AND U.ID NOT IN(1,2,3,4) AND U.STATUS NOT IN(1,2,3)) AND OFFICAL_EMAIL_ID IS NOT NULL AND REPORTING_MGR!=4");
		} catch (ProfileInfoDaoException e1){
			e1.printStackTrace();
		}
		if (ids == null || ids.length < 1){
			logger.error("Unable to fetch email ids of all employees.");
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String endDate = PortalUtility.getdd_MM_yyyy(new Date(cal.getTimeInMillis()));
		cal.add(Calendar.DAY_OF_MONTH, -6);
		String startDate = PortalUtility.getdd_MM_yyyy(new Date(cal.getTimeInMillis()));
		String period = startDate + " to " + endDate;
		try{
			PortalMail portalMail = new PortalMail();
			portalMail.setMailSubject("Timesheet Reminder for " + period);
			portalMail.setTemplateName(MailSettings.NOTIFICATION_TO_EMPLOYEE_ON_DUE_DATE_PASSED);
			portalMail.setCandidateName("Employee");
			portalMail.setTsheetDueDate(endDate);
			portalMail.setAllReceipientBCCMailId(ids);
			portalMail.setTimePeriod(period);
			new MailGenerator().invoker(portalMail);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @author gurunath.rokkam
	 * @since jan 07,2013
	 */
	public void removeOldTimesheetsrequests() {
		//Code commented to allow transaction as MySQL uses safe mode.
		//JDBCUtiility.getInstance().update("DELETE FROM TIMESHEET_REQ WHERE TSHEET_ID IN (SELECT ID FROM TIME_SHEET_DETAILS Y WHERE TIMESTAMPDIFF(DAY,START_DATE,CURDATE()) >15 AND STATUS='" + Status.APPROVED + "')", null);
		
		final String SELECT_TDIDS="SELECT ID FROM TIME_SHEET_DETAILS Y WHERE TIMESTAMPDIFF(DAY,START_DATE,CURDATE()) >15 AND STATUS='" + Status.APPROVED + "'";
		
		JDBCUtiility jdbcUtil=JDBCUtiility.getInstance();
		List<Object> tdIds=jdbcUtil.getSingleColumn(SELECT_TDIDS, null);
		String tdIdsCsv=ModelUtiility.getCommaSeparetedValues(tdIds);
		
		jdbcUtil.update("DELETE FROM TIMESHEET_REQ WHERE TSHEET_ID IN ("+tdIdsCsv+")", null);
	}
}
