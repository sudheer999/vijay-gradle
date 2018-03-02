package com.dikshatech.portal.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.dao.BonusReconciliationReportDao;
import com.dikshatech.portal.dao.FbpDetailsDao;
import com.dikshatech.portal.dao.FbpReqDao;
import com.dikshatech.portal.dao.MonthlyPayrollDao;
import com.dikshatech.portal.dao.RetentionBonusDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.TdsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dao.VoluntaryProvidentFundDao;
import com.dikshatech.portal.dto.BonusReconciliationReport;
import com.dikshatech.portal.dto.FbpDetails;
import com.dikshatech.portal.dto.FbpReq;
import com.dikshatech.portal.dto.MonthlyPayroll;
import com.dikshatech.portal.dto.MonthlyPayrollPk;
import com.dikshatech.portal.dto.RetentionBonus;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.Tds;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.VoluntaryProvidentFund;
import com.dikshatech.portal.exceptions.BonusReconciliationReportDaoException;
import com.dikshatech.portal.exceptions.FbpDetailsDaoException;
import com.dikshatech.portal.exceptions.FbpReqDaoException;
import com.dikshatech.portal.exceptions.MonthlyPayrollDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.TdsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.exceptions.VoluntaryProvidentFundDaoException;
import com.dikshatech.portal.factory.BonusReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.FbpDetailsDaoFactory;
import com.dikshatech.portal.factory.FbpReqDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.MonthlyPayrollDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.TdsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.factory.VoluntaryProvidentFundDaoFactory;
import com.dikshatech.portal.models.FBPModel;
import com.dikshatech.portal.models.SalaryReconciliationModel;

public class MonthlySalaryJob implements Job {

	private final Logger				logger	= LoggerUtil.getLogger(MonthlySalaryJob.class);
	private boolean						okFlag	= true;
	private static Map<String, String>	names	= new HashMap<String, String>();
	static{
		names.put("A - BASIC - Basic Salary", "Basic");
		names.put("B - ALLOWANCE - Conveyance", "Conveyance");
		names.put("B - ALLOWANCE - HRA", "HRA");
		names.put("B - ALLOWANCE - Additional Allowances", "Addt allowances");
		names.put("LTA", "Leave Travel Allow");
		names.put("TPA", "Telephone Allowance");
		names.put("MA", "Medical Allowance");
		names.put("CEA", "Child Care Allowance");
		names.put("MV", "Meal Allowance");
		names.put("TRA", "Transport Allowance");
		names.put("OA", "Other Allowance");
		names.put("Employer Contribution to the Provident Funds", "Provident Fund");
		names.put("Professional Tax", "Professional Tax");
		names.put("Tax Deduction at Source (TDS)", "TDS");
		names.put("Voluntary Provident Fund", "VPF");
		names.put("Employees' State Insurance Corporation", "ESIC");
		names.put("Sodexo Meal Vouchers", "Sodexo");
		names.put("Salary In Advance", "Salary In Advance");
		names.put("Travel In Advance", "Travel In Advance");
		names.put("D-VARIABLES-Quaterely Performance Linked Bonus(QPLB)", "Quaterely Bonus");
		names.put("D-VARIABLES-Company Performance Linked Bonus(CPLB)", "Company Bonus");
		names.put("D-VARIABLES-Retention Bonus", "Retention Bonus");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			doJob(0);
		} catch (FbpReqDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VoluntaryProvidentFundDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SalaryDetailsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FbpDetailsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int doJob(int i) throws FbpReqDaoException, VoluntaryProvidentFundDaoException, SalaryDetailsDaoException, FbpDetailsDaoException  {
		MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		int userId = 0;
		try{
			String monthId = FBPModel.getMonthId();
			
			
			
			// check bonus reconciliation already done 
		//	String month=findMonth(monthId);
		/*if(month.equalsIgnoreCase("March")||month.equalsIgnoreCase("June")||month.equalsIgnoreCase("September")||month.equalsIgnoreCase("december"))
			{
			BonusReconciliation bonus[] = bonusDao.findByDynamicWhere(" MONTH=? AND YEAR=? ", new Object[] { month, year });
				if (bonus == null || bonus.length == 0){
					logger.error("Bonus Reconciliation for the month " + month + " is Still pending ,do you need to proceed Salary Reconciliation ");
					return 3;
			}
		}*/
			int rowCount = (int) JDBCUtiility.getInstance().getRowCount("FROM MONTHLY_PAYROLL WHERE MONTHID=?", new Object[] { monthId });
			if (i == 0 && rowCount > 0) return 1;
			Users[] users = usersDao.findByDynamicWhere("EMP_ID>? AND ID>3", new Object[] { 0 });
    //        Users[] users = usersDao.findByDynamicWhere("STATUS=? AND EMP_ID>0 AND ID>3", new Object[] { 0 });
			JDBCUtiility.getInstance().update("DELETE FROM MONTHLY_PAYROLL WHERE MONTHID=?", new Object[] { monthId });
			if (users != null && users.length > 0){
				for (Users eachUser : users){
					userId = eachUser.getId();
					List<MonthlyPayroll> salaries = getGrossSalaries(userId, monthId);
					salaries.addAll(getAllowances(userId, monthId));
					salaries.addAll(getDeductions(userId, monthId));
			//		salaries.addAll(getRetentionBonus(userId, monthId));
				/*	if(monthId.equals(str.concat("03"))||monthId.equals(str.concat("06"))||monthId.equals(str.concat("09"))||monthId.equals(str.concat("12")))
					{
				 salaries.addAll(getBonus(userId, monthId));		
					}*/
					for (MonthlyPayroll eachDetail : salaries){
						
						
						if (eachDetail.getAmount() != null){
							eachDetail.setId(0);
							eachDetail.setAmount(DesEncrypterDecrypter.getInstance().encrypt(Math.round(Double.parseDouble((DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getAmount())))) + ".00"));
							eachDetail.setComponent(names.get(eachDetail.getComponent()));
							monthlyPayrollDao.insert(eachDetail);
						}
						
					}
					if (!okFlag){
						logger.error("The records for the user " + userId + "for the month" + monthId + " is incomplete and hence they deleting ");
						deleteInCompleteRecords(userId, monthId);
					}
					okFlag = true;
				
				}
			}
			
		}
		catch (UsersDaoException e){
			logger.error(" There is a UsersDaoException occured whle querying the users from USERS table ");
			return 2;
		} catch (MonthlyPayrollDaoException e){
			logger.error(" There is a MonthlyPayrollDaoException while inserting the record for the monthlypayrol for the user " + userId + " for the month " + e.getMessage());
			return 2;
		}
		/* catch (BonusReconciliationDaoException e){
			e.printStackTrace();
			return 2;}*/
		
	
		return SalaryReconciliationModel.getInstance().generateSalaryReport(i);
	}


	public static void main(String[] args) throws Exception {
		new MonthlySalaryJob().execute(null);
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("24", "300000");
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			UsersDao dao = UsersDaoFactory.create(conn);
			MonthlyPayrollDao payrollDao = MonthlyPayrollDaoFactory.create(conn);
			SalaryReconciliationReportDao reportDao = SalaryReconciliationReportDaoFactory.create(conn);
			for (Map.Entry<String, String> entry : map.entrySet()){
				Users[] user = dao.findWhereEmpIdEquals(Integer.parseInt(entry.getKey()));
				System.out.println(entry.getKey() + "--" + user[0].getId());
				payrollDao.insert(new MonthlyPayroll(user[0].getId(), "Bonus", DesEncrypterDecrypter.getInstance().encrypt(entry.getValue()), "201309", 0));
				SalaryReconciliationReport[] report = reportDao.findByDynamicWhere("USER_ID=? AND SR_ID=?	", new Object[] { user[0].getId(), 4 });
				System.out.println("report value :" + report);
				report[0].setSalary((Double.parseDouble(report[0].getSalary()) + Double.parseDouble(entry.getValue())) + "");
				reportDao.update(report[0].createPk(), report[0]);
				System.out.println("done value :" + report);
			}
			conn.commit();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			ResourceManager.close(conn);
		}*/
	}

	private void deleteInCompleteRecords(int userId, String monthId) {
		MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create();
		try{
			MonthlyPayroll[] components = monthlyPayrollDao.findByDynamicWhere(" USERID = ? AND MONTHID = ? ", new Object[] { userId, monthId });
			for (MonthlyPayroll eachPayroll : components){
				monthlyPayrollDao.delete(new MonthlyPayrollPk(eachPayroll.getId()));
			}
		} catch (MonthlyPayrollDaoException e){
			logger.error(" There is a MonthlyPayrollDaoException while Deleting the incorrect records for the user " + userId + " for the month " + e.getMessage());
		}
	}

	/**
	 * Returns all the Allowances parts of the users payroll from the FBP.
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 * @throws SalaryDetailsDaoException 
	 * @throws FbpDetailsDaoException 
	 */
	public List<MonthlyPayroll> getAllowances(int userId, String monthId) throws SalaryDetailsDaoException, FbpDetailsDaoException {
		List<MonthlyPayroll> allAllowances = new ArrayList<MonthlyPayroll>();
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		String financialYear = FBPModel.getFinancialYear();
		try {
			FbpReq[] fbpReq = fbpReqDao.findByDynamicWhereMaxF(
					" USER_ID = ? AND (MONTH_ID = ? OR MONTH_ID = ? )AND STATUS = ? AND (SEQUENCE = 3 OR SEQUENCE = 0) ",
					new Object[] { userId, monthId, financialYear, "Processed" });

			FbpDetails[] fbpDetails = fbpDetailsDao.findWhereFbpIdEquals(fbpReq[0].getId());

			if (fbpDetails != null && fbpDetails.length > 0 && fbpDetails[0].getId() > 0
					&& fbpReq[0].getStatus().equalsIgnoreCase("Processed")) {

				for (FbpDetails eachFbpDetails : fbpDetails) {
					if (!eachFbpDetails.getUsedAmt().equalsIgnoreCase("NA")
							&& !eachFbpDetails.getUsedAmt().equalsIgnoreCase("UA")) {
						monthlyPayroll = new MonthlyPayroll();
						monthlyPayroll.setMonthid(monthId);
						monthlyPayroll.setUserid(userId);
						monthlyPayroll.setComponentType(1);
						monthlyPayroll.setComponent(eachFbpDetails.getFbp());
						if (fbpReq[0].getFrequent().equalsIgnoreCase("yearly")) {
							monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance()
									.encrypt(String.valueOf(Float.valueOf(eachFbpDetails.getUsedAmt()) / 12)));
						} else {
							monthlyPayroll.setAmount(
									DesEncrypterDecrypter.getInstance().encrypt(eachFbpDetails.getUsedAmt()));
						}
						allAllowances.add(monthlyPayroll);
					}
				}
			}

			else {
				SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
				SalaryDetails[] grossSalaries = salaryDetailsDao
						.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (5) ORDER BY SAL_ID ", new Object[] { userId });
				float sum = 0;
				if (grossSalaries == null || grossSalaries.length <= 0) {
					logger.error(" There is FbpReqDaoException while querying FBP_REQ for the user " + userId);
				} else {
					monthlyPayroll.setUserid(userId);
					monthlyPayroll.setComponent("OA");
					monthlyPayroll.setMonthid(monthId);
					monthlyPayroll.setComponentType(0);
					for (SalaryDetails eachDetail : grossSalaries) {
						monthlyPayroll.setAmount(eachDetail.getMonthly());
						allAllowances.add(monthlyPayroll);
					}
				}
			}
		} catch (FbpReqDaoException e) {
			logger.error(" There is FbpReqDaoException while querying FBP_REQ for the user " + userId);
			okFlag = false;
		}
		return allAllowances;
	}

	
	
	/**
	 * Returns all the Bonus parts of the users payroll from the BonusReconciliation.
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 * @throws SalaryDetailsDaoException 
	 */
	public List<MonthlyPayroll> getBonus(int userId, String monthId)  {
		List<MonthlyPayroll> allBonuses = new ArrayList<MonthlyPayroll>();
		MonthlyPayroll monthlyPayroll=new MonthlyPayroll();
		MonthlyPayroll monthly=new MonthlyPayroll();
//		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
//		SalaryDetails[] quaterely = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID= 9 ORDER BY SAL_ID ", new Object[] { userId });
//		SalaryDetails[] company = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID= 10 ORDER BY SAL_ID ", new Object[] { userId });
		BonusReconciliationReportDao bonusReportDao=BonusReconciliationReportDaoFactory.create();
		String month =findMonth(monthId);
		// for testing purpose kept userId=48; 
		try{
		BonusReconciliationReport[] bonusReport=bonusReportDao.findByDynamicWhere("USER_ID = ? AND MONTH= ? ",new Object[] { userId, month});
		for(BonusReconciliationReport bonus:bonusReport){
		if(month.equalsIgnoreCase("MARCH")||month.equalsIgnoreCase("JUNE")||month.equalsIgnoreCase("SEPTEMBER")||month.equalsIgnoreCase("DECEMBER")){
		        monthlyPayroll.setUserid(userId);
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponentType(3);
				monthlyPayroll.setComponent("D-VARIABLES-Quaterely Performance Linked Bonus(QPLB)");
				monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(Double.parseDouble(bonus.getqAmount())))); //qAmount=qBonus/4;
				allBonuses.add(monthlyPayroll);
				}
			if(month.equalsIgnoreCase("MARCH")){
			    monthly.setUserid(userId);
				monthly.setMonthid(monthId);
				monthly.setComponentType(3);
				monthly.setComponent("D-VARIABLES-Company Performance Linked Bonus(CPLB)");
				monthly.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(Double.parseDouble(bonus.getcAmount()))));
			allBonuses.add(monthly);
		}
	}
		}
	catch (BonusReconciliationReportDaoException e){
		logger.error("There is a TDSException that occured during the querying of the TDS table for the user " + userId + "in month " + monthId);
		okFlag = false;
	}
		
		return allBonuses;
	}
	
	
	
	public String findMonth(String monthId){
		String month="";
	String mon=	monthId.substring(monthId.length() - 2);
	if(mon.equalsIgnoreCase("03")) month= "March";
	else if(mon.equalsIgnoreCase("06"))month= "June";
	else if(mon.equalsIgnoreCase("09")) month= "September";
	else if(mon.equalsIgnoreCase("12")) month= "December";
	else if(mon.equalsIgnoreCase("01")) month= "January";
	else if(mon.equalsIgnoreCase("02"))month= "Febraury";
	else if(mon.equalsIgnoreCase("04")) month= "April";
	else if(mon.equalsIgnoreCase("05")) month= "May";
	else if(mon.equalsIgnoreCase("07"))month= "July";
	else if(mon.equalsIgnoreCase("08")) month= "August";
	else if(mon.equalsIgnoreCase("10")) month= "October";
	else if(mon.equalsIgnoreCase("11")) month= "November";
	
	return month;
	}
	
	
	/**
	 * Returns all the deductions of the users payroll from the TDS and calculating others.
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 * @throws FbpReqDaoException 
	 * @throws VoluntaryProvidentFundDaoException 
	 */
	public List<MonthlyPayroll> getDeductions(int userId, String monthId) throws FbpReqDaoException, VoluntaryProvidentFundDaoException {
		List<MonthlyPayroll> allDeductions = new ArrayList<MonthlyPayroll>();
		//ESIC
		 allDeductions.add(getEsic(userId,monthId));
		//PT
		allDeductions.add(getPTDeductions(userId, monthId));
		//TDS
		allDeductions.add(getTds(userId, monthId));
		//SODEXO
		allDeductions.add(getSodexoIfAny(userId, monthId));
		//Salary In Advance
		allDeductions.add(getSalaryInAdvance(userId, monthId));
		//PF
		allDeductions.add(getPfDetail(userId, monthId));
		//VPF
		allDeductions.add(getVpfDetail(userId, monthId));
		//Travel in Advance
		allDeductions.add(getTravelInAdvance(userId, monthId));
	
		return allDeductions;
	}

	/**
	 * Returns the monthlyPayroll record of the user for the Sodexo deductions if Any declared
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 */
	private MonthlyPayroll getSodexoIfAny(int userId, String monthId) {
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		String financialYear = FBPModel.getFinancialYear();
		try{
			FbpReq[] fbpReq = fbpReqDao.findByDynamicWhereMaxF(" USER_ID = ? AND (MONTH_ID = ? OR MONTH_ID = ? )AND STATUS = ? AND (SEQUENCE = 3 OR SEQUENCE = 0) ", new Object[] { userId, monthId, financialYear,"Processed" });
							if (fbpReq != null && fbpReq.length == 1){
				FbpDetails[] fbpDetails = fbpDetailsDao.findByDynamicWhere(" FBP_ID = ? AND FBP = ? ", new Object[] { fbpReq[0].getId(), "MV" });
				if (fbpDetails != null && fbpDetails.length == 1){
					monthlyPayroll.setMonthid(monthId);
					monthlyPayroll.setUserid(userId);
					monthlyPayroll.setComponentType(2);
					monthlyPayroll.setComponent("Sodexo Meal Vouchers");
					float socdexo = Float.parseFloat(fbpDetails[0].getUsedAmt().equals("NA") ? "0" : fbpDetails[0].getUsedAmt());
					if (fbpReq[0].getFrequent().toLowerCase().equals("yearly")) socdexo = socdexo / 12;
					monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(socdexo + ""));
				} else logger.error(" There is no/or more than one FBPDETAILS record for a particular user " + userId);
			} else logger.error(" There is no/or more than one FBPReq record for a particular user " + userId);
		} catch (FbpReqDaoException e){
			logger.error(" There is FbpReqDaoException while querying FBP_REQ for the user " + userId + "for the month" + monthId + e.getMessage());
			okFlag = false;
		} catch (FbpDetailsDaoException e){
			logger.error(" There is FbpDetailsDaoException while querying FBP_DETAILS for the user " + userId + "for the month" + monthId + e.getMessage());
			okFlag = false;
		}
		return monthlyPayroll;
	}

	/**
	 * Returns the monthlyPayroll record of the user for the TDS deductions
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 */
	private MonthlyPayroll getTds(int userId, String monthId) {
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		TdsDao tdsDao = TdsDaoFactory.create();
		try{
			Tds[] tds = tdsDao.findByDynamicWhere(" USERID = ? AND MONTH_ID = ? ", new Object[] { userId, monthId });
			if (tds != null && tds.length == 1){
				monthlyPayroll.setUserid(userId);
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponent("Tax Deduction at Source (TDS)");
				monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt("0"));
				//		monthlyPayroll.setAmount(" ");
				monthlyPayroll.setComponentType(2);
				// Updating the TDS Record to freeze the status of the Current month TDS Record
				tds[0].setStatus(1);
				tdsDao.update(tds[0].createPk(), tds[0]);
			} else
			{
				monthlyPayroll.setUserid(userId);
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponent("Tax Deduction at Source (TDS)");
				monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt("0"));
		
				monthlyPayroll.setComponentType(2);
				Tds tdso = new Tds();
				tdso.setMonthId(monthId);
				tdso.setUserid(userId);
				tdso.setAmount("0");
				tdso.setStatus(1);
				
				// inserting TDS row with zero value and status 1 
				tdsDao.insert(tdso);
				
	
				
			}
		} catch (TdsDaoException e){
			logger.error("There is a TDSException that occured during the querying of the TDS table for the user " + userId + "in month " + monthId);
			okFlag = false;
		}
		return monthlyPayroll;
	}

	/**
	 * Returns the monthlyPayroll record of the user for the PT deductions
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 */
	public MonthlyPayroll getPTDeductions(int userId, String monthId) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		try{
			SalaryDetails[] grossSalaries = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (1,2,3,4,5) ORDER BY SAL_ID ", new Object[] { userId });
			float sum = 0;
			if (grossSalaries != null && grossSalaries.length == 5){
				monthlyPayroll.setUserid(userId);
				monthlyPayroll.setComponent("Professional Tax");  
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponentType(2);
				for (SalaryDetails eachDetail : grossSalaries){
					sum = sum + Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getMonthly()));
				}
				monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(getPTDeductions(sum))));
			}
		} catch (SalaryDetailsDaoException e){
			logger.error("There is a No correct no of records available for calculating the PT of the user " + userId);
			okFlag = false;
		}
		return monthlyPayroll;
	}
	
	public MonthlyPayroll getEsic(int userId, String monthId) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		try{
			SalaryDetails[] grossSalaries = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (1,2,3,4,5) ORDER BY SAL_ID ", new Object[] { userId });
			float sum = 0;
			if (grossSalaries != null && grossSalaries.length == 5){
				monthlyPayroll.setUserid(userId);
				monthlyPayroll.setComponent("Employees' State Insurance Corporation");  
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponentType(2);
				for (SalaryDetails eachDetail : grossSalaries)
				{
					if(eachDetail.getEsic().equalsIgnoreCase("no") ||eachDetail.getEsic().equalsIgnoreCase("NO") || (eachDetail.getEsic().isEmpty()))
			       {
						monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(0.00)));
			       }
					else
					{	sum = sum + Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getMonthly()));
						monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(getEsicDeductions(sum))));
					}
				   
				}
		}} catch (SalaryDetailsDaoException e){
			logger.error("There is a No correct no of records available for calculating the PT of the user " + userId);
			okFlag = false;
		}
		return monthlyPayroll;
	}
	

	/**
	 * Returns the monthlyPayroll record of the user for the PT deductions
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 */
	public MonthlyPayroll getSalaryInAdvance(int userId, String monthId) {
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		try{
			String[] sal = FinanceInfoDaoFactory.create().getSalaryInAdv(userId);
			if (sal != null && sal[0] != null && sal[1] != null && sal[2] != null && sal[6].equalsIgnoreCase("SALARY")){
				
				monthlyPayroll.setUserid(userId);
				monthlyPayroll.setComponent("Salary In Advance");
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponentType(2);
				monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(sal[2]));
			}
		} catch (Exception e){
			logger.error("There is a No correct no of records available for calculating the PT of the user " + userId);
			okFlag = false;
		}
		return monthlyPayroll;
	}
	
	
	public MonthlyPayroll getTravelInAdvance(int userId, String monthId) {
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		try{
			String[] travelfixed= FinanceInfoDaoFactory.create().getTravelInAdv(userId);
			if (travelfixed != null && travelfixed[0] != null && travelfixed[1] != null && travelfixed[2] != null && travelfixed[6].equalsIgnoreCase("SALARY")){
				
				monthlyPayroll.setUserid(userId);
				monthlyPayroll.setComponent("Travel In Advance");
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponentType(2);
				monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(travelfixed[2]));
			}
		} catch (Exception e){
			logger.error("There is a No correct no of records available for calculating the PT of the user " + userId);
			okFlag = false;
		}
		return monthlyPayroll;
	}

	/**
	 * Returns the professional tax and esic value for record of the user for the PF deductions
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 */
	public float getPTDeductions(float salary) {
		
		if(14999>salary)
		{
			return (float) 0.0;
		}
		else
		{	return (float) 200.00;
			
		}
		
		
		
		
		/*if (sum <= 3000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(0)));
		} else if (sum > 3000 && sum <= 5000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(30)));
		} else if (sum > 5000 && sum <= 8000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(60)));
		} else*/
	/*	if (salary < 10000){
			return (float) (0 + (salary * 0.0175));
		} else if (salary >= 10000 && salary < 15000){
			return (float) (150 + (salary * 0.0175));
		} else if (salary >= 15000){ return 200f; }
		return 0f;*/
	}

	public float getEsicDeductions(float salary) {
		if(!(salary == 0))
		{
			
		 double cal = 1.75*salary/100;
	      return (float) cal;
			
		}
		return salary;
		
		
		
		
		/*if (sum <= 3000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(0)));
		} else if (sum > 3000 && sum <= 5000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(30)));
		} else if (sum > 5000 && sum <= 8000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(60)));
		} else*/
	/*	if (salary < 10000){
			return (float) (0 + (salary * 0.0175));
		} else if (salary >= 10000 && salary < 15000){
			return (float) (150 + (salary * 0.0175));
		} else if (salary >= 15000){ return 200f; }
		return 0f;*/
	}
	
	/**
	 * Returns the monthlyPayroll record of the user for the PF deductions
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 * @throws FbpReqDaoException 
	 */
	private MonthlyPayroll getPfDetail(int userId, String monthId) throws FbpReqDaoException {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		try {

			SalaryDetails[] pFDetail = salaryDetailsDao
					.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (7) ORDER BY SAL_ID ", new Object[] { userId });

			for (SalaryDetails salaryDetails : pFDetail) {

				if (salaryDetails == null && pFDetail.length < 0) {

					logger.error("There is no pf component  " + userId);

				} else {
					monthlyPayroll.setUserid(userId);

					monthlyPayroll.setMonthid(monthId);

					monthlyPayroll.setComponent("Employer Contribution to the Provident Funds");

					monthlyPayroll.setAmount(pFDetail[0].getMonthly());

					monthlyPayroll.setComponentType(2);
				}

			}

		} catch (SalaryDetailsDaoException e) {
			logger.error(
					"There is a Dao Exception while getting the PF records from the SALARY_DETAILS table for the user "
							+ userId + e.getMessage());
			okFlag = false;
		}

		return monthlyPayroll;
	}

	private MonthlyPayroll getVpfDetail(int userId, String monthId) throws VoluntaryProvidentFundDaoException {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		VoluntaryProvidentFundDao vpfDao = VoluntaryProvidentFundDaoFactory.create();
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		try {

			SalaryDetails[] pFDetail = salaryDetailsDao
					.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (7) ORDER BY SAL_ID ", new Object[] { userId });
			{
				VoluntaryProvidentFund[] vpf = vpfDao.findByVpf("SELECT * FROM VPF_REQ where USER_ID = ? ORDER BY ID DESC LIMIT 1", new Object[]{(userId)} );
				if (vpf == null || vpf.length <= 0) {
					logger.error("There is no  VPF record for the user" + userId);
				}
				
				for (VoluntaryProvidentFund voluntaryProvidentFund : vpf) {

					if (voluntaryProvidentFund.getVpf() == null||voluntaryProvidentFund.getVpf().equals("NO")||voluntaryProvidentFund.getVpf().equals("no")||voluntaryProvidentFund.getVpf().equals("")) {
						logger.error("You have not opted for VPF" + userId);
					}

					else {
						monthlyPayroll.setUserid(userId);
						monthlyPayroll.setMonthid(monthId);
						monthlyPayroll.setComponent("Voluntary Provident Fund");
						monthlyPayroll.setAmount(pFDetail[0].getMonthly());
						monthlyPayroll.setComponentType(2);
					}
				}

			}

		}
		 catch (SalaryDetailsDaoException e){
			logger.error("There is a Dao Exception while getting the PF records from the SALARY_DETAILS table for the user " + userId + e.getMessage());
			okFlag = false;
		}
		return monthlyPayroll;
	}


	/**
	 * Returns the basic components of your salary from SALARYDETAILS
	 * 
	 * @param userId
	 * @param monthId
	 * @return
	 */
	public List<MonthlyPayroll> getGrossSalaries(int userId, String monthId) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		List<MonthlyPayroll> allGrossSalaries = new ArrayList<MonthlyPayroll>();
		try{
			SalaryDetails[] grossSalaries = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (1,2,3,4) ORDER BY SAL_ID ", new Object[] { userId });
			if (grossSalaries != null && grossSalaries.length > 0){
				for (SalaryDetails eachGrossSalary : grossSalaries){
					MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
					monthlyPayroll.setUserid(userId);
					monthlyPayroll.setMonthid(monthId);
					monthlyPayroll.setComponentType(0);
					monthlyPayroll.setComponent(eachGrossSalary.getFieldLabel());
					//monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(Math.round(Double.parseDouble((DesEncrypterDecrypter.getInstance().decrypt(eachGrossSalary.getMonthly())))) + ".00"));
					monthlyPayroll.setAmount(eachGrossSalary.getMonthly());
					allGrossSalaries.add(monthlyPayroll);
				}
			} else logger.error(" There is no records in the SALARY_DETAILS Table for the user");
		} catch (SalaryDetailsDaoException ex){
			okFlag = false;
			logger.error("There is a Dao Exception while getting the records from the SALARY_DETAILS table" + ex.getMessage());
		}
		return allGrossSalaries;
	}
	
	public List<MonthlyPayroll> getRetentionBonus(int userId, String monthId) {
	
		List<MonthlyPayroll> retentionBonus = new ArrayList<MonthlyPayroll>();
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		RetentionBonusDao retenDao=RetentionBonusDaoFactory.create();
		String retenBonus=null;
		SalaryDetails[] salary=null;
		RetentionBonus[] rBonus=null;
//		SalaryInfo[] info=null;
		int installments=0;
		float amount=0;
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll(); 
	    try {
	    	salary = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID=17 ORDER BY SAL_ID ", new Object[] { userId });
		if(salary!=null && salary.length>0){
			retenBonus=salary[0].getAnnual();
			amount=Float.parseFloat(DesEncrypterDecrypter.getInstance().decrypt(retenBonus));
			if(amount>0){
	    	
			rBonus=retenDao.findWhereUserIdEquals(userId);
			if(rBonus!=null && rBonus.length>0){
	    		for(RetentionBonus i:rBonus){
	    		installments=(i.getRetentionInstallments()>0)?i.getRetentionInstallments():0;
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(i.getRetentionStartDate());
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(new Date());
			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)+1;
		// setting based on the Installments selected
	    	 monthlyPayroll.setUserid(userId);
			 monthlyPayroll.setMonthid(monthId);
			 monthlyPayroll.setComponentType(1);
			 monthlyPayroll.setComponent("D-VARIABLES-Retention Bonus");
			 switch (installments) {
			case 4:
				if (diffMonth>0 && diffMonth%12==0){ 
					monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(Math.round(amount)+""));
					retentionBonus.add(monthlyPayroll);
		             }
				           break;
			case 3:
				if(diffMonth>0 && diffMonth%6==0){
					monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(Math.round(amount/2)+""));
					retentionBonus.add(monthlyPayroll);
	            }
			         break;
			case 2:
				if (diffMonth>0 && diffMonth%3==0){
					 monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(Math.round(amount/4)+""));
					retentionBonus.add(monthlyPayroll); 
		        }
			             break;
		   case 1:
				if (diffMonth>1){
					monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(Math.round(amount/12)+""));
					retentionBonus.add(monthlyPayroll);
		
	}
				        break;
			default:
				       break;
	}
	    		
	    		}				
		}
		}
	    }
	    } catch (RetentionBonusDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		 } catch (SalaryDetailsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retentionBonus;
	}
	
		
}