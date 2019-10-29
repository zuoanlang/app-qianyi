<#--阿里云web直传-->
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/base64.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/upload-edit.js"></script>
<link rel="stylesheet" type="text/css" href="../js/oss-h5-upload-js-direct/style.css"/>
<style>
	.underline{
		display: none;
	}
</style>
<div style="padding:10px 10px 10px 10px" >
	<form id="courseEditForm" class="itemForm" method="post">
		<input type="hidden" name="courseId" id="courseIdEdit" value="">
		<input type="hidden" name="courseDetails" id="courseDetailsEdit" value="">
	    <table cellpadding="5">
			<tr>
				<td>课程名:</td>
				<td><input class="easyui-textbox" type="text" name="courseName" id="courseNameEdit" data-options="required:true" style="width: 405px;"></input></td>
				<td>讲师名:</td>
				<td>
					<input class="easyui-textbox" type="text" name="userName" id="userNameEdit" data-options="required:true,validType:'length[1,30]'" style="width: 405px;"/>
				</td>
				<td>身份证:</td>
				<td>
					<input class="easyui-textbox" type="text" id="idCardNoEdit" name="idCardNo" data-options="required:true,validType:'length[1,18]'" style="width: 200px;"/>
				</td>
			</tr>
	        <tr>
				<td>授课方式:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectTeachMethods">选择类目</a>
					<input type="hidden" name="courseIsOnline" id="courseIsOnlineEdit" style="width: 280px;"></input>
				</td>
	            <td>课程分类:</td>
	            <td>
	            	<a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a>
	            	<input type="hidden" name="courseType" id="courseTypeEdit" style="width: 280px;"></input>
	            </td>
				<td>课程级别:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectCourseLevel">选择类目</a>
					<input type="hidden" name="courseLevel" id="courseLevelEdit" style="width: 280px;"></input>
				</td>

	        </tr>
			<tr>
				<td>课程价格(￥):</td>
				<td><input class="easyui-numberbox" type="text" name="priceView" data-options="min:0.1,max:99999999,precision:2,required:true" />
					<input type="hidden" name="coursePrice" id="coursePriceEdit"/>
				</td>
				<td class="online">课程形式:</td>
				<td class="online">
					<input class="easyui-textbox" type="text" name="courseWays" id="courseWaysEdit" data-options="required:false" style="width: 405px;"/>
				</td>
				<td class="online">视频时长:</td>
				<td class="online">
					<input id="videoTimeEdit" name="videoTime" class="easyui-timespinner" style="width:200px;" data-options="showSeconds:true">
				</td>
				<td class="underline">授课时间:</td>
				<td class="underline">
					<input id="startDateEdit" name="startdate" class="easyui-datebox underlineInput" style="width:150px;" data-options="min:'08:30',showSeconds:false">
					~
					<input id="endDateEdit" name="enddate" class="easyui-datebox underlineInput" style="width:150px;" data-options="min:'08:30',showSeconds:false">
				</td>
				<td class="underline">授课地点:</td>
				<td class="underline">
					<input id="addressEdit" name="courseAddress" class="easyui-textbox underlineInput" style="width:165px;" data-options="">
				</td>
			</tr>
			<tr class="underline" style="display: none">
				<td>课程详情:</td>
				<td rowspan="2" colspan="8">
					<!-- 加载编辑器的容器 -->
					<script id="editorCourseUnderEdit" name="content" type="text/plain" style="width: 895px;height:400px;"/>
				</td>
			</tr>
	        <tr class="online">
				<td>课程目录:</td>
				<td rowspan="2">
					<div style="height: 600px;border: 1px solid #D3D3D3;">
						<ul id="contentCategory-course-edit" class="easyui-tree" >
						</ul>
					</div>
				</td>
	            <td>目录文稿:</td>
				<td rowspan="2">
					<!-- 加载编辑器的容器 -->
					<script id="editorCourseEdit" name="content" type="text/plain" style="width: 405px;height:400px;"/>
				</td>
				<td colspan="2">
					文件名称：<input type="text" class="easyui-textbox" name="videoNameEdit" id="videoNameEdit" value="" disabled style="width: 200px;"/>
					<div style="display: none">
						<input type="hidden" name="videoPath" id="videoPathEdit" value="" />
					</div>
					<br/>

					<#--<h4>您所选择的文件列表：</h4>-->
					<div style="margin-top: 50px">文件上传：</div>
					<div style="margin-left: 66px;margin-top: -50px;">
						<div id="ossfile-edit-course" style="margin-bottom: -15px;height: 48px">
							你的浏览器不支持flash,Silverlight或者HTML5！
						</div>

						<br/>
						<div id="container">
							<a id="selectfiles-edit-course" href="javascript:void(0);" class='btn'>选择文件</a>
							<a id="postfiles-edit-course" href="javascript:void(0);" class='btn'>开始上传</a>
						</div>
						<pre id="console"></pre>
					</div>

				</td>
			</tr>
	        <tr>


	        </tr>
	        <tr class="">
				<td>课程配图:</td>
				<td>
					<img id="courseEditImg" src="" style="height: 228px;width: 405px;border: 1px solid #D3D3D3;    margin-left: 1px;">
					<br>
					<span style="color: red">建议尺寸比例(16:9)</span>
					<div style="display: none">
						<input type="hidden" name="courseImg" id="courseImgEdit" value="" />
						<div name="theform" style="display: none">

							<input type="radio" name="myradio-edit" value="local_name"/> 上传文件名字保持本地文件名字
							<input type="radio" name="myradio-edit" value="random_name" checked/> 上传文件名字是随机文件名
							<br/>
						</div>
					</div>

					<#--<h4>您所选择的文件列表：</h4>-->
					<div id="ossfile-edit">你的浏览器不支持flash,Silverlight或者HTML5！</div>

					<br/>
					<div id="container">
						<a id="selectfiles-edit" href="javascript:void(0);" class='btn'>选择文件</a>
						<a id="postfiles-edit" href="javascript:void(0);" class='btn'>开始上传</a>
					</div>
					<pre id="console"></pre>
				</td>
				<td>课程简介:</td>
				<td><input class="easyui-textbox" name="courseDescription" id="courseDescriptionEdit" data-options="multiline:true,validType:'length[0,200]'" style="height:270px;width: 400px;"></input></td>
			</tr>
	    </table>
	</form>
	<div id="contentCategoryMenu-course-edit" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandlerCourseEdit">
		<div data-options="iconCls:'icon-Edit',name:'Edit'">添加</div>
		<div data-options="iconCls:'icon-edit',name:'rename'">重命名</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
	</div>

	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitCourseFormEdit()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearCourseFormEdit()">重置</a>
	</div>
</div>
<script type="text/javascript">
	var editorCourseEdit;
	var editorCourseUnderEdit;
	var currentNode = {
		id:'',
		name:'',
		draft:'',
		isParent:''
	};
	$(function () {
		editorCourseEdit = UE.getEditor('editorCourseEdit', {
			imageFieldName: "upfile", //* 提交的图片表单名称 *!/
			allowDivTransToP: false
		});

		/* 赋值脚本 */
		editorCourseEdit.ready(function () {//监听编辑器实例化完成的事件
			editorCourseEdit.setContent("快来编辑您的课件文稿吧~~~");
			editorCourseEdit.setDisabled();
		});

		editorCourseUnderEdit = UE.getEditor('editorCourseUnderEdit', {
			imageFieldName: "upfile", //* 提交的图片表单名称 *!/
			allowDivTransToP: false
		});
		editorCourseUnderEdit.ready(function () {//监听编辑器实例化完成的事件
			//editorCourseUnderEdit.setContent("快来编辑您的线下课程海报吧~~~");
		})
	});
	function courseEditInit(courseId){
		$("#contentCategory-course-edit").tree({
			url : '/qianyi/catalog/list?type=1&courseId='+courseId,
			animate: true,
			method : "GET",
			onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
				$('#contentCategoryMenu-course-edit').menu('show',{
					left: e.pageX,
					top: e.pageY
				});
			},
			onAfterEdit : function(node){
				var _tree = $(this);
				if(node.id == 0){
					// 新增节点
					$.post('/qianyi/catalog/create?courseId='+courseId,{parentId:node.parentId,name:node.text},function(data){
						if(data.status == 200){
							_tree.tree("update",{
								target : node.target,
								id : data.data.catalogId
							});
						}else{
							$.messager.alert('提示','创建'+node.text+' 目录节点失败!');
						}
					});
				}else{
					$.post("/qianyi/catalog/update",{id:node.id,name:node.text});
				}
			},
			onClick:function(node){
				//比较两次节点
				if (!currentNode.isParent){
					updateDraftEdit(node,"click");
				}
				//1.将当前节点的值存储起来(课件文稿)
				//加载数据
				//判断该节点是否为父节点
				if(node.state == 'open' && node.children == undefined){
					$.get('/qianyi/catalog/getCatalogVideo?type=edit',{id:node.id},function(data){
						if(data.code == "0"){
							//1.取得数据,回显课程文件名称
							$("#courseEditForm").form("load",data);
							//2.回显课程文稿
							/* 赋值脚本 */
							editorCourseEdit.ready(function () {//监听编辑器实例化完成的事件
								editorCourseEdit.setEnabled();
								editorCourseEdit.setContent(data.videoDraftEdit,false);
							});
							//3.回显当前点击节点的视频时长
							$('#videoTimeEdit').timespinner('enable');
							$('#videoTimeEdit').timespinner('setValue', data.videoTimeEdit);
							//4.切换当前节点
							currentNode.id = node.id;
							currentNode.name = node.text,
							currentNode.draft = data.videoDraftEdit;
						}else{
							$.messager.alert('提示','该目录节点尚未录入视频和课件文稿');
						}
					});
				} else {
					var data={
						videoNameEdit:''
					}
					$("#courseEditForm").form("load",data);
					$('#videoTimeEdit').timespinner('disable');
					$('#videoTimeEdit').timespinner('setValue', '');
					editorCourseEdit.ready(function () {//监听编辑器实例化完成的事件
						editorCourseEdit.setContent("选择目录节点时可编辑",false);
						//不可编辑
						editorCourseEdit.setDisabled();
					})
				}
			}
		});
	}
	function updateDraftEdit(node,type){
		//资讯内容赋值
		editorCourseEdit.sync();
		currentNode.draft = editorCourseEdit.getContent();
		var flag = false;
		if(type == "click" ){
			if(node.id != currentNode.id && currentNode.id !=""){
				flag = true;
			}
		} else {
			flag = true;
		}
		if(flag){
			if(currentNode.id == ''&&currentNode.id == null){
				return;
			}
			$.ajax({
				type : "post",
				url : "/qianyi/catalog/updateDraft",
				data : {
					id:currentNode.id,
					draft:currentNode.draft
				},
				async : false,
				success : function(data){
					if(data.code == "0"){
						$.messager.show({
							title:'提示',
							msg:currentNode.name+'的课件文稿已经同步至服务器',
							timeout:3000,
							showType:'slide'
						});
					}else{
						$.messager.show({
							title:'提示',
							msg:currentNode.name+'的课件文稿同步至服务器失败',
							timeout:3000,
							showType:'slide'
						});
					}
				}
			});
		}

	}
	function menuHandlerCourseEdit(item){
		var tree = $("#contentCategory-course-edit");
		var node = tree.tree("getSelected");
		var nodeDeep = easyui_tree_options.getLevel("#contentCategory-course-edit",node);
		var flag = false;
		if(nodeDeep == 3 && item.name === "Edit"){
			$.messager.alert('提示','具体分类无法新建子分类！');
			return false;
		}

		if(node.state == 'open' && node.children == undefined){
			//子节点
			flag = true;
		}
		if(!flag){
			if(nodeDeep != 3 && item.name === "delete"){
				$.messager.alert('提示','父节点无法删除！');
				return false;
			}
		}


		if(item.name === "Edit"){
			tree.tree('append', {
				parent: (node?node.target:null),
				data: [{
					text: '新建分类',
					id : 0,
					parentId : node.id
				}]
			});
			var _node = tree.tree('find',0);
			tree.tree("select",_node.target).tree('beginEdit',_node.target);
		}else if(item.name === "rename"){
			tree.tree('beginEdit',node.target);
		}else if(item.name === "delete"){
			$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
				if(r){
					$.post("/qianyi/catalog/delete/",{id:node.id},function(){
						tree.tree("remove",node.target);
					});
				}
			});
		}
	}
	//获得tree的层数
	var easyui_tree_options = {
		length : 0,  //层数
		getLevel : function(treeObj, node){	//treeObj为tree的dom对象，node为选中的节点
			while(node != null){
				node = $(treeObj).tree('getParent', node.target)
				easyui_tree_options.length++;
			}
			var length1 = easyui_tree_options.length;
			easyui_tree_options.length = 0;		//重置层数
			return length1;
		}
	}

	//提交表单
	function submitCourseFormEdit(){
		//有效性验证
		if(!$('#courseEditForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//三个状态验证
		//1.授课方式

		if($('#courseIsOnline').val() == ""){
			$.messager.alert('提示','授课方式未填写');
			return ;
		}
		//2.课程分类
		if($('#courseLevel').val() == ""){
			$.messager.alert('提示','课程分类未填写');
			return ;
		}
		//3.课程级别
		if($('#courseType').val() == ""){
			$.messager.alert('提示','课程级别未填写');
			return ;
		}
		//4.课程在线状态判断
		if($("#courseIsOnline").val() == "23"){
			//线下课程时间处理
			var startDate = $('#startDate').combobox("getValue");
			var endDate = $('#endDate').combobox("getValue");
			if(startDate == ""){
				$.messager.alert('提示','课程开始时间未填写');
				return ;
			}
			if(endDate == ""){
				$.messager.alert('提示','课程结束时间未填写');
				return ;
			}
			if(startDate>endDate){
				$.messager.alert('提示','开始时间不能小于结束时间');
				return ;
			}
			//线下课程地点处理
			var address = $("#address").val();
			if(address.trim() =="" ){
				$.messager.alert('提示','授课地点不可为空');
				return ;
			}

		}
		updateDraftEdit(null,"submit");
		//取商品价格，单位为“分”
		$("#courseEditForm [name=coursePrice]").val(eval($("#courseEditForm [name=priceView]").val()) * 100);

		//ajax的post方式提交表单
		//将表单序列号为key-value形式的字符串
		var formJson;
		if($("#courseIsOnline").val() == "22"){
			formJson = {
				courseId:$("#courseIdEdit").val(),
				courseName:$("#courseNameEdit").val(),
				userName:$("#userNameEdit").val(),
				idCardNo:$("#idCardNoEdit").val(),
				courseType:$("#courseTypeEdit").val(),
				courseLevel:$("#courseLevelEdit").val(),
				courseIsOnline:$("#courseIsOnlineEdit").val(),
				coursePrice:$("#coursePriceEdit").val(),
				courseWays:$("#courseWaysEdit").val(),
				courseDescription:$("#courseDescriptionEdit").val(),
				courseImg:$("#courseImgEdit").val()
			}
		} else {
			editorCourseUnderEdit.sync();
			var courseDetails = editorCourseUnderEdit.getContent();
			formJson = {
				courseId:$("#courseIdEdit").val(),
				courseName:$("#courseNameEdit").val(),
				userName:$("#userNameEdit").val(),
				idCardNo:$("#idCardNoEdit").val(),
				courseType:$("#courseTypeEdit").val(),
				courseLevel:$("#courseLevelEdit").val(),
				courseIsOnline:$("#courseIsOnlineEdit").val(),
				coursePrice:$("#coursePriceEdit").val(),
				courseDescription:$("#courseDescriptionEdit").val(),
				courseImg:$("#courseImgEdit").val(),
				courseWays:'线下课程',
				//课程详情
				courseDetails:courseDetails,
				//课程时间
				startdate :$('#startDateEdit').combobox("getValue"),
				enddate : $('#endDateEdit').combobox("getValue"),
				//授课地点
				courseAddress :  $("#addressEdit").val()
			}


		}
		$.post("/qianyi/course/saveCourseInfo",formJson, function(data){
			if(data.code == "0"){
				$.messager.alert('提示','修改课程成功!');
				$('#courseEditWindow').window('close');
				$('#courseList').datagrid('reload');
			} else {
				$.messager.alert('错误',data.result);
			}
		});
	}
	
	function clearCourseFormEdit(){
		$('#courseEditForm').form('reset');
	}
</script>
