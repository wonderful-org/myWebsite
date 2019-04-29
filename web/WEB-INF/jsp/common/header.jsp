<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="bg-white">
    <div class="container">
        <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 bg-white box-shadow">
            <h5 class="my-0 mr-md-auto font-weight-normal"><a href="${ctx}">icon</a></h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="p-2 text-dark" href="${ctx}/note/toNotes">博客</a>
                <a class="p-2 text-dark" href="javascript:;">个人主页</a>
                <a class="p-2 text-dark" href="${ctx}/message/toMessages">留言区</a>
                <a class="p-2 text-dark" href="javascript:;">关于</a>
            </nav>
            <c:choose>
                <c:when test="${empty currentUser.username}">
                    <a class="btn btn-outline-primary" href="${ctx}/user/toLogin">登录</a>
                </c:when>
                <c:otherwise>
                    <div class="dropdown show">
                        <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                                ${currentUser.username}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item" href="${ctx}/user/logout">退出</a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<img class="img-responsive" src="${ctx}/static/img/jumbotron-bg-aviator.jpg"/>

