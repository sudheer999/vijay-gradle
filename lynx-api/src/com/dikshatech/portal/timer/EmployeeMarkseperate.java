package com.dikshatech.portal.timer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.TimeSheetNotifier;
import com.dikshatech.common.utils.UserMarkAsSeperated;
import com.dikshatech.portal.actions.ActionResult;

public class EmployeeMarkseperate implements Job
{
	boolean flag=false;
	private static Logger logger = Logger.getLogger(EmployeeMarkseperate.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		/**
		 * put code to change  employee status as seperated
		 */
		try
		{
			logger.info("Executing method employee status change to seperated .");
			UserMarkAsSeperated markAsSeperated= new UserMarkAsSeperated();
			flag=markAsSeperated.markAsSeperated();
			if(!flag){
				throw new JobExecutionException();
			}
		}
		catch (Exception e)
		{
			logger.debug("error in Employee mark as seperate scheduler.....");
			e.printStackTrace();
		}
		
	}

}
