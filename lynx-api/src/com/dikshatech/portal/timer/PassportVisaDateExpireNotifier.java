package com.dikshatech.portal.timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.PassportInfoDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dao.VisaInfoDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.PassportInfo;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.VisaInfo;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PassportInfoDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.factory.VisaInfoDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class PassportVisaDateExpireNotifier {

	private static Logger							logger	= Logger.getLogger(PassportVisaDateExpireNotifier.class);
	private static PassportVisaDateExpireNotifier	insatnce;

	private PassportVisaDateExpireNotifier() {}

	public static PassportVisaDateExpireNotifier getInstatnce() {
		if (insatnce == null) insatnce = new PassportVisaDateExpireNotifier();
		return insatnce;
	}

	public void passportExpireNotification() {
		try{
			PassportInfoDao passInfoDao = PassportInfoDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			UsersDao userDao = UsersDaoFactory.create();
			Users[] users = userDao.findByDynamicSelect("SELECT U.* FROM USERS U JOIN PASSPORT_INFO P ON U.PASSPORT_ID=P.ID WHERE P.DATE_OF_EXPIRE = DATE_ADD(CURDATE(),INTERVAL 9 MONTH) AND U.STATUS NOT IN (1, 2, 3)", null);
			if (users != null && users.length > 0){
				PortalMail pMail = null;
				MailGenerator mailGenerator = new MailGenerator();
				for (Users user : users){
					try{
						pMail = new PortalMail();
						pMail.setTemplateName(MailSettings.PASSPORT_EXPIRE_USER_NOTIFY);
						pMail.setMailSubject("Diksha: RMG: Your Passport is about expire");
						ProfileInfo userProfile = profileInfoDao.findByPrimaryKey(user.getProfileId());
						PassportInfo passInfo = passInfoDao.findByPrimaryKey(user.getPassportId());
						setEmployeeDetails(pMail, user, userProfile);
						pMail.setOnDate(PortalUtility.getdd_MM_yyyy(passInfo.getDateOfExpire()));
						pMail.setDate(PortalUtility.getdd_MM_yyyy(passInfo.getDateOfIssue()));
						pMail.setRegion(passInfo.getPlaceOfIssue());
						pMail.setReqId(passInfo.getPassportNo());
						pMail.setRecipientMailId(userProfile.getOfficalEmailId());
						String ids = "" + userProfile.getReportingMgr();
						if (userProfile.getHrSpoc() > 2) ids += ", " + userProfile.getHrSpoc();
						String[] hrRmEmails = profileInfoDao.findOfficalMailIdsWhere(" WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ids + " ))");
						Set<String> cc = new HashSet<String>(getHandlersEmails(user.getId()));
						for (String mailId : hrRmEmails)
							cc.add(mailId);
						pMail.setAllReceipientcCMailId(cc.toArray(new String[cc.size()]));
						mailGenerator.invoker(pMail);
					} catch (Exception e){
						logger.error("Unable to send passport expire notification to " + user.getId(), e);
					}
				}
			}
		} catch (Exception e){
			logger.error("Unable to send passport expire notification", e);
		}
	}

	public void visaExpireNotification() {
		try{
			PassportInfoDao passInfoDao = PassportInfoDaoFactory.create();
			VisaInfoDao visaInfoDao = VisaInfoDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			UsersDao userDao = UsersDaoFactory.create();
			Users[] users = userDao.findByDynamicSelect("SELECT U.* FROM USERS U JOIN VISA_INFO V ON V.PASSPORT_ID=U.PASSPORT_ID WHERE V.VISA_TO = DATE_ADD(CURDATE(),INTERVAL 2 MONTH) AND U.STATUS NOT IN (1, 2, 3)", null);
			if (users != null && users.length > 0){
				PortalMail pMail = null;
				MailGenerator mailGenerator = new MailGenerator();
				List<Integer> userList = new ArrayList<Integer>();
				for (Users user : users){
					//if more than one visa is expiring on same day then user ll repeat.
					if (userList.contains(user.getId())) continue;
					userList.add(user.getId());
					try{
						pMail = new PortalMail();
						pMail.setTemplateName(MailSettings.VISA_EXPIRE_USER_NOTIFY);
						pMail.setMailSubject("Diksha: RMG: Your Visa is about expire");
						ProfileInfo userProfile = profileInfoDao.findByPrimaryKey(user.getProfileId());
						PassportInfo passInfo = passInfoDao.findByPrimaryKey(user.getPassportId());
						setEmployeeDetails(pMail, user, userProfile);
						pMail.setOnDate(PortalUtility.getdd_MM_yyyy(passInfo.getDateOfExpire()));
						pMail.setDate(PortalUtility.getdd_MM_yyyy(passInfo.getDateOfIssue()));
						pMail.setRegion(passInfo.getPlaceOfIssue());
						pMail.setReqId(passInfo.getPassportNo());
						pMail.setRecipientMailId(userProfile.getOfficalEmailId());
						String ids = "" + userProfile.getReportingMgr();
						if (userProfile.getHrSpoc() > 2) ids += ", " + userProfile.getHrSpoc();
						String[] hrRmEmails = profileInfoDao.findOfficalMailIdsWhere(" WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ids + " ))");
						Set<String> cc = new HashSet<String>(getHandlersEmails(user.getId()));
						for (String mailId : hrRmEmails)
							cc.add(mailId);
						pMail.setAllReceipientcCMailId(cc.toArray(new String[cc.size()]));
						VisaInfo visaInfo[] = visaInfoDao.findByDynamicWhere(" VISA_TO = DATE_ADD(CURDATE(),INTERVAL 7 DAY) AND PASSPORT_ID = ?", new Object[] { user.getPassportId() });
						for (VisaInfo visa : visaInfo){
							pMail.setLeaveStartDate(PortalUtility.getdd_MM_yyyy(visa.getVisaFrom()));
							pMail.setLeaveEndDate(PortalUtility.getdd_MM_yyyy(visa.getVisaTo()));
							pMail.setType(visa.getVisaType());
							pMail.setRegionName(visa.getCountry());
							mailGenerator.invoker(pMail);
						}
					} catch (Exception e){
						logger.error("Unable to send visa expire notification to " + user.getId(), e);
					}
				}
			}
		} catch (Exception e){
			logger.error("Unable to send visa expire notifications", e);
		}
	}

	private static List<String> getHandlersEmails(Integer userId) {
		try{
			Regions rgnDto = RegionsDaoFactory.create().findByDynamicSelect("SELECT * FROM REGIONS R JOIN DIVISON D ON R.ID=D.REGION_ID JOIN LEVELS L ON L.DIVISION_ID=D.ID JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { userId })[0];
			Notification notification = new Notification(rgnDto.getRefAbbreviation());
			String[] ids = ProfileInfoDaoFactory.create().findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS U WHERE U.EMP_ID IN (" + notification.visaPassportNotifiers + ") AND U.STATUS NOT IN (1,2,3) )");
			if (ids != null) return Arrays.asList(ids);
		} catch (Exception e){}
		return new ArrayList<String>();
	}

	private void setEmployeeDetails(PortalMail portalMail, Users userDto, ProfileInfo profileInfoDto) throws Exception {
		LevelsDao levelsdao = LevelsDaoFactory.create();
		DivisonDao divisionDao = DivisonDaoFactory.create();
		Levels level = levelsdao.findByPrimaryKey(profileInfoDto.getLevelId());
		Divison division = divisionDao.findByPrimaryKey(level.getDivisionId());
		portalMail.setEmpDesignation(level.getDesignation());
		portalMail.setEmpFname(profileInfoDto.getFirstName());
		portalMail.setEmpLName(profileInfoDto.getLastName());
		portalMail.setEmpId(userDto.getEmpId() + "");
		portalMail.setOfferedDepartment("N.A");
		portalMail.setEmpDivision("N.A");
		if (division != null){
			if (division.getParentId() != 0){
				portalMail.setEmpDivision(division.getName());
				while (division.getParentId() != 0){
					division = divisionDao.findByPrimaryKey(division.getParentId());
				}
				if (division != null) portalMail.setOfferedDepartment(division.getName());
			} else portalMail.setOfferedDepartment(division.getName());
		}
	}
}
