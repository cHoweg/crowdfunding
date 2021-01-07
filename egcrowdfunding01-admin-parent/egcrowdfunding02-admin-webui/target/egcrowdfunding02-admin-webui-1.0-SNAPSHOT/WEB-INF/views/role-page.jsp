<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zn-CN">
<head>
    <%@include file="/WEB-INF/views/include-head.jsp" %>
    <link rel="stylesheet" href="css/pagination.css">
    <script type="text/javascript" src="jquery/jquery.pagination.js"></script>
    <script src="crowd/my-role.js"></script>
    <script type="text/javascript">
        $(function () {

            // 1.为分页操作准备初始化数据 设置为window的全局变量
            window.pageNum = 1;
            window.pageSize = 5;
            window.keyword = "";

            // 2.调用执行分页的函数, 显示分页效果
            generatePage();

            // 3.查询按钮绑定响应函数
            $("#searchBtn").click(function () {

                // ①获取关键词赋值给全局变量
                window.keyword = $("#keywordInput").val();

                // ②调用分页函数刷新页面
                generatePage();
            })

            // 4.点击新增按钮打开模态框
            $("#showAddModalBtn").click(function () {
                $("#addModal").modal("show");
            });

            // 5.给新增模态框保存绑定绑定单击响应函数
            $("#saveRoleBtn").click(function () {

                // ①获取文本框的文本
                // [name=roleName]表示匹配name属性等于roleName的元素
                var roleName = $.trim($("#addModal :text[name=roleName]").val());
                layer.msg("1231312321")
                // ②发送Ajax请求
                $.ajax({
                    "url": "role/save.json",
                    "type": "post",
                    "data": {
                        "name": roleName
                    },
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;

                        if (result == "SUCCESS") {
                            layer.msg("保存成功: " + roleName)

                            // 页面定位到最后一页
                            window.pageNum = 999999;
                            // 重新加载分页数据
                            generatePage();
                        }

                        if (result == "FAILED") {
                            layer.msg("保存失败: " + response.message);
                        }
                    },
                    "error": function (response) {
                        layer.msg(response.status + " " + response.statusText);
                    }
                });

                // 关闭模态框
                $("#addModal").modal("hide");

                // 清空模态框
                $("#addModal [name=roleName]").val("");

            })

            // 6.给页面上的"铅笔"绑定单击响应函数
            // 使用jQuery的on()函数解决问题
            // ①找到所有"动态生成"的元素所附着的"静态"元素
            // ②on()函数的第一个参数是事件类型
            // ③on()函数的第二个参数是找到真正要绑定时间的元素选择器
            // ④on()函数的第三个参数是事件的响应函数
            $("#rolePageBody").on("click", ".pencilBtn", function () {

                // 打开模态框
                $("#editModal").modal("show");

                // 获取当前选择的角色名称
                $(this).parent().prev().text();

                // 获取当前角色的id
                window.roleId = this.id;

                // 使用roleName的值设置模态框中的文本框
                $("#editModal [name=roleName]").val(roleName);
            });

            // 7.更新模态框中的更新按钮绑定函数
            $("#updateRoleBtn").click(function () {

                // 取文本框中的文本
                var roleName = $("#editModal [name=roleName]").val();

                $.ajax({
                    "url": "role/update.json",
                    "type": "post",
                    "data": {
                        "id": window.roleId,
                        "name": roleName
                    },
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;

                        if (result == "SUCCESS") {
                            layer.msg("更新成功: " + roleName)

                            // 重新加载分页数据
                            generatePage();
                        }

                        if (result == "FAILED") {
                            layer.msg("更新失败: " + response.message);
                        }
                    },
                    "error": function (response) {
                        layer.msg(response.status + " " + response.statusText);
                    }
                })

                // 关闭模态框
                $("#editModal").modal("hide");
            })

            // 8.点击确认模态框中的确认删除按钮执行删除
            $("#removeRoleBtn").click(function () {

                // 从全局变量获取数组转为JSON字符串
                var requestBody = JSON.stringify(window.roleIdArray);

                $.ajax({
                    "url": "role/remove/by/role/id/array.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",
                    "success": function (response) {

                        var result = response.result;

                        if (result == "SUCCESS") {
                            layer.msg("删除成功")

                            // 重新加载分页数据
                            generatePage();
                        }

                        if (result == "FAILED") {
                            layer.msg("删除失败: " + response.message);
                        }
                    },
                    "error": function (response) {
                        layer.msg(response.status + " " + response.statusText);
                    }
                })

                // 关闭模态框
                $("#confirmModal").modal("hide");

                $("#summaryBox").prop("checked", false)
            })

            // 9.给单击删除按钮绑定函数
            $("#rolePageBody").on("click", ".removeBtn", function () {

                // 从当前按钮出发获取角色名称
                var roleName = $(this).parent().prev().text();

                // 创建role对象存入数组
                var roleArray = [{
                    roleId: this.id,
                    roleName: roleName
                }]

                // 调用专门的数组打开模态框
                showConfirmModal(roleArray);

            });

            // 10.给总的checkBox绑定单击响应函数(全选)
            $("#summaryBox").click(function () {

                // 获取当前多选框自身的状态
                var currentStatus = this.checked;

                // 用当前多选框的状态去设置其他多选框的状态
                $(".itemBox").prop("checked", currentStatus);
            })

            // 11.给总的checkBox绑定单击响应函数(全选√)
            $("#rolePageBody").on("click", ".itemBox", function () {

                // 获取当前已经选中的itemBox数量
                var checkedBoxCount = $(".itemBox:checked").length;

                // 获取起步的itemBox数量
                var totalBoxCount = $(".itemBox").length;

                // 使用二者比较结果来设置总checkBox
                $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount)
            })

            // 12.给批量删除的按钮绑定单击响应函数
            $("#batchRemoveBtn").click(function () {

                // 创建一个数组对象来存放后面获取的角色对象
                var roleArray = [];

                // 遍历当前选中的多选框
                $(".itemBox:checked").each(function () {


                    // 使用this引用当前遍历得到的多选框
                    var roleId = this.id;

                    // 通过DOM操作获取角色名称
                    var roleName = $(this).parent().next().text();

                    roleArray.push({
                        "roleId": roleId,
                        "roleName": roleName
                    });
                });

                // 判断选没
                if (roleArray.length == 0) {
                    layer.msg("请至少选择一个删除项")
                    return;
                }

                // 打开模态框
                showConfirmModal(roleArray);
            })
        });
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
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" id="showAddModalBtn" class="btn btn-primary" style="float:right;"
                    ><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"></tbody>
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
<%@include file="model-role-add.jsp" %>
<%@include file="model-role-edit.jsp" %>
<%@include file="model-role-confirm.jsp" %>
</body>
</html>