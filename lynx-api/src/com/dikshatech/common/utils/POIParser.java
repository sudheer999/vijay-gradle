package com.dikshatech.common.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class POIParser {

	public static Vector<Vector<Object>> parseXls(String fileName, int sheet) throws Exception {
		return parseXls(new FileInputStream(fileName), sheet);
	}
	public static Vector<Vector<Object>> parseXls(InputStream myInput, int sheet) throws Exception {
		//List<String> header = new ArrayList<String>();
		Vector<Vector<Object>> cellVectorHolder = new Vector<Vector<Object>>();
		int MAX_CELL_NO = 0;
		POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
		HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
		HSSFSheet mySheet = myWorkBook.getSheetAt(sheet);
		Iterator<?> rowIter = mySheet.rowIterator();
		HSSFRow headRow = (HSSFRow) rowIter.next();
		Iterator<?> headItr = headRow.cellIterator();
		while (headItr.hasNext()){
			headItr.next();
			/*try{
				myCell.getStringCellValue();
			} catch (IllegalStateException e){
				myCell.getNumericCellValue();
			}
			header.add(myCell.getStringCellValue());*/
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
									double tempInt = (double) myCell.getNumericCellValue();
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
			System.out.println(cellStoreVector);
			cellVectorHolder.addElement(cellStoreVector);
		}
		return cellVectorHolder;
	}
	public static Vector<Vector<Object>> parseXlsSal(InputStream myInput, int sheet) throws Exception {
		//List<String> header = new ArrayList<String>();
		Vector<Vector<Object>> cellVectorHolder = new Vector<Vector<Object>>();
		int MAX_CELL_NO = 0;
		POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
		HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
		HSSFSheet mySheet = myWorkBook.getSheetAt(sheet);
		Iterator<?> rowIter = mySheet.rowIterator();
		HSSFRow headRow = (HSSFRow) rowIter.next();
		Iterator<?> headItr = headRow.cellIterator();
		while (headItr.hasNext()){
			headItr.next();
			/*try{
				myCell.getStringCellValue();
			} catch (IllegalStateException e){
				myCell.getNumericCellValue();
			}
			header.add(myCell.getStringCellValue());*/
			MAX_CELL_NO++;
		}
		while (rowIter.hasNext()){
			HSSFRow myRow = (HSSFRow) rowIter.next();
			Vector<Object> cellStoreVector = new Vector<Object>();
			for (int index = 0; index < MAX_CELL_NO; index++){
				HSSFCell myCell = myRow.getCell(index);
				Object temp = null;
				if (myCell == null){
					//cellStoreVector.addElement("");
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
									double tempInt = (double) myCell.getNumericCellValue();
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
			System.out.println(cellStoreVector);
			cellVectorHolder.addElement(cellStoreVector);
		}
		return cellVectorHolder;
	}
	public static Vector<Vector<Object>> parseXlsRmgComp(InputStream myInput, int sheet) throws Exception {
		//List<String> header = new ArrayList<String>();
		Vector<Vector<Object>> cellVectorHolder = new Vector<Vector<Object>>();
		int MAX_CELL_NO = 55;
		POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
		HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
		HSSFSheet mySheet = myWorkBook.getSheetAt(sheet);
		Iterator<?> rowIter = mySheet.rowIterator();
		HSSFRow headRow = (HSSFRow) rowIter.next();
		Iterator<?> headItr = headRow.cellIterator();
		/*while (headItr.hasNext()){
			headItr.next();
			try{
				myCell.getStringCellValue();
			} catch (IllegalStateException e){
				myCell.getNumericCellValue();
			}
			header.add(myCell.getStringCellValue());
			MAX_CELL_NO++;
		}*/
		while (rowIter.hasNext()){
			HSSFRow myRow = (HSSFRow) rowIter.next();
			Vector<Object> cellStoreVector = new Vector<Object>();
			for (int index = 0; index < MAX_CELL_NO; index++){
				HSSFCell myCell = myRow.getCell(index);
				Object temp = null;
				if (myCell == null){
					//cellStoreVector.addElement("");
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
									double tempInt = (double) myCell.getNumericCellValue();
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
			System.out.println(cellStoreVector);
			cellVectorHolder.addElement(cellStoreVector);
		}
		return cellVectorHolder;
	}
}
