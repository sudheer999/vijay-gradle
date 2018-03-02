package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.CategoryDao;
import com.dikshatech.portal.dto.Category;
import com.dikshatech.portal.dto.Location;
import com.dikshatech.portal.exceptions.CategoryDaoException;
import com.dikshatech.portal.exceptions.LocationDaoException;


public class CategoryDaoImpl extends AbstractDAO implements CategoryDao{
	
	
	protected static final Logger logger = Logger.getLogger( CategoryDaoImpl.class );
	protected java.sql.Connection userConn;
	protected int maxRows;
	
	public CategoryDaoImpl()
	{
	}
	
	public CategoryDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}
	
	public Category[] findAllCategory(String sql) throws CategoryDaoException
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
		
			
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new CategoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	protected Category[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<Category> resultList = new ArrayList<Category>();
		while (rs.next()){
			Category dto = new Category();
		
			populateDto(dto, rs);
			resultList.add(dto);
		}
		Category ret[] = new Category[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	protected void populateDto(Category dto, ResultSet rs) throws SQLException
	{	int index = 1;
		dto.setId( rs.getInt( index++ ) );
		dto.setCategory(rs.getString( index++ ));
		
		
	}
	protected Category[] fetchMultiResultsC(ResultSet rs) throws SQLException {
		Collection<Category> resultList = new ArrayList<Category>();
		while (rs.next()){
			Category dto = new Category();
		
			populateDtoC(dto, rs);
			resultList.add(dto);
		}
		Category ret[] = new Category[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	protected void populateDtoC(Category dto, ResultSet rs) throws SQLException
	{	int index = 1;
		dto.setId( rs.getInt( index++ ) );
		
		
		
	}

	public Category[] findByCategory(String sql, Object[] sqlParams) throws CategoryDaoException
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
			return fetchMultiResultsC(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new CategoryDaoException( "Exception: " + _e.getMessage(), _e );
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
