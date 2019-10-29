<table class="easyui-datagrid" id="refundList" title="用户列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/order/getRefundList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadError">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'orderStatus',align:'center',width:120,formatter:E3.orderApproveStatus">审批状态</th>
        <th data-options="field:'orderId',align:'center',width:150">订单ID</th>
        <th data-options="field:'orderAmount',align:'center',width:120,formatter:E3.formatPrice">订单金额（￥）</th>
        <th data-options="field:'orderType',align:'center',width:120,formatter:E3.formatOrderType">订单类型</th>
        <th data-options="field:'userId',align:'center',width:150">用户ID</th>
        <th data-options="field:'goodName',align:'center',width:120">订单名称</th>
        <th data-options="field:'goodId',align:'center',width:150">订单ID</th>
        <th data-options="field:'goodBelongTo',align:'center',width:150">名师ID</th>
        <th data-options="field:'orderCreateTime',align:'center',width:150,formatter:E3.formatDateTime">订单创建时间</th>
        <th data-options="field:'orderRefundStime',align:'center',width:150,formatter:E3.formatDateTime">申请退款时间</th>
        <th data-options="field:'modeOfPayment',align:'center',width:100,formatter:E3.formatPayment">支付方式</th>
        <th data-options="field:'orderRefundReason',align:'left',width:400">退款理由</th>
    </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑订单"
     data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/page/item-edit'"
     style="width:80%;height:80%;padding:10px;">
</div>
<script>
    function onLoadError(data) {
        if (data.responseText == 'sessionTimeOut') {
            $.messager.alert('错误', '登录信息失效，请重新登录!');
            window.location.href = "http://localhost:443/qianyi/loginPage.html";
        }

    }

    function getSelectionsIds() {
        var orderList = $("#refundList");
        var sels = orderList.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].orderId);
        }
        ids = ids.join(",");
        return ids;
    }

    var toolbar = [{
        text: '审批',
        iconCls: 'icon-edit',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '必须选择一个订单才能审批!');
                return;
            }
            if (ids.indexOf(',') > 0) {
                $.messager.alert('提示', '只能选择一个订单!');
                return;
            }
            $.messager.confirm('确认', '确定退款ID为 ' + ids + ' 的订单吗？', function (r) {
                if (r) {
                    var params = {
                        orderStatus:5,
                        orderId: ids
                    };
                    $.post("/qianyi/manager/orderApprove", params, function (data) {
                        if (data.code == 0) {
                            $.messager.alert('提示', '退款操作成功!', undefined, function () {
                                $("#refundList").datagrid("reload");
                            });
                        } else {
                            $.messager.alert('提示', data.msg);
                        }
                    });
                }
            });
        }
    }, {
        text: '驳回',
        iconCls: 'icon-cancel',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中订单!');
                return;
            }
            $.messager.confirm('确认', '确定驳回ID为 ' + ids + ' 的订单吗？', function (r) {
                if (r) {
                    var params = {
                        orderStatus:7,
                        orderId: ids
                    };
                    $.post("/qianyi/manager/orderApprove", params, function (data) {
                        if (data.status == 200) {
                            $.messager.alert('提示', '驳回订单成功!', undefined, function () {
                                $("#refundList").datagrid("reload");
                            });
                        } else {
                            $.messager.alert('提示', data.msg);
                        }
                    });
                }
            });
        }
    }];
</script>