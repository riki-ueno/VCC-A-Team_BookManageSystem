package app.model;

import java.sql.Date;

public class Remind {
	private String mailAddress;
	private String accountName;
	private String bookTitle;
	private String publisherName;
	private Date scheduledReturnDate;

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Date getScheduledReturnDate() {
		return scheduledReturnDate;
	}

	public void setScheduledReturnDate(Date scheduledReturnDate) {
		this.scheduledReturnDate = scheduledReturnDate;
	}

}
