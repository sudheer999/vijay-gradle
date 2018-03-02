package com.dikshatech.common.utils;

import java.util.Properties;

import com.dikshatech.portal.dto.Address;
import com.dikshatech.portal.dto.Commitments;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SalaryInfo;
import com.dikshatech.portal.forms.CandidateSaveForm;
//import com.dikshatech.portal.dto.Separation;

public class PopulateForms
{

	public ProfileInfo setProfileInfo(CandidateSaveForm cSaveForm)
	{
		ProfileInfo profileInfo = new ProfileInfo();
		profileInfo.setFirstName(cSaveForm.getFirstName());
		profileInfo.setMiddleName(cSaveForm.getMiddleName());
		profileInfo.setLastName(cSaveForm.getLastName());
		profileInfo.setMaidenName(cSaveForm.getMaidenName());
		profileInfo.setNationality(cSaveForm.getNationality());
		profileInfo.setGender(cSaveForm.getGender());
		profileInfo.setDob(cSaveForm.getDob());
		profileInfo.setOfficalEmailId(cSaveForm.getOfficalEmailId());
		profileInfo.setHrSpoc(cSaveForm.getHrSpoc());
		profileInfo.setReportingMgr(cSaveForm.getReportingMgr());
		profileInfo.setDateOfConfirmation(cSaveForm.getDateOfConfirmation());
		profileInfo.setDateOfJoining(cSaveForm.getDateOfJoining());
		profileInfo.setNoticePeriod(cSaveForm.getNoticePeriod());
		profileInfo.setEmpType(cSaveForm.getEmpType());
		profileInfo.setCreateDate(cSaveForm.getCreateDate());
		profileInfo.setLevelId(cSaveForm.getLevelId());
		profileInfo.setProjectId(cSaveForm.getProjectId());
		
		if(cSaveForm.getReportingTime()!=null && cSaveForm.getReportingTime().equals("")){
			profileInfo.setReportingTime("10:00 AM");
		}else{
		   profileInfo.setReportingTime(cSaveForm.getReportingTime());
		}
		if(cSaveForm.getReportingAddress()!=null &&cSaveForm.getReportingAddress().equals("")){
			Properties props= PropertyLoader.loadProperties("conf.Portal.properties");
			profileInfo.setReportingAddress((String)props.get("diksha.corporateaddress"));
		}else{
		 profileInfo.setReportingAddress(cSaveForm.getReportingAddress());
		}
		if(cSaveForm.getReportingAddressNormal()!=null && cSaveForm.getReportingAddressNormal().equals("")){
			Properties props= PropertyLoader.loadProperties("conf.Portal.properties");
			profileInfo.setReportingAddressNormal((String)props.get("diksha.corporateaddress"));
		}else{
		 profileInfo.setReportingAddressNormal(cSaveForm.getReportingAddressNormal());
		}

		return profileInfo;
	}

	public PersonalInfo setPersonalInfo(CandidateSaveForm cSaveForm)
	{
		PersonalInfo personalInfo = new PersonalInfo();
		personalInfo.setCurrentAddress(cSaveForm.getCurrentAddress());
		personalInfo.setPermanentAddress(cSaveForm.getPermenantAddress());
		personalInfo.setPermAddress(cSaveForm.getPermAddress());
		personalInfo.setPrimaryPhoneNo(cSaveForm.getPrimaryPhoneNo());
		personalInfo.setSecondaryPhoneNo(cSaveForm.getSecondaryPhoneNo());
		personalInfo.setPersonalEmailId(cSaveForm.getPersonalEmailId());
		personalInfo.setAlternateEmailId(cSaveForm.getAlternateEmailId());
		personalInfo.setMotherName(cSaveForm.getMotherName());
		personalInfo.setFatherName(cSaveForm.getFatherName());
		personalInfo.setMaritalStatus(cSaveForm.getMaritalStatus());
		personalInfo.setSpouseName(cSaveForm.getSpouseName());
		personalInfo.setEmerContactName(cSaveForm.getEmerContactName());
		personalInfo.setEmerCpRelationship(cSaveForm.getEmerCpRelationship());
		personalInfo.setEmerPhoneNo(String.valueOf(cSaveForm.getEmerPhoneNo()));
		personalInfo.setCity(cSaveForm.getCity());
		personalInfo.setZipCode(cSaveForm.getZipCode());
		personalInfo.setCountry(cSaveForm.getCountry());
		personalInfo.setState(cSaveForm.getState());

		return personalInfo;
	}

	public SalaryDetails setSalaryDetails(CandidateSaveForm cSaveForm)
	{
		SalaryDetails salaryDetails = new SalaryDetails();
		salaryDetails.setCandidateId(cSaveForm.getCandidateId());
		salaryDetails.setUserId(cSaveForm.getUserId());
		salaryDetails.setFields(cSaveForm.getFields());
		salaryDetails.setFieldLabel(cSaveForm.getFieldLabel());
		return salaryDetails;
	}

	public Address setPermAddress(CandidateSaveForm cSaveForm)
	{
		Address address  = new Address();
		address.setAddress(cSaveForm.getPermAddress());
		address.setCity(cSaveForm.getCity());
		address.setState(cSaveForm.getState());
		address.setCountry(cSaveForm.getCountry());
		address.setZipcode((cSaveForm.getZipCode()));//converting int to string
		
		return address;
	}
	
	public Address setcurrentAddress(CandidateSaveForm cSaveForm)
	{
		Address address  = new Address();
		if(cSaveForm.getCurAddress()==null && cSaveForm.getCurrcity()==null && cSaveForm.getCurrstate()==null && cSaveForm.getCurrcountry()==null && cSaveForm.getCurrzipCode()==0){
			address=null;
		}else{
		address.setAddress(cSaveForm.getCurAddress());
		address.setCity(cSaveForm.getCurrcity());
		address.setState(cSaveForm.getCurrstate());
		address.setCountry(cSaveForm.getCurrcountry());
		address.setZipcode((cSaveForm.getCurrzipCode()));//converting int to string
		}
		return address;
	}
	public Commitments setCommitments(CandidateSaveForm cSaveForm)
	{
		Commitments commit=new Commitments();
		commit.setComment(cSaveForm.getCommitments());
		commit.setCandidateId(cSaveForm.getCandidateId());
		return commit;
	}
	public SalaryInfo setSalaryInfo(CandidateSaveForm cSaveForm)
	{
		SalaryInfo salaryInfo=new SalaryInfo();
		salaryInfo.setRelocationBonus(cSaveForm.getRelocationBonus());
		salaryInfo.setJoiningBonusString(cSaveForm.getJoiningBonusString());
		salaryInfo.setRetentionBonus(cSaveForm.getRetentionBonus());
		salaryInfo.setPerdiemString(cSaveForm.getPerdiemString());
		salaryInfo.setJoiningBonusAmount(cSaveForm.getJoiningBonusAmount());
		salaryInfo.setPaymentTerms(cSaveForm.getPaymentTerms());
		salaryInfo.setRetentionInstallments(cSaveForm.getRetentionInstallments());
		salaryInfo.setPerdiemOffered(cSaveForm.getPerdiemOffered());
		salaryInfo.setRelocationCity(cSaveForm.getRelocationCity());
		salaryInfo.setPerdiemType(cSaveForm.getPerdiemType());
		return salaryInfo;
	}
//	public Separation setSeparation(Users user)
//	{
//		Separation separation =new Separation();
//		
//
//		return separation;
//	}
	
}
