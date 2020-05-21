/**
 *
 */
var changepassword = function(){
	var inputPassword = $('#password').val();
	var inputRepassword = $('#rePassword').val();
	if(inputPassword === inputRepassword){
		var requestQuery = {
				password : inputPassword,
			};

		$.ajax({
			type : 'POST',
			dataType:'json',
			url : '/BookManageSystem/api/auth/passwordChange',
			data : requestQuery,
			success : function(json) {
				// サーバーとの通信に成功した時の処理
				// 確認のために返却値を出力
				console.log('返却値', json);
				alert('パスワードが変更されました。');
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				// サーバーとの通信に失敗した時の処理
				alert('パスワードが変更できませんでした。時間をおいて再度試してください。');
				console.log(errorThrown)
			}
		});
	}else{
		alert('入力したパスワードが一致しませんでした。再度入力してください。')
	}

}
var LoginCertificate = function(){
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/BookManageSystem/api/auth/loginCertification',
		success : function(json) {
			if(json.result === "true"){
				alert('ログイン済みです。');
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('データベースへの更新に失敗しました。');
			console.log(errorThrown)
		}
	});
}
$(document).ready(function() {
	  'use strict';
	LoginCertificate();
	$('#commit').click(changepassword);
});