<div style="margin-bottom:10px ">
    <form id="courseSearch" method="post">
        课程ID:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseIdSearch" style="width:150px">
        名称:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseNameSearch" style="width:150px">
        讲师:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="userNameSearch" style="width:150px">
        类型:<input class="easyui-textbox" data-options="iconCls:'icon-search'" id="courseTypeSearch" style="width:150px">
        是否线上:是<input class="easyui-radio" data-options="iconCls:'icon-search'" type="radio" name="courseIsOnlineSearch" style="width:10px" value="1">
                否<input class="easyui-radio" data-options="iconCls:'icon-search'" type="radio" name="courseIsOnlineSearch" style="width:10px" value="0">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchCourse()">查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearCourse()">清空</a>
    </form>

</div>

<table class="easyui-datagrid" id="courseList" title="课程列表"
       data-options="singleSelect:true,collapsible:true,pagination:true,url:'/qianyi/courseMan/getCourseList',method:'get',pageSize:30,toolbar:toolbar,onLoadError:onLoadCourseError">
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
            <th data-options="field:'createTime',width:200,align:'center',formatter:E3.formatDateTime">发布日期</th>
        </tr>
    </thead>
</table>
<div id="courseAddWindow" class="easyui-window" title="新增课程" data-options="modal:true,minimizable:false,maximizable:false,closed:true,iconCls:'icon-save',href:'../pages/course-add'" style="width:80%;height:90%;padding:10px;">
</div>
<div id="courseEditWindow" class="easyui-window" title="编辑课程" data-options="modal:true,minimizable:false,maximizable:false,closed:true,iconCls:'icon-save',href:'../pages/course-edit'" style="width:80%;height:90%;padding:10px;">
</div>
<script>

    function searchCourse() {
        var courseId=$("#courseIdSearch").val();
        var courseName=$("#courseNameSearch").val();
        var userName=$("#userNameSearch").val();
        var courseType=$("#courseTypeSearch").val();
        var courseIsOnline=$("input[name=courseIsOnlineSearch]:checked").val();
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

    function clearCourse() {
        $('#courseSearch').form('reset');
    }
    function onLoadCourseError(data) {
        if(data.responseText == 'sessionTimeOut'){
            $.messager.alert('错误','登录信息失效，请重新登录!');
            window.location.href="http://localhost:443/qianyi/loginPage.html";
        }

    }
    
    function getSelectionsCourseIds(){
    	var courseList = $("#courseList");
    	var sels = courseList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].courseId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
            $("#courseAddWindow").window({
                //预加载插入课程数据
                onLoad:function(){
                    // 1.获取课程id
                    var courseId ;
                    $.getJSON('/qianyi/courseMan/getCourseId',function(_data){
                        if(_data.code == "0"){
                            courseId = _data.courseId;
                            $("#courseId").val(_data.courseId);
                            // 2.初始化目录
                            courseAddInit(courseId);
                        } else {
                            $.messager.alert('提示',_data.msg);
                        }
                    });
                    // 初始化选择类目组件（课程分类）
                    E3.initItemCat();
                    // 初始化选择类目组件（课程级别）
                    E3.selectCourseLevel();
                    // 初始化选择类目组件（授课方式）
                    E3.selectTeachMethods()
                },
                onClose : function(){
                    editorCourse.destroy();
                    editorCourseUnder.destroy();
                }
            }).window("open");
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsCourseIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一门课程才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一门课程!');
        		return ;
        	}
        	$("#courseEditWindow").window({
        		onLoad :function(){
        			//回显数据
        			var data = $("#courseList").datagrid("getSelections")[0];
        			data.priceView = E3.formatPrice(data.coursePrice);
        			$("#courseEditForm").form("load",data);

                    $("#courseEditImg").attr("src",data.courseImg);

                    editorCourseUnderEdit.ready(function () {//监听编辑器实例化完成的事件
                        editorCourseUnderEdit.setContent($('#courseDetailsEdit').val(),false);
                    });
                    // 初始化课程目录
                    courseEditInit(data.courseId);

                    // 初始化选择类目组件（课程分类）
                    E3.initItemCat(data);
                    // 初始化选择类目组件（课程级别）
                    E3.selectCourseLevel(data);
                    // 初始化选择类目组件（授课方式）
                    E3.selectTeachMethods(data)

        		},
                onClose : function(){
                    editorCourseEdit.destroy();
                    editorCourseUnderEdit.destroy();
                }
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsCourseIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中课程!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的课程吗？',function(r){
        	    if (r){
        	    	var params = {"courseId":ids};
                	$.post("/qianyi/course/deleteCourse",params, function(data){
            			if(data.code == "0"){
            				$.messager.alert('提示','删除课程成功!',undefined,function(){
            					$("#courseList").datagrid("reload");
                                $("#carouselList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('提示','删除课程失败!',undefined,function(){
                                $("#courseList").datagrid("reload");
                                $("#carouselList").datagrid("reload");
                            });
                        }
            		});
        	    }
        	});
        }
    },'-',{
        text:'下架',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsCourseIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中课程!');
        		return ;
        	}
            var sels = $("#courseList").datagrid("getSelections");
        	if(sels[0].effectFlag == "2"){
                $.messager.alert('提示','该课程已经下架，请勿重复操作');
                return ;
            }
        	$.messager.confirm('确认','确定下架ID为 '+ids+' 的课程吗？',function(r){
        	    if (r){
        	    	var params = {
                        courseId:ids,
                        type:"2"
        	    	};
                	$.post("/qianyi/course/updateEffectStatus",params, function(data){
            			if(data.code == 0){
            				$.messager.alert('提示','下架课程成功!',undefined,function(){
            					$("#courseList").datagrid("reload");
                                $("#carouselList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('提示','下架课程失败!',undefined,function(){
                                $("#courseList").datagrid("reload");
                                $("#carouselList").datagrid("reload");
                            });
                        }
            		});
        	    }
        	});
        }
    },{
        text:'上架',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsCourseIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中课程!');
        		return ;
        	}
            var sels = $("#courseList").datagrid("getSelections");
            if(sels[0].effectFlag == "1"){
                $.messager.alert('提示','该课程已经上架，请勿重复操作');
                return ;
            }
        	$.messager.confirm('确认','确定上架ID为 '+ids+' 的课程吗？',function(r){
        	    if (r){
        	    	var params = {
        	    	    courseId:ids,
                        type:"1"
        	    	};
                	$.post("/qianyi/course/updateEffectStatus",params, function(data){
            			if(data.code == 0){
            				$.messager.alert('提示','上架课程成功!',undefined,function(){
            					$("#courseList").datagrid("reload");
                                $("#carouselList").datagrid("reload");
            				});
            			} else {
                            $.messager.alert('提示','上架课程失败!',undefined,function(){
                                $("#courseList").datagrid("reload");
                            });
                        }
            		});
        	    }
        	});
        }
    }];
</script>