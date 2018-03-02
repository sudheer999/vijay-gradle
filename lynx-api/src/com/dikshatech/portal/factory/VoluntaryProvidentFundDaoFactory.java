package com.dikshatech.portal.factory;
import java.sql.Connection;
import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.jdbc.VoluntaryProvidentFundDaoImpl;

public class VoluntaryProvidentFundDaoFactory {

		/**
		 * Method 'create'
		 * 
		 * @return ProfileInfoDao
		 */
	public static VoluntaryProvidentFundDao create(){
		
		return new VoluntaryProvidentFundDaoImpl();
		
	}
	
	public static VoluntaryProvidentFundDao create(Connection conn)
	{
		return new VoluntaryProvidentFundDaoImpl(conn);
	
	}

}


	
