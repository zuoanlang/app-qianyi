<#--阿里云web直传-->
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/base64.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/upload-infoEdit.js"></script>
<link rel="stylesheet" type="text/css" href="../js/oss-h5-upload-js-direct/style.css"/>
<div style="padding:10px 10px 10px 10px">
	<form id="infoEditForm" class="itemForm" method="post">
		<input type="hidden" id="infoId" name="infoId" value="">
		<input type="hidden" name="infoContent" id="infoContentEdit"/>
		<table cellpadding="5">
			<tr>
				<td>资讯分类:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectInfoType">选择类目</a>
					<input type="hidden" id="infoTypeEdit" name="infoType" style="width: 280px;" value=""/>
				</td>
			</tr>
			<tr>
				<td>资讯标题:</td>
				<td>
					<input class="easyui-textbox" type="text" id="infoTitleEdit" name="infoTitle" data-options="required:true" style="width: 280px;"/>
				</td>
			</tr>
			<tr>
				<td>资讯作者:</td>
				<td>
					<input class="easyui-textbox" type="text" id="infoWriterEdit" name="infoWriter" data-options="required:true" style="width: 280px;"/>
				</td>
			</tr>
			<tr>
				<td>资讯状态:</td>
				<td>
					<div>
						发布:<input data-options="required:true" type="radio" name="effectFlag" value="1" >
						草稿:<input data-options="required:true" type="radio" name="effectFlag" value="0" >
					</div>
				</td>
			</tr>
			<td>资讯配图:</td>
			<td>
				<img id="editImg" style="width: 300px;height: 168px" src="">
				<br>
				<span style="color: red">建议尺寸比例(16:9)</span>
				<div style="display: none">
					<input type="hidden" name="infoImgPath" id="infoImgPathEdit" value="" />
					<div name="theform" style="display: none">

						<input type="radio" name="myradio" value="local_name"/> 上传文件名字保持本地文件名字
						<input type="radio" name="myradio" value="random_name" checked/> 上传文件名字是随机文件名
						<br/>
					</div>
				</div>

				<#--<h4>您所选择的文件列表：</h4>-->
				<div id="ossfile-infoEdit">你的浏览器不支持flash,Silverlight或者HTML5！</div>

				<br/>
				<div id="container">
					<a id="selectfiles-infoEdit" href="javascript:void(0);" class='btn'>选择文件</a>
					<a id="postfiles-infoEdit" href="javascript:void(0);" class='btn'>开始上传</a>
				</div>
				<pre id="console"></pre>

				<p>&nbsp;</p>
			</td>
			<tr>
				<td>资讯内容:</td>
				<td>
					<!-- 加载编辑器的容器 -->
					<script id="editorEdit" name="content" type="text/plain" style="width: 1024px;height:500px;"/>
				</td>
			</tr>
		</table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitEditForm()">提交</a>
	</div>
</div>
<script type="text/javascript">
	var editorEdit;
	$(function () {
		editorEdit = UE.getEditor('editorEdit', {
			imageFieldName: "upfile", //* 提交的图片表单名称 *!/
			allowDivTransToP: false
		});
	});

	//提交表单
	function submitEditForm() {
		//有效性验证
		if (!$('#infoEditForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		//验证infoImgPath
		if ($('#infoType').val() == "") {
			$.messager.alert('提示', '请选择资讯分类!');
			return;
		}
		//验证infoImgPath
		if ($('#infoImgPathEdit').val() == "") {
			$.messager.alert('提示', '请上传资讯配图!');
			return;
		}
		//资讯内容赋值
		editorEdit.sync();
		$("#infoContentEdit").val(editorEdit.getContent());
		var formJson = {
			infoId:$("#infoId").val(),
			infoType : $("#infoTypeEdit").val() ,
			infoTitle : $("#infoTitleEdit").val(),
			infoImgPath:$("#infoImgPathEdit").val(),
			infoWriter:$("#infoWriterEdit").val(),
			effectFlag:$("input[name='effectFlag']:checked").val(),
			infoContent:$("#infoContentEdit").val()
		}
		//取商品的规格
		$.post("/qianyi/infoMan/editNews", formJson, function (data) {
			if(data.code == "0"){
				$.messager.alert('提示','修改资讯成功!');
				$('#infoEditWindow').window('close');
				$('#infoList').datagrid('reload');
			} else {
				$.messager.alert('错误',data.result);
			}
		});

		function clearForm() {
			$('#infoEditForm').form('reset');
			$('#ossfile-infoEdit').html('');
			editor.setContent('');
		}
	}
</script>
