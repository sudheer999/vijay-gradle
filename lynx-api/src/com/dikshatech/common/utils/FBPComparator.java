package com.dikshatech.common.utils;

import java.util.Comparator;

import com.dikshatech.beans.FbpComponent;

public class FBPComparator implements Comparator{
	public int compare(Object fbp1, Object fbp2) 
	{
		FbpComponent fbpProvider=new FbpComponent();
		int fbpOrder1=fbpProvider.getFbpComponentByName((String)fbp1).getOrder();
		int fbpOrder2=fbpProvider.getFbpComponentByName((String)fbp2).getOrder();
		
		return(fbpOrder1-fbpOrder2);
	};
}
