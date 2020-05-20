package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Author;
import app.model.Book;
import app.model.Genre;
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
				Reservation reservation = new Reservation();
				Book book = new Book();
				book.setTitle(rs1.getString("TITLE"));
				book.setId(rs1.getInt("BOOKID"));
				book.setPurchaserName(rs1.getString("PUBULISHER_NAME"));
				reservation.setReturnedAt(rs1.getDate("RETURNED_AT"));
				reservation.setBook(book);
				reservationList.add(reservation);
			}
			return reservationList;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
	public List<Author> authorList (String name) {
		String AuthorSql = creatReservationAuthorSql(name);
		try (
				PreparedStatement pstmt1 = createPreparedStatement(AuthorSql);
				ResultSet rs1 = pstmt1.executeQuery();
		) {
			List<Author> authorList  = new ArrayList<>();
			while(rs1.next()){
				Author author = new Author();
				author.setName(rs1.getString("AUTHORS_NAME"));
				authorList.add(author);
			}
			return authorList;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
	public List<Genre> genreList (String name) {
		String GenreSql = creatReservationGenreSql(name);
		try (
				PreparedStatement pstmt1 = createPreparedStatement(GenreSql);
				ResultSet rs1 = pstmt1.executeQuery();
		) {
			List<Genre> genreList  = new ArrayList<>();
			while(rs1.next()){
				Genre genre = new Genre();
				genre.setName(rs1.getString("GENRES_NAME"));
				genreList.add(genre);
			}
			return genreList;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
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
		String sql = "select \n" +
				"b.ID bookId \n" +
				",b.RESERVER_ID \n" +
				",b.TITLE \n" +
				",p.NAME pubulisher_name \n" +
				",rt.RETURNED_AT \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",PUBLISHERS p \n" +
				",RENTALS r \n" +
				",RETURNS rt \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and b.RESERVER_ID = a.ID \n" +
				"and b.PUBLISHER_ID = p.ID \n" +
				"and b.ID = r.BOOK_ID(+) \n" +
				"and r.ID = rt.RENTAL_ID(+) \n" +
				" \n" +
				"order by \n" +
				"b.ID \n";
		return sql;
	}
	private String creatReservationAuthorSql(String name) {
		String sql = "select \n" +
				"b.ID bookId \n" +
				",LISTAGG(ah.NAME, '/') WITHIN GROUP (order by ah.NAME) AS authors_name \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",BOOKS_AUTHORS ba \n" +
				",AUTHORS ah \n" +
				",RENTALS r \n" +
				",RETURNS rt \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and b.RESERVER_ID = a.ID \n" +
				"and ba.BOOK_ID = b.ID \n" +
				"and ah.ID = ba.AUTHOR_ID \n" +
				"and b.ID = r.BOOK_ID(+) \n" +
				"and r.ID = rt.RENTAL_ID(+) \n" +
				" \n" +
				"GROUP BY \n" +
				"b.ID,b.TITLE \n" +
				"order by \n" +
				"b.ID \n";
		return sql;
	}
	private String creatReservationGenreSql(String name) {
		String sql = "select  \n" +
				"b.ID as bookId \n" +
				",LISTAGG(g.NAME, '/') WITHIN GROUP (order by g.NAME) AS genres_name \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",RENTALS r \n" +
				",GENRES g \n" +
				",BOOKS_GENRES bg \n" +
				",RETURNS rt \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and b.RESERVER_ID = a.ID \n" +
				"and bg.BOOK_ID = b.ID \n" +
				"and g.ID = bg.GENRE_ID \n" +
				"and b.ID = r.BOOK_ID(+) \n" +
				"and r.ID = rt.RENTAL_ID(+) \n" +
				" \n" +
				"group by \n" +
				"b.ID \n" +
				" \n" +
				"order by \n" +
				"b.ID \n" +
				" \n";
		return sql;
	}
}
