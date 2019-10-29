<div style="padding:10px 10px 10px 10px">
	<form id="adminEditForm" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr>
				<input name="userName" id="userNameEdit" type="hidden">
	            <td>用户名:</td>
	            <td>
					<input class="easyui-textbox" id="adminName" type="text" name="userName" disabled data-options="required:true,validType:'length[3,10]'"  style="width: 200px;"/>
				</td>
	        </tr>
			<tr>
				<td>原密码:</td>
				<td>
					<input class="easyui-textbox" id="passwordEdit" type="password" name="password" data-options="required:true,validType:'length[3,10]'"  style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<td>新密码:</td>
				<td>
					<input class="easyui-textbox" id="password_n" type="password" name="password_n" data-options="required:true,validType:'length[3,10]'"  style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<td>确认密码:</td>
				<td>
					<input class="easyui-textbox" id="password_c" type="password" name="password_c" data-options="required:true,validType:'length[3,10]'"  style="width: 200px;"/>
				</td>
			</tr>

	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAdminEditForm()">提交</a>
	</div>
</div>
<script type="text/javascript">
	function submitAdminEditForm(){
		if(!$('#adminEditForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return;
		}
		var password_n = $("#password_n").val();
		var password_c = $("#password_c").val();
		if (password_n === password_c) {

		} else {
			$.messager.alert('提示','前后密码不一致');
			return;
		}
		$.post("/qianyi/manager/editAdmin",$("#adminEditForm").serialize(), function(data){
			if(data.code == 0){
				$.messager.alert('提示','修改密码成功,重新登录生效!','info',function(){
					$("#adminEditWindow").window('close');
					$("#userAdminList").datagrid("reload");
				});
			} else {
				$.messager.alert('提示',data.msg);
			}
		});
	}
</script>
