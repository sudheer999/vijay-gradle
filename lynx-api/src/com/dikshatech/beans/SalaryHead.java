package com.dikshatech.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class SalaryHead implements Serializable{
	private static final long serialVersionUID=1L;
	private int order;
	private String name;
	private float annualSum;
	private float monthlySum;
	private List<SalaryConfigs> salaryConfigs=new ArrayList<SalaryConfigs>();
	

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getAnnualSum() {
		return annualSum;
	}
	public void setAnnualSum(float annualSum) {
		this.annualSum = annualSum;
	}
	public float getMonthlySum() {
		return monthlySum;
	}
	public void setMonthlySum(float monthlySum) {
		this.monthlySum = monthlySum;
	}
	public List<SalaryConfigs> getSalaryConfigs() {
		return salaryConfigs;
	}
	public void setSalaryConfigs(List<SalaryConfigs> salaryConfigs) {
		this.salaryConfigs = salaryConfigs;
	}
	
	public void setSum()
	{
		float tempAnnualSum,tempMonthlySum;
		
		tempAnnualSum=tempMonthlySum=0.0f;
		
		for(SalaryConfigs element:salaryConfigs)
		{
			tempAnnualSum+=element.getAnnualAmount();
			tempMonthlySum+=element.getMonthlyAmount();

		}
		tempAnnualSum=Math.round(tempAnnualSum);
		tempMonthlySum=Math.round(tempMonthlySum);
		
		setAnnualSum(tempAnnualSum);
		setMonthlySum(tempMonthlySum);
	}
}