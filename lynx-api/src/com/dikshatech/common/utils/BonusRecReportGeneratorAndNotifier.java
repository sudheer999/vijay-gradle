package com.dikshatech.common.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.BonusMasterDataDao;
import com.dikshatech.portal.dao.BonusReconciliationDao;
import com.dikshatech.portal.dao.BonusReconciliationReportDao;
import com.dikshatech.portal.dao.BonusReconciliationReqDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.BonusMasterData;
import com.dikshatech.portal.dto.BonusReconciliation;
import com.dikshatech.portal.dto.BonusReconciliationReport;
import com.dikshatech.portal.dto.BonusReconciliationReq;
import com.dikshatech.portal.dto.Client;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.BonusMasterDataDaoException;
import com.dikshatech.portal.exceptions.BonusReconciliationReportDaoException;
import com.dikshatech.portal.exceptions.BonusReconciliationReqDaoException;
import com.dikshatech.portal.exceptions.CurrencyDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemHistoryDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.ProjectMappingDaoException;
import com.dikshatech.portal.exceptions.RollOnDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.BonusMasterDataDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationReqDaoFactory;
import com.dikshatech.portal.factory.ClientDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;


public class BonusRecReportGeneratorAndNotifier {
	private static Logger	logger	= LoggerUtil.getLogger(ReconciliationReportGeneratorAndNotifier.class);

	public int generateReportAndNotify() {
		return generateReportAndNotify(0);
		}
	
		
		
		public int generateReportAndNotify(int userId) {
			int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			int val =Calendar.getInstance().get(Calendar.MONTH)+1; 
	        String month=findStringFormatofMonth(val);
			if(month==null) return 3;
			else{
			if (month.equalsIgnoreCase("MARCH")||month.equalsIgnoreCase("JUNE")||month.equalsIgnoreCase("SEPTEMBER")||month.equalsIgnoreCase("DECEMBER")){
            
			logger.info("Bonus Report Generation Initiated for the Month of " + month + " " + year);
			BonusMasterDataDao bonusMasterDao = BonusMasterDataDaoFactory.create();
			EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
			RegionsDao regionsDao = RegionsDaoFactory.create();
			ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
			BonusReconciliationDao bonusDao = BonusReconciliationDaoFactory.create();
			ProcessEvaluator pEval = new ProcessEvaluator();
			EmpSerReqMapPk esrmapPk = null;
			try{
				int userRegionid = 1;
				BonusReconciliation details = null;
				ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_BONUS_RECON")[0];
				BonusReconciliation detailsList[] = bonusDao.findByDynamicWhere(" MONTH=? AND YEAR=? ", new Object[] { month, year });
				Integer[] lastLevelApproverIds = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
				if (detailsList != null && detailsList.length > 0){
				if (userId == 0) return 1;
					details = detailsList[0];
					Set<Integer> toList = new HashSet<Integer>();
					Connection conn = null;
					try{
						conn = ResourceManager.getConnection();
						BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create(conn);
						BonusReconciliationReq bonusReq[] = bonusReqDao.findWhereBonusIdEquals(details.getId());
						if (bonusReq != null && bonusReq.length > 0){
							for (BonusReconciliationReq req : bonusReq){
								toList.add(req.getAssignedTo());
								bonusReqDao.delete(req.createPk());
							}
						}
						JDBCUtiility.getInstance().update("DELETE FROM BONUS_RECONCILIATION_REQ WHERE BR_ID=?", new Object[] { details.getId() }, conn);
						JDBCUtiility.getInstance().update("DELETE FROM BONUS_REC_REPORT WHERE BR_ID=?", new Object[] { details.getId() }, conn);
						JDBCUtiility.getInstance().update("DELETE FROM BONUS_REC_EXCHANGE_RATE WHERE REP_ID=?", new Object[] { details.getId() }, conn);
						JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { details.getEsrMapId() }, conn);
						JDBCUtiility.getInstance().update("DELETE FROM BONUS_MASTER_DATA WHERE MONTH=? AND YEAR=?", new Object[] { month, year }, conn);
					} catch (Exception e){
						e.printStackTrace();
					} finally{
						ResourceManager.close(conn);
					}
					details.setSubmittedOn(null);
					details.setStatus("Report Generated");
					details.setHtmlStatus("Report Generated");
					bonusDao.update(details.createPk(), details);
					try{
					String userName = ModelUtiility.getInstance().getEmployeeName(userId);
					sendMail(toList.toArray(new Integer[toList.size()]), lastLevelApproverIds, userName);
					} catch (Exception e){}
				} else{
					details = new BonusReconciliation();
					int maxBonusId = bonusDao.findMaxId();
					String reqId = regionsDao.findByPrimaryKey(userRegionid).getRefAbbreviation() + "-BR-" + (maxBonusId);
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
					details.setMonth(month);
					details.setYear(year);
					details.setSubmittedOn(null);
					details.setStatus("Report Generated");
					details.setHtmlStatus("Report Generated");
					bonusDao.insert(details);
				}
				
				insertIntoBonusMaster(month,year);
				List<BonusReconciliationReport> list = bonusMasterDao.getBonusCurrencyMaps(month,year);// BONUS_MASTER_DATA
                insertReports(list, details, details.getId());
				Integer[] firstSeqApproverIds = pEval.handlers(processChainDto.getHandler(), 1);
				if (firstSeqApproverIds == null || firstSeqApproverIds.length == 0){
					logger.error("There are no approvers for bonus reconciliation. however report generated and not assinged to any one");
					return 2;
				}
				insertIntoReq(firstSeqApproverIds, details.getId(), 0);
				String msgBody = sendMail(firstSeqApproverIds, lastLevelApproverIds, null);
			sendInboxNotification(firstSeqApproverIds, details.getEsrMapId(), msgBody, "Report Generated", "Bonus Reconciliation Report Generated");
			
			
			} catch (Exception ex){
				logger.error("Bonus Report Generation Failed : ", ex);
				return 2;}
			} 
			}
			return 0;
		}
		
	public String findStringFormatofMonth(int value){
		String month=""; 
		if(value==3||value==6||value==9||value==12){
			
			if(value==3)       month= "MARCH"; 
			else if(value==6)  month= "JUNE";
			else if(value==9)  month= "SEPTEMBER";
			else if(value==12) month= "DECEMBER";
		
		}
		else {return null;}
		return month;
	}
	
	private void insertIntoReq(Integer[] approverIds, int bonusId, int seqId) throws BonusReconciliationReqDaoException {
		BonusReconciliationReq bonusReq = null;
		BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create();
		for (Integer eachApproverId : approverIds){
			bonusReq = new BonusReconciliationReq();
			bonusReq.setBonusId(bonusId);
			bonusReq.setSeqId(seqId);
			bonusReq.setAssignedTo(eachApproverId);
			bonusReq.setReceivedOn(new Date());
			bonusReq.setSubmittedOn(null);// intentionally
			bonusReq.setIsEscalated(0);
			bonusReqDao.insert(bonusReq);
			
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
			inbox.setCategory("BONUS_REPORT");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setMessageBody(msgBody);
			inboxDao.insert(inbox);
		}
	}

	private String sendMail(Integer[] toIds, Integer[] ccIds, String by) throws FileNotFoundException, ProfileInfoDaoException, AddressException, UnsupportedEncodingException, MessagingException, DepPerdiemDaoException {
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		int val =Calendar.getInstance().get(Calendar.MONTH)+1; 
		String month=findStringFormatofMonth(val);
	    String msgBody = "";
		PortalMail portalMail = new PortalMail();
		portalMail.setTemplateName(MailSettings.BONUS_REPORT_AUTO_GENERATED);
		if (by != null){
			portalMail.setMailSubject("Bonus Report Re-Generated for " + month +"  "+year+ " by " + by);
			portalMail.setMessageBody("The Bonus report for the month " + month +"  "+year+ " has been re-generated by " + by);
		} else{
			portalMail.setMailSubject("Bonus Report Generated for " + month);
			portalMail.setMessageBody("The Bonus report for the month " + month + " has been generated and is awaiting your action. Please do the needful.");
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
	
	
	private void insertReports(List<BonusReconciliationReport> list, BonusReconciliation details, int bonusId) throws NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, DepPerdiemHistoryDaoException, ProfileInfoDaoException, CurrencyDaoException, ProjectMappingDaoException, ProjectDaoException, BonusReconciliationReportDaoException, SQLException {
		/* dateRelatedData [] = new Integer[]{year,presentDate,lastDateOfThisMonth}; */
		Connection conn = null;
	    conn = ResourceManager.getConnection();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		BonusReconciliationReportDao bonusReportDao = BonusReconciliationReportDaoFactory.create(conn);
		
		for (BonusReconciliationReport report : list){
			try{
				ProfileInfo[] userProfileInfo = profileInfoDao.findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { report.getUserId() });
				String client = null;
				double total=0;
				if (report.getClientName() == null){
					Client[] clients = ClientDaoFactory.create().findByDynamicSelect("SELECT C.* FROM CLIENT C JOIN PROJ_CLIENT_MAP PCM ON PCM.CLIENT_ID= C.ID JOIN PROJECT_MAPPING P ON  P.PROJECT_ID= PCM.PROJ_ID  WHERE USER_ID=? ORDER BY P.ID DESC LIMIT 0,1", new Object[] { report.getUserId() });
					if (clients != null && clients.length > 0){
						client = clients[0].getName();
					} else{
						client = "Diksha";
						logger.error("bonus report : emp_name=" + userProfileInfo[0].getFirstName() + " is not associated with any client, bonus_id=" + bonusId);
					}
				} else client = report.getClientName();
				double qAmount = Double.parseDouble(report.getqBonus())/4;
				double cAmount = Double.parseDouble(report.getcBonus());
				if (qAmount <= 0 && cAmount<=0){
					logger.info("bonus amount is 0 for " + userProfileInfo[0].getFirstName() + ". skipping this entry from reports.");
					continue;
				}
				report.setqAmount(qAmount+"");
				report.setcAmount(cAmount + "");
			if (report.getCurrencyType() == 1){
				report.setqAmountInr(report.getqAmount());
				report.setcAmountInr(report.getcAmount());}
					// total amount needs to calculate 
			if(report.getMonth().equalsIgnoreCase("MARCH")) total+=qAmount+cAmount;
			else if(report.getMonth().equalsIgnoreCase("JUNE")||report.getMonth().equalsIgnoreCase("SEPTEMBER")||report.getMonth().equalsIgnoreCase("DECEMBER")) total+= qAmount;
			      report.setTotal(total+"");
				  report.setData(bonusId, userProfileInfo[0].getReportingMgr(), ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()), client);
				 			
		  // finding number of TotalQuater Days,project days and Global Bench Days
				int globalBenchDays=0;
		        int quatertotalDays=findtotaldays(report.getMonth());  // Finding Total Days of that Quater
		        int projectdays =findProjectDays(report.getUserId(),conn,report.getMonth()); // Finding Project Days of Bonus Quater	
		  if(projectdays>0)   
				globalBenchDays= quatertotalDays-projectdays;
		  else{
			    projectdays=0;
				globalBenchDays= quatertotalDays;
			     }
		report.setProjectDays(projectdays);
		report.setGlobalBenchDays(globalBenchDays);
		report.setTotalDays(quatertotalDays);
		
		bonusReportDao.insert(report);
		
			}
	catch (Exception e){
				e.printStackTrace();
	}
		}	
	}
	

	private void insertIntoBonusMaster(String month,int year){
		BonusMasterDataDao bonusMasterDao=BonusMasterDataDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		Users[] users;
		
		try{
			Connection conn = null;
		    conn = ResourceManager.getConnection();
		    SalaryDetailsDao salDetail=SalaryDetailsDaoFactory.create(conn);
			users = usersDao.findByDynamicWhere("STATUS=? AND EMP_ID>0 AND ID>3", new Object[] { 0 });
		
		if (users != null && users.length > 0){
			for (Users eachUser : users){
			double cBonus=0;
			double qBonus=0;
			int	userId = eachUser.getId();
			SalaryDetails[] comBonus=salDetail.findByDynamicSelect("SELECT ID, USER_ID, CANDIDATE_ID, FIELD_LABEL, MONTHLY, ANNUAL, SUM, FIELDTYPE, SAL_ID FROM SALARY_DETAILS WHERE USER_ID = ? AND SAL_ID= 10 ORDER BY USER_ID", new Object[] {  new Integer(userId)} );
			if (comBonus != null && comBonus.length > 0){
				for (SalaryDetails salCom : comBonus){
					if(month.equalsIgnoreCase("June")||month.equalsIgnoreCase("september")||month.equalsIgnoreCase("December")) cBonus=0;
					else cBonus=Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(salCom.getAnnual()));
					}
				}
			SalaryDetails[] salary=salDetail.findByDynamicSelect("SELECT ID, USER_ID, CANDIDATE_ID, FIELD_LABEL, MONTHLY, ANNUAL, SUM, FIELDTYPE, SAL_ID FROM SALARY_DETAILS WHERE USER_ID = ? AND SAL_ID= 9 ORDER BY USER_ID", new Object[] {  new Integer(userId)} );
			if (salary != null && salary.length>0){
				for (SalaryDetails sal : salary){
					qBonus=Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(sal.getAnnual()));
				}
			}
			SalaryDetails[] finalsalary=salDetail.findByDynamicSelect("SELECT * FROM SALARY_DETAILS WHERE USER_ID = ? ORDER BY USER_ID", new Object[] {  new Integer(userId)} );
			if (qBonus>0 || cBonus>0 && finalsalary!=null && finalsalary.length>0){
			BonusMasterData bonus=new BonusMasterData(); 
			bonus.setUserId(userId);
			bonus.setCurrencyType(1);
			bonus.setcBonus(cBonus+"");
			bonus.setqBonus(qBonus+"");
			bonus.setMonth(month);
			bonus.setYear(year);
			bonusMasterDao.insert(bonus);}
		}
		}
		} catch (UsersDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	 catch (SalaryDetailsDaoException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		catch (BonusMasterDataDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	catch (SQLException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BonusRecReportGeneratorAndNotifier autoTrigger = new BonusRecReportGeneratorAndNotifier();
		autoTrigger.generateReportAndNotify();
	}
		
	public int findtotaldays(String month)
		 {
			int totalDays=0;
			Calendar aCalendar = Calendar.getInstance();
			Date firstDateOfBonusMonth=null;
			Date lastDateOfBonusMonth=null;
			
			if(month.equalsIgnoreCase("MARCH")){
				aCalendar.set(Calendar.MONTH,0);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, 0);
				firstDateOfBonusMonth = aCalendar.getTime();
				aCalendar.set(Calendar.MONTH,3);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, -1);
				lastDateOfBonusMonth = aCalendar.getTime();
			}
			else if(month.equalsIgnoreCase("JUNE")){
				aCalendar.set(Calendar.MONTH,3);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, 0);
				firstDateOfBonusMonth = aCalendar.getTime();
				aCalendar.set(Calendar.MONTH,6);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, -1);
				lastDateOfBonusMonth = aCalendar.getTime();
				}
			else if(month.equalsIgnoreCase("SEPTEMBER")){
				aCalendar.set(Calendar.MONTH,6);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, 0);
				firstDateOfBonusMonth = aCalendar.getTime();
				aCalendar.set(Calendar.MONTH,9);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, -1);
				lastDateOfBonusMonth = aCalendar.getTime();
			}
			else if(month.equalsIgnoreCase("DECEMBER")){
				aCalendar.set(Calendar.MONTH,9);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, 0);
				firstDateOfBonusMonth = aCalendar.getTime();
				aCalendar.set(Calendar.MONTH,12);
				aCalendar.set(Calendar.DATE, 1);
				aCalendar.add(Calendar.DAY_OF_MONTH, -1);
				lastDateOfBonusMonth = aCalendar.getTime();
			}
			long diff = lastDateOfBonusMonth.getTime() - firstDateOfBonusMonth.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			totalDays=(int)diffDays;
			 return totalDays;
		 }
	
	
	public int findProjectDays(int userId,Connection conn,String month) throws ParseException, RollOnDaoException{
		int projectDays=0;
		RollOn[] roll=null;
		roll=RollOnDaoFactory.create(conn).findByDynamicSelect("SELECT * FROM ROLL_ON RO JOIN USERS U ON RO.EMP_ID=U.ID JOIN ROLL_ON_PROJ_MAP RP ON RO.ID=RP.ROLL_ON_ID JOIN PROJECT PR ON RP.PROJ_ID=PR.ID AND CURRENT=1 WHERE U.ID=? AND U.ID > 3 AND U.EMP_ID>0", new Object[] { userId});		
		for (RollOn rolls : roll){
			
			Date projStartDate=rolls.getStartDate();
			Date projEndDate=rolls.getEndDate();
			int daysCount=0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// Finding First Date and Last Date of Bonus Quater
				Date quaterFirstDate=findfirstBonusDate(month);
				Date quaterLastDate=findLastBonusDate(month);
		
	if(projStartDate!=null){
			if(projStartDate.before(quaterFirstDate)){
				String startDate=df.format(quaterFirstDate);
			    Date d1 =df.parse(startDate);
			    if(projEndDate==null){
			    	long diff = quaterLastDate.getTime() - d1.getTime();
					long diffDays = diff / (24 * 60 * 60 * 1000);	
					daysCount=(int)diffDays;
			    }else{
			    	String endDate=df.format(projEndDate);
			    	 Date d2 =df.parse(endDate);
			    	long diff = d2.getTime() - d1.getTime();
			    	long diffDays = diff / (24 * 60 * 60 * 1000);	
			    	daysCount=(int)diffDays;}
			   }
			else if(projStartDate.after(quaterFirstDate)){	
			    String startDate=df.format(projStartDate);
		        Date d1 =df.parse(startDate);
		        if(projEndDate==null){
			    	long diff = quaterLastDate.getTime() - d1.getTime();
					long diffDays = diff / (24 * 60 * 60 * 1000);	
					daysCount=(int)diffDays;
			    }else{
			    	String endDate=df.format(projEndDate);
			    	Date d2 =df.parse(endDate);
			    	long diff = d2.getTime() - d1.getTime();
			    	long diffDays = diff / (24 * 60 * 60 * 1000);	
			    	daysCount=(int)diffDays;}
			   }
			projectDays=daysCount;
	}	
	
	}
		return projectDays;	
	}
	
	public Date findfirstBonusDate(String month){
		Date firstBonusDate=null;
		Calendar aCalendar = Calendar.getInstance();
		if(month.equalsIgnoreCase("MARCH")){
			aCalendar.set(Calendar.MONTH,0);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}
		else if(month.equalsIgnoreCase("JUNE")){
			aCalendar.set(Calendar.MONTH,3);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
			}
		else if(month.equalsIgnoreCase("SEPTEMBER")){
			aCalendar.set(Calendar.MONTH,6);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}
		else if(month.equalsIgnoreCase("DECEMBER")){
			aCalendar.set(Calendar.MONTH,9);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}
		
		return firstBonusDate;
	}
	
		
	public Date findLastBonusDate(String month){
		Date lastBonusDate=null;
		Calendar aCalendar = Calendar.getInstance();
		if(month.equalsIgnoreCase("MARCH")){
		aCalendar.set(Calendar.MONTH,3);
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		lastBonusDate = aCalendar.getTime();
		}
		else if(month.equalsIgnoreCase("JUNE")){
		aCalendar.set(Calendar.MONTH,6);
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		lastBonusDate = aCalendar.getTime();
		}
		else if(month.equalsIgnoreCase("SEPTEMBER")){
		aCalendar.set(Calendar.MONTH,9);
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		lastBonusDate = aCalendar.getTime();
		}
		else if(month.equalsIgnoreCase("DECEMBER")){
		aCalendar.set(Calendar.MONTH,12);
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		lastBonusDate = aCalendar.getTime();
		}
	 return lastBonusDate;
	}
		
	}

