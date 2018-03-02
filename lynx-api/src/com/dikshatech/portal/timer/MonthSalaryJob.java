package com.dikshatech.portal.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionException;

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.dao.FbpDetailsDao;
import com.dikshatech.portal.dao.FbpReqDao;
import com.dikshatech.portal.dao.MonthlyPayrollDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.TdsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dao.VoluntaryProvidentFundDao;
import com.dikshatech.portal.dto.FbpDetails;
import com.dikshatech.portal.dto.FbpReq;
import com.dikshatech.portal.dto.MonthlyPayroll;
import com.dikshatech.portal.dto.MonthlyPayrollPk;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.Tds;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.VoluntaryProvidentFund;
import com.dikshatech.portal.exceptions.FbpDetailsDaoException;
import com.dikshatech.portal.exceptions.FbpReqDaoException;
import com.dikshatech.portal.exceptions.MonthlyPayrollDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.TdsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.exceptions.VoluntaryProvidentFundDaoException;
import com.dikshatech.portal.factory.FbpDetailsDaoFactory;
import com.dikshatech.portal.factory.FbpReqDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.MonthlyPayrollDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.TdsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.factory.VoluntaryProvidentFundDaoFactory;
import com.dikshatech.portal.models.FBPModel;
import com.dikshatech.portal.models.SalaryReconciliationModel;




public class MonthSalaryJob {
	
	private boolean	okFlag	= true;
	private final Logger				logger	= LoggerUtil.getLogger(MonthSalaryJob.class);
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
	
	public int generateReportAndNotify() throws JobExecutionException, FbpReqDaoException, VoluntaryProvidentFundDaoException, SalaryDetailsDaoException {
		return generateReportAndNotify(0);
	}
	public int  generateReportAndNotify( int userid) throws JobExecutionException, FbpReqDaoException, VoluntaryProvidentFundDaoException, SalaryDetailsDaoException {
		logger.info("automatic generation of salary triggered");
		MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		int userId = 0;
		try{
			String monthId = FBPModel.getMonthId();
			int rowCount = (int) JDBCUtiility.getInstance().getRowCount("FROM MONTHLY_PAYROLL WHERE MONTHID=?", new Object[] { monthId });
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
				
			} catch (MonthlyPayrollDaoException e){
				logger.error(" There is a MonthlyPayrollDaoException while inserting the record for the monthlypayrol for the user " + userId + " for the month " + e.getMessage());
				
			}
			/* catch (BonusReconciliationDaoException e){
				e.printStackTrace();
				return 2;}*/
			
		
			return SalaryReconciliationModel.getInstance().generateSalaryReportauto(userid);
	}
	
	
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
	
	public List<MonthlyPayroll> getAllowances(int userId, String monthId) throws SalaryDetailsDaoException {
		List<MonthlyPayroll> allAllowances = new ArrayList<MonthlyPayroll>();
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		String financialYear = FBPModel.getFinancialYear();
		try{
			FbpReq[] fbpReq = fbpReqDao.findByDynamicWhereMaxF(" USER_ID = ? AND (MONTH_ID = ? OR MONTH_ID = ? )AND STATUS = ? AND (SEQUENCE = 3 OR SEQUENCE = 0) ", new Object[] { userId, monthId, financialYear,"Processed" });
			
				FbpDetails[] fbpDetails = fbpDetailsDao.findWhereFbpIdEquals(fbpReq[0].getId());
				
				if (fbpDetails != null && fbpDetails.length > 0 && fbpDetails[0].getId()>0 && fbpReq[0].getStatus().equalsIgnoreCase("Processed")){
				
					
					
					for (FbpDetails eachFbpDetails : fbpDetails){
						if (!eachFbpDetails.getUsedAmt().equalsIgnoreCase("NA") && !eachFbpDetails.getUsedAmt().equalsIgnoreCase("UA")){
							monthlyPayroll = new MonthlyPayroll();
							monthlyPayroll.setMonthid(monthId);
							monthlyPayroll.setUserid(userId);
							monthlyPayroll.setComponentType(1);
							monthlyPayroll.setComponent(eachFbpDetails.getFbp());
							if (fbpReq[0].getFrequent().equalsIgnoreCase("yearly")){
								monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(Float.valueOf(eachFbpDetails.getUsedAmt()) / 12)));
							} else{
								monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(eachFbpDetails.getUsedAmt()));
							}
							allAllowances.add(monthlyPayroll);
						}
					}
				}
		
			else
			{	SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
				SalaryDetails[] grossSalaries = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (5) ORDER BY SAL_ID ", new Object[] { userId });
				float sum = 0;
				if(grossSalaries == null|| grossSalaries.length<=0)
				{
					logger.error(" There is FbpReqDaoException while querying FBP_REQ for the user " + userId);
				}
				else {
					monthlyPayroll.setUserid(userId);
					monthlyPayroll.setComponent("OA");  
					monthlyPayroll.setMonthid(monthId);
					monthlyPayroll.setComponentType(0);
					for (SalaryDetails eachDetail : grossSalaries)
					{
						monthlyPayroll.setAmount(eachDetail.getMonthly());
						allAllowances.add(monthlyPayroll);
						}
			}
				}} catch (FbpReqDaoException e){
			logger.error(" There is FbpReqDaoException while querying FBP_REQ for the user " + userId);
			okFlag = false;
		} catch (FbpDetailsDaoException e){
			logger.error(" There is FbpDetailsDaoException while querying FBP_REQ for the user " + userId);
			okFlag = false;
		}
		return allAllowances;
	}
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


	private MonthlyPayroll getPfDetail(int userId, String monthId) throws FbpReqDaoException {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
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

	private MonthlyPayroll getTds(int userId, String monthId) {
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		TdsDao tdsDao = TdsDaoFactory.create();
		try {
			Tds[] tds = tdsDao.findByDynamicWhere(" USERID = ? AND MONTH_ID = ? ", new Object[] { userId, monthId });
			if (tds != null && tds.length == 1) {
				monthlyPayroll.setUserid(userId);
				monthlyPayroll.setMonthid(monthId);
				monthlyPayroll.setComponent("Tax Deduction at Source (TDS)");
				monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt("0"));
				// monthlyPayroll.setAmount(" ");
				monthlyPayroll.setComponentType(2);
				// Updating the TDS Record to freeze the status of the Current
				// month TDS Record
				tds[0].setStatus(1);
				tdsDao.update(tds[0].createPk(), tds[0]);
			} else {
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
		} catch (TdsDaoException e) {
			logger.error("There is a TDSException that occured during the querying of the TDS table for the user "
					+ userId + "in month " + monthId);
			okFlag = false;
		}
		return monthlyPayroll;
	}
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
	public MonthlyPayroll getSalaryInAdvance(int userId, String monthId) {
		MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
		try{
			String[] sal = FinanceInfoDaoFactory.create().getSalaryInAdv(userId);
			if (sal != null && sal[0] != null && sal[1] != null && sal[2] != null){
				
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
	public float getPTDeductions(float salary) {
		
		if(salary>14999)
		{
			return (float) 200.00;
		}
		else
		{
			return 0.00f;
		}
		/*if (sum <= 3000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(0)));
		} else if (sum > 3000 && sum <= 5000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(30)));
		} else if (sum > 5000 && sum <= 8000){
		monthlyPayroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(60)));
		} else*/
		/*if (salary < 10000){
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
					if(eachDetail.getEsic().equalsIgnoreCase("no") ||eachDetail.getEsic().equalsIgnoreCase("NO")|| (eachDetail.getEsic().isEmpty()))
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
		;
		return monthlyPayroll;
	}
	
	
}



