package com.dikshatech.portal.jdbc;

import com.dikshatech.portal.dao.MailDao;

public class MailDaoImpl extends AbstractDAO implements MailDao
{/*
	

	*//** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 *//*
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( AccessibilityLevelDaoImpl.class );

	*//** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 *//*
	protected final String SQL_SELECT = "SELECT ID, MODULE_PERM_ID, COMPANY_ID, REGION_ID, DIVISION_ID FROM " + getTableName() + "";

	*//** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 *//*
	protected int maxRows;

	*//** 
	 * SQL INSERT statement for this table
	 *//*
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, MODULE_PERM_ID, COMPANY_ID, REGION_ID, DIVISION_ID ) VALUES ( ?, ?, ?, ?, ? )";

	*//** 
	 * SQL UPDATE statement for this table
	 *//*
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, MODULE_PERM_ID = ?, COMPANY_ID = ?, REGION_ID = ?, DIVISION_ID = ? WHERE ID = ?";

	*//** 
	 * SQL DELETE statement for this table
	 *//*
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	*//** 
	 * Index of column ID
	 *//*
	protected static final int COLUMN_ID = 1;

	*//** 
	 * Index of column MODULE_PERM_ID
	 *//*
	protected static final int COLUMN_MODULE_PERM_ID = 2;

	*//** 
	 * Index of column COMPANY_ID
	 *//*
	protected static final int COLUMN_COMPANY_ID = 3;

	*//** 
	 * Index of column REGION_ID
	 *//*
	protected static final int COLUMN_REGION_ID = 4;

	*//** 
	 * Index of column DIVISION_ID
	 *//*
	protected static final int COLUMN_DIVISION_ID = 5;

	*//** 
	 * Number of columns
	 *//*
	protected static final int NUMBER_OF_COLUMNS = 5;

	*//** 
	 * Index of primary-key column ID
	 *//*
	protected static final int PK_COLUMN_ID = 1;

	*//** 
	 * Inserts a new row in the ACCESSIBILITY_LEVEL table.
	 *//*
	public AccessibilityLevelPk insert(AccessibilityLevel dto) throws AccessibilityLevelDaoException
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
			stmt.setInt( index++, dto.getModulePermId() );
			stmt.setString( index++, dto.getCompanyId() );
			stmt.setString( index++, dto.getRegionId() );
			stmt.setString( index++, dto.getDivisionId() );
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
			throw new AccessibilityLevelDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	*//** 
	 * Updates a single row in the ACCESSIBILITY_LEVEL table.
	 *//*
	public void update(AccessibilityLevelPk pk, AccessibilityLevel dto) throws AccessibilityLevelDaoException
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
			stmt.setInt( index++, dto.getModulePermId() );
			stmt.setString( index++, dto.getCompanyId() );
			stmt.setString( index++, dto.getRegionId() );
			stmt.setString( index++, dto.getDivisionId() );
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
			throw new AccessibilityLevelDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	*//** 
	 * Deletes a single row in the ACCESSIBILITY_LEVEL table.
	 *//*
	public void delete(AccessibilityLevelPk pk) throws AccessibilityLevelDaoException
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
			throw new AccessibilityLevelDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	*//** 
	 * Returns the rows from the ACCESSIBILITY_LEVEL table that matches the specified primary-key value.
	 *//*
	public AccessibilityLevel findByPrimaryKey(AccessibilityLevelPk pk) throws AccessibilityLevelDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	*//** 
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'ID = :id'.
	 *//*
	public AccessibilityLevel findByPrimaryKey(int id) throws AccessibilityLevelDaoException
	{
		AccessibilityLevel ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	*//** 
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria ''.
	 *//*
	public AccessibilityLevel[] findAll() throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	*//** 
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the criteria 'MODULE_PERM_ID = :modulePermId'.
	 *//*
	public AccessibilityLevel[] findWhereModulePermIdEquals(int modulePermId) throws AccessibilityLevelDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MODULE_PERM_ID = ? ORDER BY MODULE_PERM_ID", new Object[] {  new Integer(modulePermId) } );
	}

	

	*//**
	 * Method 'MailDaoImpl'
	 * 
	 * @param userConn
	 *//*
	public MailDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	public MailDaoImpl()
	{
		// TODO Auto-generated constructor stub
	}

	*//** 
	 * Sets the value of maxRows
	 *//*
	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	*//** 
	 * Gets the value of maxRows
	 *//*
	public int getMaxRows()
	{
		return maxRows;
	}

	*//**
	 * Method 'getTableName'
	 * 
	 * @return String
	 *//*
	public String getTableName()
	{
		return "ACCESSIBILITY_LEVEL";
	}

	*//** 
	 * Fetches a single row from the result set
	 *//*
	protected AccessibilityLevel fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			AccessibilityLevel dto = new AccessibilityLevel();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	*//** 
	 * Fetches multiple rows from the result set
	 *//*
	protected AccessibilityLevel[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			AccessibilityLevel dto = new AccessibilityLevel();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		AccessibilityLevel ret[] = new AccessibilityLevel[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	*//** 
	 * Populates a DTO with data from a ResultSet
	 *//*
	protected void populateDto(AccessibilityLevel dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setModulePermId( rs.getInt( COLUMN_MODULE_PERM_ID ) );
		dto.setCompanyId( rs.getString( COLUMN_COMPANY_ID ) );
		dto.setRegionId( rs.getString( COLUMN_REGION_ID ) );
		dto.setDivisionId( rs.getString( COLUMN_DIVISION_ID ) );
	}

	*//** 
	 * Resets the modified attributes in the DTO
	 *//*
	protected void reset(AccessibilityLevel dto)
	{
	}

	*//** 
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the specified arbitrary SQL statement
	 *//*
	public AccessibilityLevel[] findByDynamicSelect(String sql, Object[] sqlParams) throws AccessibilityLevelDaoException
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
			throw new AccessibilityLevelDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	*//** 
	 * Returns all rows from the ACCESSIBILITY_LEVEL table that match the specified arbitrary SQL statement
	 *//*
	public AccessibilityLevel[] findByDynamicWhere(String sql, Object[] sqlParams) throws AccessibilityLevelDaoException
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
			throw new AccessibilityLevelDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}



*/}
