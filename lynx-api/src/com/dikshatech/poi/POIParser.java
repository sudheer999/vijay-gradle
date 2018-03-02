package com.dikshatech.poi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.models.ProfileInfoModel;

public class POIParser {

	private static final int	EMP_ID					= 0;
	private static final int	FIRST_NAME				= 1;
	private static final int	MIDDLE_NAME				= 2;
	private static final int	LAST_NAME				= 3;
	private static final int	DOB						= 4;
	private static final int	EMAIL_ID				= 5;
	private static final int	SPOC_ID					= 6;
	private static final int	LEVEL_ID				= 7;
	private static final int	DOJ						= 8;
	private static final int	RM_ID					= 9;
	private static final int	DATE_OF_CONFIRMATION	= 10;
	private static final int	EMP_TYPE				= 11;
	private static final int	GENDER					= 12;
	private static final int	NATIONALITY				= 13;
	private static final int	NOTICE_PERIOD			= 14;
	private static final int	PROJECT_ID				= 15;
	private static final int	ROLE_ID					= 16;
	private static final Logger	logger					= Logger.getLogger(POIParser.class);
	private int					USERS_SHEET				= 0;
	private int					LEAVE_SHEET				= 0;
	private List<String>		header					= new ArrayList<String>();

	private Vector<Vector<Object>> parse(String fileName, int sheet) throws Exception {
		Vector<Vector<Object>> cellVectorHolder = new Vector<Vector<Object>>();
		int MAX_CELL_NO = 0;
		InputStream myInput = new FileInputStream(fileName);
		POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
		HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
		HSSFSheet mySheet = myWorkBook.getSheetAt(sheet);
		Iterator<?> rowIter = mySheet.rowIterator();
		HSSFRow headRow = (HSSFRow) rowIter.next();
		Iterator<?> headItr = headRow.cellIterator();
		while (headItr.hasNext()){
			HSSFCell myCell = (HSSFCell) headItr.next();
			try{
				myCell.getStringCellValue();
			} catch (IllegalStateException e){
				myCell.getNumericCellValue();
			}
			header.add(myCell.getStringCellValue());
			MAX_CELL_NO++;
		}
		while (rowIter.hasNext()){
			HSSFRow myRow = (HSSFRow) rowIter.next();
			Vector<Object> cellStoreVector = new Vector<Object>();
			for (int index = 0; index < MAX_CELL_NO; index++){
				HSSFCell myCell = myRow.getCell(index);
				Object temp = null;
				if (myCell == null){
					cellStoreVector.addElement("");
				} else{
					try{
						switch (myCell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								temp = myCell.getRichStringCellValue().getString();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								temp = new Double(myCell.getNumericCellValue()).toString();
								if (HSSFDateUtil.isCellDateFormatted(myCell)){
									double date = myCell.getNumericCellValue();
									temp = HSSFDateUtil.getJavaDate(date);
								} else{
									int tempInt = (int) myCell.getNumericCellValue();
									temp = tempInt;
								}
								break;
							default:
						}
						cellStoreVector.addElement(temp);
					} catch (IllegalStateException e){
						e.printStackTrace();
					}
				}
			}
			cellVectorHolder.addElement(cellStoreVector);
		}
		logger.info("Reading excel file is over.");
		logger.info("Headers: " + header.toString());
		for (Object obj : cellVectorHolder){
			logger.info(obj);
		}
		return cellVectorHolder;
	}

	public boolean parseInfo(HttpServletRequest request, String path) {
		try{
			String usersSheet = (String) request.getParameter("users");
			String leaveSheet = (String) request.getParameter("leave");
			if (usersSheet != null && usersSheet.equals("1")){
				USERS_SHEET = 0;
				ProfileInfoModel pInfoModel = new ProfileInfoModel();
				Vector<Vector<Object>> cellVectorHolder = parse(path, USERS_SHEET);
				for (Object obj : cellVectorHolder){
					Vector<Object> cellStoreVector = (Vector<Object>) obj;
					ProfileInfo profileInfo = populateProfileDTO(cellStoreVector);
					pInfoModel.save(profileInfo, request);
					logger.info(cellStoreVector);
				}
			}
			if (leaveSheet != null && leaveSheet.equals("1")){
				LEAVE_SHEET = 0;
				Vector<Vector<Object>> cellVectorHolder = parse(path, LEAVE_SHEET);
				for (Object obj : cellVectorHolder){
					Vector<Object> cellStoreVector = (Vector<Object>) obj;
					// TODO Leave update......
					logger.info(cellStoreVector);
				}
			}
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private ProfileInfo populateProfileDTO(Vector<Object> cellVectorHolder) throws ParseException {
		ProfileInfo profileInfo = new ProfileInfo();
		profileInfo.setEmpId(convertObj(cellVectorHolder.get(EMP_ID)));
		profileInfo.setFirstName(cellVectorHolder.get(FIRST_NAME).toString());
		profileInfo.setMiddleName(cellVectorHolder.get(MIDDLE_NAME).toString());
		profileInfo.setLastName(cellVectorHolder.get(LAST_NAME).toString());
		profileInfo.setDob((Date) cellVectorHolder.get(DOB));
		profileInfo.setOfficalEmailId(cellVectorHolder.get(EMAIL_ID).toString());
		profileInfo.setHrSpoc(convertObj(cellVectorHolder.get(SPOC_ID)));
		profileInfo.setLevelId(convertObj(cellVectorHolder.get(LEVEL_ID)));
		profileInfo.setDateOfJoining((Date) cellVectorHolder.get(DOJ));
		profileInfo.setReportingMgr(convertObj(cellVectorHolder.get(RM_ID)));
		profileInfo.setDateOfConfirmation((Date) cellVectorHolder.get(DATE_OF_CONFIRMATION));
		profileInfo.setEmpType(cellVectorHolder.get(EMP_TYPE).toString());
		profileInfo.setGender(cellVectorHolder.get(GENDER).toString());
		profileInfo.setNationality(cellVectorHolder.get(NATIONALITY).toString());
		profileInfo.setNoticePeriod(convertObj(cellVectorHolder.get(NOTICE_PERIOD)));
		profileInfo.setProjectId(convertObj(cellVectorHolder.get(PROJECT_ID).toString()));
		profileInfo.setRoleId(convertObj(cellVectorHolder.get(ROLE_ID)));
		return profileInfo;
	}

	private int convertObj(Object obj) {
		return Integer.parseInt(obj.toString());
	}

	public static void main(String[] args) {
		try{
			new POIParser().parse("/home/manish/Downloads/The correct format of excel sheet on Users/LeavesNew.xls", 0);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
