package com.dikshatech.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Escalation;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;

/**
 * Parser Utility for the escalation module
 * EscalatinoParser will accept
 * approvalChainEsc=
 * RM:{due:2,esc:{true|102,false|RM2}};
 * 161:{due:2,esc:{true|120}};
 * and return List<Escalation>
 * 
 * @author praneeth.r
 */
public class EscalationParser {

	Logger	logger	= LoggerUtil.getLogger(EscalationParser.class);

	/**
	 * This method Parse string to Escalation objects
	 * 
	 * @param approvalEsc
	 * @return List<Escalation>
	 */
	public List<Escalation> getEscalations(String pcId, String escalationtype, String approvalEsc) {
		// RM:{due:2,esc:{true|102,false|RM}}; 161:{due:2,esc:{true|120}};
		List<Escalation> escalations = new ArrayList<Escalation>();
		Matcher matcher = null;
		Pattern pat = null;
		if (approvalEsc != null && approvalEsc.length() > 0){
			String[] tokens = approvalEsc.split(";");
			for (int i = 0; i < tokens.length; i++){
				String str = tokens[i];
				Escalation entry = new Escalation();
				if (escalationtype.equalsIgnoreCase("APPROVAL")){
					entry.setEscalType("APPROVAL");
					entry.setPcId(Integer.valueOf(pcId));
					String userOrLevelId = str.substring(0, tokens[i].indexOf(":"));
					if (userOrLevelId.equalsIgnoreCase("HRSPOC") || userOrLevelId.equalsIgnoreCase("RM")) entry.setUsersId(userOrLevelId);
					else entry.setLevelId(Integer.parseInt(userOrLevelId));
					if (str != null && str.length() > 0 && str.substring(str.indexOf(":") + 1, str.length()).length() > 0){
						entry.setDueDays(Integer.valueOf(str.substring(str.indexOf("due:") + 4, str.indexOf(","))));
						pat = Pattern.compile("(.*?)}");
						matcher = pat.matcher(str.substring(str.indexOf("esc:{") + 5));
						if (matcher.find()){
							entry.setEscalTo(matcher.group(1));
						}
					}
				} else if (escalationtype.equalsIgnoreCase("HANDLER")){
					entry.setEscalType("HANDLER");
					entry.setPcId(Integer.valueOf(pcId));
					String userOrLevelId = str.substring(0, tokens[i].indexOf(":"));
					if (userOrLevelId.equalsIgnoreCase("HRSPOC") || userOrLevelId.equalsIgnoreCase("RM")) entry.setUsersId(userOrLevelId);
					else entry.setLevelId(Integer.parseInt(userOrLevelId));
					try{
						entry.setDueDays(Integer.valueOf(str.substring(str.indexOf("due:") + 4, str.indexOf(","))));
						pat = Pattern.compile("(.*?)}");
						matcher = pat.matcher(str.substring(str.indexOf("esc:{") + 5));
					} catch (Exception e){
						logger.debug("The Users or levels were sent with no proper Parameters for saving for the PC ID " + pcId);
					}
					if (matcher != null && matcher.find()){
						entry.setEscalTo(matcher.group(1));
					}
				}
				escalations.add(entry);
			}
		} else{
			logger.info("The Approval Or Handler chain is passed with null while saving for the ESCALATION For the Process Chain ID " + pcId);
		}
		return (escalations);
	}

	/**
	 * This method Parse string to EscPermissionBean objects
	 * 
	 * @param String
	 *            like true|102,false|RM2
	 * @return List<EscPermissionBean>
	 * @throws
	 * @throws NumberFormatException
	 */
	public List<EscPermissionBean> getEscalationPermissionsForUsers(String escString) throws NumberFormatException {
		List<EscPermissionBean> permissionsList = new ArrayList<EscPermissionBean>();
		if (escString != null && escString.trim().length() > 0){
			String[] alluserpermissions = escString.split(",");
			EscPermissionBean escPerm;
			UsersDao userProvider = UsersDaoFactory.create();
			for (int i = 0; i < alluserpermissions.length; i++){
				String tempstring = alluserpermissions[i];
				String[] strarr = tempstring.split("\\|");
				escPerm = new EscPermissionBean();
				escPerm.setPermission(Boolean.valueOf(strarr[0]));
				escPerm.setUserId(strarr[1]);
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				ProfileInfo profile = null;
				Users user = null;
				// Code added to return empId from USERS table
				try{
					if (strarr[1].equalsIgnoreCase("RM")){
						escPerm.setName("RM");
					} else if (strarr[1].equalsIgnoreCase("HRSPOC")){
						escPerm.setName("HRSPOC");
					} else{
						user = userProvider.findByPrimaryKey(Integer.parseInt(strarr[1]));
						String empId = Integer.toString(user.getEmpId());
						escPerm.setEmpId(empId);
						// Login login = null;
						if (strarr[1] != null && !strarr[1].equalsIgnoreCase("RM") && !strarr[1].equalsIgnoreCase("HRSPOC")){
							try{
								profile = profileInfoDao.findByPrimaryKey(user.getProfileId());
								String fullname = profile.getFirstName() + " " + profile.getLastName();
								escPerm.setName(fullname);
							} catch (NumberFormatException e){
								e.printStackTrace();
							} catch (ProfileInfoDaoException e){
								e.printStackTrace();
							}
						} else{
							logger.debug("Getting Escalation user name. Unable to get for USERID: " + strarr[1]);
						}
					}
					if (!escPerm.getuserId().equalsIgnoreCase("RM") && !escPerm.getuserId().equalsIgnoreCase("HRSPOC")){
						escPerm.setLevelId(Integer.toString(profile.getLevelId()));
					}
					permissionsList.add(escPerm);
				} catch (UsersDaoException e1){
					logger.error("Unable to get employee id from USERS for ID: " + strarr[1] + e1.getMessage());
				}
			}
		} else{
			logger.debug("Escalation permission bean list is empty, as ESCAL_TO string is empty.");
		}
		return permissionsList;
	}
}