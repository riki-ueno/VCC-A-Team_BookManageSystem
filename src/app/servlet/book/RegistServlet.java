package app.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.AuthorsDAO;
import app.dao.BooksAuthorsDAO;
import app.dao.BooksDAO;
import app.dao.BooksGenresDAO;
import app.dao.GenresDAO;
import app.dao.PublishersDAO;
import app.model.Book;
import app.model.BooksAuthors;
import app.model.BooksGenres;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("OK"));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("book[title]");
		String[] authors = request.getParameterValues("authors[]");
		String publisherName = request.getParameter("publisher[name]");
		String[] genres = request.getParameterValues("genres[]");
		String purchaserName = request.getParameter("book[purchaserName]");
		String purchasedAtStr = request.getParameter("book[purchasedAt]");

		System.out.println(title);

		AuthorsDAO authorsDao = new AuthorsDAO();
		int[] authorIds = new int[authors.length];
		for (int i = 0; i < authors.length; i++) {
			String authorName = authors[i];
			authorsDao.checkAuthorNameAlreadyRegisterd(authorName);
			int authorId = authorsDao.convertAuthorNameToAuthorId(authorName);
			authorIds[i] = authorId;
			System.out.println(authorId);
		}

		PublishersDAO publishersDao = new PublishersDAO();
		publishersDao.checkPublisherNameAlreadyRegisterd(publisherName);
		int publisherId = publishersDao.convertPublisherNameToPublisherId(publisherName);
		System.out.println(publisherId);

		GenresDAO genresDao = new GenresDAO();
		int[] genreIds = new int[genres.length];
		for (int i = 0; i < genres.length; i++) {
			String genreName = genres[i];
			genresDao.checkGenreNameAlreadyRegisterd(genreName);
			int genreId = genresDao.convertGenreNameToGenreId(genreName);
			genreIds[i] = genreId;
			System.out.println(genreId);
		}

		HttpSession session = request.getSession(true);
		int registerId = (int) session.getAttribute("account_id");
		System.out.println(registerId);

		Date purchasedAt = Date.valueOf(purchasedAtStr);
		System.out.println(purchasedAt);

		BooksDAO booksDao = new BooksDAO();
		Book book = new Book();
		book.setTitle(title);
		book.setPublisherId(publisherId);
		book.setPurchasedAt(purchasedAt);
		book.setPurchaserName(purchaserName);
		book.setRegisterId(registerId);
		booksDao.create(book);

		int bookId = booksDao.convertTitleToBookId(title);

		BooksAuthorsDAO booksAuthorsDao = new BooksAuthorsDAO();
		for (int i = 0; i < authors.length; i++) {
			BooksAuthors booksAuthors = new BooksAuthors();
			booksAuthors.setBookId(bookId);
			booksAuthors.setAuthorId(authorIds[i]);
			booksAuthorsDao.create(booksAuthors);
		}

		BooksGenresDAO booksGenresDao = new BooksGenresDAO();
		for (int i = 0; i < genres.length; i++) {
			BooksGenres booksGenres = new BooksGenres();
			booksGenres.setBookId(bookId);
			booksGenres.setGenreId(genreIds[i]);
			booksGenresDao.create(booksGenres);
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString (bookId));
	}

}
