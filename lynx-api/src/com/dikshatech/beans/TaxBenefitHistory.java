package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dikshatech.portal.dto.TaxBenefitDeclaration;
public class TaxBenefitHistory implements Serializable{
	private static final long serialVersionUID=1L;
	
	private String updateOn;
	private Date updatedOnDate;
	
	private List<TaxBenefitDetail> taxBenefitDetails;

	public String getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

	public Date getUpdatedOnDate() {
		return updatedOnDate;
	}

	public void setUpdatedOnDate(Date updatedOnDate) {
		this.updatedOnDate = updatedOnDate;
	}

	public List<TaxBenefitDetail> getTaxBenefitDetails() {
		return taxBenefitDetails;
	}

	public void setTaxBenefitDetails(List<TaxBenefitDetail> taxBenefitDetails) {
		this.taxBenefitDetails = taxBenefitDetails;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.beans.TaxBenefitHistory: " );
		ret.append( "updatedON: " + updateOn );
		ret.append( ", updatedONDate: " + updatedOnDate );
		ret.append(", taxBenefitDetails: " + taxBenefitDetails.toString());
		return ret.toString();
	}
	
}
