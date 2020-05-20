package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import app.model.Book;
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
				String str1 = rs1.getString("AUTHORS_NAME");
				String str2 = rs1.getString("GENRES_NAME");
				String authorNames = reString(str1);
				String genreNames = reString(str2);
				Rental rental = new Rental();
				rental.setReturnDeadline(rs1.getDate("RETURN_DEADLINE"));
				Book book = new Book();
				book.setAuthorNames(authorNames);
				book.setGenreNames(genreNames);
				Publisher publisher = new Publisher();
				book.setTitle(rs1.getString("TITLE"));
				book.setId(rs1.getInt("BOOKID"));
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
	private String creatRentalSql(String name) {
		String sql ="select \n" +
				"r.ID \n" +
				",b.TITLE \n" +
				",p.NAME pubulisher_name \n" +
				",r.RETURN_DEADLINE \n" +
				",b.ID as bookId \n" +
				",rt.RETURNED_AT \n" +
				",LISTAGG(ah.NAME, '/') WITHIN GROUP (order by ah.NAME) AS authors_name \n" +
				",LISTAGG(g.NAME, '/') WITHIN GROUP (order by g.NAME) AS genres_name \n" +
				" \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",ACCOUNTS a \n" +
				",PUBLISHERS p \n" +
				",RENTALS r \n" +
				",RETURNS rt \n" +
				",BOOKS_AUTHORS ba \n" +
				",AUTHORS ah \n" +
				",GENRES g \n" +
				",BOOKS_GENRES bg \n" +
				" \n" +
				"where 1=1 \n" +
				"and a.NAME = '"+name+"' \n" +
				"and a.ID = r.ACCOUNT_ID \n" +
				"and b.ID = r.BOOK_ID \n" +
				"and b.PUBLISHER_ID = p.ID \n" +
				"and r.ID = rt.RENTAL_ID(+) \n" +
				"and rt.RENTAL_ID is null \n" +
				"and b.ID = ba.BOOK_ID \n" +
				"and ba.AUTHOR_ID = ah.ID \n" +
				"and b.ID = bg.BOOK_ID \n" +
				"and bg.GENRE_ID = g.ID \n" +
				" \n" +
				"GROUP BY \n" +
				"r.ID,b.TITLE,p.NAME,r.RETURN_DEADLINE,b.ID,rt.RETURNED_AT \n" +
				" \n" +
				"order by \n" +
				"r.ID \n";
		return sql;
	}
}
