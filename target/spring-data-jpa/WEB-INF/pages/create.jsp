<%--
  Created by IntelliJ IDEA.
  User: zhangjie
  Date: 2014/12/8
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="/create.html" method="post">
    <input name="firstName" placeholder="firstName" type="text">
    <input name="lastName" placeholder="lastName" type="text">
    <input name="提交" type="submit">
</form>

<form action="/delete.html" method="post">
    <input name="firstName" value="${p.firstName}" placeholder="firstName" type="text">
    <input name="lastName" value="${p.lastName}" placeholder="lastName" type="text">
    <input name="id" value="${p.id}"  type="hidden">
    <input name="删除" type="submit">
</form>
</body>
</html>
