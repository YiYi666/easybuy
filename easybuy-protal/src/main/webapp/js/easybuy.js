var EB = EASYBUY = {
	checkLogin : function(){
		var _ticket = $.cookie("eb_token");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8093/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到易购！<a href=\"http://127.0.0.1:8082/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	EB.checkLogin();
});

