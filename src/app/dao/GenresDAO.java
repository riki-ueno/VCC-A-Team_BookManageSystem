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
		String sql = "INSERT INTO genres (id, name) " + "VALUES (?, ?)";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {

			pstmt.setInt(1, genre.getId());
			pstmt.setString(2, genre.getName());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public int[] convertGenreNameArrayToGenreIdArray(String[] genreNames) {
		int[] genreIds = new int[genreNames.length];
		for (int i = 0; i < genreNames.length; i++) {
			String genreName = genreNames[i];
			int genreId = convertGenreNameToGenreId(genreName);
			genreIds[i] = genreId;
		}
		return genreIds;
	}

	public int convertGenreNameToGenreId(String genreName) {
		String sql = "SELECT g.id FROM genres g WHERE g.name = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, genreName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int genreId = rs.getInt(1);
				return genreId;
			} else {
				GenresDAO genreDao = new GenresDAO();
				List<Genre> genreList = genreDao.all();
				int newGenreId = genreList.size() + 1;
				Genre genre = new Genre();
				genre.setId(newGenreId);
				genre.setName(genreName);
				genreDao.create(genre);
				return newGenreId;
			}

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
