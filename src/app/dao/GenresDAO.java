package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Genre;

public class GenresDAO extends DAOBase {
	public GenresDAO() {
		super();
	}

	public List<Genre> all() {
		String sql = "SELECT name FROM genres order by name";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
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

	public boolean create(Genre genre) {
		String sql = "INSERT INTO genres (name) " + "VALUES (?)";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {

			pstmt.setString(1, genre.getName());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public int convertGenreNameToGenreId(String genreName) {
		int genreId = 0;
		String sql = "SELECT id FROM genres WHERE name = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, genreName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				genreId = rs.getInt(1);
			}
			return genreId;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean checkGenreNameAlreadyRegisterd(String genreName) {
		String sql = "SELECT id FROM genres WHERE name = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, genreName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				Genre genre = new Genre();
				genre.setName(genreName);
				create(genre);
				return false;
			}

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

}
