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
import org.apache.log4j.Logger;
import com.dikshatech.portal.dao.AccessibilityLevelDao;
import com.dikshatech.portal.dto.AccessibilityLevel;
import com.dikshatech.portal.dto.AccessibilityLevelPk;
import com.dikshatech.portal.exceptions.AccessibilityLevelDaoException;

public class AccessibilityLevelDaoImpl extends AbstractDAO implements AccessibilityLevelDao
{
	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;

	protected static final Logger	logger				= Logger.getLogger(AccessibilityLevelDaoImpl.class);

	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String		SQL_SELECT			= "SELECT ID, COMPANY_ID, REGION_ID, DIVISION_ID, DEPARTMENT_ID, USER_IDS, SAVE, VIEW, REMOVE, MODIFY, APPROVE, REJECT, ENABLE, DISABLE, RESEND_OFFER, GEN_OFFER, MARK_AS_EMP, NOSHOW, CANCEL, ROLLON, SUBMIT, DOWNLOAD, UPLOAD, EMAIL, ASSIGN,HOLD FROM " + getTableName() + "";

	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int				maxRows;

	/**
	 * SQL INSERT statement for this table
	 */
	protected final String		SQL_INSERT			= "INSERT INTO " + getTableName() + " ( ID, COMPANY_ID, REGION_ID, DIVISION_ID, DEPARTMENT_ID, USER_IDS, SAVE, VIEW, REMOVE, MODIFY, APPROVE, REJECT, ENABLE, DISABLE, RESEND_OFFER, GEN_OFFER, MARK_AS_EMP, NOSHOW, CANCEL, ROLLON, SUBMIT, DOWNLOAD, UPLOAD, EMAIL,ASSIGN,HOLD ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,? )";

	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String		SQL_UPDATE			= "UPDATE " + getTableName() + " SET ID = ?, COMPANY_ID = ?, REGION_ID = ?, DIVISION_ID = ?, DEPARTMENT_ID = ?, USER_IDS = ?, SAVE = ?, VIEW = ?, REMOVE = ?, MODIFY = ?, APPROVE = ?, REJECT = ?, ENABLE = ?, DISABLE = ?, RESEND_OFFER = ?, GEN_OFFER = ?, MARK_AS_EMP = ?, NOSHOW = ?, CANCEL = ?, ROLLON = ?, SUBMIT = ?, DOWNLOAD = ?, UPLOAD = ?, EMAIL = ?, ASSIGN=? HOLD=? WHERE ID = ?";

	/**
	 * SQL DELETE statement for this table
	 */
	protected final String		SQL_DELETE			= "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/**
	 * Index of column ID
	 */
	protected static final int	COLUMN_ID				= 1;

	/**
	 * Index of column COMPANY_ID
	 */
	protected static final int	COLUMN_COMPANY_ID		= 2;

	/**
	 * Index of column REGION_ID
	 */
	protected static final int	COLUMN_REGION_ID		= 3;

	/**
	 * Index of column DIVISION_ID
	 */
	protected static final int	COLUMN_DIVISION_ID		= 4;

	/**
	 * Index of column DEPARTMENT_ID
	 */
	protected static final int	COLUMN_DEPARTMENT_ID	= 5;

	/**
	 * Index of column USER_IDS
	 */
	protected static final int	COLUMN_USER_IDS		= 6;

	/**
	 * Index of column SAVE
	 */
	protected static final int	COLUMN_SAVE			= 7;

	/**
	 * Index of column VIEW
	 */
	protected static final int	COLUMN_VIEW			= 8;

	/**
	 * Index of column REMOVE
	 */
	protected static final int	COLUMN_REMOVE			= 9;

	/**
	 * Index of column MODIFY
	 */
	protected static final int	COLUMN_MODIFY			= 10;

	/**
	 * Index of column APPROVE
	 */
	protected static final int	COLUMN_APPROVE			= 11;

	/**
	 * Index of column REJECT
	 */
	protected static final int	COLUMN_REJECT			= 12;

	/**
	 * Index of column ENABLE
	 */
	protected static final int	COLUMN_ENABLE			= 13;

	/**
	 * Index of column DISABLE
	 */
	protected static final int	COLUMN_DISABLE			= 14;

	/**
	 * Index of column RESEND_OFFER
	 */
	protected static final int	COLUMN_RESEND_OFFER		= 15;

	/**
	 * Index of column GEN_OFFER
	 */
	protected static final int	COLUMN_GEN_OFFER		= 16;

	/**
	 * Index of column MARK_AS_EMP
	 */
	protected static final int	COLUMN_MARK_AS_EMP		= 17;
	/**
	 * Index of column NOSHOW
	 */
	protected static final int	COLUMN_NOSHOW			= 18;

	/**
	 * Index of column CANCEL
	 */
	protected static final int	COLUMN_CANCEL			= 19;

	/**
	 * Index of column ROLLON
	 */
	protected static final int	COLUMN_ROLLON			= 20;

	/**
	 * Index of column SUBMIT
	 */
	protected static final int	COLUMN_SUBMIT			= 21;

	/**
	 * Index of column DOWNLOAD
	 */
	protected static final int	COLUMN_DOWNLOAD		= 22;

	/**
	 * Index of column UPLOAD
	 */
	protected static final int	COLUMN_UPLOAD			= 23;

	/**
	 * Index of column EMAIL
	 */
	protected static final int	COLUMN_EMAIL			= 24;
	/**
	 * Index of column ASSIGN
	 */
	protected static final int	COLUMN_ASSIGN			= 25;
	/**
	 * Index of column HOLD
	 */
	protected static final int	COLUMN_HOLD			= 26;

	/**
	 * Number of columns
	 */
	protected static final int	NUMBER_OF_COLUMNS		= 26;

	/**
	 * Index of primary-key column ID
	 */
	protected static final int	PK_COLUMN_ID			= 1;

	/**
	 * Inserts a new row in the ACCESSIBILITY_LEVEL table.
	 */
	public AccessibilityLevelPk insert(AccessibilityLevel dto) throws AccessibilityLevelDaoException
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
			stmt.setString(index++, dto.getCompanyId());
			stmt.setString(index++, dto.getRegionId());
			stmt.setString(index++, dto.getDivisionId());
			stmt.setString(index++, dto.getDepartmentId());
			stmt.setString(index++, dto.getUserIds());
			if (dto.isSaveNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getSave());
			}

			if (dto.isViewNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getView());
			}

			if (dto.isRemoveNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getRemove());
			}

			if (dto.isModifyNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getModify());
			}

			if (dto.isApproveNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getApprove());
			}

			if (dto.isRejectNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getReject());
			}

			if (dto.isEnableNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getEnable());
			}

			if (dto.isDisableNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getDisable());
			}

			if (dto.isResendOfferNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getResendOffer());
			}

			if (dto.isGenOfferNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getGenOffer());
			}

			if (dto.isMarkAsEmpNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getMarkAsEmp());
			}

			if (dto.isNoshowNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getNoshow());
			}

			if (dto.isCancelNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getCancel());
			}

			if (dto.isRollonNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getRollon());
			}

			if (dto.isSubmitNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getSubmit());
			}

			if (dto.isDownloadNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getDownload());
			}

			if (dto.isUploadNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getUpload());
			}

			if (dto.isEmailNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getEmail());
			}
			if (dto.isAssignNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getAssign());
			}
			if (dto.isHoldNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getHold());
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
			throw new AccessibilityLevelDaoException("Exception: " + _e.getMessage(), _e);
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
	 * Updates a single row in the ACCESSIBILITY_LEVEL table.
	 */
	public void update(AccessibilityLevelPk pk, AccessibilityLevel dto) throws AccessibilityLevelDaoException
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
			stmt.setString(index++, dto.getCompanyId());
			stmt.setString(index++, dto.getRegionId());
			stmt.setString(index++, dto.getDivisionId());
			stmt.setString(index++, dto.getDepartmentId());
			stmt.setString(index++, dto.getUserIds());
			if (dto.isSaveNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getSave());
			}

			if (dto.isViewNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getView());
			}

			if (dto.isRemoveNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getRemove());
			}

			if (dto.isModifyNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getModify());
			}

			if (dto.isApproveNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getApprove());
			}

			if (dto.isRejectNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getReject());
			}

			if (dto.isEnableNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getEnable());
			}

			if (dto.isDisableNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getDisable());
			}

			if (dto.isResendOfferNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getResendOffer());
			}

			if (dto.isGenOfferNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getGenOffer());
			}

			if (dto.isMarkAsEmpNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getMarkAsEmp());
			}

			if (dto.isNoshowNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getNoshow());
			}

			if (dto.isCancelNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getCancel());
			}

			if (dto.isRollonNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getRollon());
			}

			if (dto.isSubmitNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getSubmit());
			}

			if (dto.isDownloadNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getDownload());
			}

			if (dto.isUploadNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getUpload());
			}

			if (dto.isEmailNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getEmail());
			}
			if (dto.isAssignNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getAssign());
			}
			if (dto.isHoldNull())
			{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setShort(index++, dto.getHold());
			}
			stmt.setInt(27, pk.getId());
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
			throw new AccessibilityLevelDaoException("Exception: " + _e.getMessage(), _e);
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
	 * Deletes a single row in the ACCESSIBILITY_LEVEL table.
	 */
	public void delete(AccessibilityLevelPk pk) throws AccessibilityLevelDaoException
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
			throw new AccessibilityLevelDaoException("Exception: " + _e.getMessage(), _e);
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
	 * Returns the rows from the ACCESSIBILITY_LEVEL table that matches the specified primary-key
	 * value.
	 */
	public AccessibilityLevel findByPrimaryKey(AccessibilityLevelPk pk) throws AccessibilityLevelDaoException
	{
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'ID = :id'.
	 */
	public AccessibilityLevel findByPrimaryKey(int id) throws AccessibilityLevelDaoException
	{
		AccessibilityLevel ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[ ]
		{ new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria ''.
	 */
	public AccessibilityLevel[ ] findAll() throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'ID = :id'.
	 */
	public AccessibilityLevel[ ] findWhereIdEquals(int id) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[ ]
		{ new Integer(id) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'COMPANY_ID =
	 * :companyId'.
	 */
	public AccessibilityLevel[ ] findWhereCompanyIdEquals(String companyId) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE COMPANY_ID = ? ORDER BY COMPANY_ID", new Object[ ]
		{ companyId });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'REGION_ID =
	 * :regionId'.
	 */
	public AccessibilityLevel[ ] findWhereRegionIdEquals(String regionId) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE REGION_ID = ? ORDER BY REGION_ID", new Object[ ]
		{ regionId });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'DIVISION_ID =
	 * :divisionId'.
	 */
	public AccessibilityLevel[ ] findWhereDivisionIdEquals(String divisionId) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE DIVISION_ID = ? ORDER BY DIVISION_ID", new Object[ ]
		{ divisionId });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'DEPARTMENT_ID =
	 * :departmentId'.
	 */
	public AccessibilityLevel[ ] findWhereDepartmentIdEquals(String departmentId) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE DEPARTMENT_ID = ? ORDER BY DEPARTMENT_ID", new Object[ ]
		{ departmentId });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'USER_IDS =
	 * :userIds'.
	 */
	public AccessibilityLevel[ ] findWhereUserIdsEquals(String userIds) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_IDS = ? ORDER BY USER_IDS", new Object[ ]
		{ userIds });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'SAVE = :save'.
	 */
	public AccessibilityLevel[ ] findWhereSaveEquals(short save) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE SAVE = ? ORDER BY SAVE", new Object[ ]
		{ new Short(save) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'VIEW = :view'.
	 */
	public AccessibilityLevel[ ] findWhereViewEquals(short view) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE VIEW = ? ORDER BY VIEW", new Object[ ]
		{ new Short(view) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'REMOVE =
	 * :remove'.
	 */
	public AccessibilityLevel[ ] findWhereRemoveEquals(short remove) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE REMOVE = ? ORDER BY REMOVE", new Object[ ]
		{ new Short(remove) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'MODIFY =
	 * :modify'.
	 */
	public AccessibilityLevel[ ] findWhereModifyEquals(short modify) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE MODIFY = ? ORDER BY MODIFY", new Object[ ]
		{ new Short(modify) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'APPROVE =
	 * :approve'.
	 */
	public AccessibilityLevel[ ] findWhereApproveEquals(short approve) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE APPROVE = ? ORDER BY APPROVE", new Object[ ]
		{ new Short(approve) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'REJECT =
	 * :reject'.
	 */
	public AccessibilityLevel[ ] findWhereRejectEquals(short reject) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE REJECT = ? ORDER BY REJECT", new Object[ ]
		{ new Short(reject) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'ENABLE =
	 * :enable'.
	 */
	public AccessibilityLevel[ ] findWhereEnableEquals(short enable) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE ENABLE = ? ORDER BY ENABLE", new Object[ ]
		{ new Short(enable) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'DISABLE =
	 * :disable'.
	 */
	public AccessibilityLevel[ ] findWhereDisableEquals(short disable) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE DISABLE = ? ORDER BY DISABLE", new Object[ ]
		{ new Short(disable) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'RESEND_OFFER =
	 * :resendOffer'.
	 */
	public AccessibilityLevel[ ] findWhereResendOfferEquals(short resendOffer) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE RESEND_OFFER = ? ORDER BY RESEND_OFFER", new Object[ ]
		{ new Short(resendOffer) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'GEN_OFFER =
	 * :genOffer'.
	 */
	public AccessibilityLevel[ ] findWhereGenOfferEquals(short genOffer) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE GEN_OFFER = ? ORDER BY GEN_OFFER", new Object[ ]
		{ new Short(genOffer) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'MARK_AS_EMP =
	 * :markAsEmp'.
	 */
	public AccessibilityLevel[ ] findWhereMarkAsEmpEquals(short markAsEmp) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE MARK_AS_EMP = ? ORDER BY MARK_AS_EMP", new Object[ ]
		{ new Short(markAsEmp) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'NOSHOW =
	 * :noshow'.
	 */
	public AccessibilityLevel[ ] findWhereNoshowEquals(short noshow) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE NOSHOW = ? ORDER BY NOSHOW", new Object[ ]
		{ new Short(noshow) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'ASSIGN =
	 * :assign'.
	 */
	public AccessibilityLevel[ ] findWhereAssignEquals(short assign) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE ASSIGN = ? ORDER BY ASSIGN", new Object[ ]
		{ new Short(assign) });
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'HOLD = :hold'.
	 */
	public AccessibilityLevel[ ] findWhereHoldEquals(short hold) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect(SQL_SELECT + " WHERE HOLD = ? ORDER BY HOLD", new Object[ ]
		{ new Short(hold) });
	}

	/**
	 * Method 'AccessibilityLevelDaoImpl'
	 */
	public AccessibilityLevelDaoImpl()
	{
	}

	/**
	 * Method 'AccessibilityLevelDaoImpl'
	 * 
	 * @param userConn
	 */
	public AccessibilityLevelDaoImpl(final java.sql.Connection userConn)
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
		return "ACCESSIBILITY_LEVEL";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected AccessibilityLevel fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next())
		{
			AccessibilityLevel dto = new AccessibilityLevel();
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
	protected AccessibilityLevel[ ] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<AccessibilityLevel> resultList = new ArrayList<AccessibilityLevel>();
		while (rs.next())
		{
			AccessibilityLevel dto = new AccessibilityLevel();
			populateDto(dto, rs);
			resultList.add(dto);
		}

		AccessibilityLevel ret[] = new AccessibilityLevel[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(AccessibilityLevel dto, ResultSet rs) throws SQLException
	{
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setCompanyId(rs.getString(COLUMN_COMPANY_ID));
		dto.setRegionId(rs.getString(COLUMN_REGION_ID));
		dto.setDivisionId(rs.getString(COLUMN_DIVISION_ID));
		dto.setDepartmentId(rs.getString(COLUMN_DEPARTMENT_ID));
		dto.setUserIds(rs.getString(COLUMN_USER_IDS));
		dto.setSave(rs.getShort(COLUMN_SAVE));
		if (rs.wasNull())
		{
			dto.setSaveNull(true);
		}

		dto.setView(rs.getShort(COLUMN_VIEW));
		if (rs.wasNull())
		{
			dto.setViewNull(true);
		}

		dto.setRemove(rs.getShort(COLUMN_REMOVE));
		if (rs.wasNull())
		{
			dto.setRemoveNull(true);
		}

		dto.setModify(rs.getShort(COLUMN_MODIFY));
		if (rs.wasNull())
		{
			dto.setModifyNull(true);
		}

		dto.setApprove(rs.getShort(COLUMN_APPROVE));
		if (rs.wasNull())
		{
			dto.setApproveNull(true);
		}

		dto.setReject(rs.getShort(COLUMN_REJECT));
		if (rs.wasNull())
		{
			dto.setRejectNull(true);
		}

		dto.setEnable(rs.getShort(COLUMN_ENABLE));
		if (rs.wasNull())
		{
			dto.setEnableNull(true);
		}

		dto.setDisable(rs.getShort(COLUMN_DISABLE));
		if (rs.wasNull())
		{
			dto.setDisableNull(true);
		}

		dto.setResendOffer(rs.getShort(COLUMN_RESEND_OFFER));
		if (rs.wasNull())
		{
			dto.setResendOfferNull(true);
		}

		dto.setGenOffer(rs.getShort(COLUMN_GEN_OFFER));
		if (rs.wasNull())
		{
			dto.setGenOfferNull(true);
		}

		dto.setMarkAsEmp(rs.getShort(COLUMN_MARK_AS_EMP));
		if (rs.wasNull())
		{
			dto.setMarkAsEmpNull(true);
		}

		dto.setNoshow(rs.getShort(COLUMN_NOSHOW));
		if (rs.wasNull())
		{
			dto.setNoshowNull(true);
		}

		dto.setCancel(rs.getShort(COLUMN_CANCEL));
		if (rs.wasNull())
		{
			dto.setCancelNull(true);
		}

		dto.setRollon(rs.getShort(COLUMN_ROLLON));
		if (rs.wasNull())
		{
			dto.setRollonNull(true);
		}

		dto.setSubmit(rs.getShort(COLUMN_SUBMIT));
		if (rs.wasNull())
		{
			dto.setSubmitNull(true);
		}

		dto.setDownload(rs.getShort(COLUMN_DOWNLOAD));
		if (rs.wasNull())
		{
			dto.setDownloadNull(true);
		}

		dto.setUpload(rs.getShort(COLUMN_UPLOAD));
		if (rs.wasNull())
		{
			dto.setUploadNull(true);
		}

		dto.setEmail(rs.getShort(COLUMN_EMAIL));
		if (rs.wasNull())
		{
			dto.setEmailNull(true);
		}
		dto.setAssign(rs.getShort(COLUMN_ASSIGN));
		if (rs.wasNull())
		{
			dto.setAssignNull(true);
		}
		dto.setHold(rs.getShort(COLUMN_HOLD));
		if (rs.wasNull())
		{
			dto.setHoldNull(true);
		}
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(AccessibilityLevel dto)
	{
	}

	/**
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the specified arbitrary SQL
	 * statement
	 */
	public AccessibilityLevel[ ] findByDynamicSelect(String sql, Object[ ] sqlParams) throws AccessibilityLevelDaoException
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
			throw new AccessibilityLevelDaoException("Exception: " + _e.getMessage(), _e);
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
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the specified arbitrary SQL
	 * statement
	 */
	public AccessibilityLevel[ ] findByDynamicWhere(String sql, Object[ ] sqlParams) throws AccessibilityLevelDaoException
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
			throw new AccessibilityLevelDaoException("Exception: " + _e.getMessage(), _e);
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

}