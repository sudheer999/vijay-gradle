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

public class ServiceReqInfo extends  PortalForm  implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the SERVICE_REQ_INFO table.
	 */
	protected int id;
	protected int count;
	

	/** 
	 * This attribute maps to the column ESR_MAP_ID in the SERVICE_REQ_INFO table.
	 */
	protected int esrMapId;

	/** 
	 * This attribute represents whether the primitive attribute esrMapId is null.
	 */
	protected boolean esrMapIdNull = true;

	/** 
	 * This attribute maps to the column ASSIGNED_TO_DIV in the SERVICE_REQ_INFO table.
	 */
	protected int assignedToDiv;

	/** 
	 * This attribute represents whether the primitive attribute assignedToDiv is null.
	 */
	protected boolean assignedToDivNull = true;

	/** 
	 * This attribute maps to the column STATUS in the SERVICE_REQ_INFO table.
	 */
	protected String status;

	/** 
	 * This attribute maps to the column ACTION_BY in the SERVICE_REQ_INFO table.
	 */
	protected int actionBy;

	/** 
	 * This attribute represents whether the primitive attribute actionBy is null.
	 */
	protected boolean actionByNull = true;

	/** 
	 * This attribute maps to the column HD_EST_DATE_RESOLVE in the SERVICE_REQ_INFO table.
	 */
	protected Date hdEstDateResolve;

	/** 
	 * This attribute maps to the column HD_COMMENTS in the SERVICE_REQ_INFO table.
	 */
	protected String hdComments;

	/** 
	 * This attribute maps to the column ESCALATE_TO in the SERVICE_REQ_INFO table.
	 */
	protected int escalateTo;

	/** 
	 * This attribute represents whether the primitive attribute escalateTo is null.
	 */
	protected boolean escalateToNull = true;

	/** 
	 * This attribute maps to the column H_REMARKS in the SERVICE_REQ_INFO table.
	 */
	protected String hRemarks;

	/** 
	 * This attribute maps to the column CREATION_DATETIME in the SERVICE_REQ_INFO table.
	 */
	protected Date creationDatetime;

	/** 
	 * This attribute maps to the column SUMMARY in the SERVICE_REQ_INFO table.
	 */
	protected String summary;

	/** 
	 * This attribute maps to the column DESCRIPTION in the SERVICE_REQ_INFO table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column EST_DATE_RESOLVE in the SERVICE_REQ_INFO table.
	 */
	protected Date estDateResolve;

	/** 
	 * This attribute maps to the column COMMENT in the SERVICE_REQ_INFO table.
	 */
	protected String comment;

	/** 
	 * This attribute maps to the column DEP_SERV_REQ in the SERVICE_REQ_INFO table.
	 */
	protected int depServReq;

	/** 
	 * This attribute represents whether the primitive attribute depServReq is null.
	 */
	protected boolean depServReqNull = true;

	/** 
	 * This attribute maps to the column ASSIGNED_TO in the SERVICE_REQ_INFO table.
	 */
	protected int assignedTo;

	/** 
	 * This attribute represents whether the primitive attribute assignedTo is null.
	 */
	protected boolean assignedToNull = true;

	/** 
	 * This attribute maps to the column SEVERITY in the SERVICE_REQ_INFO table.
	 */
	protected String severity;

	/** 
	 * This attribute maps to the column PRIORITY in the SERVICE_REQ_INFO table.
	 */
	protected String priority;

	/**
	 * Method 'ServiceReqInfo'
	 * 
	 */
	public ServiceReqInfo()
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
	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
	/**
	 * Method 'getEsrMapId'
	 * 
	 * @return int
	 */
	public int getEsrMapId()
	{
		return esrMapId;
	}

	/**
	 * Method 'setEsrMapId'
	 * 
	 * @param esrMapId
	 */
	public void setEsrMapId(int esrMapId)
	{
		this.esrMapId = esrMapId;
		this.esrMapIdNull = false;
	}

	/**
	 * Method 'setEsrMapIdNull'
	 * 
	 * @param value
	 */
	public void setEsrMapIdNull(boolean value)
	{
		this.esrMapIdNull = value;
	}

	/**
	 * Method 'isEsrMapIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isEsrMapIdNull()
	{
		return esrMapIdNull;
	}

	/**
	 * Method 'getAssignedToDiv'
	 * 
	 * @return int
	 */
	public int getAssignedToDiv()
	{
		return assignedToDiv;
	}

	/**
	 * Method 'setAssignedToDiv'
	 * 
	 * @param assignedToDiv
	 */
	public void setAssignedToDiv(int assignedToDiv)
	{
		this.assignedToDiv = assignedToDiv;
		this.assignedToDivNull = false;
	}

	/**
	 * Method 'setAssignedToDivNull'
	 * 
	 * @param value
	 */
	public void setAssignedToDivNull(boolean value)
	{
		this.assignedToDivNull = value;
	}

	/**
	 * Method 'isAssignedToDivNull'
	 * 
	 * @return boolean
	 */
	public boolean isAssignedToDivNull()
	{
		return assignedToDivNull;
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
	 * Method 'getActionBy'
	 * 
	 * @return int
	 */
	public int getActionBy()
	{
		return actionBy;
	}

	/**
	 * Method 'setActionBy'
	 * 
	 * @param actionBy
	 */
	public void setActionBy(int actionBy)
	{
		this.actionBy = actionBy;
		this.actionByNull = false;
	}

	/**
	 * Method 'setActionByNull'
	 * 
	 * @param value
	 */
	public void setActionByNull(boolean value)
	{
		this.actionByNull = value;
	}

	/**
	 * Method 'isActionByNull'
	 * 
	 * @return boolean
	 */
	public boolean isActionByNull()
	{
		return actionByNull;
	}

	/**
	 * Method 'getHdEstDateResolve'
	 * 
	 * @return Date
	 */
	public Date getHdEstDateResolve()
	{
		return hdEstDateResolve;
	}

	/**
	 * Method 'setHdEstDateResolve'
	 * 
	 * @param hdEstDateResolve
	 */
	public void setHdEstDateResolve(Date hdEstDateResolve)
	{
		this.hdEstDateResolve = hdEstDateResolve;
	}

	/**
	 * Method 'getHdComments'
	 * 
	 * @return String
	 */
	public String getHdComments()
	{
		return hdComments;
	}

	/**
	 * Method 'setHdComments'
	 * 
	 * @param hdComments
	 */
	public void setHdComments(String hdComments)
	{
		this.hdComments = hdComments;
	}

	/**
	 * Method 'getEscalateTo'
	 * 
	 * @return int
	 */
	public int getEscalateTo()
	{
		return escalateTo;
	}

	/**
	 * Method 'setEscalateTo'
	 * 
	 * @param escalateTo
	 */
	public void setEscalateTo(int escalateTo)
	{
		this.escalateTo = escalateTo;
		this.escalateToNull = false;
	}

	/**
	 * Method 'setEscalateToNull'
	 * 
	 * @param value
	 */
	public void setEscalateToNull(boolean value)
	{
		this.escalateToNull = value;
	}

	/**
	 * Method 'isEscalateToNull'
	 * 
	 * @return boolean
	 */
	public boolean isEscalateToNull()
	{
		return escalateToNull;
	}

	/**
	 * Method 'getHRemarks'
	 * 
	 * @return String
	 */
	public String getHRemarks()
	{
		return hRemarks;
	}

	/**
	 * Method 'setHRemarks'
	 * 
	 * @param hRemarks
	 */
	public void setHRemarks(String hRemarks)
	{
		this.hRemarks = hRemarks;
	}

	/**
	 * Method 'getCreationDatetime'
	 * 
	 * @return Date
	 */
	public Date getCreationDatetime()
	{
		return creationDatetime;
	}

	/**
	 * Method 'setCreationDatetime'
	 * 
	 * @param creationDatetime
	 */
	public void setCreationDatetime(Date creationDatetime)
	{
		this.creationDatetime = creationDatetime;
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
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Method 'getEstDateResolve'
	 * 
	 * @return Date
	 */
	public Date getEstDateResolve()
	{
		return estDateResolve;
	}

	/**
	 * Method 'setEstDateResolve'
	 * 
	 * @param estDateResolve
	 */
	public void setEstDateResolve(Date estDateResolve)
	{
		this.estDateResolve = estDateResolve;
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
	 * Method 'getDepServReq'
	 * 
	 * @return int
	 */
	public int getDepServReq()
	{
		return depServReq;
	}

	/**
	 * Method 'setDepServReq'
	 * 
	 * @param depServReq
	 */
	public void setDepServReq(int depServReq)
	{
		this.depServReq = depServReq;
		this.depServReqNull = false;
	}

	/**
	 * Method 'setDepServReqNull'
	 * 
	 * @param value
	 */
	public void setDepServReqNull(boolean value)
	{
		this.depServReqNull = value;
	}

	/**
	 * Method 'isDepServReqNull'
	 * 
	 * @return boolean
	 */
	public boolean isDepServReqNull()
	{
		return depServReqNull;
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
	 * Method 'getSeverity'
	 * 
	 * @return String
	 */
	public String getSeverity()
	{
		return severity;
	}

	/**
	 * Method 'setSeverity'
	 * 
	 * @param severity
	 */
	public void setSeverity(String severity)
	{
		this.severity = severity;
	}

	/**
	 * Method 'getPriority'
	 * 
	 * @return String
	 */
	public String getPriority()
	{
		return priority;
	}

	/**
	 * Method 'setPriority'
	 * 
	 * @param priority
	 */
	public void setPriority(String priority)
	{
		this.priority = priority;
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
		
		if (!(_other instanceof ServiceReqInfo)) {
			return false;
		}
		
		final ServiceReqInfo _cast = (ServiceReqInfo) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (esrMapId != _cast.esrMapId) {
			return false;
		}
		
		if (esrMapIdNull != _cast.esrMapIdNull) {
			return false;
		}
		
		if (assignedToDiv != _cast.assignedToDiv) {
			return false;
		}
		
		if (assignedToDivNull != _cast.assignedToDivNull) {
			return false;
		}
		
		if (status == null ? _cast.status != status : !status.equals( _cast.status )) {
			return false;
		}
		
		if (actionBy != _cast.actionBy) {
			return false;
		}
		
		if (actionByNull != _cast.actionByNull) {
			return false;
		}
		
		if (hdEstDateResolve == null ? _cast.hdEstDateResolve != hdEstDateResolve : !hdEstDateResolve.equals( _cast.hdEstDateResolve )) {
			return false;
		}
		
		if (hdComments == null ? _cast.hdComments != hdComments : !hdComments.equals( _cast.hdComments )) {
			return false;
		}
		
		if (escalateTo != _cast.escalateTo) {
			return false;
		}
		
		if (escalateToNull != _cast.escalateToNull) {
			return false;
		}
		
		if (hRemarks == null ? _cast.hRemarks != hRemarks : !hRemarks.equals( _cast.hRemarks )) {
			return false;
		}
		
		if (creationDatetime == null ? _cast.creationDatetime != creationDatetime : !creationDatetime.equals( _cast.creationDatetime )) {
			return false;
		}
		
		if (summary == null ? _cast.summary != summary : !summary.equals( _cast.summary )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (estDateResolve == null ? _cast.estDateResolve != estDateResolve : !estDateResolve.equals( _cast.estDateResolve )) {
			return false;
		}
		
		if (comment == null ? _cast.comment != comment : !comment.equals( _cast.comment )) {
			return false;
		}
		
		if (depServReq != _cast.depServReq) {
			return false;
		}
		
		if (depServReqNull != _cast.depServReqNull) {
			return false;
		}
		
		if (assignedTo != _cast.assignedTo) {
			return false;
		}
		
		if (assignedToNull != _cast.assignedToNull) {
			return false;
		}
		
		if (severity == null ? _cast.severity != severity : !severity.equals( _cast.severity )) {
			return false;
		}
		
		if (priority == null ? _cast.priority != priority : !priority.equals( _cast.priority )) {
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
		_hashCode = 29 * _hashCode + esrMapId;
		_hashCode = 29 * _hashCode + (esrMapIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + assignedToDiv;
		_hashCode = 29 * _hashCode + (assignedToDivNull ? 1 : 0);
		if (status != null) {
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		
		_hashCode = 29 * _hashCode + actionBy;
		_hashCode = 29 * _hashCode + (actionByNull ? 1 : 0);
		if (hdEstDateResolve != null) {
			_hashCode = 29 * _hashCode + hdEstDateResolve.hashCode();
		}
		
		if (hdComments != null) {
			_hashCode = 29 * _hashCode + hdComments.hashCode();
		}
		
		_hashCode = 29 * _hashCode + escalateTo;
		_hashCode = 29 * _hashCode + (escalateToNull ? 1 : 0);
		if (hRemarks != null) {
			_hashCode = 29 * _hashCode + hRemarks.hashCode();
		}
		
		if (creationDatetime != null) {
			_hashCode = 29 * _hashCode + creationDatetime.hashCode();
		}
		
		if (summary != null) {
			_hashCode = 29 * _hashCode + summary.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (estDateResolve != null) {
			_hashCode = 29 * _hashCode + estDateResolve.hashCode();
		}
		
		if (comment != null) {
			_hashCode = 29 * _hashCode + comment.hashCode();
		}
		
		_hashCode = 29 * _hashCode + depServReq;
		_hashCode = 29 * _hashCode + (depServReqNull ? 1 : 0);
		_hashCode = 29 * _hashCode + assignedTo;
		_hashCode = 29 * _hashCode + (assignedToNull ? 1 : 0);
		if (severity != null) {
			_hashCode = 29 * _hashCode + severity.hashCode();
		}
		
		if (priority != null) {
			_hashCode = 29 * _hashCode + priority.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ServiceReqInfoPk
	 */
	public ServiceReqInfoPk createPk()
	{
		return new ServiceReqInfoPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.ServiceReqInfo: " );
		ret.append( "id=" + id );
		ret.append( ", esrMapId=" + esrMapId );
		ret.append( ", assignedToDiv=" + assignedToDiv );
		ret.append( ", status=" + status );
		ret.append( ", actionBy=" + actionBy );
		ret.append( ", hdEstDateResolve=" + hdEstDateResolve );
		ret.append( ", hdComments=" + hdComments );
		ret.append( ", escalateTo=" + escalateTo );
		ret.append( ", hRemarks=" + hRemarks );
		ret.append( ", creationDatetime=" + creationDatetime );
		ret.append( ", summary=" + summary );
		ret.append( ", description=" + description );
		ret.append( ", estDateResolve=" + estDateResolve );
		ret.append( ", comment=" + comment );
		ret.append( ", depServReq=" + depServReq );
		ret.append( ", assignedTo=" + assignedTo );
		ret.append( ", severity=" + severity );
		ret.append( ", priority=" + priority );
		return ret.toString();
	}

}
