package com.dikshatech.portal.timer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.dikshatech.common.utils.TimeSheetNotifier;

public class TimeSheetNotify implements Job {

	private static Logger	logger	= Logger.getLogger(TimeSheetNotify.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TimeSheetNotifier timesheetNotifier = new TimeSheetNotifier();
		/**
		 * put code to invoke the calculator method
		 */
		try{
			logger.info("TimeSheetNotify execute method invoked.");
			timesheetNotifier.notifyEmployeesOnTimeSheetDueDatePassed();
		} catch (Exception e){
			e.printStackTrace();
		}
		//move old request details to backup table...
		timesheetNotifier.removeOldTimesheetsrequests();
	}
}
