/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import java.util.ArrayList;
import java.util.Date;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface IssueCommentsDao
{
	/** 
	 * Inserts a new row in the ISSUE_COMMENTS table.
	 */
	public IssueCommentsPk insert(IssueComments dto) throws IssueCommentsDaoException;

	/** 
	 * Updates a single row in the ISSUE_COMMENTS table.
	 */
	public void update(IssueCommentsPk pk, IssueComments dto) throws IssueCommentsDaoException;

	/** 
	 * Deletes a single row in the ISSUE_COMMENTS table.
	 */
	public void delete(IssueCommentsPk pk) throws IssueCommentsDaoException;

	/** 
	 * Returns the rows from the ISSUE_COMMENTS table that matches the specified primary-key value.
	 */
	public IssueComments findByPrimaryKey(IssueCommentsPk pk) throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'ID = :id'.
	 */
	public IssueComments findByPrimaryKey(int id) throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria ''.
	 */
	public IssueComments[] findAll() throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'ID = :id'.
	 */
	public IssueComments[] findWhereIdEquals(int id) throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'COMMENT = :comment'.
	 */
	public IssueComments[] findWhereCommentEquals(String comment) throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'ATTACHMENT = :attachment'.
	 */
	public IssueComments[] findWhereAttachmentEquals(String attachment) throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'COMMENT_DATE = :commentDate'.
	 */
	public IssueComments[] findWhereCommentDateEquals(Date commentDate) throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the criteria 'IS_REPLY = :isReply'.
	 */
	public IssueComments[] findWhereIsReplyEquals(short isReply) throws IssueCommentsDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the specified arbitrary SQL statement
	 */
	public IssueComments[] findByDynamicSelect(String sql, Object[] sqlParams) throws IssueCommentsDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENTS table that match the specified arbitrary SQL statement
	 */
	public IssueComments[] findByDynamicWhere(String sql, Object[] sqlParams) throws IssueCommentsDaoException;
	
	public ArrayList<String> getIssueComments(String sql) throws IssueCommentsDaoException;

}
