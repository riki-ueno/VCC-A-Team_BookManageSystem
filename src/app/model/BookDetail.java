package app.model;

import java.sql.Date;

public class BookDetail {
	private String title;
	private String publisherNmae;
	private String authorsName;
	private String genresName;
	private String status;
	private Date returnDeadline;
	private String rentedName;
	private String reserver;
	private String lastRentedName;
	private String purchaserName;
	private Date purchasedAt;
	private String rigistrantName;
	private Date rigisteredAt;
	private String updaterName;
	private Date updatedAt;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublisherNmae() {
		return publisherNmae;
	}
	public void setPublisherNmae(String publisherNmae) {
		this.publisherNmae = publisherNmae;
	}
	public String getAuthorsName() {
		return authorsName;
	}
	public void setAuthorsName(String authorsName) {
		this.authorsName = authorsName;
	}
	public String getGenresName() {
		return genresName;
	}
	public void setGenresName(String genresName) {
		this.genresName = genresName;
	}
	public Date getReturnDeadline() {
		return returnDeadline;
	}
	public void setReturnDeadline(Date returnDeadline) {
		this.returnDeadline = returnDeadline;
	}
	public String getRentedName() {
		return rentedName;
	}
	public void setRentedName(String rentedName) {
		this.rentedName = rentedName;
	}
	public String getReserver() {
		return reserver;
	}
	public void setReserver(String reserver) {
		this.reserver = reserver;
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
	public String getRigistrantName() {
		return rigistrantName;
	}
	public void setRigistrantName(String rigistrantName) {
		this.rigistrantName = rigistrantName;
	}
	public String getUpdaterName() {
		return updaterName;
	}
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
	public Date getRigisteredAt() {
		return rigisteredAt;
	}
	public void setRigisteredAt(Date rigisteredAt) {
		this.rigisteredAt = rigisteredAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastRentedName() {
		return lastRentedName;
	}
	public void setLastRentedName(String lastRentedName) {
		this.lastRentedName = lastRentedName;
	}
}
