package com.dikshatech.portal.mail;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class Mailer {

	public static String	userName;
	public static String	password;
	public static String	host;
	public static String	port;
	public static String	auth;
	public static boolean	debug;
	public static String	starttls;
	public static String	socketFactoryClass;
	public static String	fallback;
	public static String	protocol;
	public static String	template;

	/**
	 * This Mailer constructor creates Mailer object and initialise the static
	 * fields of this class with the arguments passed as parameters to
	 * constructor, which are used by getSession and sendMail methods.
	 * 
	 * @param userName
	 * @param password
	 * @param host
	 * @param port
	 * @param auth
	 * @param stattls
	 * @param socFactClass
	 * @param fallBack
	 * @param debug
	 */
	public Mailer(Properties properties) {
		Mailer.userName = properties.getProperty(MailSettings.FROM_MAILID);
		Mailer.password = properties.getProperty(MailSettings.PASSWORD);
		Mailer.host = properties.getProperty(MailSettings.HOST_IP);
		Mailer.port = properties.getProperty(MailSettings.PORT_NO);
		Mailer.auth = properties.getProperty(MailSettings.AUTHENTICATION);
		//Mailer.debug = Boolean.parseBoolean(properties.getProperty(MailSettings.DEBUG));
		Mailer.starttls = properties.getProperty(MailSettings.START_TLS);
		Mailer.socketFactoryClass = properties.getProperty(MailSettings.SOC_FACT_CLASS);
		Mailer.fallback = properties.getProperty(MailSettings.FALLBACK);
		Mailer.protocol = properties.getProperty(MailSettings.PROTOCOL);
	}

	/**
	 * This method is used to create javax.mail.Session object for sending mail.
	 * 
	 * @return
	 */
	public static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.debug", debug);
		if (!"".equals(starttls)) props.put("mail.smtp.starttls.enable", true);
		if (!"".equals(port)) props.put("mail.smtp.socketFactory.port", port);
		if (!"".equals(socketFactoryClass)) props.put("mail.smtp.socketFactory.class", socketFactoryClass);
		if (!"".equals(fallback)) props.put("mail.smtp.socketFactory.fallback", false);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
		return session;
	}

	/**
	 * This method requires javax.mail.MimeMessage and javax.mail.Session object
	 * to send a mail.
	 * 
	 * @param message
	 * @param session
	 * @return
	 * @throws MessagingException
	 */
	public synchronized static boolean sendMail(MimeMessage message, Session session) throws MessagingException {
		Transport transport = session.getTransport(protocol);
		transport.connect(host, userName, password);
		if (transport.isConnected()){
			transport.sendMessage(message, message.getAllRecipients());
		}
		transport.close();
		return true;
	}
}
