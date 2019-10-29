<div style="margin-bottom:10px ">
    <form id="carouselSearch" method="post">
        课程ID:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseIdCarouselSearch" style="width:150px">
        名称:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseNameCarouselSearch" style="width:150px">
        讲师:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="userNameCarouselSearch" style="width:150px">
        类型:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseTypeCarouselSearch" style="width:150px">
        是否线上:是<input class="easyui-radio" data-options="iconCls:'icon-search'" type="radio" name="courseIsOnlineCarouselSearch" style="width:10px" value="1">
        否<input class="easyui-radio" data-options="iconCls:'icon-search'" type="radio" name="courseIsOnlineSearch" style="width:10px" value="0">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchCarouselCourse()">查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearCarouselCourse()">清空</a>
    </form>

</div>

<table class="easyui-datagrid" id="carouselList" title="首页课程轮播列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/courseMan/getCourseList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadCarouselError">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'courseId',align:'center',width:150">课程ID</th>
        <th data-options="field:'courseName',align:'center',width:300">课程名</th>
        <th data-options="field:'effectFlag',align:'center',width:100,formatter:E3.formatEffectStatus" >课程状态</th>
        <th data-options="field:'userName',align:'center',width:100">讲师名</th>
        <th data-options="field:'coursePrice',width:70,align:'center',formatter:E3.formatPrice">课程价格</th>
        <th data-options="field:'isCarousel',align:'center',width:100,formatter:E3.formatIsOrNotStatus">是否轮播</th>
        <th data-options="field:'courseType',align:'center',width:120">课程类型</th>
        <th data-options="field:'courseLevel',align:'center',width:200">课程等级</th>
        <th data-options="field:'courseIsOnline',align:'center',width:100">授课方式</th>
        <th data-options="field:'courseLearningFrequency',align:'center',width:100">学习次数</th>
        <th data-options="field:'courseWays',align:'center',width:200">课程形式</th>
        <th data-options="field:'createTime',width:150,align:'center',formatter:E3.formatDateTime">发布日期</th>
    </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑课程" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/page/item-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

    function onLoadCarouselError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }

    }

    function searchCarouselCourse() {
        var courseId=$("#courseIdCarouselSearch").val();
        var courseName=$("#courseNameCarouselSearch").val();
        var userName=$("#userNameCarouselSearch").val();
        var courseType=$("#courseTypeCarouselSearch").val();
        var courseIsOnline=$("input[name=courseIsOnlineCarouselSearch]:checked").val();
        if(courseIsOnline == undefined){
            courseIsOnline = '';
        }
        $('#courseList').datagrid("options").url = '/qianyi/courseMan/getCourseList?'
            + 'courseId='+ courseId
            + '&courseName='+courseName
            + '&userName='+userName
            + '&courseType='+courseType
            + '&courseIsOnline='+courseIsOnline;
        $('#courseList').datagrid('load');
    }

    function clearCarouselCourse() {
        $('#carouselSearch').form('reset');
    }

    function getCaiouselSelectionsIds(){
    	var itemList = $("#carouselList");
    	var sels = itemList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].courseId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'置为轮播',
        iconCls:'icon-add',
        handler:function(){
        	var ids = getCaiouselSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中课程!');
        		return ;
        	}
            var carousel = $("#carouselList").datagrid("getSelections")[0];
        	if(carousel.effectFlag == "2"){
                $.messager.alert('提示','上架课程才能进行轮播操作!');
                return ;
            }
        	$.messager.confirm('确认','确定轮播ID为 '+ids+' 的课程吗？',function(r){
        	    if (r){
        	    	var params = {
        	    	    "courseId":ids,
                        "type":1
        	    	};
                	$.post("/qianyi/course/carousel",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','设置轮播成功!',undefined,function(){
            					$("#carouselList").datagrid("reload");
                                $("#courseList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('提示','操作失败!');
                        }
            		});
        	    }
        	});
        }
    },{
        text:'取消轮播',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getCaiouselSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中课程!');
        		return ;
        	}
            var carousel = $("#carouselList").datagrid("getSelections")[0];
            if(carousel.effectFlag == "2"){
                $.messager.alert('提示','上架课程才能进行轮播操作!');
                return ;
            }
        	$.messager.confirm('确认','确定取消ID为 '+ids+' 的课程轮播吗？',function(r){
        	    if (r){
                    var params = {
                        "courseId":ids,
                        "type":0
                    };
                	$.post("/qianyi/course/carousel",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','取消轮播成功!',undefined,function(){
            					$("#carouselList").datagrid("reload");
                                $("#courseList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('提示','操作失败!');
                        }
            		});
        	    }
        	});
        }
    }];
</script>