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
			    getReturnBookInfo();
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
var getReturnBookInfo = function(){
	var parameter  = location.search;
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];
	var requestQuery = { rentalId  : parameter };
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/BookManageSystem/api/auth/Return',
		data : requestQuery,
		success : function(json) {
			var tableElemnt = '';
			tableElemnt += '<tr>';
			tableElemnt += '<td><a href="./bookDetail.html?bookId='+json.bookId+'">'+json.title+'</a></td>';
			tableElemnt += '<td>'+json.publisherNmae+'</td>';
			tableElemnt += '<td>'+json.authorsName+'</td>';
			tableElemnt += '<td>'+json.returnDeadline+'</td>';
			tableElemnt +='</tr>';
			$('table tbody').html(tableElemnt);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('返却書籍情報の取得に失敗しました。');
			console.log(errorThrown)
		}
	});
}
var confirm = function(){
	var parameter  = location.search;
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];
	var requestQuery = { rental:{id  : parameter}, };
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/BookManageSystem/api/auth/Return',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			alert('返却を受理しました。');
			location.href = "/BookManageSystem/account/mypage.html"
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('返却を受理出来ませんでした。再度返却処理をしてください。');
			console.log(errorThrown)
		}
	});
}
var back = function(){
	location.href = "javascript:history.back();"
}
$(document).ready(function () {
	 'use strict';
    LoginCertificate();
    $('#confirm').click(confirm);
    $('#back').click(back);
});