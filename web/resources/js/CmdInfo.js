var CmdInfo={}	

$(document).ready(function() {
	if ($("#type").val() != 3) {
		$("#wirelessProtocolDiv").hide()
	}
	$("#type").change(function() {
		if ($("#type").val() != 3) {
			$("#wirelessProtocolDiv").hide()
			$("#wirelessProtocol").val("");
		}else{
			$("#wirelessProtocolDiv").show();
		}
	})
	
	$("#saveCmdInfo").click(function(thiz) {
		var cmdname = $('#cmdname').val();
		var pwd = $('#pwd').val();
		var isManager = $('#isManager').val();
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			data : {
				cmdname : cmdname,
				pwd : pwd,
				isManager : isManager
			},
			url : "/admin/cmd/save",// 请求的action路径
			error : function() {// 请求失败处理函数
				showError("保存失败")
			},
			success : function(data) {
				if (data == true) {
					showMsg("保存成功")
				} else {
					showError("保存失败")
				}
			}
		});

	});
})
