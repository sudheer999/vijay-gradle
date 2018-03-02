package com.dikshatech.beans;

public class ResumeManagementBean {
	/** 
	 * This attribute maps to the column ID in the RESUME_MANAGEMENT table.
	 */
	protected int id;
	
	protected String skillSet;

	/** 
	 * This attribute maps to the column BILL_RATE in the RESUME_MANAGEMENT table.
	 */
	protected String billRate;

	/** 
	 * This attribute maps to the column PAY_RATE in the RESUME_MANAGEMENT table.
	 */
	protected String payRate;

	/** 
	 * This attribute maps to the column CITY in the RESUME_MANAGEMENT table.
	 */
	protected String city;

	/** 
	 * This attribute maps to the column STATE in the RESUME_MANAGEMENT table.
	 */
	protected String state;

	/** 
	 * This attribute maps to the column RELOCATION in the RESUME_MANAGEMENT table.
	 */
	protected int relocation;
	
	/** 
	 * This attribute maps to the column E_MAILID in the RESUME_MANAGEMENT table.
	 */
	protected String eMailid;	
	
	/** 
	 * This attribute maps to the column CELL_NO in the RESUME_MANAGEMENT table.
	 */
	protected String cellNo;

	/** 
	 * This attribute maps to the column RECRUT_ID in the RESUME_MANAGEMENT table.
	 */
	protected int recrutId;

	/** 
	 * This attribute maps to the column RESUME in the RESUME_MANAGEMENT table.
	 */
	protected String resume;

	/** 
	 * This attribute maps to the column HOME_PHONE_NUMBER in the RESUME_MANAGEMENT table.
	 */
	protected String homePhoneNumber;
	/** 
	 * This attribute maps to the column HOME_PHONE_NUMBER in the RESUME_MANAGEMENT table.
	 */
	/** 
	 * This attribute maps to the column VISA_TYPE in the RESUME_MANAGEMENT table.
	 */
	protected String visaType;
	
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	protected String name;
	protected String recrutName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}
	public String getBillRate() {
		return billRate;
	}
	public void setBillRate(String billRate) {
		this.billRate = billRate;
	}
	public String getPayRate() {
		return payRate;
	}
	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getRelocation() {
		return relocation;
	}
	public void setRelocation(int relocation) {
		this.relocation = relocation;
	}
	public String geteMailid() {
		return eMailid;
	}
	public void seteMailid(String eMailid) {
		this.eMailid = eMailid;
	}
	public String getCellNo() {
		return cellNo;
	}
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}
	public int getRecrutId() {
		return recrutId;
	}
	public void setRecrutId(int recrutId) {
		this.recrutId = recrutId;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}
	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRecrutName() {
		return recrutName;
	}
	public void setRecrutName(String recrutName) {
		this.recrutName = recrutName;
	}
	

	
}
