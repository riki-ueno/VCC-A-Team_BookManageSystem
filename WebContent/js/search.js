function loginCertification(){
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/BookManageSystem/api/auth/status',
		success : function(json) {
			if(json.result !== "true"){
				alert('ログインしてください')
				location.href = "./login.html"
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('データベースへの更新に失敗しました。');
			console.log(errorThrown)
		}
	});
}

function initialize() {
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

	console.log(requestQuery)



	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/book/search",
		data: requestQuery,
		dataType: "json",
		success: function(bookList) {
			console.log(bookList)

			let html = ""
			bookList.forEach(function(book, index) {
				console.log(book)
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
						button = "<input class='uk-button uk-button-default uk-button-small' type='button' value='予約' />"
						break
					case "貸出可":
						button = "<input class='uk-button uk-button-default uk-button-small' type='button' value='借りる' />"
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
					"<td>" + book.title + "</td>" +
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

$(document).ready(function () {
	'use strict'

	loginCertification()
    initialize()
    $("#submit").bind('click', submit)
});