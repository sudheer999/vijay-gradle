package com.dikshatech.common.utils;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.dikshatech.portal.dao.CandidateReqDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.CandidateReq;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.CandidateReqDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.DtoToBeanConverter;

public class Notification {

	public int		productsId;
	public int		hrdId;
	public int		financeId;
	public int		operationsId;
	public int		businessId;
	public int		consultingId;
	public int		supportId;
	public int		itAdminId;
	public int		issueID;
	public int		financeHeadLevelId;
	public int		tatLeadLevelId;
	public int		rmgManagerLevelId;
	public int		admin;
	public String	visaPassportNotifiers;

	public Notification(String regAbbrivation) {
		Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
		try{
			productsId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".products"));
		} catch (Exception e){}
		try{
			hrdId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".hrd"));
		} catch (Exception e){}
		try{
			financeId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".finance"));
		} catch (Exception e){}
		try{
			operationsId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".operations"));
		} catch (Exception e){}
		try{
			businessId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".bussiness"));
		} catch (Exception e){}
		try{
			consultingId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".consulting"));
		} catch (Exception e){}
		try{
			supportId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".support"));
		} catch (Exception e){}
		try{
			itAdminId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".itadmin"));
		} catch (Exception e){}
		try{
			financeHeadLevelId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".financeHeadLevelId"));
		} catch (Exception e){}
		try{
			tatLeadLevelId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".tatLeadLevelId"));
		} catch (Exception e){}
		try{
			rmgManagerLevelId = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".rmgManagerLevelId"));
		} catch (Exception e){}
		try{
			admin = Integer.parseInt(pro.getProperty("dept." + regAbbrivation + ".admin"));
		} catch (Exception e){}
		try{
			visaPassportNotifiers = pro.getProperty("dept." + regAbbrivation + ".visaPassportNotifiers");
		} catch (Exception e){}
	}

	public Notification(String regAbbrivation, String modules) {
		this(regAbbrivation);
		Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
		issueID = Integer.parseInt(pro.getProperty("module." + regAbbrivation + ".issue"));
	}

	/**
	 * This method is used to notify the departments on joining of a candidate
	 * 
	 * @param candidate
	 */
	public Set<String> notifyDept(Candidate candidate, int raisedBy, int joiningUser) throws Exception {
		UsersDao uDao = UsersDaoFactory.create();
		InboxDao iDao = InboxDaoFactory.create();
		CandidateReqDao cReqDao = CandidateReqDaoFactory.create();
		Set<String> emailIds = new HashSet<String>();
		CandidateReq candidateReq = cReqDao.findWhereCandidateIdEquals(candidate.getId())[0];
		Integer[] deptIds = { financeId, operationsId, itAdminId, admin };
		String whereCond = "PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE LEVEL_ID IN (SELECT ID FROM LEVELS WHERE DIVISION_ID = ?))";
		for (int deptId : deptIds){
			Object[] sqlParams = { new Integer(deptId) };
			Users[] users = uDao.findByDynamicWhere(whereCond, sqlParams);
			for (Users user : users){
				//				Inbox inbox = new Inbox();
				//				inbox.setEsrMapId(candidateReq.getEsrqmId());
				//				
				//				if(deptId == operationsId)
				//				{
				//					inbox.setSubject("Open Bank account, Access card, Medical Insurance card");
				//				}else if(deptId == itAdminId)
				//				{
				//					inbox.setSubject("Allot seating area, Make system arrangements");
				//				}else if(deptId == financeId)
				//				{
				//					inbox.setSubject("Open PF account");
				//				}	
				//				
				//				if(user.getId() != joiningUser)
				//				{
				//					inbox = sendToDockingStation(inbox, candidate, user.getId(), raisedBy);
				//					iDao.insert(inbox);
				//				}	
				ProfileInfo userProfileToEmail = ProfileInfoDaoFactory.create().findByPrimaryKey(user.getProfileId());
				emailIds.add(userProfileToEmail.getOfficalEmailId());
			}
		}
		//		/**
		//		 * notify reporting-manager
		//		 */
		//		ProfileInfoDao pInfoDao = ProfileInfoDaoFactory.create();
		//		ProfileInfo profileInfo = pInfoDao.findByPrimaryKey(candidate.getProfileId());
		//		
		//		Inbox inbox = new Inbox();
		//		inbox.setSubject("Candidate joining process");
		//		inbox.setEsrMapId(candidateReq.getEsrqmId());
		//		inbox = sendToDockingStation(inbox, candidate, profileInfo.getReportingMgr(), raisedBy);
		//		iDao.insert(inbox);
		//		
		//		candidateReq.setStatus("Joining Process");
		//		candidateReq.setCreatedatetime(null);
		//		candidateReq.setId(0);
		//		
		//		cReqDao.insert(candidateReq);
		return emailIds;
	}

	private Inbox sendToDockingStation(Inbox inbox, Candidate candidate, int receiverId, int raisedBy) throws Exception {
		ProfileInfoDao pInfoDao = ProfileInfoDaoFactory.create();
		PersonalInfoDao perInfoDao = PersonalInfoDaoFactory.create();
		ProfileInfo profileInfo = pInfoDao.findByPrimaryKey(candidate.getProfileId());
		PersonalInfo personalInfo = perInfoDao.findByPrimaryKey(candidate.getPersonalId());
		UsersDao usersDao = UsersDaoFactory.create();
		Users receiver = usersDao.findByPrimaryKey(receiverId);
		ProfileInfo receiverprofileInfo = pInfoDao.findByPrimaryKey(receiver.getProfileId());
		inbox.setAssignedTo(receiverId);
		inbox.setStatus("Joining Process");
		inbox.setCategory("CANDIDATE");
		inbox.setReceiverId(receiverId);
		inbox.setRaisedBy(raisedBy);
		PortalMail portalMail = new PortalMail();
		MailGenerator mGenerator = new MailGenerator();
		portalMail = DtoToBeanConverter.DtoToBeanConverter(portalMail, candidate, profileInfo, personalInfo, null, false);
		portalMail.setTemplateName(MailSettings.CANDIDATE_TAT_ACCEPTANCE);
		portalMail.setEmpFname(receiverprofileInfo.getFirstName());
		inbox.setMessageBody(mGenerator.replaceFields(mGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail));
		return inbox;
	}

	public int getDepartmentIdBasedOnRegion(int divId, String regionAbbr) {
		int userId = 0;
		if (divId == operationsId){
			userId = getHandlerUserIdOfParticularDept(regionAbbr, "OPERATION");
		} else if (divId == itAdminId){
			userId = getHandlerUserIdOfParticularDept(regionAbbr, "ITADMIN");
		} else if (divId == financeId){
			userId = getHandlerUserIdOfParticularDept(regionAbbr, "FINANCE");
		} else if (divId == hrdId){
			userId = getHandlerUserIdOfParticularDept(regionAbbr, "HRD");
		}
		return userId;
	}

	public static int getHandlerUserIdOfParticularDept(String regionAbbr, String pattern) {
		int userID;
		Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
		userID = Integer.parseInt(pro.getProperty("HANDLER." + regionAbbr + "." + pattern));
		return userID;
	}
}
