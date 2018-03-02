package com.dikshatech.portal.models;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.dikshatech.common.utils.PropertyLoader;

public class mailModel {
	
	
	public static void emailPdf(String[] officeEmailId,String regName) throws FileNotFoundException, MessagingException{
		  String smtpHost;
		  int smtpPort;
		  String subject;
		  String sender;
		  String password;
		  String description;
		  String filename;
		  
		Properties pro= PropertyLoader.loadProperties("conf.email.properties");
		smtpHost=pro.getProperty("SMTPHOST");
		smtpPort=Integer.parseInt(pro.getProperty("SMTPPORT"));
		subject=pro.getProperty("SUBJECT");
		sender=pro.getProperty("SENDER");
		password=pro.getProperty("PASSWORD");
		description=pro.getProperty("DESCRIPTION");
		filename=pro.getProperty("FILE").concat("calendar2012_").concat(regName).concat(".pdf");
		String[] recipients =getrecipients(officeEmailId);
		postMail(recipients,subject,description,sender,smtpHost,smtpPort,filename,password,regName);
	}

	public static void postMail(String[] recipients, String subject,
			String description, String sender, String smtpHost, int smtpPort, String filename,String password,String regName ) throws MessagingException,FileNotFoundException {
		boolean debug = false;
		
		//Set the host smtp address
		Properties props = new Properties();
		
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host",smtpHost);
		props.put("mail.smtp.port",smtpPort);
		props.put("mail.smtp.user",sender);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.auth",true);
		props.put("mail.smtp.socketFactory.class",true);
		
		// create some properties and get the default Session
		 Session session = Session.getDefaultInstance(props,
				 new javax.mail.Authenticator() {

				 protected PasswordAuthentication getPasswordAuthentication() {
				 return new PasswordAuthentication( "admin@dikshatech.com","praya123");
				 
				 }
				 });
		session.setDebug(debug);
		// create a message
		Message msg = new MimeMessage(session);
		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(sender);
		msg.setFrom(addressFrom);
		
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++)
		{
		addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		// Set the email message text.
	    MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText(description);
        // Set the email attachment file
        MimeBodyPart attachmentPart = new MimeBodyPart();
        FileDataSource fileDataSource = new FileDataSource(filename) {
        @Override
        public String getContentType() {
        return "application/octet-stream";
        }
        };
        attachmentPart.setDataHandler(new DataHandler(fileDataSource));
        attachmentPart.setFileName(filename);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);
        multipart.addBodyPart(attachmentPart);
        msg.setContent(multipart);
        Transport.send(msg);
	}

	private static String[] getrecipients(String[] officeEmailId) {
		String[] recipients=new String[ officeEmailId.length];
		int i=0;
		for(String emailId:officeEmailId){
			recipients[i]=emailId;
			i++;
		}
		
		return recipients;
	}
	
	

}
