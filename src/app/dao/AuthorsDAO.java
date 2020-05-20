package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sql = "INSERT INTO authors (id, name) " + "VALUES (?, ?)";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {

			pstmt.setInt(1, author.getId());
			pstmt.setString(2, author.getName());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public int[] convertAuthorNameArrayToAuthorIdArray(String[] authorNames) {
		int[] authorIds = new int[authorNames.length];
		for (int i = 0; i < authorNames.length; i++) {
			String authorName = authorNames[i];
			int authorId = convertAuthorNameToAuthorId(authorName);
			authorIds[i] = authorId;
		}
		return authorIds;

	}

	public int convertAuthorNameToAuthorId(String authorName) {
		String sql = "SELECT a.id FROM authors a WHERE a.name = '" + authorName + "'";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int authorId = rs.getInt(1);
				return authorId;
			} else {
				AuthorsDAO authorsDao = new AuthorsDAO();
				List<Author> authorsList = authorsDao.all();
				int newAuthorId = authorsList.size() + 1;
				Author author = new Author();
				author.setId(newAuthorId);
				author.setName(authorName);
				authorsDao.create(author);
				return newAuthorId;
			}

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
