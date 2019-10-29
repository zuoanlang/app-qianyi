<div style="padding:10px 10px 10px 10px">
	<form id="opinionForm" class="itemForm" method="post">
	    <table cellpadding="5">
			<input type="hidden" name="userId"></input>
			<input type="hidden" name="flag" value="0"></input>
	        <tr>
	            <td>审批意见:</td>
				<td>
					<input class="easyui-textbox" name="approvalOpinion" id="approvalOpinion" data-options="required:true,multiline:true,validType:'length[0,200]'" style="height:100px;width: 400px;">
				</td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" id="reject" onclick="submitOpinionForm()">驳回</a>
	</div>
</div>
<script type="text/javascript">
	function submitOpinionForm(){
		if(!$('#opinionForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$.post("/qianyi/manager/approval",$("#opinionForm").serialize(), function(data){
			if(data.code == 0){
				$.messager.alert('提示','驳回成功!','info',function(){
					$("#approvalEditWindow").window('close');
					$("#userApplyList").datagrid("reload");
				});
			} else {
				$.messager.alert('错误',data.msg);
			}
		});
	}
</script>
