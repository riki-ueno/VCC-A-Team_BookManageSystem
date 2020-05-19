package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Author;

public class AuthorsDAO extends DAOBase{
	public AuthorsDAO() {
		super();
	}

	public List<Author> all() {
		String sql = "SELECT name FROM authors order by name";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
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
}
