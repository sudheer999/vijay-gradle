/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;
import com.dikshatech.portal.dao.TravelReqDao;
import com.dikshatech.portal.dto.TravelReq;
import com.dikshatech.portal.dto.TravelReqPk;
import com.dikshatech.portal.exceptions.TravelReqDaoException;

public class TravelReqDaoImpl extends AbstractDAO implements TravelReqDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( TravelReqDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, TL_REQ_ID, STATUS, ASSIGNED_TO, APP_LEVEL, ACTION_TYPE, COMMENT, CURRENCY, TOTAL_COST, MESSAGE_BODY, DATE_OF_COMPLETION FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, TL_REQ_ID, STATUS, ASSIGNED_TO, APP_LEVEL, ACTION_TYPE, COMMENT, CURRENCY, TOTAL_COST, MESSAGE_BODY, DATE_OF_COMPLETION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, TL_REQ_ID = ?, STATUS = ?, ASSIGNED_TO = ?, APP_LEVEL = ?, ACTION_TYPE = ?, COMMENT = ?, CURRENCY = ?, TOTAL_COST = ?, MESSAGE_BODY = ?, DATE_OF_COMPLETION = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column TL_REQ_ID
	 */
	protected static final int COLUMN_TL_REQ_ID = 2;

	/** 
	 * Index of column STATUS
	 */
	protected static final int COLUMN_STATUS = 3;

	/** 
	 * Index of column ASSIGNED_TO
	 */
	protected static final int COLUMN_ASSIGNED_TO = 4;

	/** 
	 * Index of column APP_LEVEL
	 */
	protected static final int COLUMN_APP_LEVEL = 5;

	/** 
	 * Index of column ACTION_TYPE
	 */
	protected static final int COLUMN_ACTION_TYPE = 6;

	/** 
	 * Index of column COMMENT
	 */
	protected static final int COLUMN_COMMENT = 7;

	/** 
	 * Index of column CURRENCY
	 */
	protected static final int COLUMN_CURRENCY = 8;

	/** 
	 * Index of column TOTAL_COST
	 */
	protected static final int COLUMN_TOTAL_COST = 9;

	/** 
	 * Index of column MESSAGE_BODY
	 */
	protected static final int COLUMN_MESSAGE_BODY = 10;

	/** 
	 * Index of column DATE_OF_COMPLETION
	 */
	protected static final int COLUMN_DATE_OF_COMPLETION = 11;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 11;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the TRAVEL_REQ table.
	 */
	public TravelReqPk insert(TravelReq dto) throws TravelReqDaoException
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
			if (dto.isTlReqIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getTlReqId() );
			}
		
			if (dto.isStatusNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getStatus() );
			}
		
			if (dto.isAssignedToNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAssignedTo() );
			}
		
			if (dto.isAppLevelNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAppLevel() );
			}
		
			if (dto.isActionTypeNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionType() );
			}
		
			stmt.setString( index++, dto.getComment() );
			stmt.setString( index++, dto.getCurrency() );
			if (dto.isTotalCostNull()) {
				stmt.setNull( index++, java.sql.Types.DOUBLE );
			} else {
				stmt.setDouble( index++, dto.getTotalCost() );
			}
		
			stmt.setString( index++, dto.getMessageBody() );
			stmt.setTimestamp(index++, dto.getDateOfCompletion()==null ? null : new java.sql.Timestamp( dto.getDateOfCompletion().getTime() ) );
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_INSERT + " with DTO: " + dto);
			}
		
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setId( rs.getInt( 1 ) );
			}
		
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TravelReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the TRAVEL_REQ table.
	 */
	public void update(TravelReqPk pk, TravelReq dto) throws TravelReqDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
		
			stmt = conn.prepareStatement( SQL_UPDATE );
			int index=1;
			stmt.setInt( index++, dto.getId() );
			if (dto.isTlReqIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getTlReqId() );
			}
		
			if (dto.isStatusNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getStatus() );
			}
		
			if (dto.isAssignedToNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAssignedTo() );
			}
		
			if (dto.isAppLevelNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAppLevel() );
			}
		
			if (dto.isActionTypeNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionType() );
			}
		
			stmt.setString( index++, dto.getComment() );
			stmt.setString( index++, dto.getCurrency() );
			if (dto.isTotalCostNull()) {
				stmt.setNull( index++, java.sql.Types.DOUBLE );
			} else {
				stmt.setDouble( index++, dto.getTotalCost() );
			}
		
			stmt.setString( index++, dto.getMessageBody() );
			stmt.setTimestamp(index++, dto.getDateOfCompletion()==null ? null : new java.sql.Timestamp( dto.getDateOfCompletion().getTime() ) );
			stmt.setInt( 12, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TravelReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the TRAVEL_REQ table.
	 */
	public void delete(TravelReqPk pk) throws TravelReqDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_DELETE + " with PK: " + pk);
			}
		
			stmt = conn.prepareStatement( SQL_DELETE );
			stmt.setInt( 1, pk.getId() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TravelReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the TRAVEL_REQ table that matches the specified primary-key value.
	 */
	public TravelReq findByPrimaryKey(TravelReqPk pk) throws TravelReqDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ID = :id'.
	 */
	public TravelReq findByPrimaryKey(int id) throws TravelReqDaoException
	{
		TravelReq ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria ''.
	 */
	public TravelReq[] findAll() throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ID = :id'.
	 */
	public TravelReq[] findWhereIdEquals(int id) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'TL_REQ_ID = :tlReqId'.
	 */
	public TravelReq[] findWhereTlReqIdEquals(int tlReqId) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TL_REQ_ID = ? ORDER BY TL_REQ_ID", new Object[] {  new Integer(tlReqId) } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'STATUS = :status'.
	 */
	public TravelReq[] findWhereStatusEquals(int status) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE STATUS = ? ORDER BY STATUS", new Object[] {  new Integer(status) } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public TravelReq[] findWhereAssignedToEquals(int assignedTo) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ASSIGNED_TO = ? ORDER BY ASSIGNED_TO", new Object[] {  new Integer(assignedTo) } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'APP_LEVEL = :appLevel'.
	 */
	public TravelReq[] findWhereAppLevelEquals(int appLevel) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE APP_LEVEL = ? ORDER BY APP_LEVEL", new Object[] {  new Integer(appLevel) } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ACTION_TYPE = :actionType'.
	 */
	public TravelReq[] findWhereActionTypeEquals(int actionType) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ACTION_TYPE = ? ORDER BY ACTION_TYPE", new Object[] {  new Integer(actionType) } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'COMMENT = :comment'.
	 */
	public TravelReq[] findWhereCommentEquals(String comment) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENT = ? ORDER BY COMMENT", new Object[] { comment } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'CURRENCY = :currency'.
	 */
	public TravelReq[] findWhereCurrencyEquals(String currency) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CURRENCY = ? ORDER BY CURRENCY", new Object[] { currency } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'TOTAL_COST = :totalCost'.
	 */
	public TravelReq[] findWhereTotalCostEquals(double totalCost) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TOTAL_COST = ? ORDER BY TOTAL_COST", new Object[] {  new Double(totalCost) } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public TravelReq[] findWhereMessageBodyEquals(String messageBody) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MESSAGE_BODY = ? ORDER BY MESSAGE_BODY", new Object[] { messageBody } );
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'DATE_OF_COMPLETION = :dateOfCompletion'.
	 */
	public TravelReq[] findWhereDateOfCompletionEquals(Date dateOfCompletion) throws TravelReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DATE_OF_COMPLETION = ? ORDER BY DATE_OF_COMPLETION", new Object[] { dateOfCompletion==null ? null : new java.sql.Timestamp( dateOfCompletion.getTime() ) } );
	}
	/**
	 * Returns all rows from the TRAVEL_REQ table that match the criteria
	 * 'TL_REQ_ID = :tlReqId'.
	 */
	public TravelReq[] findByTravel(int trvlreqId) throws TravelReqDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[]
		{ new Integer(trvlreqId) });
	}

	/**
	 * Method 'TravelReqDaoImpl'
	 * 
	 */
	public TravelReqDaoImpl()
	{
	}

	/**
	 * Method 'TravelReqDaoImpl'
	 * 
	 * @param userConn
	 */
	public TravelReqDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "TRAVEL_REQ";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected TravelReq fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			TravelReq dto = new TravelReq();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected TravelReq[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<TravelReq> resultList = new ArrayList<TravelReq>();
		while (rs.next()) {
			TravelReq dto = new TravelReq();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		TravelReq ret[] = new TravelReq[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(TravelReq dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setTlReqId( rs.getInt( COLUMN_TL_REQ_ID ) );
		if (rs.wasNull()) {
			dto.setTlReqIdNull( true );
		}
		
		dto.setStatus( rs.getInt( COLUMN_STATUS ) );
		if (rs.wasNull()) {
			dto.setStatusNull( true );
		}
		
		dto.setAssignedTo( rs.getInt( COLUMN_ASSIGNED_TO ) );
		if (rs.wasNull()) {
			dto.setAssignedToNull( true );
		}
		
		dto.setAppLevel( rs.getInt( COLUMN_APP_LEVEL ) );
		if (rs.wasNull()) {
			dto.setAppLevelNull( true );
		}
		
		dto.setActionType( rs.getInt( COLUMN_ACTION_TYPE ) );
		if (rs.wasNull()) {
			dto.setActionTypeNull( true );
		}
		
		dto.setComment( rs.getString( COLUMN_COMMENT ) );
		dto.setCurrency( rs.getString( COLUMN_CURRENCY ) );
		dto.setTotalCost( rs.getDouble( COLUMN_TOTAL_COST ) );
		if (rs.wasNull()) {
			dto.setTotalCostNull( true );
		}
		
		dto.setMessageBody( rs.getString( COLUMN_MESSAGE_BODY ) );
		dto.setDateOfCompletion( rs.getTimestamp(COLUMN_DATE_OF_COMPLETION ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(TravelReq dto)
	{
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the specified arbitrary SQL statement
	 */
	public TravelReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws TravelReqDaoException
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
			final String SQL = sql;
		
		
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
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TravelReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the specified arbitrary SQL statement
	 */
	public TravelReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws TravelReqDaoException
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
			final String SQL = SQL_SELECT + " WHERE " + sql;
		
		
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
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TravelReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	@Override
	public Object findMaxLevel(String sql, Object[] sqlParams) throws TravelReqDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			final String SQL = sql;

			if (logger.isDebugEnabled())
			{
				logger.debug("Executing " + SQL);
			}

			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);

			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++)
			{
				stmt.setObject(i + 1, sqlParams[i]);
			}

			rs = stmt.executeQuery();

			Object result = null;
			while (rs.next())
			{
				result = rs.getObject(1);
				
			}
			
			// fetch the results
			return result;
		}
		catch (Exception _e)
		{
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new TravelReqDaoException("Exception: " + _e.getMessage(), _e);
		}
		finally
		{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied)
			{
				ResourceManager.close(conn);
			}

		}
	}
	
	
	public void executeUpdate(String sql) throws TravelReqDaoException
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
			final String SQL = sql;
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			stmt.executeUpdate();
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TravelReqDaoException( "Exception: " + _e.getMessage(), _e );
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
