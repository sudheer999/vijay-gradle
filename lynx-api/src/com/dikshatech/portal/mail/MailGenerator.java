package com.dikshatech.portal.mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PropertyLoader;

public class MailGenerator {

	private static Properties	properties;
	private static Logger		logger	= LoggerUtil.getLogger(MailGenerator.class);

	public MailGenerator() {
		properties = PropertyLoader.loadProperties("conf.email.properties");
	}

	public String getMailTemplate(String templateName) throws FileNotFoundException {
		File fFile = new File(getTemplatePath(templateName));
		String html_trimmed = "";
		Scanner scanner = new Scanner(new FileReader(fFile));
		try{
			while (scanner.hasNextLine()){
				String s = scanner.nextLine();
				String tmp = s.trim();
				html_trimmed = html_trimmed.concat(tmp);
			}
		} finally{
			scanner.close();
		}
		return html_trimmed;
	}

	public String replaceFields(String ipString, PortalMail portalMail) {
		String opString = ipString;
		TemplateFields[] tFields = TemplateFields.values();
		for (TemplateFields tFields2 : tFields){

			String field = "\\[" + tFields2.name() + "\\]";
			try{
				opString = opString.replaceAll(field, getValue(portalMail, tFields2.name()));
			} catch (Exception e){
				logger.info("Error while replacing field value : " + field);
			}
		}
		return opString;
	}

	public String getValue(Object obj, String field) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String result = "";
		Method[] methods = obj.getClass().getMethods();
		Object[] object = null;
		for (Method method : methods){
			if (method.getName().equalsIgnoreCase("get" + field)){
				if (method.invoke(obj, object) != null){
					result = method.invoke(obj, object).toString();
				}
				break;
			}
		}
		return result;
	}

	public void invoker(PortalMail portalMail) throws AddressException, MessagingException, UnsupportedEncodingException, FileNotFoundException {
		// stop mails you dont wish...
		if (properties.get("disableMails") != null && properties.get("disableMails").toString().equalsIgnoreCase("true")) return;
		// get the deep cloning object
		final PortalMail pMail = portalMail.getcopy();
		new Thread() {

			public void run() {
				try{
					logger.info("Sending Mail:- Subject : " + pMail.getMailSubject() + " \n TO : " + (pMail.getRecipientMailId() != null ? pMail.getRecipientMailId() : Arrays.toString(pMail.getAllReceipientMailId())) + (pMail.getAllReceipientcCMailId() == null ? "" : (" \n CC : " + Arrays.toString(pMail.getAllReceipientcCMailId()))) + (pMail.getAllReceipientBCCMailId() == null ? "" : (" \n BCC : " + Arrays.toString(pMail.getAllReceipientBCCMailId()))));
					String bodyText = replaceFields(getMailTemplate(pMail.getTemplateName()), pMail);
					pMail.setMailBody(bodyText);
					new Mailer(properties);
					Session session = Mailer.getSession();
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(pMail.getFromMailId() == null ? properties.getProperty(MailSettings.FROM_MAILID) : pMail.getFromMailId()));
					message = setMailInfo(pMail, message);
					message.saveChanges();
					Mailer.sendMail((MimeMessage) message, session);
					PendingMails.getInstance().notifyPendingMails();
				} catch (AddressException e){
					// add to db
					logger.info("Mail sending faild permanently :-subject: " + pMail.getMailSubject() + " \n TO : " + (pMail.getRecipientMailId() != null ? pMail.getRecipientMailId() : Arrays.toString(pMail.getAllReceipientMailId())) + (pMail.getAllReceipientcCMailId() == null ? "" : (" \n CC : " + Arrays.toString(pMail.getAllReceipientcCMailId()))) + (pMail.getAllReceipientBCCMailId() == null ? "" : (" \n BCC : " + Arrays.toString(pMail.getAllReceipientBCCMailId()))) + "\nDetails:"
							+ pMail.getMailBody());
					logger.info(e.toString());
					logger.error(e.toString());
				} catch (MessagingException e){
					PendingMails.getInstance().savePendingEmails(pMail);
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static Message setMailInfo(PortalMail pMail, Message message) throws MessagingException {
		if (pMail.getAllReceipientMailId() != null && pMail.getAllReceipientMailId().length > 0){
			for (String Recipients : pMail.getAllReceipientMailId()){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(Recipients));
			}
		} else if (pMail.getRecipientMailId() != null && pMail.getRecipientMailId().length() > 0){
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(pMail.getRecipientMailId()));
		} else{
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(properties.getProperty(MailSettings.FROM_MAILID)));
		}
		if (pMail.getAllReceipientBCCMailId() != null && pMail.getAllReceipientBCCMailId().length > 0){
			for (String Recipients : pMail.getAllReceipientBCCMailId()){
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(Recipients));
			}
		}
		if (pMail.getAllReceipientcCMailId() != null && pMail.getAllReceipientcCMailId().length > 0){
			InternetAddress[] addressTo = new InternetAddress[pMail.getAllReceipientcCMailId().length];
			int i = 0;
			for (String Recipients : pMail.getAllReceipientcCMailId()){
				addressTo[i] = new InternetAddress(Recipients);
				i++;
			}
			message.setRecipients(Message.RecipientType.CC, addressTo);
		}
		message.setSubject(pMail.getMailSubject());
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(pMail.getMailBody(), "text/html");
		MimeMultipart multipart = new MimeMultipart("related");
		multipart.addBodyPart(messageBodyPart);
		if (pMail.getFileSources() != null){
			for (Attachements dataSrc : pMail.getFileSources()){
				messageBodyPart = new MimeBodyPart();
				messageBodyPart.setFileName(dataSrc.getFileName());
				DataSource source = new FileDataSource(dataSrc.getFilePath());
				messageBodyPart.setDataHandler(new DataHandler(source));
				multipart.addBodyPart(messageBodyPart);
			}
		}
		// Put parts in message
		message.setContent(multipart);
		message.setSentDate(new Date());
		return message;
	}

	private String getTemplatePath(String template) {
		String templatePath = PropertyLoader.getEnvVariable() + properties.getProperty(MailSettings.TEMPLATE) + template;
		templatePath = templatePath.replace('/', File.separator.charAt(0));
		return templatePath;
	}
}