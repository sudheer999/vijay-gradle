package com.dikshatech.portal.timer;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseTemplate {

	private static final Logger	logger	= Logger.getLogger(ParseTemplate.class);

	public String getHTMLBodyText(String htmlString) {
		Document doc = Jsoup.parse(htmlString);
		Elements imgs = doc.select("img");
		for (Element element : imgs){
			element.remove();
		}
		return doc.body().toString();
	}

	public String updateTagData(String attName, String newData, String htmlString) {
		String returnData = null;
		Document doc = Jsoup.parse(htmlString);
		Elements label = doc.getElementsByAttributeValue("id", attName);
		if (label.hasText()){
			label.empty();
			label.append(newData);
		}
		returnData = doc.html();
		return returnData;
	}

	public String getTagDataById(String attName, String htmlString) {
		String returnData = null;
		Document doc = Jsoup.parse(htmlString);
		Elements label = doc.getElementsByAttributeValue("id", attName);
		if (label.hasText()){
			returnData = label.text();
		}
		return returnData;
	}

	public static void main(String[] args) {
		try{
			String msg = FileUtils.readFileToString(new File("/home/vijay.jayaram/Desktop/templates/SODEXO_REQ_SERVICED_TO_HANDLER.html"));
			//logger.info(new ParseTemplate().updateTagData("empName", "abcd", htmlString));
			ParseTemplate pTemplate = new ParseTemplate();
			String newMsgStr = pTemplate.updateTagData("textMsg", pTemplate.getTagDataById("empName", msg) + " has serviced the sodexo request", msg);
			newMsgStr = pTemplate.updateTagData("empName", "Vijay", newMsgStr);
			logger.info(newMsgStr);
			//new ParseTemplate().getHTMLBodyText(htmlString);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
