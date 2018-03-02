package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.VoluntaryProvidentFundDao;
import com.dikshatech.portal.dto.FbpReq;
import com.dikshatech.portal.dto.VoluntaryProvidentFund;
import com.dikshatech.portal.dto.VoluntaryProvidentFundPk;
import com.dikshatech.portal.exceptions.FbpReqDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.VoluntaryProvidentFundDaoException;

public class VoluntaryProvidentFundDaoImpl implements VoluntaryProvidentFundDao {
	
	protected java.sql.Connection	userConn;
	protected static final Logger	logger						= Logger.getLogger(VoluntaryProvidentFundDaoImpl.class);
	
	protected final String			SQL_SELECT					= "SELECT ID, USER_ID, VPF FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT					= "INSERT INTO " + getTableName() + " ( ID, USER_ID, VPF  ) VALUES (  ?, ?, ?)";
	/**
	 * SQL UPDATE statement for this table
	 */

	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID					= 1;
	

	/**
	 * Index of column RECEIVER_ID
	 */
	protected static final int		COLUMN_USER_ID			    = 2;
	/**
	 * Index of column ESR_MAP_ID
	 */
	protected static final int		COLUMN_VPF      			= 3;
	
	
	
	
	public static String getTableName() {
		return "VPF_REQ";
	}

	
	
	protected void reset(VoluntaryProvidentFund dto) {}
	

	/**
	 * Method 'ProfileInfoDaoImpl'
	 * 
	 * @param userConn
	 */
	public VoluntaryProvidentFundDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}
	
	public VoluntaryProvidentFundDaoImpl() {}

	
	
	public VoluntaryProvidentFundPk insert(VoluntaryProvidentFund dto) throws VoluntaryProvidentFundDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			 stmt.setInt( index++, dto.getId() );
			
		    stmt.setInt(index++, dto.getUserId());
			
			stmt.setString(index++, dto.getVpf());
	
			
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			}
			int rows = stmt.executeUpdate();
			//InboxCountAdapter.invokeUser(dto.getReceiverId());
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
			_e.printStackTrace();
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new VoluntaryProvidentFundDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the INBOX table.
	 */
	

	/**
	 * Deletes a single row in the INBOX table.
	 */
	
	
	
	protected VoluntaryProvidentFund[] fetchMultiResult(ResultSet rs)
			throws SQLException {
		Collection<VoluntaryProvidentFund> resultList = new ArrayList<VoluntaryProvidentFund>();
		while (rs.next()) {
			VoluntaryProvidentFund dto = new VoluntaryProvidentFund();
			populateDto(dto, rs);
			resultList.add(dto);

		}

		VoluntaryProvidentFund ret[] = new VoluntaryProvidentFund[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(VoluntaryProvidentFund dto, ResultSet rs)
			throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setUserId(rs.getInt(COLUMN_USER_ID));
		dto.setVpf(rs.getString(COLUMN_VPF));
		

	}
	@Override
	public VoluntaryProvidentFund[] findByVpf(String sql, Object[] id) throws VoluntaryProvidentFundDaoException{
		// TODO Auto-generated method stub
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
				final String SQL =  sql;
			
			
				if (logger.isDebugEnabled()) {
					logger.debug( "Executing " + SQL);
				}
			
				// prepare statement
				stmt = conn.prepareStatement( SQL );
				stmt.setMaxRows( maxRows );
		
			
				for (int i=0; id!=null && i<id.length; i++ ) {
					stmt.setObject( i+1, id[i] );
				}
			
				rs = stmt.executeQuery();
			
				// fetch the results
				return fetchMultiResultsVpf(rs);
			}
			catch (Exception _e) {
				logger.error( "Exception: " + _e.getMessage(), _e );
				throw new VoluntaryProvidentFundDaoException( "Exception: " + _e.getMessage(), _e );
			}
			finally {
				ResourceManager.close(rs);
				ResourceManager.close(stmt);
				if (!isConnSupplied) {
					ResourceManager.close(conn);
				}
			
			}
			
		}
	
	
	}
	
	protected VoluntaryProvidentFund[] fetchMultiResultsVpf(ResultSet rs) throws SQLException
	{
		Collection<VoluntaryProvidentFund> resultList = new ArrayList<VoluntaryProvidentFund>();
		while (rs.next()) {
			VoluntaryProvidentFund dto = new VoluntaryProvidentFund();
			populateDtoVpf( dto, rs);
			resultList.add( dto );
		}
		
		VoluntaryProvidentFund ret[] = new VoluntaryProvidentFund[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDtoVpf(VoluntaryProvidentFund dto, ResultSet rs) throws SQLException
	{	
		dto.setVpf(rs.getString(COLUMN_VPF));
		
		
	}
	
	
	
	
	
	

}
