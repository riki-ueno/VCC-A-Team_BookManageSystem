function submit() {
	'use strict'

	if ($("form").valid()) {
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
	            	location.href = "/BookManageSystem/book/search.html"
	            } else {
	            	alert("ログインに失敗しました")
	            }
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown) {
	        	alert("通信に失敗しました")
	        }
	    })
	}
}

function initializeValidateRules() {
	$("form").validate({
		rules: {
			"account_mail_address": {
				required: true,
				email: true
			},
			"account_password": {
				required: true
			}
		}
	})
}

$(document).ready(function () {
    'use strict'
    initializeValidateRules()
    $("#submit").bind('click', submit)
});