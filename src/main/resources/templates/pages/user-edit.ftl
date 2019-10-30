<style>
    #sp input {
        width: 18px;
    }
	#sp1 input {
		width: 18px;
	}
</style>
<div style="padding:10px 10px 10px 10px">
    <form id="userEditForm" class="itemForm" method="post">
        <table cellpadding="5">
            <input type="hidden" name="userId" id="masterId" style="width: 280px;"></input>
            <tr>
                <td>真实姓名:</td>
                <td>
                    <input class="easyui-textbox" type="text" name="userName" data-options="required:true" disabled
                           style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>身份证号:</td>
                <td>
                    <input class="easyui-textbox" type="text" name="idCardNo" data-options="required:true"
                           style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>职称:</td>
                <td>
                    <select id="cc" style="width:150px" name="masterRank"></select>
                    <div id="sp">
                        <input type="radio" name="masterRank_1" value="中级"><span>中级</span><br/>
                        <input type="radio" name="masterRank_1" value="高级"><span>高级</span><br/>
                        <input type="radio" name="masterRank_1" value="资深"><span>资深</span><br/>
                    </div>

                </td>
            </tr>
            <tr>
                <td>官方:</td>
                <td>
                    <select id="cc1" style="width:150px" name="isOfficial"></select>
                    <div id="sp1">
                        <input type="radio" name="office" value="1"><span>是</span><br/>
                        <input type="radio" name="office" value="0"><span>否</span><br/>
                    </div>

                </td>
            </tr>
            <tr>
                <td>服务列表:</td>
                <td>
                    <table class="easyui-datagrid" id="userServiceList" title="服务列表"
                           data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/manager/getMasterServerList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadError">
                        <thead>
                        <tr>
                            <th data-options="field:'ck',checkbox:true"></th>
                            <th data-options="field:'serviceId',align:'center',width:140">ID</th>
                            <th data-options="field:'serviceName',align:'center',width:200">服务名称</th>
                            <th data-options="field:'servicePrice',align:'center',formatter:E3.formatPrice">服务金额(元)</th>
                        </tr>
                        </thead>
                    </table>
                    <div id="userTAddWindowService" class="easyui-window" title="服务发布"
                         data-options="modal:true,closed:true,iconCls:'icon-save',href:'../pages/user-service'"
                         style="width:20%;height:20%;padding:10px;">
                    </div>
                    <div id="userTEditWindowService" class="easyui-window" title="服务编辑"
                         data-options="modal:true,closed:true,iconCls:'icon-save',href:'../pages/user-serviceE'"
                         style="width:20%;height:20%;padding:10px;">
                    </div>
                </td>
            </tr>
        </table>
    </form>
    <div style="padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitMasterForm()">提交</a>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#cc').combo({
            required: true,
            editable: false
        });
        $('#sp').appendTo($('#cc').combo('panel'));
        $('#sp input').click(function () {
            var v = $(this).val();
            var s = $(this).next('span').text();
            $('#cc').combo('setValue', v).combo('setText', s).combo('hidePanel');
        });

		$('#cc1').combo({
			required: true,
			editable: false
		});
		$('#sp1').appendTo($('#cc1').combo('panel'));
		$('#sp1 input').click(function () {
			var v = $(this).val();
			var s = $(this).next('span').text();
			$('#cc1').combo('setValue', v).combo('setText', s).combo('hidePanel');
		});
    });

    function getSelectionsServiceIds() {
        var userServiceList = $("#userServiceList");
        var sels = userServiceList.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].serviceId);
        }
        ids = ids.join(",");
        return ids;
    }

    var toolbar = [{
        text: '添加',
        iconCls: 'icon-add',
        handler: function () {
            //最多限定九条数据
            var userServiceList = $("#userServiceList");
            var sels = userServiceList.datagrid("getRows");
            if (sels.length >= 9) {
                $.messager.alert('提示', '名师最多可以发布九条服务!');
                return false;
            }
            //表格数据添加一行
            $("#userTAddWindowService").window({
                onLoad: function () {
                    // 初始化选择类目组件（测算服务）
                    E3.initItemService()
                    // master userID 传递
                    $("#serviceUserId").val($("#masterId").val())
                }
            }).window("open");
        }
    }, {
        text: '编辑',
        iconCls: 'icon-edit',
        handler: function () {
            var ids = getSelectionsServiceIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '必须选择一项服务才能编辑!');
                return;
            }
            if (ids.indexOf(',') > 0) {
                $.messager.alert('提示', '只能选择一项服务!');
                return;
            }
            //表格数据添加一行
            $("#userTEditWindowService").window({
                onLoad: function () {
                    var data = $("#userServiceList").datagrid("getSelections")[0];
                    data.priceView = E3.formatPrice(data.servicePrice);
                    // master userID 传递
                    var userId = $("#masterId").val()
                    $("#serviceUserIdE").val(userId);
                    $("#userServiceEForm").form("load", data);
                    // 初始化选择类目组件（测算服务）
                    E3.initItemService(data)
                }
            }).window("open");
        }
    }, {
        text: '删除',
        iconCls: 'icon-cancel',
        handler: function () {
            var ids = getSelectionsServiceIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中测算服务!');
                return;
            }
            $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的服务吗？', function (r) {
                if (r) {
                    var params = {"serviceId": ids};
                    $.post("/qianyi/manager/deleteServiceByServiceId", params, function (data) {
                        if (data.code == 0) {
                            $.messager.alert('提示', '删除服务成功!', undefined, function () {
                                $("#userServiceList").datagrid("reload");
                            });
                        } else {
                            $.messager.alert('提示', '删除用户失败!');
                        }
                    });
                }
            });
        }
    }];

    function submitMasterForm() {
        if (!$('#userEditForm').form('validate')) {
            $.messager.alert('提示', '表单还未填写完成!');
            return;
        }
        $.post("/qianyi/manager/saveMasterIdNo", $("#userEditForm").serialize(), function (data) {
            if (data.code == 0) {
                $.messager.alert('提示', '保存用户信息成功!', 'info', function () {
                    $("#userTEditWindow").window('close');
                    $("#userMasterList").datagrid("reload");
                });
            }
        });
    }
</script>
