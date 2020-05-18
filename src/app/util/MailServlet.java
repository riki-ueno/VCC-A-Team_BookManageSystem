package app.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MailServlet
 */
@WebServlet("/MailServlet")
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String title = "title";// request.getParameter("title");
		String message = "message";// request.getParameter("message");

		System.out.println("タイトル：" + title);
		System.out.println("メッセージ:" + message);

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = response.getWriter();

		try {
			Properties property = new Properties();

			property.put("mail.smtp.host", "smtp.gmail.com");

			// GmailのSMTPを使う場合
			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.port", "587");
			property.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("testaddress.lib@gmail.com", "library111/");
				}
			});

			/*
			 * //一般的なSMTPを使う場合
			 * 
			 * //ポートが25の場合は省略可能 property.put("mail.smtp.port", 25);
			 * 
			 * Session session = Session.getDefaultInstance(property, null);
			 */

			MimeMessage mimeMessage = new MimeMessage(session);

			InternetAddress toAddress = new InternetAddress("testaddress.lib@gmail.com", "受信者");

			mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);

			InternetAddress fromAddress = new InternetAddress("testaddress.lib@gmail.com", "送信者");

			mimeMessage.setFrom(fromAddress);

			mimeMessage.setSubject(title, "ISO-2022-JP");

			mimeMessage.setText(message, "ISO-2022-JP");

			Transport.send(mimeMessage);

			pw.println("<htm><body>");
			pw.println("■お問い合わせ内容を担当者へ送信しました。");
			pw.println("<body></html>");
		} catch (Exception e) {
			pw.println("<html><body>");
			pw.println("■担当者への送信に失敗しました");
			pw.println("<br>エラーの内容" + e);
			pw.println("</body></html>");
		}

		pw.close();
	}

}
