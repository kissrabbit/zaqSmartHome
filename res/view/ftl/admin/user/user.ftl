<script src="/resources/js/User.js" type="text/javascript"></script>	
<div class="btn-toolbar list-toolbar">
  <div class="btn-group">
  	<a class="btn btn-primary" href="#" onclick="showPage('/admin/user/addUser')" data-toggle="modal" class="padding-right-small"><i class="fa fa-plus"></i> 新增用户 </a>
  </div>
</div>
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>用户名称</th>
      <th>是否为管理员</th>
      <th style="width: 3.5em;"></th>
    </tr>
  </thead>
  <tbody>
  		<#list users as user>
   		<tr id="user_del_${user.username }">
	      <td>${user_index+1}</td>
	      <td>${user.username }</td>
	      <td> ${((user.type)==0)?string('是','否')}</td>
	     
	      <td>
		       <a href="#" onclick="showPage('/admin/user/${user.id }')"><i class="fa fa-pencil"></i></a>
		       <a href="javascript:void(0)" onclick="User.doDel('${user.username }')" role="button" data-toggle="modal"><i class="fa fa-trash-o"></i></a>
    	  </td>
	    </tr>
   	</#list>
  </tbody>
</table>

<div class="modal small fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="userModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="userModalLabel">Delete Confirmation</h3>
        </div>
        <div class="modal-body">
            <p class="error-text"><i class="fa fa-warning modal-icon"></i>确定删除此用户？</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancel</button>
            <button class="btn btn-danger" id="delUser" data-dismiss="modal">Delete</button>
        </div>
      </div>
    </div>
</div>
