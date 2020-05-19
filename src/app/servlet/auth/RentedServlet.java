package app.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.RentedDAO;

/**
 * Servlet implementation class RentingServlet
 */
@WebServlet("/api/auth/rented")
public class RentedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RentedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RentedDAO rentedDAO = new RentedDAO();
		HttpSession session = request.getSession(true);
		String name = (String) session.getAttribute("account_name");
		String parameter = request.getParameter("parameter");
		PrintWriter pw = response.getWriter();
		if(parameter.equals("rental")){
			pw.append(new ObjectMapper().writeValueAsString(rentedDAO.rentalList(name)));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
