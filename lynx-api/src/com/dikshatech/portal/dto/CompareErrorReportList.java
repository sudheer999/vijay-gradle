package com.dikshatech.portal.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dikshatech.portal.dto.CompareErrorReport;

@XmlRootElement(name = "CompareErrorReportList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompareErrorReportList {
	
	@XmlElement(name = "CompareErrorReport")
	private List<CompareErrorReport> list ;

	public List<CompareErrorReport> getList() {
		return list;
	}

	public void setList(List<CompareErrorReport> list) {
		this.list = list;
	}
	
	

}
