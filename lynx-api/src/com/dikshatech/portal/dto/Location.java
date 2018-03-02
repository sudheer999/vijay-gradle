package com.dikshatech.portal.dto;

import java.io.Serializable;

import com.dikshatech.portal.forms.PortalForm;

public class Location extends PortalForm implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int region_id;
	
	protected int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRegion_id() {
		return region_id;
	}

	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	protected String location;
	
	
	private int	key2;

	public int getKey2() {
		return key2;
	}

	public void setKey2(int key2) {
		this.key2 = key2;
	}

	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof Location)) {
			return false;
		}
		
		final Location _cast = (Location) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (region_id != _cast.region_id) {
			return false;
		}
		
		if (location == null ? _cast.location != location : !location.equals( _cast.location )) {
			return false;
		}
	
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCode = 29 * _hashCode + region_id;
		if ( location!= null) {
			_hashCode = 29 * _hashCode + location.hashCode();
		}
		
		
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return RegionsPk
	 */
	public LocationPk createPk()
	{
		return new LocationPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.Locations: " );
		ret.append( "id=" + id );
		ret.append( ", region_id=" + region_id );
		ret.append( ", location=" + location );

		return ret.toString();
	}

	private Object[]			dropDown;
	public Object[] getDropDown() {
		return dropDown;
	}

	public void setDropDown(Object[] dropDown) {
		this.dropDown = dropDown;
	}

}


