<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="margin-bottom: 50px">
    <table width=100%>
        <tr>
            <td><a href="${ctx}">icon</a></td>
            <td><input type="text">
                <button>搜索</button>
            </td>
            <td>简历</td>
            <td>博客</td>
            <td>留言板</td>
            <td><a href="${ctx}/user/toLogin">登陆</a></td>
            <td><a href="${ctx}/user/toRegister">注册</a><br></td>
            <td>当前用户：${empty currentUser.username ? "未登录" : currentUser.username}</td>
            <td><a href="${ctx}/user/logout">退出</a><br></td>
        </tr>
    </table>
    <hr>
</div>

