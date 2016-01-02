$(function() {
	//点击消息关闭提示
	$('#alertMessage').click(function() {
		hideTop();
	});
	//上传文件组件关闭刷新当前页
	$('#uploadFile').on('hide.bs.modal', function () {
			showPage("admin/app/"+$("#appId").val());
	})
});

/**
 * 加载中心面板页面
 * @param {} url
 */
function showPage(url,filePath){
      $('#page-main').html('页面加载中，请稍后...'); // 设置页面加载时的loading图片
      $('#page-main').load(url); // ajax加载页面
      if(filePath){
      	$("#filePath").html(filePath);
      }
} 

function hideTop() {
	$('#alertMessage').animate({
		opacity : 0,
		right : '-20'
	}, 500, function() {
		$(this).hide();
	});
}
//显示错误提示
function showError(str) {
	$('#alertMessage').addClass('error').html(str).stop(true, true).show().animate({
		opacity : 0.6,
		right:'0'
	}, 500);
	
	setTimeout('hideTop()', 1000);
}
 
function showMsg(str) {
	$('#alertMessage').addClass('success').html(str).stop(true, true).show().animate({
		opacity : 0.6,
		right:'0'
	}, 500);
	
	setTimeout('hideTop()', 1000);
}

function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}