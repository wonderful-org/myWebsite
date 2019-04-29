<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <title>写博客</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
    <div class="container">
        <form action="" method="post">
            <div class="form-group">
                <label for="title">文章标题</label>
                <input type="text" class="form-control" id="title" placeholder="想一个响亮的标题吧~">
            </div>
            <div class="form-control">
                <label for="source">文章来源</label>
                <input id="source" type="text" class="form-control" placeholder="非原创文章因标明来源哦~">
            </div>
            <!-- 文本编辑器 -->
            <div id="content" class="form-group">
                <div id="summernote"></div>
            </div>
            <!-- 文本编辑器结束 -->
            <div>
                <label>
                    <input type="checkbox" value="open" checked> 公开
                </label>
                <label class="label-right">
                    文章专辑
                </label>
            </div>
            <button type="submit" class="btn btn-default" style="float:right;" onclick="return submitForm()">提交</button>
        </form>
    </div>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
<script src="${resPath}/js/common.js"></script>
</html>
