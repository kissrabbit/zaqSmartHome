<script src="/resources/js/userInfo.js" type="text/javascript"></script>	
<ul class="nav nav-tabs">
  <li class="active"><a href="#info" data-toggle="tab">修改用户信息</a></li>
</ul>

<div class="row">
    <br>
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane active fade in col-md-5" id="info">
		        <div class="form-group">
		        <label>用户名</label>
		        <input type="text" id="username" name="username" readOnly="readOnly" value="${user.username }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>密码</label>
     		    <input class="form-control" id="pwd" type="password" name="pwd" >
		        </div>
				<div class="form-group">
				    <section class="model-1">
					  <div class="checkbox">
					    <input id="isManager" name="isManager" checked="${((user.type)==0)?string('true','false') }" type="checkbox">是否为管理员</label>
					    <label></label>
					  </div>
					</section>   
				</div>
		        <div class="btn-toolbar list-toolbar">
			      <button class="btn btn-primary" id="saveUserInfo"><i class="fa fa-save"></i> 保存</button>
			    </div>
      </div>

    </div>

</div>
