<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <nav aria-label="...">
        <ul class="pagination">
            <li class="page-item ${data.pageNum == 1 ? 'disabled' : null}">
                <a class="page-link" href="${ctx}/message/messages?pageNum=${data.pageNum - 1}">&laquo;</a>
            </li>
            <c:if test="${data.pageNum - 2 > 0}">
                <li class="page-item"><a class="page-link" href="${ctx}/message/messages?pageNum=${data.pageNum - 2}">${data.pageNum - 2}</a></li>
            </c:if>
            <c:if test="${data.pageNum - 1 > 0}">
                <li class="page-item"><a class="page-link" href="${ctx}/message/messages?pageNum=${data.pageNum - 1}">${data.pageNum - 1}</a></li>
            </c:if>
            <li class="page-item active">
                <span class="page-link">${data.pageNum}<span class="sr-only">(current)</span></span>
            </li>
            <c:if test="${data.pageNum + 1 <= data.pageCount}">
                <li class="page-item"><a class="page-link" href="${ctx}/message/messages?pageNum=${data.pageNum + 1}">${data.pageNum + 1}</a></li>
            </c:if>
            <c:if test="${data.pageNum + 2 <= data.pageCount}">
                <li class="page-item"><a class="page-link" href="${ctx}/message/messages?pageNum=${data.pageNum + 2}">${data.pageNum + 2}</a></li>
            </c:if>
            <li class="page-item ${data.pageNum >= data.pageCount ? 'disabled' : null}">
                <a class="page-link" href="${ctx}/message/messages?pageNum=${data.pageNum + 1}">&raquo;</a>
            </li>
        </ul>
    </nav>
</div>