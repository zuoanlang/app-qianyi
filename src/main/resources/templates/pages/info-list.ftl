<div style="margin-bottom:10px ">
    <form id="infoSearch" method="post">
        资讯ID:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="infoIdSearch" style="width:150px">
        资讯标题:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="infoTitleSearch" style="width:150px">
        作者:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="infoWriterSearch" style="width:150px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchInfo()">查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearInfo()">清空</a>
    </form>

</div>

<table class="easyui-datagrid" id="infoList" title="资讯列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,
                    url:'/qianyi/infoMan/getInfoList',
                    method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadInfoError">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'infoId',align:'center',width:150">资讯ID</th>
            <th data-options="field:'infoType',align:'center',width:200">资讯类型</th>
            <th data-options="field:'infoTitle',align:'center',width:120">标题</th>
            <th data-options="field:'infoWriter',align:'center',width:200">作者</th>
            <th data-options="field:'infoViewTimes',align:'center',width:100">浏览次数</th>
            <#--<th data-options="field:'effectFlag',width:60,align:'center',formatter:E3.formatItemStatus">资讯状态</th>-->
            <th data-options="field:'remark2',width:60,align:'center'">资讯状态</th>
            <th data-options="field:'publishedTime',width:150,align:'center',formatter:E3.formatDateTime">编辑时间</th>
        </tr>
    </thead>
</table>
<div id="infoEditWindow" class="easyui-window" title="编辑资讯" data-options="modal:true,closed:true,iconCls:'icon-save',href:'../pages/info-edit'" style="width:60%;height:80%;padding:10px;">
<div id="infoAddWindow" class="easyui-window" title="添加资讯" data-options="modal:true,closed:true,iconCls:'icon-save',href:'../pages/info-add'" style="width:60%;height:80%;padding:10px;">
</div>
<script>
    function onLoadInfoError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }

    }

    function searchInfo() {
        var infoId=$("#infoIdSearch").val();
        var infoTitle=$("#infoTitleSearch").val();
        var infoWriter=$("#infoWriterSearch").val();
        $('#infoList').datagrid("options").url = '/qianyi/infoMan/getInfoList?infoId='+infoId+'&infoTitle='+infoTitle+'&infoWriter='+infoWriter;
        $('#infoList').datagrid('load');
    }

    function clearInfo() {
        $('#infoSearch').form('reset');
    }
    function getSelectionsInfoIds(){
    	var infoList = $("#infoList");
    	var sels = infoList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].infoId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
            $("#infoAddWindow").window({
                onClose:function () {
                    editor.destroy();
                }
            }).window("open");
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsInfoIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一条资讯才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一条资讯!');
        		return ;
        	}
        	
        	$("#infoEditWindow").window({
        		onLoad :function(){
        			//回显数据
        			var data = $("#infoList").datagrid("getSelections")[0];
        			$("#infoEditForm").form("load",data);
                    $("#editImg").attr("src",$("#infoImgPathEdit").val());
                    editorEdit.ready(function () {//监听编辑器实例化完成的事件
                        editorEdit.setContent($("#infoContentEdit").val(),false);
                    });
        			//初始化
                    E3.selectInfoType(data);
        		},
                onClose:function () {
                    editorEdit.destroy();
                }
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsInfoIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中资讯!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的资讯吗？',function(r){
        	    if (r){
        	    	var params = {"infoIds":ids};
                	$.post("/qianyi/infoMan/deleteNews",params, function(data){
            			if(data.code == "0"){
            				$.messager.alert('提示','资讯删除成功!',undefined,function(){
            					$("#infoList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('提示','资讯删除失败!');
                        }
            		});
        	    }
        	});
        }
    },'-',{
        text:'发布',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsInfoIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中资讯!');
        		return ;
        	}
        	$.messager.confirm('确认','确定发布ID为 '+ids+' 的资讯吗？',function(r){
        	    if (r){
                    var params = {
                        "infoIds":ids,
                        "type":1
                    };
                	$.post("/qianyi/infoMan/publish",params, function(data){
            			if(data.code == 0){
            				$.messager.alert('提示','发布资讯成功!',undefined,function(){
            					$("#infoList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    },{
        text:'草稿',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsInfoIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中资讯!');
        		return ;
        	}
        	$.messager.confirm('确认','确定将ID为 '+ids+' 的资讯存为草稿吗？',function(r){
        	    if (r){
        	    	var params = {
        	    	    "infoIds":ids,
                        "type":0
        	    	};
                	$.post("/qianyi/infoMan/publish",params, function(data){
            			if(data.code == 0){
            				$.messager.alert('提示','存为草稿成功!',undefined,function(){
            					$("#infoList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>