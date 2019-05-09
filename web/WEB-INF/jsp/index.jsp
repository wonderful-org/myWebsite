<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <title>首页</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <c:choose>
        <c:when test="${empty currentUser}">
            <main role="main">
                <h1 class="cover-heading">加入我们.</h1>
                <p class="lead">本网站是供学习交流的个人网站，欢迎加入我们并提出宝贵意见.</p>
                <p class="lead">
                    <a href="${ctx}/user/toRegister" class="btn btn-lg btn-secondary">去注册吧~</a>
                </p>
            </main>
        </c:when>
        <c:otherwise>
            欢迎您，${empty currentUser.userInfo.nickname ? currentUser.username : currentUser.userInfo.nickname}，现在时间是<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日" />
        </c:otherwise>
    </c:choose>

</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
</html>
