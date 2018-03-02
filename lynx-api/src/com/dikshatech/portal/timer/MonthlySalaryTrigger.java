package com.dikshatech.portal.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.portal.exceptions.FbpReqDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.VoluntaryProvidentFundDaoException;

public class MonthlySalaryTrigger implements Job {
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		MonthSalaryJob ms = new MonthSalaryJob();
		try {
			try {
				ms.generateReportAndNotify();
			} catch (VoluntaryProvidentFundDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SalaryDetailsDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FbpReqDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


