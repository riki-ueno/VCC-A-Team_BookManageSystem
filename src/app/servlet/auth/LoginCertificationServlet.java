package app.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LoginCertificationServlet
 */
@WebServlet("/api/auth/status")
public class LoginCertificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCertificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(true);
		String account_name = (String) session.getAttribute("account_name");
		PrintWriter pw = response.getWriter();
		Map <String, String> responseData = new HashMap<>();
		if(account_name != null) {
			responseData.put("result", "true");
			responseData.put("account_name", account_name);
			responseData.put("account_is_library_staff", session.getAttribute("account_is_library_staff").toString());
			pw.append(new ObjectMapper().writeValueAsString(responseData));
		}else {
			responseData.put("result", "false");
			pw.append(new ObjectMapper().writeValueAsString(responseData));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
