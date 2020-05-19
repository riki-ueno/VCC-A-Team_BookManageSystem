/**
 * 
 */

var authorNameFormCounter = 1;
var authorNamesStr;
var genreNameFormCounter = 1;
var genreNamesStr;

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
			alert('データの通信に失敗しました。');
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
			alert('データの通信に失敗しました。');
		}
	});
}
function addAuthorNameForm() {
	authorNameFormCounter++;
	$('#author_name_forms').append(
			'<br><input type="text" class="author_name" id="author_name_' + authorNameFormCounter + '" list="author_names_' + authorNameFormCounter + '" >'
					+ '<datalist id="author_names_' + authorNameFormCounter + '"></datalist>');
	$('#author_names_' + authorNameFormCounter).append(authorNamesStr);
}

function addGenreNameForm() {
	genreNameFormCounter++;
	$('#genre_name_forms').append(
			'<br><input type="text" class="genre_name" id="genre_name_' + genreNameFormCounter + '" list="genre_names_' + genreNameFormCounter + '" >'
					+ '<datalist id="genre_names_' + genreNameFormCounter + '"></datalist>');
	$('#genre_names_' + genreNameFormCounter).append(genreNamesStr);
}


function　setTestValues() {
	$('#book_title').val('題名');
	$('#author_name_1').val('筆者名1');
	addAuthorNameForm();
	$('#author_name_2').val('筆者名2');
	$('#publisher_name').val('出版社名');
	$('#genre_name_1').val('ジャンル1');
	addGenreNameForm();
	$('#genre_name_2').val('ジャンル2');
	addGenreNameForm();
	$('#genre_name_3').val('ジャンル3');
	$('#purchaser_name').val('購入者');
	$('#purchased_at').val('購入日');
}


$(document).ready(function() {
	'use strict';
	setAuthorNamesToDatalist();
	setGenreNamesToDatalist();
	$('#set_test_value_button').click(setTestValues);
	$('#add_author_name_form').click(addAuthorNameForm);
	$('#add_genre_name_form').click(addGenreNameForm);
	$('#regist').click(regist);
	
});

function regist() {
	var title = $('#book_title').val();
	var publisherName = $('#publisher_name').val();
	var purchaserName = $('#purchaser_name').val();
	var purchasedAt = $('#purchased_at').val();
	var authorNames = [] ;
	var authorNameElements = $('.author_name');
	for(var i=0;i<authorNameElements.length;i++){
		authorNames.push(authorNameElements[i].value);
	}
	var genreNames = [] ;
	var genreNameElements = $('.genre_name');
	for(var i=0;i<genreNameElements.length;i++){
		genreNames.push(genreNameElements[i].value);
	}
	var requestQuery = {
		book : {
			title : title,
			purchaserName : purchaserName,
			purchasedAt : purchasedAt
		},
		genres : genreNames,
		authors :authorNames,
		publisher : {
			name : publisherName
		}
	};
	console.log(requestQuery);
	$.ajax({
	 type : 'POST',
	 url : '/BookManageSystem/api/book/regist',
	 dataType:'json',
	 data : requestQuery,
	 success : function() {
		 alert('書籍の登録が完了しました。');
	 },
	 error : function() {
		 alert('登録できませんでした。入力情報を確認してください。');
	 }
	 });
}
