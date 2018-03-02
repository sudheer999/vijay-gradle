package com.dikshatech.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.LoginDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.LoginDaoFactory;

public class PortalUtility {

	protected static final Logger	logger	= Logger.getLogger(PortalUtility.class);

	public static String returnMonth(int month) {
		String MN = null;
		switch (month) {
			case 1:
				MN = "Jan";
				break;
			case 2:
				MN = "Feb";
				break;
			case 3:
				MN = "Mar";
				break;
			case 4:
				MN = "Apr";
				break;
			case 5:
				MN = "May";
				break;
			case 6:
				MN = "Jun";
				break;
			case 7:
				MN = "Jul";
				break;
			case 8:
				MN = "Aug";
				break;
			case 9:
				MN = "Sep";
				break;
			case 10:
				MN = "Oct";
				break;
			case 11:
				MN = "Nov";
				break;
			case 12:
				MN = "Dec";
				break;
			default:
				MN = "No result found";
		}
		return MN;
	}

	public static String getMonthFullName(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month - 1);
		return new SimpleDateFormat("MMMM").format(cal.getTime());
	}
	/**
	 * @author supriya.bhike
	 * @param date
	 * @param months
	 * @return Returns date with proper value of year,month and days adding number of months to date
	 */
	public static java.sql.Date addMonths(final java.util.Date date, final int months) {
		java.sql.Date calculatedDate = null;
		if (date != null){
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, months);
			calculatedDate = new java.sql.Date(calendar.getTime().getTime());
		}
		return calculatedDate;
	}
	public static java.sql.Date utilToSql(final java.util.Date date) {
		java.sql.Date sqlDate = null;
		if (date != null){
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			sqlDate = new java.sql.Date(calendar.getTime().getTime());
		}
		return sqlDate;
	}

	public static java.sql.Date addDays(final java.util.Date date, final int days) {
		java.sql.Date calculatedDate = null;
		if (date != null){
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, days);
			calculatedDate = new java.sql.Date(calendar.getTime().getTime());
		}
		return calculatedDate;
	}

	public Object[] copyArray(Object[] srcArray, Object[] destArray) {
		for (int index = 0; index < srcArray.length; index++){
			destArray[index] = srcArray[index];
		}
		return destArray;
	}

	public static Date fromStringToDate(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			date = sdf.parse(d);
		}
		return date;
	}
	public static Date fromStringToDates(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			date = sdf.parse(d);
		}
		return date;
	}


	public static Date fromStringTo(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(d);
		}
		return date;
	}

	public static Date fromStringToDateHHMMSS(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			date = sdf.parse(d);
		}
		return date;
	}

	public static Date fromStringToDateDDMMYYYYHHMMSS(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			date = sdf.parse(d);
		}
		return date;
	}

	public static Date StringToDate(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			date = sdf.parse(d);
		}
		return date;
	}

	public static Date StringToDateDB(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(d);
		}
		return date;
	}

	//EEE, d MMM yyyy HH:mm:ss Z
	//Tue May 15 00:00:00 IST 2012
	public static Date StringToDateEEE(String d) throws ParseException {
		Date date = null;
		if (d != null && d.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
			date = sdf.parse(d);
		}
		return date;
	}

	public static String formatDate(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String date1 = null;
			formatter.setTimeZone(TimeZone.getDefault());
			date1 = formatter.format(d);
			return date1;
		}
		return null;
	}

	public static String formatDateddMMyyyy(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = null;
			formatter.setTimeZone(TimeZone.getDefault());
			date1 = formatter.format(d);
			return date1;
		}
		return null;
	}

	public static String formatDateToyyyymmddahhmmss(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssa");
			String date1 = null;
			formatter.setTimeZone(TimeZone.getDefault());
			date1 = formatter.format(d);
			//logger.info("formatDateToyyyymmddahhmmss------------------------------>"+date1);
			return date1;
		}
		return null;
	}

	/**
	 * @author supriya.bhike
	 *         Retutn date time to string as dd/MM/yyyy hh:mm:ssa
	 * @param d
	 * @return dd/MM/yyyy hh:mm:ssa
	 */
	public static String formatDateToddmmyyyyhhmmss(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssa");
			String date1 = null;
			formatter.setTimeZone(TimeZone.getDefault());
			date1 = formatter.format(d);
			return date1;
		}
		return null;
	}

	/**
	 * @author supriya.bhike
	 *         Retutn date time to string as dd-mm-yyyy hh:mm:ssa
	 * @param d
	 * @return dd-mm-yyyy hh:mm:ssa
	 */
	public static String formatDateddmmyyyyhhmmss(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
			formatter.setTimeZone(TimeZone.getDefault());
			return formatter.format(d);
		}
		return null;
	}

	//initially written for usage in ClientModel
	public static String formatDateToddmmyy(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
			String date1 = null;
			formatter.setTimeZone(TimeZone.getDefault());
			date1 = formatter.format(d);
			return date1;
		}
		return null;
	}

	public static String yearFormatDate(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date1 = null;
			formatter.setTimeZone(TimeZone.getDefault());
			date1 = formatter.format(d);
			return date1;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static Date formateDateTimeToDate(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = new Date(dateFormat.format(date));
			return date1;
		}
		return null;
	}

	public static String formateDateTimeToDateDB(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(date);
		}
		return null;
	}

	/**
	 * @author gurunath.rokkam
	 * @param date
	 * @return EX: 31-12-2012
	 */
	public static String getdd_MM_yyyy(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			return dateFormat.format(date);
		}
		return null;
	}

	/**
	 * @author gurunath.rokkam
	 * @param date
	 * @return EX: 31-12-2012 01:15 PM
	 */
	public static String getdd_MM_yyyy_hh_mm_a(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			return dateFormat.format(date);
		}
		return null;
	}

	public static String formateDateTimeToDByyyyMMdd(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(date);
		}
		return null;
	}

	/**
	 * @author supriya.bhike
	 * @param date
	 *            - date time
	 * @return format date time date to MM/dd/yyyy format
	 */
	public static Date formateDateTimeToDateyyyyMMdd(Date date) {
		if (date != null){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try{
				String dd = simpleDateFormat.format(date);
				return fromStringToDate(dd);
			} catch (ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @author supriya.bhike
	 * @param date
	 * @return format date to dd/MM/yyyy HH:mm:ss
	 */
	public static Date formateDateToDate(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String date1 = dateFormat.format(date);
			Date date2 = null;
			try{
				date2 = dateFormat.parse(date1);
			} catch (ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date2;
		}
		return null;
	}

	public static Date formateDateTimeToDateTime(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String date1 = dateFormat.format(date);
			try{
				return fromStringToDate(date1);
			} catch (ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Date formateDateTimeToDateOnly(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = dateFormat.format(date);
			try{
				return fromStringToDate(date1);
			} catch (ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String formatTime(Date d) {
		if (d != null){
			SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
			String date1 = null;
			formatter.setTimeZone(TimeZone.getDefault());
			date1 = formatter.format(d);
			return date1;
		}
		return null;
	}

	public static String formatDate(String Str) // give input as dd/mm/yyyy
	{
		// TimeZone.setDefault(TimeZone.getTimeZone(timezone));
		String[] DATEMONTH = Str.toString().split("/");
		return DATEMONTH[2] + "-" + DATEMONTH[1] + "-" + DATEMONTH[0];
	}

	/**
	 * @author supriya.bhike
	 * @param date
	 *            firstdate
	 * @param date1
	 *            second greater date
	 * @return difference in months between two dates
	 */
	public static int calculateNoOfMonths(String date, String date1) {
		String[] DATEMONTH = date.toString().split("/");
		String[] DATEMONTH1 = date1.toString().split("/");
		Calendar firstDate = new GregorianCalendar(Integer.parseInt(DATEMONTH[2]), Integer.parseInt(DATEMONTH[0]), Integer.parseInt(DATEMONTH[1]));
		Calendar secondDate = new GregorianCalendar(Integer.parseInt(DATEMONTH1[2]), Integer.parseInt(DATEMONTH1[0]), Integer.parseInt(DATEMONTH1[1]));
		int months = (firstDate.get(Calendar.YEAR) - secondDate.get(Calendar.YEAR)) * 12 + (firstDate.get(Calendar.MONTH) - secondDate.get(Calendar.MONTH));
		return months;
	}

	/**
	 * @author supriya.bhike
	 * @param date
	 *            firstdate
	 * @param months
	 *            second months
	 * @return add months to given date
	 */
	/*
	public static int calculatddeNoOfMonths(Date date, int months) {
	logger.info("Diff is.." + months);
	return months;
	}*/

	public static String returnDate(String Str) // this returns at a time
	// today's either yyyy or MM or
	// yyyy-MM-dd
	{
		Date date = new Date();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat(Str);
		return sdf.format(date);
	}

	public static long gettodaysDateTimeInMilSec() // this returns base date to
	// current date in
	// milliseconds, where base
	// date 1970 Jan 01 5:30:00
	{
		Date date = new Date();
		long milsec = date.getTime();
		return milsec;
	}

	public static String getUniqueID() {
		return String.valueOf(UUID.randomUUID());
	}

	public static long getCurrentHour() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.HOUR);
	}

	public static long getCurrentMinute() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.MINUTE);
	}

	public static long getCurrentSecond() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.SECOND);
	}

	public static long getCurrentDate() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.DATE);
	}

	public static long getCurrentyear() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.YEAR);
	}

	public static String getTommorowDate(String dateformat) {
		String singleDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.DATE, 1);
		singleDate = sdf.format(c1.getTime());
		return singleDate;
	}

	/**
	 * This method will return only Week Days Date, ( It will not return Sat & Sun date, except these both date, it returns Mon )
	 */
	public static String getNextWeekDaysDate(String dateformat) {
		String singleDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Calendar c1 = Calendar.getInstance();
		switch (c1.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.FRIDAY:
				c1.add(Calendar.DATE, 3);
				break;
			default:
				c1.add(Calendar.DATE, 1);
				break;
		}
		singleDate = sdf.format(c1.getTime());
		return singleDate;
	}

	public static long days(String date1, String date2, int type) { /* count WeekDays with out Sunday and Saturday */
		/*
		 * if(type == PortalConstants.COMP_OFF) { return
		 * withweekend(date1,date2); }else{ return withoutweekend(date1,date2);
		 * }
		 */
		return withoutweekend(date1, date2);
	}

	public static long withoutweekend(String date1, String date2) { /* count WeekDays with out Sunday and Saturday */
		// Ignore argument check
		String[] d1 = date1.trim().split("/");
		String[] d2 = date2.trim().split("/");
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(d1[2]), Integer.parseInt(d1[1]) - 1, Integer.parseInt(d1[0])); // yyyy
		// mm
		// dd
		int w1 = c1.get(Calendar.DAY_OF_WEEK);
		c1.add(Calendar.DAY_OF_WEEK, -w1);
		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt(d2[2]), Integer.parseInt(d2[1]) - 1, Integer.parseInt(d2[0]));
		int w2 = c2.get(Calendar.DAY_OF_WEEK);
		c2.add(Calendar.DAY_OF_WEEK, -w2);
		// logger.info((c1.compareTo(c2))+":(c1.compareTo(c2))");
		if (w1 == w2 & w1 == 7) /*
								 * if both dates are same and start date is
								 * Saturday
								 */
		{
			return -97;
		} else if (w1 == w2 & w1 == 1) /*
										 * if both dates are same and start date is
										 * Sunday
										 */
		{
			return -96;
		} else if (w1 == 7 & w2 == 1) /*
										 * if start date = Saturday and end date =
										 * Sunday
										 */
		{
			return -95;
		} else if (w1 == 1 & w2 == 7) /*
										 * if start date = Sunday and end date =
										 * Saturday
										 */
		{
			return -94;
		} else{
			/* end Saturday to start Saturday */
			int satflag = (w1 != 1 & w1 != 7 & w2 == 7) ? 1 : 0; /*
																	 * When start
																	 * date is not
																	 * week-end and
																	 * End date is
																	 * Saturday,
																	 * then our
																	 * total date
																	 * gets wrong
																	 * value i.e.
																	 * (TOTAL VALUE
																	 * + 1). So to
																	 * maintain
																	 * actual date
																	 * difference
																	 * we need this
																	 * flag.
																	 */
			long days = (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000 * 60 * 60 * 24);
			long daysWithoutSunday = days - (days * 2 / 7);
			return (daysWithoutSunday - w1 + w2 - satflag);
		}
	}

	public static long withweekend(String date1, String date2) { /* count Days difference */
		// Ignore argument check
		String[] d1 = date1.trim().split("/");
		String[] d2 = date2.trim().split("/");
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(d1[2]), Integer.parseInt(d1[1]) - 1, Integer.parseInt(d1[0])); // yyyy
		// mm
		// dd
		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt(d2[2]), Integer.parseInt(d2[1]) - 1, Integer.parseInt(d2[0]));
		long milliseconds1 = c1.getTimeInMillis();
		long milliseconds2 = c2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		// long diffSeconds = diff / 1000;
		// long diffMinutes = diff / (60 * 1000);
		// long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}

	public static void copyDirectory(File srcDir, File dstDir) throws IOException {
		if (srcDir.isDirectory()){
			if (!dstDir.exists()){
				dstDir.mkdir();
			}
			String[] children = srcDir.list();
			for (int i = 0; i < children.length; i++){
				copy(new File(srcDir, children[i]), new File(dstDir, children[i]));
			}
		}
	}

	public static void createDirectory(File newDir) {
		if (!newDir.exists()){
			newDir.mkdir();
		}
	}

	public static void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);
		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0){
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/**
	 * Get the list of file name, from a folder
	 * 
	 * @param dst
	 * @return
	 */
	public static HashMap getListOfFileName(File dst) {
		HashMap map = new HashMap();
		File dir = dst;
		String[] children = dir.list();
		if (children == null){
			// Either dir does not exist or is not a directory
		} else{
			for (int i = 0; i < children.length; i++){
				// Get filename of file or directory
				// String BuiltFileName="File "+i;
				map.put("" + i, children[i]);
				// logger.info("filename:"+filename);
			}
		}
		return map;
	}

	public static String renameFileName(String oldNameStr, String newNameStr) {
		File oldName = new File(oldNameStr);
		File newName = new File(newNameStr);
		boolean rename = oldName.renameTo(newName);
		if (rename){
			logger.info("File :" + oldName + " rename to :" + newName);
		} else logger.info("File :" + oldName + " failed to rename.");
		return newNameStr;
	}

	public static boolean deleteDir(File dir) /*
												 * removing 1st all files then
												 * remove empty dir
												 */
	{
		if (dir.isDirectory()){
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++){
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success){
					return false;
				}
			}
		} // The directory is now empty so delete it
		return dir.delete();
	}

	public static boolean deleteFile(File file) /* removing files */
	{
		if (!file.isDirectory()) // This is a file.
		{
			boolean success = file.delete();
			if (!success){
				return false; // file deletion fail
			}
			return true; // file deletion successful
		}
		return false; // This is not a file.
	}

	public static String getformatedFileName(String oldNameStr, String newNameHelperStr) {
		/* extracting file extension with help of dot */
		// String[] extension = oldNameStr.split("\\.");
		// String oldNameWithOutExtension = extension[0];
		String ext = "";
		if (oldNameStr.endsWith(".xls")) ext = ".xls";
		else if (oldNameStr.endsWith(".xlsx")) ext = ".xlsx";
		else ext = "";
		// String fileExtension = extension[1];
		String newNameStr = newNameHelperStr + ext;
		return newNameStr;
	}

	/*
	 * Example: String extensionSeparator = "."; String pathSeparator = "\\";
	 */
	public static String getNewFileName(String oldNameStr, String extensionSeparator, String pathSeparator) {
		/* extracting file extension with help of dot */
		return (filename(oldNameStr, extensionSeparator, pathSeparator) + "__" + PortalUtility.getUniqueID() + "." + extension(oldNameStr, extensionSeparator));
	}

	public static String getPreviousFileName(String latestNameStr, String extensionSeparator, String pathSeparator) {
		/* extracting file extension with help of dot */
		return (filename(latestNameStr, extensionSeparator, pathSeparator) + "." + extension(latestNameStr, "."));
	}

	public static String extension(String fullPath, String extensionSeparator) {
		int dot = fullPath.lastIndexOf(extensionSeparator);
		return fullPath.substring(dot + 1);
	}

	public static String filename(String fullPath, String extensionSeparator, String pathSeparator) { // gets filename without extension
		int dot = fullPath.lastIndexOf(extensionSeparator);
		int sep = fullPath.lastIndexOf(pathSeparator);
		return fullPath.substring(sep + 1, dot);
	}

	public static String getDDMMYYYY(String STR) // yyyy-mm-dd
	{
		String[] DATEMONTH = STR.toString().split("-");
		return DATEMONTH[2] + "-" + DATEMONTH[1] + "-" + DATEMONTH[0];
	}

	public static StringBuilder getNextDates(String _fromdate, int _forDays) // yyyy-mm-dd
	{
		StringBuilder DateStr = new StringBuilder();
		String _dateChange = getTodayDate(_fromdate);
		for (int i = 0; i < _forDays; i++){
			DateStr.append(_dateChange);
			_dateChange = getTommDate(_dateChange);
			DateStr.append(",");
		}
		return DateStr;
	}

	public static String get_N_Date(String _Trgdate, int _forDays) // yyyy-mm-dd
	{
		String singleDate = null;
		String[] strDate = _Trgdate.split("/");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(strDate[2]), Integer.parseInt(strDate[1]) - 1, Integer.parseInt(strDate[0]));
		c1.add(Calendar.DATE, _forDays);
		singleDate = sdf.format(c1.getTime());
		return singleDate;
	}

	public static String getTommDate(String _trgdate) {
		String singleDate = null;
		String[] strDate = _trgdate.split("/");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Calendar c1 = Calendar.getInstance();
		int tempMnth = (Integer.parseInt(strDate[0]));
		c1.set(Integer.parseInt(strDate[2]), Integer.parseInt(strDate[1]) - 1, tempMnth);
		c1.add(Calendar.DATE, 1);
		singleDate = sdf.format(c1.getTime());
		logger.info("Date : " + singleDate);
		return singleDate;
	}

	public static String getTodayDate(String _Trgdate) {
		String singleDate = null;
		String[] strDate = _Trgdate.split("/");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(strDate[2]), Integer.parseInt(strDate[1]) - 1, Integer.parseInt(strDate[0]));
		c1.add(Calendar.DATE, 0);
		singleDate = sdf.format(c1.getTime());
		return singleDate;
	}

	public static String returnTodaysDate() {
		String singleDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.DATE, 0);
		singleDate = sdf.format(c1.getTime());
		return singleDate;
	}

	public static String getFormatedDate(String trgDate) {
		String singleDate = null;
		String[] strDate = trgDate.split("/");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(strDate[2]), Integer.parseInt(strDate[1]) - 1, Integer.parseInt(strDate[0]));
		c1.add(Calendar.DATE, 0);
		singleDate = sdf.format(c1.getTime());
		return singleDate;
	}

	public static String getTomDATE_YYYYMMDD(String _trgdate) {
		String singleDate = null;
		String[] strDate = _trgdate.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		int tempDay = (Integer.parseInt(strDate[2]));
		c1.set(Integer.parseInt(strDate[0]), Integer.parseInt(strDate[1]) - 1, tempDay);
		c1.add(Calendar.DATE, 1);
		singleDate = sdf.format(c1.getTime());
		logger.info("Date : " + singleDate);
		return singleDate;
	}

	public static String getDDMMYYYYSplash(String STR) // yyyy-mm-dd to return
	// dd/mm/yyyy
	{
		if (STR != null) if (STR.length() == 10){
			String[] DATEMONTH = STR.toString().split("-");
			return DATEMONTH[2] + "/" + DATEMONTH[1] + "/" + DATEMONTH[0];
		}
		return " ";
	}

	public static String removeDelimiterFromDate(String STR, String delimiter) // dd/mm/yyyy
	// to
	// ddmmyyyy
	{
		if (STR != null) if (STR.length() == 10){
			return STR.replace(delimiter, "");
		}
		return " ";
	}

	public static String returnTime_HH_MM(String timeStr) // give input as
	// HH:MM:SS and
	// return string be
	// HH:MM
	{
		return (timeStr != null ? timeStr.substring(0, 5) : "");
	}

	public static String removeNullStr(String strVal) {
		return (!strVal.equals("null") ? strVal : " ");
	}

	public static String removeNull(String strVal) {
		return (strVal != null ? strVal : " ");
	}

	public static String removeNullByZero(String strVal) {
		return (strVal != null ? strVal : "0");
	}

	public static String removeNullSpacesByZero(String strVal) {
		strVal = removeNullByZero(strVal);
		return (strVal.trim().length() > 0 ? strVal : "0");
	}

	public static String replaceNullWithDash(String strVal) {
		return (strVal != null ? strVal : " --- ");
	}

	public static String removeZeros(int intVal) {
		return (intVal != 0 ? intVal + "" : " --- ");
	}

	public static String replaceDummyDateByStr(String dateVal) {
		return (!(removeNull(dateVal).equals("00/00/0000")) ? dateVal : "");
	}

	public static String removeZerosBySpace(int intVal) {
		return (intVal != 0 ? intVal + "" : " ");
	}

	public static int getGrtZero(int intVal) {
		return (intVal > 0 ? intVal : 0);
	}

	public static String removeDotstr(String strVal) {
		if (strVal.contains(".")) return strVal.trim().substring(0, strVal.trim().indexOf("."));
		return strVal;
	}

	/**
	 * @author supriya.bhike
	 * @param strMnth
	 * @return list of current week day,date and time starting from sunday to
	 *         saturday in following format list[Sun Aug 14 14:26:32 IST 2011,]
	 *         Day0---Sun Aug 14 14:26:32 IST 2011 Day1---Mon Aug 15 14:26:32
	 *         IST 2011 Day2---Tue Aug 16 14:26:32 IST 2011 Day3---Wed Aug 17
	 *         14:26:32 IST 2011 Day4---Thu Aug 18 14:26:32 IST 2011 Day5---Fri
	 *         Aug 19 14:26:32 IST 2011 Day6---Sat Aug 20 14:26:32 IST 2011
	 */
	public static StringBuilder getCurrentWeek(String dateStr) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		// df.parse formats string -->date
		Date selectedDate = null;
		try{
			selectedDate = df.parse(dateStr);
		} catch (ParseException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// df.format formats date -->string
		StringBuilder stringBuilder = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(selectedDate);
		if (calendar.get(Calendar.DAY_OF_WEEK) != 7){
			calendar.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK));
		}
		Date d1 = calendar.getTime();
		stringBuilder.append(d1.getDate());
		stringBuilder.append(",");
		// First First DAY of current Week
		// All the dates in Week
		for (int wkCount = 1; wkCount < 7; wkCount++){
			// logger.info("Day"+wkCount+"---"+calendar.getTime());
			calendar.add(Calendar.DATE, +1);
			d1 = calendar.getTime();
			// df.format formats date -->string
			stringBuilder.append(d1.getDate());
			stringBuilder.append(",");
		}
		return stringBuilder;
	}

	/**
	 * get first date of the selected/current week
	 * 
	 * @param strMnth
	 * @return
	 */
	public static String getFirstCurrentWeek(String dateStr) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		// df.parse formats string -->date
		Date selectedDate = null;
		try{
			selectedDate = df.parse(dateStr);
		} catch (ParseException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// df.format formats date -->string
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(selectedDate);
		if (calendar.get(Calendar.DAY_OF_WEEK) != 7){
			calendar.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK));
		}
		Date d1 = calendar.getTime();
		return df.format(d1);
	}

	public static long getDiffbetweenTwoDates(String strDate1, String strDate2, String spliter) /*
																								 * strDate1
																								 * =
																								 * start
																								 * date
																								 * ,
																								 * strDate2
																								 * =
																								 * end
																								 * date
																								 */
	{
		long diffDays;
		String[] date1 = getDateArray(strDate1, spliter);
		String[] date2 = getDateArray(strDate2, spliter);
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
		calendar2.set(Integer.parseInt(date2[2]), Integer.parseInt(date2[1]), Integer.parseInt(date2[0]));
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		// long diffSeconds = diff / 1000;
		// long diffMinutes = diff / (60 * 1000);
		// long diffHours = diff / (60 * 60 * 1000);
		diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}

	public static String[] getDateArray(String rtnStrDate, String spliter) {
		String[] rtnDate = rtnStrDate.split(spliter);
		return rtnDate;
	}

	public static String[] getTimeArray(String rtnStrTime, String spliter) {
		String[] rtnTime = rtnStrTime.split(spliter);
		return rtnTime;
	}

	public static boolean getRoleTypeSpoc(int roleType) {
		if (roleType == 5101 || roleType == 5158 || roleType == 5159 || roleType == 5160 || roleType == 5161 || roleType == 5162 || roleType == 5163 || roleType == 5164 || roleType == 5166 || roleType == 5168){
			return true;
		}
		return false;
	}

	public static String removeBracesessGetEmpID(String strVal, String indexcharLeft, String indexcharRight) // this
	// removes
	// all
	// string
	// and
	// bracesses,
	// return
	// employeeid
	{
		return strVal.trim().substring(strVal.indexOf(indexcharLeft) + 1, strVal.indexOf(indexcharRight)).trim();
	}

	/* check all indexes */
	public static boolean isSuccessfulBatchTransaction(int[] total_transactions) {
		if (total_transactions != null && total_transactions.length > 0){
			for (int i : total_transactions){
				if (i == 0){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/* check only those index which are allowed */
	public static boolean isSuccBatchTranAlongCondition(int[] total_transactions, ArrayList<Integer> uncheckIndexs) {
		if (total_transactions != null && total_transactions.length > 0){
			for (int i = 0; i < total_transactions.length; i++){
				if (uncheckIndexs.contains(i)){
					continue;
				} else if (total_transactions[i] == 0){
					return false;
				} else{
					continue;
				}
			}
			return true;
		}
		return false;
	}

	public static String getFormatedString(String msg, String... replacer) {
		/*
		 * replacing {0},{1} etc arguments with some arguments specified ex: Hi
		 * {0}, your leave application approved on {1}. EQUIVALENT TO Hi Raj,
		 * your leave application approved on 12 july 2011.
		 */
		for (int i = 0; i < replacer.length; i++){
			msg = msg.replace("{" + i + "}", replacer[i]);
		}
		return msg;
	}

	public static boolean compareStrArr_By_SingleStr(String[] srcArray, String comparableEle) {
		for (String ele : srcArray){
			if (ele.equals(comparableEle)) return true;
		}
		return false;
	}

	public static String getLowerCase(String str) {
		return (str != null ? str.toLowerCase() : "");
	}

	public static String[] getArrayString(ArrayList<String> list) {
		return list.toArray(new String[list.size()]);
	}

	public static String includeZerosForEmpID(int empid) {
		String StrEmpid = Integer.toString(empid);
		for (int i = StrEmpid.length(); i < 4; i++){
			StrEmpid = new StringBuilder("0").append(StrEmpid.trim()).toString();
		}
		return StrEmpid;
	}

	public String generateUserName(ProfileInfo profileInfo, int userId) {
		//String userName = null;
		String NonEncPassword = null;
		try{
			LoginDao loginDao = LoginDaoFactory.create();
			Login login = new Login();
			String Uname = profileInfo.getFirstName().replace(" ", "").trim() + "." + profileInfo.getLastName().replace(" ", "").trim();
			String username = "%" + Uname + "%";
			/**
			 * Check if same guest login exist in Login table if Yes append
			 * 1,2,3...and so on and set password as DOB.
			 */
			String sql = "SELECT * FROM LOGIN WHERE USER_NAME like  ? ";
			Login loginCheck[] = loginDao.findByDynamicSelect(sql, new Object[] { username });
			Uname = Uname.trim().toLowerCase();
			if (loginCheck.length > 0){
				Uname = Uname + Integer.toString(loginCheck.length);
				login.setUserName(Uname + "@dikshatech.com");
			} else{
				login.setUserName(Uname + "@dikshatech.com");
			}
			// MM/dd/yyyy DOB
			String date = PortalUtility.formatDate(profileInfo.getDob());
			String arr[] = date.split("/");
			login.setPassword(arr[1] + arr[0] + arr[2]);
			login.setCandidateId(userId);
			NonEncPassword = login.getPassword();
			/**
			 * Decrypt Password
			 */
			//before inserting Encrypt Password using DES algorithm
			String encryptedPassword = DesEncrypterDecrypter.getInstance().encrypt(login.getPassword());
			//String nonEncPass = login.getPassword();
			if (encryptedPassword != null){
				login.setPassword(encryptedPassword);
			}
			loginDao.insert(login);
		} catch (Exception e){
			// TODO: handle exception
		}
		return NonEncPassword;
	}

	public static String getEndDateOfTheWeek(Date date) {
		String dateStr = "";
		Format formatter;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = formatter.format(date);
		calendar.add(Calendar.DATE, 6);
		date = calendar.getTime();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = formatter.format(date);
		return dateStr;
	}

	public static String getEndDateOfTheWeek(Date date, int next) {
		String dateStr = "";
		Format formatter;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = formatter.format(date);
		calendar.add(Calendar.DATE, next);
		date = calendar.getTime();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = formatter.format(date);
		return dateStr;
	}

	public static String getDayOfParticularDate(String dateStr) {
		String[] dateSplitArr = dateStr.split("-");
		// |--Format is YYYY/MTH/DAY
		Date date = (new GregorianCalendar(Integer.parseInt(dateSplitArr[0]), Integer.parseInt(dateSplitArr[1]) - 1, Integer.parseInt(dateSplitArr[2])).getTime());
		SimpleDateFormat f = new SimpleDateFormat("EEEE");
		String day = f.format(date);
		return day;
	}

	public static Time fromStringToTime(String time) throws ParseException {
		long tl;
		Time t = null;
		Date d = new Date();
		if (time != null && time.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
			d = sdf.parse(time);
			tl = d.getTime();
			t = new Time(tl);
		}
		return t;
	}

	public Integer[] convertStringArraytoIntArray(String[] sarray) {
		if (sarray != null){
			Integer intarray[] = new Integer[sarray.length];
			for (int i = 0; i < sarray.length; i++){
				intarray[i] = Integer.parseInt(sarray[i]);
			}
			return intarray;
		}
		return null;
	}

	/**
	 * @author supriya.bhike
	 * @param date
	 * @return DateTime to Date (yyyy/MM/dd)
	 */
	@SuppressWarnings("deprecation")
	public static Date formateDateTimeToDateFormat(Date date) {
		if (date != null){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date1 = new Date(dateFormat.format(date));
			return date1;
		}
		return null;
	}

	/**
	 * @author supriya.bhike
	 * @param dateOfJoining
	 * @return returns two days before todays date
	 *         @
	 */
	public static Date reminderDate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, +2);
		return (calendar.getTime());
	}
	
	public static boolean isReminderToSend(Date expireDate)
	{
		boolean flag= false;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(expireDate);
		//calendar1.add(Calendar.DATE, -2);
		Date date = new Date();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		
		long diff = calendar1.getTimeInMillis() -calendar2.getTimeInMillis();
		long diffDays = diff / (24 * 60 * 60 * 1000);
			if(diffDays ==2)
				{
					flag = true;
				}
		return flag ;
	}

	/**
	 * @author supriya.bhike
	 * @param dateOfJoining
	 * @return returns two days before 6 months after temp date of joining
	 *//*
	public static Date reminderDatef(Date dateOfJoining) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		calendar.add(Calendar.DATE, 178);
		return (calendar.getTime());
	}
*/
	/**
	 * @author supriya.bhike
	 * @param dateOfJoining
	 * @return returns one days before 6 months after date of joining
	 *         @
	 */
	/*public static Date reminderDateOneDayBefore(Date dateOfJoining) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		calendar.add(Calendar.DATE, 179);
		logger.info("adding 6 months minus 1  days" + calendar.getTime());
		return (calendar.getTime());
	}*/

	/**
	 * @author supriya.bhike
	 * @param dateOfJoining
	 * @return returns two days before 6 months after temp date of joining
	 */
	public static Date reminderDateTemp(Date dateOfJoining) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		calendar.add(Calendar.DATE, -2);
		logger.info(" minus 2 days" + calendar.getTime());
		return (calendar.getTime());
	}

	/**
	 * @author supriya.bhike
	 * @param dateOfJoining
	 * @return returns one days before temp date of joining
	 *         @
	 */
	public static Date reminderDateOneDayBeforeTemp(Date dateOfJoining) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		calendar.add(Calendar.DATE, -1);
		logger.info("minus 1  days" + calendar.getTime());
		return (calendar.getTime());
	}

	/**
	 * @author gurunath.rokkam
	 * @param startDate
	 * @param endDate
	 * @return Number of days between two dates. if any error occurs returns -1.
	 */
	public static int getMothsBetween(Date startDate, Date endDate) {
		try{
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			if (endDate.getTime() == startDate.getTime()) return 0;
			if (endDate.getTime() - startDate.getTime() > 0){
				start.setTime(startDate);
				end.setTime(endDate);
			} else{
				start.setTime(endDate);
				end.setTime(startDate);
			}
			int months = 0;
			if (start.get(Calendar.YEAR) != end.get(Calendar.YEAR)) months = (12 - start.get(Calendar.MONTH)) + (((end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) - 1) * 12) + end.get(Calendar.MONTH);
			else months = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
			if (endDate.getTime() - startDate.getTime() > 0) return months;
			return -months;
		} catch (Exception e){
			return -1;
		}
	}
	
	public static int getYearBetween(Date currentDate,Date empDate){ 
		
			long ms_per_day=1000 * 60 * 60 * 24; //milli seconda of a day
			int age=0;
			int age1=0;
			
			if(empDate!=null){				 	
		 					 	
		 	int totalDays=0;
		 	Date tempDate = new Date((currentDate).getTime() - (empDate).getTime());             
     		totalDays=Math.round((tempDate.getTime()/ ms_per_day) + 1); 
     		age=Math.round(totalDays/365);
     		age1 = totalDays;
     		}
     		/*SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
	      
	      return Integer.parseInt(simpleDateformat.format(currentDate))- Integer.parseInt(simpleDateformat.format(empDate));
     		 	*/
     		return age;
	    }

	public static String removeExtraChars(String value, int length) {
		if (value == null) return null;
		if (value.length() <= length) return value;
		return value.substring(0, length);
	}

	public static String getDepartment(int levelId) {
		try{
			DivisonDao divisionDao = DivisonDaoFactory.create();
			Levels level = LevelsDaoFactory.create().findByPrimaryKey(levelId);
			Divison division = divisionDao.findByPrimaryKey(level.getDivisionId());
			if (division != null){
				if (division.getParentId() != 0){
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) return division.getName();
				}
				return division.getName();
			}
		} catch (Exception e){}
		return "N.A";
	}

	public static String getyear(Date date) {
		if (date != null){
			return new SimpleDateFormat("yyyy").format(date);
		}
		return null;
	}
	
	public static String getMonthNumber(String monthName)
	{
		if(monthName.equalsIgnoreCase("Jan"))return "01";
		if(monthName.equalsIgnoreCase("Feb"))return "02";
		if(monthName.equalsIgnoreCase("Mar"))return "03";
		if(monthName.equalsIgnoreCase("Apr"))return "04";
		if(monthName.equalsIgnoreCase("May"))return "05";
		if(monthName.equalsIgnoreCase("Jun"))return "06";
		if(monthName.equalsIgnoreCase("Jul"))return "07";
		if(monthName.equalsIgnoreCase("Aug"))return "08";
		if(monthName.equalsIgnoreCase("Sep"))return "09";
		if(monthName.equalsIgnoreCase("Oct"))return "10";
		if(monthName.equalsIgnoreCase("Nov"))return "11";
		if(monthName.equalsIgnoreCase("Dec"))return "12";
		return " ";
	}
	//perdiem term for monthly

	public static String getFullMonthName(int month)
	{
		if(month==1) return "January";
		if(month==2) return "February";
		if(month==3) return "March";
		if(month==4) return "April";				
		if(month==5) return "May";
		if(month==6) return "June";
		if(month==7) return "July";
		if(month==8) return "August";				
		if(month==9) return "September";
		if(month==10) return "October";
		if(month==11) return "November";
		if(month==12) return "December";
		return null;				
					
	}
	
	
	
	
	public static String getDuration(Date oldDate, Date newDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		if (newDate.compareTo(oldDate) > 0){
			c1.setTime(oldDate);
			c2.setTime(newDate);
		} else{
			return "0-0-0";
		}
		int year = 0;
		int month = 0;
		int days = 0;
		boolean doneMonth = false;
		boolean doneYears = false;
		while (c1.before(c2)){
			if (!doneYears){
				c1.add(Calendar.YEAR, 1);
				year++;
			}
			if (c1.after(c2) || doneYears){
				if (!doneYears){
					doneYears = true;
					year--;
					c1.add(Calendar.YEAR, -1);
				}
				if (!doneMonth){
					c1.add(Calendar.MONTH, 1);
					month++;
				}
				if (c1.after(c2) || doneMonth){
					if (!doneMonth){
						doneMonth = true;
						month--;
						c1.add(Calendar.MONTH, -1);
					}
					c1.add(Calendar.DATE, 1);
					days++;
					if (c1.after(c2)){
						days--;
					}
					if (days == 31 || month == 12){
						break;
					}
				}
			}
		}
		return year + "-" + month + "-" + days;
	}

	public static String getTodayDate() {
		// TODO Auto-generated method stub
		String singleDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.DATE, 0);
		singleDate = sdf.format(c1.getTime());
		return singleDate;
	}
}
