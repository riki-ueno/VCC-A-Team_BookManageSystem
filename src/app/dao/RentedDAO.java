package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Author;
import app.model.Book;
import app.model.Genre;
import app.model.Publisher;
import app.model.Rental;

public class RentedDAO extends DAOBase {

	public RentedDAO() {
		super();
	}

	public List<Rental> rentalList (String name) {
		String RentalSql= creatRentalSql(name);
		try (
				PreparedStatement pstmt1 = createPreparedStatement(RentalSql);
				ResultSet rs1 = pstmt1.executeQuery();
		) {
			List<Rental> rentalList = new ArrayList<>();
			while(rs1.next()){
				Rental rental = new Rental();
				rental.setReturnDeadline(rs1.getDate("RETURN_DEADLINE"));
				Book book = new Book();
				Publisher publisher = new Publisher();
				book.setTitle(rs1.getString("TITLE"));
				publisher.setName(rs1.getString("PUBULISHER_NAME"));
				book.setPublisher(publisher);
				rental.setBook(book);
				rental.setId(rs1.getInt("ID"));
				rentalList.add(rental);
			}
			return rentalList;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
	public List<Genre> genreList (String name) {
		String GenreSql = creatGenreSql(name);
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
	public List<Author> authorList (String name) {
		String AuthorSql = creatAuthorSql(name);
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
	private String creatGenreSql(String name) {
		String sql = "select  \n" +
				"r.ID \n" +
				",b.TITLE  \n" +
				",LISTAGG(g.NAME, '/') WITHIN GROUP (order by g.NAME) AS genres_name \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",RENTALS r \n" +
				",GENRES g \n" +
				",BOOKS_GENRES bg \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and a.ID = r.ACCOUNT_ID \n" +
				"and b.ID = r.BOOK_ID \n" +
				"and b.ID = bg.BOOK_ID \n" +
				"and bg.GENRE_ID = g.ID \n" +
				" \n" +
				"group by \n" +
				"r.ID,b.TITLE \n" +
				" \n" +
				"order by \n" +
				"r.ID \n";
		return sql;
	}

	private String creatAuthorSql(String name) {
		String sql = "select  \n" +
				"r.ID \n" +
				",b.TITLE  \n" +
				",LISTAGG(ah.NAME, '/') WITHIN GROUP (order by ah.NAME) AS authors_name \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",BOOKS_AUTHORS ba \n" +
				",AUTHORS ah \n" +
				",RENTALS r \n" +
				" \n" +
				" \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and a.ID = r.ACCOUNT_ID \n" +
				"and b.ID = r.BOOK_ID \n" +
				"and b.ID = ba.BOOK_ID \n" +
				"and ba.AUTHOR_ID = ah.ID \n" +
				" \n" +
				" \n" +
				"GROUP BY  \n" +
				"r.ID,b.TITLE \n" +
				"order by \n" +
				"r.ID \n";
		return sql;
	}

	private String creatRentalSql(String name) {
		String sql = "select  \n" +
				"r.ID \n" +
				",b.TITLE  \n" +
				",p.NAME pubulisher_name \n" +
				",r.RETURN_DEADLINE \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",PUBLISHERS p \n" +
				",RENTALS r \n" +
				" \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and a.ID = r.ACCOUNT_ID \n" +
				"and b.ID = r.BOOK_ID \n" +
				"and b.PUBLISHER_ID = p.ID \n" +
				" \n" +
				" \n" +
				"order by \n" +
				"r.ID \n";
		return sql;
	}
}
