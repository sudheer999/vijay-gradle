package com.dikshatech.portal.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
public class InvoiceReconciliationPk  implements Serializable {

	protected int id;

	/** 
	 * This attribute represents whether the primitive attribute id is null.
	 */
	protected boolean idNull;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIdNull() {
		return idNull;
	}

	public void setIdNull(boolean idNull) {
		this.idNull = idNull;
	}

	public InvoiceReconciliationPk()
	{
	}

	/**
	 * Method 'ProjLocationsPk'
	 * 
	 * @param id
	 */
	public InvoiceReconciliationPk(final int id)
	{
		this.id = id;
	}


	
	
	
	
}
