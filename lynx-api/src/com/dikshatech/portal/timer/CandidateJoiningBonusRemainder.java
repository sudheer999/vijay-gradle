package com.dikshatech.portal.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.SalaryInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.SalaryInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class CandidateJoiningBonusRemainder implements Job {
	boolean flag=false;
	Logger logger = Logger.getLogger(CandidateJoiningBonusRemainder.class);
	
	
	public static void main(String args[]){
		try {
			new CandidateJoiningBonusRemainder().execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			UsersDao usersDao = UsersDaoFactory.create();
			ProfileInfoDao profileInfoDao=ProfileInfoDaoFactory.create();
			SalaryInfoDao salaryInfoDao=SalaryInfoDaoFactory.create();
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail pMail = new PortalMail();
			 ProfileInfo profile=null;
			 SalaryInfo[] info=null;
			 ProfileInfo hrProfile=null;Users hrspoc = null;
			Users[] users = usersDao.findByDynamicWhere("STATUS=? AND EMP_ID>0 AND ID>3", new Object[] { 0 });
			if (users != null && users.length > 0){
				for (Users eachUser : users){
					if(eachUser.getId()>280){
				     profile=profileInfoDao.findByPrimaryKey(eachUser.getProfileId());
				     if(profile!=null){
				    	Date joiningDate=profile.getDateOfJoining();
				    	Calendar startCalendar = new GregorianCalendar();
						startCalendar.setTime(joiningDate);
						Calendar endCalendar = new GregorianCalendar();
						endCalendar.setTime(new Date());
						int days=daysBetween(startCalendar.getTime(),endCalendar.getTime());
				    	 if(days==30){
				    		 info=salaryInfoDao.findWhereuserIdEquals(eachUser.getId());
				 	    	if(info!=null){
				 	    		for(SalaryInfo i:info){
				 	    			float joiningBonus=Float.parseFloat(i.getJoiningBonusAmount());
				 	    			 if(joiningBonus>0){
				 	    				 
				 	    	                  // Mail sending Part
				 	    			hrspoc = usersDao.findByPrimaryKey(profile.getHrSpoc());
				 	   				hrProfile = profileInfoDao.findByPrimaryKey(hrspoc.getProfileId());	 
				 	    			pMail.setRecipientMailId(hrProfile.getOfficalEmailId());
				 					pMail.setMailSubject("Employee "+profile.getFirstName() +" Joining Bonus Reminder");
				 					pMail.setEmpFname(hrProfile.getFirstName());
				 				    pMail.setCandidateName(profile.getFirstName());
				 				    pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profile.getDateOfJoining()));
				 				    pMail.setJoiningBonusAmount(joiningBonus+"");
				 				    pMail.setEmployeeName(profile.getFirstName()+" "+profile.getLastName());
				 				    if(eachUser.getEmpId()>0)
				 				    pMail.setEmpId(eachUser.getEmpId()+"");
				 				    System.out.println(profile.getFirstName());
				 				    pMail.setTemplateName(MailSettings.JOINING_BONUS_REMAINDER);
				 				    mailGenerator.invoker(pMail);}
				 	    			 
				 	    	    }
				 	    	}
				    		 
				        }
				    	 
				     }
				     
				}
				}
			}

		}catch (Exception e)
			{
				logger.debug("error in joing Bonus Reminder scheduler.....");
				e.printStackTrace();
			}
		
		
	}


	 public int daysBetween(Date d1, Date d2){
         return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
 }
	
}
