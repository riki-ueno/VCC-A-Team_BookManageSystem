package app.service;

import app.dao.AccountsDAO;
import app.dao.BooksDAO;
import app.dao.RentalsDAO;
import app.model.Rental;

public class RentalService {
	public static boolean call(int bookId, int accountId) {
		Rental rental = new Rental();
		rental.setBookId(bookId);
		rental.setAccountId(accountId);

		AccountsDAO accountsDAO = new AccountsDAO();
		BooksDAO    booksDAO   = new BooksDAO();
		RentalsDAO  rentalsDAO = new RentalsDAO();

		if (booksDAO.isAvailableForRental(bookId, accountId) && !accountsDAO.isReachTheNumberOfBooksLimit(accountId)) {
			return rentalsDAO.create(rental);
		} else {
			return false;
		}
	}
}
