<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title >${courseInfo.courseName }</title>
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
            text-align: left;
            font-size: 14px;
        }
        .body{
            max-width: 1200px;
            margin:0 auto ;
        }
        .lineSty{
            height: 1px;
            border: none;
            border-top: 1px solid #e4e2e2;
        }
    </style>
</head>
<body>
    <div class="body">
        <div class="container">
            <div class="row context-img">
                <img src="${courseInfo.courseImg}">
            </div>
            <div class="row title">
                <span >${courseInfo.courseName}</span>
            </div>
            <div class="row title">
                <div style="margin-top: 5px;">
                    <img src="/qianyi/images/rili.svg" style="width: 17px;">
                    <div style="margin-top: -20px;margin-left: 25px;font-size: 12px;">${courseInfo.courseDate}</div>
                </div>
                <hr class="lineSty">
            </div>
            <div class="row title">
                <div style="margin-top:5px;">
                    <img src="/qianyi/images/local2.svg" style="width: 17px;">
                    <div style="margin-top: -20px;margin-left: 25px;font-size: 12px;">${courseInfo.address}</div>
                </div>
                <hr class="lineSty">
            </div>
            <div class="row title">
                <div style="margin-top: 5px;">
                    <img src="/qianyi/images/baoming.svg" style="width: 17px;">
                    <div style="margin-top: -20px;margin-left: 25px;font-size: 12px;">已报名 ${courseInfo.orderNum}</div>
                </div>
                <hr class="lineSty">
            </div>
            <div class="row title">
                <div style="margin-top: 5px;">
                    <img src="/qianyi/images/i-money.svg" style="width: 17px;">
                    <div style="margin-top: -20px;margin-left: 25px;font-size: 12px;">${courseInfo.coursePrice}</div>
                </div>
                <hr class="lineSty">
            </div>

        </div>
        <div class="new-content context-img1">
            ${courseInfo.details}
        </div>
    </div>


</body>
</html>