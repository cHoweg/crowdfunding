// 声明专门的函数显示确认模态框
function showConfirmModal(roleArray) {

    $("#confirmModal").modal("show");

    $("#roleNameDiv").empty();

    for (var i = 0; i < roleArray.length; i++) {

        // 全局变量存两个方法都用的角色ID
        window.roleIdArray = [];

        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName + "</br>")

        var roleId = role.roleId;

        // 使用数组的push()方法
        window.roleIdArray.push(roleId);
    }

}


// 执行分页,生成页面效果,调用此函数重新加载页面
function generatePage() {

    // 1.获取分页数据
    var pageInfo = getPageInfoRemote();

    // 2.填充表格
    fillTableBody(pageInfo);

}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {

    // 调用ajax函数发送请求并接受返回值
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async": false,
        "dataType": "json"
    })

    console.log(ajaxResult)

    // 判断当前响应状态码是否为200
    var statusCode = ajaxResult.status;

    // 如果当前请求处理失败状态码不是200，显示提示消息，让函数停止执行
    if (statusCode != 200) {
        layer.msg("Failed！响应状态码=" + statusCode + " 错误信息：" + ajaxResult.statusText);
        return null;
    }

    // 如果状态码是200，说明请求处理成功，获取pageInfo
    var resultEntity = ajaxResult.responseJSON;

    // 从resultEntity中获取result属性
    var result = resultEntity.result;

    // 判断result是否成功
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    // 确认result为成功后获取pageInfo
    var pageInfo = resultEntity.data;

    // 返回pageInfo
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {

    // 清除tbody的旧内容
    $("#rolePageBody").empty();

    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    // 判断pageInfo是否为有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {

        $("#rolePageBody").append("<tr><td colspan='4' align='center'>无数据!</td></tr>")
        return null;
    }

    // 使用pageInfo的list填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {

        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;

        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";

        // 通过button标签的id属性把roleId传递到响应函数中,函数中用this.Id
        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";

        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {

    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页",
    }

    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {

    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;

    $("#summaryBox").prop("checked", false);

    // 调用分页函数
    generatePage();

    // 取消页码超链接的默认行为
    return false;
}