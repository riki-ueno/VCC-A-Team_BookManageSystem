package app.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordChangeDAO extends DAOBase {
	public PasswordChangeDAO() {
		super();
	}
	public void passwordChange(String account_name, String hashedPassword) {
		String sql ="UPDATE ACCOUNTS \n" +
				"SET    HASHED_PASSWORD = '"+hashedPassword+"' \n" +
				"WHERE NAME='"+account_name+"'";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.executeUpdate();
			System.out.println("test");

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
