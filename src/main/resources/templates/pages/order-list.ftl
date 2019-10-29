<div style="margin-bottom:10px ">
    <form id="orderSearch" method="post">
        订单ID:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="orderIdSearch" style="width:150px">
        客户ID:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="userIdOrderSearch" style="width:150px">
        课程ID:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseIdOrderSearch" style="width:150px">
        课程名称:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseNameOrderSearch" style="width:150px">
        名师ID:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="goodBelongToOrderSearch" style="width:150px">
        订单状态:
            <select id="orderStatus" class="easyui-combobox" name="dept" style="width:100px;">
                <option value=""> </option>
                <option value="1">已下单</option>
                <option value="2">已付款</option>
                <option value="3">已完成</option>
                <option value="4">退款中</option>
                <option value="5">已退款</option>
            </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchOrder()">查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearOrder()">清空</a>
    </form>

</div>

<table class="easyui-datagrid" id="orderList" title="订单列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/orderMan/getOrderList',method:'get',pageSize:30,
                     toolbar:toolbar,onLoadError:onLoadOrderError">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'orderId',align:'center',width:150">订单ID</th>
        <th data-options="field:'thirdTradeno',align:'center',width:250">第三方订单号</th>
        <th data-options="field:'userId',align:'center',width:150">客户ID</th>
        <th data-options="field:'userName',align:'center',width:150">客户昵称</th>
        <th data-options="field:'goodId',align:'center',width:150">商品ID</th>
        <th data-options="field:'goodName',align:'center',width:300">商品名称</th>
        <th data-options="field:'goodBelongTo',align:'center',width:150">名师ID</th>
        <th data-options="field:'masterName',align:'center',width:150">名师姓名</th>
        <th data-options="field:'orderStatus',align:'center',width:150,formatter:E3.formatOrderStatus" >订单状态</th>
        <th data-options="field:'orderType',align:'center',width:100,formatter:E3.formatOrderType">订单类型</th>
        <th data-options="field:'orderAmount',width:100,align:'center',formatter:E3.formatPrice">订单金额(元)</th>
        <th data-options="field:'modeOfPayment',align:'center',width:80,formatter:E3.formatPayment">支付方式</th>
        <th data-options="field:'orderCreateTime',align:'center',width:200,formatter:E3.formatDateTime">创建时间</th>
        <th data-options="field:'orderPayTime',width:200,align:'center',formatter:E3.formatDateTime">支付时间</th>
    </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑课程" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/page/item-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

    function onLoadOrderError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }

    }

    function searchOrder() {
        var orderId=$("#orderIdSearch").val();
        var userId=$("#userIdOrderSearch").val();
        var courseId=$("#courseIdOrderSearch").val();
        var courseName=$("#courseNameOrderSearch").val();
        var masterId=$("#goodBelongToSearch").val();
        var orderStatus=$("#orderStatus").combobox("getValue");
        $('#orderList').datagrid("options").url = '/qianyi/orderMan/getOrderList?'
            + 'orderId='+ orderId
            + '&userId='+ userId
            + '&goodId='+ courseId
            + '&goodName='+ courseName
            + '&goodBelongTo='+ masterId
            + '&orderStatus='+ orderStatus
        $('#orderList').datagrid('load');
    }

    function clearOrder() {
        $('#orderSearch').form('reset');
    }

    function getOrderSelectionsIds(){
    	var itemList = $("#orderList");
    	var sels = itemList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].courseId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [];
</script>