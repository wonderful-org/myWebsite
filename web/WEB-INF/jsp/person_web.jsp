<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <title>个人主页</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <c:choose>
        <c:when test="${empty data}">
            <a href="${ctx}/personCenter/userInfo">您还没有完善您的个人信息，请先去填写~</a>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty data.realName}">
                <label for="realName">真实姓名</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="realName" value="${data.realName}">
                </div>
            </c:if>

            <c:if test="${not empty data.nickname}">
                <label for="nickname">昵称</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="nickname" value="${data.nickname}">
                </div>
            </c:if>

            <c:if test="${not empty data.gender}">
                <label for="gender">性别</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="gender" value="${data.gender == 0 ? '保密' : data.gender == 1 ? '男' : '女'}">
                </div>
            </c:if>

            <c:if test="${not empty data.birthday}">
                <label for="birthday">出生日期</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="birthday" value="${data.birthday}">
                </div>
            </c:if>

            <c:if test="${not empty data.phone}">
                <label for="phone">联系号码</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="phone" value="${data.phone}">
                </div>
            </c:if>

            <c:if test="${not empty data.personWebsite}">
                <label for="personWebsite">个人网站</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="personWebsite" value="${data.personWebsite}">
                </div>
            </c:if>

            <c:if test="${not empty data.email}">
                <label for="email">email</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="email" value="${data.email}">
                </div>
            </c:if>

            <c:if test="${not empty data.address}">
                <label for="address">地址</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="address" value="${data.address}">
                </div>
            </c:if>

            <c:if test="${not empty data.introduction}">
                <label for="introduction">个人简介</label>
                <div class="list-group">
                    <input type="text" class="form-control" id="introduction" value="${data.introduction}">
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
</html>
