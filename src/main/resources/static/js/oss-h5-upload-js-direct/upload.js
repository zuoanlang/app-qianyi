accessid = 'LTAIh7QUIIjheNnI';
accesskey = 'o6YUhS76FNkr8k6BXYjkeqwu0TXKqA';
host = 'http://qianyi-app.oss-cn-shenzhen.aliyuncs.com';
targeturl = "https://qianyi-app.oss-cn-shenzhen.aliyuncs.com"

g_dirname = ''
g_object_name = ''
g_object_name_type = ''
now = timestamp = Date.parse(new Date()) / 1000;

var policyText = {
    "expiration": "2020-01-01T12:00:00.000Z", //设置该Policy的失效时间，超过这个失效时间之后，就没有办法通过这个policy上传文件了
    "conditions": [
        ["content-length-range", 0, 1048576000] // 设置上传文件的大小限制
    ]
};

var policyBase64 = Base64.encode(JSON.stringify(policyText))
message = policyBase64
var bytes = Crypto.HMAC(Crypto.SHA1, message, accesskey, {asBytes: true});
var signature = Crypto.util.bytesToBase64(bytes);

function check_object_radio() {
    var tt = document.getElementsByName('myradio');
    for (var i = 0; i < tt.length; i++) {
        if (tt[i].checked) {
            g_object_name_type = tt[i].value;
            break;
        }
    }
}

function get_image_dirname() {
    g_dirname = 'qianyi/course/images/';
}

function get_video_dirname() {
    g_dirname = 'qianyi/course/videos/';
}

function random_string(len) {
    len = len || 32;
    var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function calculate_object_name(filename) {
    if (g_object_name_type == 'local_name') {
        g_object_name += "${filename}"
    } else if (g_object_name_type == 'random_name') {
        suffix = get_suffix(filename)
        g_object_name = g_dirname + random_string(10) + suffix
    }
    return ''
}

function get_uploaded_object_name(filename) {
    if (g_object_name_type == 'local_name') {
        tmp_name = g_object_name
        tmp_name = tmp_name.replace("${filename}", filename);
        return tmp_name
    } else if (g_object_name_type == 'random_name') {
        return g_object_name
    }
}

function set_upload_param(up, filename, ret) {
    g_object_name = g_dirname;
    if (filename != '') {
        suffix = get_suffix(filename)
        calculate_object_name(filename)
    }
    new_multipart_params = {
        'key': g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid,
        'success_action_status': '200', //让服务端返回200,不然，默认会返回204
        'signature': signature,
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

var multi_selection_fm = false;
var uploader = new plupload.Uploader({
    runtimes: 'html5,flash,silverlight,html4',
    browse_button: 'selectfiles',
    multi_selection: multi_selection_fm,
    container: document.getElementById('container'),
    flash_swf_url: 'lib/plupload-2.1.2/js/Moxie.swf',
    silverlight_xap_url: 'lib/plupload-2.1.2/js/Moxie.xap',
    url: 'http://oss.aliyuncs.com',
    filters: {
        mime_types: [ //只允许上传图片文件
            {title: "图片文件", extensions: "jpg,gif,png"}
        ]
    },

    init: {
        PostInit: function () {
            document.getElementById('ossfile').innerHTML = '';
            document.getElementById('postfiles').onclick = function () {
                var idNum = $("#idCardNo").val();
                var userName = $("#userName").val();
                //请求数据库核对名师身份
                $.ajax({
                    url: "/qianyi/manager/checkOutMaster",
                    data: {
                        userName: userName,
                        idNum: idNum
                    },
                    async: false,
                    success: function (data) {
                        if (data.code == "1") {
                            $.messager.alert('错误', '请确认名师是否存在或者师名姓名、身份证号码是否匹配!');
                            return false;
                        } else if(data.code == "0") {
                            set_upload_param(uploader, '', false);
                        } else {
                            $.messager.alert('错误', '当前信息已过期，请刷新重试');
                        }
                    }
                })

                return false;
            };
        },

        FilesAdded: function (up, files) {
            //单文件
            if (!multi_selection_fm) {
                if (uploader.files.length > 1) {
                    uploader.files.splice(0, 1);
                }

                plupload.each(files, function (file) {
                    document.getElementById('ossfile').innerHTML = '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                        + '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                        + '</div>';
                });
            } else {
                plupload.each(files, function (file) {
                    document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                        + '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                        + '</div>';
                });
            }
        },

        BeforeUpload: function (up, file) {
            check_object_radio();
            get_image_dirname();
            set_upload_param(up, file.name, true);
        },

        UploadProgress: function (up, file) {
            var d = document.getElementById(file.id);
            d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
            var progBar = prog.getElementsByTagName('div')[0]
            progBar.style.width = 2 * file.percent + 'px';
            progBar.setAttribute('aria-valuenow', file.percent);
        },

        FileUploaded: function (up, file, info) {
            if (info.status == 200) {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传成功';
                $("#courseImg").val(targeturl + "/" + get_uploaded_object_name(file.name))
                $("#courseAddImg").attr("src", targeturl + "/" + get_uploaded_object_name(file.name));
            } else {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            }
        },

        Error: function (up, err) {
            document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
        }
    }
});
//上传课程视频文件
var courseUploader = new plupload.Uploader({
    runtimes: 'html5,flash,silverlight,html4',
    browse_button: 'selectfiles-course',
    multi_selection: multi_selection_fm,
    container: document.getElementById('container'),
    flash_swf_url: 'lib/plupload-2.1.2/js/Moxie.swf',
    silverlight_xap_url: 'lib/plupload-2.1.2/js/Moxie.xap',
    url: 'http://oss.aliyuncs.com',
    filters: {
        max_file_size: '10gb', //最大上传文件大小（格式100b, 10kb, 10mb, 1gb）
        mime_types: [ //只允许上传视频文件
            {title: "视频文件", extensions: "AVI,mov,rmvb,rm,FLV,mp4,3GP,mov,m4v,mpg,MOV,DAT"}
        ]
    },

    init: {
        PostInit: function () {
            document.getElementById('ossfile-course').innerHTML = '';
            document.getElementById('postfiles-course').onclick = function () {
                var idNum = $("#idCardNo").val();
                var userName = $("#userName").val();
                //请求数据库核对名师身份
                $.ajax({
                    url: "/qianyi/manager/checkOutMaster",
                    data: {
                        userName: userName,
                        idNum: idNum
                    },
                    async: false,
                    success: function (data) {
                        if (data.code == "1") {
                            $.messager.alert('错误', '讲师名或身份证号码错误!');
                            return false;
                        } else {
                            set_upload_param(courseUploader, '', false);
                        }
                    }
                })
                return false;
            };
        },

        FilesAdded: function (up, files) {
            //目录文件上传-多文件（用课程名来匹配）
            if (multi_selection_fm) {
                if (courseUploader.files.length > 1) {
                    courseUploader.files.splice(0, 1);
                }

                plupload.each(files, function (file) {
                    document.getElementById('ossfile-course').innerHTML =
                        '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                        + '<div class="progress">'
                        + '<div class="progress-bar" style="width: 0%"></div></div>'
                        + '</div>';
                });
            } else {
                //添加文件之前
                console.log(files);
                console.log(courseUploader.files);
                var length = courseUploader.files.length;
                //1.判断节点选中
                var tree = $("#contentCategory-course");
                var node = tree.tree("getSelected");
                var videoTime = $('#videoTime').timespinner("getValue");
                //判断时长是否已填
                if (videoTime <= '00:00:00') {
                    $.messager.alert('提示', '请先填写章节视频时长');
                    return false;
                }

                if (node != null) {
                    //2.判断节点有无子节点
                    if (node.state == 'open' && node.children == undefined) {
                        //子节点
                        //取文件名
                        var fileName = files[0].name;
                        if (node.text != fileName.substring(0, fileName.lastIndexOf("."))) {
                            $.messager.alert('提示', '请保持文件名和目录名称一致！');
                            courseUploader.files.splice(length - 1, 1);
                        }
                    } else {
                        $.messager.alert('提示', '请选中底层目录节点,再上传文件！');
                        courseUploader.files.splice(length - 1, 1);
                    }

                } else {
                    $.messager.alert('提示', '请先选中目录节点,再上传文件！');
                    courseUploader.files.splice(length - 1, 1);
                }

                //2.相同文件名的处理
                for (var i = 0; i < courseUploader.files.length; i++) {
                    if (courseUploader.files[i].name == files[0].name) {
                        //上传了同名新文件
                        if (courseUploader.files[i].id != files[0].id) {
                            //找到相同名称的文件
                            //1.删除原先的进度条div
                            var id = courseUploader.files[i].id;
                            $("#" + id).remove();
                            //2.courseUploader中删除旧文件
                            courseUploader.files.splice(i, 1);
                            //3.生成新的进度条div
                            plupload.each(files, function (file) {
                                document.getElementById('ossfile-course').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                                    + '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                                    + '</div>';
                            });
                        } else {
                            //3.生成新的进度条div
                            plupload.each(files, function (file) {
                                document.getElementById('ossfile-course').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                                    + '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                                    + '</div>';
                            });
                        }
                    }
                }

            }
        },

        BeforeUpload: function (up, file) {
            check_object_radio();
            get_video_dirname();
            set_upload_param(up, file.name, true);
        },

        UploadProgress: function (up, file) {
            var d = document.getElementById(file.id);
            d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
            var progBar = prog.getElementsByTagName('div')[0]
            progBar.style.width = 2 * file.percent + 'px';
            progBar.setAttribute('aria-valuenow', file.percent);
        },

        FileUploaded: function (up, file, info) {
            if (info.status == 200) {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传成功';
                //1.隐藏域赋值
                $("#videoPath").val(targeturl + "/" + get_uploaded_object_name(file.name))
                var videoPath = targeturl + "/" + get_uploaded_object_name(file.name);
                //2.在目录表中插入videoPath,ajax异步
                //2.1.取得选中的节点
                var tree = $("#contentCategory-course");
                var node = tree.tree("getSelected");
                var videoTime = $('#videoTime').timespinner("getValue");
                $.post('/qianyi/catalog/uploadVideoName', {
                    id: node.id,
                    videoName: file.name,
                    videoPath: videoPath,
                    videoTime: videoTime
                }, function (data) {
                    if (data.code == "0") {
                        //1.取得数据,回显课程文件名称
                        var data = {
                            videoName: file.name
                        }
                        $("#courseForm").form("load", data);
                    } else {
                        $.messager.alert('提示', '上传失败！');
                    }
                });
            } else {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            }
        },

        Error: function (up, err) {
            document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
        }
    }
});

uploader.init();
courseUploader.init();
