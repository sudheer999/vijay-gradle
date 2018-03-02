package com.dikshatech.portal.dao;


import com.dikshatech.portal.dto.Category;
import com.dikshatech.portal.exceptions.CategoryDaoException;


public interface CategoryDao {
	
	public Category[] findAllCategory(String sql) throws CategoryDaoException;
	
	public Category[] findByCategory(String sql, Object[] sqlParams)throws CategoryDaoException;

}
