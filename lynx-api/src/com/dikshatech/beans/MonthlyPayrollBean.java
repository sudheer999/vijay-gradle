package com.dikshatech.beans;

import java.text.DecimalFormat;

import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.TdsDaoException;
import com.dikshatech.portal.forms.PortalForm;

public class MonthlyPayrollBean {

	private String	component;
	private String	amount;
	private String	id;

	public MonthlyPayrollBean() {}

	public MonthlyPayrollBean(String id, String component, String amount,PortalForm form,Float a) throws TdsDaoException, SalaryDetailsDaoException {
		setId(id);
		setComponent(component);
		setAmount(new DecimalFormat("0.00").format(Float.parseFloat(amount)));
	
		/*if(component.equalsIgnoreCase("TDS"))
		{
			Tds[]tds = tdsdao.findByStatus( " USERID = ? AND MONTH_ID = ? ", new Object[] { formBean.getUserId(), formBean.getTerm() });
			
			for(Tds t:tds)
			{
		     setAmount((t.getAmount()));
	}
		}*/
	
				
	}
	

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
