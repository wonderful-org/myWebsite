<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <title>关于</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-2 col-lg-2"></div>
        <div class="col-md-8 col-lg-8">
            <h1 class="text-center">关于本站</h1>
            <h2>浏览器支持</h2>
            <p>ie9+、chrome、firefox...</p>
            <h2>网站介绍</h2>
            <p>个人练习网站，基于mvc三层架构</p>
            <p>前端：bootstrap框架、jsp后端渲染</p>
            <p>后端：servlet3.0、jdbc</p>
            <p>数据库：mysql</p>
            <p>github：<a href="https://github.com/wonderful-org/myWebsite" target="_blank">github.com/wonderful-org/myWebsite</a></p>
        </div>
        <div class="col-md-2 col-lg-2"></div>
    </div>
</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
</html>
