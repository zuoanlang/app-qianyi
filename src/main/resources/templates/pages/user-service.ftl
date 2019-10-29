<div style="padding:10px 10px 10px 10px">
	<form id="userServiceForm" class="itemForm" method="post">
	    <table cellpadding="5">
			<input type="hidden" name="userId" id="serviceUserId" style="width: 280px;"></input>
	        <tr>
	            <td>服务名称:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectItemService">选择类目</a>
					<input type="hidden" name="serviceName" id="serviceName" style="width: 280px;"></input>
				</td>
	        </tr>
	        <tr>
	            <td>服务金额:</td>
	            <td>
					<input class="easyui-numberbox" type="text" name="servicePrice" data-options="min:0.1,max:99999999,precision:2,required:true" style="width: 200px;"/>
				</td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitServiceForm()">保存</a>
	</div>
</div>
<script type="text/javascript">
	function submitServiceForm(){
		if(!$('#userServiceForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$("#userServiceForm [name=servicePrice]").val(eval($("#userServiceForm [name=servicePrice]").val()) * 100);

		$.post("/qianyi/manager/saveServiceByMasterId",$("#userServiceForm").serialize(), function(data){
			if(data.code == 0){
				$.messager.alert('提示','保存测算服务成功!','info',function(){
					$("#userTAddWindowService").window('close');
					$("#userServiceList").datagrid("reload");
				});
			} else {
				$.messager.alert('错误',data.msg);
			}
		});
	}
</script>
