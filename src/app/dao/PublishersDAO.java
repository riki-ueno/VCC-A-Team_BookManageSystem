package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Publisher;

public class PublishersDAO extends DAOBase {
	public PublishersDAO() {
		super();
	}

	public List<Publisher> all() {
		String sql = "SELECT name FROM publishers order by name";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			List<Publisher> publisherList = new ArrayList<>();

			while (rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setName(rs.getString("name"));
				publisherList.add(publisher);
			}

			return publisherList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean create(Publisher publisher) {
		String sql = "INSERT INTO publishers (name) " + "VALUES (?)";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {

			pstmt.setString(1, publisher.getName());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public int convertPublisherNameToPublisherId(String publisherName) {
		int publisherId = 0;
		String sql = "SELECT id FROM publishers WHERE name = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, publisherName);
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				publisherId = rs.getInt(1);
			}

			return publisherId;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean checkPublisherNameAlreadyRegisterd(String publisherName) {
		String sql = "SELECT id FROM publishers WHERE name = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, publisherName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				Publisher publisher = new Publisher();
				publisher.setName(publisherName);
				create(publisher);
				return false;
			}

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

}
