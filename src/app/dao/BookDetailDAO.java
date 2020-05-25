package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import app.model.BookDetail;

public class BookDetailDAO extends DAOBase {

	public BookDetailDAO() {
		super();
	}
	public BookDetail bookdetail (String bookID) {
		String sql= creatBookDetailSql(bookID);
		try (
				PreparedStatement pstmt1 = createPreparedStatement(sql);
				ResultSet rs1 = pstmt1.executeQuery();
		) {
			BookDetail bookdetail = new BookDetail();
			if(rs1.next()){
				bookdetail.setTitle(rs1.getString("TITLE"));
				bookdetail.setPublisherNmae(rs1.getString("PUBLISHERNAME"));
				String str1 = rs1.getString("AUTHORS_NAME");
				String str2 = rs1.getString("GENRES_NAME");
				String authorNames = reString(str1);
				String genreNames = reString(str2);
				bookdetail.setAuthorsName(authorNames);
				bookdetail.setGenresName(genreNames);
				int rentalID = rs1.getInt("RENTALID");
				int returnID = rs1.getInt("RETURNID");
				int reserverID = rs1.getInt("RESERVER_ID");
				if(rentalID == 0){
					bookdetail.setStatus("貸出可");
				}else if(returnID != 0 && reserverID == 0){
					bookdetail.setStatus("貸出可");
					bookdetail.setLastRentedName(rs1.getString("RENTEDNAME"));
				}else if(returnID == 0 && reserverID == 0){
					bookdetail.setStatus("貸出中");
					bookdetail.setLastRentedName(rs1.getString("RENTEDNAME"));
					bookdetail.setRentedName(rs1.getString("RENTEDNAME"));
					bookdetail.setReturnDeadline(rs1.getDate("RETURN_DEADLINE"));
				}else if(reserverID != 0){
					bookdetail.setStatus("予約中");
					bookdetail.setLastRentedName(rs1.getString("RENTEDNAME"));
					bookdetail.setRentedName(rs1.getString("RENTEDNAME"));
					bookdetail.setReturnDeadline(rs1.getDate("RETURN_DEADLINE"));
					bookdetail.setReserver(rs1.getString("RESERVER"));
				}
				bookdetail.setPurchaserName(rs1.getString("PURCHASER_NAME"));
				bookdetail.setPurchasedAt(rs1.getDate("PURCHASED_AT"));
				bookdetail.setRigistrantName(rs1.getString("RIGISTRANTNAME"));
				bookdetail.setRigisteredAt(rs1.getDate("REGISTERED_AT"));
				bookdetail.setUpdaterName(rs1.getString("UPDATERNAME"));
				bookdetail.setUpdatedAt(rs1.getDate("UPDATED_AT"));
			}
			return bookdetail;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
	private String creatBookDetailSql(String bookID) {
		String sql = "select \n" +
				"b.TITLE \n" +
				",p.NAME publisherName \n" +
				",LISTAGG(a.NAME, '/') WITHIN GROUP (order by a.NAME) AS authors_name \n" +
				",LISTAGG(g.NAME, '/') WITHIN GROUP (order by g.NAME) AS genres_name \n" +
				",r.ID rentalId \n" +
				",rt.ID returnId \n" +
				",b.RESERVER_ID \n" +
				",r.RETURN_DEADLINE \n" +
				",ac.NAME rentedName \n" +
				",ac2.NAME reserver \n" +
				",b.PURCHASER_NAME \n" +
				",b.PURCHASED_AT \n" +
				",ac3.NAME rigistrantName \n" +
				",b.REGISTERED_AT \n" +
				",ac4.NAME updaterName \n" +
				",b.UPDATED_AT \n" +
				" \n" +
				"from \n" +
				"BOOKS b \n" +
				",BOOKS_AUTHORS ba \n" +
				",AUTHORS a \n" +
				",PUBLISHERS p \n" +
				",BOOKS_GENRES bg \n" +
				",GENRES g \n" +
				",RENTALS r \n" +
				",RETURNS rt \n" +
				",ACCOUNTS ac \n" +
				",ACCOUNTS ac2 \n" +
				",ACCOUNTS ac3 \n" +
				",ACCOUNTS ac4 \n" +
				"where 1=1 \n" +
				"and b.ID = '"+bookID+"' \n" +
				"and ba.BOOK_ID = b.ID \n" +
				"and a.ID = ba.AUTHOR_ID \n" +
				"and b.PUBLISHER_ID = p.ID \n" +
				"and bg.BOOK_ID = b.ID \n" +
				"and bg.GENRE_ID = g.ID \n" +
				"and b.ID = r.BOOK_ID(+) \n" +
				"and r.ID = rt.RENTAL_ID(+) \n" +
				"and r.ACCOUNT_ID = ac.ID(+)  \n" +
				"and b.RESERVER_ID = ac2.ID(+)  \n" +
				"and b.REGISTER_ID = ac3.ID(+)  \n" +
				"and b.UPDATER_ID = ac4.ID(+)  \n" +
				"GROUP BY \n" +
				"b.TITLE,p.NAME,r.ID,r.RETURN_DEADLINE,rt.ID,b.RESERVER_ID,ac.NAME,ac2.NAME,b.PURCHASER_NAME,b.PURCHASED_AT,ac3.NAME,b.REGISTERED_AT,b.UPDATED_AT,ac4.NAME \n" +
				"order by \n" +
				"r.ID DESC \n";
		return sql;
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
}
