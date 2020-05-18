package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOBase {
	public DAOBase() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバーのロードに失敗しました。詳細:[%s]",e.getMessage()));
		}
	}

	public PreparedStatement createPreparedStatement(String sql) throws SQLException {
		Connection con = createConnection();
		return con.prepareStatement(sql);
	}

	public Connection createConnection() throws SQLException {
		final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		final String USER_NAME = "a_team";
		final String PASSWORD  = "a_team";
		return DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
	}
}
