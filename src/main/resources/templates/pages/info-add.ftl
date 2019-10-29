<#--阿里云web直传-->
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/base64.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="../js/oss-h5-upload-js-direct/upload-info.js"></script>
<link rel="stylesheet" type="text/css" href="../js/oss-h5-upload-js-direct/style.css"/>
<div style="padding:10px 10px 10px 10px">
    <form id="newsAddForm" class="itemForm" method="post">
        <table cellpadding="5">
            <tr>
                <td>资讯分类:</td>
                <td>
                    <a href="javascript:void(0)" class="easyui-linkbutton selectInfoType">选择类目</a>
                    <input type="hidden" name="infoType" id="infoType" style="width: 280px;" value=""/>
                </td>
            </tr>
            <tr>
                <td>资讯标题:</td>
                <td>
                    <input class="easyui-textbox" type="text" name="infoTitle" id="infoTitle" data-options="required:true" style="width: 280px;"/>
                </td>
            </tr>
            <tr>
                <td>资讯作者:</td>
                <td>
                    <input class="easyui-textbox" type="text" name="infoWriter" id="infoWriter" data-options="required:true" style="width: 280px;"/>
                </td>
            </tr>
            <td>资讯配图:</td>
            <td>
                <img id="addImg" style="width: 300px;height:168px;" src="">
                <br>
                <span style="color: red">建议尺寸比例(16:9)</span>
                <div style="display: none">
                    <input type="hidden" name="infoImgPath" id="infoImgPath" value="" />
                    <div name="theform" style="display: none">

                        <input type="radio" name="myradio" value="local_name"/> 上传文件名字保持本地文件名字
                        <input type="radio" name="myradio" value="random_name" checked/> 上传文件名字是随机文件名
                        <br/>
                    </div>
                </div>

                <#--<h4>您所选择的文件列表：</h4>-->
                <div id="ossfile-info">你的浏览器不支持flash,Silverlight或者HTML5！</div>

                <br/>
                <div id="container">
                    <a id="selectfiles-info" href="javascript:void(0);" class='btn'>选择文件</a>
                    <a id="postfiles-info" href="javascript:void(0);" class='btn'>开始上传</a>
                </div>
                <pre id="console"></pre>

                <p>&nbsp;</p>
            </td>
            <tr>
                <td>资讯内容:</td>
                <td>
                    <!-- 加载编辑器的容器 -->
                    <script id="editor" name="content" type="text/plain" style="width: 1024px;height:500px;">
                        这里写你的初始化内容
                    </script>
                </td>
            </tr>
        </table>
        <input type="hidden" name="infoContent" id="infoContent"/>
    </form>
    <div style="padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitNewsForm()">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
    </div>
</div>

<script type="text/javascript">
    var editor;
    $(function () {
        editor = UE.getEditor('editor', {
            imageFieldName: "upfile", //* 提交的图片表单名称 *!/
            allowDivTransToP: false
        });

        /* 赋值脚本 */
        editor.ready(function () {//监听编辑器实例化完成的事件
            editor.setContent("快来编辑您的最新资讯吧~~~");
        })

        //创建富文本编辑器
        newsAddEditor = E3.createEditor("#newsAddForm [name=desc]");
        //初始化类目选择和图片上传器
        E3.init({fun:function(node){
            //根据商品的分类id取商品 的规格模板，生成规格信息。第四天内容。
            //E3.changeItemParam(node, "itemAddForm");
        }});
    });

    //提交表单
    function submitNewsForm() {
        //有效性验证
        if (!$('#newsAddForm').form('validate')) {
            $.messager.alert('提示', '表单还未填写完成!');
            return;
        }
        //验证infoImgPath
        if ($('#infoType').val() == "") {
            $.messager.alert('提示', '请选择资讯分类!');
            return;
        }
        //验证infoImgPath
        if ($('#infoImgPath').val() == "") {
            $.messager.alert('提示', '请上传资讯配图!');
            return;
        }
        //资讯内容赋值
        editor.sync();
        $("#infoContent").val(editor.getContent());
        var formJson = {
            infoType : $("#infoType").val() ,
            infoTitle : $("#infoTitle").val(),
            infoImgPath:$("#infoImgPath").val(),
            infoWriter:$("#infoWriter").val(),
            infoContent:$("#infoContent").val()
        }
        //取商品的规格
        $.post("/qianyi/infoMan/addNews", formJson, function (data) {
            if(data.code == "0"){
                $.messager.alert('提示','新增资讯成功!');
                $('#infoAddWindow').window('close');
                $('#infoList').datagrid('reload');
                clearForm();
            } else {
                $.messager.alert('错误',data.result);
            }
        });
    }

    function clearForm() {
        $('#newsAddForm').form('reset');
        $('#ossfile-info').html('');
        $('#ossfile-info').html('');
        $("#addImg").attr("src",'');
        editor.setContent('');
    }
</script>
