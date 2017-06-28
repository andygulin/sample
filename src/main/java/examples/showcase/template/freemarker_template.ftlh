<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>测试</title>
</head>
<body>
<table width="100%">
    <tr>
        <td>ID</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>地址</td>
        <td>创建时间</td>
    </tr>
<#list users as item>
    <tr>
        <td>${item.id}</td>
        <td>${item.name}</td>
        <td>${item.age}</td>
        <td>${item.address}</td>
        <td>${item.createAt?string("yyyy-MM-dd HH:mm:ss")}</td>
    </tr>
</#list>
</table>
</body>
</html>