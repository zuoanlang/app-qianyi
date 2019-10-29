<div style="padding:10px 10px 10px 10px">
	<form id="userApplyForm" class="itemForm" method="post">
	    <table cellpadding="5">
			<input type="hidden" name="userId" style="width: 280px;"></input>
	        <tr>
	            <td>名师姓名:</td>
				<td>
					<input type="text" class="easyui-textbox" name="userName"  disabled style="width: 200px;"/>
				</td>
				<td>身份证号:</td>
				<td>
					<input class="easyui-numberbox" type="text" disabled name="idCardNo" data-options="required:true" style="width: 200px;"/>
				</td>
	        </tr>
			<tr>
				<td>正面图片:</td>
				<td>
					<img id="img1" src="" style="height: 250px;width: 200px;border: 1px solid #D3D3D3;    margin-left: 1px;">
				</td>
				<td>反面图片:</td>
				<td>
					<img id="img2" src="" style="height: 250px;width: 200px;border: 1px solid #D3D3D3;    margin-left: 1px;">
				</td>
			</tr>
	    </table>
	</form>
</div>
<script type="text/javascript">
	function submitApplyForm(){
		if(!$('#userApplyForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
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
