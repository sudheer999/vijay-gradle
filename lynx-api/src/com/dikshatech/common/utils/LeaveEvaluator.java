package com.dikshatech.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import com.dikshatech.portal.dao.LeaveBalanceDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.LeaveBalancePk;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.LeaveBalanceDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;

public class LeaveEvaluator {

	private static Logger	logger		= Logger.getLogger(LeaveEvaluator.class);
	Calendar				calendar	= Calendar.getInstance();

	public LeaveEvaluator() {}

	public boolean evaluate() {
		boolean flag = false;
		try{
			UsersDao usersDao = UsersDaoFactory.create();
			Users[] users = null;
			if (calendar.get(Calendar.MONTH) == Calendar.JANUARY) users = usersDao.findByDynamicSelect("SELECT * FROM USERS WHERE STATUS NOT IN (1,2,3)", null);
			// GET ONLY WHOS DATE OF CONFIRMATION IS NULL OR BEFORE ONE MONTH CONFIRMED.
			else users = usersDao.findByDynamicSelect("SELECT * FROM USERS WHERE STATUS NOT IN (1,2,3) AND PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE TIMESTAMPDIFF(DAY, DATE_OF_CONFIRMATION,CURRENT_DATE())<0 OR  DATE_OF_CONFIRMATION IS NULL)", null);
			for (Users user : users){
				LeaveBalance lBalance = accumalateLeave(user);
				if (lBalance != null) updateLeave(lBalance);
			}
			// add 90 days maternity leaves who completed 1 year 
			ProfileInfoDaoFactory.create().findByDynamicUpdate("UPDATE LEAVE_BALANCE SET MATERNITY=90 WHERE USER_ID IN (SELECT ID FROM USERS WHERE PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE TIMESTAMPDIFF(month, DATE_OF_CONFIRMATION,CURRENT_DATE())=12 AND GENDER='FEMALE')) AND MATERNITY=0", null);
		} catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method is invoked if its first day of month to accumulate leaves
	 * 
	 * @author gurunath.rokkam
	 * @param user
	 * @throws Exception
	 */
	private LeaveBalance accumalateLeave(Users user) throws Exception {
		ProfileInfoDao proInfoDao = ProfileInfoDaoFactory.create();
		LeaveBalanceDao lBalanceDao = LeaveBalanceDaoFactory.create();
		ProfileInfo profileInfo = proInfoDao.findByPrimaryKey(user.getProfileId());
		LeaveBalance lBalance = lBalanceDao.findWhereUserIdEquals(user.getId());
		if (lBalance == null) return lBalance;
		int monthdiff = PortalUtility.getMothsBetween(profileInfo.getDateOfConfirmation(), new Date());
		if (profileInfo.getDateOfConfirmation() == null || monthdiff < 0){
			Calendar conf = Calendar.getInstance();
			conf.setTime(profileInfo.getDateOfJoining());
			if (calendar.get(Calendar.YEAR) == conf.get(Calendar.YEAR)){
				lBalance.setLeaveAccumalated(getInitLeave(profileInfo.getDateOfJoining()) + PortalUtility.getMothsBetween(profileInfo.getDateOfJoining(), new Date()));
			} else{
				if (calendar.get(Calendar.MONTH) == Calendar.JANUARY){
					float bLeave = lBalance.getBalance();
					if (bLeave >= 10) bLeave = 10;
					lBalance.setLeavesTaken(0);
					lBalance.setLeaveAccumalated(bLeave);
				}
				lBalance.setLeaveAccumalated(lBalance.getLeaveAccumalated() + 1);
			}
		} else if (calendar.get(Calendar.MONTH) == Calendar.JANUARY && monthdiff > 0){
			float bLeave = lBalance.getBalance();
			if (bLeave >= 10) bLeave = 10;
			lBalance.setLeaveAccumalated(bLeave + 20);
			lBalance.setLeavesTaken(0);
			lBalance.setBalance(lBalance.getLeaveAccumalated());
		}
		return lBalance;
	}

	public boolean compareEQDate(Date date1, Date date2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			String strDate1 = format.format(date1);
			String strDate2 = format.format(date2);
			if (strDate1.equals(strDate2)) return true;
		} catch (Exception e){
			logger.error(e.getMessage());
		}
		return false;
	}

	public boolean compareDate(Date currDate, Date confDate) {
		boolean result = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			String currDStr = format.format(currDate);
			String confDStr = format.format(confDate);
			Date currD = format.parse(currDStr);
			Date confD = format.parse(confDStr);
			if (confD.before(currD)){
				result = true;
			}
		} catch (Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

	private int getInitLeave(Date dateOfJoining) {
		int accLeave = 0;
		Calendar doj = Calendar.getInstance();
		doj.setTime(dateOfJoining);
		if (doj.get(Calendar.DAY_OF_MONTH) <= 15) accLeave = 1;
		return accLeave;
	}

	/**
	 * This method is used for updating LeaveBalance table based on the leaves
	 * taken
	 * 
	 * @param user
	 * @throws Exception
	 */
	private void updateLeave(LeaveBalance leaveBalance) throws Exception {
		leaveBalance.setBalance(leaveBalance.getLeaveAccumalated() - leaveBalance.getLeavesTaken());
		LeaveBalanceDaoFactory.create().update(new LeaveBalancePk(leaveBalance.getId()), leaveBalance);
	}
}
