package com.dikshatech.portal.jdbc;

import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

import java.sql.Connection;
import java.util.Collection;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationDaoImpl extends AbstractDAO implements LocationDao
{
	
	public LocationDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	protected int maxRows;
	
	public Location[] findByRegion(int region_id) throws LocationDaoException
	{
		
		String SQL_SELECT = "SELECT * FROM LOCATIONS";
		
		
		return findByDynamicSelect( SQL_SELECT + " WHERE REGION_ID = ?", new Object[] {  new Integer(region_id) } );
	}

		protected java.sql.Connection userConn;

		protected static final Logger logger = Logger.getLogger( LocationDaoImpl.class );
		
	
			// TODO Auto-generated constructor stub
	
		public LocationDaoImpl()
		{
		}
		public Location[] findByDynamicSelect(String sql, Object[] sqlParams) throws LocationDaoException
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
				throw new LocationDaoException( "Exception: " + _e.getMessage(), _e );
			}
			finally {
				ResourceManager.close(rs);
				ResourceManager.close(stmt);
				if (!isConnSupplied) {
					ResourceManager.close(conn);
				}
			
			}
			
		}
		
		public Location[] findByLocation(String sql, Object[] sqlParams) throws LocationDaoException
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
				return fetchMultiResultsL(rs);
			}
			catch (Exception _e) {
				logger.error( "Exception: " + _e.getMessage(), _e );
				throw new LocationDaoException( "Exception: " + _e.getMessage(), _e );
			}
			finally {
				ResourceManager.close(rs);
				ResourceManager.close(stmt);
				if (!isConnSupplied) {
					ResourceManager.close(conn);
				}
			
			}
			
		}
		protected Location[] fetchMultiResults(ResultSet rs) throws SQLException
		{
			Collection<Location> resultList = new ArrayList<Location>();
			while (rs.next()) {
				Location dto = new Location();
				populateDto( dto, rs);
				resultList.add( dto );
			}
			
			Location[] ret = new Location[ resultList.size() ];
			resultList.toArray( ret );
			return ret;
		}
		
		
		protected Location[] fetchMultiResultsL(ResultSet rs) throws SQLException
		{
			Collection<Location> resultList = new ArrayList<Location>();
			while (rs.next()) {
				Location dto = new Location();
				populateDtoL( dto, rs);
				resultList.add( dto );
			}
			
			Location[] ret = new Location[ resultList.size() ];
			resultList.toArray( ret );
			return ret;
		}
		
		
		protected void populateDtoL(Location dto, ResultSet rs) throws SQLException
		{
		    int index = 1;
			dto.setId(rs.getInt(index++));
			
		}
		
		
		protected void populateDto(Location dto, ResultSet rs) throws SQLException
		{
		    int index = 1;
			dto.setId(rs.getInt(index++));
			dto.setRegion_id(rs.getInt(index++));
			dto.setLocation(rs.getString(index++));
		}
		
	
		

}
