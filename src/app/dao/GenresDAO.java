package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Genre;

public class GenresDAO extends DAOBase{
	public GenresDAO() {
		super();
	}

	public List<Genre> all() {
		String sql = "SELECT name FROM genres order by name";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			ResultSet rs = pstmt.executeQuery();
			List<Genre> genreList = new ArrayList<>();

			while (rs.next()) {
				Genre genre = new Genre();
				genre.setName(rs.getString("name"));
				genreList.add(genre);
			}

			return genreList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
