package app.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.service.ReserveService;

/**
 * Servlet implementation class ReserveServlet
 */
@WebServlet("/api/book/reserve")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReserveServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("book[id]"));
		int accountId = (int) request.getSession().getAttribute("account_id");

		boolean result = ReserveService.call(bookId, accountId);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}

}
