package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dikshatech.common.utils.PortalUtility;


public class BonusRecReportHistory implements Serializable {
	
	
	
	/** 
	 * This attribute maps to the column ID in the BONUS_REC_REPORT_HISTORY table.
	 */
	private int		id;
	/** 
	 * This attribute maps to the column REP_ID in the BONUS_REC_REPORT_HISTORY table.
	 */
	private int		repId;
	
	/** 
	 * This attribute maps to the column QUATERELY_BONUS in the BONUS_REC_REPORT_HISTORY table.
	 */
	private String	qBonus;
	/** 
	 * This attribute maps to the column QUATERELY_BONUS in the BONUS_REC_REPORT_HISTORY table.
	 */
	private String	cBonus;
	/** 
	 * This attribute maps to the column CURRENCY_TYPE in the BONUS_REC_REPORT_HISTORY table.
	 */
	private short	currencyType;
	/** 
	 * This attribute maps to the column MODIFIED_BY in the BONUS_REC_REPORT_HISTORY table.
	 */
	private String	modifiedBy;
	/** 
	 * This attribute maps to the column MODIFIED_ON in the BONUS_REC_REPORT_HISTORY table.
	 */
	private Date	modifiedOn;
	/** 
	 * This attribute maps to the column COMMENTS in the BONUS_REC_REPORT_HISTORY table.
	 */
	private String	comments;
	/** 
	 * This attribute maps to the column IS_DELETED in the BONUS_REC_REPORT_HISTORY
	 *  table.
	 */
	private short	type;
	private String	qAmount;
	private String	qAmountInr;
	private String	cAmount;
	private String	cAmountInr;
	private String	total;
	private String	currencyName;
	private String	month;
	/**
	 * Method 'DepPerdiemReportHistory'
	 * 
	 */
	public BonusRecReportHistory() {}

	public BonusRecReportHistory(int repId, String qbonus,String cbonus, short currencyType,  String modifiedBy, Date modifiedOn, String comments, short type,String qAmount,String cAmount, String qAmountInr,String cAmountInr, String total,String currencyName) {
		super();
		this.repId = repId;
		this.qBonus = qbonus;
		this.cBonus = cbonus;
		this.qAmount = qAmount;
		this.qAmountInr = qAmountInr;
		this.cAmount = cAmount;
		this.cAmountInr = cAmountInr;
		this.currencyType = currencyType;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.comments = comments;
		this.type = type;
		this.total = total;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getRepId() {
		return repId;
	}
	
	public void setRepId(int repId) {
		this.repId = repId;
	}
	
	
	
	public short getCurrencyType() {
		return currencyType;
	}
	
	public void setCurrencyType(short currencyType) {
		this.currencyType = currencyType;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Date getModifiedOn() {
		return modifiedOn;
	}
	
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public short getType() {
		return type;
	}
	
	public void setType(short type) {
		this.type = type;
	}
	
	
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){ return false; }
		if (_other == this){ return true; }
		if (!(_other instanceof BonusRecReportHistory)){ return false; }
		final BonusRecReportHistory _cast = (BonusRecReportHistory) _other;
		if (id != _cast.id){ return false; }
		if (repId != _cast.repId){ return false; }
		if (qBonus == null ? _cast.qBonus != qBonus : !qBonus.equals(_cast.qBonus)){ return false; }
		if (cBonus == null ? _cast.cBonus != cBonus : !cBonus.equals(_cast.cBonus)){ return false; }
		if (currencyType != _cast.currencyType){ return false; }
		if (modifiedBy == null ? _cast.modifiedBy != modifiedBy : !modifiedBy.equals(_cast.modifiedBy)){ return false; }
		if (modifiedOn == null ? _cast.modifiedOn != modifiedOn : !modifiedOn.equals(_cast.modifiedOn)){ return false; }
		if (comments == null ? _cast.comments != comments : !comments.equals(_cast.comments)){ return false; }
		if (type != _cast.type){ return false; }
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
		_hashCode = 29 * _hashCode + repId;
		if (qBonus != null){
			_hashCode = 29 * _hashCode + qBonus.hashCode();
		}
		if (cBonus != null){
			_hashCode = 29 * _hashCode + cBonus.hashCode();
		}
		_hashCode = 29 * _hashCode + (int) currencyType;
		if (modifiedBy != null){
			_hashCode = 29 * _hashCode + modifiedBy.hashCode();
		}
		if (modifiedOn != null){
			_hashCode = 29 * _hashCode + modifiedOn.hashCode();
		}
		if (comments != null){
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		_hashCode = 29 * _hashCode + (int) type;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemReportHistoryPk
	 */
	public BonusRecReportHistoryPk createPk() {
		return new BonusRecReportHistoryPk(id);
	}

	

	@Override
	public String toString() {
		return "BonusRecReportHistory [id=" + id + ", repId=" + repId + ", "+ (qBonus != null ? "qBonus=" + qBonus + ", " : "") +(cBonus != null ? "cBonus=" + cBonus + ", " : "") + "currencyType=" + currencyType + ", " + (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") + (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") + (comments != null ? "comments=" + comments + ", " : "") + "type=" + type + ", " + (qAmount != null ? "qAmount=" + qAmount + ", " : "")
				+ (qAmountInr != null ? "qAmountInr=" + qAmountInr + ", " : "")+(cAmount != null ? "cAmount=" + cAmount + ", " : "")+ (cAmountInr != null ? "cAmountInr=" + cAmountInr + ", " : "") + (total != null ? "total=" + total + ", " : "") + (currencyName != null ? "currencyName=" + currencyName : "") + "]";
	}

	public Map<String, String> toHashMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("qbonus", qBonus);
		map.put("qbonus", cBonus);
		//map.put("amountInr", amountInr);
		map.put("total", total == null ? "" : total);
		map.put("currencyName", currencyName);
		map.put("qAmount", qAmount);
		map.put("cAmount", cAmount);
		map.put("modifiedBy", modifiedBy == null ? "" : modifiedBy);
		map.put("modifiedOn", modifiedOn == null ? "" : PortalUtility.getdd_MM_yyyy_hh_mm_a(modifiedOn));
		map.put("comments", comments == null ? "" : comments);
		return map;
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

	
	public String getqAmount() {
		return qAmount;
	}

	
	public void setqAmount(String qAmount) {
		this.qAmount = qAmount;
	}

	
	public String getqAmountInr() {
		return qAmountInr;
	}

	
	public void setqAmountInr(String qAmountInr) {
		this.qAmountInr = qAmountInr;
	}

	
	public String getcAmount() {
		return cAmount;
	}

	
	public void setcAmount(String cAmount) {
		this.cAmount = cAmount;
	}

	
	public String getcAmountInr() {
		return cAmountInr;
	}

	
	public void setcAmountInr(String cAmountInr) {
		this.cAmountInr = cAmountInr;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
