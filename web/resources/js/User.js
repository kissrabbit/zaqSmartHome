var User={}	
/**
 * 绑定删除事件
 * @param {} username
 */
User.doDel=function(username){
		$('#userModal').modal('show');
		$('#delUser').unbind();
		$("#delUser").click(
				function(){
					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{ username:username},
						url : "/admin/user/del",// 请求的action路径
						error : function() {// 请求失败处理函数
							//删除失败
							showError("删除异常！")
						},
						success : function(data) {
							if (data==true) {
								//删除成功
								showMsg("删除成功!");
								$("#user_del_"+username).remove();
							}else{
							    showError("删除失败！")
							}
						}
					});
				}
			)
	}