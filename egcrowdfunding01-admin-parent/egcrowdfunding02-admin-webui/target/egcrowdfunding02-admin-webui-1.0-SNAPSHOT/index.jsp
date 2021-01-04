<%--
  Created by IntelliJ IDEA.
  User: eugenechow
  Date: 2020/12/24
  Time: 下午3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>testJsp</title>
</head>
<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $("#btn1").click(function () {
            $.ajax({
                "url": "send/arrayOne.html",
                "type": "post",
                "data": {
                    "array": [2,5,8]
                },
                "dataType": "text",
                "success": function (response) {
                    // 服务器成功处理请求后回调函数
                    alert(response)
                },
                "error": function(response){
                    // 失败后回调函数
                    alert(response)
                }
            });
        })

        $("#btn2").click(function () {
            $.ajax({
                "url": "send/arrayTow.html",
                "type": "post",
                "data": {
                    "array[0]": 2,
                    "array[1]": 5,
                    "array[2]": 6,
                },
                "dataType": "text",
                "success": function (response) {
                    // 服务器成功处理请求后回调函数
                    alert(response)
                },
                "error": function(response){
                    // 失败后回调函数
                    alert(response)
                }
            });
        })

        $("#btn3").click(function () {
            let array = [4, 3, 2];

            // 将JSON数组转换为JSON字符串
            let requestBody = JSON.stringify(array);

            $.ajax({
                "url": "send/arrayThree.html",
                "type": "post",
                "data": requestBody,  // 请求体
                "contentType": "application/json;charset=UTF-8",  // 设置请求体的内容类型
                "dataType": "text",
                "success": function (response) {
                    // 服务器成功处理请求后回调函数
                    alert(response)
                },
                "error": function(response){
                    // 失败后回调函数
                    alert(response)
                }
            });
        })

        $("#btn4").click(function () {
            let student = {
                "stuId": 5,
                "strName": "tom",
                "address": {
                    "province": "广东",
                    "city": "广州",
                    "street": "景溪大道"
                },
                "subjectList": [
                    {
                        "subjectName": "JavaSE",
                        "subjectScore": "100"
                    },
                    {
                        "subjectName": "Spring",
                        "subjectScore": "90"
                    }
                ],
                "map": {
                    "key1": "v1",
                    "key2": "v2"
                }
            };

            let requestBody = JSON.stringify(student)

            $.ajax({
                "url": "send/compose/object.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "text",
                "success": function (response) {
                    console.log(response);
                },
                "error": function (response) {
                    console.log(response);
                }
            });
        });

        $("#btn5").click(function () {
            layer.msg("layer的弹框");
        });
    })


</script>
<body>
    <a href="test/ssm.html">测试ssm整合环境搭建</a>
    <br>
    <br>
    <button id="btn1" style="height: 50px">test send1[ ]</button>
    <br>
    <br>
    <button id="btn2" style="height: 50px">test send2[ ]</button>
    <br>
    <br>
    <button id="btn3" style="height: 50px">test send3[ ]</button>
    <br>
    <br>
    <button id="btn4" style="height: 50px">test send4[ ]</button>
    <br>
    <br>
    <button id="btn5" style="height: 50px">test send5[ ]</button>
</body>
</html>
