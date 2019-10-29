<table class="easyui-datagrid" id="commentList" title="用户列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/qianyi/comment/getCommentApplyList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadError">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'remark1',align:'center',width:100,formatter:E3.formatApproveStatus">审批状态</th>
        <th data-options="field:'commentId',align:'center',width:130">评论ID</th>
        <th data-options="field:'commentUserId',align:'center',width:130">评论人的ID</th>
        <th data-options="field:'commentarySubjectId',align:'center',width:130">被评论主体ID</th>
        <th data-options="field:'commentType',align:'center',width:120,formatter:E3.formatCommentType">评论类型</th>
        <th data-options="field:'commentContent',align:'center',width:300">评论内容</th>
        <th data-options="field:'commentDateTime',width:200,align:'center',formatter:E3.formatDateTime">评论时间</th>
    </tr>
    </thead>
</table>
<script>
    function onLoadError(data) {
        if (data.responseText == 'sessionTimeOut') {
            $.messager.alert('错误', '登录信息失效，请重新登录!');
            window.location.href = "http://localhost:443/qianyi/loginPage.html";
        }

    }

    function getCommentSelectionsIds() {
        var userList = $("#commentList");
        var sels = userList.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].commentId);
        }
        ids = ids.join(",");
        return ids;
    }

    var toolbar = [{
        text: '通过',
        iconCls: 'icon-ok',
        handler: function () {
            var ids = getCommentSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中申请!');
                return;
            }
            //状态判断
            var userList = $("#commentList");
            var sel = userList.datagrid("getSelections")[0];
            if (sel.remark1 != 0 ){
                $.messager.alert('提示', '该申请已经审批结束');
                return;
            }
            var data = {
                flag:"1",
                commentId:ids,
            }
            $.post("/qianyi/comment/approval",data, function(data){
                if(data.code == 0){
                    $.messager.alert('提示','审批成功!','info',function(){
                        $("#commentList").datagrid("reload");
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
            var ids = getCommentSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中申请!');
                return;
            }
            //状态判断
            var userList = $("#commentList");
            var sel = userList.datagrid("getSelections")[0];
            if (sel.remark1 != 1 ){
                $.messager.alert('提示', '该申请已经审批结束');
                return;
            }
            var data = {
                flag:"0",
                commentId:ids,
            }
            $.post("/qianyi/comment/approval",data, function(data){
                if(data.code == 0){
                    $.messager.alert('提示','驳回成功!','info',function(){
                        $("#commentList").datagrid("reload");
                    });
                } else {
                    $.messager.alert('错误',data.msg);
                }
            });
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
            var ids = getCommentSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中申请!');
                return;
            }
            //状态判断
            var userList = $("#commentList");
            var sel = userList.datagrid("getSelections")[0];
            if (sel.remark1 == 1 ){
                $.messager.alert('提示', '该申请未审批，无法删除');
                return;
            }
            var data = {
                commentId:ids,
            }
            $.post("/qianyi/comment/delete",data, function(data){
                if(data.code == 0){
                    $.messager.alert('提示','删除成功!','info',function(){
                        $("#commentList").datagrid("reload");
                    });
                } else {
                    $.messager.alert('错误',data.msg);
                }
            });
        }
    }];
</script>