package com.dikshatech.portal.mail;

import java.io.FileNotFoundException;

import javax.mail.internet.AddressException;

import com.mysql.jdbc.Field;

/**
 * This is a client program for testing purpose to generate a mail using MailGenerator.
 * @author manish
 *
 */
public class Client
{
	
	public PortalMail getPortalMail() throws AddressException, FileNotFoundException
	{
		PortalMail pMail = new PortalMail();

		pMail.setCandidateName("Y. Suresh");
		pMail.setRecipientMailId("supriya.bhike@dikshatech.com");

		Attachements[] attachements = new Attachements[1];
		Attachements attachement = new Attachements();
		attachement.setFilePath("/home/supriya.bhike/Desktop/Diksha Holiday Calendar.pdf");
		attachement.setFileName("OfferLetter.pdf");
		attachements[0] = attachement;
		
		pMail.setFileSources(attachements);
		pMail.setMailSubject("Offer Letter");
		pMail.setTemplateName(MailSettings.CANDIDATE_OFFER);
		
		pMail.setOfferedDesignation("Software Engineer");

		return pMail;
	}

	public static void main(String[] arg)
	{
		MailGenerator mGenerator = new MailGenerator();
		try
		{
		//	mGenerator.invoker(new Client().getPortalMail());
//			String value="degreeCourse=BE~=~type=fdg~=~subjectMajor=eng~=~startDate=2011/10/10~=~yearPassing=2001~=~yearPassingNull=false~=~studIdNoEnrollNo=fffgf~=~collegeUniversity=Bangalore~=~gradePercentage=1st~=~gradutionDate=2011/10/10,"
//                         +"degreeCourse=MCA~=~type=jduhyfuj~=~subjectMajor=hgycv~=~startDate=2011/10/10~=~yearPassing=2011~=~yearPassingNull=false~=~studIdNoEnrollNo=jdfijkid~=~collegeUniversity=Pune~=~gradePercentage=2nd~=~gradutionDate=2011/10/10,"
//                         +"degreeCourse=btech~=~type=FullTime~=~subjectMajor=IT~=~startDate=2011/10/10~=~yearPassing=2011~=~yearPassingNull=false~=~studIdNoEnrollNo=hbhb~=~collegeUniversity=Mumbai";
//			String String_Array[]= value.toString().split(",");
			
			Integer field[]=new Integer[]{12,123,1};
			String files[]=new String[field.length];
			for(int i=0;i<field.length;i++){
				 files[i]= String.valueOf(field[i]);
				
			 }
			 System.out.println("file"+files);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
