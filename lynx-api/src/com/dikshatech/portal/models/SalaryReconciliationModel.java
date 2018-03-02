package com.dikshatech.portal.models;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.MonthlyPayrollBean;
import com.dikshatech.beans.SalaryReconciliationBean;
import com.dikshatech.beans.SalaryReportBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.LeaveLwpDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.MonthlyPayrollDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.SalaryRecReportHistoryDao;
import com.dikshatech.portal.dao.SalaryReconciliationDao;
import com.dikshatech.portal.dao.SalaryReconciliationHoldDao;
import com.dikshatech.portal.dao.SalaryReconciliationReportDao;
import com.dikshatech.portal.dao.SalaryReconciliationReqDao;
import com.dikshatech.portal.dao.TdsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dao.VoluntaryProvidentFundDao;
import com.dikshatech.portal.dto.CompareErrorReport;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.ESIC;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.FinanceInfo;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.LeaveLwp;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.MonthlyPayroll;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SalaryRecReportHistory;
import com.dikshatech.portal.dto.SalaryReconcilationESICStatus;
import com.dikshatech.portal.dto.SalaryReconcilationESICStatusList;
import com.dikshatech.portal.dto.SalaryReconcilationFBPStatus;
import com.dikshatech.portal.dto.SalaryReconcilationFBPStatusList;
import com.dikshatech.portal.dto.SalaryReconciliation;
import com.dikshatech.portal.dto.SalaryReconciliationHold;
import com.dikshatech.portal.dto.SalaryReconciliationReport;
import com.dikshatech.portal.dto.SalaryReconciliationReq;
import com.dikshatech.portal.dto.Tds;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.VoluntaryProvidentFund;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.FbpDetailsDaoException;
import com.dikshatech.portal.exceptions.FbpReqDaoException;
import com.dikshatech.portal.exceptions.LeaveBalanceDaoException;
import com.dikshatech.portal.exceptions.MonthlyPayrollDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.SalaryReconciliationDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.exceptions.VoluntaryProvidentFundDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.LeaveLwpDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.MonthlyPayrollDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.SalaryRecReportHistoryDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationHoldDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationReqDaoFactory;
import com.dikshatech.portal.factory.TdsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.factory.VoluntaryProvidentFundDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.timer.EscalationJob;
import com.dikshatech.portal.timer.MonthlySalaryJob;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class SalaryReconciliationModel extends ActionMethods {

	private static Logger						logger				= LoggerUtil.getLogger(SalaryReconciliationModel.class);
	private static SalaryReconciliationModel	salaryReconciliationModel;
	public static final int						MODULEID			= 65;
	public static final short					AUTO				= 0;
	public static final short					MODIFIED			= 1;
	public static final short					HOLD				= 2;
	public static final short					RELEASED			= 3;
	public static final short					REJECTED			= 4;
	private static final boolean				isCompleteApprove	= true;

	public static SalaryReconciliationModel getInstance() {
		if (salaryReconciliationModel == null) salaryReconciliationModel = new SalaryReconciliationModel();
		return salaryReconciliationModel;
	}
	protected java.sql.Connection	userConn;
	public static final String	CATEGORY	= "SALARY RECONCILIATION";
	protected int					maxRows;

	private SalaryReconciliationModel() {}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) throws VoluntaryProvidentFundDaoException, FbpReqDaoException, SalaryDetailsDaoException, FbpDetailsDaoException {
		Login login = Login.getLogin(request);
		ActionResult result = new ActionResult();
		SalaryReconciliationBean formBean = (SalaryReconciliationBean) form;
		if (ModelUtiility.hasModulePermission(login, MODULEID)){
			logger.info("salary reconciliation job triggered by " + ModelUtiility.getInstance().getEmployeeName(login.getUserId()));
			int res = new MonthlySalaryJob().doJob(formBean.getId() == 0 ? 0 : login.getUserId());
			if (res == 1){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.confirm"));
			} else if (res == 2){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.error", "Salary"));
			}
			else if (res == 3){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.bonus.error"));
			}
		} else{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access salary reconciliation receive Urls without having permisson at :" + new Date());
		}
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	private int getDivisionId(int userId, Connection conn) {
		try{
			LevelsDao levelsdao = LevelsDaoFactory.create(conn);
			DivisonDao divisionDao = DivisonDaoFactory.create(conn);
			Users user = UsersDaoFactory.create(conn).findByPrimaryKey(userId);
			Levels level = levelsdao.findByPrimaryKey(user.getLevelId());
			Divison division = divisionDao.findByPrimaryKey(level.getDivisionId());
			if (division != null){
				if (division.getParentId() != 0){
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) return division.getId();
				} else return division.getId();
			}
		} catch (Exception e){}
		return 0;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access salary reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		request.setAttribute("actionForm", "");
		SalaryReconciliationBean formBean = (SalaryReconciliationBean) form;
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			/*switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEALL:{
					try{
						String query = "SELECT S.* ,(SRR.ACTION_BY=0 && (SRR.LEVEL = (SELECT SRRI.LEVEL FROM SALARY_RECONCILIATION_REQ SRRI WHERE SRRI.ID=(SELECT MAX(SRRII.ID) FROM SALARY_RECONCILIATION_REQ SRRII WHERE SRRII.SR_ID=(S.ID)))))  AS CANEDIT,(SELECT REQ_ID FROM EMP_SER_REQ_MAP ESRM WHERE ESRM.ID=S.ESR_MAP_ID ) AS REQ_ID FROM SALARY_RECONCILIATION S JOIN SALARY_RECONCILIATION_REQ SRR ON SRR.SR_ID=S.ID WHERE SRR.ID IN (SELECT MAX(SRRII.ID) FROM SALARY_RECONCILIATION_REQ SRRII WHERE SRRII.ASSIGNED_TO=? GROUP BY SRRII.SR_ID)";
						SalaryReconciliation[] reconciliations = SalaryReconciliationDaoFactory.create(conn).findByDynamicSelect(query, new Object[] { login.getUserId() });
						formBean.setList(setStatusString(reconciliations));
						formBean.setIsFinance(getDivisionId(login.getUserId(), conn) == 3 ? "1" : "0"); //check for finance or not
						request.setAttribute("actionForm", formBean);
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVEALL : failed to fetch data for userId=" + login.getUserId(), ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						return result;
					}
					break;
				}*/
			int usId=login.getUserId();
			LevelsDao levelDao=LevelsDaoFactory.create(conn);
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEALL:{
					try{
						int levelss=0;
						 levelss =levelDao.findWhereUsersIdEquals(usId);
							
						String query = "SELECT S.* ,(SRR.ACTION_BY=0 && (SRR.LEVEL = (SELECT SRRI.LEVEL FROM SALARY_RECONCILIATION_REQ SRRI WHERE SRRI.ID=(SELECT MAX(SRRII.ID) FROM SALARY_RECONCILIATION_REQ SRRII WHERE SRRII.SR_ID=(S.ID)))))  AS CANEDIT,(SELECT REQ_ID FROM EMP_SER_REQ_MAP ESRM WHERE ESRM.ID=S.ESR_MAP_ID ) AS REQ_ID FROM SALARY_RECONCILIATION S JOIN SALARY_RECONCILIATION_REQ SRR ON SRR.SR_ID=S.ID WHERE SRR.ID IN (SELECT MAX(SRRII.ID) FROM SALARY_RECONCILIATION_REQ SRRII WHERE SRRII.ASSIGNED_TO=? GROUP BY SRRII.SR_ID)";
						SalaryReconciliation[] reconciliations = SalaryReconciliationDaoFactory.create(conn).findByDynamicSelect(query, new Object[] { login.getUserId() });
						formBean.setList(setStatusString(reconciliations));
						
						
					//	formBean.setIsFinance(getDivisionId(login.getUserId(), conn) == 3 ? "1" : "0"); //check for finance or not
						
						
						if(getDivisionId(login.getUserId(), conn) == 3 ){
							
							formBean.setIsFinance("1");
							
						}
						else if( (getDivisionId(login.getUserId(), conn) == 2) || (getDivisionId(login.getUserId(), conn) == 31  )){
							
							formBean.setIsFinance("2");
							
						}
						else {
							
							formBean.setIsFinance("0");
						}
						formBean.setPermissionFlagId(levelss);
						request.setAttribute("actionForm", formBean);
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVEALL : failed to fetch data for userId=" + login.getUserId(), ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						return result;
					}
					break;
				}
				case RECEIVEALL_ESIC_STATUS:{
					java.sql.Connection userConn=null;
					final boolean isConnSupplied = (userConn != null);
					Connection conn1 = null;
					PreparedStatement stmt = null;
					ResultSet  rset= null;
					try {
						
						
						String sqlQuery="select sd.USER_ID,us.EMP_ID,p.FIRST_NAME,p.LAST_NAME,sd.ESIC from SALARY_DETAILS sd,USERS us,PROFILE_INFO p where sd.USER_ID=us.ID and us.PROFILE_ID=p.ID and sd.ESIC='yes' GROUP BY us.EMP_ID;";
						//SalaryReconcilationESICStatusList reconciliation = (SalaryReconcilationESICStatusList) SalaryReconciliationReportDaoFactory.create(conn1);
						conn1 = isConnSupplied ? userConn : ResourceManager.getConnection();
						stmt = conn1.prepareStatement(sqlQuery);
						rset = stmt.executeQuery();
						SalaryReconcilationESICStatusList listdata=new SalaryReconcilationESICStatusList();
						List<SalaryReconcilationESICStatus> list = new ArrayList();
						List<SalaryReconcilationESICStatus> listsendable= new ArrayList();
						while(rset.next())
						{
							SalaryReconcilationESICStatus obj=new SalaryReconcilationESICStatus();
							obj.setUserId((Integer)rset.getInt(1));
						obj.setEmployeeId((Integer)rset.getInt(2));
						obj.setFirstName((String)rset.getString(3));
						obj.setLastName((String)rset.getString(4));
						obj.setEsicStatus((String)rset.getString(5));
						list.add(obj);
			 
						}
						
						for(SalaryReconcilationESICStatus sres:list){
							try {
								Double grossSalary=0.00;
								final boolean isConnSupplied1 = (userConn != null);
								Connection conn2 = null;
							Integer userid=	sres.getUserId();
							String monthIdT = FBPModel.getMonthId();
							String datecoming=formBean.getDate();
							String a[]=datecoming.split("-");
							//System.out.println("date:"+a[2]);
							String dateesic=a[0]+""+a[1];
							//String startDateString = "06/27/2007";
							/*DateFormat df = new SimpleDateFormat("YYYYmm"); 
							
							Date startDate;
							
							    startDate = df.parse(datecoming);
							    System.out.println(startDate);*/
							    
							    /*String newDateString = df.format(startDate);*/
							  /*  System.out.println("Going date"+newDateString);*/
							
							
							//System.out.println(sres.getFirstName());
							//System.out.println(monthIdT);
							String sql1="SELECT  Amount FROM DIKSHA_PORTAL_2.MONTHLY_PAYROLL where MONTHID ="+dateesic+" and USERID ="+userid+" AND(COMPONENT_TYPE=0 OR COMPONENT_TYPE=1);";
							//SalaryReconcilationESICStatusList reconciliation1 = (SalaryReconcilationESICStatusList) SalaryReconciliationReportDaoFactory.create(conn);
							if(conn2==null){
							conn2 = isConnSupplied1 ? userConn : ResourceManager.getConnection();
							}
							stmt = conn2.prepareStatement(sql1);
							rset = stmt.executeQuery();
							SalaryReconcilationESICStatusList listdata1=new SalaryReconcilationESICStatusList();
							//List<SalaryReconcilationESICStatus> list1 = new ArrayList();
							while(rset.next())
							{
								SalaryReconcilationESICStatus obj1=new SalaryReconcilationESICStatus();
							
							Double amt = new Double((DesEncrypterDecrypter.getInstance().decrypt (rset.getString(1))));
							grossSalary=grossSalary+amt;
							obj1.setGrossSalary(grossSalary);
							//list1.add(obj1);
							}
							Double value = grossSalary;
						    Double valueRounded = Math.round(value * 100D) / 100D;
							sres.setGrossSalary(valueRounded);
							listsendable.add(sres);
							listsendable.toString();
							conn2.close();
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
						listdata.setSalaryReconcilationESICStatusList(listsendable);
						request.setAttribute("actionForm", listdata);
						
					}
					
					catch (SQLException se) {
						se.printStackTrace();
					} 
					
					catch (Exception e) {
						e.printStackTrace();
					}finally{
						conn1.close();
						stmt.close();
						rset.close();
					}
					
					break;
				}
				case RECEIVEALL_FBP_STATUS:{
					java.sql.Connection userConn=null;
					final boolean isConnSupplied = (userConn != null);
					Connection conn1 = null;
					PreparedStatement stmt = null;
					ResultSet  rset= null;
					try {
						
						
						String sqlQuery="select fbp.USER_ID, us.EMP_ID,p.FIRST_NAME,p.LAST_NAME,fbp.STATUS as FBPSTATUS from FBP_REQ "
								+ "fbp,USERS us,PROFILE_INFO p where fbp.USER_ID=us.ID and us.PROFILE_ID=p.ID and fbp.STATUS='APPROVED'"
								+ " and fbp.`SEQUENCE`='1' group by us.EMP_ID;";
						//SalaryReconcilationESICStatusList reconciliation = (SalaryReconcilationESICStatusList) SalaryReconciliationReportDaoFactory.create(conn1);
						conn1 = isConnSupplied ? userConn : ResourceManager.getConnection();
						stmt = conn1.prepareStatement(sqlQuery);
						rset = stmt.executeQuery();
						SalaryReconcilationFBPStatusList listdata=new SalaryReconcilationFBPStatusList();
						List<SalaryReconcilationFBPStatus> list = new ArrayList();
						List<SalaryReconcilationFBPStatus> listsendable= new ArrayList();
						while(rset.next())
						{
							SalaryReconcilationFBPStatus obj=new SalaryReconcilationFBPStatus();
							obj.setUserId((Integer)rset.getInt(1));
						obj.setEmployeeId((Integer)rset.getInt(2));
						obj.setFirstName((String)rset.getString(3));
						obj.setLastName((String)rset.getString(4));
						obj.setFbpStatus((String)rset.getString(5));
						list.add(obj);
			 
						}
						
						for(SalaryReconcilationFBPStatus sres:list){
							try {
								Double grossSalary=0.00;
								final boolean isConnSupplied1 = (userConn != null);
								Connection conn2 = null;
							Integer userid=	sres.getUserId();
							String monthIdT = FBPModel.getMonthId();
							String datecoming=formBean.getDate();
							String a[]=datecoming.split("-");
						
							String dateesic=a[0]+""+a[1];
							
							String sql1="SELECT  Amount FROM DIKSHA_PORTAL_2.MONTHLY_PAYROLL where MONTHID ="+dateesic+" and USERID ="+userid+" AND(COMPONENT_TYPE=0 OR COMPONENT_TYPE=1);";
							//SalaryReconcilationESICStatusList reconciliation1 = (SalaryReconcilationESICStatusList) SalaryReconciliationReportDaoFactory.create(conn);
							if(conn2==null){
							conn2 = isConnSupplied1 ? userConn : ResourceManager.getConnection();
							}
							stmt = conn2.prepareStatement(sql1);
							rset = stmt.executeQuery();
							SalaryReconcilationFBPStatusList listdata1=new SalaryReconcilationFBPStatusList();
							//List<SalaryReconcilationESICStatus> list1 = new ArrayList();
							while(rset.next())
							{
								SalaryReconcilationFBPStatus obj1=new SalaryReconcilationFBPStatus();
							
							Double amt = new Double((DesEncrypterDecrypter.getInstance().decrypt (rset.getString(1))));
							grossSalary=grossSalary+amt;
							obj1.setGrossSalary(grossSalary);
							//list1.add(obj1);
							}
							Double value = grossSalary;
						    Double valueRounded = Math.round(value * 100D) / 100D;
							sres.setGrossSalary(valueRounded);
							listsendable.add(sres);
							listsendable.toString();
							conn2.close();
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
						listdata.setSalaryReconcilationFBPStatusList(listsendable);
						request.setAttribute("actionForm", listdata);
						
					}
					
					catch (SQLException se) {
						se.printStackTrace();
					} 
					
					catch (Exception e) {
						e.printStackTrace();
					}finally{
						conn1.close();
						stmt.close();
						rset.close();
					}
					
					break;
				}
				case RECEIVE:{
					try{
						SalaryReconciliationReport[] reconciliationsReports=null;
						int userId = 0;
						UsersDao usersDao = UsersDaoFactory.create();
						TdsDao tdsdao = TdsDaoFactory.create(conn);
						SalaryReconciliation reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(formBean.getId());
						formBean.setTerm(getTerm(reconciliation.getMonth(), reconciliation.getYear()));
						
						String flagBank=formBean.getBankFlag();
						float tms = 0;
						String salAmt = 0+"";
						 float total = 0;
						 Float tdsAmt = (float) 0;
						 float tsal = 0;
						if(flagBank!=null){
							if(flagBank.equals("BANK_FLAG")){
								
								 reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelect("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID " + "FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID WHERE SR_ID=? ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId() });

								 //		 reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelect("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID, D.NAME " + "FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID LEFT JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID WHERE SR_ID=? AND D.ID = 12 ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId(),/*formBean.getDeptId()*/ });

									List<SalaryReconciliationReport> list = new ArrayList<SalaryReconciliationReport>(), modified = new ArrayList<SalaryReconciliationReport>();
									List<SalaryReconciliationReport> hold = new ArrayList<SalaryReconciliationReport>(), released = new ArrayList<SalaryReconciliationReport>(), rejected = new ArrayList<SalaryReconciliationReport>();
									for (SalaryReconciliationReport report : reconciliationsReports){
										
										
							//			String monthId = FBPModel.getMonthId();
										
										
										
										
						                  Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { report.getUserId(), formBean.getTerm() });
						               
											for(Tds t:tds)
											{
										     tdsAmt=   Float.valueOf(t.getAmount());
											}
										
										
											if(!report.getNo_of_modifications().equals("0"))
											{
												float  amt = Float.valueOf(report.getSalary());
												tsal = amt + tdsAmt;
												report.setSalary(String.valueOf(tsal));
											}
											else
											{
												report.setSalary(report.getSalary());
											}
							
										
										report.setSalary(Math.round(Double.parseDouble(report.getSalary()))+"");
										if(report.getSalaryCycle()==null){
											report.setSalaryCycle("0");
											
										}
										switch (report.getStatus()) {
									
											case AUTO:
											case MODIFIED:
												total +=Double.parseDouble(report.getSalary());
												if (report.getModifiedBy() == null) list.add(report);
												else modified.add(report);
												break;
											case HOLD:
												total += Double.parseDouble(report.getSalary());
												total=Math.round(total);
												
												report.setRelease(JDBCUtiility.getInstance().getRowCount(" FROM SALARY_RECONCILIATION_HOLD WHERE USER_ID=? AND REP_ID=?", new Object[] { login.getUserId(), report.getId() }, conn) > 0 ? "1" : null);
												hold.add(report);
												break;
											case RELEASED:
												total += Double.parseDouble(report.getSalary());
												total=Math.round(total);
												released.add(report);
												break;
											case REJECTED:
												rejected.add(report);
												break;
										}
									}
									formBean.setSalary(new DecimalFormat("0.00").format(total));
									formBean.setList(list.toArray(new SalaryReconciliationReport[list.size()]));
									if (modified.size() > 0) formBean.setModified(modified.toArray(new SalaryReconciliationReport[modified.size()]));
									if (hold.size() > 0) formBean.setHold(hold.toArray(new SalaryReconciliationReport[hold.size()]));
									if (released.size() > 0) formBean.setReleased(released.toArray(new SalaryReconciliationReport[released.size()]));
									if (rejected.size() > 0) formBean.setRejected(rejected.toArray(new SalaryReconciliationReport[rejected.size()]));
							}else{
								List<SalaryReconciliationReport> listHdfc = new ArrayList<SalaryReconciliationReport>();
								List<SalaryReconciliationReport> listNonHdfc = new ArrayList<SalaryReconciliationReport>();
								
								if(flagBank.equals("HDFC_BANK")){
									if(formBean.getDeptId()==0)
									{
									reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelectBank("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID ,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID   JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=?  AND  F.PRIM_BANK_NAME Like '%HDFC%' ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId() });
									for (SalaryReconciliationReport report : reconciliationsReports){
										 Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { report.getUserId(), formBean.getTerm() });
							               
											for(Tds t:tds)
											{
										     tdsAmt=   Float.valueOf(t.getAmount());
											}
										if(!report.getNo_of_modifications().equals("0"))
										{
											float  amt = Float.valueOf(report.getSalary());
											tsal = amt + tdsAmt;
											report.setSalary(String.valueOf(tsal));
										}
										else
										{
											report.setSalary(String.valueOf(report.getSalary()));
										}
									}	
									}
									
									else
									{
										
								
									reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelectBank("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID ,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME, D.NAME AS DEPT_NAME FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID  JOIN  LEVELS L ON L.ID = U.LEVEL_ID JOIN  DIVISON D ON L.DIVISION_ID = D.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=? AND D.ID = ? AND  F.PRIM_BANK_NAME Like '%HDFC%' ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId(),formBean.getDeptId() });
									for (SalaryReconciliationReport report : reconciliationsReports){
										 Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { report.getUserId(), formBean.getTerm() });
							               
											for(Tds t:tds)
											{
										     tdsAmt=   Float.valueOf(t.getAmount());
											}
										if(!report.getNo_of_modifications().equals("0"))
										{
											float  amt = Float.valueOf(report.getSalary());
											tsal = amt + tdsAmt;
											report.setSalary(String.valueOf(tsal));
										}
										else
										{
											report.setSalary(String.valueOf(report.getSalary()));
										}
									}
									
									
									}
									//	reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelectBank("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID ,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=? AND  F.PRIM_BANK_NAME  Like '%HDFC%' ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId() });

								}else{
									
									if(formBean.getDeptId()==0)
									{
										
									reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelectBank("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID ,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID   JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=?   AND F.PRIM_BANK_NAME NOT Like '%HDFC%' ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId() });
									for (SalaryReconciliationReport report : reconciliationsReports){
										 Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { report.getUserId(), formBean.getTerm() });
							               
											for(Tds t:tds)
											{
										     tdsAmt=   Float.valueOf(t.getAmount());
											}
										if(!report.getNo_of_modifications().equals("0"))
										{
											float  amt = Float.valueOf(report.getSalary());
											tsal = amt + tdsAmt;
											report.setSalary(String.valueOf(tsal));
										}
										else
										{
											report.setSalary(String.valueOf(report.getSalary()));
										}
									}
									}
									else
									{
										
								
									reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelectBank("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID ,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME, D.NAME AS DEPT_NAME FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID  JOIN  LEVELS L ON L.ID = U.LEVEL_ID JOIN  DIVISON D ON L.DIVISION_ID = D.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=?  AND D.ID = ?  AND F.PRIM_BANK_NAME NOT Like '%HDFC%' ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId(),formBean.getDeptId() });
									for (SalaryReconciliationReport report : reconciliationsReports){
										 Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { report.getUserId(), formBean.getTerm() });
							               
											for(Tds t:tds)
											{
										     tdsAmt=   Float.valueOf(t.getAmount());
											}
										if(!report.getNo_of_modifications().equals("0"))
										{
											float  amt = Float.valueOf(report.getSalary());
											tsal = amt + tdsAmt;
											report.setSalary(String.valueOf(tsal));
										}
										else
										{
											report.setSalary(String.valueOf(report.getSalary()));
										}
									}
							//		reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelectBank("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID ,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=? AND  F.PRIM_BANK_NAME NOT Like '%HDFC%' ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId() });
									}
								}
								for (SalaryReconciliationReport report : reconciliationsReports){
									
									if(report.getSalaryCycle()==null){
										report.setSalaryCycle("0");
										
									}
									if(flagBank.equals("HDFC_BANK")){
									total += Double.parseDouble(report.getSalary());
									total=Math.round(total);
									listHdfc.add(report);
									}else{
										total += Double.parseDouble(report.getSalary());
										total=Math.round(total);
										listNonHdfc.add(report);
									}
									
								}
								formBean.setSalary(new DecimalFormat("0.00").format(total));
								if(flagBank.equals("HDFC_BANK")){
								formBean.setListHdfc(listHdfc.toArray(new SalaryReconciliationReport[listHdfc.size()]));
								}else{
								formBean.setListNonHdfc(listNonHdfc.toArray(new SalaryReconciliationReport[listNonHdfc.size()]));
								}
							}
						}
						
						
						request.setAttribute("actionForm", formBean);
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVE : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						return result;
					}
					break;
				}
				/*RECEIVEALLPAIDANDUNPAID*/
				case RECEIVEALLPAIDANDUNPAID:{
					try{
						SalaryReconciliationReport[] reconciliationsReports=null;
						SalaryReconciliation reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(formBean.getId());
						formBean.setTerm(getTerm(reconciliation.getMonth(), reconciliation.getYear()));
						String flagBank=formBean.getFlag();
						 double total = 0;
						 List<SalaryReconciliationReport> list = new ArrayList<SalaryReconciliationReport>();
						
						if(flagBank!=null){
						 
							reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findAllPaidAndUnpaid(formBean.getId(),flagBank);
						
						}
						
					
						 for (SalaryReconciliationReport report : reconciliationsReports){
							 if(report.getSalaryCycle()==null){
									report.setSalaryCycle("0");
									
								}
							 if(report.getPaid()==null){
								 report.setPaid("Un paid");
							 }
							 total += Double.parseDouble(report.getSalary());
								total=Math.round(total);
								list.add(report);
						 }
						 formBean.setSalary(new DecimalFormat("0.00").format(total));
							if(flagBank.equals("HDFC_BANK")){
						     formBean.setListHdfc(list.toArray(new SalaryReconciliationReport[list.size()]));
							}else{
								 formBean.setListNonHdfc(list.toArray(new SalaryReconciliationReport[list.size()]));
							}
					    request.setAttribute("actionForm", formBean);
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVE : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						return result;
					}
					break;
				}
				
				case RECEIVEPAY:{
					try{
						//SalaryReconciliationReport[] reconciliationsReports=null;
						String updateReceivePay=null;
						SalaryReconciliation reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(formBean.getId());
						formBean.setTerm(getTerm(reconciliation.getMonth(), reconciliation.getYear()));
	
						String flagBank=formBean.getBankFlag();
						 double total = 0;
						 double valueRounded1 = 0;
						 List<SalaryReconciliationReport> list = new ArrayList<SalaryReconciliationReport>();
						
							String amount=formBean.getTotalAmount();
						    String[] str=amount.split(",");
							
						    ArrayList<Integer> amt=new ArrayList<Integer>();
						    for(String w:str){  
						    	
						    	total+=Float.valueOf(w);
						    	valueRounded1 = Math.round(total * 100D) / 100D;
						    	
						    }
						    
							MailGenerator mailGenerator = new MailGenerator();
							PortalMail pMail = new PortalMail();
							List<String> allReceipientcCMailId = new ArrayList<String>();

							Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
							String regAbbrivation =  "IN";
							String mailId;
							mailId = pro.getProperty("mailId." + regAbbrivation + ".CEO");
							allReceipientcCMailId.add(mailId);
							pMail.setMailSubject("Amount Debited");
							pMail.setTotalAmount(Double.toString(valueRounded1));
							pMail.setTemplateName(MailSettings.AMOUNT_TO_BE_DEDUCTED);
							pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
							mailGenerator.invoker(pMail);
						    
		
					//	request.setAttribute("actionForm", updateReceivePay);
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVE : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						return result;
					}
					break;
				}
				
				case RECEIVEREPORT:{
					float  a = 0;
					 float b = 0;
					 float esicCal = 0;
					 float tdsAmt = 0;
					 float tsal = 0;
					 TdsDao tdsdao = TdsDaoFactory.create(conn);
					 ArrayList<Tds[]> tdssal = new ArrayList<Tds[]>();
					try{
						
						SalaryReconciliationReport report = SalaryReconciliationReportDaoFactory.create(conn).findByPrimaryKey(formBean.getId());
					
						
						
						
						String monthId = FBPModel.getMonthId();
					
						Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { formBean.getUserId(), monthId });
						
						for(Tds t:tds)
						{
					     tdsAmt=   Float.valueOf(t.getAmount());
				
						if(report.getNo_of_modifications().equals("0"))	
						{  
							report.setSalary(String.valueOf(report.getSalary()));
						}
						
						else
						{
							float  amt = Float.valueOf(report.getSalary());
							tsal = amt + tdsAmt;
							report.setSalary(String.valueOf(tsal));
						
						}
						}
						
						if (report != null){
							List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();
							if (report.getModifiedBy() == null){
								historyList.add(report.toHistory().toHashMap());
							} else{
								SalaryRecReportHistoryDao historyDao = SalaryRecReportHistoryDaoFactory.create(conn);
								SalaryRecReportHistory[] historyArray = historyDao.findWhereSrrIdEquals(report.getId());
								if (historyArray != null) for (SalaryRecReportHistory history : historyArray){
									historyList.add(history.toHashMap());
								}
								int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId(), conn);
								String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId(), conn) + "(" + empId + ")";
								if (!report.getModifiedBy().equalsIgnoreCase(empName)){
									historyList.add(report.toHistory().toHashMap());
									report.setComments("");
								}
							}
							Map<String, Object> map = report.toHashMap();
							ArrayList<MonthlyPayrollBean> additions = new ArrayList<MonthlyPayrollBean>(), deductions = new ArrayList<MonthlyPayrollBean>();
							
							ArrayList<ESIC> esic = new ArrayList<ESIC>();
					
							SalaryReconciliation reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(report.getSrId());
							formBean.setTerm(getTerm(reconciliation.getMonth(), reconciliation.getYear()));
							
							if (formBean.getUserId() > 0 && Integer.parseInt(formBean.getTerm()) > 0){
								MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create(conn);
								
								try{
									MonthlyPayroll[] monthlyPayrolls = monthlyPayrollDao.findByDynamicWhere(" USERID = ? AND MONTHID = ? ", new Object[] { formBean.getUserId(), formBean.getTerm() });
									for (MonthlyPayroll eachMp : monthlyPayrolls){
										if (eachMp.getComponentType() == 0 || eachMp.getComponentType() == 1||eachMp.getComponentType() == 3)
										{  
											String total = (DesEncrypterDecrypter.getInstance().decrypt (eachMp.getAmount()));
									        a += Float.valueOf(total);
										additions.add(eachMp.getBean(form,a));
											 } 
										else if (eachMp.getComponentType() == 2)
										{
											
										deductions.add(eachMp.getBean(form,a));
											
											
									
											
										} 
										else
										{
											logger.error("The record in the Monthlypayrol tab;e has component type " + eachMp.getComponentType() + " for the record " + eachMp.getId());
										}
									}
								} catch (MonthlyPayrollDaoException e){
									logger.debug("There is a MonthyPayrollDaoException Occured for the u");
								}
							}
							// calculating LWP
							ArrayList<LeaveLwp[]> balance = new ArrayList<LeaveLwp[]>();
						double payableDays = 0;
						float days = 0;
						float a1 = 0;
						float lwpvalue = 0;
						Calendar calendar = Calendar.getInstance();
						int currentMonth = (calendar.get(Calendar.MONTH) + 1);// 11
						int previousMonth = (calendar.get(Calendar.MONTH));// 10
						int currentYear = Calendar.getInstance().get(Calendar.YEAR);// 2017
						String previousThirdCycle = currentYear + "-" + previousMonth + "-" + 3;
						String currentFirstCycle = currentYear + "-" + currentMonth + "-" + 1;
						String currentSecondCycle = currentYear + "-" + currentMonth + "-" + 2;
						LeaveLwp lwp = new LeaveLwp();
						LeaveLwpDao leaveLwpDao = LeaveLwpDaoFactory.create();
						LeaveLwp[] lp = leaveLwpDao.findByDynamicSelect(
								"SELECT * FROM LEAVE_LWP WHERE USER_ID = ? AND( MONTH_CYCLE =? OR MONTH_CYCLE = ? OR MONTH_CYCLE = ?) ",
								new Object[] { formBean.getUserId(), previousThirdCycle, currentFirstCycle,
										currentSecondCycle });
						if (lp.length <= 0 || lp[0] == null) {
							 lwpvalue = 0;
						} else {

							for (LeaveLwp leaveLwp : lp) {
								lwpvalue += leaveLwp.getLwp();
								lwp.setLwp(lwpvalue);
								
				
								

							}
						}
							/*LeaveBalanceDao leavebalancedao = LeaveBalanceDaoFactory.create(conn);
							LeaveBalance[] leavebalance =findByUserID(" USER_ID = ? ",new Object[] {formBean.getUserId()}); 
							for(LeaveBalance lb:leavebalance)
							{
								float leaavebalance =  lb.getLeaveAccumalated() - lb.getLeavesTaken();
								if(leaavebalance>0)
								{
									lb.setLwp(0);
								}
								else
								{
									float lwpleave = Math.abs(leaavebalance);
									lb.setLwp(lwpleave);
									balance.add(leavebalance);
							}
							}*/
							map.put("history", historyList);
							if (additions.size() > 0) map.put("additions", additions.toArray());
							if (deductions.size() > 0) map.put("deductions", deductions.toArray());
							if(balance.size()>0) map.put("Balance",balance.toArray());
							map.put("Tdssal", tdssal.toArray());
							if(esic.size()>0) map.put("esic",esic.toArray());
							
 							request.setAttribute("actionForm", map);
						} else{
							logger.error("No record associated with given Report id : " + formBean.getId());
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
							return result;
						}
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVE : failed to fetch data " + ex);
						ex.printStackTrace();
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						return result;
					}
					break;
				}
				case BANKACCOUNTS:{
					SalaryReconciliationReport report = SalaryReconciliationReportDaoFactory.create(conn).findByPrimaryKey(formBean.getId());
					FinanceInfo finance = FinanceInfoDaoFactory.create(conn).findByUserId(report.getUserId());
					Map<String, String> map = new HashMap<String, String>();
					if (finance != null){
						if (finance.getPrimBankName() != null && !finance.getPrimBankName().trim().equals("")) map.put("primary", finance.getPrimBankName() + " - " + finance.getPrimBankAccNo());
						if (finance.getSecBankName() != null && !finance.getSecBankName().trim().equals("")) map.put("secondary", finance.getSecBankName() + " - " + finance.getSecBankAccNo());
						map.put("selected", String.valueOf(report.getAccountType()));
					}
					request.setAttribute("actionForm", map);
				}
				
				
			}
		} catch (Exception e){
			logger.error("", e);
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}



	public LeaveBalance[] findByUserID(String sql, Object[] sqlParams) throws LeaveBalanceDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					// get the user-specified connection or get a connection from the ResourceManager
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
					// construct the SQL statement
					
					final String SQL = "SELECT LEAVE_ACCUMALATED,LEAVES_TAKEN FROM LEAVE_BALANCE "+ " WHERE " + sql;
					if (logger.isDebugEnabled()){
						logger.debug("Executing " + SQL);
					}
					// prepare statement
					stmt = conn.prepareStatement(SQL);
					stmt.setMaxRows(maxRows);
					// bind parameters
					for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
						stmt.setObject(i + 1, sqlParams[i]);
					}
					rs = stmt.executeQuery();
					// fetch the results
					return fetchMultiResultsLeave(rs);
				} catch (Exception _e){
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new LeaveBalanceDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
			}
	protected LeaveBalance[] fetchMultiResultsLeave(ResultSet rs) throws SQLException {
		Collection<LeaveBalance> resultList = new ArrayList<LeaveBalance>();
		while (rs.next()){
			LeaveBalance dto = new LeaveBalance();
			populateDtoLeave(dto, rs);
			resultList.add(dto);
		}
		LeaveBalance ret[] = new LeaveBalance[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	protected void populateDtoLeave(LeaveBalance dto, ResultSet rs) throws SQLException {
		int index = 0;
		dto.setLeaveAccumalated(rs.getFloat(1));
		dto.setLeavesTaken(rs.getFloat(2));
		
	}

	private SalaryReconciliation[] setStatusString(SalaryReconciliation[] reconciliations) {
		for (SalaryReconciliation reconciliation : reconciliations){
			if (reconciliation.getStatus() == Status.getStatusId(Status.ACCEPTED)){
				try{
					SalaryReconciliationReqDao reconciliationReqDao = SalaryReconciliationReqDaoFactory.create();
					SalaryReconciliationReq reconciliationReqArr[] = reconciliationReqDao.findByDynamicWhere(" SR_ID=? AND LEVEL=(SELECT LEVEL FROM SALARY_RECONCILIATION_REQ WHERE ID=(SELECT MAX(ID) FROM SALARY_RECONCILIATION_REQ WHERE SR_ID = ?)) ORDER BY ID DESC", new Object[] { reconciliation.getId(), reconciliation.getId() });
					ArrayList<String> status = new ArrayList<String>();
					boolean isApproved = false;
					for (SalaryReconciliationReq req : reconciliationReqArr){
						if (req.getActionBy() > 0) isApproved = true;
						status.add(getColoredStatus(ModelUtiility.getInstance().getEmployeeName(req.getAssignedTo()), req.getActionBy()));
					}
					if (isApproved && status.size() > 0) reconciliation.setStatusName(Status.APPROVED + "( " + ModelUtiility.getCommaSeparetedValues(status) + " )");
				} catch (Exception e){
					logger.error("", e);
				}
			}
		}
		return reconciliations;
	}

	private String getColoredStatus(String employeeName, int actionBy) {
		if (actionBy == 0) return "<font color=\"#FF0000\">" + employeeName + "</font>";
		return "<font color=\"#006600\">" + employeeName + "</font>";
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		SalaryReconciliationBean formBean = (SalaryReconciliationBean) form;
		Connection conn = null;
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			return result;
		}
		if (formBean.getId() <= 0){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.salary.reconcilitation.update.no.srId"));
			return result;
		}
		switch (SaveTypes.getValue(form.getsType())) {
			case SAVE:
				updateReports(formBean, result, login);
				break;
			case HOLD:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					SalaryReconciliationReportDao dao = SalaryReconciliationReportDaoFactory.create(conn);
					SalaryRecReportHistoryDao historyDao = SalaryRecReportHistoryDaoFactory.create(conn);
					SalaryReconciliationHoldDao holdDao = SalaryReconciliationHoldDaoFactory.create(conn);
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					SalaryReconciliationReport report = dao.findByPrimaryKey(formBean.getId());
					/*if (formBean.getTerm() == null){
						SalaryReconciliation reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(report.getSrId());
						formBean.setTerm(getTerm(reconciliation.getMonth(), reconciliation.getYear()));
					}*/
					if (report.getModifiedBy() == null || !report.getModifiedBy().equalsIgnoreCase(empName)) historyDao.insert(report.toHistory());
					report.setComments(formBean.getComments());
					report.setModifiedBy(empName);
					report.setModifiedOn(new Date());
					report.setStatus(HOLD);
					dao.update(report.createPk(), report);
					holdDao.insert(new SalaryReconciliationHold(report.getId(), login.getUserId(), HOLD, report.getComments()));
					conn.commit();
				} catch (Exception e){
					logger.error("", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.hold.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case RELEASE:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					SalaryReconciliationReportDao dao = SalaryReconciliationReportDaoFactory.create(conn);
					SalaryRecReportHistoryDao historyDao = SalaryRecReportHistoryDaoFactory.create(conn);
					SalaryReconciliationHoldDao holdDao = SalaryReconciliationHoldDaoFactory.create(conn);
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId(), conn);
					String approverName = ModelUtiility.getInstance().getEmployeeName(login.getUserId(), conn);
					String empName = approverName + "(" + empId + ")";
					SalaryReconciliationReport report = dao.findByPrimaryKey(formBean.getId());
					SalaryReconciliation recon = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(report.getSrId());
					if (report.getModifiedBy() == null || !report.getModifiedBy().equalsIgnoreCase(empName)) historyDao.insert(report.toHistory());
					report.setComments(formBean.getComments());
					report.setModifiedBy(empName);
					report.setModifiedOn(new Date());
					boolean sts = (recon.getStatus() == Status.getStatusId(Status.APPROVED) || recon.getStatus() == Status.getStatusId(Status.COMPLETED));
					if (sts) report.setStatus(formBean.getStatus() == null ? RELEASED : REJECTED);
					else report.setStatus(formBean.getStatus() == null ? MODIFIED : REJECTED);
					dao.update(report.createPk(), report);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE SUBJECT LIKE ? AND CATEGORY='SALARY_RECON' AND ESR_MAP_ID=? ", new Object[] { "%(" + ModelUtiility.getInstance().getEmployeeId(report.getUserId(), conn) + ")", recon.getEsrMapId() });
					if (sts){
						holdDao.insert(new SalaryReconciliationHold(report.getId(), login.getUserId(), report.getStatus(), report.getComments()));
						releaseMail(recon, report, approverName, login.getUserId(), conn);
					} else{
						JDBCUtiility.getInstance().update("DELETE FROM SALARY_RECONCILIATION_HOLD WHERE REP_ID=?", new Object[] { report.getId() }, conn);
					}
					conn.commit();
				} catch (Exception e){
					logger.error("", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.release.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case BANKACCOUNTS:{
				try{
					conn = ResourceManager.getConnection();
					SalaryReconciliationReportDao dao = SalaryReconciliationReportDaoFactory.create(conn);
					SalaryReconciliationReport report = dao.findByPrimaryKey(formBean.getId());
					if (formBean.getStatus() != null){
						report.setAccountType(Short.parseShort(formBean.getStatus()));
						dao.update(report.createPk(), report);
					}
				} catch (Exception e){
					logger.error("", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
				} finally{
					ResourceManager.close(conn);
				}
			}
		}
		return result;
	}

 // Updating Salary in advance table once Salary Reconciliation is finished
	@Override
	/*public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		request.setAttribute("actionForm", "");
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			return result;
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			SalaryReconciliationBean formBean = (SalaryReconciliationBean) form;
			SalaryReconciliationReqDao reconciliationReqDao = SalaryReconciliationReqDaoFactory.create(conn);
			SalaryReconciliationReq reconciliationReqArr[] = reconciliationReqDao.findByDynamicWhere(" SR_ID=? AND ASSIGNED_TO=? AND LEVEL=(SELECT LEVEL FROM SALARY_RECONCILIATION_REQ WHERE ID=(SELECT MAX(ID) FROM SALARY_RECONCILIATION_REQ WHERE SR_ID = ?)) ORDER BY ID DESC", new Object[] { formBean.getId(), login.getUserId(), formBean.getId() });
			SalaryReconciliationDao reconciliationDao = SalaryReconciliationDaoFactory.create(conn);
			SalaryReconciliation reconciliation = reconciliationDao.findByPrimaryKey(formBean.getId());
			if (reconciliationReqArr == null || reconciliationReqArr.length <= 0 || reconciliationReqArr[0] == null || reconciliation == null){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
				return result;
			}
			SalaryReconciliationReq reconciliationReq = reconciliationReqArr[0];
			switch (ActionMethods.UpdateTypes.getValue(form.getuType())) {
				case UPDATE:
					//updateReports(formBean, result, login);
					break;
				case ACCEPTED:
					///updateReports(formBean, result, login);
					reconciliationReq.setComments(formBean.getComments());
					reconciliationReq.setActionBy(login.getUserId());
					reconciliationReq.setActionOn(new Date());
					reconciliationReqDao.update(reconciliationReq.createPk(), reconciliationReq);
					ProcessChain pchain = getProcessChain(conn);
					Integer[] notifiers = new ProcessEvaluator().notifiers(pchain.getNotification(), 1);
					if (reconciliationReq.getEscalatedFrom() != null && reconciliationReq.getEscalatedFrom().length() > 0 && !reconciliationReq.getEscalatedFrom().equals("0")){
						JDBCUtiility.getInstance().update("UPDATE SALARY_RECONCILIATION_REQ SET ACTION_BY=?, ACTION_ON=NOW() WHERE  ESCALATED_FROM IN ( " + reconciliationReq.getEscalatedFrom() + " )", new Object[] { login.getUserId() }, conn);
					}
					if (reconciliation.getStatus() == Status.getStatusId(Status.GENERATED)){
						reconciliation.setStatus(Status.getStatusId(Status.SUBMITTED));
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.REJECTED)){
						reconciliation.setStatus(Status.getStatusId(Status.RESUBMITTED));
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.ACCEPTED)){
						if (isCompleteApprove && JDBCUtiility.getInstance().getRowCount("FROM SALARY_RECONCILIATION_REQ WHERE LEVEL=? AND SR_ID=? AND ACTION_BY=0", new Object[] { reconciliationReq.getLevel(), reconciliationReq.getSrId() }, conn) > 0) break;
						reconciliation.setStatus(Status.getStatusId(Status.APPROVED));
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.APPROVED)){
						reconciliation.setStatus(Status.getStatusId(Status.COMPLETED));
						reconciliation.setCompletedOn(new Date());
						reconciliationDao.update(reconciliation.createPk(), reconciliation);
						List<Object> ccids = JDBCUtiility.getInstance().getSingleColumn("SELECT ASSIGNED_TO FROM SALARY_RECONCILIATION_REQ WHERE SR_ID=?", new Object[] { reconciliation.getId() }, conn);
					// updating salary in advance table	
						
						FinanceInfoDaoFactory.create(conn).updateSalaryInAdvance(reconciliation);
						Set<Integer> ccidsSet = new HashSet<Integer>();
						if (ccids != null && ccids.size() > 0){
							for (Object ids : ccids)
								ccidsSet.add((Integer) ids);
							for (Integer ids : notifiers)
								ccidsSet.add((Integer) ids);
						}
						if (ccidsSet.size() > 0){
							String mailSubject = "Final Salary Reconciliation Report for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + "ready for disbursal";
							String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The final Salary Reconciliation Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " is ready for disbursal. ";
							sendMail(mailSubject, messageBody, ccidsSet.toArray(new Integer[ccidsSet.size()]), null, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
						}
						break;
					}
					Integer[] approvers = new ProcessEvaluator().approvers(pchain.getApprovalChain(), reconciliationReq.getLevel() + 1, 1);
					if (approvers != null && approvers.length > 0){
						for (int approver : approvers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), approver, reconciliationReq.getLevel() + 1, "", new Date(), "0"));
						}
						String mailSubject = "Salary Reconciliation Report submitted for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear();
						String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been submitted and is awaiting your action. Please do the needful.";
						sendMail(mailSubject, messageBody, approvers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.SUBMITTED) || reconciliation.getStatus() == Status.getStatusId(Status.RESUBMITTED)){
						reconciliation.setStatus(Status.getStatusId(Status.ACCEPTED));
						
						
						Integer[] handlers = new ProcessEvaluator().handlers(pchain.getHandler(), 1);
						for (int handler : handlers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), handler, reconciliationReq.getLevel() + 1, "", new Date(), "0"));
						}
						String mailSubject = "Salary Reconciliation Report for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " Accepted by Finance";
						String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been Accepted by Finance and is awaiting your action. Please do the needful.";
						sendMail(mailSubject, messageBody, handlers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.APPROVED)){
						approvers = new ProcessEvaluator().approvers(pchain.getApprovalChain(), reconciliationReq.getLevel() - 1, 1);
						Integer[] handlers = new ProcessEvaluator().handlers(pchain.getHandler(), 1);
						StringBuffer handlersNames = new StringBuffer();
						boolean flag = true;
						for (int id : handlers){
							handlersNames.append(ModelUtiility.getInstance().getEmployeeName(id));
							if (flag) handlersNames.append(" & ");
							flag = false;
						}
						for (int approver : approvers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), approver, reconciliationReq.getLevel() + 1, "", new Date(), "0"));
						}
						String mailSubject = "Salary Reconciliation Report for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " Approved by " + handlersNames.toString();
						String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The  Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been approved by " + handlersNames.toString() + " and is awaiting your action. Please do the needful.";
						sendMail(mailSubject, messageBody, approvers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					}
					reconciliationDao.update(reconciliation.createPk(), reconciliation);
					break;
				case REJECTED:
					reconciliationReq.setActionBy(login.getUserId());
					reconciliationReq.setActionOn(new Date());
					reconciliationReq.setComments(formBean.getComments());
					reconciliationReqDao.update(reconciliationReq.createPk(), reconciliationReq);
					if (reconciliationReq.getEscalatedFrom() != null && reconciliationReq.getEscalatedFrom().length() > 0 && !reconciliationReq.getEscalatedFrom().equals("0")){
						JDBCUtiility.getInstance().update("UPDATE SALARY_RECONCILIATION_REQ SET ACTION_BY=?, ACTION_ON=NOW() WHERE  ESCALATED_FROM IN ( " + reconciliationReq.getEscalatedFrom() + " )", new Object[] { login.getUserId() }, conn);
					}
					reconciliation.setStatus(Status.getStatusId(Status.REJECTED));
					reconciliationDao.update(reconciliation.createPk(), reconciliation);
					ProcessChain prchain = getProcallReceipientcCMailId.add("akash.anand@dikshatech.com");essChain(conn);
					Integer[] reapprovers = new ProcessEvaluator().approvers(prchain.getApprovalChain(), 1, 1);
					if (reapprovers != null && reapprovers.length > 0){
						for (int approver : reapprovers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), approver, 1, "", new Date(), "0"));
						}
					}
					Integer[] renotifiers = new ProcessEvaluator().notifiers(prchain.getNotification(), 1);
					String mailSubject = "Salary Reconciliation Report rejected for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear();
					String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been rejected and is awaiting your action. Please do the needful.";
					sendMail(mailSubject, messageBody, reapprovers, renotifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					break;
			}
			conn.commit();
		} catch (Exception ex){
			logger.error("RECONCILATION UPDATE : failed to update data", ex);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
			return result;
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}*/
	
	
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		request.setAttribute("actionForm", "");
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			return result;
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			SalaryReconciliationBean formBean = (SalaryReconciliationBean) form;
			SalaryReconciliationReqDao reconciliationReqDao = SalaryReconciliationReqDaoFactory.create(conn);
			SalaryReconciliationReq reconciliationReqArr[] = reconciliationReqDao.findByDynamicWhere(" SR_ID=? AND ASSIGNED_TO=? AND LEVEL=(SELECT LEVEL FROM SALARY_RECONCILIATION_REQ WHERE ID=(SELECT MAX(ID) FROM SALARY_RECONCILIATION_REQ WHERE SR_ID = ?)) ORDER BY ID DESC", new Object[] { formBean.getId(), login.getUserId(), formBean.getId() });
			SalaryReconciliationDao reconciliationDao = SalaryReconciliationDaoFactory.create(conn);
			SalaryReconciliation reconciliation = reconciliationDao.findByPrimaryKey(formBean.getId());
			if (reconciliationReqArr == null || reconciliationReqArr.length <= 0 || reconciliationReqArr[0] == null || reconciliation == null){
				
				SalaryReconciliationReportDao salaryReconciliationReportDao = SalaryReconciliationReportDaoFactory.create();
				SalaryReconciliationReport salaryReconciliationReport = new SalaryReconciliationReport();
				salaryReconciliationReport.setReason(formBean.getReason());
				salaryReconciliationReport.setDate(formBean.getDate());
				salaryReconciliationReport.setSrId(formBean.getSrId());
				salaryReconciliationReport.setUserId(formBean.getUserId());
				salaryReconciliationReportDao.reasonForNonPay(salaryReconciliationReport.getReason(),salaryReconciliationReport.getDate(),formBean.getSrId(),salaryReconciliationReport.getUserId());
				return result;
			}
			SalaryReconciliationReq reconciliationReq = reconciliationReqArr[0];
			switch (ActionMethods.UpdateTypes.getValue(form.getuType())) {
				case UPDATE:
					//updateReports(formBean, result, login);
					break;
				case ACCEPTED:
					///updateReports(formBean, result, login);
					reconciliationReq.setComments(formBean.getComments());
					reconciliationReq.setActionBy(login.getUserId());
					reconciliationReq.setActionOn(new Date());
					reconciliationReqDao.update(reconciliationReq.createPk(), reconciliationReq);
					ProcessChain pchain = getProcessChain(conn);
					Integer[] notifiers = new ProcessEvaluator().notifiers(pchain.getNotification(), 1);
					if (reconciliationReq.getEscalatedFrom() != null && reconciliationReq.getEscalatedFrom().length() > 0 && !reconciliationReq.getEscalatedFrom().equals("0")){
						JDBCUtiility.getInstance().update("UPDATE SALARY_RECONCILIATION_REQ SET ACTION_BY=?, ACTION_ON=NOW() WHERE  ESCALATED_FROM IN ( " + reconciliationReq.getEscalatedFrom() + " )", new Object[] { login.getUserId() }, conn);
					}
					if (reconciliation.getStatus() == Status.getStatusId(Status.GENERATED)){
						reconciliation.setStatus(Status.getStatusId(Status.SUBMITTED));
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.REJECTED)){
						reconciliation.setStatus(Status.getStatusId(Status.RESUBMITTED));
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.ACCEPTED)){
						if (isCompleteApprove && JDBCUtiility.getInstance().getRowCount("FROM SALARY_RECONCILIATION_REQ WHERE LEVEL=? AND SR_ID=? AND ACTION_BY=0", new Object[] { reconciliationReq.getLevel(), reconciliationReq.getSrId() }, conn) > 0) break;
						reconciliation.setStatus(Status.getStatusId(Status.APPROVED));
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.APPROVED)){
						reconciliation.setStatus(Status.getStatusId(Status.COMPLETED));
						reconciliation.setCompletedOn(new Date());
						reconciliationDao.update(reconciliation.createPk(), reconciliation);
						List<Object> ccids = JDBCUtiility.getInstance().getSingleColumn("SELECT ASSIGNED_TO FROM SALARY_RECONCILIATION_REQ WHERE SR_ID=?", new Object[] { reconciliation.getId() }, conn);
					// updating salary in advance table	
						
						FinanceInfoDaoFactory.create(conn).updateSalaryInAdvance(reconciliation);
						Set<Integer> ccidsSet = new HashSet<Integer>();
						if (ccids != null && ccids.size() > 0){
							for (Object ids : ccids)
								ccidsSet.add((Integer) ids);
							for (Integer ids : notifiers)
								ccidsSet.add((Integer) ids);
						}
						if (ccidsSet.size() > 0){
							String mailSubject = "Final Salary Reconciliation Report for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + "ready for disbursal";
							String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The final Salary Reconciliation Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " is ready for disbursal. ";
							sendMail(mailSubject, messageBody, ccidsSet.toArray(new Integer[ccidsSet.size()]), null, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
						}
						sendMail(formBean);
						break;
					}
					Integer[] approvers = new ProcessEvaluator().approvers(pchain.getApprovalChain(), reconciliationReq.getLevel() + 1, 1);
					if (approvers != null && approvers.length > 0){
						for (int approver : approvers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), approver, reconciliationReq.getLevel() + 1, "", new Date(), "0"));
						}
						String mailSubject = "Salary Reconciliation Report submitted for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear();
						String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been submitted and is awaiting your action. Please do the needful.";
						sendMail(mailSubject, messageBody, approvers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.SUBMITTED) || reconciliation.getStatus() == Status.getStatusId(Status.RESUBMITTED)){
						reconciliation.setStatus(Status.getStatusId(Status.ACCEPTED));
						
						
						Integer[] handlers = new ProcessEvaluator().handlers(pchain.getHandler(), 1);
						for (int handler : handlers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), handler, reconciliationReq.getLevel() + 1, "", new Date(), "0"));
						}
						String mailSubject = "Salary Reconciliation Report for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " Accepted by Finance";
						String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been Accepted by Finance and is awaiting your action. Please do the needful.";
						sendMail(mailSubject, messageBody, handlers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					} else if (reconciliation.getStatus() == Status.getStatusId(Status.APPROVED)){
						approvers = new ProcessEvaluator().approvers(pchain.getApprovalChain(), reconciliationReq.getLevel() - 1, 1);
						Integer[] handlers = new ProcessEvaluator().handlers(pchain.getHandler(), 1);
						StringBuffer handlersNames = new StringBuffer();
						boolean flag = true;
						for (int id : handlers){
							handlersNames.append(ModelUtiility.getInstance().getEmployeeName(id));
							if (flag) handlersNames.append(" & ");
							flag = false;
						}
						for (int approver : approvers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), approver, reconciliationReq.getLevel() + 1, "", new Date(), "0"));
						}
						String mailSubject = "Salary Reconciliation Report for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " Approved by " + handlersNames.toString();
						String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The  Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been approved by " + handlersNames.toString() + " and is awaiting your action. Please do the needful.";
						sendMail(mailSubject, messageBody, approvers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					}
					reconciliationDao.update(reconciliation.createPk(), reconciliation);
					break;
				case REJECTED:
					reconciliationReq.setActionBy(login.getUserId());
					reconciliationReq.setActionOn(new Date());
					reconciliationReq.setComments(formBean.getComments());
					reconciliationReqDao.update(reconciliationReq.createPk(), reconciliationReq);
					if (reconciliationReq.getEscalatedFrom() != null && reconciliationReq.getEscalatedFrom().length() > 0 && !reconciliationReq.getEscalatedFrom().equals("0")){
						JDBCUtiility.getInstance().update("UPDATE SALARY_RECONCILIATION_REQ SET ACTION_BY=?, ACTION_ON=NOW() WHERE  ESCALATED_FROM IN ( " + reconciliationReq.getEscalatedFrom() + " )", new Object[] { login.getUserId() }, conn);
					}
					reconciliation.setStatus(Status.getStatusId(Status.REJECTED));
					reconciliationDao.update(reconciliation.createPk(), reconciliation);
					ProcessChain prchain = getProcessChain(conn);
					Integer[] reapprovers = new ProcessEvaluator().approvers(prchain.getApprovalChain(), 1, 1);
					if (reapprovers != null && reapprovers.length > 0){
						for (int approver : reapprovers){
							reconciliationReqDao.insert(new SalaryReconciliationReq(reconciliation.getId(), approver, 1, "", new Date(), "0"));
						}
					}
					Integer[] renotifiers = new ProcessEvaluator().notifiers(prchain.getNotification(), 1);
					String mailSubject = "Salary Reconciliation Report rejected for " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear();
					String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been rejected and is awaiting your action. Please do the needful.";
					sendMail(mailSubject, messageBody, reapprovers, renotifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
					break;
					
					
				/*case  NONPAYMENT:
					
					SalaryReconciliationReportDao salaryReconciliationReportDao = SalaryReconciliationReportDaoFactory.create();
					SalaryReconciliationReport salaryReconciliationReport = new SalaryReconciliationReport();
					salaryReconciliationReport.setReason(formBean.getReason());
					salaryReconciliationReport.setDate(formBean.getDate());
					salaryReconciliationReport.setSrId(formBean.getSrId());
					salaryReconciliationReportDao.reasonForNonPay(salaryReconciliationReport.getReason(),salaryReconciliationReport.getDate(),formBean.getSrId());*/
	
					
					
			}
			conn.commit();
		} catch (Exception ex){
			logger.error("RECONCILATION UPDATE : failed to update data", ex);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
			return result;
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}

	private void updateReports(SalaryReconciliationBean formBean, ActionResult result, Login login) {
		if (formBean.getSrId() <= 0){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.salary.reconcilitation.update.no.id"));
			return;
		}
		if (formBean.getDeductions() == null || formBean.getAdditions() == null || formBean.getDeductions().length == 0 || formBean.getAdditions().length == 0){
			if(formBean.getDeductions()!=null && formBean.getDeductions().length >0){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.salary.reconcilitation.update.no.components"));
			return;
		}}
		if (formBean.getSalary() == null || formBean.getPayableDays() == null || formBean.getComments() == null){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.salary.reconcilitation.update.no.details"));
			return;
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			SalaryReconciliationReportDao dao = SalaryReconciliationReportDaoFactory.create(conn);
			SalaryRecReportHistoryDao historyDao = SalaryRecReportHistoryDaoFactory.create(conn);
			MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create(conn);
			int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
			String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
			SalaryReconciliationReport[] report = dao.findByPrimaryKeyTds(formBean.getId());
			for (SalaryReconciliationReport salaryReconciliationReport : report) {
				
			
			if (formBean.getSrId() != salaryReconciliationReport.getSrId()){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.salary.reconcilitation.update.no.match.srId"));
				return;
			}
			if (formBean.getTerm() == null){
				
				SalaryReconciliation reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(salaryReconciliationReport.getSrId());
				formBean.setTerm(getTerm(reconciliation.getMonth(), reconciliation.getYear()));
			}
			String no_of_modifications = salaryReconciliationReport.getNo_of_modifications();
			if (salaryReconciliationReport.getModifiedBy() == null || !salaryReconciliationReport.getModifiedBy().equalsIgnoreCase(empName)) historyDao.insert(salaryReconciliationReport.toHistory());
			salaryReconciliationReport.setComments(formBean.getComments());
			salaryReconciliationReport.setModifiedBy(empName);
			salaryReconciliationReport.setModifiedOn(new Date());
			salaryReconciliationReport.setSalary(formBean.getSalary());
			salaryReconciliationReport.setPayableDays(Double.parseDouble(formBean.getPayableDays()));
			salaryReconciliationReport.setSalaryCycle(formBean.getSalaryCycle());
			salaryReconciliationReport.setNo_of_modifications(Integer.toString(Integer.parseInt(no_of_modifications)+1));
			salaryReconciliationReport.setReason(null);
			salaryReconciliationReport.setDate(null);
		

			dao.update(salaryReconciliationReport.createPk(), salaryReconciliationReport);
			Map<String, MonthlyPayroll> map = getPayroll(salaryReconciliationReport.getUserId(), formBean.getTerm(), monthlyPayrollDao);
			for (String data : formBean.getAdditions()){
				updatePayRoll(data, 1, salaryReconciliationReport.getUserId(), formBean.getTerm(), map, monthlyPayrollDao);
			}
			if(formBean.getDeductions()!=null && formBean.getDeductions().length>0){
			for (String data : formBean.getDeductions()){
				updatePayRoll(data, 2, salaryReconciliationReport.getUserId(), formBean.getTerm(), map, monthlyPayrollDao);
			}}
			
			logger.info("deleting PayRoll details for userId:" + salaryReconciliationReport.getUserId() + " for term:" + formBean.getTerm() + " list:" + map);
			for (Map.Entry<String, MonthlyPayroll> entry : map.entrySet()){
				monthlyPayrollDao.delete(entry.getValue().createPk());
			}
			}
			conn.commit();
		} catch (Exception e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
			logger.error("Unable to update salary reconciliation details for :" + formBean, e);
		} finally{
			ResourceManager.close(conn);
		}
	}

	public void updatePayRoll(String data, int type, int userId, String term, Map<String, MonthlyPayroll> map, MonthlyPayrollDao monthlyPayrollDao) throws Exception {
		try{
			String[] d = data.split("~=~");// 1~=~comment~=~3000
			int id = Integer.parseInt(d[0]);
			if (id == 0){
				monthlyPayrollDao.insert(new MonthlyPayroll(userId, d[1], DesEncrypterDecrypter.getInstance().encrypt(d[2]), term, type));
			} else{
				MonthlyPayroll payroll = map.get(d[0]);
				map.remove(d[0]);
				payroll.setAmount(DesEncrypterDecrypter.getInstance().encrypt(d[2]));
				payroll.setComponent(d[1]);
				monthlyPayrollDao.update(payroll.createPk(), payroll);
			}
		} catch (Exception e){
			logger.error("Unable to update salary reconciliation details for :" + data, e);
			throw new Exception();
		}
	}

	public Map<String, MonthlyPayroll> getPayroll(int userId, String term, MonthlyPayrollDao monthlyPayrollDao) {
		Map<String, MonthlyPayroll> map = new HashMap<String, MonthlyPayroll>();
		try{
			MonthlyPayroll[] monthlyPayrolls = monthlyPayrollDao.findByDynamicWhere(" USERID = ? AND MONTHID = ? ", new Object[] { userId, term });
			for (MonthlyPayroll eachMp : monthlyPayrolls){
				map.put(eachMp.getId() + "", eachMp);
			}
		} catch (MonthlyPayrollDaoException e){
			logger.debug("There is a MonthyPayrollDaoException Occured for the u");
		}
		return map;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public int generateSalaryReport(int month, int year, int userId) {
		synchronized (salaryReconciliationModel){
			Connection conn = null;
			try{
				SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
				conn = ResourceManager.getConnection();
				conn.setAutoCommit(false);
				SalaryReconciliationDao reconciliationDao = SalaryReconciliationDaoFactory.create(conn);
				SalaryReconciliationReportDao reconcilationReportDao = SalaryReconciliationReportDaoFactory.create(conn);
				SalaryReconciliationReqDao reconciliationReqDao = SalaryReconciliationReqDaoFactory.create(conn);
				SalaryReconciliation reconciliation, reconciliationArr[] = reconciliationDao.findByDynamicWhere("MONTH=? AND YEAR=?", new Object[] { month, year });
				ProcessChain pchain = getProcessChain(conn);
				Integer[] approvers = new ProcessEvaluator().approvers(pchain.getApprovalChain(), 1, 1);
				Integer[] notifiers = new ProcessEvaluator().notifiers(pchain.getNotification(), 1);
				Set<Integer> toList = null;
				if (reconciliationArr != null && reconciliationArr.length == 1){
					reconciliation = reconciliationArr[0];
					reconciliation.setStatus(Status.getStatusId(Status.GENERATED));
					reconciliation.setCreatedOn(new Date());
					reconciliationDao.update(reconciliation.createPk(), reconciliation);
					SalaryReconciliationReq reqs[] = reconciliationReqDao.findWhereSrIdEquals(reconciliation.getId());
					//JDBCUtiility.getInstance().update("DELETE FROM SALARY_RECONCILIATION_REQ WHERE SR_ID=?", new Object[] { reconciliation.getId() }, conn);
					toList = new HashSet<Integer>();
					for (SalaryReconciliationReq req : reqs){
						toList.add(req.getAssignedTo());
						reconciliationReqDao.delete(req.createPk());
					}
					JDBCUtiility.getInstance().update("DELETE FROM SALARY_RECONCILIATION_REPORT WHERE SR_ID=?", new Object[] { reconciliation.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { reconciliation.getEsrMapId() }, conn);
				} else
					reconciliation = createNewReport(month, year, conn);
				String payableDaysQuery = "CASE WHEN DATE_ADD(LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), INTERVAL 1 DAY)<PF.DATE_OF_JOINING AND LAST_DAY(CURDATE())>PF.DATE_OF_SEPERATION THEN DATEDIFF(PF.DATE_OF_SEPERATION,PF.DATE_OF_JOINING)+1 WHEN DATE_ADD(LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), INTERVAL 1 DAY)<PF.DATE_OF_JOINING THEN DATEDIFF(LAST_DAY(CURDATE()),PF.DATE_OF_JOINING)+1 WHEN LAST_DAY(CURDATE())>PF.DATE_OF_SEPERATION"
						+ "  THEN DATEDIFF(PF.DATE_OF_SEPERATION,DATE_ADD(LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), INTERVAL 1 DAY))+1 ELSE DAY(LAST_DAY(CURDATE())) END AS PAYABLEDAYS";
				String query = "SELECT U.*,"
						+ payableDaysQuery
						+ " FROM USERS U LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID JOIN PROFILE_INFO PF ON PF.ID = U.PROFILE_ID WHERE U.EMP_ID !=0 AND U.ID > 3 AND D.ID IN (SELECT D1.ID FROM DIVISON D1, DIVISON D2 WHERE D1.REGION_ID = ? AND (D1.ID = D2.PARENT_ID OR D1.ID = D2.ID) GROUP BY D1.ID) AND U.STATUS NOT IN(1,3) AND ((U.STATUS=2 AND PF.DATE_OF_SEPERATION IS NOT NULL AND MONTH(PF.DATE_OF_SEPERATION)=MONTH(CURDATE()) AND YEAR(PF.DATE_OF_SEPERATION)=YEAR(CURDATE())) OR  U.STATUS!=2) ORDER BY U.EMP_ID";
				
				double payableDays = 0;
				float days = 0;
				float totalDays = 0;
				float a = 0;
				float lwpvalue = 0;
				Calendar calendar = Calendar.getInstance();
				int currentMonth = (calendar.get(Calendar.MONTH) + 1);//11
				int previousMonth = (calendar.get(Calendar.MONTH));//10
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);//2017
				String previousThirdCycle  = currentYear+"-"+previousMonth+"-"+3;
				String currentFirstCycle  = currentYear+"-"+currentMonth+"-"+1;
				String currentSecondCycle  = currentYear+"-"+currentMonth+"-"+2;
				
				Users[] users = UsersDaoFactory.create(conn).findByDynamicSelect(query, new Object[] { 1 });
				for (Users user : users) {

					days = Float.valueOf(user.getPayableDays());

					LeaveLwpDao leaveLwpDao = LeaveLwpDaoFactory.create();
					LeaveLwp[] lp = leaveLwpDao.findByDynamicSelect("SELECT * FROM LEAVE_LWP WHERE USER_ID = ? AND( MONTH_CYCLE =? OR MONTH_CYCLE = ? OR MONTH_CYCLE = ?) ", new Object[] { user.getId(),previousThirdCycle,currentFirstCycle,currentSecondCycle });
					if (lp.length <= 0||lp[0]==null) {
						totalDays = days;
					} else {

						for (LeaveLwp leaveLwp : lp) {

							lwpvalue += leaveLwp.getLwp();
							totalDays = days - lwpvalue;

						}
					}
					String active = "";
					Calendar cal = Calendar.getInstance();
					int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					String term = getTerm(month, year);
					payableDays = Double.parseDouble(String.valueOf(totalDays));
					 SalaryDetails[] pFDetail = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (1) ORDER BY SAL_ID ", new Object[] { user.getId()});		
					for (SalaryDetails salaryDetails : pFDetail) {
						
						active=salaryDetails.getActive();
					}	
					if (payableDays > 0)
						
						if (active.equals("") || active.equals(null) || active.equalsIgnoreCase("YES")) {
							reconcilationReportDao
									.insert(new SalaryReconciliationReport(reconciliation.getId(), user.getId(),
											getsalary(user.getId(), payableDays, maxDays, term, conn), payableDays));
						}
				}
				for (int approver : approvers) {
					reconciliationReqDao.insert(
							new SalaryReconciliationReq(reconciliation.getId(), approver, 1, "", new Date(), "0"));

				}
				String mailSubject = "Salary Reconciliation Report Generated for " + PortalUtility.returnMonth(month) + " " + year;
				String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been generated and is awaiting your action. Please do the needful.";
				conn.commit();
				ResourceManager.close(conn);
				conn = ResourceManager.getConnection();
				if (toList != null){
					try{
						String userName = ModelUtiility.getInstance().getEmployeeName(userId);
						String remailSubject = "Salary Reconciliation Report Re-Generated for " + PortalUtility.returnMonth(month) + " " + year + " by " + userName;
						String remessageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been re-generated by " + userName + ".";
						sendMail(remailSubject, remessageBody, toList.toArray(new Integer[toList.size()]), notifiers, reconciliation.getEsrMapId(), null, conn);
					} catch (Exception e){}
				}
				sendMail(mailSubject, messageBody, approvers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
			} catch (Exception e){
				e.printStackTrace();
				return 2;
			} finally{
				ResourceManager.close(conn);
			}
		}
		return 0;
	}

	public int generateSalaryReportauto(int month, int year, int userId) {
		if(userId==0) userId = 483;
		
		synchronized (salaryReconciliationModel){
			Connection conn = null;
			try{
				SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
				conn = ResourceManager.getConnection();
				conn.setAutoCommit(false);
				SalaryReconciliationDao reconciliationDao = SalaryReconciliationDaoFactory.create(conn);
				SalaryReconciliationReportDao reconcilationReportDao = SalaryReconciliationReportDaoFactory.create(conn);
				SalaryReconciliationReqDao reconciliationReqDao = SalaryReconciliationReqDaoFactory.create(conn);
				SalaryReconciliation reconciliation, reconciliationArr[] = reconciliationDao.findByDynamicWhere("MONTH=? AND YEAR=?", new Object[] { month, year });
				ProcessChain pchain = getProcessChain(conn);
				Integer[] approvers = new ProcessEvaluator().approvers(pchain.getApprovalChain(), 1, 1);
				Integer[] notifiers = new ProcessEvaluator().notifiers(pchain.getNotification(), 1);
				Set<Integer> toList = null;
				if (reconciliationArr != null && reconciliationArr.length == 1){
					reconciliation = reconciliationArr[0];
					reconciliation.setStatus(Status.getStatusId(Status.GENERATED));
					reconciliation.setCreatedOn(new Date());
					reconciliationDao.update(reconciliation.createPk(), reconciliation);
					SalaryReconciliationReq reqs[] = reconciliationReqDao.findWhereSrIdEquals(reconciliation.getId());
					//JDBCUtiility.getInstance().update("DELETE FROM SALARY_RECONCILIATION_REQ WHERE SR_ID=?", new Object[] { reconciliation.getId() }, conn);
					toList = new HashSet<Integer>();
					for (SalaryReconciliationReq req : reqs){
						toList.add(req.getAssignedTo());
						reconciliationReqDao.delete(req.createPk());
					}
					JDBCUtiility.getInstance().update("DELETE FROM SALARY_RECONCILIATION_REPORT WHERE SR_ID=?", new Object[] { reconciliation.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { reconciliation.getEsrMapId() }, conn);
				} else
					reconciliation = createNewReport(month, year, conn);
				String payableDaysQuery = "CASE WHEN DATE_ADD(LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), INTERVAL 1 DAY)<PF.DATE_OF_JOINING AND LAST_DAY(CURDATE())>PF.DATE_OF_SEPERATION THEN DATEDIFF(PF.DATE_OF_SEPERATION,PF.DATE_OF_JOINING)+1 WHEN DATE_ADD(LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), INTERVAL 1 DAY)<PF.DATE_OF_JOINING THEN DATEDIFF(LAST_DAY(CURDATE()),PF.DATE_OF_JOINING)+1 WHEN LAST_DAY(CURDATE())>PF.DATE_OF_SEPERATION"
						+ "  THEN DATEDIFF(PF.DATE_OF_SEPERATION,DATE_ADD(LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), INTERVAL 1 DAY))+1 ELSE DAY(LAST_DAY(CURDATE())) END AS PAYABLEDAYS";
				String query = "SELECT U.*,"
						+ payableDaysQuery
						+ " FROM USERS U LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID JOIN PROFILE_INFO PF ON PF.ID = U.PROFILE_ID WHERE U.EMP_ID !=0 AND U.ID > 3 AND D.ID IN (SELECT D1.ID FROM DIVISON D1, DIVISON D2 WHERE D1.REGION_ID = ? AND (D1.ID = D2.PARENT_ID OR D1.ID = D2.ID) GROUP BY D1.ID) AND U.STATUS NOT IN(1,3) AND ((U.STATUS=2 AND PF.DATE_OF_SEPERATION IS NOT NULL AND MONTH(PF.DATE_OF_SEPERATION)=MONTH(CURDATE()) AND YEAR(PF.DATE_OF_SEPERATION)=YEAR(CURDATE())) OR  U.STATUS!=2) ORDER BY U.EMP_ID";
				
				double payableDays = 0;
				float days = 0;
				float totalDays = 0;
				float a = 0;
				float lwpvalue = 0;
				Calendar calendar = Calendar.getInstance();
				int currentMonth = (calendar.get(Calendar.MONTH) + 1);//11
				int previousMonth = (calendar.get(Calendar.MONTH));//10
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);//2017
				String previousThirdCycle  = currentYear+"-"+previousMonth+"-"+3;
				String currentFirstCycle  = currentYear+"-"+currentMonth+"-"+1;
				String currentSecondCycle  = currentYear+"-"+currentMonth+"-"+2;

				Users[] users = UsersDaoFactory.create(conn).findByDynamicSelect(query, new Object[] {1 });
				for (Users user : users) {

					days = Float.valueOf(user.getPayableDays());
					LeaveLwpDao leaveLwpDao = LeaveLwpDaoFactory.create();
					LeaveLwp[] lp = leaveLwpDao.findByDynamicSelect("SELECT * FROM LEAVE_LWP WHERE USER_ID = ? AND( MONTH_CYCLE =? OR MONTH_CYCLE = ? OR MONTH_CYCLE = ?) ", new Object[] { user.getId(),previousThirdCycle,currentFirstCycle,currentSecondCycle });
					if (lp.length <= 0||lp[0]==null) {
						totalDays = days;
					} else {

						for (LeaveLwp leaveLwp : lp) {

							lwpvalue += leaveLwp.getLwp();
							totalDays = days - lwpvalue;

						}
					}
					Calendar cal = Calendar.getInstance();
					int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					String term = getTerm(month, year);
					String active = "";
					payableDays = Double.parseDouble(String.valueOf(totalDays));
					 SalaryDetails[] pFDetail = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (1) ORDER BY SAL_ID ", new Object[] { user.getId()});		
					for (SalaryDetails salaryDetails : pFDetail) {

						active = salaryDetails.getActive();
					}

					if (payableDays > 0)

						if (active.equals("") || active.equals(null) || active.equalsIgnoreCase("YES")) {
							reconcilationReportDao
									.insert(new SalaryReconciliationReport(reconciliation.getId(), user.getId(),
											getsalary(user.getId(), payableDays, maxDays, term, conn), payableDays));
						}

				}
				for (int approver : approvers) {
					reconciliationReqDao.insert(
							new SalaryReconciliationReq(reconciliation.getId(), approver, 1, "", new Date(), "0"));

				}
				String mailSubject = "Salary Reconciliation Report Generated for " + PortalUtility.returnMonth(month) + " " + year;
				String messageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been generated and is awaiting your action. Please do the needful.";
				conn.commit();
				ResourceManager.close(conn);
				conn = ResourceManager.getConnection();
				if (toList != null){
					try{
						String userName = ModelUtiility.getInstance().getEmployeeName(userId);
						String remailSubject = "Salary Reconciliation Report Re-Generated for " + PortalUtility.returnMonth(month) + " " + year + " by " + userName;
						String remessageBody = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The Salary Report for the period " + PortalUtility.returnMonth(reconciliation.getMonth()) + " " + reconciliation.getYear() + " has been re-generated by " + userName + ".";
						sendMail(remailSubject, remessageBody, toList.toArray(new Integer[toList.size()]), notifiers, reconciliation.getEsrMapId(), null, conn);
					} catch (Exception e){}
				}
				sendMail(mailSubject, messageBody, approvers, notifiers, reconciliation.getEsrMapId(), reconciliation.getStatusName(), conn);
				
			} catch (Exception e){
				e.printStackTrace();
				return 2;
			} finally{
				ResourceManager.close(conn);
			}
		}
		return 0;
	}

	private String sendMail(String mailSubject, String messageBody, Integer[] to, Integer[] cc, int esrMapId, String status, Connection conn) {
		PortalMail pmail = new PortalMail();
		MailGenerator generator = new MailGenerator();
		String body = null;
		try{
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			pmail.setTemplateName(MailSettings.SALARY_REPORT_NOTIFICATIONS);
			pmail.setMailSubject(mailSubject);
			pmail.setMessageBody(messageBody);
			pmail.setApproverName("");
			Set<Integer> notificationlist = new HashSet<Integer>();
			if (to != null && to.length > 0){
				if (to != null && to.length == 1){
					pmail.setApproverName(ModelUtiility.getInstance().getEmployeeName(to[0], conn));
				} else if (to != null && to.length > 1){
					StringBuffer toNames = new StringBuffer();
					for (int i = 0; i < to.length; i++){
						toNames.append(ModelUtiility.getInstance().getEmployeeName(to[i], conn));
						if (i != to.length - 1) toNames.append(" / ");
					}
					pmail.setApproverName(toNames.toString());
				}
				notificationlist.addAll(Arrays.asList(to));
				pmail.setAllReceipientMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ModelUtiility.getCommaSeparetedValues(to) + ") AND STATUS=0 AND EMP_ID>0) AND OFFICAL_EMAIL_ID IS NOT NULL"));
			}
			if (cc != null && cc.length > 0){
				HashSet<Integer> ccset = new HashSet<Integer>();
				for (int ids : cc){
					if (!notificationlist.contains(ids)) ccset.add(ids);
				}
				notificationlist.addAll(Arrays.asList(cc));
				if (ccset.size() > 0) pmail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ModelUtiility.getCommaSeparetedValues(ccset) + ") AND STATUS=0 AND EMP_ID>0 ) AND OFFICAL_EMAIL_ID IS NOT NULL"));
			}
			generator.invoker(pmail);
			body = generator.replaceFields(generator.getMailTemplate(pmail.getTemplateName()), pmail);
			if (status != null){
				InboxModel inboxModel = new InboxModel();
				for (Integer user : notificationlist){
					inboxModel.populateInbox(esrMapId, mailSubject, status, 0, user, 1, body, CATEGORY, conn);
				}
			}
		} catch (Exception e){
			logger.error("Unable to send mail sub:" + mailSubject, e);
		}
		return body;
	}
	
	private String sendMail(SalaryReconciliationBean formBean) {
		PortalMail pmail = new PortalMail();
		Connection conn = null;
		MailGenerator generator = new MailGenerator();
		String body = null;
		try{
			SalaryReconciliationReport[] reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelect("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID " + "FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID WHERE SR_ID=? AND NO_OF_MODIFICATIONS <> \"0\" ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId() });
			SalaryReconciliationReport[] totalEmployees = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelect("SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID " + "FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID WHERE SR_ID=? ORDER BY U.EMP_ID,SRR.MODIFIED_ON DESC,SRR.ID ASC", new Object[] { formBean.getId() });
			int sum_of_no_of_modification = 0;
			float count = 0;
			for (SalaryReconciliationReport salaryReconciliationReport : reconciliationsReports) {
				sum_of_no_of_modification = sum_of_no_of_modification + Integer.parseInt(salaryReconciliationReport.getNo_of_modifications());
			}
			for (SalaryReconciliationReport salaryReconciliationReport : totalEmployees) {
				count++;
			}

			float percentage = (sum_of_no_of_modification * 100)/ count;
			BigDecimal bd = new BigDecimal(Float.toString(percentage));
	        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	  //      System.out.println(bd.toString());
			String modifications = bd.toString();
	//		System.out.println(modifications);
			List<String> allReceipientcCMailId = new ArrayList<String>();
			String[] recipientMailId = new String[1];
			String[] recipientCCMailId = new String[2];
	//		recipientMailId[0] = "shivi.suhane@dikshatech.com";
//			recipientCCMailId[0] = "rmg@dikshatech.com";
	//		recipientCCMailId[1] = "ananda@dikshatech.com";
			pmail.setAllReceipientMailId(recipientMailId);
			pmail.setAllReceipientcCMailId(recipientCCMailId);
			pmail.setMailSubject("No of Modifications done on Salary Registry");
			pmail.setTemplateName(MailSettings.MODIFICATION_SALARY_REGISTRY);
			pmail.setNoOfEmployees(Float.toString(count));
			pmail.setNoOfModifications(Integer.toString(sum_of_no_of_modification));
			pmail.setPercentage(modifications);
			generator.invoker(pmail);
		} catch (Exception e){
			logger.error("Unable to send mail ");
		}
		return body;
	}

	private String getTerm(int month, int year) {
		return year + "" + (month > 9 ? month + "" : "0" + month);
	}
	private String getTermSal(int month, int year) {
		return year + "" + (month > 9 ? month + "" : "0" + month);
	}

	private String getsalary(int userId, double payableDays, int maxDays, String monthId, Connection conn) throws LeaveBalanceDaoException, SalaryDetailsDaoException, VoluntaryProvidentFundDaoException {
		float basicVpf = 0;
		float sum = 0;
		float other = 0;
		float sodex = 0;
		float basic = 0;
		float sumadd = 0;
		float finalSalary = 0;
	//	if(userId==496){
		MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create(conn);
		
		String month =findMonth(monthId);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		ProfileInfo[] profInfo = null;
		ProfileInfoDao profInfoDao=ProfileInfoDaoFactory.create(conn);
		
		try{
			MonthlyPayroll[] monthlyPayrolls = monthlyPayrollDao.findByDynamicWhere(" USERID = ? AND MONTHID = ? ", new Object[] { userId, monthId });
			MonthlyPayroll otherAllowance = null;
			for (MonthlyPayroll eachMp : monthlyPayrolls){
				for (MonthlyPayroll eachMpb : monthlyPayrolls){
				if (eachMpb.getComponent().equalsIgnoreCase("Basic")) {

					basic = (float) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(eachMpb.getAmount()));
				}
				}

				float amount = (float) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(eachMp.getAmount()));
				if (eachMp.getComponentType() == 0 || eachMp.getComponentType() == 1){
					if (payableDays < maxDays){
						if ("Meal Allowance".equalsIgnoreCase(eachMp.getComponent())){
							sodex = amount - (float) (amount / maxDays * payableDays);;
							amount -= sodex;
						} else{
							amount = (float) (amount / maxDays * payableDays);
							eachMp.setAmount(DesEncrypterDecrypter.getInstance().encrypt(amount + ""));
							if ("Other Allowance".equalsIgnoreCase(eachMp.getComponent())){
								other = amount;
								otherAllowance = eachMp;
							} 
							else monthlyPayrollDao.update(eachMp.createPk(), eachMp);
						}
					}
					sum += amount;
					sumadd = sum;
					
				}else if (eachMp.getComponentType() == 2){
					if (payableDays < maxDays){
						if ("VPF".equalsIgnoreCase(eachMp.getComponent())){
							/*float temp = amount / (float)maxDays;
							amount = (float) (temp * payableDays);*/
							float temp = basic*12/100;
							 amount = temp;
							 VoluntaryProvidentFundDao vpfDao = VoluntaryProvidentFundDaoFactory.create();
							 VoluntaryProvidentFund[] vpf = vpfDao.findByVpf("SELECT * FROM VPF_REQ where USER_ID = ? ORDER BY ID DESC LIMIT 1", new Object[]{(userId)} );
							 for (VoluntaryProvidentFund voluntaryProvidentFund : vpf) {
								if(voluntaryProvidentFund.getVpf().equalsIgnoreCase("no")||voluntaryProvidentFund.getVpf().equals("")||voluntaryProvidentFund.getVpf().equalsIgnoreCase("NO")||voluntaryProvidentFund.getVpf()==null)
								{
									amount =0;
								}
							}
							eachMp.setAmount(DesEncrypterDecrypter.getInstance().encrypt(amount + ""));
							monthlyPayrollDao.update(eachMp.createPk(), eachMp);
						}
						else if ("Provident Fund".equalsIgnoreCase(eachMp.getComponent())){
							/*float temp = amount / (float)maxDays;
							amount = (float) (temp * payableDays);*/
							float temp = basic*12/100;
							 amount = temp;
							 
							 eachMp.setAmount(DesEncrypterDecrypter.getInstance().encrypt(amount + ""));
							monthlyPayrollDao.update(eachMp.createPk(), eachMp);
						}
						else	if ("ESIC".equalsIgnoreCase(eachMp.getComponent())){
							float	temp  = (float) (sum*1.75/100);
							amount = temp;
							SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
							SalaryDetails[] grossSalaries = salaryDetailsDao.findByDynamicWhere(" USER_ID = ? AND SAL_ID IN (1,2,3,4,5) ORDER BY SAL_ID ", new Object[] { userId });
							for (SalaryDetails salaryDetails : grossSalaries) {
								if(salaryDetails.getEsic().equalsIgnoreCase("no") ||salaryDetails.getEsic().equalsIgnoreCase("NO")|| (salaryDetails.getEsic().isEmpty()))
							       {
									amount = 0;
							}
							}
							eachMp.setAmount(DesEncrypterDecrypter.getInstance().encrypt(amount + ""));
							monthlyPayrollDao.update(eachMp.createPk(), eachMp);
						
							
						}
						else	if ("Professional Tax".equalsIgnoreCase(eachMp.getComponent())){
								if(14999>sum)
								{
									amount = 0;
								
								}
								else
								{
									amount = 200;
								}
							eachMp.setAmount(DesEncrypterDecrypter.getInstance().encrypt(amount + ""));
							monthlyPayrollDao.update(eachMp.createPk(), eachMp);
						}
						
							
					}
					sum -= amount;
				}
				else if(eachMp.getComponentType() == 3){
					if(month.equalsIgnoreCase("MARCH")||month.equalsIgnoreCase("JUNE")||month.equalsIgnoreCase("SEPTEMBER")||month.equalsIgnoreCase("DECEMBER")){
						if ("Quaterely Bonus".equalsIgnoreCase(eachMp.getComponent())){
							sum+=amount;
						}
					}
					    if(month.equalsIgnoreCase("MARCH")){
						if ("Company Bonus".equalsIgnoreCase(eachMp.getComponent())){
							profInfo=profInfoDao.findByDynamicSelect("SELECT PF.* FROM PROFILE_INFO PF JOIN USERS U ON PF.ID=U.PROFILE_ID  WHERE U.ID= ?", new Object[] {userId});
							for (ProfileInfo report1 : profInfo){ 
							     String s[]=report1.getDateOfJoining().toString().split("-");
							     int joinYear=Integer.parseInt(s[0]);
							     int joinMonth=Integer.parseInt(s[1]);
							 if(year-joinYear>1)  sum+=amount;
							 else if (year-joinYear==1 && joinMonth<=3 ) sum+=amount;
                     }
						}
				}
				}
			else{
					logger.error("The record in the Monthlypayrol tab;e has component type " + eachMp.getComponentType() + " for the record " + eachMp.getId());
				}
			}
			if (otherAllowance != null){
				otherAllowance.setAmount(DesEncrypterDecrypter.getInstance().encrypt((other - sodex) + ""));
				monthlyPayrollDao.update(otherAllowance.createPk(), otherAllowance);
	
			}
		} catch (MonthlyPayrollDaoException e){
			logger.debug("There is a MonthyPayrollDaoException Occured for the u");
		}
		 catch (ProfileInfoDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//	}
		return new DecimalFormat("0").format(sum);
		
	}

	private String findMonth(String monthId){
		String month="";
	String mon=	monthId.substring(monthId.length() - 2);
	if(mon.equalsIgnoreCase("03")) month= "March";
	else if(mon.equalsIgnoreCase("06"))month= "June";
	else if(mon.equalsIgnoreCase("09")) month= "September";
	else if(mon.equalsIgnoreCase("12")) month= "December";
	
	return month;
	}
	 
	private ProcessChain getProcessChain(Connection conn) throws ProcessChainDaoException {
		// return
		// ProcessChainDaoFactory.create().findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=65  WHERE UR.USER_ID=?",
		// new Object[] { new Integer(1) })[0];
		// return ProcessChainDaoFactory.create().findByPrimaryKey(72);
		return ProcessChainDaoFactory.create(conn).findWhereProcNameEquals("IN_MSR")[0];
	}

	private SalaryReconciliation createNewReport(int month, int year, Connection conn) throws Exception {
		EmpSerReqMapDao emp_Ser_Req_Map_Dao = EmpSerReqMapDaoFactory.create(conn);
		int unQ = 1;
		EmpSerReqMap empMap[] = emp_Ser_Req_Map_Dao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=? AND REQ_ID LIKE '%-MSR-%')", new Object[] { 17 });
		if (empMap.length > 0){
			String s = empMap[0].getReqId().split("-")[2];
			unQ = Integer.parseInt(s) + 1;
		}
		ProcessChain pchain = getProcessChain(conn);
		EmpSerReqMap empReqMapDto = new EmpSerReqMap();
		empReqMapDto.setSubDate(new Date());
		empReqMapDto.setReqId("IN-MSR-" + unQ);
		empReqMapDto.setReqTypeId(17);
		empReqMapDto.setRegionId(1);
		empReqMapDto.setRequestorId(1);
		empReqMapDto.setProcessChainId(pchain.getId());
		emp_Ser_Req_Map_Dao.insert(empReqMapDto);
		SalaryReconciliation reconciliation = new SalaryReconciliation(empReqMapDto.getId(), month, year, Status.getStatusId(Status.GENERATED), new Date(), null);
		SalaryReconciliationDaoFactory.create(conn).insert(reconciliation);
		return reconciliation;
	}

	public static void main(String[] args) {
		JDBCUtiility.getInstance().update("UPDATE SALARY_RECONCILIATION_HOLD SET ACTION_ON = ADDDATE(ACTION_ON, INTERVAL -1 DAY)", null);
		new SalaryReconciliationModel().escalationforSalaryHold();
	}

	public int generateSalaryReport(int userId) {
		
		Calendar calendar = Calendar.getInstance();
		return generateSalaryReport(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), userId);
		
	}

	private SalaryReportBean[] getConfirmedSalaryDetails(int sr_id, int flag) {
		return SalaryReconciliationDaoFactory.create().getConfirmedSalaryDetails(sr_id, flag); // moved the code in SalaryReconcliationDaoImpl.java
	}

	private SalaryReportBean[] generateBankSalaryReport(int sr_id,ArrayList<Integer> ssr_id,String flag) {
	
		return SalaryReconciliationDaoFactory.create().getBankSalaryDetails(sr_id,ssr_id,flag); // moved the code in SalaryReconcliationDaoImpl.java
	}
	
	public int generateSalaryReportauto(int userId) {
		Calendar calendar = Calendar.getInstance();
		return generateSalaryReportauto(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), userId);
	}
	
	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		if (ModelUtiility.hasModulePermission((Login) form.getObject(), MODULEID)){
			SalaryReconciliationBean salaryReconciliationBean = (SalaryReconciliationBean) form;
			PortalData portalData = new PortalData();
			String path = portalData.getfolder(portalData.getDirPath()) + "temp";
			int sr_id = salaryReconciliationBean.getId();
			String flag=salaryReconciliationBean.getBankFlag();
			 
			
			
			try{
				File file = new File(path);
				if (!file.exists()) file.mkdir();
				switch (DownloadTypes.getValue(form.getdType())) {
					case BANKLETTER:
						SalaryReconciliation sr = SalaryReconciliationDaoFactory.create().findByPrimaryKey(sr_id);
						SalaryReportBean[] salaryBeanArray = getConfirmedSalaryDetails(sr_id, 1);
						List<SalaryReportBean> list = new ArrayList<SalaryReportBean>();
						for (SalaryReportBean bean : salaryBeanArray)
							list.add(bean);
						String fileName = "Salary_Disbursement_Letter " + PortalUtility.getMonthFullName(sr.getMonth()) + "_" + sr.getYear() + ".pdf";
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(PropertyLoader.getEnvVariable() + File.separator + "jasper" + File.separator + "/salarybankletter.jasper");
						Map params = new HashMap();
						params.put(JRParameter.REPORT_LOCALE, Locale.US);
						params.put("MONTH", PortalUtility.getMonthFullName(sr.getMonth()) + " - " + sr.getYear());
						JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(list));
						JasperExportManager.exportReportToPdfFile(jasperPrint, path + File.separator + fileName);
						attachements.setFileName(fileName);
						/*
						try{
							path += seprator + "temp";
							File file1 = new File(path);
							if (!file1.exists()) file1.mkdir();
							attachements.setFileName(new GenerateXls().generateSalaryDisbLetter(getConfirmedSalaryDetails(sr_id, 1), path, sr_id));
						} catch (Exception e){
							logger.error("unable to generate Salary Letter report xls" + e.getMessage());
						}*/
						break;
					case ICICIREPORT:
						try{
							attachements.setFileName(new GenerateXls().generateSalaryICICIReport(getConfirmedSalaryDetails(sr_id, 1), path, sr_id));
						} catch (Exception e){
							logger.error("unable to generate Salary Disbursement Letter report xls" + e.getMessage());
						}
						break;
					case OTHERREPORT:
						try{
							attachements.setFileName(new GenerateXls().generateSalaryOtherReport(getConfirmedSalaryDetails(sr_id, 2), path, sr_id));
						} catch (Exception e){
							logger.error("unable to generate Salary Disbursement Letter report xls" + e.getMessage());
						}
						break;
					default:
						try{
							if(salaryReconciliationBean.getBankFlag().equals("BANK_FLAG")){
								attachements.setFileName(new GenerateXls().generateSalaryReport(getConfirmedSalaryDetails(sr_id, 3), path, sr_id));
							}else{
								
							if(flag.equals("HDFC_BANK")){
								
								double total = 0;
								double  valueRounded1 = 0;
								 List<SalaryReconciliationReport> listHDFC = new ArrayList<SalaryReconciliationReport>();
								
									String amountDHDFC=salaryReconciliationBean.getTotalAmount();
								    String[] strAmtHDFC=amountDHDFC.split(",");
									
								    ArrayList<Integer> amountTD=new ArrayList<Integer>();
								    for(String w:strAmtHDFC){  
								    	
								    	total+=Float.valueOf(w);
								    	  valueRounded1 = Math.round(total * 100D) / 100D;
								    }
								    
									MailGenerator mailGenerator = new MailGenerator();
									PortalMail pMail = new PortalMail();
									List<String> allReceipientcCMailId = new ArrayList<String>();
									Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
									String regAbbrivation =  "IN";
									String mailId;
									mailId = pro.getProperty("mailId." + regAbbrivation + ".CEO");
									allReceipientcCMailId.add(mailId);
									pMail.setMailSubject("Amount Payable");
									pMail.setTotalAmount(String.valueOf(valueRounded1));
									pMail.setTemplateName(MailSettings.AMOUNT_DEDUCTED);
									pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
									mailGenerator.invoker(pMail);
								
								
				
								
								String ssrId=salaryReconciliationBean.getSrrId();
							    String[] str=ssrId.split(",");
								
							    ArrayList<Integer> ssr_id=new ArrayList<Integer>();
							    for(String w:str){  
							    	Integer x = Integer.valueOf(w);
							    	ssr_id.add(x);
							    	} 
								attachements.setFileName(new GenerateXls().generateBankSalaryReportHdfc(generateBankSalaryReport(sr_id,ssr_id,flag), path, sr_id,flag));

							}else{
								double total = 0;
								double  valueRounded1 = 0;
								 List<SalaryReconciliationReport> listNHDFC = new ArrayList<SalaryReconciliationReport>();
								
									String amountNHDFC =salaryReconciliationBean.getTotalAmount();
								    String[] strNHDFC=amountNHDFC.split(",");
									
								    ArrayList<Integer> ssr_idHDFC=new ArrayList<Integer>();
								    for(String w:strNHDFC){  
								    	
								    	total+=Float.valueOf(w);
								    	valueRounded1 = Math.round(total * 100D) / 100D;
								    }
								    
									MailGenerator mailGenerator = new MailGenerator();
									PortalMail pMail = new PortalMail();
									List<String> allReceipientcCMailId = new ArrayList<String>();
									Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
									String regAbbrivation =  "IN";
									String mailId;
									mailId = pro.getProperty("mailId." + regAbbrivation + ".CEO");
									allReceipientcCMailId.add(mailId);
									pMail.setMailSubject("Amount Payable");
									pMail.setTotalAmount(String.valueOf(valueRounded1));
									pMail.setTemplateName(MailSettings.AMOUNT_DEDUCTED);
									pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
									mailGenerator.invoker(pMail);
									String ssrId=salaryReconciliationBean.getSrrId();
									String[] str=ssrId.split(",");
									ArrayList<Integer> ssr_id=new ArrayList<Integer>();
									for(String w:str){  
							    	Integer x = Integer.valueOf(w);
							    	ssr_id.add(x);
							    	} 
							   // System.out.println("calling excel sheet=="+generateBankSalaryReport(sr_id,ssr_id,flag));
							    //System.out.println("calling excel sheet 2=="+new GenerateXls().generateBankSalaryReportNonHdfc(generateBankSalaryReport(sr_id,ssr_id,flag), path, sr_id,flag));
								attachements.setFileName(new GenerateXls().generateBankSalaryReportNonHdfc(generateBankSalaryReport(sr_id,ssr_id,flag), path, sr_id,flag));

							}
								
							}
							
						} catch (Exception e){
							logger.error("unable to generate Salary Disbursement Letter report xls" + e.getMessage());
						}
						break;
				}
				path = path + File.separator + attachements.getFileName();
				attachements.setFilePath(path);
			} catch (Exception e){
				e.printStackTrace();
			}
		} else{
			logger.info(ModelUtiility.getInstance().getEmployeeName(((Login) form.getObject()).getUserId()) + " trying to download salary reconciliation reports without having permisson at :" + new Date());
		}
		return attachements;
	}

	private void releaseMail(SalaryReconciliation recon, SalaryReconciliationReport report, String approverName, int userId, Connection conn) {
		try{
			PortalMail mail = new PortalMail();
			
			mail.setTemplateName(MailSettings.SALARY_REPORT_NOTIFICATIONS);
			String empName = ModelUtiility.getInstance().getEmployeeName(report.getUserId(), conn);
			mail.setMailSubject("Diksha Lynx: Salary release of " + empName);
			String messageBody = "The Salary of " + empName + " for the month " + PortalUtility.returnMonth(recon.getMonth()) + " " + recon.getYear() + " is " + (report.getStatus() == RELEASED ? "released" : "rejected") + " by " + approverName + ".<br/><br/> Comments : " + report.getComments();
			ProcessChain processChainDto = getProcessChain(conn);
			ProcessEvaluator pEval = new ProcessEvaluator();
			// get the senior RMG from leavel last but one
			Integer[] srRmg = pEval.approvers(processChainDto.getApprovalChain(), processChainDto.getApprovalChain().split(";").length - 1, 1);
			// get all the users involved in this hold process, means escalations
			List<Object> escUsers = JDBCUtiility.getInstance().getSingleColumn("SELECT USER_ID FROM SALARY_RECONCILIATION_HOLD WHERE REP_ID=?", new Object[] { report.getId() }, conn);
			// get the finance users as last level.
			Integer[] finance = pEval.approvers(processChainDto.getApprovalChain(), processChainDto.getApprovalChain().split(";").length, 1);
			Set<String> ccIds = new HashSet<String>();
			for (Object ech : escUsers)
				ccIds.add(((Integer) ech).toString());
			for (Integer ech : srRmg)
				ccIds.add(ech.toString());
			for (Integer ech : finance)
				ccIds.remove(ech.toString());
			List<String> ccids = new ArrayList<String>(ccIds);
			Integer cc[] = new Integer[ccids.size()];
			for (int i = 0; i < ccids.size(); i++)
				cc[i] = Integer.valueOf(ccids.get(i));
			sendMail("Salary release of " + empName, messageBody, finance, cc, recon.getEsrMapId(), "Released", conn);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void escalationforSalaryHold() {
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			Map<String, Date> dateMap = new HashMap<String, Date>();
			SalaryReconciliationHold perdiemHold[] = SalaryReconciliationHoldDaoFactory.create(conn).findByDynamicSelect("SELECT SRH.* FROM SALARY_RECONCILIATION_HOLD SRH JOIN SALARY_RECONCILIATION_REPORT SRR ON SRR.ID=SRH.REP_ID WHERE SRR.STATUS=" + SalaryReconciliationModel.HOLD + " ORDER BY SRH.ID", null);
			for (SalaryReconciliationHold hold : perdiemHold){
				int daysCrossed = EscalationJob.getWorkingDaysCount(1, hold.getActionOn(), new Date());
				if (hold.getEscFrom() == 0 && hold.getActionOn() != null) dateMap.put(hold.getRepId() + "", hold.getActionOn());
				if (daysCrossed == 7 && hold.getEscFrom() == 0){
					sendSalaryHoldTask(hold, conn, daysCrossed);
				} else if ((daysCrossed == 9 && hold.getEscFrom() == 0) || (daysCrossed == 2 && hold.getEscFrom() != 0)){
					if (hold.getEscFrom() > 0 && dateMap.containsKey(hold.getRepId() + "")) hold.setActionOn(dateMap.get(hold.getRepId() + ""));//make sure mail should contain actual hold date.
					escSalaryHold(hold, conn, daysCrossed);
				} else if (daysCrossed >= 3 || hold.getEscFrom() > 0){
					if (dateMap.containsKey(hold.getRepId() + "")) hold.setActionOn(dateMap.get(hold.getRepId() + ""));//make sure mail should contain actual hold date.
					reminderSalaryHold(hold, conn);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			ResourceManager.close(conn);
		}
	}

	private void sendSalaryHoldTask(SalaryReconciliationHold hold, Connection conn, int days) {
		try{
			SalaryReconciliationReport report = SalaryReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			SalaryReconciliation recon = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(report.getSrId());
			String empName = ModelUtiility.getInstance().getEmployeeName(report.getUserId(), conn);
			StringBuffer messageBody = new StringBuffer();
			messageBody.append("The Salary  of ");
			messageBody.append(empName);
			messageBody.append(" for the month ");
			messageBody.append(PortalUtility.returnMonth(recon.getMonth()) + " " + recon.getYear());
			messageBody.append(" is on hold form ");
			messageBody.append(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
			messageBody.append(" and it crossed ");
			messageBody.append(days);
			messageBody.append(" days. Requesting you to take action as soon as possible and please do the needfull.");
			String bodyText = sendMail("Salary On Hold of " + empName, messageBody.toString(), new Integer[] { hold.getUserId() }, null, recon.getEsrMapId(), Status.ON_HOLD, conn);
			InboxModel inbox = new InboxModel();
			inbox.populateInbox(recon.getEsrMapId(), "Salary On Hold of " + empName + "(" + ModelUtiility.getInstance().getEmployeeId(report.getUserId(), conn) + ")", "On Hold", hold.getUserId(), hold.getUserId(), 1, bodyText, "SALARY_RECON");
		} catch (Exception e){}
	}

	private void escSalaryHold(SalaryReconciliationHold hold, Connection conn, int days) {
		try{
			SalaryReconciliationReport report = SalaryReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			SalaryReconciliation recon = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(report.getSrId());
			ProcessChain processChainDto = getProcessChain(conn);
			ProcessEvaluator pEval = new ProcessEvaluator();
			// get the senior RMG from leavel 1
			List<String> srRmg = getList(pEval.approvers(processChainDto.getApprovalChain(), processChainDto.getApprovalChain().split(";").length - 1, 1));
			// get the vp users.
			List<String> toList = null;
			if (hold.getEscFrom() == 0){
				if (srRmg.contains(hold.getUserId() + "")){
					toList = getList(pEval.handlers(processChainDto.getHandler(), 1));
				} else{
					toList = srRmg;
				}
			} else{
				List<String> vp = getList(pEval.handlers(processChainDto.getHandler(), 1));
				if (!vp.contains(hold.getUserId() + "")){
					toList = vp;
				} else return;
			}
			if (toList != null && !toList.isEmpty()){
				SalaryReconciliationHoldDao holdDao = SalaryReconciliationHoldDaoFactory.create(conn);
				String empName = ModelUtiility.getInstance().getEmployeeName(report.getUserId(), conn);
				String userName = ModelUtiility.getInstance().getEmployeeName(hold.getUserId(), conn);
				StringBuffer messageBody = new StringBuffer();
				Integer to[] = new Integer[toList.size()];
				for (int i = 0; i < toList.size(); i++)
					to[i] = Integer.valueOf(toList.get(i));
				messageBody.append("The Salary  of ");
				messageBody.append(empName);
				messageBody.append(" for the month ");
				messageBody.append(PortalUtility.returnMonth(recon.getMonth()) + " " + recon.getYear());
				messageBody.append(" is on hold form ");
				messageBody.append(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
				messageBody.append(". " + userName + " is unable to take actin in ");
				messageBody.append(days);
				messageBody.append(" days and escalated to you please do the needfull");
				String bodyText = sendMail("Salary On Hold Escalation of " + empName, messageBody.toString(), to, new Integer[] { hold.getUserId() }, recon.getEsrMapId(), null, conn);
				InboxModel inbox = new InboxModel();
				for (Integer id : to){
					holdDao.insert(new SalaryReconciliationHold(hold.getRepId(), id, HOLD, "", hold.getId()));
					inbox.populateInbox(recon.getEsrMapId(), "Salary On Hold Escalation of" + empName + "(" + ModelUtiility.getInstance().getEmployeeId(report.getUserId(), conn) + ")", "On Hold", id, id, 1, bodyText, "SALARY_RECON");
				}
				inbox.populateInbox(recon.getEsrMapId(), "Salary On Hold Escalation of" + empName + "(" + ModelUtiility.getInstance().getEmployeeId(report.getUserId(), conn) + ")", "On Hold", 0, hold.getUserId(), 1, bodyText, "SALARY_RECON");
			}
		} catch (Exception e){}
	}

	private List<String> getList(Object[] array) {
		List<String> l = new ArrayList<String>();
		for (Object o : array)
			l.add(o.toString());
		return l;
	}

	private void reminderSalaryHold(SalaryReconciliationHold hold, Connection conn) {
		try{
			SalaryReconciliationReport report = SalaryReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			SalaryReconciliation recon = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(report.getSrId());
			String empName = ModelUtiility.getInstance().getEmployeeName(report.getUserId(), conn);
			StringBuffer messageBody = new StringBuffer();
			messageBody.append("The Salary  of ");
			messageBody.append(empName);
			messageBody.append(" for the month ");
			messageBody.append(PortalUtility.returnMonth(recon.getMonth()) + " " + recon.getYear());
			messageBody.append(" is on hold form ");
			messageBody.append(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
			messageBody.append(". Please do the needfull.");
			sendMail("Salary On Hold Reminder of " + empName, messageBody.toString(), new Integer[] { hold.getUserId() }, null, recon.getEsrMapId(), Status.ON_HOLD, conn);
		} catch (Exception e){}
	}
	
	
	
	
	
	
	
	
	
	
	

	public Integer[] upload(List<FileItem> fileItems, String docType, int uploadSrId, HttpServletRequest request, String description) {
		
		Integer fieldsId[] = new Integer[fileItems.size()];
		DocumentTypes dTypes = DocumentTypes.valueOf(docType);
		Integer[] nonsuccess = new Integer[fileItems.size()];
		Connection conn = null;
		PreparedStatement stmt = null;
	
		switch (dTypes) {
		case SAL_XLS:{
			
			if (fileItems != null && !fileItems.isEmpty()){
				FileItem file = (FileItem) fileItems.get(0);
				InputStream stream = null;
				InputStream stream1 = null;
				String sal = 0+"";
				String mailId = "";
				String name = null ;
				int month = Calendar.getInstance().get(Calendar.MONTH);
				Vector<Vector<Object>> list = null;
			
				try{
			//		month = Integer.parseInt(file.getName().substring(0, file.getName().indexOf(".")));
					stream = file != null ? file.getInputStream() : null;
					stream1 = file != null ? file.getInputStream() : null;
				} catch (IOException e1){
					e1.printStackTrace();
				}
				if (month == 0){
					request.setAttribute("SALARY UPLOAD MESSAGE", "please rename file like 09.xls(for month 09)");
					return new Integer[] { 0 };
				}
				ArrayList notuudatedList = new ArrayList();
				
				if (stream != null) 
					try{
					 list = POIParser.parseXlsSal(stream1, 0);
					 if(list.equals("")||list.isEmpty())
					{
						 list = POIParser.parseXlsSal(stream, 1);	
					}
					stream.close();
					if (list != null && !list.isEmpty()){
						Map<Integer, Integer> userids = UsersDaoFactory.create().findAllUsersEmployeeIds();
//						int sizeOfList = list.size();
//						int c = 0;
					/*	Connection conn = null;
						PreparedStatement stmt = null;*/
						final boolean isConnSupplied = (userConn != null);
						conn = isConnSupplied ? userConn : ResourceManager.getConnection();
						Vector<Object> row1 = new Vector<Object>();
						for (Vector<Object> row : list){
						/*	c++;
						 * Vector<Integer> vec = new Vector<Integer>
							if(c != sizeOfList) {*/
							int userId = userids.get(((Double) row.get(1)).intValue());
							sal =   (String) row.get(3);
					
							try{
								
						
								ResultSet res = null;
								SalaryReportBean salaryReportBean = null;
					
							
								String sql="UPDATE SALARY_RECONCILIATION_REPORT SET PAID=? WHERE  USER_ID IN("+userId+") AND SR_ID IN ("+uploadSrId +") ";
								int i=0;
								stmt = conn.prepareStatement(sql);
								stmt.setObject(1, "paid");
								int affectedrow=stmt.executeUpdate();
								logger.debug("PAID STATUS UPDATED IN SALARY_RECONCILIATION_REPORT ROW AFFECTED"+affectedrow);
						/*		SalaryReconciliationReportDao srdao =  SalaryReconciliationReportDaoFactory.create();
								SalaryReconciliationReport[] reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByUserID( "SELECT * FROM SALARY_RECONCILIATION_REPORT WHERE USER_ID = ? AND  SR_ID = ? ", new Object[] { userId,uploadSrId });
								for (SalaryReconciliationReport salaryReconciliationReport : reconciliationsReports) {
						//			String monthIdT = FBPModel.getMonthId();
									
									SalaryReconciliation reconciliation = null;
									try {
										reconciliation = SalaryReconciliationDaoFactory.create().findByPrimaryKey(uploadSrId);
									} catch (SalaryReconciliationDaoException e1) {
										e1.printStackTrace();
									}
									String month1 = reconciliation.getMonth().toString();
									if(reconciliation.getMonth() < 10) {
										month1= "0"+month1;
									}
									String monthIdT =  reconciliation.getYear().toString() + month1; 
									
									TdsDao tdsdao = TdsDaoFactory.create(conn);
									float tdsAmt = 0;
									   Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { userId, monthIdT });
										
										for(Tds t:tds)
										{
									     tdsAmt=   Float.valueOf(t.getAmount());
										}
							
									 if(!salaryReconciliationReport.getNo_of_modifications().equals("0")){
										
									sal=	(float) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(salaryReconciliationReport.getSalary()));
									 }
									 
									 else
									 {	sal=Float.valueOf(salaryReconciliationReport.getSalary());
										 sal = sal-tdsAmt;
									 }
//								float amount = (float) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(eachMp.getAmount()));
								}*/
								ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
								String profileInfo = ProfileInfoDaoFactory.create(conn).findByDynamicSelect1("SELECT CONCAT(FIRST_NAME, ' ', LAST_NAME) as EMPLOYEE_NAME FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(userId) })[0].getEmployeeName();
								ProfileInfo[] profileInfo1 = ProfileInfoDaoFactory.create(conn).findByOfficialEmailId("SELECT OFFICAL_EMAIL_ID FROM PROFILE_INFO P LEFT JOIN USERS U ON U.PROFILE_ID = P.ID WHERE U.ID = ?",new Object[] {userId});
								for (ProfileInfo profileInfo2 : profileInfo1) {
									mailId =profileInfo2.getOfficalEmailId();
									
								}
								PortalMail pMail = new PortalMail();
								MailGenerator mailGenerator = new MailGenerator();
								List<String> allReceipientcCMailId = new ArrayList<String>();
								allReceipientcCMailId.add(mailId);

								pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
						
								pMail.setMailSubject("Payment Notification");

								pMail.setFinalSalary(String.valueOf(sal));
								pMail.setEmployeeName(profileInfo);
		
								pMail.setTemplateName(MailSettings.PAYMENT_NOTIFICATION);
								mailGenerator.invoker(pMail);
		/*					
								Documents documents = new Documents();
								DocumentsDao documentsDao = DocumentsDaoFactory.create();
								documents.setFilename(docType);
								documents.setDoctype("SALARY");
								documents.setDescriptions(description);
								logger.info("The file " + "SAL REPORT"
										+ " successfully uploaded");
								
								*/
								fieldsId[0] = uploadSrId;
								nonsuccess[0] = uploadSrId;
								}
							catch(SQLException e){
								e.printStackTrace();
								nonsuccess = null;
							}
						}
					}
						
					} catch (Exception e) {
						nonsuccess[0] = uploadSrId;
					
					}
				finally {
					ResourceManager.close(conn);
					ResourceManager.close(stmt);
					
				}
			}
			
			
		}
		break;
		

		case NETSALARY_XLS: {
			InputStream stream = null;
			FileItem file = (FileItem) fileItems.get(0);
			List<CompareErrorReport> reportList = new ArrayList<CompareErrorReport>();
			
			nonsuccess[0] = 3338;

			try {
				stream = file != null ? file.getInputStream() : null;
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (stream != null) {
				Vector<Vector<Object>> list = null;
					try {
						list = POIParser.parseXls(stream, 0);
						stream.close();
					} catch (Exception e) {
						CompareErrorReport report = new CompareErrorReport();
						report.setId("NA");
						report.setErrorMessage("Error accured while parsing excel");
						reportList.add(report);
						e.printStackTrace();
					}
					
					if (list != null && !list.isEmpty()) {
						
						int i = 0;

						for (Vector<Object> row : list) {
							
							if(i != 0) {
						try {
							Integer emplId = ((Double) row.get(0)).intValue();
							Integer addAllEarned = ((Double) row.get(3)).intValue();
							Integer basicEarned = ((Double) row.get(8)).intValue();
							Integer convEarned = ((Double) row.get(10)).intValue();
							Integer educationExp = ((Double) row.get(11)).intValue();
							Integer hraEarned = ((Double) row.get(15)).intValue();
							Integer lta1Earned = ((Double) row.get(17)).intValue();
							Integer mrEarned = ((Double) row.get(19)).intValue();
							Integer otherAllEarned = ((Double) row.get(20)).intValue();
							Integer qplb = ((Double) row.get(22)).intValue();
							Integer telephoneExpEarned = ((Double) row.get(24)).intValue();
							Integer sodexEarned = ((Double) row.get(25)).intValue();
							Integer travelAllowanceEarned = ((Double) row.get(28)).intValue();
							
							Integer totalEarned = ((Double) row.get(38)).intValue();
							
							Integer pf = ((Double) row.get(39)).intValue();
							Integer esi = ((Double) row.get(40)).intValue();
							Integer pTax = ((Double) row.get(41)).intValue();
							Integer salaryAdvance = ((Double) row.get(47)).intValue();
							Integer sodexo = ((Double) row.get(48)).intValue();
							Integer tds = ((Double) row.get(49)).intValue();
							Integer vpf = ((Double) row.get(50)).intValue();
							Integer totalDeductions = ((Double) row.get(51)).intValue();			
							Integer netSalary = ((Double) row.get(52)).intValue();
						
					
							Map<Integer, Integer> userids = null;
							
							try {
								userids = UsersDaoFactory.create().findAllUsersEmployeeIds();
							} catch (UsersDaoException e) {
								e.printStackTrace();
							}
							
							int userId = userids.get(((Double) row.get(0)).intValue());
							
							/*Login[] login = null;
							try {
								 login = LoginDaoFactory.create(conn).findWhereUserIdEquals(userId);
							} catch (LoginDaoException e2) {
								e2.printStackTrace();
							}*/
							//findWhereUserIdEquals
							
//							String monthIdT = FBPModel.getMonthId();
//							String monthIdT = "201711";
							SalaryReconciliation reconciliation = null;
							try {
								reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(uploadSrId);
							} catch (SalaryReconciliationDaoException e1) {
								e1.printStackTrace();
							}
							String month = reconciliation.getMonth().toString();
							if(reconciliation.getMonth() < 10) {
								month = "0"+month;
							}
							String monthIdT =  reconciliation.getYear().toString() + month; 
						
							MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create(conn);
							
							Double allEarned = new Double(0);
							Double allDeducted = new Double(0);
							
							MonthlyPayroll[] monthlyPayrolls = null;
							try {
								monthlyPayrolls = monthlyPayrollDao.findByDynamicWhere(" USERID = ? AND MONTHID = ? ", new Object[] { userId, monthIdT });
							} catch (MonthlyPayrollDaoException e) {
								e.printStackTrace();
							}
							finally {
								ResourceManager.close(conn);
							}
							// Check is there a data to this user
							if(monthlyPayrolls == null || monthlyPayrolls.length <=0) {
								CompareErrorReport report = new CompareErrorReport();
								report.setId(emplId.toString());
								report.setName(row.get(1).toString());
								report.setErrorMessage("No data found in Lynx. Month = "+monthIdT);
							} else {
							
							for (MonthlyPayroll eachMp : monthlyPayrolls){
								CompareErrorReport report = new CompareErrorReport();
								report.setId(emplId.toString());
								report.setName(row.get(1).toString());
															
								Double amtount = new Double((DesEncrypterDecrypter.getInstance().decrypt (eachMp.getAmount())));
								Integer amt = (int) Math.round(amtount);
								if(eachMp.getComponentType() == 1 || eachMp.getComponentType() == 0) {
									allEarned += amt;
								}
								else if(eachMp.getComponentType() == 2) {
									allDeducted += amt;
								}
								
								if(eachMp.getComponent().equalsIgnoreCase("Basic") && !amt.equals(basicEarned)) {
									report.setErrorMessage("Basic : Paypack = "+basicEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Conveyance") && !amt.equals(convEarned)) {
									report.setErrorMessage("Conveyance : Paypack = "+convEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Education") && !amt.equals(educationExp)) {
									report.setErrorMessage("Education : Paypack = "+educationExp +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("HRA") && !amt.equals(hraEarned)) {
									report.setErrorMessage("HRA : Paypack = "+hraEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Leave Travel Allow") && !amt.equals(lta1Earned)) {
									report.setErrorMessage("Leave Travel Allow : Paypack = "+lta1Earned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Medical Allowance") && !amt.equals(mrEarned)) {
									report.setErrorMessage("Medical Allowance : Paypack = "+mrEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Other Allowance") && !amt.equals(otherAllEarned)) {
									report.setErrorMessage("Other Allowance : Paypack = "+otherAllEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("QPLB") && !amt.equals(qplb)) {
									report.setErrorMessage("QPLB : Paypack = "+qplb +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Telephone Allowance") && !amt.equals(telephoneExpEarned)) {
									report.setErrorMessage("Telephone Allowance : Paypack = "+telephoneExpEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Sodexo") && !amt.equals(sodexEarned)) {
									report.setErrorMessage("Sodexo : Paypack = "+sodexEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Transport Allowance") && !amt.equals(travelAllowanceEarned)) {
									report.setErrorMessage("Transport Allowance : Paypack = "+travelAllowanceEarned +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Provident Fund") && !amt.equals(pf)) {
									report.setErrorMessage("Provident Fund : Paypack = "+pf +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("ESIC") && !amt.equals(esi)) {
									report.setErrorMessage("ESIC : Paypack = "+esi +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Professional Tax") && !amt.equals(pTax)) {
									report.setErrorMessage("Salary In Advance : Paypack = "+pTax +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("Salary In Advance") && !amt.equals(salaryAdvance)) {
									report.setErrorMessage("Professional Tax : Paypack = "+salaryAdvance +" Lynx = "+amt);
									reportList.add(report);
								}			
								else if(eachMp.getComponent().equalsIgnoreCase("Sodexo") && !amt.equals(sodexo)) {
									report.setErrorMessage("Sodexo : Paypack = "+sodexo +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("TDS") && !amt.equals(tds)) {
									report.setErrorMessage("TDS : Paypack = "+tds +" Lynx = "+amt);
									reportList.add(report);
								}
								else if(eachMp.getComponent().equalsIgnoreCase("VPF") && !amt.equals(vpf)) {
									report.setErrorMessage("VPF : Paypack = "+vpf +" Lynx = "+amt);
									reportList.add(report);
								}		
							}
							
							if(!allEarned.equals(addAllEarned)) {
								CompareErrorReport report = new CompareErrorReport();
								report.setId(emplId.toString());
								report.setName(row.get(1).toString());
								report.setErrorMessage("ADD ALL Earned : Paypack = "+addAllEarned +" Lynx = "+allEarned);
								reportList.add(report);
							}
							
							if(!allEarned.equals(totalEarned)) {
								CompareErrorReport report = new CompareErrorReport();
								report.setId(emplId.toString());
								report.setName(row.get(1).toString());
								report.setErrorMessage("Total Earned : Paypack = "+totalEarned +" Lynx = "+allEarned);
								reportList.add(report);
							}
							
							if(!allDeducted.equals(totalDeductions)) {
								CompareErrorReport report = new CompareErrorReport();
								report.setId(emplId.toString());
								report.setName(row.get(1).toString());
								report.setErrorMessage("Total deductions : Paypack = "+totalDeductions +" Lynx = "+allDeducted);
								reportList.add(report);
							}
							
							Double netSalaryLynx = allEarned - allDeducted;
							
							if(!netSalaryLynx.equals(netSalary)) {
								CompareErrorReport report = new CompareErrorReport();
								report.setId(emplId.toString());
								report.setName(row.get(1).toString());
								report.setErrorMessage("Net Salary : Paypack = "+netSalary +" Lynx = "+netSalaryLynx);
								reportList.add(report);
							}
							
						}
						} catch (NullPointerException e) {
							CompareErrorReport report = new CompareErrorReport();
							report.setId(row.get(0).toString());
							report.setName(row.get(1).toString());
							report.setErrorMessage("NullPointerException :: OOPS!!! something went wrong(possibility: employee Id doen't exist in DB)");
							reportList.add(report);
							e.printStackTrace();
							
						} catch (ClassCastException e) {
							CompareErrorReport report = new CompareErrorReport();
							report.setId(row.get(0).toString());
							report.setName(row.get(1).toString());
							report.setErrorMessage("ClassCastException :: String or Empty values are not allowed. Please correct and upload again");
							reportList.add(report);
							e.printStackTrace();
						}	 catch (ArrayIndexOutOfBoundsException e) {
							CompareErrorReport report = new CompareErrorReport();
							report.setId(row.get(0).toString());
							report.setName(row.get(1).toString());
							report.setErrorMessage("ArrayIndexOutOfBoundsException :: It seems number of columns in excel sheet are lesser than 53");
							reportList.add(report);
							e.printStackTrace();
						}
														
						}
						
							i++;
						}
					}
				
			}
			request.setAttribute("REPORTLIST", reportList);
		}
		break;
		
		case GROSSSALARY_XLS: {
			List<CompareErrorReport> reportList = new ArrayList<CompareErrorReport>();
			nonsuccess[0] = 3338;
			if (fileItems != null && !fileItems.isEmpty()) {
				FileItem file = (FileItem) fileItems.get(0);
				InputStream stream1 = null;
				InputStream stream2 = null;
				MonthlyPayrollDao monthlyPayrollDao = MonthlyPayrollDaoFactory.create();
				float sal = 0;
				String mailId = "";
				String name = null;
				Vector<Vector<Object>> list1 = null;
				
				SalaryReconciliation reconciliation = null;
				try {
					reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(uploadSrId);
				} catch (SalaryReconciliationDaoException e1) {
					e1.printStackTrace();
				}
				String month = reconciliation.getMonth().toString();
				if(reconciliation.getMonth() < 10) {
					month = "0"+month;
				}
				String yearMonth =  reconciliation.getYear().toString() + month; 

				try {

					stream1 = file != null ? file.getInputStream() : null;

				} catch (IOException e1) {
					e1.printStackTrace();
				}

				ArrayList notuudatedList = new ArrayList();

				if (stream1 != null)
					try {
						try {
							list1 = POIParser.parseXlsRmgComp(stream1, 0);
							stream1.close();
						} catch (Exception e) {
							CompareErrorReport report = new CompareErrorReport();
							report.setId("NA");
							report.setErrorMessage("Error accured while parsing excel");
							reportList.add(report);
							e.printStackTrace();
						}
						

						

						if (list1 != null && !list1.isEmpty()) {
							
							final boolean isConnSupplied = (userConn != null);
							conn = isConnSupplied ? userConn : ResourceManager.getConnection();
							Iterator<Vector<Object>> itr1 = list1.iterator();

							MonthlyPayroll[] monthlyPayroll;
							monthlyPayroll = monthlyPayrollDao.findWhereMonthidEquals(yearMonth);

							int i = 1;
							/*int userId = 0;
*/							
							
							

							// Iterator<Vector<Object>> itr = list1.iterator();
							for (Vector<Object> a : list1) {
								if (i < 5)
									i++;
								else {
									
									//report.setId(a.get(1).toString());
									try{
										int netSalary = 0;
										int deductions = 0;
										int grossSalary = 0;
									
									    //Lynx variables
										int tds = 0;
										int pt = 0;
										int esic =0;
										int pf = 0;
										int sudexo = 0;
										int basic = 0;
										int hra = 0;
										int conveyance = 0;
										int additionalAllowance = 0;
										int otherAllowance =0;
										int medicalAllowance=0;
										int leaveTravelAllow=0;
										int transportAllowance=0;
										int TelephoneAllowance=0;
										int childCareAllowance=0;
										//flags
										int tdsFlag=0;
										int ptFlag=0;
										int esicFlag=0;
										int pfFlag=0;
										int sudexoFlag=0;
										int basicFlag = 0;
										int hraFlag = 0;
										int conveyanceFlag = 0;
										int additionalAllowanceFlag = 0;
										int otherAllowanceFlag = 0;
										int medicalAllowanceFlag=0;
										int leaveTravelAllowFlag=0;
										int transportAllowanceFlag=0;
										int TelephoneAllowanceFlag=0;
										int childCareAllowanceFlag=0;
										
									int empIdExcel =  ((Double)a.get(1)).intValue();
									int grossSalaryExcel = ((Double) a.get(54)).intValue();
									int totalSalaryExcel = ((Double) a.get(53)).intValue();
									int totalDeductionsExcel = ((Double) a.get(52)).intValue();
									
									int tdsExcel = ((Double) a.get(50)).intValue();
									int	ptExcel = ((Double) a.get(49)).intValue();
									int esicExcel = ((Double) a.get(48)).intValue();
									int pfExcel = ((Double) a.get(47)).intValue();
									int sudexoExcel = ((Double) a.get(40)).intValue();
									int basicExcel = ((Double) a.get(33)).intValue();
									int hraExcel = ((Double) a.get(34)).intValue();
									int conveyanceExcel = ((Double) a.get(35)).intValue();
									int additionalAllowanceExcel = ((Double) a.get(36)).intValue();
									int otherAllowanceExcel = ((Double) a.get(45)).intValue();
									int medicalAllowanceExcel = ((Double) a.get(39)).intValue();
									int leaveTravelAllowExcel = ((Double) a.get(41)).intValue();
									int transportAllowanceExcel = ((Double) a.get(44)).intValue();
									int TelephoneAllowanceExcel = ((Double) a.get(43)).intValue();
									int childCareAllowanceExcel = ((Double) a.get(42)).intValue();
									
									Map<Integer, Integer> userids = null;
									
									try {
										userids = UsersDaoFactory.create().findAllUsersEmployeeIds();
									} catch (UsersDaoException e) {
										e.printStackTrace();
									}
									
									int userId = userids.get(((Double) a.get(1)).intValue());
									
							
									

									for (MonthlyPayroll m : monthlyPayroll) {
										if (userId == m.getUserid()) {
										
											Double amtDouble = new Double((DesEncrypterDecrypter.getInstance().decrypt (m.getAmount())));
											int amt = (int) Math.round(amtDouble);
											if(m.getComponent().equalsIgnoreCase("TDS") && !(amt == tdsExcel)) {
												tds=amt;
												tdsFlag=1;
											}
											 if(m.getComponent().equalsIgnoreCase("Professional Tax") && !(amt == ptExcel)) {
												pt=amt;
												ptFlag=1;
											}
											else if(m.getComponent().equalsIgnoreCase("ESIC") && !(amt == esicExcel)) {
												esic=amt;
												esicFlag=1;
											}
											else if (m.getComponent().equalsIgnoreCase("Provident Fund") && !(amt == pfExcel)) {
												pf = amt;
												pfFlag = 1;
											} else if (m.getComponent().equalsIgnoreCase("Sodexo") && !(amt == sudexoExcel)) {
												sudexo = amt;
												sudexoFlag = 1;
											}

											else if (m.getComponent().equalsIgnoreCase("Basic") && !(amt == basicExcel)) {
												basic = amt;
												basicFlag = 1;
											} 
											else if (m.getComponent().equalsIgnoreCase("HRA") && !(amt == hraExcel)) {
												hra = amt;
												hraFlag = 1;
											} 
											else if (m.getComponent().equalsIgnoreCase("Conveyance") && !(amt == conveyanceExcel)) {
												conveyance = amt;
												conveyanceFlag = 1;
												}
											else if (m.getComponent().equalsIgnoreCase("Addt allowances") && !(amt == additionalAllowanceExcel)) {
												additionalAllowance = amt;
												additionalAllowanceFlag = 1;
												}
											else if (m.getComponent().equalsIgnoreCase("Other Allowance") && !(amt == otherAllowanceExcel)) {
												otherAllowance = amt;
												otherAllowanceFlag = 1;
												}
											else if (m.getComponent().equalsIgnoreCase("Medical Allowance") && !(amt == medicalAllowanceExcel)) {
												medicalAllowance = amt;
												medicalAllowanceFlag = 1;
												}
											else if (m.getComponent().equalsIgnoreCase("Leave Travel Allow") && !(amt == leaveTravelAllowExcel)) {
												leaveTravelAllow = amt;
												leaveTravelAllowFlag = 1;
												}
											else if (m.getComponent().equalsIgnoreCase("Transport Allowance") && !(amt == transportAllowanceExcel)) {
												transportAllowance = amt;
												transportAllowanceFlag = 1;
												}
											else if (m.getComponent().equalsIgnoreCase("Telephone Allowance") && !(amt == TelephoneAllowanceExcel)) {
												TelephoneAllowance = amt;
												TelephoneAllowanceFlag = 1;
												}
											else if (m.getComponent().equalsIgnoreCase("Child Care Allowance") && !(amt == childCareAllowanceExcel)) {
												childCareAllowance = amt;
												childCareAllowanceFlag = 1;
												}
											
											if (m.getComponentType() == 0 || m.getComponentType() == 1) {
												netSalary = netSalary + amt;
											}
											if (m.getComponentType() == 2) {
												deductions = deductions + amt;
											}
											grossSalary = netSalary - deductions;

										}
									}
									if (grossSalaryExcel != grossSalary) {
										CompareErrorReport report = new CompareErrorReport();
										report.setId(a.get(1).toString());
										report.setName(a.get(5).toString());
										report.setErrorMessage("Gross Salary : Paypack = " + grossSalaryExcel
												+ " Lynx = " + grossSalary);
										
										reportList.add(report);
										
										if (totalSalaryExcel != netSalary) {
											CompareErrorReport report1 = new CompareErrorReport();
											report1.setId(a.get(1).toString());
											report1.setName(a.get(5).toString());
											report1.setErrorMessage("Total Salary : Paypack = " + totalSalaryExcel
													+ " Lynx = " + netSalary);
											reportList.add(report1);
										
										}
										if (totalDeductionsExcel != deductions) {
											CompareErrorReport report1 = new CompareErrorReport();
											report1.setId(a.get(1).toString());
											report1.setName(a.get(5).toString());
											report1.setErrorMessage("Deductions : Paypack = " + totalDeductionsExcel
													+ " Lynx = " + deductions);
											reportList.add(report1);
											
										}
										if(tdsFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("TDS Deduction : Paypack = " + tdsExcel
													+ " Lynx = " + tds);
											reportList.add(report3);
											
										}
										if(ptFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Professional Tax : Paypack = " + ptExcel
													+ " Lynx = " + pt);
											reportList.add(report3);
											
										}
										if(esicFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("ESIC : Paypack = " + esicExcel
													+ " Lynx = " + esic);
											reportList.add(report3);
											
										}
										if(pfFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Provident Fund : Paypack = " + pfExcel
													+ " Lynx = " + pf);
											reportList.add(report3);
											
										}
										if(sudexoFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Sudexo : Paypack = " + sudexoExcel
													+ " Lynx = " + sudexo);
											reportList.add(report3);
											
										}
										if(basicFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Basic : Paypack = " + basicExcel
													+ " Lynx = " + basic);
											reportList.add(report3);
											
										}
										if(hraFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("HRA : Paypack = " + hraExcel
													+ " Lynx = " + hra);
											reportList.add(report3);
											
										}
										if(conveyanceFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Conveyance : Paypack = " + conveyanceExcel
													+ " Lynx = " + conveyance);
											reportList.add(report3);
											
										}
										if(additionalAllowanceFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("additionalAllowance : Paypack = " + additionalAllowanceExcel
													+ " Lynx = " + additionalAllowance);
											reportList.add(report3);
											
										}
										if(otherAllowanceFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("otherAllowance : Paypack = " + otherAllowanceExcel
													+ " Lynx = " + otherAllowance);
											reportList.add(report3);
											
										}
										if(medicalAllowanceFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("MedicalAllowance : Paypack = " + medicalAllowanceExcel
													+ " Lynx = " + medicalAllowance);
											reportList.add(report3);
											
										}
										if(leaveTravelAllowFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("LeaveTravel Allow : Paypack = " + leaveTravelAllowExcel
													+ " Lynx = " + leaveTravelAllow);
											reportList.add(report3);
											
										}
										if(transportAllowanceFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Transport Allowance : Paypack = " + transportAllowanceExcel
													+ " Lynx = " + transportAllowance);
											reportList.add(report3);
											
										}
										if(TelephoneAllowanceFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Telephone Allowance : Paypack = " + TelephoneAllowanceExcel
													+ " Lynx = " + TelephoneAllowance);
											reportList.add(report3);
											
										}
										if(childCareAllowanceFlag==1){
											CompareErrorReport report3 = new CompareErrorReport();
											report3.setId(a.get(1).toString());
											report3.setName(a.get(5).toString());
											report3.setErrorMessage("Child Care Allowance : Paypack = " + childCareAllowanceExcel
													+ " Lynx = " + childCareAllowance);
											reportList.add(report3);
											
										}
										}
										
									}catch (NullPointerException e) {
										CompareErrorReport report = new CompareErrorReport();
										report.setId(a.get(1).toString());
										report.setName(a.get(5).toString());
										report.setErrorMessage("NullPointerException :: OOPS!!! something went wrong(possibility: employee Id doen't exist in DB OR NULL value in excel Uploaded)");
										reportList.add(report);
										e.printStackTrace();
										
									} catch (ClassCastException e) {
										CompareErrorReport report = new CompareErrorReport();
										report.setId(a.get(1).toString());
										report.setName(a.get(5).toString());
										report.setErrorMessage("ClassCastException :: String or Empty values are not allowed. Please correct and upload again");
										reportList.add(report);
										e.printStackTrace();
									}	 catch (ArrayIndexOutOfBoundsException e) {
										CompareErrorReport report = new CompareErrorReport();
										report.setId(a.get(1).toString());
										report.setName(a.get(5).toString());
										report.setErrorMessage("ArrayIndexOutOfBoundsException :: It seems number of columns in excel sheet are lesser than 54");
										reportList.add(report);
										e.printStackTrace();
									}
									}

								}
							}

						
					} catch (Exception e) {
						e.printStackTrace();

					} finally {
						ResourceManager.close(conn);
						ResourceManager.close(stmt);

					}
			}

			request.setAttribute("REPORTLIST", reportList);
		}
		break;
		}
	
		
		
		return nonsuccess;
	}
	

	public Attachements downloads(PortalForm form)
	{
		Attachements attachements = new Attachements();
		String seprator = File.separator;
		String path = "Data" + seprator;
		try
		{
			SalaryReconciliationBean sm = (SalaryReconciliationBean) form;
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents[ ] dacDocuments = documentsDao
					.findWhereIdEquals(Integer.parseInt(sm.getSalReport()));
			PortalData portalData = new PortalData();
			path = portalData.getDirPath();
			attachements.setFileName(dacDocuments[0].getFilename());

			path = portalData.getfolder(path);
			path = path + seprator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (DocumentsDaoException e)
		{
			// TODO Auto-generated catch block
			logger.info("failed to download check the file path:",e);
			e.printStackTrace();
		}
		return attachements;

	}
	
}
