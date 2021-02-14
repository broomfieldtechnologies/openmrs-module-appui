package org.openmrs.module.appui.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
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

public class SendEmail {
	 
	 public static void send(
			 final String from, 
			 String to, 
			 String subject,
			 String msg,
			 final String password, 
			 String smtpHost, 
			 String smtpPort
			 //List<File> filesToAttach
		) throws IOException {	 
	  Properties prop = new Properties();
	  prop.put("mail.smtp.host", smtpHost);
	  prop.put("mail.smtp.port", smtpPort);
	  prop.put("mail.smtp.auth", "true");
	  prop.put("mail.smtp.socketFactory.port", smtpPort);
	  prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	  
	  Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
	   protected PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication(from, password);
	   }
	  });
	 
	  try {    
	   MimeBodyPart mimeBodyPart = new MimeBodyPart();
	   mimeBodyPart.setContent(msg, "text/html");
	   Multipart multipart = new MimeMultipart();
	   multipart.addBodyPart(mimeBodyPart);
	    
	   /*
	   MimeBodyPart attachmentBodyPart = new MimeBodyPart();
	   
	   for (File fileToAttach: filesToAttach) {
		   attachmentBodyPart.attachFile(fileToAttach);
	   }
	   multipart.addBodyPart(attachmentBodyPart);
	   */
	   
	   Message message = new MimeMessage(session);
	   message.setFrom(new InternetAddress(from));
	   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	   message.setSubject(subject);
	   message.setContent(multipart);
	   
	   Transport.send(message);
	 
	   System.out.println("Mail successfully sent..");
	 
	  } catch (MessagingException e) {
	   e.printStackTrace();
	  }
	 }
	}
