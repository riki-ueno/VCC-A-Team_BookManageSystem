package app.model;

import java.sql.Date;

public class Rental {
	private int id;
	private int accountId;
	private Account account;
	private int bookId;
	private Book book;
	private Date returnDeadline;
	private Date insertedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getReturnDeadline() {
		return returnDeadline;
	}

	public void setReturnDeadline(Date returnDeadline) {
		this.returnDeadline = returnDeadline;
	}

	public Date getInsertedAt() {
		return insertedAt;
	}

	public void setInsertedAt(Date insertedAt) {
		this.insertedAt = insertedAt;
	}
}
