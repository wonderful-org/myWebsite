<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>register</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<h1>注册</h1>
    <form action="${ctx}/user/register" method="post">
        用户名：<input name="username" type="text"><br>
        密码：<input name="password" type="password"><br>
        <input type="submit" value="注册">
    </form>
    <div style="color: red">${errMsg}</div>
</body>
</html>
