package app.servlet.genre;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.GenresDAO;
import app.model.Genre;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/api/genre/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Genre> genreList = new GenresDAO().all();
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(genreList));
	}
}
