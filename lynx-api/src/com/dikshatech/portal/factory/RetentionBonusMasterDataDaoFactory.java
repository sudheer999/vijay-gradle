package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusMasterDataDao;
import com.dikshatech.portal.jdbc.RetentionBonusMasterDataDaoImpl;

public class RetentionBonusMasterDataDaoFactory {


	/**
	 * Method 'create'
	 * 
	 * @return PerdiemMasterDataDao
	 */
	public static RetentionBonusMasterDataDao create() {
		return new RetentionBonusMasterDataDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return PerdiemMasterDataDao
	 */
	public static RetentionBonusMasterDataDao create(Connection conn) {
		return new RetentionBonusMasterDataDaoImpl(conn);
	}


}
