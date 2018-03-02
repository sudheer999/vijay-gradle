package com.dikshatech.portal.mail;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.PropertyLoader;

public class PendingMails {

	private static Properties	properties;
	private static PendingMails	pendingmail	= null;
	private int					fileId		= 0;
	private String				fPath		= PropertyLoader.getEnvVariable() + File.separator + "email" + File.separator + "pending";
	private List<Integer>		fileList	= new Vector<Integer>();
	private Thread				mailThread	= null;
	/**
	 * while creating thread is it need to perform sending mails.
	 */
	private boolean				preRun		= true;
	private static Logger		logger		= LoggerUtil.getLogger(PendingMails.class);

	private PendingMails() {
		fileId = lastFileModified(fPath);
		properties = PropertyLoader.loadProperties("conf.email.properties");
		if (!fileList.isEmpty()) createThread();
	}

	private void createThread() {
		// if thread already running then return;
		if (mailThread != null) return;
		mailThread = new Thread() {

			@Override
			public void run() {
				while (!fileList.isEmpty()){
					if (preRun) readPendingMails();
					else preRun = true;
					if (!fileList.isEmpty()) try{
						Thread.sleep(2 * 60 * 60 * 1000);
					} catch (InterruptedException e){}
				}
				if (fileList.isEmpty()) fileId = 0;
				mailThread = null;
			}
		};
		mailThread.setName("MAIL_THREAD");
		mailThread.start();
	}

	public static PendingMails getInstance() {
		if (pendingmail == null) pendingmail = new PendingMails();
		return pendingmail;
	}

	public void savePendingEmails(PortalMail pmail) {
		logger.info("Mail sending faild :- Subject : " + pmail.getMailSubject() + " \n TO : " + (pmail.getRecipientMailId() != null ? pmail.getRecipientMailId() : Arrays.toString(pmail.getAllReceipientMailId())) + (pmail.getAllReceipientcCMailId() == null ? "" : (" \n CC : " + Arrays.toString(pmail.getAllReceipientcCMailId()))) + (pmail.getAllReceipientBCCMailId() == null ? "" : (" \n BCC : " + Arrays.toString(pmail.getAllReceipientBCCMailId()))));
		String str = ++fileId + "";
		ObjectOutputStream oos;
		try{
			oos = new ObjectOutputStream(new FileOutputStream(new File(fPath + "/" + str + ".obj")));
			oos.writeObject(pmail);
			fileList.add(fileId);
			oos.flush();
			oos.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		preRun = false;
		createThread();
	}

	public void readPendingMails() {
		File f = null;
		ObjectInputStream ois = null;
		PortalMail pMail = null;
		while (!fileList.isEmpty()){
			String mail = fileList.get(0) + ".obj";
			try{
				f = new File(fPath + "/" + mail);
				if (f.isFile()){
					ois = new ObjectInputStream(new FileInputStream(f));
					pMail = (PortalMail) ois.readObject();
					if (ois != null) try{
						ois.close();
					} catch (IOException e){}
					// log and delete if the mail is older than two days
					if ((new Date().getTime() - f.lastModified()) / (24 * 60 * 60 * 1000) >= 2){
						// add to log and delete
						logger.info("Mail sending faild permanently :-subject: " + pMail.getMailSubject() + " \n TO : " + (pMail.getRecipientMailId() != null ? pMail.getRecipientMailId() : Arrays.toString(pMail.getAllReceipientMailId())) + (pMail.getAllReceipientcCMailId() == null ? "" : (" \n CC : " + Arrays.toString(pMail.getAllReceipientcCMailId()))) + (pMail.getAllReceipientBCCMailId() == null ? "" : (" \n BCC : " + Arrays.toString(pMail.getAllReceipientBCCMailId()))) + " SENT ON: "
								+ new Date(f.lastModified()) + "\nDetails:" + pMail.getMailBody());
					} else sendPendingMails(pMail);
				}
				f.delete();
				fileList.remove(0);
			} catch (AddressException e){
				// add to db
				logger.info("Mail sending faild permanently :-subject: " + pMail.getMailSubject() + " \n TO : " + (pMail.getRecipientMailId() != null ? pMail.getRecipientMailId() : Arrays.toString(pMail.getAllReceipientMailId())) + (pMail.getAllReceipientcCMailId() == null ? "" : (" \n CC : " + Arrays.toString(pMail.getAllReceipientcCMailId()))) + (pMail.getAllReceipientBCCMailId() == null ? "" : (" \n BCC : " + Arrays.toString(pMail.getAllReceipientBCCMailId()))) + "\nDetails:"
						+ pMail.getMailBody());
				logger.info(e.toString());
				logger.error(e.toString());
				fileList.remove(0);
				if (ois != null) try{
					ois.close();
				} catch (IOException ee){}
				f.delete();
				continue;
			} catch (MessagingException e){
				break;
			} catch (Exception e){
				// add to db
				fileList.remove(0);
				f.delete();
				continue;
			}
		}
		if (ois != null) try{
			ois.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	private void sendPendingMails(PortalMail pMail) throws Exception {
		logger.info("Mail sending :- SUBJECT:" + pMail.getMailSubject() + " TO:" + pMail.getRecipientMailId());
		new Mailer(properties);
		Session session = Mailer.getSession();
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(properties.getProperty(MailSettings.FROM_MAILID)));
		message = MailGenerator.setMailInfo(pMail, message);
		message.saveChanges();
		Mailer.sendMail((MimeMessage) message, session);
	}

	public int lastFileModified(String dir) {
		File fl = new File(dir);
		PortalUtility.createDirectory(fl);
		File[] files = fl.listFiles(new FileFilter() {

			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choise = new File("0.obj");
		if (files != null) for (File file : files){
			fileList.add(Integer.parseInt(file.getName().replace(".obj", "").trim()));
			if (file.lastModified() > lastMod){
				choise = file;
				lastMod = file.lastModified();
			}
		}
		Collections.sort(fileList);
		return Integer.parseInt(choise.getName().replace(".obj", "").trim());
	}

	public void notifyPendingMails() {
		if (mailThread != null) mailThread.interrupt();
	}

	public void stop() {
		fileList.clear();
		notifyPendingMails();
	}

	public static void main(String[] args) {
		getInstance().notifyPendingMails();
	}
}
