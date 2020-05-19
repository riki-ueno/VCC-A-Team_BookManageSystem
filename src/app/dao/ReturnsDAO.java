package app.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.model.Return;

public class ReturnsDAO extends DAOBase{
	public ReturnsDAO() {
		super();
	}

	public boolean create(Return returnObject) {
		String sql =
				"INSERT INTO RETURNS (RENTAL_ID) " +
				"VALUES (?)";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {

			pstmt.setInt(1, returnObject.getRentalId());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
