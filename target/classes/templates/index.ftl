<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>乾易后台管理系统</title>
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.4.1/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../css/e3.css" />
<link rel="stylesheet" type="text/css" href="../css/default.css" />
<script type="text/javascript" src="../js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/common.js"></script>

<#--browser upload-->
<script type="text/javascript" src="../js/aliyun-oss-sdk/aliyun-oss-sdk.js"></script>
<#--引入element-ui-->
<!-- 引入样式 -->
<link rel="stylesheet" href="../plugins/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="../plugins/vue/vue.min.js"></script>
<script src="../plugins/element-ui/lib/index.js"></script>

<style type="text/css">
	.content {
		padding: 10px 10px 10px 10px;
	}
</style>
</head>
<body class="easyui-layout">
    <!-- 头部标题 -->
	<div data-options="region:'north',border:false" style="height:60px; padding:5px; background:#F3F3F3"> 
		<span class="northTitle">乾易后台管理系统</span>
	    <span class="loginInfo">登录用户：admin&nbsp;&nbsp;姓名：管理员&nbsp;&nbsp;角色：系统管理员</span>
	</div>
    <div data-options="region:'west',title:'菜单',split:true" style="width:180px;">
    	<ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
         	<li>
         		<span>用户管理</span>
         		<ul>
					<li data-options="attributes:{'url':'../pages/user-list'}">用户列表</li>
	         		<li data-options="attributes:{'url':'../pages/item-add'}">名师列表</li>
	         		<li data-options="attributes:{'url':'../pages/item-param-list'}">订单管理</li>
	         	</ul>
         	</li>
			<li>
				<span>订单管理</span>
				<ul>
					<li data-options="attributes:{'url':'../pages/user-list'}">订单列表</li>
				</ul>
			</li>
			<li>
				<span>课程管理</span>
				<ul>
					<li data-options="attributes:{'url':'../pages/course-list'}">课程列表</li>
					<li data-options="attributes:{'url':'../pages/course-add'}">课程上传</li>
				</ul>
			</li>
         	<li>
         		<span>APP运维</span>
         		<ul>
					<li data-options="attributes:{'url':'../pages/item-add'}">课程上传</li>
	         		<li data-options="attributes:{'url':'../pages/content-category'}">数据字典</li>
	         		<li data-options="attributes:{'url':'../pages/content'}">APP轮播</li>
	         	</ul>
         	</li>
         	<li>
         		<span>索引库管理</span>
         		<ul>
	         		<li data-options="attributes:{'url':'../pages/index-item'}">solr索引库维护</li>
	         	</ul>
         	</li>
         </ul>
    </div>
    <div data-options="region:'center',title:''">
    	<div id="tabs" class="easyui-tabs">
		    <div title="首页" style="padding:20px;">
		        	
		    </div>
		</div>
    </div>
    <!-- 页脚信息 -->
	<div data-options="region:'south',border:false" style="height:20px; background:#F3F3F3; padding:2px; vertical-align:middle;">
		<span id="sysVersion">系统版本：V1.0</span>
	    <span id="nowTime"></span>
	</div>
<script type="text/javascript">
$(function(){
	$('#menu').tree({
		onClick: function(node){
			if($('#menu').tree("isLeaf",node.target)){
				var tabs = $("#tabs");
				var tab = tabs.tabs("getTab",node.text);
				if(tab){
					tabs.tabs("select",node.text);
				}else{
					tabs.tabs('add',{
					    title:node.text,
					    href: node.attributes.url,
					    closable:true,
					    bodyCls:"content"
					});
				}
			}
		}
	});
});
setInterval("document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
</script>
</body>
</html>