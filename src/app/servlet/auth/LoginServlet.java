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

import app.dao.AccountsDAO;
import app.model.Account;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/api/auth/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mailAddress = request.getParameter("account[mailAddress]");
		String password = request.getParameter("account[password]");

		AccountsDAO accountsDAO = new AccountsDAO();
		Account account = accountsDAO.login(mailAddress, DigestUtils.sha256Hex(password));

		if (account.getName() != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("id", account.getId());
			session.setAttribute("account_name", account.getName());
			session.setAttribute("account_is_library_staff", account.getIsLibraryStaff());
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(account.getName() != null));
	}

}
