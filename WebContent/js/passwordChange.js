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
			url : '/javaTraining/ChangePasswordServlet',
			data : requestQuery,
			success : function(json) {
				// サーバーとの通信に成功した時の処理
				// 確認のために返却値を出力
				console.log('返却値', json);
				alert('パスワードが変更されました、再度ログインしてください。');
				logout();
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				// サーバーとの通信に失敗した時の処理
				alert('データベースへの更新に失敗しました。');
				console.log(errorThrown)
			}
		});
	}else{
		alert('入力したパスワードが一致しませんでした。再度入力してください。')
	}

}
$(document).ready(function() {

	$('#commit').click(changepassword);
});