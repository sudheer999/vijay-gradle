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
import java.util.List;
import org.apache.log4j.Logger;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.exceptions.DocumentsDaoException;

public class DocumentsDaoImpl extends AbstractDAO implements DocumentsDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( DocumentsDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, FILENAME, DOCTYPE, DESCRIPTIONS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, FILENAME, DOCTYPE, DESCRIPTIONS ) VALUES ( ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, FILENAME = ?, DOCTYPE = ?, DESCRIPTIONS = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column FILENAME
	 */
	protected static final int COLUMN_FILENAME = 2;

	/** 
	 * Index of column DOCTYPE
	 */
	protected static final int COLUMN_DOCTYPE = 3;

	/** 
	 * Index of column DESCRIPTIONS
	 */
	protected static final int COLUMN_DESCRIPTIONS = 4;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 4;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the DOCUMENTS table.
	 */
	public DocumentsPk insert(Documents dto) throws DocumentsDaoException
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
			stmt.setString( index++, dto.getFilename() );
			stmt.setString( index++, dto.getDoctype() );
			stmt.setString( index++, dto.getDescriptions() );
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
			throw new DocumentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the DOCUMENTS table.
	 */
	public void update(DocumentsPk pk, Documents dto) throws DocumentsDaoException
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
			stmt.setString( index++, dto.getFilename() );
			stmt.setString( index++, dto.getDoctype() );
			stmt.setString( index++, dto.getDescriptions() );
			stmt.setInt( 5, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new DocumentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the DOCUMENTS table.
	 */
	public void delete(DocumentsPk pk) throws DocumentsDaoException
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
			throw new DocumentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the DOCUMENTS table that matches the specified primary-key value.
	 */
	public Documents findByPrimaryKey(DocumentsPk pk) throws DocumentsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the DOCUMENTS table that match the criteria 'ID = :id'.
	 */
	public Documents findByPrimaryKey(int id) throws DocumentsDaoException
	{
		Documents ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the DOCUMENTS table that match the criteria ''.
	 */
	public Documents[] findAll() throws DocumentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the DOCUMENTS table that match the criteria 'ID = :id'.
	 */
	public Documents[] findWhereIdEquals(int id) throws DocumentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the DOCUMENTS table that match the criteria 'FILENAME = :filename'.
	 */
	public Documents[] findWhereFilenameEquals(String filename) throws DocumentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE FILENAME = ? ORDER BY FILENAME", new Object[] { filename } );
	}

	/** 
	 * Returns all rows from the DOCUMENTS table that match the criteria 'DOCTYPE = :doctype'.
	 */
	public Documents[] findWhereDoctypeEquals(String doctype) throws DocumentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DOCTYPE = ? ORDER BY DOCTYPE", new Object[] { doctype } );
	}

	/** 
	 * Returns all rows from the DOCUMENTS table that match the criteria 'DESCRIPTIONS = :descriptions'.
	 */
	public Documents[] findWhereDescriptionsEquals(byte[] descriptions) throws DocumentsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DESCRIPTIONS = ? ORDER BY DESCRIPTIONS", new Object[] { descriptions } );
	}

	/**
	 * Method 'DocumentsDaoImpl'
	 * 
	 */
	public DocumentsDaoImpl()
	{
	}

	/**
	 * Method 'DocumentsDaoImpl'
	 * 
	 * @param userConn
	 */
	public DocumentsDaoImpl(final java.sql.Connection userConn)
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
		return "DOCUMENTS";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected Documents fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Documents dto = new Documents();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected Documents[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<Documents> resultList = new ArrayList<Documents>();
		while (rs.next()) {
			Documents dto = new Documents();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Documents ret[] = new Documents[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Documents dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setFilename( rs.getString( COLUMN_FILENAME ) );
		dto.setDoctype( rs.getString( COLUMN_DOCTYPE ) );
		dto.setDescriptions( rs.getString( COLUMN_DESCRIPTIONS ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Documents dto)
	{
	}

	/** 
	 * Returns all rows from the DOCUMENTS table that match the specified arbitrary SQL statement
	 */
	public Documents[] findByDynamicSelect(String sql, Object[] sqlParams) throws DocumentsDaoException
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
			throw new DocumentsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the DOCUMENTS table that match the specified arbitrary SQL statement
	 */
	public Documents[] findByDynamicWhere(String sql, Object[] sqlParams) throws DocumentsDaoException
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
			throw new DocumentsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Download All Employee Details 
	 */
	
	
	public List<String[]> findinternalReportData(){
		
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String[]> resultList = new ArrayList<String[]>();
		
		try{
			
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			
			//final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME,ED.DEGREE_COURSE,F.CTC FROM PROFILE_INFO PF JOIN USERS U ON PF.ID = U.PROFILE_ID JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID JOIN LEVELS L ON L.ID = U.LEVEL_ID JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID"; 
			
			//final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME,CD.CH_CODE_NAME,ED.DEGREE_COURSE,F.CTC FROM ROFILE_INFO PF JOIN USERS U ON PF.ID = U.PROFILE_ID JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID JOIN LEVELS L ON L.ID = U.LEVEL_ID JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID";
			
			//final String SQL = "SELECT U.ID, U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME,CD.CH_CODE_NAME, ED.DEGREE_COURSE, F.CTC FROM  USERS U JOIN PROFILE_INFO PF ON PF.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID LEFT JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID LEFT JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID LEFT JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID WHERE PF.ID > 3 AND RON.CURRENT = 1";
			
			//final String SQL ="SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME AS PROJECT_NAME,CD.CH_CODE_NAME, ED.DEGREE_COURSE, F.CTC, (SELECT CONVERT( CONCAT( SUM(TIMESTAMPDIFF(YEAR,EXP.DATE_JOINING, EXP.DATE_RELIEVING)) + TIMESTAMPDIFF(YEAR,PF.DATE_OF_JOINING,CURDATE()),'-','YEAR',',', SUM(ROUND(TIMESTAMPDIFF(MONTH,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%12))+ROUND(TIMESTAMPDIFF(MONTH,PF.DATE_OF_JOINING,CURDATE())%12), '-','MONTH',',', SUM(ROUND(TIMESTAMPDIFF(DAY,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%30.437))+ ROUND(TIMESTAMPDIFF(DAY,PF.DATE_OF_JOINING,CURDATE())%30.437),'-','DAY') ,CHAR)FROM EXPERIENCE_INFO EXP, PROFILE_INFO PF,USERS U2 where U2.ID = PF.ID AND U2.ID=U.ID AND EXP.USER_ID= U.ID) as EXPERIENCE FROM  USERS U JOIN PROFILE_INFO PF ON PF.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID LEFT JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID LEFT JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID LEFT JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID WHERE U.ID >4"; 
			
			//final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME AS PROJECT_NAME,CD.CH_CODE_NAME, ED.DEGREE_COURSE, F.CTC, (SELECT CONVERT( CONCAT( SUM(TIMESTAMPDIFF(YEAR,EXP.DATE_JOINING, EXP.DATE_RELIEVING)) + TIMESTAMPDIFF(YEAR,PF.DATE_OF_JOINING,CURDATE()),'-','YEAR',',', SUM(ROUND(TIMESTAMPDIFF(MONTH,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%12))+ROUND(TIMESTAMPDIFF(MONTH,PF.DATE_OF_JOINING,CURDATE())%12), '-','MONTH',',', SUM(ROUND(TIMESTAMPDIFF(DAY,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%30.437))+ ROUND(TIMESTAMPDIFF(DAY,PF.DATE_OF_JOINING,CURDATE())%30.437),'-','DAY') ,CHAR)FROM EXPERIENCE_INFO EXP, PROFILE_INFO PF,USERS U2 where U2.ID = PF.ID AND U2.ID=U.ID AND EXP.USER_ID= U.ID) as EXPERIENCE FROM  USERS U JOIN PROFILE_INFO PF ON PF.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID LEFT JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID LEFT JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID LEFT JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID WHERE U.ID >3";
			
			//final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME AS PROJECT_NAME,CL.NAME AS CLIENT_NAME, CD.CH_CODE_NAME, ED.DEGREE_COURSE, F.CTC, (SELECT CONVERT( CONCAT( IFNULL(SUM(TIMESTAMPDIFF(YEAR,EXP.DATE_JOINING, EXP.DATE_RELIEVING)),0) + TIMESTAMPDIFF(YEAR,PF.DATE_OF_JOINING,CURDATE()),'-', 'YEAR',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(MONTH,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%12)),0)+ ROUND(TIMESTAMPDIFF(MONTH,PF.DATE_OF_JOINING,CURDATE())%12) ,'-','MONTH',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(DAY,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%30.437)),0)+ ROUND(TIMESTAMPDIFF(DAY,PF.DATE_OF_JOINING,CURDATE())%30.437), '-','DAY') ,CHAR) FROM EXPERIENCE_INFO EXP WHERE EXP.USER_ID = U.ID ) AS EXPERIENCE FROM PROFILE_INFO PF JOIN  USERS U ON PF.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID LEFT JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID LEFT JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID LEFT JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID LEFT JOIN PROJ_CLIENT_MAP PRCLM ON PRCLM.PROJ_ID = PR.ID LEFT JOIN CLIENT CL ON CL.ID = PRCLM.CLIENT_ID WHERE U.ID > 3";
			
			//final String SQL = "SELECT U.ID,PF.ID, U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME ,CD.CH_CODE_NAME, ED.DEGREE_COURSE, F.CTC, (SELECT CONVERT( CONCAT( IFNULL(SUM(TIMESTAMPDIFF(YEAR,EXP.DATE_JOINING, EXP.DATE_RELIEVING)),0) + TIMESTAMPDIFF(YEAR,PF.DATE_OF_JOINING,CURDATE()),'-','YEAR',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(MONTH,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%12)),0)+ROUND(TIMESTAMPDIFF(MONTH,PF.DATE_OF_JOINING,CURDATE())%12),'-','MONTH',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(DAY,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%30.437)),0)+ ROUND(TIMESTAMPDIFF(DAY,PF.DATE_OF_JOINING,CURDATE())%30.437),'-','DAY') ,CHAR)FROM EXPERIENCE_INFO EXP WHERE EXP.USER_ID = U.ID)FROM PROFILE_INFO PF JOIN  USERS U ON PF.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID LEFT JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID LEFT JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID LEFT JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID WHERE U.ID > 3";
		
	//		final String SQL = "SELECT U.ID,PF.ID, U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME ,CD.CH_CODE_NAME, ED.DEGREE_COURSE, F.CTC, (SELECT CONVERT( CONCAT( IFNULL(SUM(TIMESTAMPDIFF(YEAR,EXP.DATE_JOINING, EXP.DATE_RELIEVING)),0) + TIMESTAMPDIFF(YEAR,PF.DATE_OF_JOINING,CURDATE()),'-','YEAR',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(MONTH,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%12)),0)+ROUND(TIMESTAMPDIFF(MONTH,PF.DATE_OF_JOINING,CURDATE())%12),'-','MONTH',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(DAY,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%30.437)),0)+ ROUND(TIMESTAMPDIFF(DAY,PF.DATE_OF_JOINING,CURDATE())%30.437),'-','DAY') ,CHAR)FROM EXPERIENCE_INFO EXP WHERE EXP.USER_ID = U.ID)FROM PROFILE_INFO PF JOIN  USERS U ON ((PF.ID = U.PROFILE_ID   AND PF.DATE_OF_SEPERATION IS NULL) AND (U.ID != 0 AND U.EMP_ID != 0)) LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID LEFT JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID LEFT JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID LEFT JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID WHERE U.ID > 3 AND  STATUS = 0 GROUP BY U.EMP_ID ORDER BY U.ID";

			//previous working query
			//final String SQL = "SELECT U.ID,PF.ID, U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,OFFICAL_EMAIL_ID,PER.PRIMARY_PHONE_NO,L.DESIGNATION,PR.NAME ,CD.CH_CODE_NAME, ED.DEGREE_COURSE, F.CTC, (SELECT CONVERT( CONCAT( IFNULL(SUM(TIMESTAMPDIFF(YEAR,EXP.DATE_JOINING, EXP.DATE_RELIEVING)),0) + TIMESTAMPDIFF(YEAR,PF.DATE_OF_JOINING,CURDATE()),'-','YEAR',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(MONTH,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%12)),0)+ROUND(TIMESTAMPDIFF(MONTH,PF.DATE_OF_JOINING,CURDATE())%12),'-','MONTH',',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(DAY,EXP.DATE_JOINING, EXP.DATE_RELIEVING)%30.437)),0)+ ROUND(TIMESTAMPDIFF(DAY,PF.DATE_OF_JOINING,CURDATE())%30.437),'-','DAY') ,CHAR)FROM EXPERIENCE_INFO EXP WHERE EXP.USER_ID = U.ID)FROM PROFILE_INFO PF JOIN  USERS U ON ((PF.ID = U.PROFILE_ID   AND PF.DATE_OF_SEPERATION IS NULL) AND (U.ID != 0 AND U.EMP_ID != 0)) LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN  EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.EMP_ID LEFT JOIN ROLL_ON_PROJ_MAP RONMAP ON RONMAP.ROLL_ON_ID = RON.ID LEFT JOIN PROJECT PR ON PR.ID = RONMAP.PROJ_ID LEFT JOIN CHARGE_CODE CD ON CD.PO_ID = RON.CH_CODE_ID WHERE U.ID > 3 AND  STATUS = 0 GROUP BY U.EMP_ID ORDER BY U.ID";

			final String SQL = "SELECT U.ID, PF.ID, U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) AS NAME, OFFICAL_EMAIL_ID, PER.PRIMARY_PHONE_NO, L.DESIGNATION, ED.DEGREE_COURSE, F.CTC, (select pp.NAME  from  PROJECT pp where pp.ID=(select rmp.PROJ_ID  from  ROLL_ON_PROJ_MAP rmp where rmp.ROLL_ON_ID=(select max(ID) from  ROLL_ON RONv where RONv.EMP_ID=U.ID ))) as Name, (select max(cc.CH_CODE_NAME) from CHARGE_CODE cc left join PO_DETAILS po on po.ID=cc.PO_ID left join ROLL_ON_PROJ_MAP RRP on RRP.PROJ_ID=po.PROJ_ID where po.PROJ_ID=(select rmp.PROJ_ID  from  ROLL_ON_PROJ_MAP rmp where rmp.ROLL_ON_ID=(select max(ID) from  ROLL_ON RONv where RONv.EMP_ID=U.ID )))as CH_CODE_NAME, (SELECT CONVERT( CONCAT(IFNULL(SUM(TIMESTAMPDIFF(YEAR, EXP.DATE_JOINING, EXP.DATE_RELIEVING)), 0) + TIMESTAMPDIFF(YEAR, PF.DATE_OF_JOINING, CURDATE()), '-', 'YEAR', ',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(MONTH, EXP.DATE_JOINING, EXP.DATE_RELIEVING) % 12)), 0) + ROUND(TIMESTAMPDIFF(MONTH, PF.DATE_OF_JOINING, CURDATE()) % 12), '-', 'MONTH', ',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(DAY, EXP.DATE_JOINING, EXP.DATE_RELIEVING) % 30.437)), 0) + ROUND(TIMESTAMPDIFF(DAY, PF.DATE_OF_JOINING, CURDATE()) % 30.437), '-', 'DAY') , CHAR) FROM EXPERIENCE_INFO EXP WHERE EXP.USER_ID = U.ID) as expname , PF.LOCATION FROM PROFILE_INFO PF JOIN USERS U ON ((PF.ID = U.PROFILE_ID AND PF.DATE_OF_SEPERATION IS NULL)  AND (U.ID != 0 AND U.EMP_ID != 0)) LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.ID WHERE U.ID > 3 and STATUS = 0 GROUP BY U.EMP_ID ORDER BY U.ID";
		/*	SELECT U.ID, PF.ID, U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) AS NAME, OFFICAL_EMAIL_ID, PER.PRIMARY_PHONE_NO, L.DESIGNATION, ED.DEGREE_COURSE, F.CTC, (select pp.NAME  from  PROJECT pp where pp.ID=(select rmp.PROJ_ID  from  ROLL_ON_PROJ_MAP rmp where rmp.ROLL_ON_ID=(select max(ID) from  ROLL_ON RONv where RONv.EMP_ID=U.ID ))) as PROJNAME, (select max(cc.CH_CODE_NAME) from CHARGE_CODE cc left join PO_DETAILS po on po.ID=cc.PO_ID left join ROLL_ON_PROJ_MAP RRP on RRP.PROJ_ID=po.PROJ_ID where po.PROJ_ID=(select rmp.PROJ_ID  from  ROLL_ON_PROJ_MAP rmp where rmp.ROLL_ON_ID=(select max(ID) from  ROLL_ON RONv where RONv.EMP_ID=U.ID ))), (SELECT CONVERT( CONCAT(IFNULL(SUM(TIMESTAMPDIFF(YEAR, EXP.DATE_JOINING, EXP.DATE_RELIEVING)), 0) + TIMESTAMPDIFF(YEAR, PF.DATE_OF_JOINING, CURDATE()), '-', 'YEAR', ',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(MONTH, EXP.DATE_JOINING, EXP.DATE_RELIEVING) % 12)), 0) + ROUND(TIMESTAMPDIFF(MONTH, PF.DATE_OF_JOINING, CURDATE()) % 12), '-', 'MONTH', ',', IFNULL(SUM(ROUND(TIMESTAMPDIFF(DAY, EXP.DATE_JOINING, EXP.DATE_RELIEVING) % 30.437)), 0) + ROUND(TIMESTAMPDIFF(DAY, PF.DATE_OF_JOINING, CURDATE()) % 30.437), '-', 'DAY') , CHAR) FROM EXPERIENCE_INFO EXP WHERE EXP.USER_ID = U.ID) as expname FROM PROFILE_INFO PF JOIN USERS U ON ((PF.ID = U.PROFILE_ID AND PF.DATE_OF_SEPERATION IS NULL)  AND (U.ID != 0 AND U.EMP_ID != 0)) LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN EDUCATION_INFO ED ON ED.USER_ID = U.ID LEFT JOIN PERSONAL_INFO PER ON PER.ID = U.PERSONAL_ID LEFT JOIN ROLL_ON RON ON RON.EMP_ID = U.ID WHERE U.ID > 3 and STATUS = 0 GROUP BY U.EMP_ID ORDER BY U.ID;*/
	
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				String[] row = new String[13];
				row[0] = rs.getInt(1) + "";
				row[1] = rs.getInt(2) + "";
				row[2] = rs.getInt(3) + "";
				row[3] = rs.getString(4);
				row[4] = rs.getString(5);
				row[5] = rs.getString(6);
				row[6] = rs.getString(7);
				row[7] = rs.getString(8);
				row[8] = rs.getString(9);
				row[9] = rs.getString(10);
				row[10] = rs.getString(11);
		        row[11] = rs.getString(12);
		        row[12] = rs.getString(13);//location
				
				/*row[1] = rs.getString(2);
				row[2] = rs.getString(3);
				row[3] = rs.getString(4);
				row[4] = rs.getString(5);
				row[5] = rs.getString(6);
				row[6] = rs.getString(7);
				row[7] = rs.getString(8);
				row[8] = rs.getString(9);
				row[9] = rs.getString(10);*/
				//row[10] = rs.getString(11);
				resultList.add(row);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		return resultList;
		
	}
	
	
	
	
	
	
	
	

	@Override
	public Documents findByUserId(int id) throws DocumentsDaoException {
		Documents ret[] = findByDynamicSelect( "SELECT DP.ID, DP.FILENAME, DP.DOCTYPE, DP.DESCRIPTIONS  FROM DOCUMENTS DP LEFT JOIN DOCUMENT_MAPPING DM ON DM.DOCUMENT_ID=DP.ID WHERE DM.USER_SEPRATION_ID=?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}


}