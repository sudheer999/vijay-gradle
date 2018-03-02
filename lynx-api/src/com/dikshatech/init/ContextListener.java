package com.dikshatech.init;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.dikshatech.common.db.MyDBConnect;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.portal.mail.PendingMails;

public class ContextListener implements ServletContextListener {

	private static Logger	logger	= LoggerUtil.getLogger(ContextListener.class);
	public static String	realPath;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		MyDBConnect.releaseConnection();
		//LeaveAccumalater.stopJobScheduler();
		PendingMails.getInstance().stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		realPath = arg0.getServletContext().getRealPath(".");
		logger.info("RealPath:  " + realPath);
		MyDBConnect.getConnect();
		logger.info("creating work directories.");
		createWorkDirs();
	//	LeaveAccumalater.startJobScheduler();
		logger.info("creating new thread to send pending mails");
		new Thread() {

			public void run() {
				PendingMails.getInstance();
			};
		}.start();
	}

	public void createWorkDirs() {
		String homeDir = PropertyLoader.getEnvVariable();
		String tempDir = homeDir + File.separator + "temp";
		String cDataDir = homeDir + File.separator + "Data" + File.separator + "candidate";
		String calDataDir = homeDir + File.separator + "Data" + File.separator + "calendar";
		String eDataDir = homeDir + File.separator + "Data" + File.separator + "employee";
		String appraisals = homeDir + File.separator + "Data" + File.separator + "appraisals";
		String leave = homeDir + File.separator + "Data" + File.separator + "leave";
		String payslip = homeDir + File.separator + "Data" + File.separator + "payslip";
		String news = homeDir + File.separator + "Data" + File.separator + "news";
		for (String dir : new String[] { homeDir, tempDir, cDataDir, appraisals, calDataDir, eDataDir, leave, payslip, news }){
			File file = new File(dir);
			if (!file.isDirectory() && file.exists()) file.delete();
			if (!file.exists()) file.mkdirs();
		}
		// gurunath commented on 18/09/2012
		/*File homePath = new File(homeDir);
		File appPath = new File(appraisal);
		File cDataPath = new File(cDataDir);
		File calDataPath = new File(calDataDir);
		File eDataPath = new File(eDataDir);
		File leavePath = new File(leave);
		File payslipPath = new File(payslip);
		File newsPath = new File(news);
		if (!homePath.isDirectory()) new File(tempDir).mkdirs();
		if (!cDataPath.isDirectory()) new File(cDataDir).mkdirs();
		if (!calDataPath.isDirectory()) new File(calDataDir).mkdirs();
		if (!eDataPath.isDirectory()) new File(eDataDir).mkdirs();
		if (!appPath.isDirectory()) new File(appraisal).mkdirs();
		if (!leavePath.isDirectory()) new File(leave).mkdirs();
		if (!payslipPath.isDirectory()) new File(payslip).mkdirs();
		if (!newsPath.isDirectory()) new File(news).mkdirs();*/
	}
}
