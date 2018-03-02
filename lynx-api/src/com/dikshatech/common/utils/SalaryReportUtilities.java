package com.dikshatech.common.utils;

import java.util.Calendar;

public class SalaryReportUtilities {

	private static final String[]	monthNames	= { "", "January", "Febrauary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

	public static String getTextMonthFromId(String monthId) {
		String monthName = new String();
		if (monthId.length() == 6){
			monthName = monthId.substring(0, 4);
			monthName = monthNames[Integer.valueOf(monthId.substring(monthId.length() - 2, monthId.length()))] + " " + monthName;
		}
		return monthName;
	}

	public static String Decrypt(String salaryString) {
		return DesEncrypterDecrypter.getInstance().decrypt(salaryString);
	}

	public static float getNoOfDaysInAMonth(String monthId /*, int paidDays, float amount*/) {
		Calendar calendar = Calendar.getInstance();
		int year = Integer.valueOf(monthId.substring(0, 4));
		int month = Integer.valueOf(monthId.substring(monthId.length() - 2, monthId.length())) - 1;
		int date = 1;
		calendar.set(year, month, date);
		// float temp = (paidDays / calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		// return temp * amount;
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/*public static void main(String[] args) throws JRException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("MONTH_ID", "201304");
		JasperReport report = JasperCompileManager.compileReport("/opt/dev-env/praya-java/Jaspers/MonthlySalaryReport.jrxml");
		JasperPrint print = JasperFillManager.fillReport(report, params, MyDBConnect.getConnect());
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "/home/praneeth.r/Desktop.123.xls");
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, new ByteArrayOutputStream());
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.exportReport();
	}*/
}
