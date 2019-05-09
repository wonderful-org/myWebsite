<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="curPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="resPath" value="${pageContext.request.contextPath}/static"/>
<script>
    window.ctx = "${ctx}";
</script>