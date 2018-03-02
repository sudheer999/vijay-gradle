package com.dikshatech.portal.timer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.Status;

/**
 * @author gurunath.rokkam
 */
public class InboxTimer implements Job {

	private static Logger	logger	= Logger.getLogger(InboxTimer.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * put code to invoke the calculator method
		 */
		try{
			final String sql = "TIMESTAMPDIFF(DAY,CREATION_DATETIME,NOW())>=1 and IS_READ=1 AND ( RECEIVER_ID != ASSIGNED_TO OR (CATEGORY ='CANDIDATE' AND STATUS!='" + Status.PENDINGAPPROVAL + "')) AND CATEGORY NOT IN('ISSUES','ITSUPPORT')"
					+ "OR (TIMESTAMPDIFF(DAY,CREATION_DATETIME,NOW())>=1 and IS_READ=1 AND STATUS in ('Resolved','Closed','Revoked') AND CATEGORY IN('ISSUES','ITSUPPORT'))";
			
			//DELETE FROM INBOX 
			
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + sql);
			}
			
			//JDBCUtiility.getInstance().update(sql, null);
			JDBCUtiility.getInstance().deleteInbox(sql);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
