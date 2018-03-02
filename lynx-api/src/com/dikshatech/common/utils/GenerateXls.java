package com.dikshatech.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.cf.BorderFormatting;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.CellRangeAddress;
//import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.dikshatech.beans.AppraisalReport;
import com.dikshatech.beans.AppraisalReportV1;
import com.dikshatech.beans.PerdiemReportBean;
import com.dikshatech.beans.SalaryReportBean;
import com.dikshatech.beans.TimesheetDelayedBean;
import com.dikshatech.portal.dto.LeaveBalance;
import com.dikshatech.portal.dto.RmgTimeSheet;
import com.dikshatech.portal.dto.SalaryReconciliation;
import com.dikshatech.portal.exceptions.SalaryReconciliationDaoException;
import com.dikshatech.portal.factory.SalaryReconciliationDaoFactory;
import com.dikshatech.portal.models.AppraisalModel;

/**
 * @author gurunath.rokkam
 */
public class GenerateXls {

	private static Logger	logger	= LoggerUtil.getLogger(AppraisalModel.class);

	public String generateXlsHRDReport(AppraisalReport[] setAppraisalReports, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		int i = 0;
		int j = 1;
		try{
			/**
			 * Create a new instance for HSSFWorkBook class and create a
			 * sample work-sheet using HSSFSheet class to write data.
			 */
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("APPRAISAL HRD REPORTS");
			int columnWidth[] = new int[] { 2500, 6000, 8000, 8000, 5000, 5000, 5000, 5000, 20000, 20000, 4000, 4000, 5000, 5000, 4000, 20000 };
			for (int width : columnWidth)
				sampleDataSheet.setColumnWidth(i++, width);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			/**
			 * Call the setHeaderStyle method and set the styles for the
			 * all the three header cells.
			 */
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			String columns[] = new String[] { "SL NO", "NAME", "Designation", "New Designation", "Professional\n Competencies\n rating", "Personal\n Competencies\n rating", "Over all Score", "Total Number\n of days on\n project", "IDP (From Appraisee)", "FeedBack (Appraiser FB in IDP)", "Promotability", "How Soon", "Promoted\n as", "Appraiser", "Completion date", "Suggestions/ Recommendation/ Comments" };
			headerRow.setHeightInPoints(50);
			i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle nameStyle = setNameStyle(sampleWorkbook, false);
			/**
			 * Set the cell value for all the data rows.
			 */
			for (AppraisalReport report : setAppraisalReports){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(250);
				HSSFCell dataCell1 = dataRow.createCell(i++);
				dataCell1.setCellStyle(cellStyle);
				dataCell1.setCellValue(report.getId());
				HSSFCell dataCell2 = dataRow.createCell(i++);
				dataCell2.setCellStyle(nameStyle);
				dataCell2.setCellValue(report.getName());
				HSSFCell dataCell3 = dataRow.createCell(i++);
				dataCell3.setCellStyle(nameStyle);
				dataCell3.setCellValue(report.getDesignation());
				HSSFCell dataCell4 = dataRow.createCell(i++);
				dataCell4.setCellStyle(nameStyle);
				dataCell4.setCellValue(report.getNewDesignation());
				HSSFCell dataCell5 = dataRow.createCell(i++);
				dataCell5.setCellStyle(cellStyle);
				dataCell5.setCellValue(report.getProfessionalRating());
				HSSFCell dataCell6 = dataRow.createCell(i++);
				dataCell6.setCellStyle(cellStyle);
				dataCell6.setCellValue(report.getPersonalRating());
				HSSFCell dataCell7 = dataRow.createCell(i++);
				dataCell7.setCellStyle(cellStyle);
				dataCell7.setCellValue(report.getOverallScore());
				HSSFCell dataCell8 = dataRow.createCell(i++);
				dataCell8.setCellStyle(cellStyle);
				dataCell8.setCellValue(report.getTotalProjectDays());
				HSSFCell dataCell9 = dataRow.createCell(i++);
				dataCell9.setCellStyle(commStyle);
				dataCell9.setCellValue(report.getAppraiseeComments());
				HSSFCell dataCell10 = dataRow.createCell(i++);
				dataCell10.setCellStyle(commStyle);
				dataCell10.setCellValue(report.getAppraiserComments());
				HSSFCell dataCell11 = dataRow.createCell(i++);
				dataCell11.setCellStyle(cellStyle);
				dataCell11.setCellValue(report.getPromotability());
				HSSFCell dataCell12 = dataRow.createCell(i++);
				dataCell12.setCellStyle(cellStyle);
				dataCell12.setCellValue(report.getHowSoon());
				HSSFCell dataCell13 = dataRow.createCell(i++);
				dataCell13.setCellStyle(cellStyle);
				dataCell13.setCellValue(report.getPromotedas());
				HSSFCell dataCell14 = dataRow.createCell(i++);
				dataCell14.setCellStyle(cellStyle);
				dataCell14.setCellValue(report.getAppraiser());
				HSSFCell dataCell15 = dataRow.createCell(i++);
				dataCell15.setCellStyle(cellStyle);
				dataCell15.setCellValue(report.getCompletionDate());
				HSSFCell dataCell16 = dataRow.createCell(i++);
				dataCell16.setCellStyle(commStyle);
				dataCell16.setCellValue(report.getSuggestions());
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}
	
	
	//Hyper Link method
	
	
	private HSSFCellStyle hyperlink(HSSFWorkbook sampleWorkBook, boolean b) {
		HSSFFont font = sampleWorkBook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		if (b){
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.BLUE.index);
		  }
		HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		if (b){
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderTop(BorderFormatting.BORDER_THICK);
			cellStyle.setBorderLeft(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderRight(BorderFormatting.BORDER_THIN);
			
			
		}
		return cellStyle;
	}
	
	

	private HSSFCellStyle setHeaderStyleHyperLink(HSSFWorkbook sampleWorkBook, boolean b) {
		HSSFFont font = sampleWorkBook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		if (b){
		
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setUnderline(HSSFFont.U_SINGLE);
			font.setColor(HSSFColor.BLUE.index);
		}
		HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		if (b){
		
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
		
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(BorderFormatting.BORDER_THICK);
			cellStyle.setBorderTop(BorderFormatting.BORDER_THICK);
			cellStyle.setBorderLeft(BorderFormatting.BORDER_THICK);
			cellStyle.setBorderRight(BorderFormatting.BORDER_THICK);
		}
		return cellStyle;
	}
	
	
	
	private HSSFCellStyle setHeaderStyle1(HSSFWorkbook sampleWorkBook, boolean b) {
		HSSFFont font = sampleWorkBook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		if (b){
		
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.BLACK.index);
		}
		HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		if (b){
			cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderTop(BorderFormatting.BORDER_THICK);
			cellStyle.setBorderLeft(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderRight(BorderFormatting.BORDER_THIN);
		}
		return cellStyle;
	}
	
	private HSSFCellStyle setHeaderBlank(HSSFWorkbook sampleWorkBook, boolean b) {
		HSSFFont font = sampleWorkBook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		if (b){
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.BLACK.index);
		}
		HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		if (b){
			cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderTop(BorderFormatting.BORDER_THICK);
			cellStyle.setBorderLeft(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderRight(BorderFormatting.BORDER_THIN);
		}
		return cellStyle;
	}

	private HSSFCellStyle setHeaderStyle(HSSFWorkbook sampleWorkBook, boolean b) {
		HSSFFont font = sampleWorkBook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		if (b){
			//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.BLACK.index);
		}
		HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		if (b){
			cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderTop(BorderFormatting.BORDER_THICK);
			cellStyle.setBorderLeft(BorderFormatting.BORDER_THIN);
			cellStyle.setBorderRight(BorderFormatting.BORDER_THIN);
		}
		return cellStyle;
	}

	private HSSFCellStyle setSumStyle(HSSFWorkbook sampleWorkBook) {
		HSSFFont font = sampleWorkBook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		cellStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderFormatting.BORDER_THIN);
		cellStyle.setBorderTop(BorderFormatting.BORDER_THIN);
		cellStyle.setBorderLeft(BorderFormatting.BORDER_DASHED);
		cellStyle.setBorderRight(BorderFormatting.BORDER_DOTTED);
		return cellStyle;
	}

	private HSSFCellStyle setCommentsStyle(HSSFWorkbook sampleWorkBook, boolean b) {
		HSSFCellStyle hs = setHeaderStyle(sampleWorkBook, b);
		hs.setAlignment(CellStyle.ALIGN_LEFT);
		hs.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		return hs;
	}

	private HSSFCellStyle setNameStyle(HSSFWorkbook sampleWorkBook, boolean b) {
		HSSFCellStyle hs = setHeaderStyle(sampleWorkBook, b);
		hs.setAlignment(CellStyle.ALIGN_LEFT);
		return hs;
	}

	public String generateXlsHRReport(String[][] appraisalReports, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("APPRAISAL HR REPORTS");
			String columns[] = new String[] { "SL NO", "Employee Name", "Company policies are well defined and communicated effectively", "Avenues are available to express my views and opinions on issues affecting me and organization", "HRD facilitates support for additional training and education", "There are adequate opportunities to keep up my good work", "Opportunities available for personal development/advancement ", "Salaries and perdiems are paid in a timely manner",
					"Reimbursement of mediclaims/travel claims are received in a timely manner.", "Clarity on procedures involved in applying for leave", "Clarity on procedures involved in applying for various benefits like Laptop/ Personal Loan.", "Performance Appraisals", "Birthday/Job Anniversary wishes", "Availability", "addresses queries and concerns in a reasonable amount of time", "sends updates and reminders regularly (Eg Timesheets)",
					"assists with timely arrangements of travel and accommodation arrangements", "is courteous and responds appropriately", "usefulness of this unique HR initiative", "comments and Suggestions" };
			sampleDataSheet.setColumnWidth(0, 3000);
			int k = 1;
			for (; k < columns.length - 1; k++)
				sampleDataSheet.setColumnWidth(k, 6000);
			sampleDataSheet.setColumnWidth(k, 20000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(70);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			int j = 1;
			for (String[] reports : appraisalReports){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(60);
				HSSFCell dataCell = null;
				for (String report : reports){
					dataCell = dataRow.createCell(i++);
					if (i == 20) dataCell.setCellStyle(commStyle);
					else dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(report);
				}
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	private String getMonthId(int sr_id) {
		String monthId = "";
		try{
			SalaryReconciliation sr = SalaryReconciliationDaoFactory.create().findByPrimaryKey(sr_id);
			int month = sr.getMonth();
			String strMonth = "" + month;
			if (month < 10 && month > 0) strMonth = "0" + month;
			monthId = String.valueOf(sr.getYear()) + strMonth;
		} catch (SalaryReconciliationDaoException e){
			e.printStackTrace();
		}
		return monthId;
	}

	public String generateSalaryICICIReport(SalaryReportBean[] salaryDetails, String path, int sr_id) {
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;
		String monthId = getMonthId(sr_id);
		String fileName = "Salary ICICI REPORT " + SalaryReportUtilities.getTextMonthFromId(monthId) + ".xls";
		path += File.separator + fileName;
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("ICICI REPORT");
			String columns[] = new String[] { "NAME", "ACCOUNT_NO", "CUR_CODE", "CR_DR", " TRAN_AMT ", "TRAN_PART" };
			sampleDataSheet.setColumnWidth(0, 8000);
			sampleDataSheet.setColumnWidth(1, 5000);
			sampleDataSheet.setColumnWidth(2, 2000);
			sampleDataSheet.setColumnWidth(3, 2000);
			sampleDataSheet.setColumnWidth(4, 4000);
			sampleDataSheet.setColumnWidth(5, 5500);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(20);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 1;
			if (salaryDetails != null && salaryDetails.length > 0){
				for (SalaryReportBean eachSalaryBean : salaryDetails){
					HSSFRow dataRow = sampleDataSheet.createRow(j++);
					i = 0;
					dataRow.setHeightInPoints(13);
					/*HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(++k);*/
					HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getName());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getAccount_no());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getCur_code());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getCr_dr());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					dataCell.setCellStyle(cellStyle1);
					dataCell.setCellValue(eachSalaryBean.getTran_amt() != null ? Double.valueOf(eachSalaryBean.getTran_amt()) : 0);
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getTran_part());
				}
			}
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(4, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(E" + 2 + ":E" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			FileOutputStream fileOutputStream = new FileOutputStream(path, true);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			success = true;
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
		} catch (IOException e){
			logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
		}
		if (success) return fileName;
		else return "error";
	}

	public String generateSalaryOtherReport(SalaryReportBean[] salaryDetails, String path, int sr_id) {
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;
		String monthId = getMonthId(sr_id);
		String fileName = "Salary OTHER REPORT " + SalaryReportUtilities.getTextMonthFromId(monthId) + ".xls";
		path += File.separator + fileName;
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("OTHER REPORT");
			String columns[] = new String[] { "NAME", "ACCOUNT_NO", "CUR_CODE", "CR_DR", " TRAN_AMT ", "TRAN_PART", "BANK NAME" };
			sampleDataSheet.setColumnWidth(0, 8000);
			sampleDataSheet.setColumnWidth(1, 4000);
			sampleDataSheet.setColumnWidth(2, 2500);
			sampleDataSheet.setColumnWidth(3, 2500);
			sampleDataSheet.setColumnWidth(4, 5000);
			sampleDataSheet.setColumnWidth(5, 6000);
			sampleDataSheet.setColumnWidth(6, 5000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(20);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 1;
			if (salaryDetails != null && salaryDetails.length > 0){
				for (SalaryReportBean eachSalaryBean : salaryDetails){
					HSSFRow dataRow = sampleDataSheet.createRow(j++);
					i = 0;
					dataRow.setHeightInPoints(13);
					/*HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(++k);*/
					HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getName());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getAccount_no());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getCur_code());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getCr_dr());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					dataCell.setCellStyle(cellStyle1);
					String amount = eachSalaryBean.getTran_amt();
					dataCell.setCellValue(amount != null ? Double.valueOf(amount) : 0);
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getTran_part());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getBankName());
				}
			}
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(4, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(E" + 2 + ":E" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			FileOutputStream fileOutputStream = new FileOutputStream(path, true);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			success = true;
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
		} catch (IOException e){
			logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
		}
		if (success) return fileName;
		else return "error";
	}

	public String generateSalaryReport(SalaryReportBean[] salaryDetails, String path, int sr_id) {
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;
		String monthId = getMonthId(sr_id);
		String fileName = "Salary Internal Report " + SalaryReportUtilities.getTextMonthFromId(monthId) + ".xls";
		path += File.separator + fileName;
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("INTERNAL REPORT");
			String columns[] = new String[] { "EMP ID", "NAME", "PAYABLE DAYS", " AMOUNT ", "ACCOUNT NO", "BANK NAME" };
			int i = 0;
			sampleDataSheet.setColumnWidth(i++, 2000);
			sampleDataSheet.setColumnWidth(i++, 8000);
			sampleDataSheet.setColumnWidth(i++, 3000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 6000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(20);
			i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 1;
			if (salaryDetails != null && salaryDetails.length > 0){
				for (SalaryReportBean eachSalaryBean : salaryDetails){
					HSSFRow dataRow = sampleDataSheet.createRow(j++);
					i = 0;
					dataRow.setHeightInPoints(13);
					HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getEmp_code());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getName());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getPayableDays());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					dataCell.setCellStyle(cellStyle1);
					String amount = eachSalaryBean.getTran_amt();
					dataCell.setCellValue(amount != null ? Double.valueOf(amount) : 0);
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getAccount_no());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getBankName());
				}
			}
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			FileOutputStream fileOutputStream = new FileOutputStream(path, true);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			success = true;
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
		} catch (IOException e){
			logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
		}
		if (success) return fileName;
		else return "error";
	}
	
	/*public String generateBankSalaryReport(SalaryReportBean[] salaryDetails, String path, int sr_id) {
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		HSSFSheet sampleDataSheet1 = null;
		boolean success = false;
		String monthId = getMonthId(sr_id);
		String fileName = "Salary Internal Report " + SalaryReportUtilities.getTextMonthFromId(monthId) + ".xls";
		path += File.separator + fileName;//?
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("INTERNAL REPORT");
			 sampleDataSheet1 = sampleWorkbook.createSheet("sheet1");
		
			String columns[] = new String[] { "Transaction Type NEFT/RT GS \n A \n 1 \nMandatory \n N for NEFT \n   R for RTGS", "BLANK", "Bnificiary Account number\n A\n 25\n Mandatory\n 9999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n Mandatory\n Shah Enterprices PVT LTD","BLANk","BLANK","Bene Address 1\n A\n 70\n Optional\n 26A properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n pIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional\n","Payment details 3\n C\n 30\n Optional\n","Payment details 4\n C\n 30\n Optional\n","Payment details 5\n C\n 30\n Optional\n","Payment details 6\n C\n 30\n Optional\n","Payment details 7\n C\n 30\n Optional\n","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","Blank","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of INDIA","Bene Bank Branch Name\n A\n 40\n Mandatory\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email Id" };
			String columns1[] = new String[] { "Transaction  NEFT/RT GS \n A \n 1 \nMandatory \n N for NEFT \n   R for RTGS", "BLANK", "Bnificiary Account number\n A\n 25\n Mandatory\n 9999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n Mandatory\n Shah Enterprices PVT LTD","BLANk","BLANK","Bene Address 1\n A\n 70\n Optional\n 26A properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n pIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional\n","Payment details 3\n C\n 30\n Optional\n","Payment details 4\n C\n 30\n Optional\n","Payment details 5\n C\n 30\n Optional\n","Payment details 6\n C\n 30\n Optional\n","Payment details 7\n C\n 30\n Optional\n","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","Blank","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of INDIA","Bene Bank Branch Name\n A\n 40\n Mandatory\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email Id" };

			for(int i=0;i<=columns1.length-1;i++){
				sampleDataSheet1.setColumnWidth(i,8000);
			}
			
			HSSFRow headerRow1 = sampleDataSheet1.createRow(0);
			HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
			headerRow1.setHeightInPoints(90);
			int k=0;
			for (String columnName : columns1){
				HSSFCell headerCell1 = headerRow1.createCell(k++);
				headerCell1.setCellStyle(headerStyle1);
				headerCell1.setCellValue(new HSSFRichTextString(columnName));
			}
			
			
			
			
			
			//sheet1
			
			int i = 0;
			sampleDataSheet.setColumnWidth(i++, 4000);
			sampleDataSheet.setColumnWidth(i++, 3000);
			sampleDataSheet.setColumnWidth(i++, 5500);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 8000);
			sampleDataSheet.setColumnWidth(i++, 3000);
			sampleDataSheet.setColumnWidth(i++, 3000);//g
			
			sampleDataSheet.setColumnWidth(i++, 8000);
			sampleDataSheet.setColumnWidth(i++, 8000);
			sampleDataSheet.setColumnWidth(i++, 8000);
			sampleDataSheet.setColumnWidth(i++, 8000);
			sampleDataSheet.setColumnWidth(i++, 8000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);//n
			
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);
			sampleDataSheet.setColumnWidth(i++, 5000);//u
			
			sampleDataSheet.setColumnWidth(i++, 2000);
			sampleDataSheet.setColumnWidth(i++, 3000);
			sampleDataSheet.setColumnWidth(i++, 2000);
			sampleDataSheet.setColumnWidth(i++, 5000);//y
			
			sampleDataSheet.setColumnWidth(i++, 9000);//z
			
	        sampleDataSheet.setColumnWidth(i++, 6000);//y
			
			sampleDataSheet.setColumnWidth(i++, 9000);//z
			
			
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(90);
			
			CellRangeAddressList addressList = new CellRangeAddressList(0, 5, 0, 0);
			 DVConstraint dvConstraint = DVConstraint
		                .createExplicitListConstraint(new String[] { "N", "R" });
			 HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,
		                dvConstraint);
			 dataValidation.setSuppressDropDownArrow(false);
			 sampleDataSheet.addValidationData(dataValidation);
			
			i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			int j=1;
			for (String columnName : columns1){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i=0;
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
		
			//int j = 1;
			if (salaryDetails != null && salaryDetails.length > 0){
				for (SalaryReportBean eachSalaryBean : salaryDetails){
					HSSFRow dataRow = sampleDataSheet.createRow(j++);
					i = 0;
					dataRow.setHeightInPoints(13);
					
					HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue("");
					
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue("blank");
					
					
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getAccount_no());
					
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					dataCell.setCellStyle(cellStyle1);
					String amount = eachSalaryBean.getTran_amt();
					dataCell.setCellValue(amount != null ? Double.valueOf(amount) : 0);
					
					
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getName());
					
					dataCell = dataRow.createCell(26, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getBankName());
				}
			}
			
			
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			
			
			FileOutputStream fileOutputStream = new FileOutputStream(path, true);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			success = true;
			
			
			
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
		} catch (IOException e){
			logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
		}
		if (success) return fileName;
		else return "error";
	}*/
	
	public String generateBankSalaryReportNonHdfc(SalaryReportBean[] salaryDetails, String path, int sr_id,String flag) {
		
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;
		String monthId = getMonthId(sr_id);
		String fileName = "Salary Internal Report " + SalaryReportUtilities.getTextMonthFromId(monthId) + ".xls";
		path += File.separator + fileName;//?
		File file = new File(path);
		if (file.exists()) file.delete();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   //get current date time with Date()
		   Date date = new Date();
		   logger.debug("system date for excel sheet"+dateFormat.format(date));
		   
		
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE PRINT");
			HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		
			
			String columns[] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments"};
			String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments"};
			String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory"};
            String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments "};
			
            
            // sheet 5
			for(int u=0;u<columns2.length;u++){
				SEFTEFT.setColumnWidth(u, 4000);}
			
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				headerRow5.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList5 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint5 = DVConstraint
			                .createExplicitListConstraint(new String[] { "S" });
				 HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,
			                dvConstraint5);
				 dataValidation5.setSuppressDropDownArrow(false);
				 SEFTEFT.addValidationData(dataValidation5);
				 
				int u1 = 0;
				for (String columnNames : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(u1++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 4
			
			for(int t=0;t<columns2.length;t++){
				PAYORDERPRINT.setColumnWidth(t, 4000);}
			
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				headerRow4.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList4 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint4 = DVConstraint
			                .createExplicitListConstraint(new String[] { "H" });
				 HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,
			                dvConstraint4);
				 dataValidation4.setSuppressDropDownArrow(false);
				 PAYORDERPRINT.addValidationData(dataValidation4);
				 
				int t1 = 0;
				for (String columnNames : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(t1++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 3
			for(int p=0;p<columns2.length;p++){
				DRAFTPRINT.setColumnWidth(p, 4000);}
			
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				headerRow3.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList3 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint3 = DVConstraint
			                .createExplicitListConstraint(new String[] { "D" });
				 HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,
			                dvConstraint3);
				 dataValidation3.setSuppressDropDownArrow(false);
				 DRAFTPRINT.addValidationData(dataValidation3);
				 
				int p1 = 0;
				for (String columnNames : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(p1++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 2
			for(int q=0;q<columns2.length;q++){
				CHEQUEPRINT.setColumnWidth(q, 4000);
				}
			
				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				headerRow2.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList2 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint2 = DVConstraint
			                .createExplicitListConstraint(new String[] { "C" });
				 HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,
			                dvConstraint2);
				 dataValidation2.setSuppressDropDownArrow(false);
				 CHEQUEPRINT.addValidationData(dataValidation2);
				 
				int q1 = 0;
				for (String columnNames : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(q1++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnNames));
				}
				
			
				
					//new SET COLUMN WIDTHS
					for(int l=0;l<columnsz.length;l++){
						FUNDTRANSFER.setColumnWidth(l, 4000);}
				
					HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
					HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
					HSSFCellStyle headerBlank = setHeaderBlank(sampleWorkbook, true);
					headerRow1.setHeightInPoints(120);
					
					// For add dropdown menu in xls
					CellRangeAddressList addressList = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint = DVConstraint
				                .createExplicitListConstraint(new String[] { "I"});
					 HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,
				                dvConstraint);
					 dataValidation.setSuppressDropDownArrow(false);
					 FUNDTRANSFER.addValidationData(dataValidation);
					int l1 = 0;
				    for (String columnNames : columnsz){
						HSSFCell headerCell1 = headerRow1.createCell(l1++);
						headerCell1.setCellStyle(headerStyle1);
						headerCell1.setCellValue(new HSSFRichTextString(columnNames));
						
						String s=headerCell1.getStringCellValue();
		                   
	                    //TO BOLD the BLANK field
	                   
	                    if(s.equals("BLANK")){
	                        headerCell1.setCellStyle(headerBlank);
	                        headerCell1.setCellValue(new HSSFRichTextString(columnNames));
	                        //setHeaderStyle1
	                    }
						
						
						
					}
					int i = 0;
					sampleDataSheet.setColumnWidth(i++, 4000);
					sampleDataSheet.setColumnWidth(i++, 3000);
					sampleDataSheet.setColumnWidth(i++, 5500);
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 8000);
					sampleDataSheet.setColumnWidth(i++, 3000);
					sampleDataSheet.setColumnWidth(i++, 3000);//g
					
					sampleDataSheet.setColumnWidth(i++, 8000);
					sampleDataSheet.setColumnWidth(i++, 8000);
					sampleDataSheet.setColumnWidth(i++, 8000);
					sampleDataSheet.setColumnWidth(i++, 8000);
					sampleDataSheet.setColumnWidth(i++, 8000);
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 5000);//n
					
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 5000);
					sampleDataSheet.setColumnWidth(i++, 5000);//u
					
					sampleDataSheet.setColumnWidth(i++, 2000);
					sampleDataSheet.setColumnWidth(i++, 3000);
					sampleDataSheet.setColumnWidth(i++, 2000);
					sampleDataSheet.setColumnWidth(i++, 5000);//y
					
					sampleDataSheet.setColumnWidth(i++, 9000);//z
					
			        sampleDataSheet.setColumnWidth(i++, 6000);//y
					
					sampleDataSheet.setColumnWidth(i++, 9000);//z
					
					
					HSSFRow headerRow = sampleDataSheet.createRow(0);
					HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
					headerRow.setHeightInPoints(90);
					
					CellRangeAddressList addressList1 = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint1 = DVConstraint
				                .createExplicitListConstraint(new String[] { "N", "R" });
					 HSSFDataValidation dataValidation1= new HSSFDataValidation(addressList1,
				                dvConstraint1);
					 dataValidation1.setSuppressDropDownArrow(false);
					 sampleDataSheet.addValidationData(dataValidation1);
					i = 0;
					for (String columnName : columns){
						HSSFCell headerCell = headerRow.createCell(i++);
						headerCell.setCellStyle(headerStyle);
						headerCell.setCellValue(new HSSFRichTextString(columnName));
					}
				
					
					/**
					 * Set the cell value for all the data rows.
					 */
					HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
					HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
					cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
					cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
				
					
					int j = 1;
					if (salaryDetails != null && salaryDetails.length > 0){
						for (SalaryReportBean eachSalaryBean : salaryDetails){
							HSSFRow dataRow = sampleDataSheet.createRow(j++);
							i = 0;
							dataRow.setHeightInPoints(13);
							
							HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("N");
							
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getEmp_code());
							
							
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getAccount_no());
							
						/*	dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
							cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
							dataCell.setCellStyle(cellStyle1);
							String amount = eachSalaryBean.getTran_amt();
							dataCell.setCellValue(amount != null ? Double.valueOf(amount) : 0);
							*/
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getTran_amt());
							
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getName());
							
							
							
							dataCell = dataRow.createCell(7);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("Bangalore");
						
							dataCell = dataRow.createCell(8);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("Bangalore");
						
							dataCell = dataRow.createCell(9);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("Bangalore");
						
							dataCell = dataRow.createCell(10);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("Bangalore");
						
							dataCell = dataRow.createCell(11);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("560080");
						
							dataCell = dataRow.createCell(12);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("Salary");
						
							dataCell = dataRow.createCell(13);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("Salary");
						
							dataCell = dataRow.createCell(14);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("Salary");
						
							dataCell = dataRow.createCell(22, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(dateFormat.format(date));
						
							dataCell = dataRow.createCell(24, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getPrimaryIfsc());
						
							dataCell = dataRow.createCell(25, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getBankName());
						
						
						
							dataCell = dataRow.createCell(27, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getEmail_id());
						
						}
					}
						int formEnd = j;
						j++;
						HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
						HSSFRow sumRow = sampleDataSheet.createRow(j++);
						/*sumRow.setHeightInPoints(20);
						HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
						cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
				//		cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
						cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						sumCell.setCellStyle(cellStyle5);
				//		sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
*/						HSSFPalette palette = sampleWorkbook.getCustomPalette();
						palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
					
					FileOutputStream fileOutputStream = new FileOutputStream(path, true);
				sampleWorkbook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
				success = true;
				
				
				
			} catch (FileNotFoundException e){
				logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
			} catch (IOException e){
				logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
			}
			if (success) return fileName;
			else return "error";
		}
	
	/*generateBankSalaryReport*/
public String generateBankSalaryReportHdfc(SalaryReportBean[] salaryDetails, String path, int sr_id,String flag) {
		
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;
		String monthId = getMonthId(sr_id);
		String fileName = "Salary Internal Report " + SalaryReportUtilities.getTextMonthFromId(monthId) + ".xls";
		path += File.separator + fileName;//?
		File file = new File(path);
		if (file.exists()) file.delete();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   //get current date time with Date()
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
		
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE PRINT");
			HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		
			
			
			String columns[] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments"};
			String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments"};
			String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory"};
            String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments "};
			
			// sheet 5
			for(int u=0;u<columns2.length;u++){
				SEFTEFT.setColumnWidth(u, 4000);}
			
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				headerRow5.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList5 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint5 = DVConstraint
			                .createExplicitListConstraint(new String[] { "S" });
				 HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,
			                dvConstraint5);
				 dataValidation5.setSuppressDropDownArrow(false);
				 SEFTEFT.addValidationData(dataValidation5);
				 
				int u1 = 0;
				for (String columnNames : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(u1++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 4
			
			for(int t=0;t<columns2.length;t++){
				PAYORDERPRINT.setColumnWidth(t, 4000);}
			
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				headerRow4.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList4 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint4 = DVConstraint
			                .createExplicitListConstraint(new String[] { "H" });
				 HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,
			                dvConstraint4);
				 dataValidation4.setSuppressDropDownArrow(false);
				 PAYORDERPRINT.addValidationData(dataValidation4);
				 
				int t1 = 0;
				for (String columnNames : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(t1++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 3
			for(int p=0;p<columns2.length;p++){
				DRAFTPRINT.setColumnWidth(p, 4000);}
			
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				headerRow3.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList3 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint3 = DVConstraint
			                .createExplicitListConstraint(new String[] { "D" });
				 HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,
			                dvConstraint3);
				 dataValidation3.setSuppressDropDownArrow(false);
				 DRAFTPRINT.addValidationData(dataValidation3);
				 
				int p1 = 0;
				for (String columnNames : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(p1++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 2
			for(int q=0;q<columns2.length;q++){
				CHEQUEPRINT.setColumnWidth(q, 4000);
				}
			
				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				headerRow2.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList2 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint2 = DVConstraint
			                .createExplicitListConstraint(new String[] { "C" });
				 HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,
			                dvConstraint2);
				 dataValidation2.setSuppressDropDownArrow(false);
				 CHEQUEPRINT.addValidationData(dataValidation2);
				 
				int q1 = 0;
				for (String columnNames : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(q1++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnNames));
				}
				
			
				
					//new SET COLUMN WIDTHS
					for(int l=0;l<columns.length;l++){
						sampleDataSheet.setColumnWidth(l, 4000);}
				
					HSSFRow headerRow = sampleDataSheet.createRow(0);
					HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
					headerRow.setHeightInPoints(120);
					
					// For add dropdown menu in xls
					CellRangeAddressList addressList1 = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint1 = DVConstraint
				                .createExplicitListConstraint(new String[] { "I"});
					 HSSFDataValidation dataValidation1 = new HSSFDataValidation(addressList1,
				                dvConstraint1);
					 dataValidation1.setSuppressDropDownArrow(false);
					 sampleDataSheet.addValidationData(dataValidation1);
					int l1 = 0;
					for (String columnNames : columns){
						HSSFCell headerCell1 = headerRow.createCell(l1++);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue(new HSSFRichTextString(columnNames));
					}
					
					
					
					//fundTransfer
					
					int i = 0;
					FUNDTRANSFER.setColumnWidth(i++, 4000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 5500);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);//g
					
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//n
					
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//u
					
					FUNDTRANSFER.setColumnWidth(i++, 2000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 2000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//y
					
					FUNDTRANSFER.setColumnWidth(i++, 9000);//z
					
			        FUNDTRANSFER.setColumnWidth(i++, 6000);//y
					
					FUNDTRANSFER.setColumnWidth(i++, 9000);//z
					
					
					
					HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
					HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
					headerRow1.setHeightInPoints(120);
					
					CellRangeAddressList addressList = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint = DVConstraint
				                .createExplicitListConstraint(new String[] { "I" });
					 HSSFDataValidation dataValidation= new HSSFDataValidation(addressList,
				                dvConstraint);
					 dataValidation.setSuppressDropDownArrow(false);
					 FUNDTRANSFER.addValidationData(dataValidation);
					  i = 0;
						for (String columnNames : columnsz){
							HSSFCell headerCell1 = headerRow1.createCell(i++);
							headerCell1.setCellStyle(headerStyle1);
							headerCell1.setCellValue(new HSSFRichTextString(columnNames));
						}
				
					
					/**
					 * Set the cell value for all the data rows.
					 */
					HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
					HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
					cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
					cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
				
					
					int j = 1;
					if (salaryDetails != null && salaryDetails.length > 0){
						for (SalaryReportBean eachSalaryBean : salaryDetails){
							HSSFRow dataRow = FUNDTRANSFER.createRow(j++);
							i = 0;
							dataRow.setHeightInPoints(13);
							
							HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue("I");
							
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getEmp_code());
							
							
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getAccount_no());
							
		/*					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
							cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
							dataCell.setCellStyle(cellStyle1);
							String amount = eachSalaryBean.getTran_amt();
							dataCell.setCellValue(amount != null ? Double.valueOf(amount) : 0);
							*/
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getTran_amt());
						
							
							dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
							dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(eachSalaryBean.getName());
						
						
						
						dataCell = dataRow.createCell(7);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(8);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(9);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(10);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(11);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("560080");
						
						dataCell = dataRow.createCell(12);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Salary");
						
						dataCell = dataRow.createCell(13);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Salary");
						
						dataCell = dataRow.createCell(14);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Salary");
						
						dataCell = dataRow.createCell(22, Cell.CELL_TYPE_STRING);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue(dateFormat.format(date));
						
						/*dataCell = dataRow.createCell(24, Cell.CELL_TYPE_STRING);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue(eachSalaryBean.getBankName());*/
						
						dataCell = dataRow.createCell(27, Cell.CELL_TYPE_STRING);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue(eachSalaryBean.getEmail_id());
						}
					}
					
						int formEnd = j;
						j++;
						HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
						HSSFRow sumRow = FUNDTRANSFER.createRow(j++);
						sumRow.setHeightInPoints(20);
						HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
						cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
					//	cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
						cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						sumCell.setCellStyle(cellStyle5);
				//		sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
						HSSFPalette palette = sampleWorkbook.getCustomPalette();
						palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
				
						FileOutputStream fileOutputStream = new FileOutputStream(path, true);
						sampleWorkbook.write(fileOutputStream);
						fileOutputStream.flush();
						fileOutputStream.close();
						success = true;
					
			} catch (FileNotFoundException e){
				logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
			} catch (IOException e){
				logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
			}
			if (success) return fileName;
			else return "error";
		}

public String generateInvoiceReconciliationReport(List<String[]> findWhereInvoiceidForReport, String path) {
	FileOutputStream fileOutputStream = null;
	HSSFWorkbook sampleWorkbook = null;
	HSSFSheet sampleDataSheet = null;
	try{
		sampleWorkbook = new HSSFWorkbook();
		sampleDataSheet = sampleWorkbook.createSheet("InvoiceReconciliation");
String columns [] = new String[] {"Emp ID", "Name", "Project", " Charge code ", "Start date","End date","PO No","Bill Rate","Currency","PO Status","Start date","End date","Working Days","Leave","Timesheet Category","Approval status","Action By","Invoice Amount","Invoice Date","Action date","Invoice Status","Collection Amount","Collection Date"};

int u=0;
		 int columnWidth1[] = new int[]  { 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000,4000, 4000, 4000,4000,4000, 4000, 4000};
		 for (int width : columnWidth1)
			 sampleDataSheet.setColumnWidth(u++, width);
		
		
			HSSFRow headerRow1 = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
			headerRow1.setHeightInPoints(90);
			


      HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
		HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
		HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
		commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
		int l1 = 0;
		for (String columnNames : columns){
			HSSFCell headerCell1 = headerRow1.createCell(l1++);
			headerCell1.setCellStyle(headerStyle1);
			headerCell1.setCellValue(new HSSFRichTextString(columnNames));
		}
		int j = 1;
		for (String[] row : findWhereInvoiceidForReport){
			//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
			HSSFRow dataRow = sampleDataSheet.createRow(j++);
			int i = 0;
			dataRow.setHeightInPoints(13);
			HSSFCell dataCell = dataRow.createCell(i++,Cell.CELL_TYPE_NUMERIC);			
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[0]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[1]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[2]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[3]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[4]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[5]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[6]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[7]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[8]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[9]);
			
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[10]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[11]);//st date
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[12]);//end date
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[13]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[14]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[15]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[16]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[17]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[18]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[19]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[20]);
		/*	
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[21]);*/
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[21]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[22]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[23]);

			
			
			
			
			/*HSSFCell dataCell = dataRow.createCell(i++,Cell.CELL_TYPE_NUMERIC);			
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[0]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[1]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[2]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[3]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[4]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[5]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[6]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[7]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[8]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[9]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[4]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[5]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[10]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[11]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[12]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[13]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[14]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[15]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[16]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[17]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[18]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[19]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[20]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[21]);*/

			
}
		

		
		
		
		
		
		HSSFPalette palette = sampleWorkbook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
		fileOutputStream = new FileOutputStream(path);
		sampleWorkbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
		e.printStackTrace();
		logger.error("xls creation exception", e);
	}
	return "";
}


public String generateFinalReconciliationReport(List<String[]> findWhereInvoiceidForReport, String path) {
	FileOutputStream fileOutputStream = null;
	HSSFWorkbook sampleWorkbook = null;
	HSSFSheet sampleDataSheet = null;
	try{
		sampleWorkbook = new HSSFWorkbook();
		sampleDataSheet = sampleWorkbook.createSheet("InvoiceReconciliation");
String columns [] = new String[] {"Emp ID", "Name", "Project", " Charge code ", "Start date", "End date", "PO No", "Bill Rate","Currency", "PO Status", "Start date", "End date", "Working days", "Leave","Timesheet category", "Approval status", "Action By", "Invoice amount", " Invoice date","Action date", "Invoice status", "Collection Amount", "Collection date", "Resource Cost", "Travel and Acc", "Other Cost", "Total cost", "Profit%", "Diff in PO & Collection"};

int u=0;
		 int columnWidth1[] = new int[]  {4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000,4000, 4000, 4000,4000,4000, 4000, 4000,4000, 4000,4000,4000, 4000, 4000};
		 for (int width : columnWidth1)
			 sampleDataSheet.setColumnWidth(u++, width);
		
		
			HSSFRow headerRow1 = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
			headerRow1.setHeightInPoints(90);
			


      HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
		HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
		HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
		commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
		int l1 = 0;
		for (String columnNames : columns){
			HSSFCell headerCell1 = headerRow1.createCell(l1++);
			headerCell1.setCellStyle(headerStyle1);
			headerCell1.setCellValue(new HSSFRichTextString(columnNames));
		}
		int j = 1;
		for (String[] row : findWhereInvoiceidForReport){
			//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
			HSSFRow dataRow = sampleDataSheet.createRow(j++);
			int i = 0;
			dataRow.setHeightInPoints(13);
			HSSFCell dataCell = dataRow.createCell(i++,Cell.CELL_TYPE_NUMERIC);			
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[0]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[1]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[2]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[3]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[4]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[5]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[6]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[7]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[8]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[9]);
			
			/*dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[4]);//start date
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[5]);//end date
			*/
			dataCell = dataRow.createCell(10);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[27]);
			
			dataCell = dataRow.createCell(11);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[28]);
			
			dataCell = dataRow.createCell(12);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[10]);
			
			dataCell = dataRow.createCell(13);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[11]);
			
			dataCell = dataRow.createCell(14);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[12]);
			
			dataCell = dataRow.createCell(15);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[13]);
			
			dataCell = dataRow.createCell(16);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[14]);
			
			dataCell = dataRow.createCell(17);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[15]);
			
			dataCell = dataRow.createCell(18);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[16]);
			
			dataCell = dataRow.createCell(19);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[17]);
			
			dataCell = dataRow.createCell(20);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[18]);
			//final report value
			dataCell = dataRow.createCell(21);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[19]);
			
			dataCell = dataRow.createCell(22);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[20]);
			
			dataCell = dataRow.createCell(23);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[21]);
			
			dataCell = dataRow.createCell(24);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[22]);
			
			dataCell = dataRow.createCell(25);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[23]);
			
			dataCell = dataRow.createCell(26);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[24]);
			
			
			dataCell = dataRow.createCell(27);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[25]);
			
			dataCell = dataRow.createCell(28);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[26]);
			
			
			
		/*	HSSFCell dataCell = dataRow.createCell(i++,Cell.CELL_TYPE_NUMERIC);			
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[0]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[1]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[2]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[3]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[4]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[5]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[6]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[7]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[8]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[9]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[4]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[5]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[10]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[11]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[12]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[13]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[14]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[15]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[16]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[17]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[18]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[19]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[20]);
			//final report value
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[21]);
			
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[22]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[23]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[24]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[25]);
			dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue(row[26]);
			*/

			
}
		

		
		
		
		
		
		HSSFPalette palette = sampleWorkbook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
		fileOutputStream = new FileOutputStream(path);
		sampleWorkbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
		e.printStackTrace();
		logger.error("xls creation exception", e);
	}
	return "";
}







public String generateTimeSheetReconciliation(RmgTimeSheet[] rmgTimeSheet, String path) {
	
	HSSFWorkbook sampleWorkbook = null;
	HSSFSheet sampleDataSheet = null;
	boolean success = false;
	//String monthId = getMonthId(sr_id);
	String fileName = "TimeSheet Internal Report " + "=" + ".xls";
	path += File.separator + fileName;
	File file = new File(path);
	if (file.exists()) file.delete();
	try{
		sampleWorkbook = new HSSFWorkbook();
		sampleDataSheet = sampleWorkbook.createSheet("TIMESHEET INTERNAL REPORT");
		String columns[] = new String[] { "Emp ID", "Name", "Project", " Charge code ", "Start date", "End date","Working Days","Leave","Timesheet Category","Approval status","Action By" };
		int i = 0;
		sampleDataSheet.setColumnWidth(i++, 2000);
		sampleDataSheet.setColumnWidth(i++, 8000);
		sampleDataSheet.setColumnWidth(i++, 8000);
		sampleDataSheet.setColumnWidth(i++, 5000);
		sampleDataSheet.setColumnWidth(i++, 5000);
		sampleDataSheet.setColumnWidth(i++, 6000);
		
		sampleDataSheet.setColumnWidth(i++, 8000);
		sampleDataSheet.setColumnWidth(i++, 3000);
		sampleDataSheet.setColumnWidth(i++, 5000);
		sampleDataSheet.setColumnWidth(i++, 5000);
		sampleDataSheet.setColumnWidth(i++, 6000);
		
		HSSFRow headerRow = sampleDataSheet.createRow(0);
		HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
		headerRow.setHeightInPoints(20);
		i = 0;
		for (String columnName : columns){
			HSSFCell headerCell = headerRow.createCell(i++);
			headerCell.setCellStyle(headerStyle);
			headerCell.setCellValue(new HSSFRichTextString(columnName));
		}
		HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
		HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
		int j = 1;
		if (rmgTimeSheet != null && rmgTimeSheet.length > 0){
			for (RmgTimeSheet timeSheet : rmgTimeSheet){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getEmpId());
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getName());
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getPrj_Name());
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getChrg_Code_Name());
				
				
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				if(timeSheet.getStartDate()==null){
					dataCell.setCellValue(0);
				}else{
					dataCell.setCellValue(timeSheet.getStartDate());
					
				}
				
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				if(timeSheet.getEndDate()==null){
					dataCell.setCellValue(0);
				}else{
					dataCell.setCellValue(timeSheet.getEndDate());
				}
				//dataCell.setCellValue(timeSheet.getEndDate());
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getWorking_Days());
				

				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getLeave());
				

				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getTimeSheet_Cato());
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getStatus());
				
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(timeSheet.getActionBy());
				
				
			}
		}
		int formEnd = j;
		j++;
		HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
		HSSFRow sumRow = sampleDataSheet.createRow(j++);
		sumRow.setHeightInPoints(20);
		HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
		cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		sumCell.setCellStyle(cellStyle5);
		sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
		HSSFPalette palette = sampleWorkbook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
		FileOutputStream fileOutputStream = new FileOutputStream(path, true);
		sampleWorkbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		success = true;
	} catch (FileNotFoundException e){
		logger.error("There is a FileNotFoundException while generating the TimeSheet Internal Reports " + e.getMessage());
	} catch (IOException e){
		logger.error("There is a IOException while generating the TimeSheet Internal Reports" + e.getMessage());
	}
	if (success) return fileName;
	else return "error";
}

	public String generateSalaryDisbLetter(SalaryReportBean[] salaryDetails, String path, int sr_id) {
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;
		String monthId = getMonthId(sr_id);
		String fileName = "Salary_Disbursement_Letter " + SalaryReportUtilities.getTextMonthFromId(monthId) + " (" + PortalUtility.getdd_MM_yyyy(new Date()) + ")" + ".xls";
		path += File.separator + fileName;
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			int j = 1;
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("Salary Disbursement Letter " + PortalUtility.getdd_MM_yyyy(new Date()));
			String columns[] = new String[] { "SL. No.", "Name", "Account  No", "Amount" };
			sampleDataSheet.setColumnWidth(0, 2000);
			//sampleDataSheet.setColumnWidth(1, 2500);
			sampleDataSheet.setColumnWidth(1, 8000);
			sampleDataSheet.setColumnWidth(2, 5000);
			sampleDataSheet.setColumnWidth(3, 4000);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow dikshaRow = sampleDataSheet.createRow(j++);
			dikshaRow.setHeightInPoints(20);
			HSSFCell dikshaCell = dikshaRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(dikshaRow.getRowNum(), dikshaRow.getRowNum(), 0, 4));
			cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle1.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			dikshaCell.setCellStyle(cellStyle1);
			dikshaCell.setCellValue(new HSSFRichTextString("DIKSHA TECHNOLOGIES PVT LTD"));
			j++;
			HSSFCellStyle cellStyle4 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow dateRow = sampleDataSheet.createRow(j++);
			dateRow.setHeightInPoints(15);
			HSSFCell dateCell = dateRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(dateRow.getRowNum(), dateRow.getRowNum(), 0, 4));
			cellStyle4.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle4.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			dateCell.setCellStyle(cellStyle4);
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			dateCell.setCellValue(new HSSFRichTextString(sf.format(new Date())));
			HSSFCellStyle cellStyle2 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow iciciRow = sampleDataSheet.createRow(j++);
			iciciRow.setHeightInPoints(15);
			HSSFCell iciciCell = iciciRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(iciciRow.getRowNum(), iciciRow.getRowNum(), 0, 4));
			cellStyle2.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle2.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			iciciCell.setCellStyle(cellStyle2);
			iciciCell.setCellValue(new HSSFRichTextString("ICICI Bank Limited"));
			HSSFCellStyle cellStyle3 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow mgRow = sampleDataSheet.createRow(j++);
			mgRow.setHeightInPoints(15);
			HSSFCell mgCell = mgRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(mgRow.getRowNum(), mgRow.getRowNum(), 0, 4));
			cellStyle3.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle3.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			mgCell.setCellStyle(cellStyle3);
			mgCell.setCellValue("M G ROAD");
			HSSFRow bgRow = sampleDataSheet.createRow(j++);
			bgRow.setHeightInPoints(15);
			HSSFCell bgCell = bgRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(bgRow.getRowNum(), bgRow.getRowNum(), 0, 4));
			bgCell.setCellStyle(cellStyle3);
			bgCell.setCellValue("BANGALORE");
			HSSFRow pinRow = sampleDataSheet.createRow(j++);
			pinRow.setHeightInPoints(15);
			HSSFCell pinCell = pinRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(pinRow.getRowNum(), pinRow.getRowNum(), 0, 4));
			pinCell.setCellStyle(cellStyle3);
			pinCell.setCellValue("560001");
			j++;
			HSSFRow dearRow = sampleDataSheet.createRow(j++);
			dearRow.setHeightInPoints(15);
			HSSFCell dearCell = dearRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(dearRow.getRowNum(), dearRow.getRowNum(), 0, 4));
			dearCell.setCellStyle(cellStyle3);
			dearCell.setCellValue("Dear Sirs,");
			j++;
			HSSFRow refRow = sampleDataSheet.createRow(j++);
			refRow.setHeightInPoints(15);
			HSSFCell refCell = refRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(refRow.getRowNum(), refRow.getRowNum(), 0, 4));
			refCell.setCellStyle(cellStyle3);
			refCell.setCellValue("Ref:  Salary  for the month: " + SalaryReportUtilities.getTextMonthFromId(monthId));
			HSSFRow subRow = sampleDataSheet.createRow(j++);
			subRow.setHeightInPoints(40);
			HSSFCell subCell = subRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(subRow.getRowNum(), subRow.getRowNum(), 0, 4));
			subCell.setCellStyle(cellStyle3);
			subCell.setCellValue("Kindly credit the following Savings Bank A/C with you,with the individual amounts shown against them anddebit our account with you / Our cheque number _______________ dated ____________ for the total amount is enclosed. ");
			j++;
			HSSFRow headerRow = sampleDataSheet.createRow(j++);
			HSSFFont font = sampleWorkbook.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle cellStyle111 = sampleWorkbook.createCellStyle();
			cellStyle111.setFont(font);
			cellStyle111.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle111.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			HSSFCellStyle cellStyle12 = sampleWorkbook.createCellStyle();
			cellStyle12.setFont(font);
			cellStyle12.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle12.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			headerRow.setHeightInPoints(18);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++, Cell.CELL_TYPE_STRING);
				if (i == 4) headerCell.setCellStyle(cellStyle12);
				else headerCell.setCellStyle(cellStyle111);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			HSSFCellStyle cellStyleright = setHeaderStyle(sampleWorkbook, false);
			cellStyleright.setAlignment(CellStyle.ALIGN_RIGHT);
			int k = 0;
			int formStart = j + 1;
			if (salaryDetails != null && salaryDetails.length > 0){
				for (SalaryReportBean eachSalaryBean : salaryDetails){
					HSSFRow dataRow = sampleDataSheet.createRow(j++);
					i = 0;
					dataRow.setHeightInPoints(13);
					HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(++k);
					/*dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getEmp_code());*/
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getName());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(eachSalaryBean.getAccount_no());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyleright);
					String strSal = eachSalaryBean.getTran_amt();
					cellStyleright.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					dataCell.setCellValue(strSal != null ? Double.valueOf(strSal) : 0);
				}
			}
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + formStart + ":D" + formEnd + ")");
			// sumCell.setCellValue(new HSSFRichTextString("Sum : " + String.valueOf(sumOfSal)));
			j++;
			j++;
			HSSFRow forRow = sampleDataSheet.createRow(j++);
			forRow.setHeightInPoints(15);
			HSSFCell forCell = forRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(forRow.getRowNum(), forRow.getRowNum(), 0, 4));
			cellStyle3.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle3.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			forCell.setCellStyle(cellStyle3);
			forCell.setCellValue("For Diksha Technologies Pvt Ltd");
			j++;
			j++;
			j++;
			j++;
			HSSFRow signRow = sampleDataSheet.createRow(j++);
			signRow.setHeightInPoints(15);
			HSSFCell signCell = signRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(signRow.getRowNum(), signRow.getRowNum(), 0, 4));
			cellStyle3.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle3.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			signCell.setCellStyle(cellStyle3);
			signCell.setCellValue("Authorised Signatory");
			//palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			FileOutputStream fileOutputStream = new FileOutputStream(path, true);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			success = true;
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
		} catch (IOException e){
			logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
		}
		if (success) return fileName;
		else return "error";
	}

	public String generateLeaveBalanceXls(LeaveBalance[] leaveBalance, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("LeaveBalance on " + PortalUtility.getdd_MM_yyyy(new Date()));
			String columns[] = new String[] { "EMP_ID", "LEAVES ACCUMALATED", "LEAVES_TAKEN", "PATERNITY", "MARRIAGE", "BEREAVEMENT", "MATERNITY", "COMP_OFF", "LWP" };
			sampleDataSheet.setColumnWidth(1, 6000);
			for (int k = 0; k < columns.length; k++)
				sampleDataSheet.setColumnWidth(k, 4000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(40);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			int j = 1;
			for (LeaveBalance leave : leaveBalance){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(30);
				HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getId());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getLeaveAccumalated());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getLeavesTaken());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getPaternity());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getMarriage());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getBereavement());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getMaternity());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getCompOff());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(leave.getLwp());
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	public String generateSodexoXls(String[][] sodexoReports, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("sodexo on " + PortalUtility.getdd_MM_yyyy(new Date()));
			String columns[] = new String[] { " Sl. No.", "EMP_ID", "Name", "Amount", "Address" };
			sampleDataSheet.setColumnWidth(1, 4000);
			sampleDataSheet.setColumnWidth(2, 8000);
			sampleDataSheet.setColumnWidth(3, 4000);
			sampleDataSheet.setColumnWidth(4, 15000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(30);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle nameStyle = setNameStyle(sampleWorkbook, false);
			int j = 1;
			HSSFCell dataCell;
			HSSFRow dataRow = null;
			for (String[] reports : sodexoReports){
				dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(20);
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(j - 1);
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(Integer.parseInt(reports[1]));
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(nameStyle);
				dataCell.setCellValue(reports[2]);
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(Integer.parseInt(reports[3]));
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(reports[4]);
			}
			cellStyle = setSumStyle(sampleWorkbook);
			dataRow = sampleDataSheet.createRow(j++);
			dataRow.setHeightInPoints(30);
			dataCell = dataRow.createCell(0);
			dataCell.setCellStyle(cellStyle);
			dataCell = dataRow.createCell(1);
			dataCell.setCellStyle(cellStyle);
			dataCell = dataRow.createCell(2);
			dataCell.setCellStyle(cellStyle);
			dataCell.setCellValue("Total Amount Rs. ");
			cellStyle = setSumStyle(sampleWorkbook);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			dataCell = dataRow.createCell(3);
			dataCell.setCellStyle(cellStyle);
			String cellsFrom_D2_to_Dn = "D2:D" + (j - 1);
			dataCell.setCellFormula("SUM(" + cellsFrom_D2_to_Dn + ")");
			dataCell = dataRow.createCell(4);
			dataCell.setCellStyle(cellStyle);
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			palette.setColorAtIndex(HSSFColor.GOLD.index, (byte) 221, (byte) 217, (byte) 215);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	/**
	 * @author Gurunath.rokkam
	 * @param list
	 * @param path
	 * @return file name
	 */
	public String generateXlsRMReport(List<String[]> list, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("APPRAISAL HR REPORTS");
			String columns[] = new String[] { "ID", "Req Id", "Employee Id", "Employee Name", "Total Amount", "Currency Type", " Requested Date", "Status", "Department", "Division", "Project Name", "Cheque No/Comments" };
			sampleDataSheet.setColumnWidth(0, 3000);
			int k = 1;
			for (; k < columns.length; k++)
				sampleDataSheet.setColumnWidth(k, 4000);
			sampleDataSheet.setColumnWidth(3, 9000);
			sampleDataSheet.setColumnWidth(columns.length, 9000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(25);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			int j = 1;
			for (String[] reports : list){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(20);
				HSSFCell dataCell = null;
				for (String report : reports){
					dataCell = dataRow.createCell(i++);
					if (i == 1 || i == 3 || i == 5){
						dataCell.setCellType(Cell.CELL_TYPE_NUMERIC);
						dataCell.setCellValue(new Double(report));
					} else dataCell.setCellValue(report);
					dataCell.setCellStyle(cellStyle);
				}
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}
	
	
	public String reimbursementReportHdfc(List<Map<String, Object>> list,String path) {
		
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		String fileName = "Reimbursement Report Hdfc.xls";
		path += File.separator + fileName;//?
		File file = new File(path);
		if (file.exists()) file.delete();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   //get current date time with Date()
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
		
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE PRINT");
			HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		
			
			
/*			String columns[] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments"};
			String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Employee Id\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Reimbursement Req Id\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments"};*/
			
			String columns[] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments"};
			String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments"};
			String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory"};
            String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments "};
			
			// sheet 5
			for(int u=0;u<columns2.length;u++){
				SEFTEFT.setColumnWidth(u, 4000);}
			
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				headerRow5.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList5 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint5 = DVConstraint
			                .createExplicitListConstraint(new String[] { "S" });
				 HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,
			                dvConstraint5);
				 dataValidation5.setSuppressDropDownArrow(false);
				 SEFTEFT.addValidationData(dataValidation5);
				 
				int u1 = 0;
				for (String columnNames : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(u1++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 4
			
			for(int t=0;t<columns2.length;t++){
				PAYORDERPRINT.setColumnWidth(t, 4000);}
			
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				headerRow4.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList4 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint4 = DVConstraint
			                .createExplicitListConstraint(new String[] { "H" });
				 HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,
			                dvConstraint4);
				 dataValidation4.setSuppressDropDownArrow(false);
				 PAYORDERPRINT.addValidationData(dataValidation4);
				 
				int t1 = 0;
				for (String columnNames : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(t1++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 3
			for(int p=0;p<columns2.length;p++){
				DRAFTPRINT.setColumnWidth(p, 4000);}
			
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				headerRow3.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList3 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint3 = DVConstraint
			                .createExplicitListConstraint(new String[] { "D" });
				 HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,
			                dvConstraint3);
				 dataValidation3.setSuppressDropDownArrow(false);
				 DRAFTPRINT.addValidationData(dataValidation3);
				 
				int p1 = 0;
				for (String columnNames : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(p1++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 2
			for(int q=0;q<columns2.length;q++){
				CHEQUEPRINT.setColumnWidth(q, 4000);
				}
			
				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				headerRow2.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList2 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint2 = DVConstraint
			                .createExplicitListConstraint(new String[] { "C" });
				 HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,
			                dvConstraint2);
				 dataValidation2.setSuppressDropDownArrow(false);
				 CHEQUEPRINT.addValidationData(dataValidation2);
				 
				int q1 = 0;
				for (String columnNames : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(q1++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnNames));
				}
				
			
				
					//new SET COLUMN WIDTHS
					for(int l=0;l<columns.length;l++){
						sampleDataSheet.setColumnWidth(l, 4000);}
				
					HSSFRow headerRow = sampleDataSheet.createRow(0);
					HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
					headerRow.setHeightInPoints(120);
					
					// For add dropdown menu in xls
					CellRangeAddressList addressList1 = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint1 = DVConstraint
				                .createExplicitListConstraint(new String[] { "I"});
					 HSSFDataValidation dataValidation1 = new HSSFDataValidation(addressList1,
				                dvConstraint1);
					 dataValidation1.setSuppressDropDownArrow(false);
					 sampleDataSheet.addValidationData(dataValidation1);
					int l1 = 0;
					for (String columnNames : columns){
						HSSFCell headerCell1 = headerRow.createCell(l1++);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue(new HSSFRichTextString(columnNames));
					}
					
					
					
					//fundTransfer
					
					int i = 0;
					FUNDTRANSFER.setColumnWidth(i++, 4000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 5500);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);//g
					
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//n
					
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//u
					
					FUNDTRANSFER.setColumnWidth(i++, 2000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 2000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//y
					
					FUNDTRANSFER.setColumnWidth(i++, 9000);//z
					
			        FUNDTRANSFER.setColumnWidth(i++, 6000);//y
					
					FUNDTRANSFER.setColumnWidth(i++, 9000);//z
					
					
					
					HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
					HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
					headerRow1.setHeightInPoints(120);
					
					CellRangeAddressList addressList = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint = DVConstraint
				                .createExplicitListConstraint(new String[] { "I" });
					 HSSFDataValidation dataValidation= new HSSFDataValidation(addressList,
				                dvConstraint);
					 dataValidation.setSuppressDropDownArrow(false);
					 FUNDTRANSFER.addValidationData(dataValidation);
					  i = 0;
						for (String columnNames : columnsz){
							HSSFCell headerCell1 = headerRow1.createCell(i++);
							headerCell1.setCellStyle(headerStyle1);
							headerCell1.setCellValue(new HSSFRichTextString(columnNames));
						}
				
					
					/**
					 * Set the cell value for all the data rows.
					 */
					HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
					HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
					cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
					cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
				
					int j = 1;

					for (Map<String, Object> map : list) {
						
						
						HSSFRow dataRow = FUNDTRANSFER.createRow(j++);
						dataRow.setHeightInPoints(13);
						HSSFCell dataCell = dataRow.createCell(0);
						//		dataCell.setCellStyle(cellStyle);
								dataCell.setCellValue("I");
								 Object value =null;
								 
							dataCell = dataRow.createCell(27);
							if(map.containsKey("email_id")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("email_id");
							dataCell.setCellValue(value.toString()+",rmg@dikshatech.com");
							
							}
							
							
							dataCell = dataRow.createCell(1);
							if(map.containsKey("emp_code")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("emp_code");
							dataCell.setCellValue(value.toString());
							
							}
									
							
							dataCell = dataRow.createCell(3);
							if(map.containsKey("totalAmount")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("totalAmount");
							dataCell.setCellValue(value.toString());//amount
							}
						
						
					
							dataCell = dataRow.createCell(4);
							if(map.containsKey("name")){
						//	dataCell.setCellStyle(cellStyle);
								 value = map.get("name");
							dataCell.setCellValue(value.toString());//name
							}
						
							dataCell = dataRow.createCell(22);
					//		dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(dateFormat.format(date));
							
					/*		dataCell = dataRow.createCell(25);
							if(map.containsKey("bankName")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("bankName");
							dataCell.setCellValue(value.toString());//bank name
							}
							
							
							dataCell = dataRow.createCell(24);
							if(map.containsKey("primaryIfsc")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("primaryIfsc");
							dataCell.setCellValue(value.toString()); //ifsc
							}*/
							
							
							dataCell = dataRow.createCell(2);
							if(map.containsKey("account_no")){
						//	dataCell.setCellStyle(cellStyle);
								 value = map.get("account_no");
							dataCell.setCellValue(value.toString()); //acc no
							}
						
							dataCell = dataRow.createCell(12);
							if(map.containsKey("reqId")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("reqId");
							dataCell.setCellValue(value.toString());
							}
						
						
						dataCell = dataRow.createCell(7);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(8);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(9);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(10);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(11);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("560080");
						
					
						
						dataCell = dataRow.createCell(13);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Reimbursement");
						
						dataCell = dataRow.createCell(14);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Reimbursement");
		
					    
					}
					
					
					
					
						j++;
						HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
						HSSFRow sumRow = FUNDTRANSFER.createRow(j++);
						sumRow.setHeightInPoints(20);
						HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
						cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
						cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
						cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						sumCell.setCellStyle(cellStyle5);
				
						HSSFPalette palette = sampleWorkbook.getCustomPalette();
						palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
				
					
						FileOutputStream fileOutputStream = new FileOutputStream(path, true);
						sampleWorkbook.write(fileOutputStream);
						fileOutputStream.flush();
						fileOutputStream.close();
						
						} catch (Exception e){
						e.printStackTrace();
						logger.error("xls creation exception", e);
					}
					return fileName;
				}
					
					
			
	
public String reimbursementReportNonHdfc(List<Map<String, Object>> list,String path) {

		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;

		String fileName = "Reimbursement Report Nonhdfc.xls";
		path += File.separator + fileName;//?
		File file = new File(path);
		if (file.exists()) file.delete();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   //get current date time with Date()
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
		
		try{
			
			
			
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE PRINT");
			HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		
			
			
/*			String columns[] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","Employee Id\n A\n 13\n Mandatory","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Reimbursement Req Id\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments"};
			String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments"};*/
			
			String columns[] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments"};
			String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments"};
		    String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory"};
            String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory"};
            String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments "};
			
			// sheet 5
			for(int u=0;u<columns2.length;u++){
				SEFTEFT.setColumnWidth(u, 4000);}
			
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				headerRow5.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList5 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint5 = DVConstraint
			                .createExplicitListConstraint(new String[] { "S" });
				 HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,
			                dvConstraint5);
				 dataValidation5.setSuppressDropDownArrow(false);
				 SEFTEFT.addValidationData(dataValidation5);
				 
				int u1 = 0;
				for (String columnNames : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(u1++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 4
			
			for(int t=0;t<columns2.length;t++){
				PAYORDERPRINT.setColumnWidth(t, 4000);}
			
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				headerRow4.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList4 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint4 = DVConstraint
			                .createExplicitListConstraint(new String[] { "H" });
				 HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,
			                dvConstraint4);
				 dataValidation4.setSuppressDropDownArrow(false);
				 PAYORDERPRINT.addValidationData(dataValidation4);
				 
				int t1 = 0;
				for (String columnNames : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(t1++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 3
			for(int p=0;p<columns2.length;p++){
				DRAFTPRINT.setColumnWidth(p, 4000);}
			
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				headerRow3.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList3 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint3 = DVConstraint
			                .createExplicitListConstraint(new String[] { "D" });
				 HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,
			                dvConstraint3);
				 dataValidation3.setSuppressDropDownArrow(false);
				 DRAFTPRINT.addValidationData(dataValidation3);
				 
				int p1 = 0;
				for (String columnNames : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(p1++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnNames));
				}
			
			
			
			
			//sheet 2
			for(int q=0;q<columns2.length;q++){
				CHEQUEPRINT.setColumnWidth(q, 4000);
				}
			
				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				headerRow2.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList2 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint2 = DVConstraint
			                .createExplicitListConstraint(new String[] { "C" });
				 HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,
			                dvConstraint2);
				 dataValidation2.setSuppressDropDownArrow(false);
				 CHEQUEPRINT.addValidationData(dataValidation2);
				 
				int q1 = 0;
				for (String columnNames : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(q1++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnNames));
				}
				
			
				
					//new SET COLUMN WIDTHS
					for(int l=0;l<columns.length;l++){
						sampleDataSheet.setColumnWidth(l, 4000);}
				
					HSSFRow headerRow = sampleDataSheet.createRow(0);
					HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
					headerRow.setHeightInPoints(120);
					
					// For add dropdown menu in xls
					CellRangeAddressList addressList1 = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint1 = DVConstraint
				                .createExplicitListConstraint(new String[] { "N"});
					 HSSFDataValidation dataValidation1 = new HSSFDataValidation(addressList1,
				                dvConstraint1);
					 dataValidation1.setSuppressDropDownArrow(false);
					 sampleDataSheet.addValidationData(dataValidation1);
					int l1 = 0;
					for (String columnNames : columns){
						HSSFCell headerCell1 = headerRow.createCell(l1++);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue(new HSSFRichTextString(columnNames));
					}
					
					
					
					//fundTransfer
					
					int i = 0;
					FUNDTRANSFER.setColumnWidth(i++, 4000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 5500);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);//g
					
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 8000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//n
					
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//u
					
					FUNDTRANSFER.setColumnWidth(i++, 2000);
					FUNDTRANSFER.setColumnWidth(i++, 3000);
					FUNDTRANSFER.setColumnWidth(i++, 2000);
					FUNDTRANSFER.setColumnWidth(i++, 5000);//y
					
					FUNDTRANSFER.setColumnWidth(i++, 9000);//z
					
			        FUNDTRANSFER.setColumnWidth(i++, 6000);//y
					
					FUNDTRANSFER.setColumnWidth(i++, 9000);//z
					
					
					
					HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
					HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
					headerRow1.setHeightInPoints(120);
					
					CellRangeAddressList addressList = new CellRangeAddressList(1, 100, 0, 0);
					 DVConstraint dvConstraint = DVConstraint
				                .createExplicitListConstraint(new String[] { "N" });
					 HSSFDataValidation dataValidation= new HSSFDataValidation(addressList,
				                dvConstraint);
					 dataValidation.setSuppressDropDownArrow(false);
					 FUNDTRANSFER.addValidationData(dataValidation);
					  i = 0;
						for (String columnNames : columnsz){
							HSSFCell headerCell1 = headerRow1.createCell(i++);
							headerCell1.setCellStyle(headerStyle1);
							headerCell1.setCellValue(new HSSFRichTextString(columnNames));
						}
				
						
						
		
							
					
					/**
					 * Set the cell value for all the data rows.
					 */
					HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
					HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
					cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
					cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
				
					
					
					int j = 1;

					for (Map<String, Object> map : list) {
						
						
						HSSFRow dataRow = sampleDataSheet.createRow(j++);
						dataRow.setHeightInPoints(13);
						HSSFCell dataCell = dataRow.createCell(0);
						//		dataCell.setCellStyle(cellStyle);
								dataCell.setCellValue("N");
						
				
								
					    	      String key=null ;
						        Object value =null;
					        System.out.println("key===="+key+ "value===="+value);
					        
			
							
						
							dataCell = dataRow.createCell(27);
							if(map.containsKey("email_id")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("email_id");
							dataCell.setCellValue(value.toString()+",rmg@dikshatech.com");
							
							}
									
							
							dataCell = dataRow.createCell(3);
							if(map.containsKey("totalAmount")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("totalAmount");
							dataCell.setCellValue(value.toString());//amount
							}
						
						
					
							dataCell = dataRow.createCell(4);
							if(map.containsKey("name")){
						//	dataCell.setCellStyle(cellStyle);
								 value = map.get("name");
							dataCell.setCellValue(value.toString());//name
							}
						
							dataCell = dataRow.createCell(22);
					//		dataCell.setCellStyle(cellStyle);
							dataCell.setCellValue(dateFormat.format(date));
							
							dataCell = dataRow.createCell(25);
							if(map.containsKey("bankName")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("bankName");
							dataCell.setCellValue(value.toString());//bank name
							}
							
							
							dataCell = dataRow.createCell(24);
							if(map.containsKey("primaryIfsc")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("primaryIfsc");
							dataCell.setCellValue(value.toString()); //ifsc
							}
							
							
							dataCell = dataRow.createCell(2);
							if(map.containsKey("account_no")){
						//	dataCell.setCellStyle(cellStyle);
								 value = map.get("account_no");
							dataCell.setCellValue(value.toString()); //acc no
							}
						
							dataCell = dataRow.createCell(12);
							if(map.containsKey("reqId")){
					//		dataCell.setCellStyle(cellStyle);
								 value = map.get("reqId");
							dataCell.setCellValue(value.toString());
							}
						
						
						dataCell = dataRow.createCell(7);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(8);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(9);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(10);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Bangalore");
						
						dataCell = dataRow.createCell(11);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("560080");
						
					
						
						dataCell = dataRow.createCell(13);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Reimbursement");
						
						dataCell = dataRow.createCell(14);
				//		dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue("Reimbursement");
		
					    
					}
					
					
		
				
				
						HSSFPalette palette = sampleWorkbook.getCustomPalette();
						palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
				
			
					
				FileOutputStream fileOutputStream = new FileOutputStream(path, true);
				sampleWorkbook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
				success = true;
				
						
				
			} catch (FileNotFoundException e){
				logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
			} catch (IOException e){
				logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
			}
			if (success) return fileName;
		else return "error";
		
		}
	public String generateReimbursementInExcelNONHDFC(List<String[]> list, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("APPRAISAL HR REPORTS");
			String columns[] = new String[] { "ID", "Req Id", "Employee Id", "Employee Name", "Total Amount", "Currency Type", " Requested Date", "Status", "Department", "Division", "Project Name", "Cheque No/Comments" };
			sampleDataSheet.setColumnWidth(0, 3000);
			int k = 1;
			for (; k < columns.length; k++)
				sampleDataSheet.setColumnWidth(k, 4000);
			sampleDataSheet.setColumnWidth(3, 9000);
			sampleDataSheet.setColumnWidth(columns.length, 9000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(25);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			int j = 1;
			for (String[] reports : list){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(20);
				HSSFCell dataCell = null;
				for (String report : reports){
					dataCell = dataRow.createCell(i++);
					if (i == 1 || i == 3 || i == 5){
						dataCell.setCellType(Cell.CELL_TYPE_NUMERIC);
						dataCell.setCellValue(new Double(report));
					} else dataCell.setCellValue(report);
					dataCell.setCellStyle(cellStyle);
				}
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}
	

	public String generateDelayedTsSubmission(List<TimesheetDelayedBean> delayedBeanList, String path, String fromDate, String endDate) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("Delayed timesheet submission " + fromDate + "_TO_" + endDate);
			String columns[] = new String[] { "EMP_ID", "EMP_NAME", "PROJECT", "REPORTING_MGR" };
			for (int k = 0; k < columns.length; k++)
				sampleDataSheet.setColumnWidth(k, 7000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(40);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			int j = 1;
			for (Iterator<TimesheetDelayedBean> iter = delayedBeanList.iterator(); iter.hasNext();){
				TimesheetDelayedBean bean = iter.next();
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(30);
				HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(bean.getEmpId());
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(bean.getEmpName());
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(bean.getProjectName());
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(bean.getRmName());
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	public String generatePerdiemReportInExcel(List<String[]> perdiemReportBean, String path, String term) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet(term);
			//EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, PERDIEM, CURRENCY_TYPE, PAYABLE_DAYS, TOTAL, PERDIEM_FROM, PERDIEM_TO, ACC_NO, BANK_NAME
			String columns[] = new String[] { "EMP ID", "EMP NAME", "CLIENT NAME", "PERDIEM", "CURRENCY TYPE", "PAYABLE DAYS", "TOTAL AMOUNT", "PERDIEM FROM", "PERDIEM TO", "ACCOUNT NO", "BANK NAME" };
			// SET COLUMN WIDTHS
			int k = 0;
			sampleDataSheet.setColumnWidth(k++, 2000);
			sampleDataSheet.setColumnWidth(k++, 6000);
			sampleDataSheet.setColumnWidth(k++, 6000);
			sampleDataSheet.setColumnWidth(k++, 2500);
			sampleDataSheet.setColumnWidth(k++, 3000);
			sampleDataSheet.setColumnWidth(k++, 2500);
			sampleDataSheet.setColumnWidth(k++, 2500);
			sampleDataSheet.setColumnWidth(k++, 4000);
			sampleDataSheet.setColumnWidth(k++, 4000);
			sampleDataSheet.setColumnWidth(k++, 4000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(40);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 1;
			for (String[] row : perdiemReportBean){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(Integer.parseInt(row[i++]));
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(row[i++]);
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(row[i++]);
				dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(commStyle1);
				dataCell.setCellValue(Double.parseDouble(row[i++]));
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
				dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(Double.parseDouble(row[i++]));
				dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(commStyle1);
				dataCell.setCellValue(Double.parseDouble(row[i++]));
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}
	//Non Hdfc
	public String generatePerdiemReportInExcelNONHDFC(List<String[]> perdiemReportBean, String path, String term) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		//HSSFSheet FUND_TRANSFER=null;
	//	HSSFSheet sheet1= workBook.createSheet("sheet1");
		//HSSFSheet sheet1=sampleWorkbook.createSheet("Sheet1");
		
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND_TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE_PRINT");
			HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT_PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER_PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		
			
		

			String columns [] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
            String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
            String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
            String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
            String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
            String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
            
			
			
			
			// sheet 5
	
			int u=0;
			int columnWidth5[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 5000, 4500, 8000,6000,7000,4000 };
			for (int width : columnWidth5)
				SEFTEFT.setColumnWidth(u++, width);
			
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank5 = setHeaderStyle1(sampleWorkbook, true);
				headerRow5.setHeightInPoints(120);
				
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList5 = new CellRangeAddressList(1,100, 0, 0);
				 DVConstraint dvConstraint5 = DVConstraint
			                .createExplicitListConstraint(new String[] { "S"});
				 HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,
			                dvConstraint5);
				 dataValidation5.setSuppressDropDownArrow(false);
				 SEFTEFT.addValidationData(dataValidation5);
				 
				int u1 = 0;
				for (String columnNames : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(u1++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnNames));
					String s5=headerCell5.getStringCellValue();
					
					//TO BOLD the BLANK field
					
					if(s5.equals("BLANK")){
						headerCell5.setCellStyle(headerStyleblank5);
						headerCell5.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
					}
		}
				
			//sheet 4
			
	
				int t=0;
				int columnWidth4[] = new int[] { 4000, 2500, 6000, 5000, 8000, 3500, 3500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 4000, 4000, 2500, 2500, 8000,6000,7000,4000  };
				for (int width : columnWidth4)
					PAYORDERPRINT.setColumnWidth(t++, width);
			
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank4 = setHeaderStyle1(sampleWorkbook, true);
				headerRow4.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList4 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint4 = DVConstraint
			                .createExplicitListConstraint(new String[] { "H"});
				 HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,
			                dvConstraint4);
				 dataValidation4.setSuppressDropDownArrow(false);
				 PAYORDERPRINT.addValidationData(dataValidation4);
				 
				int t1 = 0;
				for (String columnNames : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(t1++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnNames));
					String s4=headerCell4.getStringCellValue();
					//System.out.println(s);
					if(s4.equals("BLANK")){
						headerCell4.setCellStyle(headerStyleblank4);
						headerCell4.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
						
					}
				}
			
			
			
			
			//sheet 3
	
				int p=0;
				int columnWidth3[] = new int[] { 4000, 2500, 6000, 5000, 8000, 3500, 3500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 3500, 2500, 2500, 8000,6000,7000,4000  };
				for (int width : columnWidth3)
					DRAFTPRINT.setColumnWidth(p++, width);
			
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank3 = setHeaderStyle1(sampleWorkbook, true);
				headerRow3.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList3 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint3 = DVConstraint
			                .createExplicitListConstraint(new String[] {"D"});
				 HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,
			                dvConstraint3);
				 dataValidation3.setSuppressDropDownArrow(false);
				 DRAFTPRINT.addValidationData(dataValidation3);
				 
				int p1 = 0;
				for (String columnNames : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(p1++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnNames));
					String s3=headerCell3.getStringCellValue();
					//System.out.println(s);
					if(s3.equals("BLANK")){
						headerCell3.setCellStyle(headerStyleblank3);
						headerCell3.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
					}
					
				}
			
			
			
			
			//sheet 2
	
				int q=0;
				int columnWidth2[] = new int[] { 4000, 2500, 6000, 5000, 8000, 3500, 3500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 3500, 2500, 2500, 8000,6000,7000,4000  };
				for (int width : columnWidth2)
					CHEQUEPRINT.setColumnWidth(q++, width);
			
				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank2 = setHeaderStyle1(sampleWorkbook, true);
				headerRow2.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList2 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint2 = DVConstraint
			                .createExplicitListConstraint(new String[] {"C"});
				 HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,
			                dvConstraint2);
				 dataValidation2.setSuppressDropDownArrow(false);
				 CHEQUEPRINT.addValidationData(dataValidation2);
				 
				int q1 = 0;
				for (String columnNames : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(q1++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnNames));
					String s2=headerCell2.getStringCellValue();
					//System.out.println(s);
					if(s2.equals("BLANK")){
						headerCell2.setCellStyle(headerStyleblank2);
						headerCell2.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
					}
					
					
				}
				
			
			
			//Sheet 1
	
				int l=0;
				int columnWidth1[] = new int[] { 4000, 4000, 6000, 5000, 8000, 2500, 2500, 5000, 5000, 5000, 5000, 5000, 3500, 3500, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 2500, 2500, 2500,2500,7000,4000  };
				for (int width : columnWidth1)
					FUNDTRANSFER.setColumnWidth(l++, width);
		
			HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
			HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
			HSSFCellStyle headerStyleblank1 = setHeaderStyle1(sampleWorkbook, true);
			headerRow1.setHeightInPoints(120);
			
			// For add dropdown menu in xls
			CellRangeAddressList addressList = new CellRangeAddressList(1, 100, 0, 0);
			 DVConstraint dvConstraint = DVConstraint
		                .createExplicitListConstraint(new String[] {"I"});
			 HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,
		                dvConstraint);
			 dataValidation.setSuppressDropDownArrow(false);
			 FUNDTRANSFER.addValidationData(dataValidation);
			 
			
			int l1 = 0;
			for (String columnNames : columnsz){
				HSSFCell headerCell1 = headerRow1.createCell(l1++);
				headerCell1.setCellStyle(headerStyle1);
				headerCell1.setCellValue(new HSSFRichTextString(columnNames));
				String s1=headerCell1.getStringCellValue();
				//System.out.println(s);
				if(s1.equals("BLANK")){
					headerCell1.setCellStyle(headerStyleblank1);
					headerCell1.setCellValue(new HSSFRichTextString(columnNames));
					//setHeaderStyle1
				}	
			}
			
			//main Sheet
				// SET COLUMN WIDTHS 
			
			int i3=0;
			int columnWidth[] = new int[] { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4000, 4000,4000, 4000, 4000, 4000, 4000, 2500, 4000, 2500, 4000, 5000,5000,6000,4000  };
			for (int width : columnWidth)
				sampleDataSheet.setColumnWidth(i3++, width);
			
				CellRangeAddressList addressListmain = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraintmain = DVConstraint
			                .createExplicitListConstraint(new String[] { "N", "R" });
				 HSSFDataValidation dataValidationmain = new HSSFDataValidation(addressListmain,
			                dvConstraintmain);
				 dataValidationmain.setSuppressDropDownArrow(false);
				
				 sampleDataSheet.addValidationData(dataValidationmain);
				 
		
	
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			HSSFCellStyle headerStyleblank = setHeaderStyle1(sampleWorkbook, true);
			headerRow.setHeightInPoints(120);
			
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				
				headerCell.setCellValue(new HSSFRichTextString(columnName));
				
				String s=headerCell.getStringCellValue();
				//TO BOLD the BLANK field
				
				if(s.equals("BLANK")){
					headerCell.setCellStyle(headerStyleblank);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
					//setHeaderStyle1
				}
				
			}
		
			
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			
			int j = 1;
			for (String[] row : perdiemReportBean){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
						
				
				dataCell = dataRow.createCell(0);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("N");
				
				
				
			/*	dataCell = dataRow.createCell(1);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[0]);*/
				

				dataCell = dataRow.createCell(2);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[9]);
				
				
				dataCell = dataRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
				cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
				dataCell.setCellStyle(cellStyle1);
		//		dataCell.setCellValue(row[6]);
				String amount = row[6];
				dataCell.setCellValue(amount != null ? Double.valueOf(amount):0);
				
				
				dataCell = dataRow.createCell(25);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[10]);
				
				
				dataCell = dataRow.createCell(4);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[1]);
				
				
				dataCell = dataRow.createCell(22);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[13]);
				
				dataCell = dataRow.createCell(24);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[12]);
				
				
				
				
				dataCell = dataRow.createCell(27);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[11]);
				
				
				/*dataCell = dataRow.createCell(24);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("IFIC NUMBER");
				*/
				dataCell = dataRow.createCell(7);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(8);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(9);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(10);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(11);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("560080");
				dataCell = dataRow.createCell(12);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Perdiem");
				dataCell = dataRow.createCell(13);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Perdiem");
				dataCell = dataRow.createCell(14);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Perdiem");
				}
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
			} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	// HDFC
	public String generatePerdiemReportInExcelHdfc(List<String[]> perdiemReportBean, String path, String term) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
	
		
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND_TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE_PRINT");
			HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT_PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER_PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		

			String columns [] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
            String columnsz[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
            String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
            String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
            String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
            String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
            
			
			
		// sheet 5
			int u=0;
			int columnWidth5[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 5000, 4500, 8000,6000,7000,4000 };
			for (int width : columnWidth5)
				SEFTEFT.setColumnWidth(u++, width);
		
			
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank5 = setHeaderStyle1(sampleWorkbook, true);
				headerRow5.setHeightInPoints(120);
				
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList5 = new CellRangeAddressList(1,100, 0, 0);
				 DVConstraint dvConstraint5 = DVConstraint
			                .createExplicitListConstraint(new String[] { "S"});
				 HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,
			                dvConstraint5);
				 dataValidation5.setSuppressDropDownArrow(false);
				 SEFTEFT.addValidationData(dataValidation5);
				 
				int u1 = 0;
				for (String columnNames : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(u1++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnNames));
					String s5=headerCell5.getStringCellValue();
					
					//TO BOLD the BLANK field
					
					if(s5.equals("BLANK")){
						headerCell5.setCellStyle(headerStyleblank5);
						headerCell5.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
					}
		}
				
		//sheet 4
			
				int t=0;
				int columnWidth4[] = new int[] { 4000, 2500, 6000, 5000, 8000, 3500, 3500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 4000, 4000, 2500, 2500, 8000,6000,7000,4000  };
				for (int width : columnWidth4)
					PAYORDERPRINT.setColumnWidth(t++, width);
			
			
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank4 = setHeaderStyle1(sampleWorkbook, true);
				headerRow4.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList4 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint4 = DVConstraint
			                .createExplicitListConstraint(new String[] { "H"});
				 HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,
			                dvConstraint4);
				 dataValidation4.setSuppressDropDownArrow(false);
				 PAYORDERPRINT.addValidationData(dataValidation4);
				 
				int t1 = 0;
				for (String columnNames : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(t1++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnNames));
					String s4=headerCell4.getStringCellValue();
					//System.out.println(s);
					if(s4.equals("BLANK")){
						headerCell4.setCellStyle(headerStyleblank4);
						headerCell4.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
						
					}
				}
			
			
			
			
		//sheet 3
				int p=0;
				int columnWidth3[] = new int[] { 4000, 2500, 6000, 5000, 8000, 3500, 3500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 3500, 2500, 2500, 8000,6000,7000,4000  };
				for (int width : columnWidth3)
					DRAFTPRINT.setColumnWidth(p++, width);
				
			/*for(int p=0;p<columns3.length;p++){
				DRAFTPRINT.setColumnWidth(p, 4000);}*/
			
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank3 = setHeaderStyle1(sampleWorkbook, true);
				headerRow3.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList3 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint3 = DVConstraint
			                .createExplicitListConstraint(new String[] {"D"});
				 HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,
			                dvConstraint3);
				 dataValidation3.setSuppressDropDownArrow(false);
				 DRAFTPRINT.addValidationData(dataValidation3);
				 
				int p1 = 0;
				for (String columnNames : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(p1++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnNames));
					String s3=headerCell3.getStringCellValue();
					//System.out.println(s);
					if(s3.equals("BLANK")){
						headerCell3.setCellStyle(headerStyleblank3);
						headerCell3.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
					}
					
				}
			
			
			
			
		//sheet 2
				
				int q=0;
				int columnWidth2[] = new int[] { 4000, 2500, 6000, 5000, 8000, 3500, 3500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 3500, 2500, 2500, 8000,6000,7000,4000  };
				for (int width : columnWidth2)
					CHEQUEPRINT.setColumnWidth(q++, width);
		
				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle headerStyleblank2 = setHeaderStyle1(sampleWorkbook, true);
				headerRow2.setHeightInPoints(120);
				
								// For add dropdown menu in xls
								  
				CellRangeAddressList addressList2 = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraint2 = DVConstraint
			                .createExplicitListConstraint(new String[] {"C"});
				 HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,
			                dvConstraint2);
				 dataValidation2.setSuppressDropDownArrow(false);
				 CHEQUEPRINT.addValidationData(dataValidation2);
				 
				int q1 = 0;
				for (String columnNames : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(q1++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnNames));
					String s2=headerCell2.getStringCellValue();
					//System.out.println(s);
					if(s2.equals("BLANK")){
						headerCell2.setCellStyle(headerStyleblank2);
						headerCell2.setCellValue(new HSSFRichTextString(columnNames));
						//setHeaderStyle1
					}				
					
				}
				
			//main Sheet
				// SET COLUMN WIDTHS 
				int i3=0;
				int columnWidth[] = new int[] { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4000, 4000,4000, 4000, 4000, 4000, 4000, 2500, 4000, 2500, 4000, 5000,5000,6000,4000  };
				for (int width : columnWidth)
					sampleDataSheet.setColumnWidth(i3++, width);
		
			
				CellRangeAddressList addressListmain = new CellRangeAddressList(1, 100, 0, 0);
				 DVConstraint dvConstraintmain = DVConstraint
			                .createExplicitListConstraint(new String[] { "N", "R" });
				 HSSFDataValidation dataValidationmain = new HSSFDataValidation(addressListmain,
			                dvConstraintmain);
				 dataValidationmain.setSuppressDropDownArrow(false);
				
				 sampleDataSheet.addValidationData(dataValidationmain);
				 
	
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			HSSFCellStyle headerStyleblank = setHeaderStyle1(sampleWorkbook, true);
			HSSFCellStyle setHeaderStyleHyperLink=setHeaderStyleHyperLink(sampleWorkbook, true);
			headerRow.setHeightInPoints(120);
			
			
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				
				
				headerCell.setCellValue(new HSSFRichTextString(columnName));
				
				
				String s=headerCell.getStringCellValue();
				String s1=headerCell.getStringCellValue();
				
				//TO BOLD the BLANK field
				
				if(s.equals("BLANK")){
					headerCell.setCellStyle(headerStyleblank);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
					//setHeaderStyle1
				
				}
				else if (s1.equals("SUBMIT HERE")){
					HSSFHyperlink link =new HSSFHyperlink(HSSFHyperlink.LINK_URL);
					link.setAddress("http://www.");
					headerCell.setHyperlink(link);
					headerCell.setCellStyle(setHeaderStyleHyperLink);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
				}
				
			}
			
		//Sheet 1
			int l=0;
			int columnWidth1[] = new int[] { 4000, 4000, 6000, 5000, 8000, 2500, 2500, 5000, 5000, 5000, 5000, 5000, 3500, 3500, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 2500, 2500, 2500,2500,7000,4000  };
			for (int width : columnWidth1)
				FUNDTRANSFER.setColumnWidth(l++, width);
			
			
			HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
			HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
			HSSFCellStyle headerStyleblank1 = setHeaderStyle1(sampleWorkbook, true);
			headerRow1.setHeightInPoints(120);
			
			// For add dropdown menu in xls
			CellRangeAddressList addressList = new CellRangeAddressList(1, 100, 0, 0);
			 DVConstraint dvConstraint = DVConstraint
		                .createExplicitListConstraint(new String[] {"I"});
			 HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,
		                dvConstraint);
			 dataValidation.setSuppressDropDownArrow(false);
			 FUNDTRANSFER.addValidationData(dataValidation);
			 
			
			int l1 = 0;
			for (String columnNames : columnsz){
				HSSFCell headerCell1 = headerRow1.createCell(l1++);
				headerCell1.setCellStyle(headerStyle1);
				headerCell1.setCellValue(new HSSFRichTextString(columnNames));
				String s1=headerCell1.getStringCellValue();
				//System.out.println(s);
				if(s1.equals("BLANK")){
					headerCell1.setCellStyle(headerStyleblank1);
					headerCell1.setCellValue(new HSSFRichTextString(columnNames));
					//setHeaderStyle1
				}	
			}
			
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			
			int j = 1;
			for (String[] row : perdiemReportBean){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = FUNDTRANSFER.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
						
				
				dataCell = dataRow.createCell(0);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("I");
				
				
				
				dataCell = dataRow.createCell(1);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[0]);
				
				dataCell = dataRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
				cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
				dataCell.setCellStyle(cellStyle1);
		//		dataCell.setCellValue(row[6]);
				String amount = row[6];
				dataCell.setCellValue(amount != null ? Double.valueOf(amount):0);
				
				

				dataCell = dataRow.createCell(2);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[9]);
				
				dataCell = dataRow.createCell(4);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[1]);
	
				
			/*	dataCell = dataRow.createCell(24);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[12]);*/
				
				dataCell = dataRow.createCell(22);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[14]);
				
				
				dataCell = dataRow.createCell(27);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[11]);
				
				
				/*dataCell = dataRow.createCell(24);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("IFIC NUMBER");
				*/
				dataCell = dataRow.createCell(7);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(8);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(9);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(10);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				dataCell = dataRow.createCell(11);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("560080");
				dataCell = dataRow.createCell(12);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Perdiem");
				dataCell = dataRow.createCell(13);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Perdiem");
				dataCell = dataRow.createCell(14);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Perdiem");
				}
			
			int formEnd = j;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = FUNDTRANSFER.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
			} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	

	
	
	
	
	public String generateBonusReportInExcel(List<String[]> bonusReportBean, String path, String term) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet(term);
			//EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MONTH, TOTAL,ACCOUNT NO, BANK_NAME
			String columns[] = new String[] { "EMP ID", "EMP NAME", "CLIENT NAME", "QUATERELY BONUS","COMPANY BONUS", "CURRENCY TYPE", "MONTH", "TOTAL AMOUNT", "ACCOUNT NO", "BANK NAME" };
			// SET COLUMN WIDTHS
			int k = 0;
			sampleDataSheet.setColumnWidth(k++, 2000);
			sampleDataSheet.setColumnWidth(k++, 6000);
			sampleDataSheet.setColumnWidth(k++, 6000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			sampleDataSheet.setColumnWidth(k++, 2500);
			sampleDataSheet.setColumnWidth(k++, 2500);
			sampleDataSheet.setColumnWidth(k++, 4000);
			sampleDataSheet.setColumnWidth(k++, 5000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(40);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 1;
			for (String[] row : bonusReportBean){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(Integer.parseInt(row[i++]));
				
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(row[i++]);
				
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(row[i++]);
				
				dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(commStyle1);
				dataCell.setCellValue(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(row[i++]))));
				
				dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(row[i++]))));
				
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
				
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
				
				dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(commStyle1);
				dataCell.setCellValue(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(row[i++]))));
				
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);
				
				dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[i++]);	
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}
	// for HDFC
	
	public String generateBonusReportInExcelHDFC(List<String[]> bonusReportBean, String path, String term) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE PRINT");
		    HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		
			
			//EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MONTH, TOTAL,ACCOUNT NO, BANK_NAME
		//	String columns[] = new String[] { "EMP ID", "EMP NAME", "CLIENT NAME", "QUARTERLY BONUS","COMPANY BONUS", "CURRENCY TYPE", "MONTH", "TOTAL AMOUNT", "ACCOUNT NO", "BANK NAME" };
	
			//new
			String columns [] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
			String columns1[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
			String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
			String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
			String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
			String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
			
			//for columns1[]
			
		/*	for(int k1=0;k1<columns1.length;k1++){
				FUNDTRANSFER.setColumnWidth(k1, 8000);
				} */
			
			 int u=0;
			 int columnWidth1[] = new int[]  { 5000, 5000, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 2500, 2500, 2500,2500,7000,8000 };

		//	 int columnWidth1[] = new int[]  { 4000, 5000, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 5000, 4500, 8000,6000,7000,8000 };
			 for (int width : columnWidth1)
	               FUNDTRANSFER.setColumnWidth(u++, width);

			    HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
				HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle6 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink6 = hyperlink(sampleWorkbook, true);
				headerRow1.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList1 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint1 = DVConstraint.createExplicitListConstraint(new String[] { "I"});
	             HSSFDataValidation dataValidation1 = new HSSFDataValidation(addressList1,dvConstraint1);
	             dataValidation1.setSuppressDropDownArrow(false);
	             FUNDTRANSFER.addValidationData(dataValidation1);
	             
	             int a=0;
	             for (String columnName : columns1){
					HSSFCell headerCell1 = headerRow1.createCell(a++);
					headerCell1.setCellStyle(headerStyle1);
					headerCell1.setCellValue(new HSSFRichTextString(columnName));
					String s6 = headerCell1.getStringCellValue();
					String sf = headerCell1.getStringCellValue();
					 if(s6.equals("BLANK")){
							
							headerCell1.setCellStyle(heCellStyle6);
							headerCell1.setCellValue(new HSSFRichTextString(columnName));}
					 
					 if(sf.equals("Click Here To Submit The Details")){
						    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
							link.setAddress("https://www.");
							headerCell1.setHyperlink(link);
							headerCell1.setCellStyle(hyperlink6);
							headerCell1.setCellValue(new HSSFRichTextString(columnName));}
}
				
				
				
				// for columns2[]
				
				/*for(int k2=0;k2<columns2.length;k2++){
					CHEQUEPRINT.setColumnWidth(k2, 8000);
				}*/
				
				int u1=0;
				int columnWidth2[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 5000, 5000, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 2500, 2500, 4000, 2500, 2500, 8000,6000,7000,8000 }; 
		//		int columnWidth2[] = new int[]   { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 5000, 4500, 8000,6000,7000,8000 };
				 for (int width : columnWidth2)
					 CHEQUEPRINT.setColumnWidth(u1++, width);

				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle5 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink5 = hyperlink(sampleWorkbook, true);
				headerRow2.setHeightInPoints(90);
				// for drop down in XLS
				CellRangeAddressList addressList2 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint2 = DVConstraint.createExplicitListConstraint(new String[] { "C"});
	             HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,dvConstraint2);
	             dataValidation2.setSuppressDropDownArrow(false);
	             CHEQUEPRINT.addValidationData(dataValidation2);
				
				int b=0;
				for (String columnName : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(b++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnName));
					String s5 = headerCell2.getStringCellValue();
					String se=headerCell2.getStringCellValue();
					 if(s5.equals("BLANK")){
							headerCell2.setCellStyle(heCellStyle5);
							headerCell2.setCellValue(new HSSFRichTextString(columnName));
				}
					 
					 if(se.equals("Click Here To Submit The Details")){
						    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
							link.setAddress("http://www.");
							headerCell2.setHyperlink(link);
							headerCell2.setCellStyle(hyperlink5);
							headerCell2.setCellValue(new HSSFRichTextString(columnName));
				}
				}
				
			//	for columns3[]
				
				
				/*for(int k3=0;k3<columns3.length;k3++){
					DRAFTPRINT.setColumnWidth(k3, 8000);
				}
				*/
				
				 int u2=0;
				 int columnWidth3[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 5000, 5000, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 2500, 2500, 8000,6000,7000,8000 };
		//		 int columnWidth3[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 5000, 4500, 8000,6000,7000,8000 };
				 for (int width : columnWidth3)
					 DRAFTPRINT.setColumnWidth(u2++, width);
				
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle4 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink4 = hyperlink(sampleWorkbook, true);
				headerRow3.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList3 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint3 = DVConstraint.createExplicitListConstraint(new String[] { "D"});
	             HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,dvConstraint3);
	             dataValidation3.setSuppressDropDownArrow(false);
	             DRAFTPRINT.addValidationData(dataValidation3);
				int c=0;
				for (String columnName : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(c++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnName));
					String s4 = headerCell3.getStringCellValue();
					String sd = headerCell3.getStringCellValue();
					 if(s4.equals("BLANK")){
							
							headerCell3.setCellStyle(heCellStyle4);
							headerCell3.setCellValue(new HSSFRichTextString(columnName));
				}
					 if(sd.equals("Click Here To Submit The Details")){
						    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
							link.setAddress("http://www.");
							headerCell3.setHyperlink(link);
							headerCell3.setCellStyle(hyperlink4);
							headerCell3.setCellValue(new HSSFRichTextString(columnName));	 
				}
				}
			//	for columns4[]
				
				/*for(int k4=0;k4<columns4.length;k4++){
					PAYORDERPRINT.setColumnWidth(k4, 8000);
				}
				*/
				int u3=0;
				int columnWidth4[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 5000, 5000, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 2500, 2500, 8000,6000,7000,8000 };

		//		 int columnWidth4[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 5000, 4500, 8000,6000,7000,8000 };
				 for (int width : columnWidth4)
					 PAYORDERPRINT.setColumnWidth(u3++, width);
				
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle3 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink3 = hyperlink(sampleWorkbook, true);
				headerRow4.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList4 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint4 = DVConstraint.createExplicitListConstraint(new String[] { "H"});
	             HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,dvConstraint4);
	             dataValidation4.setSuppressDropDownArrow(false);
	             PAYORDERPRINT.addValidationData(dataValidation4);
				
				int d=0;
			   for (String columnName : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(d++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnName));
					String s3=headerCell4.getStringCellValue();
					String sc=headerCell4.getStringCellValue();
					
                          if(s3.equals("BLANK")){
						
						headerCell4.setCellStyle(heCellStyle3);
						headerCell4.setCellValue(new HSSFRichTextString(columnName));
						
					}
                          if(sc.equals("Click Here To Submit The Details")){
                        	HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
          					link.setAddress("http://www.");
          					headerCell4.setHyperlink(link);
      						headerCell4.setCellStyle(hyperlink3);
      						headerCell4.setCellValue(new HSSFRichTextString(columnName));
      						 
                          }
				}
				
			//	for columns5[]
				
				/*for(int k5=0;k5<columns5.length;k5++){
					SEFTEFT.setColumnWidth(k5,8000);
				}
		*/
				int u4=0;
				int columnWidth5[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 5000, 4500, 8000,6000,7000,8000 };
			
				 for (int width : columnWidth5)
					 SEFTEFT.setColumnWidth(u4++, width);
				
				
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle2 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink2 = hyperlink(sampleWorkbook, true);
				headerRow5.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList5 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint5 = DVConstraint.createExplicitListConstraint(new String[] { "S"});
	             HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,dvConstraint5);
	             dataValidation5.setSuppressDropDownArrow(false);
	             SEFTEFT.addValidationData(dataValidation5);
				int e=0;
				for (String columnName : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(e++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnName));
					String s2=headerCell5.getStringCellValue();
					String sb=headerCell5.getStringCellValue();
					
					if(s2.equals("BLANK")){
						
						headerCell5.setCellStyle(heCellStyle2);
						headerCell5.setCellValue(new HSSFRichTextString(columnName));
						
					}
					if(sb.equals("Click Here To Submit The Details")){
						
						HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
						link.setAddress("http://www.");
						headerCell5.setHyperlink(link);
						headerCell5.setCellStyle(hyperlink2);
						headerCell5.setCellValue(new HSSFRichTextString(columnName));
					}	
				
				
				}

				
				 int u6=0;
				 int columnWidth6[] = new int[]  { 5000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 2500, 4500, 8000,6000,7000,8000 };
			
				 for (int width : columnWidth6)
					 sampleDataSheet.setColumnWidth(u6++, width);	
				
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			HSSFCellStyle heCellStyle1=setHeaderStyle1(sampleWorkbook, true);//added
			HSSFCellStyle hyperlink1 = hyperlink(sampleWorkbook, true);
			headerRow.setHeightInPoints(90);
			
			// for drop down in XLS
			CellRangeAddressList addressList = new CellRangeAddressList(1,100, 0, 0);
             DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(new String[] { "N", "R"  });
             HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,dvConstraint);
             dataValidation.setSuppressDropDownArrow(false);
             sampleDataSheet.addValidationData(dataValidation);
			
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
				String s1= headerCell.getStringCellValue();//added
				String sa= headerCell.getStringCellValue();//added
				
				if(s1.equals("BLANK")){
					
					headerCell.setCellStyle(heCellStyle1);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
				}
				
                 if(sa.equals("Click Here To Submit The Details")){
                	 
					HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
					link.setAddress("http://www.");
					headerCell.setHyperlink(link);
                	headerCell.setCellStyle(hyperlink1);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
				}
				
			}
			
            
            
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			
			int j = 1;
			for (String[] row : bonusReportBean){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = FUNDTRANSFER.createRow(j++);// to move all hdfc data fom NEFT-RTGS TO FUNDTRANSFER 
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(Cell.CELL_TYPE_NUMERIC);
			
				
				
				
				dataCell = dataRow.createCell(1);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[0]);
				
				dataCell = dataRow.createCell(0);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("I");
				
				
				
				
				
				dataCell = dataRow.createCell(27);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[10]);
				
			/*	dataCell = dataRow.createCell(27);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[10]);*/
				
				
				dataCell = dataRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
				cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
				dataCell.setCellStyle(cellStyle1);
				String amount = row[7];
				dataCell.setCellValue(amount != null ? Double.valueOf(amount):0);
				
				
				dataCell = dataRow.createCell(22);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[9]);
				
				
			
				dataCell = dataRow.createCell(4);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[1]);
					
				dataCell = dataRow.createCell(2);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[8]);
				
				
				
			/*	dataCell = dataRow.createCell(25);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[9]);*/
				
				
				dataCell = dataRow.createCell(14);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("QPLB");
				

				dataCell = dataRow.createCell(13);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("QPLB");
				

				dataCell = dataRow.createCell(12);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("QPLB");
				
				
				dataCell = dataRow.createCell(7);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				

				dataCell = dataRow.createCell(8);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				

				dataCell = dataRow.createCell(9);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				
				
				dataCell = dataRow.createCell(10);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				
				dataCell = dataRow.createCell(11);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("560080");
				
				
				
			}
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = FUNDTRANSFER.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
			} catch (Exception e){
			e.printStackTrace();
			logger.error("xls creation exception", e);
		}
		return "";
	}
	
	// for NON HDFC
	
	
	public String generateBonusReportInExcelNONHDFC(List<String[]> bonusReportBean, String path, String term) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("NEFT-RTGS");
			HSSFSheet FUNDTRANSFER=sampleWorkbook.createSheet("FUND TRANSFER");
			HSSFSheet CHEQUEPRINT=sampleWorkbook.createSheet("CHEQUE PRINT");
		    HSSFSheet DRAFTPRINT=sampleWorkbook.createSheet("DRAFT PRINT");
			HSSFSheet PAYORDERPRINT=sampleWorkbook.createSheet("PAYORDER PRINT");
			HSSFSheet SEFTEFT=sampleWorkbook.createSheet("SEFT-EFT");
		
			
			//EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MONTH, TOTAL,ACCOUNT NO, BANK_NAME
		//	String columns[] = new String[] { "EMP ID", "EMP NAME", "CLIENT NAME", "QUARTERLY BONUS","COMPANY BONUS", "CURRENCY TYPE", "MONTH", "TOTAL AMOUNT", "ACCOUNT NO", "BANK NAME" };
	
			//new
			String columns [] = new String[] { "Transaction Type NEFT/RTGS\n A\n 1\n Mandatory\n N for NEFT R for RTGS","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices Pvt Ltd","BLANK","BLANK", "Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 70\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Transaction Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","IFSC Code\n N\n 15\n Mandatory\n PNB","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
			String columns1[] = new String[] { "Transaction Type Fund Transfer\n A\n 1\n Mandatory\n I-Fund Transfer","Beneficiary Code\n A\n 13\n Mandatory\n Example : BIL02","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Optional\n Shah Enterprices Pvt Ltd","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n PIN 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789"," Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006 ","BLANK","BLANK","BLANK","BLANK","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
			String columns2[] = new String[] { "Transaction Type Cheque Print\n A\n 1\n Mandatory\n C-Cheque Print","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Optional\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Cheque Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional ","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary Email Id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
			String columns3[] = new String[] { "Transaction Type Draft Printing\n A\n 1\n Mandatory\n D-Draft Printing","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 200\n Mandatory\n Shah Enterprices Pvt Ltd","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
			String columns4[] = new String[] { "Transaction Type Pay Order Print\n A\n 1\n Mandatory\n H-PayOrder","BLANK","Beneficiary Account Number\n A\n 25\n Optional\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 1500.59","Beneficiary Name\n C\n 35\n Optional\n Shah Enterprices","Drawee Location\n A\n 20\n Mandatory\n MUM (Cash in Code) Mandatory in case of DD","Print Location\n A\n 20\n Mandatory\n MUM (Cash in Code)","Bene Address 1\n A\n 35\n Mandatory\n 26 A Properties (35 Mandatory in case Of Payorder)","Bene Address 2\n A\n 35\n Mandatory\n Chandivali","Bene Address 3\n A\n 30\n Mandatory\n Off Saki Vihar Road","Bene Address 4\n A\n 30\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","Cheque Number\n N\n 12\n Mandatory\n 999999","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","BLANK","BLANK","Bene Bank Name\n A\n 100\n Optional","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n Mandatory","Click Here To Submit The Details"};
			String columns5[] = new String[] { "Transaction Type SEFT/EFT\n A\n 1\n Mandatory\n S-SEFT/EFT","BLANK","Beneficiary Account Number\n A\n 25\n Mandatory\n 9999999999999","Instrument Amount\n N\n 20(17.2)\n Mandatory\n 150.59","Beneficiary Name\n C\n 40\n Mandatory\n Shah Enterprices","BLANK","BLANK","Bene Address 1\n A\n 70\n Optional\n 26 A Properties","Bene Address 2\n A\n 70\n Optional\n Chandivali","Bene Address 3\n A\n 70\n Optional\n Off Saki Vihar Road","Bene Address 4\n A\n 70\n Optional\n Andheri","Bene Address 5\n A\n 20\n Optional\n 400072","Instruction Reference Number\n C\n 20\n Optional\n 123456789","Customer Reference Number\n C\n 20\n Optional\n 123456789","Payment details 1\n C\n 30\n Optional\n A-1756/25","Payment details 2\n C\n 30\n Optional","Payment details 3\n C\n 30\n Optional","Payment details 4\n C\n 30\n Optional","Payment details 5\n C\n 30\n Optional","Payment details 6\n C\n 30\n Optional","Payment details 7\n C\n 30\n Optional","BLANK","Chq/Trn Date\n D\n 10\n Mandatory\n 28/01/2006","MICR Number\n N\n 15\n Mandatory\n 400002002","IFSC Code\n N\n 15\n O/M","Bene Bank Name\n A\n 40\n Mandatory\n State Bank Of India","Bene Bank Branch Name\n A\n 40\n Optional\n Nariman Point","Beneficiary email id\n A\n 100\n M\n Email id where the advice needs to be send for RBI payments","Click Here To Submit The Details"};
			
			//for columns1[]
			
			/*for(int k1=0;k1<columns1.length;k1++){
				FUNDTRANSFER.setColumnWidth(k1, 8000);
				}
			*/
			
			int u=0;
			 int columnWidth1[] = new int[]  { 5000, 5000, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 2500, 2500, 2500,2500,7000,8000 };
			 for (int width : columnWidth1)
	               FUNDTRANSFER.setColumnWidth(u++, width);
			
			
				HSSFRow headerRow1 = FUNDTRANSFER.createRow(0);
				HSSFCellStyle headerStyle1 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle6 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink6 = hyperlink(sampleWorkbook, true);
				headerRow1.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList1 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint1 = DVConstraint.createExplicitListConstraint(new String[] { "I"});
	             HSSFDataValidation dataValidation1 = new HSSFDataValidation(addressList1,dvConstraint1);
	             dataValidation1.setSuppressDropDownArrow(false);
	             FUNDTRANSFER.addValidationData(dataValidation1);
	             
	             int a=0;
	             for (String columnName : columns1){
					HSSFCell headerCell1 = headerRow1.createCell(a++);
					headerCell1.setCellStyle(headerStyle1);
					headerCell1.setCellValue(new HSSFRichTextString(columnName));
					String s6 = headerCell1.getStringCellValue();
					String sf = headerCell1.getStringCellValue();
					 if(s6.equals("BLANK")){
							
							headerCell1.setCellStyle(heCellStyle6);
							headerCell1.setCellValue(new HSSFRichTextString(columnName));}
					 
					 if(sf.equals("Click Here To Submit The Details")){
						    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
							link.setAddress("http://www.");
							headerCell1.setHyperlink(link);
							headerCell1.setCellStyle(hyperlink6);
							headerCell1.setCellValue(new HSSFRichTextString(columnName));}
}
				
				
				
				// for columns2[]
				
				/*for(int k2=0;k2<columns2.length;k2++){
					CHEQUEPRINT.setColumnWidth(k2, 8000);
				}*/
				
				 int u1=0;
				 int columnWidth2[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 5000, 5000, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 2500, 2500, 4000, 5000, 4500, 8000,6000,7000,8000 };
				 for (int width : columnWidth2)
					 CHEQUEPRINT.setColumnWidth(u1++, width);
				
				
				HSSFRow headerRow2 = CHEQUEPRINT.createRow(0);
				HSSFCellStyle headerStyle2 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle5 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink5 = hyperlink(sampleWorkbook, true);
				headerRow2.setHeightInPoints(90);
				// for drop down in XLS
				CellRangeAddressList addressList2 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint2 = DVConstraint.createExplicitListConstraint(new String[] { "C"});
	             HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2,dvConstraint2);
	             dataValidation2.setSuppressDropDownArrow(false);
	             CHEQUEPRINT.addValidationData(dataValidation2);
				
				int b=0;
				for (String columnName : columns2){
					HSSFCell headerCell2 = headerRow2.createCell(b++);
					headerCell2.setCellStyle(headerStyle2);
					headerCell2.setCellValue(new HSSFRichTextString(columnName));
					String s5 = headerCell2.getStringCellValue();
					String se=headerCell2.getStringCellValue();
					 if(s5.equals("BLANK")){
							headerCell2.setCellStyle(heCellStyle5);
							headerCell2.setCellValue(new HSSFRichTextString(columnName));
				}
					 
					 if(se.equals("Click Here To Submit The Details")){
						    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
							link.setAddress("http://www.");
							headerCell2.setHyperlink(link);
							headerCell2.setCellStyle(hyperlink5);
							headerCell2.setCellValue(new HSSFRichTextString(columnName));
				}
				}
				
			//	for columns3[]
				
				/*
				for(int k3=0;k3<columns3.length;k3++){
					DRAFTPRINT.setColumnWidth(k3, 8000);
				}*/
				 int u2=0;
				 int columnWidth3[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 5000, 5000, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 2500, 2500, 8000,6000,7000,8000 };
				 for (int width : columnWidth3)
					 DRAFTPRINT.setColumnWidth(u2++, width);
				
				
				HSSFRow headerRow3 = DRAFTPRINT.createRow(0);
				HSSFCellStyle headerStyle3 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle4 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink4 = hyperlink(sampleWorkbook, true);
				headerRow3.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList3 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint3 = DVConstraint.createExplicitListConstraint(new String[] { "D"});
	             HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3,dvConstraint3);
	             dataValidation3.setSuppressDropDownArrow(false);
	             DRAFTPRINT.addValidationData(dataValidation3);
				int c=0;
				for (String columnName : columns3){
					HSSFCell headerCell3 = headerRow3.createCell(c++);
					headerCell3.setCellStyle(headerStyle3);
					headerCell3.setCellValue(new HSSFRichTextString(columnName));
					String s4 = headerCell3.getStringCellValue();
					String sd = headerCell3.getStringCellValue();
					 if(s4.equals("BLANK")){
							
							headerCell3.setCellStyle(heCellStyle4);
							headerCell3.setCellValue(new HSSFRichTextString(columnName));
				}
					 if(sd.equalsIgnoreCase("Click Here To Submit The Details")){
						    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
							link.setAddress("http://www.");
							headerCell3.setHyperlink(link);
							headerCell3.setCellStyle(hyperlink4);
							headerCell3.setCellValue(new HSSFRichTextString(columnName));	 
				}
				}
			//	for columns4[]
				
				/*for(int k4=0;k4<columns4.length;k4++){
					PAYORDERPRINT.setColumnWidth(k4, 8000);
				}*/
				
				 int u3=0;
				 int columnWidth4[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 3500, 4000, 2500, 2500, 8000,6000,7000,8000 };
				 for (int width : columnWidth4)
					 PAYORDERPRINT.setColumnWidth(u3++, width);
				
				
				HSSFRow headerRow4 = PAYORDERPRINT.createRow(0);
				HSSFCellStyle headerStyle4 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle3 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink3 = hyperlink(sampleWorkbook, true);
				headerRow4.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList4 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint4 = DVConstraint.createExplicitListConstraint(new String[] { "H"});
	             HSSFDataValidation dataValidation4 = new HSSFDataValidation(addressList4,dvConstraint4);
	             dataValidation4.setSuppressDropDownArrow(false);
	             PAYORDERPRINT.addValidationData(dataValidation4);
				
				int d=0;
			   for (String columnName : columns4){
					HSSFCell headerCell4 = headerRow4.createCell(d++);
					headerCell4.setCellStyle(headerStyle4);
					headerCell4.setCellValue(new HSSFRichTextString(columnName));
					String s3=headerCell4.getStringCellValue();
					String sc=headerCell4.getStringCellValue();
					
                          if(s3.equals("BLANK")){
						
						headerCell4.setCellStyle(heCellStyle3);
						headerCell4.setCellValue(new HSSFRichTextString(columnName));
						
					}
                          if(sc.equals("Click Here To Submit The Details")){
                        	HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
          					link.setAddress("http://www.");
          					headerCell4.setHyperlink(link);
      						headerCell4.setCellStyle(hyperlink3);
      						headerCell4.setCellValue(new HSSFRichTextString(columnName));
      						 
                          }
				}
				
			//	for columns5[]
				
				/*for(int k5=0;k5<columns5.length;k5++){
					SEFTEFT.setColumnWidth(k5,8000);
				}*/
		
				 int u4=0;
				 int columnWidth5[] = new int[]  { 4000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 5000, 4500, 8000,6000,7000,8000 };
				 for (int width : columnWidth5)
					 SEFTEFT.setColumnWidth(u4++, width);
				
				HSSFRow headerRow5 = SEFTEFT.createRow(0);
				HSSFCellStyle headerStyle5 = setHeaderStyle(sampleWorkbook, true);
				HSSFCellStyle heCellStyle2 =setHeaderStyle1(sampleWorkbook, true);
				HSSFCellStyle hyperlink2 = hyperlink(sampleWorkbook, true);
				headerRow5.setHeightInPoints(90);
				
				// for drop down in XLS
				CellRangeAddressList addressList5 = new CellRangeAddressList(1,100, 0, 0);
	             DVConstraint dvConstraint5 = DVConstraint.createExplicitListConstraint(new String[] { "S"});
	             HSSFDataValidation dataValidation5 = new HSSFDataValidation(addressList5,dvConstraint5);
	             dataValidation5.setSuppressDropDownArrow(false);
	             SEFTEFT.addValidationData(dataValidation5);
				int e=0;
				for (String columnName : columns5){
					HSSFCell headerCell5 = headerRow5.createCell(e++);
					headerCell5.setCellStyle(headerStyle5);
					headerCell5.setCellValue(new HSSFRichTextString(columnName));
					String s2=headerCell5.getStringCellValue();
					String sb=headerCell5.getStringCellValue();
					
					if(s2.equals("BLANK")){
						
						headerCell5.setCellStyle(heCellStyle2);
						headerCell5.setCellValue(new HSSFRichTextString(columnName));
						
					}
					if(sb.equals("Click Here To Submit The Details")){
						
						HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
						link.setAddress("http://www.");
						headerCell5.setHyperlink(link);
						headerCell5.setCellStyle(hyperlink2);
						headerCell5.setCellValue(new HSSFRichTextString(columnName));
					}	
				
				
				}
		
		
				
			int u6=0;
			int columnWidth6[] = new int[]  { 5000, 2500, 6000, 5000, 8000, 2500, 2500, 6000, 6000, 6000, 6000, 6000, 5000, 5000, 4500, 4500,4500, 4500, 4500, 4500, 4500, 2500, 4000, 2500, 4500, 8000,6000,7000,8000 };
				 for (int width : columnWidth6)
					 sampleDataSheet.setColumnWidth(u6++, width);	
			
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			HSSFCellStyle heCellStyle1=setHeaderStyle1(sampleWorkbook, true);//added
			HSSFCellStyle hyperlink1 = hyperlink(sampleWorkbook, true);
			headerRow.setHeightInPoints(90);
			
			// for drop down in XLS
			CellRangeAddressList addressList = new CellRangeAddressList(1,100,0,0);
             DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(new String[] { "N", "R"  });
             HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,dvConstraint);
             dataValidation.setSuppressDropDownArrow(false);
             sampleDataSheet.addValidationData(dataValidation);
			
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
				String s1= headerCell.getStringCellValue();//added
				String sa= headerCell.getStringCellValue();//added
				
				if(s1.equals("BLANK")){
					
					headerCell.setCellStyle(heCellStyle1);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
				}
				
                 if(sa.equals("Click Here To Submit The Details")){
                	 
					HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
					link.setAddress("http://www.");
					headerCell.setHyperlink(link);
                	headerCell.setCellStyle(hyperlink1);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
				}
				
			}
			
            
            
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			
			
			int j = 1;
			for (String[] row : bonusReportBean){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(Cell.CELL_TYPE_NUMERIC);
				
				
				
				
				dataCell = dataRow.createCell(27);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[11]);
				
				dataCell = dataRow.createCell(24);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[12]);
				
				
				
				
				
				dataCell = dataRow.createCell(0);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("N");
				
				
				dataCell = dataRow.createCell(4);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[1]);
				
				
				dataCell = dataRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
				cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
				dataCell.setCellStyle(cellStyle1);
				String amount = row[7];
				dataCell.setCellValue(amount != null ? Double.valueOf(amount):0);
				
				
				
				dataCell = dataRow.createCell(22);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[10]);
				
				
				
				dataCell = dataRow.createCell(2);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[8]);
				
				
				
				dataCell = dataRow.createCell(25);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[9]);
				
				dataCell = dataRow.createCell(14);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("QPLB");
				

				dataCell = dataRow.createCell(13);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("QPLB");
				

				dataCell = dataRow.createCell(12);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("QPLB");
				
				
				dataCell = dataRow.createCell(7);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				

				dataCell = dataRow.createCell(8);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				

				dataCell = dataRow.createCell(9);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				
				
				dataCell = dataRow.createCell(10);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("Bangalore");
				
				dataCell = dataRow.createCell(11);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("560080");
				
				
				
			}
			
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + 2 + ":D" + formEnd + ")");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			
			
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
			} catch (Exception e){
			e.printStackTrace();
			logger.error("xls creation exception", e);
		}
		return "";
	}
	

	public static void main(String[] args) {
		// new GenerateXls().generateSalaryLetter(SalaryReconciliationModel.getConfirmedSalaryDetails(), "/home/praneeth.r/Desktop" );
		// new GenerateXls().generateSalaryDisbLetter(SalaryReconciliationModel.getConfirmedSalaryDetails() , "/home/praneeth.r/Desktop" );
	}

	public String generateTSRport(List<String[]> report, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("Timesheet Report");
			String columns[] = new String[] { "EMP ID", "EMPLOYEE NAME", "CLIENT", "PROJECT", "LOCATION", "TOTAL HRS", "TOTAL LEAVES", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
			for (int k = 0; k < columns.length; k++)
				if (k == 0) sampleDataSheet.setColumnWidth(k, 2000);
				else if (k == 1 || k == 2 || k == 3) sampleDataSheet.setColumnWidth(k, 7000);
				else if (k == 5 || k == 6 || k == 4) sampleDataSheet.setColumnWidth(k, 3500);
				else sampleDataSheet.setColumnWidth(k, 1600);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(25);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			HSSFCellStyle holidayStyle = setHeaderStyle(sampleWorkbook, false);
			holidayStyle.setAlignment(CellStyle.ALIGN_LEFT);
			holidayStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			holidayStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			HSSFCellStyle LeaveStyle = setHeaderStyle(sampleWorkbook, false);
			LeaveStyle.setAlignment(CellStyle.ALIGN_LEFT);
			LeaveStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			LeaveStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			int j = 1;
			for (Iterator<String[]> iter = report.iterator(); iter.hasNext();){
				String[] bean = iter.next();
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(20);
				for (String value : bean){
					HSSFCell dataCell = dataRow.createCell(i++);
					if (value == null) continue;
					try{
						dataCell.setCellValue(i == 0 ? Integer.parseInt(value) : Float.parseFloat(value));
						dataCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					} catch (Exception e){
						dataCell.setCellValue(value);
						if (i > 5 && value.startsWith("W")){
							dataCell.setCellStyle(holidayStyle);
							dataCell.setCellValue(value.length() == 1 ? "" : value.substring(2));
						} else if (i > 5 && value.startsWith("H")){
							dataCell.setCellStyle(holidayStyle);
						} else if (i > 5 && value.startsWith("L")){
							dataCell.setCellStyle(LeaveStyle);
						} else dataCell.setCellStyle(cellStyle);
					}
				}
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	
	/**
	 * 
	 * @param 
	 * @param path
	 * Internal report for Exam question's
	 * @return
	 */
	public String generateQuestionReportInExcel( List<String[]> ReportBean, String path, String term){
		
		try{
			
			FileOutputStream fileOutputStream = null;
			HSSFWorkbook sampleWorkbook = null;
			HSSFSheet sampleDataSheet = null;
			
			try{
				
				sampleWorkbook = new HSSFWorkbook();
				sampleDataSheet = sampleWorkbook.createSheet(term);
				String columns[] = new String[] { "QUESTION" , "FIRST OPTION" , "SECOND OPTION" , "THIRD OPTION" , "FOURTH OPTION" , "CORRECT ANSWER" , "CATEGORY" };
				int k = 0;
				sampleDataSheet.setColumnWidth(k++, 8000);
				sampleDataSheet.setColumnWidth(k++, 8000);
				sampleDataSheet.setColumnWidth(k++, 8000);
				sampleDataSheet.setColumnWidth(k++, 8000);
				sampleDataSheet.setColumnWidth(k++, 8000);
				sampleDataSheet.setColumnWidth(k++, 8000);
				sampleDataSheet.setColumnWidth(k++, 8000);
				
				HSSFRow headerRow = sampleDataSheet.createRow(0);
				HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
				headerRow.setHeightInPoints(40);
				int i = 0;
				for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columnName));
				}
				/**
				 * Set the cell value for all the data rows.
				 */
				HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
				HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
				HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
				commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
				int j = 1;
				
				for(String[] row : ReportBean ){
					
					HSSFRow dataRow = sampleDataSheet.createRow(j++);
					i = 0;
					dataRow.setHeightInPoints(18);
					
					HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(Integer.parseInt(row[i++]));
					
					dataCell = dataRow.createCell(i);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(row[i++]);
					
					dataCell = dataRow.createCell(i);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(row[i++]);
					
					dataCell = dataRow.createCell(i);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(row[i++]);
					
					dataCell = dataRow.createCell(i);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(row[i++]);
					
					dataCell = dataRow.createCell(i);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(row[i++]);
					
					dataCell = dataRow.createCell(i);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(row[i++]);
					
					dataCell = dataRow.createCell(i);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(row[i++]);
					
				}
				
				HSSFPalette palette = sampleWorkbook.getCustomPalette();
				palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
				fileOutputStream = new FileOutputStream(path);
				sampleWorkbook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
				return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
				
			}catch(Exception _e){
				
				_e.printStackTrace();
				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		return "";
	}
	
	/**
	 * 
	 * @param 
	 * @param path
	 * @return
	 * 
	 * Download all the employee details 
	 * 
	 */
/*	
	public String generateAllEmployeeDetailsReportInExcel( List<String[]> ReportBean, String path, String term,String columns[] ){
		
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet(term);
			//EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MONTH, TOTAL,ACCOUNT NO, BANK_NAME
			String columns1[] = new String[] { "EMP ID", "EMP NAME","OFFICIAL EMAIL ID","CONATCT NUMBER","DESIGNATION","PROJECT NAME","CHARGE CODE","EDUCATION DETAILS","CTC","EXPERIENCE DETAILS" };
			// SET COLUMN WIDTHS
			int k = 0;
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			//sampleDataSheet.setColumnWidth(k++, 10000);
			//sampleDataSheet.setColumnWidth(k++, 6000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(10);
			int i = 0;
			int cellCount1 =0;
			for(String columnName : columns1){
				if(columnName != null || columnName != "" || columnName.length() > 0){
				HSSFCell headerCell = headerRow.createCell(cellCount1);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
				cellCount1++;
				}
			}
			for(String columnName : columns1){
			if(columns1[0].toString() != null  && columns1[0].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[0].toString()));
				//}
			}
			if(columns1[1].toString() != null  && columns1[1].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[1].toString()));
				//}
			}
			if(columns1[2].toString() != null && columns1[2].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[2].toString()));
				//}
			}
			if(columns1[3].toString() != null  && columns1[3].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[3].toString()));
				//}
			}
			if(columns1[4].toString() != null  && columns1[4].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[4].toString()));
				//}
			}
			if(columns1[5].toString() != null  && columns1[5].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[5].toString()));
				//}
			}
			if(columns1[6].toString() != null  && columns1[6].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[6].toString()));
				//}
			}
			if(columns1[7].toString() != null  && columns1[7].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[7].toString()));
				//}
			}
			if(columns1[8].toString() != null  && columns1[8].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[8].toString()));
				//}
			}
			if(columns1[9].toString() != null && columns1[9].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns1[9].toString()));
				//}
			}
			if(columns[10].toString() != null && columns[10].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[10].toString()));
				//}
			}
			//}
			*//**
			 * Set the cell value for all the data rows.
			 *//*
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 1;
			
			for (String[] row : ReportBean){
				
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				//HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				//HSSFCell dataCell = dataRow.createCell(i);
				int cellCount =0;
				for(int i1=0;i1<row.length;i1++){
					
			//		if(row[i1] != null && row[i1].length() > 0){
					
						HSSFCell dataCell = dataRow.createCell(cellCount);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue(row[i1]);
						cellCount++;
						//i1++;
					}
					//cellCount++;
				}
				i++;
				
			}
			
			for (String[] row : ReportBean){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(Cell.CELL_TYPE_NUMERIC);
				
				
				
				
				dataCell = dataRow.createCell(0);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[0]);
				
				
				dataCell = dataRow.createCell(1);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[1]);
				
				
				
				dataCell = dataRow.createCell(2);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[2]);
				
				
				
				dataCell = dataRow.createCell(3);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[3]);
				
				dataCell = dataRow.createCell(4);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[4]);
				

				dataCell = dataRow.createCell(5);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[5]);
				

				dataCell = dataRow.createCell(6);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[6]);
				
				
				dataCell = dataRow.createCell(7);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[7]);
				

				dataCell = dataRow.createCell(8);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[8]);
				
				dataCell = dataRow.createCell(9);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue(row[9]);
				

			
				
				
				
			}
			
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} }catch (Exception e){
			e.printStackTrace();
			logger.error("xls creation exception", e);
		}
		return "";
	}*/
public String generateAllEmployeeDetailsReportInExcel( List<String[]> ReportBean, String path, String term,String columns[] ){
		
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet(term);
			//EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MONTH, TOTAL,ACCOUNT NO, BANK_NAME
			//String columns[] = new String[] {"USER ID", "EMP ID", "EMP NAME","OFFICIAL EMAIL ID","CONATCT NUMBER","DESIGNATION","PROJECT NAME","CHARGE CODE","EDUCATION DETAILS","CTC","EXPERIENCE DETAILS" };
			// SET COLUMN WIDTHS
			int k = 0;
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			sampleDataSheet.setColumnWidth(k++, 10000);
			//sampleDataSheet.setColumnWidth(k++, 10000);
			//sampleDataSheet.setColumnWidth(k++, 6000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(10);
			int i = 0;
			/*int cellCount1 =0;
			for(String columnName : columns){
				if(columnName != null || columnName != "" || columnName.length() > 0){
				HSSFCell headerCell = headerRow.createCell(cellCount1);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
				cellCount1++;
				}
			}*/
			//for(String columnName : columns){
			if(columns[0].toString() != null  && columns[0].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[0].toString()));
				//}
			}
			if(columns[1].toString() != null  && columns[1].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[1].toString()));
				//}
			}
			if(columns[2].toString() != null && columns[2].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[2].toString()));
				//}
			}
			if(columns[3].toString() != null  && columns[3].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[3].toString()));
				//}
			}
			if(columns[4].toString() != null  && columns[4].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[4].toString()));
				//}
			}
			if(columns[5].toString() != null  && columns[5].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[5].toString()));
				//}
			}
			if(columns[6].toString() != null  && columns[6].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[6].toString()));
				//}
			}
			if(columns[7].toString() != null  && columns[7].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[7].toString()));
				//}
			}
			if(columns[8].toString() != null  && columns[8].toString().length() > 0){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[8].toString()));
				//}
			}
			if(columns[9].toString() != null && columns[9].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[9].toString()));
				//}
			}
			if(columns[10].toString() != null && columns[10].toString().length() > 0 ){
				//for (String columnName : columns){
					HSSFCell headerCell = headerRow.createCell(i++);
					headerCell.setCellStyle(headerStyle);
					headerCell.setCellValue(new HSSFRichTextString(columns[10].toString()));
				//}
			}
			//}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 1;
			
		
			
               for (String[] row : ReportBean){
				
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				//HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
				//HSSFCell dataCell = dataRow.createCell(i);
				int cellCount =0;
				
                if(row!=null || row.length>0) {
                for(int i1=0;i1<row.length;i1++){
					
					try{
					if(row[i1].length() > 0){
						HSSFCell dataCell = dataRow.createCell(cellCount);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue(row[i1]);
						cellCount++;
					}
					}
				
					catch(Exception e){
						HSSFCell dataCell = dataRow.createCell(cellCount);
						dataCell.setCellStyle(cellStyle);
						dataCell.setCellValue(row[i1]);
						cellCount++;
					}
					}
						i++;
                        }
               			}
			

			
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		}
		 catch (Exception e){
			e.printStackTrace();
			logger.error("xls creation exception", e);
		}
		return "";
			}
	
	
	public String appraisalReportV1(AppraisalReportV1[] appraisalReportV1, String path) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		int i = 0;
		int j = 1;
		try{
			/**
			 * Create a new instance for HSSFWorkBook class and create a
			 * sample work-sheet using HSSFSheet class to write data.
			 */
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("APPRAISAL HRD REPORTS");
			int columnWidth[] = new int[] { 2500, 6000, 8000, 8000, 5000, 6000, 6000, 5000, 4000, 4000, 4000, 15000, 25000, 15000, 15000, 20000 };
			for (int width : columnWidth)
				sampleDataSheet.setColumnWidth(i++, width);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			/**
			 * Call the setHeaderStyle method and set the styles for the
			 * all the three header cells.
			 */
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			String columns[] = new String[] { "EMP ID", "NAME", "Designation", "Appraiser Name", "No of Billable days", "Client Name & Location", "Functional Area", "Project Type", "Functional Area rating", "Personal Competencies rating", "Over all Score", "Long Term Goal", "Smart Goals", "Smart Goals achieved in the last assessment year", "Final Objectives", "Appraiser comment" };
			headerRow.setHeightInPoints(50);
			i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle nameStyle = setNameStyle(sampleWorkbook, false);
			/**
			 * Set the cell value for all the data rows.
			 */
			for (AppraisalReportV1 report : appraisalReportV1){
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(120);
				HSSFCell dataCell1 = dataRow.createCell(i++);
				dataCell1.setCellStyle(cellStyle);
				dataCell1.setCellValue(report.getId());
				HSSFCell dataCell2 = dataRow.createCell(i++);
				dataCell2.setCellStyle(nameStyle);
				dataCell2.setCellValue(report.getName());
				HSSFCell dataCell3 = dataRow.createCell(i++);
				dataCell3.setCellStyle(nameStyle);
				dataCell3.setCellValue(report.getDesignation());
				HSSFCell dataCell4 = dataRow.createCell(i++);
				dataCell4.setCellStyle(nameStyle);
				dataCell4.setCellValue(report.getAppraiser());
				HSSFCell dataCell5 = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell5.setCellStyle(cellStyle);
				dataCell5.setCellValue(Integer.parseInt(report.getBillableDays()));
				HSSFCell dataCell6 = dataRow.createCell(i++);
				dataCell6.setCellStyle(commStyle);
				dataCell6.setCellValue(report.getClient());
				HSSFCell dataCell7 = dataRow.createCell(i++);
				dataCell7.setCellStyle(commStyle);
				dataCell7.setCellValue(report.getFunctionalArea());
				HSSFCell dataCell8 = dataRow.createCell(i++);
				dataCell8.setCellStyle(commStyle);
				dataCell8.setCellValue(report.getProjectType());
				HSSFCell dataCell9 = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell9.setCellStyle(cellStyle);
				dataCell9.setCellValue(Double.parseDouble(report.getFunctionalAreaRatings()));
				HSSFCell dataCell10 = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell10.setCellStyle(cellStyle);
				dataCell10.setCellValue(Double.parseDouble(report.getPersonalRatings()));
				HSSFCell dataCell11 = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell11.setCellStyle(cellStyle);
				dataCell11.setCellValue(Double.parseDouble(report.getOveralScore()));
				HSSFCell dataCell12 = dataRow.createCell(i++);
				dataCell12.setCellStyle(cellStyle);
				dataCell12.setCellValue(report.getLongTermGoal());
				HSSFCell dataCell13 = dataRow.createCell(i++);
				dataCell13.setCellStyle(commStyle);
				dataCell13.setCellValue(report.getObjectives());
				HSSFCell dataCell15 = dataRow.createCell(i++);
				dataCell15.setCellStyle(cellStyle);
				dataCell15.setCellValue(report.getSmartGoal());
				HSSFCell dataCell16 = dataRow.createCell(i++);
				dataCell16.setCellStyle(cellStyle);
				dataCell16.setCellValue(report.getFinalObjectives());
				HSSFCell dataCell17 = dataRow.createCell(i++);
				dataCell17.setCellStyle(commStyle);
				dataCell17.setCellValue(report.getComments());
			}
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	public String generatePerdiemBankLetter(List<PerdiemReportBean> beans, String path, String term, String period) {
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		boolean success = false;
		String fileName = "Salary_Disbursement_Letter " + term + ".xls";
		path += File.separator + fileName;
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			int j = 1;
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("Salary Disbursement Letter " + PortalUtility.getdd_MM_yyyy(new Date()));
			//Sl No	EMPLOYEE NAME	ACCOUNT  No.	 AMOUNT (Rs.) 
			String columns[] = new String[] { "SL. No.", "EMPLOYEE NAME", "ACCOUNT  No", "AMOUNT (Rs.)" };
			sampleDataSheet.setColumnWidth(0, 2000);
			sampleDataSheet.setColumnWidth(1, 6000);
			sampleDataSheet.setColumnWidth(2, 6000);
			sampleDataSheet.setColumnWidth(3, 4000);
			HSSFCellStyle cellStyle1 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow dikshaRow = sampleDataSheet.createRow(j++);
			dikshaRow.setHeightInPoints(20);
			HSSFCell dikshaCell = dikshaRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(dikshaRow.getRowNum(), dikshaRow.getRowNum(), 0, 4));
			cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle1.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			dikshaCell.setCellStyle(cellStyle1);
			dikshaCell.setCellValue(new HSSFRichTextString("DIKSHA TECHNOLOGIES PVT LTD"));
			j++;
			HSSFCellStyle cellStyle4 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow dateRow = sampleDataSheet.createRow(j++);
			dateRow.setHeightInPoints(15);
			HSSFCell dateCell = dateRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(dateRow.getRowNum(), dateRow.getRowNum(), 0, 4));
			cellStyle4.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle4.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			dateCell.setCellStyle(cellStyle4);
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			dateCell.setCellValue(new HSSFRichTextString(sf.format(new Date())));
			HSSFCellStyle cellStyle2 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow iciciRow = sampleDataSheet.createRow(j++);
			iciciRow.setHeightInPoints(15);
			HSSFCell iciciCell = iciciRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(iciciRow.getRowNum(), iciciRow.getRowNum(), 0, 4));
			cellStyle2.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle2.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			iciciCell.setCellStyle(cellStyle2);
			iciciCell.setCellValue(new HSSFRichTextString("ICICI Bank Limited"));
			HSSFCellStyle cellStyle3 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow mgRow = sampleDataSheet.createRow(j++);
			mgRow.setHeightInPoints(15);
			HSSFCell mgCell = mgRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(mgRow.getRowNum(), mgRow.getRowNum(), 0, 4));
			cellStyle3.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle3.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			mgCell.setCellStyle(cellStyle3);
			mgCell.setCellValue("M G ROAD");
			HSSFRow bgRow = sampleDataSheet.createRow(j++);
			bgRow.setHeightInPoints(15);
			HSSFCell bgCell = bgRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(bgRow.getRowNum(), bgRow.getRowNum(), 0, 4));
			bgCell.setCellStyle(cellStyle3);
			bgCell.setCellValue("BANGALORE");
			HSSFRow pinRow = sampleDataSheet.createRow(j++);
			pinRow.setHeightInPoints(15);
			HSSFCell pinCell = pinRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(pinRow.getRowNum(), pinRow.getRowNum(), 0, 4));
			pinCell.setCellStyle(cellStyle3);
			pinCell.setCellValue("560001");
			j++;
			HSSFRow dearRow = sampleDataSheet.createRow(j++);
			dearRow.setHeightInPoints(15);
			HSSFCell dearCell = dearRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(dearRow.getRowNum(), dearRow.getRowNum(), 0, 4));
			dearCell.setCellStyle(cellStyle3);
			dearCell.setCellValue("Dear Sir,");
			j++;
			HSSFRow refRow = sampleDataSheet.createRow(j++);
			refRow.setHeightInPoints(15);
			HSSFCell refCell = refRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(refRow.getRowNum(), refRow.getRowNum(), 0, 4));
			refCell.setCellStyle(cellStyle3);
			refCell.setCellValue("Sub: Credit of Employee Per Diem Allowances for the month of " + period);
			HSSFRow subRow = sampleDataSheet.createRow(j++);
			subRow.setHeightInPoints(40);
			HSSFCell subCell = subRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(subRow.getRowNum(), subRow.getRowNum(), 0, 4));
			subCell.setCellStyle(cellStyle3);
			subCell.setCellValue("Please credit the following employee bank accounts with the amounts mentioned below:");
			j++;
			HSSFRow headerRow = sampleDataSheet.createRow(j++);
			HSSFFont font = sampleWorkbook.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle cellStyle111 = sampleWorkbook.createCellStyle();
			cellStyle111.setFont(font);
			cellStyle111.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle111.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			HSSFCellStyle cellStyle12 = sampleWorkbook.createCellStyle();
			cellStyle12.setFont(font);
			cellStyle12.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle12.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			headerRow.setHeightInPoints(18);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++, Cell.CELL_TYPE_STRING);
				if (i == 5) headerCell.setCellStyle(cellStyle12);
				else headerCell.setCellStyle(cellStyle111);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle cellStyleright = setHeaderStyle(sampleWorkbook, false);
			cellStyleright.setAlignment(CellStyle.ALIGN_RIGHT);
			int k = 0;
			int formStart = j + 1;
			if (beans != null && beans.size() > 0){
				for (PerdiemReportBean bean : beans){
					HSSFRow dataRow = sampleDataSheet.createRow(j++);
					i = 0;
					dataRow.setHeightInPoints(13);
					HSSFCell dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyle);
					dataCell.setCellValue(++k);
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(bean.getEmployeeName());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
					dataCell.setCellStyle(commStyle);
					dataCell.setCellValue(bean.getAccountNo());
					dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
					dataCell.setCellStyle(cellStyleright);
					dataCell.setCellValue(bean.getTotal());
				}
			}
			int formEnd = j;
			j++;
			HSSFCellStyle cellStyle5 = setHeaderStyle(sampleWorkbook, false);
			HSSFRow sumRow = sampleDataSheet.createRow(j++);
			sumRow.setHeightInPoints(20);
			HSSFCell sumCell = sumRow.createCell(3, Cell.CELL_TYPE_NUMERIC);
			cellStyle5.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle5.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			sumCell.setCellStyle(cellStyle5);
			sumCell.setCellFormula("SUM(D" + formStart + ":D" + formEnd + ")");
			// sumCell.setCellValue(new HSSFRichTextString("Sum : " + String.valueOf(sumOfSal)));
			j++;
			j++;
			subRow = sampleDataSheet.createRow(j++);
			subRow.setHeightInPoints(40);
			subCell = subRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(subRow.getRowNum(), subRow.getRowNum(), 0, 4));
			subCell.setCellStyle(cellStyle3);
			subCell.setCellValue("a) I/We Enclose RTGS/ Cheque No__________________ Favouring __________________");
			subRow = sampleDataSheet.createRow(j++);
			subRow.setHeightInPoints(40);
			subCell = subRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(subRow.getRowNum(), subRow.getRowNum(), 0, 4));
			subCell.setCellStyle(cellStyle3);
			subCell.setCellValue("b)I/We Authorize you to Debit Our Account No___________________________________");
			subRow = sampleDataSheet.createRow(j++);
			subRow.setHeightInPoints(100);
			subCell = subRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(subRow.getRowNum(), subRow.getRowNum(), 0, 4));
			subCell.setCellStyle(cellStyle3);
			subCell.setCellValue("Certify that the accounts of the beneficiaries names and account numbers mentioned in the floppy are the same as that mentioned in the hard copy and that the bank is not responsible for any mismatch between the hard copy and the data given in the Floppy.");
			HSSFRow forRow = sampleDataSheet.createRow(j++);
			forRow.setHeightInPoints(15);
			j++;
			j++;
			HSSFCell forCell = forRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(forRow.getRowNum(), forRow.getRowNum(), 0, 4));
			cellStyle3.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle3.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			forCell.setCellStyle(cellStyle3);
			forCell.setCellValue("Thanking you,");
			j++;
			j++;
			j++;
			HSSFRow signRow = sampleDataSheet.createRow(j++);
			signRow.setHeightInPoints(15);
			HSSFCell signCell = signRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(signRow.getRowNum(), signRow.getRowNum(), 0, 4));
			cellStyle3.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle3.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			signCell.setCellStyle(cellStyle3);
			signCell.setCellValue("Yours Faithfully,");
			signRow = sampleDataSheet.createRow(j++);
			signRow.setHeightInPoints(15);
			signCell = signRow.createCell(0, Cell.CELL_TYPE_STRING);
			sampleDataSheet.addMergedRegion(new CellRangeAddress(signRow.getRowNum(), signRow.getRowNum(), 0, 4));
			cellStyle3.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle3.getFont(sampleWorkbook).setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			signCell.setCellStyle(cellStyle3);
			signCell.setCellValue("for DIKSHA TECHNOLOGIES PVT. LTD.");
			HSSFPalette palette = sampleWorkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			FileOutputStream fileOutputStream = new FileOutputStream(path, true);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			success = true;
		} catch (FileNotFoundException e){
			logger.error("There is a FileNotFoundException while generating the Salary Disbursement Letter " + e.getMessage());
		} catch (IOException e){
			logger.error("There is a IOException while generating the Salary Disbursement Letter " + e.getMessage());
		}
		if (success) return fileName;
		else return "error";
	}

	public String generateICICIReport(List<PerdiemReportBean> beans, String path, String term, String month) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		String fileName = term + ".xls";
		path += File.separator + fileName;
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("Bank Format");
			//NAME	ACCOUNT_NO	CUR_CODE	CR_DR	 TRAN_AMT 	TRAN_PART
			String columns[] = new String[] { "NAME", "ACCOUNT_NO", "CUR_CODE", "CR_DR", "TRAN_AMT", "TRAN_PART" };
			// SET COLUMN WIDTHS
			int k = 0;
			sampleDataSheet.setColumnWidth(k++, 7000);
			sampleDataSheet.setColumnWidth(k++, 5000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			sampleDataSheet.setColumnWidth(k++, 2000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			sampleDataSheet.setColumnWidth(k++, 5500);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(20);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 2;
			double total = 0;
			for (PerdiemReportBean bean : beans){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(bean.getEmployeeName());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(bean.getAccountNo());
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("INR");
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("CR");
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(commStyle1);
				dataCell.setCellValue(bean.getTotal());
				total += bean.getTotal();
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue("PER DIEM FOR" + month);
			}
			HSSFRow dataRow = sampleDataSheet.createRow(j++);
			dataRow.setHeightInPoints(18);
			HSSFCell dataCell = dataRow.createCell(4, Cell.CELL_TYPE_NUMERIC);
			dataCell.setCellStyle(commStyle1);
			dataCell.setCellValue(total);
			//HSSFPalette palette = sampleWorkbook.getCustomPalette();
			//palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}

	public String generateOtherReport(List<PerdiemReportBean> beans, String path, String term, String month) {
		FileOutputStream fileOutputStream = null;
		HSSFWorkbook sampleWorkbook = null;
		HSSFSheet sampleDataSheet = null;
		String fileName = term + ".xls";
		path += File.separator + fileName;
		File file = new File(path);
		if (file.exists()) file.delete();
		try{
			sampleWorkbook = new HSSFWorkbook();
			sampleDataSheet = sampleWorkbook.createSheet("Bank Format");
			//NAME	ACCOUNT_NO	CUR_CODE	CR_DR	 TRAN_AMT 	TRAN_PART
			String columns[] = new String[] { "NAME", "ACCOUNT_NO", "CUR_CODE", "CR_DR", "TRAN_AMT", "TRAN_PART", "BANK NAME" };
			// SET COLUMN WIDTHS
			int k = 0;
			sampleDataSheet.setColumnWidth(k++, 7000);
			sampleDataSheet.setColumnWidth(k++, 5000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			sampleDataSheet.setColumnWidth(k++, 2000);
			sampleDataSheet.setColumnWidth(k++, 3000);
			sampleDataSheet.setColumnWidth(k++, 7000);
			sampleDataSheet.setColumnWidth(k++, 7000);
			HSSFRow headerRow = sampleDataSheet.createRow(0);
			HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
			headerRow.setHeightInPoints(20);
			int i = 0;
			for (String columnName : columns){
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(new HSSFRichTextString(columnName));
			}
			/**
			 * Set the cell value for all the data rows.
			 */
			HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
			HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
			commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
			int j = 2;
			double total = 0;
			for (PerdiemReportBean bean : beans){
				//PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
				HSSFRow dataRow = sampleDataSheet.createRow(j++);
				i = 0;
				dataRow.setHeightInPoints(18);
				HSSFCell dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(bean.getEmployeeName());
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_STRING);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(bean.getAccountNo());
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("INR");
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(cellStyle);
				dataCell.setCellValue("CR");
				dataCell = dataRow.createCell(i++, Cell.CELL_TYPE_NUMERIC);
				dataCell.setCellStyle(commStyle1);
				dataCell.setCellValue(bean.getTotal());
				total += bean.getTotal();
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue("PER DIEM FOR" + month);
				dataCell = dataRow.createCell(i++);
				dataCell.setCellStyle(commStyle);
				dataCell.setCellValue(bean.getBankName());
			}
			HSSFRow dataRow = sampleDataSheet.createRow(j++);
			dataRow.setHeightInPoints(18);
			HSSFCell dataCell = dataRow.createCell(4, Cell.CELL_TYPE_NUMERIC);
			dataCell.setCellStyle(commStyle1);
			dataCell.setCellValue(total);
			//HSSFPalette palette = sampleWorkbook.getCustomPalette();
			//palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
			fileOutputStream = new FileOutputStream(path);
			sampleWorkbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
		} catch (Exception e){
			logger.error("xls creation exception", e);
		}
		return "";
	}
	
	public String generateRBonusReportInExcel(List<String[]> bonusReportBean, String path, String term) {
        FileOutputStream fileOutputStream = null;
        HSSFWorkbook sampleWorkbook = null;
        HSSFSheet sampleDataSheet = null;
        try{
            sampleWorkbook = new HSSFWorkbook();
            sampleDataSheet = sampleWorkbook.createSheet(term);
            //EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUATERELY_BONUS,COMPANY_BONUS, CURRENCY_TYPE, MONTH, TOTAL,ACCOUNT NO, BANK_NAME
            String columns[] = new String[] { "EMP ID", "EMP NAME", "CLIENT NAME", "RETENTION BONUS","CURRENCY TYPE", "MONTH", "TOTAL AMOUNT", "ACCOUNT NO", "BANK NAME" };
            // SET COLUMN WIDTHS
            int k = 0;
            sampleDataSheet.setColumnWidth(k++, 2000);
            sampleDataSheet.setColumnWidth(k++, 6000);
            sampleDataSheet.setColumnWidth(k++, 6000);
            sampleDataSheet.setColumnWidth(k++, 3000);
            sampleDataSheet.setColumnWidth(k++, 3000);
            sampleDataSheet.setColumnWidth(k++, 4000);
            sampleDataSheet.setColumnWidth(k++, 2500);
            sampleDataSheet.setColumnWidth(k++, 4000);
            sampleDataSheet.setColumnWidth(k++, 5000);
            //sampleDataSheet.setColumnWidth(k++, 3000);
            HSSFRow headerRow = sampleDataSheet.createRow(0);
            HSSFCellStyle headerStyle = setHeaderStyle(sampleWorkbook, true);
            headerRow.setHeightInPoints(40);
            int i = 0;
            for (String columnName : columns){
                HSSFCell headerCell = headerRow.createCell(i++);
                headerCell.setCellStyle(headerStyle);
                headerCell.setCellValue(new HSSFRichTextString(columnName));
            }
            /**
             * Set the cell value for all the data rows.
             */
            HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook, false);
            HSSFCellStyle commStyle = setCommentsStyle(sampleWorkbook, false);
            HSSFCellStyle commStyle1 = setCommentsStyle(sampleWorkbook, false);
            commStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            commStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
            int j = 1;
            for (String[] row : bonusReportBean){
                //PerdiemReportBean bean = iter.next();Double.parseDouble(row[i])
                HSSFRow dataRow = sampleDataSheet.createRow(j++);
                i = 0;
                dataRow.setHeightInPoints(18);
                HSSFCell dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
                dataCell.setCellStyle(cellStyle);
                dataCell.setCellValue(Integer.parseInt(row[i++]));
                
                dataCell = dataRow.createCell(i);
                dataCell.setCellStyle(commStyle);
                dataCell.setCellValue(row[i++]);
                
                dataCell = dataRow.createCell(i);
                dataCell.setCellStyle(commStyle);
                dataCell.setCellValue(row[i++]);
                
                dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
                dataCell.setCellStyle(commStyle1);
                dataCell.setCellValue(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(row[i++]))));
                
                //dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
                //dataCell.setCellStyle(cellStyle);
                //dataCell.setCellValue(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(row[i++]))));
                
                dataCell = dataRow.createCell(i);
                dataCell.setCellStyle(cellStyle);
                dataCell.setCellValue(row[i++]);
                
                dataCell = dataRow.createCell(i);
                dataCell.setCellStyle(cellStyle);
                dataCell.setCellValue(row[i++]);
                
                dataCell = dataRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
                dataCell.setCellStyle(commStyle1);
                dataCell.setCellValue(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(row[i++]))));
                
                dataCell = dataRow.createCell(i);
                dataCell.setCellStyle(cellStyle);
                dataCell.setCellValue(row[i++]);
                
                dataCell = dataRow.createCell(i);
                dataCell.setCellStyle(cellStyle);
                dataCell.setCellValue(row[i++]);    
            }
            HSSFPalette palette = sampleWorkbook.getCustomPalette();
            palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 255, (byte) 133, (byte) 18);
            fileOutputStream = new FileOutputStream(path);
            sampleWorkbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return path.substring(path.lastIndexOf(File.separator.equals("/") ? File.separator : "\\") + 1);
        } catch (Exception e){
            logger.error("xls creation exception", e);
        }
        return "";
    }

}