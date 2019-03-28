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
				<td><input class="easyui-textbox" type="text" name="title" data-options="required:true" style="width: 280px;"></input></td>
				<td>讲师姓名:</td>
				<td>
					<input class="easyui-textbox" type="text" name="barcode" data-options="required:true,validType:'length[1,30]'" />
				</td>
				<td>省份证:</td>
				<td>
					<input class="easyui-textbox" type="text" name="barcode" data-options="required:true,validType:'length[1,30]'" />
				</td>
			</tr>
			<#--<tr>
				<td>讲师姓名:</td>
				<td>
					<input class="easyui-textbox" type="text" name="barcode" data-options="required:true,validType:'length[1,30]'" />
				</td>
			</tr>-->
			<#--<tr>
				<td>省份证:</td>
				<td>
					<input class="easyui-textbox" type="text" name="barcode" data-options="required:true,validType:'length[1,30]'" />
				</td>
			</tr>-->
	        <tr>
	            <td>课程分类:</td>
	            <td>
	            	<a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a>
	            	<input type="hidden" name="cid" style="width: 280px;"></input>
	            </td>
				<td>课程级别:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectCourseLevel">选择类目</a>
					<input type="hidden" name="cid" style="width: 280px;"></input>
				</td>
				<td>授课方式:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectTeachMethods">选择类目</a>
					<input type="hidden" name="cid" style="width: 280px;"></input>
				</td>
	        </tr>
			<#--<tr>
				<td>课程级别:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectCourseLevel">选择类目</a>
					<input type="hidden" name="cid" style="width: 280px;"></input>
				</td>

			</tr>
			<tr>
				<td>授课方式:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectTeachMethods">选择类目</a>
					<input type="hidden" name="cid" style="width: 280px;"></input>
				</td>
			</tr>-->
			<tr>
				<td>课程价格(￥):</td>
				<td><input class="easyui-numberbox" type="text" name="priceView" data-options="min:1,max:99999999,precision:2,required:true" />
					<input type="hidden" name="price"/>
				</td>
			</tr>
	        <tr>
	            <td>课程简介:</td>
	            <td><input class="easyui-textbox" name="sellPoint" data-options="multiline:true,validType:'length[0,150]'" style="height:100px;width: 400px;"></input></td>
	        </tr>
	        <tr>
	            <td>课程封面:</td>
	            <td>
					<div style="display: none">
						<input type="file" name="" id="file" value="" />
						<form name="theform" style="display: none">

							<input type="radio" name="myradio" value="local_name" checked=true/> 上传文件名字保持本地文件名字
							<input type="radio" name="myradio" value="random_name" checked/> 上传文件名字是随机文件名
							<br/>
							<input type="hidden" id='dirname' placeholder="如果不填，默认是上传到根目录" value="qianyi/images/course" size=50>
						</form>
					</div>

					<h4>您所选择的文件列表：</h4>
					<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>

					<br/>
					<div id="container">
						<a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
						<a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
					</div>
					<pre id="console"></pre>

					<p>&nbsp;</p>
					<input type="hidden" name="image"/>
	            </td>
	        </tr>
			<tr>
				<td>课程目录:</td>
				<td colspan="6" id="courseAddApp">
					<el-table
							border
							:data="tableData"
							stripe
							style="width: 100%">
						<el-table-column
								prop="date"
								label="日期"
								width="180">
						</el-table-column>
						<el-table-column
								prop="date"
								label="日期"
								width="180">
						</el-table-column>
						<el-table-column
								prop="name"
								label="姓名"
								width="180">
						</el-table-column>
					</el-table>
				</td>
			</tr>
	        <#--<tr>
	            <td>商品描述:</td>
	            <td colspan="5">
	                <textarea style="width:800px;height:300px;visibility:hidden;" name="desc"></textarea>
	            </td>
	        </tr>-->
	        <#--<tr class="params hide">
	        	<td>商品规格:</td>
	        	<td>
	        		
	        	</td>
	        </tr>-->
	    </table>
	    <input type="hidden" name="itemParams"/>
	</form>


	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	var client = new OSS.Wrapper({
		region: 'oss-cn-shenzhen',
		accessKeyId: 'LTAIh7QUIIjheNnI',
		accessKeySecret: 'o6YUhS76FNkr8k6BXYjkeqwu0TXKqA',
		bucket: 'qianyi-app'
	});
	$("#file").change(function(){
		console.log("change");
		client.multipartUpload("testImg", this.files[0]).then(function (result) {
			console.log(result);
		}).catch(function (err) {
			console.log(err);
		});
	});
	var courseAddApp = new Vue({
		el: '#courseAddApp',
		data: function() {
			return {
				visible: false,
				tableData: [{
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1517 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1519 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1516 弄'
				}]
			}
		}
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
		itemAddEditor.sync();
		//取商品的规格
		/*
		var paramJson = [];
		$("#itemAddForm .params li").each(function(i,e){
			var trs = $(e).find("tr");
			var group = trs.eq(0).text();
			var ps = [];
			for(var i = 1;i<trs.length;i++){
				var tr = trs.eq(i);
				ps.push({
					"k" : $.trim(tr.find("td").eq(0).find("span").text()),
					"v" : $.trim(tr.find("input").val())
				});
			}
			paramJson.push({
				"group" : group,
				"params": ps
			});
		});
		//把json对象转换成字符串
		paramJson = JSON.stringify(paramJson);
		$("#itemAddForm [name=itemParams]").val(paramJson);
		*/
		//ajax的post方式提交表单
		//$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串
		$.post("/item/save",$("#itemAddForm").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增商品成功!');
			}
		});
	}
	
	function clearForm(){
		$('#itemAddForm').form('reset');
		itemAddEditor.html('');
	}
</script>
