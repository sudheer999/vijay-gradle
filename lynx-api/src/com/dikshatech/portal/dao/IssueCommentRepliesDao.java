/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import java.util.Date;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface IssueCommentRepliesDao
{
	/** 
	 * Inserts a new row in the ISSUE_COMMENT_REPLIES table.
	 */
	public IssueCommentRepliesPk insert(IssueCommentReplies dto) throws IssueCommentRepliesDaoException;

	/** 
	 * Updates a single row in the ISSUE_COMMENT_REPLIES table.
	 */
	public void update(IssueCommentRepliesPk pk, IssueCommentReplies dto) throws IssueCommentRepliesDaoException;

	/** 
	 * Deletes a single row in the ISSUE_COMMENT_REPLIES table.
	 */
	public void delete(IssueCommentRepliesPk pk) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns the rows from the ISSUE_COMMENT_REPLIES table that matches the specified primary-key value.
	 */
	public IssueCommentReplies findByPrimaryKey(IssueCommentRepliesPk pk) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'ID = :id'.
	 */
	public IssueCommentReplies findByPrimaryKey(int id) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria ''.
	 */
	public IssueCommentReplies[] findAll() throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'COMMENT_ID = :commentId'.
	 */
	public IssueCommentReplies[] findByIssueComments(int commentId) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'ID = :id'.
	 */
	public IssueCommentReplies[] findWhereIdEquals(int id) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'REPLY = :reply'.
	 */
	public IssueCommentReplies[] findWhereReplyEquals(String reply) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'REPLY_DATE = :replyDate'.
	 */
	public IssueCommentReplies[] findWhereReplyDateEquals(Date replyDate) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'REPLIED_BY = :repliedBy'.
	 */
	public IssueCommentReplies[] findWhereRepliedByEquals(int repliedBy) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the criteria 'COMMENT_ID = :commentId'.
	 */
	public IssueCommentReplies[] findWhereCommentIdEquals(int commentId) throws IssueCommentRepliesDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the specified arbitrary SQL statement
	 */
	public IssueCommentReplies[] findByDynamicSelect(String sql, Object[] sqlParams) throws IssueCommentRepliesDaoException;

	/** 
	 * Returns all rows from the ISSUE_COMMENT_REPLIES table that match the specified arbitrary SQL statement
	 */
	public IssueCommentReplies[] findByDynamicWhere(String sql, Object[] sqlParams) throws IssueCommentRepliesDaoException;

}