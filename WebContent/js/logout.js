function logout() {
	$.ajax({
		type: "POST",
		url: "/BookManageSystem/api/auth/logout",
		dataType: "json",
		success: function() {
			alert("ログアウトしました")
			location.href = "./login.html"
		}
	})
}