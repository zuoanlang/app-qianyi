<table class="easyui-datagrid" id="userAdminList" title="管理员列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/manager/getAdminUserList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadError">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'userId',align:'center',width:150">用户ID</th>
            <th data-options="field:'userName',align:'center',width:200">用户名</th>
            <th data-options="field:'handphone',align:'center',width:120">手机号</th>
            <#--<th data-options="field:'isMaster',align:'center',width:100,formatter:E3.formatIsAdminStatus">名师</th>
            <th data-options="field:'isOfficial',align:'center',width:120,formatter:E3.formatIsAdminStatus">官方(名师)</th>
            <th data-options="field:'idCardNo',align:'center',width:150">身份证号</th>
            <th data-options="field:'sex',align:'center',width:100,formatter:E3.formatSex">性别</th>
            <th data-options="field:'birthday',align:'center',width:200,formatter:E3.formatBirthday">生日</th>-->
            <th data-options="field:'lastLoginTime',width:150,align:'center',formatter:E3.formatDateTime">最后登录日期</th>
            <th data-options="field:'registerTime',width:150,align:'center',formatter:E3.formatDateTime">注册日期</th>
        </tr>
    </thead>
</table>
<div id="adminAddWindow" class="easyui-window" title="添加管理员" data-options="modal:true,closed:true,iconCls:'icon-save',href:'../pages/user-admin-add'" style="width:20%;height:25%;padding:10px;">
</div>
<div id="adminEditWindow" class="easyui-window" title="编辑管理员" data-options="modal:true,closed:true,iconCls:'icon-save',href:'../pages/user-admin-edit'" style="width:20%;height:30%;padding:10px;">
</div>
<script>
    function onLoadError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }

    }
    
    function getSelectionsAdminIds(){
    	var userList = $("#userAdminList");
    	var sels = userList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].userId);
    	}
    	ids = ids.join(",");
    	return ids;
    }

    var toolbar = [{
        text:'添加管理员',
        iconCls:'icon-add',
        handler:function(){
            //最多限定九条数据
            var userServiceList = $("#userAdminList");
            var sels = userServiceList.datagrid("getRows");
            //表格数据添加一行
            $("#adminAddWindow").window({

            }).window("open");
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
            var ids = getSelectionsAdminIds();
            if(ids.length == 0){
                $.messager.alert('提示','必须选择一条记录才能编辑!');
                return ;
            }
            if(ids.indexOf(',') > 0){
                $.messager.alert('提示','只能选择一项记录!');
                return ;
            }
            //表格数据添加一行
            $("#adminEditWindow").window({
                onLoad :function(){
                    var data = $("#userAdminList").datagrid("getSelections")[0];
                    $("#adminEditForm").form("load",data);
                    $("#userNameEdit").val(data.userName);
                }
            }).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
            var ids = getSelectionsAdminIds();
            if(ids.length == 0){
                $.messager.alert('提示','未选中管理员!');
                return ;
            }
            $.messager.confirm('确认','确定删除ID为 '+ids+' 的管理员吗？',function(r){
                if (r){
                    var params = {"userId":ids};
                    $.post("/qianyi/manager/deleteAdmin",params, function(data){
                        if(data.code == 0){
                            $.messager.alert('提示','删除用户成功!',undefined,function(){
                                $("#userAdminList").datagrid("reload");
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