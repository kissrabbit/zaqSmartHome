 $(document).ready(function(){
  			$("#deployXml").click(
  				function(thiz){
  					var xmlXML=$('#xmlXML').val();
  					var xmlId=$('#xmlId').val();
  					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{ id:xmlId,xml:xmlXML},
						url : "admin/app/xml/updateDeploy",// 请求的action路径
						error : function() {// 请求失败处理函数
							showError("部署失败！")
						},
						success : function(data) {
							if (data=='true') {
								showMsg("部署完成！")
							}else{
							    showError("部署异常！")
							}
						}
					});
  			});
  			
 			$("#saveXmlInfo").click(
  				function(thiz){
	  				var xmlName=$('#xmlName').val();
	  				var xmlFlowFuns=$('#xmlFlowFuns').val();
	  				var xmlRemark=$('#xmlRemark').val();
	  				var xmlId=$('#xmlId').val();
  					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{ id:xmlId, name :xmlName,flowFuns:xmlFlowFuns,remark:xmlRemark},
						url : "admin/app/xml/updateInfo",// 请求的action路径
						error : function() {// 请求失败处理函数
							showError("保存失败")
						},
						success : function(data) {
							if (data=='true') {
								showMsg("保存成功")
							}
						}
					});
	  				
  			});
 })
 
