<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${resPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resPath}/css/global.css"/>
    <link rel="stylesheet" href="${resPath}/css/summernote-bs4.css"/>
    <title>写博客</title>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <form action="" method="post">
        <div class="form-group">
            <label for="title">文章标题<span class="required-mark">*</span></label>
            <input type="text" class="form-control" id="title" placeholder="想一个响亮的标题吧~">
        </div>
        <div class="form-group">
            <label for="source">文章来源</label>
            <input id="source" type="text" class="form-control" placeholder="非原创文章因标明来源哦~">
        </div>
        <!-- 文本编辑器 -->
        <div id="content" class="form-group">
            <label for="summernote">文章内容<span class="required-mark">*</span></label>
            <textarea id="summernote" name="contentHtml"></textarea>
        </div>
        <!-- 文本编辑器结束 -->
        <div class="form-group">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="open" checked>
                <label class="form-check-label" for="open">
                    公开文章
                </label>
            </div>
        </div>
        <p class="err-msg">${errMsg}</p>
        <div class="form-group">
            <button type="submit" class="btn btn-primary" onclick="return submitForm()">提交</button>
        </div>
    </form>
</div>
<%@ include file="common/footer.jsp" %>
</body>
<script src="${resPath}/js/jquery-3.4.0.min.js"></script>
<script src="${resPath}/js/popper.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
<script src="${resPath}/js/common.js"></script>
<script src="${resPath}/js/summernote-bs4.min.js"></script>
<script src="${resPath}/js/summernote-zh-CN.min.js"></script>
<script>
    var imgUrlArray = new Array();
    $(function () {
        $('#title').bind('input propertychange', function() {
            $('.err-msg').text('');
        });
        $('#content').bind('input propertychange', function() {
            $('.err-msg').text('');
        });

        $('#summernote').summernote({
           placeholder: '写点什么吧~',
            height: 340,
            lang: 'zh-CN',
            callbacks: {
                onImageUpload: function (files) {
                    var formData = new FormData();
                    formData.append('file', files[0]);
                    $.ajax({
                        url: baseUrl + '/upload/note/img',
                        type: 'POST',
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (res) {
                            var code = res.code;
                            if (code == '00000') {
                                var url = res.data;
                                $('#summernote').summernote('insertImage', baseUrl + url, url.substr(url.lastIndexOf('/') + 1));
                                imgUrlArray.push(url);
                            } else {
                                var msg = res.msg;
                                $('.err-msg').text(msg);
                            }
                        }
                    });
                }
            }
        });
    });

    function submitForm() {
        var title = $('#title').val();
        var source = $('#source').val();
        var content = $('#summernote').summernote('code');
        var isOpen = $('#open').prop('checked') ? 0 : 1;
        if(isBlank(title)){
            $('.err-msg').text('标题不可为空');
        }else if(isBlank(content)){
            $('.err-msg').text('内容不可为空');
        }else {
            var url = baseUrl + '/note/add';
            var data = {
                title: title,
                content: content,
                source: source,
                open: isOpen,
                imgUrls: imgUrlArray
            };
            var toNotes = function () {
                window.open(baseUrl + '/note/toNotes', '_self');
            };
            var failCallback = function(res){
                $('.err-msg').text(res.msg);
            };
            ajaxPost(url, data, toNotes, failCallback);
        }
        return false;
    }
</script>
</html>
