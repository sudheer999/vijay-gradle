/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dikshatech.beans.SalaryReportBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.SalaryReportUtilities;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.SalaryReconciliationDao;
import com.dikshatech.portal.dao.TdsDao;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryReconciliation;
import com.dikshatech.portal.dto.SalaryReconciliationPk;
import com.dikshatech.portal.dto.SalaryReconciliationReport;
import com.dikshatech.portal.dto.Tds;
import com.dikshatech.portal.exceptions.SalaryReconciliationDaoException;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationDaoFactory;
import com.dikshatech.portal.factory.SalaryReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.TdsDaoFactory;
import com.dikshatech.portal.models.FBPModel;
import com.dikshatech.portal.models.SalaryReconciliationModel;



public class SalaryReconciliationDaoImpl extends AbstractDAO implements SalaryReconciliationDao {

	
	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger				= Logger.getLogger(SalaryReconciliationDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT			= "SELECT ID, ESR_MAP_ID, MONTH, YEAR, STATUS, CREATED_ON, COMPLETED_ON FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT			= "INSERT INTO " + getTableName() + " ( ID, ESR_MAP_ID, MONTH, YEAR, STATUS, CREATED_ON, COMPLETED_ON ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE			= "UPDATE " + getTableName() + " SET ID = ?, ESR_MAP_ID = ?, MONTH = ?, YEAR = ?, STATUS = ?, CREATED_ON = ?, COMPLETED_ON = ? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE			= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID			= 1;
	/**
	 * Index of column ESR_MAP_ID
	 */
	protected static final int		COLUMN_ESR_MAP_ID	= 2;
	/**
	 * Index of column MONTH
	 */
	protected static final int		COLUMN_MONTH		= 3;
	/**
	 * Index of column YEAR
	 */
	protected static final int		COLUMN_YEAR			= 4;
	/**
	 * Index of column STATUS
	 */
	protected static final int		COLUMN_STATUS		= 5;
	/**
	 * Index of column CREATED_ON
	 */
	protected static final int		COLUMN_CREATED_ON	= 6;
	/**
	 * Index of column COMPLETED_ON
	 */
	protected static final int		COLUMN_COMPLETED_ON	= 7;
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS	= 7;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID		= 1;

	/**
	 * Inserts a new row in the salary_reconciliation table.
	 */
	public SalaryReconciliationPk insert(SalaryReconciliation dto) throws SalaryReconciliationDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			if (dto.getId() != null){
				stmt.setInt(index++, dto.getId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getEsrMapId() != null){
				stmt.setInt(index++, dto.getEsrMapId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getMonth() != null){
				stmt.setInt(index++, dto.getMonth().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getYear() != null){
				stmt.setInt(index++, dto.getYear().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getStatus() != null){
				stmt.setInt(index++, dto.getStatus().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setDate(index++, dto.getCreatedOn() == null ? null : new java.sql.Date(dto.getCreatedOn().getTime()));
			stmt.setTimestamp(index++, dto.getCompletedOn() == null ? null : new java.sql.Timestamp(dto.getCompletedOn().getTime()));
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			}
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()){
				dto.setId(new Integer(rs.getInt(1)));
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the salary_reconciliation table.
	 */
	public void update(SalaryReconciliationPk pk, SalaryReconciliation dto) throws SalaryReconciliationDaoException {
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
			if (dto.getId() != null){
				stmt.setInt(index++, dto.getId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getEsrMapId() != null){
				stmt.setInt(index++, dto.getEsrMapId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getMonth() != null){
				stmt.setInt(index++, dto.getMonth().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getYear() != null){
				stmt.setInt(index++, dto.getYear().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getStatus() != null){
				stmt.setInt(index++, dto.getStatus().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setDate(index++, dto.getCreatedOn() == null ? null : new java.sql.Date(dto.getCreatedOn().getTime()));
			stmt.setTimestamp(index++, dto.getCompletedOn() == null ? null : new java.sql.Timestamp(dto.getCompletedOn().getTime()));
			if (pk.getId() != null){
				stmt.setInt(8, pk.getId().intValue());
			} else{
				stmt.setNull(8, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Deletes a single row in the salary_reconciliation table.
	 */
	public void delete(SalaryReconciliationPk pk) throws SalaryReconciliationDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_DELETE + " with PK: " + pk);
			}
			stmt = conn.prepareStatement(SQL_DELETE);
			if (pk.getId() != null){
				stmt.setInt(1, pk.getId().intValue());
			} else{
				stmt.setNull(1, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns the rows from the salary_reconciliation table that matches the specified primary-key value.
	 */
	public SalaryReconciliation findByPrimaryKey(SalaryReconciliationPk pk) throws SalaryReconciliationDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria 'ID = :id'.
	 */
	public SalaryReconciliation findByPrimaryKey(Integer id) throws SalaryReconciliationDaoException {
		SalaryReconciliation ret[]=null;
		try{
			ret = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { id });
			
		}catch(Exception e){
			logger.error("Exception: " +e);
		}
		return ret.length == 0 ? null : ret[0];	
		
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria ''.
	 */
	public SalaryReconciliation[] findAll() throws SalaryReconciliationDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public SalaryReconciliation[] findWhereEsrMapIdEquals(Integer esrMapId) throws SalaryReconciliationDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ESR_MAP_ID = ? ORDER BY ESR_MAP_ID", new Object[] { esrMapId });
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria 'MONTH = :month'.
	 */
	public SalaryReconciliation[] findWhereMonthEquals(Integer month) throws SalaryReconciliationDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE MONTH = ? ORDER BY MONTH", new Object[] { month });
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria 'YEAR = :year'.
	 */
	public SalaryReconciliation[] findWhereYearEquals(Integer year) throws SalaryReconciliationDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE YEAR = ? ORDER BY YEAR", new Object[] { year });
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria 'STATUS = :status'.
	 */
	public SalaryReconciliation[] findWhereStatusEquals(Integer status) throws SalaryReconciliationDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE STATUS = ? ORDER BY STATUS", new Object[] { status });
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria 'CREATED_ON = :createdOn'.
	 */
	public SalaryReconciliation[] findWhereCreatedOnEquals(Date createdOn) throws SalaryReconciliationDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE CREATED_ON = ? ORDER BY CREATED_ON", new Object[] { createdOn == null ? null : new java.sql.Date(createdOn.getTime()) });
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the criteria 'COMPLETED_ON = :completedOn'.
	 */
	public SalaryReconciliation[] findWhereCompletedOnEquals(Date completedOn) throws SalaryReconciliationDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE COMPLETED_ON = ? ORDER BY COMPLETED_ON", new Object[] { completedOn == null ? null : new java.sql.Timestamp(completedOn.getTime()) });
	}

	/**
	 * Method 'SalaryReconciliationDaoImpl'
	 */
	public SalaryReconciliationDaoImpl() {}

	/**
	 * Method 'SalaryReconciliationDaoImpl'
	 * 
	 * @param userConn
	 */
	public SalaryReconciliationDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "SALARY_RECONCILIATION";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected SalaryReconciliation fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			SalaryReconciliation dto = new SalaryReconciliation();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected SalaryReconciliation[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection resultList = new ArrayList();
		while (rs.next()){
			SalaryReconciliation dto = new SalaryReconciliation();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		SalaryReconciliation ret[] = new SalaryReconciliation[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(SalaryReconciliation dto, ResultSet rs) throws SQLException {
		dto.setId(new Integer(rs.getInt(COLUMN_ID)));
		dto.setEsrMapId(new Integer(rs.getInt(COLUMN_ESR_MAP_ID)));
		dto.setMonth(new Integer(rs.getInt(COLUMN_MONTH)));
		dto.setYear(new Integer(rs.getInt(COLUMN_YEAR)));
		dto.setStatus(new Integer(rs.getInt(COLUMN_STATUS)));
		dto.setCreatedOn(rs.getDate(COLUMN_CREATED_ON));
		dto.setCompletedOn(rs.getTimestamp(COLUMN_COMPLETED_ON));
		if (rs.getMetaData().getColumnCount() > NUMBER_OF_COLUMNS){
			try{
				dto.setCanEdit(rs.getInt("CANEDIT") + "");
				dto.setReqId(rs.getString("REQ_ID") + "");
			} catch (Exception e){}
		}
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(SalaryReconciliation dto) {}

	/**
	 * Returns all rows from the salary_reconciliation table that match the specified arbitrary SQL statement
	 */
	public SalaryReconciliation[] findByDynamicSelect(String sql, Object[] sqlParams) throws SalaryReconciliationDaoException {
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
			throw new SalaryReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns all rows from the salary_reconciliation table that match the specified arbitrary SQL statement
	 */
	public SalaryReconciliation[] findByDynamicWhere(String sql, Object[] sqlParams) throws SalaryReconciliationDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;
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
			throw new SalaryReconciliationDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	private String getMonthId(int sr_id) {
		String monthId = "";
		try{
			SalaryReconciliation sr = SalaryReconciliationDaoFactory.create().findByPrimaryKey(sr_id);
			int month = sr.getMonth();
			String strMonth = month + "";
			if (month < 10 && month > 0) strMonth = "0" + month;
			monthId = String.valueOf(sr.getYear()) + strMonth;
		} catch (SalaryReconciliationDaoException e){
			e.printStackTrace();
		}
		return monthId;
	}

	@Override
	public SalaryReportBean[] getConfirmedSalaryDetails(int sr_id, int flag) {
		final String accNoSql = ", (CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO";
		final String bankNameSql = ", (CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME";
		final String SQL = "SELECT U.EMP_ID , CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,SRR.SALARY" + accNoSql + bankNameSql + ",SRR.PAYABLE_DAYS FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=? AND SRR.STATUS!=? AND SRR.STATUS!=? ORDER BY U.EMP_ID";
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		SalaryReportBean salaryReportBean = null;
		List<SalaryReportBean> list = new ArrayList<SalaryReportBean>();
		String monthId = getMonthId(sr_id);
		try{
			//String trans_part = "SAL FOR " + SalaryReportUtilities.getTextMonthFromId(monthId);
			String trans_part = SalaryReportUtilities.getTextMonthFromId(monthId);
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setObject(1, sr_id);
			stmt.setObject(2, SalaryReconciliationModel.HOLD);
			stmt.setObject(3, SalaryReconciliationModel.REJECTED);
			ResultSet res = stmt.executeQuery();
			while (res.next()){
				String bankName = res.getString(5);
				if (flag == 3 || (flag == 1 && bankName != null && bankName.toUpperCase().contains("ICICI")) || (flag == 2 && (bankName == null || !bankName.toUpperCase().contains("ICICI")))){
					salaryReportBean = new SalaryReportBean();
					salaryReportBean.setEmp_code(res.getInt(1));
					salaryReportBean.setName(res.getString(2));
					salaryReportBean.setCr_dr("CR");
					salaryReportBean.setCur_code("INR");
					salaryReportBean.setBankName(bankName);
					salaryReportBean.setPayableDays(res.getDouble(6));
					try{
						float finalSal = 0;
						String sal = (new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt((res.getString(3))))));
						float tdsAmt = 0;
						int id  = 0;
						TdsDao tdsdao = TdsDaoFactory.create(conn);
						ProfileInfoDao profileInfoDaoJ = ProfileInfoDaoFactory.create();
						ProfileInfo[] eeUm = null;
		//				String monthIdT = FBPModel.getMonthId();
						SalaryReconciliation reconciliation = null;
						try {
							reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(sr_id);
						} catch (SalaryReconciliationDaoException e1) {
							e1.printStackTrace();
						}
						String month1 = reconciliation.getMonth().toString();
						if(reconciliation.getMonth() < 10) {
							month1 = "0"+month1;
						}
						String monthIdT =  reconciliation.getYear().toString() + month1; 
						
						
						Calendar now = Calendar.getInstance();
						int year = now.get(Calendar.YEAR);
						String yearInString = String.valueOf(year);
						 float tsal = 0;
						 int srId = 0;
						 int month = Calendar.getInstance().get(Calendar.MONTH)+1;
						 SalaryReconciliationDao sr = SalaryReconciliationDaoFactory.create();
							SalaryReconciliation[] srr = sr.findSrId("MONTH = ? AND YEAR = ?", new Object[] {  month, yearInString});
							for (SalaryReconciliation salaryReconciliation : srr) {
								srId=salaryReconciliation.getId();
							}
						eeUm = profileInfoDaoJ.findByUserId("SELECT ID FROM USERS where EMP_ID  = ("+salaryReportBean.getEmp_code()+") ",new Object[] {});
						for(ProfileInfo eUm:eeUm)
						{
						 id = eUm.getId();
							
						}
						
		                   Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { id, monthIdT });
							
							for(Tds t:tds)
							{
						     tdsAmt=   Float.valueOf(t.getAmount());
							}
							SalaryReconciliationReport[] reconciliationsReports=null;
							reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelect("SELECT * FROM SALARY_RECONCILIATION_REPORT WHERE USER_ID = ? AND SR_ID = ?", new Object[] { id,sr_id });
							for (SalaryReconciliationReport report : reconciliationsReports){
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
			
						     
							
						salaryReportBean.setTran_amt(String.valueOf(report.getSalary()));
							}
					} catch (Exception e){
						logger.error("there is no valid salary for :" + salaryReportBean.getName());
					}
					try{
						salaryReportBean.setAccount_no(res.getString(4));
					} catch (Exception e){
						logger.error("there is no valid account no for :" + salaryReportBean.getName());
					}
					salaryReportBean.setTran_part(trans_part);
					if (Double.parseDouble(salaryReportBean.getTran_amt()) > 0) list.add(salaryReportBean);
				}
			}
		} catch (SQLException e){
			logger.error("There is an SQLException occured while querying the users details from users table " + e.getMessage(), e);
		}
		return list.toArray(new SalaryReportBean[list.size()]);
	}
	@Override
	public SalaryReportBean[] getBankSalaryDetails(int sr_id,ArrayList<Integer> ssr_id,String flag) {
		final String accNoSql = ", (CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO";
		final String bankNameSql = ", (CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME";
		
		int retval = ssr_id.size();
		int count=1;
		StringBuilder builder = new StringBuilder();
		for( int i = 0 ; i < retval; i++ ) {
			if(count<retval){
			    builder.append("?,");
				count++;
			}else{
				builder.append("?");
			}
		
		}
	    final String SQL_HDFC = "SELECT U.EMP_ID , CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,SRR.SALARY" + accNoSql + bankNameSql + ",SRR.PAYABLE_DAYS,F.PRIMARY_IFSC_CODE,PF.OFFICAL_EMAIL_ID FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=? and SRR.ID in("+builder+") AND SRR.STATUS!=? AND SRR.STATUS!=? AND F.PRIM_BANK_NAME Like '%HDFC%' ORDER BY U.EMP_ID";
		final String SQL_NON_HDFC="SELECT U.EMP_ID , CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,SRR.SALARY" + accNoSql + bankNameSql + ",SRR.PAYABLE_DAYS,F.PRIMARY_IFSC_CODE,PF.OFFICAL_EMAIL_ID FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE SR_ID=? and SRR.ID in("+builder+") AND SRR.STATUS!=? AND SRR.STATUS!=? AND F.PRIM_BANK_NAME NOT Like '%HDFC%' ORDER BY U.EMP_ID";
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		SalaryReportBean salaryReportBean = null;
		List<SalaryReportBean> list = new ArrayList<SalaryReportBean>();
		String monthId = getMonthId(sr_id);
		//String flag=bean.getBankFlag();
		
		
	
		
		try{
			String sql="UPDATE SALARY_RECONCILIATION_REPORT SET PAID =? WHERE SR_ID=? AND ID IN("+builder+") ";
			int i=3;
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setObject(1, "un paid");
			stmt.setObject(2, sr_id);
			for(i=3;i<ssr_id.size()+3;i++){
				stmt.setObject(i,ssr_id.get(i-3));	
			}
			int affectedrow=stmt.executeUpdate();
			logger.debug("PAID STATUS UPDATED IN SALARY_RECONCILIATION_REPORT ROW AFFECTED"+affectedrow);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
		try{
			//String trans_part = "SAL FOR " + SalaryReportUtilities.getTextMonthFromId(monthId);
			String trans_part = SalaryReportUtilities.getTextMonthFromId(monthId);
			//conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if(flag.equals("HDFC_BANK")){
				stmt = conn.prepareStatement(SQL_HDFC);
				logger.debug("HDFC bank result");
				
				
			}else{
				stmt = conn.prepareStatement(SQL_NON_HDFC);
				logger.debug("NON HDFC bank result");
			
				
			
			}
			int i=2;
			stmt.setObject(1, sr_id);
			for(i=2;i<ssr_id.size()+2;i++){
				stmt.setObject(i,ssr_id.get(i-2));
				System.out.println(ssr_id.get(i-2));
			}
			stmt.setObject(i++, SalaryReconciliationModel.HOLD);
			stmt.setObject(i++, SalaryReconciliationModel.REJECTED);
			
			
			 res = stmt.executeQuery();
			logger.debug("AFFECTED ROW FOR DOWNLODE EXCEL SHEET"+res);
			
			
			while (res.next()){
				String bankName = res.getString(5);
				    salaryReportBean = new SalaryReportBean();
					salaryReportBean.setEmp_code(res.getInt(1));
					salaryReportBean.setName(res.getString(2));
					salaryReportBean.setCr_dr("CR");
					salaryReportBean.setCur_code("INR");
					salaryReportBean.setBankName(bankName);
					salaryReportBean.setPayableDays(res.getDouble(6));
					salaryReportBean.setPrimaryIfsc(res.getString(7));
					salaryReportBean.setEmail_id(res.getString(8));
					try{
						float finalSal = 0;
						String sal = (new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt((res.getString(3))))));
						float tdsAmt = 0;
						int id  = 0;
						TdsDao tdsdao = TdsDaoFactory.create(conn);
						ProfileInfoDao profileInfoDaoJ = ProfileInfoDaoFactory.create();
						ProfileInfo[] eeUm = null;
						 float tsal = 0;
		//				String monthIdT = FBPModel.getMonthId();
						 SalaryReconciliation reconciliation = null;
							try {
								reconciliation = SalaryReconciliationDaoFactory.create(conn).findByPrimaryKey(sr_id);
							} catch (SalaryReconciliationDaoException e1) {
								e1.printStackTrace();
							}
							String month = reconciliation.getMonth().toString();
							if(reconciliation.getMonth() < 10) {
								month = "0"+month;
							}
							String monthIdT =  reconciliation.getYear().toString() + month; 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						eeUm = profileInfoDaoJ.findByUserId("SELECT ID FROM USERS where EMP_ID  = ("+salaryReportBean.getEmp_code()+") ",new Object[] {});
						for(ProfileInfo eUm:eeUm)
						{
						 id = eUm.getId();
							
						}
						SalaryReconciliationReport[] reconciliationsReports=null;
					    Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { id, monthIdT });
						
						for(Tds t:tds)
						{
					     tdsAmt=   Float.valueOf(t.getAmount());
						}
						reconciliationsReports = SalaryReconciliationReportDaoFactory.create(conn).findByDynamicSelect("SELECT * FROM SALARY_RECONCILIATION_REPORT WHERE USER_ID = ? AND SR_ID = ?", new Object[] { id,sr_id });
						for (SalaryReconciliationReport report : reconciliationsReports){
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
						     
							
						salaryReportBean.setTran_amt(String.valueOf(report.getSalary()));
						}
					} catch (Exception e){
						logger.error("there is no valid salary for :" + salaryReportBean.getName());
					}
					try{
						salaryReportBean.setAccount_no(res.getString(4));
					} catch (Exception e){
						logger.error("there is no valid account no for :" + salaryReportBean.getName());
					}
					salaryReportBean.setTran_part(trans_part);
					if (Double.parseDouble(salaryReportBean.getTran_amt()) > 0)
						list.add(salaryReportBean);
				}
			
		} catch (SQLException e){
			logger.error("There is an SQLException occured while querying the users details from users table " + e.getMessage(), e);
		
		}finally{
			ResourceManager.close(res);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
		System.out.println(list.toArray(new SalaryReportBean[list.size()]));
		return list.toArray(new SalaryReportBean[list.size()]);
	}

	public SalaryReconciliation[] findSrId(String sql, Object[] sqlParams) throws SalaryReconciliationDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = "SELECT ID  FROM SALARY_RECONCILIATION   " + " where "  + sql ;
			

			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResultsSr(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new SalaryReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	protected SalaryReconciliation[] fetchMultiResultsSr(ResultSet rs) throws SQLException
	{
		Collection<SalaryReconciliation> resultList1 = new ArrayList<SalaryReconciliation>();
		while (rs.next()) {
			SalaryReconciliation dto = new SalaryReconciliation();
			populateDtoSr( dto, rs);
			resultList1.add( dto );
		}
		
		SalaryReconciliation ret[] = new SalaryReconciliation[ resultList1.size() ];
		resultList1.toArray( ret );
		return ret;
	}
	protected void populateDtoSr(SalaryReconciliation dto, ResultSet rs) throws SQLException
	{

		
		int index = 0;
		dto.setId(rs.getInt(1));


		}
	
	
}
