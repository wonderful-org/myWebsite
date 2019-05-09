<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <title>个人博客</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container">
    <c:if test="${empty errMsg}">
        <div class="row">
            <%@ include file="left.jsp" %>
            <div class="col-md-9 col-lg-9">
                <h4 class="mb-3" style="display: inline-block">个人博客</h4><a href="${ctx}/note/toAdd" style="float: right">去写博客</a>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item" aria-current="page"><a href="${ctx}/personCenter/blog">个人博客</a></li>
                            <c:if test="${not empty data.folderBreadcrumb && data.folderId != 0}">
                                <c:forEach var="item" items="${data.folderBreadcrumb}">
                                    <li class="breadcrumb-item ${item.id == data.folderId ? active : ''}" aria-current="page"><a href="${ctx}/personCenter/blog?folderId=${item.id}">${item.folderName}</a></li>
                                </c:forEach>
                            </c:if>
                        </ol>
                    </nav>
                <hr class="mb-4">
                <c:choose>
                    <c:when test="${empty data.folders && empty data.notes}">
                        <div class="blog-post">
                            还没写过文章呢，快来写篇吧~
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${not empty data.folders}">
                            <c:forEach var="item" items="${data.folders}">
                                <div class="blog-post">
                                    <a href="${ctx}/personCenter/blog?folderId=${item.id}">${item.folderName}</a>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${not empty data.notes}">
                            <c:forEach var="item" items="${data.notes}">
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
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <hr class="mb-4">
                <form action="${ctx}/folder/add" method="post" class="needs-validation">
                    <div class="form-group">
                        <label for="folderName">新增文件夹</label>
                        <input type="text" id="folderName" class="form-control" name="folderName" placeholder="文件夹名称" required>
                        <input type="hidden" name="parentFolderId" value="${data.folderId}">
                    </div>
                    <button type="submit" class="btn btn-primary">提交</button>
                </form>
                <form action="${ctx}/folder/update" method="post" class="needs-validation">
                    <div class="form-group">
                        <label for="upFolderName">修改文件夹</label>
                        <input type="text" id="upFolderName" class="form-control" name="folderName" placeholder="文件夹名称" required>
                        <input type="hidden" name="id" value="${data.folderId}">
                    </div>
                    <button type="submit" class="btn btn-primary">提交</button>
                </form>
                <div class="form-group">
                    <a href="javascript:;" onclick="deleteFolder()">删除文件夹</a>
                </div>
            </div>
        </div>
    </c:if>
    <p class="err-msg">${errMsg}</p>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
<script src="${resPath}/js/common.js"></script>
<script>
    function deleteFolder() {
        if(confirm('您确定要删除此文件夹吗？')){
            var url = baseUrl + '/folder/delete?id=${data.folderId}';
            var callback = function(data){
                window.open(baseUrl + '/personCenter/blog?folderId=' + data, '_self');
            };
            ajaxGet(url, callback);
        }
    }
</script>
</html>
