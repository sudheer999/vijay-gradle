package com.dikshatech.common.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class BirthdayNotifier {

	private static Logger		logger		= Logger.getLogger(BirthdayNotifier.class);
	private static final String	selectUsers	= "SELECT * FROM PROFILE_INFO p LEFT JOIN USERS u ON p.ID=u.PROFILE_ID WHERE DAY(p.DOB)=DAY(CURRENT_DATE()) AND MONTH(p.DOB)=MONTH(CURRENT_DATE()) AND u.STATUS=0 ";

	public BirthdayNotifier() {}

	public void wishBirthDayEmployee() {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao userDao = UsersDaoFactory.create();
		try{
			ProfileInfo birthdayUsers[] = profileInfoDao.findByDynamicSelect(selectUsers, null);
			int i = 0;
			for (ProfileInfo singleUser : birthdayUsers){
				ProfileInfo profileUserInfo = profileInfoDao.findByPrimaryKey(singleUser.getId());
				Set<String> ccMailidsOfHrSpocAndRmOfCandidate = new HashSet<String>();//hrSpoc and RM mail id added to cCMail by Sasmita Sabar
				Users hrSpocUsersInfo = userDao.findByPrimaryKey(profileUserInfo.getHrSpoc());
				ProfileInfo profileUserInfoForHrSpoc = profileInfoDao.findByPrimaryKey(hrSpocUsersInfo.getProfileId());
				ccMailidsOfHrSpocAndRmOfCandidate.add(profileUserInfoForHrSpoc.getOfficalEmailId());
				Users rMUsersInfo = userDao.findByPrimaryKey(profileUserInfo.getReportingMgr());
				ProfileInfo profileUserInfoForRM = profileInfoDao.findByPrimaryKey(rMUsersInfo.getProfileId());
				ccMailidsOfHrSpocAndRmOfCandidate.add(profileUserInfoForRM.getOfficalEmailId());
				String[] ccMailIds = (String[]) Arrays.copyOf(ccMailidsOfHrSpocAndRmOfCandidate.toArray(), ccMailidsOfHrSpocAndRmOfCandidate.toArray().length, String[].class);
				Date date = new Date();
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
				String year = simpleDateformat.format(date);
				String mailSubject = "Diksha: RMG:" + year + " Wishes you a very happy birthday ";
				String templateName = MailSettings.BIRTHDAY_WISHES_AUTO;
				String candidateName = profileUserInfo.getFirstName();
				//Users userDto = userDao.findByPrimaryKey(profileUserInfo.getHrSpoc());				
				//ProfileInfo hrProfileUserInfo = profileInfoDao.findByPrimaryKey(userDto.getId());	
				//String hrName=hrProfileUserInfo.getFirstName();				
				int num = generateRandomNumber();
				if (num == 0){
					num = 10;
				}
				String message = "Happy Birthday and many happy returns of the day.";
				sendEmailToRecipient(mailSubject, profileUserInfo.getOfficalEmailId(), candidateName, templateName, message, num, ccMailIds);
				i++;
			}
		} catch (ProfileInfoDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsersDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendEmailToRecipient(String mailSubject, String mailId, String candidateName, String templateName, String message, int random, String[] ccMailIds) {
		try{
			PortalMail portalMail = new PortalMail();
			portalMail.setMailSubject(mailSubject);
			portalMail.setTemplateName(templateName);
			portalMail.setCandidateName(candidateName);
			portalMail.setFromMailId("rmg@dikshatech.com");
			portalMail.setComments(message);
			portalMail.setRecipientMailId(mailId);
			portalMail.setRandom(random);
			portalMail.setAllReceipientcCMailId(ccMailIds);
			if (mailId != null && mailId.contains("@")){
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}
		} catch (AddressException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public int generateRandomNumber() {
		Random rand = new Random();
		int random = 0;
		random = rand.nextInt(10);
		return random;
	}

	public void sendAnniversariMails() {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao userDao = UsersDaoFactory.create();
		try{
			ProfileInfo anniversariUsers[] = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO p LEFT JOIN USERS u ON p.ID=u.PROFILE_ID WHERE DAY(p.DATE_OF_JOINING)=DAY(CURRENT_DATE()) AND MONTH(p.DATE_OF_JOINING)=MONTH(CURRENT_DATE()) AND u.STATUS=0", null);
			int year = new Date().getYear();
			MailGenerator mgenerator = new MailGenerator();
			if(anniversariUsers!=null && anniversariUsers.length>0 ){
			for (ProfileInfo singleUser : anniversariUsers){
				try{
					PortalMail pmail = new PortalMail();
					switch (year - singleUser.getDateOfJoining().getYear()) {
						case 1:
							pmail.setTemplateName(MailSettings.ANNIVERSARY_1);
							break;
						case 3:
							pmail.setTemplateName(MailSettings.ANNIVERSARY_3);
							break;
						case 5:
							pmail.setTemplateName(MailSettings.ANNIVERSARY_5);
							break;
						case 7:
							pmail.setTemplateName(MailSettings.ANNIVERSARY_7);
							break;
						case 10:
							pmail.setTemplateName(MailSettings.ANNIVERSARY_10);
							break;
						default:
							if (year - singleUser.getDateOfJoining().getYear() > 10){
								pmail.setTemplateName(MailSettings.ANNIVERSARY_11);
								pmail.setDate(year - singleUser.getDateOfJoining().getYear() + "");
							}
							break;
					}
					if (pmail.getTemplateName() == null) continue;
					pmail.setRecipientMailId(singleUser.getOfficalEmailId());
					pmail.setEmployeeName(singleUser.getFirstName());
					pmail.setSenderName("RMG");
					if (pmail.getDate() == null) pmail.setDate(new SimpleDateFormat("MMM dd, yyyy").format(new Date()));
					pmail.setMailSubject("Diksha : RMG : " + year + " : Congratulations");
					String ids = "" + singleUser.getReportingMgr();
					if (singleUser.getHrSpoc() > 2) ids += ", " + singleUser.getHrSpoc();
					pmail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere(" WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ids + " ))"));
					mgenerator.invoker(pmail);
			
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		} }catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new BirthdayNotifier().sendAnniversariMails();
	}
}
