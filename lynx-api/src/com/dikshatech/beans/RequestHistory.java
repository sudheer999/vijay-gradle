package com.dikshatech.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RequestHistory implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6266095802856269425L;
	protected String			offerMadeOn;
	protected String			offerMadeBy;
	protected String			rejectedOn;
	protected String			reOfferApprovedOn;
	protected String			reOfferApprovedBy;
	protected String			reOfferOn;
	protected String			reOfferMadeBy;
	protected String			acceptedOn;
	private String				offerMadeOnTime;
	private String				rejectedOnTime;
	private String				reOfferApprovedOnTime;
	private String				reOfferOnTime;
	private String				acceptedOnTime;
	private Approval[]			approvals;

	public Approval[] getApprovals() {
		return approvals;
	}

	public void setApprovals(Approval[] approvals) {
		this.approvals = approvals;
	}

	public String getAcceptedOn() {
		return acceptedOn;
	}

	public void setAcceptedOn(String acceptedOn) {
		this.acceptedOn = acceptedOn;
		this.acceptedOnTime=getTime(acceptedOn);
	}

	public String getOfferMadeOn() {
		return offerMadeOn;
	}

	public void setOfferMadeOn(String offerMadeOn) {
		this.offerMadeOn = offerMadeOn;
		this.offerMadeOnTime=getTime(offerMadeOn);
	}

	public String getOfferMadeBy() {
		return offerMadeBy;
	}

	public void setOfferMadeBy(String offerMadeBy) {
		this.offerMadeBy = offerMadeBy;
	}

	public String getRejectedOn() {
		return rejectedOn;
	}

	public void setRejectedOn(String rejectedOn) {
		this.rejectedOn = rejectedOn;
		this.rejectedOnTime=getTime(rejectedOn);
	}

	public String getReOfferApprovedOn() {
		return reOfferApprovedOn;
	}

	public void setReOfferApprovedOn(String reOfferApprovedOn) {
		this.reOfferApprovedOn = reOfferApprovedOn;
		this.reOfferApprovedOnTime=getTime(reOfferApprovedOn);
	}

	public String getReOfferApprovedBy() {
		return reOfferApprovedBy;
	}

	public void setReOfferApprovedBy(String reOfferApprovedBy) {
		this.reOfferApprovedBy = reOfferApprovedBy;
	}

	public String getReOfferOn() {
		return reOfferOn;
	}

	public void setReOfferOn(String reOfferOn) {
		this.reOfferOn = reOfferOn;
		this.reOfferOnTime=getTime(reOfferOn);
	}

	public String getReOfferMadeBy() {
		return reOfferMadeBy;
	}

	public void setReOfferMadeBy(String reOfferMadeBy) {
		this.reOfferMadeBy = reOfferMadeBy;
	}

	public String getOfferMadeOnTime() {
		return offerMadeOnTime;
	}

	public String getRejectedOnTime() {
		return rejectedOnTime;
	}

	public String getReOfferApprovedOnTime() {
		return reOfferApprovedOnTime;
	}

	public String getReOfferOnTime() {
		return reOfferOnTime;
	}

	public String getAcceptedOnTime() {
		return acceptedOnTime;
	}

	public void setOfferMadeOnTime(String offerMadeOnTime) {
		this.offerMadeOnTime = offerMadeOnTime;
	}

	public void setRejectedOnTime(String rejectedOnTime) {
		this.rejectedOnTime = rejectedOnTime;
	}

	public void setReOfferApprovedOnTime(String reOfferApprovedOnTime) {
		this.reOfferApprovedOnTime = reOfferApprovedOnTime;
	}

	public void setReOfferOnTime(String reOfferOnTime) {
		this.reOfferOnTime = reOfferOnTime;
	}

	public void setAcceptedOnTime(String acceptedOnTime) {
		this.acceptedOnTime = acceptedOnTime;
	}

	private String getTime(String date) {
		try{
			return new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(date).getTime()+"";
		} catch (ParseException e){}
		return "0";
	}
}