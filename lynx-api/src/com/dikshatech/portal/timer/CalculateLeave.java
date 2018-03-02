package com.dikshatech.portal.timer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.LeaveEvaluator;

public class CalculateLeave implements Job
{

	private static Logger logger = Logger.getLogger(CalculateLeave.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{

		/**
		 * put code to invoke the calculator method
		 */
		try
		{
			logger.info("CalculateLeave execute method invoked.");
			LeaveEvaluator lEvaluator = new LeaveEvaluator();
			lEvaluator.evaluate();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
