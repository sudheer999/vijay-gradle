/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.jdbc;

import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.factory.*;
import java.util.Date;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;
import java.sql.Connection;
import java.util.Collection;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class IssueCommentsDaoImpl extends AbstractDAO implements IssueCommentsDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( IssueCommentsDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, COMMENT, ATTACHMENT, COMMENT_DATE, IS_REPLY FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, COMMENT, ATTACHMENT, COMMENT_DATE, IS_REPLY ) VALUES ( ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, COMMENT = ?, ATTACHMENT = ?, COMMENT_DATE = ?, IS_REPLY = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column COMMENT
	 */
	protected static final int COLUMN_COMMENT = 2;

	/** 
	 * Index of column ATTACHMENT
	 */
	protected static final int COLUMN_ATTACHMENT = 3;

	/** 
	 * Index of column COMMENT_DATE
	 */
	protected static final int COLUMN_COMMENT_DATE = 4;

	/** 
	 * Index of column IS_REPLY
	 */
	protected static final int COLUMN_IS_REPLY = 5;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 5;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the ISSUE_COMMENTS table.
	 */
	public IssueCommentsPk insert(IssueComments dto) throws IssueCommentsDaoException
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
			stmt.setString( index++, dto.getComment() );
			stmt.setString( index++, dto.getAttachment() );
			stmt.setTimestamp(index++, dto.getCommentDate()==null ? null : new java.sql.Timestamp( dto.getCommentDate().getTime() ) );
			stmt.setShort( index++, dto.getIsReply() );
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
			throw new IssueCommentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the ISSUE_COMMENTS table.
	 */
	public void update(IssueCommentsPk pk, IssueComments dto) throws IssueCommentsDaoException
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
			stmt.setString( index++, dto.getComment() );
			stmt.setString( index++, dto.getAttachment() );
			stmt.setTimestamp(index++, dto.getCommentDate()==null ? null : new java.sql.Timestamp( dto.getCommentDate().getTime() ) );
			stmt.setShort( index++, dto.getIsReply() );
			stmt.setInt( 6, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new IssueCommentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the ISSUE_COMMENTS table.
	 */
	public void delete(IssueCommentsPk pk) throws IssueCommentsDaoException
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
			throw new IssueCommentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the ISSUE_COMMENTS table that matches the specified primary-key value.
	 */
	public IssueComments findByPrimaryKey(IssueCommentsPk pk) throws IssueCommentsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'ID = :id'.
	 */
	public IssueComments findByPrimaryKey(int id) throws IssueCommentsDaoException
	{
		IssueComments ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria ''.
	 */
	public IssueComments[] findAll() throws IssueCommentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'ID = :id'.
	 */
	public IssueComments[] findWhereIdEquals(int id) throws IssueCommentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'COMMENT = :comment'.
	 */
	public IssueComments[] findWhereCommentEquals(String comment) throws IssueCommentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENT = ? ORDER BY COMMENT", new Object[] { comment } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'ATTACHMENT = :attachment'.
	 */
	public IssueComments[] findWhereAttachmentEquals(String attachment) throws IssueCommentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ATTACHMENT = ? ORDER BY ATTACHMENT", new Object[] { attachment } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'COMMENT_DATE = :commentDate'.
	 */
	public IssueComments[] findWhereCommentDateEquals(Date commentDate) throws IssueCommentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENT_DATE = ? ORDER BY COMMENT_DATE", new Object[] { commentDate==null ? null : new java.sql.Timestamp( commentDate.getTime() ) } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'IS_REPLY = :isReply'.
	 */
	public IssueComments[] findWhereIsReplyEquals(short isReply) throws IssueCommentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE IS_REPLY = ? ORDER BY IS_REPLY", new Object[] {  new Short(isReply) } );
	}
	
	
	
	public ArrayList<String> getIssueComments(String sql) throws IssueCommentsDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> resultList = new ArrayList<String>();
		
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
			
			rs = stmt.executeQuery();
			if(rs!=null)
			{
				while (rs.next()) {
					resultList.add(rs.getString("ISSUE_COMMENTS"));
				}
			}
			
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new IssueCommentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return resultList;
	}

	/**
	 * Method 'IssueCommentsDaoImpl'
	 * 
	 */
	public IssueCommentsDaoImpl()
	{
	}

	/**
	 * Method 'IssueCommentsDaoImpl'
	 * 
	 * @param userConn
	 */
	public IssueCommentsDaoImpl(final java.sql.Connection userConn)
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
		return "ISSUE_COMMENTS";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected IssueComments fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			IssueComments dto = new IssueComments();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected IssueComments[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<IssueComments> resultList = new ArrayList<IssueComments>();
		while (rs.next()) {
			IssueComments dto = new IssueComments();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		IssueComments ret[] = new IssueComments[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(IssueComments dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setComment( rs.getString( COLUMN_COMMENT ) );
		dto.setAttachment( rs.getString( COLUMN_ATTACHMENT ) );
		dto.setCommentDate( rs.getTimestamp(COLUMN_COMMENT_DATE ) );
		dto.setIsReply( rs.getShort( COLUMN_IS_REPLY ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(IssueComments dto)
	{
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the specified arbitrary SQL statement
	 */
	public IssueComments[] findByDynamicSelect(String sql, Object[] sqlParams) throws IssueCommentsDaoException
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
			throw new IssueCommentsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the ISSUE_COMMENTS table that match the specified arbitrary SQL statement
	 */
	public IssueComments[] findByDynamicWhere(String sql, Object[] sqlParams) throws IssueCommentsDaoException
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
			throw new IssueCommentsDaoException( "Exception: " + _e.getMessage(), _e );
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
