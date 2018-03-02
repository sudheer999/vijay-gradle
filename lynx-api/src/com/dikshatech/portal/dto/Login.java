/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.dikshatech.beans.UserLogin;
import com.dikshatech.portal.exceptions.SessionExpiredException;
import com.dikshatech.portal.forms.PortalForm;

public class Login extends PortalForm implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8865506042430810641L;
	/**
	 * This attribute maps to the column ID in the LOGIN table.
	 */
	protected int				id;
	/**
	 * This attribute maps to the column USER_ID in the LOGIN table.
	 */
	protected int				userId;
	/**
	 * This attribute represents whether the primitive attribute userId is null.
	 */
	protected boolean			userIdNull			= true;
	/**
	 * This attribute maps to the column USER_NAME in the LOGIN table.
	 */
	protected String			userName;
	/**
	 * This attribute maps to the column PASSWORD in the LOGIN table.
	 */
	protected String			password;
	private UserLogin			userLogin;
	private int					loginType;
	/**
	 * This attribute maps to the column CANDIDATE_ID in the LOGIN table.
	 */
	protected int				candidateId;
	/**
	 * This attribute represents whether the primitive attribute candidateId is null.
	 */
	protected boolean			candidateIdNull		= true;
	/**
	 * Method 'Login'
	 */
	protected String			newPassword;
	
	
	protected String			status;
	protected String shareRegId;
	protected String message;
	protected String regId;
	protected int[] UserIds;

	public int[] getUserIds() {
		return UserIds;
	}

	public void setUserIds(int[] userIds) {
		UserIds = userIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShareRegId() {
		return shareRegId;
	}

	public void setShareRegId(String shareRegId) {
		this.shareRegId = shareRegId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Login() {}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method 'getUserId'
	 * 
	 * @return int
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Method 'setUserId'
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
		this.userIdNull = false;
	}

	/**
	 * Method 'setUserIdNull'
	 * 
	 * @param value
	 */
	public void setUserIdNull(boolean value) {
		this.userIdNull = value;
	}

	/**
	 * Method 'isUserIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isUserIdNull() {
		return userIdNull;
	}

	/**
	 * Method 'getUserName'
	 * 
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Method 'setUserName'
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Method 'getPassword'
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method 'setPassword'
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Method 'getCandidateId'
	 * 
	 * @return int
	 */
	public int getCandidateId() {
		return candidateId;
	}

	/**
	 * Method 'setCandidateId'
	 * 
	 * @param candidateId
	 */
	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
		this.candidateIdNull = false;
	}

	/**
	 * Method 'setCandidateIdNull'
	 * 
	 * @param value
	 */
	public void setCandidateIdNull(boolean value) {
		this.candidateIdNull = value;
	}

	/**
	 * Method 'isCandidateIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isCandidateIdNull() {
		return candidateIdNull;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){
			return false;
		}
		if (_other == this){
			return true;
		}
		if (!(_other instanceof Login)){
			return false;
		}
		final Login _cast = (Login) _other;
		if (id != _cast.id){
			return false;
		}
		if (userId != _cast.userId){
			return false;
		}
		if (userIdNull != _cast.userIdNull){
			return false;
		}
		if (userName == null ? _cast.userName != userName : !userName.equals(_cast.userName)){
			return false;
		}
		if (password == null ? _cast.password != password : !password.equals(_cast.password)){
			return false;
		}
		if (candidateId != _cast.candidateId){
			return false;
		}
		if (candidateIdNull != _cast.candidateIdNull){
			return false;
		}
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCode = 29 * _hashCode + userId;
		_hashCode = 29 * _hashCode + (userIdNull ? 1 : 0);
		if (userName != null){
			_hashCode = 29 * _hashCode + userName.hashCode();
		}
		if (password != null){
			_hashCode = 29 * _hashCode + password.hashCode();
		}
		_hashCode = 29 * _hashCode + candidateId;
		_hashCode = 29 * _hashCode + (candidateIdNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LoginPk
	 */
	public LoginPk createPk() {
		return new LoginPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("Login: ");
		ret.append("id=" + id);
		ret.append(", userId=" + userId);
		//ret.append(", userName=" + userName);
		//ret.append(", password=" + password);
		//ret.append(", candidateId=" + candidateId);
		return ret.toString();
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public int getLoginType() {
		return loginType;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @author gurunath.rokkam
	 * @param request
	 * @return Login
	 */
	public static Login getLogin(HttpServletRequest request) {
		Login login = (Login) request.getSession(false).getAttribute("login");
		if (login != null && login.getUserId().intValue() > 0) return login;
		throw new SessionExpiredException();
	}
}
