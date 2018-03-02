package com.dikshatech.portal.timer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.dikshatech.common.utils.BirthdayNotifier;

public class BirthdayNotify implements Job {

	private static Logger	logger	= Logger.getLogger(BirthdayNotify.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * put code to invoke the calculator method
		 */
		BirthdayNotifier birthdayNotifier = new BirthdayNotifier();
		try{
			birthdayNotifier.wishBirthDayEmployee();
		} catch (Exception e){
			e.printStackTrace();
		}
		try{
			//birthdayNotifier.sendAnniversariMails();
		} catch (Exception e){
			logger.error("unable to send anniversary mails", e);
		}
	}
}
