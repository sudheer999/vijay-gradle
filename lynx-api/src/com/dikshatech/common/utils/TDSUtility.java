package com.dikshatech.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dikshatech.beans.FbpComponent;
import com.dikshatech.portal.dao.FbpDetailsDao;
import com.dikshatech.portal.dao.FbpReqDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.TaxBenefitAcceptDao;
import com.dikshatech.portal.dao.TdsDao;
import com.dikshatech.portal.dto.FbpDetails;
import com.dikshatech.portal.dto.FbpReq;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.TaxBenefitAccept;
import com.dikshatech.portal.dto.TaxBenefitDeclaration;
import com.dikshatech.portal.dto.Tds;
import com.dikshatech.portal.exceptions.FbpDetailsDaoException;
import com.dikshatech.portal.exceptions.FbpReqDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.TaxBenefitAcceptDaoException;
import com.dikshatech.portal.exceptions.TaxBenefitDaoException;
import com.dikshatech.portal.exceptions.TdsDaoException;
import com.dikshatech.portal.factory.FbpDetailsDaoFactory;
import com.dikshatech.portal.factory.FbpReqDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.TaxBenefitAcceptDaoFactory;
import com.dikshatech.portal.factory.TdsDaoFactory;
import com.dikshatech.portal.models.FBPModel;
import com.dikshatech.portal.models.TaxBenefitDeclarationModel;
import com.dikshatech.portal.timer.MonthlySalaryJob;

/**
 * @author praneeth.r
 *         @ This class implements the Calculation according to details in the Tax_Computation.xls file.
 *         This calculates the various sources for the taxable income and reduce the exemption amount and return the net taxable income
 */
public class TDSUtility {

	private static Logger				logger								= LoggerUtil.getLogger(TDSUtility.class);
	private final String				BENEFIT_CATEGORY_US10				= " Exemption U/S 10(13A)";
	//	private final String BENEFIT_CATEGORY_US80G = "Deduction U/S 80G / 80GG /80GGA / 80GGC";
	private final String				BENEFIT_CATEGORY_US24				= "Deduction U/S 24";
	private final String				RENT_PAID							= "The amount of exemption in respect of house rent allowance(HRA) received by an employee";
	private final String				HOUSE_PROPERTY						= "Income from Rent on building / property / Machinery / Plant or furniture";
	//	private final String OTHER_SOURCES ="Income from other sources";
	private final String				CATEGORY_OTHER_SOURCES_OF_INCOME	= "Income from other sources";
	private final String				BENEFIT_CATEGORY_US80C				= "Deduction U/S 80C";
	private final String				BENEFIT_CATEGORY_US80CCC			= "Deduction U/S 80CCC";
	private final String				BENEFIT_CATEGORY_US80CCD			= "Deduction U/S 80CCD / 80CCF / 80CCG";
	//	private final String BENEFIT_CATEGORY_OTHER_DEDUCTIONS = "Others Deduction";
	//private final String BENEFIT_CATEGORY_VIA = "DEDUCTION UNDER CHAPTER - VI A";
	private static Map<String, String>	map									= null;
	static{
		map = new HashMap<String, String>();
		map.put("01", "January");
		map.put("02", "February");
		map.put("03", "March");
		map.put("04", "April");
		map.put("05", "May");
		map.put("06", "June");
		map.put("07", "July");
		map.put("08", "August");
		map.put("09", "September");
		map.put("10", "October");
		map.put("11", "November");
		map.put("12", "December");
	}

	//	public TaxCalculator receiveCalculator(int userId){
	//		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
	//		LevelsDao levelDao = LevelsDaoFactory.create();
	//		ProfileInfo profileInfo = null;
	//		Levels[] level = null;
	//		TaxCalculator taxs = new TaxCalculator();
	//		taxs.setUserId(userId);
	//		try {
	//			profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
	//			taxs.setName(profileInfo != null ? (profileInfo.getFirstName() + " " + profileInfo.getLastName()) : "" );
	//			level = levelDao.findByDynamicWhere("  ID in (SELECT LEVEL_ID FROM USERS WHERE ID = ? ) ", new Object[]{userId});
	//			taxs.setLevel(level != null ? (level[0].getLabel() + " " + level[0].getDesignation()) : " " );
	//			TaxBenefitItem taxBenefitItem = null;
	//			//Income from Salary Item
	//			taxBenefitItem = new TaxBenefitItem();
	//			taxBenefitItem.setName(" INCOME FROM SALARY ");
	//			taxBenefitItem.setTaxable(true);
	//			taxBenefitItem.setEditable(false);
	//			taxBenefitItem.setAmount(getIncomeFromSalary(userId));
	//			taxs.getBenefitsLists().add(taxBenefitItem);
	//			
	//			// Conveyance amount 
	//			taxBenefitItem = new TaxBenefitItem();
	//			taxBenefitItem.setName(" Conveyance Allowance ");
	//			taxBenefitItem.setEditable(false);
	//			taxBenefitItem.setTaxable(false);
	//			taxBenefitItem.setAmount(9600);
	//			
	//			
	//			
	//			
	//		} catch(ProfileInfoDaoException e) {
	//			logger.error("There is a ProfileDaoException Occured while getting the records for the user " + userId);
	//		} catch(LevelsDaoException e) {
	//			logger.error("There is a LevelsDaoException Occured while getting the records for the user " + userId);
	//		} 
	//
	//
	//		return taxs;
	//	}
	public List<Tds> getallmonthsTds(int userId,String Fyear ) {//2016-2017
		TdsDao tdsDao = TdsDaoFactory.create();
		Tds[] tds = null;
		String[] parts = Fyear.split("-");
		String year = parts[1]; //2017
		if(year.equals(PortalUtility.getCurrentyear()+"")){
			try{
				
				tds = tdsDao.findByDynamicWhereTds(" M.USERID = ? AND MONTH_ID BETWEEN ? AND ? AND M.COMPONENT = 'TDS'", new Object[] { userId, getStartingMonthtds(),getEndingMonthtds()});
				
				
				
				if (tds != null && tds.length > 0){
					
					for (Tds eachTds : tds){
						/*if(eachTds.getCompleted()>0&&eachTds.getCompleted()==19)
						{*/
						if(eachTds.getId()>0)
						{
							String amount = eachTds.getAmount();
							eachTds.setAmount(amount);
							eachTds.setStatus(1);
						}
						else
						{
							eachTds.setAmount("0");
							eachTds.setStatus(0);
						}
			
			
						String monthId = eachTds.getMonthId();
						eachTds.setMonthId(map.get(monthId.substring(4, 6)) + " " + monthId.substring(0, 4));
					
				}
		}}
			catch (TdsDaoException e){
				logger.error("Ther is tdsDao exception occured while retrieving the tds records for the user " + userId + e.getMessage());
			}
		} 
		else{
			try{
				
			
				        tds = tdsDao.findByDynamicWhereTds(" M.USERID = ? AND MONTH_ID BETWEEN ? AND ?  AND M.COMPONENT = 'TDS' ", new Object[] { userId, getStartingMonth(), getEndingMonth() });
			
	
				//		tds = tdsDao.findByDynamicWhere(" USERID = ? AND MONTH_ID BETWEEN ? AND ? ", new Object[] { userId, getStartingMonth(), getEndingMonth() });
		            if (tds != null && tds.length > 0){
				
					
					        for (Tds eachTds : tds){
						/*if(eachTds.getCompleted()>0&&eachTds.getCompleted()==19)
						{*/
						          if(eachTds.getId()>0)
						             {
							
							
							String amount = eachTds.getAmount();
							eachTds.setAmount(amount);
							eachTds.setStatus(1);
						             }
						          else
						             {
							eachTds.setAmount("0");
							eachTds.setStatus(0);
						             }
			
						    String monthId = eachTds.getMonthId();
						    eachTds.setMonthId(map.get(monthId.substring(4, 6)) + " " + monthId.substring(0, 4));
					}
				
		}}
			catch (TdsDaoException e){
				logger.error("Ther is tdsDao exception occured while retrieving the tds records for the user " + userId + e.getMessage());
			}
		}
		return Arrays.asList(tds);
	}

	public float receiveCalculatedTax(String gender, float taxableAmt) {
		return getTds(gender, taxableAmt);
	}

	public List<Tds> getAllTds(int userId, String financialYear) {
		TdsDao tdsDao = TdsDaoFactory.create();
		Tds[] tds = null;
		try{
			tds = tdsDao.findByDynamicWhere(" USERID = ? AND MONTH_ID BETWEEN ? AND ? ", new Object[] { userId, financialYear.substring(0, 4), financialYear.substring(5, financialYear.length()) });
		} catch (TdsDaoException e){
			logger.error(" There occurred a TdsDaoException while querying the Tds Table for the User " + userId);
		}
		return Arrays.asList(tds);
	}

	/**
	 * This method recalculate the TDS using the send parameters and database and returns for viewing
	 * 
	 * @param userId
	 * @throws TaxBenefitDaoException
	 */
	public List<Tds> reCalculate(int userId, String fbpBenefitsDetails, String taxBenefit) throws TaxBenefitDaoException {
		TdsDao tdsDao = TdsDaoFactory.create();
		List<Tds> result = new ArrayList<Tds>();
		try{
			Tds[] tdsFreezed = tdsDao.findByDynamicWhere(" USERID = ? AND STATUS = 1 AND MONTH_ID BETWEEN ? AND ? ", new Object[] { userId, getStartingMonth(), getEndingMonth() });
			int lenFreezed = tdsFreezed.length;
			float sumFreezed = 0.0f;
			for (Tds eachFreezed : tdsFreezed)
				sumFreezed += Float.valueOf(eachFreezed.getAmount());
			Tds[] tdsToBeUpdated = tdsDao.findByDynamicWhere(" USERID = ? AND STATUS = 0 AND MONTH_ID BETWEEN ? AND ? ", new Object[] { userId, getStartingMonth(), getEndingMonth() });
			if (tdsToBeUpdated != null && tdsToBeUpdated.length > 0){
				float taxableIncome = getActualTaxableAmt(userId, fbpBenefitsDetails, taxBenefit);
				String amount = String.valueOf((getTds(getGender(userId), taxableIncome) - sumFreezed) / (12 - lenFreezed));
				for (Tds eachTds : tdsToBeUpdated){
					eachTds.setAmount(amount);
					//tdsDao.update(eachTds.createPk(), eachTds);
				}
			}
			for (Tds element : tdsToBeUpdated)
				result.add(element);
		} catch (TdsDaoException e){
			logger.error("There is a TdsDaoException occured while getting the Tds Records for the user " + userId + " " + e.getMessage());
		}
		return (result);
	}

	/**
	 * This method recalculate the TDS and updates the same for the remaining records
	 * 
	 * @param userId
	 */
	public void reCalculate(int userId) {
		TdsDao tdsDao = TdsDaoFactory.create();
		try{
			Tds[] tdsFreezed = tdsDao.findByDynamicWhere(" USERID = ? AND STATUS = 1 AND MONTH_ID BETWEEN ? AND ? ", new Object[] { userId, getStartingMonth(), getEndingMonth() });
			int lenFreezed = tdsFreezed.length;
			float sumFreezed = 0.0f;
			for (Tds eachFreezed : tdsFreezed)
				sumFreezed += Float.valueOf(eachFreezed.getAmount());
			Tds[] tdsToBeUpdated = tdsDao.findByDynamicWhere(" USERID = ? AND STATUS = 0 AND MONTH_ID BETWEEN ? AND ? ", new Object[] { userId, getStartingMonth(), getEndingMonth() });
			if (tdsToBeUpdated != null && tdsToBeUpdated.length > 0){
				float taxableAmount = getActualTaxableAmt(userId);
				float tdsAmount = getTds(getGender(userId), taxableAmount);
				float qplb = getTds(getGender(userId), taxableAmount + getQPLBAmount(userId)) - tdsAmount;
				float cplb = getTds(getGender(userId), taxableAmount + getCPLBAmount(userId)) - tdsAmount;
				float amount = (tdsAmount - sumFreezed) / (12 - lenFreezed);
				for (Tds eachTds : tdsToBeUpdated){
					if (eachTds.getMonthId().endsWith("03")) eachTds.setAmount((amount + (qplb / 4) + cplb) + "");
					else if (eachTds.getMonthId().endsWith("06") || eachTds.getMonthId().endsWith("09") || eachTds.getMonthId().endsWith("12")) eachTds.setAmount((amount + (qplb / 4)) + "");
					else eachTds.setAmount(amount + "");
					tdsDao.update(eachTds.createPk(), eachTds);
				}
			}
		} catch (TdsDaoException e){
			logger.error("There is a TdsDaoException occured while getting the Tds Records for the user " + userId + " " + e.getMessage());
		}
	}

	private float getCPLBAmount(int userId) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		try{
			SalaryDetails[] salaryDetails = salaryDetailsDao.findByDynamicWhere(" SAL_ID IN (10) AND USER_ID = ?", new Object[] { userId });
			if (salaryDetails != null && salaryDetails.length > 0){
				return Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(salaryDetails[0].getAnnual()));
			} else{
				logger.error("There is no records found for the given user in the table for the user " + userId);
			}
		} catch (SalaryDetailsDaoException e){
			logger.error("There is a SalaryDetailsDaoException occured while querying the IncomeFromSalary from the SalaryDetails table for the user " + userId + " " + e.getMessage());
		}
		return 0.0f;
	}

	private float getQPLBAmount(int userId) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		try{
			SalaryDetails[] salaryDetails = salaryDetailsDao.findByDynamicWhere(" SAL_ID IN (9) AND USER_ID = ?", new Object[] { userId });
			if (salaryDetails != null && salaryDetails.length > 0){
				return Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(salaryDetails[0].getAnnual()));
			} else{
				logger.error("There is no records found for the given user in the table for the user " + userId);
			}
		} catch (SalaryDetailsDaoException e){
			logger.error("There is a SalaryDetailsDaoException occured while querying the IncomeFromSalary from the SalaryDetails table for the user " + userId + " " + e.getMessage());
		}
		return 0.0f;
	}

	/**
	 * This method save the TDS to TDS table for the all the months depending on the taxable amount calculated...
	 * 
	 * @param userId
	 */
	public void saveTds(int userId) {
		TdsDao tdsDao = TdsDaoFactory.create();
		float taxableAmount = getActualTaxableAmt(userId);
		float tdsAmount = getTds(getGender(userId), taxableAmount);
		float qplb = getTds(getGender(userId), taxableAmount + getQPLBAmount(userId)) - tdsAmount;
		float cplb = getTds(getGender(userId), taxableAmount + getCPLBAmount(userId)) - tdsAmount;
		float amount = tdsAmount / 12;
		String monthId = getStartingMonth();
		Tds tds = new Tds();
		tds.setUserid(userId);
		tds.setStatus(0);
		//tds.setAmount(String.valueOf(tdsAmount / 12));
		for (int i = 0; i < 12; i++){
			tds.setMonthId(monthId);
			try{
				tds.setId(0);
				if (tds.getMonthId().endsWith("03")) tds.setAmount((amount + (qplb / 4) + cplb) + "");
				else if (tds.getMonthId().endsWith("06") || tds.getMonthId().endsWith("09") || tds.getMonthId().endsWith("12")) tds.setAmount((amount + (qplb / 4)) + "");
				else tds.setAmount(amount + "");
				tdsDao.insert(tds);
			} catch (TdsDaoException e){
				logger.error("There is a TdsDaoException occured while getting the Tds Records for the user " + userId + " " + e.getMessage());
			}
			monthId = getNextMonth(monthId);
		}
	}

	public static String getNextMonth(String currentMonth) {
		int month = Integer.valueOf(currentMonth.substring(currentMonth.length() - 2, currentMonth.length()));
		int year = Integer.valueOf(currentMonth.substring(0, 4));
		if (month == 12){
			return String.valueOf(year + 1) + String.valueOf("01");
		} else{
			return String.valueOf(year) + String.valueOf(++month < 10 ? "0" + month : month);
		}
	}
	
	public static String getStartingMonthtds() {
		Calendar cal = Calendar.getInstance();
		String month = "04";
		return (String.valueOf(cal.get(Calendar.YEAR) -1) + month);
	}

	public static String getEndingMonthtds() {
		Calendar cal = Calendar.getInstance();
		String month = "03";
		return (String.valueOf(cal.get(Calendar.YEAR)) + month);
	}
	
	

	public static String getStartingMonth() {
		Calendar cal = Calendar.getInstance();
		String month = "04";
		return (String.valueOf(cal.get(Calendar.YEAR)) + month);
	}

	public static String getEndingMonth() {
		Calendar cal = Calendar.getInstance();
		String month = "03";
		return (String.valueOf(cal.get(Calendar.YEAR) + 1) + month);
	}

	/**
	 * This method takes the parameters of gender and taxable income and return the tax amount along with cess@3 % amount;
	 * Formula used is
	 * =ROUND(IF(K4="M",IF(H69<=200000,0,IF(H69<=500000,((H69-200000)*0.1),IF(H69<=1000000,(((H69-500000)*0.2)+30000),IF(H69>1000000,((
	 * (H69-1000000)*0.3
	 * )+130000))))),IF(K4="F",IF(H69<=200000,0,IF(H69<=300000,((H69-200000)*0.1),IF(H69<=500000,(((H69-300000)*0.2)+12000),IF(H69>500000
	 * ,(((H69-500000)*0.3)+52000))))))),0)
	 * 
	 * @param gender
	 * @param taxableIncome
	 * @return
	 */
	public float getTds(String gender, float taxableIncome) {
		float tax = 0.0f;
		if (gender.equalsIgnoreCase("M")){
			if (taxableIncome <= 200000){
				tax = 0;
			} else if (200000 < taxableIncome && taxableIncome <= 500000){
				tax = ((taxableIncome - 200000) * 0.1f);
			} else if (500000 < taxableIncome && taxableIncome <= 1000000){
				tax = ((taxableIncome - 500000) * 0.2f) + 30000;
			} else if (taxableIncome > 1000000){
				tax = (((taxableIncome - 1000000) * 0.3f) + 130000);
			}
		} else if (gender.equalsIgnoreCase("F")){
			if (taxableIncome <= 200000){
				tax = 0;
			} else if (200000 < taxableIncome && taxableIncome <= 300000){
				tax = ((taxableIncome - 200000) * 0.1f);
			} else if (300000 < taxableIncome && taxableIncome <= 500000){
				tax = ((taxableIncome - 300000) * 0.2f) + 12000;
			} else if (taxableIncome > 500000){
				tax = (((taxableIncome - 500000) * 0.3f) + 52000);
			}
		} else{
			logger.error("The gender passed is incorrect Please check for the gender paramaeter as M/F");
		}
		return tax += tax * 0.03f;
	}

	/**
	 * Get the Gender of the user
	 * 
	 * @param useId
	 * @return
	 */
	public static String getGender(int userId) {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String gen = "";
		try{
			ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(userId);
			if (profileInfo != null && profileInfo.getGender().equalsIgnoreCase("Male")){
				gen = "M";
			} else if (profileInfo != null && profileInfo.getGender().equalsIgnoreCase("Female")){
				gen = "F";
			}
		} catch (ProfileInfoDaoException e){
			logger.error("There is a ProfileInfoDaoException occured while getting the gender  for user " + userId + e.getMessage());
		}
		return gen;
	}

	/**
	 * Use this method to get the Total Annual Net taxable income for the user for proposed benefits for display.
	 * Formula used is
	 * 
	 * @param userId
	 * @param fbpBenefitsDetails
	 *            String
	 * @param taxBenefit
	 *            String
	 * @return
	 * @throws TaxBenefitDaoException
	 */
	public float getActualTaxableAmt(int userId, String fbpBenefitsDetails, String taxBenefit) throws TaxBenefitDaoException {
		String monthId = FBPModel.getMonthId();
		//String financialYear = FBPModel.getFinancialYear();
		float incomeAdd = getIncomeFromSalary(userId);
		float fbpDecsLess = getFbpDeclarations(fbpBenefitsDetails);
		float us13ADeduc = 0.0f;
		String mp = new MonthlySalaryJob().getPTDeductions(userId, monthId).getAmount();
		float stdPT = Float.valueOf(mp != null ? DesEncrypterDecrypter.getInstance().decrypt(mp) : "0");
		float pfAmount = getPfForContribution(userId);
		//Calculate all tax exemption
		List<TaxBenefitDeclaration> taxBenefitDec = new TaxBenefitDeclarationModel().parseTaxBenefit(taxBenefit, userId);
		float allDeduc = 0.0f;
		float sumUs80c = 0.0f;
		float incomeOtherSrcs = 0.0f;
		for (TaxBenefitDeclaration element : taxBenefitDec){
			String category = element.getBenefitCategory();
			category = category != null ? category : "???";
			float currDeduc = Float.valueOf(element.getAmount());
			if (category.equalsIgnoreCase(CATEGORY_OTHER_SOURCES_OF_INCOME)) incomeOtherSrcs += currDeduc;
			else{
				if (category.equalsIgnoreCase(BENEFIT_CATEGORY_US80C) || category.equalsIgnoreCase(BENEFIT_CATEGORY_US80CCC) || category.equalsIgnoreCase(BENEFIT_CATEGORY_US80CCD)) sumUs80c += currDeduc;
				else if (category.equalsIgnoreCase(BENEFIT_CATEGORY_US10) && element.getBenefitName().equalsIgnoreCase(RENT_PAID)) //This deduction required formula and taken care by us13ADeduc variable
				us13ADeduc = getUs13ADeductions(userId, currDeduc);
				else allDeduc += currDeduc;
			}
		}
		if (sumUs80c > 100000) sumUs80c = 100000;
		allDeduc += sumUs80c + us13ADeduc;
		return (incomeAdd + incomeOtherSrcs - fbpDecsLess - (stdPT * 12) - allDeduc - pfAmount);
		//return (incomeAdd - fbpDecsLess - us13ADeduc - (stdPT*12) + incomeHouseProp + incomeOtherSrcs - allDeduc);
	}

	/**
	 * Use this method to get the Total Annual Net taxable income for the user for the current financial year
	 * Formula used is
	 * 
	 * @param userId
	 * @return
	 */
	public float getActualTaxableAmt(int userId) {
		String monthId = FBPModel.getMonthId();
		String financialYear = FBPModel.getFinancialYear();
		float incomeAdd = getIncomeFromSalary(userId);
		float fbpDecsLess = getFbpDeclarations(userId, monthId, financialYear);
		float us13ADeduc = getUs13ADeductions(userId, financialYear);
		//String mp = new MonthlySalaryJob().getPTDeductions(userId, monthId).getAmount();
		float stdPT = new MonthlySalaryJob().getPTDeductions(incomeAdd);
		float incomeHouseProp = getFromBenefits(userId, HOUSE_PROPERTY, financialYear);
		float incomeOtherSrcs = getAllFromBenefits(userId, new String[] { "Income by way of Dividends", "From any fund/foundation/ Educational institution/ hospital/ medical institutions/ trust or any institution u/s 10(23C);", "Any other income (Lotteries , interest on securities / bonds" }, financialYear);
		float pfAmount = getPfForContribution(userId);
		//Calculate all tax exemption 
		/* Change the Types of the Categories and the contents according to suggested By Shambhavi HS Starts Here - Fix - By Praneeth Ramesh - 18/06/2013*/
		/*		
				List<String> tbCategories=getTaxBenefitCategories();
				float allDeduc=0.0f;
				float sumUs80c=0.0f;
				for(String category:tbCategories){
					if(category.equalsIgnoreCase(CATEGORY_OTHER_SOURCES_OF_INCOME))
						continue;
					else{
						float currDeduc = getDeductionsByCategory(userId, category, financialYear);
						if(category.equalsIgnoreCase(BENEFIT_CATEGORY_US80C)||category.equalsIgnoreCase(BENEFIT_CATEGORY_US80CCC)||category.equalsIgnoreCase(BENEFIT_CATEGORY_US80CCD))
							sumUs80c+=currDeduc;
						else if(category.equalsIgnoreCase(BENEFIT_CATEGORY_US24) || category.equalsIgnoreCase(BENEFIT_CATEGORY_US80G))
							allDeduc += currDeduc;
					}
				}
				if(sumUs80c>100000)	sumUs80c=100000;
				allDeduc+=sumUs80c+us13ADeduc;
				
				float benefit80CCCG = getFromBenefits(userId, "Sec 80CCG - Rajeev Gandhi Equity Saving Scheme", financialYear);
				if(benefit80CCCG > 50000)	benefit80CCCG = 50000;
				allDeduc+=benefit80CCCG;
				float benefit80D = getFromBenefits(userId, "Sec 80D - Medical Insurance Premium", financialYear);
				if (benefit80D > 20000) 	benefit80D = 20000;
				allDeduc+=benefit80D;
		*/
		/* Change the Types of the Categories and the contents according to suggested By Shambhavi HS Ends Here - Fix - By Praneeth Ramesh - 18/06/2013*/
		float sum24A = getDeductionsByCategory(userId, BENEFIT_CATEGORY_US24, financialYear);
		if (sum24A > 150000) sum24A = 150000;
		float sum80C80CCCC = getDeductionsByCategory(userId, BENEFIT_CATEGORY_US80C, financialYear) + getDeductionsByCategory(userId, BENEFIT_CATEGORY_US80CCC, financialYear);
		sum80C80CCCC += pfAmount;
		if (sum80C80CCCC > 100000) sum80C80CCCC = 100000;
		float sum80D = getFromBenefits(userId, "80D (Medical insurance premium for Self and/or Family)", financialYear) + getFromBenefits(userId, "80D (Medical insurance premium for Parents)", financialYear);
		if (sum80D > 15000) sum80D = 15000;
		float sum80DDB = getFromBenefits(userId, "80DDB (Medical treatment for specific diseases)(Patient Below 65 years)", financialYear);
		if (sum80DDB > 40000) sum80DDB = 40000;
		float sum80E = getFromBenefits(userId, "80E Interest paid on Higher Education Loan", financialYear);
		if (sum80E > 20000) sum80E = 20000;
		float sum80EE = getFromBenefits(userId, "80EE Income tax benefit on interest on home loan (1st time buyers)", financialYear);
		if (sum80EE > 10000) sum80EE = 10000;
		float sum80G = getFromBenefits(userId, "80G Donations for charitable purposes", financialYear);
		float allDeduc = sum24A + sum80C80CCCC + sum80D + sum80DDB + sum80E + sum80EE + sum80G + us13ADeduc;
		//Done Fix
		return (incomeAdd - incomeHouseProp - incomeOtherSrcs - fbpDecsLess - (stdPT * 12) - allDeduc);
		//return (incomeAdd - fbpDecsLess - us13ADeduc - (stdPT*12) + incomeHouseProp + incomeOtherSrcs - allDeduc);
	}

	private float getAllFromBenefits(int userId, String[] benefitNames, String financialYear) {
		float sum = 0;
		TaxBenefitAcceptDao taxBenefitAcceptDao = TaxBenefitAcceptDaoFactory.create();
		try{
			TaxBenefitAccept[] Detail = taxBenefitAcceptDao.findByDynamicWhere("BENEFIT_ID IN (SELECT ID FROM TAX_BENEFIT WHERE `NAME` IN (?, ?, ?) ) AND USERID = ? AND FINANCIAL_YEAR = ?", new Object[] { benefitNames[0], benefitNames[1], benefitNames[2], userId, financialYear });
			if (Detail != null && Detail.length > 0){
				for (TaxBenefitAccept eachBenefit : Detail){
					sum += Float.valueOf(eachBenefit.getAmount());
				}
			}
		} catch (TaxBenefitAcceptDaoException e){
			logger.error(" There is a TaxBenefitAcceptDaoException occured while querying from the TaxBenefitsDeclaration table for the user " + userId + " " + e.getMessage());
		}
		return sum;
	}

	/*private String getBenefitsFromArray(String[] benefits) {
		String str = "";
		for (String eachString : benefits){
			str += "'" + eachString + "',";
		}
		return str.substring(0, str.length() - 1);
	}*/
	private float getPfForContribution(int userId) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		try{
			SalaryDetails[] salaryDetails = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID = 1 ", new Object[] { userId });
			if (salaryDetails != null && salaryDetails.length == 1){
				return (Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(salaryDetails[0].getAnnual())) * 0.12f);
			} else logger.error("There are no correct records for the user " + userId + " in salaryDetails Table ");
		} catch (SalaryDetailsDaoException e){
			logger.error("There is a salaryDetailsDaoException occured for the user " + userId + " while getting the basic salary record ");
		}
		return 0;
	}

	private float getDeductionsByCategory(int userId, String categoryName, String financialYear) {
		TaxBenefitAcceptDao taxBenefitAcceptDao = TaxBenefitAcceptDaoFactory.create();
		float sum = 0.0f;
		try{
			TaxBenefitAccept[] details = taxBenefitAcceptDao.findByDynamicWhere("BENEFIT_ID IN (SELECT ID FROM TAX_BENEFIT WHERE CATEGORY = ? ) AND USERID = ? AND FINANCIAL_YEAR = ?", new Object[] { categoryName, userId, financialYear });
			if (details != null && details.length > 0){
				for (TaxBenefitAccept eachDetail : details){
					sum += Float.valueOf(eachDetail.getAmount());
				}
			} else{
				logger.debug("There is no benefits decalarations declared for the user " + userId);
			}
		} catch (TaxBenefitAcceptDaoException e){
			logger.error(" There is a TaxBenefitAcceptDaoException occured while querying from the TaxBenefitsDeclaration table for the user " + userId + " " + e.getMessage());
		}
		return sum;
	}

	private float getFromBenefits(int userId, String benefitName, String financialYear) {
		TaxBenefitAcceptDao taxBenefitAcceptDao = TaxBenefitAcceptDaoFactory.create();
		try{
			TaxBenefitAccept[] Detail = taxBenefitAcceptDao.findByDynamicWhere("BENEFIT_ID IN (SELECT ID FROM TAX_BENEFIT WHERE `NAME` = ? ) AND USERID = ? AND FINANCIAL_YEAR = ?", new Object[] { benefitName, userId, financialYear });
			if (Detail != null && Detail.length == 1){ return Float.valueOf(Detail[0].getAmount()); }
		} catch (TaxBenefitAcceptDaoException e){
			logger.error(" There is a TaxBenefitAcceptDaoException occured while querying from the TaxBenefitsDeclaration table for the user " + userId + " " + e.getMessage());
		}
		return 0;
	}

	private float getUs13ADeductions(int userId, float us13ADedAmt) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		//TaxBenefitAcceptDao taxBenefitAcceptDao = TaxBenefitAcceptDaoFactory.create();
		float rent = 0.0f;
		float temp = 0.0f;
		float basic40 = 0.0f;
		try{
			SalaryDetails[] salaryDetails = salaryDetailsDao.findByDynamicWhere(" SAL_ID IN (1,3) AND USER_ID = ?", new Object[] { userId });
			//TaxBenefitAccept[] rentDetail = taxBenefitAcceptDao.findByDynamicWhere("BENEFIT_ID = (SELECT ID FROM TAX_BENEFIT WHERE `NAME` = ? ) AND USERID = ? AND FINANCIAL_YEAR = ?", new Object[]{RENT_PAID, userId, financialYear});
			if (salaryDetails != null && salaryDetails.length == 2){
				float basic = 0.0f, hra = 0.0f;
				for (SalaryDetails eachDetail : salaryDetails){
					if (eachDetail.getSalId() == 1){
						basic = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getAnnual()));
					} else if (eachDetail.getSalId() == 3){
						hra = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getAnnual()));
					}
				}
				basic40 = basic * 0.4f;
				temp = Math.min(hra, basic40);
				rent = us13ADedAmt - (basic * 0.1f);
				temp = Math.min(rent, temp);
			} else{
				logger.error(" There is no correct records found for the given user in the table for the user " + userId);
			}
		} catch (SalaryDetailsDaoException e){
			logger.error(" There is a SalaryDetailsDaoException occured while querying the BASIC AND HRA from the SalaryDetails table for the user " + userId + " " + e.getMessage());
		}
		return temp;
	}

	private float getUs13ADeductions(int userId, String financialYear) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		TaxBenefitAcceptDao taxBenefitAcceptDao = TaxBenefitAcceptDaoFactory.create();
		float rent = 0.0f;
		float temp = 0.0f;
		float basic40 = 0.0f;
		try{
			SalaryDetails[] salaryDetails = salaryDetailsDao.findByDynamicWhere(" SAL_ID IN (1,3) AND USER_ID = ?", new Object[] { userId });
			TaxBenefitAccept[] rentDetail = taxBenefitAcceptDao.findByDynamicWhere("BENEFIT_ID = (SELECT ID FROM TAX_BENEFIT WHERE `NAME` = ? ) AND USERID = ? AND FINANCIAL_YEAR = ?", new Object[] { RENT_PAID, userId, financialYear });
			if (salaryDetails != null && salaryDetails.length == 2){
				float basic = 0.0f, hra = 0.0f;
				for (SalaryDetails eachDetail : salaryDetails){
					if (eachDetail.getSalId() == 1){
						basic = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getAnnual()));
					} else if (eachDetail.getSalId() == 3){
						hra = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getAnnual()));
					}
				}
				basic40 = basic * 0.4f;
				temp = Math.min(hra, basic40);
				if (rentDetail != null && rentDetail.length == 1){
					rent = Float.valueOf(rentDetail[0].getAmount()) - (basic * 0.1f);
					temp = Math.min(rent, temp);
				} else if (rentDetail != null && rentDetail.length == 0){
					rent = 0;
					temp = Math.min(rent, temp);
				} else{
					logger.debug("The user has not decalared the rent " + userId);
				}
			} else{
				logger.error(" There is no correct records found for the given user in the table for the user " + userId);
			}
		} catch (SalaryDetailsDaoException e){
			logger.error(" There is a SalaryDetailsDaoException occured while querying the BASIC AND HRA from the SalaryDetails table for the user " + userId + " " + e.getMessage());
		} catch (TaxBenefitAcceptDaoException e){
			logger.error(" There is a TaxBenefitAcceptDaoException occured while querying the Rent paid from the TaxBenefitsDeclaration table for the user " + userId + " " + e.getMessage());
		}
		return temp;
	}

	/**
	 * Returns sum of proposed used amount by user. Supports TDS calculation for display.
	 * 
	 * @param fbpBenefitsDetails
	 * @return
	 */
	private float getFbpDeclarations(String fbpBenefitsDetails) {
		float sum = 0.0f;
		List<FbpDetails> fbpDetails = parseFbpDetails(fbpBenefitsDetails);
		for (FbpDetails element : fbpDetails){
			String amt = element.getUsedAmt();
			if (!amt.equalsIgnoreCase("NA") && !amt.equalsIgnoreCase("UA") && !element.getFbp().equalsIgnoreCase("OA")) sum += Float.valueOf(amt);
			else logger.debug("Skip " + element.getFbp() + " = " + amt);
		}
		return (sum);
	}

	private float getFbpDeclarations(int userId, String monthId, String financialYear) {
		FbpReqDao fbpReqDao = FbpReqDaoFactory.create();
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		FbpDetailsDao fbpDetailsDao = FbpDetailsDaoFactory.create();
		float sum = 0.0f;
		try{
			SalaryDetails[] salaryDetails = salaryDetailsDao.findByDynamicWhere(" SAL_ID IN (2) AND USER_ID = ? ", new Object[] { userId });
			if (salaryDetails != null && salaryDetails.length == 1) sum += Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(salaryDetails[0].getAnnual()));
			FbpReq[] fbpReqs = fbpReqDao.findByDynamicWhere(" MONTH_ID in (?,?) AND USER_ID = ? AND FREQUENT = 'yearly' ", new Object[] { monthId, financialYear, userId });
			if (fbpReqs != null && fbpReqs.length == 1){
				FbpDetails[] fbpDetails = fbpDetailsDao.findWhereFbpIdEquals(fbpReqs[0].getId());
				if (fbpDetails != null && fbpDetails.length > 0){
					for (FbpDetails eachDetails : fbpDetails){
						String amt = eachDetails.getUsedAmt();
						if (!amt.equalsIgnoreCase("NA") && !amt.equalsIgnoreCase("UA") && !eachDetails.getFbp().equalsIgnoreCase("OA")) sum += Float.valueOf(amt);
						else logger.debug("Skip " + eachDetails.getFbp() + " = " + amt);
					}
				} else{
					logger.debug("There is no correct FbpDetails records for the user " + userId + " for the month " + monthId);
				}
			} else{
				logger.debug("There is no FbpRecords for the user " + userId + " for the month " + monthId);
			}
		} catch (FbpReqDaoException e){
			logger.error("There is a FbpReqDaoException occured while querying the FbpReq For the user " + userId + " for the month " + monthId + e.getMessage());
		} catch (FbpDetailsDaoException e){
			logger.error("There is a FbpDetailsDaoException occured while querying the FbpDeclarations of the user " + userId + " for the month " + monthId + e.getMessage());
		} catch (SalaryDetailsDaoException e){
			logger.error("There is a SalaryDetailsDaoException occured while querying the conveyance of the user " + userId + e.getMessage());
		}
		return sum;
	}

	public float getIncomeFromSalary(int userId) {
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		float sum = 0.0f;
		try{
			SalaryDetails[] salaryDetails = salaryDetailsDao.findByDynamicWhere(" SAL_ID IN (1,2,3,4,5) AND USER_ID = ?", new Object[] { userId });
			if (salaryDetails != null && salaryDetails.length > 0){
				for (SalaryDetails eachDetail : salaryDetails){
					sum += Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(eachDetail.getAnnual()));
				}
			} else{
				logger.error("There is no records found for the given user in the table for the user " + userId);
			}
		} catch (SalaryDetailsDaoException e){
			logger.error("There is a SalaryDetailsDaoException occured while querying the IncomeFromSalary from the SalaryDetails table for the user " + userId + " " + e.getMessage());
		}
		return sum;
	}

	protected List<String> getTaxBenefitCategories() {
		List<String> categories = new ArrayList<String>();
		List<Object> result = JDBCUtiility.getInstance().getSingleColumn("SELECT DISTINCT(CATEGORY) FROM TAX_BENEFIT ORDER BY CATEGORY", null);
		for (Object element : result){
			if (element instanceof java.lang.String){
				String temp = (String) element;
				categories.add(temp);
			}
		}
		return (categories);
	}

	/**
	 * Parse FBP details string to FbpDetails bean.
	 * LTA=500;TPA=1000;MA=1500;CEA=500;MV=2500;TRA=900;OA=1500; to JavaBean
	 * 
	 * @param benefitsDetails
	 * @return
	 */
	private List<FbpDetails> parseFbpDetails(String benefitsDetails) {
		List<FbpDetails> fbpDec = new ArrayList<FbpDetails>();
		if (benefitsDetails != null && benefitsDetails.length() > 0){
			FbpComponent fbpComponent = new FbpComponent();
			String[] benefitsArr = benefitsDetails.split(";");
			for (String eachBenefit : benefitsArr){
				String[] eachBenefitArr = eachBenefit.split("=");
				String fbpAbbr = eachBenefitArr[0].toUpperCase();
				String fbpUsedAmt = eachBenefitArr[1];
				FbpComponent currFbpComponent = fbpComponent.getFbpComponentByName(fbpAbbr);
				FbpDetails tempFbp = new FbpDetails();
				tempFbp.setFbp(fbpAbbr);
				tempFbp.setUsedAmt(fbpUsedAmt);
				tempFbp.setFbpLabel(currFbpComponent.getFbpLabel());
				fbpDec.add(tempFbp);
			}
		}
		return (fbpDec);
	}

	public static void main(String[] args) {
		/*String[] s = new String[3];
		s[0] = "Income by way of Dividends"; 
		s[1] = "From any fund/foundation/ Educational institution/ hospital/ medical institutions/ trust or any institution u/s 10(23C);";
		s[2] = "Any other income (Lotteries , interest on securities / bonds";
		new TDSUtility().getAllFromBenefits(218, s, "2012-2013");*/
		new TDSUtility().reCalculate(37);
	}
}
