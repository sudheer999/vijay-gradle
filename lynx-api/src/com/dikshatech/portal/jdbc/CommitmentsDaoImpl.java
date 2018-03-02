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

public class CommitmentsDaoImpl extends AbstractDAO implements CommitmentsDao
{
	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;

	protected static final Logger	logger			= Logger.getLogger(CommitmentsDaoImpl.class);

	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String		SQL_SELECT		= "SELECT ID, COMMENTS, DATE_PICKER, CANDIDATE_ID, USER_ID_EMP FROM " + getTableName() + "";

	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int				maxRows;

	/**
	 * SQL INSERT statement for this table
	 */
	protected final String		SQL_INSERT		= "INSERT INTO " + getTableName() + " ( ID, COMMENTS, DATE_PICKER, CANDIDATE_ID, USER_ID_EMP ) VALUES ( ?, ?, ?, ?, ? )";

	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String		SQL_UPDATE		= "UPDATE " + getTableName() + " SET ID = ?, COMMENTS = ?, DATE_PICKER = ?, CANDIDATE_ID = ?, USER_ID_EMP = ? WHERE ID = ?";

	/**
	 * SQL DELETE statement for this table
	 */
	protected final String		SQL_DELETE		= "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/**
	 * Index of column ID
	 */
	protected static final int	COLUMN_ID			= 1;

	/**
	 * Index of column COMMENTS
	 */
	protected static final int	COLUMN_COMMENTS	= 2;

	/**
	 * Index of column DATE_PICKER
	 */
	protected static final int	COLUMN_DATE_PICKER	= 3;

	/**
	 * Index of column CANDIDATE_ID
	 */
	protected static final int	COLUMN_CANDIDATE_ID	= 4;

	/**
	 * Index of column USER_ID_EMP
	 */
	protected static final int	COLUMN_USER_ID_EMP	= 5;

	/**
	 * Number of columns
	 */
	protected static final int	NUMBER_OF_COLUMNS	= 5;

	/**
	 * Index of primary-key column ID
	 */
	protected static final int	PK_COLUMN_ID		= 1;

	/**
	 * Inserts a new row in the COMMITMENTS table.
	 */
	public CommitmentsPk insert(Commitments dto) throws CommitmentsDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setString(index++, dto.getComments());
			stmt.setDate(index++, dto.getDatePicker() == null ? null : new java.sql.Date(dto.getDatePicker().getTime()));
			if (dto.getCandidateId() == 0)
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setInt(index++, dto.getCandidateId());
			}

			if (dto.getUserIdEmp() == 0)
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setInt(index++, dto.getUserIdEmp());
			}

			if (logger.isDebugEnabled())
			{
				logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			}

			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled())
			{
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}

			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next())
			{
				dto.setId(rs.getInt(1));
			}

			reset(dto);
			return dto.createPk();
		} catch (Exception _e)
		{
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new CommitmentsDaoException("Exception: " + _e.getMessage(), _e);
		}
		finally
		{
			ResourceManager.close(stmt);
			if (!isConnSupplied)
			{
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Updates a single row in the COMMITMENTS table.
	 */
	public void update(CommitmentsPk pk, Commitments dto) throws CommitmentsDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;

		try
		{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			if (logger.isDebugEnabled())
			{
				logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			}

			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setString(index++, dto.getComments());
			stmt.setDate(index++, dto.getDatePicker() == null ? null : new java.sql.Date(dto.getDatePicker().getTime()));
			if (dto.getCandidateId() == 0)
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setInt(index++, dto.getCandidateId());
			}

			if (dto.getUserIdEmp() == 0)
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setInt(index++, dto.getUserIdEmp());
			}

			stmt.setInt(6, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled())
			{
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}

		} catch (Exception _e)
		{
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new CommitmentsDaoException("Exception: " + _e.getMessage(), _e);
		}
		finally
		{
			ResourceManager.close(stmt);
			if (!isConnSupplied)
			{
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Deletes a single row in the COMMITMENTS table.
	 */
	public void delete(CommitmentsPk pk) throws CommitmentsDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;

		try
		{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			if (logger.isDebugEnabled())
			{
				logger.debug("Executing " + SQL_DELETE + " with PK: " + pk);
			}

			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, pk.getId());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled())
			{
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}

		} catch (Exception _e)
		{
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new CommitmentsDaoException("Exception: " + _e.getMessage(), _e);
		}
		finally
		{
			ResourceManager.close(stmt);
			if (!isConnSupplied)
			{
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Returns the rows from the COMMITMENTS table that matches the specified primary-key value.
	 */
	public Commitments findByPrimaryKey(CommitmentsPk pk) throws CommitmentsDaoException
	{
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the criteria 'ID = :id'.
	 */
	public Commitments findByPrimaryKey(int id) throws CommitmentsDaoException
	{
		Commitments ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[ ]
		{ new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the criteria ''.
	 */
	public Commitments[ ] findAll() throws CommitmentsDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the criteria 'ID = :id'.
	 */
	public Commitments[ ] findWhereIdEquals(int id) throws CommitmentsDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[ ]
		{ new Integer(id) });
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the criteria 'COMMENTS = :comments'.
	 */
	public Commitments[ ] findWhereCommentsEquals(String comments) throws CommitmentsDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE COMMENTS = ? ORDER BY COMMENTS", new Object[ ]
		{ comments });
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the criteria 'DATE_PICKER =
	 * :datePicker'.
	 */
	public Commitments[ ] findWhereDatePickerEquals(Date datePicker) throws CommitmentsDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE DATE_PICKER = ? ORDER BY DATE_PICKER", new Object[ ]
		{ datePicker == null ? null : new java.sql.Date(datePicker.getTime()) });
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the criteria 'CANDIDATE_ID =
	 * :candidateId'.
	 */
	public Commitments[ ] findWhereCandidateIdEquals(int candidateId) throws CommitmentsDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE CANDIDATE_ID = ? ORDER BY CANDIDATE_ID", new Object[ ]
		{ new Integer(candidateId) });
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the criteria 'USER_ID_EMP =
	 * :userIdEmp'.
	 */
	public Commitments[ ] findWhereUserIdEmpEquals(int userIdEmp) throws CommitmentsDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID_EMP = ? ORDER BY USER_ID_EMP", new Object[ ]
		{ new Integer(userIdEmp) });
	}

	/**
	 * Method 'CommitmentsDaoImpl'
	 */
	public CommitmentsDaoImpl()
	{
	}

	/**
	 * Method 'CommitmentsDaoImpl'
	 * 
	 * @param userConn
	 */
	public CommitmentsDaoImpl(final java.sql.Connection userConn)
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
		return "COMMITMENTS";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected Commitments fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next())
		{
			Commitments dto = new Commitments();
			populateDto(dto, rs);
			return dto;
		}
		else
		{
			return null;
		}

	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected Commitments[ ] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<Commitments> resultList = new ArrayList<Commitments>();
		while (rs.next())
		{
			Commitments dto = new Commitments();
			populateDto(dto, rs);
			resultList.add(dto);
		}

		Commitments ret[] = new Commitments[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Commitments dto, ResultSet rs) throws SQLException
	{
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setComments(rs.getString(COLUMN_COMMENTS));
		dto.setDatePicker(rs.getDate(COLUMN_DATE_PICKER));
		dto.setCandidateId(rs.getInt(COLUMN_CANDIDATE_ID));
		if (rs.wasNull())
		{
			dto.setCandidateIdNull(true);
		}

		dto.setUserIdEmp(rs.getInt(COLUMN_USER_ID_EMP));
		if (rs.wasNull())
		{
			dto.setUserIdEmpNull(true);
		}

	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Commitments dto)
	{
	}

	/**
	 * Returns all rows from the COMMITMENTS table that match the specified arbitrary SQL statement
	 */
	public Commitments[ ] findByDynamicSelect(String sql, Object[ ] sqlParams) throws CommitmentsDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			// get the user-specified connection or get a connection from the ResourceManager
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

			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e)
		{
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new CommitmentsDaoException("Exception: " + _e.getMessage(), _e);
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

	/**
	 * Returns all rows from the COMMITMENTS table that match the specified arbitrary SQL statement
	 */
	public Commitments[ ] findByDynamicWhere(String sql, Object[ ] sqlParams) throws CommitmentsDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;

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

			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e)
		{
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new CommitmentsDaoException("Exception: " + _e.getMessage(), _e);
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

	/**
	 * Dynamic Deletes a single row in the COMMITMENTS table.
	 */
	public void dynamicDelete(int id) throws CommitmentsDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;

		try
		{
			String SQL_DYNAMIC_DELETE = "DELETE  FROM COMMITMENTS WHERE CANDIDATE_ID=?";
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_DYNAMIC_DELETE);
			stmt.setInt(1, id);
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled())
			{
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}

		} catch (Exception _e)
		{
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new CommitmentsDaoException("Exception: " + _e.getMessage(), _e);
		}
		finally
		{
			ResourceManager.close(stmt);
			if (!isConnSupplied)
			{
				ResourceManager.close(conn);
			}

		}

	}
}