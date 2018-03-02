package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class UserLogin implements Serializable {

	private static final long	serialVersionUID	= -3456781070065515201L;
	private Integer				userId;
	private String				employeeId;
	private String				level;
	private String				designation;
	private int					regionId;
	private String				regionName;
	private int					divisionId;
	private String				divisionName;
	private String				userName;
	private String				firstName;
	private String				lastName;
	private boolean				login;
	private int					offerStatus;
	private int					sequence;
	private String				gender;
	private String        		officialEmaiID;
	private String				unReadNotifications;
	private String				unReadDocked;
	private String				docked				= "false";
	private boolean				isHrd;											//to find out if the logged_in user belongs to HRD... related to ROLL_ON
	private Set<Roles>			roles;

	public Integer getUserId() {
		return userId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getLevel() {
		return level;
	}

	public String getDesignation() {
		return designation;
	}

	public int getRegionId() {
		return regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public String getOfficialEmaiID() {
		return officialEmaiID;
	}

	public void setOfficialEmaiID(String officialEmaiID) {
		this.officialEmaiID = officialEmaiID;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isLogin() {
		return login;
	}

	public void setOfferStatus(int offerStatus) {
		this.offerStatus = offerStatus;
	}

	public int getOfferStatus() {
		return offerStatus;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	/**
	 * @param isHrd
	 *            the isHrd to set
	 */
	public void setIsHrd(boolean isHrd) {
		this.isHrd = isHrd;
	}

	/**
	 * @return the isHrd
	 */
	public boolean getIsHrd() {
		return isHrd;
	}

	public void setDocked(String docked) {
		this.docked = docked;
	}

	public String getDocked() {
		return docked;
	}

	public void setUnReadNotifications(String unReadNotifications) {
		this.unReadNotifications = unReadNotifications;
	}

	public String getUnReadNotifications() {
		return unReadNotifications;
	}

	public void setUnReadDocked(String unReadDocked) {
		this.unReadDocked = unReadDocked;
	}

	public String getUnReadDocked() {
		return unReadDocked;
	}
}
