function loginCertification(){
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/BookManageSystem/api/auth/loginCertification',
		success : function(json) {
			if(json.result !== "true"){
				alert('ログインしてください')
				location.href = "/BookManageSystem/login.html"
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('データベースへの更新に失敗しました。');
		}
	});
}

function initial() {
	'use strict'

	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/genre/index",
		dataType: "json",
		success: function(genreList) {
			let html = "<option value=\"\">指定しない</option>"
			genreList.forEach(function(genre) {
				html += "<option>" + genre.name + "</option>"
			})
			$("#genre_name").append(html)
		}
	})

	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/author/index",
		dataType: "json",
		success: function(authorList) {
			let html = ""
			authorList.forEach(function(author) {
				html += '<option value="' + author.name + '">'
			})
			$("#author_name_keywords").append(html)
		}
	})

	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/publisher/index",
		dataType: "json",
		success: function(publisherList) {
			let html = ""
			publisherList.forEach(function(publisher) {
				html += '<option value="' + publisher.name + '">'
			})
			$("#publisher_name_keywords").append(html)
		}
	})
}

function submit() {
	'use strict'

	const bookTitle = $("#book_title").val()
	const publisherName = $("#publisher_name").val()
	const authorName = $("#author_name").val()
	const genreName = $("#genre_name").val()
	const bookStatus = $("#book_status").val()

	const requestQuery =
		{
			book: {
				title: bookTitle,
				status: bookStatus
			},
			publisher: {
				name: publisherName
			},
			author: {
				name: authorName
			},
			genre: {
				name: genreName
			}
		}

	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/book/search",
		data: requestQuery,
		dataType: "json",
		success: function(bookList) {

			let html = ""
			bookList.forEach(function(book, index) {
				let status;
				if (book.reserverId != 0) {
					status = "予約中"
				} else if (book.rentals[0].returnObj.returnedAt == null && book.rentals[0].returnDeadline != null) {
					status = "貸出中"
				} else {
					status = "貸出可"
				}

				let button;
				switch (status) {
					case "予約中":
						button = ""
						break
					case "貸出中":
						button = "<input class='uk-button uk-button-default uk-button-small' book_id='" + book.id + "' type='button' onclick='reserve(this)' value='予約' />"
						break
					case "貸出可":
						button = "<input class='uk-button uk-button-default uk-button-small' book_id='" + book.id + "' type='button' onclick='rental(this)' value='借りる' />"
				}

				let returnDeadline;
				if (book.rentals[0].returnDeadline != null) {
					let date = new Date()
					date.setDate(date.getDate() + 1)
					if (Date.parse(book.rentals[0].returnDeadline) < Date.parse(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate())) {
						returnDeadline = "<td class='uk-text-danger'>" + (book.rentals[0].returnDeadline || "") + "</td>"
					} else {
						returnDeadline = "<td>" + book.rentals[0].returnDeadline + "</td>"
					}
				} else {
					returnDeadline = "<td></td>"
				}

				html += ("<tr>" +
					"<td>" + (index + 1) + "</td>" +
					"<td>" + (book.genreNames || "") + "</td>" +
					"<td><a href='/BookManageSystem/book/show.html?bookId=" + book.id + "'>" + book.title + "</a></td>" +
					"<td>" + (book.publisher.name || "") + "</td>" +
					"<td>" + (book.authorNames || "") + "</td>" +
					"<td>" + status + "</td>" +
					returnDeadline +
					"<td>" + button + "</td>" +
				"</tr>")
			})
			$("#book_list_table tbody tr").remove();
			$("#book_list_table tbody").append(html);
		}
	})
}

function rental(button) {
	const buttonElem = $(button)
	const bookId = buttonElem.attr("book_id")
	const bookTitle = buttonElem.parent().prev().prev().prev().prev().prev().text()

	if (confirm("レンタルする本の確認\nタイトル: " + bookTitle + "\nで間違いないですか？")) {
		$.ajax({
			type : 'POST',
			url : '/BookManageSystem/api/book/rental',
			dataType: "json",
			data: {book: {id: bookId}},
			success: function(result) {
				if (result === true) {
					let date = new Date()
					date.setDate(date.getDate() + 14)
					alert("レンタルが完了しました。\n返却期限は" + date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate()+ "です。")
					submit()
				} else {
					alert("レンタルに失敗しました。時間を空けて再度行ってみてください。")
				}
			}
		})
	}
}

function reserve(button) {
	const buttonElem = $(button)
	const bookId = buttonElem.attr("book_id")
	const bookTitle = buttonElem.parent().prev().prev().prev().prev().prev().text()

	if (confirm("予約する本の確認\nタイトル: " + bookTitle + "\nで間違いないですか？")) {
		$.ajax({
			type : 'POST',
			url : '/BookManageSystem/api/book/reserve',
			dataType: "json",
			data: {book: {id: bookId}},
			success: function(result) {
				if (result === true) {
					alert("予約しました")
					submit()
				} else {
					alert("予約に失敗しました。時間を空けて再度行ってみてください。")
				}
			}
		})
	}
}

$(document).ready(function () {
	'use strict'

	loginCertification()
    initial()
    $("#submit").bind('click', submit)
});