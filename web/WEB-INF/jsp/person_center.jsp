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
    <title>个人中心</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <c:if test="${empty errMsg}">
        <div class="row">
            <div class="col-md-3 col-lg-3">
                <ul class="list-group mb-3">
                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                        <div>
                            <h6 class="my-0">个人信息</h6>
                            <small class="text-muted">完善你的个人资料</small>
                        </div>
                    </li>
                    <%--<li class="list-group-item d-flex justify-content-between lh-condensed">--%>
                        <%--<div>--%>
                            <%--<h6 class="my-0">Second product</h6>--%>
                            <%--<small class="text-muted">Brief description</small>--%>
                        <%--</div>--%>
                        <%--<span class="text-muted">$8</span>--%>
                    <%--</li>--%>
                </ul>
            </div>
            <div class="col-md-9 col-lg-9">
                右侧
            </div>
        </div>
    </c:if>
    <p class="err-msg">${errMsg}</p>
</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
</html>
