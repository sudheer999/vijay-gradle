package com.dikshatech.portal.forms;

import java.util.List;
import com.dikshatech.portal.dto.FbpDetails;
import com.dikshatech.portal.dto.FbpReq;
import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.Status;

import java.util.ArrayList;

public class FBPForm extends PortalForm {
	
	private static final long serialVersionUID = 1L;

	private int fbpConfId;
	
	

	private int fbpId;
	
	/**
	 * String of the format Lta=500;TPA=1000;MA=1500;CEA=500;MV=2500;TRA=900;OA=1500;
	 */
	private String benefitsDetails;

	private int id;
	private int userID;
	private int levelId;
	private int todoCount;
	private String level,empName;
	private boolean newFlag,approveFlag,handleFlag,yearlyFlag;
	private List<FbpDetails> benefits;
	private List<FbpReq> requests;
	private String frequent;
	private String comments;
	private List<Handlers> handlers;
	private List<Status> status;
	private float fbpAmt;
	
	//Following parameter are used while getting FBP report
	private String month;
	private String year;
	private String type;
	private int empId;
	
	private String oaAmt;
	
	private String vpf;
	
	
	

	public String getVpf() {
		return vpf;
	}

	public void setVpf(String vpf) {
		this.vpf = vpf;
	}

	public String getOaAmt() {
		return oaAmt;
	}

	public void setOaAmt(String oaAmt) {
		this.oaAmt = oaAmt;
	}

	public boolean isYearlyFlag() {
		return yearlyFlag;
	}

	public void setYearlyFlag(boolean yearlyFlag) {
		this.yearlyFlag = yearlyFlag;
	}

	public boolean isApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(boolean approveFlag) {
		this.approveFlag = approveFlag;
	}

	public boolean isHandleFlag() {
		return handleFlag;
	}

	public void setHandleFlag(boolean handleFlag) {
		this.handleFlag = handleFlag;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public float getFbpAmt() {
		return fbpAmt;
	}

	public void setFbpAmt(float fbpAmt) {
		this.fbpAmt = fbpAmt;
	}

	/**
	 * LevelName for the receive of the FBP CONFIF for editing the master
	 */
	private String levelName;

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
 	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public int getFbpConfId() {
		return fbpConfId;
	}
	public void setFbpConfId(int fbpConfId) {
		this.fbpConfId = fbpConfId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getFrequent() {
		return frequent;
	}
	public void setFrequent(String frequent) {
		this.frequent = frequent;
	}

	private int assigneeId;
	
	public int getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}
	public boolean isNewFlag() {
		return newFlag;
	}
	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}
	public int getFbpId() {
		return fbpId;
	}
	public void setFbpId(int fbpId) {
		this.fbpId = fbpId;
	}
	public String getBenefitsDetails() {
		return benefitsDetails;
	}
	public void setBenefitsDetails(String benefitsDetails) {
		this.benefitsDetails = benefitsDetails;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public List<FbpDetails> getBenefits() {
		return benefits;
	}
	public void setBenefits(List<FbpDetails> benefits) {
		this.benefits = benefits;
	}
	
	public void setBenefits(FbpDetails[] benefits) {
		this.benefits = new ArrayList<FbpDetails>();
		
		for(FbpDetails element:benefits)
			this.benefits.add(element);
	}

	public List<FbpReq> getRequests() {
		return requests;
	}
	public void setRequests(List<FbpReq> requests) {
		this.requests = requests;
	}

	public void setRequests(FbpReq[] requests) {
		this.requests = new ArrayList<FbpReq>();
		
		for(FbpReq element:requests)
			this.requests.add(element);
	}
	public List<Handlers> getHandlers() {
		return handlers;
	}
	public void setHandlers(List<Handlers> handlers) {
		this.handlers = handlers;
	}
	public List<Status> getStatus() {
		return status;
	}
	public void setStatus(List<Status> status) {
		this.status = status;
	}
	public int getTodoCount() {
		return todoCount;
	}
	public void setTodoCount(int todoCount) {
		this.todoCount = todoCount;
	}
}
