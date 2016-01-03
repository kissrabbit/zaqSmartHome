<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>zaqSmartHome管理界面</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href='http://fonts.useso.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css"
	href="/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/lib/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="/resources/css/style.css">
<script src="/lib/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/lib/jQuery-Knob/js/jquery.knob.js" type="text/javascript"></script>
<script src="/resources/js/main.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		//初始化上传组件
		$("#uploadInputFile").fileinput();
		$(".knob").knob();
	});
</script>


<link rel="stylesheet" type="text/css" href="/stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="/stylesheets/premium.css">
<link href="/lib/bootstrap/fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />

<script src="/lib/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/lib/bootstrap/fileinput/js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
<script src="/lib/bootstrap/fileinput/js/fileinput.min.js"></script>
<script src="/lib/bootstrap/fileinput/js/fileinput_locale_zh.js"></script>
</head>
<body class=" theme-blue">
	<script type="text/javascript">
		$(function() {
			var match = document.cookie.match(new RegExp('color=([^;]+)'));
			if (match)
				var color = match[1];
			if (color) {
				$('body').removeClass(function(index, css) {
					return (css.match(/\btheme-\S+/g) || []).join(' ')
				})
				$('body').addClass('theme-' + color);
			}

			$('[data-popover="true"]').popover({
				html : true
			});

		});
	</script>
	<style type="text/css">
		#line-chart {
			height: 300px;
			width: 800px;
			margin: 0px auto;
			margin-top: 1em;
		}
		
		.navbar-default .navbar-brand,.navbar-default .navbar-brand:hover {
			color: #fff;
		}
	</style>	

	<script type="text/javascript">
		$(function() {
			var uls = $('.sidebar-nav > ul > *').clone();
			uls.addClass('visible-xs');
			$('#main-menu').append(uls.clone());
		});
		var current_fullname='${username}';
		var current_username='${username}';
	</script>

	<div class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<span class="navbar-brand"><span
					class="fa fa-paper-plane"></span> zaqSmartHome管理界面</span>
		</div>

		<div class="navbar-collapse collapse" style="height: 1px;">
			<ul id="main-menu" class="nav navbar-nav navbar-right">
				<li class="dropdown hidden-xs"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <span
						class="glyphicon glyphicon-user padding-right-small"
						style="position:relative;top: 3px;"></span> ${username} <i
						class="fa fa-caret-down"></i>
				</a>

					<ul class="dropdown-menu">
						<li><a href="./">刷新</a></li>
						<li class="divider"></li>
						<li class="divider"></li>
						<li><a tabindex="-1" href="/logout">退出</a></li>
					</ul></li>
			</ul>

		</div>
	</div>


	<div class="sidebar-nav">
		<ul>
			<li><a href="#" data-target=".dashboard-menu" class="nav-header"
				data-toggle="collapse"><i class="fa fa-fw fa-dashboard"></i>
					菜单列表<i class="fa fa-collapse"></i></a></li>
			<li>
				<ul class="dashboard-menu nav nav-list collapse in">
					<li>
						<a href="#page-main" class="panel-heading" data-toggle="collapse">
							<span class="fa fa-caret-right"></span> 指令管理</a>
					</li>
					<li>
						<a href="#page-main" class="panel-heading" data-toggle="collapse">
							<span class="fa fa-caret-right"></span> 指令日志查看</a>
					</li>
					<li>
						<a href="#page-main" class="panel-heading" data-toggle="collapse">
							<span class="fa fa-caret-right"></span> 语音合成管理</a>
					</li>
					<li>
						<a href="#page-main" class="panel-heading" data-toggle="collapse">
							<span class="fa fa-caret-right"></span> 折叠</a>
					</li>
				</ul>
			</li>
			<li><a href="#" data-target=".accounts-menu" class="nav-header"
				data-toggle="collapse"><i class="fa fa-fw fa-briefcase"></i>
					系统管理<i class="fa fa-collapse"></i></a></li>
			<li>
				<ul class="accounts-menu nav nav-list collapse in">
					<li>
						<a href="#" onclick='showPage("/admin/user/","系统管理/用户管理")' class="panel-heading" data-toggle="collapse">
							<span class="fa fa-caret-right"></span> 用户管理</a>
					</li>
					<li>
						<a href="#page-main" class="panel-heading" data-toggle="collapse">
							<span class="fa fa-caret-right"></span> 登陆日志管理</a>
					</li>
				</ul>
		</ul>
	</div>

	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<span class="label label-info">${total }</span> 指令总数
				</p>
				<p class="stat">
					<span class="label label-success">${used }</span> 可使用数
				</p>
				<p class="stat">
					<span class="label label-danger">${del }</span> 刪除数
				</p>
			</div>

			<h1 class="page-title">应用</h1>
			<ul class="breadcrumb">
				<li><a href="">首页</a></li>
				<li class="active" id="filePath"></li>
			</ul>

		</div>
		<div class="main-content">
			<div class="panel">
        		<div id="page-main" class="panel-collapse panel-body collapse in">
        			欢迎使用zaqSmartHome管理界面
				</div>		
			</div>
		
			<footer>
				<hr>
				<p class="pull-right">
					有个性，没签名！
				</p>
			</footer>
		</div>
	</div>
	<div id="alertMessage"></div>
	<div class="modal fade" id="uploadFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	        <div class="modal-header">
	            <h3 id="myModalLabel">上传xml文件</h3>
	        </div>
	        <div class="modal-body">
	            <input type="file" name="file" id="uploadInputFile" class="file btn btn-primary" data-preview-file-type="text">
	        </div>
	      </div>
	    </div>
	</div>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		$(function() {
			$('.demo-cancel-click').click(function() {
				return false;
			});
		});
	</script>

</body>
</html>
