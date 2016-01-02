var App={}	
/**
 * 绑定删除xml事件
 * @param {} xmlId
 */
App.doDelXml=function(xmlId){
		$('#appModal').modal('show')
			$('#delXml').unbind()
			$("#delXml").click(
				function(){
					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{ id:xmlId},
						url : "admin/app/xml/del",// 请求的action路径
						error : function() {// 请求失败处理函数
							//删除失败
							showError("删除失败！")
						},
						success : function(data) {
							if (data=='true') {
								//删除成功
								//showPage("admin/app/"+$('#appId').val());
								$("#xmlName_"+xmlId).css({"text-decoration":"line-through" ,"color":"red"});
								$("#xmlStatus_"+xmlId).html("未部署")
								$("#xmlUserLastUpdate_"+xmlId).html(current_fullname);
							}else{
							    showError("删除异常！")
							}
						}
					});
				}
			)
	}