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
				location.href = "/BookManageSystem/login.html"
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('ログイン情報の取得に失敗しました。');
			console.log(errorThrown)
		}
	});
}
var parameter;
var getrentalInfo = function(){
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/rented',
		success : function(json) {
			console.log('返却値', json);
			var tableElemnt = '';
			if(json.length > 0){
				var index=0;
					for (var i=0; i < json.length; i++) {
						var rental = json[i];
						index = i+1;
						tableElemnt += '<tr>';
						tableElemnt += '<td>'+index+'</td>';
						tableElemnt += '<td>'+rental.book.genreNames+'</td>';
						tableElemnt += '<td><a href="/BookManageSystem/book/show.html?bookId='+rental.book.id+'">'+rental.book.title+'</a></td>';
						tableElemnt += '<td>'+rental.book.publisher.name+'</td>';
						tableElemnt += '<td>'+rental.book.authorNames+'</td>';
						tableElemnt += '<td>'+rental.returnDeadline+'</td>';
						tableElemnt += '<td><button class="returnBook uk-button uk-button-default" type="button" value="'+rental.id+'">返却</button></td>';
						tableElemnt += '</tr>';
					}
				$('#rental_table tbody').html(tableElemnt);
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
var returnBook = function(){
	var inputRentalId = document.activeElement.value;
	var url = '/BookManageSystem/book/returnConfirm.html?rentalId='+inputRentalId;
	location.href=url;
}
var getReservationInfo = function(){
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/reservation',
		success : function(json) {
			console.log('返却値', json);
			var tableElemnt = '';
			if(json.length > 0){
				var index=0;
					for (var i=0; i < json.length; i++) {
						var reservation = json[i];
						index = i+1;
						tableElemnt += '<tr>';
						tableElemnt += '<td>'+index+'</td>';
						tableElemnt += '<td>'+reservation.book.genreNames+'</td>';
						tableElemnt += '<td><a href="/BookManageSystem/book/show.html?bookId='+reservation.book.id+'">'+reservation.book.title+'</a></td>';
						tableElemnt += '<td>'+reservation.book.purchaserName+'</td>';
						tableElemnt += '<td>'+reservation.book.authorNames+'</td>';
						tableElemnt += '<td><button class="cancel uk-button uk-button-default uk-button-small" type="submit" value="'+reservation.book.id+'">キャンセル</button></td>';
						if(reservation.availableForRental){
							tableElemnt += '<td><button class="rental uk-button uk-button-default uk-button-small" type="submit" value="'+reservation.book.id+'">借りる</button></td>';
						}
						tableElemnt += '</tr>';
					}
				$('#reservation_table tbody').html(tableElemnt);
				$('.cancel').click(cancel);
				$('.rental').click(rental);
			}else{
				$('#reservationContainer').html('予約している本はありません。');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			$('#reservationContainer').html('予約している本はありません。');
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
		url : '/BookManageSystem/api/auth/reservation',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			alert('キャンセルが完了しました。');
			location.href = "/BookManageSystem/account/mypage.html"
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
			location.href = "/BookManageSystem/account/mypage.html"
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('貸出ができませんでした。時間を置いて再度試してください。');
			console.log(errorThrown)
		}
	});
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/BookManageSystem/api/auth/reservation',
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
    getReservationInfo();
});

