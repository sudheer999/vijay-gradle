/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class CandidateReq extends PortalForm implements Serializable,Comparator
{
	/** 
	 * This attribute maps to the column ID in the CANDIDATE_REQ table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column ESRQM_ID in the CANDIDATE_REQ table.
	 */
	protected int esrqmId;

	/** 
	 * This attribute maps to the column COMMENTS in the CANDIDATE_REQ table.
	 */
	protected String comments;

	/** 
	 * This attribute maps to the column STATUS in the CANDIDATE_REQ table.
	 */
	protected String status;

	/** 
	 * This attribute maps to the column ASSIGNED_TO in the CANDIDATE_REQ table.
	 */
	protected int assignedTo;

	/** 
	 * This attribute represents whether the primitive attribute assignedTo is null.
	 */
	protected boolean assignedToNull = true;

	/** 
	 * This attribute maps to the column RAISED_BY in the CANDIDATE_REQ table.
	 */
	protected int raisedBy;

	/** 
	 * This attribute represents whether the primitive attribute raisedBy is null.
	 */
	protected boolean raisedByNull = true;

	/** 
	 * This attribute maps to the column SUMMARY in the CANDIDATE_REQ table.
	 */
	protected String summary;

	/** 
	 * This attribute maps to the column CANDIDATE_ID in the CANDIDATE_REQ table.
	 */
	protected int candidateId;

	/** 
	 * This attribute maps to the column OFFER_LETTER in the CANDIDATE_REQ table.
	 */
	protected String offerLetter;
	
	protected String assignedToName;
	protected String raisedByName;
	protected Object candidateDetails;

	/** 
	 * This attribute maps to the column RE_SERVE in the CANDIDATE_REQ table.
	 */
	protected short reServe;

	/** 
	 * This attribute maps to the column CREATEDATETIME in the CANDIDATE_REQ table.
	 */
	protected Date createdatetime;

	/** 
	 * This attribute maps to the column CYCLE in the CANDIDATE_REQ table.
	 */
	protected int cycle;

	/** 
	 * This attribute represents whether the primitive attribute cycle is null.
	 */
	protected boolean cycleNull = true;

	/** 
	 * This attribute maps to the column MESSAGE_BODY in the CANDIDATE_REQ table.
	 */
	protected String messageBody;
	
	/** 
	 * This attribute maps to the column SERVED in the CANDIDATE_REQ table.
	 */
	protected int served;

	/** 
	 * This attribute represents whether the primitive attribute served is null.
	 */
	protected boolean servedNull = true;
	
	/** 
	 * This attribute maps to the column ACTION_TAKEN in the CANDIDATE_REQ table.
	 */
	protected int actionTaken;

	/** 
	 * This attribute represents whether the primitive attribute actionTaken is null.
	 */
	protected boolean actionTakenNull = true;
	
	/**
	 * Method 'CandidateReq'
	 * 
	 */
	public CandidateReq()
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
	 * Method 'getEsrqmId'
	 * 
	 * @return int
	 */
	public int getEsrqmId()
	{
		return esrqmId;
	}

	/**
	 * Method 'setEsrqmId'
	 * 
	 * @param esrqmId
	 */
	public void setEsrqmId(int esrqmId)
	{
		this.esrqmId = esrqmId;
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
	 * Method 'getStatus'
	 * 
	 * @return String
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * Method 'getAssignedTo'
	 * 
	 * @return int
	 */
	public int getAssignedTo()
	{
		return assignedTo;
	}

	/**
	 * Method 'setAssignedTo'
	 * 
	 * @param assignedTo
	 */
	public void setAssignedTo(int assignedTo)
	{
		this.assignedTo = assignedTo;
		this.assignedToNull = false;
	}

	/**
	 * Method 'setAssignedToNull'
	 * 
	 * @param value
	 */
	public void setAssignedToNull(boolean value)
	{
		this.assignedToNull = value;
	}

	/**
	 * Method 'isAssignedToNull'
	 * 
	 * @return boolean
	 */
	public boolean isAssignedToNull()
	{
		return assignedToNull;
	}

	/**
	 * Method 'getRaisedBy'
	 * 
	 * @return int
	 */
	public int getRaisedBy()
	{
		return raisedBy;
	}

	/**
	 * Method 'setRaisedBy'
	 * 
	 * @param raisedBy
	 */
	public void setRaisedBy(int raisedBy)
	{
		this.raisedBy = raisedBy;
		this.raisedByNull = false;
	}

	/**
	 * Method 'setRaisedByNull'
	 * 
	 * @param value
	 */
	public void setRaisedByNull(boolean value)
	{
		this.raisedByNull = value;
	}

	/**
	 * Method 'isRaisedByNull'
	 * 
	 * @return boolean
	 */
	public boolean isRaisedByNull()
	{
		return raisedByNull;
	}

	/**
	 * Method 'getSummary'
	 * 
	 * @return String
	 */
	public String getSummary()
	{
		return summary;
	}

	/**
	 * Method 'setSummary'
	 * 
	 * @param summary
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
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
	}

	/**
	 * Method 'getReServe'
	 * 
	 * @return short
	 */
	public short getReServe()
	{
		return reServe;
	}

	/**
	 * Method 'setReServe'
	 * 
	 * @param reServe
	 */
	public void setReServe(short reServe)
	{
		this.reServe = reServe;
	}

	/**
	 * Method 'getOfferLetter'
	 * 
	 * @return String
	 */
	public String getOfferLetter()
	{
		return offerLetter;
	}

	/**
	 * Method 'setOfferLetter'
	 * 
	 * @param offerLetter
	 */
	public void setOfferLetter(String offerLetter)
	{
		this.offerLetter = offerLetter;
	}

	public String getAssignedToName()
	{
		return assignedToName;
	}

	public String getRaisedByName()
	{
		return raisedByName;
	}

	public Object getCandidateDetails()
	{
		return candidateDetails;
	}

	public void setAssignedToName(String assignedToName)
	{
		this.assignedToName = assignedToName;
	}

	public void setRaisedByName(String raisedByName)
	{
		this.raisedByName = raisedByName;
	}

	public void setCandidateDetails(Object candidateDetails)
	{
		this.candidateDetails = candidateDetails;
	}


	/**
	 * Method 'getCreatedatetime'
	 * 
	 * @return Date
	 */
	public Date getCreatedatetime()
	{
		return createdatetime;
	}

	/**
	 * Method 'setCreatedatetime'
	 * 
	 * @param createdatetime
	 */
	public void setCreatedatetime(Date createdatetime)
	{
		this.createdatetime = createdatetime;
	}

	/**
	 * Method 'getCycle'
	 * 
	 * @return int
	 */
	public int getCycle()
	{
		return cycle;
	}

	/**
	 * Method 'setCycle'
	 * 
	 * @param cycle
	 */
	public void setCycle(int cycle)
	{
		this.cycle = cycle;
		this.cycleNull = false;
	}

	/**
	 * Method 'setCycleNull'
	 * 
	 * @param value
	 */
	public void setCycleNull(boolean value)
	{
		this.cycleNull = value;
	}

	/**
	 * Method 'isCycleNull'
	 * 
	 * @return boolean
	 */
	public boolean isCycleNull()
	{
		return cycleNull;
	}


	/**
	 * Method 'getMessageBody'
	 * 
	 * @return String
	 */
	public String getMessageBody()
	{
		return messageBody;
	}

	/**
	 * Method 'setMessageBody'
	 * 
	 * @param messageBody
	 */
	public void setMessageBody(String messageBody)
	{
		this.messageBody = messageBody;
	}
	
	/**
	 * Method 'getServed'
	 * 
	 * @return int
	 */
	public int getServed()
	{
		return served;
	}

	/**
	 * Method 'setServed'
	 * 
	 * @param served
	 */
	public void setServed(int served)
	{
		this.served = served;
		this.servedNull = false;
	}

	/**
	 * Method 'setServedNull'
	 * 
	 * @param value
	 */
	public void setServedNull(boolean value)
	{
		this.servedNull = value;
	}
	
	/**
	 * Method 'isServedNull'
	 * 
	 * @return boolean
	 */
	public boolean isServedNull()
	{
		return servedNull;
	}

	/**
	 * Method 'getActionTaken'
	 * 
	 * @return int
	 */
	public int getActionTaken()
	{
		return actionTaken;
	}

	/**
	 * Method 'setActionTaken'
	 * 
	 * @param actionTaken
	 */
	public void setActionTaken(int actionTaken)
	{
		this.actionTaken = actionTaken;
		this.actionTakenNull = false;
	}

	/**
	 * Method 'setActionTakenNull'
	 * 
	 * @param value
	 */
	public void setActionTakenNull(boolean value)
	{
		this.actionTakenNull = value;
	}

	/**
	 * Method 'isActionTakenNull'
	 * 
	 * @return boolean
	 */
	public boolean isActionTakenNull()
	{
		return actionTakenNull;
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
		
		if (!(_other instanceof CandidateReq)) {
			return false;
		}
		
		final CandidateReq _cast = (CandidateReq) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (esrqmId != _cast.esrqmId) {
			return false;
		}
		
		if (comments == null ? _cast.comments != comments : !comments.equals( _cast.comments )) {
			return false;
		}
		
		if (status == null ? _cast.status != status : !status.equals( _cast.status )) {
			return false;
		}
		
		if (assignedTo != _cast.assignedTo) {
			return false;
		}

		if (assignedToNull != _cast.assignedToNull) {
			return false;
		}
		
		if (raisedBy != _cast.raisedBy) {
			return false;
		}
		
		if (raisedByNull != _cast.raisedByNull) {
			return false;
		}

		if (summary == null ? _cast.summary != summary : !summary.equals( _cast.summary )) {
			return false;
		}
		
		if (candidateId != _cast.candidateId) {
			return false;
		}
		
		if (reServe != _cast.reServe) {
			return false;
		}
		
		if (createdatetime == null ? _cast.createdatetime != createdatetime : !createdatetime.equals( _cast.createdatetime )) {
			return false;
		}

		if (cycle != _cast.cycle) {
			return false;
		}
		
		if (offerLetter == null ? _cast.offerLetter != offerLetter : !offerLetter.equals( _cast.offerLetter )) {
			return false;
		}
		
		if (cycleNull != _cast.cycleNull) {
			return false;
		}
		if (messageBody == null ? _cast.messageBody != messageBody : !messageBody.equals( _cast.messageBody )) {
			return false;
		}
		if (served != _cast.served) {
			return false;
		}
		
		if (servedNull != _cast.servedNull) {
			return false;
		}
		
		if (actionTaken != _cast.actionTaken) {
			return false;
		}
		
		if (actionTakenNull != _cast.actionTakenNull) {
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
		_hashCode = 29 * _hashCode + esrqmId;
		if (comments != null) {
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		
		if (status != null) {
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		
		_hashCode = 29 * _hashCode + assignedTo;
		_hashCode = 29 * _hashCode + (assignedToNull ? 1 : 0);
		_hashCode = 29 * _hashCode + raisedBy;
		_hashCode = 29 * _hashCode + (raisedByNull ? 1 : 0);
		if (summary != null) {
			_hashCode = 29 * _hashCode + summary.hashCode();
		}
		
		_hashCode = 29 * _hashCode + candidateId;
		if (offerLetter != null) {
			_hashCode = 29 * _hashCode + offerLetter.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) reServe;
		if (createdatetime != null) {
			_hashCode = 29 * _hashCode + createdatetime.hashCode();
		}
		
		_hashCode = 29 * _hashCode + cycle;
		_hashCode = 29 * _hashCode + (cycleNull ? 1 : 0);
		if (messageBody != null) {
			_hashCode = 29 * _hashCode + messageBody.hashCode();
		}
		_hashCode = 29 * _hashCode + served;
		_hashCode = 29 * _hashCode + (servedNull ? 1 : 0);
		_hashCode = 29 * _hashCode + actionTaken;
		_hashCode = 29 * _hashCode + (actionTakenNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return CandidateReqPk
	 */
	public CandidateReqPk createPk()
	{
		return new CandidateReqPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.CandidateReq: " );
		ret.append( "id=" + id );
		ret.append( ", esrqmId=" + esrqmId );
		ret.append( ", comments=" + comments );
		ret.append( ", status=" + status );
		ret.append( ", assignedTo=" + assignedTo );
		ret.append( ", raisedBy=" + raisedBy );
		ret.append( ", summary=" + summary );
		ret.append( ", candidateId=" + candidateId );
		ret.append( ", reServe=" + reServe );
		ret.append( ", offerLetter=" + offerLetter );
		ret.append( ", createdatetime=" + createdatetime );
		ret.append( ", cycle=" + cycle );
		ret.append( ", messageBody=" + messageBody );
		ret.append( ", served=" + served );
		ret.append( ", actionTaken=" + actionTaken );
		return ret.toString();
	}

	@Override
	public int compare(Object o1, Object o2)
	{
		CandidateReq candidate1 = (CandidateReq) o1;
		CandidateReq candidate2 = (CandidateReq) o2;
		int id1 = candidate1.getId();
		int id2 = candidate2.getId();

		if (id1 > id2)
		{
			return -1;
		}
		else if (id1 < id2)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}