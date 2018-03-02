package com.dikshatech.common.utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.dikshatech.beans.Features;
import com.dikshatech.beans.ModuleBean;
import com.dikshatech.beans.Roles;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dto.EmpSerReqHistory;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.factory.EmpSerReqHistoryDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;

/**
 * @author gurunath.rokkam
 */
public class ModelUtiility {

	private static Logger			logger	= LoggerUtil.getLogger(ModelUtiility.class);
	private static ModelUtiility	mu		= null;

	public enum Department {
		PRODUCTS, HRD, FINANCE, OPERATIONS, BUSINESS, CONSULTING, SUPPORT, ITADMIN, ISSUE, FINANCEHEADLEVEL, TATLEADLEVEL, RMGMANAGERLEVEL, ADMIN;
	};

	private ModelUtiility() {}

	public static ModelUtiility getInstance() {
		if (mu == null) mu = new ModelUtiility();
		return mu;
	}

	public void deleteInboxEntries(int esrqmId) {
		try{
			JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=? AND RECEIVER_ID=ASSIGNED_TO", new Object[] { esrqmId });
		} catch (Exception e){
			logger.error("Error while deleting entries from Inbox", e);
		}
	}

	public void updateSiblings(Integer[] approverGroupIds, int esrqmId) {
		try{
			JDBCUtiility.getInstance().update("UPDATE EMP_SER_REQ_MAP SET SIBLINGS = ? WHERE ID=?", new Object[] { getCommaSeparetedValues(approverGroupIds), esrqmId });
		} catch (Exception e){
			logger.error("Error while updating siblings from EMP_SER_REQ_MAP.", e);
		}
	}

	public static String getCommaSeparetedValues(Object[] list) {
		try{
			if (list != null) return (Arrays.toString(list).replace("[", "").replace("]", ""));
		} catch (Exception e){
			logger.error("Error while getCommaSeparetedValues from " + Arrays.toString(list), e);
		}
		return "";
	}

	public static String getCommaSeparetedValues(Collection list) {
		try{
			if (list != null) return (list.toString().replace("[", "").replace("]", ""));
		} catch (Exception e){
			logger.error("Error while getCommaSeparetedValues from " + list, e);
		}
		return "";
	}

	public boolean isValidApprover(int esrqmId, int userId) {
		return isValidApprover(esrqmId, userId, null);
	}

	public boolean isValidApprover(int esrqmId, int userId, Connection conn) {
		try{
			EmpSerReqHistory esrHistory = EmpSerReqHistoryDaoFactory.create(conn).findWhereEsrMapIdEqualsMax(esrqmId);
			if (esrHistory == null){
				List<String> users = getSiblingUsersList(esrqmId);
				if (users.isEmpty() || users.contains(userId + "")) return true;
			} else return (esrHistory.getAssignedTo() == userId);
		} catch (Exception e){
			logger.error("Error while validateApprover ", e);
		}
		return false;
	}
	public boolean isAssignedApprover(int esrqmId, int userId) {
		return isAssignedApprover(esrqmId, userId, null);
	}

	public boolean isAssignedApprover(int esrqmId, int userId, Connection conn) {
		try{
			EmpSerReqHistory esrHistory = EmpSerReqHistoryDaoFactory.create(conn).findWhereEsrMapIdEqualsMax(esrqmId);
			if (esrHistory == null){
				List<String> users = getSiblingUsersList(esrqmId);
				if (users.isEmpty() || (users.contains(userId + "") && users.size() == 1)) return true;
			} else return (esrHistory.getAssignedTo() == userId);
		} catch (Exception e){
			logger.error("Error while validateApprover ", e);
		}
		return false;
	}

	public List<String> getSiblingUsersList(int esrId) {
		List<String> allsiblings = new ArrayList<String>();
		List<Object> users = JDBCUtiility.getInstance().getSingleColumn(" SELECT SIBLINGS FROM EMP_SER_REQ_MAP WHERE ID = " + esrId, null);
		if (users != null && users.size() > 0){
			String siblingUsers = (String) users.get(0);
			if (siblingUsers != null && siblingUsers.length() > 0){
				String[] sUsers = siblingUsers.split(",");
				for (String su : sUsers)
					try{
						su = su.replace(" ", "");
						if (Integer.parseInt(su) != 0) allsiblings.add(su);
					} catch (Exception e){
						logger.error("not valid user id : " + su, e);
					}
			}
		}
		return allsiblings;
	}

	public int getUnreadNotifications(int userId) {
		return (int) JDBCUtiility.getInstance().getRowCount("FROM INBOX WHERE RECEIVER_ID = ? AND IS_READ = 0 AND IS_DELETED = 0 AND (RECEIVER_ID != ASSIGNED_TO OR (CATEGORY ='CANDIDATE' AND STATUS!='" + Status.PENDINGAPPROVAL + "'))", new Object[] { userId });
	}

	public int getUnreadDocked(int userId) {
		return (int) JDBCUtiility.getInstance().getRowCount("FROM INBOX WHERE RECEIVER_ID = ? AND RECEIVER_ID = ASSIGNED_TO AND IS_DELETED = 0 AND (CATEGORY !='CANDIDATE' OR STATUS ='" + Status.PENDINGAPPROVAL + "')", new Object[] { userId });
	}

	public boolean isPayslipsUploaded() {
		return JDBCUtiility.getInstance().getRowCount("FROM PAYSLIP WHERE YEAR_=YEAR(NOW()) AND MONTH_=MONTH(NOW())-1", null) > 0;
	}

	public int[] getFinanceUserIds(int userId) {
		return getDepartmentUserIds(userId, Department.FINANCE);
	}

	public int[] getDepartmentUserIds(int userId, Department dept) {
		try{
			Regions rgnDto = RegionsDaoFactory.create().findByDynamicSelect("SELECT * FROM REGIONS R JOIN DIVISON D ON R.ID=D.REGION_ID JOIN LEVELS L ON L.DIVISION_ID=D.ID JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { userId })[0];
			return getDepartmentUserIds(getdeptId(dept, new Notification(rgnDto.getRefAbbreviation())));
		} catch (Exception e){
			e.printStackTrace();
		}
		return new int[] {};
	}

	public int[] getDepartmentUserIds(String abbrevition, Department dept) {
		try{
			return getDepartmentUserIds(getdeptId(dept, new Notification(abbrevition)));
		} catch (Exception e){
			e.printStackTrace();
		}
		return new int[] {};
	}

	public int[] getDepartmentUserIds(int notificationId) {
		int[] users = new int[] {};
		try{
			List<Object> ids = JDBCUtiility.getInstance().getSingleColumn("SELECT U.ID FROM USERS U JOIN LEVELS L ON L.ID=U.LEVEL_ID WHERE DIVISION_ID=? AND STATUS NOT IN (1, 2, 3)", new Object[] { notificationId });
			users = new int[ids.size()];
			int i = 0;
			for (Object id : ids)
				users[i++] = ((Integer) id).intValue();
		} catch (Exception e){
			e.printStackTrace();
		}
		return users;
	}

	public int getdeptId(Department dept, Notification notification) {
		switch (dept) {
			case FINANCE:
				return notification.financeId;
			case ITADMIN:
				return notification.itAdminId;
			case OPERATIONS:
				return notification.operationsId;
			case RMGMANAGERLEVEL:
				return notification.rmgManagerLevelId;
			case ADMIN:
				return notification.admin;
			case HRD:
				return notification.hrdId;
				default:
				return 0;
		}
	}

	public EmpSerReqMapPk createEmpSerReq(int userId, String reqAbbrevation, int req_type_id) throws Exception {
		EmpSerReqMap empReqMapDto = new EmpSerReqMap();
		Regions reg_id = RegionsDaoFactory.create().findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { userId })[0];
		EmpSerReqMapDao emp_Ser_Req_Map_Dao = EmpSerReqMapDaoFactory.create();
		int unQ = 1;
		EmpSerReqMap empMap[] = emp_Ser_Req_Map_Dao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=? AND REQ_ID LIKE '%-" + reqAbbrevation + "-%')", new Object[] { req_type_id });
		if (empMap.length > 0){
			String s = empMap[0].getReqId().split("-")[2];
			unQ = Integer.parseInt(s) + 1;
		}
		empReqMapDto = new EmpSerReqMap();
		empReqMapDto.setSubDate(new Date());
		empReqMapDto.setReqId(reg_id.getRefAbbreviation() + "-" + reqAbbrevation + "-" + unQ);
		empReqMapDto.setReqTypeId(req_type_id);
		empReqMapDto.setRegionId(reg_id.getId());
		empReqMapDto.setRequestorId(userId);
		return emp_Ser_Req_Map_Dao.insert(empReqMapDto);
	}

	public static boolean hasModulePermission(Login login, int moduleId) {
		try{
			UserLogin ul = login.getUserLogin();
			Set<Roles> roles = ul.getRoles();
			for (Roles rl : roles){
				Set<ModuleBean> modules = rl.getModules();
				for (ModuleBean module : modules){
					Set<Features> features = module.getFeatures();
					for (Features feature : features){
						if (feature.getFeatureId() == moduleId) return true;
					}
				}
			}
		} catch (Exception e){}
		return false;
	}

	public Features getModulePermission(Login login, int moduleId) {
		try{
			UserLogin ul = login.getUserLogin();
			Set<Roles> roles = ul.getRoles();
			for (Roles rl : roles){
				Set<ModuleBean> modules = rl.getModules();
				for (ModuleBean module : modules){
					Set<Features> features = module.getFeatures();
					for (Features feature : features){
						if (feature.getFeatureId() == moduleId) return feature;
					}
				}
			}
		} catch (Exception e){}
		return new Features();
	}

	/**
	 * @author gurunath.rokkam
	 * @param loginSess
	 * @return List<Integer>
	 */
	public List getRMGManagerUserIds(Login loginSess) {
		int regionId = 1;// default india
		try{
			regionId = loginSess.getUserLogin().getRegionId();
		} catch (Exception e){}
		return getRMGManagerUserIds(regionId);
	}

	public List getRMGManagerUserIds(UsersPk userPk) {
		int regionId = 1;// default india
		try{
			Regions rgnDto = RegionsDaoFactory.create().findByDynamicSelect("SELECT * FROM REGIONS R JOIN DIVISON D ON R.ID=D.REGION_ID JOIN LEVELS L ON L.DIVISION_ID=D.ID JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { userPk.getId() })[0];
			regionId = rgnDto.getId();
		} catch (Exception e){}
		return getRMGManagerUserIds(regionId);
	}

	/**
	 * @author gurunath.rokkam
	 * @param loginSess
	 * @return List<Integer>
	 */
	public List getRMGManagerUserIds(int regionId) {
		try{
			Regions region = RegionsDaoFactory.create().findByPrimaryKey(regionId);
			Notification notification = new Notification(region.getRefAbbreviation());
			List<Object> userslist = JDBCUtiility.getInstance().getSingleColumn("SELECT ID FROM USERS WHERE PROFILE_ID IN( SELECT ID FROM PROFILE_INFO WHERE LEVEL_ID IN ( " + notification.rmgManagerLevelId + " )) AND STATUS NOT IN(1,2,3)", null);
			return userslist;
		} catch (Exception e){
			logger.error("unable to fetch rmg manager ids", e);
		}
		return new ArrayList();
	}

	public String getEmployeeName(int userId) {
		return getEmployeeName(userId, null);
	}

	public String getEmployeeName(int userId, Connection conn) {
		String userName = "";
		try{
			List<Object> userNameList = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { userId }, conn);
			userName = (String) userNameList.get(0);
		} catch (Exception e){}
		return userName == null ? "" : userName;
	}

	public int getEmployeeId(int userId) {
		return getEmployeeId(userId, null);
	}

	public int getEmployeeId(int userId, Connection conn) {
		int empId = 0;
		try{
			List<Object> userNameList = JDBCUtiility.getInstance().getSingleColumn("SELECT EMP_ID FROM USERS WHERE ID=?", new Object[] { userId }, conn);
			empId = ((Integer) userNameList.get(0)).intValue();
		} catch (Exception e){}
		return empId;
	}

	public <T> List<T> toList(T[] array) {
		List<T> list = new ArrayList<T>();
		if (array != null) for (T a : array){
			list.add(a);
		}
		return list;
	}
}
