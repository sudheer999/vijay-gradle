package com.dikshatech.portal.actions;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ActionResult
{
	@Override
	public String toString()
	{
		return "ActionResult [actionMessages=" + actionMessages + ", forwardName=" + forwardName + ", methodResult="
		        + methodResult + "]";
	}

	public final static String	SUCCESS	   = "success";
	

	private boolean	           methodResult;
	private ActionMessages	   actionMessages;
	private String dispatchDestination;

	public String getDispatchDestination()
    {
    	return dispatchDestination;
    }

	public void setDispatchDestination(String dispatchDestination)
    {
    	this.dispatchDestination = dispatchDestination;
    }

	// default value will be success
	private String	           forwardName	= SUCCESS;

	public boolean isMethodResult()
	{
		return methodResult;
	}

	public void setMethodResult(boolean methodResult)
	{
		this.methodResult = methodResult;
	}

	public ActionMessages getActionMessages()
	{
		if (actionMessages == null)
			actionMessages = new ActionMessages();

		return actionMessages;
	}

/*	public void setActionMessages(ActionMessages actionMessages)
	{
		this.actionMessages = actionMessages;
	}*/

	public String getForwardName()
	{
		return this.forwardName;
	}

	public void setForwardName(String forwardName)
	{
		this.forwardName = forwardName;
	}

	public void handleDefaultException(Exception e)
	{
		this.setMethodResult(false);
		this.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.general", e.getMessage()+",caused by "+e.getCause()));
		e.printStackTrace();

	}
}
