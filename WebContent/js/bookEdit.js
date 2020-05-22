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

function initializeValidateRules() {
	jQuery.validator.addMethod("smallerThan", function(value, element) {
		return new Date() > new Date(value)
	}, '今日以前の日付を入力してください')

	$("form").validate({
		rules : {
			"book_title" : {
				required : true
			},
			"publisher_name" : {
				required : true
			},
			"book_purchaser_name" : {
				required : true
			},
			"book_purchased_at" : {
				required : true,
				smallerThan : true
			}
		}
	})

	$(".author_name").each(function() {
		$(this).rules("add", {
			required : true
		})
	})

	$(".genre_name").each(function() {
		$(this).rules("add", {
			required : true
		})
	})
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

			if ($(".author_name").length == 1) {
				$(".author_name:first").next().remove()
			}

			if ($(".genre_name").length == 1) {
				$(".genre_name:first").next().remove()
			}
		}
	})
}

function addAuthorForm(span) {
	$(span).before('<div class="uk-margin-small"><input class="uk-input uk-form-width-medium author_name" type="text" list="author_name_keywords"><span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span></div>')
	if ($(".author_name").length == 2) {
		$(".author_name:first").after('<span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span>')
	}
}

function addGenreForm(span) {
	$(span).before('<div class="uk-margin-small"><input class="uk-input uk-form-width-medium genre_name" type="text" list="genre_name_keywords"><span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span></div>')
	if ($(".genre_name").length == 2) {
		$(".genre_name:first").after('<span class="uk-icon-button uk-margin-left" uk-icon="close" onclick="removeForm(this)"></span>')
	}
}

function removeForm(span) {
	$(span).parent().remove()
	if ($(".author_name").length == 1) {
		$(".author_name:first").next().remove()
	}

	if ($(".genre_name").length == 1) {
		$(".genre_name:first").next().remove()
	}
}

function submit() {
	const requestQuery = buildRequestQuery()

	console.log(requestQuery)

	if ($("form").valid()) {
		$.ajax({
			type: "POST",
			url: "/BookManageSystem/api/book/edit",
			dataType: "json",
			data: requestQuery,
			success: function(result) {
				if (result == true) {
					alert("更新しました。")
					location.href = "/BookManageSystem/book/show.html?bookId=" + bookId
				} else {
					alert("更新に失敗しました。")
				}
			}
		})
	}
}

function buildRequestQuery() {
	let parameter  = location.search.substring( 1, location.search.length )
	parameter = decodeURIComponent(parameter)
	const bookId = parameter.split('=')[1]
	const bookTitle = $("#book_title").val()
	const bookPurchaserName = $("#book_purchaser_name").val()
	const bookPurchasedAt = $("#book_purchased_at").val()
	const publisherName = $("#publisher_name").val()
	const authorNameList = $.unique($(".author_name").map(function(index, element) {return element.value}).filter(function(el) {return el != null}))
	const genreNameList  = $.unique($(".genre_name").map(function(index, element) {return element.value}).filter(function(el) {return el != null}))

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

	return requestQuery
}

$(document).ready(function() {
	'use strict'
	loginCertification()
	initializeValidateRules()
	bookInitialize()

	$("#submit").bind('click', submit)
})