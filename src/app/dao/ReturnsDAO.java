package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.BookDetail;
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
	public BookDetail returnBookdetail (String rentalId) {
		String sql= creatBookDetailSql(rentalId);
		try (
				PreparedStatement pstmt1 = createPreparedStatement(sql);
				ResultSet rs1 = pstmt1.executeQuery();
		) {
			BookDetail bookdetail = new BookDetail();
			if(rs1.next()){
				bookdetail.setTitle(rs1.getString("TITLE"));
				bookdetail.setPublisherNmae(rs1.getString("PUBLISHERNAME"));
				bookdetail.setReturnDeadline(rs1.getDate("RETURN_DEADLINE"));
				bookdetail.setAuthorsName(rs1.getString("AUTHORS_NAME"));
				bookdetail.setBookId(rs1.getInt("ID"));
			}
			return bookdetail;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
	private String creatBookDetailSql(String rentalId) {
		String sql = "select \n" +
				"b.ID \n" +
				",b.TITLE \n" +
				",p.NAME publisherName \n" +
				",r.RETURN_DEADLINE \n" +
				",LISTAGG(a.NAME, '/') WITHIN GROUP (order by a.NAME) AS authors_name \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",BOOKS_AUTHORS ba \n" +
				",AUTHORS a \n" +
				",RENTALS r \n" +
				",PUBLISHERS p \n" +
				" \n" +
				"where 1=1 \n" +
				"and r.ID = '"+rentalId+"' \n" +
				"and r.BOOK_ID = b.ID \n" +
				"and ba.BOOK_ID = b.ID \n" +
				"and a.ID = ba.AUTHOR_ID \n" +
				"and b.PUBLISHER_ID = p.ID \n" +
				" \n" +
				"group by \n" +
				"b.TITLE,p.NAME,r.RETURN_DEADLINE,b.ID \n";
		return sql;
	}
}
