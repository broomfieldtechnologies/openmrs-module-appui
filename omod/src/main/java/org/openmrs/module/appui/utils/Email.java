package org.openmrs.module.appui.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//Reference: https://www.youtube.com/watch?v=LWkq8S8UFa4&ab_channel=SimplifyingTech
public class Email {
	private String from;
	private String to;
	private String smtp_host;
	private String subject;
	private String messageText;
	public void setFrom(String from) {
		this.from = from;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setHost(String host) {
		this.smtp_host = host;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setMessage(String message) {
		this.messageText = message;
	}
	public void send() {    

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", this.smtp_host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(this.from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.to));

	         // Set Subject: header field
	         message.setSubject(this.subject);

	         // Now set the actual message
	         message.setText(this.messageText);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException messageException) {
	         messageException.printStackTrace();
	      }
	   }
	}