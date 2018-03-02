package com.dikshatech.common.utils;

public class Status {

	public static final String	NEW						= "New";							//1
	public static final String	ACCEPTED				= "Accepted";						//2
	public static final String	APPROVED				= "Approved";
	public static final String	REJECTED				= "Rejected";						//3
	public static final String	OFFERUNDERREVIEW		= "Pending Approval";				//4
	public static final String	OFFERSENT				= "Offer Sent";					//5
	public static final String	OFFERRESENT				= "Offer Re-Sent";					//6
	public static final String	OFFERAPPROVED			= "Approved";						//7
	public static final String	OFFERNOTAPPROVED		= "Rejected By Approver";			//8
	public static final String	NO_SHOW					= "No Show";						//9
	public static final String	UNDER_REVIEW			= "Under Review";					//10
	public static final String	ON_HOLD					= "On Hold";						//11
	public static final String	REJECT					= "Reject";						//12
	public static final String	COMPLETE				= "Complete";						//13
	public static final String	CANCEL					= "Cancel";						//14
	public static final String	ROLLEDON				= "Rolled on";						//15
	public static final String	NOT_SUBMITTED			= "Not Submitted";					//16
	public static final String	SUBMITTED				= "Submitted";						//17
	public static final String	ASSIGNED				= "Assigned";						//18	
	public static final String	COMPLETED				= "Completed";						//19
	public static final String	INPROGRESS				= "In-progress";					//20
	public static final String	CANCELLED				= "Cancelled";						//21
	public static final String	CLOSE					= "Close";							//22
	public static final String	RESOLVED				= "Resolved";						//23
	public static final String	REASSINGN				= "Re-Assign";						//24
	public static final String	REQUESTRAISED			= "Request Raised";				//25
	public static final String	WORKINPROGRESS			= "Work In-Progress";				//26
	public static final String	PROCESSED				= "Processed";						//27
	public static final String	REVOKED					= "Revoked";						//28
	public static final String	REQUESTSAVED			= "Request Saved";					//29
	public static final String	PENDINGAPPROVAL			= "Pending Approval";				//30
	public static final String	CANDIDATECREATED		= "Candidate Created";				//31
	public static final String	NEEDMORETIME			= "Need More Time";				//32
	public static final String	REJECTEDBYAPPROVER		= "Rejected By Approver";			//33
	public static final String	CANCELREQUEST			= "Cancel Request";				//34
	public static final String	PROCESSING				= "Processing";					//35
	public static final String	CANCELINPROGRESS		= "Cancel In-Progress";			//36
	public static final String	CLOSED					= "Closed";
	public static final String	PAFAVAILABLE			= "PAF Available";					//38
	public static final String	PAFSUBMITTED			= "PAF Submitted";					//39
	public static final String	PENDING_APPRAISER		= "Pending - Appraiser";			//40
	public static final String	PENDING_RMG				= "Pending - RMG";					//41
	public static final String	REJECTEDBYAPPRAISER		= "Rejected By Appraiser";			//42
	public static final String	REJECTEDBYRMG			= "Rejected By RMG";				//43
	public static final String	REOPENED				= "Reopened";						//44
	public static final int		AT_APPROVED				= 1;
	public static final int		AT_REJECTED				= 2;
	public static final int		AT_ACCEPT				= 1;
	public static final int		AT_REJECT				= 2;
	public static final int		AT_ONHOLD				= 3;
	public static final String	RESIGNED				= "Resigned";						//45
	public static final String	PENDING_RM				= "Pending with RM";				//46
	public static final String	SERVING_NOTICE_PERIOD	= "Serving Notice period";			//47
	public static final String	EXIT_FORMALITIES		= "Exit formalities in-process";	//48
	public static final String	RELIEVED				= "Relieved";						//49
	public static final String	SEPERATED				= "Separated";						//50
	public static final String	WITHDRAWN				= "Withdrawn";						//51
	public static final String	WITHDRAWN_RAISED		= "Withdrawn Raised";				//52
	public static final String	WITHDRAWN_IN_PROGRESS	= "Withdrawn In-Progress";			//53
	// salary reconciliation
	public static final String	GENERATED				= "Generated";						//54
	public static final String	RESUBMITTED				= "Re-Submitted";					//55
	public static final String UPDATED					= "Updated";						//56
	public static final String COMPLETEDWITHOUTLWP					= "CompletedwithoutLWP";
	public static String getStatus(int id) {
		String status = "";
		switch (id) {
			case 1:
				status = NEW;
				break;
			case 2:
				status = ACCEPTED;
				break;
			case 3:
				status = REJECTED;
				break;
			case 4:
				status = OFFERUNDERREVIEW;
				break;
			case 5:
				status = OFFERSENT;
				break;
			case 6:
				status = OFFERRESENT;
				break;
			case 7:
				status = OFFERAPPROVED;
				break;
			case 8:
				status = OFFERNOTAPPROVED;
				break;
			case 9:
				status = NO_SHOW;
				break;
			case 10:
				status = UNDER_REVIEW;
				break;
			case 11:
				status = ON_HOLD;
				break;
			case 12:
				status = REJECT;
				break;
			case 13:
				status = COMPLETE;
				break;
			case 14:
				status = CANCEL;
				break;
			case 15:
				status = ROLLEDON;
				break;
			case 16:
				status = NOT_SUBMITTED;
				break;
			case 17:
				status = SUBMITTED;
				break;
			case 18:
				status = ASSIGNED;
				break;
			case 19:
				status = COMPLETED;
				break;
			case 20:
				status = INPROGRESS;
				break;
			case 21:
				status = CANCELLED;
				break;
			case 22:
				status = CLOSE;
				break;
			case 23:
				status = RESOLVED;
				break;
			case 24:
				status = REASSINGN;
				break;
			case 25:
				status = REQUESTRAISED;
				break;
			case 26:
				status = WORKINPROGRESS;
				break;
			case 27:
				status = PROCESSED;
				break;
			case 28:
				status = REVOKED;
				break;
			case 29:
				status = REQUESTSAVED;
				break;
			case 30:
				status = PENDINGAPPROVAL;
				break;
			case 31:
				status = CANDIDATECREATED;
				break;
			case 32:
				status = NEEDMORETIME;
				break;
			case 33:
				status = REJECTEDBYAPPROVER;
				break;
			case 34:
				status = CANCELREQUEST;
				break;
			case 35:
				status = PROCESSING;
				break;
			case 36:
				status = CANCELINPROGRESS;
				break;
			case 37:
				status = CLOSED;
				break;
			case 38:
				status = PAFAVAILABLE;
				break;
			case 39:
				status = PAFSUBMITTED;
				break;
			case 40:
				status = PENDING_APPRAISER;
				break;
			case 41:
				status = PENDING_RMG;
				break;
			case 42:
				status = REJECTEDBYAPPRAISER;
				break;
			case 43:
				status = REJECTEDBYRMG;
				break;
			case 44:
				status = REOPENED;
				break;
			case 45:
				status = RESIGNED;
				break;
			case 46:
				status = PENDING_RM;
				break;
			case 47:
				status = SERVING_NOTICE_PERIOD;
				break;
			case 48:
				status = EXIT_FORMALITIES;
				break;
			case 49:
				status = RELIEVED;
				break;
			case 50:
				status = SEPERATED;
				break;
			case 51:
				status = WITHDRAWN;
				break;
			case 52:
				status = WITHDRAWN_RAISED;
				break;
			case 53:
				status = WITHDRAWN_IN_PROGRESS;
				break;
			case 54:
				status = GENERATED;
				break;
			case 55:
				status = RESUBMITTED;
				break;
			case 56:
				status= UPDATED;
		}
		return status;
	}

	public static int getStatusId(String status) {
		if (status.equals(NEW)) return 1;
		if (status.equals(ACCEPTED)) return 2;
		if (status.equals(REJECTED)) return 3;
		if (status.equals(OFFERUNDERREVIEW)) return 4;
		if (status.equals(OFFERSENT)) return 5;
		if (status.equals(OFFERRESENT)) return 6;
		if (status.equals(OFFERAPPROVED)) return 7;
		if (status.equals(OFFERNOTAPPROVED)) return 8;
		if (status.equals(NO_SHOW)) return 9;
		if (status.equals(UNDER_REVIEW)) return 10;
		if (status.equals(ON_HOLD)) return 11;
		if (status.equals(REJECT)) return 12;
		if (status.equals(COMPLETE)) return 13;
		if (status.equals(CANCEL)) return 14;
		if (status.equals(AT_APPROVED)) return 1;
		if (status.equals(AT_REJECTED)) return 2;
		if (status.equals(ROLLEDON)) return 15;
		if (status.equals(NOT_SUBMITTED)) return 16;
		if (status.equals(SUBMITTED)) return 17;
		if (status.equals(ASSIGNED)) return 18;
		if (status.equals(COMPLETED)) return 19;
		if (status.equals(INPROGRESS)) return 20;
		if (status.equals(CANCELLED)) return 21;
		if (status.equals(CLOSE)) return 22;
		if (status.equals(RESOLVED)) return 23;
		if (status.equals(REASSINGN)) return 24;
		if (status.equals(REQUESTRAISED)) return 25;
		if (status.equals(WORKINPROGRESS)) return 26;
		if (status.equals(PROCESSED)) return 27;
		if (status.equals(REVOKED)) return 28;
		if (status.equals(REQUESTSAVED)) return 29;
		if (status.equals(PENDINGAPPROVAL)) return 30;
		if (status.equals(CANDIDATECREATED)) return 31;
		if (status.equals(NEEDMORETIME)) return 32;
		if (status.equals(REJECTEDBYAPPROVER)) return 33;
		if (status.equals(CANCELREQUEST)) return 34;
		if (status.equals(PROCESSING)) return 35;
		if (status.equals(CANCELINPROGRESS)) return 36;
		if (status.equals(CLOSED)) return 37;
		if (status.equals(PAFAVAILABLE)) return 38;
		if (status.equals(PAFSUBMITTED)) return 39;
		if (status.equals(PENDING_APPRAISER)) return 40;
		if (status.equals(PENDING_RMG)) return 41;
		if (status.equals(REJECTEDBYAPPRAISER)) return 42;
		if (status.equals(REJECTEDBYRMG)) return 43;
		if (status.equals(REOPENED)) return 44;
		if (status.equals(RESIGNED)) return 45;
		if (status.equals(PENDING_RM)) return 45;
		if (status.equals(SERVING_NOTICE_PERIOD)) return 47;
		if (status.equals(EXIT_FORMALITIES)) return 48;
		if (status.equals(RELIEVED)) return 49;
		if (status.equals(SEPERATED)) return 50;
		if (status.equals(WITHDRAWN)) return 51;
		if (status.equals(WITHDRAWN_RAISED)) return 52;
		if (status.equals(WITHDRAWN_IN_PROGRESS)) return 53;
		if (status.equals(GENERATED)) return 54;
		if (status.equals(RESUBMITTED)) return 55;
		if (status.equals(UPDATED)) return 56;
		return 0;
	}
}