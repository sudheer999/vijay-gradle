package com.dikshatech.common.utils;

public enum ExportType
{

	pdf, xmlEmbed, xml, html, rtf, xls, jxl, csv, odt, ods, docx,doc, xlsx, pptx,zip,jsp,xhtml,png,jpg,gif,jpeg,txt;


	public static ExportType getValue(String value)
	{
		try
		{
			return valueOf(value);
		}
		catch (Exception e)
		{
			return pdf;
		}
	};
}
