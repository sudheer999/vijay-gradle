package com.dikshatech.portal.dto;


public class BonusReconciliationHistory {
	
	/** 
	 * This attribute maps to the column ID in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column DEP_ID in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected int bonusId;

	/** 
	 * This attribute maps to the column USER_ID in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected int userId;

	/** 
	 * This attribute maps to the column ADDED_BY in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected int addedBy;

	/** 
	 * This attribute maps to the column MODIFIED_BY in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected int modifiedBy;

	/** 
	 * This attribute maps to the column MANAGER_ID in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected int managerId;

	/** 
	 * This attribute maps to the column MANAGER_NAME in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String managerName;

	/** 
	 * This attribute maps to the column PROJECT_NAME in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String projectName;

	/** 
	 * This attribute maps to the column QUATERELY_BONUS in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String qBonus;
	/** 
	 * This attribute maps to the column COMPANY_BONUS in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String cBonus;
	/** 
	 * This attribute maps to the column QUA_BONUS_HISTORY in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String qBonusHistory;
	/** 
	 * This attribute maps to the column COM_BONUS_HISTORY in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String cBonusHistory;
	/** 
	 * This attribute maps to the column CURRENCY in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String currency;

	/** 
	 * This attribute maps to the column CURRENCY_HISTORY in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String currencyHistory;

	/** 
	 * This attribute maps to the column QUA_BONUS_COMPUTED in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String qBonusComputed;
	/** 
	 * This attribute maps to the column COM_BONUS_COMPUTED in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String cBonusComputed;
	/** 
	 * This attribute maps to the column COMMENTS in the BONUS_RECONCILIATION_HISTORY table.
	 */
	protected String comments;

	/**
	 * Method 'BonusReconciliationHistory'
	 * 
	 */
	public BonusReconciliationHistory()
	{
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getBonusId() {
		return bonusId;
	}

	
	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}

	
	public int getUserId() {
		return userId;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	public int getAddedBy() {
		return addedBy;
	}

	
	public void setAddedBy(int addedBy) {
		this.addedBy = addedBy;
	}

	
	public int getModifiedBy() {
		return modifiedBy;
	}

	
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	public int getManagerId() {
		return managerId;
	}

	
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	
	public String getManagerName() {
		return managerName;
	}

	
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	
	public String getProjectName() {
		return projectName;
	}

	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	


	
	public String getCurrency() {
		return currency;
	}

	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
	public String getCurrencyHistory() {
		return currencyHistory;
	}

	
	public void setCurrencyHistory(String currencyHistory) {
		this.currencyHistory = currencyHistory;
	}

	

	
	public String getqBonus() {
		return qBonus;
	}


	
	public void setqBonus(String qBonus) {
		this.qBonus = qBonus;
	}


	
	public String getcBonus() {
		return cBonus;
	}


	
	public void setcBonus(String cBonus) {
		this.cBonus = cBonus;
	}


	
	public String getqBonusHistory() {
		return qBonusHistory;
	}


	
	public void setqBonusHistory(String qBonusHistory) {
		this.qBonusHistory = qBonusHistory;
	}


	
	public String getcBonusHistory() {
		return cBonusHistory;
	}


	
	public void setcBonusHistory(String cBonusHistory) {
		this.cBonusHistory = cBonusHistory;
	}


	
	public String getqBonusComputed() {
		return qBonusComputed;
	}


	
	public void setqBonusComputed(String qBonusComputed) {
		this.qBonusComputed = qBonusComputed;
	}


	
	public String getcBonusComputed() {
		return cBonusComputed;
	}


	
	public void setcBonusComputed(String cBonusComputed) {
		this.cBonusComputed = cBonusComputed;
	}


	public String getComments() {
		return comments;
	}

	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof BonusReconciliationHistory)) {
			return false;
		}
		
		final BonusReconciliationHistory _cast = (BonusReconciliationHistory) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (bonusId != _cast.bonusId) {
			return false;
		}
		
		if (userId != _cast.userId) {
			return false;
		}
		
		if (addedBy != _cast.addedBy) {
			return false;
		}
		
		if (modifiedBy != _cast.modifiedBy) {
			return false;
		}
		
		if (managerId != _cast.managerId) {
			return false;
		}
		
		if (managerName == null ? _cast.managerName != managerName : !managerName.equals( _cast.managerName )) {
			return false;
		}
		
		if (projectName == null ? _cast.projectName != projectName : !projectName.equals( _cast.projectName )) {
			return false;
		}
		
		if (qBonus == null ? _cast.qBonus != qBonus : !qBonus.equals( _cast.qBonus )) {
			return false;
		}
		if (cBonus == null ? _cast.cBonus != cBonus : !cBonus.equals( _cast.cBonus )) {
			return false;
		}
		
		if (qBonusHistory == null ? _cast.qBonusHistory != qBonusHistory : !qBonusHistory.equals( _cast.qBonusHistory )) {
			return false;
		}
		if (cBonusHistory == null ? _cast.cBonusHistory != cBonusHistory : !cBonusHistory.equals( _cast.cBonusHistory )) {
			return false;
		}
		
		if (currency == null ? _cast.currency != currency : !currency.equals( _cast.currency )) {
			return false;
		}
		
		if (currencyHistory == null ? _cast.currencyHistory != currencyHistory : !currencyHistory.equals( _cast.currencyHistory )) {
			return false;
		}
		
		if (qBonusComputed == null ? _cast.qBonusComputed != qBonusComputed : !qBonusComputed.equals( _cast.qBonusComputed )) {
			return false;
		}
		if (cBonusComputed == null ? _cast.cBonusComputed != cBonusComputed : !cBonusComputed.equals( _cast.cBonusComputed )) {
			return false;
		}
		
		if (comments == null ? _cast.comments != comments : !comments.equals( _cast.comments )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCode = 29 * _hashCode + bonusId;
		_hashCode = 29 * _hashCode + userId;
		_hashCode = 29 * _hashCode + addedBy;
		_hashCode = 29 * _hashCode + modifiedBy;
		_hashCode = 29 * _hashCode + managerId;
		if (managerName != null) {
			_hashCode = 29 * _hashCode + managerName.hashCode();
		}
		
		if (projectName != null) {
			_hashCode = 29 * _hashCode + projectName.hashCode();
		}
		
		if (qBonus != null) {
			_hashCode = 29 * _hashCode + qBonus.hashCode();
		}
		if (cBonus != null) {
			_hashCode = 29 * _hashCode + cBonus.hashCode();
		}
		if (qBonusHistory != null) {
			_hashCode = 29 * _hashCode + qBonusHistory.hashCode();
		}
		if (cBonusHistory != null) {
			_hashCode = 29 * _hashCode + cBonusHistory.hashCode();
		}
		if (currency != null) {
			_hashCode = 29 * _hashCode + currency.hashCode();
		}
		
		if (currencyHistory != null) {
			_hashCode = 29 * _hashCode + currencyHistory.hashCode();
		}
		
		if (qBonusComputed != null) {
			_hashCode = 29 * _hashCode + qBonusComputed.hashCode();
		}
		if (cBonusComputed != null) {
			_hashCode = 29 * _hashCode + cBonusComputed.hashCode();
		}
		
		if (comments != null) {
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemHistoryPk
	 */
	public BonusReconciliationHistoryPk createPk()
	{
		return new BonusReconciliationHistoryPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.BonusReconciliationHistory: ");
		ret.append( "id=" + id );
		ret.append( ", depId=" + bonusId );
		ret.append( ", userId=" + userId );
		ret.append( ", addedBy=" + addedBy );
		ret.append( ", modifiedBy=" + modifiedBy );
		ret.append( ", managerId=" + managerId );
		ret.append( ", managerName=" + managerName );
		ret.append( ", projectName=" + projectName );
		ret.append( ", qBonus=" + qBonus );
		ret.append( ", cBonus=" + cBonus );
		ret.append( ", qBonusHistory=" + qBonusHistory );
		ret.append( ", cBonusHistory=" + cBonusHistory );
		ret.append( ", currency=" + currency );
		ret.append( ", currencyHistory=" + currencyHistory );
		ret.append( ", qBonusComputed=" + qBonusComputed );
		ret.append( ", cBonusComputed=" + cBonusComputed );
		ret.append( ", comments=" + comments );
		return ret.toString();
	}

}
