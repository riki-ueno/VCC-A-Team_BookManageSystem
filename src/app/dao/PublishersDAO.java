package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Publisher;


public class PublishersDAO extends DAOBase{
	public PublishersDAO() {
		super();
	}

	public List<Publisher> all() {
		String sql = "SELECT name FROM publishers order by name";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			ResultSet rs = pstmt.executeQuery();
			List<Publisher> publisherList = new ArrayList<>();

			while (rs.next()) {
				Publisher publisher= new Publisher();
				publisher.setName(rs.getString("name"));
				publisherList.add(publisher);
			}

			return publisherList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
