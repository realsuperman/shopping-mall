
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>마이페이지</title>
</head>
<body>

    ${sessionScope.get("login_user").getAddress()}
    <%= session.getAttribute("login_user") %>

    <li><a href="/cart">나의 장바구니</a></li>
    <li><a href="/orderSetList">나의 주문목록</a></li>

</body>
</html>
