package app.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.PasswordChangeDAO;

/**
 * Servlet implementation class PasswordChangeServlet
 */
@WebServlet("/api/auth/passwordChange")
public class PasswordChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		HttpSession session = request.getSession(true);
		String account_name = (String)session.getAttribute("account_name");

		PasswordChangeDAO passwordChangeDao = new PasswordChangeDAO();
		passwordChangeDao.passwordChange(account_name, DigestUtils.sha256Hex(password));
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

}
