package com.dikshatech.portal.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.InvoiceReconciliationDao;
import com.dikshatech.portal.dto.InvoiceReconciliationPk;
import com.dikshatech.portal.dto.Invoicedto;
import com.dikshatech.portal.exceptions.InvoiceReconciliationDaoException;
import com.dikshatech.portal.factory.CurrencyDaoFactory;


public class InvoiceReconciliationDaoImpl implements InvoiceReconciliationDao{


	protected java.sql.Connection	userConn;
	protected static final Logger	logger		= Logger.getLogger(InvoiceReconciliationDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT	= "SELECT ID,USER_ID,INVOICE_AMOUNT ,INVOICE_DATE,ACTION_DATE,INVOICE_STATUS, COLLECTION_AMOUNT, COLLECTION_DATE,RMG_ID FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this tableINSERT INTO INVOICE_RECONCILIATION_REPORT
 ( ID,USER_ID,RMG_ID ) 
VALUES ( 0,95,3);
	 */
	protected final String			SQL_INSERT	= "INSERT INTO " + getTableName() + " ( ID,USER_ID,INVOICE_AMOUNT ,INVOICE_DATE,ACTION_DATE,INVOICE_STATUS, COLLECTION_AMOUNT, COLLECTION_DATE,RMG_ID ) VALUES ( ?, ?, ?, ?, ?, ?, ?,?,?)";
	protected final String			SQL_INSERT1	= "INSERT INTO " + getTableName() + " (  ID,USER_ID,RMG_ID,RMG_TR_REPORT,IS_EDIT_INVOICE ) VALUES ( ?, ?, ?,?,?)";

	protected final String			SQL_UPDATE	= "UPDATE " + getTableName() + " SET USER_ID=?,INVOICE_AMOUNT=? ,INVOICE_DATE=?,ACTION_DATE=?,INVOICE_STATUS=?, COLLECTION_AMOUNT=?, COLLECTION_DATE=?,IS_EDIT_INVOICE=? WHERE RMG_ID=? and RMG_TR_REPORT=?";

	
	
	/**
	 * SQL UPDATE statement for this table
	 *//*
	
	protected final String			SQL_UPDATE	= "UPDATE " + getTableName() + " SET ID = ?, DEP_ID = ?, USER_ID = ?, PAYABLE_DAYS = ?, PERDIEM = ?, CURRENCY_TYPE = ?, AMOUNT = ?, AMOUNT_INR = ?, TOTAL = ?,  MANAGER_ID = ?, MANAGER_NAME = ?, CLIENT_NAME = ?, MODIFIED_BY = ?, MODIFIED_ON = ?, TYPE = ?, COMMENTS = ?,PERDIEM_FROM = ?, PERDIEM_TO = ?, ACCOUNT_TYPE = ?, SALARY_CYCLE=? WHERE ID = ?";
	*//**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE	= "DELETE FROM " + getTableName() + " WHERE ID = ?";

	


	

	public String getTableName() {
		return "INVOICE_RECONCILIATION_REPORT";
	}
	public InvoiceReconciliationPk insert(Invoicedto dto) throws InvoiceReconciliationDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		    stmt = conn.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );
			int index = 1;
			stmt.setInt( index++, dto.getId() );
			stmt.setInt( index++, dto.getUserId());
		//	stmt.setInt( index++, dto.getInvoice_Id());
			stmt.setInt( index++, dto.getInvoiceAmount());
			
			/*stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			stmt.setDate(index++, dto.getCompletedOn()==null ? null : new java.sql.Date( dto.getCompletedOn().getTime() ) );*/
			
			stmt.setDate(index++,dto.getInvoiceDate()==null?null:new java.sql.Date(dto.getInvoiceDate().getTime() ));
			stmt.setDate(index++,dto.getActionDate()==null?null:new java.sql.Date(dto.getActionDate().getTime() ));
			
			stmt.setString( index++, dto.getInvoiceStatus() );
		
			stmt.setInt( index++, dto.getCollectionAmount() );
			stmt.setDate(index++,dto.getCollectionDate()==null?null:new java.sql.Date(dto.getCollectionDate().getTime() ));
			
			stmt.setInt( index++, dto.getInvoice_Id());
			logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setId( rs.getInt( 1 ) );
			}
		
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new InvoiceReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	public InvoiceReconciliationPk saveForInvoiceReconciliation(Invoicedto dto) throws InvoiceReconciliationDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		    stmt = conn.prepareStatement( SQL_INSERT1, Statement.RETURN_GENERATED_KEYS );
			int index = 1;
			stmt.setInt( index++, 0);
			stmt.setInt( index++, dto.getUser_Id());
		    stmt.setInt( index++, dto.getTimeSheet_Id() );
			stmt.setInt( index++, dto.getTimesheetReportId());
			stmt.setString(index++, dto.getIseditinvoice());
			logger.debug("Executing " + SQL_INSERT1 + " with DTO: " + dto );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setId( rs.getInt( 1 ) );
			}
		
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new InvoiceReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	@Override
	public void update(Invoicedto dto) throws InvoiceReconciliationDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			//stmt.setInt( index++, dto.getId() );
			stmt.setInt( index++, dto.getUser_Id());
		//	stmt.setInt( index++, dto.getInvoice_Id());
			stmt.setInt( index++, dto.getInvoiceAmount());
			
			stmt.setDate(index++,dto.getInvoiceDate()==null?null:new java.sql.Date(dto.getInvoiceDate().getTime() ));
			stmt.setDate(index++,dto.getActionDate()==null?null:new java.sql.Date(dto.getActionDate().getTime() ));
			/*stmt.setDate(index++, (Date) dto.getInvoiceDate());
			stmt.setDate(index++, (Date) dto.getActionDate());*/
			stmt.setString( index++, dto.getInvoiceStatus() );
		
			stmt.setInt( index++, dto.getCollectionAmount() );
			stmt.setDate(index++,dto.getCollectionDate()==null?null:new java.sql.Date(dto.getCollectionDate().getTime() ));
			//stmt.setDate( index++, (Date) dto.getCollectionDate() );
			stmt.setString( index++, dto.getIseditinvoice() );
			stmt.setInt( index++, dto.getInvoice_Id());
			stmt.setInt( index++, dto.getTimesheetReportId());
			
			
			int rows = stmt.executeUpdate();
			
			//USER_ID=?,INVOICE_AMOUNT=? ,INVOICE_DATE=?,ACTION_DATE=?,INVOICE_STATUS=?, COLLECTION_AMOUNT=?, COLLECTION_DATE=?,IS_EDIT_INVOICE=? WHERE RMG_ID=? and RMG_TR_REPORT=?			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new InvoiceReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	
	
	
	private void reset(Invoicedto dto) {
		// TODO Auto-generated method stub
		
	}
	public  Invoicedto[] findWhereInvoiceId(int ID,String flag) throws InvoiceReconciliationDaoException {
	
		if("1".equals(flag)){
			return findByDynamicSelect("select distinct RM.ID,rmgt.ID,roll.emp_id,u.ID as user_id,concat(pf.first_name,' ',pf.last_name), ch.ch_code_name, p.name as project_name,pod.PO_NUMBER,pomap.PO_ST_DATE, pomap.PO_END_DATE,pomap.RATE,pomap.CURRENCY, pomap.CURRENT as PO_STATUS,roll.project_type,rmgt.START_DATE,rmgt.END_DATE,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE ,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.ACTION_DATE,IR.INVOICE_STATUS,IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE,IR.IS_EDIT_INVOICE from ROLL_ON roll, PROFILE_INFO pf,PROJECT p,CHARGE_CODE ch, PO_DETAILS pod, PO_EMP_MAP pomap,USERS u left join RMG_TIMESHEET_REPORT rmgt on u.ID=rmgt.USER_ID left join RMG_TIMESHEET RM on RM.ID=rmgt.RMG_TIMESHEET_ID left join INVOICE_RECONCILIATION_REPORT IR on IR.RMG_ID=RM.ID where IR.RMG_ID=? and IR.IS_EDIT_INVOICE='1' AND IR.RMG_TR_REPORT=rmgt.ID and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id", new Object[] { new Integer(ID) });

		}
		if("0".equals(flag)){
			return findByDynamicSelect("select distinct RM.ID,rmgt.ID,roll.emp_id,u.ID as user_id,concat(pf.first_name,' ',pf.last_name), ch.ch_code_name, p.name as project_name,pod.PO_NUMBER,pomap.PO_ST_DATE, pomap.PO_END_DATE,pomap.RATE,pomap.CURRENCY, pomap.CURRENT as PO_STATUS,roll.project_type,rmgt.START_DATE,rmgt.END_DATE,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE ,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.ACTION_DATE,IR.INVOICE_STATUS,IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE,IR.IS_EDIT_INVOICE from ROLL_ON roll, PROFILE_INFO pf,PROJECT p,CHARGE_CODE ch, PO_DETAILS pod, PO_EMP_MAP pomap,USERS u left join RMG_TIMESHEET_REPORT rmgt on u.ID=rmgt.USER_ID left join RMG_TIMESHEET RM on RM.ID=rmgt.RMG_TIMESHEET_ID left join INVOICE_RECONCILIATION_REPORT IR on IR.RMG_ID=RM.ID where IR.RMG_ID=?  AND IR.RMG_TR_REPORT=rmgt.ID and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id", new Object[] { new Integer(ID) });
			//return findByDynamicSelect("select roll.emp_id,concat(pf.first_name,' ',pf.last_name), ch.ch_code_name,p.name as project_name,pod.PO_NUMBER,pomap.PO_ST_DATE, pomap.PO_END_DATE,pomap.RATE,pomap.CURRENCY,pomap.CURRENT as PO_STATUS,roll.project_type,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE , IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.INVOICE_STATUS,IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE from ROLL_ON roll, PROFILE_INFO pf, USERS u left join RMG_TIMESHEET_REPORT rmgt on u.ID=rmgt.USER_ID left join INVOICE_RECONCILIATION_REPORT IR on IR.USER_ID=rmgt.USER_ID left join RMG_TIMESHEET RM on RM.ID=IR.RMG_ID, PROJECT p,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where IR.id=? and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;", new Object[] { new Integer(ID) });
		}
		return null;
	}
	public  Invoicedto[] findAll() throws InvoiceReconciliationDaoException {
		
		return findByDynamicSelect1("select RM.ID,roll.emp_id,concat(pf.first_name,' ',pf.last_name),pomap.po_number,ch.ch_code_name, p.name as project_name,pomap.PO_ST_DATE, pomap.PO_END_DATE, pomap.rate,pomap.currency, pomap.CURRENT as PO_STATUS,roll.project_type,R.CHRG_CODE_ID, R.START_DATE,R.END_DATE,R.WORKING_DAYS,I.INVOICE_AMOUNT,I.INVOICE_DATE,I.INVOICE_STATUS,I.COLLECTION_AMOUNT, I.COLLECTION_DATE from ROLL_ON roll, PROFILE_INFO pf,USERS u,PROJECT p,CHARGE_CODE ch, PO_DETAILS pod, PO_EMP_MAP pomap,INVOICE_RECONCILIATION_REPORT I, INVOICE_RECONCILIATION IR,RMG_TIMESHEET RM ,RMG_TIMESHEET_REPORT R where pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and u.ID=R.USER_ID and I.INVOICE_ID=IR.ID and IR.STATUS=RM.STATUS and R.RMG_TIMESHEET_ID=RM.ID and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;");

}

	@Override
	public Invoicedto[] findWhereInvoiceReportId(int id) throws InvoiceReconciliationDaoException {
		// TODO Auto-generated method stub
		return findByDynamicSelect("select RM.ID,roll.emp_id,concat(pf.first_name,' ',pf.last_name), ch.ch_code_name,p.name as project_name,pod.PO_NUMBER,pomap.PO_ST_DATE, pomap.PO_END_DATE,pomap.RATE,pomap.CURRENCY,pomap.CURRENT as PO_STATUS,roll.project_type,rmgt.START_DATE,rmgt.END_DATE,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE ,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.INVOICE_STATUS,IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE from ROLL_ON roll, PROFILE_INFO pf, USERS u left join RMG_TIMESHEET_REPORT rmgt on u.ID=rmgt.USER_ID left join INVOICE_RECONCILIATION_REPORT IR on IR.USER_ID=rmgt.USER_ID left join RMG_TIMESHEET RM on RM.ID=IR.RMG_ID, PROJECT p,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where IR.id=? and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;", new Object[] { new Integer(id) });
	}

	public  Invoicedto[] findAllReport(int ID) throws InvoiceReconciliationDaoException {
		
	
			return findByDynamicSelect("select RM.ID,rmgt.ID,roll.emp_id,u.ID as user_id,concat(pf.first_name,' ',pf.last_name), ch.ch_code_name, p.name as project_name,pod.PO_NUMBER,pomap.PO_ST_DATE, pomap.PO_END_DATE,pomap.RATE,pomap.CURRENCY, pomap.CURRENT as PO_STATUS,roll.project_type,rmgt.START_DATE,rmgt.END_DATE,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE ,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.ACTION_DATE,IR.INVOICE_STATUS,IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE from ROLL_ON roll, PROFILE_INFO pf,PROJECT p,CHARGE_CODE ch, PO_DETAILS pod, PO_EMP_MAP pomap,USERS u left join RMG_TIMESHEET_REPORT rmgt on u.ID=rmgt.USER_ID left join RMG_TIMESHEET RM on RM.ID=rmgt.RMG_TIMESHEET_ID left join INVOICE_RECONCILIATION_REPORT IR on IR.RMG_ID=RM.ID where RM.ID=? and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;", new Object[] { new Integer(ID) });

		
	}


	public Invoicedto[] findByDynamicSelect1(String sql) throws InvoiceReconciliationDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = sql;
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL);
			}
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			// bind parameters
		/*	for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}*/
			rs = stmt.executeQuery();
			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new InvoiceReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	
	

	public Invoicedto[] findByDynamicSelect(String sql, Object[] sqlParams) throws InvoiceReconciliationDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = sql;
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
			return fetchMultiResults(rs);
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new InvoiceReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	
	private Invoicedto[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<Invoicedto> resultList = new ArrayList<Invoicedto>();
		while (rs.next()){
			Invoicedto dto = new Invoicedto();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		Invoicedto ret[] = new Invoicedto[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	private void populateDto(Invoicedto dto, ResultSet rs) throws SQLException {
		int index = 1;

		dto.setId(rs.getInt(index++));
		dto.setTimesheetReportId(rs.getInt(index++));
		dto.setEmpId(rs.getInt(index++));
		dto.setUser_Id(rs.getInt(index++));
		dto.setName(rs.getString(index++));
		//dto.setPoNo(rs.getString(index++));
		dto.setChrg_Code_Name(rs.getString(index++));
		dto.setPrj_Name(rs.getString(index++));
		dto.setPoNo(rs.getString(index++));
		dto.setPoStartDate(rs.getDate(index++));
	
		dto.setPoEndDate(rs.getDate(index++));
	
		dto.setRate(rs.getInt(index++));
		dto.setCurrency(rs.getString(index++));
		dto.setPoStatus(rs.getString(index++));
		dto.setProjectType(rs.getString(index++));
		//dto.setPoid(rs.getString(index++));
		//dto.setPoEmpId(rs.getString(index++));
		//dto.setChrg_Code_Id(rs.getInt(index++));
		dto.setTimesheetStartDate(rs.getDate(index++));
		dto.setTimesheetEndDate(rs.getDate(index++));
		dto.setWorking_Days(rs.getInt(index++));
		dto.setLeave(rs.getInt(index++));
		dto.setTimeSheet_Cato(rs.getString(index++));
		dto.setTimesheetStatus(rs.getString(index++));
		dto.setActionBy(rs.getString(index++));
		dto.setInvoiceAmount(rs.getInt(index++));
		dto.setInvoiceDate(rs.getDate(index++));
		dto.setActionDate(rs.getDate(index++));
		dto.setInvoiceStatus(rs.getString(index++));
		dto.setCollectionAmount(rs.getInt(index++));
		dto.setCollectionDate(rs.getDate(index++));
	
		

	}


public InvoiceReconciliationDaoImpl() {
		
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * Method 'DepPerdiemReportDaoImpl'
	 * 
	 * @param userConn
	 */
	public InvoiceReconciliationDaoImpl(final java.sql.Connection userConn) {
		// TODO Auto-generated constructor stub
		this.userConn = userConn;
	}
	@Override
	public List<String[]> findWhereInvoiceidForReport(int id) throws InvoiceReconciliationDaoException {
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			final String SQL = "select roll.emp_id,concat(pf.first_name,' ',pf.last_name),p.name as project_name,ch.ch_code_name,pomap.PO_ST_DATE, pomap.PO_END_DATE,pod.PO_NUMBER,pomap.RATE,pomap.CURRENCY,pomap.CURRENT as PO_STATUS,rmgt.START_DATE,rmgt.END_DATE,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE , rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.ACTION_DATE,IR.INVOICE_STATUS, IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE ,IR.RMG_TR_REPORT from ROLL_ON roll, PROFILE_INFO pf, USERS u  left join INVOICE_RECONCILIATION_REPORT IR on IR.USER_ID=u.ID left join RMG_TIMESHEET_REPORT rmgt on rmgt.ID=IR.RMG_TR_REPORT,PROJECT p,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where  IR.RMG_ID=?  and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;";

		//	final String SQL = "select roll.emp_id,concat(pf.first_name,' ',pf.last_name),p.name as project_name,ch.ch_code_name,pomap.PO_ST_DATE, pomap.PO_END_DATE,pod.PO_NUMBER,pomap.RATE,pomap.CURRENCY,pomap.CURRENT as PO_STATUS,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE , rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.ACTION_DATE,IR.INVOICE_STATUS, IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE ,IR.RMG_TR_REPORT from ROLL_ON roll, PROFILE_INFO pf, USERS u  left join INVOICE_RECONCILIATION_REPORT IR on IR.USER_ID=u.ID left join RMG_TIMESHEET_REPORT rmgt on rmgt.ID=IR.RMG_TR_REPORT,PROJECT p,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where  IR.RMG_ID=?  and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;";
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			int i=1;
			stmt.setObject(1, id);
				
			rs = stmt.executeQuery();
			List<String[]> resultList = new ArrayList<String[]>();
			Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
			while (rs.next()){
				String[] row = new String[24];
				row[0] = rs.getInt(1) + "";//emp id
				row[1] = rs.getString(2);// name
				row[2] = rs.getString(3);// prj name
				row[3] = rs.getString(4);//ch name
				row[4] = rs.getString(5);//po st date
				row[5] = rs.getString(6);//po end date
				row[6] = rs.getString(7);//po num 
				row[7] = rs.getString(8);// po rate
				row[8] = rs.getString(9);//curr
				row[9] = rs.getString(10);//status
				row[10] = rs.getString(11);//sd
				row[11] = rs.getString(12);//ed
				row[12] = rs.getString(13);//workday
				row[13] = rs.getString(14);//lea
				row[14] = rs.getString(15);//cate
				row[15] = rs.getString(16);//stat
				row[16] = rs.getString(17);//invoice
				row[17] = rs.getString(18);//action
				row[18] = rs.getString(19);//invoice amt
				row[19] = rs.getString(20);//invoice date
				row[20] = rs.getString(21);//action date
				row[21] = rs.getString(22);//invoice status
				row[22] = rs.getString(23);//coll date
			//	row[23] = rs.getString(24);//
			//	row[24] = rs.getString(25);//

				resultList.add(row);
			}
			return resultList;
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new InvoiceReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	@Override
	public List<String[]> findWhereFinalReconciliationReport(int id) throws InvoiceReconciliationDaoException {
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		//	final String SQL = "select roll.emp_id,concat(pf.first_name,' ',pf.last_name),p.name as project_name,ch.ch_code_name,pomap.PO_ST_DATE, pomap.PO_END_DATE,pod.PO_NUMBER,pomap.RATE,pomap.CURRENCY,pomap.CURRENT as PO_STATUS,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE , rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.ACTION_DATE,IR.INVOICE_STATUS, IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE,sal.CTC,rfd.AMOUNT,rmgt.START_DATE,rmgt.END_DATE from ROLL_ON roll, PROFILE_INFO pf, USERS u left join INVOICE_RECONCILIATION_REPORT IR on IR.USER_ID=u.ID left join RMG_TIMESHEET_REPORT rmgt on rmgt.ID=IR.RMG_TR_REPORT left join SALARY sal on sal.ID=u.ID  left join EMP_SER_REQ_MAP eq on eq.REQUESTOR_ID=u.ID left join REIMBURSEMENT_FINANCIAL_DATA rfd on rfd.ESRMAP_ID=eq.ID ,PROJECT p ,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where IR.RMG_ID=? and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;";
			
			final String SQL = "select roll.emp_id,concat(pf.first_name,' ',pf.last_name),p.name as project_name,ch.ch_code_name,pomap.PO_ST_DATE, pomap.PO_END_DATE,pod.PO_NUMBER,pomap.RATE,pomap.CURRENCY,pomap.CURRENT as PO_STATUS,rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE , rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY, IR.INVOICE_AMOUNT,IR.INVOICE_DATE,IR.ACTION_DATE,IR.INVOICE_STATUS, IR.COLLECTION_AMOUNT, IR.COLLECTION_DATE,FI.CTC,rfd.AMOUNT,rmgt.START_DATE,rmgt.END_DATE from ROLL_ON roll, PROFILE_INFO pf, USERS u left join INVOICE_RECONCILIATION_REPORT IR on IR.USER_ID=u.ID left join RMG_TIMESHEET_REPORT rmgt on rmgt.ID=IR.RMG_TR_REPORT left join FINANCE_INFO FI on FI.ID=u.ID  left join EMP_SER_REQ_MAP eq on eq.REQUESTOR_ID=u.ID left join REIMBURSEMENT_FINANCIAL_DATA rfd on rfd.ESRMAP_ID=eq.ID ,PROJECT p ,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where IR.RMG_ID=? and pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;";
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			int i=1;
			stmt.setObject(1, id);
				
			rs = stmt.executeQuery();
			List<String[]> resultList = new ArrayList<String[]>();
			Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
			while (rs.next()){
				String[] row = new String[29];
				row[0] = rs.getInt(1) + "";//id
				row[1] = rs.getString(2);//name
				row[2] = rs.getString(3);//pj name
				row[3] = rs.getString(4);//ch name
				row[4] = rs.getString(5); //po st date
				row[5] = rs.getString(6); //po end date
				row[6] = rs.getString(7);//po nunm 
				row[7] = rs.getString(8);//po -rate
				row[8] = rs.getString(9);//po curr
				row[9] = rs.getString(10);//postatus 
				row[10] = rs.getString(11);//work days
				row[11] = rs.getString(12);//leav
				row[12] = rs.getString(13);//cate
				row[13] = rs.getString(14);//status
				row[14] = rs.getString(15);//action by
				row[15] = rs.getString(16);//inv amt
				row[16] = rs.getString(17);//invoice date
				row[17] = rs.getString(18);//act date
				row[18] = rs.getString(19);//inv status
				row[19] = rs.getString(20);//coll amt
				row[20] = rs.getString(21);//coll date
				
				
	
	//final Reportdata
				double  resourceCost;
				double  travelCost;
				double collectionAmount;
				if(rs.getString(22)!=null){
			//	String  resourceCost1 = DesEncrypterDecrypter.getInstance().decrypt(rs.getString(22));
					String  resourceCost1 =(rs.getString(22));
					resourceCost=Double.parseDouble(resourceCost1);
				}
				else{
					 resourceCost=0;		
				}
				
				
				
				if(rs.getString(23)!=null){
					//int resourceCost = Integer.parseInt(rs.getString(22));
				travelCost = Double.parseDouble(rs.getString(23));
				 //travelCost = Integer.parseInt(new DecimalFormat("0.00").format(Double.parseDouble(rs.getString(23))));	
				}
					else{
						travelCost=0;		
					}
				if(rs.getString(20)!=null){
					//int resourceCost = Integer.parseInt(rs.getString(22));
					collectionAmount =  Double.parseDouble(rs.getString(20));
				 //travelCost = Integer.parseInt(new DecimalFormat("0.00").format(Double.parseDouble(rs.getString(23))));	
				}
					else{
				 collectionAmount =0;		
					}
				double amount=resourceCost/12;
				double rate =  Double.parseDouble(rs.getString(8));
				double totalCost=amount+travelCost;
			//	double collectionAmount =  Double.parseDouble(rs.getString(20));
				double profit=(collectionAmount/totalCost)*100;
				double diffInPOAndCollection=rate - collectionAmount;
				
				row[21] = amount + "";	
				row[22] = rs.getString(23);
				row[23] = "0";
				row[24] = totalCost + "";
				row[25] = profit + "" ;
				row[26] = diffInPOAndCollection + "" ;
				row[27] = rs.getString(24);//st date
				row[28] = rs.getString(25);//en date
				
				

				resultList.add(row);
			}
			return resultList;
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new InvoiceReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	

	
}