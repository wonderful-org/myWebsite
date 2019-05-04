<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <title>博客</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <div class="blog-post">
        <h2 class="blog-post-title">${data.title}</h2>
        <p class="blog-post-meta"><a href="#">${data.authorInfo.nickname}</a> 发表于 <fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
        ${data.content}
        <c:if test="${not empty currentUser && currentUser.id == data.author.id}">
            <a href="${ctx}/note/delete?id=${data.id}" onclick="return confirm('确定删除该文章吗？')">删除</a>
            <a href="${ctx}/note/toUpdate?id=${data.id}" onclick="return confirm('去修改文章？')">修改</a>
        </c:if>
    </div>
    <p class="err-msg">${errMsg}</p>
</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
</html>
