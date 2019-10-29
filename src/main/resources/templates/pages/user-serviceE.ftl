<div style="padding:10px 10px 10px 10px">
	<form id="userServiceEForm" class="itemForm" method="post">
	    <table cellpadding="5">
			<input type="hidden" name="userId" id="serviceUserIdE" style="width: 280px;"/>
			<input type="hidden" name="serviceId" id="serviceIdE" style="width: 280px;"/>
	        <tr>
	            <td>服务名称:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectItemService">选择类目</a>
					<input type="hidden" name="serviceName" id="serviceNameE" style="width: 280px;"></input>
				</td>
	        </tr>
	        <tr>
	            <td>服务金额:</td>
	            <td>
					<input class="easyui-numberbox" type="text" name="priceView" data-options="min:0.1,max:99999999,precision:2,required:true" style="width: 200px;"/>
					<input type="hidden" name="servicePrice" id="servicePriceE"/>
				</td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitServiceEForm()">保存</a>
	</div>
</div>
<script type="text/javascript">
	function submitServiceEForm(){
		if(!$('#userServiceForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$("#userServiceEForm [name=servicePrice]").val(eval($("#userServiceEForm [name=priceView]").val()) * 100);

		$.post("/qianyi/manager/saveServiceByMasterId",$("#userServiceEForm").serialize(), function(data){
			if(data.code == 0){
				$.messager.alert('提示','保存测算服务成功!','info',function(){
					$("#userTEditWindow").window('close');
					$("#userMasterList").datagrid("reload");
				});
			} else {
				$.messager.alert('错误',data.msg);
			}
		});
	}
</script>
