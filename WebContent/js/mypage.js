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
						tableElemnt += '<td><a href="./bookDetail.html?bookId='+rental.book.id+'">'+rental.book.title+'</a></td>';
						tableElemnt += '<td>'+rental.book.publisher.name+'</td>';
						tableElemnt += '<td id=author'+index+'></td>';
						tableElemnt += '<td>'+rental.returnDeadline+'</td>';
						tableElemnt += '<td><button class=returnBook type="submit" value="'+rental.id+'">返却</button></td>';
					}
				$('#rentalTable').html(tableElemnt);
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
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
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
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			console.log(errorThrown)
		}
	});
}
var returnBook = function(){
	var inputRentalId = document.activeElement.value;
	var url = './rentalConfirm.html?rentalId='+inputRentalId;
	location.href=url;
}
var getReservationInfo = function(){
	parameter = 'Reservation';
	var requestQuery = {
			parameter : parameter,
		};
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/ReservationServlet',
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
				tableElemnt +='<th>操作</th>';
				tableElemnt +='</tr>';
				var index=0;
					for (var i=0; i < json.length; i++) {
						var reservation = json[i];
						index = i+1;
						tableElemnt += '<tr>';
						tableElemnt += '<td>'+index+'</td>';
						tableElemnt += '<td id=ReservationGenre'+index+'></td>';
						tableElemnt += '<td><a href="./bookDetail.html?bookId='+reservation.book.id+'">'+reservation.book.title+'</a></td>';
						tableElemnt += '<td>'+reservation.book.purchaserName+'</td>';
						tableElemnt += '<td id=ReservationAuthor'+index+'></td>';
						tableElemnt += '<td><button class=cancel type="submit" value="'+reservation.book.id+'">キャンセル</button></td>';
						if(reservation.returnedAt != null){
							tableElemnt += '<td><button class=rental type="submit" value="'+reservation.book.id+'">借りる</button></td>';
						}
					}
				$('#reservationTable').html(tableElemnt);
				$('.cancel').click(cancel);
				$('.rental').click(rental);
			}else if(json.length == 0){
				$('reservationContainer').html('予約している本はありません。');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			$('reservationContainer').html('予約している本はありません。');
			console.log(errorThrown)
		}
	});
}
var getReservationAuthorInfo = function(){
	parameter = 'ReservationAuthor';
	var requestQuery = {
			parameter : parameter,
		};
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/ReservationServlet',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			if(json.length > 0){
				var index=0;
				for (var i=0; i < json.length; i++) {
					index = i+1;
					var author = json[i];
					$('#ReservationAuthor'+index).html(author.name);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			console.log(errorThrown)
		}
	});
}
var getReservationGenreInfo = function(){
	parameter = 'ReservationGenre';
	var requestQuery = {
			parameter : parameter,
		};
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/ReservationServlet',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			if(json.length > 0){
				var index=0;
				for (var i=0; i < json.length; i++) {
					index = i+1;
					var genre = json[i];
					$('#ReservationGenre'+index).html(genre.name);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			console.log(errorThrown)
		}
	});
}
var cancel = function(){
	var inputBookId = document.activeElement.value;
	var requestQuery = {
			bookId : inputBookId,
		};
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/BookManageSystem/api/auth/ReservationServlet',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			alert('キャンセルが完了しました。');
			location.href = "./mypage.html"
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('キャンセルができませんでした。時間を置いて再度試してください。');
			console.log(errorThrown)
		}
	});
}
var rental = function(){
	var inputBookId = document.activeElement.value;
	var rentalQuery = {
		  book: {id: inputBookId},
		};
	var cancelQuery = {
			bookId : inputBookId,
		};
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/BookManageSystem/api/book/rental',
		data : rentalQuery,
		success : function(json) {
			console.log('返却値', json);
			alert('貸出が完了しました。');
			location.href = "./mypage.html"
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('貸出ができませんでした。時間を置いて再度試してください。');
			console.log(errorThrown)
		}
	});
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/BookManageSystem/api/auth/ReservationServlet',
		data : cancelQuery,
		success : function(json) {
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			console.log(errorThrown)
		}
	});
}
$(document).ready(function () {
    'use strict';
    LoginCertificate();
    getrentalInfo();
    getauthorInfo();
    getGenreInfo();
    getReservationInfo();
    getReservationGenreInfo();
    getReservationAuthorInfo();
});
