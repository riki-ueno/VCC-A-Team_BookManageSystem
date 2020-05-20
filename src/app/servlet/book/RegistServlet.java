package app.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet(name = "RegistServlet", urlPatterns = { "/api/book/regist" })
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("book[title]");
		String purchaserName = request.getParameter("book[purchaserName]");
		String purchasedAt = request.getParameter("book[purchasedAt]");
		String publisherName = request.getParameter("publisher[name]");
		String[] genres = request.getParameterValues("genres[]");
		String[] authors = request.getParameterValues("authors[]");

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("OK"));
	}

}
