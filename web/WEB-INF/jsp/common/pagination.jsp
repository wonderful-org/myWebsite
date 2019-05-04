<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="paginationData" value="${empty paginationData ? data : paginationData}"/>
<div>
    <nav aria-label="...">
        <ul class="pagination">
            <li class="page-item ${paginationData.pageNum == 1 ? 'disabled' : null}">
                <a class="page-link" href="${paginationUrl}?pageNum=${paginationData.pageNum - 1}">&laquo;</a>
            </li>
            <c:if test="${paginationData.pageNum - 2 > 0}">
                <li class="page-item"><a class="page-link" href="${paginationUrl}?pageNum=${paginationData.pageNum - 2}">${paginationData.pageNum - 2}</a></li>
            </c:if>
            <c:if test="${paginationData.pageNum - 1 > 0}">
                <li class="page-item"><a class="page-link" href="${paginationUrl}?pageNum=${paginationData.pageNum - 1}">${paginationData.pageNum - 1}</a></li>
            </c:if>
            <li class="page-item active">
                <span class="page-link">${paginationData.pageNum}<span class="sr-only">(current)</span></span>
            </li>
            <c:if test="${paginationData.pageNum + 1 <= paginationData.pageCount}">
                <li class="page-item"><a class="page-link" href="${paginationUrl}?pageNum=${paginationData.pageNum + 1}">${paginationData.pageNum + 1}</a></li>
            </c:if>
            <c:if test="${paginationData.pageNum + 2 <= paginationData.pageCount}">
                <li class="page-item"><a class="page-link" href="${paginationUrl}?pageNum=${paginationData.pageNum + 2}">${paginationData.pageNum + 2}</a></li>
            </c:if>
            <li class="page-item ${paginationData.pageNum >= paginationData.pageCount ? 'disabled' : null}">
                <a class="page-link" href="${paginationUrl}?pageNum=${paginationData.pageNum + 1}">&raquo;</a>
            </li>
        </ul>
    </nav>
</div>