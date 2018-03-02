package com.dikshatech.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.log4j.Logger;

import java.util.List;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.models.FBPModel;

import java.util.Collections;

public class FbpComponent implements Serializable{
	private static final long serialVersionUID=1l;
	
	private List<FbpComponent> fbpComponents;

	private Logger logger=LoggerUtil.getLogger();
	
	private String fbp,fbpLabel;
	private int order;
	private int period,occurence;//period is specified in months

	public FbpComponent() {
		final String[] abbr={"LTA","TPA","MA","CEA","MV","TRA","OA"};
		final String[] names=
			{"Leave Travel Allowance",
				"Telephone Allowance",
				"Medical Allowance",
				"Children Education Allowance",
				"Meal Voucher",
				"Transport Allowance",
				"Other Allowance"
				};//Labels
		final int[] order={1,2,3,4,5,6,7};
		final int[] period={48,1,1,1,1,1,1};//in months
		final int[] occurence={2,1,1,1,1,1,1};//how many times allowed
		
		
		List<FbpComponent> fbpList=new ArrayList<FbpComponent>();
		for(int idx=0;idx<abbr.length;idx++)
		{
			FbpComponent tempFbp=new FbpComponent(abbr[idx],names[idx],period[idx],occurence[idx],order[idx]);
			fbpList.add(tempFbp);
		}
		
		this.fbpComponents=fbpList;
	}


	private FbpComponent(String fbp, String fbpLabel, int period, int occurence,int order) {
		this.fbp = fbp;
		this.fbpLabel = fbpLabel;
		this.period = period;
		this.occurence = occurence;
		this.order=order;
	}

	public String getFbp() {
		return fbp;
	}

	public void setFbp(String fbp) {
		this.fbp = fbp;
	}

	public String getFbpLabel() {
		return fbpLabel;
	}

	public void setFbpLabel(String fbpLabel) {
		this.fbpLabel = fbpLabel;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	public List<FbpComponent> getFbpComponents() {
		return fbpComponents;
	}

	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


	@Override
	public String toString() {
		return "[FbpComponent:{fbp: " + getFbp() 
		+ ", fbpLabel: " + getFbpLabel()
		+ ", period: " + getPeriod() 
		+ ", order: " + getOrder()
		+ ", occurence: " + getOccurence()+"}]";
	}

	public FbpComponent getFbpComponentByName(String fbpAbbr){
		FbpComponent result=null;
		int pos=-1;
		int size=this.fbpComponents.size();
		for(int i=0;i<size;i++)
		{
			if(fbpComponents.get(i).getFbp().equalsIgnoreCase(fbpAbbr))
			{
				pos=i;
				break;
			}
		}
		
		if(pos!=-1)
			result=this.fbpComponents.get(pos);
		else
			result=null;
		
		return(result);
	}

	public FbpComponent getFbpComponentByLabel(String fbpLabel){
		FbpComponent result=null;
		int pos=-1;
		int size=this.fbpComponents.size();
		for(int i=0;i<size;i++)
		{
			if(fbpComponents.get(i).getFbpLabel().equalsIgnoreCase(fbpLabel))
			{
				pos=i;
				break;
			}
		}
		
		if(pos!=-1)
			result=this.fbpComponents.get(pos);
		else
			result=null;
		
		return(result);
	}
	
	public String getFbpComponentLabel(String fbpAbbr){
		String fbpLabel=fbpAbbr;
		FbpComponent fbpComponent=getFbpComponentByLabel(fbpAbbr);
		
		if(fbpComponent!=null)
			fbpLabel=fbpComponent.getFbpLabel();
		
		return(fbpLabel);
	}


	@Override
	public boolean equals(Object obj) {
		boolean result=false;
		
		if(obj instanceof FbpComponent)
		{
			FbpComponent arg=(FbpComponent)obj;
			if(this.fbp.equals(arg.getFbp())&&this.fbpLabel.equals(arg.getFbpLabel()))
				result=true;
		}
		
		return(result);
	}


	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == this.fbp ? 0 : this.fbp.hashCode());
		hash = 31 * hash + (null == this.fbpLabel ? 0 : this.fbpLabel.hashCode());
		return hash;
	}
	
	
}
