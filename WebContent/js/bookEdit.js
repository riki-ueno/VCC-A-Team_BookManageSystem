function loginCertification(){
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/BookManageSystem/api/auth/loginCertification',
		success : function(json) {
			if(json.result !== "true"){
				alert('ログインしてください')
				location.href = "./login.html"
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('データベースへの更新に失敗しました。');
		}
	});
}

function bookInitialize() {
	let parameter  = location.search.substring( 1, location.search.length )
	parameter = decodeURIComponent(parameter)
	const bookId = parameter.split('=')[1]
	const requestQuery = {book: {id: bookId}}


	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/genre/index",
		dataType: "json",
		success: function(genreList) {
			let html = ""
			genreList.forEach(function(genre) {
				html += "<option>" + genre.name + "</option>"
			})
			$("#genre_name_keywords").append(html)
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

	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/book/edit",
		dataType: "json",
		data: requestQuery,
		success: function(book) {
			$("#book_title").val(book.title)
			$("#book_purchaser_name").val(book.purchaserName || "")
			$("#book_purchased_at").val(book.purchasedAt)
			$("#publisher_name").val(book.publisher.name || "")
			let authorsHtml = ""
			$.each(book.authors, function (index, value) {
				authorsHtml += '<div class="uk-margin-small"><input class="uk-input uk-form-width-medium author_name" type="text" list="author_name_keywords" value="' + value.name + '"><span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span></div>'
			})
			$("#author_names").prepend(authorsHtml)
			let genresHtml = ""
			$.each(book.genres, function (index, value) {
				genresHtml += '<div class="uk-margin-small"><input class="uk-input uk-form-width-medium genre_name" type="text" list="genre_name_keywords" value="' + value.name + '"><span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span></div>'
			})
			$("#genre_names").prepend(genresHtml)
			$(".author_name:first").next().remove()
			$(".genre_name:first").next().remove()
		}
	})
}

function addAuthorForm(span) {
	$(span).before('<div class="uk-margin-small"><input class="uk-input uk-form-width-medium author_name" type="text" list="author_name_keywords"><span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span></div>')
}

function addGenreForm(span) {
	$(span).before('<div class="uk-margin-small"><input class="uk-input uk-form-width-medium genre_name" type="text" list="genre_name_keywords"><span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span></div>')
}

function removeForm(span) {
	$(span).parent().remove()
}

function submit() {
	let parameter  = location.search.substring( 1, location.search.length )
	parameter = decodeURIComponent(parameter)
	const bookId = parameter.split('=')[1]
	const bookTitle = $("#book_title").val()
	const bookPurchaserName = $("#book_purchaser_name").val()
	const bookPurchasedAt = $("#book_purchased_at").val()
	const publisherName = $("#publisher_name").val()
	const authorNameList = $.unique($(".author_name").map(function(index, element) {return element.value}))
	const genreNameList  = $.unique($(".genre_name").map(function(index, element) {return element.value}))

	const requestQuery = {
		book: {
			id: bookId,
			title: bookTitle,
			purchaserName: bookPurchaserName,
			purchasedAt: bookPurchasedAt
		},
		publisher: {
			name: publisherName
		},
		authors: $.makeArray(authorNameList),
		genres: $.makeArray(genreNameList)
	}

	$.ajax({
		type: "POST",
		url: "/BookManageSystem/api/book/edit",
		dataType: "json",
		data: requestQuery,
		success: function(result) {
		}
	})
}

$(document).ready(function() {
	'use strict'
	loginCertification()
	bookInitialize()

	$("#submit").bind('click', submit)
})