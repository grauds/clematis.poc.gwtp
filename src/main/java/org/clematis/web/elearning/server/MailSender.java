package org.clematis.web.elearning.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.clematis.web.elearning.shared.domain.EmailAddress;

public class MailSender {
	
 public static void sendMail(EmailAddress emailAddress, String subject, String body) throws Exception {

    final String username = "anton.troshin@gmail.com";
	final String password = "wNbYg1Yx";
	
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	
	Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	});
	
	try {
	
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("noreply@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress.toString()));
		message.setSubject(subject);
		message.setText(body);
	
		Transport.send(message);

	} catch (MessagingException e) {
		throw new Exception(e);
	}
 }

}
