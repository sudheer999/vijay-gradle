package com.dikshatech.portal.timer;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.dikshatech.common.utils.ExportType;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.jasper.JGenerator;
import com.dikshatech.portal.dao.LeaveBalanceDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Holidays;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.LeaveBalancePk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.HolidaysDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.HolidaysDaoFactory;
import com.dikshatech.portal.factory.LeaveBalanceDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.ProfileInfoModel;
import com.dikshatech.portal.models.UserModel;

public class EmployeeDateOfConfirmation implements Job {

	boolean					flag	= false;
	private static Logger	logger	= LoggerUtil.getLogger(EmployeeDateOfConfirmation.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			sendprobationRemainders();
			sendDateOfConfirmationMail(null);
		} catch (Exception e){
			logger.debug("error in Employee date of confirmation scheduler.....");
			e.printStackTrace();
		}
	}

	private void sendprobationRemainders() {
		try{
			Date date = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7) return;
			LevelsDao levelsDao = LevelsDaoFactory.create();
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail pMail = new PortalMail();
			UsersDao usersDao = UsersDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			Regions[] regions = RegionsDaoFactory.create().findAll();
			for (Regions region : regions){
				try{
					int daysBefore = getNextSecondWorkingDayDiff(region.getId(), date);
					if (daysBefore == 0) continue;// if its holiday
					ProfileInfo profileInfos[] = profileInfoDao.findByDynamicSelect("SELECT PF.* FROM PROFILE_INFO PF,USERS U WHERE U.STATUS NOT IN (1, 2, 3) AND U.PROFILE_ID=PF.ID  AND (SELECT REGION_ID FROM DIVISON D,LEVELS L WHERE D.ID=L.DIVISION_ID AND L.ID=PF.LEVEL_ID)=? AND DATE_OF_CONFIRMATION IS NULL AND (( DOC IS NOT NULL AND DATEDIFF(?,DOC)>=?) OR ( DOC IS NULL AND DATEDIFF(?,DATE_OF_JOINING)>=DATEDIFF(ADDDATE(ADDDATE(DATE_OF_JOINING,INTERVAL 6 MONTH),?),DATE_OF_JOINING)))",
							new Object[] { region.getId(), date, daysBefore, date, daysBefore });
					if (profileInfos == null || profileInfos.length == 0) continue;
					Notification notifi1 = new Notification(region.getRefAbbreviation());
					int rmgdept = notifi1.hrdId;
					List<String> rmgEmails = UserModel.getUsersByDivision(rmgdept, null);
					for (ProfileInfo profileInfo : profileInfos){
						try{
							Users user = usersDao.findWhereProfileIdEquals(profileInfo.getId())[0];
							Levels levels = levelsDao.findByPrimaryKey(profileInfo.getLevelId());
							Divison div = DivisonDaoFactory.create().findByPrimaryKey(levels.getDivisionId());
							String toCompareDoc;
							if (profileInfo.getDoc() != null && profileInfo.getMonths() != null){
								toCompareDoc = PortalUtility.getdd_MM_yyyy(profileInfo.getDoc());
							} else{
								toCompareDoc = PortalUtility.getdd_MM_yyyy(PortalUtility.addMonths(profileInfo.getDateOfJoining(), 6));
							}
							profileInfo.setToMonth(toCompareDoc);
							//find rmg users emails
							pMail.setAllReceipientMailId(rmgEmails.toArray(new String[rmgEmails.size()]));
							String division = "N.A";
							division = div.getName();
							profileInfo.setExtension("1");
							profileInfoDao.update(new ProfileInfoPk(profileInfo.getId()), profileInfo);
							pMail.setMailSubject(" Employment Confirmation Reminder - " + profileInfo.getFirstName());
							pMail.setCandidateName(profileInfo.getFirstName());
							pMail.setDate(PortalUtility.getDDMMYYYY(toCompareDoc)); //date
							pMail.setEmpId(String.valueOf(user.getEmpId()));//employee id
							pMail.setOffreredDivision(division);//division
							pMail.setOfferedDesignation(levels.getDesignation()); //designation
							pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
							if ((PortalUtility.getdd_MM_yyyy(date).compareTo(toCompareDoc)) > 0){
								pMail.setTemplateName(MailSettings.EMPLOYEE_PROBATION_DETAILS_POSTDATE);
							} else{
								pMail.setTemplateName(MailSettings.EMPLOYEE_PROBATION_DETAILS);
							}
							mailGenerator.invoker(pMail);
						} catch (Exception e){
							logger.error("unable to send probation Remainder mail : " + profileInfo, e);
						}
					}
					new DailyNotifications().remindProbitionPeriod(profileInfos, region);
				} catch (Exception e){
					logger.error("unable to send probation Remainder mail for " + region.getRegName(), e);
				}
			}
		} catch (Exception e){
			logger.error("unable to send probation Remainder mail", e);
		}
	}

	public int getNextSecondWorkingDayDiff(int regionId, Date date) throws HolidaysDaoException {
		Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS WHERE CAL_ID IN (SELECT ID FROM CALENDAR WHERE REGION = ? AND YEAR=? ) AND DATE_PICKER BETWEEN ? AND ADDDATE(?,15) ORDER BY DATE_PICKER ASC", new Object[] { regionId, PortalUtility.getyear(date), date, date });
		if (holiday != null && holiday.length > 0 && holiday[0].getDatePicker().equals(date)) return 0;
		int daysBefore = -2, nextdays = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while (nextdays < 2){
			cal.add(Calendar.DAY_OF_MONTH, 1);
			if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
				daysBefore -= 1;
				continue;
			}
			String dat = PortalUtility.getdd_MM_yyyy(cal.getTime());
			boolean tocontinue = false;
			if (holiday != null && holiday.length > 0) for (Holidays tmpHoliday : holiday){
				if (dat.equals(PortalUtility.getdd_MM_yyyy(tmpHoliday.getDatePicker()))){
					daysBefore -= 1;
					tocontinue = true;
					break;
				}
			}
			if (tocontinue) continue;
			nextdays++;
		}
		return daysBefore;
	}

	/**
	 * for users whose date of confirmation is todays date
	 */
	public void sendDateOfConfirmationMail(Users users[]) {
		try{
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail pMail = new PortalMail();
			UsersDao usersDao = UsersDaoFactory.create();
			if (users == null) users = usersDao.findByDynamicWhere(" STATUS =0 AND PROFILE_ID IN " + "(SELECT ID FROM PROFILE_INFO WHERE DATE_OF_CONFIRMATION  IS NOT NULL AND DATE_OF_CONFIRMATION=current_date() AND EMPLOYEE_TYPE = 'CONFIRMED')", null);
			for (Users user : users){
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());
				Levels levels = LevelsDaoFactory.create().findByPrimaryKey(profileInfo.getLevelId());
				String todaysDate = PortalUtility.formateDateTimeToDateDB(new Date());
				String dateofConfirm = PortalUtility.formateDateTimeToDateDB(profileInfo.getDateOfConfirmation());
				if (dateofConfirm.equals(todaysDate)){
					/**
					 * send email employee with attachment
					 */
					addLeaves(user.getId(), profileInfo.getDateOfConfirmation());
					if (profileInfo.getEmployeeType().equalsIgnoreCase("CONFIRMED")){
						Attachements[] attachements = new Attachements[1];
						Attachements attachement = new Attachements();
						Users user1 = usersDao.findByPrimaryKey(user.getId());
						String tempFileName1 = ProfileInfoModel.generateFName(user1.getId(), PortalData.CONFIRMATION_LETTER, ExportType.pdf);
						if (ProfileInfoModel.generateConfirmation(user, profileInfo, levels, tempFileName1)){
							//save some field in user/profile table
							String filePath = new JGenerator().getOutputFile(JGenerator.EMPLOYEE, tempFileName1);
							attachement.setFilePath(filePath);
							attachement.setFileName("ConfirmationLetter.pdf");
							attachements[0] = attachement;
							pMail.setFileSources(attachements);
						}
						List ccIds = ModelUtiility.getInstance().getRMGManagerUserIds(new UsersPk(user.getId()));
						ccIds.add(profileInfo.getReportingMgr());
						ccIds.add(profileInfo.getHrSpoc());
						pMail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(ccIds) + ") AND STATUS NOT IN (1,2,3))"));
						pMail.setMailSubject("Employment Confirmation");
						pMail.setCandidateName(profileInfo.getFirstName());
						pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
						pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo.getDateOfJoining()));
						pMail.setTemplateName(MailSettings.EMPLOYEE_PROBATION_CONFIRM);
						mailGenerator.invoker(pMail);
					}
				}
			}
		} catch (Exception e){
			logger.error("unable to send date of confirmation mail", e);
		}
	}

	public void addLeaves(int id, Date dateOfConfirmation) {
		try{
			LeaveBalanceDao lBalanceDao = LeaveBalanceDaoFactory.create();
			LeaveBalance lBalance = lBalanceDao.findWhereUserIdEquals(id);
			if (lBalance == null) return;
			double accLeave = 0;
			Calendar conf = Calendar.getInstance();
			Calendar current = Calendar.getInstance();
			conf.setTime(dateOfConfirmation);
			Date cuurdate = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
			current.setTime(cuurdate);
			/*if (dateOfConfirmation.equals(cuurdate) && conf.get(Calendar.DAY_OF_MONTH) == 1) accLeave = 1.6;
			else */
			if (conf.get(Calendar.DAY_OF_MONTH) <= 15) accLeave = 0.6;
			int monthdiff = PortalUtility.getMothsBetween(dateOfConfirmation, cuurdate);
			if (monthdiff > 0) accLeave += monthdiff * 0.6;
			accLeave += (11 - conf.get(Calendar.MONTH)) * 1.6;
			float value = Float.parseFloat(new DecimalFormat("0.0").format(accLeave));
			// Make sure value should be either *.0 or *.5 format
			value = (((int) (value * 10) % 10) > 7 ? ((int) value + 1) : ((int) (value * 10) % 10) > 2 ? ((int) value + 0.5f) : ((int) value)) + lBalance.getLeaveAccumalated();
			lBalance.setLeaveAccumalated(value);
			lBalance.setMarriage(3);
			lBalance.setBereavement(3);
			ProfileInfo info[] = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT P.* FROM PROFILE_INFO P JOIN USERS U ON U.PROFILE_ID = P.ID AND U.ID=?", new Object[] { id });
			if (info != null && info.length == 1 && info[0] != null && (info[0].getGender() + "").equalsIgnoreCase("MALE")) lBalance.setPaternity(3);
			lBalance.setBalance(lBalance.getLeaveAccumalated() - lBalance.getLeavesTaken());
			lBalanceDao.update(new LeaveBalancePk(lBalance.getId()), lBalance);
		} catch (Exception e){}
	}
}