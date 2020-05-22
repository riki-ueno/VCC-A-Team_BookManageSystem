package app.service.book;

import java.sql.Date;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import app.dao.AuthorsDAO;
import app.dao.BooksAuthorsDAO;
import app.dao.BooksDAO;
import app.dao.BooksGenresDAO;
import app.dao.GenresDAO;
import app.dao.PublishersDAO;
import app.model.Book;
import app.model.BooksAuthors;
import app.model.BooksGenres;

public class EditService {
	public static boolean call(HttpServletRequest request) {
	    Enumeration names = request.getParameterNames();
	    while (names.hasMoreElements()){
	      System.out.println((String)names.nextElement());
	    }
		int accountId = (int) request.getSession().getAttribute("account_id");
		int bookId    = Integer.parseInt(request.getParameter("book[id]"));
		String bookTitle     = request.getParameter("book[title]");
		String publisherName = request.getParameter("publisher[name]");
		String purchaserName = request.getParameter("book[purchaserName]");
		String purchasedAt   = request.getParameter("book[purchasedAt]");
		List<String> authors = Arrays.asList(request.getParameterValues("authors[]"));
		List<String> genres  = Arrays.asList(request.getParameterValues("genres[]"));

		BooksAuthorsDAO booksAuthorsDAO = new BooksAuthorsDAO();
		BooksGenresDAO  booksGenresDAO  = new BooksGenresDAO();
		AuthorsDAO      authorsDAO      = new AuthorsDAO();
		GenresDAO       genresDAO       = new GenresDAO();
		PublishersDAO   publishersDAO    = new PublishersDAO();
		BooksDAO        booksDAO        = new BooksDAO();

		booksAuthorsDAO.delete(bookId);
		booksGenresDAO.delete(bookId);

		for (String authorName : authors) {
			authorsDAO.checkAuthorNameAlreadyRegisterd(authorName);
			int authorId = authorsDAO.convertAuthorNameToAuthorId(authorName);

			BooksAuthors booksAuthors = new BooksAuthors();
			booksAuthors.setAuthorId(authorId);
			booksAuthors.setBookId(bookId);
			booksAuthorsDAO.create(booksAuthors);
		}

		for (String genreName : genres) {
			genresDAO.checkGenreNameAlreadyRegisterd(genreName);
			int genreId = genresDAO.convertGenreNameToGenreId(genreName);

			BooksGenres booksGenres = new BooksGenres();
			booksGenres.setGenreId(genreId);
			booksGenres.setBookId(bookId);
			booksGenresDAO.create(booksGenres);
		}

		publishersDAO.checkPublisherNameAlreadyRegisterd(publisherName);
		int publisherId = publishersDAO.convertPublisherNameToPublisherId(publisherName);

		Book book = new Book();
		book.setId(bookId);
		book.setTitle(bookTitle);
		book.setPublisherId(publisherId);
		book.setPurchaserName(purchaserName);
		book.setPurchasedAt(Date.valueOf(purchasedAt));
		book.setUpdaterId(accountId);

		return booksDAO.update(book);
	}
}
