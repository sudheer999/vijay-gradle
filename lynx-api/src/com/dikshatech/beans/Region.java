/*
 * 
 * @author: praveenkumar
 * 
 */
package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class Region implements Serializable {
	private static final long serialVersionUID = 1L;
	private int regionId;
	private String regionName;
	private Set<Region> subRegion;
	
	
	public Set<Region> getSubRegion()
	{
		return subRegion;
	}
	
	public void setSubRegion(Set<Region> subRegion)
	{
		this.subRegion = subRegion;
	}
	
	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}


