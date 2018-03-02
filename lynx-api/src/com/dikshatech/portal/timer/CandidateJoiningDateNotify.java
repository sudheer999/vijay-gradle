package com.dikshatech.portal.timer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.UserModel;

public class CandidateJoiningDateNotify implements Job
{
	boolean flag=false;
	private static Logger logger = Logger.getLogger(CandidateJoiningDateNotify.class);

	
	public static void main(String args[]){
		try {
			new CandidateJoiningDateNotify().execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		/**
		 * put code to change  employee status as seperated
		 */
		try
		
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			logger.info("Executing method for sending Joining Reminder to SPOC");
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			CandidateDao candidateDao= CandidateDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			Calendar cal = Calendar.getInstance();
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail pMail = new PortalMail();
			Candidate candidate[]= candidateDao.findByDynamicWhere(" STATUS =?", new Object[]{2});
			if(candidate!=null && candidate.length>0){
			
			for(Candidate cand:candidate){
				ProfileInfo hrProfile=null;Users hrspoc = null;
				ProfileInfo profileInfo=profileInfoDao.findByPrimaryKey(cand.getProfileId());
				Date joiningDate=profileInfo.getDateOfJoining();
				String date1= sdf.format(joiningDate);
				String date2=sdf.format(new Date());
				hrspoc = usersDao.findByPrimaryKey(profileInfo.getHrSpoc());
				hrProfile = profileInfoDao.findByPrimaryKey(hrspoc.getProfileId());
			    //	    String[] allIds={"manoj.h@dikshatech.com","manoj.ajith888@gmail.com"};
	    // Checking whether today's date and joining date is equal
				if(date1.equals(date2)){
					sendMailToOperations(profileInfo);
				     pMail.setRecipientMailId(hrProfile.getOfficalEmailId());
				     pMail.setMailSubject("Candidate "+profileInfo.getFirstName() +" Joining Date Remainder");
				     pMail.setEmpFname(hrProfile.getFirstName());
					 pMail.setCandidateName(profileInfo.getFirstName());
					 pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
					 pMail.setRepoMngrAtClient(profileInfo.getReportingAddressNormal());//reporting address
					 pMail.setReportingTm(profileInfo.getReportingTime());//reporting time
					 pMail.setTemplateName(MailSettings.CANDIDATE_JOININGDATE_REMAINDER);
					 mailGenerator.invoker(pMail);
				}
				           Date dt=sdf.parse(date1);
				           cal.setTime(dt);
				           cal.add(Calendar.DATE, -1);
				           String date3=sdf.format(cal.getTime());;
		// Checking whether today's date and joining date's previous date is equal
				if(date3.equals(date2)){
					sendMailToOperations(profileInfo);
					 pMail.setRecipientMailId(hrProfile.getOfficalEmailId());
					 pMail.setMailSubject("Candidate "+profileInfo.getFirstName() +" Joining Date Remainder");
				     pMail.setEmpFname(hrProfile.getFirstName());
					 pMail.setCandidateName(profileInfo.getFirstName());
					 pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
					 pMail.setRepoMngrAtClient(profileInfo.getReportingAddressNormal());//reporting address
					 pMail.setReportingTm(profileInfo.getReportingTime());//reporting time
					 pMail.setTemplateName(MailSettings.CANDIDATE_JOININGDATE_REMAINDER);
					 mailGenerator.invoker(pMail);
				}
			
			}
			}
		}
		catch (Exception e)
		{
			logger.debug("error in Employee mark as seperate scheduler.....");
			e.printStackTrace();
		}
		
	}

public void sendMailToOperations(ProfileInfo profileInfo){
	 try {
	MailGenerator mailGenerator = new MailGenerator();
	PortalMail pMail = new PortalMail();
	 List<String> itMailIds=UserModel.getUsersByDivision1(19);
	 if(itMailIds!=null){
	 String ids = Arrays.asList(itMailIds).toString().replace('[', ' ').replace(']', ' ').trim();
	    String[] allIds=ids.split(",");
	    pMail.setAllReceipientMailId(allIds);
	    pMail.setMailSubject("Candidate "+profileInfo.getFirstName() +" Joining Date Remainder");
//	     pMail.setEmpFname(hrProfile.getFirstName());
		 pMail.setCandidateName(profileInfo.getFirstName());
		 pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
		 pMail.setRepoMngrAtClient(profileInfo.getReportingAddressNormal());//reporting address
		 pMail.setReportingTm(profileInfo.getReportingTime());//reporting time
		 pMail.setTemplateName(MailSettings.CANDIDATE_JOINING_IT_REMAINDER);
		
			mailGenerator.invoker(pMail);
	 }
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
}
}
