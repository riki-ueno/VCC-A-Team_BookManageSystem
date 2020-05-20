package app.service;

import java.util.HashMap;
import java.util.List;

import app.dao.BooksDAO;
import app.model.Book;

public class SearchService {
	public static List<Book> call(String bookTitle, String bookStatus, String authorName, String publisherName, String genreName){
		HashMap<String, String> searchCondition = new HashMap<String, String>();
		if (!bookTitle.isEmpty()) searchCondition.put("books.title LIKE ?", "%" + bookTitle + "%");
		if (!authorName.isEmpty()) searchCondition.put("author_name_table.author_names LIKE ?", "%" + authorName + "%");
		if (!publisherName.isEmpty()) searchCondition.put("publishers.name LIKE ?", "%" + publisherName + "%");
		if (!genreName.isEmpty()) searchCondition.put("genre_name_table.genre_names LIKE ?", "%" + genreName + "%");
		String bookCondition = "1 = 1";
		switch(bookStatus) {
			case "指定しない":
				break;
			case "利用可":
				bookCondition = "(returns.rental_id is not null or main.ID is null) and books.RESERVER_ID is null";
				break;
			case "貸出中":
				bookCondition = "returns.rental_id is null";
				break;
			case "予約中":
				bookCondition = "books.reserver_id is not null";
				break;
		}

		return new BooksDAO().all(searchCondition, bookCondition);
	}
}
