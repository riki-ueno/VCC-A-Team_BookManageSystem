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
			alert('ログイン情報の取得に失敗しました。');
			console.log(errorThrown)
		}
	});
}
var getBookDetailInfo = function(){
	var parameter  = location.search;
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];
	var requestQuery = { bookId  : parameter };
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/BookManageSystem/api/book/detail',
		data : requestQuery,
		success : function(json) {
			console.log(json);
			$('#title').html(json.title);
			$('#author').html(json.authorsName);
			$('#publisher').html(json.publisherNmae);
			$('#genre').html(json.genresName);
			$('#status').html(json.status);
			$('#rentedName').html(json.rentedName);
			$('#deadlineAt').html(json.returnDeadline);
			$('#reserver').html(json.reserver);
			$('#lastRentedName').html(json.lastRentedName);
			$('#purchaserName').html(json.purchaserName);
			$('#purchasedAt').html(json.purchasedAt);
			$('#rigistrantName').html(json.rigistrantName);
			$('#regisitedAt').html(json.rigisteredAt);
			$('#updaterName').html(json.updaterName);
			$('#updatedAt').html(json.updatedAt);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('データベースへの更新に失敗しました。');
			console.log(errorThrown)
		}
	});
}
var edit = function(){
	var parameter  = location.search;
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];
	location.href = "./bookEdit.html?bookId="+parameter;
}
var back = function(){
	location.href = "javascript:history.back();"
}
$(document).ready(function () {
	 'use strict';
    LoginCertificate();
    getBookDetailInfo();
    $('#edit').click(edit);
    $('#back').click(back);

});