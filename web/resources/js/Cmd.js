var Cmd={}	
/**
 * 绑定删除事件
 * @param {} cmdId
 */
Cmd.doDel=function(cmdId){
		$('#cmdModal').modal('show');
		$('#delCmd').unbind();
		$("#delCmd").click(
				function(){
					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{},
						url : "/admin/cmd/del/"+cmdId,// 请求的action路径
						error : function() {// 请求失败处理函数
							//删除失败
							showError("删除异常！")
						},
						success : function(data) {
							if (data==true) {
								//删除成功
								showMsg("删除成功!");
								$("#cmd_del_"+cmdId).remove();
							}else{
							    showError("删除失败！")
							}
						}
					});
				}
			)
	}