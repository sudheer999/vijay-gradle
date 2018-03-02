package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusDao;
import com.dikshatech.portal.jdbc.RetentionBonusDaoImpl;

public class RetentionBonusDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return PerdiemMasterDataDao
	 */
	public static RetentionBonusDao create() {
		return new RetentionBonusDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return PerdiemMasterDataDao
	 */
	public static RetentionBonusDao create(Connection conn) {
		return new RetentionBonusDaoImpl(conn);
	}

}
