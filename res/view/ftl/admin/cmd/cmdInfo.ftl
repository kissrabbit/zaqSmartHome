<script src="/resources/js/cmdInfo.js" type="text/javascript"></script>	
<ul class="nav nav-tabs">
  <li class="active"><a href="#info" data-toggle="tab">修改指令信息</a></li>
</ul>

<div class="row">
    <br>
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane active fade in col-md-12" id="info">
		        <div class="form-group">
		        <label class="col-md-2" >功能描述</label>
		        <input class="col-md-10 form-control" type="text" id="function" name="function" value="${cmd.function }">
		        </div>
		        <div class="form-group">
		        <label>文本字令</label>
		        <input type="text" id="cmd" name="cmd" value="${cmd.cmd }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>发送类型</label>
		        <input type="text" id="type" name="type" value="${cmd.type }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>发送给设备的指令</label>
		        <input type="text" id="code" name="code" value="${cmd.code }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>使用的无线传输协议</label>
		        <input type="text" id="wirelessProtocol" name="wirelessProtocol" value="${cmd.wirelessProtocol!'' }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>默认指定的延时（单位s秒）</label>
		        <input type="text" id="autoDelayTime" name="autoDelayTime" value="${cmd.autoDelayTime!'0' }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>延时执行的指令</label>
		        <input type="text" id="autoDelayExecId" name="autoDelayExecId" value="${cmd.autoDelayExecId!'' }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>是否为系统的指令</label>
		         <section class="model-1">
					  <div class="checkbox">
					    <input id="isSys" name="isSys" ${((cmd.isSys)==1)?string('checked="checked"','') }" type="checkbox">
					    <label></label>
					  </div>
					</section>   
		        </div>
		        <div class="form-group">
		        <label>创建时间</label>
		        <input type="text" id="timeCreate" name="timeCreate" value="${cmd.timeCreate }" class="form-control">
		        </div>
		        <div class="btn-toolbar list-toolbar">
			      <button class="btn btn-primary" id="saveCmdInfo"><i class="fa fa-save"></i> 保存</button>
			    </div>
      </div>

    </div>

</div>
