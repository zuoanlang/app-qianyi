<div style="padding:10px 10px 10px 10px">
	<form id="adminAddForm" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>用户名:</td>
	            <td>
					<input class="easyui-textbox" type="text" name="userName" data-options="required:true,validType:'length[3,10]'"  style="width: 200px;"/>
				</td>
	        </tr>
			<tr>
				<td>密码:</td>
				<td>
					<input class="easyui-textbox" id="password" type="password" name="password" data-options="required:true,validType:'length[3,10]'"  style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<td>确认密码:</td>
				<td>
					<input class="easyui-textbox" id="password_r" type="password" name="password_r" data-options="required:true,validType:'length[3,10]'"  style="width: 200px;"/>
				</td>
			</tr>

	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAdminForm()">提交</a>
	</div>
</div>
<script type="text/javascript">
	function submitAdminForm(){
		if(!$('#adminAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return;
		}
		var password = $("#password").val();
		var password_r = $("#password_r").val();
		if (password === password_r) {

		} else {
			$.messager.alert('提示','密码错误');
			return;
		}
		$.post("/qianyi/manager/addAdmin",$("#adminAddForm").serialize(), function(data){
			if(data.code == 0){
				$.messager.alert('提示','新增管理员成功!','info',function(){
					$("#adminAddWindow").window('close');
					$("#userAdminList").datagrid("reload");
				});
			} else {
				$.messager.alert('提示',data.msg);
			}
		});
	}
</script>
