package app.model;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

public class Account {
	private int id;
	private String mailAddress;
	private String name;
	private String password;
	private String hashedPassword;
	private Date joinedAt;
	private int isLibraryStaff;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public Date getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
	}


	public int getIsLibraryStaff() {
		return isLibraryStaff;
	}

	public void setIsLibraryStaff(int isLibraryStaff) {
		this.isLibraryStaff = isLibraryStaff;
	}

	public Account build(HttpServletRequest request) {
		Account account = new Account();

		String id = request.getParameter("account[id]");
		String mailAddress = request.getParameter("account[mailAddress]");
		String name = request.getParameter("account[name]");
		String password = request.getParameter("account[password]");
		String joinedAt = request.getParameter("account[joinedAt]");

		if (NumberUtils.isDigits(id)) account.setId(Integer.parseInt(id));
		account.setMailAddress(mailAddress);
		account.setName(name);
		account.setPassword(password);
		if (joinedAt != null && !joinedAt.isEmpty()) account.setJoinedAt(Date.valueOf(joinedAt));

		return account;
	}
}
