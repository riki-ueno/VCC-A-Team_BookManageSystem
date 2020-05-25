package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.model.Author;
import app.model.Book;
import app.model.Genre;
import app.model.Publisher;
import app.model.Rental;

public class BooksDAO extends DAOBase {
	public BooksDAO() {
		super();
	}

	public boolean create(Book book) {
		String sql = "INSERT INTO books (TITLE, PUBLISHER_ID, PURCHASED_AT, PURCHASER_NAME,	REGISTER_ID) VALUES(?,?,?,?,?)";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {

			pstmt.setString(1, book.getTitle());
			pstmt.setInt(2, book.getPublisherId());
			pstmt.setDate(3, book.getPurchasedAt());
			System.out.println(book.getPurchasedAt());
			pstmt.setString(4, book.getPurchaserName());
			pstmt.setInt(5, book.getRegisterId());
			int count = pstmt.executeUpdate();

			return count == 1;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public int convertTitleToBookId(String title) {
		int bookId = 0;
		String sql = "SELECT id FROM books WHERE title = ?";
		try (PreparedStatement pstmt = createPreparedStatement(sql);) {
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bookId = rs.getInt(1);
			}
			return bookId;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean isAvailableForRental(int bookId, int accountId) {
		String sql1 = "select * " + "from books " + "right join rentals " + "on BOOKS.ID = RENTALS.BOOK_ID "
				+ "left join returns "
				+ "on rentals.id = returns.RENTAL_ID " + "where books.id = ? " + "and returns.RENTAL_ID is null "
				+ "order by rentals.id desc";
		String sql2 = "select * from books where books.id = ? and (books.RESERVER_ID is null or books.RESERVER_ID = ?)";
		try (PreparedStatement pstmt1 = createPreparedStatement(sql1);
				PreparedStatement pstmt2 = createPreparedStatement(sql2);) {

			pstmt1.setInt(1, bookId);

			pstmt2.setInt(1, bookId);
			pstmt2.setInt(2, accountId);
			ResultSet rs1 = pstmt1.executeQuery();
			ResultSet rs2 = pstmt2.executeQuery();

			return !rs1.next() && rs2.next();

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public List<Book> all(HashMap<String, String> searchCondition, String selectCondition, String sortCondition) {
		String sql =
				"select BOOKS.ID, " +
						"BOOKS.TITLE, " +
						"BOOKS.RESERVER_ID, " +
						"PUBLISHERS.NAME PUBLISHER_NAME, " +
						"author_name_table.author_names, " +
						"genre_name_table.genre_names, " +
						"rental_count_table.rental_count, " +
						"case " +
							"when main.RETURN_DEADLINE is not NULL AND RETURNS.RETURNED_AT is not null then null " +
							"else main.RETURN_DEADLINE " +
							"end RETURN_DEADLINE " +
				"from BOOKS " +
						"LEFT JOIN ( " +
					"select BOOKS.ID books_id, LISTAGG(AUTHORS.NAME, '/') within group (order by AUTHORS.NAME) author_names " +
					"from BOOKS " +
							"left join BOOKS_AUTHORS BA on BOOKS.ID = BA.BOOK_ID " +
							"left join AUTHORS on BA.AUTHOR_ID = AUTHORS.ID " +
					"group by BOOKS.ID " +
				") author_name_table on author_name_table.books_id = BOOKS.ID " +
						"LEFT JOIN ( " +
					"select BOOKS.ID books_id, LISTAGG(GENRES.NAME, '/') within group (order by GENRES.NAME) genre_names " +
					"from BOOKS " +
						"left join BOOKS_GENRES BG on BOOKS.ID = BG.BOOK_ID " +
						"left join GENRES on BG.GENRE_ID = GENRES.ID " +
					"group by BOOKS.ID " +
				") genre_name_table on genre_name_table.books_id = BOOKS.ID " +
						"LEFT JOIN (" +
					"select rentals.book_id books_id, count(*) rental_count " +
					"from RENTALS " +
					"group by BOOK_ID " +
				") rental_count_table on rental_count_table.books_id = BOOKS.ID " +
						"LEFT JOIN PUBLISHERS on BOOKS.PUBLISHER_ID = PUBLISHERS.ID " +
						"LEFT JOIN RENTALS main on BOOKS.ID = main.BOOK_ID " +
						"LEFT JOIN RENTALS sub on sub.BOOK_ID = main.BOOK_ID and sub.id > main.ID " +
						"LEFT JOIN RETURNS on main.id = RETURNS.RENTAL_ID " +
				"where sub.ID is null ";

		String searchConditionQuery = String.join(" AND ", searchCondition.keySet());
		if (searchConditionQuery.isEmpty()) {
			searchConditionQuery += selectCondition;
		} else {
			searchConditionQuery += (" AND " + selectCondition);
		}

		sql += (" AND " + searchConditionQuery + sortCondition);

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);) {
			int indexCount = 1;
			for (String key : searchCondition.keySet()) {
				pstmt.setString(indexCount, searchCondition.get(key));
				indexCount++;
			}

			System.out.println(sql);

			ResultSet rs = pstmt.executeQuery();

			List<Book> bookList = new ArrayList<Book>();

			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setReserverId(rs.getInt("reserver_id"));
				book.setAuthorNames(rs.getString("author_names"));
				book.setGenreNames(rs.getString("genre_names"));
				List<Rental> rentalList = new ArrayList<Rental>();
				Rental rental = new Rental();
				rental.setReturnDeadline(rs.getDate("return_deadline"));
				rentalList.add(rental);
				book.setRentals(rentalList);
				Publisher publisher = new Publisher();
				publisher.setName(rs.getString("publisher_name"));
				book.setPublisher(publisher);
				bookList.add(book);
			}

			return bookList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean isAlreadyReserved(int bookId) {
		String sql = "select * from books where id = ? and reserver_id is not null";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {

			pstmt.setInt(1, bookId);

			ResultSet rs = pstmt.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean reserve(int bookId, int accountId) {
		String sql = "update books set reserver_id = ? where id = ?";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {

			pstmt.setInt(1, accountId);
			pstmt.setInt(2, bookId);

			int rsCount = pstmt.executeUpdate();

			return rsCount == 1;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public Book get(int bookId) {
		String sql =
				"select BOOKS.ID, " +
				"       BOOKS.TITLE, " +
				"       BOOKS.PURCHASER_NAME, " +
				"       BOOKS.PURCHASED_AT, " +
				"       PUBLISHERS.NAME PUBLISHER_NAME, " +
				"       author_name_table.author_names, " +
				"       genre_name_table.genre_names " +
				"from BOOKS" +
				"         LEFT JOIN (select BOOKS.ID                                                        books_id, " +
				"                           LISTAGG(AUTHORS.NAME, '/') within group (order by AUTHORS.NAME) author_names " +
				"                    from BOOKS " +
				"                             left join BOOKS_AUTHORS BA on BOOKS.ID = BA.BOOK_ID " +
				"                             left join AUTHORS on BA.AUTHOR_ID = AUTHORS.ID " +
				"                    group by BOOKS.ID) author_name_table " +
				"                   on author_name_table.books_id = BOOKS.ID " +
				"         LEFT JOIN (select BOOKS.ID books_id, LISTAGG(GENRES.NAME, '/') within group (order by GENRES.NAME) genre_names " +
				"                    from BOOKS " +
				"                             left join BOOKS_GENRES BG on BOOKS.ID = BG.BOOK_ID " +
				"                             left join GENRES on BG.GENRE_ID = GENRES.ID " +
				"                    group by BOOKS.ID) genre_name_table on genre_name_table.books_id = BOOKS.ID " +
				"         LEFT JOIN PUBLISHERS on BOOKS.PUBLISHER_ID = PUBLISHERS.ID " +
				"where BOOKS.ID = ? " +
				"order by BOOKS.ID";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {

			pstmt.setInt(1, bookId);

			ResultSet rs = pstmt.executeQuery();

			Book book = new Book();

			if (rs.next()) {
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setPurchaserName(rs.getString("purchaser_name"));
				book.setPurchasedAt(rs.getDate("purchased_at"));
				Publisher publisher = new Publisher();
				publisher.setName(rs.getString("publisher_name"));
				book.setPublisher(publisher);
				System.out.println(rs.getString("author_names"));
				List<Author> authorList = new ArrayList<Author>();
				for (String authorName : rs.getString("author_names").split("/")) {
					Author author = new Author();
					author.setName(authorName);
					authorList.add(author);
				}
				book.setAuthors(authorList);
				List<Genre> genreList = new ArrayList<Genre>();
				for (String genreName : rs.getString("genre_names").split("/")) {
					Genre genre = new Genre();
					genre.setName(genreName);
					genreList.add(genre);
				}
				book.setGenres(genreList);
			}

			return book;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean update(Book book) {
		String sql =
				"UPDATE books "
				+ "SET title = ?, publisher_id = ?, purchaser_name = ?, purchased_at = ?, updater_id = ? "
				+ "where id = ?";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, book.getTitle());
			pstmt.setInt(2, book.getPublisherId());
			pstmt.setString(3, book.getPurchaserName());
			pstmt.setDate(4, book.getPurchasedAt());
			pstmt.setInt(5, book.getUpdaterId());
			pstmt.setInt(6, book.getId());

			int rsCount = pstmt.executeUpdate();

			return rsCount == 1;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

}
