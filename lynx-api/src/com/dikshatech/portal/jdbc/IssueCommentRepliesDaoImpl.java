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

public class IssueCommentRepliesDaoImpl extends AbstractDAO implements IssueCommentRepliesDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( IssueCommentRepliesDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, REPLY, REPLY_DATE, REPLIED_BY, COMMENT_ID FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, REPLY, REPLY_DATE, REPLIED_BY, COMMENT_ID ) VALUES ( ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, REPLY = ?, REPLY_DATE = ?, REPLIED_BY = ?, COMMENT_ID = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column REPLY
	 */
	protected static final int COLUMN_REPLY = 2;

	/** 
	 * Index of column REPLY_DATE
	 */
	protected static final int COLUMN_REPLY_DATE = 3;

	/** 
	 * Index of column REPLIED_BY
	 */
	protected static final int COLUMN_REPLIED_BY = 4;

	/** 
	 * Index of column COMMENT_ID
	 */
	protected static final int COLUMN_COMMENT_ID = 5;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 5;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the ISSUE_COMMENT_REPLIES table.
	 */
	public IssueCommentRepliesPk insert(IssueCommentReplies dto) throws IssueCommentRepliesDaoException
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
			stmt.setString( index++, dto.getReply() );
			stmt.setTimestamp(index++, dto.getReplyDate()==null ? null : new java.sql.Timestamp( dto.getReplyDate().getTime() ) );
			stmt.setInt( index++, dto.getRepliedBy() );
			stmt.setInt( index++, dto.getCommentId() );
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
			throw new IssueCommentRepliesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the ISSUE_COMMENT_REPLIES table.
	 */
	public void update(IssueCommentRepliesPk pk, IssueCommentReplies dto) throws IssueCommentRepliesDaoException
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
			stmt.setString( index++, dto.getReply() );
			stmt.setTimestamp(index++, dto.getReplyDate()==null ? null : new java.sql.Timestamp( dto.getReplyDate().getTime() ) );
			stmt.setInt( index++, dto.getRepliedBy() );
			stmt.setInt( index++, dto.getCommentId() );
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
			throw new IssueCommentRepliesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the ISSUE_COMMENT_REPLIES table.
	 */
	public void delete(IssueCommentRepliesPk pk) throws IssueCommentRepliesDaoException
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
			throw new IssueCommentRepliesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the ISSUE_COMMENT_REPLIES table that matches the specified primary-key value.
	 */
	public IssueCommentReplies findByPrimaryKey(IssueCommentRepliesPk pk) throws IssueCommentRepliesDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'ID = :id'.
	 */
	public IssueCommentReplies findByPrimaryKey(int id) throws IssueCommentRepliesDaoException
	{
		IssueCommentReplies ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria ''.
	 */
	public IssueCommentReplies[] findAll() throws IssueCommentRepliesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'COMMENT_ID = :commentId'.
	 */
	public IssueCommentReplies[] findByIssueComments(int commentId) throws IssueCommentRepliesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENT_ID = ?", new Object[] {  new Integer(commentId) } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'ID = :id'.
	 */
	public IssueCommentReplies[] findWhereIdEquals(int id) throws IssueCommentRepliesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'REPLY = :reply'.
	 */
	public IssueCommentReplies[] findWhereReplyEquals(String reply) throws IssueCommentRepliesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REPLY = ? ORDER BY REPLY", new Object[] { reply } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'REPLY_DATE = :replyDate'.
	 */
	public IssueCommentReplies[] findWhereReplyDateEquals(Date replyDate) throws IssueCommentRepliesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REPLY_DATE = ? ORDER BY REPLY_DATE", new Object[] { replyDate==null ? null : new java.sql.Timestamp( replyDate.getTime() ) } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'REPLIED_BY = :repliedBy'.
	 */
	public IssueCommentReplies[] findWhereRepliedByEquals(int repliedBy) throws IssueCommentRepliesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REPLIED_BY = ? ORDER BY REPLIED_BY", new Object[] {  new Integer(repliedBy) } );
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'COMMENT_ID = :commentId'.
	 */
	public IssueCommentReplies[] findWhereCommentIdEquals(int commentId) throws IssueCommentRepliesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENT_ID = ? ORDER BY COMMENT_ID", new Object[] {  new Integer(commentId) } );
	}

	/**
	 * Method 'IssueCommentRepliesDaoImpl'
	 * 
	 */
	public IssueCommentRepliesDaoImpl()
	{
	}

	/**
	 * Method 'IssueCommentRepliesDaoImpl'
	 * 
	 * @param userConn
	 */
	public IssueCommentRepliesDaoImpl(final java.sql.Connection userConn)
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
		return "ISSUE_COMMENT_REPLIES";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected IssueCommentReplies fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			IssueCommentReplies dto = new IssueCommentReplies();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected IssueCommentReplies[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<IssueCommentReplies> resultList = new ArrayList<IssueCommentReplies>();
		while (rs.next()) {
			IssueCommentReplies dto = new IssueCommentReplies();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		IssueCommentReplies ret[] = new IssueCommentReplies[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(IssueCommentReplies dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setReply( rs.getString( COLUMN_REPLY ) );
		dto.setReplyDate( rs.getTimestamp(COLUMN_REPLY_DATE ) );
		dto.setRepliedBy( rs.getInt( COLUMN_REPLIED_BY ) );
		dto.setCommentId( rs.getInt( COLUMN_COMMENT_ID ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(IssueCommentReplies dto)
	{
	}

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the specified arbitrary SQL statement
	 */
	public IssueCommentReplies[] findByDynamicSelect(String sql, Object[] sqlParams) throws IssueCommentRepliesDaoException
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
			throw new IssueCommentRepliesDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the specified arbitrary SQL statement
	 */
	public IssueCommentReplies[] findByDynamicWhere(String sql, Object[] sqlParams) throws IssueCommentRepliesDaoException
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
			throw new IssueCommentRepliesDaoException( "Exception: " + _e.getMessage(), _e );
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
