package com.dikshatech.portal.forms;

import java.util.ArrayList;
import java.util.List;

import com.dikshatech.beans.TaxBenefitDetail;
import com.dikshatech.beans.TaxBenefitHistory;
import com.dikshatech.portal.dto.TaxBenefitDeclaration;
import com.dikshatech.portal.dto.TaxBenefitReq;
import com.dikshatech.portal.dto.Tds;

public class TaxBenefitForm extends PortalForm{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Field added to support return values for Tax Benefit Receive 
	 * 
	 */
	protected boolean newFlag,handleFlag;
	protected int toDoCount;
	protected int empId;
	protected String empName;
	protected String department,division,levelName,designation;
	protected List<TaxBenefitDetail> taxBenefitDetails;
	protected List<TaxBenefitHistory> taxBenefitHistory;
	
	protected List<TaxBenefitReq> requests;
	
	protected int count;
	protected List<Tds> tds;
	protected String[] tdsArray;
	
	public String[] getTdsArray() {
		return tdsArray;
	}

	public void setTdsArray(String[] tdsArray) {
		this.tdsArray = tdsArray;
	}

	public List<Tds> getTds() {
		return tds;
	}

	public void setTds(List<Tds> tds) {
		this.tds = tds;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Field to return the newly added tax benefit declarations
	 */
	private List<TaxBenefitDeclaration> taxBenefitDeclaration;
	
	/**
	 * Fields to accept parameters from UI for save/submit
	 */
	private String fbpBenefit;
	private String taxBenefit,mainComment,financialYear;
	protected int taxBenefitRequestId;
	
	
	public String getFbpBenefit() {
		return fbpBenefit;
	}

	public void setFbpBenefit(String fbpBenefit) {
		this.fbpBenefit = fbpBenefit;
	}

	public int getToDoCount() {
		return toDoCount;
	}

	public void setToDoCount(int toDoCount) {
		this.toDoCount = toDoCount;
	}

	public boolean isHandleFlag() {
		return handleFlag;
	}

	public void setHandleFlag(boolean handleFlag) {
		this.handleFlag = handleFlag;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public List<TaxBenefitDetail> getTaxBenefitDetails() {
		return taxBenefitDetails;
	}

	public void setTaxBenefitDetails(List<TaxBenefitDetail> taxBenefitDetails) {
		this.taxBenefitDetails = taxBenefitDetails;
	}

	public List<TaxBenefitHistory> getTaxBenefitHistory() {
		return taxBenefitHistory;
	}

	public void setTaxBenefitHistory(List<TaxBenefitHistory> taxBenefitHistory) {
		this.taxBenefitHistory = taxBenefitHistory;
	}

	public boolean isNewFlag() {
		return newFlag;
	}

	

	

	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}
	
	public boolean getNewFlag() {
		return this.newFlag;
	} 

	public String getFinancialYear() {
			return financialYear;
			//		return "2017-2018";
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getMainComment() {
		return mainComment;
	}

	public void setMainComment(String mainComment) {
		this.mainComment = mainComment;
	}

	public List<TaxBenefitDeclaration> getTaxBenefitDeclaration() {
		return taxBenefitDeclaration;
	}

	public void setTaxBenefitDeclaration(
			List<TaxBenefitDeclaration> taxBenefitDeclaration) {
		this.taxBenefitDeclaration = taxBenefitDeclaration;
	}
	
	public List<TaxBenefitReq> getRequests() {
		return requests;
	}

	public void setRequests(List<TaxBenefitReq> requests) {
		this.requests = requests;
	}

	public void setRequests(TaxBenefitReq[] requests) {
		this.requests=new ArrayList<TaxBenefitReq>();
		for(TaxBenefitReq element:requests){
			this.requests.add(element);
		}
	}
	
	public int getTaxBenefitRequestId() {
		return taxBenefitRequestId;
	}

	public void setTaxBenefitRequestId(int taxBenefitRequestId) {
		this.taxBenefitRequestId = taxBenefitRequestId;
	}
	public String getTaxBenefit() {
		return taxBenefit;
	}

	public void setTaxBenefit(String taxBenefit) {
		this.taxBenefit = taxBenefit;
	}

	



	
}
