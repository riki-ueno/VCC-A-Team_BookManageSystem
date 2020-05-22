package app.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.model.Book;
import app.service.SearchService;

/**
 * Servlet implementation class Search
 */
@WebServlet("/api/book/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookTitle     = request.getParameter("book[title]");
		String bookStatus    = request.getParameter("book[status]");
		String authorName    = request.getParameter("author[name]");
		String publisherName = request.getParameter("publisher[name]");
		String genreName     = request.getParameter("genre[name]");

		List<Book> bookList = SearchService.call(bookTitle, bookStatus, authorName, publisherName, genreName);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(bookList));
	}
}
