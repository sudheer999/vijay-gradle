package com.dikshatech.enums;


public enum ContactType
{
	Business,Project,Finance,Procurement,UNKNOWN;
	
	public static ContactType getValue(String value)
	{
		try
		{
			return valueOf(value);
		}
		catch (Exception e)
		{
			return UNKNOWN;
		}
	};
	

}
