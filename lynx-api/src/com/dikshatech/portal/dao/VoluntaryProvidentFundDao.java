package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.VoluntaryProvidentFund;
import com.dikshatech.portal.dto.VoluntaryProvidentFundPk;
import com.dikshatech.portal.exceptions.VoluntaryProvidentFundDaoException;
	

public interface VoluntaryProvidentFundDao {

public VoluntaryProvidentFundPk insert(VoluntaryProvidentFund dto) throws VoluntaryProvidentFundDaoException;


public VoluntaryProvidentFund[] findByVpf(String sql, Object[] id) throws VoluntaryProvidentFundDaoException;


}



