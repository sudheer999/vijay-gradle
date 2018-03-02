package com.dikshatech.portal.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dikshatech.beans.SalaryReportBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.InvoiceReconciliationDao;
import com.dikshatech.portal.dao.InvoiceReconciliationReportDao;
import com.dikshatech.portal.dto.DepPerdiemReport;
import com.dikshatech.portal.dto.DepPerdiemReq;
import com.dikshatech.portal.dto.InvoiceReconciliation;
import com.dikshatech.portal.dto.InvoiceReconciliationPk;
import com.dikshatech.portal.dto.Invoicedto;
import com.dikshatech.portal.dto.RmgTimeSheetReco;
import com.dikshatech.portal.dto.SalaryReconciliation;
import com.dikshatech.portal.exceptions.DepPerdiemReqDaoException;
import com.dikshatech.portal.exceptions.InvoiceReconciliationDaoException;
import com.dikshatech.portal.factory.CurrencyDaoFactory;


public class InvoiceReconciliationReportDaoImpl implements InvoiceReconciliationReportDao {

	protected java.sql.Connection	userConn;
	protected static final Logger	logger		= Logger.getLogger(InvoiceReconciliationDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT	= "SELECT ID,ESR_MAP_ID,TERM,MONTH ,YEAR,SUBMITTED_ON, COMPLETED_ON, STATUS,HTML_STATUS,INVOICE_STATUS FROM " + getTableName() +"";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT	= "INSERT INTO " + getTableName() + " ( ID,ESR_MAP_ID,TERM,MONTH ,YEAR,SUBMITED_ON, COMPLETED_ON, STATUS,HTML_STATUS,INVOICE_STATUS FROM ) VALUES ( ?, ?, ?, ?, ?, ?, ?,?,?,?)";
	/**
	 * SQL UPDATE statement for this table
	 *//*
	protected final String			SQL_UPDATE	= "UPDATE " + getTableName() + " SET ID = ?, DEP_ID = ?, USER_ID = ?, PAYABLE_DAYS = ?, PERDIEM = ?, CURRENCY_TYPE = ?, AMOUNT = ?, AMOUNT_INR = ?, TOTAL = ?,  MANAGER_ID = ?, MANAGER_NAME = ?, CLIENT_NAME = ?, MODIFIED_BY = ?, MODIFIED_ON = ?, TYPE = ?, COMMENTS = ?,PERDIEM_FROM = ?, PERDIEM_TO = ?, ACCOUNT_TYPE = ?, SALARY_CYCLE=? WHERE ID = ?";
	*//**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE	= "DELETE FROM " + getTableName() + " WHERE ID = ?";

	public InvoiceReconciliationReportDaoImpl(com.mysql.jdbc.Connection conn) {
		// TODO Auto-generated constructor stub
		this.userConn = userConn;
	}

	public InvoiceReconciliationReportDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public String getTableName() {
		return "RMG_TIMESHEET";
	}

	@Override
	public InvoiceReconciliationPk insert(Invoicedto dto) throws InvoiceReconciliationDaoException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public InvoiceReconciliation[] findAll() throws InvoiceReconciliationDaoException {
		//return findByDynamicSelect( SQL_SELECT + " WHERE STATUS LIKE '%submited%' ORDER BY ID" );
		return findByDynamicSelect( "SELECT ID,ESR_MAP_ID,TERM,MONTH ,YEAR,SUBMITTED_ON, COMPLETED_ON, STATUS,HTML_STATUS,INVOICE_STATUS FROM RMG_TIMESHEET WHERE   STATUS LIKE '%completed%' ");
		
	}

	private InvoiceReconciliation[] findByDynamicSelect(String sql) throws InvoiceReconciliationDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				
				try {
					// get the user-specified connection or get a connection from the ResourceManager
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
				
					// construct the SQL statement
					final String SQL = sql;
				
				
					if (logger.isDebugEnabled()) {
						logger.debug( "Executing " + SQL);
					}
				
					// prepare statement
					stmt = conn.prepareStatement( SQL );
					stmt.setMaxRows( maxRows );
				
					// bind parameters
				
				
					rs = stmt.executeQuery();
				
					// fetch the results
					return fetchMultiResults(rs);
				}
				catch (Exception _e) {
					logger.error( "Exception: " + _e.getMessage(), _e );
					throw new InvoiceReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
				}
				finally {
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied) {
						ResourceManager.close(conn);
					}
				
				}
	}

	private InvoiceReconciliation[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<InvoiceReconciliation> resultList = new ArrayList<InvoiceReconciliation>();
		while (rs.next()) {
			InvoiceReconciliation dto = new InvoiceReconciliation();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		InvoiceReconciliation ret[] = new InvoiceReconciliation[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}
	
	
	


	private void populateDto(InvoiceReconciliation dto, ResultSet rs) throws SQLException {
		int index = 1;

		dto.setId( rs.getInt(index++));
		dto.setEsrMapId(rs.getInt(index++));
		dto.setTerm(rs.getString(index++));
		dto.setMonth(rs.getString(index++));
		dto.setYear(rs.getInt(index++));
		dto.setSubmittedOn(rs.getDate(index++));
		dto.setCompletedOn(rs.getTime(index++));
		dto.setStatus(rs.getString(index++));
		dto.setHtml_status(rs.getString(index++));
		dto.setInvoice_status(rs.getString(index++));
		
		
	}

	@Override
	public RmgTimeSheetReco[] findByTimeSheetId(int id)throws InvoiceReconciliationDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		RmgTimeSheetReco ret[]=null;
		RmgTimeSheetReco dto = null;
		List<RmgTimeSheetReco> list = new ArrayList<RmgTimeSheetReco>();
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = "SELECT ID,ESR_MAP_ID,TERM,MONTH ,YEAR,SUBMITTED_ON, COMPLETED_ON, STATUS,HTML_STATUS,INVOICE_STATUS FROM RMG_TIMESHEET WHERE   STATUS LIKE '%completed%' and ID=?";
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
		
			stmt.setObject(1, id);
			rs = stmt.executeQuery();
	
		int index=1;
			// fetch the results
			while (rs.next()) {
			        dto = new RmgTimeSheetReco();
				dto.setId( rs.getInt(index++));
				dto.setEsrMapId(rs.getInt(index++));
				dto.setTerm(rs.getString(index++));
				dto.setMonth(rs.getString(index++));
				dto.setYear(rs.getInt(index++));
				dto.setSubmittedOn(rs.getDate(index++));
				dto.setCompletedOn(rs.getTime(index++));
				dto.setStatus(rs.getString(index++));
			list.add(dto);
			}
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new InvoiceReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
		return list.toArray(new RmgTimeSheetReco[list.size()]);
}




}