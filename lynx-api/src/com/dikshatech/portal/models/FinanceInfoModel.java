package com.dikshatech.portal.models;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.ReportStatus;
import com.dikshatech.beans.Salary;
import com.dikshatech.beans.SalaryConfigs;
import com.dikshatech.beans.SalaryHead;
import com.dikshatech.common.db.MyDBConnect;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.TDSUtility;
import com.dikshatech.jasper.JGenerator;
import com.dikshatech.jasper.JTemplates;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.DocumentMappingDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.FinanceInfoDao;
import com.dikshatech.portal.dao.FixedPerdiemDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.PerdiemInAdvanceDao;
import com.dikshatech.portal.dao.PerdiemMasterDataDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RetentionBonusDao;
import com.dikshatech.portal.dao.SalaryConfigDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.SalaryInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.DocumentMapping;
import com.dikshatech.portal.dto.DocumentMappingPk;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.FinanceInfo;
import com.dikshatech.portal.dto.FinanceInfoPk;
import com.dikshatech.portal.dto.FixedPerdiem;
import com.dikshatech.portal.dto.FixedPerdiemPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PerdiemInAdvance;
import com.dikshatech.portal.dto.PerdiemInAdvancePk;
import com.dikshatech.portal.dto.PerdiemMasterData;
import com.dikshatech.portal.dto.PerdiemMasterDataPk;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.RetentionBonus;
import com.dikshatech.portal.dto.RetentionBonusPk;
import com.dikshatech.portal.dto.SalaryConfig;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SalaryInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.FixedPerdiemDaoException;
import com.dikshatech.portal.exceptions.PerdiemInAdvanceDaoException;
import com.dikshatech.portal.exceptions.PerdiemMasterDataDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.DocumentMappingDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.FixedPerdiemDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PerdiemInAdvanceDaoFactory;
import com.dikshatech.portal.factory.PerdiemMasterDataDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusDaoFactory;
import com.dikshatech.portal.factory.SalaryConfigDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.SalaryInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.FinanceInfoDaoImpl;
import com.dikshatech.portal.mail.Attachements;

public class FinanceInfoModel extends ActionMethods {

	private static Logger		logger			= LoggerUtil.getLogger(FinanceInfoModel.class);
	private static final String	HEAD_INSURANCE	= "INSURANCE";
	private static final String	COMPONENT_QPLB	= "Quarterly Performance linked Bonus(QPLB)";
	private static final String	COMPONENT_APLB	= "Company performance linked Bonus(CPLB)";

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		FinanceInfo financeInfo = (FinanceInfo) form;
		FinanceInfoDao financeinfodao = FinanceInfoDaoFactory.create();
		FinanceInfoPk financeinfopk = new FinanceInfoPk();
		financeinfopk.setId(financeInfo.getId());
		try{
			financeinfodao.delete(financeinfopk);
		} catch (Exception e){
			logger.info("Failed to delete Profile Info");
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
		FinanceInfo financeInfoForm = (FinanceInfo) form;
		Login login = Login.getLogin(request);
		ActionResult result = new ActionResult();
		switch (ReceiveTypes.getValue(form.getrType())) {
		/**
		 * rType added for the calculation of the salary details when Ctc and perDiem is sent
		 */
			case CALCULATEDSALARY:
				float ctc = Float.valueOf(financeInfoForm.getCtcAmount());
				float perDiem = Float.valueOf(financeInfoForm.getPerdiemAmount() != null ? financeInfoForm.getPerdiemAmount() : "0");
				float fixedPerDiem = Float.valueOf(financeInfoForm.getFixedPerdiemAmount() != null ? financeInfoForm.getFixedPerdiemAmount() : "0");
				float retentionBonus = Float.valueOf(financeInfoForm.getRetentionBonus() != null ? financeInfoForm.getRetentionBonus() : "0");
				String plb = financeInfoForm.getPlb() != null ? financeInfoForm.getPlb() : "0";
				Salary salary = getCalculatedSalary(plb, ctc, perDiem, false,fixedPerDiem,retentionBonus,form);
				if (salary == null) return result;
				List<SalaryHead> salaryHeads = salary.getSalaryHead();
				SalaryHead insuranceHead = null;
				for (SalaryHead element : salaryHeads){
					if (element.getName().equalsIgnoreCase(SalaryDetailModel.HEAD_INSURANCE)) insuranceHead = element;
				}
				if (insuranceHead != null) salaryHeads.remove(insuranceHead);
				SalaryHead salaryHead = new SalaryHead();
				salaryHead.setName("Total Cost To Company (A+B+C+D) ");
				salaryHead.setAnnualSum(salary.getAnnualCTC());
				salaryHead.setMonthlySum(salary.getMonthlyCTC());
				// Reordering heads
				salaryHeads.add(salaryHead);
				// salaryHeads.add(insuranceHead);
				request.setAttribute("actionForm", salary);
				break;
			case RECEIVE:{
				
				int levelId=0;
				ProfileInfo profileInfo=null;
				UsersDao usersDao = UsersDaoFactory.create();
				ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
				LevelsDao levelsDao=LevelsDaoFactory.create();
				FinanceInfo financeInfo = (FinanceInfo) form;
				FinanceInfoDao financeinfodao = FinanceInfoDaoFactory.create();
				SalaryInfoDao salaryInfoDao=SalaryInfoDaoFactory.create();
				FinanceInfoPk financeinfopk = new FinanceInfoPk();
				Candidate candidate = new Candidate();
				CandidateDao c = CandidateDaoFactory.create();
				DocumentMapping documentMapping[] = null;
				DocumentsDao documentsDao = DocumentsDaoFactory.create();
				DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
				FinanceInfo financeInfoDto = new FinanceInfo();
				FinanceInfo financeInfoDto1 =null;
				
				
				try{
					if (financeInfo.getCandidateId() > 0){
						candidate = c.findByPrimaryKey(financeInfo.getCandidateId());
					    profileInfo=profielInfoDao.findByPrimaryKey(candidate.getProfileId());
						financeinfopk.setId(candidate.getFinancialId());
						financeInfoDto1=financeinfodao.findByPrimaryKey(financeinfopk);

						if(financeInfoDto1!=null) financeInfoDto=insertToFinanceInfo(financeInfoDto1);

						financeInfoDto.setCandidateId(candidate.getId());
						documentMapping = mappingDao.findWhereFinanceIdEquals(candidate.getFinancialId());
					} else if (financeInfo.getUserId() != null && financeInfo.getUserId() > 0){
						Users user = new Users();
						user = usersDao.findByPrimaryKey(financeInfo.getUserId());
						profileInfo=profielInfoDao.findByPrimaryKey(user.getProfileId());
						financeinfopk.setId(user.getFinanceId());
						financeInfoDto1=financeinfodao.findByPrimaryKey(financeinfopk);

						if(financeInfoDto1!=null) financeInfoDto=insertToFinanceInfo(financeInfoDto1);

						financeInfoDto.setUserId(user.getId());
						documentMapping = mappingDao.findWhereFinanceIdEquals(financeinfopk.getId());
					}
		
					if (financeInfo.getCandidateId() > 0){
						SalaryInfo[] salaryInfo=salaryInfoDao.findWhereBasicEquals(candidate.getId());
						if(salaryInfo!=null){
							for(SalaryInfo info:salaryInfo){
								financeInfoDto.setRetentionBonus(info.getRetentionBonus());
								financeInfoDto.setJoiningBonus(info.getJoiningBonusAmount());
								financeInfoDto.setPerdiemOffered(info.getPerdiemOffered());
							}
							
						}
						
					}
					else if(financeInfo.getUserId() != null && financeInfo.getUserId() > 0){
						SalaryInfo[] salaryInfo=salaryInfoDao.findWhereuserIdEquals(financeInfo.getUserId());
						if(salaryInfo!=null){
							for(SalaryInfo info:salaryInfo){
								financeInfoDto.setRetentionBonus(info.getRetentionBonus());
								financeInfoDto.setJoiningBonus(info.getJoiningBonusAmount());
								financeInfoDto.setPerdiemOffered(info.getPerdiemOffered());
								//System.out.println("Perdiem Offered in Financial Info :: Userid"+info.getPerdiemOffered());
								//System.out.println("Perdiem Offered in Financial Info :: financeInfoDto  Userid"+financeInfoDto.getPerdiemOffered());
							}
							
						}
					}
					
					
					//				financeInfoDto = financeinfodao.findByPrimaryKey(financeinfopk);
					if (financeInfo.getUserId() != null && financeInfo.getUserId() > 0){
						if (!ModelUtiility.hasModulePermission(login, 17) && !ModelUtiility.hasModulePermission(login, 18) && financeInfo.getUserId().intValue() != login.getUserId().intValue()){
							request.setAttribute("actionForm", "Access denied!!!! Mail has been sent to RMG about your activity.");
							break;
						}
						/**
						 * Commented/changed to return the salary details according to the new salary structure...
						 */
					// SalaryDetails salaryDetailBean[]=salaryDetailsDao.findWhereUserIdEquals(financeInfo.getUserId());
						SalaryDetailModel sdm = new SalaryDetailModel();
						Salary salaryStack = sdm.getSalarynew(financeInfo.getUserId(), true, SalaryDetailModel.TYPE_NORMAL);
						if (salaryStack != null){
							if (financeInfoDto == null){
								financeInfoDto = new FinanceInfo();
							}
							financeInfoDto.setCtc(new DecimalFormat("0.00").format(salaryStack.getAnnualCTC()));
							for (SalaryHead sal : salaryStack.getSalaryHead()){
								try{
									if (sal.getName().equalsIgnoreCase(SalaryDetailModel.HEAD_INSURANCE)){
										salaryStack.getSalaryHead().remove(sal);
										break;
									}
								} catch (Exception e){}
							}
							financeInfoDto.setSalary(new Salary[] { salaryStack });
						}
						// salary in advance...
						try{
							float salMonth=0;
							String[] salInAdv = financeinfodao.getSalaryInAdv(financeInfo.getUserId());
							if (salInAdv != null && salInAdv.length == 7){
								financeInfoDto.setSalaryInAdvTot(salInAdv[1]);
								financeInfoDto.setSalaryInAdvMon(salInAdv[2]);
								financeInfoDto.setSalaryInNoOfMonths(salInAdv[3]);
								financeInfoDto.setSalaryInAdvBalance(salInAdv[4]);
								financeInfoDto.setSalaryInAdvPaid(salInAdv[5]);
								// new code added here for status
								financeInfoDto.setSalaryAdvToBeDeducted(salInAdv[6]);
								if(Float.valueOf(salInAdv[4])>0 && Float.valueOf(salInAdv[2])>0){
									salMonth=Float.valueOf(salInAdv[4])/Float.valueOf(salInAdv[2]);	
								}
								financeInfoDto.setSalaryPaidMonth(Math.round(salMonth)+"");
							}
						} catch (Exception e){
							logger.error("Unable to receive Salary in advance for user:" + financeInfo.getUserId() + " Exception:" + e.getMessage());
						}
						//-------------------------------------------------//
						// new code added here for travel in advance by venkat
						try {
							float travelMonth=0;
							String[] travelInAdv = financeinfodao.getTravelInAdv(financeInfo.getUserId());
							if(travelInAdv != null && travelInAdv.length == 7) {
								financeInfoDto.setTravelInAdvTot(travelInAdv[1]);
								financeInfoDto.setTravelInAdvMon(travelInAdv[2]);
								financeInfoDto.setTravelInNoOfMonths(travelInAdv[3]);
								financeInfoDto.setTravelInAdvBalance(travelInAdv[4]);
								financeInfoDto.setTravelInAdvPaid(travelInAdv[5]);
								financeInfoDto.setTravelDeductionStatus(travelInAdv[6]);
								if(Float.valueOf(travelInAdv[4])>0 && Float.valueOf(travelInAdv[2])>0){
									
									travelMonth=Float.valueOf(travelInAdv[4])/Float.valueOf(travelInAdv[2]);	
									
								}
								financeInfoDto.setTravelPaidMonth(Math.round(travelMonth)+"");
								
							}
							
							
						}catch (Exception e) {
							
							logger.error("Unable to receive Travel in Advance for user:" + financeInfo.getUserId() + "Exceptin:" + e.getMessage());
						}
						
						// travel av
						//-------------------------------------------------//
						
						try{
							FixedPerdiem fixedPerdiem = FixedPerdiemDaoFactory.create().findByPrimaryKey(financeInfo.getUserId());
							if (fixedPerdiem != null){
								financeInfoDto.setFixedPerdiemAmount(fixedPerdiem.getPerdiem());
								financeInfoDto.setFixedPerdiemCurrencyType(fixedPerdiem.getCurrencyType());
								financeInfoDto.setFixedPerdiemFrom(fixedPerdiem.getPerdiemFrom());
								financeInfoDto.setFixedPerdiemTo(fixedPerdiem.getPerdiemTo());
							}
						} catch (Exception e){
							logger.error("Unable to receive Fixed Perdiem for user:" + financeInfo.getUserId(), e);
						}
						try{
							float month=0;
							PerdiemInAdvance salInAdv = PerdiemInAdvanceDaoFactory.create().findByPrimaryKey(financeInfo.getUserId());
							if (salInAdv != null){
								financeInfoDto.setPerdiemInAdvTot(salInAdv.getTotal());
								financeInfoDto.setPerdiemInAdvMon(salInAdv.getMonthly());
								financeInfoDto.setPerdiemTerms(salInAdv.getTerms());
								financeInfoDto.setPerdiemInNoOfMonths(salInAdv.getNumber_of_months());
								financeInfoDto.setPerdiemInBalTot(salInAdv.getAdvanceBal());
								if(Float.valueOf(salInAdv.getAdvanceBal())>0 && Float.valueOf(salInAdv.getMonthly())>0 ){
									month=Float.valueOf(salInAdv.getAdvanceBal())/Float.valueOf(salInAdv.getMonthly());	
								}
								financeInfoDto.setPaidMonth(Math.round(month)+"");
							}
						} catch (Exception e){
							logger.error("Unable to receive Salary in advance for user:" + financeInfo.getUserId() + " Exception:" + e.getMessage());
						}
					}
					if (documentMapping != null && documentMapping.length > 0){
						Documents[] documentArr = new Documents[documentMapping.length];
						int i = 0;
						for (DocumentMapping tempDocMap : documentMapping){
							Documents newDocument = new Documents();
							Documents documentsFromDb = documentsDao.findByPrimaryKey(tempDocMap.getDocumentId());
							newDocument.setId(documentsFromDb.getId());
							String filename = documentsFromDb.getFilename();
							String extension = filename.substring(filename.lastIndexOf(".") + 1);
							String fileName1 = filename.substring(4, filename.lastIndexOf("~"));
							newDocument.setFilename(fileName1 + "." + extension);
							newDocument.setDoctype(documentsFromDb.getDoctype());
							newDocument.setDescriptions(documentsFromDb.getDescriptions());
							documentArr[i] = newDocument;
							i++;
						}
						financeInfoDto.setDocumentArr(documentArr);
					}
					if (financeInfo.getUserId() != null){
						int id = financeInfo.getUserId();
						if (financeInfoDto != null){
							if (login.getUserId() == id){
								financeInfoDto.setSalaryEdit(false);
							} else{
								financeInfoDto.setSalaryEdit(true);
							}
						}
					}
					SalaryDetails[] salarydetails=null;
					
					SalaryDetailsDao salarydetailsdao=SalaryDetailsDaoFactory.create();
					salarydetails=salarydetailsdao.findbyuserid(financeInfo.getUserId());
					for(SalaryDetails k:salarydetails)
						
					{
						if(k.getSalaryStack()!=1)
						{
							financeInfoDto.setCPLBperformance(salarydetails[0].getCPLBperformance());
							financeInfoDto.setQPLBperformance(salarydetails[0].getQPLBperformance());
							financeInfoDto.setSalaryStack(k.getSalaryStack());
						}
						else
						{	financeInfoDto.setCPLBperformance(salarydetails[0].getCPLBperformance());
							financeInfoDto.setQPLBperformance(salarydetails[0].getQPLBperformance());
							financeInfoDto.setSalaryStack(k.getSalaryStack());
						}
					
					}
					
					
					
			// Added Retention Bonus code to be retrieved
					RetentionBonus[] RetenBonus=null;
					RetentionBonusDao retentionDao=RetentionBonusDaoFactory.create();
					RetenBonus=retentionDao.findWhereUserIdEquals(financeInfo.getUserId());
					if(RetenBonus!=null && RetenBonus.length>0){
						financeInfoDto.setRetentionBonus(RetenBonus[0].getAmount());
						financeInfoDto.setRetentionInstallments(RetenBonus[0].getRetentionInstallments()+"");
						financeInfoDto.setRetentionStartDate(RetenBonus[0].getRetentionStartDate());
					}
					
			// Added Variable Perdiem code to be retrieved
					PerdiemMasterData[] perdiemMaster=null;
					PerdiemMasterDataDao perdiemMasterDataDao=PerdiemMasterDataDaoFactory.create();
					perdiemMaster=perdiemMasterDataDao.findWhereUserIdEquals(financeInfo.getUserId());
					if(perdiemMaster!=null && perdiemMaster.length>0){
						perdiemMaster[0].setPerdiem(DesEncrypterDecrypter.getInstance().decrypt(perdiemMaster[0].getPerdiem()));
						financeInfoDto.setPerdiemMasterData(perdiemMaster[0]);
					}
					
					request.setAttribute("actionForm", financeInfoDto);
				} catch (Exception e){
					logger.info("Failed to receive single Finance Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					e.printStackTrace();
				}
			}
				break;
			case RECEIVEPERDIEM:{
				request.setAttribute("actionForm", "");
				try{
					PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create();
					PerdiemMasterData[] perdiemMasterDto = perdiemMasterDao.findWhereUserIdEquals(financeInfoForm.getPerdiemUserId());
					if (perdiemMasterDto != null & perdiemMasterDto.length > 0){
						perdiemMasterDto[0].setPerdiem(DesEncrypterDecrypter.getInstance().decrypt(perdiemMasterDto[0].getPerdiem()));
						request.setAttribute("actionForm", perdiemMasterDto[0]);
					}
				} catch (Exception ex){
					logger.info("Failed To Teceive Perdiem Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("perdiem.receive.failed"));
					ex.printStackTrace();
				}
				break;
			}
			case RECEIVEREPORT:{
				FinanceInfo financeInfo = (FinanceInfo) form;
				String monthId = financeInfo.getMonth();
				Calendar calendar = Calendar.getInstance();
				String newMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				if (newMonth.length() == 1) newMonth = "0" + newMonth;
				String currMonth = String.valueOf(calendar.get(Calendar.YEAR)) + newMonth;
				if (Integer.valueOf(monthId) > Integer.valueOf(currMonth)){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.incorrectDateReport"));
				}
				JGenerator jGenerator = new JGenerator();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("MONTH_ID", monthId);
				boolean statusReport = jGenerator.generateFile(JGenerator.SALARY, "SalaryDetails_" + monthId + ".pdf", JTemplates.SALARY, map);
				ReportStatus s = new ReportStatus();
				s.setStatus(statusReport);
				s.setFilename("SalaryDetails_" + monthId + ".pdf");
				request.setAttribute("actionForm", s);
			}
				break;
		
		}
			
				return result;
			
			
			
		}

	public FinanceInfo insertToFinanceInfo(FinanceInfo finance){
		FinanceInfo financeInfo=new FinanceInfo();
		financeInfo.setPrimBankAccNo(finance.getPrimBankAccNo());
		financeInfo.setPrimBankName(finance.getPrimBankName());
		financeInfo.setPanNo(finance.getPanNo());
		financeInfo.setPfAccNo(finance.getPfAccNo());
		financeInfo.setSecBankAccNo(finance.getSecBankAccNo());
		financeInfo.setSecBankName(finance.getSecBankName());
		return finance;
	}
	
	
	
	/**
	 * Method for calculating the salary when CTC and PerDiem given
	 * 
	 * @param ctc
	 * @param perDiem
	 * @return
	 */
	public Salary getCalculatedSalary(String plbString, float ctc, float perDiem, boolean isSave) {
		if (ctc == 0) return null;
		final String CTC = "CTC";
		final String PERDIEM = "PERDIEM";
		float plb = 0.0f;
		if (plbString != null && !plbString.equalsIgnoreCase("")) plb = Float.valueOf(plbString);
		SalaryConfigDao salaryConfigDao = SalaryConfigDaoFactory.create();
		Connection connection = MyDBConnect.getConnect();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Salary salary = null;
		try{
			stmt = connection.prepareStatement("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG");
			resultSet = stmt.executeQuery();
			// SalaryConfig[] heads = salaryConfigDao.findByDynamicSelect("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG", null);
			salary = new Salary();
			List<SalaryHead> salHeadsList = new ArrayList<SalaryHead>();
			skipConfig: while (resultSet.next()){
				List<SalaryConfigs> salConfigs = new ArrayList<SalaryConfigs>();
				SalaryHead salHeads = new SalaryHead();
				SalaryConfigs tempconfigs;
				float annualSum = 0;
				float monthlySum = 0.0f;
				SalaryConfig[] eachItems = salaryConfigDao.findWhereHeadEquals(resultSet.getString(1));
				for (SalaryConfig eachItem : eachItems){
					tempconfigs = new SalaryConfigs();
					tempconfigs.setId(eachItem.getId());
					tempconfigs.setAutoCalc(eachItem.getAutoCalc());
					tempconfigs.setComponent(eachItem.getComponent());
					tempconfigs.setComponentOrder(eachItem.getComponentOrder());
					tempconfigs.setValue(eachItem.getValue());
					tempconfigs.setValueType(eachItem.getValueType());
					if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(CTC) != -1){
						tempconfigs.setAnnualAmount(ctc);
						tempconfigs.setMonthlyAmount(ctc / 12);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(PERDIEM) != -1){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount(eachItem.getValue());
						tempconfigs.setMonthlyAmount(eachItem.getValue() / 12);
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount((ctc * eachItem.getValue()) - 9600);
						tempconfigs.setMonthlyAmount(tempconfigs.getAnnualAmount() / 12);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERDIEM"))){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else{
						logger.error("Unable to calculate the salary component as the configuration is incorrect for " + eachItem.getHead() + "  " + eachItem.getComponent());
					}
					if (FinanceInfoModel.isLeaveMonthly(eachItem.getHead(), eachItem.getComponent())) tempconfigs.setMonthlyAmount(0.0f);
					if (plbString != null && !plbString.equalsIgnoreCase("")){
						if (eachItem.getId() == 9){
							tempconfigs.setAnnualAmount(plb * 0.75f);
							tempconfigs.setMonthlyAmount(0);
						} else if (eachItem.getId() == 10){
							tempconfigs.setAnnualAmount(plb * 0.25f);
							tempconfigs.setMonthlyAmount(0);
						}
					}
					annualSum += tempconfigs.getAnnualAmount();
					monthlySum += tempconfigs.getMonthlyAmount();
					salConfigs.add(tempconfigs);
				}
				salHeads.setSalaryConfigs(salConfigs);
				salHeads.setName(resultSet.getString(1));
				salHeads.setAnnualSum(Math.round(annualSum));
				// salHeads.setMonthlySum(Math.round(annualSum/12));
				salHeads.setMonthlySum(Math.round(monthlySum));
				salHeadsList.add(salHeads);
			}
			salary.setSalaryHead(salHeadsList);
			float totalCTC = 0, monthlyCtc = 0.0f;
			for (SalaryHead salaryHead : salHeadsList){
				if (!salaryHead.getName().equalsIgnoreCase(SalaryDetailModel.HEAD_INSURANCE)){
					totalCTC += salaryHead.getAnnualSum();
					monthlyCtc += salaryHead.getMonthlySum();
				} else logger.debug(salaryHead + " not applicable for total CTC calculation.");
			}
			totalCTC = Math.round(totalCTC);
			salary.setCtc(ctc);
			salary.setPerDiem(perDiem);
			salary.setFixed(ctc);
			salary.setFBP(ctc);
			salary.setTblb(Float.valueOf(plbString));
			salary.setAnnualCTC(totalCTC);
			// salary.setMonthlyCTC(Math.round(totalCTC/12));
			salary.setMonthlyCTC(monthlyCtc);
			salary.setTotCTC(totalCTC);
		} catch (Exception e){
			logger.debug("Exception thrown in Calculate the salary details in FinanceInfoModel " + e.getMessage());
		} finally{
			try{
				MyDBConnect.close(connection);
			} catch (SQLException e){
				logger.debug("Unable to close the connection opened for querying the heads of salary " + e.getMessage());
			}
		}
		return salary;
	}

	/**
	 * @author supriya.bhike
	 *         Decrypt method for decrypting salary detail fields annul,monthly and sum
	 */
	public SalaryDetails[] decrypt(SalaryDetails[] salaryDetails) {
		SalaryDetails newSalarydetails[] = new SalaryDetails[salaryDetails.length];
		try{
			int i = 0;
			for (SalaryDetails salaryDetails2 : salaryDetails){
				if (salaryDetails2.getAnnual() != null && !salaryDetails2.getAnnual().equals("0")){
					// salaryDetails2.setAnnual(integerPerm.decipher(salaryDetails2.getAnnual()));
					salaryDetails2.setAnnual(DesEncrypterDecrypter.getInstance().decrypt(salaryDetails2.getAnnual()));
				}
				if (salaryDetails2.getMonthly() != null && !salaryDetails2.getAnnual().equals("0")){
					salaryDetails2.setMonthly(DesEncrypterDecrypter.getInstance().decrypt(salaryDetails2.getMonthly()));
				}
				if (salaryDetails2.getSum() != null && !salaryDetails2.getAnnual().equals("0")){
					salaryDetails2.setSum(DesEncrypterDecrypter.getInstance().decrypt(salaryDetails2.getSum()));
				}
				newSalarydetails[i] = salaryDetails2;
				i++;
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newSalarydetails;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		FinanceInfo financeInfo = (FinanceInfo) form;
		switch (SaveTypes.getValue(financeInfo.getsType())) {
			case SAVEPERDIEM:{
				ProfileInfo employeeProfileInfo = null;
				try{
					employeeProfileInfo = ProfileInfoDaoFactory.create().findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE ID=?) ", new Object[] { financeInfo.getPerdiemUserId() })[0];
					savePerdiemMasterData(financeInfo);
				} catch (Exception ex){
					logger.error("PERDIEM_MASTER_DATA INSERTION ERROR : Failed to insert into PerdiemMasterData for employee : " + employeeProfileInfo.getFirstName() + " " + employeeProfileInfo.getLastName(), ex);
				}
			}
				break;
			case SAVE:{
				FinanceInfoDao financeinfodao = FinanceInfoDaoFactory.create();
				Login login = Login.getLogin(request);
				FinanceInfoPk financeinfopk = new FinanceInfoPk();
				financeInfo.setId(financeInfo.getId() != 0 ? 0 : 0);
				CandidateModel candidateModel = new CandidateModel();
				Candidate candidate = new Candidate();
				CandidateDao c = CandidateDaoFactory.create();
				try{
					financeInfo.setModifiedBy(login.getUserId());
					financeinfopk = financeinfodao.insert(financeInfo);
					financeInfo.setId(financeinfopk.getId());
					/**
					 * Insert in candidate table the relevant id
					 */
					if (financeInfo.getCandidateId() > 0){
						candidate = c.findByPrimaryKey(financeInfo.getCandidateId());
						candidate.setFinancialId(financeinfopk.getId());
						candidate.setuType("UPDATECANDIDATE");
						candidateModel.update(candidate, request);
					}
					
					
					/**
					 * save salary
					 */
					// New code added to save the salary details according to
					// the salary restructure
					int userId = financeInfo.getUserId();
					if (userId > 0){
						float ctc = Float.valueOf(financeInfo.getCtcAmount());
						float perDiem = Float.valueOf(financeInfo.getPerdiemAmount());
						float retentionBonus = Float.valueOf(financeInfo.getRetentionBonus() != null && !financeInfo.getRetentionBonus().equalsIgnoreCase("") ? financeInfo.getRetentionBonus() : "0");
						float fixedPerdiem;
						String plb = financeInfo.getPlb();
						if(financeInfo.getFixedPerdiemAmount()!=null){
						fixedPerdiem=Float.valueOf(financeInfo.getFixedPerdiemAmount());}
						else  fixedPerdiem=0;
						int candidateId = 0;
						float balanceAmt=0;
						
					// Saving total amt,balance amt and paid amt in Salary In Advance	
						float paidAmt=0;
						if(financeInfo.getSalaryInAdvTot()!=null && !(financeInfo.getSalaryInAdvTot().equalsIgnoreCase(""))){
							 // new field is added here (SalaryAdvToBeDeducted) by venkat
							financeinfodao.updateSalaryInAdv(new String[] { userId + "", financeInfo.getSalaryInAdvTot(), financeInfo.getSalaryInAdvMon(), financeInfo.getSalaryInNoOfMonths(),financeInfo.getSalaryInAdvTot(),"0.0",financeInfo.getSalaryAdvToBeDeducted()});
						}
						
						Salary salary = new FinanceInfoModel().getCalculatedSalary(plb, ctc, perDiem, true,fixedPerdiem,retentionBonus,form);
						
						//			Salary salaryCtc = new FinanceInfoModel().getCalculatedSalaryCtc(plb, ctc, perDiem, true,fixedPerdiem,retentionBonus);
						
						SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
						//		SalaryDetailModel salaryDetailModelCtc = new SalaryDetailModel();
						
				//		result = salaryDetailModel.saveSalaryDetails(Float.valueOf(plb), salary, userId, candidateId, SalaryDetailModel.TYPE_NORMAL);
						
									result = salaryDetailModel.saveSalaryDetailsCtc(Float.valueOf(plb), salary, userId, candidateId, SalaryDetailModel.TYPE_NORMAL,form);
						new TDSUtility().reCalculate(userId);
						updateFixedPerdiem(financeInfo);
						updatePerdiemInAdvance(financeInfo);
					} else logger.debug("Unable to save salary details as user id is " + userId);
					
					// new code added to save Travel Advance by venkat
					
					int userId1 = financeInfo.getUserId();
					if(userId1 > 0) {
						float paidAmt=0;
						if(financeInfo.getTravelInAdvTot() !=null && !(financeInfo.getTravelInAdvTot().equalsIgnoreCase(""))) {
						financeinfodao.updateTravelInAdv(new String[] {userId1 + "",financeInfo.getTravelInAdvTot(),financeInfo.getTravelInAdvMon(),financeInfo.getTravelInNoOfMonths(),financeInfo.getTravelInAdvTot(),"0.0",financeInfo.getTravelDeductionStatus()});
						}
						
					}else logger.debug("Unable to save travel details as user id is " + userId1);
					
					
					
					
					
					/**
					 * add in User Table
					 */
					if (financeInfo.getUserId() != null && financeInfo.getUserId() > 0){
						UsersPk usersPk = new UsersPk();
						UsersDao usersDao = UsersDaoFactory.create();
						Users users = usersDao.findByPrimaryKey(financeInfo.getUserId());
						users.setFinanceId(financeinfopk.getId());
						usersPk.setId(users.getId());
						usersDao.update(usersPk, users);
					}
					/**
					 * Insert in document mapping table
					 */
					if (financeInfo.getFiles() != null){
						String[] files = financeInfo.getFiles().split("~");
						DocumentMapping documentMapping = new DocumentMapping();
						DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
						if (files.length > 0) for (String fileIds : files){
							documentMapping.setDocumentId(Integer.parseInt(fileIds));
							documentMapping.setFinanceId(financeinfopk.getId());
							documentMapping.setId(documentMapping.getId() != 0 ? 0 : 0);
							mappingDao.insert(documentMapping);
						}
					}
					// save perdiem master data
					if (financeInfo.getPerdiemAmount() != null && financeInfo.getPerdiemAmount().length() > 0){
						savePerdiemMasterData(financeInfo);
					}
					
					if(!(financeInfo.getSalaryStack()==1))
					{
						if (financeInfo.getRetentionBonus() != null && financeInfo.getRetentionBonus().length() > 0 &&  !financeInfo.getRetentionBonus().equalsIgnoreCase("")){
						saveRetetionBonus(financeInfo);
					}}
					
				} catch (Exception e){
					logger.info("Failed to save Profile Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					e.printStackTrace();
				}
			}
				break;
		}
		return result;
	}

	private void saveRetetionBonus(FinanceInfo financeInfo) {
		RetentionBonusPk pk=new RetentionBonusPk();
		RetentionBonus rBonus=new RetentionBonus();
		RetentionBonusDao retentionDao=RetentionBonusDaoFactory.create();
		RetentionBonus RetenBonus[]=null;
		try {
			RetenBonus=retentionDao.findWhereUserIdEquals(financeInfo.getUserId());
			if(RetenBonus!=null && RetenBonus.length>0){
				rBonus=RetenBonus[0];
				pk.setId(rBonus.getId());
				rBonus.setUserId(financeInfo.getUserId());
				rBonus.setAmount(financeInfo.getRetentionBonus());
				rBonus.setRetentionInstallments(Integer.parseInt(financeInfo.getRetentionInstallments()));
				rBonus.setRetentionStartDate(financeInfo.getRetentionStartDate());
				retentionDao.update(pk, rBonus);
				
			}else{	
		rBonus.setUserId(financeInfo.getUserId());
		rBonus.setAmount(financeInfo.getRetentionBonus());
		rBonus.setRetentionInstallments(Integer.parseInt(financeInfo.getRetentionInstallments()));
		rBonus.setRetentionStartDate(financeInfo.getRetentionStartDate());
		retentionDao.insert(rBonus);
			}
		} catch (RetentionBonusDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteFromRetetionBonus(FinanceInfo financeInfo) throws PerdiemMasterDataDaoException {
		RetentionBonusDao retentionDao=RetentionBonusDaoFactory.create();
		int userId = financeInfo.getUserId();
		if (userId > 0){
			RetentionBonus rBonus[];
			try {
				rBonus = retentionDao.findWhereUserIdEquals(userId);
		if (rBonus != null && rBonus.length > 0){
			retentionDao.delete(new RetentionBonusPk(rBonus[0].getId()));
		}
			} catch (RetentionBonusDaoException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}
	}

	private void updateFixedPerdiem(FinanceInfo financeInfo) throws FixedPerdiemDaoException {
		try{
			if (financeInfo.getUserId() <= 0) throw new FixedPerdiemDaoException("user Id is 0 to update fixed perdiem");
			FixedPerdiemDao perdiemDao = FixedPerdiemDaoFactory.create();
			if (financeInfo.getFixedPerdiemAmount() == null || Double.parseDouble((financeInfo.getFixedPerdiemAmount())) <= 0){
				perdiemDao.delete(new FixedPerdiemPk(financeInfo.getUserId()));
				return;
			}
			FixedPerdiem perdiem = perdiemDao.findByPrimaryKey(financeInfo.getUserId());
			if (perdiem != null){
				perdiem.setValues(financeInfo.getFixedPerdiemAmount(), financeInfo.getFixedPerdiemFrom(), financeInfo.getFixedPerdiemTo(), financeInfo.getFixedPerdiemCurrencyType());
				perdiemDao.update(perdiem.createPk(), perdiem);
			} else{
				perdiemDao.insert(new FixedPerdiem(financeInfo.getUserId(), financeInfo.getFixedPerdiemAmount(), financeInfo.getFixedPerdiemFrom(), financeInfo.getFixedPerdiemTo(), financeInfo.getFixedPerdiemCurrencyType()));
			}
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void updatePerdiemInAdvance(FinanceInfo financeInfo) throws PerdiemInAdvanceDaoException {
		if (financeInfo.getUserId() <= 0)
			throw new PerdiemInAdvanceDaoException("user Id is 0 to update fixed perdiem");
		float total = 0, monthly = 0, paid = 0, balance = 0;
		try {
			PerdiemInAdvanceDao perdiemDao = PerdiemInAdvanceDaoFactory.create();
			if (financeInfo.getPerdiemInAdvTot() == null
					|| Double.parseDouble((financeInfo.getPerdiemInAdvTot())) <= 0) {
				perdiemDao.delete(new PerdiemInAdvancePk(financeInfo.getUserId()));
				return;
			}

			PerdiemInAdvance perdiem = perdiemDao.findByPrimaryKey(financeInfo.getUserId());
			if (perdiem != null) {
				total = Float.parseFloat(financeInfo.getPerdiemInAdvTot());
				if (total > 0) {
					if (financeInfo.getPerdiemInAdvPaid() != null)
						paid = Float.parseFloat(financeInfo.getPerdiemInAdvPaid());
					balance = total - paid;
				}

				perdiem.setValues(financeInfo.getPerdiemInAdvTot(), financeInfo.getPerdiemInAdvMon(),
						financeInfo.getPerdiemInNoOfMonths(), financeInfo.getPerdiemTerms(), String.valueOf(balance),
						String.valueOf(paid));
				perdiemDao.update(perdiem.createPk(), perdiem);
			} else {
				total = Float.parseFloat(financeInfo.getPerdiemInAdvTot());
				if (total > 0) {
					if (financeInfo.getPerdiemInAdvPaid() != null)
						paid = Float.parseFloat(financeInfo.getPerdiemInAdvPaid());

					balance = total - paid;
				}
				perdiemDao.insert(new PerdiemInAdvance(financeInfo.getUserId(), financeInfo.getPerdiemInAdvTot(),
						financeInfo.getPerdiemInAdvMon(), financeInfo.getPerdiemInNoOfMonths(),
						financeInfo.getPerdiemTerms(), String.valueOf(balance), String.valueOf(paid)));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		FinanceInfo financeInfo = (FinanceInfo) form;
		switch (UpdateTypes.getValue(financeInfo.getuType())) {
			case UPDATE:{
				FinanceInfoDao financeinfodao = FinanceInfoDaoFactory.create();
				FinanceInfoPk financeinfopk = new FinanceInfoPk();
				financeinfopk.setId(financeInfo.getId());
				Candidate candidate = new Candidate();
				CandidateDao c = CandidateDaoFactory.create();
				String files[] = null;
				boolean contains = false;
				DocumentMapping documentMapping = new DocumentMapping();
				DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
				DocumentMappingPk docMapPk = new DocumentMappingPk();
				try{
					if (financeInfo.getCandidateId() > 0){
						candidate = c.findByPrimaryKey(financeInfo.getCandidateId());
						financeinfopk.setId(candidate.getFinancialId());
						financeInfo.setId(candidate.getFinancialId());
					} else{
						financeinfopk.setId(financeInfo.getId());
					}
					/**
					 * Update finance info
					 */
					financeInfo.setModifiedBy(login.getUserId());
					financeinfodao.update(financeinfopk, financeInfo);
					/***
					 * update salary details if exists
					 */
					Integer userId = financeInfo.getUserId();
					if (userId != null && userId > 0){
						int candidateId = 0;
						float ctc = Float.valueOf(financeInfo.getCtcAmount());
						float perDiem = Float.valueOf(financeInfo.getPerdiemAmount() != null ? financeInfo.getPerdiemAmount() : "0");
						float fixedPerDiem = Float.valueOf(financeInfo.getFixedPerdiemAmount() != null ? financeInfo.getFixedPerdiemAmount() : "0");
						float retentionBonus = Float.valueOf(financeInfo.getRetentionBonus() != null && !financeInfo.getRetentionBonus().equalsIgnoreCase("") ? financeInfo.getRetentionBonus() : "0");
						String plb = financeInfo.getPlb();
						if (plb == null || plb.equalsIgnoreCase("")) plb = "0";
						// Saving total amt,balance amt and paid amt in Salary In Advance	
						float balanceAmt=0;
						float paidAmt=0;
						
						
						
						String[] sal=null;
						if(financeInfo.getSalaryInAdvTot()!=null && !(financeInfo.getSalaryInAdvTot().equalsIgnoreCase(""))){
							
								 sal = FinanceInfoDaoFactory.create().getSalaryInAdv(userId);
								 if(sal!=null){
									 if(sal[5]!=null){
										 paidAmt= Float.valueOf(sal[5]);
									 }
									 
								 }
							balanceAmt=Float.valueOf(financeInfo.getSalaryInAdvTot())-paidAmt;
						}
						financeinfodao.updateSalaryInAdv(new String[] { userId + "", financeInfo.getSalaryInAdvTot(), financeInfo.getSalaryInAdvMon(), financeInfo.getSalaryInNoOfMonths(),String.valueOf(balanceAmt),String.valueOf(paidAmt),financeInfo.getSalaryAdvToBeDeducted()});
					    Salary salary = new FinanceInfoModel().getCalculatedSalary(plb, ctc, perDiem, true,fixedPerDiem,retentionBonus,form);
					   
					    //		Salary salaryCtc = new FinanceInfoModel().getCalculatedSalaryCtc(plb, ctc, perDiem, true,fixedPerDiem,retentionBonus);
						
						SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
						
						//			SalaryDetailModel salaryDetailModelCtc = new SalaryDetailModel();
						
				//		result = salaryDetailModel.saveSalaryDetails(Float.valueOf(plb), salary, userId, candidateId, SalaryDetailModel.TYPE_NORMAL);
						
								result = salaryDetailModel.saveSalaryDetailsCtc(Float.valueOf(plb), salary, userId, candidateId, SalaryDetailModel.TYPE_NORMAL,form);
						
						new TDSUtility().reCalculate(userId);
						updateFixedPerdiem(financeInfo);
						updatePerdiemInAdvance(financeInfo);
					} else logger.debug("Unable to save salary details as user id is " + userId);
					
					// Saving total amt,balance amt and paid amt in Travel In Advance	by venkat
					float tbalanceAmt=0;
					float tpaidAmt=0;
					
					String[] travel=null;
					if(financeInfo.getTravelInAdvTot()!=null && !(financeInfo.getTravelInAdvTot().equalsIgnoreCase(""))) {
						travel= FinanceInfoDaoFactory.create().getTravelInAdv(userId);
						if(travel!=null) {
							if(travel[5]!=null) {
								tpaidAmt=Float.valueOf(travel[5]);
							}
						}
						tbalanceAmt=Float.valueOf(financeInfo.getTravelInAdvTot())-tpaidAmt;
					}
					financeinfodao.updateTravelInAdv(new String[] { userId + "", financeInfo.getTravelInAdvTot(), financeInfo.getTravelInAdvMon(), financeInfo.getTravelInNoOfMonths(), String.valueOf(tbalanceAmt),String.valueOf(tpaidAmt),financeInfo.getTravelDeductionStatus()});
	                //------------------------------------------------------
					/**
					 * Insert in DocumentMapping Table
					 */
					if (financeInfo.getFiles() != null){
						files = financeInfo.getFiles().split("~");
						if (files != null && files.length > 0){
							Integer field[] = new Integer[files.length];
							for (int l = 0; l < files.length; l++){
								field[l] = Integer.parseInt(files[l]);
							}
							DocumentMapping documentMap[] = mappingDao.findWhereFinanceIdEquals(financeinfopk.getId());
							List<?> containArray = Arrays.asList(field);
							for (DocumentMapping doc : documentMap){
								contains = containArray.contains(doc.getDocumentId());
								if (!contains){
									DocumentMappingPk documentMappingPk = new DocumentMappingPk();
									documentMappingPk.setId(doc.getId());
									mappingDao.delete(documentMappingPk);
								}
							}
							if (field != null && field.length > 0){
								DocumentMapping docMap[] = new DocumentMapping[field.length];
								for (int i = 0; i < field.length; i++){
									documentMapping.setFinanceId(financeinfopk.getId());
									if (field != null && field[i] != 0){
										documentMapping.setDocumentId(field[i]);
										/**
										 * this query and for loop is for checking if already exists and deleting and reinserting new entries
										 */
										docMap = mappingDao.findByDynamicWhere("DOCUMENT_ID = ? AND FINANCE_ID = ?", new Object[] { field[i], financeinfopk.getId() });
										if (docMap != null && docMap.length > 0){
											for (int k = 0; k < docMap.length; k++){
												docMapPk.setId(docMap[k].getId());
												mappingDao.delete(docMapPk);
											}
										}
										documentMapping.setId(documentMapping.getId() != 0 ? 0 : 0);
										mappingDao.insert(documentMapping);
									}
								}
							}
						}// if files not null
					}
					if (financeInfo.getPerdiemAmount() != null && financeInfo.getPerdiemAmount().length() > 0 && financeInfo.getPerdiemFrom() != null){
						savePerdiemMasterData(financeInfo);
					} else{
						deleteFromPerdiemMasterData(financeInfo);
					}
					if(!(financeInfo.getSalaryStack()==1))
					{
					if (financeInfo.getRetentionBonus()!= null && !financeInfo.getRetentionBonus().equalsIgnoreCase("") &&  financeInfo.getRetentionInstallments()!=null && financeInfo.getRetentionStartDate() != null){
						saveRetetionBonus(financeInfo);
					} else{
						deleteFromRetetionBonus(financeInfo);
					}
					}
				} catch (Exception e){
					logger.info("Failed to update Profile Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					e.printStackTrace();
				}
			}
				break;
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		FinanceInfo financeInfo = (FinanceInfo) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		path = PropertyLoader.getEnvVariable() + seprator + path;
		/**
		 * Get filename from id
		 */
		PortalData portalData = new PortalData();
		path = portalData.getfolder(path);
		try{
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents docNew = new Documents();
			docNew = documentsDao.findByPrimaryKey(financeInfo.getDocid());
			attachements.setFileName(docNew.getFilename());
			attachements.setFilePath(path);
		} catch (Exception e){
			e.printStackTrace();
		}
		return attachements;
	}

	public void savePerdiemMasterData(FinanceInfo financeInfo) throws PerdiemMasterDataDaoException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create();
		int userId = financeInfo.getPerdiemUserId() == 0 ? financeInfo.getUserId() : financeInfo.getPerdiemUserId();
		if (userId == 0) throw new PerdiemMasterDataDaoException("UserId should not be 0 to insert perdiem details");
		PerdiemMasterData fetchedPerdiemDto = null, fetchedPerdiemDtos[] = perdiemMasterDao.findWhereUserIdEquals(userId);
		if (fetchedPerdiemDtos != null && fetchedPerdiemDtos.length > 0){
			fetchedPerdiemDto = fetchedPerdiemDtos[0];
		} else{
			fetchedPerdiemDto = new PerdiemMasterData();
		}
		try{
			financeInfo.getPerdiemAmount();
		} catch (Exception e){}
		fetchedPerdiemDto.setPerdiem(DesEncrypterDecrypter.getInstance().encrypt(financeInfo.getPerdiemAmount()));
		fetchedPerdiemDto.setPerdiemFrom(financeInfo.getPerdiemFrom());
		fetchedPerdiemDto.setUserId(userId);
		fetchedPerdiemDto.setPerdiemTo(financeInfo.getPerdiemTo());
		fetchedPerdiemDto.setCurrencyType(financeInfo.getPerdiemCurrencyType());
		if (fetchedPerdiemDto.getId() > 0){
			perdiemMasterDao.update(fetchedPerdiemDto.createPk(), fetchedPerdiemDto);
		} else{
			perdiemMasterDao.insert(fetchedPerdiemDto);
		}
	}

	public void deleteFromPerdiemMasterData(FinanceInfo financeInfo) throws PerdiemMasterDataDaoException {
		PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create();
		int userId = financeInfo.getPerdiemUserId() == 0 ? financeInfo.getUserId() : financeInfo.getPerdiemUserId();
		if (userId == 0) throw new PerdiemMasterDataDaoException("UserId should not be 0 to insert perdiem details");
		PerdiemMasterData fetchedPerdiemDtos[] = perdiemMasterDao.findWhereUserIdEquals(userId);
		if (fetchedPerdiemDtos != null && fetchedPerdiemDtos.length > 0){
			perdiemMasterDao.delete(new PerdiemMasterDataPk(fetchedPerdiemDtos[0].getId()));
		}
	}

	public static final boolean isLeaveMonthly(String head, String component) {
		boolean result = false;
		// Sets true if either head is INSURANCE or
		// component is ANNUL/QUARTERLY LINKED BONUS
		result = (head != null && head.equalsIgnoreCase(HEAD_INSURANCE)) || (component != null && (component.equalsIgnoreCase(COMPONENT_QPLB) || component.equalsIgnoreCase(COMPONENT_APLB)));
		return (result);
	}
	
	
	
	
	
	/*
	public Salary getCalculatedSalary(String plbString, float ctc, float perDiem, boolean isSave,float fixedPerdiem) {
		if (ctc == 0) return null;
		final String CTC = "CTC";
		final String PERDIEM = "PERDIEM";
		float plb = 0.0f;
		if (plbString != null && !plbString.equalsIgnoreCase("")) plb = Float.valueOf(plbString);
		SalaryConfigDao salaryConfigDao = SalaryConfigDaoFactory.create();
		Connection connection = MyDBConnect.getConnect();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Salary salary = null;
		try{
			stmt = connection.prepareStatement("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG");
			resultSet = stmt.executeQuery();
			// SalaryConfig[] heads = salaryConfigDao.findByDynamicSelect("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG", null);
			salary = new Salary();
			List<SalaryHead> salHeadsList = new ArrayList<SalaryHead>();
			skipConfig: while (resultSet.next()){
				List<SalaryConfigs> salConfigs = new ArrayList<SalaryConfigs>();
				SalaryHead salHeads = new SalaryHead();
				SalaryConfigs tempconfigs;
				float annualSum = 0;
				float monthlySum = 0.0f;
				SalaryConfig[] eachItems = salaryConfigDao.findWhereHeadEquals(resultSet.getString(1));
				for (SalaryConfig eachItem : eachItems){
					tempconfigs = new SalaryConfigs();
					tempconfigs.setId(eachItem.getId());
					tempconfigs.setAutoCalc(eachItem.getAutoCalc());
					tempconfigs.setComponent(eachItem.getComponent());
					tempconfigs.setComponentOrder(eachItem.getComponentOrder());
					tempconfigs.setValue(eachItem.getValue());
					tempconfigs.setValueType(eachItem.getValueType());
					if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(CTC) != -1){
						tempconfigs.setAnnualAmount(ctc);
						tempconfigs.setMonthlyAmount(ctc / 12);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(PERDIEM) != -1){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount(eachItem.getValue());
						tempconfigs.setMonthlyAmount(eachItem.getValue() / 12);
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount((ctc * eachItem.getValue()) - 9600);
						tempconfigs.setMonthlyAmount(tempconfigs.getAnnualAmount() / 12);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERDIEM"))){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("FIXED"))){
						tempconfigs.setAnnualAmount(fixedPerdiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(fixedPerdiem * SalaryDetailModel.MONTH_DAYS);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else{
						logger.error("Unable to calculate the salary component as the configuration is incorrect for " + eachItem.getHead() + "  " + eachItem.getComponent());
					}
					if (FinanceInfoModel.isLeaveMonthly(eachItem.getHead(), eachItem.getComponent())) tempconfigs.setMonthlyAmount(0.0f);
					if (plbString != null && !plbString.equalsIgnoreCase("")){
						if (eachItem.getId() == 9){
							tempconfigs.setAnnualAmount(plb * 0.75f);
							tempconfigs.setMonthlyAmount(0);
						} else if (eachItem.getId() == 10){
							tempconfigs.setAnnualAmount(plb * 0.25f);
							tempconfigs.setMonthlyAmount(0);
						}
					}
					annualSum += tempconfigs.getAnnualAmount();
					monthlySum += tempconfigs.getMonthlyAmount();
					salConfigs.add(tempconfigs);
				}
				salHeads.setSalaryConfigs(salConfigs);
				salHeads.setName(resultSet.getString(1));
				salHeads.setAnnualSum(Math.round(annualSum));
				// salHeads.setMonthlySum(Math.round(annualSum/12));
				salHeads.setMonthlySum(Math.round(monthlySum));
				salHeadsList.add(salHeads);
			}
			salary.setSalaryHead(salHeadsList);
			float totalCTC = 0, monthlyCtc = 0.0f;
			for (SalaryHead salaryHead : salHeadsList){
				if (!salaryHead.getName().equalsIgnoreCase(SalaryDetailModel.HEAD_INSURANCE)){
					totalCTC += salaryHead.getAnnualSum();
					monthlyCtc += salaryHead.getMonthlySum();
				} else logger.debug(salaryHead + " not applicable for total CTC calculation.");
			}
			totalCTC = Math.round(totalCTC);
			salary.setCtc(ctc);
			salary.setPerDiem(perDiem);
			salary.setFixed(ctc);
			salary.setFBP(ctc);
			salary.setTblb(Float.valueOf(plbString));
			salary.setAnnualCTC(totalCTC);
			salary.setFixedPerdiemAmount(fixedPerdiem);
			
			// salary.setMonthlyCTC(Math.round(totalCTC/12));
			salary.setMonthlyCTC(monthlyCtc);
			salary.setTotCTC(totalCTC);
		} catch (Exception e){
			logger.debug("Exception thrown in Calculate the salary details in FinanceInfoModel " + e.getMessage());
		} finally{
			try{
				MyDBConnect.close(connection);
			} catch (SQLException e){
				logger.debug("Unable to close the connection opened for querying the heads of salary " + e.getMessage());
			}
		}
		return salary;
	}
	*/
	
	
	public Salary getCalculatedSalary(String plbString, float ctc, float perDiem, boolean isSave,float fixedPerdiem,float retentionBonus,PortalForm form) {
		FinanceInfo financeInfo = (FinanceInfo) form;
		if (ctc == 0) return null;
		final String CTC = "CTC";
		final String PERDIEM = "PERDIEM";
		float plb = 0.0f;
		if (plbString != null && !plbString.equalsIgnoreCase("")) plb = Float.valueOf(plbString);
		SalaryConfigDao salaryConfigDao = SalaryConfigDaoFactory.create();
		Connection connection = MyDBConnect.getConnect();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Salary salary = null;
		try{
//	stmt = connection.prepareStatement("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG where ID IN(1,2,3,4,5,6,7,8,9,10,11,16,17)");
			stmt = connection.prepareStatement("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG");
			resultSet = stmt.executeQuery();
			// SalaryConfig[] heads = salaryConfigDao.findByDynamicSelect("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG", null);
			salary = new Salary();
			List<SalaryHead> salHeadsList = new ArrayList<SalaryHead>();
			skipConfig: while (resultSet.next()){
				List<SalaryConfigs> salConfigs = new ArrayList<SalaryConfigs>();
				SalaryHead salHeads = new SalaryHead();
				SalaryConfigs tempconfigs;
				float annualSum = 0;
				float monthlySum = 0.0f;
				float FBP=0;
				float ECIP = 0;
				float PF=0;
				float Gratuity=0;
				float FBPm=0;
				float ECIPm=0;
				float PFm=0;
				float Gratuitym=0;
				float fm=0;
				float f =0;
				float perdiema = 0;
				float Fixedperdiema = 0;
				SalaryConfig[] eachItems = salaryConfigDao.findWhereHeadEquals(resultSet.getString(1));
				for (SalaryConfig eachItem : eachItems){
					tempconfigs = new SalaryConfigs();
					tempconfigs.setId(eachItem.getId());
					tempconfigs.setAutoCalc(eachItem.getAutoCalc());
					tempconfigs.setComponent(eachItem.getComponent());
					tempconfigs.setComponentOrder(eachItem.getComponentOrder());
					tempconfigs.setValue(eachItem.getValue());
					tempconfigs.setValueType(eachItem.getValueType());
					if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(CTC) != -1){
						tempconfigs.setAnnualAmount(ctc);
						tempconfigs.setMonthlyAmount(ctc / 12);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(PERDIEM) != -1){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount(eachItem.getValue());
						tempconfigs.setMonthlyAmount(eachItem.getValue() / 12);
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount((ctc * eachItem.getValue()) - 19200);
						tempconfigs.setMonthlyAmount(tempconfigs.getAnnualAmount() / 12);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERDIEM"))){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("FIXED"))){
						tempconfigs.setAnnualAmount(fixedPerdiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(fixedPerdiem * SalaryDetailModel.MONTH_DAYS);
					}else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RBONUS"))){
						tempconfigs.setAnnualAmount(retentionBonus);
						tempconfigs.setMonthlyAmount(0);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else{
						logger.error("Unable to calculate the salary component as the configuration is incorrect for " + eachItem.getHead() + "  " + eachItem.getComponent());
					}
					if (FinanceInfoModel.isLeaveMonthly(eachItem.getHead(), eachItem.getComponent())) tempconfigs.setMonthlyAmount(0.0f);
					if (plbString != null && !plbString.equalsIgnoreCase("")){
						
					if(financeInfo.getSalaryStack()==1 && eachItem.getId()==9){
							int QPLB = (financeInfo.getQPLBperformance());
							perdiema=perDiem * SalaryDetailModel.YEAR_DAYS;
							Fixedperdiema=fixedPerdiem * SalaryDetailModel.YEAR_DAYS;
							float total = Float.valueOf((plbString))+ctc+perdiema+Fixedperdiema;
							float	total1 = ((total) * QPLB)/100;
							tempconfigs.setAnnualAmount(total1);
							
						}
						
					if (eachItem.getId() == 9 &&financeInfo.getSalaryStack()!=1){
						
							tempconfigs.setAnnualAmount(plb * 0.75f);
							tempconfigs.setMonthlyAmount(0);
						}
						
					if(financeInfo.getSalaryStack()==1 && eachItem.getId()==10){
							int APLB = (financeInfo.getCPLBperformance());
							perdiema=perDiem * SalaryDetailModel.YEAR_DAYS;
							Fixedperdiema=fixedPerdiem * SalaryDetailModel.YEAR_DAYS;
							float totala = Float.valueOf(plbString)+ctc+perdiema+Fixedperdiema;
						    float	total2 = (Float.valueOf(totala) * APLB)/100;
							tempconfigs.setAnnualAmount(total2);
						}
							
					if (eachItem.getId() == 10&&financeInfo.getSalaryStack()!=1){
							
							tempconfigs.setAnnualAmount(plb * 0.25f);
							tempconfigs.setMonthlyAmount(0);
						 }
					}
					
					
					
					
					if(financeInfo.getSalaryStack()==1&&eachItem.getId()==5)
					{
						FBP=ctc * eachItem.getValue();
						FBPm=(ctc * eachItem.getValue()/12);
					}
					
					if(financeInfo.getSalaryStack()==1&&eachItem.getId()==6)
					{
						ECIP=eachItem.getValue();
						ECIPm=(eachItem.getValue()/12);
						
					}
					
					if(financeInfo.getSalaryStack()==1&&eachItem.getId()==7)
					{
						
						PF=ctc * eachItem.getValue();
						PFm=(ctc * eachItem.getValue()/12);
						
					}
					
					if(financeInfo.getSalaryStack()==1&&eachItem.getId()==8)
					{
						Gratuity=ctc * eachItem.getValue();
						Gratuitym=(ctc * eachItem.getValue()/12);
					}
			
					annualSum += tempconfigs.getAnnualAmount();
					monthlySum += tempconfigs.getMonthlyAmount();
					salConfigs.add(tempconfigs);
				}
				
				
			for(SalaryConfigs SalaryConfigs : salConfigs)
			{
				if(financeInfo.getSalaryStack()==1&&SalaryConfigs.getComponent().equalsIgnoreCase("Flexi Benefit Plan(FBP)"))
						{
					     SalaryConfigs.setAnnualAmount(FBP-(ECIP+PF+Gratuity));
					      f = SalaryConfigs.getAnnualAmount();
					     SalaryConfigs.setMonthlyAmount(FBPm-(ECIPm+PFm+Gratuitym));
					      fm = SalaryConfigs.getMonthlyAmount();
						}}
			
					float total1=ECIP+PF+Gratuity;
			        float finalannual=f+total1;
			        float total2=ECIPm+PFm+Gratuitym;
			        float finalmonthly= fm+total2;
			        
			    salHeads.setSalaryConfigs(salConfigs);
				salHeads.setName(resultSet.getString(1));
				if(financeInfo.getSalaryStack()==1 && salHeads.getName().equalsIgnoreCase("C - BENEFITS")){
					salHeads.setAnnualSum(Math.round(finalannual));
					salHeads.setMonthlySum(Math.round(finalmonthly));
					salHeadsList.add(salHeads);
				}
				else{
				salHeads.setAnnualSum(Math.round(annualSum));
				salHeads.setMonthlySum(Math.round(monthlySum));
				salHeadsList.add(salHeads);}
			}
			salary.setSalaryHead(salHeadsList);
			float totalCTC = 0, monthlyCtc = 0.0f;
			
			for (SalaryHead salaryHead : salHeadsList){
				if (!salaryHead.getName().equalsIgnoreCase(SalaryDetailModel.HEAD_INSURANCE)){
					totalCTC += salaryHead.getAnnualSum();
			//		logger.info("annual\t"+salaryHead.getAnnualSum()+"\t monthly amount\t"+salaryHead.getMonthlySum());
			//		System.out.println("salaryHead.getName()"+salaryHead.getName()+ "annual\t" +salaryHead.getAnnualSum()+"\t monthly amount\t"+salaryHead.getMonthlySum());
				//	int a = 19200;
				//	double totalCtc = totalCTC+a;
			//		System.out.println(totalCtc);
					
					monthlyCtc += salaryHead.getMonthlySum();
				} else logger.debug(salaryHead + " not applicable for total CTC calculation.");
			}
		//	int a = 19200;
			totalCTC = totalCTC;
		//	System.out.println(totalCTC);
			totalCTC = Math.round(totalCTC);
			salary.setCtc(ctc);
			salary.setPerDiem(perDiem);
			salary.setFixed(ctc);
			salary.setFBP(ctc);
			salary.setTblb(Float.valueOf(plbString));
			salary.setAnnualCTC(totalCTC);
			salary.setFixedPerdiemAmount(fixedPerdiem);
			salary.setRetentionBonus(retentionBonus);
			salary.setEsic(financeInfo.getEsic());
			
			// salary.setMonthlyCTC(Math.round(totalCTC/12));
			salary.setMonthlyCTC(monthlyCtc);
			salary.setTotCTC(totalCTC);
		} catch (Exception e){
			logger.debug("Exception thrown in Calculate the salary details in FinanceInfoModel " + e.getMessage());
		} finally{
			try{
				MyDBConnect.close(connection);
			} catch (SQLException e){
				logger.debug("Unable to close the connection opened for querying the heads of salary " + e.getMessage());
			}
		}
		return salary;
	}
	
	
	public Salary getCalculatedSalaryCtc(String plbString, float ctc, float perDiem, boolean isSave,float fixedPerdiem,float retentionBonus) {
		if (ctc == 0) return null;
		final String CTC = "CTC";
		final String PERDIEM = "PERDIEM";
		float plb = 0.0f;
		if (plbString != null && !plbString.equalsIgnoreCase("")) plb = Float.valueOf(plbString);
		SalaryConfigDao salaryConfigDao = SalaryConfigDaoFactory.create();
		Connection connection = MyDBConnect.getConnect();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Salary salary = null;
		try{
			stmt = connection.prepareStatement("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG where ID IN(1,2,3,4,5,6,7,8,9,10,11,16,17)");
			//stmt = connection.prepareStatement("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG");
			resultSet = stmt.executeQuery();
			// SalaryConfig[] heads = salaryConfigDao.findByDynamicSelect("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG", null);
			salary = new Salary();
			List<SalaryHead> salHeadsList = new ArrayList<SalaryHead>();
			skipConfig: while (resultSet.next()){
				List<SalaryConfigs> salConfigs = new ArrayList<SalaryConfigs>();
				SalaryHead salHeads = new SalaryHead();
				SalaryConfigs tempconfigs;
				float annualSum = 0;
				float monthlySum = 0.0f;
				SalaryConfig[] eachItems = salaryConfigDao.findWhereHeadEquals(resultSet.getString(1));
				for (SalaryConfig eachItem : eachItems){
					tempconfigs = new SalaryConfigs();
					tempconfigs.setId(eachItem.getId());
					tempconfigs.setAutoCalc(eachItem.getAutoCalc());
					tempconfigs.setComponent(eachItem.getComponent());
					tempconfigs.setComponentOrder(eachItem.getComponentOrder());
					tempconfigs.setValue(eachItem.getValue());
					tempconfigs.setValueType(eachItem.getValueType());
					if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(CTC) != -1){
						tempconfigs.setAnnualAmount(ctc);
						tempconfigs.setMonthlyAmount(ctc / 12);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(PERDIEM) != -1){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount(eachItem.getValue());
						tempconfigs.setMonthlyAmount(eachItem.getValue() / 12);
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount((ctc * eachItem.getValue()) - 19200);
						tempconfigs.setMonthlyAmount(tempconfigs.getAnnualAmount() / 12);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERDIEM"))){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("FIXED"))){
						tempconfigs.setAnnualAmount(fixedPerdiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(fixedPerdiem * SalaryDetailModel.MONTH_DAYS);
					}else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RBONUS"))){
						tempconfigs.setAnnualAmount(retentionBonus);
						tempconfigs.setMonthlyAmount(0);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else{
						logger.error("Unable to calculate the salary component as the configuration is incorrect for " + eachItem.getHead() + "  " + eachItem.getComponent());
					}
					if (FinanceInfoModel.isLeaveMonthly(eachItem.getHead(), eachItem.getComponent())) tempconfigs.setMonthlyAmount(0.0f);
					if (plbString != null && !plbString.equalsIgnoreCase("")){
						if (eachItem.getId() == 9){
							tempconfigs.setAnnualAmount(plb * 0.75f);
							tempconfigs.setMonthlyAmount(0);
						} else if (eachItem.getId() == 10){
							tempconfigs.setAnnualAmount(plb * 0.25f);
							tempconfigs.setMonthlyAmount(0);
						}
					}
					annualSum += tempconfigs.getAnnualAmount();
					monthlySum += tempconfigs.getMonthlyAmount();
					salConfigs.add(tempconfigs);
				}
				salHeads.setSalaryConfigs(salConfigs);
				salHeads.setName(resultSet.getString(1));
				salHeads.setAnnualSum(Math.round(annualSum));
				// salHeads.setMonthlySum(Math.round(annualSum/12));
				salHeads.setMonthlySum(Math.round(monthlySum));
				salHeadsList.add(salHeads);
			}
			salary.setSalaryHead(salHeadsList);
			float totalCTC = 0, monthlyCtc = 0.0f;
			for (SalaryHead salaryHead : salHeadsList){
				if (!salaryHead.getName().equalsIgnoreCase(SalaryDetailModel.HEAD_INSURANCE)){
					totalCTC += salaryHead.getAnnualSum();
				//	int a = 19200;
				//	double totalCtc = totalCTC+a;
			//		System.out.println(totalCtc);
					
					monthlyCtc += salaryHead.getMonthlySum();
				} else logger.debug(salaryHead + " not applicable for total CTC calculation.");
			}
		//	int a = 19200;
			totalCTC = totalCTC;
		//	System.out.println(totalCTC);
			totalCTC = Math.round(totalCTC);
			salary.setCtc(ctc);
			salary.setPerDiem(perDiem);
			salary.setFixed(ctc);
			salary.setFBP(ctc);
			salary.setTblb(Float.valueOf(plbString));
			salary.setAnnualCTC(totalCTC);
			salary.setFixedPerdiemAmount(fixedPerdiem);
			salary.setRetentionBonus(retentionBonus);
			
			// salary.setMonthlyCTC(Math.round(totalCTC/12));
			salary.setMonthlyCTC(monthlyCtc);
			salary.setTotCTC(totalCTC);
		} catch (Exception e){
			logger.debug("Exception thrown in Calculate the salary details in FinanceInfoModel " + e.getMessage());
		} finally{
			try{
				MyDBConnect.close(connection);
			} catch (SQLException e){
				logger.debug("Unable to close the connection opened for querying the heads of salary " + e.getMessage());
			}
		}
		return salary;
	}
	
	/**
	 * Method for calculating the salary when CTC and PerDiem given
	 * 
	 * @param ctc
	 * @param perDiem
	 * @return
	 */
	public Salary getCalculatedSalary1(String plbString, float ctc, float perDiem, boolean isSave,float retentionBonus) {
		if (ctc == 0) return null;
		final String CTC = "CTC";
		final String PERDIEM = "PERDIEM";
		float plb = 0.0f;
		if (plbString != null && !plbString.equalsIgnoreCase("")) plb = Float.valueOf(plbString);
		SalaryConfigDao salaryConfigDao = SalaryConfigDaoFactory.create();
		Connection connection = MyDBConnect.getConnect();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Salary salary = null;
		try{
			stmt = connection.prepareStatement("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG");
			resultSet = stmt.executeQuery();
			// SalaryConfig[] heads = salaryConfigDao.findByDynamicSelect("SELECT DISTINCT(HEAD) FROM SALARY_CONFIG", null);
			salary = new Salary();
			List<SalaryHead> salHeadsList = new ArrayList<SalaryHead>();
			skipConfig: while (resultSet.next()){
				List<SalaryConfigs> salConfigs = new ArrayList<SalaryConfigs>();
				SalaryHead salHeads = new SalaryHead();
				SalaryConfigs tempconfigs;
				float annualSum = 0;
				float monthlySum = 0.0f;
				SalaryConfig[] eachItems = salaryConfigDao.findWhereHeadEquals(resultSet.getString(1));
				for (SalaryConfig eachItem : eachItems){
					tempconfigs = new SalaryConfigs();
					tempconfigs.setId(eachItem.getId());
					tempconfigs.setAutoCalc(eachItem.getAutoCalc());
					tempconfigs.setComponent(eachItem.getComponent());
					tempconfigs.setComponentOrder(eachItem.getComponentOrder());
					tempconfigs.setValue(eachItem.getValue());
					tempconfigs.setValueType(eachItem.getValueType());
					if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(CTC) != -1){
						tempconfigs.setAnnualAmount(ctc);
						tempconfigs.setMonthlyAmount(ctc / 12);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getValueType() == null || eachItem.getValueType().equalsIgnoreCase("")) && eachItem.getComponent().indexOf(PERDIEM) != -1){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
						if (isSave == false) continue skipConfig;
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount(eachItem.getValue());
						tempconfigs.setMonthlyAmount(eachItem.getValue() / 12);
					} else if ((eachItem.getAutoCalc() == 1) && (eachItem.getValueType().equalsIgnoreCase("RAW"))){
						tempconfigs.setAnnualAmount((ctc * eachItem.getValue()) - 19200);
						tempconfigs.setMonthlyAmount(tempconfigs.getAnnualAmount() / 12);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERDIEM"))){
						tempconfigs.setAnnualAmount(perDiem * SalaryDetailModel.YEAR_DAYS);
						tempconfigs.setMonthlyAmount(perDiem * SalaryDetailModel.MONTH_DAYS);
					}else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("RBONUS"))){
						tempconfigs.setAnnualAmount(retentionBonus);
						tempconfigs.setMonthlyAmount(0);
					} else if ((eachItem.getAutoCalc() == 0) && (eachItem.getValueType().equalsIgnoreCase("PERCENT"))){
						tempconfigs.setAnnualAmount(ctc * eachItem.getValue());
						tempconfigs.setMonthlyAmount(((ctc * eachItem.getValue()) / 12));
					}else{
						logger.error("Unable to calculate the salary component as the configuration is incorrect for " + eachItem.getHead() + "  " + eachItem.getComponent());
					}
					if (FinanceInfoModel.isLeaveMonthly(eachItem.getHead(), eachItem.getComponent())) tempconfigs.setMonthlyAmount(0.0f);
					if (plbString != null && !plbString.equalsIgnoreCase("")){
						if (eachItem.getId() == 9){
							tempconfigs.setAnnualAmount(plb * 0.75f);
							tempconfigs.setMonthlyAmount(0);
						} else if (eachItem.getId() == 10){
							tempconfigs.setAnnualAmount(plb * 0.25f);
							tempconfigs.setMonthlyAmount(0);
						}
					}
					annualSum += tempconfigs.getAnnualAmount();
					monthlySum += tempconfigs.getMonthlyAmount();
					salConfigs.add(tempconfigs);
				}
				salHeads.setSalaryConfigs(salConfigs);
				salHeads.setName(resultSet.getString(1));
				salHeads.setAnnualSum(Math.round(annualSum));
				// salHeads.setMonthlySum(Math.round(annualSum/12));
				salHeads.setMonthlySum(Math.round(monthlySum));
				salHeadsList.add(salHeads);
			}
			salary.setSalaryHead(salHeadsList);
			float totalCTC = 0, monthlyCtc = 0.0f;
			for (SalaryHead salaryHead : salHeadsList){
				if (!salaryHead.getName().equalsIgnoreCase(SalaryDetailModel.HEAD_INSURANCE)){
					totalCTC += salaryHead.getAnnualSum();
					monthlyCtc += salaryHead.getMonthlySum();
				} else logger.debug(salaryHead + " not applicable for total CTC calculation.");
			}
			totalCTC = Math.round(totalCTC);
			salary.setCtc(ctc);
			salary.setPerDiem(perDiem);
			salary.setFixed(ctc);
			salary.setFBP(ctc);
			salary.setTblb(Float.valueOf(plbString));
			salary.setAnnualCTC(totalCTC);
			salary.setRetentionBonus(retentionBonus);
			// salary.setMonthlyCTC(Math.round(totalCTC/12));
			salary.setMonthlyCTC(monthlyCtc);
			salary.setTotCTC(totalCTC);
		} catch (Exception e){
			logger.debug("Exception thrown in Calculate the salary details in FinanceInfoModel " + e.getMessage());
		} finally{
			try{
				MyDBConnect.close(connection);
			} catch (SQLException e){
				logger.debug("Unable to close the connection opened for querying the heads of salary " + e.getMessage());
			}
		}
		return salary;
	}

}
