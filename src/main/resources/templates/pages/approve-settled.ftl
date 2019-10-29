<table class="easyui-datagrid" id="userApplyList" title="用户列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/approve/getMasterSettledList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadError">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'applyStatus',align:'center',width:100,formatter:E3.formatSettledApproveStatus">审批状态</th>
        <th data-options="field:'userId',align:'center',width:130">用户ID</th>
        <th data-options="field:'userName',align:'center',width:120">用户名</th>
        <th data-options="field:'profession',align:'center',width:100,formatter:E3.formatProfession">职业选择</th>
        <th data-options="field:'handphone',align:'center',width:120">手机号</th>
        <th data-options="field:'major',align:'center',width:300">擅长分类</th>
        <th data-options="field:'masterIntroduction',align:'center',width:400">名师介绍</th>
        <th data-options="field:'lastLoginTime',width:200,align:'center',formatter:E3.formatDateTime">最后登录日期</th>
        <th data-options="field:'registerTime',width:200,align:'center',formatter:E3.formatDateTime">注册日期</th>
    </tr>
    </thead>
</table>
<div id="approvalViewWindow" class="easyui-window" title="查看名师详情"
     data-options="modal:true,closed:true,iconCls:'icon-save',href:'/qianyi/pages/approve-details'"
     style="width:40%;height:45%;padding:10px;">
</div>
<div id="approvalEditWindow" class="easyui-window" title="查看名师详情"
     data-options="modal:true,closed:true,iconCls:'icon-save',href:'/qianyi/pages/approve-opinion'"
     style="width:40%;height:30%;padding:10px;">
</div>
<script>
    function onLoadError(data) {
        if (data.responseText == 'sessionTimeOut') {
            $.messager.alert('错误', '登录信息失效，请重新登录!');
            window.location.href = "http://localhost:443/qianyi/loginPage.html";
        }

    }

    function getApplySelectionsIds() {
        var userList = $("#userApplyList");
        var sels = userList.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].userId);
        }
        ids = ids.join(",");
        return ids;
    }

    var toolbar = [{
        text: '查看',
        iconCls: 'icon-search',
        handler: function () {
            var ids = getApplySelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '必须选择一个商品才能编辑!');
                return;
            }
            if (ids.indexOf(',') > 0) {
                $.messager.alert('提示', '只能选择一个!');
                return;
            }

            $("#approvalViewWindow").window({
                onLoad: function () {
                    //回显数据
                    var data = $("#userApplyList").datagrid("getSelections")[0];
                    $("#userApplyForm").form("load", data);
                    $("#img1").attr("src",data.idCardImg1);
                    $("#img2").attr("src",data.idCardImg2);
                }
            }).window("open");
        }
    },{
        text: '通过',
        iconCls: 'icon-ok',
        handler: function () {
            var ids = getApplySelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中申请!');
                return;
            }
            //状态判断
            var userList = $("#userApplyList");
            var sel = userList.datagrid("getSelections")[0];
            if (sel.applyStatus != 1 ){
                $.messager.alert('提示', '该申请已经审批结束');
                return;
            }
            var data = {
                flag:"1",
                userId:ids,
                approvalOpinion:"通过"
            }
            $.post("/qianyi/manager/approval",data, function(data){
                if(data.code == 0){
                    $.messager.alert('提示','审批成功!','info',function(){
                        $("#userApplyList").datagrid("reload");
                    });
                } else {
                    $.messager.alert('错误',data.msg);
                }
            });
        }
    }, {
        text: '驳回',
        iconCls: 'icon-cancel',
        handler: function () {
            var ids = getApplySelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中入驻申请!');
                return;
            }
            //状态判断
            var userList = $("#userApplyList");
            var sel = userList.datagrid("getSelections")[0];
            if (sel.applyStatus != 1 ){
                $.messager.alert('提示', '该申请已经审批结束');
                return;
            }

            $("#approvalEditWindow").window({
                onLoad: function () {
                    //回显数据
                    var data = $("#userApplyList").datagrid("getSelections")[0];
                    $("#opinionForm").form("load", data);
                }
            }).window("open");
        }
    }];
</script>