package app.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.model.Rental;

public class RentalsDAO extends DAOBase{
	public RentalsDAO() {
		super();
	}

	public boolean create(Rental rental) {
		String sql =
				"INSERT INTO RENTALS (ACCOUNT_ID, BOOK_ID) " +
				"VALUES (?, ?)";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {

			pstmt.setInt(1, rental.getAccountId());
			pstmt.setInt(2, rental.getBookId());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
