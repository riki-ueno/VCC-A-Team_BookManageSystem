package app.service;

import app.dao.BooksDAO;

public class ReserveService {
	public static boolean call(int bookId, int accountId) {
		BooksDAO booksDAO = new BooksDAO();

		boolean result =
				!booksDAO.isAlreadyReserved(bookId) &&
				booksDAO.reserve(bookId, accountId);

		return result;
	}
}
