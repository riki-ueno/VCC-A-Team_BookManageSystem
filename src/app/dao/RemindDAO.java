package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.model.Remind;

public class RemindDAO extends DAOBase {
	public RemindDAO() {
		super();
	}

	public List<Remind> RemindList() {
		String sql = "select a.MAIL_ADDRESS, a.NAME, b.TITLE, pub.NAME, rent.RETURN_DEADLINE \n"
				+ "from  RENTALS rent, ACCOUNTS a, BOOKS b, PUBLISHERS pub \n"
				+ "where rent.ACCOUNT_ID = a.ID and rent.BOOK_ID = b.ID  " + "and b.PUBLISHER_ID = pub.ID \n"
				+ "and rent.RETURN_DEADLINE < current_date \n" + "and not exists (select 1 \n"
				+ "from RETURNS ret \n" + "where rent.ID = ret.RENTAL_ID) \n" + "order by rent.ID ";

		try (Connection con = createConnection();) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Remind> remindList = new ArrayList<>();

			while (rs.next()) {
				Remind remind = new Remind();
				remind.setMailAddress(rs.getString(1));
				remind.setAccountName(rs.getString(2));
				remind.setBookTitle(rs.getString(3));
				remind.setPublisherName(rs.getString(4));
				remind.setScheduledReturnDate(rs.getDate(5));
				remindList.add(remind);
			}
			System.out.println(remindList.size());
			return remindList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
