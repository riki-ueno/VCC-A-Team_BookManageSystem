package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.Account;

public class AccountsDAO extends DAOBase{
	public AccountsDAO() {
		super();
	}

	public Account login(String mailAddress, String hashedPassword) {
		String sql =
				"select "
					+ "NAME, IS_LIBRARY_STAFF "
				+ "from "
					+ "ACCOUNTS "
				+ "where "
					+ "MAIL_ADDRESS = ? "
				+ "AND "
					+ "HASHED_PASSWORD = ?";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			Account account = new Account();
			pstmt.setString(1, mailAddress);
			pstmt.setString(2, hashedPassword);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				account.setName(rs.getString("name"));
				int isLibraryStaff = rs.getInt("is_library_staff");
				account.setIsLibraryStaff(isLibraryStaff);
			}

			System.out.println("test");

			return account;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
