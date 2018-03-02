package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.Salary;
import com.dikshatech.beans.SalaryConfigs;
import com.dikshatech.beans.SalaryHead;
import com.dikshatech.common.db.MyDBConnect;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.PerdiemMasterDataDao;
import com.dikshatech.portal.dao.SalaryConfigDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dto.FinanceInfo;
import com.dikshatech.portal.dto.PerdiemMasterData;
import com.dikshatech.portal.dto.SalaryConfig;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SalaryDetailsPk;
import com.dikshatech.portal.exceptions.DaoException;
import com.dikshatech.portal.exceptions.SalaryConfigDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.factory.PerdiemMasterDataDaoFactory;
import com.dikshatech.portal.factory.SalaryConfigDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class SalaryDetailModel extends ActionMethods {

	public static String		TYPE_REPLICA					= "REPLICA";
	public static String		TYPE_NORMAL						= "NORMAL";
	private static Logger		logger							= LoggerUtil.getLogger(SalaryDetailModel.class);
	IntegerPerm					integerPerm						= new IntegerPerm();
	public final static String	HEAD_INSURANCE					= "INSURANCE";
	public static final int		MONTH_DAYS						= 30;
	public static final int		YEAR_DAYS						= 365;
	public final static String	REPORT_LOCALE_KEY				= "REPORT_LOCALE";
	public final static Locale	IN_LOCALE						= new Locale("en", "IN");
	public final static String	ESCC_KEY						= "EXCLUDE_SALARY_CONFIG_CLAUSE";
	public final static String	VSCC_KEY						= "VIEW_SALARY_CONFIG_CLAUSE";
	public final static String	EXCLUDE_SALARY_CONFIG_CLAUSE	= "SALARY_CONFIG.VALUE_TYPE IS NOT NULL AND SALARY_CONFIG.FORMULA>=0";
	public final static String	VIEW_SALARY_CONFIG_CLAUSE		= "SALARY_CONFIG.FORMULA<0";
//	public final static String	SALARY_CONFIG					= "CONFIG";
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		SalaryDetails salaryDetails = (SalaryDetails) form;
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		SalaryDetailsPk salaryDetailsPk = new SalaryDetailsPk();
		try{
			if (salaryDetails.getCandidateId() > 0){
				SalaryDetails salaryDetails2[] = null;
				//salaryDetails2 = salaryDetailsDao.findByDynamicWhere("CANDIDATE_ID = ?  AND ID = ?",new Object[]{salaryDetails.getCandidateId(),salaryDetails.getId()});
				salaryDetails2 = salaryDetailsDao.findWhereCandidateIdEquals(salaryDetails.getCandidateId());
				for (int i = 0; i < salaryDetails2.length; i++){
					salaryDetailsPk.setId(salaryDetails2[i].getId());
					salaryDetailsDao.delete(salaryDetailsPk);
				}
			} else if (salaryDetails.getId() > 0){
				salaryDetailsPk.setId(salaryDetails.getId());
				salaryDetailsDao.delete(salaryDetailsPk);
			}
		} catch (Exception e){
			logger.info("Failed to delete Salary Information");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		SalaryDetails salaryDetails = (SalaryDetails) form;
		DropDown dropdown = new DropDown();
		try{
			int candidateId = salaryDetails.getCandidateId();
			int userId = salaryDetails.getUserId();
			if (candidateId > 0){
				//				SalaryDetails salaryDetails2[] = null;
				//				salaryDetails2 = salaryDetailsDao.findWhereCandidateIdEquals(salaryDetails.getCandidateId());
				//				SalaryDetailBean[] sal = new SalaryDetailBean[salaryDetails2.length];
				//				for (int i = 0; i < salaryDetails2.length; i++){
				//					SalaryDetailBean salaryDetailBean = DtoToBeanConverter.DtoToBeanConverter(salaryDetails2[i]);
				//					sal[i] = salaryDetailBean;
				//				}
				Salary sal = getSalary(candidateId, false, SalaryDetailModel.TYPE_NORMAL);
				Object[] salArray = new Object[] { sal };
				dropdown.setDropDown(salArray);
			} else if (userId > 0){
				Salary sal = getSalary(candidateId, true, SalaryDetailModel.TYPE_NORMAL);
				Object[] salArray = new Object[] { sal };
				dropdown.setDropDown(salArray);
			} else{
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			}
		} catch (Exception e){
			logger.info("Failed to receive Salary Information");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		request.setAttribute("actionForm", dropdown);
		return result;
	}

	public Salary getSalary(int candidateId, boolean isUserId, String retrieveFrom) {
		final String TOTAL = "Total";
		final String CTC = "CTC";
		final String PERDIEM = "PERDIEM";
		final String SALARY_STACK = "SALARY STACK";
		final int AUTO_CALC = 0;
		final float VALUE = 0.0F;
		final float FORUMULA = 0.0f;
		float tplb = 0.0F;
		final String VALUE_TYPE = "EXISTING SALARY";
		Salary candidateSalary = new Salary();
		try{
			//Obtain all salary details for a candidate
			SalaryConfigDao salaryConfigProvider = SalaryConfigDaoFactory.create();
			SalaryDetailsDao salaryProvider = null;
			if (retrieveFrom.equals(SalaryDetailModel.TYPE_REPLICA)){
				salaryProvider = SalaryDetailsDaoFactory.createReplica();
			} else{
				salaryProvider = SalaryDetailsDaoFactory.create();
			}
			SalaryDetails[] salaryDetails = null;
			if (isUserId) salaryDetails = salaryProvider.findWhereUserIdEquals(candidateId);
			else salaryDetails = salaryProvider.findWhereCandidateIdEquals(candidateId);
			//Stores salary heads. Salary heads are created based on salary detail being processing.
			Map<String, SalaryHead> salaryHeadsCache = new HashMap<String, SalaryHead>();
			for (SalaryDetails element : salaryDetails){
				SalaryHead salaryHead = null;
				String component = null;
				String head = null;
				int headOrder = 0;
				SalaryHead existingSalaryHead = null;
				SalaryConfig salaryConfig = null;
				boolean flagSalId = element.isSalIdNull();
				if (flagSalId == false){
					//New salary structure processing logic
					//All salary details under different salary heads
					salaryConfig = salaryConfigProvider.findByPrimaryKey(element.getSalId());
					head = salaryConfig.getHead();
					headOrder = salaryConfig.getHeadOrder();
					component = salaryConfig.getComponent();
					String salaryConfigComponent = salaryConfig.getComponent();
				 if (component.equalsIgnoreCase("fixed Perdiem")){
						String fixedPerdiemAmount = element.getMonthly();
						fixedPerdiemAmount = fixedPerdiemAmount != null ? DesEncrypterDecrypter.getInstance().decrypt(fixedPerdiemAmount) : "0.0f";
						candidateSalary.setFixedPerdiemAmount(Math.round(Float.valueOf(fixedPerdiemAmount) / MONTH_DAYS));
					}
				 if (component.equalsIgnoreCase("Retention Bonus")){
						String retentionBonus = element.getAnnual();
						retentionBonus = retentionBonus != null ? DesEncrypterDecrypter.getInstance().decrypt(retentionBonus) : "0.0f";
						candidateSalary.setRetentionBonus(Math.round(Float.valueOf(retentionBonus)));
					}
					if (salaryConfig.getValueType() == null){
						//Processing special salary config
						//Currently CTC and PERDIEM only
						//Special salary-configs are designated by having VALUE_TYPE null
						//and others as PERCENT or RAW
						if (salaryConfigComponent.indexOf(CTC) != -1){
							String ctcAmount = element.getAnnual();
							ctcAmount = ctcAmount != null ? DesEncrypterDecrypter.getInstance().decrypt(ctcAmount) : "0.0f";
							float ctc = Float.valueOf(ctcAmount);
							candidateSalary.setCtc(ctc);
							candidateSalary.setFBP(ctc);
							candidateSalary.setFixed(ctc);
							//candidateSalary.setTblb(candidateSalary.getFixed());
						} else if (salaryConfigComponent.indexOf(PERDIEM) != -1){
							String perdiemAmount = element.getMonthly();
							perdiemAmount = perdiemAmount != null ? DesEncrypterDecrypter.getInstance().decrypt(perdiemAmount) : "0.0f";
							candidateSalary.setPerDiem(Math.round(Float.valueOf(perdiemAmount) / MONTH_DAYS));
						} else{
							logger.debug("Unable to process SALARY_CONFIG entry ID: " + salaryConfig.getId());
						}
						logger.debug("Setting " + head + " for associated SALARY_DETAILS.ID: " + element.getId());
						logger.debug("Excluding this salary details ID: " + element.getId() + "  to be added to head: " + head);
						continue;
					} else logger.debug("Processing SALARY_DETAILS for ID: " + element.getId());
				} else{
					//Previous salary structure processing logic
					//All details under one salary head
					head = SALARY_STACK;
				}
				//Obtain SalaryHead object from mapanotherString
				existingSalaryHead = salaryHeadsCache.get(head);
				if (existingSalaryHead == null){
					//Create and add SalaryHead object if doesn't exists
					SalaryHead newSalaryHead = new SalaryHead();
					newSalaryHead.setOrder(headOrder);
					newSalaryHead.setName(head);
					newSalaryHead.setAnnualSum(0.0f);
					newSalaryHead.setMonthlySum(0.0f);
					salaryHeadsCache.put(head, newSalaryHead);
					salaryHead = salaryHeadsCache.get(head);
					//Assigns new SalaryHead got from map for processing
					salaryHead = newSalaryHead;
				} else{
					//Assigns existing SalaryHead got from map for processing
					salaryHead = existingSalaryHead;
				}
				//Stores salary-details for each head
				List<SalaryConfigs> salaryComponent = salaryHead.getSalaryConfigs();
				SalaryConfigs newSalaryConfig = new SalaryConfigs();
				newSalaryConfig.setId(element.getId());
				if (flagSalId == false){
					//New salary structure processing logic
					newSalaryConfig.setComponent(salaryConfig.getComponent());
					newSalaryConfig.setComponentOrder(salaryConfig.getComponentOrder());
					newSalaryConfig.setAutoCalc(salaryConfig.getAutoCalc());
					newSalaryConfig.setValue(salaryConfig.getValue());
					newSalaryConfig.setValueType(salaryConfig.getValueType());
					newSalaryConfig.setFormula(salaryConfig.getFormula());
				} else{
					//Previous salary structure processing logic
					//Sets some fields to default as values doesn't exists
					newSalaryConfig.setComponent(element.getFieldLabel());
					newSalaryConfig.setComponentOrder(salaryComponent.size() + 1);
					newSalaryConfig.setAutoCalc(AUTO_CALC);
					newSalaryConfig.setValue(VALUE);
					newSalaryConfig.setValueType(VALUE_TYPE);
					newSalaryConfig.setFormula(FORUMULA);
				}
				String strAmount = element.getAnnual();
				if (strAmount != null){
					strAmount = DesEncrypterDecrypter.getInstance().decrypt(strAmount);
					newSalaryConfig.setAnnualAmount(Float.valueOf(strAmount));
				} else newSalaryConfig.setAnnualAmount(0.0f);
				if (component.toLowerCase().indexOf("performance linked") > 0) candidateSalary.setTblbValue(candidateSalary.getTblb() + newSalaryConfig.getAnnualAmount());
				strAmount = element.getMonthly();
				boolean isLeaveAmt = FinanceInfoModel.isLeaveMonthly(head, component);
				if (!isLeaveAmt && strAmount != null){
					strAmount = DesEncrypterDecrypter.getInstance().decrypt(strAmount);
					newSalaryConfig.setMonthlyAmount(Float.valueOf(strAmount));
				} else{
					newSalaryConfig.setMonthlyAmount(0.0f);
				}
				if (flagSalId && newSalaryConfig.getComponent().equals(TOTAL)){
					candidateSalary.setAnnualCTC(newSalaryConfig.getAnnualAmount());
					candidateSalary.setMonthlyCTC(newSalaryConfig.getMonthlyAmount());
					candidateSalary.setTotCTC(newSalaryConfig.getAnnualAmount());
				} else salaryComponent.add(newSalaryConfig);
			}
			//Set annualSum and monthlySum for each head
			Collection<SalaryHead> candidateSalaryHeads = salaryHeadsCache.values();
			for (SalaryHead element : candidateSalaryHeads){
				
		//		System.out.println("element" +element);
				element.setSum();
			}
			SalaryHead insuranceHead = null;
			//Calculated Total cost to the company
			if (candidateSalary.getAnnualCTC() <= 0){
				float totAnnualCtc = 0f;
				float totMonthlyCtc = 0f;
				for (SalaryHead element : candidateSalaryHeads){
					String headName = element.getName();
					if (headName != null && !headName.equals(HEAD_INSURANCE)){
						totAnnualCtc += element.getAnnualSum();
						totMonthlyCtc += element.getMonthlySum();
					} else{
						insuranceHead = element;
					}
				}
				totAnnualCtc = Math.round(totAnnualCtc);
				totMonthlyCtc = Math.round(totMonthlyCtc);
				candidateSalary.setAnnualCTC(totAnnualCtc);
				candidateSalary.setMonthlyCTC(totMonthlyCtc);
				candidateSalary.setTotCTC(totAnnualCtc);
			}
			List<SalaryHead> allSalaryHeads = new ArrayList<SalaryHead>(candidateSalaryHeads);
			allSalaryHeads.remove(insuranceHead);
			SalaryHead totCtcHead = new SalaryHead();
			totCtcHead.setName("Total Cost To Company (A+B+C+D)");
			totCtcHead.setAnnualSum(candidateSalary.getAnnualCTC());
			totCtcHead.setMonthlySum(candidateSalary.getMonthlyCTC());
			Collections.sort(allSalaryHeads, new Comparator<SalaryHead>() {

				@Override
				public int compare(SalaryHead head1, SalaryHead head2) {
					return (head1.getOrder() - head2.getOrder());
				}
			});
			//Adds total cost to the company salary head
			allSalaryHeads.add(totCtcHead);
			//Putting insurance head down
			allSalaryHeads.add(insuranceHead);
			candidateSalary.setSalaryHead(allSalaryHeads);
			if (isUserId){
				try{
					PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create();
					PerdiemMasterData fetchedPerdiemDtos[] = perdiemMasterDao.findWhereUserIdEquals(candidateId);
					if (fetchedPerdiemDtos != null && fetchedPerdiemDtos.length > 0){
						candidateSalary.setPerDiem(Float.parseFloat(DesEncrypterDecrypter.getInstance().decrypt(fetchedPerdiemDtos[0].getPerdiem())));
						candidateSalary.setPerdiemFrom(PortalUtility.yearFormatDate(fetchedPerdiemDtos[0].getPerdiemFrom()));
						candidateSalary.setPerdiemTo(PortalUtility.yearFormatDate(fetchedPerdiemDtos[0].getPerdiemTo()));
						candidateSalary.setPerdiemCurrencyType(fetchedPerdiemDtos[0].getCurrencyType());
					}
				} catch (Exception e){}
			}
		} catch (SalaryDetailsDaoException ex){
			logger.error("Unable to get salary details for CANDIDATE_ID: " + candidateId);
			logger.error(ex.getMessage());
		} catch (SalaryConfigDaoException ex){
			logger.error("Unable to get salary config for CANDIDATE_ID: " + candidateId);
			logger.error(ex.getMessage());
		}
		return (candidateSalary);
	}
	public Salary getSalarynew(int candidateId, boolean isUserId, String retrieveFrom) {
	
		
		
		final String TOTAL = "Total";
		final String CTC = "CTC";
		final String PERDIEM = "PERDIEM";
		final String SALARY_STACK = "SALARY STACK";
		final int AUTO_CALC = 0;
		final float VALUE = 0.0F;
		final float FORUMULA = 0.0f;
		float tplb = 0.0F;
		final String VALUE_TYPE = "EXISTING SALARY";
		Salary candidateSalary = new Salary();
		try{
			//Obtain all salary details for a candidate
			SalaryConfigDao salaryConfigProvider = SalaryConfigDaoFactory.create();
			SalaryDetailsDao salaryProvider = null;
			if (retrieveFrom.equals(SalaryDetailModel.TYPE_REPLICA)){
				salaryProvider = SalaryDetailsDaoFactory.createReplica();
			} else{
				salaryProvider = SalaryDetailsDaoFactory.create();
			}
			SalaryDetails[] salaryDetails = null;
			if (isUserId) salaryDetails = salaryProvider.findWhereUserIdEquals(candidateId);
			else salaryDetails = salaryProvider.findWhereCandidateIdEquals(candidateId);
			//Stores salary heads. Salary heads are created based on salary detail being processing.
			Map<String, SalaryHead> salaryHeadsCache = new HashMap<String, SalaryHead>();
			for (SalaryDetails element : salaryDetails){
				SalaryHead salaryHead = null;
				String component = null;
				String head = null;
				int headOrder = 0;
				SalaryHead existingSalaryHead = null;
				SalaryConfig salaryConfig = null;
				boolean flagSalId = element.isSalIdNull();
				if (flagSalId == false){
					//New salary structure processing logic
					//All salary details under different salary heads
					salaryConfig = salaryConfigProvider.findByPrimaryKey(element.getSalId());
					head = salaryConfig.getHead();
					headOrder = salaryConfig.getHeadOrder();
					component = salaryConfig.getComponent();
					String salaryConfigComponent = salaryConfig.getComponent();
				 if (component.equalsIgnoreCase("fixed Perdiem")){
						String fixedPerdiemAmount = element.getMonthly();
						fixedPerdiemAmount = fixedPerdiemAmount != null ? DesEncrypterDecrypter.getInstance().decrypt(fixedPerdiemAmount) : "0.0f";
						candidateSalary.setFixedPerdiemAmount(Math.round(Float.valueOf(fixedPerdiemAmount) / MONTH_DAYS));
					}
				 if (component.equalsIgnoreCase("Retention Bonus")){
						String retentionBonus = element.getAnnual();
						retentionBonus = retentionBonus != null ? DesEncrypterDecrypter.getInstance().decrypt(retentionBonus) : "0.0f";
						candidateSalary.setRetentionBonus(Math.round(Float.valueOf(retentionBonus)));
					}
					if (salaryConfig.getValueType() == null){
						//Processing special salary config
						//Currently CTC and PERDIEM only
						//Special salary-configs are designated by having VALUE_TYPE null
						//and others as PERCENT or RAW
						if (salaryConfigComponent.indexOf(CTC) != -1){
							String ctcAmount = element.getAnnual();
							ctcAmount = ctcAmount != null ? DesEncrypterDecrypter.getInstance().decrypt(ctcAmount) : "0.0f";
							float ctc = Float.valueOf(ctcAmount);
							candidateSalary.setCtc(ctc);
							if(element.getSalaryStack()!=1)
							{
							float	FBPRATE	= .25f;
							candidateSalary.setFBP(ctc*FBPRATE);
							}
							candidateSalary.setFixed(ctc);
							//candidateSalary.setTblb(candidateSalary.getFixed());
						} else if (salaryConfigComponent.indexOf(PERDIEM) != -1){
							String perdiemAmount = element.getMonthly();
							perdiemAmount = perdiemAmount != null ? DesEncrypterDecrypter.getInstance().decrypt(perdiemAmount) : "0.0f";
							candidateSalary.setPerDiem(Math.round(Float.valueOf(perdiemAmount) / MONTH_DAYS));
						} else{
							logger.debug("Unable to process SALARY_CONFIG entry ID: " + salaryConfig.getId());
						}
						logger.debug("Setting " + head + " for associated SALARY_DETAILS.ID: " + element.getId());
						logger.debug("Excluding this salary details ID: " + element.getId() + "  to be added to head: " + head);
						continue;
					} else logger.debug("Processing SALARY_DETAILS for ID: " + element.getId());
				} else{
					//Previous salary structure processing logic
					//All details under one salary head
					head = SALARY_STACK;
				}
				//Obtain SalaryHead object from mapanotherString
				existingSalaryHead = salaryHeadsCache.get(head);
				if (existingSalaryHead == null){
					//Create and add SalaryHead object if doesn't exists
					SalaryHead newSalaryHead = new SalaryHead();
					newSalaryHead.setOrder(headOrder);
					newSalaryHead.setName(head);
					newSalaryHead.setAnnualSum(0.0f);
					newSalaryHead.setMonthlySum(0.0f);
					salaryHeadsCache.put(head, newSalaryHead);
					salaryHead = salaryHeadsCache.get(head);
					//Assigns new SalaryHead got from map for processing
					salaryHead = newSalaryHead;
				} else{
					//Assigns existing SalaryHead got from map for processing
					salaryHead = existingSalaryHead;
				}
				//Stores salary-details for each head
				List<SalaryConfigs> salaryComponent = salaryHead.getSalaryConfigs();
				SalaryConfigs newSalaryConfig = new SalaryConfigs();
				newSalaryConfig.setId(element.getId());
				if (flagSalId == false){
					//New salary structure processing logic
					newSalaryConfig.setComponent(salaryConfig.getComponent());
					newSalaryConfig.setComponentOrder(salaryConfig.getComponentOrder());
					newSalaryConfig.setAutoCalc(salaryConfig.getAutoCalc());
					newSalaryConfig.setValue(salaryConfig.getValue());
					newSalaryConfig.setValueType(salaryConfig.getValueType());
					newSalaryConfig.setFormula(salaryConfig.getFormula());
				} else{
					//Previous salary structure processing logic
					//Sets some fields to default as values doesn't exists
					newSalaryConfig.setComponent(element.getFieldLabel());
					newSalaryConfig.setComponentOrder(salaryComponent.size() + 1);
					newSalaryConfig.setAutoCalc(AUTO_CALC);
					newSalaryConfig.setValue(VALUE);
					newSalaryConfig.setValueType(VALUE_TYPE);
					newSalaryConfig.setFormula(FORUMULA);
				}
				String strAmount = element.getAnnual();
				if (strAmount != null){
					strAmount = DesEncrypterDecrypter.getInstance().decrypt(strAmount);
					newSalaryConfig.setAnnualAmount(Float.valueOf(strAmount));
				} else newSalaryConfig.setAnnualAmount(0.0f);
				if (component.toLowerCase().indexOf("performance linked") > 0) 
					{candidateSalary.setTblbValue(candidateSalary.getTblb() + newSalaryConfig.getAnnualAmount());}
				
				if(element.getSalaryStack()==1)
				{
					candidateSalary.setTblbValue(element.getTplp());
				}
				strAmount = element.getMonthly();
				boolean isLeaveAmt = FinanceInfoModel.isLeaveMonthly(head, component);
				if (!isLeaveAmt && strAmount != null){
					strAmount = DesEncrypterDecrypter.getInstance().decrypt(strAmount);
					newSalaryConfig.setMonthlyAmount(Float.valueOf(strAmount));
				} else{
					newSalaryConfig.setMonthlyAmount(0.0f);
				}
				if (flagSalId && newSalaryConfig.getComponent().equals(TOTAL)){
					candidateSalary.setAnnualCTC(newSalaryConfig.getAnnualAmount());
					candidateSalary.setMonthlyCTC(newSalaryConfig.getMonthlyAmount());
					candidateSalary.setTotCTC(newSalaryConfig.getAnnualAmount());
				} else salaryComponent.add(newSalaryConfig);
				
				if((element.getSalaryStack()==1 )&&element.getFieldLabel().equalsIgnoreCase("C - BENEFITS - Flexi Benefit Plan(FBP)"))
				{
					String a = element.getAnnual();
					a = DesEncrypterDecrypter.getInstance().decrypt(a);
					candidateSalary.setFBP (Float.valueOf(a));
				}
				candidateSalary.setEsic(element.getEsic());
				candidateSalary.setActive(element.getActive());
				candidateSalary.setInActiveFromDate(element.getInActiveFromDate() == null ? null : new java.sql.Date(element.getInActiveFromDate().getTime()));
				candidateSalary.setInActiveToDate(element.getInActiveToDate()== null ? null : new java.sql.Date(element.getInActiveToDate().getTime()));
			}
			 
			//Set annualSum and monthlySum for each head
			Collection<SalaryHead> candidateSalaryHeads = salaryHeadsCache.values();
			for (SalaryHead element : candidateSalaryHeads){
				
		//		System.out.println("element" +element);
				element.setSum();
			}
			SalaryHead insuranceHead = null;
			//Calculated Total cost to the company
			if (candidateSalary.getAnnualCTC() <= 0){
				float totAnnualCtc = 0f;
				float totMonthlyCtc = 0f;
				for (SalaryHead element : candidateSalaryHeads){
					String headName = element.getName();
					if (headName != null && !headName.equals(HEAD_INSURANCE)){
						totAnnualCtc += element.getAnnualSum();
						totMonthlyCtc += element.getMonthlySum();
					} else{
						insuranceHead = element;
					}
				}
				totAnnualCtc = Math.round(totAnnualCtc);
				totMonthlyCtc = Math.round(totMonthlyCtc);
				candidateSalary.setAnnualCTC(totAnnualCtc);
				candidateSalary.setMonthlyCTC(totMonthlyCtc);
				candidateSalary.setTotCTC(totAnnualCtc);
			}
			List<SalaryHead> allSalaryHeads = new ArrayList<SalaryHead>(candidateSalaryHeads);
			allSalaryHeads.remove(insuranceHead);
			SalaryHead totCtcHead = new SalaryHead();
			totCtcHead.setName("Total Cost To Company (A+B+C+D)");
			totCtcHead.setAnnualSum(candidateSalary.getAnnualCTC());
			totCtcHead.setMonthlySum(candidateSalary.getMonthlyCTC());
			Collections.sort(allSalaryHeads, new Comparator<SalaryHead>() {

				@Override
				public int compare(SalaryHead head1, SalaryHead head2) {
					return (head1.getOrder() - head2.getOrder());
				}
			});
			//Adds total cost to the company salary head
			allSalaryHeads.add(totCtcHead);
			//Putting insurance head down
			allSalaryHeads.add(insuranceHead);
			candidateSalary.setSalaryHead(allSalaryHeads);
			if (isUserId){
				try{
					PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create();
					PerdiemMasterData fetchedPerdiemDtos[] = perdiemMasterDao.findWhereUserIdEquals(candidateId);
					if (fetchedPerdiemDtos != null && fetchedPerdiemDtos.length > 0){
						candidateSalary.setPerDiem(Float.parseFloat(DesEncrypterDecrypter.getInstance().decrypt(fetchedPerdiemDtos[0].getPerdiem())));
						candidateSalary.setPerdiemFrom(PortalUtility.yearFormatDate(fetchedPerdiemDtos[0].getPerdiemFrom()));
						candidateSalary.setPerdiemTo(PortalUtility.yearFormatDate(fetchedPerdiemDtos[0].getPerdiemTo()));
						candidateSalary.setPerdiemCurrencyType(fetchedPerdiemDtos[0].getCurrencyType());
					}
				} catch (Exception e){}
			}
		} catch (SalaryDetailsDaoException ex){
			logger.error("Unable to get salary details for CANDIDATE_ID: " + candidateId);
			logger.error(ex.getMessage());
		} catch (SalaryConfigDaoException ex){
			logger.error("Unable to get salary config for CANDIDATE_ID: " + candidateId);
			logger.error(ex.getMessage());
		}
		return (candidateSalary);
	}

	/**
	 * This method takes the salaryDetailsDto array and convert into the Salary bean
	 * Also take care of the existing records to return
	 * 
	 * @param salaryDetails
	 * @param oldRecord
	 * @return
	 */
	public Salary getSalaryBeanFromSalaryDetailsDto(SalaryDetails[] salaryDetails, boolean oldRecord, int candidateId) {
		final String GET_DETAILS_QUERY = "SELECT SC.ID, HEAD, COMPONENT, `VALUE`, VALUE_TYPE, AUTO_CALC, COMPONENT_ORDER, MONTHLY, ANNUAL, SAL_ID " + " FROM SALARY_CONFIG SC JOIN SALARY_DETAILS SD " + " ON SD.SAL_ID = SC.ID " + " WHERE SC.HEAD = ? " + " AND SD.CANDIDATE_ID = ? ";
		final String GET_HEADS_QUERY = "SELECT DISTINCT(HEAD) FROM SALARY_CONFIG";
		float ctc = 0, perDiem = 0;
		Salary salary = new Salary();
		SalaryHead salaryHead = new SalaryHead();
		List<SalaryHead> salaryHeadList = new ArrayList<SalaryHead>();
		SalaryConfigs salaryConfigs;
		List<SalaryConfigs> salaryConfigsList = new ArrayList<SalaryConfigs>();
		int componentValue = 0;
		Connection conn = MyDBConnect.getConnect();
		Statement stmt;
		PreparedStatement pStmt;
		ResultSet res1, res;
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		if (oldRecord == true){
			for (SalaryDetails eachSalaryDetail : salaryDetails){
				salaryConfigs = new SalaryConfigs();
				salaryConfigs.setId(eachSalaryDetail.getId());
				salaryConfigs.setComponentOrder(++componentValue);
				salaryConfigs.setComponent(eachSalaryDetail.getFieldLabel());
				String annualAmount = DesEncrypterDecrypter.getInstance().decrypt(eachSalaryDetail.getAnnual());
				if (annualAmount != null) salaryConfigs.setAnnualAmount(Float.valueOf(annualAmount));
				String monthlyAmount = DesEncrypterDecrypter.getInstance().decrypt(eachSalaryDetail.getMonthly());
				if (monthlyAmount != null) salaryConfigs.setMonthlyAmount(Float.valueOf(monthlyAmount));
				salaryConfigs.setValue(0);
				salaryConfigs.setAutoCalc(0);
				salaryConfigs.setValueType("");
				salaryConfigsList.add(salaryConfigs);
			}
			salaryHead.setSalaryConfigs(salaryConfigsList);
			salaryHead.setName("Diksha Salary Stach Up");
			salaryHeadList.add(salaryHead);
			salary.setSalaryHead(salaryHeadList);
		} else{
			int headTotal = 0;
			try{
				stmt = conn.createStatement();
				res1 = stmt.executeQuery(GET_HEADS_QUERY);
				while (res1.next()){
					pStmt = conn.prepareStatement(GET_DETAILS_QUERY);
					pStmt.setString(1, res1.getString("HEAD"));
					pStmt.setInt(2, candidateId);
					res = pStmt.executeQuery();
					while (res.next()){
						salaryConfigs = new SalaryConfigs();
						salaryConfigs.setId(res.getInt("ID"));
						salaryConfigs.setComponentOrder(res.getInt("COMPONENT_ORDER"));
						salaryConfigs.setComponent(res.getString("COMPONENT"));
						float annual = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(res.getString("ANNUAL")));
						salaryConfigs.setAnnualAmount(annual);
						salaryConfigs.setMonthlyAmount(Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(res.getString("MONTHLY"))));
						salaryConfigs.setValue(res.getFloat("VALUE"));
						salaryConfigs.setAutoCalc(res.getInt("AUTO_CALC"));
						salaryConfigs.setValueType(res.getString("VALUE_TYPE"));
						salaryConfigsList.add(salaryConfigs);
						headTotal += annual;
					}
					salaryHead = new SalaryHead();
					salaryHead.setName(res1.getString("HEAD"));
					salaryHead.setSalaryConfigs(salaryConfigsList);
					salaryHead.setAnnualSum(headTotal);
					salaryHead.setMonthlySum(headTotal / 12);
					salaryHeadList.add(salaryHead);
				}
				salary.setSalaryHead(salaryHeadList);
				SalaryDetails[] ctcSalaryDetails = salaryDetailsDao.findByDynamicWhere(" CANDIDATE_ID = ? AND SAL_ID in (SELECT ID FROM SALARY_CONFIG WHERE VALUE_TYPE = '') ", new Object[] { candidateId });
				if (ctcSalaryDetails.length == 2){
					if (ctcSalaryDetails[0].getFieldLabel().indexOf("CTC") != -1) ctc = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(ctcSalaryDetails[0].getAnnual()));
					if (ctcSalaryDetails[1].getFieldLabel().indexOf("PERDIEM") != -1) perDiem = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(ctcSalaryDetails[1].getAnnual()));
				} else{
					logger.error(" The no of records returned for the query to get ctc and perdiem is not 2 for the candidate " + candidateId);
				}
				salary.setCtc(ctc);
				salary.setPerDiem(perDiem);
				salary.setFixed((float) (ctc * .75));
			} catch (Exception e){
				logger.error("The data could not be queried from DB for the candidate " + candidateId + e.getMessage());
			}
		}
		return salary;
	}

	/**
	 * Extra method written for Salary_restructure to call from Candidate Model while saving to SALARY_DETAILS modified table
	 * 
	 * @param salary
	 */
	public ActionResult saveSalaryDetails(float plb, Salary salary, int userId, int candidateId, String saveType) {
		ActionResult result = new ActionResult();
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		if (saveType.equalsIgnoreCase(SalaryDetailModel.TYPE_REPLICA)){
			salaryDetailsDao = SalaryDetailsDaoFactory.createReplica();
		} else{
			salaryDetailsDao = SalaryDetailsDaoFactory.create();
		}
		SalaryHead salHead;
		List<SalaryConfigs> salaryConfigs;
		SalaryConfigs salConfigs;
		SalaryDetails salaryDetails;
		Iterator<SalaryHead> iterator = null;
		Iterator<SalaryConfigs> iterator1 = null;
		boolean isExceptionflag = false;
		SalaryDetails[] existingSalaryDetails = null;
		try{
			if (userId != 0) existingSalaryDetails = salaryDetailsDao.findWhereUserIdEquals(userId);
			else if (candidateId != 0) existingSalaryDetails = salaryDetailsDao.findWhereCandidateIdEquals(candidateId);
			else logger.debug("Salary detail doesn't exists for userId: " + userId + ", candidateId: " + candidateId);
			if (existingSalaryDetails != null){
				String existingsalaryDetailsId = "";
				for (int i = 0; i < existingSalaryDetails.length; i++){
					int detailsId = existingSalaryDetails[i].getId();
					existingsalaryDetailsId += detailsId + ",";
					salaryDetailsDao.delete(new SalaryDetailsPk(detailsId));
				}
				logger.debug("The records with ID " + existingsalaryDetailsId + " are being deleted from SalaryDetails due to updating with the new details for the userId: " + userId + ", candidateId: " + candidateId);
			}
			if (salary == null) return null;
			List<SalaryHead> salaryHeads = salary.getSalaryHead();
			for (iterator = salaryHeads.iterator(); iterator.hasNext();){
				salHead = (SalaryHead) iterator.next();
				salaryConfigs = salHead.getSalaryConfigs();
				for (iterator1 = salaryConfigs.iterator(); iterator1.hasNext();){
					salConfigs = (SalaryConfigs) iterator1.next();
					salaryDetails = new SalaryDetails();
					if (userId != 0){
					if (candidateId != 0) salaryDetails.setCandidateId(candidateId);
					if (userId != 0) salaryDetails.setUserId(userId);
					salaryDetails.setFieldLabel(salHead.getName() + " - " + salConfigs.getComponent());
					salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
					salaryDetails.setMonthly(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getMonthlyAmount())));
					salaryDetails.setSum(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
					salaryDetails.setSalId(salConfigs.getId());
					if (salConfigs.getId() == 9) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.75f)));
					if (salConfigs.getId() == 10) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.25f)));
					if (salConfigs.getId() == 17) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salary.getRetentionBonus())));
					salaryDetailsDao.insert(salaryDetails);
			//		System.out.println(salaryDetails);
					}else{
						
						// Fixed Perdiem is not added for Candidates
						if(salConfigs.getId()!=16){
						if (candidateId != 0) salaryDetails.setCandidateId(candidateId);
						if (userId != 0) salaryDetails.setUserId(userId);
						salaryDetails.setFieldLabel(salHead.getName() + " - " + salConfigs.getComponent());
						salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
						salaryDetails.setMonthly(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getMonthlyAmount())));
						salaryDetails.setSum(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
						salaryDetails.setSalId(salConfigs.getId());
						if (salConfigs.getId() == 9) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.75f)));
						if (salConfigs.getId() == 10) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.25f)));
						if (salConfigs.getId() == 17) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salary.getRetentionBonus())));
						salaryDetailsDao.insert(salaryDetails);
						}
					}
				}
			}
		} catch (DaoException e){
			logger.debug("Exception occured in Accessing Dao " + e.getMessage());
			isExceptionflag = true;
		}
		if (isExceptionflag) result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.probleminsalary"));
		return result;
	}

	public ActionResult saveSalaryDetailsCtc(float plb, Salary salary, int userId, int candidateId, String saveType,PortalForm form) {
		FinanceInfo financeInfo = (FinanceInfo) form;
		ActionResult result = new ActionResult();
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		if (saveType.equalsIgnoreCase(SalaryDetailModel.TYPE_REPLICA)){
			salaryDetailsDao = SalaryDetailsDaoFactory.createReplica();
		} else{
			salaryDetailsDao = SalaryDetailsDaoFactory.create();
		}
		SalaryHead salHead;
		List<SalaryConfigs> salaryConfigs;
		SalaryConfigs salConfigs;
		SalaryDetails salaryDetails;
		Iterator<SalaryHead> iterator = null;
		Iterator<SalaryConfigs> iterator1 = null;
		boolean isExceptionflag = false;
		SalaryDetails[] existingSalaryDetails = null;
		try{
			if (userId != 0) existingSalaryDetails = salaryDetailsDao.findWhereUserIdEquals(userId);
			else if (candidateId != 0) existingSalaryDetails = salaryDetailsDao.findWhereCandidateIdEquals(candidateId);
			else logger.debug("Salary detail doesn't exists for userId: " + userId + ", candidateId: " + candidateId);
			if (existingSalaryDetails != null){
				String existingsalaryDetailsId = "";
				for (int i = 0; i < existingSalaryDetails.length; i++){
					int detailsId = existingSalaryDetails[i].getId();
					existingsalaryDetailsId += detailsId + ",";
					salaryDetailsDao.delete(new SalaryDetailsPk(detailsId));
				}
				logger.debug("The records with ID " + existingsalaryDetailsId + " are being deleted from SalaryDetails due to updating with the new details for the userId: " + userId + ", candidateId: " + candidateId);
			}
			if (salary == null) return null;
			List<SalaryHead> salaryHeads = salary.getSalaryHead();
			for (iterator = salaryHeads.iterator(); iterator.hasNext();){
				salHead = (SalaryHead) iterator.next();
				salaryConfigs = salHead.getSalaryConfigs();
				for (iterator1 = salaryConfigs.iterator(); iterator1.hasNext();){
					salConfigs = (SalaryConfigs) iterator1.next();
					salaryDetails = new SalaryDetails();
					if (userId != 0){
					if (candidateId != 0) salaryDetails.setCandidateId(candidateId);
					if (userId != 0) salaryDetails.setUserId(userId);
					salaryDetails.setFieldLabel(salHead.getName() + " - " + salConfigs.getComponent());
					salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
					//	salaryDetails.setTotal(Float.valueOf(salConfigs.getAnnualAmount()));
					
					
					salaryDetails.setMonthly(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getMonthlyAmount())));
					salaryDetails.setSum(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
					salaryDetails.setSalId(salConfigs.getId());
					salaryDetails.setCPLBperformance(financeInfo.getCPLBperformance());
					salaryDetails.setQPLBperformance(financeInfo.getQPLBperformance());
					
					if(financeInfo.getSalaryStack()==1&& salConfigs.getId() == 9){
						float QPLB = Float.valueOf(financeInfo.getQPLBperformance());
						if(financeInfo.getFixedPerdiemAmount()==null)
						{
							float fixedPerdiema =0;
							float perdiemb = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
							float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdiema+perdiemb;
							float	total1 = (Float.valueOf(total) * QPLB)/100;
						    salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
						}
						else
						{
						float fixedPerdiemc = Float.valueOf(financeInfo.getFixedPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
						float perdiemd = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
						float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdiemc+perdiemd;
					    float	total1 = (Float.valueOf(total) * QPLB)/100;
					    salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
					}}
					
					if(financeInfo.getSalaryStack()==1 && salConfigs.getId() == 10){
						float APLB = Float.valueOf(financeInfo.getCPLBperformance());
						if(financeInfo.getFixedPerdiemAmount()==null)
						{
							float fixedPerdieme =0;
							float perdiemf = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
							float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdieme+perdiemf;
					        float	total1 = (Float.valueOf(total) * APLB)/100;
					        salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
					}
						else
						{
							float fixedPerdiemg = Float.valueOf(financeInfo.getFixedPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
							float perdiemh = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
							float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdiemg+perdiemh;
							float	total1 = (Float.valueOf(total) * APLB)/100;
							salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
						}
					}
					if (salConfigs.getId() == 9&&financeInfo.getSalaryStack()!=1) {
						salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.75f)));}
					if (salConfigs.getId() == 10&&financeInfo.getSalaryStack()!=1) {
						salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.25f)));}
					if (salConfigs.getId() == 17){
						salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salary.getRetentionBonus())));}
					salaryDetails.setSalaryStack(financeInfo.getSalaryStack());
					salaryDetails.setTplp(plb);
					salaryDetails.setEsic(financeInfo.getEsic());
					salaryDetails.setActive(financeInfo.getActive());
					salaryDetails.setInActiveFromDate(financeInfo.getInActiveFromDate());
					salaryDetails.setInActiveToDate(financeInfo.getInActiveToDate());
					salaryDetailsDao.insert(salaryDetails);
					
				
					List<SalaryDetails> list = new ArrayList<SalaryDetails>();
					list.add(salaryDetails);
				
					}else{
						
						// Fixed Perdiem is not added for Candidates
						if(salConfigs.getId()!=16){
						if (candidateId != 0) salaryDetails.setCandidateId(candidateId);
						if (userId != 0) salaryDetails.setUserId(userId);
						salaryDetails.setFieldLabel(salHead.getName() + " - " + salConfigs.getComponent());
						salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
						salaryDetails.setMonthly(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getMonthlyAmount())));
						salaryDetails.setSum(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salConfigs.getAnnualAmount())));
						salaryDetails.setSalId(salConfigs.getId());
						if(financeInfo.getSalaryStack()==1&& salConfigs.getId() == 9){
							float QPLB = Float.valueOf(financeInfo.getQPLBperformance());
							if(financeInfo.getFixedPerdiemAmount()==null)
							{
								float fixedPerdiema =0;
								float perdiemb = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
								float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdiema+perdiemb;
								float	total1 = (Float.valueOf(total) * QPLB)/100;
							    salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
							}
							else
							{
							float fixedPerdiemc = Float.valueOf(financeInfo.getFixedPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
							float perdiemd = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
							float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdiemc+perdiemd;
						    float	total1 = (Float.valueOf(total) * QPLB)/100;
						    salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
						}}
						if(financeInfo.getSalaryStack()==1 && salConfigs.getId() == 10){
							float APLB = Float.valueOf(financeInfo.getCPLBperformance());
							if(financeInfo.getFixedPerdiemAmount()==null)
							{
								float fixedPerdieme =0;
								float perdiemf = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
								float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdieme+perdiemf;
						        float	total1 = (Float.valueOf(total) * APLB)/100;
						        salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
						}
							else
							{
								float fixedPerdiemg = Float.valueOf(financeInfo.getFixedPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
								float perdiemh = Float.valueOf(financeInfo.getPerdiemAmount())*SalaryDetailModel.YEAR_DAYS;
								float total = Float.valueOf(plb)+salary.getCtc()+fixedPerdiemg+perdiemh;
								float	total1 = (Float.valueOf(total) * APLB)/100;
								salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(total1)));
							}
						}
						
						if (salConfigs.getId() == 9&&financeInfo.getSalaryStack()!=1) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.75f)));
						if (salConfigs.getId() == 10&&financeInfo.getSalaryStack()!=1) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(plb * 0.25f)));
						if (salConfigs.getId() == 17) salaryDetails.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(salary.getRetentionBonus())));
						salaryDetails.setSalaryStack(financeInfo.getSalaryStack());
						salaryDetails.setTplp(plb);
						salaryDetails.setEsic(financeInfo.getEsic());
						salaryDetails.setActive(financeInfo.getActive());
						salaryDetails.setInActiveFromDate(financeInfo.getInActiveFromDate());
						salaryDetails.setInActiveToDate(financeInfo.getInActiveToDate());
						salaryDetailsDao.insert(salaryDetails);
						}
					}
				}
			}
		} catch (DaoException e){
			logger.debug("Exception occured in Accessing Dao " + e.getMessage());
			isExceptionflag = true;
		}
		if (isExceptionflag) result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.probleminsalary"));
		return result;
	}
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		SalaryDetails salaryDetails = (SalaryDetails) form;
		String fieldsArray[] = salaryDetails.getFields();
		String rowfields[] = null;
		String colfields[] = null;
		String singleField[] = null;
		String column[] = null;
		SalaryDetails[] salDetail = null;
		SalaryDetailsDao salaryDetailsDao = null;
		// fieldsArray will be as below each row data saved seperated by comma
		// fieldLabel=Basic~=~annual=300000~=~Monthly=25000~=~fieldType,
		// fieldLabel=HRA~=~annual=12000~=~Monthly=1000~=~fieldType,
		// fieldLabel=Medical Allowance~=~annual=120000~=~Monthly=1000~=~fieldType,
		// fieldLabel=Personal Allowance~=~annual=12000~=~Monthly=1000~=~fieldType
		int j = 0;
		for (String rows : fieldsArray){
			rowfields = rows.split(",");
			salDetail = new SalaryDetails[rowfields.length];
			for (String fields : rowfields)// fieldLabel=Basic~=~annual=300000~=~Monthly=25000~=~fieldType,
			{
				colfields = fields.split("~=~");
				SalaryDetails details = new SalaryDetails();
				for (String field : colfields)//fieldLabel=Basic
				{
					singleField = field.split(",");
					try{
						for (String singleField1 : singleField){
							column = singleField1.split("=");//fieldLabel /Basic
																//annual/300000
							if (column[0].equals("fieldLabel")){
								details.setFieldLabel(column[1]);
							} else if (column[0].equals("annual")){
								//details.setAnnual((Integer.parseInt(column[1])));
								details.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(column[1]));
								//details.setAnnual(DesEncrypterDecrypter.getInstance().encrypt("343444.55"));
							} else if (column[0].equals("monthly")){
								if (column.length > 1){
									//details.setMonthly((Integer.parseInt(column[1])));
									details.setMonthly(DesEncrypterDecrypter.getInstance().encrypt(column[1]));
									//details.setMonthly(DesEncrypterDecrypter.getInstance().encrypt("3434.50"));
								}
							} else if (column[0].equals("fieldType")){
								details.setFieldtype((column[1]));
							}
						}
					} catch (Exception e){
						logger.info("Failed to Save Salary Details");
						if (e.toString().contains("NumberFormatException")){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidsalary"));
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.probleminsalary"));
						}
						e.printStackTrace();
						return result;
					}
					if (salaryDetails.getUserId() > 0){
						details.setUserId(salaryDetails.getUserId() > 0 ? salaryDetails.getUserId() : 0);
					} else{
						details.setCandidateId(salaryDetails.getCandidateId());
					}
				}
				salDetail[j] = details;
				j++;
			}
			logger.info(salDetail);
		}
		salaryDetails.setId(salaryDetails.getId() != 0 ? 0 : 0);
		salaryDetails.setFields(null);
		if (salaryDetails.getuType() != null && salaryDetails.getuType().equals("replica")){
			salaryDetailsDao = SalaryDetailsDaoFactory.createReplica();
		} else{
			salaryDetailsDao = SalaryDetailsDaoFactory.create();
		}
		SalaryDetailsPk salaryDetailsPk = new SalaryDetailsPk();
		int i = 0;
		SalaryDetails Details[] = new SalaryDetails[salDetail.length];
		try{
			for (SalaryDetails sald : salDetail){
				SalaryDetails details = new SalaryDetails();
				if (sald.getFieldLabel().equals("Total")){
					//sald.setSum((sald.getAnnual()));
					//sald.setSum(integerPerm.encipher(sald.getAnnual()));
					sald.setSum((sald.getAnnual()));
				}
				salaryDetailsPk = salaryDetailsDao.insert(sald);
				salaryDetails.setId(salaryDetailsPk.getId());
				sald.setId(salaryDetailsPk.getId());
				details = sald;
				Details[i] = details;
				i++;
			}
		} catch (Exception e){
			logger.info("Failed to Update Salary Details");
			if (e.toString().contains("NumberFormatException")){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidsalary"));
			} else{
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.probleminsalary"));
			}
			e.printStackTrace();
		}
		request.setAttribute("salaryDetails", Details);
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		SalaryDetails salaryDetails = (SalaryDetails) form;
		String fieldsArray[] = salaryDetails.getFields();
		String rowfields[] = null;
		String colfields[] = null;
		String singleField[] = null;
		String column[] = null;
		SalaryDetails[] salDetail = null;
		SalaryDetailsPk salaryDetailsPk = new SalaryDetailsPk();
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		try{
			switch (UpdateTypes.getValue(form.getuType())) {
				case UPDATEFOREMPLOYEE:
					salaryDetailsDao.delete(new SalaryDetailsPk(salaryDetails.getId()));
					salaryDetails.setId(0);
					salaryDetailsDao.insert(salaryDetails);
					break;
				case UPDATE:
					//Integers max value is 2147483647 
					if (salaryDetails.getCandidateId() > 0 || salaryDetails.getUserId() > 0){
						SalaryDetails salaryDetails2[] = null;
						if (salaryDetails.getCandidateId() > 0){
							salaryDetails2 = salaryDetailsDao.findWhereCandidateIdEquals(salaryDetails.getCandidateId());
						} else if (salaryDetails.getUserId() > 0){
							salaryDetails2 = salaryDetailsDao.findWhereUserIdEquals(salaryDetails.getUserId());
						}
						List<Integer> salaryList = new ArrayList<Integer>();
						int j = 0;
						for (String rows : fieldsArray){
							rowfields = rows.split(",");
							salDetail = new SalaryDetails[rowfields.length];
							for (String fields : rowfields){// length =4
								SalaryDetails details = new SalaryDetails();
								colfields = fields.split("~=~");
								for (String field : colfields){
									singleField = field.split(",");
									for (String singleField1 : singleField){
										column = singleField1.split("=");
										if (column[0].equals("fieldLabel")){
											details.setFieldLabel(column[1]);
										} else if (column[0].equals("annual")){
											//details.setAnnual(Integer.parseInt(column[1]));
											details.setAnnual(DesEncrypterDecrypter.getInstance().encrypt(column[1]));
											//details.setAnnual(DesEncrypterDecrypter.getInstance().encrypt("343444.55"));
										} else if (column[0].equals("monthly")){
											if (column.length > 1){
												//details.setMonthly(Integer.parseInt(column[1]));
												details.setMonthly(DesEncrypterDecrypter.getInstance().encrypt(column[1]));
												//details.setAnnual(DesEncrypterDecrypter.getInstance().encrypt("343.55"));
											}
										} else if (column[0].equals("fieldType")){
											details.setFieldtype((column[1]));
										} else if (column[0].equals("id")){
											if (column.length > 1){
												details.setId(Integer.parseInt(column[1]));
											}
											if (salaryDetails.getsType() != null && salaryDetails.getsType().equals("replica")){
												details.setId(0);
											}
										}
									}
									if (salaryDetails.getUserId() > 0){
										int uid = salaryDetails.getUserId();
										details.setUserId(uid);
										details.setCandidateIdNull(true);
									} else{
										details.setCandidateId(salaryDetails.getCandidateId());
										details.setUserIdNull(true);
									}
								}
								salDetail[j] = details;
								salaryList.add(salDetail[j].getId());
								j++;
							}
							logger.info(salDetail);
						}
						SalaryDetails Details[] = new SalaryDetails[salDetail.length];
						if (salaryDetails.getsType() != null && salaryDetails.getsType().equals("replica")){
							salaryDetailsDao = SalaryDetailsDaoFactory.createReplica();
							salaryDetails2 = salaryDetailsDao.findWhereCandidateIdEquals(salaryDetails.getCandidateId());
						} else{
							salaryDetailsDao = SalaryDetailsDaoFactory.create();
						}
						for (SalaryDetails sal : salaryDetails2){
							int idsal = sal.getId();
							if (!salaryList.contains(idsal)){
								salaryDetailsPk.setId(idsal);
								salaryDetailsDao.delete(salaryDetailsPk);
							}
						}
						int count = 0;
						for (SalaryDetails sald : salDetail){
							SalaryDetails details1 = new SalaryDetails();
							if (sald.getId() > 0) salaryDetailsDao.delete(new SalaryDetailsPk(sald.getId()));
							sald.setId(0);
							if (sald.getFieldLabel().equals("Total")){
								sald.setSum((sald.getAnnual()));
							}
							salaryDetailsPk = salaryDetailsDao.insert(sald);
							sald.setId(salaryDetailsPk.getId());
							/*} else{
								if (sald.getFieldLabel().equals("Total")){
									sald.setSum((sald.getAnnual()));
								}
								salaryDetailsPk.setId(sald.getId());
								salaryDetailsDao.update(salaryDetailsPk, sald);
							}*/
							details1 = sald;
							Details[count] = details1;
							count++;
						}
						request.setAttribute("salaryDetails", Details);
					}
					break;
				default:
					break;
			}// switch ends
		} catch (Exception e){
			logger.info("Failed to Update Salary Details");
			if (e.toString().contains("NumberFormatException")){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidsalary"));
			} else{
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.probleminsalary"));
			}
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
