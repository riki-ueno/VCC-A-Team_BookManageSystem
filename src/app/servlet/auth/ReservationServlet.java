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

import app.dao.ReservationDAO;

/**
 * Servlet implementation class ReservationServlet
 */
@WebServlet("/api/auth/reservation")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReservationDAO reservationDAO = new ReservationDAO();
		HttpSession session = request.getSession(true);
		String name = (String) session.getAttribute("account_name");
		String parameter = request.getParameter("parameter");
		PrintWriter pw = response.getWriter();
		if(parameter.equals("Reservation")){
			pw.append(new ObjectMapper().writeValueAsString(reservationDAO.ReservationList(name)));
		}else if(parameter.equals("ReservationAuthor")){
			pw.append(new ObjectMapper().writeValueAsString(reservationDAO.authorList(name)));
		}else if(parameter.equals("ReservationGenre")){
			pw.append(new ObjectMapper().writeValueAsString(reservationDAO.genreList(name)));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReservationDAO reservationDAO = new ReservationDAO();
		String bookId = request.getParameter("bookId");
		reservationDAO.ReservationCancel(bookId);
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

}
