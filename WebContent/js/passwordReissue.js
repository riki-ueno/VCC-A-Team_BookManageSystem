function submit() {
	$("#submit").prop("disabled", true)
	const accountMailAddress = $("#account_mail_address").val()
	const requestQuery = {account: {mailAddress: accountMailAddress}}

	console.log(requestQuery)

	$.ajax({
		type : 'POST',
		url : '/BookManageSystem/api/account/password/reissue',
		dataType : "json",
		data : requestQuery,
		success : function(result) {
			if (result === true) {
				alert("パスワードを再発行しました。メールを確認してください。")
				location.href = "/BookManageSystem/login.html"
			} else {
				alert("パスワード再発行に失敗しました。メールアドレスを確認してください。")
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("通信に失敗しました")
		}
	}).always(() => {
		$("#submit").prop("disabled", false)
	})
}

$(document).ready(function () {
    'use strict';
    $("#submit").bind('click', submit)
});