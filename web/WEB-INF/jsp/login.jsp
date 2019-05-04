<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <link rel="stylesheet" href="${resPath}/css/login.css"/>
    <title>login</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-3 col-lg-3"></div>
        <div class="col-md-6 col-lg-6">
            <h1>登录</h1>
            <form class="form-signin" action="${ctx}/user/login" method="post">
                <div class="form-label-group">
                    <input type="text" id="username" name="username" class="form-control" placeholder="用户名/邮箱" maxlength="20" required autofocus>
                </div>
                <div class="form-label-group">
                    <input type="password" id="password" name="password" class="form-control" placeholder="请输入密码" maxlength="30" required>
                </div>
                <div>
                    <label>
                        <input type="checkbox" value="remember-me"> 记住我
                    </label>
                    <label class="label-right">
                        <a href="${ctx}/user/toRegister">还没有账号？去注册吧~</a>
                    </label>
                </div>
                <input type="hidden" name="curUrl" value="${data}">
                <p class="err-msg">${errMsg}</p>
                <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="return verify()">登录</button>
                <p class="mt-5 mb-3 text-muted text-center">&copy; 2018-2019</p>
            </form>
        </div>
        <div class="col-md-3 col-lg-3"></div>
    </div>
</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
<script src="${resPath}/js/common.js"></script>
<script>
    $(function(){
        $('#username').bind('input propertychange', function() {
            $('.err-msg').text('');
        });
        $('#password').bind('input propertychange', function() {
            $('.err-msg').text('');
        });
    });

    function verify() {
        var username = $('#username').val();
        var password = $('#password').val();
        if(isNotBlank(username) && username.length < usernameMinLength){
            $('.err-msg').text('用户名长度不可小于' + usernameMinLength + '且不可大于' + usernameMaxLength);
            return false;
        }
        if(isNotBlank(password) && password.length < passwordMinLength){
            $('.err-msg').text('密码长度不可小于' + passwordMinLength + '且不可大于' + passwordMaxLength);
            return false;
        }
        return true;
    }
</script>
</html>
