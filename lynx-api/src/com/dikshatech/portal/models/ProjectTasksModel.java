package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ProjTasksDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProjTasks;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.exceptions.ProjTasksDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.factory.ProjTasksDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.forms.PortalForm;

public class ProjectTasksModel extends ActionMethods
{

	@Override
    public ActionResult defaultMethod(PortalForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public ActionResult delete(PortalForm form, HttpServletRequest request)
    {
	    // TODO Auto-generated method stub
	    return null;
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
		ActionResult result =  new ActionResult();
		Login login = Login.getLogin(request);
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
        {
        case RECEIVETASK:
        {
	       ProjTasks tasksForm = (ProjTasks) form;
	       ProjTasksDao taskDao = ProjTasksDaoFactory.create();
	      
	       try
	       {
	    	   ProjTasks task = taskDao.findByPrimaryKey(tasksForm.getId());
	    	   tasksForm.setProjId(task.getProjId());
	    	   tasksForm.setTasks(task.getTasks());
	    	   request.setAttribute("actionForm",tasksForm);
	           result.setForwardName("success");
	       }
	       catch (ProjTasksDaoException e)
	       {
	    	   // TODO Auto-generated catch block
	       		e.printStackTrace();
	       }
        
	        break;
        }
        case RECEIVETASKSBYPROJECT:
        {
	       ProjTasks tasksForm = (ProjTasks) form;
	       ProjTasksDao taskDao = ProjTasksDaoFactory.create();
	       ProjectDao projectDao = ProjectDaoFactory.create();
	       try
	       {
	    	   Project project = projectDao.findByPrimaryKey(tasksForm.getProjId());
	    	   ProjTasks[] allTasksInProj = taskDao.findWhereProjIdEquals(tasksForm.getProjId());
	    	   tasksForm.setAllTasks(allTasksInProj);
	    	   tasksForm.setProjId(project.getId());
	    	   tasksForm.setProjName(project.getName());
	    	   request.setAttribute("actionForm",tasksForm);
	           result.setForwardName("success");
	       }
	       catch (ProjTasksDaoException e)
	       {
	    	   // TODO Auto-generated catch block
	       		e.printStackTrace();
	       }
        catch (ProjectDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	        break;
        }
        case RECEIVEALLTASKS:
        {
        	ProjTasks tasksForm = (ProjTasks) form;
        	ProjTasksDao taskDao = ProjTasksDaoFactory.create();
        	ProjectDao projectDao = ProjectDaoFactory.create();
        	Set<Project> projects = new HashSet<Project>();
        	
        	try
        	{
        		//String sql = "SELECT DISTINCT PROJ_ID FROM PROJ_TASKS";
        		
        		String sql="SELECT * FROM PROJECT WHERE ID IN (SELECT DISTINCT PROJ_ID FROM PROJ_TASKS) AND IS_ENABLE ='Enabled'";
        		Project[] allProjects = projectDao.findProjectFromTasks(sql, null);
        		                          
        		for (Project eachProjectFetched : allProjects)
                {
        			Project project = new Project();
        			ProjTasks[] allTasksInProj = taskDao.findWhereProjIdEquals(eachProjectFetched.getId());
        			project.setAllTasks(allTasksInProj);
        			project.setId(eachProjectFetched.getId());
        			project.setName(eachProjectFetched.getName());
        			if(eachProjectFetched.getOwnerId() == login.getUserId()){
        				project.setOwnerFlag(true);
        			}        			
        			projects.add(project);        			
                }
        		tasksForm.setProjects(projects);
        	//	ProjTasks[] allTasksInProj = taskDao.findWhereProjIdEquals(tasksForm.getProjId());
        		//tasksForm.setAllTasks(allTasksInProj);
        		request.setAttribute("actionForm",tasksForm);
 	           	result.setForwardName("success");
        		
        	}
        	catch(Exception e)
        	{
        		
        	}
        	break;
        }
        default:
	        break;
        }
	    // TODO Auto-generated method stub
	    return result;
    }

	@Override
    public ActionResult save(PortalForm form, HttpServletRequest request)
    {
		ActionResult result =  new ActionResult();
	    ProjTasks tasksForm = (ProjTasks) form;
	    ProjTasksDao tasksDao = ProjTasksDaoFactory.create();
		try
		{
			if (tasksForm.getTasksArr() != null)
			{
				for (String task : tasksForm.getTasksArr())
				{
					ProjTasks projTasks = new ProjTasks();
					projTasks.setTasks(task);
					projTasks.setProjId(tasksForm.getProjId());
					tasksDao.insert(projTasks);
				}

			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return result;
    }

	@Override
    public ActionResult update(PortalForm form, HttpServletRequest request)
    {
		ActionResult result = new ActionResult();
		ProjTasks tasksForm = (ProjTasks) form;
		
		ProjTasksDao tasksDao = ProjTasksDaoFactory.create();
		try
        {
			
	      //  ProjTasks[] projTask = tasksDao.findWhereProjIdEquals(tasksForm.getProjId());
	     //   int projId = projTask.getProjId();
	        if(tasksForm.getTasksArr() != null)
	        {
	        	boolean isDeleted = tasksDao.deleteAllTaskByProject(tasksForm.getId());
	        	if(isDeleted)
				{
					for (String task : tasksForm.getTasksArr())
					{
						ProjTasks projTasks = new ProjTasks();
						projTasks.setTasks(task);
						projTasks.setProjId(tasksForm.getProjId());
						tasksDao.insert(projTasks);
					}
				}
	        
	        }
	        result.setForwardName("success");
        }
        catch (ProjTasksDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		
	    // TODO Auto-generated method stub
	    return result;
    }

	@Override
    public ActionResult validate(PortalForm form, HttpServletRequest request)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

}
