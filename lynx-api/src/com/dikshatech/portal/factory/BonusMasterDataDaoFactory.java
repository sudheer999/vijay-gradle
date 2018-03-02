package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusMasterDataDao;
import com.dikshatech.portal.jdbc.BonusMasterDataDaoImpl;


public class BonusMasterDataDaoFactory {

	/**
	 * Method 'create'
	 * 
	 * @return PerdiemMasterDataDao
	 */
	public static BonusMasterDataDao create() {
		return new BonusMasterDataDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return PerdiemMasterDataDao
	 */
	public static BonusMasterDataDao create(Connection conn) {
		return new BonusMasterDataDaoImpl(conn);
	}

}

