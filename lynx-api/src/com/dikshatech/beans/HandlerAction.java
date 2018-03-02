package com.dikshatech.beans;

import java.io.Serializable;

public class HandlerAction implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4900773018822733925L;
	protected int				reSend;
	protected int				viewOffer;
	protected int				approve;
	protected int				reject;
	protected int				assign;
	protected int				complete;
	protected int				onHold;
	protected int				reassign;
	protected int				resolved;
	protected int				closed;
	//for handling the visibility of AVAIL CHANGE WITHDRAW CANCEL buttons
	private boolean				availVisible;
	private boolean				changeVisible;
	private boolean				withdrawVisible;
	private boolean				cancelVisible;
	private int					assignButtonForHandlerOrApprover;

	public int getReSend() {
		return reSend;
	}

	public void setReSend(int reSend) {
		this.reSend = reSend;
	}

	public int getViewOffer() {
		return viewOffer;
	}

	public void setViewOffer(int viewOffer) {
		this.viewOffer = viewOffer;
	}

	public int getApprove() {
		return approve;
	}

	public void setApprove(int approve) {
		this.approve = approve;
	}

	public int getReject() {
		return reject;
	}

	public void setReject(int reject) {
		this.reject = reject;
	}

	public int getAssign() {
		return assign;
	}

	public void setAssign(int assign) {
		this.assign = assign;
	}

	public int getOnHold() {
		return onHold;
	}

	public void setOnHold(int onHold) {
		this.onHold = onHold;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}

	public int getReassign() {
		return reassign;
	}

	public void setReassign(int reassign) {
		this.reassign = reassign;
	}

	public int getResolved() {
		return resolved;
	}

	public void setResolved(int resolved) {
		this.resolved = resolved;
	}

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public boolean isAvailVisible() {
		return availVisible;
	}

	public void setAvailVisible(boolean availVisible) {
		this.availVisible = availVisible;
	}

	public boolean isChangeVisible() {
		return changeVisible;
	}

	public void setChangeVisible(boolean changeVisible) {
		this.changeVisible = changeVisible;
	}

	public boolean isWithdrawVisible() {
		return withdrawVisible;
	}

	public void setWithdrawVisible(boolean withdrawVisible) {
		this.withdrawVisible = withdrawVisible;
	}

	public boolean isCancelVisible() {
		return cancelVisible;
	}

	public void setCancelVisible(boolean cancelVisible) {
		this.cancelVisible = cancelVisible;
	}

	/**
	 * @param assignButtonForHandlerOrApprover the assignButtonForHandlerOrApprover to set
	 */
	public void setAssignButtonForHandlerOrApprover(
			int assignButtonForHandlerOrApprover) {
		this.assignButtonForHandlerOrApprover = assignButtonForHandlerOrApprover;
	}

	/**
	 * @return the assignButtonForHandlerOrApprover
	 */
	public int getAssignButtonForHandlerOrApprover() {
		return assignButtonForHandlerOrApprover;
	}
	
	
}
