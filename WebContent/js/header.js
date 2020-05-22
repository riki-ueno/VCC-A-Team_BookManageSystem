function initialize() {
	$.ajax({
		type: "GET",
		url: "/BookManageSystem/api/auth/loginCertification",
		dataType: "json",
		success: function(json) {
			$("#account_name").text(json.account_name)
			if (json.account_is_library_staff != "1") {
				$("#book_regist").remove()
			}
		}
	})
}

function logout() {
	$.ajax({
		type: "POST",
		url: "/BookManageSystem/api/auth/logout",
		dataType: "json",
		success: function() {
			alert("ログアウトしました")
			location.href = "/BookManageSystem/login.html"
		}
	})
}

$(document).ready(function () {
	'use strict'

    initialize()
});