package com.dikshatech.portal.forms;

import java.util.Locale;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.struts.action.ActionForm;

import com.dikshatech.portal.actions.ActionComponents;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.models.AccessibilityLevelModel;
import com.dikshatech.portal.models.AppraisalModel;
import com.dikshatech.portal.models.BankDetailsModel;
import com.dikshatech.portal.models.BonusReconciliationModel;
import com.dikshatech.portal.models.CalendarModel;
import com.dikshatech.portal.models.CandidateModel;
import com.dikshatech.portal.models.ClientModel;
import com.dikshatech.portal.models.CommitmentModel;
import com.dikshatech.portal.models.CompanyModel;
import com.dikshatech.portal.models.ContactTypeModel;
import com.dikshatech.portal.models.DivisionModel;
import com.dikshatech.portal.models.EducationInfoModel;
import com.dikshatech.portal.models.EmploymentTypeModel;
import com.dikshatech.portal.models.EscalationModel;
import com.dikshatech.portal.models.ExitModel;
import com.dikshatech.portal.models.ExperienceInfoModel;
import com.dikshatech.portal.models.FBPModel;
import com.dikshatech.portal.models.FaqModel;
import com.dikshatech.portal.models.FestivalWishesModel;
import com.dikshatech.portal.models.FinanceInfoModel;
import com.dikshatech.portal.models.InboxModel;
import com.dikshatech.portal.models.InsurancePolicyModel;
import com.dikshatech.portal.models.InvoiceReconciliationModel;
import com.dikshatech.portal.models.IssueTicketModel;
import com.dikshatech.portal.models.ItModel;
import com.dikshatech.portal.models.LeaveModel;
import com.dikshatech.portal.models.LevelsModel;
import com.dikshatech.portal.models.LoanModel;
import com.dikshatech.portal.models.LoginModel;
import com.dikshatech.portal.models.ModuleModel;
//import com.dikshatech.portal.models.NDAOnlineExamModel;
import com.dikshatech.portal.models.NewsModel;
import com.dikshatech.portal.models.NomineeModel;
import com.dikshatech.portal.models.PassportInfoModel;
import com.dikshatech.portal.models.PayslipModel;
import com.dikshatech.portal.models.PersonalInfoModel;
import com.dikshatech.portal.models.PoDetailsModel;
import com.dikshatech.portal.models.ProcessModel;
import com.dikshatech.portal.models.ProfileInfoModel;
import com.dikshatech.portal.models.ProjectModel;
import com.dikshatech.portal.models.ProjectTasksModel;
import com.dikshatech.portal.models.ReconciliationModel;
import com.dikshatech.portal.models.ReferFriendModel;
import com.dikshatech.portal.models.RegionModel;
import com.dikshatech.portal.models.ReimbursementModel;
import com.dikshatech.portal.models.ResourceReqModel;
import com.dikshatech.portal.models.ResumeManagementModel;
import com.dikshatech.portal.models.RetentionBonusReconciliationModel;
import com.dikshatech.portal.models.RmgTimeSheetModel;
//import com.dikshatech.portal.models.RmgTimeSheetModel;
import com.dikshatech.portal.models.RoleModel;
import com.dikshatech.portal.models.RollOnModel;
import com.dikshatech.portal.models.SalaryDetailModel;
import com.dikshatech.portal.models.SalaryReconciliationModel;
import com.dikshatech.portal.models.SkillSetModel;
import com.dikshatech.portal.models.SodexoModel;
import com.dikshatech.portal.models.TaxBenefitDeclarationModel;
import com.dikshatech.portal.models.TimeSheetModel;
import com.dikshatech.portal.models.TravelModel;
import com.dikshatech.portal.models.UserModel;

public class PortalForm extends ActionForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	String						cType;
	String						rType;
	String						vType;
	String						sType;
	String						uType;
	String						dType;
	String						eType;
	String						user;
	Integer						userId;
	protected int				candidateId;
	Integer						actionFlag;
	private Object				object;
	// gurunath added for filtering
	private String				searchyear;
	private String				month;
	private String				toMonth;
	private String				searchName;
	private String				device;
	private String				forwardName;
	private String 				otherEmailId_Candidate;
	private String				initialBonusAmount;
	static{
		String pattern = "yyyy/MM/dd";
		Locale locale = Locale.getDefault();
		DateLocaleConverter converter = new DateLocaleConverter(null, locale, pattern);
		converter.setLenient(false);
		ConvertUtils.register(converter, java.util.Date.class);
	}
	
	public String getInitialBonusAmount() {
		return initialBonusAmount;
	}

	public void setInitialBonusAmount(String initialBonusAmount) {
		this.initialBonusAmount = initialBonusAmount;
	}

	public String getcType() {
		return cType;
	}

	public String getrType() {
		return rType;
	}

	public String getvType() {
		return vType;
	}

	public String getsType() {
		return sType;
	}

	public String getuType() {
		return uType;
	}

	public String getdType() {
		return dType;
	}

	public String geteType() {
		return eType;
	}

	public String getUser() {
		return user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public void setrType(String rType) {
		this.rType = rType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public void setuType(String uType) {
		this.uType = uType;
	}

	public void setdType(String dType) {
		this.dType = dType;
	}

	public void seteType(String eType) {
		this.eType = eType;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ActionMethods getComponentObject(String cType) {
		ActionMethods actionMethods = null;
		ActionComponents actionComponent = ActionComponents.getValue(cType);
		switch (actionComponent) {
			case USER:{
				actionMethods = getUserComponent();
				break;
			}
			/*case USERS:{
				actionMethods = getUserComponent();
				break;
			}*/
			case LOGIN:{
				actionMethods = getLoginComponent();
				break;
			}
			case COMPANY:{
				actionMethods = getCompanyComponent();
				break;
			}
			case DIVISION:{
				actionMethods = getDivisionComponent();
				break;
			}
			case MODULES:{
				actionMethods = getModuleComponent();
				break;
			}
			case ROLES:{
				actionMethods = getRolesComponent();
				break;
			}
			case REGION:{
				actionMethods = getRegionComponent();
				break;
			}
			case CALENDAR:{
				actionMethods = getCalendarComponent();
				break;
			}
			case LEVELS:{
				actionMethods = getLevelComponent();
				break;
			}
			case LEAVE:{
				actionMethods = getLeaveComponent();
				break;
			}
			case CANDIDATE:{
				actionMethods = getCandidateComponent();
				break;
			}
			case PROFILEINFO:{
				actionMethods = getProfileInfoComponent();
				break;
			}
			case EXPERIENCEINFO:{
				actionMethods = getExperienceInfoComponent();
				break;
			}
			case EDUCATIONINFO:{
				actionMethods = getEducationInfoComponent();
				break;
			}
			case FINANCEINFO:{
				actionMethods = getFinanceInfoComponent();
				break;
			}
			case PASSPORTINFO:{
				actionMethods = getPassportInfoComponent();
				break;
			}
			case PERSONALINFO:{
				actionMethods = getPersonalInfoComponent();
				break;
			}
			case NOMINEEINFO:{
				actionMethods = getNomineeInfo();
				break;
			}
			case PROCESSCHAIN:{
				actionMethods = getProcessChainModel();
				break;
			}
			case SALARYDETAILS:{
				actionMethods = getSalaryDetailsComponent();
				break;
			}
			case ACCESSIBILITY:{
				actionMethods = getAccessibilityComponent();
				break;
			}
			case INBOX:{
				actionMethods = getInboxComponent();
				break;
			}
			case TIMESHEET:{
				actionMethods = getTimeSheetComponent();
				break;
			}
			case SKILLSET:{
				actionMethods = getSkillSetComponent();
				break;
			}
			case EMPLOYEMENTTYPE:{
				actionMethods = getEmploymentTypeComponent();
				break;
			}
			case ROLLON:{
				actionMethods = getRollOnOffComponent();
				break;
			}
			case CLIENT:{
				actionMethods = getClientRequestComponent();
				break;
			}
			case PROJECT:{
				actionMethods = getProjectRequestComponent();
				break;
			}
			case PODETAILS:{
				actionMethods = getPoDetails();
				break;
			}
			case REIMBURSEMENT:{
				actionMethods = getReimbursementComponent();
				break;
			}
			case COMMITMENT:{
				actionMethods = getCommitmentComponent();
				break;
			}
			case ISSUE:{
				actionMethods = getIssueTicketComponent();
				break;
			}
			case BANKDETAILS:{
				actionMethods = getBankDetailsComponent();
				break;
			}
			case PROJECTTASKS:{
				actionMethods = getProjTasksDetails();
				break;
			}
			case PAYSLIP:{
				actionMethods = getPayslipComponent();
				break;
			}
			case ITSUPPORT:{
				actionMethods = getITComponent();
				break;
			}
			case LOAN:{
				actionMethods = getLoanComponent();
				break;
			}
			case SODEXO:{
				actionMethods = getSodexoComponent();
				break;
			}
			case TRAVEL:{
				actionMethods = getTravelComponent();
				break;
			}
			case NEWS:{
				actionMethods = getNewsComponent();
				break;
			}
			case FESTIVALWISHS:{
				actionMethods = getFestivalWishesComponent();
				break;
			}
			case CONTACTTYPE:{
				actionMethods = getContactTypeComponent();
				break;
			}
			case APPRAISAL:{
				actionMethods = AppraisalModel.getInstance();
				break;
			}
			case REFERFRIEND:{
				actionMethods = new ReferFriendModel();
				break;
			}
			case RESUMEMANAGEMENT:{
				actionMethods = ResumeManagementModel.getInstance();
				break;
			}
			case EXITEMPLOYEE:{
				actionMethods = ExitModel.getInstance();
				break;
			}
			case INSURANCE:{
				actionMethods = new InsurancePolicyModel();
				break;
			}
			/*
			 * Component for process chain escalation
			 * 
			 */
			case PROCESSCHAINESCALATION:{
				actionMethods = new EscalationModel();
				break;
			}
			case FAQ:{
				actionMethods = FaqModel.getInstance();
				break;
			}
			case RECONCILIATION:{
				actionMethods = new ReconciliationModel();
				break;
			}
			case SALARYRECONCILIATION:{
			actionMethods = SalaryReconciliationModel.getInstance();
			
				break;
			}
			case INVOICERECONCILIATION:{
				actionMethods =new InvoiceReconciliationModel();
				break;
			}
			case BONUSRECONCILIATION:{
				actionMethods =new BonusReconciliationModel();
				break;
			}
			case RETENTIONBONUSRECONCILIATION:{
				actionMethods =new RetentionBonusReconciliationModel();
				break;
			}
			/*case NDAONLINEEXAM:{
				actionMethods = new NDAOnlineExamModel();
				break;
			}*/
			case FBP:{
				actionMethods = new FBPModel();
				break;
			}
			case TAXBENEFIT:{
				
				actionMethods = new TaxBenefitDeclarationModel();
				break;
			}
           case RESOURCEREQUIREMENT:{
				
				actionMethods = new ResourceReqModel();
				break;
			}
           case TIMESHEETRECONCILIATION:{
				
				actionMethods = new RmgTimeSheetModel();
				break;
			}
			default:
				actionMethods = getLoginComponent();
		}
		return actionMethods;
	}
	
	public String getOtherEmailId_Candidate() {
		return otherEmailId_Candidate;
	}

	public void setOtherEmailId_Candidate(String otherEmailId_Candidate) {
		this.otherEmailId_Candidate = otherEmailId_Candidate;
	}

	private ActionMethods getTravelComponent() {
		return new TravelModel();
	}

	/*private ActionMethods getModulesComponent() {
		return new ModuleModel();
	}*/
	protected ActionMethods getLevelComponent() {
		return new LevelsModel();
	}

	protected ActionMethods getDivisionComponent() {
		return new DivisionModel();
	}

	protected ActionMethods getCompanyComponent() {
		return new CompanyModel();
	}

	protected ActionMethods getUserComponent() {
		return new UserModel();
	}

	protected ActionMethods getLoginComponent() {
		return new LoginModel();
	}

	protected ActionMethods getModuleComponent() {
		return new ModuleModel();
	}

	protected ActionMethods getRolesComponent() {
		return new RoleModel();
	}

	protected ActionMethods getRegionComponent() {
		return new RegionModel();
	}

	protected ActionMethods getLeaveComponent() {
		return new LeaveModel();
	}

	protected ActionMethods getCalendarComponent() {
		return new CalendarModel();
	}

	protected ActionMethods getCandidateComponent() {
		return new CandidateModel();
	}

	protected ActionMethods getProfileInfoComponent() {
		return new ProfileInfoModel();
	}

	protected ActionMethods getEducationInfoComponent() {
		return new EducationInfoModel();
	}

	protected ActionMethods getPersonalInfoComponent() {
		return new PersonalInfoModel();
	}

	protected ActionMethods getExperienceInfoComponent() {
		return new ExperienceInfoModel();
	}

	protected ActionMethods getFinanceInfoComponent() {
		return new FinanceInfoModel();
	}

	protected ActionMethods getPassportInfoComponent() {
		return new PassportInfoModel();
	}

	protected ActionMethods getProcessChainModel() {
		return new ProcessModel();
	}

	protected ActionMethods getSalaryDetailsComponent() {
		return new SalaryDetailModel();
	}

	protected ActionMethods getAccessibilityComponent() {
		return new AccessibilityLevelModel();
	}

	protected ActionMethods getInboxComponent() {
		return new InboxModel();
	}

	protected ActionMethods getSkillSetComponent() {
		return new SkillSetModel();
	}

	protected ActionMethods getNomineeInfo() {
		return new NomineeModel();
	}

	protected ActionMethods getRollOnOffComponent() {
		return new RollOnModel();
	}

	protected ActionMethods getClientRequestComponent() {
		return new ClientModel();
	}

	protected ActionMethods getEmploymentTypeComponent() {
		return new EmploymentTypeModel();
	}

	protected ActionMethods getProjectRequestComponent() {
		return new ProjectModel();
	}

	protected ActionMethods getPoDetails() {
		return new PoDetailsModel();
	}

	protected ActionMethods getProjTasksDetails() {
		return new ProjectTasksModel();
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public ActionMethods getTimeSheetComponent() {
		return new TimeSheetModel();
	}

	private ActionMethods getPayslipComponent() {
		return new PayslipModel();
	}

	protected ActionMethods getReimbursementComponent() {
		return new ReimbursementModel();
	}

	protected ActionMethods getCommitmentComponent() {
		return new CommitmentModel();
	}

	protected ActionMethods getLoanComponent() {
		return new LoanModel();
	}

	protected ActionMethods getIssueTicketComponent() {
		return new IssueTicketModel();
	}

	private ActionMethods getSodexoComponent() {
		return new SodexoModel();
	}

	protected ActionMethods getBankDetailsComponent() {
		return new BankDetailsModel();
	}

	protected ActionMethods getITComponent() {
		return new ItModel();
	}

	protected ActionMethods getNewsComponent() {
		return new NewsModel();
	}

	protected ActionMethods getFestivalWishesComponent() {
		return new FestivalWishesModel();
	}

	protected ActionMethods getContactTypeComponent() {
		return new ContactTypeModel();
	}

	public Integer getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(Integer actionFlag) {
		this.actionFlag = actionFlag;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public String getMonth() {
		return month;
	}

	public String getToMonth() {
		return toMonth;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setSearchyear(String searchyear) {
		this.searchyear = searchyear;
	}

	public String getSearchyear() {
		return searchyear;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public boolean isAndroid() {
		if (this.device == null) return false;
		if (this.device.equalsIgnoreCase("android")) return true;
		return false;
	}

	public String getForwardName() {
		return forwardName;
	}

	public void setForwardName(String forwardName) {
		this.forwardName = forwardName;
	}
}