package app.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.model.BooksAuthors;

public class BooksAuthorsDAO extends DAOBase {
	public BooksAuthorsDAO() {
		super();
	}

	public boolean create(BooksAuthors booksAuthors) {
		String sql = "INSERT INTO books_authors (book_id, author_id) " + "VALUES ( ?, ?)";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {

			pstmt.setInt(1, booksAuthors.getBookId());
			pstmt.setInt(2, booksAuthors.getAuthorId());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean delete(int bookId) {
		String sql = "DELETE FROM books_authors WHERE book_id = ?";

		try (
			PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setInt(1, bookId);

			int count = pstmt.executeUpdate();

			return count > 0;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("削除処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
