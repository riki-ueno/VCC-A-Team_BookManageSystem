package app.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.model.BooksGenres;

public class BooksGenresDAO extends DAOBase {
	public BooksGenresDAO() {
		super();
	}

	public boolean create(BooksGenres booksGenres) {
		String sql = "INSERT INTO books_genres (book_id, genre_id) " + "VALUES (?, ?)";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {

			pstmt.setInt(1, booksGenres.getBookId());
			pstmt.setInt(2, booksGenres.getGenreId());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
