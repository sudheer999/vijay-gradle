package com.dikshatech.common.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import com.dikshatech.portal.models.SalaryDetailModel;

/**
 * @author anil.subudhi
 */
public class Converter {

	//	private static IntegerPerm		iPerm	= new IntegerPerm();
	SalaryDetailModel				salaryDetailModel	= new SalaryDetailModel();
	private static final String[]	tensNames			= { "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety" };
	private static final String[]	numNames			= { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen" };
	protected static final Logger	logger				= Logger.getLogger(Converter.class);
	/**
	 * This method returns salary in words for value less than thousands
	 * 
	 * @param less
	 *            than thousand salary in number
	 * @return salary in words
	 */
	private static String convertLessThanOneThousand(int number) {
		String soFar;
		if (number % 100 < 20){
			soFar = numNames[number % 100];
			number /= 100;
		} else{
			soFar = numNames[number % 10];
			number /= 10;
			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0) return soFar;
		return numNames[number] + " hundred" + soFar;
	}

	/**
	 * This method returns salary in words
	 * 
	 * @param number
	 *            string representation of salary in number
	 * @return salary in words
	 */
	public static String convert(String number) {
		//String numInt = DesEncrypterDecrypter.getInstance().decrypt(number);
		//number = numInt;
		if (number != null && number.equalsIgnoreCase("0")){
			return "zero";
		}
		String snumber = (number);
		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = number.substring(0, number.indexOf("."));
		int decimals = Integer.parseInt(number.substring(number.indexOf(".") + 1));
		snumber = df.format(Float.parseFloat(number));
		int billions = Integer.parseInt(snumber.substring(0, 3));
		int millions = Integer.parseInt(snumber.substring(3, 5));
		int lakhs = Integer.parseInt(snumber.substring(5, 7));
		int thousand = Integer.parseInt(snumber.substring(7, 9));
		int hundreads = Integer.parseInt(snumber.substring(9, 10));
		int tens = Integer.parseInt(snumber.substring(10, 12));
		String tradBillions;
		switch (billions) {
			case 0:
				tradBillions = "";
				break;
			case 1:
				tradBillions = convertLessThanOneThousand(billions) + " Billion ";
				break;
			default:
				tradBillions = convertLessThanOneThousand(billions) + " Billion ";
		}
		String result = tradBillions;
		String tradMillions;
		switch (millions) {
			case 0:
				tradMillions = "";
				break;
			case 1:
				tradMillions = convertLessThanOneThousand(millions) + " Million ";
				break;
			default:
				tradMillions = convertLessThanOneThousand(millions) + " Million ";
		}
		result = result + tradMillions;
		String mlakhs;
		switch (lakhs) {
			case 0:
				mlakhs = "";
				break;
			case 1:
				mlakhs = convertLessThanOneThousand(lakhs) + " Lakh ";
				break;
			default:
				mlakhs = convertLessThanOneThousand(lakhs) + " Lakh ";
		}
		result = result + mlakhs;
		String thousands;
		switch (thousand) {
			case 0:
				thousands = "";
				break;
			case 1:
				thousands = convertLessThanOneThousand(thousand) + " Thousand ";
				break;
			default:
				thousands = convertLessThanOneThousand(thousand) + " Thousand ";
		}
		result = result + thousands;
		String hundread;
		switch (hundreads) {
			case 0:
				hundread = "";
				break;
			case 1:
				hundread = convertLessThanOneThousand(hundreads) + " Hundred ";
				break;
			default:
				hundread = convertLessThanOneThousand(hundreads) + " Hundred ";
		}
		result = result + hundread;
		String ten;
		switch (tens) {
			case 0:
				ten = "";
				break;
			case 1:
				ten = convertLessThanOneThousand(tens);
				break;
			default:
				ten = convertLessThanOneThousand(tens);
		}
		result = result + ten;
		String decimal;
		switch (decimals) {
			case 0:
				decimal = "";
				break;
			case 1:
				decimal = convertLessThanOneThousand(decimals);
				break;
			default:
				decimal = convertLessThanOneThousand(decimals);
		}
		result = result + decimal;
		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

	/**
	 * Returns number representation of salary in proper perfect decimal fields included
	 * 
	 * @param x
	 *            string Encrypted form of value (Annual/monthly etc)
	 * @return Decrypeted form of integer with decimal points
	 */
	public static String returnNumber(String x) {
		String numInt = DesEncrypterDecrypter.getInstance().decrypt(x);
		String num = (numInt.substring(0, numInt.indexOf(".")));
		String decimal = numInt.substring(numInt.indexOf(".") + 1);
		try{
			if (num != null && num.equalsIgnoreCase("0")){
				return "0.00";
			}
			if (num.length() == 3){
				MaskFormatter format = new MaskFormatter("###");
				format.setValueContainsLiteralCharacters(false);
				return format.valueToString(num) + "." + decimal;
			}
			if (num.length() == 4){
				MaskFormatter format = new MaskFormatter("#,###");
				format.setValueContainsLiteralCharacters(false);
				return format.valueToString(num) + "." + decimal;
			}
			if (num.length() == 5){
				MaskFormatter format = new MaskFormatter("##,###");
				format.setValueContainsLiteralCharacters(false);
				return format.valueToString(num) + "." + decimal;
			} else if (num.length() == 6){
				MaskFormatter format = new MaskFormatter("#,##,###");
				format.setValueContainsLiteralCharacters(false);
				return format.valueToString(num) + "." + decimal;
			} else if (num.length() == 7){
				MaskFormatter format = new MaskFormatter("##,##,###");
				format.setValueContainsLiteralCharacters(false);
				return format.valueToString(num) + "." + decimal;
			} else if (num.length() == 8){
				MaskFormatter format = new MaskFormatter("#,##,##,###");
				format.setValueContainsLiteralCharacters(false);
				return format.valueToString(num) + "." + decimal;
			} else if (num.length() >= 9){
				return new java.text.DecimalFormat().format(Float.parseFloat((num)) + decimal);
			}
		} catch (ParseException e){
			logger.error("Can't parse the input value :" + e.getMessage());
			return "0.00";
		}
		return num;
	}

	/**
	 * Returns date strinf for MMMMMMMM dd yyyy
	 * 
	 * @param date
	 *            date of joining in Date
	 * @return string appending st/nd/rd/th representation to DD 21/10/1985 eg. 21st or 2/10/1985 --2nd
	 */
	public static String returnDate(Date date) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("MMMMMMMM dd yyyy");
		String splitdate = formatter.format(date);
		String[] dateArray = splitdate.split("\\s");
		StringBuffer output = new StringBuffer();
		output.append(dateArray[0] + " ");
		if (Character.getNumericValue((dateArray[1].charAt(1))) == 1 && Integer.parseInt(dateArray[1]) != 11){
			output.append(dateArray[1] + "<sup>st</sup> ");
		} else if (Character.getNumericValue((dateArray[1].charAt(1))) == 2 && Integer.parseInt(dateArray[1]) != 12){
			output.append(dateArray[1] + "<sup>nd</sup> ");
		} else if (Character.getNumericValue((dateArray[1].charAt(1))) == 3 && Integer.parseInt(dateArray[1]) != 13){
			output.append(dateArray[1] + "<sup>rd</sup> ");
		} else{
			output.append(dateArray[1] + "<sup>th</sup> ");
		}
		output.append(dateArray[2]);
		return output.toString();
	}

	/**
	 * Returns date strinf for dd MMMMMMMM yyyy
	 * 
	 * @param date
	 *            date of joining in Date
	 * @return string appending st/nd/rd/th representation to DD 21/10/1985 eg. 21st or 2/10/1985 --2nd
	 */
	public static String returnDateString(Date date) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd MMMMMMMM yyyy");
		String splitdate = formatter.format(date);
		String[] dateArray = splitdate.split("\\s");
		StringBuffer output = new StringBuffer();
		if (Character.getNumericValue((dateArray[0].charAt(1))) == 1 && Integer.parseInt(dateArray[0]) != 11){
			output.append(dateArray[0] + "<sup>st</sup> ");
		} else if (Character.getNumericValue((dateArray[0].charAt(1))) == 2 && Integer.parseInt(dateArray[0]) != 12){
			output.append(dateArray[0] + "<sup>nd</sup> ");
		} else if (Character.getNumericValue((dateArray[0].charAt(1))) == 3 && Integer.parseInt(dateArray[0]) != 13){
			output.append(dateArray[0] + "<sup>rd</sup> ");
		} else{
			output.append(dateArray[0] + "<sup>th</sup> ");
		}
		output.append(dateArray[1] + " " + dateArray[2]);
		return output.toString();
	}

	/**
	 * Return bold for field lables
	 * 
	 * @param data
	 *            field label from salary stack
	 * @return retuen them as bold depending on condition
	 */
	public static String returnFieldLebel(String data) {
		//logger.info("val------------>"+ numInt);
		if (data.equalsIgnoreCase("Fixed")){
			return "<b>Fixed</b>";
		} else if (data.equalsIgnoreCase("Flexi Benefit plan")){
			//logger.info("val------------>"+ numInt);
			return "<b>Flexi Benefit plan</b>";
		} else if (data.equalsIgnoreCase("Retirals")){
			return "<b>Retirals</b>";
		} else if (data.equalsIgnoreCase("Total Salary") || data.equalsIgnoreCase("Total")){
			return "<b>Total</b>";
		}
		return data;
	}

	/**
	 * Return bold for salary fields
	 * 
	 * @param data
	 *            field Label
	 * @param val
	 *            Encrypted value in string
	 * @return return bold fields label and value as with decimal points
	 */
	public static String returnSalary(String data, String val) {
		String numInt = DesEncrypterDecrypter.getInstance().decrypt(val);
		String decimal = "";
		String num = "";
		if (!numInt.contains(".")){
			num = numInt;
			decimal = "00";
		} else{
			num = (numInt.substring(0, numInt.indexOf(".")));
			decimal = numInt.substring(numInt.indexOf(".") + 1);
		}
		if (num != null && !(num.equalsIgnoreCase("0"))){
			DecimalFormat df = new DecimalFormat();
			
			if (data.equalsIgnoreCase("TOTAL") || data.equalsIgnoreCase("Total Salary")){
				return "<b>" + df.format(Float.parseFloat(num)) + "." + decimal + "</b>";
			}
			return df.format(Float.parseFloat(num)) + "." + decimal;
		}
		
		
		return "";
	}

	public static float getComponentAmount(String amount)
	{
		float componentAmount=0.0f;
		try
		{
			componentAmount=Float.valueOf(DesEncrypterDecrypter.getInstance().decrypt(amount));
		}catch (Exception ex) {
			LoggerUtil.getLogger().error("Unable to obtain component value while generating offer letter." + ex.getMessage());
			ex.printStackTrace();
		}
		return(componentAmount);
	}
	
	/**
	 * RETURN PERDIEM PER DAY
	 * 
	 * @param data
	 *            Field Label
	 * @param val
	 *            value with decimal
	 * @return return perdiem calculation per day i.e /365
	 */
	public static String returnPerdiemPerDay(String data, String val) {
		String numInt = DesEncrypterDecrypter.getInstance().decrypt(data);
		String decimal = "";
		String num = "";
		if (!numInt.contains(".")){
			num = numInt;
			decimal = "00";
		} else{
			num = (numInt.substring(0, numInt.indexOf(".")));
			decimal = numInt.substring(numInt.indexOf(".") + 1);
		}
		if (num != null && !(num.equalsIgnoreCase("0"))){
			DecimalFormat df = new DecimalFormat();
			if (val.equalsIgnoreCase("2_1")){
				return df.format(Float.parseFloat(num) / 365) + "." + decimal;
			}
			return df.format(Float.parseFloat(num)) + "." + decimal;
		}
		return "";
	}

	public static Object checkDateNull(Date date) {
		if (date != null){
			return date;
		}
		return "";
	}

	/*public static void main(String[] args) {
		
		 * logger.info("Enter Number is :5123347 and its conversion =" +
		 * Converter.convert(311825));
		 * logger.info("Enter Number is :1452840 and its conversion =" +
		 * Converter.returnNumber(311825)); logger.info("Date =" +
		 * Converter.returnDate(new Date())); logger.info("Date =" +
		 * Converter.returnDateString(new Date()));
		 
		final String driverClass = "com.mysql.jdbc.Driver";
		final String connectionURL = "jdbc:mysql://localhost:3306/DIKSHA_PORTAL_2";
		final String userID = "root";
		final String userPassword = "suresh";
		Connection con = null;
		try{
			Class.forName(driverClass).newInstance();
			con = DriverManager.getConnection(connectionURL, userID, userPassword);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT FIELD_LABEL,MONTHLY, ANNUAL, SUM FROM SALARY_DETAILS WHERE USER_ID = 2");
			while (rs.next()){
				String num = rs.getString(2);
				if (num != null && num.equalsIgnoreCase("0")){
					logger.info("salary " + rs.getString(1) + " : " + 0);
				} else{
					if (num == null){
						logger.info("salary " + rs.getString(1) + " : " + 0);
					} else{
						String numInt = DesEncrypterDecrypter.getInstance().decrypt(num);
						logger.info("salary " + rs.getString(1) + " : " + numInt);
					}
				}
				// logger.info(returnSalary(rs.getString(1), num));
			}
		} catch (InstantiationException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(convert("123654.00"));
	}*/
	
	public static String getMonthNameFromMonthId(String monthId){
		String monthName="";
		if(monthId!=null && monthId.length()==6)
		{
			String year=monthId.substring(0,4);
			String month=monthId.substring(4);
			Calendar calendar=Calendar.getInstance();
			calendar.set(Calendar.YEAR,Integer.parseInt(year));
			calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			
			month=calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT,Locale.ENGLISH );
			
			month=month.concat("-").concat(year);
			
			monthName=month;
		}else if(monthId!=null && monthId.length()>6){
			String[] fisicalYears=monthId.split("-");
			String year=fisicalYears.length>0?fisicalYears[0]:String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			Calendar calendar=Calendar.getInstance();
			calendar.set(Calendar.YEAR,Integer.parseInt(year));
			calendar.set(Calendar.MONTH, Calendar.MARCH);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			monthName=calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT,Locale.ENGLISH );
			monthName=monthName.concat("-").concat(year);
		}
		return(monthName);
	}
}
