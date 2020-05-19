package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksDAO extends DAOBase{
	public BooksDAO() {
		super();
	}

	public boolean isAvailableForRental(int bookId, int accountId) {
		String sql1 =
				"select * " +
				"from books " +
						"right join rentals " +
								"on BOOKS.ID = RENTALS.BOOK_ID " +
						"left join returns " +
								"on rentals.id = returns.RENTAL_ID " +
				"where books.id = ? " +
				  "and returns.RENTAL_ID is null " +
				"order by rentals.id desc";
		String sql2 =
				"select * from books where books.id = ? and (books.RESERVER_ID is null or books.RESERVER_ID = ?)";
		try (
				PreparedStatement pstmt1 = createPreparedStatement(sql1);
				PreparedStatement pstmt2 = createPreparedStatement(sql2);
		) {

			pstmt1.setInt(1, bookId);

			pstmt2.setInt(1, bookId);
			pstmt2.setInt(2, accountId);
			ResultSet rs1 = pstmt1.executeQuery();
			ResultSet rs2 = pstmt2.executeQuery();

			return !rs1.next() && rs2.next();

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
