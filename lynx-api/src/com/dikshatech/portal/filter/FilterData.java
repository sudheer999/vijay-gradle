package com.dikshatech.portal.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.dikshatech.beans.CandidateCommonBean;
import com.dikshatech.beans.ProfileBean;
import com.dikshatech.beans.ProfileListBean;
import com.dikshatech.portal.actions.ActionComponents;
import com.dikshatech.portal.dao.AccessibilityTopLevelDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dto.AccessibilityTopLevel;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.factory.AccessibilityTopLevelDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;

public class FilterData
{

	public Object[] filter(Object[] records, String cType, Login login)
	{
		Object[] filterData = null;

		ActionComponents actionComponent = ActionComponents.getValue(cType);
		switch (actionComponent)
		{
		case CANDIDATE:
		{
			List<CandidateCommonBean> candidates = filterCandidate(records, login);
			filterData = candidates.toArray();
			break;
		}
		case USER:
			List<ProfileBean> users = filterUsers(records, login);
			filterData = users.toArray();
			break;
		case PROFILEINFO:
			List<ProfileListBean> user = filterProfiles(records, login);
			filterData = user.toArray();
			break;
		default:
		}
		return filterData;
	}

	private List<ProfileBean> filterUsers(Object[] records, Login login)
	{
		List<ProfileBean> users = new ArrayList<ProfileBean>();
		try
		{
			/**
			 * 17 is the featureId of employee
			 */
			AccessibilityTopLevel[] aTopLevels = getAccessLevel(login, 17);
			Set<Integer> levelIds = getLevels(aTopLevels);

			for (Object record : records)
			{
				ProfileBean cBean = (ProfileBean) record;
				if (levelIds.contains(cBean.getLevelid()))
				{
					users.add(cBean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return users;
	}
	private List<ProfileListBean> filterProfiles(Object[] records, Login login)
	{
		List<ProfileListBean> users = new ArrayList<ProfileListBean>();
		try
		{
			/**
			 * 17 is the featureId of employee
			 */
			AccessibilityTopLevel[] aTopLevels = getAccessLevel(login, 17);
			Set<Integer> levelIds = getLevels(aTopLevels);

			for (Object record : records)
			{
				ProfileListBean cBean = (ProfileListBean) record;
				if (levelIds.contains(cBean.getLevelid()))
				{
					users.add(cBean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return users;
	}

	/**
	 * This method returns accessibilityLevels based on the login for the given moduleId.
	 * @param login
	 * @param moduleId
	 * @return AccessibilityTopLevel[]
	 * @throws Exception
	 */
	private AccessibilityTopLevel[] getAccessLevel(Login login, int moduleId) throws Exception
	{
		ModulePermissionDao mPermissionDao = ModulePermissionDaoFactory.create();
		UserRolesDao uRolesDao = UserRolesDaoFactory.create();
		AccessibilityTopLevelDao aTopLevelDao = AccessibilityTopLevelDaoFactory.create();

		UserRoles uRoles = uRolesDao.findWhereUserIdEquals(login.getUserId());
		int roleId=0;
		if(login.getUserId()==27){
			roleId=20;
		}else roleId=uRoles.getRoleId();

		ModulePermission mPermission = mPermissionDao.findByRoleAndModule(roleId, moduleId);

		return aTopLevelDao.findWhereAccessibilityIdEquals(mPermission.getAccessibilityId());

	}

	/**
	 * This method returns the set of levels that can be accessed for the given AccessibilityTopLevel[]
	 * @param aTopLevel
	 * @return
	 */
	private Set<Integer> getLevels(AccessibilityTopLevel[] aTopLevel)
	{
		LevelsDao lDao = LevelsDaoFactory.create();

		Set<Integer> levelIds = new HashSet<Integer>();

		for (AccessibilityTopLevel aLevel : aTopLevel)
		{

			try
			{
				if (aLevel.getDivisionId() != null && !aLevel.getDivisionId().equals("0"))
				{
					Set<Levels> levelSet = new HashSet<Levels>();
					for(String divId :  aLevel.getDivisionId().split(","))
					{
						Levels[] levels = lDao.findWhereDivisionIdEquals(Integer.parseInt(divId));
						for(Levels lev : levels)
						{
							levelSet.add(lev);
						}	
					}	
					
					for (Levels levels2 : levelSet)
					{
						levelIds.add(levels2.getId());
					}
				}
				else if (aLevel.getDepartmentId() != null && !aLevel.getDepartmentId().equals("0"))
				{
					Object[] sqlParams =
					{ new Integer(aLevel.getDepartmentId()), new Integer(aLevel.getDepartmentId()) };
					Levels[] levels = lDao.findByDynamicWhere("DIVISION_ID IN (SELECT ID FROM DIVISON WHERE PARENT_ID = ?) OR DIVISION_ID = ?",
							sqlParams);
					for (Levels levels2 : levels)
					{
						levelIds.add(levels2.getId());
					}
				}
				else if (aLevel.getRegionId() != null && !aLevel.getRegionId().equals("0"))
				{
					Object[] sqlParams =
					{ new Integer(aLevel.getRegionId()) };
					Levels[] levels = lDao.findByDynamicWhere("DIVISION_ID IN (SELECT ID FROM DIVISON WHERE REGION_ID = ?)", sqlParams);
					for (Levels levels2 : levels)
					{
						levelIds.add(levels2.getId());
					}
				}
				else if (aLevel.getCompanyId() != null && !aLevel.getCompanyId().equals("0"))
				{
					Object[] sqlParams =
					{ new Integer(aLevel.getCompanyId()) };
					Levels[] levels = lDao.findByDynamicWhere(
							"DIVISION_ID IN (SELECT ID FROM DIVISON WHERE REGION_ID IN (SELECT ID FROM REGIONS WHERE COMPANY_ID = ?))",
							sqlParams);
					for (Levels levels2 : levels)
					{
						levelIds.add(levels2.getId());
					}
				}
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
			catch (LevelsDaoException e)
			{
				e.printStackTrace();
			}
		}

		return levelIds;
	}

	/**
	 * This method filters the candidate records based on the accessibility level for the logged in user.
	 * @param records
	 * @param login
	 * @return
	 */
	private List<CandidateCommonBean> filterCandidate(Object[] records, Login login)
	{
		List<CandidateCommonBean> candidates = new ArrayList<CandidateCommonBean>();
		try
		{
			/**
			 * 15 is the featureId of candidate
			 */
			AccessibilityTopLevel[] aTopLevels = getAccessLevel(login, 15);
			Set<Integer> levelIds = getLevels(aTopLevels);

			for (Object record : records)
			{
				CandidateCommonBean cBean = (CandidateCommonBean) record;
				if (levelIds.contains(cBean.getLevelId()))
				{
					candidates.add(cBean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return candidates;
	}

}
