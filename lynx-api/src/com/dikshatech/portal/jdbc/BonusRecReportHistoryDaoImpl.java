package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.BonusRecReportHistoryDao;
import com.dikshatech.portal.dto.BonusRecReportHistory;
import com.dikshatech.portal.dto.BonusRecReportHistoryPk;
import com.dikshatech.portal.exceptions.BonusRecReportHistoryDaoException;



public class BonusRecReportHistoryDaoImpl extends AbstractDAO implements BonusRecReportHistoryDao {

	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger		= Logger.getLogger(DepPerdiemReportHistoryDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT	= "SELECT ID, REP_ID, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MODIFIED_BY, MODIFIED_ON, COMMENTS, TYPE,QUA_AMOUNT, QUA_AMOUNT_INR,COM_AMOUNT, COM_AMOUNT_INR, TOTAL,CURRENCY_NAME FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT	= "INSERT INTO " + getTableName() + " ( ID, REP_ID, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MODIFIED_BY, MODIFIED_ON, COMMENTS, TYPE,QUA_AMOUNT, QUA_AMOUNT_INR,COM_AMOUNT, COM_AMOUNT_INR, TOTAL,CURRENCY_NAME ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE	= "UPDATE " + getTableName() + " SET ID = ?, REP_ID = ?, QUATERELY_BONUS = ?, COMPANY_BONUS = ?, CURRENCY_TYPE = ?, MODIFIED_BY = ?, MODIFIED_ON = ?, COMMENTS = ?, TYPE = ?  QUA_AMOUNT = ?,COM_AMOUNT = ?, QUA_AMOUNT_INR = ? ,COM_AMOUNT_INR = ? ,TOTAL = ?,CURRENCY_NAME=? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE	= "DELETE FROM " + getTableName() + " WHERE ID = ?";

	
	
	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(BonusRecReportHistory dto) {}

	/**
	

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "BONUS_REC_REPORT_HISTORY";
	}
	/**
	 * Inserts a new row in the bonus_rec_report_history table.
	 */
	@Override
	public BonusRecReportHistoryPk insert(BonusRecReportHistory dto) throws BonusRecReportHistoryDaoException {
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
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getRepId());
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqBonus()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcBonus()));
			stmt.setShort(index++, dto.getCurrencyType());
			stmt.setString(index++, dto.getModifiedBy());
			stmt.setTimestamp(index++, dto.getModifiedOn() == null ? null : new java.sql.Timestamp(dto.getModifiedOn().getTime()));
            stmt.setString(index++, dto.getComments());
			stmt.setShort(index++, dto.getType());
			
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmount()));
			stmt.setString(index++, dto.getqAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmountInr()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmount()));
			stmt.setString(index++, dto.getcAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmountInr()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getTotal()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getCurrencyName()));
		

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
				dto.setId(rs.getInt(1));
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new BonusRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	/**
	 * Updates a single row in the bonus_rec_report_history table.
	 */
	@Override
	public void update(BonusRecReportHistoryPk pk, BonusRecReportHistory dto) throws BonusRecReportHistoryDaoException {
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
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getRepId());
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqBonus()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcBonus()));
			stmt.setShort(index++, dto.getCurrencyType());
			stmt.setString(index++, dto.getModifiedBy());
			stmt.setTimestamp(index++, dto.getModifiedOn() == null ? null : new java.sql.Timestamp(dto.getModifiedOn().getTime()));
			stmt.setString(index++, dto.getComments());
			stmt.setShort(index++, dto.getType());
			
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmount()));
			stmt.setString(index++, dto.getqAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmountInr()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmount()));
			stmt.setString(index++, dto.getcAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmountInr()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getTotal()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getCurrencyName()));
			stmt.setInt(index++, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new BonusRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}

	/**
	 * Deletes a single row in the bonus_rec_report_history table.
	 */
	@Override
	public void delete(BonusRecReportHistoryPk pk) throws BonusRecReportHistoryDaoException {
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
			stmt.setInt(1, pk.getId());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new BonusRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}
	/**
	 * Returns the rows from the bonus_rec_report_history table that matches the specified primary-key value.
	 */
	@Override
	public BonusRecReportHistory findByPrimaryKey(BonusRecReportHistoryPk pk) throws BonusRecReportHistoryDaoException {
		return findByPrimaryKey(pk.getId());
	}
	/**
	 * Returns all rows from the bonus_rec_report_history table that match the criteria 'ID = :id'.
	 */
	@Override
	public BonusRecReportHistory findByPrimaryKey(int id) throws BonusRecReportHistoryDaoException {
		BonusRecReportHistory ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the bonus_rec_report_history table that match the criteria ''.
	 */
	@Override
	public BonusRecReportHistory[] findAll() throws BonusRecReportHistoryDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the bonus_rec_report_history table that match the criteria 'REP_ID = :repId'.
	 */
	@Override
	public BonusRecReportHistory[] findByBonusReport(int repId) throws BonusRecReportHistoryDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE REP_ID = ? ORDER BY ID DESC", new Object[] { new Integer(repId) });
	}

	/**
	 * Returns all rows from the bonus_rec_report_history table that match the criteria 'REP_ID = :repId'.
	 */
	@Override
	public BonusRecReportHistory[] findWhereRepIdEquals(int repId) throws BonusRecReportHistoryDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE REP_ID = ? ORDER BY REP_ID", new Object[] { new Integer(repId) });
	}

	/**
	 * Returns all rows from the bonus_rec_report_history table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	@Override
	public BonusRecReportHistory[] findWhereIsDeletedEquals(short isDeleted) throws BonusRecReportHistoryDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE IS_DELETED = ? ORDER BY IS_DELETED", new Object[] { new Short(isDeleted) });
	}
	
	/**
	 * Method 'BonusRecReportHistoryDaoImpl'
	 */
	public BonusRecReportHistoryDaoImpl() {}

	/**
	 * Method 'BonusRecReportHistoryDaoImpl'
	 * 
	 * @param userConn
	 */
	public BonusRecReportHistoryDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}
	/**
	 * Sets the value of maxRows
	 */
	@Override
	public void setMaxRows(int maxRows) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Gets the value of maxRows
	 */
	@Override
	public int getMaxRows() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Fetches a single row from the result set
	 */
	protected BonusRecReportHistory fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			BonusRecReportHistory dto = new BonusRecReportHistory();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected BonusRecReportHistory[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<BonusRecReportHistory> resultList = new ArrayList<BonusRecReportHistory>();
		while (rs.next()){
			BonusRecReportHistory dto = new BonusRecReportHistory();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		BonusRecReportHistory ret[] = new BonusRecReportHistory[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(BonusRecReportHistory dto, ResultSet rs) throws SQLException {
		int index = 1;
		dto.setId(rs.getInt(index++));
		dto.setRepId(rs.getInt(index++));
		try{
			int columnid = index++;
			dto.setqBonus(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e){}
		try{
			int columnid = index++;
			dto.setcBonus(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e){}
		dto.setCurrencyType(rs.getShort(index++));
		dto.setModifiedBy(rs.getString(index++));
		dto.setModifiedOn(rs.getTimestamp(index++));
		dto.setComments(rs.getString(index++));
		dto.setType(rs.getShort(index++));
		try{
			int columnid = index++;
			dto.setqAmount(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e){}
		
		try{
			int columnid = index++;
			dto.setqAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e){}
		try{
			int columnid = index++;
			dto.setcAmount(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e){}
		try{
			int columnid = index++;
			dto.setcAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e){}
		try{
			int columnid = index++;
			dto.setTotal(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e){}
		dto.setCurrencyName(rs.getString(index++));
	}

	/**
	 * Returns all rows from the bonus_rec_report_history table that match the specified arbitrary SQL statement
	 */
	@Override
	public BonusRecReportHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusRecReportHistoryDaoException {
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
					throw new BonusRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}
	/**
	 * Returns all rows from the bonus_rec_report_history table that match the specified arbitrary SQL statement
	 */
	@Override
	public BonusRecReportHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusRecReportHistoryDaoException {
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
					throw new BonusRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
			}
	}	
	
	
