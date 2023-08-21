<%@ page import="com.bit.shoppingmall.dto.OrderSetDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>

</script>

<html>
<head>
    <title>주문 내역 목록</title>
</head>
<body>
    <!-- [상품명 외 X종, 배송지, 배송지 연락처]의 목록 조회 -->
    <%
        List<OrderSetDto> orderSetDtoList = (List<OrderSetDto>) request.getAttribute("consumerOrderSetList");
        for(OrderSetDto orderSetDto: orderSetDtoList) {
            out.print(orderSetDto.toString()+"<br>");
        }
    %>
</body>
</html>
