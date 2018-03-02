package com.dikshatech.beans;

import java.io.Serializable;

public class SalaryConfigs implements Serializable{
	private static final long serialVersionUID=1L;
	
	private int id;
	private String component;
	private int componentOrder;
	private float value;
	private String valueType;
	private int autoCalc;
	private float annualAmount;
	private float monthlyAmount;
	private float formula;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public int getComponentOrder() {
		return componentOrder;
	}
	public void setComponentOrder(int componentOrder) {
		this.componentOrder = componentOrder;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public int getAutoCalc() {
		return autoCalc;
	}
	public void setAutoCalc(int autoCalc) {
		this.autoCalc = autoCalc;
	}
	public float getAnnualAmount() {
		return annualAmount;
	}
	public void setAnnualAmount(float annualAmount) {
		this.annualAmount = annualAmount;
	}
	public float getMonthlyAmount() {
		return monthlyAmount;
	}
	public void setMonthlyAmount(float monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}
	public float getFormula() {
		return formula;
	}
	public void setFormula(float formula) {
		this.formula = formula;
	}
}
