package com.dikshatech.portal.timer;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.UserMarkAsSeperated;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class CandidateReporting  implements Job
{
	boolean flag=false;
	private static Logger logger = Logger.getLogger(CandidateReporting.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		/**
		 * put code to change  employee status as seperated
		 */
		try
		{
			logger.info("Executing method for sending Reporting Reminder to Candidate");
			CandidateDao candidateDao= CandidateDaoFactory.create();
			Date newDate=PortalUtility.reminderDate();
			logger.info("+2 date"+PortalUtility.formateDateToDate(newDate));
			Candidate candidate[]= candidateDao.findByDynamicWhere(" STATUS =2 AND PROFILE_ID IN " +
					"(SELECT ID FROM PROFILE_INFO WHERE DATE_OF_JOINING = ? )", new Object[]{(PortalUtility.formateDateTimeToDateDB(newDate))});
			for(Candidate cand:candidate){
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				ProfileInfo profileInfo=profileInfoDao.findByPrimaryKey(cand.getProfileId());
				 ProfileInfo profileInfo1=profileInfoDao.findByPrimaryKey(cand.getTatId());
					    MailGenerator mailGenerator = new MailGenerator();
					    PersonalInfo personalInfo = new PersonalInfo();
						PortalMail pMail = new PortalMail();
						PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
						personalInfo=personalInfoDao.findByPrimaryKey(cand.getPersonalId());
						pMail.setMailSubject("Reporting Details");
					    pMail.setCandidateName(profileInfo.getFirstName());
					    pMail.setRecipientMailId(personalInfo.getPersonalEmailId());
					    pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
					    pMail.setRepoMngrAtClient(profileInfo.getReportingAddressNormal());//reporting address
					    pMail.setReportingTm(profileInfo.getReportingTime());//reporting time
					    pMail.setEmpFname(profileInfo1.getFirstName());
					    pMail.setTemplateName(MailSettings.CANDIDATE_REPORTING_DETAILS);
					    mailGenerator.invoker(pMail);
			logger.info("Reporting Reminder mail successfully send to Candidate");
			}
			
		}
		catch (Exception e)
		{
			logger.debug("error in Employee mark as seperate scheduler.....");
			e.printStackTrace();
		}
		
	}

}