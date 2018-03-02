package com.dikshatech.portal.timer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.FBPModel;

public class FBPNotification implements Job {

	private final Logger	logger	= LoggerUtil.getLogger(FBPNotification.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("running FbpTrigger");
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		MailGenerator generator = new MailGenerator();
		PortalMail mail = new PortalMail();
		int userId = 0;
		try{
			String year = FBPModel.getFinancialYear();
			String[] email_ids = profileInfoDao.findOfficalMailIdsWhere(" JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE OFFICAL_EMAIL_ID IS NOT NULL AND U.EMP_ID != 0 AND U.STATUS NOT IN (1,2,3) AND U.ID>3 AND U.ID NOT IN (SELECT USER_ID FROM FBP_REQ WHERE MONTH_ID = '" + year + "')");
			mail.setTemplateName(MailSettings.FBP);
			mail.setMailSubject("Flexi Benefit Reminder for the current month");
			mail.setAllReceipientBCCMailId(email_ids);
			generator.invoker(mail);
		} catch (ProfileInfoDaoException e){
			logger.error(" There was an ProfileInfoDaoException found for the user for sending notifications for user " + userId + e.getMessage());
		} catch (FileNotFoundException e){
			logger.error(" There was an FileNotFoundException thrown ehile getting the FBP.hmtl for sending the notofication mails " + e.getMessage());
		} catch (AddressException e){
			logger.error(" There was an AddressException found for the user for sending notifications for user " + userId + e.getMessage());
		} catch (UnsupportedEncodingException e){
			logger.error(" There was an UnsupportedEncodingException found for the user for sending notifications for user " + userId + e.getMessage());
		} catch (MessagingException e){
			logger.error(" There was an MessagingException found for the user for sending notifications for user " + userId + e.getMessage());
		}
	}
}
