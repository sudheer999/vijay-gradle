package com.dikshatech.portal.models;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.dikshatech.beans.CalendarBean;
import com.dikshatech.beans.HolidaysBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.jasper.JGenerator;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CalendarDao;
import com.dikshatech.portal.dao.HolidaysDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dto.Calendar;
import com.dikshatech.portal.dto.CalendarPk;
import com.dikshatech.portal.dto.Holidays;
import com.dikshatech.portal.dto.HolidaysPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.factory.CalendarDaoFactory;
import com.dikshatech.portal.factory.HolidaysDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class CalendarModel extends ActionMethods
{

	Logger log = LoggerUtil.getLogger(CalendarModel.class);
	
	protected static java.sql.Connection userConn;

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author sasmita.sabar delete calendar
	 **/
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{

		CalendarDao calendarDao = CalendarDaoFactory.create();
		ActionResult result = new ActionResult();
		try
		{
			switch (ActionMethods.DeleteTypes.getValue(form.getdType()))
			{
			case DELETEALL:
				Calendar calDto = (Calendar) form;
				CalendarPk calpk = new CalendarPk();
				int calid[] = calDto.getCalids();

				for (int id : calid)
				{
					calpk.setId(id);
					calendarDao.delete(calpk);
				}
				break;

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtodelete"));

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

	/**
	 * @author sasmita.sabar view calendar
	 **/
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		CalendarDao calendarDao = CalendarDaoFactory.create();
		ActionResult result = new ActionResult();

		switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
		{
		case RECEIVEALL:

			try
			{
				DropDown dropDown = (DropDown) form;
				Calendar[] cal1 = calendarDao.findAll();
				HolidaysDao holidaysDao = HolidaysDaoFactory.create();
				CalendarBean[] calBeans = new CalendarBean[cal1.length];

				int j = 0;
				for (Calendar calendar : cal1)
				{
					CalendarBean calendarBean = new CalendarBean();
					int regionId = calendar.getRegion();
					String regName1 = null;
					RegionsDao regDao1 = RegionsDaoFactory.create();
					Regions[] regions1 = regDao1.findWhereIdEquals(regionId);
					if(regions1.length > 0)
					{
						regName1 = regions1[0].getRegName();
						int id = calendar.getId();
						Holidays holidays[] = holidaysDao.findByCalendar(id);
    					HolidaysBean[] holidaysBeans = new HolidaysBean[holidays.length];
    					int i = 0;
    					for (Holidays holidays2 : holidays)
    					{
    						HolidaysBean holidaysBean = DtoToBeanConverter.DtoToBeanConverter(holidays2);
    						holidaysBeans[i] = holidaysBean;
    						i++;
    
    					}
    
    					calendarBean.setCalendarId(calendar.getId());
    					calendarBean.setCalendarName(calendar.getName());
    					calendarBean.setRegion(calendar.getRegion());
    					calendarBean.setRegionName(regName1);
    					calendarBean.setCalendarYear(calendar.getYear());
    					calendarBean.setHolidaysBean(holidaysBeans);
    					calBeans[j] = calendarBean;
    					j++;
					}
				}

				dropDown.setDropDown(calBeans);
				request.setAttribute("actionForm", dropDown);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoreceive"));
			}
			break;
		case RECEIVEBYCALID:
			try
			{
				Calendar calendarform = (Calendar) form;

				CalendarDao calendarDao2 = CalendarDaoFactory.create();
				Calendar cal = calendarDao2.findByPrimaryKey(calendarform.getId());

				CalendarBean[] calendarBean = new CalendarBean[1];
				HolidaysDao holidaysDao = HolidaysDaoFactory.create();

				RegionsDao regDao1 = RegionsDaoFactory.create();
				Regions[] regions1 = regDao1.findWhereIdEquals(cal.getRegion());

				Holidays holidays[] = holidaysDao.findByCalendar(cal.getId());
				HolidaysBean[] holidaysBeans = new HolidaysBean[holidays.length];
				int i = 0;
				for (Holidays holidays2 : holidays)
				{
					HolidaysBean holidaysBean = DtoToBeanConverter.DtoToBeanConverter(holidays2);
					holidaysBeans[i] = holidaysBean;
					i++;

				}

				CalendarBean calBean = DtoToBeanConverter.DtoToBeanConverter(cal);

				calBean.setRegionName(regions1[0].getRegName());
				calBean.setHolidaysBean(holidaysBeans);

				calendarBean[0] = calBean;
				DropDown dropDown = new DropDown();
				dropDown.setDropDown(calendarBean);

				request.setAttribute("actionForm", calendarBean);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoreceive"));
			}
			break;

		case EMAIL_PDF:

			try
			{
				boolean flag = false;
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				RegionsDao regDao = RegionsDaoFactory.create();

				Calendar calDto = (Calendar) form;
				Calendar calendar = calendarDao.findByPrimaryKey(calDto.getId());

				Regions regions = regDao.findByPrimaryKey(calendar.getRegion());
				String regName = regions.getRegName();

				ProfileInfo[] profileInfo = profileInfoDao.findByRegionId(regions.getId());
				int year = calendar.getYear();

				/** to generate PDF **/
				String outputFile = "";
				String name[]=new String[profileInfo.length];
				try
				{
					outputFile = calendar.getName().concat("_"+year + "").concat(regName).concat(".pdf");
					JGenerator generatepdf = new JGenerator();
					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("ID", calendar.getId());
					generatepdf.generateFile("calendar", outputFile, "Calendar.jrxml", params);
					flag = true;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtogeneratepdf"));
				}

				if (flag)
				{
					String filePath = PropertyLoader.getEnvVariable() + File.separator + "Data" + File.separator + "calendar";
					/** to sent mail **/
					if (profileInfo.length > 0)
					{
						for (int n=0;n<profileInfo.length;n++)
						{
							name[n] = profileInfo[n].getOfficalEmailId();
						}
						PortalMail portalMail = new PortalMail();
						portalMail.setMailSubject("Diksha Holiday List " + year);
						portalMail.setAllReceipientBCCMailId(name);
						Attachements attachments[] = new Attachements[1];
						Attachements attachment = new Attachements();
						attachment.setFileName(outputFile);
						attachment.setFilePath(filePath + File.separator + outputFile);
						attachments[0] = attachment;
						portalMail.setFileSources(attachments);
						//portalMail.setCandidateName(profInfo.getFirstName() + " " + profInfo.getLastName());
						portalMail.setTemplateName(MailSettings.CALENDAR);
						MailGenerator mailGenarator = new MailGenerator();
						mailGenarator.invoker(portalMail);

					}
					else
					{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.usernotfound"));
					}
				}
			}
			catch (Exception e)
			{
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.filenotfound"));
			}
			break;
			
		case RECEIVEHOLIDAYLIST:
			Login login = Login.getLogin(request);
			try{
				DropDown dropDown = (DropDown) form;
				
				CalendarDao calendarDao2 = CalendarDaoFactory.create();
				Calendar[] cal = calendarDao2.findWhereRegionEquals(login.getUserLogin().getRegionId());
				cal[0].getId();
				 
				HolidaysDao holidaysDao = HolidaysDaoFactory.create();
				Holidays[] holidays=holidaysDao.findWhereCalIdEquals(cal[0].getId());
				HolidaysBean[] holidaysBeans = new HolidaysBean[holidays.length];
				int i = 0;
				for (Holidays holidays2 : holidays)
				{
					HolidaysBean holidaysBean = DtoToBeanConverter.DtoToBeanConverter(holidays2);
					holidaysBeans[i] = holidaysBean;
					i++;
				}
				dropDown.setDropDown(holidaysBeans);
				request.setAttribute("actionForm", dropDown);
			}
			catch(Exception e)
			{
				e.printStackTrace();

			}

		}

		return result;
	}

	/**
	 * @author sasmita.sabar insert calendar
	 **/
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{

		CalendarDao calendarDao = CalendarDaoFactory.create();
		ActionResult result = new ActionResult();
		try
		{
			switch (ActionMethods.SaveTypes.getValue(form.getsType()))
			{
			case SAVE:
				Calendar calDto = (Calendar) form;
				String holidayList = calDto.getHolidayList();
				String holidayList1[] = holidayList.split(",");
				CalendarPk pk = new CalendarPk();
				pk = calendarDao.insert(calDto);
				Holidays holidaysDto = new Holidays();
				HolidaysDao holidaysDao = HolidaysDaoFactory.create();
				for (String holidaylist : holidayList1)
				{
					String holidayList3[] = holidaylist.split("~=~");
					for (String holiday : holidayList3)
					{

						if ((holiday.split("=")[0]).equalsIgnoreCase("holidayName"))
						{
							String holidayName = holidayList3[0].split("=")[1];
							holidaysDto.setHolidayName(holidayName);
						}
						else if ((holiday.split("=")[0]).equalsIgnoreCase("datePicker"))
						{
							String formatdate = holidayList3[1].split("=")[1].trim();
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
							try
							{
								Date datepicker = formatter.parse(formatdate);
								holidaysDto.setDatePicker(datepicker);
							}
							catch (ParseException e)
							{
								e.printStackTrace();
							}

						}
					}
					holidaysDto.setCalId(pk.getId());
					holidaysDao.insert(holidaysDto);
					holidaysDto.setId(0);

				}

				break;
			}
		}
		catch (Exception e)
		{
			if(e.toString().contains("Duplicate")){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtocreate"));
			}else{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosave"));
			}
		}
		return result;
	}

	/**
	 * @author sasmita.sabar update calendar
	 **/
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		try
		{
			switch (ActionMethods.UpdateTypes.getValue(form.getuType()))
			{
			case UPDATE:

				Calendar calDto = (Calendar) form;
				CalendarDao calendarDao2 = CalendarDaoFactory.create();
				CalendarPk pk = new CalendarPk();
				pk.setId(calDto.getId());
				calendarDao2.update(pk, calDto);

				Holidays holidays = new Holidays();
				HolidaysDao holidaysDao = HolidaysDaoFactory.create();

				String holidayList = calDto.getHolidayList();
				String holidayList1[] = holidayList.split(",");

				int id;
				HolidaysPk pk2 = new HolidaysPk();
				Holidays[] holidays2 = holidaysDao.findByCalendar(calDto.getId());

				List<Integer> holidaysIds = new ArrayList<Integer>();
				for (String holidaylist : holidayList1)
				{
					holidays.setId(0);
					String holidayList3[] = holidaylist.split("~=~");
					for (String holiday : holidayList3)
					{

						if ((holiday.split("=")[0]).equalsIgnoreCase("holidayName"))
						{
							String holidayName = holidayList3[0].split("=")[1];
							holidays.setHolidayName(holidayName);
						}
						else if ((holiday.split("=")[0]).equalsIgnoreCase("datePicker"))
						{
							String formatdate = null;

							formatdate = holidayList3[1].split("=")[1];

							SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
							try
							{
								Date datepicker = formatter.parse(formatdate);
								holidays.setDatePicker(datepicker);
							}
							catch (ParseException e)
							{
								e.printStackTrace();
							}

						}
						else if ((holiday.split("=")[0]).equalsIgnoreCase("id"))
						{
							id = Integer.parseInt(holidayList3[2].split("=")[1]);
							holidays.setId(id);
							pk2.setId(id);
						}
					}
					holidays.setCalId(calDto.getId());
					if (holidays.getId() != 0)
					{
						holidaysDao.update(pk2, holidays);
						holidaysIds.add(holidays.getId());

					}
					else
					{
						holidaysDao.insert(holidays);
					}
				}
				for (Holidays holidays3 : holidays2)
				{
					if (!(holidaysIds.contains(holidays3.getId())))
					{
						pk2.setId(holidays3.getId());
						holidaysDao.delete(pk2);
					}
				}

			}
		}
		catch (Exception e)
		{
			
			if(e.toString().contains("Duplicate")){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtocreate"));
			}else{
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoupdate"));
			}
			
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
