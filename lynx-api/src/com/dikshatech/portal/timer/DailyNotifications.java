package com.dikshatech.portal.timer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.ModelUtiility.Department;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.models.AppraisalModel;
import com.dikshatech.portal.models.ExitModel;

public class DailyNotifications implements Job {

	private static Logger	logger	= LoggerUtil.getLogger(ModelUtiility.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("executing DailyNotifications");
		sendNotificationsForPaySlips();
		deleteEmailIDNotify();
	    remindIdcardsIssue();
		remindSeperatedUsertasks();
		remaindProbitionPeriod();
		remindJoiningProcess();
		offerFollowUpreminder();
		AppraisalModel.getInstance().appraisalReminders();
		//Following code in comment is moved to method to support mass update/delete in MySQL 5.0 INNODB engine 
		//JDBCUtiility.getInstance().update("UPDATE INBOX SET IS_READ=0 WHERE CATEGORY='NOTIFICATION' AND ASSIGNED_TO != 0", null);
		JDBCUtiility.getInstance().updateInbox("IS_READ=0", "CATEGORY='NOTIFICATION' AND ASSIGNED_TO != 0");

		
		ExitModel.getInstance().enableNOCToSubmit();
		PassportVisaDateExpireNotifier.getInstatnce().passportExpireNotification();
		PassportVisaDateExpireNotifier.getInstatnce().visaExpireNotification();
	}
	
	private void offerFollowUpreminder() {
		try{
			//Following code in comment is moved to method to support mass update/delete in MySQL 5.0 INNODB engine 
			//JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE CATEGORY='NOTIFICATION' AND ESR_MAP_ID IN ( SELECT DISTINCT(ESRQM_ID) FROM CANDIDATE_REQ )", null);
			JDBCUtiility.getInstance().deleteInbox("CATEGORY='NOTIFICATION' AND ESR_MAP_ID IN ( SELECT DISTINCT(ESRQM_ID) FROM CANDIDATE_REQ)");
			
			Candidate candidates[] = CandidateDaoFactory.create().findByDynamicWhere("STATUS IN (5, 6) AND TIMESTAMPDIFF(DAY,DATE_OF_OFFER,DATE_FORMAT(NOW(),'%Y-%m-%d'))=3 AND IS_ACTIVE=0", null);
			for (Candidate candidate : candidates){
				try{
					List<Object> userNameObj = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=?", new Object[] { candidate.getProfileId() });
					String userName = (String) userNameObj.get(0) + " (" + candidate.getId() + ")";
					String sentDate = PortalUtility.getdd_MM_yyyy(candidate.getDateOfOffer());
					Inbox inbox = new Inbox();
					List<Object> esrqId = JDBCUtiility.getInstance().getSingleColumn("SELECT MAX(ESRQM_ID) FROM CANDIDATE_REQ C WHERE  CANDIDATE_ID = ?", new Object[] { candidate.getId() });
					inbox.setStatus(Status.getStatus(candidate.getStatus()));
					inbox.setCategory("NOTIFICATION");
					inbox.setEsrMapId(((Integer) esrqId.get(0)).intValue());
					inbox.setSubject("Follow up on Offer Sent to " + userName + " on " + sentDate);
					inbox.setMessageBody(getHtmlContent("Dear TAT", "Follow up on Offer Sent to " + userName + " on " + sentDate));
					inbox.setAssignedTo(0);
					inbox.setReceiverId(candidate.getTatId());
					InboxDaoFactory.create().insert(inbox);
				} catch (Exception e){
					logger.error("unable to notify offer follow up reminder to tat  of " + candidate, e);
				}
			}
		} catch (Exception e){
			logger.error("unable to notify offer follow up reminders", e);
		}
	}

	private void remindJoiningProcess() {
		try{
			ProfileInfo profiles[] = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT * FROM PROFILE_INFO P WHERE (SELECT STATUS FROM CANDIDATE C WHERE C.PROFILE_ID=P.ID) = 2 AND TIMESTAMPDIFF(DAY,DATE_OF_JOINING,DATE_FORMAT(NOW(),'%Y-%m-%d'))=0 ", null);
			if (profiles != null && profiles.length > 0){
				int[] rmgDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.RMGMANAGERLEVEL);
				int[] hrDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.HRD);
				int[] opDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.OPERATIONS);
				int[] adminDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.ADMIN);
				int[] finDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.FINANCE);
				Set<Integer> allDeptIds = new HashSet<Integer>();
				for (int[] ids : new int[][] { rmgDeptIds, hrDeptIds, opDeptIds, finDeptIds, adminDeptIds })
					if (ids != null && ids.length > 0) for (int id : ids)
						allDeptIds.add(id);
				for (ProfileInfo profile : profiles){
					int userId = 1;
					Inbox inbox = new Inbox();
					EmpSerReqMapPk reqpk = ModelUtiility.getInstance().createEmpSerReq(userId, "NT", 0);
					inbox.setStatus(Status.REQUESTRAISED);
					inbox.setCategory("NOTIFICATION");
					inbox.setEsrMapId(reqpk.getId());
					inbox.setSubject("Joining Formalities for " + profile.getFirstName() + " " + profile.getLastName());
					inbox.setMessageBody(getHtmlContent("Dear All", "Joining Formalities for " + profile.getFirstName() + " " + profile.getLastName()));
					for (Object i : allDeptIds){
						try{
							inbox.setAssignedTo(0);
							inbox.setReceiverId(((Integer) i).intValue());
							InboxDaoFactory.create().insert(inbox);
						} catch (Exception e){
							logger.error("unable to notify joining reminder for " + i + " of " + profile.getFirstName(), e);
						}
					}
				}
			}
		} catch (Exception e){
			logger.error("unable to notify joining reminders", e);
		}
	}

	private void remaindProbitionPeriod() {
		try{
			Date date = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
			Regions[] regions = RegionsDaoFactory.create().findAll();
			for (Regions region : regions){
				int daysBefore = new EmployeeDateOfConfirmation().getNextSecondWorkingDayDiff(region.getId(), date);
				if (daysBefore == 0) continue;
				ProfileInfo profileInfos[] = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT PF.* FROM PROFILE_INFO PF,USERS U WHERE U.STATUS NOT IN (1, 2, 3) AND U.PROFILE_ID=PF.ID  AND (SELECT REGION_ID FROM DIVISON D,LEVELS L WHERE D.ID=L.DIVISION_ID AND L.ID=PF.LEVEL_ID)=? AND DATE_OF_CONFIRMATION IS NOT NULL AND DATEDIFF(?,DATE_OF_CONFIRMATION) BETWEEN -2 AND ?", new Object[] { region.getId(), date, daysBefore });
				remindProbitionPeriod(profileInfos, region);
			}
		} catch (Exception e){
			logger.error("unable to notify Probition Period ending reminders", e);
		}
	}

	public void remindProbitionPeriod(ProfileInfo profiles[], Regions region) {
		try{
			if (profiles != null && profiles.length > 0){
				int[] opDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(region.getRefAbbreviation(), Department.RMGMANAGERLEVEL);
				for (ProfileInfo profile : profiles){
					int userId = 1;
					Inbox inbox = new Inbox();
					EmpSerReqMapPk reqpk = ModelUtiility.getInstance().createEmpSerReq(userId, "NT", 0);
					inbox.setStatus(Status.REQUESTRAISED);
					inbox.setCategory("NOTIFICATION");
					inbox.setEsrMapId(reqpk.getId());
					String date = profile.getDateOfConfirmation() == null ? profile.getToMonth() : PortalUtility.getdd_MM_yyyy(profile.getDateOfConfirmation());
					inbox.setSubject("Completion of Probation Period for " + profile.getFirstName() + " " + profile.getLastName() + " on " + date);
					inbox.setMessageBody(getHtmlContent("Dear RMG", "Completion of Probation Period for " + profile.getFirstName() + " " + profile.getLastName() + " on " + date + "."));
					for (int i : opDeptIds){
						inbox.setAssignedTo(0);
						inbox.setReceiverId(i);
						InboxDaoFactory.create().insert(inbox);
					}
				}
			}
		} catch (Exception e){
			logger.error("unable to notify Probition Period ending reminders", e);
		}
	}

	private void remindSeperatedUsertasks() {
		try{
			ProfileInfo profiles[] = ProfileInfoDaoFactory.create().findByDynamicWhere("DATE_OF_SEPERATION = DATE_ADD(DATE_FORMAT(NOW(),'%Y-%m-%d'),INTERVAL 1 DAY)", null);
			if (profiles != null && profiles.length > 0){
				int[] opDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.RMGMANAGERLEVEL);
				for (ProfileInfo profile : profiles){
					int userId = 1;
					Inbox inbox = new Inbox();
					EmpSerReqMapPk reqpk = ModelUtiility.getInstance().createEmpSerReq(userId, "NT", 0);
					inbox.setStatus(Status.REQUESTRAISED);
					inbox.setCategory("NOTIFICATION");
					inbox.setEsrMapId(reqpk.getId());
					inbox.setSubject("Employee Seperation Formalities today for " + profile.getFirstName() + " " + profile.getLastName());
					inbox.setMessageBody(getHtmlContent("Dear RMG", "Employee " + profile.getFirstName() + " " + profile.getLastName() + " is leaving company by tomorrow. Follow Seperation Formalities today."));
					for (int i : opDeptIds){
						inbox.setAssignedTo(0);
						inbox.setReceiverId(i);
						InboxDaoFactory.create().insert(inbox);
					}
				}
			}
		} catch (Exception e){
			logger.error("unable to notify Seperate reminders", e);
		}
	}

	private void remindIdcardsIssue() {
		try{
			ProfileInfo profiles[] = ProfileInfoDaoFactory.create().findByDynamicWhere("DATE_OF_JOINING = DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-%d'),INTERVAL 7 DAY)", null);
			if (profiles != null && profiles.length > 0){
				int[] opDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.OPERATIONS);
				for (ProfileInfo profile : profiles){
					int userId = 1;
					Inbox inbox = new Inbox();
					EmpSerReqMapPk reqpk = ModelUtiility.getInstance().createEmpSerReq(userId, "NT", 0);
					inbox.setStatus(Status.REQUESTRAISED);
					inbox.setCategory("NOTIFICATION");
					inbox.setEsrMapId(reqpk.getId());
					inbox.setSubject("ID & Mediclaim Cards formalities for Employee " + profile.getFirstName() + " " + profile.getLastName());
					inbox.setMessageBody(getHtmlContent("Dear All", "Please provide ID & Mediclaim Cards formalities for Employee  " + profile.getFirstName() + " " + profile.getLastName() + "."));
					for (int i : opDeptIds){
						inbox.setAssignedTo(i);
						inbox.setReceiverId(i);
						InboxDaoFactory.create().insert(inbox);
					}
				}
			}
		} catch (Exception e){
			logger.error("unable to notify Id cards Issue reminders", e);
		}
	}

	private void deleteEmailIDNotify() {
		try{
			ProfileInfo profiles[] = ProfileInfoDaoFactory.create().findByDynamicWhere("DATE_OF_SEPERATION=DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-%d'),INTERVAL 1 DAY)", null);
			if (profiles != null && profiles.length > 0){
				int[] idDeptIds = ModelUtiility.getInstance().getDepartmentUserIds(1, Department.ITADMIN);
				for (ProfileInfo profile : profiles){
					int userId = 1;
					Inbox inbox = new Inbox();
					EmpSerReqMapPk reqpk = ModelUtiility.getInstance().createEmpSerReq(userId, "NT", 0);
					inbox.setStatus(Status.REQUESTRAISED);
					inbox.setCategory("NOTIFICATION");
					inbox.setEsrMapId(reqpk.getId());
					inbox.setSubject("Deletion of " + profile.getFirstName() + " " + profile.getLastName() + "'s Email ID");
					inbox.setMessageBody(getHtmlContent("Dear All", "Employee " + profile.getFirstName() + " " + profile.getLastName() + " has left the company. Delete his/her Email ID '" + profile.getOfficalEmailId() + "'."));
					for (int i : idDeptIds){
						inbox.setAssignedTo(i);
						inbox.setReceiverId(i);
						InboxDaoFactory.create().insert(inbox);
					}
				}
			}
		} catch (Exception e){
			logger.error("unable to notify delete EmailIDs reminder", e);
		}
	}

	private void sendNotificationsForPaySlips() {
		try{
			InboxDao inboxDao = InboxDaoFactory.create();
			if (!ModelUtiility.getInstance().isPayslipsUploaded()){
				if (JDBCUtiility.getInstance().getRowCount("FROM INBOX WHERE CATEGORY='PAYSLIPS'", null) > 0){
					//Following code in comment is moved to method to support mass update/delete in MySQL 5.0 INNODB engine
					//JDBCUtiility.getInstance().update("UPDATE INBOX SET IS_READ=0 WHERE CATEGORY='PAYSLIPS'", null);
					JDBCUtiility.getInstance().updateInbox("IS_READ=0", "CATEGORY='PAYSLIPS'");
				}
				else
				{
					int userId = 1;
					Inbox inbox = new Inbox();
					EmpSerReqMapPk reqpk = ModelUtiility.getInstance().createEmpSerReq(userId, "PS", 0);;
					inbox.setStatus(Status.REQUESTRAISED);
					inbox.setCategory("PAYSLIPS");
					inbox.setEsrMapId(reqpk.getId());
					inbox.setSubject("Upload Salary Slips ");
					inbox.setMessageBody(getHtmlContent("Dear All", "Upload Salary Slips for all employees."));
					for (int i : ModelUtiility.getInstance().getFinanceUserIds(1)){
						inbox.setAssignedTo(i);
						inbox.setReceiverId(i);
						inboxDao.insert(inbox);
					}
				}
			}
			else
			{
				//Following code in comment is moved to method to support mass update/delete in MySQL 5.0 INNODB engine
				//JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE CATEGORY='PAYSLIPS'", null);
				JDBCUtiility.getInstance().deleteInbox("CATEGORY='PAYSLIPS'");
			}
		} catch (Exception e){
			logger.error("unable to notify PaySlips reminder", e);
		}
	}

	public static String getHtmlContent(String header, String body) {
		return "<html><body ><center><table><tr><td style='background-color:#f0f0f0;padding:15px 10px; width: 500px;border-bottom: 6px solid #ff8512'><img src='http://www.dikshatech.com/images/newportal_images/diksha_logo.png' alt='Diksha' /></td></tr>" + "<tr><td><br/><p style='color:#505050; font-family:Arial; font-size:12px;'>" + header + ",<br/><br/><font style='padding-left: 20px;'>" + body
				+ "</font></p><br/><br/></td></tr><tr><td style='background-color:#f0f0f0; color:#707070; font-family:Arial; font-size:11px; line-height:125%; text-align:left; padding-left:16px;padding-bottom:10px; padding-top:10px;'><div>" + "This is an auto generated email, please do not reply to this email. <br/>To ensure delivery to inbox, add noreply@dikshatech.com to your address book.<br /><br />" + "</div></td></tr></table><br/><br/></center></body></html>";
	}
}
