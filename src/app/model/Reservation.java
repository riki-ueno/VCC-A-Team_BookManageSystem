package app.model;

import java.sql.Date;

public class Reservation {
	private int id;
	private int account;
	private int bookId;
	private Book book;
	private Date returnedAt;
	private String bookAuthorsName;
	private String bookGenresName;
	public String getBookAuthorsName() {
		return bookAuthorsName;
	}
	public void setBookAuthorsName(String bookAuthorsName) {
		this.bookAuthorsName = bookAuthorsName;
	}
	public String getBookGenresName() {
		return bookGenresName;
	}
	public void setBookGenresName(String bookGenresName) {
		this.bookGenresName = bookGenresName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
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
	public Date getReturnedAt() {
		return returnedAt;
	}
	public void setReturnedAt(Date returnedAt) {
		this.returnedAt = returnedAt;
	}
}
