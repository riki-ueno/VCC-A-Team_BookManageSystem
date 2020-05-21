package app.model;
import java.sql.Date;
import java.util.List;
public class Book {
	private int id;
	private String title;
	private int publisherId;
	private Publisher publisher;
	private String purchaserName;
	private Date purchasedAt;
	private int registerId;
	private Account register;
	private Date registeredAt;
	private int updaterId;
	private Account updater;
	private Date updatedAt;
	private int reserverId;
	private Account reserver;
	private String status;
	private List<Author> authors;
	private String authorNames;
	private List<Genre> genres;
	private String genreNames;
	private List<Rental> rentals;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public Date getPurchasedAt() {
		return purchasedAt;
	}
	public void setPurchasedAt(Date purchasedAt) {
		this.purchasedAt = purchasedAt;
	}
	public int getRegisterId() {
		return registerId;
	}
	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}
	public Account getRegister() {
		return register;
	}
	public void setRegister(Account register) {
		this.register = register;
	}
	public Date getRegisteredAt() {
		return registeredAt;
	}
	public void setRegisteredAt(Date registeredAt) {
		this.registeredAt = registeredAt;
	}
	public int getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(int updaterId) {
		this.updaterId = updaterId;
	}
	public Account getUpdater() {
		return updater;
	}
	public void setUpdater(Account updater) {
		this.updater = updater;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getReserverId() {
		return reserverId;
	}
	public void setReserverId(int reserverId) {
		this.reserverId = reserverId;
	}
	public Account getReserver() {
		return reserver;
	}
	public void setReserver(Account reserver) {
		this.reserver = reserver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public String getAuthorNames() {
		return authorNames;
	}

	public void setAuthorNames(String authorNames) {
		this.authorNames = authorNames;
	}

	public String getGenreNames() {
		return genreNames;
	}

	public void setGenreNames(String genreNames) {
		this.genreNames = genreNames;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}
}
