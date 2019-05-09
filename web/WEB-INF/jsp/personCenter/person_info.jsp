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
    <title>个人中心</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container">
    <c:if test="${empty errMsg}">
        <div class="row">
            <%@ include file="left.jsp" %>
            <div class="col-md-9 col-lg-9">
                <h4 class="mb-3">个人信息</h4>
                <hr class="mb-4">
                <form class="needs-validation" novalidate action="${ctx}/personCenter/addUserInfo" method="post">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="realName">真实姓名</label>
                            <input type="text" class="form-control" id="realName" name="realName" placeholder="" value="${empty data ? '' : data.realName}" required>
                            <div class="invalid-feedback">
                                Valid real name is required.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="nickname">昵称</label>
                            <input type="text" class="form-control" id="nickname" name="nickname" placeholder="" value="${empty data ? '' : data.nickname}" required>
                            <div class="invalid-feedback">
                                Valid nick name is required.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="gender">性别</label>
                            <select class="custom-select d-block w-100" id="gender" name="gender" required>
                                <c:choose>
                                    <c:when test="${empty data or data.gender == 0}">
                                        <option value="0" selected>保密</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="0">保密</option>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${data.gender == 1}">
                                        <option value="1" selected>男</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="1">男</option>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${data.gender == 2}">
                                        <option value="2" selected>女</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="2">女</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                            <div class="invalid-feedback">
                                Please select a gender.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="birthday">出生日期</label>
                            <input type="text" class="form-control" id="birthday" name="birthday" placeholder="" value="${empty data ? '' : data.birthday}" required>
                            <div class="invalid-feedback">
                                Valid birthday is required.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="phone">联系号码</label>
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="" value="${empty data ? '' : data.phone}" required>
                            <div class="invalid-feedback">
                                Valid phone is required.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="personWebsite">个人网站</label>
                            <input type="text" class="form-control" id="personWebsite" name="personWebsite" placeholder="" value="${empty data ? '' : data.personWebsite}" required>
                            <div class="invalid-feedback">
                                Valid personWebsite is required.
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="" value="${empty data ? '' : data.email}">
                        <div class="invalid-feedback">
                            Please enter a valid email address for shipping updates.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="address">Address</label>
                        <input type="text" class="form-control" id="address" name="address" placeholder="" value="${empty data ? '' : data.address}" required>
                        <div class="invalid-feedback">
                            Please enter your shipping address.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="introduction">个人简介</label>
                        <textarea type="text" class="form-control" id="introduction" name="introduction" placeholder="" required>${empty data ? '' : data.introduction}</textarea>
                        <div class="invalid-feedback">
                            Please enter your description.
                        </div>
                    </div>

                    <hr class="mb-4">
                    <button class="btn btn-primary btn-lg btn-block" type="submit">保存</button>
                </form>
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
</html>
