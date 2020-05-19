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
			alert('データベースへの更新に失敗しました。');
			console.log(errorThrown)
		}
	});
}
var parameter;
var getrentalInfo = function(){
	parameter = 'rental';
	var requestQuery = {
			parameter : parameter,
		};
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/BookManageSystem/api/auth/rented',
		data : requestQuery,
		success : function(json) {
			console.log('返却値', json);
			var tableElemnt = '';
			if(json.length > 0){
				tableElemnt +='<tr>';
				tableElemnt +='<th>No</th>';
				tableElemnt +='<th>ジャンル</th>';
				tableElemnt +='<th>タイトル</th>';
				tableElemnt +='<th>出版社</th>';
				tableElemnt +='<th>著者</th>';
				tableElemnt +='<th>返却期限</th>';
				tableElemnt +='<th>操作</th>';
				tableElemnt +='</tr>';
				var index=0;
					for (var i=0; i < json.length; i++) {
						var rental = json[i];
						index = i+1;
						tableElemnt += '<tr>';
						tableElemnt += '<td>'+index+'</td>';
						tableElemnt += '<td id=genre'+rental.id+'>ここにジャンル</td>';
						tableElemnt += '<td>'+rental.book.title+'</td>';
						tableElemnt += '<td>'+rental.book.publisher.name+'</td>';
						tableElemnt += '<td id=author'+rental.id+'>ここに著者</td>';
						tableElemnt += '<td>'+rental.returnDeadline+'</td>';
						tableElemnt += '<td><button class=return type="submit">返却</button></td>';
					}
				$('#rentalTableContainer').html(tableElemnt);
			}else if(json.length == 0){
				$('#rentalContainer').html('登録している申請がありません。');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('データベースの接続に失敗しました');
			$('#rentalContainer').html('登録している申請がありません。');
			console.log(errorThrown)
		}
	});
}
$(document).ready(function () {
    'use strict';
    LoginCertificate();
    getrentalInfo();
});