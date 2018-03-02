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
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class Commitments extends PortalForm implements Serializable
{
	/**
	 * This attribute maps to the column ID in the COMMITMENTS table.
	 */
	protected int		id;

	/**
	 * This attribute maps to the column COMMENTS in the COMMITMENTS table.
	 */
	protected String	comments;

	/**
	 * This attribute maps to the column DATE_PICKER in the COMMITMENTS table.
	 */
	protected Date		datePicker;

	/**
	 * This attribute maps to the column CANDIDATE_ID in the COMMITMENTS table.
	 */
	protected int		candidateId;

	/**
	 * This attribute represents whether the primitive attribute candidateId is null.
	 */
	protected boolean	candidateIdNull	= true;

	/**
	 * This attribute maps to the column USER_ID_EMP in the COMMITMENTS table.
	 */
	protected int		userIdEmp;

	/**
	 * This attribute represents whether the primitive attribute userIdEmp is null.
	 */
	protected boolean	userIdEmpNull		= true;

	/**
	 * This attribute represents whether the primitive attribute comments is null.
	 */
	protected String[ ]	comment;

	/**
	 * Method 'Commitments'
	 */
	public Commitments()
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
	 * Method 'getComments'
	 * 
	 * @return String
	 */
	public String getComments()
	{
		return comments;
	}

	/**
	 * Method 'setComments'
	 * 
	 * @param comments
	 */
	public void setComments(String comments)
	{
		this.comments = comments;
	}

	/**
	 * Method 'getDatePicker'
	 * 
	 * @return Date
	 */
	public Date getDatePicker()
	{
		return datePicker;
	}

	/**
	 * Method 'setDatePicker'
	 * 
	 * @param datePicker
	 */
	public void setDatePicker(Date datePicker)
	{
		this.datePicker = datePicker;
	}

	/**
	 * Method 'getCandidateId'
	 * 
	 * @return int
	 */
	public int getCandidateId()
	{
		return candidateId;
	}

	/**
	 * Method 'setCandidateId'
	 * 
	 * @param candidateId
	 */
	public void setCandidateId(int candidateId)
	{
		this.candidateId = candidateId;
		this.candidateIdNull = false;
	}

	/**
	 * Method 'setCandidateIdNull'
	 * 
	 * @param value
	 */
	public void setCandidateIdNull(boolean value)
	{
		this.candidateIdNull = value;
	}

	/**
	 * Method 'isCandidateIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isCandidateIdNull()
	{
		return candidateIdNull;
	}

	/**
	 * Method 'getUserIdEmp'
	 * 
	 * @return int
	 */
	public int getUserIdEmp()
	{
		return userIdEmp;
	}

	/**
	 * Method 'setUserIdEmp'
	 * 
	 * @param userIdEmp
	 */
	public void setUserIdEmp(int userIdEmp)
	{
		this.userIdEmp = userIdEmp;
		this.userIdEmpNull = false;
	}

	/**
	 * Method 'setUserIdEmpNull'
	 * 
	 * @param value
	 */
	public void setUserIdEmpNull(boolean value)
	{
		this.userIdEmpNull = value;
	}

	/**
	 * Method 'isUserIdEmpNull'
	 * 
	 * @return boolean
	 */
	public boolean isUserIdEmpNull()
	{
		return userIdEmpNull;
	}

	public String[ ] getComment()
	{
		return comment;
	}

	public void setComment(String[ ] comment)
	{
		this.comment = comment;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null)
		{
			return false;
		}

		if (_other == this)
		{
			return true;
		}

		if (!(_other instanceof Commitments))
		{
			return false;
		}

		final Commitments _cast = (Commitments) _other;
		if (id != _cast.id)
		{
			return false;
		}

		if (comments == null ? _cast.comments != comments : !comments.equals(_cast.comments))
		{
			return false;
		}

		if (datePicker == null ? _cast.datePicker != datePicker : !datePicker.equals(_cast.datePicker))
		{
			return false;
		}

		if (candidateId != _cast.candidateId)
		{
			return false;
		}

		if (candidateIdNull != _cast.candidateIdNull)
		{
			return false;
		}

		if (userIdEmp != _cast.userIdEmp)
		{
			return false;
		}

		if (userIdEmpNull != _cast.userIdEmpNull)
		{
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
		if (comments != null)
		{
			_hashCode = 29 * _hashCode + comments.hashCode();
		}

		if (datePicker != null)
		{
			_hashCode = 29 * _hashCode + datePicker.hashCode();
		}

		_hashCode = 29 * _hashCode + candidateId;
		_hashCode = 29 * _hashCode + (candidateIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + userIdEmp;
		_hashCode = 29 * _hashCode + (userIdEmpNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return CommitmentsPk
	 */
	public CommitmentsPk createPk()
	{
		return new CommitmentsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.Commitments: ");
		ret.append("id=" + id);
		ret.append(", comments=" + comments);
		ret.append(", datePicker=" + datePicker);
		ret.append(", candidateId=" + candidateId);
		ret.append(", userIdEmp=" + userIdEmp);
		return ret.toString();
	}

}