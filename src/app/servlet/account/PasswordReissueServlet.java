package app.servlet.account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.service.PasswordReissueService;

/**
 * Servlet implementation class PasswordReissueServlet
 */
@WebServlet("/api/account/password/reissue")
public class PasswordReissueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PasswordReissueServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mailAddress = request.getParameter("account[mailAddress]");
		boolean result = PasswordReissueService.call(mailAddress);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}

}
