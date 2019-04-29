<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>test</title>
</head>
<body>
    返回码：${code}<br>
    返回信息：${msg}<br>
    测试请求参数: name -> ${data}

    <br><br>
    <div id="ajaxTestGet">ajax测试，get请求，返回json：</div>
    <div id="ajaxTestPost">ajax测试，post请求，返回json：</div>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/common.js"></script>
<script>
    $(function(){
        var getUrl = getRealPath() + '/test/userJsonGet?username=get请求测试用户名&password=123';
        var successCallbackGet = function (data) {
            $('#ajaxTestGet').append(toJsonString(data));
        };
        ajaxGet(getUrl, successCallbackGet);

        var postUrl = getRealPath() + '/test/userJsonPost';
        var postData = {
            username:'post请求测试用户名',
            password:123
        };
        var successCallbackPost = function (data) {
            $('#ajaxTestPost').append(toJsonString(data));
        };
        ajaxPost(postUrl, postData, successCallbackPost);
    });
</script>
</html>
