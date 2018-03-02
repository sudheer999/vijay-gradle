package com.dikshatech.portal.models;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.dikshatech.beans.ProcessChainBean;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.ModulesDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProcessChainPk;
import com.dikshatech.portal.exceptions.ModulesDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.ModulesDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class ProcessModel extends ActionMethods
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
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();

		ProcessChain processChainDto = (ProcessChain) form;

		try
		{
			StringBuffer holdNotDeletedIds = new StringBuffer();

			for (Integer id : processChainDto.getpChainIds())
			{

				ModulePermission[] modulePermission = modulePermissionDao.findByDynamicSelect(
						"SELECT * FROM MODULE_PERMISSION  WHERE PROC_CHAIN_ID =?  ", new Object[]
						{ id.intValue() });

				if (modulePermission.length == 0)
				{
					processChainDao.delete(new ProcessChainPk(id.intValue()));
				}
				else
				{

					holdNotDeletedIds.append(id.intValue() + ",");
				}

			}

			if (holdNotDeletedIds.length() > 0)
			{

				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,
						new ActionMessage("processchain.delete.error", holdNotDeletedIds.toString()));
				result.setForwardName("success");

			}

		}
		catch (ProcessChainDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ModulesDao modulesDao = ModulesDaoFactory.create();
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
		{

		case RECEIVEPROCESSCHAIN:
		{
			ProcessChain processChainDto = (ProcessChain) form;
			try
			{
				processChainDto = processChainDao.findByPrimaryKey(processChainDto.getId());
				Modules moduleChild = modulesDao.findByPrimaryKey(processChainDto.getFeatureId());
				processChainDto.setModuleId(moduleChild.getParentId());
				ProcessChainBean[] ProcChainBeanArr = new ProcessChainBean[3];
				ProcessChainBean pcBeanApproval = null;
				ProcessChainBean pcBeanNotification = null;
				ProcessChainBean pBeanHandlerBean = null;

				if (processChainDto.getApprovalChain() != null && !(processChainDto.getApprovalChain().equals("")))
				{
					pcBeanApproval = DtoToBeanConverter.DtoToBeanConverterToParseApprovalChainForReceive(processChainDto.getApprovalChain(),
							"APPROVAL", processChainDto.getId());
					pcBeanApproval.setProcessChainName("APPROVALCHAIN");
					ProcChainBeanArr[0] = pcBeanApproval;
				}
				if (processChainDto.getNotification() != null && !(processChainDto.getNotification().equals("")))
				{
					pcBeanNotification = DtoToBeanConverter.DtoToBeanConverterToParseApprovalChainForReceive(processChainDto.getNotification(),
							"NOTIFICATION", processChainDto.getId());
					pcBeanNotification.setProcessChainName("NOTIFICATIONCHAIN");
					ProcChainBeanArr[1] = pcBeanNotification;
				}
				if (processChainDto.getHandler() != null && !(processChainDto.getHandler().equals("")))
				{
					pBeanHandlerBean = DtoToBeanConverter.DtoToBeanConverterToParseApprovalChainForReceive(processChainDto.getHandler(), 
							"HANDLER", processChainDto.getId());
					pBeanHandlerBean.setProcessChainName("HANDLERCHAIN");
					ProcChainBeanArr[2] = pBeanHandlerBean;
				}

				processChainDto.setProcChainBean(ProcChainBeanArr);
				request.setAttribute("actionForm", processChainDto);
			}
			catch (ProcessChainDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ModulesDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
		case RECEIVEALLPROCESSCHAIN:
		{

			DropDown dropDown = (DropDown) form;
			try
			{
				dropDown.setDropDown(processChainDao.findAll());
			}
			catch (ProcessChainDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("actionForm", dropDown);
			break;

		}
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		try
		{
			ProcessChain processchainDto = (ProcessChain) form;

			ProcessChain tempprocesschainDto = new ProcessChain();
			tempprocesschainDto.setProcName(processchainDto.getProcName());
			if (processchainDto.getApprovalChain() != "")
			{
				tempprocesschainDto.setApprovalChain(processchainDto.getApprovalChain());
			}
			if (processchainDto.getHandler() != "")
			{
				tempprocesschainDto.setHandler(processchainDto.getHandler());
			}
			if (processchainDto.getNotification() != "")
			{
				tempprocesschainDto.setNotification(processchainDto.getNotification());
			}
			tempprocesschainDto.setFeatureId(processchainDto.getFeatureId());

			ProcessChainDao processChainDao = ProcessChainDaoFactory.create();

			ProcessChainPk newPcPk= processChainDao.insert(tempprocesschainDto);
			processchainDto.setId(newPcPk.getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		try
		{
			ProcessChain processchainDto = (ProcessChain) form;

			ProcessChainDao processChainDao = ProcessChainDaoFactory.create();

			ProcessChain tempProcesschainDto = processChainDao.findByPrimaryKey(processchainDto.getId());

			tempProcesschainDto.setProcName(processchainDto.getProcName());

			tempProcesschainDto.setApprovalChain(processchainDto.getApprovalChain());

			tempProcesschainDto.setNotification(processchainDto.getNotification());
			tempProcesschainDto.setHandler(processchainDto.getHandler());
			tempProcesschainDto.setFeatureId(processchainDto.getFeatureId());
			processChainDao.update(new ProcessChainPk(processchainDto.getId()), tempProcesschainDto);

		}
		catch (ProcessChainDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
