package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class ClientDetailsBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2553377042153984934L;
	Set<Division> divisions;

	public Set<Division> getDivisions()
    {
    	return divisions;
    }

	public void setDivisions(Set<Division> divisions)
    {
    	this.divisions = divisions;
    }

}
