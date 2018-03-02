package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface LocationDao {

	public Location[] findByRegion(int region_id) throws LocationDaoException;

	public Location[] findByLocation(String sql, Object[] sqlParams)throws LocationDaoException;


}



	
	
	
	



