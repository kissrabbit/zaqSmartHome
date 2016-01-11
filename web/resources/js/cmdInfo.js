 $(document).ready(function(){
 			$("#saveCmdInfo").click(
  				function(thiz){
	  				var cmdname=$('#cmdname').val();
	  				var pwd=$('#pwd').val();
	  				var isManager=$('#isManager').val();
  					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{  cmdname :cmdname,pwd:pwd,isManager:isManager},
						url : "/admin/cmd/save",// 请求的action路径
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
  			
  			$("#addCmdInfo").click(
  				function(thiz){
	  				var cmdname=$('#cmdname').val();
	  				var pwd=$('#pwd').val();
	  				var isManager=$('#isManager').is(':checked');
  					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{  cmdname :cmdname,pwd:pwd,isManager:isManager},
						url : "/admin/cmd/save",// 请求的action路径
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
 
