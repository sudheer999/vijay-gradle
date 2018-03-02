package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.CandidateCommonBean;
import com.dikshatech.beans.CommitmentBean;
import com.dikshatech.beans.ProfileBean;
import com.dikshatech.beans.Salary;
import com.dikshatech.beans.SalaryConfigs;
import com.dikshatech.beans.SalaryHead;
import com.dikshatech.beans.SalaryInfoBean;
import com.dikshatech.common.db.MyDBConnect;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.ExportType;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PopulateForms;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.Status;
import com.dikshatech.init.ContextListener;
import com.dikshatech.jasper.JGenerator;
import com.dikshatech.jasper.JTemplates;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AddressDao;
import com.dikshatech.portal.dao.CandidateChecklistDocsDao;
import com.dikshatech.portal.dao.CandidateChecklistStatusDao;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.CandidatePerdiemDetailsDao;
import com.dikshatech.portal.dao.CandidateReqDao;
import com.dikshatech.portal.dao.CommitmentsDao;
import com.dikshatech.portal.dao.CompanyDao;
import com.dikshatech.portal.dao.ContactTypeDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EducationInfoDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ExperienceInfoDao;
import com.dikshatech.portal.dao.FixedCandidatePerdiemDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.LoginDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.SalaryInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Address;
import com.dikshatech.portal.dto.AddressPk;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.CandidateChecklistStatus;
import com.dikshatech.portal.dto.CandidateChecklistStatusPk;
import com.dikshatech.portal.dto.CandidatePerdiemDetails;
import com.dikshatech.portal.dto.CandidatePerdiemDetailsPk;
import com.dikshatech.portal.dto.CandidatePk;
import com.dikshatech.portal.dto.CandidateReq;
import com.dikshatech.portal.dto.CandidateReqPk;
import com.dikshatech.portal.dto.Commitments;
import com.dikshatech.portal.dto.CommitmentsPk;
import com.dikshatech.portal.dto.Company;
import com.dikshatech.portal.dto.ContactType;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EducationInfo;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.ExperienceInfo;
import com.dikshatech.portal.dto.FinanceInfo;
import com.dikshatech.portal.dto.FixedCandidatePerdiem;
import com.dikshatech.portal.dto.FixedCandidatePerdiemPk;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PassportInfo;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.PersonalInfoPk;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SalaryDetailsPk;
import com.dikshatech.portal.dto.SalaryInfo;
import com.dikshatech.portal.dto.SalaryInfoPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.CandidateChecklistStatusDaoException;
import com.dikshatech.portal.exceptions.CandidateDaoException;
import com.dikshatech.portal.exceptions.CandidateReqDaoException;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.FixedCandidatePerdiemDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.SalaryInfoDaoException;
import com.dikshatech.portal.factory.AddressDaoFactory;
import com.dikshatech.portal.factory.CandidateChecklistDocsDaoFactory;
import com.dikshatech.portal.factory.CandidateChecklistStatusDaoFactory;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.CandidatePerdiemDetailsDaoFactory;
import com.dikshatech.portal.factory.CandidateReqDaoFactory;
import com.dikshatech.portal.factory.CommitmentsDaoFactory;
import com.dikshatech.portal.factory.CompanyDaoFactory;
import com.dikshatech.portal.factory.ContactTypeDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EducationInfoDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ExperienceInfoDaoFactory;
import com.dikshatech.portal.factory.FixedCandidatePerdiemDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.LoginDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.SalaryInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.CandidateSaveForm;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

/**
 * @author supriya.bhike
 */
public class CandidateModel extends ActionMethods {

	private static Logger	logger	= LoggerUtil.getLogger(CandidateModel.class);

	public CandidateModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Candidate candidate = (Candidate) form;
		CandidateDao candidateDao = CandidateDaoFactory.create();
		CandidatePk candidatePk = new CandidatePk();
		candidatePk.setId(candidate.getId());
		try{
			candidate = candidateDao.findByPrimaryKey(candidatePk);
			if (candidate.getEducationId() > 0){
				EducationInfoModel educationInfoModel = new EducationInfoModel();
				EducationInfo educationInfo = new EducationInfo();
				educationInfo.setId(candidate.getEducationId());
				educationInfoModel.delete(educationInfo, request);
			}
			if (candidate.getExperienceId() > 0){
				ExperienceInfoModel experienceInfoModel = new ExperienceInfoModel();
				ExperienceInfo experienceInfo = new ExperienceInfo();
				experienceInfo.setId(candidate.getExperienceId());
				experienceInfoModel.delete(experienceInfo, request);
			}
			if (candidate.getFinancialId() > 0){
				FinanceInfoModel financeInfoModel = new FinanceInfoModel();
				FinanceInfo financeInfo = new FinanceInfo();
				financeInfo.setId(candidate.getFinancialId());
				financeInfoModel.delete(financeInfo, request);
			}
			if (candidate.getPersonalId() > 0){
				PersonalInfoModel personalInfoModel = new PersonalInfoModel();
				PersonalInfo personalinfo = new PersonalInfo();
				personalinfo.setId(candidate.getPersonalId());
				personalInfoModel.delete(personalinfo, request);
			}
			if (candidate.getPassportId() > 0){
				PassportInfoModel passportInfoModel = new PassportInfoModel();
				PassportInfo passportInfo = new PassportInfo();
				passportInfo.setId(candidate.getPassportId());
				passportInfoModel.delete(passportInfo, request);
			}
			if (candidate.getProfileId() > 0){
				ProfileInfoModel profileInfoModel = new ProfileInfoModel();
				ProfileInfo profileInfo = new ProfileInfo();
				profileInfo.setId(candidate.getProfileId());
				profileInfoModel.delete(profileInfo, request);
			}
			SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
			SalaryDetails salaryDetails2[] = null;
			salaryDetails2 = salaryDetailsDao.findWhereCandidateIdEquals(candidate.getId());
			if (salaryDetails2.length > 0){
				SalaryDetailsPk salaryDetailsPk = new SalaryDetailsPk();
				for (int i = 0; i < salaryDetails2.length; i++){
					salaryDetailsPk.setId(salaryDetails2[i].getId());
					salaryDetailsDao.delete(salaryDetailsPk);
				}
			}
			// delete from commitment table
			CommitmentsDao commitmentDao = CommitmentsDaoFactory.create();
			Commitments[] commit = commitmentDao.findWhereCandidateIdEquals(candidate.getId());
			if (commit.length > 0){
				CommitmentsPk pk = new CommitmentsPk();
				for (Commitments c : commit){
					pk.setId(c.getId());
					commitmentDao.delete(pk);
				}
			}
		} catch (Exception e){
			logger.info("Failed to delete Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		} finally{
			try{
				candidateDao.delete(candidatePk);
			} catch (CandidateDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		ActionResult actionResult = new ActionResult();
		this.deleteFiles(form);
		return actionResult;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author supriya.bhike
	 * @param PortalForm
	 *            ,HttpServletRequest
	 * @return Returns a single or list of Candidate names and respective Id's
	 *         for drop down
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		EducationInfoDao educationinfodao = EducationInfoDaoFactory.create();
		ExperienceInfoDao eInfoDao = ExperienceInfoDaoFactory.create();
		Candidate candidate;
		ActionResult result = new ActionResult();
		//SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		try{
			switch (ReceiveTypes.getValue(form.getrType())) {
				case OFFERPREVIEW:
					String destPath = ContextListener.realPath + File.separator + "preview";
					Candidate candidate2 = (Candidate) form;
					
					boolean GenerateOfferLetter = false;
					HttpSession session=request.getSession(false);
					boolean view = candidate2.isView() ? true : false;
					if (candidate2.isGenerateOfferLetter()){
						GenerateOfferLetter = true;
					}
					CandidateDao cDao = CandidateDaoFactory.create();
					ProfileInfoDao pDao = ProfileInfoDaoFactory.create();
					//CommitmentsDao commitmentsDao = CommitmentsDaoFactory.create();
			Candidate candidate1 = cDao.findByPrimaryKey(candidate2.getId());
			candidate1.setHideModifyButton(candidate2.isHideModifyButton());
					if (candidate1.getStatus() == 7 && GenerateOfferLetter){
						candidate1.setGenerateOfferLetter(GenerateOfferLetter);
					}
					String tempFileName1 = generateFName(candidate1.getId(), PortalData.OFF_LETTER, ExportType.pdf);
					JGenerator jGenerator1 = new JGenerator();
					HashMap<String, Object> params = getSalaryReportParams(candidate1.getId(), candidate1.getProApprovalId() > 0 ? true : false);
					ProfileInfo profileInfo = pDao.findByPrimaryKey(candidate1.getProfileId());
			 String template = getTemplate(profileInfo.getLevelId(), candidate1.getProApprovalId() > 0 ? true : false);
			// String template = JTemplates.OFFER_LETTER_CHECK;
					if (jGenerator1.generateFile(JGenerator.CANDIDATE, tempFileName1,template, params)){
						candidate1.setOfferLetter(tempFileName1);
					}
					// temp=PORTAL_HOME + relative folder path
					String temp = JGenerator.getOutputFile(JGenerator.CANDIDATE, candidate1.getOfferLetter());
					// destination=/opt/dev-env/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/DikshaPortal/preview$
					// which is servlet context path
					try{
     					File source = new File(temp);
						File destination = new File(destPath + File.separator + candidate1.getOfferLetter());
						FileUtils.copyFile(source, destination);
						request.setAttribute("actionForm", candidate1);
						logger.info("Copied offer letter to path: " + destination);
						
					} catch (Exception e){
						e.printStackTrace();
					}
					candidate1.setView(view);
					request.setAttribute("actionForm",candidate1);
					break;
					
				case OFFERFORAPPROVAL:
					String destPath1 = ContextListener.realPath + File.separator + "preview";
					Candidate candidate3 = (Candidate) form;
		            CandidateDao canDao = CandidateDaoFactory.create();
					ProfileInfoDao profDao = ProfileInfoDaoFactory.create();
					Candidate candidate4 = canDao.findByPrimaryKey(candidate3.getId());
			        candidate4.setHideModifyButton(candidate3.isHideModifyButton());
				
					String tempFileName = generateFName(candidate4.getId(), PortalData.OFF_LETTER, ExportType.pdf);
					JGenerator jGenerator = new JGenerator();
					HashMap<String, Object> params1 = getSalaryReportParams(candidate4.getId(), candidate4.getProApprovalId() > 0 ? true : false);
					ProfileInfo profileInfo1 = profDao.findByPrimaryKey(candidate4.getProfileId());
			       String template1 ="OfferApprove.jrxml";
					if (jGenerator.generateFile(JGenerator.CANDIDATE, tempFileName,template1, params1)){
						candidate4.setOfferLetter(tempFileName);
					}
					// temp=PORTAL_HOME + relative folder path
					String temp1 = JGenerator.getOutputFile(JGenerator.CANDIDATE, candidate4.getOfferLetter());
					// destination=/opt/dev-env/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/DikshaPortal/preview$
					// which is servlet context path
					try{
     					File source = new File(temp1);
						File destination = new File(destPath1 + File.separator + candidate4.getOfferLetter());
						FileUtils.copyFile(source, destination);
						request.setAttribute("actionForm", candidate4);
						logger.info("Copied offer letter to path: " + destination);
						
					} catch (Exception e){
						e.printStackTrace();
					}
					request.setAttribute("actionForm",candidate4);
					break;
				
				
				case RECEIVEUPDATEPROFILE:
					candidate = (Candidate) form;
					CandidateDao cDao1 = CandidateDaoFactory.create();
					Candidate profilecandidate = cDao1.findByPrimaryKey(candidate.getCandidateId());
					ContactTypeDao contactTypeDao = ContactTypeDaoFactory.create();
					ContactType contactType[] = contactTypeDao.findWhereCandidateIdEquals(candidate.getCandidateId());
					EducationInfo educationinfo[] = educationinfodao.findWhereCandidateIdEquals(candidate.getCandidateId());
					ExperienceInfo exInfo[] = eInfoDao.findWhereCandidateIdEquals(candidate.getCandidateId());
					if (educationinfo.length > 0){
						profilecandidate.setEducationId(1);
					}
					if (exInfo.length > 0){
						profilecandidate.setExperienceId(1);
					}
					if (contactType.length > 0){
						
						
						profilecandidate.setContactType(1);
					}
					if (profilecandidate.getId() != 0){
						profilecandidate.setCandidateId(candidate.getCandidateId());
						request.setAttribute("actionForm", profilecandidate);
					} else{
						request.setAttribute("actionForm",candidate);
					}
					break;
				case RECEIVEALL:
					DropDown dropDown = (DropDown) form;
					ProfileInfoDao profileinfodao = ProfileInfoDaoFactory.create();
					ProfileInfo[] profileinfoDtos = profileinfodao.findAll();
					ProfileBean[] profileinfobeans = new ProfileBean[profileinfoDtos.length];
					int i = 0;
					for (ProfileInfo profileDto : profileinfoDtos){
						ProfileBean proBean = DtoToBeanConverter.DtoToBeanConverter(profileDto, null);
						profileinfobeans[i] = proBean;
						i++;
					}
					dropDown.setDropDown(profileinfobeans);
					request.setAttribute("actionForm", dropDown);
					break;
				case RECEIVE:
					int levelid = 0;
					CandidateSaveForm candidateSaveForm = (CandidateSaveForm) form;
					profileInfo = new ProfileInfo();
					Address permaddress = new Address();
					PersonalInfo personalInfo = new PersonalInfo();
					candidate = new Candidate();
					candidate.setId(candidateSaveForm.getCandidateId());
					CandidateDao candidateDao1 = CandidateDaoFactory.create();
					candidate = candidateDao1.findByPrimaryKey(candidateSaveForm.getCandidateId());
					profileInfo.setId(candidate.getProfileId());
					personalInfo.setId(candidate.getPersonalId());
					ProfileInfoDao profiledao = ProfileInfoDaoFactory.create();
					profileInfo = profiledao.findByPrimaryKey(profileInfo.getId());
					PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
					personalInfo = personalInfoDao.findByPrimaryKey(personalInfo.getId());
					AddressDao addressDao = AddressDaoFactory.create();
					permaddress = addressDao.findByPrimaryKey(personalInfo.getPermanentAddress());
					//SalaryDetailBean salaryDetailBean[] = null;
					if (candidate.getId() > 0){
						// coded by praneeth.r
						// Commented to use unified
						// getSalary(candidateId:int):Salary method
						// boolean oldRecord = false;
						// Salary salary = null;
						// SalaryDetails salaryDetails2[] = null;
						// salaryDetails2 =
						// salaryDetailsDao.findWhereCandidateIdEquals(candidate.getId());
						// if(salaryDetails2 != null && salaryDetails2.length>0){
						// for (int j = 0; j < salaryDetails2.length; j++) {
						// if(new Integer(salaryDetails2[j].getSalId()) == null)
						// oldRecord = true;
						// }
						// salary =
						// getSalaryBeanFromSalaryDetailsDto(salaryDetails2,
						// oldRecord, candidate.getId());
						// Delegating salary retrieval to SalaryDetailModel
						SalaryDetailModel sdm = new SalaryDetailModel();
						Salary salary = sdm.getSalary(candidate.getId(), false, SalaryDetailModel.TYPE_NORMAL);
						if (salary != null){
							candidateSaveForm.setSalary(new Salary[] { salary });
						}
						/**
						 * Commented to return the salary details according to the
						 * new salary structure...
						 */
						// FinanceInfoModel financeInfoModel = new
						// FinanceInfoModel();
						// salaryDetails2 =
						// financeInfoModel.decrypt(salaryDetails2);
						// salaryDetailBean = new
						// SalaryDetailBean[salaryDetails2.length];
						// for (int j = 0; j < salaryDetails2.length; j++) {
						// SalaryDetailBean salaryBean =
						// DtoToBeanConverter.DtoToBeanConverter(salaryDetails2[j]);
						// salaryDetailBean[j] = salaryBean;
						// }
						candidateSaveForm.setStatusId(candidate.getStatus());
					}
					/**
					 * Get level region department designation
					 */
					if (profileInfo.getLevelId() > 0){
						LevelsDao levelsDao = LevelsDaoFactory.create();
						Divison divison = new Divison();
						DivisonDao divisonDao = DivisonDaoFactory.create();
						/**
						 * level and designation
						 */
						levelid = profileInfo.getLevelId();
						Levels level = levelsDao.findByPrimaryKey(levelid);
						candidateSaveForm.setDesignation(level.getDesignation());
						candidateSaveForm.setLevel(level.getLabel());
						candidateSaveForm.setDesignationId(levelid);
						/**
						 * Division and id
						 */
						divison = divisonDao.findByPrimaryKey(level.getDivisionId());
						if (divison.getParentId() != 0){
							candidateSaveForm.setDivision(divison.getName());
							candidateSaveForm.setDivisionId(divison.getId());
						}
						/**
						 * Region and id
						 */
						Regions region = new Regions();
						RegionsDao regionDao = RegionsDaoFactory.create();
						region = regionDao.findByPrimaryKey(divison.getRegionId());
						candidateSaveForm.setRegion(region.getRegName());
						candidateSaveForm.setRegionId(region.getId());
						region.getCompanyId();
						if (region.getParentId() == 0){
							candidateSaveForm.setRegion(region.getRegName());
							candidateSaveForm.setRegionId(region.getId());
						} else{
							Regions mainregion = regionDao.findByPrimaryKey(region.getParentId());
							candidateSaveForm.setRegion(mainregion.getRegName());
							candidateSaveForm.setRegionId(mainregion.getId());
						}
						/**
						 * Company and id
						 */
						CompanyDao companyDao = CompanyDaoFactory.create();
						Company company = new Company();
						company = companyDao.findByPrimaryKey(region.getCompanyId());
						candidateSaveForm.setCompany(company.getCompanyName());
						candidateSaveForm.setCompanyId(company.getId());
						/**
						 * Department and id
						 */
						if (divison.getParentId() == 0){
							candidateSaveForm.setDepartment(divison.getName());// department
							// name
							candidateSaveForm.setDepartmentId(divison.getId());// department
							// id
						} else{
							Divison department = divisonDao.findByPrimaryKey(divison.getParentId());
							candidateSaveForm.setDepartment(department.getName());// department
							// name
							candidateSaveForm.setDepartmentId(department.getId());// department
							// id
						}
					}// if ends
					candidateSaveForm = DtoToBeanConverter.DtoToBeanConverter(candidateSaveForm, personalInfo, profileInfo, null, permaddress);
					/**
					 * reporting manager name
					 */
					if (profileInfo.getReportingMgr() > 0){
						UsersDao usersDao = UsersDaoFactory.create();
						String fname = "";
						Users reportingMngr = usersDao.findByPrimaryKey(profileInfo.getReportingMgr());
						ProfileInfo repMngr = profiledao.findByPrimaryKey(reportingMngr.getProfileId());
						if (repMngr.getFirstName() != null){
							fname = repMngr.getFirstName();
						}
						if (repMngr.getLastName() != null){
							candidateSaveForm.setReportingMgrName(fname + " " + repMngr.getLastName());
						} else{
							candidateSaveForm.setReportingMgrName(fname);
						}
					}
					/**
					 * hrspoc name name
					 */
					if (profileInfo.getHrSpoc() > 0){
						UsersDao usersDao = UsersDaoFactory.create();
						String fname = "";
						Users hrspoc = usersDao.findByPrimaryKey(profileInfo.getHrSpoc());
						ProfileInfo hrspocProfile = profiledao.findByPrimaryKey(hrspoc.getProfileId());
						if (hrspocProfile.getFirstName() != null){
							fname = hrspocProfile.getFirstName();
						}
						if (hrspocProfile.getLastName() != null){
							candidateSaveForm.setHrSpocName(fname + " " + hrspocProfile.getLastName());
						} else{
							candidateSaveForm.setHrSpocName(fname);
						}
					}
					// candidateSaveForm.setSalaryDetailBean(salaryDetailBean);
					candidateSaveForm.setUserId(null);
					candidateSaveForm.setFieldLabel(null);
					candidateSaveForm.setFields(null);
					// get the commitment details for candidate
					CommitmentsDao commitmentDao = CommitmentsDaoFactory.create();
					Commitments[] commitments = commitmentDao.findWhereCandidateIdEquals(candidateSaveForm.getCandidateId());
					com.dikshatech.beans.CommitmentBean[] bean = new com.dikshatech.beans.CommitmentBean[commitments.length];
					int m = 0;
					for (Commitments c : commitments){
						bean[m] = DtoToBeanConverter.DtoToBeanConverter(c);
						m++;
					}
					candidateSaveForm.setCommitmentbean(bean);
					/**
					 * receive one time payments
					 */
					SalaryInfo salaryInfo[] = SalaryInfoDaoFactory.create().findWhereBasicEquals(candidateSaveForm.getCandidateId());
					if (salaryInfo != null && salaryInfo.length > 0){
						SalaryInfoBean salaryInfoBean = setSalaryInfoBean(salaryInfo[0]);
						
						SalaryInfoBean infoBean[] = new SalaryInfoBean[1];
						infoBean[0] = salaryInfoBean;
						candidateSaveForm.setSalaryInfoBean(infoBean);
					}
					
					//receive candidate's perdiem details
					CandidatePerdiemDetailsDao perdiemDao = CandidatePerdiemDetailsDaoFactory.create();
					String perdiemDetails = perdiemDao.getPerdiemDetails(candidateSaveForm.getCandidateId());
					candidateSaveForm.setCandidatePerdiemDetails(perdiemDetails);
					request.setAttribute("actionForm", candidateSaveForm);
					break;
				case RECEIVEALLCANDIDATE:
					// getting the name from profile table and phone number
					// from
					// personal table
					// for list all candidates on home page Recruitment
					// view
					// Candidatessanju
					Login login = Login.getLogin(request);
					DropDown dropdown = (DropDown) form;
					CandidateDao candidateDao = CandidateDaoFactory.create();
					Candidate[] candStatus = candidateDao.findByDynamicWhere("IS_EMPLOYEE = 0 AND IS_ACTIVE = 0 AND STATUS <> 9 AND PROFILE_ID>0 ",null);
					CandidateCommonBean[] finalCand = new CandidateCommonBean[candStatus.length];
					ProfileInfoDao profileinfodao1 = ProfileInfoDaoFactory.create();
					PersonalInfoDao personalinfodao = PersonalInfoDaoFactory.create();
					CandidateReqDao cReqDao = CandidateReqDaoFactory.create();
					int j = 0;
					for (Candidate can : candStatus){
						if (can.getProfileId() > 0 && can.getPersonalId() > 0){
							PersonalInfo personalinfoDtos = personalinfodao.findByPrimaryKey(can.getPersonalId());
							ProfileInfo profileinfoDtos1 = profileinfodao1.findByPrimaryKey(can.getProfileId());
							CandidateReq[] cReq = cReqDao.findWhereCandidateIdEquals(can.getId());
							Arrays.sort(cReq, new CandidateReq());
							CandidateCommonBean candidateCommonBean = DtoToBeanConverter.DtoToBeanConverter(can, profileinfoDtos1, personalinfoDtos, cReq[0].getStatus());
							candidateCommonBean.setTatId(can.getTatId() + "");
							// check no show enable or not
							if (new Date().compareTo(profileinfoDtos1.getDateOfJoining()) >= 0 && can.getStatus() == 2){
								candidateCommonBean.setNoShow(true);
							}
							if (candidateCommonBean.getStatus() == 1){
								candidateCommonBean.setStatusname("Candidate Created");
							}
							finalCand[j] = candidateCommonBean;
							j++;
						}
					}
				//	dropdown.setDropDown(new FilterData().filter(finalCand, ActionComponents.CANDIDATE.toString(), login));
					dropdown.setDropDown(finalCand);
					request.setAttribute("actionForm", dropdown);
					break;
				case RECEIVECANDIDATECHECKLIST:{
					Candidate candidateForm = (Candidate) form;
					dropdown = new DropDown();
					CandidateChecklistDocsDao checklistDocsDao = CandidateChecklistDocsDaoFactory.create();
					CandidateChecklistStatusDao checkListStatusDao = CandidateChecklistStatusDaoFactory.create();
					CandidateChecklistStatus[] checklists = null;
					try{
						checklists = checkListStatusDao.findWhereCandIdEquals(candidateForm.getCandidateId());
						for (CandidateChecklistStatus eachChecklist : checklists){
							eachChecklist.setChklstDocName(checklistDocsDao.findByPrimaryKey(eachChecklist.getChklstDocId()).getName());
						}
					} catch (CandidateChecklistStatusDaoException ccsdex){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.candidate.checklist.fetch.failed"));
					}
					dropdown.setDropDown(checklists);
					
					request.setAttribute("actionForm", dropdown);
				}
					break;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method takes the salaryDetailsDto array and convert into the Salary
	 * bean Also take care of the existing records to return
	 * 
	 * @param salaryDetails
	 * @param oldRecord
	 * @return
	 */
	public Salary getSalaryBeanFromSalaryDetailsDto(SalaryDetails[] salaryDetails, boolean oldRecord, int candidateId) {
		final String GET_DETAILS_QUERY = "SELECT SC.ID, HEAD, COMPONENT, `VALUE`, VALUE_TYPE, AUTO_CALC, COMPONENT_ORDER, MONTHLY, ANNUAL, SAL_ID " + " FROM SALARY_CONFIG SC JOIN SALARY_DETAILS SD " + " ON SD.SAL_ID = SC.ID " + " WHERE SC.HEAD = ? " + " AND SD.CANDIDATE_ID = ? ";
		final String GET_HEADS_QUERY = "SELECT DISTINCT(HEAD) FROM SALARY_CONFIG";
		float ctc = 0, perDiem = 0;
		Salary salary = new Salary();
		SalaryHead salaryHead = new SalaryHead();
		List<SalaryHead> salaryHeadList = new ArrayList<SalaryHead>();
		SalaryConfigs salaryConfigs;
		List<SalaryConfigs> salaryConfigsList = new ArrayList<SalaryConfigs>();
		int componentValue = 0;
		Connection conn = MyDBConnect.getConnect();
		Statement stmt;
		PreparedStatement pStmt;
		ResultSet res1, res;
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		if (oldRecord == true){
			for (SalaryDetails eachSalaryDetail : salaryDetails){
				salaryConfigs = new SalaryConfigs();
				salaryConfigs.setId(eachSalaryDetail.getId());
				salaryConfigs.setComponentOrder(++componentValue);
				salaryConfigs.setComponent(eachSalaryDetail.getFieldLabel());
				String annualAmount = DesEncrypterDecrypter.getInstance().decrypt(eachSalaryDetail.getAnnual());
				if (annualAmount != null) salaryConfigs.setAnnualAmount(Float.valueOf(annualAmount));
				String monthlyAmount = DesEncrypterDecrypter.getInstance().decrypt(eachSalaryDetail.getMonthly());
				if (monthlyAmount != null) salaryConfigs.setMonthlyAmount(Float.valueOf(monthlyAmount));
				salaryConfigs.setValue(0);
				salaryConfigs.setAutoCalc(0);
				salaryConfigs.setValueType("");
				salaryConfigsList.add(salaryConfigs);
			}
			salaryHead.setSalaryConfigs(salaryConfigsList);
			
			salaryHead.setName("Diksha Salary Stach Up");
			salaryHeadList.add(salaryHead);
			salary.setSalaryHead(salaryHeadList);
		} else{
			int headTotal = 0;
			try{
				stmt = conn.createStatement();
				res1 = stmt.executeQuery(GET_HEADS_QUERY);
				while (res1.next()){
					pStmt = conn.prepareStatement(GET_DETAILS_QUERY);
					pStmt.setString(1, res1.getString("HEAD"));
					pStmt.setInt(2, candidateId);
					res = pStmt.executeQuery();
					while (res.next()){
						salaryConfigs = new SalaryConfigs();
						salaryConfigs.setId(res.getInt("ID"));
						salaryConfigs.setComponentOrder(res.getInt("COMPONENT_ORDER"));
						salaryConfigs.setComponent(res.getString("COMPONENT"));
						float annual = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(res.getString("ANNUAL")));
						salaryConfigs.setAnnualAmount(annual);
						salaryConfigs.setMonthlyAmount(Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(res.getString("MONTHLY"))));
						salaryConfigs.setValue(res.getFloat("VALUE"));
						salaryConfigs.setAutoCalc(res.getInt("AUTO_CALC"));
						salaryConfigs.setValueType(res.getString("VALUE_TYPE"));
						salaryConfigsList.add(salaryConfigs);
						headTotal += annual;
					}
					salaryHead = new SalaryHead();
					salaryHead.setName(res1.getString("HEAD"));
					salaryHead.setSalaryConfigs(salaryConfigsList);
					salaryHead.setAnnualSum(headTotal);
					salaryHead.setMonthlySum(headTotal / 12);
					salaryHeadList.add(salaryHead);
				}
				salary.setSalaryHead(salaryHeadList);
				SalaryDetails[] ctcSalaryDetails = salaryDetailsDao.findByDynamicWhere(" CANDIDATE_ID = ? AND SAL_ID in (SELECT ID FROM SALARY_CONFIG WHERE VALUE_TYPE = '') ", new Object[] { candidateId });
				if (ctcSalaryDetails.length == 2){
					if (ctcSalaryDetails[0].getFieldLabel().indexOf("CTC") != -1) ctc = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(ctcSalaryDetails[0].getAnnual()));
					if (ctcSalaryDetails[1].getFieldLabel().indexOf("PERDIEM") != -1) perDiem = Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(ctcSalaryDetails[0].getAnnual()));
				} else{
					logger.error(" The no of records returned for the query to get ctc and perdiem is not 2 for the candidate " + candidateId);
				}
				salary.setCtc(ctc);
				salary.setPerDiem(perDiem);
				salary.setFixed((float) (ctc * .75));
			} catch (Exception e){
				logger.error("The data could not be queried from DB for the candidate " + candidateId + e.getMessage());
			}
		}
		return salary;
	}

	/*private HashMap<String, Object> putParams(HashMap<String, Object> params, SalaryDetails[] salPerdiem) {
		if (salPerdiem != null && salPerdiem.length > 0){
			if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("Perdiem")){
				params.put("PERDIEM", 1);
			} else if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("LTA")){
				params.put("LTA", 1);
			} else if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("Performance Linked Bonus")){
				params.put("PLB", 1);
			} else if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("Retention Bonus")){
				params.put("RETENTIONBONUS", 1);
			}
		} else{
			if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("Perdiem")){
				params.put("PERDIEM", 0);
			} else if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("LTA")){
				params.put("LTA", 0);
			} else if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("Performance Linked Bonus")){
				params.put("PLB", 0);
			} else if (salPerdiem[0].getFieldLabel().equalsIgnoreCase("Retention Bonus")){
				params.put("RETENTIONBONUS", 0);
			}
		}
		return params;
	}*/
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Boolean flag = true, flag1 = true;
		
		PopulateForms pForms = new PopulateForms();
		CandidateSaveForm candidatesaveform = (CandidateSaveForm) form;
		EmpSerReqMapDao eDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao pChainDao = ProcessChainDaoFactory.create();
		CandidateReqDao cReqDao = CandidateReqDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		PersonalInfoDao personalinfodao = PersonalInfoDaoFactory.create();
		CandidateDao candidateDao = CandidateDaoFactory.create();
		AddressDao addressDao = AddressDaoFactory.create();
		SalaryInfoDao salaryInfoDao = SalaryInfoDaoFactory.create();
		Candidate candidate = new Candidate();
		ProfileInfo profileInfo = pForms.setProfileInfo(candidatesaveform);
		PersonalInfo personalInfo = pForms.setPersonalInfo(candidatesaveform);
		//SalaryDetails salaryDetails = pForms.setSalaryDetails(candidatesaveform);
		SalaryInfo salaryInfo = pForms.setSalaryInfo(candidatesaveform);
		Address allAddress[] = new Address[2];
		allAddress[0] = pForms.setPermAddress(candidatesaveform);
		allAddress[1] = null;// no current address for candidate
		//SalaryDetails Details[] = null;
		ProfileInfoPk profileInfoPk = new ProfileInfoPk();
		AddressPk addressPk = new AddressPk();
		PersonalInfoPk personalInfoPk = new PersonalInfoPk();
		CandidatePk candidatePk = new CandidatePk();
		CandidateReqPk candidateReqPk = new CandidateReqPk();
		EmpSerReqMapPk eMapPk = new EmpSerReqMapPk();
		SalaryInfoPk salaryInfoPk = new SalaryInfoPk();
		/**
		 * Save Profile info
		 */
		if (profileInfo != null){
			profileInfo.setId(profileInfo.getId() != 0 ? 0 : 0);
			try{
				// profile info
				if (profileInfo != null) profileInfoPk = profileInfoDao.insert(profileInfo);
				profileInfo.setId(profileInfoPk.getId());
			} catch (Exception e){
				flag = false;
				logger.info("Failed to save Profile Info");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
				e.printStackTrace();
			}
		}
		/************************** Profile Save Ends **********************/
		/**
		 * Save Address info
		 */
		int j = 0;
		for (Address perAddress : allAddress){
			if (perAddress != null){
				perAddress.setId(perAddress.getId() != 0 ? 0 : 0);
				try{
					// address
					if (perAddress != null) addressPk = addressDao.insert(perAddress);
					perAddress.setId(addressPk.getId());
				} catch (Exception e){
					flag = false;
					logger.info("Failed to save Address Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					e.printStackTrace();
				}
				if (j == 0){
					personalInfo.setPermanentAddress(perAddress.getId());// Permanent
				}
				if (j == 1){
					personalInfo.setCurrentAddress(perAddress.getId());// Current
				}
				j++;
			}
		}// for ends
		/**
		 * Save Personal Info
		 */
		if (personalInfo != null){
			personalInfo.setId(personalInfo.getId() != 0 ? 0 : 0);
			try{
				if (flag == true){
					personalInfoPk = personalinfodao.insert(personalInfo);
					personalInfo.setId(personalInfoPk.getId());
				}
			} catch (Exception e){
				flag1 = false;
				logger.info("Failed to save Profile Info");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
				e.printStackTrace();
			}
		}
		/************************** Personal Save Ends **********************/
		if (profileInfo.getId() > 0){
			candidate.setProfileId(profileInfo.getId());
		}
		if (personalInfo.getId() > 0){
			candidate.setPersonalId(personalInfo.getId());
		}
		// candidate.setStatus(1);
		try{
			if (flag == true && flag1 == true){
				/**
				 * Login details for TAT id
				 */
				Login login = Login.getLogin(request);
				candidate.setTatId(login.getUserId());
				candidate.setStatus(1);
				candidate.setId(candidate.getId() != 0 ? 0 : 0);
				candidatePk = candidateDao.insert(candidate);
				candidate.setId(candidatePk.getId());
				Set<com.dikshatech.beans.Roles> roles = login.getUserLogin().getRoles();
				com.dikshatech.beans.Roles roles2 = (com.dikshatech.beans.Roles) roles.toArray()[0];
				ProcessChain[] pChain = pChainDao.findByDynamicSelect("SELECT ID, PROC_NAME, APPROVAL_CHAIN, NOTIFICATION, HANDLER, FEATURE_ID FROM PROCESS_CHAIN WHERE ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION WHERE ROLE_ID = ? AND MODULE_ID = 15)", new Object[] { new Integer(roles2.getRoleId()) });
				/**
				 * insert record into empSerReqMap table
				 */
				RegionsDao regionsDao = RegionsDaoFactory.create();
				Regions regions = regionsDao.findByPrimaryKey(login.getUserLogin().getRegionId());
				EmpSerReqMap eMap = new EmpSerReqMap();
				eMap.setSubDate(new Date());
				eMap.setReqId(regions.getRefAbbreviation() + "-CAD-" + candidate.getId());
				eMap.setReqTypeId(7);
				eMap.setRegionId(regions.getId());
				eMap.setRequestorId(login.getUserId());
				eMap.setProcessChainId(pChain[0].getId());
				eMap.setSiblings("");
				eMap.setNotify(pChain[0].getNotification());
				eMapPk = eDao.insert(eMap);
				CandidateReq cReq = new CandidateReq();
				cReq.setAssignedTo(login.getUserId());
				cReq.setRaisedBy(login.getUserId());
				cReq.setStatus(Status.NEW);
				cReq.setCandidateId(candidate.getId());
				cReq.setEsrqmId(eMapPk.getId());
				cReq.setCycle(1);
				candidateReqPk = cReqDao.insert(cReq);
				
			// *********** Saving Salary Details *****************//	
				
				Salary salary = null;
				if (candidate.getId() > 0){
					// New code added to save the salary details according to
					// the salary restructure
					float ctc = Float.valueOf(candidatesaveform.getCtcAmount());
					//float perDiem = Float.valueOf(candidatesaveform.getPerdiemAmount());
					float perDiem,perDiemOffered;
				//	float fixedPerdiem;
					float retenBonus;
					if(candidatesaveform.getPerdiemAmount()!=null&&!(candidatesaveform.getPerdiemAmount().equalsIgnoreCase(""))){
						perDiem= Float.valueOf(candidatesaveform.getPerdiemAmount());
					}else{
						perDiem=0;
					}
					
					
					
					
		// If fixed perdiem is available then it is saved in a temp table			
					
				/*	if(candidatesaveform.getFixedPerdiemAmount()!=null && !(candidatesaveform.getFixedPerdiemAmount().equalsIgnoreCase(""))){
						fixedPerdiem= Float.valueOf(candidatesaveform.getFixedPerdiemAmount());
						saveCandidateFixPerdiem(fixedPerdiem,candidate.getId());
					}else{
						fixedPerdiem=0;
					}
					*/
					if(candidatesaveform.getRetentionBonus()!=null&&!(candidatesaveform.getRetentionBonus().equalsIgnoreCase(""))){
						retenBonus= Float.valueOf(candidatesaveform.getRetentionBonus());
					}else{
						retenBonus=0;
					}
					String plb = candidatesaveform.getPlb();
				//	salary = new FinanceInfoModel().getCalculatedSalary(plb, ctc, perDiem, true,fixedPerdiem,retenBonus);
					salary = new FinanceInfoModel().getCalculatedSalary1(plb, ctc, perDiem, true,retenBonus);
					int userId = 0;
					int candidateId = candidate.getId();
					SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
					result = salaryDetailModel.saveSalaryDetails(Float.valueOf(plb), salary, userId, candidateId, SalaryDetailModel.TYPE_NORMAL);
					// New code added to save the salary details according to
					// the salary restructure Ends Here
					// if (salaryDetails.getFields() != null){
					// salaryDetails.setCandidateId(candidate.getId());
					// SalaryDetailModel salaryDetailModel = new
					// SalaryDetailModel();
					// result = salaryDetailModel.save(salaryDetails, request);
					// }
				}
				candidatesaveform.setCandidateId(candidate.getId());
				if (result.getActionMessages() == null || result.getActionMessages().isEmpty()){
					// Details = (SalaryDetails[])
					// request.getAttribute("salaryDetails");
					// SalaryDetailBean salbean[] = new
					// SalaryDetailBean[Details.length];
					// int i = 0;
					// for (SalaryDetails salary : Details) {
					// salbean[i] = DtoToBeanConverter
					// .DtoToBeanConverter(salary);
					// i++;
					// }
					// candidatesaveform.setSalaryDetailBean(salbean);
					candidatesaveform.setSalary(new Salary[] { salary });
					candidatesaveform.setId(candidate.getId());
					candidatesaveform.setPermenantAddress(personalInfo.getPermanentAddress());
					request.setAttribute("actionForm", candidatesaveform);
					// SAVE COMMITMENTS
					CommitmentsDao commitmentsDao = CommitmentsDaoFactory.create();
					CommitmentsPk pk = new CommitmentsPk();
					String comments[] = null;
					int p = 0;
					// format of the string "comment~=~date"
					if (candidatesaveform.getCommitments() != null && candidatesaveform.getCommitments().length > 0){
						CommitmentBean[] c = new CommitmentBean[candidatesaveform.getCommitments().length];
						for (String s : candidatesaveform.getCommitments()){
							Commitments commitments = new Commitments();
							comments = s.split("~=~");
							
							
							
							if (comments.length > 0){
								
								commitments.setComments(comments[0]);
								
								if (comments.length > 1) commitments.setDatePicker(PortalUtility.StringToDate(comments[1]));
							
								commitments.setCandidateId(candidatePk.getId());
								commitments.setUserIdEmp(0);
								pk = commitmentsDao.insert(commitments);
								commitments.setId(pk.getId());
								c[p] = DtoToBeanConverter.DtoToBeanConverter(commitments);
								p++;
							}
						}
						candidatesaveform.setCommitmentbean(c);
					}
					
					
					//save candidate perdiem details  format followed     locationName1~=~perdiemDetails~;~locationNAme2~=~perdiemDetails2
					CandidatePerdiemDetailsDao perdiemDetailsDao = CandidatePerdiemDetailsDaoFactory.create();
					String[] candidatePerdiemDetails = candidatesaveform.getCandidatePerdiemDetails().split("~;~");
					for (String eachPerdiemDetail : candidatePerdiemDetails){
						String[] locationPerdiemDetails = eachPerdiemDetail.split("~=~");
						CandidatePerdiemDetails detailsDto = new CandidatePerdiemDetails();
						detailsDto.setCandidateId(candidate.getId());
						detailsDto.setLocation(locationPerdiemDetails[0]);
						detailsDto.setPerdiem(locationPerdiemDetails[1]);
						perdiemDetailsDao.insert(detailsDto);
					}
					/**
					 * Save one time payments section
					 */
					if (candidatesaveform.getRelocationBonus() != null && !candidatesaveform.getRelocationBonus().equalsIgnoreCase("")){
						salaryInfo.setRelocationBonus(candidatesaveform.getRelocationBonus());
						salaryInfo.setRelocationCity(candidatesaveform.getRelocationCity());
					}else salaryInfo.setRelocationBonus("");
					
					if (candidatesaveform.getJoiningBonusString() != null){
						salaryInfo.setJoiningBonusString(candidatesaveform.getJoiningBonusString());
						salaryInfo.setJoiningBonusAmount(candidatesaveform.getJoiningBonusAmount() == null||candidatesaveform.getJoiningBonusAmount() =="" ?"": candidatesaveform.getJoiningBonusAmount());
						salaryInfo.setPaymentTerms(candidatesaveform.getPaymentTerms() == null||candidatesaveform.getPaymentTerms() == "" ? "" : candidatesaveform.getPaymentTerms());
					} 
					
					if (candidatesaveform.getPerdiemString() != null){
						salaryInfo.setPerdiemString(candidatesaveform.getPerdiemString().equalsIgnoreCase("") ? "" : candidatesaveform.getPerdiemString());
					}
					
					if (candidatesaveform.getRetentionBonus() != null && !candidatesaveform.getRetentionBonus().equalsIgnoreCase("")){
						salaryInfo.setRetentionBonus(candidatesaveform.getRetentionBonus());
					}else salaryInfo.setRetentionBonus("");	
					
					if(candidatesaveform.getPerdiemOffered()!=null){
						salaryInfo.setPerdiemOffered(candidatesaveform.getPerdiemOffered().equalsIgnoreCase("") ? "" : candidatesaveform.getPerdiemOffered());
					}else{
						salaryInfo.setPerdiemOffered("");
					}
					
					if (candidatesaveform.getRetentionInstallments() != null && !candidatesaveform.getRetentionInstallments().equalsIgnoreCase("")){
						salaryInfo.setRetentionInstallments(candidatesaveform.getRetentionInstallments());
					}else salaryInfo.setRetentionInstallments("");	
					
				
					
					salaryInfo.setBasic(candidatePk.getId());// Basic
																// -->candidate
																// id
					salaryInfo.setHra(0);// HRA-->user Id primary key of user
											// table 0
					salaryInfoPk = salaryInfoDao.insert(salaryInfo);
					SalaryInfoBean salaryInfoBean = setSalaryInfoBean(salaryInfo);
					salaryInfoBean.setId(salaryInfoPk.getId());
					SalaryInfoBean infoBean[] = new SalaryInfoBean[1];
					infoBean[0] = salaryInfoBean;
					candidatesaveform.setSalaryInfoBean(infoBean);
					
					request.setAttribute("actionForm", candidatesaveform);
				} else{
					/**
					 * delete from profile,personal,candidate request,emp
					 * service request,candidate,
					 */
					profileInfoDao.delete(profileInfoPk);
					addressPk.setId(personalInfo.getPermanentAddress());
					addressDao.delete(addressPk);
					addressPk.setId(personalInfo.getCurrentAddress());
					addressDao.delete(addressPk);
					personalinfodao.delete(personalInfoPk);
					cReqDao.delete(candidateReqPk);
					eDao.delete(eMapPk);
					if (salaryInfoPk.getId() > 0) salaryInfoDao.delete(salaryInfoPk);
					candidateDao.delete(candidatePk);
					return result;
					
				}
			}
		} catch (Exception e){
			logger.info("Failed to save Candidate Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		MailGenerator mailGenerator = new MailGenerator();
		ActionResult result = new ActionResult();
		boolean approvedSalary = true, salaryinfo = true;
		PopulateForms pForms = new PopulateForms();
		Candidate candidate = new Candidate();
		CandidatePk candidatePk = new CandidatePk();
		CandidateDao candidateDao = CandidateDaoFactory.create();
		CandidateReqDao cReqDao = CandidateReqDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		AddressDao addressDao = AddressDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
		LoginDao loginDao = LoginDaoFactory.create();
		EmpSerReqMapDao eDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao pDao = ProcessChainDaoFactory.create();
		PortalMail pMail = new PortalMail();
		InboxModel inboxModel = new InboxModel();
		//SalaryDetails Details[] = null;
		String bodyText = "";
		Inbox inbox = null;
		CandidateReq cReq = null;
		CandidateReq candidateReqPrev = null;
		int nextCycle = 1;
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		RegionsDao regionsDao = RegionsDaoFactory.create();
		InboxDao inboxDao = null;
		try{
			switch (UpdateTypes.getValue(form.getuType())) {
			
			
				case UPDATECANDIDATE:
					candidate = (Candidate) form;
					/**
					 * Below check if request is for disable candidate
					 */
					if (candidate.getIsActive() == 1){
						Candidate disablecandidate = candidateDao.findByPrimaryKey(candidate.getId());
						candidatePk.setId(disablecandidate.getId());
						disablecandidate.setIsActive(candidate.getIsActive());
						candidateDao.update(candidatePk, disablecandidate);
					} else{
						candidatePk.setId(candidate.getId());
						candidateDao.update(candidatePk, candidate);
					}
					break;
				case UPDATE:
					CandidateSaveForm candidatesaveform = (CandidateSaveForm) form;
 					ProfileInfo profileInfo = pForms.setProfileInfo(candidatesaveform);
					PersonalInfo personalInfo = pForms.setPersonalInfo(candidatesaveform);
					SalaryDetails salaryDetails = pForms.setSalaryDetails(candidatesaveform);
					SalaryInfo salaryInfo = pForms.setSalaryInfo(candidatesaveform);
					Address allAddress[] = new Address[2];
					allAddress[0] = pForms.setPermAddress(candidatesaveform);
					allAddress[1] = pForms.setcurrentAddress(candidatesaveform);
					candidate.setId(candidatesaveform.getId());
					candidatePk.setId(candidatesaveform.getId());
					candidate = candidateDao.findByPrimaryKey(candidate.getId());
					salaryDetails.setCandidateId(candidate.getId());
					if (candidate.getProfileId() > 0){
						profileInfo.setId(candidate.getProfileId());
					}
					if (candidate.getPersonalId() > 0){
						personalInfo.setId(candidate.getPersonalId());
						personalInfo.setCandidateId(candidate.getId());
					}
					ProfileInfoModel profileInfoModel = new ProfileInfoModel();
					profileInfo.setCandidateId(candidate.getId());
					profileInfoModel.update(profileInfo, request);
					PersonalInfoModel personalInfoModel = new PersonalInfoModel();
					PersonalInfo personal = personalInfoDao.findByPrimaryKey(personalInfo.getId());
					personalInfoModel.update(personalInfo, request);
					AddressModel addressModel = new AddressModel();
					AddressPk addressPk = new AddressPk();
					int count = 0;
					for (Address address : allAddress){
						if (address != null) if (count == 0){
							Address newadd = addressDao.findByPrimaryKey(personal.getPermanentAddress());
							address.setId(newadd.getId());
							addressModel.update(address, request);
						} else{
							if (personalInfo.getCurrentAddress() > 0){
								Address newadd = addressDao.findByPrimaryKey(personal.getCurrentAddress());
								address.setId(newadd.getId());
								addressModel.update(address, request);
							} else{
								address.setId(address.getId() != 0 ? 0 : 0);
								addressPk = addressDao.insert(address);
								personalInfo.setCurrentAddress(addressPk.getId());
								address.setId(addressPk.getId());
							}
						}
						count++;
					}
					// need this for proper working of address fields
					personalInfo.setuType("candiatePersonal");
					personalInfoModel.update(personalInfo, request);
					// Part of the code commented to update the salary details
					// according to salary restructure
					// if (salaryDetails.getFields() != null) {
					// salaryDetails.setuType("UPDATE");
					// SalaryDetailModel salaryDetailModel = new
					// SalaryDetailModel();
					// result = salaryDetailModel.update(salaryDetails, request);
					// }
					// Code to update the salarydetails for the
					// salary details sent from the user
					if (candidate != null){
						SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
						float ctc = Float.valueOf(candidatesaveform.getCtcAmount());
						float perDiem;
					//	float fixedPerDiem;
						float retenBonus;
						if(candidatesaveform.getPerdiemAmount()!=null&&!(candidatesaveform.getPerdiemAmount().equalsIgnoreCase(""))){
							perDiem= Float.valueOf(candidatesaveform.getPerdiemAmount());
						}else{
							perDiem=0;
						}
						
						
						
					/*	if(candidatesaveform.getFixedPerdiemAmount()!=null&&!(candidatesaveform.getFixedPerdiemAmount().equalsIgnoreCase(""))){
							fixedPerDiem= Float.valueOf(candidatesaveform.getFixedPerdiemAmount());
							saveCandidateFixPerdiem(fixedPerDiem,candidate.getId());
						}else{
							fixedPerDiem=0;
						}*/
						
						if(candidatesaveform.getRetentionBonus()!=null&&!(candidatesaveform.getRetentionBonus().equalsIgnoreCase(""))){
							retenBonus= Float.valueOf(candidatesaveform.getRetentionBonus());
						}else{
							retenBonus=0;
						}
						String plb = candidatesaveform.getPlb();
				//		Salary salary = new FinanceInfoModel().getCalculatedSalary(plb, ctc, perDiem, true,fixedPerDiem,retenBonus);
						Salary salary = new FinanceInfoModel().getCalculatedSalary1(plb, ctc, perDiem, true,retenBonus);
						result = salaryDetailModel.saveSalaryDetails(Float.valueOf(plb), salary, 0, candidate.getId(), SalaryDetailModel.TYPE_NORMAL);
						if (result != null && !result.getActionMessages().isEmpty()){
							logger.error("The records for the candidate " + candidate.getCandidateId() + " was not saved properly due to error in saving the record to table SALARY_DETAILS");
						}
					}
					candidateDao.update(candidatePk, candidate);
					if (result.getActionMessages().isEmpty()){
						// Details = (SalaryDetails[])
						// request.getAttribute("salaryDetails");
						// SalaryDetailBean salbean[] = new
						// SalaryDetailBean[Details.length];
						// int i = 0;
						// for (SalaryDetails salary : Details) {
						// salbean[i] =
						// DtoToBeanConverter.DtoToBeanConverter(salary);
						// i++;
						// }
						// candidatesaveform.setSalaryDetailBean(salbean);
						candidatesaveform.setId(candidate.getId());
						// updating commitment
						// format of the string "id:comment~=~date"
						CommitmentModel obj = new CommitmentModel();
						CommitmentBean[] c = obj.update(candidatesaveform, request);
						candidatesaveform.setCommitmentbean(c);
						/**
						 * update one time payment section delete old details and
						 * insert new
						 */
						SalaryInfoDao salaryInfoDao = SalaryInfoDaoFactory.create();
						SalaryInfo salaryInfoOld[] = salaryInfoDao.findWhereBasicEquals(candidate.getId());
						if (salaryInfoOld != null && salaryInfoOld.length > 0){
							salaryInfoDao.delete((new SalaryInfoPk(salaryInfoOld[0].getId())));
						}
						salaryInfo.setBasic(candidatePk.getId());
						SalaryInfoPk salaryInfoPk = salaryInfoDao.insert(salaryInfo);
						SalaryInfoBean salaryInfoBean = setSalaryInfoBean(salaryInfo);
		
						salaryInfoBean.setId(salaryInfoPk.getId());
						SalaryInfoBean infoBean[] = new SalaryInfoBean[1];
						infoBean[0] = salaryInfoBean;
						candidatesaveform.setSalaryInfoBean(infoBean);
				//update candidate perdiem details  format followed     locationName1~=~perdiemDetails~;~locationNAme2~=~perdiemDetails2
						CandidatePerdiemDetailsDao perdiemDetailsDao = CandidatePerdiemDetailsDaoFactory.create();
						String[] candidatePerdiemDetails = candidatesaveform.getCandidatePerdiemDetails().split("~;~");
						if(candidatePerdiemDetails==null||candidatePerdiemDetails[0].equalsIgnoreCase("")){
							CandidatePerdiemDetails[] candPer=perdiemDetailsDao.findWhereCandidateIdEquals(candidate.getId());
							if(candPer!=null){
							for(CandidatePerdiemDetails eachcandPer:candPer){
							CandidatePerdiemDetailsPk pk=	new CandidatePerdiemDetailsPk();
							pk.setId(eachcandPer.getId());
							perdiemDetailsDao.delete(pk);}
							}
						}
						else{
							
							CandidatePerdiemDetails[] existingCandidatePerdiemDetails = perdiemDetailsDao.findWhereCandidateIdEquals(candidate.getId());
							    if(existingCandidatePerdiemDetails!=null && existingCandidatePerdiemDetails.length>0 && existingCandidatePerdiemDetails[0]!=null){
								for(CandidatePerdiemDetails eachcandPer:existingCandidatePerdiemDetails){
									CandidatePerdiemDetailsPk pk=	new CandidatePerdiemDetailsPk();
									pk.setId(eachcandPer.getId());
									perdiemDetailsDao.delete(pk);	
							                                     }
							                            }
							     for(String eachCandPer:candidatePerdiemDetails){
								        String[] temp1=eachCandPer.split(",");
								        String[] temp=temp1[0].split("~=~");
							        CandidatePerdiemDetails eachCand=new CandidatePerdiemDetails();
							        	eachCand.setCandidateId(candidate.getId());
							        	eachCand.setLocation(temp[0]);
							        	eachCand.setPerdiem(temp[1]);
							        	perdiemDetailsDao.insert(eachCand);
							        }
							}
						
						
					/*	CandidatePerdiemDetails[] existingCandidatePerdiemDetails = perdiemDetailsDao.findWhereCandidateIdEquals(candidate.getId());
						if(existingCandidatePerdiemDetails!=null && existingCandidatePerdiemDetails.length>0){
						if (existingCandidatePerdiemDetails.length == candidatePerdiemDetails.length){
							int i = 0;
							for (String eachPerdiemDetail : candidatePerdiemDetails){
								String[] locationPerdiemDetails = eachPerdiemDetail.split("~=~");
								existingCandidatePerdiemDetails[i].setCandidateId(candidate.getId());
								if(locationPerdiemDetails[0].equals("")||locationPerdiemDetails[1].equals("")){
								}else{
								existingCandidatePerdiemDetails[i].setLocation(locationPerdiemDetails[0]);
								existingCandidatePerdiemDetails[i].setPerdiem(locationPerdiemDetails[1]);}
								perdiemDetailsDao.update(new CandidatePerdiemDetailsPk(existingCandidatePerdiemDetails[i].getId()), existingCandidatePerdiemDetails[i]);
								i++;
							}
						} else if (existingCandidatePerdiemDetails.length > candidatePerdiemDetails.length){
							int i = 0;
							for (String eachPerdiemDetail : candidatePerdiemDetails){
								String[] locationPerdiemDetails = eachPerdiemDetail.split("~=~");
								existingCandidatePerdiemDetails[i].setCandidateId(candidate.getId());
								if(locationPerdiemDetails[0].equals("")||locationPerdiemDetails[1].equals("")){
								}else{
								existingCandidatePerdiemDetails[i].setLocation(locationPerdiemDetails[0]);
								existingCandidatePerdiemDetails[i].setPerdiem(locationPerdiemDetails[1]);}
								perdiemDetailsDao.update(new CandidatePerdiemDetailsPk(existingCandidatePerdiemDetails[i].getId()), existingCandidatePerdiemDetails[i]);
								i++;
							}
							while (i < existingCandidatePerdiemDetails.length){
								perdiemDetailsDao.delete(new CandidatePerdiemDetailsPk(existingCandidatePerdiemDetails[i].getId()));
								i++;
							}
						} else{
						
							int i = 0;
							for (CandidatePerdiemDetails eachPerdiemDetail : existingCandidatePerdiemDetails){
								String[] locationPerdiemDetails = candidatePerdiemDetails[i].split("~=~");
								existingCandidatePerdiemDetails[i].setCandidateId(candidate.getId());
								if(locationPerdiemDetails[0].equals("")||locationPerdiemDetails[1].equals("")){}
								else{
								eachPerdiemDetail.setLocation(locationPerdiemDetails[0]);
								eachPerdiemDetail.setPerdiem(locationPerdiemDetails[1]);}
								perdiemDetailsDao.update(new CandidatePerdiemDetailsPk(eachPerdiemDetail.getId()), eachPerdiemDetail);
								i++;
							}
							while (i < candidatePerdiemDetails.length){
								CandidatePerdiemDetails detailsDto = new CandidatePerdiemDetails();
								String[] locationPerdiemDetails = candidatePerdiemDetails[i].split("~=~");
								detailsDto.setCandidateId(candidate.getId());
								if(locationPerdiemDetails[0].equals("")||locationPerdiemDetails[1].equals("")){}
								else{
								detailsDto.setLocation(locationPerdiemDetails[0]);
								detailsDto.setPerdiem(locationPerdiemDetails[1]);
								}
								perdiemDetailsDao.insert(detailsDto);
								i++;
							}
							
							}
						}else if(candidatePerdiemDetails!=null && candidatePerdiemDetails.length>0 && candidatePerdiemDetails[0]!="") {
							CandidatePerdiemDetails detailsDto = new CandidatePerdiemDetails();
							String[] perdiemDetail=null;
							for(String singlePerdiemDetails:candidatePerdiemDetails){
								perdiemDetail=singlePerdiemDetails.split("~=~");
							detailsDto.setCandidateId(candidate.getId());
							detailsDto.setLocation(perdiemDetail[0]);
							detailsDto.setPerdiem(perdiemDetail[1]);
							perdiemDetailsDao.insert(detailsDto);
							}
						}
						*/
						
						
						request.setAttribute("actionForm", candidatesaveform);
					} else{
						return result;
					}
					break;
				case SENDOFFERLETTER:
					Login login = Login.getLogin(request);
					candidate = (Candidate) form;
					candidatePk.setId(candidate.getId());
					CandidateReq cReq1 = cReqDao.findWhereCandidateIdEquals(candidatePk.getId())[0];
					//SalaryDetailsDao salaryDetailsDao1 = SalaryDetailsDaoFactory.create();
					//CommitmentsDao commitmentDao = CommitmentsDaoFactory.create();
					List<String> allReceipientcCMailId = new ArrayList<String>();
					inboxDao = InboxDaoFactory.create();
					//InetAddress addr = InetAddress.getLocalHost();
					//String serverId = addr.getHostAddress();
					if (candidate.isPreviewOfferLetter()){
						candidate = candidateDao.findByPrimaryKey(candidate.getId());
						ProfileInfo profileInfo2 = profileInfoDao.findByPrimaryKey(candidate.getProfileId());
						//PersonalInfo personalInfo2 = personalInfoDao.findByPrimaryKey(candidate.getPersonalId());
						String tempFileName1 = generateFName(candidate.getId(), PortalData.OFF_LETTER, ExportType.pdf);
						JGenerator jGenerator = new JGenerator();
						HashMap<String, Object> params2 = getSalaryReportParams(candidate.getId(), candidate.getProApprovalId() > 0 ? true : false);
						String template1 = getTemplate(profileInfo2.getLevelId(), false);
					if (jGenerator.generateFile(JGenerator.CANDIDATE, tempFileName1, template1, params2)){
						candidate.setOfferLetter(tempFileName1);
					}
						candidatePk.setId(candidate.getId());
						candidateDao.update(candidatePk, candidate);
					} else{
						// int port = request.getServerPort();
						// logger.info("IP==" + serverId + " PORT==" + port);
						candidatePk.setId(candidate.getId());
						candidate = candidateDao.findByPrimaryKey(candidate.getId());
						ProfileInfo profileInfo2 = profileInfoDao.findByPrimaryKey(candidate.getProfileId());
						PersonalInfo personalInfo2 = personalInfoDao.findByPrimaryKey(candidate.getPersonalId());
						/**
						 * This piece of code is used to generate UUID for candidate
						 * accept/reject of offer
						 */
						UUID uuid = UUID.randomUUID();
						candidate.setUuid(uuid.toString());
						String acceptLink = "validate?uuid=" + candidate.getUuid() + "&cId=" + candidate.getId() + "&sId=2";
						String rejectLink = "pages/aboutDiksha.jsp?uuid=" + candidate.getUuid() + "&cId=" + candidate.getId() + "&sId=3";
						Object[] sqlParams = { cReq1.getEsrqmId(), cReq1.getCandidateId() };
						CandidateReq[] alreadyOffered = cReqDao.findByDynamicWhere("ESRQM_ID = ? AND CANDIDATE_ID = ? AND ACTION_TAKEN IS NOT NULL ORDER BY CREATEDATETIME DESC", sqlParams);
						if (alreadyOffered != null && alreadyOffered.length > 0){
							candidate.setStatus(6);// set status to Re-Offered
						} else{
							candidate.setStatus(5);// set status to offer sent
						}
						/**
						 * check if offer is not generated then generate new offer
						 * letter
						 */
						/*String tempFileName = generateFName(candidate.getId(),
								PortalData.OFF_LETTER, ExportType.pdf);
						JGenerator jGenerator = new JGenerator();
						HashMap<String, Object> params1 = new HashMap<String, Object>();
						params1.put("CANDIDATE_ID", candidate.getId());

						SalaryDetails[] salPerdiem = salaryDetailsDao1
								.findByDynamicWhere(
										"CANDIDATE_ID=? AND FIELDTYPE=?",
										new Object[] { candidate.getId(), "2_1" });
						params1.put("PERDIEM", salPerdiem.length == 0 ? 0 : 1);
						SalaryDetails[] salLTA = salaryDetailsDao1
								.findByDynamicWhere(
										"CANDIDATE_ID=? AND FIELDTYPE=?",
										new Object[] { candidate.getId(), "2_2" });
						params1.put("LTA", salLTA.length == 0 ? 0 : 1);
						SalaryDetails[] salPLB = salaryDetailsDao1
								.findByDynamicWhere(
										"CANDIDATE_ID=? AND FIELDTYPE=?",
										new Object[] { candidate.getId(), "2_3" });
						params1.put("PLB", salPLB.length == 0 ? 0 : 1);
						SalaryDetails[] salRetentionBonus = salaryDetailsDao1
								.findByDynamicWhere(
										"CANDIDATE_ID=? AND FIELDTYPE=?",
										new Object[] { candidate.getId(), "2_4" });
						params1.put("RETENTIONBONUS",
								salRetentionBonus.length == 0 ? 0 : 1);

						SalaryInfoDao salaryInfoDao = SalaryInfoDaoFactory.create();
						SalaryInfo salaryInfo1[] = salaryInfoDao
								.findByDynamicWhere("BASIC=? ",
										new Object[] { candidate.getId() });
						if (salaryInfo1 != null && salaryInfo1.length > 0) {
							if (!(salaryInfo1[0].getRelocationBonus() != null && salaryInfo1[0]
									.getRelocationBonus().equalsIgnoreCase(""))) {
								params1.put("RELOCATIONALLOWANCE", 1);
							} else {
								params1.put("RELOCATIONALLOWANCE", 0);
							}
							;
							if (!(salaryInfo1[0].getJoiningBonusString() != null && salaryInfo1[0]
									.getJoiningBonusString().equalsIgnoreCase(""))) {
								params1.put("JOININGBONUS", 1);
							} else {
								params1.put("JOININGBONUS", 0);
							}
							;
						}
						Integer param = 0;
						if (salPerdiem.length > 0) {
							param++;
						}
						if (salPLB.length > 0) {
							param++;
						}
						if (salRetentionBonus.length > 0) {
							param++;
						}
						if (!(salaryInfo1[0].getRelocationBonus() != null && salaryInfo1[0]
								.getRelocationBonus().equalsIgnoreCase(""))) {
							param++;
						} else if (!(salaryInfo1[0].getJoiningBonusString() != null && salaryInfo1[0]
								.getJoiningBonusString().equalsIgnoreCase(""))) {
							param++;
						}
						for (int i = 5; i < 12; i++) {
							// params1.put("param"+i,++param);
						}
						params1.put("P", 100);
						// params1 = putParams(params1, salPerdiem);
						// params1 = putParams(params1, salLTA);
						// params1 = putParams(params1, salPLB);
						// params1 = putParams(params1, salRetentionBonus);

						Commitments[] commitment = commitmentDao
								.findWhereCandidateIdEquals(candidate.getId());
						if (commitment.length > 0) {
							params1.put("COMMITMENTS", 1);
						} else {
							params1.put("COMMITMENTS", 0);
						}
						String template = getTemplate(profileInfo2.getLevelId(),
								false);

						if (jGenerator.generateFile(JGenerator.CANDIDATE,
								tempFileName, template, params1)) {
							candidate.setOfferLetter(tempFileName);
						}*/
						String tempFileName1 = generateFName(candidate.getId(), PortalData.OFF_LETTER, ExportType.pdf);
						candidate.setOfferLetter(tempFileName1);
						candidate.setDateOfOffer(new Date());
						candidateDao.update(candidatePk, candidate);
						cReq = new CandidateReq();
						cReq.setAssignedTo(login.getUserId());
						cReq.setRaisedBy(cReq1.getRaisedBy());
						if (candidate.getStatus() == 6){
							cReq.setStatus("Re-Offered");
							nextCycle = alreadyOffered[0].getCycle() + 1;
						} else{
							cReq.setStatus("Offer Sent");
						}
						// candidates level
						Levels levelsCandidate = LevelsDaoFactory.create().findByPrimaryKey(profileInfo2.getLevelId());
						ProfileInfo tat_pInfo = profileInfoDao.findWhereIdEquals(usersDao.findByPrimaryKey(login.getUserId()).getProfileId())[0];
						/**
						 * Code for send email for candidate and CC to TAT
						 */
						
						
						
						/**
						 * Code for send email for candidate 
						 */
						
					//	Users candRMuser = usersDao.findByPrimaryKey(profileInfo2.getReportingMgr());
					//	ProfileInfo candRMuserprofileInfo = profileInfoDao.findByPrimaryKey(candRMuser.getProfileId());
					// allReceipientcCMailId.add(candRMuserprofileInfo.getOfficalEmailId());
					//	allReceipientcCMailId.add(tat_pInfo.getOfficalEmailId());
						pMail.setLeaveType(levelsCandidate.getLabel());
						pMail.setAcceptOfferLink(acceptLink);
						pMail.setRejectOfferLink(rejectLink);
						pMail.setServerId(request.getRequestURL().toString().replaceAll(request.getServletPath(), ""));
						// pMail.setPort(port);
						
						pMail.setMailSubject("Congratulations! Employment Offer Details from Diksha Technologies");
						pMail.setEmpFname(tat_pInfo.getFirstName()+""+tat_pInfo.getLastName());
						pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, profileInfo2, personalInfo2, null, true);
						pMail.setTemplateName(MailSettings.CANDIDATE_OFFER_PLINK);
				//		pMail.setAllReceipientcCMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
						mailGenerator.invoker(pMail);
							
						PortalMail pMail1 = new PortalMail();
						pMail1.setMailSubject("Diksha: Offer sent notification");
						pMail1.setEmpFname(tat_pInfo.getFirstName()+""+tat_pInfo.getLastName());
						pMail1.setTemplateName(MailSettings.CANDIDATE_OFFER_SEND_TAT_NOTIFY);
						pMail1.setCandidateName(profileInfo2.getFirstName()+""+profileInfo2.getLastName());
						
						Users candRMuser = usersDao.findByPrimaryKey(profileInfo2.getReportingMgr());
						ProfileInfo candRMuserprofileInfo = profileInfoDao.findByPrimaryKey(candRMuser.getProfileId());
						allReceipientcCMailId.add(candRMuserprofileInfo.getOfficalEmailId());
						allReceipientcCMailId.add(tat_pInfo.getOfficalEmailId());
						pMail1.setAllReceipientcCMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
						
						mailGenerator.invoker(pMail1);
						
			
						
						/**
						 * Inbox notification to TAT user
						 */
						String offersent = "Diksha: Offer Sent -  [" + profileInfo2.getFirstName() +" "+profileInfo2.getLastName() +"] on [" + PortalUtility.formatDateddMMyyyy(candidate.getDateOfOffer()) + "]";
						String offer_resent = "Diksha: Offer Resent - [" + profileInfo2.getFirstName() +""+profileInfo2.getLastName()+ "] on [" + PortalUtility.formatDateddMMyyyy(candidate.getDateOfOffer()) + "]";
						pMail.setLeaveType(levelsCandidate.getLabel());
						pMail.setMailSubject(candidate.getStatus() == 6 ? offer_resent : offersent);
						pMail.setEmpFname(tat_pInfo.getFirstName()+""+tat_pInfo.getLastName());
						pMail.setTemplateName(candidate.getStatus() == 6 ? MailSettings.NOTIFICATION_OFFER_RESEND_TAT : MailSettings.NOTIFICATION_OFFER_SEND_TAT);
						pMail.setEmpFname(tat_pInfo.getFirstName()+""+tat_pInfo.getLastName());
						pMail.setRecipientMailId(tat_pInfo.getOfficalEmailId());
						bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(candidate.getStatus() == 6 ? MailSettings.NOTIFICATION_OFFER_RESEND_TAT : MailSettings.NOTIFICATION_OFFER_SEND_TAT), pMail);
						pMail.setMailBody(bodyText);
						cReq.setSummary(candidate.getStatus() == 6 ? offer_resent : offersent);
						cReq.setCandidateId(candidate.getId());
						cReq.setEsrqmId(cReq1.getEsrqmId());
						cReq.setOfferLetter(candidate.getOfferLetter());
					
						
						
						pMail.setFileSources(null);
						cReq.setMessageBody(bodyText);
						cReq.setCycle(nextCycle);
						CandidateReqPk cReqPk = cReqDao.insert(cReq);
						inbox = inboxModel.sendToDockingStation(cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus());
						/**
						 * Inbox notification to RM of candidate
						 */
						pMail.setMailSubject(candidate.getStatus() == 6 ? offer_resent : offersent);
						pMail.setEmpFname(candRMuserprofileInfo.getFirstName()+""+candRMuserprofileInfo.getLastName());
						pMail.setTemplateName(candidate.getStatus() == 6 ? MailSettings.NOTIFICATION_OFFER_RESEND_TAT : MailSettings.NOTIFICATION_OFFER_SEND_TAT);
						pMail.setEmpFname(candRMuserprofileInfo.getFirstName()+""+candRMuserprofileInfo.getLastName());
						pMail.setRecipientMailId(candRMuserprofileInfo.getOfficalEmailId());
						bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(candidate.getStatus() == 6 ? MailSettings.NOTIFICATION_OFFER_RESEND_TAT : MailSettings.NOTIFICATION_OFFER_SEND_TAT), pMail);
						pMail.setMailBody(bodyText);
					pMail.setFileSources(null);
						try{
							Inbox inbox3 = new Inbox();
							cReq.setMessageBody(bodyText);
							inbox3 = insertInboxManual(inbox3, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail, candRMuser.getId());
							inboxDao.insert(inbox3);
						} catch (InboxDaoException e){
							e.printStackTrace();
						}
					}
					break;
				case ACCEPTREJECTOFFER:
					inboxDao = InboxDaoFactory.create();
					List<String> allReceipientcC = new ArrayList<String>();
					Set<String> allReceipient = new HashSet<String>();
					candidate = (Candidate) form;
					Candidate[] candidateCheck = candidateDao.findByDynamicWhere("STATUS IN(2,3) AND ID=?",  new Object[] { candidate.getId() });
					if(candidateCheck!=null && candidateCheck.length>0){
						result.setForwardName("validJsp");
						return result;
					}
					String comments = candidate.getComments();
					int acc = 0;
					CandidateReqPk cReqPk = new CandidateReqPk();
					acc = candidate.getIsAccepted();
					String candidateNameId = "";
					candidate = candidateDao.findByPrimaryKey(candidate.getId());
					candidate.setUuidNull(true);
					candidate.setUuid(null);
					candidate.setIsAccepted(acc);
					ProfileInfo profileInfo3 = profileInfoDao.findByPrimaryKey(candidate.getProfileId());
					PersonalInfo personalInfo3 = personalInfoDao.findByPrimaryKey(candidate.getPersonalId());
					// send email to candidate
					if (candidate.getIsAccepted() == 2){
						Login login3 = loginDao.findWhereCandidateIdEquals(candidate.getId());
						if (login3 == null){
							new PortalUtility().generateUserName(profileInfo3, candidate.getId());
							login3 = loginDao.findWhereCandidateIdEquals(candidate.getId());
						}
						login3.setPassword(DesEncrypterDecrypter.getInstance().decrypt(login3.getPassword()));
						pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, profileInfo3, personalInfo3, login3, false);
						pMail.setTemplateName(MailSettings.CANDIDATE_OFFER_ACCEPT);
						candidate.setStatus(2);
					} else{
						pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, profileInfo3, personalInfo3, null, false);
						pMail.setTemplateName(MailSettings.CANDIDATE_OFFER_REJECT);
						candidate.setStatus(3);
					}
					candidateNameId = profileInfo3.getFirstName() + " " + profileInfo3.getLastName()+ candidate.getId();
					candidatePk.setId(candidate.getId());
					candidateDao.update(candidatePk, candidate);
					cReq1 = cReqDao.findWhereCandidateIdEquals(candidatePk.getId())[0];
					candidateReqPrev = cReqDao.findByDynamicWhere("ESRQM_ID=? ORDER BY CREATEDATETIME DESC", new Object[] { cReq1.getEsrqmId() })[0];
					nextCycle = candidateReqPrev.getCycle();
					// Date format dd/mm/yyyy
					String date = pMail.getCandidateDOJ();
					date = date.substring(3, 6) + date.substring(0, 2) + date.substring(5, 10);
					pMail.setCandidateDOJ(date);
					pMail.setEmpFname(profileInfo3.getFirstName());
					pMail.setMailSubject(candidate.getStatus() == 2 ? "Warm welcome to Diksha Technologies" : "Re:Congratulations! Employment Offer Details from Diksha Technologies");
					pMail.setFileSources(null);
					mailGenerator.invoker(pMail);
					Users candRMuser = usersDao.findByPrimaryKey(profileInfo3.getReportingMgr());
					ProfileInfo candRMuserprofileInfo = profileInfoDao.findByPrimaryKey(candRMuser.getProfileId());
					// Send email to TAT template
					// create candidate req
					cReq = new CandidateReq();
					if (candidate.getStatus() == 2){
						cReq.setStatus(Status.ACCEPTED);
						pMail.setMailSubject("Diksha: Offer Accepted - [" + profileInfo3.getFirstName() +""+profileInfo3.getLastName()+ "] with DOJ [" + PortalUtility.formatDateddMMyyyy(profileInfo3.getDateOfJoining()) + "]");
					} else if (candidate.getStatus() == 3){	
						cReq.setStatus(Status.REJECTED);
						pMail.setMailSubject("Diksha: Offer Rejected - [" + profileInfo3.getFirstName() +""+profileInfo3.getLastName()+ "]");
					}
					Users user = new Users();
					user = usersDao.findByPrimaryKey(candidate.getTatId());
					profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());
					Levels levels = LevelsDaoFactory.create().findByPrimaryKey(profileInfo.getLevelId());
					Divison divison = DivisonDaoFactory.create().findByPrimaryKey(levels.getDivisionId());
					Regions region1 = regionsDao.findByPrimaryKey(divison.getRegionId());
					Notification notifi = new Notification(region1.getRefAbbreviation());
					int tatLead = notifi.tatLeadLevelId;
					int rmgManager = notifi.rmgManagerLevelId;
					int financedept = notifi.financeId;
					int itdept = notifi.operationsId;
					int admin = notifi.admin;
					List<String> tatLeadEmails = UserModel.getUsersByLevelId(tatLead, null);
					List<String> rmgManagerEmails = UserModel.getUsersByLevelId(rmgManager, null);
					List<String> financedeptEmails = UserModel.getUsersByDivision(financedept, null);
					List<String> ITemails = UserModel.getUsersByDivision(itdept, null);
					List<String> adminemails = UserModel.getUsersByDivision(admin, null);
					Levels levelsCandidate = LevelsDaoFactory.create().findByPrimaryKey(profileInfo3.getLevelId());
					Address address = AddressDaoFactory.create().findByPrimaryKey(personalInfo3.getPermanentAddress());
					/**
					 * Commented to return the salary details according to the new
					 * salary structure.
					 */
					// SalaryDetails salary[] = SalaryDetailsDaoFactory.create()
					// .findByDynamicWhere(
					// " CANDIDATE_ID= ? "
					// + "AND FIELD_LABEL = 'Total'",
					// new Object[] { candidate.getId() });
					// String CTC =
					// DesEncrypterDecrypter.getInstance().decrypt(salary[0].getAnnual());
					Salary candidateSalary = new SalaryDetailModel().getSalary(candidate.getId(), false, SalaryDetailModel.TYPE_NORMAL);
					String CTC = String.valueOf(candidateSalary.getCtc());
					if (candidate.getStatus() == 2){
						allReceipient.addAll(rmgManagerEmails);
						allReceipient.addAll(financedeptEmails);
						allReceipient.addAll(ITemails);
						allReceipient.addAll(adminemails);
						pMail.setActionType(personalInfo3.getAlternateEmailId() == null || personalInfo3.getAlternateEmailId().equals("") ? "N.A" : personalInfo3.getAlternateEmailId());// alternate
																																															// email id
						pMail.setRepoMngrAtClient(candRMuserprofileInfo.getFirstName());// reporting
																						// manager
						pMail.setDate(PortalUtility.formatDateddMMyyyy(profileInfo3.getDob()));// DOB
						pMail.setFieldName(personalInfo3.getPrimaryPhoneNo());// contact
																				// number
						pMail.setAmount(getAddressFields(address));// address
																	// permenant
					} else{
						pMail.setDate(PortalUtility.formatDateddMMyyyy(candidate.getDateOfOffer()));// Date														// offer
						pMail.setHrSPOC(profileInfo.getFirstName());// tat name
						pMail.setAmount(CTC);// CTC
					}
					allReceipient.add(profileInfo.getOfficalEmailId());// tat email													// id
					pMail.setEmpFname(profileInfo.getFirstName());
					// pMail.setRecipientMailId(profileInfo.getOfficalEmailId());//tat
					// email id
					pMail.setFileSources(null);
					pMail.setEmpFname(profileInfo.getFirstName()+" "+profileInfo.getLastName());
					pMail.setTemplateName(candidate.getStatus() == 2 ? MailSettings.CANDIDATE_TAT_ACCEPTANCE : MailSettings.CANDIDATE_TAT_REJECT);
					pMail.setFileSources(null);
					pMail.setComment(comments == null ? "" : comments);
					pMail.setLeaveType(levelsCandidate.getLabel());// level
					bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(pMail.getTemplateName()), pMail);
					pMail.setMailBody(bodyText);
					pMail.setFieldName(personalInfo3.getPrimaryPhoneNo());
					// populate inbox
					cReq.setAssignedTo(cReq1.getAssignedTo());
					cReq.setRaisedBy(cReq1.getRaisedBy());
					cReq.setSummary(pMail.getMailSubject());
					cReq.setCandidateId(candidate.getId());
					cReq.setEsrqmId(cReq1.getEsrqmId());
					cReq.setCycle(nextCycle);
					cReq.setComments(comments);
					cReq.setMessageBody(pMail.getMailBody());
					cReqPk = cReqDao.insert(cReq);
					inbox = inboxModel.sendToDockingStation(cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus());
					inboxModel.notify(cReq1.getEsrqmId(), inbox);
					// add RM and TAT lead in CC and email to TAT
					allReceipientcC.add(candRMuserprofileInfo.getOfficalEmailId());
					allReceipientcC.addAll(tatLeadEmails);// add tat leads
					String[] allccReceipientMailIds = allReceipientcC.toArray(new String[0]); // Collection
					String[] allReceipientMailIds = allReceipient.toArray(new String[0]); // Collection
					pMail.setAllReceipientcCMailId(allccReceipientMailIds);
					pMail.setRecipientMailId(null);
					pMail.setAllReceipientMailId(allReceipientMailIds);
					bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(pMail.getTemplateName()), pMail);
					pMail.setMailBody(bodyText);
					mailGenerator.invoker(pMail);
					// notify RM used insertInboxManual
					Inbox inbox3 = new Inbox();
					inbox3 = insertInboxManual(inbox3, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail, candRMuser.getId());
					inboxDao.insert(inbox3);
					// notify TAT LEAD used insertInboxManual
					Users tatLeadUsers[] = UserModel.getUsersIDByLevelId(tatLead, null);
					if (tatLeadUsers != null && tatLeadUsers.length > 0) for (Users single : tatLeadUsers){
						inbox3 = insertInboxManual(inbox3, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail, single.getId());
						inboxDao.insert(inbox3);
					}
					if (candidate.getStatus() == 2){
						// notify itdept insertInboxManual
						Users ITUsers[] = UserModel.getUsersIDByDivisionId(itdept, null);
						if (ITUsers != null && ITUsers.length > 0) for (Users single : ITUsers){
							inbox3 = insertInboxManual(inbox3, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail, single.getId());
							inboxDao.insert(inbox3);
						}
						// notify finance insertInboxManual
						Users financeUsers[] = UserModel.getUsersIDByDivisionId(financedept, null);
						if (financeUsers != null && financeUsers.length > 0) for (Users single : financeUsers){
							inbox3 = insertInboxManual(inbox3, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail, single.getId());
							inboxDao.insert(inbox3);
						}
						// notify RMG insertInboxManual
						Users RMGUsers[] = UserModel.getUsersIDByLevelId(rmgManager, null);
						if (RMGUsers != null && RMGUsers.length > 0) for (Users single : RMGUsers){
							inbox3 = insertInboxManual(inbox3, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail, single.getId());
							inboxDao.insert(inbox3);
						}
					}
					/**
					 * Send email to IT and Hr dept
					 */
					// if(candidate.getStatus() == 2){
					// LevelsDao levelsDao= LevelsDaoFactory.create();
					// Levels level =
					// levelsDao.findByPrimaryKey(profileInfo.getLevelId());// level
					// id
					// Divison div= new Divison();
					// DivisonDao divisonDao = DivisonDaoFactory.create();
					// div = divisonDao.findByPrimaryKey(level.getDivisionId());
					// Regions region =
					// regionsDao.findByPrimaryKey(div.getRegionId());
					// Notification notification = new
					// Notification(region.getRefAbbreviation());
					// UserModel userModel= new UserModel();
					// int FINANCE = notification.financeId;
					// int TAT = notification.hrdId;
					// int IT = notification.itAdminId;
					// List<String> finance = userModel.getUsersByDivision(IT,
					// null);
					// List<String> TATusers = userModel.getUsersByParentId(TAT,
					// null);
					// finance.addAll(TATusers);
					//
					// String[] allReceipientMailId = finance.toArray(new
					// String[0]); // Collection
					// pMail.setMailSubject("Offer Acceptance");
					// pMail.setAllReceipientMailId(allReceipientMailId);
					// pMail.setTemplateName( MailSettings.CANDIDATE_NOTIFY_DEPT);
					// mailGenerator.invoker(pMail);
					// }
					if (candidate.getStatus() == 2){
						Date newDate = PortalUtility.reminderDate();
						logger.info("+2 date" + PortalUtility.formateDateToDate(newDate));
						logger.info("**************************************************" + PortalUtility.formateDateTimeToDateDB(profileInfo3.getDateOfJoining()).equals(PortalUtility.formateDateTimeToDateDB(newDate)));
						if (PortalUtility.formateDateTimeToDateDB(profileInfo3.getDateOfJoining()).equals(PortalUtility.formateDateTimeToDateDB(newDate))){
							sendReportingDetails(profileInfo3, candidate);
						}
					}
					break;
				case RESENDOFFERLETTER:
					int stat = 0;
					boolean noapprover = false;
					boolean someerror = false;
					CandidateReqPk candReqPk = new CandidateReqPk();
					SalaryInfoPk salaryInfoPk = new SalaryInfoPk();
					//SalaryInfoDao salaryInfoDao = SalaryInfoDaoFactory.create();
					inboxDao = InboxDaoFactory.create();
					try{
						float totalABCDSalary = 0.0f;
						cReqPk = new CandidateReqPk();
						login = Login.getLogin(request);
						CandidateSaveForm candidatesavefrm = (CandidateSaveForm) form;
						Users requestor = usersDao.findByPrimaryKey(login.getUserId());
						ProfileInfo requestorProfile = profileInfoDao.findByPrimaryKey(requestor.getProfileId());
						ProfileInfo profileInfo1 = pForms.setProfileInfo(candidatesavefrm);
						PersonalInfo personalInfo1 = pForms.setPersonalInfo(candidatesavefrm);
						SalaryDetails salaryDetails1 = pForms.setSalaryDetails(candidatesavefrm);
						Address perAddress = pForms.setPermAddress(candidatesavefrm);
						SalaryInfo salaryInfo1 = pForms.setSalaryInfo(candidatesavefrm);
						candidate.setId(candidatesavefrm.getId());
						candidatePk.setId(candidatesavefrm.getId());
						candidate = candidateDao.findByPrimaryKey(candidate.getId());
						salaryDetails1.setCandidateId(candidate.getId());
						if (candidate.getProfileId() > 0){
							profileInfo1.setId(candidate.getProfileId());
						}
						if (candidate.getPersonalId() > 0){
							personalInfo1.setId(candidate.getPersonalId());
						}
						/**
						 * Update Profile Info
						 */
						ProfileInfoPk profileInfoPk = new ProfileInfoPk();
						PersonalInfoPk personalInfoPk = new PersonalInfoPk();
						if (!candidatesavefrm.isSendforapprovalPreview()){
							if (candidate.getProApprovalId() > 0){
								// update
								ProfileInfoPk profilePk = new ProfileInfoPk();
								profilePk.setId(candidate.getProApprovalId());
								profileInfo1.setId(candidate.getProApprovalId());
								profileInfoDao.update(profilePk, profileInfo1);
								profileInfoPk.setId(candidate.getProApprovalId());
							} else{
								// save
								ProfileInfoModel profileInfoModel1 = new ProfileInfoModel();
								profileInfo1.setEmpId(-99);// avoid being inserted
															// in user table
							
								profileInfoModel1.save(profileInfo1, request);
								profileInfoPk = (ProfileInfoPk) request.getAttribute("profileId");
								candidate.setProApprovalId(profileInfoPk.getId());
								
								
							}
							if (candidate.getPerApprovalId() > 0){
								// update
								PersonalInfo personalInfoTemp = personalInfoDao.findByPrimaryKey(candidate.getPerApprovalId());
								if (perAddress != null){
									AddressModel addressModel2 = new AddressModel();
									Address tempadd = addressDao.findByPrimaryKey(personalInfoTemp.getPermanentAddress());
									perAddress.setId(tempadd.getId());
									addressModel2.update(perAddress, request);
								}
								PersonalInfoPk personalPk = new PersonalInfoPk();
								personalPk.setId(candidate.getPerApprovalId());
								personalInfo1.setId(candidate.getPerApprovalId());
								personalInfoDao.update(personalPk, personalInfo1);
								personalInfoPk.setId(candidate.getPerApprovalId());
							} else{
								// save
								/**
								 * Save Address info
								 */
								if (perAddress != null){
									perAddress.setId(perAddress.getId() != 0 ? 0 : 0);
									addressDao = AddressDaoFactory.create();
									AddressPk addressPk1 = new AddressPk();
									try{
										if (perAddress != null){
											addressPk1 = addressDao.insert(perAddress);
										}
										perAddress.setId(addressPk1.getId());
									} catch (Exception e){
										logger.info("Failed to save Address Info");
										result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
										e.printStackTrace();
									}
									personalInfo1.setPermanentAddress(perAddress.getId());
								}
								/**
								 * Save Personal Info
								 */
								PersonalInfoModel personalInfoModel1 = new PersonalInfoModel();
								personalInfo1.setuType("replica");
								personalInfoModel1.save(personalInfo1, request);
								personalInfoPk = (PersonalInfoPk) request.getAttribute("personalId");
								candidate.setPerApprovalId(personalInfoPk.getId());
							}
							//							SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.createReplica();
							//							SalaryDetails[] details2 = salaryDetailsDao.findWhereCandidateIdEquals(candidate.getId());
							//							if (details2 != null && details2.length > 0){
							// Update Salary
							//Code to create new salary and update any existing salary.
							//								if (salaryDetails1.getFields() != null){
							//									salaryDetails1.setuType("UPDATE");
							//									salaryDetails1.setsType("replica");
							//									SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
							//									salaryDetails1.setCandidateId(candidate.getId());
							//									salaryDetailModel.update(salaryDetails1, request);
							//								}
							if (candidate != null){
								SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
								float ctc = Float.valueOf(candidatesavefrm.getCtcAmount());
								float perDiem;
						//		float fixedPerdiem;
								float retenBonus;
								if(candidatesavefrm.getPerdiemAmount()!=null&&!(candidatesavefrm.getPerdiemAmount().equalsIgnoreCase(""))){
									perDiem= Float.valueOf(candidatesavefrm.getPerdiemAmount());
								}else{
									perDiem=0;
								}
								
							/*	if(candidatesavefrm.getFixedPerdiemAmount()!=null&&!(candidatesavefrm.getFixedPerdiemAmount().equalsIgnoreCase(""))){
									fixedPerdiem= Float.valueOf(candidatesavefrm.getFixedPerdiemAmount());
									saveCandidateFixPerdiem(fixedPerdiem,candidate.getId());
									
								}else{
									fixedPerdiem=0;
								}*/
								
								if(candidatesavefrm.getRetentionBonus()!=null&&!(candidatesavefrm.getRetentionBonus().equalsIgnoreCase(""))){
									retenBonus= Float.valueOf(candidatesavefrm.getRetentionBonus());
								}else{
									retenBonus=0;
								}
								String plb = candidatesavefrm.getPlb();
						//		Salary salary = new FinanceInfoModel().getCalculatedSalary(plb, ctc, perDiem, true,fixedPerdiem,retenBonus);
								Salary salary = new FinanceInfoModel().getCalculatedSalary1(plb, ctc, perDiem, true,retenBonus);
								result = salaryDetailModel.saveSalaryDetails(Float.valueOf(plb), salary, 0, candidate.getId(), SalaryDetailModel.TYPE_REPLICA);
								Salary sal = salaryDetailModel.getSalary(candidate.getId(), false, SalaryDetailModel.TYPE_REPLICA);
								totalABCDSalary = sal.getAnnualCTC();
							
								
								if (result != null && !result.getActionMessages().isEmpty()){
									logger.error("The records for the candidate " + candidate.getCandidateId() + " was not saved properly due to error in saving the record to table SALARY_DETAILS");
								}
							}
							//							} else{
							//								/**
							//								 * Save Salary Info
							//								 */
							//								//Code to create new salary and store in replica table
							//								if (candidate != null){
							//									SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
							//									float ctc = Float.valueOf(candidatesavefrm.getCtcAmount());
							//									float perDiem = Float.valueOf(candidatesavefrm.getPerDeimAmount());
							//									Salary salary = new FinanceInfoModel().getCalculatedSalary(ctc, perDiem, true);
							//									result = salaryDetailModel.saveSalaryDetails(salary, 0, candidate.getId(),SalaryDetailModel.TYPE_REPLICA);
							//									if (result != null && !result.getActionMessages().isEmpty()){
							//										logger.error("The records for the candidate " + candidate.getCandidateId() + " was not saved properly due to error in saving the record to table SALARY_DETAILS");
							//									}
							//								}
							//							}
							/**
							 * Save one time payments section if data exists update
							 * else save
							 */
							SalaryInfoDao salaryInfoDaoR = SalaryInfoDaoFactory.createReplica();
							SalaryInfo salaryInfo2[] = salaryInfoDaoR.findWhereBasicEquals(candidate.getId());
					if (salaryInfo2 != null && salaryInfo2.length > 0){
								if (candidatesavefrm.getRelocationBonus() != null && !candidatesavefrm.getRelocationBonus().equalsIgnoreCase("")){
									salaryInfo2[0].setRelocationBonus(candidatesavefrm.getRelocationBonus());
									salaryInfo2[0].setRelocationCity(candidatesavefrm.getRelocationCity());
								}else salaryInfo2[0].setRelocationBonus("");
								
								if (candidatesavefrm.getJoiningBonusString() != null){
									salaryInfo2[0].setJoiningBonusString(candidatesavefrm.getJoiningBonusString());
									salaryInfo2[0].setJoiningBonusAmount(candidatesavefrm.getJoiningBonusAmount() == null||candidatesavefrm.getJoiningBonusAmount() =="" ? "0" : candidatesavefrm.getJoiningBonusAmount());
									salaryInfo2[0].setPaymentTerms(candidatesavefrm.getPaymentTerms() == null||candidatesavefrm.getPaymentTerms() == "" ? "" : candidatesavefrm.getPaymentTerms());
								}
								
								if (candidatesavefrm.getPerdiemString() != null){
									salaryInfo2[0].setPerdiemString(candidatesavefrm.getPerdiemString() =="" ? "" : candidatesavefrm.getPerdiemString());
								}
								
								if (candidatesavefrm.getRetentionBonus() != null && candidatesavefrm.getRetentionBonus() !=""){
									salaryInfo2[0].setRetentionBonus(candidatesavefrm.getRetentionBonus());
								}else salaryInfo2[0].setRetentionBonus("");
								salaryInfo2[0].setPerdiemType(candidatesavefrm.getPerdiemType());
								salaryInfo2[0].setBasic(candidatePk.getId());// Basic// id
								salaryInfo2[0].setHra(0);// HRA-->user Id primary  key of user table 0
								salaryInfoDaoR.update(new SalaryInfoPk(salaryInfo2[0].getId()), salaryInfo2[0]);
								SalaryInfoBean salaryInfoBean = setSalaryInfoBean(salaryInfo1);
								salaryInfoBean.setId(salaryInfoPk.getId());
								SalaryInfoBean infoBean[] = new SalaryInfoBean[1];
								infoBean[0] = salaryInfoBean;
								candidatesavefrm.setSalaryInfoBean(infoBean);
							}
					else
					         {
								if (candidatesavefrm.getRelocationBonus() != null && candidatesavefrm.getRelocationBonus()!=""){
			                  salaryInfo1.setRelocationBonus(candidatesavefrm.getRelocationBonus());
			                  salaryInfo1.setRelocationCity(candidatesavefrm.getRelocationCity());
								}else salaryInfo1.setRelocationBonus("");
								if (candidatesavefrm.getJoiningBonusString() != null){
									salaryInfo1.setJoiningBonusString(candidatesavefrm.getJoiningBonusString());
									salaryInfo1.setJoiningBonusAmount(candidatesavefrm.getJoiningBonusAmount() == null||candidatesavefrm.getJoiningBonusAmount() =="" ? "0" : candidatesavefrm.getJoiningBonusAmount());
									salaryInfo1.setPaymentTerms(candidatesavefrm.getPaymentTerms() == null||candidatesavefrm.getPaymentTerms() == "" ? "" : candidatesavefrm.getPaymentTerms());
								} 
								if (candidatesavefrm.getPerdiemString() != null){
									salaryInfo1.setPerdiemString(candidatesavefrm.getPerdiemString() =="" ? "" : candidatesavefrm.getPerdiemString());
								}
								if (candidatesavefrm.getRetentionBonus() != null && candidatesavefrm.getRetentionBonus() !=""){
									salaryInfo1.setRetentionBonus(candidatesavefrm.getRetentionBonus());
								}else salaryInfo1.setRetentionBonus("");
								salaryInfo1.setPerdiemType(candidatesavefrm.getPerdiemType());
								salaryInfo1.setBasic(candidatePk.getId());// Basic
								salaryInfo1.setHra(0);// HRA-->user Id primary key of user table 0
								salaryInfoPk = salaryInfoDaoR.insert(salaryInfo1);
								SalaryInfoBean salaryInfoBean = setSalaryInfoBean(salaryInfo1);
								salaryInfoBean.setId(salaryInfoPk.getId());
								SalaryInfoBean infoBean[] = new SalaryInfoBean[1];
								infoBean[0] = salaryInfoBean;
								candidatesavefrm.setSalaryInfoBean(infoBean);
							}// end salry info save/update
					
					// saving candidate Perdiem details
					//update candidate perdiem details  format followed     locationName1~=~perdiemDetails~;~locationNAme2~=~perdiemDetails2
					CandidatePerdiemDetailsDao perdiemDetailsDao = CandidatePerdiemDetailsDaoFactory.create();
					String[] candidatePerdiemDetails = candidatesavefrm.getCandidatePerdiemDetails().split("~;~");
					if(candidatePerdiemDetails==null||candidatePerdiemDetails[0].equalsIgnoreCase("")){
						CandidatePerdiemDetails[] candPer=perdiemDetailsDao.findWhereCandidateIdEquals(candidate.getId());
						if(candPer!=null){
						for(CandidatePerdiemDetails eachcandPer:candPer){
						CandidatePerdiemDetailsPk pk=	new CandidatePerdiemDetailsPk();
						pk.setId(eachcandPer.getId());
						perdiemDetailsDao.delete(pk);}
						}
					}
					else{
						
						CandidatePerdiemDetails[] existingCandidatePerdiemDetails = perdiemDetailsDao.findWhereCandidateIdEquals(candidate.getId());
						    if(existingCandidatePerdiemDetails!=null && existingCandidatePerdiemDetails.length>0 && existingCandidatePerdiemDetails[0]!=null){
							for(CandidatePerdiemDetails eachcandPer:existingCandidatePerdiemDetails){
								CandidatePerdiemDetailsPk pk=	new CandidatePerdiemDetailsPk();
								pk.setId(eachcandPer.getId());
								perdiemDetailsDao.delete(pk);	
						                                     }
						                            }
						     for(String eachCandPer:candidatePerdiemDetails){
							        String[] temp1=eachCandPer.split(",");
							        String[] temp=temp1[0].split("~=~");
						        CandidatePerdiemDetails eachCand=new CandidatePerdiemDetails();
						        	eachCand.setCandidateId(candidate.getId());
						        	eachCand.setLocation(temp[0]);
						        	eachCand.setPerdiem(temp[1]);
						        	perdiemDetailsDao.insert(eachCand);
						        }
						}
						}
					
					if (candidate.getStatus() == 3 || candidate.getStatus() == 2 || candidate.getStatus() == 7 || candidate.getStatus() == 8){
							candidatePk.setId(candidate.getId());
							Candidate tempcandidate = candidateDao.findByPrimaryKey(candidate.getId());
							stat = candidate.getStatus();
							if ((!candidatesavefrm.isPreview() && candidatesavefrm.isSendforapprovalPreview())){
								tempcandidate.setStatus(4);// set status to offer
															// under
								tempcandidate.setProApprovalId(candidate.getProApprovalId());
								tempcandidate.setPerApprovalId(candidate.getPerApprovalId());
								profileInfo1 = profileInfoDao.findByPrimaryKey(candidate.getProApprovalId());
								personalInfo1 = personalInfoDao.findByPrimaryKey(candidate.getPerApprovalId());
							} else if (!candidatesavefrm.isPreview() && !candidatesavefrm.isSendforapprovalPreview()){
								tempcandidate.setStatus(4);// set status to offer
															// under
								tempcandidate.setProApprovalId(candidate.getProApprovalId());
								tempcandidate.setPerApprovalId(candidate.getPerApprovalId());
								profileInfo1 = profileInfoDao.findByPrimaryKey(candidate.getProApprovalId());
								personalInfo1 = personalInfoDao.findByPrimaryKey(candidate.getPerApprovalId());
							} else{
								tempcandidate.setProApprovalId(profileInfoPk.getId() > 0 ? profileInfoPk.getId() : 0);
								tempcandidate.setPerApprovalId(personalInfoPk.getId() > 0 ? personalInfoPk.getId() : 0);
							}
							candidateDao.update(candidatePk, tempcandidate);
							Levels levelC = LevelsDaoFactory.create().findByPrimaryKey(profileInfo1.getLevelId());
							// SalaryDetails
							// salary1[]=SalaryDetailsDaoFactory.create().findByDynamicWhere(" CANDIDATE_ID= ? "
							// +
							// "AND FIELD_LABEL = 'Total'", new Object[ ]{
							// candidate.getId() });
							// String
							// CTC1=DesEncrypterDecrypter.getInstance().decrypt(salary1[0].getAnnual());
							//							SalaryDetails salary1[] = SalaryDetailsDaoFactory.createReplica().findByDynamicWhere(" CANDIDATE_ID= ? " + "AND FIELD_LABEL = 'Total'", new Object[] { candidate.getId() });
							//							String CTC1 = DesEncrypterDecrypter.getInstance().decrypt(salary1[0].getAnnual());
							//SalaryDetailModel sdm=Sala
							// tatprofile
							Users tatuser = usersDao.findByPrimaryKey(tempcandidate.getTatId());
							ProfileInfo tatProfile = profileInfoDao.findByPrimaryKey(tatuser.getProfileId());
							if (!candidatesavefrm.isPreview() || candidatesavefrm.isSendforapprovalPreview()){
								cReq1 = cReqDao.findWhereCandidateIdEquals(candidatePk.getId())[0];
								candidateReqPrev = cReqDao.findByDynamicWhere("ESRQM_ID=? ORDER BY CREATEDATETIME DESC", new Object[] { cReq1.getEsrqmId() })[0];
								nextCycle = candidateReqPrev.getCycle();
								EmpSerReqMap eReqMap = eDao.findByPrimaryKey(cReq1.getEsrqmId());
								int lastApproveLevel = pEvaluator.findLastAppLevel(eReqMap);
								lastApproveLevel = 0;
								if (lastApproveLevel > 0){
									lastApproveLevel++;
								} else{
									lastApproveLevel = 1;
								}
								Integer[] assignTos = pEvaluator.approvers(pDao.findByPrimaryKey(eReqMap.getProcessChainId()).getApprovalChain(), lastApproveLevel, eReqMap.getRequestorId());
								candidateNameId = profileInfo1.getFirstName() + " " +profileInfo1.getLastName()+ candidate.getId();
								cReq = new CandidateReq();
								if (assignTos != null && assignTos.length > 0){
									for (Integer assignTo : assignTos){
										cReq.setAssignedTo(assignTo);
										cReq.setRaisedBy(login.getUserId());
										cReq.setStatus("Pending Approval");
										cReq.setSummary("Offer Resend Approval - [" + profileInfo1.getFirstName() +""+profileInfo1.getLastName()+ "] by [" + requestorProfile.getFirstName() +""+requestorProfile.getLastName()+ "]");
										cReq.setCandidateId(candidate.getId());
										cReq.setEsrqmId(cReq1.getEsrqmId());
										cReq.setCycle(nextCycle);
										cReq.setServed(lastApproveLevel);
										Users assignedToUser = usersDao.findByPrimaryKey(assignTo);
										profileInfo = profileInfoDao.findByPrimaryKey(assignedToUser.getProfileId());
										pMail.setDate(PortalUtility.formatDateddMMyyyy(candidate.getDateOfOffer()));// Date
																													// of
																													// offer
										pMail.setHrSPOC(tatProfile.getFirstName()+""+tatProfile.getLastName());// tat
																					// name
									// ctc for preview and send for Approval 	
										SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
										Salary sal = salaryDetailModel.getSalary(candidate.getId(), false, SalaryDetailModel.TYPE_REPLICA);
									    totalABCDSalary = sal.getAnnualCTC();
										
										
										pMail.setAmount(String.valueOf(totalABCDSalary));// CTC
										pMail.setLeaveType(levelC.getLabel());
										pMail.setMailSubject("Diksha: Offer Resend Approval - [" + profileInfo1.getFirstName() +""+profileInfo1.getLastName()+ "] by [" + requestorProfile.getFirstName() +""+requestorProfile.getLastName()+ "]");
										pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
										pMail.setFileSources(null);
										pMail.setCandidateName(profileInfo1.getFirstName()+""+profileInfo1.getLastName());
										pMail.setEmpFname(profileInfo.getFirstName()+""+profileInfo.getLastName());
										pMail.setTemplateName(MailSettings.RESEND_OFFER_NOTIFICATION);
										pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, profileInfo1, personalInfo1, null, false);
										String tempFileName1 = generateFName(candidate.getId(), PortalData.OFF_LETTER, ExportType.pdf);
										JGenerator jGenerator1 = new JGenerator();
										HashMap<String, Object> params = getSalaryReportParams(candidate.getId(), true);
								//		String template = getTemplate(0, true);
										String template ="OfferApprove.jrxml";
										if (jGenerator1.generateFile(JGenerator.CANDIDATE, tempFileName1, template, params)){
											candidate.setOfferLetter(tempFileName1);
										}
										Attachements attachement = new Attachements();
										String filePath = JGenerator.getOutputFile(JGenerator.CANDIDATE, candidate.getOfferLetter());
										attachement.setFilePath(filePath);
										attachement.setFileName("OfferLetter.pdf");
										
										pMail.setFileSources(new Attachements[] { attachement});
										pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
										bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.RESEND_OFFER_NOTIFICATION), pMail);
										pMail.setMailBody(bodyText);
										pMail.setEmpFname(profileInfo.getFirstName()+""+profileInfo.getLastName());
										mailGenerator.invoker(pMail);
										cReq.setMessageBody(pMail.getMailBody());
										candReqPk = cReqDao.insert(cReq);
										inbox = inboxModel.sendToDockingStation(cReq.getEsrqmId(), candReqPk.getId(), cReq.getSummary(), cReq.getStatus());
										inboxModel.notify(cReq1.getEsrqmId(), inbox);
									}
								} else{
									noapprover = true;
									throw new Exception("No approvers for this process !");
								}
							}// end of isPreview
						}
						// Update commmitment sections
						// format of the string "id:comment~=~date"
						CommitmentModel obj1 = new CommitmentModel();
						obj1.update(candidatesavefrm, request);
					} catch (Exception e){
						someerror = true;
						logger.info("Failed to update Login Info");
						if (noapprover){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.loan.invalid.chain"));
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
						}
						e.printStackTrace();
					} finally{
						if (noapprover || someerror){
							if (candReqPk.getId() > 0) cReqDao.delete(candReqPk);
							candidate.setStatus(stat);// set status to
							candidateDao.update(candidatePk, candidate);
						}
					}
					break;
				case UPDATECANDIDATEREQ:
					pMail = new PortalMail();
					cReqPk = new CandidateReqPk();
					ProfileInfo assignedToprofileInfo = new ProfileInfo();
					login = Login.getLogin(request);
					int perId = 0,
					proId = 0;
					CandidateSaveForm cSaveForm = (CandidateSaveForm) form;
					inboxDao = InboxDaoFactory.create();
					cReq1 = cReqDao.findWhereCandidateIdEquals(cSaveForm.getCandidateId())[0];
					candidateReqPrev = cReqDao.findByDynamicWhere("ESRQM_ID=? ORDER BY CREATEDATETIME DESC", new Object[] { cReq1.getEsrqmId() })[0];
					Candidate candidate2 = candidateDao.findByPrimaryKey(candidateReqPrev.getCandidateId());
					ProfileInfo candidProfile = profileInfoDao.findByPrimaryKey(candidate2.getProfileId());
					PersonalInfo candidpersonal = personalInfoDao.findByPrimaryKey(candidate2.getPersonalId());
					candidateNameId = candidProfile.getFirstName() + "" +candidProfile.getLastName() +""+ candidate2.getId();
					updateActionBy(cReq1.getEsrqmId(), login.getUserId(), cSaveForm.getApproveOffer(), cSaveForm.getComments());
					Levels levelC = LevelsDaoFactory.create().findByPrimaryKey(candidProfile.getLevelId());
					float totalABCDSalary = 0.0f;
					// SalaryDetails
					// salary2[]=SalaryDetailsDaoFactory.create().findByDynamicWhere(" CANDIDATE_ID= ? "
					// +
					// "AND FIELD_LABEL = 'Total'", new Object[ ]{
					// cSaveForm.getCandidateId() });
					// String
					// CTC2=DesEncrypterDecrypter.getInstance().decrypt(salary2[0].getAnnual());
					//					SalaryDetails salary2[] = SalaryDetailsDaoFactory.createReplica().findByDynamicWhere(" CANDIDATE_ID= ? " + "AND FIELD_LABEL = 'Total'", new Object[] { cSaveForm.getCandidateId() });
					//					String CTC2 = DesEncrypterDecrypter.getInstance().decrypt(salary2[0].getAnnual());
					if (candidate != null){
						SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
						Salary salary = salaryDetailModel.getSalary(cSaveForm.getCandidateId(), false, SalaryDetailModel.TYPE_REPLICA);
						totalABCDSalary = salary.getAnnualCTC();
					}
					String CTC2 = String.valueOf(totalABCDSalary);
					
					
					
					
					
					// tatprofile
					Users tatuser2 = usersDao.findByPrimaryKey(candidate2.getTatId());
					ProfileInfo tatProfile2 = profileInfoDao.findByPrimaryKey(tatuser2.getProfileId());
					EmpSerReqMap eReqMap = eDao.findByPrimaryKey(cReq1.getEsrqmId());
					Users raisedByUser = usersDao.findByPrimaryKey(cReq1.getRaisedBy());
					ProfileInfo raisedByUserprofileInfo = profileInfoDao.findByPrimaryKey(raisedByUser.getProfileId());
					Levels levels1 = LevelsDaoFactory.create().findByPrimaryKey(raisedByUserprofileInfo.getLevelId());
					Divison divison1 = DivisonDaoFactory.create().findByPrimaryKey(levels1.getDivisionId());
					Regions region11 = regionsDao.findByPrimaryKey(divison1.getRegionId());
					Notification notifi1 = new Notification(region11.getRefAbbreviation());
					int tatLead1 = notifi1.tatLeadLevelId;
					//int rmgManager1 = notifi1.rmgManagerLevelId;
					//List<String> tatLeadEmails1 = UserModel.getUsersByLevelId(tatLead1, null);
					//List<String> rmgManagerEmails1 = UserModel.getUsersByLevelId(rmgManager1, null);
					int lastApproveLevel = pEvaluator.findLastAppLevel(eReqMap);
					if (lastApproveLevel > 0){
						lastApproveLevel++;
					} else{
						lastApproveLevel = 1;
					}
					Integer[] assignTos = pEvaluator.approvers(pDao.findByPrimaryKey(eReqMap.getProcessChainId()).getApprovalChain(), lastApproveLevel, eReqMap.getRequestorId());
					if (assignTos != null && assignTos.length > 0){
						for (Integer assignTo : assignTos){
							cReq = new CandidateReq();
							cReq.setRaisedBy(cReq1.getRaisedBy());
							nextCycle = candidateReqPrev.getCycle();
							CandidatePk cPk = new CandidatePk();
							cPk.setId(candidateReqPrev.getCandidateId());
							if (cSaveForm.getApproveOffer() == 1){
								// if (assignTo == 0)
								// {
								cReq.setAssignedTo(cReq1.getAssignedTo());
								cReq.setStatus(Status.OFFERAPPROVED);
								cReq.setSummary("Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
								pMail.setIssueReqStatus(" approved ");
								pMail.setMailSubject("Diksha: Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
								candidate2.setStatus(7);
								/**
								 * If approved enter update candidate data for
								 * profile and personal and deleted existing salary
								 * stack and insert new
								 */
								perId = candidate2.getPersonalId();
								proId = candidate2.getProfileId();
								
								
								//Testing part reporting time	
								ProfileInfoPk pk=new ProfileInfoPk();
								ProfileInfo tempProfile = profileInfoDao.findByPrimaryKey(candidate2.getProfileId());
								pk.setId(candidProfile.getId());
								tempProfile.setReportingTime(tempProfile.getReportingTime());
								profileInfoDao.update(pk, candidProfile);
								
								candidate2.setPersonalId(candidate2.getPerApprovalId());
								candidate2.setProfileId(candidate2.getProApprovalId());
								candidate2.setPerApprovalId(0);
								candidate2.setProApprovalId(0);
								approvedSalary = setApprovedSalary(candidate2);
								salaryinfo = setApprovedSalaryInfo(candidate2);
								// }
								// else
								// {
								// cReq.setAssignedTo(assignTo);
								// cReq.setStatus(Status.OFFERUNDERREVIEW);
								// cReq.setSummary("Re:- Offer Under Review");
								// candidate2.setStatus(4);
								// }
							} else if (cSaveForm.getRejectOffer() == 1){
								cReq.setAssignedTo(cReq1.getAssignedTo());
								cReq.setStatus(Status.OFFERNOTAPPROVED);
								cReq.setSummary("Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
								pMail.setIssueReqStatus(" not Approved ");
								pMail.setMailSubject("Diksha: Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
								candidate2.setStatus(8);
								approvedSalary = false;
								salaryinfo = false;
							}
							candidateDao.update(cPk, candidate2);
							cReq.setCandidateId(cSaveForm.getCandidateId());
							cReq.setEsrqmId(cReq1.getEsrqmId());
							cReq.setCycle(nextCycle);
							cReq.setServed(lastApproveLevel);
							/**
							 * Send email notification also to approver
							 */
							Users assignedToUser = usersDao.findByPrimaryKey(assignTo);
							assignedToprofileInfo = profileInfoDao.findByPrimaryKey(assignedToUser.getProfileId());
							// pMail.setFileSources(null);
							// pMail.setCandidateName(candidProfile.getFirstName());
							// pMail.setEmpFname(assignedToprofileInfo.getFirstName());
							// pMail.setTemplateName(MailSettings.OFFER_APPROVAL_NOTIFICATION);
							// pMail.setRecipientMailId(assignedToprofileInfo.getOfficalEmailId());
							// bodyText =
							// mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.OFFER_APPROVAL_NOTIFICATION),
							// pMail);
							// pMail = DtoToBeanConverter.DtoToBeanConverter(pMail,
							// candidate, candidProfile, candidpersonal, null,
							// true);
							// pMail.setMailBody(bodyText);
							// pMail.setRecipientMailId(assignedToprofileInfo.getOfficalEmailId());
							// pMail.setEmpFname(assignedToprofileInfo.getFirstName());
							// //mailGenerator.invoker(pMail);
							//
							// cReq.setMessageBody(pMail.getMailBody());
							// cReqPk = cReqDao.insert(cReq);
							// inbox =
							// inboxModel.sendToDockingStation(cReq.getEsrqmId(),
							// cReqPk.getId(), cReq.getSummary(), cReq.getStatus());
							// inboxModel.notify(cReq1.getEsrqmId(), inbox);
						}
						/**
						 * Send email to requestor
						 */
						pMail.setTemplateName(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR);
						pMail.setMailSubject("Diksha: Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
						pMail.setDate(PortalUtility.formatDateddMMyyyy(candidate2.getDateOfOffer()));// Date
																										// of
																										// offer
						pMail.setHrSPOC(tatProfile2.getFirstName()+""+tatProfile2.getLastName());// tat name
						pMail.setAmount(CTC2);// CTC
						pMail.setLeaveType(levelC.getLabel());// level label
						pMail.setRecipientMailId(raisedByUserprofileInfo.getOfficalEmailId());
						pMail.setCandidateName(candidProfile.getFirstName()+""+candidProfile.getLastName());
						pMail.setEmpFname(raisedByUserprofileInfo.getFirstName()+""+raisedByUserprofileInfo.getLastName());
						pMail.setEmpLName(assignedToprofileInfo.getFirstName()+""+assignedToprofileInfo.getLastName());
						pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, candidProfile, candidpersonal, null, true);
						pMail.setRecipientMailId(raisedByUserprofileInfo.getOfficalEmailId());
						bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR), pMail);
						pMail.setMailBody(bodyText);
						pMail.setEmpFname(raisedByUserprofileInfo.getFirstName()+""+raisedByUserprofileInfo.getLastName());
						pMail.setFileSources(null);
						mailGenerator.invoker(pMail);
						Inbox inbox2 = new Inbox();
						inbox2 = insertInbox(inbox2, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail);
						inboxDao.insert(inbox2);
						/**
						 * Send email to TAT LEad and //notify TAT LEAD used
						 * insertInboxManual
						 */
						Users tatLeads[] = UserModel.getUsersIDByLevelId(tatLead1, null);
						if (tatLeads != null && tatLeads.length > 0) for (Users single : tatLeads){
							ProfileInfo tatleadProfile = profileInfoDao.findByPrimaryKey(single.getProfileId());
							pMail.setMailSubject("Diksha: Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
							pMail.setDate(PortalUtility.formatDateddMMyyyy(candidate2.getDateOfOffer()));// Date
																											// of
																											// offer
							pMail.setHrSPOC(tatProfile2.getFirstName()+""+tatProfile2.getLastName());// tat
																		// name
							pMail.setAmount(CTC2);// CTC
							pMail.setLeaveType(levelC.getLabel());// level label
							pMail.setTemplateName(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR);
							pMail.setRecipientMailId(tatleadProfile.getOfficalEmailId());
							pMail.setCandidateName(candidProfile.getFirstName()+""+candidProfile.getLastName());
							pMail.setEmpFname(tatleadProfile.getFirstName()+""+tatleadProfile.getLastName());
							pMail.setEmpLName(assignedToprofileInfo.getFirstName()+""+assignedToprofileInfo.getLastName());
							pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, candidProfile, candidpersonal, null, true);
							pMail.setRecipientMailId(tatleadProfile.getOfficalEmailId());
							bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR), pMail);
							pMail.setMailBody(bodyText);
							pMail.setEmpFname(tatleadProfile.getFirstName()+""+tatleadProfile.getLastName());
							pMail.setFileSources(null);
							mailGenerator.invoker(pMail);
							Inbox in = new Inbox();
							in = insertInboxManual(in, cReq.getEsrqmId(), cReqPk.getId(), cReq.getSummary(), cReq.getStatus(), pMail, single.getId());
							inboxDao.insert(in);
						}
					} else{
						CandidateReq cReqUp = cReqDao.findByDynamicWhere("CANDIDATE_ID = ? ORDER BY CREATEDATETIME DESC", new Object[] { new Integer(cSaveForm.getCandidateId()) })[0];
						cReqUp.setId(0);
						cReqUp.setCreatedatetime(null);
						if (cSaveForm.getApproveOffer() == 1){
							cReqUp.setStatus(Status.OFFERAPPROVED);
							// cReqUp.setSummary("Re:- Offer Approved");
							cReqUp.setSummary("Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
							cReqUp.setAssignedTo(cReqUp.getAssignedTo());
							candidate2.setStatus(7);
							pMail.setIssueReqStatus("  Approved ");
							pMail.setMailSubject("Diksha: Offer Approved : Candidate " + candidateNameId);
							/**
							 * If approved enter update candidate data for profile
							 * and personal and deleted existing salary stack and
							 * insert new
							 */
							perId = candidate2.getPersonalId();
							proId = candidate2.getProfileId();
							candidate2.setPersonalId(candidate2.getPerApprovalId());
							candidate2.setProfileId(candidate2.getProApprovalId());
							candidate2.setPerApprovalId(0);
							candidate2.setProApprovalId(0);
							
						//Testing part reporting time	
							ProfileInfoPk pk=new ProfileInfoPk();
							ProfileInfo tempProfile = profileInfoDao.findByPrimaryKey(candidate2.getProfileId());
				//			pk.setId(candidate2.getProfileId());
							pk.setId(candidProfile.getId());
							tempProfile.setReportingTime(tempProfile.getReportingTime());
							profileInfoDao.update(pk, candidProfile);
							
							
							
							
							approvedSalary = setApprovedSalary(candidate2);
							salaryinfo = setApprovedSalaryInfo(candidate2);
						} else if (cSaveForm.getRejectOffer() == 1){
							cReqUp.setAssignedTo(cReqUp.getAssignedTo());
							cReqUp.setStatus(Status.OFFERNOTAPPROVED);
							// cReqUp.setSummary("Re:- Offer Not Approved");
							cReqUp.setSummary("Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
							candidate2.setStatus(8);
							cReqUp.setActionTaken(Status.AT_REJECTED);
							approvedSalary = false;
							salaryinfo = false;
							pMail.setIssueReqStatus(" not Approved ");
							pMail.setMailSubject("Diksha: Offer Not Approved : Candidate " + candidateNameId);
						}
						CandidatePk cPk = new CandidatePk();
						cPk.setId(candidateReqPrev.getCandidateId());
						candidateDao.update(cPk, candidate2);
						/**
						 * Send email notification also to approver
						 */
						Users assignedToUser = usersDao.findByPrimaryKey(cReqUp.getAssignedTo());
						profileInfo = profileInfoDao.findByPrimaryKey(assignedToUser.getProfileId());
						//
						//
						// pMail.setCandidateName(candidProfile.getFirstName());
						// pMail.setEmpFname(profileInfo.getFirstName());
						// pMail.setTemplateName(MailSettings.OFFER_APPROVAL_NOTIFICATION);
						// pMail = DtoToBeanConverter.DtoToBeanConverter(pMail,
						// candidate, candidProfile, candidpersonal, null, true);
						// pMail.setRecipientMailId(profileInfo.getOfficalEmailId());
						// bodyText =
						// mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.OFFER_APPROVAL_NOTIFICATION),
						// pMail);
						// pMail.setMailBody(bodyText);
						// pMail.setEmpFname(profileInfo.getFirstName());
						// pMail.setFileSources(null);
						// mailGenerator.invoker(pMail);
						//
						//
						//
						// cReqUp.setMessageBody(pMail.getMailBody());
						// cReqPk = cReqDao.insert(cReqUp);
						//
						// inbox =
						// inboxModel.sendToDockingStation(cReqUp.getEsrqmId(),
						// cReqPk.getId(), cReqUp.getSummary(), cReqUp.getStatus());
						// inboxModel.notify(cReq1.getEsrqmId(), inbox);
						/**
						 * Send email to raised by
						 */
						pMail.setMailSubject("Diksha: Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
						pMail.setDate(PortalUtility.formatDateddMMyyyy(candidate2.getDateOfOffer()));// Date
																										// of
																										// offer
						pMail.setHrSPOC(tatProfile2.getFirstName()+""+tatProfile2.getLastName());// tat name
						pMail.setAmount(CTC2);// CTC
						pMail.setLeaveType(levelC.getLabel());// level label
						pMail.setRecipientMailId(raisedByUserprofileInfo.getOfficalEmailId());
						pMail.setCandidateName(candidProfile.getFirstName()+""+candidProfile.getLastName());
						pMail.setEmpFname(raisedByUserprofileInfo.getFirstName()+""+raisedByUserprofileInfo.getLastName());
						pMail.setEmpLName(profileInfo.getFirstName()+""+profileInfo.getLastName());
						pMail.setTemplateName(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR);
						pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, candidProfile, candidpersonal, null, true);
						pMail.setRecipientMailId(raisedByUserprofileInfo.getOfficalEmailId());
						bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR), pMail);
						pMail.setEmpFname(raisedByUserprofileInfo.getFirstName()+""+raisedByUserprofileInfo.getLastName());
						pMail.setMailBody(bodyText);
						pMail.setFileSources(null);
						mailGenerator.invoker(pMail);
						cReqUp.setMessageBody(pMail.getMailBody());
						cReqPk = cReqDao.insert(cReqUp);
						Inbox inbox2 = new Inbox();
						cReqUp.setMessageBody(pMail.getMailBody());
						inbox2 = insertInbox(inbox2, cReqUp.getEsrqmId(), cReqPk.getId(), cReqUp.getSummary(), cReqUp.getStatus(), pMail);
						inboxDao.insert(inbox2);
						/**
						 * Send email and to TAT LEad
						 */
						// notify TAT LEAD used insertInboxManual
						Users tatLeads[] = UserModel.getUsersIDByLevelId(tatLead1, null);
						if (tatLeads != null && tatLeads.length > 0) for (Users single : tatLeads){
							ProfileInfo tatleadProfile = profileInfoDao.findByPrimaryKey(single.getProfileId());
							pMail.setMailSubject("Diksha: Offer Resend " + eReqMap.getReqId() + " - " + candidProfile.getFirstName()+""+candidProfile.getLastName());
							pMail.setDate(PortalUtility.formatDateddMMyyyy(candidate2.getDateOfOffer()));// Date
																											// of
																											// offer
							pMail.setHrSPOC(tatProfile2.getFirstName()+""+tatProfile2.getLastName());// tat
																		// name
							pMail.setAmount(CTC2);// CTC
							pMail.setLeaveType(levelC.getLabel());// level label
							pMail.setTemplateName(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR);
							pMail.setRecipientMailId(tatleadProfile.getOfficalEmailId());
							pMail.setCandidateName(candidProfile.getFirstName()+""+candidProfile.getLastName());
							pMail.setEmpFname(tatleadProfile.getFirstName()+""+tatleadProfile.getLastName());
							pMail.setEmpLName(profileInfo.getFirstName()+""+profileInfo.getLastName());
							pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, candidProfile, candidpersonal, null, true);
							pMail.setRecipientMailId(tatleadProfile.getOfficalEmailId());
							bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR), pMail);
							pMail.setMailBody(bodyText);
							pMail.setEmpFname(tatleadProfile.getFirstName()+""+tatleadProfile.getLastName());
							pMail.setFileSources(null);
							mailGenerator.invoker(pMail);
							Inbox in = new Inbox();
							in = insertInboxManual(in, cReqUp.getEsrqmId(), cReqPk.getId(), cReqUp.getSummary(), cReqUp.getStatus(), pMail, single.getId());
							inboxDao.insert(in);
						}
					}
					/**
					 * set isDeleted=1 for inbox entry of approver to remove it from
					 * my tasks
					 */
					// fetch logged in persons inbox entry
					Inbox approverInbox[] = inboxDao.findByDynamicWhere("RECEIVER_ID=? AND ASSIGNED_TO=? AND ESR_MAP_ID=? ORDER BY ID DESC", new Object[] { login.getUserId(), login.getUserId(), cReq1.getEsrqmId() });
					approverInbox[0].setIsDeleted(1);
					inboxDao.update(new InboxPk(approverInbox[0].getId()), approverInbox[0]);
					/**
					 * delete old profile and personal info
					 */
					if (approvedSalary && salaryinfo){
						ProfileInfo ProfileDelete = new ProfileInfo();
						ProfileInfoModel profileInfoModel2 = new ProfileInfoModel();
						ProfileDelete.setId(proId);
						profileInfoModel2.delete(ProfileDelete, request);
						PersonalInfo PersonalDelete = new PersonalInfo();
						PersonalInfoModel personalInfoModel2 = new PersonalInfoModel();
						PersonalDelete.setId(perId);
						personalInfoModel2.delete(PersonalDelete, request);
					} else{
						/**
						 * delete mirror copy if rejected
						 */
						ProfileInfo ProfileDelete = new ProfileInfo();
						ProfileInfoModel profileInfoModel2 = new ProfileInfoModel();
						if (candidate2.getProApprovalId() > 0){
							ProfileDelete.setId(candidate2.getProApprovalId());
							profileInfoModel2.delete(ProfileDelete, request);
						}
						candidate2.setProApprovalId(0);
						PersonalInfo PersonalDelete = new PersonalInfo();
						PersonalInfoModel personalInfoModel2 = new PersonalInfoModel();
						if (candidate2.getPerApprovalId() > 0){
							PersonalDelete.setId(candidate2.getPerApprovalId());
							personalInfoModel2.delete(PersonalDelete, request);
						}
						candidate2.setPerApprovalId(0);
						candidatePk.setId(candidate2.getId());
						candidateDao.update(candidatePk, candidate2);
						// delete Salary for approval also
						SalaryDetailsDao salaryDetailsReplica = SalaryDetailsDaoFactory.createReplica();
						SalaryDetails salDetailsToDelete[] = salaryDetailsReplica.findWhereCandidateIdEquals(candidate2.getId());
						if (salDetailsToDelete != null && salDetailsToDelete.length > 0) for (SalaryDetails singleSal : salDetailsToDelete){
							SalaryDetailsPk salaryDetailsPk = new SalaryDetailsPk();
							salaryDetailsPk.setId(singleSal.getId());
							salaryDetailsReplica.delete(salaryDetailsPk);
						}
						// change isdeleted to 0
						//approverInbox[0].setIsDeleted(0);
						//inboxDao.update(new InboxPk(approverInbox[0].getId()), approverInbox[0]);
						inboxDao.delete(approverInbox[0].createPk());
					}
					break;
				case NOSHOW:
					try{
						CandidateSaveForm updateNoshowcomemnt = (CandidateSaveForm) form;
						CandidateReqDao reqDao = CandidateReqDaoFactory.create();
						Login login4 = loginDao.findWhereCandidateIdEquals(updateNoshowcomemnt.getCandidateId());
						candidate = candidateDao.findByPrimaryKey(updateNoshowcomemnt.getCandidateId());
						ProfileInfo profileInfo4 = profileInfoDao.findByPrimaryKey(candidate.getProfileId());
						PersonalInfo personalInfo4 = personalInfoDao.findByPrimaryKey(candidate.getPersonalId());
						// candidate level
						Levels levelCand = LevelsDaoFactory.create().findByPrimaryKey(profileInfo4.getLevelId());
						// candidates reporting manager
						Users rmuser = usersDao.findByPrimaryKey(profileInfo4.getReportingMgr());
						ProfileInfo rmprofile = profileInfoDao.findByPrimaryKey(rmuser.getProfileId());
						ProfileInfo hrProfile = null;
						String hrSpoc = "";
						// hrspoc of candidate
						Users hrspoc = null;
						if (profileInfo4.getHrSpoc() > 0){
							hrspoc = usersDao.findByPrimaryKey(profileInfo4.getHrSpoc());
							hrProfile = profileInfoDao.findByPrimaryKey(hrspoc.getProfileId());
							hrSpoc = hrProfile.getFirstName();
						} else{
							hrSpoc = "N.A";
						}
						CandidateReq candidateReqData = reqDao.findByDynamicSelect("SELECT * FROM CANDIDATE_REQ WHERE STATUS='Accepted' AND CANDIDATE_ID=?", new Object[] { new Integer(updateNoshowcomemnt.getCandidateId()) })[0];
						CandidateReq insertUpdateData = new CandidateReq();
						insertUpdateData.setEsrqmId(candidateReqData.getEsrqmId());
						insertUpdateData.setComments(updateNoshowcomemnt.getComments());
						insertUpdateData.setStatus(Status.NO_SHOW);
						insertUpdateData.setAssignedTo(candidateReqData.getAssignedTo());
						insertUpdateData.setRaisedBy(candidateReqData.getRaisedBy());
						insertUpdateData.setSummary("No Show - " + profileInfo4.getFirstName());
						insertUpdateData.setCandidateId(candidateReqData.getCandidateId());
						insertUpdateData.setReServe(candidateReqData.getReServe());
						insertUpdateData.setOfferLetter(candidateReqData.getOfferLetter());
						insertUpdateData.setCreatedatetime(candidateReqData.getCreatedatetime());
						insertUpdateData.setCycle(candidateReqData.getCycle());
						// sending mail Alerts
						pMail.setMailSubject("Diksha -" + profileInfo4.getFirstName()+ "- No Show - Your offer has been Cancelled");
						pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate, profileInfo4, personalInfo4, login4, false);
						if (rmprofile != null) pMail.setRepoMngrAtClient(rmprofile.getFirstName());
						pMail.setHrSPOC(hrSpoc);
						pMail.setLeaveType(levelCand.getLabel());
						pMail.setTemplateName(MailSettings.CANDIDATE_NO_SHOW);
						pMail.setFileSources(null);
						// mailGenerator.invoker(pMail);
						// candidate's level id
						Regions regions = RegionsDaoFactory.create().findByLevelId(profileInfo4.getLevelId());
						Notification notification = new Notification(regions.getRefAbbreviation());
						Set<String> emailIds = notification.notifyDept(candidate, 0, 0);
						// pMail.setRecipientMailId(rmprofile[0].getOfficalEmailId());
						if (rmprofile != null) emailIds.add(rmprofile.getOfficalEmailId());
						// spoc
						if (hrProfile != null) emailIds.add(hrProfile.getOfficalEmailId());
						// tat id
						Users tatID = usersDao.findByPrimaryKey(candidate.getTatId());
						ProfileInfo tatProfile = profileInfoDao.findByPrimaryKey(tatID.getProfileId());
						emailIds.add(tatProfile.getOfficalEmailId());
						List<String> TatLeadEmail = UserModel.getUsersByLevelId(notification.tatLeadLevelId, null);
						String[] allReceipientMailId = emailIds.toArray(new String[0]);
						pMail.setAllReceipientcCMailId(allReceipientMailId);
						pMail.setAllReceipientMailId(TatLeadEmail.toArray(new String[0]));// to
																							// TAT
																							// Lead
						pMail.setFileSources(null);
						mailGenerator.invoker(pMail);
						bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.CANDIDATE_NO_SHOW), pMail);
						insertUpdateData.setMessageBody(bodyText);
						pMail.setMailBody(bodyText);
						CandidateReqPk pk = reqDao.insert(insertUpdateData);
						candidate.setStatus(9);
						candidateDao.update(new CandidatePk(candidate.getId()), candidate);
						inbox = inboxModel.sendToDockingStation(insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), "No Show");
						Inbox inbox4 = new Inbox();
						inboxDao = InboxDaoFactory.create();
						// notify RM used insertInboxManual
						if (rmuser != null && rmuser.getId() > 0){
							inbox4 = insertInboxManual(inbox4, insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), insertUpdateData.getStatus(), pMail, rmuser.getId());
							inboxDao.insert(inbox4);
						}
						// notify SPOC used insertInboxManual
						if (hrspoc != null && hrspoc.getId() > 0){
							inbox4 = insertInboxManual(inbox4, insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), insertUpdateData.getStatus(), pMail, hrspoc.getId());
							inboxDao.insert(inbox4);
						}
						// notify TAT used insertInboxManual
						if (tatID.getId() != insertUpdateData.getAssignedTo()){
							inbox4 = insertInboxManual(inbox4, insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), insertUpdateData.getStatus(), pMail, tatID.getId());
							inboxDao.insert(inbox4);
						}
						// notify TAT LEAD used insertInboxManual
						Users tatLeadU[] = UserModel.getUsersIDByLevelId(notification.tatLeadLevelId, null);
						if (tatLeadU != null && tatLeadU.length > 0) for (Users single : tatLeadU){
							inbox4 = insertInboxManual(inbox4, insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), insertUpdateData.getStatus(), pMail, single.getId());
							inboxDao.insert(inbox4);
						}
						// notify itdept insertInboxManual
						Users ITUsers[] = UserModel.getUsersIDByDivisionId(notification.itAdminId, null);
						if (ITUsers != null && ITUsers.length > 0) for (Users single : ITUsers){
							inbox4 = insertInboxManual(inbox4, insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), insertUpdateData.getStatus(), pMail, single.getId());
							inboxDao.insert(inbox4);
						}
						// notify finance insertInboxManual
						Users financeUsers[] = UserModel.getUsersIDByDivisionId(notification.financeId, null);
						if (financeUsers != null && financeUsers.length > 0) for (Users single : financeUsers){
							inbox3 = insertInboxManual(inbox4, insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), insertUpdateData.getStatus(), pMail, single.getId());
							inboxDao.insert(inbox3);
						}
						// notify RMG Manager insertInboxManual
						Users RMGUsers[] = UserModel.getUsersIDByLevelId(notification.rmgManagerLevelId, null);
						if (RMGUsers != null && RMGUsers.length > 0) for (Users single : RMGUsers){
							inbox3 = insertInboxManual(inbox4, insertUpdateData.getEsrqmId(), pk.getId(), insertUpdateData.getSummary(), insertUpdateData.getStatus(), pMail, single.getId());
							inboxDao.insert(inbox3);
						}
					} catch (Exception e){
						logger.info("Failed to send mail No Show Info");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
						e.printStackTrace();
					}
					break;
				case UPDATECANDIDATECHKLST:
					try{
						Candidate candidateForm = (Candidate) form;//send id
						CandidateChecklistStatusDao chklstStatusDao = CandidateChecklistStatusDaoFactory.create();
						CandidateChecklistStatus fetchedChklstStatus = null;
						//boolean informCandidate = false;
						//ex : 18~#~rejected~#~pancard number not scanned properly						
						for (String eachRow : candidateForm.getChklstChanges()){
							String[] chklstData = eachRow.split("~#~");
							int chklstStatusId = Integer.parseInt(chklstData[0]);
							fetchedChklstStatus = chklstStatusDao.findByPrimaryKey(chklstStatusId);
							fetchedChklstStatus.setStatus(chklstData[1]);
							if (chklstData.length > 2){
								fetchedChklstStatus.setComments(chklstData[2]);
							} else{
								fetchedChklstStatus.setComments(null);
							}
							if (fetchedChklstStatus.getStatus().equalsIgnoreCase("APPROVED")){
								fetchedChklstStatus.setIsUploaded(1);
							} else{
								//informCandidate = true;
								fetchedChklstStatus.setIsUploaded(0);
								DocumentsDao documentsDao = DocumentsDaoFactory.create();
								documentsDao.delete(new DocumentsPk(fetchedChklstStatus.getDocId()));
								fetchedChklstStatus.setDocId(0);
							}
							chklstStatusDao.update(new CandidateChecklistStatusPk(fetchedChklstStatus.getId()), fetchedChklstStatus);
						}
						//if(informCandidate){
						/*
						 * send mail to candidate saying something has been modified and he must have a look
						 * mail will be sent to candidate's personal mail-id if he is still a candidate
						 * else
						 * mail will be sent to his official mail-id
						 */
						String candidateMailId = null;
						Candidate candidateDto = candidateDao.findByPrimaryKey(candidateForm.getCandidateId());
						ProfileInfo candidateProfileInfo = ProfileInfoDaoFactory.create().findByPrimaryKey(candidateDto.getProfileId());
						if (candidateDto.getIsEmployee() > 0){
							candidateMailId = candidateProfileInfo.getOfficalEmailId();
						} else{
							PersonalInfo candidatePersonalInfo = PersonalInfoDaoFactory.create().findByPrimaryKey(candidateDto.getPersonalId());
							candidateMailId = candidatePersonalInfo.getPersonalEmailId();
						}
						PortalMail portalMail = new PortalMail();
						portalMail.setCandidateName(candidateProfileInfo.getFirstName() + " " + candidateProfileInfo.getLastName());
						portalMail.setMailSubject("Diksha: Checklist status updated");
						portalMail.setTemplateName(MailSettings.CANDIDATE_CHKLST_REJECTED);
						mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
						String[] mailIds = new String[] { candidateMailId };
						portalMail.setAllReceipientMailId(mailIds);
						mailGenerator.invoker(portalMail);
						//}						
					} catch (Exception ex){
						logger.info("Exception :  unable to update the candidate status");
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.candidate.checklist.update.failed"));
						ex.printStackTrace();
					}
					break;
			}
		} catch (Exception e){
			logger.info("Failed to update Candidate Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	public boolean sendReportingDetails(ProfileInfo candProfileInfo, Candidate cand) {
		boolean flag = true;
		try{
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			Users tatUser = UsersDaoFactory.create().findByPrimaryKey(cand.getTatId());
			ProfileInfo profileTAT = profileInfoDao.findByPrimaryKey(tatUser.getProfileId());
			MailGenerator mailGenerator = new MailGenerator();
			PersonalInfo personalInfo = new PersonalInfo();
			PortalMail pMail = new PortalMail();
			PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
			personalInfo = personalInfoDao.findByPrimaryKey(cand.getPersonalId());
			pMail.setMailSubject("Diksha: Reporting Details");
			pMail.setCandidateName(candProfileInfo.getFirstName()+""+candProfileInfo.getLastName());
			pMail.setRecipientMailId(personalInfo.getPersonalEmailId());
			pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(candProfileInfo.getDateOfJoining()));
			pMail.setRepoMngrAtClient(candProfileInfo.getReportingAddressNormal());// reporting
																					// address
			pMail.setReportingTm(candProfileInfo.getReportingTime());// reporting
			
																		// time
			pMail.setEmpFname(profileTAT.getFirstName()+""+profileTAT.getLastName());// name of contat is
															// tat
			pMail.setTemplateName(MailSettings.CANDIDATE_REPORTING_DETAILS);
			mailGenerator.invoker(pMail);
		} catch (Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	private SalaryInfoBean setSalaryInfoBean(SalaryInfo salaryInfo) {
		SalaryInfoBean salaryInfoBean = new SalaryInfoBean();
		salaryInfoBean.setId(salaryInfo.getId());
		salaryInfoBean.setRelocationBonus(salaryInfo.getRelocationBonus()==null||salaryInfo.getRelocationBonus().trim().equalsIgnoreCase("")||salaryInfo.getRelocationBonus()=="0" ?"": salaryInfo.getRelocationBonus());
		salaryInfoBean.setJoiningBonusString((salaryInfo.getJoiningBonusString()==null||salaryInfo.getJoiningBonusString().equalsIgnoreCase("")) ? null : salaryInfo.getJoiningBonusString());
		salaryInfoBean.setPerdiemString(salaryInfo.getPerdiemString());
		//salaryInfoBean.setBasic(salaryInfo.getBasic());// Candiate ID
		//salaryInfoBean.setHra(salaryInfo.getHra());// User id
		salaryInfoBean.setJoiningBonusAmount((salaryInfo.getJoiningBonusAmount()==null||salaryInfo.getJoiningBonusAmount().trim().equalsIgnoreCase("")||salaryInfo.getJoiningBonusAmount()=="0")?"":salaryInfo.getJoiningBonusAmount());
		salaryInfoBean.setRetentionBonus((salaryInfo.getRetentionBonus()==null ||salaryInfo.getRetentionBonus().trim().equalsIgnoreCase("")||salaryInfo.getRetentionBonus()=="0")?"" :salaryInfo.getRetentionBonus());
		salaryInfoBean.setRetentionInstallments((salaryInfo.getRetentionInstallments()==null ||salaryInfo.getRetentionInstallments().trim().equalsIgnoreCase("")||salaryInfo.getRetentionInstallments()=="0")?"" :salaryInfo.getRetentionInstallments());
		salaryInfoBean.setPerdiemOffered((salaryInfo.getPerdiemOffered()==null ||salaryInfo.getPerdiemOffered().trim().equalsIgnoreCase("")||salaryInfo.getPerdiemOffered()=="0")?"" :salaryInfo.getPerdiemOffered());
		salaryInfoBean.setRelocationCity((salaryInfo.getRelocationCity()==null ||salaryInfo.getRelocationCity().trim().equalsIgnoreCase(""))?"" :salaryInfo.getRelocationCity());
		salaryInfoBean.setPerdiemType(salaryInfo.getPerdiemType());
		return salaryInfoBean;
	}

	public String getAddressFields(Address address) {
		String add = new String();
		add = address.getAddress();
		add = add + ",<br/>" + address.getCity();
		add = add + ",<br/>" + address.getState();
		add = add + ",<br/>" + address.getZipcode();
		add = add + ",<br/>" + address.getCountry();
		return add;
	}

	public Inbox insertInbox(Inbox inbox, int esrMapId, int reqId, String subject, String status, PortalMail pMail) {
		CandidateReqDao canReqqDao = CandidateReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM CANDIDATE_REQ WHERE ID=? AND ESRQM_ID=?";
			CandidateReq canReq = canReqqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(canReq.getAssignedTo());
			inbox.setRaisedBy(canReq.getRaisedBy());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("CANDIDATE");
			inbox.setEsrMapId(esrMapId);
			inbox.setComments(canReq.getComments());
			inbox.setMessageBody(pMail.getMailBody());
			inbox.setReceiverId(canReq.getRaisedBy());
			return inbox;
		} catch (CandidateReqDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Inbox insertInboxManual(Inbox inbox, int esrMapId, int reqId, String subject, String status, PortalMail pMail, int assignedTo) {
		CandidateReqDao canReqqDao = CandidateReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM CANDIDATE_REQ WHERE ID=? AND ESRQM_ID=?";
			CandidateReq canReq = canReqqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(canReq.getAssignedTo());
			inbox.setRaisedBy(canReq.getRaisedBy());
			inbox.setStatus(status);
			inbox.setCreationDatetime(new Date());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("CANDIDATE");
			inbox.setEsrMapId(esrMapId);
			inbox.setComments(canReq.getComments());
			inbox.setMessageBody(pMail.getMailBody());
			inbox.setReceiverId(assignedTo);
			return inbox;
		} catch (CandidateReqDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/*private PortalMail sendEmail(PortalMail pMail, Candidate candidate, ProfileInfo raisedByUserprofileInfo, ProfileInfo candidProfile, ProfileInfo assignedToprofileInfo, PersonalInfo candidpersonal) {
		try{
			// MailGenerator mailGenerator= new MailGenerator();
			// pMail.setRecipientMailId(raisedByUserprofileInfo.getOfficalEmailId());
			// pMail.setCandidateName(candidProfile.getFirstName());
			// pMail.setEmpFname(raisedByUserprofileInfo.getFirstName());
			// pMail.setEmpLName(assignedToprofileInfo.getFirstName());
			// pMail.setTemplateName(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR);
			// pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, candidate,
			// candidProfile, candidpersonal, null, true);
			// String bodyText =
			// mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.OFFER_APPROVAL_NOTIFICATION_FOR_REQUESTOR),
			// pMail);
			// pMail.setMailBody(bodyText);
			// pMail.setEmpFname(raisedByUserprofileInfo.getFirstName());
			// pMail.setFileSources(null);
			// mailGenerator.invoker(pMail);
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	private void updateActionBy(int esrqmId, int actionBy, int actionTaken, String comments) {
		CandidateReqDao cReqDao = CandidateReqDaoFactory.create();
		try{
			Object[] sqlParams = { esrqmId, actionBy };
			String SQL = "ESRQM_ID = ? AND ASSIGNED_TO = ? AND SERVED IS NOT NULL AND STATUS = 'Pending Approval' ORDER BY CREATEDATETIME DESC";
			CandidateReq[] candidateReq = cReqDao.findByDynamicWhere(SQL, sqlParams);
			if (candidateReq.length > 0){
				CandidateReqPk cReqPk = new CandidateReqPk();
				cReqPk.setId(candidateReq[0].getId());
				candidateReq[0].setComments(comments);
				candidateReq[0].setActionTaken(actionTaken);
				cReqDao.update(cReqPk, candidateReq[0]);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public boolean setApprovedSalary(Candidate candidateForSalary) {
		SalaryDetailsDao salaryDetailsReplica = SalaryDetailsDaoFactory.createReplica();
		int id = candidateForSalary.getId();
		boolean flag = true;
		SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
		SalaryDetails salaryApproved[] = null;
		try{
			SalaryDetails salDetailsToDelete[] = salaryDetailsDao.findWhereCandidateIdEquals(id);
			if (salDetailsToDelete != null && salDetailsToDelete.length > 0) for (SalaryDetails singleSal : salDetailsToDelete){
				SalaryDetailsPk salaryDetailsPk = new SalaryDetailsPk();
				salaryDetailsPk.setId(singleSal.getId());
				salaryDetailsDao.delete(salaryDetailsPk);
			}
			/**
			 * Insert new salary stack
			 */
			if (candidateForSalary.getId() > 0){
				salaryApproved = salaryDetailsReplica.findWhereCandidateIdEquals(candidateForSalary.getId());
				if (salaryApproved != null && salaryApproved.length > 0) for (SalaryDetails singleSal : salaryApproved){
					singleSal.setId(0);
					salaryDetailsDao.insert(singleSal);
				}
			}
		} catch (SalaryDetailsDaoException e){
			flag = false;
			e.printStackTrace();
		} finally{
			if (flag){
				try{
					salaryApproved = salaryDetailsReplica.findWhereCandidateIdEquals(candidateForSalary.getId());
					if (salaryApproved != null && salaryApproved.length > 0) for (SalaryDetails singleSal : salaryApproved){
						SalaryDetailsPk salaryDetailsPk = new SalaryDetailsPk();
						salaryDetailsPk.setId(singleSal.getId());
						salaryDetailsReplica.delete(salaryDetailsPk);
					}
				} catch (SalaryDetailsDaoException e){
					flag = false;
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public boolean setApprovedSalaryInfo(Candidate candidateForSalaryInfo) {
		SalaryInfoDao salaryInfoReplica = SalaryInfoDaoFactory.createReplica();
		int id = candidateForSalaryInfo.getId();
		boolean flag = true;
		SalaryInfoDao salaryInfoDao = SalaryInfoDaoFactory.create();
		SalaryInfo salaryApproved[] = null;
		try{
			SalaryInfo salDetailsToDelete[] = salaryInfoDao.findWhereBasicEquals(id);
			if (salDetailsToDelete != null && salDetailsToDelete.length > 0) for (SalaryInfo singleSal : salDetailsToDelete){
				SalaryInfoPk salaryInfoPk = new SalaryInfoPk();
				salaryInfoPk.setId(singleSal.getId());
				salaryInfoDao.delete(salaryInfoPk);
			}
			/**
			 * Insert new salary stack
			 */
			if (candidateForSalaryInfo.getId() > 0){
				salaryApproved = salaryInfoReplica.findWhereBasicEquals(candidateForSalaryInfo.getId());
				if (salaryApproved != null && salaryApproved.length > 0) for (SalaryInfo singleSal : salaryApproved){
					singleSal.setId(0);
					salaryInfoDao.insert(singleSal);
				}
			}
		} catch (SalaryInfoDaoException e){
			flag = false;
			e.printStackTrace();
		} finally{
			if (flag){
				try{
					salaryApproved = salaryInfoReplica.findWhereBasicEquals(candidateForSalaryInfo.getId());
					if (salaryApproved != null && salaryApproved.length > 0) for (SalaryInfo singleSal : salaryApproved){
						SalaryInfoPk salaryInfoPk = new SalaryInfoPk();
						salaryInfoPk.setId(singleSal.getId());
						salaryInfoReplica.delete(salaryInfoPk);
					}
				} catch (SalaryInfoDaoException e){
					flag = false;
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * @param levelId
	 *            not using now
	 * @param temp
	 *            return template name depending on pending approval or approved
	 *            offer from JTemplates where its an interface with template
	 *            names
	 * @return
	 */
	private String getTemplate(int levelId, boolean temp) {
		String template = null;
		//DivisonDao divisonDao = DivisonDaoFactory.create();
		//RegionsDao rDao = RegionsDaoFactory.create();
		try{
			template = temp ? JTemplates.OFFER_LETTER:JTemplates.OFFER_LETTER;
			// Object[ ] sqlParams =
			// { levelId };
			// Divison divison =
			// divisonDao.findByDynamicWhere("ID = (SELECT DIVISION_ID FROM LEVELS L WHERE L.ID = ?)",
			// sqlParams)[0];
			//
			// if (divison.getParentId() > 0)
			// {
			// divison = divisonDao.findByPrimaryKey(divison.getParentId());
			// }
			//
			// Regions regions = rDao.findByPrimaryKey(divison.getRegionId());
			// Notification notification = new
			// Notification(regions.getRefAbbreviation());
			//
			// if (notification.consultingId == divison.getId())
			// {
			// template =
			// temp?JTemplates.TEMP_OFFER_LETTER:JTemplates.OFFER_LETTER;
			// }
			// else
			// {
			// template =
			// temp?JTemplates.TEMP_OFFER_LETTER:JTemplates.OFFER_LETTER;
			// }
		} catch (Exception e){
			e.printStackTrace();
		}
		return template;
	}

	public ActionResult enabledisable(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Candidate candidate = (Candidate) form;
		CandidateDao candidateDao = CandidateDaoFactory.create();
		CandidatePk candidatePk = new CandidatePk();
		candidatePk.setId(candidate.getId());
		try{
			candidateDao.update(candidatePk, candidate);
		} catch (Exception e){
			logger.info("Failed to update Candidate Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	static Integer	count	= 0;

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		boolean isUpload = true;
		Integer fieldsId[] = new Integer[fileItems.size()];
		int i = 0;
		for (FileItem fileItem2 : fileItems){
			logger.info("Inside candidate model.");
			logger.info("FileName: " + fileItem2.getName());
			logger.info("FileType: " + fileItem2.getContentType());
			logger.info("FileSize: " + fileItem2.getSize());
			String[] temp = fileItem2.getName().split("\\.");
			logger.info("FileExtension: " + temp[temp.length - 1]);
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			try{
				String fileName = portalData.saveFile(fileItem2, dTypes, id);
				String DBFilename = fileName;
				if (fileName != null){
					/**
					 * save fileName in dataBase
					 */
					Documents documents = new Documents();
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					documents.setFilename(DBFilename);
					documents.setDoctype(docType);
					documents.setDescriptions(description);
					try{
						DocumentsPk documentsPk = documentsDao.insert(documents);
						fieldsId[i] = documentsPk.getId();
						
						Integer chklstStatusId = (Integer) request.getAttribute("chklstStatusId");
						if(chklstStatusId!=null){
						CandidateChecklistStatusDao chklstStatusDao = CandidateChecklistStatusDaoFactory.create();
						CandidateChecklistStatus chklstStatus = chklstStatusDao.findByPrimaryKey(chklstStatusId);
						chklstStatus.setDocId(documentsPk.getId());
						chklstStatus.setIsUploaded(1);
						chklstStatus.setStatus("Uploaded");
						chklstStatusDao.update(new CandidateChecklistStatusPk(chklstStatus.getId()), chklstStatus);}
					} catch (DocumentsDaoException e){
						e.printStackTrace();
					} catch (CandidateChecklistStatusDaoException ex){
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					i++;
				} else{
					isUpload = false;
				}
			} catch (FileNotFoundException e){
				isUpload = false;
				e.printStackTrace();
			}
		}
		if (isUpload){
			logger.info("File uploaded successfully.");
		} else{
			logger.info("File uploaded failed.");
		}
		// request.getSession(false).setAttribute("fileId",fieldsId);
		return fieldsId;
	}

	@Override
	public Attachements download(PortalForm form) {
		/*Attachements attachements = new Attachements();
		Candidate candidateForm = (Candidate) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		path = PropertyLoader.getEnvVariable() + seprator + path;
		*//**
			* Get filename from id
			*/
		/*
		PortalData portalData = new PortalData();
		path = portalData.getfolder(path);
		try{
		CandidateChecklistStatusDao chklstStatusDao = CandidateChecklistStatusDaoFactory.create();
		CandidateChecklistStatus chklstStatusDto = chklstStatusDao.findByPrimaryKey(candidateForm.getId());
		StringBuilder candidateFolderName = new StringBuilder("cand");
		candidateFolderName.append("_").append(chklstStatusDto.getCandId()).append("_");
		CandidateDao candidateDao = CandidateDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		Candidate candidateDto = candidateDao.findByPrimaryKey(chklstStatusDto.getCandId());
		ProfileInfo candidateProfileInfo = profileInfoDao.findByPrimaryKey(candidateDto.getProfileId());
		candidateFolderName.append(candidateProfileInfo.getFirstName()).append("_").append(candidateProfileInfo.getLastName());
		DocumentsDao documentsDao = DocumentsDaoFactory.create();
		Documents docNew = new Documents();
		docNew = documentsDao.findByPrimaryKey(chklstStatusDto.getDocId());
		attachements.setFileName(docNew.getFilename());
		attachements.setFilePath(path + seprator + candidateFolderName + seprator + docNew.getFilename());
		} catch (DocumentsDaoException ex){
		ex.printStackTrace();
		} catch (ProfileInfoDaoException ex){
		ex.printStackTrace();
		} catch (CandidateDaoException ex){
		ex.printStackTrace();
		} catch (CandidateChecklistStatusDaoException ex){
		ex.printStackTrace();
		}
		return attachements;*/
		//TODO needs add switch statment for candidate check list. as of now only reverted code to work other file uploads
		Attachements attachements = new Attachements();
		Candidate candidate = (Candidate) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		path = PropertyLoader.getEnvVariable() + seprator + path;
		PortalData portalData = new PortalData();
		path = portalData.getfolder(path);
		try{
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents docNew = new Documents();
			docNew = documentsDao.findByPrimaryKey(candidate.getDocid());
			attachements.setFileName(docNew.getFilename());
			attachements.setFilePath(path + seprator + docNew.getFilename());
		} catch (Exception e){
			e.printStackTrace();
		}
		return attachements;
	}

	/*public List<Integer> getNextProcId(String procChain, int esrMapId) {
		List<Integer> nextProcId = new ArrayList<Integer>();
		String[] temp = procChain.split("\\|");
		String[] userIds = temp[1].split(";");
		CandidateReqDao cReqDao = CandidateReqDaoFactory.create();
		CandidateReq[] candidateReqs;
		int index = 0;
		try{
			candidateReqs = cReqDao.findByDynamicSelect("SELECT * FROM CANDIDATE_REQ WHERE STATUS=? AND ESRQM_ID=?  AND RE_SERVE = 0", new Object[] { "Pending Approval", esrMapId });
			if (candidateReqs.length > 0){
				for (String usrId : userIds){
					boolean flag = false;
					for (String usId : usrId.split(",")){
						int uId = Integer.parseInt(usId);
						for (CandidateReq cReq : candidateReqs){
							if (cReq.getAssignedTo() == uId){
								flag = true;
							}
						}
						if (!flag){
							nextProcId.add(uId);
							break;
						}
					}
				}
			} else{
				for (String uIds : userIds[0].split(",")){
					nextProcId.add(Integer.parseInt(uIds));
					index++;
				}
			}
		} catch (CandidateReqDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextProcId;
	}*/
	/**
	 * @param candidateId
	 * @param abbrivation
	 *            name of file as in DTPLabc.pdf
	 * @param ext
	 *            type of preview expected eg: pdf for offerletter,etc
	 * @return complete file name
	 */
	private String generateFName(int candidateId, String abbrivation, ExportType ext) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHH");
		String date = format.format(new Date());
		String fileName = abbrivation + candidateId + date + "." + ext;
		return fileName;
	}

	/**
	 * @author supriya.bhike This methods deletes files from Document table if
	 *         upload is done but form data not saved
	 */
	@Override
	public ActionResult deleteFiles(PortalForm form) {
		ActionResult result = new ActionResult();
		DropDown dropDown = (DropDown) form;
		String files = (String) dropDown.getDetail();
		String fileIds[] = files.split(",");
		try{
			if (fileIds != null && fileIds.length > 0){
				DocumentsDao documentsDao = DocumentsDaoFactory.create();
				DocumentsPk documentsPk = new DocumentsPk();
				for (String id : fileIds){
					documentsPk.setId(Integer.parseInt(id));
					documentsDao.delete(documentsPk);
				}
			}
		} catch (DocumentsDaoException e){
			logger.info("Uploaded files deletion failed");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	protected HashMap<String, Object> getSalaryReportParams(int candidateId, boolean isPreview) {
		HashMap<String, Object> salaryParams = new HashMap<String, Object>();
		salaryParams.put("TABLE_NAME", isPreview ? "SALARY_FOR_APPROVAL" : "SALARY_DETAILS");
		salaryParams.put(SalaryDetailModel.ESCC_KEY, SalaryDetailModel.EXCLUDE_SALARY_CONFIG_CLAUSE);
		salaryParams.put(SalaryDetailModel.VSCC_KEY, SalaryDetailModel.VIEW_SALARY_CONFIG_CLAUSE);
		salaryParams.put(SalaryDetailModel.REPORT_LOCALE_KEY, SalaryDetailModel.IN_LOCALE);
		if (candidateId > 0){
			salaryParams.put("CANDIDATE_ID", candidateId);
			/*SalaryDetailModel sdm = new SalaryDetailModel();
			Salary candidateSalary = sdm.getSalary(candidateId, false, SalaryDetailModel.TYPE_NORMAL);
			float annualPerdiem = candidateSalary.getPerDiem();
			salaryParams.put("PERDIEM", annualPerdiem <= 0 ? 0 : 1);
			if (annualPerdiem > 0){
				salaryParams.put("PERDIEM", 1);
				salaryParams.put("DAILY_PERDIEM", annualPerdiem);
			} else{
				salaryParams.put("PERDIEM", 0);
			}*/
			// TODO: Write code to find LTA for given candidate
			//salaryParams.put("LTA", 0);
			//salaryParams.put("PLB", candidateSalary.getTblb() <= 0 ? 0 : 1);
			try{
				SalaryInfoDao salaryInfoProvider = SalaryInfoDaoFactory.create();
				SalaryInfo[] candidateSalInfo = salaryInfoProvider.findWhereBasicEquals(candidateId);
				String retentionBonus =null;
				String joiningBonus = null;
				String relocationBonus = null;
				// TODO: Retentions bonus in not stored so it will be default 0.
				// Update this code if retention bonus is captured.
				//retentionBonus = null;
				if (candidateSalInfo.length > 0){
					joiningBonus = candidateSalInfo[0].getJoiningBonusString();
					relocationBonus=candidateSalInfo[0].getRelocationBonus();
					if(relocationBonus!=null && relocationBonus.equalsIgnoreCase("")){
						relocationBonus=null;
					}
					/*if(joiningBonus.equalsIgnoreCase("")){
						joiningBonus=null;
					}*/
					
					if (!(retentionBonus != null && retentionBonus.equalsIgnoreCase(""))){
						salaryParams.put("RETENTIONBONUS", 1);
					} else{
					salaryParams.put("RETENTIONBONUS", 0);
					}
					if (!(joiningBonus != null && joiningBonus.equalsIgnoreCase(""))){
						salaryParams.put("JOININGBONUS", 1);
					} else
						salaryParams.put("JOININGBONUS", 0);
					
					try{
						if (!(relocationBonus!=null && relocationBonus.equalsIgnoreCase("")))
							salaryParams.put("RELOCATIONALLOWANCE", 1);
						else 
							salaryParams.put("RELOCATIONALLOWANCE",0);
					
					
					} catch (Exception e){
						salaryParams.put("RELOCATIONALLOWANCE", 0);
						logger.error("Unable to parse RelocationBonus of candidate. CANDIDATE_ID: " + candidateId, e);
					}
				} else logger.debug("Unable to obtain salary info for candidate. CANDIDATE_ID: " + candidateId);
				/*CommitmentsDao commitmentProvider = CommitmentsDaoFactory.create();
				Commitments[] commitment = commitmentProvider.findWhereCandidateIdEquals(candidateId);
				if (commitment.length > 0){
					salaryParams.put("COMMITMENTS", 1);
				} else{
					salaryParams.put("COMMITMENTS", 0);
				}
				Integer param = 0;
				if (candidateSalary.getPerDiem() > 0){
					param++;
				}
				if (candidateSalary.getTblb() > 0){
					param++;
				}
				if (!(retentionBonus != null && retentionBonus.equalsIgnoreCase(""))){
					param++;
				}
				if (!(relocationAllowance != null && relocationAllowance.equalsIgnoreCase(""))){
					param++;
				} else if (!(joiningBonus != null && joiningBonus.equalsIgnoreCase(""))){
					param++;
				}
				for (int i = 5; i < 12; i++){
					salaryParams.put("P" + i, ++param);
				}*/
			} catch (SalaryInfoDaoException ex){
				logger.error("Unable to get salary info for candidate ID: " + candidateId);
				logger.error(ex.getMessage());
			} catch (Exception ex){
				logger.error("Unable to get commitment for candidate ID: " + candidateId);
				logger.error(ex.getMessage());
			}
		} else{
			logger.error("Unable to obtain candidate details for candidate ID: " + candidateId);
		}
		return (salaryParams);
	}
	
	
	
	public void saveCandidateFixPerdiem(float fixedPerdiem,int id) throws FixedCandidatePerdiemDaoException{
		
	FixedCandidatePerdiemDao fixedDao=FixedCandidatePerdiemDaoFactory.create();
	FixedCandidatePerdiem perdiemFix=null;
	perdiemFix=fixedDao.findByPrimaryKey(id);
	if(perdiemFix!=null ){
		perdiemFix.setPerdiem(fixedPerdiem+"");
		FixedCandidatePerdiemPk fixedPk = new FixedCandidatePerdiemPk();
		fixedPk.setId(id);
		fixedDao.update(fixedPk, perdiemFix);
	}
	else{
	    FixedCandidatePerdiem fixed=new FixedCandidatePerdiem();
	    fixed.setId(id);
	    fixed.setPerdiem(fixedPerdiem+"");
		fixedDao.insert(fixed);
	}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}