<link href="../js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../js/oss-h5-upload-js-direct/style.css"/>
<script type="text/javascript" charset="utf-8" src="../js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<#--阿里云web直传-->
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/base64.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/upload.js"></script>
<div style="padding:10px 10px 10px 10px" >
	<form id="itemAddForm" class="itemForm" method="post">
	    <table cellpadding="5">
			<tr>
				<td>课程名:</td>
				<td><input class="easyui-textbox" type="text" name="courseName" data-options="required:true" style="width: 280px;"></input></td>
				<td>讲师姓名:</td>
				<td>
					<input class="easyui-textbox" type="text" name="userName" data-options="required:true,validType:'length[1,30]'" />
				</td>
				<td>身份证:</td>
				<td>
					<input class="easyui-textbox" type="text" id="idNum" name="idNum" data-options="required:true,validType:'length[1,18]'" />
				</td>
			</tr>
	        <tr>
	            <td>课程分类:</td>
	            <td>
	            	<a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a>
	            	<input type="hidden" name="courseType" style="width: 280px;"></input>
	            </td>
				<td>课程级别:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectCourseLevel">选择类目</a>
					<input type="hidden" name="courseLevel" style="width: 280px;"></input>
				</td>
				<td>授课方式:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectTeachMethods">选择类目</a>
					<input type="hidden" name="courseIsOnline" style="width: 280px;"></input>
				</td>
	        </tr>
			<tr>
				<td>课程价格(￥):</td>
				<td><input class="easyui-numberbox" type="text" name="priceView" data-options="min:1,max:99999999,precision:2,required:true" />
					<input type="hidden" name="coursePrice"/>
				</td>
			</tr>
	        <tr>
	            <td>课程简介:</td>
	            <td><input class="easyui-textbox" name="courseDescription" data-options="multiline:true,validType:'length[0,150]'" style="height:100px;width: 400px;"></input></td>
	        </tr>
	        <tr>
	            <td>课程封面:</td>
	            <td>
					<div style="display: none">
						<input type="file" name="" id="file" value="" />
						<form name="theform" style="display: none">

							<input type="radio" name="myradio" value="local_name"/> 上传文件名字保持本地文件名字
							<input type="radio" name="myradio" value="random_name" checked/> 上传文件名字是随机文件名
							<br/>
						</form>
					</div>

					<#--<h4>您所选择的文件列表：</h4>-->
					<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>

					<br/>
					<div id="container">
						<a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
						<a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
					</div>
					<pre id="console"></pre>

					<p>&nbsp;</p>
					<input type="hidden" name="image" />
	            </td>
	        </tr>
	        <tr class="">
				<td>课程目录:</td>
				<td>
					<div>
						<ul id="contentCategory-course" class="easyui-tree">
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>课程文件:</td>
				<td>
					<div style="display: none">
						<input type="file" name="" id="file" value="" />
					</div>

					<#--<h4>您所选择的文件列表：</h4>-->
					<div id="ossfile-course">你的浏览器不支持flash,Silverlight或者HTML5！</div>

					<br/>
					<div id="container">
						<a id="selectfiles-course" href="javascript:void(0);" class='btn'>选择文件</a>
						<a id="postfiles-course" href="javascript:void(0);" class='btn'>开始上传</a>
					</div>
					<pre id="console"></pre>

					<p>&nbsp;</p>
					<input type="hidden" name="image"/>
				</td>
			</tr>
	    </table>
	    <input type="hidden" name="itemParams"/>
	</form>
	<div id="contentCategoryMenu-course" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandlerCourse">
		<div data-options="iconCls:'icon-add',name:'add'">添加</div>
		<div data-options="iconCls:'icon-edit',name:'rename'">重命名</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
	</div>

	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#contentCategory-course").tree({
			url : '/qianyi/catalog/list',
			animate: true,
			method : "GET",
			onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
				$('#contentCategoryMenu-course').menu('show',{
					left: e.pageX,
					top: e.pageY
				});
			},
			onAfterEdit : function(node){
				debugger
				var _tree = $(this);
				if(node.id == 0){
					// 新增节点
					$.post("/qianyi/catalog/create",{parentId:node.parentId,name:node.text},function(data){
						if(data.status == 200){
							_tree.tree("update",{
								target : node.target,
								id : data.data.id
							});
						}else{
							$.messager.alert('提示','创建'+node.text+' 分类失败!');
						}
					});
				}else{
					$.post("/qianyi/catalog/update",{id:node.id,name:node.text});
				}
			}
		});
	});
	function menuHandlerCourse(item){
		var tree = $("#contentCategory-course");
		var node = tree.tree("getSelected");
		var nodeDeep = easyui_tree_options.getLevel("#contentCategory-course",node);
		if(nodeDeep == 3 && item.name === "add"){
			$.messager.alert('提示','具体分类无法新建子分类！');
			return false;
		}

		if(nodeDeep != 3 && item.name === "delete"){
			$.messager.alert('提示','父节点无法删除！');
			return false;
		}

		if(item.name === "add"){
			console.log(node);
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
	$("#file").change(function(){
		console.log("change");
		client.multipartUpload("testImg", this.files[0]).then(function (result) {
			console.log(result);
		}).catch(function (err) {
			console.log(err);
		});
	});

	var itemAddEditor ;
	//页面初始化完毕后执行此方法
	$(function(){
		//创建富文本编辑器
		itemAddEditor = E3.createEditor("#itemAddForm [name=desc]");
		//初始化类目选择和图片上传器
		E3.init({fun:function(node){
			//根据商品的分类id取商品 的规格模板，生成规格信息。第四天内容。
			//E3.changeItemParam(node, "itemAddForm");
		}});
	});
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#itemAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//取商品价格，单位为“分”
		$("#itemAddForm [name=price]").val(eval($("#itemAddForm [name=priceView]").val()) * 100);
		//同步文本框中的商品描述
		//itemAddEditor.sync();
		//取商品的规格
		// var paramJson = [];
		// $("#itemAddForm .params li").each(function(i,e){
		// 	var trs = $(e).find("tr");
		// 	var group = trs.eq(0).text();
		// 	var ps = [];
		// 	for(var i = 1;i<trs.length;i++){
		// 		var tr = trs.eq(i);
		// 		ps.push({
		// 			"k" : $.trim(tr.find("td").eq(0).find("span").text()),
		// 			"v" : $.trim(tr.find("input").val())
		// 		});
		// 	}
		// 	paramJson.push({
		// 		"group" : group,
		// 		"params": ps
		// 	});
		// });
		//把json对象转换成字符串
		//paramJson = JSON.stringify(paramJson);
		//$("#itemAddForm [name=itemParams]").val(paramJson);

		//ajax的post方式提交表单
		//将表单序列号为key-value形式的字符串
		var formJson = $("#itemAddForm").serialize();
		formJson = decodeURIComponent(formJson,true);
		console.log(formJson)
		// $.post("/item/save",$("#itemAddForm").serialize(), function(data){
		// 	if(data.status == 200){
		// 		$.messager.alert('提示','新增商品成功!');
		// 	}
		// });
	}
	
	function clearForm(){
		$('#itemAddForm').form('reset');
		itemAddEditor.html('');
	}
</script>
