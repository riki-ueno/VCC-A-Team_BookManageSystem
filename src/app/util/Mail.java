package app.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	public static void send(String subject, String content, String reciever) {

		final String to = reciever;
		final String from = "testaddress.lib@gmail.com";

		final String username = "testaddress.lib@gmail.com"; // Google account
		final String password = "library111/"; // Google App password

		// final String charset = "ISO-2022-JP";
		final String charset = "UTF-8";

		final String encoding = "base64";

		// for gmail
		String host = "smtp.gmail.com";
		String port = "587";
		String starttls = "true";

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", starttls);

		props.put("mail.smtp.connectiontimeout", "10000");// timeoutの設定
		props.put("mail.smtp.timeout", "10000");

		props.put("mail.debug", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);

			// Set From:
			message.setFrom(new InternetAddress(from, "図書管理システム"));
			// Set ReplyTo:
			message.setReplyTo(new Address[] { new InternetAddress(from) });
			// Set To:
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject(subject, charset);
			message.setText(content, charset);

			message.setHeader("Content-Transfer-Encoding", encoding);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

}