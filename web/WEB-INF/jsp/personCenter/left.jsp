<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-3 col-lg-3">
    <h4 class="d-flex justify-content-between align-items-center mb-3">
        <span class="text-muted">个人中心</span>
    </h4>
    <div class="list-group mb-3">
        <a href="${ctx}/personCenter/userInfo" class="list-group-item d-flex justify-content-between lh-condensed">
            <div>
                <h6 class="my-0">个人信息</h6>
                <small class="text-muted">完善你的个人资料</small>
            </div>
        </a>
        <a href="${ctx}/personCenter/blog" class="list-group-item d-flex justify-content-between lh-condensed">
            <div>
                <h6 class="my-0">个人博客</h6>
                <small class="text-muted">博客归档在这里</small>
            </div>
        </a>
    </div>
</div>