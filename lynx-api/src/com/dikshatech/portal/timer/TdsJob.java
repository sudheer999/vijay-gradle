package com.dikshatech.portal.timer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.TDSUtility;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.UsersDaoFactory;

/**
 * @author praneeth.r
 *         This job will insert the TDS records for each user at the starting of the financial year
 */
public class TdsJob implements Job {

	private static Logger	logger	= LoggerUtil.getLogger(TdsJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Starting TDs Jobs");
		UsersDao usersDao = UsersDaoFactory.create();
		TDSUtility utility = new TDSUtility();
		Users[] users = null;
		try{
			users = usersDao.findByDynamicWhere(" STATUS NOT IN(1,2,3) AND EMP_ID !=0 ", new Object[] {});
			logger.info("deleting entries of period " + TDSUtility.getStartingMonth() + " - " + TDSUtility.getEndingMonth() + " if already exists. Deleted entries count:" + (JDBCUtiility.getInstance().update("DELETE FROM TDS  WHERE  MONTH_ID BETWEEN ? AND ? ", new Object[] { TDSUtility.getStartingMonth(), TDSUtility.getEndingMonth() })));
			if (users != null && users.length > 0){
				for (Users eachUser : users){
					utility.saveTds(eachUser.getId());
				}
			}
		} catch (UsersDaoException e){
			logger.error(" There occured a UsersDaoException while querying the records for the users");
		}
	}

	public static void main(String[] args) throws Exception {
		new TdsJob().execute(null);
	}
}
