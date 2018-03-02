package com.dikshatech.common.utils;

import java.util.Date;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.models.UserModel;

public class UserMarkAsSeperated {

	private static Logger	logger	= Logger.getLogger(UserMarkAsSeperated.class);

	public UserMarkAsSeperated() {}

	public boolean markAsSeperated() {
		boolean flag = true;
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		short seperated = 2;
		try{
			ProfileInfo profile[] = profileInfoDao.findWhereDateOfSeperationEquals(new Date());
			for (ProfileInfo singlepProfile : profile){
				UsersPk userPk = new UsersPk();
				Users user[] = usersDao.findWhereProfileIdEquals(singlepProfile.getId());
				if (user[0].getStatus() != 2){
					user[0].setStatus(seperated);
					user[0].setDateOfSeperation(singlepProfile.getDateOfSeperation());
					userPk.setId(user[0].getId());
					usersDao.update(userPk, user[0]);
					logger.info("User name and Id marked as Seperated" + singlepProfile.getFirstName() + "" + singlepProfile.getId());
					UserModel usermodel = new UserModel();
					usermodel.sendMailNotifications(user[0], null);
				}
			}
		} catch (Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
}
