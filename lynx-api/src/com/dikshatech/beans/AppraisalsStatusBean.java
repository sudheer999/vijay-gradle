package com.dikshatech.beans;

public class AppraisalsStatusBean {

	private int	available;
	private int	submitted;
	private int	in_progress;
	private int	pending_appraiser;
	private int	rejectedByAppraiser;
	private int	pending_rmg;
	private int	rejectedByRMG;
	private int	completed;

	public AppraisalsStatusBean() {}

	public int getSubmitted() {
		return submitted;
	}

	public int getIn_progress() {
		return in_progress;
	}

	public int getPending_appraiser() {
		return pending_appraiser;
	}

	public int getRejectedByAppraiser() {
		return rejectedByAppraiser;
	}

	public int getPending_rmg() {
		return pending_rmg;
	}

	public int getRejectedByRMG() {
		return rejectedByRMG;
	}

	public int getCompleted() {
		return completed;
	}

	public void setSubmitted(int submitted) {
		this.submitted = submitted;
	}

	public void setIn_progress(int inProgress) {
		in_progress = inProgress;
	}

	public void setPending_appraiser(int pendingAppraiser) {
		pending_appraiser = pendingAppraiser;
	}

	public void setRejectedByAppraiser(int rejectedByAppraiser) {
		this.rejectedByAppraiser = rejectedByAppraiser;
	}

	public void setPending_rmg(int pendingRmg) {
		pending_rmg = pendingRmg;
	}

	public void setRejectedByRMG(int rejectedByRMG) {
		this.rejectedByRMG = rejectedByRMG;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getAvailable() {
		return available;
	}
}
