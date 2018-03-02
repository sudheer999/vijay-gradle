package com.dikshatech.portal.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.ReconciliationReportGeneratorAndNotifier;

public class ReconciliationNotifyJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		ReconciliationReportGeneratorAndNotifier reconciliationNotifier = new ReconciliationReportGeneratorAndNotifier();
		reconciliationNotifier.generateReportAndNotify();
	}
}
