/**
 *
 */
var LoginCertificate = function(){
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/BookManageSystem/api/auth/loginCertification',
		success : function(json) {
			if(json.result === "true"){
			}else{
				alert('ログインしてください。')
				location.href = "./login.html"
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('データベースへの更新に失敗しました。');
			console.log(errorThrown)
		}
	});
}
var parameter;
var getrentalInfo = function(){
	parameter = 'rental';
	var requestQuery = {
			parameter : parameter,
		};
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/rented',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			var tableElemnt = '';
			if(json.length > 0){
				tableElemnt +='<tr>';
				tableElemnt +='<th>No</th>';
				tableElemnt +='<th>ジャンル</th>';
				tableElemnt +='<th>タイトル</th>';
				tableElemnt +='<th>出版社</th>';
				tableElemnt +='<th>著者</th>';
				tableElemnt +='<th>返却期限</th>';
				tableElemnt +='<th>操作</th>';
				tableElemnt +='</tr>';
				var index=0;
					for (var i=0; i < json.length; i++) {
						var rental = json[i];
						index = i+1;
						tableElemnt += '<tr>';
						tableElemnt += '<td>'+index+'</td>';
						tableElemnt += '<td id=genre'+index+'></td>';
						tableElemnt += '<td>'+rental.book.title+'</td>';
						tableElemnt += '<td>'+rental.book.publisher.name+'</td>';
						tableElemnt += '<td id=author'+index+'></td>';
						tableElemnt += '<td>'+rental.returnDeadline+'</td>';
						tableElemnt += '<td><button class=returnBook type="submit" value="'+rental.id+'">返却</button></td>';
					}
				$('#rentalTableContainer').html(tableElemnt);
				$('.returnBook').click(returnBook);
			}else if(json.length == 0){
				$('#rentalContainer').html('借りている本はありません。');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			$('#rentalContainer').html('借りている本はありません。');
			console.log(errorThrown)
		}
	});
}
var getauthorInfo = function(){
	parameter = 'author';
	var requestQuery = {
			parameter : parameter,
		};
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/rented',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			if(json.length > 0){
				var index=0;
				for (var i=0; i < json.length; i++) {
					index = i+1;
					var rental = json[i];
					$('#author'+index).html(rental.name);
				}
			}else if(json.length == 0){
				$('#rentalContainer').html('登録している申請がありません。');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			$('#rentalContainer').html('登録している申請がありません。');
			console.log(errorThrown)
		}
	});
}
var getGenreInfo = function(){
	parameter = 'genre';
	var requestQuery = {
			parameter : parameter,
		};
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/rented',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			if(json.length > 0){
				var index=0;
				for (var i=0; i < json.length; i++) {
					index = i+1;
					var genre = json[i];
					$('#genre'+index).html(genre.name);
				}
			}else if(json.length == 0){
				$('#rentalContainer').html('登録している申請がありません。');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			$('#rentalContainer').html('登録している申請がありません。');
			console.log(errorThrown)
		}
	});
}
var returnBook = function(){
	var inputRentalId = document.activeElement.value;
	var url = './rentalConfirm.html?q='+inputRentalId;
	location.href=url;
}
$(document).ready(function () {
    'use strict';
    LoginCertificate();
    getrentalInfo();
    getauthorInfo();
    getGenreInfo();
});