package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.CommitmentBean;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CommitmentsDao;
import com.dikshatech.portal.dto.Commitments;
import com.dikshatech.portal.dto.CommitmentsPk;
import com.dikshatech.portal.factory.CommitmentsDaoFactory;
import com.dikshatech.portal.forms.CandidateSaveForm;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class CommitmentModel extends ActionMethods
{

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Commitments commitmentform = (Commitments) form;
		CommitmentsDao commitmentsDao = CommitmentsDaoFactory.create();
		try
		{
			commitmentsDao.delete(new CommitmentsPk(commitmentform.getId()));

		} catch (Exception e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Commitments commitmentform = (Commitments) form;
		CommitmentsDao commitmentsDao = CommitmentsDaoFactory.create();
		try
		{
			DropDown dropDown = new DropDown();
			if (commitmentform.getUserIdEmp() > 0)
			{
				Commitments[ ] commitments = commitmentsDao.findWhereUserIdEmpEquals(commitmentform.getUserIdEmp());
				com.dikshatech.beans.CommitmentBean[] bean = new com.dikshatech.beans.CommitmentBean[commitments.length];
				int i = 0;
				for (Commitments c : commitments)
				{
					bean[i] = DtoToBeanConverter.DtoToBeanConverter(c);
					i++;
				}
				dropDown.setDropDown(bean);
			}
			/*
			 * else if (commitmentform.getCandidateId() > 0)
			 * {
			 * Commitments[ ] commitments =
			 * commitmentsDao.findWhereUserIdEmpEquals(commitmentform.getUserIdEmp());
			 * com.dikshatech.beans.CommitmentBean[] bean = new
			 * com.dikshatech.beans.CommitmentBean[commitments.length];
			 * int i = 0;
			 * for (Commitments c : commitments)
			 * {
			 * bean[i] = DtoToBeanConverter.DtoToBeanConverter(c);
			 * i++;
			 * }
			 * dropDown.setDropDown(bean);
			 * }
			 */
			request.setAttribute("actionForm", dropDown);
		} catch (Exception e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoreceive"));
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Commitments commitmentform = (Commitments) form;
		CommitmentsDao commitmentsDao = CommitmentsDaoFactory.create();
		String comments[] = null;
		try
		{
			// format of the string "comment~=~date"
			if (commitmentform.getComment() != null && commitmentform.getComment().length > 0)
			{
				for (String s : commitmentform.getComment())
				{
					Commitments commitments = new Commitments();
					comments = s.split("~=~");
					commitments.setComments(comments[0]);
					if (comments.length > 1)
						commitments.setDatePicker(PortalUtility.fromStringToDate(comments[1]));
					commitments.setCandidateId(commitmentform.getCandidateId());
					commitments.setUserIdEmp(commitmentform.getUserIdEmp());
					commitmentsDao.insert(commitments);
				}

			}

		} catch (Exception e)
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}

		return result;
	}

	public CommitmentBean[ ] update(CandidateSaveForm candidatesaveform, HttpServletRequest request)
	{
		CommitmentsDao commitmentsDao = CommitmentsDaoFactory.create();
		CommitmentsPk cpk = new CommitmentsPk();
		String comments[] = null;
		int p = 0;
		int id = 0;
		// format of the string "comment~=~date"
		try
		{
			if (candidatesaveform.getCommitments() != null && candidatesaveform.getCommitments().length > 0)
			{
				CommitmentBean[ ] c = new CommitmentBean[candidatesaveform.getCommitments().length];
				ArrayList< Integer > list = new ArrayList< Integer >();
				for (String s : candidatesaveform.getCommitments())
				{
					Commitments commitments = new Commitments();
					if (s.split(":").length > 1)
					{
						cpk.setId(Integer.parseInt(s.split(":")[0]));
						comments = s.split(":")[1].split("~=~");
						if (comments.length > 1){
							commitments.setComments(comments[0]);
							commitments.setDatePicker(PortalUtility.StringToDate(comments[1]));
						    commitments.setCandidateId(candidatesaveform.getId());
						    commitments.setUserIdEmp(0);
						    commitments.setId(cpk.getId());
						    commitmentsDao.update(cpk, commitments);
						}

					}
					else
					{
						comments = s.split("~=~");
						if (comments.length > 0)
						{
							commitments.setComments(comments[0]);
							if (comments.length > 1)
								commitments.setDatePicker(PortalUtility.StringToDate(comments[1]));
							commitments.setCandidateId(candidatesaveform.getId());
							commitments.setUserIdEmp(0);
							cpk = commitmentsDao.insert(commitments);
						}
					}
					list.add(cpk.getId());
				}
				Commitments commit[] = commitmentsDao.findWhereCandidateIdEquals(candidatesaveform.getId());
				for (Commitments com : commit)
				{
					if (list.contains(com.getId()))
					{
						c[p] = DtoToBeanConverter.DtoToBeanConverter(com);
						p++;
					}
					else
					{
						commitmentsDao.delete(new CommitmentsPk(com.getId()));
					}

				}
				return c;
			}
			else
			{
				commitmentsDao.dynamicDelete(candidatesaveform.getId());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
