package com.dikshatech.common.utils;

import org.apache.log4j.Logger;

public class EnumUtil {

	protected static final Logger	logger	= Logger.getLogger(EnumUtil.class);

	public enum Permission {
		S, V, D, E, ME, EN, DS, GO, RS, AP, RJ, NS, CN, RN, SU, DN, UP, EM, AS, HO;
	};

	public enum PermissionType {
		SAVE, VIEW, DELETE, EDIT, MARKEMPLOYEE, ENABLE, DISABLE, GENERATEOFFER, RESENDOFFER, APPROVE, REJECT, REMOVE, MODIFY, ONHOLD, NOSHOW, CANCELLED, CANCEL, ROLLON, SUBMIT, DOWNLOAD, UPLOAD, EMAIL, ASSIGN, HOLD, UNKNOWN;

		public static PermissionType getValue(String value) {
			try{
				return valueOf(value);
			} catch (Exception e){
				return UNKNOWN;
			}
		};
	}

	public static String checkPermissions(String varStr) {
		String tempPermission = "";
		for (String s1 : varStr.split("\\-")){
			switch (Permission.valueOf(s1)) {
				case S:{
					tempPermission += PermissionType.SAVE + ";";
					break;
				}
				case V:{
					tempPermission += PermissionType.VIEW + ";";
					break;
				}
				case D:{
					tempPermission += PermissionType.REMOVE + ";";
					break;
				}
				case E:{
					tempPermission += PermissionType.MODIFY + ";";
					break;
				}
				case ME:{
					tempPermission += PermissionType.MARKEMPLOYEE + ";";
					break;
				}
				case AP:{
					tempPermission += PermissionType.APPROVE + ";";
					break;
				}
				case RJ:{
					tempPermission += PermissionType.REJECT + ";";
					break;
				}
				case RS:{
					tempPermission += PermissionType.RESENDOFFER + ";";
					break;
				}
				case EN:{
					tempPermission += PermissionType.ENABLE + ";";
					break;
				}
				case DS:{
					tempPermission += PermissionType.DISABLE + ";";
					break;
				}
				case GO:{
					tempPermission += PermissionType.GENERATEOFFER + ";";
					break;
				}
				case NS:{
					tempPermission += PermissionType.NOSHOW + ";";
					break;
				}
				case CN:{
					tempPermission += PermissionType.CANCEL + ";";
					break;
				}
				case RN:{
					tempPermission += PermissionType.ROLLON + ";";
					break;
				}
				case SU:{
					tempPermission += PermissionType.SUBMIT + ";";
					break;
				}
				case DN:{
					tempPermission += PermissionType.DOWNLOAD + ";";
					break;
				}
				case UP:{
					tempPermission += PermissionType.UPLOAD + ";";
					break;
				}
				case EM:{
					tempPermission += PermissionType.EMAIL + ";";
					break;
				}
				case AS:{
					tempPermission += PermissionType.ASSIGN + ";";
					break;
				}
				case HO:{
					tempPermission += PermissionType.HOLD + ";";
					break;
				}
				default:
					logger.error("No Match Found");
			}//End of switch
		}
		return tempPermission;
	}
}