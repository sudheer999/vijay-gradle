package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

public class RmgTimeSheetReq implements Serializable{
	
	
	/** 
	 * This attribute maps to the column ID in the DEP_PERDIEM_REQ table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column DEP_ID in the DEP_PERDIEM_REQ table.
	 */
	protected int RMG_TIMESHEET_ID;

	/** 
	 * This attribute maps to the column SEQ_ID in the DEP_PERDIEM_REQ table.
	 */
	protected int seqId;

	/** 
	 * This attribute maps to the column ASSIGNED_TO in the DEP_PERDIEM_REQ table.
	 */
	protected int assignedTo;

	/** 
	 * This attribute maps to the column RECEIVED_ON in the DEP_PERDIEM_REQ table.
	 */
	protected Date receivedOn;

	/** 
	 * This attribute maps to the column SUBMITTED_ON in the DEP_PERDIEM_REQ table.
	 */
	protected Date submittedOn;

	/** 
	 * This attribute maps to the column IS_ESCALATED in the DEP_PERDIEM_REQ table.
	 */
	protected int isEscalated;

	/** 
	 * This attribute represents whether the primitive attribute isEscalated is null.
	 */
	protected boolean isEscalatedNull = true;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRMG_TIMESHEET_ID() {
		return RMG_TIMESHEET_ID;
	}

	public void setRMG_TIMESHEET_ID(int rMG_TIMESHEET_ID) {
		RMG_TIMESHEET_ID = rMG_TIMESHEET_ID;
	}

	public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	public int getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Date getReceivedOn() {
		return receivedOn;
	}

	public void setReceivedOn(Date receivedOn) {
		this.receivedOn = receivedOn;
	}

	public Date getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}

	public int getIsEscalated() {
		return isEscalated;
	}

	public void setIsEscalated(int isEscalated) {
		this.isEscalated = isEscalated;
	}

	public boolean isEscalatedNull() {
		return isEscalatedNull;
	}

	public void setEscalatedNull(boolean isEscalatedNull) {
		this.isEscalatedNull = isEscalatedNull;
	}
	
	public RmgTimeSheetReqPk createPk()
	{
		return new RmgTimeSheetReqPk(id);
	}
	
	

}
