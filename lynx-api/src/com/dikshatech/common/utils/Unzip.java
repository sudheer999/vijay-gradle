package com.dikshatech.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

public class Unzip {

	protected static final Logger	logger	= Logger.getLogger(Unzip.class);
	 
	
	public static void unzip(String zipFilePath, String destPath, String fileName) {
		String zipEntryName = null;
		try{
			ZipFile objZipFile = new ZipFile(zipFilePath);
			for (Enumeration entries = objZipFile.entries(); entries.hasMoreElements();){
				ZipEntry zipEntry = (ZipEntry) entries.nextElement();
				
		
				
				zipEntryName = zipEntry.getName();
				StringTokenizer st = new StringTokenizer(zipEntryName, File.separator);
				while (st.hasMoreElements()){
					logger.info("zip : " + zipEntryName);
					zipEntryName = st.nextToken();
				}
				File file=new File(destPath + File.separator + zipEntryName);
			    file.getParentFile().mkdirs();
				OutputStream out = new FileOutputStream(file);
				InputStream in = objZipFile.getInputStream(zipEntry);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0){
					out.write(buf, 0, len);
				}
				// Close streams
				out.close();
				in.close();
			}
			String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
			File file1 = new File(destPath, fileNameWithOutExt);
			file1.delete();
			File file2 = new File(zipFilePath);
			file2.delete();
		} catch (ZipException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	
	
	public void copy(File src, File dst) throws IOException {
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
 
}
