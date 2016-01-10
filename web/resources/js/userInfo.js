 $(document).ready(function(){
 			$("#saveUserInfo").click(
  				function(thiz){
	  				var username=$('#username').val();
	  				var pwd=$('#pwd').val();
	  				var isManager=$('#isManager').val();
  					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{  username :username,pwd:pwd,isManager:isManager},
						url : "/admin/user/save",// 请求的action路径
						error : function() {// 请求失败处理函数
							showError("保存失败")
						},
						success : function(data) {
							if (data==true) {
								showMsg("保存成功")
							}else{
								showError("保存失败")
							}
						}
					});
	  				
  			});
  			
  			$("#addUserInfo").click(
  				function(thiz){
	  				var username=$('#username').val();
	  				var pwd=$('#pwd').val();
	  				var isManager=$('#isManager').is(':checked');
  					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{  username :username,pwd:pwd,isManager:isManager},
						url : "/admin/user/save",// 请求的action路径
						error : function() {// 请求失败处理函数
							showError("保存失败")
						},
						success : function(data) {
							if (data==true) {
								showMsg("保存成功")
							}else{
								showError("保存失败")
							}
						}
					});
	  				
  			});
 })
 
