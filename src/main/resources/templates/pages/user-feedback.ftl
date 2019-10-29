<table class="easyui-datagrid" id="feedbackList" title="用户反馈"
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/qianyi/message/getFeedbackList',
                     method:'get',pageSize:30,toolbar:feedToolbar,onLoadError:onLoadFeedbackError">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'messageId',align:'center',width:120">反馈ID</th>
        	<th data-options="field:'isRead',align:'center',width:80,formatter:E3.formatIsOrNotStatus">已读</th>
        	<th data-options="field:'messageSender',align:'center',width:150">用户ID</th>
            <th data-options="field:'remark1',align:'center',width:200">用户名</th>
            <th data-options="field:'remark2',align:'center',width:120">手机号</th>
            <th data-options="field:'messageContent',align:'left',width:600">反馈内容</th>
            <th data-options="field:'messageDateTime',width:150,align:'center',formatter:E3.formatDateTime">反馈日期</th>
        </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑反馈信息" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/page/item-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>
    function onLoadFeedbackError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }
    }
    
    function getFeedbackSelectionsIds(){
    	var feedbackList = $("#feedbackList");
    	var sels = feedbackList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].messageId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var feedToolbar = ['-',{
        text:'已读',
        iconCls:'icon-remove',
        handler:function(){
            var ids = getFeedbackSelectionsIds();
            if(ids.length == 0){
                $.messager.alert('提示','未选中反馈信息!');
                return ;
            }
            var params = {"ids":ids};
            $.post("/qianyi/manager/read",params, function(data){
                if(data.code == 0){
                    $.messager.alert('提示','信息已读成功!',undefined,function(){
                        $("#feedbackList").datagrid("reload");
                    });
                } else {
                    $.messager.alert('提示','操作失败，请刷新重试!');
                }
            });
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getFeedbackSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中反馈信息!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的反馈信息吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/qianyi/manager/deleteFeedBack",params, function(data){
            			if(data.code == 0){
            				$.messager.alert('提示','删除反馈信息成功!',undefined,function(){
            					$("#feedbackList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('错误','删除反馈信息失败!');
                        }
            		});
        	    }
        	});
        }
    }];
</script>