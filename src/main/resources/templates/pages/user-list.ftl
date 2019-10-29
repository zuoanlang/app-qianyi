<table class="easyui-datagrid" id="userOrdinaryList" title="用户列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/manager/getOrdinaryUserList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadError">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'userId',align:'center',width:150">用户ID</th>
            <th data-options="field:'nickName',align:'center',width:200">昵称</th>
            <th data-options="field:'handphone',align:'center',width:120">手机号</th>
            <th data-options="field:'sex',align:'center',width:100,formatter:E3.formatSex">性别</th>
            <th data-options="field:'birthday',align:'center',width:200,formatter:E3.formatDateTime">生日</th>
            <th data-options="field:'lastLoginTime',width:200,align:'center',formatter:E3.formatDateTime">最后登录日期</th>
            <th data-options="field:'registerTime',width:200,align:'center',formatter:E3.formatDateTime">注册日期</th>
        </tr>
    </thead>
</table>
<div id="userEditWindow" class="easyui-window" title="编辑用户" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/page/user-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>
    function onLoadError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }

    }
    
    function getSelectionsUserIds(){
    	var userList = $("#userOrdinaryList");
    	var sels = userList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].userId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsUserIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中用户!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的用户吗？',function(r){
        	    if (r){
        	    	var userId = {"userId":ids};
                	$.post("/qianyi/manager/deleteUserById",userId, function(data){
            			if(data.code == 0){
            				$.messager.alert('提示','删除用户成功!',undefined,function(){
            					$("#userOrdinaryList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('提示','删除用户失败!');
                        }
            		});
        	    }
        	});
        }
    }];
</script>