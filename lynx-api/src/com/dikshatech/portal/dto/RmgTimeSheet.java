package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.dikshatech.portal.forms.PortalForm;

public class RmgTimeSheet extends PortalForm implements Serializable,Comparable<DepPerdiemReport>{
	
	protected int id;
	
	protected int user_Id;
	
	protected int prj_Id;
	
	protected int chrg_Code_Id;
	
	protected Date startDate;

	protected Date endDate;
	
	protected int working_Days;
	
	protected int timeSheet_Id;
	
	protected String comments;
	
	protected int leave;
	
	protected String timeSheet_Cato;
	
	protected String status;
	
	protected String ActionBy;
	
	protected int projType;
	
	protected int empId;
	
	protected String name;
	
    protected String prj_Name;
	
	protected String chrg_Code_Name;
	
	protected String[] timeSheetDetails;
	
	protected String projectType;
	
	protected int roll_on_id;
	
	protected int rmg_timeSheet_Id;
	
	protected int rmgTimeSheetReportId;
	
	protected String tr_Flag;
	
	

	public String getTr_Flag() {
		return tr_Flag;
	}

	public void setTr_Flag(String tr_Flag) {
		this.tr_Flag = tr_Flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
	}

	public int getPrj_Id() {
		return prj_Id;
	}

	public void setPrj_Id(int prj_Id) {
		this.prj_Id = prj_Id;
	}

	public int getChrg_Code_Id() {
		return chrg_Code_Id;
	}

	public void setChrg_Code_Id(int chrg_Code_Id) {
		this.chrg_Code_Id = chrg_Code_Id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getWorking_Days() {
		return working_Days;
	}

	public void setWorking_Days(int working_Days) {
		this.working_Days = working_Days;
	}

	public int getTimeSheet_Id() {
		return timeSheet_Id;
	}

	public void setTimeSheet_Id(int timeSheet_Id) {
		this.timeSheet_Id = timeSheet_Id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getLeave() {
		return leave;
	}

	public void setLeave(int leave) {
		this.leave = leave;
	}

	public String getTimeSheet_Cato() {
		return timeSheet_Cato;
	}

	public void setTimeSheet_Cato(String timeSheet_Cato) {
		this.timeSheet_Cato = timeSheet_Cato;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActionBy() {
		return ActionBy;
	}

	public void setActionBy(String actionBy) {
		ActionBy = actionBy;
	}

	public int getProjType() {
		return projType;
	}

	public void setProjType(int projType) {
		this.projType = projType;
	}
	
	

	public String[] getTimeSheetDetails() {
		return timeSheetDetails;
	}

	public void setTimeSheetDetails(String[] timeSheetDetails) {
		this.timeSheetDetails = timeSheetDetails;
	}

	

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrj_Name() {
		return prj_Name;
	}

	public void setPrj_Name(String prj_Name) {
		this.prj_Name = prj_Name;
	}

	public String getChrg_Code_Name() {
		return chrg_Code_Name;
	}

	public void setChrg_Code_Name(String chrg_Code_Name) {
		this.chrg_Code_Name = chrg_Code_Name;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	
	
	public int getRmg_timeSheet_Id() {
		return rmg_timeSheet_Id;
	}

	public void setRmg_timeSheet_Id(int rmg_timeSheet_Id) {
		this.rmg_timeSheet_Id = rmg_timeSheet_Id;
	}
	
	

	public int getRmgTimeSheetReportId() {
		return rmgTimeSheetReportId;
	}

	public void setRmgTimeSheetReportId(int rmgTimeSheetReportId) {
		this.rmgTimeSheetReportId = rmgTimeSheetReportId;
	}
	
	

	public int getRoll_on_id() {
		return roll_on_id;
	}

	public void setRoll_on_id(int roll_on_id) {
		this.roll_on_id = roll_on_id;
	}

	

	
	public Map<String, Object> toMap(int i) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("empId", empId);
		map.put("name", name);
		map.put("project", prj_Name);
		map.put("chargeCode", chrg_Code_Name);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("rmgTimeSheetReportId", id);
		map.put("user_Id", user_Id);
		map.put("projectType", projectType);
		map.put("working_Days", working_Days);
		map.put("leave", leave);
        map.put("timeSheet_Cato", timeSheet_Cato);
		map.put("status", status);
		map.put("ActionBy", ActionBy);

		return map;
	}

	@Override
	public int compareTo(DepPerdiemReport arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

	public RmgTimeSheetPk createPk()
	{
		return new RmgTimeSheetPk(id);
	}
}
