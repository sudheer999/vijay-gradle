package com.dikshatech.enums;



public enum StateType
{
	Enabled,Disabled,UNKNOWN;
	
	public static StateType getValue(String value)
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
