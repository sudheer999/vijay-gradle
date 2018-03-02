package com.dikshatech.portal.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dikshatech.beans.BankDetalilsBean;
import com.dikshatech.beans.ClientDetailsBean;
import com.dikshatech.beans.Division;
import com.dikshatech.beans.EducationBean;
import com.dikshatech.beans.EmpserReqMapBean;
import com.dikshatech.beans.ExperienceBean;
import com.dikshatech.beans.Features;
import com.dikshatech.beans.HandlerAction;
import com.dikshatech.beans.IssueBean;
import com.dikshatech.beans.LeaveDetails;
import com.dikshatech.beans.LoanDetailsBean;
import com.dikshatech.beans.NewsBean;
import com.dikshatech.beans.PassportBean;
import com.dikshatech.beans.PersonalBean;
import com.dikshatech.beans.Region;
import com.dikshatech.beans.ReimbursementBean;
import com.dikshatech.beans.RequestIssueBean;
import com.dikshatech.beans.ResumeManagementBean;
import com.dikshatech.beans.SkillSetBean;
import com.dikshatech.beans.TimesheetBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.Status;
import com.dikshatech.jasper.JGenerator;
import com.dikshatech.portal.dao.CategoryDao;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.CompanyDao;
import com.dikshatech.portal.dao.ContactTypeDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.EscalationDao;
import com.dikshatech.portal.dao.LeaveBalanceDao;
import com.dikshatech.portal.dao.LeaveTypeDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.LoanRequestDao;
import com.dikshatech.portal.dao.LocationDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.ProjectMappingDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RolesDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.AccessibilityLevel;
import com.dikshatech.portal.dto.Address;
import com.dikshatech.portal.dto.BankDetails;
import com.dikshatech.portal.dto.Calendar;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.Category;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.ClientDivMap;
import com.dikshatech.portal.dto.Commitments;
import com.dikshatech.portal.dto.Company;
import com.dikshatech.portal.dto.ContactType;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DivisonPk;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.EducationInfo;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.Escalation;
import com.dikshatech.portal.dto.ExperienceInfo;
import com.dikshatech.portal.dto.Holidays;
import com.dikshatech.portal.dto.Invoicedto;
import com.dikshatech.portal.dto.Issues;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.LeaveType;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.LoanDetails;
import com.dikshatech.portal.dto.LoanRequest;
import com.dikshatech.portal.dto.Location;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.dto.News;
import com.dikshatech.portal.dto.PassportInfo;
import com.dikshatech.portal.dto.Payslip;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.PoDetail;
import com.dikshatech.portal.dto.PoEmpMap;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProjContInfo;
import com.dikshatech.portal.dto.ProjLocations;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectMapping;
import com.dikshatech.portal.dto.ProjectMappingPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.ReimbursementFinancialData;
import com.dikshatech.portal.dto.ReimbursementReq;
import com.dikshatech.portal.dto.RequestedIssues;
import com.dikshatech.portal.dto.ResumeManagement;
import com.dikshatech.portal.dto.RmgTimeSheet;
import com.dikshatech.portal.dto.Roles;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SkillSet;
import com.dikshatech.portal.dto.TimeSheetDetails;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.UserTaskTimesheetMap;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.ChargeCodeDaoException;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.EscalationDaoException;
import com.dikshatech.portal.exceptions.LeaveBalanceDaoException;
import com.dikshatech.portal.exceptions.LeaveTypeDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.LoanRequestDaoException;
import com.dikshatech.portal.exceptions.LocationDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.CategoryDaoFactory;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.CompanyDaoFactory;
import com.dikshatech.portal.factory.ContactTypeDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.EscalationDaoFactory;
import com.dikshatech.portal.factory.LeaveBalanceDaoFactory;
import com.dikshatech.portal.factory.LeaveTypeDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.LoanRequestDaoFactory;
import com.dikshatech.portal.factory.LocationDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.ProjectMappingDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RolesDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.CandidateSaveForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.PortalMail;



public class DtoToBeanConverter {
	/**
	 * @author supriya.bhike
	 * @param divDto
	 * @param subDivDto
	 * @return Division bean where it sets division and it's sub division
	 */
	private static Logger	logger				= LoggerUtil.getLogger(DtoToBeanConverter.class);
	protected static com.dikshatech.beans.Division DtoToBeanConverter(
			Divison divDto, Divison[] subDivDto) {
		com.dikshatech.beans.Division divBean = new com.dikshatech.beans.Division();

		divBean.setDivisionId(divDto.getId());
		divBean.setDivisionName(divDto.getName());

		Set<com.dikshatech.beans.Division> subDiv = new HashSet<com.dikshatech.beans.Division>();

		for (Divison divison : subDivDto) {
			Division division = new Division();
			division.setDivisionId(divison.getId());
			division.setDivisionName(divison.getName());
			subDiv.add(division);
		}

		divBean.setSubDivision(subDiv);
		return divBean;
	}

	/**
	 * @author supriya.bhike For giving Skills and sub-skill set mapping
	 */
	protected static com.dikshatech.beans.SkillSetBean DtoToBeanConverter(
			SkillSet skillset, SkillSet[] subSkillset) {
		com.dikshatech.beans.SkillSetBean skillSetBean = new SkillSetBean();

		skillSetBean.setId(skillset.getId());
		skillSetBean.setSkillname(skillset.getSkillname());

		Set<com.dikshatech.beans.SkillSetBean> subSkillsets = new HashSet<com.dikshatech.beans.SkillSetBean>();

		for (SkillSet skill : subSkillset) {
			SkillSetBean singlesubskill = new SkillSetBean();
			singlesubskill.setId(skill.getId());
			singlesubskill.setSkillname(skill.getSkillname());
			singlesubskill.setParentId(skill.getParentId());
			subSkillsets.add(singlesubskill);
		}

		skillSetBean.setSubSkillSet(subSkillsets);
		return skillSetBean;
	}

	/**
	 * @author supriya.bhike
	 * @param divDtoPK
	 *            ,divDto @ Saves new division
	 * @return
	 */
	protected static com.dikshatech.beans.Division DtoToBeanConverter(
			DivisonPk divDtoPK, Divison divDto) {
		com.dikshatech.beans.Division divBean = new com.dikshatech.beans.Division();

		divBean.setDivisionId(divDtoPK.getId());
		divBean.setDivisionName(divDto.getName());

		return divBean;
	}

	/**
	 * @author supriya.bhike
	 * @param divDto
	 *            @ Sets name of division to receive single division
	 * @return
	 */
	protected static com.dikshatech.beans.Division DtoToBeanConverter(
			Divison divDto) {
		com.dikshatech.beans.Division divBean = new com.dikshatech.beans.Division();

		divBean.setDivisionId(divDto.getId());
		divBean.setDivisionName(divDto.getName());

		return divBean;
	}

	protected static com.dikshatech.beans.Region DtoToBeanConverter(
			Regions regDto, Regions[] subRegDto) {
		com.dikshatech.beans.Region regBean = new com.dikshatech.beans.Region();

		regBean.setRegionId(regDto.getId());
		regBean.setRegionName(regDto.getRegName());

		Set<com.dikshatech.beans.Region> subReg = new HashSet<com.dikshatech.beans.Region>();

		for (Regions regions : subRegDto) {
			Region region = new Region();
			region.setRegionId(regions.getId());
			region.setRegionName(regions.getRegName());
			subReg.add(region);
		}

		regBean.setSubRegion(subReg);
		return regBean;
	}

	protected static com.dikshatech.beans.LeaveBean DtoToBeanConverter(
			LeaveBalance leaveBalance) {
		com.dikshatech.beans.LeaveBean leaveBean = new com.dikshatech.beans.LeaveBean();

		leaveBean.setLeaveAccumalated(leaveBalance.getLeaveAccumalated());
		leaveBean.setLeavesTaken((leaveBalance.getLeavesTaken()));

		return leaveBean;
	}

	protected static HashMap<String, String> getStringTokensMap(
			String delimitedText) {
		String tokens[] = delimitedText.split(";");
		HashMap<String, String> stringMap = new HashMap<String, String>();

		// logger.trace("Populating WfNodeForm from String:" +
		// wfNodeMap.get("nodeId"));
		for (String token : tokens) {

			String[] keyValue = token.split("=");
			if (keyValue.length >= 2) {
				keyValue[1] = ("null".equalsIgnoreCase(keyValue[1])) ? null
						: keyValue[1];
				stringMap.put(keyValue[0], keyValue[1]);
			}
		}
		return stringMap;
	}

	protected static com.dikshatech.beans.LeaveBean DtoToBeanConverter(LeaveMaster leaveDto) throws DocumentsDaoException {
		com.dikshatech.beans.LeaveBean leaveBean = new com.dikshatech.beans.LeaveBean();
		LeaveTypeDao leaveTypeDao = LeaveTypeDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		DocumentsDao documentsDao = DocumentsDaoFactory.create();
		Users[] users;
		ProfileInfo[] profileInfos;
		LeaveType[] leaveTypeDto;
		Divison divison;
		DivisonDao divisonDao = DivisonDaoFactory.create();
		Levels[] levels;
		LevelsDao levelsDao = LevelsDaoFactory.create();
		ProjectDao projectDao = ProjectDaoFactory.create();
		Project[] project = null;
		ChargeCodeDao chareCodeDao = ChargeCodeDaoFactory.create();
		ChargeCode[] chargeCode = null;
		try{
			leaveTypeDto = leaveTypeDao.findWhereIdEquals(leaveDto.getLeaveType());
			leaveBean.setLeaveTypeName(leaveTypeDto[0].getLeaveType());
		} catch (LeaveTypeDaoException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		leaveBean.setId(leaveDto.getId() + "");
		leaveBean.setEsr_map_id(leaveDto.getEsrMapId() + "");
		leaveBean.setLeaveType(leaveDto.getLeaveType() + "");
		leaveBean.setAppliedDate(leaveDto.getAppliedDateTime());
		leaveBean.setFromDate(leaveDto.getFromDate());
		leaveBean.setToDate(leaveDto.getToDate());
		leaveBean.setStatus(leaveDto.getStatus());
		leaveBean.setStatusId(leaveDto.getStatusId() + "");
		leaveBean.setComment(leaveDto.getComment());
		leaveBean.setReason(leaveDto.getReason());
		leaveBean.setContactNo(leaveDto.getContactNo());
		leaveBean.setDuration(leaveDto.getDuration() + "");
		leaveBean.setProjectName(leaveDto.getProjectName());
		leaveBean.setChargeCode(leaveDto.getProjectTitle());
		leaveBean.setToCancell((leaveDto.getStatus() + "").equals(Status.REJECTED) ? "1" : leaveDto.getToCancell() + "");
		try{
			project = projectDao.findWhereNameEquals(leaveBean.getProjectName());
			if (project.length > 0) leaveBean.setProjectID(project[0].getId() + "");
			chargeCode = chareCodeDao.findWhereChCodeNameEquals(leaveBean.getChargeCode());
			if (chargeCode.length > 0) leaveBean.setChargeCodeid(chargeCode[0].getId() + "");
		} catch (ProjectDaoException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ChargeCodeDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		leaveBean.setChargeCode(leaveDto.getProjectTitle());
		if (leaveDto.getAttachment() != null){
			String id[] = leaveDto.getAttachment().split("~=~");
			leaveDto.setAttachment(id[0]);
		}
		if (leaveDto.getAttachment() == null || leaveDto.getAttachment() == "" || Integer.parseInt(leaveDto.getAttachment()) == 0){
			leaveBean.setAttachment(null);
			leaveBean.setFileName(null);
		} else{
			int fileid = Integer.parseInt(leaveDto.getAttachment());
			Documents[] documents = documentsDao.findWhereIdEquals(fileid);
			if (documents != null && documents.length > 0){
				leaveBean.setFileName(documents[0].getFilename());
				leaveBean.setAttachment(leaveDto.getAttachment());
				leaveBean.setDescriptions(documents[0].getDescriptions());
			}
		}
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		EmpSerReqMap[] empSerReqMap;
		LeaveBalanceDao leaveBalanceDao = LeaveBalanceDaoFactory.create();
		LeaveBalance leaveBalances;
		try{
			empSerReqMap = empSerReqMapDao.findWhereIdEquals(leaveDto.getEsrMapId());
			int req_id = empSerReqMap[0].getRequestorId();
			users = usersDao.findWhereIdEquals(req_id);
			leaveBean.setUserId(String.valueOf(users[0].getId()));
			int profile_id = users[0].getProfileId();
			profileInfos = profileInfoDao.findWhereIdEquals(profile_id);
			String applier_name = profileInfos[0].getFirstName();
			int levelID = profileInfos[0].getLevelId();
			int rmID = profileInfos[0].getReportingMgr();
			Users RM = usersDao.findByPrimaryKey(rmID);
			ProfileInfo[] rm_profileInfos = profileInfoDao.findWhereIdEquals(RM.getProfileId());
			leaveBean.setReportingMgr(rm_profileInfos[0].getFirstName() + rm_profileInfos[0].getLastName());
			levels = levelsDao.findWhereIdEquals(levelID);
			divison = divisonDao.findByPrimaryKey(levels[0].getDivisionId());
			if (divison.getParentId() != 0){
				leaveBean.setDivisionid(divison.getId() + "");// division id
				leaveBean.setDivision(divison.getName());// division name
			}
			if (divison.getParentId() == 0){
				leaveBean.setDepartmentid(divison.getId() + "");
				leaveBean.setDepartment(divison.getName()); // department
				// is parent
			} else{
				Divison department = divisonDao.findByPrimaryKey(divison.getParentId());
				leaveBean.setDepartment(department.getName()); // sub
				// division
				// exist as
				// department
				leaveBean.setDepartmentid(department.getId() + "");
			}
			leaveBean.setAppliedBy(applier_name);
			leaveBean.setReqId(empSerReqMap[0].getReqId());
			leaveBalances = leaveBalanceDao.findWhereUserIdEquals(empSerReqMap[0].getRequestorId());
			if (leaveBalances != null){
				leaveBean.setLeaveAccumalated(leaveBalances.getLeaveAccumalated());
				leaveBean.setLeavesTaken(leaveBalances.getLeavesTaken());
				leaveBean.setLeaveBalance(leaveBalances.getBalance());
			}
		} catch (EmpSerReqMapDaoException e2){
			e2.printStackTrace();
		} catch (LeaveBalanceDaoException e){
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			e.printStackTrace();
		} catch (UsersDaoException e){
			e.printStackTrace();
		} catch (LevelsDaoException e){
			e.printStackTrace();
		} catch (DivisonDaoException e){
			e.printStackTrace();
		}
		return leaveBean;
	}

	protected static com.dikshatech.beans.LeaveDetails DtoToBeanConverter(Login login) {
		LeaveDetails leaveDetails = new LeaveDetails();
		LeaveBalanceDao leaveBalanceDao = LeaveBalanceDaoFactory.create();
		LeaveBalance leaveBalances;
		try{
			leaveBalances = leaveBalanceDao.findWhereUserIdEquals(login.getUserId());
			if (leaveBalances != null){
				leaveDetails.setLeaveAccumalated(leaveBalances.getLeaveAccumalated() + "");
				leaveDetails.setLeavesTaken(leaveBalances.getLeavesTaken() + "");
				leaveDetails.setLeaveBalance(leaveBalances.getBalance() + "");
				leaveDetails.setCompOff(leaveBalances.getCompOff() + "");
			}
		} catch (LeaveBalanceDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return leaveDetails;
	}

	protected static Map<Integer, String> getUserGroupsBasedOnSequence(
			String approvalChain)

	{

		if (approvalChain != null && approvalChain.contains("|")) {
			String tempStr = approvalChain;
			String[] str = tempStr.split("\\|");

			HashMap<Integer, String> groupedUsersMap = new HashMap<Integer, String>();

			String[] groupArr = str[1].split("\\;");
			Integer seqkey = 1;
			if (groupArr.length > 0) {

				for (int i = 0; i < groupArr.length; i++) {

					groupedUsersMap.put(seqkey, groupArr[i]);
					seqkey++;
				}

			}
			return groupedUsersMap;
		}

		return null;

	}

	/**
	 * @author supriya.bhike
	 * @param canDto
	 * @return returns Candidate Bean setting all the Relevant Id's of other
	 *         referencing table and fields
	 */
	protected static com.dikshatech.beans.CandidateBean DtoToBeanConverter(
			Candidate canDto) {
		com.dikshatech.beans.CandidateBean canBean = new com.dikshatech.beans.CandidateBean();
		canBean.setId(canDto.getId());

		if (canDto.getPassportId() > 0) {
			canBean.setPassportId(canDto.getPassportId());
			PassportInfo passportinfo = new PassportInfo();
			passportinfo.setId(canDto.getPassportId());
			PassportBean passportbean = DtoToBeanConverter(passportinfo);
		}

		if (canDto.getEducationId() > 0) {
			canBean.setEducationId(canDto.getEducationId());
		}

		if (canDto.getPersonalId() > 0) {
			canBean.setPersonalId(canDto.getPersonalId());
		}

		if (canDto.getExperienceId() > 0) {
			canBean.setExperienceId(canDto.getExperienceId());
		}

		if (canDto.getFinancialId() > 0) {
			canBean.setFinancialId(canDto.getFinancialId());
		}

		if (canDto.getProfileId() > 0) {
			canBean.setProfileId(canDto.getProfileId());
		}

		canBean.setIsEmployee(canDto.getIsEmployee());
		canBean.setIsActive(canDto.getIsActive());
		canBean.setCreateDate(canDto.getCreateDate());
		canBean.setStatus(canDto.getStatus());
		return canBean;
	}

	/**
	 * @author supriya.bhike
	 * @param canDto
	 * @return returns Candidate Bean setting all the Relevant Id's of other
	 *         referencing table and fields
	 */
	protected static com.dikshatech.beans.CandidateCommonBean DtoToBeanConverter(
			Candidate canDto, ProfileInfo profileinfo,
			PersonalInfo personalInfo, String status) {
		com.dikshatech.beans.CandidateCommonBean candidatecommonbean = new com.dikshatech.beans.CandidateCommonBean();
		candidatecommonbean.setId(canDto.getId());
		candidatecommonbean.setIsActive(canDto.getIsActive());
		int levelid = 0;

		try {
			candidatecommonbean.setStatus(canDto.getStatus());
			candidatecommonbean.setDateOfOffer(PortalUtility.getdd_MM_yyyy(canDto.getDateOfOffer()));

			if (!personalInfo.isPrimaryPhoneNoNull()
					&& personalInfo.getPrimaryPhoneNo() != null) {
				candidatecommonbean.setPrimaryPhoneNo(personalInfo
						.getPrimaryPhoneNo());
			}
			// Status status= new Status();
			StatusDao statusDao = StatusDaoFactory.create();
			if (!canDto.isStatusNull() && canDto.getStatus() > 0) {
				candidatecommonbean.setStatus(canDto.getStatus());
				// status =statusDao.findByPrimaryKey(canDto.getStatus());
				// candidatecommonbean.setStatus(status.getId());//status id
				candidatecommonbean.setStatusname(status);// status name
			}
			candidatecommonbean.setStatusname(status);
			candidatecommonbean.setFirstName(profileinfo.getFirstName());
			candidatecommonbean.setLastName(profileinfo.getLastName());
			candidatecommonbean
					.setDateOfJoining(PortalUtility.getdd_MM_yyyy(profileinfo.getDateOfJoining()));

			if (profileinfo.getLevelId() > 0) {
				LevelsDao levelsDao = LevelsDaoFactory.create();
				Divison divison = new Divison();
				Regions region = new Regions();

				DivisonDao divisonDao = DivisonDaoFactory.create();
				RegionsDao regionsDao = RegionsDaoFactory.create();

				levelid = profileinfo.getLevelId();
				Levels level = levelsDao.findByPrimaryKey(levelid);
				candidatecommonbean.setLevelId(levelid);
				candidatecommonbean.setDesignation(level.getDesignation());

				divison = divisonDao.findByPrimaryKey(level.getDivisionId());
				if (divison.getParentId() != 0) {
					candidatecommonbean.setDivision(divison.getName());
				}

				region = regionsDao.findByPrimaryKey(divison.getRegionId());
				candidatecommonbean.setRegion(region.getRegName());

				candidatecommonbean.setDateOfOffer(PortalUtility.getdd_MM_yyyy(canDto.getDateOfOffer()));
				if (divison.getParentId() == 0) {
					candidatecommonbean.setDepartment(divison.getName());

				} else {
					Divison department = divisonDao.findByPrimaryKey(divison
							.getParentId());
					candidatecommonbean.setDepartment(department.getName());
				}

			}// if ends
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return candidatecommonbean;
	}

	public static CandidateSaveForm DtoToBeanConverter(
			CandidateSaveForm candidateSaveForm2, PersonalInfo personalinfo,
			ProfileInfo profileinfo, Address current, Address permenant) {
		try {

			candidateSaveForm2.setFirstName(profileinfo.getFirstName());
			candidateSaveForm2.setLastName(profileinfo.getLastName());
			candidateSaveForm2.setMaidenName(profileinfo.getMaidenName());
			candidateSaveForm2.setMiddleName(profileinfo.getMiddleName());
			candidateSaveForm2.setNationality(profileinfo.getNationality());
			candidateSaveForm2.setGender(profileinfo.getGender());
			candidateSaveForm2.setDob(profileinfo.getDob());
			candidateSaveForm2.setOfficalEmailId(profileinfo
					.getOfficalEmailId());
			candidateSaveForm2.setHrSpoc(profileinfo.getHrSpoc());
			candidateSaveForm2.setReportingMgr(profileinfo.getReportingMgr());
			candidateSaveForm2.setDateOfConfirmation(profileinfo
					.getDateOfConfirmation());
			candidateSaveForm2.setDateOfJoining(profileinfo.getDateOfJoining());
			candidateSaveForm2.setNoticePeriod(profileinfo.getNoticePeriod());
			candidateSaveForm2.setEmpType(profileinfo.getEmpType());
			candidateSaveForm2.setCreateDate(profileinfo.getCreateDate());
			candidateSaveForm2.setDateOfSeperation(profileinfo
					.getDateOfSeperation());
			candidateSaveForm2.setLevelId(profileinfo.getLevelId());
			candidateSaveForm2.setReportingTime(profileinfo.getReportingTime());
			candidateSaveForm2.setReportingAddress(profileinfo.getReportingAddress());
			candidateSaveForm2.setReportingAddressNormal(profileinfo.getReportingAddressNormal());
			
			candidateSaveForm2.setCurrentAddress(personalinfo
					.getCurrentAddress());
			candidateSaveForm2.setPermenantAddress(personalinfo
					.getPermanentAddress());
			candidateSaveForm2.setPrimaryPhoneNo(personalinfo
					.getPrimaryPhoneNo());
			candidateSaveForm2.setSecondaryPhoneNo(personalinfo
					.getSecondaryPhoneNo());
			candidateSaveForm2.setPersonalEmailId(personalinfo
					.getPersonalEmailId());
			candidateSaveForm2.setAlternateEmailId(personalinfo
					.getAlternateEmailId());
			candidateSaveForm2.setMotherName(personalinfo.getMotherName());
			candidateSaveForm2.setFatherName(personalinfo.getFatherName());
			candidateSaveForm2
					.setMaritalStatus(personalinfo.getMaritalStatus());
			candidateSaveForm2.setSpouseName(personalinfo.getSpouseName());
			candidateSaveForm2.setEmerContactName(personalinfo
					.getEmerContactName());
			candidateSaveForm2.setEmerCpRelationship(personalinfo
					.getEmerCpRelationship());
			candidateSaveForm2.setEmerPhoneNo(Long.parseLong(personalinfo.getEmerPhoneNo()));
			candidateSaveForm2.setCity(personalinfo.getCity());
			candidateSaveForm2.setZipCode(personalinfo.getZipCode());
			candidateSaveForm2.setCountry(personalinfo.getCountry());
			candidateSaveForm2.setState(personalinfo.getState());

			if (current != null) {
				candidateSaveForm2.setCurAddress(current.getAddress());
				candidateSaveForm2.setCurrcity(current.getCity());
				candidateSaveForm2.setCurrcountry(current.getCountry());
				candidateSaveForm2.setCurrstate(current.getState());
				candidateSaveForm2.setCurrzipCode(current.getZipcode());
			}

			candidateSaveForm2.setPermAddress(permenant.getAddress());
			candidateSaveForm2.setCity(permenant.getCity());
			candidateSaveForm2.setCountry(permenant.getCountry());
			candidateSaveForm2.setState(permenant.getState());
			candidateSaveForm2.setZipCode(permenant.getZipcode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return candidateSaveForm2;

	}

	/**
	 * @author supriya.bhike
	 * @param passportInfo
	 * @return sets all passport information to bean
	 */
	protected static com.dikshatech.beans.PassportBean DtoToBeanConverter(
			PassportInfo passportInfo) {
		com.dikshatech.beans.PassportBean passportbean = new com.dikshatech.beans.PassportBean();
		passportbean.setId(passportInfo.getId());
		passportbean.setDateOfExpire(passportInfo.getDateOfExpire());
		passportbean.setDateOfIssue(passportInfo.getDateOfIssue());
		passportbean.setGivenname(passportInfo.getGivenname());
		passportbean.setPassportNo(passportInfo.getPassportNo());
		passportbean.setPlaceOfBirth(passportInfo.getPlaceOfBirth());
		passportbean.setPlaceOfIssue(passportInfo.getPlaceOfIssue());
		passportbean.setSurname(passportInfo.getSurname());
		return passportbean;
	}

	/**
	 * @author supriya.bhike
	 * @param SalaryDetailBean
	 * @return sets all Salary information to column wise
	 */
	protected static com.dikshatech.beans.SalaryDetailBean DtoToBeanConverter(
			SalaryDetails salaryDetails) {
		com.dikshatech.beans.SalaryDetailBean salaryDetailBean = new com.dikshatech.beans.SalaryDetailBean();
		salaryDetailBean.setId(salaryDetails.getId());
		if (salaryDetails.getCandidateId() > 0) {
			salaryDetailBean.setCandidateId(salaryDetails.getCandidateId());
		}
		if (salaryDetails.getCandidateId() > 0) {
			salaryDetailBean.setUserId(salaryDetails.getUserId());
		}
		if (salaryDetails.getFieldLabel() != null) {
			salaryDetailBean.setFieldLabel(salaryDetails.getFieldLabel());
		}
		if (salaryDetails.getAnnual()!=null) {
			salaryDetailBean.setAnnual(salaryDetails.getAnnual());
		}
		if (salaryDetails.getMonthly()!=null) {
			salaryDetailBean.setMonthly(salaryDetails.getMonthly());
		}
		if (salaryDetails.getFieldtype() !=null ) {
			salaryDetailBean.setFieldType(salaryDetails.getFieldtype());
		}
		if (salaryDetails.getId() > 0) {
			salaryDetailBean.setId(salaryDetails.getId());
		}

		return salaryDetailBean;
	}

	/**
	 * @author supriya.bhike
	 * @param User and Profileinfo
	 *            and profile,personal data setter for employee home page
	 * @return Profile Bean setting for employee home page
	 */

	protected static com.dikshatech.beans.ProfileBean DtoToBeanConverter(
			Users user, ProfileInfo profileinfo)

	{
		com.dikshatech.beans.ProfileBean profilebean = new com.dikshatech.beans.ProfileBean();

		PersonalInfo personalInfo = new PersonalInfo();
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		Users user1 = new Users();
		UsersDao usersDao = UsersDaoFactory.create();
		int levelid = 0;
		try {
			profilebean.setId(profileinfo.getId());
			profilebean.setEmpid(user.getEmpId());
			profilebean.setUserId(user.getId());
			profilebean.setFirstName(profileinfo.getFirstName());
			profilebean.setLastName(profileinfo.getLastName());
			profilebean.setOfficalEmailId(profileinfo.getOfficalEmailId());
			profilebean.setMaidenName(profileinfo.getMaidenName());
			profilebean.setMiddleName(profileinfo.getMiddleName());
			profilebean.setNationality(profileinfo.getNationality());
			profilebean.setGender(profileinfo.getGender());
			profilebean.setDob(profileinfo.getDob());
			profilebean.setHrSpoc(profileinfo.getHrSpoc());
			profilebean.setReportingMgr(profileinfo.getReportingMgr());
			profilebean.setProjectId(profileinfo.getProjectId());
			profilebean.setDateOfSeperation(PortalUtility.getdd_MM_yyyy(profileinfo.getDateOfSeperation()));
			profilebean.setUserCreatedBy(user.getUserCreatedBy());
			profilebean.setHideFinanceDetails(profileinfo.isHideFinanceDetails());
			profilebean.setHideCategoryDetails(profileinfo.isHideCategoryDetails());
			profilebean.setLocation(profileinfo.getLocation());
			profilebean.setCategory(profileinfo.getCategory());
			
	
			
			ContactTypeDao contactTypeDao = ContactTypeDaoFactory.create();
			ContactType contactType3[]=contactTypeDao.findWhereUserIdEquals(user.getId());
			if(contactType3!=null && contactType3.length>0){
				 profilebean.setReceiveContactType(contactType3);
			}
			
			if(user.getUserCreatedBy()>0){
			Users createdBy=usersDao.findByPrimaryKey(user.getUserCreatedBy());
			ProfileInfo userCreatedByProfile= profileInfoDao.findByPrimaryKey(createdBy.getProfileId());
			profilebean.setUserCreatedByName(userCreatedByProfile.getFirstName()+ " "+ userCreatedByProfile.getLastName());
			}

			ProjectMappingPk projectMappingPk = new ProjectMappingPk();
			ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory
					.create();

			//does not give last rolled-on project information june 20 vijay j
			//ProjectMapping projectMapping[] = projectMappingDao.findWhereUserIdEquals(user.getId());
			
			ProjectMapping projectMapping[] = projectMappingDao.findByDynamicSelect("SELECT * FROM PROJECT_MAPPING PM WHERE PM.USER_ID=? ORDER BY PM.ID DESC ", new Object[]{new Integer(user.getId())});
			
			if (projectMapping != null && projectMapping.length > 0) {
				ProjectDao projectDao = ProjectDaoFactory.create();

				Project project = projectDao.findByPrimaryKey(projectMapping[0]
						.getProjectId());
				profilebean.setProjectId(projectMapping[0].getProjectId());// ids
				profilebean.setProjectName(project.getName()); // name
			}
			/**
			 * reporting manager name
			 */
			if (profileinfo.getReportingMgr() > 0) {

				usersDao = UsersDaoFactory.create();
				Users reportingMngr = usersDao.findByPrimaryKey(profileinfo
						.getReportingMgr());
				// logger.info.println("USER ID ***********" +user.getId() );
				profilebean.setReportingMngrName(getName(reportingMngr));
			}
			/**
			 * SPOC name
			 */
			if (profileinfo.getHrSpoc() > 0) {

				usersDao = UsersDaoFactory.create();
				Users hrspoc = usersDao.findByPrimaryKey(profileinfo
						.getHrSpoc());
				// logger.info.println("USER ID HRRR ***********" +user.getId()
				// );
				profilebean.setHrSpocName(getName(hrspoc));
			}
			profilebean.setMonths(profileinfo.getMonths());
			profilebean.setDoc(profileinfo.getDoc());
			profilebean.setExtension(profileinfo.getExtension());
			profilebean.setDateOfConfirmation((profileinfo.getDateOfConfirmation()));
			profilebean.setDateOfJoining((profileinfo.getDateOfJoining()));
			profilebean.setNoticePeriod(profileinfo.getNoticePeriod());
			profilebean.setEmployee_type(profileinfo.getEmployeeType());
			profilebean.setEmpType(profileinfo.getEmpType());
			profilebean.setCreateDate(profileinfo.getCreateDate());
			profilebean.setLevelid(profileinfo.getLevelId());

			/**
			 * Role for user
			 */
			UserRolesDao rolesDao = UserRolesDaoFactory.create();
			UserRoles roles = rolesDao.findWhereUserIdEquals(user.getId());
			RolesDao role = RolesDaoFactory.create();

			if (roles != null) {

				profilebean.setRoleid(roles.getRoleId());
				Roles roledetail = role.findByPrimaryKey(roles.getRoleId());
				profilebean.setRoleName(roledetail.getName());
			}

			personalInfo = personalInfoDao.findByPrimaryKey(user
					.getPersonalId());
			if (personalInfo != null
					&& personalInfo.getPrimaryPhoneNo() != null) {
				profilebean.setPhonenumber(personalInfo.getPrimaryPhoneNo());
				profilebean.setBloodGroup(personalInfo.getBloodGroup());
			}
			if (profileinfo.getLevelId() > 0 || user.getLevelId() > 0) {
				LevelsDao levelsDao = LevelsDaoFactory.create();
				Divison divison = new Divison();
				Regions region = new Regions();
				Company company = new Company();

				DivisonDao divisonDao = DivisonDaoFactory.create();
				RegionsDao regionsDao = RegionsDaoFactory.create();

				if (profileinfo.getLevelId() > 0) {
					levelid = profileinfo.getLevelId(); // level id
				} else {
					levelid = user.getLevelId();
				}

				Levels level = levelsDao.findByPrimaryKey(levelid);
				profilebean.setDesignation(level.getDesignation()); // level
				// designation
				profilebean.setLevel(level.getLabel()); // level label

				divison = divisonDao.findByPrimaryKey(level.getDivisionId());
				if (divison.getParentId() != 0) {
					profilebean.setDivisionid(divison.getId());// division id
					profilebean.setDivision(divison.getName());// division name
				}

				region = regionsDao.findByPrimaryKey(divison.getRegionId());
				profilebean.setRegion(region.getRegName()); // region id
				profilebean.setRegionid(region.getId());// region name

				CompanyDao companyDao = CompanyDaoFactory.create();
				company = companyDao.findByPrimaryKey(region.getCompanyId());
				profilebean.setCompany(company.getCompanyName()); // company
				// name
				profilebean.setCompanyid(company.getId());// company id

				if (divison.getParentId() == 0) {
					profilebean.setDepartmentid(divison.getId());
					profilebean.setDepartment(divison.getName()); // department
					// is parent
				} else {
					Divison department = divisonDao.findByPrimaryKey(divison
							.getParentId());
					profilebean.setDepartment(department.getName()); // sub
					// division
					// exist as
					// department
					profilebean.setDepartmentid(department.getId());
				}
				LocationDao locationDao = LocationDaoFactory.create();
				try {
					
					Location[] loc = locationDao.findByLocation("SELECT ID FROM LOCATIONS WHERE LOCATION  = '"+profileinfo.getLocation().toString()+"';", new Object[] {});
					for(Location location:loc)
					{
					
						profilebean.setLocationid(location.getId());
					
					}
				} catch (LocationDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CategoryDao categoryDao = CategoryDaoFactory.create();
			 Category[]cat= categoryDao.findByCategory("SELECT ID FROM CATEGORY WHERE NAME  = '"+profileinfo.getCategory().toString()+"';", new Object[] {});
			for (Category category : cat) {
				profilebean.setCategoryid(String.valueOf((category.getId())));
				
			}
				
				

			}// if ends

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return profilebean;
	}
	
	
	protected static com.dikshatech.beans.ProfileBean DtoToBeanConverter1(
			Users user, ProfileInfo profileinfo)

	{
		com.dikshatech.beans.ProfileBean profilebean = new com.dikshatech.beans.ProfileBean();

		PersonalInfo personalInfo = new PersonalInfo();
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		int levelid = 0;
		try {
			profilebean.setId(user.getId());
			profilebean.setEmpid(user.getEmpId());
			profilebean.setUserId(user.getId());
			profilebean.setFirstName(profileinfo.getFirstName());
			profilebean.setLastName(profileinfo.getLastName());
			profilebean.setOfficalEmailId(profileinfo.getOfficalEmailId());
			profilebean.setMaidenName(profileinfo.getMaidenName());
			profilebean.setMiddleName(profileinfo.getMiddleName());
			profilebean.setNationality(profileinfo.getNationality());
			profilebean.setGender(profileinfo.getGender());
			profilebean.setDob(profileinfo.getDob());
			profilebean.setHrSpoc(profileinfo.getHrSpoc());
			profilebean.setReportingMgr(profileinfo.getReportingMgr());
			profilebean.setProjectId(profileinfo.getProjectId());
			profilebean.setDateOfSeperation(PortalUtility.getdd_MM_yyyy(profileinfo.getDateOfSeperation()));
			profilebean.setActionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(user.getCreateDate()));

			ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory.create();
			
			//this is not fetching latest information... say, after roll-on to some project   june 20 vijay
			//ProjectMapping projectMapping[] = projectMappingDao.findWhereUserIdEquals(user.getId());
			
			ProjectMapping projectMapping[] = projectMappingDao.findByDynamicSelect("SELECT * FROM PROJECT_MAPPING PM WHERE PM.USER_ID=? ORDER BY PM.ID DESC", new Object[]{new Integer(user.getId())});
			if (projectMapping != null && projectMapping.length > 0) {
				ProjectDao projectDao = ProjectDaoFactory.create();

				Project project = projectDao.findByPrimaryKey(projectMapping[0]
						.getProjectId());
				profilebean.setProjectId(projectMapping[0].getProjectId());// ids
				profilebean.setProjectName(project.getName()); // name
			}
			/**
			 * reporting manager name
			 */
			if (profileinfo.getReportingMgr() > 0) {

				usersDao = UsersDaoFactory.create();
				Users reportingMngr = usersDao.findByPrimaryKey(profileinfo
						.getReportingMgr());
				// logger.info.println("USER ID ***********" +user.getId() );
				profilebean.setReportingMngrName(getName(reportingMngr));
			}
			/**
			 * SPOC name
			 */
			if (profileinfo.getHrSpoc() > 0) {

				usersDao = UsersDaoFactory.create();
				Users hrspoc = usersDao.findByPrimaryKey(profileinfo
						.getHrSpoc());
				// logger.info.println("USER ID HRRR ***********" +user.getId()
				// );
				profilebean.setHrSpocName(getName(hrspoc));
			}
			profilebean.setDateOfConfirmation((profileinfo
					.getDateOfConfirmation()));
			profilebean.setDateOfJoining((profileinfo.getDateOfJoining()));
			profilebean.setNoticePeriod(profileinfo.getNoticePeriod());
			profilebean.setEmpType(profileinfo.getEmpType());
			profilebean.setCreateDate(profileinfo.getCreateDate());
			profilebean.setLevelid(profileinfo.getLevelId());

			/**
			 * Role for user
			 */
			UserRolesDao rolesDao = UserRolesDaoFactory.create();
			UserRoles roles = rolesDao.findWhereUserIdEquals(user.getId());
			RolesDao role = RolesDaoFactory.create();

			if (roles != null) {

				profilebean.setRoleid(roles.getRoleId());
				Roles roledetail = role.findByPrimaryKey(roles.getRoleId());
				profilebean.setRoleName(roledetail.getName());
			}

			personalInfo = personalInfoDao.findByPrimaryKey(user
					.getPersonalId());
			if (personalInfo != null
					&& personalInfo.getPrimaryPhoneNo() != null) {
				profilebean.setPhonenumber(personalInfo.getPrimaryPhoneNo());
			}
			if (profileinfo.getLevelId() > 0 || user.getLevelId() > 0) {
				LevelsDao levelsDao = LevelsDaoFactory.create();
				Divison divison = new Divison();
				Regions region = new Regions();
				Company company = new Company();

				DivisonDao divisonDao = DivisonDaoFactory.create();
				RegionsDao regionsDao = RegionsDaoFactory.create();

				if (profileinfo.getLevelId() > 0) {
					levelid = profileinfo.getLevelId(); // level id
				} else {
					levelid = user.getLevelId();
				}

				Levels level = levelsDao.findByPrimaryKey(levelid);
				profilebean.setDesignation(level.getDesignation()); // level
				// designation
				profilebean.setLevel(level.getLabel()); // level label

				divison = divisonDao.findByPrimaryKey(level.getDivisionId());
				if (divison.getParentId() != 0) {
					profilebean.setDivisionid(divison.getId());// division id
					profilebean.setDivision(divison.getName());// division name
				}

				region = regionsDao.findByPrimaryKey(divison.getRegionId());
				profilebean.setRegion(region.getRegName()); // region id
				profilebean.setRegionid(region.getId());// region name

				CompanyDao companyDao = CompanyDaoFactory.create();
				company = companyDao.findByPrimaryKey(region.getCompanyId());
				profilebean.setCompany(company.getCompanyName()); // company
				// name
				profilebean.setCompanyid(company.getId());// company id

				if (divison.getParentId() == 0) {
					profilebean.setDepartmentid(divison.getId());
					profilebean.setDepartment(divison.getName()); // department
					// is parent
				} else {
					Divison department = divisonDao.findByPrimaryKey(divison
							.getParentId());
					profilebean.setDepartment(department.getName()); // sub
					// division
					// exist as
					// department
					profilebean.setDepartmentid(department.getId());
				}

			}// if ends

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return profilebean;
	}

	private static String getName(Users reportingMngr) {
		String fname = "";
		String lname = "";
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProfileInfo repMngr;
		try {
			if(reportingMngr!=null){
			repMngr = profileInfoDao.findByPrimaryKey(reportingMngr
					.getProfileId());
			if (repMngr.getFirstName() != null) {
				fname = repMngr.getFirstName();
			}
			if (repMngr.getLastName() != null) {
				// profilebean.setReportingMngrName(fname + " " +
				// repMngr.getLastName());
				fname = fname + " " + repMngr.getLastName();
			} else {
				// profilebean.setReportingMngrName(fname);
				fname = fname;
			}
			}
		} catch (ProfileInfoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fname;
	}

	/**
	 * @author supriya.bhike
	 * @param profileinfo
	 * @return Profile Bean setting the first name and id for drop down of
	 *         candidate
	 */
	protected static com.dikshatech.beans.PersonalBean DtoToBeanConverter(
			PersonalInfo personalInfo) {
		com.dikshatech.beans.PersonalBean personalBean = new com.dikshatech.beans.PersonalBean();
		personalBean.setId(personalInfo.getId());
		personalBean.setEmerContactName(personalInfo.getEmerContactName());
		personalBean
				.setEmerCpRelationship(personalInfo.getEmerCpRelationship());
		personalBean.setEmerPhoneNo(Long.parseLong(personalInfo.getEmerPhoneNo()));
		personalBean.setFatherName(personalInfo.getFatherName());
		personalBean.setMotherName(personalInfo.getMotherName());
		personalBean.setMaritalStatus(personalInfo.getMaritalStatus());
		personalBean.setPermanentAddress(personalInfo.getPermanentAddress());
		personalBean.setPersonalEmailId(personalInfo.getPersonalEmailId());
		personalBean.setSpouseName(personalInfo.getSpouseName());
		personalBean.setPrimaryPhoneNo(personalInfo.getPrimaryPhoneNo());
		personalBean.setSecondaryPhoneNo(personalInfo.getSecondaryPhoneNo());
		personalBean.setCurrentAddress(personalInfo.getCurrentAddress());
		if (personalInfo.getPermAddress() != null) {
			// personalBean.setPermAddress(personalInfo.getPermAddress());
			// personalBean.setCity(personalInfo.getCity());
			// personalBean.setCountry(personalInfo.getCountry());
			// personalBean.setState(personalInfo.getState());
			// personalBean.setZipCode(personalInfo.getZipCode());
			setAddressFields(personalInfo.getPermAddress(), personalBean);
		}
		if (personalInfo.getCurrAddress() != null) {
			setAddressFields(personalInfo.getCurrAddress(), personalBean);
		}
		return personalBean;
	}

	public static void setAddressFields(String add, PersonalBean personalBean) {
		String address[] = add.split("~");
		if (!address[0].equals(""))
			personalBean.setPermAddress(address[0]);
		if (!address[1].equals(""))
			personalBean.setCity(address[1]);
		if (!address[2].equals(""))
			personalBean.setZipCode(Integer.parseInt(address[2]));
		if (!address[4].equals(""))
			personalBean.setState(address[4]);
		if (!address[3].equals(""))
			personalBean.setCountry(address[3]);
	}

	/**
	 * @author supriya.bhike
	 * @param profileinfo
	 * @return Profile Bean setting the first name and id for drop down of
	 *         candidate
	 */

	protected static com.dikshatech.beans.ProfileBean DtoToBeanConverter(
			ProfileInfo profileinfo, Users users) {
		com.dikshatech.beans.ProfileBean profilebean = new com.dikshatech.beans.ProfileBean();

		if (users != null) {
			profilebean.setUserId(users.getId());
			profilebean.setEmpid(users.getEmpId());
		}
		profilebean.setFirstName(profileinfo.getFirstName());
		profilebean.setLastName(profileinfo.getLastName());
		profilebean.setDob(profileinfo.getDob());
		profilebean.setId(profileinfo.getId());
		profilebean.setOfficalEmailId(profileinfo.getOfficalEmailId());
		return profilebean;
	}

	/**
	 * @author supriya.bhike
	 * @param rolesDto
	 * @return set role id and name
	 */
	protected static com.dikshatech.beans.Roles DtoToBeanConverter(
			Roles rolesDto) {
		com.dikshatech.beans.Roles rolesBean = new com.dikshatech.beans.Roles();

		rolesBean.setRoleId(rolesDto.getId());
		rolesBean.setRoleName(rolesDto.getName());

		return rolesBean;
	}

	/**
	 * @author sasmita.sabar
	 * @param newsDto
	 * @return
	 */
	protected static com.dikshatech.beans.CalendarBean DtoToBeanConverter(
			Calendar calendar) {
		com.dikshatech.beans.CalendarBean calBean = new com.dikshatech.beans.CalendarBean();

		calBean.setCalendarId(calendar.getId());
		calBean.setCalendarName(calendar.getName());
		calBean.setRegion(calendar.getRegion());
		calBean.setCalendarYear(calendar.getYear());
		return calBean;

	}

	/**
	 * @author sasmita.sabar
	 * @param newsDto
	 * @return
	 */
	protected static com.dikshatech.beans.HolidaysBean DtoToBeanConverter(
			Holidays holidays) {
		com.dikshatech.beans.HolidaysBean holidaysBean = new com.dikshatech.beans.HolidaysBean();

		holidaysBean.setHoliDaysId(holidays.getId());
		holidaysBean.setHoliDayscalId(holidays.getCalId());
		holidaysBean.setHolidayName(holidays.getHolidayName());
		holidaysBean.setDatepicker(holidays.getDatePicker());
		return holidaysBean;

	}

	/**
	 * sets education info for user for degree,for 12th,10th etc etc
	 * 
	 * @param modulesDto
	 * @return
	 */

	public static EducationBean DtoToBeanConverter(EducationInfo educationInfo) {

		EducationBean educationBean = new EducationBean();
		educationBean.setId(educationInfo.getId());
		educationBean.setCandidateId(educationInfo.getCandidateId());
		educationBean.setUserId(educationInfo.getUserId());
		educationBean.setDegreecourse(educationInfo.getDegreeCourse());
		educationBean.setType(educationInfo.getType());
		educationBean.setYearPassing(educationInfo.getYearPassing());
		educationBean.setGradepercentage(educationInfo.getGradePercentage());
		educationBean.setGradutionDate(educationInfo.getGradutionDate());
		educationBean.setStartDate(educationInfo.getStartDate());
		educationBean.setStudIdNoenrollNo(educationInfo.getStudIdNoEnrollNo());
		educationBean
				.setCollegeuniversity(educationInfo.getCollegeUniversity());
		educationBean.setSubjectMajor(educationInfo.getSubjectMajor());
		educationBean.setSequence(educationInfo.getSequence());
		return educationBean;
	}

	/**
	 * sets experience info for user for degree,for 12th,10th etc etc
	 * 
	 * @param modulesDto
	 * @return
	 */

	public static ExperienceBean DtoToBeanConverter(ExperienceInfo expInfo) {

		ExperienceBean expBean = new ExperienceBean();
		expBean.setCompany(expInfo.getCompany());
		expBean.setDateJoining(expInfo.getDateJoining());
		expBean.setDateRelieving(expInfo.getDateRelieving());
		expBean.setJoiningDesignation(expInfo.getJoiningDesignation());
		expBean.setLeavingDesignation(expInfo.getLeavingDesignation());
		expBean.setRptMgrName(expInfo.getRptMgrName());
		expBean.setHrName(expInfo.getHrName());
		expBean.setEmpCode(expInfo.getEmpCode());
		expBean.setEmployment_type(expInfo.getEmploymentType());
		expBean.setId(expInfo.getId());
		expBean.setCandidateId(expInfo.getCandidateId());
		expBean.setUserId(expInfo.getUserId());
		expBean.setLeavingReason(expInfo.getLeavingReason());

		return expBean;
	}

	/**
	 * Set all email related data in PortalMail DTO for
	 * 
	 * @param modulesDto
	 * @return
	 */
	public static com.dikshatech.portal.mail.PortalMail DtoToBeanConverter(
			PortalMail pMail, Candidate candidate, ProfileInfo profileInfo2,
			PersonalInfo personalInfo, Login login1, boolean isAttachements)
			throws Exception {

		String candidatename = null, designation = null, offeredDepartment = null;
		/**
		 * get designation
		 */
		LevelsDao levelsDao = LevelsDaoFactory.create();
		int levelid = profileInfo2.getLevelId();
		Levels level;
		level = levelsDao.findByPrimaryKey(levelid);// level id
		designation = level.getDesignation();// designation
		candidatename = profileInfo2.getFirstName() + " "
				+ profileInfo2.getLastName();
		Divison divison = new Divison();

		DivisonDao divisonDao = DivisonDaoFactory.create();

		divison = divisonDao.findByPrimaryKey(level.getDivisionId());

		if (divison.getParentId() == 0) {
			offeredDepartment = divison.getName();

		} else {
			Divison department = divisonDao.findByPrimaryKey(divison
					.getParentId());
			offeredDepartment = department.getName();
		}

		pMail.setCandidateName(candidatename);

		if (login1 != null) {
			pMail.setAutoGenUserName(login1.getUserName());
			pMail.setAutoGenPassword(login1.getPassword());
		}
		if (personalInfo != null) {
			pMail.setRecipientMailId(personalInfo.getPersonalEmailId());
		} else {
			pMail.setRecipientMailId(profileInfo2.getOfficalEmailId());
		}
		Attachements[] attachements = new Attachements[2];

		if (isAttachements) {
			Attachements attachement = new Attachements();
			String filePath = new JGenerator().getOutputFile(
					JGenerator.CANDIDATE, candidate.getOfferLetter());
			attachement.setFilePath(filePath);
			attachement.setFileName("OfferLetter.pdf");
			attachements[0] = attachement;
			Attachements attachement1 = new Attachements();
			String temp = JGenerator.getOutputFile(JGenerator.CANDIDATE, "Diksha_Employment_Offer_Letter_-_Annexure_2A__2B_-_Terms_and_Conditions_-_Confidentiality_and_NDA.pdf");
			attachement1.setFileName("Diksha_Employment_Offer_Letter_-_Annexure_2A__2B_-_Terms_and_Conditions_-_Confidentiality_and_NDA.pdf");
			attachement1.setFilePath(temp);
			attachements[1] = attachement1;
	
	}

		pMail.setFileSources(attachements);
		pMail.setOffreredDivision(divison.getName());
		pMail.setOfferedDepartment(offeredDepartment);
		pMail.setCandidateDOJ(PortalUtility.formatDate(profileInfo2
				.getDateOfJoining()));
		pMail.setOfferedDesignation(designation);
		pMail.setFieldName(personalInfo.getPrimaryPhoneNo());
		return pMail;
	}

	protected static com.dikshatech.beans.ModuleBean DtoToBeanConverter(
			Modules modulesDto)

	{
		com.dikshatech.beans.ModuleBean modulesBean = new com.dikshatech.beans.ModuleBean();

		modulesBean.setModuleId(modulesDto.getId());
		modulesBean.setModuleName(modulesDto.getName());

		return modulesBean;
	}

	protected static com.dikshatech.beans.Features DtoToBeanConverter(
			Modules modulesDto, AccessibilityLevel asseccLevel) {
		Features featuresBean = new Features();

		featuresBean.setFeatureId(modulesDto.getId());
		featuresBean.setFeatureName(modulesDto.getName());
		featuresBean.setParentId(modulesDto.getParentId());

		if (asseccLevel.getSave() == 1) {
			featuresBean.setSave(true);
		} else {
			featuresBean.setSave(false);
		}

		if (asseccLevel.getView() == 1) {
			featuresBean.setView(true);
		} else {
			featuresBean.setView(false);
		}

		if (asseccLevel.getModify() == 1) {
			featuresBean.setEdit(true);
		} else {
			featuresBean.setEdit(false);
		}

		if (asseccLevel.getApprove() == 1) {
			featuresBean.setApprove(true);
		} else {
			featuresBean.setApprove(false);
		}

		if (asseccLevel.getReject() == 1) {
			featuresBean.setReject(true);
		} else {
			featuresBean.setReject(false);
		}

		if (asseccLevel.getRemove() == 1) {
			featuresBean.setRemove(true);
		} else {
			featuresBean.setRemove(false);
		}

		if (asseccLevel.getEnable() == 1) {
			featuresBean.setEnable(true);
		} else {
			featuresBean.setEnable(false);
		}

		if (asseccLevel.getDisable() == 1) {
			featuresBean.setDisable(true);
		} else {
			featuresBean.setDisable(false);
		}

		if (asseccLevel.getResendOffer() == 1) {
			featuresBean.setResendOffer(true);
		} else {
			featuresBean.setResendOffer(false);
		}

		if (asseccLevel.getMarkAsEmp() == 1) {
			featuresBean.setMarkAsEmployee(true);
		} else {
			featuresBean.setMarkAsEmployee(false);
		}

		if (asseccLevel.getGenOffer() == 1) {
			featuresBean.setGenerateOffer(true);
		} else {
			featuresBean.setGenerateOffer(false);
		}

		return featuresBean;
	}

	protected static Object[] populateFormFromString(String delimitedText) {
		Object[] objArray = new Object[2];
		String hourSpent = "";
		UserTaskTimesheetMap userTaskTimeSheetMapDto = null;
		HashMap<String, String> timeSheetmapMap = getTokensMap(delimitedText);
		if (!timeSheetmapMap.isEmpty()){
			try{
				userTaskTimeSheetMapDto = new UserTaskTimesheetMap();
				userTaskTimeSheetMapDto.setProjectId((timeSheetmapMap.get("projectid") != null) ? Integer.parseInt(timeSheetmapMap.get("projectid")) : 0);
				userTaskTimeSheetMapDto.setEtc((timeSheetmapMap.get("etc") != null) ? Float.parseFloat(timeSheetmapMap.get("etc")) : 0);
				userTaskTimeSheetMapDto.setTotalEtc((timeSheetmapMap.get("totaletc") != null) ? Float.parseFloat(timeSheetmapMap.get("totaletc")) : 0);
				String taskName = timeSheetmapMap.get("taskname");
				taskName = taskName != null ? taskName.length() > 50 ? taskName.substring(0, 49) : taskName : "";
				userTaskTimeSheetMapDto.setTaskName(taskName);
				hourSpent = (timeSheetmapMap.get("hourspent") != null) ? timeSheetmapMap.get("hourspent") : "";
				String[] hourSpentArr = hourSpent.split("~!~");
				if(hourSpentArr.length==7){
					userTaskTimeSheetMapDto.setMon(hourSpentArr[0]);
					userTaskTimeSheetMapDto.setTue(hourSpentArr[1]);
					userTaskTimeSheetMapDto.setWed(hourSpentArr[2]);
					userTaskTimeSheetMapDto.setThu(hourSpentArr[3]);
					userTaskTimeSheetMapDto.setFri(hourSpentArr[4]);
					userTaskTimeSheetMapDto.setSat(hourSpentArr[5]);
					userTaskTimeSheetMapDto.setSun(hourSpentArr[6]);
				}else logger.error("unable to get expected time string for timesheet task detalis");
			} catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		objArray[0] = userTaskTimeSheetMapDto;
		objArray[1] = hourSpent;
		return objArray;
	}

	protected static HashMap<String, String> getTokensMap(String delimitedText) {
		String tokens[] = delimitedText.split("~;~");
		HashMap<String, String> timeSheetMap = new HashMap<String, String>();

		// logger.trace("Populating WfNodeForm from String:" +
		// wfNodeMap.get("nodeId"));
		for (String token : tokens) {

			String[] keyValue = token.split("~=~");
			if (keyValue.length >= 2) {
				keyValue[1] = ("null".equalsIgnoreCase(keyValue[1])) ? null
						: keyValue[1];
				timeSheetMap.put(keyValue[0], keyValue[1]);
			}
		}

		return timeSheetMap;
	}

	protected static String[] getParseComaSeparatedString(String delimitedText) {
		String[] splittedArr = null;
		if (delimitedText == null || delimitedText.length() == 0) return new String[0];
		splittedArr = delimitedText.split(",");
		return splittedArr;
	}

	protected static com.dikshatech.beans.Features DtoToBeanConverter(
			com.dikshatech.beans.Features featuresBean,
			AccessibilityLevel asseccLevel) {
		String tempPermission = "";

		if (asseccLevel.getSave() == 1) {
			featuresBean.setSave(true);
			tempPermission += "SAVE" + ";";
		} else {
			featuresBean.setSave(false);
		}

		if (asseccLevel.getView() == 1) {
			featuresBean.setView(true);
			tempPermission += "VIEW" + ";";
		} else {
			featuresBean.setView(false);
		}

		if (asseccLevel.getApprove() == 1) {
			featuresBean.setApprove(true);
			tempPermission += "APPROVE" + ";";

		} else {
			featuresBean.setApprove(false);

		}

		if (asseccLevel.getReject() == 1) {
			featuresBean.setReject(true);
			tempPermission += "REJECT" + ";";
		} else {
			featuresBean.setReject(false);
		}

		if (asseccLevel.getRemove() == 1) {
			featuresBean.setRemove(true);
			tempPermission += "REMOVE" + ";";
		} else {
			featuresBean.setRemove(false);
		}

		if (asseccLevel.getModify() == 1) {
			featuresBean.setEdit(true);
			tempPermission += "MODIFY" + ";";
		} else {
			featuresBean.setEdit(false);
		}

		if (asseccLevel.getEnable() == 1) {
			featuresBean.setEnable(true);
			tempPermission += "ENABLE" + ";";
		} else {
			featuresBean.setEnable(false);
		}
		if (asseccLevel.getDisable() == 1) {
			featuresBean.setDisable(true);
			tempPermission += "DISABLE" + ";";
		} else {
			featuresBean.setDisable(false);
		}
		if (asseccLevel.getResendOffer() == 1) {
			featuresBean.setResendOffer(true);
			// tempPermission += "RESEND-OFFER" + ";";
			tempPermission += "RESENDOFFER" + ";";

		} else {
			featuresBean.setResendOffer(false);
		}

		if (asseccLevel.getGenOffer() == 1) {
			featuresBean.setGenerateOffer(true);
			// tempPermission += "GENERATE-OFFER" + ";";
			tempPermission += "GENERATEOFFER" + ";";
		} else {
			featuresBean.setGenerateOffer(false);
		}

		if (asseccLevel.getMarkAsEmp() == 1) {
			featuresBean.setMarkAsEmployee(true);
			tempPermission += "MARKEMPLOYEE" + ";";
		} else {
			featuresBean.setMarkAsEmployee(false);
		}

		if (asseccLevel.getNoshow() == 1) {
			featuresBean.setNoShow(true);
			tempPermission += "NOSHOW" + ";";
		} else {
			featuresBean.setNoShow(false);
		}

		if (asseccLevel.getCancel() == 1) {
			featuresBean.setCancel(true);
			tempPermission += "CANCEL" + ";";
		} else {
			featuresBean.setCancel(false);
		}

		if (asseccLevel.getRollon() == 1) {
			featuresBean.setRollOn(true);
			tempPermission += "ROLLON" + ";";
		} else {
			featuresBean.setRollOn(false);
		}

		if (asseccLevel.getSubmit() == 1) {
			featuresBean.setSubmit(true);
			tempPermission += "SUBMIT" + ";";
		} else {
			featuresBean.setSubmit(false);
		}

		if (asseccLevel.getDownload() == 1) {
			featuresBean.setDownload(true);
			tempPermission += "DOWNLOAD" + ";";
		} else {
			featuresBean.setDownload(false);
		}

		if (asseccLevel.getUpload() == 1) {
			featuresBean.setUpload(true);
			tempPermission += "UPLOAD" + ";";
		} else {
			featuresBean.setUpload(false);
		}

		if (asseccLevel.getEmail() == 1) {
			featuresBean.setEmail(true);
			tempPermission += "EMAIL" + ";";
		} else {
			featuresBean.setEmail(false);
		}
		if (asseccLevel.getAssign() == 1) {
			featuresBean.setAssign(true);
			tempPermission += "ASSIGN" + ";";
		} else {
			featuresBean.setAssign(false);
		}
		if (asseccLevel.getHold() == 1) {
			featuresBean.setHold(true);
			tempPermission += "HOLD" + ";";
		} else {
			featuresBean.setHold(false);
		}
		featuresBean.setAccessibilitySelected(tempPermission);
		return featuresBean;
	}

	protected static com.dikshatech.beans.RollOnOffBean DtoToBeanConverter(
			ProfileInfo profile, Users user, RollOn rollOnOff) {
		LevelsDao levelDao = LevelsDaoFactory.create();
		Levels level = null;
		com.dikshatech.beans.RollOnOffBean rollOnOffbean = new com.dikshatech.beans.RollOnOffBean();
		rollOnOffbean.setRollonId(rollOnOff.getId());
		rollOnOffbean.setFirstName(profile.getFirstName());
		rollOnOffbean.setLastName(profile.getLastName());
		try {
			level = levelDao.findByPrimaryKey(user.getLevelId());
		} catch (LevelsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		rollOnOffbean.setDesignation(level.getDesignation());
		rollOnOffbean.setDivisionId(level.getDivisionId());
		return rollOnOffbean;
	}

	protected static com.dikshatech.beans.ProcessChainBean DtoToBeanConverterToParseApprovalChainForReceive(
			String chainAttributes, String chainType , int PC_ID) {
		com.dikshatech.beans.ProcessChainBean processChainBean = new com.dikshatech.beans.ProcessChainBean();
		List<Levels> levelSet = new ArrayList<Levels>();
		LevelsDao lDao = LevelsDaoFactory.create();
		EscalationDao escDao = EscalationDaoFactory.create();
		String[] str = chainAttributes.split("\\|");
		if (str.length > 1) {
			chainAttributes = str[1];
		} else {
			chainAttributes = str[0];
		}
		String[] temp = chainAttributes.split(";");

		for (int index = 0; index < temp.length; index++) {
			String tempStr = temp[index];
			if (tempStr != null && tempStr.length() > 0) {
				for (String lStr : tempStr.split(",")) {
					Levels levels = null;
					try {
						levels = lDao.findByPrimaryKey(Integer.parseInt(lStr
								.trim()));
					} catch (NumberFormatException e) {

						if (lStr.equals(ProcessEvaluator.HRSPOC)) {
							levels = new Levels();
							levels.setComponent(ProcessEvaluator.HRSPOC);
						} else if (lStr.equals(ProcessEvaluator.RPMGR)) {
							levels = new Levels();
							levels.setComponent(ProcessEvaluator.RPMGR);
						}
					} catch (LevelsDaoException e) {
						e.printStackTrace();
					}
					
					/* The code for the getting the Duedays and Escalation ID from Escalation TBL starts here */
					
					Escalation[] escExisting = null;

					String strToBePassed = lStr;
					int levelId=0;
					
					if(!strToBePassed.equals(ProcessEvaluator.RPMGR)&& !strToBePassed.equals(ProcessEvaluator.HRSPOC))	
						levelId=Integer.parseInt(strToBePassed);
					
					try {
						escExisting =  escDao.findByDynamicWhere(" PC_ID = ? AND ESCAL_TYPE = ? AND (USERS_ID=? OR LEVEL_ID=?)", new Object[]{PC_ID,chainType,strToBePassed,levelId});
					} catch (EscalationDaoException e) {
						e.printStackTrace();
					}
					if(escExisting != null && escExisting.length > 0){
						levels.setEscalationId(escExisting[0].getId());
						levels.setDueDays(escExisting[0].getDueDays());
					}
										
					/* The code for the getting the Duedays and Escalation ID from Escalation TBL starts here */
					if (levels != null) {
						levels.setSequence(index + 1);
						levelSet.add(levels);
					}
				}
			}
		}

		processChainBean
				.setLevels(levelSet.toArray(new Levels[levelSet.size()]));

		return processChainBean;
	}
	protected static Invoicedto invoiceReconciliationDetailsToBeanConverter(String deLimitedText) throws ParseException {
		
		Invoicedto invoicedto=new Invoicedto();
		HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);

		if (stringTextMap != null) {
			//invoice_Id=4;invoiceAmount=6;invoiceDate=2017/01/12;actionDate=2017/01/24;invoiceStatus=Pending;collectionAmount=5;
			//collectionDate=2017/01/24;user_Id=532;id=4;iseditinvoice=1;timesheetReportId=4376

			invoicedto.setInvoice_Id(Integer.parseInt(stringTextMap.get("invoice_Id")));
			invoicedto.setInvoiceAmount(Integer.parseInt(stringTextMap.get("invoiceAmount")));
			invoicedto.setInvoiceDate(PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("invoiceDate"))));
		
			
			invoicedto.setActionDate(PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("actionDate"))));
			invoicedto.setInvoiceStatus(stringTextMap.get("invoiceStatus"));
			
			
			
			//invoicedto.setActionDate(PortalUtility.fromStringToDate(stringTextMap.get("actionDate")));
		     invoicedto.setCollectionAmount(Integer.parseInt(stringTextMap.get("collectionAmount")));
			invoicedto.setCollectionDate(PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("collectionDate"))));
			invoicedto.setUser_Id(Integer.parseInt(stringTextMap.get("user_Id")));
			invoicedto.setId(Integer.parseInt(stringTextMap.get("id")));
			
			invoicedto.setIseditinvoice(stringTextMap.get("iseditinvoice"));
			invoicedto.setTimesheetReportId(Integer.parseInt(stringTextMap.get("timesheetReportId")));
			
			
		}
		return invoicedto;
	}
	protected static Invoicedto invoiceReconciliationDetailsToBeanConverterSubmit(String deLimitedText) throws ParseException {
		
		Invoicedto invoicedto=new Invoicedto();
		HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);

		if (stringTextMap != null) {
			
			invoicedto.setInvoice_Id(Integer.parseInt(stringTextMap.get("invoice_Id")));
			invoicedto.setInvoiceAmount(Integer.parseInt(stringTextMap.get("invoiceAmount")));
			invoicedto.setInvoiceDate(PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("invoiceDate"))));
			invoicedto.setActionDate(PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("actionDate"))));
			invoicedto.setInvoiceStatus(stringTextMap.get("invoiceStatus"));
			
			
			
			//invoicedto.setActionDate(PortalUtility.fromStringToDate(stringTextMap.get("actionDate")));
		     invoicedto.setCollectionAmount(Integer.parseInt(stringTextMap.get("collectionAmount")));
			invoicedto.setCollectionDate(PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("collectionDate"))));
			invoicedto.setUser_Id(Integer.parseInt(stringTextMap.get("user_Id")));
			invoicedto.setId(Integer.parseInt(stringTextMap.get("id")));
			
			invoicedto.setIseditinvoice(stringTextMap.get("iseditinvoice"));
			invoicedto.setTimesheetReportId(Integer.parseInt(stringTextMap.get("timesheetReportId")));

			
			
		}
		return invoicedto;
	}
	

	protected static com.dikshatech.beans.TimesheetBean DtoToBeanConverter(TimeSheetDetails timeSheetDto, String raisedBy) {
		TimesheetBean tempTimeSheetBean = new TimesheetBean();
		tempTimeSheetBean.setId(timeSheetDto.getId());
		tempTimeSheetBean.setStartDate(PortalUtility.getdd_MM_yyyy(timeSheetDto.getStartDate()).intern());
		// tempTimeSheetBean.setDueDate(PortalUtility.formatDate(timeSheetDto.getEndDate()).intern());
		tempTimeSheetBean.setEndDate(PortalUtility.getdd_MM_yyyy(timeSheetDto.getEndDate()).intern());
		tempTimeSheetBean.setStatus(timeSheetDto.getStatus());
		tempTimeSheetBean.setStatusId(Status.getStatusId(timeSheetDto.getStatus())+"");
		if (timeSheetDto.getSubmissionDate() != null) tempTimeSheetBean.setSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(timeSheetDto.getSubmissionDate()).intern());
		tempTimeSheetBean.setTimeSheetPeriod((PortalUtility.getdd_MM_yyyy(timeSheetDto.getStartDate())) + "  to  " + (PortalUtility.getdd_MM_yyyy(timeSheetDto.getEndDate())));
		tempTimeSheetBean.setRaisedBy(raisedBy);
		if (raisedBy != null) tempTimeSheetBean.setUserId(timeSheetDto.getUserId() + "");
		return tempTimeSheetBean;
	}

	protected static com.dikshatech.beans.LoanDetailsBean DtoTOBeanConverter(
			LoanDetails loanDto, String raisedBy) {
		LoanDetailsBean loanBean = new LoanDetailsBean();
		if (loanDto != null) {
			// loanBean.setLoanReqId(loanDto.getR)
			if (loanDto.getStatusId() != 0) {
				loanBean.setLoanStatus(Status.getStatus(loanDto.getStatusId()));
			}
			if (loanDto.getLoanTypeId() != 0) {

				if (loanDto.getLoanTypeId() == 1) {
					loanBean.setLoanType("PERSONAL LOAN");
					loanBean.setLoneId(loanDto.getLoanTypeId());
				}
				if (loanDto.getLoanTypeId() == 2) {
					loanBean.setLoanType("LAPTOP LOAN");
					loanBean.setLoneId(loanDto.getLoanTypeId());

				}

			}
			if (loanDto.getApprovedAmount() != 0) {
				loanBean.setLoanAmt(loanDto.getApprovedAmount());
			}
			if (loanDto.getCreateDateTime() != null) {
				loanBean.setCreatedDate(loanDto.getCreateDateTime().toString());
			}
			if (loanDto.getEligibilityAmount() != 0) {
				loanBean.setEligibilityAmt(loanDto.getEligibilityAmount());
			}
			if (loanDto.getEmiEligibility() != 0) {
				loanBean.setEmiEligibility(loanDto.getEmiEligibility());
			}
			if (loanDto.getGrossSalary() != 0) {
				loanBean.setGrossSalary(loanDto.getGrossSalary());
			}
			if (loanDto.getNetSalary() != 0) {
				loanBean.setNetSalary(loanDto.getNetSalary());
			}
			if (loanDto.getEmiPeriod() != 0) {
				loanBean.setEmiPeriod(loanDto.getEmiPeriod());
			}
			if (loanDto.getPurpose() != null) {
				loanBean.setPurpose(loanDto.getPurpose());
			}
			if (loanDto.getApplyDate() != null) {
				loanBean.setRequestedOn(loanDto.getApplyDate().toString());
			}
			/*if (loanDto.getResponseDate() != null) {
				loanBean.setResponseDate(loanDto.getResponseDate().toString());
			}*/
		}
		return loanBean;
	}

	protected static com.dikshatech.beans.LoanDetailsBean DtoTOBeanConverter(LoanRequest loanReq, String raisedBy) throws UsersDaoException,ProfileInfoDaoException, LoanRequestDaoException {
		logger.info("<<<<<>>>>>>>Inside DtoToBeanConverter<<<<<<<<>>>>>>>>");
		LoanDetailsBean loanBean = new LoanDetailsBean();
		UsersDao userDao = UsersDaoFactory.create();
		Users actionUser = null;
		ProfileInfoDao proDao = null;
		ProfileInfo proInfo = null;
		HandlerAction handler = new HandlerAction();
		LoanRequestDao loanReqDao = LoanRequestDaoFactory.create();
		LoanRequest[] loanReqArr1 = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID=? AND ACTION_TAKEN_BY!=0 AND SEQUENCE ="+loanReq.getSequence(),
						new Object[] { loanReq.getLoanId() });
		if (loanReq.getActionTakenBy() != 0) {
			actionUser = userDao.findByPrimaryKey(loanReq.getActionTakenBy());
			proDao = ProfileInfoDaoFactory.create();
			proInfo = proDao.findByPrimaryKey(actionUser.getProfileId());
		}
		if (loanReq != null) {
			// loanBean.setLoanReqId(loanDto.getR)
			if (loanReq.getStatusId() != 0) {
				loanBean.setLoanStatus(Status.getStatus(loanReq.getStatusId()));
			}
			if (loanReq.getLoanTypeId() != 0) {
				if (loanReq.getLoanTypeId() == 1) {
					loanBean.setLoanType("PERSONAL LOAN");
				}
				if (loanReq.getLoanTypeId() == 2) {
					loanBean.setLoanType("LAPTOP LOAN");
				}

			}
			if (loanReq.getRequestedLoanAmt() != 0) {
				loanBean.setLoanAmt(loanReq.getRequestedLoanAmt());
			}
			if (loanReq.getCreatedDatetime() != null) {
				loanBean.setCreatedDate(loanReq.getCreatedDatetime().toString());
			}
			if (loanReq.getLoanUserId() != 0) {
				loanBean.setEmpId(loanReq.getLoanUserId());
			}
			if (loanReq.getComments() != null) {
				loanBean.setComment(loanReq.getComments());
			}
			/*
			 * if(loanReqArr1[0].getActionTakenBy()!=0) { actionUser =
			 * userDao.findByPrimaryKey(loanReqArr1[0].getActionTakenBy());
			 * proDao = ProfileInfoDaoFactory.create(); proInfo =
			 * proDao.findByPrimaryKey(actionUser.getProfileId());
			 * 
			 * }
			 */
			if (loanReqArr1.length > 0)
			{	if (loanReq.getActionTakenBy() != 0 && ((loanReq.getStatusId() == Status.getStatusId(Status.ACCEPTED)) 
													||(loanReq.getStatusId() == Status.getStatusId(Status.REJECTED)) 
													||(loanReq.getStatusId() == Status.getStatusId(Status.ON_HOLD)))) {
						actionUser = userDao.findByPrimaryKey(loanReq.getActionTakenBy());
						proDao = ProfileInfoDaoFactory.create();
						proInfo = proDao.findByPrimaryKey(actionUser.getProfileId());
						loanBean.setResponseDate(loanReq.getActionTakenDate().toString());
						loanBean.setResponseBy(proInfo.getFirstName());
						loanBean.setLoanStatus(Status.getStatus(loanReq.getStatusId()));
					}
				//}
			else {
				actionUser = userDao.findByPrimaryKey(loanReqArr1[0].getActionTakenBy());
				proDao = ProfileInfoDaoFactory.create();
				proInfo = proDao.findByPrimaryKey(actionUser.getProfileId());
				loanBean.setResponseDate(loanReqArr1[0].getActionTakenDate().toString());
				loanBean.setResponseBy(proInfo.getFirstName());
				loanBean.setLoanStatus(Status.getStatus(loanReqArr1[0].getStatusId()));
			}
			}
			if (loanReq.getCreatedDatetime() != null) {
				loanBean.setRequestedOn(loanReq.getCreatedDatetime().toString());
			}
			/*
			 * if(loanReq.getEligibilityAmount()!=0) {
			 * loanBean.setEligibilityAmt(loanDto.getEligibilityAmount()); }
			 * if(loanDto.getEmiEligibility()!=0) {
			 * loanBean.setEmiEligibility(loanDto.getEmiEligibility()); }
			 * if(loanDto.getGrossSalary()!=0) {
			 * loanBean.setGrossSalary(loanDto.getGrossSalary()); }
			 * if(loanDto.getNetSalary()!=0) {
			 * loanBean.setNetSalary(loanDto.getNetSalary()); }
			 * if(loanDto.getEmiPeriod()!=0) {
			 * loanBean.setEmiPeriod(loanDto.getEmiPeriod()); }
			 * if(loanDto.getPurpose()!=null) {
			 * loanBean.setPurpose(loanDto.getPurpose()); }
			 * if(loanDto.getApplyDate()!=null) {
			 * loanBean.setRequestedOn(loanDto.getApplyDate().toString()); }
			 * if(loanDto.getResponseDate()!=null) {
			 * loanBean.setResponseDate(loanDto.getResponseDate().toString()); }
			 */
		}
		return loanBean;
	}

	protected static ClientDetailsBean PopulateBeanFromDto(
			ClientDivMap[] clientDivions) {
		ClientDetailsBean object = new ClientDetailsBean();
		try {
			Set<Division> divisions = new HashSet<Division>();
			DivisonDao divisonDao = DivisonDaoFactory.create();
			for (ClientDivMap allDivision : clientDivions) {
				Divison division = divisonDao.findByPrimaryKey(allDivision
						.getDivId());
				Division div = new Division();
				div.setDivisionId(division.getId());
				div.setDivisionName(division.getName());
				divisions.add(div);
			}
			object.setDivisions(divisions);
		} catch (DivisonDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;

	}

	protected static ProjContInfo stringToProjInfoConverter(String deLimitedText) {
		HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);
		ProjContInfo contInfo = new ProjContInfo();
		if (stringTextMap != null) {
			contInfo.setName((stringTextMap.get("contName") != null) ? stringTextMap
					.get("contName") : null);
			contInfo.setDesignation((stringTextMap.get("contDesignation") != null) ? stringTextMap
					.get("contDesignation") : null);
			contInfo.setEMail((stringTextMap.get("contEmail") != null) ? stringTextMap
					.get("contEmail") : null);
			contInfo.setContType((stringTextMap.get("contType") != null) ? stringTextMap
					.get("contType") : null);
			contInfo.setComments((stringTextMap.get("contComments") != null) ? stringTextMap
					.get("contComments") : null);

			if (stringTextMap.get("contNumber") != null)
				contInfo.setContNumber(stringTextMap.get("contNumber"));

		}
		return contInfo;
	}

	protected static ProjLocations stringToProjLocConverter(String deLimitedText) {
		HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);
		ProjLocations locations = new ProjLocations();
		if (stringTextMap != null) {
			locations
					.setName((stringTextMap.get("locName") != null) ? stringTextMap
							.get("locName") : null);
			locations
					.setAddress((stringTextMap.get("locAddress") != null) ? stringTextMap
							.get("locAddress") : null);
			locations
					.setCity((stringTextMap.get("locCity") != null) ? stringTextMap
							.get("locCity") : null);
			locations
					.setState((stringTextMap.get("locState") != null) ? stringTextMap
							.get("locState") : null);
			locations
					.setCountry((stringTextMap.get("locCountry") != null) ? stringTextMap
							.get("locCountry") : null);
			if (stringTextMap.get("locZip") != null)
				locations.setZipCode(Integer.parseInt(stringTextMap
						.get("locZip")));

			if (stringTextMap.get("locTelNo") != null)
				locations.setTelNum(stringTextMap.get("locTelNo"));
			if (stringTextMap.get("locFaxNo") != null)
				locations.setFaxNum(stringTextMap.get("locFaxNo"));

		}
		return locations;
	}
//inactive=1;empPoNumber=43;empPoDate=2016/11/21;empPoStDate=2016/11/22;empPoEndDate=2016/11/28;empPoDuration=6
	protected static PoEmpMap stringToBeanConverter(String deLimitedText) {
		PoEmpMap poEmpObj = new PoEmpMap();
		HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);

		if (stringTextMap != null) {
			poEmpObj.setEmpId(Integer.parseInt(stringTextMap.get("userId")));
			// poEmpObj.setPoId(Integer.parseInt(stringTextMap.get("poId")));
			poEmpObj.setRate(stringTextMap.get("poRate"));
			poEmpObj.setCurrency(stringTextMap.get("currency"));
			poEmpObj.setType(stringTextMap.get("poType"));
			poEmpObj.setInactive(Short.parseShort(stringTextMap.get("inactive")));
			
			
			poEmpObj.setEmpPoNumber(stringTextMap.get("empPoNumber"));
			
			String podate=stringTextMap.get("empPoDate");
			String poSdate=stringTextMap.get("empPoStDate");
			String poEdate=stringTextMap.get("empPoEndDate");
			//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
			
			   java.util.Date date1;
			   java.util.Date date2;
			   java.util.Date date3;
		
				 try {
				
					 
					 if(podate!=null && podate !="null"){
						 date1 = formatter.parse(podate);
							poEmpObj.setEmpPoDate(date1);
							}
					 
					 if(poSdate!=null && poSdate !="null"){
						 date2 = formatter.parse(poSdate);
							poEmpObj.setEmpPoStDate(date2);
			         }
					 if(poEdate!=null && poEdate !="null"){
						 date3 =  formatter.parse(poEdate);
						 poEmpObj.setEmpPoEndDate(date3);
				   
					 }
				
				         poEmpObj.setEmpPoDuration(stringTextMap.get("empPoDuration"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			//update CURRENT in PO_EMP_MAP set it to 0 for old charge-code .. if associated
			
			
		}
		return poEmpObj;
	}
	//"userId=365;empPoNumber=99;empPoDate=22-12-2016;empPoStDate=22-12-2016;empPoEndDate=20-12-2016;empPoDuration=2"
	protected static PoDetail stringConvertor(String deLimitedText) {
		PoDetail poDet = new PoDetail();
		//PoEmpMap poEmpObj = new PoEmpMap();
		HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);

		if (stringTextMap != null) {
			poDet.setEmpPoNumber(stringTextMap.get("empPoNumber"));
			
			String podate=stringTextMap.get("empPoDate");
			String poSdate=stringTextMap.get("empPoStDate");
			String poEdate=stringTextMap.get("empPoEndDate");
			//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			
			/*String testDate = "29-Apr-2010,13:00:14 PM";
			DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
			Date date = formatter.parse(testDate);
			System.out.println(date);*/
			
			   //get current date time with Date()
			  // Date date = new Date();
			   java.util.Date date1;
			   java.util.Date date2;
			   java.util.Date date3;
			try {
				 date1 = formatter.parse(podate);
				date2 = formatter.parse(poSdate);
				date3 =  formatter.parse(poEdate);
				//datee = (Date) dateFormat.parse(poEdate);
				   //java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				poDet.setEmpPoDate(date1);
				poDet.setEmpPoStDate(date2);
				poDet.setEmpPoEndDate(date3);
				
				poDet.setEmpPoDuration(stringTextMap.get("empPoDuration"));
				 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		
			
			//update CURRENT in PO_EMP_MAP set it to 0 for old charge-code .. if associated
			/*try{
				PoDetailsModel.updatePoEmpMap(poEmpObj.getEmpId());
			}catch(Exception ex){
				logger.error("charge-code new  : update CURRENT=0 failed for empId="+poEmpObj.getEmpId(),ex);
			}*/
			
		}
		return poDet;
	}

	/***
	 * @author praveen.kumar
	 * @param payslip
	 * @return
	 */
	protected static com.dikshatech.beans.PayslipBean DtoToBeanConverter(
			Payslip payslip) {
		com.dikshatech.beans.PayslipBean payslipBean = new com.dikshatech.beans.PayslipBean();
		try {
			Users[] users = null;
			payslipBean.setFileID(payslip.getFileID());
			payslipBean.setID(payslip.getId());
			DocumentsDao documentsDao = DocumentsDaoFactory.create();

			Documents[] documents = documentsDao.findWhereIdEquals(payslipBean
					.getFileID());
			payslipBean.setFileName(documents[0].getFilename());
			payslipBean.setDescriptions(documents[0].getDescriptions());
			UsersDao usersDao = UsersDaoFactory.create();

			users = usersDao.findWhereEmpIdEquals(payslip.getUserID());
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			ProfileInfo[] profileInfo;
			if (users != null && users.length > 0) {
				profileInfo = profileInfoDao.findWhereIdEquals(users[0]
						.getProfileId());

				payslipBean.setUserFname(profileInfo[0].getFirstName());
				payslipBean.setUserMname(profileInfo[0].getMiddleName());
				payslipBean.setUserLname(profileInfo[0].getLastName());
				payslipBean.setUserID(payslip.getUserID());
				payslipBean.setMonth(payslip.getMonth());
				payslipBean.setYear(payslip.getYear());
			}
		} catch (UsersDaoException e) {
			e.printStackTrace();
		} catch (ProfileInfoDaoException e) {
			e.printStackTrace();
		} catch (DocumentsDaoException e) {
			e.printStackTrace();
		}
		return payslipBean;
	}

	/**
	 * @author supriya.bhike
	 * @param profileinfo
	 * @return Profile Bean setting the first name and id for drop down of
	 *         candidate
	 */
	protected static com.dikshatech.beans.ProfileBean DtoToBeanConverter(
			ProfileInfo profileinfo) {
		com.dikshatech.beans.ProfileBean profilebean = new com.dikshatech.beans.ProfileBean();
		profilebean.setId(profileinfo.getId());
		profilebean.setFirstName(profileinfo.getFirstName());
		profilebean.setLastName(profileinfo.getLastName());
		return profilebean;
	}

	protected static ProjLocations DtoToBeanConverter(ProjLocations location) {
		ProjLocations projLocDato = new ProjLocations();
		projLocDato.setId(location.getId());
		projLocDato.setCity(location.getCity());
		projLocDato.setCountry(location.getCountry());
		projLocDato.setName(location.getName());
		projLocDato.setState(location.getState());
		projLocDato.setTelNum(location.getTelNum());
		projLocDato.setFaxNum(location.getFaxNum());
		projLocDato.setAddress(location.getAddress());
		projLocDato.setZipCode(location.getZipCode());

		return projLocDato;
	}

	protected static com.dikshatech.beans.CommitmentBean DtoToBeanConverter(
			Commitments commitment) {
		com.dikshatech.beans.CommitmentBean bean = new com.dikshatech.beans.CommitmentBean();
		bean.setId(commitment.getId());
		bean.setComments(commitment.getComments());
		bean.setDatePicker(commitment.getDatePicker());
		bean.setCandidateId(commitment.getCandidateId());
		bean.setUserIdEmp(commitment.getUserIdEmp());
		return bean;
	}

	protected static com.dikshatech.beans.Features DtoToBeanConverter(
			Features featureBean) {
		String tempPermission = "";
		String[] permissionLabels;
		String[] permissionSelected;

		if (featureBean.getAccessibilitySelected() != null
				&& featureBean.getPermissionLabels() != null) {

			permissionLabels = featureBean.getAccessibilitySelected()
					.split(";");
			permissionSelected = featureBean.getPermissionLabels().split(";");

			List<String> list1 = Arrays.asList(permissionLabels);

			for (String temp : permissionSelected) {

				if (list1.contains(temp))
					tempPermission += temp + ";";
			}

			featureBean.setAccessibilitySelected(tempPermission);
		}

		return featureBean;

	}

	protected static com.dikshatech.beans.ReimbursementBean DtoToBeanConverter(
			ReimbursementReq dto, ReimbursementFinancialData fdto[]) {
		ReimbursementBean bean = new ReimbursementBean();
		bean.setId(dto.getId());
		bean.setEsrMapId(dto.getEsrMapId());
		bean.setProjectCode(dto.getProjectCode());
		bean.setProjectName(dto.getProjectName());
		bean.setChargeCode(dto.getChargeCode());
		bean.setCcTitle(dto.getCcTitle());
		bean.setDescription(dto.getDescription());
		bean.setAssignTo(dto.getAssignTo());
		bean.setStatus(dto.getStatus());
		bean.setOldStatus(dto.getOldStatus());
		bean.setRemark(dto.getRemark());
		bean.setMessageBody(dto.getMessageBody());
		bean.setRequesterId(dto.getRequesterId());
		bean.setCreateDate(PortalUtility.formatDateddmmyyyyhhmmss(dto.getCreateDate()));
		bean.setChargeCodeName(dto.getChargeCodeName());
		bean.setFinacialDetails(fdto);
		

		return bean;
	}

	protected static com.dikshatech.beans.IssueBean DtoToBeanConverterIssueDeptList(
			Issues issueDto) {

		IssueBean issueBean = null;

		try {
			issueBean = new IssueBean();
			DivisonDao divDao = DivisonDaoFactory.create();

			issueBean.setIssueId(issueDto.getId());
			issueBean.setIssueName(issueDto.getIsName());
			issueBean.setIssueDeptId(issueDto.getDivId());
			issueBean.setIssueFeatureId(issueDto.getFeatureIssueId());
			issueBean.setIssueDeptName(divDao.findByPrimaryKey(
					issueDto.getDivId()).getName());

		} catch (DivisonDaoException de) {
			de.printStackTrace();
			issueBean = null;

		}
		return issueBean;

	}

	protected static com.dikshatech.beans.RequestIssueBean DtoToBeanConverter(
			RequestedIssues requestIssueDto, String raisedBy) {

		RequestIssueBean reqIssueBean = new RequestIssueBean();
		reqIssueBean.setId(requestIssueDto.getId());
		reqIssueBean.setStatus(requestIssueDto.getStatus());
		reqIssueBean.setSummary(requestIssueDto.getSummary());
		reqIssueBean.setDescription(requestIssueDto.getDescription());
		reqIssueBean.setDependentId(requestIssueDto.getDependentId());
		reqIssueBean.setEsrMapId(requestIssueDto.getEsrMapId());
		reqIssueBean.setAutoReqId(requestIssueDto.getAutoReqId());
		reqIssueBean.setRequestedBy(raisedBy);
		reqIssueBean.setSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(requestIssueDto.getSubmissionDate()).intern());
		reqIssueBean.setUserId(requestIssueDto.getUserId());
		return reqIssueBean;

	}

	public static BankDetalilsBean DtoToBeanConverter(BankDetails bank) {
		BankDetalilsBean bankDetalilsBean = new BankDetalilsBean();
		bankDetalilsBean.setId(bank.getId());
		bankDetalilsBean.setBankAddress(bank.getBankAddress());
		bankDetalilsBean.setBankName(bank.getBankName());
		bankDetalilsBean.setAccountNumber(bank.getAccountNumber());
		bankDetalilsBean.setIfciNumber(bank.getIfciNumber());
		bankDetalilsBean.setSwiftCode(bank.getSwiftCode());
		bankDetalilsBean.setMicrCode(bank.getMicrCode());
		bankDetalilsBean.setBranch(bank.getBranch());
		bankDetalilsBean.setCreatedBy(bank.getCreatedBy());
		bankDetalilsBean.setCreatedOn(bank.getCreatedOn());
		return bankDetalilsBean;
	}

	public static NewsBean DtoToBeanConverter(News news) {
		NewsBean newsBean = new NewsBean();
		newsBean.setId(news.getId());
		newsBean.setNewsTitle(news.getNewsTitle());
		newsBean.setNewsHtmlTitle(news.getNewsHtmlTitle());
		newsBean.setNewsDisc(news.getNewsDisc());
		newsBean.setPhotos(news.getPhotos());
		return newsBean;
	}
	public static NewsBean DtoToBeanConverterCordova(News news) {
		NewsBean newsBean = new NewsBean();
		newsBean.setId(news.getId());
		newsBean.setNewsTitle(news.getNewsTitle());
		newsBean.setNewsHtmlTitle(news.getNewsHtmlTitle());
		newsBean.setNewsDisc(news.getNewsDisc());
		newsBean.setPhotos(news.getPhotos());
		if(news.getPhotos() != null && news.getPhotos() != ""){
			DocumentsDao d = DocumentsDaoFactory.create();
			Documents doc = null;
			try {
				 doc = d.findByPrimaryKey( Integer.parseInt(news.getPhotos()));
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (DocumentsDaoException e) {
				
				e.printStackTrace();
			}
			newsBean.setImageName(doc.getFilename());
		} else
		{
			newsBean.setImageName("");
		}
		
		return newsBean;
	}
	public static ResumeManagementBean DtoToBeanConverter(ResumeManagement resumeManagement) {
		
		ResumeManagementBean resumeManagementBeanBean = new ResumeManagementBean();		
		UsersDao userDao = UsersDaoFactory.create();
		Users recrutUser=null;
		try {
			recrutUser = userDao.findByPrimaryKey(resumeManagement.getRecrutId());
		} catch (UsersDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ProfileInfo recrutProfile=null;
		try {
			if(recrutUser!=null)
			recrutProfile = profileInfoDao.findByPrimaryKey(recrutUser.getProfileId());
		} catch (ProfileInfoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		resumeManagementBeanBean.setId(resumeManagement.getId());
		resumeManagementBeanBean.setName((resumeManagement.getCandidateFirstName()+ " " +resumeManagement.getCandidateLastName()));
		resumeManagementBeanBean.setVisaType(resumeManagement.getVisaType());
		resumeManagementBeanBean.setResume(resumeManagement.getResume());
		resumeManagementBeanBean.setCellNo(resumeManagement.getCellNo());
		resumeManagementBeanBean.setBillRate(resumeManagement.getBillRate());
		resumeManagementBeanBean.setRecrutId(resumeManagement.getRecrutId());
		if(recrutProfile!=null)
		resumeManagementBeanBean.setRecrutName(recrutProfile.getFirstName()+" "+recrutProfile.getLastName());
		resumeManagementBeanBean.setSkillSet(resumeManagement.getSkillSet());
		resumeManagementBeanBean.setPayRate(resumeManagement.getPayRate());
		resumeManagementBeanBean.setRelocation(resumeManagement.getRelocation());	
		resumeManagementBeanBean.setState(resumeManagement.getState());
		resumeManagementBeanBean.setCity(resumeManagement.getCity());
		return resumeManagementBeanBean;
	}
	
	public static IssueBean DtoToBeanConverter(IssueBean issuBean,
			Users userDto, ProfileInfo profileDto, Levels levelsDto) {

		DivisonDao divDao = DivisonDaoFactory.create();
		try {
			issuBean.setEmployeeId(new Integer(userDto.getEmpId()).toString());
			issuBean.setEmpName(profileDto.getFirstName());
			issuBean.setEmpDesignation(levelsDto.getDesignation());
			issuBean.setEmpDivisionName(divDao.findByPrimaryKey(
					levelsDto.getDivisionId()).getName());
		} catch (DivisonDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return issuBean;

	}

	public static EmpserReqMapBean DtoToBeanConverter(
			EmpSerReqMap singleSerRequest) {
		EmpserReqMapBean empserReqMapBean = new EmpserReqMapBean();

		empserReqMapBean.setId(singleSerRequest.getId());
		empserReqMapBean
				.setProcessChainId(singleSerRequest.getProcessChainId());
		empserReqMapBean.setReqTypeId(singleSerRequest.getReqTypeId());
		empserReqMapBean.setReqId(singleSerRequest.getReqId());
		empserReqMapBean.setRequestorId(singleSerRequest.getRequestorId());
		empserReqMapBean.setRegionId(singleSerRequest.getRegionId());
		empserReqMapBean.setSubDate(singleSerRequest.getSubDate());
		empserReqMapBean.setNotify(singleSerRequest.getNotify());

		return empserReqMapBean;

	}
	

	//rmgTimeSheetReportId=599;leave=55;timeSheet_Cato=Pending Submission;status=Approved;ActionBy=Shambhavi;user_Id=532;
	//startDate=2017/01/24; =2017/01/24;working_Days=0;trFlag=1
		protected static RmgTimeSheet rmgTimeSheetDetailsToBeanConverter(String deLimitedText) throws ParseException {
			RmgTimeSheet timeSheetReconciliation = new RmgTimeSheet();
			HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);
			//"userId=365;prj_Id=99;chrg_Code_Id=22-12-2016;working_Days=22-12-2016"
			if (stringTextMap != null) {
				timeSheetReconciliation.setId(Integer.parseInt(stringTextMap.get("rmgTimeSheetReportId")));
				timeSheetReconciliation.setLeave(Integer.parseInt(stringTextMap.get("leave")));
				timeSheetReconciliation.setTimeSheet_Cato(stringTextMap.get("timeSheet_Cato"));
				timeSheetReconciliation.setStatus(stringTextMap.get("status"));
				timeSheetReconciliation.setActionBy(stringTextMap.get("ActionBy"));
				timeSheetReconciliation.setUser_Id(Integer.parseInt(stringTextMap.get("user_Id")));
				timeSheetReconciliation.setStartDate (PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("startDate"))));
				timeSheetReconciliation.setEndDate (PortalUtility.utilToSql(PortalUtility.fromStringToDates(stringTextMap.get("endDate"))));
				timeSheetReconciliation.setWorking_Days(Integer.parseInt(stringTextMap.get("working_Days")));
				timeSheetReconciliation.setTr_Flag(stringTextMap.get("trFlag"));
				
				/*timeSheetReconciliation.setPrj_Id(Integer.parseInt(stringTextMap.get("prj_Id")));
				timeSheetReconciliation.setChrg_Code_Id(Integer.parseInt(stringTextMap.get("chrg_Code_Id")));
				timeSheetReconciliation.setWorking_Days(Integer.parseInt(stringTextMap.get("working_Days")));*/
				

				//timeSheetReconciliation.setTimeSheet_Cato(stringTextMap.get("poRate"));
				//timeSheetReconciliation.setStatus(stringTextMap.get("currency"));
				//timeSheetReconciliation.setActionBy(stringTextMap.get("poType"));
				
			}
			return timeSheetReconciliation;
		}
		
		protected static ExperienceInfo backGroundVerificationDocToBeanConverter(String deLimitedText) {
			ExperienceInfo backGroundVeri = new ExperienceInfo();
			HashMap<String, String> stringTextMap = getStringTokensMap(deLimitedText);
			//id=11112,backGroundVerificationDocId=16
			if (stringTextMap != null) {
				backGroundVeri.setVerificationFileId(Integer.parseInt(stringTextMap.get("id")));//file Id
		      String data=stringTextMap.get("backGroundVerificationDocId");
		if(data!=null){
			backGroundVeri.setBackGroundVeriFileId(Integer.parseInt(stringTextMap.get("backGroundVerificationDocId")));

		}
			
				
			}
			return backGroundVeri;
		}
		

}
