package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.EmpserReqMapBean;
import com.dikshatech.beans.ExperienceBean;
import com.dikshatech.beans.ProfileBean;
import com.dikshatech.beans.ProfileListBean;
import com.dikshatech.beans.RollOnBean;
import com.dikshatech.beans.Salary;
import com.dikshatech.beans.UserBean;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PopulateForms;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.Status;
import com.dikshatech.common.utils.TDSUtility;
import com.dikshatech.portal.actions.ActionComponents;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.CandidatePerdiemDetailsDao;
import com.dikshatech.portal.dao.CandidateReqDao;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.CommitmentsDao;
import com.dikshatech.portal.dao.ContactTypeDao;
import com.dikshatech.portal.dao.CurrencyDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentMappingDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EducationInfoDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ExperienceInfoDao;
import com.dikshatech.portal.dao.ItRequestDao;
import com.dikshatech.portal.dao.LeaveBalanceDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.LoanDetailsDao;
import com.dikshatech.portal.dao.LoanRequestDao;
import com.dikshatech.portal.dao.LoginDao;
import com.dikshatech.portal.dao.PerdiemMasterDataDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.PoDetailsDao;
import com.dikshatech.portal.dao.PoEmpMapDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjLocationsDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.ProjectMapDao;
import com.dikshatech.portal.dao.ProjectMappingDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.ReimbursementReqDao;
import com.dikshatech.portal.dao.RequestTypeDao;
import com.dikshatech.portal.dao.RetentionBonusDao;
import com.dikshatech.portal.dao.RollOnDao;
import com.dikshatech.portal.dao.RollOnProjMapDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.SalaryInfoDao;
import com.dikshatech.portal.dao.ServiceReqInfoDao;
import com.dikshatech.portal.dao.SodexoDetailsDao;
import com.dikshatech.portal.dao.SodexoReqDao;
import com.dikshatech.portal.dao.TravelDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.CandidatePerdiemDetails;
import com.dikshatech.portal.dto.CandidatePerdiemDetailsPk;
import com.dikshatech.portal.dto.CandidatePk;
import com.dikshatech.portal.dto.CandidateReq;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.Commitments;
import com.dikshatech.portal.dto.ContactType;
import com.dikshatech.portal.dto.ContactTypePk;
import com.dikshatech.portal.dto.Currency;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DocumentMapping;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EducationInfo;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.EmployeeType;
import com.dikshatech.portal.dto.ExperienceInfo;
import com.dikshatech.portal.dto.ItRequest;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.LoanDetails;
import com.dikshatech.portal.dto.LoanRequest;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.LoginPk;
import com.dikshatech.portal.dto.PerdiemMasterData;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.PoEmpMap;
import com.dikshatech.portal.dto.PoEmpMapPk;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.ProjLocations;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectMap;
import com.dikshatech.portal.dto.ProjectMapping;
import com.dikshatech.portal.dto.ProjectMappingPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.ReimbursementReq;
import com.dikshatech.portal.dto.RequestType;
import com.dikshatech.portal.dto.RetentionBonus;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.RollOnPk;
import com.dikshatech.portal.dto.RollOnProjMap;
import com.dikshatech.portal.dto.RollOnProjMapPk;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.SalaryInfo;
import com.dikshatech.portal.dto.SalaryInfoPk;
import com.dikshatech.portal.dto.ServiceReqInfo;
import com.dikshatech.portal.dto.SodexoDetails;
import com.dikshatech.portal.dto.SodexoReq;
import com.dikshatech.portal.dto.Travel;
import com.dikshatech.portal.dto.TravelReq;
import com.dikshatech.portal.dto.UserDetails;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.UserRolesPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EducationInfoDaoException;
import com.dikshatech.portal.exceptions.ExperienceInfoDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.LoginDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectMappingDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.CandidatePerdiemDetailsDaoFactory;
import com.dikshatech.portal.factory.CandidateReqDaoFactory;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.CommitmentsDaoFactory;
import com.dikshatech.portal.factory.ContactTypeDaoFactory;
import com.dikshatech.portal.factory.CurrencyDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentMappingDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EducationInfoDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.EmployeeTypeDaoFactory;
import com.dikshatech.portal.factory.ExperienceInfoDaoFactory;
import com.dikshatech.portal.factory.ItRequestDaoFactory;
import com.dikshatech.portal.factory.LeaveBalanceDaoFactory;
import com.dikshatech.portal.factory.LeaveMasterDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.LoanDetailsDaoFactory;
import com.dikshatech.portal.factory.LoanRequestDaoFactory;
import com.dikshatech.portal.factory.LoginDaoFactory;
import com.dikshatech.portal.factory.PerdiemMasterDataDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.PoEmpMapDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjLocationsDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.ProjectMapDaoFactory;
import com.dikshatech.portal.factory.ProjectMappingDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.ReimbursementReqDaoFactory;
import com.dikshatech.portal.factory.RequestTypeDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.factory.RollOnProjMapDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.SalaryInfoDaoFactory;
import com.dikshatech.portal.factory.ServiceReqInfoDaoFactory;
import com.dikshatech.portal.factory.SodexoDetailsDaoFactory;
import com.dikshatech.portal.factory.SodexoReqDaoFactory;
import com.dikshatech.portal.factory.TravelDaoFactory;
import com.dikshatech.portal.factory.TravelReqDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.filter.FilterData;
import com.dikshatech.portal.forms.CandidateSaveForm;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.DropDownBean;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class UserModel extends ActionMethods {

	private static Logger		logger		= LoggerUtil.getLogger(UserModel.class);
	private static final String	selectUsers	= "SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE,U.USER_CREATED_BY,U.EXPERIENCE_ID, U.SKILLSET_ID, U.OTHERS, U.ACTION_BY  FROM USERS U ";
	public static final short	ACTIVE		= 0;
	private static short		SEPERATED	= 2;
	private static short		ABSCONDING	= 3;

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		ActionResult result = null;
		return result;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		boolean dropDownList = false;
		DropDown dropDown = (DropDown) form;
		ProfileInfoDao profileinfodao = ProfileInfoDaoFactory.create();
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		Users users[] = null;
		Object[] profileinfobeans = null;
		Login login = Login.getLogin(request);
		boolean listReceive = false;
		boolean directSet = false;
		List<Object> objs = new ArrayList<Object>();
		CommitmentsDao commitmentsDao = CommitmentsDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String isEmpIdNotZero = "";
		try{
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEUSERSBYLEVEL:
					users = usersDao.findByLevels(dropDown.getKey1());
					listReceive = true;
					break;
				case RECEIVEUSERSBYDIVISION:
					if (dropDown.getKey2() == 1){
						isEmpIdNotZero = "U.EMP_ID !=0 AND";
						directSet = true;
					} else listReceive = true;
					Integer[] params = { new Integer(dropDown.getKey1()), new Integer(dropDown.getKey1()), new Integer(dropDown.getKey1()) };
					users = usersDao.findByDynamicSelect(selectUsers + " WHERE " + isEmpIdNotZero + " U.LEVEL_ID IN (SELECT ID FROM LEVELS WHERE DIVISION_ID IN (SELECT ID FROM DIVISON WHERE PARENT_ID = (SELECT PARENT_ID FROM DIVISON WHERE ID = ? AND PARENT_ID != 0) OR ID = (SELECT PARENT_ID FROM DIVISON WHERE ID = ?) OR ID = ?)) AND U.STATUS NOT IN(1,2,3) ;", params);
					break;
				case RECEIVEUSERSBYDEPARTMENT:
					if (dropDown.getKey2() == 1){
						isEmpIdNotZero = "U.EMP_ID !=0 AND";
						directSet = true;
					} else listReceive = true;
					users = usersDao.findByDynamicSelect(selectUsers + "LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE " + isEmpIdNotZero + " D.ID IN (SELECT ID FROM DIVISON WHERE ID = ? OR PARENT_ID = ?) AND U.STATUS NOT IN(1,2,3) ;", new Object[] { new Integer(dropDown.getKey1()), new Integer(dropDown.getKey1()) });
					break;
				case RECEIVEUSERSBYREGIONS:
					if (dropDown.getKey2() == 1){
						isEmpIdNotZero = "U.EMP_ID !=0 AND";
						directSet = true;
					} else listReceive = true;
					users = usersDao.findByDynamicSelect(selectUsers + "LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE " + isEmpIdNotZero + " D.ID IN (SELECT D1.ID FROM DIVISON D1, DIVISON D2 WHERE D1.REGION_ID = ? AND (D1.ID = D2.PARENT_ID OR D1.ID = D2.ID) GROUP BY D1.ID) AND U.STATUS NOT IN(1,2,3) ;", new Object[] { new Integer(dropDown.getKey1()) });
					break;
				case RECEIVEUSERSBYCOMPANY:
					users = usersDao.findByDynamicSelect(selectUsers + "LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT D1.ID FROM DIVISON D1, DIVISON D2 LEFT JOIN REGIONS R ON R.ID = D2.REGION_ID WHERE R.COMPANY_ID = ? AND (D1.ID = D2.PARENT_ID OR D1.ID = D2.ID) GROUP BY D1.ID) AND U.STATUS NOT IN(1,2,3) ;", new Object[] { new Integer(dropDown.getKey1()) });
					listReceive = true;
					break;
				case RECEIVEALL:
					dropDown = (DropDown) form;
					if (dropDown.getKey1() == SEPERATED){
						users = usersDao.findWhereStatusEquals(SEPERATED);
					} else if (dropDown.getKey1() == ABSCONDING){
						users = usersDao.findWhereStatusEquals(ABSCONDING);
					} else{
						/**
						 * gurunath made changes for performance on 29/11/2012
						 */
						try{
							dropDown.setDropDown(new FilterData().filter(usersDao.findAllEmployeesProfileListBean(0), ActionComponents.PROFILEINFO.toString(), login));
							HashMap<Integer, String> departments = new HashMap<Integer, String>();
							for (Object entry : dropDown.getDropDown()){
								ProfileListBean plBean = (ProfileListBean) entry;
								plBean.setProjectName(getProjectName(plBean.getUserId()));
								if (!departments.containsKey(plBean.getLevelid())) departments.put(plBean.getLevelid(), getDepartment(plBean.getLevelid()));
								plBean.setDepartment(departments.get(plBean.getLevelid()));
							}
							request.setAttribute("actionForm", dropDown);
							return result;
						} catch (Exception e){
							logger.error("error while getting employees, returned data using old process.", e);
							users = usersDao.findWhereStatusEquals((short) 0);
						}
					}
					listReceive = true;
					dropDownList = true;
					break;
				case RECEIVEEDITEMPLOYEE:
					Users user1 = new Users();
					user1 = usersDao.findByPrimaryKey(dropDown.getKey1());
					EducationInfoDao educationInfoDao = EducationInfoDaoFactory.create();
					ExperienceInfoDao experienceInfoDao = ExperienceInfoDaoFactory.create();
					CandidateReqDao candidateReqDao = CandidateReqDaoFactory.create();
					if (user1.getSkillsetId() != null){
						user1.setSkillsetIdNull(false);
					}
					if (educationInfoDao.findWhereUserIdEquals(dropDown.getKey1()).length > 0){
						user1.setEducationIdNull(false);
					}
					if (experienceInfoDao.findWhereUserIdEquals(dropDown.getKey1()).length > 0){
						user1.setExperienceIdNull(false);
					}
					// singleUserData = user1;
					Commitments[] commitments = commitmentsDao.findByDynamicSelect("SELECT * FROM COMMITMENTS CM LEFT JOIN CANDIDATE CA ON CM.CANDIDATE_ID=CA.ID WHERE CA.PROFILE_ID=?", new Object[] { new Integer(user1.getProfileId()) });
					com.dikshatech.beans.CommitmentBean[] bean = new com.dikshatech.beans.CommitmentBean[commitments.length];
					int p = 0;
					for (Commitments c : commitments){
						bean[p] = DtoToBeanConverter.DtoToBeanConverter(c);
						p++;
					}
					user1.setCommitmentBean(bean);
					CandidateReq cReq[] = candidateReqDao.findByDynamicSelect("SELECT * FROM CANDIDATE_REQ CR LEFT JOIN CANDIDATE C ON C.ID=CR.CANDIDATE_ID WHERE C.PROFILE_ID=? ORDER BY CREATEDATETIME DESC", new Object[] { new Integer(user1.getProfileId()) });
					if (cReq.length > 0){
						ProfileInfo getinfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI LEFT JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(cReq[0].getRaisedBy()) })[0];
						user1.setCreatedBy(getinfo.getFirstName() + " " + getinfo.getLastName());
						user1.setCommitmentDate(cReq[0].getCreatedatetime());
					}
					/**
					 * Set Details for service Request information if user requested any
					 */
					EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
					EmpSerReqMap empSerReqMap[] = empSerReqMapDao.findByDynamicWhere("REQUESTOR_ID = ? AND REQ_TYPE_ID !=7 AND REQ_TYPE_ID !=8 AND REQ_TYPE_ID !=9" + " AND REQ_TYPE_ID !=10 ORDER BY REQUESTOR_ID", new Object[] { user1.getId() });
					if (empSerReqMap != null && empSerReqMap.length > 0){
						EmpserReqMapBean empserReqMapArray[] = new EmpserReqMapBean[empSerReqMap.length];
						int i = 0;
						LoanDetails details = null;
						for (EmpSerReqMap singleSerRequest : empSerReqMap){
							EmpserReqMapBean empserReqMapBean = DtoToBeanConverter.DtoToBeanConverter(singleSerRequest);
							if (singleSerRequest.getReqTypeId() == 1){//LEAVE
								ServiceReqInfoDao serviceReqInfoDao = ServiceReqInfoDaoFactory.create();
								ServiceReqInfo serviceReqInfo[] = serviceReqInfoDao.findWhereEsrMapIdEquals(singleSerRequest.getId());
								if (serviceReqInfo != null && serviceReqInfo.length > 0){
									empserReqMapBean.setStatusName(serviceReqInfo[0].getStatus());//status string only
								}
							} else if (singleSerRequest.getReqTypeId() == 2){//TRAVEL
								TravelDao travelDao = TravelDaoFactory.create();
								Travel travel[] = travelDao.findWhereEsrqmIdEquals(singleSerRequest.getId());
								if (travel != null && travel.length > 0){
									empserReqMapBean.setStatusId(travel[0].getStatus());//status id
									empserReqMapBean.setStatusName(Status.getStatus(travel[0].getStatus()));//status name
								}
							} else if (singleSerRequest.getReqTypeId() == 3){//SODEXO
								SodexoDetailsDao sodexoDetailsDao = SodexoDetailsDaoFactory.create();
								SodexoReqDao sodexoReqDao = SodexoReqDaoFactory.create();
								SodexoReq sodexoReq[] = sodexoReqDao.findWhereEsrMapIdEquals(singleSerRequest.getId());
								if (sodexoReq != null && sodexoReq.length > 0){
									SodexoDetails sodexoDetails = sodexoDetailsDao.findByPrimaryKey(sodexoReq[0].getSdId());
									empserReqMapBean.setStatusId(sodexoDetails.getStatus());//status id
									empserReqMapBean.setStatusName(Status.getStatus(sodexoDetails.getStatus()));//status name
								}
							} else if (singleSerRequest.getReqTypeId() == 4){//LOAN
								LoanDetailsDao loanDetlDao = LoanDetailsDaoFactory.create();
								LoanRequestDao loanReqDao = LoanRequestDaoFactory.create();
								LoanRequest loanRequest[] = loanReqDao.findWhereEsrMapIdEquals(singleSerRequest.getId());
								if (loanRequest != null && loanRequest.length > 0){
									details = loanDetlDao.findByPrimaryKey(loanRequest[0].getLoanId());
									empserReqMapBean.setStatusId(details.getStatusId());//status id
									empserReqMapBean.setStatusName(Status.getStatus(details.getStatusId()));//status name
									if (details.getStatusId() == Status.getStatusId(Status.COMPLETED)){
										Date toDate = PortalUtility.addMonths(details.getResponseDate(), details.getEmiPeriod());
										if ((new Date().after(details.getResponseDate()) || new Date().equals(details.getResponseDate())) && (new Date().before(toDate) || new Date().equals(toDate))){
											empserReqMapBean.setIsLoan(true);
											empserReqMapBean.setLoanType(details.getLoanTypeId() == 1 ? "PERSONAL LOAN" : "LAPTOP LOAN");
											empserReqMapBean.setLoanId(details.getId());
											logger.info("TRue Active loan");
										}
									}
								}
							} else if (singleSerRequest.getReqTypeId() == 5){//REMBURSIMENT
								ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
								ReimbursementReq reimbursementReq = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID=" + "(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?)", new Object[] { new Integer(singleSerRequest.getId()) })[0];
								empserReqMapBean.setStatusName(reimbursementReq.getStatus());//string only
							} else if (singleSerRequest.getReqTypeId() == 6){//IT SUPPORT
								ItRequestDao itReqDao = ItRequestDaoFactory.create();
								ItRequest reqD = itReqDao.findByDynamicSelect("SELECT * FROM IT_REQUEST WHERE ID=" + "(SELECT MAX(ID) FROM IT_REQUEST WHERE ESR_MAP_ID=?)", new Object[] { new Integer(singleSerRequest.getId()) })[0];
								empserReqMapBean.setStatusName(reqD.getStatus());//string only
							}
							try{
								RequestTypeDao requestTypeDao = RequestTypeDaoFactory.create();
								RequestType requestType = requestTypeDao.findByPrimaryKey(singleSerRequest.getReqTypeId());
								empserReqMapBean.setRequestTypeName(requestType.getName());
								empserReqMapArray[i] = empserReqMapBean;
								i++;
							} catch (Exception e){
								// TODO: handle exception
							}
						}//for ends
						user1.setEmpserReqMapBeans(empserReqMapArray);
					}//if ends
					request.setAttribute("actionForm", user1);
					break;
				case RECEIVEALLUSER:
					users = usersDao.findByDynamicWhere("STATUS = 0  ", null);
					listReceive = true;
					break;
				case RECEIVEALLUSERTAT:
					users = usersDao.findByDynamicWhere(" STATUS NOT IN (2,3)  ", null);
					directSet = true;
					break;
				case RECEIVEALLCONSULTANT:
					DropDownBean dropdown = new DropDownBean();
					ArrayList<RollOnBean> activeUsersList = null;
					try{
						Users[] activeUsers = usersDao.findByDynamicWhere(" ID != ? AND STATUS=0 AND ID > 3 AND EMP_ID>0", new Object[] { new Integer(login.getUserId()) });//exclude admin.portal
						if (activeUsers != null && activeUsers.length > 0) activeUsersList = new RollOnModel().populateProfileBean(activeUsers, null);
						else logger.debug("ROLL_ON-->RECEIVEALLCONSULTANT : FOUND NO ACTIVE RESOURCES OTHER THAN SELF : " + login.getUserId());
					} catch (UsersDaoException udex){
						logger.error("ROLL_ON-->RECEIVEALLCONSULTANT : EXCEPTION WAS THROWN ON TRYING TO FETCH DATA FROM USERS FOR ACTIVE_AND_REPORTING ", udex);
					}
					dropdown.setDropDown((activeUsersList == null) ? null : activeUsersList.toArray());
					request.setAttribute("actionForm", dropdown);
					break;
				case RECEIVEUSERRESOURCE:{
					ProjectMappingDao proMappingDao = ProjectMappingDaoFactory.create();
					ProjectDao projectDao = ProjectDaoFactory.create();
					String sql = " PROFILE_ID IN(SELECT ID FROM PROFILE_INFO WHERE REPORTING_MGR=" + form.getUserId() + ") AND STATUS=0 AND ID > 3 AND EMP_ID>0";
					Users resourceArr[] = usersDao.findByDynamicWhere(sql, null);
					ProfileInfo[] profileinfoDtos = new ProfileInfo[resourceArr.length];
					profileinfobeans = new ProfileBean[resourceArr.length];
					int i = 0;
					for (Users singleUser : resourceArr){
						if (singleUser.getId() == 1){
							continue;
						}
						profileinfoDtos[i] = profileinfodao.findByPrimaryKey(singleUser.getProfileId());// changed
						// emp-id to profile-id
						ProfileBean proBean = DtoToBeanConverter.DtoToBeanConverter(profileinfoDtos[i], singleUser);
						ProjectMapping[] empprojects = proMappingDao.findWhereUserIdEquals(singleUser.getId());
						for (ProjectMapping projectMapping : empprojects){
							Project project = projectDao.findByPrimaryKey(projectMapping.getProjectId());
							proBean.setProjectId(project.getId());
							proBean.setProjectName(project.getName());
						}
						profileinfobeans[i] = proBean;
						i++;
					}
					dropDown.setDropDown(profileinfobeans);
					request.setAttribute("actionForm", dropDown);
					break;
				}
				case RECEIVECOMPANYRESOURCE:{
					/**
					 * find all the people who belong to company level projects
					 * A company level project does not have region,department,division
					 * fetch the company level project information from PROJECT_MAP ..... PROJECT_ID's
					 * get the list of DISTINCT userIds from PROJECT_MAPPING PM where PM.PROJECT_ID= above fetched ids
					 * SELECT * FROM PROJECT_MAP PMAP WHERE PMAP.REGION_ID IS NULL AND PMAP.DEPARTMENT_ID IS NULL AND PMAP.DIVISON_ID IS NULL
					 */
					ProjectMapDao projectMapDao = ProjectMapDaoFactory.create();
					ProjectMap[] companyProjects = projectMapDao.findByDynamicSelect("SELECT * FROM PROJECT_MAP PMAP WHERE PMAP.REGION_ID IS NULL AND PMAP.DEPT_ID IS NULL AND PMAP.DIV_ID IS NULL", null);
					ProfileBean[] profileBean = null;
					if (companyProjects != null && companyProjects.length > 0){
						logger.debug("NEW_CHARGE_CODE : RECEIVECOMPANYRESOURCE --> found company level projects");
						List<Integer> companyLevelProjectIds = new ArrayList<Integer>(companyProjects.length);
						for (ProjectMap eachProjectMap : companyProjects)
							companyLevelProjectIds.add(eachProjectMap.getProjId());
						String query = "SELECT * FROM PROJECT_MAPPING PM WHERE PM.PROJECT_ID IN(" + companyLevelProjectIds.toString().replace("[", " ").replace("]", " ").trim() + ") ORDER BY PM.ID DESC";
						ProjectMappingDao proMappingDao = ProjectMappingDaoFactory.create();
						ProjectMapping[] fetchedProjectMappings = proMappingDao.findByDynamicSelect(query, null);
						if (fetchedProjectMappings != null && fetchedProjectMappings.length > 0){
							Set<Integer> usersIdsOnCompanyProjects = new HashSet<Integer>(fetchedProjectMappings.length);
							for (ProjectMapping eachProjectMapping : fetchedProjectMappings)
								usersIdsOnCompanyProjects.add(eachProjectMapping.getUserId());//userId repetition is restricted
							query = "SELECT * FROM USERS U WHERE U.ID IN(" + usersIdsOnCompanyProjects.toString().replace("[", " ").replace("]", " ").trim() + ") AND U.STATUS=0 AND U.EMP_ID>0";
							UsersDao userDao = UsersDaoFactory.create();
							Users[] usersOnCompanyProjects = userDao.findByDynamicSelect(query, null);
							if (usersOnCompanyProjects != null && usersOnCompanyProjects.length > 0){
								logger.debug("NEW_CHARGE_CODE : RECEIVECOMPANYRESOURCE --> fetched " + usersOnCompanyProjects.length + " rows from USERS");
								profileBean = new ProfileBean[usersOnCompanyProjects.length];
								int index = 0;
								for (Users eachUserFetched : usersOnCompanyProjects){//---------------------------------------------------------------
									ProfileInfo userProfileInfo = profileInfoDao.findByPrimaryKey(eachUserFetched.getProfileId()); // *
									if (userProfileInfo.getReportingMgr() != Integer.parseInt(request.getParameter("userId")) // *
											&& eachUserFetched.getId() != Integer.parseInt(request.getParameter("userId"))) // *
									profileBean[index] = DtoToBeanConverter.DtoToBeanConverter(userProfileInfo, eachUserFetched); // *
									// *
									if (profileBean[index] != null) // *
									index++; // *
								}//-------------------------------------------------------------------------------------------------------------------
							} else logger.error("NEW_CHARGE_CODE : RECEIVECOMPANYRESOURCE --> fetched 0 rows from USERS on executing the query..." + query);
						} else logger.error("NEW_CHARGE_CODE : RECEIVECOMPANYRESOURCE --> fetched 0 rows from PROJECT_MAPPING on executing the query..." + query);
					} else logger.debug("NEW_CHARGE_CODE : RECEIVECOMPANYRESOURCE --> could not find any company level projects ! ! ");
					dropDown.setDropDown(profileBean);
					request.setAttribute("actionForm", dropDown);
					break;
				}
				case RECEIVEREGIONRESOURCE:{
					ProfileBean[] profileBean = null;
					String sql = "SELECT * FROM PROJECT_MAP WHERE REGION_ID IS NOT NULL";
					ProjectMap[] fetchedProjMaps = ProjectMapDaoFactory.create().findByDynamicSelect(sql, null);
					if (fetchedProjMaps != null && fetchedProjMaps.length > 0){
						ArrayList<Integer> projIdList = new ArrayList<Integer>();
						for (ProjectMap eachProjMap : fetchedProjMaps)
							projIdList.add(eachProjMap.getProjId());
						String projIds = projIdList.toString().replace('[', ' ').replace(']', ' ').trim();
						sql = "SELECT * FROM PROJECT_MAPPING WHERE PROJECT_ID IN(" + projIds + ")";
						ProjectMapping[] fetchedProjectMapping = ProjectMappingDaoFactory.create().findByDynamicSelect(sql, null);
						HashSet<Integer> userIdSet = new HashSet<Integer>();
						for (ProjectMapping eachProjMapping : fetchedProjectMapping)
							userIdSet.add(eachProjMapping.getUserId());
						String userIds = userIdSet.toString().replace('[', ' ').replace(']', ' ').trim();
						sql = "SELECT * FROM USERS U WHERE U.ID IN(" + userIds + ") AND U.STATUS=0 AND U.ID != 1 AND U.EMP_ID>0";
						Users[] fetchedUsers = UsersDaoFactory.create().findByDynamicSelect(sql, null);
						profileBean = new ProfileBean[fetchedUsers.length];
						int j = 0;
						for (Users eachUser : fetchedUsers){
							ProfileInfo userProfile = profileinfodao.findByPrimaryKey(eachUser.getProfileId());
							if (userProfile.getReportingMgr() != Integer.parseInt(request.getParameter("userId")) && eachUser.getId() != Integer.parseInt(request.getParameter("userId"))){
								profileBean[j] = DtoToBeanConverter.DtoToBeanConverter(userProfile, eachUser);
								j++;
							}
						}
					}
					dropDown.setDropDown(profileBean);
					request.setAttribute("actionForm", dropDown);
					/*String compsql = "SELECT * FROM PROJECT_MAPPING PMAP WHERE PMAP.PROJECT_ID IN(SELECT PM.PROJ_ID FROM PROJECT_MAP PM WHERE PM.REGION_ID IS NOT NULL AND PM.DEPT_ID IS NULL AND DIV_ID IS NULL AND PM.COMP_ID IS NOT NULL)";
					
					ProjectMappingDao proMappingDao = ProjectMappingDaoFactory.create();
					UsersDao userDao = UsersDaoFactory.create();
					ProjectMapping[] projects = proMappingDao.findByDynamicSelect(compsql, null);
					ProfileBean[] profileBean = new ProfileBean[projects.length];
					if(projects.length != 0){
						int j = 0;
						for(ProjectMapping projectMapping : projects){
							Users user = userDao.findByPrimaryKey(projectMapping.getUserId());
							if(user.getEmpId()>0){
								ProfileInfo userProfile = profileinfodao.findByPrimaryKey(user.getProfileId());
								if(userProfile.getReportingMgr() != Integer.parseInt(request.getParameter("userId"))
								        && user.getId() != Integer.parseInt(request.getParameter("userId")) ){
									
											profileBean[j] = DtoToBeanConverter.DtoToBeanConverter(userProfile, user);
											j++;
								}
							}							
						}
						dropDown.setDropDown(profileBean);
						request.setAttribute("actionForm", dropDown);
					}*/
					break;
				}
				case HRSPOCS:
					Login loginSess = Login.getLogin(request);
					UserLogin userLogin = loginSess.getUserLogin();
					RegionsDao regionDao = RegionsDaoFactory.create();
					Regions region = regionDao.findByPrimaryKey(userLogin.getRegionId());
					Notification notification = new Notification(region.getRefAbbreviation());
					int hrDeptID = notification.hrdId;
					users = usersDao.findByDynamicSelect(selectUsers + "LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT ID FROM DIVISON WHERE ID = ? OR PARENT_ID = ?) AND U.STATUS NOT IN(1,2,3)", new Object[] { hrDeptID, hrDeptID });
					if (users.length < 1){
						users = new Users[1];
						users[0] = usersDao.findByPrimaryKey(1);
					}
					directSet = true;
					break;
				case RECEIVECURRENCY:
					CurrencyDao currencyDao = CurrencyDaoFactory.create();
					Currency currency[] = currencyDao.findAll();
					DropDown dd = new DropDown();
					dd.setDropDown(currency);
					request.setAttribute("actionForm", dd);
					break;
				case EMPLOYEETYPE:
					EmployeeType employeeType[] = EmployeeTypeDaoFactory.create().findAll();
					DropDown empType = new DropDown();
					empType.setDropDown(employeeType);
					request.setAttribute("actionForm", empType);
					break;
				case RECEIVERMS:
					List<UserBean> rmList = new ArrayList<UserBean>();
					users = usersDao
							.findByDynamicSelect(
									"SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE, U.USER_CREATED_BY, U.EXPERIENCE_ID, U.SKILLSET_ID,(SELECT CONCAT(PIF.FIRST_NAME,' ',PIF.LAST_NAME) FROM PROFILE_INFO PIF WHERE PIF.ID= U.PROFILE_ID) AS OTHERS , U.ACTION_BY FROM USERS U JOIN PROFILE_INFO PF ON U.PROFILE_ID=PF.ID WHERE U.STATUS NOT IN(1,2,3) AND U.ID IN (SELECT P.REPORTING_MGR FROM PROFILE_INFO P WHERE P.REPORTING_MGR >3 GROUP BY P.REPORTING_MGR)",
									null);
					for (Users user : users){
						if (user != null){
							rmList.add(new UserBean(user.getId() + "", user.getOthers() + "", user.getEmpId() + "", false));
						}
					}
					dropDown.setDropDown(rmList.toArray());
					request.setAttribute("actionForm", dropDown);
					return result;
				case RECEIVEHRSPOCS:
					List<UserBean> hrList = new ArrayList<UserBean>();
					users = usersDao
							.findByDynamicSelect(
									"SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE, U.USER_CREATED_BY, U.EXPERIENCE_ID, U.SKILLSET_ID,(SELECT CONCAT(PIF.FIRST_NAME,' ',PIF.LAST_NAME) FROM PROFILE_INFO PIF WHERE PIF.ID= U.PROFILE_ID) AS OTHERS , U.ACTION_BY FROM USERS U JOIN PROFILE_INFO PF ON U.PROFILE_ID=PF.ID WHERE U.STATUS NOT IN(1,2,3) AND U.ID IN (SELECT P.HR_SPOC FROM PROFILE_INFO P WHERE P.HR_SPOC >3 GROUP BY P.HR_SPOC)",
									null);
					for (Users user : users){
						if (user != null){
							hrList.add(new UserBean(user.getId() + "", user.getOthers() + "", user.getEmpId() + "", false));
						}
					}
					dropDown.setDropDown(hrList.toArray());
					request.setAttribute("actionForm", dropDown);
					return result;
				case RMRESOURCE:
					List<UserBean> resources = new ArrayList<UserBean>();
					if (dropDown.getUserId() > 3){
						users = usersDao.findByDynamicSelect(
								"SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE, U.USER_CREATED_BY, U.EXPERIENCE_ID, U.SKILLSET_ID,(SELECT CONCAT(PIF.FIRST_NAME,' ',PIF.LAST_NAME) FROM PROFILE_INFO PIF WHERE PIF.ID= U.PROFILE_ID) AS OTHERS , U.ACTION_BY FROM USERS U JOIN PROFILE_INFO PF ON U.PROFILE_ID=PF.ID WHERE U.STATUS NOT IN(1,2,3) AND PF.REPORTING_MGR=?", new Object[] { dropDown
										.getUserId() });
						for (Users user : users){
							if (user != null){
								resources.add(new UserBean(user.getId() + "", user.getOthers() + "", user.getEmpId() + "", false));
							}
						}
					}
					dropDown.setDropDown(resources.toArray());
					request.setAttribute("actionForm", dropDown);
					return result;
				case HRSPOCRESOURCE:
					List<UserBean> hrResources = new ArrayList<UserBean>();
					if (dropDown.getUserId() > 3){
						users = usersDao.findByDynamicSelect("SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE, U.USER_CREATED_BY, U.EXPERIENCE_ID, U.SKILLSET_ID,(SELECT CONCAT(PIF.FIRST_NAME,' ',PIF.LAST_NAME) FROM PROFILE_INFO PIF WHERE PIF.ID= U.PROFILE_ID) AS OTHERS , U.ACTION_BY FROM USERS U JOIN PROFILE_INFO PF ON U.PROFILE_ID=PF.ID WHERE U.STATUS NOT IN(1,2,3) AND PF.HR_SPOC=?",
								new Object[] { dropDown.getUserId() });
						for (Users user : users){
							if (user != null){
								hrResources.add(new UserBean(user.getId() + "", user.getOthers() + "", user.getEmpId() + "", false));
							}
						}
					}
					dropDown.setDropDown(hrResources.toArray());
					request.setAttribute("actionForm", dropDown);
					return result;
				default:
					break;
			}
			if (listReceive){
				if (users != null) for (Users singleUser : users){
					if (dropDownList){
						ProfileInfo profileInfo = profileinfodao.findByPrimaryKey(singleUser.getProfileId());
						ProfileBean proBean = DtoToBeanConverter.DtoToBeanConverter1(singleUser, profileInfo);
						if (!checkIncomplete(singleUser)){
							proBean.setIncomplete(true);
						}
						objs.add(proBean);
					} else{
						ProfileInfo profileInfo = profileinfodao.findByPrimaryKey(singleUser.getProfileId());
						ProfileBean proBean = DtoToBeanConverter.DtoToBeanConverter1(singleUser, profileInfo);
						if (!checkIncomplete(singleUser)){
							proBean.setIncomplete(true);
						}
						objs.add(proBean);
					}
				}
				profileinfobeans = new ProfileBean[objs.size()];
				profileinfobeans = (Object[]) objs.toArray();
				dropDown.setDropDown(new FilterData().filter(profileinfobeans, ActionComponents.USER.toString(), login));
				request.setAttribute("actionForm", dropDown);
			} else if (directSet){
				ArrayList<UserDetails> proBean = new ArrayList<UserDetails>(users.length);
				if (users != null) for (Users singleUser : users){
					try{
						ProfileInfo profileInfo = profileinfodao.findByPrimaryKey(singleUser.getProfileId());
						String maritalStatus;
						if (singleUser.getPersonalId() > 0){
							PersonalInfo personalInfo = personalInfoDao.findByPrimaryKey(singleUser.getPersonalId());
							maritalStatus = personalInfo.getMaritalStatus();
						} else{
							maritalStatus = "";
						}
						proBean.add(new UserDetails(singleUser.getId(), profileInfo.getFirstName(), profileInfo.getFirstName() + " " + profileInfo.getLastName() + " (" + singleUser.getEmpId() + ")", profileInfo.getLastName(), singleUser.getEmpId() + "", maritalStatus));
						objs.add(proBean);
						objs.add(proBean);
					} catch (Exception e){}
				}
				dropDown.setDropDown(proBean.toArray());
				request.setAttribute("actionForm", dropDown);
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private String getDepartment(int levelId) {
		try{
			DivisonDao divisionDao = DivisonDaoFactory.create();
			Divison division = null, divisions[] = divisionDao.findByDynamicSelect("SELECT D.* FROM DIVISON D JOIN LEVELS L ON L.DIVISION_ID=D.ID AND L.ID = ?", new Object[] { levelId });
			if (divisions != null && divisions.length > 0 && divisions[0] != null){
				division = divisions[0];
				if (division.getParentId() != 0){
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) return division.getName();
					return "N.A";
				}
				return division.getName();
			}
		} catch (Exception e){}
		return "N.A";
	}

	private String getProjectName(int userId) {
		List<Object> projName = JDBCUtiility.getInstance().getSingleColumn("SELECT PROJ.NAME FROM PROJECT PROJ JOIN ROLL_ON_PROJ_MAP RM ON PROJ.ID=RM.PROJ_ID JOIN ROLL_ON R ON ROLL_ON_ID=R.ID AND EMP_ID=? AND CURRENT=1;", new Object[] { userId });
		if (projName != null && projName.size() > 0) return (String) (projName.get(0) != null ? projName.get(0) : "");
		return "";
	}

	boolean checkIncomplete(Users user) {
		boolean flag = true;
		Users user1 = new Users();
		UsersDao usersDao = UsersDaoFactory.create();
		try{
			user1 = usersDao.findByPrimaryKey(user.getId());
			EducationInfoDao educationInfoDao = EducationInfoDaoFactory.create();
			ExperienceInfoDao experienceInfoDao = ExperienceInfoDaoFactory.create();
			if (user1.getSkillsetId() == null){
				user1.setSkillsetIdNull(true);
			} else{
				user1.setSkillsetIdNull(false);
			}
			if ((educationInfoDao.findWhereUserIdEquals(user1.getId()).length > 0)){
				user1.setEducationIdNull(false);
			}
			if ((experienceInfoDao.findWhereUserIdEquals(user1.getId()).length > 0)){
				user1.setExperienceIdNull(false);
			}
			if (user1.getPassportId() == 0){
				flag = false;
			} else if (user1.getPersonalId() == 0){
				flag = false;
			} else if (user1.getProfileId() == 0){
				flag = false;
			} else if (user1.getFinanceId() == 0){
				flag = false;
			} else if (user1.isExperienceIdNull()){
				flag = false;
			} else if (user1.isEducationIdNull()){
				flag = false;
			} else if (user1.isSkillsetIdNull()){
				flag = false;
			}
		}// try ends
		catch (UsersDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EducationInfoDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExperienceInfoDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	private boolean migrateCandidate(CandidateSaveForm candidatesaveform, HttpServletRequest request) {
		//boolean result = false;
		Boolean flag = true;
		Login loginSess = Login.getLogin(request);
		UsersDao usersDao = UsersDaoFactory.create();
		LeaveBalanceDao lDao = LeaveBalanceDaoFactory.create();
		CandidateDao candidateDao = CandidateDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
		LoginDao loginDao = LoginDaoFactory.create();
		RollOnDao rollOnDao = RollOnDaoFactory.create();
		ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		DivisonDao divisonDao = DivisonDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		ProjLocationsDao proLocDao = ProjLocationsDaoFactory.create();
		SalaryInfoDao salaryInfoDao=SalaryInfoDaoFactory.create();
		RollOnPk rollOnPk = null;
		RollOnProjMapPk rollOnProjMapPk = null;
		EmpSerReqMapPk empMapPk = new EmpSerReqMapPk();
		PoDetailsDao poDetailsDao = PoDetailsDaoFactory.create();
		RollOnProjMapDao rollOnProjMapDao = RollOnProjMapDaoFactory.create();
		PoEmpMapPk insertedRow = new PoEmpMapPk();
		PoEmpMapDao poEmpMapDao = PoEmpMapDaoFactory.create();
		Users users = new Users();
		UsersPk usersPk = new UsersPk();
		PopulateForms pForms = new PopulateForms();
		ProfileInfo profileInfo = pForms.setProfileInfo(candidatesaveform);
		PersonalInfo personalInfo = pForms.setPersonalInfo(candidatesaveform);
		SalaryDetails salaryDetails = pForms.setSalaryDetails(candidatesaveform);
		PortalMail welcomeMail = new PortalMail();
		PortalMail portalMail = new PortalMail();
		CandidatePerdiemDetailsDao perdiemDetailsDao = CandidatePerdiemDetailsDaoFactory.create();
		RetentionBonus retentionBonus=new RetentionBonus();
		RetentionBonusDao retentionBonusDao=RetentionBonusDaoFactory.create();
		PerdiemMasterData perMaster=new PerdiemMasterData();
		PerdiemMasterDataDao perdiemMasterDataDao=PerdiemMasterDataDaoFactory.create();
		try{
			Candidate candidate = candidateDao.findByPrimaryKey(candidatesaveform.getId());
			CandidatePk candidatePk = new CandidatePk();
			candidatePk.setId(candidate.getId());
			salaryDetails.setCandidateId(candidate.getId());
			ProfileInfo profile = null;
			if (candidate.getProfileId() > 0){
				profile = profileInfoDao.findByPrimaryKey(candidate.getProfileId());
				profileInfo.setId(candidate.getProfileId());
				profile.setOfficalEmailId(profileInfo.getOfficalEmailId());
				profile.setLevelId(profileInfo.getLevelId());
				profile.setHrSpoc(profileInfo.getHrSpoc());
				profile.setReportingMgr(profileInfo.getReportingMgr());
				welcomeMail.setCandidateName(profile.getFirstName() + " " + profile.getLastName());
			}
			
			
			
			
			if (candidate.getPersonalId() > 0){
				personalInfo = personalInfoDao.findByPrimaryKey(candidate.getPersonalId());
				welcomeMail.setRecipientMailId(personalInfo.getPersonalEmailId());
			}
			ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory.create();
			ProjectMapping pMapping = new ProjectMapping();
			/**
			 * add mapping to project
			 */
			if (profileInfo.getProjectId() > 0){
				pMapping.setProjectId(profileInfo.getProjectId());
			}
			Login login = loginDao.findWhereCandidateIdEquals(candidate.getId());
			LoginPk loginPk = new LoginPk();
			/**
			 * Update Profile Info
			 */
			profile.setOfficalEmailId(login.getUserName());
			ProfileInfoModel profileInfoModel = new ProfileInfoModel();
			profile.setCandidateId(candidate.getId());
			/**
			 * set employee type as probation as still date of confirmation not given
			 */
			profile.setEmpType("PERMANENT");
			profile.setEmployeeType("PROBATION");
			ActionResult resultFromUpdate = profileInfoModel.update(profile, request);
			if (!(resultFromUpdate.getActionMessages() == null || resultFromUpdate.getActionMessages().isEmpty())){ throw new Exception("Error in updating Profile"); }
			welcomeMail.setAutoGenUserName(login.getUserName());
			if (login.getPassword() != null){
				welcomeMail.setAutoGenPassword(DesEncrypterDecrypter.getInstance().decrypt(login.getPassword()));
			}
			UserRolesDao uRolesDao = UserRolesDaoFactory.create();
			// get education details
			EducationInfoModel educationInfoModel = new EducationInfoModel();
			EducationInfoDao educationinfodao = EducationInfoDaoFactory.create();
			EducationInfo eduoinf[] = educationinfodao.findWhereCandidateIdEquals(candidate.getId());
			// get experience details
			ExperienceInfoModel experienceInfoModel = new ExperienceInfoModel();
			ExperienceInfoDao experienceInfodao = ExperienceInfoDaoFactory.create();
			ExperienceInfo expinf[] = experienceInfodao.findWhereCandidateIdEquals(candidate.getId());
			// get salary details
			SalaryDetailModel salaryDetailModel = new SalaryDetailModel();
			SalaryDetails details[];
			SalaryDetailsDao salaryDetailsDao = SalaryDetailsDaoFactory.create();
			details = salaryDetailsDao.findWhereCandidateIdEquals(candidate.getId());
			try{
				candidate = candidateDao.findByPrimaryKey(candidate.getId());
				if (candidate.getProfileId() > 0){
					users.setProfileId(candidate.getProfileId());
				}
				if (candidate.getPersonalId() > 0){
					users.setPersonalId(candidate.getPersonalId());
				}
				if (candidate.getPassportId() > 0){
					users.setPassportId(candidate.getPassportId());
				}
				if (candidate.getExperienceId() > 0){
					users.setExperienceId(candidate.getExperienceId());
				}
				if (candidate.getNomineeId() > 0){
					users.setNomineeId(candidate.getNomineeId());
				}
				if (candidate.getFinancialId() > 0){
					users.setFinanceId(candidate.getFinancialId());
				}
				short isemployee = 1;
				candidate.setIsEmployee(isemployee);
				candidate.setStatus(2);
				candidateDao.update(candidatePk, candidate);
				profileInfo = profileInfoDao.findByPrimaryKey(candidate.getProfileId());
				users.setLevelId(profileInfo.getLevelId());
				users.setUserCreatedBy(candidate.getTatId());
				usersPk = usersDao.insert(users);
				/**
				 * currently auto-populate of emp_id is disabled it will be
				 * manually updated when required.
				 */
				// users = generateEmpId(usersPk.getId());
				usersDao.update(usersPk, users);
				if (pMapping.getProjectId() > 0){
					pMapping.setUserId(usersPk.getId());
					projectMappingDao.insert(pMapping);
				}
				/**
				 * add accumulated leave balance
				 */
				LeaveBalance lBalance = new LeaveBalance();
				lBalance.setUserId(usersPk.getId());
				lBalance.setBalance(0);
				lBalance.setBereavement(0);
				lBalance.setMarriage(0);
				lBalance.setPaternity(0);
				lBalance.setMaternity(0);
				lBalance.setCompOff(0);
				lBalance.setLeaveAccumalated(0);
				lBalance.setLeavesTaken(0);
				lDao.insert(lBalance);
				/**
				 * attach role to user
				 */
				UserRoles userRoles1 = new UserRoles();
				userRoles1.setUserId(users.getId());
				userRoles1.setCandidateIdNull(true);
				userRoles1.setRoleId(candidatesaveform.getRoleId());
				uRolesDao.insert(userRoles1);
				/**
				 * Save in Contact Type
				 */
				ContactTypeDao contactTypeDao = ContactTypeDaoFactory.create();
				ContactType contactType[] = contactTypeDao.findWhereCandidateIdEquals(candidate.getId());
				if (contactType != null && contactType.length > 0){
					for (ContactType contactType2 : contactType){
						ContactTypePk contactTypePk = new ContactTypePk();
						contactTypePk.setId(contactType2.getId());
						contactType2.setUserId(usersPk.getId());
						contactType2.setCandidateIdNull(true);
						contactTypeDao.update(contactTypePk, contactType2);
					}
				}
				/**
				 * Save in Login table
				 */
				if (login != null){
					loginPk.setId(login.getId());
					login.setUserId(usersPk.getId());
					login.setCandidateIdNull(true);
					loginDao.update(loginPk, login);
				}
				/**
				 * Update education details
				 */
				if (eduoinf.length > 0){
					for (int i = 0; i < eduoinf.length; i++){
						eduoinf[i].setUserId(users.getId());
						eduoinf[i].setCandidateIdNull(true);
						eduoinf[i].setuType("UPDATEID");
						educationInfoModel.update(eduoinf[i], request);
					}
				}
				/**
				 * Update experience details
				 */
				if (expinf.length > 0){
					for (int i = 0; i < expinf.length; i++){
						expinf[i].setUserId(users.getId());
						expinf[i].setCandidateIdNull(true);
						expinf[i].setuType("UPDATEID");
						experienceInfoModel.update(expinf[i], request);
					}
				}
				/**
				 * Update Salary Details
				 */
				if (details != null && details.length > 0){
					for (int i = 0; i < details.length; i++){
						details[i].setUserId(users.getId());
						details[i].setCandidateIdNull(true);
						details[i].setuType("UPDATEFOREMPLOYEE");
						salaryDetailModel.update(details[i], request);
					}
					new TDSUtility().reCalculate(users.getId());
				}
				/**
				 * Update Salary Info
				 */
				SalaryInfo[] salaryInfo=salaryInfoDao.findWhereBasicEquals(candidate.getId());
				if(salaryInfo!=null){
					for(SalaryInfo info:salaryInfo){
						//System.out.println("Perdiem Offered"+info.getPerdiemOffered());
						info.setUserId(users.getId());
						if(users.getId()>0) info.setUserIdNull(false);
						SalaryInfoPk pk=new SalaryInfoPk();
						pk.setId(info.getId());
						salaryInfoDao.update(pk, info);
						if(info.getPerdiemOffered()!=null && !info.getPerdiemOffered().equalsIgnoreCase("") && !info.getPerdiemOffered().equalsIgnoreCase("0")){
							perMaster.setUserId(users.getId());
							perMaster.setPerdiem(info.getPerdiemOffered());
							perMaster.setPerdiemFrom(profile.getDateOfJoining());
							perMaster.setCurrencyType("1");
							perdiemMasterDataDao.insert(perMaster);
						}
					}
					
				}
				
				
				/**
				 * Updating the userId column from CandidatePerdiemDetails
				 */
				if(candidate.getId() > 0)
				{
					CandidatePerdiemDetailsPk pkdetails = new CandidatePerdiemDetailsPk();
					pkdetails.setId(candidate.getId());
					CandidatePerdiemDetails[] detailsDto = null;
					detailsDto=perdiemDetailsDao.findWhereCandidateIdEquals(candidate.getId());
					if(detailsDto!=null){
						for(CandidatePerdiemDetails detail:detailsDto){
							detail.setUserId(String.valueOf(users.getId()));
							pkdetails.setId(detail.getId());
					         perdiemDetailsDao.update(pkdetails, detail);
					}
					}
				}
				
				
				
				/**
				 * Adding  new entry to fixed Perdiem table
				 */
				/*FixedCandidatePerdiemDao fixedDao=FixedCandidatePerdiemDaoFactory.create();
				FixedCandidatePerdiem perdiemFix=null;
				FixedPerdiemDao perDao=FixedPerdiemDaoFactory.create();
				perdiemFix=fixedDao.findByPrimaryKey(candidate.getId());
				if(perdiemFix!=null){
					FixedPerdiem perdiem=new FixedPerdiem();
					perdiem.setId(users.getId());
					perdiem.setPerdiem(perdiemFix.getPerdiem());
					perdiem.setPerdiemFrom(profile.getDateOfJoining());
					perDao.insert(perdiem);
					
				}*/
				
				/**
				 * Add in roll on table and roll on project map and empserReq map
				 */
				if (pMapping.getProjectId() > 0){
					PoDetails poDetails[] = poDetailsDao.findByDynamicWhere("PROJ_ID=? ORDER BY ID DESC", new Object[] { pMapping.getProjectId() });
					ChargeCode chargeCode[] = null;
					if (poDetails != null && poDetails.length > 0){
						chargeCode = chargeCodeDao.findByDynamicWhere("PO_ID=?", new Object[] { poDetails[0].getId() });
					}
					Levels levels = levelsDao.findByPrimaryKey(profileInfo.getLevelId());
					Divison divison = divisonDao.findByPrimaryKey(levels.getDivisionId());
					SimpleDateFormat uniqueIdformat = new SimpleDateFormat("ssMMdd");
					EmpSerReqMap empSerReqMap = new EmpSerReqMap();
					profileInfo.setUserId(usersPk.getId());
					String uniqueID = profileInfo.getUserId() + uniqueIdformat.format(new Date());
					empSerReqMap.setSubDate(new Date());
					empSerReqMap.setReqId("IN-RN-" + uniqueID);
					empSerReqMap.setReqTypeId(9);
					empSerReqMap.setRequestorId(loginSess.getUserId());
					empSerReqMap.setRegionId(divison.getRegionId());
					ProcessChain processChainDto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC  LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID WHERE ROLE_ID=(SELECT ROLE_ID FROM USER_ROLES UR LEFT JOIN USERS U ON U.ID=UR.USER_ID WHERE U.ID=?) AND MODULE_ID=18", new Object[] { new Integer(loginSess.getUserId()) })[0];
					empSerReqMap.setProcessChainId(processChainDto.getId());
					empSerReqMap.setNotify(processChainDto.getNotification());
					empMapPk = empSerReqMapDao.insert(empSerReqMap);
					RollOn rollOn = new RollOn();
					rollOn.setEmpId(users.getId());
					if (chargeCode != null && chargeCode.length > 0){
						rollOn.setChCodeId(chargeCode[0].getId());
					}
					rollOn.setEsrqmId(empMapPk.getId());
					rollOn.setCurrent(Short.parseShort("1"));
					rollOn.setStartDate(new Date());
					rollOnPk = rollOnDao.insert(rollOn);
					RollOnProjMap rollOnProjMap = new RollOnProjMap();
					rollOnProjMap.setRollOnId(rollOnPk.getId());
					ProjLocations location[] = proLocDao.findWhereProjIdEquals(profileInfo.getProjectId());
					if (location != null && location.length > 0){
						rollOnProjMap.setProjLocId(location[0].getId());
					} else rollOnProjMap.setProjLocId(0);
					rollOnProjMap.setProjId(pMapping.getProjectId());
					rollOnProjMapPk = rollOnProjMapDao.insert(rollOnProjMap);
					if (chargeCode != null && chargeCode.length > 0){
						//fix for ;post rollon, no of resources not getting updated
						PoEmpMap poEmpMapDto = new PoEmpMap();
						poEmpMapDto.setPoId(chargeCode[0].getPoId());
						poEmpMapDto.setEmpId(profileInfo.getUserId());
						poEmpMapDto.setRate("0.0");
						poEmpMapDto.setType(null);
						poEmpMapDto.setCurrency(null);
						poEmpMapDto.setInactive((short) 1);//INACTIVE=1 ==>
						poEmpMapDto.setCurrent((short) 1);//CURRENT=1 ==>
						insertedRow = poEmpMapDao.insert(poEmpMapDto);
					}
				}
				
				
				/**
				 * Update Retention Bonus if available
				 * 
				 * 
				 */
				SalaryInfo[] salaryInfoRetention=salaryInfoDao.findWhereBasicEquals(candidate.getId());
				if(salaryInfoRetention!=null && salaryInfoRetention.length>0){
					if(salaryInfoRetention[0].getRetentionBonus()!=null && !salaryInfoRetention[0].getRetentionBonus().equalsIgnoreCase("") && !salaryInfoRetention[0].getRetentionBonus().equalsIgnoreCase("0")){
						String str=salaryInfoRetention[0].getRetentionBonus().trim();
						double retBonus=Double.parseDouble(str);
						if(retBonus>0){
						retentionBonus.setUserId(users.getId());
						retentionBonus.setAmount(salaryInfoRetention[0].getRetentionBonus());					
					    retentionBonus.setRetentionInstallments(Integer.parseInt(salaryInfoRetention[0].getRetentionInstallments()));
					    retentionBonus.setRetentionStartDate(profile.getDateOfJoining());
					    retentionBonusDao.insert(retentionBonus);
					}
					}
				}
				
			} catch (Exception e){
				flag = false;
				logger.info("Failed to save Retention Bonus Info for user");
				e.printStackTrace();
			} finally{
				if (flag == false){
					/**
					 * cancel user roles
					 */
					UserRoles userRoles[] = uRolesDao.findWhereCandidateIdEquals(candidate.getId());
					if (userRoles != null && userRoles.length > 0){
						UserRolesPk userRolesPk = new UserRolesPk();
						userRoles[0].setUserIdNull(true);
						userRoles[0].setCandidateId(candidate.getId());
						userRolesPk.setId(userRoles[0].getId());
						uRolesDao.update(userRolesPk, userRoles[0]);
					}
					/**
					 * cancel login changes
					 */
					if (login != null){
						loginPk.setId(login.getId());
						login.setUserIdNull(true);
						login.setCandidateId(candidate.getId());
						loginDao.update(loginPk, login);
					}
					/**
					 * Cancel education details
					 */
					if (eduoinf.length > 0){
						for (int i = 0; i < eduoinf.length; i++){
							eduoinf[i].setUserIdNull(true);
							eduoinf[i].setCandidateId(candidate.getId());
							eduoinf[i].setuType("UPDATEID");
							educationInfoModel.update(eduoinf[i], request);
						}
					}
					/**
					 * Undo experience details
					 */
					if (expinf.length > 0){
						for (int i = 0; i < expinf.length; i++){
							expinf[i].setUserIdNull(true);
							expinf[i].setCandidateId(candidate.getId());
							expinf[i].setuType("UPDATEID");
							experienceInfoModel.update(eduoinf[i], request);
						}
					}
					/**
					 * cancel Salary Info
					 */
					if (details != null && details.length > 0){
						for (int i = 0; i < details.length; i++){
							details[i].setUserIdNull(true);
							details[i].setCandidateId(candidate.getId());
							details[i].setuType("UPDATEFOREMPLOYEE");
							salaryDetailModel.update(details[i], request);
						}
					}
					/**
					 * Cancell roll on changes
					 */
					if (empMapPk.getId() > 0){
						empSerReqMapDao.delete(empMapPk);
						if (rollOnPk.getId() > 0) rollOnDao.delete(rollOnPk);
						if (rollOnProjMapPk.getId() > 0) rollOnProjMapDao.delete(rollOnProjMapPk);
						if (insertedRow.getId() > 0) poEmpMapDao.delete(insertedRow);
					}
					/**
					 * Cancell in Contact Type
					 */
					ContactTypeDao contactTypeDao = ContactTypeDaoFactory.create();
					ContactType contactType[] = contactTypeDao.findWhereCandidateIdEquals(candidate.getId());
					if (contactType != null && contactType.length > 0){
						for (ContactType contactType2 : contactType){
							ContactTypePk contactTypePk = new ContactTypePk();
							contactTypePk.setId(contactType2.getId());
							contactType2.setCandidateId(candidate.getId());
							contactType2.setUserIdNull(true);
							contactTypeDao.update(contactTypePk, contactType2);
						}
					}
					/**
					 * delete user
					 */
					usersDao.delete(usersPk);
					/**
					 * update candidate as isEmployee =0
					 */
					candidate.setIsEmployee(new Short((short) 0));
					candidateDao.update(candidatePk, candidate);
				}
			}
			RegionsDao rDao = RegionsDaoFactory.create();
			if (flag){
				ProfileInfo tat_pInfo = null;
				if (candidate.getTatId() > 0){
					tat_pInfo = profileInfoDao.findWhereIdEquals(usersDao.findByPrimaryKey(candidate.getTatId()).getProfileId())[0];
				}
				Regions regions = rDao.findByLevelId(profile.getLevelId());
				Notification notification = new Notification(regions.getRefAbbreviation());
				Set<String> emailIds = notification.notifyDept(candidate, loginSess.getUserId(), usersPk.getId());
				emailIds.add(tat_pInfo.getOfficalEmailId());
				sendWelcomeMail(welcomeMail, profile.getHrSpoc());
				sendEmail(portalMail, candidate, users, emailIds);
			}
			//result = true;
		} catch (Exception e){
			flag = false;
			logger.info("Error in migrating candidate");
			e.printStackTrace();
		}
		return flag;
	}

	private void sendWelcomeMail(PortalMail welcomeMail, int hrSpocId) {
		ProfileInfoDao pDao = ProfileInfoDaoFactory.create();
		MailGenerator mailGenerator = new MailGenerator();
		try{
			String SQL = "ID = (SELECT PROFILE_ID FROM USERS WHERE ID = ?)";
			Object[] sqlParams = { hrSpocId };
			pDao.setMaxRows(1);
			ProfileInfo hrspoc = pDao.findByDynamicWhere(SQL, sqlParams)[0];
			welcomeMail.setHrSPOC(hrspoc.getFirstName() + " " + hrspoc.getLastName());
			welcomeMail.setTemplateName(MailSettings.EMPLOYEE_WELCOME);
			welcomeMail.setMailSubject("Welcome to Diksha - envision. empower. evolve");
			mailGenerator.invoker(welcomeMail);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void sendEmail(PortalMail portalEmail, Candidate candidate, Users user, Set<String> emailIds) {
		UsersDao usersDao = UsersDaoFactory.create();
		//	CandidateDao candidateDao = CandidateDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
		try{
			ProfileInfo cProfile = profileInfoDao.findByPrimaryKey(user.getProfileId());
			PersonalInfo cPersonal = personalInfoDao.findByPrimaryKey(user.getPersonalId());
			Users HRProfile = usersDao.findByPrimaryKey(cProfile.getHrSpoc());
			ProfileInfo hrProfile = profileInfoDao.findByPrimaryKey(HRProfile.getProfileId());
			Users RMProfile = usersDao.findByPrimaryKey(cProfile.getReportingMgr());
			ProfileInfo rmProfile = profileInfoDao.findByPrimaryKey(RMProfile.getProfileId());
			Levels levels = LevelsDaoFactory.create().findByPrimaryKey(cProfile.getLevelId());
			//allReceipientMailId[0]=hrProfile.getOfficalEmailId();
			//allReceipientMailId[1]=rmProfile.getOfficalEmailId();
			emailIds.add(hrProfile.getOfficalEmailId());
			emailIds.add(rmProfile.getOfficalEmailId());
			String[] allReceipientMailId = emailIds.toArray(new String[0]);
			portalEmail.setAllReceipientMailId(allReceipientMailId);
			portalEmail.setTemplateName(MailSettings.CANDIDATE_MARKED_AS_EMPLOYEE);
			portalEmail = DtoToBeanConverter.DtoToBeanConverter(portalEmail, candidate, cProfile, cPersonal, null, false);
			portalEmail.setHrSPOC(hrProfile.getFirstName());
			portalEmail.setRepoMngrAtClient(rmProfile.getFirstName());
			portalEmail.setLeaveType(levels.getLabel());//level
			portalEmail.setAmountAvailed(candidate.getId());
			portalEmail.setFileSources(null);
			portalEmail.setMailSubject("New Employee -  " + cProfile.getFirstName() + " -  DOJ [" + PortalUtility.formatDateddMMyyyy(cProfile.getDateOfJoining()) + "]");
			MailGenerator generator = new MailGenerator();
			generator.invoker(portalEmail);
		} catch (ProfileInfoDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsersDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			// TODO Auto-generated catch block";
			e.printStackTrace();
		}
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
				case MARKCANDIDATEASEMPLOYEE:
					boolean flag = migrateCandidate((CandidateSaveForm) form, request);
					if (!flag){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtomigratecandidate"));
					}
					break;
				default:
					break;
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean sendMailNotifications(Users user, HttpServletRequest request) {
		/**
		 * is-Active=2(seperated) is-Active=3(Absconding)
		 */
		MailGenerator mailGenerator = new MailGenerator();
		Login login = null;
		if (user.getStatus() == 2 || user.getStatus() == 3){
			ProfileInfo empprofileInfo = new ProfileInfo();
			PersonalInfo emppersonalInfo = new PersonalInfo();
			UsersDao usersDao = UsersDaoFactory.create();
			PortalMail pMail = new PortalMail();
			ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
			//PersonalInfoDao personalInfoDao = PersonalInfoDaoFactory.create();
			Users actionByUser = new Users();
			try{
				/**
				 * user who marks as seprated or absconding send him email
				 */
				// Action by persons info from action by coplumn in users table
				actionByUser = usersDao.findByPrimaryKey(user.getActionBy());
				//PersonalInfo loginpersonalInfo = personalInfoDao.findByPrimaryKey(actionByUser.getPersonalId());
				ProfileInfo loginprofileInfo = profielInfoDao.findByPrimaryKey(actionByUser.getProfileId());
				// Employee on whom action is taken
				Users userEmp = usersDao.findByPrimaryKey(user.getId());
				empprofileInfo = profielInfoDao.findByPrimaryKey(userEmp.getProfileId());
				// Reporting Manager for employee on whom action is taken
				Users reportingManager = usersDao.findByPrimaryKey(empprofileInfo.getReportingMgr());
				ProfileInfo ReportingManagerProfile = profielInfoDao.findByPrimaryKey(reportingManager.getProfileId());
				/**
				 * send email to Reporting manager,Finance Dept,IT Dept,RMG(SPOC part of RMG)
				 */
				UserLogin userLogin = userInfo(actionByUser);
				RegionsDao regionDao = RegionsDaoFactory.create();
				Regions region = regionDao.findByPrimaryKey(userLogin.getRegionId());
				Notification notification = new Notification(region.getRefAbbreviation());
				int FINANCE = notification.financeId;
				int RMG = notification.hrdId;
				int IT = notification.itAdminId;
				List<String> finance = getUsersByDivisionSeperation(FINANCE, null, user.getId());
				List<String> Itusers = getUsersByDivisionSeperation(IT, null, user.getId());
				List<String> RMGusers = getUsersByDivisionSeperation(RMG, null, user.getId());
				finance.addAll(Itusers);
				finance.addAll(RMGusers);
				finance.add(ReportingManagerProfile.getOfficalEmailId());
				String[] RecipientsCC = finance.toArray(new String[0]); // Collection to array
				logger.info("number of emails" + RecipientsCC.length);
				pMail.setAllReceipientcCMailId(RecipientsCC);
				if (user.getStatus() == SEPERATED){
					pMail.setMailSubject((" Employee Separated - " + empprofileInfo.getFirstName() + " " + empprofileInfo.getLastName() + " on " + PortalUtility.formatDateddMMyyyy(user.getDateOfSeperation())));
					pMail.setTemplateName(MailSettings.EMPLOYEE_SEPERATION_NOTIFY_USER);
					pMail.setDateOfSeperation(PortalUtility.formatDateddMMyyyy(user.getDateOfSeperation()));
				} else{
					pMail.setMailSubject(" Employee Absconding - " + empprofileInfo.getFirstName() + " " + empprofileInfo.getLastName() + " on " + PortalUtility.formatDateddMMyyyy(user.getDateOfSeperation()));
					pMail.setTemplateName(MailSettings.EMPLOYEE_ABSCONDING_NOTIFY_USER);
					pMail.setDateOfSeperation(PortalUtility.formatDateddMMyyyy(user.getDateOfSeperation()));// action
					// date
				}
				pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, null, empprofileInfo, emppersonalInfo, login, false);
				pMail.setRecipientMailId(loginprofileInfo.getOfficalEmailId());
				pMail.setEmpId(new Integer(user.getEmpId()).toString());
				pMail.setEmpLName(loginprofileInfo.getFirstName() + " " + loginprofileInfo.getLastName());
				pMail.setFileSources(null);
				mailGenerator.invoker(pMail);
				//				if ( userLogin != null )
				//				{
				//					if ( user.getStatus() == SEPERATED )
				//					{
				//						pMail.setMailSubject("Employee " + empprofileInfo.getFirstName() + " " + empprofileInfo.getLastName() + " is seperated");
				//						pMail.setTemplateName(MailSettings.EMPLOYEE_SEPERATION);
				//						pMail.setDateOfSeperation(PortalUtility.formatDate(user.getDateOfSeperation()));
				//						
				//					}
				//					else
				//					{
				//						pMail.setMailSubject("Employee " + empprofileInfo.getFirstName() + " " + empprofileInfo.getLastName() + " is  Absconding");
				//						pMail.setTemplateName(MailSettings.EMPLOYEE_ABSCONDING);
				//						pMail.setDateOfSeperation(PortalUtility.formatDate(user.getCreateDate()));// action
				//						// date
				//					}
				//					pMail.setAllReceipientMailId(Recipients);
				//					pMail.setEmpId(new Integer(user.getEmpId()).toString());
				//					pMail.setEmpLName(loginprofileInfo.getFirstName() + " " + loginprofileInfo.getLastName());
				//					pMail = DtoToBeanConverter.DtoToBeanConverter(pMail, null, empprofileInfo, emppersonalInfo, login, false);
				//					pMail.setFileSources(null);
				//					mailGenerator.invoker(pMail);
				//				}
			} catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	public static UserLogin userInfo(Users user) {
		//Users user1 = user;
		UserLogin userLogin = new UserLogin();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		try{
			ProfileInfo profileinfo = profielInfoDao.findByPrimaryKey(user.getProfileId());
			if (profileinfo != null && profileinfo.getLevelId() > 0){
				LevelsDao levelsDao = LevelsDaoFactory.create();
				Divison divison = new Divison();
				Regions region = new Regions();
				DivisonDao divisonDao = DivisonDaoFactory.create();
				RegionsDao regionsDao = RegionsDaoFactory.create();
				Levels level = levelsDao.findByPrimaryKey(profileinfo.getLevelId());
				divison = divisonDao.findByPrimaryKey(level.getDivisionId());
				if (divison.getParentId() == 0){
					userLogin.setDivisionId(level.getDivisionId());
					userLogin.setDivisionName(divison.getName());
				}
				region = regionsDao.findByPrimaryKey(divison.getRegionId());
				userLogin.setRegionId(region.getId());
				userLogin.setRegionName(region.getRegName());
			}// if ends
		}// try ends
		catch (RegionsDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DivisonDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LevelsDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userLogin;
	}

	public static List<String> getUsersByDivision(int id, HttpServletRequest request) {
		List<String> Mylist = new ArrayList<String>();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		try{
			Users users[] = usersDao.findByDynamicSelect(selectUsers + "  LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID " + "LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT ID FROM " + "DIVISON WHERE ID = ? OR PARENT_ID = ?) AND U.STATUS NOT IN(1,2,3);", new Integer[] { id, id });
			if (users != null){
				for (Users user : users){
					ProfileInfo profile = profielInfoDao.findByPrimaryKey(user.getProfileId());
					Mylist.add(profile.getOfficalEmailId());
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mylist;
	}
	
	public static List<String> getUsersByDivision1(int id) {
		List<String> Mylist = new ArrayList<String>();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		try{
			Users users[] = usersDao.findByDynamicSelect(selectUsers + "  LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID " + "LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT ID FROM " + "DIVISON WHERE ID = ? OR PARENT_ID = ?) AND U.STATUS NOT IN(1,2,3);", new Integer[] { id, id });
			if (users != null){
				for (Users user : users){
					ProfileInfo profile = profielInfoDao.findByPrimaryKey(user.getProfileId());
					Mylist.add(profile.getOfficalEmailId());
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mylist;
	}

	/**
	 * @param id
	 *            level id from notification from properties file
	 * @param request
	 * @return list of email id of users of that level
	 */
	public static List<String> getUsersByLevelId(int id, HttpServletRequest request) {
		List<String> Mylist = new ArrayList<String>();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		try{
			String sqlForFinanceHeadByLevel = selectUsers + " LEFT JOIN PROFILE_INFO P ON P.ID = U.PROFILE_ID  WHERE P.LEVEL_ID = ? AND U.STATUS NOT IN(1,2,3)";
			Users userBylevel[] = usersDao.findByDynamicSelect(sqlForFinanceHeadByLevel, new Object[] { new Integer(id) });
			if (userBylevel != null){
				for (Users user : userBylevel){
					ProfileInfo profile = profielInfoDao.findByPrimaryKey(user.getProfileId());
					Mylist.add(profile.getOfficalEmailId());
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mylist;
	}

	/**
	 * @param id
	 *            level id from notification from properties file
	 * @param request
	 * @return list of email id of users of that level
	 */
	public static Users[] getUsersIDByLevelId(int id, HttpServletRequest request) {
		UsersDao usersDao = UsersDaoFactory.create();
		//ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		Users userBylevel[] = null;
		try{
			String sqlForFinanceHeadByLevel = selectUsers + " LEFT JOIN PROFILE_INFO P ON P.ID = U.PROFILE_ID  WHERE P.LEVEL_ID = ? AND U.STATUS NOT IN(1,2,3); ";
			userBylevel = usersDao.findByDynamicSelect(sqlForFinanceHeadByLevel, new Object[] { new Integer(id) });
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userBylevel;
	}

	/**
	 * @param id
	 *            level id from notification from properties file
	 * @param request
	 * @return list of email id of users of that level
	 */
	public static Users[] getUsersIDByDivisionId(int id, HttpServletRequest request) {
		UsersDao usersDao = UsersDaoFactory.create();
		//ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		Users userByDivision[] = null;
		try{
			userByDivision = usersDao.findByDynamicSelect(selectUsers + "  LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID " + "LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT ID FROM " + "DIVISON WHERE ID = ? OR PARENT_ID = ?) AND U.STATUS NOT IN(1,2,3);", new Integer[] { id, id });
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userByDivision;
	}

	public static List<String> getUsersByDivisionSeperation(int id, HttpServletRequest request, int empID) {
		List<String> Mylist = new ArrayList<String>();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		try{
			Users users[] = usersDao.findByDynamicSelect(selectUsers + "  LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID " + "LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT ID FROM " + "DIVISON WHERE ID = ? OR PARENT_ID = ?) AND U.ID != ? AND U.STATUS NOT IN(1,2,3);", new Integer[] { id, id, empID });
			if (users != null){
				for (Users user : users){
					ProfileInfo profile = profielInfoDao.findByPrimaryKey(user.getProfileId());
					if (profile.getOfficalEmailId() != null){
						Mylist.add(profile.getOfficalEmailId());
					}
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mylist;
	}

	public static List<String> getUsersByParentId(int id, HttpServletRequest request) {
		List<String> Mylist = new ArrayList<String>();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		try{
			Users users[] = usersDao.findByDynamicSelect(selectUsers + "  LEFT JOIN LEVELS L ON L.ID = U.LEVEL_ID " + "LEFT JOIN DIVISON D ON L.DIVISION_ID = D.ID WHERE D.ID IN (SELECT ID FROM " + "DIVISON WHERE PARENT_ID = ?)AND U.STATUS NOT IN(1,2,3);", new Integer[] { id });
			if (users != null){
				for (Users user : users){
					ProfileInfo profile = profielInfoDao.findByPrimaryKey(user.getProfileId());
					Mylist.add(profile.getOfficalEmailId());
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mylist;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Users user = new Users();
		UsersPk usersPk = new UsersPk();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		switch (UpdateTypes.getValue(form.getuType())) {
		/**
		 * Update for user table including id's and enable
		 * disable/seperated/absconding etc.
		 */
			case UPDATE:
				user = (Users) form;
				usersPk.setId(user.getId());
				Date date = new Date();
				short stat = 0;
				Login login = Login.getLogin(request);
				/**
				 * User cannot be seperated if he has active request under him
				 */
				StringBuffer sb = checkActiveRequest(usersPk);
				String bufferString = new String(sb);
				if (!bufferString.equals("")){
					logger.error("User has active request");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.separation.request.error", sb.toString()));
					result.setForwardName("success");
					return result;
				}
				/**
					* logged_in USER cannot separate himself
					*/

				if (login.getUserId() == user.getId()){
				logger.error("user_separation : user_id=" + login.getUserId() + " tried self-separation");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.self.separation"));
				result.setForwardName("success");
				return result;
				}
				/**
				 * check if the user being separated has other users as resources (ex. the user being marked for separation is HR_SPOC or RM)
				 */
				try{
					Integer[] hrSpocsFetched = profielInfoDao.findByDistinct("HR_SPOC");
					if (hrSpocsFetched != null && hrSpocsFetched.length > 0){
						if (Arrays.asList(hrSpocsFetched).contains(user.getId())){
							Users[] resources = usersDao.findByDynamicSelect("SELECT * FROM USERS U WHERE U.PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE HR_SPOC=?) AND STATUS=?", new Object[] { new Integer(user.getId()), new Integer(0) });
							if (resources != null && resources.length > 0){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.separation.hrspoc.error"));
								result.setForwardName("success");
								return result;
							}
						}
					}
					Integer[] rmFetched = profielInfoDao.findByDistinct("REPORTING_MGR");
					if (rmFetched != null && rmFetched.length > 0){
						if (Arrays.asList(rmFetched).contains(user.getId())){
							Users[] resources = usersDao.findByDynamicSelect("SELECT * FROM USERS U WHERE U.PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE REPORTING_MGR=?) AND STATUS=?", new Object[] { new Integer(user.getId()), new Integer(0) });
							if (resources != null && resources.length > 0){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.separation.rm.error"));
								result.setForwardName("success");
								return result;
							}
						}
					}
				} catch (UsersDaoException ex){
					logger.error("user_separation pre-process : exception was thrown when trying to fetch row from users for user_id=" + user.getId(), ex);
				} catch (ProfileInfoDaoException ex){
					logger.error("user_separation pre-process : exception was thrown when trying to fetch distinct hr_spoc/r.m from profile_info", ex);
				}
				try{
					/**
					 * Below check if request is for disable employee Status =1
					 * single list for disable candidate with Status
					 * =0(Re-Employed) Status =1(disabled) Status=2(Seperated)
					 * Status=3(Absconding)
					 */
					Users disableuser = usersDao.findByPrimaryKey(user.getId());
					if (user.getStatus() == 0 || user.getStatus() == 1 || user.getStatus() == 2 || user.getStatus() == 3){
						if (user.getStatus() == 2 && !(PortalUtility.formatDate(date).equals(PortalUtility.formatDate(user.getDateOfSeperation())))){
							if (date.compareTo(user.getDateOfSeperation()) == 1){
								disableuser.setStatus(user.getStatus());
							} else{
								disableuser.setStatus(stat);
							}
						} else{
							disableuser.setStatus(user.getStatus());
						}
						disableuser.setDateOfSeperation(user.getDateOfSeperation());// passing date of	 seperation  to set
						// in user profile
						disableuser.setActionBy(login.getUserId());// set action by:id of user  who marks as separated
						usersDao.update(usersPk, disableuser);
						if (user.getStatus() == 2 || user.getStatus() == 3){
							/**
							 * send email to relsted departments and persons
							 */
							sendMailNotifications(disableuser, request);// send email in case of seperated and absconding to
							/**
							 * Update date of seperation
							 */
							if (user.getStatus() == 2 || user.getStatus() == 3){
								ProfileInfoPk profileInfoPk = new ProfileInfoPk();
								ProfileInfo profile = profielInfoDao.findByPrimaryKey(disableuser.getProfileId());
								profile.setDateOfSeperation(user.getDateOfSeperation());
								profileInfoPk.setId(profile.getId());
								profielInfoDao.update(profileInfoPk, profile);
							}
						if (user.getStatus() == 2) {
							DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();

							DocumentMapping dmpo = new DocumentMapping();

							dmpo.setDocumentId(user.getBgc_Seperate());
							dmpo.setUserSeprationId(user.getId());
							mappingDao.insert(dmpo);

						}

					}
					} else{
						usersDao.update(usersPk, user);
					}
					user.setCreateDate(disableuser.getCreateDate());// date when marked as absconding or action taken as seperated
					// disableuser.setSeparationInfo(id)// insert in separation table
					// insert details in separated table and update user// with id
					//					String[] separatedColumns=user.getSeperatedString().split(";");
					//					Separation separation=setSeparation(separatedColumns);
					//					separation.setUserId(disableuser.getId());
					//					SeparationDao separationDao = SeparationDaoFactory.create();
					//					separationDao.insert(separation);
					/**
					 * When an employee is separated, he will be removed from associated projects
					 */
					try{
						ProjectMappingDao projectMapppingDao = ProjectMappingDaoFactory.create();
						ProjectMapping[] associatedProjects = projectMapppingDao.findWhereUserIdEquals(user.getId());
						if (associatedProjects.length > 0) logger.debug("user_separation invoked for USER_ID=" + user.getId() + ". . . found associated projects");
						else{
							logger.debug("user_separation invoked for USER_ID=" + user.getId() + ". . . user was not associated with any projects");
							break;
						}
						for (ProjectMapping proj : associatedProjects){
							projectMapppingDao.delete(new ProjectMappingPk(proj.getId()));
							logger.debug("user_separation : deleted a row from PROJECT_MAPPING with ID=" + proj.getId());
						}
					} catch (ProjectMappingDaoException ex){
						logger.error("USER SEPARATION : EXCEPTION WAS THROWN WHEN TRYING TO FETCH/DELETE A ROW FROM PROJECT_MAPPING FOR USER_ID=" + user.getId(), ex);
					}
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case VALIDATEEMPLOYEEID:
				try{
					DropDown dropDown = (DropDown) form;
					Users users[] = usersDao.findByDynamicWhere("EMP_ID = ? AND EMP_ID !=0", new Object[] { dropDown.getKey1() });
					if (users != null && users.length > 0){
						dropDown.setDetail(new Boolean(true)); // emp id exists
					} else{
						dropDown.setDetail(new Boolean(false));// emp id dose  not  exists
					}
					request.setAttribute("actionForm", dropDown);
				} catch (UsersDaoException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case GENERATEEMPLOYEEID:
				try{
					DropDown dropDown = (DropDown) form;
					Users userEMP = generateEmpId(dropDown.getKey1());
					request.setAttribute("actionForm", userEMP);
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case MARKASINCOMPLETE:
				try{
					user = (Users) form;
					if (user.getUserIds() != null && user.getUserIds().length > 0) JDBCUtiility.getInstance().update("UPDATE USERS SET COMPLETE=0 WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(user.getUserIds()) + ")", null);
					request.setAttribute("actionForm", "");
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case MARKASCOMPLETE:
				try{
					user = (Users) form;
					if (user.getUserIds() != null && user.getUserIds().length > 0) JDBCUtiility.getInstance().update("UPDATE USERS SET COMPLETE=1 WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(user.getUserIds()) + ")", null);
					request.setAttribute("actionForm", "");
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
		}// switch ends
		return result;
	}

	private StringBuffer checkActiveRequest(UsersPk usersPk) {
		StringBuffer stringBuffer = new StringBuffer();
		try{
			CandidateReq candidateReq[] = CandidateReqDaoFactory.create().findWhereAssignedToEquals(usersPk.getId());
			//SodexoReq sodexoReq[] = SodexoReqDaoFactory.create().findWhereAssignedToEquals(usersPk.getId());
			LeaveMaster leaveMaster[] = LeaveMasterDaoFactory.create().findWhereAssignedToEquals(usersPk.getId());
			TravelReq travelReq[] = TravelReqDaoFactory.create().findWhereAssignedToEquals(usersPk.getId());
			ReimbursementReq reimbursementReq[] = ReimbursementReqDaoFactory.create().findByDynamicWhere(" ASSIGN_TO=? AND ESR_MAP_ID NOT IN (SELECT ID FROM EMP_SER_REQ_MAP WHERE REQUESTOR_ID=? AND REQ_TYPE_ID=5)", new Object[] { usersPk.getId(), usersPk.getId() });
			ItRequest itRequest[] = ItRequestDaoFactory.create().findWhereAssignToEquals(usersPk.getId());
			//TravelReq req[] = TravelReqDaoFactory.create().findWhereAssignedToEquals(usersPk.getId());
			//LoanRequest loanRequest[] = LoanRequestDaoFactory.create().findWhereAssignToEquals(usersPk.getId());
			if (candidateReq != null && candidateReq.length > 0){
				for (CandidateReq single : candidateReq){
					if (single.getStatus().equalsIgnoreCase(Status.PENDINGAPPROVAL)){
						stringBuffer.append("Candidate Request|");
						break;
					}
				}
			}
			/*if (sodexoReq != null && sodexoReq.length > 0){
				for (SodexoReq single : sodexoReq){
					SodexoDetails sodexoDetails = SodexoDetailsDaoFactory.create().findByPrimaryKey(single.getSdId());
					if (sodexoDetails.getStatus() == Status.getStatusId(Status.REQUESTRAISED)){
						stringBuffer.append("Sodexo Request|");
						break;
					}
				}
			}*/
			if (leaveMaster != null && leaveMaster.length > 0){
				for (LeaveMaster single : leaveMaster){
					if (single.getStatus().equalsIgnoreCase((Status.REQUESTRAISED))){
						stringBuffer.append("Leave Request|");
						break;
					}
				}
			}
			if (travelReq != null && travelReq.length > 0){
				for (TravelReq single : travelReq){
					if (single.getStatus() == Status.getStatusId((Status.REQUESTRAISED))){
						stringBuffer.append("Travel Request|");
						break;
					}
				}
			}
			if (reimbursementReq != null && reimbursementReq.length > 0){
				for (ReimbursementReq single : reimbursementReq){
					if (single.getStatus().equalsIgnoreCase((Status.REQUESTRAISED))){
						stringBuffer.append("Reimbursement Request|");
						break;
					}
				}
			}
			if (itRequest != null && itRequest.length > 0){
				for (ItRequest single : itRequest){
					if (single.getStatus().equalsIgnoreCase((Status.REQUESTRAISED))){
						stringBuffer.append("It Request|");
						break;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return stringBuffer;
	}

	public static synchronized Users generateEmpId(int userId) throws Exception {
		UsersDao usersDao = UsersDaoFactory.create();
		Users userEMP = usersDao.findByPrimaryKey(userId);
		Regions reg_id = RegionsDaoFactory.create().findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { userId })[0];
		List<Object> empidObj = JDBCUtiility.getInstance().getSingleColumn("SELECT MAX(EMP_ID) + 1 AS ID FROM USERS WHERE LEVEL_ID IN (SELECT ID FROM LEVELS L WHERE DIVISION_ID IN (SELECT ID FROM DIVISON WHERE REGION_ID=?))", new Object[] { reg_id.getId() });
		userEMP.setEmpId(((Number) empidObj.get(0)).intValue());
		usersDao.update(new UsersPk(userEMP.getId()), userEMP);
		return userEMP;
	}

	//	public Separation setSeparation(String[] separationColumns){
	//		Separation separation = new Separation();
	//		/*seperatedString=lastDay~;
	//		typeOfDeparture~;
	//		reasonForSeparation~;
	//		note~;rehire~;rehireNote~;
	//		lastDateOfPayment~;amountPaid~;
	//		noticePeriodDue~;otherDues~;duesNote~;
	//		reasonForResignation~;resignationNote~;
	//		dateOfResignation~;exitProcess~;userId~*/
	//		try
	//		{
	//			separation.setLastDay(PortalUtility.StringToDate(separationColumns[0].split("~")[1]));
	//			separation.setTypeOfDeparture(separationColumns[1].split("~")[1]);
	//			separation.setReasonForSeparation(Integer.parseInt(separationColumns[2].split("~")[1]));
	//			separation.setNote(separationColumns[3].split("~")[1]);
	//			separation.setRehire(Short.parseShort(separationColumns[4].split("~")[1]));
	//			separation.setRehireNote(separationColumns[5].split("~")[1]);
	//			separation.setLastDateOfPayment(PortalUtility.StringToDate(separationColumns[6].split("~")[1]));
	//			separation.setAmountPaid(separationColumns[7].split("~")[1]);
	//			separation.setNoticePeriodDue(separationColumns[8].split("~")[1]);
	//			separation.setOtherDues(separationColumns[9].split("~")[1]);
	//			separation.setDuesNote(separationColumns[10].split("~")[1]);
	//			separation.setReasonForResignation(separationColumns[11].split("~")[1]);
	//			separation.setResignationNote(separationColumns[12].split("~")[1]);
	//			separation.setDateOfResignation(PortalUtility.StringToDate(separationColumns[13].split("~")[1]));
	//			separation.setExitProcess(Integer.parseInt(separationColumns[14].split("~")[1]));
	//		}
	//		catch (ParseException e)
	//		{
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return separation;
	//	}
	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		Login logonForm = (Login) form;
		ActionResult result = null;
		switch (ActionMethods.ValidationTypes.getValue(logonForm.getvType())) {
			case IsValidSession:{
				logger.debug("validation user session.....");
				result = new ActionResult();
				logger.trace("Logon form ..." + logonForm);
				request.getSession(false).setAttribute("logonForm", logonForm);
				result.setForwardName(ActionResult.SUCCESS);
				break;
			}
			default:
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		Login logonForm = (Login) form;
		ActionResult result = null;
		switch (ActionMethods.ExecuteTypes.getValue(logonForm.geteType())) {
			case LOGOUT:{
				request.getSession(false).invalidate();
				result = new ActionResult();
				result.setDispatchDestination("index.jsp");
				break;
			}
			case CHANGEPASSWORD:{
				result = new ActionResult();
				LoginDao loginDao = LoginDaoFactory.create();
				String encrypted = DesEncrypterDecrypter.getInstance().encrypt(logonForm.getPassword());
				String userSql = "USER_ID=" + logonForm.getUserId() + " AND PASSWORD='" + encrypted + "'";
				try{
					Login[] loginDto = loginDao.findByDynamicWhere(userSql, null);
					if (loginDto.length != 0){
						for (Login login : loginDto){
							LoginPk loginPk = login.createPk();
							loginPk.setId(login.getId());
							String newEncrypted = DesEncrypterDecrypter.getInstance().encrypt(logonForm.getNewPassword());
							login.setPassword(newEncrypted);
							loginDao.update(loginPk, login);
						}
						result.setForwardName(ActionResult.SUCCESS);
					} else{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalid.password"));
					}
				} catch (LoginDaoException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			default:
				break;
		}
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		return null;
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		boolean isUpload = true;
		Integer fieldsId[] = new Integer[fileItems.size()];
		int i = 0;
		for (FileItem fileItem2 : fileItems){
		//	logger.info("Inside user model.");
			logger.info("FileName: " + fileItem2.getName());
		//	logger.info("FileType: " + fileItem2.getContentType());
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
					} catch (DocumentsDaoException e){
						e.printStackTrace();
					}
					i++;
				} else{
					/**
					 * send an error message
					 */
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
	Attachements attachements = new Attachements();
		Users user = (Users) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		path = PropertyLoader.getEnvVariable() + seprator + path;
		/**
		 * Get filename from id
		 */
		//PortalData portalData = new PortalData();
		//path = portalData.getfolder(path);
		path = path + "Employee Details";
		switch(DownloadTypes.getValue(form.getdType())){
			case DOCSUSER :
				
				try{
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					Documents docNew = new Documents();
					docNew = documentsDao.findByPrimaryKey(user.getDocid());
					attachements.setFileName(docNew.getFilename());
					attachements.setFilePath(path + seprator + docNew.getFilename());
				} catch (Exception e){
					e.printStackTrace();
				}
				
			break;
			
			case ALLEMPDETAILS :
				
				try{
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
	
					//Documents docNew = new Documents();
					//docNew = documentsDao.findByPrimaryKey(user.getDocid());
					/*String prevExp = "0-0-0";
					String currExp = "0-0-0";
					String totalExp = "0-0-0";*/
				
					SalaryDetailModel Tot = new SalaryDetailModel();
					ProfileInfo profileInfo = null, profileInfos[] = null;
					String month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());;
					
					/*SalaryDetailModel Tot = new SalaryDetailModel();
					Salary salaryStack = Tot.getSalary(4, true, SalaryDetailModel.TYPE_NORMAL);
					*/
					
					
					List<String[]> empData = documentsDao.findinternalReportData();
					List<String[]> responseData=new ArrayList<String[]>();
					String[] columns = new String[11];
					columns=to_initialize(columns);
					for(String[] data : empData){
				    Salary salaryStack = Tot.getSalary(Integer.parseInt(data[0]), true, SalaryDetailModel.TYPE_NORMAL);
					   String prevExp = "0-0-0";
						String currExp = "0-0-0";
						String totalExp = "0-0-0";
						String[] str1=new String[11];
						str1=to_initialize(str1);
						ExperienceInfo[] ExpDetails = 	ExperienceInfoDaoFactory.create().findWhereUserIdEquals(Integer.parseInt(data[0]));
						ExperienceBean[] edu = new ExperienceBean[ExpDetails.length];
						for (int i = 0; i < ExpDetails.length; i++){
							ExperienceBean expBean = DtoToBeanConverter.DtoToBeanConverter(ExpDetails[i]);
						    prevExp = addExperiance(expBean.getDateJoining(), expBean.getDateRelieving(), prevExp);
							edu[i] = expBean;
						}
						profileInfos = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT P.* FROM PROFILE_INFO P WHERE P.ID=?", new Object[] {Integer.parseInt(data[1]) });
						
						if(profileInfos != null && profileInfos.length == 1){
							profileInfo = profileInfos[0];
						}
						if(profileInfo != null){
							currExp = PortalUtility.getDuration(profileInfo.getDateOfJoining(), (user.getStatus() == 0) ? new Date() : (profileInfo.getDateOfSeperation() == null) ? new Date() : profileInfo.getDateOfSeperation());
						}
						//for(String datset:data){
							
						//ExperienceInfo[] ExpDetails = 	ExperienceInfoDaoFactory.create().findWhereUserIdEquals(Integer.parseInt(data[0]));
						//ExperienceInfo[] ExpDetails = ExperienceInfoDaoFactory.create().findByDynamicSelect(" SELECT DATE_JOINING, DATE_RELIEVING FROM EXPERIENCE_INFO ",new Object[] {  new Integer(Integer.parseInt(data[0]))});
						//ExperienceBean expBean = DtoToBeanConverter.DtoToBeanConverter(ExpDetails);
						totalExp = addExperiance(currExp, prevExp);
						
						String totalE[] = totalExp.split("-");
						String totalEx = totalE[0]+"-"+"YEAR"+"/"+totalE[1]+"-"+"MONTH"+"/"+totalE[2]+"-"+"DAY";
						
				
						
						if(user.getEmplid() == 1){
							str1[0]=data[2];
							columns[0] = "EMP ID";
						}
						if(user.getEmpName() == 1){
							str1[1]=data[3];
							columns[1] = "EMP NAME";
						} 
						if(user.getEmailId() == 1){
							//if( data[4] == null){
								//str1[2]="test@dikshatech.com";
								//columns[2] = "OFFICIAL MAIL ID";
							//}else{
								str1[2]=data[4];
								columns[2] = "OFFICIAL MAIL ID";
							//}
						} 
						if(user.getContactNo() == 1){
							//if( data[5] == null){
							//	str1[3]="0000000000";
							//	columns[3] = "CONTACT NUMBER";
							//}else{
							str1[3]=data[5];
							columns[3] = "CONTACT NUMBER";
							//}
						}
						if(user.getDesignation() == 1){
							//if(data[6] == null){
							//	str1[4]="TEST";
							//	columns[4] = "DESIGNATION";
							//}else{
							str1[4]=data[6];
							columns[4] = "DESIGNATION";
							//}
						} 
						if(user.getEducation() == 1){
							//if(data[7]==null){
								//str1[5]="TEST";
								//str1[5]=data[7];
								//columns[5] = "PROJECT NAME ";
							//}else{
							str1[5]=data[7];
								//str1[5]="TEST";
							columns[5] = "EDUCATION DETAILS";
							//}
						} /*if(user.getClient() == 1){
							str1[6]=data[6];
							columns[6] = " CLIENT DETAILS  ";	}*/ 
							
						;
						if(user.getCtc()==1){
								str1[6]= Float.toString(salaryStack.getAnnualCTC());
						//		System.out.println("amt" +str1[6]);
								columns[6] = " CTC ";} 
							
						
						if(user.getProject() == 1){
							//if( data[9] == null){
							//	str1[7]="MCA";
							//	columns[7] = "EDUCATION DETAILS ";
							//}else{
							str1[7]=data[9];
							columns[7] = "PROJECT NAME";
							//}
						}
						if(user.getChargeCode() == 1){
							//if( data[10] == null){
							//	str1[8]="20000";
							//	columns[8] = " CTC   ";
							//}else{
							str1[8]=data[10];
							columns[8] = " CHARGE_CODE ";
							//}
						}
						if(user.getExperience() == 1){
							//str1[9]=data[11];
							//if(totalEx.isEmpty() || totalEx == null){
							//	str1[9]="1-YEAR/1-MONTH/1-DAY";
							//	columns[9] = " EXPERIENCE DETAILS ";
							//}else{
								str1[9]=totalEx;
								columns[9] = " EXPERIENCE DETAILS ";
							//}
							
						}
						if(user.getLocation() ==1)
						{
							str1[10]=data[12];
							columns[10] = " LOCATION ";
						}
						
						/*	if(user.getEmplid() == 1){*/
					//	str1[10]=data[0]; 
					//	columns[10] = "USER ID";
					/*}*/
						//}	
						responseData.add(str1);
					}
					
					//attachements.setFileName(new GenerateXls().generateAllEmployeeDetailsReportInExcel(documentsDao.findinternalReportData(), path + File.separator + " Employee Details Internal Report _" + month + ".xls", month));
					attachements.setFileName(new GenerateXls().generateAllEmployeeDetailsReportInExcel(responseData, path + File.separator + " Employee Details Internal Report _" + new Date() + ".xls", month,columns));
					attachements.setFilePath(path + seprator+ attachements.getFileName());
					
				}catch(Exception e){
					e.printStackTrace();
				}
		default:
			break;
				
			
		}
		return attachements;
	}
	private String[] to_initialize(String[] strdata){
		for(int i=0;i<strdata.length;i++){
			strdata[i]="";
		}
		return strdata;
		}
	public String addExperiance(Date dateJoining, Date dateRelieving, String totalExp)throws NullPointerException {
        
		String currentExp =null;
		 if (dateJoining != null || dateRelieving != null){
			 
				 currentExp = PortalUtility.getDuration(dateJoining, dateRelieving);
	    	    return addExperiance(currentExp, totalExp);
		 
		 }
		 else
		 {
			 
			 return addExperiance(currentExp, totalExp);
	       
		 }
		 
        
        
        
	
	
	}
	String addExperiance(String currExp, String prevExp) {
		String totalExp = "0-0-0";
		if (currExp != null && prevExp != null){
			String currExpArr[] = currExp.split("-");
			String ttlExpArr[] = prevExp.split("-");
			if (currExpArr.length == 3 && ttlExpArr.length == 3){
				int days = Integer.parseInt(ttlExpArr[2]) + Integer.parseInt(currExpArr[2]);
				int months = Integer.parseInt(ttlExpArr[1]) + Integer.parseInt(currExpArr[1]) + (days / 30);
				ttlExpArr[2] = (days % 30) + "";
				ttlExpArr[0] = (Integer.parseInt(ttlExpArr[0]) + Integer.parseInt(currExpArr[0]) + (months / 12)) + "";
				ttlExpArr[1] = (months % 12) + "";
				totalExp = ttlExpArr[0]+ "-" + ttlExpArr[1]+ "-" + ttlExpArr[2];
			}
		}
		return totalExp;
	}
}
