<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zn-CN">
<head>

    <%@include file="/WEB-INF/views/include-head.jsp" %>
    <link rel="stylesheet" href="css/pagination.css">
    <script type="text/javascript" src="jquery/jquery.pagination.js"></script>
    <script type="text/javascript">
        $(function () {
            initPagination()
        });

        // 生成页码导航条函数
        function initPagination() {
            // 获取总记录数
            var totalRecord = "${requestScope.pageInfo.total}";

            // 声明JSON对象存储Pagination要设置的属性
            var properties = {
                num_edge_entries: 3,                                 // 边缘页数
                num_display_entries: 5,                              // 主体页数
                callback: pageSelectCallBack,                        // 点击翻页按钮时的跳转页面的回调函数
                items_per_page: ${requestScope.pageInfo.pageSize},   // 每页要显示的条数
                current_page: ${requestScope.pageInfo.pageNum -1},   // 当前页，pageNum 从 1 开始，必须-1 后才可以赋值
                prev_text: "上一页",                                  // 上一页按钮上显示的文本
                next_text: "下一页"                                   // 上一页按钮上显示的文本
            };

            // 生成页码导航条
            $("#Pagination").pagination(totalRecord, properties);
        }

        // 点击"1 2 3"页码时调用此函数进行跳转
        // pageIndex是Pagination传给我们的从0开始的页码
        // pageNum 是用户在页面上点击的页码数值
        function pageSelectCallBack(pageIndex, jQuery) {

            // 根据pageIndex计算得到pageNum
            let pagNum = pageIndex + 1;

            // 跳转页面
            window.location.href = "admin/get/page.html?pageNum=" + pagNum + "&keyword=${param.keyword}";

            // 由于每一个页码按钮都是超链接, 所以在这个函数最后取消超链接的默认行为
            return false;
        }
    </script>
</head>
<body>

<%@include file="/WEB-INF/views/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/views/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.html" method="post" class="form-inline" role="form"
                          style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" name="keyword" type="text" value="${param.keyword}"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <a style="float:right;" href="admin/to/add/page.html" class="btn btn-primary"><i
                            class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" style="text-align: center">
                                        <h3 style="color: #ac2925">无数据！</h3>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <button type="button" class="btn btn-success btn-xs"><i
                                                    class=" glyphicon glyphicon-check"></i></button>
                                            <a href="admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs">
                                                <i class=" glyphicon glyphicon-pencil"></i>
                                            </a>
                                            <a href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html"
                                               class="btn btn-danger btn-xs">
                                                <i class=" glyphicon glyphicon-remove"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination">
                                        <!-- 这里显示分页 -->
                                    </div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>