<table class="easyui-datagrid" id="userMasterList" title="名师列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/manager/getTeacherUserList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadError">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'userId',align:'center',width:150">名师ID</th>
            <th data-options="field:'userName',align:'center',width:200">名师名</th>
            <th data-options="field:'nickName',align:'center',width:200">昵称</th>
            <th data-options="field:'handphone',align:'center',width:120">手机号</th>
            <th data-options="field:'isOfficial',align:'center',width:120,formatter:E3.formatIsAdminStatus">官方(名师)</th>
            <th data-options="field:'idCardNo',align:'center',width:200">身份证号</th>
            <th data-options="field:'sex',align:'center',width:100,formatter:E3.formatSex">性别</th>
            <th data-options="field:'birthday',align:'center',width:200,formatter:E3.formatDateTime">生日</th>
            <th data-options="field:'lastLoginTime',width:200,align:'center',formatter:E3.formatDateTime">最后登录日期</th>
            <th data-options="field:'registerTime',width:200,align:'center',formatter:E3.formatDateTime">注册日期</th>
        </tr>
    </thead>
</table>
<div id="userTEditWindow" class="easyui-window" title="编辑名师" data-options="modal:true,closed:true,iconCls:'icon-save',href:'../pages/user-edit'" style="width:30%;height:50%;padding:10px;">
</div>
<script>
    function onLoadError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }

    }
    
    function getTSelectionsIds(){
    	var userList = $("#userMasterList");
    	var sels = userList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].userId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getTSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个名师才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个名师!');
        		return ;
        	}
        	
        	$("#userTEditWindow").window({
        		onLoad :function(){
        			//回显数据
        			var data = $("#userMasterList").datagrid("getSelections")[0];
        			$("#userEditForm").form("load",data);
                    $('#userServiceList').datagrid("options").url = '/qianyi/manager/getMasterServerList?userId='+data.userId;
        		}
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getTSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中名师!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的名师吗(无法找回)？',function(r){
        	    if (r){
        	    	let userId = {"userId":ids};
                	$.post("/qianyi/manager/deleteMaster",userId, function(data){
            			if(data.code == 0){
            				$.messager.alert('提示','删除名师成功!',undefined,function(){
            					$("#userMasterList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('错误','删除失败!');
                        }
            		});
        	    }
        	});
        }
    }];
</script>