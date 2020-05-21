package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

import app.model.Account;

public class AccountsDAO extends DAOBase{
	public AccountsDAO() {
		super();
	}

	public Account login(String mailAddress, String hashedPassword) {
		String sql =
				"select "
					+ "ID, NAME, IS_LIBRARY_STAFF "
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
				account.setId(rs.getInt("id"));
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

	public boolean isReachTheNumberOfBooksLimit(int id) {
		String sql1 =
				"select count(*) count " +
				"from accounts " +
				         "left join RENTALS on ACCOUNTS.ID = RENTALS.ACCOUNT_ID " +
				         "left join RETURNS on RENTALS.ID = RETURNS.RENTAL_ID " +
				"where returns.RENTAL_ID is null " +
				  "and ACCOUNTS.ID = ?";
		String sql2 =
				"select count(*) count " +
				"from accounts " +
				         "right join books on ACCOUNTS.ID = BOOKS.RESERVER_ID " +
				"where ACCOUNTS.ID = ?";
		try (
				PreparedStatement pstmt1 = createPreparedStatement(sql1);
				PreparedStatement pstmt2 = createPreparedStatement(sql2);
		) {
			pstmt1.setInt(1, id);
			pstmt2.setInt(1, id);

			ResultSet rs1 = pstmt1.executeQuery();
			ResultSet rs2 = pstmt2.executeQuery();

			if (rs1.next() && rs2.next()) {
				return rs1.getInt("count") + rs2.getInt("count") >= 10;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public Account getByMailAddress(String mailAddress) {
		String sql =
				"select * from accounts where mail_address = ?";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, mailAddress);

			ResultSet rs = pstmt.executeQuery();

			Account account = new Account();

			if (rs.next()) {
				account.setId(rs.getInt("id"));
				account.setName(rs.getString("name"));
			}

			return account;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean updateAccountPassword(String mailAddress, String password) {
		String sql =
				"UPDATE accounts SET hashed_password = ? where mail_address = ?";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, DigestUtils.sha256Hex(password));
			pstmt.setString(2, mailAddress);

			int rsCount = pstmt.executeUpdate();

			return rsCount == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}


	}
}
