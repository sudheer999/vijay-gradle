package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.quartz.JobExecutionException;

import com.dikshatech.beans.TaxBenefitDetail;
import com.dikshatech.beans.TaxBenefitHistory;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.Status;
import com.dikshatech.common.utils.TDSUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ModulesDao;
import com.dikshatech.portal.dao.MonthlyPayrollDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RequestTypeDao;
import com.dikshatech.portal.dao.SalaryReconciliationDao;
import com.dikshatech.portal.dao.TaxBenefitAcceptDao;
import com.dikshatech.portal.dao.TaxBenefitDao;
import com.dikshatech.portal.dao.TaxBenefitDeclarationDao;
import com.dikshatech.portal.dao.TaxBenefitReqDao;
import com.dikshatech.portal.dao.TbdHistoryDao;
import com.dikshatech.portal.dao.TdsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.dto.MonthlyPayroll;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.RequestType;
import com.dikshatech.portal.dto.SalaryReconciliation;
import com.dikshatech.portal.dto.SalaryReconciliationReport;
import com.dikshatech.portal.dto.TaxBenefit;
import com.dikshatech.portal.dto.TaxBenefitAccept;
import com.dikshatech.portal.dto.TaxBenefitAcceptPk;
import com.dikshatech.portal.dto.TaxBenefitDeclaration;
import com.dikshatech.portal.dto.TaxBenefitDeclarationPk;
import com.dikshatech.portal.dto.TaxBenefitReq;
import com.dikshatech.portal.dto.TaxBenefitReqPk;
import com.dikshatech.portal.dto.TbdHistory;
import com.dikshatech.portal.dto.Tds;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.ModulesDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.RequestTypeDaoException;
import com.dikshatech.portal.exceptions.TaxBenefitAcceptDaoException;
import com.dikshatech.portal.exceptions.TaxBenefitDaoException;
import com.dikshatech.portal.exceptions.TaxBenefitDeclarationDaoException;
import com.dikshatech.portal.exceptions.TaxBenefitReqDaoException;
import com.dikshatech.portal.exceptions.TbdHistoryDaoException;
import com.dikshatech.portal.exceptions.TdsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ModulesDaoFactory;
import com.dikshatech.portal.factory.MonthlyPayrollDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RequestTypeDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.TaxBenefitAcceptDaoFactory;
import com.dikshatech.portal.factory.TaxBenefitDaoFactory;
import com.dikshatech.portal.factory.TaxBenefitDeclarationDaoFactory;
import com.dikshatech.portal.factory.TaxBenefitReqDaoFactory;
import com.dikshatech.portal.factory.TbdHistoryDaoFactory;
import com.dikshatech.portal.factory.TdsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.forms.TaxBenefitForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.timer.TdsJob;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author pawan.kaushal
 */
public class TaxBenefitDeclarationModel extends ActionMethods {

	private static final Logger	logger							= LoggerUtil.getLogger();
	private final String		PARAM_TAXBENEFIT_SEP			= "~=~";
	private final String		PARAM_TAXID_SEP					= "=";
	private final String		PARAM_TAXVALUE_SEP				= "\\|";
	public static final int		PARAM_TAXAMOUNT_INDEX			= 0;
	public static final int		PARAM_COMMENT_INDEX				= 1;
	public static final String	TAX_BENEFIT_MODULE_NAME			= "Benefit Declaration";
	public static final String	TAX_BENEFIT_PROCESSCHAIN_NAME	= "IN_TAX_BENEFIT_PROC";
	private final String		TAX_BENEFIT_SUBJECT_PREFIX		= "Tax benefit ";
	private final String		TAX_BENEFIT_INFO_HTML			= "<table style=\"border:1px solid #666666;font-family:Arial;color:blue;font-size:9pt;\">" + "<tr><th>Tax Benefit</th><th>Previous Amount</th><th>Updated Amount</th><th>Comments</th></tr>";
	private final String		DB_ERROR_KEY					= "errors.tbd.update.database";
	private final String		NO_REPLY_EMAIL_ID				= null;

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// Auto-generated method stub
		// blank implementation
		return null;
	}

	/**
	 * Do save operation for tax benefit declaration. Implementation worker bean
	 * to RequestHandler action.
	 * id1=value1|comment1~=~id2=value3|comment2~=~id3=value3|comment3~=~ eg.
	 * 1=2300.0|first dec~=~2=10222.5|sodexo~=~3=900.0|test
	 */
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		String sType = form.getsType();
		TaxBenefitForm taxBenefitForm = (TaxBenefitForm) form;
		SaveTypes paramSType = SaveTypes.getValue(sType);
		Login login = Login.getLogin(request);
		int userId = login != null ? login.getUserId() : 0;
		String financialYear = FBPModel.getFinancialYear();
		switch (paramSType) {
			case SAVEANDSUBMIT:{
				Connection conn = null;
				try{
					// Begin transaction
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					result = saveTaxBenefitDeclaration(userId, financialYear, taxBenefitForm, conn);
					if (result.getActionMessages().size() > 0){
						logger.error("Unable to save and submit. Problem in saving tax benefit declaration. ");
						this.rollback(conn, result, null);
					} else{
						result = submitTaxBenefitDeclaration(userId, financialYear, taxBenefitForm, conn);
						if (result.getActionMessages().size() > 0){
							logger.error("Unable to save and submit. Problem in submitting tax benefit declaration. ");
							this.rollback(conn, result, null);
						} else{
							conn.commit();
						}
					}
				} catch (SQLException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits history.", ex);
					this.rollback(conn, result, null);
				} finally{
					// Close connection
					if (conn != null) ResourceManager.close(conn);
				}
			}
				break;
			case SUBMIT:{
				Connection conn = null;
				String errorKey = "errors.tbd.submit";
				try{
					List<TaxBenefitDeclaration> userCurrentDec = getUserTaxBenefitsDeclaration(userId, financialYear);
					// Update taxBenefitForm with user tax benefit declaration
					taxBenefitForm.setTaxBenefitDeclaration(userCurrentDec);
					// Begin transaction
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					// update tax benefit declartions
					updateTaxBenefitDeclaration(userId, financialYear, taxBenefitForm, errorKey, conn);
					// Submit tax benefit declarations
					result = submitTaxBenefitDeclaration(userId, financialYear, taxBenefitForm, conn);
					if (result.getActionMessages().size() > 0){
						logger.error("Unable to save and submit. Problem in submitting tax benefit declaration. ");
						this.rollback(conn, result, null);
					} else{
						conn.commit();
					}
				} catch (SQLException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits history.", ex);
					this.rollback(conn, result, errorKey);
				} catch (TaxBenefitDeclarationDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits history.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
				} catch (TaxBenefitDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits history.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
				} finally{
					// Close connection
					if (conn != null) ResourceManager.close(conn);
				}
			}
				break;
			case SAVE:{
				Connection conn = null;
				try{
					// Begin transaction
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					result = saveTaxBenefitDeclaration(userId, financialYear, taxBenefitForm, conn);
					if (result.getActionMessages().size() > 0){
						logger.error("Unable to save and submit. Problem in saving tax benefit declaration. ");
						this.rollback(conn, result, null);
					} else{
						conn.commit();
					}
				} catch (SQLException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits history.", ex);
					this.rollback(conn, result, null);
				} finally{
					// Close connection
					if (conn != null) ResourceManager.close(conn);
				}
			}
				break;
			case SAVETDS:{
				String[] tdsArray = taxBenefitForm.getTdsArray();
				TdsDao tdsDao = TdsDaoFactory.create();
				boolean recalcFalg = taxBenefitForm.getNewFlag();
				int recalcuser = taxBenefitForm.getUserId();
				String[] tdsDet = null;
				Tds tds = null;
				try{
					for (String eachTds : tdsArray){
						tdsDet = eachTds.split(",");
						tds = tdsDao.findByPrimaryKey(tdsDet[0] != "" ? Integer.valueOf(tdsDet[0]) : 0);
						if (tds != null){
							tds.setAmount(tdsDet[1] != "" ? tdsDet[1] : "");
							tds.setStatus(tdsDet[2] != "" ? Integer.valueOf(tdsDet[2]) : 0);
							tdsDao.update(tds.createPk(), tds);
						}
					}
					if (recalcFalg == true){
						new TDSUtility().reCalculate(recalcuser);
					}
				} catch (TdsDaoException e){
					logger.error("There is a TdsDaoException occured while updating the records for the TDS table " + e.getMessage());
				}
			}
				break;
			default:{
				logger.info("TaxBenefitDeclarationModel defualt case blank implementation.");
			}
				break;
		}
		request.setAttribute("actionForm", taxBenefitForm);
		return result;
	}

	protected ActionResult saveTaxBenefitDeclaration(int userId, String financialYear, TaxBenefitForm taxBenefitForm, Connection conn) {
		ActionResult result = new ActionResult();
		String paramTaxBenefit = taxBenefitForm.getTaxBenefit();
		String paramMainComments = taxBenefitForm.getMainComment();
		// Comments required only when you submit details.
		// String paramComment=taxBenefitForm.getMainComment();
		String errorKey = "errors.tbd.save.database";
		if (paramTaxBenefit == null || paramTaxBenefit.length() <= 0){
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
		} else{
			try{
				TaxBenefitDeclarationDao taxBenefitDecProvider = TaxBenefitDeclarationDaoFactory.create(conn);
				List<TaxBenefitDeclaration> benefitDec = parseTaxBenefit(paramTaxBenefit, userId);
				for (TaxBenefitDeclaration dec : benefitDec){
					// insert record
					taxBenefitDecProvider.insert(dec);
				}
				// Create new request with status new
				EmpSerReqMapDao empSerReqProvider = EmpSerReqMapDaoFactory.create(conn);
				TaxBenefitReqDao taxBenefitReqProvider = TaxBenefitReqDaoFactory.create(conn);
				String status = Status.NEW;
				Date createdOn = new Date();
				Integer requestTypeId, regionId;
				requestTypeId = regionId = null;
				String taxBenefitReqId = getTaxBenefitRequestId(userId);
				RequestType taxBenefitReqType = getTaxBenefitRequestType();
				requestTypeId = taxBenefitReqType.getId();
				RegionsDao regionProvider = RegionsDaoFactory.create();
				Regions[] regions = regionProvider.findByDynamicWhere("ID = (SELECT D.REGION_ID FROM DIVISON D " + "WHERE D.ID = (SELECT L.DIVISION_ID FROM LEVELS L " + "WHERE L.ID = (SELECT U.LEVEL_ID FROM USERS U WHERE ID = ?)))", new Object[] { userId });
				if (regions.length > 0){
					regionId = regions[0].getId();
				} else{
					regionId = 0;
				}
				ProcessChain taxBenefitPC = getTaxBenefitProcessChain(userId);
				int taxBenefitPCId = taxBenefitPC.getId();
				String messageBody = "";
				String processChainNotifier = taxBenefitPC.getNotification();
				String processChainHandler = taxBenefitPC.getHandler();
				ProcessEvaluator pEval = new ProcessEvaluator();
				logger.debug("Executing process chain for tax benefit declaration/update for USERID: " + userId);
				logger.debug("Assuming approval is not required for tax benefit declaration process chain.");
				Integer[] handlers = pEval.handlers(processChainHandler, userId);
				String siblings = "";
				for (Integer element : handlers){
					if (element == userId) continue;
					siblings += element + ",";
				}
				if (siblings.length() > 0){
					siblings = siblings.substring(0, siblings.lastIndexOf(","));
				}
				// Create employee servicer request map entry
				EmpSerReqMap taxBenefitEsr = new EmpSerReqMap();
				taxBenefitEsr.setSubDate(createdOn);
				taxBenefitEsr.setReqId(taxBenefitReqId);
				taxBenefitEsr.setReqTypeId(requestTypeId);
				taxBenefitEsr.setRegionId(regionId);
				taxBenefitEsr.setRequestorId(userId);
				taxBenefitEsr.setProcessChainId(taxBenefitPCId);
				taxBenefitEsr.setNotify(processChainNotifier);
				taxBenefitEsr.setSiblings(siblings);
				empSerReqProvider.insert(taxBenefitEsr);
				int esrMapId = taxBenefitEsr.getId();
				// Create tax benefit declaration request entry
				TaxBenefitReq taxBenefitReq = new TaxBenefitReq();
				taxBenefitReq.setEsrMapId(esrMapId);
				taxBenefitReq.setStatus(status);
				taxBenefitReq.setRequesterId(userId);
				taxBenefitReq.setCreatedOn(createdOn);
				taxBenefitReq.setMessageBody(messageBody);// Empty message body
				taxBenefitReq.setComments(paramMainComments);
				taxBenefitReqProvider.insert(taxBenefitReq);
				// Commit transaction
				conn.commit();
				// Save the new set of declaration for returning and/or further
				// processing.
				taxBenefitForm.setTaxBenefitRequestId(taxBenefitReq.getId());
				taxBenefitForm.setTaxBenefitDeclaration(benefitDec);
			} catch (TaxBenefitDeclarationDaoException ex){
				logger.error("Unable to save tax benefit declaration. Problem in information to be saved.", ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (TaxBenefitDaoException ex){
				logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits master.", ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (SQLException ex){
				logger.error("Unable to save tax benefit declaration. Problem in information to be saved.", ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (TaxBenefitReqDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in creating tax benefit request entry.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (EmpSerReqMapDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in creating employee service request map entry.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (ModulesDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding tax benefit modules.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (ProcessChainDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding process chain for tax benefit modules.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (RequestTypeDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding request type.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (RegionsDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding region id for user: " + userId + ".", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			}
		}
		return (result);
	}

	protected ActionResult submitTaxBenefitDeclaration(int userId, String financialYear, TaxBenefitForm taxBenefitForm, Connection conn) {
		ActionResult result = new ActionResult();
		int paramTaxBenefitReqId = taxBenefitForm.getTaxBenefitRequestId();
		String paramComment = taxBenefitForm.getMainComment();
		String errorKey = "errors.tbd.save.database";
		List<TaxBenefitDeclaration> benefitDec = taxBenefitForm.getTaxBenefitDeclaration();
		if (benefitDec == null || benefitDec.size() <= 0){
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
		} else{
			String taxBenefitDeclarationHtml = null;
			try{
				taxBenefitDeclarationHtml = getTaxBenefitDeclarationHtml(benefitDec);
				// Process processChain
				doProcessChain(conn, paramTaxBenefitReqId, userId, financialYear, taxBenefitDeclarationHtml, paramComment);
			} catch (InboxDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in creating inbox entries.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (ModulesDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding tax benefit modules.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (ProcessChainDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding process chain for tax benefit modules.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (ProfileInfoDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding profile for notifier/handler/requester.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (RequestTypeDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding request type.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (TaxBenefitReqDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in creating tax benefit request entry.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (RegionsDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding region id for user: " + userId + ".", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (EmpSerReqMapDaoException e){
				logger.error("Unable to save tax benefit declaration. Problem in creating EMP_SER_REQ_MAP entry.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (FileNotFoundException e){
				logger.error("Unable to save tax benefit declaration. Problem in finding email templates.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} catch (Exception e){
				logger.error("Unable to save tax benefit declaration. Unknown problem in application.", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			}
		}
		return (result);
	}

	protected String getTaxBenefitDeclarationHtml(List<TaxBenefitDeclaration> benefitDec) {
		final String userTaxBenefitHistory = "FINANCIAL_YEAR=? AND USERID=? AND BENEFIT_ID=?";
		String taxBenefitDeclarationHtml = null;
		StringBuilder taxBenefitInfo = new StringBuilder(TAX_BENEFIT_INFO_HTML);
		int requesterId = benefitDec != null && benefitDec.size() > 0 ? benefitDec.get(0).getUserid() : 0;
		String financialYear = benefitDec != null && benefitDec.size() > 0 ? benefitDec.get(0).getFinancialYear() : FBPModel.getFinancialYear();
		TaxBenefitAcceptDao taxBenefitAcceptProvider = TaxBenefitAcceptDaoFactory.create();
		if (benefitDec != null && benefitDec.size() > 0){
			for (TaxBenefitDeclaration dec : benefitDec){
				String previousAmt = null;
				String updatedAmt = dec.getAmount();
				int taxBenefitId = dec.getBenefitId();
				TaxBenefitAccept[] tbdHistory;
				try{
					tbdHistory = taxBenefitAcceptProvider.findByDynamicWhere(userTaxBenefitHistory, new Object[] { financialYear, requesterId, taxBenefitId });
					if (tbdHistory.length > 0){
						previousAmt = tbdHistory[0].getAmount();
					} else{
						previousAmt = "-";
					}
				} catch (TaxBenefitAcceptDaoException e){
					logger.error("Unable to find TAX_BENEFIT_ACCEPT for user USERID: " + requesterId + ", FINANCIAL_YEAR: " + financialYear + ", BENEFIT_ID: " + taxBenefitId);
					previousAmt = "-";
				}
				// create html table send in email
				taxBenefitInfo.append("<tr><td>");
				taxBenefitInfo.append(dec.getBenefitName());
				taxBenefitInfo.append("</td><td>");
				taxBenefitInfo.append(previousAmt);
				taxBenefitInfo.append("</td><td>");
				taxBenefitInfo.append(updatedAmt);
				taxBenefitInfo.append("</td><td>");
				String comments = dec.getComments();
				comments = comments != null ? comments : "-";
				taxBenefitInfo.append(comments);
				taxBenefitInfo.append("</td></tr>");
			}
			taxBenefitInfo.append("</table>");
			taxBenefitDeclarationHtml = taxBenefitInfo.toString();
		} else{
			logger.debug("No tax benefit declaration data found to create html representation.");
		}
		return (taxBenefitDeclarationHtml);
	}

	/**
	 * Parse string to a list of TaxBenefitDeclaration objects
	 * id1=value1|comment1~=~id2=value3|comment2~=~id3=value3|comment3~=~ eg.
	 * 1=2300.0|first dec~=~2=10222.5|sodexo~=~3=900.0|test
	 * 
	 * @param paramTaxBenefit
	 * @param userId
	 * @return
	 * @throws TaxBenefitDaoException
	 */
	public List<TaxBenefitDeclaration> parseTaxBenefit(String paramTaxBenefit, int userId) throws TaxBenefitDaoException {
		TaxBenefitDao taxBenefitProvider = TaxBenefitDaoFactory.create();
		TaxBenefit taxBenefitDb = null;
		List<TaxBenefitDeclaration> benefitDec = new ArrayList<TaxBenefitDeclaration>();
		// Get all id=value pairs
		String[] taxBenefitValues = paramTaxBenefit.split(PARAM_TAXBENEFIT_SEP);// ~=~ tildeequaltotilde
		String financialYear = FBPModel.getFinancialYear();
		for (String element : taxBenefitValues){
			if (element.length() > 0){ // Get id and value
				String[] taxBenefit = element.split(PARAM_TAXID_SEP);// = equal to symbol
				if (taxBenefit.length == 2){
					int taxBenefitId = Integer.parseInt(taxBenefit[0]);
					taxBenefitDb = taxBenefitProvider.findByPrimaryKey(taxBenefitId);
					String taxBenefitName = taxBenefitDb != null ? taxBenefitDb.getName() : "???";
					String taxBenefitCategory = taxBenefitDb != null ? taxBenefitDb.getCategory() : "???";
					String taxAllValue = taxBenefit[1];
					String[] taxValue = taxAllValue.split(PARAM_TAXVALUE_SEP);// | Pipe symbol
					//					float taxAmount = taxValue.length > 0 ? Float.valueOf(taxValue[TaxBenefitDeclarationModel.PARAM_TAXAMOUNT_INDEX]).longValue() : 0.0f;
					String taxAmountStr = String.valueOf(taxValue.length > 0 ? taxValue[TaxBenefitDeclarationModel.PARAM_TAXAMOUNT_INDEX] : "");
					String comments = taxValue.length > 1 ? taxValue[TaxBenefitDeclarationModel.PARAM_COMMENT_INDEX] : null;
					TaxBenefitDeclaration dec = new TaxBenefitDeclaration();
					dec.setId(0);
					dec.setBenefitId(taxBenefitId);
					dec.setUserid(userId);
					dec.setAmount(taxAmountStr);
					dec.setComments(comments);
					dec.setFinancialYear(financialYear);
					dec.setBenefitName(taxBenefitName);
					dec.setBenefitCategory(taxBenefitCategory);
					benefitDec.add(dec);
				}
			}
		}
		return (benefitDec);
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		String rType = form.getrType();
		TaxBenefitForm taxBenefitForm = (TaxBenefitForm) form;
		ReceiveTypes paramRType = ReceiveTypes.getValue(rType);
		Login login = Login.getLogin(request);
		int userId = login.getUserId();
		String financialYear = FBPModel.getFinancialYear();
		String errorKey = "errors.tbd.receive";
		switch (paramRType) {
			case RECEIVEALL:{
				try{
					boolean newFlag = true;
					InboxDao inboxProvider = InboxDaoFactory.create();
					TaxBenefitReqDao tbReqProvider = TaxBenefitReqDaoFactory.create();
					EmpSerReqMapDao esrMapProvider = EmpSerReqMapDaoFactory.create();
					TaxBenefitReq[] userTbRequest = tbReqProvider.findWhereRequesterIdEquals(userId);
					for (TaxBenefitReq element : userTbRequest){
						boolean editFlag = true;
						boolean cancelFlag = false;
						boolean submitFlag = false;
						int esrMapId = element.getEsrMapId();
						EmpSerReqMap serviceReq = esrMapProvider.findByPrimaryKey(esrMapId);
						String reqId = serviceReq != null ? serviceReq.getReqId() : "???";
						Date reqDate = element.getCreatedOn();
						element.setReqId(reqId);
						element.setFinancialYear(getFinancialYear(reqDate));
						String reqStatus = element.getStatus();
						// Set newFlag
						if (element.getFinancialYear().equalsIgnoreCase(financialYear)){
							newFlag = false;
							// Set editFlag, cancelFlag and submitFlag
							if (reqStatus.equalsIgnoreCase(Status.SUBMITTED) || reqStatus.equalsIgnoreCase(Status.RESUBMITTED)){
								editFlag = false;
								cancelFlag = true;
							} else if (reqStatus.equalsIgnoreCase(Status.NEW) || reqStatus.equalsIgnoreCase(Status.UPDATED)){
								submitFlag = true;
							}
							element.setEditFlag(editFlag);
							element.setSubmitFlag(submitFlag);
							element.setCancelFlag(cancelFlag);
							// Set email message body to null
							element.setMessageBody(null);
						} else{
							logger.debug("Tax benefit request corresponds to financial " + "year other than current year and therefore cannot be edited. TAX_BENEFIT_REQ.ID: " + element.getId());
						}
					}
					taxBenefitForm.setNewFlag(newFlag);
					taxBenefitForm.setRequests(userTbRequest);
					// Setting handleFlag and count of requests to handle
					final String WHERE_INBOX = "ESR_MAP_ID IN (SELECT ESR_MAP_ID FROM TAX_BENEFIT_REQ) AND RECEIVER_ID=ASSIGNED_TO AND RECEIVER_ID=?";
					Inbox[] myTask = inboxProvider.findByDynamicWhere(WHERE_INBOX, new Object[] { userId });
					int toDoCount = myTask.length;
					if (toDoCount > 0){
						taxBenefitForm.setHandleFlag(true);
						taxBenefitForm.setToDoCount(toDoCount);
					} else if (ModelUtiility.hasModulePermission(login, 17) || JDBCUtiility.getInstance().getRowCount(" FROM TAX_BENEFIT_REQ WHERE ACTION_BY=?", new Object[] { userId }) > 0) taxBenefitForm.setHandleFlag(true);
				} catch (TaxBenefitReqDaoException ex){
					logger.error("Unable to find tax benefit requests for user USERID: " + userId + ". Problem in reading tax benefit requests.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, ex.getMessage()));
				} catch (EmpSerReqMapDaoException ex){
					logger.error("Unable to find tax benefit requests for user USERID: " + userId + ". Problem in reading employee service requests.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, ex.getMessage()));
				} catch (InboxDaoException ex){
					logger.error("Unable to find tax benefit requests to be hadled for user USERID: " + userId + ". Problem in reading inbox.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, ex.getMessage()));
				}
			}
				break;
			case RECEIVE:{
				getTaxBenefitDeclarationForReceive(request, result, taxBenefitForm, userId, errorKey, false);
			}
				break;
			case RECEIVEALLTOHANDLE:{
				try{
			/*		SELECT 
				     I.ESR_MAP_ID 
				FROM
				    INBOX I
				left join
				TAX_BENEFIT_REQ T
				on  I.ESR_MAP_ID = T.ESR_MAP_ID
				WHERE
				   I.RECEIVER_ID = I.ASSIGNED_TO
				        AND I.RECEIVER_ID = 16
				ORDER BY CREATED_ON DESC;*/
					
					String WHERE_TBREQ = "ACTION_BY=? " + "OR EXISTS(" + "SELECT I.ESR_MAP_ID FROM INBOX I left join TAX_BENEFIT_REQ T on I.ESR_MAP_ID = T.ESR_MAP_ID " + "WHERE  I.RECEIVER_ID=I.ASSIGNED_TO AND INBOX.RECEIVER_ID=?" + ") ORDER BY CREATED_ON DESC";
					
		//		String WHERE_TBREQ = "ACTION_BY=? " + "OR EXISTS(" + "SELECT ESR_MAP_ID FROM INBOX " + "WHERE INBOX.ESR_MAP_ID=TAX_BENEFIT_REQ.ESR_MAP_ID AND INBOX.RECEIVER_ID=INBOX.ASSIGNED_TO AND INBOX.RECEIVER_ID=?" + ") ORDER BY CREATED_ON DESC";
					Object[] params = new Object[] { userId, userId };
				
					if (ModelUtiility.hasModulePermission(login, 17)){
					//	WHERE_TBREQ = "STATUS = 'Submitted'";
					//	params = null;
					}
					TaxBenefitReqDao tbReqProvider = TaxBenefitReqDaoFactory.create();
					EmpSerReqMapDao esrMapProvider = EmpSerReqMapDaoFactory.create();
				//	TaxBenefitReq[] handleTbRequest = tbReqProvider.findByDynamicWhere(WHERE_TBREQ, params);
					TaxBenefitReq[] handleTbRequest = tbReqProvider.findByDynamicWhereTaxHandle();
					
					int count = 0;
					for (TaxBenefitReq element : handleTbRequest){
						boolean actionFlag = false;
						int esrMapId = element.getEsrMapId();
						EmpSerReqMap serviceReq = esrMapProvider.findByPrimaryKey(esrMapId);
						String reqId = serviceReq != null ? serviceReq.getReqId() : "???";
						Date reqDate = element.getCreatedOn();
						element.setReqId(reqId);
						element.setFinancialYear(getFinancialYear(reqDate));
						String reqStatus = element.getStatus();
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
						UsersDao usersDao = UsersDaoFactory.create();
						try{
							ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(element.getRequesterId());
							element.setName(profileInfo != null ? profileInfo.getFirstName() + " " + profileInfo.getLastName() : "");
							Users user = usersDao.findByPrimaryKey(element.getRequesterId());
							element.setEmpId(user != null ? user.getEmpId() : 0);
						} catch (ProfileInfoDaoException ex){
							logger.error("There is ProfileInfoDaoException occured while getting the records from the profileInfo table" + ex.getMessage());
						} catch (UsersDaoException e){
							logger.error("There is usersDao occured while getting the records from the Users table" + e.getMessage());
						}
						if (element.getStatus().equalsIgnoreCase(Status.SUBMITTED) || element.getStatus().equalsIgnoreCase(Status.RESUBMITTED)) count++;
						// Set actionFlag
						if (element.getFinancialYear().equalsIgnoreCase(financialYear) && (reqStatus.equalsIgnoreCase(Status.SUBMITTED) || reqStatus.equalsIgnoreCase(Status.RESUBMITTED))){
							actionFlag = true;
						}
						element.setActionFlag(actionFlag);
					}
					taxBenefitForm.setCount(count);
					taxBenefitForm.setRequests(handleTbRequest);
					// Setting handleFlag and count of requests to handle
					final String WHERE_INBOX = "ESR_MAP_ID IN (SELECT ESR_MAP_ID FROM TAX_BENEFIT_REQ) AND RECEIVER_ID=ASSIGNED_TO AND RECEIVER_ID=?";
					InboxDao inboxProvider = InboxDaoFactory.create();
					Inbox[] myTask = inboxProvider.findByDynamicWhere(WHERE_INBOX, new Object[] { userId });
					int toDoCount = myTask.length;
					if (toDoCount > 0){
						taxBenefitForm.setHandleFlag(true);
						taxBenefitForm.setToDoCount(toDoCount);
					}
				} catch (TaxBenefitReqDaoException ex){
					logger.error("Unable to find tax benefit requests for user USERID: " + userId + ". Problem in reading tax benefit requests.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, ex.getMessage()));
				} catch (EmpSerReqMapDaoException ex){
					logger.error("Unable to find tax benefit requests for user USERID: " + userId + ". Problem in reading employee service requests.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, ex.getMessage()));
				} catch (InboxDaoException ex){
					logger.error("Unable to find tax benefit requests to be hadled for user USERID: " + userId + ". Problem in reading inbox.", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, ex.getMessage()));
				}
			}
				break;
			case RECEIVETOHANDLE:{
				getTaxBenefitDeclarationForReceive(request, result, taxBenefitForm, userId, errorKey, false);
			}
				break;
			case RECEIVETAXBENEFIT:{
				int paramEmpId = taxBenefitForm.getEmpId();
				try{
					UsersDao usersProvider = UsersDaoFactory.create();
					Users forUser = usersProvider.findByPrimaryKey(paramEmpId);
					if (forUser != null){
						int forUserId = forUser.getId();
						TaxBenefitReqDao taxBenefitReqProvider = TaxBenefitReqDaoFactory.create();
						TaxBenefitReq[] taxBenefitReq = taxBenefitReqProvider.findWhereRequesterIdEquals(forUserId);
						if (taxBenefitReq.length >0){
							int tbrId = taxBenefitReq[0].getId();
							taxBenefitForm.setTaxBenefitRequestId(tbrId);
							getTaxBenefitDeclarationForReceive(request, result, taxBenefitForm, userId, errorKey, true);
						} else{
							logger.error("Unable to find tax benefit request for EMP_ID: " + paramEmpId + ", USER_ID: " + forUserId);
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
						}
					} else{
						logger.error("Unable to find user for EMP_ID: " + paramEmpId);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
					}
				} catch (UsersDaoException ex){
					logger.error("Unable to access users information for EMP_ID: " + paramEmpId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
				} catch (TaxBenefitReqDaoException e){
					logger.error("Unable to find tax benefit request for EMP_ID: " + paramEmpId);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
				}
			}
				break;
			case RECEIVEMASTER:{
				try{
					TaxBenefitDao taxBenefitProvider = TaxBenefitDaoFactory.create();
					List<String> taxBenefitCategories = getTaxBenefitCategories();
					List<TaxBenefitDetail> taxBenefitDetails = new ArrayList<TaxBenefitDetail>();
					for (String category : taxBenefitCategories){
						TaxBenefitDetail tbDetail = new TaxBenefitDetail();
						tbDetail.setHead(category);
						List<TaxBenefitDeclaration> tbd = new ArrayList<TaxBenefitDeclaration>();
						TaxBenefit[] taxBenefits = taxBenefitProvider.findByDynamicWhere("CATEGORY=? ORDER BY ID", new Object[] { category });
						for (TaxBenefit element : taxBenefits){
							TaxBenefitDeclaration temp = new TaxBenefitDeclaration();
							temp.setBenefitId(element.getId());
							temp.setBenefitName(element.getName());
							temp.setDescription(element.getDescription());
							tbd.add(temp);
						}
						if (taxBenefits.length > 0){
							tbDetail.setBenefit(tbd);
							taxBenefitDetails.add(tbDetail);
						} else{
							continue;
						}
					}
					// Set employee information
					// empid, empname, dept, div, desig, level
					taxBenefitForm = getEmployeeInfo(userId, taxBenefitForm);
					// Set master details
					taxBenefitForm.setTaxBenefitDetails(taxBenefitDetails);
				} catch (TaxBenefitDaoException e){
					logger.error("Unable to find tax benefits. Problem in reading tax benefits.", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
				} catch (UsersDaoException e){
					logger.error("Unable to find user USERS.ID: " + userId + ". Problem in reading users.", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
				} catch (ProfileInfoDaoException e){
					logger.error("Unable to find profile info for USERS.ID: " + userId + ". Problem in reading profile information.", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
				} catch (LevelsDaoException e){
					logger.error("Unable to find level for USERS.ID: " + userId + ". Problem in reading levels.", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
				} catch (DivisonDaoException e){
					logger.error("Unable to find division for USERS.ID: " + userId + ". Problem in reading divisions.", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
				}
			}
				break;
			case RECEIVETDS:{
				List<Tds> tds = new TDSUtility().getallmonthsTds(taxBenefitForm.getEmpId(),taxBenefitForm.getFinancialYear());
				taxBenefitForm.setTds(tds);
				try{
					Users user = UsersDaoFactory.create().findByPrimaryKey(taxBenefitForm.getEmpId());
					taxBenefitForm.setEmpId(user != null ? user.getEmpId() : 0);
					ProfileInfo profileInfo = ProfileInfoDaoFactory.create().findByPrimaryKey(user.getProfileId());
					taxBenefitForm.setEmpName(profileInfo != null ? profileInfo.getFirstName() + " " + profileInfo.getLastName() : "");
				} catch (UsersDaoException e){
					logger.error("There is a usersDaoException occured while getting the user from users table for the user" + e.getMessage());
				} catch (ProfileInfoDaoException e){
					logger.error("There is a ProfileInfoDaoException occured while getting the user from users table for the user" + e.getMessage());
				}
				request.setAttribute("actionForm", taxBenefitForm);
			}
				break;
			default:{
				int tempUserId = taxBenefitForm.getUserId();
				String fbpBenefitsDetails = taxBenefitForm.getFbpBenefit();
				String taxBenefit = taxBenefitForm.getTaxBenefit();
				try{
					List<Tds> tds = new TDSUtility().reCalculate(tempUserId, fbpBenefitsDetails, taxBenefit);
					for (Tds element : tds)
						logger.info(element);
				} catch (TaxBenefitDaoException e){
					e.printStackTrace();
				}
				logger.debug("TaxBenefitDeclaration receive default case blank implementation.");
			}
				break;
		}
		return result;
	}

	/**
	 * @param request
	 * @param result
	 * @param taxBenefitForm
	 * @param userId
	 * @param errorKey
	 */
	private void getTaxBenefitDeclarationForReceive(HttpServletRequest request, ActionResult result, TaxBenefitForm taxBenefitForm, int userId, String errorKey, boolean isHandler) {
		int paramTaxBenefitReqId = taxBenefitForm.getTaxBenefitRequestId();
		TaxBenefitForm taxBenefitFormResult = null;
		try{
			taxBenefitFormResult = getTaxBenefitDetails(paramTaxBenefitReqId, isHandler, result ,taxBenefitForm.getFinancialYear());
			request.setAttribute("actionForm", taxBenefitFormResult);
		} catch (TaxBenefitReqDaoException e){
			logger.error("Unable to find tax benefit requests for user USERID: " + userId + ". Problem in reading tax benefit requests.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (UsersDaoException e){
			logger.error("Unable to find user USERS.ID: " + userId + ". Problem in reading users.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (ProfileInfoDaoException e){
			logger.error("Unable to find profile info for USERS.ID: " + userId + ". Problem in reading profile information.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (LevelsDaoException e){
			logger.error("Unable to find level for USERS.ID: " + userId + ". Problem in reading levels.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (DivisonDaoException e){
			logger.error("Unable to find division for USERS.ID: " + userId + ". Problem in reading divisions.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (TaxBenefitDeclarationDaoException e){
			logger.error("Unable to find tax benefit declaration for TAX_BENEFIT_REQ.ID: " + paramTaxBenefitReqId + ". Problem in reading user tax benefit declaration.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (TaxBenefitDaoException e){
			logger.error("Unable to find tax benefit for TAX_BENEFIT_REQ.ID: " + paramTaxBenefitReqId + ". Problem in reading tax benefit.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (TbdHistoryDaoException e){
			logger.error("Unable to find tax benefit history for TAX_BENEFIT_REQ.ID: " + paramTaxBenefitReqId + ". Problem in reading user tax benefit history.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		} catch (TaxBenefitAcceptDaoException e){
			logger.error("Unable to find tax benefit accepted for TAX_BENEFIT_REQ.ID: " + paramTaxBenefitReqId + ". Problem in reading user tax benefit accept.", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, e.getMessage()));
		}
	}

	protected TaxBenefitForm getTaxBenefitDetails(int taxBenefitReqId, boolean isHandler, ActionResult result,String financial_Year) throws TaxBenefitReqDaoException, UsersDaoException, ProfileInfoDaoException, LevelsDaoException, DivisonDaoException, TaxBenefitDeclarationDaoException, TaxBenefitDaoException, TbdHistoryDaoException, TaxBenefitAcceptDaoException {
		TaxBenefitForm tbForm = new TaxBenefitForm();
		TaxBenefitReqDao taxBenefitReqProvider = TaxBenefitReqDaoFactory.create();
		TaxBenefitReq taxBenefitReq = taxBenefitReqProvider.findByPrimaryKey(taxBenefitReqId);
		tbForm.setTaxBenefitRequestId(taxBenefitReqId);
		if (taxBenefitReq == null){
			logger.error("Unable to get tax benefit detail for TAX_BENEFIT_REQ.ID: " + taxBenefitReqId);
			throw new TaxBenefitReqDaoException("Tax benefit request not found for ID: " + taxBenefitReqId);
		} else{
			int userId = taxBenefitReq.getRequesterId();
			Date dateOfAction = taxBenefitReq.getCreatedOn();
			dateOfAction = dateOfAction == null ? taxBenefitReq.getCreatedOn() : dateOfAction;
			String financialYear = getFinancialYear(dateOfAction);
			// String status=taxBenefitReq.getStatus();
			// Get general details EMPID, FIRST NAME, LAST NAME, DEPARTMENT,
			// LEVEL
			UsersDao userProvider = UsersDaoFactory.create();
			Users requester = userProvider.findByPrimaryKey(userId);
			if (requester != null){
				tbForm = getEmployeeInfo(userId, tbForm);
				// Get status and fill current declarations
				List<String> taxBenefitCategories = getTaxBenefitCategories();
				// Read from TAX_BENEFIT_DECLARATION
				TaxBenefitDao taxBenefitProvider = TaxBenefitDaoFactory.create();
				List<TaxBenefitDetail> benefitDetails = new ArrayList<TaxBenefitDetail>();
				TaxBenefitDeclarationDao taxBenefitDecProvider = TaxBenefitDeclarationDaoFactory.create();
				final String WHERE_TBDEC = "BENEFIT_ID IN (SELECT ID FROM TAX_BENEFIT WHERE CATEGORY=? ) AND USERID=? AND FINANCIAL_YEAR=?";
				for (String category : taxBenefitCategories){
					TaxBenefitDetail tbDetail = new TaxBenefitDetail();
					tbDetail.setHead(category);
					List<TaxBenefitDeclaration> sortedTbDec = new ArrayList<TaxBenefitDeclaration>();
					if (!isHandler){// Get details from TAX_BENEFIT_DECLARATION table if not handler
						/*	TaxBenefitDeclaration[] userTbDec = taxBenefitDecProvider.findByDynamicWhere(WHERE_TBDEC, new Object[]{category, userId, financialYear });	*/
						TaxBenefit[] benefits = taxBenefitProvider.findByDynamicWhere(" CATEGORY = ? ORDER BY ID ", new Object[] { category });
						//TaxBenefitDeclaration[] declarations = new TaxBenefitDeclaration[benefits.length];
						TaxBenefitDeclaration declaration = null;
						for (TaxBenefit eachBenefit : benefits){
							declaration = new TaxBenefitDeclaration();
							declaration.setUserid(userId);
							declaration.setBenefitId(eachBenefit.getId());
							declaration.setBenefitName(eachBenefit.getName());
							declaration.setDescription(eachBenefit.getDescription());
							TaxBenefitDeclaration[] taxes = taxBenefitDecProvider.findByDynamicWhere("BENEFIT_ID = ? AND USERID = ? AND FINANCIAL_YEAR = ? ", new Object[] { eachBenefit.getId(), userId, financialYear });
							if (taxes != null && taxes.length == 1){
								declaration.setComments(taxes[0].getComments());
								declaration.setFinancialYear(financialYear);
								declaration.setId(taxes[0].getId());
								declaration.setAmount(taxes[0].getAmount());
							}
							sortedTbDec.add(declaration);
						}
						Collections.sort(sortedTbDec, new Comparator<TaxBenefitDeclaration>() {

							@Override
							public int compare(TaxBenefitDeclaration o1, TaxBenefitDeclaration o2) {
								return new Integer(o1.getId()).compareTo(new Integer(o2.getId()));
							};
						});
						/*		for (TaxBenefitDeclaration dec : userTbDec) {
									TaxBenefit taxBenefit = taxBenefitProvider.findByPrimaryKey(dec.getBenefitId());
									String benefitName = taxBenefit != null ? taxBenefit.getName() : "???";
									String description = taxBenefit != null ? taxBenefit.getDescription() : "-";
									dec.setBenefitName(benefitName);
									dec.setDescription(description);
								}
								sortedTbDec = getSortedTbdByBenefitName(userTbDec);			*/
					} else{// Get details from TAX_BENEFIT_ACCEPT table if handler
						TaxBenefitAcceptDao taxBenefitAcceptProvider = TaxBenefitAcceptDaoFactory.create();
						TaxBenefitAccept[] tbAccepted = taxBenefitAcceptProvider.findByDynamicWhere(WHERE_TBDEC, new Object[] { category, userId, financial_Year });
						TaxBenefitDeclaration[] tbd = new TaxBenefitDeclaration[tbAccepted.length];
						int index = 0;
						for (TaxBenefitAccept element : tbAccepted){
							TaxBenefit taxBenefit = taxBenefitProvider.findByPrimaryKey(element.getBenefitId());
							String benefitName = taxBenefit != null ? taxBenefit.getName() : "???";
							String description = taxBenefit != null ? taxBenefit.getDescription() : "-";
							element.setBenefitName(benefitName);
							element.setDescription(description);
							tbd[index++] = element.getTaxBenefitDeclaration();
						}
						sortedTbDec = getSortedTbdByBenefitName(tbd);
					}
					tbDetail.setBenefit(sortedTbDec);
					if (sortedTbDec.size() > 0) benefitDetails.add(tbDetail);// Add Head only when details exists
					else continue;
				}
				if (benefitDetails.size() == 0) result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.noAcceptedRecords"));
				tbForm.setTaxBenefitDetails(benefitDetails);
				// Get previous/history declarations up-to this request date
				List<TaxBenefitHistory> taxBenefitHistory = new ArrayList<TaxBenefitHistory>();
				TbdHistoryDao tbdHistoryProvider = TbdHistoryDaoFactory.create();
				List<Date> historyDate = getTaxBenefitUpdatedOn(userId, financialYear, dateOfAction);
				for (Date updateDate : historyDate){
					TaxBenefitHistory tbHis = new TaxBenefitHistory();
					tbHis.setUpdatedOnDate(updateDate);
					tbHis.setUpdateOn(PortalUtility.getdd_MM_yyyy(updateDate));
					List<TaxBenefitDetail> tbDetails = new ArrayList<TaxBenefitDetail>();
					for (String category : taxBenefitCategories){
						TaxBenefitDetail tbDetail = new TaxBenefitDetail();
						tbDetail.setHead(category);
						TbdHistory[] userTbDec = tbdHistoryProvider.findByDynamicWhere("BENEFIT_ID IN (SELECT ID FROM TAX_BENEFIT WHERE CATEGORY=?) AND UPDATED_ON=?  AND USERID=? AND FINANCIAL_YEAR=?", new Object[] { category, updateDate, userId, financialYear });
						TaxBenefitDeclaration[] tbd = new TaxBenefitDeclaration[userTbDec.length];
						int index = 0;
						for (TbdHistory dec : userTbDec){
							TaxBenefit taxBenefit = taxBenefitProvider.findByPrimaryKey(dec.getBenefitId());
							String benefitName = taxBenefit != null ? taxBenefit.getName() : "???";
							String description = taxBenefit != null ? taxBenefit.getDescription() : "-";
							dec.setBenefitName(benefitName);
							dec.setDescription(description);
							tbd[index++] = dec.getTaxBenefitDeclaration();
						}
						List<TaxBenefitDeclaration> sortedTbDec = getSortedTbdByBenefitName(tbd);
						tbDetail.setBenefit(sortedTbDec);
						if (userTbDec.length > 0){
							tbDetails.add(tbDetail);// Add Head only when
													// details exists
						} else continue;
					}
					if (tbDetails.size() > 0) taxBenefitHistory.add(tbHis);// Add history only if details exists
					else continue;
				}
				tbForm.setTaxBenefitHistory(taxBenefitHistory);
			} else{
				logger.error("Unable to find user for USERS.ID: " + userId);
				throw new UsersDaoException("User not found for ID: " + userId);
			}
		}
		return (tbForm);
	}

	protected TaxBenefitForm getEmployeeInfo(int userId, TaxBenefitForm tbForm) throws ProfileInfoDaoException, LevelsDaoException, DivisonDaoException, UsersDaoException {
		// Get general details EMPID, FIRST NAME, LAST NAME, DEPARTMENT, LEVEL
		UsersDao userProvider = UsersDaoFactory.create();
		Users requester = userProvider.findByPrimaryKey(userId);
		if (requester != null){
			String fName, mName, lName;
			String empName = null;
			int empId = requester.getEmpId();
			int levelId = requester.getLevelId();
			String dept, div, desig, levelName;
			dept = div = desig = levelName = null;
			ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
			LevelsDao levelProvider = LevelsDaoFactory.create();
			DivisonDao divisionProvider = DivisonDaoFactory.create();
			// Get empName
			ProfileInfo userProfile = profileInfoProvider.findWhereUserIdEquals(userId);
			fName = userProfile != null ? userProfile.getFirstName() : "???";
			mName = userProfile != null ? userProfile.getMiddleName() : "???";
			lName = userProfile != null ? userProfile.getLastName() : "???";
			empName = fName;
			empName += mName != null && mName.length() > 0 ? " " + mName : "";
			empName += lName != null && lName.length() > 0 ? " " + lName : "";
			// Get level, div, dept, desig
			Levels userLevel = levelProvider.findByPrimaryKey(levelId);
			levelName = userLevel != null ? userLevel.getLabel() : "???";
			desig = userLevel != null ? userLevel.getDesignation() : "???";
			int divId = userLevel != null ? userLevel.getDivisionId() : 0;
			Divison userDivision = divisionProvider.findByPrimaryKey(divId);
			div = userDivision != null ? userDivision.getName() : "???";
			int deptId = userDivision != null ? userDivision.getParentId() : 0;
			if (deptId == 0){
				dept = div;
			} else{
				Divison userDept = divisionProvider.findByPrimaryKey(deptId);
				dept = userDept != null ? userDept.getName() : "???";
			}
			// Set values to return	
			tbForm.setUserId(userId);
			tbForm.setEmpId(empId);
			tbForm.setEmpName(empName);
			tbForm.setDepartment(dept);
			tbForm.setDivision(div);
			tbForm.setLevelName(levelName);
			tbForm.setDesignation(desig);
		} else{
			throw new UsersDaoException("User not found for USERS.ID: " + userId);
		}
		return (tbForm);
	}

	protected List<TaxBenefitDeclaration> getSortedTbdByBenefitName(TaxBenefitDeclaration[] argTbDec) {
		List<TaxBenefitDeclaration> tbDeclaration = new ArrayList<TaxBenefitDeclaration>();
		// Copy from array to list
		for (TaxBenefitDeclaration element : argTbDec)
			tbDeclaration.add(element);
		// Sort by benefit name
		Collections.sort(tbDeclaration, new Comparator<TaxBenefitDeclaration>() {

			@Override
			public int compare(TaxBenefitDeclaration o1, TaxBenefitDeclaration o2) {
				return o1.getBenefitName().compareTo(o2.getBenefitName());
			}
		});
		return (tbDeclaration);
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

	protected List<Date> getTaxBenefitUpdatedOn(int userId, String financialYear, Date currentUpdateOn) {
		List<Date> updatedOn = new ArrayList<Date>();
		List<Object> result = JDBCUtiility.getInstance().getSingleColumn("SELECT DISTINCT(UPDATED_ON) FROM TBD_HISTORY WHERE UPDATED_ON<? AND USERID=? AND FINANCIAL_YEAR=? ORDER BY UPDATED_ON DESC", new Object[] { currentUpdateOn, userId, financialYear });
		for (Object element : result){
			if (element instanceof Date){
				updatedOn.add((Date) element);
			}
		}
		return (updatedOn);
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		String uType = form.getuType();
		TaxBenefitForm taxBenefitForm = (TaxBenefitForm) form;
		UpdateTypes paramUType = UpdateTypes.getValue(uType);
		Login login = Login.getLogin(request);
		String financialYear = FBPModel.getFinancialYear();
		switch (paramUType) {
			case UPDATE:{
				int userId = login != null ? login.getUserId() : 0;
				String errorKey = "errors.tbd.update.database";
				Connection conn = null;
				try{
					// Begin transaction
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					result = updateTaxBenefitDeclaration(userId, financialYear, taxBenefitForm, errorKey, conn);
					conn.commit();
					if (result.getActionMessages().size() > 0){
						this.rollback(conn, result, null);
					} else{
						conn.commit();
					}
				} catch (SQLException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits history.", ex);
					this.rollback(conn, result, null);
				} finally{
					// Close connection
					if (conn != null) ResourceManager.close(conn);
				}
			}
				break;
			case ACCEPTED:{
				String paramComment = taxBenefitForm.getMainComment();
				int paramTaxBenefitReqId = taxBenefitForm.getTaxBenefitRequestId();
				int approverId = login != null ? login.getUserId() : 0;
				try{
					String errorKey = "errors.tbd.update.database";
					if (paramTaxBenefitReqId <= 0){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
					} else{
						TaxBenefitReqDao taxBenefitReqProvider = TaxBenefitReqDaoFactory.create();
						TaxBenefitReq taxBenefitReq = taxBenefitReqProvider.findByPrimaryKey(paramTaxBenefitReqId);
						int requesterId = taxBenefitReq != null ? taxBenefitReq.getRequesterId() : 0;
						result = updateTaxBenefitRequestStatus(approverId, paramTaxBenefitReqId, paramComment, financialYear, Status.ACCEPTED, errorKey);
						if (result.getActionMessages().size() <= 0){
							// Recompute tds
							new TDSUtility().reCalculate(requesterId);
						} else{
							logger.error("Some error messages exists while updating status of tax benefit declaration request for TAX_BENEFIT_REQ.ID: " + paramTaxBenefitReqId);
						}
					}
				} catch (TaxBenefitReqDaoException ex){
					logger.error("Unable to find tax benefit request entry for ID: " + paramTaxBenefitReqId, ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
				}
			}
				break;
			case REJECTED:{
				String paramComment = taxBenefitForm.getMainComment();
				int paramTaxBenefitReqId = taxBenefitForm.getTaxBenefitRequestId();
				int approverId = login != null ? login.getUserId() : 0;
				String errorKey = "errors.tbd.update.database";
				if (paramTaxBenefitReqId <= 0){
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
				} else{
					result = updateTaxBenefitRequestStatus(approverId, paramTaxBenefitReqId, paramComment, financialYear, Status.REJECTED, errorKey);
				}
			}
				break;
			case CANCEL:{
				String paramComment = taxBenefitForm.getMainComment();
				int paramTaxBenefitReqId = taxBenefitForm.getTaxBenefitRequestId();
				int requesterId = login != null ? login.getUserId() : 0;
				String errorKey = "errors.tbd.update.database";
				if (paramTaxBenefitReqId <= 0){
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
				} else{
					result = updateTaxBenefitRequestStatus(requesterId, paramTaxBenefitReqId, paramComment, financialYear, Status.CANCELLED, errorKey);
				}
			}
				break;
			case UPDATEBYHANDLER:{
				int approverId = login != null ? login.getUserId() : 0;
				int paramUserId = taxBenefitForm.getUserId();
				String paramFinancialYear = taxBenefitForm.getFinancialYear();
				String paramComment = taxBenefitForm.getMainComment();
				String errorKey = "errors.tbd.update.database";
				Connection conn = null;
				try{
					// Begin transaction
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					result = updateTaxBenefitAccept(paramUserId, paramFinancialYear, taxBenefitForm, errorKey, conn);
					if (result.getActionMessages().size() > 0){
						this.rollback(conn, result, null);
					} else{
						result = updateTaxBenefitDeclaration(paramUserId, paramFinancialYear, taxBenefitForm, errorKey, conn);
						if (result.getActionMessages().size() > 0){
							this.rollback(conn, result, null);
						} else{
							InboxDao inboxWriter = InboxDaoFactory.create(conn);
							ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
							ProfileInfo profile = profileInfoProvider.findWhereUserIdEquals(approverId);
							String approver = profile != null ? profile.getFirstName() : "???";
							profile = profileInfoProvider.findWhereUserIdEquals(paramUserId);
							String empFname = profile.getFirstName();
							String empOfficialEmailId = profile.getOfficalEmailId();
							String subject = TAX_BENEFIT_SUBJECT_PREFIX + " updated by " + approver;
							List<TaxBenefitDeclaration> benefitDec = getUserTaxBenefitsDeclaration(paramUserId, paramFinancialYear);
							String taxBenefitDeclarationHtml = getTaxBenefitDeclarationHtml(benefitDec);
							PortalMail taxDecUpdateByHandler = getTaxBenefitUpdateByHandlerEmail(approver, empFname, paramFinancialYear, paramComment, subject, taxBenefitDeclarationHtml);
							String messageBody = taxDecUpdateByHandler.getMailBody();
							Inbox inbox = getTaxBenefitInbox(paramUserId, 0, subject, "Update", messageBody);
							inbox.setId(0);
							inbox.setReceiverId(paramUserId);
							inbox.setAssignedTo(0);
							// Sending notification to requester about updated
							// values
							inboxWriter.insert(inbox);
							ProcessChain processChain = getTaxBenefitProcessChain(approverId);
							ProcessEvaluator pEval = new ProcessEvaluator();
							String processChainNotifier, processChainHandler;
							processChainNotifier = processChain != null ? processChain.getNotification() : "";
							processChainHandler = processChain != null ? processChain.getHandler() : "";
							Integer[] notifiers = pEval.notifiers(processChainNotifier, paramUserId);
							Integer[] handlers = pEval.handlers(processChainHandler, paramUserId);
							List<String> decTo, decCc;
							decTo = new ArrayList<String>();
							decCc = new ArrayList<String>();
							// Sending notification to handlers and notifiers
							for (Integer handlerId : handlers){
								if (handlerId == approverId) continue;
								inbox.setId(0);
								inbox.setReceiverId(handlerId);
								inbox.setAssignedTo(0);
								inboxWriter.insert(inbox);
								ProfileInfo notifierProfile = profileInfoProvider.findWhereUserIdEquals(handlerId);
								String officialEmail = notifierProfile != null ? notifierProfile.getOfficalEmailId() : null;
								if (officialEmail != null) decTo.add(officialEmail);
							}
							for (Integer notifierId : notifiers){
								if (notifierId == approverId) continue;
								inbox.setId(0);
								inbox.setReceiverId(notifierId);
								inbox.setAssignedTo(0);
								inboxWriter.insert(inbox);
								ProfileInfo notifierProfile = profileInfoProvider.findWhereUserIdEquals(notifierId);
								String officialEmail = notifierProfile != null ? notifierProfile.getOfficalEmailId() : null;
								if (officialEmail != null) decCc.add(officialEmail);
							}
							String[] recipientTo = new String[decTo.size()];
							recipientTo = decTo.toArray(recipientTo);
							String[] recipientCc = new String[decCc.size()];
							recipientCc = decCc.toArray(recipientCc);
							taxDecUpdateByHandler.setAllReceipientMailId(recipientTo);
							taxDecUpdateByHandler.setFromMailId(NO_REPLY_EMAIL_ID);
							taxDecUpdateByHandler.setMailSubject(subject);
							taxDecUpdateByHandler.setAllReceipientcCMailId(recipientCc);
							conn.commit();
							try{
								MailGenerator emailProvider = new MailGenerator();
								// Send email to handlers and notifiers
								emailProvider.invoker(taxDecUpdateByHandler);
								taxDecUpdateByHandler.setAllReceipientMailId(null);
								taxDecUpdateByHandler.setAllReceipientcCMailId(null);
								taxDecUpdateByHandler.setRecipientMailId(empOfficialEmailId);
								// Send email to requester
								emailProvider.invoker(taxDecUpdateByHandler);
							} catch (Exception ex){
								logger.error("Unable to send email notification. Problem in mail generator", ex);
							}
						}
					}
				} catch (SQLException ex){
					logger.error("Unable to save tax benefit declaration. Problem database operation.", ex);
					this.rollback(conn, result, errorKey);
				} catch (ProfileInfoDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving profile information.", ex);
					this.rollback(conn, result, errorKey);
				} catch (TaxBenefitDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefit information.", ex);
					this.rollback(conn, result, errorKey);
				} catch (TaxBenefitDeclarationDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefit declaration.", ex);
					this.rollback(conn, result, errorKey);
				} catch (FileNotFoundException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving email template.", ex);
					this.rollback(conn, result, errorKey);
				} catch (InboxDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem creating inbox entries.", ex);
					this.rollback(conn, result, errorKey);
				} catch (ModulesDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits module.", ex);
					this.rollback(conn, result, errorKey);
				} catch (ProcessChainDaoException ex){
					logger.error("Unable to save tax benefit declaration. Problem retriving tax benefits process chain.", ex);
					this.rollback(conn, result, errorKey);
				} finally{
					// Close connection
					if (conn != null) ResourceManager.close(conn);
				}
			}
				break;
			default:{
				logger.info("TaxBenefitDeclarationModel defualt case blank implementation.");
			}
				break;
		}
		request.setAttribute("actionForm", taxBenefitForm);
		return result;
	}

	protected ActionResult updateTaxBenefitAccept(int userId, String financialYear, TaxBenefitForm taxBenefitForm, String errorKey, Connection conn) {
		ActionResult result = new ActionResult();
		// String errorKey="errors.tbd.update.database";
		final String WHERE_TAXDEC = "FINANCIAL_YEAR=? AND USERID=? AND BENEFIT_ID=?";
		String paramTaxBenefit = taxBenefitForm.getTaxBenefit();
		if (paramTaxBenefit.length() <= 0){
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
		} else{
			try{
				TaxBenefitAcceptDao taxBenefitAcceptReader = TaxBenefitAcceptDaoFactory.create();
				TaxBenefitAcceptDao taxBenefitAcceptWriter = TaxBenefitAcceptDaoFactory.create(conn);
				List<TaxBenefitDeclaration> benefitDec = parseTaxBenefit(paramTaxBenefit, userId);
				List<TaxBenefitAccept> benefitAccept = new ArrayList<TaxBenefitAccept>();
				for (TaxBenefitDeclaration element : benefitDec){
					benefitAccept.add(element.getTaxBenefitAccept());
				}
				for (TaxBenefitAccept dec : benefitAccept){
					String previousAmt = "0.0";
					String updatedAmt = "0.0";
					// Retrieve record for updating if no record exists
					// insert a new record
					TaxBenefitAccept[] existingDec = taxBenefitAcceptReader.findByDynamicWhere(WHERE_TAXDEC, new Object[] { financialYear, userId, dec.getBenefitId() });
					// Update if exists else insert record
					if (existingDec.length > 0){
						TaxBenefitAccept existDec = existingDec[0];
						String newComments = dec.getComments();
						String prevComments = existDec.getComments();
						previousAmt = existDec.getAmount();
						updatedAmt = dec.getAmount();
						boolean isCommentDiff = prevComments == null && newComments == null;
						isCommentDiff = !isCommentDiff ? (prevComments != null && newComments != null && !newComments.equalsIgnoreCase(prevComments)) : isCommentDiff;
						if (!previousAmt.equalsIgnoreCase(updatedAmt) || isCommentDiff){
							existDec.setAmount(dec.getAmount());
							existDec.setComments(newComments);
							taxBenefitAcceptWriter.update(new TaxBenefitAcceptPk(existDec.getId()), existDec);
						} else{
							logger.debug("Skipping updatation as posted values are already updated in database.");
						}
					} else{
						updatedAmt = dec.getAmount();
						// insert record
						taxBenefitAcceptWriter.insert(dec);
					}
				}
				taxBenefitForm.setTaxBenefitDeclaration(benefitDec);
			} catch (TaxBenefitAcceptDaoException ex){
				logger.error("Unable to save tax benefit acceptance. Problem in information to be saved.", ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.update.invalid"));
			} catch (Exception ex){
				logger.error("Unable to save tax benefit declaration.", ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.update.invalid"));
			}
		}
		return (result);
	}

	protected ActionResult updateTaxBenefitDeclaration(int userId, String financialYear, TaxBenefitForm taxBenefitForm, String errorKey, Connection conn) {
		ActionResult result = new ActionResult();
		// String errorKey="errors.tbd.update.database";
		final String WHERE_TAXDEC = "FINANCIAL_YEAR=? AND USERID=? AND BENEFIT_ID=?";
		String paramTaxBenefit = taxBenefitForm.getTaxBenefit();
		int paramTaxBenefitReqId = taxBenefitForm.getTaxBenefitRequestId();
		if (paramTaxBenefit.length() <= 0){
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.invalidInfo"));
		} else{
			try{
				TaxBenefitDeclarationDao taxBenefitDecReader = TaxBenefitDeclarationDaoFactory.create();
				TaxBenefitDeclarationDao taxBenefitDecWriter = TaxBenefitDeclarationDaoFactory.create(conn);
				TaxBenefitReqDao taxBenefitReqReader = TaxBenefitReqDaoFactory.create();
				TaxBenefitReqDao taxBenefitReqWriter = TaxBenefitReqDaoFactory.create(conn);
				List<TaxBenefitDeclaration> benefitDec = parseTaxBenefit(paramTaxBenefit, userId);
				// Update declaration
				String newEntries = "";
				for (TaxBenefitDeclaration dec : benefitDec){
					String previousAmt = "0.0";
					String updatedAmt = "0.0";
					// Retrieve record for updating if no record exists insert a new record
					TaxBenefitDeclaration[] existingDec = taxBenefitDecReader.findByDynamicWhere(WHERE_TAXDEC, new Object[] { financialYear, userId, dec.getBenefitId() });
					// Update if exists else insert record
					if (existingDec.length > 0){
						TaxBenefitDeclaration existDec = existingDec[0];
						String newComments = dec.getComments();
						String prevComments = existDec.getComments();
						previousAmt = existDec.getAmount();
						updatedAmt = dec.getAmount();
						boolean isCommentDiff = prevComments == null && newComments == null;
						isCommentDiff = !isCommentDiff ? (prevComments != null && newComments != null && !newComments.equalsIgnoreCase(prevComments)) : isCommentDiff;
						if (!previousAmt.equalsIgnoreCase(updatedAmt) || isCommentDiff){
							existDec.setAmount(dec.getAmount());
							existDec.setComments(newComments);
							newEntries += "," + existDec.getId();
							taxBenefitDecWriter.update(new TaxBenefitDeclarationPk(existDec.getId()), existDec);
						} else{
							logger.debug("Skipping updatation as posted values are already updated in database.");
							newEntries += "," + existDec.getId();
						}
					} else{
						updatedAmt = dec.getAmount();
						// insert record
						newEntries += "," + taxBenefitDecWriter.insert(dec).getId();
					}
				}
				String deleteWhere = "FINANCIAL_YEAR = ? AND USERID = ? AND ID NOT IN ( ";
				deleteWhere += newEntries != null ? (!newEntries.equalsIgnoreCase("") ? newEntries.substring(1, newEntries.length()) : "") : "";
				deleteWhere += " ) ";
				TaxBenefitDeclaration[] deletes = taxBenefitDecReader.findByDynamicWhere(deleteWhere, new Object[] { financialYear, userId });
				for (TaxBenefitDeclaration eachDelete : deletes){
					taxBenefitDecWriter.delete(eachDelete.createPk());
				}
				// Update request status
				TaxBenefitReq taxBenefitReq = taxBenefitReqReader.findByPrimaryKey(paramTaxBenefitReqId);
				if (taxBenefitReq != null && !taxBenefitReq.getStatus().equalsIgnoreCase(Status.NEW)){
					String status = Status.UPDATED;
					taxBenefitReq.setStatus(status);
					taxBenefitReq.setCreatedOn(new Date());
					taxBenefitReqWriter.update(new TaxBenefitReqPk(taxBenefitReq.getId()), taxBenefitReq);
				}
				taxBenefitForm.setTaxBenefitDeclaration(benefitDec);
			} catch (TaxBenefitDeclarationDaoException ex){
				logger.error("Unable to save tax benefit declaration. Problem in information to be saved.", ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.update.invalid"));
			} catch (Exception ex){
				logger.error("Unable to save tax benefit declaration.", ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.update.invalid"));
			}
		}
		return (result);
	}

	protected ActionResult updateTaxBenefitRequestStatus(int approverId, int paramTaxBenefitReqId, String paramComment, String financialYear, String status, String errorKey) {
		ActionResult result = new ActionResult();
		Connection conn = null;
		Users user1=null;
		UsersDao usersDao = UsersDaoFactory.create();
		try{
			
			TaxBenefitReqDao taxBenefitReqReader = TaxBenefitReqDaoFactory.create();
			TaxBenefitReq taxBenefitReq = taxBenefitReqReader.findByPrimaryKey(paramTaxBenefitReqId);
			if (taxBenefitReq == null /*&& taxBenefitReq.getStatus().equalsIgnoreCase(Status.SUBMITTED) || taxBenefitReq.getStatus().equalsIgnoreCase(Status.RESUBMITTED)*/){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.notfound", new Integer(paramTaxBenefitReqId)));
			} else if (taxBenefitReq != null && taxBenefitReq.getStatus().equalsIgnoreCase(status)){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.action.taken", status));
			} else if (taxBenefitReq != null && taxBenefitReq.getStatus().equalsIgnoreCase(Status.CANCELLED)){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.cancelled", status));
			} else{
				conn = ResourceManager.getConnection();
				conn.setAutoCommit(false);
				ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
				EmpSerReqMapDao esrMapProvider = EmpSerReqMapDaoFactory.create();
				TaxBenefitReqDao taxBenefitReqWriter = TaxBenefitReqDaoFactory.create(conn);
				TbdHistoryDao tbdHistoryWriter = TbdHistoryDaoFactory.create(conn);
				// TaxBenefitDeclarationDao
				// taxBenefitDeclarationReader=TaxBenefitDeclarationDaoFactory.create();
				TaxBenefitAcceptDao taxBenefitAcceptReader = TaxBenefitAcceptDaoFactory.create();
				TaxBenefitAcceptDao taxBenefitAcceptWriter = TaxBenefitAcceptDaoFactory.create(conn);
				InboxDao inboxReader = InboxDaoFactory.create();
				InboxDao inboxWriter = InboxDaoFactory.create(conn);
				// Update status of request
				Date actionOn = new Date();
				int requesterId = taxBenefitReq != null ? taxBenefitReq.getRequesterId() : 0;
				int esrMapId = taxBenefitReq != null ? taxBenefitReq.getEsrMapId() : 0;;
				taxBenefitReq.setStatus(status);
				taxBenefitReq.setActionOn(actionOn);
				taxBenefitReq.setActionBy(approverId);
				taxBenefitReq.setComments(paramComment);
				// Update request status
				taxBenefitReqWriter.update(new TaxBenefitReqPk(taxBenefitReq.getId()), taxBenefitReq);
				String approverName, empFname, empOfficialEmailId;
				String subject, taxBenefitDeclarationHtml;
				ProfileInfo tempProfile = profileInfoProvider.findWhereUserIdEquals(approverId);
				approverName = tempProfile != null ? tempProfile.getFirstName() : "???";
				tempProfile = profileInfoProvider.findWhereUserIdEquals(requesterId);
				empFname = tempProfile != null ? tempProfile.getFirstName() : "???";
				empOfficialEmailId = tempProfile != null ? tempProfile.getOfficalEmailId() : null;
				EmpSerReqMap esrMap = esrMapProvider.findByPrimaryKey(esrMapId);
				String reqId = esrMap.getReqId();
				subject = TAX_BENEFIT_SUBJECT_PREFIX + reqId + " " + status;
				List<TaxBenefitDeclaration> userTaxBenefitDec = getUserTaxBenefitsDeclaration(requesterId, financialYear);
				taxBenefitDeclarationHtml = getTaxBenefitDeclarationHtml(userTaxBenefitDec);
				 user1 = usersDao.findByPrimaryKey(requesterId);
				PortalMail taxBenefitStatusEmail = null;
				if (status.equalsIgnoreCase(Status.ACCEPTED)){
					// Delete previous TAX_BENEFIT_ACCEPT records for requester
					// and insert new records
					// Delete existing TAX_BENEFIT_ACCEPT
					TaxBenefitAccept[] existingTbAccept = taxBenefitAcceptReader.findByDynamicWhere("FINANCIAL_YEAR=? AND USERID=?", new Object[] { financialYear, requesterId });
					Date updatedOn = new Date();
					for (TaxBenefitAccept element : existingTbAccept){
						TbdHistory tbdHistory = element.getTbdHistory(updatedOn);
						tbdHistoryWriter.insert(tbdHistory);
						taxBenefitAcceptWriter.delete(new TaxBenefitAcceptPk(element.getId()));
					}
					// Insert new TAX_BENEFIT_DECLARATION into
					// TAX_BENEFIT_ACCEPT
					for (TaxBenefitDeclaration element : userTaxBenefitDec){
						TaxBenefitAccept tbAccept = element.getTaxBenefitAccept();
						taxBenefitAcceptWriter.insert(tbAccept);
					}
					taxBenefitStatusEmail = getTaxBenefitAcceptEmail(approverName, empFname,user1.getEmpId(), financialYear, paramComment, subject, taxBenefitDeclarationHtml);
				} else if (status.equalsIgnoreCase(Status.REJECTED)){
					Date dateOfAction = new Date();
					taxBenefitStatusEmail = getTaxBenefitRejectEmail(approverName, empFname, financialYear, PortalUtility.getdd_MM_yyyy(dateOfAction), paramComment, subject, taxBenefitDeclarationHtml);
				} else if (status.equalsIgnoreCase(Status.CANCELLED)){
					Date dateOfAction = new Date();
					taxBenefitStatusEmail = getTaxBenefitCancelEmail("All", empFname, financialYear, PortalUtility.getdd_MM_yyyy(dateOfAction), paramComment, subject, taxBenefitDeclarationHtml);
				}
				// Delete in-box/docking station/my-task entries
				Inbox[] prevInbox = inboxReader.findByDynamicWhere("RECEIVER_ID=ASSIGNED_TO AND ESR_MAP_ID=?", new Object[] { esrMapId });
				for (Inbox element : prevInbox){
					inboxWriter.delete(new InboxPk(element.getId()));
				}
				// Send notification
				RequestType taxBenefitReqType = getTaxBenefitRequestType();
				String category = taxBenefitReqType != null ? taxBenefitReqType.getName() : "Tax Benefit";
				String messageBody = taxBenefitStatusEmail.getMailBody();
				Inbox inbox = getTaxBenefitInbox(requesterId, esrMapId, subject, status, messageBody);
				ProcessChain taxBenefitPc = getTaxBenefitProcessChain(approverId);
				ProcessEvaluator pEval = new ProcessEvaluator();
				Integer[] notifiers, handlers;
				if (taxBenefitPc != null){
					notifiers = pEval.notifiers(taxBenefitPc.getNotification(), requesterId);
					handlers = pEval.handlers(taxBenefitPc.getHandler(), requesterId);
					inbox.setSubject("STATUS UPDATED: " + inbox.getSubject());
					inbox.setCategory(category);
					List<String> decTo, decCc;
					decTo = new ArrayList<String>();
					decCc = new ArrayList<String>();
					for (int handlerId : handlers){
						inbox.setId(0);
						inbox.setReceiverId(handlerId);
						inbox.setAssignedTo(0);
						inboxWriter.insert(inbox);
						ProfileInfo notifierProfile = profileInfoProvider.findWhereUserIdEquals(handlerId);
						String officialEmail = notifierProfile != null ? notifierProfile.getOfficalEmailId() : null;
						if (officialEmail != null) decTo.add(officialEmail);
					}
					for (int notifierId : notifiers){
						inbox.setId(0);
						inbox.setReceiverId(notifierId);
						inbox.setAssignedTo(0);
						inboxWriter.insert(inbox);
						ProfileInfo notifierProfile = profileInfoProvider.findWhereUserIdEquals(notifierId);
						String officialEmail = notifierProfile != null ? notifierProfile.getOfficalEmailId() : null;
						if (officialEmail != null) decCc.add(officialEmail);
					}
					String[] recipientTo = new String[decTo.size()];
					recipientTo = decTo.toArray(recipientTo);
					String[] recipientCc = new String[decCc.size()];
					recipientCc = decCc.toArray(recipientCc);
					taxBenefitStatusEmail.setAllReceipientMailId(recipientTo);
					taxBenefitStatusEmail.setFromMailId(NO_REPLY_EMAIL_ID);
					taxBenefitStatusEmail.setAllReceipientcCMailId(recipientCc);
				}
				// Notification to requester
				inbox.setId(0);
				inbox.setReceiverId(requesterId);
				inbox.setSubject(subject);
				inbox.setAssignedTo(0);
				inboxWriter.insert(inbox);
				conn.commit();
				try{
					MailGenerator emailProvider = new MailGenerator();
					// Send email to handlers and notifiers
					emailProvider.invoker(taxBenefitStatusEmail);
					taxBenefitStatusEmail.setAllReceipientMailId(null);
					taxBenefitStatusEmail.setAllReceipientcCMailId(null);
					taxBenefitStatusEmail.setRecipientMailId(empOfficialEmailId);
					// Send email to requester
					emailProvider.invoker(taxBenefitStatusEmail);
				} catch (Exception ex){
					logger.error("Unable to send email notification. Problem in mail generator", ex);
				}
			}
		} catch (InboxDaoException ex){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in creating inbox entries.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (ModulesDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding tax benefit declaration module.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (ProcessChainDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding process chain for tax benefit declaration.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (ProfileInfoDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding profile information.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (EmpSerReqMapDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding EMP_SER_REQ_MAP information.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (TaxBenefitDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding tax benefit information.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (TaxBenefitDeclarationDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding tax benefit declaration information.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (FileNotFoundException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding email templates.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (RequestTypeDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in finding tax benefit reqeust type.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (TaxBenefitReqDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in updating tax benefit declaration request.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (SQLException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem in database operation.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (TaxBenefitAcceptDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem inserting/deleting records in TAX_BENEFIT_ACCEPT.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (TbdHistoryDaoException e){
			logger.error("Unable to accept/reject tax benefit declaration request. Problem inserting/deleting records in TBD_HISTORY.");
			this.rollback(conn, result, errorKey);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		} catch (UsersDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (conn != null) ResourceManager.close(conn);
		}
		return (result);
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		TaxBenefitDeclaration taxBenefitForm = (TaxBenefitDeclaration) form;
		int taxBenefitDecId = taxBenefitForm.getId();
		TaxBenefitDeclarationDao taxBenefitDecProvider = TaxBenefitDeclarationDaoFactory.create();
		if (taxBenefitDecId > 0){
			try{
				TaxBenefitDeclaration existingDec = taxBenefitDecProvider.findByPrimaryKey(taxBenefitDecId);
				if (existingDec != null){
					taxBenefitDecProvider.delete(new TaxBenefitDeclarationPk(taxBenefitDecId));
				} else{
					logger.debug("Unable to find tax benefit declaration for deletion TAX_BENEFIT_DECLARATION.ID: " + taxBenefitDecId);
				}
			} catch (TaxBenefitDeclarationDaoException e){
				logger.error("Unable to delete tax benefit declaration for ID: " + taxBenefitDecId, e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.delete"));
			} finally{}
		} else{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tbd.delete"));
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// Auto-generated method stubpackage com.dikshatech.portal.models;
		// blank implementation
		return (null);
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// Auto-generated method stub
		// blank implementation
		return null;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// Auto-generated method stub
		// blank implementation
		return null;
	}

	/**
	 * Create tax benefit declaration request and send notification to handlers
	 * and notifiers of declaration updates and Tax deduction at source
	 * auto-recalculation for the same. Assumes tax benefit declaration has no
	 * approvers and only handlers and or notifiers
	 * 
	 * @param userId
	 * @param taxBenefitDeclarationHtml
	 * @throws SQLException
	 * @throws InboxDaoException
	 * @throws ProcessChainDaoException
	 * @throws ModulesDaoException
	 * @throws ProfileInfoDaoException
	 * @throws RegionsDaoException
	 * @throws TaxBenefitReqDaoException
	 * @throws RequestTypeDaoException
	 * @throws EmpSerReqMapDaoException
	 * @throws FileNotFoundException
	 * @throws UsersDaoException 
	 */
	protected void doProcessChain(Connection conn, int taxBenefitReqId, int userId, String financialYear, String taxBenefitDeclarationHtml, String paramMainComment) throws SQLException, InboxDaoException, ModulesDaoException, ProcessChainDaoException, ProfileInfoDaoException, RequestTypeDaoException, TaxBenefitReqDaoException, RegionsDaoException, EmpSerReqMapDaoException, FileNotFoundException, UsersDaoException {
		ProcessChain taxBenefitPC = getTaxBenefitProcessChain(userId);
		UsersDao usersDao = UsersDaoFactory.create();
		if (taxBenefitPC != null){
			ProfileInfoDao profileInfoProvider = ProfileInfoDaoFactory.create();
			ProfileInfo profile = profileInfoProvider.findWhereUserIdEquals(userId);
			Users user1 = usersDao.findByPrimaryKey(userId);
			String empFname = profile != null ? profile.getFirstName() : "???";
			String empOfficialEmailId = profile != null ? profile.getOfficalEmailId() : null;
			InboxDao inboxProvider = InboxDaoFactory.create(conn);
			EmpSerReqMapDao esrMapProvider = EmpSerReqMapDaoFactory.create();
			TaxBenefitReqDao taxBenefitReqReader = TaxBenefitReqDaoFactory.create();
			TaxBenefitReqDao taxBenefitReqWriter = TaxBenefitReqDaoFactory.create(conn);
			TaxBenefitReq newTaxBenefitReq = taxBenefitReqReader.findByPrimaryKey(taxBenefitReqId);
			int esrMapId = newTaxBenefitReq.getEsrMapId();
			EmpSerReqMap esrMap = esrMapProvider.findByPrimaryKey(esrMapId);
			String reqId = esrMap.getReqId();
			String existingStatus = newTaxBenefitReq.getStatus();
			String status = null;
			if (existingStatus.equalsIgnoreCase(Status.NEW)) status = Status.SUBMITTED;
			else if (existingStatus.equalsIgnoreCase(Status.ACCEPTED) || existingStatus.equalsIgnoreCase(Status.REJECTED) || existingStatus.equalsIgnoreCase(Status.CANCELLED) || existingStatus.equalsIgnoreCase(Status.UPDATED)) status = Status.RESUBMITTED;
			Date createdOn = new Date();
			String subject = TAX_BENEFIT_SUBJECT_PREFIX + reqId + " " + status;
			String messageBody = "";
			String processChainNotifier = taxBenefitPC.getNotification();
			String processChainHandler = taxBenefitPC.getHandler();
			ProcessEvaluator pEval = new ProcessEvaluator();
			logger.debug("Executing process chain for tax benefit declaration/update for USERID: " + userId);
			logger.debug("Assuming approval is not required for tax benefit declaration process chain.");
			Integer[] notifiers = pEval.notifiers(processChainNotifier, userId);
			Integer[] handlers = pEval.handlers(processChainHandler, userId);
			String createdOnStr = PortalUtility.getdd_MM_yyyy(createdOn);
			PortalMail taxDecEmail = getTaxBenefitUpdateEmail("[Approver]", empFname,user1.getEmpId(), financialYear, createdOnStr, "-", subject, taxBenefitDeclarationHtml);
			messageBody = taxDecEmail.getMailBody();
			// Create tax benefit declaration request entry
			newTaxBenefitReq.setStatus(status);
			newTaxBenefitReq.setRequesterId(userId);
			newTaxBenefitReq.setCreatedOn(createdOn);
			newTaxBenefitReq.setMessageBody(messageBody);
			newTaxBenefitReq.setComments(paramMainComment);
			taxBenefitReqWriter.update(new TaxBenefitReqPk(taxBenefitReqId), newTaxBenefitReq);
			// Create Inbox entry for handlers
			// String
			// category=taxBenefitReqType!=null?taxBenefitReqType.getName():"???";
			Inbox inbox = new Inbox();
			inbox = getTaxBenefitInbox(userId, esrMapId, subject, status, messageBody);
			// inbox.setEsrMapId(esrMapId);
			// inbox.setSubject(subject);
			// inbox.setRaisedBy(userId);
			// inbox.setCreationDatetime(createdOn);
			// inbox.setStatus(status);
			// inbox.setCategory(category);
			// inbox.setIsRead(0);
			// inbox.setIsDeleted(0);
			// inbox.setMessageBody(messageBody);
			// inbox.setIsEscalated(0);
			// inbox.setRaisedByName(empFname);
			List<String> decTo, decCc;
			decTo = new ArrayList<String>();
			decCc = new ArrayList<String>();
			for (int handlerId : handlers){
				if (handlerId == userId) continue;
				String temp = messageBody;
				ProfileInfo appProfile = profileInfoProvider.findWhereUserIdEquals(handlerId);
				String approver = appProfile != null ? appProfile.getFirstName() : "all";
				String officialEmail = appProfile != null ? appProfile.getOfficalEmailId() : null;
				if (officialEmail != null) decTo.add(officialEmail);
				temp = temp.replaceAll("\\[Approver\\]", approver);
				inbox.setId(0);
				inbox.setReceiverId(handlerId);
				inbox.setAssignedTo(handlerId);
				inbox.setMessageBody(temp);
				inboxProvider.insert(inbox);
			}
			for (int notifierId : notifiers){
				String temp = messageBody;
				ProfileInfo appProfile = profileInfoProvider.findWhereUserIdEquals(notifierId);
				String approver = appProfile != null ? appProfile.getFirstName() : "all";
				String officialEmail = appProfile != null ? appProfile.getOfficalEmailId() : null;
				if (officialEmail != null) decCc.add(officialEmail);
				temp = temp.replaceAll("\\[Approver\\]", approver);
				inbox.setId(0);
				inbox.setReceiverId(notifierId);
				inbox.setAssignedTo(0);
				inbox.setMessageBody(temp);
				inboxProvider.insert(inbox);
			}
			String[] recipientTo = new String[decTo.size()];
			recipientTo = decTo.toArray(recipientTo);
			String[] recipientCc = new String[decCc.size()];
			recipientCc = decCc.toArray(recipientCc);
			taxDecEmail.setApproverName("All");
			taxDecEmail.setAllReceipientMailId(recipientTo);
			taxDecEmail.setFromMailId(NO_REPLY_EMAIL_ID);
			taxDecEmail.setMailSubject(subject);
			taxDecEmail.setAllReceipientcCMailId(recipientCc);
			// Create acknowledgement entry for requester
			PortalMail taxDecAckEmail = getTaxBenefitUpdateAckEmail(empFname,user1.getEmpId(), financialYear, createdOnStr, "-", subject, taxBenefitDeclarationHtml);
			messageBody = taxDecAckEmail.getMailBody();
			inbox = new Inbox();
			inbox = getTaxBenefitInbox(userId, esrMapId, subject, status, messageBody);
			inbox.setReceiverId(userId);
			// inbox.setEsrMapId(esrMapId);
			// inbox.setSubject(subject);
			// inbox.setRaisedBy(userId);
			// inbox.setCreationDatetime(createdOn);
			// inbox.setStatus(status);
			// inbox.setCategory(category);
			// inbox.setIsRead(0);
			// inbox.setIsDeleted(0);
			// inbox.setMessageBody(messageBody);
			// inbox.setIsEscalated(0);
			// inbox.setRaisedByName(empFname);
			// inbox.setAssignedTo(0);
			inboxProvider.insert(inbox);
			taxDecAckEmail.setRecipientMailId(empOfficialEmailId);
			taxDecAckEmail.setFromMailId(NO_REPLY_EMAIL_ID);
			taxDecAckEmail.setMailSubject(subject);
			try{
				MailGenerator emailProvider = new MailGenerator();
				emailProvider.invoker(taxDecEmail);
				emailProvider.invoker(taxDecAckEmail);
			} catch (Exception ex){
				logger.error("Unable to send email notification. Problem in mail generator", ex);
			}
		} else{
			logger.error("No process chain found for tax benefit module.");
		}
	}

	protected Inbox getTaxBenefitInbox(int userId, int esrMapId, String subject, String status, String messageBody) {
		String category = null;
		try{
			RequestType taxBenefitReqType = getTaxBenefitRequestType();
			category = taxBenefitReqType != null ? taxBenefitReqType.getName() : "???";
		} catch (RequestTypeDaoException ex){
			logger.debug("Unable to find request type for tax benefit declaration.");
			category = "???";
		}
		Date createdOn = new Date();
		Inbox inbox = new Inbox();
		inbox.setId(0);
		inbox.setEsrMapId(esrMapId);
		inbox.setSubject(subject);
		inbox.setRaisedBy(userId);
		inbox.setCreationDatetime(createdOn);
		inbox.setStatus(status);
		inbox.setCategory(category);
		inbox.setIsRead(0);
		inbox.setIsDeleted(0);
		inbox.setMessageBody(messageBody);
		inbox.setIsEscalated(0);
		inbox.setAssignedTo(0);
		return (inbox);
	}

	protected PortalMail getTaxBenefitUpdateEmail(String approverName, String empFname,int empId, String financialYear, String doa, String comments, String subject, String taxBenefitDeclarationHtml) throws FileNotFoundException {
		final String TEMPLATE_NAME = MailSettings.TAX_BENEFIT_DECLARATION;
		String emailBody = null;
		PortalMail eMailDesigner = new PortalMail();
		MailGenerator eMailGenerator = new MailGenerator();
		eMailDesigner.setTemplateName(TEMPLATE_NAME);
		eMailDesigner.setApproverName(approverName);
		eMailDesigner.setEmpFname(empFname);
		eMailDesigner.setEmployeeId(empId);
		eMailDesigner.setTimePeriod(financialYear);
		eMailDesigner.setDateOfAction(doa);
		eMailDesigner.setComments(comments);
		eMailDesigner.setMessageBody(taxBenefitDeclarationHtml);
		// Creating string email message by merging template with field values
		emailBody = eMailGenerator.replaceFields(eMailGenerator.getMailTemplate(TEMPLATE_NAME), eMailDesigner);
		// Setting PortalMail subject
		eMailDesigner.setMailSubject(subject);
		// Setting PortalMail message body
		eMailDesigner.setMailBody(emailBody);
		return (eMailDesigner);
	}

	protected PortalMail getTaxBenefitUpdateAckEmail(String empFname,int empId, String financialYear, String doa, String comments, String subject, String taxBenefitDeclarationHtml) throws FileNotFoundException {
		final String TEMPLATE_NAME = MailSettings.TAX_BENEFIT_DECLARATION_ACK;
		String emailBody = null;
		PortalMail eMailDesigner = new PortalMail();
		MailGenerator eMailGenerator = new MailGenerator();
		eMailDesigner.setTemplateName(TEMPLATE_NAME);
		eMailDesigner.setEmpFname(empFname);
		eMailDesigner.setEmployeeId(empId);
		eMailDesigner.setTimePeriod(financialYear);
		eMailDesigner.setDateOfAction(doa);
		eMailDesigner.setComments(comments);
		eMailDesigner.setMessageBody(taxBenefitDeclarationHtml);
		// Creating string email message by merging template with field values
		emailBody = eMailGenerator.replaceFields(eMailGenerator.getMailTemplate(TEMPLATE_NAME), eMailDesigner);
		// Setting PortalMail subject
		eMailDesigner.setMailSubject(subject);
		// Setting PortalMail message body
		eMailDesigner.setMailBody(emailBody);
		return (eMailDesigner);
	}

	protected PortalMail getTaxBenefitAcceptEmail(String approverName, String empFname,int empId, String financialYear, String comments, String subject, String taxBenefitDeclarationHtml) throws FileNotFoundException {
		final String TEMPLATE_NAME = MailSettings.TAX_BENEFIT_ACCEPT;
		String emailBody = null;
		PortalMail eMailDesigner = new PortalMail();
		MailGenerator eMailGenerator = new MailGenerator();
		eMailDesigner.setTemplateName(TEMPLATE_NAME);
		eMailDesigner.setApproverName(approverName);
		eMailDesigner.setEmpFname(empFname);
		eMailDesigner.setEmployeeId(empId);
		eMailDesigner.setTimePeriod(financialYear);
		eMailDesigner.setComments(comments);
		eMailDesigner.setMessageBody(taxBenefitDeclarationHtml);
		// Creating string email message by merging template with field values
		emailBody = eMailGenerator.replaceFields(eMailGenerator.getMailTemplate(TEMPLATE_NAME), eMailDesigner);
		// Setting PortalMail subject
		eMailDesigner.setMailSubject(subject);
		// Setting PortalMail message body
		eMailDesigner.setMailBody(emailBody);
		return (eMailDesigner);
	}

	protected PortalMail getTaxBenefitRejectEmail(String approverName, String empFname, String financialYear, String doa, String comments, String subject, String taxBenefitDeclarationHtml) throws FileNotFoundException {
		final String TEMPLATE_NAME = MailSettings.TAX_BENEFIT_REJECT;
		String emailBody = null;
		PortalMail eMailDesigner = new PortalMail();
		MailGenerator eMailGenerator = new MailGenerator();
		eMailDesigner.setTemplateName(TEMPLATE_NAME);
		eMailDesigner.setApproverName(approverName);
		eMailDesigner.setEmpFname(empFname);
		eMailDesigner.setTimePeriod(financialYear);
		eMailDesigner.setComments(comments);
		eMailDesigner.setMessageBody(taxBenefitDeclarationHtml);
		// Creating string email message by merging template with field values
		emailBody = eMailGenerator.replaceFields(eMailGenerator.getMailTemplate(TEMPLATE_NAME), eMailDesigner);
		// Setting PortalMail subject
		eMailDesigner.setMailSubject(subject);
		// Setting PortalMail message body
		eMailDesigner.setMailBody(emailBody);
		return (eMailDesigner);
	}

	protected PortalMail getTaxBenefitCancelEmail(String approverName, String empFname, String financialYear, String doa, String comments, String subject, String taxBenefitDeclarationHtml) throws FileNotFoundException {
		final String TEMPLATE_NAME = MailSettings.TAX_BENEFIT_CANCEL;
		String emailBody = null;
		PortalMail eMailDesigner = new PortalMail();
		MailGenerator eMailGenerator = new MailGenerator();
		eMailDesigner.setTemplateName(TEMPLATE_NAME);
		eMailDesigner.setApproverName(approverName);
		eMailDesigner.setEmpFname(empFname);
		eMailDesigner.setTimePeriod(financialYear);
		eMailDesigner.setComments(comments);
		eMailDesigner.setMessageBody(taxBenefitDeclarationHtml);
		// Creating string email message by merging template with field values
		emailBody = eMailGenerator.replaceFields(eMailGenerator.getMailTemplate(TEMPLATE_NAME), eMailDesigner);
		// Setting PortalMail subject
		eMailDesigner.setMailSubject(subject);
		// Setting PortalMail message body
		eMailDesigner.setMailBody(emailBody);
		return (eMailDesigner);
	}

	protected PortalMail getTaxBenefitUpdateByHandlerEmail(String approverName, String empFname, String financialYear, String comments, String subject, String taxBenefitDeclarationHtml) throws FileNotFoundException {
		final String TEMPLATE_NAME = MailSettings.TAX_BENEFIT_HANDLER_UPDATE;
		String emailBody = null;
		PortalMail eMailDesigner = new PortalMail();
		MailGenerator eMailGenerator = new MailGenerator();
		eMailDesigner.setTemplateName(TEMPLATE_NAME);
		eMailDesigner.setApproverName(approverName);
		eMailDesigner.setEmpFname(empFname);
		eMailDesigner.setTimePeriod(financialYear);
		eMailDesigner.setComments(comments);
		eMailDesigner.setMessageBody(taxBenefitDeclarationHtml);
		// Creating string email message by merging template with field values
		emailBody = eMailGenerator.replaceFields(eMailGenerator.getMailTemplate(TEMPLATE_NAME), eMailDesigner);
		// Setting PortalMail subject
		eMailDesigner.setMailSubject(subject);
		// Setting PortalMail message body
		eMailDesigner.setMailBody(emailBody);
		return (eMailDesigner);
	}

	protected String getTaxBenefitRequestId(int userId) throws RequestTypeDaoException, TaxBenefitReqDaoException, RegionsDaoException {
		String result = null;
		String regionAbbr = null;
		String requestAbbr = null;
		int newTaxBenefitId = 0;
		TaxBenefitReqDao taxBenefitReqProvider = TaxBenefitReqDaoFactory.create();
		RequestType taxBenefitReqType = getTaxBenefitRequestType();
		if (taxBenefitReqType != null){
			requestAbbr = taxBenefitReqType.getAbbrevation();
		} else requestAbbr = "REQ_??";
		TaxBenefitReq[] lastTaxBenefitReq = taxBenefitReqProvider.findByDynamicWhere("ID=(SELECT MAX(ID) FROM TAX_BENEFIT_REQ)", null);
		if (lastTaxBenefitReq.length > 0){
			newTaxBenefitId = lastTaxBenefitReq[0].getId() + 1;
		} else{
			newTaxBenefitId = 1;
		}
		RegionsDao regionProvider = RegionsDaoFactory.create();
		Regions[] regions = regionProvider.findByDynamicWhere("ID = (SELECT D.REGION_ID FROM DIVISON D " + "WHERE D.ID = (SELECT L.DIVISION_ID FROM LEVELS L " + "WHERE L.ID = (SELECT U.LEVEL_ID FROM USERS U WHERE ID = ?)))", new Object[] { userId });
		if (regions.length > 0){
			regionAbbr = regions[0].getRefAbbreviation();
		} else{
			regionAbbr = "??";
		}
		result = regionAbbr + "-" + requestAbbr + "-" + newTaxBenefitId;
		return (result);
	}

	protected RequestType getTaxBenefitRequestType() throws RequestTypeDaoException {
		final String requestName = "TAX BENEFIT";
		RequestType result = null;
		RequestTypeDao requestTypeProvider = RequestTypeDaoFactory.create();
		RequestType[] requestTypes = requestTypeProvider.findByDynamicWhere("NAME=?", new Object[] { requestName });
		if (requestTypes.length == 1){
			result = requestTypes[0];
		} else{
			throw new RequestTypeDaoException("Unable to find request tyep for NAME: " + requestName);
		}
		return (result);
	}

	protected ProcessChain getTaxBenefitProcessChain(int userId) throws ModulesDaoException, ProcessChainDaoException {
		ProcessChain result = null;
		//int taxBenefitModuleId = getTaxBenefitModuelId();
		ProcessChainDao processChainProvider = ProcessChainDaoFactory.create();
		/**
		 * Uncomment following code if process chain is associated with roles
		 * and comment the next set of statements
		 */
		// ProcessChain[] processChain =
		// processChainProvider.findByDynamicWhere("ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION "
		// +
		// "WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLES WHERE USER_ID= ?) AND MODULE_ID=?)",
		// new Object[] { userId,taxBenefitModuleId});
		// Process chain is obtained without considering it associated to any
		// role.
		ProcessChain[] processChain = processChainProvider.findWhereProcNameEquals(TaxBenefitDeclarationModel.TAX_BENEFIT_PROCESSCHAIN_NAME);
		if (processChain.length == 1){
			result = processChain[0];
		} else{
			throw new ProcessChainDaoException("Unable to find process chain for USERID: " + userId + " and NAME: " + TaxBenefitDeclarationModel.TAX_BENEFIT_PROCESSCHAIN_NAME);
		}
		return (result);
	}

	protected int getTaxBenefitModuelId() throws ModulesDaoException {
		int moduleId = 0;
		ModulesDao moduleProvider = ModulesDaoFactory.create();
		Modules[] module = moduleProvider.findWhereNameEquals(TaxBenefitDeclarationModel.TAX_BENEFIT_MODULE_NAME);
		if (module.length == 1){
			moduleId = module[0].getId();
		} else{
			throw new ModulesDaoException("Module not found with name: " + TaxBenefitDeclarationModel.TAX_BENEFIT_MODULE_NAME);
		}
		return (moduleId);
	}

	protected void rollback(Connection conn, ActionResult result, String errorKey) {
		try{
			conn.rollback();
		} catch (SQLException nestedEx){
			logger.error("Unble to perform rollback.", nestedEx);
			if (errorKey != null){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
			} else{
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(this.DB_ERROR_KEY));
			}
		}
	}

	protected List<TaxBenefitDeclaration> getUserTaxBenefitsDeclaration(int userId, String financialYear) throws TaxBenefitDaoException, TaxBenefitDeclarationDaoException {
		final String userTaxBenefit = "USERID=? AND FINANCIAL_YEAR=?";
		TaxBenefitDao taxBenefitProvider = TaxBenefitDaoFactory.create();
		TaxBenefitDeclarationDao taxBenefitDecProvider = TaxBenefitDeclarationDaoFactory.create();
		// Find all user benefit declaration and set into taxBenefitForm for
		// processing
		TaxBenefitDeclaration[] userTaxBenefitDec = taxBenefitDecProvider.findByDynamicWhere(userTaxBenefit, new Object[] { userId, financialYear });
		for (TaxBenefitDeclaration element : userTaxBenefitDec){
			TaxBenefit taxBenefit = taxBenefitProvider.findByPrimaryKey(element.getBenefitId());
			String benefitName = taxBenefit != null ? taxBenefit.getName() : "???";
			String description = taxBenefit != null ? taxBenefit.getDescription() : "-";
			element.setBenefitName(benefitName);
			element.setDescription(description);
		}
		List<TaxBenefitDeclaration> userCurrentDec = Arrays.asList(userTaxBenefitDec);
		return (userCurrentDec);
	}

	protected String getFinancialYear(Date givenDate) {
		String financialYear = "";
		if (givenDate != null){
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			Date fisicalDate = null;
			calendar.set(Calendar.YEAR, currentYear);
			calendar.set(Calendar.MONTH, Calendar.MARCH);
			calendar.set(Calendar.DATE, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			fisicalDate = calendar.getTime();
			int startYear = givenDate.after(fisicalDate) ? currentYear : currentYear - 1;
			int endYear = startYear + 1;
			financialYear = startYear + "-" + endYear;
		} else{
			financialYear = FBPModel.getFinancialYear();
		}
		return (financialYear);
	}

	public String uploadBenefitDetails(String path, InputStream stream) {
		String notSuccess = "0";
		List<Integer> benefitIds = new ArrayList<Integer>();
		Map<Integer, Integer> userIds = new HashMap<Integer, Integer>();
		Set<Integer> usersTBDReq = new HashSet<Integer>();
		Row row = null;
		try{
			//Creates a cache of benefits Ids
			TaxBenefitAcceptDao taxBenefitAcceptDao = TaxBenefitAcceptDaoFactory.create();
			TaxBenefitDeclarationDao taxBenefitdecDao = TaxBenefitDeclarationDaoFactory.create();
			TaxBenefitDao taxBenefitDao = TaxBenefitDaoFactory.create();
			TaxBenefit[] taxBenefits = taxBenefitDao.findAll();
			for (TaxBenefit eachBenefit : taxBenefits){
				benefitIds.add(eachBenefit.getId());
			}
			//Create a cache of the users ids
			UsersDao usersDao = UsersDaoFactory.create();
			Users[] users = usersDao.findByDynamicWhere(" STATUS NOT IN(1,2,3) AND EMP_ID !=0 ", new Object[] {});
			for (Users eachUser : users)
				userIds.put(eachUser.getEmpId(), eachUser.getId());
			String finYear = getFinancialYear(null);
			InputStream fs = stream;
			//InputStream fs = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			boolean currRecSucc = false;
			Integer[] values = null;
			TaxBenefitAccept taxBenefitAccept = null;
			while (rowIterator.hasNext()){
				currRecSucc = false;
				try{
					values = new Integer[3];
					taxBenefitAccept = new TaxBenefitAccept();
					row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					if (row.getRowNum() == 0) continue;
					while (cellIterator.hasNext()){
						Cell cell = cellIterator.next();
						int colNum = cell.getColumnIndex();
						switch (colNum) {
							case 0:
								values[0] = (int) getCellValue(cell);
								break;
							case 1:
								values[1] = (int) getCellValue(cell);
								break;
							case 2:
								values[2] = (int) getCellValue(cell);
								break;
							default:
								break;
						}
					}
					if (userIds.containsKey(values[0].intValue()) && benefitIds.contains(values[1])){
						taxBenefitAccept.setUserid(userIds.get(values[0].intValue()));
						taxBenefitAccept.setBenefitId(values[1]);
						taxBenefitAccept.setAmount(String.valueOf(values[2]));
						taxBenefitAccept.setFinancialYear(finYear);
						try{
							JDBCUtiility.getInstance().update("DELETE FROM TAX_BENEFIT_ACCEPT WHERE FINANCIAL_YEAR=? AND BENEFIT_ID=? AND USERID=?", new Object[] { finYear, values[1], taxBenefitAccept.getUserid() });
							JDBCUtiility.getInstance().update("DELETE FROM TAX_BENEFIT_DECLARATION WHERE FINANCIAL_YEAR=? AND BENEFIT_ID=? AND USERID=?", new Object[] { finYear, values[1], taxBenefitAccept.getUserid() });
						} catch (Exception e){
							logger.error("unable to delete existing tax_benifit records for " + values[0].intValue(), e);
						}
						taxBenefitAcceptDao.insert(taxBenefitAccept);
						taxBenefitdecDao.insert(new TaxBenefitDeclaration(taxBenefitAccept.getUserid(), taxBenefitAccept.getBenefitId(), taxBenefitAccept.getAmount(), taxBenefitAccept.getFinancialYear()));
						usersTBDReq.add(taxBenefitAccept.getUserid());
						currRecSucc = true;
					}
				} catch (TaxBenefitAcceptDaoException e){
					logger.error("There is a TaxBenefitAcceptDaoException while creating a record for the TaxBenefitAccept for the emp " + (row.getRowNum() + 1));
				} catch (Exception e){
					logger.error("There is a Exception while creating a record for the TaxBenefitAccept for the row in the excel " + (row.getRowNum() + 1));
				}
				if (!currRecSucc){
					notSuccess += "," + (row.getRowNum() + 1);
				}
			}
			TaxBenefitReqDao benefitReqDao = TaxBenefitReqDaoFactory.create();
			int esrSqe = 1;
			int reqId = 18;
			EmpSerReqMapDao empSerReqProvider = EmpSerReqMapDaoFactory.create();
			try{
				esrSqe = Integer.parseInt(JDBCUtiility.getInstance().getSingleColumn("SELECT MAX(ID) FROM TAX_BENEFIT_REQ", null).get(0).toString());
			} catch (Exception e){}
			for (Integer req : usersTBDReq){
				try{
					TaxBenefitReq taxBenefitReq = null;
					TaxBenefitReq[] taxreqs = benefitReqDao.findWhereRequesterIdEquals(req.intValue());
					if (taxreqs != null && taxreqs.length > 0 && taxreqs[0] != null){
						taxBenefitReq = taxreqs[0];
					} else{
						EmpSerReqMap taxBenefitEsr = new EmpSerReqMap();
						taxBenefitEsr.setSubDate(new Date());
						taxBenefitEsr.setReqId("IN-TAX-" + (esrSqe++));
						taxBenefitEsr.setReqTypeId(reqId);
						taxBenefitEsr.setRegionId(1);
						taxBenefitEsr.setRequestorId(req.intValue());
						taxBenefitEsr.setProcessChainId(1);
						//taxBenefitEsr.setNotify("");
						//taxBenefitEsr.setSiblings("");
						empSerReqProvider.insert(taxBenefitEsr);
						taxBenefitReq = new TaxBenefitReq();
						taxBenefitReq.setEsrMapId(taxBenefitEsr.getId());
						taxBenefitReq.setRequesterId(req.intValue());
						taxBenefitReq.setActionBy(16);
					}
					taxBenefitReq.setStatus("Accepted");
					taxBenefitReq.setCreatedOn(new Date());
					taxBenefitReq.setActionOn(new Date());
					if (taxBenefitReq.getId() > 0) benefitReqDao.update(taxBenefitReq.createPk(), taxBenefitReq);
					else benefitReqDao.insert(taxBenefitReq);
				} catch (EmpSerReqMapDaoException e){
					logger.error("esr req not created for " + req, e);
				} catch (TaxBenefitReqDaoException e){
					logger.error("TaxBenefitReq not created for " + req, e);
				}
			}
			try{
				new TdsJob().execute(null);
			} catch (JobExecutionException e){
				logger.error("unable to trigger tds job after tds xls uoload.", e);
			}
		} catch (UsersDaoException e){
			logger.error("There is a UsersDaoException while creating records for the TaxBenefitAccept Upload");
		} catch (IOException e){
			logger.error("There is a IOException while creating records for the TaxBenefitAccept Upload");
		} catch (TaxBenefitDaoException e){
			logger.error("There is a TaxBenefitDaoException while creating records for the TaxBenefitAccept Upload");
		}
		return notSuccess;
	}

	private int getCellValue(Cell cell) {
		int res = 0;
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			res = (int) cell.getNumericCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
			res = Integer.valueOf(cell.getStringCellValue());
		}
		return res;
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		DocumentTypes dTypes = DocumentTypes.valueOf(docType);
		Integer[] nonsuccess = null;
		switch (dTypes) {
			case BENEFIT_XLS:{
				if (fileItems != null && !fileItems.isEmpty()){
					FileItem file = (FileItem) fileItems.get(0);
					InputStream stream = null;
					try{
						stream = file != null ? file.getInputStream() : null;
					} catch (IOException e){
						logger.error("There is a IOException occured while getting the Inputstream for the uploaded file" + e.getMessage());
					}
					String[] temp = uploadBenefitDetails(new PortalData().getDirPath() + File.separator + "Data" + File.separator + "Tax_Benefit_Upload.xls", stream).split(",");
					nonsuccess = new Integer[temp.length];
					for (int i = 0; i < temp.length; i++){
						try{
							nonsuccess[i] = Integer.parseInt(temp[i]);
						} catch (NumberFormatException nfe){};
					}
				}
			}
				break;
			case TDS_XLS:{
				String monthIdt = FBPModel.getMonthId();
				
				if (fileItems != null && !fileItems.isEmpty()){
					FileItem file = (FileItem) fileItems.get(0);
					Connection conn = null;
					TdsDao tdsdao = TdsDaoFactory.create(conn);
					float oldtdsAmt = 0;
					InputStream stream = null;
					int year = 0;
					try{
						year = Integer.parseInt(file.getName().substring(0, file.getName().indexOf(".")));
						stream = file != null ? file.getInputStream() : null;
					} catch (IOException e1){
						e1.printStackTrace();
					}
					if (year == 0){
						request.setAttribute("TDS_MESSAGE", "please rename file like 2013.xls(for year 2013)");
						return new Integer[] { 0 };
					}
					ArrayList notuudatedList = new ArrayList();
					if (stream != null) try{
						Vector<Vector<Object>> list = POIParser.parseXls(stream, 0);
						stream.close();
						if (list != null && !list.isEmpty()){
							Map<Integer, Integer> userids = UsersDaoFactory.create().findAllUsersEmployeeIds();
							TdsDao tdsDao = TdsDaoFactory.create();
							TDSUtility tdsUtility = new TDSUtility();
							for (Vector<Object> row : list){
								try{
									int userId = userids.get(((Double) row.get(0)).intValue());
									Tds tdslist[] = tdsDao.findByDynamicWhere(" USERID =? AND MONTH_ID BETWEEN ? AND ? ", new Object[] { userId, year + "04", (year + 1) + "03" });
									if (tdslist == null || tdslist.length == 0){
										tdsUtility.saveTds(userId);
										tdslist = tdsDao.findByDynamicWhere(" USERID =? AND MONTH_ID BETWEEN  ? AND ? ", new Object[] { userId, year + "04", (year + 1) + "03" });
									}
									for (int i = 4; i < 16; i++){
										Object val = row.get(i - 2);
										String monthId = (i < 13 ? year : year + 1) + (i > 9 ? (i < 13 ? i + "" : "0" + (i % 12)) : "0" + i);
										int srId = 0;
										try{
											if (val != null){
												Tds tdsEntry = getTdsFromList(tdslist, monthId);
												if (tdsEntry == null){
													tdsEntry = new Tds();
													tdsEntry.setMonthId(monthId);
													tdsEntry.setUserid(userId);
												}
												int month = Calendar.getInstance().get(Calendar.MONTH)+1;
												SalaryReconciliationDao sr = SalaryReconciliationDaoFactory.create();
												SalaryReconciliation[] srr = sr.findSrId("MONTH = ? AND YEAR = ?", new Object[] {  month, year});
												for (SalaryReconciliation salaryReconciliation : srr) {
													srId=salaryReconciliation.getId();
												}
												// getting old tds amount for particular user.
												Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { userId, monthIdt });
												
												for(Tds t:tds)
												{
											     oldtdsAmt=   Float.valueOf(t.getAmount());
											    
												}
												// getting new tds amount
												tdsEntry.setAmount(val.toString());
												tdsEntry.setStatus(1);
												
												// selectinng report amount
												if(monthId.equals(monthIdt)) {
												SalaryReconciliationReport[] report = SalaryReconciliationReportDaoFactory.create(conn).findByPrimaryKeySr("SR_ID = ? AND USER_ID = ?", new Object [] {srId ,userId});
												for (SalaryReconciliationReport salaryReconciliationReport : report) {
													
												if(salaryReconciliationReport.getNo_of_modifications() != null && !salaryReconciliationReport.getNo_of_modifications().equals("0"))
												{	//comparing old tds and new tds
													String newTdsAmt = tdsEntry.getAmount();
													
													float diffTds = Float.valueOf(newTdsAmt)- Float.valueOf(oldtdsAmt); 
													Float sal = Float.valueOf(salaryReconciliationReport.getSalary())-diffTds;
													MonthlyPayrollDao mpdao = MonthlyPayrollDaoFactory.create();
													String salDec=DesEncrypterDecrypter.getInstance().encrypt(String.valueOf(sal));
													 String mp=mpdao.salaryReport(salDec,userId,srId);
													
												}
													
												}}
												if (tdsEntry.getId() > 0){
													
													tdsDao.update(tdsEntry.createPk(), tdsEntry);
												} 
												else 
													{
													tdsDao.insert(tdsEntry);
													}
										MonthlyPayrollDao mpdao = MonthlyPayrollDaoFactory.create();
								        String mp=mpdao.updateMonthlyPayroll(tdsEntry.getAmount(),userId,monthId);
											}
										} catch (Exception e){
											logger.error(e.getMessage() + "\tunable to update tds for employee " + row.get(0) + " of month " + monthId);
										}
									}
									logger.info("Tds details updated for " + row.get(1) + "(" + row.get(0) + ")\t\t\t" + row);
								} catch (Exception e){
									logger.error(e.getMessage() + "\tunable to update tds for employee " + row.get(0));
									notuudatedList.add(row.get(0));
								}
							}
						}
						if (notuudatedList.size() == 0) request.setAttribute("TDS_MESSAGE", "Tds updated successfully.");
						else request.setAttribute("TDS_MESSAGE", "TDS updated successfully.\nBut unable to update following employees TDS.\nEmp Id(s):" + ModelUtiility.getCommaSeparetedValues(notuudatedList));
						return new Integer[] { 0, 1 };
					} catch (Exception e){
						request.setAttribute("TDS_MESSAGE", "unable to update tds.\n Cause:" + e.getMessage());
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return nonsuccess;
	}

	private Tds getTdsFromList(Tds[] tdsList, String monthId) {
		if (tdsList == null) return null;
		for (Tds tds : tdsList)
			if (tds.getMonthId().equals(monthId)) return tds;
		return null;
	}

	public static void main(String[] args) {
		//logger.info(new TaxBenefitDeclarationModel().uploadBenefitDetails("/home/praneeth.r/Desktop/Tax_Benefit_Upload.xls", null));
		int year = 2013;
		for (int i = 4; i < 16; i++){
			String monthId = (i < 13 ? year : year + 1) + (i > 9 ? (i < 13 ? i + "" : "0" + (i % 12)) : "0" + i);
			logger.info(monthId);
		}
	}
}
