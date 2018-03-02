package com.dikshatech.common.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.DepPerdiemComponentsDao;
import com.dikshatech.portal.dao.DepPerdiemDao;
import com.dikshatech.portal.dao.DepPerdiemReportDao;
import com.dikshatech.portal.dao.DepPerdiemReqDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.PerdiemMasterDataDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RollOnDao;
import com.dikshatech.portal.dto.Client;
import com.dikshatech.portal.dto.DepPerdiem;
import com.dikshatech.portal.dto.DepPerdiemReport;
import com.dikshatech.portal.dto.DepPerdiemReq;
import com.dikshatech.portal.dto.EmpSerReqHistory;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.exceptions.ClientDaoException;
import com.dikshatech.portal.exceptions.CurrencyDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemHistoryDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemReportDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemReqDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.ProjectMappingDaoException;
import com.dikshatech.portal.exceptions.RollOnDaoException;
import com.dikshatech.portal.factory.ClientDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemComponentsDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReportDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReqDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.PerdiemMasterDataDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.ReconciliationModel;

public class ReconciliationReportGeneratorAndNotifier {

	private static Logger	logger	= LoggerUtil.getLogger(ReconciliationReportGeneratorAndNotifier.class);

	public int generateReportAndNotify() {
		return generateReportAndNotify(0);
	}    
	

	public int generateReportAndNotify(int userId) {
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		String month = new SimpleDateFormat("MMMM").format(new Date());
		int presentDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
		int lastDateOfThisMonth = (Integer.parseInt(new SimpleDateFormat("dd").format(getLastDateOfMonth(year, Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))))));
		logger.info("Perdiem Report Generation Initiated for the " + ((presentDate < 15) ? "First" : "Second" + " Term of " + month + " " + year));
		PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create();
		EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
		RegionsDao regionsDao = RegionsDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		DepPerdiemDao depPerdiemDao = DepPerdiemDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		EmpSerReqMapPk esrmapPk = null;
		try{	
			DepPerdiem details = null;
			ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_DEPUTATION_PROC")[0];
			/*DepPerdiem detailsList[] = depPerdiemDao.findByDynamicWhere(" MONTH=? AND YEAR=? AND TERM=?", new Object[] { month, year, (presentDate < 15) ? "First" : "Second" });*/
			List<DepPerdiem> detailsList = depPerdiemDao.findByDynamicListWhere(" MONTH=? AND YEAR=? AND TERM=?", new Object[] { month, year, "Monthly" });
			Integer[] lastLevelApproverIds = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
			if (detailsList != null && !detailsList.isEmpty()){
				//Second time generation
				if (userId == 0) return 1;
				details = detailsList.get(0);
				Set<Integer> toList = new HashSet<Integer>();
				Connection conn = null;
				try{
					conn = ResourceManager.getConnection();
					DepPerdiemReqDao depPerdiemReqDao = DepPerdiemReqDaoFactory.create(conn);
					DepPerdiemReq perdiemReq[] = depPerdiemReqDao.findWhereDepIdEquals(details.getId());
					if (perdiemReq != null && perdiemReq.length > 0){
						for (DepPerdiemReq req : perdiemReq){
							toList.add(req.getAssignedTo());
							depPerdiemReqDao.delete(req.createPk());
						}
					}
					JDBCUtiility.getInstance().update("DELETE FROM DEP_PERDIEM_REQ WHERE DEP_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM DEP_PERDIEM_REPORT WHERE DEP_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM DEP_PERDIEM_EXCHANGE_RATES WHERE REP_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { details.getEsrMapId() }, conn);
				} catch (Exception e){
					e.printStackTrace();
				} finally{
					ResourceManager.close(conn);
				}
				details.setSubmittedOn(new Date());
				details.setStatus("Report Generated");
				details.setHtmlStatus("Report Generated");
				depPerdiemDao.update(details.createPk(), details);
				try{
					String userName = ModelUtiility.getInstance().getEmployeeName(userId);
					sendMail(toList.toArray(new Integer[toList.size()]), lastLevelApproverIds, userName);
				} catch (Exception e){}
			} else{
				//First time generation
				int userRegionid = 1;
				details = new DepPerdiem();
				int maxDepId = depPerdiemDao.findMaxId();
				String reqId = regionsDao.findByPrimaryKey(userRegionid).getRefAbbreviation() + "-MR-" + (maxDepId);
				EmpSerReqMap esrRow = new EmpSerReqMap();
				esrRow.setSubDate(new Date());
				esrRow.setReqId(reqId);
				esrRow.setReqTypeId(16);
				esrRow.setRegionId(userRegionid);
				esrRow.setRequestorId(1);
				esrRow.setProcessChainId(processChainDto.getId());
				esrRow.setNotify(processChainDto.getNotification());
				esrmapPk = esrMapDao.insert(esrRow);
				//
				details.setEsrMapId(esrmapPk.getId());
				//details.setTerm((presentDate < 15) ? "First" : "Second");
				details.setTerm("Monthly");
				details.setMonth(month);
				details.setYear(year);
				details.setSubmittedOn(new Date());
				details.setStatus("Report Generated");
				details.setHtmlStatus("Report Generated");
				depPerdiemDao.insert(details);
			}
			Date[] dates = getStartEndDates(PortalUtility.formateDateTimeToDateyyyyMMdd(new Date()));
			//System.out.println("Start Date ::: " +dates[0]);
			//System.out.println("End Date ::: "+dates[1]);
			List<DepPerdiemReport> list = perdiemMasterDao.getPerdiemCurrencyMaps(dates[0], dates[1]);// PERDIEM_MASTER_DATA
			Integer[] dateRelatedData = new Integer[] { year, presentDate, lastDateOfThisMonth };
			insertReports(list, details, details.getId(), dateRelatedData);
			Integer[] firstSeqApproverIds = pEval.handlers(processChainDto.getHandler(), 1);
			if (firstSeqApproverIds == null || firstSeqApproverIds.length == 0){
				logger.error("There are no approvers for perdiem reconciliation. however report generated and not assinged to any one");
				return 2;
			}
			insertIntoReq(firstSeqApproverIds, details.getId(), 0);
			String msgBody = sendMail(firstSeqApproverIds, lastLevelApproverIds, null);
			sendInboxNotification(firstSeqApproverIds, details.getEsrMapId(), msgBody, "Report Generated", "Reconciliation Report Generated");
		} catch (Exception ex){
			logger.error("Perdiem Report Generation Failed : ", ex);
			return 2;
		}
		return 0;
	}

	/**
	 * Based on given date sets the start date.
	 * ex:
	 * 	if day>15 then startdate will be 2013-01-16
	 *  else 2013-01-01
	 *  last date : last date of month 2013-01-31
	 * @param date
	 * @return new Date[]{Startdate, lastDate}
	 */
	private Date[] getStartEndDates(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date startDate = calendar.getTime();
		Date lastDate = calendar.getTime();
		/*if (calendar.get(Calendar.DAY_OF_MONTH) < 16){
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, 15);
			lastDate = calendar.getTime();
		} else{
			calendar.set(Calendar.DAY_OF_MONTH, 16);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastDate = calendar.getTime();
		}*/
		
		if(calendar.get(Calendar.DAY_OF_MONTH) < 16){
			calendar.set(Calendar.DAY_OF_MONTH, 15);
			lastDate = calendar.getTime();
			
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 16);
			startDate = calendar.getTime();
		}else{
			calendar.set(Calendar.DAY_OF_MONTH, 16);
			startDate = calendar.getTime();
			
			calendar.add(Calendar.MONTH, +1);
			calendar.set(Calendar.DAY_OF_MONTH, 15);
			lastDate = calendar.getTime();
		}
		
		/*calendar.set(Calendar.DAY_OF_MONTH, 15);
		lastDate = calendar.getTime();
		
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 16);
		startDate = calendar.getTime();*/
		
		return new Date[] { startDate, lastDate };
	}
	public static float findPaidLeavesDays(int userId,Date cycleFrom ,Date cycleTo){
		int financialManager=16;//hardcoding
		float paiddays=0.0f;
		java.sql.Connection userConn=null;
		final boolean isConnSupplied = (userConn != null);
		Connection conn1 = null;
		PreparedStatement stmt = null;
		ResultSet  rset= null;
		ProfileInfo proInfo=new ProfileInfo();
		
		List<EmpSerReqHistory> listOfEsrmapId=new ArrayList();
		
		LeaveMaster leaveMaster3rdQuery = new LeaveMaster();
		float lwp=0.0f;
		try {
			try{
			//query to get SpockId  based on userId
			String sql0="SELECT u.ID,pri.HR_SPOC from USERS u,PROFILE_INFO pri where u.PROFILE_ID=pri.ID and u.ID="+userId+";";
			conn1 = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn1.prepareStatement(sql0);
			rset = stmt.executeQuery();
			while(rset.next())
			{
				proInfo.setId((int)rset.getInt(1));
				proInfo.setHrSpoc((int)rset.getInt(2));
				//System.out.println("UserId::"+proInfo.getId()+" HRSpoc::"+proInfo.getHrSpoc());
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					conn1.close();
					stmt.close();
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try{
				//converting date to timestamp
				SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
				String fromString="";
				String toString="";
				fromString = sdfr.format( cycleFrom );
				toString = sdfr.format( cycleTo );
				fromString=fromString+" 00:00:01";
				toString=toString+" 23:59:59";
			//query to get list of esrmapId based on Perdiem Cycle dates
			String sql1="SELECT * FROM DIKSHA_PORTAL_2.EMP_SER_REQ_MAP A , LEAVE_MASTER  B WHERE  A.ID = B.ESR_MAP_ID AND B.APPROVED_DATE_TIME BETWEEN '"+fromString+"' AND '"+toString+"' AND REQUESTOR_ID = "+userId+" AND ASSIGNED_TO= "+proInfo.getHrSpoc()+"  GROUP BY A.ID";
			conn1 = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn1.prepareStatement(sql1);
			rset = stmt.executeQuery();
			while(rset.next())
			{
				EmpSerReqHistory empSerReqHistory=new EmpSerReqHistory();
				empSerReqHistory.setEsrMapId((int)rset.getInt(1));
				listOfEsrmapId.add(empSerReqHistory);
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					conn1.close();
					stmt.close();
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			for(EmpSerReqHistory objBean:listOfEsrmapId){
				LeaveMaster leaveMaster2ndQuery = new LeaveMaster();
				try{
				String sql2="SELECT status,duration FROM DIKSHA_PORTAL_2.LEAVE_MASTER where esr_map_id="+objBean.getEsrMapId()+" and ASSIGNED_TO="+proInfo.getHrSpoc()+";";
				conn1 = isConnSupplied ? userConn : ResourceManager.getConnection();
				stmt = conn1.prepareStatement(sql2);
				rset = stmt.executeQuery();
				while(rset.next()){
					leaveMaster2ndQuery.setStatus((String)rset.getString(1));
					leaveMaster2ndQuery.setDuration((float)rset.getFloat(2));
					//System.out.println("Status::"+leaveMaster2ndQuery.getStatus()+" Duration::"+leaveMaster2ndQuery.getDuration());
				}
				}
				catch (Exception e) {
					e.printStackTrace();
					}
					finally {
						try {
							conn1.close();
							stmt.close();
							rset.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				if(leaveMaster2ndQuery.getStatus()!=null){
				if(leaveMaster2ndQuery.getStatus().equals("Accepted")){
					try{
					String sql3="SELECT islwp,duration FROM DIKSHA_PORTAL_2.LEAVE_MASTER where esr_map_id="+objBean.getEsrMapId()+" and ASSIGNED_TO="+financialManager+";";
					conn1 = isConnSupplied ? userConn : ResourceManager.getConnection();
					stmt = conn1.prepareStatement(sql3);
					rset = stmt.executeQuery();
					while(rset.next()){
						leaveMaster3rdQuery.setIslwp((float)rset.getFloat(1));
						leaveMaster3rdQuery.setDuration((float)rset.getFloat(2));
						lwp=lwp+leaveMaster3rdQuery.getIslwp();
						if(leaveMaster3rdQuery.getDuration()!=leaveMaster3rdQuery.getIslwp()){
							paiddays=paiddays+(leaveMaster3rdQuery.getDuration()-leaveMaster3rdQuery.getIslwp());
						}
					}while(rset.previous()){
						
					}
					if(rset.next()==false){
						paiddays=paiddays+leaveMaster2ndQuery.getDuration();
					}
					}catch (Exception e) {
						e.printStackTrace();
					}
					finally {
						try {
							conn1.close();
							stmt.close();
							rset.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
				}
				}
			}
			return paiddays;	
		}catch (Exception e) {
		e.printStackTrace();
		}
		finally {
			try {
				conn1.close();
				stmt.close();
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return paiddays;
	}

	private void insertReports(List<DepPerdiemReport> list, DepPerdiem details, int depId, Integer[] dateRelatedData) throws NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, DepPerdiemHistoryDaoException, ProfileInfoDaoException, CurrencyDaoException, ProjectMappingDaoException, ProjectDaoException {
		/* dateRelatedData [] = new Integer[]{year,presentDate,lastDateOfThisMonth}; */
		int presentDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		RollOnDao rollOnDao = RollOnDaoFactory.create();
		DepPerdiemReportDao depPerdiemReportDao = DepPerdiemReportDaoFactory.create();
		DepPerdiemComponentsDao componentsDao = DepPerdiemComponentsDaoFactory.create();
		for (DepPerdiemReport report : list){
			
			if(report.getType() == ReconciliationModel.FIXED) {
				//Fixed PerDiem calculations
				//No of days  =  Date of joining - Total Number of Days in a Perdiem Cycle.
				Float payableDays = report.getPayableDays();
				ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(report.getUserId());
				Date dateOfJoining = profileInfo.getDateOfJoining();
				Date fromDate = report.getFrom();
				
				long diff = Math.abs(fromDate.getTime() - dateOfJoining.getTime());
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				if(dateOfJoining.compareTo(fromDate) > 0 && dateOfJoining.compareTo(report.getTo()) < 0) {
					//If dateOfJoining in between fronDate and toDate				
					payableDays = payableDays-diffDays;
				} 
				else if(dateOfJoining.compareTo(fromDate) < 0 && diffDays < 3) {
					payableDays = payableDays + diffDays;
				}
				
				//TODO: An Amount of Rs.200 will be deducted, for the no. of accommodation days provided to  the employee by the company
				//TODO: Salary and travel advance must be deducted one time or with no of equal installments
				
				//Adding code for salary in advance
				Float salaryadvance = (float)0;
				String[] salfixed = FinanceInfoDaoFactory.create().getSalaryInAdv(report.getUserId());
				if(/*salfixed[7].equalsIgnoreCase("PERDEIM")&&*/salfixed!=null&&salfixed[6].equalsIgnoreCase("PERDIEM"))
					//need too handle one condition if record exists and DEDUCTION_STATUS is null ? so should we deduct in perdiem or salary
				{
					salaryadvance=Float.valueOf(salfixed[2]);
				}
				else
				{
					salaryadvance=(float)0;
				}
				//Adding code for travel in advance
				Float traveladvance = 0F;
				String[] travelfixed= FinanceInfoDaoFactory.create().getTravelInAdv(report.getUserId());
				if(/*travelfixed[7].equalsIgnoreCase("PERDEIM")&&*/travelfixed!=null&&travelfixed[6].equalsIgnoreCase("PERDIEM"))
				{
					//need too handle one condition if record exists and DEDUCTION_STATUS is null ? so should we deduct in perdiem or salary
					traveladvance=Float.valueOf(travelfixed[2]);
				}
				else
				{
					traveladvance=(float) 0;
				}
				Float amount = payableDays * Float.valueOf(report.getPerdiem());
				Float totalAmount = amount - salaryadvance -traveladvance ;
						
				report.setPayableDays(payableDays);
				report.setAmount(amount+"");
				report.setTotal(totalAmount+"");
				report.setSalaryAdvanceDeduction(salaryadvance+"");
				report.setTravelAdvanceDeduction(traveladvance+"");
		
			} else if(report.getType() == ReconciliationModel.AUTO) {
				//Variable PerDiem calculations
				Float payableDays = report.getPayableDays();
				ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(report.getUserId());
				RollOn[] rollOns = null;	
				try {
					 rollOns = rollOnDao.findByDynamicWhere("CH_CODE_ID not in (?) AND EMP_ID = ? AND START_DATE < ? AND (ROLL_OFF_DATE IS NULL OR ROLL_OFF_DATE > ?)", 
							new Object[] { 16, report.getUserId(), report.getTo(), report.getFrom() });
				} catch (RollOnDaoException e) {
					e.printStackTrace();
				}
				
				Date sDate = report.getFrom();
				Date eDate = report.getTo();
				Float totalDays = 0F;
				for (RollOn rollOn : rollOns) {
						// check is roll On start date greater than PerDiem start cycle
						if(rollOn.getStartDate().compareTo(report.getFrom()) > 0) {
							sDate = rollOn.getStartDate();
						} 
						// check is roll off date is null AND  than PerDiem start cycle
						if(rollOn.getRollOffDate() != null && report.getTo().compareTo(rollOn.getRollOffDate()) > 0) {
							eDate = rollOn.getRollOffDate();	
						}
						
						long diff = Math.abs(sDate.getTime() - eDate.getTime());
						long diffDays = diff / (24 * 60 * 60 * 1000);
						totalDays = totalDays+diffDays;
					}
				// deducting LWP Days
				totalDays = totalDays - report.getLwpDays();
				
				
				//deducting paid leaves
				Float paidLeaves = findPaidLeavesDays(report.getUserId(),report.getFrom(),report.getTo());
				
				totalDays = totalDays - paidLeaves;
			 	
				
				
				//Adding code for salary in advance
				Float salaryadvance = (float) 0;
				String[] salvar = FinanceInfoDaoFactory.create().getSalaryInAdv(report.getUserId());
				if(/*salvar[7].equalsIgnoreCase("PERDIEM")&&*/salvar!=null&&salvar[6].equalsIgnoreCase("PERDIEM"))
				{	//need too handle one condition if record exists and DEDUCTION_STATUS is null ? so should we deduct in perdiem or salary
					
					salaryadvance=Float.valueOf(salvar[2]);
				}
				else
				{
					salaryadvance = (float)0;
				}
				
				
				
				//Adding code for travel in advance
				Float traveladvance = (float) 0;
				String[] travelfixed= FinanceInfoDaoFactory.create().getTravelInAdv(report.getUserId());
				if(/*travelfixed[7].equalsIgnoreCase("PERDEIM")&&*/travelfixed!=null&&travelfixed[6].equalsIgnoreCase("PERDIEM"))
				{
					//need too handle one condition if record exists and DEDUCTION_STATUS is null ? so should we deduct in perdiem or salary
					traveladvance=Float.valueOf(travelfixed[2]);
				}
				else
				{
					traveladvance = (float) 0;
				}
				//When on paid leaves rupees 200 only, shall be paid when an employee is on leave.
				Float amount = (totalDays * Float.valueOf(report.getPerdiem())) + (paidLeaves*200);
				Float totalAmount = amount - salaryadvance -traveladvance;
				
					
				//TODO: Salary and travel advance must be deducted one time or with no of equal installments
										
				report.setPayableDays(payableDays);
				report.setAmount(amount+"");
				report.setTotal(totalAmount+"");
				report.setSalaryAdvanceDeduction(salaryadvance+"");
				report.setTravelAdvanceDeduction(traveladvance+"");
								
			}
					
			ProfileInfo[] userProfileInfo = profileInfoDao.findByDynamicWhere(
					"ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { report.getUserId() });
			String client = null;

			Client[] clients = null;
			try {
				clients = ClientDaoFactory.create().findByDynamicSelect(
						"SELECT C.* FROM CLIENT C JOIN PROJ_CLIENT_MAP PCM ON PCM.CLIENT_ID= C.ID JOIN PROJECT_MAPPING P ON  P.PROJECT_ID= PCM.PROJ_ID  WHERE USER_ID=? ORDER BY P.ID DESC LIMIT 0,1",
						new Object[] { report.getUserId() });
			} catch (ClientDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (clients != null && clients.length > 0) {
				client = clients[0].getName();
			} else {
				client = "Diksha";
				logger.error("perdiem report : emp_name=" + userProfileInfo[0].getFirstName()
						+ " is not associated with any client, dep_id=" + depId);
			}
			
			report.setClientName(client);
			report.setDepId(depId);
			report.setManagerId(userProfileInfo[0].getReportingMgr());
			report.setManagerName(ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()));
			
			if (report.getCurrencyType() == 1){
				report.setAmountInr(report.getAmount());
			}
			try {
				depPerdiemReportDao.insert(report);
			} catch (DepPerdiemReportDaoException e) {
				e.printStackTrace();
			}
			
/*			
			try{
				ProfileInfo[] userProfileInfo = profileInfoDao.findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { report.getUserId() });
				String client = null;
				if (report.getClientName() == null){
					Client[] clients = ClientDaoFactory.create().findByDynamicSelect("SELECT C.* FROM CLIENT C JOIN PROJ_CLIENT_MAP PCM ON PCM.CLIENT_ID= C.ID JOIN PROJECT_MAPPING P ON  P.PROJECT_ID= PCM.PROJ_ID  WHERE USER_ID=? ORDER BY P.ID DESC LIMIT 0,1", new Object[] { report.getUserId() });
					if (clients != null && clients.length > 0){
						client = clients[0].getName();
					} else{
						client = "Diksha";
						logger.error("perdiem report : emp_name=" + userProfileInfo[0].getFirstName() + " is not associated with any client, dep_id=" + depId);
					}
				} else client = report.getClientName();
				double perdiemAmount=0;
				if(!report.getPerdiem().equalsIgnoreCase("")){
					
				perdiemAmount = report.getPayableDays() * Double.parseDouble((report.getPerdiem()));
				//System.out.println("Total Perdiem Amount ::: " + perdiemAmount + "for  :: "+userProfileInfo[0].getFirstName());
				
				}
				if (perdiemAmount <= 0){
					logger.info("Perdiem amount is 0 for " + userProfileInfo[0].getFirstName() + ". skipping this entry from reports.");
					//System.out.println("Perdiem amount is 0 for " + userProfileInfo[0].getFirstName() + ". skipping this entry from reports.");
					continue;
				}
				report.setAmount(perdiemAmount + "");
				double perdiemInAdvance = 0;
				PerdiemInAdvance salInAdv = null;
				try{
					salInAdv = PerdiemInAdvanceDaoFactory.create().findByPrimaryKey(report.getUserId());
					if (salInAdv != null && salInAdv.getMonthly() != null && Double.parseDouble(salInAdv.getMonthly()) > 0 && !(salInAdv.getMonthly().equalsIgnoreCase(""))){
						perdiemInAdvance = Double.parseDouble(salInAdv.getMonthly());
						if (report.getCurrencyType() == 1){
							// check the term
							if(salInAdv.getTerms().equalsIgnoreCase((presentDate < 15) ? "1" : "2")){
							report.setTotal((perdiemAmount - perdiemInAdvance) + "");
							}else{
							report.setTotal(perdiemAmount+ "");	
							}
							report.setTotal((perdiemAmount - perdiemInAdvance) + "");
						}
					}
				} catch (Exception e){
					logger.error("Unable to receive Salary in advance for user:" + report.getUserId() + " Exception:" + e.getMessage());
				}
				if (report.getCurrencyType() == 1){
					report.setAmountInr(report.getAmount());
					//TODO loan amount needs to deduct
					
					// check the term
					if(salInAdv.getTerms().equalsIgnoreCase((presentDate < 15) ? "1" : "2")){
					report.setTotal((perdiemAmount - perdiemInAdvance) + "");
					}else{
					report.setTotal(perdiemAmount+ "");	
					}
				}
				report.setData(depId, userProfileInfo[0].getReportingMgr(), ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()), client);
				depPerdiemReportDao.insert(report);
				try{
	// Adding Component Perdiem In Advance				
					DepPerdiemReport[]	allPerdiem=	depPerdiemReportDao.findByDynamicWhere("DEP_ID=? and USER_ID =?", new Object[] { depId,report.getUserId() });
					if(allPerdiem!=null && allPerdiem.length==1){
					if (salInAdv != null && salInAdv.getMonthly() != null && Double.parseDouble(salInAdv.getMonthly()) > 0 && salInAdv.getTerms().equalsIgnoreCase((presentDate < 15) ? "1" : "2")){
						DepPerdiemComponents components = new DepPerdiemComponents();
						components.setValues(report.getId(), (short) 2, "Per-Diem in Advance", 1, salInAdv.getMonthly(), "Auto", new Date(), "");
						components.setAmountInr(components.getAmount());
						components.setId(0);
						componentsDao.insert(components);
					}
					} else if(allPerdiem!=null &&  allPerdiem.length==2){
						for(DepPerdiemReport reportz:allPerdiem){
							if(reportz.getType()==0){
								reportz.setTotal(reportz.getTotal()+perdiemInAdvance+"");
								depPerdiemReportDao.update(reportz.createPk(), reportz);
							}
						}
						
					}
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to receive Salary in advance for user:" + report.getUserId() + " Exception:" + e.getMessage());
				}
			} catch (Exception e){
				e.printStackTrace();
			}*/
		}
	}

	/*private void insertIntoHistory(List<HashMap<Integer, String>> list, DepPerdiem details, int depId, Integer[] dateRelatedData) throws NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, DepPerdiemHistoryDaoException, ProfileInfoDaoException, CurrencyDaoException, ProjectMappingDaoException, ProjectDaoException {
		 dateRelatedData [] = new Integer[]{year,presentDate,lastDateOfThisMonth}; 
		HashMap<Integer, String> userPerdiemMap = list.get(0);
		HashMap<Integer, String> userCurrencyMap = list.get(1);
		HashMap<Integer, String> userPayableDaysMap = list.get(2);
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		DepPerdiemReportDao depPerdiemReportDao = DepPerdiemReportDaoFactory.create();
		for (Iterator<Integer> idIter = userPerdiemMap.keySet().iterator(); idIter.hasNext();){
			try{
				int userId = idIter.next();
				int currencyId;
				if (userCurrencyMap.get(userId) == null){
					currencyId = 1;
				} else{
					currencyId = Integer.parseInt(userCurrencyMap.get(userId));
				}
				ProfileInfo[] userProfileInfo = profileInfoDao.findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { userId });
				Client[] clients = ClientDaoFactory.create().findByDynamicSelect("SELECT C.* FROM CLIENT C JOIN PROJ_CLIENT_MAP PCM ON PCM.CLIENT_ID= C.ID JOIN PROJECT_MAPPING P ON  P.PROJECT_ID= PCM.PROJ_ID  WHERE USER_ID=? ORDER BY P.ID DESC LIMIT 0,1", new Object[] { userId });
				String projectName;
				if (clients != null && clients.length > 0){
					projectName = clients[0].getName();
				} else{
					projectName = "N.A";
					logger.error("perdiem report : report generation : emp_name=" + userProfileInfo[0].getFirstName() + ", was not associated with any project, dep_id=" + depId);
				}
				depPerdiemReportDao.insert(new DepPerdiemReport(depId, userId, Float.parseFloat(userPayableDaysMap.get(userId).trim()), DesEncrypterDecrypter.getInstance().decrypt(userPerdiemMap.get(userId)), currencyId, userProfileInfo[0].getReportingMgr(), ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()), projectName));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}*/
	private static Date getLastDateOfMonth(int year, int month) {
		if (month > 0){
			month--;
		}
		Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	private void insertIntoReq(Integer[] approverIds, int depId, int seqId) throws DepPerdiemReqDaoException {
		DepPerdiemReq depPerdiemReq = null;
		DepPerdiemReqDao perdiemReqDao = DepPerdiemReqDaoFactory.create();
		for (Integer eachApproverId : approverIds){
			depPerdiemReq = new DepPerdiemReq();
			depPerdiemReq.setDepId(depId);
			depPerdiemReq.setSeqId(seqId);
			depPerdiemReq.setAssignedTo(eachApproverId);
			depPerdiemReq.setReceivedOn(new Date());
			depPerdiemReq.setSubmittedOn(null);// intentionally
			depPerdiemReq.setIsEscalated(0);
			perdiemReqDao.insert(depPerdiemReq);
		}
	}

	private void sendInboxNotification(Integer[] receiverIds, int esrMapId, String msgBody, String status, String subject) throws InboxDaoException {
		Inbox inbox = null;
		InboxDao inboxDao = InboxDaoFactory.create();
		for (Integer eachReceiverid : receiverIds){
			inbox = new Inbox();
			inbox.setReceiverId(eachReceiverid);
			inbox.setEsrMapId(esrMapId);
			inbox.setSubject(subject);
			inbox.setAssignedTo(0);
			inbox.setRaisedBy(1);
			inbox.setCreationDatetime(new Date());
			inbox.setStatus(status);
			inbox.setCategory("PERDIEM_REPORT");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setMessageBody(msgBody);
			inboxDao.insert(inbox);
		}
	}

	private String sendMail(Integer[] toIds, Integer[] ccIds, String by) throws FileNotFoundException, ProfileInfoDaoException, AddressException, UnsupportedEncodingException, MessagingException, DepPerdiemDaoException {
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		String month = new SimpleDateFormat("MMMM").format(new Date());
		int presentDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
		int lastDateOfThisMonth = (Integer.parseInt(new SimpleDateFormat("dd").format(getLastDateOfMonth(year, Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))))));
		String perdiemTerm;
		if (presentDate < 15){
			perdiemTerm = "1st " + month + " - 15th " + month;
		} else{
			perdiemTerm = "16th " + month + " - " + (lastDateOfThisMonth == 31 ? "31st" : lastDateOfThisMonth + "th") + " " + month;
		}
		String msgBody = "";
		PortalMail portalMail = new PortalMail();
		portalMail.setTemplateName(MailSettings.PERDIEM_REPORT_AUTO_GENERATED);
		if (by != null){
			portalMail.setMailSubject("Per-Diem Report Re-Generated for " + perdiemTerm + " by " + by);
			portalMail.setMessageBody("The Per-Diem report for the period " + perdiemTerm + " has been re-generated by " + by);
		} else{
			portalMail.setMailSubject("Per-Diem Report Generated for " + perdiemTerm);
			portalMail.setMessageBody("The Per-Diem report for the period " + perdiemTerm + " has been generated and is awaiting your action. Please do the needful.");
		}
		MailGenerator mailGenerator = new MailGenerator();
		msgBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
		if (toIds != null && toIds.length > 0){
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			if (toIds != null && toIds.length == 1){
				portalMail.setApproverName(ModelUtiility.getInstance().getEmployeeName(toIds[0]));
			} else if (toIds != null && toIds.length > 1){
				StringBuffer toNames = new StringBuffer();
				for (int i = 0; i < toIds.length; i++){
					toNames.append(ModelUtiility.getInstance().getEmployeeName(toIds[i]));
					if (i != toIds.length - 1) toNames.append(" / ");
				}
				portalMail.setApproverName(toNames.toString());
			}
			if (toIds != null && toIds.length > 0){
				portalMail.setAllReceipientMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN(" + ModelUtiility.getCommaSeparetedValues(toIds) + "))"));
			}
			if (ccIds != null && ccIds.length > 0){
				portalMail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN(" + ModelUtiility.getCommaSeparetedValues(ccIds) + "))"));
			}
			mailGenerator.invoker(portalMail);
		}
		return msgBody;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	ReconciliationReportGeneratorAndNotifier junk = new ReconciliationReportGeneratorAndNotifier();
		junk.generateReportAndNotify();
	}	
	
}
