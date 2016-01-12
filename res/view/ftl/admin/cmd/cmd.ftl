<script src="/resources/js/Cmd.js" type="text/javascript"></script>	
<div class="btn-toolbar list-toolbar">
  <div class="btn-group">
  	<a class="btn btn-primary" href="#" onclick="showPage('/admin/cmd/addCmd')" data-toggle="modal" class="padding-right-small"><i class="fa fa-plus"></i> 新增指令 </a>
  </div>
</div>
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>功能描述</th>
      <th>文本字令</th>
      <th>发送类型</th>
      <th>创建时间</th>
      <th>是否为系统指令</th>
      <th>是否删除</th>
      <th style="width: 3.5em;"></th>
    </tr>
  </thead>
  <tbody>
  		<#list cmds as cmd>
   		<tr id="cmd_del_${cmd.id }">
	      <td>${cmd_index+1}</td>
	      <td>${cmd.function }</td>
	      <td>${cmd.cmd }</td>
	      <td>${cmd.type }</td>
	      <td>${cmd.timeCreate }</td>
	      <td> ${((cmd.isSys)==1)?string('是','否')}</td>
	      <td> ${((cmd.isDel)==1)?string('是','否')}</td>
	      <td>
		       <a href="#" onclick="showPage('/admin/cmd/${cmd.id }')"><i class="fa fa-pencil"></i></a>
		       <a href="javascript:void(0)" onclick="Cmd.doDel('${cmd.id }')" role="button" data-toggle="modal"><i class="fa fa-trash-o"></i></a>
    	  </td>
	    </tr>
   	</#list>
  </tbody>
</table>

<div class="modal small fade" id="cmdModal" tabindex="-1" role="dialog" aria-labelledby="cmdModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="cmdModalLabel">Delete Confirmation</h3>
        </div>
        <div class="modal-body">
            <p class="error-text"><i class="fa fa-warning modal-icon"></i>确定删除此指令？</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancel</button>
            <button class="btn btn-danger" id="delCmd" data-dismiss="modal">Delete</button>
        </div>
      </div>
    </div>
</div>
