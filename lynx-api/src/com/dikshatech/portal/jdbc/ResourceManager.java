package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.dikshatech.common.db.MyDBConnect;

public class ResourceManager {

	public static DataSource	dataSource	= null;

	public static Connection getConnection() throws SQLException {
		/*if (dataSource == null){
			try{
				// Get DataSource
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				dataSource = (DataSource) envContext.lookup("jdbc/TestDB");
			} catch (NamingException e){
				e.printStackTrace();
			}
		}
		return dataSource.getConnection();*/
		return MyDBConnect.getConnect();
	}

	public static void close(Connection conn) {
		try{
			if (conn != null) MyDBConnect.close(conn);
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
	}

	public static void close(PreparedStatement stmt) {
		try{
			if (stmt != null) stmt.close();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try{
			if (rs != null) rs.close();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
	}
}
