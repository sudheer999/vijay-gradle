package com.dikshatech.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dikshatech.portal.dto.TaxBenefitDeclaration;

public class TaxBenefitDetail implements Serializable{
	private static final long serialVersionUID=1L;
	String head;
	List<TaxBenefitDeclaration> benefit;

	public TaxBenefitDetail(){
		head="???";
		benefit=new ArrayList<TaxBenefitDeclaration>();
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public List<TaxBenefitDeclaration> getBenefit() {
		return benefit;
	}

	public void setBenefit(List<TaxBenefitDeclaration> benefit) {
		this.benefit = benefit;
	}

	public void setBenefit(TaxBenefitDeclaration[] benefit) {
		for(TaxBenefitDeclaration element:benefit)
			this.benefit.add(element);
	}

	@Override
	public String toString() {
		return "TaxBenefitDetail: [head: " + head
		+", benfit: " + benefit.toString()
		+"]";
	}
}

