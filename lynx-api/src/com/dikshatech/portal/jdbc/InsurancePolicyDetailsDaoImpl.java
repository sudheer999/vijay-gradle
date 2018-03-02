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
import com.dikshatech.portal.dao.InsurancePolicyDetailsDao;
import com.dikshatech.portal.dto.InsurancePolicyDetails;
import com.dikshatech.portal.dto.InsurancePolicyDetailsPk;
import com.dikshatech.portal.exceptions.InsurancePolicyDetailsDaoException;

public class InsurancePolicyDetailsDaoImpl extends AbstractDAO implements InsurancePolicyDetailsDao
{
	protected static final Logger	logger				= Logger.getLogger(InsurancePolicyDetailsDaoImpl.class);
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, ESR_MAP_ID, NAME, GENDER, DOB, AGE, RELATIONSHIP, POLICY_REQ_ID, CARD_ID, ENDORSEMENT_NO FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESR_MAP_ID, NAME, GENDER, DOB, AGE, RELATIONSHIP, POLICY_REQ_ID, CARD_ID, ENDORSEMENT_NO ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESR_MAP_ID = ?, NAME = ?, GENDER = ?, DOB = ?, AGE = ?, RELATIONSHIP = ?, POLICY_REQ_ID = ?, CARD_ID = ?, ENDORSEMENT_NO = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column ESR_MAP_ID
	 */
	protected static final int COLUMN_ESR_MAP_ID = 2;

	/** 
	 * Index of column NAME
	 */
	protected static final int COLUMN_NAME = 3;

	/** 
	 * Index of column GENDER
	 */
	protected static final int COLUMN_GENDER = 4;

	/** 
	 * Index of column DOB
	 */
	protected static final int COLUMN_DOB = 5;

	/** 
	 * Index of column AGE
	 */
	protected static final int COLUMN_AGE = 6;

	/** 
	 * Index of column RELATIONSHIP
	 */
	protected static final int COLUMN_RELATIONSHIP = 7;

	/** 
	 * Index of column POLICY_REQ_ID
	 */
	protected static final int COLUMN_POLICY_REQ_ID = 8;

	/** 
	 * Index of column CARD_ID
	 */
	protected static final int COLUMN_CARD_ID = 9;

	/** 
	 * Index of column ENDORSEMENT_NO
	 */
	protected static final int COLUMN_ENDORSEMENT_NO = 10;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 10;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the INSURANCE_POLICY_DETAILS table.
	 */
	public InsurancePolicyDetailsPk insert(InsurancePolicyDetails dto) throws InsurancePolicyDetailsDaoException
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
			if (dto.isEsrMapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrMapId() );
			}
		
			stmt.setString( index++, dto.getName() );
			stmt.setString( index++, dto.getGender() );
			stmt.setDate(index++, dto.getDob()==null ? null : new java.sql.Date( dto.getDob().getTime() ) );
			if (dto.isAgeNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAge() );
			}
		
			stmt.setString( index++, dto.getRelationship() );
			if (dto.isPolicyReqIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getPolicyReqId() );
			}
		
			stmt.setString( index++, dto.getCardId() );
			stmt.setString( index++, dto.getEndorsementNo() );
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
			throw new InsurancePolicyDetailsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the INSURANCE_POLICY_DETAILS table.
	 */
	public void update(InsurancePolicyDetailsPk pk, InsurancePolicyDetails dto) throws InsurancePolicyDetailsDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto );
			stmt = conn.prepareStatement( SQL_UPDATE );
			int index=1;
			stmt.setInt( index++, dto.getId() );
			if (dto.isEsrMapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrMapId() );
			}
		
			stmt.setString( index++, dto.getName() );
			stmt.setString( index++, dto.getGender() );
			stmt.setDate(index++, dto.getDob()==null ? null : new java.sql.Date( dto.getDob().getTime() ) );
			if (dto.isAgeNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAge() );
			}
		
			stmt.setString( index++, dto.getRelationship() );
			if (dto.isPolicyReqIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getPolicyReqId() );
			}
		
			stmt.setString( index++, dto.getCardId() );
			stmt.setString( index++, dto.getEndorsementNo() );
			stmt.setInt( 11, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new InsurancePolicyDetailsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the INSURANCE_POLICY_DETAILS table.
	 */
	public void delete(InsurancePolicyDetailsPk pk) throws InsurancePolicyDetailsDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			logger.debug("Executing " + SQL_DELETE + " with PK: " + pk );
			stmt = conn.prepareStatement( SQL_DELETE );
			stmt.setInt( 1, pk.getId() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new InsurancePolicyDetailsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the INSURANCE_POLICY_DETAILS table that matches the specified primary-key value.
	 */
	public InsurancePolicyDetails findByPrimaryKey(InsurancePolicyDetailsPk pk) throws InsurancePolicyDetailsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ID = :id'.
	 */
	public InsurancePolicyDetails findByPrimaryKey(int id) throws InsurancePolicyDetailsDaoException
	{
		InsurancePolicyDetails ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria ''.
	 */
	public InsurancePolicyDetails[] findAll() throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ID = :id'.
	 */
	public InsurancePolicyDetails[] findWhereIdEquals(Integer id) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] { id } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public InsurancePolicyDetails[] findWhereEsrMapIdEquals(Integer esrMapId) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ESR_MAP_ID = ? ORDER BY ESR_MAP_ID", new Object[] { esrMapId } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'NAME = :name'.
	 */
	public InsurancePolicyDetails[] findWhereNameEquals(String name) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE NAME = ? ORDER BY NAME", new Object[] { name } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'GENDER = :gender'.
	 */
	public InsurancePolicyDetails[] findWhereGenderEquals(String gender) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE GENDER = ? ORDER BY GENDER", new Object[] { gender } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'DOB = :dob'.
	 */
	public InsurancePolicyDetails[] findWhereDobEquals(Date dob) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DOB = ? ORDER BY DOB", new Object[] { dob==null ? null : new java.sql.Date( dob.getTime() ) } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'AGE = :age'.
	 */
	public InsurancePolicyDetails[] findWhereAgeEquals(Integer age) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE AGE = ? ORDER BY AGE", new Object[] { age } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'RELATIONSHIP = :relationship'.
	 */
	public InsurancePolicyDetails[] findWhereRelationshipEquals(String relationship) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RELATIONSHIP = ? ORDER BY RELATIONSHIP", new Object[] { relationship } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'POLICY_REQ_ID = :policyReqId'.
	 */
	public InsurancePolicyDetails[] findWherePolicyReqIdEquals(Integer policyReqId) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE POLICY_REQ_ID = ? ORDER BY POLICY_REQ_ID", new Object[] { policyReqId } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'CARD_ID = :cardId'.
	 */
	public InsurancePolicyDetails[] findWhereCardIdEquals(String cardId) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CARD_ID = ? ORDER BY CARD_ID", new Object[] { cardId } );
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ENDORSEMENT_NO = :endorsementNo'.
	 */
	public InsurancePolicyDetails[] findWhereEndorsementNoEquals(String endorsementNo) throws InsurancePolicyDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ENDORSEMENT_NO = ? ORDER BY ENDORSEMENT_NO", new Object[] { endorsementNo } );
	}

	/**
	 * Method 'InsurancePolicyDetailsDaoImpl'
	 * 
	 */
	public InsurancePolicyDetailsDaoImpl()
	{
	}

	/**
	 * Method 'InsurancePolicyDetailsDaoImpl'
	 * 
	 * @param userConn
	 */
	public InsurancePolicyDetailsDaoImpl(final java.sql.Connection userConn)
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
		return "INSURANCE_POLICY_DETAILS";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected InsurancePolicyDetails fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			InsurancePolicyDetails dto = new InsurancePolicyDetails();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected InsurancePolicyDetails[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<InsurancePolicyDetails> resultList = new ArrayList<InsurancePolicyDetails>();
		while (rs.next()) {
			InsurancePolicyDetails dto = new InsurancePolicyDetails();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		InsurancePolicyDetails ret[] = new InsurancePolicyDetails[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(InsurancePolicyDetails dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setEsrMapId( rs.getInt( COLUMN_ESR_MAP_ID ) );
		if (rs.wasNull()) {
			dto.setEsrMapIdNull( true );
		}
		
		dto.setName( rs.getString( COLUMN_NAME ) );
		dto.setGender( rs.getString( COLUMN_GENDER ) );
		dto.setDob( rs.getDate(COLUMN_DOB ) );
		dto.setAge( rs.getInt( COLUMN_AGE ) );
		if (rs.wasNull()) {
			dto.setAgeNull( true );
		}
		
		dto.setRelationship( rs.getString( COLUMN_RELATIONSHIP ) );
		dto.setPolicyReqId( rs.getInt( COLUMN_POLICY_REQ_ID ) );
		if (rs.wasNull()) {
			dto.setPolicyReqIdNull( true );
		}
		
		dto.setCardId( rs.getString( COLUMN_CARD_ID ) );
		dto.setEndorsementNo( rs.getString( COLUMN_ENDORSEMENT_NO ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(InsurancePolicyDetails dto)
	{
	}

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the specified arbitrary SQL statement
	 */
	public InsurancePolicyDetails[] findByDynamicSelect(String sql, Object[] sqlParams) throws InsurancePolicyDetailsDaoException
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
		
		
			logger.debug("Executing " + SQL );
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
			_e.printStackTrace();
			throw new InsurancePolicyDetailsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the specified arbitrary SQL statement
	 */
	public InsurancePolicyDetails[] findByDynamicWhere(String sql, Object[] sqlParams) throws InsurancePolicyDetailsDaoException
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
		
		
			logger.debug("Executing " + SQL );
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
			_e.printStackTrace();
			throw new InsurancePolicyDetailsDaoException( "Exception: " + _e.getMessage(), _e );
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
