package com.dikshatech.common.utils;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * The Class LoggerUtil is a generic calss for Log4j Logger.
 * 
 * @author Rajesh Hanumanthappa
 * @version $Revision: 01 $ $Date: 2010-03-30 10:51:48 -0500 (Tue, 30 Mar 2010)
 *          $
 */
public class LoggerUtil
{

	private static Logger logger = Logger.getLogger(LoggerUtil.class);
	
	static
	{
		try
		{
			URL path2Conf = Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
			DOMConfigurator.configure(path2Conf);
			logger.info("LoggerUtil util configured from log4j.xml file");
		}
		catch (Exception e)
		{
			// ignore
		}
	}

	

	public static Logger getLogger()
	{
		return logger;
	}

	public static Logger getLogger(Class<?> clazz)
	{

		if (logger == null)
			logger = Logger.getLogger(LoggerUtil.class);

		return Logger.getLogger(clazz);
	}

	public static void main(String[] args)
	{
		logger.trace("Trace");
		logger.debug("Debug");
		logger.info("Info");
		logger.warn("Warn");
		logger.error("Error");
		logger.fatal("Fatal");
	}

}
