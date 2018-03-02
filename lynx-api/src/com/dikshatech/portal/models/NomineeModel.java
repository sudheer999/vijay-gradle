package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.NomineeInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.NomineeInfo;
import com.dikshatech.portal.dto.NomineeInfoPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.NomineeInfoDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.NomineeInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.PortalForm;

public class NomineeModel extends ActionMethods {

	NomineeInfoDao			nomineeInfoDao	= NomineeInfoDaoFactory.create();
	private static Logger	logger			= LoggerUtil.getLogger(CandidateModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		NomineeInfo nomineeInfo = (NomineeInfo) form;
		NomineeInfoPk nomineeInfoPk = new NomineeInfoPk();
		Candidate candidate = new Candidate();
		CandidateDao c = CandidateDaoFactory.create();
		try{
			if (nomineeInfo.getCandidateId() > 0){
				candidate = c.findByPrimaryKey(nomineeInfo.getCandidateId());
				nomineeInfoPk.setId(candidate.getNomineeId());
			} else if (nomineeInfo.getUserId() > 0){
				Users user = new Users();
				UsersDao usersDao = UsersDaoFactory.create();
				user = usersDao.findByPrimaryKey(nomineeInfo.getUserId());
				nomineeInfoPk.setId(user.getNomineeId());
			}
			nomineeInfo = nomineeInfoDao.findByPrimaryKey(nomineeInfoPk.getId());
			if (nomineeInfo != null && nomineeInfo.getDepName() != null && !(nomineeInfo.getDepName().equalsIgnoreCase("{~=~}"))){
				nomineeInfo.setDepName(nomineeInfo.getDepName().substring(1, nomineeInfo.getDepName().length() - 1));
				String dependents_array[] = nomineeInfo.getDepName().split(",");
				NomineeInfo nomineeArr[] = new NomineeInfo[dependents_array.length];
				int count = 0;
				for (String single : dependents_array){
					try{
						NomineeInfo nominee = new NomineeInfo();
						nominee.setDepName(single.split("~=~")[0]);
						nominee.setRelationship(single.split("~=~")[1]);
						nomineeArr[count] = nominee;
						count++;
					} catch (Exception e){}
				}
				nomineeInfo.setNomineeArray(nomineeArr);
			}
		} catch (Exception ne){
			logger.info("Failed to update Nominee  Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			ne.printStackTrace();
		}
		request.setAttribute("actionForm", nomineeInfo);
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		NomineeInfo nomineeInfo = (NomineeInfo) form;
		nomineeInfo.setId(0);
		CandidateModel candidateModel = new CandidateModel();
		Candidate candidate = new Candidate();
		CandidateDao c = CandidateDaoFactory.create();
		try{
			String dependents_array[] = nomineeInfo.getDepNameArray();
			String depNames = Arrays.toString(dependents_array);
			nomineeInfo.setDepName(depNames);
			nomineeInfo.setModifiedBy(login.getUserId());
			NomineeInfoPk nomineeInfoPk = nomineeInfoDao.insert(nomineeInfo);
			/**
			 * Insert in candidate table the relevant id
			 */
			if (nomineeInfo.getCandidateId() > 0){
				candidate = c.findByPrimaryKey(nomineeInfo.getCandidateId());
				candidate.setNomineeId(nomineeInfoPk.getId());
				candidate.setuType("UPDATECANDIDATE");
				candidateModel.update(candidate, request);
			}
			/**
			 * add in User Table
			 */
			if (nomineeInfo.getUserId() != null && nomineeInfo.getUserId() > 0){
				UsersPk usersPk = new UsersPk();
				UsersDao usersDao = UsersDaoFactory.create();
				Users users = usersDao.findByPrimaryKey(nomineeInfo.getUserId());
				users.setNomineeId(nomineeInfoPk.getId());
				usersPk.setId(users.getId());
				usersDao.update(usersPk, users);
			}
		} catch (Exception ne){
			logger.info("Failed to save Nominee Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			ne.printStackTrace();
		}
		return result;
	}

	/**
	 * @author supriya.bhike Update's nominee Info
	 * @param PortalForm
	 *            form,HttpServletRequest
	 */
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		NomineeInfo nomineeInfo = (NomineeInfo) form;
		NomineeInfoPk nomineeInfoPk = new NomineeInfoPk();
		String dependents_array[] = nomineeInfo.getDepNameArray();
		String depNames = Arrays.toString(dependents_array);
		nomineeInfo.setDepName(depNames);
		nomineeInfo.setModifiedBy(login.getUserId());
		nomineeInfoPk.setId(nomineeInfo.getId());
		try{
			nomineeInfoDao.update(nomineeInfoPk, nomineeInfo);
		} catch (NomineeInfoDaoException ne){
			logger.info("Failed to update Nominee  Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			ne.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
