<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title >${infoDetails.infoTitle}</title>
    <script src="../plugins/bootstrap-3.3.7/css/bootstrap.css"></script>
    <style>
        .context-img img{
            width: 100%;
        }
        .context-img1 img{
            width: auto;
            height: auto;
            max-width: 100%;
            max-height: 100%;
        }
        .title{
            text-align: center;
            font-size: larger;
            font-weight: bold;
        }
        .second{
            text-align: center;
            margin-top: 3px;
            font-size: 14px;
        }
        .body{
            max-width: 1200px;
            margin:0 auto ;
        }
        .round_icon{
            width: 34px;
            height: 34px;
            display: flex;
            border-radius: 50%;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }
        .comment-div{
            background-color: #F8F8F8;
            margin-top: 4px;
            padding-bottom: 4px;
        }
        .nameTime{
            float: left;
            margin-left: 50px;
            margin-top: -31px;
        }
        .timeDiv{
            margin-top: -28px;
            float: right;
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <div class="body">
        <div class="container">
            <div class="row context-img">
                <img src="${infoDetails.infoImgPath}">
            </div>
            <div class="row title">
                <span >${infoDetails.infoTitle}</span>
            </div>
            <div class="row second">
                <span>作者:${infoDetails.infoWriter}</span>
                <span>${infoDetails.publishedTime}</span>
            </div>
        </div>
        <div class="new-content context-img1">
            ${infoDetails.infoContent}
        </div>
        <div class="new-comment">
            <span>精选评论</span>
            <#list infoComments as comment>
                <div class="comment-div">
                    <div>
                        <img src="${comment.headImg}" class="round_icon">
                        <div class="nameTime">
                            <span style="font-weight: bold">${comment.userName}</span>
                        </div>
                        <div class="timeDiv">
                            <span style="float: right;font-size: 12px">${comment.commentDateTime}</span>
                        </div>

                    </div>
                    <div style="margin-left: 50px">
                        <span>${comment.commentContent}</span>
                    </div>
                </div>
            </#list>
        </div>

    </div>


</body>
</html>