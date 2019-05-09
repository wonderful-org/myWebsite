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
    <link rel="stylesheet" href="${resPath}/css/notes.css"/>
    <title>博客</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
    <div class="container">
        <div class="jumbotron p-3 p-md-5 text-white rounded bg-dark">
            <div class="col-md-6 px-0">
                <h1 class="display-4 font-italic">文章展示</h1>
                <p class="lead my-3">最新文章都会在这里展示，点击下面的链接去写写看吧~.</p>
                <p class="lead mb-0"><a href="${ctx}/note/toAdd" class="text-white font-weight-bold">去写文章&nbsp;&raquo;</a></p>
            </div>
        </div>
    </div>
    <main role="main" class="container">
        <div class="row">
            <div class="col-md-8 blog-main">
                <h3 class="pb-3 mb-4 font-italic border-bottom">
                    ${data.dataType == 0 ? "最新文章" : data.betweenDate.concat("归档文章")}
                </h3>
                <c:choose>
                    <c:when test="${empty data.page.data}">
                        <div class="blog-post">
                            还没有人写文章呢，快来写篇吧~
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${data.page.data}">
                            <div class="blog-post">
                                <h2 class="blog-post-title"><a href="${ctx}/note/detail?id=${item.id}">${item.title}</a></h2>
                                <p class="blog-post-meta"><a href="#">${empty item.authorInfo.nickname ? item.author.username : item.authorInfo.nickname}</a> 发表于 <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                                <c:choose>
                                    <c:when test="${fn:length(item.contentText) > 200}">
                                        ${fn:substring(item.contentText, 0, 200)}......
                                    </c:when>
                                    <c:otherwise>
                                        ${item.contentText}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <c:set var="paginationUrl" value="${ctx}/note/notes"/>
                <c:set var="paginationData" value="${data.page}"/>
                <%@ include file="common/pagination.jsp" %>
            </div><!-- /.blog-main -->

            <aside class="col-md-4 blog-sidebar">
                <div class="p-3 mb-3 bg-light rounded">
                    <h4 class="font-italic">About</h4>
                    <p class="mb-0">${data.dataType == 0 ? "这里展示了最新的文章" : "这里展示了".concat(data.betweenDate).concat("的所有文章")}.</p>
                </div>

                <c:if test="${not empty data.archives}">
                    <div class="p-3">
                        <h4 class="font-italic">归档</h4>
                        <ol class="list-unstyled mb-0">
                            <c:forEach var="entry" items="${data.archives}">
                                <li><a href="${ctx}/note/notes?betweenDate=${entry.key}">${entry.key}(${entry.value})</a></li>
                            </c:forEach>
                        </ol>
                    </div>
                </c:if>

                <%--<div class="p-3">--%>
                    <%--<h4 class="font-italic">Elsewhere</h4>--%>
                    <%--<ol class="list-unstyled">--%>
                        <%--<li><a href="#">GitHub</a></li>--%>
                        <%--<li><a href="#">Twitter</a></li>--%>
                        <%--<li><a href="#">Facebook</a></li>--%>
                    <%--</ol>--%>
                <%--</div>--%>
            </aside><!-- /.blog-sidebar -->
        </div><!-- /.row -->
    </main><!-- /.container -->
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
</html>
