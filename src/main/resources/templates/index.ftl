<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
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
<!-- 富文本 -->
<script type="text/javascript" charset="utf-8" src="../UEditor/js/editor_api.js"></script>
<script type="text/javascript" charset="utf-8" src="../UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../UEditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../UEditor/lang/zh-cn/zh-cn.js"></script>
<#--browser upload-->
<script type="text/javascript" src="../js/aliyun-oss-sdk/aliyun-oss-sdk.js"></script>
<#--kindeditor-->
<link href="../js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="../js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/kindeditor-4.1.10/lang/zh_CN.js"></script>

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
	    <span class="loginInfo">登录用户：${username}&nbsp;&nbsp;角色：系统管理员</span>
	</div>
    <div data-options="region:'west',title:'菜单',split:true" style="width:180px;">
    	<ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
         	<li>
         		<span>用户管理</span>
         		<ul>
					<li data-options="attributes:{'url':'../pages/user-admin'}">管理员列表</li>
					<li data-options="attributes:{'url':'../pages/user-teacherList'}">名师列表</li>
					<li data-options="attributes:{'url':'../pages/user-list'}">用户列表</li>
	         		<li data-options="attributes:{'url':'../pages/user-feedback'}">用户反馈</li>
	         	</ul>
         	</li>
			<li>
				<span>订单管理</span>
				<ul>
					<li data-options="attributes:{'url':'../pages/order-list'}">订单列表</li>
				</ul>
			</li>
			<li>
				<span>审批管理</span>
				<ul>
					<li data-options="attributes:{'url':'../pages/approve-refund'}">退款审批</li>
					<li data-options="attributes:{'url':'../pages/approve-settled'}">入驻审批</li>
					<li data-options="attributes:{'url':'../pages/approve-comment'}">评论管理</li>
				</ul>
			</li>
			<li>
				<span>课程管理</span>
				<ul>
					<li data-options="attributes:{'url':'../pages/course-list'}">课程列表</li>
				</ul>
			</li>
			<li>
				<span>资讯管理</span>
				<ul>
					<li data-options="attributes:{'url':'../pages/info-list'}">资讯列表</li>
				</ul>
			</li>
         	<li>
         		<span>APP运维</span>
         		<ul>
	         		<li data-options="attributes:{'url':'../pages/content-category'}">数据字典</li>
	         		<li data-options="attributes:{'url':'../pages/course-carousel'}">轮播管理</li>
	         	</ul>
         	</li>
         </ul>
    </div>
    <div data-options="region:'center',title:''">
    	<div id="tabs" class="easyui-tabs">
		    <div id="mainPage" title="首页" style="padding:20px;">
				<div id="rz" class="easyui-panel" title="名师入驻申请"
					 style="width:500px;height:150px;padding:10px;background:#fafafa;"
					 data-options="iconCls:'icon-save',closable:false,collapsible:true,minimizable:false,maximizable:true">
					<p id="rz_d"></p>
				</div>
				<div id="tk" class="easyui-panel" title="用户退款申请"
					 style="width:500px;height:150px;padding:10px;background:#fafafa;"
					 data-options="iconCls:'icon-save',closable:false,collapsible:true,minimizable:false,maximizable:true">
					<p id="tk_d"></p>
				</div>
				<div id="pl" class="easyui-panel" title="用户评论审核"
					 style="width:500px;height:150px;padding:10px;background:#fafafa;"
					 data-options="iconCls:'icon-save',closable:false,collapsible:true,minimizable:false,maximizable:true">
					<p id="pl_d"></p>
				</div>
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
	$.post("/qianyi/manager/getApprovalCount", function(data){
		if(data.code == "0"){
			$("#rz_d").text("待审批："+ data.rz +"条")
			$("#tk_d").text("待审批："+ data.tk +"条")
			$("#pl_d").text("待审批："+ data.pl +"条")
		}
	});
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
	$('#tabs').tabs({
		border:false,
		onSelect:function(title){
			if (title == "首页"){
				$.post("/qianyi/manager/getApprovalCount", function(data){
					if(data.code == "0"){
						$("#rz_d").text("待审批："+ data.rz +"条")
						$("#tk_d").text("待审批："+ data.tk +"条")
						$("#pl_d").text("待审批："+ data.pl +"条")
					}
				});
			}
		}

	});

});

setInterval("document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
</script>
</body>
</html>