<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <link rel="stylesheet" href="${resPath}/css/message.css"/>
    <title>留言</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <div class="list-group list-group-flush message-list-group">
        <c:choose>
            <c:when test="${empty data.data}">
                <li class="list-group-item">
                    还没有人留言呢，快来留言吧~
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach var="item" items="${data.data}">
                    <li class="list-group-item">
                        <div class="d-flex w-100 justify-content-between">
                            <a href="javascript:;">${empty item.authorInfo.nickname ? item.author.username : item.authorInfo.nickname}</a>
                            <small><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
                        </div>
                        <p class="mb-1">${item.content}</p>
                    </li>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <%--<a href="#" class="list-group-item">--%>
        <%--<div class="d-flex w-100 justify-content-between">--%>
        <%--<h5 class="mb-1">List group item heading</h5>--%>
        <%--<small class="text-muted">3 days ago</small>--%>
        <%--</div>--%>
        <%--<p class="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>--%>
        <%--<small class="text-muted">Donec id elit non mi porta.</small>--%>
        <%--</a>--%>
    </div>
    <c:set var="paginationUrl" value="${ctx}/message/messages"/>
    <%@ include file="common/pagination.jsp" %>
    <div style="margin-top: 20px">
        <form action="${ctx}/message/" method="post">
            <div class="form-group">
                <textarea name="content" class="form-control" placeholder="说点什么吧..."></textarea>
            </div>
            <button type="submit" class="btn btn-primary" style="float: right" onclick="return verify()">留言</button>
        </form>
    </div>
    <p class="err-msg">${errMsg}</p>
</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
<script src="${resPath}/js/common.js"></script>
<script>
    $(function () {
        $('textarea[name="content"]').bind('input propertychange', function () {
            $('.err-msg').text('');
        });
        $('textarea[name="content"]').bind('input propertychange', function () {
            $('.err-msg').text('');
        });
    });

    function verify() {
        var content = $('textarea[name="content"]').val();
        if (isBlank(content)) {
            $('.err-msg').text('留言内容为空');
            return false;
        }
        return true;
    }
</script>
</html>
