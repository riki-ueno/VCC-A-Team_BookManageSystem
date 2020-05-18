function submit() {
	'use strict'
	const mailAddress = $("#account_mail_address").val();
	const password = $("#account_password").val();

    const requestQuery = {account: {mailAddress: mailAddress, password: password}}

    $.ajax({
        type : 'POST',
        url : '/BookManageSystem/api/auth/login',
        dataType: "json",
        data: requestQuery,
        success: function(result) {
            if (result !== false) {
            	alert("ログインしました")
            	location.href = "./search.html"
            } else {
            	alert("ログインに失敗しました")
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
        	alert("通信に失敗しました")
        }
    })
}
$(document).ready(function () {
    'use strict';
    $("#submit").bind('click', submit)
});