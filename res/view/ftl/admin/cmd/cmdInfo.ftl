<script src="/resources/js/CmdInfo.js" type="text/javascript"></script>	
<ul class="nav nav-tabs">
  <li class="active"><a href="#info" data-toggle="tab">修改指令信息</a></li>
</ul>

<div class="row">
    <br>
    <div id="myTabContent" class="tab-content">
      <div class="form-horizontal fade in col-md-12" id="cmdInfo" role="form">
		        <input type="hidden" id="cmdId" name="cmdId" value="${cmd.id }">
		        <div class="form-group">
		        	<span  class="col-md-2"><label >功能描述</label></span>
		        	<span class="col-md-10">		       
		        	   <input class="form-control" type="text" id="function" name="function" value="${cmd.function }">
		        	</span>
		        </div>
		        <div class="form-group">
			        <span class="col-md-2"><label>语音文本字令</label></span>
			        <span class="col-md-10">
			        	<input type="text" id="cmd" name="cmd" value="${cmd.cmd }" class="form-control">
			        </span>
		        </div>
		        <div class="form-group">
			        <span class="col-md-2"><label>发送类型</label></span>
			        <span class="col-md-4">
			        	<select id="type" name="type" class="form-control">
	                         <option value="1" ${((cmd.type)==1)?string('selected="true"','') } >红外 </option>
	                         <option value="2" ${((cmd.type)==2)?string('selected="true"','') } >无线 315M </option>
	                         <option value="3" ${((cmd.type)==3)?string('selected="true"','') } >无线 433M </option>
	                         <option value="4" ${((cmd.type)==4)?string('selected="true"','') } >gpio开关型 </option>
	                    </select>
			        </span>
			        <span class="col-md-2"><label>发送给设备的指令</label></span>
			        <span class="col-md-4">
				        <input type="text" id="code" name="code" value="${cmd.code }" class="form-control">
			        </span>
		        </div>
		        <div class="form-group" id="wirelessProtocolDiv">
			        <span class="col-md-2"><label>使用的无线传输协议</label></span>
			        <span class="col-md-10">
			        	<select id="wirelessProtocol" name="wirelessProtocol" class="form-control">
			        		 <option value=""  >请选择 </option>
	                         <option value="1" >ONE </option>
	                         <option value="2" >TWO </option>
	                         <option value="3" >THREE </option>
	                    </select>
			        </span>
		        </div>
		        <div class="form-group">
		        	<span class="col-md-2"><label>延时执行的指令</label></span>
			        <span class="col-md-4">
    			        <select id="autoDelayExecId" class="form-control">
    			        	 <option value=""  >请选择 </option>
	                         <#list delayCmds as delayCmd>
	                         	<option value="${delayCmd.id}" } >${delayCmd.cmd} </option>
	                         </#list>
	                    </select>	 
    			        	 
			        </span>
			        <span class="col-md-2"><label>默认指定的延时长</label></span>
			        <span class="col-md-4">
	    			        <input type="text" id="autoDelayTime" name="autoDelayTime" value="${cmd.autoDelayTime!'0' }" class="form-control">
			        </span>
		        </div>
		        <div class="form-group">
		        	<span class="col-md-2"><label>是否为系统的指令</label></span>
			        <span class="col-md-4">
			        	<section class="model-1">
						  <div class="checkbox">
						    <input id="isSys" name="isSys" ${((cmd.isSys)==1)?string('checked="checked"','') } type="checkbox">
						    <label></label>
						  </div>
						</section>   
			        </span>
			        <span class="col-md-2"><label>创建时间</label></span>
			        <span class="col-md-4">
        			        <input type="text" readOnly="readOnly" id="timeCreate" name="timeCreate" value="${cmd.timeCreate }" class="form-control">
			        </span>
		        </div>
		        <div class="btn-toolbar list-toolbar">
			      <button class="btn btn-primary" id="saveCmdInfo"><i class="fa fa-save"></i> 保存</button>
			    </div>
      </div>
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#wirelessProtocol").val(${cmd.wirelessProtocol!''});
		$("#autoDelayExecId").val(${cmd.autoDelayExecId!''});
	})
</script>