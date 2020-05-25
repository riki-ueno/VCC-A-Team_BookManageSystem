package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import app.model.Book;
import app.model.Reservation;

public class ReservationDAO extends DAOBase {

	public ReservationDAO() {
		super();
	}
	public List<Reservation> ReservationList (String name) {
		String reservationSql= creatReservationSql(name);
		try (
				PreparedStatement pstmt1 = createPreparedStatement(reservationSql);
				ResultSet rs1 = pstmt1.executeQuery();
		) {
			List<Reservation> reservationList = new ArrayList<>();
			while(rs1.next()){
				String str1 = rs1.getString("AUTHORS_NAME");
				String str2 = rs1.getString("GENRES_NAME");
				String authorNames = reString(str1);
				String genreNames = reString(str2);
				Reservation reservation = new Reservation();
				Book book = new Book();
				book.setAuthorNames(authorNames);
				book.setGenreNames(genreNames);
				book.setTitle(rs1.getString("TITLE"));
				book.setId(rs1.getInt("BOOKID"));
				BooksDAO BooksDAO = new BooksDAO();
				reservation.setAvailableForRental(BooksDAO.isAvailableForRental(rs1.getInt("BOOKID"), rs1.getInt("ID")));
				book.setPurchaserName(rs1.getString("PUBULISHER_NAME"));
				reservation.setBook(book);
				reservationList.add(reservation);
			}
			return reservationList;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
	private String reString(String str) {
		String[] str1 = str.split("/", 0);
		Object[] str2 = eliminateDuplicates(str1);
		String[] str3 = new String[str2.length];
		for(int i=0;i<str2.length;i++){
			str3[i] = (String) str2[i];
		}
		String strFinal = String.join("/", str3);
		return strFinal;
	}
	private static Object[] eliminateDuplicates(String[] strings) {

	    // LinkedHashSetオブジェクトを用意
	    Set<String> linkedHashSet = new LinkedHashSet<String>();

	    // 配列の要素を順にLinkedHashSetオブジェクトへ追加
	    for (int i = 0; i < strings.length; i++) {
	    linkedHashSet.add(strings[i]);
	    }

	    // LinkedHashSetオブジェクトを配列に変換
	    Object[] strings_after = linkedHashSet.toArray();

	    return strings_after;
	  }
	public void ReservationCancel(String bookId){
		String sql="update BOOKS \n" +
				"set RESERVER_ID = '' \n" +
				"where ID = '"+bookId+"'";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.executeUpdate();
			System.out.println("test");

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
	private String creatReservationSql(String name) {
		String sql ="select \n" +
				"a.ID \n" +
				",b.ID bookId \n" +
				",b.RESERVER_ID \n" +
				",b.TITLE \n" +
				",p.NAME pubulisher_name \n" +
				",LISTAGG(ah.NAME, '/') WITHIN GROUP (order by ah.NAME) AS authors_name \n" +
				",LISTAGG(g.NAME, '/') WITHIN GROUP (order by g.NAME) AS genres_name \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",PUBLISHERS p \n" +
				",BOOKS_AUTHORS ba \n" +
				",AUTHORS ah \n" +
				",GENRES g \n" +
				",BOOKS_GENRES bg \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and b.RESERVER_ID = a.ID \n" +
				"and b.PUBLISHER_ID = p.ID \n" +
				"and ba.BOOK_ID = b.ID \n" +
				"and ah.ID = ba.AUTHOR_ID \n" +
				"and bg.BOOK_ID = b.ID \n" +
				"and g.ID = bg.GENRE_ID \n" +
				" \n" +
				"group by \n" +
				"b.ID,b.RESERVER_ID,b.TITLE,p.NAME,a.ID \n" +
				"order by \n" +
				"b.ID \n";
		return sql;
	}
}
