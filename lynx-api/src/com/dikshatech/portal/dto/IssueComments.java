/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.factory.*;
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class IssueComments implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the ISSUE_COMMENTS table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column COMMENT in the ISSUE_COMMENTS table.
	 */
	protected String comment;

	/** 
	 * This attribute maps to the column ATTACHMENT in the ISSUE_COMMENTS table.
	 */
	protected String attachment;

	/** 
	 * This attribute maps to the column COMMENT_DATE in the ISSUE_COMMENTS table.
	 */
	protected Date commentDate;

	/** 
	 * This attribute maps to the column IS_REPLY in the ISSUE_COMMENTS table.
	 */
	protected short isReply;

	/**
	 * Method 'IssueComments'
	 * 
	 */
	public IssueComments()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Method 'getComment'
	 * 
	 * @return String
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * Method 'setComment'
	 * 
	 * @param comment
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	/**
	 * Method 'getAttachment'
	 * 
	 * @return String
	 */
	public String getAttachment()
	{
		return attachment;
	}

	/**
	 * Method 'setAttachment'
	 * 
	 * @param attachment
	 */
	public void setAttachment(String attachment)
	{
		this.attachment = attachment;
	}

	/**
	 * Method 'getCommentDate'
	 * 
	 * @return Date
	 */
	public Date getCommentDate()
	{
		return commentDate;
	}

	/**
	 * Method 'setCommentDate'
	 * 
	 * @param commentDate
	 */
	public void setCommentDate(Date commentDate)
	{
		this.commentDate = commentDate;
	}

	/**
	 * Method 'getIsReply'
	 * 
	 * @return short
	 */
	public short getIsReply()
	{
		return isReply;
	}

	/**
	 * Method 'setIsReply'
	 * 
	 * @param isReply
	 */
	public void setIsReply(short isReply)
	{
		this.isReply = isReply;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof IssueComments)) {
			return false;
		}
		
		final IssueComments _cast = (IssueComments) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (comment == null ? _cast.comment != comment : !comment.equals( _cast.comment )) {
			return false;
		}
		
		if (attachment == null ? _cast.attachment != attachment : !attachment.equals( _cast.attachment )) {
			return false;
		}
		
		if (commentDate == null ? _cast.commentDate != commentDate : !commentDate.equals( _cast.commentDate )) {
			return false;
		}
		
		if (isReply != _cast.isReply) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (comment != null) {
			_hashCode = 29 * _hashCode + comment.hashCode();
		}
		
		if (attachment != null) {
			_hashCode = 29 * _hashCode + attachment.hashCode();
		}
		
		if (commentDate != null) {
			_hashCode = 29 * _hashCode + commentDate.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) isReply;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return IssueCommentsPk
	 */
	public IssueCommentsPk createPk()
	{
		return new IssueCommentsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.IssueComments: " );
		ret.append( "id=" + id );
		ret.append( ", comment=" + comment );
		ret.append( ", attachment=" + attachment );
		ret.append( ", commentDate=" + commentDate );
		ret.append( ", isReply=" + isReply );
		return ret.toString();
	}

}
