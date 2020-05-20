package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.model.Author;

public class AuthorsDAO extends DAOBase {
	public AuthorsDAO() {
		super();
	}

	public List<Author> all() {
		String sql = "SELECT name FROM authors order by name";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			List<Author> authorList = new ArrayList<>();

			while (rs.next()) {
				Author author = new Author();
				author.setName(rs.getString("name"));
				authorList.add(author);
			}

			return authorList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean create(Author author) {
		String sql = "INSERT INTO authors (name) " + "VALUES (?)";
		try (Connection con = createConnection(); PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, author.getName());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public int convertAuthorNameToAuthorId(String authorName) {
		int authorId = 0;
		String sql = "SELECT id FROM authors WHERE name = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, authorName);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				authorId = rs.getInt(1);
			}

			return authorId;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean checkAuthorNameAlreadyRegisterd(String authorName) {
		String sql = "SELECT id FROM authors WHERE name = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, authorName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				Author author = new Author();
				author.setName(authorName);
				create(author);
				return false;
			}

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
