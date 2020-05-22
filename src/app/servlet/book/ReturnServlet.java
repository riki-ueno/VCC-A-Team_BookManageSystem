package app.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.ReturnsDAO;
import app.model.Return;

/**
 * Servlet implementation class Return
 */
@WebServlet("/api/auth/Return")
public class ReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReturnServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReturnsDAO returnsDAO = new ReturnsDAO();
		String rentalId = request.getParameter("rentalId");
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(returnsDAO.returnBookdetail(rentalId)));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rentalId = Integer.parseInt(request.getParameter("rental[id]"));

		Return returnObject = new Return();

		returnObject.setRentalId(rentalId);

		boolean result = new ReturnsDAO().create(returnObject);
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}
}
