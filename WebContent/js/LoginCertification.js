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
				alert('ログイン済みです。');
				location.href = "/BookManageSystem/book/search.html"
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('ログイン情報の取得に失敗しました。');
			console.log(errorThrown)
		}
	});
}

$(document).ready(function () {
    'use strict';
    LoginCertificate();
});