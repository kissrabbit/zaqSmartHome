<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1.0" />
<title>登录界面</title>

<link rel="stylesheet" type="text/css"
	href="/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/login.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
<script src="/lib/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/lib/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<style type="text/css">
html,body {
	height: 100%;
}
</style>
<script type="text/javascript">
	$(function() {
		if('${message_login}'){
			$("#loginAlert").modal('show')
		}
		
	});
</script>
</head>

<body>
	<div class="box">
		<div class="login-box">
			<div class="login-title text-center">
				<h1>
					<small>登录</small>
				</h1>
			</div>
			<div class="login-content ">
				<div class="form">
					<form action="login" method="post">
						<div class="form-group">
							<div class="col-xs-12  ">
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-user"></span></span> <input type="text"
										id="username" name="userName" class="form-control"
										placeholder="用户名" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12  ">
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-lock"></span></span> <input
										type="password" id="password" name="password"
										class="form-control" placeholder="密码" />
								</div>
							</div>
						</div>
							<div class="col-xs-12  ">
								<div class="input-group">
									<section class="model-1">
									  <div class="checkbox">
									    <input id="rememberMe" name="rememberMe" type="checkbox">记住密码一个月</label>
									    <label></label>
									  </div>
									</section>   
								</div>
							</div>
						<div class="form-group form-actions">
							<div class="col-xs-4 col-xs-offset-4 ">
								<button type="submit" class="btn btn-sm btn-info">
									<span class="glyphicon glyphicon-off"></span> 登录
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="loginAlert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	        <div class="modal-header">
	            <h5 id="myModalLabel">登陆提示</h5>
	        </div>
	        <div class="modal-body">
	        	<h3>${message_login}</h3>
	        </div>
	      </div>
	    </div>
	</div>
</body>

</html>