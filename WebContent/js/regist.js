/**
 * 
 */

var LoginCertificate = function() {
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/BookManageSystem/api/auth/loginCertification',
		success : function(json) {
			if (json.result === "true") {
			} else {
				alert('ログインしてください。');
				location.href = "./login.html"
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースへの更新に失敗しました。');
			console.log(errorThrown)
		}
	});
}

var authorNameFormCounter = 1;
var authorNamesStr;
var genreNameFormCounter = 1;
var genreNamesStr;
var publisherNamesStr;

function setAuthorNamesToDatalist() {
	$.ajax({
		type : 'GET',
		url : '/BookManageSystem/api/author/index',
		dataType : 'json',
		success : function(authorDatas) {
			for (var i = 0; i < authorDatas.length; i++) {
				var authorData = authorDatas[i];
				authorNamesStr += '<option>' + authorData.name + '</option>';
			}
			$('#author_names_1').append(authorNamesStr);
		},
		error : function() {
			alert('筆者一覧の取得に失敗しました。');
		}
	});
}

function setGenreNamesToDatalist() {
	$.ajax({
		type : 'GET',
		url : '/BookManageSystem/api/genre/index',
		dataType : 'json',
		success : function(genreDatas) {
			for (var i = 0; i < genreDatas.length; i++) {
				var genreData = genreDatas[i];
				genreNamesStr += '<option>' + genreData.name + '</option>';
			}
			$('#genre_names_1').append(genreNamesStr);
		},
		error : function() {
			alert('ジャンル一覧の取得に失敗しました。');
		}
	});
}

function setPublisherNamesToDataList() {
	$.ajax({
		type : 'GET',
		url : '/BookManageSystem/api/publisher/index',
		dataType : 'json',
		success : function(publisherDatas) {
			for (var i = 0; i < publisherDatas.length; i++) {
				var publisherData = publisherDatas[i];
				publisherNamesStr += '<option>' + publisherData.name + '</option>';
			}
			$('#publisher_names').append(publisherNamesStr);
		},
		error : function() {
			alert('出版社一覧の取得に失敗しました。');
		}
	});
}

function addAuthorNameForm() {
	authorNameFormCounter++;
	$('#author_name_forms').append(
			'<br><input type="text" class="author_name" id="author_name_' + authorNameFormCounter + '" list="author_names_' + authorNameFormCounter
					+ '" required >' + '<datalist id="author_names_' + authorNameFormCounter + '"></datalist>');
	$('#author_names_' + authorNameFormCounter).append(authorNamesStr);
}

function addGenreNameForm() {
	genreNameFormCounter++;
	$('#genre_name_forms').append(
			'<br><input type="text" class="genre_name" id="genre_name_' + genreNameFormCounter + '" list="genre_names_' + genreNameFormCounter
					+ '" required >' + '<datalist id="genre_names_' + genreNameFormCounter + '"></datalist>');
	$('#genre_names_' + genreNameFormCounter).append(genreNamesStr);
}

function regist() {
	var title = $('#book_title').val();
	var publisherName = $('#publisher_name').val();
	var purchaserName = $('#purchaser_name').val();
	var purchasedAt = $('#purchased_at').val();
	var authorNames = [];
	var authorNameElements = $('.author_name');
	for (var i = 0; i < authorNameElements.length; i++) {
		authorNames.push(authorNameElements[i].value);
	}
	var genreNames = [];
	var genreNameElements = $('.genre_name');
	for (var i = 0; i < genreNameElements.length; i++) {
		genreNames.push(genreNameElements[i].value);
	}
	var requestQuery = {
		book : {
			title : title,
			purchaserName : purchaserName,
			purchasedAt : purchasedAt
		},
		genres : genreNames,
		authors : authorNames,
		publisher : {
			name : publisherName
		}
	};
	console.log(requestQuery);
	$.ajax({
		type : 'POST',
		url : '/BookManageSystem/api/book/regist',
		dataType : 'text',
		data : requestQuery,
		success : function(bookId) {
			alert('書籍の登録が完了しました。');
			console.log(bookId);
			location.href = './bookDetail.html/?bookId=' + bookId;
		},
		error : function() {
			alert('登録できませんでした。入力情報を確認してください。');
		}
	});
}

function registContinue() {
	var title = $('#book_title').val();
	var publisherName = $('#publisher_name').val();
	var purchaserName = $('#purchaser_name').val();
	var purchasedAt = $('#purchased_at').val();
	var authorNames = [];
	var authorNameElements = $('.author_name');
	for (var i = 0; i < authorNameElements.length; i++) {
		authorNames.push(authorNameElements[i].value);
	}
	var genreNames = [];
	var genreNameElements = $('.genre_name');
	for (var i = 0; i < genreNameElements.length; i++) {
		genreNames.push(genreNameElements[i].value);
	}
	var requestQuery = {
		book : {
			title : title,
			purchaserName : purchaserName,
			purchasedAt : purchasedAt
		},
		genres : genreNames,
		authors : authorNames,
		publisher : {
			name : publisherName
		}
	};
	console.log(requestQuery);
	$.ajax({
		type : 'POST',
		url : '/BookManageSystem/api/book/regist',
		dataType : 'text',
		data : requestQuery,
		success : function(bookId) {
			alert('書籍の登録が完了しました。');
			location.reload();
		},
		error : function() {
			alert('登録できませんでした。入力情報を確認してください。');
		}
	});
}

$(document).ready(function() {
	'use strict';
	LoginCertificate();
	setAuthorNamesToDatalist();
	setGenreNamesToDatalist();
	setPublisherNamesToDataList();
	$('#add_author_name_form').click(addAuthorNameForm);
	$('#add_genre_name_form').click(addGenreNameForm);
	$('#regist').click(regist);
	$('#regist_continue').click(registContinue);
});
